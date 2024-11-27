package com.android.server.accessibility;

/* loaded from: classes.dex */
class FlashNotificationsController {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String ACTION_FLASH_NOTIFICATION_START_PREVIEW = "com.android.internal.intent.action.FLASH_NOTIFICATION_START_PREVIEW";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String ACTION_FLASH_NOTIFICATION_STOP_PREVIEW = "com.android.internal.intent.action.FLASH_NOTIFICATION_STOP_PREVIEW";
    private static final boolean DEBUG = true;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String EXTRA_FLASH_NOTIFICATION_PREVIEW_COLOR = "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_COLOR";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String EXTRA_FLASH_NOTIFICATION_PREVIEW_TYPE = "com.android.internal.intent.extra.FLASH_NOTIFICATION_PREVIEW_TYPE";
    private static final java.lang.String LOG_TAG = "FlashNotifController";

    @com.android.internal.annotations.VisibleForTesting
    static final int PREVIEW_TYPE_LONG = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int PREVIEW_TYPE_SHORT = 0;
    private static final int SCREEN_DEFAULT_ALPHA = 1711276032;
    private static final int SCREEN_DEFAULT_COLOR = 16776960;
    private static final int SCREEN_DEFAULT_COLOR_WITH_ALPHA = 1728052992;
    private static final int SCREEN_FADE_DURATION_MS = 200;
    private static final int SCREEN_FADE_OUT_TIMEOUT_MS = 10;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String SETTING_KEY_CAMERA_FLASH_NOTIFICATION = "camera_flash_notification";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String SETTING_KEY_SCREEN_FLASH_NOTIFICATION = "screen_flash_notification";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR = "screen_flash_notification_color_global";
    private static final java.lang.String TAG_ALARM = "alarm";
    private static final java.lang.String TAG_PREVIEW = "preview";
    private static final int TYPE_DEFAULT = 1;
    private static final int TYPE_DEFAULT_OFF_MS = 250;
    private static final int TYPE_DEFAULT_ON_MS = 350;
    private static final int TYPE_DEFAULT_SCREEN_DELAY_MS = 300;
    private static final int TYPE_LONG_PREVIEW = 3;
    private static final int TYPE_LONG_PREVIEW_OFF_MS = 1000;
    private static final int TYPE_LONG_PREVIEW_ON_MS = 5000;
    private static final int TYPE_SEQUENCE = 2;
    private static final int TYPE_SEQUENCE_OFF_MS = 700;
    private static final int TYPE_SEQUENCE_ON_MS = 700;
    private static final java.lang.String WAKE_LOCK_TAG = "a11y:FlashNotificationsController";
    private static final long WAKE_LOCK_TIMEOUT_MS = 300000;
    private final android.media.AudioManager.AudioPlaybackCallback mAudioPlaybackCallback;
    private final android.os.Handler mCallbackHandler;
    private java.lang.String mCameraId;
    private android.hardware.camera2.CameraManager mCameraManager;
    private final android.content.Context mContext;
    private com.android.server.accessibility.FlashNotificationsController.FlashNotification mCurrentFlashNotification;
    private final android.hardware.display.DisplayManager mDisplayManager;
    private int mDisplayState;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.accessibility.FlashNotificationsController.FlashBroadcastReceiver mFlashBroadcastReceiver;
    private final android.os.Handler mFlashNotificationHandler;

    @com.android.internal.annotations.GuardedBy({"mFlashNotifications"})
    private final java.util.LinkedList<com.android.server.accessibility.FlashNotificationsController.FlashNotification> mFlashNotifications;
    private boolean mIsAlarming;
    private boolean mIsCameraFlashNotificationEnabled;
    private boolean mIsCameraOpened;
    private boolean mIsScreenFlashNotificationEnabled;
    private boolean mIsTorchOn;
    private boolean mIsTorchTouched;
    private final android.os.Handler mMainHandler;
    private android.view.View mScreenFlashNotificationOverlayView;
    private volatile com.android.server.accessibility.FlashNotificationsController.FlashNotificationThread mThread;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.camera2.CameraManager.AvailabilityCallback mTorchAvailabilityCallback;
    private final android.hardware.camera2.CameraManager.TorchCallback mTorchCallback;
    private final android.os.PowerManager.WakeLock mWakeLock;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface FlashNotificationType {
    }

