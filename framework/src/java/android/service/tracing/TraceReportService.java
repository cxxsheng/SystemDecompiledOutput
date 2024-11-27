package android.service.tracing;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
/* loaded from: classes3.dex */
public class TraceReportService extends android.app.Service {
    public static final int MSG_REPORT_TRACE = 1;
    private static final java.lang.String TAG = "TraceReportService";
    private android.os.Messenger mMessenger = null;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final class TraceParams {
        private final android.os.ParcelFileDescriptor mFd;
        private final java.util.UUID mUuid;

        private TraceParams(android.tracing.TraceReportParams traceReportParams) {
            this.mFd = traceReportParams.fd;
            this.mUuid = new java.util.UUID(traceReportParams.uuidMsb, traceReportParams.uuidLsb);
        }

        public android.os.ParcelFileDescriptor getFd() {
            return this.mFd;
        }

        public java.util.UUID getUuid() {
            return this.mUuid;
        }
    }

    public void onReportTrace(android.service.tracing.TraceReportService.TraceParams traceParams) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onMessage(android.os.Message message) {
        if (message.what != 1) {
            return false;
        }
        if (!(message.obj instanceof android.tracing.TraceReportParams)) {
            android.util.Log.e(TAG, "Received invalid type for report trace message.");
            return false;
        }
        android.service.tracing.TraceReportService.TraceParams traceParams = new android.service.tracing.TraceReportService.TraceParams((android.tracing.TraceReportParams) message.obj);
        try {
            onReportTrace(traceParams);
            return true;
        } finally {
            try {
                traceParams.getFd().close();
            } catch (java.io.IOException e) {
            }
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mMessenger == null) {
            this.mMessenger = new android.os.Messenger(new android.os.Handler(android.os.Looper.getMainLooper(), new android.os.Handler.Callback() { // from class: android.service.tracing.TraceReportService$$ExternalSyntheticLambda0
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(android.os.Message message) {
                    boolean onMessage;
                    onMessage = android.service.tracing.TraceReportService.this.onMessage(message);
                    return onMessage;
                }
            }));
        }
        return this.mMessenger.getBinder();
    }
}
