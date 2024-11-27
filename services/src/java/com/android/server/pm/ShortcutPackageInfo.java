package com.android.server.pm;

/* loaded from: classes2.dex */
class ShortcutPackageInfo {
    private static final java.lang.String ATTR_BACKUP_ALLOWED = "allow-backup";
    private static final java.lang.String ATTR_BACKUP_ALLOWED_INITIALIZED = "allow-backup-initialized";
    private static final java.lang.String ATTR_BACKUP_SOURCE_BACKUP_ALLOWED = "bk_src_backup-allowed";
    private static final java.lang.String ATTR_BACKUP_SOURCE_VERSION = "bk_src_version";
    private static final java.lang.String ATTR_LAST_UPDATE_TIME = "last_udpate_time";
    private static final java.lang.String ATTR_SHADOW = "shadow";
    private static final java.lang.String ATTR_SIGNATURE_HASH = "hash";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final java.lang.String TAG = "ShortcutService";
    static final java.lang.String TAG_ROOT = "package-info";
    private static final java.lang.String TAG_SIGNATURE = "signature";
    private boolean mBackupAllowedInitialized;
    private boolean mIsShadow;
    private long mLastUpdateTime;
    private java.util.ArrayList<byte[]> mSigHashes;
    private long mVersionCode;
    private long mBackupSourceVersionCode = -1;
    private boolean mBackupAllowed = false;
    private boolean mBackupSourceBackupAllowed = false;

    private ShortcutPackageInfo(long j, long j2, java.util.ArrayList<byte[]> arrayList, boolean z) {
        this.mVersionCode = -1L;
        this.mVersionCode = j;
        this.mLastUpdateTime = j2;
        this.mIsShadow = z;
        this.mSigHashes = arrayList;
    }

    public static com.android.server.pm.ShortcutPackageInfo newEmpty() {
        return new com.android.server.pm.ShortcutPackageInfo(-1L, 0L, new java.util.ArrayList(0), false);
    }

    public boolean isShadow() {
        return this.mIsShadow;
    }

    public void setShadow(boolean z) {
        this.mIsShadow = z;
    }

    public long getVersionCode() {
        return this.mVersionCode;
    }

