package android.gsi;

/* loaded from: classes.dex */
public interface IImageService extends android.os.IInterface {
    public static final int CREATE_IMAGE_DEFAULT = 0;
    public static final int CREATE_IMAGE_READONLY = 1;
    public static final int CREATE_IMAGE_ZERO_FILL = 2;
    public static final java.lang.String DESCRIPTOR = "android.gsi.IImageService";
    public static final int IMAGE_ERROR = 1;
    public static final int IMAGE_OK = 0;

    boolean backingImageExists(java.lang.String str) throws android.os.RemoteException;

    void createBackingImage(java.lang.String str, long j, int i, android.gsi.IProgressCallback iProgressCallback) throws android.os.RemoteException;

    void deleteBackingImage(java.lang.String str) throws android.os.RemoteException;

    void disableImage(java.lang.String str) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllBackingImages() throws android.os.RemoteException;

    int getAvbPublicKey(java.lang.String str, android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException;

    java.lang.String getMappedImageDevice(java.lang.String str) throws android.os.RemoteException;

    boolean isImageDisabled(java.lang.String str) throws android.os.RemoteException;

    boolean isImageMapped(java.lang.String str) throws android.os.RemoteException;

    void mapImageDevice(java.lang.String str, int i, android.gsi.MappedImage mappedImage) throws android.os.RemoteException;

    void removeAllImages() throws android.os.RemoteException;

    void removeDisabledImages() throws android.os.RemoteException;

    void unmapImageDevice(java.lang.String str) throws android.os.RemoteException;

    void zeroFillNewImage(java.lang.String str, long j) throws android.os.RemoteException;

    public static class Default implements android.gsi.IImageService {
        @Override // android.gsi.IImageService
        public void createBackingImage(java.lang.String str, long j, int i, android.gsi.IProgressCallback iProgressCallback) throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public void deleteBackingImage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public void mapImageDevice(java.lang.String str, int i, android.gsi.MappedImage mappedImage) throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public void unmapImageDevice(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public boolean backingImageExists(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public boolean isImageMapped(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public int getAvbPublicKey(java.lang.String str, android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IImageService
        public java.util.List<java.lang.String> getAllBackingImages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IImageService
        public void zeroFillNewImage(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public void removeAllImages() throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public void disableImage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public void removeDisabledImages() throws android.os.RemoteException {
        }

        @Override // android.gsi.IImageService
        public boolean isImageDisabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IImageService
        public java.lang.String getMappedImageDevice(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.gsi.IImageService {
        static final int TRANSACTION_backingImageExists = 5;
        static final int TRANSACTION_createBackingImage = 1;
        static final int TRANSACTION_deleteBackingImage = 2;
        static final int TRANSACTION_disableImage = 11;
        static final int TRANSACTION_getAllBackingImages = 8;
        static final int TRANSACTION_getAvbPublicKey = 7;
        static final int TRANSACTION_getMappedImageDevice = 14;
        static final int TRANSACTION_isImageDisabled = 13;
        static final int TRANSACTION_isImageMapped = 6;
        static final int TRANSACTION_mapImageDevice = 3;
        static final int TRANSACTION_removeAllImages = 10;
        static final int TRANSACTION_removeDisabledImages = 12;
        static final int TRANSACTION_unmapImageDevice = 4;
        static final int TRANSACTION_zeroFillNewImage = 9;

        public Stub() {
            attachInterface(this, android.gsi.IImageService.DESCRIPTOR);
        }

        public static android.gsi.IImageService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.gsi.IImageService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.gsi.IImageService)) {
                return (android.gsi.IImageService) queryLocalInterface;
            }
            return new android.gsi.IImageService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.gsi.IImageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.gsi.IImageService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    android.gsi.IProgressCallback asInterface = android.gsi.IProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createBackingImage(readString, readLong, readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteBackingImage(readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    android.gsi.MappedImage mappedImage = new android.gsi.MappedImage();
                    parcel.enforceNoDataAvail();
                    mapImageDevice(readString3, readInt2, mappedImage);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mappedImage, 1);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmapImageDevice(readString4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean backingImageExists = backingImageExists(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backingImageExists);
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isImageMapped = isImageMapped(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImageMapped);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    android.gsi.AvbPublicKey avbPublicKey = new android.gsi.AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    int avbPublicKey2 = getAvbPublicKey(readString7, avbPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeInt(avbPublicKey2);
                    parcel2.writeTypedObject(avbPublicKey, 1);
                    return true;
                case 8:
                    java.util.List<java.lang.String> allBackingImages = getAllBackingImages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allBackingImages);
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    zeroFillNewImage(readString8, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    removeAllImages();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableImage(readString9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    removeDisabledImages();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isImageDisabled = isImageDisabled(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImageDisabled);
                    return true;
                case 14:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String mappedImageDevice = getMappedImageDevice(readString11);
                    parcel2.writeNoException();
                    parcel2.writeString(mappedImageDevice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.gsi.IImageService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.gsi.IImageService.DESCRIPTOR;
            }

            @Override // android.gsi.IImageService
            public void createBackingImage(java.lang.String str, long j, int i, android.gsi.IProgressCallback iProgressCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void deleteBackingImage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void mapImageDevice(java.lang.String str, int i, android.gsi.MappedImage mappedImage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        mappedImage.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void unmapImageDevice(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public boolean backingImageExists(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public boolean isImageMapped(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public int getAvbPublicKey(java.lang.String str, android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public java.util.List<java.lang.String> getAllBackingImages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void zeroFillNewImage(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void removeAllImages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void disableImage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public void removeDisabledImages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public boolean isImageDisabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IImageService
            public java.lang.String getMappedImageDevice(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IImageService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
