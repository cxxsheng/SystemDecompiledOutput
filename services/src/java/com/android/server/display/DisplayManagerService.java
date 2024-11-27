package com.android.server.display;

/* loaded from: classes.dex */
public final class DisplayManagerService extends com.android.server.SystemService {
    static final long DISPLAY_MODE_RETURNS_PHYSICAL_REFRESH_RATE = 170503758;
    private static final java.lang.String FORCE_WIFI_DISPLAY_ENABLE = "persist.debug.wfd.enable";
    private static final int MSG_DELIVER_DISPLAY_EVENT = 3;
    private static final int MSG_DELIVER_DISPLAY_EVENT_FRAME_RATE_OVERRIDE = 7;
    private static final int MSG_DELIVER_DISPLAY_GROUP_EVENT = 8;
    private static final int MSG_LOAD_BRIGHTNESS_CONFIGURATIONS = 6;
    private static final int MSG_RECEIVED_DEVICE_STATE = 9;
    private static final int MSG_REGISTER_ADDITIONAL_DISPLAY_ADAPTERS = 2;
    private static final int MSG_REGISTER_DEFAULT_DISPLAY_ADAPTERS = 1;
    private static final int MSG_REQUEST_TRAVERSAL = 4;
    private static final int MSG_UPDATE_VIEWPORT = 5;
    private static final java.lang.String PROP_DEFAULT_DISPLAY_TOP_INSET = "persist.sys.displayinset.top";
    private static final float THRESHOLD_FOR_REFRESH_RATES_DIVISORS = 9.0E-4f;
    private static final long WAIT_FOR_DEFAULT_DISPLAY_TIMEOUT = 10000;
    private android.app.ActivityManager mActivityManager;
    private android.app.ActivityManagerInternal mActivityManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private boolean mAreUserDisabledHdrTypesAllowed;
    private boolean mBootCompleted;
    private final com.android.internal.display.BrightnessSynchronizer mBrightnessSynchronizer;
    private com.android.server.display.BrightnessTracker mBrightnessTracker;
    public final android.util.SparseArray<com.android.server.display.DisplayManagerService.CallbackRecord> mCallbacks;
    private final com.android.server.display.feature.DeviceConfigParameterProvider mConfigParameterProvider;
    private final android.content.Context mContext;
    private int mCurrentUserId;
    private final int mDefaultDisplayDefaultColorMode;
    private int mDefaultDisplayTopInset;
    private android.hardware.devicestate.DeviceStateManagerInternal mDeviceStateManager;
    private final android.util.SparseArray<android.util.IntArray> mDisplayAccessUIDs;
    private final java.util.ArrayList<com.android.server.display.DisplayAdapter> mDisplayAdapters;
    private final com.android.server.display.DisplayBlanker mDisplayBlanker;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private final android.util.SparseArray<com.android.server.display.DisplayManagerService.BrightnessPair> mDisplayBrightnesses;
    private final com.android.server.display.DisplayDeviceRepository mDisplayDeviceRepo;
    private final java.util.concurrent.CopyOnWriteArrayList<android.hardware.display.DisplayManagerInternal.DisplayGroupListener> mDisplayGroupListeners;
    private final com.android.server.display.mode.DisplayModeDirector mDisplayModeDirector;
    private final com.android.server.display.notifications.DisplayNotificationManager mDisplayNotificationManager;
    private android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
    private final android.util.SparseArray<com.android.server.display.DisplayPowerControllerInterface> mDisplayPowerControllers;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private final android.util.SparseIntArray mDisplayStates;
    private final java.util.concurrent.CopyOnWriteArrayList<android.hardware.display.DisplayManagerInternal.DisplayTransactionListener> mDisplayTransactionListeners;
    final android.util.SparseArray<android.util.Pair<android.companion.virtual.IVirtualDevice, android.window.DisplayWindowPolicyController>> mDisplayWindowPolicyControllers;
    private final com.android.server.display.ExternalDisplayPolicy mExternalDisplayPolicy;
    private final com.android.server.display.ExternalDisplayStatsService mExternalDisplayStatsService;
    private final boolean mExtraDisplayEventLogging;
    private final java.lang.String mExtraDisplayLoggingPackageName;
    private final com.android.server.display.feature.DisplayManagerFlags mFlags;
    private final com.android.server.display.DisplayManagerService.DisplayManagerHandler mHandler;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private android.hardware.display.HdrConversionMode mHdrConversionMode;
    private final com.android.server.display.HighBrightnessModeMetadataMapper mHighBrightnessModeMetadataMapper;
    private final android.content.BroadcastReceiver mIdleModeReceiver;
    private final com.android.server.display.DisplayManagerService.Injector mInjector;
    private com.android.server.input.InputManagerInternal mInputManagerInternal;
    private boolean mIsDocked;
    private boolean mIsDreaming;
    private volatile boolean mIsHdrOutputControlEnabled;
    private final com.android.server.display.LogicalDisplayMapper mLogicalDisplayMapper;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private boolean mMinimalPostProcessingAllowed;
    private final android.hardware.display.Curve mMinimumBrightnessCurve;
    private final android.util.Spline mMinimumBrightnessSpline;
    private final android.hardware.OverlayProperties mOverlayProperties;
    private android.hardware.display.HdrConversionMode mOverrideHdrConversionMode;

    @com.android.internal.annotations.GuardedBy({"mPendingCallbackSelfLocked"})
    public final android.util.SparseArray<com.android.server.display.DisplayManagerService.PendingCallback> mPendingCallbackSelfLocked;
    private boolean mPendingTraversal;
    private final com.android.server.display.PersistentDataStore mPersistentDataStore;
    private android.os.Handler mPowerHandler;

    @android.annotation.Nullable
    private android.media.projection.IMediaProjectionManager mProjectionService;
    public boolean mSafeMode;
    private android.hardware.SensorManager mSensorManager;
    private com.android.server.display.DisplayManagerService.SettingsObserver mSettingsObserver;
    private com.android.server.display.SmallAreaDetectionController mSmallAreaDetectionController;
    private android.graphics.Point mStableDisplaySize;
    private int[] mSupportedHdrOutputType;
    private final com.android.server.display.DisplayManagerService.SyncRoot mSyncRoot;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private int mSystemPreferredHdrOutputType;
    private boolean mSystemReady;
    private final java.util.ArrayList<com.android.server.display.DisplayManagerService.CallbackRecord> mTempCallbacks;
    private final java.util.ArrayList<android.hardware.display.DisplayViewport> mTempViewports;
    private final android.os.Handler mUiHandler;
    private com.android.server.display.DisplayManagerService.UidImportanceListener mUidImportanceListener;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private int[] mUserDisabledHdrTypes;
    private android.view.Display.Mode mUserPreferredMode;

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private final java.util.ArrayList<android.hardware.display.DisplayViewport> mViewports;
    private com.android.server.display.VirtualDisplayAdapter mVirtualDisplayAdapter;
    private final android.graphics.ColorSpace mWideColorSpace;
    private com.android.server.display.WifiDisplayAdapter mWifiDisplayAdapter;
    private int mWifiDisplayScanRequestCount;
    private com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private static final java.lang.String TAG = "DisplayManagerService";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private static final int[] EMPTY_ARRAY = new int[0];
    private static final android.hardware.display.HdrConversionMode HDR_CONVERSION_MODE_UNSUPPORTED = new android.hardware.display.HdrConversionMode(0);

    @com.android.internal.annotations.VisibleForTesting
    public interface Clock {
        long uptimeMillis();
    }

    public static final class SyncRoot {
    }

