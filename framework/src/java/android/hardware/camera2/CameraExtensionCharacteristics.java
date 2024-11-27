package android.hardware.camera2;

/* loaded from: classes.dex */
public final class CameraExtensionCharacteristics {
    public static final int EXTENSION_AUTOMATIC = 0;

    @java.lang.Deprecated
    public static final int EXTENSION_BEAUTY = 1;
    public static final int EXTENSION_BOKEH = 2;
    public static final int EXTENSION_EYES_FREE_VIDEOGRAPHY = 5;
    public static final int EXTENSION_FACE_RETOUCH = 1;
    public static final int EXTENSION_HDR = 3;
    public static final int EXTENSION_NIGHT = 4;
    public static final int NON_PROCESSING_INPUT_FORMAT = 34;
    public static final int PROCESSING_INPUT_FORMAT = 35;
    private static final java.lang.String TAG = "CameraExtensionCharacteristics";
    private final java.lang.String mCameraId;
    private final java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> mCharacteristicsMap;
    private final java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> mCharacteristicsMapNative;
    private final android.content.Context mContext;
    private static final int[] EXTENSION_LIST = {0, 1, 2, 3, 4};

    @android.hardware.camera2.impl.ExtensionKey
    @android.hardware.camera2.impl.PublicKey
    public static final android.hardware.camera2.CameraCharacteristics.Key<android.util.Range<java.lang.Float>> EFV_PADDING_ZOOM_FACTOR_RANGE = android.hardware.camera2.CameraCharacteristics.EFV_PADDING_ZOOM_FACTOR_RANGE;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Extension {
    }

    public CameraExtensionCharacteristics(android.content.Context context, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> map) {
        this.mContext = context;
        this.mCameraId = str;
        this.mCharacteristicsMap = map;
        this.mCharacteristicsMapNative = android.hardware.camera2.impl.CameraExtensionUtils.getCharacteristicsMapNative(map);
    }

