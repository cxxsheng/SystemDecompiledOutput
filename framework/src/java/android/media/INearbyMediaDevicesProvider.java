package android.media;

/* loaded from: classes2.dex */
public interface INearbyMediaDevicesProvider extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.INearbyMediaDevicesProvider";

    void registerNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws android.os.RemoteException;

    void unregisterNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws android.os.RemoteException;

    public static class Default implements android.media.INearbyMediaDevicesProvider {
        @Override // android.media.INearbyMediaDevicesProvider
        public void registerNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws android.os.RemoteException {
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void unregisterNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.INearbyMediaDevicesProvider {
        static final int TRANSACTION_registerNearbyDevicesCallback = 3;
        static final int TRANSACTION_unregisterNearbyDevicesCallback = 4;

        public Stub() {
            attachInterface(this, android.media.INearbyMediaDevicesProvider.DESCRIPTOR);
        }

        public static android.media.INearbyMediaDevicesProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.INearbyMediaDevicesProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.INearbyMediaDevicesProvider)) {
                return (android.media.INearbyMediaDevicesProvider) queryLocalInterface;
            }
            return new android.media.INearbyMediaDevicesProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 3:
                    return "registerNearbyDevicesCallback";
                case 4:
                    return "unregisterNearbyDevicesCallback";
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
                parcel.enforceInterface(android.media.INearbyMediaDevicesProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.INearbyMediaDevicesProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 3:
                    android.media.INearbyMediaDevicesUpdateCallback asInterface = android.media.INearbyMediaDevicesUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyDevicesCallback(asInterface);
                    return true;
                case 4:
                    android.media.INearbyMediaDevicesUpdateCallback asInterface2 = android.media.INearbyMediaDevicesUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyDevicesCallback(asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.INearbyMediaDevicesProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.INearbyMediaDevicesProvider.DESCRIPTOR;
            }

            @Override // android.media.INearbyMediaDevicesProvider
            public void registerNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.INearbyMediaDevicesProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesUpdateCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.INearbyMediaDevicesProvider
            public void unregisterNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.INearbyMediaDevicesProvider.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesUpdateCallback);
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
