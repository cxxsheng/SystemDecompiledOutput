package android.app.admin;

/* loaded from: classes.dex */
public interface IKeyguardClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.admin.IKeyguardClient";

    void onCreateKeyguardSurface(android.os.IBinder iBinder, android.app.admin.IKeyguardCallback iKeyguardCallback) throws android.os.RemoteException;

    public static class Default implements android.app.admin.IKeyguardClient {
        @Override // android.app.admin.IKeyguardClient
        public void onCreateKeyguardSurface(android.os.IBinder iBinder, android.app.admin.IKeyguardCallback iKeyguardCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.admin.IKeyguardClient {
        static final int TRANSACTION_onCreateKeyguardSurface = 1;

        public Stub() {
            attachInterface(this, android.app.admin.IKeyguardClient.DESCRIPTOR);
        }

        public static android.app.admin.IKeyguardClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.admin.IKeyguardClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.admin.IKeyguardClient)) {
                return (android.app.admin.IKeyguardClient) queryLocalInterface;
            }
            return new android.app.admin.IKeyguardClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreateKeyguardSurface";
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
                parcel.enforceInterface(android.app.admin.IKeyguardClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.admin.IKeyguardClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.app.admin.IKeyguardCallback asInterface = android.app.admin.IKeyguardCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCreateKeyguardSurface(readStrongBinder, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.admin.IKeyguardClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.admin.IKeyguardClient.DESCRIPTOR;
            }

            @Override // android.app.admin.IKeyguardClient
            public void onCreateKeyguardSurface(android.os.IBinder iBinder, android.app.admin.IKeyguardCallback iKeyguardCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.admin.IKeyguardClient.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iKeyguardCallback);
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
