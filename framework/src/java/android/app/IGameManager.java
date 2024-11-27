package android.app;

/* loaded from: classes.dex */
public interface IGameManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IGameManager";

    int getGameMode() throws android.os.RemoteException;

    public static class Default implements android.app.IGameManager {
        @Override // android.app.IGameManager
        public int getGameMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IGameManager {
        static final int TRANSACTION_getGameMode = 1;

        public Stub() {
            attachInterface(this, android.app.IGameManager.DESCRIPTOR);
        }

        public static android.app.IGameManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IGameManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IGameManager)) {
                return (android.app.IGameManager) queryLocalInterface;
            }
            return new android.app.IGameManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getGameMode";
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
                parcel.enforceInterface(android.app.IGameManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IGameManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int gameMode = getGameMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(gameMode);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IGameManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IGameManager.DESCRIPTOR;
            }

            @Override // android.app.IGameManager
            public int getGameMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
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
