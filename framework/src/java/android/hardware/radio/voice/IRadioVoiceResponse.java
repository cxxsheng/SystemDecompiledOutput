package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public interface IRadioVoiceResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$voice$IRadioVoiceResponse".replace('$', '.');
    public static final java.lang.String HASH = "78fb79bcb32590a868b3eb7affb39ab90e4ca782";
    public static final int VERSION = 3;

    void acceptCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void acknowledgeRequest(int i) throws android.os.RemoteException;

    void cancelPendingUssdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void conferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void dialResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void emergencyDialResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void exitEmergencyCallbackModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void explicitCallTransferResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void getCallForwardStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.CallForwardInfo[] callForwardInfoArr) throws android.os.RemoteException;

    void getCallWaitingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException;

    void getClipResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getClirResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException;

    void getCurrentCallsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.Call[] callArr) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getLastCallFailCauseResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.LastCallFailCauseInfo lastCallFailCauseInfo) throws android.os.RemoteException;

    void getMuteResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void getPreferredVoicePrivacyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void getTtyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void handleStkCallSetupRequestFromSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void hangupConnectionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void hangupForegroundResumeBackgroundResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void hangupWaitingOrBackgroundResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void isVoNrEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void rejectCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void sendBurstDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void sendCdmaFeatureCodeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void sendDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void sendUssdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void separateConnectionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCallForwardResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCallWaitingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setClirResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setMuteResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setPreferredVoicePrivacyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setTtyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setVoNrEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void startDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void stopDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void switchWaitingOrHoldingAndActiveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.voice.IRadioVoiceResponse {
        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void acceptCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void cancelPendingUssdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void conferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void dialResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void emergencyDialResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void exitEmergencyCallbackModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void explicitCallTransferResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getCallForwardStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.CallForwardInfo[] callForwardInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getCallWaitingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getClipResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getClirResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getCurrentCallsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.Call[] callArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getLastCallFailCauseResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.LastCallFailCauseInfo lastCallFailCauseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getMuteResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getPreferredVoicePrivacyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getTtyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void handleStkCallSetupRequestFromSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void hangupConnectionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void hangupForegroundResumeBackgroundResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void hangupWaitingOrBackgroundResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void isVoNrEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void rejectCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendBurstDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendCdmaFeatureCodeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendUssdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void separateConnectionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setCallForwardResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setCallWaitingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setClirResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setMuteResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setPreferredVoicePrivacyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setTtyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setVoNrEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void startDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void stopDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void switchWaitingOrHoldingAndActiveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.voice.IRadioVoiceResponse {
        static final int TRANSACTION_acceptCallResponse = 1;
        static final int TRANSACTION_acknowledgeRequest = 2;
        static final int TRANSACTION_cancelPendingUssdResponse = 3;
        static final int TRANSACTION_conferenceResponse = 4;
        static final int TRANSACTION_dialResponse = 5;
        static final int TRANSACTION_emergencyDialResponse = 6;
        static final int TRANSACTION_exitEmergencyCallbackModeResponse = 7;
        static final int TRANSACTION_explicitCallTransferResponse = 8;
        static final int TRANSACTION_getCallForwardStatusResponse = 9;
        static final int TRANSACTION_getCallWaitingResponse = 10;
        static final int TRANSACTION_getClipResponse = 11;
        static final int TRANSACTION_getClirResponse = 12;
        static final int TRANSACTION_getCurrentCallsResponse = 13;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLastCallFailCauseResponse = 14;
        static final int TRANSACTION_getMuteResponse = 15;
        static final int TRANSACTION_getPreferredVoicePrivacyResponse = 16;
        static final int TRANSACTION_getTtyModeResponse = 17;
        static final int TRANSACTION_handleStkCallSetupRequestFromSimResponse = 18;
        static final int TRANSACTION_hangupConnectionResponse = 19;
        static final int TRANSACTION_hangupForegroundResumeBackgroundResponse = 20;
        static final int TRANSACTION_hangupWaitingOrBackgroundResponse = 21;
        static final int TRANSACTION_isVoNrEnabledResponse = 22;
        static final int TRANSACTION_rejectCallResponse = 23;
        static final int TRANSACTION_sendBurstDtmfResponse = 24;
        static final int TRANSACTION_sendCdmaFeatureCodeResponse = 25;
        static final int TRANSACTION_sendDtmfResponse = 26;
        static final int TRANSACTION_sendUssdResponse = 27;
        static final int TRANSACTION_separateConnectionResponse = 28;
        static final int TRANSACTION_setCallForwardResponse = 29;
        static final int TRANSACTION_setCallWaitingResponse = 30;
        static final int TRANSACTION_setClirResponse = 31;
        static final int TRANSACTION_setMuteResponse = 32;
        static final int TRANSACTION_setPreferredVoicePrivacyResponse = 33;
        static final int TRANSACTION_setTtyModeResponse = 34;
        static final int TRANSACTION_setVoNrEnabledResponse = 35;
        static final int TRANSACTION_startDtmfResponse = 36;
        static final int TRANSACTION_stopDtmfResponse = 37;
        static final int TRANSACTION_switchWaitingOrHoldingAndActiveResponse = 38;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.voice.IRadioVoiceResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.voice.IRadioVoiceResponse)) {
                return (android.hardware.radio.voice.IRadioVoiceResponse) queryLocalInterface;
            }
            return new android.hardware.radio.voice.IRadioVoiceResponse.Stub.Proxy(iBinder);
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
                    android.hardware.radio.RadioResponseInfo radioResponseInfo = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acceptCallResponse(radioResponseInfo);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(readInt);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelPendingUssdResponse(radioResponseInfo2);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    conferenceResponse(radioResponseInfo3);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    dialResponse(radioResponseInfo4);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    emergencyDialResponse(radioResponseInfo5);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    exitEmergencyCallbackModeResponse(radioResponseInfo6);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    explicitCallTransferResponse(radioResponseInfo7);
                    return true;
                case 9:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.voice.CallForwardInfo[] callForwardInfoArr = (android.hardware.radio.voice.CallForwardInfo[]) parcel.createTypedArray(android.hardware.radio.voice.CallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCallForwardStatusResponse(radioResponseInfo8, callForwardInfoArr);
                    return true;
                case 10:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo9 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallWaitingResponse(radioResponseInfo9, readBoolean, readInt2);
                    return true;
                case 11:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo10 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClipResponse(radioResponseInfo10, readInt3);
                    return true;
                case 12:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo11 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClirResponse(radioResponseInfo11, readInt4, readInt5);
                    return true;
                case 13:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo12 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.voice.Call[] callArr = (android.hardware.radio.voice.Call[]) parcel.createTypedArray(android.hardware.radio.voice.Call.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCurrentCallsResponse(radioResponseInfo12, callArr);
                    return true;
                case 14:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo13 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.voice.LastCallFailCauseInfo lastCallFailCauseInfo = (android.hardware.radio.voice.LastCallFailCauseInfo) parcel.readTypedObject(android.hardware.radio.voice.LastCallFailCauseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getLastCallFailCauseResponse(radioResponseInfo13, lastCallFailCauseInfo);
                    return true;
                case 15:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo14 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getMuteResponse(radioResponseInfo14, readBoolean2);
                    return true;
                case 16:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo15 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getPreferredVoicePrivacyResponse(radioResponseInfo15, readBoolean3);
                    return true;
                case 17:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo16 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTtyModeResponse(radioResponseInfo16, readInt6);
                    return true;
                case 18:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo17 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleStkCallSetupRequestFromSimResponse(radioResponseInfo17);
                    return true;
                case 19:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo18 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupConnectionResponse(radioResponseInfo18);
                    return true;
                case 20:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo19 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupForegroundResumeBackgroundResponse(radioResponseInfo19);
                    return true;
                case 21:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo20 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupWaitingOrBackgroundResponse(radioResponseInfo20);
                    return true;
                case 22:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo21 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isVoNrEnabledResponse(radioResponseInfo21, readBoolean4);
                    return true;
                case 23:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo22 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectCallResponse(radioResponseInfo22);
                    return true;
                case 24:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo23 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendBurstDtmfResponse(radioResponseInfo23);
                    return true;
                case 25:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo24 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaFeatureCodeResponse(radioResponseInfo24);
                    return true;
                case 26:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo25 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDtmfResponse(radioResponseInfo25);
                    return true;
                case 27:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo26 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendUssdResponse(radioResponseInfo26);
                    return true;
                case 28:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo27 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    separateConnectionResponse(radioResponseInfo27);
                    return true;
                case 29:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo28 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallForwardResponse(radioResponseInfo28);
                    return true;
                case 30:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo29 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallWaitingResponse(radioResponseInfo29);
                    return true;
                case 31:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo30 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setClirResponse(radioResponseInfo30);
                    return true;
                case 32:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo31 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMuteResponse(radioResponseInfo31);
                    return true;
                case 33:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo32 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredVoicePrivacyResponse(radioResponseInfo32);
                    return true;
                case 34:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo33 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTtyModeResponse(radioResponseInfo33);
                    return true;
                case 35:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo34 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVoNrEnabledResponse(radioResponseInfo34);
                    return true;
                case 36:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo35 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDtmfResponse(radioResponseInfo35);
                    return true;
                case 37:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo36 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopDtmfResponse(radioResponseInfo36);
                    return true;
                case 38:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo37 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchWaitingOrHoldingAndActiveResponse(radioResponseInfo37);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.voice.IRadioVoiceResponse {
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

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void acceptCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acceptCallResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void acknowledgeRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void cancelPendingUssdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cancelPendingUssdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void conferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method conferenceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void dialResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method dialResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void emergencyDialResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method emergencyDialResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void exitEmergencyCallbackModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method exitEmergencyCallbackModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void explicitCallTransferResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method explicitCallTransferResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getCallForwardStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.CallForwardInfo[] callForwardInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(callForwardInfoArr, 0);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCallForwardStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getCallWaitingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCallWaitingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getClipResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getClipResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getClirResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getClirResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getCurrentCallsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.Call[] callArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(callArr, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCurrentCallsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getLastCallFailCauseResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.voice.LastCallFailCauseInfo lastCallFailCauseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(lastCallFailCauseInfo, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getLastCallFailCauseResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getMuteResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getMuteResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getPreferredVoicePrivacyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getPreferredVoicePrivacyResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getTtyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getTtyModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void handleStkCallSetupRequestFromSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method handleStkCallSetupRequestFromSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void hangupConnectionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hangupConnectionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void hangupForegroundResumeBackgroundResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hangupForegroundResumeBackgroundResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void hangupWaitingOrBackgroundResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method hangupWaitingOrBackgroundResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void isVoNrEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isVoNrEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void rejectCallResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(23, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method rejectCallResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendBurstDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(24, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendBurstDtmfResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendCdmaFeatureCodeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(25, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendCdmaFeatureCodeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(26, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendDtmfResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendUssdResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(27, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendUssdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void separateConnectionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(28, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method separateConnectionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setCallForwardResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(29, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCallForwardResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setCallWaitingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(30, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCallWaitingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setClirResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(31, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setClirResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setMuteResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(32, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setMuteResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setPreferredVoicePrivacyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(33, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setPreferredVoicePrivacyResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setTtyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(34, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setTtyModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setVoNrEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(35, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setVoNrEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void startDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(36, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startDtmfResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void stopDtmfResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(37, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopDtmfResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void switchWaitingOrHoldingAndActiveResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(38, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method switchWaitingOrHoldingAndActiveResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
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

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
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
