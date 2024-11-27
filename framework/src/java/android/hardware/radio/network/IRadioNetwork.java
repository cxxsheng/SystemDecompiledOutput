package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface IRadioNetwork extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$radio$network$IRadioNetwork".replace('$', '.');
    public static final java.lang.String HASH = "c45c122528c07c449ea08f6eacaace17bb7abc38";
    public static final int VERSION = 3;

    void cancelEmergencyNetworkScan(int i, boolean z) throws android.os.RemoteException;

    void exitEmergencyMode(int i) throws android.os.RemoteException;

    void getAllowedNetworkTypesBitmap(int i) throws android.os.RemoteException;

    void getAvailableBandModes(int i) throws android.os.RemoteException;

    void getAvailableNetworks(int i) throws android.os.RemoteException;

    void getBarringInfo(int i) throws android.os.RemoteException;

    void getCdmaRoamingPreference(int i) throws android.os.RemoteException;

    void getCellInfoList(int i) throws android.os.RemoteException;

    void getDataRegistrationState(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    void getImsRegistrationState(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void getNetworkSelectionMode(int i) throws android.os.RemoteException;

    void getOperator(int i) throws android.os.RemoteException;

    void getSignalStrength(int i) throws android.os.RemoteException;

    void getSystemSelectionChannels(int i) throws android.os.RemoteException;

    void getUsageSetting(int i) throws android.os.RemoteException;

    void getVoiceRadioTechnology(int i) throws android.os.RemoteException;

    void getVoiceRegistrationState(int i) throws android.os.RemoteException;

    void isCellularIdentifierTransparencyEnabled(int i) throws android.os.RemoteException;

    void isN1ModeEnabled(int i) throws android.os.RemoteException;

    void isNrDualConnectivityEnabled(int i) throws android.os.RemoteException;

    void isNullCipherAndIntegrityEnabled(int i) throws android.os.RemoteException;

    void isSecurityAlgorithmsUpdatedEnabled(int i) throws android.os.RemoteException;

    void responseAcknowledgement() throws android.os.RemoteException;

    void setAllowedNetworkTypesBitmap(int i, int i2) throws android.os.RemoteException;

    void setBandMode(int i, int i2) throws android.os.RemoteException;

    void setBarringPassword(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void setCdmaRoamingPreference(int i, int i2) throws android.os.RemoteException;

    void setCellInfoListRate(int i, int i2) throws android.os.RemoteException;

    void setCellularIdentifierTransparencyEnabled(int i, boolean z) throws android.os.RemoteException;

    void setEmergencyMode(int i, int i2) throws android.os.RemoteException;

    void setIndicationFilter(int i, int i2) throws android.os.RemoteException;

    void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) throws android.os.RemoteException;

    void setLocationUpdates(int i, boolean z) throws android.os.RemoteException;

    void setN1ModeEnabled(int i, boolean z) throws android.os.RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws android.os.RemoteException;

    void setNetworkSelectionModeManual(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void setNrDualConnectivityState(int i, byte b) throws android.os.RemoteException;

    void setNullCipherAndIntegrityEnabled(int i, boolean z) throws android.os.RemoteException;

    void setResponseFunctions(android.hardware.radio.network.IRadioNetworkResponse iRadioNetworkResponse, android.hardware.radio.network.IRadioNetworkIndication iRadioNetworkIndication) throws android.os.RemoteException;

    void setSecurityAlgorithmsUpdatedEnabled(int i, boolean z) throws android.os.RemoteException;

    void setSignalStrengthReportingCriteria(int i, android.hardware.radio.network.SignalThresholdInfo[] signalThresholdInfoArr) throws android.os.RemoteException;

    void setSuppServiceNotifications(int i, boolean z) throws android.os.RemoteException;

    void setSystemSelectionChannels(int i, boolean z, android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr) throws android.os.RemoteException;

    void setUsageSetting(int i, int i2) throws android.os.RemoteException;

    void startNetworkScan(int i, android.hardware.radio.network.NetworkScanRequest networkScanRequest) throws android.os.RemoteException;

    void stopNetworkScan(int i) throws android.os.RemoteException;

    void supplyNetworkDepersonalization(int i, java.lang.String str) throws android.os.RemoteException;

    void triggerEmergencyNetworkScan(int i, android.hardware.radio.network.EmergencyNetworkScanTrigger emergencyNetworkScanTrigger) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.network.IRadioNetwork {
        @Override // android.hardware.radio.network.IRadioNetwork
        public void getAllowedNetworkTypesBitmap(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getAvailableBandModes(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getAvailableNetworks(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getBarringInfo(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getCdmaRoamingPreference(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getCellInfoList(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getDataRegistrationState(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getImsRegistrationState(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getNetworkSelectionMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getOperator(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getSignalStrength(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getSystemSelectionChannels(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getVoiceRadioTechnology(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getVoiceRegistrationState(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isNrDualConnectivityEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void responseAcknowledgement() throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setAllowedNetworkTypesBitmap(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setBandMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setBarringPassword(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setCdmaRoamingPreference(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setCellInfoListRate(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setIndicationFilter(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setLocationUpdates(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNetworkSelectionModeAutomatic(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNetworkSelectionModeManual(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNrDualConnectivityState(int i, byte b) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setResponseFunctions(android.hardware.radio.network.IRadioNetworkResponse iRadioNetworkResponse, android.hardware.radio.network.IRadioNetworkIndication iRadioNetworkIndication) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSignalStrengthReportingCriteria(int i, android.hardware.radio.network.SignalThresholdInfo[] signalThresholdInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSuppServiceNotifications(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSystemSelectionChannels(int i, boolean z, android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void startNetworkScan(int i, android.hardware.radio.network.NetworkScanRequest networkScanRequest) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void stopNetworkScan(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void supplyNetworkDepersonalization(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setUsageSetting(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void getUsageSetting(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setEmergencyMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void triggerEmergencyNetworkScan(int i, android.hardware.radio.network.EmergencyNetworkScanTrigger emergencyNetworkScanTrigger) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void cancelEmergencyNetworkScan(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void exitEmergencyMode(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setNullCipherAndIntegrityEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isNullCipherAndIntegrityEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isN1ModeEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setN1ModeEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isCellularIdentifierTransparencyEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setCellularIdentifierTransparencyEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void setSecurityAlgorithmsUpdatedEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public void isSecurityAlgorithmsUpdatedEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetwork
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.network.IRadioNetwork {
        static final int TRANSACTION_cancelEmergencyNetworkScan = 39;
        static final int TRANSACTION_exitEmergencyMode = 40;
        static final int TRANSACTION_getAllowedNetworkTypesBitmap = 1;
        static final int TRANSACTION_getAvailableBandModes = 2;
        static final int TRANSACTION_getAvailableNetworks = 3;
        static final int TRANSACTION_getBarringInfo = 4;
        static final int TRANSACTION_getCdmaRoamingPreference = 5;
        static final int TRANSACTION_getCellInfoList = 6;
        static final int TRANSACTION_getDataRegistrationState = 7;
        static final int TRANSACTION_getImsRegistrationState = 8;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNetworkSelectionMode = 9;
        static final int TRANSACTION_getOperator = 10;
        static final int TRANSACTION_getSignalStrength = 11;
        static final int TRANSACTION_getSystemSelectionChannels = 12;
        static final int TRANSACTION_getUsageSetting = 36;
        static final int TRANSACTION_getVoiceRadioTechnology = 13;
        static final int TRANSACTION_getVoiceRegistrationState = 14;
        static final int TRANSACTION_isCellularIdentifierTransparencyEnabled = 45;
        static final int TRANSACTION_isN1ModeEnabled = 43;
        static final int TRANSACTION_isNrDualConnectivityEnabled = 15;
        static final int TRANSACTION_isNullCipherAndIntegrityEnabled = 42;
        static final int TRANSACTION_isSecurityAlgorithmsUpdatedEnabled = 48;
        static final int TRANSACTION_responseAcknowledgement = 16;
        static final int TRANSACTION_setAllowedNetworkTypesBitmap = 17;
        static final int TRANSACTION_setBandMode = 18;
        static final int TRANSACTION_setBarringPassword = 19;
        static final int TRANSACTION_setCdmaRoamingPreference = 20;
        static final int TRANSACTION_setCellInfoListRate = 21;
        static final int TRANSACTION_setCellularIdentifierTransparencyEnabled = 46;
        static final int TRANSACTION_setEmergencyMode = 37;
        static final int TRANSACTION_setIndicationFilter = 22;
        static final int TRANSACTION_setLinkCapacityReportingCriteria = 23;
        static final int TRANSACTION_setLocationUpdates = 24;
        static final int TRANSACTION_setN1ModeEnabled = 44;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 25;
        static final int TRANSACTION_setNetworkSelectionModeManual = 26;
        static final int TRANSACTION_setNrDualConnectivityState = 27;
        static final int TRANSACTION_setNullCipherAndIntegrityEnabled = 41;
        static final int TRANSACTION_setResponseFunctions = 28;
        static final int TRANSACTION_setSecurityAlgorithmsUpdatedEnabled = 47;
        static final int TRANSACTION_setSignalStrengthReportingCriteria = 29;
        static final int TRANSACTION_setSuppServiceNotifications = 30;
        static final int TRANSACTION_setSystemSelectionChannels = 31;
        static final int TRANSACTION_setUsageSetting = 35;
        static final int TRANSACTION_startNetworkScan = 32;
        static final int TRANSACTION_stopNetworkScan = 33;
        static final int TRANSACTION_supplyNetworkDepersonalization = 34;
        static final int TRANSACTION_triggerEmergencyNetworkScan = 38;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.network.IRadioNetwork asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.network.IRadioNetwork)) {
                return (android.hardware.radio.network.IRadioNetwork) queryLocalInterface;
            }
            return new android.hardware.radio.network.IRadioNetwork.Stub.Proxy(iBinder);
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
                    getAllowedNetworkTypesBitmap(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAvailableBandModes(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAvailableNetworks(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getBarringInfo(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaRoamingPreference(readInt5);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCellInfoList(readInt6);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDataRegistrationState(readInt7);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationState(readInt8);
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNetworkSelectionMode(readInt9);
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getOperator(readInt10);
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSignalStrength(readInt11);
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSystemSelectionChannels(readInt12);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVoiceRadioTechnology(readInt13);
                    return true;
                case 14:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVoiceRegistrationState(readInt14);
                    return true;
                case 15:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isNrDualConnectivityEnabled(readInt15);
                    return true;
                case 16:
                    responseAcknowledgement();
                    return true;
                case 17:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedNetworkTypesBitmap(readInt16, readInt17);
                    return true;
                case 18:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBandMode(readInt18, readInt19);
                    return true;
                case 19:
                    int readInt20 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setBarringPassword(readInt20, readString, readString2, readString3);
                    return true;
                case 20:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCdmaRoamingPreference(readInt21, readInt22);
                    return true;
                case 21:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCellInfoListRate(readInt23, readInt24);
                    return true;
                case 22:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIndicationFilter(readInt25, readInt26);
                    return true;
                case 23:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLinkCapacityReportingCriteria(readInt27, readInt28, readInt29, readInt30, createIntArray, createIntArray2, readInt31);
                    return true;
                case 24:
                    int readInt32 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationUpdates(readInt32, readBoolean);
                    return true;
                case 25:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeAutomatic(readInt33);
                    return true;
                case 26:
                    int readInt34 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeManual(readInt34, readString4, readInt35);
                    return true;
                case 27:
                    int readInt36 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setNrDualConnectivityState(readInt36, readByte);
                    return true;
                case 28:
                    android.hardware.radio.network.IRadioNetworkResponse asInterface = android.hardware.radio.network.IRadioNetworkResponse.Stub.asInterface(parcel.readStrongBinder());
                    android.hardware.radio.network.IRadioNetworkIndication asInterface2 = android.hardware.radio.network.IRadioNetworkIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 29:
                    int readInt37 = parcel.readInt();
                    android.hardware.radio.network.SignalThresholdInfo[] signalThresholdInfoArr = (android.hardware.radio.network.SignalThresholdInfo[]) parcel.createTypedArray(android.hardware.radio.network.SignalThresholdInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReportingCriteria(readInt37, signalThresholdInfoArr);
                    return true;
                case 30:
                    int readInt38 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSuppServiceNotifications(readInt38, readBoolean2);
                    return true;
                case 31:
                    int readInt39 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr = (android.hardware.radio.network.RadioAccessSpecifier[]) parcel.createTypedArray(android.hardware.radio.network.RadioAccessSpecifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemSelectionChannels(readInt39, readBoolean3, radioAccessSpecifierArr);
                    return true;
                case 32:
                    int readInt40 = parcel.readInt();
                    android.hardware.radio.network.NetworkScanRequest networkScanRequest = (android.hardware.radio.network.NetworkScanRequest) parcel.readTypedObject(android.hardware.radio.network.NetworkScanRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    startNetworkScan(readInt40, networkScanRequest);
                    return true;
                case 33:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopNetworkScan(readInt41);
                    return true;
                case 34:
                    int readInt42 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalization(readInt42, readString5);
                    return true;
                case 35:
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUsageSetting(readInt43, readInt44);
                    return true;
                case 36:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getUsageSetting(readInt45);
                    return true;
                case 37:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEmergencyMode(readInt46, readInt47);
                    return true;
                case 38:
                    int readInt48 = parcel.readInt();
                    android.hardware.radio.network.EmergencyNetworkScanTrigger emergencyNetworkScanTrigger = (android.hardware.radio.network.EmergencyNetworkScanTrigger) parcel.readTypedObject(android.hardware.radio.network.EmergencyNetworkScanTrigger.CREATOR);
                    parcel.enforceNoDataAvail();
                    triggerEmergencyNetworkScan(readInt48, emergencyNetworkScanTrigger);
                    return true;
                case 39:
                    int readInt49 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelEmergencyNetworkScan(readInt49, readBoolean4);
                    return true;
                case 40:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    exitEmergencyMode(readInt50);
                    return true;
                case 41:
                    int readInt51 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNullCipherAndIntegrityEnabled(readInt51, readBoolean5);
                    return true;
                case 42:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isNullCipherAndIntegrityEnabled(readInt52);
                    return true;
                case 43:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isN1ModeEnabled(readInt53);
                    return true;
                case 44:
                    int readInt54 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setN1ModeEnabled(readInt54, readBoolean6);
                    return true;
                case 45:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isCellularIdentifierTransparencyEnabled(readInt55);
                    return true;
                case 46:
                    int readInt56 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCellularIdentifierTransparencyEnabled(readInt56, readBoolean7);
                    return true;
                case 47:
                    int readInt57 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSecurityAlgorithmsUpdatedEnabled(readInt57, readBoolean8);
                    return true;
                case 48:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isSecurityAlgorithmsUpdatedEnabled(readInt58);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.network.IRadioNetwork {
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

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getAllowedNetworkTypesBitmap(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAllowedNetworkTypesBitmap is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getAvailableBandModes(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAvailableBandModes is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getAvailableNetworks(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getAvailableNetworks is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getBarringInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getBarringInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getCdmaRoamingPreference(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCdmaRoamingPreference is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getCellInfoList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getCellInfoList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getDataRegistrationState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getDataRegistrationState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getImsRegistrationState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getImsRegistrationState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getNetworkSelectionMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getNetworkSelectionMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getOperator(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getOperator is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getSignalStrength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSignalStrength is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getSystemSelectionChannels(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getSystemSelectionChannels is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getVoiceRadioTechnology(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(13, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getVoiceRadioTechnology is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getVoiceRegistrationState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(14, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getVoiceRegistrationState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isNrDualConnectivityEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isNrDualConnectivityEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void responseAcknowledgement() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(16, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setAllowedNetworkTypesBitmap(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(17, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setAllowedNetworkTypesBitmap is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setBandMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(18, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setBandMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setBarringPassword(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(19, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setBarringPassword is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setCdmaRoamingPreference(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(20, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCdmaRoamingPreference is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setCellInfoListRate(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(21, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCellInfoListRate is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setIndicationFilter(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(22, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setIndicationFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeInt(i5);
                    if (!this.mRemote.transact(23, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setLinkCapacityReportingCriteria is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setLocationUpdates(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(24, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setLocationUpdates is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNetworkSelectionModeAutomatic(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(25, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNetworkSelectionModeAutomatic is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNetworkSelectionModeManual(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(26, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNetworkSelectionModeManual is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNrDualConnectivityState(int i, byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(27, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNrDualConnectivityState is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setResponseFunctions(android.hardware.radio.network.IRadioNetworkResponse iRadioNetworkResponse, android.hardware.radio.network.IRadioNetworkIndication iRadioNetworkIndication) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioNetworkResponse);
                    obtain.writeStrongInterface(iRadioNetworkIndication);
                    if (!this.mRemote.transact(28, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSignalStrengthReportingCriteria(int i, android.hardware.radio.network.SignalThresholdInfo[] signalThresholdInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(signalThresholdInfoArr, 0);
                    if (!this.mRemote.transact(29, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSignalStrengthReportingCriteria is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSuppServiceNotifications(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(30, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSuppServiceNotifications is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSystemSelectionChannels(int i, boolean z, android.hardware.radio.network.RadioAccessSpecifier[] radioAccessSpecifierArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(radioAccessSpecifierArr, 0);
                    if (!this.mRemote.transact(31, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSystemSelectionChannels is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void startNetworkScan(int i, android.hardware.radio.network.NetworkScanRequest networkScanRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(networkScanRequest, 0);
                    if (!this.mRemote.transact(32, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method startNetworkScan is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void stopNetworkScan(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(33, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method stopNetworkScan is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void supplyNetworkDepersonalization(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(34, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method supplyNetworkDepersonalization is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setUsageSetting(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(35, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setUsageSetting is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void getUsageSetting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(36, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method getUsageSetting is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setEmergencyMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(37, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setEmergencyMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void triggerEmergencyNetworkScan(int i, android.hardware.radio.network.EmergencyNetworkScanTrigger emergencyNetworkScanTrigger) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(emergencyNetworkScanTrigger, 0);
                    if (!this.mRemote.transact(38, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method triggerEmergencyNetworkScan is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void cancelEmergencyNetworkScan(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(39, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method cancelEmergencyNetworkScan is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void exitEmergencyMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(40, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method exitEmergencyMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setNullCipherAndIntegrityEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(41, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setNullCipherAndIntegrityEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isNullCipherAndIntegrityEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(42, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isNullCipherAndIntegrityEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isN1ModeEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(43, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isN1ModeEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setN1ModeEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(44, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setN1ModeEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isCellularIdentifierTransparencyEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(45, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isCellularIdentifierTransparencyEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setCellularIdentifierTransparencyEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(46, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setCellularIdentifierTransparencyEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void setSecurityAlgorithmsUpdatedEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(47, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setSecurityAlgorithmsUpdatedEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
            public void isSecurityAlgorithmsUpdatedEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(48, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method isSecurityAlgorithmsUpdatedEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetwork
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

            @Override // android.hardware.radio.network.IRadioNetwork
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
