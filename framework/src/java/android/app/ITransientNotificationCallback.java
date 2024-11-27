package android.app;

/* loaded from: classes.dex */
public interface ITransientNotificationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ITransientNotificationCallback";

    void onToastHidden() throws android.os.RemoteException;

    void onToastShown() throws android.os.RemoteException;

    public static class Default implements android.app.ITransientNotificationCallback {
        @Override // android.app.ITransientNotificationCallback
        public void onToastShown() throws android.os.RemoteException {
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastHidden() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ITransientNotificationCallback {
        static final int TRANSACTION_onToastHidden = 2;
        static final int TRANSACTION_onToastShown = 1;

        public Stub() {
            attachInterface(this, android.app.ITransientNotificationCallback.DESCRIPTOR);
        }

        public static android.app.ITransientNotificationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ITransientNotificationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ITransientNotificationCallback)) {
                return (android.app.ITransientNotificationCallback) queryLocalInterface;
            }
            return new android.app.ITransientNotificationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onToastShown";
                case 2:
                    return "onToastHidden";
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
                parcel.enforceInterface(android.app.ITransientNotificationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ITransientNotificationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onToastShown();
                    return true;
                case 2:
                    onToastHidden();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ITransientNotificationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ITransientNotificationCallback.DESCRIPTOR;
            }

            @Override // android.app.ITransientNotificationCallback
            public void onToastShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITransientNotificationCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITransientNotificationCallback
            public void onToastHidden() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ITransientNotificationCallback.DESCRIPTOR);
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
