package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public class RcsSubscribeResponseAidlWrapper implements android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback {
    private final android.telephony.ims.aidl.ISubscribeResponseCallback mResponseBinder;

    public RcsSubscribeResponseAidlWrapper(android.telephony.ims.aidl.ISubscribeResponseCallback iSubscribeResponseCallback) {
        this.mResponseBinder = iSubscribeResponseCallback;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onCommandError(int i) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onCommandError(i);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    @java.lang.Deprecated
    public void onNetworkResponse(int i, java.lang.String str) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new android.telephony.ims.SipDetails.Builder(3).setSipResponseCode(i, str).build());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    @java.lang.Deprecated
    public void onNetworkResponse(int i, java.lang.String str, int i2, java.lang.String str2) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(new android.telephony.ims.SipDetails.Builder(3).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onNetworkResponse(android.telephony.ims.SipDetails sipDetails) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(sipDetails);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onNotifyCapabilitiesUpdate(java.util.List<java.lang.String> list) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNotifyCapabilitiesUpdate(list);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onResourceTerminated(java.util.List<android.util.Pair<android.net.Uri, java.lang.String>> list) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onResourceTerminated(getTerminatedReasonList(list));
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }

    private java.util.List<android.telephony.ims.RcsContactTerminatedReason> getTerminatedReasonList(java.util.List<android.util.Pair<android.net.Uri, java.lang.String>> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (list != null) {
            for (android.util.Pair<android.net.Uri, java.lang.String> pair : list) {
                arrayList.add(new android.telephony.ims.RcsContactTerminatedReason(pair.first, pair.second));
            }
        }
        return arrayList;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.SubscribeResponseCallback
    public void onTerminated(java.lang.String str, long j) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onTerminated(str, j);
        } catch (android.os.RemoteException e) {
            throw new android.telephony.ims.ImsException(e.getMessage(), 1);
        }
    }
}
