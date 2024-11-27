package com.android.server.ambientcontext;

/* loaded from: classes.dex */
public class DefaultAmbientContextManagerPerUserService extends com.android.server.ambientcontext.AmbientContextManagerPerUserService {
    private static final java.lang.String TAG = com.android.server.ambientcontext.DefaultAmbientContextManagerPerUserService.class.getSimpleName();
    private android.content.ComponentName mComponentName;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService mRemoteService;
    private final java.lang.String mServiceName;
    private final com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType mServiceType;

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public /* bridge */ /* synthetic */ void onQueryServiceStatus(int[] iArr, java.lang.String str, android.os.RemoteCallback remoteCallback) {
        super.onQueryServiceStatus(iArr, str, remoteCallback);
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public /* bridge */ /* synthetic */ void onRegisterObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, android.app.ambientcontext.IAmbientContextObserver iAmbientContextObserver) {
        super.onRegisterObserver(ambientContextEventRequest, str, iAmbientContextObserver);
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public /* bridge */ /* synthetic */ void onStartConsentActivity(int[] iArr, java.lang.String str) {
        super.onStartConsentActivity(iArr, str);
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public /* bridge */ /* synthetic */ void onUnregisterObserver(java.lang.String str) {
        super.onUnregisterObserver(str);
    }

    DefaultAmbientContextManagerPerUserService(@android.annotation.NonNull com.android.server.ambientcontext.AmbientContextManagerService ambientContextManagerService, java.lang.Object obj, int i, com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType serviceType, java.lang.String str) {
        super(ambientContextManagerService, obj, i);
        this.mServiceType = serviceType;
        this.mServiceName = str;
        this.mComponentName = android.content.ComponentName.unflattenFromString(this.mServiceName);
        android.util.Slog.d(TAG, "Created DefaultAmbientContextManagerPerUserServiceand service type: " + this.mServiceType.name() + " and service name: " + str);
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void ensureRemoteServiceInitiated() {
        if (this.mRemoteService == null) {
            this.mRemoteService = new com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService(getContext(), this.mComponentName, getUserId());
        }
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    @com.android.internal.annotations.VisibleForTesting
    android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected void setComponentName(android.content.ComponentName componentName) {
        this.mComponentName = componentName;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected com.android.server.ambientcontext.RemoteAmbientDetectionService getRemoteService() {
        return this.mRemoteService;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected java.lang.String getProtectedBindPermission() {
        return "android.permission.BIND_AMBIENT_CONTEXT_DETECTION_SERVICE";
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType getServiceType() {
        return this.mServiceType;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected int getAmbientContextPackageNameExtraKeyConfig() {
        return android.R.string.concurrent_display_notification_thermal_content;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected int getAmbientContextEventArrayExtraKeyConfig() {
        return android.R.string.concurrent_display_notification_power_save_title;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected int getConsentComponentConfig() {
        return android.R.string.config_customResolverActivity;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    protected void clearRemoteService() {
        this.mRemoteService = null;
    }
}
