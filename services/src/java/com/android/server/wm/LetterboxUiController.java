package com.android.server.wm;

/* loaded from: classes3.dex */
final class LetterboxUiController {
    private static final java.util.function.Predicate<com.android.server.wm.ActivityRecord> FIRST_OPAQUE_NOT_FINISHING_ACTIVITY_PREDICATE = new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda11();

    @com.android.internal.annotations.VisibleForTesting
    static final int MIN_COUNT_TO_IGNORE_REQUEST_IN_LOOP = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int SET_ORIENTATION_REQUEST_COUNTER_TIMEOUT_MS = 1000;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final float UNDEFINED_ASPECT_RATIO = 0.0f;
    private final com.android.server.wm.ActivityRecord mActivityRecord;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowDisplayOrientationOverride;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowForceResizeOverride;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowIgnoringOrientationRequestWhenLoopDetected;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowMinAspectRatioOverride;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowOrientationOverride;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowUserAspectRatioFullscreenOverride;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyAllowUserAspectRatioOverride;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyCameraCompatAllowForceRotation;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyCameraCompatAllowRefresh;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyCameraCompatEnableRefreshViaPause;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyFakeFocus;

    @android.annotation.Nullable
    private final java.lang.Boolean mBooleanPropertyIgnoreRequestedOrientation;
    private boolean mDoubleTapEvent;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord mFirstOpaqueActivityBeneath;
    private com.android.server.wm.ActivityRecord.CompatDisplayInsets mInheritedCompatDisplayInsets;
    private final boolean mIsOverrideAnyOrientationEnabled;
    private final boolean mIsOverrideCameraCompatDisableForceRotationEnabled;
    private final boolean mIsOverrideCameraCompatDisableRefreshEnabled;
    private final boolean mIsOverrideCameraCompatEnableRefreshViaPauseEnabled;
    private final boolean mIsOverrideEnableCompatFakeFocusEnabled;
    private final boolean mIsOverrideEnableCompatIgnoreOrientationRequestWhenLoopDetectedEnabled;
    private final boolean mIsOverrideEnableCompatIgnoreRequestedOrientationEnabled;
    private final boolean mIsOverrideForceNonResizeApp;
    private final boolean mIsOverrideForceResizeApp;
    private final boolean mIsOverrideMinAspectRatio;
    private final boolean mIsOverrideOrientationOnlyForCameraEnabled;
    private final boolean mIsOverrideRespectRequestedOrientationEnabled;
    private final boolean mIsOverrideToNosensorOrientationEnabled;
    private final boolean mIsOverrideToPortraitOrientationEnabled;
    private final boolean mIsOverrideToReverseLandscapeOrientationEnabled;
    private final boolean mIsOverrideUseDisplayLandscapeNaturalOrientationEnabled;
    private boolean mIsRefreshAfterRotationRequested;
    private boolean mIsRelaunchingAfterRequestedOrientationChanged;
    private final boolean mIsSystemOverrideToFullscreenEnabled;
    private boolean mLastShouldShowLetterboxUi;

    @android.annotation.Nullable
    private com.android.server.wm.Letterbox mLetterbox;

    @android.annotation.Nullable
    private com.android.server.wm.WindowContainerListener mLetterboxConfigListener;
    private final com.android.server.wm.LetterboxConfiguration mLetterboxConfiguration;
    private boolean mShowWallpaperForLetterboxBackground;
    private final android.graphics.Point mTmpPoint = new android.graphics.Point();
    private final java.util.List<com.android.server.wm.LetterboxUiController> mDestroyListeners = new java.util.ArrayList();
    private float mInheritedMinAspectRatio = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mInheritedMaxAspectRatio = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private long mTimeMsLastSetOrientationRequest = 0;
    private int mInheritedOrientation = 0;
    private int mInheritedAppCompatState = 0;
    private int mSetOrientationRequestCounter = 0;
    private int mUserAspectRatio = 0;

