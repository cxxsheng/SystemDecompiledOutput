package android.app;

/* loaded from: classes.dex */
public interface IGameStateListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IGameStateListener";

    void onGameStateChanged(java.lang.String str, android.app.GameState gameState, int i) throws android.os.RemoteException;

    public static class Default implements android.app.IGameStateListener {
        @Override // android.app.IGameStateListener
        public void onGameStateChanged(java.lang.String str, android.app.GameState gameState, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IGameStateListener {
        static final int TRANSACTION_onGameStateChanged = 1;

        public Stub() {
            attachInterface(this, android.app.IGameStateListener.DESCRIPTOR);
        }

        public static android.app.IGameStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IGameStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IGameStateListener)) {
                return (android.app.IGameStateListener) queryLocalInterface;
            }
            return new android.app.IGameStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onGameStateChanged";
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
                parcel.enforceInterface(android.app.IGameStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IGameStateListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.app.GameState gameState = (android.app.GameState) parcel.readTypedObject(android.app.GameState.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onGameStateChanged(readString, gameState, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IGameStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IGameStateListener.DESCRIPTOR;
            }

            @Override // android.app.IGameStateListener
            public void onGameStateChanged(java.lang.String str, android.app.GameState gameState, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IGameStateListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(gameState, 0);
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
