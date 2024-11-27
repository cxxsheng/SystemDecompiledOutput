package android.telephony.mbms;

/* loaded from: classes3.dex */
public class StreamingService implements java.lang.AutoCloseable {
    public static final int BROADCAST_METHOD = 1;
    private static final java.lang.String LOG_TAG = "MbmsStreamingService";
    public static final int REASON_BY_USER_REQUEST = 1;
    public static final int REASON_END_OF_SESSION = 2;
    public static final int REASON_FREQUENCY_CONFLICT = 3;
    public static final int REASON_LEFT_MBMS_BROADCAST_AREA = 6;
    public static final int REASON_NONE = 0;
    public static final int REASON_NOT_CONNECTED_TO_HOMECARRIER_LTE = 5;
    public static final int REASON_OUT_OF_MEMORY = 4;
    public static final int STATE_STALLED = 3;
    public static final int STATE_STARTED = 2;
    public static final int STATE_STOPPED = 1;
    public static final int UNICAST_METHOD = 2;
    private final android.telephony.mbms.InternalStreamingServiceCallback mCallback;
    private final android.telephony.MbmsStreamingSession mParentSession;
    private android.telephony.mbms.vendor.IMbmsStreamingService mService;
    private final android.telephony.mbms.StreamingServiceInfo mServiceInfo;
    private final int mSubscriptionId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamingState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamingStateChangeReason {
    }

    public StreamingService(int i, android.telephony.mbms.vendor.IMbmsStreamingService iMbmsStreamingService, android.telephony.MbmsStreamingSession mbmsStreamingSession, android.telephony.mbms.StreamingServiceInfo streamingServiceInfo, android.telephony.mbms.InternalStreamingServiceCallback internalStreamingServiceCallback) {
        this.mSubscriptionId = i;
        this.mParentSession = mbmsStreamingSession;
        this.mService = iMbmsStreamingService;
        this.mServiceInfo = streamingServiceInfo;
        this.mCallback = internalStreamingServiceCallback;
    }

    public android.net.Uri getPlaybackUri() {
        if (this.mService == null) {
            throw new java.lang.IllegalStateException("No streaming service attached");
        }
        try {
            return this.mService.getPlaybackUri(this.mSubscriptionId, this.mServiceInfo.getServiceId());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote process died");
            this.mService = null;
            this.mParentSession.onStreamingServiceStopped(this);
            sendErrorToApp(3, null);
            return null;
        }
    }

    public android.telephony.mbms.StreamingServiceInfo getInfo() {
        return this.mServiceInfo;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            if (this.mService == null) {
                throw new java.lang.IllegalStateException("No streaming service attached");
            }
            try {
                this.mService.stopStreaming(this.mSubscriptionId, this.mServiceInfo.getServiceId());
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Remote process died");
                this.mService = null;
                sendErrorToApp(3, null);
            }
        } finally {
            this.mParentSession.onStreamingServiceStopped(this);
        }
    }

    public android.telephony.mbms.InternalStreamingServiceCallback getCallback() {
        return this.mCallback;
    }

    private void sendErrorToApp(int i, java.lang.String str) {
        try {
            this.mCallback.onError(i, str);
        } catch (android.os.RemoteException e) {
        }
    }
}
