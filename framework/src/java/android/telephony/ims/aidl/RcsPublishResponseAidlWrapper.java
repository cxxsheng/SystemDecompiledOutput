package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public class RcsPublishResponseAidlWrapper implements android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback {
    private final android.telephony.ims.aidl.IPublishResponseCallback mResponseBinder;

    public RcsPublishResponseAidlWrapper(android.telephony.ims.aidl.IPublishResponseCallback iPublishResponseCallback) {
        this.mResponseBinder = iPublishResponseCallback;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    public void onCommandError(int i) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onCommandError(i);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    @java.lang.Deprecated
    public void onNetworkResponse(int i, java.lang.String str) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new android.telephony.ims.SipDetails.Builder(2).setSipResponseCode(i, str).build());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    @java.lang.Deprecated
    public void onNetworkResponse(int i, java.lang.String str, int i2, java.lang.String str2) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new android.telephony.ims.SipDetails.Builder(2).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.PublishResponseCallback
    public void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(sipDetails);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }
}
