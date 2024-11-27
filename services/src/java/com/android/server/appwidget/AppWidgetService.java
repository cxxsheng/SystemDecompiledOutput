package com.android.server.appwidget;

/* loaded from: classes.dex */
public class AppWidgetService extends com.android.server.SystemService {
    private final com.android.server.appwidget.AppWidgetServiceImpl mImpl;

    public AppWidgetService(android.content.Context context) {
        super(context);
        this.mImpl = new com.android.server.appwidget.AppWidgetServiceImpl(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mImpl.onStart();
        publishBinderService("appwidget", this.mImpl);
        com.android.server.AppWidgetBackupBridge.register(this.mImpl);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            this.mImpl.setSafeMode(isSafeMode());
            this.mImpl.systemServicesReady();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mImpl.onUserStopped(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        this.mImpl.reloadWidgetsMaskedStateForGroup(targetUser2.getUserIdentifier());
    }
}
