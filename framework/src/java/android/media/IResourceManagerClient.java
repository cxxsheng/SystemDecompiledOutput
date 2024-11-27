package android.media;

/* loaded from: classes2.dex */
public interface IResourceManagerClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IResourceManagerClient";

    java.lang.String getName() throws android.os.RemoteException;

    boolean reclaimResource() throws android.os.RemoteException;

    public static class Default implements android.media.IResourceManagerClient {
        @Override // android.media.IResourceManagerClient
        public boolean reclaimResource() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IResourceManagerClient
        public java.lang.String getName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IResourceManagerClient {
        static final int TRANSACTION_getName = 2;
        static final int TRANSACTION_reclaimResource = 1;

        public Stub() {
            attachInterface(this, android.media.IResourceManagerClient.DESCRIPTOR);
        }

        public static android.media.IResourceManagerClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IResourceManagerClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IResourceManagerClient)) {
                return (android.media.IResourceManagerClient) queryLocalInterface;
            }
            return new android.media.IResourceManagerClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reclaimResource";
                case 2:
                    return "getName";
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
                parcel.enforceInterface(android.media.IResourceManagerClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IResourceManagerClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean reclaimResource = reclaimResource();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reclaimResource);
                    return true;
                case 2:
                    java.lang.String name = getName();
                    parcel2.writeNoException();
                    parcel2.writeString(name);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IResourceManagerClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IResourceManagerClient.DESCRIPTOR;
            }

            @Override // android.media.IResourceManagerClient
            public boolean reclaimResource() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IResourceManagerClient
            public java.lang.String getName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IResourceManagerClient.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
