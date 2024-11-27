package com.android.server.wm;

/* loaded from: classes3.dex */
final class LetterboxConfiguration {
    static final float DEFAULT_LETTERBOX_ASPECT_RATIO_FOR_MULTI_WINDOW = 1.01f;
    private static final boolean DEFAULT_VALUE_ALLOW_IGNORE_ORIENTATION_REQUEST = true;
    private static final boolean DEFAULT_VALUE_ENABLE_CAMERA_COMPAT_TREATMENT = true;
    private static final boolean DEFAULT_VALUE_ENABLE_COMPAT_FAKE_FOCUS = true;
    private static final boolean DEFAULT_VALUE_ENABLE_DISPLAY_ROTATION_IMMERSIVE_APP_COMPAT_POLICY = true;
    private static final boolean DEFAULT_VALUE_ENABLE_LETTERBOX_BACKGROUND_WALLPAPER = false;
    private static final boolean DEFAULT_VALUE_ENABLE_LETTERBOX_TRANSLUCENT_ACTIVITY = true;
    private static final boolean DEFAULT_VALUE_ENABLE_USER_ASPECT_RATIO_FULLSCREEN = true;
    private static final boolean DEFAULT_VALUE_ENABLE_USER_ASPECT_RATIO_SETTINGS = true;
    private static final java.lang.String KEY_ALLOW_IGNORE_ORIENTATION_REQUEST = "allow_ignore_orientation_request";
    private static final java.lang.String KEY_ENABLE_CAMERA_COMPAT_TREATMENT = "enable_compat_camera_treatment";
    private static final java.lang.String KEY_ENABLE_COMPAT_FAKE_FOCUS = "enable_compat_fake_focus";
    private static final java.lang.String KEY_ENABLE_DISPLAY_ROTATION_IMMERSIVE_APP_COMPAT_POLICY = "enable_display_rotation_immersive_app_compat_policy";
    private static final java.lang.String KEY_ENABLE_LETTERBOX_BACKGROUND_WALLPAPER = "enable_letterbox_background_wallpaper";
    private static final java.lang.String KEY_ENABLE_LETTERBOX_TRANSLUCENT_ACTIVITY = "enable_letterbox_translucent_activity";
    private static final java.lang.String KEY_ENABLE_USER_ASPECT_RATIO_FULLSCREEN = "enable_app_compat_user_aspect_ratio_fullscreen";
    private static final java.lang.String KEY_ENABLE_USER_ASPECT_RATIO_SETTINGS = "enable_app_compat_aspect_ratio_user_settings";
    static final int LETTERBOX_BACKGROUND_APP_COLOR_BACKGROUND = 1;
    static final int LETTERBOX_BACKGROUND_APP_COLOR_BACKGROUND_FLOATING = 2;
    static final int LETTERBOX_BACKGROUND_OVERRIDE_UNSET = -1;
    static final int LETTERBOX_BACKGROUND_SOLID_COLOR = 0;
    static final int LETTERBOX_BACKGROUND_WALLPAPER = 3;
    static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_CENTER = 1;
    static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_LEFT = 0;
    static final int LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_RIGHT = 2;
    static final float LETTERBOX_POSITION_MULTIPLIER_CENTER = 0.5f;
    static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_BOTTOM = 2;
    static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_CENTER = 1;
    static final int LETTERBOX_VERTICAL_REACHABILITY_POSITION_TOP = 0;
    static final float MIN_FIXED_ORIENTATION_LETTERBOX_ASPECT_RATIO = 1.0f;
    private static final java.lang.String TAG = "ActivityTaskManager";
    final android.content.Context mContext;
    private float mDefaultMinAspectRatioForUnresizableApps;
    private int mDefaultPositionForHorizontalReachability;
    private int mDefaultPositionForVerticalReachability;

    @android.annotation.NonNull
    private final com.android.server.wm.SynchedDeviceConfig mDeviceConfig;
    private float mFixedOrientationLetterboxAspectRatio;
    private boolean mIsAutomaticReachabilityInBookModeEnabled;
    private boolean mIsCameraCompatRefreshCycleThroughStopEnabled;
    private final boolean mIsCameraCompatSplitScreenAspectRatioEnabled;
    private boolean mIsCameraCompatTreatmentRefreshEnabled;
    private boolean mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox;
    private boolean mIsEducationEnabled;
    private boolean mIsHorizontalReachabilityEnabled;
    private final boolean mIsPolicyForIgnoringRequestedOrientationEnabled;
    private boolean mIsSplitScreenAspectRatioForUnresizableAppsEnabled;
    private boolean mIsVerticalReachabilityEnabled;
    private int mLetterboxActivityCornersRadius;

