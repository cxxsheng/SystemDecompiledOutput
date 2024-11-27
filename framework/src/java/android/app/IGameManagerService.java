package android.app;

/* loaded from: classes.dex */
public interface IGameManagerService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IGameManagerService";

    void addGameModeListener(android.app.IGameModeListener iGameModeListener) throws android.os.RemoteException;

    void addGameStateListener(android.app.IGameStateListener iGameStateListener) throws android.os.RemoteException;

    int[] getAvailableGameModes(java.lang.String str, int i) throws android.os.RemoteException;

    int getGameMode(java.lang.String str, int i) throws android.os.RemoteException;

    android.app.GameModeInfo getGameModeInfo(java.lang.String str, int i) throws android.os.RemoteException;

    float getResolutionScalingFactor(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean isAngleEnabled(java.lang.String str, int i) throws android.os.RemoteException;

    void notifyGraphicsEnvironmentSetup(java.lang.String str, int i) throws android.os.RemoteException;

    void removeGameModeListener(android.app.IGameModeListener iGameModeListener) throws android.os.RemoteException;

    void removeGameStateListener(android.app.IGameStateListener iGameStateListener) throws android.os.RemoteException;

    void setGameMode(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setGameServiceProvider(java.lang.String str) throws android.os.RemoteException;

    void setGameState(java.lang.String str, android.app.GameState gameState, int i) throws android.os.RemoteException;

    void toggleGameDefaultFrameRate(boolean z) throws android.os.RemoteException;

    void updateCustomGameModeConfiguration(java.lang.String str, android.app.GameModeConfiguration gameModeConfiguration, int i) throws android.os.RemoteException;

    void updateResolutionScalingFactor(java.lang.String str, int i, float f, int i2) throws android.os.RemoteException;

    public static class Default implements android.app.IGameManagerService {
        @Override // android.app.IGameManagerService
        public int getGameMode(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IGameManagerService
        public void setGameMode(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public int[] getAvailableGameModes(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IGameManagerService
        public boolean isAngleEnabled(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IGameManagerService
        public void notifyGraphicsEnvironmentSetup(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void setGameState(java.lang.String str, android.app.GameState gameState, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public android.app.GameModeInfo getGameModeInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IGameManagerService
        public void setGameServiceProvider(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void updateResolutionScalingFactor(java.lang.String str, int i, float f, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public float getResolutionScalingFactor(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.app.IGameManagerService
        public void updateCustomGameModeConfiguration(java.lang.String str, android.app.GameModeConfiguration gameModeConfiguration, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void addGameModeListener(android.app.IGameModeListener iGameModeListener) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void removeGameModeListener(android.app.IGameModeListener iGameModeListener) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void addGameStateListener(android.app.IGameStateListener iGameStateListener) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void removeGameStateListener(android.app.IGameStateListener iGameStateListener) throws android.os.RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void toggleGameDefaultFrameRate(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IGameManagerService {
        static final int TRANSACTION_addGameModeListener = 12;
        static final int TRANSACTION_addGameStateListener = 14;
        static final int TRANSACTION_getAvailableGameModes = 3;
        static final int TRANSACTION_getGameMode = 1;
        static final int TRANSACTION_getGameModeInfo = 7;
        static final int TRANSACTION_getResolutionScalingFactor = 10;
        static final int TRANSACTION_isAngleEnabled = 4;
        static final int TRANSACTION_notifyGraphicsEnvironmentSetup = 5;
        static final int TRANSACTION_removeGameModeListener = 13;
        static final int TRANSACTION_removeGameStateListener = 15;
        static final int TRANSACTION_setGameMode = 2;
        static final int TRANSACTION_setGameServiceProvider = 8;
        static final int TRANSACTION_setGameState = 6;
        static final int TRANSACTION_toggleGameDefaultFrameRate = 16;
        static final int TRANSACTION_updateCustomGameModeConfiguration = 11;
        static final int TRANSACTION_updateResolutionScalingFactor = 9;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.app.IGameManagerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.app.IGameManagerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IGameManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IGameManagerService)) {
                return (android.app.IGameManagerService) queryLocalInterface;
            }
            return new android.app.IGameManagerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getGameMode";
                case 2:
                    return "setGameMode";
                case 3:
                    return "getAvailableGameModes";
                case 4:
                    return "isAngleEnabled";
                case 5:
                    return "notifyGraphicsEnvironmentSetup";
                case 6:
                    return "setGameState";
                case 7:
                    return "getGameModeInfo";
                case 8:
                    return "setGameServiceProvider";
                case 9:
                    return "updateResolutionScalingFactor";
                case 10:
                    return "getResolutionScalingFactor";
                case 11:
                    return "updateCustomGameModeConfiguration";
                case 12:
                    return "addGameModeListener";
                case 13:
                    return "removeGameModeListener";
                case 14:
                    return "addGameStateListener";
                case 15:
                    return "removeGameStateListener";
                case 16:
                    return "toggleGameDefaultFrameRate";
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
                parcel.enforceInterface(android.app.IGameManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IGameManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int gameMode = getGameMode(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(gameMode);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGameMode(readString2, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] availableGameModes = getAvailableGameModes(readString3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableGameModes);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAngleEnabled = isAngleEnabled(readString4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAngleEnabled);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyGraphicsEnvironmentSetup(readString5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.app.GameState gameState = (android.app.GameState) parcel.readTypedObject(android.app.GameState.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGameState(readString6, gameState, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.GameModeInfo gameModeInfo = getGameModeInfo(readString7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gameModeInfo, 1);
                    return true;
                case 8:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGameServiceProvider(readString8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString9 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateResolutionScalingFactor(readString9, readInt9, readFloat, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float resolutionScalingFactor = getResolutionScalingFactor(readString10, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeFloat(resolutionScalingFactor);
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    android.app.GameModeConfiguration gameModeConfiguration = (android.app.GameModeConfiguration) parcel.readTypedObject(android.app.GameModeConfiguration.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCustomGameModeConfiguration(readString11, gameModeConfiguration, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.app.IGameModeListener asInterface = android.app.IGameModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addGameModeListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.app.IGameModeListener asInterface2 = android.app.IGameModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGameModeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.app.IGameStateListener asInterface3 = android.app.IGameStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addGameStateListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.app.IGameStateListener asInterface4 = android.app.IGameStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGameStateListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    toggleGameDefaultFrameRate(readBoolean);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IGameManagerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IGameManagerService.DESCRIPTOR;
            }

            @Override // android.app.IGameManagerService
            public int getGameMode(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameMode(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public int[] getAvailableGameModes(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public boolean isAngleEnabled(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void notifyGraphicsEnvironmentSetup(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameState(java.lang.String str, android.app.GameState gameState, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(gameState, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public android.app.GameModeInfo getGameModeInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.GameModeInfo) obtain2.readTypedObject(android.app.GameModeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameServiceProvider(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateResolutionScalingFactor(java.lang.String str, int i, float f, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public float getResolutionScalingFactor(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateCustomGameModeConfiguration(java.lang.String str, android.app.GameModeConfiguration gameModeConfiguration, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(gameModeConfiguration, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameModeListener(android.app.IGameModeListener iGameModeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameModeListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameModeListener(android.app.IGameModeListener iGameModeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameModeListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameStateListener(android.app.IGameStateListener iGameStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameStateListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameStateListener(android.app.IGameStateListener iGameStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameStateListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void toggleGameDefaultFrameRate(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IGameManagerService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void toggleGameDefaultFrameRate_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_GAME_MODE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
