package com.android.server.accounts;

/* loaded from: classes.dex */
public final class AccountManagerBackupHelper {
    private static final java.lang.String ATTR_ACCOUNT_SHA_256 = "account-sha-256";
    private static final java.lang.String ATTR_DIGEST = "digest";
    private static final java.lang.String ATTR_PACKAGE = "package";
    private static final long PENDING_RESTORE_TIMEOUT_MILLIS = 3600000;
    private static final java.lang.String TAG = "AccountManagerBackupHelper";
    private static final java.lang.String TAG_PERMISSION = "permission";
    private static final java.lang.String TAG_PERMISSIONS = "permissions";
    private final android.accounts.AccountManagerInternal mAccountManagerInternal;
    private final com.android.server.accounts.AccountManagerService mAccountManagerService;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Runnable mRestoreCancelCommand;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.accounts.AccountManagerBackupHelper.RestorePackageMonitor mRestorePackageMonitor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.accounts.AccountManagerBackupHelper.PendingAppPermission> mRestorePendingAppPermissions;

    public AccountManagerBackupHelper(com.android.server.accounts.AccountManagerService accountManagerService, android.accounts.AccountManagerInternal accountManagerInternal) {
        this.mAccountManagerService = accountManagerService;
        this.mAccountManagerInternal = accountManagerInternal;
    }

    private final class PendingAppPermission {

        @android.annotation.NonNull
        private final java.lang.String accountDigest;

        @android.annotation.NonNull
        private final java.lang.String certDigest;

        @android.annotation.NonNull
        private final java.lang.String packageName;
        private final int userId;

        public PendingAppPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            this.accountDigest = str;
            this.packageName = str2;
            this.certDigest = str3;
            this.userId = i;
        }

