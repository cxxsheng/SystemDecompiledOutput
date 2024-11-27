package com.android.server.camera;

/* loaded from: classes.dex */
public class CameraServiceProxy extends com.android.server.SystemService implements android.os.Handler.Callback, android.os.IBinder.DeathRecipient {
    private static final java.lang.String CAMERA_SERVICE_BINDER_NAME = "media.camera";
    public static final java.lang.String CAMERA_SERVICE_PROXY_BINDER_NAME = "media.camera.proxy";
    private static final boolean DEBUG = false;
    private static final float MAX_PREVIEW_FPS = 60.0f;
    private static final int MAX_STREAM_STATISTICS = 5;
    private static final int MAX_USAGE_HISTORY = 20;
    private static final float MIN_PREVIEW_FPS = 30.0f;
    private static final int MSG_NOTIFY_DEVICE_STATE = 2;
    private static final int MSG_SWITCH_USER = 1;
    private static final java.lang.String NFC_NOTIFICATION_PROP = "ro.camera.notify_nfc";
    public static final long OVERRIDE_CAMERA_RESIZABLE_AND_SDK_CHECK = 191513214;
    public static final long OVERRIDE_CAMERA_ROTATE_AND_CROP_DEFAULTS = 189229956;
    private static final int RETRY_DELAY_TIME = 20;
    private static final int RETRY_TIMES = 60;
    private static final java.lang.String TAG = "CameraService_proxy";
    private static final android.os.IBinder nfcInterfaceToken = new android.os.Binder();
    private final android.util.ArrayMap<java.lang.String, com.android.server.camera.CameraServiceProxy.CameraUsageEvent> mActiveCameraUsage;
    private final android.hardware.ICameraServiceProxy.Stub mCameraServiceProxy;
    private android.hardware.ICameraService mCameraServiceRaw;
    private final java.util.List<com.android.server.camera.CameraServiceProxy.CameraUsageEvent> mCameraUsageHistory;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mDeviceState;
    private final com.android.server.camera.CameraServiceProxy.DisplayWindowListener mDisplayWindowListener;
    private java.util.Set<java.lang.Integer> mEnabledCameraUsers;
    private final android.hardware.devicestate.DeviceStateManager.FoldStateListener mFoldStateListener;
    private final android.os.Handler mHandler;
    private final com.android.server.ServiceThread mHandlerThread;
    private final android.content.BroadcastReceiver mIntentReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mLastReportedDeviceState;
    private int mLastUser;
    private final java.lang.Object mLock;
    private java.util.concurrent.ScheduledThreadPoolExecutor mLogWriterService;
    private final boolean mNotifyNfc;
    private android.os.UserManager mUserManager;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DeviceStateFlags {
    }

    public static final class TaskInfo {
        public int displayId;
        public int frontTaskId;
        public boolean isFixedOrientationLandscape;
        public boolean isFixedOrientationPortrait;
        public boolean isResizeable;
        public int userId;
    }

    private static class CameraUsageEvent {
        public final int mAPILevel;
        public final int mAction;
        public final int mCameraFacing;
        public final java.lang.String mCameraId;
        public final java.lang.String mClientName;
        public boolean mDeviceError;
        public int mInternalReconfigure;
        public final boolean mIsNdk;
        public final int mLatencyMs;
        public final long mLogId;
        public final int mOperatingMode;
        public long mRequestCount;
        public long mResultErrorCount;
        public final int mSessionIndex;
        public java.util.List<android.hardware.CameraStreamStats> mStreamStats;
        public boolean mUsedUltraWide;
        public boolean mUsedZoomOverride;
        public java.lang.String mUserTag;
        public int mVideoStabilizationMode;
        public android.hardware.CameraExtensionSessionStats mExtSessionStats = null;
        private long mDurationOrStartTimeMs = android.os.SystemClock.elapsedRealtime();
        private boolean mCompleted = false;

        CameraUsageEvent(java.lang.String str, int i, java.lang.String str2, int i2, boolean z, int i3, int i4, int i5, boolean z2, long j, int i6) {
            this.mCameraId = str;
            this.mCameraFacing = i;
            this.mClientName = str2;
            this.mAPILevel = i2;
            this.mIsNdk = z;
            this.mAction = i3;
            this.mLatencyMs = i4;
            this.mOperatingMode = i5;
            this.mDeviceError = z2;
            this.mLogId = j;
            this.mSessionIndex = i6;
        }

