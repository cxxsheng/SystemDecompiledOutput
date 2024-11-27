package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface IRadioNetworkResponse extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$network$IRadioNetworkResponse".replace('$', '.');
    public static final java.lang.String HASH = "c45c122528c07c449ea08f6eacaace17bb7abc38";
    public static final int VERSION = 3;

    void acknowledgeRequest(int i) throws android.os.RemoteException;

    void cancelEmergencyNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void exitEmergencyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void getAllowedNetworkTypesBitmapResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getAvailableBandModesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int[] iArr) throws android.os.RemoteException;

    void getAvailableNetworksResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.OperatorInfo[] operatorInfoArr) throws android.os.RemoteException;

    void getBarringInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.CellIdentity cellIdentity, android.hardware.radio.network.BarringInfo[] barringInfoArr) throws android.os.RemoteException;

    void getCdmaRoamingPreferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getCellInfoListResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.CellInfo[] cellInfoArr) throws android.os.RemoteException;

    void getDataRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RegStateResult regStateResult) throws android.os.RemoteException;

    @java.lang.Deprecated
    void getImsRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getNetworkSelectionModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void getOperatorResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void getSignalStrengthResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.SignalStrength signalStrength) throws android.os.RemoteException;

    void getSystemSelectionChannelsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr) throws android.os.RemoteException;

    void getUsageSettingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getVoiceRadioTechnologyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void getVoiceRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RegStateResult regStateResult) throws android.os.RemoteException;

    void isCellularIdentifierTransparencyEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void isN1ModeEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void isNrDualConnectivityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void isNullCipherAndIntegrityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void isSecurityAlgorithmsUpdatedEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    void setAllowedNetworkTypesBitmapResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setBandModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setBarringPasswordResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCdmaRoamingPreferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCellInfoListRateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setCellularIdentifierTransparencyEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setEmergencyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.EmergencyRegResult emergencyRegResult) throws android.os.RemoteException;

    void setIndicationFilterResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setLinkCapacityReportingCriteriaResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setLocationUpdatesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setN1ModeEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setNetworkSelectionModeAutomaticResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setNetworkSelectionModeManualResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setNrDualConnectivityStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setNullCipherAndIntegrityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSecurityAlgorithmsUpdatedEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSignalStrengthReportingCriteriaResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSuppServiceNotificationsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setSystemSelectionChannelsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void setUsageSettingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void startNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void stopNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    void supplyNetworkDepersonalizationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException;

    void triggerEmergencyNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.network.IRadioNetworkResponse {
        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getAllowedNetworkTypesBitmapResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getAvailableBandModesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getAvailableNetworksResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.OperatorInfo[] operatorInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getBarringInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.CellIdentity cellIdentity, android.hardware.radio.network.BarringInfo[] barringInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getCdmaRoamingPreferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getCellInfoListResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.CellInfo[] cellInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getDataRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RegStateResult regStateResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getImsRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getNetworkSelectionModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getOperatorResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getSignalStrengthResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.SignalStrength signalStrength) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getSystemSelectionChannelsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getVoiceRadioTechnologyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getVoiceRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RegStateResult regStateResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isNrDualConnectivityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setAllowedNetworkTypesBitmapResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setBandModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setBarringPasswordResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setCdmaRoamingPreferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setCellInfoListRateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setIndicationFilterResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setLinkCapacityReportingCriteriaResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setLocationUpdatesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNetworkSelectionModeAutomaticResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNetworkSelectionModeManualResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNrDualConnectivityStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSignalStrengthReportingCriteriaResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSuppServiceNotificationsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSystemSelectionChannelsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void startNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void stopNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void supplyNetworkDepersonalizationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setUsageSettingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getUsageSettingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setEmergencyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.EmergencyRegResult emergencyRegResult) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void triggerEmergencyNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void exitEmergencyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void cancelEmergencyNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNullCipherAndIntegrityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isNullCipherAndIntegrityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isN1ModeEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setN1ModeEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isCellularIdentifierTransparencyEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setCellularIdentifierTransparencyEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSecurityAlgorithmsUpdatedEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isSecurityAlgorithmsUpdatedEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.network.IRadioNetworkResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_cancelEmergencyNetworkScanResponse = 39;
        static final int TRANSACTION_exitEmergencyModeResponse = 38;
        static final int TRANSACTION_getAllowedNetworkTypesBitmapResponse = 2;
        static final int TRANSACTION_getAvailableBandModesResponse = 3;
        static final int TRANSACTION_getAvailableNetworksResponse = 4;
        static final int TRANSACTION_getBarringInfoResponse = 5;
        static final int TRANSACTION_getCdmaRoamingPreferenceResponse = 6;
        static final int TRANSACTION_getCellInfoListResponse = 7;
        static final int TRANSACTION_getDataRegistrationStateResponse = 8;
        static final int TRANSACTION_getImsRegistrationStateResponse = 9;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNetworkSelectionModeResponse = 10;
        static final int TRANSACTION_getOperatorResponse = 11;
        static final int TRANSACTION_getSignalStrengthResponse = 12;
        static final int TRANSACTION_getSystemSelectionChannelsResponse = 13;
        static final int TRANSACTION_getUsageSettingResponse = 35;
        static final int TRANSACTION_getVoiceRadioTechnologyResponse = 14;
        static final int TRANSACTION_getVoiceRegistrationStateResponse = 15;
        static final int TRANSACTION_isCellularIdentifierTransparencyEnabledResponse = 44;
        static final int TRANSACTION_isN1ModeEnabledResponse = 42;
        static final int TRANSACTION_isNrDualConnectivityEnabledResponse = 16;
        static final int TRANSACTION_isNullCipherAndIntegrityEnabledResponse = 41;
        static final int TRANSACTION_isSecurityAlgorithmsUpdatedEnabledResponse = 47;
        static final int TRANSACTION_setAllowedNetworkTypesBitmapResponse = 17;
        static final int TRANSACTION_setBandModeResponse = 18;
        static final int TRANSACTION_setBarringPasswordResponse = 19;
        static final int TRANSACTION_setCdmaRoamingPreferenceResponse = 20;
        static final int TRANSACTION_setCellInfoListRateResponse = 21;
        static final int TRANSACTION_setCellularIdentifierTransparencyEnabledResponse = 45;
        static final int TRANSACTION_setEmergencyModeResponse = 36;
        static final int TRANSACTION_setIndicationFilterResponse = 22;
        static final int TRANSACTION_setLinkCapacityReportingCriteriaResponse = 23;
        static final int TRANSACTION_setLocationUpdatesResponse = 24;
        static final int TRANSACTION_setN1ModeEnabledResponse = 43;
        static final int TRANSACTION_setNetworkSelectionModeAutomaticResponse = 25;
        static final int TRANSACTION_setNetworkSelectionModeManualResponse = 26;
        static final int TRANSACTION_setNrDualConnectivityStateResponse = 27;
        static final int TRANSACTION_setNullCipherAndIntegrityEnabledResponse = 40;
        static final int TRANSACTION_setSecurityAlgorithmsUpdatedEnabledResponse = 46;
        static final int TRANSACTION_setSignalStrengthReportingCriteriaResponse = 28;
        static final int TRANSACTION_setSuppServiceNotificationsResponse = 29;
        static final int TRANSACTION_setSystemSelectionChannelsResponse = 30;
        static final int TRANSACTION_setUsageSettingResponse = 34;
        static final int TRANSACTION_startNetworkScanResponse = 31;
        static final int TRANSACTION_stopNetworkScanResponse = 32;
        static final int TRANSACTION_supplyNetworkDepersonalizationResponse = 33;
        static final int TRANSACTION_triggerEmergencyNetworkScanResponse = 37;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.network.IRadioNetworkResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.network.IRadioNetworkResponse)) {
                return (android.hardware.radio.network.IRadioNetworkResponse) queryLocalInterface;
            }
            return new android.hardware.radio.network.IRadioNetworkResponse.Stub.Proxy(iBinder);
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
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedNetworkTypesBitmapResponse(radioResponseInfo, readInt2);
                    return true;
                case 3:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo2 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getAvailableBandModesResponse(radioResponseInfo2, createIntArray);
                    return true;
                case 4:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo3 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.OperatorInfo[] operatorInfoArr = (android.hardware.radio.network.OperatorInfo[]) parcel.createTypedArray(android.hardware.radio.network.OperatorInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAvailableNetworksResponse(radioResponseInfo3, operatorInfoArr);
                    return true;
                case 5:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo4 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.CellIdentity cellIdentity = (android.hardware.radio.network.CellIdentity) parcel.readTypedObject(android.hardware.radio.network.CellIdentity.CREATOR);
                    android.hardware.radio.network.BarringInfo[] barringInfoArr = (android.hardware.radio.network.BarringInfo[]) parcel.createTypedArray(android.hardware.radio.network.BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getBarringInfoResponse(radioResponseInfo4, cellIdentity, barringInfoArr);
                    return true;
                case 6:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo5 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaRoamingPreferenceResponse(radioResponseInfo5, readInt3);
                    return true;
                case 7:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo6 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.CellInfo[] cellInfoArr = (android.hardware.radio.network.CellInfo[]) parcel.createTypedArray(android.hardware.radio.network.CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCellInfoListResponse(radioResponseInfo6, cellInfoArr);
                    return true;
                case 8:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo7 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.RegStateResult regStateResult = (android.hardware.radio.network.RegStateResult) parcel.readTypedObject(android.hardware.radio.network.RegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDataRegistrationStateResponse(radioResponseInfo7, regStateResult);
                    return true;
                case 9:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo8 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationStateResponse(radioResponseInfo8, readBoolean, readInt4);
                    return true;
                case 10:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo9 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getNetworkSelectionModeResponse(radioResponseInfo9, readBoolean2);
                    return true;
                case 11:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo10 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getOperatorResponse(radioResponseInfo10, readString, readString2, readString3);
                    return true;
                case 12:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo11 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.SignalStrength signalStrength = (android.hardware.radio.network.SignalStrength) parcel.readTypedObject(android.hardware.radio.network.SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSignalStrengthResponse(radioResponseInfo11, signalStrength);
                    return true;
                case 13:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo12 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr = (android.hardware.radio.network.RadioAccessSpecifier[]) parcel.createTypedArray(android.hardware.radio.network.RadioAccessSpecifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSystemSelectionChannelsResponse(radioResponseInfo12, radioAccessSpecifierArr);
                    return true;
                case 14:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo13 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVoiceRadioTechnologyResponse(radioResponseInfo13, readInt5);
                    return true;
                case 15:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo14 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.RegStateResult regStateResult2 = (android.hardware.radio.network.RegStateResult) parcel.readTypedObject(android.hardware.radio.network.RegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getVoiceRegistrationStateResponse(radioResponseInfo14, regStateResult2);
                    return true;
                case 16:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo15 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isNrDualConnectivityEnabledResponse(radioResponseInfo15, readBoolean3);
                    return true;
                case 17:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo16 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAllowedNetworkTypesBitmapResponse(radioResponseInfo16);
                    return true;
                case 18:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo17 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBandModeResponse(radioResponseInfo17);
                    return true;
                case 19:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo18 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBarringPasswordResponse(radioResponseInfo18);
                    return true;
                case 20:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo19 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaRoamingPreferenceResponse(radioResponseInfo19);
                    return true;
                case 21:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo20 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCellInfoListRateResponse(radioResponseInfo20);
                    return true;
                case 22:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo21 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIndicationFilterResponse(radioResponseInfo21);
                    return true;
                case 23:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo22 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLinkCapacityReportingCriteriaResponse(radioResponseInfo22);
                    return true;
                case 24:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo23 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLocationUpdatesResponse(radioResponseInfo23);
                    return true;
                case 25:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo24 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeAutomaticResponse(radioResponseInfo24);
                    return true;
                case 26:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo25 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeManualResponse(radioResponseInfo25);
                    return true;
                case 27:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo26 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNrDualConnectivityStateResponse(radioResponseInfo26);
                    return true;
                case 28:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo27 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReportingCriteriaResponse(radioResponseInfo27);
                    return true;
                case 29:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo28 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSuppServiceNotificationsResponse(radioResponseInfo28);
                    return true;
                case 30:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo29 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemSelectionChannelsResponse(radioResponseInfo29);
                    return true;
                case 31:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo30 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startNetworkScanResponse(radioResponseInfo30);
                    return true;
                case 32:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo31 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopNetworkScanResponse(radioResponseInfo31);
                    return true;
                case 33:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo32 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalizationResponse(radioResponseInfo32, readInt6);
                    return true;
                case 34:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo33 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUsageSettingResponse(radioResponseInfo33);
                    return true;
                case 35:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo34 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getUsageSettingResponse(radioResponseInfo34, readInt7);
                    return true;
                case 36:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo35 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    android.hardware.radio.network.EmergencyRegResult emergencyRegResult = (android.hardware.radio.network.EmergencyRegResult) parcel.readTypedObject(android.hardware.radio.network.EmergencyRegResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    setEmergencyModeResponse(radioResponseInfo35, emergencyRegResult);
                    return true;
                case 37:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo36 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    triggerEmergencyNetworkScanResponse(radioResponseInfo36);
                    return true;
                case 38:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo37 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    exitEmergencyModeResponse(radioResponseInfo37);
                    return true;
                case 39:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo38 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelEmergencyNetworkScanResponse(radioResponseInfo38);
                    return true;
                case 40:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo39 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNullCipherAndIntegrityEnabledResponse(radioResponseInfo39);
                    return true;
                case 41:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo40 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isNullCipherAndIntegrityEnabledResponse(radioResponseInfo40, readBoolean4);
                    return true;
                case 42:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo41 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isN1ModeEnabledResponse(radioResponseInfo41, readBoolean5);
                    return true;
                case 43:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo42 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setN1ModeEnabledResponse(radioResponseInfo42);
                    return true;
                case 44:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo43 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isCellularIdentifierTransparencyEnabledResponse(radioResponseInfo43, readBoolean6);
                    return true;
                case 45:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo44 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCellularIdentifierTransparencyEnabledResponse(radioResponseInfo44);
                    return true;
                case 46:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo45 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSecurityAlgorithmsUpdatedEnabledResponse(radioResponseInfo45);
                    return true;
                case 47:
                    android.hardware.radio.RadioResponseInfo radioResponseInfo46 = (android.hardware.radio.RadioResponseInfo) parcel.readTypedObject(android.hardware.radio.RadioResponseInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isSecurityAlgorithmsUpdatedEnabledResponse(radioResponseInfo46, readBoolean7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.network.IRadioNetworkResponse {
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

            @Override // android.hardware.radio.network.IRadioNetworkResponse
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

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getAllowedNetworkTypesBitmapResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAllowedNetworkTypesBitmapResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getAvailableBandModesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAvailableBandModesResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getAvailableNetworksResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.OperatorInfo[] operatorInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(operatorInfoArr, 0);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAvailableNetworksResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getBarringInfoResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.CellIdentity cellIdentity, android.hardware.radio.network.BarringInfo[] barringInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeTypedArray(barringInfoArr, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getBarringInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getCdmaRoamingPreferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCdmaRoamingPreferenceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getCellInfoListResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.CellInfo[] cellInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(cellInfoArr, 0);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCellInfoListResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getDataRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RegStateResult regStateResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(regStateResult, 0);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getDataRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getImsRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getImsRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getNetworkSelectionModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getNetworkSelectionModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getOperatorResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getOperatorResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getSignalStrengthResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.SignalStrength signalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(signalStrength, 0);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSignalStrengthResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getSystemSelectionChannelsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(radioAccessSpecifierArr, 0);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSystemSelectionChannelsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getVoiceRadioTechnologyResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getVoiceRadioTechnologyResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getVoiceRegistrationStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.RegStateResult regStateResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(regStateResult, 0);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getVoiceRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isNrDualConnectivityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isNrDualConnectivityEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setAllowedNetworkTypesBitmapResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setAllowedNetworkTypesBitmapResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setBandModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setBandModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setBarringPasswordResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setBarringPasswordResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setCdmaRoamingPreferenceResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaRoamingPreferenceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setCellInfoListRateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCellInfoListRateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setIndicationFilterResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setIndicationFilterResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setLinkCapacityReportingCriteriaResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(23, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setLinkCapacityReportingCriteriaResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setLocationUpdatesResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(24, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setLocationUpdatesResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNetworkSelectionModeAutomaticResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(25, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNetworkSelectionModeAutomaticResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNetworkSelectionModeManualResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(26, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNetworkSelectionModeManualResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNrDualConnectivityStateResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(27, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNrDualConnectivityStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSignalStrengthReportingCriteriaResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(28, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSignalStrengthReportingCriteriaResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSuppServiceNotificationsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(29, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSuppServiceNotificationsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSystemSelectionChannelsResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(30, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSystemSelectionChannelsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void startNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(31, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void stopNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(32, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void supplyNetworkDepersonalizationResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(33, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplyNetworkDepersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setUsageSettingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(34, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setUsageSettingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getUsageSettingResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(35, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getUsageSettingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setEmergencyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, android.hardware.radio.network.EmergencyRegResult emergencyRegResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(emergencyRegResult, 0);
                    if (!this.mRemote.transact(36, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setEmergencyModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void triggerEmergencyNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(37, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method triggerEmergencyNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void exitEmergencyModeResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(38, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method exitEmergencyModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void cancelEmergencyNetworkScanResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(39, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cancelEmergencyNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNullCipherAndIntegrityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(40, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNullCipherAndIntegrityEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isNullCipherAndIntegrityEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(41, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isNullCipherAndIntegrityEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isN1ModeEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(42, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isN1ModeEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setN1ModeEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(43, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setN1ModeEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isCellularIdentifierTransparencyEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(44, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isCellularIdentifierTransparencyEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setCellularIdentifierTransparencyEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(45, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCellularIdentifierTransparencyEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSecurityAlgorithmsUpdatedEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (!this.mRemote.transact(46, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSecurityAlgorithmsUpdatedEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isSecurityAlgorithmsUpdatedEnabledResponse(android.hardware.radio.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(47, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isSecurityAlgorithmsUpdatedEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
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

            @Override // android.hardware.radio.network.IRadioNetworkResponse
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
