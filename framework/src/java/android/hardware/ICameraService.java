package android.hardware;

/* loaded from: classes.dex */
public interface ICameraService extends android.os.IInterface {
    public static final int API_VERSION_1 = 1;
    public static final int API_VERSION_2 = 2;
    public static final int CAMERA_TYPE_ALL = 1;
    public static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    public static final int DEVICE_STATE_BACK_COVERED = 1;
    public static final int DEVICE_STATE_FOLDED = 4;
    public static final int DEVICE_STATE_FRONT_COVERED = 2;
    public static final int DEVICE_STATE_LAST_FRAMEWORK_BIT = Integer.MIN_VALUE;
    public static final int DEVICE_STATE_NORMAL = 0;
    public static final int ERROR_ALREADY_EXISTS = 2;
    public static final int ERROR_CAMERA_IN_USE = 7;
    public static final int ERROR_DEPRECATED_HAL = 9;
    public static final int ERROR_DISABLED = 6;
    public static final int ERROR_DISCONNECTED = 4;
    public static final int ERROR_ILLEGAL_ARGUMENT = 3;
    public static final int ERROR_INVALID_OPERATION = 10;
    public static final int ERROR_MAX_CAMERAS_IN_USE = 8;
    public static final int ERROR_PERMISSION_DENIED = 1;
    public static final int ERROR_TIMED_OUT = 5;
    public static final int EVENT_NONE = 0;
    public static final int EVENT_USB_DEVICE_ATTACHED = 2;
    public static final int EVENT_USB_DEVICE_DETACHED = 3;
    public static final int EVENT_USER_SWITCHED = 1;
    public static final int USE_CALLING_PID = -1;
    public static final int USE_CALLING_UID = -1;

    android.hardware.CameraStatus[] addListener(android.hardware.ICameraServiceListener iCameraServiceListener) throws android.os.RemoteException;

    android.hardware.ICamera connect(android.hardware.ICameraClient iCameraClient, int i, java.lang.String str, int i2, int i3, int i4, boolean z, boolean z2) throws android.os.RemoteException;

