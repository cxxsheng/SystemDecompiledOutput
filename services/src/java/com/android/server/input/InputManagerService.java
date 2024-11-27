package com.android.server.input;

/* loaded from: classes2.dex */
public class InputManagerService extends android.hardware.input.IInputManager.Stub implements com.android.server.Watchdog.Monitor {
    public static final int BTN_MOUSE = 272;
    private static final int DEFAULT_VIBRATION_MAGNITUDE = 192;
    private static final java.lang.String EXCLUDED_DEVICES_PATH = "etc/excluded-input-devices.xml";
    private static final int INJECTION_TIMEOUT_MILLIS = 30000;
    public static final int INPUT_OVERLAY_LAYER_GESTURE_MONITOR = 1;
    public static final int INPUT_OVERLAY_LAYER_HANDWRITING_SURFACE = 2;
    public static final int KEY_STATE_DOWN = 1;
    public static final int KEY_STATE_UNKNOWN = -1;
    public static final int KEY_STATE_UP = 0;
    public static final int KEY_STATE_VIRTUAL = 2;
    private static final int MSG_DELIVER_INPUT_DEVICES_CHANGED = 1;
    private static final int MSG_DELIVER_TABLET_MODE_CHANGED = 3;
    private static final int MSG_POINTER_DISPLAY_ID_CHANGED = 4;
    private static final int MSG_RELOAD_DEVICE_ALIASES = 2;
    private static final java.lang.String PORT_ASSOCIATIONS_PATH = "etc/input-port-associations.xml";
    public static final int SW_CAMERA_LENS_COVER = 9;
    public static final int SW_CAMERA_LENS_COVER_BIT = 512;
    public static final int SW_HEADPHONE_INSERT = 2;
    public static final int SW_HEADPHONE_INSERT_BIT = 4;
    public static final int SW_JACK_BITS = 212;
    public static final int SW_JACK_PHYSICAL_INSERT = 7;
    public static final int SW_JACK_PHYSICAL_INSERT_BIT = 128;
    public static final int SW_KEYPAD_SLIDE = 10;
    public static final int SW_KEYPAD_SLIDE_BIT = 1024;
    public static final int SW_LID = 0;
    public static final int SW_LID_BIT = 1;
    public static final int SW_LINEOUT_INSERT = 6;
    public static final int SW_LINEOUT_INSERT_BIT = 64;
    public static final int SW_MICROPHONE_INSERT = 4;
    public static final int SW_MICROPHONE_INSERT_BIT = 16;
    public static final int SW_MUTE_DEVICE = 14;
    public static final int SW_MUTE_DEVICE_BIT = 16384;
    public static final int SW_TABLET_MODE = 1;
    public static final int SW_TABLET_MODE_BIT = 2;
    private static final java.lang.String VELOCITYTRACKER_STRATEGY_PROPERTY = "velocitytracker_strategy";

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private int mAcknowledgedPointerDisplayId;

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private final android.util.SparseArray<com.android.server.input.InputManagerService.AdditionalDisplayInputProperties> mAdditionalDisplayInputProperties;
    private final java.lang.Object mAdditionalDisplayInputPropertiesLock;
    private final java.lang.Object mAssociationsLock;
    private final com.android.server.input.BatteryController mBatteryController;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private final com.android.server.input.InputManagerService.AdditionalDisplayInputProperties mCurrentDisplayProperties;
    private final com.android.server.input.PersistentDataStore mDataStore;

    @com.android.internal.annotations.GuardedBy({"mAssociationsLock"})
    private final java.util.Map<java.lang.String, java.lang.String> mDeviceTypeAssociations;
    private android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private final java.io.File mDoubleTouchGestureEnableFile;

