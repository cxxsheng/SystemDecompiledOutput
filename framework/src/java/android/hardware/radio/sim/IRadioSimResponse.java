package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public interface IRadioSimResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$sim$IRadioSimResponse".replace('$', '.');
    public static final java.lang.String HASH = "ea7be3035be8d4869237a6478d2e0bb0efcc1e87";
    public static final int VERSION = 3;

    void acknowledgeRequest(int i) throws android.os.RemoteException;

    void areUiccApplicationsEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void changeIccPin2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void changeIccPinForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void enableUiccApplicationsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void getAllowedCarriersResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.CarrierRestrictions carrierRestrictions, int i) throws android.os.RemoteException;

    void getCdmaSubscriptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) throws android.os.RemoteException;

    void getCdmaSubscriptionSourceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getFacilityLockForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getIccCardStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.CardStatus cardStatus) throws android.os.RemoteException;

    void getImsiForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getSimPhonebookCapacityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.PhonebookCapacity phonebookCapacity) throws android.os.RemoteException;

    void getSimPhonebookRecordsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    @java.lang.Deprecated
    void iccCloseLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void iccCloseLogicalChannelWithSessionInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void iccIoForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException;

    void iccOpenLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, byte[] bArr) throws android.os.RemoteException;

    void iccTransmitApduBasicChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException;

    void iccTransmitApduLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException;

    void reportStkServiceIsRunningResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void requestIccSimAuthenticationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException;

    void sendEnvelopeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException;

    void sendEnvelopeWithStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException;

    void sendTerminalResponseToSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setAllowedCarriersResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCdmaSubscriptionSourceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setFacilityLockForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void setSimCardPowerResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setUiccSubscriptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void supplyIccPin2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void supplyIccPinForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void supplyIccPuk2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void supplyIccPukForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void supplySimDepersonalizationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException;

    void updateSimPhonebookRecordsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.sim.IRadioSimResponse {
        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void areUiccApplicationsEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void changeIccPin2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void changeIccPinForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void enableUiccApplicationsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getAllowedCarriersResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.CarrierRestrictions carrierRestrictions, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getCdmaSubscriptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getCdmaSubscriptionSourceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getFacilityLockForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getIccCardStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.CardStatus cardStatus) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getImsiForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getSimPhonebookCapacityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.PhonebookCapacity phonebookCapacity) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getSimPhonebookRecordsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccCloseLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccIoForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccOpenLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccTransmitApduBasicChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccTransmitApduLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void reportStkServiceIsRunningResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void requestIccSimAuthenticationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void sendEnvelopeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void sendEnvelopeWithStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void sendTerminalResponseToSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setAllowedCarriersResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setCdmaSubscriptionSourceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setFacilityLockForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setSimCardPowerResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setUiccSubscriptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPin2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPinForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPuk2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPukForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplySimDepersonalizationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void updateSimPhonebookRecordsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccCloseLogicalChannelWithSessionInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.sim.IRadioSimResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_areUiccApplicationsEnabledResponse = 2;
        static final int TRANSACTION_changeIccPin2ForAppResponse = 3;
        static final int TRANSACTION_changeIccPinForAppResponse = 4;
        static final int TRANSACTION_enableUiccApplicationsResponse = 5;
        static final int TRANSACTION_getAllowedCarriersResponse = 6;
        static final int TRANSACTION_getCdmaSubscriptionResponse = 7;
        static final int TRANSACTION_getCdmaSubscriptionSourceResponse = 8;
        static final int TRANSACTION_getFacilityLockForAppResponse = 9;
        static final int TRANSACTION_getIccCardStatusResponse = 10;
        static final int TRANSACTION_getImsiForAppResponse = 11;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSimPhonebookCapacityResponse = 12;
        static final int TRANSACTION_getSimPhonebookRecordsResponse = 13;
        static final int TRANSACTION_iccCloseLogicalChannelResponse = 14;
        static final int TRANSACTION_iccCloseLogicalChannelWithSessionInfoResponse = 36;
        static final int TRANSACTION_iccIoForAppResponse = 15;
        static final int TRANSACTION_iccOpenLogicalChannelResponse = 16;
        static final int TRANSACTION_iccTransmitApduBasicChannelResponse = 17;
        static final int TRANSACTION_iccTransmitApduLogicalChannelResponse = 18;
        static final int TRANSACTION_reportStkServiceIsRunningResponse = 19;
        static final int TRANSACTION_requestIccSimAuthenticationResponse = 20;
        static final int TRANSACTION_sendEnvelopeResponse = 21;
        static final int TRANSACTION_sendEnvelopeWithStatusResponse = 22;
        static final int TRANSACTION_sendTerminalResponseToSimResponse = 23;
        static final int TRANSACTION_setAllowedCarriersResponse = 24;
        static final int TRANSACTION_setCarrierInfoForImsiEncryptionResponse = 25;
        static final int TRANSACTION_setCdmaSubscriptionSourceResponse = 26;
        static final int TRANSACTION_setFacilityLockForAppResponse = 27;
        static final int TRANSACTION_setSimCardPowerResponse = 28;
        static final int TRANSACTION_setUiccSubscriptionResponse = 29;
        static final int TRANSACTION_supplyIccPin2ForAppResponse = 30;
        static final int TRANSACTION_supplyIccPinForAppResponse = 31;
        static final int TRANSACTION_supplyIccPuk2ForAppResponse = 32;
        static final int TRANSACTION_supplyIccPukForAppResponse = 33;
        static final int TRANSACTION_supplySimDepersonalizationResponse = 34;
        static final int TRANSACTION_updateSimPhonebookRecordsResponse = 35;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.sim.IRadioSimResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.sim.IRadioSimResponse)) {
                return (android.hardware.radio.sim.IRadioSimResponse) queryLocalInterface;
            }
            return new android.hardware.radio.sim.IRadioSimResponse.Stub.Proxy(iBinder);
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
                    acknowledgeRequest(readInt);
                    return true;
                case 2:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    areUiccApplicationsEnabledResponse(radioResponseInfo, readBoolean);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeIccPin2ForAppResponse(radioResponseInfo2, readInt2);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeIccPinForAppResponse(radioResponseInfo3, readInt3);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableUiccApplicationsResponse(radioResponseInfo4);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.CarrierRestrictions carrierRestrictions = (android.hardware.radio.sim.CarrierRestrictions) parcel.readTypedObject(android.hardware.radio.sim.CarrierRestrictions.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedCarriersResponse(radioResponseInfo5, carrierRestrictions, readInt4);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscriptionResponse(radioResponseInfo6, readString, readString2, readString3, readString4, readString5);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscriptionSourceResponse(radioResponseInfo7, readInt5);
                    return true;
                case 9:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getFacilityLockForAppResponse(radioResponseInfo8, readInt6);
                    return true;
                case 10:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo9 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.CardStatus cardStatus = (android.hardware.radio.sim.CardStatus) parcel.readTypedObject(android.hardware.radio.sim.CardStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    getIccCardStatusResponse(radioResponseInfo9, cardStatus);
                    return true;
                case 11:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo10 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getImsiForAppResponse(radioResponseInfo10, readString6);
                    return true;
                case 12:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo11 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.PhonebookCapacity phonebookCapacity = (android.hardware.radio.sim.PhonebookCapacity) parcel.readTypedObject(android.hardware.radio.sim.PhonebookCapacity.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimPhonebookCapacityResponse(radioResponseInfo11, phonebookCapacity);
                    return true;
                case 13:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo12 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimPhonebookRecordsResponse(radioResponseInfo12);
                    return true;
                case 14:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo13 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannelResponse(radioResponseInfo13);
                    return true;
                case 15:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo14 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.IccIoResult iccIoResult = (android.hardware.radio.sim.IccIoResult) parcel.readTypedObject(android.hardware.radio.sim.IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccIoForAppResponse(radioResponseInfo14, iccIoResult);
                    return true;
                case 16:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo15 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    iccOpenLogicalChannelResponse(radioResponseInfo15, readInt7, createByteArray);
                    return true;
                case 17:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo16 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.IccIoResult iccIoResult2 = (android.hardware.radio.sim.IccIoResult) parcel.readTypedObject(android.hardware.radio.sim.IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduBasicChannelResponse(radioResponseInfo16, iccIoResult2);
                    return true;
                case 18:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo17 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.IccIoResult iccIoResult3 = (android.hardware.radio.sim.IccIoResult) parcel.readTypedObject(android.hardware.radio.sim.IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduLogicalChannelResponse(radioResponseInfo17, iccIoResult3);
                    return true;
                case 19:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo18 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportStkServiceIsRunningResponse(radioResponseInfo18);
                    return true;
                case 20:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo19 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.IccIoResult iccIoResult4 = (android.hardware.radio.sim.IccIoResult) parcel.readTypedObject(android.hardware.radio.sim.IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIccSimAuthenticationResponse(radioResponseInfo19, iccIoResult4);
                    return true;
                case 21:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo20 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendEnvelopeResponse(radioResponseInfo20, readString7);
                    return true;
                case 22:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo21 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.sim.IccIoResult iccIoResult5 = (android.hardware.radio.sim.IccIoResult) parcel.readTypedObject(android.hardware.radio.sim.IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEnvelopeWithStatusResponse(radioResponseInfo21, iccIoResult5);
                    return true;
                case 23:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo22 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTerminalResponseToSimResponse(radioResponseInfo22);
                    return true;
                case 24:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo23 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAllowedCarriersResponse(radioResponseInfo23);
                    return true;
                case 25:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo24 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryptionResponse(radioResponseInfo24);
                    return true;
                case 26:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo25 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaSubscriptionSourceResponse(radioResponseInfo25);
                    return true;
                case 27:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo26 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFacilityLockForAppResponse(radioResponseInfo26, readInt8);
                    return true;
                case 28:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo27 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimCardPowerResponse(radioResponseInfo27);
                    return true;
                case 29:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo28 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiccSubscriptionResponse(radioResponseInfo28);
                    return true;
                case 30:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo29 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPin2ForAppResponse(radioResponseInfo29, readInt9);
                    return true;
                case 31:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo30 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPinForAppResponse(radioResponseInfo30, readInt10);
                    return true;
                case 32:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo31 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPuk2ForAppResponse(radioResponseInfo31, readInt11);
                    return true;
                case 33:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo32 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPukForAppResponse(radioResponseInfo32, readInt12);
                    return true;
                case 34:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo33 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplySimDepersonalizationResponse(radioResponseInfo33, readInt13, readInt14);
                    return true;
                case 35:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo34 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSimPhonebookRecordsResponse(radioResponseInfo34, readInt15);
                    return true;
                case 36:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo35 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannelWithSessionInfoResponse(radioResponseInfo35);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.sim.IRadioSimResponse {
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

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void acknowledgeRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void areUiccApplicationsEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method areUiccApplicationsEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void changeIccPin2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method changeIccPin2ForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void changeIccPinForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method changeIccPinForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void enableUiccApplicationsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method enableUiccApplicationsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getAllowedCarriersResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.CarrierRestrictions carrierRestrictions, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(carrierRestrictions, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAllowedCarriersResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getCdmaSubscriptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCdmaSubscriptionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getCdmaSubscriptionSourceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCdmaSubscriptionSourceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getFacilityLockForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getFacilityLockForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getIccCardStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.CardStatus cardStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(cardStatus, 0);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getIccCardStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getImsiForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getImsiForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getSimPhonebookCapacityResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.PhonebookCapacity phonebookCapacity) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(phonebookCapacity, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSimPhonebookCapacityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getSimPhonebookRecordsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSimPhonebookRecordsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccCloseLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method iccCloseLogicalChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccIoForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method iccIoForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccOpenLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method iccOpenLogicalChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccTransmitApduBasicChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method iccTransmitApduBasicChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccTransmitApduLogicalChannelResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method iccTransmitApduLogicalChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void reportStkServiceIsRunningResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method reportStkServiceIsRunningResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void requestIccSimAuthenticationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method requestIccSimAuthenticationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void sendEnvelopeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendEnvelopeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void sendEnvelopeWithStatusResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.sim.IccIoResult iccIoResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendEnvelopeWithStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void sendTerminalResponseToSimResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(23, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method sendTerminalResponseToSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setAllowedCarriersResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(24, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setAllowedCarriersResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(25, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCarrierInfoForImsiEncryptionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setCdmaSubscriptionSourceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(26, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaSubscriptionSourceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setFacilityLockForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(27, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setFacilityLockForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setSimCardPowerResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(28, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSimCardPowerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setUiccSubscriptionResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(29, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setUiccSubscriptionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPin2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(30, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplyIccPin2ForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPinForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(31, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplyIccPinForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPuk2ForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(32, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplyIccPuk2ForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPukForAppResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(33, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplyIccPukForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplySimDepersonalizationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(34, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplySimDepersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void updateSimPhonebookRecordsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(35, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method updateSimPhonebookRecordsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccCloseLogicalChannelWithSessionInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(36, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method iccCloseLogicalChannelWithSessionInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
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

            @Override // android.hardware.radio.sim.IRadioSimResponse
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
