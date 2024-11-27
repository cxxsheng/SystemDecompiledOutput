package android.service.euicc;

/* loaded from: classes3.dex */
public interface IRetainSubscriptionsForFactoryResetCallback extends android.os.IInterface {
    void onComplete(int i) throws android.os.RemoteException;

    public static class Default implements android.service.euicc.IRetainSubscriptionsForFactoryResetCallback {
        @Override // android.service.euicc.IRetainSubscriptionsForFactoryResetCallback
        public void onComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.euicc.IRetainSubscriptionsForFactoryResetCallback {
        public static final java.lang.String DESCRIPTOR = "android.service.euicc.IRetainSubscriptionsForFactoryResetCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.euicc.IRetainSubscriptionsForFactoryResetCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.euicc.IRetainSubscriptionsForFactoryResetCallback)) {
                return (android.service.euicc.IRetainSubscriptionsForFactoryResetCallback) queryLocalInterface;
            }
            return new android.service.euicc.IRetainSubscriptionsForFactoryResetCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onComplete";
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
                    onComplete(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.euicc.IRetainSubscriptionsForFactoryResetCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.euicc.IRetainSubscriptionsForFactoryResetCallback.Stub.DESCRIPTOR;
            }

            @Override // android.service.euicc.IRetainSubscriptionsForFactoryResetCallback
            public void onComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.euicc.IRetainSubscriptionsForFactoryResetCallback.Stub.DESCRIPTOR);
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
