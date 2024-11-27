package android.service.contentcapture;

/* loaded from: classes3.dex */
public interface IDataShareCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.contentcapture.IDataShareCallback";

    void accept(android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter) throws android.os.RemoteException;

    void reject() throws android.os.RemoteException;

    public static class Default implements android.service.contentcapture.IDataShareCallback {
        @Override // android.service.contentcapture.IDataShareCallback
        public void accept(android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter) throws android.os.RemoteException {
        }

        @Override // android.service.contentcapture.IDataShareCallback
        public void reject() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.contentcapture.IDataShareCallback {
        static final int TRANSACTION_accept = 1;
        static final int TRANSACTION_reject = 2;

        public Stub() {
            attachInterface(this, android.service.contentcapture.IDataShareCallback.DESCRIPTOR);
        }

        public static android.service.contentcapture.IDataShareCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.contentcapture.IDataShareCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.contentcapture.IDataShareCallback)) {
                return (android.service.contentcapture.IDataShareCallback) queryLocalInterface;
            }
            return new android.service.contentcapture.IDataShareCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "accept";
                case 2:
                    return "reject";
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
                parcel.enforceInterface(android.service.contentcapture.IDataShareCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.contentcapture.IDataShareCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.contentcapture.IDataShareReadAdapter asInterface = android.service.contentcapture.IDataShareReadAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    accept(asInterface);
                    return true;
                case 2:
                    reject();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.contentcapture.IDataShareCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.contentcapture.IDataShareCallback.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IDataShareCallback
            public void accept(android.service.contentcapture.IDataShareReadAdapter iDataShareReadAdapter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IDataShareCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iDataShareReadAdapter);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IDataShareCallback
            public void reject() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.contentcapture.IDataShareCallback.DESCRIPTOR);
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
