package com.android.server.systemcaptions;

/* loaded from: classes2.dex */
final class SystemCaptionsManagerPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.systemcaptions.SystemCaptionsManagerPerUserService, com.android.server.systemcaptions.SystemCaptionsManagerService> {
    private static final java.lang.String TAG = com.android.server.systemcaptions.SystemCaptionsManagerPerUserService.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.systemcaptions.RemoteSystemCaptionsManagerService mRemoteService;

    SystemCaptionsManagerPerUserService(@android.annotation.NonNull com.android.server.systemcaptions.SystemCaptionsManagerService systemCaptionsManagerService, @android.annotation.NonNull java.lang.Object obj, boolean z, int i) {
        super(systemCaptionsManagerService, obj, i);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @android.annotation.NonNull
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void initializeLocked() {
        if (((com.android.server.systemcaptions.SystemCaptionsManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "initialize()");
        }
        if (getRemoteServiceLocked() == null && ((com.android.server.systemcaptions.SystemCaptionsManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "initialize(): Failed to init remote server");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void destroyLocked() {
        if (((com.android.server.systemcaptions.SystemCaptionsManagerService) this.mMaster).verbose) {
            android.util.Slog.v(TAG, "destroyLocked()");
        }
        if (this.mRemoteService != null) {
            this.mRemoteService.destroy();
            this.mRemoteService = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.systemcaptions.RemoteSystemCaptionsManagerService getRemoteServiceLocked() {
        if (this.mRemoteService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.systemcaptions.SystemCaptionsManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "getRemoteServiceLocked(): Not set");
                    return null;
                }
                return null;
            }
            this.mRemoteService = new com.android.server.systemcaptions.RemoteSystemCaptionsManagerService(getContext(), android.content.ComponentName.unflattenFromString(componentNameLocked), this.mUserId, ((com.android.server.systemcaptions.SystemCaptionsManagerService) this.mMaster).verbose);
            if (((com.android.server.systemcaptions.SystemCaptionsManagerService) this.mMaster).verbose) {
                android.util.Slog.v(TAG, "getRemoteServiceLocked(): initialize for user " + this.mUserId);
            }
            this.mRemoteService.initialize();
        }
        return this.mRemoteService;
    }
}