        public void markCompleted(int i, long j, long j2, boolean z, java.util.List<android.hardware.CameraStreamStats> list, java.lang.String str, int i2, boolean z2, boolean z3, android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats) {
            if (!this.mCompleted) {
                this.mCompleted = true;
                this.mDurationOrStartTimeMs = android.os.SystemClock.elapsedRealtime() - this.mDurationOrStartTimeMs;
                this.mInternalReconfigure = i;
                this.mRequestCount = j;
                this.mResultErrorCount = j2;
                this.mDeviceError = z;
                this.mStreamStats = list;
                this.mUserTag = str;
                this.mVideoStabilizationMode = i2;
                this.mUsedUltraWide = z2;
                this.mUsedZoomOverride = z3;
                this.mExtSessionStats = cameraExtensionSessionStats;
            }
        }

        public long getDuration() {
            if (this.mCompleted) {
                return this.mDurationOrStartTimeMs;
            }
            return 0L;
        }
    }

    private final class DisplayWindowListener extends android.view.IDisplayWindowListener.Stub {
        private DisplayWindowListener() {
        }

        public void onDisplayConfigurationChanged(int i, android.content.res.Configuration configuration) {
            android.hardware.ICameraService cameraServiceRawLocked = com.android.server.camera.CameraServiceProxy.this.getCameraServiceRawLocked();
            if (cameraServiceRawLocked == null) {
                return;
            }
            try {
                cameraServiceRawLocked.notifyDisplayConfigurationChange();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.camera.CameraServiceProxy.TAG, "Could not notify cameraserver, remote exception: " + e);
            }
        }

        public void onDisplayAdded(int i) {
        }

        public void onDisplayRemoved(int i) {
        }

        public void onFixedRotationStarted(int i, int i2) {
        }

        public void onFixedRotationFinished(int i) {
        }

