package android.app.blob;

/* loaded from: classes.dex */
public interface IBlobCommitCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.blob.IBlobCommitCallback";

    void onResult(int i) throws android.os.RemoteException;

    public static class Default implements android.app.blob.IBlobCommitCallback {
        @Override // android.app.blob.IBlobCommitCallback
        public void onResult(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.blob.IBlobCommitCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, android.app.blob.IBlobCommitCallback.DESCRIPTOR);
        }

        public static android.app.blob.IBlobCommitCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.blob.IBlobCommitCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.blob.IBlobCommitCallback)) {
                return (android.app.blob.IBlobCommitCallback) queryLocalInterface;
            }
            return new android.app.blob.IBlobCommitCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResult";
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
                parcel.enforceInterface(android.app.blob.IBlobCommitCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.blob.IBlobCommitCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onResult(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.blob.IBlobCommitCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.blob.IBlobCommitCallback.DESCRIPTOR;
            }

            @Override // android.app.blob.IBlobCommitCallback
            public void onResult(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobCommitCallback.DESCRIPTOR);
                    obtain.writeInt(i);
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
