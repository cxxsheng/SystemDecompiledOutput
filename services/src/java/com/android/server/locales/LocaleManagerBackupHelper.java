package com.android.server.locales;

/* loaded from: classes2.dex */
class LocaleManagerBackupHelper {
    private static final java.lang.String ATTR_DELEGATE_SELECTOR = "delegate_selector";
    private static final java.lang.String ATTR_LOCALES = "locales";
    private static final java.lang.String ATTR_PACKAGE_NAME = "name";
    private static final java.lang.String LOCALES_FROM_DELEGATE_PREFS = "LocalesFromDelegatePrefs.xml";
    private static final java.lang.String LOCALES_XML_TAG = "locales";
    private static final java.lang.String PACKAGE_XML_TAG = "package";
    private static final java.time.Duration STAGE_DATA_RETENTION_PERIOD = java.time.Duration.ofDays(3);
    private static final java.lang.String SYSTEM_BACKUP_PACKAGE_KEY = "android";
    private static final java.lang.String TAG = "LocaleManagerBkpHelper";
    private final java.time.Clock mClock;
    private final android.content.Context mContext;
    private final android.content.SharedPreferences mDelegateAppLocalePackages;
    private final com.android.server.locales.LocaleManagerService mLocaleManagerService;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.util.SparseArray<com.android.server.locales.LocaleManagerBackupHelper.StagedData> mStagedData;
    private final java.lang.Object mStagedDataLock;
    private final android.content.BroadcastReceiver mUserMonitor;

