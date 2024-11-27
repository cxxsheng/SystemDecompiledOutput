package android.service.wallpaper;

/* loaded from: classes3.dex */
public interface IWallpaperConnection extends android.os.IInterface {
    void attachEngine(android.service.wallpaper.IWallpaperEngine iWallpaperEngine, int i) throws android.os.RemoteException;

    void engineShown(android.service.wallpaper.IWallpaperEngine iWallpaperEngine) throws android.os.RemoteException;

    void onLocalWallpaperColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors, int i) throws android.os.RemoteException;

    void onWallpaperColorsChanged(android.app.WallpaperColors wallpaperColors, int i) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor setWallpaper(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.service.wallpaper.IWallpaperConnection {
        @Override // android.service.wallpaper.IWallpaperConnection
        public void attachEngine(android.service.wallpaper.IWallpaperEngine iWallpaperEngine, int i) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void engineShown(android.service.wallpaper.IWallpaperEngine iWallpaperEngine) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public android.os.ParcelFileDescriptor setWallpaper(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void onWallpaperColorsChanged(android.app.WallpaperColors wallpaperColors, int i) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperConnection
        public void onLocalWallpaperColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.wallpaper.IWallpaperConnection {
        public static final java.lang.String DESCRIPTOR = "android.service.wallpaper.IWallpaperConnection";
        static final int TRANSACTION_attachEngine = 1;
        static final int TRANSACTION_engineShown = 2;
        static final int TRANSACTION_onLocalWallpaperColorsChanged = 5;
        static final int TRANSACTION_onWallpaperColorsChanged = 4;
        static final int TRANSACTION_setWallpaper = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.wallpaper.IWallpaperConnection asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.wallpaper.IWallpaperConnection)) {
                return (android.service.wallpaper.IWallpaperConnection) queryLocalInterface;
            }
            return new android.service.wallpaper.IWallpaperConnection.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "attachEngine";
                case 2:
                    return "engineShown";
                case 3:
                    return "setWallpaper";
                case 4:
                    return "onWallpaperColorsChanged";
                case 5:
                    return "onLocalWallpaperColorsChanged";
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
                    android.service.wallpaper.IWallpaperEngine asInterface = android.service.wallpaper.IWallpaperEngine.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    attachEngine(asInterface, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.service.wallpaper.IWallpaperEngine asInterface2 = android.service.wallpaper.IWallpaperEngine.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    engineShown(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor wallpaper = setWallpaper(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wallpaper, 1);
                    return true;
                case 4:
                    android.app.WallpaperColors wallpaperColors = (android.app.WallpaperColors) parcel.readTypedObject(android.app.WallpaperColors.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onWallpaperColorsChanged(wallpaperColors, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.graphics.RectF rectF = (android.graphics.RectF) parcel.readTypedObject(android.graphics.RectF.CREATOR);
                    android.app.WallpaperColors wallpaperColors2 = (android.app.WallpaperColors) parcel.readTypedObject(android.app.WallpaperColors.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLocalWallpaperColorsChanged(rectF, wallpaperColors2, readInt3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.wallpaper.IWallpaperConnection {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.wallpaper.IWallpaperConnection.Stub.DESCRIPTOR;
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void attachEngine(android.service.wallpaper.IWallpaperEngine iWallpaperEngine, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperConnection.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperEngine);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void engineShown(android.service.wallpaper.IWallpaperEngine iWallpaperEngine) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperConnection.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperEngine);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public android.os.ParcelFileDescriptor setWallpaper(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperConnection.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void onWallpaperColorsChanged(android.app.WallpaperColors wallpaperColors, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(wallpaperColors, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperConnection
            public void onLocalWallpaperColorsChanged(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperConnection.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rectF, 0);
                    obtain.writeTypedObject(wallpaperColors, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
