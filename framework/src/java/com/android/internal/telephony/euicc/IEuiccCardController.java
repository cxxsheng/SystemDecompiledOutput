package com.android.internal.telephony.euicc;

/* loaded from: classes5.dex */
public interface IEuiccCardController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccCardController";

    void authenticateServer(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, com.android.internal.telephony.euicc.IAuthenticateServerCallback iAuthenticateServerCallback) throws android.os.RemoteException;

    void cancelSession(java.lang.String str, java.lang.String str2, byte[] bArr, int i, com.android.internal.telephony.euicc.ICancelSessionCallback iCancelSessionCallback) throws android.os.RemoteException;

    void deleteProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.IDeleteProfileCallback iDeleteProfileCallback) throws android.os.RemoteException;

    void disableProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, com.android.internal.telephony.euicc.IDisableProfileCallback iDisableProfileCallback) throws android.os.RemoteException;

    void getAllProfiles(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetAllProfilesCallback iGetAllProfilesCallback) throws android.os.RemoteException;

    void getDefaultSmdpAddress(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws android.os.RemoteException;

    void getEnabledProfile(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IGetProfileCallback iGetProfileCallback) throws android.os.RemoteException;

    void getEuiccChallenge(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws android.os.RemoteException;

    void getEuiccInfo1(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws android.os.RemoteException;

    void getEuiccInfo2(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws android.os.RemoteException;

    void getProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.IGetProfileCallback iGetProfileCallback) throws android.os.RemoteException;

    void getRulesAuthTable(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws android.os.RemoteException;

    void getSmdsAddress(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetSmdsAddressCallback iGetSmdsAddressCallback) throws android.os.RemoteException;

    void listNotifications(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IListNotificationsCallback iListNotificationsCallback) throws android.os.RemoteException;

    void loadBoundProfilePackage(java.lang.String str, java.lang.String str2, byte[] bArr, com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws android.os.RemoteException;

    void prepareDownload(java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, com.android.internal.telephony.euicc.IPrepareDownloadCallback iPrepareDownloadCallback) throws android.os.RemoteException;

    void removeNotificationFromList(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws android.os.RemoteException;

    void resetMemory(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IResetMemoryCallback iResetMemoryCallback) throws android.os.RemoteException;

    void retrieveNotification(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRetrieveNotificationCallback iRetrieveNotificationCallback) throws android.os.RemoteException;

    void retrieveNotificationList(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws android.os.RemoteException;

    void setDefaultSmdpAddress(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws android.os.RemoteException;

    void setNickname(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, com.android.internal.telephony.euicc.ISetNicknameCallback iSetNicknameCallback) throws android.os.RemoteException;

    void switchToProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, com.android.internal.telephony.euicc.ISwitchToProfileCallback iSwitchToProfileCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.euicc.IEuiccCardController {
        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getAllProfiles(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetAllProfilesCallback iGetAllProfilesCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.IGetProfileCallback iGetProfileCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEnabledProfile(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IGetProfileCallback iGetProfileCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void disableProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, com.android.internal.telephony.euicc.IDisableProfileCallback iDisableProfileCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void switchToProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, com.android.internal.telephony.euicc.ISwitchToProfileCallback iSwitchToProfileCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void setNickname(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, com.android.internal.telephony.euicc.ISetNicknameCallback iSetNicknameCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void deleteProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.IDeleteProfileCallback iDeleteProfileCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void resetMemory(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IResetMemoryCallback iResetMemoryCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getDefaultSmdpAddress(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getSmdsAddress(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetSmdsAddressCallback iGetSmdsAddressCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void setDefaultSmdpAddress(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getRulesAuthTable(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEuiccChallenge(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEuiccInfo1(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void getEuiccInfo2(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void authenticateServer(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, com.android.internal.telephony.euicc.IAuthenticateServerCallback iAuthenticateServerCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void prepareDownload(java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, com.android.internal.telephony.euicc.IPrepareDownloadCallback iPrepareDownloadCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void loadBoundProfilePackage(java.lang.String str, java.lang.String str2, byte[] bArr, com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void cancelSession(java.lang.String str, java.lang.String str2, byte[] bArr, int i, com.android.internal.telephony.euicc.ICancelSessionCallback iCancelSessionCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void listNotifications(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IListNotificationsCallback iListNotificationsCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void retrieveNotificationList(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void retrieveNotification(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRetrieveNotificationCallback iRetrieveNotificationCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccCardController
        public void removeNotificationFromList(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.euicc.IEuiccCardController {
        static final int TRANSACTION_authenticateServer = 16;
        static final int TRANSACTION_cancelSession = 19;
        static final int TRANSACTION_deleteProfile = 7;
        static final int TRANSACTION_disableProfile = 4;
        static final int TRANSACTION_getAllProfiles = 1;
        static final int TRANSACTION_getDefaultSmdpAddress = 9;
        static final int TRANSACTION_getEnabledProfile = 3;
        static final int TRANSACTION_getEuiccChallenge = 13;
        static final int TRANSACTION_getEuiccInfo1 = 14;
        static final int TRANSACTION_getEuiccInfo2 = 15;
        static final int TRANSACTION_getProfile = 2;
        static final int TRANSACTION_getRulesAuthTable = 12;
        static final int TRANSACTION_getSmdsAddress = 10;
        static final int TRANSACTION_listNotifications = 20;
        static final int TRANSACTION_loadBoundProfilePackage = 18;
        static final int TRANSACTION_prepareDownload = 17;
        static final int TRANSACTION_removeNotificationFromList = 23;
        static final int TRANSACTION_resetMemory = 8;
        static final int TRANSACTION_retrieveNotification = 22;
        static final int TRANSACTION_retrieveNotificationList = 21;
        static final int TRANSACTION_setDefaultSmdpAddress = 11;
        static final int TRANSACTION_setNickname = 6;
        static final int TRANSACTION_switchToProfile = 5;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
        }

        public static com.android.internal.telephony.euicc.IEuiccCardController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.euicc.IEuiccCardController)) {
                return (com.android.internal.telephony.euicc.IEuiccCardController) queryLocalInterface;
            }
            return new com.android.internal.telephony.euicc.IEuiccCardController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllProfiles";
                case 2:
                    return "getProfile";
                case 3:
                    return "getEnabledProfile";
                case 4:
                    return "disableProfile";
                case 5:
                    return "switchToProfile";
                case 6:
                    return "setNickname";
                case 7:
                    return "deleteProfile";
                case 8:
                    return "resetMemory";
                case 9:
                    return "getDefaultSmdpAddress";
                case 10:
                    return "getSmdsAddress";
                case 11:
                    return "setDefaultSmdpAddress";
                case 12:
                    return "getRulesAuthTable";
                case 13:
                    return "getEuiccChallenge";
                case 14:
                    return "getEuiccInfo1";
                case 15:
                    return "getEuiccInfo2";
                case 16:
                    return "authenticateServer";
                case 17:
                    return "prepareDownload";
                case 18:
                    return "loadBoundProfilePackage";
                case 19:
                    return "cancelSession";
                case 20:
                    return "listNotifications";
                case 21:
                    return "retrieveNotificationList";
                case 22:
                    return "retrieveNotification";
                case 23:
                    return "removeNotificationFromList";
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
                parcel.enforceInterface(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetAllProfilesCallback asInterface = com.android.internal.telephony.euicc.IGetAllProfilesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAllProfiles(readString, readString2, asInterface);
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetProfileCallback asInterface2 = com.android.internal.telephony.euicc.IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getProfile(readString3, readString4, readString5, asInterface2);
                    return true;
                case 3:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    int readInt = parcel.readInt();
                    com.android.internal.telephony.euicc.IGetProfileCallback asInterface3 = com.android.internal.telephony.euicc.IGetProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEnabledProfile(readString6, readString7, readInt, asInterface3);
                    return true;
                case 4:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    com.android.internal.telephony.euicc.IDisableProfileCallback asInterface4 = com.android.internal.telephony.euicc.IDisableProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disableProfile(readString8, readString9, readString10, readBoolean, asInterface4);
                    return true;
                case 5:
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    com.android.internal.telephony.euicc.ISwitchToProfileCallback asInterface5 = com.android.internal.telephony.euicc.ISwitchToProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    switchToProfile(readString11, readString12, readString13, readInt2, readBoolean2, asInterface5);
                    return true;
                case 6:
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    com.android.internal.telephony.euicc.ISetNicknameCallback asInterface6 = com.android.internal.telephony.euicc.ISetNicknameCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setNickname(readString14, readString15, readString16, readString17, asInterface6);
                    return true;
                case 7:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    com.android.internal.telephony.euicc.IDeleteProfileCallback asInterface7 = com.android.internal.telephony.euicc.IDeleteProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteProfile(readString18, readString19, readString20, asInterface7);
                    return true;
                case 8:
                    java.lang.String readString21 = parcel.readString();
                    java.lang.String readString22 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    com.android.internal.telephony.euicc.IResetMemoryCallback asInterface8 = com.android.internal.telephony.euicc.IResetMemoryCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    resetMemory(readString21, readString22, readInt3, asInterface8);
                    return true;
                case 9:
                    java.lang.String readString23 = parcel.readString();
                    java.lang.String readString24 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback asInterface9 = com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultSmdpAddress(readString23, readString24, asInterface9);
                    return true;
                case 10:
                    java.lang.String readString25 = parcel.readString();
                    java.lang.String readString26 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetSmdsAddressCallback asInterface10 = com.android.internal.telephony.euicc.IGetSmdsAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getSmdsAddress(readString25, readString26, asInterface10);
                    return true;
                case 11:
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback asInterface11 = com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDefaultSmdpAddress(readString27, readString28, readString29, asInterface11);
                    return true;
                case 12:
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetRulesAuthTableCallback asInterface12 = com.android.internal.telephony.euicc.IGetRulesAuthTableCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getRulesAuthTable(readString30, readString31, asInterface12);
                    return true;
                case 13:
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetEuiccChallengeCallback asInterface13 = com.android.internal.telephony.euicc.IGetEuiccChallengeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccChallenge(readString32, readString33, asInterface13);
                    return true;
                case 14:
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetEuiccInfo1Callback asInterface14 = com.android.internal.telephony.euicc.IGetEuiccInfo1Callback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo1(readString34, readString35, asInterface14);
                    return true;
                case 15:
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    com.android.internal.telephony.euicc.IGetEuiccInfo2Callback asInterface15 = com.android.internal.telephony.euicc.IGetEuiccInfo2Callback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo2(readString36, readString37, asInterface15);
                    return true;
                case 16:
                    java.lang.String readString38 = parcel.readString();
                    java.lang.String readString39 = parcel.readString();
                    java.lang.String readString40 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    com.android.internal.telephony.euicc.IAuthenticateServerCallback asInterface16 = com.android.internal.telephony.euicc.IAuthenticateServerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    authenticateServer(readString38, readString39, readString40, createByteArray, createByteArray2, createByteArray3, createByteArray4, asInterface16);
                    return true;
                case 17:
                    java.lang.String readString41 = parcel.readString();
                    java.lang.String readString42 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    byte[] createByteArray7 = parcel.createByteArray();
                    byte[] createByteArray8 = parcel.createByteArray();
                    com.android.internal.telephony.euicc.IPrepareDownloadCallback asInterface17 = com.android.internal.telephony.euicc.IPrepareDownloadCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    prepareDownload(readString41, readString42, createByteArray5, createByteArray6, createByteArray7, createByteArray8, asInterface17);
                    return true;
                case 18:
                    java.lang.String readString43 = parcel.readString();
                    java.lang.String readString44 = parcel.readString();
                    byte[] createByteArray9 = parcel.createByteArray();
                    com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback asInterface18 = com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    loadBoundProfilePackage(readString43, readString44, createByteArray9, asInterface18);
                    return true;
                case 19:
                    java.lang.String readString45 = parcel.readString();
                    java.lang.String readString46 = parcel.readString();
                    byte[] createByteArray10 = parcel.createByteArray();
                    int readInt4 = parcel.readInt();
                    com.android.internal.telephony.euicc.ICancelSessionCallback asInterface19 = com.android.internal.telephony.euicc.ICancelSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelSession(readString45, readString46, createByteArray10, readInt4, asInterface19);
                    return true;
                case 20:
                    java.lang.String readString47 = parcel.readString();
                    java.lang.String readString48 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    com.android.internal.telephony.euicc.IListNotificationsCallback asInterface20 = com.android.internal.telephony.euicc.IListNotificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    listNotifications(readString47, readString48, readInt5, asInterface20);
                    return true;
                case 21:
                    java.lang.String readString49 = parcel.readString();
                    java.lang.String readString50 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    com.android.internal.telephony.euicc.IRetrieveNotificationListCallback asInterface21 = com.android.internal.telephony.euicc.IRetrieveNotificationListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveNotificationList(readString49, readString50, readInt6, asInterface21);
                    return true;
                case 22:
                    java.lang.String readString51 = parcel.readString();
                    java.lang.String readString52 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    com.android.internal.telephony.euicc.IRetrieveNotificationCallback asInterface22 = com.android.internal.telephony.euicc.IRetrieveNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retrieveNotification(readString51, readString52, readInt7, asInterface22);
                    return true;
                case 23:
                    java.lang.String readString53 = parcel.readString();
                    java.lang.String readString54 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback asInterface23 = com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeNotificationFromList(readString53, readString54, readInt8, asInterface23);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.euicc.IEuiccCardController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getAllProfiles(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetAllProfilesCallback iGetAllProfilesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetAllProfilesCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.IGetProfileCallback iGetProfileCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iGetProfileCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEnabledProfile(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IGetProfileCallback iGetProfileCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetProfileCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void disableProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, com.android.internal.telephony.euicc.IDisableProfileCallback iDisableProfileCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDisableProfileCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void switchToProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, boolean z, com.android.internal.telephony.euicc.ISwitchToProfileCallback iSwitchToProfileCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSwitchToProfileCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void setNickname(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, com.android.internal.telephony.euicc.ISetNicknameCallback iSetNicknameCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStrongInterface(iSetNicknameCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void deleteProfile(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.IDeleteProfileCallback iDeleteProfileCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iDeleteProfileCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void resetMemory(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IResetMemoryCallback iResetMemoryCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResetMemoryCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getDefaultSmdpAddress(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetDefaultSmdpAddressCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getSmdsAddress(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetSmdsAddressCallback iGetSmdsAddressCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetSmdsAddressCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void setDefaultSmdpAddress(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iSetDefaultSmdpAddressCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getRulesAuthTable(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetRulesAuthTableCallback iGetRulesAuthTableCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetRulesAuthTableCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccChallenge(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccChallengeCallback iGetEuiccChallengeCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetEuiccChallengeCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccInfo1(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccInfo1Callback iGetEuiccInfo1Callback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetEuiccInfo1Callback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void getEuiccInfo2(java.lang.String str, java.lang.String str2, com.android.internal.telephony.euicc.IGetEuiccInfo2Callback iGetEuiccInfo2Callback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iGetEuiccInfo2Callback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void authenticateServer(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, com.android.internal.telephony.euicc.IAuthenticateServerCallback iAuthenticateServerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeByteArray(bArr4);
                    obtain.writeStrongInterface(iAuthenticateServerCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void prepareDownload(java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, com.android.internal.telephony.euicc.IPrepareDownloadCallback iPrepareDownloadCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeByteArray(bArr4);
                    obtain.writeStrongInterface(iPrepareDownloadCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void loadBoundProfilePackage(java.lang.String str, java.lang.String str2, byte[] bArr, com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iLoadBoundProfilePackageCallback);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void cancelSession(java.lang.String str, java.lang.String str2, byte[] bArr, int i, com.android.internal.telephony.euicc.ICancelSessionCallback iCancelSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCancelSessionCallback);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void listNotifications(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IListNotificationsCallback iListNotificationsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iListNotificationsCallback);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void retrieveNotificationList(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRetrieveNotificationListCallback iRetrieveNotificationListCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRetrieveNotificationListCallback);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void retrieveNotification(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRetrieveNotificationCallback iRetrieveNotificationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRetrieveNotificationCallback);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccCardController
            public void removeNotificationFromList(java.lang.String str, java.lang.String str2, int i, com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccCardController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoveNotificationFromListCallback);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
