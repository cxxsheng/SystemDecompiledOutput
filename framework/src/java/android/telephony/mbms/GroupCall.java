package android.telephony.mbms;

/* loaded from: classes3.dex */
public class GroupCall implements java.lang.AutoCloseable {
    private static final java.lang.String LOG_TAG = "MbmsGroupCall";
    public static final int REASON_BY_USER_REQUEST = 1;
    public static final int REASON_FREQUENCY_CONFLICT = 3;
    public static final int REASON_LEFT_MBMS_BROADCAST_AREA = 6;
    public static final int REASON_NONE = 0;
    public static final int REASON_NOT_CONNECTED_TO_HOMECARRIER_LTE = 5;
    public static final int REASON_OUT_OF_MEMORY = 4;
    public static final int STATE_STALLED = 3;
    public static final int STATE_STARTED = 2;
    public static final int STATE_STOPPED = 1;
    private final android.telephony.mbms.InternalGroupCallCallback mCallback;
    private final android.telephony.MbmsGroupCallSession mParentSession;
    private android.telephony.mbms.vendor.IMbmsGroupCallService mService;
    private final int mSubscriptionId;
    private final long mTmgi;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GroupCallState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GroupCallStateChangeReason {
    }

    public GroupCall(int i, android.telephony.mbms.vendor.IMbmsGroupCallService iMbmsGroupCallService, android.telephony.MbmsGroupCallSession mbmsGroupCallSession, long j, android.telephony.mbms.InternalGroupCallCallback internalGroupCallCallback) {
        this.mSubscriptionId = i;
        this.mParentSession = mbmsGroupCallSession;
        this.mService = iMbmsGroupCallService;
        this.mTmgi = j;
        this.mCallback = internalGroupCallCallback;
    }

    public long getTmgi() {
        return this.mTmgi;
    }

    public void updateGroupCall(java.util.List<java.lang.Integer> list, java.util.List<java.lang.Integer> list2) {
        try {
            if (this.mService == null) {
                throw new java.lang.IllegalStateException("No group call service attached");
            }
            try {
                this.mService.updateGroupCall(this.mSubscriptionId, this.mTmgi, list, list2);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Remote process died");
                this.mService = null;
                sendErrorToApp(3, null);
            }
        } finally {
            this.mParentSession.onGroupCallStopped(this);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            if (this.mService == null) {
                throw new java.lang.IllegalStateException("No group call service attached");
            }
            try {
                this.mService.stopGroupCall(this.mSubscriptionId, this.mTmgi);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Remote process died");
                this.mService = null;
                sendErrorToApp(3, null);
            }
        } finally {
            this.mParentSession.onGroupCallStopped(this);
        }
    }

    public android.telephony.mbms.InternalGroupCallCallback getCallback() {
        return this.mCallback;
    }

    private void sendErrorToApp(int i, java.lang.String str) {
        this.mCallback.onError(i, str);
    }
}
