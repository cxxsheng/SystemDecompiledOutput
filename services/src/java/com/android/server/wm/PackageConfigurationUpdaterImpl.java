package com.android.server.wm;

/* loaded from: classes3.dex */
final class PackageConfigurationUpdaterImpl implements com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater {
    private static final java.lang.String TAG = "PackageConfigurationUpdaterImpl";
    private com.android.server.wm.ActivityTaskManagerService mAtm;
    private int mGrammaticalGender;
    private android.os.LocaleList mLocales;
    private java.lang.Integer mNightMode;
    private java.lang.String mPackageName;
    private final java.util.Optional<java.lang.Integer> mPid;
    private int mUserId;

    PackageConfigurationUpdaterImpl(int i, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mPid = java.util.Optional.of(java.lang.Integer.valueOf(i));
        this.mAtm = activityTaskManagerService;
    }

    PackageConfigurationUpdaterImpl(java.lang.String str, int i, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mPackageName = str;
        this.mUserId = i;
        this.mAtm = activityTaskManagerService;
        this.mPid = java.util.Optional.empty();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater
    public com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater setNightMode(int i) {
        synchronized (this) {
            this.mNightMode = java.lang.Integer.valueOf(i);
        }
        return this;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater
    public com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater setLocales(android.os.LocaleList localeList) {
        synchronized (this) {
            this.mLocales = localeList;
        }
        return this;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater
    public com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater setGrammaticalGender(int i) {
        synchronized (this) {
            this.mGrammaticalGender = i;
        }
        return this;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater
    public boolean commit() {
        int i;
        synchronized (this) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (this.mPid.isPresent()) {
                            com.android.server.wm.WindowProcessController process = this.mAtm.mProcessMap.getProcess(this.mPid.get().intValue());
                            if (process == null) {
                                android.util.Slog.w(TAG, "commit: Override application configuration failed: cannot find pid " + this.mPid);
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            i = process.mUid;
                            this.mUserId = process.mUserId;
                            this.mPackageName = process.mInfo.packageName;
                        } else {
                            int packageUid = this.mAtm.getPackageManagerInternalLocked().getPackageUid(this.mPackageName, 131072L, this.mUserId);
                            if (packageUid < 0) {
                                android.util.Slog.w(TAG, "commit: update of application configuration failed: userId or packageName not valid " + this.mUserId);
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            i = packageUid;
                        }
                        updateConfig(i, this.mPackageName);
                        boolean updateFromImpl = this.mAtm.mPackageConfigPersister.updateFromImpl(this.mPackageName, this.mUserId, this);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return updateFromImpl;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    private void updateConfig(int i, java.lang.String str) {
        android.util.ArraySet<com.android.server.wm.WindowProcessController> processes = this.mAtm.mProcessMap.getProcesses(i);
        if (processes == null || processes.isEmpty()) {
            return;
        }
        android.os.LocaleList combineLocalesIfOverlayExists = com.android.server.wm.LocaleOverlayHelper.combineLocalesIfOverlayExists(this.mLocales, this.mAtm.getGlobalConfiguration().getLocales());
        for (int size = processes.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowProcessController valueAt = processes.valueAt(size);
            if (valueAt.mInfo.packageName.equals(str)) {
                valueAt.applyAppSpecificConfig(this.mNightMode, combineLocalesIfOverlayExists, java.lang.Integer.valueOf(this.mGrammaticalGender));
            }
            valueAt.updateAppSpecificSettingsForAllActivitiesInPackage(str, this.mNightMode, combineLocalesIfOverlayExists, this.mGrammaticalGender);
        }
    }

    java.lang.Integer getNightMode() {
        return this.mNightMode;
    }

    android.os.LocaleList getLocales() {
        return this.mLocales;
    }

    java.lang.Integer getGrammaticalGender() {
        return java.lang.Integer.valueOf(this.mGrammaticalGender);
    }
}
