package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsMmTelFeature extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelFeature";

    void acknowledgeSms(int i, int i2, int i3) throws android.os.RemoteException;

    void acknowledgeSmsReport(int i, int i2, int i3) throws android.os.RemoteException;

    void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws android.os.RemoteException;

    void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void changeCapabilitiesConfiguration(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void changeOfferedRtpHeaderExtensionTypes(java.util.List<android.telephony.ims.RtpHeaderExtensionType> list) throws android.os.RemoteException;

    android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2) throws android.os.RemoteException;

    com.android.ims.internal.IImsCallSession createCallSession(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException;

    int getFeatureState() throws android.os.RemoteException;

    com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException;

    java.lang.String getSmsFormat() throws android.os.RemoteException;

    com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException;

    void notifySrvccCanceled() throws android.os.RemoteException;

    void notifySrvccCompleted() throws android.os.RemoteException;

    void notifySrvccFailed() throws android.os.RemoteException;

    void notifySrvccStarted(android.telephony.ims.aidl.ISrvccStartedCallback iSrvccStartedCallback) throws android.os.RemoteException;

    void onMemoryAvailable(int i) throws android.os.RemoteException;

    void onSmsReady() throws android.os.RemoteException;

    void queryCapabilityConfiguration(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    int queryCapabilityStatus() throws android.os.RemoteException;

    android.telephony.ims.MediaQualityStatus queryMediaQualityStatus(int i) throws android.os.RemoteException;

    void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void sendSms(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, byte[] bArr) throws android.os.RemoteException;

    void setListener(android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener) throws android.os.RemoteException;

    void setMediaQualityThreshold(int i, android.telephony.ims.MediaThreshold mediaThreshold) throws android.os.RemoteException;

    void setSmsListener(android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) throws android.os.RemoteException;

    void setTerminalBasedCallWaitingStatus(boolean z) throws android.os.RemoteException;

    void setUiTtyMode(int i, android.os.Message message) throws android.os.RemoteException;

    int shouldProcessCall(java.lang.String[] strArr) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsMmTelFeature {
        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setListener(android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int getFeatureState() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeOfferedRtpHeaderExtensionTypes(java.util.List<android.telephony.ims.RtpHeaderExtensionType> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsCallSession createCallSession(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int shouldProcessCall(java.lang.String[] strArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setUiTtyMode(int i, android.os.Message message) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int queryCapabilityStatus() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setTerminalBasedCallWaitingStatus(boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeCapabilitiesConfiguration(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void queryCapabilityConfiguration(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccStarted(android.telephony.ims.aidl.ISrvccStartedCallback iSrvccStartedCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCompleted() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccFailed() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCanceled() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setMediaQualityThreshold(int i, android.telephony.ims.MediaThreshold mediaThreshold) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public android.telephony.ims.MediaQualityStatus queryMediaQualityStatus(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setSmsListener(android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendSms(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onMemoryAvailable(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSms(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsReport(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public java.lang.String getSmsFormat() throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onSmsReady() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsMmTelFeature {
        static final int TRANSACTION_acknowledgeSms = 26;
        static final int TRANSACTION_acknowledgeSmsReport = 28;
        static final int TRANSACTION_acknowledgeSmsWithPdu = 27;
        static final int TRANSACTION_addCapabilityCallback = 13;
        static final int TRANSACTION_changeCapabilitiesConfiguration = 15;
        static final int TRANSACTION_changeOfferedRtpHeaderExtensionTypes = 4;
        static final int TRANSACTION_createCallProfile = 3;
        static final int TRANSACTION_createCallSession = 5;
        static final int TRANSACTION_getEcbmInterface = 8;
        static final int TRANSACTION_getFeatureState = 2;
        static final int TRANSACTION_getMultiEndpointInterface = 10;
        static final int TRANSACTION_getSmsFormat = 29;
        static final int TRANSACTION_getUtInterface = 7;
        static final int TRANSACTION_notifySrvccCanceled = 20;
        static final int TRANSACTION_notifySrvccCompleted = 18;
        static final int TRANSACTION_notifySrvccFailed = 19;
        static final int TRANSACTION_notifySrvccStarted = 17;
        static final int TRANSACTION_onMemoryAvailable = 25;
        static final int TRANSACTION_onSmsReady = 30;
        static final int TRANSACTION_queryCapabilityConfiguration = 16;
        static final int TRANSACTION_queryCapabilityStatus = 11;
        static final int TRANSACTION_queryMediaQualityStatus = 22;
        static final int TRANSACTION_removeCapabilityCallback = 14;
        static final int TRANSACTION_sendSms = 24;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setMediaQualityThreshold = 21;
        static final int TRANSACTION_setSmsListener = 23;
        static final int TRANSACTION_setTerminalBasedCallWaitingStatus = 12;
        static final int TRANSACTION_setUiTtyMode = 9;
        static final int TRANSACTION_shouldProcessCall = 6;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsMmTelFeature asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsMmTelFeature)) {
                return (android.telephony.ims.aidl.IImsMmTelFeature) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsMmTelFeature.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setListener";
                case 2:
                    return "getFeatureState";
                case 3:
                    return "createCallProfile";
                case 4:
                    return "changeOfferedRtpHeaderExtensionTypes";
                case 5:
                    return "createCallSession";
                case 6:
                    return "shouldProcessCall";
                case 7:
                    return "getUtInterface";
                case 8:
                    return "getEcbmInterface";
                case 9:
                    return "setUiTtyMode";
                case 10:
                    return "getMultiEndpointInterface";
                case 11:
                    return "queryCapabilityStatus";
                case 12:
                    return "setTerminalBasedCallWaitingStatus";
                case 13:
                    return "addCapabilityCallback";
                case 14:
                    return "removeCapabilityCallback";
                case 15:
                    return "changeCapabilitiesConfiguration";
                case 16:
                    return "queryCapabilityConfiguration";
                case 17:
                    return "notifySrvccStarted";
                case 18:
                    return "notifySrvccCompleted";
                case 19:
                    return "notifySrvccFailed";
                case 20:
                    return "notifySrvccCanceled";
                case 21:
                    return "setMediaQualityThreshold";
                case 22:
                    return "queryMediaQualityStatus";
                case 23:
                    return "setSmsListener";
                case 24:
                    return "sendSms";
                case 25:
                    return "onMemoryAvailable";
                case 26:
                    return "acknowledgeSms";
                case 27:
                    return "acknowledgeSmsWithPdu";
                case 28:
                    return "acknowledgeSmsReport";
                case 29:
                    return "getSmsFormat";
                case 30:
                    return "onSmsReady";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.aidl.IImsMmTelListener asInterface = android.telephony.ims.aidl.IImsMmTelListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int featureState = getFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureState);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.ImsCallProfile createCallProfile = createCallProfile(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createCallProfile, 1);
                    return true;
                case 4:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.RtpHeaderExtensionType.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeOfferedRtpHeaderExtensionTypes(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsCallSession createCallSession = createCallSession(imsCallProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createCallSession);
                    return true;
                case 6:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int shouldProcessCall = shouldProcessCall(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(shouldProcessCall);
                    return true;
                case 7:
                    com.android.ims.internal.IImsUt utInterface = getUtInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(utInterface);
                    return true;
                case 8:
                    com.android.ims.internal.IImsEcbm ecbmInterface = getEcbmInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ecbmInterface);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    android.os.Message message = (android.os.Message) parcel.readTypedObject(android.os.Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTtyMode(readInt3, message);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    com.android.ims.internal.IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                case 11:
                    int queryCapabilityStatus = queryCapabilityStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCapabilityStatus);
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTerminalBasedCallWaitingStatus(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface2 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCapabilityCallback(asInterface2);
                    return true;
                case 14:
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface3 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCapabilityCallback(asInterface3);
                    return true;
                case 15:
                    android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest = (android.telephony.ims.feature.CapabilityChangeRequest) parcel.readTypedObject(android.telephony.ims.feature.CapabilityChangeRequest.CREATOR);
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface4 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeCapabilitiesConfiguration(capabilityChangeRequest, asInterface4);
                    return true;
                case 16:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface5 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryCapabilityConfiguration(readInt4, readInt5, asInterface5);
                    return true;
                case 17:
                    android.telephony.ims.aidl.ISrvccStartedCallback asInterface6 = android.telephony.ims.aidl.ISrvccStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    notifySrvccStarted(asInterface6);
                    return true;
                case 18:
                    notifySrvccCompleted();
                    return true;
                case 19:
                    notifySrvccFailed();
                    return true;
                case 20:
                    notifySrvccCanceled();
                    return true;
                case 21:
                    int readInt6 = parcel.readInt();
                    android.telephony.ims.MediaThreshold mediaThreshold = (android.telephony.ims.MediaThreshold) parcel.readTypedObject(android.telephony.ims.MediaThreshold.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaQualityThreshold(readInt6, mediaThreshold);
                    return true;
                case 22:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.MediaQualityStatus queryMediaQualityStatus = queryMediaQualityStatus(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryMediaQualityStatus, 1);
                    return true;
                case 23:
                    android.telephony.ims.aidl.IImsSmsListener asInterface7 = android.telephony.ims.aidl.IImsSmsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSmsListener(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSms(readInt8, readInt9, readString, readString2, readBoolean2, createByteArray);
                    return true;
                case 25:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailable(readInt10);
                    return true;
                case 26:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSms(readInt11, readInt12, readInt13);
                    return true;
                case 27:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(readInt14, readInt15, readInt16, createByteArray2);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsReport(readInt17, readInt18, readInt19);
                    return true;
                case 29:
                    java.lang.String smsFormat = getSmsFormat();
                    parcel2.writeNoException();
                    parcel2.writeString(smsFormat);
                    return true;
                case 30:
                    onSmsReady();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsMmTelFeature {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setListener(android.telephony.ims.aidl.IImsMmTelListener iImsMmTelListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsMmTelListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int getFeatureState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public android.telephony.ims.ImsCallProfile createCallProfile(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.ImsCallProfile) obtain2.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeOfferedRtpHeaderExtensionTypes(java.util.List<android.telephony.ims.RtpHeaderExtensionType> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public com.android.ims.internal.IImsCallSession createCallSession(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int shouldProcessCall(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public com.android.ims.internal.IImsUt getUtInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsUt.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public com.android.ims.internal.IImsEcbm getEcbmInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsEcbm.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setUiTtyMode(int i, android.os.Message message) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public com.android.ims.internal.IImsMultiEndpoint getMultiEndpointInterface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsMultiEndpoint.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int queryCapabilityStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setTerminalBasedCallWaitingStatus(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void addCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void removeCapabilityCallback(android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeCapabilitiesConfiguration(android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeTypedObject(capabilityChangeRequest, 0);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void queryCapabilityConfiguration(int i, int i2, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccStarted(android.telephony.ims.aidl.ISrvccStartedCallback iSrvccStartedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iSrvccStartedCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccCanceled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setMediaQualityThreshold(int i, android.telephony.ims.MediaThreshold mediaThreshold) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaThreshold, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public android.telephony.ims.MediaQualityStatus queryMediaQualityStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.MediaQualityStatus) obtain2.readTypedObject(android.telephony.ims.MediaQualityStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setSmsListener(android.telephony.ims.aidl.IImsSmsListener iImsSmsListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsSmsListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendSms(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void onMemoryAvailable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSms(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSmsReport(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public java.lang.String getSmsFormat() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void onSmsReady() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }
    }
}