    LocaleManagerBackupHelper(com.android.server.locales.LocaleManagerService localeManagerService, android.content.pm.PackageManager packageManager, android.os.HandlerThread handlerThread) {
        this(localeManagerService.mContext, localeManagerService, packageManager, java.time.Clock.systemUTC(), new android.util.SparseArray(), handlerThread, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    LocaleManagerBackupHelper(android.content.Context context, com.android.server.locales.LocaleManagerService localeManagerService, android.content.pm.PackageManager packageManager, java.time.Clock clock, android.util.SparseArray<com.android.server.locales.LocaleManagerBackupHelper.StagedData> sparseArray, android.os.HandlerThread handlerThread, android.content.SharedPreferences sharedPreferences) {
        this.mStagedDataLock = new java.lang.Object();
        this.mContext = context;
        this.mLocaleManagerService = localeManagerService;
        this.mPackageManager = packageManager;
        this.mClock = clock;
        this.mStagedData = sparseArray;
        this.mDelegateAppLocalePackages = sharedPreferences == null ? createPersistedInfo() : sharedPreferences;
        this.mUserMonitor = new com.android.server.locales.LocaleManagerBackupHelper.UserMonitor();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        context.registerReceiverAsUser(this.mUserMonitor, android.os.UserHandle.ALL, intentFilter, null, handlerThread.getThreadHandler());
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.BroadcastReceiver getUserMonitor() {
        return this.mUserMonitor;
    }

    public byte[] getBackupPayload(int i) {
        boolean z;
        synchronized (this.mStagedDataLock) {
            cleanStagedDataForOldEntriesLocked();
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (android.content.pm.ApplicationInfo applicationInfo : this.mPackageManager.getInstalledApplicationsAsUser(android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), i)) {
            try {
                android.os.LocaleList applicationLocales = this.mLocaleManagerService.getApplicationLocales(applicationInfo.packageName, i);
                if (!applicationLocales.isEmpty()) {
                    if (this.mDelegateAppLocalePackages == null) {
                        z = false;
                    } else {
                        z = this.mDelegateAppLocalePackages.getStringSet(java.lang.Integer.toString(i), java.util.Collections.emptySet()).contains(applicationInfo.packageName);
                    }
                    hashMap.put(applicationInfo.packageName, new com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo(applicationLocales.toLanguageTags(), z));
                }
            } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Exception when getting locales for package: " + applicationInfo.packageName, e);
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            writeToXml(byteArrayOutputStream, hashMap);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Could not write to xml for backup ", e2);
            return null;
        }
    }

    private void cleanStagedDataForOldEntriesLocked() {
        for (int i = 0; i < this.mStagedData.size(); i++) {
            int keyAt = this.mStagedData.keyAt(i);
            if (this.mStagedData.get(keyAt).mCreationTimeMillis < this.mClock.millis() - STAGE_DATA_RETENTION_PERIOD.toMillis()) {
                deleteStagedDataLocked(keyAt);
            }
        }
    }

    public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
        if (bArr == null) {
            android.util.Slog.e(TAG, "stageAndApplyRestoredPayload: no payload to restore for user " + i);
            return;
        }
        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
        try {
            com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
            newFastPullParser.setInput(byteArrayInputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            com.android.internal.util.XmlUtils.beginDocument(newFastPullParser, "locales");
            java.util.HashMap<java.lang.String, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo> readFromXml = readFromXml(newFastPullParser);
            synchronized (this.mStagedDataLock) {
                try {
                    com.android.server.locales.LocaleManagerBackupHelper.StagedData stagedData = new com.android.server.locales.LocaleManagerBackupHelper.StagedData(this.mClock.millis(), new java.util.HashMap());
                    for (java.lang.String str : readFromXml.keySet()) {
                        com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo localesInfo = readFromXml.get(str);
                        if (isPackageInstalledForUser(str, i)) {
                            checkExistingLocalesAndApplyRestore(str, localesInfo, i);
                        } else {
                            stagedData.mPackageStates.put(str, localesInfo);
                        }
                    }
                    if (!stagedData.mPackageStates.isEmpty()) {
                        this.mStagedData.put(i, stagedData);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Could not parse payload ", e);
        }
    }

    public void notifyBackupManager() {
        android.app.backup.BackupManager.dataChanged("android");
    }

    void onPackageAdded(java.lang.String str, int i) {
        try {
            synchronized (this.mStagedDataLock) {
                try {
                    cleanStagedDataForOldEntriesLocked();
                    int userId = android.os.UserHandle.getUserId(i);
                    if (this.mStagedData.contains(userId)) {
                        doLazyRestoreLocked(str, userId);
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Exception in onPackageAdded.", e);
        }
    }

    void onPackageUpdateFinished(java.lang.String str, int i) {
        cleanApplicationLocalesIfNeeded(str, android.os.UserHandle.getUserId(i));
    }

    void onPackageDataCleared(java.lang.String str, int i) {
        try {
            notifyBackupManager();
            removePackageFromPersistedInfo(str, android.os.UserHandle.getUserId(i));
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Exception in onPackageDataCleared.", e);
        }
    }

    void onPackageRemoved(java.lang.String str, int i) {
        try {
            notifyBackupManager();
            removePackageFromPersistedInfo(str, android.os.UserHandle.getUserId(i));
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Exception in onPackageRemoved.", e);
        }
    }

    private boolean isPackageInstalledForUser(java.lang.String str, int i) {
        android.content.pm.PackageInfo packageInfo;
        try {
            packageInfo = this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    private void checkExistingLocalesAndApplyRestore(@android.annotation.NonNull java.lang.String str, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo localesInfo, int i) {
        if (localesInfo == null) {
            android.util.Slog.w(TAG, "No locales info for " + str);
            return;
        }
        try {
            if (!this.mLocaleManagerService.getApplicationLocales(str, i).isEmpty()) {
                return;
            }
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Could not check for current locales before restoring", e);
        }
        try {
            this.mLocaleManagerService.setApplicationLocales(str, i, android.os.LocaleList.forLanguageTags(localesInfo.mLocales), localesInfo.mSetFromDelegate, 3);
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, "Could not restore locales for " + str, e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteStagedDataLocked(int i) {
        this.mStagedData.remove(i);
    }

    @android.annotation.NonNull
    private java.util.HashMap<java.lang.String, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo> readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.HashMap<java.lang.String, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo> hashMap = new java.util.HashMap<>();
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("package")) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "locales");
                boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_DELEGATE_SELECTOR);
                if (!android.text.TextUtils.isEmpty(attributeValue) && !android.text.TextUtils.isEmpty(attributeValue2)) {
                    hashMap.put(attributeValue, new com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo(attributeValue2, attributeBoolean));
                }
            }
        }
        return hashMap;
    }

    private static void writeToXml(java.io.OutputStream outputStream, @android.annotation.NonNull java.util.HashMap<java.lang.String, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo> hashMap) throws java.io.IOException {
        if (hashMap.isEmpty()) {
            return;
        }
        com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((java.lang.String) null, true);
        newFastSerializer.startTag((java.lang.String) null, "locales");
        for (java.lang.String str : hashMap.keySet()) {
            newFastSerializer.startTag((java.lang.String) null, "package");
            newFastSerializer.attribute((java.lang.String) null, "name", str);
            newFastSerializer.attribute((java.lang.String) null, "locales", hashMap.get(str).mLocales);
            newFastSerializer.attributeBoolean((java.lang.String) null, ATTR_DELEGATE_SELECTOR, hashMap.get(str).mSetFromDelegate);
            newFastSerializer.endTag((java.lang.String) null, "package");
        }
        newFastSerializer.endTag((java.lang.String) null, "locales");
        newFastSerializer.endDocument();
    }

    static class StagedData {
        final long mCreationTimeMillis;
        final java.util.HashMap<java.lang.String, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo> mPackageStates;

        StagedData(long j, java.util.HashMap<java.lang.String, com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo> hashMap) {
            this.mCreationTimeMillis = j;
            this.mPackageStates = hashMap;
        }
    }

    static class LocalesInfo {
        final java.lang.String mLocales;
        final boolean mSetFromDelegate;

        LocalesInfo(java.lang.String str, boolean z) {
            this.mLocales = str;
            this.mSetFromDelegate = z;
        }
    }

    private final class UserMonitor extends android.content.BroadcastReceiver {
        private UserMonitor() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            try {
                if (intent.getAction().equals("android.intent.action.USER_REMOVED")) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                    synchronized (com.android.server.locales.LocaleManagerBackupHelper.this.mStagedDataLock) {
                        com.android.server.locales.LocaleManagerBackupHelper.this.deleteStagedDataLocked(intExtra);
                        com.android.server.locales.LocaleManagerBackupHelper.this.removeProfileFromPersistedInfo(intExtra);
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.locales.LocaleManagerBackupHelper.TAG, "Exception in user monitor.", e);
            }
        }
    }

    private void doLazyRestoreLocked(java.lang.String str, int i) {
        if (!isPackageInstalledForUser(str, i)) {
            android.util.Slog.e(TAG, str + " not installed for user " + i + ". Could not restore locales from stage data");
            return;
        }
        com.android.server.locales.LocaleManagerBackupHelper.StagedData stagedData = this.mStagedData.get(i);
        for (java.lang.String str2 : stagedData.mPackageStates.keySet()) {
            com.android.server.locales.LocaleManagerBackupHelper.LocalesInfo localesInfo = stagedData.mPackageStates.get(str2);
            if (str2.equals(str)) {
                checkExistingLocalesAndApplyRestore(str2, localesInfo, i);
                stagedData.mPackageStates.remove(str2);
                if (stagedData.mPackageStates.isEmpty()) {
                    this.mStagedData.remove(i);
                    return;
                }
                return;
            }
        }
    }

    android.content.SharedPreferences createPersistedInfo() {
        return this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(new java.io.File(android.os.Environment.getDataSystemDeDirectory(0), LOCALES_FROM_DELEGATE_PREFS), 0);
    }

    public android.content.SharedPreferences getPersistedInfo() {
        return this.mDelegateAppLocalePackages;
    }

    private void removePackageFromPersistedInfo(java.lang.String str, int i) {
        if (this.mDelegateAppLocalePackages == null) {
            android.util.Slog.w(TAG, "Failed to persist data into the shared preference!");
            return;
        }
        java.lang.String num = java.lang.Integer.toString(i);
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mDelegateAppLocalePackages.getStringSet(num, new android.util.ArraySet()));
        if (arraySet.contains(str)) {
            arraySet.remove(str);
            android.content.SharedPreferences.Editor edit = this.mDelegateAppLocalePackages.edit();
            edit.putStringSet(num, arraySet);
            if (!edit.commit()) {
                android.util.Slog.e(TAG, "Failed to commit data!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeProfileFromPersistedInfo(int i) {
        java.lang.String num = java.lang.Integer.toString(i);
        if (this.mDelegateAppLocalePackages == null || !this.mDelegateAppLocalePackages.contains(num)) {
            android.util.Slog.w(TAG, "The profile is not existed in the persisted info");
        } else if (!this.mDelegateAppLocalePackages.edit().remove(num).commit()) {
            android.util.Slog.e(TAG, "Failed to commit data!");
        }
    }

    void persistLocalesModificationInfo(int i, java.lang.String str, boolean z, boolean z2) {
        if (this.mDelegateAppLocalePackages == null) {
            android.util.Slog.w(TAG, "Failed to persist data into the shared preference!");
            return;
        }
        android.content.SharedPreferences.Editor edit = this.mDelegateAppLocalePackages.edit();
        java.lang.String num = java.lang.Integer.toString(i);
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mDelegateAppLocalePackages.getStringSet(num, new android.util.ArraySet()));
        if (z && !z2) {
            if (!arraySet.contains(str)) {
                arraySet.add(str);
                edit.putStringSet(num, arraySet);
            }
        } else if (arraySet.contains(str)) {
            arraySet.remove(str);
            edit.putStringSet(num, arraySet);
        }
        if (!edit.commit()) {
            android.util.Slog.e(TAG, "failed to commit locale setter info");
        }
    }

    boolean areLocalesSetFromDelegate(int i, java.lang.String str) {
        if (this.mDelegateAppLocalePackages == null) {
            android.util.Slog.w(TAG, "Failed to persist data into the shared preference!");
            return false;
        }
        return new android.util.ArraySet(this.mDelegateAppLocalePackages.getStringSet(java.lang.Integer.toString(i), new android.util.ArraySet())).contains(str);
    }

    private void cleanApplicationLocalesIfNeeded(java.lang.String str, int i) {
        if (this.mDelegateAppLocalePackages == null) {
            android.util.Slog.w(TAG, "Failed to persist data into the shared preference!");
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mDelegateAppLocalePackages.getStringSet(java.lang.Integer.toString(i), new android.util.ArraySet()));
        try {
            if (this.mLocaleManagerService.getApplicationLocales(str, i).isEmpty()) {
                return;
            }
            if (!arraySet.contains(str)) {
                return;
            }
            try {
                this.mLocaleManagerService.removeUnsupportedAppLocales(str, i, new android.app.LocaleConfig(this.mContext.createPackageContextAsUser(str, 0, android.os.UserHandle.of(i))), 4);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "Can not found the package name : " + str + " / " + e);
            }
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, "Exception when getting locales for " + str, e2);
        }
    }
}
