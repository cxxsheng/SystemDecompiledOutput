package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsServiceController extends android.os.IInterface {
    void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException;

    com.android.ims.internal.IImsMMTelFeature createEmergencyMMTelFeature(int i) throws android.os.RemoteException;

    com.android.ims.internal.IImsMMTelFeature createMMTelFeature(int i) throws android.os.RemoteException;

    com.android.ims.internal.IImsRcsFeature createRcsFeature(int i) throws android.os.RemoteException;

    void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException;

    void removeImsFeature(int i, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsServiceController {
        @Override // com.android.ims.internal.IImsServiceController
        public com.android.ims.internal.IImsMMTelFeature createEmergencyMMTelFeature(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsServiceController
        public com.android.ims.internal.IImsMMTelFeature createMMTelFeature(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsServiceController
        public com.android.ims.internal.IImsRcsFeature createRcsFeature(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void removeImsFeature(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsServiceController {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsServiceController";
        static final int TRANSACTION_addFeatureStatusCallback = 5;
        static final int TRANSACTION_createEmergencyMMTelFeature = 1;
        static final int TRANSACTION_createMMTelFeature = 2;
        static final int TRANSACTION_createRcsFeature = 3;
        static final int TRANSACTION_removeFeatureStatusCallback = 6;
        static final int TRANSACTION_removeImsFeature = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsServiceController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsServiceController)) {
                return (com.android.ims.internal.IImsServiceController) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsServiceController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createEmergencyMMTelFeature";
                case 2:
                    return "createMMTelFeature";
                case 3:
                    return "createRcsFeature";
                case 4:
                    return "removeImsFeature";
                case 5:
                    return "addFeatureStatusCallback";
                case 6:
                    return "removeFeatureStatusCallback";
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
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsMMTelFeature createEmergencyMMTelFeature = createEmergencyMMTelFeature(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createEmergencyMMTelFeature);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsMMTelFeature createMMTelFeature = createMMTelFeature(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createMMTelFeature);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.ims.internal.IImsRcsFeature createRcsFeature = createRcsFeature(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createRcsFeature);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeImsFeature(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    com.android.ims.internal.IImsFeatureStatusCallback asInterface = com.android.ims.internal.IImsFeatureStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addFeatureStatusCallback(readInt6, readInt7, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    com.android.ims.internal.IImsFeatureStatusCallback asInterface2 = com.android.ims.internal.IImsFeatureStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeFeatureStatusCallback(readInt8, readInt9, asInterface2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsServiceController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsServiceController
            public com.android.ims.internal.IImsMMTelFeature createEmergencyMMTelFeature(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsMMTelFeature.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceController
            public com.android.ims.internal.IImsMMTelFeature createMMTelFeature(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsMMTelFeature.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceController
            public com.android.ims.internal.IImsRcsFeature createRcsFeature(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.ims.internal.IImsRcsFeature.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceController
            public void removeImsFeature(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceController
            public void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsFeatureStatusCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceController
            public void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceController.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsFeatureStatusCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
