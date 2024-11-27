package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public class CapabilityExchangeAidlWrapper implements android.telephony.ims.stub.CapabilityExchangeEventListener {
    private static final java.lang.String LOG_TAG = "CapExchangeListener";
    private final android.telephony.ims.aidl.ICapabilityExchangeEventListener mListenerBinder;

    public CapabilityExchangeAidlWrapper(android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener) {
        this.mListenerBinder = iCapabilityExchangeEventListener;
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onRequestPublishCapabilities(int i) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onRequestPublishCapabilities(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "request publish capabilities exception: " + e);
            throw new android.telephony.ims.ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onUnpublish() throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onUnpublish();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Unpublish exception: " + e);
            throw new android.telephony.ims.ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    @java.lang.Deprecated
    public void onPublishUpdated(int i, java.lang.String str, int i2, java.lang.String str2) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onPublishUpdated(new android.telephony.ims.SipDetails.Builder(2).setSipResponseCode(i, str).setSipResponseReasonHeader(i2, str2).build());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "onPublishUpdated exception: " + e);
            throw new android.telephony.ims.ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onPublishUpdated(android.telephony.ims.SipDetails sipDetails) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onPublishUpdated(sipDetails);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "onPublishUpdated exception: " + e);
            throw new android.telephony.ims.ImsException("Remote is not available", 1);
        }
    }

    @Override // android.telephony.ims.stub.CapabilityExchangeEventListener
    public void onRemoteCapabilityRequest(android.net.Uri uri, java.util.Set<java.lang.String> set, final android.telephony.ims.stub.CapabilityExchangeEventListener.OptionsRequestCallback optionsRequestCallback) throws android.telephony.ims.ImsException {
        android.telephony.ims.aidl.ICapabilityExchangeEventListener iCapabilityExchangeEventListener = this.mListenerBinder;
        if (iCapabilityExchangeEventListener == null) {
            return;
        }
        try {
            iCapabilityExchangeEventListener.onRemoteCapabilityRequest(uri, new java.util.ArrayList(set), new android.telephony.ims.aidl.IOptionsRequestCallback.Stub() { // from class: android.telephony.ims.aidl.CapabilityExchangeAidlWrapper.1
                @Override // android.telephony.ims.aidl.IOptionsRequestCallback
                public void respondToCapabilityRequest(android.telephony.ims.RcsContactUceCapability rcsContactUceCapability, boolean z) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        optionsRequestCallback.onRespondToCapabilityRequest(rcsContactUceCapability, z);
                    } finally {
                        restoreCallingIdentity(clearCallingIdentity);
                    }
                }

                @Override // android.telephony.ims.aidl.IOptionsRequestCallback
                public void respondToCapabilityRequestWithError(int i, java.lang.String str) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        optionsRequestCallback.onRespondToCapabilityRequestWithError(i, str);
                    } finally {
                        restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            });
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote capability request exception: " + e);
            throw new android.telephony.ims.ImsException("Remote is not available", 1);
        }
    }
}
