package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class CallStreamingService extends android.app.Service {
    private static final int MSG_CALL_STREAMING_STARTED = 2;
    private static final int MSG_CALL_STREAMING_STATE_CHANGED = 4;
    private static final int MSG_CALL_STREAMING_STOPPED = 3;
    private static final int MSG_SET_STREAMING_CALL_ADAPTER = 1;
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.CallStreamingService";
    public static final int STREAMING_FAILED_ALREADY_STREAMING = 1;
    public static final int STREAMING_FAILED_NO_SENDER = 2;
    public static final int STREAMING_FAILED_SENDER_BINDING_ERROR = 3;
    public static final int STREAMING_FAILED_UNKNOWN = 0;
    private android.telecom.StreamingCall mCall;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.telecom.CallStreamingService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (android.telecom.CallStreamingService.this.mStreamingCallAdapter == null && message.what != 1) {
                android.telecom.Log.i(this, "handleMessage: null adapter!", new java.lang.Object[0]);
            }
            switch (message.what) {
                case 1:
                    if (message.obj != null) {
                        android.telecom.Log.i(this, "MSG_SET_STREAMING_CALL_ADAPTER", new java.lang.Object[0]);
                        android.telecom.CallStreamingService.this.mStreamingCallAdapter = new android.telecom.StreamingCallAdapter((com.android.internal.telecom.IStreamingCallAdapter) message.obj);
                        break;
                    }
                    break;
                case 2:
                    android.telecom.Log.i(this, "MSG_CALL_STREAMING_STARTED", new java.lang.Object[0]);
                    android.telecom.CallStreamingService.this.mCall = (android.telecom.StreamingCall) message.obj;
                    android.telecom.CallStreamingService.this.mCall.setAdapter(android.telecom.CallStreamingService.this.mStreamingCallAdapter);
                    android.telecom.CallStreamingService.this.onCallStreamingStarted(android.telecom.CallStreamingService.this.mCall);
                    break;
                case 3:
                    android.telecom.Log.i(this, "MSG_CALL_STREAMING_STOPPED", new java.lang.Object[0]);
                    android.telecom.CallStreamingService.this.mCall = null;
                    android.telecom.CallStreamingService.this.mStreamingCallAdapter = null;
                    android.telecom.CallStreamingService.this.onCallStreamingStopped();
                    break;
                case 4:
                    int intValue = ((java.lang.Integer) message.obj).intValue();
                    if (android.telecom.CallStreamingService.this.mStreamingCallAdapter != null) {
                        android.telecom.CallStreamingService.this.mCall.requestStreamingState(intValue);
                        android.telecom.CallStreamingService.this.onCallStreamingStateChanged(intValue);
                        break;
                    }
                    break;
            }
        }
    };
    private android.telecom.StreamingCallAdapter mStreamingCallAdapter;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamingFailedReason {
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        android.telecom.Log.i(this, "onBind", new java.lang.Object[0]);
        return new android.telecom.CallStreamingService.CallStreamingServiceBinder();
    }

    private final class CallStreamingServiceBinder extends com.android.internal.telecom.ICallStreamingService.Stub {
        private CallStreamingServiceBinder() {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void setStreamingCallAdapter(com.android.internal.telecom.IStreamingCallAdapter iStreamingCallAdapter) throws android.os.RemoteException {
            android.telecom.Log.i(this, "setCallStreamingAdapter", new java.lang.Object[0]);
            android.telecom.CallStreamingService.this.mHandler.obtainMessage(1, iStreamingCallAdapter).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStarted(android.telecom.StreamingCall streamingCall) throws android.os.RemoteException {
            android.telecom.Log.i(this, "onCallStreamingStarted", new java.lang.Object[0]);
            android.telecom.CallStreamingService.this.mHandler.obtainMessage(2, streamingCall).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStopped() throws android.os.RemoteException {
            android.telecom.CallStreamingService.this.mHandler.obtainMessage(3).sendToTarget();
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStateChanged(int i) throws android.os.RemoteException {
            android.telecom.CallStreamingService.this.mHandler.obtainMessage(4, java.lang.Integer.valueOf(i)).sendToTarget();
        }
    }

    public void onCallStreamingStarted(android.telecom.StreamingCall streamingCall) {
    }

    public void onCallStreamingStopped() {
    }

    public void onCallStreamingStateChanged(int i) {
    }
}
