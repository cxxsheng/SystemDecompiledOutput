package android.security.rkp;

/* loaded from: classes3.dex */
public interface IRemoteProvisioning extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.rkp.IRemoteProvisioning";

    void getRegistration(java.lang.String str, android.security.rkp.IGetRegistrationCallback iGetRegistrationCallback) throws android.os.RemoteException;

    public static class Default implements android.security.rkp.IRemoteProvisioning {
        @Override // android.security.rkp.IRemoteProvisioning
        public void getRegistration(java.lang.String str, android.security.rkp.IGetRegistrationCallback iGetRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.rkp.IRemoteProvisioning {
        static final int TRANSACTION_getRegistration = 1;

        public Stub() {
            attachInterface(this, android.security.rkp.IRemoteProvisioning.DESCRIPTOR);
        }

        public static android.security.rkp.IRemoteProvisioning asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.rkp.IRemoteProvisioning.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.rkp.IRemoteProvisioning)) {
                return (android.security.rkp.IRemoteProvisioning) queryLocalInterface;
            }
            return new android.security.rkp.IRemoteProvisioning.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getRegistration";
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
                parcel.enforceInterface(android.security.rkp.IRemoteProvisioning.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.rkp.IRemoteProvisioning.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.security.rkp.IGetRegistrationCallback asInterface = android.security.rkp.IGetRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getRegistration(readString, asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.rkp.IRemoteProvisioning {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.rkp.IRemoteProvisioning.DESCRIPTOR;
            }

            @Override // android.security.rkp.IRemoteProvisioning
            public void getRegistration(java.lang.String str, android.security.rkp.IGetRegistrationCallback iGetRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IRemoteProvisioning.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iGetRegistrationCallback);
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
