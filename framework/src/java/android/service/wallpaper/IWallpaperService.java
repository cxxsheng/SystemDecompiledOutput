package android.service.wallpaper;

/* loaded from: classes3.dex */
public interface IWallpaperService extends android.os.IInterface {
    void attach(android.service.wallpaper.IWallpaperConnection iWallpaperConnection, android.os.IBinder iBinder, int i, boolean z, int i2, int i3, android.graphics.Rect rect, int i4, int i5, android.app.WallpaperInfo wallpaperInfo) throws android.os.RemoteException;

    void detach(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.service.wallpaper.IWallpaperService {
        @Override // android.service.wallpaper.IWallpaperService
        public void attach(android.service.wallpaper.IWallpaperConnection iWallpaperConnection, android.os.IBinder iBinder, int i, boolean z, int i2, int i3, android.graphics.Rect rect, int i4, int i5, android.app.WallpaperInfo wallpaperInfo) throws android.os.RemoteException {
        }

        @Override // android.service.wallpaper.IWallpaperService
        public void detach(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.wallpaper.IWallpaperService {
        public static final java.lang.String DESCRIPTOR = "android.service.wallpaper.IWallpaperService";
        static final int TRANSACTION_attach = 1;
        static final int TRANSACTION_detach = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.wallpaper.IWallpaperService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.wallpaper.IWallpaperService)) {
                return (android.service.wallpaper.IWallpaperService) queryLocalInterface;
            }
            return new android.service.wallpaper.IWallpaperService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "attach";
                case 2:
                    return "detach";
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
                    android.service.wallpaper.IWallpaperConnection asInterface = android.service.wallpaper.IWallpaperConnection.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.app.WallpaperInfo wallpaperInfo = (android.app.WallpaperInfo) parcel.readTypedObject(android.app.WallpaperInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    attach(asInterface, readStrongBinder, readInt, readBoolean, readInt2, readInt3, rect, readInt4, readInt5, wallpaperInfo);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detach(readStrongBinder2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.wallpaper.IWallpaperService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.wallpaper.IWallpaperService.Stub.DESCRIPTOR;
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void attach(android.service.wallpaper.IWallpaperConnection iWallpaperConnection, android.os.IBinder iBinder, int i, boolean z, int i2, int i3, android.graphics.Rect rect, int i4, int i5, android.app.WallpaperInfo wallpaperInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperConnection);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(wallpaperInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperService
            public void detach(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.wallpaper.IWallpaperService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
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
