package com.android.server.companion.virtual.camera;

/* loaded from: classes.dex */
public final class VirtualCameraController implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "VirtualCameraController";
    private static final java.lang.String VIRTUAL_CAMERA_SERVICE_NAME = "virtual_camera";
    private final int mCameraPolicy;

    @com.android.internal.annotations.GuardedBy({"mCameras"})
    private final java.util.Map<android.os.IBinder, com.android.server.companion.virtual.camera.VirtualCameraController.CameraDescriptor> mCameras;
    private final java.lang.Object mServiceLock;

    @com.android.internal.annotations.GuardedBy({"mServiceLock"})
    @android.annotation.Nullable
    private android.companion.virtualcamera.IVirtualCameraService mVirtualCameraService;

    public VirtualCameraController(int i) {
        this(null, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    VirtualCameraController(android.companion.virtualcamera.IVirtualCameraService iVirtualCameraService, int i) {
        this.mServiceLock = new java.lang.Object();
        this.mCameras = new android.util.ArrayMap();
        this.mVirtualCameraService = iVirtualCameraService;
        this.mCameraPolicy = i;
    }

    public void registerCamera(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig, android.content.AttributionSource attributionSource) {
        checkConfigByPolicy(virtualCameraConfig);
        connectVirtualCameraServiceIfNeeded();
        try {
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (registerCameraWithService(virtualCameraConfig)) {
            com.android.server.companion.virtual.camera.VirtualCameraController.CameraDescriptor cameraDescriptor = new com.android.server.companion.virtual.camera.VirtualCameraController.CameraDescriptor(virtualCameraConfig);
            android.os.IBinder asBinder = virtualCameraConfig.getCallback().asBinder();
            asBinder.linkToDeath(cameraDescriptor, 0);
            synchronized (this.mCameras) {
                this.mCameras.put(asBinder, cameraDescriptor);
            }
            if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_virtual_camera_created_count", attributionSource.getUid());
                return;
            }
            return;
        }
        throw new java.lang.RuntimeException("Failed to register virtual camera.");
    }

    public void unregisterCamera(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
        synchronized (this.mCameras) {
            try {
                android.os.IBinder asBinder = virtualCameraConfig.getCallback().asBinder();
                if (!this.mCameras.containsKey(asBinder)) {
                    android.util.Slog.w(TAG, "Virtual camera was not registered.");
                } else {
                    connectVirtualCameraServiceIfNeeded();
                    try {
                        synchronized (this.mServiceLock) {
                            this.mVirtualCameraService.unregisterCamera(asBinder);
                        }
                        this.mCameras.remove(asBinder);
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getCameraId(@android.annotation.NonNull android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
        int cameraId;
        connectVirtualCameraServiceIfNeeded();
        try {
            synchronized (this.mServiceLock) {
                cameraId = this.mVirtualCameraService.getCameraId(virtualCameraConfig.getCallback().asBinder());
            }
            return cameraId;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.d(TAG, "Virtual camera service died.");
        synchronized (this.mServiceLock) {
            this.mVirtualCameraService = null;
        }
        synchronized (this.mCameras) {
            this.mCameras.clear();
        }
    }

    public void close() {
        synchronized (this.mCameras) {
            if (!this.mCameras.isEmpty()) {
                connectVirtualCameraServiceIfNeeded();
                synchronized (this.mServiceLock) {
                    java.util.Iterator<android.os.IBinder> it = this.mCameras.keySet().iterator();
                    while (it.hasNext()) {
                        try {
                            this.mVirtualCameraService.unregisterCamera(it.next());
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.w(TAG, "close(): Camera failed to be removed on camera service.", e);
                        }
                    }
                }
                this.mCameras.clear();
            }
        }
        synchronized (this.mServiceLock) {
            this.mVirtualCameraService = null;
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "VirtualCameraController:");
        java.lang.String str2 = str + str;
        synchronized (this.mCameras) {
            try {
                printWriter.printf("%sRegistered cameras:%d%n\n", str2, java.lang.Integer.valueOf(this.mCameras.size()));
                java.util.Iterator<com.android.server.companion.virtual.camera.VirtualCameraController.CameraDescriptor> it = this.mCameras.values().iterator();
                while (it.hasNext()) {
                    printWriter.printf("%s token: %s\n", str2, it.next().mConfig);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void checkConfigByPolicy(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
        if (this.mCameraPolicy == 0) {
            throw new java.lang.IllegalArgumentException("Cannot create virtual camera with DEVICE_POLICY_DEFAULT for POLICY_TYPE_CAMERA");
        }
        if (isLensFacingAlreadyPresent(virtualCameraConfig.getLensFacing())) {
            throw new java.lang.IllegalArgumentException("Only a single virtual camera can be created with lens facing " + virtualCameraConfig.getLensFacing());
        }
    }

    private boolean isLensFacingAlreadyPresent(int i) {
        synchronized (this.mCameras) {
            try {
                java.util.Iterator<com.android.server.companion.virtual.camera.VirtualCameraController.CameraDescriptor> it = this.mCameras.values().iterator();
                while (it.hasNext()) {
                    if (it.next().mConfig.getLensFacing() == i) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void connectVirtualCameraServiceIfNeeded() {
        synchronized (this.mServiceLock) {
            try {
                if (this.mVirtualCameraService == null) {
                    connectVirtualCameraService();
                }
                if (this.mVirtualCameraService == null) {
                    throw new java.lang.IllegalStateException("Virtual camera service is not connected.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void connectVirtualCameraService() {
        android.os.IBinder waitForService;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                waitForService = android.os.ServiceManager.waitForService(VIRTUAL_CAMERA_SERVICE_NAME);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (waitForService == null) {
                android.util.Slog.e(TAG, "connectVirtualCameraService: Failed to connect to the virtual camera service");
            } else {
                waitForService.linkToDeath(this, 0);
                this.mVirtualCameraService = android.companion.virtualcamera.IVirtualCameraService.Stub.asInterface(waitForService);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean registerCameraWithService(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) throws android.os.RemoteException {
        boolean registerCamera;
        android.companion.virtualcamera.VirtualCameraConfiguration serviceCameraConfiguration = com.android.server.companion.virtual.camera.VirtualCameraConversionUtil.getServiceCameraConfiguration(virtualCameraConfig);
        synchronized (this.mServiceLock) {
            registerCamera = this.mVirtualCameraService.registerCamera(virtualCameraConfig.getCallback().asBinder(), serviceCameraConfiguration);
        }
        return registerCamera;
    }

    private final class CameraDescriptor implements android.os.IBinder.DeathRecipient {
        private final android.companion.virtual.camera.VirtualCameraConfig mConfig;

        CameraDescriptor(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
            this.mConfig = virtualCameraConfig;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.d(com.android.server.companion.virtual.camera.VirtualCameraController.TAG, "Virtual camera binder died");
            com.android.server.companion.virtual.camera.VirtualCameraController.this.unregisterCamera(this.mConfig);
        }
    }
}
