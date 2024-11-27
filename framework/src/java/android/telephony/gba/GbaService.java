package android.telephony.gba;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class GbaService extends android.app.Service {
    private static final boolean DBG = android.os.Build.IS_DEBUGGABLE;
    private static final int EVENT_GBA_AUTH_REQUEST = 1;
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.gba.GbaService";
    private static final java.lang.String TAG = "GbaService";
    private final android.telephony.gba.GbaService.GbaServiceHandler mHandler;
    private final android.util.SparseArray<android.telephony.IBootstrapAuthenticationCallback> mCallbacks = new android.util.SparseArray<>();
    private final android.telephony.gba.GbaService.IGbaServiceWrapper mBinder = new android.telephony.gba.GbaService.IGbaServiceWrapper();
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(TAG);

    public GbaService() {
        this.mHandlerThread.start();
        this.mHandler = new android.telephony.gba.GbaService.GbaServiceHandler(this.mHandlerThread.getLooper());
        android.util.Log.d(TAG, "GBA service created");
    }

    private class GbaServiceHandler extends android.os.Handler {
        GbaServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.telephony.gba.GbaAuthRequest gbaAuthRequest = (android.telephony.gba.GbaAuthRequest) message.obj;
                    synchronized (android.telephony.gba.GbaService.this.mCallbacks) {
                        android.telephony.gba.GbaService.this.mCallbacks.put(gbaAuthRequest.getToken(), gbaAuthRequest.getCallback());
                    }
                    android.telephony.gba.GbaService.this.onAuthenticationRequest(gbaAuthRequest.getSubId(), gbaAuthRequest.getToken(), gbaAuthRequest.getAppType(), gbaAuthRequest.getNafUrl(), gbaAuthRequest.getSecurityProtocol(), gbaAuthRequest.isForceBootStrapping());
                    return;
                default:
                    return;
            }
        }
    }

    public void onAuthenticationRequest(int i, int i2, int i3, android.net.Uri uri, byte[] bArr, boolean z) {
        reportAuthenticationFailure(i2, 1);
    }

    public final void reportKeysAvailable(int i, byte[] bArr, java.lang.String str) throws java.lang.RuntimeException {
        android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback;
        synchronized (this.mCallbacks) {
            iBootstrapAuthenticationCallback = this.mCallbacks.get(i);
            this.mCallbacks.remove(i);
        }
        if (iBootstrapAuthenticationCallback != null) {
            try {
                iBootstrapAuthenticationCallback.onKeysAvailable(i, bArr, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public final void reportAuthenticationFailure(int i, int i2) throws java.lang.RuntimeException {
        android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback;
        synchronized (this.mCallbacks) {
            iBootstrapAuthenticationCallback = this.mCallbacks.get(i);
            this.mCallbacks.remove(i);
        }
        if (iBootstrapAuthenticationCallback != null) {
            try {
                iBootstrapAuthenticationCallback.onAuthenticationFailure(i, i2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            android.util.Log.d(TAG, "GbaService Bound.");
            return this.mBinder;
        }
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quit();
        super.onDestroy();
    }

    private class IGbaServiceWrapper extends android.telephony.gba.IGbaService.Stub {
        private IGbaServiceWrapper() {
        }

        @Override // android.telephony.gba.IGbaService
        public void authenticationRequest(android.telephony.gba.GbaAuthRequest gbaAuthRequest) {
            if (android.telephony.gba.GbaService.DBG) {
                android.util.Log.d(android.telephony.gba.GbaService.TAG, "receive request: " + gbaAuthRequest);
            }
            android.telephony.gba.GbaService.this.mHandler.obtainMessage(1, gbaAuthRequest).sendToTarget();
        }
    }
}