    @android.annotation.Nullable
    private android.graphics.Color mLetterboxBackgroundColorOverride;

    @android.annotation.Nullable
    private java.lang.Integer mLetterboxBackgroundColorResourceIdOverride;
    private final int mLetterboxBackgroundType;
    private int mLetterboxBackgroundTypeOverride;
    private int mLetterboxBackgroundWallpaperBlurRadiusPx;
    private float mLetterboxBackgroundWallpaperDarkScrimAlpha;
    private float mLetterboxBookModePositionMultiplier;

    @android.annotation.NonNull
    private final com.android.server.wm.LetterboxConfigurationPersister mLetterboxConfigurationPersister;
    private float mLetterboxHorizontalPositionMultiplier;
    private float mLetterboxTabletopModePositionMultiplier;
    private float mLetterboxVerticalPositionMultiplier;
    private boolean mTranslucentLetterboxingOverrideEnabled;
    private boolean mUserAppAspectRatioFullscreenOverrideEnabled;
    private boolean mUserAppAspectRatioSettingsOverrideEnabled;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface LetterboxBackgroundType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface LetterboxHorizontalReachabilityPosition {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface LetterboxVerticalReachabilityPosition {
    }

    LetterboxConfiguration(@android.annotation.NonNull final android.content.Context context) {
        this(context, new com.android.server.wm.LetterboxConfigurationPersister(new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$new$0;
                lambda$new$0 = com.android.server.wm.LetterboxConfiguration.lambda$new$0(context);
                return lambda$new$0;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$new$1;
                lambda$new$1 = com.android.server.wm.LetterboxConfiguration.lambda$new$1(context);
                return lambda$new$1;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$new$2;
                lambda$new$2 = com.android.server.wm.LetterboxConfiguration.lambda$new$2(context);
                return lambda$new$2;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$new$3;
                lambda$new$3 = com.android.server.wm.LetterboxConfiguration.lambda$new$3(context);
                return lambda$new$3;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$new$0(android.content.Context context) {
        return java.lang.Integer.valueOf(readLetterboxHorizontalReachabilityPositionFromConfig(context, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$new$1(android.content.Context context) {
        return java.lang.Integer.valueOf(readLetterboxVerticalReachabilityPositionFromConfig(context, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$new$2(android.content.Context context) {
        return java.lang.Integer.valueOf(readLetterboxHorizontalReachabilityPositionFromConfig(context, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$new$3(android.content.Context context) {
        return java.lang.Integer.valueOf(readLetterboxVerticalReachabilityPositionFromConfig(context, true));
    }

    @com.android.internal.annotations.VisibleForTesting
    LetterboxConfiguration(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.wm.LetterboxConfigurationPersister letterboxConfigurationPersister) {
        this.mLetterboxBackgroundTypeOverride = -1;
        this.mIsCameraCompatTreatmentRefreshEnabled = true;
        this.mIsCameraCompatRefreshCycleThroughStopEnabled = true;
        this.mContext = context;
        this.mFixedOrientationLetterboxAspectRatio = this.mContext.getResources().getFloat(android.R.dimen.config_defaultBinderHeavyHitterAutoSamplerThreshold);
        this.mLetterboxBackgroundType = readLetterboxBackgroundTypeFromConfig(this.mContext);
        this.mLetterboxActivityCornersRadius = this.mContext.getResources().getInteger(android.R.integer.config_jobSchedulerUserGracePeriod);
        this.mLetterboxBackgroundWallpaperBlurRadiusPx = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.config_displayWhiteBalanceLowLightAmbientColorTemperature);
        this.mLetterboxBackgroundWallpaperDarkScrimAlpha = this.mContext.getResources().getFloat(android.R.dimen.config_displayWhiteBalanceHighLightAmbientColorTemperatureStrong);
        this.mLetterboxHorizontalPositionMultiplier = this.mContext.getResources().getFloat(android.R.dimen.config_hapticChannelMaxVibrationAmplitude);
        this.mLetterboxVerticalPositionMultiplier = this.mContext.getResources().getFloat(android.R.dimen.config_horizontalScrollFactor);
        this.mLetterboxBookModePositionMultiplier = this.mContext.getResources().getFloat(android.R.dimen.config_displayWhiteBalanceLowLightAmbientColorTemperatureStrong);
        this.mLetterboxTabletopModePositionMultiplier = this.mContext.getResources().getFloat(android.R.dimen.config_highResTaskSnapshotScale);
        this.mIsHorizontalReachabilityEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsAutomaticReachabilityInBookModeEnabled);
        this.mIsVerticalReachabilityEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsEducationEnabled);
        this.mIsAutomaticReachabilityInBookModeEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_keepRestrictedProfilesInBackground);
        this.mDefaultPositionForHorizontalReachability = readLetterboxHorizontalReachabilityPositionFromConfig(this.mContext, false);
        this.mDefaultPositionForVerticalReachability = readLetterboxVerticalReachabilityPositionFromConfig(this.mContext, false);
        this.mIsEducationEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_launchCameraOnCameraLensCoverToggle);
        setDefaultMinAspectRatioForUnresizableApps(this.mContext.getResources().getFloat(android.R.dimen.config_fixedOrientationLetterboxAspectRatio));
        this.mIsSplitScreenAspectRatioForUnresizableAppsEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsDisplayRotationImmersiveAppCompatPolicyEnabled);
        this.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox = this.mContext.getResources().getBoolean(android.R.bool.config_keyguardUserSwitcher);
        this.mIsCameraCompatSplitScreenAspectRatioEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_isCompatFakeFocusEnabled);
        this.mIsPolicyForIgnoringRequestedOrientationEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsDisplayAspectRatioForFixedOrientationLetterboxEnabled);
        this.mLetterboxConfigurationPersister = letterboxConfigurationPersister;
        this.mLetterboxConfigurationPersister.start();
        this.mDeviceConfig = com.android.server.wm.SynchedDeviceConfig.builder("window_manager", context.getMainExecutor()).addDeviceConfigEntry(KEY_ENABLE_CAMERA_COMPAT_TREATMENT, true, this.mContext.getResources().getBoolean(android.R.bool.config_isDesktopModeSupported)).addDeviceConfigEntry(KEY_ENABLE_DISPLAY_ROTATION_IMMERSIVE_APP_COMPAT_POLICY, true, this.mContext.getResources().getBoolean(android.R.bool.config_knownNetworksEnabledForService)).addDeviceConfigEntry(KEY_ALLOW_IGNORE_ORIENTATION_REQUEST, true, true).addDeviceConfigEntry(KEY_ENABLE_COMPAT_FAKE_FOCUS, true, this.mContext.getResources().getBoolean(android.R.bool.config_ignoreVibrationsOnWirelessCharger)).addDeviceConfigEntry(KEY_ENABLE_LETTERBOX_TRANSLUCENT_ACTIVITY, true, this.mContext.getResources().getBoolean(android.R.bool.config_leftRightSplitInPortrait)).addDeviceConfigEntry(KEY_ENABLE_USER_ASPECT_RATIO_SETTINGS, true, this.mContext.getResources().getBoolean(android.R.bool.config_appCompatUserAppAspectRatioSettingsIsEnabled)).addDeviceConfigEntry(KEY_ENABLE_LETTERBOX_BACKGROUND_WALLPAPER, false, true).addDeviceConfigEntry(KEY_ENABLE_USER_ASPECT_RATIO_FULLSCREEN, true, this.mContext.getResources().getBoolean(android.R.bool.config_appCompatUserAppAspectRatioFullscreenIsEnabled)).build();
    }

    boolean isIgnoreOrientationRequestAllowed() {
        return this.mDeviceConfig.getFlagValue(KEY_ALLOW_IGNORE_ORIENTATION_REQUEST);
    }

    void setFixedOrientationLetterboxAspectRatio(float f) {
        this.mFixedOrientationLetterboxAspectRatio = f;
    }

    void resetFixedOrientationLetterboxAspectRatio() {
        this.mFixedOrientationLetterboxAspectRatio = this.mContext.getResources().getFloat(android.R.dimen.config_defaultBinderHeavyHitterAutoSamplerThreshold);
    }

    float getFixedOrientationLetterboxAspectRatio() {
        return this.mFixedOrientationLetterboxAspectRatio;
    }

    void resetDefaultMinAspectRatioForUnresizableApps() {
        setDefaultMinAspectRatioForUnresizableApps(this.mContext.getResources().getFloat(android.R.dimen.config_fixedOrientationLetterboxAspectRatio));
    }

    float getDefaultMinAspectRatioForUnresizableApps() {
        return this.mDefaultMinAspectRatioForUnresizableApps;
    }

    void setDefaultMinAspectRatioForUnresizableApps(float f) {
        this.mDefaultMinAspectRatioForUnresizableApps = f;
    }

    void setLetterboxActivityCornersRadius(int i) {
        this.mLetterboxActivityCornersRadius = i;
    }

    void resetLetterboxActivityCornersRadius() {
        this.mLetterboxActivityCornersRadius = this.mContext.getResources().getInteger(android.R.integer.config_jobSchedulerUserGracePeriod);
    }

    boolean isLetterboxActivityCornersRounded() {
        return getLetterboxActivityCornersRadius() != 0;
    }

    int getLetterboxActivityCornersRadius() {
        return this.mLetterboxActivityCornersRadius;
    }

    android.graphics.Color getLetterboxBackgroundColor() {
        int i;
        if (this.mLetterboxBackgroundColorOverride != null) {
            return this.mLetterboxBackgroundColorOverride;
        }
        if (this.mLetterboxBackgroundColorResourceIdOverride != null) {
            i = this.mLetterboxBackgroundColorResourceIdOverride.intValue();
        } else {
            i = android.R.color.car_toast_background;
        }
        return android.graphics.Color.valueOf(this.mContext.getResources().getColor(i));
    }

    void setLetterboxBackgroundColor(android.graphics.Color color) {
        this.mLetterboxBackgroundColorOverride = color;
    }

    void setLetterboxBackgroundColorResourceId(int i) {
        this.mLetterboxBackgroundColorResourceIdOverride = java.lang.Integer.valueOf(i);
    }

    void resetLetterboxBackgroundColor() {
        this.mLetterboxBackgroundColorOverride = null;
        this.mLetterboxBackgroundColorResourceIdOverride = null;
    }

    int getLetterboxBackgroundType() {
        if (this.mLetterboxBackgroundTypeOverride != -1) {
            return this.mLetterboxBackgroundTypeOverride;
        }
        return getDefaultLetterboxBackgroundType();
    }

    void setLetterboxBackgroundTypeOverride(int i) {
        this.mLetterboxBackgroundTypeOverride = i;
    }

    void resetLetterboxBackgroundType() {
        this.mLetterboxBackgroundTypeOverride = -1;
    }

    private int getDefaultLetterboxBackgroundType() {
        if (this.mDeviceConfig.getFlagValue(KEY_ENABLE_LETTERBOX_BACKGROUND_WALLPAPER)) {
            return 3;
        }
        return this.mLetterboxBackgroundType;
    }

    static java.lang.String letterboxBackgroundTypeToString(int i) {
        switch (i) {
            case 0:
                return "LETTERBOX_BACKGROUND_SOLID_COLOR";
            case 1:
                return "LETTERBOX_BACKGROUND_APP_COLOR_BACKGROUND";
            case 2:
                return "LETTERBOX_BACKGROUND_APP_COLOR_BACKGROUND_FLOATING";
            case 3:
                return "LETTERBOX_BACKGROUND_WALLPAPER";
            default:
                return "unknown=" + i;
        }
    }

    private static int readLetterboxBackgroundTypeFromConfig(android.content.Context context) {
        int integer = context.getResources().getInteger(android.R.integer.config_keepPreloadsMinDays);
        if (integer == 0 || integer == 1 || integer == 2 || integer == 3) {
            return integer;
        }
        return 0;
    }

    void setLetterboxBackgroundWallpaperDarkScrimAlpha(float f) {
        this.mLetterboxBackgroundWallpaperDarkScrimAlpha = f;
    }

    void resetLetterboxBackgroundWallpaperDarkScrimAlpha() {
        this.mLetterboxBackgroundWallpaperDarkScrimAlpha = this.mContext.getResources().getFloat(android.R.dimen.config_displayWhiteBalanceHighLightAmbientColorTemperatureStrong);
    }

    float getLetterboxBackgroundWallpaperDarkScrimAlpha() {
        return this.mLetterboxBackgroundWallpaperDarkScrimAlpha;
    }

    void setLetterboxBackgroundWallpaperBlurRadiusPx(int i) {
        this.mLetterboxBackgroundWallpaperBlurRadiusPx = i;
    }

    void resetLetterboxBackgroundWallpaperBlurRadiusPx() {
        this.mLetterboxBackgroundWallpaperBlurRadiusPx = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.config_displayWhiteBalanceLowLightAmbientColorTemperature);
    }

    int getLetterboxBackgroundWallpaperBlurRadiusPx() {
        return this.mLetterboxBackgroundWallpaperBlurRadiusPx;
    }

    float getLetterboxHorizontalPositionMultiplier(boolean z) {
        if (z) {
            if (this.mLetterboxBookModePositionMultiplier < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mLetterboxBookModePositionMultiplier > 1.0f) {
                android.util.Slog.w(TAG, "mLetterboxBookModePositionMultiplier out of bounds (isInBookMode=true): " + this.mLetterboxBookModePositionMultiplier);
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            }
            return this.mLetterboxBookModePositionMultiplier;
        }
        if (this.mLetterboxHorizontalPositionMultiplier < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mLetterboxHorizontalPositionMultiplier > 1.0f) {
            android.util.Slog.w(TAG, "mLetterboxBookModePositionMultiplier out of bounds (isInBookMode=false):" + this.mLetterboxBookModePositionMultiplier);
            return 0.5f;
        }
        return this.mLetterboxHorizontalPositionMultiplier;
    }

    float getLetterboxVerticalPositionMultiplier(boolean z) {
        if (z) {
            return (this.mLetterboxTabletopModePositionMultiplier < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mLetterboxTabletopModePositionMultiplier > 1.0f) ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : this.mLetterboxTabletopModePositionMultiplier;
        }
        if (this.mLetterboxVerticalPositionMultiplier < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mLetterboxVerticalPositionMultiplier > 1.0f) {
            return 0.5f;
        }
        return this.mLetterboxVerticalPositionMultiplier;
    }

    void setLetterboxHorizontalPositionMultiplier(float f) {
        this.mLetterboxHorizontalPositionMultiplier = f;
    }

    void setLetterboxVerticalPositionMultiplier(float f) {
        this.mLetterboxVerticalPositionMultiplier = f;
    }

    void resetLetterboxHorizontalPositionMultiplier() {
        this.mLetterboxHorizontalPositionMultiplier = this.mContext.getResources().getFloat(android.R.dimen.config_hapticChannelMaxVibrationAmplitude);
    }

    void resetLetterboxVerticalPositionMultiplier() {
        this.mLetterboxVerticalPositionMultiplier = this.mContext.getResources().getFloat(android.R.dimen.config_horizontalScrollFactor);
    }

    boolean getIsHorizontalReachabilityEnabled() {
        return this.mIsHorizontalReachabilityEnabled;
    }

    boolean getIsVerticalReachabilityEnabled() {
        return this.mIsVerticalReachabilityEnabled;
    }

    boolean getIsAutomaticReachabilityInBookModeEnabled() {
        return this.mIsAutomaticReachabilityInBookModeEnabled;
    }

    void setIsHorizontalReachabilityEnabled(boolean z) {
        this.mIsHorizontalReachabilityEnabled = z;
    }

    void setIsVerticalReachabilityEnabled(boolean z) {
        this.mIsVerticalReachabilityEnabled = z;
    }

    void setIsAutomaticReachabilityInBookModeEnabled(boolean z) {
        this.mIsAutomaticReachabilityInBookModeEnabled = z;
    }

    void resetIsHorizontalReachabilityEnabled() {
        this.mIsHorizontalReachabilityEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsAutomaticReachabilityInBookModeEnabled);
    }

    void resetIsVerticalReachabilityEnabled() {
        this.mIsVerticalReachabilityEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsEducationEnabled);
    }

    void resetEnabledAutomaticReachabilityInBookMode() {
        this.mIsAutomaticReachabilityInBookModeEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_keepRestrictedProfilesInBackground);
    }

    int getDefaultPositionForHorizontalReachability() {
        return this.mDefaultPositionForHorizontalReachability;
    }

    int getDefaultPositionForVerticalReachability() {
        return this.mDefaultPositionForVerticalReachability;
    }

    void setDefaultPositionForHorizontalReachability(int i) {
        this.mDefaultPositionForHorizontalReachability = i;
    }

    void setDefaultPositionForVerticalReachability(int i) {
        this.mDefaultPositionForVerticalReachability = i;
    }

    void resetDefaultPositionForHorizontalReachability() {
        this.mDefaultPositionForHorizontalReachability = readLetterboxHorizontalReachabilityPositionFromConfig(this.mContext, false);
    }

    void resetDefaultPositionForVerticalReachability() {
        this.mDefaultPositionForVerticalReachability = readLetterboxVerticalReachabilityPositionFromConfig(this.mContext, false);
    }

    void setPersistentLetterboxPositionForHorizontalReachability(boolean z, int i) {
        this.mLetterboxConfigurationPersister.setLetterboxPositionForHorizontalReachability(z, i);
    }

    void setPersistentLetterboxPositionForVerticalReachability(boolean z, int i) {
        this.mLetterboxConfigurationPersister.setLetterboxPositionForVerticalReachability(z, i);
    }

    void resetPersistentLetterboxPositionForHorizontalReachability() {
        this.mLetterboxConfigurationPersister.setLetterboxPositionForHorizontalReachability(false, readLetterboxHorizontalReachabilityPositionFromConfig(this.mContext, false));
        this.mLetterboxConfigurationPersister.setLetterboxPositionForHorizontalReachability(true, readLetterboxHorizontalReachabilityPositionFromConfig(this.mContext, true));
    }

    void resetPersistentLetterboxPositionForVerticalReachability() {
        this.mLetterboxConfigurationPersister.setLetterboxPositionForVerticalReachability(false, readLetterboxVerticalReachabilityPositionFromConfig(this.mContext, false));
        this.mLetterboxConfigurationPersister.setLetterboxPositionForVerticalReachability(true, readLetterboxVerticalReachabilityPositionFromConfig(this.mContext, true));
    }

    private static int readLetterboxHorizontalReachabilityPositionFromConfig(android.content.Context context, boolean z) {
        int i;
        android.content.res.Resources resources = context.getResources();
        if (z) {
            i = android.R.integer.config_keyChordPowerVolumeUp;
        } else {
            i = android.R.integer.config_keyguardDrawnTimeout;
        }
        int integer = resources.getInteger(i);
        if (integer == 0 || integer == 1 || integer == 2) {
            return integer;
        }
        return 1;
    }

    private static int readLetterboxVerticalReachabilityPositionFromConfig(android.content.Context context, boolean z) {
        int i;
        android.content.res.Resources resources = context.getResources();
        if (z) {
            i = android.R.integer.config_letterboxActivityCornersRadius;
        } else {
            i = android.R.integer.config_letterboxBackgroundType;
        }
        int integer = resources.getInteger(i);
        if (integer == 0 || integer == 1 || integer == 2) {
            return integer;
        }
        return 1;
    }

    float getHorizontalMultiplierForReachability(boolean z) {
        int letterboxPositionForHorizontalReachability = this.mLetterboxConfigurationPersister.getLetterboxPositionForHorizontalReachability(z);
        switch (letterboxPositionForHorizontalReachability) {
            case 0:
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            case 1:
                return 0.5f;
            case 2:
                return 1.0f;
            default:
                throw new java.lang.AssertionError("Unexpected letterbox position type: " + letterboxPositionForHorizontalReachability);
        }
    }

    float getVerticalMultiplierForReachability(boolean z) {
        int letterboxPositionForVerticalReachability = this.mLetterboxConfigurationPersister.getLetterboxPositionForVerticalReachability(z);
        switch (letterboxPositionForVerticalReachability) {
            case 0:
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            case 1:
                return 0.5f;
            case 2:
                return 1.0f;
            default:
                throw new java.lang.AssertionError("Unexpected letterbox position type: " + letterboxPositionForVerticalReachability);
        }
    }

    int getLetterboxPositionForHorizontalReachability(boolean z) {
        return this.mLetterboxConfigurationPersister.getLetterboxPositionForHorizontalReachability(z);
    }

    int getLetterboxPositionForVerticalReachability(boolean z) {
        return this.mLetterboxConfigurationPersister.getLetterboxPositionForVerticalReachability(z);
    }

    static java.lang.String letterboxHorizontalReachabilityPositionToString(int i) {
        switch (i) {
            case 0:
                return "LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_LEFT";
            case 1:
                return "LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_CENTER";
            case 2:
                return "LETTERBOX_HORIZONTAL_REACHABILITY_POSITION_RIGHT";
            default:
                throw new java.lang.AssertionError("Unexpected letterbox position type: " + i);
        }
    }

    static java.lang.String letterboxVerticalReachabilityPositionToString(int i) {
        switch (i) {
            case 0:
                return "LETTERBOX_VERTICAL_REACHABILITY_POSITION_TOP";
            case 1:
                return "LETTERBOX_VERTICAL_REACHABILITY_POSITION_CENTER";
            case 2:
                return "LETTERBOX_VERTICAL_REACHABILITY_POSITION_BOTTOM";
            default:
                throw new java.lang.AssertionError("Unexpected letterbox position type: " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$movePositionForHorizontalReachabilityToNextRightStop$4(boolean z, java.lang.Integer num) {
        return java.lang.Integer.valueOf(java.lang.Math.min(num.intValue() + (z ? 2 : 1), 2));
    }

    void movePositionForHorizontalReachabilityToNextRightStop(final boolean z) {
        updatePositionForHorizontalReachability(z, new java.util.function.Function() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$movePositionForHorizontalReachabilityToNextRightStop$4;
                lambda$movePositionForHorizontalReachabilityToNextRightStop$4 = com.android.server.wm.LetterboxConfiguration.lambda$movePositionForHorizontalReachabilityToNextRightStop$4(z, (java.lang.Integer) obj);
                return lambda$movePositionForHorizontalReachabilityToNextRightStop$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$movePositionForHorizontalReachabilityToNextLeftStop$5(boolean z, java.lang.Integer num) {
        return java.lang.Integer.valueOf(java.lang.Math.max(num.intValue() - (z ? 2 : 1), 0));
    }

    void movePositionForHorizontalReachabilityToNextLeftStop(final boolean z) {
        updatePositionForHorizontalReachability(z, new java.util.function.Function() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$movePositionForHorizontalReachabilityToNextLeftStop$5;
                lambda$movePositionForHorizontalReachabilityToNextLeftStop$5 = com.android.server.wm.LetterboxConfiguration.lambda$movePositionForHorizontalReachabilityToNextLeftStop$5(z, (java.lang.Integer) obj);
                return lambda$movePositionForHorizontalReachabilityToNextLeftStop$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$movePositionForVerticalReachabilityToNextBottomStop$6(boolean z, java.lang.Integer num) {
        return java.lang.Integer.valueOf(java.lang.Math.min(num.intValue() + (z ? 2 : 1), 2));
    }

    void movePositionForVerticalReachabilityToNextBottomStop(final boolean z) {
        updatePositionForVerticalReachability(z, new java.util.function.Function() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$movePositionForVerticalReachabilityToNextBottomStop$6;
                lambda$movePositionForVerticalReachabilityToNextBottomStop$6 = com.android.server.wm.LetterboxConfiguration.lambda$movePositionForVerticalReachabilityToNextBottomStop$6(z, (java.lang.Integer) obj);
                return lambda$movePositionForVerticalReachabilityToNextBottomStop$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$movePositionForVerticalReachabilityToNextTopStop$7(boolean z, java.lang.Integer num) {
        return java.lang.Integer.valueOf(java.lang.Math.max(num.intValue() - (z ? 2 : 1), 0));
    }

    void movePositionForVerticalReachabilityToNextTopStop(final boolean z) {
        updatePositionForVerticalReachability(z, new java.util.function.Function() { // from class: com.android.server.wm.LetterboxConfiguration$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$movePositionForVerticalReachabilityToNextTopStop$7;
                lambda$movePositionForVerticalReachabilityToNextTopStop$7 = com.android.server.wm.LetterboxConfiguration.lambda$movePositionForVerticalReachabilityToNextTopStop$7(z, (java.lang.Integer) obj);
                return lambda$movePositionForVerticalReachabilityToNextTopStop$7;
            }
        });
    }

    boolean getIsEducationEnabled() {
        return this.mIsEducationEnabled;
    }

    void setIsEducationEnabled(boolean z) {
        this.mIsEducationEnabled = z;
    }

    void resetIsEducationEnabled() {
        this.mIsEducationEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_launchCameraOnCameraLensCoverToggle);
    }

    boolean getIsSplitScreenAspectRatioForUnresizableAppsEnabled() {
        return this.mIsSplitScreenAspectRatioForUnresizableAppsEnabled;
    }

    boolean getIsDisplayAspectRatioEnabledForFixedOrientationLetterbox() {
        return this.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox;
    }

    void setIsSplitScreenAspectRatioForUnresizableAppsEnabled(boolean z) {
        this.mIsSplitScreenAspectRatioForUnresizableAppsEnabled = z;
    }

    void setIsDisplayAspectRatioEnabledForFixedOrientationLetterbox(boolean z) {
        this.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox = z;
    }

    void resetIsSplitScreenAspectRatioForUnresizableAppsEnabled() {
        this.mIsSplitScreenAspectRatioForUnresizableAppsEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_letterboxIsDisplayRotationImmersiveAppCompatPolicyEnabled);
    }

    void resetIsDisplayAspectRatioEnabledForFixedOrientationLetterbox() {
        this.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox = this.mContext.getResources().getBoolean(android.R.bool.config_keyguardUserSwitcher);
    }

    boolean isTranslucentLetterboxingEnabled() {
        return this.mTranslucentLetterboxingOverrideEnabled || this.mDeviceConfig.getFlagValue(KEY_ENABLE_LETTERBOX_TRANSLUCENT_ACTIVITY);
    }

    void setTranslucentLetterboxingOverrideEnabled(boolean z) {
        this.mTranslucentLetterboxingOverrideEnabled = z;
    }

    void resetTranslucentLetterboxingEnabled() {
        setTranslucentLetterboxingOverrideEnabled(false);
    }

    private void updatePositionForHorizontalReachability(boolean z, java.util.function.Function<java.lang.Integer, java.lang.Integer> function) {
        this.mLetterboxConfigurationPersister.setLetterboxPositionForHorizontalReachability(z, function.apply(java.lang.Integer.valueOf(this.mLetterboxConfigurationPersister.getLetterboxPositionForHorizontalReachability(z))).intValue());
    }

    private void updatePositionForVerticalReachability(boolean z, java.util.function.Function<java.lang.Integer, java.lang.Integer> function) {
        this.mLetterboxConfigurationPersister.setLetterboxPositionForVerticalReachability(z, function.apply(java.lang.Integer.valueOf(this.mLetterboxConfigurationPersister.getLetterboxPositionForVerticalReachability(z))).intValue());
    }

    boolean isCompatFakeFocusEnabled() {
        return this.mDeviceConfig.getFlagValue(KEY_ENABLE_COMPAT_FAKE_FOCUS);
    }

    boolean isPolicyForIgnoringRequestedOrientationEnabled() {
        return this.mIsPolicyForIgnoringRequestedOrientationEnabled;
    }

    boolean isCameraCompatSplitScreenAspectRatioEnabled() {
        return this.mIsCameraCompatSplitScreenAspectRatioEnabled;
    }

    boolean isCameraCompatTreatmentEnabled() {
        return this.mDeviceConfig.getFlagValue(KEY_ENABLE_CAMERA_COMPAT_TREATMENT);
    }

    boolean isCameraCompatTreatmentEnabledAtBuildTime() {
        return this.mDeviceConfig.isBuildTimeFlagEnabled(KEY_ENABLE_CAMERA_COMPAT_TREATMENT);
    }

    boolean isCameraCompatRefreshEnabled() {
        return this.mIsCameraCompatTreatmentRefreshEnabled;
    }

    void setCameraCompatRefreshEnabled(boolean z) {
        this.mIsCameraCompatTreatmentRefreshEnabled = z;
    }

    void resetCameraCompatRefreshEnabled() {
        this.mIsCameraCompatTreatmentRefreshEnabled = true;
    }

    boolean isCameraCompatRefreshCycleThroughStopEnabled() {
        return this.mIsCameraCompatRefreshCycleThroughStopEnabled;
    }

    void setCameraCompatRefreshCycleThroughStopEnabled(boolean z) {
        this.mIsCameraCompatRefreshCycleThroughStopEnabled = z;
    }

    void resetCameraCompatRefreshCycleThroughStopEnabled() {
        this.mIsCameraCompatRefreshCycleThroughStopEnabled = true;
    }

    boolean isDisplayRotationImmersiveAppCompatPolicyEnabledAtBuildTime() {
        return this.mDeviceConfig.isBuildTimeFlagEnabled(KEY_ENABLE_DISPLAY_ROTATION_IMMERSIVE_APP_COMPAT_POLICY);
    }

    boolean isDisplayRotationImmersiveAppCompatPolicyEnabled() {
        return this.mDeviceConfig.getFlagValue(KEY_ENABLE_DISPLAY_ROTATION_IMMERSIVE_APP_COMPAT_POLICY);
    }

    boolean isUserAppAspectRatioSettingsEnabled() {
        return this.mUserAppAspectRatioSettingsOverrideEnabled || this.mDeviceConfig.getFlagValue(KEY_ENABLE_USER_ASPECT_RATIO_SETTINGS);
    }

    void setUserAppAspectRatioSettingsOverrideEnabled(boolean z) {
        this.mUserAppAspectRatioSettingsOverrideEnabled = z;
    }

    void resetUserAppAspectRatioSettingsEnabled() {
        setUserAppAspectRatioSettingsOverrideEnabled(false);
    }

    boolean isUserAppAspectRatioFullscreenEnabled() {
        return isUserAppAspectRatioSettingsEnabled() && (this.mUserAppAspectRatioFullscreenOverrideEnabled || this.mDeviceConfig.getFlagValue(KEY_ENABLE_USER_ASPECT_RATIO_FULLSCREEN));
    }

    void setUserAppAspectRatioFullscreenOverrideEnabled(boolean z) {
        this.mUserAppAspectRatioFullscreenOverrideEnabled = z;
    }

    void resetUserAppAspectRatioFullscreenEnabled() {
        setUserAppAspectRatioFullscreenOverrideEnabled(false);
    }
}
