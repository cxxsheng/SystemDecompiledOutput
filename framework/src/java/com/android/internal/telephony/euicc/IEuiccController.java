package com.android.internal.telephony.euicc;

/* loaded from: classes5.dex */
public interface IEuiccController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccController";

    void continueOperation(int i, android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException;

    void deleteSubscription(int i, int i2, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void downloadSubscription(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, java.lang.String str, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void eraseSubscriptions(int i, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void eraseSubscriptionsWithOptions(int i, int i2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    long getAvailableMemoryInBytes(int i, java.lang.String str) throws android.os.RemoteException;

    void getDefaultDownloadableSubscriptionList(int i, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void getDownloadableSubscriptionMetadata(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    java.lang.String getEid(int i, java.lang.String str) throws android.os.RemoteException;

    android.telephony.euicc.EuiccInfo getEuiccInfo(int i) throws android.os.RemoteException;

    int getOtaStatus(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getSupportedCountries(boolean z) throws android.os.RemoteException;

    boolean hasCarrierPrivilegesForPackageOnAnyPhone(java.lang.String str) throws android.os.RemoteException;

    boolean isCompatChangeEnabled(java.lang.String str, long j) throws android.os.RemoteException;

    boolean isPsimConversionSupported(int i) throws android.os.RemoteException;

    boolean isSimPortAvailable(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    boolean isSupportedCountry(java.lang.String str) throws android.os.RemoteException;

    void retainSubscriptionsForFactoryReset(int i, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void setPsimConversionSupportedCarriers(int[] iArr) throws android.os.RemoteException;

    void setSupportedCountries(boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void switchToSubscription(int i, int i2, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void switchToSubscriptionWithPort(int i, int i2, int i3, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void updateSubscriptionNickname(int i, int i2, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.euicc.IEuiccController {
        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void continueOperation(int i, android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void getDownloadableSubscriptionMetadata(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void getDefaultDownloadableSubscriptionList(int i, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public java.lang.String getEid(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public int getOtaStatus(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void downloadSubscription(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, java.lang.String str, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public android.telephony.euicc.EuiccInfo getEuiccInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void deleteSubscription(int i, int i2, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void switchToSubscription(int i, int i2, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void switchToSubscriptionWithPort(int i, int i2, int i3, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void updateSubscriptionNickname(int i, int i2, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void eraseSubscriptions(int i, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void eraseSubscriptionsWithOptions(int i, int i2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void retainSubscriptionsForFactoryReset(int i, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void setSupportedCountries(boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public java.util.List<java.lang.String> getSupportedCountries(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isSupportedCountry(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isSimPortAvailable(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean hasCarrierPrivilegesForPackageOnAnyPhone(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isCompatChangeEnabled(java.lang.String str, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void setPsimConversionSupportedCarriers(int[] iArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isPsimConversionSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public long getAvailableMemoryInBytes(int i, java.lang.String str) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.euicc.IEuiccController {
        static final int TRANSACTION_continueOperation = 1;
        static final int TRANSACTION_deleteSubscription = 8;
        static final int TRANSACTION_downloadSubscription = 6;
        static final int TRANSACTION_eraseSubscriptions = 12;
        static final int TRANSACTION_eraseSubscriptionsWithOptions = 13;
        static final int TRANSACTION_getAvailableMemoryInBytes = 23;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 3;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 4;
        static final int TRANSACTION_getEuiccInfo = 7;
        static final int TRANSACTION_getOtaStatus = 5;
        static final int TRANSACTION_getSupportedCountries = 16;
        static final int TRANSACTION_hasCarrierPrivilegesForPackageOnAnyPhone = 19;
        static final int TRANSACTION_isCompatChangeEnabled = 20;
        static final int TRANSACTION_isPsimConversionSupported = 22;
        static final int TRANSACTION_isSimPortAvailable = 18;
        static final int TRANSACTION_isSupportedCountry = 17;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 14;
        static final int TRANSACTION_setPsimConversionSupportedCarriers = 21;
        static final int TRANSACTION_setSupportedCountries = 15;
        static final int TRANSACTION_switchToSubscription = 9;
        static final int TRANSACTION_switchToSubscriptionWithPort = 10;
        static final int TRANSACTION_updateSubscriptionNickname = 11;

        public Stub() {
            attachInterface(this, com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
        }

        public static com.android.internal.telephony.euicc.IEuiccController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.euicc.IEuiccController)) {
                return (com.android.internal.telephony.euicc.IEuiccController) queryLocalInterface;
            }
            return new com.android.internal.telephony.euicc.IEuiccController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "continueOperation";
                case 2:
                    return "getDownloadableSubscriptionMetadata";
                case 3:
                    return "getDefaultDownloadableSubscriptionList";
                case 4:
                    return "getEid";
                case 5:
                    return "getOtaStatus";
                case 6:
                    return "downloadSubscription";
                case 7:
                    return "getEuiccInfo";
                case 8:
                    return "deleteSubscription";
                case 9:
                    return "switchToSubscription";
                case 10:
                    return "switchToSubscriptionWithPort";
                case 11:
                    return "updateSubscriptionNickname";
                case 12:
                    return "eraseSubscriptions";
                case 13:
                    return "eraseSubscriptionsWithOptions";
                case 14:
                    return "retainSubscriptionsForFactoryReset";
                case 15:
                    return "setSupportedCountries";
                case 16:
                    return "getSupportedCountries";
                case 17:
                    return "isSupportedCountry";
                case 18:
                    return "isSimPortAvailable";
                case 19:
                    return "hasCarrierPrivilegesForPackageOnAnyPhone";
                case 20:
                    return "isCompatChangeEnabled";
                case 21:
                    return "setPsimConversionSupportedCarriers";
                case 22:
                    return "isPsimConversionSupported";
                case 23:
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
                parcel.enforceInterface(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    continueOperation(readInt, intent, bundle);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.telephony.euicc.DownloadableSubscription downloadableSubscription = (android.telephony.euicc.DownloadableSubscription) parcel.readTypedObject(android.telephony.euicc.DownloadableSubscription.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDownloadableSubscriptionMetadata(readInt2, downloadableSubscription, readString, pendingIntent);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDefaultDownloadableSubscriptionList(readInt3, readString2, pendingIntent2);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String eid = getEid(readInt4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeString(eid);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int otaStatus = getOtaStatus(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(otaStatus);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    android.telephony.euicc.DownloadableSubscription downloadableSubscription2 = (android.telephony.euicc.DownloadableSubscription) parcel.readTypedObject(android.telephony.euicc.DownloadableSubscription.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.PendingIntent pendingIntent3 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    downloadSubscription(readInt6, downloadableSubscription2, readBoolean, readString4, bundle2, pendingIntent3);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.euicc.EuiccInfo euiccInfo = getEuiccInfo(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(euiccInfo, 1);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    android.app.PendingIntent pendingIntent4 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSubscription(readInt8, readInt9, readString5, pendingIntent4);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    android.app.PendingIntent pendingIntent5 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToSubscription(readInt10, readInt11, readString6, pendingIntent5);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    android.app.PendingIntent pendingIntent6 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToSubscriptionWithPort(readInt12, readInt13, readInt14, readString7, pendingIntent6);
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    android.app.PendingIntent pendingIntent7 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSubscriptionNickname(readInt15, readInt16, readString8, readString9, pendingIntent7);
                    return true;
                case 12:
                    int readInt17 = parcel.readInt();
                    android.app.PendingIntent pendingIntent8 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    eraseSubscriptions(readInt17, pendingIntent8);
                    return true;
                case 13:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    android.app.PendingIntent pendingIntent9 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    eraseSubscriptionsWithOptions(readInt18, readInt19, pendingIntent9);
                    return true;
                case 14:
                    int readInt20 = parcel.readInt();
                    android.app.PendingIntent pendingIntent10 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    retainSubscriptionsForFactoryReset(readInt20, pendingIntent10);
                    return true;
                case 15:
                    boolean readBoolean2 = parcel.readBoolean();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setSupportedCountries(readBoolean2, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> supportedCountries = getSupportedCountries(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportedCountries);
                    return true;
                case 17:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSupportedCountry = isSupportedCountry(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportedCountry);
                    return true;
                case 18:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSimPortAvailable = isSimPortAvailable(readInt21, readInt22, readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSimPortAvailable);
                    return true;
                case 19:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCarrierPrivilegesForPackageOnAnyPhone = hasCarrierPrivilegesForPackageOnAnyPhone(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCarrierPrivilegesForPackageOnAnyPhone);
                    return true;
                case 20:
                    java.lang.String readString13 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isCompatChangeEnabled = isCompatChangeEnabled(readString13, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCompatChangeEnabled);
                    return true;
                case 21:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setPsimConversionSupportedCarriers(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPsimConversionSupported = isPsimConversionSupported(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPsimConversionSupported);
                    return true;
                case 23:
                    int readInt24 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long availableMemoryInBytes = getAvailableMemoryInBytes(readInt24, readString14);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableMemoryInBytes);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.euicc.IEuiccController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void continueOperation(int i, android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void getDownloadableSubscriptionMetadata(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void getDefaultDownloadableSubscriptionList(int i, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public java.lang.String getEid(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public int getOtaStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void downloadSubscription(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription, boolean z, java.lang.String str, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public android.telephony.euicc.EuiccInfo getEuiccInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.euicc.EuiccInfo) obtain2.readTypedObject(android.telephony.euicc.EuiccInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void deleteSubscription(int i, int i2, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void switchToSubscription(int i, int i2, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void switchToSubscriptionWithPort(int i, int i2, int i3, java.lang.String str, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void updateSubscriptionNickname(int i, int i2, java.lang.String str, java.lang.String str2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void eraseSubscriptions(int i, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void eraseSubscriptionsWithOptions(int i, int i2, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void retainSubscriptionsForFactoryReset(int i, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void setSupportedCountries(boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public java.util.List<java.lang.String> getSupportedCountries(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isSupportedCountry(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isSimPortAvailable(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean hasCarrierPrivilegesForPackageOnAnyPhone(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isCompatChangeEnabled(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void setPsimConversionSupportedCarriers(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isPsimConversionSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public long getAvailableMemoryInBytes(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.euicc.IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
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