    android.hardware.camera2.ICameraDeviceUser connectDevice(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(java.lang.String str, int i) throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative getCameraCharacteristics(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    android.hardware.CameraInfo getCameraInfo(int i, boolean z) throws android.os.RemoteException;

    android.hardware.camera2.params.VendorTagDescriptorCache getCameraVendorTagCache() throws android.os.RemoteException;

    android.hardware.camera2.params.VendorTagDescriptor getCameraVendorTagDescriptor() throws android.os.RemoteException;

    android.hardware.camera2.utils.ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws android.os.RemoteException;

    java.lang.String getLegacyParameters(int i) throws android.os.RemoteException;

    int getNumberOfCameras(int i) throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative getSessionCharacteristics(java.lang.String str, int i, boolean z, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException;

    int getTorchStrengthLevel(java.lang.String str) throws android.os.RemoteException;

    android.hardware.camera2.ICameraInjectionSession injectCamera(java.lang.String str, java.lang.String str2, java.lang.String str3, android.hardware.camera2.ICameraInjectionCallback iCameraInjectionCallback) throws android.os.RemoteException;

    void injectSessionParams(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    boolean isConcurrentSessionConfigurationSupported(android.hardware.camera2.utils.CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i) throws android.os.RemoteException;

    boolean isHiddenPhysicalCamera(java.lang.String str) throws android.os.RemoteException;

    boolean isSessionConfigurationWithParametersSupported(java.lang.String str, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException;

    void notifyDeviceStateChange(long j) throws android.os.RemoteException;

    void notifyDisplayConfigurationChange() throws android.os.RemoteException;

    void notifySystemEvent(int i, int[] iArr) throws android.os.RemoteException;

    void remapCameraIds(android.hardware.CameraIdRemapping cameraIdRemapping) throws android.os.RemoteException;

    void removeListener(android.hardware.ICameraServiceListener iCameraServiceListener) throws android.os.RemoteException;

    java.lang.String reportExtensionSessionStats(android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats) throws android.os.RemoteException;

    void setTorchMode(java.lang.String str, boolean z, android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean supportsCameraApi(java.lang.String str, int i) throws android.os.RemoteException;

    void turnOnTorchWithStrengthLevel(java.lang.String str, int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.hardware.ICameraService {
        @Override // android.hardware.ICameraService
        public int getNumberOfCameras(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.CameraInfo getCameraInfo(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.ICamera connect(android.hardware.ICameraClient iCameraClient, int i, java.lang.String str, int i2, int i3, int i4, boolean z, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.ICameraDeviceUser connectDevice(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.CameraStatus[] addListener(android.hardware.ICameraServiceListener iCameraServiceListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.utils.ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean isConcurrentSessionConfigurationSupported(android.hardware.camera2.utils.CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void remapCameraIds(android.hardware.CameraIdRemapping cameraIdRemapping) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void injectSessionParams(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void removeListener(android.hardware.ICameraServiceListener iCameraServiceListener) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.impl.CameraMetadataNative getCameraCharacteristics(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.params.VendorTagDescriptor getCameraVendorTagDescriptor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.params.VendorTagDescriptorCache getCameraVendorTagCache() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public java.lang.String getLegacyParameters(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean supportsCameraApi(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public boolean isHiddenPhysicalCamera(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.ICameraInjectionSession injectCamera(java.lang.String str, java.lang.String str2, java.lang.String str3, android.hardware.camera2.ICameraInjectionCallback iCameraInjectionCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public void setTorchMode(java.lang.String str, boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void turnOnTorchWithStrengthLevel(java.lang.String str, int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public int getTorchStrengthLevel(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraService
        public void notifySystemEvent(int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDisplayConfigurationChange() throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceStateChange(long j) throws android.os.RemoteException {
        }

        @Override // android.hardware.ICameraService
        public java.lang.String reportExtensionSessionStats(android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean isSessionConfigurationWithParametersSupported(java.lang.String str, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public android.hardware.camera2.impl.CameraMetadataNative getSessionCharacteristics(java.lang.String str, int i, boolean z, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.ICameraService {
        public static final java.lang.String DESCRIPTOR = "android.hardware.ICameraService";
        static final int TRANSACTION_addListener = 5;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_connectDevice = 4;
        static final int TRANSACTION_createDefaultRequest = 25;
        static final int TRANSACTION_getCameraCharacteristics = 11;
        static final int TRANSACTION_getCameraInfo = 2;
        static final int TRANSACTION_getCameraVendorTagCache = 13;
        static final int TRANSACTION_getCameraVendorTagDescriptor = 12;
        static final int TRANSACTION_getConcurrentCameraIds = 6;
        static final int TRANSACTION_getLegacyParameters = 14;
        static final int TRANSACTION_getNumberOfCameras = 1;
        static final int TRANSACTION_getSessionCharacteristics = 27;
        static final int TRANSACTION_getTorchStrengthLevel = 20;
        static final int TRANSACTION_injectCamera = 17;
        static final int TRANSACTION_injectSessionParams = 9;
        static final int TRANSACTION_isConcurrentSessionConfigurationSupported = 7;
        static final int TRANSACTION_isHiddenPhysicalCamera = 16;
        static final int TRANSACTION_isSessionConfigurationWithParametersSupported = 26;
        static final int TRANSACTION_notifyDeviceStateChange = 23;
        static final int TRANSACTION_notifyDisplayConfigurationChange = 22;
        static final int TRANSACTION_notifySystemEvent = 21;
        static final int TRANSACTION_remapCameraIds = 8;
        static final int TRANSACTION_removeListener = 10;
        static final int TRANSACTION_reportExtensionSessionStats = 24;
        static final int TRANSACTION_setTorchMode = 18;
        static final int TRANSACTION_supportsCameraApi = 15;
        static final int TRANSACTION_turnOnTorchWithStrengthLevel = 19;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.ICameraService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.ICameraService)) {
                return (android.hardware.ICameraService) queryLocalInterface;
            }
            return new android.hardware.ICameraService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getNumberOfCameras";
                case 2:
                    return "getCameraInfo";
                case 3:
                    return android.media.MediaMetrics.Value.CONNECT;
                case 4:
                    return "connectDevice";
                case 5:
                    return "addListener";
                case 6:
                    return "getConcurrentCameraIds";
                case 7:
                    return "isConcurrentSessionConfigurationSupported";
                case 8:
                    return "remapCameraIds";
                case 9:
                    return "injectSessionParams";
                case 10:
                    return "removeListener";
                case 11:
                    return "getCameraCharacteristics";
                case 12:
                    return "getCameraVendorTagDescriptor";
                case 13:
                    return "getCameraVendorTagCache";
                case 14:
                    return "getLegacyParameters";
                case 15:
                    return "supportsCameraApi";
                case 16:
                    return "isHiddenPhysicalCamera";
                case 17:
                    return "injectCamera";
                case 18:
                    return "setTorchMode";
                case 19:
                    return "turnOnTorchWithStrengthLevel";
                case 20:
                    return "getTorchStrengthLevel";
                case 21:
                    return "notifySystemEvent";
                case 22:
                    return "notifyDisplayConfigurationChange";
                case 23:
                    return "notifyDeviceStateChange";
                case 24:
                    return "reportExtensionSessionStats";
                case 25:
                    return "createDefaultRequest";
                case 26:
                    return "isSessionConfigurationWithParametersSupported";
                case 27:
                    return "getSessionCharacteristics";
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
                    parcel.enforceNoDataAvail();
                    int numberOfCameras = getNumberOfCameras(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(numberOfCameras);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.CameraInfo cameraInfo = getCameraInfo(readInt2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraInfo, 1);
                    return true;
                case 3:
                    android.hardware.ICameraClient asInterface = android.hardware.ICameraClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.ICamera connect = connect(asInterface, readInt3, readString, readInt4, readInt5, readInt6, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(connect);
                    return true;
                case 4:
                    android.hardware.camera2.ICameraDeviceCallbacks asInterface2 = android.hardware.camera2.ICameraDeviceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.ICameraDeviceUser connectDevice = connectDevice(asInterface2, readString2, readString3, readString4, readInt7, readInt8, readInt9, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(connectDevice);
                    return true;
                case 5:
                    android.hardware.ICameraServiceListener asInterface3 = android.hardware.ICameraServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.CameraStatus[] addListener = addListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(addListener, 1);
                    return true;
                case 6:
                    android.hardware.camera2.utils.ConcurrentCameraIdCombination[] concurrentCameraIds = getConcurrentCameraIds();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(concurrentCameraIds, 1);
                    return true;
                case 7:
                    android.hardware.camera2.utils.CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr = (android.hardware.camera2.utils.CameraIdAndSessionConfiguration[]) parcel.createTypedArray(android.hardware.camera2.utils.CameraIdAndSessionConfiguration.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConcurrentSessionConfigurationSupported = isConcurrentSessionConfigurationSupported(cameraIdAndSessionConfigurationArr, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConcurrentSessionConfigurationSupported);
                    return true;
                case 8:
                    android.hardware.CameraIdRemapping cameraIdRemapping = (android.hardware.CameraIdRemapping) parcel.readTypedObject(android.hardware.CameraIdRemapping.CREATOR);
                    parcel.enforceNoDataAvail();
                    remapCameraIds(cameraIdRemapping);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString5 = parcel.readString();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectSessionParams(readString5, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.ICameraServiceListener asInterface4 = android.hardware.ICameraServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString6 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative cameraCharacteristics = getCameraCharacteristics(readString6, readInt11, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraCharacteristics, 1);
                    return true;
                case 12:
                    android.hardware.camera2.params.VendorTagDescriptor cameraVendorTagDescriptor = getCameraVendorTagDescriptor();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraVendorTagDescriptor, 1);
                    return true;
                case 13:
                    android.hardware.camera2.params.VendorTagDescriptorCache cameraVendorTagCache = getCameraVendorTagCache();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraVendorTagCache, 1);
                    return true;
                case 14:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String legacyParameters = getLegacyParameters(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeString(legacyParameters);
                    return true;
                case 15:
                    java.lang.String readString7 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean supportsCameraApi = supportsCameraApi(readString7, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsCameraApi);
                    return true;
                case 16:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHiddenPhysicalCamera = isHiddenPhysicalCamera(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHiddenPhysicalCamera);
                    return true;
                case 17:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    android.hardware.camera2.ICameraInjectionCallback asInterface5 = android.hardware.camera2.ICameraInjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.ICameraInjectionSession injectCamera = injectCamera(readString9, readString10, readString11, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(injectCamera);
                    return true;
                case 18:
                    java.lang.String readString12 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setTorchMode(readString12, readBoolean6, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString13 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    turnOnTorchWithStrengthLevel(readString13, readInt14, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int torchStrengthLevel = getTorchStrengthLevel(readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(torchStrengthLevel);
                    return true;
                case 21:
                    int readInt15 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifySystemEvent(readInt15, createIntArray);
                    return true;
                case 22:
                    notifyDisplayConfigurationChange();
                    return true;
                case 23:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyDeviceStateChange(readLong);
                    return true;
                case 24:
                    android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats = (android.hardware.CameraExtensionSessionStats) parcel.readTypedObject(android.hardware.CameraExtensionSessionStats.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String reportExtensionSessionStats = reportExtensionSessionStats(cameraExtensionSessionStats);
                    parcel2.writeNoException();
                    parcel2.writeString(reportExtensionSessionStats);
                    return true;
                case 25:
                    java.lang.String readString15 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest = createDefaultRequest(readString15, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createDefaultRequest, 1);
                    return true;
                case 26:
                    java.lang.String readString16 = parcel.readString();
                    android.hardware.camera2.params.SessionConfiguration sessionConfiguration = (android.hardware.camera2.params.SessionConfiguration) parcel.readTypedObject(android.hardware.camera2.params.SessionConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSessionConfigurationWithParametersSupported = isSessionConfigurationWithParametersSupported(readString16, sessionConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSessionConfigurationWithParametersSupported);
                    return true;
                case 27:
                    java.lang.String readString17 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    android.hardware.camera2.params.SessionConfiguration sessionConfiguration2 = (android.hardware.camera2.params.SessionConfiguration) parcel.readTypedObject(android.hardware.camera2.params.SessionConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative sessionCharacteristics = getSessionCharacteristics(readString17, readInt17, readBoolean7, sessionConfiguration2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionCharacteristics, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.ICameraService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.ICameraService.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.ICameraService
            public int getNumberOfCameras(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.CameraInfo getCameraInfo(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.CameraInfo) obtain2.readTypedObject(android.hardware.CameraInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.ICamera connect(android.hardware.ICameraClient iCameraClient, int i, java.lang.String str, int i2, int i3, int i4, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.ICamera.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.ICameraDeviceUser connectDevice(android.hardware.camera2.ICameraDeviceCallbacks iCameraDeviceCallbacks, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraDeviceCallbacks);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.ICameraDeviceUser.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.CameraStatus[] addListener(android.hardware.ICameraServiceListener iCameraServiceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraServiceListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.CameraStatus[]) obtain2.createTypedArray(android.hardware.CameraStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.utils.ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.utils.ConcurrentCameraIdCombination[]) obtain2.createTypedArray(android.hardware.camera2.utils.ConcurrentCameraIdCombination.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isConcurrentSessionConfigurationSupported(android.hardware.camera2.utils.CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(cameraIdAndSessionConfigurationArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void remapCameraIds(android.hardware.CameraIdRemapping cameraIdRemapping) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraIdRemapping, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void injectSessionParams(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeListener(android.hardware.ICameraServiceListener iCameraServiceListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraServiceListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.impl.CameraMetadataNative getCameraCharacteristics(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.params.VendorTagDescriptor getCameraVendorTagDescriptor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.params.VendorTagDescriptor) obtain2.readTypedObject(android.hardware.camera2.params.VendorTagDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.params.VendorTagDescriptorCache getCameraVendorTagCache() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.params.VendorTagDescriptorCache) obtain2.readTypedObject(android.hardware.camera2.params.VendorTagDescriptorCache.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public java.lang.String getLegacyParameters(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean supportsCameraApi(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenPhysicalCamera(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.ICameraInjectionSession injectCamera(java.lang.String str, java.lang.String str2, java.lang.String str3, android.hardware.camera2.ICameraInjectionCallback iCameraInjectionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iCameraInjectionCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.ICameraInjectionSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setTorchMode(java.lang.String str, boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void turnOnTorchWithStrengthLevel(java.lang.String str, int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public int getTorchStrengthLevel(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifySystemEvent(int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDisplayConfigurationChange() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChange(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public java.lang.String reportExtensionSessionStats(android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraExtensionSessionStats, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.impl.CameraMetadataNative createDefaultRequest(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isSessionConfigurationWithParametersSupported(java.lang.String str, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(sessionConfiguration, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public android.hardware.camera2.impl.CameraMetadataNative getSessionCharacteristics(java.lang.String str, int i, boolean z, android.hardware.camera2.params.SessionConfiguration sessionConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.ICameraService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(sessionConfiguration, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 26;
        }
    }
}
