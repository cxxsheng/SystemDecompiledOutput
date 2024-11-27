package android.permissionpresenterservice;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class RuntimePermissionPresenterService extends android.app.Service {
    private static final java.lang.String KEY_RESULT = "android.content.pm.permission.RuntimePermissionPresenter.key.result";
    public static final java.lang.String SERVICE_INTERFACE = "android.permissionpresenterservice.RuntimePermissionPresenterService";
    private android.os.Handler mHandler;

    public abstract java.util.List<android.content.pm.permission.RuntimePermissionPresentationInfo> onGetAppPermissions(java.lang.String str);

    @Override // android.app.Service, android.content.ContextWrapper
    public final void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.os.Handler(context.getMainLooper());
    }

    /* renamed from: android.permissionpresenterservice.RuntimePermissionPresenterService$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.pm.permission.IRuntimePermissionPresenter.Stub {
        AnonymousClass1() {
        }

        @Override // android.content.pm.permission.IRuntimePermissionPresenter
        public void getAppPermissions(java.lang.String str, android.os.RemoteCallback remoteCallback) {
            com.android.internal.util.Preconditions.checkNotNull(str, "packageName");
            com.android.internal.util.Preconditions.checkNotNull(remoteCallback, "callback");
            android.permissionpresenterservice.RuntimePermissionPresenterService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.permissionpresenterservice.RuntimePermissionPresenterService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.permissionpresenterservice.RuntimePermissionPresenterService) obj).getAppPermissions((java.lang.String) obj2, (android.os.RemoteCallback) obj3);
                }
            }, android.permissionpresenterservice.RuntimePermissionPresenterService.this, str, remoteCallback));
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.permissionpresenterservice.RuntimePermissionPresenterService.AnonymousClass1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAppPermissions(java.lang.String str, android.os.RemoteCallback remoteCallback) {
        java.util.List<android.content.pm.permission.RuntimePermissionPresentationInfo> onGetAppPermissions = onGetAppPermissions(str);
        if (onGetAppPermissions != null && !onGetAppPermissions.isEmpty()) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelableList(KEY_RESULT, onGetAppPermissions);
            remoteCallback.sendResult(bundle);
            return;
        }
        remoteCallback.sendResult(null);
    }
}
