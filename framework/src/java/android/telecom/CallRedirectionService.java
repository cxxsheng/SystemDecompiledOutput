package android.telecom;

/* loaded from: classes3.dex */
public abstract class CallRedirectionService extends android.app.Service {
    private static final int MSG_PLACE_CALL = 1;
    private static final int MSG_TIMEOUT = 2;
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.CallRedirectionService";
    private com.android.internal.telecom.ICallRedirectionAdapter mCallRedirectionAdapter;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.telecom.CallRedirectionService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.CallRedirectionService.this.mCallRedirectionAdapter = (com.android.internal.telecom.ICallRedirectionAdapter) someArgs.arg1;
                        android.telecom.CallRedirectionService.this.onPlaceCall((android.net.Uri) someArgs.arg2, (android.telecom.PhoneAccountHandle) someArgs.arg3, ((java.lang.Boolean) someArgs.arg4).booleanValue());
                        return;
                    } finally {
                        someArgs.recycle();
                    }
                case 2:
                    android.telecom.CallRedirectionService.this.onRedirectionTimeout();
                    return;
                default:
                    return;
            }
        }
    };

    public abstract void onPlaceCall(android.net.Uri uri, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z);

    public void onRedirectionTimeout() {
    }

    public final void placeCallUnmodified() {
        try {
            if (this.mCallRedirectionAdapter == null) {
                throw new java.lang.IllegalStateException("Can only be called from onPlaceCall.");
            }
            this.mCallRedirectionAdapter.placeCallUnmodified();
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public final void redirectCall(android.net.Uri uri, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
        try {
            if (this.mCallRedirectionAdapter == null) {
                throw new java.lang.IllegalStateException("Can only be called from onPlaceCall.");
            }
            this.mCallRedirectionAdapter.redirectCall(uri, phoneAccountHandle, z);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public final void cancelCall() {
        try {
            if (this.mCallRedirectionAdapter == null) {
                throw new java.lang.IllegalStateException("Can only be called from onPlaceCall.");
            }
            this.mCallRedirectionAdapter.cancelCall();
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    private final class CallRedirectionBinder extends com.android.internal.telecom.ICallRedirectionService.Stub {
        private CallRedirectionBinder() {
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void placeCall(com.android.internal.telecom.ICallRedirectionAdapter iCallRedirectionAdapter, android.net.Uri uri, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = iCallRedirectionAdapter;
            obtain.arg2 = uri;
            obtain.arg3 = phoneAccountHandle;
            obtain.arg4 = java.lang.Boolean.valueOf(z);
            android.telecom.CallRedirectionService.this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void notifyTimeout() {
            android.telecom.CallRedirectionService.this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.telecom.CallRedirectionService.CallRedirectionBinder();
    }

    @Override // android.app.Service
    public final boolean onUnbind(android.content.Intent intent) {
        return false;
    }
}
