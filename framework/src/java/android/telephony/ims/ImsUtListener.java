package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsUtListener {

    @java.lang.Deprecated
    public static final java.lang.String BUNDLE_KEY_CLIR = "queryClir";

    @java.lang.Deprecated
    public static final java.lang.String BUNDLE_KEY_SSINFO = "imsSsInfo";
    private static final java.lang.String LOG_TAG = "ImsUtListener";
    private com.android.ims.internal.IImsUtListener mServiceInterface;

    public void onUtConfigurationUpdated(int i) {
        try {
            this.mServiceInterface.utConfigurationUpdated(null, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationUpdated: remote exception");
        }
    }

    public void onUtConfigurationUpdateFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mServiceInterface.utConfigurationUpdateFailed(null, i, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationUpdateFailed: remote exception");
        }
    }

    @java.lang.Deprecated
    public void onUtConfigurationQueried(int i, android.os.Bundle bundle) {
        try {
            this.mServiceInterface.utConfigurationQueried(null, i, bundle);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationQueried: remote exception");
        }
    }

    public void onLineIdentificationSupplementaryServiceResponse(int i, android.telephony.ims.ImsSsInfo imsSsInfo) {
        try {
            this.mServiceInterface.lineIdentificationSupplementaryServiceResponse(i, imsSsInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void onUtConfigurationQueryFailed(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mServiceInterface.utConfigurationQueryFailed(null, i, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationQueryFailed: remote exception");
        }
    }

    public void onUtConfigurationCallBarringQueried(int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) {
        try {
            this.mServiceInterface.utConfigurationCallBarringQueried(null, i, imsSsInfoArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationCallBarringQueried: remote exception");
        }
    }

    public void onUtConfigurationCallForwardQueried(int i, android.telephony.ims.ImsCallForwardInfo[] imsCallForwardInfoArr) {
        try {
            this.mServiceInterface.utConfigurationCallForwardQueried(null, i, imsCallForwardInfoArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationCallForwardQueried: remote exception");
        }
    }

    public void onUtConfigurationCallWaitingQueried(int i, android.telephony.ims.ImsSsInfo[] imsSsInfoArr) {
        try {
            this.mServiceInterface.utConfigurationCallWaitingQueried(null, i, imsSsInfoArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "utConfigurationCallWaitingQueried: remote exception");
        }
    }

    public void onSupplementaryServiceIndication(android.telephony.ims.ImsSsData imsSsData) {
        try {
            this.mServiceInterface.onSupplementaryServiceIndication(imsSsData);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "onSupplementaryServiceIndication: remote exception");
        }
    }

    public ImsUtListener(com.android.ims.internal.IImsUtListener iImsUtListener) {
        this.mServiceInterface = iImsUtListener;
    }

    public com.android.ims.internal.IImsUtListener getListenerInterface() {
        return this.mServiceInterface;
    }
}
