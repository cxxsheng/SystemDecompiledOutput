package android.app;

/* loaded from: classes.dex */
public interface IUserSwitchObserver extends android.os.IInterface {
    void onBeforeUserSwitching(int i) throws android.os.RemoteException;

    void onForegroundProfileSwitch(int i) throws android.os.RemoteException;

    void onLockedBootComplete(int i) throws android.os.RemoteException;

    void onUserSwitchComplete(int i) throws android.os.RemoteException;

    void onUserSwitching(int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    public static class Default implements android.app.IUserSwitchObserver {
        @Override // android.app.IUserSwitchObserver
        public void onBeforeUserSwitching(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onUserSwitching(int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onUserSwitchComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onForegroundProfileSwitch(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onLockedBootComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IUserSwitchObserver {
        public static final java.lang.String DESCRIPTOR = "android.app.IUserSwitchObserver";
        static final int TRANSACTION_onBeforeUserSwitching = 1;
        static final int TRANSACTION_onForegroundProfileSwitch = 4;
        static final int TRANSACTION_onLockedBootComplete = 5;
        static final int TRANSACTION_onUserSwitchComplete = 3;
        static final int TRANSACTION_onUserSwitching = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IUserSwitchObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IUserSwitchObserver)) {
                return (android.app.IUserSwitchObserver) queryLocalInterface;
            }
            return new android.app.IUserSwitchObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBeforeUserSwitching";
                case 2:
                    return "onUserSwitching";
                case 3:
                    return "onUserSwitchComplete";
                case 4:
                    return "onForegroundProfileSwitch";
                case 5:
                    return "onLockedBootComplete";
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
                    onBeforeUserSwitching(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onUserSwitching(readInt2, asInterface);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserSwitchComplete(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onForegroundProfileSwitch(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLockedBootComplete(readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IUserSwitchObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IUserSwitchObserver.Stub.DESCRIPTOR;
            }

            @Override // android.app.IUserSwitchObserver
            public void onBeforeUserSwitching(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUserSwitchObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onUserSwitching(int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUserSwitchObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onUserSwitchComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUserSwitchObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onForegroundProfileSwitch(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUserSwitchObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onLockedBootComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IUserSwitchObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
