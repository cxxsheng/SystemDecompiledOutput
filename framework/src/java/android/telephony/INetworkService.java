package android.telephony;

/* loaded from: classes3.dex */
public interface INetworkService extends android.os.IInterface {
    void createNetworkServiceProvider(int i) throws android.os.RemoteException;

    void registerForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException;

    void removeNetworkServiceProvider(int i) throws android.os.RemoteException;

    void requestNetworkRegistrationInfo(int i, int i2, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException;

    void unregisterForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.telephony.INetworkService {
        @Override // android.telephony.INetworkService
        public void createNetworkServiceProvider(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void removeNetworkServiceProvider(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void requestNetworkRegistrationInfo(int i, int i2, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void registerForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.INetworkService
        public void unregisterForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.INetworkService {
        public static final java.lang.String DESCRIPTOR = "android.telephony.INetworkService";
        static final int TRANSACTION_createNetworkServiceProvider = 1;
        static final int TRANSACTION_registerForNetworkRegistrationInfoChanged = 4;
        static final int TRANSACTION_removeNetworkServiceProvider = 2;
        static final int TRANSACTION_requestNetworkRegistrationInfo = 3;
        static final int TRANSACTION_unregisterForNetworkRegistrationInfoChanged = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.INetworkService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.INetworkService)) {
                return (android.telephony.INetworkService) queryLocalInterface;
            }
            return new android.telephony.INetworkService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createNetworkServiceProvider";
                case 2:
                    return "removeNetworkServiceProvider";
                case 3:
                    return "requestNetworkRegistrationInfo";
                case 4:
                    return "registerForNetworkRegistrationInfoChanged";
                case 5:
                    return "unregisterForNetworkRegistrationInfoChanged";
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
                    createNetworkServiceProvider(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeNetworkServiceProvider(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.telephony.INetworkServiceCallback asInterface = android.telephony.INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestNetworkRegistrationInfo(readInt3, readInt4, asInterface);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    android.telephony.INetworkServiceCallback asInterface2 = android.telephony.INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForNetworkRegistrationInfoChanged(readInt5, asInterface2);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    android.telephony.INetworkServiceCallback asInterface3 = android.telephony.INetworkServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForNetworkRegistrationInfoChanged(readInt6, asInterface3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.INetworkService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.INetworkService.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.INetworkService
            public void createNetworkServiceProvider(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.INetworkService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void removeNetworkServiceProvider(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.INetworkService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void requestNetworkRegistrationInfo(int i, int i2, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.INetworkService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iNetworkServiceCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void registerForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.INetworkService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkServiceCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.INetworkService
            public void unregisterForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.INetworkService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetworkServiceCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
