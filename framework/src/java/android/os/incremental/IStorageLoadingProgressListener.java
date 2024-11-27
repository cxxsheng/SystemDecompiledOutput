package android.os.incremental;

/* loaded from: classes3.dex */
public interface IStorageLoadingProgressListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.incremental.IStorageLoadingProgressListener";

    void onStorageLoadingProgressChanged(int i, float f) throws android.os.RemoteException;

    public static class Default implements android.os.incremental.IStorageLoadingProgressListener {
        @Override // android.os.incremental.IStorageLoadingProgressListener
        public void onStorageLoadingProgressChanged(int i, float f) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.incremental.IStorageLoadingProgressListener {
        static final int TRANSACTION_onStorageLoadingProgressChanged = 1;

        public Stub() {
            attachInterface(this, android.os.incremental.IStorageLoadingProgressListener.DESCRIPTOR);
        }

        public static android.os.incremental.IStorageLoadingProgressListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.incremental.IStorageLoadingProgressListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.incremental.IStorageLoadingProgressListener)) {
                return (android.os.incremental.IStorageLoadingProgressListener) queryLocalInterface;
            }
            return new android.os.incremental.IStorageLoadingProgressListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStorageLoadingProgressChanged";
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
                parcel.enforceInterface(android.os.incremental.IStorageLoadingProgressListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.incremental.IStorageLoadingProgressListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onStorageLoadingProgressChanged(readInt, readFloat);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.incremental.IStorageLoadingProgressListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.incremental.IStorageLoadingProgressListener.DESCRIPTOR;
            }

            @Override // android.os.incremental.IStorageLoadingProgressListener
            public void onStorageLoadingProgressChanged(int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IStorageLoadingProgressListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
