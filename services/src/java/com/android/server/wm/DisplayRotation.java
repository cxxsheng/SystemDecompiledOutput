package com.android.server.wm;

/* loaded from: classes3.dex */
public class DisplayRotation {
    private static final int ALLOW_ALL_ROTATIONS_DISABLED = 0;
    private static final int ALLOW_ALL_ROTATIONS_ENABLED = 1;
    private static final int ALLOW_ALL_ROTATIONS_UNDEFINED = -1;
    private static final int CAMERA_ROTATION_DISABLED = 0;
    private static final int CAMERA_ROTATION_ENABLED = 1;
    private static final int FOLDING_RECOMPUTE_CONFIG_DELAY_MS = 800;
    private static final int ROTATION_UNDEFINED = -1;
    private static final java.lang.String TAG = "WindowManager";
    public final boolean isDefaultDisplay;
    private int mAllowAllRotations;
    private final boolean mAllowRotationResolver;
    private boolean mAllowSeamlessRotationDespiteNavBarMoving;
    private int mCameraRotationMode;
    private final int mCarDockRotation;

    @android.annotation.Nullable
    private final com.android.server.wm.DisplayRotationImmersiveAppCompatPolicy mCompatPolicyForImmersiveApps;
    private final android.content.Context mContext;
    private int mCurrentAppOrientation;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final java.lang.Runnable mDefaultDisplayRotationChangedCallback;
    private boolean mDefaultFixedToUserRotation;
    private int mDeferredRotationPauseCount;
    private int mDemoHdmiRotation;
    private boolean mDemoHdmiRotationLock;
    private int mDemoRotation;
    private boolean mDemoRotationLock;
    private final int mDeskDockRotation;

    @android.annotation.NonNull
    private final com.android.server.wm.DeviceStateController mDeviceStateController;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final com.android.server.wm.DisplayPolicy mDisplayPolicy;

    @android.annotation.NonNull
    private final com.android.server.wm.DisplayRotationCoordinator mDisplayRotationCoordinator;
    private final com.android.server.wm.DisplayWindowSettings mDisplayWindowSettings;
    private int mFixedToUserRotation;

    @android.annotation.Nullable
    final com.android.server.wm.DisplayRotation.FoldController mFoldController;

    @com.android.internal.annotations.VisibleForTesting
    int mLandscapeRotation;
    private int mLastOrientation;
    int mLastSensorRotation;
    private final int mLidOpenRotation;
    private final java.lang.Object mLock;
    private com.android.server.wm.DisplayRotation.OrientationListener mOrientationListener;

    @com.android.internal.annotations.VisibleForTesting
    int mPortraitRotation;
    private boolean mRotatingSeamlessly;
    private int mRotation;
    private int mRotationChoiceShownToUserForConfirmation;
    private final com.android.server.wm.DisplayRotation.RotationHistory mRotationHistory;
    private final com.android.server.wm.DisplayRotation.RotationLockHistory mRotationLockHistory;
    private int mSeamlessRotationCount;

    @com.android.internal.annotations.VisibleForTesting
    int mSeascapeRotation;
    private final com.android.server.wm.WindowManagerService mService;
    private com.android.server.wm.DisplayRotation.SettingsObserver mSettingsObserver;
    private int mShowRotationSuggestions;
    private com.android.server.statusbar.StatusBarManagerInternal mStatusBarManagerInternal;
    private final boolean mSupportAutoRotation;
    private final com.android.server.wm.DisplayRotation.RotationAnimationPair mTmpRotationAnim;
    private final int mUndockedHdmiRotation;

    @com.android.internal.annotations.VisibleForTesting
    int mUpsideDownRotation;
    private int mUserRotation;
    private int mUserRotationAngles;
    private int mUserRotationMode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface AllowAllRotations {
    }

    private static class RotationAnimationPair {
        int mEnter;
        int mExit;

        private RotationAnimationPair() {
        }
    }

    DisplayRotation(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, android.view.DisplayAddress displayAddress, @android.annotation.NonNull com.android.server.wm.DeviceStateController deviceStateController, @android.annotation.NonNull com.android.server.wm.DisplayRotationCoordinator displayRotationCoordinator) {
        this(windowManagerService, displayContent, displayAddress, displayContent.getDisplayPolicy(), windowManagerService.mDisplayWindowSettings, windowManagerService.mContext, windowManagerService.getWindowManagerLock(), deviceStateController, displayRotationCoordinator);
    }

