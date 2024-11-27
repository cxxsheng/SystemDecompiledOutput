package android.adbroot;

/* loaded from: classes.dex */
public interface IADBRootService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.adbroot.IADBRootService";

    boolean getEnabled() throws android.os.RemoteException;

    boolean isSupported() throws android.os.RemoteException;

    void setEnabled(boolean z) throws android.os.RemoteException;

    public static class Default implements android.adbroot.IADBRootService {
        @Override // android.adbroot.IADBRootService
        public boolean isSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.adbroot.IADBRootService
        public void setEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.adbroot.IADBRootService
        public boolean getEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.adbroot.IADBRootService {
        static final int TRANSACTION_getEnabled = 3;
        static final int TRANSACTION_isSupported = 1;
        static final int TRANSACTION_setEnabled = 2;

        public Stub() {
            attachInterface(this, android.adbroot.IADBRootService.DESCRIPTOR);
        }

        public static android.adbroot.IADBRootService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.adbroot.IADBRootService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.adbroot.IADBRootService)) {
                return (android.adbroot.IADBRootService) queryLocalInterface;
            }
            return new android.adbroot.IADBRootService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isSupported";
                case 2:
                    return "setEnabled";
                case 3:
                    return "getEnabled";
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
                parcel.enforceInterface(android.adbroot.IADBRootService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.adbroot.IADBRootService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isSupported = isSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupported);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean enabled = getEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.adbroot.IADBRootService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.adbroot.IADBRootService.DESCRIPTOR;
            }

            @Override // android.adbroot.IADBRootService
            public boolean isSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.adbroot.IADBRootService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.adbroot.IADBRootService
            public void setEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.adbroot.IADBRootService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.adbroot.IADBRootService
            public boolean getEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.adbroot.IADBRootService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