    public long getBackupSourceVersionCode() {
        return this.mBackupSourceVersionCode;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isBackupSourceBackupAllowed() {
        return this.mBackupSourceBackupAllowed;
    }

    public long getLastUpdateTime() {
        return this.mLastUpdateTime;
    }

    public boolean isBackupAllowed() {
        return this.mBackupAllowed;
    }

    public void updateFromPackageInfo(@android.annotation.NonNull android.content.pm.PackageInfo packageInfo) {
        if (packageInfo != null) {
            this.mVersionCode = packageInfo.getLongVersionCode();
            this.mLastUpdateTime = packageInfo.lastUpdateTime;
            this.mBackupAllowed = com.android.server.pm.ShortcutService.shouldBackupApp(packageInfo);
            this.mBackupAllowedInitialized = true;
        }
    }

    public boolean hasSignatures() {
        return this.mSigHashes.size() > 0;
    }

    public int canRestoreTo(com.android.server.pm.ShortcutService shortcutService, android.content.pm.PackageInfo packageInfo, boolean z) {
        if (!com.android.server.backup.BackupUtils.signaturesMatch(this.mSigHashes, packageInfo, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class))) {
            android.util.Slog.w(TAG, "Can't restore: Package signature mismatch");
            return 102;
        }
        if (!com.android.server.pm.ShortcutService.shouldBackupApp(packageInfo) || !this.mBackupSourceBackupAllowed) {
            android.util.Slog.w(TAG, "Can't restore: package didn't or doesn't allow backup");
            return 101;
        }
        if (!z && packageInfo.getLongVersionCode() < this.mBackupSourceVersionCode) {
            android.util.Slog.w(TAG, java.lang.String.format("Can't restore: package current version %d < backed up version %d", java.lang.Long.valueOf(packageInfo.getLongVersionCode()), java.lang.Long.valueOf(this.mBackupSourceVersionCode)));
            return 100;
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.pm.ShortcutPackageInfo generateForInstalledPackageForTest(com.android.server.pm.ShortcutService shortcutService, java.lang.String str, int i) {
        android.content.pm.PackageInfo packageInfoWithSignatures = shortcutService.getPackageInfoWithSignatures(str, i);
        android.content.pm.SigningInfo signingInfo = packageInfoWithSignatures.signingInfo;
        if (signingInfo == null) {
            android.util.Slog.e(TAG, "Can't get signatures: package=" + str);
            return null;
        }
        com.android.server.pm.ShortcutPackageInfo shortcutPackageInfo = new com.android.server.pm.ShortcutPackageInfo(packageInfoWithSignatures.getLongVersionCode(), packageInfoWithSignatures.lastUpdateTime, com.android.server.backup.BackupUtils.hashSignatureArray(signingInfo.getApkContentsSigners()), false);
        shortcutPackageInfo.mBackupSourceBackupAllowed = com.android.server.pm.ShortcutService.shouldBackupApp(packageInfoWithSignatures);
        shortcutPackageInfo.mBackupSourceVersionCode = packageInfoWithSignatures.getLongVersionCode();
        return shortcutPackageInfo;
    }

    public void refreshSignature(com.android.server.pm.ShortcutService shortcutService, com.android.server.pm.ShortcutPackageItem shortcutPackageItem) {
        if (this.mIsShadow) {
            shortcutService.wtf("Attempted to refresh package info for shadow package " + shortcutPackageItem.getPackageName() + ", user=" + shortcutPackageItem.getOwnerUserId());
            return;
        }
        android.content.pm.PackageInfo packageInfoWithSignatures = shortcutService.getPackageInfoWithSignatures(shortcutPackageItem.getPackageName(), shortcutPackageItem.getPackageUserId());
        if (packageInfoWithSignatures == null) {
            android.util.Slog.w(TAG, "Package not found: " + shortcutPackageItem.getPackageName());
            return;
        }
        android.content.pm.SigningInfo signingInfo = packageInfoWithSignatures.signingInfo;
        if (signingInfo == null) {
            android.util.Slog.w(TAG, "Not refreshing signature for " + shortcutPackageItem.getPackageName() + " since it appears to have no signing info.");
            return;
        }
        this.mSigHashes = com.android.server.backup.BackupUtils.hashSignatureArray(signingInfo.getApkContentsSigners());
    }

    public void saveToXml(com.android.server.pm.ShortcutService shortcutService, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException {
        if (z && !this.mBackupAllowedInitialized) {
            shortcutService.wtf("Backup happened before mBackupAllowed is initialized.");
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_ROOT);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_VERSION, this.mVersionCode);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_LAST_UPDATE_TIME, this.mLastUpdateTime);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_SHADOW, this.mIsShadow);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_BACKUP_ALLOWED, this.mBackupAllowed);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_BACKUP_ALLOWED_INITIALIZED, this.mBackupAllowedInitialized);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_BACKUP_SOURCE_VERSION, this.mBackupSourceVersionCode);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_BACKUP_SOURCE_BACKUP_ALLOWED, this.mBackupSourceBackupAllowed);
        for (int i = 0; i < this.mSigHashes.size(); i++) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_SIGNATURE);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_SIGNATURE_HASH, java.util.Base64.getEncoder().encodeToString(this.mSigHashes.get(i)));
            typedXmlSerializer.endTag((java.lang.String) null, TAG_SIGNATURE);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_ROOT);
    }

    public void loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        long parseLongAttribute = com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_VERSION, -1L);
        long parseLongAttribute2 = com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_LAST_UPDATE_TIME);
        int i = 1;
        boolean z2 = z || com.android.server.pm.ShortcutService.parseBooleanAttribute(typedXmlPullParser, ATTR_SHADOW);
        long parseLongAttribute3 = com.android.server.pm.ShortcutService.parseLongAttribute(typedXmlPullParser, ATTR_BACKUP_SOURCE_VERSION, -1L);
        boolean parseBooleanAttribute = com.android.server.pm.ShortcutService.parseBooleanAttribute(typedXmlPullParser, ATTR_BACKUP_ALLOWED, true);
        boolean parseBooleanAttribute2 = com.android.server.pm.ShortcutService.parseBooleanAttribute(typedXmlPullParser, ATTR_BACKUP_SOURCE_BACKUP_ALLOWED, true);
        java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != i && (next != 3 || typedXmlPullParser.getDepth() > depth)) {
                if (next == 2) {
                    int depth2 = typedXmlPullParser.getDepth();
                    java.lang.String name = typedXmlPullParser.getName();
                    if (depth2 == depth + 1) {
                        switch (name.hashCode()) {
                            case 1073584312:
                                if (name.equals(TAG_SIGNATURE)) {
                                    c = 0;
                                    break;
                                }
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                arrayList.add(java.util.Base64.getDecoder().decode(com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_SIGNATURE_HASH)));
                                i = 1;
                                continue;
                        }
                    }
                    com.android.server.pm.ShortcutService.warnForInvalidTag(depth2, name);
                    i = 1;
                }
            }
        }
        if (z) {
            this.mVersionCode = -1L;
            this.mBackupSourceVersionCode = parseLongAttribute;
            this.mBackupSourceBackupAllowed = parseBooleanAttribute;
        } else {
            this.mVersionCode = parseLongAttribute;
            this.mBackupSourceVersionCode = parseLongAttribute3;
            this.mBackupSourceBackupAllowed = parseBooleanAttribute2;
        }
        this.mLastUpdateTime = parseLongAttribute2;
        this.mIsShadow = z2;
        this.mSigHashes = arrayList;
        this.mBackupAllowed = false;
        this.mBackupAllowedInitialized = false;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println();
        printWriter.print(str);
        printWriter.println("PackageInfo:");
        printWriter.print(str);
        printWriter.print("  IsShadow: ");
        printWriter.print(this.mIsShadow);
        printWriter.print(this.mIsShadow ? " (not installed)" : " (installed)");
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  Version: ");
        printWriter.print(this.mVersionCode);
        printWriter.println();
        if (this.mBackupAllowedInitialized) {
            printWriter.print(str);
            printWriter.print("  Backup Allowed: ");
            printWriter.print(this.mBackupAllowed);
            printWriter.println();
        }
        if (this.mBackupSourceVersionCode != -1) {
            printWriter.print(str);
            printWriter.print("  Backup source version: ");
            printWriter.print(this.mBackupSourceVersionCode);
            printWriter.println();
            printWriter.print(str);
            printWriter.print("  Backup source backup allowed: ");
            printWriter.print(this.mBackupSourceBackupAllowed);
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("  Last package update time: ");
        printWriter.print(this.mLastUpdateTime);
        printWriter.println();
        for (int i = 0; i < this.mSigHashes.size(); i++) {
            printWriter.print(str);
            printWriter.print("    ");
            printWriter.print("SigHash: ");
            printWriter.println(libcore.util.HexEncoding.encode(this.mSigHashes.get(i)));
        }
    }
}
