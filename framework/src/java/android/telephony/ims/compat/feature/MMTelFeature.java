package android.telephony.ims.compat.feature;

/* loaded from: classes3.dex */
public class MMTelFeature extends android.telephony.ims.compat.feature.ImsFeature {
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.ims.internal.IImsMMTelFeature mImsMMTelBinder = new com.android.ims.internal.IImsMMTelFeature.Stub() { // from class: android.telephony.ims.compat.feature.MMTelFeature.1
        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startSession(android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
            int startSession;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                startSession = android.telephony.ims.compat.feature.MMTelFeature.this.startSession(pendingIntent, iImsRegistrationListener);
            }
            return startSession;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void endSession(int i) throws android.os.RemoteException {
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.compat.feature.MMTelFeature.this.endSession(i);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isConnected(int i, int i2) throws android.os.RemoteException {
            boolean isConnected;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                isConnected = android.telephony.ims.compat.feature.MMTelFeature.this.isConnected(i, i2);
            }
            return isConnected;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isOpened() throws android.os.RemoteException {
            boolean isOpened;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                isOpened = android.telephony.ims.compat.feature.MMTelFeature.this.isOpened();
            }
            return isOpened;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int getFeatureStatus() throws android.os.RemoteException {
            int featureState;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                featureState = android.telephony.ims.compat.feature.MMTelFeature.this.getFeatureState();
            }
            return featureState;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void addRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.compat.feature.MMTelFeature.this.addRegistrationListener(iImsRegistrationListener);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void removeRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) throws android.os.RemoteException {
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.compat.feature.MMTelFeature.this.removeRegistrationListener(iImsRegistrationListener);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) throws android.os.RemoteException {
            android.telephony.ims.ImsCallProfile createCallProfile;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                createCallProfile = android.telephony.ims.compat.feature.MMTelFeature.this.createCallProfile(i, i2, i3);
            }
            return createCallProfile;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            com.android.ims.internal.IImsCallSession createCallSession;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                createCallSession = android.telephony.ims.compat.feature.MMTelFeature.this.createCallSession(i, imsCallProfile, null);
            }
            return createCallSession;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) throws android.os.RemoteException {
            com.android.ims.internal.IImsCallSession pendingCallSession;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                pendingCallSession = android.telephony.ims.compat.feature.MMTelFeature.this.getPendingCallSession(i, str);
            }
            return pendingCallSession;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
            com.android.ims.internal.IImsUt iImsUt;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.stub.ImsUtImplBase utInterface = android.telephony.ims.compat.feature.MMTelFeature.this.getUtInterface();
                iImsUt = utInterface != null ? utInterface.getInterface() : null;
            }
            return iImsUt;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsConfig getConfigInterface() throws android.os.RemoteException {
            com.android.ims.internal.IImsConfig configInterface;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                configInterface = android.telephony.ims.compat.feature.MMTelFeature.this.getConfigInterface();
            }
            return configInterface;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOnIms() throws android.os.RemoteException {
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.compat.feature.MMTelFeature.this.turnOnIms();
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOffIms() throws android.os.RemoteException {
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.compat.feature.MMTelFeature.this.turnOffIms();
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
            com.android.ims.internal.IImsEcbm imsEcbm;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.stub.ImsEcbmImplBase ecbmInterface = android.telephony.ims.compat.feature.MMTelFeature.this.getEcbmInterface();
                imsEcbm = ecbmInterface != null ? ecbmInterface.getImsEcbm() : null;
            }
            return imsEcbm;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setUiTTYMode(int i, android.os.Message message) throws android.os.RemoteException {
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.compat.feature.MMTelFeature.this.setUiTTYMode(i, message);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
            com.android.ims.internal.IImsMultiEndpoint iImsMultiEndpoint;
            synchronized (android.telephony.ims.compat.feature.MMTelFeature.this.mLock) {
                android.telephony.ims.stub.ImsMultiEndpointImplBase multiEndpointInterface = android.telephony.ims.compat.feature.MMTelFeature.this.getMultiEndpointInterface();
                iImsMultiEndpoint = multiEndpointInterface != null ? multiEndpointInterface.getIImsMultiEndpoint() : null;
            }
            return iImsMultiEndpoint;
        }
    };

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public final com.android.ims.internal.IImsMMTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }

    public int startSession(android.app.PendingIntent pendingIntent, com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) {
        return 0;
    }

    public void endSession(int i) {
    }

    public boolean isConnected(int i, int i2) {
        return false;
    }

    public boolean isOpened() {
        return false;
    }

    public void addRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) {
    }

    public void removeRegistrationListener(com.android.ims.internal.IImsRegistrationListener iImsRegistrationListener) {
    }

    public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2, int i3) {
        return null;
    }

    public com.android.ims.internal.IImsCallSession createCallSession(int i, android.telephony.ims.ImsCallProfile imsCallProfile, com.android.ims.internal.IImsCallSessionListener iImsCallSessionListener) {
        return null;
    }

    public com.android.ims.internal.IImsCallSession getPendingCallSession(int i, java.lang.String str) {
        return null;
    }

    public android.telephony.ims.stub.ImsUtImplBase getUtInterface() {
        return null;
    }

    public com.android.ims.internal.IImsConfig getConfigInterface() {
        return null;
    }

    public void turnOnIms() {
    }

    public void turnOffIms() {
    }

    public android.telephony.ims.stub.ImsEcbmImplBase getEcbmInterface() {
        return null;
    }

    public void setUiTTYMode(int i, android.os.Message message) {
    }

    public android.telephony.ims.stub.ImsMultiEndpointImplBase getMultiEndpointInterface() {
        return null;
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureReady() {
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureRemoved() {
    }
}
