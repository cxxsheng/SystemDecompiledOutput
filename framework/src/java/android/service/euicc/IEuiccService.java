package android.service.euicc;

/* loaded from: classes3.dex */
public interface IEuiccService extends android.os.IInterface {
    void deleteSubscription(int i, java.lang.String str, android.service.euicc.IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws android.os.RemoteException;

    void downloadSubscription(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.os.Bundle bundle, android.service.euicc.IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws android.os.RemoteException;

    void dump(android.service.euicc.IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws android.os.RemoteException;

    void eraseSubscriptions(int i, android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws android.os.RemoteException;

    void eraseSubscriptionsWithOptions(int i, int i2, android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws android.os.RemoteException;

    void getAvailableMemoryInBytes(int i, android.service.euicc.IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws android.os.RemoteException;

    void getDefaultDownloadableSubscriptionList(int i, boolean z, android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws android.os.RemoteException;

    void getDownloadableSubscriptionMetadata(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.service.euicc.IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws android.os.RemoteException;

    void getEid(int i, android.service.euicc.IGetEidCallback iGetEidCallback) throws android.os.RemoteException;

    void getEuiccInfo(int i, android.service.euicc.IGetEuiccInfoCallback iGetEuiccInfoCallback) throws android.os.RemoteException;

    void getEuiccProfileInfoList(int i, android.service.euicc.IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws android.os.RemoteException;

    void getOtaStatus(int i, android.service.euicc.IGetOtaStatusCallback iGetOtaStatusCallback) throws android.os.RemoteException;

    void retainSubscriptionsForFactoryReset(int i, android.service.euicc.IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws android.os.RemoteException;

    void startOtaIfNecessary(int i, android.service.euicc.IOtaStatusChangedCallback iOtaStatusChangedCallback) throws android.os.RemoteException;

    void switchToSubscription(int i, int i2, java.lang.String str, boolean z, android.service.euicc.ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws android.os.RemoteException;

    void updateSubscriptionNickname(int i, java.lang.String str, java.lang.String str2, android.service.euicc.IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws android.os.RemoteException;

    public static class Default implements android.service.euicc.IEuiccService {
        @Override // android.service.euicc.IEuiccService
        public void downloadSubscription(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.os.Bundle bundle, android.service.euicc.IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getDownloadableSubscriptionMetadata(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.service.euicc.IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getEid(int i, android.service.euicc.IGetEidCallback iGetEidCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getOtaStatus(int i, android.service.euicc.IGetOtaStatusCallback iGetOtaStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void startOtaIfNecessary(int i, android.service.euicc.IOtaStatusChangedCallback iOtaStatusChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccProfileInfoList(int i, android.service.euicc.IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getDefaultDownloadableSubscriptionList(int i, boolean z, android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccInfo(int i, android.service.euicc.IGetEuiccInfoCallback iGetEuiccInfoCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void deleteSubscription(int i, java.lang.String str, android.service.euicc.IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void switchToSubscription(int i, int i2, java.lang.String str, boolean z, android.service.euicc.ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void updateSubscriptionNickname(int i, java.lang.String str, java.lang.String str2, android.service.euicc.IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptions(int i, android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptionsWithOptions(int i, int i2, android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void retainSubscriptionsForFactoryReset(int i, android.service.euicc.IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void dump(android.service.euicc.IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws android.os.RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getAvailableMemoryInBytes(int i, android.service.euicc.IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.euicc.IEuiccService {
        public static final java.lang.String DESCRIPTOR = "android.service.euicc.IEuiccService";
        static final int TRANSACTION_deleteSubscription = 9;
        static final int TRANSACTION_downloadSubscription = 1;
        static final int TRANSACTION_dump = 15;
        static final int TRANSACTION_eraseSubscriptions = 12;
        static final int TRANSACTION_eraseSubscriptionsWithOptions = 13;
        static final int TRANSACTION_getAvailableMemoryInBytes = 16;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 7;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 3;
        static final int TRANSACTION_getEuiccInfo = 8;
        static final int TRANSACTION_getEuiccProfileInfoList = 6;
        static final int TRANSACTION_getOtaStatus = 4;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 14;
        static final int TRANSACTION_startOtaIfNecessary = 5;
        static final int TRANSACTION_switchToSubscription = 10;
        static final int TRANSACTION_updateSubscriptionNickname = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.euicc.IEuiccService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.euicc.IEuiccService)) {
                return (android.service.euicc.IEuiccService) queryLocalInterface;
            }
            return new android.service.euicc.IEuiccService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "downloadSubscription";
                case 2:
                    return "getDownloadableSubscriptionMetadata";
                case 3:
                    return "getEid";
                case 4:
                    return "getOtaStatus";
                case 5:
                    return "startOtaIfNecessary";
                case 6:
                    return "getEuiccProfileInfoList";
                case 7:
                    return "getDefaultDownloadableSubscriptionList";
                case 8:
                    return "getEuiccInfo";
                case 9:
                    return "deleteSubscription";
                case 10:
                    return "switchToSubscription";
                case 11:
                    return "updateSubscriptionNickname";
                case 12:
                    return "eraseSubscriptions";
                case 13:
                    return "eraseSubscriptionsWithOptions";
                case 14:
                    return "retainSubscriptionsForFactoryReset";
                case 15:
                    return "dump";
                case 16:
                    return "getAvailableMemoryInBytes";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.telephony.euicc.DownloadableSubscription downloadableSubscription = (android.telephony.euicc.DownloadableSubscription) parcel.readTypedObject(android.telephony.euicc.DownloadableSubscription.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.service.euicc.IDownloadSubscriptionCallback asInterface = android.service.euicc.IDownloadSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    downloadSubscription(readInt, readInt2, downloadableSubscription, readBoolean, readBoolean2, bundle, asInterface);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.telephony.euicc.DownloadableSubscription downloadableSubscription2 = (android.telephony.euicc.DownloadableSubscription) parcel.readTypedObject(android.telephony.euicc.DownloadableSubscription.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    android.service.euicc.IGetDownloadableSubscriptionMetadataCallback asInterface2 = android.service.euicc.IGetDownloadableSubscriptionMetadataCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDownloadableSubscriptionMetadata(readInt3, readInt4, downloadableSubscription2, readBoolean3, readBoolean4, asInterface2);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    android.service.euicc.IGetEidCallback asInterface3 = android.service.euicc.IGetEidCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEid(readInt5, asInterface3);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    android.service.euicc.IGetOtaStatusCallback asInterface4 = android.service.euicc.IGetOtaStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getOtaStatus(readInt6, asInterface4);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    android.service.euicc.IOtaStatusChangedCallback asInterface5 = android.service.euicc.IOtaStatusChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startOtaIfNecessary(readInt7, asInterface5);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    android.service.euicc.IGetEuiccProfileInfoListCallback asInterface6 = android.service.euicc.IGetEuiccProfileInfoListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccProfileInfoList(readInt8, asInterface6);
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback asInterface7 = android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultDownloadableSubscriptionList(readInt9, readBoolean5, asInterface7);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    android.service.euicc.IGetEuiccInfoCallback asInterface8 = android.service.euicc.IGetEuiccInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo(readInt10, asInterface8);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.service.euicc.IDeleteSubscriptionCallback asInterface9 = android.service.euicc.IDeleteSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteSubscription(readInt11, readString, asInterface9);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    android.service.euicc.ISwitchToSubscriptionCallback asInterface10 = android.service.euicc.ISwitchToSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    switchToSubscription(readInt12, readInt13, readString2, readBoolean6, asInterface10, readBoolean7);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    android.service.euicc.IUpdateSubscriptionNicknameCallback asInterface11 = android.service.euicc.IUpdateSubscriptionNicknameCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSubscriptionNickname(readInt14, readString3, readString4, asInterface11);
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    android.service.euicc.IEraseSubscriptionsCallback asInterface12 = android.service.euicc.IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    eraseSubscriptions(readInt15, asInterface12);
                    return true;
                case 13:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    android.service.euicc.IEraseSubscriptionsCallback asInterface13 = android.service.euicc.IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    eraseSubscriptionsWithOptions(readInt16, readInt17, asInterface13);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    android.service.euicc.IRetainSubscriptionsForFactoryResetCallback asInterface14 = android.service.euicc.IRetainSubscriptionsForFactoryResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retainSubscriptionsForFactoryReset(readInt18, asInterface14);
                    return true;
                case 15:
                    android.service.euicc.IEuiccServiceDumpResultCallback asInterface15 = android.service.euicc.IEuiccServiceDumpResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dump(asInterface15);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    android.service.euicc.IGetAvailableMemoryInBytesCallback asInterface16 = android.service.euicc.IGetAvailableMemoryInBytesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAvailableMemoryInBytes(readInt19, asInterface16);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.euicc.IEuiccService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.euicc.IEuiccService.Stub.DESCRIPTOR;
            }

            @Override // android.service.euicc.IEuiccService
            public void downloadSubscription(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.os.Bundle bundle, android.service.euicc.IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iDownloadSubscriptionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getDownloadableSubscriptionMetadata(int i, int i2, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, boolean z2, android.service.euicc.IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iGetDownloadableSubscriptionMetadataCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEid(int i, android.service.euicc.IGetEidCallback iGetEidCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetEidCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getOtaStatus(int i, android.service.euicc.IGetOtaStatusCallback iGetOtaStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetOtaStatusCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void startOtaIfNecessary(int i, android.service.euicc.IOtaStatusChangedCallback iOtaStatusChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOtaStatusChangedCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEuiccProfileInfoList(int i, android.service.euicc.IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetEuiccProfileInfoListCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getDefaultDownloadableSubscriptionList(int i, boolean z, android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iGetDefaultDownloadableSubscriptionListCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEuiccInfo(int i, android.service.euicc.IGetEuiccInfoCallback iGetEuiccInfoCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetEuiccInfoCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void deleteSubscription(int i, java.lang.String str, android.service.euicc.IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iDeleteSubscriptionCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void switchToSubscription(int i, int i2, java.lang.String str, boolean z, android.service.euicc.ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSwitchToSubscriptionCallback);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void updateSubscriptionNickname(int i, java.lang.String str, java.lang.String str2, android.service.euicc.IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iUpdateSubscriptionNicknameCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void eraseSubscriptions(int i, android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iEraseSubscriptionsCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void eraseSubscriptionsWithOptions(int i, int i2, android.service.euicc.IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iEraseSubscriptionsCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void retainSubscriptionsForFactoryReset(int i, android.service.euicc.IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRetainSubscriptionsForFactoryResetCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void dump(android.service.euicc.IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iEuiccServiceDumpResultCallback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getAvailableMemoryInBytes(int i, android.service.euicc.IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IEuiccService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetAvailableMemoryInBytesCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
