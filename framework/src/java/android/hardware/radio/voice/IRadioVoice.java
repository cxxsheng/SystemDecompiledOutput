package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public interface IRadioVoice extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$voice$IRadioVoice".replace('$', '.');
    public static final java.lang.String HASH = "78fb79bcb32590a868b3eb7affb39ab90e4ca782";
    public static final int VERSION = 3;

    void acceptCall(int i) throws android.os.RemoteException;

    void cancelPendingUssd(int i) throws android.os.RemoteException;

    void conference(int i) throws android.os.RemoteException;

    void dial(int i, android.hardware.radio.voice.Dial dial) throws android.os.RemoteException;

    void emergencyDial(int i, android.hardware.radio.voice.Dial dial, int i2, java.lang.String[] strArr, int i3, boolean z, boolean z2) throws android.os.RemoteException;

    void exitEmergencyCallbackMode(int i) throws android.os.RemoteException;

    void explicitCallTransfer(int i) throws android.os.RemoteException;

    void getCallForwardStatus(int i, android.hardware.radio.voice.CallForwardInfo callForwardInfo) throws android.os.RemoteException;

    void getCallWaiting(int i, int i2) throws android.os.RemoteException;

    void getClip(int i) throws android.os.RemoteException;

    void getClir(int i) throws android.os.RemoteException;

    void getCurrentCalls(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getLastCallFailCause(int i) throws android.os.RemoteException;

    void getMute(int i) throws android.os.RemoteException;

    void getPreferredVoicePrivacy(int i) throws android.os.RemoteException;

    void getTtyMode(int i) throws android.os.RemoteException;

    void handleStkCallSetupRequestFromSim(int i, boolean z) throws android.os.RemoteException;

    void hangup(int i, int i2) throws android.os.RemoteException;

    void hangupForegroundResumeBackground(int i) throws android.os.RemoteException;

    void hangupWaitingOrBackground(int i) throws android.os.RemoteException;

    void isVoNrEnabled(int i) throws android.os.RemoteException;

    void rejectCall(int i) throws android.os.RemoteException;

    void responseAcknowledgement() throws android.os.RemoteException;

    void sendBurstDtmf(int i, java.lang.String str, int i2, int i3) throws android.os.RemoteException;

    void sendCdmaFeatureCode(int i, java.lang.String str) throws android.os.RemoteException;

    void sendDtmf(int i, java.lang.String str) throws android.os.RemoteException;

    void sendUssd(int i, java.lang.String str) throws android.os.RemoteException;

    void separateConnection(int i, int i2) throws android.os.RemoteException;

    void setCallForward(int i, android.hardware.radio.voice.CallForwardInfo callForwardInfo) throws android.os.RemoteException;

    void setCallWaiting(int i, boolean z, int i2) throws android.os.RemoteException;

    void setClir(int i, int i2) throws android.os.RemoteException;

    void setMute(int i, boolean z) throws android.os.RemoteException;

    void setPreferredVoicePrivacy(int i, boolean z) throws android.os.RemoteException;

    void setResponseFunctions(android.hardware.radio.voice.IRadioVoiceResponse iRadioVoiceResponse, android.hardware.radio.voice.IRadioVoiceIndication iRadioVoiceIndication) throws android.os.RemoteException;

    void setTtyMode(int i, int i2) throws android.os.RemoteException;

    void setVoNrEnabled(int i, boolean z) throws android.os.RemoteException;

    void startDtmf(int i, java.lang.String str) throws android.os.RemoteException;

    void stopDtmf(int i) throws android.os.RemoteException;

    void switchWaitingOrHoldingAndActive(int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.voice.IRadioVoice {
        @Override // android.hardware.radio.voice.IRadioVoice
        public void acceptCall(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void cancelPendingUssd(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void conference(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void dial(int i, android.hardware.radio.voice.Dial dial) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void emergencyDial(int i, android.hardware.radio.voice.Dial dial, int i2, java.lang.String[] strArr, int i3, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void exitEmergencyCallbackMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void explicitCallTransfer(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getCallForwardStatus(int i, android.hardware.radio.voice.CallForwardInfo callForwardInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getCallWaiting(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getClip(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getClir(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getCurrentCalls(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getLastCallFailCause(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getMute(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getPreferredVoicePrivacy(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getTtyMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void handleStkCallSetupRequestFromSim(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void hangup(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void hangupForegroundResumeBackground(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void hangupWaitingOrBackground(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void isVoNrEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void rejectCall(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void responseAcknowledgement() throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendBurstDtmf(int i, java.lang.String str, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendCdmaFeatureCode(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendDtmf(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendUssd(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void separateConnection(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setCallForward(int i, android.hardware.radio.voice.CallForwardInfo callForwardInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setCallWaiting(int i, boolean z, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setClir(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setMute(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setPreferredVoicePrivacy(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setResponseFunctions(android.hardware.radio.voice.IRadioVoiceResponse iRadioVoiceResponse, android.hardware.radio.voice.IRadioVoiceIndication iRadioVoiceIndication) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setTtyMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setVoNrEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void startDtmf(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void stopDtmf(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void switchWaitingOrHoldingAndActive(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.voice.IRadioVoice {
        static final int TRANSACTION_acceptCall = 1;
        static final int TRANSACTION_cancelPendingUssd = 2;
        static final int TRANSACTION_conference = 3;
        static final int TRANSACTION_dial = 4;
        static final int TRANSACTION_emergencyDial = 5;
        static final int TRANSACTION_exitEmergencyCallbackMode = 6;
        static final int TRANSACTION_explicitCallTransfer = 7;
        static final int TRANSACTION_getCallForwardStatus = 8;
        static final int TRANSACTION_getCallWaiting = 9;
        static final int TRANSACTION_getClip = 10;
        static final int TRANSACTION_getClir = 11;
        static final int TRANSACTION_getCurrentCalls = 12;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLastCallFailCause = 13;
        static final int TRANSACTION_getMute = 14;
        static final int TRANSACTION_getPreferredVoicePrivacy = 15;
        static final int TRANSACTION_getTtyMode = 16;
        static final int TRANSACTION_handleStkCallSetupRequestFromSim = 17;
        static final int TRANSACTION_hangup = 18;
        static final int TRANSACTION_hangupForegroundResumeBackground = 19;
        static final int TRANSACTION_hangupWaitingOrBackground = 20;
        static final int TRANSACTION_isVoNrEnabled = 21;
        static final int TRANSACTION_rejectCall = 22;
        static final int TRANSACTION_responseAcknowledgement = 23;
        static final int TRANSACTION_sendBurstDtmf = 24;
        static final int TRANSACTION_sendCdmaFeatureCode = 25;
        static final int TRANSACTION_sendDtmf = 26;
        static final int TRANSACTION_sendUssd = 27;
        static final int TRANSACTION_separateConnection = 28;
        static final int TRANSACTION_setCallForward = 29;
        static final int TRANSACTION_setCallWaiting = 30;
        static final int TRANSACTION_setClir = 31;
        static final int TRANSACTION_setMute = 32;
        static final int TRANSACTION_setPreferredVoicePrivacy = 33;
        static final int TRANSACTION_setResponseFunctions = 34;
        static final int TRANSACTION_setTtyMode = 35;
        static final int TRANSACTION_setVoNrEnabled = 36;
        static final int TRANSACTION_startDtmf = 37;
        static final int TRANSACTION_stopDtmf = 38;
        static final int TRANSACTION_switchWaitingOrHoldingAndActive = 39;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.voice.IRadioVoice asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.voice.IRadioVoice)) {
                return (android.hardware.radio.voice.IRadioVoice) queryLocalInterface;
            }
            return new android.hardware.radio.voice.IRadioVoice.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acceptCall(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelPendingUssd(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    conference(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    android.hardware.radio.voice.Dial dial = (android.hardware.radio.voice.Dial) parcel.readTypedObject(android.hardware.radio.voice.Dial.CREATOR);
                    parcel.enforceNoDataAvail();
                    dial(readInt4, dial);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.hardware.radio.voice.Dial dial2 = (android.hardware.radio.voice.Dial) parcel.readTypedObject(android.hardware.radio.voice.Dial.CREATOR);
                    int readInt6 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    emergencyDial(readInt5, dial2, readInt6, createStringArray, readInt7, readBoolean, readBoolean2);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    exitEmergencyCallbackMode(readInt8);
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    explicitCallTransfer(readInt9);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    android.hardware.radio.voice.CallForwardInfo callForwardInfo = (android.hardware.radio.voice.CallForwardInfo) parcel.readTypedObject(android.hardware.radio.voice.CallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCallForwardStatus(readInt10, callForwardInfo);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallWaiting(readInt11, readInt12);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClip(readInt13);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClir(readInt14);
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCurrentCalls(readInt15);
                    return true;
                case 13:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getLastCallFailCause(readInt16);
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getMute(readInt17);
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPreferredVoicePrivacy(readInt18);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTtyMode(readInt19);
                    return true;
                case 17:
                    int readInt20 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    handleStkCallSetupRequestFromSim(readInt20, readBoolean3);
                    return true;
                case 18:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangup(readInt21, readInt22);
                    return true;
                case 19:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangupForegroundResumeBackground(readInt23);
                    return true;
                case 20:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangupWaitingOrBackground(readInt24);
                    return true;
                case 21:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isVoNrEnabled(readInt25);
                    return true;
                case 22:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rejectCall(readInt26);
                    return true;
                case 23:
                    responseAcknowledgement();
                    return true;
                case 24:
                    int readInt27 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendBurstDtmf(readInt27, readString, readInt28, readInt29);
                    return true;
                case 25:
                    int readInt30 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCdmaFeatureCode(readInt30, readString2);
                    return true;
                case 26:
                    int readInt31 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmf(readInt31, readString3);
                    return true;
                case 27:
                    int readInt32 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendUssd(readInt32, readString4);
                    return true;
                case 28:
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    separateConnection(readInt33, readInt34);
                    return true;
                case 29:
                    int readInt35 = parcel.readInt();
                    android.hardware.radio.voice.CallForwardInfo callForwardInfo2 = (android.hardware.radio.voice.CallForwardInfo) parcel.readTypedObject(android.hardware.radio.voice.CallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallForward(readInt35, callForwardInfo2);
                    return true;
                case 30:
                    int readInt36 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCallWaiting(readInt36, readBoolean4, readInt37);
                    return true;
                case 31:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setClir(readInt38, readInt39);
                    return true;
                case 32:
                    int readInt40 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMute(readInt40, readBoolean5);
                    return true;
                case 33:
                    int readInt41 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPreferredVoicePrivacy(readInt41, readBoolean6);
                    return true;
                case 34:
                    android.hardware.radio.voice.IRadioVoiceResponse asInterface = android.hardware.radio.voice.IRadioVoiceResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.radio.voice.IRadioVoiceIndication asInterface2 = android.hardware.radio.voice.IRadioVoiceIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 35:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(readInt42, readInt43);
                    return true;
                case 36:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoNrEnabled(readInt44, readBoolean7);
                    return true;
                case 37:
                    int readInt45 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startDtmf(readInt45, readString5);
                    return true;
                case 38:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopDtmf(readInt46);
                    return true;
                case 39:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchWaitingOrHoldingAndActive(readInt47);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.voice.IRadioVoice {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void acceptCall(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acceptCall is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void cancelPendingUssd(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cancelPendingUssd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void conference(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method conference is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void dial(int i, android.hardware.radio.voice.Dial dial) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dial, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method dial is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void emergencyDial(int i, android.hardware.radio.voice.Dial dial, int i2, java.lang.String[] strArr, int i3, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dial, 0);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method emergencyDial is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void exitEmergencyCallbackMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method exitEmergencyCallbackMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void explicitCallTransfer(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method explicitCallTransfer is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getCallForwardStatus(int i, android.hardware.radio.voice.CallForwardInfo callForwardInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(callForwardInfo, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCallForwardStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getCallWaiting(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCallWaiting is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getClip(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getClip is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getClir(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getClir is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getCurrentCalls(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCurrentCalls is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getLastCallFailCause(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getLastCallFailCause is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getMute(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getMute is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getPreferredVoicePrivacy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getPreferredVoicePrivacy is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getTtyMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getTtyMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void handleStkCallSetupRequestFromSim(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method handleStkCallSetupRequestFromSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void hangup(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hangup is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void hangupForegroundResumeBackground(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hangupForegroundResumeBackground is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void hangupWaitingOrBackground(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hangupWaitingOrBackground is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void isVoNrEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isVoNrEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void rejectCall(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method rejectCall is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void responseAcknowledgement() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendBurstDtmf(int i, java.lang.String str, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (!this.mRemote.transact(24, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendBurstDtmf is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendCdmaFeatureCode(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(25, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendCdmaFeatureCode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendDtmf(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(26, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendDtmf is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendUssd(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(27, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendUssd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void separateConnection(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(28, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method separateConnection is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setCallForward(int i, android.hardware.radio.voice.CallForwardInfo callForwardInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(callForwardInfo, 0);
                    if (!this.mRemote.transact(29, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCallForward is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setCallWaiting(int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(30, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCallWaiting is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setClir(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(31, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setClir is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setMute(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(32, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setMute is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setPreferredVoicePrivacy(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(33, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setPreferredVoicePrivacy is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setResponseFunctions(android.hardware.radio.voice.IRadioVoiceResponse iRadioVoiceResponse, android.hardware.radio.voice.IRadioVoiceIndication iRadioVoiceIndication) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioVoiceResponse);
                    obtain.writeStrongInterface(iRadioVoiceIndication);
                    if (!this.mRemote.transact(34, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setTtyMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(35, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setTtyMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setVoNrEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(36, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setVoNrEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void startDtmf(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(37, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startDtmf is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void stopDtmf(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(38, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopDtmf is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void switchWaitingOrHoldingAndActive(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(39, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method switchWaitingOrHoldingAndActive is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
