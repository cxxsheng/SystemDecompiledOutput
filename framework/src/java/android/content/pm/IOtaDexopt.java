package android.content.pm;

/* loaded from: classes.dex */
public interface IOtaDexopt extends android.os.IInterface {
    void cleanup() throws android.os.RemoteException;

    void dexoptNextPackage() throws android.os.RemoteException;

    float getProgress() throws android.os.RemoteException;

    boolean isDone() throws android.os.RemoteException;

    java.lang.String nextDexoptCommand() throws android.os.RemoteException;

    void prepare() throws android.os.RemoteException;

    public static class Default implements android.content.pm.IOtaDexopt {
        @Override // android.content.pm.IOtaDexopt
        public void prepare() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOtaDexopt
        public void cleanup() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOtaDexopt
        public boolean isDone() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IOtaDexopt
        public float getProgress() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.content.pm.IOtaDexopt
        public void dexoptNextPackage() throws android.os.RemoteException {
        }

        @Override // android.content.pm.IOtaDexopt
        public java.lang.String nextDexoptCommand() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IOtaDexopt {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.IOtaDexopt";
        static final int TRANSACTION_cleanup = 2;
        static final int TRANSACTION_dexoptNextPackage = 5;
        static final int TRANSACTION_getProgress = 4;
        static final int TRANSACTION_isDone = 3;
        static final int TRANSACTION_nextDexoptCommand = 6;
        static final int TRANSACTION_prepare = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.IOtaDexopt asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IOtaDexopt)) {
                return (android.content.pm.IOtaDexopt) queryLocalInterface;
            }
            return new android.content.pm.IOtaDexopt.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "prepare";
                case 2:
                    return "cleanup";
                case 3:
                    return "isDone";
                case 4:
                    return "getProgress";
                case 5:
                    return "dexoptNextPackage";
                case 6:
                    return "nextDexoptCommand";
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
                    prepare();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    cleanup();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isDone = isDone();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDone);
                    return true;
                case 4:
                    float progress = getProgress();
                    parcel2.writeNoException();
                    parcel2.writeFloat(progress);
                    return true;
                case 5:
                    dexoptNextPackage();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String nextDexoptCommand = nextDexoptCommand();
                    parcel2.writeNoException();
                    parcel2.writeString(nextDexoptCommand);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IOtaDexopt {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IOtaDexopt.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.IOtaDexopt
            public void prepare() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOtaDexopt.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public void cleanup() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOtaDexopt.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public boolean isDone() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOtaDexopt.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public float getProgress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOtaDexopt.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public void dexoptNextPackage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOtaDexopt.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public java.lang.String nextDexoptCommand() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IOtaDexopt.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