    public DisplayManagerService(android.content.Context context) {
        this(context, new com.android.server.display.DisplayManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    DisplayManagerService(android.content.Context context, com.android.server.display.DisplayManagerService.Injector injector) {
        super(context);
        this.mUidImportanceListener = new com.android.server.display.DisplayManagerService.UidImportanceListener();
        this.mUserDisabledHdrTypes = new int[0];
        this.mAreUserDisabledHdrTypesAllowed = true;
        this.mHdrConversionMode = null;
        this.mOverrideHdrConversionMode = null;
        this.mSystemPreferredHdrOutputType = -1;
        this.mSyncRoot = new com.android.server.display.DisplayManagerService.SyncRoot();
        this.mCallbacks = new android.util.SparseArray<>();
        this.mDisplayWindowPolicyControllers = new android.util.SparseArray<>();
        this.mHighBrightnessModeMetadataMapper = new com.android.server.display.HighBrightnessModeMetadataMapper();
        this.mDisplayAdapters = new java.util.ArrayList<>();
        this.mDisplayTransactionListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mDisplayGroupListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mDisplayPowerControllers = new android.util.SparseArray<>();
        this.mDisplayBlanker = new com.android.server.display.DisplayBlanker() { // from class: com.android.server.display.DisplayManagerService.1
            @Override // com.android.server.display.DisplayBlanker
            public synchronized void requestDisplayState(int i, int i2, float f, float f2) {
                boolean z;
                boolean z2;
                boolean z3;
                try {
                    synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                        try {
                            int indexOfKey = com.android.server.display.DisplayManagerService.this.mDisplayStates.indexOfKey(i);
                            z = false;
                            if (indexOfKey > -1) {
                                boolean z4 = i2 != com.android.server.display.DisplayManagerService.this.mDisplayStates.valueAt(indexOfKey);
                                if (!z4) {
                                    z2 = true;
                                    z3 = true;
                                } else {
                                    int size = com.android.server.display.DisplayManagerService.this.mDisplayStates.size();
                                    int i3 = 0;
                                    z2 = true;
                                    z3 = true;
                                    while (i3 < size) {
                                        int valueAt = i3 == indexOfKey ? i2 : com.android.server.display.DisplayManagerService.this.mDisplayStates.valueAt(i3);
                                        if (valueAt != 1) {
                                            z2 = false;
                                        }
                                        if (android.view.Display.isActiveState(valueAt)) {
                                            z3 = false;
                                        }
                                        if (!z2 && !z3) {
                                            break;
                                        } else {
                                            i3++;
                                        }
                                    }
                                }
                                z = z4;
                            } else {
                                z2 = true;
                                z3 = true;
                            }
                        } finally {
                        }
                    }
                    if (i2 == 1) {
                        com.android.server.display.DisplayManagerService.this.requestDisplayStateInternal(i, i2, f, f2);
                    }
                    if (z) {
                        com.android.server.display.DisplayManagerService.this.mDisplayPowerCallbacks.onDisplayStateChange(z3, z2);
                    }
                    if (i2 != 1) {
                        com.android.server.display.DisplayManagerService.this.requestDisplayStateInternal(i, i2, f, f2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        };
        this.mDisplayStates = new android.util.SparseIntArray();
        this.mDisplayBrightnesses = new android.util.SparseArray<>();
        this.mStableDisplaySize = new android.graphics.Point();
        this.mViewports = new java.util.ArrayList<>();
        this.mPersistentDataStore = new com.android.server.display.PersistentDataStore();
        this.mTempCallbacks = new java.util.ArrayList<>();
        this.mPendingCallbackSelfLocked = new android.util.SparseArray<>();
        this.mTempViewports = new java.util.ArrayList<>();
        this.mDisplayAccessUIDs = new android.util.SparseArray<>();
        this.mBootCompleted = false;
        this.mIdleModeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.display.DisplayManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                    com.android.server.display.DisplayManagerService.this.mIsDocked = intExtra == 1 || intExtra == 3 || intExtra == 4;
                }
                if ("android.intent.action.DREAMING_STARTED".equals(intent.getAction())) {
                    com.android.server.display.DisplayManagerService.this.mIsDreaming = true;
                } else if ("android.intent.action.DREAMING_STOPPED".equals(intent.getAction())) {
                    com.android.server.display.DisplayManagerService.this.mIsDreaming = false;
                }
                com.android.server.display.DisplayManagerService.this.setDockedAndIdleEnabled(com.android.server.display.DisplayManagerService.this.mIsDocked && com.android.server.display.DisplayManagerService.this.mIsDreaming, 0);
            }
        };
        com.android.server.utils.FoldSettingProvider foldSettingProvider = new com.android.server.utils.FoldSettingProvider(context, new com.android.internal.util.SettingsWrapper(), new com.android.internal.foldables.FoldLockSettingAvailabilityProvider(context.getResources()));
        this.mInjector = injector;
        this.mContext = context;
        this.mFlags = injector.getFlags();
        this.mHandler = new com.android.server.display.DisplayManagerService.DisplayManagerHandler(com.android.server.DisplayThread.get().getLooper());
        this.mUiHandler = com.android.server.UiThread.getHandler();
        this.mDisplayDeviceRepo = new com.android.server.display.DisplayDeviceRepository(this.mSyncRoot, this.mPersistentDataStore);
        this.mLogicalDisplayMapper = new com.android.server.display.LogicalDisplayMapper(this.mContext, foldSettingProvider, this.mDisplayDeviceRepo, new com.android.server.display.DisplayManagerService.LogicalDisplayListener(), this.mSyncRoot, this.mHandler, this.mFlags);
        this.mDisplayModeDirector = new com.android.server.display.mode.DisplayModeDirector(context, this.mHandler, this.mFlags);
        this.mBrightnessSynchronizer = new com.android.internal.display.BrightnessSynchronizer(this.mContext, this.mFlags.isBrightnessIntRangeUserPerceptionEnabled());
        android.content.res.Resources resources = this.mContext.getResources();
        this.mDefaultDisplayDefaultColorMode = this.mContext.getResources().getInteger(android.R.integer.config_defaultBinderHeavyHitterWatcherBatchSize);
        this.mDefaultDisplayTopInset = android.os.SystemProperties.getInt(PROP_DEFAULT_DISPLAY_TOP_INSET, -1);
        float[] floatArray = getFloatArray(resources.obtainTypedArray(android.R.array.config_mainBuiltInDisplayWaterfallCutout));
        float[] floatArray2 = getFloatArray(resources.obtainTypedArray(android.R.array.config_mappedColorModes));
        this.mMinimumBrightnessCurve = new android.hardware.display.Curve(floatArray, floatArray2);
        this.mMinimumBrightnessSpline = android.util.Spline.createSpline(floatArray, floatArray2);
        this.mCurrentUserId = 0;
        this.mWideColorSpace = android.view.SurfaceControl.getCompositionColorSpaces()[1];
        this.mOverlayProperties = android.view.SurfaceControl.getOverlaySupport();
        this.mSystemReady = false;
        this.mConfigParameterProvider = new com.android.server.display.feature.DeviceConfigParameterProvider(android.provider.DeviceConfigInterface.REAL);
        this.mExtraDisplayLoggingPackageName = (java.lang.String) android.sysprop.DisplayProperties.debug_vri_package().orElse(null);
        this.mExtraDisplayEventLogging = !android.text.TextUtils.isEmpty(this.mExtraDisplayLoggingPackageName);
        this.mExternalDisplayStatsService = new com.android.server.display.ExternalDisplayStatsService(this.mContext, this.mHandler);
        this.mDisplayNotificationManager = new com.android.server.display.notifications.DisplayNotificationManager(this.mFlags, this.mContext, this.mExternalDisplayStatsService);
        this.mExternalDisplayPolicy = new com.android.server.display.ExternalDisplayPolicy(new com.android.server.display.DisplayManagerService.ExternalDisplayPolicyInjector());
    }

    public void setupSchedulerPolicies() {
        android.os.Process.setThreadGroupAndCpuset(com.android.server.DisplayThread.get().getThreadId(), 5);
        android.os.Process.setThreadGroupAndCpuset(com.android.server.AnimationThread.get().getThreadId(), 5);
        android.os.Process.setThreadGroupAndCpuset(com.android.server.wm.SurfaceAnimationThread.get().getThreadId(), 5);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        synchronized (this.mSyncRoot) {
            this.mPersistentDataStore.loadIfNeeded();
            loadStableDisplayValuesLocked();
        }
        this.mHandler.sendEmptyMessage(1);
        android.hardware.display.DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
        publishBinderService("display", new com.android.server.display.DisplayManagerService.BinderService(), true, 1);
        publishLocalService(android.hardware.display.DisplayManagerInternal.class, new com.android.server.display.DisplayManagerService.LocalService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 100) {
            synchronized (this.mSyncRoot) {
                try {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis() + this.mInjector.getDefaultDisplayDelayTimeout();
                    while (true) {
                        if (this.mLogicalDisplayMapper.getDisplayLocked(0) == null || this.mVirtualDisplayAdapter == null) {
                            long uptimeMillis2 = uptimeMillis - android.os.SystemClock.uptimeMillis();
                            if (uptimeMillis2 <= 0) {
                                throw new java.lang.RuntimeException("Timeout waiting for default display to be initialized. DefaultDisplay=" + this.mLogicalDisplayMapper.getDisplayLocked(0) + ", mVirtualDisplayAdapter=" + this.mVirtualDisplayAdapter);
                            }
                            if (DEBUG) {
                                android.util.Slog.d(TAG, "waitForDefaultDisplay: waiting, timeout=" + uptimeMillis2);
                            }
                            try {
                                this.mSyncRoot.wait(uptimeMillis2);
                            } catch (java.lang.InterruptedException e) {
                            }
                        }
                    }
                } finally {
                }
            }
            return;
        }
        if (i == 1000) {
            synchronized (this.mSyncRoot) {
                try {
                    this.mBootCompleted = true;
                    for (int i2 = 0; i2 < this.mDisplayPowerControllers.size(); i2++) {
                        this.mDisplayPowerControllers.valueAt(i2).onBootCompleted();
                    }
                } finally {
                }
            }
            this.mDisplayModeDirector.onBootCompleted();
            this.mLogicalDisplayMapper.onBootCompleted();
            this.mDisplayNotificationManager.onBootCompleted();
            this.mExternalDisplayPolicy.onBootCompleted();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        final int userIdentifier = targetUser2.getUserIdentifier();
        final int userSerialNumber = getUserManager().getUserSerialNumber(userIdentifier);
        synchronized (this.mSyncRoot) {
            try {
                final boolean z = this.mCurrentUserId != userIdentifier;
                if (z) {
                    this.mCurrentUserId = userIdentifier;
                }
                this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda15
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.display.DisplayManagerService.this.lambda$onUserSwitching$0(z, userSerialNumber, userIdentifier, (com.android.server.display.LogicalDisplay) obj);
                    }
                });
                handleSettingsChange();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitching$0(boolean z, int i, int i2, com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface;
        if (logicalDisplay.getDisplayInfoLocked().type != 1 || (displayPowerControllerInterface = this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked())) == null) {
            return;
        }
        if (z) {
            displayPowerControllerInterface.setBrightnessConfiguration(getBrightnessConfigForDisplayWithPdsFallbackLocked(logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(), i), true);
        }
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        float brightness = primaryDisplayDeviceLocked == null ? Float.NaN : this.mPersistentDataStore.getBrightness(primaryDisplayDeviceLocked, i);
        if (java.lang.Float.isNaN(brightness)) {
            brightness = logicalDisplay.getDisplayInfoLocked().brightnessDefault;
        }
        displayPowerControllerInterface.onSwitchUser(i2, i, brightness);
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void windowManagerAndInputReady() {
        synchronized (this.mSyncRoot) {
            this.mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
            this.mInputManagerInternal = (com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class);
            this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            this.mActivityManager = (android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class);
            this.mActivityManager.addOnUidImportanceListener(this.mUidImportanceListener, 400);
            this.mDeviceStateManager = (android.hardware.devicestate.DeviceStateManagerInternal) com.android.server.LocalServices.getService(android.hardware.devicestate.DeviceStateManagerInternal.class);
            ((android.hardware.devicestate.DeviceStateManager) this.mContext.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).registerCallback(new android.os.HandlerExecutor(this.mHandler), new com.android.server.display.DisplayManagerService.DeviceStateListener());
            scheduleTraversalLocked(false);
        }
    }

    public void systemReady(boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                this.mSafeMode = z;
                this.mSystemReady = true;
                this.mIsHdrOutputControlEnabled = this.mConfigParameterProvider.isHdrOutputControlFeatureEnabled();
                this.mConfigParameterProvider.addOnPropertiesChangedListener(com.android.internal.os.BackgroundThread.getExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda9
                    public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                        com.android.server.display.DisplayManagerService.this.lambda$systemReady$1(properties);
                    }
                });
                recordTopInsetLocked(this.mLogicalDisplayMapper.getDisplayLocked(0));
                updateSettingsLocked();
                updateUserDisabledHdrTypesFromSettingsLocked();
                updateUserPreferredDisplayModeSettingsLocked();
                if (this.mIsHdrOutputControlEnabled) {
                    updateHdrConversionModeSettingsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mDisplayModeDirector.setDesiredDisplayModeSpecsListener(new com.android.server.display.DisplayManagerService.DesiredDisplayModeSpecsObserver());
        this.mDisplayModeDirector.start(this.mSensorManager);
        this.mHandler.sendEmptyMessage(2);
        this.mSettingsObserver = new com.android.server.display.DisplayManagerService.SettingsObserver();
        this.mBrightnessSynchronizer.startSynchronizing();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        this.mContext.registerReceiver(this.mIdleModeReceiver, intentFilter);
        this.mSmallAreaDetectionController = this.mFlags.isSmallAreaDetectionEnabled() ? com.android.server.display.SmallAreaDetectionController.create(this.mContext) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$1(android.provider.DeviceConfig.Properties properties) {
        this.mIsHdrOutputControlEnabled = this.mConfigParameterProvider.isHdrOutputControlFeatureEnabled();
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getDisplayHandler() {
        return this.mHandler;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.DisplayDeviceRepository getDisplayDeviceRepository() {
        return this.mDisplayDeviceRepo;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.LogicalDisplayMapper getLogicalDisplayMapper() {
        return this.mLogicalDisplayMapper;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isMinimalPostProcessingAllowed() {
        boolean z;
        synchronized (this.mSyncRoot) {
            z = this.mMinimalPostProcessingAllowed;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setMinimalPostProcessingAllowed(boolean z) {
        synchronized (this.mSyncRoot) {
            this.mMinimalPostProcessingAllowed = z;
        }
    }

    com.android.server.display.notifications.DisplayNotificationManager getDisplayNotificationManager() {
        return this.mDisplayNotificationManager;
    }

    private void loadStableDisplayValuesLocked() {
        android.graphics.Point stableDisplaySize = this.mPersistentDataStore.getStableDisplaySize();
        if (stableDisplaySize.x > 0 && stableDisplaySize.y > 0) {
            this.mStableDisplaySize.set(stableDisplaySize.x, stableDisplaySize.y);
            return;
        }
        android.content.res.Resources resources = this.mContext.getResources();
        int integer = resources.getInteger(android.R.integer.config_sidefpsKeyguardPowerPressWindow);
        int integer2 = resources.getInteger(android.R.integer.config_sidefpsBpPowerPressWindow);
        if (integer > 0 && integer2 > 0) {
            setStableDisplaySizeLocked(integer, integer2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.graphics.Point getStableDisplaySizeInternal() {
        android.graphics.Point point = new android.graphics.Point();
        synchronized (this.mSyncRoot) {
            try {
                if (this.mStableDisplaySize.x > 0 && this.mStableDisplaySize.y > 0) {
                    point.set(this.mStableDisplaySize.x, this.mStableDisplaySize.y);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return point;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDisplayTransactionListenerInternal(android.hardware.display.DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
        this.mDisplayTransactionListeners.add(displayTransactionListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterDisplayTransactionListenerInternal(android.hardware.display.DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
        this.mDisplayTransactionListeners.remove(displayTransactionListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDisplayInfoOverrideFromWindowManagerInternal(int i, android.view.DisplayInfo displayInfo) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked != null && displayLocked.setDisplayInfoOverrideFromWindowManagerLocked(displayInfo)) {
                    handleLogicalDisplayChangedLocked(displayLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getNonOverrideDisplayInfoInternal(int i, android.view.DisplayInfo displayInfo) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked != null) {
                    displayLocked.getNonOverrideDisplayInfoLocked(displayInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void performTraversalInternal(android.view.SurfaceControl.Transaction transaction, android.util.SparseArray<android.view.SurfaceControl.Transaction> sparseArray) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mPendingTraversal) {
                    this.mPendingTraversal = false;
                    performTraversalLocked(transaction, sparseArray);
                    java.util.Iterator<android.hardware.display.DisplayManagerInternal.DisplayTransactionListener> it = this.mDisplayTransactionListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onDisplayTransaction(transaction);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private float clampBrightness(int i, float f) {
        if (i == 1) {
            return -1.0f;
        }
        if (f != -1.0f && f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return Float.NaN;
        }
        if (f > 1.0f) {
            return 1.0f;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestDisplayStateInternal(int i, int i2, float f, float f2) {
        if (i2 == 0) {
            i2 = 2;
        }
        float clampBrightness = clampBrightness(i2, f);
        float clampBrightness2 = clampBrightness(i2, f2);
        synchronized (this.mSyncRoot) {
            try {
                int indexOfKey = this.mDisplayStates.indexOfKey(i);
                com.android.server.display.DisplayManagerService.BrightnessPair valueAt = indexOfKey < 0 ? null : this.mDisplayBrightnesses.valueAt(indexOfKey);
                if (indexOfKey < 0 || (this.mDisplayStates.valueAt(indexOfKey) == i2 && valueAt.brightness == clampBrightness && valueAt.sdrBrightness == clampBrightness2)) {
                    return;
                }
                if (android.os.Trace.isTagEnabled(131072L)) {
                    android.os.Trace.asyncTraceForTrackBegin(131072L, "requestDisplayStateInternal:" + i, android.view.Display.stateToString(i2) + ", brightness=" + clampBrightness + ", sdrBrightness=" + clampBrightness2, i);
                }
                this.mDisplayStates.setValueAt(indexOfKey, i2);
                valueAt.brightness = clampBrightness;
                valueAt.sdrBrightness = clampBrightness2;
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked.isEnabledLocked() || i2 == 1) {
                    java.lang.Runnable updateDisplayStateLocked = updateDisplayStateLocked(displayLocked.getPrimaryDisplayDeviceLocked());
                    if (android.os.Trace.isTagEnabled(131072L)) {
                        android.os.Trace.asyncTraceForTrackEnd(131072L, "requestDisplayStateInternal:" + i, i);
                    }
                    if (updateDisplayStateLocked != null) {
                        updateDisplayStateLocked.run();
                    }
                }
            } finally {
            }
        }
    }

    private class UidImportanceListener implements android.app.ActivityManager.OnUidImportanceListener {
        private UidImportanceListener() {
        }

        public void onUidImportance(int i, int i2) {
            synchronized (com.android.server.display.DisplayManagerService.this.mPendingCallbackSelfLocked) {
                try {
                    if (i2 >= 1000) {
                        android.util.Slog.d(com.android.server.display.DisplayManagerService.TAG, "Drop pending events for gone uid " + i);
                        com.android.server.display.DisplayManagerService.this.mPendingCallbackSelfLocked.delete(i);
                        return;
                    }
                    if (i2 >= 400) {
                        return;
                    }
                    com.android.server.display.DisplayManagerService.PendingCallback pendingCallback = com.android.server.display.DisplayManagerService.this.mPendingCallbackSelfLocked.get(i);
                    if (pendingCallback == null) {
                        return;
                    }
                    if (com.android.server.display.DisplayManagerService.DEBUG) {
                        android.util.Slog.d(com.android.server.display.DisplayManagerService.TAG, "Uid " + i + " becomes " + i2);
                    }
                    pendingCallback.sendPendingDisplayEvent();
                    com.android.server.display.DisplayManagerService.this.mPendingCallbackSelfLocked.delete(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver() {
            super(com.android.server.display.DisplayManagerService.this.mHandler);
            com.android.server.display.DisplayManagerService.this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("minimal_post_processing_allowed"), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            com.android.server.display.DisplayManagerService.this.handleSettingsChange();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSettingsChange() {
        synchronized (this.mSyncRoot) {
            updateSettingsLocked();
            scheduleTraversalLocked(false);
        }
    }

    private void updateSettingsLocked() {
        setMinimalPostProcessingAllowed(android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "minimal_post_processing_allowed", 1, -2) != 0);
    }

    private void updateUserDisabledHdrTypesFromSettingsLocked() {
        this.mAreUserDisabledHdrTypesAllowed = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "are_user_disabled_hdr_formats_allowed", 1) != 0;
        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "user_disabled_hdr_formats");
        if (string != null) {
            try {
                java.lang.String[] split = android.text.TextUtils.split(string, ",");
                this.mUserDisabledHdrTypes = new int[split.length];
                for (int i = 0; i < split.length; i++) {
                    this.mUserDisabledHdrTypes[i] = java.lang.Integer.parseInt(split[i]);
                }
                if (!this.mAreUserDisabledHdrTypesAllowed) {
                    this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.display.DisplayManagerService.this.lambda$updateUserDisabledHdrTypesFromSettingsLocked$2((com.android.server.display.LogicalDisplay) obj);
                        }
                    });
                    return;
                }
                return;
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Failed to parse USER_DISABLED_HDR_FORMATS. Clearing the setting.", e);
                clearUserDisabledHdrTypesLocked();
                return;
            }
        }
        clearUserDisabledHdrTypesLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUserDisabledHdrTypesFromSettingsLocked$2(com.android.server.display.LogicalDisplay logicalDisplay) {
        logicalDisplay.setUserDisabledHdrTypes(this.mUserDisabledHdrTypes);
        handleLogicalDisplayChangedLocked(logicalDisplay);
    }

    private void clearUserDisabledHdrTypesLocked() {
        synchronized (this.mSyncRoot) {
            this.mUserDisabledHdrTypes = new int[0];
            android.provider.Settings.Global.putString(this.mContext.getContentResolver(), "user_disabled_hdr_formats", "");
        }
    }

    private void updateUserPreferredDisplayModeSettingsLocked() {
        final android.view.Display.Mode mode = new android.view.Display.Mode(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "user_preferred_resolution_width", -1), android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "user_preferred_resolution_height", -1), android.provider.Settings.Global.getFloat(this.mContext.getContentResolver(), "user_preferred_refresh_rate", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE));
        this.mUserPreferredMode = isResolutionAndRefreshRateValid(mode) ? mode : null;
        if (this.mUserPreferredMode != null) {
            this.mDisplayDeviceRepo.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.display.DisplayDevice) obj).setUserPreferredDisplayModeLocked(mode);
                }
            });
        } else {
            this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.display.DisplayManagerService.this.lambda$updateUserPreferredDisplayModeSettingsLocked$4((com.android.server.display.LogicalDisplay) obj);
                }
            });
        }
    }

    private android.view.DisplayInfo getDisplayInfoForFrameRateOverride(android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr, android.view.DisplayInfo displayInfo, int i) {
        float f = displayInfo.renderFrameRate;
        int length = frameRateOverrideArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            android.view.DisplayEventReceiver.FrameRateOverride frameRateOverride = frameRateOverrideArr[i2];
            if (frameRateOverride.uid != i) {
                i2++;
            } else {
                f = frameRateOverride.frameRateHz;
                break;
            }
        }
        if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return displayInfo;
        }
        boolean z = i < 10000 || android.app.compat.CompatChanges.isChangeEnabled(DISPLAY_MODE_RETURNS_PHYSICAL_REFRESH_RATE, i);
        android.view.Display.Mode mode = displayInfo.getMode();
        float refreshRate = mode.getRefreshRate() / f;
        float round = java.lang.Math.round(refreshRate);
        if (java.lang.Math.abs(refreshRate - round) > THRESHOLD_FOR_REFRESH_RATES_DIVISORS) {
            return displayInfo;
        }
        float refreshRate2 = mode.getRefreshRate() / round;
        android.view.DisplayInfo displayInfo2 = new android.view.DisplayInfo();
        displayInfo2.copyFrom(displayInfo);
        for (android.view.Display.Mode mode2 : displayInfo.supportedModes) {
            if (mode2.equalsExceptRefreshRate(mode) && mode2.getRefreshRate() >= refreshRate2 - THRESHOLD_FOR_REFRESH_RATES_DIVISORS && mode2.getRefreshRate() <= refreshRate2 + THRESHOLD_FOR_REFRESH_RATES_DIVISORS) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "found matching modeId " + mode2.getModeId());
                }
                displayInfo2.refreshRateOverride = mode2.getRefreshRate();
                if (!z) {
                    displayInfo2.modeId = mode2.getModeId();
                }
                return displayInfo2;
            }
        }
        displayInfo2.refreshRateOverride = refreshRate2;
        if (!z) {
            displayInfo2.supportedModes = (android.view.Display.Mode[]) java.util.Arrays.copyOf(displayInfo.supportedModes, displayInfo.supportedModes.length + 1);
            displayInfo2.supportedModes[displayInfo2.supportedModes.length - 1] = new android.view.Display.Mode(255, mode.getPhysicalWidth(), mode.getPhysicalHeight(), displayInfo2.refreshRateOverride, mode.getVsyncRate(), new float[0], mode.getSupportedHdrTypes());
            displayInfo2.modeId = displayInfo2.supportedModes[displayInfo2.supportedModes.length - 1].getModeId();
        }
        return displayInfo2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.DisplayInfo getDisplayInfoInternal(int i, int i2) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked != null) {
                    android.view.DisplayInfo displayInfoForFrameRateOverride = getDisplayInfoForFrameRateOverride(displayLocked.getFrameRateOverrides(), displayLocked.getDisplayInfoLocked(), i2);
                    if (!displayInfoForFrameRateOverride.hasAccess(i2)) {
                        if (isUidPresentOnDisplayInternal(i2, i)) {
                        }
                    }
                    return displayInfoForFrameRateOverride;
                }
                return null;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerCallbackInternal(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback, int i, int i2, long j) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord = this.mCallbacks.get(i);
                if (callbackRecord != null) {
                    callbackRecord.updateEventsMask(j);
                    return;
                }
                com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord2 = new com.android.server.display.DisplayManagerService.CallbackRecord(i, i2, iDisplayManagerCallback, j);
                try {
                    iDisplayManagerCallback.asBinder().linkToDeath(callbackRecord2, 0);
                    this.mCallbacks.put(i, callbackRecord2);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallbackDied(com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord) {
        synchronized (this.mSyncRoot) {
            this.mCallbacks.remove(callbackRecord.mPid);
            stopWifiDisplayScanLocked(callbackRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWifiDisplayScanInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord = this.mCallbacks.get(i);
                if (callbackRecord == null) {
                    throw new java.lang.IllegalStateException("The calling process has not registered an IDisplayManagerCallback.");
                }
                startWifiDisplayScanLocked(callbackRecord);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void startWifiDisplayScanLocked(com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord) {
        if (!callbackRecord.mWifiDisplayScanRequested) {
            callbackRecord.mWifiDisplayScanRequested = true;
            int i = this.mWifiDisplayScanRequestCount;
            this.mWifiDisplayScanRequestCount = i + 1;
            if (i == 0 && this.mWifiDisplayAdapter != null) {
                this.mWifiDisplayAdapter.requestStartScanLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopWifiDisplayScanInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord = this.mCallbacks.get(i);
                if (callbackRecord == null) {
                    throw new java.lang.IllegalStateException("The calling process has not registered an IDisplayManagerCallback.");
                }
                stopWifiDisplayScanLocked(callbackRecord);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void stopWifiDisplayScanLocked(com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord) {
        if (callbackRecord.mWifiDisplayScanRequested) {
            callbackRecord.mWifiDisplayScanRequested = false;
            int i = this.mWifiDisplayScanRequestCount - 1;
            this.mWifiDisplayScanRequestCount = i;
            if (i == 0) {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestStopScanLocked();
                }
            } else if (this.mWifiDisplayScanRequestCount < 0) {
                android.util.Slog.wtf(TAG, "mWifiDisplayScanRequestCount became negative: " + this.mWifiDisplayScanRequestCount);
                this.mWifiDisplayScanRequestCount = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectWifiDisplayInternal(java.lang.String str) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestConnectLocked(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pauseWifiDisplayInternal() {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestPauseLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeWifiDisplayInternal() {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestResumeLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectWifiDisplayInternal() {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestDisconnectLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void renameWifiDisplayInternal(java.lang.String str, java.lang.String str2) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestRenameLocked(str, str2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forgetWifiDisplayInternal(java.lang.String str) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    this.mWifiDisplayAdapter.requestForgetLocked(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.display.WifiDisplayStatus getWifiDisplayStatusInternal() {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mWifiDisplayAdapter != null) {
                    return this.mWifiDisplayAdapter.getWifiDisplayStatusLocked();
                }
                return new android.hardware.display.WifiDisplayStatus();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUserDisabledHdrTypesInternal(final int[] iArr) {
        synchronized (this.mSyncRoot) {
            try {
                if (iArr == null) {
                    android.util.Slog.e(TAG, "Null is not an expected argument to setUserDisabledHdrTypesInternal");
                    return;
                }
                if (!isSubsetOf(android.view.Display.HdrCapabilities.HDR_TYPES, iArr)) {
                    android.util.Slog.e(TAG, "userDisabledHdrTypes contains unexpected types");
                    return;
                }
                java.util.Arrays.sort(iArr);
                if (java.util.Arrays.equals(this.mUserDisabledHdrTypes, iArr)) {
                    return;
                }
                java.lang.String str = "";
                if (iArr.length != 0) {
                    str = android.text.TextUtils.join(",", java.util.Arrays.stream(iArr).boxed().toArray());
                }
                android.provider.Settings.Global.putString(this.mContext.getContentResolver(), "user_disabled_hdr_formats", str);
                this.mUserDisabledHdrTypes = iArr;
                if (!this.mAreUserDisabledHdrTypesAllowed) {
                    this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.display.DisplayManagerService.this.lambda$setUserDisabledHdrTypesInternal$5(iArr, (com.android.server.display.LogicalDisplay) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUserDisabledHdrTypesInternal$5(int[] iArr, com.android.server.display.LogicalDisplay logicalDisplay) {
        logicalDisplay.setUserDisabledHdrTypes(iArr);
        handleLogicalDisplayChangedLocked(logicalDisplay);
    }

    private boolean isSubsetOf(int[] iArr, int[] iArr2) {
        for (int i : iArr2) {
            if (java.util.Arrays.binarySearch(iArr, i) < 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAreUserDisabledHdrTypesAllowedInternal(boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mAreUserDisabledHdrTypesAllowed == z) {
                    return;
                }
                this.mAreUserDisabledHdrTypesAllowed = z;
                if (this.mUserDisabledHdrTypes.length == 0) {
                    return;
                }
                android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "are_user_disabled_hdr_formats_allowed", z ? 1 : 0);
                final int[] iArr = new int[0];
                if (!this.mAreUserDisabledHdrTypesAllowed) {
                    iArr = this.mUserDisabledHdrTypes;
                }
                this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.display.DisplayManagerService.this.lambda$setAreUserDisabledHdrTypesAllowedInternal$6(iArr, (com.android.server.display.LogicalDisplay) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAreUserDisabledHdrTypesAllowedInternal$6(int[] iArr, com.android.server.display.LogicalDisplay logicalDisplay) {
        logicalDisplay.setUserDisabledHdrTypes(iArr);
        handleLogicalDisplayChangedLocked(logicalDisplay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestColorModeInternal(int i, int i2) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked != null && displayLocked.getRequestedColorModeLocked() != i2) {
                    displayLocked.setRequestedColorModeLocked(i2);
                    scheduleTraversalLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean validatePackageName(int i, java.lang.String str) {
        java.lang.String[] packagesForUid;
        if (i == 0) {
            return true;
        }
        if (str != null && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null) {
            for (java.lang.String str2 : packagesForUid) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canProjectVideo(android.media.projection.IMediaProjection iMediaProjection) {
        if (iMediaProjection != null) {
            try {
                if (iMediaProjection.canProjectVideo()) {
                    return true;
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to query projection service for permissions", e);
            }
        }
        if (checkCallingPermission("android.permission.CAPTURE_VIDEO_OUTPUT", "canProjectVideo()")) {
            return true;
        }
        return canProjectSecureVideo(iMediaProjection);
    }

    private boolean canProjectSecureVideo(android.media.projection.IMediaProjection iMediaProjection) {
        if (iMediaProjection != null) {
            try {
                if (iMediaProjection.canProjectSecureVideo()) {
                    return true;
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to query projection service for permissions", e);
            }
        }
        return checkCallingPermission("android.permission.CAPTURE_SECURE_VIDEO_OUTPUT", "canProjectSecureVideo()");
    }

    private boolean checkCallingPermission(java.lang.String str, java.lang.String str2) {
        if (this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        android.util.Slog.w(TAG, "Permission Denial: " + str2 + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires " + str);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int createVirtualDisplayInternal(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, android.companion.virtual.IVirtualDevice iVirtualDevice, android.window.DisplayWindowPolicyController displayWindowPolicyController, java.lang.String str) {
        boolean z;
        boolean z2;
        int i;
        boolean z3;
        int createVirtualDisplayLocked;
        int i2;
        int callingUid = android.os.Binder.getCallingUid();
        if (!validatePackageName(callingUid, str)) {
            throw new java.lang.SecurityException("packageName must match the calling uid");
        }
        if (iVirtualDisplayCallback == null) {
            throw new java.lang.IllegalArgumentException("appToken must not be null");
        }
        if (virtualDisplayConfig == null) {
            throw new java.lang.IllegalArgumentException("virtualDisplayConfig must not be null");
        }
        android.view.Surface surface = virtualDisplayConfig.getSurface();
        int flags = virtualDisplayConfig.getFlags();
        if (iVirtualDevice != null) {
            try {
                if (!((android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class)).isValidVirtualDeviceId(iVirtualDevice.getDeviceId())) {
                    throw new java.lang.SecurityException("Invalid virtual device");
                }
                flags |= ((com.android.server.companion.virtual.VirtualDeviceManagerInternal) getLocalService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class)).getBaseVirtualDisplayFlags(iVirtualDevice);
            } catch (android.os.RemoteException e) {
                throw new java.lang.SecurityException("Unable to validate virtual device");
            }
        }
        if (surface != null && surface.isSingleBuffered()) {
            throw new java.lang.IllegalArgumentException("Surface can't be single-buffered");
        }
        if ((flags & 1) != 0) {
            flags |= 16;
            if ((flags & 32) != 0) {
                throw new java.lang.IllegalArgumentException("Public display must not be marked as SHOW_WHEN_LOCKED_INSECURE");
            }
        }
        if ((flags & 8) != 0) {
            flags &= -17;
        }
        if ((flags & 16) != 0) {
            flags &= -2049;
        }
        if ((flags & 2048) == 0 && (flags & 16) == 0 && iVirtualDevice != null) {
            flags |= 32768;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (iMediaProjection != null) {
                try {
                    if (!getProjectionService().isCurrentProjection(iMediaProjection)) {
                        throw new java.lang.SecurityException("Cannot create VirtualDisplay with non-current MediaProjection");
                    }
                    if (iMediaProjection.isValid()) {
                        z = false;
                    } else {
                        android.util.Slog.w(TAG, "Reusing token: create virtual display for app reusing token");
                        getProjectionService().requestConsentForInvalidProjection(iMediaProjection);
                        z = true;
                    }
                    flags = iMediaProjection.applyVirtualDisplayFlags(flags);
                    z2 = z;
                } catch (android.os.RemoteException e2) {
                    throw new java.lang.SecurityException("Unable to validate media projection or flags", e2);
                }
            } else {
                z2 = false;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (callingUid != 1000 && (flags & 16) != 0 && !canProjectVideo(iMediaProjection) && !isMirroringSupportedByVirtualDevice(iVirtualDevice)) {
                throw new java.lang.SecurityException("Requires CAPTURE_VIDEO_OUTPUT or CAPTURE_SECURE_VIDEO_OUTPUT permission, or an appropriate MediaProjection token in order to create a screen sharing virtual display. In order to create a virtual display that does not perform screen sharing (mirroring), please use the flag VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY.");
            }
            if (callingUid != 1000 && (flags & 4) != 0 && !canProjectSecureVideo(iMediaProjection)) {
                throw new java.lang.SecurityException("Requires CAPTURE_SECURE_VIDEO_OUTPUT or an appropriate MediaProjection token to create a secure virtual display.");
            }
            if (callingUid != 1000 && (flags & 1024) != 0 && !checkCallingPermission("android.permission.ADD_TRUSTED_DISPLAY", "createVirtualDisplay()")) {
                android.util.EventLog.writeEvent(1397638484, "162627132", java.lang.Integer.valueOf(callingUid), "Attempt to create a trusted display without holding permission!");
                throw new java.lang.SecurityException("Requires ADD_TRUSTED_DISPLAY permission to create a trusted virtual display.");
            }
            if (iVirtualDevice != null && (flags & 16) != 0 && (flags & 2) != 0) {
                android.util.Slog.d(TAG, "Mirror displays created by a virtual device cannot show presentations, hence ignoring flag VIRTUAL_DISPLAY_FLAG_PRESENTATION.");
                flags &= -3;
            }
            if (callingUid != 1000 && (flags & 2048) != 0 && iVirtualDevice == null && !checkCallingPermission("android.permission.ADD_TRUSTED_DISPLAY", "createVirtualDisplay()")) {
                throw new java.lang.SecurityException("Requires ADD_TRUSTED_DISPLAY permission to create a virtual display which is not in the default DisplayGroup.");
            }
            if ((flags & 4096) != 0 && callingUid != 1000 && !checkCallingPermission("android.permission.ADD_ALWAYS_UNLOCKED_DISPLAY", "createVirtualDisplay()")) {
                throw new java.lang.SecurityException("Requires ADD_ALWAYS_UNLOCKED_DISPLAY permission to create an always unlocked virtual display.");
            }
            int i3 = (flags & 1024) == 0 ? flags & (-513) : flags;
            if ((i3 & 1536) == 512 && !checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "createVirtualDisplay()")) {
                throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
            }
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.lang.String generateDisplayUniqueId = com.android.server.display.VirtualDisplayAdapter.generateDisplayUniqueId(str, callingUid, virtualDisplayConfig);
                if (virtualDisplayConfig.isHomeSupported()) {
                    if ((i3 & 1024) == 0) {
                        android.util.Slog.w(TAG, "Display created with home support but lacks VIRTUAL_DISPLAY_FLAG_TRUSTED, ignoring the home support request.");
                    } else if ((i3 & 16) != 0) {
                        android.util.Slog.w(TAG, "Display created with home support but has VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, ignoring the home support request.");
                    } else {
                        this.mWindowManagerInternal.setHomeSupportedOnDisplay(generateDisplayUniqueId, 5, true);
                    }
                }
                synchronized (this.mSyncRoot) {
                    i = i3;
                    z3 = z2;
                    try {
                        createVirtualDisplayLocked = createVirtualDisplayLocked(iVirtualDisplayCallback, iMediaProjection, callingUid, str, generateDisplayUniqueId, iVirtualDevice, surface, i3, virtualDisplayConfig);
                        if (createVirtualDisplayLocked != -1 && iVirtualDevice != null && displayWindowPolicyController != null) {
                            this.mDisplayWindowPolicyControllers.put(createVirtualDisplayLocked, android.util.Pair.create(iVirtualDevice, displayWindowPolicyController));
                            android.util.Slog.d(TAG, "Virtual Display: successfully created virtual display");
                        }
                    } finally {
                    }
                }
                if (createVirtualDisplayLocked == -1 && virtualDisplayConfig.isHomeSupported()) {
                    i2 = i;
                    if ((i2 & 1024) != 0) {
                        this.mWindowManagerInternal.clearDisplaySettings(generateDisplayUniqueId, 5);
                    }
                } else {
                    i2 = i;
                }
                android.view.ContentRecordingSession contentRecordingSession = null;
                if (iMediaProjection != null) {
                    try {
                        android.os.IBinder iBinder = iMediaProjection.getLaunchCookie() == null ? null : iMediaProjection.getLaunchCookie().binder;
                        contentRecordingSession = iBinder == null ? android.view.ContentRecordingSession.createDisplaySession(virtualDisplayConfig.getDisplayIdToMirror()) : android.view.ContentRecordingSession.createTaskSession(iBinder);
                    } catch (android.os.RemoteException e3) {
                        android.util.Slog.e(TAG, "Unable to retrieve the projection's launch cookie", e3);
                    }
                }
                if (((iMediaProjection == null && (i2 & 16) == 0) ? false : true) && createVirtualDisplayLocked != -1 && contentRecordingSession != null) {
                    contentRecordingSession.setVirtualDisplayId(createVirtualDisplayLocked);
                    contentRecordingSession.setWaitingForConsent(z3);
                    try {
                        if (!getProjectionService().setContentRecordingSession(contentRecordingSession, iMediaProjection)) {
                            android.util.Slog.w(TAG, "Content Recording: failed to start mirroring - releasing virtual display " + createVirtualDisplayLocked);
                            releaseVirtualDisplayInternal(iVirtualDisplayCallback.asBinder());
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -1;
                        }
                        if (iMediaProjection != null) {
                            android.util.Slog.d(TAG, "Content Recording: notifying MediaProjection of successful VirtualDisplay creation.");
                            iMediaProjection.notifyVirtualDisplayCreated(createVirtualDisplayLocked);
                        }
                        android.util.Slog.d(TAG, "Virtual Display: successfully set up virtual display " + createVirtualDisplayLocked);
                    } catch (android.os.RemoteException e4) {
                        android.util.Slog.e(TAG, "Unable to tell MediaProjectionManagerService to set the content recording session", e4);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return createVirtualDisplayLocked;
                    }
                }
                return createVirtualDisplayLocked;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
        }
    }

    private int createVirtualDisplayLocked(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, int i, java.lang.String str, java.lang.String str2, android.companion.virtual.IVirtualDevice iVirtualDevice, android.view.Surface surface, int i2, android.hardware.display.VirtualDisplayConfig virtualDisplayConfig) {
        if (this.mVirtualDisplayAdapter == null) {
            android.util.Slog.w(TAG, "Rejecting request to create private virtual display because the virtual display adapter is not available.");
            return -1;
        }
        android.util.Slog.d(TAG, "Virtual Display: creating DisplayDevice with VirtualDisplayAdapter");
        com.android.server.display.DisplayDevice createVirtualDisplayLocked = this.mVirtualDisplayAdapter.createVirtualDisplayLocked(iVirtualDisplayCallback, iMediaProjection, i, str, str2, surface, i2, virtualDisplayConfig);
        if (createVirtualDisplayLocked == null) {
            android.util.Slog.w(TAG, "Virtual Display: VirtualDisplayAdapter failed to create DisplayDevice");
            return -1;
        }
        if ((i2 & 32768) != 0) {
            if (iVirtualDevice != null) {
                try {
                    this.mLogicalDisplayMapper.associateDisplayDeviceWithVirtualDevice(createVirtualDisplayLocked, iVirtualDevice.getDeviceId());
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            } else {
                android.util.Slog.i(TAG, "Display created with VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP set, but no virtual device. The display will not be added to a device display group.");
            }
        }
        this.mDisplayDeviceRepo.onDisplayDeviceEvent(createVirtualDisplayLocked, 1);
        com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(createVirtualDisplayLocked);
        if (displayLocked != null) {
            return displayLocked.getDisplayIdLocked();
        }
        android.util.Slog.w(TAG, "Rejecting request to create virtual display because the logical display was not created.");
        this.mVirtualDisplayAdapter.releaseVirtualDisplayLocked(iVirtualDisplayCallback.asBinder());
        this.mDisplayDeviceRepo.onDisplayDeviceEvent(createVirtualDisplayLocked, 3);
        return -1;
    }

    private static boolean isMirroringSupportedByVirtualDevice(android.companion.virtual.IVirtualDevice iVirtualDevice) {
        return android.companion.virtual.flags.Flags.interactiveScreenMirror() && iVirtualDevice != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resizeVirtualDisplayInternal(android.os.IBinder iBinder, int i, int i2, int i3) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mVirtualDisplayAdapter == null) {
                    return;
                }
                this.mVirtualDisplayAdapter.resizeVirtualDisplayLocked(iBinder, i, i2, i3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVirtualDisplaySurfaceInternal(android.os.IBinder iBinder, android.view.Surface surface) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mVirtualDisplayAdapter == null) {
                    return;
                }
                this.mVirtualDisplayAdapter.setVirtualDisplaySurfaceLocked(iBinder, surface);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseVirtualDisplayInternal(android.os.IBinder iBinder) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mVirtualDisplayAdapter == null) {
                    return;
                }
                com.android.server.display.DisplayDevice releaseVirtualDisplayLocked = this.mVirtualDisplayAdapter.releaseVirtualDisplayLocked(iBinder);
                android.util.Slog.d(TAG, "Virtual Display: Display Device released");
                if (releaseVirtualDisplayLocked != null) {
                    this.mDisplayDeviceRepo.onDisplayDeviceEvent(releaseVirtualDisplayLocked, 3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVirtualDisplayStateInternal(android.os.IBinder iBinder, boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mVirtualDisplayAdapter == null) {
                    return;
                }
                this.mVirtualDisplayAdapter.setVirtualDisplayStateLocked(iBinder, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDefaultDisplayAdapters() {
        synchronized (this.mSyncRoot) {
            try {
                registerDisplayAdapterLocked(this.mInjector.getLocalDisplayAdapter(this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo, this.mFlags, this.mDisplayNotificationManager));
                this.mVirtualDisplayAdapter = this.mInjector.getVirtualDisplayAdapter(this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo, this.mFlags);
                if (this.mVirtualDisplayAdapter != null) {
                    registerDisplayAdapterLocked(this.mVirtualDisplayAdapter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerAdditionalDisplayAdapters() {
        synchronized (this.mSyncRoot) {
            try {
                if (shouldRegisterNonEssentialDisplayAdaptersLocked()) {
                    registerOverlayDisplayAdapterLocked();
                    registerWifiDisplayAdapterLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void registerOverlayDisplayAdapterLocked() {
        registerDisplayAdapterLocked(new com.android.server.display.OverlayDisplayAdapter(this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo, this.mUiHandler, this.mFlags));
    }

    private void registerWifiDisplayAdapterLocked() {
        if (this.mContext.getResources().getBoolean(android.R.bool.config_enableVirtualDeviceManager) || android.os.SystemProperties.getInt(FORCE_WIFI_DISPLAY_ENABLE, -1) == 1) {
            this.mWifiDisplayAdapter = new com.android.server.display.WifiDisplayAdapter(this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo, this.mPersistentDataStore, this.mFlags);
            registerDisplayAdapterLocked(this.mWifiDisplayAdapter);
        }
    }

    private boolean shouldRegisterNonEssentialDisplayAdaptersLocked() {
        return !this.mSafeMode;
    }

    private void registerDisplayAdapterLocked(com.android.server.display.DisplayAdapter displayAdapter) {
        this.mDisplayAdapters.add(displayAdapter);
        displayAdapter.registerLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    public void handleLogicalDisplayDisconnectedLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            android.util.Slog.e(TAG, "DisplayDisconnected shouldn't be received when the flag is off");
        } else {
            releaseDisplayAndEmitEvent(logicalDisplay, 7);
            this.mExternalDisplayPolicy.handleLogicalDisplayDisconnectedLocked(logicalDisplay);
        }
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    private void setupLogicalDisplay(com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        final int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        boolean z = displayIdLocked == 0;
        configureColorModeLocked(logicalDisplay, primaryDisplayDeviceLocked);
        if (!this.mAreUserDisabledHdrTypesAllowed) {
            logicalDisplay.setUserDisabledHdrTypes(this.mUserDisabledHdrTypes);
        }
        if (z) {
            notifyDefaultDisplayDeviceUpdated(logicalDisplay);
            recordStableDisplayStatsIfNeededLocked(logicalDisplay);
            recordTopInsetLocked(logicalDisplay);
        }
        if (this.mUserPreferredMode != null) {
            primaryDisplayDeviceLocked.setUserPreferredDisplayModeLocked(this.mUserPreferredMode);
        } else {
            lambda$updateUserPreferredDisplayModeSettingsLocked$4(logicalDisplay);
        }
        com.android.server.display.DisplayPowerControllerInterface addDisplayPowerControllerLocked = addDisplayPowerControllerLocked(logicalDisplay);
        if (addDisplayPowerControllerLocked != null) {
            updateDisplayPowerControllerLeaderLocked(addDisplayPowerControllerLocked, logicalDisplay.getLeadDisplayIdLocked());
            this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.display.DisplayManagerService.this.lambda$setupLogicalDisplay$7(displayIdLocked, (com.android.server.display.LogicalDisplay) obj);
                }
            });
        }
        this.mDisplayStates.append(displayIdLocked, 0);
        float f = logicalDisplay.getDisplayInfoLocked().brightnessDefault;
        this.mDisplayBrightnesses.append(displayIdLocked, new com.android.server.display.DisplayManagerService.BrightnessPair(f, f));
        android.hardware.display.DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupLogicalDisplay$7(int i, com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface;
        if (logicalDisplay.getLeadDisplayIdLocked() == i && (displayPowerControllerInterface = this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked())) != null) {
            updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, i);
        }
    }

    private void updateLogicalDisplayState(com.android.server.display.LogicalDisplay logicalDisplay) {
        java.lang.Runnable updateDisplayStateLocked = updateDisplayStateLocked(logicalDisplay.getPrimaryDisplayDeviceLocked());
        if (updateDisplayStateLocked != null) {
            updateDisplayStateLocked.run();
        }
        scheduleTraversalLocked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void handleLogicalDisplayConnectedLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            android.util.Slog.e(TAG, "DisplayConnected shouldn't be received when the flag is off");
            return;
        }
        setupLogicalDisplay(logicalDisplay);
        if (com.android.server.display.ExternalDisplayPolicy.isExternalDisplayLocked(logicalDisplay)) {
            this.mExternalDisplayPolicy.handleExternalDisplayConnectedLocked(logicalDisplay);
        } else {
            sendDisplayEventLocked(logicalDisplay, 6);
        }
        updateLogicalDisplayState(logicalDisplay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void handleLogicalDisplayAddedLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        boolean z = logicalDisplay.getDisplayIdLocked() == 0;
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            setupLogicalDisplay(logicalDisplay);
        }
        if (z) {
            this.mSyncRoot.notifyAll();
        }
        sendDisplayEventIfEnabledLocked(logicalDisplay, 1);
        updateLogicalDisplayState(logicalDisplay);
        this.mExternalDisplayPolicy.handleLogicalDisplayAddedLocked(logicalDisplay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogicalDisplayChangedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        updateViewportPowerStateLocked(logicalDisplay);
        if (logicalDisplay.getDisplayIdLocked() == 0) {
            recordTopInsetLocked(logicalDisplay);
        }
        sendDisplayEventIfEnabledLocked(logicalDisplay, 2);
        applyDisplayChangedLocked(logicalDisplay);
    }

    private void applyDisplayChangedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        scheduleTraversalLocked(false);
        this.mPersistentDataStore.saveIfNeeded();
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = this.mDisplayPowerControllers.get(displayIdLocked);
        if (displayPowerControllerInterface != null) {
            int leadDisplayIdLocked = logicalDisplay.getLeadDisplayIdLocked();
            updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, leadDisplayIdLocked);
            com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadataLocked = this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(logicalDisplay);
            if (highBrightnessModeMetadataLocked != null) {
                displayPowerControllerInterface.onDisplayChanged(highBrightnessModeMetadataLocked, leadDisplayIdLocked);
            }
        }
    }

    private void updateDisplayPowerControllerLeaderLocked(@android.annotation.NonNull com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface, int i) {
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface2;
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface3;
        if (displayPowerControllerInterface.getLeadDisplayId() == i) {
            return;
        }
        int leadDisplayId = displayPowerControllerInterface.getLeadDisplayId();
        if (leadDisplayId != -1 && (displayPowerControllerInterface3 = this.mDisplayPowerControllers.get(leadDisplayId)) != null) {
            displayPowerControllerInterface3.removeDisplayBrightnessFollower(displayPowerControllerInterface);
        }
        if (i != -1 && (displayPowerControllerInterface2 = this.mDisplayPowerControllers.get(i)) != null) {
            displayPowerControllerInterface2.addDisplayBrightnessFollower(displayPowerControllerInterface);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogicalDisplayFrameRateOverridesChangedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        sendDisplayEventFrameRateOverrideLocked(logicalDisplay.getDisplayIdLocked());
        scheduleTraversalLocked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogicalDisplayRemovedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        if (this.mFlags.isConnectedDisplayManagementEnabled()) {
            if (logicalDisplay.isValidLocked()) {
                updateViewportPowerStateLocked(logicalDisplay);
            }
            sendDisplayEventLocked(logicalDisplay, 3);
            if (logicalDisplay.isValidLocked()) {
                applyDisplayChangedLocked(logicalDisplay);
                return;
            }
            return;
        }
        releaseDisplayAndEmitEvent(logicalDisplay, 3);
    }

    private void releaseDisplayAndEmitEvent(com.android.server.display.LogicalDisplay logicalDisplay, int i) {
        final android.companion.virtual.IVirtualDevice iVirtualDevice;
        final int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = (com.android.server.display.DisplayPowerControllerInterface) this.mDisplayPowerControllers.removeReturnOld(displayIdLocked);
        if (displayPowerControllerInterface != null) {
            updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, -1);
            displayPowerControllerInterface.stop();
        }
        this.mDisplayStates.delete(displayIdLocked);
        this.mDisplayBrightnesses.delete(displayIdLocked);
        android.hardware.display.DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
        if (this.mDisplayWindowPolicyControllers.contains(displayIdLocked) && (iVirtualDevice = (android.companion.virtual.IVirtualDevice) ((android.util.Pair) this.mDisplayWindowPolicyControllers.removeReturnOld(displayIdLocked)).first) != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.DisplayManagerService.this.lambda$releaseDisplayAndEmitEvent$8(iVirtualDevice, displayIdLocked);
                }
            });
        }
        sendDisplayEventLocked(logicalDisplay, i);
        scheduleTraversalLocked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseDisplayAndEmitEvent$8(android.companion.virtual.IVirtualDevice iVirtualDevice, int i) {
        ((com.android.server.companion.virtual.VirtualDeviceManagerInternal) getLocalService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class)).onVirtualDisplayRemoved(iVirtualDevice, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogicalDisplaySwappedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        handleLogicalDisplayChangedLocked(logicalDisplay);
        updateLogicalDisplayState(logicalDisplay);
        if (logicalDisplay.getDisplayIdLocked() == 0) {
            notifyDefaultDisplayDeviceUpdated(logicalDisplay);
        }
        this.mHandler.sendEmptyMessage(6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogicalDisplayHdrSdrRatioChangedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        sendDisplayEventIfEnabledLocked(logicalDisplay, 5);
    }

    private void notifyDefaultDisplayDeviceUpdated(com.android.server.display.LogicalDisplay logicalDisplay) {
        this.mDisplayModeDirector.defaultDisplayDeviceUpdated(logicalDisplay.getPrimaryDisplayDeviceLocked().mDisplayDeviceConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLogicalDisplayDeviceStateTransitionLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked());
        if (displayPowerControllerInterface != null) {
            int leadDisplayIdLocked = logicalDisplay.getLeadDisplayIdLocked();
            updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, leadDisplayIdLocked);
            com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadataLocked = this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(logicalDisplay);
            if (highBrightnessModeMetadataLocked != null) {
                displayPowerControllerInterface.onDisplayChanged(highBrightnessModeMetadataLocked, leadDisplayIdLocked);
            }
        }
    }

    private java.lang.Runnable updateDisplayStateLocked(com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.LogicalDisplay displayLocked;
        int displayIdLocked;
        int i;
        if ((displayDevice.getDisplayDeviceInfoLocked().flags & 32) != 0 || (displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(displayDevice)) == null || (i = this.mDisplayStates.get((displayIdLocked = displayLocked.getDisplayIdLocked()))) == 0) {
            return null;
        }
        com.android.server.display.DisplayManagerService.BrightnessPair brightnessPair = this.mDisplayBrightnesses.get(displayIdLocked);
        return displayDevice.requestDisplayStateLocked(i, brightnessPair.brightness, brightnessPair.sdrBrightness, displayLocked.getDisplayOffloadSessionLocked());
    }

    private void configureColorModeLocked(com.android.server.display.LogicalDisplay logicalDisplay, com.android.server.display.DisplayDevice displayDevice) {
        if (logicalDisplay.getPrimaryDisplayDeviceLocked() == displayDevice) {
            int colorMode = this.mPersistentDataStore.getColorMode(displayDevice);
            if (colorMode == -1) {
                if (logicalDisplay.getDisplayIdLocked() == 0) {
                    colorMode = this.mDefaultDisplayDefaultColorMode;
                } else {
                    colorMode = 0;
                }
            }
            logicalDisplay.setRequestedColorModeLocked(colorMode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: configurePreferredDisplayModeLocked, reason: merged with bridge method [inline-methods] */
    public void lambda$updateUserPreferredDisplayModeSettingsLocked$4(com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        android.graphics.Point userPreferredResolution = this.mPersistentDataStore.getUserPreferredResolution(primaryDisplayDeviceLocked);
        float userPreferredRefreshRate = this.mPersistentDataStore.getUserPreferredRefreshRate(primaryDisplayDeviceLocked);
        if (userPreferredResolution == null && java.lang.Float.isNaN(userPreferredRefreshRate)) {
            return;
        }
        android.view.Display.Mode.Builder builder = new android.view.Display.Mode.Builder();
        if (userPreferredResolution != null) {
            builder.setResolution(userPreferredResolution.x, userPreferredResolution.y);
        }
        if (!java.lang.Float.isNaN(userPreferredRefreshRate)) {
            builder.setRefreshRate(userPreferredRefreshRate);
        }
        primaryDisplayDeviceLocked.setUserPreferredDisplayModeLocked(builder.build());
    }

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private void storeHdrConversionModeLocked(android.hardware.display.HdrConversionMode hdrConversionMode) {
        int i;
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "hdr_conversion_mode", hdrConversionMode.getConversionMode());
        if (hdrConversionMode.getConversionMode() == 3) {
            i = hdrConversionMode.getPreferredHdrOutputType();
        } else {
            i = -1;
        }
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "hdr_force_conversion_type", i);
    }

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    void updateHdrConversionModeSettingsLocked() {
        int i;
        int i2 = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "hdr_conversion_mode", 2);
        if (i2 == 3) {
            i = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "hdr_force_conversion_type", 1);
        } else {
            i = -1;
        }
        this.mHdrConversionMode = new android.hardware.display.HdrConversionMode(i2, i);
        setHdrConversionModeInternal(this.mHdrConversionMode);
    }

    private void recordStableDisplayStatsIfNeededLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        if (this.mStableDisplaySize.x <= 0 && this.mStableDisplaySize.y <= 0) {
            android.view.DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
            setStableDisplaySizeLocked(displayInfoLocked.getNaturalWidth(), displayInfoLocked.getNaturalHeight());
        }
    }

    private void recordTopInsetLocked(@android.annotation.Nullable com.android.server.display.LogicalDisplay logicalDisplay) {
        int i;
        if (!this.mSystemReady || logicalDisplay == null || (i = logicalDisplay.getInsets().top) == this.mDefaultDisplayTopInset) {
            return;
        }
        this.mDefaultDisplayTopInset = i;
        android.os.SystemProperties.set(PROP_DEFAULT_DISPLAY_TOP_INSET, java.lang.Integer.toString(i));
    }

    private void setStableDisplaySizeLocked(int i, int i2) {
        this.mStableDisplaySize = new android.graphics.Point(i, i2);
        try {
            this.mPersistentDataStore.setStableDisplaySize(this.mStableDisplaySize);
        } finally {
            this.mPersistentDataStore.saveIfNeeded();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.hardware.display.Curve getMinimumBrightnessCurveInternal() {
        return this.mMinimumBrightnessCurve;
    }

    int getPreferredWideGamutColorSpaceIdInternal() {
        return this.mWideColorSpace.getId();
    }

    android.hardware.OverlayProperties getOverlaySupportInternal() {
        return this.mOverlayProperties;
    }

    void setUserPreferredDisplayModeInternal(int i, android.view.Display.Mode mode) {
        int physicalHeight;
        int physicalWidth;
        synchronized (this.mSyncRoot) {
            if (mode != null) {
                try {
                    if (!isResolutionAndRefreshRateValid(mode) && i == -1) {
                        throw new java.lang.IllegalArgumentException("width, height and refresh rate of mode should be greater than 0 when setting the global user preferred display mode.");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (mode == null) {
                physicalHeight = -1;
            } else {
                physicalHeight = mode.getPhysicalHeight();
            }
            if (mode == null) {
                physicalWidth = -1;
            } else {
                physicalWidth = mode.getPhysicalWidth();
            }
            float refreshRate = mode == null ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : mode.getRefreshRate();
            storeModeInPersistentDataStoreLocked(i, physicalWidth, physicalHeight, refreshRate);
            if (i != -1) {
                setUserPreferredModeForDisplayLocked(i, mode);
            } else {
                this.mUserPreferredMode = mode;
                storeModeInGlobalSettingsLocked(physicalWidth, physicalHeight, refreshRate, mode);
            }
        }
    }

    private void storeModeInPersistentDataStoreLocked(int i, int i2, int i3, float f) {
        com.android.server.display.DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked == null) {
            return;
        }
        try {
            this.mPersistentDataStore.setUserPreferredResolution(deviceForDisplayLocked, i2, i3);
            this.mPersistentDataStore.setUserPreferredRefreshRate(deviceForDisplayLocked, f);
        } finally {
            this.mPersistentDataStore.saveIfNeeded();
        }
    }

    private void setUserPreferredModeForDisplayLocked(int i, android.view.Display.Mode mode) {
        com.android.server.display.DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked == null) {
            return;
        }
        deviceForDisplayLocked.setUserPreferredDisplayModeLocked(mode);
    }

    private void storeModeInGlobalSettingsLocked(int i, int i2, float f, final android.view.Display.Mode mode) {
        android.provider.Settings.Global.putFloat(this.mContext.getContentResolver(), "user_preferred_refresh_rate", f);
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "user_preferred_resolution_height", i2);
        android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "user_preferred_resolution_width", i);
        this.mDisplayDeviceRepo.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.DisplayDevice) obj).setUserPreferredDisplayModeLocked(mode);
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private int[] getEnabledAutoHdrTypesLocked() {
        boolean z;
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i : getSupportedHdrOutputTypesInternal()) {
            int[] iArr = this.mUserDisabledHdrTypes;
            int length = iArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = false;
                    break;
                }
                if (i != iArr[i2]) {
                    i2++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                intArray.add(i);
            }
        }
        return intArray.toArray();
    }

    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    private boolean hdrConversionIntroducesLatencyLocked() {
        android.hardware.display.HdrConversionMode hdrConversionModeSettingInternal = getHdrConversionModeSettingInternal();
        int preferredHdrOutputType = hdrConversionModeSettingInternal.getConversionMode() == 2 ? this.mSystemPreferredHdrOutputType : hdrConversionModeSettingInternal.getPreferredHdrOutputType();
        if (preferredHdrOutputType != -1) {
            return com.android.internal.util.ArrayUtils.contains(this.mInjector.getHdrOutputTypesWithLatency(), preferredHdrOutputType);
        }
        return false;
    }

    android.view.Display.Mode getUserPreferredDisplayModeInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                if (i == -1) {
                    return this.mUserPreferredMode;
                }
                com.android.server.display.DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
                if (deviceForDisplayLocked == null) {
                    return null;
                }
                return deviceForDisplayLocked.getUserPreferredDisplayModeLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.view.Display.Mode getSystemPreferredDisplayModeInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
                if (deviceForDisplayLocked == null) {
                    return null;
                }
                return deviceForDisplayLocked.getSystemPreferredDisplayModeLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setHdrConversionModeInternal(android.hardware.display.HdrConversionMode hdrConversionMode) {
        int[] iArr;
        if (!this.mInjector.getHdrOutputConversionSupport()) {
            return;
        }
        synchronized (this.mSyncRoot) {
            try {
                if (hdrConversionMode.getConversionMode() == 2 && hdrConversionMode.getPreferredHdrOutputType() != -1) {
                    throw new java.lang.IllegalArgumentException("preferredHdrOutputType must not be set if the conversion mode is HDR_CONVERSION_SYSTEM");
                }
                this.mHdrConversionMode = hdrConversionMode;
                storeHdrConversionModeLocked(this.mHdrConversionMode);
                int[] iArr2 = null;
                if (hdrConversionMode.getConversionMode() != 2) {
                    iArr = null;
                } else {
                    iArr = getEnabledAutoHdrTypesLocked();
                }
                int conversionMode = hdrConversionMode.getConversionMode();
                int preferredHdrOutputType = hdrConversionMode.getPreferredHdrOutputType();
                if (this.mOverrideHdrConversionMode == null) {
                    if (conversionMode == 3 && preferredHdrOutputType == -1) {
                        conversionMode = 1;
                        iArr2 = iArr;
                    } else {
                        iArr2 = iArr;
                    }
                } else {
                    conversionMode = this.mOverrideHdrConversionMode.getConversionMode();
                    preferredHdrOutputType = this.mOverrideHdrConversionMode.getPreferredHdrOutputType();
                }
                this.mSystemPreferredHdrOutputType = this.mInjector.setHdrConversionMode(conversionMode, preferredHdrOutputType, iArr2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.hardware.display.HdrConversionMode getHdrConversionModeSettingInternal() {
        if (!this.mInjector.getHdrOutputConversionSupport()) {
            return HDR_CONVERSION_MODE_UNSUPPORTED;
        }
        synchronized (this.mSyncRoot) {
            try {
                if (this.mHdrConversionMode != null) {
                    return this.mHdrConversionMode;
                }
                return new android.hardware.display.HdrConversionMode(2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.hardware.display.HdrConversionMode getHdrConversionModeInternal() {
        android.hardware.display.HdrConversionMode hdrConversionMode;
        if (!this.mInjector.getHdrOutputConversionSupport()) {
            return HDR_CONVERSION_MODE_UNSUPPORTED;
        }
        synchronized (this.mSyncRoot) {
            try {
                if (this.mOverrideHdrConversionMode != null) {
                    hdrConversionMode = this.mOverrideHdrConversionMode;
                } else {
                    hdrConversionMode = this.mHdrConversionMode;
                }
                if (hdrConversionMode != null && hdrConversionMode.getConversionMode() != 2) {
                    return hdrConversionMode;
                }
                return new android.hardware.display.HdrConversionMode(2, this.mSystemPreferredHdrOutputType);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getSupportedHdrOutputTypesInternal() {
        if (this.mSupportedHdrOutputType == null) {
            this.mSupportedHdrOutputType = this.mInjector.getSupportedHdrOutputTypes();
        }
        return this.mSupportedHdrOutputType;
    }

    void setShouldAlwaysRespectAppRequestedModeInternal(boolean z) {
        this.mDisplayModeDirector.setShouldAlwaysRespectAppRequestedMode(z);
    }

    boolean shouldAlwaysRespectAppRequestedModeInternal() {
        return this.mDisplayModeDirector.shouldAlwaysRespectAppRequestedMode();
    }

    void setRefreshRateSwitchingTypeInternal(int i) {
        this.mDisplayModeDirector.setModeSwitchingType(i);
    }

    int getRefreshRateSwitchingTypeInternal() {
        return this.mDisplayModeDirector.getModeSwitchingType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupportInternal(int i) {
        android.os.IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return null;
        }
        return android.view.SurfaceControl.getDisplayDecorationSupport(displayToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBrightnessConfigurationForDisplayInternal(@android.annotation.Nullable android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str, int i, java.lang.String str2) {
        validateBrightnessConfiguration(brightnessConfiguration);
        int userSerialNumber = getUserManager().getUserSerialNumber(i);
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayDevice byUniqueIdLocked = this.mDisplayDeviceRepo.getByUniqueIdLocked(str);
                if (byUniqueIdLocked == null) {
                    this.mPersistentDataStore.saveIfNeeded();
                    return;
                }
                if (this.mLogicalDisplayMapper.getDisplayLocked(byUniqueIdLocked) != null && this.mLogicalDisplayMapper.getDisplayLocked(byUniqueIdLocked).getDisplayInfoLocked().type == 1 && brightnessConfiguration != null) {
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BRIGHTNESS_CONFIGURATION_UPDATED, (float[]) brightnessConfiguration.getCurve().first, (float[]) brightnessConfiguration.getCurve().second, str);
                }
                this.mPersistentDataStore.setBrightnessConfigurationForDisplayLocked(brightnessConfiguration, byUniqueIdLocked, userSerialNumber, str2);
                this.mPersistentDataStore.saveIfNeeded();
                if (i != this.mCurrentUserId) {
                    return;
                }
                com.android.server.display.DisplayPowerControllerInterface dpcFromUniqueIdLocked = getDpcFromUniqueIdLocked(str);
                if (dpcFromUniqueIdLocked != null) {
                    dpcFromUniqueIdLocked.setBrightnessConfiguration(brightnessConfiguration, true);
                }
            } catch (java.lang.Throwable th) {
                this.mPersistentDataStore.saveIfNeeded();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.display.DisplayPowerControllerInterface getDpcFromUniqueIdLocked(java.lang.String str) {
        com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(this.mDisplayDeviceRepo.getByUniqueIdLocked(str));
        if (displayLocked != null) {
            return this.mDisplayPowerControllers.get(displayLocked.getDisplayIdLocked());
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    void validateBrightnessConfiguration(android.hardware.display.BrightnessConfiguration brightnessConfiguration) {
        if (brightnessConfiguration != null && isBrightnessConfigurationTooDark(brightnessConfiguration)) {
            throw new java.lang.IllegalArgumentException("brightness curve is too dark");
        }
    }

    private boolean isBrightnessConfigurationTooDark(android.hardware.display.BrightnessConfiguration brightnessConfiguration) {
        android.util.Pair curve = brightnessConfiguration.getCurve();
        float[] fArr = (float[]) curve.first;
        float[] fArr2 = (float[]) curve.second;
        for (int i = 0; i < fArr.length; i++) {
            if (fArr2[i] < this.mMinimumBrightnessSpline.interpolate(fArr[i])) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadBrightnessConfigurations() {
        final int userSerialNumber = getUserManager().getUserSerialNumber(this.mContext.getUserId());
        synchronized (this.mSyncRoot) {
            this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.display.DisplayManagerService.this.lambda$loadBrightnessConfigurations$10(userSerialNumber, (com.android.server.display.LogicalDisplay) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadBrightnessConfigurations$10(int i, com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface;
        android.hardware.display.BrightnessConfiguration brightnessConfigForDisplayWithPdsFallbackLocked = getBrightnessConfigForDisplayWithPdsFallbackLocked(logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(), i);
        if (brightnessConfigForDisplayWithPdsFallbackLocked != null && (displayPowerControllerInterface = this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked())) != null) {
            displayPowerControllerInterface.setBrightnessConfiguration(brightnessConfigForDisplayWithPdsFallbackLocked, false);
        }
    }

    private void performTraversalLocked(final android.view.SurfaceControl.Transaction transaction, final android.util.SparseArray<android.view.SurfaceControl.Transaction> sparseArray) {
        clearViewportsLocked();
        this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.display.DisplayManagerService.this.lambda$performTraversalLocked$11(sparseArray, transaction, (com.android.server.display.LogicalDisplay) obj);
            }
        });
        if (this.mInputManagerInternal != null) {
            this.mHandler.sendEmptyMessage(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performTraversalLocked$11(android.util.SparseArray sparseArray, android.view.SurfaceControl.Transaction transaction, com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        android.view.SurfaceControl.Transaction transaction2 = (android.view.SurfaceControl.Transaction) sparseArray.get(logicalDisplay.getDisplayIdLocked(), transaction);
        if (primaryDisplayDeviceLocked != null) {
            configureDisplayLocked(transaction2, primaryDisplayDeviceLocked);
            primaryDisplayDeviceLocked.performTraversalLocked(transaction2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x009f A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:4:0x0009, B:6:0x0011, B:9:0x0016, B:11:0x001f, B:13:0x0023, B:14:0x0049, B:18:0x0056, B:20:0x0060, B:21:0x0085, B:26:0x009f, B:27:0x00a3, B:29:0x00a9, B:31:0x00af, B:32:0x00b2, B:34:0x00b6, B:36:0x00b8, B:40:0x00c0, B:41:0x00e3, B:43:0x00d0, B:47:0x00d8, B:49:0x0065), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a9 A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:4:0x0009, B:6:0x0011, B:9:0x0016, B:11:0x001f, B:13:0x0023, B:14:0x0049, B:18:0x0056, B:20:0x0060, B:21:0x0085, B:26:0x009f, B:27:0x00a3, B:29:0x00a9, B:31:0x00af, B:32:0x00b2, B:34:0x00b6, B:36:0x00b8, B:40:0x00c0, B:41:0x00e3, B:43:0x00d0, B:47:0x00d8, B:49:0x0065), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00af A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:4:0x0009, B:6:0x0011, B:9:0x0016, B:11:0x001f, B:13:0x0023, B:14:0x0049, B:18:0x0056, B:20:0x0060, B:21:0x0085, B:26:0x009f, B:27:0x00a3, B:29:0x00a9, B:31:0x00af, B:32:0x00b2, B:34:0x00b6, B:36:0x00b8, B:40:0x00c0, B:41:0x00e3, B:43:0x00d0, B:47:0x00d8, B:49:0x0065), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b6 A[Catch: all -> 0x0013, DONT_GENERATE, TryCatch #0 {all -> 0x0013, blocks: (B:4:0x0009, B:6:0x0011, B:9:0x0016, B:11:0x001f, B:13:0x0023, B:14:0x0049, B:18:0x0056, B:20:0x0060, B:21:0x0085, B:26:0x009f, B:27:0x00a3, B:29:0x00a9, B:31:0x00af, B:32:0x00b2, B:34:0x00b6, B:36:0x00b8, B:40:0x00c0, B:41:0x00e3, B:43:0x00d0, B:47:0x00d8, B:49:0x0065), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b8 A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:4:0x0009, B:6:0x0011, B:9:0x0016, B:11:0x001f, B:13:0x0023, B:14:0x0049, B:18:0x0056, B:20:0x0060, B:21:0x0085, B:26:0x009f, B:27:0x00a3, B:29:0x00a9, B:31:0x00af, B:32:0x00b2, B:34:0x00b6, B:36:0x00b8, B:40:0x00c0, B:41:0x00e3, B:43:0x00d0, B:47:0x00d8, B:49:0x0065), top: B:3:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setDisplayPropertiesInternal(int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        int i3;
        boolean z6;
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked == null) {
                    return;
                }
                if (displayLocked.hasContentLocked() == z) {
                    z5 = false;
                } else {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Display " + i + " hasContent flag changed: hasContent=" + z + ", inTraversal=" + z4);
                    }
                    displayLocked.setHasContentLocked(z);
                    z5 = true;
                }
                if (i2 == 0 && f != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    android.view.Display.Mode findDefaultModeByRefreshRate = displayLocked.getDisplayInfoLocked().findDefaultModeByRefreshRate(f);
                    if (findDefaultModeByRefreshRate != null) {
                        i3 = findDefaultModeByRefreshRate.getModeId();
                        this.mDisplayModeDirector.getAppRequestObserver().setAppRequest(i, i3, f2, f3);
                        z6 = !isMinimalPostProcessingAllowed() && z2;
                        boolean hdrConversionIntroducesLatencyLocked = z6 ? hdrConversionIntroducesLatencyLocked() : false;
                        if (displayLocked.getRequestedMinimalPostProcessingLocked() != z6) {
                            displayLocked.setRequestedMinimalPostProcessingLocked(z6);
                            z5 = true;
                        }
                        if (z5) {
                            scheduleTraversalLocked(z4);
                        }
                        if (this.mHdrConversionMode != null) {
                            return;
                        }
                        if (this.mOverrideHdrConversionMode == null && (z3 || hdrConversionIntroducesLatencyLocked)) {
                            this.mOverrideHdrConversionMode = new android.hardware.display.HdrConversionMode(1);
                            setHdrConversionModeInternal(this.mHdrConversionMode);
                            handleLogicalDisplayChangedLocked(displayLocked);
                        } else if (this.mOverrideHdrConversionMode != null && !z3 && !hdrConversionIntroducesLatencyLocked) {
                            this.mOverrideHdrConversionMode = null;
                            setHdrConversionModeInternal(this.mHdrConversionMode);
                            handleLogicalDisplayChangedLocked(displayLocked);
                        }
                        return;
                    }
                    android.util.Slog.e(TAG, "Couldn't find a mode for the requestedRefreshRate: " + f + " on Display: " + i);
                }
                i3 = i2;
                this.mDisplayModeDirector.getAppRequestObserver().setAppRequest(i, i3, f2, f3);
                if (isMinimalPostProcessingAllowed()) {
                }
                if (z6) {
                }
                if (displayLocked.getRequestedMinimalPostProcessingLocked() != z6) {
                }
                if (z5) {
                }
                if (this.mHdrConversionMode != null) {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisplayOffsetsInternal(int i, int i2, int i3) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked == null) {
                    return;
                }
                if (displayLocked.getDisplayOffsetXLocked() != i2 || displayLocked.getDisplayOffsetYLocked() != i3) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Display " + i + " burn-in offset set to (" + i2 + ", " + i3 + ")");
                    }
                    displayLocked.setDisplayOffsetsLocked(i2, i3);
                    scheduleTraversalLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisplayScalingDisabledInternal(int i, boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked == null) {
                    return;
                }
                if (displayLocked.isDisplayScalingDisabled() != z) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Display " + i + " content scaling disabled = " + z);
                    }
                    displayLocked.setDisplayScalingDisabledLocked(z);
                    scheduleTraversalLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisplayAccessUIDsInternal(android.util.SparseArray<android.util.IntArray> sparseArray) {
        synchronized (this.mSyncRoot) {
            try {
                this.mDisplayAccessUIDs.clear();
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    this.mDisplayAccessUIDs.append(sparseArray.keyAt(size), sparseArray.valueAt(size));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUidPresentOnDisplayInternal(int i, int i2) {
        boolean z;
        synchronized (this.mSyncRoot) {
            try {
                android.util.IntArray intArray = this.mDisplayAccessUIDs.get(i2);
                z = (intArray == null || intArray.indexOf(i) == -1) ? false : true;
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.os.IBinder getDisplayToken(int i) {
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked;
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked != null && (primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked()) != null) {
                    return primaryDisplayDeviceLocked.getDisplayTokenLocked();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.window.ScreenCapture.ScreenshotHardwareBuffer systemScreenshotInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                android.os.IBinder displayToken = getDisplayToken(i);
                if (displayToken == null) {
                    return null;
                }
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked == null) {
                    return null;
                }
                android.view.DisplayInfo displayInfoLocked = displayLocked.getDisplayInfoLocked();
                return android.window.ScreenCapture.captureDisplay(new android.window.ScreenCapture.DisplayCaptureArgs.Builder(displayToken).setSize(displayInfoLocked.getNaturalWidth(), displayInfoLocked.getNaturalHeight()).setCaptureSecureLayers(true).setAllowProtected(true).build());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.window.ScreenCapture.ScreenshotHardwareBuffer userScreenshotInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                android.os.IBinder displayToken = getDisplayToken(i);
                if (displayToken == null) {
                    return null;
                }
                return android.window.ScreenCapture.captureDisplay(new android.window.ScreenCapture.DisplayCaptureArgs.Builder(displayToken).build());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.hardware.display.DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributesInternal(int i) {
        android.os.IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return null;
        }
        return android.view.SurfaceControl.getDisplayedContentSamplingAttributes(displayToken);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setDisplayedContentSamplingEnabledInternal(int i, boolean z, int i2, int i3) {
        android.os.IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return false;
        }
        return android.view.SurfaceControl.setDisplayedContentSamplingEnabled(displayToken, z, i2, i3);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.hardware.display.DisplayedContentSample getDisplayedContentSampleInternal(int i, long j, long j2) {
        android.os.IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return null;
        }
        return android.view.SurfaceControl.getDisplayedContentSample(displayToken, j, j2);
    }

    void resetBrightnessConfigurations() {
        this.mPersistentDataStore.setBrightnessConfigurationForUser(null, this.mContext.getUserId(), this.mContext.getPackageName());
        this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.display.DisplayManagerService.this.lambda$resetBrightnessConfigurations$12((com.android.server.display.LogicalDisplay) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetBrightnessConfigurations$12(com.android.server.display.LogicalDisplay logicalDisplay) {
        if (logicalDisplay.getDisplayInfoLocked().type != 1) {
            return;
        }
        setBrightnessConfigurationForDisplayInternal(null, logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(), this.mContext.getUserId(), this.mContext.getPackageName());
    }

    void setAutoBrightnessLoggingEnabled(boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = this.mDisplayPowerControllers.get(0);
                if (displayPowerControllerInterface != null) {
                    displayPowerControllerInterface.setAutoBrightnessLoggingEnabled(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDisplayWhiteBalanceLoggingEnabled(boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = this.mDisplayPowerControllers.get(0);
                if (displayPowerControllerInterface != null) {
                    displayPowerControllerInterface.setDisplayWhiteBalanceLoggingEnabled(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDisplayModeDirectorLoggingEnabled(boolean z) {
        synchronized (this.mSyncRoot) {
            this.mDisplayModeDirector.setLoggingEnabled(z);
        }
    }

    android.view.Display.Mode getActiveDisplayModeAtStart(int i) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
                if (deviceForDisplayLocked == null) {
                    return null;
                }
                return deviceForDisplayLocked.getActiveDisplayModeAtStartLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setAmbientColorTemperatureOverride(float f) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = this.mDisplayPowerControllers.get(0);
                if (displayPowerControllerInterface != null) {
                    displayPowerControllerInterface.setAmbientColorTemperatureOverride(f);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDockedAndIdleEnabled(boolean z, int i) {
        int i2;
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = this.mDisplayPowerControllers.get(i);
                if (displayPowerControllerInterface != null) {
                    if (z) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    displayPowerControllerInterface.setAutomaticScreenBrightnessMode(i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void clearViewportsLocked() {
        this.mViewports.clear();
    }

    private java.util.Optional<java.lang.Integer> getViewportType(com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        switch (displayDeviceInfo.touch) {
            case 1:
                return java.util.Optional.of(1);
            case 2:
                return java.util.Optional.of(2);
            case 3:
                if (!android.text.TextUtils.isEmpty(displayDeviceInfo.uniqueId)) {
                    return java.util.Optional.of(3);
                }
                break;
        }
        if (DEBUG) {
            android.util.Slog.w(TAG, "Display " + displayDeviceInfo + " does not support input device matching.");
        }
        return java.util.Optional.empty();
    }

    private void configureDisplayLocked(android.view.SurfaceControl.Transaction transaction, com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(displayDevice);
        if (displayLocked == null) {
            android.util.Slog.w(TAG, "Missing logical display to use for physical display device: " + displayDevice.getDisplayDeviceInfoLocked());
            return;
        }
        displayLocked.configureDisplayLocked(transaction, displayDevice, displayDeviceInfoLocked.state == 1);
        java.util.Optional<java.lang.Integer> viewportType = getViewportType(displayDeviceInfoLocked);
        if (viewportType.isPresent()) {
            populateViewportLocked(viewportType.get().intValue(), displayLocked.getDisplayIdLocked(), displayDevice, displayDeviceInfoLocked);
        }
    }

    private android.hardware.display.DisplayViewport getViewportLocked(int i, java.lang.String str) {
        if (i != 1 && i != 2 && i != 3) {
            android.util.Slog.wtf(TAG, "Cannot call getViewportByTypeLocked for type " + android.hardware.display.DisplayViewport.typeToString(i));
            return null;
        }
        int size = this.mViewports.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.hardware.display.DisplayViewport displayViewport = this.mViewports.get(i2);
            if (displayViewport.type == i && str.equals(displayViewport.uniqueId)) {
                return displayViewport;
            }
        }
        android.hardware.display.DisplayViewport displayViewport2 = new android.hardware.display.DisplayViewport();
        displayViewport2.type = i;
        displayViewport2.uniqueId = str;
        this.mViewports.add(displayViewport2);
        return displayViewport2;
    }

    private void populateViewportLocked(int i, int i2, com.android.server.display.DisplayDevice displayDevice, com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        android.hardware.display.DisplayViewport viewportLocked = getViewportLocked(i, displayDeviceInfo.uniqueId);
        displayDevice.populateViewportLocked(viewportLocked);
        viewportLocked.valid = true;
        viewportLocked.displayId = i2;
        viewportLocked.isActive = android.view.Display.isActiveState(displayDeviceInfo.state);
    }

    private void updateViewportPowerStateLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked();
        java.util.Optional<java.lang.Integer> viewportType = getViewportType(displayDeviceInfoLocked);
        if (viewportType.isPresent()) {
            java.util.Iterator<android.hardware.display.DisplayViewport> it = this.mViewports.iterator();
            while (it.hasNext()) {
                android.hardware.display.DisplayViewport next = it.next();
                if (next.type == viewportType.get().intValue() && displayDeviceInfoLocked.uniqueId.equals(next.uniqueId)) {
                    next.isActive = android.view.Display.isActiveState(displayDeviceInfoLocked.state);
                }
            }
            if (this.mInputManagerInternal != null) {
                this.mHandler.sendEmptyMessage(5);
            }
        }
    }

    private void sendDisplayEventIfEnabledLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, int i) {
        boolean isEnabledLocked = logicalDisplay.isEnabledLocked();
        if (android.os.Trace.isTagEnabled(131072L)) {
            android.os.Trace.instant(131072L, "sendDisplayEventLocked#event=" + i + ",displayEnabled=" + isEnabledLocked);
        }
        if (isEnabledLocked) {
            sendDisplayEventLocked(logicalDisplay, i);
        } else if (this.mExtraDisplayEventLogging) {
            android.util.Slog.i(TAG, "Not Sending Display Event; display is not enabled: " + logicalDisplay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDisplayEventLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(3, logicalDisplay.getDisplayIdLocked(), i);
        if (this.mExtraDisplayEventLogging) {
            android.util.Slog.i(TAG, "Deliver Display Event on Handler: " + i);
        }
        this.mHandler.sendMessage(obtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDisplayGroupEvent(int i, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(8, i, i2));
    }

    private void sendDisplayEventFrameRateOverrideLocked(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleTraversalLocked(boolean z) {
        if (!this.mPendingTraversal && this.mWindowManagerInternal != null) {
            this.mPendingTraversal = true;
            if (!z) {
                this.mHandler.sendEmptyMessage(4);
            }
        }
    }

    private boolean isUidCached(int i) {
        return this.mActivityManagerInternal != null && i >= 10000 && android.app.ActivityManager.RunningAppProcessInfo.procStateToImportance(this.mActivityManagerInternal.getUidProcessState(i)) >= 400;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliverDisplayEvent(int i, android.util.ArraySet<java.lang.Integer> arraySet, int i2) {
        int i3;
        if (DEBUG || this.mExtraDisplayEventLogging) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Delivering display event: displayId=");
            sb.append(i);
            sb.append(", event=");
            sb.append(i2);
            sb.append(arraySet != null ? ", uids=" + arraySet : "");
            android.util.Slog.d(TAG, sb.toString());
        }
        if (android.os.Trace.isTagEnabled(131072L)) {
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append("deliverDisplayEvent#event=");
            sb2.append(i2);
            sb2.append(",displayId=");
            sb2.append(i);
            sb2.append(arraySet != null ? ", uids=" + arraySet : "");
            android.os.Trace.instant(131072L, sb2.toString());
        }
        synchronized (this.mSyncRoot) {
            try {
                int size = this.mCallbacks.size();
                this.mTempCallbacks.clear();
                for (int i4 = 0; i4 < size; i4++) {
                    if (arraySet == null || arraySet.contains(java.lang.Integer.valueOf(this.mCallbacks.valueAt(i4).mUid))) {
                        this.mTempCallbacks.add(this.mCallbacks.valueAt(i4));
                    }
                }
            } finally {
            }
        }
        for (i3 = 0; i3 < this.mTempCallbacks.size(); i3++) {
            com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord = this.mTempCallbacks.get(i3);
            int i5 = callbackRecord.mUid;
            if (isUidCached(i5)) {
                synchronized (this.mPendingCallbackSelfLocked) {
                    try {
                        com.android.server.display.DisplayManagerService.PendingCallback pendingCallback = this.mPendingCallbackSelfLocked.get(i5);
                        if (extraLogging(callbackRecord.mPackageName)) {
                            android.util.Slog.i(TAG, "Uid is cached: " + i5 + ", pendingCallback: " + pendingCallback);
                        }
                        if (pendingCallback == null) {
                            this.mPendingCallbackSelfLocked.put(i5, new com.android.server.display.DisplayManagerService.PendingCallback(callbackRecord, i, i2));
                        } else {
                            pendingCallback.addDisplayEvent(i, i2);
                        }
                    } finally {
                    }
                }
            } else {
                callbackRecord.notifyDisplayEventAsync(i, i2);
            }
        }
        this.mTempCallbacks.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean extraLogging(java.lang.String str) {
        return this.mExtraDisplayEventLogging && this.mExtraDisplayLoggingPackageName.equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliverDisplayGroupEvent(int i, int i2) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Delivering display group event: groupId=" + i + ", event=" + i2);
        }
        switch (i2) {
            case 1:
                java.util.Iterator<android.hardware.display.DisplayManagerInternal.DisplayGroupListener> it = this.mDisplayGroupListeners.iterator();
                while (it.hasNext()) {
                    it.next().onDisplayGroupAdded(i);
                }
                break;
            case 2:
                java.util.Iterator<android.hardware.display.DisplayManagerInternal.DisplayGroupListener> it2 = this.mDisplayGroupListeners.iterator();
                while (it2.hasNext()) {
                    it2.next().onDisplayGroupChanged(i);
                }
                break;
            case 3:
                java.util.Iterator<android.hardware.display.DisplayManagerInternal.DisplayGroupListener> it3 = this.mDisplayGroupListeners.iterator();
                while (it3.hasNext()) {
                    it3.next().onDisplayGroupRemoved(i);
                }
                break;
        }
    }

    private android.media.projection.IMediaProjectionManager getProjectionService() {
        if (this.mProjectionService == null) {
            this.mProjectionService = this.mInjector.getProjectionService();
        }
        return this.mProjectionService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.UserManager getUserManager() {
        return (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(final java.io.PrintWriter printWriter) {
        com.android.server.display.BrightnessTracker brightnessTracker;
        printWriter.println("DISPLAY MANAGER (dumpsys display)");
        synchronized (this.mSyncRoot) {
            try {
                brightnessTracker = this.mBrightnessTracker;
                printWriter.println("  mSafeMode=" + this.mSafeMode);
                printWriter.println("  mPendingTraversal=" + this.mPendingTraversal);
                printWriter.println("  mViewports=" + this.mViewports);
                printWriter.println("  mDefaultDisplayDefaultColorMode=" + this.mDefaultDisplayDefaultColorMode);
                printWriter.println("  mWifiDisplayScanRequestCount=" + this.mWifiDisplayScanRequestCount);
                printWriter.println("  mStableDisplaySize=" + this.mStableDisplaySize);
                printWriter.println("  mMinimumBrightnessCurve=" + this.mMinimumBrightnessCurve);
                if (this.mUserPreferredMode != null) {
                    printWriter.println(" mUserPreferredMode=" + this.mUserPreferredMode);
                }
                printWriter.println();
                if (!this.mAreUserDisabledHdrTypesAllowed) {
                    printWriter.println("  mUserDisabledHdrTypes: size=" + this.mUserDisabledHdrTypes.length);
                    int[] iArr = this.mUserDisabledHdrTypes;
                    int length = iArr.length;
                    for (int i = 0; i < length; i++) {
                        printWriter.println("  " + iArr[i]);
                    }
                }
                if (this.mHdrConversionMode != null) {
                    printWriter.println("  mHdrConversionMode=" + this.mHdrConversionMode);
                }
                printWriter.println();
                int size = this.mDisplayStates.size();
                printWriter.println("Display States: size=" + size);
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mDisplayStates.keyAt(i2);
                    int valueAt = this.mDisplayStates.valueAt(i2);
                    com.android.server.display.DisplayManagerService.BrightnessPair valueAt2 = this.mDisplayBrightnesses.valueAt(i2);
                    printWriter.println("  Display Id=" + keyAt);
                    printWriter.println("  Display State=" + android.view.Display.stateToString(valueAt));
                    printWriter.println("  Display Brightness=" + valueAt2.brightness);
                    printWriter.println("  Display SdrBrightness=" + valueAt2.sdrBrightness);
                }
                final java.io.PrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "    ");
                indentingPrintWriter.increaseIndent();
                printWriter.println();
                printWriter.println("Display Adapters: size=" + this.mDisplayAdapters.size());
                java.util.Iterator<com.android.server.display.DisplayAdapter> it = this.mDisplayAdapters.iterator();
                while (it.hasNext()) {
                    com.android.server.display.DisplayAdapter next = it.next();
                    printWriter.println("  " + next.getName());
                    next.dumpLocked(indentingPrintWriter);
                }
                printWriter.println();
                printWriter.println("Display Devices: size=" + this.mDisplayDeviceRepo.sizeLocked());
                this.mDisplayDeviceRepo.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda14
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.display.DisplayManagerService.lambda$dumpInternal$13(printWriter, indentingPrintWriter, (com.android.server.display.DisplayDevice) obj);
                    }
                });
                printWriter.println();
                this.mLogicalDisplayMapper.dumpLocked(printWriter);
                int size2 = this.mCallbacks.size();
                printWriter.println();
                printWriter.println("Callbacks: size=" + size2);
                for (int i3 = 0; i3 < size2; i3++) {
                    com.android.server.display.DisplayManagerService.CallbackRecord valueAt3 = this.mCallbacks.valueAt(i3);
                    printWriter.println("  " + i3 + ": mPid=" + valueAt3.mPid + ", mWifiDisplayScanRequested=" + valueAt3.mWifiDisplayScanRequested);
                }
                int size3 = this.mDisplayPowerControllers.size();
                printWriter.println();
                printWriter.println("Display Power Controllers: size=" + size3);
                for (int i4 = 0; i4 < size3; i4++) {
                    this.mDisplayPowerControllers.valueAt(i4).dump(printWriter);
                }
                printWriter.println();
                this.mPersistentDataStore.dump(printWriter);
                int size4 = this.mDisplayWindowPolicyControllers.size();
                printWriter.println();
                printWriter.println("Display Window Policy Controllers: size=" + size4);
                for (int i5 = 0; i5 < size4; i5++) {
                    printWriter.print("Display " + this.mDisplayWindowPolicyControllers.keyAt(i5) + ":");
                    ((android.window.DisplayWindowPolicyController) this.mDisplayWindowPolicyControllers.valueAt(i5).second).dump("  ", printWriter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (brightnessTracker != null) {
            printWriter.println();
            brightnessTracker.dump(printWriter);
        }
        printWriter.println();
        this.mDisplayModeDirector.dump(printWriter);
        this.mBrightnessSynchronizer.dump(printWriter);
        if (this.mSmallAreaDetectionController != null) {
            this.mSmallAreaDetectionController.dump(printWriter);
        }
        printWriter.println();
        this.mFlags.dump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpInternal$13(java.io.PrintWriter printWriter, com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.display.DisplayDevice displayDevice) {
        printWriter.println("  " + displayDevice.getDisplayDeviceInfoLocked());
        displayDevice.dumpLocked(indentingPrintWriter);
    }

    private static float[] getFloatArray(android.content.res.TypedArray typedArray) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, Float.NaN);
        }
        typedArray.recycle();
        return fArr;
    }

    private static boolean isResolutionAndRefreshRateValid(android.view.Display.Mode mode) {
        return mode.getPhysicalWidth() > 0 && mode.getPhysicalHeight() > 0 && mode.getRefreshRate() > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    void enableConnectedDisplay(int i, boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked == null) {
                    android.util.Slog.w(TAG, "enableConnectedDisplay: Can not find displayId=" + i);
                } else if (com.android.server.display.ExternalDisplayPolicy.isExternalDisplayLocked(displayLocked)) {
                    this.mExternalDisplayPolicy.setExternalDisplayEnabledLocked(displayLocked, z);
                } else {
                    this.mLogicalDisplayMapper.setDisplayEnabledLocked(displayLocked, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.display.VirtualDisplayAdapter getVirtualDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
            return new com.android.server.display.VirtualDisplayAdapter(syncRoot, context, handler, listener, displayManagerFlags);
        }

        com.android.server.display.LocalDisplayAdapter getLocalDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, com.android.server.display.notifications.DisplayNotificationManager displayNotificationManager) {
            return new com.android.server.display.LocalDisplayAdapter(syncRoot, context, handler, listener, displayManagerFlags, displayNotificationManager);
        }

        long getDefaultDisplayDelayTimeout() {
            return 10000L;
        }

        int setHdrConversionMode(int i, int i2, int[] iArr) {
            return com.android.server.display.DisplayControl.setHdrConversionMode(i, i2, iArr);
        }

        int[] getSupportedHdrOutputTypes() {
            return com.android.server.display.DisplayControl.getSupportedHdrOutputTypes();
        }

        int[] getHdrOutputTypesWithLatency() {
            return com.android.server.display.DisplayControl.getHdrOutputTypesWithLatency();
        }

        boolean getHdrOutputConversionSupport() {
            return com.android.server.display.DisplayControl.getHdrOutputConversionSupport();
        }

        android.media.projection.IMediaProjectionManager getProjectionService() {
            return android.media.projection.IMediaProjectionManager.Stub.asInterface(android.os.ServiceManager.getService("media_projection"));
        }

        com.android.server.display.feature.DisplayManagerFlags getFlags() {
            return new com.android.server.display.feature.DisplayManagerFlags();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.DisplayDeviceInfo getDisplayDeviceInfoInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
                if (displayLocked == null) {
                    return null;
                }
                return displayLocked.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.view.Surface getVirtualDisplaySurfaceInternal(android.os.IBinder iBinder) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mVirtualDisplayAdapter == null) {
                    return null;
                }
                return this.mVirtualDisplayAdapter.getVirtualDisplaySurfaceLocked(iBinder);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeDisplayPowerControllersLocked() {
        this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.display.DisplayManagerService.this.addDisplayPowerControllerLocked((com.android.server.display.LogicalDisplay) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public com.android.server.display.DisplayPowerControllerInterface addDisplayPowerControllerLocked(final com.android.server.display.LogicalDisplay logicalDisplay) {
        if (this.mPowerHandler == null) {
            return null;
        }
        if (this.mBrightnessTracker == null && logicalDisplay.getDisplayIdLocked() == 0) {
            this.mBrightnessTracker = new com.android.server.display.BrightnessTracker(this.mContext, null);
        }
        com.android.server.display.DisplayPowerController displayPowerController = new com.android.server.display.DisplayPowerController(this.mContext, null, this.mDisplayPowerCallbacks, this.mPowerHandler, this.mSensorManager, this.mDisplayBlanker, logicalDisplay, this.mBrightnessTracker, new com.android.server.display.BrightnessSetting(getUserManager().getUserSerialNumber(this.mContext.getUserId()), this.mPersistentDataStore, logicalDisplay, this.mSyncRoot), new java.lang.Runnable() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.DisplayManagerService.this.lambda$addDisplayPowerControllerLocked$14(logicalDisplay);
            }
        }, this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(logicalDisplay), this.mBootCompleted, this.mFlags);
        this.mDisplayPowerControllers.append(logicalDisplay.getDisplayIdLocked(), displayPowerController);
        return displayPowerController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleBrightnessChange, reason: merged with bridge method [inline-methods] */
    public void lambda$addDisplayPowerControllerLocked$14(com.android.server.display.LogicalDisplay logicalDisplay) {
        synchronized (this.mSyncRoot) {
            sendDisplayEventIfEnabledLocked(logicalDisplay, 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.display.DisplayDevice getDeviceForDisplayLocked(int i) {
        com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (displayLocked == null) {
            return null;
        }
        return displayLocked.getPrimaryDisplayDeviceLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.display.BrightnessConfiguration getBrightnessConfigForDisplayWithPdsFallbackLocked(java.lang.String str, int i) {
        android.hardware.display.BrightnessConfiguration brightnessConfigurationForDisplayLocked = this.mPersistentDataStore.getBrightnessConfigurationForDisplayLocked(str, i);
        if (brightnessConfigurationForDisplayLocked == null) {
            return this.mPersistentDataStore.getBrightnessConfiguration(i);
        }
        return brightnessConfigurationForDisplayLocked;
    }

    private final class DisplayManagerHandler extends android.os.Handler {
        public DisplayManagerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            boolean z;
            switch (message.what) {
                case 1:
                    com.android.server.display.DisplayManagerService.this.registerDefaultDisplayAdapters();
                    return;
                case 2:
                    com.android.server.display.DisplayManagerService.this.registerAdditionalDisplayAdapters();
                    return;
                case 3:
                    com.android.server.display.DisplayManagerService.this.deliverDisplayEvent(message.arg1, null, message.arg2);
                    return;
                case 4:
                    com.android.server.display.DisplayManagerService.this.mWindowManagerInternal.requestTraversalFromDisplayManager();
                    return;
                case 5:
                    synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                        try {
                            z = !com.android.server.display.DisplayManagerService.this.mTempViewports.equals(com.android.server.display.DisplayManagerService.this.mViewports);
                            if (z) {
                                com.android.server.display.DisplayManagerService.this.mTempViewports.clear();
                                java.util.Iterator it = com.android.server.display.DisplayManagerService.this.mViewports.iterator();
                                while (it.hasNext()) {
                                    com.android.server.display.DisplayManagerService.this.mTempViewports.add(((android.hardware.display.DisplayViewport) it.next()).makeCopy());
                                }
                            }
                        } finally {
                        }
                    }
                    if (z) {
                        com.android.server.display.DisplayManagerService.this.mInputManagerInternal.setDisplayViewports(com.android.server.display.DisplayManagerService.this.mTempViewports);
                        return;
                    }
                    return;
                case 6:
                    com.android.server.display.DisplayManagerService.this.loadBrightnessConfigurations();
                    return;
                case 7:
                    synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                        try {
                            com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(message.arg1);
                            if (displayLocked != null) {
                                android.util.ArraySet<java.lang.Integer> pendingFrameRateOverrideUids = displayLocked.getPendingFrameRateOverrideUids();
                                displayLocked.clearPendingFrameRateOverrideUids();
                                com.android.server.display.DisplayManagerService.this.deliverDisplayEvent(message.arg1, pendingFrameRateOverrideUids, message.arg2);
                            }
                        } finally {
                        }
                    }
                    return;
                case 8:
                    com.android.server.display.DisplayManagerService.this.deliverDisplayGroupEvent(message.arg1, message.arg2);
                    return;
                case 9:
                    com.android.server.display.DisplayManagerService.this.mWindowManagerInternal.onDisplayManagerReceivedDeviceState(message.arg1);
                    return;
                default:
                    return;
            }
        }
    }

    private final class LogicalDisplayListener implements com.android.server.display.LogicalDisplayMapper.Listener {
        private LogicalDisplayListener() {
        }

        @Override // com.android.server.display.LogicalDisplayMapper.Listener
        @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
        public void onLogicalDisplayEventLocked(com.android.server.display.LogicalDisplay logicalDisplay, int i) {
            switch (i) {
                case 1:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayAddedLocked(logicalDisplay);
                    break;
                case 2:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayChangedLocked(logicalDisplay);
                    break;
                case 3:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayRemovedLocked(logicalDisplay);
                    break;
                case 4:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplaySwappedLocked(logicalDisplay);
                    break;
                case 5:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayFrameRateOverridesChangedLocked(logicalDisplay);
                    break;
                case 6:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayDeviceStateTransitionLocked(logicalDisplay);
                    break;
                case 7:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayHdrSdrRatioChangedLocked(logicalDisplay);
                    break;
                case 8:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayConnectedLocked(logicalDisplay);
                    break;
                case 9:
                    com.android.server.display.DisplayManagerService.this.handleLogicalDisplayDisconnectedLocked(logicalDisplay);
                    break;
            }
        }

        @Override // com.android.server.display.LogicalDisplayMapper.Listener
        public void onDisplayGroupEventLocked(int i, int i2) {
            com.android.server.display.DisplayManagerService.this.sendDisplayGroupEvent(i, i2);
        }

        @Override // com.android.server.display.LogicalDisplayMapper.Listener
        public void onTraversalRequested() {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                com.android.server.display.DisplayManagerService.this.scheduleTraversalLocked(false);
            }
        }
    }

    private final class CallbackRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.display.IDisplayManagerCallback mCallback;
        private java.util.concurrent.atomic.AtomicLong mEventsMask;
        private final java.lang.String mPackageName;
        public final int mPid;
        public final int mUid;
        public boolean mWifiDisplayScanRequested;

        CallbackRecord(int i, int i2, android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback, long j) {
            this.mPid = i;
            this.mUid = i2;
            this.mCallback = iDisplayManagerCallback;
            this.mEventsMask = new java.util.concurrent.atomic.AtomicLong(j);
            java.lang.String[] packagesForUid = com.android.server.display.DisplayManagerService.this.mContext.getPackageManager().getPackagesForUid(i2);
            this.mPackageName = packagesForUid == null ? null : packagesForUid[0];
        }

        public void updateEventsMask(long j) {
            this.mEventsMask.set(j);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.display.DisplayManagerService.DEBUG || com.android.server.display.DisplayManagerService.this.extraLogging(this.mPackageName)) {
                android.util.Slog.d(com.android.server.display.DisplayManagerService.TAG, "Display listener for pid " + this.mPid + " died.");
            }
            if (android.os.Trace.isTagEnabled(131072L)) {
                android.os.Trace.instant(131072L, "displayManagerBinderDied#mPid=" + this.mPid);
            }
            com.android.server.display.DisplayManagerService.this.onCallbackDied(this);
        }

        public boolean notifyDisplayEventAsync(int i, int i2) {
            if (!shouldSendEvent(i2)) {
                if (com.android.server.display.DisplayManagerService.this.extraLogging(this.mPackageName)) {
                    android.util.Slog.i(com.android.server.display.DisplayManagerService.TAG, "Not sending displayEvent: " + i2 + " due to mask:" + this.mEventsMask);
                }
                if (android.os.Trace.isTagEnabled(131072L)) {
                    android.os.Trace.instant(131072L, "notifyDisplayEventAsync#notSendingEvent=" + i2 + ",mEventsMask=" + this.mEventsMask);
                }
                return true;
            }
            try {
                this.mCallback.onDisplayEvent(i, i2);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.display.DisplayManagerService.TAG, "Failed to notify process " + this.mPid + " that displays changed, assuming it died.", e);
                binderDied();
                return false;
            }
        }

        private boolean shouldSendEvent(int i) {
            long j = this.mEventsMask.get();
            switch (i) {
                case 1:
                    if ((j & 1) != 0) {
                        break;
                    }
                    break;
                case 2:
                    if ((j & 4) != 0) {
                        break;
                    }
                    break;
                case 3:
                    if ((j & 2) != 0) {
                        break;
                    }
                    break;
                case 4:
                    if ((j & 8) != 0) {
                        break;
                    }
                    break;
                case 5:
                    if ((j & 16) != 0) {
                        break;
                    }
                    break;
                case 6:
                case 7:
                    if ((j & 32) != 0) {
                        break;
                    }
                    break;
                default:
                    android.util.Slog.e(com.android.server.display.DisplayManagerService.TAG, "Unknown display event " + i);
                    break;
            }
            return true;
        }
    }

    private static final class PendingCallback {
        private final com.android.server.display.DisplayManagerService.CallbackRecord mCallbackRecord;
        private final java.util.ArrayList<android.util.Pair<java.lang.Integer, java.lang.Integer>> mDisplayEvents = new java.util.ArrayList<>();

        PendingCallback(com.android.server.display.DisplayManagerService.CallbackRecord callbackRecord, int i, int i2) {
            this.mCallbackRecord = callbackRecord;
            this.mDisplayEvents.add(new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }

        public void addDisplayEvent(int i, int i2) {
            android.util.Pair<java.lang.Integer, java.lang.Integer> pair = this.mDisplayEvents.get(this.mDisplayEvents.size() - 1);
            if (((java.lang.Integer) pair.first).intValue() == i && ((java.lang.Integer) pair.second).intValue() == i2) {
                android.util.Slog.d(com.android.server.display.DisplayManagerService.TAG, "Ignore redundant display event " + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + " to " + this.mCallbackRecord.mUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mCallbackRecord.mPid);
                return;
            }
            this.mDisplayEvents.add(new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }

        public void sendPendingDisplayEvent() {
            int i = 0;
            while (true) {
                if (i >= this.mDisplayEvents.size()) {
                    break;
                }
                android.util.Pair<java.lang.Integer, java.lang.Integer> pair = this.mDisplayEvents.get(i);
                if (com.android.server.display.DisplayManagerService.DEBUG) {
                    android.util.Slog.d(com.android.server.display.DisplayManagerService.TAG, "Send pending display event #" + i + " " + pair.first + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + pair.second + " to " + this.mCallbackRecord.mUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mCallbackRecord.mPid);
                }
                if (this.mCallbackRecord.notifyDisplayEventAsync(((java.lang.Integer) pair.first).intValue(), ((java.lang.Integer) pair.second).intValue())) {
                    i++;
                } else {
                    android.util.Slog.d(com.android.server.display.DisplayManagerService.TAG, "Drop pending events for dead process " + this.mCallbackRecord.mPid);
                    break;
                }
            }
            this.mDisplayEvents.clear();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class BinderService extends android.hardware.display.IDisplayManager.Stub {
        BinderService() {
        }

        public android.view.DisplayInfo getDisplayInfo(int i) {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getDisplayInfoInternal(i, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int[] getDisplayIds(boolean z) {
            int[] displayIdsLocked;
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    displayIdsLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayIdsLocked(callingUid, z);
                }
                return displayIdsLocked;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isUidPresentOnDisplay(int i, int i2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.isUidPresentOnDisplayInternal(i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.graphics.Point getStableDisplaySize() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getStableDisplaySizeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerCallback(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback) {
            registerCallbackWithEventMask(iDisplayManagerCallback, 7L);
        }

        @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
        public void registerCallbackWithEventMask(android.hardware.display.IDisplayManagerCallback iDisplayManagerCallback, long j) {
            if (iDisplayManagerCallback == null) {
                throw new java.lang.IllegalArgumentException("listener must not be null");
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            if (com.android.server.display.DisplayManagerService.this.mFlags.isConnectedDisplayManagementEnabled() && (32 & j) != 0) {
                com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DISPLAYS", "Permission required to get signals about connection events.");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.registerCallbackInternal(iDisplayManagerCallback, callingPid, callingUid, j);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_WIFI_DISPLAY")
        public void startWifiDisplayScan() {
            startWifiDisplayScan_enforcePermission();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.startWifiDisplayScanInternal(callingPid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_WIFI_DISPLAY")
        public void stopWifiDisplayScan() {
            stopWifiDisplayScan_enforcePermission();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.stopWifiDisplayScanInternal(callingPid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void connectWifiDisplay(java.lang.String str) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("address must not be null");
            }
            com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to connect to a wifi display");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.connectWifiDisplayInternal(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void disconnectWifiDisplay() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.disconnectWifiDisplayInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void renameWifiDisplay(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("address must not be null");
            }
            com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to rename to a wifi display");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.renameWifiDisplayInternal(str, str2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void forgetWifiDisplay(java.lang.String str) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("address must not be null");
            }
            com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to forget to a wifi display");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.forgetWifiDisplayInternal(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_WIFI_DISPLAY")
        public void pauseWifiDisplay() {
            pauseWifiDisplay_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.pauseWifiDisplayInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_WIFI_DISPLAY")
        public void resumeWifiDisplay() {
            resumeWifiDisplay_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.resumeWifiDisplayInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.display.WifiDisplayStatus getWifiDisplayStatus() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getWifiDisplayStatusInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
        public void setUserDisabledHdrTypes(int[] iArr) {
            setUserDisabledHdrTypes_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setUserDisabledHdrTypesInternal(iArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void overrideHdrTypes(int i, int[] iArr) {
            android.os.IBinder displayToken;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                displayToken = com.android.server.display.DisplayManagerService.this.getDisplayToken(i);
                if (displayToken == null) {
                    throw new java.lang.IllegalArgumentException("Invalid display: " + i);
                }
            }
            com.android.server.display.DisplayControl.overrideHdrTypes(displayToken, iArr);
        }

        @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
        public void setAreUserDisabledHdrTypesAllowed(boolean z) {
            setAreUserDisabledHdrTypesAllowed_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setAreUserDisabledHdrTypesAllowedInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean areUserDisabledHdrTypesAllowed() {
            boolean z;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                z = com.android.server.display.DisplayManagerService.this.mAreUserDisabledHdrTypesAllowed;
            }
            return z;
        }

        public int[] getUserDisabledHdrTypes() {
            int[] iArr;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                iArr = com.android.server.display.DisplayManagerService.this.mUserDisabledHdrTypes;
            }
            return iArr;
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_DISPLAY_COLOR_MODE")
        public void requestColorMode(int i, int i2) {
            requestColorMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.requestColorModeInternal(i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, java.lang.String str) {
            return com.android.server.display.DisplayManagerService.this.createVirtualDisplayInternal(virtualDisplayConfig, iVirtualDisplayCallback, iMediaProjection, null, null, str);
        }

        public void resizeVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) {
            if (i <= 0 || i2 <= 0 || i3 <= 0) {
                throw new java.lang.IllegalArgumentException("width, height, and densityDpi must be greater than 0");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.resizeVirtualDisplayInternal(iVirtualDisplayCallback.asBinder(), i, i2, i3);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVirtualDisplaySurface(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.view.Surface surface) {
            if (surface != null && surface.isSingleBuffered()) {
                throw new java.lang.IllegalArgumentException("Surface can't be single-buffered");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setVirtualDisplaySurfaceInternal(iVirtualDisplayCallback.asBinder(), surface);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void releaseVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.releaseVirtualDisplayInternal(iVirtualDisplayCallback.asBinder());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVirtualDisplayState(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setVirtualDisplayStateInternal(iVirtualDisplayCallback.asBinder(), z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.display.DisplayManagerService.this.mContext, com.android.server.display.DisplayManagerService.TAG, printWriter)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.display.DisplayManagerService.this.dumpInternal(printWriter);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.BRIGHTNESS_SLIDER_USAGE")
        public android.content.pm.ParceledListSlice<android.hardware.display.BrightnessChangeEvent> getBrightnessEvents(java.lang.String str) {
            android.content.pm.ParceledListSlice<android.hardware.display.BrightnessChangeEvent> brightnessEvents;
            getBrightnessEvents_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            int noteOp = ((android.app.AppOpsManager) com.android.server.display.DisplayManagerService.this.mContext.getSystemService(android.app.AppOpsManager.class)).noteOp(43, callingUid, str);
            boolean z = true;
            if (noteOp == 3) {
                if (com.android.server.display.DisplayManagerService.this.mContext.checkCallingPermission("android.permission.PACKAGE_USAGE_STATS") != 0) {
                    z = false;
                }
            } else if (noteOp != 0) {
                z = false;
            }
            int userId = android.os.UserHandle.getUserId(callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    brightnessEvents = ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).getBrightnessEvents(userId, z);
                }
                return brightnessEvents;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_AMBIENT_LIGHT_STATS")
        public android.content.pm.ParceledListSlice<android.hardware.display.AmbientBrightnessDayStats> getAmbientBrightnessStats() {
            android.content.pm.ParceledListSlice<android.hardware.display.AmbientBrightnessDayStats> ambientBrightnessStats;
            getAmbientBrightnessStats_enforcePermission();
            int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    ambientBrightnessStats = ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).getAmbientBrightnessStats(userId);
                }
                return ambientBrightnessStats;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_DISPLAY_BRIGHTNESS")
        public void setBrightnessConfigurationForUser(final android.hardware.display.BrightnessConfiguration brightnessConfiguration, final int i, final java.lang.String str) {
            setBrightnessConfigurationForUser_enforcePermission();
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to change the display brightness configuration of another user");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$BinderService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.display.DisplayManagerService.BinderService.this.lambda$setBrightnessConfigurationForUser$0(brightnessConfiguration, i, str, (com.android.server.display.LogicalDisplay) obj);
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setBrightnessConfigurationForUser$0(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str, com.android.server.display.LogicalDisplay logicalDisplay) {
            if (logicalDisplay.getDisplayInfoLocked().type != 1) {
                return;
            }
            com.android.server.display.DisplayManagerService.this.setBrightnessConfigurationForDisplayInternal(brightnessConfiguration, logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(), i, str);
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_DISPLAY_BRIGHTNESS")
        public void setBrightnessConfigurationForDisplay(android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str, int i, java.lang.String str2) {
            setBrightnessConfigurationForDisplay_enforcePermission();
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to change the display brightness configuration of another user");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setBrightnessConfigurationForDisplayInternal(brightnessConfiguration, str, i, str2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_DISPLAY_BRIGHTNESS")
        public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplay(java.lang.String str, int i) {
            android.hardware.display.BrightnessConfiguration brightnessConfigForDisplayWithPdsFallbackLocked;
            com.android.server.display.DisplayPowerControllerInterface dpcFromUniqueIdLocked;
            getBrightnessConfigurationForDisplay_enforcePermission();
            if (i != android.os.UserHandle.getCallingUserId()) {
                com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to read the display brightness configuration of another user");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            int userSerialNumber = com.android.server.display.DisplayManagerService.this.getUserManager().getUserSerialNumber(i);
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    try {
                        brightnessConfigForDisplayWithPdsFallbackLocked = com.android.server.display.DisplayManagerService.this.getBrightnessConfigForDisplayWithPdsFallbackLocked(str, userSerialNumber);
                        if (brightnessConfigForDisplayWithPdsFallbackLocked == null && (dpcFromUniqueIdLocked = com.android.server.display.DisplayManagerService.this.getDpcFromUniqueIdLocked(str)) != null) {
                            brightnessConfigForDisplayWithPdsFallbackLocked = dpcFromUniqueIdLocked.getDefaultBrightnessConfiguration();
                        }
                    } finally {
                    }
                }
                return brightnessConfigForDisplayWithPdsFallbackLocked;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
            java.lang.String uniqueId;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                uniqueId = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(0).getPrimaryDisplayDeviceLocked().getUniqueId();
            }
            return getBrightnessConfigurationForDisplay(uniqueId, i);
        }

        @android.annotation.EnforcePermission("android.permission.CONFIGURE_DISPLAY_BRIGHTNESS")
        public android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() {
            android.hardware.display.BrightnessConfiguration defaultBrightnessConfiguration;
            getDefaultBrightnessConfiguration_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    defaultBrightnessConfiguration = ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).getDefaultBrightnessConfiguration();
                }
                return defaultBrightnessConfiguration;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS")
        public android.hardware.display.BrightnessInfo getBrightnessInfo(int i) {
            getBrightnessInfo_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, false);
                    if (displayLocked == null || !displayLocked.isEnabledLocked()) {
                        return null;
                    }
                    com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = (com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(i);
                    if (displayPowerControllerInterface == null) {
                        return null;
                    }
                    return displayPowerControllerInterface.getBrightnessInfo();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isMinimalPostProcessingRequested(int i) {
            boolean requestedMinimalPostProcessingLocked;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                requestedMinimalPostProcessingLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i).getRequestedMinimalPostProcessingLocked();
            }
            return requestedMinimalPostProcessingLocked;
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS")
        public void setTemporaryBrightness(int i, float f) {
            setTemporaryBrightness_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(i)).setTemporaryBrightness(f);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS")
        public void setBrightness(int i, float f) {
            setBrightness_enforcePermission();
            if (!com.android.server.display.DisplayManagerService.isValidBrightness(f)) {
                android.util.Slog.w(com.android.server.display.DisplayManagerService.TAG, "Attempted to set invalid brightness" + f);
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    try {
                        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = (com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(i);
                        if (displayPowerControllerInterface != null) {
                            displayPowerControllerInterface.setBrightness(f);
                        }
                        com.android.server.display.DisplayManagerService.this.mPersistentDataStore.saveIfNeeded();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public float getBrightness(int i) {
            float f;
            com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", "Permission required to set the display's brightness");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    try {
                        com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = (com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(i);
                        if (displayPowerControllerInterface == null) {
                            f = Float.NaN;
                        } else {
                            f = displayPowerControllerInterface.getScreenBrightnessSetting();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return f;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS")
        public void setTemporaryAutoBrightnessAdjustment(float f) {
            setTemporaryAutoBrightnessAdjustment_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                    ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).setTemporaryAutoBrightnessAdjustment(f);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.display.DisplayManagerShellCommand(com.android.server.display.DisplayManagerService.this, com.android.server.display.DisplayManagerService.this.mFlags).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public android.hardware.display.Curve getMinimumBrightnessCurve() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getMinimumBrightnessCurveInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getPreferredWideGamutColorSpaceId() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getPreferredWideGamutColorSpaceIdInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MODIFY_USER_PREFERRED_DISPLAY_MODE")
        public void setUserPreferredDisplayMode(int i, android.view.Display.Mode mode) {
            setUserPreferredDisplayMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setUserPreferredDisplayModeInternal(i, mode);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.view.Display.Mode getUserPreferredDisplayMode(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getUserPreferredDisplayModeInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.view.Display.Mode getSystemPreferredDisplayMode(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getSystemPreferredDisplayModeInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setHdrConversionMode(android.hardware.display.HdrConversionMode hdrConversionMode) {
            if (!com.android.server.display.DisplayManagerService.this.mIsHdrOutputControlEnabled) {
                return;
            }
            com.android.server.display.DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_HDR_CONVERSION_MODE", "Permission required to set the HDR conversion mode.");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setHdrConversionModeInternal(hdrConversionMode);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.display.HdrConversionMode getHdrConversionModeSetting() {
            if (!com.android.server.display.DisplayManagerService.this.mIsHdrOutputControlEnabled) {
                return com.android.server.display.DisplayManagerService.HDR_CONVERSION_MODE_UNSUPPORTED;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getHdrConversionModeSettingInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.display.HdrConversionMode getHdrConversionMode() {
            if (!com.android.server.display.DisplayManagerService.this.mIsHdrOutputControlEnabled) {
                return com.android.server.display.DisplayManagerService.HDR_CONVERSION_MODE_UNSUPPORTED;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getHdrConversionModeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int[] getSupportedHdrOutputTypes() {
            if (!com.android.server.display.DisplayManagerService.this.mIsHdrOutputControlEnabled) {
                return com.android.server.display.DisplayManagerService.EMPTY_ARRAY;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getSupportedHdrOutputTypesInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.OVERRIDE_DISPLAY_MODE_REQUESTS")
        public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
            setShouldAlwaysRespectAppRequestedMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setShouldAlwaysRespectAppRequestedModeInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.OVERRIDE_DISPLAY_MODE_REQUESTS")
        public boolean shouldAlwaysRespectAppRequestedMode() {
            shouldAlwaysRespectAppRequestedMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.shouldAlwaysRespectAppRequestedModeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MODIFY_REFRESH_RATE_SWITCHING_TYPE")
        public void setRefreshRateSwitchingType(int i) {
            setRefreshRateSwitchingType_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.DisplayManagerService.this.setRefreshRateSwitchingTypeInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getRefreshRateSwitchingType() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getRefreshRateSwitchingTypeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getDisplayDecorationSupportInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setDisplayIdToMirror(android.os.IBinder iBinder, int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (com.android.server.display.DisplayManagerService.this.mVirtualDisplayAdapter != null) {
                        com.android.server.display.VirtualDisplayAdapter virtualDisplayAdapter = com.android.server.display.DisplayManagerService.this.mVirtualDisplayAdapter;
                        if (displayLocked == null) {
                            i = -1;
                        }
                        virtualDisplayAdapter.setDisplayIdToMirror(iBinder, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.hardware.OverlayProperties getOverlaySupport() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.DisplayManagerService.this.getOverlaySupportInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_DISPLAYS")
        public void enableConnectedDisplay(int i) {
            enableConnectedDisplay_enforcePermission();
            com.android.server.display.DisplayManagerService.this.enableConnectedDisplay(i, true);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_DISPLAYS")
        public void disableConnectedDisplay(int i) {
            disableConnectedDisplay_enforcePermission();
            com.android.server.display.DisplayManagerService.this.enableConnectedDisplay(i, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidBrightness(float f) {
        return !java.lang.Float.isNaN(f) && f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f <= 1.0f;
    }

    private static boolean isValidResolution(android.graphics.Point point) {
        return point != null && point.x > 0 && point.y > 0;
    }

    private static boolean isValidRefreshRate(float f) {
        return !java.lang.Float.isNaN(f) && f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    @com.android.internal.annotations.VisibleForTesting
    void overrideSensorManager(android.hardware.SensorManager sensorManager) {
        synchronized (this.mSyncRoot) {
            this.mSensorManager = sensorManager;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class LocalService extends android.hardware.display.DisplayManagerInternal {
        LocalService() {
        }

        public void initPowerManagement(android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks, android.os.Handler handler, android.hardware.SensorManager sensorManager) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                com.android.server.display.DisplayManagerService.this.mDisplayPowerCallbacks = displayPowerCallbacks;
                com.android.server.display.DisplayManagerService.this.mSensorManager = sensorManager;
                com.android.server.display.DisplayManagerService.this.mPowerHandler = handler;
                com.android.server.display.DisplayManagerService.this.initializeDisplayPowerControllersLocked();
            }
            com.android.server.display.DisplayManagerService.this.mHandler.sendEmptyMessage(6);
        }

        public int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.companion.virtual.IVirtualDevice iVirtualDevice, android.window.DisplayWindowPolicyController displayWindowPolicyController, java.lang.String str) {
            return com.android.server.display.DisplayManagerService.this.createVirtualDisplayInternal(virtualDisplayConfig, iVirtualDisplayCallback, null, iVirtualDevice, displayWindowPolicyController, str);
        }

        public boolean requestPowerState(int i, android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z) {
            com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.DisplayGroup displayGroupLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayGroupLocked(i);
                    boolean z2 = true;
                    if (displayGroupLocked == null) {
                        return true;
                    }
                    int sizeLocked = displayGroupLocked.getSizeLocked();
                    for (int i2 = 0; i2 < sizeLocked; i2++) {
                        int idLocked = displayGroupLocked.getIdLocked(i2);
                        if ((com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(idLocked).getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked().flags & 32) == 0 && (displayPowerControllerInterface = (com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(idLocked)) != null) {
                            z2 &= displayPowerControllerInterface.requestPowerState(displayPowerRequest, z);
                        }
                    }
                    return z2;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isProximitySensorAvailable() {
            boolean isProximitySensorAvailable;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                isProximitySensorAvailable = ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).isProximitySensorAvailable();
            }
            return isProximitySensorAvailable;
        }

        public void registerDisplayGroupListener(android.hardware.display.DisplayManagerInternal.DisplayGroupListener displayGroupListener) {
            com.android.server.display.DisplayManagerService.this.mDisplayGroupListeners.add(displayGroupListener);
        }

        public void unregisterDisplayGroupListener(android.hardware.display.DisplayManagerInternal.DisplayGroupListener displayGroupListener) {
            com.android.server.display.DisplayManagerService.this.mDisplayGroupListeners.remove(displayGroupListener);
        }

        public android.window.ScreenCapture.ScreenshotHardwareBuffer systemScreenshot(int i) {
            return com.android.server.display.DisplayManagerService.this.systemScreenshotInternal(i);
        }

        public android.window.ScreenCapture.ScreenshotHardwareBuffer userScreenshot(int i) {
            return com.android.server.display.DisplayManagerService.this.userScreenshotInternal(i);
        }

        public android.view.DisplayInfo getDisplayInfo(int i) {
            return com.android.server.display.DisplayManagerService.this.getDisplayInfoInternal(i, android.os.Process.myUid());
        }

        public java.util.Set<android.view.DisplayInfo> getPossibleDisplayInfo(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    if (com.android.server.display.DisplayManagerService.this.mDeviceStateManager == null) {
                        android.util.Slog.w(com.android.server.display.DisplayManagerService.TAG, "Can't get supported states since DeviceStateManager not ready");
                        return arraySet;
                    }
                    for (int i2 : com.android.server.display.DisplayManagerService.this.mDeviceStateManager.getSupportedStateIdentifiers()) {
                        android.view.DisplayInfo displayInfoForStateLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayInfoForStateLocked(i2, i);
                        if (displayInfoForStateLocked != null) {
                            arraySet.add(displayInfoForStateLocked);
                        }
                    }
                    return arraySet;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.graphics.Point getDisplayPosition(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (displayLocked == null) {
                        return null;
                    }
                    return displayLocked.getDisplayPosition();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void registerDisplayTransactionListener(android.hardware.display.DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
            if (displayTransactionListener == null) {
                throw new java.lang.IllegalArgumentException("listener must not be null");
            }
            com.android.server.display.DisplayManagerService.this.registerDisplayTransactionListenerInternal(displayTransactionListener);
        }

        public void unregisterDisplayTransactionListener(android.hardware.display.DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
            if (displayTransactionListener == null) {
                throw new java.lang.IllegalArgumentException("listener must not be null");
            }
            com.android.server.display.DisplayManagerService.this.unregisterDisplayTransactionListenerInternal(displayTransactionListener);
        }

        public void setDisplayInfoOverrideFromWindowManager(int i, android.view.DisplayInfo displayInfo) {
            com.android.server.display.DisplayManagerService.this.setDisplayInfoOverrideFromWindowManagerInternal(i, displayInfo);
        }

        public void getNonOverrideDisplayInfo(int i, android.view.DisplayInfo displayInfo) {
            com.android.server.display.DisplayManagerService.this.getNonOverrideDisplayInfoInternal(i, displayInfo);
        }

        public void performTraversal(android.view.SurfaceControl.Transaction transaction, android.util.SparseArray<android.view.SurfaceControl.Transaction> sparseArray) {
            com.android.server.display.DisplayManagerService.this.performTraversalInternal(transaction, sparseArray);
        }

        public void setDisplayProperties(int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4) {
            com.android.server.display.DisplayManagerService.this.setDisplayPropertiesInternal(i, z, f, i2, f2, f3, z2, z3, z4);
        }

        public void setDisplayOffsets(int i, int i2, int i3) {
            com.android.server.display.DisplayManagerService.this.setDisplayOffsetsInternal(i, i2, i3);
        }

        public void setDisplayScalingDisabled(int i, boolean z) {
            com.android.server.display.DisplayManagerService.this.setDisplayScalingDisabledInternal(i, z);
        }

        public void setDisplayAccessUIDs(android.util.SparseArray<android.util.IntArray> sparseArray) {
            com.android.server.display.DisplayManagerService.this.setDisplayAccessUIDsInternal(sparseArray);
        }

        public void persistBrightnessTrackerState() {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).persistBrightnessTrackerState();
            }
        }

        public void onOverlayChanged() {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                com.android.server.display.DisplayManagerService.this.mDisplayDeviceRepo.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$LocalService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.display.DisplayDevice) obj).onOverlayChangedLocked();
                    }
                });
            }
        }

        public android.hardware.display.DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i) {
            return com.android.server.display.DisplayManagerService.this.getDisplayedContentSamplingAttributesInternal(i);
        }

        public boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3) {
            return com.android.server.display.DisplayManagerService.this.setDisplayedContentSamplingEnabledInternal(i, z, i2, i3);
        }

        public android.hardware.display.DisplayedContentSample getDisplayedContentSample(int i, long j, long j2) {
            return com.android.server.display.DisplayManagerService.this.getDisplayedContentSampleInternal(i, j, j2);
        }

        public void ignoreProximitySensorUntilChanged() {
            ((com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(0)).ignoreProximitySensorUntilChanged();
        }

        public int getRefreshRateSwitchingType() {
            return com.android.server.display.DisplayManagerService.this.getRefreshRateSwitchingTypeInternal();
        }

        public android.view.SurfaceControl.RefreshRateRange getRefreshRateForDisplayAndSensor(int i, java.lang.String str, java.lang.String str2) {
            android.hardware.SensorManager sensorManager;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                sensorManager = com.android.server.display.DisplayManagerService.this.mSensorManager;
            }
            if (sensorManager == null || com.android.server.display.utils.SensorUtils.findSensor(sensorManager, str2, str, 0) == null) {
                return null;
            }
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (displayLocked == null) {
                        return null;
                    }
                    com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked();
                    if (primaryDisplayDeviceLocked == null) {
                        return null;
                    }
                    com.android.server.display.config.SensorData proximitySensor = primaryDisplayDeviceLocked.getDisplayDeviceConfig().getProximitySensor();
                    if (proximitySensor == null || !proximitySensor.matches(str, str2)) {
                        return null;
                    }
                    return new android.view.SurfaceControl.RefreshRateRange(proximitySensor.minRefreshRate, proximitySensor.maxRefreshRate);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.util.List<android.hardware.display.DisplayManagerInternal.RefreshRateLimitation> getRefreshRateLimitations(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.DisplayDevice deviceForDisplayLocked = com.android.server.display.DisplayManagerService.this.getDeviceForDisplayLocked(i);
                    if (deviceForDisplayLocked == null) {
                        return null;
                    }
                    return deviceForDisplayLocked.getDisplayDeviceConfig().getRefreshRateLimitations();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setWindowManagerMirroring(int i, boolean z) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.DisplayDevice deviceForDisplayLocked = com.android.server.display.DisplayManagerService.this.getDeviceForDisplayLocked(i);
                    if (deviceForDisplayLocked != null) {
                        deviceForDisplayLocked.setWindowManagerMirroringLocked(z);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.graphics.Point getDisplaySurfaceDefaultSize(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.DisplayDevice deviceForDisplayLocked = com.android.server.display.DisplayManagerService.this.getDeviceForDisplayLocked(i);
                    if (deviceForDisplayLocked == null) {
                        return null;
                    }
                    return deviceForDisplayLocked.getDisplaySurfaceDefaultSizeLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onEarlyInteractivityChange(boolean z) {
            com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.onEarlyInteractivityChange(z);
        }

        public android.window.DisplayWindowPolicyController getDisplayWindowPolicyController(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    if (!com.android.server.display.DisplayManagerService.this.mDisplayWindowPolicyControllers.contains(i)) {
                        return null;
                    }
                    return (android.window.DisplayWindowPolicyController) com.android.server.display.DisplayManagerService.this.mDisplayWindowPolicyControllers.get(i).second;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getDisplayIdToMirror(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (displayLocked == null) {
                        return -1;
                    }
                    com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked();
                    int i2 = 0;
                    if (((primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().flags & 128) != 0) || primaryDisplayDeviceLocked.isWindowManagerMirroringLocked()) {
                        return -1;
                    }
                    int displayIdToMirrorLocked = primaryDisplayDeviceLocked.getDisplayIdToMirrorLocked();
                    if (com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(displayIdToMirrorLocked) != null) {
                        i2 = displayIdToMirrorLocked;
                    }
                    return i2;
                } finally {
                }
            }
        }

        public android.view.SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(int i) {
            android.os.IBinder displayToken;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                displayToken = com.android.server.display.DisplayManagerService.this.getDisplayToken(i);
                if (displayToken == null) {
                    throw new java.lang.IllegalArgumentException("Invalid displayId=" + i);
                }
            }
            return android.view.SurfaceControl.getDisplayNativePrimaries(displayToken);
        }

        public android.hardware.input.HostUsiVersion getHostUsiVersion(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (displayLocked == null) {
                        return null;
                    }
                    return displayLocked.getPrimaryDisplayDeviceLocked().getDisplayDeviceConfig().getHostUsiVersion();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.hardware.display.DisplayManagerInternal.AmbientLightSensorData getAmbientLightSensorData(int i) {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (displayLocked == null) {
                        return null;
                    }
                    com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked();
                    if (primaryDisplayDeviceLocked == null) {
                        return null;
                    }
                    com.android.server.display.config.SensorData ambientLightSensor = primaryDisplayDeviceLocked.getDisplayDeviceConfig().getAmbientLightSensor();
                    return new android.hardware.display.DisplayManagerInternal.AmbientLightSensorData(ambientLightSensor.name, ambientLightSensor.type);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.util.IntArray getDisplayGroupIds() {
            final android.util.ArraySet arraySet = new android.util.ArraySet();
            final android.util.IntArray intArray = new android.util.IntArray();
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$LocalService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.display.DisplayManagerService.LocalService.this.lambda$getDisplayGroupIds$0(arraySet, intArray, (com.android.server.display.LogicalDisplay) obj);
                    }
                });
            }
            return intArray;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getDisplayGroupIds$0(java.util.Set set, android.util.IntArray intArray, com.android.server.display.LogicalDisplay logicalDisplay) {
            int displayGroupIdFromDisplayIdLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayGroupIdFromDisplayIdLocked(logicalDisplay.getDisplayIdLocked());
            if (!set.contains(java.lang.Integer.valueOf(displayGroupIdFromDisplayIdLocked))) {
                set.add(java.lang.Integer.valueOf(displayGroupIdFromDisplayIdLocked));
                intArray.add(displayGroupIdFromDisplayIdLocked);
            }
        }

        public android.hardware.display.DisplayManagerInternal.DisplayOffloadSession registerDisplayOffloader(int i, @android.annotation.NonNull android.hardware.display.DisplayManagerInternal.DisplayOffloader displayOffloader) {
            if (!com.android.server.display.DisplayManagerService.this.mFlags.isDisplayOffloadEnabled()) {
                return null;
            }
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    com.android.server.display.LogicalDisplay displayLocked = com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
                    if (displayLocked == null) {
                        android.util.Slog.w(com.android.server.display.DisplayManagerService.TAG, "registering DisplayOffloader: LogicalDisplay for displayId=" + i + " is not found. No Op.");
                        return null;
                    }
                    com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface = (com.android.server.display.DisplayPowerControllerInterface) com.android.server.display.DisplayManagerService.this.mDisplayPowerControllers.get(displayLocked.getDisplayIdLocked());
                    if (displayPowerControllerInterface == null) {
                        android.util.Slog.w(com.android.server.display.DisplayManagerService.TAG, "setting doze state override: DisplayPowerController for displayId=" + i + " is unavailable. No Op.");
                        return null;
                    }
                    com.android.server.display.DisplayOffloadSessionImpl displayOffloadSessionImpl = new com.android.server.display.DisplayOffloadSessionImpl(displayOffloader, displayPowerControllerInterface);
                    displayLocked.setDisplayOffloadSessionLocked(displayOffloadSessionImpl);
                    displayPowerControllerInterface.setDisplayOffloadSession(displayOffloadSessionImpl);
                    return displayOffloadSessionImpl;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onPresentation(int i, boolean z) {
            com.android.server.display.DisplayManagerService.this.mExternalDisplayPolicy.onPresentation(i, z);
        }
    }

    class DesiredDisplayModeSpecsObserver implements com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecsListener {
        private final java.util.function.Consumer<com.android.server.display.LogicalDisplay> mSpecsChangedConsumer = new java.util.function.Consumer() { // from class: com.android.server.display.DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.display.DisplayManagerService.DesiredDisplayModeSpecsObserver.this.lambda$new$0((com.android.server.display.LogicalDisplay) obj);
            }
        };

        @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
        private boolean mChanged = false;

        DesiredDisplayModeSpecsObserver() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(com.android.server.display.LogicalDisplay logicalDisplay) {
            com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs = com.android.server.display.DisplayManagerService.this.mDisplayModeDirector.getDesiredDisplayModeSpecs(logicalDisplay.getDisplayIdLocked());
            com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecsLocked = logicalDisplay.getDesiredDisplayModeSpecsLocked();
            if (com.android.server.display.DisplayManagerService.DEBUG) {
                android.util.Slog.i(com.android.server.display.DisplayManagerService.TAG, "Comparing display specs: " + desiredDisplayModeSpecs + ", existing: " + desiredDisplayModeSpecsLocked);
            }
            if (!desiredDisplayModeSpecs.equals(desiredDisplayModeSpecsLocked)) {
                logicalDisplay.setDesiredDisplayModeSpecsLocked(desiredDisplayModeSpecs);
                this.mChanged = true;
            }
        }

        @Override // com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecsListener
        public void onDesiredDisplayModeSpecsChanged() {
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                try {
                    this.mChanged = false;
                    com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(this.mSpecsChangedConsumer, false);
                    if (this.mChanged) {
                        com.android.server.display.DisplayManagerService.this.scheduleTraversalLocked(false);
                        this.mChanged = false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    class DeviceStateListener implements android.hardware.devicestate.DeviceStateManager.DeviceStateCallback {
        private int mBaseState = -1;

        DeviceStateListener() {
        }

        public void onStateChanged(int i) {
            boolean z = i != this.mBaseState;
            synchronized (com.android.server.display.DisplayManagerService.this.mSyncRoot) {
                android.os.Message obtainMessage = com.android.server.display.DisplayManagerService.this.mHandler.obtainMessage(9);
                obtainMessage.arg1 = i;
                com.android.server.display.DisplayManagerService.this.mHandler.sendMessage(obtainMessage);
                com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper.setDeviceStateLocked(i, z);
            }
        }

        public void onBaseStateChanged(int i) {
            this.mBaseState = i;
        }
    }

    private class BrightnessPair {
        public float brightness;
        public float sdrBrightness;

        BrightnessPair(float f, float f2) {
            this.brightness = f;
            this.sdrBrightness = f2;
        }
    }

    private class ExternalDisplayPolicyInjector implements com.android.server.display.ExternalDisplayPolicy.Injector {
        private ExternalDisplayPolicyInjector() {
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        public void sendExternalDisplayEventLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, int i) {
            com.android.server.display.DisplayManagerService.this.sendDisplayEventLocked(logicalDisplay, i);
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.Nullable
        public android.os.IThermalService getThermalService() {
            return android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice"));
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.NonNull
        public com.android.server.display.feature.DisplayManagerFlags getFlags() {
            return com.android.server.display.DisplayManagerService.this.mFlags;
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.NonNull
        public com.android.server.display.LogicalDisplayMapper getLogicalDisplayMapper() {
            return com.android.server.display.DisplayManagerService.this.mLogicalDisplayMapper;
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.NonNull
        public com.android.server.display.DisplayManagerService.SyncRoot getSyncRoot() {
            return com.android.server.display.DisplayManagerService.this.mSyncRoot;
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.NonNull
        public com.android.server.display.notifications.DisplayNotificationManager getDisplayNotificationManager() {
            return com.android.server.display.DisplayManagerService.this.mDisplayNotificationManager;
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.NonNull
        public android.os.Handler getHandler() {
            return com.android.server.display.DisplayManagerService.this.mHandler;
        }

        @Override // com.android.server.display.ExternalDisplayPolicy.Injector
        @android.annotation.NonNull
        public com.android.server.display.ExternalDisplayStatsService getExternalDisplayStatsService() {
            return com.android.server.display.DisplayManagerService.this.mExternalDisplayStatsService;
        }
    }
}
