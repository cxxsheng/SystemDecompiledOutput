package android.app.wallpapereffectsgeneration;

/* loaded from: classes.dex */
public interface IWallpaperEffectsGenerationManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager";

    void generateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest, android.app.wallpapereffectsgeneration.ICinematicEffectListener iCinematicEffectListener) throws android.os.RemoteException;

    void returnCinematicEffectResponse(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) throws android.os.RemoteException;

    public static class Default implements android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager {
        @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
        public void generateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest, android.app.wallpapereffectsgeneration.ICinematicEffectListener iCinematicEffectListener) throws android.os.RemoteException {
        }

        @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
        public void returnCinematicEffectResponse(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager {
        static final int TRANSACTION_generateCinematicEffect = 1;
        static final int TRANSACTION_returnCinematicEffectResponse = 2;

        public Stub() {
            attachInterface(this, android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR);
        }

        public static android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager)) {
                return (android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager) queryLocalInterface;
            }
            return new android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "generateCinematicEffect";
                case 2:
                    return "returnCinematicEffectResponse";
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
                parcel.enforceInterface(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest = (android.app.wallpapereffectsgeneration.CinematicEffectRequest) parcel.readTypedObject(android.app.wallpapereffectsgeneration.CinematicEffectRequest.CREATOR);
                    android.app.wallpapereffectsgeneration.ICinematicEffectListener asInterface = android.app.wallpapereffectsgeneration.ICinematicEffectListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    generateCinematicEffect(cinematicEffectRequest, asInterface);
                    return true;
                case 2:
                    android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse = (android.app.wallpapereffectsgeneration.CinematicEffectResponse) parcel.readTypedObject(android.app.wallpapereffectsgeneration.CinematicEffectResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    returnCinematicEffectResponse(cinematicEffectResponse);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR;
            }

            @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
            public void generateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest, android.app.wallpapereffectsgeneration.ICinematicEffectListener iCinematicEffectListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectRequest, 0);
                    obtain.writeStrongInterface(iCinematicEffectListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager
            public void returnCinematicEffectResponse(android.app.wallpapereffectsgeneration.CinematicEffectResponse cinematicEffectResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.wallpapereffectsgeneration.IWallpaperEffectsGenerationManager.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectResponse, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
