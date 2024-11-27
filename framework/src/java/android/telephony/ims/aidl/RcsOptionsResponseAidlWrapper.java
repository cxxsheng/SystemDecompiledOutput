package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public class RcsOptionsResponseAidlWrapper implements android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback {
    private final android.telephony.ims.aidl.IOptionsResponseCallback mResponseBinder;

    public RcsOptionsResponseAidlWrapper(android.telephony.ims.aidl.IOptionsResponseCallback iOptionsResponseCallback) {
        this.mResponseBinder = iOptionsResponseCallback;
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback
    public void onCommandError(int i) {
        try {
            this.mResponseBinder.onCommandError(i);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.telephony.ims.stub.RcsCapabilityExchangeImplBase.OptionsResponseCallback
    public void onNetworkResponse(int i, java.lang.String str, java.util.List<java.lang.String> list) throws android.telephony.ims.ImsException {
        try {
            this.mResponseBinder.onNetworkResponse(i, str, list);
        } catch (android.os.RemoteException e) {
        }
    }
}
