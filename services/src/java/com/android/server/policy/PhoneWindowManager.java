package com.android.server.policy;

/* loaded from: classes2.dex */
public class PhoneWindowManager implements com.android.server.policy.WindowManagerPolicy {
    private static final java.lang.String ACTION_TORCH_OFF = "com.android.server.policy.PhoneWindowManager.ACTION_TORCH_OFF";
    private static final java.lang.String ACTION_VOICE_ASSIST_RETAIL = "android.intent.action.VOICE_ASSIST_RETAIL";
    private static final int BRIGHTNESS_STEPS = 10;
    private static final long BUGREPORT_TV_GESTURE_TIMEOUT_MILLIS = 1000;
    static final boolean DEBUG_INPUT = false;
    static final boolean DEBUG_KEYGUARD = false;
    static final boolean DEBUG_WAKEUP = false;
    static final int DOUBLE_PRESS_PRIMARY_NOTHING = 0;
    static final int DOUBLE_PRESS_PRIMARY_SWITCH_RECENT_APP = 1;
    static final int DOUBLE_TAP_HOME_NOTHING = 0;
    static final int DOUBLE_TAP_HOME_PIP_MENU = 2;
    static final int DOUBLE_TAP_HOME_RECENT_SYSTEM_UI = 1;
    static final boolean ENABLE_DESK_DOCK_HOME_CAPTURE = false;
    static final boolean ENABLE_VR_HEADSET_HOME_CAPTURE = true;
    private static final float KEYGUARD_SCREENSHOT_CHORD_DELAY_MULTIPLIER = 2.5f;
    static final int LAST_LONG_PRESS_HOME_BEHAVIOR = 3;
    static final int LAST_SHORT_PRESS_SETTINGS_BEHAVIOR = 1;
    static final int LONG_PRESS_BACK_GO_TO_VOICE_ASSIST = 1;
    static final int LONG_PRESS_BACK_NOTHING = 0;
    static final int LONG_PRESS_HOME_ALL_APPS = 1;
    static final int LONG_PRESS_HOME_ASSIST = 2;
    static final int LONG_PRESS_HOME_NOTHING = 0;
    static final int LONG_PRESS_HOME_NOTIFICATION_PANEL = 3;
    static final int LONG_PRESS_POWER_ASSISTANT = 5;
    static final int LONG_PRESS_POWER_GLOBAL_ACTIONS = 1;
    static final int LONG_PRESS_POWER_GO_TO_VOICE_ASSIST = 4;
    static final int LONG_PRESS_POWER_NOTHING = 0;
    static final int LONG_PRESS_POWER_SHUT_OFF = 2;
    static final int LONG_PRESS_POWER_SHUT_OFF_NO_CONFIRM = 3;
    static final int LONG_PRESS_POWER_TORCH = 6;
    static final int LONG_PRESS_PRIMARY_LAUNCH_VOICE_ASSISTANT = 1;
    static final int LONG_PRESS_PRIMARY_NOTHING = 0;
    private static final int MSG_ACCESSIBILITY_SHORTCUT = 17;
    private static final int MSG_ACCESSIBILITY_TV = 19;
    private static final int MSG_BUGREPORT_TV = 18;
    private static final int MSG_CAMERA_LONG_PRESS = 101;
    private static final int MSG_DISPATCH_BACK_KEY_TO_AUTOFILL = 20;
    private static final int MSG_DISPATCH_MEDIA_KEY_REPEAT_WITH_WAKE_LOCK = 4;
    private static final int MSG_DISPATCH_MEDIA_KEY_WITH_WAKE_LOCK = 3;
    private static final int MSG_DISPATCH_SHOW_GLOBAL_ACTIONS = 10;
    private static final int MSG_DISPATCH_SHOW_RECENTS = 9;
    private static final int MSG_HANDLE_ALL_APPS = 22;
    private static final int MSG_HIDE_BOOT_MESSAGE = 11;
    private static final int MSG_KEYGUARD_DRAWN_COMPLETE = 5;
    private static final int MSG_KEYGUARD_DRAWN_TIMEOUT = 6;
    private static final int MSG_LAUNCH_ASSIST = 23;
    private static final int MSG_LAUNCH_VOICE_ASSIST_WITH_WAKE_LOCK = 12;
    private static final int MSG_LOG_KEYBOARD_SYSTEM_EVENT = 26;
    private static final int MSG_RINGER_TOGGLE_CHORD = 24;
    private static final int MSG_SCREENSHOT_CHORD = 16;
    private static final int MSG_SET_DEFERRED_KEY_ACTIONS_EXECUTABLE = 27;
    private static final int MSG_SHOW_PICTURE_IN_PICTURE_MENU = 15;
    private static final int MSG_SWITCH_KEYBOARD_LAYOUT = 25;
    private static final int MSG_SYSTEM_KEY_PRESS = 21;
    private static final int MSG_TOGGLE_TORCH = 100;
    private static final int MSG_WINDOW_MANAGER_DRAWN_COMPLETE = 7;
    static final int MULTI_PRESS_POWER_BRIGHTNESS_BOOST = 2;
    static final int MULTI_PRESS_POWER_LAUNCH_TARGET_ACTIVITY = 3;
    static final int MULTI_PRESS_POWER_NOTHING = 0;
    static final int MULTI_PRESS_POWER_THEATER_MODE = 1;
    static final int PENDING_KEY_NULL = -1;
    private static final int POWER_BUTTON_SUPPRESSION_DELAY_DEFAULT_MILLIS = 800;
    static final int POWER_VOLUME_UP_BEHAVIOR_GLOBAL_ACTIONS = 2;
    static final int POWER_VOLUME_UP_BEHAVIOR_MUTE = 1;
    static final int POWER_VOLUME_UP_BEHAVIOR_NOTHING = 0;
    static final int SEARCH_BEHAVIOR_DEFAULT_SEARCH = 0;
    static final int SEARCH_BEHAVIOR_TARGET_ACTIVITY = 1;
    static final int SHORT_PRESS_POWER_CLOSE_IME_OR_GO_HOME = 5;
    static final int SHORT_PRESS_POWER_DREAM_OR_SLEEP = 7;
    static final int SHORT_PRESS_POWER_GO_HOME = 4;
    static final int SHORT_PRESS_POWER_GO_TO_SLEEP = 1;
    static final int SHORT_PRESS_POWER_LOCK_OR_SLEEP = 6;
    static final int SHORT_PRESS_POWER_NOTHING = 0;
    static final int SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP = 2;
    static final int SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP_AND_GO_HOME = 3;
    static final int SHORT_PRESS_PRIMARY_LAUNCH_ALL_APPS = 1;
    static final int SHORT_PRESS_PRIMARY_LAUNCH_TARGET_ACTIVITY = 2;
    static final int SHORT_PRESS_PRIMARY_NOTHING = 0;
    static final int SHORT_PRESS_SETTINGS_NOTHING = 0;
    static final int SHORT_PRESS_SETTINGS_NOTIFICATION_PANEL = 1;
    static final int SHORT_PRESS_SLEEP_GO_TO_SLEEP = 0;
    static final int SHORT_PRESS_SLEEP_GO_TO_SLEEP_AND_GO_HOME = 1;
    static final int SHORT_PRESS_WINDOW_NOTHING = 0;
    static final int SHORT_PRESS_WINDOW_PICTURE_IN_PICTURE = 1;
    public static final java.lang.String SYSTEM_DIALOG_REASON_ASSIST = "assist";
    public static final java.lang.String SYSTEM_DIALOG_REASON_GESTURE_NAV = "gestureNav";
    public static final java.lang.String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    public static final java.lang.String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    public static final java.lang.String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final java.lang.String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    public static final java.lang.String SYSTEM_DIALOG_REASON_SCREENSHOT = "screenshot";
    static final java.lang.String TAG = "WindowManager";
    public static final int TOAST_WINDOW_ANIM_BUFFER = 600;
    public static final int TOAST_WINDOW_TIMEOUT = 4100;
    public static final java.lang.String TRACE_WAIT_FOR_ALL_WINDOWS_DRAWN_METHOD = "waitForAllWindowsDrawn";
    static final int TRIPLE_PRESS_PRIMARY_NOTHING = 0;
    static final int TRIPLE_PRESS_PRIMARY_TOGGLE_ACCESSIBILITY = 1;
    static final int VERY_LONG_PRESS_POWER_GLOBAL_ACTIONS = 1;
    static final int VERY_LONG_PRESS_POWER_NOTHING = 0;
    static final int WAITING_FOR_DRAWN_TIMEOUT = 1000;
    private static final int[] WINDOW_TYPES_WHERE_HOME_DOESNT_WORK = {2003, 2010};
    static final boolean localLOGV = false;
    android.view.accessibility.AccessibilityManager mAccessibilityManager;
    com.android.server.AccessibilityManagerInternal mAccessibilityManagerInternal;
    private com.android.internal.accessibility.AccessibilityShortcutController mAccessibilityShortcutController;
    android.app.ActivityManagerInternal mActivityManagerInternal;
    android.app.IActivityManager mActivityManagerService;
    com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
    android.app.AlarmManager mAlarmManager;
    boolean mAllowStartActivityForLongPressOnPowerDuringSetup;
    android.app.AppOpsManager mAppOpsManager;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mAppSwitchLongPressAction;
    boolean mAppSwitchLongPressed;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mAppSwitchPressAction;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mAssistLongPressAction;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mAssistPressAction;
    boolean mAssistPressed;
    private com.android.internal.app.AssistUtils mAssistUtils;
    android.media.AudioManagerInternal mAudioManagerInternal;
    android.view.autofill.AutofillManagerInternal mAutofillManagerInternal;
    volatile boolean mBackKeyHandled;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mBackLongPressAction;
    private android.app.ActivityManager.RecentTaskInfo mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp;
    volatile boolean mBootAnimationDismissable;
    boolean mBootMessageNeedsHiding;
    android.os.PowerManager.WakeLock mBroadcastWakeLock;
    com.android.server.policy.BurnInProtectionHelper mBurnInProtectionHelper;
    private com.android.server.policy.PhoneWindowManager.ButtonOverridePermissionChecker mButtonOverridePermissionChecker;
    volatile boolean mCameraGestureTriggered;
    volatile boolean mCameraGestureTriggeredDuringGoingToSleep;
    boolean mCameraLaunch;
    private android.hardware.camera2.CameraManager mCameraManager;
    boolean mCameraSleepOnRelease;
    android.content.Intent mCarDockIntent;
    boolean mClickPartialScreenshot;
    android.content.Context mContext;
    private int mCurrentUserId;
    android.view.Display mDefaultDisplay;
    com.android.server.wm.DisplayPolicy mDefaultDisplayPolicy;
    com.android.server.wm.DisplayRotation mDefaultDisplayRotation;
    android.content.Intent mDeskDockIntent;
    volatile boolean mDeviceGoingToSleep;
    private int mDeviceHardwareKeys;
    private volatile boolean mDismissImeOnBackKeyPressed;
    private com.android.server.policy.DisplayFoldController mDisplayFoldController;
    android.hardware.display.DisplayManager mDisplayManager;
    android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    int mDoublePressOnPowerBehavior;
    private int mDoublePressOnStemPrimaryBehavior;
    android.service.dreams.DreamManagerInternal mDreamManagerInternal;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mEdgeLongSwipeAction;
    volatile boolean mEndCallKeyHandled;
    int mEndcallBehavior;
    boolean mFocusReleasedGoToSleep;
    private android.app.ActivityTaskManager.RootTaskInfo mFocusedTaskInfoOnStemPrimarySingleKeyUp;
    private com.android.server.GestureLauncherService mGestureLauncherService;
    private com.android.server.policy.GlobalActions mGlobalActions;
    private java.util.function.Supplier<com.android.server.policy.GlobalActions> mGlobalActionsFactory;
    private com.android.server.policy.GlobalKeyManager mGlobalKeyManager;
    private boolean mGoToSleepOnButtonPressTheaterMode;
    private boolean mHandleVolumeKeysInWM;
    private android.os.Handler mHandler;
    private com.android.server.vibrator.HapticFeedbackVibrationProvider mHapticFeedbackVibrationProvider;
    private boolean mHasFeatureAuto;
    private boolean mHasFeatureHdmiCec;
    private boolean mHasFeatureLeanback;
    private boolean mHasFeatureWatch;
    boolean mHaveBuiltInKeyboard;
    boolean mHavePendingMediaKeyRepeatWithWakeLock;
    com.android.server.policy.PhoneWindowManager.HdmiControl mHdmiControl;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mHomeDoubleTapAction;
    android.content.Intent mHomeIntent;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mHomeLongPressAction;
    int mIncallBackBehavior;
    int mIncallPowerBehavior;
    android.hardware.input.InputManager mInputManager;
    com.android.server.input.InputManagerInternal mInputManagerInternal;
    boolean mIsFocusPressed;
    boolean mIsLongPress;
    private com.android.server.policy.KeyCombinationManager mKeyCombinationManager;
    private boolean mKeyguardBound;
    private com.android.server.policy.keyguard.KeyguardServiceDelegate mKeyguardDelegate;
    private boolean mKeyguardDrawnOnce;
    private boolean mKeyguardOccludedChanged;
    boolean mKidsModeEnabled;
    int mLidKeyboardAccessibility;
    int mLidNavigationAccessibility;
    private org.lineageos.internal.buttons.LineageButtons mLineageButtons;
    private lineageos.hardware.LineageHardwareManager mLineageHardware;
    private boolean mLockAfterDreamingTransitionFinished;
    com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    int mLockScreenTimeout;
    boolean mLockScreenTimerActive;
    com.android.internal.logging.MetricsLogger mLogger;
    int mLongPressOnBackBehavior;
    long mLongPressOnPowerAssistantTimeoutMs;
    int mLongPressOnPowerBehavior;
    private int mLongPressOnStemPrimaryBehavior;
    private boolean mLongSwipeDown;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mMenuLongPressAction;
    private org.lineageos.internal.util.DeviceKeysConstants.Action mMenuPressAction;
    boolean mMenuPressed;
    com.android.server.policy.ModifierShortcutManager mModifierShortcutManager;
    android.content.pm.PackageManager mPackageManager;
    boolean mPendingCapsLockToggle;
    private boolean mPendingKeyguardOccluded;
    boolean mPendingMetaAction;
    volatile boolean mPictureInPictureVisible;
    android.content.ComponentName mPowerDoublePressTargetActivity;
    volatile boolean mPowerKeyHandled;
    android.os.PowerManager.WakeLock mPowerKeyWakeLock;
    android.os.PowerManager mPowerManager;
    android.os.PowerManagerInternal mPowerManagerInternal;
    int mPowerVolUpBehavior;
    boolean mPreloadedRecentApps;
    android.content.ComponentName mPrimaryShortPressTargetActivity;
    private java.lang.String mRearFlashCameraId;
    int mRecentAppsHeldModifiers;
    volatile boolean mRecentsVisible;
    volatile boolean mRequestedOrSleepingDefaultDisplay;
    private int mResolvedLongPressOnPowerBehavior;
    int mRingHomeBehavior;
    boolean mSafeMode;
    private com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer mScreenOffSleepTokenAcquirer;
    int mSearchKeyBehavior;
    android.content.ComponentName mSearchKeyTargetActivity;
    android.hardware.SensorPrivacyManager mSensorPrivacyManager;
    com.android.server.policy.PhoneWindowManager.SettingsObserver mSettingsObserver;
    int mShortPressOnPowerBehavior;
    int mShortPressOnSettingsBehavior;
    int mShortPressOnSleepBehavior;
    private int mShortPressOnStemPrimaryBehavior;
    int mShortPressOnWindowBehavior;
    private boolean mShouldEarlyShortPressOnPower;
    boolean mShouldEarlyShortPressOnStemPrimary;
    com.android.server.policy.SideFpsEventHandler mSideFpsEventHandler;
    boolean mSilenceRingerOnSleepKey;
    private com.android.server.policy.SingleKeyGestureDetector mSingleKeyGestureDetector;
    com.android.server.statusbar.StatusBarManagerInternal mStatusBarManagerInternal;
    com.android.internal.statusbar.IStatusBarService mStatusBarService;
    private boolean mSupportLongPressPowerWhenNonInteractive;
    private boolean mSupportShortPressPowerWhenDefaultDisplayOn;
    boolean mSystemBooted;
    boolean mSystemNavigationKeysEnabled;
    boolean mSystemReady;
    private com.android.server.policy.TalkbackShortcutController mTalkbackShortcutController;
    private boolean mTorchEnabled;
    private boolean mTorchLongPressPowerEnabled;
    private android.app.PendingIntent mTorchOffPendingIntent;
    private int mTorchTimeout;
    int mTriplePressOnPowerBehavior;
    private int mTriplePressOnStemPrimaryBehavior;
    int mUiMode;
    android.app.IUiModeManager mUiModeManager;
    boolean mUseTvRouting;
    com.android.server.pm.UserManagerInternal mUserManagerInternal;
    int mVeryLongPressOnPowerBehavior;
    android.os.Vibrator mVibrator;
    boolean mVolumeAnswerCall;
    boolean mVolumeDownWakeTriggered;
    boolean mVolumeMuteWakeTriggered;
    boolean mVolumeUpWakeTriggered;
    android.content.Intent mVrHeadsetHomeIntent;
    volatile com.android.server.vr.VrManagerInternal mVrManagerInternal;
    boolean mWakeGestureEnabledSetting;
    com.android.server.policy.PhoneWindowManager.MyWakeGestureListener mWakeGestureListener;
    boolean mWakeOnAppSwitchKeyPress;
    boolean mWakeOnAssistKeyPress;
    boolean mWakeOnBackKeyPress;
    boolean mWakeOnCameraKeyPress;
    boolean mWakeOnDpadKeyPress;
    boolean mWakeOnHomeKeyPress;
    boolean mWakeOnMenuKeyPress;
    boolean mWakeOnVolumeKeyPress;
    long mWakeUpToLastStateTimeout;
    private com.android.server.wallpaper.WallpaperManagerInternal mWallpaperManagerInternal;
    com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;
    com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private com.android.server.policy.WindowWakeUpPolicy mWindowWakeUpPolicy;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseArray<com.android.server.policy.WindowManagerPolicy.ScreenOnListener> mScreenOnListeners = new android.util.SparseArray<>();
    final java.lang.Object mServiceAcquireLock = new java.lang.Object();
    boolean mEnableShiftMenuBugReports = false;
    private boolean mEnableCarDockHomeCapture = true;
    final com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener mKeyguardDrawnCallback = new com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener() { // from class: com.android.server.policy.PhoneWindowManager.1
        @Override // com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener
        public void onDrawn() {
            com.android.server.policy.PhoneWindowManager.this.mHandler.sendEmptyMessage(5);
        }
    };
    volatile boolean mNavBarVirtualKeyHapticFeedbackEnabled = true;
    volatile int mPendingWakeKey = -1;
    int mCameraLensCoverState = -1;
    boolean mStylusButtonsEnabled = true;
    boolean mHasSoftInput = false;
    private int mForceNavbar = -1;
    private java.util.HashSet<java.lang.Integer> mAllowLockscreenWhenOnDisplays = new java.util.HashSet<>();
    int mRingerToggleChord = 0;
    private final android.util.SparseArray<java.util.Set<java.lang.Integer>> mConsumedKeysForDevice = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.view.KeyCharacterMap.FallbackAction> mFallbackActions = new android.util.SparseArray<>();
    private final com.android.internal.policy.LogDecelerateInterpolator mLogDecelerateInterpolator = new com.android.internal.policy.LogDecelerateInterpolator(100, 0);
    private final com.android.server.policy.DeferredKeyActionExecutor mDeferredKeyActionExecutor = new com.android.server.policy.DeferredKeyActionExecutor();
    private volatile int mTopFocusedDisplayId = -1;
    private int mPowerButtonSuppressionDelayMillis = 800;
    private boolean mLockNowPending = false;
    private int mKeyguardDrawnTimeout = 1000;
    private final java.util.List<com.android.internal.os.DeviceKeyHandler> mDeviceKeyHandlers = new java.util.ArrayList();
    private android.os.UEventObserver mHDMIObserver = new android.os.UEventObserver() { // from class: com.android.server.policy.PhoneWindowManager.2
        public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
            com.android.server.policy.PhoneWindowManager.this.mDefaultDisplayPolicy.setHdmiPlugged("1".equals(uEvent.get("SWITCH_STATE")));
        }
    };
    final android.service.vr.IPersistentVrStateCallbacks mPersistentVrModeListener = new android.service.vr.IPersistentVrStateCallbacks.Stub() { // from class: com.android.server.policy.PhoneWindowManager.3
        public void onPersistentVrStateChanged(boolean z) {
            com.android.server.policy.PhoneWindowManager.this.mDefaultDisplayPolicy.setPersistentVrModeEnabled(z);
        }
    };
    private final java.lang.Runnable mEndCallLongPress = new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager.4
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.policy.PhoneWindowManager.this.mEndCallKeyHandled = true;
            com.android.server.policy.PhoneWindowManager.this.performHapticFeedback(0, false, "End Call - Long Press - Show Global Actions");
            com.android.server.policy.PhoneWindowManager.this.showGlobalActionsInternal();
        }
    };
    private final android.util.SparseArray<com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler> mDisplayHomeButtonHandlers = new android.util.SparseArray<>();
    android.content.BroadcastReceiver mDockReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManager.14
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                com.android.server.policy.PhoneWindowManager.this.mDefaultDisplayPolicy.setDockMode(intent.getIntExtra("android.intent.extra.DOCK_STATE", 0));
            } else {
                try {
                    android.app.IUiModeManager asInterface = android.app.IUiModeManager.Stub.asInterface(android.os.ServiceManager.getService("uimode"));
                    com.android.server.policy.PhoneWindowManager.this.mUiMode = asInterface.getCurrentModeType();
                } catch (android.os.RemoteException e) {
                }
            }
            com.android.server.policy.PhoneWindowManager.this.updateRotation(true);
            com.android.server.policy.PhoneWindowManager.this.mDefaultDisplayRotation.updateOrientationListener();
        }
    };
    android.content.BroadcastReceiver mMultiuserReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManager.15
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                com.android.server.policy.PhoneWindowManager.this.mSettingsObserver.onChange(false);
                com.android.server.policy.PhoneWindowManager.this.mDefaultDisplayRotation.onUserSwitch();
                com.android.server.policy.PhoneWindowManager.this.mWindowManagerFuncs.onUserSwitched();
            }
        }
    };
    android.app.ProgressDialog mBootMsgDialog = null;
    final com.android.server.policy.PhoneWindowManager.ScreenLockTimeout mScreenLockTimeout = new com.android.server.policy.PhoneWindowManager.ScreenLockTimeout();

    private class PolicyHandler extends android.os.Handler {
        private PolicyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 3:
                    com.android.server.policy.PhoneWindowManager.this.dispatchMediaKeyWithWakeLock((android.view.KeyEvent) message.obj);
                    break;
                case 4:
                    com.android.server.policy.PhoneWindowManager.this.dispatchMediaKeyRepeatWithWakeLock((android.view.KeyEvent) message.obj);
                    break;
                case 5:
                    com.android.server.policy.PhoneWindowManager.this.finishKeyguardDrawn();
                    break;
                case 6:
                    android.util.Slog.w(com.android.server.policy.PhoneWindowManager.TAG, "Keyguard drawn timeout. Setting mKeyguardDrawComplete");
                    com.android.server.policy.PhoneWindowManager.this.finishKeyguardDrawn();
                    break;
                case 7:
                    int i = message.arg1;
                    android.os.Trace.asyncTraceEnd(32L, com.android.server.policy.PhoneWindowManager.TRACE_WAIT_FOR_ALL_WINDOWS_DRAWN_METHOD, i);
                    com.android.server.policy.PhoneWindowManager.this.finishWindowsDrawn(i);
                    break;
                case 9:
                    com.android.server.policy.PhoneWindowManager.this.showRecentApps(false);
                    break;
                case 10:
                    com.android.server.policy.PhoneWindowManager.this.showGlobalActionsInternal();
                    break;
                case 11:
                    com.android.server.policy.PhoneWindowManager.this.handleHideBootMessage();
                    break;
                case 12:
                    com.android.server.policy.PhoneWindowManager.this.launchVoiceAssistWithWakeLock();
                    break;
                case 15:
                    com.android.server.policy.PhoneWindowManager.this.showPictureInPictureMenuInternal();
                    break;
                case 16:
                    com.android.server.policy.PhoneWindowManager.this.handleScreenShot(message.arg1, message.arg2);
                    break;
                case 17:
                    com.android.server.policy.PhoneWindowManager.this.accessibilityShortcutActivated();
                    break;
                case 18:
                    com.android.server.policy.PhoneWindowManager.this.requestBugreportForTv();
                    break;
                case 19:
                    if (com.android.server.policy.PhoneWindowManager.this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(false)) {
                        com.android.server.policy.PhoneWindowManager.this.accessibilityShortcutActivated();
                        break;
                    }
                    break;
                case 20:
                    com.android.server.policy.PhoneWindowManager.this.mAutofillManagerInternal.onBackKeyPressed();
                    break;
                case 21:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) message.obj;
                    com.android.server.policy.PhoneWindowManager.this.sendSystemKeyToStatusBar(keyEvent);
                    keyEvent.recycle();
                    break;
                case 22:
                    com.android.server.policy.PhoneWindowManager.this.launchAllAppsAction();
                    break;
                case 23:
                    com.android.server.policy.PhoneWindowManager.this.launchAssistAction(null, message.arg1, ((java.lang.Long) message.obj).longValue(), 7);
                    break;
                case 24:
                    com.android.server.policy.PhoneWindowManager.this.handleRingerChordGesture();
                    break;
                case 25:
                    com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject switchKeyboardLayoutMessageObject = (com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject) message.obj;
                    com.android.server.policy.PhoneWindowManager.this.handleSwitchKeyboardLayout(switchKeyboardLayoutMessageObject.keyEvent, switchKeyboardLayoutMessageObject.direction, switchKeyboardLayoutMessageObject.focusedToken);
                    break;
                case 26:
                    com.android.server.policy.PhoneWindowManager.this.handleKeyboardSystemEvent(com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.from(message.arg1), (android.view.KeyEvent) message.obj);
                    break;
                case 27:
                    com.android.server.policy.PhoneWindowManager.this.mDeferredKeyActionExecutor.setActionsExecutable(message.arg1, ((java.lang.Long) message.obj).longValue());
                    break;
                case 100:
                    com.android.server.policy.PhoneWindowManager.this.toggleTorch();
                    break;
                case 101:
                    com.android.server.policy.PhoneWindowManager.this.mIsLongPress = true;
                    break;
            }
        }
    }

    class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        void observe() {
            android.content.ContentResolver contentResolver = com.android.server.policy.PhoneWindowManager.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("end_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("incall_power_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("incall_back_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("camera_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("camera_sleep_on_release"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("camera_launch"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.Secure.getUriFor("ring_home_button_behavior"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("wake_gesture_enabled"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("screen_off_timeout"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("default_input_method"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("volume_hush_gesture"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("system_navigation_keys_enabled"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_short_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_double_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_triple_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_long_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_long_press_duration_ms"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_very_long_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("stem_primary_button_short_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("stem_primary_button_double_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("stem_primary_button_triple_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("stem_primary_button_long_press"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("key_chord_power_volume_up"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("power_button_suppression_delay_after_gesture_wake"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("stylus_buttons_enabled"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("nav_bar_kids_mode"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("torch_long_press_power_gesture"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("torch_long_press_power_timeout"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("click_partial_screenshot"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_back_long_press_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_home_long_press_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_home_double_tap_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("force_show_navbar"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_menu_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_menu_long_press_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_assist_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_assist_long_press_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_app_switch_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_app_switch_long_press_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("key_edge_long_swipe_action"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("home_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("back_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("menu_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("assist_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("app_switch_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("volume_wake_screen"), false, this, -1);
            contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("volume_answer_call"), false, this, -1);
            com.android.server.policy.PhoneWindowManager.this.updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.policy.PhoneWindowManager.this.updateSettings();
        }
    }

    class MyWakeGestureListener extends com.android.server.policy.WakeGestureListener {
        MyWakeGestureListener(android.content.Context context, android.os.Handler handler) {
            super(context, handler);
        }

        @Override // com.android.server.policy.WakeGestureListener
        public void onWakeUp() {
            synchronized (com.android.server.policy.PhoneWindowManager.this.mLock) {
                try {
                    if (com.android.server.policy.PhoneWindowManager.this.shouldEnableWakeGestureLp()) {
                        com.android.server.policy.PhoneWindowManager.this.performHapticFeedback(1, false, "Wake Up");
                        com.android.server.policy.PhoneWindowManager.this.mWindowWakeUpPolicy.wakeUpFromWakeGesture();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class SwitchKeyboardLayoutMessageObject extends java.lang.Record {
        private final int direction;
        private final android.os.IBinder focusedToken;
        private final android.view.KeyEvent keyEvent;

        private SwitchKeyboardLayoutMessageObject(android.view.KeyEvent keyEvent, android.os.IBinder focusedToken, int direction) {
            this.keyEvent = keyEvent;
            this.focusedToken = focusedToken;
            this.direction = direction;
        }

        public int direction() {
            return this.direction;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject.class, java.lang.Object.class), com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject.class, "keyEvent;focusedToken;direction", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->keyEvent:Landroid/view/KeyEvent;", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->focusedToken:Landroid/os/IBinder;", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->direction:I").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        public android.os.IBinder focusedToken() {
            return this.focusedToken;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject.class), com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject.class, "keyEvent;focusedToken;direction", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->keyEvent:Landroid/view/KeyEvent;", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->focusedToken:Landroid/os/IBinder;", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->direction:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public android.view.KeyEvent keyEvent() {
            return this.keyEvent;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject.class), com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject.class, "keyEvent;focusedToken;direction", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->keyEvent:Landroid/view/KeyEvent;", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->focusedToken:Landroid/os/IBinder;", "FIELD:Lcom/android/server/policy/PhoneWindowManager$SwitchKeyboardLayoutMessageObject;->direction:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRingerChordGesture() {
        if (this.mRingerToggleChord == 0) {
            return;
        }
        getAudioManagerInternal();
        this.mAudioManagerInternal.silenceRingerModeInternal("volume_hush");
        android.provider.Settings.Secure.putInt(this.mContext.getContentResolver(), "hush_gesture_used", 1);
        this.mLogger.action(1440, this.mRingerToggleChord);
    }

    com.android.internal.statusbar.IStatusBarService getStatusBarService() {
        com.android.internal.statusbar.IStatusBarService iStatusBarService;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mStatusBarService == null) {
                    this.mStatusBarService = com.android.internal.statusbar.IStatusBarService.Stub.asInterface(android.os.ServiceManager.getService("statusbar"));
                }
                iStatusBarService = this.mStatusBarService;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return iStatusBarService;
    }

    com.android.server.statusbar.StatusBarManagerInternal getStatusBarManagerInternal() {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mStatusBarManagerInternal == null) {
                    this.mStatusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
                }
                statusBarManagerInternal = this.mStatusBarManagerInternal;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return statusBarManagerInternal;
    }

    android.media.AudioManagerInternal getAudioManagerInternal() {
        android.media.AudioManagerInternal audioManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mAudioManagerInternal == null) {
                    this.mAudioManagerInternal = (android.media.AudioManagerInternal) com.android.server.LocalServices.getService(android.media.AudioManagerInternal.class);
                }
                audioManagerInternal = this.mAudioManagerInternal;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return audioManagerInternal;
    }

    com.android.server.AccessibilityManagerInternal getAccessibilityManagerInternal() {
        com.android.server.AccessibilityManagerInternal accessibilityManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mAccessibilityManagerInternal == null) {
                    this.mAccessibilityManagerInternal = (com.android.server.AccessibilityManagerInternal) com.android.server.LocalServices.getService(com.android.server.AccessibilityManagerInternal.class);
                }
                accessibilityManagerInternal = this.mAccessibilityManagerInternal;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return accessibilityManagerInternal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean backKeyPress() {
        android.telecom.TelecomManager telecommService;
        this.mLogger.count("key_back_press", 1);
        boolean z = this.mBackKeyHandled;
        if (this.mHasFeatureWatch && (telecommService = getTelecommService()) != null) {
            if (telecommService.isRinging()) {
                telecommService.silenceRinger();
                return false;
            }
            if ((1 & this.mIncallBackBehavior) != 0 && telecommService.isInCall()) {
                return telecommService.endCall();
            }
        }
        if (this.mAutofillManagerInternal != null) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(20));
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void interceptPowerKeyDown(android.view.KeyEvent keyEvent, boolean z) {
        boolean z2;
        if (!this.mPowerKeyWakeLock.isHeld()) {
            this.mPowerKeyWakeLock.acquire();
        }
        this.mWindowManagerFuncs.onPowerKeyDown(z);
        android.telecom.TelecomManager telecommService = getTelecommService();
        if (telecommService != null) {
            if (telecommService.isRinging()) {
                telecommService.silenceRinger();
            } else if ((this.mIncallPowerBehavior & 2) != 0 && telecommService.isInCall() && z) {
                z2 = telecommService.endCall();
                boolean interceptPowerKeyDown = this.mPowerManagerInternal.interceptPowerKeyDown(keyEvent);
                sendSystemKeyToStatusBarAsync(keyEvent);
                this.mPowerKeyHandled = !this.mPowerKeyHandled || z2 || interceptPowerKeyDown || this.mKeyCombinationManager.isPowerKeyIntercepted();
                if (this.mPowerKeyHandled) {
                    this.mResolvedLongPressOnPowerBehavior = getResolvedLongPressOnPowerBehavior();
                    if (!z) {
                        if ((keyEvent.getFlags() & 128) != 0) {
                            wakeUpFromWakeKey(keyEvent);
                            return;
                        } else {
                            if (this.mSupportLongPressPowerWhenNonInteractive && hasLongPressOnPowerBehavior() && this.mResolvedLongPressOnPowerBehavior != 6) {
                                wakeUpFromWakeKey(keyEvent);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (this.mSingleKeyGestureDetector.isKeyIntercepted(26)) {
                    android.util.Slog.d(TAG, "Skip power key gesture for other policy has handled it.");
                    this.mSingleKeyGestureDetector.reset();
                    return;
                }
                return;
            }
        }
        z2 = false;
        boolean interceptPowerKeyDown2 = this.mPowerManagerInternal.interceptPowerKeyDown(keyEvent);
        sendSystemKeyToStatusBarAsync(keyEvent);
        this.mPowerKeyHandled = !this.mPowerKeyHandled || z2 || interceptPowerKeyDown2 || this.mKeyCombinationManager.isPowerKeyIntercepted();
        if (this.mPowerKeyHandled) {
        }
    }

    private void interceptPowerKeyUp(android.view.KeyEvent keyEvent, boolean z) {
        sendSystemKeyToStatusBarAsync(keyEvent);
        if (!(z || this.mPowerKeyHandled) && (keyEvent.getFlags() & 128) == 0) {
            android.os.Handler handler = this.mHandler;
            final com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs = this.mWindowManagerFuncs;
            java.util.Objects.requireNonNull(windowManagerFuncs);
            handler.post(new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs.this.triggerAnimationFailsafe();
                }
            });
            if (this.mResolvedLongPressOnPowerBehavior == 6 && (!isScreenOn() || isDozeMode())) {
                wakeUpFromWakeKey(keyEvent);
            }
        }
        finishPowerKeyPress();
    }

    private void finishPowerKeyPress() {
        this.mPowerKeyHandled = false;
        if (this.mPowerKeyWakeLock.isHeld()) {
            this.mPowerKeyWakeLock.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void powerPress(final long j, int i, int i2) {
        if (i == 1) {
            this.mSideFpsEventHandler.notifyPowerPressed();
        }
        if (this.mDefaultDisplayPolicy.isScreenOnEarly() && !this.mDefaultDisplayPolicy.isScreenOnFully()) {
            android.util.Slog.i(TAG, "Suppressed redundant power key press while already in the process of turning the screen on.");
        }
        boolean isAwake = this.mDefaultDisplayPolicy.isAwake();
        android.util.Slog.d(TAG, "powerPress: eventTime=" + j + " interactive=" + isAwake + " count=" + i + " mShortPressOnPowerBehavior=" + this.mShortPressOnPowerBehavior);
        if (i == 2) {
            powerMultiPressAction(j, isAwake, this.mDoublePressOnPowerBehavior);
            return;
        }
        if (i == 3) {
            powerMultiPressAction(j, isAwake, this.mTriplePressOnPowerBehavior);
            return;
        }
        if (i > 3 && i <= getMaxMultiPressPowerCount()) {
            android.util.Slog.d(TAG, "No behavior defined for power press count " + i);
            return;
        }
        if (i == 1 && shouldHandleShortPressPowerAction(isAwake, j)) {
            switch (this.mShortPressOnPowerBehavior) {
                case 1:
                    sleepDefaultDisplayFromPowerButton(j, 0);
                    break;
                case 2:
                    sleepDefaultDisplayFromPowerButton(j, 1);
                    break;
                case 3:
                    if (sleepDefaultDisplayFromPowerButton(j, 1)) {
                        launchHomeFromHotKey(0);
                        break;
                    }
                    break;
                case 4:
                    shortPressPowerGoHome();
                    break;
                case 5:
                    if (this.mDismissImeOnBackKeyPressed) {
                        com.android.server.inputmethod.InputMethodManagerInternal.get().hideAllInputMethods(17, i2);
                        break;
                    } else {
                        shortPressPowerGoHome();
                        break;
                    }
                case 6:
                    if (this.mKeyguardDelegate == null || !this.mKeyguardDelegate.hasKeyguard() || !this.mKeyguardDelegate.isSecure(this.mCurrentUserId) || keyguardOn()) {
                        sleepDefaultDisplayFromPowerButton(j, 0);
                        break;
                    } else {
                        lockNow(null);
                        break;
                    }
                    break;
                case 7:
                    attemptToDreamFromShortPowerButtonPress(true, new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.policy.PhoneWindowManager.this.lambda$powerPress$0(j);
                        }
                    });
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$powerPress$0(long j) {
        sleepDefaultDisplayFromPowerButton(j, 0);
    }

    private boolean shouldHandleShortPressPowerAction(boolean z, long j) {
        if (this.mSupportShortPressPowerWhenDefaultDisplayOn) {
            boolean isOnState = android.view.Display.isOnState(this.mDefaultDisplay.getState());
            boolean beganFromDefaultDisplayOn = this.mSingleKeyGestureDetector.beganFromDefaultDisplayOn();
            if (isOnState && beganFromDefaultDisplayOn) {
                return true;
            }
            android.util.Slog.v(TAG, "Ignoring short press of power button because the default display is not on. defaultDisplayOn=" + isOnState + ", beganFromDefaultDisplayOn=" + beganFromDefaultDisplayOn);
            return false;
        }
        boolean beganFromNonInteractive = this.mSingleKeyGestureDetector.beganFromNonInteractive();
        if (!z || beganFromNonInteractive) {
            android.util.Slog.v(TAG, "Ignoring short press of power button because the device is not interactive. interactive=" + z + ", beganFromNonInteractive=" + beganFromNonInteractive);
            return false;
        }
        if (!this.mSideFpsEventHandler.shouldConsumeSinglePress(j)) {
            return true;
        }
        android.util.Slog.i(TAG, "Suppressing power key because the user is interacting with the fingerprint sensor");
        return false;
    }

    private void attemptToDreamFromShortPowerButtonPress(boolean z, java.lang.Runnable runnable) {
        if (this.mShortPressOnPowerBehavior != 7) {
            runnable.run();
            return;
        }
        android.service.dreams.DreamManagerInternal dreamManagerInternal = getDreamManagerInternal();
        if (dreamManagerInternal == null || !dreamManagerInternal.canStartDreaming(z)) {
            android.util.Slog.d(TAG, "Can't start dreaming when attempting to dream from short power press (isScreenOn=" + z + ")");
            runnable.run();
            return;
        }
        synchronized (this.mLock) {
            this.mLockAfterDreamingTransitionFinished = this.mLockPatternUtils.getPowerButtonInstantlyLocks(this.mCurrentUserId);
        }
        dreamManagerInternal.requestDream();
    }

    private boolean sleepDefaultDisplayFromPowerButton(long j, int i) {
        android.os.PowerManager.WakeData lastWakeup = this.mPowerManagerInternal.getLastWakeup();
        if (lastWakeup != null && (lastWakeup.wakeReason == 4 || lastWakeup.wakeReason == 16 || lastWakeup.wakeReason == 17)) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (this.mPowerButtonSuppressionDelayMillis > 0 && uptimeMillis < lastWakeup.wakeTime + this.mPowerButtonSuppressionDelayMillis) {
                android.util.Slog.i(TAG, "Sleep from power button suppressed. Time since gesture: " + (uptimeMillis - lastWakeup.wakeTime) + "ms");
                return false;
            }
        }
        sleepDefaultDisplay(j, 4, i);
        return true;
    }

    private void sleepDefaultDisplay(long j, int i, int i2) {
        this.mRequestedOrSleepingDefaultDisplay = true;
        this.mPowerManager.goToSleep(j, i, i2);
    }

    private void shortPressPowerGoHome() {
        launchHomeFromHotKey(0, true, false);
        if (isKeyguardShowingAndNotOccluded()) {
            this.mKeyguardDelegate.onShortPowerPressedGoHome();
        }
    }

    private void powerMultiPressAction(long j, boolean z, int i) {
        switch (i) {
            case 1:
                if (!isUserSetupComplete()) {
                    android.util.Slog.i(TAG, "Ignoring toggling theater mode - device not setup.");
                    break;
                } else if (isTheaterModeEnabled()) {
                    android.util.Slog.i(TAG, "Toggling theater mode off.");
                    android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "theater_mode_on", 0);
                    if (!z) {
                        wakeUpFromWakeKey(j, 26, false);
                        break;
                    }
                } else {
                    android.util.Slog.i(TAG, "Toggling theater mode on.");
                    android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "theater_mode_on", 1);
                    if (this.mGoToSleepOnButtonPressTheaterMode && z) {
                        sleepDefaultDisplay(j, 4, 0);
                        break;
                    }
                }
                break;
            case 2:
                android.util.Slog.i(TAG, "Starting brightness boost.");
                if (!z) {
                    wakeUpFromWakeKey(j, 26, false);
                }
                this.mPowerManager.boostScreenBrightness(j);
                break;
            case 3:
                launchTargetActivityOnMultiPressPower();
                break;
        }
    }

    private void launchTargetActivityOnMultiPressPower() {
        if (this.mPowerDoublePressTargetActivity != null) {
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(this.mPowerDoublePressTargetActivity);
            boolean z = false;
            if (this.mContext.getPackageManager().resolveActivity(intent, 0) != null) {
                if (this.mKeyguardDelegate != null && this.mKeyguardDelegate.isShowing()) {
                    z = true;
                }
                intent.addFlags(270532608);
                if (!z) {
                    startActivityAsUser(intent, android.os.UserHandle.CURRENT_OR_SELF);
                    return;
                } else {
                    this.mKeyguardDelegate.dismissKeyguardToLaunch(intent);
                    return;
                }
            }
            android.util.Slog.e(TAG, "Could not resolve activity with : " + this.mPowerDoublePressTargetActivity.flattenToString() + " name.");
        }
    }

    private int getLidBehavior() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "lid_behavior", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMaxMultiPressPowerCount() {
        if (this.mHasFeatureWatch && com.android.server.GestureLauncherService.isEmergencyGestureSettingEnabled(this.mContext, android.app.ActivityManager.getCurrentUser())) {
            return 5;
        }
        if (this.mTriplePressOnPowerBehavior != 0) {
            return 3;
        }
        if (this.mDoublePressOnPowerBehavior != 0) {
            return 2;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void powerLongPress(long j) {
        int i = this.mResolvedLongPressOnPowerBehavior;
        android.util.Slog.d(TAG, "powerLongPress: eventTime=" + j + " mResolvedLongPressOnPowerBehavior=" + this.mResolvedLongPressOnPowerBehavior);
        switch (i) {
            case 1:
                this.mPowerKeyHandled = true;
                performHapticFeedback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, false, "Power - Long Press - Global Actions");
                showGlobalActions();
                break;
            case 2:
            case 3:
                this.mPowerKeyHandled = true;
                if (!android.app.ActivityManager.isUserAMonkey()) {
                    performHapticFeedback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, false, "Power - Long Press - Shut Off");
                    sendCloseSystemWindows(SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS);
                    this.mWindowManagerFuncs.shutdown(i == 2);
                    break;
                }
                break;
            case 4:
                this.mPowerKeyHandled = true;
                performHapticFeedback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, false, "Power - Long Press - Go To Voice Assist");
                launchVoiceAssist(this.mAllowStartActivityForLongPressOnPowerDuringSetup);
                break;
            case 5:
                this.mPowerKeyHandled = true;
                performHapticFeedback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER, false, "Power - Long Press - Go To Assistant");
                launchAssistAction(null, -2, j, 6);
                break;
            case 6:
                this.mPowerKeyHandled = true;
                this.mHandler.removeMessages(100);
                android.os.Message obtainMessage = this.mHandler.obtainMessage(100);
                obtainMessage.setAsynchronous(true);
                obtainMessage.sendToTarget();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void powerVeryLongPress() {
        switch (this.mVeryLongPressOnPowerBehavior) {
            case 1:
                this.mPowerKeyHandled = true;
                performHapticFeedback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, false, "Power - Very Long Press - Show Global Actions");
                showGlobalActions();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backLongPress() {
        if (hasLongPressOnBackBehavior()) {
            this.mBackKeyHandled = true;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.KeyEvent keyEvent = new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, 4, 0, 0, -1, 0, 8, 257);
            performHapticFeedback(0, false, "Back - Long Press");
            performKeyAction(this.mBackLongPressAction, keyEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void accessibilityShortcutActivated() {
        this.mAccessibilityShortcutController.performAccessibilityShortcut();
    }

    private void sleepPress() {
        if (this.mShortPressOnSleepBehavior == 1) {
            launchHomeFromHotKey(0, false, true);
        }
    }

    private void sleepRelease(long j) {
        android.telecom.TelecomManager telecommService;
        if (this.mSilenceRingerOnSleepKey && (telecommService = getTelecommService()) != null && telecommService.isRinging()) {
            telecommService.silenceRinger();
            android.util.Slog.i(TAG, "sleepRelease() silence ringer");
        } else {
            switch (this.mShortPressOnSleepBehavior) {
                case 0:
                case 1:
                    android.util.Slog.i(TAG, "sleepRelease() calling goToSleep(GO_TO_SLEEP_REASON_SLEEP_BUTTON)");
                    sleepDefaultDisplay(j, 6, 0);
                    break;
            }
        }
    }

    private boolean hasAssistant() {
        return this.mAssistUtils.getAssistComponentForUser(this.mCurrentUserId) != null;
    }

    private boolean isDozeMode() {
        android.service.dreams.IDreamManager dreamManager = getDreamManager();
        if (dreamManager != null) {
            try {
                if (dreamManager.isDreaming()) {
                    return true;
                }
                return false;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException when checking if dreaming", e);
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getResolvedLongPressOnPowerBehavior() {
        if (android.os.FactoryTest.isLongPressOnPowerOffEnabled()) {
            return 3;
        }
        if (this.mTorchLongPressPowerEnabled && (!isScreenOn() || isDozeMode() || this.mTorchEnabled)) {
            return 6;
        }
        if (this.mLongPressOnPowerBehavior == 5 && (!isDeviceProvisioned() || !hasAssistant())) {
            return 1;
        }
        if (this.mLongPressOnPowerBehavior == 4 && !isLongPressToAssistantEnabled(this.mContext)) {
            return 0;
        }
        return this.mLongPressOnPowerBehavior;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stemPrimaryPress(int i) {
        if (i == 3) {
            stemPrimaryTriplePressAction(this.mTriplePressOnStemPrimaryBehavior);
        } else if (i == 2) {
            stemPrimaryDoublePressAction(this.mDoublePressOnStemPrimaryBehavior);
        } else if (i == 1) {
            stemPrimarySinglePressAction(this.mShortPressOnStemPrimaryBehavior);
        }
    }

    private void stemPrimarySinglePressAction(int i) {
        if (i == 0) {
        }
        if (this.mKeyguardDelegate != null && this.mKeyguardDelegate.isShowing()) {
            this.mKeyguardDelegate.onSystemKeyPressed(264);
            return;
        }
        switch (i) {
            case 1:
                android.content.Intent intent = new android.content.Intent("android.intent.action.ALL_APPS");
                intent.addFlags(270532608);
                startActivityAsUser(intent, android.os.UserHandle.CURRENT_OR_SELF);
                break;
            case 2:
                if (this.mPrimaryShortPressTargetActivity != null) {
                    android.content.Intent intent2 = new android.content.Intent();
                    intent2.setComponent(this.mPrimaryShortPressTargetActivity);
                    if (this.mContext.getPackageManager().resolveActivity(intent2, 0) != null) {
                        intent2.addFlags(270548992);
                        startActivityAsUser(intent2, android.os.UserHandle.CURRENT_OR_SELF);
                        break;
                    } else {
                        android.util.Slog.wtf(TAG, "Could not resolve activity with : " + this.mPrimaryShortPressTargetActivity.flattenToString() + " name.");
                        break;
                    }
                } else {
                    android.util.Slog.wtf(TAG, "mPrimaryShortPressTargetActivity must not be null and correctly specified");
                    break;
                }
        }
    }

    private void stemPrimaryDoublePressAction(int i) {
        boolean isShowing;
        switch (i) {
            case 1:
                if (this.mKeyguardDelegate == null) {
                    isShowing = false;
                } else {
                    isShowing = this.mKeyguardDelegate.isShowing();
                }
                if (!isShowing) {
                    performStemPrimaryDoublePressSwitchToRecentTask();
                    break;
                }
                break;
        }
    }

    private void stemPrimaryTriplePressAction(int i) {
        switch (i) {
            case 1:
                this.mTalkbackShortcutController.toggleTalkback(this.mCurrentUserId);
                if (this.mTalkbackShortcutController.isTalkBackShortcutGestureEnabled()) {
                    performHapticFeedback(16, false, "Stem primary - Triple Press - Toggle Accessibility");
                    break;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stemPrimaryLongPress(long j) {
        switch (this.mLongPressOnStemPrimaryBehavior) {
            case 1:
                launchAssistAction(null, -2, j, 0);
                break;
        }
    }

    void performStemPrimaryDoublePressSwitchToRecentTask() {
        android.app.ActivityManager.RecentTaskInfo recentTaskInfo = this.mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp;
        if (recentTaskInfo == null) {
            goHome();
            return;
        }
        try {
            this.mActivityManagerService.startActivityFromRecents(recentTaskInfo.persistentId, (android.os.Bundle) null);
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Failed to start task " + recentTaskInfo.persistentId + " from recents", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMaxMultiPressStemPrimaryCount() {
        switch (this.mTriplePressOnStemPrimaryBehavior) {
            case 1:
                if (this.mTalkbackShortcutController.isTalkBackShortcutGestureEnabled()) {
                    return 3;
                }
                break;
        }
        if (this.mDoublePressOnStemPrimaryBehavior != 0) {
            return 2;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasLongPressOnPowerBehavior() {
        return getResolvedLongPressOnPowerBehavior() != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasVeryLongPressOnPowerBehavior() {
        return this.mVeryLongPressOnPowerBehavior != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasLongPressOnBackBehavior() {
        return this.mBackLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasLongPressOnStemPrimaryBehavior() {
        return this.mLongPressOnStemPrimaryBehavior != 0;
    }

    private boolean hasStemPrimaryBehavior() {
        return getMaxMultiPressStemPrimaryCount() > 1 || hasLongPressOnStemPrimaryBehavior() || this.mShortPressOnStemPrimaryBehavior != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void interceptScreenshotChord(int i, long j) {
        interceptScreenshotChord(1, i, j);
    }

    private void interceptScreenshotChord(int i, int i2, long j) {
        this.mHandler.removeMessages(16);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(16, i2, i), j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void interceptAccessibilityShortcutChord() {
        this.mHandler.removeMessages(17);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(17), getAccessibilityShortcutTimeout());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void interceptRingerToggleChord() {
        this.mHandler.removeMessages(24);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(24), getRingerToggleChordDelay());
    }

    private long getAccessibilityShortcutTimeout() {
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(this.mContext);
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_shortcut_dialog_shown", 0, this.mCurrentUserId) != 0;
        boolean z2 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "skip_accessibility_shortcut_dialog_timeout_restriction", 0, this.mCurrentUserId) != 0;
        if (z || z2) {
            return viewConfiguration.getAccessibilityShortcutKeyTimeoutAfterConfirmation();
        }
        return viewConfiguration.getAccessibilityShortcutKeyTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getScreenshotChordLongPressDelay() {
        long max = java.lang.Long.max(this.mClickPartialScreenshot ? 500L : 0L, android.provider.DeviceConfig.getLong("systemui", "screenshot_keychord_delay", android.view.ViewConfiguration.get(this.mContext).getScreenshotChordKeyTimeout()));
        if (this.mKeyguardDelegate.isShowing()) {
            return (long) (max * KEYGUARD_SCREENSHOT_CHORD_DELAY_MULTIPLIER);
        }
        return max;
    }

    private long getRingerToggleChordDelay() {
        return android.view.ViewConfiguration.getTapTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPendingScreenshotChordAction() {
        boolean hasMessages = this.mHandler.hasMessages(16);
        this.mHandler.removeMessages(16);
        if (this.mClickPartialScreenshot && hasMessages) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(16, 5, 2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPendingAccessibilityShortcutAction() {
        this.mHandler.removeMessages(17);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPendingRingerToggleChordAction() {
        this.mHandler.removeMessages(24);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScreenShot(@android.view.WindowManager.ScreenshotSource int i, @android.view.WindowManager.ScreenshotType int i2) {
        this.mDefaultDisplayPolicy.takeScreenshot(i2, i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void showGlobalActions() {
        this.mHandler.removeMessages(10);
        this.mHandler.sendEmptyMessage(10);
    }

    void showGlobalActionsInternal() {
        if (this.mGlobalActions == null) {
            this.mGlobalActions = this.mGlobalActionsFactory.get();
        }
        this.mGlobalActions.showDialog(isKeyguardShowingAndNotOccluded(), isDeviceProvisioned());
        this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelGlobalActionsAction() {
        this.mHandler.removeMessages(10);
    }

    boolean isDeviceProvisioned() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isUserSetupComplete() {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) != 0;
        if (this.mHasFeatureLeanback) {
            return z & isTvUserSetupComplete();
        }
        if (this.mHasFeatureAuto) {
            return z & isAutoUserSetupComplete();
        }
        return z;
    }

    private boolean isAutoUserSetupComplete() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "android.car.SETUP_WIZARD_IN_PROGRESS", 0, -2) == 0;
    }

    private boolean isTvUserSetupComplete() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "tv_user_setup_complete", 0, -2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShortPressOnHome(android.view.KeyEvent keyEvent) {
        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.HOME);
        com.android.server.policy.PhoneWindowManager.HdmiControl hdmiControl = getHdmiControl();
        if (hdmiControl != null) {
            hdmiControl.turnOnTv();
        }
        android.service.dreams.DreamManagerInternal dreamManagerInternal = getDreamManagerInternal();
        if (dreamManagerInternal != null && dreamManagerInternal.isDreaming()) {
            this.mDreamManagerInternal.stopDream(false, "short press on home");
        } else {
            launchHomeFromHotKey(keyEvent.getDisplayId());
        }
    }

    private com.android.server.policy.PhoneWindowManager.HdmiControl getHdmiControl() {
        android.hardware.hdmi.HdmiPlaybackClient hdmiPlaybackClient;
        if (this.mHdmiControl == null) {
            if (!this.mHasFeatureHdmiCec) {
                return null;
            }
            android.hardware.hdmi.HdmiControlManager hdmiControlManager = (android.hardware.hdmi.HdmiControlManager) this.mContext.getSystemService("hdmi_control");
            if (hdmiControlManager == null) {
                hdmiPlaybackClient = null;
            } else {
                hdmiPlaybackClient = hdmiControlManager.getPlaybackClient();
            }
            this.mHdmiControl = new com.android.server.policy.PhoneWindowManager.HdmiControl(hdmiPlaybackClient);
        }
        return this.mHdmiControl;
    }

    private static class HdmiControl {
        private final android.hardware.hdmi.HdmiPlaybackClient mClient;

        private HdmiControl(android.hardware.hdmi.HdmiPlaybackClient hdmiPlaybackClient) {
            this.mClient = hdmiPlaybackClient;
        }

        public void turnOnTv() {
            if (this.mClient == null) {
                return;
            }
            this.mClient.oneTouchPlay(new android.hardware.hdmi.HdmiPlaybackClient.OneTouchPlayCallback() { // from class: com.android.server.policy.PhoneWindowManager.HdmiControl.1
                public void onComplete(int i) {
                    if (i != 0) {
                        android.util.Log.w(com.android.server.policy.PhoneWindowManager.TAG, "One touch play failed: " + i);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchAllAppsAction() {
        android.content.Intent intent = new android.content.Intent("android.intent.action.ALL_APPS");
        if (this.mHasFeatureLeanback) {
            android.content.Intent intent2 = new android.content.Intent("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.HOME");
            android.content.pm.ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(intent2, 1048576, this.mCurrentUserId);
            if (resolveActivityAsUser != null) {
                intent.setPackage(resolveActivityAsUser.activityInfo.packageName);
            }
        }
        startActivityAsUser(intent, android.os.UserHandle.CURRENT);
    }

    private void launchAllAppsViaA11y() {
        com.android.server.AccessibilityManagerInternal accessibilityManagerInternal = getAccessibilityManagerInternal();
        if (accessibilityManagerInternal != null) {
            accessibilityManagerInternal.performSystemAction(14);
        }
        dismissKeyboardShortcutsMenu();
    }

    private void toggleNotificationPanel() {
        com.android.internal.statusbar.IStatusBarService statusBarService = getStatusBarService();
        if (isUserSetupComplete() && statusBarService != null) {
            try {
                statusBarService.togglePanel();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void showSystemSettings() {
        startActivityAsUser(new android.content.Intent("android.settings.SETTINGS"), android.os.UserHandle.CURRENT_OR_SELF);
    }

    private void showPictureInPictureMenu(android.view.KeyEvent keyEvent) {
        this.mHandler.removeMessages(15);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(15);
        obtainMessage.setAsynchronous(true);
        obtainMessage.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPictureInPictureMenuInternal() {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.showPictureInPictureMenu();
        }
    }

    private void launchCameraAction() {
        sendCloseSystemWindows();
        this.mContext.sendBroadcast(new android.content.Intent("lineageos.intent.action.SCREEN_CAMERA_GESTURE"), "android.permission.STATUS_BAR_SERVICE");
    }

    private void triggerVirtualKeypress(int i) {
        android.hardware.input.InputManager inputManager = android.hardware.input.InputManager.getInstance();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.view.KeyEvent keyEvent = new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, i, 0, 0, -1, 0, 8, 257);
        android.view.KeyEvent changeAction = android.view.KeyEvent.changeAction(keyEvent, 1);
        inputManager.injectInputEvent(keyEvent, 0);
        inputManager.injectInputEvent(changeAction, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performKeyAction(org.lineageos.internal.util.DeviceKeysConstants.Action action, android.view.KeyEvent keyEvent) {
        performKeyAction(action, keyEvent, 0);
    }

    /* renamed from: com.android.server.policy.PhoneWindowManager$17, reason: invalid class name */
    static /* synthetic */ class AnonymousClass17 {
        static final /* synthetic */ int[] $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action = new int[org.lineageos.internal.util.DeviceKeysConstants.Action.values().length];

        static {
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.MENU.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.SEARCH.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.VOICE_SEARCH.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.IN_APP_SEARCH.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.LAUNCH_CAMERA.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.SLEEP.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.LAST_APP.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.SPLIT_SCREEN.ordinal()] = 10;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[org.lineageos.internal.util.DeviceKeysConstants.Action.KILL_APP.ordinal()] = 11;
            } catch (java.lang.NoSuchFieldError e11) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performKeyAction(org.lineageos.internal.util.DeviceKeysConstants.Action action, android.view.KeyEvent keyEvent, int i) {
        switch (com.android.server.policy.PhoneWindowManager.AnonymousClass17.$SwitchMap$org$lineageos$internal$util$DeviceKeysConstants$Action[action.ordinal()]) {
            case 2:
                triggerVirtualKeypress(82);
                break;
            case 3:
                toggleRecentApps();
                logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.APP_SWITCH);
                break;
            case 4:
                launchAssistAction(null, keyEvent.getDeviceId(), keyEvent.getEventTime(), i);
                logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LAUNCH_ASSISTANT);
                break;
            case 5:
                launchVoiceAssistWithWakeLock();
                break;
            case 6:
                triggerVirtualKeypress(84);
                break;
            case 7:
                launchCameraAction();
                break;
            case 8:
                this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis());
                break;
            case 9:
                org.lineageos.internal.util.ActionUtils.switchToLastApp(this.mContext, this.mCurrentUserId);
                break;
            case 10:
                toggleSplitScreen();
                break;
            case 11:
                org.lineageos.internal.util.ActionUtils.killForegroundApp(this.mContext, this.mCurrentUserId);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisplayHomeButtonHandler {
        private final int mDisplayId;
        private boolean mHomeConsumed;
        private final java.lang.Runnable mHomeDoubleTapTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler.1
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler.this.mPendingHomeKeyEvent != null) {
                    com.android.server.policy.PhoneWindowManager.this.handleShortPressOnHome(com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler.this.mPendingHomeKeyEvent);
                    com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler.this.mPendingHomeKeyEvent = null;
                }
            }
        };
        private boolean mHomePressed;
        private android.view.KeyEvent mPendingHomeKeyEvent;

        DisplayHomeButtonHandler(int i) {
            this.mDisplayId = i;
        }

        boolean handleHomeButton(android.os.IBinder iBinder, final android.view.KeyEvent keyEvent) {
            android.telecom.TelecomManager telecommService;
            boolean keyguardOn = com.android.server.policy.PhoneWindowManager.this.keyguardOn();
            int repeatCount = keyEvent.getRepeatCount();
            boolean z = keyEvent.getAction() == 0;
            boolean isCanceled = keyEvent.isCanceled();
            boolean z2 = (keyEvent.getFlags() & 128) != 0;
            if (!z) {
                if (this.mDisplayId == 0 && com.android.server.policy.PhoneWindowManager.this.mHomeDoubleTapAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                    com.android.server.policy.PhoneWindowManager.this.cancelPreloadRecentApps();
                }
                this.mHomePressed = false;
                if (this.mHomeConsumed) {
                    this.mHomeConsumed = false;
                    return true;
                }
                if (isCanceled) {
                    android.util.Log.i(com.android.server.policy.PhoneWindowManager.TAG, "Ignoring HOME; event canceled.");
                    return true;
                }
                if ((com.android.server.policy.PhoneWindowManager.this.mRingHomeBehavior & 2) != 0 && (telecommService = com.android.server.policy.PhoneWindowManager.this.getTelecommService()) != null && telecommService.isRinging()) {
                    telecommService.acceptRingingCall();
                    return true;
                }
                if (com.android.server.policy.PhoneWindowManager.this.mHomeDoubleTapAction != org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING) {
                    com.android.server.policy.PhoneWindowManager.this.mHandler.removeCallbacks(this.mHomeDoubleTapTimeoutRunnable);
                    this.mPendingHomeKeyEvent = keyEvent;
                    com.android.server.policy.PhoneWindowManager.this.mHandler.postDelayed(this.mHomeDoubleTapTimeoutRunnable, android.view.ViewConfiguration.getDoubleTapTimeout());
                    return true;
                }
                com.android.server.policy.PhoneWindowManager.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$DisplayHomeButtonHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler.this.lambda$handleHomeButton$0(keyEvent);
                    }
                });
                return true;
            }
            com.android.internal.policy.KeyInterceptionInfo keyInterceptionInfoFromToken = com.android.server.policy.PhoneWindowManager.this.mWindowManagerInternal.getKeyInterceptionInfoFromToken(iBinder);
            if (keyInterceptionInfoFromToken != null) {
                if (keyInterceptionInfoFromToken.layoutParamsType == 2009 || (keyInterceptionInfoFromToken.layoutParamsType == 2040 && com.android.server.policy.PhoneWindowManager.this.isKeyguardShowing())) {
                    return false;
                }
                for (int i : com.android.server.policy.PhoneWindowManager.WINDOW_TYPES_WHERE_HOME_DOESNT_WORK) {
                    if (keyInterceptionInfoFromToken.layoutParamsType == i) {
                        return true;
                    }
                }
            }
            if (repeatCount == 0) {
                this.mHomePressed = true;
                if (this.mPendingHomeKeyEvent != null) {
                    this.mPendingHomeKeyEvent = null;
                    com.android.server.policy.PhoneWindowManager.this.mHandler.removeCallbacks(this.mHomeDoubleTapTimeoutRunnable);
                    com.android.server.policy.PhoneWindowManager.this.performKeyAction(com.android.server.policy.PhoneWindowManager.this.mHomeDoubleTapAction, keyEvent);
                    if (com.android.server.policy.PhoneWindowManager.this.mHomeDoubleTapAction != org.lineageos.internal.util.DeviceKeysConstants.Action.SLEEP) {
                        this.mHomeConsumed = true;
                    }
                } else if (this.mDisplayId == 0 && (com.android.server.policy.PhoneWindowManager.this.mHomeLongPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH || com.android.server.policy.PhoneWindowManager.this.mHomeDoubleTapAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH)) {
                    com.android.server.policy.PhoneWindowManager.this.preloadRecentApps();
                }
            } else if (z2 && !keyguardOn && !this.mHomeConsumed && com.android.server.policy.PhoneWindowManager.this.mHomeLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING) {
                if (com.android.server.policy.PhoneWindowManager.this.mHomeLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                    com.android.server.policy.PhoneWindowManager.this.cancelPreloadRecentApps();
                }
                this.mHomePressed = true;
                com.android.server.policy.PhoneWindowManager.this.performHapticFeedback(0, false, "Home - Long Press");
                com.android.server.policy.PhoneWindowManager.this.performKeyAction(com.android.server.policy.PhoneWindowManager.this.mHomeLongPressAction, keyEvent, 5);
                if (com.android.server.policy.PhoneWindowManager.this.mHomeLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.SLEEP) {
                    this.mHomeConsumed = true;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleHomeButton$0(android.view.KeyEvent keyEvent) {
            com.android.server.policy.PhoneWindowManager.this.handleShortPressOnHome(keyEvent);
        }

        public java.lang.String toString() {
            return java.lang.String.format("mDisplayId = %d, mHomePressed = %b", java.lang.Integer.valueOf(this.mDisplayId), java.lang.Boolean.valueOf(this.mHomePressed));
        }
    }

    private boolean isRoundWindow() {
        return this.mContext.getResources().getConfiguration().isScreenRound();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setDefaultDisplay(com.android.server.policy.WindowManagerPolicy.DisplayContentInfo displayContentInfo) {
        this.mDefaultDisplay = displayContentInfo.getDisplay();
        this.mDefaultDisplayRotation = displayContentInfo.getDisplayRotation();
        this.mDefaultDisplayPolicy = this.mDefaultDisplayRotation.getDisplayPolicy();
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private final android.content.Context mContext;
        private final com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;

        Injector(android.content.Context context, com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
            this.mContext = context;
            this.mWindowManagerFuncs = windowManagerFuncs;
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs getWindowManagerFuncs() {
            return this.mWindowManagerFuncs;
        }

        android.os.Looper getLooper() {
            return android.os.Looper.myLooper();
        }

        com.android.internal.accessibility.AccessibilityShortcutController getAccessibilityShortcutController(android.content.Context context, android.os.Handler handler, int i) {
            return new com.android.internal.accessibility.AccessibilityShortcutController(context, handler, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.policy.GlobalActions lambda$getGlobalActionsFactory$0() {
            return new com.android.server.policy.GlobalActions(this.mContext, this.mWindowManagerFuncs);
        }

        java.util.function.Supplier<com.android.server.policy.GlobalActions> getGlobalActionsFactory() {
            return new java.util.function.Supplier() { // from class: com.android.server.policy.PhoneWindowManager$Injector$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.policy.GlobalActions lambda$getGlobalActionsFactory$0;
                    lambda$getGlobalActionsFactory$0 = com.android.server.policy.PhoneWindowManager.Injector.this.lambda$getGlobalActionsFactory$0();
                    return lambda$getGlobalActionsFactory$0;
                }
            };
        }

        com.android.server.policy.keyguard.KeyguardServiceDelegate getKeyguardServiceDelegate() {
            return new com.android.server.policy.keyguard.KeyguardServiceDelegate(this.mContext, new com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback() { // from class: com.android.server.policy.PhoneWindowManager.Injector.1
                @Override // com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback
                public void onTrustedChanged() {
                    com.android.server.policy.PhoneWindowManager.Injector.this.mWindowManagerFuncs.notifyKeyguardTrustedChanged();
                }

                @Override // com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback
                public void onShowingChanged() {
                    com.android.server.policy.PhoneWindowManager.Injector.this.mWindowManagerFuncs.onKeyguardShowingAndNotOccludedChanged();
                }
            });
        }

        android.app.IActivityManager getActivityManagerService() {
            return android.app.ActivityManager.getService();
        }

        com.android.server.policy.PhoneWindowManager.ButtonOverridePermissionChecker getButtonOverridePermissionChecker() {
            return new com.android.server.policy.PhoneWindowManager.ButtonOverridePermissionChecker();
        }

        com.android.server.policy.TalkbackShortcutController getTalkbackShortcutController() {
            return new com.android.server.policy.TalkbackShortcutController(this.mContext);
        }

        com.android.server.policy.WindowWakeUpPolicy getWindowWakeUpPolicy() {
            return new com.android.server.policy.WindowWakeUpPolicy(this.mContext);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void init(android.content.Context context, com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        init(new com.android.server.policy.PhoneWindowManager.Injector(context, windowManagerFuncs));
    }

    @com.android.internal.annotations.VisibleForTesting
    void init(com.android.server.policy.PhoneWindowManager.Injector injector) {
        int integer;
        int i;
        int i2;
        int i3;
        int i4;
        this.mContext = injector.getContext();
        this.mWindowManagerFuncs = injector.getWindowManagerFuncs();
        this.mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mActivityManagerService = injector.getActivityManagerService();
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mInputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        this.mInputManagerInternal = (com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class);
        this.mDreamManagerInternal = (android.service.dreams.DreamManagerInternal) com.android.server.LocalServices.getService(android.service.dreams.DreamManagerInternal.class);
        this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mSensorPrivacyManager = (android.hardware.SensorPrivacyManager) this.mContext.getSystemService(android.hardware.SensorPrivacyManager.class);
        this.mDisplayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mPackageManager = this.mContext.getPackageManager();
        this.mHasFeatureWatch = this.mPackageManager.hasSystemFeature("android.hardware.type.watch");
        this.mHasFeatureLeanback = this.mPackageManager.hasSystemFeature("android.software.leanback");
        this.mHasFeatureAuto = this.mPackageManager.hasSystemFeature("android.hardware.type.automotive");
        this.mHasFeatureHdmiCec = this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec");
        this.mAccessibilityShortcutController = injector.getAccessibilityShortcutController(this.mContext, new android.os.Handler(), this.mCurrentUserId);
        this.mGlobalActionsFactory = injector.getGlobalActionsFactory();
        this.mLockPatternUtils = new com.android.internal.widget.LockPatternUtils(this.mContext);
        this.mLogger = new com.android.internal.logging.MetricsLogger();
        this.mScreenOffSleepTokenAcquirer = this.mActivityTaskManagerInternal.createSleepTokenAcquirer("ScreenOff");
        android.content.res.Resources resources = this.mContext.getResources();
        this.mWakeOnDpadKeyPress = resources.getBoolean(android.R.bool.config_volume_down_to_enter_silent);
        boolean z = this.mContext.getResources().getBoolean(android.R.bool.config_enableBackSound);
        boolean z2 = android.os.SystemProperties.getBoolean("persist.debug.force_burn_in", false);
        if (z || z2) {
            if (z2) {
                integer = isRoundWindow() ? 6 : -1;
                i = -8;
                i3 = -8;
                i2 = 8;
                i4 = -4;
            } else {
                android.content.res.Resources resources2 = this.mContext.getResources();
                int integer2 = resources2.getInteger(android.R.integer.config_burnInProtectionMaxVerticalOffset);
                int integer3 = resources2.getInteger(android.R.integer.config_brightness_ramp_rate_slow);
                int integer4 = resources2.getInteger(android.R.integer.config_burnInProtectionMinHorizontalOffset);
                int integer5 = resources2.getInteger(android.R.integer.config_burnInProtectionMaxRadius);
                integer = resources2.getInteger(android.R.integer.config_burnInProtectionMaxHorizontalOffset);
                i = integer2;
                i2 = integer3;
                i3 = integer4;
                i4 = integer5;
            }
            this.mBurnInProtectionHelper = new com.android.server.policy.BurnInProtectionHelper(this.mContext, i, i2, i3, i4, integer);
        }
        this.mHandler = new com.android.server.policy.PhoneWindowManager.PolicyHandler(injector.getLooper());
        this.mWakeGestureListener = new com.android.server.policy.PhoneWindowManager.MyWakeGestureListener(this.mContext, this.mHandler);
        this.mSettingsObserver = new com.android.server.policy.PhoneWindowManager.SettingsObserver(this.mHandler);
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        this.mCameraManager = (android.hardware.camera2.CameraManager) this.mContext.getSystemService("camera");
        this.mCameraManager.registerTorchCallback(new com.android.server.policy.PhoneWindowManager.TorchModeCallback(), this.mHandler);
        this.mModifierShortcutManager = new com.android.server.policy.ModifierShortcutManager(this.mContext, this.mHandler);
        this.mUiMode = this.mContext.getResources().getInteger(android.R.integer.config_defaultRefreshRateInHbmHdr);
        this.mHomeIntent = new android.content.Intent("android.intent.action.MAIN", (android.net.Uri) null);
        this.mHomeIntent.addCategory("android.intent.category.HOME");
        this.mHomeIntent.addFlags(270532608);
        this.mEnableCarDockHomeCapture = this.mContext.getResources().getBoolean(android.R.bool.config_enableBurnInProtection);
        this.mCarDockIntent = new android.content.Intent("android.intent.action.MAIN", (android.net.Uri) null);
        this.mCarDockIntent.addCategory("android.intent.category.CAR_DOCK");
        this.mCarDockIntent.addFlags(270532608);
        this.mDeskDockIntent = new android.content.Intent("android.intent.action.MAIN", (android.net.Uri) null);
        this.mDeskDockIntent.addCategory("android.intent.category.DESK_DOCK");
        this.mDeskDockIntent.addFlags(270532608);
        this.mVrHeadsetHomeIntent = new android.content.Intent("android.intent.action.MAIN", (android.net.Uri) null);
        this.mVrHeadsetHomeIntent.addCategory("android.intent.category.VR_HOME");
        this.mVrHeadsetHomeIntent.addFlags(270532608);
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService("power");
        this.mBroadcastWakeLock = this.mPowerManager.newWakeLock(1, "PhoneWindowManager.mBroadcastWakeLock");
        this.mPowerKeyWakeLock = this.mPowerManager.newWakeLock(1, "PhoneWindowManager.mPowerKeyWakeLock");
        this.mEnableShiftMenuBugReports = "1".equals(android.os.SystemProperties.get("ro.debuggable"));
        this.mLidKeyboardAccessibility = this.mContext.getResources().getInteger(android.R.integer.config_letterboxDefaultPositionForBookModeReachability);
        this.mLidNavigationAccessibility = this.mContext.getResources().getInteger(android.R.integer.config_letterboxDefaultPositionForHorizontalReachability);
        this.mGoToSleepOnButtonPressTheaterMode = this.mContext.getResources().getBoolean(android.R.bool.config_forceOrientationListenerEnabledWhileDreaming);
        this.mSupportLongPressPowerWhenNonInteractive = this.mContext.getResources().getBoolean(android.R.bool.config_supportAudioSourceUnprocessed);
        this.mSupportShortPressPowerWhenDefaultDisplayOn = this.mContext.getResources().getBoolean(android.R.bool.config_supportLongPressPowerWhenNonInteractive);
        this.mLongPressOnBackBehavior = this.mContext.getResources().getInteger(android.R.integer.config_lidNavigationAccessibility);
        this.mLongPressOnPowerBehavior = this.mContext.getResources().getInteger(android.R.integer.config_lightSensorWarmupTime);
        this.mLongPressOnPowerAssistantTimeoutMs = this.mContext.getResources().getInteger(android.R.integer.config_lockSoundVolumeDb);
        this.mVeryLongPressOnPowerBehavior = this.mContext.getResources().getInteger(android.R.integer.config_toastDefaultGravity);
        this.mPowerDoublePressTargetActivity = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_deviceProvisioningPackage));
        this.mPrimaryShortPressTargetActivity = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_onDeviceIntelligenceModelUnloadedBroadcastKey));
        this.mShortPressOnSleepBehavior = this.mContext.getResources().getInteger(android.R.integer.config_screen_rotation_fade_out);
        this.mSilenceRingerOnSleepKey = this.mContext.getResources().getBoolean(android.R.bool.config_showSysuiShutdown);
        this.mAllowStartActivityForLongPressOnPowerDuringSetup = this.mContext.getResources().getBoolean(android.R.bool.config_allowStartActivityForLongPressOnPowerInSetup);
        this.mUseTvRouting = android.media.AudioSystem.getPlatformType(this.mContext) == 2;
        this.mHandleVolumeKeysInWM = this.mContext.getResources().getBoolean(android.R.bool.config_guestUserAllowEphemeralStateChange);
        this.mWakeUpToLastStateTimeout = this.mContext.getResources().getInteger(android.R.integer.config_vibrationWaveformRampDownDuration);
        this.mSearchKeyBehavior = this.mContext.getResources().getInteger(android.R.integer.config_screen_magnification_multi_tap_adjustment);
        this.mSearchKeyTargetActivity = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_rearDisplayPhysicalAddress));
        this.mDeviceHardwareKeys = this.mContext.getResources().getInteger(1057423369);
        updateKeyAssignments();
        this.mDisplayFoldController = com.android.server.policy.DisplayFoldController.create(this.mContext, 0);
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService("accessibility");
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.app.UiModeManager.ACTION_ENTER_CAR_MODE);
        intentFilter.addAction(android.app.UiModeManager.ACTION_EXIT_CAR_MODE);
        intentFilter.addAction(android.app.UiModeManager.ACTION_ENTER_DESK_MODE);
        intentFilter.addAction(android.app.UiModeManager.ACTION_EXIT_DESK_MODE);
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        android.content.Intent registerReceiver = this.mContext.registerReceiver(this.mDockReceiver, intentFilter);
        if (registerReceiver != null) {
            this.mDefaultDisplayPolicy.setDockMode(registerReceiver.getIntExtra("android.intent.extra.DOCK_STATE", 0));
        }
        this.mContext.registerReceiver(this.mMultiuserReceiver, new android.content.IntentFilter("android.intent.action.USER_SWITCHED"));
        this.mVibrator = (android.os.Vibrator) this.mContext.getSystemService("vibrator");
        this.mHapticFeedbackVibrationProvider = new com.android.server.vibrator.HapticFeedbackVibrationProvider(this.mContext.getResources(), this.mVibrator);
        this.mAssistUtils = new com.android.internal.app.AssistUtils(this.mContext);
        this.mGlobalKeyManager = new com.android.server.policy.GlobalKeyManager(this.mContext);
        initializeHdmiState();
        if (!this.mPowerManager.isInteractive()) {
            startedGoingToSleep(0, 2);
            finishedGoingToSleep(0, 2);
        }
        this.mWindowManagerInternal.registerAppTransitionListener(new com.android.server.wm.WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.policy.PhoneWindowManager.5
            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public int onAppTransitionStartingLocked(long j, long j2) {
                return com.android.server.policy.PhoneWindowManager.this.handleTransitionForKeyguardLw(false, false);
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionCancelledLocked(boolean z3) {
                com.android.server.policy.PhoneWindowManager.this.handleTransitionForKeyguardLw(z3, true);
                synchronized (com.android.server.policy.PhoneWindowManager.this.mLock) {
                    com.android.server.policy.PhoneWindowManager.this.mLockAfterDreamingTransitionFinished = false;
                }
            }

            @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
            public void onAppTransitionFinishedLocked(android.os.IBinder iBinder) {
                synchronized (com.android.server.policy.PhoneWindowManager.this.mLock) {
                    try {
                        android.service.dreams.DreamManagerInternal dreamManagerInternal = com.android.server.policy.PhoneWindowManager.this.getDreamManagerInternal();
                        if (dreamManagerInternal != null && dreamManagerInternal.isDreaming() && com.android.server.policy.PhoneWindowManager.this.mLockAfterDreamingTransitionFinished) {
                            com.android.server.policy.PhoneWindowManager.this.lockNow(null);
                        }
                        com.android.server.policy.PhoneWindowManager.this.mLockAfterDreamingTransitionFinished = false;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        });
        this.mKeyguardDrawnTimeout = this.mContext.getResources().getInteger(android.R.integer.config_jobSchedulerInactivityIdleThresholdOnStablePower);
        this.mKeyguardDelegate = injector.getKeyguardServiceDelegate();
        this.mTalkbackShortcutController = injector.getTalkbackShortcutController();
        this.mWindowWakeUpPolicy = injector.getWindowWakeUpPolicy();
        initKeyCombinationRules();
        initSingleKeyGestureRules(injector.getLooper());
        this.mButtonOverridePermissionChecker = injector.getButtonOverridePermissionChecker();
        this.mSideFpsEventHandler = new com.android.server.policy.SideFpsEventHandler(this.mContext, this.mHandler, this.mPowerManager);
        java.lang.String[] stringArray = resources.getStringArray(1057095682);
        java.lang.String[] stringArray2 = resources.getStringArray(1057095681);
        for (int i5 = 0; i5 < stringArray.length && i5 < stringArray2.length; i5++) {
            try {
                this.mDeviceKeyHandlers.add((com.android.internal.os.DeviceKeyHandler) new dalvik.system.PathClassLoader(stringArray[i5], getClass().getClassLoader()).loadClass(stringArray2[i5]).getConstructor(android.content.Context.class).newInstance(this.mContext));
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Could not instantiate device key handler " + stringArray[i5] + " from class " + stringArray2[i5], e);
            }
        }
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.PhoneWindowManager.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.policy.PhoneWindowManager.this.mTorchOffPendingIntent = null;
                if (com.android.server.policy.PhoneWindowManager.this.mTorchEnabled) {
                    com.android.server.policy.PhoneWindowManager.this.mHandler.removeMessages(100);
                    android.os.Message obtainMessage = com.android.server.policy.PhoneWindowManager.this.mHandler.obtainMessage(100);
                    obtainMessage.setAsynchronous(true);
                    obtainMessage.sendToTarget();
                }
            }
        };
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction(ACTION_TORCH_OFF);
        this.mContext.registerReceiver(broadcastReceiver, intentFilter2);
    }

    private void initKeyCombinationRules() {
        this.mKeyCombinationManager = new com.android.server.policy.KeyCombinationManager(this.mHandler);
        int i = 25;
        int i2 = 26;
        if (this.mContext.getResources().getBoolean(android.R.bool.config_enableProximityService)) {
            this.mKeyCombinationManager.addRule(new com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule(i, i2) { // from class: com.android.server.policy.PhoneWindowManager.7
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                void execute() {
                    com.android.server.policy.PhoneWindowManager.this.mPowerKeyHandled = true;
                    com.android.server.policy.PhoneWindowManager.this.interceptScreenshotChord(1, com.android.server.policy.PhoneWindowManager.this.getScreenshotChordLongPressDelay());
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel() {
                    com.android.server.policy.PhoneWindowManager.this.cancelPendingScreenshotChordAction();
                }
            });
            if (this.mHasFeatureWatch) {
                this.mKeyCombinationManager.addRule(new com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule(i2, 264) { // from class: com.android.server.policy.PhoneWindowManager.8
                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    void execute() {
                        com.android.server.policy.PhoneWindowManager.this.mPowerKeyHandled = true;
                        com.android.server.policy.PhoneWindowManager.this.interceptScreenshotChord(1, com.android.server.policy.PhoneWindowManager.this.getScreenshotChordLongPressDelay());
                    }

                    /* JADX INFO: Access modifiers changed from: package-private */
                    @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                    public void cancel() {
                        com.android.server.policy.PhoneWindowManager.this.cancelPendingScreenshotChordAction();
                    }
                });
            }
        }
        int i3 = 24;
        this.mKeyCombinationManager.addRule(new com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule(i, i3) { // from class: com.android.server.policy.PhoneWindowManager.9
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            boolean preCondition() {
                return com.android.server.policy.PhoneWindowManager.this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(com.android.server.policy.PhoneWindowManager.this.isKeyguardLocked());
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            void execute() {
                com.android.server.policy.PhoneWindowManager.this.interceptAccessibilityShortcutChord();
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void cancel() {
                com.android.server.policy.PhoneWindowManager.this.cancelPendingAccessibilityShortcutAction();
            }
        });
        this.mKeyCombinationManager.addRule(new com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule(i3, i2) { // from class: com.android.server.policy.PhoneWindowManager.10
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            boolean preCondition() {
                switch (com.android.server.policy.PhoneWindowManager.this.mPowerVolUpBehavior) {
                    case 1:
                        if (com.android.server.policy.PhoneWindowManager.this.mRingerToggleChord != 0) {
                        }
                        break;
                }
                return true;
            }

            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            void execute() {
                switch (com.android.server.policy.PhoneWindowManager.this.mPowerVolUpBehavior) {
                    case 1:
                        com.android.server.policy.PhoneWindowManager.this.interceptRingerToggleChord();
                        com.android.server.policy.PhoneWindowManager.this.mPowerKeyHandled = true;
                        break;
                    case 2:
                        com.android.server.policy.PhoneWindowManager.this.performHapticFeedback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, false, "Power + Volume Up - Global Actions");
                        com.android.server.policy.PhoneWindowManager.this.showGlobalActions();
                        com.android.server.policy.PhoneWindowManager.this.mPowerKeyHandled = true;
                        break;
                }
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
            public void cancel() {
                switch (com.android.server.policy.PhoneWindowManager.this.mPowerVolUpBehavior) {
                    case 1:
                        com.android.server.policy.PhoneWindowManager.this.cancelPendingRingerToggleChordAction();
                        break;
                    case 2:
                        com.android.server.policy.PhoneWindowManager.this.cancelGlobalActionsAction();
                        break;
                }
            }
        });
        if (this.mHasFeatureLeanback) {
            int i4 = 4;
            this.mKeyCombinationManager.addRule(new com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule(i4, 20) { // from class: com.android.server.policy.PhoneWindowManager.11
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                void execute() {
                    com.android.server.policy.PhoneWindowManager.this.mBackKeyHandled = true;
                    com.android.server.policy.PhoneWindowManager.this.interceptAccessibilityGestureTv();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel() {
                    com.android.server.policy.PhoneWindowManager.this.cancelAccessibilityGestureTv();
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                long getKeyInterceptDelayMs() {
                    return 0L;
                }
            });
            this.mKeyCombinationManager.addRule(new com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule(23, i4) { // from class: com.android.server.policy.PhoneWindowManager.12
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                void execute() {
                    com.android.server.policy.PhoneWindowManager.this.mBackKeyHandled = true;
                    com.android.server.policy.PhoneWindowManager.this.interceptBugreportGestureTv();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                public void cancel() {
                    com.android.server.policy.PhoneWindowManager.this.cancelBugreportGestureTv();
                }

                @Override // com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule
                long getKeyInterceptDelayMs() {
                    return 0L;
                }
            });
        }
    }

    private final class PowerKeyRule extends com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule {
        PowerKeyRule() {
            super(26);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        boolean supportLongPress() {
            return com.android.server.policy.PhoneWindowManager.this.hasLongPressOnPowerBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        boolean supportVeryLongPress() {
            return com.android.server.policy.PhoneWindowManager.this.hasVeryLongPressOnPowerBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        int getMaxMultiPressCount() {
            return com.android.server.policy.PhoneWindowManager.this.getMaxMultiPressPowerCount();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onPress(long j, int i) {
            if (com.android.server.policy.PhoneWindowManager.this.mShouldEarlyShortPressOnPower) {
                return;
            }
            com.android.server.policy.PhoneWindowManager.this.powerPress(j, 1, i);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        long getLongPressTimeoutMs() {
            if (com.android.server.policy.PhoneWindowManager.this.getResolvedLongPressOnPowerBehavior() == 5) {
                return com.android.server.policy.PhoneWindowManager.this.mLongPressOnPowerAssistantTimeoutMs;
            }
            return super.getLongPressTimeoutMs();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onLongPress(long j) {
            if (com.android.server.policy.PhoneWindowManager.this.mSingleKeyGestureDetector.beganFromNonInteractive() && !com.android.server.policy.PhoneWindowManager.this.mSupportLongPressPowerWhenNonInteractive) {
                android.util.Slog.v(com.android.server.policy.PhoneWindowManager.TAG, "Not support long press power when device is not interactive.");
            } else {
                com.android.server.policy.PhoneWindowManager.this.powerLongPress(j);
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onVeryLongPress(long j) {
            com.android.server.policy.PhoneWindowManager.this.mActivityManagerInternal.prepareForPossibleShutdown();
            com.android.server.policy.PhoneWindowManager.this.powerVeryLongPress();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onMultiPress(long j, int i, int i2) {
            com.android.server.policy.PhoneWindowManager.this.powerPress(j, i, i2);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onKeyUp(long j, int i, int i2) {
            if (com.android.server.policy.PhoneWindowManager.this.mShouldEarlyShortPressOnPower && i == 1) {
                com.android.server.policy.PhoneWindowManager.this.powerPress(j, 1, i2);
            }
        }
    }

    private final class BackKeyRule extends com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule {
        BackKeyRule() {
            super(4);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        boolean supportLongPress() {
            return com.android.server.policy.PhoneWindowManager.this.hasLongPressOnBackBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        int getMaxMultiPressCount() {
            return 1;
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onPress(long j, int i) {
            com.android.server.policy.PhoneWindowManager.this.mBackKeyHandled |= com.android.server.policy.PhoneWindowManager.this.backKeyPress();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onLongPress(long j) {
            com.android.server.policy.PhoneWindowManager.this.backLongPress();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StemPrimaryKeyRule extends com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule {
        StemPrimaryKeyRule() {
            super(264);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        boolean supportLongPress() {
            return com.android.server.policy.PhoneWindowManager.this.hasLongPressOnStemPrimaryBehavior();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        int getMaxMultiPressCount() {
            return com.android.server.policy.PhoneWindowManager.this.getMaxMultiPressStemPrimaryCount();
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onPress(long j, int i) {
            if (shouldHandleStemPrimaryEarlyShortPress()) {
                return;
            }
            com.android.server.policy.PhoneWindowManager.this.mDeferredKeyActionExecutor.queueKeyAction(264, j, new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.policy.PhoneWindowManager.StemPrimaryKeyRule.this.lambda$onPress$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPress$0() {
            com.android.server.policy.PhoneWindowManager.this.stemPrimaryPress(1);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onLongPress(final long j) {
            com.android.server.policy.PhoneWindowManager.this.mDeferredKeyActionExecutor.queueKeyAction(264, j, new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.policy.PhoneWindowManager.StemPrimaryKeyRule.this.lambda$onLongPress$1(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLongPress$1(long j) {
            com.android.server.policy.PhoneWindowManager.this.stemPrimaryLongPress(j);
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onMultiPress(long j, final int i, int i2) {
            if (i == 3 && com.android.server.policy.PhoneWindowManager.this.mTriplePressOnStemPrimaryBehavior == 1) {
                com.android.server.policy.PhoneWindowManager.this.mDeferredKeyActionExecutor.cancelQueuedAction(264);
                undoEarlySinglePress();
                com.android.server.policy.PhoneWindowManager.this.stemPrimaryPress(i);
                return;
            }
            com.android.server.policy.PhoneWindowManager.this.mDeferredKeyActionExecutor.queueKeyAction(264, j, new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.policy.PhoneWindowManager.StemPrimaryKeyRule.this.lambda$onMultiPress$2(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMultiPress$2(int i) {
            com.android.server.policy.PhoneWindowManager.this.stemPrimaryPress(i);
        }

        private void undoEarlySinglePress() {
            if (shouldHandleStemPrimaryEarlyShortPress() && com.android.server.policy.PhoneWindowManager.this.mFocusedTaskInfoOnStemPrimarySingleKeyUp != null) {
                try {
                    com.android.server.policy.PhoneWindowManager.this.mActivityManagerService.startActivityFromRecents(com.android.server.policy.PhoneWindowManager.this.mFocusedTaskInfoOnStemPrimarySingleKeyUp.taskId, (android.os.Bundle) null);
                } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(com.android.server.policy.PhoneWindowManager.TAG, "Failed to start task " + com.android.server.policy.PhoneWindowManager.this.mFocusedTaskInfoOnStemPrimarySingleKeyUp.taskId + " from recents", e);
                }
            }
        }

        @Override // com.android.server.policy.SingleKeyGestureDetector.SingleKeyRule
        void onKeyUp(long j, int i, int i2) {
            if (i == 1) {
                com.android.server.policy.PhoneWindowManager.this.mBackgroundRecentTaskInfoOnStemPrimarySingleKeyUp = com.android.server.policy.PhoneWindowManager.this.mActivityTaskManagerInternal.getMostRecentTaskFromBackground();
                com.android.server.policy.PhoneWindowManager.this.mFocusedTaskInfoOnStemPrimarySingleKeyUp = null;
                if (shouldHandleStemPrimaryEarlyShortPress()) {
                    com.android.server.policy.PhoneWindowManager.this.mDeferredKeyActionExecutor.queueKeyAction(264, j, new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$StemPrimaryKeyRule$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.policy.PhoneWindowManager.StemPrimaryKeyRule.this.lambda$onKeyUp$3();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKeyUp$3() {
            try {
                com.android.server.policy.PhoneWindowManager.this.mFocusedTaskInfoOnStemPrimarySingleKeyUp = com.android.server.policy.PhoneWindowManager.this.mActivityManagerService.getFocusedRootTaskInfo();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.policy.PhoneWindowManager.TAG, "StemPrimaryKeyRule: onKeyUp: error while getting focused task info.", e);
            }
            com.android.server.policy.PhoneWindowManager.this.stemPrimaryPress(1);
        }

        private boolean shouldHandleStemPrimaryEarlyShortPress() {
            return com.android.server.policy.PhoneWindowManager.this.mShouldEarlyShortPressOnStemPrimary && com.android.server.policy.PhoneWindowManager.this.mShortPressOnStemPrimaryBehavior == 1;
        }
    }

    private void initSingleKeyGestureRules(android.os.Looper looper) {
        this.mSingleKeyGestureDetector = com.android.server.policy.SingleKeyGestureDetector.get(this.mContext, looper);
        this.mSingleKeyGestureDetector.addRule(new com.android.server.policy.PhoneWindowManager.PowerKeyRule());
        this.mSingleKeyGestureDetector.addRule(new com.android.server.policy.PhoneWindowManager.BackKeyRule());
    }

    private void updateKeyAssignments() {
        int i = this.mDeviceHardwareKeys;
        if (this.mForceNavbar == 1) {
            i = 0;
        }
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 8) != 0;
        boolean z3 = (i & 16) != 0;
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.content.res.Resources resources = this.mContext.getResources();
        this.mEdgeLongSwipeAction = org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING;
        this.mMenuPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.MENU;
        this.mMenuLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromIntSafe(resources.getInteger(1057423378));
        if (this.mMenuLongPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING && z && !z2) {
            this.mMenuLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.SEARCH;
        }
        this.mAssistPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.SEARCH;
        this.mAssistLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.VOICE_SEARCH;
        this.mAppSwitchPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH;
        this.mAppSwitchLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromIntSafe(resources.getInteger(1057423375));
        this.mBackLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromIntSafe(resources.getInteger(1057423376));
        if (this.mBackLongPressAction.ordinal() > org.lineageos.internal.util.DeviceKeysConstants.Action.SLEEP.ordinal()) {
            this.mBackLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING;
        }
        this.mBackLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_back_long_press_action", this.mBackLongPressAction);
        this.mHomeLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromIntSafe(resources.getInteger(1057423377));
        if (this.mHomeLongPressAction.ordinal() > org.lineageos.internal.util.DeviceKeysConstants.Action.SLEEP.ordinal()) {
            this.mHomeLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING;
        }
        this.mHomeDoubleTapAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromIntSafe(resources.getInteger(1057423374));
        if (this.mHomeDoubleTapAction.ordinal() > org.lineageos.internal.util.DeviceKeysConstants.Action.SLEEP.ordinal()) {
            this.mHomeDoubleTapAction = org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING;
        }
        this.mHomeLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_home_long_press_action", this.mHomeLongPressAction);
        this.mHomeDoubleTapAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_home_double_tap_action", this.mHomeDoubleTapAction);
        if (z) {
            this.mMenuPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_menu_action", this.mMenuPressAction);
            this.mMenuLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_menu_long_press_action", this.mMenuLongPressAction);
        }
        if (z2) {
            this.mAssistPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_assist_action", this.mAssistPressAction);
            this.mAssistLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_assist_long_press_action", this.mAssistLongPressAction);
        }
        if (z3) {
            this.mAppSwitchPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_app_switch_action", this.mAppSwitchPressAction);
        }
        this.mAppSwitchLongPressAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_app_switch_long_press_action", this.mAppSwitchLongPressAction);
        this.mEdgeLongSwipeAction = org.lineageos.internal.util.DeviceKeysConstants.Action.fromSettings(contentResolver, "key_edge_long_swipe_action", this.mEdgeLongSwipeAction);
        this.mShortPressOnWindowBehavior = 0;
        if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
            this.mShortPressOnWindowBehavior = 1;
        }
        this.mShortPressOnSettingsBehavior = resources.getInteger(android.R.integer.config_screen_rotation_fade_in_delay);
        if (this.mShortPressOnSettingsBehavior < 0 || this.mShortPressOnSettingsBehavior > 1) {
            this.mShortPressOnSettingsBehavior = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSettings() {
        updateSettings(null);
    }

    void updateSettings(android.os.Handler handler) {
        boolean z;
        if (handler != null) {
            handler.post(new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.policy.PhoneWindowManager.this.lambda$updateSettings$1();
                }
            });
            return;
        }
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        int integer = this.mContext.getResources().getInteger(1057423370);
        synchronized (this.mLock) {
            try {
                this.mEndcallBehavior = android.provider.Settings.System.getIntForUser(contentResolver, "end_button_behavior", 2, -2);
                this.mIncallPowerBehavior = android.provider.Settings.Secure.getIntForUser(contentResolver, "incall_power_button_behavior", 1, -2);
                this.mIncallBackBehavior = android.provider.Settings.Secure.getIntForUser(contentResolver, "incall_back_button_behavior", 0, -2);
                this.mRingHomeBehavior = lineageos.providers.LineageSettings.Secure.getIntForUser(contentResolver, "ring_home_button_behavior", 1, -2);
                this.mSystemNavigationKeysEnabled = android.provider.Settings.Secure.getIntForUser(contentResolver, "system_navigation_keys_enabled", 0, -2) == 1;
                this.mRingerToggleChord = android.provider.Settings.Secure.getIntForUser(contentResolver, "volume_hush_gesture", 0, -2);
                this.mPowerButtonSuppressionDelayMillis = android.provider.Settings.Global.getInt(contentResolver, "power_button_suppression_delay_after_gesture_wake", 800);
                if (!this.mContext.getResources().getBoolean(android.R.bool.config_viewRotaryEncoderHapticScrollFedbackEnabled)) {
                    this.mRingerToggleChord = 0;
                }
                this.mTorchLongPressPowerEnabled = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "torch_long_press_power_gesture", 0, -2) == 1;
                this.mTorchTimeout = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "torch_long_press_power_timeout", 0, -2);
                this.mClickPartialScreenshot = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "click_partial_screenshot", 0, -2) == 1;
                this.mWakeOnHomeKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "home_wake_screen", 1, -2) == 1 && (integer & 1) != 0;
                this.mWakeOnBackKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "back_wake_screen", 0, -2) == 1 && (integer & 2) != 0;
                this.mWakeOnMenuKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "menu_wake_screen", 0, -2) == 1 && (integer & 4) != 0;
                this.mWakeOnAssistKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "assist_wake_screen", 0, -2) == 1 && (integer & 8) != 0;
                this.mWakeOnAppSwitchKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "app_switch_wake_screen", 0, -2) == 1 && (integer & 16) != 0;
                this.mWakeOnVolumeKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "volume_wake_screen", 0, -2) == 1 && (integer & 64) != 0;
                this.mVolumeAnswerCall = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "volume_answer_call", 0, -2) == 1 && (integer & 64) != 0;
                this.mWakeOnCameraKeyPress = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "camera_wake_screen", 0, -2) == 1 && (integer & 32) != 0;
                this.mCameraSleepOnRelease = this.mWakeOnCameraKeyPress && lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "camera_sleep_on_release", 0, -2) == 1;
                this.mCameraLaunch = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "camera_launch", 0, -2) == 1;
                boolean z2 = android.provider.Settings.Secure.getIntForUser(contentResolver, "wake_gesture_enabled", 0, -2) != 0;
                if (this.mWakeGestureEnabledSetting != z2) {
                    this.mWakeGestureEnabledSetting = z2;
                    updateWakeGestureListenerLp();
                }
                int intForUser = lineageos.providers.LineageSettings.System.getIntForUser(contentResolver, "force_show_navbar", 0, -2);
                if (intForUser != this.mForceNavbar) {
                    this.mForceNavbar = intForUser;
                    if (this.mLineageHardware.isSupported(32)) {
                        this.mLineageHardware.set(32, this.mForceNavbar == 1);
                    }
                }
                updateKeyAssignments();
                this.mLockScreenTimeout = android.provider.Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 0, -2);
                java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(contentResolver, "default_input_method", -2);
                boolean z3 = stringForUser != null && stringForUser.length() > 0;
                if (this.mHasSoftInput == z3) {
                    z = false;
                } else {
                    this.mHasSoftInput = z3;
                    z = true;
                }
                this.mShortPressOnPowerBehavior = android.provider.Settings.Global.getInt(contentResolver, "power_button_short_press", this.mContext.getResources().getInteger(android.R.integer.config_screen_rotation_fade_in));
                this.mDoublePressOnPowerBehavior = android.provider.Settings.Global.getInt(contentResolver, "power_button_double_press", this.mContext.getResources().getInteger(android.R.integer.config_displayWhiteBalanceTransitionTime));
                this.mTriplePressOnPowerBehavior = android.provider.Settings.Global.getInt(contentResolver, "power_button_triple_press", this.mContext.getResources().getInteger(android.R.integer.config_stableDeviceDisplayHeight));
                int i = android.provider.Settings.Global.getInt(contentResolver, "power_button_long_press", this.mContext.getResources().getInteger(android.R.integer.config_lightSensorWarmupTime));
                int i2 = android.provider.Settings.Global.getInt(contentResolver, "power_button_very_long_press", this.mContext.getResources().getInteger(android.R.integer.config_toastDefaultGravity));
                if (this.mLongPressOnPowerBehavior != i || this.mVeryLongPressOnPowerBehavior != i2) {
                    this.mLongPressOnPowerBehavior = i;
                    this.mVeryLongPressOnPowerBehavior = i2;
                }
                this.mLongPressOnPowerAssistantTimeoutMs = android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "power_button_long_press_duration_ms", this.mContext.getResources().getInteger(android.R.integer.config_lockSoundVolumeDb));
                this.mPowerVolUpBehavior = android.provider.Settings.Global.getInt(contentResolver, "key_chord_power_volume_up", this.mContext.getResources().getInteger(android.R.integer.config_jobSchedulerInactivityIdleThreshold));
                this.mShortPressOnStemPrimaryBehavior = android.provider.Settings.Global.getInt(contentResolver, "stem_primary_button_short_press", this.mContext.getResources().getInteger(android.R.integer.config_screen_rotation_total_180));
                this.mDoublePressOnStemPrimaryBehavior = android.provider.Settings.Global.getInt(contentResolver, "stem_primary_button_double_press", this.mContext.getResources().getInteger(android.R.integer.config_displayWhiteBalanceTransitionTimeDecrease));
                this.mTriplePressOnStemPrimaryBehavior = android.provider.Settings.Global.getInt(contentResolver, "stem_primary_button_triple_press", this.mContext.getResources().getInteger(android.R.integer.config_stableDeviceDisplayWidth));
                this.mLongPressOnStemPrimaryBehavior = android.provider.Settings.Global.getInt(contentResolver, "stem_primary_button_long_press", this.mContext.getResources().getInteger(android.R.integer.config_longPressOnBackBehavior));
                this.mShouldEarlyShortPressOnPower = this.mContext.getResources().getBoolean(android.R.bool.config_settingsHelpLinksEnabled);
                this.mShouldEarlyShortPressOnStemPrimary = this.mContext.getResources().getBoolean(android.R.bool.config_sf_limitedAlpha);
                this.mStylusButtonsEnabled = android.provider.Settings.Secure.getIntForUser(contentResolver, "stylus_buttons_enabled", 1, -2) == 1;
                this.mInputManagerInternal.setStylusButtonMotionEventsEnabled(this.mStylusButtonsEnabled);
                boolean z4 = android.provider.Settings.Secure.getIntForUser(contentResolver, "nav_bar_kids_mode", 0, -2) == 1;
                if (this.mKidsModeEnabled != z4) {
                    this.mKidsModeEnabled = z4;
                    updateKidsModeSettings();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            updateRotation(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSettings$1() {
        updateSettings(null);
    }

    private void updateKidsModeSettings() {
        if (this.mKidsModeEnabled) {
            if (this.mContext.getResources().getBoolean(android.R.bool.config_requireCallCapableAccountForHandle)) {
                this.mWindowManagerInternal.setOrientationRequestPolicy(true, new int[]{0, 8}, new int[]{6, 6});
                return;
            } else {
                this.mWindowManagerInternal.setOrientationRequestPolicy(true, null, null);
                return;
            }
        }
        this.mWindowManagerInternal.setOrientationRequestPolicy(false, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.service.dreams.DreamManagerInternal getDreamManagerInternal() {
        if (this.mDreamManagerInternal == null) {
            this.mDreamManagerInternal = (android.service.dreams.DreamManagerInternal) com.android.server.LocalServices.getService(android.service.dreams.DreamManagerInternal.class);
        }
        return this.mDreamManagerInternal;
    }

    private void updateWakeGestureListenerLp() {
        if (shouldEnableWakeGestureLp()) {
            this.mWakeGestureListener.requestWakeUpTrigger();
        } else {
            this.mWakeGestureListener.cancelWakeUpTrigger();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldEnableWakeGestureLp() {
        return this.mWakeGestureEnabledSetting && !this.mDefaultDisplayPolicy.isAwake() && !(getLidBehavior() == 1 && this.mDefaultDisplayPolicy.getLidState() == 0) && this.mWakeGestureListener.isSupported();
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.policy.WindowManagerPolicy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int checkAddPermission(int i, boolean z, java.lang.String str, int[] iArr) {
        android.content.pm.ApplicationInfo applicationInfo;
        if (z && this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") != 0) {
            return -8;
        }
        iArr[0] = -1;
        if ((i < 1 || i > 99) && ((i < 1000 || i > 1999) && (i < 2000 || i > 2999))) {
            return -10;
        }
        if (i < 2000 || i > 2999) {
            return 0;
        }
        if (!android.view.WindowManager.LayoutParams.isSystemAlertWindowType(i)) {
            switch (i) {
                case 2005:
                    iArr[0] = 45;
                    return 0;
                case 2032:
                    if (android.view.contentprotection.flags.Flags.createAccessibilityOverlayAppOpEnabled()) {
                        iArr[0] = 138;
                        return 0;
                    }
                case 2011:
                case 2013:
                case 2024:
                case 2030:
                case 2031:
                case 2035:
                case 2037:
                    return 0;
                default:
                    return this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") == 0 ? 0 : -8;
            }
        } else {
            iArr[0] = 24;
            int callingUid = android.os.Binder.getCallingUid();
            if (android.os.UserHandle.getAppId(callingUid) == 1000) {
                return 0;
            }
            try {
                try {
                    applicationInfo = this.mPackageManager.getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserId(callingUid));
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    applicationInfo = null;
                    if (applicationInfo != null) {
                    }
                    if (this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") != 0) {
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            }
            if (applicationInfo != null || (i != 2038 && applicationInfo.targetSdkVersion >= 26)) {
                return this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_SYSTEM_WINDOW") != 0 ? 0 : -8;
            }
            if (this.mContext.checkCallingOrSelfPermission("android.permission.SYSTEM_APPLICATION_OVERLAY") == 0) {
                return 0;
            }
            switch (this.mAppOpsManager.noteOpNoThrow(iArr[0], callingUid, str, (java.lang.String) null, "check-add")) {
                case 0:
                case 1:
                    return 0;
                case 2:
                    return applicationInfo.targetSdkVersion < 23 ? 0 : -8;
                default:
                    return this.mContext.checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == 0 ? 0 : -8;
            }
        }
    }

    void readLidState() {
        this.mDefaultDisplayPolicy.setLidState(this.mWindowManagerFuncs.getLidState());
    }

    private void readCameraLensCoverState() {
        this.mCameraLensCoverState = this.mWindowManagerFuncs.getCameraLensCoverState();
    }

    private boolean isHidden(int i) {
        int lidState = this.mDefaultDisplayPolicy.getLidState();
        switch (i) {
            case 1:
                if (lidState != 0) {
                    break;
                }
                break;
            case 2:
                if (lidState != 1) {
                    break;
                }
                break;
        }
        return false;
    }

    private boolean isBuiltInKeyboardVisible() {
        return this.mHaveBuiltInKeyboard && !isHidden(this.mLidKeyboardAccessibility);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void adjustConfigurationLw(android.content.res.Configuration configuration, int i, int i2) {
        this.mHaveBuiltInKeyboard = (i & 1) != 0;
        readLidState();
        if (configuration.keyboard == 1 || (i == 1 && isHidden(this.mLidKeyboardAccessibility))) {
            configuration.hardKeyboardHidden = 2;
            if (!this.mHasSoftInput) {
                configuration.keyboardHidden = 2;
            }
        }
        if (configuration.navigation == 1 || (i2 == 1 && isHidden(this.mLidNavigationAccessibility))) {
            configuration.navigationHidden = 2;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardHostWindow(android.view.WindowManager.LayoutParams layoutParams) {
        return layoutParams.type == 2040;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public android.view.animation.Animation createHiddenByKeyguardExit(boolean z, boolean z2, boolean z3) {
        return com.android.internal.policy.TransitionAnimation.createHiddenByKeyguardExit(this.mContext, this.mLogDecelerateInterpolator, z, z2, z3);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public android.view.animation.Animation createKeyguardWallpaperExit(boolean z) {
        if (z) {
            return null;
        }
        return android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.lock_screen_wallpaper_exit);
    }

    private static void awakenDreams() {
        android.service.dreams.IDreamManager dreamManager = getDreamManager();
        if (dreamManager != null) {
            try {
                dreamManager.awaken();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    static android.service.dreams.IDreamManager getDreamManager() {
        return android.service.dreams.IDreamManager.Stub.asInterface(android.os.ServiceManager.checkService("dreams"));
    }

    android.telecom.TelecomManager getTelecommService() {
        return (android.telecom.TelecomManager) this.mContext.getSystemService("telecom");
    }

    android.app.NotificationManager getNotificationService() {
        return (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
    }

    static android.media.IAudioService getAudioService() {
        android.media.IAudioService asInterface = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.checkService("audio"));
        if (asInterface == null) {
            android.util.Log.w(TAG, "Unable to find IAudioService interface.");
        }
        return asInterface;
    }

    boolean keyguardOn() {
        return isKeyguardShowingAndNotOccluded() || inKeyguardRestrictedKeyInputMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyboardSystemEvent(com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent, android.view.KeyEvent keyEvent) {
        com.android.server.input.KeyboardMetricsCollector.logKeyboardSystemsEventReportedAtom(this.mInputManager.getInputDevice(keyEvent.getDeviceId()), keyboardLogEvent, keyEvent.getMetaState(), keyEvent.getKeyCode());
        keyEvent.recycle();
    }

    private void logKeyboardSystemsEventOnActionUp(android.view.KeyEvent keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        if (keyEvent.getAction() != 1) {
            return;
        }
        logKeyboardSystemsEvent(keyEvent, keyboardLogEvent);
    }

    private void logKeyboardSystemsEventOnActionDown(android.view.KeyEvent keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        if (keyEvent.getAction() != 0) {
            return;
        }
        logKeyboardSystemsEvent(keyEvent, keyboardLogEvent);
    }

    private void logKeyboardSystemsEvent(android.view.KeyEvent keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent keyboardLogEvent) {
        this.mHandler.obtainMessage(26, keyboardLogEvent.getIntValue(), 0, android.view.KeyEvent.obtain(keyEvent)).sendToTarget();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public long interceptKeyBeforeDispatching(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        int keyCode = keyEvent.getKeyCode();
        int flags = keyEvent.getFlags();
        int deviceId = keyEvent.getDeviceId();
        if (this.mKeyCombinationManager.isKeyConsumed(keyEvent)) {
            return -1L;
        }
        if ((flags & 1024) == 0) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long keyInterceptTimeout = this.mKeyCombinationManager.getKeyInterceptTimeout(keyCode);
            if (uptimeMillis < keyInterceptTimeout) {
                return keyInterceptTimeout - uptimeMillis;
            }
        }
        java.util.Set<java.lang.Integer> set = this.mConsumedKeysForDevice.get(deviceId);
        if (set == null) {
            set = new java.util.HashSet<>();
            this.mConsumedKeysForDevice.put(deviceId, set);
        }
        if (interceptSystemKeysAndShortcuts(iBinder, keyEvent) && keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            set.add(java.lang.Integer.valueOf(keyCode));
            return -1L;
        }
        boolean contains = set.contains(java.lang.Integer.valueOf(keyCode));
        if (keyEvent.getAction() == 1 || keyEvent.isCanceled()) {
            set.remove(java.lang.Integer.valueOf(keyCode));
            if (set.isEmpty()) {
                this.mConsumedKeysForDevice.remove(deviceId);
            }
        }
        return contains ? -1L : 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x055d  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x056b  */
    @android.annotation.SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean interceptSystemKeysAndShortcuts(android.os.IBinder iBinder, android.view.KeyEvent keyEvent) {
        boolean z;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal2;
        boolean z2;
        boolean keyguardOn = keyguardOn();
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        int metaState = keyEvent.getMetaState();
        boolean z3 = keyEvent.getAction() == 0;
        boolean isCanceled = keyEvent.isCanceled();
        int displayId = keyEvent.getDisplayId();
        int deviceId = keyEvent.getDeviceId();
        boolean z4 = z3 && repeatCount == 0;
        boolean z5 = (keyEvent.getFlags() & 128) != 0;
        boolean z6 = keyEvent.getDeviceId() == -1;
        if (this.mPendingMetaAction && !android.view.KeyEvent.isMetaKey(keyCode)) {
            this.mPendingMetaAction = false;
        }
        if (this.mPendingCapsLockToggle && !android.view.KeyEvent.isMetaKey(keyCode) && !android.view.KeyEvent.isAltKey(keyCode)) {
            this.mPendingCapsLockToggle = false;
        }
        if (isUserSetupComplete() && !keyguardOn && this.mModifierShortcutManager.interceptKey(keyEvent)) {
            dismissKeyboardShortcutsMenu();
            this.mPendingMetaAction = false;
            this.mPendingCapsLockToggle = false;
            return true;
        }
        switch (keyCode) {
            case 3:
                break;
            case 19:
                z = false;
                if (z4 && keyEvent.isMetaPressed() && keyEvent.isCtrlPressed() && (statusBarManagerInternal2 = getStatusBarManagerInternal()) != null) {
                    statusBarManagerInternal2.moveFocusedTaskToFullscreen(getTargetDisplayIdForKeyEvent(keyEvent));
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.MULTI_WINDOW_NAVIGATION);
                    break;
                }
                if (!isValidGlobalKey(keyCode)) {
                    z2 = true;
                } else if (!this.mGlobalKeyManager.handleGlobalKey(this.mContext, keyCode, keyEvent)) {
                    z2 = true;
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    if ((65536 & metaState) == 0) {
                        break;
                    } else {
                        break;
                    }
                }
                break;
            case 20:
                z = false;
                if (z4 && keyEvent.isMetaPressed() && keyEvent.isCtrlPressed() && (statusBarManagerInternal = getStatusBarManagerInternal()) != null) {
                    statusBarManagerInternal.enterDesktop(getTargetDisplayIdForKeyEvent(keyEvent));
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.DESKTOP_MODE);
                    break;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
            case 21:
                z = false;
                if (z4 && keyEvent.isMetaPressed()) {
                    if (keyEvent.isCtrlPressed()) {
                        enterStageSplitFromRunningApp(true);
                        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.SPLIT_SCREEN_NAVIGATION);
                        break;
                    } else {
                        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.BACK);
                        injectBackGesture(keyEvent.getDownTime());
                        break;
                    }
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
            case 22:
                if (z4 && keyEvent.isMetaPressed() && keyEvent.isCtrlPressed()) {
                    enterStageSplitFromRunningApp(false);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.SPLIT_SCREEN_NAVIGATION);
                    break;
                } else {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 24:
            case 25:
            case 164:
                if (this.mUseTvRouting || this.mHandleVolumeKeysInWM) {
                    dispatchDirectAudioEvent(keyEvent);
                    break;
                } else {
                    if (!this.mDefaultDisplayPolicy.isPersistentVrModeEnabled()) {
                        z = false;
                    } else {
                        android.view.InputDevice device = keyEvent.getDevice();
                        if (device == null || device.isExternal()) {
                            z = false;
                        }
                    }
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 29:
                if (!z4 || !keyEvent.isMetaPressed()) {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                } else {
                    launchAssistAction("android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD", deviceId, keyEvent.getEventTime(), 0);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LAUNCH_ASSISTANT);
                    break;
                }
                break;
            case 36:
            case 66:
                if (keyEvent.isMetaPressed()) {
                    break;
                } else {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 37:
                if (!z4 || !keyEvent.isMetaPressed()) {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                } else {
                    showSystemSettings();
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LAUNCH_SYSTEM_SETTINGS);
                    break;
                }
                break;
            case 40:
                if (z4 && keyEvent.isMetaPressed()) {
                    lockNow(null);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LOCK_SCREEN);
                    break;
                } else {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 42:
                if (!z4 || !keyEvent.isMetaPressed()) {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                } else if (keyEvent.isCtrlPressed()) {
                    sendSystemKeyToStatusBarAsync(keyEvent);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.OPEN_NOTES);
                    break;
                } else {
                    toggleNotificationPanel();
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_NOTIFICATION_PANEL);
                    break;
                }
                break;
            case 47:
                if (!z4 || !keyEvent.isMetaPressed() || !keyEvent.isCtrlPressed()) {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                } else {
                    interceptScreenshotChord(keyEvent.isShiftPressed() ? 2 : 1, 2, 0L);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TAKE_SCREENSHOT);
                    break;
                }
                break;
            case 57:
            case 58:
                if (!z3) {
                    if (this.mRecentAppsHeldModifiers != 0 && (this.mRecentAppsHeldModifiers & metaState) == 0) {
                        this.mRecentAppsHeldModifiers = 0;
                        hideRecentApps(true, false);
                        break;
                    } else if (!this.mPendingCapsLockToggle) {
                        z = false;
                    } else {
                        this.mInputManagerInternal.toggleCapsLock(keyEvent.getDeviceId());
                        this.mPendingCapsLockToggle = false;
                        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_CAPS_LOCK);
                        break;
                    }
                } else if (keyEvent.isMetaPressed()) {
                    this.mPendingCapsLockToggle = true;
                    z = false;
                    this.mPendingMetaAction = false;
                } else {
                    z = false;
                    this.mPendingCapsLockToggle = false;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
            case 61:
                if (!z4 || keyguardOn || !isUserSetupComplete()) {
                    z = false;
                } else if (keyEvent.isMetaPressed()) {
                    showRecentApps(false);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.RECENT_APPS);
                    break;
                } else if (this.mRecentAppsHeldModifiers != 0) {
                    z = false;
                } else {
                    int modifiers = keyEvent.getModifiers() & (-194);
                    if (android.view.KeyEvent.metaStateHasModifiers(modifiers, 2)) {
                        this.mRecentAppsHeldModifiers = modifiers;
                        showRecentApps(true);
                        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.RECENT_APPS);
                        break;
                    } else {
                        z = false;
                    }
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
            case 67:
            case 111:
                if (z4 && keyEvent.isMetaPressed()) {
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.BACK);
                    injectBackGesture(keyEvent.getDownTime());
                    break;
                } else {
                    z = false;
                    if (z4) {
                        statusBarManagerInternal2.moveFocusedTaskToFullscreen(getTargetDisplayIdForKeyEvent(keyEvent));
                        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.MULTI_WINDOW_NAVIGATION);
                        break;
                    }
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 76:
                if (z4 && keyEvent.isMetaPressed() && !keyguardOn) {
                    toggleKeyboardShortcutsMenu(keyEvent.getDeviceId());
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.OPEN_SHORTCUT_HELPER);
                    break;
                } else {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 82:
                if (!z6 && !keyguardOn) {
                    if (z3) {
                        if (this.mMenuPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH || this.mMenuLongPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                            preloadRecentApps();
                        }
                        if (repeatCount == 0) {
                            this.mMenuPressed = true;
                            if (this.mEnableShiftMenuBugReports && (metaState & 1) == 1) {
                                this.mContext.sendOrderedBroadcastAsUser(new android.content.Intent("android.intent.action.BUG_REPORT"), android.os.UserHandle.CURRENT, null, null, null, 0, null, null);
                                break;
                            }
                        } else if (z5 && !keyguardOn && this.mMenuLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING) {
                            if (this.mMenuLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                                cancelPreloadRecentApps();
                            }
                            performHapticFeedback(0, false, "Menu - Long Press");
                            performKeyAction(this.mMenuLongPressAction, keyEvent);
                            this.mMenuPressed = false;
                            break;
                        }
                    }
                    if (!z3 && this.mMenuPressed) {
                        if (this.mMenuPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                            cancelPreloadRecentApps();
                        }
                        this.mMenuPressed = false;
                        if (!isCanceled) {
                            performKeyAction(this.mMenuPressAction, keyEvent);
                            break;
                        }
                    }
                }
                break;
            case 83:
                if (!z3) {
                    toggleNotificationPanel();
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_NOTIFICATION_PANEL);
                    break;
                }
                break;
            case 84:
                if (!z4 || keyguardOn) {
                    z = false;
                } else {
                    switch (this.mSearchKeyBehavior) {
                        case 1:
                            launchTargetSearchActivity();
                            logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LAUNCH_SEARCH);
                            break;
                        default:
                            z = false;
                            break;
                    }
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
            case 115:
                if (!z3) {
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_CAPS_LOCK);
                    z = false;
                } else {
                    z = false;
                }
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
            case 117:
            case 118:
                if (z3) {
                    if (!keyEvent.isAltPressed()) {
                        this.mPendingCapsLockToggle = false;
                        this.mPendingMetaAction = true;
                        break;
                    } else {
                        this.mPendingCapsLockToggle = true;
                        this.mPendingMetaAction = false;
                        break;
                    }
                } else if (this.mPendingCapsLockToggle) {
                    this.mInputManagerInternal.toggleCapsLock(keyEvent.getDeviceId());
                    this.mPendingCapsLockToggle = false;
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_CAPS_LOCK);
                    break;
                } else if (this.mPendingMetaAction) {
                    if (!isCanceled) {
                        launchAllAppsViaA11y();
                        logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.ACCESSIBILITY_ALL_APPS);
                    }
                    this.mPendingMetaAction = false;
                    break;
                }
                break;
            case com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT /* 176 */:
                if (this.mShortPressOnSettingsBehavior != 1) {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                } else if (!z3) {
                    toggleNotificationPanel();
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_NOTIFICATION_PANEL);
                    break;
                }
                break;
            case com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED /* 187 */:
                if (!keyguardOn) {
                    if (z3) {
                        if (this.mAppSwitchPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH || this.mAppSwitchLongPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                            preloadRecentApps();
                        }
                        if (repeatCount == 0) {
                            this.mAppSwitchLongPressed = false;
                            break;
                        } else if (z5 && !keyguardOn && this.mAppSwitchLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING) {
                            if (this.mAppSwitchLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                                cancelPreloadRecentApps();
                            }
                            performHapticFeedback(0, false, "Recents - Long Press");
                            performKeyAction(this.mAppSwitchLongPressAction, keyEvent);
                            this.mAppSwitchLongPressed = true;
                            break;
                        }
                    } else if (this.mAppSwitchLongPressed) {
                        this.mAppSwitchLongPressed = false;
                        break;
                    } else {
                        if (this.mAppSwitchPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                            cancelPreloadRecentApps();
                        }
                        if (!isCanceled) {
                            performKeyAction(this.mAppSwitchPressAction, keyEvent);
                            break;
                        }
                    }
                }
                break;
            case 204:
                if (z4) {
                    sendSwitchKeyboardLayout(keyEvent, iBinder, (metaState & 193) != 0 ? -1 : 1);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LANGUAGE_SWITCH);
                    break;
                } else {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case 219:
                if (!keyguardOn) {
                    if (z3) {
                        if (this.mAssistPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH || this.mAssistLongPressAction == org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                            preloadRecentApps();
                        }
                        if (repeatCount == 0) {
                            this.mAssistPressed = true;
                            break;
                        } else if (z5 && this.mAssistLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.NOTHING) {
                            if (this.mAssistLongPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                                cancelPreloadRecentApps();
                            }
                            performHapticFeedback(0, false, "Assist - Long Press");
                            performKeyAction(this.mAssistLongPressAction, keyEvent, 7);
                            this.mAssistPressed = false;
                            break;
                        }
                    } else if (this.mAssistPressed) {
                        if (this.mAssistPressAction != org.lineageos.internal.util.DeviceKeysConstants.Action.APP_SWITCH) {
                            cancelPreloadRecentApps();
                        }
                        this.mAssistPressed = false;
                        if (!isCanceled) {
                            performKeyAction(this.mAssistPressAction, keyEvent, 7);
                            break;
                        }
                    }
                } else {
                    z = false;
                    if (!isValidGlobalKey(keyCode)) {
                    }
                    if (!dispatchKeyToKeyHandlers(keyEvent)) {
                    }
                }
                break;
            case com.android.server.usb.descriptors.UsbDescriptor.CLASSID_DIAGNOSTIC /* 220 */:
            case 221:
                if (z3) {
                    int i = keyCode == 221 ? 1 : -1;
                    if (android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -3) != 0) {
                        android.provider.Settings.System.putIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -3);
                    }
                    if (displayId < 0) {
                        displayId = 0;
                    }
                    this.mDisplayManager.setBrightness(displayId, android.util.MathUtils.constrain(com.android.internal.display.BrightnessUtils.convertGammaToLinear(android.util.MathUtils.constrain(com.android.internal.display.BrightnessUtils.convertLinearToGamma(this.mDisplayManager.getBrightness(displayId)) + (i * 0.1f), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f)), this.mPowerManager.getBrightnessConstraint(0), this.mPowerManager.getBrightnessConstraint(1)));
                    android.content.Intent intent = new android.content.Intent("com.android.intent.action.SHOW_BRIGHTNESS_DIALOG");
                    intent.addFlags(327680);
                    intent.putExtra("android.intent.extra.FROM_BRIGHTNESS_KEY", true);
                    startActivityAsUser(intent, android.os.UserHandle.CURRENT_OR_SELF);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.getBrightnessEvent(keyCode));
                    break;
                }
                break;
            case 231:
                android.util.Slog.wtf(TAG, "KEYCODE_VOICE_ASSIST should be handled in interceptKeyBeforeQueueing");
                break;
            case 264:
                if (!prepareToSendSystemKeyToApplication(iBinder, keyEvent)) {
                    sendSystemKeyToStatusBarAsync(keyEvent);
                    break;
                }
                break;
            case 284:
                if (!z3) {
                    this.mHandler.removeMessages(22);
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(22);
                    obtainMessage.setAsynchronous(true);
                    obtainMessage.sendToTarget();
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.ALL_APPS);
                    break;
                }
                break;
            case 289:
            case 290:
            case 291:
            case 292:
            case 293:
            case 294:
            case 295:
            case 296:
            case 297:
            case com.android.internal.util.FrameworkStatsLog.BLOB_COMMITTED /* 298 */:
            case com.android.internal.util.FrameworkStatsLog.BLOB_LEASED /* 299 */:
            case 300:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_ALARM_CLOCK /* 301 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_WHILE_IDLE /* 302 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SERVICE_LAUNCH /* 303 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_KEY_CHAIN /* 304 */:
                android.util.Slog.wtf(TAG, "KEYCODE_APP_X should be handled in interceptKeyBeforeQueueing");
                break;
            case 305:
                if (z3) {
                    this.mInputManagerInternal.decrementKeyboardBacklight(keyEvent.getDeviceId());
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.KEYBOARD_BACKLIGHT_DOWN);
                    break;
                }
                break;
            case 306:
                if (z3) {
                    this.mInputManagerInternal.incrementKeyboardBacklight(keyEvent.getDeviceId());
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.KEYBOARD_BACKLIGHT_UP);
                    break;
                }
                break;
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_DOMAIN_VERIFICATION_V1 /* 307 */:
                if (!z3) {
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.KEYBOARD_BACKLIGHT_TOGGLE);
                    break;
                }
                break;
            case 308:
            case 309:
            case 310:
            case 311:
                android.util.Slog.wtf(TAG, "KEYCODE_STYLUS_BUTTON_* should be handled in interceptKeyBeforeQueueing");
                break;
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_LOCATION_PROVIDER /* 312 */:
                if (z4) {
                    showRecentApps(false);
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.RECENT_APPS);
                    break;
                }
                break;
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ROLE_DIALER /* 318 */:
                if (com.android.hardware.input.Flags.emojiAndScreenshotKeycodesAvailable() && z3 && repeatCount == 0) {
                    interceptScreenshotChord(2, 0L);
                    break;
                }
                break;
            default:
                z = false;
                if (!isValidGlobalKey(keyCode)) {
                }
                if (!dispatchKeyToKeyHandlers(keyEvent)) {
                }
                break;
        }
        return true;
    }

    private boolean prepareToSendSystemKeyToApplication(android.os.IBinder iBinder, android.view.KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = false;
        if (!keyEvent.isSystem()) {
            android.util.Log.wtf(TAG, "Illegal keycode provided to prepareToSendSystemKeyToApplication: " + android.view.KeyEvent.keyCodeToString(keyCode));
            return false;
        }
        if ((keyEvent.getAction() == 0) && keyEvent.getRepeatCount() == 0) {
            com.android.internal.policy.KeyInterceptionInfo keyInterceptionInfoFromToken = this.mWindowManagerInternal.getKeyInterceptionInfoFromToken(iBinder);
            if (keyInterceptionInfoFromToken != null && this.mButtonOverridePermissionChecker.canAppOverrideSystemKey(this.mContext, keyInterceptionInfoFromToken.windowOwnerUid)) {
                return true;
            }
            setDeferredKeyActionsExecutableAsync(keyCode, keyEvent.getDownTime());
            return false;
        }
        java.util.Set<java.lang.Integer> set = this.mConsumedKeysForDevice.get(keyEvent.getDeviceId());
        if (set != null && set.contains(java.lang.Integer.valueOf(keyCode))) {
            z = true;
        }
        return !z;
    }

    private void setDeferredKeyActionsExecutableAsync(int i, long j) {
        android.os.Message obtain = android.os.Message.obtain(this.mHandler, 27);
        obtain.arg1 = i;
        obtain.obj = java.lang.Long.valueOf(j);
        obtain.setAsynchronous(true);
        obtain.sendToTarget();
    }

    @android.annotation.SuppressLint({"MissingPermission"})
    private void injectBackGesture(long j) {
        android.view.KeyEvent keyEvent = new android.view.KeyEvent(j, j, 0, 4, 0, 0, -1, 0, 72, 257);
        this.mInputManager.injectInputEvent(keyEvent, 0);
        android.view.KeyEvent changeAction = android.view.KeyEvent.changeAction(keyEvent, 1);
        this.mInputManager.injectInputEvent(changeAction, 0);
        keyEvent.recycle();
        changeAction.recycle();
    }

    private boolean handleHomeShortcuts(android.os.IBinder iBinder, android.view.KeyEvent keyEvent) {
        com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler displayHomeButtonHandler = this.mDisplayHomeButtonHandlers.get(keyEvent.getDisplayId());
        if (displayHomeButtonHandler == null) {
            displayHomeButtonHandler = new com.android.server.policy.PhoneWindowManager.DisplayHomeButtonHandler(keyEvent.getDisplayId());
            this.mDisplayHomeButtonHandlers.put(keyEvent.getDisplayId(), displayHomeButtonHandler);
        }
        return displayHomeButtonHandler.handleHomeButton(iBinder, keyEvent);
    }

    private void toggleMicrophoneMuteFromKey() {
        int i;
        if (this.mSensorPrivacyManager.supportsSensorToggle(1, 1)) {
            boolean isSensorPrivacyEnabled = this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 1);
            this.mSensorPrivacyManager.setSensorPrivacy(1, !isSensorPrivacyEnabled);
            if (isSensorPrivacyEnabled) {
                i = android.R.string.mediasize_na_gvrnmt_letter;
            } else {
                i = android.R.string.mediasize_na_foolscap;
            }
            android.widget.Toast.makeText(this.mContext, com.android.server.UiThread.get().getLooper(), this.mContext.getString(i), 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void interceptBugreportGestureTv() {
        this.mHandler.removeMessages(18);
        android.os.Message obtain = android.os.Message.obtain(this.mHandler, 18);
        obtain.setAsynchronous(true);
        this.mHandler.sendMessageDelayed(obtain, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelBugreportGestureTv() {
        this.mHandler.removeMessages(18);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void interceptAccessibilityGestureTv() {
        this.mHandler.removeMessages(19);
        android.os.Message obtain = android.os.Message.obtain(this.mHandler, 19);
        obtain.setAsynchronous(true);
        this.mHandler.sendMessageDelayed(obtain, getAccessibilityShortcutTimeout());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAccessibilityGestureTv() {
        this.mHandler.removeMessages(19);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBugreportForTv() {
        try {
            if (!android.app.ActivityManager.getService().launchBugReportHandlerApp()) {
                android.app.ActivityManager.getService().requestInteractiveBugReport();
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error taking bugreport", e);
        }
    }

    private boolean dispatchKeyToKeyHandlers(android.view.KeyEvent keyEvent) {
        java.util.Iterator<com.android.internal.os.DeviceKeyHandler> it = this.mDeviceKeyHandlers.iterator();
        while (it.hasNext()) {
            try {
                keyEvent = it.next().handleKeyEvent(keyEvent);
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Could not dispatch event to device key handler", e);
            }
            if (keyEvent == null) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public android.view.KeyEvent dispatchUnhandledKey(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        android.view.KeyCharacterMap.FallbackAction fallbackAction;
        if (!interceptUnhandledKey(keyEvent, iBinder) && (keyEvent.getFlags() & 1024) == 0) {
            android.view.KeyCharacterMap keyCharacterMap = keyEvent.getKeyCharacterMap();
            int keyCode = keyEvent.getKeyCode();
            int metaState = keyEvent.getMetaState();
            boolean z = keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0;
            if (z) {
                fallbackAction = keyCharacterMap.getFallbackAction(keyCode, metaState);
            } else {
                fallbackAction = this.mFallbackActions.get(keyCode);
            }
            if (fallbackAction != null) {
                android.view.KeyEvent obtain = android.view.KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), fallbackAction.keyCode, keyEvent.getRepeatCount(), fallbackAction.metaState, keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags() | 1024, keyEvent.getSource(), keyEvent.getDisplayId(), null);
                if (!interceptFallback(iBinder, obtain, i)) {
                    obtain.recycle();
                    obtain = null;
                }
                if (z) {
                    this.mFallbackActions.put(keyCode, fallbackAction);
                    return obtain;
                }
                if (keyEvent.getAction() != 1) {
                    return obtain;
                }
                this.mFallbackActions.remove(keyCode);
                fallbackAction.recycle();
                return obtain;
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean interceptUnhandledKey(android.view.KeyEvent keyEvent, android.os.IBinder iBinder) {
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        boolean z = keyEvent.getAction() == 0;
        int modifiers = keyEvent.getModifiers();
        switch (keyCode) {
            case 54:
                if (z && android.view.KeyEvent.metaStateHasModifiers(modifiers, com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3) && this.mAccessibilityShortcutController.isAccessibilityShortcutAvailable(isKeyguardLocked())) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(17));
                    return true;
                }
                return false;
            case 62:
                if (z && repeatCount == 0 && android.view.KeyEvent.metaStateHasModifiers(modifiers & (-194), 4096)) {
                    sendSwitchKeyboardLayout(keyEvent, iBinder, (modifiers & 193) != 0 ? -1 : 1);
                    return true;
                }
                return false;
            case 111:
                if (z && android.view.KeyEvent.metaStateHasNoModifiers(modifiers) && repeatCount == 0) {
                    this.mContext.closeSystemDialogs();
                    return true;
                }
                return false;
            case 120:
                if (z && repeatCount == 0) {
                    interceptScreenshotChord(2, 0L);
                    return true;
                }
                return false;
            case 264:
                handleUnhandledSystemKey(keyEvent);
                sendSystemKeyToStatusBarAsync(keyEvent);
                return true;
            default:
                return false;
        }
    }

    private void handleUnhandledSystemKey(android.view.KeyEvent keyEvent) {
        if (!keyEvent.isSystem()) {
            android.util.Log.wtf(TAG, "Illegal keycode provided to handleUnhandledSystemKey: " + android.view.KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
            return;
        }
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            setDeferredKeyActionsExecutableAsync(keyEvent.getKeyCode(), keyEvent.getDownTime());
        }
    }

    private void sendSwitchKeyboardLayout(@android.annotation.NonNull android.view.KeyEvent keyEvent, @android.annotation.Nullable android.os.IBinder iBinder, int i) {
        this.mHandler.obtainMessage(25, new com.android.server.policy.PhoneWindowManager.SwitchKeyboardLayoutMessageObject(keyEvent, iBinder, i)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSwitchKeyboardLayout(@android.annotation.NonNull android.view.KeyEvent keyEvent, int i, android.os.IBinder iBinder) {
        if (android.util.FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_ui")) {
            com.android.server.inputmethod.InputMethodManagerInternal.get().onSwitchKeyboardLayoutShortcut(i, keyEvent.getDisplayId(), this.mWindowManagerInternal.getTargetWindowTokenFromInputToken(iBinder));
        } else {
            this.mWindowManagerFuncs.switchKeyboardLayout(keyEvent.getDeviceId(), i);
        }
    }

    private boolean interceptFallback(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        if ((interceptKeyBeforeQueueing(keyEvent, i) & 1) != 0 && interceptKeyBeforeDispatching(iBinder, keyEvent, i) == 0 && !interceptUnhandledKey(keyEvent, iBinder)) {
            return true;
        }
        return false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setTopFocusedDisplay(int i) {
        this.mTopFocusedDisplayId = i;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void registerDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) {
        if (this.mDisplayFoldController != null) {
            this.mDisplayFoldController.registerDisplayFoldListener(iDisplayFoldListener);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void unregisterDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) {
        if (this.mDisplayFoldController != null) {
            this.mDisplayFoldController.unregisterDisplayFoldListener(iDisplayFoldListener);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setOverrideFoldedArea(android.graphics.Rect rect) {
        if (this.mDisplayFoldController != null) {
            this.mDisplayFoldController.setOverrideFoldedArea(rect);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public android.graphics.Rect getFoldedArea() {
        if (this.mDisplayFoldController != null) {
            return this.mDisplayFoldController.getFoldedArea();
        }
        return new android.graphics.Rect();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void onDefaultDisplayFocusChangedLw(com.android.server.policy.WindowManagerPolicy.WindowState windowState) {
        if (this.mDisplayFoldController != null) {
            this.mDisplayFoldController.onDefaultDisplayFocusChanged(windowState != null ? windowState.getOwningPackage() : null);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void registerShortcutKey(long j, com.android.internal.policy.IShortcutService iShortcutService) throws android.os.RemoteException {
        synchronized (this.mLock) {
            this.mModifierShortcutManager.registerShortcutKey(j, iShortcutService);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void onKeyguardOccludedChangedLw(boolean z) {
        if (this.mKeyguardDelegate != null) {
            this.mPendingKeyguardOccluded = z;
            this.mKeyguardOccludedChanged = true;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public int applyKeyguardOcclusionChange() {
        if (setKeyguardOccludedLw(this.mPendingKeyguardOccluded)) {
            return 5;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleTransitionForKeyguardLw(boolean z, boolean z2) {
        int i;
        if (!z2) {
            i = 0;
        } else {
            i = applyKeyguardOcclusionChange();
        }
        if (z) {
            startKeyguardExitAnimation(android.os.SystemClock.uptimeMillis());
        }
        return i;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void showDismissibleKeyguard() {
        this.mKeyguardDelegate.showDismissibleKeyguard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchAssistAction(java.lang.String str, int i, long j, int i2) {
        sendCloseSystemWindows(SYSTEM_DIALOG_REASON_ASSIST);
        if (!isUserSetupComplete()) {
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        if (i != -2) {
            bundle.putInt("android.intent.extra.ASSIST_INPUT_DEVICE_ID", i);
        }
        if (str != null) {
            bundle.putBoolean(str, true);
        }
        bundle.putLong("android.intent.extra.TIME", j);
        bundle.putInt("invocation_type", i2);
        android.app.SearchManager searchManager = (android.app.SearchManager) this.mContext.getSystemService(android.app.SearchManager.class);
        if (searchManager != null) {
            searchManager.launchAssist(bundle);
            return;
        }
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.startAssist(bundle);
        }
    }

    private void launchVoiceAssist(boolean z) {
        if (this.mKeyguardDelegate != null && this.mKeyguardDelegate.isShowing()) {
            this.mKeyguardDelegate.dismissKeyguardToLaunch(new android.content.Intent("android.intent.action.VOICE_ASSIST"));
        } else {
            startActivityAsUser(new android.content.Intent("android.intent.action.VOICE_ASSIST"), null, android.os.UserHandle.CURRENT_OR_SELF, z);
        }
    }

    private boolean isInRetailMode() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_demo_mode", 0) == 1;
    }

    private void startActivityAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle);
    }

    private void startActivityAsUser(android.content.Intent intent, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        startActivityAsUser(intent, bundle, userHandle, false);
    }

    private void startActivityAsUser(android.content.Intent intent, android.os.Bundle bundle, android.os.UserHandle userHandle, boolean z) {
        if (z || isUserSetupComplete()) {
            this.mContext.startActivityAsUser(intent, bundle, userHandle);
            dismissKeyboardShortcutsMenu();
        } else {
            android.util.Slog.i(TAG, "Not starting activity because user setup is in progress: " + intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void preloadRecentApps() {
        this.mPreloadedRecentApps = true;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.preloadRecentApps();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPreloadRecentApps() {
        if (this.mPreloadedRecentApps) {
            this.mPreloadedRecentApps = false;
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.cancelPreloadRecentApps();
            }
        }
    }

    private void toggleTaskbar() {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.toggleTaskbar();
        }
    }

    private void toggleRecentApps() {
        this.mPreloadedRecentApps = false;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.toggleRecentApps();
        }
    }

    private void toggleSplitScreen() {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.toggleSplitScreen();
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void showRecentApps() {
        this.mHandler.removeMessages(9);
        this.mHandler.obtainMessage(9).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRecentApps(boolean z) {
        this.mPreloadedRecentApps = false;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.showRecentApps(z);
        }
        dismissKeyboardShortcutsMenu();
    }

    private void toggleKeyboardShortcutsMenu(int i) {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.toggleKeyboardShortcutsMenu(i);
        }
    }

    private void dismissKeyboardShortcutsMenu() {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.dismissKeyboardShortcutsMenu();
        }
    }

    private void hideRecentApps(boolean z, boolean z2) {
        this.mPreloadedRecentApps = false;
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.hideRecentApps(z, z2);
        }
    }

    private void enterStageSplitFromRunningApp(boolean z) {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.enterStageSplitFromRunningApp(z);
        }
    }

    void launchHomeFromHotKey(int i) {
        launchHomeFromHotKey(i, true, true);
    }

    void launchHomeFromHotKey(final int i, final boolean z, boolean z2) {
        if (z2) {
            if (isKeyguardShowingAndNotOccluded()) {
                return;
            }
            if (!isKeyguardOccluded() && this.mKeyguardDelegate.isInputRestricted()) {
                this.mKeyguardDelegate.verifyUnlock(new com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult() { // from class: com.android.server.policy.PhoneWindowManager.13
                    @Override // com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult
                    public void onKeyguardExitResult(boolean z3) {
                        if (z3) {
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                com.android.server.policy.PhoneWindowManager.this.startDockOrHome(i, true, z);
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                });
                return;
            }
        }
        if (this.mRecentsVisible) {
            try {
                android.app.ActivityManager.getService().stopAppSwitches();
            } catch (android.os.RemoteException e) {
            }
            if (z) {
                awakenDreams();
            }
            hideRecentApps(false, true);
            return;
        }
        if (this.mDefaultDisplayPolicy.isScreenOnFully()) {
            startDockOrHome(i, true, z);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setRecentsVisibilityLw(boolean z) {
        this.mRecentsVisible = z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setPipVisibilityLw(boolean z) {
        this.mPictureInPictureVisible = z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setNavBarVirtualKeyHapticFeedbackEnabledLw(boolean z) {
        this.mNavBarVirtualKeyHapticFeedbackEnabled = z;
    }

    private boolean setKeyguardOccludedLw(boolean z) {
        this.mKeyguardOccludedChanged = false;
        this.mPendingKeyguardOccluded = z;
        this.mKeyguardDelegate.setOccluded(z, true);
        return this.mKeyguardDelegate.isShowing();
    }

    private void setVolumeWakeTriggered(int i, boolean z) {
        switch (i) {
            case 24:
                this.mVolumeUpWakeTriggered = z;
                break;
            case 25:
                this.mVolumeDownWakeTriggered = z;
                break;
            case 164:
                this.mVolumeMuteWakeTriggered = z;
                break;
            default:
                android.util.Log.w(TAG, "setVolumeWakeTriggered: unexpected keyCode=" + i);
                break;
        }
    }

    private boolean getVolumeWakeTriggered(int i) {
        switch (i) {
            case 24:
                return this.mVolumeUpWakeTriggered;
            case 25:
                return this.mVolumeDownWakeTriggered;
            case 164:
                return this.mVolumeMuteWakeTriggered;
            default:
                android.util.Log.w(TAG, "getVolumeWakeTriggered: unexpected keyCode=" + i);
                return false;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void notifyLidSwitchChanged(long j, boolean z) {
        if (z == this.mDefaultDisplayPolicy.getLidState()) {
            return;
        }
        this.mDefaultDisplayPolicy.setLidState(z ? 1 : 0);
        applyLidSwitchState();
        updateRotation(true);
        if (!z) {
            if (getLidBehavior() != 1) {
                this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), false);
                return;
            }
            return;
        }
        this.mWindowWakeUpPolicy.wakeUpFromLid();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void notifyCameraLensCoverSwitchChanged(long j, boolean z) {
        android.content.Intent intent;
        if (this.mCameraLensCoverState == z || !this.mContext.getResources().getBoolean(android.R.bool.config_jobSchedulerRestrictBackgroundUser)) {
            return;
        }
        if (this.mCameraLensCoverState == 1 && !z) {
            if (this.mKeyguardDelegate == null ? false : this.mKeyguardDelegate.isShowing()) {
                intent = new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE");
            } else {
                intent = new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA");
            }
            this.mWindowWakeUpPolicy.wakeUpFromCameraCover(j / 1000000);
            startActivityAsUser(intent, android.os.UserHandle.CURRENT_OR_SELF);
        }
        this.mCameraLensCoverState = z ? 1 : 0;
    }

    void initializeHdmiState() {
        int allowThreadDiskReadsMask = android.os.StrictMode.allowThreadDiskReadsMask();
        try {
            initializeHdmiStateInternal();
        } finally {
            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:22:0x0047
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    void initializeHdmiStateInternal() {
        /*
            r9 = this;
            java.lang.String r0 = "Couldn't read hdmi state from /sys/class/switch/hdmi/state: "
            java.lang.String r1 = "WindowManager"
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "/sys/devices/virtual/switch/hdmi/state"
            r2.<init>(r3)
            boolean r2 = r2.exists()
            r3 = 0
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L90
            android.os.UEventObserver r2 = r9.mHDMIObserver
            java.lang.String r6 = "DEVPATH=/devices/virtual/switch/hdmi"
            r2.startObserving(r6)
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Throwable -> L49 java.lang.NumberFormatException -> L4b java.io.IOException -> L50
            java.lang.String r6 = "/sys/class/switch/hdmi/state"
            r2.<init>(r6)     // Catch: java.lang.Throwable -> L49 java.lang.NumberFormatException -> L4b java.io.IOException -> L50
            r3 = 15
            char[] r3 = new char[r3]     // Catch: java.lang.Throwable -> L3b java.lang.NumberFormatException -> L3e java.io.IOException -> L40
            int r6 = r2.read(r3)     // Catch: java.lang.Throwable -> L3b java.lang.NumberFormatException -> L3e java.io.IOException -> L40
            if (r6 <= r4) goto L42
            java.lang.String r7 = new java.lang.String     // Catch: java.lang.Throwable -> L3b java.lang.NumberFormatException -> L3e java.io.IOException -> L40
            int r6 = r6 - r4
            r7.<init>(r3, r5, r6)     // Catch: java.lang.Throwable -> L3b java.lang.NumberFormatException -> L3e java.io.IOException -> L40
            int r0 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.Throwable -> L3b java.lang.NumberFormatException -> L3e java.io.IOException -> L40
            if (r0 == 0) goto L42
            r5 = r4
            goto L42
        L3b:
            r0 = move-exception
            r3 = r2
            goto L88
        L3e:
            r3 = move-exception
            goto L55
        L40:
            r3 = move-exception
            goto L6e
        L42:
            r2.close()     // Catch: java.io.IOException -> L47
        L46:
            goto L87
        L47:
            r0 = move-exception
            goto L46
        L49:
            r0 = move-exception
            goto L88
        L4b:
            r2 = move-exception
            r8 = r3
            r3 = r2
            r2 = r8
            goto L55
        L50:
            r2 = move-exception
            r8 = r3
            r3 = r2
            r2 = r8
            goto L6e
        L55:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b
            r6.<init>()     // Catch: java.lang.Throwable -> L3b
            r6.append(r0)     // Catch: java.lang.Throwable -> L3b
            r6.append(r3)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r0 = r6.toString()     // Catch: java.lang.Throwable -> L3b
            android.util.Slog.w(r1, r0)     // Catch: java.lang.Throwable -> L3b
            if (r2 == 0) goto L87
            r2.close()     // Catch: java.io.IOException -> L47
            goto L46
        L6e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3b
            r6.<init>()     // Catch: java.lang.Throwable -> L3b
            r6.append(r0)     // Catch: java.lang.Throwable -> L3b
            r6.append(r3)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r0 = r6.toString()     // Catch: java.lang.Throwable -> L3b
            android.util.Slog.w(r1, r0)     // Catch: java.lang.Throwable -> L3b
            if (r2 == 0) goto L87
            r2.close()     // Catch: java.io.IOException -> L47
            goto L46
        L87:
            goto Lb1
        L88:
            if (r3 == 0) goto L8f
            r3.close()     // Catch: java.io.IOException -> L8e
            goto L8f
        L8e:
            r1 = move-exception
        L8f:
            throw r0
        L90:
            java.lang.String r0 = "HDMI"
            java.lang.String[] r0 = new java.lang.String[]{r0}
            java.util.List r0 = com.android.server.ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(r0)
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto Lb1
            com.android.server.policy.PhoneWindowManager$HdmiVideoExtconUEventObserver r1 = new com.android.server.policy.PhoneWindowManager$HdmiVideoExtconUEventObserver
            r1.<init>()
            java.lang.Object r0 = r0.get(r5)
            com.android.server.ExtconUEventObserver$ExtconInfo r0 = (com.android.server.ExtconUEventObserver.ExtconInfo) r0
            boolean r5 = com.android.server.policy.PhoneWindowManager.HdmiVideoExtconUEventObserver.m6582$$Nest$minit(r1, r0)
            r9.mHDMIObserver = r1
        Lb1:
            com.android.server.wm.DisplayPolicy r0 = r9.mDefaultDisplayPolicy
            r0.setHdmiPlugged(r5, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.PhoneWindowManager.initializeHdmiStateInternal():void");
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public int interceptKeyBeforeQueueing(android.view.KeyEvent keyEvent, int i) {
        int i2;
        boolean z;
        java.lang.String str;
        boolean z2;
        int i3;
        com.android.server.policy.PhoneWindowManager.HdmiControl hdmiControl;
        int keyCode = keyEvent.getKeyCode();
        boolean z3 = keyEvent.getAction() == 0;
        boolean z4 = (i & 1) != 0 || keyEvent.isWakeKey();
        if (!this.mSystemBooted) {
            if (z3 && (keyCode == 26 || keyCode == 177)) {
                wakeUpFromWakeKey(keyEvent);
            } else if (z3 && ((z4 || keyCode == 224) && isWakeKeyWhenScreenOff(keyCode))) {
                wakeUpFromWakeKey(keyEvent);
            } else {
                r11 = false;
            }
            if (r11 && (hdmiControl = getHdmiControl()) != null) {
                hdmiControl.turnOnTv();
            }
            return 0;
        }
        boolean z5 = (536870912 & i) != 0;
        boolean isCanceled = keyEvent.isCanceled();
        int displayId = keyEvent.getDisplayId();
        boolean z6 = (16777216 & i) != 0;
        boolean z7 = this.mKeyguardDelegate != null && (!z5 ? !this.mKeyguardDelegate.isShowing() : !isKeyguardShowingAndNotOccluded());
        if (z5 || (z6 && !z4)) {
            if (z5) {
                int i4 = (keyCode != this.mPendingWakeKey || z3) ? 1 : 0;
                this.mPendingWakeKey = -1;
                i2 = i4;
                z = false;
            } else {
                i2 = 1;
                z = false;
            }
        } else if (shouldDispatchInputWhenNonInteractive(displayId, keyCode)) {
            boolean z8 = z4 && isWakeKeyEnabled(keyCode);
            this.mPendingWakeKey = -1;
            z = z8;
            i2 = 1;
        } else {
            if (z4 && (!z3 || !isWakeKeyWhenScreenOff(keyCode))) {
                z4 = false;
            }
            if (z4 && z3) {
                this.mPendingWakeKey = keyCode;
            }
            z = z4;
            i2 = 0;
        }
        if (isValidGlobalKey(keyCode) && this.mGlobalKeyManager.shouldHandleGlobalKey(keyCode)) {
            if (!z5 && z && z3 && this.mGlobalKeyManager.shouldDispatchFromNonInteractive(keyCode)) {
                this.mGlobalKeyManager.setBeganFromNonInteractive();
                this.mPendingWakeKey = -1;
                i2 = 1;
            }
            if (z) {
                wakeUpFromWakeKey(keyEvent, true);
            }
            return i2;
        }
        android.hardware.hdmi.HdmiControlManager hdmiControlManager = getHdmiControlManager();
        if (keyCode == 177 && this.mHasFeatureLeanback && (hdmiControlManager == null || !hdmiControlManager.shouldHandleTvPowerKey())) {
            return interceptKeyBeforeQueueing(android.view.KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), 26, keyEvent.getRepeatCount(), keyEvent.getMetaState(), keyEvent.getDeviceId(), keyEvent.getScanCode(), keyEvent.getFlags(), keyEvent.getSource(), keyEvent.getDisplayId(), null), i);
        }
        boolean isOnState = android.view.Display.isOnState(this.mDefaultDisplay.getState());
        boolean z9 = z5 && this.mDefaultDisplayPolicy.isAwake();
        if ((keyEvent.getFlags() & 1024) == 0) {
            handleKeyGesture(keyEvent, z9, isOnState);
        }
        boolean z10 = z3 && (i & 2) != 0 && (!((keyEvent.getFlags() & 64) != 0) || this.mNavBarVirtualKeyHapticFeedbackEnabled) && keyEvent.getRepeatCount() == 0;
        if (dispatchKeyToKeyHandlers(keyEvent)) {
            return 0;
        }
        boolean z11 = z9;
        switch (keyCode) {
            case 3:
                str = TAG;
                z2 = z10;
                z2 = z10;
                if (z3 && !z5) {
                    boolean z12 = this.mWakeOnHomeKeyPress;
                    if (!z12) {
                        z = z12;
                        z2 = false;
                        break;
                    } else {
                        z = z12;
                        z2 = z10;
                        break;
                    }
                }
                break;
            case 4:
                str = TAG;
                boolean z13 = (keyEvent.getFlags() & 2048) != 0;
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.BACK);
                if (!this.mLongSwipeDown || !z13 || z3) {
                    this.mLongSwipeDown = z13 && z3;
                    if (!this.mLongSwipeDown) {
                        if (!z3) {
                            if (!hasLongPressOnBackBehavior()) {
                                this.mBackKeyHandled |= backKeyPress();
                            }
                            z2 = z10;
                            if (this.mBackKeyHandled) {
                                i2 = 0;
                                z2 = z10;
                                break;
                            }
                        } else {
                            this.mWindowManagerInternal.moveFocusToTopEmbeddedWindowIfNeeded();
                            this.mBackKeyHandled = false;
                            z2 = z10;
                            if (keyEvent.getRepeatCount() > 0) {
                                z2 = z10;
                                if (hasLongPressOnBackBehavior()) {
                                    i2 = 0;
                                    z2 = z10;
                                    break;
                                }
                            }
                        }
                    } else {
                        i2 = 0;
                        z2 = z10;
                        break;
                    }
                } else {
                    performKeyAction(this.mEdgeLongSwipeAction, keyEvent);
                    this.mLongSwipeDown = false;
                    i2 = 0;
                    z2 = z10;
                    break;
                }
                break;
            case 5:
                str = TAG;
                z2 = z10;
                if (z3) {
                    android.telecom.TelecomManager telecommService = getTelecommService();
                    z2 = z10;
                    if (telecommService != null) {
                        z2 = z10;
                        if (telecommService.isRinging()) {
                            android.util.Log.i(str, "interceptKeyBeforeQueueing: CALL key-down while ringing: Answer the call!");
                            telecommService.acceptRingingCall();
                            i2 = 0;
                            z2 = z10;
                            break;
                        }
                    }
                }
                break;
            case 6:
                str = TAG;
                if (!z3) {
                    if (!this.mEndCallKeyHandled) {
                        this.mHandler.removeCallbacks(this.mEndCallLongPress);
                        if (!isCanceled && (((this.mEndcallBehavior & 1) == 0 || !goHome()) && (this.mEndcallBehavior & 2) != 0)) {
                            sleepDefaultDisplay(keyEvent.getEventTime(), 4, 0);
                            z = false;
                            i2 = 0;
                            z2 = z10;
                            break;
                        }
                    }
                } else {
                    android.telecom.TelecomManager telecommService2 = getTelecommService();
                    boolean endCall = telecommService2 != null ? telecommService2.endCall() : false;
                    if (!z5 || endCall) {
                        this.mEndCallKeyHandled = true;
                    } else {
                        this.mEndCallKeyHandled = false;
                        this.mHandler.postDelayed(this.mEndCallLongPress, android.view.ViewConfiguration.get(this.mContext).getDeviceGlobalActionKeyTimeout());
                    }
                }
                i2 = 0;
                z2 = z10;
                break;
            case 24:
            case 25:
            case 164:
                if (!z || !this.mWakeOnVolumeKeyPress) {
                    if (getVolumeWakeTriggered(keyCode) && !z3) {
                        setVolumeWakeTriggered(keyCode, false);
                        i2 = 0;
                        str = TAG;
                        z2 = z10;
                        break;
                    } else {
                        logKeyboardSystemsEventOnActionDown(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.getVolumeEvent(keyCode));
                        if (z3) {
                            sendSystemKeyToStatusBarAsync(keyEvent);
                            android.app.NotificationManager notificationService = getNotificationService();
                            if (notificationService != null && !this.mHandleVolumeKeysInWM) {
                                notificationService.silenceNotificationSound();
                            }
                            android.telecom.TelecomManager telecommService3 = getTelecommService();
                            if (telecommService3 == null || this.mHandleVolumeKeysInWM) {
                                str = TAG;
                            } else if (telecommService3.isRinging()) {
                                if (this.mVolumeAnswerCall) {
                                    telecommService3.acceptRingingCall();
                                }
                                str = TAG;
                                android.util.Log.i(str, "interceptKeyBeforeQueueing: VOLUME key-down while ringing: Silence ringer!");
                                telecommService3.silenceRinger();
                                i2 = 0;
                                z2 = z10;
                                break;
                            } else {
                                str = TAG;
                            }
                            try {
                                i3 = getAudioService().getMode();
                            } catch (java.lang.Exception e) {
                                android.util.Log.e(str, "Error getting AudioService in interceptKeyBeforeQueueing.", e);
                                i3 = 0;
                            }
                            if (((telecommService3 != null && telecommService3.isInCall()) || i3 == 3) && (i2 & 1) == 0) {
                                android.media.session.MediaSessionLegacyHelper.getHelper(this.mContext).sendVolumeKeyEvent(keyEvent, Integer.MIN_VALUE, false);
                                z2 = z10;
                                break;
                            }
                        } else {
                            str = TAG;
                        }
                        if (!this.mUseTvRouting && !this.mHandleVolumeKeysInWM) {
                            z2 = z10;
                            if ((i2 & 1) == 0) {
                                z2 = z10;
                                if (!this.mWakeOnVolumeKeyPress) {
                                    z2 = z10;
                                    if (!this.mLineageButtons.handleVolumeKey(keyEvent, z5)) {
                                        android.media.session.MediaSessionLegacyHelper.getHelper(this.mContext).sendVolumeKeyEvent(keyEvent, Integer.MIN_VALUE, true);
                                        z2 = z10;
                                        break;
                                    }
                                }
                            }
                        } else {
                            i2 = 1;
                            z2 = z10;
                            break;
                        }
                    }
                } else {
                    setVolumeWakeTriggered(keyCode, true);
                    str = TAG;
                    z2 = z10;
                    break;
                }
                break;
            case 26:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_POWER);
                com.android.server.policy.EventLogTags.writeInterceptPower(android.view.KeyEvent.actionToString(keyEvent.getAction()), this.mPowerKeyHandled ? 1 : 0, this.mSingleKeyGestureDetector.getKeyPressCounter(26));
                if (z3) {
                    interceptPowerKeyDown(keyEvent, z11);
                } else {
                    interceptPowerKeyUp(keyEvent, isCanceled);
                }
                z = false;
                i2 = 0;
                str = TAG;
                z2 = z10;
                break;
            case 27:
                if (z3 && this.mIsFocusPressed) {
                    this.mIsFocusPressed = false;
                }
                if (!z3) {
                    this.mHandler.removeMessages(101);
                    if (!this.mIsLongPress || !this.mCameraLaunch) {
                        str = TAG;
                        z2 = z10;
                        break;
                    } else {
                        startActivityAsUser(z7 ? new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE") : new android.content.Intent("android.media.action.STILL_IMAGE_CAMERA"), android.os.UserHandle.CURRENT_OR_SELF);
                        z = true;
                        str = TAG;
                        z2 = z10;
                        break;
                    }
                } else {
                    this.mIsLongPress = false;
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(101, new android.view.KeyEvent(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), keyCode, 0));
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessageDelayed(obtainMessage, android.view.ViewConfiguration.getLongPressTimeout());
                    str = TAG;
                    z2 = z10;
                    break;
                }
                break;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 126:
            case 127:
            case 130:
            case 222:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.MEDIA_KEY);
                if (android.media.session.MediaSessionLegacyHelper.getHelper(this.mContext).isGlobalPriorityActive()) {
                    i2 = 0;
                }
                if ((i2 & 1) == 0) {
                    this.mBroadcastWakeLock.acquire();
                    android.os.Message obtainMessage2 = this.mHandler.obtainMessage(3, new android.view.KeyEvent(keyEvent));
                    obtainMessage2.setAsynchronous(true);
                    obtainMessage2.sendToTarget();
                }
                str = TAG;
                z2 = z10;
                break;
            case 80:
                if (!z3 || z5 || !this.mCameraSleepOnRelease) {
                    if (!z3) {
                        if (this.mDefaultDisplayPolicy.isScreenOnFully() && this.mIsFocusPressed) {
                            this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis());
                        } else if (!z5 && this.mCameraSleepOnRelease) {
                            this.mFocusReleasedGoToSleep = true;
                        }
                        this.mIsFocusPressed = false;
                        str = TAG;
                        z2 = z10;
                        break;
                    } else {
                        str = TAG;
                        z2 = z10;
                        break;
                    }
                } else {
                    this.mIsFocusPressed = true;
                    str = TAG;
                    z2 = z10;
                    break;
                }
                break;
            case 91:
                if (z3 && keyEvent.getRepeatCount() == 0) {
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.SYSTEM_MUTE);
                    toggleMicrophoneMuteFromKey();
                }
                str = TAG;
                i2 = 0;
                z2 = z10;
                break;
            case com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_USER_DECLINED_CONSENT /* 171 */:
                if (this.mShortPressOnWindowBehavior != 1) {
                    str = TAG;
                    z2 = z10;
                    break;
                } else if (!this.mPictureInPictureVisible) {
                    str = TAG;
                    z2 = z10;
                    break;
                } else {
                    if (!z3) {
                        showPictureInPictureMenu(keyEvent);
                    }
                    str = TAG;
                    i2 = 0;
                    z2 = z10;
                    break;
                }
            case 177:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.TOGGLE_POWER);
                if (z3 && hdmiControlManager != null) {
                    hdmiControlManager.toggleAndFollowTvPower();
                }
                str = TAG;
                z = false;
                i2 = 0;
                z2 = z10;
                break;
            case 219:
                if (z3 && !z5) {
                    boolean z14 = this.mWakeOnAssistKeyPress;
                    if (!z14) {
                        z = z14;
                        str = TAG;
                        z2 = false;
                        break;
                    } else {
                        z = z14;
                        str = TAG;
                        z2 = z10;
                        break;
                    }
                } else {
                    str = TAG;
                    z2 = z10;
                    break;
                }
            case com.android.internal.util.FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED /* 223 */:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.SLEEP);
                boolean z15 = z10;
                if (!this.mPowerManager.isInteractive()) {
                    z15 = false;
                }
                if (z3) {
                    sleepPress();
                } else {
                    sleepRelease(keyEvent.getEventTime());
                }
                sendSystemKeyToStatusBarAsync(keyEvent);
                str = TAG;
                z = false;
                i2 = 0;
                z2 = z15;
                break;
            case com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS /* 224 */:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.WAKEUP);
                str = TAG;
                z = true;
                i2 = 0;
                z2 = z10;
                break;
            case 231:
                if (!z3) {
                    this.mBroadcastWakeLock.acquire();
                    android.os.Message obtainMessage3 = this.mHandler.obtainMessage(12);
                    obtainMessage3.setAsynchronous(true);
                    obtainMessage3.sendToTarget();
                    logKeyboardSystemsEvent(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.LAUNCH_VOICE_ASSISTANT);
                }
                str = TAG;
                i2 = 0;
                z2 = z10;
                break;
            case 264:
                if (!z3 || keyEvent.getRepeatCount() != 0 || (i2 & 1) != 0) {
                    str = TAG;
                    z2 = z10;
                    break;
                } else {
                    setDeferredKeyActionsExecutableAsync(keyCode, keyEvent.getDownTime());
                    str = TAG;
                    z2 = z10;
                    break;
                }
                break;
            case com.android.internal.util.FrameworkStatsLog.TV_TUNER_STATE_CHANGED /* 276 */:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.SLEEP);
                if (!z3) {
                    this.mPowerManagerInternal.setUserInactiveOverrideFromWindowManager();
                }
                sendSystemKeyToStatusBarAsync(keyEvent);
                str = TAG;
                z = false;
                i2 = 0;
                z2 = z10;
                break;
            case com.android.internal.util.FrameworkStatsLog.TV_CAS_SESSION_OPEN_STATUS /* 280 */:
            case com.android.internal.util.FrameworkStatsLog.ASSISTANT_INVOCATION_REPORTED /* 281 */:
            case com.android.internal.util.FrameworkStatsLog.DISPLAY_WAKE_REPORTED /* 282 */:
            case 283:
                logKeyboardSystemsEventOnActionUp(keyEvent, com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.SYSTEM_NAVIGATION);
                interceptSystemNavigationKey(keyEvent);
                str = TAG;
                i2 = 0;
                z2 = z10;
                break;
            case 289:
            case 290:
            case 291:
            case 292:
            case 293:
            case 294:
            case 295:
            case 296:
            case 297:
            case com.android.internal.util.FrameworkStatsLog.BLOB_COMMITTED /* 298 */:
            case com.android.internal.util.FrameworkStatsLog.BLOB_LEASED /* 299 */:
            case 300:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_ALARM_CLOCK /* 301 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_WHILE_IDLE /* 302 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SERVICE_LAUNCH /* 303 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_KEY_CHAIN /* 304 */:
                str = TAG;
                i2 = 0;
                z2 = z10;
                break;
            case 308:
            case 309:
            case 310:
            case 311:
                android.util.Slog.i(TAG, "Stylus buttons event: " + keyCode + " received. Should handle event? " + this.mStylusButtonsEnabled);
                if (this.mStylusButtonsEnabled) {
                    sendSystemKeyToStatusBarAsync(keyEvent);
                }
                str = TAG;
                i2 = 0;
                z2 = z10;
                break;
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_BUTTON /* 313 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_EVENT_SMS /* 314 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_EVENT_MMS /* 315 */:
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SHELL /* 316 */:
                str = TAG;
                i2 = 0;
                z2 = z10;
                break;
            case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK /* 317 */:
                if (!com.android.hardware.input.Flags.emojiAndScreenshotKeycodesAvailable()) {
                    str = TAG;
                    i2 = 0;
                    z2 = z10;
                    break;
                } else {
                    str = TAG;
                    z2 = z10;
                    break;
                }
            default:
                str = TAG;
                z2 = z10;
                break;
        }
        if (z2) {
            performHapticFeedback(1, false, "Virtual Key - Press");
        }
        if (z) {
            wakeUpFromWakeKey(keyEvent, keyEvent.getKeyCode() == 224);
        }
        if ((i2 & 1) != 0 && displayId != -1 && displayId != this.mTopFocusedDisplayId) {
            android.util.Log.i(str, "Attempting to move non-focused display " + displayId + " to top because a key is targeting it");
            this.mWindowManagerFuncs.moveDisplayToTopIfAllowed(displayId);
        }
        return i2;
    }

    private void handleKeyGesture(android.view.KeyEvent keyEvent, boolean z, boolean z2) {
        if (this.mKeyCombinationManager.interceptKey(keyEvent, z)) {
            this.mSingleKeyGestureDetector.reset();
            return;
        }
        if (keyEvent.getKeyCode() == 26 && keyEvent.getAction() == 0) {
            this.mPowerKeyHandled = handleCameraGesture(keyEvent, z);
            if (this.mPowerKeyHandled) {
                this.mSingleKeyGestureDetector.reset();
                return;
            }
        }
        this.mSingleKeyGestureDetector.interceptKey(keyEvent, z, z2);
    }

    private boolean handleCameraGesture(android.view.KeyEvent keyEvent, boolean z) {
        if (this.mGestureLauncherService == null) {
            return false;
        }
        this.mCameraGestureTriggered = false;
        android.util.MutableBoolean mutableBoolean = new android.util.MutableBoolean(false);
        boolean interceptPowerKeyDown = this.mGestureLauncherService.interceptPowerKeyDown(keyEvent, z, mutableBoolean);
        if (!mutableBoolean.value) {
            return interceptPowerKeyDown;
        }
        this.mCameraGestureTriggered = true;
        if (this.mRequestedOrSleepingDefaultDisplay) {
            this.mCameraGestureTriggeredDuringGoingToSleep = true;
            this.mWindowWakeUpPolicy.wakeUpFromPowerKeyCameraGesture();
        }
        return true;
    }

    private void interceptSystemNavigationKey(android.view.KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            if ((!this.mAccessibilityManager.isEnabled() || !this.mAccessibilityManager.sendFingerprintGesture(keyEvent.getKeyCode())) && this.mSystemNavigationKeysEnabled) {
                sendSystemKeyToStatusBarAsync(keyEvent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSystemKeyToStatusBar(android.view.KeyEvent keyEvent) {
        com.android.internal.statusbar.IStatusBarService statusBarService = getStatusBarService();
        if (statusBarService != null) {
            try {
                statusBarService.handleSystemKey(keyEvent);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void sendSystemKeyToStatusBarAsync(android.view.KeyEvent keyEvent) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(21, android.view.KeyEvent.obtain(keyEvent));
        obtainMessage.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage);
    }

    private static boolean isValidGlobalKey(int i) {
        switch (i) {
            case 26:
            case com.android.internal.util.FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED /* 223 */:
            case com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS /* 224 */:
                return false;
            default:
                return true;
        }
    }

    private boolean isWakeKeyEnabled(int i) {
        switch (i) {
            case 4:
                return this.mWakeOnBackKeyPress;
            case 24:
            case 25:
            case 164:
                return this.mWakeOnVolumeKeyPress || this.mDefaultDisplayPolicy.getDockMode() != 0;
            case 27:
            case 80:
                return this.mWakeOnCameraKeyPress;
            case 82:
                return this.mWakeOnMenuKeyPress;
            case com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED /* 187 */:
                return this.mWakeOnAppSwitchKeyPress;
            case 219:
                return this.mWakeOnAssistKeyPress;
            default:
                return true;
        }
    }

    private boolean isWakeKeyWhenScreenOff(int i) {
        switch (i) {
            case 4:
                return this.mWakeOnBackKeyPress;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return this.mWakeOnDpadKeyPress;
            case 24:
            case 25:
            case 164:
                return this.mWakeOnVolumeKeyPress || this.mDefaultDisplayPolicy.getDockMode() != 0;
            case 27:
            case 80:
                return this.mWakeOnCameraKeyPress;
            case 82:
                return this.mWakeOnMenuKeyPress;
            case com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED /* 187 */:
                return this.mWakeOnAppSwitchKeyPress;
            case 219:
                return this.mWakeOnAssistKeyPress;
            case 308:
            case 309:
            case 310:
            case 311:
                return this.mStylusButtonsEnabled;
            default:
                return true;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public int interceptMotionBeforeQueueingNonInteractive(int i, int i2, int i3, long j, int i4) {
        int i5 = i4 & 1;
        if (i5 != 0) {
            if (this.mWindowWakeUpPolicy.wakeUpFromMotion(j / 1000000, i2, i3 == 0)) {
                return 1;
            }
        }
        if (shouldDispatchInputWhenNonInteractive(i, 0)) {
            return 1;
        }
        if (isTheaterModeEnabled() && i5 != 0) {
            if (this.mWindowWakeUpPolicy.wakeUpFromMotion(j / 1000000, i2, i3 == 0)) {
                return 1;
            }
        }
        return 0;
    }

    private boolean shouldDispatchInputWhenNonInteractive(int i, int i2) {
        android.view.Display display;
        boolean z = i == 0 || i == -1;
        if (z) {
            display = this.mDefaultDisplay;
        } else {
            display = this.mDisplayManager.getDisplay(i);
        }
        if (display == null || display.getState() == 1) {
            return false;
        }
        boolean isDozeMode = isDozeMode();
        if ((i2 == 25 || i2 == 24) && isDozeMode) {
            return false;
        }
        if (isKeyguardShowingAndNotOccluded()) {
            return true;
        }
        return z && isDozeMode;
    }

    private void dispatchDirectAudioEvent(android.view.KeyEvent keyEvent) {
        android.hardware.hdmi.HdmiAudioSystemClient audioSystemClient;
        android.hardware.hdmi.HdmiControlManager hdmiControlManager = getHdmiControlManager();
        if (hdmiControlManager != null && !hdmiControlManager.getSystemAudioMode() && shouldCecAudioDeviceForwardVolumeKeysSystemAudioModeOff() && (audioSystemClient = hdmiControlManager.getAudioSystemClient()) != null) {
            audioSystemClient.sendKeyEvent(keyEvent.getKeyCode(), keyEvent.getAction() == 0);
            return;
        }
        try {
            getAudioService().handleVolumeKey(keyEvent, this.mUseTvRouting, this.mContext.getOpPackageName(), TAG);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error dispatching volume key in handleVolumeKey for event:" + keyEvent, e);
        }
    }

    @android.annotation.Nullable
    private android.hardware.hdmi.HdmiControlManager getHdmiControlManager() {
        if (!this.mHasFeatureHdmiCec) {
            return null;
        }
        return (android.hardware.hdmi.HdmiControlManager) this.mContext.getSystemService(android.hardware.hdmi.HdmiControlManager.class);
    }

    private boolean shouldCecAudioDeviceForwardVolumeKeysSystemAudioModeOff() {
        return com.android.internal.os.RoSystemProperties.CEC_AUDIO_DEVICE_FORWARD_VOLUME_KEYS_SYSTEM_AUDIO_MODE_OFF;
    }

    void dispatchMediaKeyWithWakeLock(android.view.KeyEvent keyEvent) {
        if (this.mHavePendingMediaKeyRepeatWithWakeLock) {
            this.mHandler.removeMessages(4);
            this.mHavePendingMediaKeyRepeatWithWakeLock = false;
            this.mBroadcastWakeLock.release();
        }
        dispatchMediaKeyWithWakeLockToAudioService(keyEvent);
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            this.mHavePendingMediaKeyRepeatWithWakeLock = true;
            android.os.Message obtainMessage = this.mHandler.obtainMessage(4, keyEvent);
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessageDelayed(obtainMessage, android.view.ViewConfiguration.getKeyRepeatTimeout());
            return;
        }
        this.mBroadcastWakeLock.release();
    }

    void dispatchMediaKeyRepeatWithWakeLock(android.view.KeyEvent keyEvent) {
        this.mHavePendingMediaKeyRepeatWithWakeLock = false;
        dispatchMediaKeyWithWakeLockToAudioService(android.view.KeyEvent.changeTimeRepeat(keyEvent, android.os.SystemClock.uptimeMillis(), 1, keyEvent.getFlags() | 128));
        this.mBroadcastWakeLock.release();
    }

    void dispatchMediaKeyWithWakeLockToAudioService(android.view.KeyEvent keyEvent) {
        if (this.mActivityManagerInternal.isSystemReady()) {
            android.media.session.MediaSessionLegacyHelper.getHelper(this.mContext).sendMediaButtonEvent(keyEvent, true);
        }
    }

    void launchVoiceAssistWithWakeLock() {
        android.content.Intent intent;
        sendCloseSystemWindows(SYSTEM_DIALOG_REASON_ASSIST);
        if (!keyguardOn()) {
            intent = new android.content.Intent("android.speech.action.WEB_SEARCH");
        } else {
            android.os.DeviceIdleManager deviceIdleManager = (android.os.DeviceIdleManager) this.mContext.getSystemService(android.os.DeviceIdleManager.class);
            if (deviceIdleManager != null) {
                deviceIdleManager.endIdle("voice-search");
            }
            intent = new android.content.Intent("android.speech.action.VOICE_SEARCH_HANDS_FREE");
            intent.putExtra("android.speech.extras.EXTRA_SECURE", true);
        }
        startActivityAsUser(intent, android.os.UserHandle.CURRENT_OR_SELF);
        this.mBroadcastWakeLock.release();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedWakingUpGlobal(int i) {
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedWakingUpGlobal(int i) {
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedGoingToSleepGlobal(int i) {
        this.mDeviceGoingToSleep = true;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedGoingToSleepGlobal(int i) {
        this.mDeviceGoingToSleep = false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedGoingToSleep(int i, int i2) {
        if (i != 0) {
            return;
        }
        this.mRequestedOrSleepingDefaultDisplay = true;
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.onStartedGoingToSleep(i2);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedGoingToSleep(int i, int i2) {
        if (i != 0) {
            return;
        }
        com.android.server.policy.EventLogTags.writeScreenToggled(0);
        com.android.internal.logging.MetricsLogger.histogram(this.mContext, "screen_timeout", this.mLockScreenTimeout / 1000);
        this.mRequestedOrSleepingDefaultDisplay = false;
        this.mDefaultDisplayPolicy.setAwake(false);
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
            updateLockScreenTimeout();
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.onFinishedGoingToSleep(i2, this.mCameraGestureTriggeredDuringGoingToSleep);
        }
        if (this.mDisplayFoldController != null) {
            this.mDisplayFoldController.finishedGoingToSleep();
        }
        this.mCameraGestureTriggeredDuringGoingToSleep = false;
        this.mCameraGestureTriggered = false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startedWakingUp(int i, int i2) {
        if (i != 0) {
            return;
        }
        com.android.server.policy.EventLogTags.writeScreenToggled(1);
        this.mDefaultDisplayPolicy.setAwake(true);
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
            updateLockScreenTimeout();
        }
        this.mDefaultDisplayRotation.updateOrientationListener();
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.onStartedWakingUp(i2, this.mCameraGestureTriggered);
        }
        this.mCameraGestureTriggered = false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void finishedWakingUp(int i, int i2) {
        if (i != 0) {
            return;
        }
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.onFinishedWakingUp();
        }
        if (this.mDisplayFoldController != null) {
            this.mDisplayFoldController.finishedWakingUp();
        }
    }

    private boolean shouldWakeUpWithHomeIntent() {
        return this.mWakeUpToLastStateTimeout > 0 && this.mPowerManagerInternal.getLastWakeup().sleepDurationRealtime > this.mWakeUpToLastStateTimeout;
    }

    private void wakeUpFromWakeKey(android.view.KeyEvent keyEvent) {
        wakeUpFromWakeKey(keyEvent.getEventTime(), keyEvent.getKeyCode(), keyEvent.getAction() == 0, false);
    }

    private void wakeUpFromWakeKey(android.view.KeyEvent keyEvent, boolean z) {
        wakeUpFromWakeKey(keyEvent.getEventTime(), keyEvent.getKeyCode(), keyEvent.getAction() == 0, z);
    }

    private void wakeUpFromWakeKey(long j, int i, boolean z) {
        wakeUpFromWakeKey(j, i, z, false);
    }

    private void wakeUpFromWakeKey(long j, int i, boolean z, boolean z2) {
        if (this.mWindowWakeUpPolicy.wakeUpFromKey(j, i, z, z2)) {
            boolean z3 = i == 3 || i == 26;
            if (shouldWakeUpWithHomeIntent() && z3) {
                startDockOrHome(0, i == 3, true, "Wake from " + android.view.KeyEvent.keyCodeToString(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishKeyguardDrawn() {
        if (!this.mDefaultDisplayPolicy.finishKeyguardDrawn()) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mKeyguardDelegate != null) {
                    this.mHandler.removeMessages(6);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.os.Trace.asyncTraceBegin(32L, TRACE_WAIT_FOR_ALL_WINDOWS_DRAWN_METHOD, -1);
        this.mWindowManagerInternal.waitForAllWindowsDrawn(this.mHandler.obtainMessage(7, -1, 0), 1000L, -1);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurnedOff(int i, boolean z) {
        if (i == 0) {
            updateScreenOffSleepToken(true, z);
            this.mRequestedOrSleepingDefaultDisplay = false;
            this.mDefaultDisplayPolicy.screenTurnedOff();
            synchronized (this.mLock) {
                try {
                    if (this.mKeyguardDelegate != null) {
                        this.mKeyguardDelegate.onScreenTurnedOff();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mDefaultDisplayRotation.updateOrientationListener();
            reportScreenStateToVrManager(false);
        }
    }

    private long getKeyguardDrawnTimeout() {
        if (((com.android.server.SystemServiceManager) com.android.server.LocalServices.getService(com.android.server.SystemServiceManager.class)).isBootCompleted()) {
            return this.mKeyguardDrawnTimeout;
        }
        return 5000L;
    }

    @android.annotation.Nullable
    private com.android.server.wallpaper.WallpaperManagerInternal getWallpaperManagerInternal() {
        if (this.mWallpaperManagerInternal == null) {
            this.mWallpaperManagerInternal = (com.android.server.wallpaper.WallpaperManagerInternal) com.android.server.LocalServices.getService(com.android.server.wallpaper.WallpaperManagerInternal.class);
        }
        return this.mWallpaperManagerInternal;
    }

    private void reportScreenTurningOnToWallpaper(int i) {
        com.android.server.wallpaper.WallpaperManagerInternal wallpaperManagerInternal = getWallpaperManagerInternal();
        if (wallpaperManagerInternal != null) {
            wallpaperManagerInternal.onScreenTurningOn(i);
        }
    }

    private void reportScreenTurnedOnToWallpaper(int i) {
        com.android.server.wallpaper.WallpaperManagerInternal wallpaperManagerInternal = getWallpaperManagerInternal();
        if (wallpaperManagerInternal != null) {
            wallpaperManagerInternal.onScreenTurnedOn(i);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurningOn(int i, com.android.server.policy.WindowManagerPolicy.ScreenOnListener screenOnListener) {
        reportScreenTurningOnToWallpaper(i);
        if (i == 0) {
            android.os.Trace.asyncTraceBegin(32L, "screenTurningOn", 0);
            updateScreenOffSleepToken(false, false);
            this.mDefaultDisplayPolicy.screenTurningOn(screenOnListener);
            this.mBootAnimationDismissable = false;
            synchronized (this.mLock) {
                try {
                    if (this.mKeyguardDelegate != null && this.mKeyguardDelegate.hasKeyguard()) {
                        this.mHandler.removeMessages(6);
                        this.mHandler.sendEmptyMessageDelayed(6, getKeyguardDrawnTimeout());
                        this.mKeyguardDelegate.onScreenTurningOn(this.mKeyguardDrawnCallback);
                    } else {
                        this.mHandler.sendEmptyMessage(5);
                    }
                } finally {
                }
            }
            return;
        }
        this.mScreenOnListeners.put(i, screenOnListener);
        android.os.Trace.asyncTraceBegin(32L, TRACE_WAIT_FOR_ALL_WINDOWS_DRAWN_METHOD, i);
        this.mWindowManagerInternal.waitForAllWindowsDrawn(this.mHandler.obtainMessage(7, i, 0), 1000L, i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurnedOn(int i) {
        reportScreenTurnedOnToWallpaper(i);
        if (i != 0) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mKeyguardDelegate != null) {
                    this.mKeyguardDelegate.onScreenTurnedOn();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mDefaultDisplayPolicy.screenTurnedOn();
        reportScreenStateToVrManager(true);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void screenTurningOff(int i, com.android.server.policy.WindowManagerPolicy.ScreenOffListener screenOffListener) {
        this.mWindowManagerFuncs.screenTurningOff(i, screenOffListener);
        if (i != 0) {
            return;
        }
        this.mRequestedOrSleepingDefaultDisplay = true;
        synchronized (this.mLock) {
            try {
                if (this.mKeyguardDelegate != null) {
                    this.mKeyguardDelegate.onScreenTurningOff();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void reportScreenStateToVrManager(boolean z) {
        if (this.mVrManagerInternal == null) {
            return;
        }
        this.mVrManagerInternal.onScreenStateChanged(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishWindowsDrawn(int i) {
        if (i != 0 && i != -1) {
            com.android.server.policy.WindowManagerPolicy.ScreenOnListener screenOnListener = (com.android.server.policy.WindowManagerPolicy.ScreenOnListener) this.mScreenOnListeners.removeReturnOld(i);
            if (screenOnListener != null) {
                screenOnListener.onScreenOn();
                return;
            }
            return;
        }
        if (!this.mDefaultDisplayPolicy.finishWindowsDrawn()) {
            return;
        }
        finishScreenTurningOn();
    }

    private void finishScreenTurningOn() {
        this.mDefaultDisplayRotation.updateOrientationListener();
        com.android.server.policy.WindowManagerPolicy.ScreenOnListener screenOnListener = this.mDefaultDisplayPolicy.getScreenOnListener();
        if (!this.mDefaultDisplayPolicy.finishScreenTurningOn()) {
            return;
        }
        android.os.Trace.asyncTraceEnd(32L, "screenTurningOn", 0);
        enableScreen(screenOnListener, true);
    }

    private void enableScreen(com.android.server.policy.WindowManagerPolicy.ScreenOnListener screenOnListener, boolean z) {
        boolean z2;
        boolean isAwake = this.mDefaultDisplayPolicy.isAwake();
        synchronized (this.mLock) {
            try {
                if (!this.mKeyguardDrawnOnce && isAwake) {
                    z2 = true;
                    this.mKeyguardDrawnOnce = true;
                    if (this.mBootMessageNeedsHiding) {
                        this.mBootMessageNeedsHiding = false;
                        hideBootMessages();
                    }
                } else {
                    z2 = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z && screenOnListener != null) {
            screenOnListener.onScreenOn();
        }
        if (z2) {
            this.mWindowManagerFuncs.enableScreenIfNeeded();
        }
        if (this.mFocusReleasedGoToSleep) {
            this.mFocusReleasedGoToSleep = false;
            this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHideBootMessage() {
        synchronized (this.mLock) {
            try {
                if (!this.mKeyguardDrawnOnce) {
                    this.mBootMessageNeedsHiding = true;
                } else if (this.mBootMsgDialog != null) {
                    this.mBootMsgDialog.dismiss();
                    this.mBootMsgDialog = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isScreenOn() {
        return this.mDefaultDisplayPolicy.isScreenOnEarly();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean okToAnimate(boolean z) {
        return (z || isScreenOn()) && !this.mDeviceGoingToSleep;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void enableKeyguard(boolean z) {
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.setKeyguardEnabled(z);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void exitKeyguardSecurely(com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult) {
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.verifyUnlock(onKeyguardExitResult);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardShowing() {
        if (this.mKeyguardDelegate == null) {
            return false;
        }
        return this.mKeyguardDelegate.isShowing();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardShowingAndNotOccluded() {
        return (this.mKeyguardDelegate == null || !this.mKeyguardDelegate.isShowing() || isKeyguardOccluded()) ? false : true;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardTrustedLw() {
        if (this.mKeyguardDelegate == null) {
            return false;
        }
        return this.mKeyguardDelegate.isTrusted();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardLocked() {
        return keyguardOn();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardSecure(int i) {
        if (this.mKeyguardDelegate == null) {
            return false;
        }
        return this.mKeyguardDelegate.isSecure(i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardOccluded() {
        if (this.mKeyguardDelegate == null) {
            return false;
        }
        return this.mKeyguardDelegate.isOccluded();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean inKeyguardRestrictedKeyInputMode() {
        if (this.mKeyguardDelegate == null) {
            return false;
        }
        return this.mKeyguardDelegate.isInputRestricted();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardUnoccluding() {
        return keyguardOn() && !this.mWindowManagerFuncs.isAppTransitionStateIdle();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void dismissKeyguardLw(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        if (this.mKeyguardDelegate != null && this.mKeyguardDelegate.isShowing()) {
            this.mKeyguardDelegate.dismiss(iKeyguardDismissCallback, charSequence);
        } else if (iKeyguardDismissCallback != null) {
            try {
                iKeyguardDismissCallback.onDismissError();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to call callback", e);
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isKeyguardDrawnLw() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mKeyguardDrawnOnce;
        }
        return z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void startKeyguardExitAnimation(long j) {
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.startKeyguardExitAnimation(j);
        }
    }

    void sendCloseSystemWindows() {
        com.android.internal.policy.PhoneWindow.sendCloseSystemWindows(this.mContext, (java.lang.String) null);
    }

    void sendCloseSystemWindows(java.lang.String str) {
        com.android.internal.policy.PhoneWindow.sendCloseSystemWindows(this.mContext, str);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setSafeMode(boolean z) {
        this.mSafeMode = z;
        if (z) {
            performHapticFeedback(com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG, true, "Safe Mode Enabled");
        }
    }

    private void bindKeyguard() {
        synchronized (this.mLock) {
            try {
                if (this.mKeyguardBound) {
                    return;
                }
                this.mKeyguardBound = true;
                this.mKeyguardDelegate.bindService(this.mContext);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void onSystemUiStarted() {
        bindKeyguard();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void systemReady() {
        this.mKeyguardDelegate.onSystemReady();
        this.mVrManagerInternal = (com.android.server.vr.VrManagerInternal) com.android.server.LocalServices.getService(com.android.server.vr.VrManagerInternal.class);
        if (this.mVrManagerInternal != null) {
            this.mVrManagerInternal.addPersistentVrModeStateListener(this.mPersistentVrModeListener);
        }
        this.mLineageHardware = lineageos.hardware.LineageHardwareManager.getInstance(this.mContext);
        this.mSettingsObserver.observe();
        readCameraLensCoverState();
        updateUiMode();
        this.mDefaultDisplayRotation.updateOrientationListener();
        synchronized (this.mLock) {
            try {
                this.mSystemReady = true;
                updateSettings(this.mHandler);
                if (this.mSystemBooted) {
                    this.mKeyguardDelegate.onBootCompleted();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mAutofillManagerInternal = (android.view.autofill.AutofillManagerInternal) com.android.server.LocalServices.getService(android.view.autofill.AutofillManagerInternal.class);
        this.mGestureLauncherService = (com.android.server.GestureLauncherService) com.android.server.LocalServices.getService(com.android.server.GestureLauncherService.class);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void systemBooted() {
        boolean z;
        boolean z2;
        bindKeyguard();
        synchronized (this.mLock) {
            try {
                this.mSystemBooted = true;
                if (this.mSystemReady) {
                    this.mKeyguardDelegate.onBootCompleted();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mLineageButtons = new org.lineageos.internal.buttons.LineageButtons(this.mContext);
        this.mSideFpsEventHandler.onFingerprintSensorReady();
        startedWakingUp(0, 0);
        finishedWakingUp(0, 0);
        if (this.mDisplayManager.getDisplay(0).getState() == 2) {
            z = true;
        } else {
            z = false;
        }
        if (this.mDefaultDisplayPolicy.getScreenOnListener() != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z || z2) {
            screenTurningOn(0, this.mDefaultDisplayPolicy.getScreenOnListener());
            screenTurnedOn(0);
        } else {
            this.mBootAnimationDismissable = true;
            enableScreen(null, false);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean canDismissBootAnimation() {
        return this.mDefaultDisplayPolicy.isKeyguardDrawComplete() || this.mBootAnimationDismissable;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void showBootMessage(final java.lang.CharSequence charSequence, boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.PhoneWindowManager.16
            @Override // java.lang.Runnable
            public void run() {
                int i;
                if (com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog == null) {
                    if (com.android.server.policy.PhoneWindowManager.this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                        i = android.R.style.Theme.Leanback.Dialog;
                    } else {
                        i = 0;
                    }
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog = new android.app.ProgressDialog(com.android.server.policy.PhoneWindowManager.this.mContext, i) { // from class: com.android.server.policy.PhoneWindowManager.16.1
                        @Override // android.app.Dialog, android.view.Window.Callback
                        public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
                            return true;
                        }

                        @Override // android.app.Dialog, android.view.Window.Callback
                        public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
                            return true;
                        }

                        @Override // android.app.Dialog, android.view.Window.Callback
                        public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
                            return true;
                        }

                        @Override // android.app.Dialog, android.view.Window.Callback
                        public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
                            return true;
                        }

                        @Override // android.app.Dialog, android.view.Window.Callback
                        public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
                            return true;
                        }

                        @Override // android.app.Dialog, android.view.Window.Callback
                        public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
                            return true;
                        }
                    };
                    if (com.android.server.policy.PhoneWindowManager.this.mPackageManager.isDeviceUpgrading()) {
                        com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.setTitle(android.R.string.android_preparing_apk);
                    } else {
                        com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.setTitle(android.R.string.alternate_eri_file);
                    }
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.setProgressStyle(0);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.setIndeterminate(true);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.getWindow().setType(2021);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.getWindow().addFlags(258);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.getWindow().setDimAmount(1.0f);
                    android.view.WindowManager.LayoutParams attributes = com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.getWindow().getAttributes();
                    attributes.screenOrientation = 5;
                    attributes.setFitInsetsTypes(0);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.getWindow().setAttributes(attributes);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.setCancelable(false);
                    com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.show();
                }
                com.android.server.policy.PhoneWindowManager.this.mBootMsgDialog.setMessage(charSequence);
            }
        });
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void hideBootMessages() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void userActivity(int i, int i2) {
        if (i == 0 && i2 == 2) {
            this.mDefaultDisplayPolicy.onUserActivityEventTouch();
        }
        synchronized (this.mScreenLockTimeout) {
            try {
                if (this.mLockScreenTimerActive) {
                    this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                    this.mHandler.postDelayed(this.mScreenLockTimeout, this.mLockScreenTimeout);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    class ScreenLockTimeout implements java.lang.Runnable {
        android.os.Bundle options;

        ScreenLockTimeout() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                try {
                    if (com.android.server.policy.PhoneWindowManager.this.mKeyguardDelegate != null) {
                        com.android.server.policy.PhoneWindowManager.this.mKeyguardDelegate.doKeyguardTimeout(this.options);
                    }
                    com.android.server.policy.PhoneWindowManager.this.mLockScreenTimerActive = false;
                    com.android.server.policy.PhoneWindowManager.this.mLockNowPending = false;
                    this.options = null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setLockOptions(android.os.Bundle bundle) {
            this.options = bundle;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void lockNow(android.os.Bundle bundle) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
        if (bundle != null) {
            this.mScreenLockTimeout.setLockOptions(bundle);
        }
        this.mHandler.post(this.mScreenLockTimeout);
        synchronized (this.mScreenLockTimeout) {
            this.mLockNowPending = true;
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setAllowLockscreenWhenOn(int i, boolean z) {
        if (z) {
            this.mAllowLockscreenWhenOnDisplays.add(java.lang.Integer.valueOf(i));
        } else {
            this.mAllowLockscreenWhenOnDisplays.remove(java.lang.Integer.valueOf(i));
        }
        updateLockScreenTimeout();
    }

    private void updateLockScreenTimeout() {
        synchronized (this.mScreenLockTimeout) {
            try {
                if (this.mLockNowPending) {
                    android.util.Log.w(TAG, "lockNow pending, ignore updating lockscreen timeout");
                    return;
                }
                boolean z = !this.mAllowLockscreenWhenOnDisplays.isEmpty() && this.mDefaultDisplayPolicy.isAwake() && this.mKeyguardDelegate != null && this.mKeyguardDelegate.isSecure(this.mCurrentUserId);
                if (this.mLockScreenTimerActive != z) {
                    if (z) {
                        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                        this.mHandler.postDelayed(this.mScreenLockTimeout, this.mLockScreenTimeout);
                    } else {
                        this.mHandler.removeCallbacks(this.mScreenLockTimeout);
                    }
                    this.mLockScreenTimerActive = z;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateScreenOffSleepToken(boolean z, boolean z2) {
        if (z) {
            this.mScreenOffSleepTokenAcquirer.acquire(0, z2);
        } else {
            this.mScreenOffSleepTokenAcquirer.release(0);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void enableScreenAfterBoot() {
        readLidState();
        applyLidSwitchState();
        updateRotation(true);
    }

    private void applyLidSwitchState() {
        this.mPowerManager.setKeyboardVisibility(isBuiltInKeyboardVisible());
        if (this.mDefaultDisplayPolicy.getLidState() == 0) {
            switch (getLidBehavior()) {
                case 1:
                    sleepDefaultDisplay(android.os.SystemClock.uptimeMillis(), 3, 1);
                    break;
                case 2:
                    this.mWindowManagerFuncs.lockDeviceNow();
                    break;
            }
        }
        synchronized (this.mLock) {
            updateWakeGestureListenerLp();
        }
        sendLidChangeBroadcast();
    }

    private void sendLidChangeBroadcast() {
        int lidState = this.mDefaultDisplayPolicy.getLidState();
        android.util.Log.d(TAG, "Sending cover change broadcast, lidState=" + lidState);
        android.content.Intent intent = new android.content.Intent("lineageos.intent.action.LID_STATE_CHANGED");
        intent.putExtra("lineageos.intent.extra.LID_STATE", lidState);
        intent.setFlags(536870912);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.SYSTEM);
    }

    void updateUiMode() {
        if (this.mUiModeManager == null) {
            this.mUiModeManager = android.app.IUiModeManager.Stub.asInterface(android.os.ServiceManager.getService("uimode"));
        }
        try {
            this.mUiMode = this.mUiModeManager.getCurrentModeType();
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public int getUiMode() {
        return this.mUiMode;
    }

    void updateRotation(boolean z) {
        this.mWindowManagerFuncs.updateRotation(z, false);
    }

    android.content.Intent createHomeDockIntent() {
        android.content.Intent intent;
        android.content.pm.ActivityInfo activityInfo;
        if (this.mUiMode == 3) {
            if (this.mEnableCarDockHomeCapture) {
                intent = this.mCarDockIntent;
            }
            intent = null;
        } else {
            if (this.mUiMode != 2) {
                if (this.mUiMode == 6) {
                    int dockMode = this.mDefaultDisplayPolicy.getDockMode();
                    intent = (dockMode == 1 || dockMode == 4 || dockMode == 3) ? this.mDeskDockIntent : null;
                } else if (this.mUiMode == 7) {
                    intent = this.mVrHeadsetHomeIntent;
                }
            }
            intent = null;
        }
        if (intent == null) {
            return null;
        }
        android.content.pm.ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(intent, 65664, this.mCurrentUserId);
        if (resolveActivityAsUser == null) {
            activityInfo = null;
        } else {
            activityInfo = resolveActivityAsUser.activityInfo;
        }
        if (activityInfo == null || activityInfo.metaData == null || !activityInfo.metaData.getBoolean("android.dock_home")) {
            return null;
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setClassName(activityInfo.packageName, activityInfo.name);
        return intent2;
    }

    void startDockOrHome(int i, boolean z, boolean z2, java.lang.String str) {
        try {
            android.app.ActivityManager.getService().stopAppSwitches();
        } catch (android.os.RemoteException e) {
        }
        sendCloseSystemWindows(SYSTEM_DIALOG_REASON_HOME_KEY);
        if (z2) {
            awakenDreams();
        }
        if (!this.mHasFeatureAuto && !isUserSetupComplete()) {
            android.util.Slog.i(TAG, "Not going home because user setup is in progress.");
            return;
        }
        android.content.Intent createHomeDockIntent = createHomeDockIntent();
        if (createHomeDockIntent != null) {
            if (z) {
                try {
                    createHomeDockIntent.putExtra("android.intent.extra.FROM_HOME_KEY", z);
                } catch (android.content.ActivityNotFoundException e2) {
                }
            }
            startActivityAsUser(createHomeDockIntent, android.os.UserHandle.CURRENT);
            return;
        }
        this.mActivityTaskManagerInternal.startHomeOnDisplay(this.mUserManagerInternal.getUserAssignedToDisplay(i), str, i, true, z);
    }

    void startDockOrHome(int i, boolean z, boolean z2) {
        startDockOrHome(i, z, z2, "startDockOrHome");
    }

    boolean goHome() {
        if (!isUserSetupComplete()) {
            android.util.Slog.i(TAG, "Not going home because user setup is in progress.");
            return false;
        }
        try {
            if (android.os.SystemProperties.getInt("persist.sys.uts-test-mode", 0) == 1) {
                android.util.Log.d(TAG, "UTS-TEST-MODE");
            } else {
                android.app.ActivityManager.getService().stopAppSwitches();
                sendCloseSystemWindows();
                android.content.Intent createHomeDockIntent = createHomeDockIntent();
                if (createHomeDockIntent != null && android.app.ActivityTaskManager.getService().startActivityAsUser((android.app.IApplicationThread) null, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), createHomeDockIntent, createHomeDockIntent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (android.os.IBinder) null, (java.lang.String) null, 0, 1, (android.app.ProfilerInfo) null, (android.os.Bundle) null, -2) == 1) {
                    return false;
                }
            }
        } catch (android.os.RemoteException e) {
        }
        return android.app.ActivityTaskManager.getService().startActivityAsUser((android.app.IApplicationThread) null, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mHomeIntent, this.mHomeIntent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (android.os.IBinder) null, (java.lang.String) null, 0, 1, (android.app.ProfilerInfo) null, (android.os.Bundle) null, -2) != 1;
    }

    private boolean isTheaterModeEnabled() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performHapticFeedback(int i, boolean z, java.lang.String str) {
        return performHapticFeedback(android.os.Process.myUid(), this.mContext.getOpPackageName(), i, z, str, false);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean isGlobalKey(int i) {
        return this.mGlobalKeyManager.shouldHandleGlobalKey(i);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean performHapticFeedback(int i, java.lang.String str, int i2, boolean z, java.lang.String str2, boolean z2) {
        android.os.VibrationEffect vibrationForHapticFeedback;
        if (!this.mVibrator.hasVibrator() || (vibrationForHapticFeedback = this.mHapticFeedbackVibrationProvider.getVibrationForHapticFeedback(i2)) == null) {
            return false;
        }
        android.os.VibrationAttributes vibrationAttributesForHapticFeedback = this.mHapticFeedbackVibrationProvider.getVibrationAttributesForHapticFeedback(i2, z, z2);
        com.android.server.vibrator.VibratorFrameworkStatsLogger.logPerformHapticsFeedbackIfKeyboard(i, i2);
        this.mVibrator.vibrate(i, str, vibrationForHapticFeedback, str2, vibrationAttributesForHapticFeedback);
        return true;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void keepScreenOnStartedLw() {
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void keepScreenOnStoppedLw() {
        if (isKeyguardShowingAndNotOccluded()) {
            this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), false);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public boolean hasNavigationBar() {
        return this.mDefaultDisplayPolicy.hasNavigationBar();
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setDismissImeOnBackKeyPressed(boolean z) {
        this.mDismissImeOnBackKeyPressed = z;
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setCurrentUserLw(int i) {
        this.mCurrentUserId = i;
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.setCurrentUser(i);
        }
        if (this.mAccessibilityShortcutController != null) {
            this.mAccessibilityShortcutController.setCurrentUser(i);
        }
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.setCurrentUser(i);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void setSwitchingUser(boolean z) {
        this.mKeyguardDelegate.setSwitchingUser(z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169922L, this.mDefaultDisplayRotation.getUserRotationMode());
        protoOutputStream.write(1159641169923L, this.mDefaultDisplayRotation.getUserRotation());
        protoOutputStream.write(1159641169924L, this.mDefaultDisplayRotation.getCurrentAppOrientation());
        protoOutputStream.write(1133871366149L, this.mDefaultDisplayPolicy.isScreenOnFully());
        protoOutputStream.write(1133871366150L, this.mDefaultDisplayPolicy.isKeyguardDrawComplete());
        protoOutputStream.write(1133871366151L, this.mDefaultDisplayPolicy.isWindowManagerDrawComplete());
        protoOutputStream.write(1133871366156L, isKeyguardOccluded());
        protoOutputStream.write(1133871366157L, this.mKeyguardOccludedChanged);
        protoOutputStream.write(1133871366158L, this.mPendingKeyguardOccluded);
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.dumpDebug(protoOutputStream, 1146756268052L);
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.policy.WindowManagerPolicy
    public void dump(java.lang.String str, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print(str);
        printWriter.print("mSafeMode=");
        printWriter.print(this.mSafeMode);
        printWriter.print(" mSystemReady=");
        printWriter.print(this.mSystemReady);
        printWriter.print(" mSystemBooted=");
        printWriter.println(this.mSystemBooted);
        printWriter.print(str);
        printWriter.print("mCameraLensCoverState=");
        printWriter.println(com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs.cameraLensStateToString(this.mCameraLensCoverState));
        printWriter.print(str);
        printWriter.print("mWakeGestureEnabledSetting=");
        printWriter.println(this.mWakeGestureEnabledSetting);
        printWriter.print(str);
        printWriter.print("mUiMode=");
        printWriter.print(android.content.res.Configuration.uiModeToString(this.mUiMode));
        printWriter.print("mEnableCarDockHomeCapture=");
        printWriter.println(this.mEnableCarDockHomeCapture);
        printWriter.print(str);
        printWriter.print("mLidKeyboardAccessibility=");
        printWriter.print(this.mLidKeyboardAccessibility);
        printWriter.print(" mLidNavigationAccessibility=");
        printWriter.print(this.mLidNavigationAccessibility);
        printWriter.print(" getLidBehavior=");
        printWriter.println(lidBehaviorToString(getLidBehavior()));
        printWriter.print(str);
        printWriter.print("mLongPressOnBackBehavior=");
        printWriter.println(longPressOnBackBehaviorToString(this.mLongPressOnBackBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnPowerBehavior=");
        printWriter.println(shortPressOnPowerBehaviorToString(this.mShortPressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnPowerBehavior=");
        printWriter.println(longPressOnPowerBehaviorToString(this.mLongPressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnSettingsBehavior=");
        printWriter.println(shortPressOnSettingsBehaviorToString(this.mShortPressOnSettingsBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnPowerAssistantTimeoutMs=");
        printWriter.println(this.mLongPressOnPowerAssistantTimeoutMs);
        printWriter.print(str);
        printWriter.print("mVeryLongPressOnPowerBehavior=");
        printWriter.println(veryLongPressOnPowerBehaviorToString(this.mVeryLongPressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mDoublePressOnPowerBehavior=");
        printWriter.println(multiPressOnPowerBehaviorToString(this.mDoublePressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mTriplePressOnPowerBehavior=");
        printWriter.println(multiPressOnPowerBehaviorToString(this.mTriplePressOnPowerBehavior));
        printWriter.print(str);
        printWriter.print("mSupportShortPressPowerWhenDefaultDisplayOn=");
        printWriter.println(this.mSupportShortPressPowerWhenDefaultDisplayOn);
        printWriter.print(str);
        printWriter.print("mPowerVolUpBehavior=");
        printWriter.println(powerVolumeUpBehaviorToString(this.mPowerVolUpBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnSleepBehavior=");
        printWriter.println(shortPressOnSleepBehaviorToString(this.mShortPressOnSleepBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnWindowBehavior=");
        printWriter.println(shortPressOnWindowBehaviorToString(this.mShortPressOnWindowBehavior));
        printWriter.print(str);
        printWriter.print("mShortPressOnStemPrimaryBehavior=");
        printWriter.println(shortPressOnStemPrimaryBehaviorToString(this.mShortPressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mDoublePressOnStemPrimaryBehavior=");
        printWriter.println(doublePressOnStemPrimaryBehaviorToString(this.mDoublePressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mTriplePressOnStemPrimaryBehavior=");
        printWriter.println(triplePressOnStemPrimaryBehaviorToString(this.mTriplePressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mLongPressOnStemPrimaryBehavior=");
        printWriter.println(longPressOnStemPrimaryBehaviorToString(this.mLongPressOnStemPrimaryBehavior));
        printWriter.print(str);
        printWriter.print("mAllowStartActivityForLongPressOnPowerDuringSetup=");
        printWriter.println(this.mAllowStartActivityForLongPressOnPowerDuringSetup);
        printWriter.print(str);
        printWriter.print("mHasSoftInput=");
        printWriter.println(this.mHasSoftInput);
        printWriter.print(str);
        printWriter.print("mDismissImeOnBackKeyPressed=");
        printWriter.print(this.mDismissImeOnBackKeyPressed);
        printWriter.print(" mIncallPowerBehavior=");
        printWriter.println(incallPowerBehaviorToString(this.mIncallPowerBehavior));
        printWriter.print(str);
        printWriter.print("mIncallBackBehavior=");
        printWriter.print(incallBackBehaviorToString(this.mIncallBackBehavior));
        printWriter.print(" mEndcallBehavior=");
        printWriter.println(endcallBehaviorToString(this.mEndcallBehavior));
        printWriter.print(str);
        printWriter.println("mDisplayHomeButtonHandlers=");
        for (int i = 0; i < this.mDisplayHomeButtonHandlers.size(); i++) {
            int keyAt = this.mDisplayHomeButtonHandlers.keyAt(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.println(this.mDisplayHomeButtonHandlers.get(keyAt));
        }
        printWriter.print(str);
        printWriter.print("mKeyguardOccluded=");
        printWriter.print(isKeyguardOccluded());
        printWriter.print(" mKeyguardOccludedChanged=");
        printWriter.print(this.mKeyguardOccludedChanged);
        printWriter.print(" mPendingKeyguardOccluded=");
        printWriter.println(this.mPendingKeyguardOccluded);
        printWriter.print(str);
        printWriter.print("mAllowLockscreenWhenOnDisplays=");
        printWriter.print(!this.mAllowLockscreenWhenOnDisplays.isEmpty());
        printWriter.print(" mLockScreenTimeout=");
        printWriter.print(this.mLockScreenTimeout);
        printWriter.print(" mLockScreenTimerActive=");
        printWriter.println(this.mLockScreenTimerActive);
        printWriter.print(str);
        printWriter.print("mKidsModeEnabled=");
        printWriter.println(this.mKidsModeEnabled);
        this.mHapticFeedbackVibrationProvider.dump(str, printWriter);
        this.mGlobalKeyManager.dump(str, printWriter);
        this.mKeyCombinationManager.dump(str, printWriter);
        this.mSingleKeyGestureDetector.dump(str, printWriter);
        this.mDeferredKeyActionExecutor.dump(str, printWriter);
        if (this.mWakeGestureListener != null) {
            this.mWakeGestureListener.dump(printWriter, str);
        }
        if (this.mBurnInProtectionHelper != null) {
            this.mBurnInProtectionHelper.dump(str, printWriter);
        }
        if (this.mKeyguardDelegate != null) {
            this.mKeyguardDelegate.dump(str, printWriter);
        }
        printWriter.print(str);
        printWriter.println("Looper state:");
        this.mHandler.getLooper().dump(new android.util.PrintWriterPrinter(printWriter), str + "  ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelTorchOff() {
        if (this.mTorchOffPendingIntent != null) {
            this.mAlarmManager.cancel(this.mTorchOffPendingIntent);
            this.mTorchOffPendingIntent = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleTorch() {
        cancelTorchOff();
        boolean z = this.mTorchEnabled;
        try {
            java.lang.String rearFlashCameraId = getRearFlashCameraId();
            if (rearFlashCameraId != null) {
                boolean z2 = true;
                this.mCameraManager.setTorchMode(rearFlashCameraId, !this.mTorchEnabled);
                if (this.mTorchEnabled) {
                    z2 = false;
                }
                this.mTorchEnabled = z2;
            }
        } catch (android.hardware.camera2.CameraAccessException e) {
        }
        if (this.mTorchEnabled && !z && this.mTorchTimeout > 0) {
            android.content.Intent intent = new android.content.Intent(ACTION_TORCH_OFF);
            intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
            this.mTorchOffPendingIntent = android.app.PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
            this.mAlarmManager.setExactAndAllowWhileIdle(2, android.os.SystemClock.elapsedRealtime() + (this.mTorchTimeout * 1000), this.mTorchOffPendingIntent);
        }
    }

    private java.lang.String getRearFlashCameraId() throws android.hardware.camera2.CameraAccessException {
        if (this.mRearFlashCameraId != null) {
            return this.mRearFlashCameraId;
        }
        java.lang.String[] cameraIdList = this.mCameraManager.getCameraIdList();
        int length = cameraIdList.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            java.lang.String str = cameraIdList[i];
            android.hardware.camera2.CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
            java.lang.Boolean bool = (java.lang.Boolean) cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE);
            java.lang.Integer num = (java.lang.Integer) cameraCharacteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING);
            if (bool == null || !bool.booleanValue() || num == null || num.intValue() != 1) {
                i++;
            } else {
                this.mRearFlashCameraId = str;
                break;
            }
        }
        return this.mRearFlashCameraId;
    }

    private class TorchModeCallback extends android.hardware.camera2.CameraManager.TorchCallback {
        private TorchModeCallback() {
        }

        @Override // android.hardware.camera2.CameraManager.TorchCallback
        public void onTorchModeChanged(java.lang.String str, boolean z) {
            if (str.equals(com.android.server.policy.PhoneWindowManager.this.mRearFlashCameraId)) {
                com.android.server.policy.PhoneWindowManager.this.mTorchEnabled = z;
                if (!com.android.server.policy.PhoneWindowManager.this.mTorchEnabled) {
                    com.android.server.policy.PhoneWindowManager.this.cancelTorchOff();
                }
            }
        }

        @Override // android.hardware.camera2.CameraManager.TorchCallback
        public void onTorchModeUnavailable(java.lang.String str) {
            if (str.equals(com.android.server.policy.PhoneWindowManager.this.mRearFlashCameraId)) {
                com.android.server.policy.PhoneWindowManager.this.mTorchEnabled = false;
                com.android.server.policy.PhoneWindowManager.this.cancelTorchOff();
            }
        }
    }

    private static java.lang.String endcallBehaviorToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            sb.append("home|");
        }
        if ((i & 2) != 0) {
            sb.append("sleep|");
        }
        int length = sb.length();
        if (length == 0) {
            return "<nothing>";
        }
        return sb.substring(0, length - 1);
    }

    private static java.lang.String incallPowerBehaviorToString(int i) {
        if ((i & 2) != 0) {
            return "hangup";
        }
        return "sleep";
    }

    private static java.lang.String incallBackBehaviorToString(int i) {
        if ((i & 1) != 0) {
            return "hangup";
        }
        return "<nothing>";
    }

    private static java.lang.String longPressOnBackBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "LONG_PRESS_BACK_NOTHING";
            case 1:
                return "LONG_PRESS_BACK_GO_TO_VOICE_ASSIST";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String longPressOnHomeBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "LONG_PRESS_HOME_NOTHING";
            case 1:
                return "LONG_PRESS_HOME_ALL_APPS";
            case 2:
                return "LONG_PRESS_HOME_ASSIST";
            case 3:
                return "LONG_PRESS_HOME_NOTIFICATION_PANEL";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String doubleTapOnHomeBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "DOUBLE_TAP_HOME_NOTHING";
            case 1:
                return "DOUBLE_TAP_HOME_RECENT_SYSTEM_UI";
            case 2:
                return "DOUBLE_TAP_HOME_PIP_MENU";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String shortPressOnPowerBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "SHORT_PRESS_POWER_NOTHING";
            case 1:
                return "SHORT_PRESS_POWER_GO_TO_SLEEP";
            case 2:
                return "SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP";
            case 3:
                return "SHORT_PRESS_POWER_REALLY_GO_TO_SLEEP_AND_GO_HOME";
            case 4:
                return "SHORT_PRESS_POWER_GO_HOME";
            case 5:
                return "SHORT_PRESS_POWER_CLOSE_IME_OR_GO_HOME";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String longPressOnPowerBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "LONG_PRESS_POWER_NOTHING";
            case 1:
                return "LONG_PRESS_POWER_GLOBAL_ACTIONS";
            case 2:
                return "LONG_PRESS_POWER_SHUT_OFF";
            case 3:
                return "LONG_PRESS_POWER_SHUT_OFF_NO_CONFIRM";
            case 4:
                return "LONG_PRESS_POWER_GO_TO_VOICE_ASSIST";
            case 5:
                return "LONG_PRESS_POWER_ASSISTANT";
            case 6:
                return "LONG_PRESS_POWER_TORCH";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String shortPressOnSettingsBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "SHORT_PRESS_SETTINGS_NOTHING";
            case 1:
                return "SHORT_PRESS_SETTINGS_NOTIFICATION_PANEL";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String veryLongPressOnPowerBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "VERY_LONG_PRESS_POWER_NOTHING";
            case 1:
                return "VERY_LONG_PRESS_POWER_GLOBAL_ACTIONS";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String powerVolumeUpBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "POWER_VOLUME_UP_BEHAVIOR_NOTHING";
            case 1:
                return "POWER_VOLUME_UP_BEHAVIOR_MUTE";
            case 2:
                return "POWER_VOLUME_UP_BEHAVIOR_GLOBAL_ACTIONS";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String multiPressOnPowerBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "MULTI_PRESS_POWER_NOTHING";
            case 1:
                return "MULTI_PRESS_POWER_THEATER_MODE";
            case 2:
                return "MULTI_PRESS_POWER_BRIGHTNESS_BOOST";
            case 3:
                return "MULTI_PRESS_POWER_LAUNCH_TARGET_ACTIVITY";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String shortPressOnSleepBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "SHORT_PRESS_SLEEP_GO_TO_SLEEP";
            case 1:
                return "SHORT_PRESS_SLEEP_GO_TO_SLEEP_AND_GO_HOME";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String shortPressOnWindowBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "SHORT_PRESS_WINDOW_NOTHING";
            case 1:
                return "SHORT_PRESS_WINDOW_PICTURE_IN_PICTURE";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String shortPressOnStemPrimaryBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "SHORT_PRESS_PRIMARY_NOTHING";
            case 1:
                return "SHORT_PRESS_PRIMARY_LAUNCH_ALL_APPS";
            case 2:
                return "SHORT_PRESS_PRIMARY_LAUNCH_TARGET_ACTIVITY";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String doublePressOnStemPrimaryBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "DOUBLE_PRESS_PRIMARY_NOTHING";
            case 1:
                return "DOUBLE_PRESS_PRIMARY_SWITCH_RECENT_APP";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String triplePressOnStemPrimaryBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "TRIPLE_PRESS_PRIMARY_NOTHING";
            case 1:
                return "TRIPLE_PRESS_PRIMARY_TOGGLE_ACCESSIBILITY";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String longPressOnStemPrimaryBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "LONG_PRESS_PRIMARY_NOTHING";
            case 1:
                return "LONG_PRESS_PRIMARY_LAUNCH_VOICE_ASSISTANT";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String lidBehaviorToString(int i) {
        switch (i) {
            case 0:
                return "LID_BEHAVIOR_NONE";
            case 1:
                return "LID_BEHAVIOR_SLEEP";
            case 2:
                return "LID_BEHAVIOR_LOCK";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static boolean isLongPressToAssistantEnabled(android.content.Context context) {
        int intForUser = android.provider.Settings.System.getIntForUser(context.getContentResolver(), "clockwork_long_press_to_assistant_enabled", 1, -2);
        if (android.util.Log.isLoggable(TAG, 3)) {
            android.util.Log.d(TAG, "longPressToAssistant = " + intForUser);
        }
        return intForUser == 1;
    }

    private class HdmiVideoExtconUEventObserver extends com.android.server.ExtconStateObserver<java.lang.Boolean> {
        private static final java.lang.String DP_EXIST = "DP=1";
        private static final java.lang.String HDMI_EXIST = "HDMI=1";
        private static final java.lang.String NAME = "hdmi";

        private HdmiVideoExtconUEventObserver() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean init(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo) {
            boolean z;
            try {
                z = parseStateFromFile(extconInfo).booleanValue();
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(com.android.server.policy.PhoneWindowManager.TAG, extconInfo.getStatePath() + " not found while attempting to determine initial state", e);
                z = false;
                startObserving(extconInfo);
                return z;
            } catch (java.io.IOException e2) {
                android.util.Slog.e(com.android.server.policy.PhoneWindowManager.TAG, "Error reading " + extconInfo.getStatePath() + " while attempting to determine initial state", e2);
                z = false;
                startObserving(extconInfo);
                return z;
            }
            startObserving(extconInfo);
            return z;
        }

        @Override // com.android.server.ExtconStateObserver
        public void updateState(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, java.lang.String str, java.lang.Boolean bool) {
            com.android.server.policy.PhoneWindowManager.this.mDefaultDisplayPolicy.setHdmiPlugged(bool.booleanValue());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.ExtconStateObserver
        public java.lang.Boolean parseState(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, java.lang.String str) {
            return java.lang.Boolean.valueOf(str.contains(HDMI_EXIST) || str.contains(DP_EXIST));
        }
    }

    private void launchTargetSearchActivity() {
        android.content.Intent intent;
        if (this.mSearchKeyTargetActivity != null) {
            intent = new android.content.Intent();
            intent.setComponent(this.mSearchKeyTargetActivity);
        } else {
            intent = new android.content.Intent("android.intent.action.WEB_SEARCH");
        }
        intent.addFlags(270532608);
        try {
            startActivityAsUser(intent, android.os.UserHandle.CURRENT_OR_SELF);
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Slog.e(TAG, "Could not resolve activity with : " + intent.getComponent().flattenToString() + " name.");
        }
    }

    static class ButtonOverridePermissionChecker {
        ButtonOverridePermissionChecker() {
        }

        boolean canAppOverrideSystemKey(android.content.Context context, int i) {
            return android.content.PermissionChecker.checkPermissionForDataDelivery(context, "android.permission.OVERRIDE_SYSTEM_KEY_BEHAVIOR_IN_FOCUSED_WINDOW", -1, i, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null) == 0;
        }
    }

    private int getTargetDisplayIdForKeyEvent(android.view.KeyEvent keyEvent) {
        int displayId = keyEvent.getDisplayId();
        if (displayId == -1) {
            displayId = this.mTopFocusedDisplayId;
        }
        if (displayId == -1) {
            return 0;
        }
        return displayId;
    }
}
