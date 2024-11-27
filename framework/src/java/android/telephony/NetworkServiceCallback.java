package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class NetworkServiceCallback {
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_FAILED = 5;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final java.lang.String mTag = android.telephony.NetworkServiceCallback.class.getSimpleName();
    private final android.telephony.INetworkServiceCallback mCallback;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Result {
    }

    public NetworkServiceCallback(android.telephony.INetworkServiceCallback iNetworkServiceCallback) {
        this.mCallback = iNetworkServiceCallback;
    }

    public void onRequestNetworkRegistrationInfoComplete(int i, android.telephony.NetworkRegistrationInfo networkRegistrationInfo) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onRequestNetworkRegistrationInfoComplete(i, networkRegistrationInfo);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(mTag, "Failed to onRequestNetworkRegistrationInfoComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(mTag, "onRequestNetworkRegistrationInfoComplete callback is null.");
    }
}
