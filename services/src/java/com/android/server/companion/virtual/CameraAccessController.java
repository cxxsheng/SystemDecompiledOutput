package com.android.server.companion.virtual;

/* loaded from: classes.dex */
class CameraAccessController extends android.hardware.camera2.CameraManager.AvailabilityCallback implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = "CameraAccessController";
    private final com.android.server.companion.virtual.CameraAccessController.CameraAccessBlockedCallback mBlockedCallback;
    private final android.hardware.camera2.CameraManager mCameraManager;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.os.UserManager mUserManager;
    private final com.android.server.companion.virtual.VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.lang.Object mObserverLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mObserverLock"})
    private int mObserverCount = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, com.android.server.companion.virtual.CameraAccessController.InjectionSessionData> mPackageToSessionData = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, com.android.server.companion.virtual.CameraAccessController.OpenCameraInfo> mAppsToBlockOnVirtualDevice = new android.util.ArrayMap<>();

    interface CameraAccessBlockedCallback {
        void onCameraAccessBlocked(int i);
    }

    static class OpenCameraInfo {
        public java.lang.String packageName;
        public java.util.Set<java.lang.Integer> packageUids;

        OpenCameraInfo() {
        }
    }

    static class InjectionSessionData {
        public int appUid;
        public android.util.ArrayMap<java.lang.String, android.hardware.camera2.CameraInjectionSession> cameraIdToSession = new android.util.ArrayMap<>();

        InjectionSessionData() {
        }
    }

    CameraAccessController(android.content.Context context, com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal, com.android.server.companion.virtual.CameraAccessController.CameraAccessBlockedCallback cameraAccessBlockedCallback) {
        this.mContext = context;
        this.mVirtualDeviceManagerInternal = virtualDeviceManagerInternal;
        this.mBlockedCallback = cameraAccessBlockedCallback;
        this.mCameraManager = (android.hardware.camera2.CameraManager) this.mContext.getSystemService(android.hardware.camera2.CameraManager.class);
        this.mPackageManager = this.mContext.getPackageManager();
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
    }

    public int getUserId() {
        return this.mContext.getUserId();
    }

    public int getObserverCount() {
        int i;
        synchronized (this.mObserverLock) {
            i = this.mObserverCount;
        }
        return i;
    }

    public void startObservingIfNeeded() {
        synchronized (this.mObserverLock) {
            try {
                if (this.mObserverCount == 0) {
                    this.mCameraManager.registerAvailabilityCallback(this.mContext.getMainExecutor(), this);
                }
                this.mObserverCount++;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopObservingIfNeeded() {
        synchronized (this.mObserverLock) {
            try {
                this.mObserverCount--;
                if (this.mObserverCount <= 0) {
                    close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.CAMERA_INJECT_EXTERNAL_CAMERA")
    public void blockCameraAccessIfNeeded(java.util.Set<java.lang.Integer> set) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mAppsToBlockOnVirtualDevice.size(); i++) {
                try {
                    java.lang.String keyAt = this.mAppsToBlockOnVirtualDevice.keyAt(i);
                    com.android.server.companion.virtual.CameraAccessController.OpenCameraInfo openCameraInfo = this.mAppsToBlockOnVirtualDevice.get(keyAt);
                    java.lang.String str = openCameraInfo.packageName;
                    java.util.Iterator<java.lang.Integer> it = openCameraInfo.packageUids.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            int intValue = it.next().intValue();
                            if (set.contains(java.lang.Integer.valueOf(intValue))) {
                                if (this.mPackageToSessionData.get(str) == null) {
                                    com.android.server.companion.virtual.CameraAccessController.InjectionSessionData injectionSessionData = new com.android.server.companion.virtual.CameraAccessController.InjectionSessionData();
                                    injectionSessionData.appUid = intValue;
                                    this.mPackageToSessionData.put(str, injectionSessionData);
                                }
                                startBlocking(str, keyAt);
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mObserverLock) {
            try {
                if (this.mObserverCount < 0) {
                    android.util.Slog.wtf(TAG, "Unexpected negative mObserverCount: " + this.mObserverCount);
                } else if (this.mObserverCount > 0) {
                    android.util.Slog.w(TAG, "Unexpected close with observers remaining: " + this.mObserverCount);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mCameraManager.unregisterAvailabilityCallback(this);
    }

    @android.annotation.RequiresPermission("android.permission.CAMERA_INJECT_EXTERNAL_CAMERA")
    public void onCameraOpened(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.CameraAccessController.InjectionSessionData injectionSessionData = this.mPackageToSessionData.get(str2);
                java.util.List aliveUsers = this.mUserManager.getAliveUsers();
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.util.Iterator it = aliveUsers.iterator();
                while (it.hasNext()) {
                    int queryUidFromPackageName = queryUidFromPackageName(((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier(), str2);
                    if (this.mVirtualDeviceManagerInternal.isAppRunningOnAnyVirtualDevice(queryUidFromPackageName)) {
                        if (injectionSessionData == null) {
                            injectionSessionData = new com.android.server.companion.virtual.CameraAccessController.InjectionSessionData();
                            injectionSessionData.appUid = queryUidFromPackageName;
                            this.mPackageToSessionData.put(str2, injectionSessionData);
                        }
                        if (injectionSessionData.cameraIdToSession.containsKey(str)) {
                            return;
                        }
                        startBlocking(str2, str);
                        return;
                    }
                    if (queryUidFromPackageName != -1) {
                        arraySet.add(java.lang.Integer.valueOf(queryUidFromPackageName));
                    }
                }
                com.android.server.companion.virtual.CameraAccessController.OpenCameraInfo openCameraInfo = new com.android.server.companion.virtual.CameraAccessController.OpenCameraInfo();
                openCameraInfo.packageName = str2;
                openCameraInfo.packageUids = arraySet;
                this.mAppsToBlockOnVirtualDevice.put(str, openCameraInfo);
                android.hardware.camera2.CameraInjectionSession cameraInjectionSession = injectionSessionData != null ? injectionSessionData.cameraIdToSession.get(str) : null;
                if (cameraInjectionSession != null) {
                    cameraInjectionSession.close();
                    injectionSessionData.cameraIdToSession.remove(str);
                    if (injectionSessionData.cameraIdToSession.isEmpty()) {
                        this.mPackageToSessionData.remove(str2);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onCameraClosed(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                this.mAppsToBlockOnVirtualDevice.remove(str);
                for (int size = this.mPackageToSessionData.size() - 1; size >= 0; size--) {
                    com.android.server.companion.virtual.CameraAccessController.InjectionSessionData valueAt = this.mPackageToSessionData.valueAt(size);
                    android.hardware.camera2.CameraInjectionSession cameraInjectionSession = valueAt.cameraIdToSession.get(str);
                    if (cameraInjectionSession != null) {
                        cameraInjectionSession.close();
                        valueAt.cameraIdToSession.remove(str);
                        if (valueAt.cameraIdToSession.isEmpty()) {
                            this.mPackageToSessionData.removeAt(size);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.CAMERA_INJECT_EXTERNAL_CAMERA")
    private void startBlocking(final java.lang.String str, final java.lang.String str2) {
        try {
            android.util.Slog.d(TAG, "startBlocking() cameraId: " + str2 + " packageName: " + str);
            this.mCameraManager.injectCamera(str, str2, "", this.mContext.getMainExecutor(), new android.hardware.camera2.CameraInjectionSession.InjectionStatusCallback() { // from class: com.android.server.companion.virtual.CameraAccessController.1
                public void onInjectionSucceeded(@android.annotation.NonNull android.hardware.camera2.CameraInjectionSession cameraInjectionSession) {
                    com.android.server.companion.virtual.CameraAccessController.this.onInjectionSucceeded(str2, str, cameraInjectionSession);
                }

                public void onInjectionError(@android.annotation.NonNull int i) {
                    com.android.server.companion.virtual.CameraAccessController.this.onInjectionError(str2, str, i);
                }
            });
        } catch (android.hardware.camera2.CameraAccessException e) {
            android.util.Slog.e(TAG, "Failed to injectCamera for cameraId:" + str2 + " package:" + str, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionSucceeded(java.lang.String str, java.lang.String str2, @android.annotation.NonNull android.hardware.camera2.CameraInjectionSession cameraInjectionSession) {
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.CameraAccessController.InjectionSessionData injectionSessionData = this.mPackageToSessionData.get(str2);
                if (injectionSessionData == null) {
                    android.util.Slog.e(TAG, "onInjectionSucceeded didn't find expected entry for package " + str2);
                    cameraInjectionSession.close();
                    return;
                }
                android.hardware.camera2.CameraInjectionSession put = injectionSessionData.cameraIdToSession.put(str, cameraInjectionSession);
                if (put != null) {
                    android.util.Slog.e(TAG, "onInjectionSucceeded found unexpected existing session for camera " + str);
                    put.close();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInjectionError(java.lang.String str, java.lang.String str2, @android.annotation.NonNull int i) {
        if (i != 2) {
            android.util.Slog.e(TAG, "Unexpected injection error code:" + i + " for camera:" + str + " and package:" + str2);
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.CameraAccessController.InjectionSessionData injectionSessionData = this.mPackageToSessionData.get(str2);
                if (injectionSessionData != null) {
                    this.mBlockedCallback.onCameraAccessBlocked(injectionSessionData.appUid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int queryUidFromPackageName(int i, java.lang.String str) {
        try {
            return this.mPackageManager.getApplicationInfoAsUser(str, 1, i).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "queryUidFromPackageName - unknown package " + str, e);
            return -1;
        }
    }
}
