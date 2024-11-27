package android.service.attention;

/* loaded from: classes3.dex */
public interface IAttentionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.attention.IAttentionService";

    void cancelAttentionCheck(android.service.attention.IAttentionCallback iAttentionCallback) throws android.os.RemoteException;

    void checkAttention(android.service.attention.IAttentionCallback iAttentionCallback) throws android.os.RemoteException;

    void onStartProximityUpdates(android.service.attention.IProximityUpdateCallback iProximityUpdateCallback) throws android.os.RemoteException;

    void onStopProximityUpdates() throws android.os.RemoteException;

    public static class Default implements android.service.attention.IAttentionService {
        @Override // android.service.attention.IAttentionService
        public void checkAttention(android.service.attention.IAttentionCallback iAttentionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.attention.IAttentionService
        public void cancelAttentionCheck(android.service.attention.IAttentionCallback iAttentionCallback) throws android.os.RemoteException {
        }

        @Override // android.service.attention.IAttentionService
        public void onStartProximityUpdates(android.service.attention.IProximityUpdateCallback iProximityUpdateCallback) throws android.os.RemoteException {
        }

        @Override // android.service.attention.IAttentionService
        public void onStopProximityUpdates() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.attention.IAttentionService {
        static final int TRANSACTION_cancelAttentionCheck = 2;
        static final int TRANSACTION_checkAttention = 1;
        static final int TRANSACTION_onStartProximityUpdates = 3;
        static final int TRANSACTION_onStopProximityUpdates = 4;

        public Stub() {
            attachInterface(this, android.service.attention.IAttentionService.DESCRIPTOR);
        }

        public static android.service.attention.IAttentionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.attention.IAttentionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.attention.IAttentionService)) {
                return (android.service.attention.IAttentionService) queryLocalInterface;
            }
            return new android.service.attention.IAttentionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkAttention";
                case 2:
                    return "cancelAttentionCheck";
                case 3:
                    return "onStartProximityUpdates";
                case 4:
                    return "onStopProximityUpdates";
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
                parcel.enforceInterface(android.service.attention.IAttentionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.attention.IAttentionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.attention.IAttentionCallback asInterface = android.service.attention.IAttentionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    checkAttention(asInterface);
                    return true;
                case 2:
                    android.service.attention.IAttentionCallback asInterface2 = android.service.attention.IAttentionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelAttentionCheck(asInterface2);
                    return true;
                case 3:
                    android.service.attention.IProximityUpdateCallback asInterface3 = android.service.attention.IProximityUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onStartProximityUpdates(asInterface3);
                    return true;
                case 4:
                    onStopProximityUpdates();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.attention.IAttentionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.attention.IAttentionService.DESCRIPTOR;
            }

            @Override // android.service.attention.IAttentionService
            public void checkAttention(android.service.attention.IAttentionCallback iAttentionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.attention.IAttentionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAttentionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void cancelAttentionCheck(android.service.attention.IAttentionCallback iAttentionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.attention.IAttentionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAttentionCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void onStartProximityUpdates(android.service.attention.IProximityUpdateCallback iProximityUpdateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.attention.IAttentionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iProximityUpdateCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.attention.IAttentionService
            public void onStopProximityUpdates() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.attention.IAttentionService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
