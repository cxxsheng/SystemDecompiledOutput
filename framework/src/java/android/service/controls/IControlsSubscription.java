package android.service.controls;

/* loaded from: classes3.dex */
public interface IControlsSubscription extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.controls.IControlsSubscription";

    void cancel() throws android.os.RemoteException;

    void request(long j) throws android.os.RemoteException;

    public static class Default implements android.service.controls.IControlsSubscription {
        @Override // android.service.controls.IControlsSubscription
        public void request(long j) throws android.os.RemoteException {
        }

        @Override // android.service.controls.IControlsSubscription
        public void cancel() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.controls.IControlsSubscription {
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_request = 1;

        public Stub() {
            attachInterface(this, android.service.controls.IControlsSubscription.DESCRIPTOR);
        }

        public static android.service.controls.IControlsSubscription asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.controls.IControlsSubscription.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.controls.IControlsSubscription)) {
                return (android.service.controls.IControlsSubscription) queryLocalInterface;
            }
            return new android.service.controls.IControlsSubscription.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "request";
                case 2:
                    return "cancel";
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
                parcel.enforceInterface(android.service.controls.IControlsSubscription.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.controls.IControlsSubscription.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    request(readLong);
                    return true;
                case 2:
                    cancel();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.controls.IControlsSubscription {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.controls.IControlsSubscription.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsSubscription
            public void request(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsSubscription.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscription
            public void cancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.controls.IControlsSubscription.DESCRIPTOR);
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
