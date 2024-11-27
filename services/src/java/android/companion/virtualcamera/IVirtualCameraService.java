package android.companion.virtualcamera;

/* loaded from: classes.dex */
public interface IVirtualCameraService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtualcamera.IVirtualCameraService";

    int getCameraId(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean registerCamera(android.os.IBinder iBinder, android.companion.virtualcamera.VirtualCameraConfiguration virtualCameraConfiguration) throws android.os.RemoteException;

    void unregisterCamera(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.companion.virtualcamera.IVirtualCameraService {
        @Override // android.companion.virtualcamera.IVirtualCameraService
        public boolean registerCamera(android.os.IBinder iBinder, android.companion.virtualcamera.VirtualCameraConfiguration virtualCameraConfiguration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.companion.virtualcamera.IVirtualCameraService
        public void unregisterCamera(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.companion.virtualcamera.IVirtualCameraService
        public int getCameraId(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtualcamera.IVirtualCameraService {
        static final int TRANSACTION_getCameraId = 3;
        static final int TRANSACTION_registerCamera = 1;
        static final int TRANSACTION_unregisterCamera = 2;

        public Stub() {
            attachInterface(this, android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
        }

        public static android.companion.virtualcamera.IVirtualCameraService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtualcamera.IVirtualCameraService)) {
                return (android.companion.virtualcamera.IVirtualCameraService) queryLocalInterface;
            }
            return new android.companion.virtualcamera.IVirtualCameraService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCamera";
                case 2:
                    return "unregisterCamera";
                case 3:
                    return "getCameraId";
                default:
                    return null;
            }
        }

        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.companion.virtualcamera.VirtualCameraConfiguration virtualCameraConfiguration = (android.companion.virtualcamera.VirtualCameraConfiguration) parcel.readTypedObject(android.companion.virtualcamera.VirtualCameraConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean registerCamera = registerCamera(readStrongBinder, virtualCameraConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCamera);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterCamera(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int cameraId = getCameraId(readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeInt(cameraId);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtualcamera.IVirtualCameraService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR;
            }

            @Override // android.companion.virtualcamera.IVirtualCameraService
            public boolean registerCamera(android.os.IBinder iBinder, android.companion.virtualcamera.VirtualCameraConfiguration virtualCameraConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualCameraConfiguration, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtualcamera.IVirtualCameraService
            public void unregisterCamera(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtualcamera.IVirtualCameraService
            public int getCameraId(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.companion.virtualcamera.IVirtualCameraService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public int getMaxTransactionId() {
            return 2;
        }
    }
}
