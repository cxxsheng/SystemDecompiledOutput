package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IOutputSurfaceConfiguration extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IOutputSurfaceConfiguration";

    android.hardware.camera2.extension.OutputSurface getImageAnalysisOutputSurface() throws android.os.RemoteException;

    android.hardware.camera2.extension.OutputSurface getImageCaptureOutputSurface() throws android.os.RemoteException;

    android.hardware.camera2.extension.OutputSurface getPostviewOutputSurface() throws android.os.RemoteException;

    android.hardware.camera2.extension.OutputSurface getPreviewOutputSurface() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IOutputSurfaceConfiguration {
        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public android.hardware.camera2.extension.OutputSurface getPreviewOutputSurface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public android.hardware.camera2.extension.OutputSurface getImageCaptureOutputSurface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public android.hardware.camera2.extension.OutputSurface getImageAnalysisOutputSurface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
        public android.hardware.camera2.extension.OutputSurface getPostviewOutputSurface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IOutputSurfaceConfiguration {
        static final int TRANSACTION_getImageAnalysisOutputSurface = 3;
        static final int TRANSACTION_getImageCaptureOutputSurface = 2;
        static final int TRANSACTION_getPostviewOutputSurface = 4;
        static final int TRANSACTION_getPreviewOutputSurface = 1;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IOutputSurfaceConfiguration asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IOutputSurfaceConfiguration)) {
                return (android.hardware.camera2.extension.IOutputSurfaceConfiguration) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IOutputSurfaceConfiguration.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPreviewOutputSurface";
                case 2:
                    return "getImageCaptureOutputSurface";
                case 3:
                    return "getImageAnalysisOutputSurface";
                case 4:
                    return "getPostviewOutputSurface";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.camera2.extension.OutputSurface previewOutputSurface = getPreviewOutputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(previewOutputSurface, 1);
                    return true;
                case 2:
                    android.hardware.camera2.extension.OutputSurface imageCaptureOutputSurface = getImageCaptureOutputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imageCaptureOutputSurface, 1);
                    return true;
                case 3:
                    android.hardware.camera2.extension.OutputSurface imageAnalysisOutputSurface = getImageAnalysisOutputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imageAnalysisOutputSurface, 1);
                    return true;
                case 4:
                    android.hardware.camera2.extension.OutputSurface postviewOutputSurface = getPostviewOutputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(postviewOutputSurface, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IOutputSurfaceConfiguration {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public android.hardware.camera2.extension.OutputSurface getPreviewOutputSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.OutputSurface) obtain2.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public android.hardware.camera2.extension.OutputSurface getImageCaptureOutputSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.OutputSurface) obtain2.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public android.hardware.camera2.extension.OutputSurface getImageAnalysisOutputSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.OutputSurface) obtain2.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IOutputSurfaceConfiguration
            public android.hardware.camera2.extension.OutputSurface getPostviewOutputSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IOutputSurfaceConfiguration.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.OutputSurface) obtain2.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
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
