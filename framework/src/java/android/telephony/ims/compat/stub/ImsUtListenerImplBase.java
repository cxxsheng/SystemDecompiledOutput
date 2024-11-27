package android.telephony.ims.compat.stub;

/* loaded from: classes3.dex */
public class ImsUtListenerImplBase extends com.android.ims.internal.IImsUtListener.Stub {
    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationUpdated(com.android.ims.internal.IImsUt iImsUt, int i) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationUpdateFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.os.Bundle bundle) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationQueryFailed(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void lineIdentificationSupplementaryServiceResponse(int i, android.telephony.ims.ImsSsInfo imsSsInfo) {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationCallBarringQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationCallForwardQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void utConfigurationCallWaitingQueried(com.android.ims.internal.IImsUt iImsUt, int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) throws android.os.RemoteException {
    }

    @Override // com.android.ims.internal.IImsUtListener
    public void onSupplementaryServiceIndication(android.telephony.ims.ImsSsData imsSsData) {
    }
}
