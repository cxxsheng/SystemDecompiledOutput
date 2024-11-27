package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsServiceController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsServiceController";

    void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsMmTelFeature createEmergencyOnlyMmTelFeature(int i) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsMmTelFeature createMmTelFeature(int i, int i2) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i, int i2) throws android.os.RemoteException;

    void disableIms(int i, int i2) throws android.os.RemoteException;

    void enableIms(int i, int i2) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsConfig getConfig(int i, int i2) throws android.os.RemoteException;

    long getImsServiceCapabilities() throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsRegistration getRegistration(int i, int i2) throws android.os.RemoteException;

    android.telephony.ims.aidl.ISipTransport getSipTransport(int i) throws android.os.RemoteException;

    void notifyImsServiceReadyForFeatureCreation() throws android.os.RemoteException;

    android.telephony.ims.stub.ImsFeatureConfiguration querySupportedImsFeatures() throws android.os.RemoteException;

    void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException;

    void removeImsFeature(int i, int i2, boolean z) throws android.os.RemoteException;

    void resetIms(int i, int i2) throws android.os.RemoteException;

    void setListener(android.telephony.ims.aidl.IImsServiceControllerListener iImsServiceControllerListener) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsServiceController {
        @Override // android.telephony.ims.aidl.IImsServiceController
        public void setListener(android.telephony.ims.aidl.IImsServiceControllerListener iImsServiceControllerListener) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsMmTelFeature createMmTelFeature(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsMmTelFeature createEmergencyOnlyMmTelFeature(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.stub.ImsFeatureConfiguration querySupportedImsFeatures() throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public long getImsServiceCapabilities() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void notifyImsServiceReadyForFeatureCreation() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeImsFeature(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsConfig getConfig(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.IImsRegistration getRegistration(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public android.telephony.ims.aidl.ISipTransport getSipTransport(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void enableIms(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void disableIms(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void resetIms(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsServiceController {
        static final int TRANSACTION_addFeatureStatusCallback = 7;
        static final int TRANSACTION_createEmergencyOnlyMmTelFeature = 3;
        static final int TRANSACTION_createMmTelFeature = 2;
        static final int TRANSACTION_createRcsFeature = 4;
        static final int TRANSACTION_disableIms = 15;
        static final int TRANSACTION_enableIms = 14;
        static final int TRANSACTION_getConfig = 11;
        static final int TRANSACTION_getImsServiceCapabilities = 6;
        static final int TRANSACTION_getRegistration = 12;
        static final int TRANSACTION_getSipTransport = 13;
        static final int TRANSACTION_notifyImsServiceReadyForFeatureCreation = 9;
        static final int TRANSACTION_querySupportedImsFeatures = 5;
        static final int TRANSACTION_removeFeatureStatusCallback = 8;
        static final int TRANSACTION_removeImsFeature = 10;
        static final int TRANSACTION_resetIms = 16;
        static final int TRANSACTION_setListener = 1;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsServiceController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsServiceController)) {
                return (android.telephony.ims.aidl.IImsServiceController) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsServiceController.Stub.Proxy(iBinder);
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
                    return "createMmTelFeature";
                case 3:
                    return "createEmergencyOnlyMmTelFeature";
                case 4:
                    return "createRcsFeature";
                case 5:
                    return "querySupportedImsFeatures";
                case 6:
                    return "getImsServiceCapabilities";
                case 7:
                    return "addFeatureStatusCallback";
                case 8:
                    return "removeFeatureStatusCallback";
                case 9:
                    return "notifyImsServiceReadyForFeatureCreation";
                case 10:
                    return "removeImsFeature";
                case 11:
                    return "getConfig";
                case 12:
                    return "getRegistration";
                case 13:
                    return "getSipTransport";
                case 14:
                    return "enableIms";
                case 15:
                    return "disableIms";
                case 16:
                    return "resetIms";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.aidl.IImsServiceControllerListener asInterface = android.telephony.ims.aidl.IImsServiceControllerListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsMmTelFeature createMmTelFeature = createMmTelFeature(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createMmTelFeature);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsMmTelFeature createEmergencyOnlyMmTelFeature = createEmergencyOnlyMmTelFeature(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createEmergencyOnlyMmTelFeature);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsRcsFeature createRcsFeature = createRcsFeature(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createRcsFeature);
                    return true;
                case 5:
                    android.telephony.ims.stub.ImsFeatureConfiguration querySupportedImsFeatures = querySupportedImsFeatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(querySupportedImsFeatures, 1);
                    return true;
                case 6:
                    long imsServiceCapabilities = getImsServiceCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeLong(imsServiceCapabilities);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    com.android.ims.internal.IImsFeatureStatusCallback asInterface2 = com.android.ims.internal.IImsFeatureStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addFeatureStatusCallback(readInt6, readInt7, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    com.android.ims.internal.IImsFeatureStatusCallback asInterface3 = com.android.ims.internal.IImsFeatureStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeFeatureStatusCallback(readInt8, readInt9, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    notifyImsServiceReadyForFeatureCreation();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    removeImsFeature(readInt10, readInt11, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsConfig config = getConfig(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(config);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsRegistration registration = getRegistration(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(registration);
                    return true;
                case 13:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.ISipTransport sipTransport = getSipTransport(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sipTransport);
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIms(readInt17, readInt18);
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableIms(readInt19, readInt20);
                    return true;
                case 16:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetIms(readInt21, readInt22);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsServiceController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void setListener(android.telephony.ims.aidl.IImsServiceControllerListener iImsServiceControllerListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsServiceControllerListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.aidl.IImsMmTelFeature createMmTelFeature(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsMmTelFeature.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.aidl.IImsMmTelFeature createEmergencyOnlyMmTelFeature(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsMmTelFeature.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsRcsFeature.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.stub.ImsFeatureConfiguration querySupportedImsFeatures() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.stub.ImsFeatureConfiguration) obtain2.readTypedObject(android.telephony.ims.stub.ImsFeatureConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public long getImsServiceCapabilities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsFeatureStatusCallback);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsFeatureStatusCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void notifyImsServiceReadyForFeatureCreation() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void removeImsFeature(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.aidl.IImsConfig getConfig(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsConfig.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.aidl.IImsRegistration getRegistration(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsRegistration.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public android.telephony.ims.aidl.ISipTransport getSipTransport(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.ISipTransport.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void enableIms(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void disableIms(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsServiceController
            public void resetIms(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsServiceController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
