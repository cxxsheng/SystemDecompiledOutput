package android.hardware.camera2;

/* loaded from: classes.dex */
public interface ICameraDeviceCallbacks extends android.os.IInterface {
    public static final int ERROR_CAMERA_BUFFER = 5;
    public static final int ERROR_CAMERA_DEVICE = 1;
    public static final int ERROR_CAMERA_DISABLED = 6;
    public static final int ERROR_CAMERA_DISCONNECTED = 0;
    public static final int ERROR_CAMERA_INVALID_ERROR = -1;
    public static final int ERROR_CAMERA_REQUEST = 3;
    public static final int ERROR_CAMERA_RESULT = 4;
    public static final int ERROR_CAMERA_SERVICE = 2;

    void onCaptureStarted(android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, long j) throws android.os.RemoteException;

    void onDeviceError(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) throws android.os.RemoteException;

    void onDeviceIdle() throws android.os.RemoteException;

    void onPrepared(int i) throws android.os.RemoteException;

    void onRepeatingRequestError(long j, int i) throws android.os.RemoteException;

    void onRequestQueueEmpty() throws android.os.RemoteException;

    void onResultReceived(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.ICameraDeviceCallbacks {
        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceError(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceIdle() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onCaptureStarted(android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onResultReceived(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onPrepared(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRepeatingRequestError(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRequestQueueEmpty() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.ICameraDeviceCallbacks {
        public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceCallbacks";
        static final int TRANSACTION_onCaptureStarted = 3;
        static final int TRANSACTION_onDeviceError = 1;
        static final int TRANSACTION_onDeviceIdle = 2;
        static final int TRANSACTION_onPrepared = 5;
        static final int TRANSACTION_onRepeatingRequestError = 6;
        static final int TRANSACTION_onRequestQueueEmpty = 7;
        static final int TRANSACTION_onResultReceived = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.camera2.ICameraDeviceCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.ICameraDeviceCallbacks)) {
                return (android.hardware.camera2.ICameraDeviceCallbacks) queryLocalInterface;
            }
            return new android.hardware.camera2.ICameraDeviceCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDeviceError";
                case 2:
                    return "onDeviceIdle";
                case 3:
                    return "onCaptureStarted";
                case 4:
                    return "onResultReceived";
                case 5:
                    return "onPrepared";
                case 6:
                    return "onRepeatingRequestError";
                case 7:
                    return "onRequestQueueEmpty";
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
                    int readInt = parcel.readInt();
                    android.hardware.camera2.impl.CaptureResultExtras captureResultExtras = (android.hardware.camera2.impl.CaptureResultExtras) parcel.readTypedObject(android.hardware.camera2.impl.CaptureResultExtras.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceError(readInt, captureResultExtras);
                    return true;
                case 2:
                    onDeviceIdle();
                    return true;
                case 3:
                    android.hardware.camera2.impl.CaptureResultExtras captureResultExtras2 = (android.hardware.camera2.impl.CaptureResultExtras) parcel.readTypedObject(android.hardware.camera2.impl.CaptureResultExtras.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureStarted(captureResultExtras2, readLong);
                    return true;
                case 4:
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    android.hardware.camera2.impl.CaptureResultExtras captureResultExtras3 = (android.hardware.camera2.impl.CaptureResultExtras) parcel.readTypedObject(android.hardware.camera2.impl.CaptureResultExtras.CREATOR);
                    android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr = (android.hardware.camera2.impl.PhysicalCaptureResultInfo[]) parcel.createTypedArray(android.hardware.camera2.impl.PhysicalCaptureResultInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResultReceived(cameraMetadataNative, captureResultExtras3, physicalCaptureResultInfoArr);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPrepared(readInt2);
                    return true;
                case 6:
                    long readLong2 = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRepeatingRequestError(readLong2, readInt3);
                    return true;
                case 7:
                    onRequestQueueEmpty();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.ICameraDeviceCallbacks {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onDeviceError(int i, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(captureResultExtras, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onDeviceIdle() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onCaptureStarted(android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(captureResultExtras, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onResultReceived(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    obtain.writeTypedObject(captureResultExtras, 0);
                    obtain.writeTypedArray(physicalCaptureResultInfoArr, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onPrepared(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onRepeatingRequestError(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onRequestQueueEmpty() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceCallbacks.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
