package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface CapabilityExchangeEventListener {

    public interface OptionsRequestCallback {
        void onRespondToCapabilityRequest(android.telephony.ims.RcsContactUceCapability rcsContactUceCapability, boolean z);

        void onRespondToCapabilityRequestWithError(int i, java.lang.String str);
    }

    void onRemoteCapabilityRequest(android.net.Uri uri, java.util.Set<java.lang.String> set, android.telephony.ims.stub.CapabilityExchangeEventListener.OptionsRequestCallback optionsRequestCallback) throws android.telephony.ims.ImsException;

    void onRequestPublishCapabilities(int i) throws android.telephony.ims.ImsException;

    void onUnpublish() throws android.telephony.ims.ImsException;

    @java.lang.Deprecated
    default void onPublishUpdated(int i, java.lang.String str, int i2, java.lang.String str2) throws android.telephony.ims.ImsException {
        onPublishUpdated(new android.telephony.ims.SipDetails.Builder(2).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
    }

    default void onPublishUpdated(android.telephony.ims.SipDetails sipDetails) throws android.telephony.ims.ImsException {
    }
}
