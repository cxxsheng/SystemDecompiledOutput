package com.android.server.rollback;

/* loaded from: classes2.dex */
public final class RollbackManagerService extends com.android.server.SystemService {
    private com.android.server.rollback.RollbackManagerServiceImpl mService;

    public RollbackManagerService(android.content.Context context) {
        super(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mService = new com.android.server.rollback.RollbackManagerServiceImpl(getContext());
        publishBinderService("rollback", this.mService);
        com.android.server.LocalServices.addService(com.android.server.rollback.RollbackManagerInternal.class, this.mService);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mService.onUnlockUser(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            this.mService.onBootCompleted();
        }
    }
}
