package android.service.games;

/* loaded from: classes3.dex */
public interface IGameService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.games.IGameService";

    void connected(android.service.games.IGameServiceController iGameServiceController) throws android.os.RemoteException;

    void disconnected() throws android.os.RemoteException;

    void gameStarted(android.service.games.GameStartedEvent gameStartedEvent) throws android.os.RemoteException;

    public static class Default implements android.service.games.IGameService {
        @Override // android.service.games.IGameService
        public void connected(android.service.games.IGameServiceController iGameServiceController) throws android.os.RemoteException {
        }

        @Override // android.service.games.IGameService
        public void disconnected() throws android.os.RemoteException {
        }

        @Override // android.service.games.IGameService
        public void gameStarted(android.service.games.GameStartedEvent gameStartedEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.games.IGameService {
        static final int TRANSACTION_connected = 1;
        static final int TRANSACTION_disconnected = 2;
        static final int TRANSACTION_gameStarted = 3;

        public Stub() {
            attachInterface(this, android.service.games.IGameService.DESCRIPTOR);
        }

        public static android.service.games.IGameService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.games.IGameService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.games.IGameService)) {
                return (android.service.games.IGameService) queryLocalInterface;
            }
            return new android.service.games.IGameService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "connected";
                case 2:
                    return "disconnected";
                case 3:
                    return "gameStarted";
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
                parcel.enforceInterface(android.service.games.IGameService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.games.IGameService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.games.IGameServiceController asInterface = android.service.games.IGameServiceController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connected(asInterface);
                    return true;
                case 2:
                    disconnected();
                    return true;
                case 3:
                    android.service.games.GameStartedEvent gameStartedEvent = (android.service.games.GameStartedEvent) parcel.readTypedObject(android.service.games.GameStartedEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    gameStarted(gameStartedEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.games.IGameService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.games.IGameService.DESCRIPTOR;
            }

            @Override // android.service.games.IGameService
            public void connected(android.service.games.IGameServiceController iGameServiceController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameServiceController);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.games.IGameService
            public void disconnected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.games.IGameService
            public void gameStarted(android.service.games.GameStartedEvent gameStartedEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameService.DESCRIPTOR);
                    obtain.writeTypedObject(gameStartedEvent, 0);
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
