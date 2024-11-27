package android.os;

/* loaded from: classes3.dex */
public interface IWakeLockCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IWakeLockCallback";

    void onStateChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.os.IWakeLockCallback {
        @Override // android.os.IWakeLockCallback
        public void onStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IWakeLockCallback {
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, android.os.IWakeLockCallback.DESCRIPTOR);
        }

        public static android.os.IWakeLockCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IWakeLockCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IWakeLockCallback)) {
                return (android.os.IWakeLockCallback) queryLocalInterface;
            }
            return new android.os.IWakeLockCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStateChanged";
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
                parcel.enforceInterface(android.os.IWakeLockCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IWakeLockCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStateChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IWakeLockCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IWakeLockCallback.DESCRIPTOR;
            }

            @Override // android.os.IWakeLockCallback
            public void onStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IWakeLockCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
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