    @com.android.internal.annotations.VisibleForTesting
    DisplayRotation(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, android.view.DisplayAddress displayAddress, com.android.server.wm.DisplayPolicy displayPolicy, com.android.server.wm.DisplayWindowSettings displayWindowSettings, android.content.Context context, java.lang.Object obj, @android.annotation.NonNull com.android.server.wm.DeviceStateController deviceStateController, @android.annotation.NonNull com.android.server.wm.DisplayRotationCoordinator displayRotationCoordinator) {
        this.mTmpRotationAnim = new com.android.server.wm.DisplayRotation.RotationAnimationPair();
        this.mRotationHistory = new com.android.server.wm.DisplayRotation.RotationHistory();
        this.mRotationLockHistory = new com.android.server.wm.DisplayRotation.RotationLockHistory();
        this.mCurrentAppOrientation = -1;
        this.mLastOrientation = -1;
        this.mLastSensorRotation = -1;
        this.mRotationChoiceShownToUserForConfirmation = -1;
        this.mAllowAllRotations = -1;
        this.mUserRotationAngles = -1;
        this.mUserRotationMode = 0;
        this.mUserRotation = 0;
        this.mCameraRotationMode = 0;
        this.mFixedToUserRotation = 0;
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mDisplayPolicy = displayPolicy;
        this.mDisplayWindowSettings = displayWindowSettings;
        this.mContext = context;
        this.mLock = obj;
        this.mDeviceStateController = deviceStateController;
        this.isDefaultDisplay = displayContent.isDefaultDisplay;
        this.mCompatPolicyForImmersiveApps = initImmersiveAppCompatPolicy(windowManagerService, displayContent);
        this.mSupportAutoRotation = this.mContext.getResources().getBoolean(android.R.bool.config_strongAuthRequiredOnBoot);
        this.mAllowRotationResolver = this.mContext.getResources().getBoolean(android.R.bool.config_allowRotationResolver);
        this.mLidOpenRotation = readRotation(android.R.integer.config_letterboxDefaultPositionForTabletopModeReachability);
        this.mCarDockRotation = readRotation(android.R.integer.config_carDockKeepsScreenOn);
        this.mDeskDockRotation = readRotation(android.R.integer.config_defaultVibrationAmplitude);
        this.mUndockedHdmiRotation = readRotation(android.R.integer.config_storageManagerDaystoRetainDefault);
        int readDefaultDisplayRotation = readDefaultDisplayRotation(displayAddress, displayContent);
        this.mRotation = readDefaultDisplayRotation;
        this.mDisplayRotationCoordinator = displayRotationCoordinator;
        if (this.isDefaultDisplay) {
            this.mDisplayRotationCoordinator.setDefaultDisplayDefaultRotation(this.mRotation);
        }
        this.mDefaultDisplayRotationChangedCallback = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayRotation.this.updateRotationAndSendNewConfigIfChanged();
            }
        };
        if (com.android.server.wm.DisplayRotationCoordinator.isSecondaryInternalDisplay(displayContent) && this.mDeviceStateController.shouldMatchBuiltInDisplayOrientationToReverseDefaultDisplay()) {
            this.mDisplayRotationCoordinator.setDefaultDisplayRotationChangedCallback(this.mDefaultDisplayRotationChangedCallback);
        }
        if (this.isDefaultDisplay) {
            android.os.Handler handler = com.android.server.UiThread.getHandler();
            this.mOrientationListener = new com.android.server.wm.DisplayRotation.OrientationListener(this.mContext, handler, readDefaultDisplayRotation);
            this.mOrientationListener.setCurrentRotation(this.mRotation);
            this.mSettingsObserver = new com.android.server.wm.DisplayRotation.SettingsObserver(handler);
            this.mSettingsObserver.observe();
            if (this.mSupportAutoRotation && isFoldable(this.mContext)) {
                this.mFoldController = new com.android.server.wm.DisplayRotation.FoldController();
                return;
            } else {
                this.mFoldController = null;
                return;
            }
        }
        this.mFoldController = null;
    }

    private static boolean isFoldable(android.content.Context context) {
        return context.getResources().getIntArray(android.R.array.config_face_acquire_vendor_keyguard_ignorelist).length > 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.wm.DisplayRotationImmersiveAppCompatPolicy initImmersiveAppCompatPolicy(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        return com.android.server.wm.DisplayRotationImmersiveAppCompatPolicy.createIfNeeded(windowManagerService.mLetterboxConfiguration, this, displayContent);
    }

    private int readDefaultDisplayRotation(android.view.DisplayAddress displayAddress, com.android.server.wm.DisplayContent displayContent) {
        java.lang.String str;
        if (!(displayAddress instanceof android.view.DisplayAddress.Physical)) {
            str = "";
        } else {
            str = android.os.SystemProperties.get("ro.bootanim.set_orientation_" + ((android.view.DisplayAddress.Physical) displayAddress).getPhysicalDisplayId(), "");
        }
        if ("".equals(str) && displayContent.isDefaultDisplay) {
            str = android.os.SystemProperties.get("ro.bootanim.set_orientation_logical_" + displayContent.getDisplayId(), "");
        }
        if (str.equals("ORIENTATION_90")) {
            return 1;
        }
        if (str.equals("ORIENTATION_180")) {
            return 2;
        }
        if (str.equals("ORIENTATION_270")) {
            return 3;
        }
        return 0;
    }

    private int readRotation(int i) {
        try {
            switch (this.mContext.getResources().getInteger(i)) {
                case 0:
                    return 0;
                case 90:
                    return 1;
                case 180:
                    return 2;
                case android.companion.virtualcamera.SensorOrientation.ORIENTATION_270 /* 270 */:
                    return 3;
                default:
                    return -1;
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            return -1;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean useDefaultSettingsProvider() {
        return this.isDefaultDisplay;
    }

    void updateUserDependentConfiguration(android.content.res.Resources resources) {
        this.mAllowSeamlessRotationDespiteNavBarMoving = resources.getBoolean(android.R.bool.config_allowSeamlessRotationDespiteNavBarMoving);
    }

    void configure(int i, int i2) {
        android.content.res.Resources resources = this.mContext.getResources();
        if (i > i2) {
            this.mLandscapeRotation = 0;
            this.mSeascapeRotation = 2;
            if (resources.getBoolean(android.R.bool.config_requireCallCapableAccountForHandle)) {
                this.mPortraitRotation = 1;
                this.mUpsideDownRotation = 3;
            } else {
                this.mPortraitRotation = 3;
                this.mUpsideDownRotation = 1;
            }
        } else {
            this.mPortraitRotation = 0;
            this.mUpsideDownRotation = 2;
            if (resources.getBoolean(android.R.bool.config_requireCallCapableAccountForHandle)) {
                this.mLandscapeRotation = 3;
                this.mSeascapeRotation = 1;
            } else {
                this.mLandscapeRotation = 1;
                this.mSeascapeRotation = 3;
            }
        }
        if ("portrait".equals(android.os.SystemProperties.get("persist.demo.hdmirotation"))) {
            this.mDemoHdmiRotation = this.mPortraitRotation;
        } else {
            this.mDemoHdmiRotation = this.mLandscapeRotation;
        }
        this.mDemoHdmiRotationLock = android.os.SystemProperties.getBoolean("persist.demo.hdmirotationlock", false);
        if ("portrait".equals(android.os.SystemProperties.get("persist.demo.remoterotation"))) {
            this.mDemoRotation = this.mPortraitRotation;
        } else {
            this.mDemoRotation = this.mLandscapeRotation;
        }
        this.mDemoRotationLock = android.os.SystemProperties.getBoolean("persist.demo.rotationlock", false);
        this.mDefaultFixedToUserRotation = (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive") || this.mContext.getPackageManager().hasSystemFeature("android.software.leanback") || this.mService.mIsPc || this.mDisplayContent.forceDesktopMode() || !this.mDisplayContent.shouldRotateWithContent()) && !"true".equals(android.os.SystemProperties.get("config.override_forced_orient"));
    }

    void applyCurrentRotation(int i) {
        this.mRotationHistory.addRecord(this, i);
        if (this.mOrientationListener != null) {
            this.mOrientationListener.setCurrentRotation(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setRotation(int i) {
        this.mRotation = i;
    }

    int getRotation() {
        return this.mRotation;
    }

    int getLastOrientation() {
        return this.mLastOrientation;
    }

    boolean updateOrientation(int i, boolean z) {
        if (i == this.mLastOrientation && !z) {
            return false;
        }
        this.mLastOrientation = i;
        if (i != this.mCurrentAppOrientation) {
            this.mCurrentAppOrientation = i;
            if (this.isDefaultDisplay) {
                updateOrientationListenerLw();
            }
        }
        return updateRotationUnchecked(z);
    }

    boolean updateRotationAndSendNewConfigIfChanged() {
        boolean updateRotationUnchecked = updateRotationUnchecked(false);
        if (updateRotationUnchecked) {
            this.mDisplayContent.sendNewConfiguration();
        }
        return updateRotationUnchecked;
    }

    boolean updateRotationUnchecked(boolean z) {
        int displayId = this.mDisplayContent.getDisplayId();
        if (!z) {
            if (this.mDeferredRotationPauseCount > 0) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -6776561147903919733L, 0, null, null);
                return false;
            }
            if (this.mDisplayContent.inTransition() && this.mDisplayContent.getDisplayPolicy().isScreenOnFully() && !this.mDisplayContent.mTransitionController.useShellTransitionsRotation()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7439675997626642740L, 0, null, null);
                return false;
            }
            if (this.mService.mDisplayFrozen) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 1104181226551849840L, 0, null, null);
                return false;
            }
            if (this.mDisplayContent.mFixedRotationTransitionListener.shouldDeferRotation()) {
                this.mLastOrientation = -2;
                return false;
            }
        }
        if (!this.mService.mDisplayEnabled) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -2222079183499215612L, 0, null, null);
            return false;
        }
        int i = this.mRotation;
        int i2 = this.mLastOrientation;
        int rotationForOrientation = rotationForOrientation(i2, i);
        if (this.mFoldController != null && this.mFoldController.shouldRevertOverriddenRotation()) {
            int revertOverriddenRotation = this.mFoldController.revertOverriddenRotation();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 662988298513100908L, 0, null, java.lang.String.valueOf(android.view.Surface.rotationToString(revertOverriddenRotation)), java.lang.String.valueOf(android.view.Surface.rotationToString(i)), java.lang.String.valueOf(android.view.Surface.rotationToString(rotationForOrientation)));
            rotationForOrientation = revertOverriddenRotation;
        }
        if (com.android.server.wm.DisplayRotationCoordinator.isSecondaryInternalDisplay(this.mDisplayContent) && this.mDeviceStateController.shouldMatchBuiltInDisplayOrientationToReverseDefaultDisplay()) {
            rotationForOrientation = android.util.RotationUtils.reverseRotationDirectionAroundZAxis(this.mDisplayRotationCoordinator.getDefaultDisplayCurrentRotation());
        }
        long j = rotationForOrientation;
        long j2 = displayId;
        long j3 = i2;
        long j4 = i;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -7113483678655694375L, 4372, null, java.lang.String.valueOf(android.view.Surface.rotationToString(rotationForOrientation)), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.String.valueOf(android.content.pm.ActivityInfo.screenOrientationToString(i2)), java.lang.Long.valueOf(j3), java.lang.String.valueOf(android.view.Surface.rotationToString(i)), java.lang.Long.valueOf(j4));
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -8809129029906317617L, 273, null, java.lang.Long.valueOf(j2), java.lang.String.valueOf(android.content.pm.ActivityInfo.screenOrientationToString(i2)), java.lang.Long.valueOf(j3), java.lang.String.valueOf(android.view.Surface.rotationToString(rotationForOrientation)), java.lang.Long.valueOf(j));
        if (i == rotationForOrientation) {
            return false;
        }
        if (this.isDefaultDisplay) {
            this.mDisplayRotationCoordinator.onDefaultDisplayRotationChanged(rotationForOrientation);
        }
        com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
        if (recentsAnimationController != null) {
            recentsAnimationController.cancelAnimationForDisplayChange();
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 6753221849083491323L, 85, null, java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j3));
        this.mRotation = rotationForOrientation;
        this.mDisplayContent.setLayoutNeeded();
        this.mDisplayContent.mWaitingForConfig = true;
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            boolean isCollecting = this.mDisplayContent.mTransitionController.isCollecting();
            this.mDisplayContent.requestChangeTransitionIfNeeded(536870912, isCollecting ? null : new android.window.TransitionRequestInfo.DisplayChange(this.mDisplayContent.getDisplayId(), i, this.mRotation));
            if (isCollecting) {
                startRemoteRotation(i, this.mRotation);
            }
            return true;
        }
        this.mService.mWindowsFreezingScreen = 1;
        this.mService.mH.sendNewMessageDelayed(11, this.mDisplayContent, 2000L);
        if (shouldRotateSeamlessly(i, rotationForOrientation, z)) {
            prepareSeamlessRotation();
        } else {
            prepareNormalRotationAnimation();
        }
        startRemoteRotation(i, this.mRotation);
        return true;
    }

    private void startRemoteRotation(int i, final int i2) {
        this.mDisplayContent.mRemoteDisplayChangeController.performRemoteDisplayChange(i, i2, null, new com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda1
            @Override // com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
            public final void onContinueRemoteDisplayChange(android.window.WindowContainerTransaction windowContainerTransaction) {
                com.android.server.wm.DisplayRotation.this.lambda$startRemoteRotation$0(i2, windowContainerTransaction);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: continueRotation, reason: merged with bridge method [inline-methods] */
    public void lambda$startRemoteRotation$0(int i, android.window.WindowContainerTransaction windowContainerTransaction) {
        if (i != this.mRotation) {
            return;
        }
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            if (!this.mDisplayContent.mTransitionController.isCollecting()) {
                android.util.Slog.e(TAG, "Trying to continue rotation outside a transition");
            }
            this.mDisplayContent.mTransitionController.collect(this.mDisplayContent);
        }
        this.mService.mAtmService.deferWindowLayout();
        try {
            this.mDisplayContent.sendNewConfiguration();
            if (windowContainerTransaction != null) {
                this.mService.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
            }
        } finally {
            this.mService.mAtmService.continueWindowLayout();
        }
    }

    void prepareNormalRotationAnimation() {
        cancelSeamlessRotation();
        com.android.server.wm.DisplayRotation.RotationAnimationPair selectRotationAnimation = selectRotationAnimation();
        this.mService.startFreezingDisplay(selectRotationAnimation.mExit, selectRotationAnimation.mEnter, this.mDisplayContent);
    }

    void cancelSeamlessRotation() {
        if (!this.mRotatingSeamlessly) {
            return;
        }
        this.mDisplayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayRotation.lambda$cancelSeamlessRotation$1((com.android.server.wm.WindowState) obj);
            }
        }, true);
        this.mSeamlessRotationCount = 0;
        this.mRotatingSeamlessly = false;
        this.mDisplayContent.finishAsyncRotationIfPossible();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cancelSeamlessRotation$1(com.android.server.wm.WindowState windowState) {
        if (windowState.mSeamlesslyRotated) {
            windowState.cancelSeamlessRotation();
            windowState.mSeamlesslyRotated = false;
        }
    }

    private void prepareSeamlessRotation() {
        this.mSeamlessRotationCount = 0;
        this.mRotatingSeamlessly = true;
    }

    boolean isRotatingSeamlessly() {
        return this.mRotatingSeamlessly;
    }

    boolean hasSeamlessRotatingWindow() {
        return this.mSeamlessRotationCount > 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldRotateSeamlessly(int i, int i2, boolean z) {
        if (this.mDisplayContent.hasTopFixedRotationLaunchingApp()) {
            return true;
        }
        com.android.server.wm.WindowState topFullscreenOpaqueWindow = this.mDisplayPolicy.getTopFullscreenOpaqueWindow();
        if (topFullscreenOpaqueWindow == null || topFullscreenOpaqueWindow != this.mDisplayContent.mCurrentFocus || topFullscreenOpaqueWindow.getAttrs().rotationAnimation != 3 || topFullscreenOpaqueWindow.inMultiWindowMode() || topFullscreenOpaqueWindow.isAnimatingLw() || !canRotateSeamlessly(i, i2)) {
            return false;
        }
        if ((topFullscreenOpaqueWindow.mActivityRecord != null && !topFullscreenOpaqueWindow.mActivityRecord.matchParentBounds()) || this.mDisplayContent.getDefaultTaskDisplayArea().hasPinnedTask() || this.mDisplayContent.hasAlertWindowSurfaces()) {
            return false;
        }
        return z || this.mDisplayContent.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean z2;
                z2 = ((com.android.server.wm.WindowState) obj).mSeamlesslyRotated;
                return z2;
            }
        }) == null;
    }

    boolean canRotateSeamlessly(int i, int i2) {
        if (this.mAllowSeamlessRotationDespiteNavBarMoving || this.mDisplayPolicy.navigationBarCanMove()) {
            return true;
        }
        return (i == 2 || i2 == 2) ? false : true;
    }

    void markForSeamlessRotation(com.android.server.wm.WindowState windowState, boolean z) {
        if (z == windowState.mSeamlesslyRotated || windowState.mForceSeamlesslyRotate) {
            return;
        }
        windowState.mSeamlesslyRotated = z;
        if (z) {
            this.mSeamlessRotationCount++;
        } else {
            this.mSeamlessRotationCount--;
        }
        if (this.mSeamlessRotationCount == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -1216224951455892544L, 0, null, null);
            this.mRotatingSeamlessly = false;
            this.mDisplayContent.finishAsyncRotationIfPossible();
            updateRotationAndSendNewConfigIfChanged();
        }
    }

    private com.android.server.wm.DisplayRotation.RotationAnimationPair selectRotationAnimation() {
        boolean z = (this.mDisplayPolicy.isScreenOnFully() && this.mService.mPolicy.okToAnimate(false)) ? false : true;
        com.android.server.wm.WindowState topFullscreenOpaqueWindow = this.mDisplayPolicy.getTopFullscreenOpaqueWindow();
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -7672508047849737424L, 52, null, java.lang.String.valueOf(topFullscreenOpaqueWindow), java.lang.Long.valueOf(topFullscreenOpaqueWindow == null ? 0L : topFullscreenOpaqueWindow.getAttrs().rotationAnimation), java.lang.Boolean.valueOf(z));
        if (z) {
            this.mTmpRotationAnim.mExit = android.R.anim.rotation_animation_jump_exit;
            this.mTmpRotationAnim.mEnter = android.R.anim.rotation_animation_enter;
            return this.mTmpRotationAnim;
        }
        if (topFullscreenOpaqueWindow != null) {
            int rotationAnimationHint = topFullscreenOpaqueWindow.getRotationAnimationHint();
            if (rotationAnimationHint < 0 && this.mDisplayPolicy.isTopLayoutFullscreen()) {
                rotationAnimationHint = topFullscreenOpaqueWindow.getAttrs().rotationAnimation;
            }
            switch (rotationAnimationHint) {
                case 1:
                case 3:
                    this.mTmpRotationAnim.mExit = android.R.anim.rotation_animation_xfade_exit;
                    this.mTmpRotationAnim.mEnter = android.R.anim.rotation_animation_enter;
                    break;
                case 2:
                    this.mTmpRotationAnim.mExit = android.R.anim.rotation_animation_jump_exit;
                    this.mTmpRotationAnim.mEnter = android.R.anim.rotation_animation_enter;
                    break;
                default:
                    com.android.server.wm.DisplayRotation.RotationAnimationPair rotationAnimationPair = this.mTmpRotationAnim;
                    this.mTmpRotationAnim.mEnter = 0;
                    rotationAnimationPair.mExit = 0;
                    break;
            }
        } else {
            com.android.server.wm.DisplayRotation.RotationAnimationPair rotationAnimationPair2 = this.mTmpRotationAnim;
            this.mTmpRotationAnim.mEnter = 0;
            rotationAnimationPair2.mExit = 0;
        }
        return this.mTmpRotationAnim;
    }

    boolean validateRotationAnimation(int i, int i2, boolean z) {
        switch (i) {
            case android.R.anim.rotation_animation_jump_exit:
            case android.R.anim.rotation_animation_xfade_exit:
                if (z) {
                    return false;
                }
                com.android.server.wm.DisplayRotation.RotationAnimationPair selectRotationAnimation = selectRotationAnimation();
                if (i == selectRotationAnimation.mExit && i2 == selectRotationAnimation.mEnter) {
                    return true;
                }
                return false;
            default:
                return true;
        }
    }

    void restoreSettings(int i, int i2, int i3) {
        this.mFixedToUserRotation = i3;
        if (this.isDefaultDisplay) {
            return;
        }
        if (i != 0 && i != 1) {
            android.util.Slog.w(TAG, "Trying to restore an invalid user rotation mode " + i + " for " + this.mDisplayContent);
            i = 0;
        }
        if (i2 < 0 || i2 > 3) {
            android.util.Slog.w(TAG, "Trying to restore an invalid user rotation " + i2 + " for " + this.mDisplayContent);
            i2 = 0;
        }
        this.mUserRotationMode = i;
        this.mUserRotation = i2;
    }

    void setFixedToUserRotation(int i) {
        if (this.mFixedToUserRotation == i) {
            return;
        }
        this.mFixedToUserRotation = i;
        this.mDisplayWindowSettings.setFixedToUserRotation(this.mDisplayContent, i);
        if (this.mDisplayContent.mFocusedApp != null) {
            this.mDisplayContent.onLastFocusedTaskDisplayAreaChanged(this.mDisplayContent.mFocusedApp.getDisplayArea());
        }
        this.mDisplayContent.updateOrientation();
    }

    @com.android.internal.annotations.VisibleForTesting
    void setUserRotation(int i, int i2, java.lang.String str) {
        boolean z;
        this.mRotationLockHistory.addRecord(i, i2, str);
        this.mRotationChoiceShownToUserForConfirmation = -1;
        boolean z2 = true;
        if (useDefaultSettingsProvider()) {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            android.provider.Settings.System.putIntForUser(contentResolver, "accelerometer_rotation", i != 1 ? 1 : 0, -2);
            android.provider.Settings.System.putIntForUser(contentResolver, "user_rotation", i2, -2);
            return;
        }
        if (this.mUserRotationMode == i) {
            z = false;
        } else {
            this.mUserRotationMode = i;
            z = true;
        }
        if (this.mUserRotation == i2) {
            z2 = z;
        } else {
            this.mUserRotation = i2;
        }
        this.mDisplayWindowSettings.setUserRotation(this.mDisplayContent, i, i2);
        if (z2) {
            this.mService.updateRotation(false, false);
        }
    }

    void freezeRotation(int i, java.lang.String str) {
        if (this.mDeviceStateController.shouldReverseRotationDirectionAroundZAxis(this.mDisplayContent)) {
            i = android.util.RotationUtils.reverseRotationDirectionAroundZAxis(i);
        }
        if (i == -1) {
            i = this.mRotation;
        }
        setUserRotation(1, i, str);
    }

    void thawRotation(java.lang.String str) {
        setUserRotation(0, this.mUserRotation, str);
    }

    boolean isRotationFrozen() {
        return !this.isDefaultDisplay ? this.mUserRotationMode == 1 : android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), "accelerometer_rotation", 0, -2) == 0;
    }

    boolean isFixedToUserRotation() {
        switch (this.mFixedToUserRotation) {
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                return false;
            default:
                return this.mDefaultFixedToUserRotation;
        }
    }

    int getFixedToUserRotationMode() {
        return this.mFixedToUserRotation;
    }

    public int getLandscapeRotation() {
        return this.mLandscapeRotation;
    }

    public int getSeascapeRotation() {
        return this.mSeascapeRotation;
    }

    public int getPortraitRotation() {
        return this.mPortraitRotation;
    }

    public int getUpsideDownRotation() {
        return this.mUpsideDownRotation;
    }

    public int getCurrentAppOrientation() {
        return this.mCurrentAppOrientation;
    }

    public com.android.server.wm.DisplayPolicy getDisplayPolicy() {
        return this.mDisplayPolicy;
    }

    public com.android.server.wm.WindowOrientationListener getOrientationListener() {
        return this.mOrientationListener;
    }

    public int getUserRotation() {
        return this.mUserRotation;
    }

    public int getUserRotationMode() {
        return this.mUserRotationMode;
    }

    public void updateOrientationListener() {
        synchronized (this.mLock) {
            updateOrientationListenerLw();
        }
    }

    void pause() {
        this.mDeferredRotationPauseCount++;
    }

    void resume() {
        if (this.mDeferredRotationPauseCount <= 0) {
            return;
        }
        this.mDeferredRotationPauseCount--;
        if (this.mDeferredRotationPauseCount == 0) {
            updateRotationAndSendNewConfigIfChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOrientationListenerLw() {
        boolean z;
        if (this.mOrientationListener == null || !this.mOrientationListener.canDetectOrientation()) {
            return;
        }
        boolean isScreenOnEarly = this.mDisplayPolicy.isScreenOnEarly();
        boolean isAwake = this.mDisplayPolicy.isAwake();
        boolean isKeyguardDrawComplete = this.mDisplayPolicy.isKeyguardDrawComplete();
        boolean isWindowManagerDrawComplete = this.mDisplayPolicy.isWindowManagerDrawComplete();
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -2426404033822048710L, 4063, null, java.lang.Boolean.valueOf(isScreenOnEarly), java.lang.Boolean.valueOf(isAwake), java.lang.Long.valueOf(this.mCurrentAppOrientation), java.lang.Boolean.valueOf(this.mOrientationListener.mEnabled), java.lang.Boolean.valueOf(isKeyguardDrawComplete), java.lang.Boolean.valueOf(isWindowManagerDrawComplete));
        if (isScreenOnEarly && ((isAwake || this.mOrientationListener.shouldStayEnabledWhileDreaming()) && isKeyguardDrawComplete && isWindowManagerDrawComplete && needSensorRunning())) {
            if (!this.mOrientationListener.mEnabled) {
                this.mOrientationListener.enable();
            }
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mOrientationListener.disable();
        }
    }

    private boolean needSensorRunning() {
        if (isFixedToUserRotation()) {
            return false;
        }
        if (this.mFoldController != null && this.mFoldController.shouldDisableRotationSensor()) {
            return false;
        }
        if (this.mSupportAutoRotation && (this.mCurrentAppOrientation == 4 || this.mCurrentAppOrientation == 10 || this.mCurrentAppOrientation == 7 || this.mCurrentAppOrientation == 6)) {
            return true;
        }
        int dockMode = this.mDisplayPolicy.getDockMode();
        if ((this.mDisplayPolicy.isCarDockEnablesAccelerometer() && dockMode == 2) || (this.mDisplayPolicy.isDeskDockEnablesAccelerometer() && (dockMode == 1 || dockMode == 3 || dockMode == 4))) {
            return true;
        }
        if (this.mUserRotationMode == 1) {
            return this.mSupportAutoRotation && this.mShowRotationSuggestions == 1;
        }
        return this.mSupportAutoRotation;
    }

    boolean needsUpdate() {
        int i = this.mRotation;
        return i != rotationForOrientation(this.mLastOrientation, i);
    }

    void resetAllowAllRotations() {
        this.mAllowAllRotations = -1;
    }

    @com.android.internal.annotations.VisibleForTesting
    int rotationForOrientation(int i, int i2) {
        int i3;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7339471241580327852L, 1092, null, java.lang.String.valueOf(android.content.pm.ActivityInfo.screenOrientationToString(i)), java.lang.Long.valueOf(i), java.lang.String.valueOf(android.view.Surface.rotationToString(i2)), java.lang.Long.valueOf(i2), java.lang.String.valueOf(android.view.Surface.rotationToString(this.mUserRotation)), java.lang.Long.valueOf(this.mUserRotation), java.lang.String.valueOf(this.mUserRotationMode == 1 ? "USER_ROTATION_LOCKED" : ""));
        if (isFixedToUserRotation()) {
            return this.mUserRotation;
        }
        int i4 = -1;
        if (this.mOrientationListener != null) {
            i3 = this.mOrientationListener.getProposedRotation();
        } else {
            i3 = -1;
        }
        if (this.mFoldController != null && this.mFoldController.shouldIgnoreSensorRotation()) {
            i3 = -1;
        }
        if (this.mDeviceStateController.shouldReverseRotationDirectionAroundZAxis(this.mDisplayContent)) {
            i3 = android.util.RotationUtils.reverseRotationDirectionAroundZAxis(i3);
        }
        this.mLastSensorRotation = i3;
        if (i3 < 0) {
            i3 = i2;
        }
        int lidState = this.mDisplayPolicy.getLidState();
        int dockMode = this.mDisplayPolicy.getDockMode();
        boolean isHdmiPlugged = this.mDisplayPolicy.isHdmiPlugged();
        boolean isCarDockEnablesAccelerometer = this.mDisplayPolicy.isCarDockEnablesAccelerometer();
        boolean isDeskDockEnablesAccelerometer = this.mDisplayPolicy.isDeskDockEnablesAccelerometer();
        if (!this.isDefaultDisplay) {
            i4 = this.mUserRotation;
        } else if (lidState == 1 && this.mLidOpenRotation >= 0) {
            i4 = this.mLidOpenRotation;
        } else if (dockMode != 2 || (!isCarDockEnablesAccelerometer && this.mCarDockRotation < 0)) {
            if ((dockMode == 1 || dockMode == 3 || dockMode == 4) && ((isDeskDockEnablesAccelerometer || this.mDeskDockRotation >= 0) && i != 14 && i != 5)) {
                if (!isDeskDockEnablesAccelerometer) {
                    i3 = this.mDeskDockRotation;
                }
                i4 = i3;
            } else if (isHdmiPlugged && this.mDemoHdmiRotationLock) {
                i4 = this.mDemoHdmiRotation;
            } else if (isHdmiPlugged && dockMode == 0 && this.mUndockedHdmiRotation >= 0) {
                i4 = this.mUndockedHdmiRotation;
            } else if (this.mDemoRotationLock) {
                i4 = this.mDemoRotation;
            } else if (this.mDisplayPolicy.isPersistentVrModeEnabled()) {
                i4 = this.mPortraitRotation;
            } else if (i == 14) {
                i4 = i2;
            } else if (!this.mSupportAutoRotation) {
                if (this.mFixedToUserRotation == 3) {
                    i4 = this.mUserRotation;
                }
            } else if (((this.mUserRotationMode == 0 || isTabletopAutoRotateOverrideEnabled()) && (i == 2 || i == -1 || i == 11 || i == 12 || i == 13)) || i == 4 || i == 10 || i == 6 || i == 7) {
                if (i != 10 && i != 13) {
                    r13 = com.android.internal.view.RotationPolicy.isRotationAllowed(i3, this.mUserRotationAngles, getAllowAllRotations() != 0);
                }
                if (r13) {
                    i4 = i3;
                } else {
                    i4 = i2;
                }
            } else if (this.mUserRotationMode == 1 && i != 5 && i != 0 && i != 1 && i != 8 && i != 9) {
                i4 = this.mUserRotation;
            }
        } else {
            if (!isCarDockEnablesAccelerometer) {
                i3 = this.mCarDockRotation;
            }
            i4 = i3;
        }
        switch (i) {
            case 0:
                if (isLandscapeOrSeascape(i4)) {
                    return i4;
                }
                return this.mLandscapeRotation;
            case 1:
                if (isAnyPortrait(i4)) {
                    return i4;
                }
                return this.mPortraitRotation;
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            default:
                if (i4 >= 0) {
                    return i4;
                }
                return 0;
            case 6:
            case 11:
                if (isLandscapeOrSeascape(i4)) {
                    return i4;
                }
                return isLandscapeOrSeascape(i2) ? i2 : this.mLandscapeRotation;
            case 7:
            case 12:
                if (isAnyPortrait(i4)) {
                    return i4;
                }
                return isAnyPortrait(i2) ? i2 : this.mPortraitRotation;
            case 8:
                if (isLandscapeOrSeascape(i4)) {
                    return i4;
                }
                return this.mSeascapeRotation;
            case 9:
                if (isAnyPortrait(i4)) {
                    return i4;
                }
                return this.mUpsideDownRotation;
        }
    }

    private int getAllowAllRotations() {
        int i;
        if (this.mAllowAllRotations == -1) {
            if (this.mContext.getResources().getBoolean(android.R.bool.config_allowAllRotations)) {
                i = 1;
            } else {
                i = 0;
            }
            this.mAllowAllRotations = i;
        }
        return this.mAllowAllRotations;
    }

    boolean isLandscapeOrSeascape(int i) {
        return i == this.mLandscapeRotation || i == this.mSeascapeRotation;
    }

    boolean isAnyPortrait(int i) {
        return i == this.mPortraitRotation || i == this.mUpsideDownRotation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidRotationChoice(int i) {
        switch (this.mCurrentAppOrientation) {
            case -1:
            case 2:
                if (getAllowAllRotations() == 1) {
                    if (i < 0) {
                        break;
                    }
                } else if (i < 0 || i == 2) {
                    break;
                }
                break;
            case 11:
                break;
            case 12:
                if (i != this.mPortraitRotation) {
                    break;
                }
                break;
            case 13:
                if (i < 0) {
                    break;
                }
                break;
        }
        return false;
    }

    private boolean isTabletopAutoRotateOverrideEnabled() {
        return this.mFoldController != null && this.mFoldController.overrideFrozenRotation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isRotationChoiceAllowed(int i) {
        int dockMode;
        if ((!(this.mCompatPolicyForImmersiveApps != null && this.mCompatPolicyForImmersiveApps.isRotationLockEnforced(i)) && this.mUserRotationMode != 1) || isTabletopAutoRotateOverrideEnabled() || isFixedToUserRotation()) {
            return false;
        }
        if ((this.mDisplayPolicy.getLidState() == 1 && this.mLidOpenRotation >= 0) || (dockMode = this.mDisplayPolicy.getDockMode()) == 2) {
            return false;
        }
        boolean isDeskDockEnablesAccelerometer = this.mDisplayPolicy.isDeskDockEnablesAccelerometer();
        if ((dockMode == 1 || dockMode == 3 || dockMode == 4) && !isDeskDockEnablesAccelerometer) {
            return false;
        }
        boolean isHdmiPlugged = this.mDisplayPolicy.isHdmiPlugged();
        if (isHdmiPlugged && this.mDemoHdmiRotationLock) {
            return false;
        }
        if ((isHdmiPlugged && dockMode == 0 && this.mUndockedHdmiRotation >= 0) || this.mDemoRotationLock || this.mDisplayPolicy.isPersistentVrModeEnabled() || !this.mSupportAutoRotation) {
            return false;
        }
        switch (this.mCurrentAppOrientation) {
            case -1:
            case 2:
            case 11:
            case 12:
            case 13:
                break;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendProposedRotationChangeToStatusBarInternal(int i, boolean z) {
        if (this.mStatusBarManagerInternal == null) {
            this.mStatusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
        }
        if (this.mStatusBarManagerInternal != null) {
            this.mStatusBarManagerInternal.onProposedRotationChanged(i, z);
        }
    }

    void dispatchProposedRotation(int i) {
        if (this.mService.mRotationWatcherController.hasProposedRotationListeners()) {
            synchronized (this.mLock) {
                this.mService.mRotationWatcherController.dispatchProposedRotation(this.mDisplayContent, i);
            }
        }
    }

    private static java.lang.String allowAllRotationsToString(int i) {
        switch (i) {
            case -1:
                return "unknown";
            case 0:
                return "false";
            case 1:
                return "true";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public void onUserSwitch() {
        if (this.mSettingsObserver != null) {
            this.mSettingsObserver.onChange(false);
        }
    }

    void onDisplayRemoved() {
        removeDefaultDisplayRotationChangedCallback();
        if (this.mFoldController != null) {
            this.mFoldController.onDisplayRemoved();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateSettings() {
        boolean z;
        int intForUser;
        boolean z2;
        boolean z3;
        int i;
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        synchronized (this.mLock) {
            try {
                z = true;
                if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                    intForUser = 0;
                } else {
                    intForUser = android.provider.Settings.Secure.getIntForUser(contentResolver, "show_rotation_suggestions", 1, -2);
                }
                if (this.mShowRotationSuggestions == intForUser) {
                    z2 = false;
                } else {
                    this.mShowRotationSuggestions = intForUser;
                    z2 = true;
                }
                int intForUser2 = android.provider.Settings.System.getIntForUser(contentResolver, "user_rotation", 0, -2);
                if (this.mUserRotation == intForUser2) {
                    z3 = false;
                } else {
                    this.mUserRotation = intForUser2;
                    z3 = true;
                }
                int intForUser3 = android.provider.Settings.System.getIntForUser(contentResolver, "accelerometer_rotation_angles", -1, -2);
                if (this.mUserRotationAngles != intForUser3) {
                    this.mUserRotationAngles = intForUser3;
                    z3 = true;
                }
                if (android.provider.Settings.System.getIntForUser(contentResolver, "accelerometer_rotation", 0, -2) != 0) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (this.mUserRotationMode != i) {
                    this.mUserRotationMode = i;
                    z2 = true;
                    z3 = true;
                }
                if (z2) {
                    updateOrientationListenerLw();
                }
                int intForUser4 = android.provider.Settings.Secure.getIntForUser(contentResolver, "camera_autorotate", 0, -2);
                if (this.mCameraRotationMode == intForUser4) {
                    z = z3;
                } else {
                    this.mCameraRotationMode = intForUser4;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    void removeDefaultDisplayRotationChangedCallback() {
        if (com.android.server.wm.DisplayRotationCoordinator.isSecondaryInternalDisplay(this.mDisplayContent)) {
            this.mDisplayRotationCoordinator.removeDefaultDisplayRotationChangedCallback();
        }
    }

    void onSetRequestedOrientation() {
        if (this.mCompatPolicyForImmersiveApps == null || this.mRotationChoiceShownToUserForConfirmation == -1) {
            return;
        }
        this.mOrientationListener.onProposedRotationChanged(this.mRotationChoiceShownToUserForConfirmation);
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "DisplayRotation");
        printWriter.println(str + "  mCurrentAppOrientation=" + android.content.pm.ActivityInfo.screenOrientationToString(this.mCurrentAppOrientation));
        printWriter.println(str + "  mLastOrientation=" + this.mLastOrientation);
        printWriter.print(str + "  mRotation=" + this.mRotation);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(" mDeferredRotationPauseCount=");
        sb.append(this.mDeferredRotationPauseCount);
        printWriter.println(sb.toString());
        printWriter.print(str + "  mLandscapeRotation=" + android.view.Surface.rotationToString(this.mLandscapeRotation));
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(" mSeascapeRotation=");
        sb2.append(android.view.Surface.rotationToString(this.mSeascapeRotation));
        printWriter.println(sb2.toString());
        printWriter.print(str + "  mPortraitRotation=" + android.view.Surface.rotationToString(this.mPortraitRotation));
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        sb3.append(" mUpsideDownRotation=");
        sb3.append(android.view.Surface.rotationToString(this.mUpsideDownRotation));
        printWriter.println(sb3.toString());
        printWriter.println(str + "  mSupportAutoRotation=" + this.mSupportAutoRotation);
        if (this.mOrientationListener != null) {
            this.mOrientationListener.dump(printWriter, str + "  ");
        }
        printWriter.println();
        printWriter.print(str + "  mCarDockRotation=" + android.view.Surface.rotationToString(this.mCarDockRotation));
        java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
        sb4.append(" mDeskDockRotation=");
        sb4.append(android.view.Surface.rotationToString(this.mDeskDockRotation));
        printWriter.println(sb4.toString());
        printWriter.print(str + "  mUserRotationMode=" + com.android.server.policy.WindowManagerPolicy.userRotationModeToString(this.mUserRotationMode));
        java.lang.StringBuilder sb5 = new java.lang.StringBuilder();
        sb5.append(" mUserRotation=");
        sb5.append(android.view.Surface.rotationToString(this.mUserRotation));
        printWriter.print(sb5.toString());
        printWriter.print(" mCameraRotationMode=" + this.mCameraRotationMode);
        printWriter.println(" mAllowAllRotations=" + allowAllRotationsToString(this.mAllowAllRotations));
        printWriter.print(str + "  mDemoHdmiRotation=" + android.view.Surface.rotationToString(this.mDemoHdmiRotation));
        java.lang.StringBuilder sb6 = new java.lang.StringBuilder();
        sb6.append(" mDemoHdmiRotationLock=");
        sb6.append(this.mDemoHdmiRotationLock);
        printWriter.print(sb6.toString());
        printWriter.println(" mUndockedHdmiRotation=" + android.view.Surface.rotationToString(this.mUndockedHdmiRotation));
        printWriter.println(str + "  mLidOpenRotation=" + android.view.Surface.rotationToString(this.mLidOpenRotation));
        printWriter.println(str + "  mFixedToUserRotation=" + isFixedToUserRotation());
        if (this.mFoldController != null) {
            printWriter.println(str + "FoldController");
            printWriter.println(str + "  mPauseAutorotationDuringUnfolding=" + this.mFoldController.mPauseAutorotationDuringUnfolding);
            printWriter.println(str + "  mShouldDisableRotationSensor=" + this.mFoldController.mShouldDisableRotationSensor);
            printWriter.println(str + "  mShouldIgnoreSensorRotation=" + this.mFoldController.mShouldIgnoreSensorRotation);
            printWriter.println(str + "  mLastDisplaySwitchTime=" + this.mFoldController.mLastDisplaySwitchTime);
            printWriter.println(str + "  mLastHingeAngleEventTime=" + this.mFoldController.mLastHingeAngleEventTime);
            printWriter.println(str + "  mDeviceState=" + this.mFoldController.mDeviceState);
        }
        if (!this.mRotationHistory.mRecords.isEmpty()) {
            printWriter.println();
            printWriter.println(str + "  RotationHistory");
            str = "    " + str;
            java.util.Iterator<com.android.server.wm.DisplayRotation.RotationHistory.Record> it = this.mRotationHistory.mRecords.iterator();
            while (it.hasNext()) {
                it.next().dump(str, printWriter);
            }
        }
        if (!this.mRotationLockHistory.mRecords.isEmpty()) {
            printWriter.println();
            printWriter.println(str + "  RotationLockHistory");
            java.lang.String str2 = "    " + str;
            java.util.Iterator it2 = this.mRotationLockHistory.mRecords.iterator();
            while (it2.hasNext()) {
                ((com.android.server.wm.DisplayRotation.RotationLockHistory.Record) it2.next()).dump(str2, printWriter);
            }
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, getRotation());
        protoOutputStream.write(1133871366146L, isRotationFrozen());
        protoOutputStream.write(1120986464259L, getUserRotation());
        protoOutputStream.write(1120986464260L, this.mFixedToUserRotation);
        protoOutputStream.write(1120986464261L, this.mLastOrientation);
        protoOutputStream.write(1133871366150L, isFixedToUserRotation());
        protoOutputStream.end(start);
    }

    boolean isDeviceInPosture(com.android.server.wm.DeviceStateController.DeviceState deviceState, boolean z) {
        if (this.mFoldController == null) {
            return false;
        }
        return this.mFoldController.isDeviceInPosture(deviceState, z);
    }

    boolean isDisplaySeparatingHinge() {
        return this.mFoldController != null && this.mFoldController.isSeparatingHinge();
    }

    void foldStateChanged(com.android.server.wm.DeviceStateController.DeviceState deviceState) {
        if (this.mFoldController != null) {
            synchronized (this.mLock) {
                this.mFoldController.foldStateChanged(deviceState);
            }
        }
    }

    void physicalDisplayChanged() {
        if (this.mFoldController != null) {
            this.mFoldController.onPhysicalDisplayChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long uptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }

    class FoldController {
        private final java.lang.Runnable mActivityBoundsUpdateCallback;
        private final boolean mAllowHalfFoldAutoRotationOverride;
        private int mDisplaySwitchRotationBlockTimeMs;
        private int mHingeAngleRotationBlockTimeMs;
        private android.hardware.SensorEventListener mHingeAngleSensorEventListener;
        private final boolean mIsDisplayAlwaysSeparatingHinge;
        private int mMaxHingeAngle;
        private final boolean mPauseAutorotationDuringUnfolding;
        private android.hardware.SensorManager mSensorManager;
        private boolean mShouldDisableRotationSensor;
        private boolean mShouldIgnoreSensorRotation;
        private int mHalfFoldSavedRotation = -1;
        private com.android.server.wm.DeviceStateController.DeviceState mDeviceState = com.android.server.wm.DeviceStateController.DeviceState.UNKNOWN;
        private long mLastHingeAngleEventTime = 0;
        private long mLastDisplaySwitchTime = 0;
        private boolean mInHalfFoldTransition = false;
        private final java.util.Set<java.lang.Integer> mTabletopRotations = new android.util.ArraySet();

        FoldController() {
            this.mAllowHalfFoldAutoRotationOverride = com.android.server.wm.DisplayRotation.this.mContext.getResources().getBoolean(android.R.bool.config_wimaxEnabled);
            int[] intArray = com.android.server.wm.DisplayRotation.this.mContext.getResources().getIntArray(android.R.array.config_deviceStatesOnWhichToWakeUp);
            if (intArray != null) {
                for (int i : intArray) {
                    switch (i) {
                        case 0:
                            this.mTabletopRotations.add(0);
                            break;
                        case 90:
                            this.mTabletopRotations.add(1);
                            break;
                        case 180:
                            this.mTabletopRotations.add(2);
                            break;
                        case android.companion.virtualcamera.SensorOrientation.ORIENTATION_270 /* 270 */:
                            this.mTabletopRotations.add(3);
                            break;
                        default:
                            com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 5325136615007859122L, 1, null, java.lang.Long.valueOf(i));
                            break;
                    }
                }
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 4616480353797749295L, 0, null, null);
            }
            this.mIsDisplayAlwaysSeparatingHinge = com.android.server.wm.DisplayRotation.this.mContext.getResources().getBoolean(android.R.bool.config_imeDrawsImeNavBar);
            this.mActivityBoundsUpdateCallback = new com.android.server.wm.DisplayRotation.FoldController.AnonymousClass1(com.android.server.wm.DisplayRotation.this);
            this.mPauseAutorotationDuringUnfolding = com.android.server.wm.DisplayRotation.this.mContext.getResources().getBoolean(android.R.bool.config_windowActionBarSupported);
            if (this.mPauseAutorotationDuringUnfolding) {
                this.mDisplaySwitchRotationBlockTimeMs = com.android.server.wm.DisplayRotation.this.mContext.getResources().getInteger(android.R.integer.config_ntpTimeout);
                this.mHingeAngleRotationBlockTimeMs = com.android.server.wm.DisplayRotation.this.mContext.getResources().getInteger(android.R.integer.config_num_physical_slots);
                this.mMaxHingeAngle = com.android.server.wm.DisplayRotation.this.mContext.getResources().getInteger(android.R.integer.config_oem_enabled_satellite_location_fresh_duration);
                registerSensorManager();
            }
        }

        /* renamed from: com.android.server.wm.DisplayRotation$FoldController$1, reason: invalid class name */
        class AnonymousClass1 implements java.lang.Runnable {
            final /* synthetic */ com.android.server.wm.DisplayRotation val$this$0;

            AnonymousClass1(com.android.server.wm.DisplayRotation displayRotation) {
                this.val$this$0 = displayRotation;
            }

            @Override // java.lang.Runnable
            public void run() {
                com.android.server.wm.ActivityRecord activityRecord;
                if (com.android.server.wm.DisplayRotation.FoldController.this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.OPEN || com.android.server.wm.DisplayRotation.FoldController.this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED) {
                    synchronized (com.android.server.wm.DisplayRotation.this.mLock) {
                        try {
                            com.android.server.wm.Task task = com.android.server.wm.DisplayRotation.this.mDisplayContent.getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayRotation$FoldController$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(java.lang.Object obj) {
                                    boolean lambda$run$0;
                                    lambda$run$0 = com.android.server.wm.DisplayRotation.FoldController.AnonymousClass1.lambda$run$0((com.android.server.wm.Task) obj);
                                    return lambda$run$0;
                                }
                            });
                            if (task != null && (activityRecord = task.topRunningActivity()) != null) {
                                activityRecord.recomputeConfiguration();
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ boolean lambda$run$0(com.android.server.wm.Task task) {
                return task.getWindowingMode() == 1;
            }
        }

        private void registerSensorManager() {
            android.hardware.Sensor defaultSensor;
            this.mSensorManager = (android.hardware.SensorManager) com.android.server.wm.DisplayRotation.this.mContext.getSystemService(android.hardware.SensorManager.class);
            if (this.mSensorManager != null && (defaultSensor = this.mSensorManager.getDefaultSensor(36)) != null) {
                this.mHingeAngleSensorEventListener = new android.hardware.SensorEventListener() { // from class: com.android.server.wm.DisplayRotation.FoldController.2
                    @Override // android.hardware.SensorEventListener
                    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
                        com.android.server.wm.DisplayRotation.FoldController.this.onHingeAngleChanged(sensorEvent.values[0]);
                    }

                    @Override // android.hardware.SensorEventListener
                    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
                    }
                };
                this.mSensorManager.registerListener(this.mHingeAngleSensorEventListener, defaultSensor, 0, com.android.server.wm.DisplayRotation.this.getHandler());
            }
        }

        void onDisplayRemoved() {
            if (this.mSensorManager != null && this.mHingeAngleSensorEventListener != null) {
                this.mSensorManager.unregisterListener(this.mHingeAngleSensorEventListener);
            }
        }

        boolean isDeviceInPosture(com.android.server.wm.DeviceStateController.DeviceState deviceState, boolean z) {
            if (deviceState != this.mDeviceState) {
                return false;
            }
            return this.mDeviceState != com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED || z == this.mTabletopRotations.contains(java.lang.Integer.valueOf(com.android.server.wm.DisplayRotation.this.mRotation));
        }

        com.android.server.wm.DeviceStateController.DeviceState getFoldState() {
            return this.mDeviceState;
        }

        boolean isSeparatingHinge() {
            return this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED || (this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.OPEN && this.mIsDisplayAlwaysSeparatingHinge);
        }

        boolean overrideFrozenRotation() {
            return this.mAllowHalfFoldAutoRotationOverride && this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED;
        }

        boolean shouldRevertOverriddenRotation() {
            return this.mAllowHalfFoldAutoRotationOverride && this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.OPEN && !this.mShouldIgnoreSensorRotation && this.mInHalfFoldTransition && com.android.server.wm.DisplayRotation.this.mDisplayContent.getRotationReversionController().isOverrideActive(2) && com.android.server.wm.DisplayRotation.this.mUserRotationMode == 1;
        }

        int revertOverriddenRotation() {
            int i = this.mHalfFoldSavedRotation;
            this.mHalfFoldSavedRotation = -1;
            com.android.server.wm.DisplayRotation.this.mDisplayContent.getRotationReversionController().revertOverride(2);
            this.mInHalfFoldTransition = false;
            return i;
        }

        void foldStateChanged(com.android.server.wm.DeviceStateController.DeviceState deviceState) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 8852346340572084230L, 5457, null, java.lang.Long.valueOf(com.android.server.wm.DisplayRotation.this.mDisplayContent.getDisplayId()), java.lang.String.valueOf(deviceState.name()), java.lang.Long.valueOf(this.mHalfFoldSavedRotation), java.lang.Long.valueOf(com.android.server.wm.DisplayRotation.this.mUserRotation), java.lang.Long.valueOf(com.android.server.wm.DisplayRotation.this.mLastSensorRotation), java.lang.Long.valueOf(com.android.server.wm.DisplayRotation.this.mLastOrientation), java.lang.Long.valueOf(com.android.server.wm.DisplayRotation.this.mRotation));
            if (this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.UNKNOWN) {
                this.mDeviceState = deviceState;
                return;
            }
            if (deviceState != com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED || this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED) {
                this.mInHalfFoldTransition = true;
                this.mDeviceState = deviceState;
                com.android.server.wm.DisplayRotation.this.mService.updateRotation(false, false);
            } else {
                com.android.server.wm.DisplayRotation.this.mDisplayContent.getRotationReversionController().beforeOverrideApplied(2);
                this.mHalfFoldSavedRotation = com.android.server.wm.DisplayRotation.this.mRotation;
                this.mDeviceState = deviceState;
                com.android.server.wm.DisplayRotation.this.mService.updateRotation(false, false);
            }
            com.android.server.UiThread.getHandler().removeCallbacks(this.mActivityBoundsUpdateCallback);
            com.android.server.UiThread.getHandler().postDelayed(this.mActivityBoundsUpdateCallback, 800L);
        }

        boolean shouldIgnoreSensorRotation() {
            return this.mShouldIgnoreSensorRotation;
        }

        boolean shouldDisableRotationSensor() {
            return this.mShouldDisableRotationSensor;
        }

        private void updateSensorRotationBlockIfNeeded() {
            long uptimeMillis = com.android.server.wm.DisplayRotation.this.uptimeMillis();
            boolean z = uptimeMillis - this.mLastDisplaySwitchTime < ((long) this.mDisplaySwitchRotationBlockTimeMs) || uptimeMillis - this.mLastHingeAngleEventTime < ((long) this.mHingeAngleRotationBlockTimeMs);
            if (z != this.mShouldIgnoreSensorRotation) {
                this.mShouldIgnoreSensorRotation = z;
                if (!this.mShouldIgnoreSensorRotation) {
                    if (this.mShouldDisableRotationSensor) {
                        this.mShouldDisableRotationSensor = false;
                        com.android.server.wm.DisplayRotation.this.updateOrientationListenerLw();
                    } else {
                        com.android.server.wm.DisplayRotation.this.updateRotationAndSendNewConfigIfChanged();
                    }
                }
            }
        }

        void onPhysicalDisplayChanged() {
            if (this.mPauseAutorotationDuringUnfolding) {
                this.mLastDisplaySwitchTime = com.android.server.wm.DisplayRotation.this.uptimeMillis();
                if (this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.OPEN || this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED) {
                    this.mShouldDisableRotationSensor = true;
                    com.android.server.wm.DisplayRotation.this.updateOrientationListenerLw();
                }
                updateSensorRotationBlockIfNeeded();
                com.android.server.wm.DisplayRotation.this.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotation$FoldController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.DisplayRotation.FoldController.this.lambda$onPhysicalDisplayChanged$0();
                    }
                }, this.mDisplaySwitchRotationBlockTimeMs);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhysicalDisplayChanged$0() {
            synchronized (com.android.server.wm.DisplayRotation.this.mLock) {
                updateSensorRotationBlockIfNeeded();
            }
        }

        void onHingeAngleChanged(float f) {
            if (f < this.mMaxHingeAngle) {
                this.mLastHingeAngleEventTime = com.android.server.wm.DisplayRotation.this.uptimeMillis();
                updateSensorRotationBlockIfNeeded();
                com.android.server.wm.DisplayRotation.this.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotation$FoldController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.DisplayRotation.FoldController.this.lambda$onHingeAngleChanged$1();
                    }
                }, this.mHingeAngleRotationBlockTimeMs);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHingeAngleChanged$1() {
            synchronized (com.android.server.wm.DisplayRotation.this.mLock) {
                updateSensorRotationBlockIfNeeded();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandler() {
        return this.mService.mH;
    }

    private class OrientationListener extends com.android.server.wm.WindowOrientationListener implements java.lang.Runnable {
        transient boolean mEnabled;

        OrientationListener(android.content.Context context, android.os.Handler handler, int i) {
            super(context, handler, i);
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public boolean isKeyguardShowingAndNotOccluded() {
            return com.android.server.wm.DisplayRotation.this.mService.isKeyguardShowingAndNotOccluded();
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public boolean isRotationResolverEnabled() {
            return com.android.server.wm.DisplayRotation.this.mAllowRotationResolver && com.android.server.wm.DisplayRotation.this.mUserRotationMode == 0 && com.android.server.wm.DisplayRotation.this.mCameraRotationMode == 1 && !com.android.server.wm.DisplayRotation.this.mService.mPowerManager.isPowerSaveMode();
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public void onProposedRotationChanged(int i) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -8674269704471038429L, 1, null, java.lang.Long.valueOf(i));
            com.android.server.wm.DisplayRotation.this.mService.mPowerManagerInternal.setPowerBoost(0, 0);
            com.android.server.wm.DisplayRotation.this.dispatchProposedRotation(i);
            if (com.android.server.wm.DisplayRotation.this.isRotationChoiceAllowed(i)) {
                com.android.server.wm.DisplayRotation.this.mRotationChoiceShownToUserForConfirmation = i;
                com.android.server.wm.DisplayRotation.this.sendProposedRotationChangeToStatusBarInternal(i, com.android.server.wm.DisplayRotation.this.isValidRotationChoice(i));
            } else {
                com.android.server.wm.DisplayRotation.this.mRotationChoiceShownToUserForConfirmation = -1;
                com.android.server.wm.DisplayRotation.this.mService.updateRotation(false, false);
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public void enable() {
            this.mEnabled = true;
            getHandler().post(this);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 418312772547457152L, 0, null, null);
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public void disable() {
            this.mEnabled = false;
            getHandler().post(this);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 4641814558273780952L, 0, null, null);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mEnabled) {
                super.enable();
            } else {
                super.disable();
            }
        }
    }

    private class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        void observe() {
            android.content.ContentResolver contentResolver = com.android.server.wm.DisplayRotation.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("show_rotation_suggestions"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("accelerometer_rotation"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("accelerometer_rotation_angles"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("user_rotation"), false, this, -1);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("camera_autorotate"), false, this, -1);
            com.android.server.wm.DisplayRotation.this.updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (com.android.server.wm.DisplayRotation.this.updateSettings()) {
                com.android.server.wm.DisplayRotation.this.mService.updateRotation(false, false);
            }
        }
    }

    private static class RotationLockHistory {
        private static final int MAX_SIZE = 8;
        private final java.util.ArrayDeque<com.android.server.wm.DisplayRotation.RotationLockHistory.Record> mRecords;

        private RotationLockHistory() {
            this.mRecords = new java.util.ArrayDeque<>(8);
        }

        private static class Record {
            final java.lang.String mCaller;
            final long mTimestamp;
            final int mUserRotation;
            final int mUserRotationMode;

            private Record(int i, int i2, java.lang.String str) {
                this.mTimestamp = java.lang.System.currentTimeMillis();
                this.mUserRotationMode = i;
                this.mUserRotation = i2;
                this.mCaller = str;
            }

            void dump(java.lang.String str, java.io.PrintWriter printWriter) {
                printWriter.println(str + android.util.TimeUtils.logTimeOfDay(this.mTimestamp) + ": mode=" + com.android.server.policy.WindowManagerPolicy.userRotationModeToString(this.mUserRotationMode) + ", rotation=" + android.view.Surface.rotationToString(this.mUserRotation) + ", caller=" + this.mCaller);
            }
        }

        void addRecord(int i, int i2, java.lang.String str) {
            if (this.mRecords.size() >= 8) {
                this.mRecords.removeFirst();
            }
            this.mRecords.addLast(new com.android.server.wm.DisplayRotation.RotationLockHistory.Record(i, i2, str));
        }
    }

    private static class RotationHistory {
        private static final int MAX_SIZE = 8;
        private static final int NO_FOLD_CONTROLLER = -2;
        final java.util.ArrayDeque<com.android.server.wm.DisplayRotation.RotationHistory.Record> mRecords;

        private RotationHistory() {
            this.mRecords = new java.util.ArrayDeque<>(8);
        }

        private static class Record {
            final com.android.server.wm.DeviceStateController.DeviceState mDeviceState;

            @android.annotation.Nullable
            final java.lang.String mDisplayRotationCompatPolicySummary;
            final int mFromRotation;
            final int mHalfFoldSavedRotation;
            final boolean mIgnoreOrientationRequest;
            final boolean mInHalfFoldTransition;
            final java.lang.String mLastOrientationSource;
            final java.lang.String mNonDefaultRequestingTaskDisplayArea;

            @android.annotation.Nullable
            final boolean[] mRotationReversionSlots;
            final int mSensorRotation;
            final int mSourceOrientation;
            final long mTimestamp = java.lang.System.currentTimeMillis();
            final int mToRotation;
            final int mUserRotation;
            final int mUserRotationMode;

            Record(com.android.server.wm.DisplayRotation displayRotation, int i, int i2) {
                int i3;
                java.lang.String str;
                int overrideOrientation;
                this.mFromRotation = i;
                this.mToRotation = i2;
                this.mUserRotation = displayRotation.mUserRotation;
                this.mUserRotationMode = displayRotation.mUserRotationMode;
                com.android.server.wm.DisplayRotation.OrientationListener orientationListener = displayRotation.mOrientationListener;
                if (orientationListener == null || !orientationListener.mEnabled) {
                    i3 = -2;
                } else {
                    i3 = displayRotation.mLastSensorRotation;
                }
                this.mSensorRotation = i3;
                com.android.server.wm.DisplayContent displayContent = displayRotation.mDisplayContent;
                this.mIgnoreOrientationRequest = displayContent.getIgnoreOrientationRequest();
                com.android.server.wm.TaskDisplayArea orientationRequestingTaskDisplayArea = displayContent.getOrientationRequestingTaskDisplayArea();
                if (orientationRequestingTaskDisplayArea == null) {
                    str = "none";
                } else if (orientationRequestingTaskDisplayArea == displayContent.getDefaultTaskDisplayArea()) {
                    str = null;
                } else {
                    str = orientationRequestingTaskDisplayArea.toString();
                }
                this.mNonDefaultRequestingTaskDisplayArea = str;
                com.android.server.wm.WindowContainer lastOrientationSource = displayContent.getLastOrientationSource();
                if (lastOrientationSource != null) {
                    this.mLastOrientationSource = lastOrientationSource.toString();
                    com.android.server.wm.WindowState asWindowState = lastOrientationSource.asWindowState();
                    if (asWindowState != null) {
                        overrideOrientation = asWindowState.mAttrs.screenOrientation;
                    } else {
                        overrideOrientation = lastOrientationSource.getOverrideOrientation();
                    }
                    this.mSourceOrientation = overrideOrientation;
                } else {
                    this.mLastOrientationSource = null;
                    this.mSourceOrientation = -2;
                }
                if (displayRotation.mFoldController != null) {
                    this.mHalfFoldSavedRotation = displayRotation.mFoldController.mHalfFoldSavedRotation;
                    this.mInHalfFoldTransition = displayRotation.mFoldController.mInHalfFoldTransition;
                    this.mDeviceState = displayRotation.mFoldController.mDeviceState;
                } else {
                    this.mHalfFoldSavedRotation = -2;
                    this.mInHalfFoldTransition = false;
                    this.mDeviceState = com.android.server.wm.DeviceStateController.DeviceState.UNKNOWN;
                }
                this.mDisplayRotationCompatPolicySummary = displayContent.mDisplayRotationCompatPolicy != null ? displayContent.mDisplayRotationCompatPolicy.getSummaryForDisplayRotationHistoryRecord() : null;
                this.mRotationReversionSlots = displayRotation.mDisplayContent.getRotationReversionController().getSlotsCopy();
            }

            void dump(java.lang.String str, java.io.PrintWriter printWriter) {
                printWriter.println(str + android.util.TimeUtils.logTimeOfDay(this.mTimestamp) + " " + android.view.Surface.rotationToString(this.mFromRotation) + " to " + android.view.Surface.rotationToString(this.mToRotation));
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(str);
                sb.append("  source=");
                sb.append(this.mLastOrientationSource);
                sb.append(" ");
                sb.append(android.content.pm.ActivityInfo.screenOrientationToString(this.mSourceOrientation));
                printWriter.println(sb.toString());
                printWriter.println(str + "  mode=" + com.android.server.policy.WindowManagerPolicy.userRotationModeToString(this.mUserRotationMode) + " user=" + android.view.Surface.rotationToString(this.mUserRotation) + " sensor=" + android.view.Surface.rotationToString(this.mSensorRotation));
                if (this.mIgnoreOrientationRequest) {
                    printWriter.println(str + "  ignoreRequest=true");
                }
                if (this.mNonDefaultRequestingTaskDisplayArea != null) {
                    printWriter.println(str + "  requestingTda=" + this.mNonDefaultRequestingTaskDisplayArea);
                }
                if (this.mHalfFoldSavedRotation != -2) {
                    printWriter.println(str + " halfFoldSavedRotation=" + this.mHalfFoldSavedRotation + " mInHalfFoldTransition=" + this.mInHalfFoldTransition + " mFoldState=" + this.mDeviceState);
                }
                if (this.mDisplayRotationCompatPolicySummary != null) {
                    printWriter.println(str + this.mDisplayRotationCompatPolicySummary);
                }
                if (this.mRotationReversionSlots != null) {
                    printWriter.println(str + " reversionSlots= NOSENSOR " + this.mRotationReversionSlots[0] + ", CAMERA " + this.mRotationReversionSlots[1] + " HALF_FOLD " + this.mRotationReversionSlots[2]);
                }
            }
        }

        void addRecord(com.android.server.wm.DisplayRotation displayRotation, int i) {
            if (this.mRecords.size() >= 8) {
                this.mRecords.removeFirst();
            }
            this.mRecords.addLast(new com.android.server.wm.DisplayRotation.RotationHistory.Record(displayRotation, displayRotation.mDisplayContent.getWindowConfiguration().getRotation(), i));
        }
    }
}
