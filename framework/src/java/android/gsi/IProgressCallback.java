package android.gsi;

/* loaded from: classes.dex */
public interface IProgressCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.gsi.IProgressCallback";

    void onProgress(long j, long j2) throws android.os.RemoteException;

    public static class Default implements android.gsi.IProgressCallback {
        @Override // android.gsi.IProgressCallback
        public void onProgress(long j, long j2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.gsi.IProgressCallback {
        static final int TRANSACTION_onProgress = 1;

        public Stub() {
            attachInterface(this, android.gsi.IProgressCallback.DESCRIPTOR);
        }

        public static android.gsi.IProgressCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.gsi.IProgressCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.gsi.IProgressCallback)) {
                return (android.gsi.IProgressCallback) queryLocalInterface;
            }
            return new android.gsi.IProgressCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onProgress";
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
                parcel.enforceInterface(android.gsi.IProgressCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.gsi.IProgressCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onProgress(readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.gsi.IProgressCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.gsi.IProgressCallback.DESCRIPTOR;
            }

            @Override // android.gsi.IProgressCallback
            public void onProgress(long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IProgressCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
