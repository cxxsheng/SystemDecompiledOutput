package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IPreviewImageProcessorImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IPreviewImageProcessorImpl";

    void onImageFormatUpdate(int i) throws android.os.RemoteException;

    void onOutputSurface(android.view.Surface surface, int i) throws android.os.RemoteException;

    void onResolutionUpdate(android.hardware.camera2.extension.Size size) throws android.os.RemoteException;

    void process(android.hardware.camera2.extension.ParcelImage parcelImage, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, int i, android.hardware.camera2.extension.IProcessResultImpl iProcessResultImpl) throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IPreviewImageProcessorImpl {
        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onOutputSurface(android.view.Surface surface, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onResolutionUpdate(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void onImageFormatUpdate(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
        public void process(android.hardware.camera2.extension.ParcelImage parcelImage, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, int i, android.hardware.camera2.extension.IProcessResultImpl iProcessResultImpl) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IPreviewImageProcessorImpl {
        static final int TRANSACTION_onImageFormatUpdate = 3;
        static final int TRANSACTION_onOutputSurface = 1;
        static final int TRANSACTION_onResolutionUpdate = 2;
        static final int TRANSACTION_process = 4;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IPreviewImageProcessorImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IPreviewImageProcessorImpl)) {
                return (android.hardware.camera2.extension.IPreviewImageProcessorImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IPreviewImageProcessorImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onOutputSurface";
                case 2:
                    return "onResolutionUpdate";
                case 3:
                    return "onImageFormatUpdate";
                case 4:
                    return "process";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOutputSurface(surface, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.camera2.extension.Size size = (android.hardware.camera2.extension.Size) parcel.readTypedObject(android.hardware.camera2.extension.Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResolutionUpdate(size);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImageFormatUpdate(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.camera2.extension.ParcelImage parcelImage = (android.hardware.camera2.extension.ParcelImage) parcel.readTypedObject(android.hardware.camera2.extension.ParcelImage.CREATOR);
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    int readInt3 = parcel.readInt();
                    android.hardware.camera2.extension.IProcessResultImpl asInterface = android.hardware.camera2.extension.IProcessResultImpl.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    process(parcelImage, cameraMetadataNative, readInt3, asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IPreviewImageProcessorImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onOutputSurface(android.view.Surface surface, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onResolutionUpdate(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void onImageFormatUpdate(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewImageProcessorImpl
            public void process(android.hardware.camera2.extension.ParcelImage parcelImage, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, int i, android.hardware.camera2.extension.IProcessResultImpl iProcessResultImpl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewImageProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(parcelImage, 0);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProcessResultImpl);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