    private static java.util.ArrayList<android.util.Size> getSupportedSizes(java.util.List<android.hardware.camera2.extension.SizeList> list, java.lang.Integer num) {
        java.util.ArrayList<android.util.Size> arrayList = new java.util.ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (android.hardware.camera2.extension.SizeList sizeList : list) {
                if (sizeList.format == num.intValue() && !sizeList.sizes.isEmpty()) {
                    for (android.hardware.camera2.extension.Size size : sizeList.sizes) {
                        arrayList.add(new android.util.Size(size.width, size.height));
                    }
                    return arrayList;
                }
            }
        }
        return arrayList;
    }

    private static java.util.List<android.util.Size> generateSupportedSizes(java.util.List<android.hardware.camera2.extension.SizeList> list, java.lang.Integer num, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap) {
        java.util.ArrayList<android.util.Size> supportedSizes = getSupportedSizes(list, num);
        android.util.Size[] outputSizes = streamConfigurationMap.getOutputSizes(num.intValue());
        if (supportedSizes.isEmpty() && outputSizes != null) {
            supportedSizes.addAll(java.util.Arrays.asList(outputSizes));
        }
        return supportedSizes;
    }

    private static java.util.List<android.util.Size> generateJpegSupportedSizes(java.util.List<android.hardware.camera2.extension.SizeList> list, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap) {
        java.util.HashSet hashSet;
        java.util.ArrayList<android.util.Size> supportedSizes = getSupportedSizes(list, 35);
        if (supportedSizes.isEmpty()) {
            hashSet = new java.util.HashSet(java.util.Arrays.asList(streamConfigurationMap.getOutputSizes(35)));
        } else {
            hashSet = new java.util.HashSet(supportedSizes);
        }
        hashSet.retainAll(new java.util.HashSet(java.util.Arrays.asList(streamConfigurationMap.getOutputSizes(256))));
        return new java.util.ArrayList(hashSet);
    }

    private static final class CameraExtensionManagerGlobal {
        private static final int FALLBACK_PACKAGE_NAME = 17039958;
        private static final int FALLBACK_SERVICE_NAME = 17039959;
        private static final android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal GLOBAL_CAMERA_MANAGER = new android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal();
        private static final java.lang.String PROXY_PACKAGE_NAME = "com.android.cameraextensions";
        private static final java.lang.String PROXY_SERVICE_NAME = "com.android.cameraextensions.CameraExtensionsProxyService";
        private static final java.lang.String TAG = "CameraExtensionManagerGlobal";
        private final java.lang.Object mLock = new java.lang.Object();
        private final int PROXY_SERVICE_DELAY_MS = 2000;
        private android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.ExtensionConnectionManager mConnectionManager = new android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.ExtensionConnectionManager();

        private CameraExtensionManagerGlobal() {
        }

        public static android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal get() {
            return GLOBAL_CAMERA_MANAGER;
        }

        private void releaseProxyConnectionLocked(android.content.Context context, int i) {
            if (this.mConnectionManager.getConnection(i) != null) {
                context.unbindService(this.mConnectionManager.getConnection(i));
                this.mConnectionManager.setConnection(i, null);
                this.mConnectionManager.setProxy(i, null);
                this.mConnectionManager.resetConnectionCount(i);
            }
        }

        private void connectToProxyLocked(android.content.Context context, final int i, boolean z) {
            if (this.mConnectionManager.getConnection(i) == null) {
                android.content.Intent intent = new android.content.Intent();
                intent.setClassName(PROXY_PACKAGE_NAME, PROXY_SERVICE_NAME);
                java.lang.String str = android.os.SystemProperties.get("ro.vendor.camera.extensions.package");
                java.lang.String str2 = android.os.SystemProperties.get("ro.vendor.camera.extensions.service");
                if (!str.isEmpty() && !str2.isEmpty()) {
                    android.util.Log.v(TAG, "Choosing the vendor camera extensions proxy package: " + str);
                    android.util.Log.v(TAG, "Choosing the vendor camera extensions proxy service: " + str2);
                    intent.setClassName(str, str2);
                }
                if (com.android.internal.camera.flags.Flags.concertMode() && z) {
                    java.lang.String string = context.getResources().getString(17039958);
                    java.lang.String string2 = context.getResources().getString(17039959);
                    if (!string.isEmpty() && !string2.isEmpty()) {
                        android.util.Log.v(TAG, "Choosing the fallback software implementation package: " + string);
                        android.util.Log.v(TAG, "Choosing the fallback software implementation service: " + string2);
                        intent.setClassName(string, string2);
                    }
                }
                final android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.InitializerFuture initializerFuture = new android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.InitializerFuture();
                android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() { // from class: android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.1
                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(android.content.ComponentName componentName) {
                        android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.this.mConnectionManager.setConnection(i, null);
                        android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.this.mConnectionManager.setProxy(i, null);
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                        android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.this.mConnectionManager.setProxy(i, android.hardware.camera2.extension.ICameraExtensionsProxyService.Stub.asInterface(iBinder));
                        if (android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.this.mConnectionManager.getProxy(i) == null) {
                            throw new java.lang.IllegalStateException("Camera Proxy service is null");
                        }
                        try {
                            android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.this.mConnectionManager.setAdvancedExtensionsSupported(i, android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.this.mConnectionManager.getProxy(i).advancedExtensionsSupported());
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.TAG, "Remote IPC failed!");
                        }
                        initializerFuture.setStatus(true);
                    }
                };
                context.bindService(intent, 1073741897, android.os.AsyncTask.THREAD_POOL_EXECUTOR, serviceConnection);
                this.mConnectionManager.setConnection(i, serviceConnection);
                try {
                    initializerFuture.get(2000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                } catch (java.util.concurrent.TimeoutException e) {
                    android.util.Log.e(TAG, "Timed out while initializing proxy service!");
                }
            }
        }

        private static class InitializerFuture implements java.util.concurrent.Future<java.lang.Boolean> {
            android.os.ConditionVariable mCondVar;
            private volatile java.lang.Boolean mStatus;

            private InitializerFuture() {
                this.mCondVar = new android.os.ConditionVariable(false);
            }

            public void setStatus(boolean z) {
                this.mStatus = java.lang.Boolean.valueOf(z);
                this.mCondVar.open();
            }

            @Override // java.util.concurrent.Future
            public boolean cancel(boolean z) {
                return false;
            }

            @Override // java.util.concurrent.Future
            public boolean isCancelled() {
                return false;
            }

            @Override // java.util.concurrent.Future
            public boolean isDone() {
                return this.mStatus != null;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Future
            public java.lang.Boolean get() {
                this.mCondVar.block();
                return this.mStatus;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Future
            public java.lang.Boolean get(long j, java.util.concurrent.TimeUnit timeUnit) throws java.util.concurrent.TimeoutException {
                if (!this.mCondVar.block(timeUnit.convert(j, java.util.concurrent.TimeUnit.MILLISECONDS))) {
                    throw new java.util.concurrent.TimeoutException("Failed to receive status after " + j + " " + timeUnit);
                }
                if (this.mStatus == null) {
                    throw new java.lang.AssertionError();
                }
                return this.mStatus;
            }
        }

        public boolean registerClientHelper(android.content.Context context, android.os.IBinder iBinder, int i, boolean z) {
            synchronized (this.mLock) {
                connectToProxyLocked(context, i, z);
                boolean z2 = false;
                if (this.mConnectionManager.getProxy(i) == null) {
                    return false;
                }
                this.mConnectionManager.incrementConnectionCount(i);
                try {
                    z2 = this.mConnectionManager.getProxy(i).registerClient(iBinder);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Failed to initialize extension! Extension service does  not respond!");
                }
                if (!z2) {
                    this.mConnectionManager.decrementConnectionCount(i);
                }
                if (this.mConnectionManager.getConnectionCount(i) <= 0) {
                    releaseProxyConnectionLocked(context, i);
                }
                return z2;
            }
        }

        public boolean registerClient(android.content.Context context, android.os.IBinder iBinder, int i, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) {
            boolean z;
            boolean registerClientHelper = registerClientHelper(context, iBinder, i, false);
            if (com.android.internal.camera.flags.Flags.concertMode()) {
                int i2 = android.provider.Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.CAMERA_EXTENSIONS_FALLBACK, 1);
                if (registerClientHelper && this.mConnectionManager.getProxy(i) != null && i2 == 1) {
                    z = android.hardware.camera2.CameraExtensionCharacteristics.isExtensionSupported(str, i, map);
                } else {
                    z = true;
                }
                if (!z) {
                    unregisterClient(context, iBinder, i);
                    return registerClientHelper(context, iBinder, i, true);
                }
                return registerClientHelper;
            }
            return registerClientHelper;
        }

        public void unregisterClient(android.content.Context context, android.os.IBinder iBinder, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mConnectionManager.getProxy(i) != null) {
                        try {
                            this.mConnectionManager.getProxy(i).unregisterClient(iBinder);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(TAG, "Failed to de-initialize extension! Extension service does not respond!");
                            this.mConnectionManager.decrementConnectionCount(i);
                            if (this.mConnectionManager.getConnectionCount(i) <= 0) {
                            }
                        }
                    }
                } finally {
                    this.mConnectionManager.decrementConnectionCount(i);
                    if (this.mConnectionManager.getConnectionCount(i) <= 0) {
                        releaseProxyConnectionLocked(context, i);
                    }
                }
            }
        }

        public void initializeSession(android.hardware.camera2.extension.IInitializeSessionCallback iInitializeSessionCallback, int i) throws android.os.RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) != null && !this.mConnectionManager.isSessionInitialized()) {
                    this.mConnectionManager.getProxy(i).initializeSession(iInitializeSessionCallback);
                    this.mConnectionManager.setSessionInitialized(true);
                } else {
                    iInitializeSessionCallback.onFailure();
                }
            }
        }

        public void releaseSession(int i) {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) != null) {
                    try {
                        this.mConnectionManager.getProxy(i).releaseSession();
                        this.mConnectionManager.setSessionInitialized(false);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "Failed to release session! Extension service does not respond!");
                    }
                }
            }
        }

        public boolean areAdvancedExtensionsSupported(int i) {
            return this.mConnectionManager.areAdvancedExtensionsSupported(i);
        }

        public android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension(int i) throws android.os.RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) == null) {
                    return null;
                }
                return this.mConnectionManager.getProxy(i).initializePreviewExtension(i);
            }
        }

        public android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension(int i) throws android.os.RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) == null) {
                    return null;
                }
                return this.mConnectionManager.getProxy(i).initializeImageExtension(i);
            }
        }

        public android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws android.os.RemoteException {
            synchronized (this.mLock) {
                if (this.mConnectionManager.getProxy(i) == null) {
                    return null;
                }
                return this.mConnectionManager.getProxy(i).initializeAdvancedExtension(i);
            }
        }

        private class ExtensionConnectionManager {
            private java.util.Map<java.lang.Integer, android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.ExtensionConnectionManager.ExtensionConnection> mConnections = new java.util.HashMap();
            private boolean mSessionInitialized = false;

            public ExtensionConnectionManager() {
                android.util.IntArray intArray = new android.util.IntArray(android.hardware.camera2.CameraExtensionCharacteristics.EXTENSION_LIST.length);
                intArray.addAll(android.hardware.camera2.CameraExtensionCharacteristics.EXTENSION_LIST);
                if (com.android.internal.camera.flags.Flags.concertMode()) {
                    intArray.add(5);
                }
                for (int i : intArray.toArray()) {
                    this.mConnections.put(java.lang.Integer.valueOf(i), new android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.ExtensionConnectionManager.ExtensionConnection());
                }
            }

            public android.hardware.camera2.extension.ICameraExtensionsProxyService getProxy(int i) {
                return this.mConnections.get(java.lang.Integer.valueOf(i)).mProxy;
            }

            public android.content.ServiceConnection getConnection(int i) {
                return this.mConnections.get(java.lang.Integer.valueOf(i)).mConnection;
            }

            public int getConnectionCount(int i) {
                return this.mConnections.get(java.lang.Integer.valueOf(i)).mConnectionCount;
            }

            public boolean areAdvancedExtensionsSupported(int i) {
                return this.mConnections.get(java.lang.Integer.valueOf(i)).mSupportsAdvancedExtensions;
            }

            public boolean isSessionInitialized() {
                return this.mSessionInitialized;
            }

            public void setProxy(int i, android.hardware.camera2.extension.ICameraExtensionsProxyService iCameraExtensionsProxyService) {
                this.mConnections.get(java.lang.Integer.valueOf(i)).mProxy = iCameraExtensionsProxyService;
            }

            public void setConnection(int i, android.content.ServiceConnection serviceConnection) {
                this.mConnections.get(java.lang.Integer.valueOf(i)).mConnection = serviceConnection;
            }

            public void incrementConnectionCount(int i) {
                this.mConnections.get(java.lang.Integer.valueOf(i)).mConnectionCount++;
            }

            public void decrementConnectionCount(int i) {
                android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.ExtensionConnectionManager.ExtensionConnection extensionConnection = this.mConnections.get(java.lang.Integer.valueOf(i));
                extensionConnection.mConnectionCount--;
            }

            public void resetConnectionCount(int i) {
                this.mConnections.get(java.lang.Integer.valueOf(i)).mConnectionCount = 0;
            }

            public void setAdvancedExtensionsSupported(int i, boolean z) {
                this.mConnections.get(java.lang.Integer.valueOf(i)).mSupportsAdvancedExtensions = z;
            }

            public void setSessionInitialized(boolean z) {
                this.mSessionInitialized = z;
            }

            private class ExtensionConnection {
                public android.content.ServiceConnection mConnection;
                public int mConnectionCount;
                public android.hardware.camera2.extension.ICameraExtensionsProxyService mProxy;
                public boolean mSupportsAdvancedExtensions;

                private ExtensionConnection() {
                    this.mProxy = null;
                    this.mConnection = null;
                    this.mConnectionCount = 0;
                    this.mSupportsAdvancedExtensions = false;
                }
            }
        }
    }

    public static boolean registerClient(android.content.Context context, android.os.IBinder iBinder, int i, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) {
        return android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().registerClient(context, iBinder, i, str, map);
    }

    public static void unregisterClient(android.content.Context context, android.os.IBinder iBinder, int i) {
        android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().unregisterClient(context, iBinder, i);
    }

    public static void initializeSession(android.hardware.camera2.extension.IInitializeSessionCallback iInitializeSessionCallback, int i) throws android.os.RemoteException {
        android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().initializeSession(iInitializeSessionCallback, i);
    }

    public static void releaseSession(int i) {
        android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().releaseSession(i);
    }

    public static boolean areAdvancedExtensionsSupported(int i) {
        return android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().areAdvancedExtensionsSupported(i);
    }

    public static boolean isExtensionSupported(java.lang.String str, int i, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map) {
        if (areAdvancedExtensionsSupported(i)) {
            try {
                return initializeAdvancedExtension(i).isExtensionAvailable(str, map);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to query extension availability! Extension service does not respond!");
                return false;
            }
        }
        try {
            android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
            try {
                if (initializeExtension.first.isExtensionAvailable(str, map.get(str))) {
                    return initializeExtension.second.isExtensionAvailable(str, map.get(str));
                }
                return false;
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "Failed to query extension availability! Extension service does not respond!");
                return false;
            }
        } catch (java.lang.IllegalArgumentException e3) {
            return false;
        }
    }

    public static android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension(int i) {
        try {
            android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().initializeAdvancedExtension(i);
            if (initializeAdvancedExtension == null) {
                throw new java.lang.IllegalArgumentException("Unknown extension: " + i);
            }
            return initializeAdvancedExtension;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Failed to initialize extension: " + i);
        }
    }

    public static android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension(int i) {
        try {
            android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension = android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().initializePreviewExtension(i);
            android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension = android.hardware.camera2.CameraExtensionCharacteristics.CameraExtensionManagerGlobal.get().initializeImageExtension(i);
            if (initializeImageExtension == null || initializePreviewExtension == null) {
                throw new java.lang.IllegalArgumentException("Unknown extension: " + i);
            }
            return new android.util.Pair<>(initializePreviewExtension, initializeImageExtension);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Failed to initialize extension: " + i);
        }
    }

    private static <T> boolean isOutputSupportedFor(java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls, "klass must not be null");
        if (cls == android.graphics.SurfaceTexture.class || cls == android.view.SurfaceView.class) {
            return true;
        }
        return false;
    }

    public java.util.List<java.lang.Integer> getSupportedExtensions() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getSupportedExtensions:" + this.mCameraId);
        android.util.IntArray intArray = new android.util.IntArray(EXTENSION_LIST.length);
        intArray.addAll(EXTENSION_LIST);
        if (com.android.internal.camera.flags.Flags.concertMode()) {
            intArray.add(5);
        }
        for (int i : intArray.toArray()) {
            try {
                if (registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative) && isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    arrayList.add(java.lang.Integer.valueOf(i));
                }
                unregisterClient(this.mContext, binder, i);
            } catch (java.lang.Throwable th) {
                unregisterClient(this.mContext, binder, i);
                throw th;
            }
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public <T> T get(int i, android.hardware.camera2.CameraCharacteristics.Key<T> key) {
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#get:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new java.lang.IllegalArgumentException("Unsupported extensions");
        }
        try {
            try {
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to query the extension for the specified key! Extension service does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extension");
            }
            if (areAdvancedExtensionsSupported(i) && getKeys(i).contains(key)) {
                android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                android.hardware.camera2.impl.CameraMetadataNative availableCharacteristicsKeyValues = initializeAdvancedExtension.getAvailableCharacteristicsKeyValues(this.mCameraId);
                if (availableCharacteristicsKeyValues == null) {
                    return null;
                }
                return (T) new android.hardware.camera2.CameraCharacteristics(availableCharacteristicsKeyValues).get(key);
            }
            return null;
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public java.util.Set<android.hardware.camera2.CameraCharacteristics.Key> getKeys(int i) {
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getKeys:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new java.lang.IllegalArgumentException("Unsupported extensions");
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            try {
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to query the extension for all available keys! Extension service does not respond!");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extension");
            }
            if (areAdvancedExtensionsSupported(i)) {
                android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                android.hardware.camera2.impl.CameraMetadataNative availableCharacteristicsKeyValues = initializeAdvancedExtension.getAvailableCharacteristicsKeyValues(this.mCameraId);
                if (availableCharacteristicsKeyValues == null) {
                    return java.util.Collections.emptySet();
                }
                int[] iArr = (int[]) availableCharacteristicsKeyValues.get(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_CHARACTERISTICS_KEYS);
                if (iArr == null) {
                    throw new java.lang.AssertionError("android.request.availableCharacteristicsKeys must be non-null in the characteristics");
                }
                hashSet.addAll(new android.hardware.camera2.CameraCharacteristics(availableCharacteristicsKeyValues).getAvailableKeyList(android.hardware.camera2.CameraCharacteristics.class, android.hardware.camera2.CameraCharacteristics.Key.class, iArr, false));
            }
            unregisterClient(this.mContext, binder, i);
            return java.util.Collections.unmodifiableSet(hashSet);
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public boolean isPostviewAvailable(int i) {
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#isPostviewAvailable:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new java.lang.IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    return initializeAdvancedExtension.isPostviewAvailable();
                }
                android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                return initializeExtension.second.isPostviewAvailable();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to query the extension for postview availability! Extension service does not respond!");
                unregisterClient(this.mContext, binder, i);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public java.util.List<android.util.Size> getPostviewSupportedSizes(int i, android.util.Size size, int i2) {
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getPostviewSupportedSizes:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extensions");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extension");
            }
            android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
            size2.width = size.getWidth();
            size2.height = size.getHeight();
            android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = (android.hardware.camera2.params.StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (areAdvancedExtensionsSupported(i)) {
                switch (i2) {
                    case 35:
                    case 256:
                    case 4101:
                        android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                        initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                        return generateSupportedSizes(initializeAdvancedExtension.getSupportedPostviewResolutions(size2), java.lang.Integer.valueOf(i2), streamConfigurationMap);
                    default:
                        throw new java.lang.IllegalArgumentException("Unsupported format: " + i2);
                }
            }
            android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
            initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
            if (initializeExtension.second.getCaptureProcessor() == null || !isPostviewAvailable(i)) {
                throw new java.lang.IllegalArgumentException("Extension does not support postview feature");
            }
            if (i2 == 35) {
                return generateSupportedSizes(initializeExtension.second.getSupportedPostviewResolutions(size2), java.lang.Integer.valueOf(i2), streamConfigurationMap);
            }
            if (i2 == 256) {
                return generateJpegSupportedSizes(initializeExtension.second.getSupportedPostviewResolutions(size2), streamConfigurationMap);
            }
            if (i2 == 4101) {
                return new java.util.ArrayList();
            }
            throw new java.lang.IllegalArgumentException("Unsupported format: " + i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to query the extension postview supported sizes! Extension service does not respond!");
            return java.util.Collections.emptyList();
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public <T> java.util.List<android.util.Size> getExtensionSupportedSizes(int i, java.lang.Class<T> cls) {
        if (!isOutputSupportedFor(cls)) {
            return new java.util.ArrayList();
        }
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getExtensionSupportedSizes:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extensions");
            }
            if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extension");
            }
            android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = (android.hardware.camera2.params.StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (areAdvancedExtensionsSupported(i)) {
                android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                return generateSupportedSizes(initializeAdvancedExtension.getSupportedPreviewOutputResolutions(this.mCameraId), 34, streamConfigurationMap);
            }
            android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
            initializeExtension.first.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
            return generateSupportedSizes(initializeExtension.first.getSupportedResolutions(), 34, streamConfigurationMap);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to query the extension supported sizes! Extension service does not respond!");
            return new java.util.ArrayList();
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public java.util.List<android.util.Size> getExtensionSupportedSizes(int i, int i2) {
        try {
            android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getExtensionSupportedSizes:" + this.mCameraId);
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new java.lang.IllegalArgumentException("Unsupported extension");
                }
                android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = (android.hardware.camera2.params.StreamConfigurationMap) this.mCharacteristicsMap.get(this.mCameraId).get(android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (areAdvancedExtensionsSupported(i)) {
                    switch (i2) {
                        case 35:
                        case 256:
                        case 4101:
                            android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                            initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                            return generateSupportedSizes(initializeAdvancedExtension.getSupportedCaptureOutputResolutions(this.mCameraId), java.lang.Integer.valueOf(i2), streamConfigurationMap);
                        default:
                            throw new java.lang.IllegalArgumentException("Unsupported format: " + i2);
                    }
                }
                if (i2 == 35) {
                    android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    return initializeExtension.second.getCaptureProcessor() == null ? new java.util.ArrayList() : generateSupportedSizes(initializeExtension.second.getSupportedResolutions(), java.lang.Integer.valueOf(i2), streamConfigurationMap);
                }
                if (i2 == 256) {
                    android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension2 = initializeExtension(i);
                    initializeExtension2.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    return initializeExtension2.second.getCaptureProcessor() != null ? generateJpegSupportedSizes(initializeExtension2.second.getSupportedResolutions(), streamConfigurationMap) : generateSupportedSizes(null, java.lang.Integer.valueOf(i2), streamConfigurationMap);
                }
                if (i2 == 4101) {
                    return new java.util.ArrayList();
                }
                throw new java.lang.IllegalArgumentException("Unsupported format: " + i2);
            } finally {
                unregisterClient(this.mContext, binder, i);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to query the extension supported sizes! Extension service does not respond!");
            return new java.util.ArrayList();
        }
    }

    public android.util.Range<java.lang.Long> getEstimatedCaptureLatencyRangeMillis(int i, android.util.Size size, int i2) {
        switch (i2) {
            case 35:
            case 256:
            case 4101:
                android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getEstimatedCaptureLatencyRangeMillis:" + this.mCameraId);
                if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                    throw new java.lang.IllegalArgumentException("Unsupported extensions");
                }
                try {
                    try {
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "Failed to query the extension capture latency! Extension service does not respond!");
                    }
                    if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                        throw new java.lang.IllegalArgumentException("Unsupported extension");
                    }
                    android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
                    size2.width = size.getWidth();
                    size2.height = size.getHeight();
                    if (areAdvancedExtensionsSupported(i)) {
                        android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                        initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                        android.hardware.camera2.extension.LatencyRange estimatedCaptureLatencyRange = initializeAdvancedExtension.getEstimatedCaptureLatencyRange(this.mCameraId, size2, i2);
                        if (estimatedCaptureLatencyRange != null) {
                            return new android.util.Range<>(java.lang.Long.valueOf(estimatedCaptureLatencyRange.min), java.lang.Long.valueOf(estimatedCaptureLatencyRange.max));
                        }
                    } else {
                        android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                        initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                        if (i2 == 35 && initializeExtension.second.getCaptureProcessor() == null) {
                            return null;
                        }
                        if (i2 == 256 && initializeExtension.second.getCaptureProcessor() != null) {
                            return null;
                        }
                        if (i2 == 4101) {
                            return null;
                        }
                        android.hardware.camera2.extension.LatencyRange estimatedCaptureLatencyRange2 = initializeExtension.second.getEstimatedCaptureLatencyRange(size2);
                        if (estimatedCaptureLatencyRange2 != null) {
                            return new android.util.Range<>(java.lang.Long.valueOf(estimatedCaptureLatencyRange2.min), java.lang.Long.valueOf(estimatedCaptureLatencyRange2.max));
                        }
                    }
                    return null;
                } finally {
                    unregisterClient(this.mContext, binder, i);
                }
            default:
                throw new java.lang.IllegalArgumentException("Unsupported format: " + i2);
        }
    }

    public boolean isCaptureProcessProgressAvailable(int i) {
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#isCaptureProcessProgressAvailable:" + this.mCameraId);
        try {
            if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
                throw new java.lang.IllegalArgumentException("Unsupported extensions");
            }
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new java.lang.IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    return initializeAdvancedExtension.isCaptureProcessProgressAvailable();
                }
                android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                return initializeExtension.second.isCaptureProcessProgressAvailable();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to query the extension progress callbacks! Extension service does not respond!");
                unregisterClient(this.mContext, binder, i);
                return false;
            }
        } finally {
            unregisterClient(this.mContext, binder, i);
        }
    }

    public java.util.Set<android.hardware.camera2.CaptureRequest.Key> getAvailableCaptureRequestKeys(int i) {
        android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative;
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getAvailableCaptureRequestKeys:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new java.lang.IllegalArgumentException("Unsupported extensions");
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new java.lang.IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    cameraMetadataNative = initializeAdvancedExtension.getAvailableCaptureRequestKeys(this.mCameraId);
                } else {
                    android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    initializeExtension.second.onInit(binder, this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    android.hardware.camera2.impl.CameraMetadataNative availableCaptureRequestKeys = initializeExtension.second.getAvailableCaptureRequestKeys();
                    initializeExtension.second.onDeInit(binder);
                    cameraMetadataNative = availableCaptureRequestKeys;
                }
                if (cameraMetadataNative != null) {
                    int[] iArr = (int[]) cameraMetadataNative.get(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS);
                    if (iArr == null) {
                        throw new java.lang.AssertionError("android.request.availableRequestKeys must be non-null in the characteristics");
                    }
                    hashSet.addAll(new android.hardware.camera2.CameraCharacteristics(cameraMetadataNative).getAvailableKeyList(android.hardware.camera2.CaptureRequest.class, android.hardware.camera2.CaptureRequest.Key.class, iArr, true));
                }
                if (!hashSet.contains(android.hardware.camera2.CaptureRequest.JPEG_QUALITY)) {
                    hashSet.add(android.hardware.camera2.CaptureRequest.JPEG_QUALITY);
                }
                if (!hashSet.contains(android.hardware.camera2.CaptureRequest.JPEG_ORIENTATION)) {
                    hashSet.add(android.hardware.camera2.CaptureRequest.JPEG_ORIENTATION);
                }
                unregisterClient(this.mContext, binder, i);
                return java.util.Collections.unmodifiableSet(hashSet);
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalStateException("Failed to query the available capture request keys!");
            }
        } catch (java.lang.Throwable th) {
            unregisterClient(this.mContext, binder, i);
            throw th;
        }
    }

    public java.util.Set<android.hardware.camera2.CaptureResult.Key> getAvailableCaptureResultKeys(int i) {
        android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative;
        android.os.Binder binder = new android.os.Binder("CameraExtensionCharacteristics#getAvailableCaptureResultKeys:" + this.mCameraId);
        if (!registerClient(this.mContext, binder, i, this.mCameraId, this.mCharacteristicsMapNative)) {
            throw new java.lang.IllegalArgumentException("Unsupported extensions");
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            try {
                if (!isExtensionSupported(this.mCameraId, i, this.mCharacteristicsMapNative)) {
                    throw new java.lang.IllegalArgumentException("Unsupported extension");
                }
                if (areAdvancedExtensionsSupported(i)) {
                    android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension = initializeAdvancedExtension(i);
                    initializeAdvancedExtension.init(this.mCameraId, this.mCharacteristicsMapNative);
                    cameraMetadataNative = initializeAdvancedExtension.getAvailableCaptureResultKeys(this.mCameraId);
                } else {
                    android.util.Pair<android.hardware.camera2.extension.IPreviewExtenderImpl, android.hardware.camera2.extension.IImageCaptureExtenderImpl> initializeExtension = initializeExtension(i);
                    initializeExtension.second.onInit(binder, this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    initializeExtension.second.init(this.mCameraId, this.mCharacteristicsMapNative.get(this.mCameraId));
                    android.hardware.camera2.impl.CameraMetadataNative availableCaptureResultKeys = initializeExtension.second.getAvailableCaptureResultKeys();
                    initializeExtension.second.onDeInit(binder);
                    cameraMetadataNative = availableCaptureResultKeys;
                }
                if (cameraMetadataNative != null) {
                    int[] iArr = (int[]) cameraMetadataNative.get(android.hardware.camera2.CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS);
                    if (iArr == null) {
                        throw new java.lang.AssertionError("android.request.availableResultKeys must be non-null in the characteristics");
                    }
                    hashSet.addAll(new android.hardware.camera2.CameraCharacteristics(cameraMetadataNative).getAvailableKeyList(android.hardware.camera2.CaptureResult.class, android.hardware.camera2.CaptureResult.Key.class, iArr, true));
                    if (!hashSet.contains(android.hardware.camera2.CaptureResult.JPEG_QUALITY)) {
                        hashSet.add(android.hardware.camera2.CaptureResult.JPEG_QUALITY);
                    }
                    if (!hashSet.contains(android.hardware.camera2.CaptureResult.JPEG_ORIENTATION)) {
                        hashSet.add(android.hardware.camera2.CaptureResult.JPEG_ORIENTATION);
                    }
                    if (!hashSet.contains(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP)) {
                        hashSet.add(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP);
                    }
                }
                unregisterClient(this.mContext, binder, i);
                return java.util.Collections.unmodifiableSet(hashSet);
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalStateException("Failed to query the available capture result keys!");
            }
        } catch (java.lang.Throwable th) {
            unregisterClient(this.mContext, binder, i);
            throw th;
        }
    }
}
