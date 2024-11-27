package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class DevicePolicyKeyguardService extends android.app.Service {
    private static final java.lang.String TAG = "DevicePolicyKeyguardService";
    private android.app.admin.IKeyguardCallback mCallback;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private final android.app.admin.IKeyguardClient mClient = new android.app.admin.DevicePolicyKeyguardService.AnonymousClass1();

    /* renamed from: android.app.admin.DevicePolicyKeyguardService$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.admin.IKeyguardClient.Stub {
        AnonymousClass1() {
        }

        @Override // android.app.admin.IKeyguardClient
        public void onCreateKeyguardSurface(final android.os.IBinder iBinder, android.app.admin.IKeyguardCallback iKeyguardCallback) {
            android.app.admin.DevicePolicyKeyguardService.this.mCallback = iKeyguardCallback;
            android.app.admin.DevicePolicyKeyguardService.this.mHandler.post(new java.lang.Runnable() { // from class: android.app.admin.DevicePolicyKeyguardService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.admin.DevicePolicyKeyguardService.AnonymousClass1.this.lambda$onCreateKeyguardSurface$0(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateKeyguardSurface$0(android.os.IBinder iBinder) {
            try {
                android.app.admin.DevicePolicyKeyguardService.this.mCallback.onRemoteContentReady(android.app.admin.DevicePolicyKeyguardService.this.onCreateKeyguardSurface(iBinder));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.app.admin.DevicePolicyKeyguardService.TAG, "Failed to return created SurfacePackage", e);
            }
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mClient.asBinder();
    }

    public android.view.SurfaceControlViewHost.SurfacePackage onCreateKeyguardSurface(android.os.IBinder iBinder) {
        return null;
    }

    public void dismiss() {
        if (this.mCallback == null) {
            android.util.Log.w(TAG, "KeyguardCallback was unexpectedly null");
            return;
        }
        try {
            this.mCallback.onDismiss();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "onDismiss failed", e);
        }
    }
}
