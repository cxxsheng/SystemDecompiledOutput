package android.app;

/* loaded from: classes.dex */
public interface ILocalWallpaperColorConsumer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ILocalWallpaperColorConsumer";

    void onColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors) throws android.os.RemoteException;

    public static class Default implements android.app.ILocalWallpaperColorConsumer {
        @Override // android.app.ILocalWallpaperColorConsumer
        public void onColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ILocalWallpaperColorConsumer {
        static final int TRANSACTION_onColorsChanged = 1;

        public Stub() {
            attachInterface(this, android.app.ILocalWallpaperColorConsumer.DESCRIPTOR);
        }

        public static android.app.ILocalWallpaperColorConsumer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ILocalWallpaperColorConsumer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ILocalWallpaperColorConsumer)) {
                return (android.app.ILocalWallpaperColorConsumer) queryLocalInterface;
            }
            return new android.app.ILocalWallpaperColorConsumer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onColorsChanged";
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
                parcel.enforceInterface(android.app.ILocalWallpaperColorConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ILocalWallpaperColorConsumer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.graphics.RectF rectF = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
                    android.app.WallpaperColors wallpaperColors = (android.app.WallpaperColors) parcel.readTypedObject(android.app.WallpaperColors.CREATOR);
                    parcel.enforceNoDataAvail();
                    onColorsChanged(rectF, wallpaperColors);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ILocalWallpaperColorConsumer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ILocalWallpaperColorConsumer.DESCRIPTOR;
            }

            @Override // android.app.ILocalWallpaperColorConsumer
            public void onColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ILocalWallpaperColorConsumer.DESCRIPTOR);
                    obtain.writeTypedObject(rectF, 0);
                    obtain.writeTypedObject(wallpaperColors, 0);
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
