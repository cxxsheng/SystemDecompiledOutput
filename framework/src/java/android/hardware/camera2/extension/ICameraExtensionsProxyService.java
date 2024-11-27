package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface ICameraExtensionsProxyService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.ICameraExtensionsProxyService";

    boolean advancedExtensionsSupported() throws android.os.RemoteException;

    android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws android.os.RemoteException;

    android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension(int i) throws android.os.RemoteException;

    android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension(int i) throws android.os.RemoteException;

    void initializeSession(android.hardware.camera2.extension.IInitializeSessionCallback iInitializeSessionCallback) throws android.os.RemoteException;

    boolean registerClient(android.os.IBinder iBinder) throws android.os.RemoteException;

    void releaseSession() throws android.os.RemoteException;

    void unregisterClient(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.ICameraExtensionsProxyService {
        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean registerClient(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void unregisterClient(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean advancedExtensionsSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void initializeSession(android.hardware.camera2.extension.IInitializeSessionCallback iInitializeSessionCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void releaseSession() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.ICameraExtensionsProxyService {
        static final int TRANSACTION_advancedExtensionsSupported = 3;
        static final int TRANSACTION_initializeAdvancedExtension = 8;
        static final int TRANSACTION_initializeImageExtension = 7;
        static final int TRANSACTION_initializePreviewExtension = 6;
        static final int TRANSACTION_initializeSession = 4;
        static final int TRANSACTION_registerClient = 1;
        static final int TRANSACTION_releaseSession = 5;
        static final int TRANSACTION_unregisterClient = 2;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.ICameraExtensionsProxyService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.ICameraExtensionsProxyService)) {
                return (android.hardware.camera2.extension.ICameraExtensionsProxyService) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.ICameraExtensionsProxyService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerClient";
                case 2:
                    return "unregisterClient";
                case 3:
                    return "advancedExtensionsSupported";
                case 4:
                    return "initializeSession";
                case 5:
                    return "releaseSession";
                case 6:
                    return "initializePreviewExtension";
                case 7:
                    return "initializeImageExtension";
                case 8:
                    return "initializeAdvancedExtension";
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
                parcel.enforceInterface(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean registerClient = registerClient(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerClient);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterClient(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean advancedExtensionsSupported = advancedExtensionsSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(advancedExtensionsSupported);
                    return true;
                case 4:
                    android.hardware.camera2.extension.IInitializeSessionCallback asInterface = android.hardware.camera2.extension.IInitializeSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeSession(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    releaseSession();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension = initializePreviewExtension(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(initializePreviewExtension);
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension = initializeImageExtension(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(initializeImageExtension);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(initializeAdvancedExtension);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.ICameraExtensionsProxyService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public boolean registerClient(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void unregisterClient(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public boolean advancedExtensionsSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void initializeSession(android.hardware.camera2.extension.IInitializeSessionCallback iInitializeSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInitializeSessionCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public void releaseSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.IPreviewExtenderImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.IImageCaptureExtenderImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
            public android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ICameraExtensionsProxyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.IAdvancedExtenderImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