    @com.android.internal.annotations.GuardedBy({"mFocusEventDebugViewLock"})
    @android.annotation.Nullable
    private com.android.server.input.debug.FocusEventDebugView mFocusEventDebugView;
    private final java.lang.Object mFocusEventDebugViewLock;
    private final com.android.server.input.InputManagerService.InputManagerHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"mInputDevicesLock"})
    private android.view.InputDevice[] mInputDevices;

    @com.android.internal.annotations.GuardedBy({"mInputDevicesLock"})
    private final android.util.SparseArray<com.android.server.input.InputManagerService.InputDevicesChangedListenerRecord> mInputDevicesChangedListeners;

    @com.android.internal.annotations.GuardedBy({"mInputDevicesLock"})
    private boolean mInputDevicesChangedPending;
    private final java.lang.Object mInputDevicesLock;

    @com.android.internal.annotations.GuardedBy({"mInputFilterLock"})
    android.view.IInputFilter mInputFilter;

    @com.android.internal.annotations.GuardedBy({"mInputFilterLock"})
    com.android.server.input.InputManagerService.InputFilterHost mInputFilterHost;
    final java.lang.Object mInputFilterLock;
    private com.android.server.inputmethod.InputMethodManagerInternal mInputMethodManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mInputMonitors"})
    final java.util.Map<android.os.IBinder, com.android.server.input.GestureMonitorSpyWindow> mInputMonitors;

    @com.android.internal.annotations.GuardedBy({"mVibratorLock"})
    private final android.util.SparseBooleanArray mIsVibrating;
    private final com.android.server.input.KeyRemapper mKeyRemapper;
    private final com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface mKeyboardBacklightController;

    @com.android.internal.annotations.GuardedBy({"mAssociationsLock"})
    private final java.util.Map<java.lang.String, java.lang.String> mKeyboardLayoutAssociations;
    private final com.android.server.input.KeyboardLayoutManager mKeyboardLayoutManager;

    @com.android.internal.annotations.GuardedBy({"mLidSwitchLock"})
    private final java.util.List<com.android.server.input.InputManagerInternal.LidSwitchCallback> mLidSwitchCallbacks;
    private final java.lang.Object mLidSwitchLock;
    private final java.lang.Object mLightLock;

    @com.android.internal.annotations.GuardedBy({"mLightLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.input.InputManagerService.LightSession> mLightSessions;
    private final com.android.server.input.NativeInputManagerService mNative;
    private int mNextVibratorTokenValue;

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private int mOverriddenPointerDisplayId;

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private android.view.PointerIcon mPointerIcon;
    private final com.android.server.input.PointerIconCache mPointerIconCache;

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private int mPointerIconType;

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private int mRequestedPointerDisplayId;

    @com.android.internal.annotations.GuardedBy({"mAssociationsLock"})
    private final java.util.Map<java.lang.String, java.lang.Integer> mRuntimeAssociations;
    private final java.util.List<com.android.server.input.InputManagerService.SensorEventListenerRecord> mSensorAccuracyListenersToNotify;

    @com.android.internal.annotations.GuardedBy({"mSensorEventLock"})
    private final android.util.SparseArray<com.android.server.input.InputManagerService.SensorEventListenerRecord> mSensorEventListeners;
    private final java.util.List<com.android.server.input.InputManagerService.SensorEventListenerRecord> mSensorEventListenersToNotify;
    private final java.lang.Object mSensorEventLock;
    private final com.android.server.input.InputSettingsObserver mSettingsObserver;
    private boolean mShowKeyPresses;
    private boolean mShowRotaryInput;
    private final java.util.Map<java.lang.String, java.lang.Integer> mStaticAssociations;
    private final com.android.server.input.StickyModifierStateController mStickyModifierStateController;
    private boolean mSystemReady;

    @com.android.internal.annotations.GuardedBy({"mTabletModeLock"})
    private final android.util.SparseArray<com.android.server.input.InputManagerService.TabletModeChangedListenerRecord> mTabletModeChangedListeners;
    private final java.lang.Object mTabletModeLock;
    private final java.util.ArrayList<com.android.server.input.InputManagerService.InputDevicesChangedListenerRecord> mTempInputDevicesChangedListenersToNotify;
    private final java.util.List<com.android.server.input.InputManagerService.TabletModeChangedListenerRecord> mTempTabletModeChangedListenersToNotify;

    @com.android.internal.annotations.GuardedBy({"mAssociationsLock"})
    private final java.util.Map<java.lang.String, java.lang.String> mUniqueIdAssociations;
    final boolean mUseDevInputEventForAudioJack;
    private final java.lang.String mVelocityTrackerStrategy;
    private final java.lang.Object mVibratorLock;

    @com.android.internal.annotations.GuardedBy({"mVibratorLock"})
    private final android.util.SparseArray<android.os.RemoteCallbackList<android.os.IVibratorStateListener>> mVibratorStateListeners;
    private final java.util.Map<android.os.IBinder, com.android.server.input.InputManagerService.VibratorToken> mVibratorTokens;
    private com.android.server.input.InputManagerService.WindowManagerCallbacks mWindowManagerCallbacks;
    private com.android.server.input.InputManagerService.WiredAccessoryCallbacks mWiredAccessoryCallbacks;
    static final java.lang.String TAG = "InputManager";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final com.android.server.input.InputManagerService.AdditionalDisplayInputProperties DEFAULT_ADDITIONAL_DISPLAY_INPUT_PROPERTIES = new com.android.server.input.InputManagerService.AdditionalDisplayInputProperties();

    public interface WindowManagerCallbacks extends com.android.server.input.InputManagerInternal.LidSwitchCallback {
        @android.annotation.Nullable
        android.view.SurfaceControl createSurfaceForGestureMonitor(java.lang.String str, int i);

        android.view.KeyEvent dispatchUnhandledKey(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i);

        android.view.SurfaceControl getParentSurfaceForPointers(int i);

        int getPointerDisplayId();

        int getPointerLayer();

        long interceptKeyBeforeDispatching(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i);

        int interceptKeyBeforeQueueing(android.view.KeyEvent keyEvent, int i);

        int interceptMotionBeforeQueueingNonInteractive(int i, int i2, int i3, long j, int i4);

        void notifyCameraLensCoverSwitchChanged(long j, boolean z);

        void notifyConfigurationChanged();

        void notifyDropWindow(android.os.IBinder iBinder, float f, float f2);

        void notifyFocusChanged(android.os.IBinder iBinder, android.os.IBinder iBinder2);

        void notifyInputChannelBroken(android.os.IBinder iBinder);

        void notifyNoFocusedWindowAnr(android.view.InputApplicationHandle inputApplicationHandle);

        void notifyPointerDisplayIdChanged(int i, float f, float f2);

        void notifyPointerLocationChanged(boolean z);

        void notifyWindowResponsive(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.util.OptionalInt optionalInt);

        void notifyWindowUnresponsive(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.util.OptionalInt optionalInt, @android.annotation.NonNull java.lang.String str);

        void onPointerDownOutsideFocus(android.os.IBinder iBinder);
    }

    public interface WiredAccessoryCallbacks {
        void notifyWiredAccessoryChanged(long j, int i, int i2);

        void systemReady();
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private final android.content.Context mContext;
        private final android.os.Looper mLooper;
        private final com.android.server.input.UEventManager mUEventManager;

        Injector(android.content.Context context, android.os.Looper looper, com.android.server.input.UEventManager uEventManager) {
            this.mContext = context;
            this.mLooper = looper;
            this.mUEventManager = uEventManager;
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        android.os.Looper getLooper() {
            return this.mLooper;
        }

        com.android.server.input.UEventManager getUEventManager() {
            return this.mUEventManager;
        }

        com.android.server.input.NativeInputManagerService getNativeService(com.android.server.input.InputManagerService inputManagerService) {
            return new com.android.server.input.NativeInputManagerService.NativeImpl(inputManagerService, this.mLooper.getQueue());
        }

        void registerLocalService(com.android.server.input.InputManagerInternal inputManagerInternal) {
            com.android.server.LocalServices.addService(com.android.server.input.InputManagerInternal.class, inputManagerInternal);
        }
    }

    public InputManagerService(android.content.Context context) {
        this(new com.android.server.input.InputManagerService.Injector(context, com.android.server.DisplayThread.get().getLooper(), new com.android.server.input.UEventManager() { // from class: com.android.server.input.InputManagerService.1
        }));
    }

    @com.android.internal.annotations.VisibleForTesting
    InputManagerService(com.android.server.input.InputManagerService.Injector injector) {
        com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface keyboardBacklightControllerInterface;
        this.mTabletModeLock = new java.lang.Object();
        this.mTabletModeChangedListeners = new android.util.SparseArray<>();
        this.mTempTabletModeChangedListenersToNotify = new java.util.ArrayList();
        this.mSensorEventLock = new java.lang.Object();
        this.mSensorEventListeners = new android.util.SparseArray<>();
        this.mSensorEventListenersToNotify = new java.util.ArrayList();
        this.mSensorAccuracyListenersToNotify = new java.util.ArrayList();
        this.mDataStore = new com.android.server.input.PersistentDataStore();
        this.mInputDevicesLock = new java.lang.Object();
        this.mInputDevices = new android.view.InputDevice[0];
        this.mInputDevicesChangedListeners = new android.util.SparseArray<>();
        this.mTempInputDevicesChangedListenersToNotify = new java.util.ArrayList<>();
        this.mVibratorLock = new java.lang.Object();
        this.mVibratorTokens = new android.util.ArrayMap();
        this.mVibratorStateListeners = new android.util.SparseArray<>();
        this.mIsVibrating = new android.util.SparseBooleanArray();
        this.mLightLock = new java.lang.Object();
        this.mLightSessions = new android.util.ArrayMap<>();
        this.mLidSwitchLock = new java.lang.Object();
        this.mLidSwitchCallbacks = new java.util.ArrayList();
        this.mInputFilterLock = new java.lang.Object();
        this.mAssociationsLock = new java.lang.Object();
        this.mRuntimeAssociations = new android.util.ArrayMap();
        this.mUniqueIdAssociations = new android.util.ArrayMap();
        this.mKeyboardLayoutAssociations = new android.util.ArrayMap();
        this.mDeviceTypeAssociations = new android.util.ArrayMap();
        this.mAdditionalDisplayInputPropertiesLock = new java.lang.Object();
        this.mOverriddenPointerDisplayId = -1;
        this.mAcknowledgedPointerDisplayId = -1;
        this.mRequestedPointerDisplayId = -1;
        this.mAdditionalDisplayInputProperties = new android.util.SparseArray<>();
        this.mCurrentDisplayProperties = new com.android.server.input.InputManagerService.AdditionalDisplayInputProperties();
        this.mPointerIconType = 1;
        this.mInputMonitors = new java.util.HashMap();
        this.mFocusEventDebugViewLock = new java.lang.Object();
        this.mShowKeyPresses = false;
        this.mShowRotaryInput = false;
        this.mStaticAssociations = loadStaticInputPortAssociations();
        this.mContext = injector.getContext();
        this.mHandler = new com.android.server.input.InputManagerService.InputManagerHandler(injector.getLooper());
        this.mNative = injector.getNativeService(this);
        this.mSettingsObserver = new com.android.server.input.InputSettingsObserver(this.mContext, this.mHandler, this, this.mNative);
        this.mKeyboardLayoutManager = new com.android.server.input.KeyboardLayoutManager(this.mContext, this.mNative, this.mDataStore, injector.getLooper());
        this.mBatteryController = new com.android.server.input.BatteryController(this.mContext, this.mNative, injector.getLooper(), injector.getUEventManager());
        if (com.android.server.input.InputFeatureFlagProvider.isKeyboardBacklightControlEnabled()) {
            keyboardBacklightControllerInterface = new com.android.server.input.KeyboardBacklightController(this.mContext, this.mNative, this.mDataStore, injector.getLooper(), injector.getUEventManager());
        } else {
            keyboardBacklightControllerInterface = new com.android.server.input.InputManagerService.KeyboardBacklightControllerInterface() { // from class: com.android.server.input.InputManagerService.2
            };
        }
        this.mKeyboardBacklightController = keyboardBacklightControllerInterface;
        this.mStickyModifierStateController = new com.android.server.input.StickyModifierStateController();
        this.mKeyRemapper = new com.android.server.input.KeyRemapper(this.mContext, this.mNative, this.mDataStore, injector.getLooper());
        this.mPointerIconCache = new com.android.server.input.PointerIconCache(this.mContext, this.mNative);
        this.mUseDevInputEventForAudioJack = this.mContext.getResources().getBoolean(android.R.bool.config_useAttentionLight);
        android.util.Slog.i(TAG, "Initializing input manager, mUseDevInputEventForAudioJack=" + this.mUseDevInputEventForAudioJack);
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_deviceSpecificAudioService);
        this.mDoubleTouchGestureEnableFile = android.text.TextUtils.isEmpty(string) ? null : new java.io.File(string);
        this.mVelocityTrackerStrategy = android.provider.DeviceConfig.getProperty("input_native_boot", VELOCITYTRACKER_STRATEGY_PROPERTY);
        injector.registerLocalService(new com.android.server.input.InputManagerService.LocalService());
    }

    public void setWindowManagerCallbacks(com.android.server.input.InputManagerService.WindowManagerCallbacks windowManagerCallbacks) {
        if (this.mWindowManagerCallbacks != null) {
            unregisterLidSwitchCallbackInternal(this.mWindowManagerCallbacks);
        }
        this.mWindowManagerCallbacks = windowManagerCallbacks;
        registerLidSwitchCallbackInternal(this.mWindowManagerCallbacks);
    }

    public void setWiredAccessoryCallbacks(com.android.server.input.InputManagerService.WiredAccessoryCallbacks wiredAccessoryCallbacks) {
        this.mWiredAccessoryCallbacks = wiredAccessoryCallbacks;
    }

    void registerLidSwitchCallbackInternal(@android.annotation.NonNull com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
        synchronized (this.mLidSwitchLock) {
            try {
                this.mLidSwitchCallbacks.add(lidSwitchCallback);
                if (this.mSystemReady) {
                    lidSwitchCallback.notifyLidSwitchChanged(0L, getSwitchState(-1, -256, 0) == 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void unregisterLidSwitchCallbackInternal(@android.annotation.NonNull com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
        synchronized (this.mLidSwitchLock) {
            this.mLidSwitchCallbacks.remove(lidSwitchCallback);
        }
    }

    public void start() {
        android.util.Slog.i(TAG, "Starting input manager");
        this.mNative.start();
        com.android.server.Watchdog.getInstance().addMonitor(this);
    }

    public void systemRunning() {
        boolean z;
        if (DEBUG) {
            android.util.Slog.d(TAG, "System ready.");
        }
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        this.mInputMethodManagerInternal = (com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class);
        this.mSettingsObserver.registerAndUpdate();
        synchronized (this.mLidSwitchLock) {
            try {
                this.mSystemReady = true;
                int switchState = getSwitchState(-1, -256, 0);
                for (int i = 0; i < this.mLidSwitchCallbacks.size(); i++) {
                    com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback = this.mLidSwitchCallbacks.get(i);
                    if (switchState == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    lidSwitchCallback.notifyLidSwitchChanged(0L, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (getSwitchState(-1, -256, 14) == 1) {
            setSensorPrivacy(1, true);
        }
        if (getSwitchState(-1, -256, 9) == 1) {
            setSensorPrivacy(2, true);
        }
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.input.InputManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.input.InputManagerService.this.reloadDeviceAliases();
            }
        }, new android.content.IntentFilter("android.bluetooth.device.action.ALIAS_CHANGED"), null, this.mHandler);
        this.mHandler.sendEmptyMessage(2);
        if (this.mWiredAccessoryCallbacks != null) {
            this.mWiredAccessoryCallbacks.systemReady();
        }
        this.mKeyboardLayoutManager.systemRunning();
        this.mBatteryController.systemRunning();
        this.mKeyboardBacklightController.systemRunning();
        this.mKeyRemapper.systemRunning();
        this.mPointerIconCache.systemRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadDeviceAliases() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Reloading device names.");
        }
        this.mNative.reloadDeviceAliases();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisplayViewportsInternal(java.util.List<android.hardware.display.DisplayViewport> list) {
        android.hardware.display.DisplayViewport[] displayViewportArr = new android.hardware.display.DisplayViewport[list.size()];
        for (int size = list.size() - 1; size >= 0; size--) {
            displayViewportArr[size] = list.get(size);
        }
        this.mNative.setDisplayViewports(displayViewportArr);
        int pointerDisplayId = this.mWindowManagerCallbacks.getPointerDisplayId();
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                if (this.mOverriddenPointerDisplayId == -1) {
                    updatePointerDisplayIdLocked(pointerDisplayId);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getKeyCodeState(int i, int i2, int i3) {
        return this.mNative.getKeyCodeState(i, i2, i3);
    }

    public int getScanCodeState(int i, int i2, int i3) {
        return this.mNative.getScanCodeState(i, i2, i3);
    }

    public int getSwitchState(int i, int i2, int i3) {
        return this.mNative.getSwitchState(i, i2, i3);
    }

    public boolean hasKeys(int i, int i2, int[] iArr, boolean[] zArr) {
        java.util.Objects.requireNonNull(iArr, "keyCodes must not be null");
        java.util.Objects.requireNonNull(zArr, "keyExists must not be null");
        if (zArr.length < iArr.length) {
            throw new java.lang.IllegalArgumentException("keyExists must be at least as large as keyCodes");
        }
        return this.mNative.hasKeys(i, i2, iArr, zArr);
    }

    public int getKeyCodeForKeyLocation(int i, int i2) {
        if (i2 <= 0 || i2 > android.view.KeyEvent.getMaxKeyCode()) {
            return 0;
        }
        return this.mNative.getKeyCodeForKeyLocation(i, i2);
    }

    public android.view.KeyCharacterMap getKeyCharacterMap(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "layoutDescriptor must not be null");
        return this.mKeyboardLayoutManager.getKeyCharacterMap(str);
    }

    @java.lang.Deprecated
    public boolean transferTouch(android.os.IBinder iBinder, int i) {
        java.util.Objects.requireNonNull(iBinder, "destChannelToken must not be null");
        return this.mNative.transferTouch(iBinder, i);
    }

    public android.view.InputChannel monitorInput(java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, "inputChannelName not be null");
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("displayId must >= 0.");
        }
        return this.mNative.createInputMonitor(i, str, android.os.Binder.getCallingPid());
    }

    @android.annotation.NonNull
    private android.view.InputChannel createSpyWindowGestureMonitor(android.os.IBinder iBinder, java.lang.String str, android.view.SurfaceControl surfaceControl, int i, int i2, int i3) {
        final android.view.InputChannel createInputChannel = createInputChannel(str);
        try {
            iBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda7
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.input.InputManagerService.this.lambda$createSpyWindowGestureMonitor$0(createInputChannel);
                }
            }, 0);
            synchronized (this.mInputMonitors) {
                this.mInputMonitors.put(createInputChannel.getToken(), new com.android.server.input.GestureMonitorSpyWindow(iBinder, str, i, i2, i3, surfaceControl, createInputChannel));
            }
            android.view.InputChannel inputChannel = new android.view.InputChannel();
            createInputChannel.copyTo(inputChannel);
            return inputChannel;
        } catch (android.os.RemoteException e) {
            android.util.Slog.i(TAG, "Client died before '" + str + "' could be created.");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSpyWindowGestureMonitor$0(android.view.InputChannel inputChannel) {
        removeSpyWindowGestureMonitor(inputChannel.getToken());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeSpyWindowGestureMonitor(android.os.IBinder iBinder) {
        com.android.server.input.GestureMonitorSpyWindow remove;
        synchronized (this.mInputMonitors) {
            remove = this.mInputMonitors.remove(iBinder);
        }
        removeInputChannel(iBinder);
        if (remove == null) {
            return;
        }
        remove.remove();
    }

    public android.view.InputMonitor monitorGestureInput(android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, int i) {
        if (!checkCallingPermission("android.permission.MONITOR_INPUT", "monitorGestureInput()")) {
            throw new java.lang.SecurityException("Requires MONITOR_INPUT permission");
        }
        java.util.Objects.requireNonNull(str, "name must not be null.");
        java.util.Objects.requireNonNull(iBinder, "token must not be null.");
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("displayId must >= 0.");
        }
        java.lang.String str2 = "[Gesture Monitor] " + str;
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.view.SurfaceControl createSurfaceForGestureMonitor = this.mWindowManagerCallbacks.createSurfaceForGestureMonitor(str2, i);
            if (createSurfaceForGestureMonitor == null) {
                throw new java.lang.IllegalArgumentException("Could not create gesture monitor surface on display: " + i);
            }
            android.view.InputChannel createSpyWindowGestureMonitor = createSpyWindowGestureMonitor(iBinder, str2, createSurfaceForGestureMonitor, i, callingPid, callingUid);
            return new android.view.InputMonitor(createSpyWindowGestureMonitor, new com.android.server.input.InputManagerService.InputMonitorHost(createSpyWindowGestureMonitor.getToken()), new android.view.SurfaceControl(createSurfaceForGestureMonitor, "IMS.monitorGestureInput"));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.view.InputChannel createInputChannel(java.lang.String str) {
        return this.mNative.createInputChannel(str);
    }

    public void removeInputChannel(android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(iBinder, "connectionToken must not be null");
        this.mNative.removeInputChannel(iBinder);
    }

    public void setInputFilter(android.view.IInputFilter iInputFilter) {
        synchronized (this.mInputFilterLock) {
            try {
                android.view.IInputFilter iInputFilter2 = this.mInputFilter;
                if (iInputFilter2 == iInputFilter) {
                    return;
                }
                if (iInputFilter2 != null) {
                    this.mInputFilter = null;
                    this.mInputFilterHost.disconnectLocked();
                    this.mInputFilterHost = null;
                    try {
                        iInputFilter2.uninstall();
                    } catch (android.os.RemoteException e) {
                    }
                }
                if (iInputFilter != null) {
                    this.mInputFilter = iInputFilter;
                    this.mInputFilterHost = new com.android.server.input.InputManagerService.InputFilterHost();
                    try {
                        iInputFilter.install(this.mInputFilterHost);
                    } catch (android.os.RemoteException e2) {
                    }
                }
                this.mNative.setInputFilterEnabled(iInputFilter != null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setInTouchMode(boolean z, int i, int i2, boolean z2, int i3) {
        return this.mNative.setInTouchMode(z, i, i2, z2, i3);
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, int i) {
        return injectInputEventToTarget(inputEvent, i, -1);
    }

    public boolean injectInputEventToTarget(android.view.InputEvent inputEvent, int i, int i2) {
        if (!checkCallingPermission("android.permission.INJECT_EVENTS", "injectInputEvent()", true)) {
            throw new java.lang.SecurityException("Injecting input events requires the caller (or the source of the instrumentation, if any) to have the INJECT_EVENTS permission.");
        }
        java.util.Objects.requireNonNull(inputEvent, "event must not be null");
        if (i != 0 && i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("mode is invalid");
        }
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        boolean z = i2 != -1;
        try {
            int injectInputEvent = this.mNative.injectInputEvent(inputEvent, z, i2, i, 30000, 134217728);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            switch (injectInputEvent) {
                case 0:
                    return true;
                case 1:
                    if (!z) {
                        throw new java.lang.IllegalStateException("Injection should not result in TARGET_MISMATCH when it is not targeted into to a specific uid.");
                    }
                    throw new java.lang.IllegalArgumentException("Targeted input event injection from pid " + callingPid + " was not directed at a window owned by uid " + i2 + ".");
                case 2:
                default:
                    android.util.Slog.w(TAG, "Input event injection from pid " + callingPid + " failed.");
                    return false;
                case 3:
                    android.util.Slog.w(TAG, "Input event injection from pid " + callingPid + " timed out.");
                    return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public android.view.VerifiedInputEvent verifyInputEvent(android.view.InputEvent inputEvent) {
        java.util.Objects.requireNonNull(inputEvent, "event must not be null");
        return this.mNative.verifyInputEvent(inputEvent);
    }

    public java.lang.String getVelocityTrackerStrategy() {
        return this.mVelocityTrackerStrategy;
    }

    public android.view.InputDevice getInputDevice(int i) {
        synchronized (this.mInputDevicesLock) {
            try {
                for (android.view.InputDevice inputDevice : this.mInputDevices) {
                    if (inputDevice.getId() == i) {
                        return inputDevice;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isInputDeviceEnabled(int i) {
        return this.mNative.isInputDeviceEnabled(i);
    }

    public void enableInputDevice(int i) {
        if (!checkCallingPermission("android.permission.DISABLE_INPUT_DEVICE", "enableInputDevice()")) {
            throw new java.lang.SecurityException("Requires DISABLE_INPUT_DEVICE permission");
        }
        this.mNative.enableInputDevice(i);
    }

    public void disableInputDevice(int i) {
        if (!checkCallingPermission("android.permission.DISABLE_INPUT_DEVICE", "disableInputDevice()")) {
            throw new java.lang.SecurityException("Requires DISABLE_INPUT_DEVICE permission");
        }
        this.mNative.disableInputDevice(i);
    }

    public int[] getInputDeviceIds() {
        int[] iArr;
        synchronized (this.mInputDevicesLock) {
            try {
                int length = this.mInputDevices.length;
                iArr = new int[length];
                for (int i = 0; i < length; i++) {
                    iArr[i] = this.mInputDevices[i].getId();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return iArr;
    }

    public android.view.InputDevice[] getInputDevices() {
        android.view.InputDevice[] inputDeviceArr;
        synchronized (this.mInputDevicesLock) {
            inputDeviceArr = this.mInputDevices;
        }
        return inputDeviceArr;
    }

    public void registerInputDevicesChangedListener(android.hardware.input.IInputDevicesChangedListener iInputDevicesChangedListener) {
        java.util.Objects.requireNonNull(iInputDevicesChangedListener, "listener must not be null");
        synchronized (this.mInputDevicesLock) {
            try {
                int callingPid = android.os.Binder.getCallingPid();
                if (this.mInputDevicesChangedListeners.get(callingPid) != null) {
                    throw new java.lang.SecurityException("The calling process has already registered an InputDevicesChangedListener.");
                }
                com.android.server.input.InputManagerService.InputDevicesChangedListenerRecord inputDevicesChangedListenerRecord = new com.android.server.input.InputManagerService.InputDevicesChangedListenerRecord(callingPid, iInputDevicesChangedListener);
                try {
                    iInputDevicesChangedListener.asBinder().linkToDeath(inputDevicesChangedListenerRecord, 0);
                    this.mInputDevicesChangedListeners.put(callingPid, inputDevicesChangedListenerRecord);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputDevicesChangedListenerDied(int i) {
        synchronized (this.mInputDevicesLock) {
            this.mInputDevicesChangedListeners.remove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliverInputDevicesChanged(android.view.InputDevice[] inputDeviceArr) {
        this.mTempInputDevicesChangedListenersToNotify.clear();
        synchronized (this.mInputDevicesLock) {
            try {
                if (this.mInputDevicesChangedPending) {
                    this.mInputDevicesChangedPending = false;
                    int size = this.mInputDevicesChangedListeners.size();
                    for (int i = 0; i < size; i++) {
                        this.mTempInputDevicesChangedListenersToNotify.add(this.mInputDevicesChangedListeners.valueAt(i));
                    }
                    int length = this.mInputDevices.length;
                    int[] iArr = new int[length * 2];
                    for (int i2 = 0; i2 < length; i2++) {
                        android.view.InputDevice inputDevice = this.mInputDevices[i2];
                        int i3 = i2 * 2;
                        iArr[i3] = inputDevice.getId();
                        iArr[i3 + 1] = inputDevice.getGeneration();
                        if (DEBUG) {
                            android.util.Log.d(TAG, "device " + inputDevice.getId() + " generation " + inputDevice.getGeneration());
                        }
                    }
                    for (int i4 = 0; i4 < size; i4++) {
                        this.mTempInputDevicesChangedListenersToNotify.get(i4).notifyInputDevicesChanged(iArr);
                    }
                    this.mTempInputDevicesChangedListenersToNotify.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.hardware.input.TouchCalibration getTouchCalibrationForInputDevice(java.lang.String str, int i) {
        android.hardware.input.TouchCalibration touchCalibration;
        java.util.Objects.requireNonNull(str, "inputDeviceDescriptor must not be null");
        synchronized (this.mDataStore) {
            touchCalibration = this.mDataStore.getTouchCalibration(str, i);
        }
        return touchCalibration;
    }

    public void setTouchCalibrationForInputDevice(java.lang.String str, int i, android.hardware.input.TouchCalibration touchCalibration) {
        if (!checkCallingPermission("android.permission.SET_INPUT_CALIBRATION", "setTouchCalibrationForInputDevice()")) {
            throw new java.lang.SecurityException("Requires SET_INPUT_CALIBRATION permission");
        }
        java.util.Objects.requireNonNull(str, "inputDeviceDescriptor must not be null");
        java.util.Objects.requireNonNull(touchCalibration, "calibration must not be null");
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("surfaceRotation value out of bounds");
        }
        synchronized (this.mDataStore) {
            try {
                try {
                    if (this.mDataStore.setTouchCalibration(str, i, touchCalibration)) {
                        this.mNative.reloadCalibration();
                    }
                } finally {
                    this.mDataStore.saveIfNeeded();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int isInTabletMode() {
        if (!checkCallingPermission("android.permission.TABLET_MODE", "isInTabletMode()")) {
            throw new java.lang.SecurityException("Requires TABLET_MODE permission");
        }
        return getSwitchState(-1, -256, 1);
    }

    public int isMicMuted() {
        return getSwitchState(-1, -256, 14);
    }

    public void registerTabletModeChangedListener(android.hardware.input.ITabletModeChangedListener iTabletModeChangedListener) {
        if (!checkCallingPermission("android.permission.TABLET_MODE", "registerTabletModeChangedListener()")) {
            throw new java.lang.SecurityException("Requires TABLET_MODE_LISTENER permission");
        }
        java.util.Objects.requireNonNull(iTabletModeChangedListener, "event must not be null");
        synchronized (this.mTabletModeLock) {
            try {
                int callingPid = android.os.Binder.getCallingPid();
                if (this.mTabletModeChangedListeners.get(callingPid) != null) {
                    throw new java.lang.IllegalStateException("The calling process has already registered a TabletModeChangedListener.");
                }
                com.android.server.input.InputManagerService.TabletModeChangedListenerRecord tabletModeChangedListenerRecord = new com.android.server.input.InputManagerService.TabletModeChangedListenerRecord(callingPid, iTabletModeChangedListener);
                try {
                    iTabletModeChangedListener.asBinder().linkToDeath(tabletModeChangedListenerRecord, 0);
                    this.mTabletModeChangedListeners.put(callingPid, tabletModeChangedListenerRecord);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTabletModeChangedListenerDied(int i) {
        synchronized (this.mTabletModeLock) {
            this.mTabletModeChangedListeners.remove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliverTabletModeChanged(long j, boolean z) {
        int size;
        int i;
        this.mTempTabletModeChangedListenersToNotify.clear();
        synchronized (this.mTabletModeLock) {
            try {
                size = this.mTabletModeChangedListeners.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mTempTabletModeChangedListenersToNotify.add(this.mTabletModeChangedListeners.valueAt(i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (i = 0; i < size; i++) {
            this.mTempTabletModeChangedListenersToNotify.get(i).notifyTabletModeChanged(j, z);
        }
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayouts() {
        return this.mKeyboardLayoutManager.getKeyboardLayouts();
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        return this.mKeyboardLayoutManager.getKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
    }

    public android.hardware.input.KeyboardLayout getKeyboardLayout(java.lang.String str) {
        return this.mKeyboardLayoutManager.getKeyboardLayout(str);
    }

    public java.lang.String getCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        return this.mKeyboardLayoutManager.getCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier);
    }

    @android.annotation.EnforcePermission("android.permission.SET_KEYBOARD_LAYOUT")
    public void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        super.setCurrentKeyboardLayoutForInputDevice_enforcePermission();
        this.mKeyboardLayoutManager.setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
    }

    public java.lang.String[] getEnabledKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        return this.mKeyboardLayoutManager.getEnabledKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
    }

    @android.annotation.EnforcePermission("android.permission.SET_KEYBOARD_LAYOUT")
    public void addKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        super.addKeyboardLayoutForInputDevice_enforcePermission();
        this.mKeyboardLayoutManager.addKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
    }

    @android.annotation.EnforcePermission("android.permission.SET_KEYBOARD_LAYOUT")
    public void removeKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        super.removeKeyboardLayoutForInputDevice_enforcePermission();
        this.mKeyboardLayoutManager.removeKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
    }

    public android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        return this.mKeyboardLayoutManager.getKeyboardLayoutForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
    }

    @android.annotation.EnforcePermission("android.permission.SET_KEYBOARD_LAYOUT")
    public void setKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype, java.lang.String str) {
        super.setKeyboardLayoutForInputDevice_enforcePermission();
        this.mKeyboardLayoutManager.setKeyboardLayoutForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype, str);
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        return this.mKeyboardLayoutManager.getKeyboardLayoutListForInputDevice(inputDeviceIdentifier, i, inputMethodInfo, inputMethodSubtype);
    }

    public void switchKeyboardLayout(int i, int i2) {
        this.mKeyboardLayoutManager.switchKeyboardLayout(i, i2);
    }

    public void setFocusedApplication(int i, android.view.InputApplicationHandle inputApplicationHandle) {
        this.mNative.setFocusedApplication(i, inputApplicationHandle);
    }

    public void setFocusedDisplay(int i) {
        this.mNative.setFocusedDisplay(i);
    }

    public void onDisplayRemoved(int i) {
        updateAdditionalDisplayInputProperties(i, new java.util.function.Consumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.input.InputManagerService.AdditionalDisplayInputProperties) obj).reset();
            }
        });
        this.mNative.displayRemoved(i);
    }

    public void requestPointerCapture(android.os.IBinder iBinder, boolean z) {
        java.util.Objects.requireNonNull(iBinder, "event must not be null");
        this.mNative.requestPointerCapture(iBinder, z);
    }

    public void setInputDispatchMode(boolean z, boolean z2) {
        this.mNative.setInputDispatchMode(z, z2);
    }

    public void setSystemUiLightsOut(boolean z) {
        this.mNative.setSystemUiLightsOut(z);
    }

    public boolean startDragAndDrop(@android.annotation.NonNull android.view.InputChannel inputChannel, @android.annotation.NonNull android.view.InputChannel inputChannel2) {
        return this.mNative.transferTouchGesture(inputChannel.getToken(), inputChannel2.getToken(), true);
    }

    public boolean transferTouchGesture(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(iBinder2);
        return this.mNative.transferTouchGesture(iBinder, iBinder2, false);
    }

    public int getMousePointerSpeed() {
        return this.mNative.getMousePointerSpeed();
    }

    public void tryPointerSpeed(int i) {
        if (!checkCallingPermission("android.permission.SET_POINTER_SPEED", "tryPointerSpeed()")) {
            throw new java.lang.SecurityException("Requires SET_POINTER_SPEED permission");
        }
        if (i < -7 || i > 7) {
            throw new java.lang.IllegalArgumentException("speed out of range");
        }
        setPointerSpeedUnchecked(i);
    }

    private void setPointerSpeedUnchecked(int i) {
        this.mNative.setPointerSpeed(java.lang.Math.min(java.lang.Math.max(i, -7), 7));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMousePointerAccelerationEnabled(final boolean z, int i) {
        updateAdditionalDisplayInputProperties(i, new java.util.function.Consumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.input.InputManagerService.AdditionalDisplayInputProperties) obj).mousePointerAccelerationEnabled = z;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPointerIconVisible(final boolean z, int i) {
        updateAdditionalDisplayInputProperties(i, new java.util.function.Consumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.input.InputManagerService.AdditionalDisplayInputProperties) obj).pointerIconVisible = z;
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private boolean updatePointerDisplayIdLocked(int i) {
        if (this.mRequestedPointerDisplayId == i) {
            return false;
        }
        this.mRequestedPointerDisplayId = i;
        this.mNative.setPointerDisplayId(i);
        applyAdditionalDisplayInputProperties();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePointerDisplayIdChanged(com.android.server.input.InputManagerService.PointerDisplayIdChangedArgs pointerDisplayIdChangedArgs) {
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            this.mAcknowledgedPointerDisplayId = pointerDisplayIdChangedArgs.mPointerDisplayId;
            this.mAdditionalDisplayInputPropertiesLock.notifyAll();
        }
        this.mWindowManagerCallbacks.notifyPointerDisplayIdChanged(pointerDisplayIdChangedArgs.mPointerDisplayId, pointerDisplayIdChangedArgs.mXPosition, pointerDisplayIdChangedArgs.mYPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setVirtualMousePointerDisplayIdBlocking(int i) {
        int i2;
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            throw new java.lang.IllegalStateException("This must not be used when PointerChoreographer is enabled");
        }
        boolean z = i == -1;
        if (z) {
            i2 = this.mWindowManagerCallbacks.getPointerDisplayId();
        } else {
            i2 = i;
        }
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                this.mOverriddenPointerDisplayId = i;
                if (!updatePointerDisplayIdLocked(i2) && this.mAcknowledgedPointerDisplayId == i2) {
                    return true;
                }
                if (z && this.mAcknowledgedPointerDisplayId == -1) {
                    return true;
                }
                try {
                    this.mAdditionalDisplayInputPropertiesLock.wait(5000L);
                } catch (java.lang.InterruptedException e) {
                }
                return z || this.mAcknowledgedPointerDisplayId == i;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVirtualMousePointerDisplayId() {
        int i;
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            throw new java.lang.IllegalStateException("This must not be used when PointerChoreographer is enabled");
        }
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            i = this.mOverriddenPointerDisplayId;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisplayEligibilityForPointerCapture(int i, boolean z) {
        this.mNative.setDisplayEligibilityForPointerCapture(i, z);
    }

    private static class VibrationInfo {
        private final int[] mAmplitudes;
        private final long[] mPattern;
        private final int mRepeat;

        public long[] getPattern() {
            return this.mPattern;
        }

        public int[] getAmplitudes() {
            return this.mAmplitudes;
        }

        public int getRepeatIndex() {
            return this.mRepeat;
        }

        VibrationInfo(android.os.VibrationEffect vibrationEffect) {
            long[] jArr;
            int i;
            int i2;
            int[] iArr;
            if (!(vibrationEffect instanceof android.os.VibrationEffect.Composed)) {
                jArr = null;
                i = -1;
                i2 = -1;
                iArr = null;
            } else {
                android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
                int size = composed.getSegments().size();
                jArr = new long[size];
                iArr = new int[size];
                i = composed.getRepeatIndex();
                int i3 = 0;
                i2 = 0;
                while (true) {
                    if (i3 >= size) {
                        break;
                    }
                    android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.VibrationEffectSegment) composed.getSegments().get(i3);
                    i = composed.getRepeatIndex() == i3 ? i2 : i;
                    if (!(stepSegment instanceof android.os.vibrator.StepSegment)) {
                        android.util.Slog.w(com.android.server.input.InputManagerService.TAG, "Input devices don't support segment " + stepSegment);
                        i2 = -1;
                        break;
                    }
                    float amplitude = stepSegment.getAmplitude();
                    if (java.lang.Float.compare(amplitude, -1.0f) == 0) {
                        iArr[i2] = 192;
                    } else {
                        iArr[i2] = (int) (amplitude * 255.0f);
                    }
                    jArr[i2] = stepSegment.getDuration();
                    i3++;
                    i2++;
                }
            }
            if (i2 < 0) {
                android.util.Slog.w(com.android.server.input.InputManagerService.TAG, "Only oneshot and step waveforms are supported on input devices");
                this.mPattern = new long[0];
                this.mAmplitudes = new int[0];
                this.mRepeat = -1;
                return;
            }
            this.mRepeat = i;
            this.mPattern = new long[i2];
            this.mAmplitudes = new int[i2];
            java.lang.System.arraycopy(jArr, 0, this.mPattern, 0, i2);
            java.lang.System.arraycopy(iArr, 0, this.mAmplitudes, 0, i2);
            if (this.mRepeat >= this.mPattern.length) {
                throw new java.lang.ArrayIndexOutOfBoundsException("Repeat index " + this.mRepeat + " must be within the bounds of the pattern.length " + this.mPattern.length);
            }
        }
    }

    private com.android.server.input.InputManagerService.VibratorToken getVibratorToken(int i, android.os.IBinder iBinder) {
        com.android.server.input.InputManagerService.VibratorToken vibratorToken;
        synchronized (this.mVibratorLock) {
            try {
                vibratorToken = this.mVibratorTokens.get(iBinder);
                if (vibratorToken == null) {
                    int i2 = this.mNextVibratorTokenValue;
                    this.mNextVibratorTokenValue = i2 + 1;
                    vibratorToken = new com.android.server.input.InputManagerService.VibratorToken(i, iBinder, i2);
                    try {
                        iBinder.linkToDeath(vibratorToken, 0);
                        this.mVibratorTokens.put(iBinder, vibratorToken);
                    } catch (android.os.RemoteException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return vibratorToken;
    }

    public void vibrate(int i, android.os.VibrationEffect vibrationEffect, android.os.IBinder iBinder) {
        com.android.server.input.InputManagerService.VibrationInfo vibrationInfo = new com.android.server.input.InputManagerService.VibrationInfo(vibrationEffect);
        com.android.server.input.InputManagerService.VibratorToken vibratorToken = getVibratorToken(i, iBinder);
        synchronized (vibratorToken) {
            vibratorToken.mVibrating = true;
            this.mNative.vibrate(i, vibrationInfo.getPattern(), vibrationInfo.getAmplitudes(), vibrationInfo.getRepeatIndex(), vibratorToken.mTokenValue);
        }
    }

    public int[] getVibratorIds(int i) {
        return this.mNative.getVibratorIds(i);
    }

    public boolean isVibrating(int i) {
        return this.mNative.isVibrating(i);
    }

    public void vibrateCombined(int i, android.os.CombinedVibration combinedVibration, android.os.IBinder iBinder) {
        com.android.server.input.InputManagerService.VibratorToken vibratorToken = getVibratorToken(i, iBinder);
        synchronized (vibratorToken) {
            try {
                if (!(combinedVibration instanceof android.os.CombinedVibration.Mono) && !(combinedVibration instanceof android.os.CombinedVibration.Stereo)) {
                    android.util.Slog.e(TAG, "Only Mono and Stereo effects are supported");
                    return;
                }
                vibratorToken.mVibrating = true;
                if (combinedVibration instanceof android.os.CombinedVibration.Mono) {
                    com.android.server.input.InputManagerService.VibrationInfo vibrationInfo = new com.android.server.input.InputManagerService.VibrationInfo(((android.os.CombinedVibration.Mono) combinedVibration).getEffect());
                    this.mNative.vibrate(i, vibrationInfo.getPattern(), vibrationInfo.getAmplitudes(), vibrationInfo.getRepeatIndex(), vibratorToken.mTokenValue);
                } else if (combinedVibration instanceof android.os.CombinedVibration.Stereo) {
                    android.util.SparseArray effects = ((android.os.CombinedVibration.Stereo) combinedVibration).getEffects();
                    android.util.SparseArray<int[]> sparseArray = new android.util.SparseArray<>(effects.size());
                    long[] jArr = new long[0];
                    int i2 = Integer.MIN_VALUE;
                    for (int i3 = 0; i3 < effects.size(); i3++) {
                        com.android.server.input.InputManagerService.VibrationInfo vibrationInfo2 = new com.android.server.input.InputManagerService.VibrationInfo((android.os.VibrationEffect) effects.valueAt(i3));
                        if (jArr.length == 0) {
                            jArr = vibrationInfo2.getPattern();
                        }
                        if (i2 == Integer.MIN_VALUE) {
                            i2 = vibrationInfo2.getRepeatIndex();
                        }
                        sparseArray.put(effects.keyAt(i3), vibrationInfo2.getAmplitudes());
                    }
                    this.mNative.vibrateCombined(i, jArr, sparseArray, i2, vibratorToken.mTokenValue);
                }
            } finally {
            }
        }
    }

    public void cancelVibrate(int i, android.os.IBinder iBinder) {
        synchronized (this.mVibratorLock) {
            com.android.server.input.InputManagerService.VibratorToken vibratorToken = this.mVibratorTokens.get(iBinder);
            if (vibratorToken == null || vibratorToken.mDeviceId != i) {
                return;
            }
            cancelVibrateIfNeeded(vibratorToken);
        }
    }

    void onVibratorTokenDied(com.android.server.input.InputManagerService.VibratorToken vibratorToken) {
        synchronized (this.mVibratorLock) {
            this.mVibratorTokens.remove(vibratorToken.mToken);
        }
        cancelVibrateIfNeeded(vibratorToken);
    }

    private void cancelVibrateIfNeeded(com.android.server.input.InputManagerService.VibratorToken vibratorToken) {
        synchronized (vibratorToken) {
            try {
                if (vibratorToken.mVibrating) {
                    this.mNative.cancelVibrate(vibratorToken.mDeviceId, vibratorToken.mTokenValue);
                    vibratorToken.mVibrating = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifyVibratorState(int i, boolean z) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "notifyVibratorState: deviceId=" + i + " isOn=" + z);
        }
        synchronized (this.mVibratorLock) {
            this.mIsVibrating.put(i, z);
            notifyVibratorStateListenersLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mVibratorLock"})
    private void notifyVibratorStateListenersLocked(int i) {
        if (!this.mVibratorStateListeners.contains(i)) {
            if (DEBUG) {
                android.util.Slog.v(TAG, "Device " + i + " doesn't have vibrator state listener.");
                return;
            }
            return;
        }
        android.os.RemoteCallbackList<android.os.IVibratorStateListener> remoteCallbackList = this.mVibratorStateListeners.get(i);
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                notifyVibratorStateListenerLocked(i, remoteCallbackList.getBroadcastItem(i2));
            } finally {
                remoteCallbackList.finishBroadcast();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mVibratorLock"})
    private void notifyVibratorStateListenerLocked(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        try {
            iVibratorStateListener.onVibrating(this.mIsVibrating.get(i));
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Vibrator state listener failed to call", e);
        }
    }

    public boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        android.os.RemoteCallbackList<android.os.IVibratorStateListener> remoteCallbackList;
        java.util.Objects.requireNonNull(iVibratorStateListener, "listener must not be null");
        synchronized (this.mVibratorLock) {
            try {
                if (this.mVibratorStateListeners.contains(i)) {
                    remoteCallbackList = this.mVibratorStateListeners.get(i);
                } else {
                    remoteCallbackList = new android.os.RemoteCallbackList<>();
                    this.mVibratorStateListeners.put(i, remoteCallbackList);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (remoteCallbackList.register(iVibratorStateListener)) {
                        notifyVibratorStateListenerLocked(i, iVibratorStateListener);
                        return true;
                    }
                    android.util.Slog.e(TAG, "Could not register vibrator state listener " + iVibratorStateListener);
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        synchronized (this.mVibratorLock) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (this.mVibratorStateListeners.contains(i)) {
                    return this.mVibratorStateListeners.get(i).unregister(iVibratorStateListener);
                }
                android.util.Slog.w(TAG, "Vibrator state listener " + i + " doesn't exist");
                return false;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public android.hardware.input.IInputDeviceBatteryState getBatteryState(int i) {
        return this.mBatteryController.getBatteryState(i);
    }

    public void setPointerIconType(int i) {
        if (i == -1) {
            throw new java.lang.IllegalArgumentException("Use setCustomPointerIcon to set custom pointers");
        }
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                this.mPointerIcon = null;
                this.mPointerIconType = i;
                if (this.mCurrentDisplayProperties.pointerIconVisible) {
                    this.mNative.setPointerIconType(this.mPointerIconType);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setCustomPointerIcon(android.view.PointerIcon pointerIcon) {
        java.util.Objects.requireNonNull(pointerIcon);
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                this.mPointerIconType = -1;
                this.mPointerIcon = pointerIcon;
                if (this.mCurrentDisplayProperties.pointerIconVisible) {
                    this.mNative.setCustomPointerIcon(this.mPointerIcon);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setPointerIcon(android.view.PointerIcon pointerIcon, int i, int i2, int i3, android.os.IBinder iBinder) {
        boolean pointerIcon2;
        java.util.Objects.requireNonNull(pointerIcon);
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            this.mPointerIconType = pointerIcon.getType();
            this.mPointerIcon = this.mPointerIconType == -1 ? pointerIcon : null;
            pointerIcon2 = this.mNative.setPointerIcon(pointerIcon, i, i2, i3, iBinder);
        }
        return pointerIcon2;
    }

    public void addPortAssociation(@android.annotation.NonNull java.lang.String str, int i) {
        if (!checkCallingPermission("android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "addPortAssociation()")) {
            throw new java.lang.SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        java.util.Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            this.mRuntimeAssociations.put(str, java.lang.Integer.valueOf(i));
        }
        this.mNative.notifyPortAssociationsChanged();
    }

    public void removePortAssociation(@android.annotation.NonNull java.lang.String str) {
        if (!checkCallingPermission("android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "removePortAssociation()")) {
            throw new java.lang.SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        java.util.Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            this.mRuntimeAssociations.remove(str);
        }
        this.mNative.notifyPortAssociationsChanged();
    }

    public void addUniqueIdAssociation(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (!checkCallingPermission("android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "addUniqueIdAssociation()")) {
            throw new java.lang.SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        synchronized (this.mAssociationsLock) {
            this.mUniqueIdAssociations.put(str, str2);
        }
        this.mNative.changeUniqueIdAssociation();
    }

    public void removeUniqueIdAssociation(@android.annotation.NonNull java.lang.String str) {
        if (!checkCallingPermission("android.permission.ASSOCIATE_INPUT_DEVICE_TO_DISPLAY", "removeUniqueIdAssociation()")) {
            throw new java.lang.SecurityException("Requires ASSOCIATE_INPUT_DEVICE_TO_DISPLAY permission");
        }
        java.util.Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            this.mUniqueIdAssociations.remove(str);
        }
        this.mNative.changeUniqueIdAssociation();
    }

    void setTypeAssociationInternal(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        synchronized (this.mAssociationsLock) {
            this.mDeviceTypeAssociations.put(str, str2);
        }
        this.mNative.changeTypeAssociation();
    }

    void unsetTypeAssociationInternal(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            this.mDeviceTypeAssociations.remove(str);
        }
        this.mNative.changeTypeAssociation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addKeyboardLayoutAssociation(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(str3);
        synchronized (this.mAssociationsLock) {
            this.mKeyboardLayoutAssociations.put(str, android.text.TextUtils.formatSimple("%s,%s", new java.lang.Object[]{str2, str3}));
        }
        this.mNative.changeKeyboardLayoutAssociation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeKeyboardLayoutAssociation(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        synchronized (this.mAssociationsLock) {
            this.mKeyboardLayoutAssociations.remove(str);
        }
        this.mNative.changeKeyboardLayoutAssociation();
    }

    public android.hardware.input.InputSensorInfo[] getSensorList(int i) {
        return this.mNative.getSensorList(i);
    }

    public boolean registerSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "registerSensorListener: listener=" + iInputSensorEventListener + " callingPid=" + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(iInputSensorEventListener, "listener must not be null");
        synchronized (this.mSensorEventLock) {
            try {
                int callingPid = android.os.Binder.getCallingPid();
                if (this.mSensorEventListeners.get(callingPid) != null) {
                    android.util.Slog.e(TAG, "The calling process " + callingPid + " has already registered an InputSensorEventListener.");
                    return false;
                }
                com.android.server.input.InputManagerService.SensorEventListenerRecord sensorEventListenerRecord = new com.android.server.input.InputManagerService.SensorEventListenerRecord(callingPid, iInputSensorEventListener);
                try {
                    iInputSensorEventListener.asBinder().linkToDeath(sensorEventListenerRecord, 0);
                    this.mSensorEventListeners.put(callingPid, sensorEventListenerRecord);
                    return true;
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "unregisterSensorListener: listener=" + iInputSensorEventListener + " callingPid=" + android.os.Binder.getCallingPid());
        }
        java.util.Objects.requireNonNull(iInputSensorEventListener, "listener must not be null");
        synchronized (this.mSensorEventLock) {
            try {
                int callingPid = android.os.Binder.getCallingPid();
                if (this.mSensorEventListeners.get(callingPid) != null) {
                    if (this.mSensorEventListeners.get(callingPid).getListener().asBinder() != iInputSensorEventListener.asBinder()) {
                        throw new java.lang.IllegalArgumentException("listener is not registered");
                    }
                    this.mSensorEventListeners.remove(callingPid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean flushSensor(int i, int i2) {
        synchronized (this.mSensorEventLock) {
            try {
                if (this.mSensorEventListeners.get(android.os.Binder.getCallingPid()) == null) {
                    return false;
                }
                return this.mNative.flushSensor(i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean enableSensor(int i, int i2, int i3, int i4) {
        boolean enableSensor;
        synchronized (this.mInputDevicesLock) {
            enableSensor = this.mNative.enableSensor(i, i2, i3, i4);
        }
        return enableSensor;
    }

    public void disableSensor(int i, int i2) {
        synchronized (this.mInputDevicesLock) {
            this.mNative.disableSensor(i, i2);
        }
    }

    private final class LightSession implements android.os.IBinder.DeathRecipient {
        private final int mDeviceId;
        private int[] mLightIds;
        private android.hardware.lights.LightState[] mLightStates;
        private final java.lang.String mOpPkg;
        private final android.os.IBinder mToken;

        LightSession(int i, java.lang.String str, android.os.IBinder iBinder) {
            this.mDeviceId = i;
            this.mOpPkg = str;
            this.mToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.InputManagerService.DEBUG) {
                android.util.Slog.d(com.android.server.input.InputManagerService.TAG, "Light token died.");
            }
            synchronized (com.android.server.input.InputManagerService.this.mLightLock) {
                com.android.server.input.InputManagerService.this.closeLightSession(this.mDeviceId, this.mToken);
                com.android.server.input.InputManagerService.this.mLightSessions.remove(this.mToken);
            }
        }
    }

    public java.util.List<android.hardware.lights.Light> getLights(int i) {
        return this.mNative.getLights(i);
    }

    private void setLightStateInternal(int i, android.hardware.lights.Light light, android.hardware.lights.LightState lightState) {
        java.util.Objects.requireNonNull(light, "light does not exist");
        if (DEBUG) {
            android.util.Slog.d(TAG, "setLightStateInternal device " + i + " light " + light + "lightState " + lightState);
        }
        if (light.getType() == 10002) {
            this.mNative.setLightPlayerId(i, light.getId(), lightState.getPlayerId());
        } else {
            this.mNative.setLightColor(i, light.getId(), lightState.getColor());
        }
    }

    private void setLightStatesInternal(int i, int[] iArr, android.hardware.lights.LightState[] lightStateArr) {
        java.util.List<android.hardware.lights.Light> lights = this.mNative.getLights(i);
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        for (int i2 = 0; i2 < lights.size(); i2++) {
            sparseArray.put(lights.get(i2).getId(), lights.get(i2));
        }
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if (sparseArray.contains(iArr[i3])) {
                setLightStateInternal(i, (android.hardware.lights.Light) sparseArray.get(iArr[i3]), lightStateArr[i3]);
            }
        }
    }

    public void setLightStates(int i, int[] iArr, android.hardware.lights.LightState[] lightStateArr, android.os.IBinder iBinder) {
        com.android.internal.util.Preconditions.checkArgument(iArr.length == lightStateArr.length, "lights and light states are not same length");
        synchronized (this.mLightLock) {
            try {
                com.android.server.input.InputManagerService.LightSession lightSession = this.mLightSessions.get(iBinder);
                com.android.internal.util.Preconditions.checkArgument(lightSession != null, "not registered");
                com.android.internal.util.Preconditions.checkState(lightSession.mDeviceId == i, "Incorrect device ID");
                lightSession.mLightIds = (int[]) iArr.clone();
                lightSession.mLightStates = (android.hardware.lights.LightState[]) lightStateArr.clone();
                if (DEBUG) {
                    android.util.Slog.d(TAG, "setLightStates for " + lightSession.mOpPkg + " device " + i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        setLightStatesInternal(i, iArr, lightStateArr);
    }

    @android.annotation.Nullable
    public android.hardware.lights.LightState getLightState(int i, int i2) {
        android.hardware.lights.LightState lightState;
        synchronized (this.mLightLock) {
            lightState = new android.hardware.lights.LightState(this.mNative.getLightColor(i, i2), this.mNative.getLightPlayerId(i, i2));
        }
        return lightState;
    }

    public void openLightSession(int i, java.lang.String str, android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(iBinder);
        synchronized (this.mLightLock) {
            com.android.internal.util.Preconditions.checkState(this.mLightSessions.get(iBinder) == null, "already registered");
            com.android.server.input.InputManagerService.LightSession lightSession = new com.android.server.input.InputManagerService.LightSession(i, str, iBinder);
            try {
                iBinder.linkToDeath(lightSession, 0);
            } catch (android.os.RemoteException e) {
                e.rethrowAsRuntimeException();
            }
            this.mLightSessions.put(iBinder, lightSession);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Open light session for " + str + " device " + i);
            }
        }
    }

    public void closeLightSession(int i, android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(iBinder);
        synchronized (this.mLightLock) {
            try {
                com.android.server.input.InputManagerService.LightSession lightSession = this.mLightSessions.get(iBinder);
                com.android.internal.util.Preconditions.checkState(lightSession != null, "not registered");
                java.util.Arrays.fill(lightSession.mLightStates, new android.hardware.lights.LightState(0));
                setLightStatesInternal(i, lightSession.mLightIds, lightSession.mLightStates);
                this.mLightSessions.remove(iBinder);
                if (!this.mLightSessions.isEmpty()) {
                    com.android.server.input.InputManagerService.LightSession valueAt = this.mLightSessions.valueAt(0);
                    setLightStatesInternal(i, valueAt.mLightIds, valueAt.mLightStates);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cancelCurrentTouch() {
        if (!checkCallingPermission("android.permission.MONITOR_INPUT", "cancelCurrentTouch()")) {
            throw new java.lang.SecurityException("Requires MONITOR_INPUT permission");
        }
        this.mNative.cancelCurrentTouch();
    }

    public void registerBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) {
        java.util.Objects.requireNonNull(iInputDeviceBatteryListener);
        this.mBatteryController.registerBatteryListener(i, iInputDeviceBatteryListener, android.os.Binder.getCallingPid());
    }

    public void unregisterBatteryListener(int i, android.hardware.input.IInputDeviceBatteryListener iInputDeviceBatteryListener) {
        java.util.Objects.requireNonNull(iInputDeviceBatteryListener);
        this.mBatteryController.unregisterBatteryListener(i, iInputDeviceBatteryListener, android.os.Binder.getCallingPid());
    }

    @android.annotation.EnforcePermission("android.permission.BLUETOOTH")
    public java.lang.String getInputDeviceBluetoothAddress(int i) {
        super.getInputDeviceBluetoothAddress_enforcePermission();
        java.lang.String bluetoothAddress = this.mNative.getBluetoothAddress(i);
        if (bluetoothAddress == null) {
            return null;
        }
        if (!android.bluetooth.BluetoothAdapter.checkBluetoothAddress(bluetoothAddress)) {
            throw new java.lang.IllegalStateException("The Bluetooth address of input device " + i + " should not be invalid: address=" + bluetoothAddress);
        }
        return bluetoothAddress;
    }

    @android.annotation.EnforcePermission("android.permission.MONITOR_INPUT")
    public void pilferPointers(android.os.IBinder iBinder) {
        super.pilferPointers_enforcePermission();
        java.util.Objects.requireNonNull(iBinder);
        this.mNative.pilferPointers(iBinder);
    }

    @android.annotation.EnforcePermission("android.permission.MONITOR_KEYBOARD_BACKLIGHT")
    public void registerKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) {
        super.registerKeyboardBacklightListener_enforcePermission();
        java.util.Objects.requireNonNull(iKeyboardBacklightListener);
        this.mKeyboardBacklightController.registerKeyboardBacklightListener(iKeyboardBacklightListener, android.os.Binder.getCallingPid());
    }

    @android.annotation.EnforcePermission("android.permission.MONITOR_KEYBOARD_BACKLIGHT")
    public void unregisterKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener) {
        super.unregisterKeyboardBacklightListener_enforcePermission();
        java.util.Objects.requireNonNull(iKeyboardBacklightListener);
        this.mKeyboardBacklightController.unregisterKeyboardBacklightListener(iKeyboardBacklightListener, android.os.Binder.getCallingPid());
    }

    public android.hardware.input.HostUsiVersion getHostUsiVersionFromDisplayConfig(int i) {
        return this.mDisplayManagerInternal.getHostUsiVersion(i);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            java.io.PrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.println("INPUT MANAGER (dumpsys input)\n");
            java.lang.String dump = this.mNative.dump();
            if (dump != null) {
                printWriter.println(dump);
            }
            indentingPrintWriter.println("Input Manager Service (Java) State:");
            indentingPrintWriter.increaseIndent();
            dumpAssociations(indentingPrintWriter);
            dumpSpyWindowGestureMonitors(indentingPrintWriter);
            dumpDisplayInputPropertiesValues(indentingPrintWriter);
            this.mBatteryController.dump(indentingPrintWriter);
            this.mKeyboardBacklightController.dump(indentingPrintWriter);
        }
    }

    private void dumpAssociations(final android.util.IndentingPrintWriter indentingPrintWriter) {
        if (!this.mStaticAssociations.isEmpty()) {
            indentingPrintWriter.println("Static Associations:");
            this.mStaticAssociations.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.input.InputManagerService.lambda$dumpAssociations$3(indentingPrintWriter, (java.lang.String) obj, (java.lang.Integer) obj2);
                }
            });
        }
        synchronized (this.mAssociationsLock) {
            try {
                if (!this.mRuntimeAssociations.isEmpty()) {
                    indentingPrintWriter.println("Runtime Associations:");
                    this.mRuntimeAssociations.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda4
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.input.InputManagerService.lambda$dumpAssociations$4(indentingPrintWriter, (java.lang.String) obj, (java.lang.Integer) obj2);
                        }
                    });
                }
                if (!this.mUniqueIdAssociations.isEmpty()) {
                    indentingPrintWriter.println("Unique Id Associations:");
                    this.mUniqueIdAssociations.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda5
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.input.InputManagerService.lambda$dumpAssociations$5(indentingPrintWriter, (java.lang.String) obj, (java.lang.String) obj2);
                        }
                    });
                }
                if (!this.mDeviceTypeAssociations.isEmpty()) {
                    indentingPrintWriter.println("Type Associations:");
                    this.mDeviceTypeAssociations.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda6
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.input.InputManagerService.lambda$dumpAssociations$6(indentingPrintWriter, (java.lang.String) obj, (java.lang.String) obj2);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpAssociations$3(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Integer num) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  display: " + num);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpAssociations$4(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Integer num) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  display: " + num);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpAssociations$5(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.String str2) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  uniqueId: " + str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpAssociations$6(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.String str2) {
        indentingPrintWriter.print("  port: " + str);
        indentingPrintWriter.println("  type: " + str2);
    }

    private void dumpSpyWindowGestureMonitors(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mInputMonitors) {
            try {
                if (this.mInputMonitors.isEmpty()) {
                    return;
                }
                indentingPrintWriter.println("Gesture Monitors (implemented as spy windows):");
                java.util.Iterator<com.android.server.input.GestureMonitorSpyWindow> it = this.mInputMonitors.values().iterator();
                int i = 0;
                while (it.hasNext()) {
                    indentingPrintWriter.append("  " + i + ": ").println(it.next().dump());
                    i++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dumpDisplayInputPropertiesValues(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                if (this.mAdditionalDisplayInputProperties.size() != 0) {
                    indentingPrintWriter.println("mAdditionalDisplayInputProperties:");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < this.mAdditionalDisplayInputProperties.size(); i++) {
                        indentingPrintWriter.println("displayId: " + this.mAdditionalDisplayInputProperties.keyAt(i));
                        com.android.server.input.InputManagerService.AdditionalDisplayInputProperties valueAt = this.mAdditionalDisplayInputProperties.valueAt(i);
                        indentingPrintWriter.println("mousePointerAccelerationEnabled: " + valueAt.mousePointerAccelerationEnabled);
                        indentingPrintWriter.println("pointerIconVisible: " + valueAt.pointerIconVisible);
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                if (this.mOverriddenPointerDisplayId != -1) {
                    indentingPrintWriter.println("mOverriddenPointerDisplayId: " + this.mOverriddenPointerDisplayId);
                }
                indentingPrintWriter.println("mAcknowledgedPointerDisplayId=" + this.mAcknowledgedPointerDisplayId);
                indentingPrintWriter.println("mRequestedPointerDisplayId=" + this.mRequestedPointerDisplayId);
                indentingPrintWriter.println("mPointerIconType=" + android.view.PointerIcon.typeToString(this.mPointerIconType));
                indentingPrintWriter.println("mPointerIcon=" + this.mPointerIcon);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCallingPermission(java.lang.String str, java.lang.String str2) {
        return checkCallingPermission(str, str2, false);
    }

    private boolean checkCallingPermission(java.lang.String str, java.lang.String str2, boolean z) {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid() || this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        if (z) {
            android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            java.util.Objects.requireNonNull(activityManagerInternal, "ActivityManagerInternal should not be null.");
            int instrumentationSourceUid = activityManagerInternal.getInstrumentationSourceUid(android.os.Binder.getCallingUid());
            if (instrumentationSourceUid != -1) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (this.mContext.checkPermission(str, -1, instrumentationSourceUid) == 0) {
                        return true;
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        android.util.Slog.w(TAG, "Permission Denial: " + str2 + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires " + str);
        return false;
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        synchronized (this.mInputFilterLock) {
        }
        synchronized (this.mAssociationsLock) {
        }
        synchronized (this.mLidSwitchLock) {
        }
        synchronized (this.mInputMonitors) {
        }
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
        }
        this.mBatteryController.monitor();
        this.mPointerIconCache.monitor();
        this.mNative.monitor();
    }

    private void notifyConfigurationChanged(long j) {
        this.mWindowManagerCallbacks.notifyConfigurationChanged();
    }

    private void notifyInputDevicesChanged(android.view.InputDevice[] inputDeviceArr) {
        synchronized (this.mInputDevicesLock) {
            try {
                if (!this.mInputDevicesChangedPending) {
                    this.mInputDevicesChangedPending = true;
                    this.mHandler.obtainMessage(1, this.mInputDevices).sendToTarget();
                }
                this.mInputDevices = inputDeviceArr;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifySwitch(long j, int i, int i2) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "notifySwitch: values=" + java.lang.Integer.toHexString(i) + ", mask=" + java.lang.Integer.toHexString(i2));
        }
        if ((i2 & 1) != 0) {
            boolean z = (i & 1) == 0;
            synchronized (this.mLidSwitchLock) {
                try {
                    if (this.mSystemReady) {
                        for (int i3 = 0; i3 < this.mLidSwitchCallbacks.size(); i3++) {
                            this.mLidSwitchCallbacks.get(i3).notifyLidSwitchChanged(j, z);
                        }
                    }
                } finally {
                }
            }
        }
        if ((i2 & 512) != 0) {
            boolean z2 = (i & 512) != 0;
            this.mWindowManagerCallbacks.notifyCameraLensCoverSwitchChanged(j, z2);
            setSensorPrivacy(2, z2);
        }
        if (this.mUseDevInputEventForAudioJack && (i2 & 212) != 0) {
            this.mWiredAccessoryCallbacks.notifyWiredAccessoryChanged(j, i, i2);
        }
        if ((i2 & 2) != 0) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = (int) ((-1) & j);
            obtain.argi2 = (int) (j >> 32);
            obtain.arg1 = java.lang.Boolean.valueOf((i & 2) != 0);
            this.mHandler.obtainMessage(3, obtain).sendToTarget();
        }
        if ((i2 & 16384) != 0) {
            boolean z3 = (i & 16384) != 0;
            ((android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)).setMicrophoneMuteFromSwitch(z3);
            setSensorPrivacy(1, z3);
        }
    }

    private void setSensorPrivacy(int i, boolean z) {
        ((android.hardware.SensorPrivacyManagerInternal) com.android.server.LocalServices.getService(android.hardware.SensorPrivacyManagerInternal.class)).setPhysicalToggleSensorPrivacy(-2, i, z);
    }

    private void notifyInputChannelBroken(android.os.IBinder iBinder) {
        synchronized (this.mInputMonitors) {
            try {
                if (this.mInputMonitors.containsKey(iBinder)) {
                    removeSpyWindowGestureMonitor(iBinder);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mWindowManagerCallbacks.notifyInputChannelBroken(iBinder);
    }

    private void notifyFocusChanged(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        this.mWindowManagerCallbacks.notifyFocusChanged(iBinder, iBinder2);
    }

    private void notifyDropWindow(android.os.IBinder iBinder, float f, float f2) {
        this.mWindowManagerCallbacks.notifyDropWindow(iBinder, f, f2);
    }

    private void notifyNoFocusedWindowAnr(android.view.InputApplicationHandle inputApplicationHandle) {
        this.mWindowManagerCallbacks.notifyNoFocusedWindowAnr(inputApplicationHandle);
    }

    private void notifyWindowUnresponsive(android.os.IBinder iBinder, int i, boolean z, java.lang.String str) {
        this.mWindowManagerCallbacks.notifyWindowUnresponsive(iBinder, z ? java.util.OptionalInt.of(i) : java.util.OptionalInt.empty(), str);
    }

    private void notifyWindowResponsive(android.os.IBinder iBinder, int i, boolean z) {
        this.mWindowManagerCallbacks.notifyWindowResponsive(iBinder, z ? java.util.OptionalInt.of(i) : java.util.OptionalInt.empty());
    }

    private void notifySensorEvent(int i, int i2, int i3, long j, float[] fArr) {
        int size;
        if (DEBUG) {
            android.util.Slog.d(TAG, "notifySensorEvent: deviceId=" + i + " sensorType=" + i2 + " values=" + java.util.Arrays.toString(fArr));
        }
        this.mSensorEventListenersToNotify.clear();
        synchronized (this.mSensorEventLock) {
            try {
                size = this.mSensorEventListeners.size();
                for (int i4 = 0; i4 < size; i4++) {
                    this.mSensorEventListenersToNotify.add(this.mSensorEventListeners.valueAt(i4));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int i5 = 0; i5 < size; i5++) {
            this.mSensorEventListenersToNotify.get(i5).notifySensorEvent(i, i2, i3, j, fArr);
        }
        this.mSensorEventListenersToNotify.clear();
    }

    private void notifySensorAccuracy(int i, int i2, int i3) {
        int size;
        int i4;
        this.mSensorAccuracyListenersToNotify.clear();
        synchronized (this.mSensorEventLock) {
            try {
                size = this.mSensorEventListeners.size();
                for (int i5 = 0; i5 < size; i5++) {
                    this.mSensorAccuracyListenersToNotify.add(this.mSensorEventListeners.valueAt(i5));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (i4 = 0; i4 < size; i4++) {
            this.mSensorAccuracyListenersToNotify.get(i4).notifySensorAccuracy(i, i2, i3);
        }
        this.mSensorAccuracyListenersToNotify.clear();
    }

    final boolean filterInputEvent(android.view.InputEvent inputEvent, int i) {
        synchronized (this.mInputFilterLock) {
            if (this.mInputFilter != null) {
                try {
                    this.mInputFilter.filterInputEvent(inputEvent, i);
                } catch (android.os.RemoteException e) {
                }
                return false;
            }
            inputEvent.recycle();
            return true;
        }
    }

    private int interceptKeyBeforeQueueing(android.view.KeyEvent keyEvent, int i) {
        synchronized (this.mFocusEventDebugViewLock) {
            try {
                if (this.mFocusEventDebugView != null) {
                    this.mFocusEventDebugView.reportKeyEvent(keyEvent);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mWindowManagerCallbacks.interceptKeyBeforeQueueing(keyEvent, i);
    }

    private int interceptMotionBeforeQueueingNonInteractive(int i, int i2, int i3, long j, int i4) {
        return this.mWindowManagerCallbacks.interceptMotionBeforeQueueingNonInteractive(i, i2, i3, j, i4);
    }

    private long interceptKeyBeforeDispatching(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        return this.mWindowManagerCallbacks.interceptKeyBeforeDispatching(iBinder, keyEvent, i);
    }

    private android.view.KeyEvent dispatchUnhandledKey(android.os.IBinder iBinder, android.view.KeyEvent keyEvent, int i) {
        return this.mWindowManagerCallbacks.dispatchUnhandledKey(iBinder, keyEvent, i);
    }

    private void onPointerDownOutsideFocus(android.os.IBinder iBinder) {
        this.mWindowManagerCallbacks.onPointerDownOutsideFocus(iBinder);
    }

    private int getVirtualKeyQuietTimeMillis() {
        return this.mContext.getResources().getInteger(android.R.integer.config_userTypePackageWhitelistMode);
    }

    private static java.lang.String[] getExcludedDeviceNames() {
        java.io.FileInputStream fileInputStream;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.io.File[] fileArr = {android.os.Environment.getRootDirectory(), android.os.Environment.getVendorDirectory()};
        for (int i = 0; i < 2; i++) {
            java.io.File file = new java.io.File(fileArr[i], EXCLUDED_DEVICES_PATH);
            try {
                fileInputStream = new java.io.FileInputStream(file);
            } catch (java.io.FileNotFoundException e) {
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(TAG, "Could not parse '" + file.getAbsolutePath() + "'", e2);
            }
            try {
                arrayList.addAll(com.android.server.input.ConfigurationProcessor.processExcludedDeviceNames(fileInputStream));
                fileInputStream.close();
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    private boolean isPerDisplayTouchModeEnabled() {
        return this.mContext.getResources().getBoolean(android.R.bool.config_perDisplayFocusEnabled);
    }

    private void notifyStylusGestureStarted(int i, long j) {
        this.mBatteryController.notifyStylusGestureStarted(i, j);
    }

    private static <T> java.lang.String[] flatten(@android.annotation.NonNull java.util.Map<java.lang.String, T> map) {
        final java.util.ArrayList arrayList = new java.util.ArrayList(map.size() * 2);
        map.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.input.InputManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.input.InputManagerService.lambda$flatten$7(arrayList, (java.lang.String) obj, obj2);
            }
        });
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$flatten$7(java.util.List list, java.lang.String str, java.lang.Object obj) {
        list.add(str);
        list.add(obj.toString());
    }

    private static java.util.Map<java.lang.String, java.lang.Integer> loadStaticInputPortAssociations() {
        java.io.File file = new java.io.File(android.os.Environment.getOdmDirectory(), PORT_ASSOCIATIONS_PATH);
        if (!file.exists()) {
            file = new java.io.File(android.os.Environment.getVendorDirectory(), PORT_ASSOCIATIONS_PATH);
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                java.util.Map<java.lang.String, java.lang.Integer> processInputPortAssociations = com.android.server.input.ConfigurationProcessor.processInputPortAssociations(fileInputStream);
                fileInputStream.close();
                return processInputPortAssociations;
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.FileNotFoundException e) {
            return new java.util.HashMap();
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Could not parse '" + file.getAbsolutePath() + "'", e2);
            return new java.util.HashMap();
        }
    }

    private java.lang.String[] getInputPortAssociations() {
        java.util.HashMap hashMap = new java.util.HashMap(this.mStaticAssociations);
        synchronized (this.mAssociationsLock) {
            hashMap.putAll(this.mRuntimeAssociations);
        }
        return flatten(hashMap);
    }

    private java.lang.String[] getInputUniqueIdAssociations() {
        java.util.HashMap hashMap;
        synchronized (this.mAssociationsLock) {
            hashMap = new java.util.HashMap(this.mUniqueIdAssociations);
        }
        return flatten(hashMap);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String[] getDeviceTypeAssociations() {
        java.util.HashMap hashMap;
        synchronized (this.mAssociationsLock) {
            hashMap = new java.util.HashMap(this.mDeviceTypeAssociations);
        }
        return flatten(hashMap);
    }

    @com.android.internal.annotations.VisibleForTesting
    private java.lang.String[] getKeyboardLayoutAssociations() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        synchronized (this.mAssociationsLock) {
            arrayMap.putAll(this.mKeyboardLayoutAssociations);
        }
        return flatten(arrayMap);
    }

    public boolean canDispatchToDisplay(int i, int i2) {
        return this.mNative.canDispatchToDisplay(i, i2);
    }

    private int getHoverTapTimeout() {
        return android.view.ViewConfiguration.getHoverTapTimeout();
    }

    private int getHoverTapSlop() {
        return android.view.ViewConfiguration.getHoverTapSlop();
    }

    private int getDoubleTapTimeout() {
        return android.view.ViewConfiguration.getDoubleTapTimeout();
    }

    private int getLongPressTimeout() {
        return android.view.ViewConfiguration.getLongPressTimeout();
    }

    private int getPointerLayer() {
        return this.mWindowManagerCallbacks.getPointerLayer();
    }

    @android.annotation.NonNull
    private android.view.PointerIcon getLoadedPointerIcon(int i, int i2) {
        return this.mPointerIconCache.getLoadedPointerIcon(i, i2);
    }

    private long getParentSurfaceForPointers(int i) {
        android.view.SurfaceControl parentSurfaceForPointers = this.mWindowManagerCallbacks.getParentSurfaceForPointers(i);
        if (parentSurfaceForPointers == null) {
            return 0L;
        }
        return parentSurfaceForPointers.mNativeObject;
    }

    private java.lang.String[] getKeyboardLayoutOverlay(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str, java.lang.String str2) {
        if (!this.mSystemReady) {
            return null;
        }
        return this.mKeyboardLayoutManager.getKeyboardLayoutOverlay(inputDeviceIdentifier, str, str2);
    }

    @android.annotation.EnforcePermission("android.permission.REMAP_MODIFIER_KEYS")
    public void remapModifierKey(int i, int i2) {
        super.remapModifierKey_enforcePermission();
        this.mKeyRemapper.remapKey(i, i2);
    }

    @android.annotation.EnforcePermission("android.permission.REMAP_MODIFIER_KEYS")
    public void clearAllModifierKeyRemappings() {
        super.clearAllModifierKeyRemappings_enforcePermission();
        this.mKeyRemapper.clearAllKeyRemappings();
    }

    @android.annotation.EnforcePermission("android.permission.REMAP_MODIFIER_KEYS")
    public java.util.Map<java.lang.Integer, java.lang.Integer> getModifierKeyRemapping() {
        super.getModifierKeyRemapping_enforcePermission();
        return this.mKeyRemapper.getKeyRemapping();
    }

    private java.lang.String getDeviceAlias(java.lang.String str) {
        android.bluetooth.BluetoothAdapter.checkBluetoothAddress(str);
        return null;
    }

    private static class PointerDisplayIdChangedArgs {
        final int mPointerDisplayId;
        final float mXPosition;
        final float mYPosition;

        PointerDisplayIdChangedArgs(int i, float f, float f2) {
            this.mPointerDisplayId = i;
            this.mXPosition = f;
            this.mYPosition = f2;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onPointerDisplayIdChanged(int i, float f, float f2) {
        this.mHandler.obtainMessage(4, new com.android.server.input.InputManagerService.PointerDisplayIdChangedArgs(i, f, f2)).sendToTarget();
    }

    @android.annotation.EnforcePermission("android.permission.MONITOR_STICKY_MODIFIER_STATE")
    public void registerStickyModifierStateListener(@android.annotation.NonNull android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) {
        super.registerStickyModifierStateListener_enforcePermission();
        java.util.Objects.requireNonNull(iStickyModifierStateListener);
        this.mStickyModifierStateController.registerStickyModifierStateListener(iStickyModifierStateListener, android.os.Binder.getCallingPid());
    }

    @android.annotation.EnforcePermission("android.permission.MONITOR_STICKY_MODIFIER_STATE")
    public void unregisterStickyModifierStateListener(@android.annotation.NonNull android.hardware.input.IStickyModifierStateListener iStickyModifierStateListener) {
        super.unregisterStickyModifierStateListener_enforcePermission();
        java.util.Objects.requireNonNull(iStickyModifierStateListener);
        this.mStickyModifierStateController.unregisterStickyModifierStateListener(iStickyModifierStateListener, android.os.Binder.getCallingPid());
    }

    void notifyStickyModifierStateChanged(int i, int i2) {
        this.mStickyModifierStateController.notifyStickyModifierStateChanged(i, i2);
    }

    boolean isInputMethodConnectionActive() {
        return this.mInputMethodManagerInternal != null && this.mInputMethodManagerInternal.isAnyInputConnectionActive();
    }

    private final class InputManagerHandler extends android.os.Handler {
        public InputManagerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.input.InputManagerService.this.deliverInputDevicesChanged((android.view.InputDevice[]) message.obj);
                    break;
                case 2:
                    com.android.server.input.InputManagerService.this.reloadDeviceAliases();
                    break;
                case 3:
                    com.android.server.input.InputManagerService.this.deliverTabletModeChanged((r6.argi1 & 4294967295L) | (r6.argi2 << 32), ((java.lang.Boolean) ((com.android.internal.os.SomeArgs) message.obj).arg1).booleanValue());
                    break;
                case 4:
                    com.android.server.input.InputManagerService.this.handlePointerDisplayIdChanged((com.android.server.input.InputManagerService.PointerDisplayIdChangedArgs) message.obj);
                    break;
            }
        }
    }

    private final class InputFilterHost extends android.view.IInputFilterHost.Stub {

        @com.android.internal.annotations.GuardedBy({"mInputFilterLock"})
        private boolean mDisconnected;

        private InputFilterHost() {
        }

        @com.android.internal.annotations.GuardedBy({"mInputFilterLock"})
        public void disconnectLocked() {
            this.mDisconnected = true;
        }

        public void sendInputEvent(android.view.InputEvent inputEvent, int i) {
            if (!com.android.server.input.InputManagerService.this.checkCallingPermission("android.permission.INJECT_EVENTS", "sendInputEvent()")) {
                throw new java.lang.SecurityException("The INJECT_EVENTS permission is required for injecting input events.");
            }
            java.util.Objects.requireNonNull(inputEvent, "event must not be null");
            synchronized (com.android.server.input.InputManagerService.this.mInputFilterLock) {
                try {
                    if (!this.mDisconnected) {
                        com.android.server.input.InputManagerService.this.mNative.injectInputEvent(inputEvent, false, -1, 0, 0, i | 67108864);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class InputMonitorHost extends android.view.IInputMonitorHost.Stub {
        private final android.os.IBinder mInputChannelToken;

        InputMonitorHost(android.os.IBinder iBinder) {
            this.mInputChannelToken = iBinder;
        }

        public void pilferPointers() {
            com.android.server.input.InputManagerService.this.mNative.pilferPointers(this.mInputChannelToken);
        }

        public void dispose() {
            com.android.server.input.InputManagerService.this.removeSpyWindowGestureMonitor(this.mInputChannelToken);
        }
    }

    private final class InputDevicesChangedListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.input.IInputDevicesChangedListener mListener;
        private final int mPid;

        public InputDevicesChangedListenerRecord(int i, android.hardware.input.IInputDevicesChangedListener iInputDevicesChangedListener) {
            this.mPid = i;
            this.mListener = iInputDevicesChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.InputManagerService.DEBUG) {
                android.util.Slog.d(com.android.server.input.InputManagerService.TAG, "Input devices changed listener for pid " + this.mPid + " died.");
            }
            com.android.server.input.InputManagerService.this.onInputDevicesChangedListenerDied(this.mPid);
        }

        public void notifyInputDevicesChanged(int[] iArr) {
            try {
                this.mListener.onInputDevicesChanged(iArr);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.input.InputManagerService.TAG, "Failed to notify process " + this.mPid + " that input devices changed, assuming it died.", e);
                binderDied();
            }
        }
    }

    private final class TabletModeChangedListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.input.ITabletModeChangedListener mListener;
        private final int mPid;

        public TabletModeChangedListenerRecord(int i, android.hardware.input.ITabletModeChangedListener iTabletModeChangedListener) {
            this.mPid = i;
            this.mListener = iTabletModeChangedListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.InputManagerService.DEBUG) {
                android.util.Slog.d(com.android.server.input.InputManagerService.TAG, "Tablet mode changed listener for pid " + this.mPid + " died.");
            }
            com.android.server.input.InputManagerService.this.onTabletModeChangedListenerDied(this.mPid);
        }

        public void notifyTabletModeChanged(long j, boolean z) {
            try {
                this.mListener.onTabletModeChanged(j, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.input.InputManagerService.TAG, "Failed to notify process " + this.mPid + " that tablet mode changed, assuming it died.", e);
                binderDied();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSensorEventListenerDied(int i) {
        synchronized (this.mSensorEventLock) {
            this.mSensorEventListeners.remove(i);
        }
    }

    private final class SensorEventListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.input.IInputSensorEventListener mListener;
        private final int mPid;

        SensorEventListenerRecord(int i, android.hardware.input.IInputSensorEventListener iInputSensorEventListener) {
            this.mPid = i;
            this.mListener = iInputSensorEventListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.InputManagerService.DEBUG) {
                android.util.Slog.d(com.android.server.input.InputManagerService.TAG, "Sensor event listener for pid " + this.mPid + " died.");
            }
            com.android.server.input.InputManagerService.this.onSensorEventListenerDied(this.mPid);
        }

        public android.hardware.input.IInputSensorEventListener getListener() {
            return this.mListener;
        }

        public void notifySensorEvent(int i, int i2, int i3, long j, float[] fArr) {
            try {
                this.mListener.onInputSensorChanged(i, i2, i3, j, fArr);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.input.InputManagerService.TAG, "Failed to notify process " + this.mPid + " that sensor event notified, assuming it died.", e);
                binderDied();
            }
        }

        public void notifySensorAccuracy(int i, int i2, int i3) {
            try {
                this.mListener.onInputSensorAccuracyChanged(i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.input.InputManagerService.TAG, "Failed to notify process " + this.mPid + " that sensor accuracy notified, assuming it died.", e);
                binderDied();
            }
        }
    }

    private final class VibratorToken implements android.os.IBinder.DeathRecipient {
        public final int mDeviceId;
        public final android.os.IBinder mToken;
        public final int mTokenValue;
        public boolean mVibrating;

        public VibratorToken(int i, android.os.IBinder iBinder, int i2) {
            this.mDeviceId = i;
            this.mToken = iBinder;
            this.mTokenValue = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.input.InputManagerService.DEBUG) {
                android.util.Slog.d(com.android.server.input.InputManagerService.TAG, "Vibrator token died.");
            }
            com.android.server.input.InputManagerService.this.onVibratorTokenDied(this);
        }
    }

    private final class LocalService extends com.android.server.input.InputManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setDisplayViewports(java.util.List<android.hardware.display.DisplayViewport> list) {
            com.android.server.input.InputManagerService.this.setDisplayViewportsInternal(list);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setInteractive(boolean z) {
            com.android.server.input.InputManagerService.this.mNative.setInteractive(z);
            com.android.server.input.InputManagerService.this.mBatteryController.onInteractiveChanged(z);
            com.android.server.input.InputManagerService.this.mKeyboardBacklightController.onInteractiveChanged(z);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void toggleCapsLock(int i) {
            com.android.server.input.InputManagerService.this.mNative.toggleCapsLock(i);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setPulseGestureEnabled(boolean z) {
            if (com.android.server.input.InputManagerService.this.mDoubleTouchGestureEnableFile == null) {
                return;
            }
            java.io.FileWriter fileWriter = null;
            try {
                try {
                    java.io.FileWriter fileWriter2 = new java.io.FileWriter(com.android.server.input.InputManagerService.this.mDoubleTouchGestureEnableFile);
                    try {
                        fileWriter2.write(z ? "1" : "0");
                        libcore.io.IoUtils.closeQuietly(fileWriter2);
                    } catch (java.io.IOException e) {
                        e = e;
                        fileWriter = fileWriter2;
                        android.util.Log.wtf(com.android.server.input.InputManagerService.TAG, "Unable to setPulseGestureEnabled", e);
                        libcore.io.IoUtils.closeQuietly(fileWriter);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        fileWriter = fileWriter2;
                        libcore.io.IoUtils.closeQuietly(fileWriter);
                        throw th;
                    }
                } catch (java.io.IOException e2) {
                    e = e2;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }

        @Override // com.android.server.input.InputManagerInternal
        public boolean transferTouchGesture(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
            return com.android.server.input.InputManagerService.this.transferTouchGesture(iBinder, iBinder2);
        }

        @Override // com.android.server.input.InputManagerInternal
        public boolean setVirtualMousePointerDisplayId(int i) {
            return com.android.server.input.InputManagerService.this.setVirtualMousePointerDisplayIdBlocking(i);
        }

        @Override // com.android.server.input.InputManagerInternal
        public int getVirtualMousePointerDisplayId() {
            return com.android.server.input.InputManagerService.this.getVirtualMousePointerDisplayId();
        }

        @Override // com.android.server.input.InputManagerInternal
        public android.graphics.PointF getCursorPosition(int i) {
            float[] mouseCursorPosition = com.android.server.input.InputManagerService.this.mNative.getMouseCursorPosition(i);
            if (mouseCursorPosition == null || mouseCursorPosition.length != 2) {
                throw new java.lang.IllegalStateException("Failed to get mouse cursor position");
            }
            return new android.graphics.PointF(mouseCursorPosition[0], mouseCursorPosition[1]);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setMousePointerAccelerationEnabled(boolean z, int i) {
            com.android.server.input.InputManagerService.this.setMousePointerAccelerationEnabled(z, i);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setDisplayEligibilityForPointerCapture(int i, boolean z) {
            com.android.server.input.InputManagerService.this.setDisplayEligibilityForPointerCapture(i, z);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setPointerIconVisible(boolean z, int i) {
            com.android.server.input.InputManagerService.this.setPointerIconVisible(z, i);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void registerLidSwitchCallback(com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
            com.android.server.input.InputManagerService.this.registerLidSwitchCallbackInternal(lidSwitchCallback);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void unregisterLidSwitchCallback(com.android.server.input.InputManagerInternal.LidSwitchCallback lidSwitchCallback) {
            com.android.server.input.InputManagerService.this.unregisterLidSwitchCallbackInternal(lidSwitchCallback);
        }

        @Override // com.android.server.input.InputManagerInternal
        public android.view.InputChannel createInputChannel(java.lang.String str) {
            return com.android.server.input.InputManagerService.this.createInputChannel(str);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void pilferPointers(android.os.IBinder iBinder) {
            com.android.server.input.InputManagerService.this.mNative.pilferPointers(iBinder);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void onInputMethodSubtypeChangedForKeyboardLayoutMapping(int i, @android.annotation.Nullable com.android.internal.inputmethod.InputMethodSubtypeHandle inputMethodSubtypeHandle, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            com.android.server.input.InputManagerService.this.mKeyboardLayoutManager.onInputMethodSubtypeChanged(i, inputMethodSubtypeHandle, inputMethodSubtype);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void notifyUserActivity() {
            com.android.server.input.InputManagerService.this.mKeyboardBacklightController.notifyUserActivity();
        }

        @Override // com.android.server.input.InputManagerInternal
        public void incrementKeyboardBacklight(int i) {
            com.android.server.input.InputManagerService.this.mKeyboardBacklightController.incrementKeyboardBacklight(i);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void decrementKeyboardBacklight(int i) {
            com.android.server.input.InputManagerService.this.mKeyboardBacklightController.decrementKeyboardBacklight(i);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setTypeAssociation(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            com.android.server.input.InputManagerService.this.setTypeAssociationInternal(str, str2);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void unsetTypeAssociation(@android.annotation.NonNull java.lang.String str) {
            com.android.server.input.InputManagerService.this.unsetTypeAssociationInternal(str);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void addKeyboardLayoutAssociation(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
            com.android.server.input.InputManagerService.this.addKeyboardLayoutAssociation(str, str2, str3);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void removeKeyboardLayoutAssociation(@android.annotation.NonNull java.lang.String str) {
            com.android.server.input.InputManagerService.this.removeKeyboardLayoutAssociation(str);
        }

        @Override // com.android.server.input.InputManagerInternal
        public void setStylusButtonMotionEventsEnabled(boolean z) {
            com.android.server.input.InputManagerService.this.mNative.setStylusButtonMotionEventsEnabled(z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.input.InputShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AdditionalDisplayInputProperties {
        static final boolean DEFAULT_MOUSE_POINTER_ACCELERATION_ENABLED = true;
        static final boolean DEFAULT_POINTER_ICON_VISIBLE = true;
        public boolean mousePointerAccelerationEnabled;
        public boolean pointerIconVisible;

        AdditionalDisplayInputProperties() {
            reset();
        }

        public boolean allDefaults() {
            return this.mousePointerAccelerationEnabled && this.pointerIconVisible;
        }

        public void reset() {
            this.mousePointerAccelerationEnabled = true;
            this.pointerIconVisible = true;
        }
    }

    private void applyAdditionalDisplayInputProperties() {
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                com.android.server.input.InputManagerService.AdditionalDisplayInputProperties additionalDisplayInputProperties = this.mAdditionalDisplayInputProperties.get(this.mRequestedPointerDisplayId);
                if (additionalDisplayInputProperties == null) {
                    additionalDisplayInputProperties = DEFAULT_ADDITIONAL_DISPLAY_INPUT_PROPERTIES;
                }
                applyAdditionalDisplayInputPropertiesLocked(additionalDisplayInputProperties);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAdditionalDisplayInputPropertiesLock"})
    private void applyAdditionalDisplayInputPropertiesLocked(com.android.server.input.InputManagerService.AdditionalDisplayInputProperties additionalDisplayInputProperties) {
        if (additionalDisplayInputProperties.pointerIconVisible != this.mCurrentDisplayProperties.pointerIconVisible) {
            this.mCurrentDisplayProperties.pointerIconVisible = additionalDisplayInputProperties.pointerIconVisible;
            if (additionalDisplayInputProperties.pointerIconVisible) {
                if (this.mPointerIconType == -1) {
                    java.util.Objects.requireNonNull(this.mPointerIcon);
                    this.mNative.setCustomPointerIcon(this.mPointerIcon);
                } else {
                    this.mNative.setPointerIconType(this.mPointerIconType);
                }
            } else {
                this.mNative.setPointerIconType(0);
            }
        }
        if (additionalDisplayInputProperties.mousePointerAccelerationEnabled != this.mCurrentDisplayProperties.mousePointerAccelerationEnabled) {
            this.mCurrentDisplayProperties.mousePointerAccelerationEnabled = additionalDisplayInputProperties.mousePointerAccelerationEnabled;
        }
    }

    private void updateAdditionalDisplayInputProperties(int i, java.util.function.Consumer<com.android.server.input.InputManagerService.AdditionalDisplayInputProperties> consumer) {
        synchronized (this.mAdditionalDisplayInputPropertiesLock) {
            try {
                com.android.server.input.InputManagerService.AdditionalDisplayInputProperties additionalDisplayInputProperties = this.mAdditionalDisplayInputProperties.get(i);
                if (additionalDisplayInputProperties == null) {
                    additionalDisplayInputProperties = new com.android.server.input.InputManagerService.AdditionalDisplayInputProperties();
                    this.mAdditionalDisplayInputProperties.put(i, additionalDisplayInputProperties);
                }
                boolean z = additionalDisplayInputProperties.pointerIconVisible;
                boolean z2 = additionalDisplayInputProperties.mousePointerAccelerationEnabled;
                consumer.accept(additionalDisplayInputProperties);
                if (z != additionalDisplayInputProperties.pointerIconVisible) {
                    this.mNative.setPointerIconVisibility(i, additionalDisplayInputProperties.pointerIconVisible);
                }
                if (z2 != additionalDisplayInputProperties.mousePointerAccelerationEnabled) {
                    this.mNative.setMousePointerAccelerationEnabled(i, additionalDisplayInputProperties.mousePointerAccelerationEnabled);
                }
                if (additionalDisplayInputProperties.allDefaults()) {
                    this.mAdditionalDisplayInputProperties.remove(i);
                }
                if (i != this.mRequestedPointerDisplayId) {
                    android.util.Log.i(TAG, "Not applying additional properties for display " + i + " because the pointer is currently targeting display " + this.mRequestedPointerDisplayId + ".");
                    return;
                }
                applyAdditionalDisplayInputPropertiesLocked(additionalDisplayInputProperties);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void updatePointerLocationEnabled(boolean z) {
        this.mWindowManagerCallbacks.notifyPointerLocationChanged(z);
    }

    void updateShowKeyPresses(boolean z) {
        if (this.mShowKeyPresses == z) {
            return;
        }
        this.mShowKeyPresses = z;
        updateFocusEventDebugViewEnabled();
        synchronized (this.mFocusEventDebugViewLock) {
            try {
                if (this.mFocusEventDebugView != null) {
                    this.mFocusEventDebugView.updateShowKeyPresses(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void updateShowRotaryInput(boolean z) {
        if (this.mShowRotaryInput == z) {
            return;
        }
        this.mShowRotaryInput = z;
        updateFocusEventDebugViewEnabled();
        synchronized (this.mFocusEventDebugViewLock) {
            try {
                if (this.mFocusEventDebugView != null) {
                    this.mFocusEventDebugView.updateShowRotaryInput(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateFocusEventDebugViewEnabled() {
        com.android.server.input.debug.FocusEventDebugView focusEventDebugView;
        boolean z = this.mShowKeyPresses || this.mShowRotaryInput;
        synchronized (this.mFocusEventDebugViewLock) {
            try {
                if (z == (this.mFocusEventDebugView != null)) {
                    return;
                }
                if (z) {
                    this.mFocusEventDebugView = new com.android.server.input.debug.FocusEventDebugView(this.mContext, this);
                    focusEventDebugView = this.mFocusEventDebugView;
                } else {
                    focusEventDebugView = this.mFocusEventDebugView;
                    this.mFocusEventDebugView = null;
                }
                java.util.Objects.requireNonNull(focusEventDebugView);
                android.view.WindowManager windowManager = (android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class);
                java.util.Objects.requireNonNull(windowManager);
                android.view.WindowManager windowManager2 = windowManager;
                if (!z) {
                    windowManager2.removeView(focusEventDebugView);
                    return;
                }
                android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
                layoutParams.type = 2015;
                layoutParams.flags = com.android.internal.util.FrameworkStatsLog.TV_CAS_SESSION_OPEN_STATUS;
                layoutParams.privateFlags |= 16;
                layoutParams.setFitInsetsTypes(0);
                layoutParams.layoutInDisplayCutoutMode = 3;
                layoutParams.format = -3;
                layoutParams.setTitle("FocusEventDebugView - display " + this.mContext.getDisplayId());
                layoutParams.inputFeatures = 1 | layoutParams.inputFeatures;
                windowManager2.addView(focusEventDebugView, layoutParams);
            } finally {
            }
        }
    }

    public void setAccessibilityBounceKeysThreshold(int i) {
        this.mNative.setAccessibilityBounceKeysThreshold(i);
    }

    public void setAccessibilitySlowKeysThreshold(int i) {
        this.mNative.setAccessibilitySlowKeysThreshold(i);
    }

    public void setAccessibilityStickyKeysEnabled(boolean z) {
        this.mNative.setAccessibilityStickyKeysEnabled(z);
    }

    void setUseLargePointerIcons(boolean z) {
        this.mPointerIconCache.setUseLargePointerIcons(z);
    }

    interface KeyboardBacklightControllerInterface {
        default void incrementKeyboardBacklight(int i) {
        }

        default void decrementKeyboardBacklight(int i) {
        }

        default void registerKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener, int i) {
        }

        default void unregisterKeyboardBacklightListener(android.hardware.input.IKeyboardBacklightListener iKeyboardBacklightListener, int i) {
        }

        default void onInteractiveChanged(boolean z) {
        }

        default void notifyUserActivity() {
        }

        default void systemRunning() {
        }

        default void dump(java.io.PrintWriter printWriter) {
        }
    }
}
