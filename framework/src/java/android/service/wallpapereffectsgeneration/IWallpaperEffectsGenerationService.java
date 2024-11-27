package android.service.wallpapereffectsgeneration;

/* loaded from: classes3.dex */
public interface IWallpaperEffectsGenerationService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService";

    void onGenerateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest) throws android.os.RemoteException;

    public static class Default implements android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService {
        @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
        public void onGenerateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService {
        static final int TRANSACTION_onGenerateCinematicEffect = 1;

        public Stub() {
            attachInterface(this, android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.DESCRIPTOR);
        }

        public static android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService)) {
                return (android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService) queryLocalInterface;
            }
            return new android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onGenerateCinematicEffect";
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
                parcel.enforceInterface(android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest = (android.app.wallpapereffectsgeneration.CinematicEffectRequest) parcel.readTypedObject(android.app.wallpapereffectsgeneration.CinematicEffectRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGenerateCinematicEffect(cinematicEffectRequest);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.DESCRIPTOR;
            }

            @Override // android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService
            public void onGenerateCinematicEffect(android.app.wallpapereffectsgeneration.CinematicEffectRequest cinematicEffectRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpapereffectsgeneration.IWallpaperEffectsGenerationService.DESCRIPTOR);
                    obtain.writeTypedObject(cinematicEffectRequest, 0);
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
