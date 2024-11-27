package com.android.server.pm;

/* loaded from: classes2.dex */
final class AppIdSettingMap {
    private int mFirstAvailableAppId;
    private final com.android.server.utils.WatchedArrayList<com.android.server.pm.SettingBase> mNonSystemSettings;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayList<com.android.server.pm.SettingBase>> mNonSystemSettingsSnapshot;
    private final com.android.server.utils.WatchedSparseArray<com.android.server.pm.SettingBase> mSystemSettings;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseArray<com.android.server.pm.SettingBase>> mSystemSettingsSnapshot;

    AppIdSettingMap() {
        this.mFirstAvailableAppId = 10000;
        this.mNonSystemSettings = new com.android.server.utils.WatchedArrayList<>();
        this.mNonSystemSettingsSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mNonSystemSettings, this.mNonSystemSettings, "AppIdSettingMap.mNonSystemSettings");
        this.mSystemSettings = new com.android.server.utils.WatchedSparseArray<>();
        this.mSystemSettingsSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mSystemSettings, this.mSystemSettings, "AppIdSettingMap.mSystemSettings");
    }

    AppIdSettingMap(com.android.server.pm.AppIdSettingMap appIdSettingMap) {
        this.mFirstAvailableAppId = 10000;
        this.mNonSystemSettings = appIdSettingMap.mNonSystemSettingsSnapshot.snapshot();
        this.mNonSystemSettingsSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mSystemSettings = appIdSettingMap.mSystemSettingsSnapshot.snapshot();
        this.mSystemSettingsSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    public boolean registerExistingAppId(int i, com.android.server.pm.SettingBase settingBase, java.lang.Object obj) {
        if (i >= 10000) {
            int i2 = i + com.android.server.am.ProcessList.INVALID_ADJ;
            for (int size = this.mNonSystemSettings.size(); i2 >= size; size++) {
                this.mNonSystemSettings.add(null);
            }
            if (this.mNonSystemSettings.get(i2) != null) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Adding duplicate app id: " + i + " name=" + obj);
                return false;
            }
            this.mNonSystemSettings.set(i2, settingBase);
            return true;
        }
        if (this.mSystemSettings.get(i) != null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Adding duplicate shared id: " + i + " name=" + obj);
            return false;
        }
        this.mSystemSettings.put(i, settingBase);
        return true;
    }

    public com.android.server.pm.SettingBase getSetting(int i) {
        if (i >= 10000) {
            int i2 = i - 10000;
            if (i2 < this.mNonSystemSettings.size()) {
                return this.mNonSystemSettings.get(i2);
            }
            return null;
        }
        return this.mSystemSettings.get(i);
    }

    public void removeSetting(int i) {
        if (i >= 10000) {
            int size = this.mNonSystemSettings.size();
            int i2 = i + com.android.server.am.ProcessList.INVALID_ADJ;
            if (i2 < size) {
                this.mNonSystemSettings.set(i2, null);
            }
        } else {
            this.mSystemSettings.remove(i);
        }
        setFirstAvailableAppId(i + 1);
    }

    private void setFirstAvailableAppId(int i) {
        if (i > this.mFirstAvailableAppId) {
            this.mFirstAvailableAppId = i;
        }
    }

    public void replaceSetting(int i, com.android.server.pm.SettingBase settingBase) {
        if (i >= 10000) {
            int size = this.mNonSystemSettings.size();
            int i2 = i + com.android.server.am.ProcessList.INVALID_ADJ;
            if (i2 < size) {
                this.mNonSystemSettings.set(i2, settingBase);
                return;
            }
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: calling replaceAppIdLpw to replace SettingBase at appId=" + i + " but nothing is replaced.");
            return;
        }
        this.mSystemSettings.put(i, settingBase);
    }

    public int acquireAndRegisterNewAppId(com.android.server.pm.SettingBase settingBase) {
        int size = this.mNonSystemSettings.size();
        for (int i = this.mFirstAvailableAppId + com.android.server.am.ProcessList.INVALID_ADJ; i < size; i++) {
            if (this.mNonSystemSettings.get(i) == null) {
                this.mNonSystemSettings.set(i, settingBase);
                return i + 10000;
            }
        }
        if (size > 9999) {
            return -1;
        }
        this.mNonSystemSettings.add(settingBase);
        return size + 10000;
    }

    public com.android.server.pm.AppIdSettingMap snapshot() {
        return new com.android.server.pm.AppIdSettingMap(this);
    }

    public void registerObserver(com.android.server.utils.Watcher watcher) {
        this.mNonSystemSettings.registerObserver(watcher);
        this.mSystemSettings.registerObserver(watcher);
    }
}
