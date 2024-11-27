package android.hardware.camera2;

/* loaded from: classes.dex */
public interface ICameraDeviceUser extends android.os.IInterface {
    public static final int AUDIO_RESTRICTION_NONE = 0;
    public static final int AUDIO_RESTRICTION_VIBRATION = 1;
    public static final int AUDIO_RESTRICTION_VIBRATION_SOUND = 3;
    public static final int CONSTRAINED_HIGH_SPEED_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int NO_IN_FLIGHT_REPEATING_FRAMES = -1;
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;
    public static final int VENDOR_MODE_START = 32768;

    void beginConfigure() throws android.os.RemoteException;

    long cancelRequest(int i) throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(int i) throws android.os.RemoteException;

    int createInputStream(int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    int createStream(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException;

    void deleteStream(int i) throws android.os.RemoteException;

    void disconnect() throws android.os.RemoteException;

    int[] endConfigure(int i, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, long j) throws android.os.RemoteException;

    void finalizeOutputConfigurations(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException;

    long flush() throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative getCameraInfo() throws android.os.RemoteException;

    int getGlobalAudioRestriction() throws android.os.RemoteException;

    android.view.Surface getInputSurface() throws android.os.RemoteException;

    boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException;

    void prepare(int i) throws android.os.RemoteException;

    void prepare2(int i, int i2) throws android.os.RemoteException;

    void setCameraAudioRestriction(int i) throws android.os.RemoteException;

    android.hardware.camera2.utils.SubmitInfo submitRequest(android.hardware.camera2.CaptureRequest captureRequest, boolean z) throws android.os.RemoteException;

    android.hardware.camera2.utils.SubmitInfo submitRequestList(android.hardware.camera2.CaptureRequest[] captureRequestArr, boolean z) throws android.os.RemoteException;

    android.hardware.camera2.ICameraOfflineSession switchToOffline(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws android.os.RemoteException;

    void tearDown(int i) throws android.os.RemoteException;

    void updateOutputConfiguration(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException;

    void waitUntilIdle() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.ICameraDeviceUser {
        @Override // android.hardware.camera2.ICameraDeviceUser
        public void disconnect() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public android.hardware.camera2.utils.SubmitInfo submitRequest(android.hardware.camera2.CaptureRequest captureRequest, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public android.hardware.camera2.utils.SubmitInfo submitRequestList(android.hardware.camera2.CaptureRequest[] captureRequestArr, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public long cancelRequest(int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void beginConfigure() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int[] endConfigure(int i, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void deleteStream(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int createStream(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int createInputStream(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public android.view.Surface getInputSurface() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public android.hardware.camera2.impl.CameraMetadataNative getCameraInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void waitUntilIdle() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public long flush() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void prepare(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void tearDown(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void prepare2(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void updateOutputConfiguration(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void finalizeOutputConfigurations(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void setCameraAudioRestriction(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int getGlobalAudioRestriction() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public android.hardware.camera2.ICameraOfflineSession switchToOffline(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.ICameraDeviceUser {
        public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceUser";
        static final int TRANSACTION_beginConfigure = 5;
        static final int TRANSACTION_cancelRequest = 4;
        static final int TRANSACTION_createDefaultRequest = 12;
        static final int TRANSACTION_createInputStream = 10;
        static final int TRANSACTION_createStream = 9;
        static final int TRANSACTION_deleteStream = 8;
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_endConfigure = 6;
        static final int TRANSACTION_finalizeOutputConfigurations = 20;
        static final int TRANSACTION_flush = 15;
        static final int TRANSACTION_getCameraInfo = 13;
        static final int TRANSACTION_getGlobalAudioRestriction = 22;
        static final int TRANSACTION_getInputSurface = 11;
        static final int TRANSACTION_isSessionConfigurationSupported = 7;
        static final int TRANSACTION_prepare = 16;
        static final int TRANSACTION_prepare2 = 18;
        static final int TRANSACTION_setCameraAudioRestriction = 21;
        static final int TRANSACTION_submitRequest = 2;
        static final int TRANSACTION_submitRequestList = 3;
        static final int TRANSACTION_switchToOffline = 23;
        static final int TRANSACTION_tearDown = 17;
        static final int TRANSACTION_updateOutputConfiguration = 19;
        static final int TRANSACTION_waitUntilIdle = 14;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.camera2.ICameraDeviceUser asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.ICameraDeviceUser)) {
                return (android.hardware.camera2.ICameraDeviceUser) queryLocalInterface;
            }
            return new android.hardware.camera2.ICameraDeviceUser.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.MediaMetrics.Value.DISCONNECT;
                case 2:
                    return "submitRequest";
                case 3:
                    return "submitRequestList";
                case 4:
                    return "cancelRequest";
                case 5:
                    return "beginConfigure";
                case 6:
                    return "endConfigure";
                case 7:
                    return "isSessionConfigurationSupported";
                case 8:
                    return "deleteStream";
                case 9:
                    return "createStream";
                case 10:
                    return "createInputStream";
                case 11:
                    return "getInputSurface";
                case 12:
                    return "createDefaultRequest";
                case 13:
                    return "getCameraInfo";
                case 14:
                    return "waitUntilIdle";
                case 15:
                    return "flush";
                case 16:
                    return "prepare";
                case 17:
                    return "tearDown";
                case 18:
                    return "prepare2";
                case 19:
                    return "updateOutputConfiguration";
                case 20:
                    return "finalizeOutputConfigurations";
                case 21:
                    return "setCameraAudioRestriction";
                case 22:
                    return "getGlobalAudioRestriction";
                case 23:
                    return "switchToOffline";
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
                    disconnect();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.hardware.camera2.CaptureRequest captureRequest = (android.hardware.camera2.CaptureRequest) parcel.readTypedObject(android.hardware.camera2.CaptureRequest.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.utils.SubmitInfo submitRequest = submitRequest(captureRequest, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitRequest, 1);
                    return true;
                case 3:
                    android.hardware.camera2.CaptureRequest[] captureRequestArr = (android.hardware.camera2.CaptureRequest[]) parcel.createTypedArray(android.hardware.camera2.CaptureRequest.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.utils.SubmitInfo submitRequestList = submitRequestList(captureRequestArr, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitRequestList, 1);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long cancelRequest = cancelRequest(readInt);
                    parcel2.writeNoException();
                    parcel2.writeLong(cancelRequest);
                    return true;
                case 5:
                    beginConfigure();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int[] endConfigure = endConfigure(readInt2, cameraMetadataNative, readLong);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(endConfigure);
                    return true;
                case 7:
                    android.hardware.camera2.params.SessionConfiguration sessionConfiguration = (android.hardware.camera2.params.SessionConfiguration) parcel.readTypedObject(android.hardware.camera2.params.SessionConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSessionConfigurationSupported = isSessionConfigurationSupported(sessionConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSessionConfigurationSupported);
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStream(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.camera2.params.OutputConfiguration outputConfiguration = (android.hardware.camera2.params.OutputConfiguration) parcel.readTypedObject(android.hardware.camera2.params.OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createStream = createStream(outputConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(createStream);
                    return true;
                case 10:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int createInputStream = createInputStream(readInt4, readInt5, readInt6, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createInputStream);
                    return true;
                case 11:
                    android.view.Surface inputSurface = getInputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputSurface, 1);
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest = createDefaultRequest(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createDefaultRequest, 1);
                    return true;
                case 13:
                    android.hardware.camera2.impl.CameraMetadataNative cameraInfo = getCameraInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraInfo, 1);
                    return true;
                case 14:
                    waitUntilIdle();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long flush = flush();
                    parcel2.writeNoException();
                    parcel2.writeLong(flush);
                    return true;
                case 16:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepare(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tearDown(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepare2(readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt12 = parcel.readInt();
                    android.hardware.camera2.params.OutputConfiguration outputConfiguration2 = (android.hardware.camera2.params.OutputConfiguration) parcel.readTypedObject(android.hardware.camera2.params.OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateOutputConfiguration(readInt12, outputConfiguration2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt13 = parcel.readInt();
                    android.hardware.camera2.params.OutputConfiguration outputConfiguration3 = (android.hardware.camera2.params.OutputConfiguration) parcel.readTypedObject(android.hardware.camera2.params.OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    finalizeOutputConfigurations(readInt13, outputConfiguration3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraAudioRestriction(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int globalAudioRestriction = getGlobalAudioRestriction();
                    parcel2.writeNoException();
                    parcel2.writeInt(globalAudioRestriction);
                    return true;
                case 23:
                    android.hardware.camera2.ICameraDeviceCallbacks asInterface = android.hardware.camera2.ICameraDeviceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.ICameraOfflineSession switchToOffline = switchToOffline(asInterface, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(switchToOffline);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.ICameraDeviceUser {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void disconnect() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public android.hardware.camera2.utils.SubmitInfo submitRequest(android.hardware.camera2.CaptureRequest captureRequest, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(captureRequest, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.utils.SubmitInfo) obtain2.readTypedObject(android.hardware.camera2.utils.SubmitInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public android.hardware.camera2.utils.SubmitInfo submitRequestList(android.hardware.camera2.CaptureRequest[] captureRequestArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(captureRequestArr, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.utils.SubmitInfo) obtain2.readTypedObject(android.hardware.camera2.utils.SubmitInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public long cancelRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void beginConfigure() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int[] endConfigure(int i, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public boolean isSessionConfigurationSupported(android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(sessionConfiguration, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void deleteStream(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int createStream(android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int createInputStream(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public android.view.Surface getInputSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.Surface) obtain2.readTypedObject(android.view.Surface.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public android.hardware.camera2.impl.CameraMetadataNative getCameraInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void waitUntilIdle() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public long flush() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void prepare(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void tearDown(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void prepare2(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void updateOutputConfiguration(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void finalizeOutputConfigurations(int i, android.hardware.camera2.params.OutputConfiguration outputConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void setCameraAudioRestriction(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int getGlobalAudioRestriction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public android.hardware.camera2.ICameraOfflineSession switchToOffline(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.ICameraDeviceUser.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraDeviceCallbacks);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.ICameraOfflineSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
