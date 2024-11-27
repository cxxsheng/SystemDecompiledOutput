package android.service.games;

/* loaded from: classes3.dex */
public interface IGameSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.games.IGameSession";

    void onDestroyed() throws android.os.RemoteException;

    void onTaskFocusChanged(boolean z) throws android.os.RemoteException;

    void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements android.service.games.IGameSession {
        @Override // android.service.games.IGameSession
        public void onDestroyed() throws android.os.RemoteException {
        }

        @Override // android.service.games.IGameSession
        public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.games.IGameSession
        public void onTaskFocusChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.games.IGameSession {
        static final int TRANSACTION_onDestroyed = 1;
        static final int TRANSACTION_onTaskFocusChanged = 3;
        static final int TRANSACTION_onTransientSystemBarVisibilityFromRevealGestureChanged = 2;

        public Stub() {
            attachInterface(this, android.service.games.IGameSession.DESCRIPTOR);
        }

        public static android.service.games.IGameSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.games.IGameSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.games.IGameSession)) {
                return (android.service.games.IGameSession) queryLocalInterface;
            }
            return new android.service.games.IGameSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDestroyed";
                case 2:
                    return "onTransientSystemBarVisibilityFromRevealGestureChanged";
                case 3:
                    return "onTaskFocusChanged";
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
                parcel.enforceInterface(android.service.games.IGameSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.games.IGameSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onDestroyed();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTransientSystemBarVisibilityFromRevealGestureChanged(readBoolean);
                    return true;
                case 3:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTaskFocusChanged(readBoolean2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.games.IGameSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.games.IGameSession.DESCRIPTOR;
            }

            @Override // android.service.games.IGameSession
            public void onDestroyed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameSession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.games.IGameSession
            public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.games.IGameSession
            public void onTaskFocusChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
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
