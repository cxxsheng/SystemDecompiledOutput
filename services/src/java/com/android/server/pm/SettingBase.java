package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public abstract class SettingBase implements com.android.server.utils.Watchable, com.android.server.utils.Snappable {
    private int mPkgFlags;
    private int mPkgPrivateFlags;
    private final com.android.server.utils.Watchable mWatchable = new com.android.server.utils.WatchableImpl();

    @java.lang.Deprecated
    protected final com.android.server.pm.permission.LegacyPermissionState mLegacyPermissionsState = new com.android.server.pm.permission.LegacyPermissionState();

    @Override // com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public void onChanged() {
        com.android.server.pm.pkg.mutate.PackageStateMutator.onPackageStateChanged();
        dispatchChange(this);
    }

    SettingBase(int i, int i2) {
        setFlags(i);
        setPrivateFlags(i2);
    }

    SettingBase(@android.annotation.Nullable com.android.server.pm.SettingBase settingBase) {
        if (settingBase != null) {
            copySettingBase(settingBase);
        }
    }

    public final void copySettingBase(com.android.server.pm.SettingBase settingBase) {
        this.mPkgFlags = settingBase.mPkgFlags;
        this.mPkgPrivateFlags = settingBase.mPkgPrivateFlags;
        this.mLegacyPermissionsState.copyFrom(settingBase.mLegacyPermissionsState);
        onChanged();
    }

    @java.lang.Deprecated
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState() {
        return this.mLegacyPermissionsState;
    }

    public com.android.server.pm.SettingBase setFlags(int i) {
        this.mPkgFlags = i;
        onChanged();
        return this;
    }

    public com.android.server.pm.SettingBase setPrivateFlags(int i) {
        this.mPkgPrivateFlags = i;
        onChanged();
        return this;
    }

    public int getFlags() {
        return this.mPkgFlags;
    }

    public int getPrivateFlags() {
        return this.mPkgPrivateFlags;
    }
}
