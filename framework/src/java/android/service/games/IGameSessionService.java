package android.service.games;

/* loaded from: classes3.dex */
public interface IGameSessionService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.games.IGameSessionService";

    void create(android.service.games.IGameSessionController iGameSessionController, android.service.games.CreateGameSessionRequest createGameSessionRequest, android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    public static class Default implements android.service.games.IGameSessionService {
        @Override // android.service.games.IGameSessionService
        public void create(android.service.games.IGameSessionController iGameSessionController, android.service.games.CreateGameSessionRequest createGameSessionRequest, android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.games.IGameSessionService {
        static final int TRANSACTION_create = 1;

        public Stub() {
            attachInterface(this, android.service.games.IGameSessionService.DESCRIPTOR);
        }

        public static android.service.games.IGameSessionService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.games.IGameSessionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.games.IGameSessionService)) {
                return (android.service.games.IGameSessionService) queryLocalInterface;
            }
            return new android.service.games.IGameSessionService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "create";
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
                parcel.enforceInterface(android.service.games.IGameSessionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.games.IGameSessionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.games.IGameSessionController asInterface = android.service.games.IGameSessionController.Stub.asInterface(parcel.readStrongBinder());
                    android.service.games.CreateGameSessionRequest createGameSessionRequest = (android.service.games.CreateGameSessionRequest) parcel.readTypedObject(android.service.games.CreateGameSessionRequest.CREATOR);
                    android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration = (android.service.games.GameSessionViewHostConfiguration) parcel.readTypedObject(android.service.games.GameSessionViewHostConfiguration.CREATOR);
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    create(asInterface, createGameSessionRequest, gameSessionViewHostConfiguration, androidFuture);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.games.IGameSessionService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.games.IGameSessionService.DESCRIPTOR;
            }

            @Override // android.service.games.IGameSessionService
            public void create(android.service.games.IGameSessionController iGameSessionController, android.service.games.CreateGameSessionRequest createGameSessionRequest, android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.games.IGameSessionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameSessionController);
                    obtain.writeTypedObject(createGameSessionRequest, 0);
                    obtain.writeTypedObject(gameSessionViewHostConfiguration, 0);
                    obtain.writeTypedObject(androidFuture, 0);
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
