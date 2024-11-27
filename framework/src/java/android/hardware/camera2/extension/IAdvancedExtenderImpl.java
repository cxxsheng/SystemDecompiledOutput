package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IAdvancedExtenderImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IAdvancedExtenderImpl";

    android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys(java.lang.String str) throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys(java.lang.String str) throws android.os.RemoteException;

    android.hardware.camera2.impl.CameraMetadataNative getAvailableCharacteristicsKeyValues(java.lang.String str) throws android.os.RemoteException;

    android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(java.lang.String str, android.hardware.camera2.extension.Size size, int i) throws android.os.RemoteException;

    android.hardware.camera2.extension.ISessionProcessorImpl getSessionProcessor() throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.SizeList> getSupportedCaptureOutputResolutions(java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) throws android.os.RemoteException;

    java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPreviewOutputResolutions(java.lang.String str) throws android.os.RemoteException;

    void init(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) throws android.os.RemoteException;

    boolean isCaptureProcessProgressAvailable() throws android.os.RemoteException;

    boolean isExtensionAvailable(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) throws android.os.RemoteException;

    boolean isPostviewAvailable() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IAdvancedExtenderImpl {
        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isExtensionAvailable(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public void init(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(java.lang.String str, android.hardware.camera2.extension.Size size, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPreviewOutputResolutions(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedCaptureOutputResolutions(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.extension.ISessionProcessorImpl getSessionProcessor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isCaptureProcessProgressAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public boolean isPostviewAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
        public android.hardware.camera2.impl.CameraMetadataNative getAvailableCharacteristicsKeyValues(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IAdvancedExtenderImpl {
        static final int TRANSACTION_getAvailableCaptureRequestKeys = 8;
        static final int TRANSACTION_getAvailableCaptureResultKeys = 9;
        static final int TRANSACTION_getAvailableCharacteristicsKeyValues = 12;
        static final int TRANSACTION_getEstimatedCaptureLatencyRange = 3;
        static final int TRANSACTION_getSessionProcessor = 7;
        static final int TRANSACTION_getSupportedCaptureOutputResolutions = 5;
        static final int TRANSACTION_getSupportedPostviewResolutions = 6;
        static final int TRANSACTION_getSupportedPreviewOutputResolutions = 4;
        static final int TRANSACTION_init = 2;
        static final int TRANSACTION_isCaptureProcessProgressAvailable = 10;
        static final int TRANSACTION_isExtensionAvailable = 1;
        static final int TRANSACTION_isPostviewAvailable = 11;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IAdvancedExtenderImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IAdvancedExtenderImpl)) {
                return (android.hardware.camera2.extension.IAdvancedExtenderImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IAdvancedExtenderImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isExtensionAvailable";
                case 2:
                    return "init";
                case 3:
                    return "getEstimatedCaptureLatencyRange";
                case 4:
                    return "getSupportedPreviewOutputResolutions";
                case 5:
                    return "getSupportedCaptureOutputResolutions";
                case 6:
                    return "getSupportedPostviewResolutions";
                case 7:
                    return "getSessionProcessor";
                case 8:
                    return "getAvailableCaptureRequestKeys";
                case 9:
                    return "getAvailableCaptureResultKeys";
                case 10:
                    return "isCaptureProcessProgressAvailable";
                case 11:
                    return "isPostviewAvailable";
                case 12:
                    return "getAvailableCharacteristicsKeyValues";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            final java.util.HashMap hashMap;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    hashMap = readInt >= 0 ? new java.util.HashMap() : null;
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), (android.hardware.camera2.impl.CameraMetadataNative) android.os.Parcel.this.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR));
                        }
                    });
                    parcel.enforceNoDataAvail();
                    boolean isExtensionAvailable = isExtensionAvailable(readString, hashMap);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExtensionAvailable);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    hashMap = readInt2 >= 0 ? new java.util.HashMap() : null;
                    java.util.stream.IntStream.range(0, readInt2).forEach(new java.util.function.IntConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), (android.hardware.camera2.impl.CameraMetadataNative) android.os.Parcel.this.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR));
                        }
                    });
                    parcel.enforceNoDataAvail();
                    init(readString2, hashMap);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.hardware.camera2.extension.Size size = (android.hardware.camera2.extension.Size) parcel.readTypedObject(android.hardware.camera2.extension.Size.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.extension.LatencyRange estimatedCaptureLatencyRange = getEstimatedCaptureLatencyRange(readString3, size, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(estimatedCaptureLatencyRange, 1);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.camera2.extension.SizeList> supportedPreviewOutputResolutions = getSupportedPreviewOutputResolutions(readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPreviewOutputResolutions, 1);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.camera2.extension.SizeList> supportedCaptureOutputResolutions = getSupportedCaptureOutputResolutions(readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedCaptureOutputResolutions, 1);
                    return true;
                case 6:
                    android.hardware.camera2.extension.Size size2 = (android.hardware.camera2.extension.Size) parcel.readTypedObject(android.hardware.camera2.extension.Size.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.hardware.camera2.extension.SizeList> supportedPostviewResolutions = getSupportedPostviewResolutions(size2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(supportedPostviewResolutions, 1);
                    return true;
                case 7:
                    android.hardware.camera2.extension.ISessionProcessorImpl sessionProcessor = getSessionProcessor();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sessionProcessor);
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative availableCaptureRequestKeys = getAvailableCaptureRequestKeys(readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureRequestKeys, 1);
                    return true;
                case 9:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative availableCaptureResultKeys = getAvailableCaptureResultKeys(readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCaptureResultKeys, 1);
                    return true;
                case 10:
                    boolean isCaptureProcessProgressAvailable = isCaptureProcessProgressAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaptureProcessProgressAvailable);
                    return true;
                case 11:
                    boolean isPostviewAvailable = isPostviewAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPostviewAvailable);
                    return true;
                case 12:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.impl.CameraMetadataNative availableCharacteristicsKeyValues = getAvailableCharacteristicsKeyValues(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableCharacteristicsKeyValues, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.hardware.camera2.extension.IAdvancedExtenderImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isExtensionAvailable(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) throws android.os.RemoteException {
                final android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new java.util.function.BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.hardware.camera2.extension.IAdvancedExtenderImpl.Stub.Proxy.lambda$isExtensionAvailable$0(android.os.Parcel.this, (java.lang.String) obj, (android.hardware.camera2.impl.CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$isExtensionAvailable$0(android.os.Parcel parcel, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public void init(java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) throws android.os.RemoteException {
                final android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new java.util.function.BiConsumer() { // from class: android.hardware.camera2.extension.IAdvancedExtenderImpl$Stub$Proxy$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.hardware.camera2.extension.IAdvancedExtenderImpl.Stub.Proxy.lambda$init$1(android.os.Parcel.this, (java.lang.String) obj, (android.hardware.camera2.impl.CameraMetadataNative) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$init$1(android.os.Parcel parcel, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public android.hardware.camera2.extension.LatencyRange getEstimatedCaptureLatencyRange(java.lang.String str, android.hardware.camera2.extension.Size size, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(size, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.LatencyRange) obtain2.readTypedObject(android.hardware.camera2.extension.LatencyRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPreviewOutputResolutions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedCaptureOutputResolutions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public java.util.List<android.hardware.camera2.extension.SizeList> getSupportedPostviewResolutions(android.hardware.camera2.extension.Size size) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeTypedObject(size, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.camera2.extension.SizeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public android.hardware.camera2.extension.ISessionProcessorImpl getSessionProcessor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.camera2.extension.ISessionProcessorImpl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureRequestKeys(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public android.hardware.camera2.impl.CameraMetadataNative getAvailableCaptureResultKeys(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.impl.CameraMetadataNative) obtain2.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isCaptureProcessProgressAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public boolean isPostviewAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IAdvancedExtenderImpl
            public android.hardware.camera2.impl.CameraMetadataNative getAvailableCharacteristicsKeyValues(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IAdvancedExtenderImpl.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
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
            return 11;
        }
    }
}
