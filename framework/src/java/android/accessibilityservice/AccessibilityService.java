package android.accessibilityservice;

/* loaded from: classes.dex */
public abstract class AccessibilityService extends android.app.Service {
    public static final int ACCESSIBILITY_TAKE_SCREENSHOT_REQUEST_INTERVAL_TIMES_MS = 333;
    public static final int ERROR_TAKE_SCREENSHOT_INTERNAL_ERROR = 1;
    public static final int ERROR_TAKE_SCREENSHOT_INTERVAL_TIME_SHORT = 3;
    public static final int ERROR_TAKE_SCREENSHOT_INVALID_DISPLAY = 4;
    public static final int ERROR_TAKE_SCREENSHOT_INVALID_WINDOW = 5;
    public static final int ERROR_TAKE_SCREENSHOT_NO_ACCESSIBILITY_ACCESS = 2;
    public static final int ERROR_TAKE_SCREENSHOT_SECURE_WINDOW = 6;
    public static final int GESTURE_2_FINGER_DOUBLE_TAP = 20;
    public static final int GESTURE_2_FINGER_DOUBLE_TAP_AND_HOLD = 40;
    public static final int GESTURE_2_FINGER_SINGLE_TAP = 19;
    public static final int GESTURE_2_FINGER_SWIPE_DOWN = 26;
    public static final int GESTURE_2_FINGER_SWIPE_LEFT = 27;
    public static final int GESTURE_2_FINGER_SWIPE_RIGHT = 28;
    public static final int GESTURE_2_FINGER_SWIPE_UP = 25;
    public static final int GESTURE_2_FINGER_TRIPLE_TAP = 21;
    public static final int GESTURE_2_FINGER_TRIPLE_TAP_AND_HOLD = 43;
    public static final int GESTURE_3_FINGER_DOUBLE_TAP = 23;
    public static final int GESTURE_3_FINGER_DOUBLE_TAP_AND_HOLD = 41;
    public static final int GESTURE_3_FINGER_SINGLE_TAP = 22;
    public static final int GESTURE_3_FINGER_SINGLE_TAP_AND_HOLD = 44;
    public static final int GESTURE_3_FINGER_SWIPE_DOWN = 30;
    public static final int GESTURE_3_FINGER_SWIPE_LEFT = 31;
    public static final int GESTURE_3_FINGER_SWIPE_RIGHT = 32;
    public static final int GESTURE_3_FINGER_SWIPE_UP = 29;
    public static final int GESTURE_3_FINGER_TRIPLE_TAP = 24;
    public static final int GESTURE_3_FINGER_TRIPLE_TAP_AND_HOLD = 45;
    public static final int GESTURE_4_FINGER_DOUBLE_TAP = 38;
    public static final int GESTURE_4_FINGER_DOUBLE_TAP_AND_HOLD = 42;
    public static final int GESTURE_4_FINGER_SINGLE_TAP = 37;
    public static final int GESTURE_4_FINGER_SWIPE_DOWN = 34;
    public static final int GESTURE_4_FINGER_SWIPE_LEFT = 35;
    public static final int GESTURE_4_FINGER_SWIPE_RIGHT = 36;
    public static final int GESTURE_4_FINGER_SWIPE_UP = 33;
    public static final int GESTURE_4_FINGER_TRIPLE_TAP = 39;
    public static final int GESTURE_DOUBLE_TAP = 17;
    public static final int GESTURE_DOUBLE_TAP_AND_HOLD = 18;
    public static final int GESTURE_PASSTHROUGH = -1;
    public static final int GESTURE_SWIPE_DOWN = 2;
    public static final int GESTURE_SWIPE_DOWN_AND_LEFT = 15;
    public static final int GESTURE_SWIPE_DOWN_AND_RIGHT = 16;
    public static final int GESTURE_SWIPE_DOWN_AND_UP = 8;
    public static final int GESTURE_SWIPE_LEFT = 3;
    public static final int GESTURE_SWIPE_LEFT_AND_DOWN = 10;
    public static final int GESTURE_SWIPE_LEFT_AND_RIGHT = 5;
    public static final int GESTURE_SWIPE_LEFT_AND_UP = 9;
    public static final int GESTURE_SWIPE_RIGHT = 4;
    public static final int GESTURE_SWIPE_RIGHT_AND_DOWN = 12;
    public static final int GESTURE_SWIPE_RIGHT_AND_LEFT = 6;
    public static final int GESTURE_SWIPE_RIGHT_AND_UP = 11;
    public static final int GESTURE_SWIPE_UP = 1;
    public static final int GESTURE_SWIPE_UP_AND_DOWN = 7;
    public static final int GESTURE_SWIPE_UP_AND_LEFT = 13;
    public static final int GESTURE_SWIPE_UP_AND_RIGHT = 14;
    public static final int GESTURE_TOUCH_EXPLORATION = -2;
    public static final int GESTURE_UNKNOWN = 0;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_ALL_APPS = 14;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_BUTTON = 11;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_BUTTON_CHOOSER = 12;
    public static final int GLOBAL_ACTION_ACCESSIBILITY_SHORTCUT = 13;
    public static final int GLOBAL_ACTION_BACK = 1;
    public static final int GLOBAL_ACTION_DISMISS_NOTIFICATION_SHADE = 15;
    public static final int GLOBAL_ACTION_DPAD_CENTER = 20;
    public static final int GLOBAL_ACTION_DPAD_DOWN = 17;
    public static final int GLOBAL_ACTION_DPAD_LEFT = 18;
    public static final int GLOBAL_ACTION_DPAD_RIGHT = 19;
    public static final int GLOBAL_ACTION_DPAD_UP = 16;
    public static final int GLOBAL_ACTION_HOME = 2;
    public static final int GLOBAL_ACTION_KEYCODE_HEADSETHOOK = 10;
    public static final int GLOBAL_ACTION_LOCK_SCREEN = 8;
    public static final int GLOBAL_ACTION_NOTIFICATIONS = 4;
    public static final int GLOBAL_ACTION_POWER_DIALOG = 6;
    public static final int GLOBAL_ACTION_QUICK_SETTINGS = 5;
    public static final int GLOBAL_ACTION_RECENTS = 3;
    public static final int GLOBAL_ACTION_TAKE_SCREENSHOT = 9;
    public static final int GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN = 7;
    public static final java.lang.String KEY_ACCESSIBILITY_SCREENSHOT_COLORSPACE = "screenshot_colorSpace";
    public static final java.lang.String KEY_ACCESSIBILITY_SCREENSHOT_HARDWAREBUFFER = "screenshot_hardwareBuffer";
    public static final java.lang.String KEY_ACCESSIBILITY_SCREENSHOT_STATUS = "screenshot_status";
    public static final java.lang.String KEY_ACCESSIBILITY_SCREENSHOT_TIMESTAMP = "screenshot_timestamp";
    private static final java.lang.String LOG_TAG = "AccessibilityService";
    public static final int OVERLAY_RESULT_INTERNAL_ERROR = 1;
    public static final int OVERLAY_RESULT_INVALID = 2;
    public static final int OVERLAY_RESULT_SUCCESS = 0;
    public static final java.lang.String SERVICE_INTERFACE = "android.accessibilityservice.AccessibilityService";
    public static final java.lang.String SERVICE_META_DATA = "android.accessibilityservice";
    public static final int SHOW_MODE_AUTO = 0;
    public static final int SHOW_MODE_HARD_KEYBOARD_ORIGINAL_VALUE = 536870912;
    public static final int SHOW_MODE_HARD_KEYBOARD_OVERRIDDEN = 1073741824;
    public static final int SHOW_MODE_HIDDEN = 1;
    public static final int SHOW_MODE_IGNORE_HARD_KEYBOARD = 2;
    public static final int SHOW_MODE_MASK = 3;
    public static final int TAKE_SCREENSHOT_SUCCESS = 0;
    private android.accessibilityservice.BrailleDisplayController mBrailleDisplayController;
    private android.accessibilityservice.FingerprintGestureController mFingerprintGestureController;
    private android.util.SparseArray<android.accessibilityservice.AccessibilityService.GestureResultCallbackInfo> mGestureStatusCallbackInfos;
    private int mGestureStatusCallbackSequence;
    private android.accessibilityservice.AccessibilityServiceInfo mInfo;
    private android.accessibilityservice.InputMethod mInputMethod;
    private int mMotionEventSources;
    private android.accessibilityservice.AccessibilityService.SoftKeyboardController mSoftKeyboardController;
    private android.view.WindowManager mWindowManager;
    private android.os.IBinder mWindowToken;
    private int mConnectionId = -1;
    private final android.util.SparseArray<android.accessibilityservice.AccessibilityService.MagnificationController> mMagnificationControllers = new android.util.SparseArray<>(0);
    private final android.util.SparseArray<android.accessibilityservice.TouchInteractionController> mTouchInteractionControllers = new android.util.SparseArray<>(0);
    private boolean mInputMethodInitialized = false;
    private final android.util.SparseArray<android.accessibilityservice.AccessibilityButtonController> mAccessibilityButtonControllers = new android.util.SparseArray<>(0);
    private final java.lang.Object mLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttachOverlayResult {
    }

