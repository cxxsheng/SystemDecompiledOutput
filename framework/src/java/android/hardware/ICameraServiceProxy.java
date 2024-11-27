package android.hardware;

/* loaded from: classes.dex */
public interface ICameraServiceProxy extends android.os.IInterface {
    int getAutoframingOverride(java.lang.String str) throws android.os.RemoteException;

    int getRotateAndCropOverride(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean isCameraDisabled(int i) throws android.os.RemoteException;

    void notifyCameraState(android.hardware.CameraSessionStats cameraSessionStats) throws android.os.RemoteException;

    void pingForUserUpdate() throws android.os.RemoteException;

    public static class Default implements android.hardware.ICameraServiceProxy {
        @Override // android.hardware.ICameraServiceProxy
        public void pingForUserUpdate() throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraServiceProxy
        public void notifyCameraState(android.hardware.CameraSessionStats cameraSessionStats) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraServiceProxy
        public int getRotateAndCropOverride(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraServiceProxy
        public int getAutoframingOverride(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraServiceProxy
        public boolean isCameraDisabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.ICameraServiceProxy {
        public static final java.lang.String DESCRIPTOR = "android.hardware.ICameraServiceProxy";
        static final int TRANSACTION_getAutoframingOverride = 4;
        static final int TRANSACTION_getRotateAndCropOverride = 3;
        static final int TRANSACTION_isCameraDisabled = 5;
        static final int TRANSACTION_notifyCameraState = 2;
        static final int TRANSACTION_pingForUserUpdate = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.ICameraServiceProxy asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.ICameraServiceProxy)) {
                return (android.hardware.ICameraServiceProxy) queryLocalInterface;
            }
            return new android.hardware.ICameraServiceProxy.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "pingForUserUpdate";
                case 2:
                    return "notifyCameraState";
                case 3:
                    return "getRotateAndCropOverride";
                case 4:
                    return "getAutoframingOverride";
                case 5:
                    return "isCameraDisabled";
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
                    pingForUserUpdate();
                    return true;
                case 2:
                    android.hardware.CameraSessionStats cameraSessionStats = (android.hardware.CameraSessionStats) parcel.readTypedObject(android.hardware.CameraSessionStats.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCameraState(cameraSessionStats);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rotateAndCropOverride = getRotateAndCropOverride(readString, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(rotateAndCropOverride);
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int autoframingOverride = getAutoframingOverride(readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(autoframingOverride);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCameraDisabled = isCameraDisabled(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraDisabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.ICameraServiceProxy {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.ICameraServiceProxy.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.ICameraServiceProxy
            public void pingForUserUpdate() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraServiceProxy.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public void notifyCameraState(android.hardware.CameraSessionStats cameraSessionStats) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraServiceProxy.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraSessionStats, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public int getRotateAndCropOverride(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraServiceProxy.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public int getAutoframingOverride(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraServiceProxy.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceProxy
            public boolean isCameraDisabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraServiceProxy.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
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