    LetterboxUiController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.ActivityRecord activityRecord) {
        this.mLetterboxConfiguration = windowManagerService.mLetterboxConfiguration;
        this.mActivityRecord = activityRecord;
        android.content.pm.PackageManager packageManager = windowManagerService.mContext.getPackageManager();
        java.lang.String str = this.mActivityRecord.packageName;
        com.android.server.wm.LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
        java.util.Objects.requireNonNull(letterboxConfiguration);
        this.mBooleanPropertyIgnoreRequestedOrientation = readComponentProperty(packageManager, str, new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda2(letterboxConfiguration), "android.window.PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION");
        java.lang.String str2 = this.mActivityRecord.packageName;
        com.android.server.wm.LetterboxConfiguration letterboxConfiguration2 = this.mLetterboxConfiguration;
        java.util.Objects.requireNonNull(letterboxConfiguration2);
        this.mBooleanPropertyAllowIgnoringOrientationRequestWhenLoopDetected = readComponentProperty(packageManager, str2, new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda2(letterboxConfiguration2), "android.window.PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED");
        java.lang.String str3 = this.mActivityRecord.packageName;
        com.android.server.wm.LetterboxConfiguration letterboxConfiguration3 = this.mLetterboxConfiguration;
        java.util.Objects.requireNonNull(letterboxConfiguration3);
        this.mBooleanPropertyFakeFocus = readComponentProperty(packageManager, str3, new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda3(letterboxConfiguration3), "android.window.PROPERTY_COMPAT_ENABLE_FAKE_FOCUS");
        this.mBooleanPropertyCameraCompatAllowForceRotation = readComponentProperty(packageManager, this.mActivityRecord.packageName, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda4
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.wm.LetterboxUiController.this.lambda$new$0();
                return lambda$new$0;
            }
        }, "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION");
        this.mBooleanPropertyCameraCompatAllowRefresh = readComponentProperty(packageManager, this.mActivityRecord.packageName, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$new$1;
                lambda$new$1 = com.android.server.wm.LetterboxUiController.this.lambda$new$1();
                return lambda$new$1;
            }
        }, "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH");
        this.mBooleanPropertyCameraCompatEnableRefreshViaPause = readComponentProperty(packageManager, this.mActivityRecord.packageName, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda6
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$new$2;
                lambda$new$2 = com.android.server.wm.LetterboxUiController.this.lambda$new$2();
                return lambda$new$2;
            }
        }, "android.window.PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE");
        this.mBooleanPropertyAllowOrientationOverride = readComponentProperty(packageManager, this.mActivityRecord.packageName, null, "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE");
        this.mBooleanPropertyAllowDisplayOrientationOverride = readComponentProperty(packageManager, this.mActivityRecord.packageName, null, "android.window.PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE");
        this.mBooleanPropertyAllowMinAspectRatioOverride = readComponentProperty(packageManager, this.mActivityRecord.packageName, null, "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE");
        this.mBooleanPropertyAllowForceResizeOverride = readComponentProperty(packageManager, this.mActivityRecord.packageName, null, "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES");
        this.mBooleanPropertyAllowUserAspectRatioOverride = readComponentProperty(packageManager, this.mActivityRecord.packageName, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda7
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$new$3;
                lambda$new$3 = com.android.server.wm.LetterboxUiController.this.lambda$new$3();
                return lambda$new$3;
            }
        }, "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_OVERRIDE");
        this.mBooleanPropertyAllowUserAspectRatioFullscreenOverride = readComponentProperty(packageManager, this.mActivityRecord.packageName, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda8
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$new$4;
                lambda$new$4 = com.android.server.wm.LetterboxUiController.this.lambda$new$4();
                return lambda$new$4;
            }
        }, "android.window.PROPERTY_COMPAT_ALLOW_USER_ASPECT_RATIO_FULLSCREEN_OVERRIDE");
        this.mIsOverrideAnyOrientationEnabled = isCompatChangeEnabled(265464455L);
        this.mIsSystemOverrideToFullscreenEnabled = isCompatChangeEnabled(310816437L);
        this.mIsOverrideToPortraitOrientationEnabled = isCompatChangeEnabled(265452344L);
        this.mIsOverrideToReverseLandscapeOrientationEnabled = isCompatChangeEnabled(266124927L);
        this.mIsOverrideToNosensorOrientationEnabled = isCompatChangeEnabled(265451093L);
        this.mIsOverrideOrientationOnlyForCameraEnabled = isCompatChangeEnabled(265456536L);
        this.mIsOverrideUseDisplayLandscapeNaturalOrientationEnabled = isCompatChangeEnabled(255940284L);
        this.mIsOverrideRespectRequestedOrientationEnabled = isCompatChangeEnabled(236283604L);
        this.mIsOverrideCameraCompatDisableForceRotationEnabled = isCompatChangeEnabled(263959004L);
        this.mIsOverrideCameraCompatDisableRefreshEnabled = isCompatChangeEnabled(264304459L);
        this.mIsOverrideCameraCompatEnableRefreshViaPauseEnabled = isCompatChangeEnabled(264301586L);
        this.mIsOverrideEnableCompatIgnoreRequestedOrientationEnabled = isCompatChangeEnabled(254631730L);
        this.mIsOverrideEnableCompatIgnoreOrientationRequestWhenLoopDetectedEnabled = isCompatChangeEnabled(273509367L);
        this.mIsOverrideEnableCompatFakeFocusEnabled = isCompatChangeEnabled(263259275L);
        this.mIsOverrideMinAspectRatio = isCompatChangeEnabled(174042980L);
        this.mIsOverrideForceResizeApp = isCompatChangeEnabled(174042936L);
        this.mIsOverrideForceNonResizeApp = isCompatChangeEnabled(181136395L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1() {
        return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$2() {
        return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$3() {
        return this.mLetterboxConfiguration.isUserAppAspectRatioSettingsEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$4() {
        return this.mLetterboxConfiguration.isUserAppAspectRatioFullscreenEnabled();
    }

    @android.annotation.Nullable
    private static java.lang.Boolean readComponentProperty(android.content.pm.PackageManager packageManager, java.lang.String str, @android.annotation.Nullable java.util.function.BooleanSupplier booleanSupplier, java.lang.String str2) {
        if (booleanSupplier != null && !booleanSupplier.getAsBoolean()) {
            return null;
        }
        try {
            return java.lang.Boolean.valueOf(packageManager.getProperty(str2, str).getBoolean());
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    void destroy() {
        if (this.mLetterbox != null) {
            this.mLetterbox.destroy();
            this.mLetterbox = null;
        }
        for (int size = this.mDestroyListeners.size() - 1; size >= 0; size--) {
            this.mDestroyListeners.get(size).updateInheritedLetterbox();
        }
        this.mDestroyListeners.clear();
        if (this.mLetterboxConfigListener != null) {
            this.mLetterboxConfigListener.onRemoved();
            this.mLetterboxConfigListener = null;
        }
    }

    void onMovedToDisplay(int i) {
        if (this.mLetterbox != null) {
            this.mLetterbox.onMovedToDisplay(i);
        }
    }

    boolean shouldIgnoreRequestedOrientation(int i) {
        com.android.server.wm.LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
        java.util.Objects.requireNonNull(letterboxConfiguration);
        if (shouldEnableWithOverrideAndProperty(new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda2(letterboxConfiguration), this.mIsOverrideEnableCompatIgnoreRequestedOrientationEnabled, this.mBooleanPropertyIgnoreRequestedOrientation)) {
            if (this.mIsRelaunchingAfterRequestedOrientationChanged) {
                android.util.Slog.w(TAG, "Ignoring orientation update to " + android.content.pm.ActivityInfo.screenOrientationToString(i) + " due to relaunching after setRequestedOrientation for " + this.mActivityRecord);
                return true;
            }
            if (isCameraCompatTreatmentActive()) {
                android.util.Slog.w(TAG, "Ignoring orientation update to " + android.content.pm.ActivityInfo.screenOrientationToString(i) + " due to camera compat treatment for " + this.mActivityRecord);
                return true;
            }
        }
        if (shouldIgnoreOrientationRequestLoop()) {
            android.util.Slog.w(TAG, "Ignoring orientation update to " + android.content.pm.ActivityInfo.screenOrientationToString(i) + " as orientation request loop was detected for " + this.mActivityRecord);
            return true;
        }
        return false;
    }

    boolean shouldIgnoreOrientationRequestLoop() {
        com.android.server.wm.LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
        java.util.Objects.requireNonNull(letterboxConfiguration);
        if (!shouldEnableWithOptInOverrideAndOptOutProperty(new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda2(letterboxConfiguration), this.mIsOverrideEnableCompatIgnoreOrientationRequestWhenLoopDetectedEnabled, this.mBooleanPropertyAllowIgnoringOrientationRequestWhenLoopDetected)) {
            return false;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (currentTimeMillis - this.mTimeMsLastSetOrientationRequest < 1000) {
            this.mSetOrientationRequestCounter++;
        } else {
            this.mSetOrientationRequestCounter = 0;
        }
        this.mTimeMsLastSetOrientationRequest = currentTimeMillis;
        return this.mSetOrientationRequestCounter >= 2 && !this.mActivityRecord.isLetterboxedForFixedOrientationAndAspectRatio();
    }

    @com.android.internal.annotations.VisibleForTesting
    int getSetOrientationRequestCounter() {
        return this.mSetOrientationRequestCounter;
    }

    boolean shouldSendFakeFocus() {
        com.android.server.wm.LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
        java.util.Objects.requireNonNull(letterboxConfiguration);
        return shouldEnableWithOverrideAndProperty(new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda3(letterboxConfiguration), this.mIsOverrideEnableCompatFakeFocusEnabled, this.mBooleanPropertyFakeFocus);
    }

    boolean shouldOverrideMinAspectRatio() {
        return shouldEnableWithOptInOverrideAndOptOutProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda25
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldOverrideMinAspectRatio$5;
                lambda$shouldOverrideMinAspectRatio$5 = com.android.server.wm.LetterboxUiController.lambda$shouldOverrideMinAspectRatio$5();
                return lambda$shouldOverrideMinAspectRatio$5;
            }
        }, this.mIsOverrideMinAspectRatio, this.mBooleanPropertyAllowMinAspectRatioOverride);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$shouldOverrideMinAspectRatio$5() {
        return true;
    }

    boolean shouldOverrideForceResizeApp() {
        return shouldEnableWithOptInOverrideAndOptOutProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldOverrideForceResizeApp$6;
                lambda$shouldOverrideForceResizeApp$6 = com.android.server.wm.LetterboxUiController.lambda$shouldOverrideForceResizeApp$6();
                return lambda$shouldOverrideForceResizeApp$6;
            }
        }, this.mIsOverrideForceResizeApp, this.mBooleanPropertyAllowForceResizeOverride);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$shouldOverrideForceResizeApp$6() {
        return true;
    }

    boolean shouldOverrideForceNonResizeApp() {
        return shouldEnableWithOptInOverrideAndOptOutProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda10
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldOverrideForceNonResizeApp$7;
                lambda$shouldOverrideForceNonResizeApp$7 = com.android.server.wm.LetterboxUiController.lambda$shouldOverrideForceNonResizeApp$7();
                return lambda$shouldOverrideForceNonResizeApp$7;
            }
        }, this.mIsOverrideForceNonResizeApp, this.mBooleanPropertyAllowForceResizeOverride);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$shouldOverrideForceNonResizeApp$7() {
        return true;
    }

    void setRelaunchingAfterRequestedOrientationChanged(boolean z) {
        this.mIsRelaunchingAfterRequestedOrientationChanged = z;
    }

    boolean isRefreshAfterRotationRequested() {
        return this.mIsRefreshAfterRotationRequested;
    }

    void setIsRefreshAfterRotationRequested(boolean z) {
        this.mIsRefreshAfterRotationRequested = z;
    }

    boolean isOverrideRespectRequestedOrientationEnabled() {
        return this.mIsOverrideRespectRequestedOrientationEnabled;
    }

    boolean shouldUseDisplayLandscapeNaturalOrientation() {
        return shouldEnableWithOptInOverrideAndOptOutProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda24
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldUseDisplayLandscapeNaturalOrientation$8;
                lambda$shouldUseDisplayLandscapeNaturalOrientation$8 = com.android.server.wm.LetterboxUiController.this.lambda$shouldUseDisplayLandscapeNaturalOrientation$8();
                return lambda$shouldUseDisplayLandscapeNaturalOrientation$8;
            }
        }, this.mIsOverrideUseDisplayLandscapeNaturalOrientationEnabled, this.mBooleanPropertyAllowDisplayOrientationOverride);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$shouldUseDisplayLandscapeNaturalOrientation$8() {
        return (this.mActivityRecord.mDisplayContent == null || this.mActivityRecord.getTask() == null || !this.mActivityRecord.mDisplayContent.getIgnoreOrientationRequest() || this.mActivityRecord.getTask().inMultiWindowMode() || this.mActivityRecord.mDisplayContent.getNaturalOrientation() != 2) ? false : true;
    }

    int overrideOrientationIfNeeded(int i) {
        com.android.server.wm.DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
        boolean z = displayContent != null && displayContent.getIgnoreOrientationRequest();
        if (shouldApplyUserFullscreenOverride() && z) {
            android.util.Slog.v(TAG, "Requested orientation " + android.content.pm.ActivityInfo.screenOrientationToString(i) + " for " + this.mActivityRecord + " is overridden to " + android.content.pm.ActivityInfo.screenOrientationToString(2) + " by user aspect ratio settings.");
            return 2;
        }
        int mapOrientationRequest = this.mActivityRecord.mWmService.mapOrientationRequest(i);
        if (shouldApplyUserMinAspectRatioOverride() && (!android.content.pm.ActivityInfo.isFixedOrientation(mapOrientationRequest) || mapOrientationRequest == 14)) {
            android.util.Slog.v(TAG, "Requested orientation " + android.content.pm.ActivityInfo.screenOrientationToString(mapOrientationRequest) + " for " + this.mActivityRecord + " is overridden to " + android.content.pm.ActivityInfo.screenOrientationToString(1) + " by user aspect ratio settings.");
            return 1;
        }
        if (java.lang.Boolean.FALSE.equals(this.mBooleanPropertyAllowOrientationOverride)) {
            return mapOrientationRequest;
        }
        if (this.mIsOverrideOrientationOnlyForCameraEnabled && displayContent != null && (displayContent.mDisplayRotationCompatPolicy == null || !displayContent.mDisplayRotationCompatPolicy.isActivityEligibleForOrientationOverride(this.mActivityRecord))) {
            return mapOrientationRequest;
        }
        if (isSystemOverrideToFullscreenEnabled() && z) {
            android.util.Slog.v(TAG, "Requested orientation  " + android.content.pm.ActivityInfo.screenOrientationToString(mapOrientationRequest) + " for " + this.mActivityRecord + " is overridden to " + android.content.pm.ActivityInfo.screenOrientationToString(2));
            return 2;
        }
        if (this.mIsOverrideToReverseLandscapeOrientationEnabled && (android.content.pm.ActivityInfo.isFixedOrientationLandscape(mapOrientationRequest) || this.mIsOverrideAnyOrientationEnabled)) {
            android.util.Slog.w(TAG, "Requested orientation  " + android.content.pm.ActivityInfo.screenOrientationToString(mapOrientationRequest) + " for " + this.mActivityRecord + " is overridden to " + android.content.pm.ActivityInfo.screenOrientationToString(8));
            return 8;
        }
        if (!this.mIsOverrideAnyOrientationEnabled && android.content.pm.ActivityInfo.isFixedOrientation(mapOrientationRequest)) {
            return mapOrientationRequest;
        }
        if (this.mIsOverrideToPortraitOrientationEnabled) {
            android.util.Slog.w(TAG, "Requested orientation  " + android.content.pm.ActivityInfo.screenOrientationToString(mapOrientationRequest) + " for " + this.mActivityRecord + " is overridden to " + android.content.pm.ActivityInfo.screenOrientationToString(1));
            return 1;
        }
        if (this.mIsOverrideToNosensorOrientationEnabled) {
            android.util.Slog.w(TAG, "Requested orientation  " + android.content.pm.ActivityInfo.screenOrientationToString(mapOrientationRequest) + " for " + this.mActivityRecord + " is overridden to " + android.content.pm.ActivityInfo.screenOrientationToString(5));
            return 5;
        }
        return mapOrientationRequest;
    }

    boolean isOverrideOrientationOnlyForCameraEnabled() {
        return this.mIsOverrideOrientationOnlyForCameraEnabled;
    }

    boolean shouldRefreshActivityForCameraCompat() {
        return shouldEnableWithOptOutOverrideAndProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda12
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldRefreshActivityForCameraCompat$9;
                lambda$shouldRefreshActivityForCameraCompat$9 = com.android.server.wm.LetterboxUiController.this.lambda$shouldRefreshActivityForCameraCompat$9();
                return lambda$shouldRefreshActivityForCameraCompat$9;
            }
        }, this.mIsOverrideCameraCompatDisableRefreshEnabled, this.mBooleanPropertyCameraCompatAllowRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$shouldRefreshActivityForCameraCompat$9() {
        return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled();
    }

    boolean shouldRefreshActivityViaPauseForCameraCompat() {
        return shouldEnableWithOverrideAndProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda14
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldRefreshActivityViaPauseForCameraCompat$10;
                lambda$shouldRefreshActivityViaPauseForCameraCompat$10 = com.android.server.wm.LetterboxUiController.this.lambda$shouldRefreshActivityViaPauseForCameraCompat$10();
                return lambda$shouldRefreshActivityViaPauseForCameraCompat$10;
            }
        }, this.mIsOverrideCameraCompatEnableRefreshViaPauseEnabled, this.mBooleanPropertyCameraCompatEnableRefreshViaPause);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$shouldRefreshActivityViaPauseForCameraCompat$10() {
        return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled();
    }

    boolean shouldForceRotateForCameraCompat() {
        return shouldEnableWithOptOutOverrideAndProperty(new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda13
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$shouldForceRotateForCameraCompat$11;
                lambda$shouldForceRotateForCameraCompat$11 = com.android.server.wm.LetterboxUiController.this.lambda$shouldForceRotateForCameraCompat$11();
                return lambda$shouldForceRotateForCameraCompat$11;
            }
        }, this.mIsOverrideCameraCompatDisableForceRotationEnabled, this.mBooleanPropertyCameraCompatAllowForceRotation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$shouldForceRotateForCameraCompat$11() {
        return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled();
    }

    private boolean isCameraCompatTreatmentActive() {
        com.android.server.wm.DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
        return (displayContent == null || displayContent.mDisplayRotationCompatPolicy == null || !displayContent.mDisplayRotationCompatPolicy.isTreatmentEnabledForActivity(this.mActivityRecord)) ? false : true;
    }

    private boolean isCompatChangeEnabled(long j) {
        return this.mActivityRecord.info.isChangeEnabled(j);
    }

    private boolean shouldEnableWithOptOutOverrideAndProperty(java.util.function.BooleanSupplier booleanSupplier, boolean z, java.lang.Boolean bool) {
        return (!booleanSupplier.getAsBoolean() || java.lang.Boolean.FALSE.equals(bool) || z) ? false : true;
    }

    private boolean shouldEnableWithOptInOverrideAndOptOutProperty(java.util.function.BooleanSupplier booleanSupplier, boolean z, java.lang.Boolean bool) {
        return booleanSupplier.getAsBoolean() && !java.lang.Boolean.FALSE.equals(bool) && z;
    }

    private boolean shouldEnableWithOverrideAndProperty(java.util.function.BooleanSupplier booleanSupplier, boolean z, java.lang.Boolean bool) {
        if (booleanSupplier.getAsBoolean() && !java.lang.Boolean.FALSE.equals(bool)) {
            return java.lang.Boolean.TRUE.equals(bool) || z;
        }
        return false;
    }

    boolean hasWallpaperBackgroundForLetterbox() {
        return this.mShowWallpaperForLetterboxBackground;
    }

    android.graphics.Rect getLetterboxInsets() {
        if (this.mLetterbox != null) {
            return this.mLetterbox.getInsets();
        }
        return new android.graphics.Rect();
    }

    void getLetterboxInnerBounds(android.graphics.Rect rect) {
        if (this.mLetterbox != null) {
            rect.set(this.mLetterbox.getInnerFrame());
            com.android.server.wm.WindowState findMainWindow = this.mActivityRecord.findMainWindow();
            if (findMainWindow != null) {
                adjustBoundsForTaskbar(findMainWindow, rect);
                return;
            }
            return;
        }
        rect.setEmpty();
    }

    private void getLetterboxOuterBounds(android.graphics.Rect rect) {
        if (this.mLetterbox != null) {
            rect.set(this.mLetterbox.getOuterFrame());
        } else {
            rect.setEmpty();
        }
    }

    boolean isFullyTransparentBarAllowed(android.graphics.Rect rect) {
        return this.mLetterbox == null || this.mLetterbox.notIntersectsOrFullyContains(rect);
    }

    void updateLetterboxSurfaceIfNeeded(com.android.server.wm.WindowState windowState) {
        updateLetterboxSurfaceIfNeeded(windowState, this.mActivityRecord.getSyncTransaction());
    }

    void updateLetterboxSurfaceIfNeeded(com.android.server.wm.WindowState windowState, android.view.SurfaceControl.Transaction transaction) {
        if (shouldNotLayoutLetterbox(windowState)) {
            return;
        }
        layoutLetterboxIfNeeded(windowState);
        if (this.mLetterbox != null && this.mLetterbox.needsApplySurfaceChanges()) {
            this.mLetterbox.applySurfaceChanges(transaction);
        }
    }

    void layoutLetterboxIfNeeded(com.android.server.wm.WindowState windowState) {
        if (shouldNotLayoutLetterbox(windowState)) {
            return;
        }
        updateRoundedCornersIfNeeded(windowState);
        updateWallpaperForLetterbox(windowState);
        if (!shouldShowLetterboxUi(windowState)) {
            if (this.mLetterbox != null) {
                this.mLetterbox.hide();
                return;
            }
            return;
        }
        if (this.mLetterbox == null) {
            this.mLetterbox = new com.android.server.wm.Letterbox(new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.view.SurfaceControl.Builder lambda$layoutLetterboxIfNeeded$12;
                    lambda$layoutLetterboxIfNeeded$12 = com.android.server.wm.LetterboxUiController.this.lambda$layoutLetterboxIfNeeded$12();
                    return lambda$layoutLetterboxIfNeeded$12;
                }
            }, this.mActivityRecord.mWmService.mTransactionFactory, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda16
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    boolean shouldLetterboxHaveRoundedCorners;
                    shouldLetterboxHaveRoundedCorners = com.android.server.wm.LetterboxUiController.this.shouldLetterboxHaveRoundedCorners();
                    return shouldLetterboxHaveRoundedCorners;
                }
            }, new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda17
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.graphics.Color letterboxBackgroundColor;
                    letterboxBackgroundColor = com.android.server.wm.LetterboxUiController.this.getLetterboxBackgroundColor();
                    return letterboxBackgroundColor;
                }
            }, new java.util.function.BooleanSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda18
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    return com.android.server.wm.LetterboxUiController.this.hasWallpaperBackgroundForLetterbox();
                }
            }, new java.util.function.IntSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda19
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    int letterboxWallpaperBlurRadiusPx;
                    letterboxWallpaperBlurRadiusPx = com.android.server.wm.LetterboxUiController.this.getLetterboxWallpaperBlurRadiusPx();
                    return letterboxWallpaperBlurRadiusPx;
                }
            }, new java.util.function.DoubleSupplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda20
                @Override // java.util.function.DoubleSupplier
                public final double getAsDouble() {
                    float letterboxWallpaperDarkScrimAlpha;
                    letterboxWallpaperDarkScrimAlpha = com.android.server.wm.LetterboxUiController.this.getLetterboxWallpaperDarkScrimAlpha();
                    return letterboxWallpaperDarkScrimAlpha;
                }
            }, new java.util.function.IntConsumer() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda21
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    com.android.server.wm.LetterboxUiController.this.handleHorizontalDoubleTap(i);
                }
            }, new java.util.function.IntConsumer() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda22
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    com.android.server.wm.LetterboxUiController.this.handleVerticalDoubleTap(i);
                }
            }, new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda23
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return com.android.server.wm.LetterboxUiController.this.getLetterboxParentSurface();
                }
            });
            this.mLetterbox.attachInput(windowState);
        }
        if (this.mActivityRecord.isInLetterboxAnimation()) {
            this.mActivityRecord.getTask().getPosition(this.mTmpPoint);
        } else {
            this.mActivityRecord.getPosition(this.mTmpPoint);
        }
        android.graphics.Rect fixedRotationTransformDisplayBounds = this.mActivityRecord.getFixedRotationTransformDisplayBounds();
        if (fixedRotationTransformDisplayBounds == null) {
            if (this.mActivityRecord.inMultiWindowMode()) {
                fixedRotationTransformDisplayBounds = this.mActivityRecord.getTaskFragment().getBounds();
            } else {
                fixedRotationTransformDisplayBounds = this.mActivityRecord.getRootTask().getParent().getBounds();
            }
        }
        this.mLetterbox.layout(fixedRotationTransformDisplayBounds, hasInheritedLetterboxBehavior() ? this.mActivityRecord.getBounds() : windowState.getFrame(), this.mTmpPoint);
        if (this.mDoubleTapEvent) {
            this.mActivityRecord.getTask().dispatchTaskInfoChangedIfNeeded(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.SurfaceControl.Builder lambda$layoutLetterboxIfNeeded$12() {
        return this.mActivityRecord.makeChildSurface(null);
    }

    boolean isFromDoubleTap() {
        boolean z = this.mDoubleTapEvent;
        this.mDoubleTapEvent = false;
        return z;
    }

    android.view.SurfaceControl getLetterboxParentSurface() {
        if (this.mActivityRecord.isInLetterboxAnimation()) {
            return this.mActivityRecord.getTask().getSurfaceControl();
        }
        return this.mActivityRecord.getSurfaceControl();
    }

    private static boolean shouldNotLayoutLetterbox(com.android.server.wm.WindowState windowState) {
        if (windowState == null) {
            return true;
        }
        int i = windowState.mAttrs.type;
        return !(i == 1 || i == 3) || windowState.mAnimatingExit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldLetterboxHaveRoundedCorners() {
        return this.mLetterboxConfiguration.isLetterboxActivityCornersRounded() && this.mActivityRecord.fillsParent();
    }

    private boolean isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState deviceState, boolean z) {
        com.android.server.wm.Task task = this.mActivityRecord.getTask();
        return this.mActivityRecord.mDisplayContent != null && this.mActivityRecord.mDisplayContent.getDisplayRotation().isDeviceInPosture(deviceState, z) && task != null && task.getWindowingMode() == 1;
    }

    private boolean isDisplayFullScreenAndSeparatingHinge() {
        com.android.server.wm.Task task = this.mActivityRecord.getTask();
        return this.mActivityRecord.mDisplayContent != null && this.mActivityRecord.mDisplayContent.getDisplayRotation().isDisplaySeparatingHinge() && task != null && task.getWindowingMode() == 1;
    }

    float getHorizontalPositionMultiplier(android.content.res.Configuration configuration) {
        boolean isFullScreenAndBookModeEnabled = isFullScreenAndBookModeEnabled();
        if (isHorizontalReachabilityEnabled(configuration)) {
            return this.mLetterboxConfiguration.getHorizontalMultiplierForReachability(isFullScreenAndBookModeEnabled);
        }
        return this.mLetterboxConfiguration.getLetterboxHorizontalPositionMultiplier(isFullScreenAndBookModeEnabled);
    }

    private boolean isFullScreenAndBookModeEnabled() {
        return isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED, false) && this.mLetterboxConfiguration.getIsAutomaticReachabilityInBookModeEnabled();
    }

    float getVerticalPositionMultiplier(android.content.res.Configuration configuration) {
        boolean isDisplayFullScreenAndInPosture = isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED, true);
        if (isVerticalReachabilityEnabled(configuration)) {
            return this.mLetterboxConfiguration.getVerticalMultiplierForReachability(isDisplayFullScreenAndInPosture);
        }
        return this.mLetterboxConfiguration.getLetterboxVerticalPositionMultiplier(isDisplayFullScreenAndInPosture);
    }

    float getFixedOrientationLetterboxAspectRatio(@android.annotation.NonNull android.content.res.Configuration configuration) {
        if (shouldUseSplitScreenAspectRatio(configuration)) {
            return getSplitScreenAspectRatio();
        }
        if (this.mActivityRecord.shouldCreateCompatDisplayInsets()) {
            return getDefaultMinAspectRatioForUnresizableApps();
        }
        return getDefaultMinAspectRatio();
    }

    void recomputeConfigurationForCameraCompatIfNeeded() {
        if (isOverrideOrientationOnlyForCameraEnabled() || isCameraCompatSplitScreenAspectRatioAllowed()) {
            this.mActivityRecord.recomputeConfiguration();
        }
    }

    boolean isCameraCompatSplitScreenAspectRatioAllowed() {
        return this.mLetterboxConfiguration.isCameraCompatSplitScreenAspectRatioEnabled() && !this.mActivityRecord.shouldCreateCompatDisplayInsets();
    }

    private boolean shouldUseSplitScreenAspectRatio(@android.annotation.NonNull android.content.res.Configuration configuration) {
        return (isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED, false) && ((getHorizontalPositionMultiplier(configuration) > 0.5f ? 1 : (getHorizontalPositionMultiplier(configuration) == 0.5f ? 0 : -1)) != 0)) || isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED, true) || (isCameraCompatSplitScreenAspectRatioAllowed() && isCameraCompatTreatmentActive());
    }

    private float getDefaultMinAspectRatioForUnresizableApps() {
        if (this.mLetterboxConfiguration.getIsSplitScreenAspectRatioForUnresizableAppsEnabled() && this.mActivityRecord.getDisplayArea() != null) {
            return getSplitScreenAspectRatio();
        }
        if (this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps() > 1.0f) {
            return this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps();
        }
        return getDefaultMinAspectRatio();
    }

    float getSplitScreenAspectRatio() {
        com.android.server.wm.TaskDisplayArea displayArea = this.mActivityRecord.getDisplayArea();
        if (displayArea == null) {
            return getDefaultMinAspectRatioForUnresizableApps();
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.dialog_no_title_padding_top) - (getResources().getDimensionPixelSize(android.R.dimen.dialog_list_padding_top_no_title) * 2);
        android.graphics.Rect rect = new android.graphics.Rect(displayArea.getWindowConfiguration().getAppBounds());
        if (rect.width() >= rect.height()) {
            rect.inset(dimensionPixelSize / 2, 0);
            rect.right = rect.centerX();
        } else {
            rect.inset(0, dimensionPixelSize / 2);
            rect.bottom = rect.centerY();
        }
        return com.android.server.wm.ActivityRecord.computeAspectRatio(rect);
    }

    boolean shouldEnableUserAspectRatioSettings() {
        return !java.lang.Boolean.FALSE.equals(this.mBooleanPropertyAllowUserAspectRatioOverride) && this.mLetterboxConfiguration.isUserAppAspectRatioSettingsEnabled() && this.mActivityRecord.mDisplayContent != null && this.mActivityRecord.mDisplayContent.getIgnoreOrientationRequest();
    }

    boolean shouldApplyUserMinAspectRatioOverride() {
        if (!shouldEnableUserAspectRatioSettings()) {
            return false;
        }
        this.mUserAspectRatio = getUserMinAspectRatioOverrideCode();
        return (this.mUserAspectRatio == 0 || this.mUserAspectRatio == 7 || this.mUserAspectRatio == 6) ? false : true;
    }

    boolean isUserFullscreenOverrideEnabled() {
        if (java.lang.Boolean.FALSE.equals(this.mBooleanPropertyAllowUserAspectRatioOverride) || java.lang.Boolean.FALSE.equals(this.mBooleanPropertyAllowUserAspectRatioFullscreenOverride) || !this.mLetterboxConfiguration.isUserAppAspectRatioFullscreenEnabled()) {
            return false;
        }
        return true;
    }

    boolean shouldApplyUserFullscreenOverride() {
        if (!isUserFullscreenOverrideEnabled()) {
            return false;
        }
        this.mUserAspectRatio = getUserMinAspectRatioOverrideCode();
        return this.mUserAspectRatio == 6;
    }

    boolean isSystemOverrideToFullscreenEnabled() {
        return this.mIsSystemOverrideToFullscreenEnabled && !java.lang.Boolean.FALSE.equals(this.mBooleanPropertyAllowOrientationOverride) && (this.mUserAspectRatio == 0 || this.mUserAspectRatio == 6);
    }

    float getUserMinAspectRatio() {
        switch (this.mUserAspectRatio) {
            case 1:
                return getSplitScreenAspectRatio();
            case 2:
                return getDisplaySizeMinAspectRatio();
            case 3:
                return 1.3333334f;
            case 4:
                return 1.7777778f;
            case 5:
                return 1.5f;
            default:
                throw new java.lang.AssertionError("Unexpected user min aspect ratio override: " + this.mUserAspectRatio);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getUserMinAspectRatioOverrideCode() {
        try {
            return this.mActivityRecord.mAtmService.getPackageManager().getUserMinAspectRatio(this.mActivityRecord.packageName, this.mActivityRecord.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Exception thrown retrieving aspect ratio user override " + this, e);
            return this.mUserAspectRatio;
        }
    }

    private float getDisplaySizeMinAspectRatio() {
        com.android.server.wm.TaskDisplayArea displayArea = this.mActivityRecord.getDisplayArea();
        if (displayArea == null) {
            return this.mActivityRecord.info.getMinAspectRatio();
        }
        return com.android.server.wm.ActivityRecord.computeAspectRatio(new android.graphics.Rect(displayArea.getWindowConfiguration().getAppBounds()));
    }

    private float getDefaultMinAspectRatio() {
        if (this.mActivityRecord.getDisplayArea() == null || !this.mLetterboxConfiguration.getIsDisplayAspectRatioEnabledForFixedOrientationLetterbox()) {
            return this.mLetterboxConfiguration.getFixedOrientationLetterboxAspectRatio();
        }
        return getDisplaySizeMinAspectRatio();
    }

    android.content.res.Resources getResources() {
        return this.mActivityRecord.mWmService.mContext.getResources();
    }

    int getLetterboxPositionForVerticalReachability() {
        return this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(isDisplayFullScreenAndSeparatingHinge());
    }

    int getLetterboxPositionForHorizontalReachability() {
        return this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(isFullScreenAndBookModeEnabled());
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleHorizontalDoubleTap(int i) {
        int i2;
        int i3;
        if (!isHorizontalReachabilityEnabled() || this.mActivityRecord.isInTransition()) {
            return;
        }
        if (this.mLetterbox.getInnerFrame().left <= i && this.mLetterbox.getInnerFrame().right >= i) {
            return;
        }
        boolean z = isDisplayFullScreenAndSeparatingHinge() && this.mLetterboxConfiguration.getIsAutomaticReachabilityInBookModeEnabled();
        int letterboxPositionForHorizontalReachability = this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(z);
        if (this.mLetterbox.getInnerFrame().left > i) {
            this.mLetterboxConfiguration.movePositionForHorizontalReachabilityToNextLeftStop(z);
            if (letterboxPositionForHorizontalReachability == 1) {
                i3 = 1;
            } else {
                i3 = 4;
            }
            logLetterboxPositionChange(i3);
            this.mDoubleTapEvent = true;
        } else if (this.mLetterbox.getInnerFrame().right < i) {
            this.mLetterboxConfiguration.movePositionForHorizontalReachabilityToNextRightStop(z);
            if (letterboxPositionForHorizontalReachability == 1) {
                i2 = 3;
            } else {
                i2 = 2;
            }
            logLetterboxPositionChange(i2);
            this.mDoubleTapEvent = true;
        }
        this.mActivityRecord.recomputeConfiguration();
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleVerticalDoubleTap(int i) {
        int i2;
        int i3;
        if (!isVerticalReachabilityEnabled() || this.mActivityRecord.isInTransition()) {
            return;
        }
        if (this.mLetterbox.getInnerFrame().top <= i && this.mLetterbox.getInnerFrame().bottom >= i) {
            return;
        }
        boolean isDisplayFullScreenAndSeparatingHinge = isDisplayFullScreenAndSeparatingHinge();
        int letterboxPositionForVerticalReachability = this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(isDisplayFullScreenAndSeparatingHinge);
        if (this.mLetterbox.getInnerFrame().top > i) {
            this.mLetterboxConfiguration.movePositionForVerticalReachabilityToNextTopStop(isDisplayFullScreenAndSeparatingHinge);
            if (letterboxPositionForVerticalReachability == 1) {
                i3 = 5;
            } else {
                i3 = 8;
            }
            logLetterboxPositionChange(i3);
            this.mDoubleTapEvent = true;
        } else if (this.mLetterbox.getInnerFrame().bottom < i) {
            this.mLetterboxConfiguration.movePositionForVerticalReachabilityToNextBottomStop(isDisplayFullScreenAndSeparatingHinge);
            if (letterboxPositionForVerticalReachability == 1) {
                i2 = 7;
            } else {
                i2 = 6;
            }
            logLetterboxPositionChange(i2);
            this.mDoubleTapEvent = true;
        }
        this.mActivityRecord.recomputeConfiguration();
    }

    private boolean isHorizontalReachabilityEnabled(android.content.res.Configuration configuration) {
        android.graphics.Rect screenResolvedBounds;
        if (hasInheritedLetterboxBehavior()) {
            screenResolvedBounds = this.mFirstOpaqueActivityBeneath.getScreenResolvedBounds();
        } else {
            screenResolvedBounds = this.mActivityRecord.getScreenResolvedBounds();
        }
        return this.mLetterboxConfiguration.getIsHorizontalReachabilityEnabled() && configuration.windowConfiguration.getWindowingMode() == 1 && configuration.windowConfiguration.getAppBounds().height() <= screenResolvedBounds.height() && configuration.windowConfiguration.getAppBounds().width() > screenResolvedBounds.width();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isHorizontalReachabilityEnabled() {
        return isHorizontalReachabilityEnabled(this.mActivityRecord.getParent().getConfiguration());
    }

    boolean isLetterboxDoubleTapEducationEnabled() {
        return isHorizontalReachabilityEnabled() || isVerticalReachabilityEnabled();
    }

    private boolean isVerticalReachabilityEnabled(android.content.res.Configuration configuration) {
        android.graphics.Rect screenResolvedBounds;
        if (hasInheritedLetterboxBehavior()) {
            screenResolvedBounds = this.mFirstOpaqueActivityBeneath.getScreenResolvedBounds();
        } else {
            screenResolvedBounds = this.mActivityRecord.getScreenResolvedBounds();
        }
        return this.mLetterboxConfiguration.getIsVerticalReachabilityEnabled() && configuration.windowConfiguration.getWindowingMode() == 1 && configuration.windowConfiguration.getAppBounds().width() <= screenResolvedBounds.width() && configuration.windowConfiguration.getAppBounds().height() > screenResolvedBounds.height();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isVerticalReachabilityEnabled() {
        return isVerticalReachabilityEnabled(this.mActivityRecord.getParent().getConfiguration());
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldShowLetterboxUi(com.android.server.wm.WindowState windowState) {
        if (this.mIsRelaunchingAfterRequestedOrientationChanged) {
            return this.mLastShouldShowLetterboxUi;
        }
        boolean z = (this.mActivityRecord.isInLetterboxAnimation() || isSurfaceVisible(windowState)) && windowState.areAppWindowBoundsLetterboxed() && (windowState.getAttrs().flags & 1048576) == 0;
        this.mLastShouldShowLetterboxUi = z;
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isSurfaceVisible(com.android.server.wm.WindowState windowState) {
        return windowState.isOnScreen() && (this.mActivityRecord.isVisible() || this.mActivityRecord.isVisibleRequested());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.graphics.Color getLetterboxBackgroundColor() {
        com.android.server.wm.WindowState findMainWindow = this.mActivityRecord.findMainWindow();
        if (findMainWindow == null || findMainWindow.isLetterboxedForDisplayCutout()) {
            return android.graphics.Color.valueOf(android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK);
        }
        int letterboxBackgroundType = this.mLetterboxConfiguration.getLetterboxBackgroundType();
        android.app.ActivityManager.TaskDescription taskDescription = this.mActivityRecord.taskDescription;
        switch (letterboxBackgroundType) {
            case 0:
                return this.mLetterboxConfiguration.getLetterboxBackgroundColor();
            case 1:
                if (taskDescription != null && taskDescription.getBackgroundColor() != 0) {
                    return android.graphics.Color.valueOf(taskDescription.getBackgroundColor());
                }
                break;
            case 2:
                if (taskDescription != null && taskDescription.getBackgroundColorFloating() != 0) {
                    return android.graphics.Color.valueOf(taskDescription.getBackgroundColorFloating());
                }
                break;
            case 3:
                if (hasWallpaperBackgroundForLetterbox()) {
                    return this.mLetterboxConfiguration.getLetterboxBackgroundColor();
                }
                android.util.Slog.w(TAG, "Wallpaper option is selected for letterbox background but blur is not supported by a device or not supported in the current window configuration or both alpha scrim and blur radius aren't provided so using solid color background");
                break;
            default:
                throw new java.lang.AssertionError("Unexpected letterbox background type: " + letterboxBackgroundType);
        }
        return this.mLetterboxConfiguration.getLetterboxBackgroundColor();
    }

    private void updateRoundedCornersIfNeeded(com.android.server.wm.WindowState windowState) {
        android.view.SurfaceControl surfaceControl = windowState.getSurfaceControl();
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        this.mActivityRecord.getSyncTransaction().setCrop(surfaceControl, getCropBoundsIfNeeded(windowState)).setCornerRadius(surfaceControl, getRoundedCornersRadius(windowState));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.graphics.Rect getCropBoundsIfNeeded(com.android.server.wm.WindowState windowState) {
        if (!requiresRoundedCorners(windowState) || this.mActivityRecord.isInLetterboxAnimation()) {
            return null;
        }
        android.graphics.Rect rect = new android.graphics.Rect(this.mActivityRecord.getBounds());
        if (hasInheritedLetterboxBehavior() && (rect.width() != windowState.mRequestedWidth || rect.height() != windowState.mRequestedHeight)) {
            return null;
        }
        adjustBoundsForTaskbar(windowState, rect);
        float f = windowState.mInvGlobalScale;
        if (f != 1.0f && f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            rect.scale(f);
        }
        rect.offsetTo(0, 0);
        return rect;
    }

    private boolean requiresRoundedCorners(com.android.server.wm.WindowState windowState) {
        return isLetterboxedNotForDisplayCutout(windowState) && this.mLetterboxConfiguration.isLetterboxActivityCornersRounded();
    }

    int getRoundedCornersRadius(com.android.server.wm.WindowState windowState) {
        int min;
        if (!requiresRoundedCorners(windowState)) {
            return 0;
        }
        if (this.mLetterboxConfiguration.getLetterboxActivityCornersRadius() >= 0) {
            min = this.mLetterboxConfiguration.getLetterboxActivityCornersRadius();
        } else {
            android.view.InsetsState insetsState = windowState.getInsetsState();
            min = java.lang.Math.min(getInsetsStateCornerRadius(insetsState, 3), getInsetsStateCornerRadius(insetsState, 2));
        }
        float f = windowState.mInvGlobalScale;
        return (f == 1.0f || f <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) ? min : (int) (f * min);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.view.InsetsSource getExpandedTaskbarOrNull(com.android.server.wm.WindowState windowState) {
        android.view.InsetsState insetsState = windowState.getInsetsState();
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if (sourceAt.getType() == android.view.WindowInsets.Type.navigationBars() && sourceAt.hasFlags(2) && sourceAt.isVisible()) {
                return sourceAt;
            }
        }
        return null;
    }

    boolean getIsRelaunchingAfterRequestedOrientationChanged() {
        return this.mIsRelaunchingAfterRequestedOrientationChanged;
    }

    private void adjustBoundsForTaskbar(com.android.server.wm.WindowState windowState, android.graphics.Rect rect) {
        android.view.InsetsSource expandedTaskbarOrNull = getExpandedTaskbarOrNull(windowState);
        if (expandedTaskbarOrNull != null) {
            rect.bottom = java.lang.Math.min(rect.bottom, expandedTaskbarOrNull.getFrame().top);
        }
    }

    private int getInsetsStateCornerRadius(android.view.InsetsState insetsState, int i) {
        android.view.RoundedCorner roundedCorner = insetsState.getRoundedCorners().getRoundedCorner(i);
        if (roundedCorner == null) {
            return 0;
        }
        return roundedCorner.getRadius();
    }

    private boolean isLetterboxedNotForDisplayCutout(com.android.server.wm.WindowState windowState) {
        return shouldShowLetterboxUi(windowState) && !windowState.isLetterboxedForDisplayCutout();
    }

    private void updateWallpaperForLetterbox(com.android.server.wm.WindowState windowState) {
        boolean z = this.mLetterboxConfiguration.getLetterboxBackgroundType() == 3 && isLetterboxedNotForDisplayCutout(windowState) && (getLetterboxWallpaperBlurRadiusPx() > 0 || getLetterboxWallpaperDarkScrimAlpha() > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) && (getLetterboxWallpaperBlurRadiusPx() <= 0 || isLetterboxWallpaperBlurSupported());
        if (this.mShowWallpaperForLetterboxBackground != z) {
            this.mShowWallpaperForLetterboxBackground = z;
            this.mActivityRecord.requestUpdateWallpaperIfNeeded();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLetterboxWallpaperBlurRadiusPx() {
        return java.lang.Math.max(this.mLetterboxConfiguration.getLetterboxBackgroundWallpaperBlurRadiusPx(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getLetterboxWallpaperDarkScrimAlpha() {
        float letterboxBackgroundWallpaperDarkScrimAlpha = this.mLetterboxConfiguration.getLetterboxBackgroundWallpaperDarkScrimAlpha();
        return (letterboxBackgroundWallpaperDarkScrimAlpha < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || letterboxBackgroundWallpaperDarkScrimAlpha >= 1.0f) ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : letterboxBackgroundWallpaperDarkScrimAlpha;
    }

    private boolean isLetterboxWallpaperBlurSupported() {
        return ((android.view.WindowManager) this.mLetterboxConfiguration.mContext.getSystemService(android.view.WindowManager.class)).isCrossWindowBlurEnabled();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        com.android.server.wm.WindowState findMainWindow = this.mActivityRecord.findMainWindow();
        if (findMainWindow == null) {
            return;
        }
        boolean areAppWindowBoundsLetterboxed = findMainWindow.areAppWindowBoundsLetterboxed();
        printWriter.println(str + "areBoundsLetterboxed=" + areAppWindowBoundsLetterboxed);
        if (!areAppWindowBoundsLetterboxed) {
            return;
        }
        printWriter.println(str + "  letterboxReason=" + getLetterboxReasonString(findMainWindow));
        printWriter.println(str + "  activityAspectRatio=" + com.android.server.wm.ActivityRecord.computeAspectRatio(this.mActivityRecord.getBounds()));
        boolean shouldShowLetterboxUi = shouldShowLetterboxUi(findMainWindow);
        printWriter.println(str + "shouldShowLetterboxUi=" + shouldShowLetterboxUi);
        if (!shouldShowLetterboxUi) {
            return;
        }
        printWriter.println(str + "  letterboxBackgroundColor=" + java.lang.Integer.toHexString(getLetterboxBackgroundColor().toArgb()));
        printWriter.println(str + "  letterboxBackgroundType=" + com.android.server.wm.LetterboxConfiguration.letterboxBackgroundTypeToString(this.mLetterboxConfiguration.getLetterboxBackgroundType()));
        printWriter.println(str + "  letterboxCornerRadius=" + getRoundedCornersRadius(findMainWindow));
        if (this.mLetterboxConfiguration.getLetterboxBackgroundType() == 3) {
            printWriter.println(str + "  isLetterboxWallpaperBlurSupported=" + isLetterboxWallpaperBlurSupported());
            printWriter.println(str + "  letterboxBackgroundWallpaperDarkScrimAlpha=" + getLetterboxWallpaperDarkScrimAlpha());
            printWriter.println(str + "  letterboxBackgroundWallpaperBlurRadius=" + getLetterboxWallpaperBlurRadiusPx());
        }
        printWriter.println(str + "  isHorizontalReachabilityEnabled=" + isHorizontalReachabilityEnabled());
        printWriter.println(str + "  isVerticalReachabilityEnabled=" + isVerticalReachabilityEnabled());
        printWriter.println(str + "  letterboxHorizontalPositionMultiplier=" + getHorizontalPositionMultiplier(this.mActivityRecord.getParent().getConfiguration()));
        printWriter.println(str + "  letterboxVerticalPositionMultiplier=" + getVerticalPositionMultiplier(this.mActivityRecord.getParent().getConfiguration()));
        printWriter.println(str + "  letterboxPositionForHorizontalReachability=" + com.android.server.wm.LetterboxConfiguration.letterboxHorizontalReachabilityPositionToString(this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(false)));
        printWriter.println(str + "  letterboxPositionForVerticalReachability=" + com.android.server.wm.LetterboxConfiguration.letterboxVerticalReachabilityPositionToString(this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(false)));
        printWriter.println(str + "  fixedOrientationLetterboxAspectRatio=" + this.mLetterboxConfiguration.getFixedOrientationLetterboxAspectRatio());
        printWriter.println(str + "  defaultMinAspectRatioForUnresizableApps=" + this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps());
        printWriter.println(str + "  isSplitScreenAspectRatioForUnresizableAppsEnabled=" + this.mLetterboxConfiguration.getIsSplitScreenAspectRatioForUnresizableAppsEnabled());
        printWriter.println(str + "  isDisplayAspectRatioEnabledForFixedOrientationLetterbox=" + this.mLetterboxConfiguration.getIsDisplayAspectRatioEnabledForFixedOrientationLetterbox());
    }

    private java.lang.String getLetterboxReasonString(com.android.server.wm.WindowState windowState) {
        if (this.mActivityRecord.inSizeCompatMode()) {
            return "SIZE_COMPAT_MODE";
        }
        if (this.mActivityRecord.isLetterboxedForFixedOrientationAndAspectRatio()) {
            return "FIXED_ORIENTATION";
        }
        if (windowState.isLetterboxedForDisplayCutout()) {
            return "DISPLAY_CUTOUT";
        }
        if (this.mActivityRecord.isAspectRatioApplied()) {
            return "ASPECT_RATIO";
        }
        return "UNKNOWN_REASON";
    }

    private int letterboxHorizontalReachabilityPositionToLetterboxPosition(int i) {
        switch (i) {
            case 0:
                return 3;
            case 1:
                return 2;
            case 2:
                return 4;
            default:
                throw new java.lang.AssertionError("Unexpected letterbox horizontal reachability position type: " + i);
        }
    }

    private int letterboxVerticalReachabilityPositionToLetterboxPosition(int i) {
        switch (i) {
            case 0:
                return 5;
            case 1:
                return 2;
            case 2:
                return 6;
            default:
                throw new java.lang.AssertionError("Unexpected letterbox vertical reachability position type: " + i);
        }
    }

    int getLetterboxPositionForLogging() {
        if (isHorizontalReachabilityEnabled()) {
            return letterboxHorizontalReachabilityPositionToLetterboxPosition(getLetterboxConfiguration().getLetterboxPositionForHorizontalReachability(isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED, false)));
        }
        if (isVerticalReachabilityEnabled()) {
            return letterboxVerticalReachabilityPositionToLetterboxPosition(getLetterboxConfiguration().getLetterboxPositionForVerticalReachability(isDisplayFullScreenAndInPosture(com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED, true)));
        }
        return 0;
    }

    private com.android.server.wm.LetterboxConfiguration getLetterboxConfiguration() {
        return this.mLetterboxConfiguration;
    }

    private void logLetterboxPositionChange(int i) {
        this.mActivityRecord.mTaskSupervisor.getActivityMetricsLogger().logLetterboxPositionChange(this.mActivityRecord, i);
    }

    @android.annotation.Nullable
    com.android.internal.statusbar.LetterboxDetails getLetterboxDetails() {
        com.android.server.wm.WindowState findMainWindow = this.mActivityRecord.findMainWindow();
        if (this.mLetterbox == null || findMainWindow == null || findMainWindow.isLetterboxedForDisplayCutout()) {
            return null;
        }
        android.graphics.Rect rect = new android.graphics.Rect();
        android.graphics.Rect rect2 = new android.graphics.Rect();
        getLetterboxInnerBounds(rect);
        getLetterboxOuterBounds(rect2);
        if (rect.isEmpty() || rect2.isEmpty()) {
            return null;
        }
        return new com.android.internal.statusbar.LetterboxDetails(rect, rect2, findMainWindow.mAttrs.insetsFlags.appearance);
    }

    void updateInheritedLetterbox() {
        final com.android.server.wm.WindowContainer parent = this.mActivityRecord.getParent();
        if (parent == null || !this.mLetterboxConfiguration.isTranslucentLetterboxingEnabled()) {
            return;
        }
        if (this.mLetterboxConfigListener != null) {
            this.mLetterboxConfigListener.onRemoved();
            clearInheritedConfig();
        }
        this.mFirstOpaqueActivityBeneath = this.mActivityRecord.getTask().getActivity(FIRST_OPAQUE_NOT_FINISHING_ACTIVITY_PREDICATE, this.mActivityRecord, false, true);
        if (this.mFirstOpaqueActivityBeneath == null || this.mFirstOpaqueActivityBeneath.isEmbedded()) {
            this.mActivityRecord.recomputeConfiguration();
            return;
        }
        if (this.mActivityRecord.getTask() == null || this.mActivityRecord.fillsParent() || this.mActivityRecord.hasCompatDisplayInsetsWithoutInheritance()) {
            return;
        }
        this.mFirstOpaqueActivityBeneath.mLetterboxUiController.mDestroyListeners.add(this);
        inheritConfiguration(this.mFirstOpaqueActivityBeneath);
        this.mLetterboxConfigListener = com.android.server.wm.WindowContainer.overrideConfigurationPropagation(this.mActivityRecord, this.mFirstOpaqueActivityBeneath, new com.android.server.wm.WindowContainer.ConfigurationMerger() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda1
            @Override // com.android.server.wm.WindowContainer.ConfigurationMerger
            public final android.content.res.Configuration merge(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
                android.content.res.Configuration lambda$updateInheritedLetterbox$13;
                lambda$updateInheritedLetterbox$13 = com.android.server.wm.LetterboxUiController.this.lambda$updateInheritedLetterbox$13(parent, configuration, configuration2);
                return lambda$updateInheritedLetterbox$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.res.Configuration lambda$updateInheritedLetterbox$13(com.android.server.wm.WindowContainer windowContainer, android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        resetTranslucentOverrideConfig(configuration2);
        android.graphics.Rect bounds = windowContainer.getWindowConfiguration().getBounds();
        android.graphics.Rect bounds2 = configuration2.windowConfiguration.getBounds();
        android.graphics.Rect bounds3 = configuration.windowConfiguration.getBounds();
        bounds2.set(bounds.left, bounds.top, bounds.left + bounds3.width(), bounds.top + bounds3.height());
        configuration2.windowConfiguration.setAppBounds(new android.graphics.Rect());
        inheritConfiguration(this.mFirstOpaqueActivityBeneath);
        return configuration2;
    }

    boolean hasInheritedLetterboxBehavior() {
        return this.mLetterboxConfigListener != null;
    }

    boolean hasInheritedOrientation() {
        return hasInheritedLetterboxBehavior() && this.mActivityRecord.getOverrideOrientation() != -1;
    }

    float getInheritedMinAspectRatio() {
        return this.mInheritedMinAspectRatio;
    }

    float getInheritedMaxAspectRatio() {
        return this.mInheritedMaxAspectRatio;
    }

    int getInheritedAppCompatState() {
        return this.mInheritedAppCompatState;
    }

    int getInheritedOrientation() {
        return this.mInheritedOrientation;
    }

    com.android.server.wm.ActivityRecord.CompatDisplayInsets getInheritedCompatDisplayInsets() {
        return this.mInheritedCompatDisplayInsets;
    }

    void clearInheritedCompatDisplayInsets() {
        this.mInheritedCompatDisplayInsets = null;
    }

    boolean applyOnOpaqueActivityBelow(@android.annotation.NonNull final java.util.function.Consumer<com.android.server.wm.ActivityRecord> consumer) {
        return ((java.lang.Boolean) findOpaqueNotFinishingActivityBelow().map(new java.util.function.Function() { // from class: com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$applyOnOpaqueActivityBelow$14;
                lambda$applyOnOpaqueActivityBelow$14 = com.android.server.wm.LetterboxUiController.lambda$applyOnOpaqueActivityBelow$14(consumer, (com.android.server.wm.ActivityRecord) obj);
                return lambda$applyOnOpaqueActivityBelow$14;
            }
        }).orElse(false)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$applyOnOpaqueActivityBelow$14(java.util.function.Consumer consumer, com.android.server.wm.ActivityRecord activityRecord) {
        consumer.accept(activityRecord);
        return true;
    }

    java.util.Optional<com.android.server.wm.ActivityRecord> findOpaqueNotFinishingActivityBelow() {
        if (!hasInheritedLetterboxBehavior() || this.mActivityRecord.getTask() == null) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.ofNullable(this.mFirstOpaqueActivityBeneath);
    }

    private static void resetTranslucentOverrideConfig(android.content.res.Configuration configuration) {
        configuration.orientation = 0;
        configuration.compatScreenWidthDp = 0;
        configuration.screenWidthDp = 0;
        configuration.compatScreenHeightDp = 0;
        configuration.screenHeightDp = 0;
        configuration.compatSmallestScreenWidthDp = 0;
        configuration.smallestScreenWidthDp = 0;
    }

    private void inheritConfiguration(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mActivityRecord.getMinAspectRatio() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            this.mInheritedMinAspectRatio = activityRecord.getMinAspectRatio();
        }
        if (this.mActivityRecord.getMaxAspectRatio() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            this.mInheritedMaxAspectRatio = activityRecord.getMaxAspectRatio();
        }
        this.mInheritedOrientation = activityRecord.getRequestedConfigurationOrientation();
        this.mInheritedAppCompatState = activityRecord.getAppCompatState();
        this.mInheritedCompatDisplayInsets = activityRecord.getCompatDisplayInsets();
    }

    private void clearInheritedConfig() {
        if (this.mFirstOpaqueActivityBeneath != null) {
            this.mFirstOpaqueActivityBeneath.mLetterboxUiController.mDestroyListeners.remove(this);
        }
        this.mFirstOpaqueActivityBeneath = null;
        this.mLetterboxConfigListener = null;
        this.mInheritedMinAspectRatio = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mInheritedMaxAspectRatio = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mInheritedOrientation = 0;
        this.mInheritedAppCompatState = 0;
        this.mInheritedCompatDisplayInsets = null;
    }
}