    public interface Callbacks {
        void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback);

        void init(int i, android.os.IBinder iBinder);

        void onAccessibilityButtonAvailabilityChanged(boolean z);

        void onAccessibilityButtonClicked(int i);

        void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        void onFingerprintCapturingGesturesChanged(boolean z);

        void onFingerprintGesture(int i);

        boolean onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent);

        void onInterrupt();

        boolean onKeyEvent(android.view.KeyEvent keyEvent);

        void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig);

        void onMotionEvent(android.view.MotionEvent motionEvent);

        void onPerformGestureResult(int i, boolean z);

        void onServiceConnected();

        void onSoftKeyboardShowModeChanged(int i);

        void onSystemActionsChanged();

        void onTouchStateChanged(int i, int i2);

        void startInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScreenshotErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SoftKeyboardShowMode {
    }

    public interface TakeScreenshotCallback {
        void onFailure(int i);

        void onSuccess(android.accessibilityservice.AccessibilityService.ScreenshotResult screenshotResult);
    }

    public abstract void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

    public abstract void onInterrupt();

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchServiceConnected() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mMagnificationControllers.size(); i++) {
                this.mMagnificationControllers.valueAt(i).onServiceConnectedLocked();
            }
            android.accessibilityservice.AccessibilityServiceInfo serviceInfo = getServiceInfo();
            if (serviceInfo != null) {
                updateInputMethod(serviceInfo);
                this.mMotionEventSources = serviceInfo.getMotionEventSources();
            }
        }
        if (this.mSoftKeyboardController != null) {
            this.mSoftKeyboardController.onServiceConnected();
        }
        onServiceConnected();
    }

    private void updateInputMethod(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        if (accessibilityServiceInfo != null) {
            boolean z = (accessibilityServiceInfo.flags & 32768) != 0;
            if (z && !this.mInputMethodInitialized) {
                this.mInputMethod = onCreateInputMethod();
                this.mInputMethodInitialized = true;
            } else if ((!z) & this.mInputMethodInitialized) {
                this.mInputMethod = null;
                this.mInputMethodInitialized = false;
            }
        }
    }

    protected void onServiceConnected() {
    }

    @java.lang.Deprecated
    protected boolean onGesture(int i) {
        return false;
    }

    public boolean onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        if (accessibilityGestureEvent.getDisplayId() == 0) {
            onGesture(accessibilityGestureEvent.getGestureId());
            return false;
        }
        return false;
    }

    protected boolean onKeyEvent(android.view.KeyEvent keyEvent) {
        return false;
    }

    public void onMotionEvent(android.view.MotionEvent motionEvent) {
    }

    public java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindows() {
        return android.view.accessibility.AccessibilityInteractionClient.getInstance(this).getWindows(this.mConnectionId);
    }

    public final android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        return android.view.accessibility.AccessibilityInteractionClient.getInstance(this).getWindowsOnAllDisplays(this.mConnectionId);
    }

    public android.view.accessibility.AccessibilityNodeInfo getRootInActiveWindow() {
        return getRootInActiveWindow(4);
    }

    public android.view.accessibility.AccessibilityNodeInfo getRootInActiveWindow(int i) {
        return android.view.accessibility.AccessibilityInteractionClient.getInstance(this).getRootInActiveWindow(this.mConnectionId, i);
    }

    public final void disableSelf() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.disableSelf();
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Context createDisplayContext(android.view.Display display) {
        return new android.accessibilityservice.AccessibilityService.AccessibilityContext(super.createDisplayContext(display), this.mConnectionId);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Context createWindowContext(int i, android.os.Bundle bundle) {
        android.content.Context createWindowContext = super.createWindowContext(i, bundle);
        if (i != 2032) {
            return createWindowContext;
        }
        return new android.accessibilityservice.AccessibilityService.AccessibilityContext(createWindowContext, this.mConnectionId);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Context createWindowContext(android.view.Display display, int i, android.os.Bundle bundle) {
        android.content.Context createWindowContext = super.createWindowContext(display, i, bundle);
        if (i != 2032) {
            return createWindowContext;
        }
        return new android.accessibilityservice.AccessibilityService.AccessibilityContext(createWindowContext, this.mConnectionId);
    }

    public final android.accessibilityservice.AccessibilityService.MagnificationController getMagnificationController() {
        return getMagnificationController(0);
    }

    public final android.accessibilityservice.AccessibilityService.MagnificationController getMagnificationController(int i) {
        android.accessibilityservice.AccessibilityService.MagnificationController magnificationController;
        synchronized (this.mLock) {
            magnificationController = this.mMagnificationControllers.get(i);
            if (magnificationController == null) {
                magnificationController = new android.accessibilityservice.AccessibilityService.MagnificationController(this, this.mLock, i);
                this.mMagnificationControllers.put(i, magnificationController);
            }
        }
        return magnificationController;
    }

    public final android.accessibilityservice.FingerprintGestureController getFingerprintGestureController() {
        if (this.mFingerprintGestureController == null) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
            this.mFingerprintGestureController = new android.accessibilityservice.FingerprintGestureController(android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId));
        }
        return this.mFingerprintGestureController;
    }

    public final boolean dispatchGesture(android.accessibilityservice.GestureDescription gestureDescription, android.accessibilityservice.AccessibilityService.GestureResultCallback gestureResultCallback, android.os.Handler handler) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection == null) {
            return false;
        }
        java.util.List<android.accessibilityservice.GestureDescription.GestureStep> gestureStepsFromGestureDescription = android.accessibilityservice.GestureDescription.MotionEventGenerator.getGestureStepsFromGestureDescription(gestureDescription, calculateGestureSampleTimeMs(gestureDescription.getDisplayId()));
        try {
            synchronized (this.mLock) {
                this.mGestureStatusCallbackSequence++;
                if (gestureResultCallback != null) {
                    if (this.mGestureStatusCallbackInfos == null) {
                        this.mGestureStatusCallbackInfos = new android.util.SparseArray<>();
                    }
                    this.mGestureStatusCallbackInfos.put(this.mGestureStatusCallbackSequence, new android.accessibilityservice.AccessibilityService.GestureResultCallbackInfo(gestureDescription, gestureResultCallback, handler));
                }
                connection.dispatchGesture(this.mGestureStatusCallbackSequence, new android.content.pm.ParceledListSlice(gestureStepsFromGestureDescription), gestureDescription.getDisplayId());
            }
            return true;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private int calculateGestureSampleTimeMs(int i) {
        android.view.Display display;
        int refreshRate;
        if (getApplicationInfo().targetSdkVersion > 29 && (display = ((android.hardware.display.DisplayManager) getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(i)) != null && (refreshRate = (int) (1000 / display.getRefreshRate())) >= 1) {
            return refreshRate;
        }
        return 100;
    }

    void onPerformGestureResult(int i, final boolean z) {
        final android.accessibilityservice.AccessibilityService.GestureResultCallbackInfo gestureResultCallbackInfo;
        if (this.mGestureStatusCallbackInfos == null) {
            return;
        }
        synchronized (this.mLock) {
            gestureResultCallbackInfo = this.mGestureStatusCallbackInfos.get(i);
            this.mGestureStatusCallbackInfos.remove(i);
        }
        if (gestureResultCallbackInfo != null && gestureResultCallbackInfo.gestureDescription != null && gestureResultCallbackInfo.callback != null) {
            if (gestureResultCallbackInfo.handler != null) {
                gestureResultCallbackInfo.handler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (z) {
                            gestureResultCallbackInfo.callback.onCompleted(gestureResultCallbackInfo.gestureDescription);
                        } else {
                            gestureResultCallbackInfo.callback.onCancelled(gestureResultCallbackInfo.gestureDescription);
                        }
                    }
                });
            } else if (z) {
                gestureResultCallbackInfo.callback.onCompleted(gestureResultCallbackInfo.gestureDescription);
            } else {
                gestureResultCallbackInfo.callback.onCancelled(gestureResultCallbackInfo.gestureDescription);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
        android.accessibilityservice.AccessibilityService.MagnificationController magnificationController;
        synchronized (this.mLock) {
            magnificationController = this.mMagnificationControllers.get(i);
        }
        if (magnificationController != null) {
            magnificationController.dispatchMagnificationChanged(region, magnificationConfig);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFingerprintCapturingGesturesChanged(boolean z) {
        getFingerprintGestureController().onGestureDetectionActiveChanged(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFingerprintGesture(int i) {
        getFingerprintGestureController().onGesture(i);
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public static final class MagnificationController {
        private final int mDisplayId;
        private android.util.ArrayMap<android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener, android.os.Handler> mListeners;
        private final java.lang.Object mLock;
        private final android.accessibilityservice.AccessibilityService mService;

        MagnificationController(android.accessibilityservice.AccessibilityService accessibilityService, java.lang.Object obj, int i) {
            this.mService = accessibilityService;
            this.mLock = obj;
            this.mDisplayId = i;
        }

        void onServiceConnectedLocked() {
            if (this.mListeners != null && !this.mListeners.isEmpty()) {
                setMagnificationCallbackEnabled(true);
            }
        }

        public void addListener(android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener onMagnificationChangedListener) {
            addListener(onMagnificationChangedListener, null);
        }

        public void addListener(android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener onMagnificationChangedListener, android.os.Handler handler) {
            synchronized (this.mLock) {
                if (this.mListeners == null) {
                    this.mListeners = new android.util.ArrayMap<>();
                }
                boolean isEmpty = this.mListeners.isEmpty();
                this.mListeners.put(onMagnificationChangedListener, handler);
                if (isEmpty) {
                    setMagnificationCallbackEnabled(true);
                }
            }
        }

        public boolean removeListener(android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener onMagnificationChangedListener) {
            boolean z;
            if (this.mListeners == null) {
                return false;
            }
            synchronized (this.mLock) {
                int indexOfKey = this.mListeners.indexOfKey(onMagnificationChangedListener);
                z = indexOfKey >= 0;
                if (z) {
                    this.mListeners.removeAt(indexOfKey);
                }
                if (z && this.mListeners.isEmpty()) {
                    setMagnificationCallbackEnabled(false);
                }
            }
            return z;
        }

        private void setMagnificationCallbackEnabled(boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    connection.setMagnificationCallbackEnabled(this.mDisplayId, z);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
        }

        void dispatchMagnificationChanged(final android.graphics.Region region, final android.accessibilityservice.MagnificationConfig magnificationConfig) {
            synchronized (this.mLock) {
                if (this.mListeners != null && !this.mListeners.isEmpty()) {
                    android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mListeners);
                    int size = arrayMap.size();
                    for (int i = 0; i < size; i++) {
                        final android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener onMagnificationChangedListener = (android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener) arrayMap.keyAt(i);
                        android.os.Handler handler = (android.os.Handler) arrayMap.valueAt(i);
                        if (handler != null) {
                            handler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$MagnificationController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.accessibilityservice.AccessibilityService.MagnificationController.this.lambda$dispatchMagnificationChanged$0(onMagnificationChangedListener, region, magnificationConfig);
                                }
                            });
                        } else {
                            onMagnificationChangedListener.onMagnificationChanged(this, region, magnificationConfig);
                        }
                    }
                    return;
                }
                android.util.Slog.d("AccessibilityService", "Received magnification changed callback with no listeners registered!");
                setMagnificationCallbackEnabled(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchMagnificationChanged$0(android.accessibilityservice.AccessibilityService.MagnificationController.OnMagnificationChangedListener onMagnificationChangedListener, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
            onMagnificationChangedListener.onMagnificationChanged(this, region, magnificationConfig);
        }

        public android.accessibilityservice.MagnificationConfig getMagnificationConfig() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getMagnificationConfig(this.mDisplayId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to obtain magnification config", e);
                    e.rethrowFromSystemServer();
                    return null;
                }
            }
            return null;
        }

        @java.lang.Deprecated
        public float getScale() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getMagnificationScale(this.mDisplayId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to obtain scale", e);
                    e.rethrowFromSystemServer();
                    return 1.0f;
                }
            }
            return 1.0f;
        }

        @java.lang.Deprecated
        public float getCenterX() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getMagnificationCenterX(this.mDisplayId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to obtain center X", e);
                    e.rethrowFromSystemServer();
                    return 0.0f;
                }
            }
            return 0.0f;
        }

        @java.lang.Deprecated
        public float getCenterY() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getMagnificationCenterY(this.mDisplayId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to obtain center Y", e);
                    e.rethrowFromSystemServer();
                    return 0.0f;
                }
            }
            return 0.0f;
        }

        @java.lang.Deprecated
        public android.graphics.Region getMagnificationRegion() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getMagnificationRegion(this.mDisplayId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to obtain magnified region", e);
                    e.rethrowFromSystemServer();
                }
            }
            return android.graphics.Region.obtain();
        }

        public android.graphics.Region getCurrentMagnificationRegion() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getCurrentMagnificationRegion(this.mDisplayId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to obtain the current magnified region", e);
                    e.rethrowFromSystemServer();
                }
            }
            return android.graphics.Region.obtain();
        }

        public boolean reset(boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.resetMagnification(this.mDisplayId, z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to reset", e);
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        public boolean resetCurrentMagnification(boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.resetCurrentMagnification(this.mDisplayId, z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to reset", e);
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        public boolean setMagnificationConfig(android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.setMagnificationConfig(this.mDisplayId, magnificationConfig, z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to set magnification config", e);
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        @java.lang.Deprecated
        public boolean setScale(float f, boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.setMagnificationConfig(this.mDisplayId, new android.accessibilityservice.MagnificationConfig.Builder().setMode(1).setScale(f).build(), z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to set scale", e);
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        @java.lang.Deprecated
        public boolean setCenter(float f, float f2, boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.setMagnificationConfig(this.mDisplayId, new android.accessibilityservice.MagnificationConfig.Builder().setMode(1).setCenterX(f).setCenterY(f2).build(), z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to set center", e);
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        public interface OnMagnificationChangedListener {
            @java.lang.Deprecated
            void onMagnificationChanged(android.accessibilityservice.AccessibilityService.MagnificationController magnificationController, android.graphics.Region region, float f, float f2, float f3);

            default void onMagnificationChanged(android.accessibilityservice.AccessibilityService.MagnificationController magnificationController, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
                if (magnificationConfig.getMode() == 1) {
                    onMagnificationChanged(magnificationController, region, magnificationConfig.getScale(), magnificationConfig.getCenterX(), magnificationConfig.getCenterY());
                }
            }
        }
    }

    public final android.accessibilityservice.AccessibilityService.SoftKeyboardController getSoftKeyboardController() {
        android.accessibilityservice.AccessibilityService.SoftKeyboardController softKeyboardController;
        synchronized (this.mLock) {
            if (this.mSoftKeyboardController == null) {
                this.mSoftKeyboardController = new android.accessibilityservice.AccessibilityService.SoftKeyboardController(this, this.mLock);
            }
            softKeyboardController = this.mSoftKeyboardController;
        }
        return softKeyboardController;
    }

    public android.accessibilityservice.InputMethod onCreateInputMethod() {
        return new android.accessibilityservice.InputMethod(this);
    }

    public final android.accessibilityservice.InputMethod getInputMethod() {
        return this.mInputMethod;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSoftKeyboardShowModeChanged(int i) {
        if (this.mSoftKeyboardController != null) {
            this.mSoftKeyboardController.dispatchSoftKeyboardShowModeChanged(i);
        }
    }

    public static final class SoftKeyboardController {
        public static final int ENABLE_IME_FAIL_BY_ADMIN = 1;
        public static final int ENABLE_IME_FAIL_UNKNOWN = 2;
        public static final int ENABLE_IME_SUCCESS = 0;
        private android.util.ArrayMap<android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener, android.os.Handler> mListeners;
        private final java.lang.Object mLock;
        private final android.accessibilityservice.AccessibilityService mService;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EnableImeResult {
        }

        public interface OnShowModeChangedListener {
            void onShowModeChanged(android.accessibilityservice.AccessibilityService.SoftKeyboardController softKeyboardController, int i);
        }

        SoftKeyboardController(android.accessibilityservice.AccessibilityService accessibilityService, java.lang.Object obj) {
            this.mService = accessibilityService;
            this.mLock = obj;
        }

        void onServiceConnected() {
            synchronized (this.mLock) {
                if (this.mListeners != null && !this.mListeners.isEmpty()) {
                    setSoftKeyboardCallbackEnabled(true);
                }
            }
        }

        public void addOnShowModeChangedListener(android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener onShowModeChangedListener) {
            addOnShowModeChangedListener(onShowModeChangedListener, null);
        }

        public void addOnShowModeChangedListener(android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener onShowModeChangedListener, android.os.Handler handler) {
            synchronized (this.mLock) {
                if (this.mListeners == null) {
                    this.mListeners = new android.util.ArrayMap<>();
                }
                boolean isEmpty = this.mListeners.isEmpty();
                this.mListeners.put(onShowModeChangedListener, handler);
                if (isEmpty) {
                    setSoftKeyboardCallbackEnabled(true);
                }
            }
        }

        public boolean removeOnShowModeChangedListener(android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener onShowModeChangedListener) {
            boolean z;
            if (this.mListeners == null) {
                return false;
            }
            synchronized (this.mLock) {
                int indexOfKey = this.mListeners.indexOfKey(onShowModeChangedListener);
                z = indexOfKey >= 0;
                if (z) {
                    this.mListeners.removeAt(indexOfKey);
                }
                if (z && this.mListeners.isEmpty()) {
                    setSoftKeyboardCallbackEnabled(false);
                }
            }
            return z;
        }

        private void setSoftKeyboardCallbackEnabled(boolean z) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    connection.setSoftKeyboardCallbackEnabled(z);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
        }

        void dispatchSoftKeyboardShowModeChanged(final int i) {
            synchronized (this.mLock) {
                if (this.mListeners != null && !this.mListeners.isEmpty()) {
                    android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mListeners);
                    int size = arrayMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        final android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener onShowModeChangedListener = (android.accessibilityservice.AccessibilityService.SoftKeyboardController.OnShowModeChangedListener) arrayMap.keyAt(i2);
                        android.os.Handler handler = (android.os.Handler) arrayMap.valueAt(i2);
                        if (handler != null) {
                            handler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService.SoftKeyboardController.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    onShowModeChangedListener.onShowModeChanged(android.accessibilityservice.AccessibilityService.SoftKeyboardController.this, i);
                                }
                            });
                        } else {
                            onShowModeChangedListener.onShowModeChanged(this, i);
                        }
                    }
                    return;
                }
                android.util.Slog.w("AccessibilityService", "Received soft keyboard show mode changed callback with no listeners registered!");
                setSoftKeyboardCallbackEnabled(false);
            }
        }

        public int getShowMode() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.getSoftKeyboardShowMode();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to set soft keyboard behavior", e);
                    e.rethrowFromSystemServer();
                    return 0;
                }
            }
            return 0;
        }

        public boolean setShowMode(int i) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.setSoftKeyboardShowMode(i);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to set soft keyboard behavior", e);
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        public boolean switchToInputMethod(java.lang.String str) {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.switchToInputMethod(str);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
            return false;
        }

        public int setInputMethodEnabled(java.lang.String str, boolean z) throws java.lang.SecurityException {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mService);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (connection != null) {
                try {
                    return connection.setInputMethodEnabled(str, z);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
            return 2;
        }
    }

    public final android.accessibilityservice.AccessibilityButtonController getAccessibilityButtonController() {
        return getAccessibilityButtonController(0);
    }

    public final android.accessibilityservice.AccessibilityButtonController getAccessibilityButtonController(int i) {
        android.accessibilityservice.AccessibilityButtonController accessibilityButtonController;
        synchronized (this.mLock) {
            accessibilityButtonController = this.mAccessibilityButtonControllers.get(i);
            if (accessibilityButtonController == null) {
                android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
                accessibilityButtonController = new android.accessibilityservice.AccessibilityButtonController(android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId));
                this.mAccessibilityButtonControllers.put(i, accessibilityButtonController);
            }
        }
        return accessibilityButtonController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityButtonClicked(int i) {
        getAccessibilityButtonController(i).dispatchAccessibilityButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityButtonAvailabilityChanged(boolean z) {
        getAccessibilityButtonController().dispatchAccessibilityButtonAvailabilityChanged(z);
    }

    public boolean setCacheEnabled(boolean z) {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null || (connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId)) == null) {
            return false;
        }
        try {
            connection.setCacheEnabled(z);
            cache.setEnabled(z);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.w("AccessibilityService", "Error while setting status of cache", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean clearCachedSubtree(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        return cache.clearSubTree(accessibilityNodeInfo);
    }

    public boolean clearCache() {
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        cache.clear();
        return true;
    }

    public boolean isNodeInCache(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        return cache.isNodeInCache(accessibilityNodeInfo);
    }

    public boolean isCacheEnabled() {
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(this.mConnectionId);
        if (cache == null) {
            return false;
        }
        return cache.isEnabled();
    }

    public void onSystemActionsChanged() {
    }

    public final java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                return connection.getSystemActions();
            } catch (android.os.RemoteException e) {
                android.util.Log.w("AccessibilityService", "Error while calling getSystemActions", e);
                e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    public final boolean performGlobalAction(int i) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                return connection.performGlobalAction(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w("AccessibilityService", "Error while calling performGlobalAction", e);
                e.rethrowFromSystemServer();
                return false;
            }
        }
        return false;
    }

    public android.view.accessibility.AccessibilityNodeInfo findFocus(int i) {
        return android.view.accessibility.AccessibilityInteractionClient.getInstance(this).findFocus(this.mConnectionId, -2, android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, i);
    }

    public final android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                return connection.getServiceInfo();
            } catch (android.os.RemoteException e) {
                android.util.Log.w("AccessibilityService", "Error while getting AccessibilityServiceInfo", e);
                e.rethrowFromSystemServer();
                return null;
            }
        }
        return null;
    }

    public final void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        this.mInfo = accessibilityServiceInfo;
        updateInputMethod(accessibilityServiceInfo);
        this.mMotionEventSources = accessibilityServiceInfo.getMotionEventSources();
        sendServiceInfo();
    }

    private void sendServiceInfo() {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (this.mInfo != null && connection != null) {
            try {
                connection.setServiceInfo(this.mInfo);
                this.mInfo = null;
                android.view.accessibility.AccessibilityInteractionClient.getInstance(this).clearCache(this.mConnectionId);
            } catch (android.os.RemoteException e) {
                android.util.Log.w("AccessibilityService", "Error while setting AccessibilityServiceInfo", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (getBaseContext() == null) {
            throw new java.lang.IllegalStateException("System services not available to Activities before onCreate()");
        }
        if (android.content.Context.WINDOW_SERVICE.equals(str)) {
            if (this.mWindowManager == null) {
                this.mWindowManager = (android.view.WindowManager) getBaseContext().getSystemService(str);
                ((android.view.WindowManagerImpl) this.mWindowManager).setDefaultToken(this.mWindowToken);
            }
            return this.mWindowManager;
        }
        return super.getSystemService(str);
    }

    public void takeScreenshot(int i, final java.util.concurrent.Executor executor, final android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback) {
        com.android.internal.util.Preconditions.checkNotNull(executor, "executor cannot be null");
        com.android.internal.util.Preconditions.checkNotNull(takeScreenshotCallback, "callback cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection == null) {
            sendScreenshotFailure(1, executor, takeScreenshotCallback);
            return;
        }
        try {
            connection.takeScreenshot(i, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.accessibilityservice.AccessibilityService$$ExternalSyntheticLambda2
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.accessibilityservice.AccessibilityService.this.lambda$takeScreenshot$0(executor, takeScreenshotCallback, bundle);
                }
            }));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshot$0(java.util.concurrent.Executor executor, android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback, android.os.Bundle bundle) {
        int i = bundle.getInt(KEY_ACCESSIBILITY_SCREENSHOT_STATUS);
        if (i != 0) {
            sendScreenshotFailure(i, executor, takeScreenshotCallback);
        } else {
            sendScreenshotSuccess(new android.accessibilityservice.AccessibilityService.ScreenshotResult((android.hardware.HardwareBuffer) bundle.getParcelable(KEY_ACCESSIBILITY_SCREENSHOT_HARDWAREBUFFER, android.hardware.HardwareBuffer.class), ((android.graphics.ParcelableColorSpace) bundle.getParcelable(KEY_ACCESSIBILITY_SCREENSHOT_COLORSPACE, android.graphics.ParcelableColorSpace.class)).getColorSpace(), bundle.getLong(KEY_ACCESSIBILITY_SCREENSHOT_TIMESTAMP)), executor, takeScreenshotCallback);
        }
    }

    public void takeScreenshotOfWindow(int i, java.util.concurrent.Executor executor, android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this).takeScreenshotOfWindow(this.mConnectionId, i, executor, takeScreenshotCallback);
    }

    public void setAccessibilityFocusAppearance(int i, int i2) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setFocusAppearance(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.w("AccessibilityService", "Error while setting the strokeWidth and color of the accessibility focus rectangle", e);
                e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper(this, getMainExecutor(), new android.accessibilityservice.AccessibilityService.Callbacks() { // from class: android.accessibilityservice.AccessibilityService.2
            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onServiceConnected() {
                android.accessibilityservice.AccessibilityService.this.dispatchServiceConnected();
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onInterrupt() {
                android.accessibilityservice.AccessibilityService.this.onInterrupt();
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
                android.accessibilityservice.AccessibilityService.this.onAccessibilityEvent(accessibilityEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void init(int i, android.os.IBinder iBinder) {
                android.accessibilityservice.AccessibilityService.this.mConnectionId = i;
                android.accessibilityservice.AccessibilityService.this.mWindowToken = iBinder;
                if (android.accessibilityservice.AccessibilityService.this.mWindowManager != null) {
                    ((android.view.WindowManagerImpl) android.accessibilityservice.AccessibilityService.this.mWindowManager).setDefaultToken(android.accessibilityservice.AccessibilityService.this.mWindowToken);
                }
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public boolean onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
                return android.accessibilityservice.AccessibilityService.this.onGesture(accessibilityGestureEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public boolean onKeyEvent(android.view.KeyEvent keyEvent) {
                return android.accessibilityservice.AccessibilityService.this.onKeyEvent(keyEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
                android.accessibilityservice.AccessibilityService.this.onMagnificationChanged(i, region, magnificationConfig);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onMotionEvent(android.view.MotionEvent motionEvent) {
                android.accessibilityservice.AccessibilityService.this.sendMotionEventToCallback(motionEvent);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onTouchStateChanged(int i, int i2) {
                android.accessibilityservice.AccessibilityService.this.onTouchStateChanged(i, i2);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onSoftKeyboardShowModeChanged(int i) {
                android.accessibilityservice.AccessibilityService.this.onSoftKeyboardShowModeChanged(i);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onPerformGestureResult(int i, boolean z) {
                android.accessibilityservice.AccessibilityService.this.onPerformGestureResult(i, z);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onFingerprintCapturingGesturesChanged(boolean z) {
                android.accessibilityservice.AccessibilityService.this.onFingerprintCapturingGesturesChanged(z);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onFingerprintGesture(int i) {
                android.accessibilityservice.AccessibilityService.this.onFingerprintGesture(i);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onAccessibilityButtonClicked(int i) {
                android.accessibilityservice.AccessibilityService.this.onAccessibilityButtonClicked(i);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onAccessibilityButtonAvailabilityChanged(boolean z) {
                android.accessibilityservice.AccessibilityService.this.onAccessibilityButtonAvailabilityChanged(z);
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void onSystemActionsChanged() {
                android.accessibilityservice.AccessibilityService.this.onSystemActionsChanged();
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
                if (android.accessibilityservice.AccessibilityService.this.mInputMethod != null) {
                    android.accessibilityservice.AccessibilityService.this.mInputMethod.createImeSession(iAccessibilityInputMethodSessionCallback);
                }
            }

            @Override // android.accessibilityservice.AccessibilityService.Callbacks
            public void startInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
                if (android.accessibilityservice.AccessibilityService.this.mInputMethod != null) {
                    if (z) {
                        android.accessibilityservice.AccessibilityService.this.mInputMethod.restartInput(remoteAccessibilityInputConnection, editorInfo);
                    } else {
                        android.accessibilityservice.AccessibilityService.this.mInputMethod.startInput(remoteAccessibilityInputConnection, editorInfo);
                    }
                }
            }
        });
    }

    public static class IAccessibilityServiceClientWrapper extends android.accessibilityservice.IAccessibilityServiceClient.Stub {
        private final android.accessibilityservice.AccessibilityService.Callbacks mCallback;
        com.android.internal.inputmethod.CancellationGroup mCancellationGroup;
        private int mConnectionId;
        private final android.content.Context mContext;
        private final java.util.concurrent.Executor mExecutor;

        public IAccessibilityServiceClientWrapper(android.content.Context context, java.util.concurrent.Executor executor, android.accessibilityservice.AccessibilityService.Callbacks callbacks) {
            this.mConnectionId = -1;
            this.mCancellationGroup = null;
            this.mCallback = callbacks;
            this.mContext = context;
            this.mExecutor = executor;
        }

        public IAccessibilityServiceClientWrapper(android.content.Context context, android.os.Looper looper, android.accessibilityservice.AccessibilityService.Callbacks callbacks) {
            this(context, new android.os.HandlerExecutor(new android.os.Handler(looper)), callbacks);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void init(final android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, final int i, final android.os.IBinder iBinder) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$init$0(i, iAccessibilityServiceConnection, iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$init$0(int i, android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, android.os.IBinder iBinder) {
            this.mConnectionId = i;
            if (iAccessibilityServiceConnection != null) {
                android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mContext);
                android.view.accessibility.AccessibilityInteractionClient.addConnection(this.mConnectionId, iAccessibilityServiceConnection, true);
                if (this.mContext != null) {
                    try {
                        iAccessibilityServiceConnection.setAttributionTag(this.mContext.getAttributionTag());
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w("AccessibilityService", "Error while setting attributionTag", e);
                        e.rethrowFromSystemServer();
                    }
                }
                this.mCallback.init(this.mConnectionId, iBinder);
                this.mCallback.onServiceConnected();
                return;
            }
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mContext).clearCache(this.mConnectionId);
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mContext);
            android.view.accessibility.AccessibilityInteractionClient.removeConnection(this.mConnectionId);
            this.mConnectionId = -1;
            this.mCallback.init(-1, null);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onInterrupt() {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onInterrupt$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterrupt$1() {
            if (this.mConnectionId != -1) {
                this.mCallback.onInterrupt();
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityEvent(final android.view.accessibility.AccessibilityEvent accessibilityEvent, final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onAccessibilityEvent$2(accessibilityEvent, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAccessibilityEvent$2(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z) {
            if (accessibilityEvent != null) {
                android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mContext).onAccessibilityEvent(accessibilityEvent, this.mConnectionId);
                if (z && this.mConnectionId != -1) {
                    this.mCallback.onAccessibilityEvent(accessibilityEvent);
                }
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onGesture(final android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onGesture$3(accessibilityGestureEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGesture$3(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
            if (this.mConnectionId != -1) {
                this.mCallback.onGesture(accessibilityGestureEvent);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void clearAccessibilityCache() {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$clearAccessibilityCache$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearAccessibilityCache$4() {
            android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mContext).clearCache(this.mConnectionId);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onKeyEvent(final android.view.KeyEvent keyEvent, final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onKeyEvent$5(keyEvent, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKeyEvent$5(android.view.KeyEvent keyEvent, int i) {
            try {
                android.view.accessibility.AccessibilityInteractionClient.getInstance(this.mContext);
                android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
                if (connection != null) {
                    try {
                        connection.setOnKeyEventResult(this.mCallback.onKeyEvent(keyEvent), i);
                    } catch (android.os.RemoteException e) {
                    }
                }
                try {
                    keyEvent.recycle();
                } catch (java.lang.IllegalStateException e2) {
                }
            } catch (java.lang.Throwable th) {
                try {
                    keyEvent.recycle();
                } catch (java.lang.IllegalStateException e3) {
                }
                throw th;
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMagnificationChanged(final int i, final android.graphics.Region region, final android.accessibilityservice.MagnificationConfig magnificationConfig) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onMagnificationChanged$6(i, region, magnificationConfig);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMagnificationChanged$6(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
            if (this.mConnectionId != -1) {
                this.mCallback.onMagnificationChanged(i, region, magnificationConfig);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSoftKeyboardShowModeChanged(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onSoftKeyboardShowModeChanged$7(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoftKeyboardShowModeChanged$7(int i) {
            if (this.mConnectionId != -1) {
                this.mCallback.onSoftKeyboardShowModeChanged(i);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onPerformGestureResult(final int i, final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onPerformGestureResult$8(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPerformGestureResult$8(int i, boolean z) {
            if (this.mConnectionId != -1) {
                this.mCallback.onPerformGestureResult(i, z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintCapturingGesturesChanged(final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onFingerprintCapturingGesturesChanged$9(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFingerprintCapturingGesturesChanged$9(boolean z) {
            if (this.mConnectionId != -1) {
                this.mCallback.onFingerprintCapturingGesturesChanged(z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintGesture(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onFingerprintGesture$10(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFingerprintGesture$10(int i) {
            if (this.mConnectionId != -1) {
                this.mCallback.onFingerprintGesture(i);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonClicked(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onAccessibilityButtonClicked$11(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAccessibilityButtonClicked$11(int i) {
            if (this.mConnectionId != -1) {
                this.mCallback.onAccessibilityButtonClicked(i);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonAvailabilityChanged(final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onAccessibilityButtonAvailabilityChanged$12(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAccessibilityButtonAvailabilityChanged$12(boolean z) {
            if (this.mConnectionId != -1) {
                this.mCallback.onAccessibilityButtonAvailabilityChanged(z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSystemActionsChanged() {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onSystemActionsChanged$13();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemActionsChanged$13() {
            if (this.mConnectionId != -1) {
                this.mCallback.onSystemActionsChanged();
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void createImeSession(final com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$createImeSession$14(iAccessibilityInputMethodSessionCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createImeSession$14(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
            if (this.mConnectionId != -1) {
                this.mCallback.createImeSession(iAccessibilityInputMethodSessionCallback);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void setImeSessionEnabled(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, final boolean z) {
            try {
                final android.accessibilityservice.AccessibilityInputMethodSession session = ((android.accessibilityservice.AccessibilityInputMethodSessionWrapper) iAccessibilityInputMethodSession).getSession();
                if (session == null) {
                    android.util.Log.w("AccessibilityService", "Session is already finished: " + iAccessibilityInputMethodSession);
                } else {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$setImeSessionEnabled$15(session, z);
                        }
                    });
                }
            } catch (java.lang.ClassCastException e) {
                android.util.Log.w("AccessibilityService", "Incoming session not of correct type: " + iAccessibilityInputMethodSession, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setImeSessionEnabled$15(android.accessibilityservice.AccessibilityInputMethodSession accessibilityInputMethodSession, boolean z) {
            if (this.mConnectionId != -1) {
                accessibilityInputMethodSession.setEnabled(z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void bindInput() {
            if (this.mCancellationGroup != null) {
                android.util.Log.e("AccessibilityService", "bindInput must be paired with unbindInput.");
            }
            this.mCancellationGroup = new com.android.internal.inputmethod.CancellationGroup();
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void unbindInput() {
            if (this.mCancellationGroup != null) {
                this.mCancellationGroup.cancelAll();
                this.mCancellationGroup = null;
            } else {
                android.util.Log.e("AccessibilityService", "unbindInput must be paired with bindInput.");
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void startInput(final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final android.view.inputmethod.EditorInfo editorInfo, final boolean z) {
            if (this.mCancellationGroup == null) {
                android.util.Log.e("AccessibilityService", "startInput must be called after bindInput.");
                this.mCancellationGroup = new com.android.internal.inputmethod.CancellationGroup();
            }
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$startInput$16(iRemoteAccessibilityInputConnection, editorInfo, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startInput$16(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
            if (this.mConnectionId != -1) {
                com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection = iRemoteAccessibilityInputConnection == null ? null : new com.android.internal.inputmethod.RemoteAccessibilityInputConnection(iRemoteAccessibilityInputConnection, this.mCancellationGroup);
                editorInfo.makeCompatible(this.mContext.getApplicationInfo().targetSdkVersion);
                this.mCallback.startInput(remoteAccessibilityInputConnection, editorInfo, z);
            }
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMotionEvent(final android.view.MotionEvent motionEvent) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onMotionEvent$17(motionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMotionEvent$17(android.view.MotionEvent motionEvent) {
            this.mCallback.onMotionEvent(motionEvent);
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onTouchStateChanged(final int i, final int i2) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$IAccessibilityServiceClientWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper.this.lambda$onTouchStateChanged$18(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTouchStateChanged$18(int i, int i2) {
            this.mCallback.onTouchStateChanged(i, i2);
        }
    }

    public static abstract class GestureResultCallback {
        public void onCompleted(android.accessibilityservice.GestureDescription gestureDescription) {
        }

        public void onCancelled(android.accessibilityservice.GestureDescription gestureDescription) {
        }
    }

    private static class GestureResultCallbackInfo {
        android.accessibilityservice.AccessibilityService.GestureResultCallback callback;
        android.accessibilityservice.GestureDescription gestureDescription;
        android.os.Handler handler;

        GestureResultCallbackInfo(android.accessibilityservice.GestureDescription gestureDescription, android.accessibilityservice.AccessibilityService.GestureResultCallback gestureResultCallback, android.os.Handler handler) {
            this.gestureDescription = gestureDescription;
            this.callback = gestureResultCallback;
            this.handler = handler;
        }
    }

    private void sendScreenshotSuccess(final android.accessibilityservice.AccessibilityService.ScreenshotResult screenshotResult, java.util.concurrent.Executor executor, final android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback) {
        executor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.accessibilityservice.AccessibilityService.TakeScreenshotCallback.this.onSuccess(screenshotResult);
            }
        });
    }

    private void sendScreenshotFailure(final int i, java.util.concurrent.Executor executor, final android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback) {
        executor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.accessibilityservice.AccessibilityService.TakeScreenshotCallback.this.onFailure(i);
            }
        });
    }

    public static final class ScreenshotResult {
        private final android.graphics.ColorSpace mColorSpace;
        private final android.hardware.HardwareBuffer mHardwareBuffer;
        private final long mTimestamp;

        public ScreenshotResult(android.hardware.HardwareBuffer hardwareBuffer, android.graphics.ColorSpace colorSpace, long j) {
            com.android.internal.util.Preconditions.checkNotNull(hardwareBuffer, "hardwareBuffer cannot be null");
            com.android.internal.util.Preconditions.checkNotNull(colorSpace, "colorSpace cannot be null");
            this.mHardwareBuffer = hardwareBuffer;
            this.mColorSpace = colorSpace;
            this.mTimestamp = j;
        }

        public android.graphics.ColorSpace getColorSpace() {
            return this.mColorSpace;
        }

        public android.hardware.HardwareBuffer getHardwareBuffer() {
            return this.mHardwareBuffer;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }
    }

    public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) {
        com.android.internal.util.Preconditions.checkNotNull(region, "region cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setGestureDetectionPassthroughRegion(i, region);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) {
        com.android.internal.util.Preconditions.checkNotNull(region, "region cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setTouchExplorationPassthroughRegion(i, region);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void setAnimationScale(float f) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this);
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setAnimationScale(f);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    private static class AccessibilityContext extends android.content.ContextWrapper {
        private final int mConnectionId;

        private AccessibilityContext(android.content.Context context, int i) {
            super(context);
            this.mConnectionId = i;
            setDefaultTokenInternal(this, getDisplayId());
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public android.content.Context createDisplayContext(android.view.Display display) {
            return new android.accessibilityservice.AccessibilityService.AccessibilityContext(super.createDisplayContext(display), this.mConnectionId);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public android.content.Context createWindowContext(int i, android.os.Bundle bundle) {
            android.content.Context createWindowContext = super.createWindowContext(i, bundle);
            if (i != 2032) {
                return createWindowContext;
            }
            return new android.accessibilityservice.AccessibilityService.AccessibilityContext(createWindowContext, this.mConnectionId);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public android.content.Context createWindowContext(android.view.Display display, int i, android.os.Bundle bundle) {
            android.content.Context createWindowContext = super.createWindowContext(display, i, bundle);
            if (i != 2032) {
                return createWindowContext;
            }
            return new android.accessibilityservice.AccessibilityService.AccessibilityContext(createWindowContext, this.mConnectionId);
        }

        private void setDefaultTokenInternal(android.content.Context context, int i) {
            android.os.IBinder iBinder;
            android.view.WindowManagerImpl windowManagerImpl = (android.view.WindowManagerImpl) context.getSystemService(android.content.Context.WINDOW_SERVICE);
            android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
            if (connection != null) {
                try {
                    iBinder = connection.getOverlayWindowToken(i);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w("AccessibilityService", "Failed to get window token", e);
                    e.rethrowFromSystemServer();
                    iBinder = null;
                }
                windowManagerImpl.setDefaultToken(iBinder);
            }
        }
    }

    public final android.accessibilityservice.TouchInteractionController getTouchInteractionController(int i) {
        android.accessibilityservice.TouchInteractionController touchInteractionController;
        synchronized (this.mLock) {
            touchInteractionController = this.mTouchInteractionControllers.get(i);
            if (touchInteractionController == null) {
                touchInteractionController = new android.accessibilityservice.TouchInteractionController(this, this.mLock, i);
                this.mTouchInteractionControllers.put(i, touchInteractionController);
            }
        }
        return touchInteractionController;
    }

    void sendMotionEventToCallback(android.view.MotionEvent motionEvent) {
        boolean z;
        android.accessibilityservice.TouchInteractionController touchInteractionController;
        if (motionEvent.isFromSource(4098)) {
            synchronized (this.mLock) {
                touchInteractionController = this.mTouchInteractionControllers.get(motionEvent.getDisplayId());
            }
            if (touchInteractionController != null) {
                touchInteractionController.onMotionEvent(motionEvent);
                z = true;
                if ((motionEvent.getSource() & (-256) & this.mMotionEventSources) == 0 && !z) {
                    onMotionEvent(motionEvent);
                    return;
                }
            }
        }
        z = false;
        if ((motionEvent.getSource() & (-256) & this.mMotionEventSources) == 0) {
        }
    }

    void onTouchStateChanged(int i, int i2) {
        android.accessibilityservice.TouchInteractionController touchInteractionController;
        synchronized (this.mLock) {
            touchInteractionController = this.mTouchInteractionControllers.get(i);
        }
        if (touchInteractionController != null) {
            touchInteractionController.onStateChanged(i2);
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl) {
        com.android.internal.util.Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToDisplay(this.mConnectionId, i, surfaceControl, null, null);
    }

    public void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        com.android.internal.util.Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToDisplay(this.mConnectionId, i, surfaceControl, executor, intConsumer);
    }

    public void attachAccessibilityOverlayToWindow(int i, android.view.SurfaceControl surfaceControl) {
        com.android.internal.util.Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToWindow(this.mConnectionId, i, surfaceControl, null, null);
    }

    public void attachAccessibilityOverlayToWindow(int i, android.view.SurfaceControl surfaceControl, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        com.android.internal.util.Preconditions.checkNotNull(surfaceControl, "SurfaceControl cannot be null");
        android.view.accessibility.AccessibilityInteractionClient.getInstance(this).attachAccessibilityOverlayToWindow(this.mConnectionId, i, surfaceControl, executor, intConsumer);
    }

    public android.accessibilityservice.BrailleDisplayController getBrailleDisplayController() {
        android.accessibilityservice.BrailleDisplayController brailleDisplayController;
        android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
        synchronized (this.mLock) {
            if (this.mBrailleDisplayController == null) {
                this.mBrailleDisplayController = new android.accessibilityservice.BrailleDisplayControllerImpl(this, this.mLock);
            }
            brailleDisplayController = this.mBrailleDisplayController;
        }
        return brailleDisplayController;
    }
}
