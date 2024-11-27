package com.android.server.systemcaptions;

/* loaded from: classes2.dex */
public final class SystemCaptionsManagerService extends com.android.server.infra.AbstractMasterSystemService<com.android.server.systemcaptions.SystemCaptionsManagerService, com.android.server.systemcaptions.SystemCaptionsManagerPerUserService> {
    public SystemCaptionsManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context, new com.android.server.infra.FrameworkResourcesServiceNameResolver(context, android.R.string.config_defaultProfcollectReportUploaderAction), null, 68);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public com.android.server.systemcaptions.SystemCaptionsManagerPerUserService newServiceLocked(int i, boolean z) {
        com.android.server.systemcaptions.SystemCaptionsManagerPerUserService systemCaptionsManagerPerUserService = new com.android.server.systemcaptions.SystemCaptionsManagerPerUserService(this, this.mLock, z, i);
        systemCaptionsManagerPerUserService.initializeLocked();
        return systemCaptionsManagerPerUserService;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractMasterSystemService
    public void onServiceRemoved(com.android.server.systemcaptions.SystemCaptionsManagerPerUserService systemCaptionsManagerPerUserService, int i) {
        synchronized (this.mLock) {
            systemCaptionsManagerPerUserService.destroyLocked();
        }
    }
}
