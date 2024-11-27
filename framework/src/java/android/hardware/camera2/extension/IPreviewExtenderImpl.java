package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IPreviewExtenderImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IPreviewExtenderImpl";
    public static final int PROCESSOR_TYPE_IMAGE_PROCESSOR = 1;
    public static final int PROCESSOR_TYPE_NONE = 2;
    public static final int PROCESSOR_TYPE_REQUEST_UPDATE_ONLY = 0;

    android.hardware.camera2.extension.CaptureStageImpl getCaptureStage() throws android.os.RemoteException;

    android.hardware.camera2.extension.IPreviewImageProcessorImpl getPreviewImageProcessor() throws android.os.RemoteException;

    int getProcessorType() throws android.os.RemoteException;

    android.hardware.camera2.extension.IRequestUpdateProcessorImpl getRequestUpdateProcessor() throws android.os.RemoteException;

    int getSessionType() throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.SizeList> getSupportedResolutions() throws android.os.RemoteException;

    void init(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    void onDeInit(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.hardware.camera2.extension.CaptureStageImpl onDisableSession() throws android.os.RemoteException;

    android.hardware.camera2.extension.CaptureStageImpl onEnableSession() throws android.os.RemoteException;

    void onInit(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException;

    android.hardware.camera2.extension.CaptureStageImpl onPresetSession() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IPreviewExtenderImpl {
        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public void onInit(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public void onDeInit(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl onPresetSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl onEnableSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl onDisableSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public void init(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public android.hardware.camera2.extension.CaptureStageImpl getCaptureStage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public int getSessionType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public int getProcessorType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public android.hardware.camera2.extension.IPreviewImageProcessorImpl getPreviewImageProcessor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public android.hardware.camera2.extension.IRequestUpdateProcessorImpl getRequestUpdateProcessor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedResolutions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IPreviewExtenderImpl {
        static final int TRANSACTION_getCaptureStage = 8;
        static final int TRANSACTION_getPreviewImageProcessor = 11;
        static final int TRANSACTION_getProcessorType = 10;
        static final int TRANSACTION_getRequestUpdateProcessor = 12;
        static final int TRANSACTION_getSessionType = 9;
        static final int TRANSACTION_getSupportedResolutions = 13;
        static final int TRANSACTION_init = 6;
        static final int TRANSACTION_isExtensionAvailable = 7;
        static final int TRANSACTION_onDeInit = 2;
        static final int TRANSACTION_onDisableSession = 5;
        static final int TRANSACTION_onEnableSession = 4;
        static final int TRANSACTION_onInit = 1;
        static final int TRANSACTION_onPresetSession = 3;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IPreviewExtenderImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IPreviewExtenderImpl)) {
                return (android.hardware.camera2.extension.IPreviewExtenderImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IPreviewExtenderImpl.Stub.Proxy(iBinder);
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
                    return "init";
                case 7:
                    return "isExtensionAvailable";
                case 8:
                    return "getCaptureStage";
                case 9:
                    return "getSessionType";
                case 10:
                    return "getProcessorType";
                case 11:
                    return "getPreviewImageProcessor";
                case 12:
                    return "getRequestUpdateProcessor";
                case 13:
                    return "getSupportedResolutions";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
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
                    java.lang.String readString2 = parcel.readString();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative2 = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    init(readString2, cameraMetadataNative2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString3 = parcel.readString();
                    android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative3 = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isExtensionAvailable = isExtensionAvailable(readString3, cameraMetadataNative3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExtensionAvailable);
                    return true;
                case 8:
                    android.hardware.camera2.extension.CaptureStageImpl captureStage = getCaptureStage();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureStage, 1);
                    return true;
                case 9:
                    int sessionType = getSessionType();
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionType);
                    return true;
                case 10:
                    int processorType = getProcessorType();
                    parcel2.writeNoException();
                    parcel2.writeInt(processorType);
                    return true;
                case 11:
                    android.hardware.camera2.extension.IPreviewImageProcessorImpl previewImageProcessor = getPreviewImageProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(previewImageProcessor);
                    return true;
                case 12:
                    android.hardware.camera2.extension.IRequestUpdateProcessorImpl requestUpdateProcessor = getRequestUpdateProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(requestUpdateProcessor);
                    return true;
                case 13:
                    java.util.List<android.hardware.camera2.extension.SizeList> supportedResolutions = getSupportedResolutions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedResolutions, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IPreviewExtenderImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public void onInit(android.os.IBinder iBinder, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
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

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public void onDeInit(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl onPresetSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl onEnableSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl onDisableSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public void init(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public boolean isExtensionAvailable(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
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

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public android.hardware.camera2.extension.CaptureStageImpl getCaptureStage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CaptureStageImpl) obtain2.readTypedObject(android.hardware.camera2.extension.CaptureStageImpl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public int getSessionType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public int getProcessorType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public android.hardware.camera2.extension.IPreviewImageProcessorImpl getPreviewImageProcessor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.IPreviewImageProcessorImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public android.hardware.camera2.extension.IRequestUpdateProcessorImpl getRequestUpdateProcessor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.IRequestUpdateProcessorImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IPreviewExtenderImpl
            public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedResolutions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IPreviewExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