        public boolean apply(android.content.pm.PackageManager packageManager) {
            android.accounts.Account account;
            com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = com.android.server.accounts.AccountManagerBackupHelper.this.mAccountManagerService.getUserAccounts(this.userId);
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    try {
                        account = null;
                        for (android.accounts.Account[] accountArr : userAccounts.accountCache.values()) {
                            int length = accountArr.length;
                            int i = 0;
                            while (true) {
                                if (i >= length) {
                                    break;
                                }
                                android.accounts.Account account2 = accountArr[i];
                                if (!this.accountDigest.equals(android.util.PackageUtils.computeSha256Digest(account2.name.getBytes()))) {
                                    i++;
                                } else {
                                    account = account2;
                                    break;
                                }
                            }
                            if (account != null) {
                                break;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            if (account == null) {
                return false;
            }
            try {
                android.content.pm.PackageInfo packageInfoAsUser = packageManager.getPackageInfoAsUser(this.packageName, 64, this.userId);
                java.lang.String[] computeSignaturesSha256Digests = android.util.PackageUtils.computeSignaturesSha256Digests(packageInfoAsUser.signatures);
                if (!this.certDigest.equals(android.util.PackageUtils.computeSignaturesSha256Digest(computeSignaturesSha256Digests)) && (packageInfoAsUser.signatures.length <= 1 || !this.certDigest.equals(computeSignaturesSha256Digests[0]))) {
                    return false;
                }
                int i2 = packageInfoAsUser.applicationInfo.uid;
                if (!com.android.server.accounts.AccountManagerBackupHelper.this.mAccountManagerInternal.hasAccountAccess(account, i2)) {
                    com.android.server.accounts.AccountManagerBackupHelper.this.mAccountManagerService.grantAppPermission(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", i2);
                }
                return true;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        }
    }

    public byte[] backupAccountAccessPermissions(int i) {
        com.android.server.accounts.AccountManagerService.UserAccounts userAccounts = this.mAccountManagerService.getUserAccounts(i);
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> findAllAccountGrants = userAccounts.accountsDb.findAllAccountGrants();
                if (findAllAccountGrants.isEmpty()) {
                    return null;
                }
                try {
                    java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                    com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
                    newFastSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                    newFastSerializer.startDocument((java.lang.String) null, true);
                    newFastSerializer.startTag((java.lang.String) null, TAG_PERMISSIONS);
                    android.content.pm.PackageManager packageManager = this.mAccountManagerService.mContext.getPackageManager();
                    for (android.util.Pair<java.lang.String, java.lang.Integer> pair : findAllAccountGrants) {
                        java.lang.String str = (java.lang.String) pair.first;
                        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(((java.lang.Integer) pair.second).intValue());
                        if (packagesForUid != null) {
                            for (java.lang.String str2 : packagesForUid) {
                                try {
                                    java.lang.String computeSignaturesSha256Digest = android.util.PackageUtils.computeSignaturesSha256Digest(packageManager.getPackageInfoAsUser(str2, 64, i).signatures);
                                    if (computeSignaturesSha256Digest != null) {
                                        newFastSerializer.startTag((java.lang.String) null, TAG_PERMISSION);
                                        newFastSerializer.attribute((java.lang.String) null, ATTR_ACCOUNT_SHA_256, android.util.PackageUtils.computeSha256Digest(str.getBytes()));
                                        newFastSerializer.attribute((java.lang.String) null, "package", str2);
                                        newFastSerializer.attribute((java.lang.String) null, ATTR_DIGEST, computeSignaturesSha256Digest);
                                        newFastSerializer.endTag((java.lang.String) null, TAG_PERMISSION);
                                    }
                                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                    android.util.Slog.i(TAG, "Skipping backup of account access grant for non-existing package: " + str2);
                                }
                            }
                        }
                    }
                    newFastSerializer.endTag((java.lang.String) null, TAG_PERMISSIONS);
                    newFastSerializer.endDocument();
                    newFastSerializer.flush();
                    return byteArrayOutputStream.toByteArray();
                } catch (java.io.IOException e2) {
                    android.util.Log.e(TAG, "Error backing up account access grants", e2);
                    return null;
                }
            }
        }
    }

    public void restoreAccountAccessPermissions(byte[] bArr, int i) {
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
            newFastPullParser.setInput(byteArrayInputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            android.content.pm.PackageManager packageManager = this.mAccountManagerService.mContext.getPackageManager();
            int depth = newFastPullParser.getDepth();
            while (true) {
                byte b = 0;
                if (com.android.internal.util.XmlUtils.nextElementWithin(newFastPullParser, depth)) {
                    if (TAG_PERMISSIONS.equals(newFastPullParser.getName())) {
                        int depth2 = newFastPullParser.getDepth();
                        while (com.android.internal.util.XmlUtils.nextElementWithin(newFastPullParser, depth2)) {
                            if (TAG_PERMISSION.equals(newFastPullParser.getName())) {
                                java.lang.String attributeValue = newFastPullParser.getAttributeValue((java.lang.String) null, ATTR_ACCOUNT_SHA_256);
                                if (android.text.TextUtils.isEmpty(attributeValue)) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(newFastPullParser);
                                }
                                java.lang.String attributeValue2 = newFastPullParser.getAttributeValue((java.lang.String) null, "package");
                                if (android.text.TextUtils.isEmpty(attributeValue2)) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(newFastPullParser);
                                }
                                java.lang.String attributeValue3 = newFastPullParser.getAttributeValue((java.lang.String) null, ATTR_DIGEST);
                                if (android.text.TextUtils.isEmpty(attributeValue3)) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(newFastPullParser);
                                }
                                com.android.server.accounts.AccountManagerBackupHelper.PendingAppPermission pendingAppPermission = new com.android.server.accounts.AccountManagerBackupHelper.PendingAppPermission(attributeValue, attributeValue2, attributeValue3, i);
                                if (!pendingAppPermission.apply(packageManager)) {
                                    synchronized (this.mLock) {
                                        try {
                                            if (this.mRestorePackageMonitor == null) {
                                                this.mRestorePackageMonitor = new com.android.server.accounts.AccountManagerBackupHelper.RestorePackageMonitor();
                                                this.mRestorePackageMonitor.register(this.mAccountManagerService.mContext, this.mAccountManagerService.mHandler.getLooper(), true);
                                            }
                                            if (this.mRestorePendingAppPermissions == null) {
                                                this.mRestorePendingAppPermissions = new java.util.ArrayList();
                                            }
                                            this.mRestorePendingAppPermissions.add(pendingAppPermission);
                                        } finally {
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    this.mRestoreCancelCommand = new com.android.server.accounts.AccountManagerBackupHelper.CancelRestoreCommand();
                    this.mAccountManagerService.mHandler.postDelayed(this.mRestoreCancelCommand, 3600000L);
                    return;
                }
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error restoring app permissions", e);
        }
    }

    private final class RestorePackageMonitor extends com.android.internal.content.PackageMonitor {
        private RestorePackageMonitor() {
        }

        public void onPackageAdded(java.lang.String str, int i) {
            synchronized (com.android.server.accounts.AccountManagerBackupHelper.this.mLock) {
                try {
                    if (com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePendingAppPermissions == null) {
                        return;
                    }
                    if (android.os.UserHandle.getUserId(i) != 0) {
                        return;
                    }
                    for (int size = com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePendingAppPermissions.size() - 1; size >= 0; size--) {
                        com.android.server.accounts.AccountManagerBackupHelper.PendingAppPermission pendingAppPermission = (com.android.server.accounts.AccountManagerBackupHelper.PendingAppPermission) com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePendingAppPermissions.get(size);
                        if (pendingAppPermission.packageName.equals(str) && pendingAppPermission.apply(com.android.server.accounts.AccountManagerBackupHelper.this.mAccountManagerService.mContext.getPackageManager())) {
                            com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePendingAppPermissions.remove(size);
                        }
                    }
                    if (com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePendingAppPermissions.isEmpty() && com.android.server.accounts.AccountManagerBackupHelper.this.mRestoreCancelCommand != null) {
                        com.android.server.accounts.AccountManagerBackupHelper.this.mAccountManagerService.mHandler.removeCallbacks(com.android.server.accounts.AccountManagerBackupHelper.this.mRestoreCancelCommand);
                        com.android.server.accounts.AccountManagerBackupHelper.this.mRestoreCancelCommand.run();
                        com.android.server.accounts.AccountManagerBackupHelper.this.mRestoreCancelCommand = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class CancelRestoreCommand implements java.lang.Runnable {
        private CancelRestoreCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.accounts.AccountManagerBackupHelper.this.mLock) {
                try {
                    com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePendingAppPermissions = null;
                    if (com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePackageMonitor != null) {
                        com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePackageMonitor.unregister();
                        com.android.server.accounts.AccountManagerBackupHelper.this.mRestorePackageMonitor = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