        public void onKeepClearAreasChanged(int i, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) {
        }
    }

    private static boolean isMOrBelow(android.content.Context context, java.lang.String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).applicationInfo.targetSdkVersion <= 23;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Package name not found!");
            return false;
        }
    }

    public static int getCropRotateScale(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.camera.CameraServiceProxy.TaskInfo taskInfo, int i, int i2, boolean z) {
        int i3;
        if (taskInfo == null) {
            return 0;
        }
        if (context.getResources().getBoolean(android.R.bool.config_isDesktopModeSupported)) {
            android.util.Slog.v(TAG, "Disable Rotate and Crop to avoid conflicts with WM force rotation treatment.");
            return 0;
        }
        if (i2 != 0 && i2 != 1) {
            android.util.Log.v(TAG, "lensFacing=" + i2 + ". Crop-rotate-scale is disabled.");
            return 0;
        }
        if (!z && !isMOrBelow(context, str) && taskInfo.isResizeable) {
            android.util.Slog.v(TAG, "The activity is N or above and claims to support resizeable-activity. Crop-rotate-scale is disabled.");
            return 0;
        }
        if (!taskInfo.isFixedOrientationPortrait && !taskInfo.isFixedOrientationLandscape) {
            android.util.Log.v(TAG, "Non-fixed orientation activity. Crop-rotate-scale is disabled.");
            return 0;
        }
        switch (i) {
            case 0:
                i3 = 0;
                break;
            case 1:
                i3 = 90;
                break;
            case 2:
                i3 = 180;
                break;
            case 3:
                i3 = android.companion.virtualcamera.SensorOrientation.ORIENTATION_270;
                break;
            default:
                android.util.Log.e(TAG, "Unsupported display rotation: " + i);
                return 0;
        }
        android.util.Slog.v(TAG, "Display.getRotation()=" + i3 + " isFixedOrientationPortrait=" + taskInfo.isFixedOrientationPortrait + " isFixedOrientationLandscape=" + taskInfo.isFixedOrientationLandscape);
        if (i3 == 0) {
            return 0;
        }
        if (i2 == 0) {
            i3 = 360 - i3;
        }
        switch (i3) {
            case 90:
                break;
            case 180:
                break;
            case android.companion.virtualcamera.SensorOrientation.ORIENTATION_270 /* 270 */:
                break;
        }
        return 0;
    }

    public CameraServiceProxy(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mActiveCameraUsage = new android.util.ArrayMap<>();
        this.mCameraUsageHistory = new java.util.ArrayList();
        this.mLogWriterService = new java.util.concurrent.ScheduledThreadPoolExecutor(1);
        this.mDisplayWindowListener = new com.android.server.camera.CameraServiceProxy.DisplayWindowListener();
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.camera.CameraServiceProxy.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                if (action == null) {
                    return;
                }
                switch (action.hashCode()) {
                    case -2114103349:
                        if (action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2061058799:
                        if (action.equals("android.intent.action.USER_REMOVED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1608292967:
                        if (action.equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -385593787:
                        if (action.equals("android.intent.action.MANAGED_PROFILE_ADDED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -201513518:
                        if (action.equals("android.intent.action.USER_INFO_CHANGED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1051477093:
                        if (action.equals("android.intent.action.MANAGED_PROFILE_REMOVED")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1121780209:
                        if (action.equals("android.intent.action.USER_ADDED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        synchronized (com.android.server.camera.CameraServiceProxy.this.mLock) {
                            try {
                                if (com.android.server.camera.CameraServiceProxy.this.mEnabledCameraUsers == null) {
                                    return;
                                }
                                com.android.server.camera.CameraServiceProxy.this.switchUserLocked(com.android.server.camera.CameraServiceProxy.this.mLastUser);
                                return;
                            } finally {
                            }
                        }
                    case 5:
                    case 6:
                        synchronized (com.android.server.camera.CameraServiceProxy.this.mLock) {
                            try {
                                android.hardware.usb.UsbDevice usbDevice = (android.hardware.usb.UsbDevice) intent.getParcelableExtra("device", android.hardware.usb.UsbDevice.class);
                                if (usbDevice != null) {
                                    com.android.server.camera.CameraServiceProxy.this.notifyUsbDeviceHotplugLocked(usbDevice, action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED"));
                                }
                            } finally {
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mCameraServiceProxy = new android.hardware.ICameraServiceProxy.Stub() { // from class: com.android.server.camera.CameraServiceProxy.2
            public int getRotateAndCropOverride(java.lang.String str, int i, int i2) {
                com.android.server.camera.CameraServiceProxy.TaskInfo taskInfo;
                boolean z;
                if (android.os.Binder.getCallingUid() != 1047) {
                    android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Calling UID: " + android.os.Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return 0;
                }
                try {
                    android.content.pm.ParceledListSlice recentTasks = android.app.ActivityTaskManager.getService().getRecentTasks(2, 0, i2);
                    if (recentTasks != null && !recentTasks.getList().isEmpty()) {
                        java.util.Iterator it = recentTasks.getList().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                taskInfo = null;
                                break;
                            }
                            android.app.ActivityManager.RecentTaskInfo recentTaskInfo = (android.app.ActivityManager.RecentTaskInfo) it.next();
                            if (recentTaskInfo.topActivityInfo != null && str.equals(recentTaskInfo.topActivityInfo.packageName)) {
                                com.android.server.camera.CameraServiceProxy.TaskInfo taskInfo2 = new com.android.server.camera.CameraServiceProxy.TaskInfo();
                                taskInfo2.frontTaskId = recentTaskInfo.taskId;
                                taskInfo2.isResizeable = recentTaskInfo.topActivityInfo.resizeMode != 0;
                                taskInfo2.displayId = recentTaskInfo.displayId;
                                taskInfo2.userId = recentTaskInfo.userId;
                                taskInfo2.isFixedOrientationLandscape = android.content.pm.ActivityInfo.isFixedOrientationLandscape(recentTaskInfo.topActivityInfo.screenOrientation);
                                taskInfo2.isFixedOrientationPortrait = android.content.pm.ActivityInfo.isFixedOrientationPortrait(recentTaskInfo.topActivityInfo.screenOrientation);
                                taskInfo = taskInfo2;
                            }
                        }
                        if (taskInfo == null) {
                            android.util.Log.e(com.android.server.camera.CameraServiceProxy.TAG, "Recent tasks don't include camera client package name: " + str);
                            return 0;
                        }
                        if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.camera.CameraServiceProxy.OVERRIDE_CAMERA_ROTATE_AND_CROP_DEFAULTS, str, android.os.UserHandle.getUserHandleForUid(taskInfo.userId))) {
                            android.util.Slog.v(com.android.server.camera.CameraServiceProxy.TAG, "OVERRIDE_CAMERA_ROTATE_AND_CROP_DEFAULTS enabled!");
                            return 0;
                        }
                        if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.camera.CameraServiceProxy.OVERRIDE_CAMERA_RESIZABLE_AND_SDK_CHECK, str, android.os.UserHandle.getUserHandleForUid(taskInfo.userId))) {
                            android.util.Slog.v(com.android.server.camera.CameraServiceProxy.TAG, "OVERRIDE_CAMERA_RESIZABLE_AND_SDK_CHECK enabled!");
                            z = true;
                        } else {
                            z = false;
                        }
                        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) com.android.server.camera.CameraServiceProxy.this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
                        if (displayManager != null) {
                            android.view.Display display = displayManager.getDisplay(taskInfo.displayId);
                            if (display == null) {
                                android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Invalid display id: " + taskInfo.displayId);
                                return 0;
                            }
                            return com.android.server.camera.CameraServiceProxy.getCropRotateScale(com.android.server.camera.CameraServiceProxy.this.mContext, str, taskInfo, display.getRotation(), i, z);
                        }
                        android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Failed to query display manager!");
                        return 0;
                    }
                    android.util.Log.e(com.android.server.camera.CameraServiceProxy.TAG, "Recent task list is empty!");
                    return 0;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.camera.CameraServiceProxy.TAG, "Failed to query recent tasks!");
                    return 0;
                }
            }

            public int getAutoframingOverride(java.lang.String str) {
                return 0;
            }

            public void pingForUserUpdate() {
                if (android.os.Binder.getCallingUid() != 1047) {
                    android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Calling UID: " + android.os.Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                com.android.server.camera.CameraServiceProxy.this.notifySwitchWithRetries(60);
                com.android.server.camera.CameraServiceProxy.this.notifyDeviceStateWithRetries(60);
            }

            public void notifyCameraState(android.hardware.CameraSessionStats cameraSessionStats) {
                if (android.os.Binder.getCallingUid() != 1047) {
                    android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Calling UID: " + android.os.Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                com.android.server.camera.CameraServiceProxy.cameraStateToString(cameraSessionStats.getNewCameraState());
                com.android.server.camera.CameraServiceProxy.cameraFacingToString(cameraSessionStats.getFacing());
                com.android.server.camera.CameraServiceProxy.this.updateActivityCount(cameraSessionStats);
            }

            public boolean isCameraDisabled(int i) {
                if (android.os.Binder.getCallingUid() != 1047) {
                    android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Calling UID: " + android.os.Binder.getCallingUid() + " doesn't match expected camera service UID!");
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) com.android.server.camera.CameraServiceProxy.this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class);
                    if (devicePolicyManager != null) {
                        return devicePolicyManager.getCameraDisabled(null, i);
                    }
                    android.util.Slog.e(com.android.server.camera.CameraServiceProxy.TAG, "Failed to get the device policy manager service");
                    return false;
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mContext = context;
        this.mHandlerThread = new com.android.server.ServiceThread(TAG, -4, false);
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper(), this);
        this.mNotifyNfc = android.os.SystemProperties.getInt(NFC_NOTIFICATION_PROP, 0) > 0;
        this.mLogWriterService.setKeepAliveTime(1L, java.util.concurrent.TimeUnit.SECONDS);
        this.mLogWriterService.allowCoreThreadTimeOut(true);
        this.mFoldStateListener = new android.hardware.devicestate.DeviceStateManager.FoldStateListener(this.mContext, new java.util.function.Consumer() { // from class: com.android.server.camera.CameraServiceProxy$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.camera.CameraServiceProxy.this.lambda$new$0((java.lang.Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            setDeviceStateFlags(4);
        } else {
            clearDeviceStateFlags(4);
        }
    }

    private void setDeviceStateFlags(int i) {
        synchronized (this.mLock) {
            try {
                this.mHandler.removeMessages(2);
                this.mDeviceState = i | this.mDeviceState;
                if (this.mDeviceState != this.mLastReportedDeviceState) {
                    notifyDeviceStateWithRetriesLocked(60);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void clearDeviceStateFlags(int i) {
        synchronized (this.mLock) {
            try {
                this.mHandler.removeMessages(2);
                this.mDeviceState = (~i) & this.mDeviceState;
                if (this.mDeviceState != this.mLastReportedDeviceState) {
                    notifyDeviceStateWithRetriesLocked(60);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                notifySwitchWithRetries(message.arg1);
                break;
            case 2:
                notifyDeviceStateWithRetries(message.arg1);
                break;
            default:
                android.util.Slog.e(TAG, "CameraServiceProxy error, invalid message: " + message.what);
                break;
        }
        return true;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mUserManager = android.os.UserManager.get(this.mContext);
        if (this.mUserManager == null) {
            throw new java.lang.IllegalStateException("UserManagerService must start before CameraServiceProxy!");
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
        publishBinderService(CAMERA_SERVICE_PROXY_BINDER_NAME, this.mCameraServiceProxy);
        publishLocalService(com.android.server.camera.CameraServiceProxy.class, this);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            com.android.server.camera.CameraStatsJobService.schedule(this.mContext);
            try {
                for (int i2 : android.view.WindowManagerGlobal.getWindowManagerService().registerDisplayWindowListener(this.mDisplayWindowListener)) {
                    this.mDisplayWindowListener.onDisplayAdded(i2);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to register display window listener!");
            }
            ((android.hardware.devicestate.DeviceStateManager) this.mContext.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).registerCallback(new android.os.HandlerExecutor(this.mHandler), this.mFoldStateListener);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabledCameraUsers == null) {
                    switchUserLocked(targetUser.getUserIdentifier());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        synchronized (this.mLock) {
            switchUserLocked(targetUser2.getUserIdentifier());
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mLock) {
            try {
                this.mCameraServiceRaw = null;
                boolean isEmpty = this.mActiveCameraUsage.isEmpty();
                this.mActiveCameraUsage.clear();
                if (this.mNotifyNfc && !isEmpty) {
                    notifyNfcService(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class EventWriterTask implements java.lang.Runnable {
        private static final long WRITER_SLEEP_MS = 100;
        private java.util.ArrayList<com.android.server.camera.CameraServiceProxy.CameraUsageEvent> mEventList;

        public EventWriterTask(java.util.ArrayList<com.android.server.camera.CameraServiceProxy.CameraUsageEvent> arrayList) {
            this.mEventList = arrayList;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mEventList != null) {
                java.util.Iterator<com.android.server.camera.CameraServiceProxy.CameraUsageEvent> it = this.mEventList.iterator();
                while (it.hasNext()) {
                    logCameraUsageEvent(it.next());
                    try {
                        java.lang.Thread.sleep(WRITER_SLEEP_MS);
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                this.mEventList.clear();
            }
        }

        private void logCameraUsageEvent(com.android.server.camera.CameraServiceProxy.CameraUsageEvent cameraUsageEvent) {
            int i;
            boolean z;
            int i2;
            int i3;
            switch (cameraUsageEvent.mCameraFacing) {
                case 0:
                    i = 1;
                    break;
                case 1:
                    i = 2;
                    break;
                case 2:
                    i = 3;
                    break;
                default:
                    android.util.Slog.w(com.android.server.camera.CameraServiceProxy.TAG, "Unknown camera facing: " + cameraUsageEvent.mCameraFacing);
                    i = 0;
                    break;
            }
            int i4 = -1;
            if (cameraUsageEvent.mExtSessionStats == null) {
                z = false;
                i2 = -1;
            } else {
                switch (cameraUsageEvent.mExtSessionStats.type) {
                    case 0:
                        i4 = 0;
                        break;
                    case 1:
                        i4 = 1;
                        break;
                    case 2:
                        i4 = 2;
                        break;
                    case 3:
                        i4 = 3;
                        break;
                    case 4:
                        i4 = 4;
                        break;
                    default:
                        android.util.Slog.w(com.android.server.camera.CameraServiceProxy.TAG, "Unknown extension type: " + cameraUsageEvent.mExtSessionStats.type);
                        break;
                }
                z = cameraUsageEvent.mExtSessionStats.isAdvanced;
                i2 = i4;
            }
            if (cameraUsageEvent.mStreamStats == null) {
                i3 = 0;
            } else {
                i3 = cameraUsageEvent.mStreamStats.size();
            }
            android.stats.camera.nano.CameraProtos.CameraStreamProto[] cameraStreamProtoArr = new android.stats.camera.nano.CameraProtos.CameraStreamProto[5];
            for (int i5 = 0; i5 < 5; i5++) {
                cameraStreamProtoArr[i5] = new android.stats.camera.nano.CameraProtos.CameraStreamProto();
                if (i5 < i3) {
                    android.hardware.CameraStreamStats cameraStreamStats = cameraUsageEvent.mStreamStats.get(i5);
                    cameraStreamProtoArr[i5].width = cameraStreamStats.getWidth();
                    cameraStreamProtoArr[i5].height = cameraStreamStats.getHeight();
                    cameraStreamProtoArr[i5].format = cameraStreamStats.getFormat();
                    cameraStreamProtoArr[i5].dataSpace = cameraStreamStats.getDataSpace();
                    cameraStreamProtoArr[i5].usage = cameraStreamStats.getUsage();
                    cameraStreamProtoArr[i5].requestCount = cameraStreamStats.getRequestCount();
                    cameraStreamProtoArr[i5].errorCount = cameraStreamStats.getErrorCount();
                    cameraStreamProtoArr[i5].firstCaptureLatencyMillis = cameraStreamStats.getStartLatencyMs();
                    cameraStreamProtoArr[i5].maxHalBuffers = cameraStreamStats.getMaxHalBuffers();
                    cameraStreamProtoArr[i5].maxAppBuffers = cameraStreamStats.getMaxAppBuffers();
                    cameraStreamProtoArr[i5].histogramType = cameraStreamStats.getHistogramType();
                    cameraStreamProtoArr[i5].histogramBins = cameraStreamStats.getHistogramBins();
                    cameraStreamProtoArr[i5].histogramCounts = cameraStreamStats.getHistogramCounts();
                    cameraStreamProtoArr[i5].dynamicRangeProfile = cameraStreamStats.getDynamicRangeProfile();
                    cameraStreamProtoArr[i5].streamUseCase = cameraStreamStats.getStreamUseCase();
                    cameraStreamProtoArr[i5].colorSpace = cameraStreamStats.getColorSpace();
                }
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CAMERA_ACTION_EVENT, cameraUsageEvent.getDuration(), cameraUsageEvent.mAPILevel, cameraUsageEvent.mClientName, i, cameraUsageEvent.mCameraId, cameraUsageEvent.mAction, cameraUsageEvent.mIsNdk, cameraUsageEvent.mLatencyMs, cameraUsageEvent.mOperatingMode, cameraUsageEvent.mInternalReconfigure, cameraUsageEvent.mRequestCount, cameraUsageEvent.mResultErrorCount, cameraUsageEvent.mDeviceError, i3, com.android.framework.protobuf.nano.MessageNano.toByteArray(cameraStreamProtoArr[0]), com.android.framework.protobuf.nano.MessageNano.toByteArray(cameraStreamProtoArr[1]), com.android.framework.protobuf.nano.MessageNano.toByteArray(cameraStreamProtoArr[2]), com.android.framework.protobuf.nano.MessageNano.toByteArray(cameraStreamProtoArr[3]), com.android.framework.protobuf.nano.MessageNano.toByteArray(cameraStreamProtoArr[4]), cameraUsageEvent.mUserTag, cameraUsageEvent.mVideoStabilizationMode, cameraUsageEvent.mLogId, cameraUsageEvent.mSessionIndex, i2, z, cameraUsageEvent.mUsedUltraWide, cameraUsageEvent.mUsedZoomOverride);
        }
    }

    void dumpUsageEvents() {
        synchronized (this.mLock) {
            java.util.Collections.shuffle(this.mCameraUsageHistory);
            this.mLogWriterService.execute(new com.android.server.camera.CameraServiceProxy.EventWriterTask(new java.util.ArrayList(this.mCameraUsageHistory)));
            this.mCameraUsageHistory.clear();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.camera.CameraStatsJobService.schedule(this.mContext);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.hardware.ICameraService getCameraServiceRawLocked() {
        if (this.mCameraServiceRaw == null) {
            android.os.IBinder binderService = getBinderService(CAMERA_SERVICE_BINDER_NAME);
            if (binderService == null) {
                return null;
            }
            try {
                binderService.linkToDeath(this, 0);
                this.mCameraServiceRaw = android.hardware.ICameraService.Stub.asInterface(binderService);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Could not link to death of native camera service");
                return null;
            }
        }
        return this.mCameraServiceRaw;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchUserLocked(int i) {
        java.util.Set<java.lang.Integer> enabledUserHandles = getEnabledUserHandles(i);
        this.mLastUser = i;
        if (this.mEnabledCameraUsers == null || !this.mEnabledCameraUsers.equals(enabledUserHandles)) {
            this.mEnabledCameraUsers = enabledUserHandles;
            notifySwitchWithRetriesLocked(60);
        }
    }

    private boolean isAutomotive() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    private java.util.Set<java.lang.Integer> getEnabledUserHandles(int i) {
        int[] enabledProfileIds = this.mUserManager.getEnabledProfileIds(i);
        android.util.ArraySet arraySet = new android.util.ArraySet(enabledProfileIds.length);
        for (int i2 : enabledProfileIds) {
            arraySet.add(java.lang.Integer.valueOf(i2));
        }
        if (com.android.internal.camera.flags.Flags.cameraHsumPermission() && android.os.UserManager.isHeadlessSystemUserMode() && isAutomotive()) {
            arraySet.add(0);
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySwitchWithRetries(int i) {
        synchronized (this.mLock) {
            notifySwitchWithRetriesLocked(i);
        }
    }

    private void notifySwitchWithRetriesLocked(int i) {
        if (this.mEnabledCameraUsers == null) {
            return;
        }
        if (notifyCameraserverLocked(1, this.mEnabledCameraUsers)) {
            i = 0;
        }
        if (i <= 0) {
            return;
        }
        android.util.Slog.i(TAG, "Could not notify camera service of user switch, retrying...");
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, i - 1, 0, null), 20L);
    }

    private boolean notifyCameraserverLocked(int i, java.util.Set<java.lang.Integer> set) {
        if (getCameraServiceRawLocked() == null) {
            android.util.Slog.w(TAG, "Could not notify cameraserver, camera service not available.");
            return false;
        }
        try {
            this.mCameraServiceRaw.notifySystemEvent(i, toArray(set));
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Could not notify cameraserver, remote exception: " + e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDeviceStateWithRetries(int i) {
        synchronized (this.mLock) {
            notifyDeviceStateWithRetriesLocked(i);
        }
    }

    private void notifyDeviceStateWithRetriesLocked(int i) {
        if (notifyDeviceStateChangeLocked(this.mDeviceState) || i <= 0) {
            return;
        }
        android.util.Slog.i(TAG, "Could not notify camera service of device state change, retrying...");
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, i - 1, 0, null), 20L);
    }

    private boolean notifyDeviceStateChangeLocked(int i) {
        if (getCameraServiceRawLocked() == null) {
            android.util.Slog.w(TAG, "Could not notify cameraserver, camera service not available.");
            return false;
        }
        try {
            this.mCameraServiceRaw.notifyDeviceStateChange(i);
            this.mLastReportedDeviceState = i;
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Could not notify cameraserver, remote exception: " + e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean notifyUsbDeviceHotplugLocked(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, boolean z) {
        if (!usbDevice.getHasVideoCapture()) {
            return false;
        }
        if (getCameraServiceRawLocked() == null) {
            android.util.Slog.w(TAG, "Could not notify cameraserver, camera service not available.");
            return false;
        }
        try {
            this.mCameraServiceRaw.notifySystemEvent(z ? 2 : 3, new int[]{usbDevice.getDeviceId()});
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Could not notify cameraserver, remote exception: " + e);
            return false;
        }
    }

    private float getMinFps(android.hardware.CameraSessionStats cameraSessionStats) {
        return java.lang.Math.max(java.lang.Math.min(cameraSessionStats.getMaxPreviewFps(), 60.0f), MIN_PREVIEW_FPS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x01c8  */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v10 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateActivityCount(android.hardware.CameraSessionStats cameraSessionStats) {
        ?? r17;
        boolean z;
        java.lang.Object obj;
        boolean z2;
        boolean z3;
        boolean isEmpty;
        java.lang.String cameraId = cameraSessionStats.getCameraId();
        int newCameraState = cameraSessionStats.getNewCameraState();
        int facing = cameraSessionStats.getFacing();
        java.lang.String clientName = cameraSessionStats.getClientName();
        int apiLevel = cameraSessionStats.getApiLevel();
        boolean isNdk = cameraSessionStats.isNdk();
        int sessionType = cameraSessionStats.getSessionType();
        int internalReconfigureCount = cameraSessionStats.getInternalReconfigureCount();
        int latencyMs = cameraSessionStats.getLatencyMs();
        long requestCount = cameraSessionStats.getRequestCount();
        long resultErrorCount = cameraSessionStats.getResultErrorCount();
        boolean deviceErrorFlag = cameraSessionStats.getDeviceErrorFlag();
        java.util.List<android.hardware.CameraStreamStats> streamStats = cameraSessionStats.getStreamStats();
        java.lang.String userTag = cameraSessionStats.getUserTag();
        int videoStabilizationMode = cameraSessionStats.getVideoStabilizationMode();
        boolean usedUltraWide = com.android.internal.camera.flags.Flags.logUltrawideUsage() ? cameraSessionStats.getUsedUltraWide() : false;
        boolean usedZoomOverride = com.android.internal.camera.flags.Flags.logZoomOverrideUsage() ? cameraSessionStats.getUsedZoomOverride() : false;
        long logId = cameraSessionStats.getLogId();
        int sessionIndex = cameraSessionStats.getSessionIndex();
        android.hardware.CameraExtensionSessionStats extensionSessionStats = cameraSessionStats.getExtensionSessionStats();
        java.lang.Object obj2 = this.mLock;
        synchronized (obj2) {
            try {
                try {
                    boolean isEmpty2 = this.mActiveCameraUsage.isEmpty();
                    boolean z4 = true;
                    switch (newCameraState) {
                        case 0:
                            z = isEmpty2;
                            obj = obj2;
                            android.media.AudioManager audioManager = (android.media.AudioManager) getContext().getSystemService(android.media.AudioManager.class);
                            if (audioManager != null) {
                                audioManager.setParameters("cameraFacing=" + (facing == 0 ? "back" : "front"));
                            }
                            this.mCameraUsageHistory.add(new com.android.server.camera.CameraServiceProxy.CameraUsageEvent(cameraId, facing, clientName, apiLevel, isNdk, 1, latencyMs, sessionType, deviceErrorFlag, logId, sessionIndex));
                            isEmpty = this.mActiveCameraUsage.isEmpty();
                            if (this.mNotifyNfc && z != isEmpty) {
                                notifyNfcService(isEmpty);
                            }
                            return;
                        case 1:
                            z = isEmpty2;
                            obj = obj2;
                            int i = 0;
                            while (true) {
                                if (i >= this.mActiveCameraUsage.size()) {
                                    z2 = false;
                                } else if (this.mActiveCameraUsage.valueAt(i).mClientName.equals(clientName)) {
                                    z2 = true;
                                } else {
                                    i++;
                                }
                            }
                            if (!z2) {
                                ((com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class)).addRefreshRateRangeForPackage(clientName, getMinFps(cameraSessionStats), 60.0f);
                            }
                            com.android.server.camera.CameraServiceProxy.CameraUsageEvent put = this.mActiveCameraUsage.put(cameraId, new com.android.server.camera.CameraServiceProxy.CameraUsageEvent(cameraId, facing, clientName, apiLevel, isNdk, 3, latencyMs, sessionType, deviceErrorFlag, logId, sessionIndex));
                            if (put != null) {
                                android.util.Slog.w(TAG, "Camera " + cameraId + " was already marked as active");
                                put.markCompleted(0, 0L, 0L, false, streamStats, "", -1, false, false, new android.hardware.CameraExtensionSessionStats());
                                this.mCameraUsageHistory.add(put);
                            }
                            isEmpty = this.mActiveCameraUsage.isEmpty();
                            if (this.mNotifyNfc) {
                                notifyNfcService(isEmpty);
                                break;
                            }
                            return;
                        case 2:
                        case 3:
                            com.android.server.camera.CameraServiceProxy.CameraUsageEvent remove = this.mActiveCameraUsage.remove(cameraId);
                            if (remove != null) {
                                remove.markCompleted(internalReconfigureCount, requestCount, resultErrorCount, deviceErrorFlag, streamStats, userTag, videoStabilizationMode, usedUltraWide, usedZoomOverride, extensionSessionStats);
                                this.mCameraUsageHistory.add(remove);
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= this.mActiveCameraUsage.size()) {
                                        z4 = false;
                                    } else if (!this.mActiveCameraUsage.valueAt(i2).mClientName.equals(clientName)) {
                                        i2++;
                                    }
                                }
                                if (!z4) {
                                    ((com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class)).removeRefreshRateRangeForPackage(clientName);
                                }
                                z3 = false;
                            } else {
                                z3 = deviceErrorFlag;
                            }
                            if (newCameraState == 3) {
                                z = isEmpty2;
                                obj = obj2;
                                this.mCameraUsageHistory.add(new com.android.server.camera.CameraServiceProxy.CameraUsageEvent(cameraId, facing, clientName, apiLevel, isNdk, 2, latencyMs, sessionType, z3, logId, sessionIndex));
                            } else {
                                z = isEmpty2;
                                obj = obj2;
                            }
                            if (this.mCameraUsageHistory.size() > 20) {
                                dumpUsageEvents();
                            }
                            isEmpty = this.mActiveCameraUsage.isEmpty();
                            if (this.mNotifyNfc) {
                            }
                            return;
                        default:
                            z = isEmpty2;
                            obj = obj2;
                            isEmpty = this.mActiveCameraUsage.isEmpty();
                            if (this.mNotifyNfc) {
                            }
                            return;
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    r17 = obj2;
                }
                th = th;
                r17 = obj2;
            } catch (java.lang.Throwable th2) {
                th = th2;
                r17 = requestCount;
            }
            throw th;
        }
    }

    private void notifyNfcService(boolean z) {
        android.nfc.NfcManager nfcManager = (android.nfc.NfcManager) this.mContext.getSystemService(android.nfc.NfcManager.class);
        if (nfcManager == null) {
            android.util.Slog.w(TAG, "Could not connect to NFC service to notify it of camera state");
            return;
        }
        android.nfc.NfcAdapter defaultAdapter = nfcManager.getDefaultAdapter();
        if (defaultAdapter == null) {
            android.util.Slog.w(TAG, "Could not connect to NFC service to notify it of camera state");
        } else {
            defaultAdapter.setReaderModePollingEnabled(z);
        }
    }

    private static int[] toArray(java.util.Collection<java.lang.Integer> collection) {
        int[] iArr = new int[collection.size()];
        java.util.Iterator<java.lang.Integer> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String cameraStateToString(int i) {
        switch (i) {
            case 0:
                return "CAMERA_STATE_OPEN";
            case 1:
                return "CAMERA_STATE_ACTIVE";
            case 2:
                return "CAMERA_STATE_IDLE";
            case 3:
                return "CAMERA_STATE_CLOSED";
            default:
                return "CAMERA_STATE_UNKNOWN";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String cameraFacingToString(int i) {
        switch (i) {
            case 0:
                return "CAMERA_FACING_BACK";
            case 1:
                return "CAMERA_FACING_FRONT";
            case 2:
                return "CAMERA_FACING_EXTERNAL";
            default:
                return "CAMERA_FACING_UNKNOWN";
        }
    }

    private static java.lang.String cameraHistogramTypeToString(int i) {
        switch (i) {
            case 1:
                return "HISTOGRAM_TYPE_CAPTURE_LATENCY";
            default:
                return "HISTOGRAM_TYPE_UNKNOWN";
        }
    }
}
