package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class RcsCapabilityExchangeImplBase {
    public static final int COMMAND_CODE_FETCH_ERROR = 3;
    public static final int COMMAND_CODE_GENERIC_FAILURE = 1;
    public static final int COMMAND_CODE_INSUFFICIENT_MEMORY = 5;
    public static final int COMMAND_CODE_INVALID_PARAM = 2;
    public static final int COMMAND_CODE_LOST_NETWORK_CONNECTION = 6;
    public static final int COMMAND_CODE_NOT_FOUND = 8;
    public static final int COMMAND_CODE_NOT_SUPPORTED = 7;
    public static final int COMMAND_CODE_NO_CHANGE = 10;
    public static final int COMMAND_CODE_REQUEST_TIMEOUT = 4;
    public static final int COMMAND_CODE_SERVICE_UNAVAILABLE = 9;
    public static final int COMMAND_CODE_SERVICE_UNKNOWN = 0;
    private static final java.lang.String LOG_TAG = "RcsCapExchangeImplBase";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CommandCode {
    }

    public interface OptionsResponseCallback {
        void onCommandError(int i) throws android.telephony.ims.ImsException;

        void onNetworkResponse(int i, java.lang.String str, java.util.List<java.lang.String> list) throws android.telephony.ims.ImsException;
    }

    public interface PublishResponseCallback {
        void onCommandError(int i) throws android.telephony.ims.ImsException;

        @java.lang.Deprecated
        void onNetworkResponse(int i, java.lang.String str) throws android.telephony.ims.ImsException;

        @java.lang.Deprecated
        void onNetworkResponse(int i, java.lang.String str, int i2, java.lang.String str2) throws android.telephony.ims.ImsException;

        default void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.telephony.ims.ImsException {
            if (android.text.TextUtils.isEmpty(sipDetails.getReasonHeaderText())) {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase());
            } else {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase(), sipDetails.getReasonHeaderCause(), sipDetails.getReasonHeaderText());
            }
        }
    }

    public interface SubscribeResponseCallback {
        void onCommandError(int i) throws android.telephony.ims.ImsException;

        @java.lang.Deprecated
        void onNetworkResponse(int i, java.lang.String str) throws android.telephony.ims.ImsException;

        @java.lang.Deprecated
        void onNetworkResponse(int i, java.lang.String str, int i2, java.lang.String str2) throws android.telephony.ims.ImsException;

        void onNotifyCapabilitiesUpdate(java.util.List<java.lang.String> list) throws android.telephony.ims.ImsException;

        void onResourceTerminated(java.util.List<android.util.Pair<android.net.Uri, java.lang.String>> list) throws android.telephony.ims.ImsException;

        void onTerminated(java.lang.String str, long j) throws android.telephony.ims.ImsException;

        default void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.telephony.ims.ImsException {
            if (android.text.TextUtils.isEmpty(sipDetails.getReasonHeaderText())) {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase());
            } else {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase(), sipDetails.getReasonHeaderCause(), sipDetails.getReasonHeaderText());
            }
        }
    }

    public void subscribeForCapabilities(java.util.Collection<android.net.Uri> collection, android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback subscribeResponseCallback) {
        android.util.Log.w(LOG_TAG, "subscribeForCapabilities called with no implementation.");
        try {
            subscribeResponseCallback.onCommandError(7);
        } catch (android.telephony.ims.ImsException e) {
        }
    }

    public void publishCapabilities(java.lang.String str, android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback publishResponseCallback) {
        android.util.Log.w(LOG_TAG, "publishCapabilities called with no implementation.");
        try {
            publishResponseCallback.onCommandError(7);
        } catch (android.telephony.ims.ImsException e) {
        }
    }

    public void sendOptionsCapabilityRequest(android.net.Uri uri, java.util.Set<java.lang.String> set, android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback optionsResponseCallback) {
        android.util.Log.w(LOG_TAG, "sendOptionsCapabilityRequest called with no implementation.");
        try {
            optionsResponseCallback.onCommandError(7);
        } catch (android.telephony.ims.ImsException e) {
        }
    }
}
