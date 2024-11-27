package android.companion;

/* loaded from: classes.dex */
public interface ISystemDataTransferCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.ISystemDataTransferCallback";

    void onError(java.lang.String str) throws android.os.RemoteException;

    void onResult() throws android.os.RemoteException;

    public static class Default implements android.companion.ISystemDataTransferCallback {
        @Override // android.companion.ISystemDataTransferCallback
        public void onResult() throws android.os.RemoteException {
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onError(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.ISystemDataTransferCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, android.companion.ISystemDataTransferCallback.DESCRIPTOR);
        }

        public static android.companion.ISystemDataTransferCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.ISystemDataTransferCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.ISystemDataTransferCallback)) {
                return (android.companion.ISystemDataTransferCallback) queryLocalInterface;
            }
            return new android.companion.ISystemDataTransferCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResult";
                case 2:
                    return "onError";
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
                parcel.enforceInterface(android.companion.ISystemDataTransferCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.ISystemDataTransferCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onResult();
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.ISystemDataTransferCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.ISystemDataTransferCallback.DESCRIPTOR;
            }

            @Override // android.companion.ISystemDataTransferCallback
            public void onResult() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ISystemDataTransferCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.ISystemDataTransferCallback
            public void onError(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.ISystemDataTransferCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
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
