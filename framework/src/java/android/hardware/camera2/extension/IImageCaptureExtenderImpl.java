package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IImageCaptureExtenderImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IImageCaptureExtenderImpl";

    android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys() throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys() throws android.os.RemoteException;

    android.hardware.camera2.extension.ICaptureProcessorImpl getCaptureProcessor() throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.CaptureStageImpl> getCaptureStages() throws android.os.RemoteException;

    android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(android.hardware.camera2.extension.Size size) throws android.os.RemoteException;

    int getMaxCaptureStage() throws android.os.RemoteException;

    android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException;

    int getSessionType() throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.SizeList> getSupportedResolutions() throws android.os.RemoteException;

    void init(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    boolean isCaptureProcessProgressAvailable() throws android.os.RemoteException;

    boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    boolean isPostviewAvailable() throws android.os.RemoteException;

    void onDeInit(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.hardware.camera2.extension.CaptureStageImpl onDisableSession() throws android.os.RemoteException;

    android.hardware.camera2.extension.CaptureStageImpl onEnableSession() throws android.os.RemoteException;

    void onInit(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    android.hardware.camera2.extension.CaptureStageImpl onPresetSession() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IImageCaptureExtenderImpl {
        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void onInit(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void onDeInit(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl onPresetSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl onEnableSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl onDisableSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public int getSessionType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public void init(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.extension.ICaptureProcessorImpl getCaptureProcessor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public java.util.List<android.hardware.camera2.extension.CaptureStageImpl> getCaptureStages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public int getMaxCaptureStage() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedResolutions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isCaptureProcessProgressAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
        public boolean isPostviewAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IImageCaptureExtenderImpl {
        static final int TRANSACTION_getAvailableCaptureRequestKeys = 15;
        static final int TRANSACTION_getAvailableCaptureResultKeys = 16;
        static final int TRANSACTION_getCaptureProcessor = 9;
        static final int TRANSACTION_getCaptureStages = 10;
        static final int TRANSACTION_getEstimatedCaptureLatencyRange = 14;
        static final int TRANSACTION_getMaxCaptureStage = 11;
        static final int TRANSACTION_getRealtimeCaptureLatency = 18;
        static final int TRANSACTION_getSessionType = 6;
        static final int TRANSACTION_getSupportedPostviewResolutions = 13;
        static final int TRANSACTION_getSupportedResolutions = 12;
        static final int TRANSACTION_init = 8;
        static final int TRANSACTION_isCaptureProcessProgressAvailable = 17;
        static final int TRANSACTION_isExtensionAvailable = 7;
        static final int TRANSACTION_isPostviewAvailable = 19;
        static final int TRANSACTION_onDeInit = 2;
        static final int TRANSACTION_onDisableSession = 5;
        static final int TRANSACTION_onEnableSession = 4;
        static final int TRANSACTION_onInit = 1;
        static final int TRANSACTION_onPresetSession = 3;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IImageCaptureExtenderImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IImageCaptureExtenderImpl)) {
                return (android.hardware.camera2.extension.IImageCaptureExtenderImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IImageCaptureExtenderImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInit";
                case 2:
                    return "onDeInit";
                case 3:
                    return "onPresetSession";
                case 4:
                    return "onEnableSession";
                case 5:
                    return "onDisableSession";
                case 6:
                    return "getSessionType";
                case 7:
                    return "isExtensionAvailable";
                case 8:
                    return "init";
                case 9:
                    return "getCaptureProcessor";
                case 10:
                    return "getCaptureStages";
                case 11:
                    return "getMaxCaptureStage";
                case 12:
                    return "getSupportedResolutions";
                case 13:
                    return "getSupportedPostviewResolutions";
                case 14:
                    return "getEstimatedCaptureLatencyRange";
                case 15:
                    return "getAvailableCaptureRequestKeys";
                case 16:
                    return "getAvailableCaptureResultKeys";
                case 17:
                    return "isCaptureProcessProgressAvailable";
                case 18:
                    return "getRealtimeCaptureLatency";
                case 19:
                    return "isPostviewAvailable";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    onInit(readStrongBinder, readString, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onDeInit(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.camera2.extension.CaptureStageImpl onPresetSession = onPresetSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onPresetSession, 1);
                    return true;
                case 4:
                    android.hardware.camera2.extension.CaptureStageImpl onEnableSession = onEnableSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onEnableSession, 1);
                    return true;
                case 5:
                    android.hardware.camera2.extension.CaptureStageImpl onDisableSession = onDisableSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onDisableSession, 1);
                    return true;
                case 6:
                    int sessionType = getSessionType();
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionType);
                    return true;
                case 7:
                    java.lang.String readString2 = parcel.readString();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2 = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isExtensionAvailable = isExtensionAvailable(readString2, cameraMetadataNative2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExtensionAvailable);
                    return true;
                case 8:
                    java.lang.String readString3 = parcel.readString();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative3 = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    init(readString3, cameraMetadataNative3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.camera2.extension.ICaptureProcessorImpl captureProcessor = getCaptureProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(captureProcessor);
                    return true;
                case 10:
                    java.util.List<android.hardware.camera2.extension.CaptureStageImpl> captureStages = getCaptureStages();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(captureStages, 1);
                    return true;
                case 11:
                    int maxCaptureStage = getMaxCaptureStage();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxCaptureStage);
                    return true;
                case 12:
                    java.util.List<android.hardware.camera2.extension.SizeList> supportedResolutions = getSupportedResolutions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedResolutions, 1);
                    return true;
                case 13:
                    android.hardware.camera2.extension.Size size = (android.hardware.camera2.extension.Size) parcel.readTypedObject(android.hardware.camera2.extension.Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.camera2.extension.SizeList> supportedPostviewResolutions = getSupportedPostviewResolutions(size);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPostviewResolutions, 1);
                    return true;
                case 14:
                    android.hardware.camera2.extension.Size size2 = (android.hardware.camera2.extension.Size) parcel.readTypedObject(android.hardware.camera2.extension.Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.extension.LatencyRange estimatedCaptureLatencyRange = getEstimatedCaptureLatencyRange(size2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(estimatedCaptureLatencyRange, 1);
                    return true;
                case 15:
                    android.hardware.camera2.impl.CameraMetadataNative availableCaptureRequestKeys = getAvailableCaptureRequestKeys();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureRequestKeys, 1);
                    return true;
                case 16:
                    android.hardware.camera2.impl.CameraMetadataNative availableCaptureResultKeys = getAvailableCaptureResultKeys();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureResultKeys, 1);
                    return true;
                case 17:
                    boolean isCaptureProcessProgressAvailable = isCaptureProcessProgressAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaptureProcessProgressAvailable);
                    return true;
                case 18:
                    android.hardware.camera2.extension.LatencyPair realtimeCaptureLatency = getRealtimeCaptureLatency();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(realtimeCaptureLatency, 1);
                    return true;
                case 19:
                    boolean isPostviewAvailable = isPostviewAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPostviewAvailable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IImageCaptureExtenderImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void onInit(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void onDeInit(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl onPresetSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl onEnableSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl onDisableSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public int getSessionType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public void init(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.extension.ICaptureProcessorImpl getCaptureProcessor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.ICaptureProcessorImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public java.util.List<android.hardware.camera2.extension.CaptureStageImpl> getCaptureStages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public int getMaxCaptureStage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedResolutions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.LatencyRange) obtain2.readTypedObject(android.hardware.camera2.extension.LatencyRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.LatencyPair) obtain2.readTypedObject(android.hardware.camera2.extension.LatencyPair.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IImageCaptureExtenderImpl
            public boolean isPostviewAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IImageCaptureExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
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
            return 18;
        }
    }
}