    /* renamed from: com.android.server.accessibility.FlashNotificationsController$3, reason: invalid class name */
    class AnonymousClass3 extends android.media.AudioManager.AudioPlaybackCallback {
        AnonymousClass3() {
        }

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) {
            boolean z;
            if (list == null) {
                z = false;
            } else {
                z = list.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.accessibility.FlashNotificationsController$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$onPlaybackConfigChanged$0;
                        lambda$onPlaybackConfigChanged$0 = com.android.server.accessibility.FlashNotificationsController.AnonymousClass3.lambda$onPlaybackConfigChanged$0((android.media.AudioPlaybackConfiguration) obj);
                        return lambda$onPlaybackConfigChanged$0;
                    }
                });
            }
            if (com.android.server.accessibility.FlashNotificationsController.this.mIsAlarming != z) {
                android.util.Log.d(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "alarm state changed: " + z);
                if (z) {
                    com.android.server.accessibility.FlashNotificationsController.this.startFlashNotificationSequenceForAlarm();
                } else {
                    com.android.server.accessibility.FlashNotificationsController.this.stopFlashNotificationSequenceForAlarm();
                }
                com.android.server.accessibility.FlashNotificationsController.this.mIsAlarming = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onPlaybackConfigChanged$0(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
            return audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getAudioAttributes().getUsage() == 4;
        }
    }

    FlashNotificationsController(android.content.Context context) {
        this(context, getStartedHandler("FlashNotificationThread"), getStartedHandler(LOG_TAG));
    }

    @com.android.internal.annotations.VisibleForTesting
    FlashNotificationsController(android.content.Context context, android.os.Handler handler, android.os.Handler handler2) {
        this.mFlashNotifications = new java.util.LinkedList<>();
        this.mIsTorchTouched = false;
        this.mIsTorchOn = false;
        this.mIsCameraFlashNotificationEnabled = false;
        this.mIsScreenFlashNotificationEnabled = false;
        this.mIsAlarming = false;
        this.mDisplayState = 1;
        this.mIsCameraOpened = false;
        this.mCameraId = null;
        this.mTorchCallback = new android.hardware.camera2.CameraManager.TorchCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.1
            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public void onTorchModeChanged(java.lang.String str, boolean z) {
                if (com.android.server.accessibility.FlashNotificationsController.this.mCameraId != null && com.android.server.accessibility.FlashNotificationsController.this.mCameraId.equals(str)) {
                    com.android.server.accessibility.FlashNotificationsController.this.mIsTorchOn = z;
                    android.util.Log.d(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "onTorchModeChanged, set mIsTorchOn=" + z);
                }
            }
        };
        this.mTorchAvailabilityCallback = new android.hardware.camera2.CameraManager.AvailabilityCallback() { // from class: com.android.server.accessibility.FlashNotificationsController.2
            public void onCameraOpened(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
                if (com.android.server.accessibility.FlashNotificationsController.this.mCameraId != null && com.android.server.accessibility.FlashNotificationsController.this.mCameraId.equals(str)) {
                    com.android.server.accessibility.FlashNotificationsController.this.mIsCameraOpened = true;
                }
            }

            public void onCameraClosed(@android.annotation.NonNull java.lang.String str) {
                if (com.android.server.accessibility.FlashNotificationsController.this.mCameraId != null && com.android.server.accessibility.FlashNotificationsController.this.mCameraId.equals(str)) {
                    com.android.server.accessibility.FlashNotificationsController.this.mIsCameraOpened = false;
                }
            }
        };
        this.mAudioPlaybackCallback = new com.android.server.accessibility.FlashNotificationsController.AnonymousClass3();
        this.mContext = context;
        this.mMainHandler = new android.os.Handler(this.mContext.getMainLooper());
        this.mFlashNotificationHandler = handler;
        this.mCallbackHandler = handler2;
        new com.android.server.accessibility.FlashNotificationsController.FlashContentObserver(this.mMainHandler).register(this.mContext.getContentResolver());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction(ACTION_FLASH_NOTIFICATION_START_PREVIEW);
        intentFilter.addAction(ACTION_FLASH_NOTIFICATION_STOP_PREVIEW);
        this.mFlashBroadcastReceiver = new com.android.server.accessibility.FlashNotificationsController.FlashBroadcastReceiver();
        this.mContext.registerReceiver(this.mFlashBroadcastReceiver, intentFilter, 4);
        this.mWakeLock = ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).newWakeLock(1, WAKE_LOCK_TAG);
        this.mDisplayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        android.hardware.display.DisplayManager.DisplayListener displayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.server.accessibility.FlashNotificationsController.4
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                android.view.Display display;
                if (com.android.server.accessibility.FlashNotificationsController.this.mDisplayManager != null && (display = com.android.server.accessibility.FlashNotificationsController.this.mDisplayManager.getDisplay(i)) != null) {
                    com.android.server.accessibility.FlashNotificationsController.this.mDisplayState = display.getState();
                }
            }
        };
        if (this.mDisplayManager != null) {
            this.mDisplayManager.registerDisplayListener(displayListener, null);
        }
    }

    private static android.os.Handler getStartedHandler(java.lang.String str) {
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(str);
        handlerThread.start();
        return handlerThread.getThreadHandler();
    }

    boolean startFlashNotificationSequence(final java.lang.String str, int i, android.os.IBinder iBinder) {
        com.android.server.accessibility.FlashNotificationsController.FlashNotification flashNotification = new com.android.server.accessibility.FlashNotificationsController.FlashNotification(str, 2, getScreenFlashColorPreference(i), iBinder, new android.os.IBinder.DeathRecipient() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda2
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.accessibility.FlashNotificationsController.this.lambda$startFlashNotificationSequence$0(str);
            }
        });
        if (!flashNotification.tryLinkToDeath()) {
            return false;
        }
        requestStartFlashNotification(flashNotification);
        return true;
    }

    boolean stopFlashNotificationSequence(java.lang.String str) {
        lambda$startFlashNotificationSequence$0(str);
        return true;
    }

    boolean startFlashNotificationEvent(java.lang.String str, int i, java.lang.String str2) {
        requestStartFlashNotification(new com.android.server.accessibility.FlashNotificationsController.FlashNotification(str, 1, getScreenFlashColorPreference(i, str2)));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFlashNotificationShortPreview() {
        requestStartFlashNotification(new com.android.server.accessibility.FlashNotificationsController.FlashNotification(TAG_PREVIEW, 1, getScreenFlashColorPreference(4)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFlashNotificationLongPreview(int i) {
        requestStartFlashNotification(new com.android.server.accessibility.FlashNotificationsController.FlashNotification(TAG_PREVIEW, 3, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopFlashNotificationLongPreview() {
        lambda$startFlashNotificationSequence$0(TAG_PREVIEW);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFlashNotificationSequenceForAlarm() {
        requestStartFlashNotification(new com.android.server.accessibility.FlashNotificationsController.FlashNotification("alarm", 2, getScreenFlashColorPreference(2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopFlashNotificationSequenceForAlarm() {
        lambda$startFlashNotificationSequence$0("alarm");
    }

    private void requestStartFlashNotification(com.android.server.accessibility.FlashNotificationsController.FlashNotification flashNotification) {
        android.util.Log.d(LOG_TAG, "requestStartFlashNotification");
        boolean isEnabled = android.util.FeatureFlagUtils.isEnabled(this.mContext, "settings_flash_notifications");
        boolean z = false;
        this.mIsCameraFlashNotificationEnabled = isEnabled && android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
        if (isEnabled && android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_SCREEN_FLASH_NOTIFICATION, 0, -2) != 0) {
            z = true;
        }
        this.mIsScreenFlashNotificationEnabled = z;
        if (flashNotification.mType == 1 && this.mIsScreenFlashNotificationEnabled) {
            this.mMainHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.accessibility.FlashNotificationsController) obj).startFlashNotification((com.android.server.accessibility.FlashNotificationsController.FlashNotification) obj2);
                }
            }, this, flashNotification), 300L);
            android.util.Log.i(LOG_TAG, "give some delay for flash notification");
        } else {
            startFlashNotification(flashNotification);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: stopFlashNotification, reason: merged with bridge method [inline-methods] */
    public void lambda$startFlashNotificationSequence$0(java.lang.String str) {
        android.util.Log.i(LOG_TAG, "stopFlashNotification: tag=" + str);
        synchronized (this.mFlashNotifications) {
            try {
                com.android.server.accessibility.FlashNotificationsController.FlashNotification removeFlashNotificationLocked = removeFlashNotificationLocked(str);
                if (this.mCurrentFlashNotification != null && removeFlashNotificationLocked == this.mCurrentFlashNotification) {
                    stopFlashNotificationLocked();
                    startNextFlashNotificationLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareForCameraFlashNotification() {
        this.mCameraManager = (android.hardware.camera2.CameraManager) this.mContext.getSystemService(android.hardware.camera2.CameraManager.class);
        if (this.mCameraManager != null) {
            try {
                this.mCameraId = getCameraId();
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(LOG_TAG, "CameraAccessException", e);
            }
            this.mCameraManager.registerTorchCallback(this.mTorchCallback, (android.os.Handler) null);
        }
    }

    private java.lang.String getCameraId() throws android.hardware.camera2.CameraAccessException {
        for (java.lang.String str : this.mCameraManager.getCameraIdList()) {
            android.hardware.camera2.CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
            java.lang.Boolean bool = (java.lang.Boolean) cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE);
            java.lang.Integer num = (java.lang.Integer) cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING);
            if (bool != null && num != null && bool.booleanValue() && num.intValue() == 1) {
                android.util.Log.d(LOG_TAG, "Found valid camera, cameraId=" + str);
                return str;
            }
        }
        return null;
    }

    private void showScreenNotificationOverlayView(int i) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.FlashNotificationsController) obj).showScreenNotificationOverlayViewMainThread(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    private void hideScreenNotificationOverlayView() {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.accessibility.FlashNotificationsController) obj).fadeOutScreenNotificationOverlayViewMainThread();
            }
        }, this));
        this.mMainHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.accessibility.FlashNotificationsController) obj).hideScreenNotificationOverlayViewMainThread();
            }
        }, this), 210L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showScreenNotificationOverlayViewMainThread(int i) {
        android.util.Log.d(LOG_TAG, "showScreenNotificationOverlayViewMainThread");
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams(-1, -1, 2015, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_LOCATION_PROVIDER, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.inputFeatures |= 1;
        if (this.mScreenFlashNotificationOverlayView == null) {
            this.mScreenFlashNotificationOverlayView = getScreenNotificationOverlayView(i);
            ((android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class)).addView(this.mScreenFlashNotificationOverlayView, layoutParams);
            fadeScreenNotificationOverlayViewMainThread(this.mScreenFlashNotificationOverlayView, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeOutScreenNotificationOverlayViewMainThread() {
        android.util.Log.d(LOG_TAG, "fadeOutScreenNotificationOverlayViewMainThread");
        if (this.mScreenFlashNotificationOverlayView != null) {
            fadeScreenNotificationOverlayViewMainThread(this.mScreenFlashNotificationOverlayView, false);
        }
    }

    private void fadeScreenNotificationOverlayViewMainThread(android.view.View view, boolean z) {
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(view, "alpha", z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
        ofFloat.setInterpolator(new android.view.animation.AccelerateInterpolator());
        ofFloat.setAutoCancel(true);
        ofFloat.setDuration(200L);
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideScreenNotificationOverlayViewMainThread() {
        android.util.Log.d(LOG_TAG, "hideScreenNotificationOverlayViewMainThread");
        if (this.mScreenFlashNotificationOverlayView != null) {
            this.mScreenFlashNotificationOverlayView.setVisibility(8);
            ((android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class)).removeView(this.mScreenFlashNotificationOverlayView);
            this.mScreenFlashNotificationOverlayView = null;
        }
    }

    private android.view.View getScreenNotificationOverlayView(int i) {
        android.widget.FrameLayout frameLayout = new android.widget.FrameLayout(this.mContext);
        frameLayout.setBackgroundColor(i);
        frameLayout.setAlpha(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        return frameLayout;
    }

    private int getScreenFlashColorPreference(int i, java.lang.String str) {
        return getScreenFlashColorPreference();
    }

    private int getScreenFlashColorPreference(int i) {
        return getScreenFlashColorPreference();
    }

    private int getScreenFlashColorPreference() {
        return android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEY_SCREEN_FLASH_NOTIFICATION_COLOR, SCREEN_DEFAULT_COLOR_WITH_ALPHA, -2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startFlashNotification(@android.annotation.NonNull com.android.server.accessibility.FlashNotificationsController.FlashNotification flashNotification) {
        int i = flashNotification.mType;
        java.lang.String str = flashNotification.mTag;
        android.util.Log.i(LOG_TAG, "startFlashNotification: type=" + i + ", tag=" + str);
        if (!this.mIsCameraFlashNotificationEnabled && !this.mIsScreenFlashNotificationEnabled && !flashNotification.mForceStartScreenFlash) {
            android.util.Log.d(LOG_TAG, "Flash notification is disabled");
            return;
        }
        if (this.mIsCameraOpened) {
            android.util.Log.d(LOG_TAG, "Since camera for torch is opened, block notification.");
            return;
        }
        if (this.mIsCameraFlashNotificationEnabled && this.mCameraId == null) {
            prepareForCameraFlashNotification();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mFlashNotifications) {
                try {
                    if (i == 1 || i == 3) {
                        if (this.mCurrentFlashNotification != null) {
                            android.util.Log.i(LOG_TAG, "Default type of flash notification can not work because previous flash notification is working");
                        } else {
                            startFlashNotificationLocked(flashNotification);
                        }
                    } else if (i == 2) {
                        if (this.mCurrentFlashNotification != null) {
                            removeFlashNotificationLocked(str);
                            stopFlashNotificationLocked();
                        }
                        this.mFlashNotifications.addFirst(flashNotification);
                        startNextFlashNotificationLocked();
                    } else {
                        android.util.Log.e(LOG_TAG, "Unavailable flash notification type");
                    }
                } finally {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mFlashNotifications"})
    private com.android.server.accessibility.FlashNotificationsController.FlashNotification removeFlashNotificationLocked(java.lang.String str) {
        java.util.ListIterator<com.android.server.accessibility.FlashNotificationsController.FlashNotification> listIterator = this.mFlashNotifications.listIterator(0);
        while (listIterator.hasNext()) {
            com.android.server.accessibility.FlashNotificationsController.FlashNotification next = listIterator.next();
            if (next != null && next.mTag.equals(str)) {
                listIterator.remove();
                next.tryUnlinkToDeath();
                android.util.Log.i(LOG_TAG, "removeFlashNotificationLocked: tag=" + next.mTag);
                return next;
            }
        }
        if (this.mCurrentFlashNotification != null && this.mCurrentFlashNotification.mTag.equals(str)) {
            this.mCurrentFlashNotification.tryUnlinkToDeath();
            return this.mCurrentFlashNotification;
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mFlashNotifications"})
    private void stopFlashNotificationLocked() {
        if (this.mThread != null) {
            android.util.Log.i(LOG_TAG, "stopFlashNotificationLocked: tag=" + this.mThread.mFlashNotification.mTag);
            this.mThread.cancel();
            this.mThread = null;
        }
        doCameraFlashNotificationOff();
        doScreenFlashNotificationOff();
    }

    @com.android.internal.annotations.GuardedBy({"mFlashNotifications"})
    private void startNextFlashNotificationLocked() {
        android.util.Log.i(LOG_TAG, "startNextFlashNotificationLocked");
        if (this.mFlashNotifications.size() <= 0) {
            this.mCurrentFlashNotification = null;
        } else {
            startFlashNotificationLocked(this.mFlashNotifications.getFirst());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mFlashNotifications"})
    private void startFlashNotificationLocked(@android.annotation.NonNull com.android.server.accessibility.FlashNotificationsController.FlashNotification flashNotification) {
        android.util.Log.i(LOG_TAG, "startFlashNotificationLocked: type=" + flashNotification.mType + ", tag=" + flashNotification.mTag);
        this.mCurrentFlashNotification = flashNotification;
        this.mThread = new com.android.server.accessibility.FlashNotificationsController.FlashNotificationThread(flashNotification);
        this.mFlashNotificationHandler.post(this.mThread);
    }

    private boolean isDozeMode() {
        return this.mDisplayState == 3 || this.mDisplayState == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCameraFlashNotificationOn() {
        if (this.mIsCameraFlashNotificationEnabled && !this.mIsTorchOn) {
            doCameraFlashNotification(true);
        }
        android.util.Log.i(LOG_TAG, "doCameraFlashNotificationOn: isCameraFlashNotificationEnabled=" + this.mIsCameraFlashNotificationEnabled + ", isTorchOn=" + this.mIsTorchOn + ", isTorchTouched=" + this.mIsTorchTouched);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCameraFlashNotificationOff() {
        if (this.mIsTorchTouched) {
            doCameraFlashNotification(false);
        }
        android.util.Log.i(LOG_TAG, "doCameraFlashNotificationOff: isCameraFlashNotificationEnabled=" + this.mIsCameraFlashNotificationEnabled + ", isTorchOn=" + this.mIsTorchOn + ", isTorchTouched=" + this.mIsTorchTouched);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doScreenFlashNotificationOn(int i, boolean z) {
        boolean isDozeMode = isDozeMode();
        if ((this.mIsScreenFlashNotificationEnabled || z) && !isDozeMode) {
            showScreenNotificationOverlayView(i);
        }
        android.util.Log.i(LOG_TAG, "doScreenFlashNotificationOn: isScreenFlashNotificationEnabled=" + this.mIsScreenFlashNotificationEnabled + ", isDozeMode=" + isDozeMode + ", color=" + java.lang.Integer.toHexString(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doScreenFlashNotificationOff() {
        hideScreenNotificationOverlayView();
        android.util.Log.i(LOG_TAG, "doScreenFlashNotificationOff: isScreenFlashNotificationEnabled=" + this.mIsScreenFlashNotificationEnabled);
    }

    private void doCameraFlashNotification(boolean z) {
        android.util.Log.d(LOG_TAG, "doCameraFlashNotification: " + z + " mCameraId : " + this.mCameraId);
        if (this.mCameraManager != null && this.mCameraId != null) {
            try {
                this.mCameraManager.setTorchMode(this.mCameraId, z);
                this.mIsTorchTouched = z;
                return;
            } catch (android.hardware.camera2.CameraAccessException e) {
                android.util.Log.e(LOG_TAG, "Failed to setTorchMode: " + e);
                return;
            }
        }
        android.util.Log.e(LOG_TAG, "Can not use camera flash notification, please check CameraManager!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FlashNotification {
        private final int mColor;

        @android.annotation.Nullable
        private final android.os.IBinder.DeathRecipient mDeathRecipient;
        private final boolean mForceStartScreenFlash;
        private final int mOffDuration;
        private final int mOnDuration;
        private int mRepeat;
        private final java.lang.String mTag;

        @android.annotation.Nullable
        private final android.os.IBinder mToken;
        private final int mType;

        private FlashNotification(java.lang.String str, int i, int i2) {
            this(str, i, i2, null, null);
        }

        private FlashNotification(java.lang.String str, int i, int i2, android.os.IBinder iBinder, android.os.IBinder.DeathRecipient deathRecipient) {
            this.mType = i;
            this.mTag = str;
            this.mColor = i2;
            this.mToken = iBinder;
            this.mDeathRecipient = deathRecipient;
            switch (i) {
                case 2:
                    this.mOnDuration = com.android.server.am.ProcessList.PREVIOUS_APP_ADJ;
                    this.mOffDuration = com.android.server.am.ProcessList.PREVIOUS_APP_ADJ;
                    this.mRepeat = 0;
                    this.mForceStartScreenFlash = false;
                    break;
                case 3:
                    this.mOnDuration = 5000;
                    this.mOffDuration = 1000;
                    this.mRepeat = 1;
                    this.mForceStartScreenFlash = true;
                    break;
                default:
                    this.mOnDuration = 350;
                    this.mOffDuration = 250;
                    this.mRepeat = 2;
                    this.mForceStartScreenFlash = false;
                    break;
            }
        }

        boolean tryLinkToDeath() {
            if (this.mToken == null || this.mDeathRecipient == null) {
                return false;
            }
            try {
                this.mToken.linkToDeath(this.mDeathRecipient, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "RemoteException", e);
                return false;
            }
        }

        boolean tryUnlinkToDeath() {
            if (this.mToken == null || this.mDeathRecipient == null) {
                return false;
            }
            try {
                this.mToken.unlinkToDeath(this.mDeathRecipient, 0);
                return true;
            } catch (java.lang.Exception e) {
                return false;
            }
        }
    }

    private class FlashNotificationThread extends java.lang.Thread {
        private int mColor;
        private final com.android.server.accessibility.FlashNotificationsController.FlashNotification mFlashNotification;
        private boolean mForceStop;
        private boolean mShouldDoCameraFlash;
        private boolean mShouldDoScreenFlash;

        private FlashNotificationThread(@android.annotation.NonNull com.android.server.accessibility.FlashNotificationsController.FlashNotification flashNotification) {
            this.mColor = 0;
            this.mShouldDoScreenFlash = false;
            this.mShouldDoCameraFlash = false;
            this.mFlashNotification = flashNotification;
            this.mForceStop = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.util.Log.d(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "run started: " + this.mFlashNotification.mTag);
            android.os.Process.setThreadPriority(-8);
            this.mColor = this.mFlashNotification.mColor;
            this.mShouldDoScreenFlash = android.graphics.Color.alpha(this.mColor) != 0 || this.mFlashNotification.mForceStartScreenFlash;
            this.mShouldDoCameraFlash = this.mFlashNotification.mType != 3;
            synchronized (this) {
                com.android.server.accessibility.FlashNotificationsController.this.mWakeLock.acquire(300000L);
                try {
                    startFlashNotification();
                } finally {
                    com.android.server.accessibility.FlashNotificationsController.this.doScreenFlashNotificationOff();
                    com.android.server.accessibility.FlashNotificationsController.this.doCameraFlashNotificationOff();
                    try {
                        com.android.server.accessibility.FlashNotificationsController.this.mWakeLock.release();
                    } catch (java.lang.RuntimeException e) {
                        android.util.Log.e(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "Error while releasing FlashNotificationsController wakelock (already released by the system?)");
                    }
                }
            }
            synchronized (com.android.server.accessibility.FlashNotificationsController.this.mFlashNotifications) {
                try {
                    if (com.android.server.accessibility.FlashNotificationsController.this.mThread == this) {
                        com.android.server.accessibility.FlashNotificationsController.this.mThread = null;
                    }
                    if (!this.mForceStop) {
                        this.mFlashNotification.tryUnlinkToDeath();
                        com.android.server.accessibility.FlashNotificationsController.this.mCurrentFlashNotification = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            android.util.Log.d(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "run finished: " + this.mFlashNotification.mTag);
        }

        private void startFlashNotification() {
            synchronized (this) {
                while (!this.mForceStop) {
                    try {
                        if (this.mFlashNotification.mType != 2 && this.mFlashNotification.mRepeat >= 0) {
                            com.android.server.accessibility.FlashNotificationsController.FlashNotification flashNotification = this.mFlashNotification;
                            int i = flashNotification.mRepeat;
                            flashNotification.mRepeat = i - 1;
                            if (i == 0) {
                            }
                        }
                        if (this.mShouldDoScreenFlash) {
                            com.android.server.accessibility.FlashNotificationsController.this.doScreenFlashNotificationOn(this.mColor, this.mFlashNotification.mForceStartScreenFlash);
                        }
                        if (this.mShouldDoCameraFlash) {
                            com.android.server.accessibility.FlashNotificationsController.this.doCameraFlashNotificationOn();
                        }
                        delay(this.mFlashNotification.mOnDuration);
                        com.android.server.accessibility.FlashNotificationsController.this.doScreenFlashNotificationOff();
                        com.android.server.accessibility.FlashNotificationsController.this.doCameraFlashNotificationOff();
                        if (this.mForceStop) {
                            break;
                        } else {
                            delay(this.mFlashNotification.mOffDuration);
                        }
                    } finally {
                    }
                }
            }
        }

        void cancel() {
            android.util.Log.d(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "run canceled: " + this.mFlashNotification.mTag);
            synchronized (this) {
                com.android.server.accessibility.FlashNotificationsController.this.mThread.mForceStop = true;
                com.android.server.accessibility.FlashNotificationsController.this.mThread.notify();
            }
        }

        private void delay(long j) {
            if (j > 0) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis() + j;
                do {
                    try {
                        wait(j);
                    } catch (java.lang.InterruptedException e) {
                    }
                    if (!this.mForceStop) {
                        j = uptimeMillis - android.os.SystemClock.uptimeMillis();
                    } else {
                        return;
                    }
                } while (j > 0);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class FlashBroadcastReceiver extends android.content.BroadcastReceiver {
        FlashBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                if (android.os.UserHandle.myUserId() != android.app.ActivityManager.getCurrentUser()) {
                    return;
                }
                com.android.server.accessibility.FlashNotificationsController.this.mIsCameraFlashNotificationEnabled = android.provider.Settings.System.getIntForUser(com.android.server.accessibility.FlashNotificationsController.this.mContext.getContentResolver(), com.android.server.accessibility.FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
                if (com.android.server.accessibility.FlashNotificationsController.this.mIsCameraFlashNotificationEnabled) {
                    com.android.server.accessibility.FlashNotificationsController.this.prepareForCameraFlashNotification();
                } else if (com.android.server.accessibility.FlashNotificationsController.this.mCameraManager != null) {
                    com.android.server.accessibility.FlashNotificationsController.this.mCameraManager.unregisterTorchCallback(com.android.server.accessibility.FlashNotificationsController.this.mTorchCallback);
                }
                android.media.AudioManager audioManager = (android.media.AudioManager) com.android.server.accessibility.FlashNotificationsController.this.mContext.getSystemService(android.media.AudioManager.class);
                if (audioManager != null) {
                    audioManager.registerAudioPlaybackCallback(com.android.server.accessibility.FlashNotificationsController.this.mAudioPlaybackCallback, com.android.server.accessibility.FlashNotificationsController.this.mCallbackHandler);
                }
                com.android.server.accessibility.FlashNotificationsController.this.mCameraManager = (android.hardware.camera2.CameraManager) com.android.server.accessibility.FlashNotificationsController.this.mContext.getSystemService(android.hardware.camera2.CameraManager.class);
                com.android.server.accessibility.FlashNotificationsController.this.mCameraManager.registerAvailabilityCallback(com.android.server.accessibility.FlashNotificationsController.this.mTorchAvailabilityCallback, com.android.server.accessibility.FlashNotificationsController.this.mCallbackHandler);
                return;
            }
            if (!com.android.server.accessibility.FlashNotificationsController.ACTION_FLASH_NOTIFICATION_START_PREVIEW.equals(intent.getAction())) {
                if (com.android.server.accessibility.FlashNotificationsController.ACTION_FLASH_NOTIFICATION_STOP_PREVIEW.equals(intent.getAction())) {
                    android.util.Log.i(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "ACTION_FLASH_NOTIFICATION_STOP_PREVIEW");
                    com.android.server.accessibility.FlashNotificationsController.this.stopFlashNotificationLongPreview();
                    return;
                }
                return;
            }
            android.util.Log.i(com.android.server.accessibility.FlashNotificationsController.LOG_TAG, "ACTION_FLASH_NOTIFICATION_START_PREVIEW");
            int intExtra = intent.getIntExtra(com.android.server.accessibility.FlashNotificationsController.EXTRA_FLASH_NOTIFICATION_PREVIEW_COLOR, 0);
            int intExtra2 = intent.getIntExtra(com.android.server.accessibility.FlashNotificationsController.EXTRA_FLASH_NOTIFICATION_PREVIEW_TYPE, 0);
            if (intExtra2 == 1) {
                com.android.server.accessibility.FlashNotificationsController.this.startFlashNotificationLongPreview(intExtra);
            } else if (intExtra2 == 0) {
                com.android.server.accessibility.FlashNotificationsController.this.startFlashNotificationShortPreview();
            }
        }
    }

    private final class FlashContentObserver extends android.database.ContentObserver {
        private final android.net.Uri mCameraFlashNotificationUri;
        private final android.net.Uri mScreenFlashNotificationUri;

        FlashContentObserver(android.os.Handler handler) {
            super(handler);
            this.mCameraFlashNotificationUri = android.provider.Settings.System.getUriFor(com.android.server.accessibility.FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION);
            this.mScreenFlashNotificationUri = android.provider.Settings.System.getUriFor(com.android.server.accessibility.FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION);
        }

        void register(android.content.ContentResolver contentResolver) {
            contentResolver.registerContentObserver(this.mCameraFlashNotificationUri, false, this, -1);
            contentResolver.registerContentObserver(this.mScreenFlashNotificationUri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (this.mCameraFlashNotificationUri.equals(uri)) {
                com.android.server.accessibility.FlashNotificationsController.this.mIsCameraFlashNotificationEnabled = android.provider.Settings.System.getIntForUser(com.android.server.accessibility.FlashNotificationsController.this.mContext.getContentResolver(), com.android.server.accessibility.FlashNotificationsController.SETTING_KEY_CAMERA_FLASH_NOTIFICATION, 0, -2) != 0;
                if (com.android.server.accessibility.FlashNotificationsController.this.mIsCameraFlashNotificationEnabled) {
                    com.android.server.accessibility.FlashNotificationsController.this.prepareForCameraFlashNotification();
                    return;
                }
                com.android.server.accessibility.FlashNotificationsController.this.mIsTorchOn = false;
                if (com.android.server.accessibility.FlashNotificationsController.this.mCameraManager != null) {
                    com.android.server.accessibility.FlashNotificationsController.this.mCameraManager.unregisterTorchCallback(com.android.server.accessibility.FlashNotificationsController.this.mTorchCallback);
                    return;
                }
                return;
            }
            if (this.mScreenFlashNotificationUri.equals(uri)) {
                com.android.server.accessibility.FlashNotificationsController.this.mIsScreenFlashNotificationEnabled = android.provider.Settings.System.getIntForUser(com.android.server.accessibility.FlashNotificationsController.this.mContext.getContentResolver(), com.android.server.accessibility.FlashNotificationsController.SETTING_KEY_SCREEN_FLASH_NOTIFICATION, 0, -2) != 0;
            }
        }
    }
}
