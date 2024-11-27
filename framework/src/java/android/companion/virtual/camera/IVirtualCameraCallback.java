package android.companion.virtual.camera;

/* loaded from: classes.dex */
public interface IVirtualCameraCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.camera.IVirtualCameraCallback";

    void onProcessCaptureRequest(int i, long j) throws android.os.RemoteException;

    void onStreamClosed(int i) throws android.os.RemoteException;

    void onStreamConfigured(int i, android.view.Surface surface, int i2, int i3, int i4) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.camera.IVirtualCameraCallback {
        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamConfigured(int i, android.view.Surface surface, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onProcessCaptureRequest(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamClosed(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.camera.IVirtualCameraCallback {
        static final int TRANSACTION_onProcessCaptureRequest = 2;
        static final int TRANSACTION_onStreamClosed = 3;
        static final int TRANSACTION_onStreamConfigured = 1;

        public Stub() {
            attachInterface(this, android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
        }

        public static android.companion.virtual.camera.IVirtualCameraCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.camera.IVirtualCameraCallback)) {
                return (android.companion.virtual.camera.IVirtualCameraCallback) queryLocalInterface;
            }
            return new android.companion.virtual.camera.IVirtualCameraCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStreamConfigured";
                case 2:
                    return "onProcessCaptureRequest";
                case 3:
                    return "onStreamClosed";
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
                parcel.enforceInterface(android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStreamConfigured(readInt, surface, readInt2, readInt3, readInt4);
                    return true;
                case 2:
                    int readInt5 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onProcessCaptureRequest(readInt5, readLong);
                    return true;
                case 3:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStreamClosed(readInt6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.camera.IVirtualCameraCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onStreamConfigured(int i, android.view.Surface surface, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onProcessCaptureRequest(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onStreamClosed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.camera.IVirtualCameraCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
