package com.android.server.display.color;

/* loaded from: classes.dex */
public final class ColorDisplayService extends com.android.server.SystemService {
    private static final com.android.server.display.color.ColorDisplayService.ColorMatrixEvaluator COLOR_MATRIX_EVALUATOR;
    private static final float[] MATRIX_GRAYSCALE;
    static final float[] MATRIX_IDENTITY = new float[16];
    private static final float[] MATRIX_INVERT_COLOR;
    private static final int MSG_APPLY_DISPLAY_WHITE_BALANCE = 5;
    private static final int MSG_APPLY_GLOBAL_SATURATION = 4;
    private static final int MSG_APPLY_NIGHT_DISPLAY_ANIMATED = 3;
    private static final int MSG_APPLY_NIGHT_DISPLAY_IMMEDIATE = 2;
    private static final int MSG_APPLY_REDUCE_BRIGHT_COLORS = 6;
    private static final int MSG_SET_UP = 1;
    private static final int MSG_USER_CHANGED = 0;
    private static final int NOT_SET = -1;
    static final java.lang.String TAG = "ColorDisplayService";
    private final com.android.server.display.color.AppSaturationController mAppSaturationController;
    private boolean mBootCompleted;
    private final java.lang.Object mCctTintApplierLock;
    private android.util.SparseIntArray mColorModeCompositionColorSpaces;
    private android.database.ContentObserver mContentObserver;
    private int mCurrentUser;
    private final com.android.server.display.feature.DisplayManagerFlags mDisplayManagerFlags;
    private com.android.server.display.color.ColorDisplayService.DisplayWhiteBalanceListener mDisplayWhiteBalanceListener;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.display.color.DisplayWhiteBalanceTintController mDisplayWhiteBalanceTintController;
    private final com.android.server.display.color.TintController mGlobalSaturationTintController;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.Handler mHandler;
    private com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode mNightDisplayAutoMode;
    private final com.android.server.display.color.ColorDisplayService.NightDisplayTintController mNightDisplayTintController;
    private com.android.server.display.color.ColorDisplayService.ReduceBrightColorsListener mReduceBrightColorsListener;
    private final com.android.server.display.color.ReduceBrightColorsTintController mReduceBrightColorsTintController;
    private android.database.ContentObserver mUserSetupObserver;

    public interface ColorTransformController {
        void applyAppSaturation(float[] fArr, float[] fArr2);
    }

    public interface DisplayWhiteBalanceListener {
        void onDisplayWhiteBalanceStatusChanged(boolean z);
    }

    public interface ReduceBrightColorsListener {
        void onReduceBrightColorsActivationChanged(boolean z, boolean z2);

        void onReduceBrightColorsStrengthChanged(int i);
    }

    static {
        android.opengl.Matrix.setIdentityM(MATRIX_IDENTITY, 0);
        COLOR_MATRIX_EVALUATOR = new com.android.server.display.color.ColorDisplayService.ColorMatrixEvaluator();
        MATRIX_GRAYSCALE = new float[]{0.2126f, 0.2126f, 0.2126f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0.7152f, 0.7152f, 0.7152f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0.0722f, 0.0722f, 0.0722f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f};
        MATRIX_INVERT_COLOR = new float[]{0.402f, -0.598f, -0.599f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -1.174f, -0.174f, -1.175f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -0.228f, -0.228f, 0.772f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, 1.0f, 1.0f, 1.0f};
    }

    public ColorDisplayService(android.content.Context context) {
        super(context);
        this.mDisplayManagerFlags = new com.android.server.display.feature.DisplayManagerFlags();
        this.mDisplayWhiteBalanceTintController = new com.android.server.display.color.DisplayWhiteBalanceTintController((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class), this.mDisplayManagerFlags);
        this.mNightDisplayTintController = new com.android.server.display.color.ColorDisplayService.NightDisplayTintController();
        this.mGlobalSaturationTintController = new com.android.server.display.color.GlobalSaturationTintController();
        this.mReduceBrightColorsTintController = new com.android.server.display.color.ReduceBrightColorsTintController();
        this.mAppSaturationController = new com.android.server.display.color.AppSaturationController();
        this.mCurrentUser = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mColorModeCompositionColorSpaces = null;
        this.mCctTintApplierLock = new java.lang.Object();
        this.mHandler = new com.android.server.display.color.ColorDisplayService.TintHandler(com.android.server.DisplayThread.get().getLooper());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("color_display", new com.android.server.display.color.ColorDisplayService.BinderService());
        publishLocalService(com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal.class, new com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal());
        publishLocalService(com.android.server.display.color.DisplayTransformManager.class, new com.android.server.display.color.DisplayTransformManager());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i >= 1000) {
            this.mBootCompleted = true;
            if (this.mCurrentUser != -10000 && this.mUserSetupObserver == null) {
                this.mHandler.sendEmptyMessage(1);
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == -10000) {
            android.os.Message obtainMessage = this.mHandler.obtainMessage(0);
            obtainMessage.arg1 = targetUser.getUserIdentifier();
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.arg1 = targetUser2.getUserIdentifier();
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == targetUser.getUserIdentifier()) {
            android.os.Message obtainMessage = this.mHandler.obtainMessage(0);
            obtainMessage.arg1 = com.android.server.am.ProcessList.INVALID_ADJ;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUserChanged(int i) {
        final android.content.ContentResolver contentResolver = getContext().getContentResolver();
        if (this.mCurrentUser != -10000) {
            if (this.mUserSetupObserver != null) {
                contentResolver.unregisterContentObserver(this.mUserSetupObserver);
                this.mUserSetupObserver = null;
            } else if (this.mBootCompleted) {
                tearDown();
            }
        }
        this.mCurrentUser = i;
        if (this.mCurrentUser != -10000) {
            if (!isUserSetupCompleted(contentResolver, this.mCurrentUser)) {
                this.mUserSetupObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.display.color.ColorDisplayService.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, android.net.Uri uri) {
                        if (com.android.server.display.color.ColorDisplayService.isUserSetupCompleted(contentResolver, com.android.server.display.color.ColorDisplayService.this.mCurrentUser)) {
                            contentResolver.unregisterContentObserver(this);
                            com.android.server.display.color.ColorDisplayService.this.mUserSetupObserver = null;
                            if (com.android.server.display.color.ColorDisplayService.this.mBootCompleted) {
                                com.android.server.display.color.ColorDisplayService.this.setUp();
                            }
                        }
                    }
                };
                contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupObserver, this.mCurrentUser);
            } else if (this.mBootCompleted) {
                setUp();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isUserSetupCompleted(android.content.ContentResolver contentResolver, int i) {
        return android.provider.Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, i) == 1;
    }

    private void setUpDisplayCompositionColorSpaces(android.content.res.Resources resources) {
        int[] intArray;
        this.mColorModeCompositionColorSpaces = null;
        int[] intArray2 = resources.getIntArray(android.R.array.config_disabledDreamComponents);
        if (intArray2 == null || (intArray = resources.getIntArray(android.R.array.config_disabledUntilUsedPreinstalledImes)) == null) {
            return;
        }
        if (intArray2.length != intArray.length) {
            android.util.Slog.e(TAG, "Number of composition color spaces doesn't match specified color modes");
            return;
        }
        this.mColorModeCompositionColorSpaces = new android.util.SparseIntArray(intArray2.length);
        for (int i = 0; i < intArray2.length; i++) {
            this.mColorModeCompositionColorSpaces.put(intArray2[i], intArray[i]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUp() {
        android.util.Slog.d(TAG, "setUp: currentUser=" + this.mCurrentUser);
        if (this.mContentObserver == null) {
            this.mContentObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.display.color.ColorDisplayService.2
                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.database.ContentObserver
                public void onChange(boolean z, android.net.Uri uri) {
                    char c;
                    super.onChange(z, uri);
                    java.lang.String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                    if (lastPathSegment != null) {
                        switch (lastPathSegment.hashCode()) {
                            case -2038150513:
                                if (lastPathSegment.equals("night_display_auto_mode")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1761668069:
                                if (lastPathSegment.equals("night_display_custom_end_time")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -969458956:
                                if (lastPathSegment.equals("night_display_color_temperature")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -686921934:
                                if (lastPathSegment.equals("accessibility_display_daltonizer_enabled")) {
                                    c = 7;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -551230169:
                                if (lastPathSegment.equals("accessibility_display_inversion_enabled")) {
                                    c = 6;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 483353904:
                                if (lastPathSegment.equals("accessibility_display_daltonizer")) {
                                    c = '\b';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 800115245:
                                if (lastPathSegment.equals("night_display_activated")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1113469195:
                                if (lastPathSegment.equals("display_white_balance_enabled")) {
                                    c = '\t';
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1300110529:
                                if (lastPathSegment.equals("reduce_bright_colors_level")) {
                                    c = 11;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1561688220:
                                if (lastPathSegment.equals("display_color_mode")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1578271348:
                                if (lastPathSegment.equals("night_display_custom_start_time")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1656644750:
                                if (lastPathSegment.equals("reduce_bright_colors_activated")) {
                                    c = '\n';
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
                                boolean isActivatedSetting = com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
                                if (com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivated() != isActivatedSetting) {
                                    com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.setActivated(java.lang.Boolean.valueOf(isActivatedSetting));
                                    break;
                                }
                                break;
                            case 1:
                                int colorTemperatureSetting = com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.getColorTemperatureSetting();
                                if (com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.getColorTemperature() != colorTemperatureSetting) {
                                    com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.onColorTemperatureChanged(colorTemperatureSetting);
                                    break;
                                }
                                break;
                            case 2:
                                com.android.server.display.color.ColorDisplayService.this.onNightDisplayAutoModeChanged(com.android.server.display.color.ColorDisplayService.this.getNightDisplayAutoModeInternal());
                                break;
                            case 3:
                                com.android.server.display.color.ColorDisplayService.this.onNightDisplayCustomStartTimeChanged(com.android.server.display.color.ColorDisplayService.this.getNightDisplayCustomStartTimeInternal().getLocalTime());
                                break;
                            case 4:
                                com.android.server.display.color.ColorDisplayService.this.onNightDisplayCustomEndTimeChanged(com.android.server.display.color.ColorDisplayService.this.getNightDisplayCustomEndTimeInternal().getLocalTime());
                                break;
                            case 5:
                                com.android.server.display.color.ColorDisplayService.this.onDisplayColorModeChanged(com.android.server.display.color.ColorDisplayService.this.getColorModeInternal());
                                break;
                            case 6:
                                com.android.server.display.color.ColorDisplayService.this.onAccessibilityInversionChanged();
                                com.android.server.display.color.ColorDisplayService.this.onAccessibilityActivated();
                                break;
                            case 7:
                                com.android.server.display.color.ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                com.android.server.display.color.ColorDisplayService.this.onAccessibilityActivated();
                                break;
                            case '\b':
                                com.android.server.display.color.ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                break;
                            case '\t':
                                com.android.server.display.color.ColorDisplayService.this.updateDisplayWhiteBalanceStatus();
                                break;
                            case '\n':
                                com.android.server.display.color.ColorDisplayService.this.onReduceBrightColorsActivationChanged(true);
                                com.android.server.display.color.ColorDisplayService.this.mHandler.sendEmptyMessage(6);
                                break;
                            case 11:
                                com.android.server.display.color.ColorDisplayService.this.onReduceBrightColorsStrengthLevelChanged();
                                com.android.server.display.color.ColorDisplayService.this.mHandler.sendEmptyMessage(6);
                                break;
                        }
                    }
                }
            };
        }
        android.content.ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("night_display_activated"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("night_display_color_temperature"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("night_display_auto_mode"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("night_display_custom_start_time"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("night_display_custom_end_time"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("display_color_mode"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("accessibility_display_inversion_enabled"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("accessibility_display_daltonizer_enabled"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("accessibility_display_daltonizer"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("display_white_balance_enabled"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("reduce_bright_colors_activated"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("reduce_bright_colors_level"), false, this.mContentObserver, this.mCurrentUser);
        onAccessibilityInversionChanged();
        onAccessibilityDaltonizerChanged();
        setUpDisplayCompositionColorSpaces(getContext().getResources());
        onDisplayColorModeChanged(getColorModeInternal());
        com.android.server.display.color.DisplayTransformManager displayTransformManager = (com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class);
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            this.mNightDisplayTintController.setActivated(null);
            this.mNightDisplayTintController.setUp(getContext(), displayTransformManager.needsLinearColorMatrix());
            this.mNightDisplayTintController.setMatrix(this.mNightDisplayTintController.getColorTemperatureSetting());
            onNightDisplayAutoModeChanged(getNightDisplayAutoModeInternal());
            if (this.mNightDisplayTintController.isActivatedStateNotSet()) {
                this.mNightDisplayTintController.setActivated(java.lang.Boolean.valueOf(this.mNightDisplayTintController.isActivatedSetting()));
            }
        }
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            this.mDisplayWhiteBalanceTintController.setUp(getContext(), true);
            updateDisplayWhiteBalanceStatus();
        }
        if (this.mReduceBrightColorsTintController.isAvailable(getContext())) {
            this.mReduceBrightColorsTintController.setUp(getContext(), displayTransformManager.needsLinearColorMatrix());
            onReduceBrightColorsStrengthLevelChanged();
            if (!resetReduceBrightColors()) {
                onReduceBrightColorsActivationChanged(false);
                this.mHandler.sendEmptyMessage(6);
            }
        }
    }

    private void tearDown() {
        android.util.Slog.d(TAG, "tearDown: currentUser=" + this.mCurrentUser);
        if (this.mContentObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mContentObserver);
        }
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            if (this.mNightDisplayAutoMode != null) {
                this.mNightDisplayAutoMode.onStop();
                this.mNightDisplayAutoMode = null;
            }
            this.mNightDisplayTintController.endAnimator();
        }
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            this.mDisplayWhiteBalanceTintController.endAnimator();
        }
        if (this.mGlobalSaturationTintController.isAvailable(getContext())) {
            this.mGlobalSaturationTintController.setActivated(null);
        }
        if (this.mReduceBrightColorsTintController.isAvailable(getContext())) {
            this.mReduceBrightColorsTintController.setActivated(null);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void cancelAllAnimators() {
        this.mNightDisplayTintController.cancelAnimator();
        this.mGlobalSaturationTintController.cancelAnimator();
        this.mReduceBrightColorsTintController.cancelAnimator();
        this.mDisplayWhiteBalanceTintController.cancelAnimator();
    }

    private boolean resetReduceBrightColors() {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        boolean z = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser) == 1;
        boolean z2 = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_persist_across_reboots", 0, this.mCurrentUser) == 0;
        if (z && this.mReduceBrightColorsTintController.isActivatedStateNotSet() && z2) {
            return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNightDisplayAutoModeChanged(int i) {
        android.util.Slog.d(TAG, "onNightDisplayAutoModeChanged: autoMode=" + i);
        if (this.mNightDisplayAutoMode != null) {
            this.mNightDisplayAutoMode.onStop();
            this.mNightDisplayAutoMode = null;
        }
        if (i == 1) {
            this.mNightDisplayAutoMode = new com.android.server.display.color.ColorDisplayService.CustomNightDisplayAutoMode();
        } else if (i == 2) {
            this.mNightDisplayAutoMode = new com.android.server.display.color.ColorDisplayService.TwilightNightDisplayAutoMode();
        }
        if (this.mNightDisplayAutoMode != null) {
            this.mNightDisplayAutoMode.onStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNightDisplayCustomStartTimeChanged(java.time.LocalTime localTime) {
        android.util.Slog.d(TAG, "onNightDisplayCustomStartTimeChanged: startTime=" + localTime);
        if (this.mNightDisplayAutoMode != null) {
            this.mNightDisplayAutoMode.onCustomStartTimeChanged(localTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNightDisplayCustomEndTimeChanged(java.time.LocalTime localTime) {
        android.util.Slog.d(TAG, "onNightDisplayCustomEndTimeChanged: endTime=" + localTime);
        if (this.mNightDisplayAutoMode != null) {
            this.mNightDisplayAutoMode.onCustomEndTimeChanged(localTime);
        }
    }

    private int getCompositionColorSpace(int i) {
        if (this.mColorModeCompositionColorSpaces == null) {
            return -1;
        }
        return this.mColorModeCompositionColorSpaces.get(i, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDisplayColorModeChanged(int i) {
        if (i == -1) {
            return;
        }
        this.mNightDisplayTintController.cancelAnimator();
        this.mDisplayWhiteBalanceTintController.cancelAnimator();
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            this.mNightDisplayTintController.setUp(getContext(), ((com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class)).needsLinearColorMatrix(i));
            this.mNightDisplayTintController.setMatrix(this.mNightDisplayTintController.getColorTemperatureSetting());
        }
        ((com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class)).setColorMode(i, this.mNightDisplayTintController.getMatrix(), getCompositionColorSpace(i));
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            updateDisplayWhiteBalanceStatus();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityActivated() {
        onDisplayColorModeChanged(getColorModeInternal());
    }

    private boolean isAccessiblityDaltonizerEnabled() {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer_enabled", 0, this.mCurrentUser) != 0;
    }

    private boolean isAccessiblityInversionEnabled() {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_inversion_enabled", 0, this.mCurrentUser) != 0;
    }

    private boolean isAccessibilityEnabled() {
        return isAccessiblityDaltonizerEnabled() || isAccessiblityInversionEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityDaltonizerChanged() {
        int i;
        if (this.mCurrentUser == -10000) {
            return;
        }
        if (isAccessiblityDaltonizerEnabled()) {
            i = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer", 12, this.mCurrentUser);
        } else {
            i = -1;
        }
        com.android.server.display.color.DisplayTransformManager displayTransformManager = (com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class);
        if (i == 0) {
            displayTransformManager.setColorMatrix(200, MATRIX_GRAYSCALE);
            displayTransformManager.setDaltonizerMode(-1);
        } else {
            displayTransformManager.setColorMatrix(200, null);
            displayTransformManager.setDaltonizerMode(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessibilityInversionChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        ((com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class)).setColorMatrix(300, isAccessiblityInversionEnabled() ? MATRIX_INVERT_COLOR : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReduceBrightColorsActivationChanged(boolean z) {
        if (this.mCurrentUser == -10000) {
            return;
        }
        boolean z2 = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser) == 1;
        this.mReduceBrightColorsTintController.setActivated(java.lang.Boolean.valueOf(z2));
        if (this.mReduceBrightColorsListener != null) {
            this.mReduceBrightColorsListener.onReduceBrightColorsActivationChanged(z2, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReduceBrightColorsStrengthLevelChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        int intForUser = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_level", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(android.R.integer.config_previousVibrationsDumpAggregationTimeMillisLimit);
        }
        this.mReduceBrightColorsTintController.setMatrix(intForUser);
        if (this.mReduceBrightColorsListener != null) {
            this.mReduceBrightColorsListener.onReduceBrightColorsStrengthChanged(intForUser);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTint(final com.android.server.display.color.TintController tintController, boolean z) {
        tintController.cancelAnimator();
        final com.android.server.display.color.DisplayTransformManager displayTransformManager = (com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class);
        float[] colorMatrix = displayTransformManager.getColorMatrix(tintController.getLevel());
        final float[] matrix = tintController.getMatrix();
        if (z) {
            displayTransformManager.setColorMatrix(tintController.getLevel(), matrix);
            return;
        }
        com.android.server.display.color.ColorDisplayService.ColorMatrixEvaluator colorMatrixEvaluator = COLOR_MATRIX_EVALUATOR;
        if (colorMatrix == null) {
            colorMatrix = MATRIX_IDENTITY;
        }
        com.android.server.display.color.ColorDisplayService.TintValueAnimator ofMatrix = com.android.server.display.color.ColorDisplayService.TintValueAnimator.ofMatrix(colorMatrixEvaluator, colorMatrix, matrix);
        tintController.setAnimator(ofMatrix);
        ofMatrix.setDuration(tintController.getTransitionDurationMilliseconds());
        ofMatrix.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.fast_out_slow_in));
        ofMatrix.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.display.color.ColorDisplayService$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.server.display.color.ColorDisplayService.lambda$applyTint$0(com.android.server.display.color.DisplayTransformManager.this, tintController, valueAnimator);
            }
        });
        ofMatrix.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.server.display.color.ColorDisplayService.3
            private boolean mIsCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                this.mIsCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.server.display.color.ColorDisplayService.TintValueAnimator tintValueAnimator = (com.android.server.display.color.ColorDisplayService.TintValueAnimator) animator;
                android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, tintController.getClass().getSimpleName() + " Animation cancelled: " + this.mIsCancelled + " to matrix: " + com.android.server.display.color.TintController.matrixToString(matrix, 16) + " min matrix coefficients: " + com.android.server.display.color.TintController.matrixToString(tintValueAnimator.getMin(), 16) + " max matrix coefficients: " + com.android.server.display.color.TintController.matrixToString(tintValueAnimator.getMax(), 16));
                if (!this.mIsCancelled) {
                    displayTransformManager.setColorMatrix(tintController.getLevel(), matrix);
                }
                tintController.setAnimator(null);
            }
        });
        ofMatrix.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyTint$0(com.android.server.display.color.DisplayTransformManager displayTransformManager, com.android.server.display.color.TintController tintController, android.animation.ValueAnimator valueAnimator) {
        displayTransformManager.setColorMatrix(tintController.getLevel(), (float[]) valueAnimator.getAnimatedValue());
        ((com.android.server.display.color.ColorDisplayService.TintValueAnimator) valueAnimator).updateMinMaxComponents();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTintByCct(final com.android.server.display.color.ColorTemperatureTintController colorTemperatureTintController, boolean z) {
        synchronized (this.mCctTintApplierLock) {
            try {
                colorTemperatureTintController.cancelAnimator();
                final com.android.server.display.color.DisplayTransformManager displayTransformManager = (com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class);
                final int appliedCct = colorTemperatureTintController.getAppliedCct();
                final int targetCct = colorTemperatureTintController.isActivated() ? colorTemperatureTintController.getTargetCct() : colorTemperatureTintController.getDisabledCct();
                if (z) {
                    android.util.Slog.d(TAG, colorTemperatureTintController.getClass().getSimpleName() + " applied immediately: toCct=" + targetCct + " fromCct=" + appliedCct);
                    displayTransformManager.setColorMatrix(colorTemperatureTintController.getLevel(), colorTemperatureTintController.computeMatrixForCct(targetCct));
                    colorTemperatureTintController.setAppliedCct(targetCct);
                } else {
                    long transitionDurationMilliseconds = colorTemperatureTintController.getTransitionDurationMilliseconds(targetCct > appliedCct);
                    android.util.Slog.d(TAG, colorTemperatureTintController.getClass().getSimpleName() + " animation started: toCct=" + targetCct + " fromCct=" + appliedCct + " with duration=" + transitionDurationMilliseconds);
                    android.animation.ValueAnimator ofInt = android.animation.ValueAnimator.ofInt(appliedCct, targetCct);
                    colorTemperatureTintController.setAnimator(ofInt);
                    com.android.server.display.color.CctEvaluator evaluator = colorTemperatureTintController.getEvaluator();
                    if (evaluator != null) {
                        ofInt.setEvaluator(evaluator);
                    }
                    ofInt.setDuration(transitionDurationMilliseconds);
                    ofInt.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.linear));
                    ofInt.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.display.color.ColorDisplayService$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                            com.android.server.display.color.ColorDisplayService.this.lambda$applyTintByCct$1(colorTemperatureTintController, displayTransformManager, valueAnimator);
                        }
                    });
                    ofInt.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.server.display.color.ColorDisplayService.4
                        private boolean mIsCancelled;

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(android.animation.Animator animator) {
                            android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, colorTemperatureTintController.getClass().getSimpleName() + " animation cancelled");
                            this.mIsCancelled = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animator) {
                            synchronized (com.android.server.display.color.ColorDisplayService.this.mCctTintApplierLock) {
                                try {
                                    android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, colorTemperatureTintController.getClass().getSimpleName() + " animation ended: wasCancelled=" + this.mIsCancelled + " toCct=" + targetCct + " fromCct=" + appliedCct);
                                    if (!this.mIsCancelled) {
                                        displayTransformManager.setColorMatrix(colorTemperatureTintController.getLevel(), colorTemperatureTintController.computeMatrixForCct(targetCct));
                                        colorTemperatureTintController.setAppliedCct(targetCct);
                                    }
                                    colorTemperatureTintController.setAnimator(null);
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    });
                    ofInt.start();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTintByCct$1(com.android.server.display.color.ColorTemperatureTintController colorTemperatureTintController, com.android.server.display.color.DisplayTransformManager displayTransformManager, android.animation.ValueAnimator valueAnimator) {
        synchronized (this.mCctTintApplierLock) {
            try {
                int intValue = ((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue();
                if (intValue != colorTemperatureTintController.getAppliedCct()) {
                    displayTransformManager.setColorMatrix(colorTemperatureTintController.getLevel(), colorTemperatureTintController.computeMatrixForCct(intValue));
                    colorTemperatureTintController.setAppliedCct(intValue);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.time.LocalDateTime getDateTimeBefore(java.time.LocalTime localTime, java.time.LocalDateTime localDateTime) {
        java.time.LocalDateTime of = java.time.LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        return of.isAfter(localDateTime) ? of.minusDays(1L) : of;
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.time.LocalDateTime getDateTimeAfter(java.time.LocalTime localTime, java.time.LocalDateTime localDateTime) {
        java.time.LocalDateTime of = java.time.LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        return of.isBefore(localDateTime) ? of.plusDays(1L) : of;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateDisplayWhiteBalanceStatus() {
        boolean isActivated = this.mDisplayWhiteBalanceTintController.isActivated();
        this.mDisplayWhiteBalanceTintController.setActivated(java.lang.Boolean.valueOf(isDisplayWhiteBalanceSettingEnabled() && !this.mNightDisplayTintController.isActivated() && !isAccessibilityEnabled() && ((com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class)).needsLinearColorMatrix() && this.mDisplayWhiteBalanceTintController.isAllowed()));
        boolean isActivated2 = this.mDisplayWhiteBalanceTintController.isActivated();
        if (this.mDisplayWhiteBalanceListener != null && isActivated != isActivated2) {
            this.mDisplayWhiteBalanceListener.onDisplayWhiteBalanceStatusChanged(isActivated2);
        }
        if (isActivated && !isActivated2) {
            this.mHandler.sendEmptyMessage(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setDisplayWhiteBalanceSettingEnabled(boolean z) {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "display_white_balance_enabled", z ? 1 : 0, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDisplayWhiteBalanceSettingEnabled() {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "display_white_balance_enabled", getContext().getResources().getBoolean(android.R.bool.config_displayWhiteBalanceAvailable) ? 1 : 0, this.mCurrentUser) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setReduceBrightColorsActivatedInternal(boolean z) {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", z ? 1 : 0, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setReduceBrightColorsStrengthInternal(int i) {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_level", i, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceColorManagedInternal() {
        return ((com.android.server.display.color.DisplayTransformManager) getLocalService(com.android.server.display.color.DisplayTransformManager.class)).isDeviceColorManaged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTransformCapabilitiesInternal() {
        int i;
        if (!android.view.SurfaceControl.getProtectedContentSupport()) {
            i = 0;
        } else {
            i = 1;
        }
        android.content.res.Resources resources = getContext().getResources();
        if (resources.getBoolean(android.R.bool.config_sendAudioBecomingNoisy)) {
            i |= 2;
        }
        if (resources.getBoolean(android.R.bool.config_send_satellite_datagram_to_modem_in_demo_mode)) {
            return i | 4;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setNightDisplayAutoModeInternal(int i) {
        if (getNightDisplayAutoModeInternal() != i) {
            android.provider.Settings.Secure.putStringForUser(getContext().getContentResolver(), "night_display_last_activated_time", null, this.mCurrentUser);
        }
        return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "night_display_auto_mode", i, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNightDisplayAutoModeInternal() {
        int nightDisplayAutoModeRawInternal = getNightDisplayAutoModeRawInternal();
        if (nightDisplayAutoModeRawInternal == -1) {
            nightDisplayAutoModeRawInternal = getContext().getResources().getInteger(android.R.integer.config_defaultMediaVibrationIntensity);
        }
        if (nightDisplayAutoModeRawInternal != 0 && nightDisplayAutoModeRawInternal != 1 && nightDisplayAutoModeRawInternal != 2) {
            android.util.Slog.e(TAG, "Invalid autoMode: " + nightDisplayAutoModeRawInternal);
            return 0;
        }
        return nightDisplayAutoModeRawInternal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNightDisplayAutoModeRawInternal() {
        if (this.mCurrentUser == -10000) {
            return -1;
        }
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_auto_mode", -1, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.display.Time getNightDisplayCustomStartTimeInternal() {
        int intForUser = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_custom_start_time", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(android.R.integer.config_defaultNightDisplayAutoMode);
        }
        return new android.hardware.display.Time(java.time.LocalTime.ofSecondOfDay(intForUser / 1000));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setNightDisplayCustomStartTimeInternal(android.hardware.display.Time time) {
        return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "night_display_custom_start_time", time.getLocalTime().toSecondOfDay() * 1000, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.display.Time getNightDisplayCustomEndTimeInternal() {
        int intForUser = android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_custom_end_time", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(android.R.integer.config_defaultMinEmergencyGestureTapDurationMillis);
        }
        return new android.hardware.display.Time(java.time.LocalTime.ofSecondOfDay(intForUser / 1000));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setNightDisplayCustomEndTimeInternal(android.hardware.display.Time time) {
        return android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "night_display_custom_end_time", time.getLocalTime().toSecondOfDay() * 1000, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.time.LocalDateTime getNightDisplayLastActivatedTimeSetting() {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(getContext().getContentResolver(), "night_display_last_activated_time", getContext().getUserId());
        if (stringForUser != null) {
            try {
                return java.time.LocalDateTime.parse(stringForUser);
            } catch (java.time.format.DateTimeParseException e) {
                try {
                    return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(java.lang.Long.parseLong(stringForUser)), java.time.ZoneId.systemDefault());
                } catch (java.lang.NumberFormatException | java.time.DateTimeException e2) {
                }
            }
        }
        return java.time.LocalDateTime.MIN;
    }

    void setSaturationLevelInternal(int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(4);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    boolean setAppSaturationLevelInternal(java.lang.String str, java.lang.String str2, int i) {
        return this.mAppSaturationController.setSaturationLevel(str, str2, this.mCurrentUser, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColorModeInternal(int i) {
        if (!isColorModeAvailable(i)) {
            throw new java.lang.IllegalArgumentException("Invalid colorMode: " + i);
        }
        android.provider.Settings.System.putIntForUser(getContext().getContentResolver(), "display_color_mode", i, this.mCurrentUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getColorModeInternal() {
        int integer;
        android.content.ContentResolver contentResolver = getContext().getContentResolver();
        if (isAccessibilityEnabled() && (integer = getContext().getResources().getInteger(android.R.integer.config_accessibilityColorMode)) >= 0) {
            return integer;
        }
        int intForUser = android.provider.Settings.System.getIntForUser(contentResolver, "display_color_mode", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getCurrentColorModeFromSystemProperties();
        }
        if (isColorModeAvailable(intForUser)) {
            return intForUser;
        }
        int[] intArray = getContext().getResources().getIntArray(android.R.array.config_lteDbmThresholds);
        if (intForUser != -1 && intArray.length > intForUser && isColorModeAvailable(intArray[intForUser])) {
            return intArray[intForUser];
        }
        int[] intArray2 = getContext().getResources().getIntArray(android.R.array.config_availableColorModes);
        if (intArray2.length > 0) {
            return intArray2[0];
        }
        return -1;
    }

    private int getCurrentColorModeFromSystemProperties() {
        int i = android.os.SystemProperties.getInt("persist.sys.sf.native_mode", 0);
        if (i == 0) {
            return "1.0".equals(android.os.SystemProperties.get("persist.sys.sf.color_saturation")) ? 0 : 1;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i >= 256 && i <= 511) {
            return i;
        }
        return -1;
    }

    private boolean isColorModeAvailable(int i) {
        int[] intArray = getContext().getResources().getIntArray(android.R.array.config_availableColorModes);
        if (intArray != null) {
            for (int i2 : intArray) {
                if (i2 == i) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(java.io.PrintWriter printWriter) {
        printWriter.println("COLOR DISPLAY MANAGER dumpsys (color_display)");
        printWriter.println("Night display:");
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mNightDisplayTintController.isActivated());
            printWriter.println("    Color temp: " + this.mNightDisplayTintController.getColorTemperature());
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Global saturation:");
        if (this.mGlobalSaturationTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mGlobalSaturationTintController.isActivated());
        } else {
            printWriter.println("    Not available");
        }
        this.mAppSaturationController.dump(printWriter);
        printWriter.println("Display white balance:");
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mDisplayWhiteBalanceTintController.isActivated());
            this.mDisplayWhiteBalanceTintController.dump(printWriter);
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Reduce bright colors:");
        if (this.mReduceBrightColorsTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mReduceBrightColorsTintController.isActivated());
            this.mReduceBrightColorsTintController.dump(printWriter);
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Color mode: " + getColorModeInternal());
    }

    private abstract class NightDisplayAutoMode {
        public abstract void onActivated(boolean z);

        public abstract void onStart();

        public abstract void onStop();

        private NightDisplayAutoMode() {
        }

        public void onCustomStartTimeChanged(java.time.LocalTime localTime) {
        }

        public void onCustomEndTimeChanged(java.time.LocalTime localTime) {
        }
    }

    private final class CustomNightDisplayAutoMode extends com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode implements android.app.AlarmManager.OnAlarmListener {
        private final android.app.AlarmManager mAlarmManager;
        private java.time.LocalTime mEndTime;
        private java.time.LocalDateTime mLastActivatedTime;
        private java.time.LocalTime mStartTime;
        private final android.content.BroadcastReceiver mTimeChangedReceiver;

        CustomNightDisplayAutoMode() {
            super();
            this.mAlarmManager = (android.app.AlarmManager) com.android.server.display.color.ColorDisplayService.this.getContext().getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
            this.mTimeChangedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.display.color.ColorDisplayService.CustomNightDisplayAutoMode.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    com.android.server.display.color.ColorDisplayService.CustomNightDisplayAutoMode.this.updateActivated();
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateActivated() {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            java.time.LocalDateTime dateTimeBefore = com.android.server.display.color.ColorDisplayService.getDateTimeBefore(this.mStartTime, now);
            java.time.LocalDateTime dateTimeAfter = com.android.server.display.color.ColorDisplayService.getDateTimeAfter(this.mEndTime, dateTimeBefore);
            boolean isBefore = now.isBefore(dateTimeAfter);
            if (this.mLastActivatedTime != null && this.mLastActivatedTime.isBefore(now) && this.mLastActivatedTime.isAfter(dateTimeBefore) && (this.mLastActivatedTime.isAfter(dateTimeAfter) || now.isBefore(dateTimeAfter))) {
                isBefore = com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
            }
            if (com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivated() != isBefore) {
                com.android.server.display.color.ColorDisplayService.NightDisplayTintController nightDisplayTintController = com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController;
                java.lang.Boolean valueOf = java.lang.Boolean.valueOf(isBefore);
                if (!isBefore) {
                    dateTimeBefore = dateTimeAfter;
                }
                nightDisplayTintController.setActivated(valueOf, dateTimeBefore);
            }
            updateNextAlarm(java.lang.Boolean.valueOf(com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivated()), now);
        }

        /* JADX WARN: Type inference failed for: r8v5, types: [java.time.ZonedDateTime] */
        private void updateNextAlarm(@android.annotation.Nullable java.lang.Boolean bool, @android.annotation.NonNull java.time.LocalDateTime localDateTime) {
            if (bool != null) {
                this.mAlarmManager.setExact(1, (bool.booleanValue() ? com.android.server.display.color.ColorDisplayService.getDateTimeAfter(this.mEndTime, localDateTime) : com.android.server.display.color.ColorDisplayService.getDateTimeAfter(this.mStartTime, localDateTime)).atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli(), com.android.server.display.color.ColorDisplayService.TAG, this, null);
            }
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStart() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            com.android.server.display.color.ColorDisplayService.this.getContext().registerReceiver(this.mTimeChangedReceiver, intentFilter);
            this.mStartTime = com.android.server.display.color.ColorDisplayService.this.getNightDisplayCustomStartTimeInternal().getLocalTime();
            this.mEndTime = com.android.server.display.color.ColorDisplayService.this.getNightDisplayCustomEndTimeInternal().getLocalTime();
            this.mLastActivatedTime = com.android.server.display.color.ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStop() {
            com.android.server.display.color.ColorDisplayService.this.getContext().unregisterReceiver(this.mTimeChangedReceiver);
            this.mAlarmManager.cancel(this);
            this.mLastActivatedTime = null;
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onActivated(boolean z) {
            this.mLastActivatedTime = com.android.server.display.color.ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
            updateNextAlarm(java.lang.Boolean.valueOf(z), java.time.LocalDateTime.now());
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onCustomStartTimeChanged(java.time.LocalTime localTime) {
            this.mStartTime = localTime;
            this.mLastActivatedTime = null;
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onCustomEndTimeChanged(java.time.LocalTime localTime) {
            this.mEndTime = localTime;
            this.mLastActivatedTime = null;
            updateActivated();
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, "onAlarm");
            updateActivated();
        }
    }

    private final class TwilightNightDisplayAutoMode extends com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode implements com.android.server.twilight.TwilightListener {
        private java.time.LocalDateTime mLastActivatedTime;
        private final com.android.server.twilight.TwilightManager mTwilightManager;

        TwilightNightDisplayAutoMode() {
            super();
            this.mTwilightManager = (com.android.server.twilight.TwilightManager) com.android.server.display.color.ColorDisplayService.this.getLocalService(com.android.server.twilight.TwilightManager.class);
        }

        private void updateActivated(com.android.server.twilight.TwilightState twilightState) {
            if (twilightState == null) {
                return;
            }
            boolean isNight = twilightState.isNight();
            if (this.mLastActivatedTime != null) {
                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                java.time.LocalDateTime sunrise = twilightState.sunrise();
                java.time.LocalDateTime sunset = twilightState.sunset();
                if (this.mLastActivatedTime.isBefore(now)) {
                    if (this.mLastActivatedTime.isBefore(sunset) ^ this.mLastActivatedTime.isBefore(sunrise)) {
                        isNight = com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
                    }
                }
            }
            if (com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivated() != isNight) {
                com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.setActivated(java.lang.Boolean.valueOf(isNight));
            }
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onActivated(boolean z) {
            this.mLastActivatedTime = com.android.server.display.color.ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStart() {
            this.mTwilightManager.registerListener(this, com.android.server.display.color.ColorDisplayService.this.mHandler);
            this.mLastActivatedTime = com.android.server.display.color.ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
            updateActivated(this.mTwilightManager.getLastTwilightState());
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStop() {
            this.mTwilightManager.unregisterListener(this);
            this.mLastActivatedTime = null;
        }

        @Override // com.android.server.twilight.TwilightListener
        public void onTwilightStateChanged(@android.annotation.Nullable com.android.server.twilight.TwilightState twilightState) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("onTwilightStateChanged: isNight=");
            sb.append(twilightState == null ? null : java.lang.Boolean.valueOf(twilightState.isNight()));
            android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, sb.toString());
            updateActivated(twilightState);
        }
    }

    static class TintValueAnimator extends android.animation.ValueAnimator {
        private float[] max;
        private float[] min;

        TintValueAnimator() {
        }

        public static com.android.server.display.color.ColorDisplayService.TintValueAnimator ofMatrix(com.android.server.display.color.ColorDisplayService.ColorMatrixEvaluator colorMatrixEvaluator, java.lang.Object... objArr) {
            com.android.server.display.color.ColorDisplayService.TintValueAnimator tintValueAnimator = new com.android.server.display.color.ColorDisplayService.TintValueAnimator();
            tintValueAnimator.setObjectValues(objArr);
            tintValueAnimator.setEvaluator(colorMatrixEvaluator);
            if (objArr == null || objArr.length == 0) {
                return null;
            }
            float[] fArr = (float[]) objArr[0];
            tintValueAnimator.min = new float[fArr.length];
            tintValueAnimator.max = new float[fArr.length];
            for (int i = 0; i < fArr.length; i++) {
                tintValueAnimator.min[i] = Float.MAX_VALUE;
                tintValueAnimator.max[i] = Float.MIN_VALUE;
            }
            return tintValueAnimator;
        }

        public void updateMinMaxComponents() {
            float[] fArr = (float[]) getAnimatedValue();
            if (fArr == null) {
                return;
            }
            for (int i = 0; i < fArr.length; i++) {
                this.min[i] = java.lang.Math.min(this.min[i], fArr[i]);
                this.max[i] = java.lang.Math.max(this.max[i], fArr[i]);
            }
        }

        public float[] getMin() {
            return this.min;
        }

        public float[] getMax() {
            return this.max;
        }
    }

    private static class ColorMatrixEvaluator implements android.animation.TypeEvaluator<float[]> {
        private final float[] mResultMatrix;

        private ColorMatrixEvaluator() {
            this.mResultMatrix = new float[16];
        }

        @Override // android.animation.TypeEvaluator
        public float[] evaluate(float f, float[] fArr, float[] fArr2) {
            for (int i = 0; i < this.mResultMatrix.length; i++) {
                this.mResultMatrix[i] = android.util.MathUtils.lerp(fArr[i], fArr2[i], f);
            }
            return this.mResultMatrix;
        }
    }

    private final class NightDisplayTintController extends com.android.server.display.color.TintController {
        private java.lang.Integer mColorTemp;
        private final float[] mColorTempCoefficients;
        private java.lang.Boolean mIsAvailable;
        private final float[] mMatrix;

        private NightDisplayTintController() {
            this.mMatrix = new float[16];
            this.mColorTempCoefficients = new float[9];
        }

        @Override // com.android.server.display.color.TintController
        public void setUp(android.content.Context context, boolean z) {
            int i;
            android.content.res.Resources resources = context.getResources();
            if (z) {
                i = android.R.array.config_networkNotifySwitches;
            } else {
                i = android.R.array.config_networkSupportedKeepaliveCount;
            }
            java.lang.String[] stringArray = resources.getStringArray(i);
            for (int i2 = 0; i2 < 9 && i2 < stringArray.length; i2++) {
                this.mColorTempCoefficients[i2] = java.lang.Float.parseFloat(stringArray[i2]);
            }
        }

        @Override // com.android.server.display.color.TintController
        public void setMatrix(int i) {
            if (this.mMatrix.length != 16) {
                android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, "The display transformation matrix must be 4x4");
                return;
            }
            android.opengl.Matrix.setIdentityM(this.mMatrix, 0);
            float f = i * i;
            float f2 = i;
            float f3 = (this.mColorTempCoefficients[0] * f) + (this.mColorTempCoefficients[1] * f2) + this.mColorTempCoefficients[2];
            float f4 = (this.mColorTempCoefficients[3] * f) + (this.mColorTempCoefficients[4] * f2) + this.mColorTempCoefficients[5];
            float f5 = (f * this.mColorTempCoefficients[6]) + (f2 * this.mColorTempCoefficients[7]) + this.mColorTempCoefficients[8];
            this.mMatrix[0] = f3;
            this.mMatrix[5] = f4;
            this.mMatrix[10] = f5;
        }

        @Override // com.android.server.display.color.TintController
        public float[] getMatrix() {
            return isActivated() ? this.mMatrix : com.android.server.display.color.ColorDisplayService.MATRIX_IDENTITY;
        }

        @Override // com.android.server.display.color.TintController
        public void setActivated(java.lang.Boolean bool) {
            setActivated(bool, java.time.LocalDateTime.now());
        }

        public void setActivated(java.lang.Boolean bool, @android.annotation.NonNull java.time.LocalDateTime localDateTime) {
            if (bool == null) {
                super.setActivated(null);
                return;
            }
            boolean z = bool.booleanValue() != isActivated();
            if (!isActivatedStateNotSet() && z) {
                android.provider.Settings.Secure.putStringForUser(com.android.server.display.color.ColorDisplayService.this.getContext().getContentResolver(), "night_display_last_activated_time", localDateTime.toString(), com.android.server.display.color.ColorDisplayService.this.mCurrentUser);
            }
            if (isActivatedStateNotSet() || z) {
                super.setActivated(bool);
                if (isActivatedSetting() != bool.booleanValue()) {
                    android.provider.Settings.Secure.putIntForUser(com.android.server.display.color.ColorDisplayService.this.getContext().getContentResolver(), "night_display_activated", bool.booleanValue() ? 1 : 0, com.android.server.display.color.ColorDisplayService.this.mCurrentUser);
                }
                onActivated(bool.booleanValue());
            }
        }

        @Override // com.android.server.display.color.TintController
        public int getLevel() {
            return 100;
        }

        @Override // com.android.server.display.color.TintController
        public boolean isAvailable(android.content.Context context) {
            if (this.mIsAvailable == null) {
                this.mIsAvailable = java.lang.Boolean.valueOf(android.hardware.display.ColorDisplayManager.isNightDisplayAvailable(context));
            }
            return this.mIsAvailable.booleanValue();
        }

        private void onActivated(boolean z) {
            android.util.Slog.i(com.android.server.display.color.ColorDisplayService.TAG, z ? "Turning on night display" : "Turning off night display");
            if (com.android.server.display.color.ColorDisplayService.this.mNightDisplayAutoMode != null) {
                com.android.server.display.color.ColorDisplayService.this.mNightDisplayAutoMode.onActivated(z);
            }
            if (com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController.isAvailable(com.android.server.display.color.ColorDisplayService.this.getContext())) {
                com.android.server.display.color.ColorDisplayService.this.updateDisplayWhiteBalanceStatus();
            }
            com.android.server.display.color.ColorDisplayService.this.mHandler.sendEmptyMessage(3);
        }

        int getColorTemperature() {
            return this.mColorTemp != null ? clampNightDisplayColorTemperature(this.mColorTemp.intValue()) : getColorTemperatureSetting();
        }

        boolean setColorTemperature(int i) {
            this.mColorTemp = java.lang.Integer.valueOf(i);
            boolean putIntForUser = android.provider.Settings.Secure.putIntForUser(com.android.server.display.color.ColorDisplayService.this.getContext().getContentResolver(), "night_display_color_temperature", i, com.android.server.display.color.ColorDisplayService.this.mCurrentUser);
            onColorTemperatureChanged(i);
            return putIntForUser;
        }

        void onColorTemperatureChanged(int i) {
            setMatrix(i);
            com.android.server.display.color.ColorDisplayService.this.mHandler.sendEmptyMessage(2);
        }

        boolean isActivatedSetting() {
            return com.android.server.display.color.ColorDisplayService.this.mCurrentUser != -10000 && android.provider.Settings.Secure.getIntForUser(com.android.server.display.color.ColorDisplayService.this.getContext().getContentResolver(), "night_display_activated", 0, com.android.server.display.color.ColorDisplayService.this.mCurrentUser) == 1;
        }

        int getColorTemperatureSetting() {
            if (com.android.server.display.color.ColorDisplayService.this.mCurrentUser == -10000) {
                return -1;
            }
            return clampNightDisplayColorTemperature(android.provider.Settings.Secure.getIntForUser(com.android.server.display.color.ColorDisplayService.this.getContext().getContentResolver(), "night_display_color_temperature", -1, com.android.server.display.color.ColorDisplayService.this.mCurrentUser));
        }

        private int clampNightDisplayColorTemperature(int i) {
            if (i == -1) {
                i = com.android.server.display.color.ColorDisplayService.this.getContext().getResources().getInteger(android.R.integer.config_networkPolicyDefaultWarning);
            }
            int minimumColorTemperature = android.hardware.display.ColorDisplayManager.getMinimumColorTemperature(com.android.server.display.color.ColorDisplayService.this.getContext());
            int maximumColorTemperature = android.hardware.display.ColorDisplayManager.getMaximumColorTemperature(com.android.server.display.color.ColorDisplayService.this.getContext());
            if (i < minimumColorTemperature) {
                return minimumColorTemperature;
            }
            if (i > maximumColorTemperature) {
                return maximumColorTemperature;
            }
            return i;
        }
    }

    public class ColorDisplayServiceInternal {
        public ColorDisplayServiceInternal() {
        }

        public void setDisplayWhiteBalanceAllowed(boolean z) {
            com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController.setAllowed(z);
            com.android.server.display.color.ColorDisplayService.this.updateDisplayWhiteBalanceStatus();
        }

        public boolean setDisplayWhiteBalanceColorTemperature(int i) {
            com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController.setTargetCct(i);
            if (com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController.isActivated()) {
                com.android.server.display.color.ColorDisplayService.this.mHandler.sendEmptyMessage(5);
                return true;
            }
            return false;
        }

        public float getDisplayWhiteBalanceLuminance() {
            return com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController.getLuminance();
        }

        public boolean resetDisplayWhiteBalanceColorTemperature() {
            int integer = com.android.server.display.color.ColorDisplayService.this.getContext().getResources().getInteger(android.R.integer.config_deviceStateConcurrentRearDisplay);
            android.util.Slog.d(com.android.server.display.color.ColorDisplayService.TAG, "resetDisplayWhiteBalanceColorTemperature: " + integer);
            return setDisplayWhiteBalanceColorTemperature(integer);
        }

        public boolean setDisplayWhiteBalanceListener(com.android.server.display.color.ColorDisplayService.DisplayWhiteBalanceListener displayWhiteBalanceListener) {
            com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceListener = displayWhiteBalanceListener;
            return com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController.isActivated();
        }

        public boolean isDisplayWhiteBalanceEnabled() {
            return com.android.server.display.color.ColorDisplayService.this.isDisplayWhiteBalanceSettingEnabled();
        }

        public boolean setReduceBrightColorsListener(com.android.server.display.color.ColorDisplayService.ReduceBrightColorsListener reduceBrightColorsListener) {
            com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsListener = reduceBrightColorsListener;
            return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.isActivated();
        }

        public boolean isReduceBrightColorsActivated() {
            return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.isActivated();
        }

        public int getReduceBrightColorsStrength() {
            return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.getStrength();
        }

        public float getReduceBrightColorsAdjustedBrightnessNits(float f) {
            return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.getAdjustedBrightness(f);
        }

        public boolean attachColorTransformController(java.lang.String str, int i, java.lang.ref.WeakReference<com.android.server.display.color.ColorDisplayService.ColorTransformController> weakReference) {
            return com.android.server.display.color.ColorDisplayService.this.mAppSaturationController.addColorTransformController(str, i, weakReference);
        }
    }

    private final class TintHandler extends android.os.Handler {
        private TintHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.display.color.ColorDisplayService.this.onUserChanged(message.arg1);
                    break;
                case 1:
                    com.android.server.display.color.ColorDisplayService.this.setUp();
                    break;
                case 2:
                    com.android.server.display.color.ColorDisplayService.this.applyTint(com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController, true);
                    break;
                case 3:
                    com.android.server.display.color.ColorDisplayService.this.applyTint(com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController, false);
                    break;
                case 4:
                    com.android.server.display.color.ColorDisplayService.this.mGlobalSaturationTintController.setMatrix(message.arg1);
                    com.android.server.display.color.ColorDisplayService.this.applyTint(com.android.server.display.color.ColorDisplayService.this.mGlobalSaturationTintController, false);
                    break;
                case 5:
                    com.android.server.display.color.ColorDisplayService.this.applyTintByCct(com.android.server.display.color.ColorDisplayService.this.mDisplayWhiteBalanceTintController, false);
                    break;
                case 6:
                    com.android.server.display.color.ColorDisplayService.this.applyTint(com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController, true);
                    break;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class BinderService extends android.hardware.display.IColorDisplayManager.Stub {
        BinderService() {
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public void setColorMode(int i) {
            setColorMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.color.ColorDisplayService.this.setColorModeInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getColorMode() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.getColorModeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDeviceColorManaged() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.isDeviceColorManagedInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setSaturationLevel(int i) {
            boolean z = com.android.server.display.color.ColorDisplayService.this.getContext().checkCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS") == 0;
            boolean z2 = com.android.server.display.color.ColorDisplayService.this.getContext().checkCallingPermission("android.permission.CONTROL_DISPLAY_SATURATION") == 0;
            if (!z && !z2) {
                throw new java.lang.SecurityException("Permission required to set display saturation level");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.color.ColorDisplayService.this.setSaturationLevelInternal(i);
                return true;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean isSaturationActivated() {
            boolean z;
            super.isSaturationActivated_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.display.color.ColorDisplayService.this.mGlobalSaturationTintController.isActivatedStateNotSet()) {
                    if (com.android.server.display.color.ColorDisplayService.this.mGlobalSaturationTintController.isActivated()) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setAppSaturationLevel(java.lang.String str, int i) {
            super.setAppSaturationLevel_enforcePermission();
            java.lang.String nameForUid = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getNameForUid(android.os.Binder.getCallingUid());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setAppSaturationLevelInternal(nameForUid, str, i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public int getTransformCapabilities() {
            super.getTransformCapabilities_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.getTransformCapabilitiesInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setNightDisplayActivated(boolean z) {
            setNightDisplayActivated_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.setActivated(java.lang.Boolean.valueOf(z));
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean isNightDisplayActivated() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.isActivated();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setNightDisplayColorTemperature(int i) {
            setNightDisplayColorTemperature_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.setColorTemperature(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightDisplayColorTemperature() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.mNightDisplayTintController.getColorTemperature();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setNightDisplayAutoMode(int i) {
            setNightDisplayAutoMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setNightDisplayAutoModeInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public int getNightDisplayAutoMode() {
            getNightDisplayAutoMode_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.getNightDisplayAutoModeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightDisplayAutoModeRaw() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.getNightDisplayAutoModeRawInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setNightDisplayCustomStartTime(android.hardware.display.Time time) {
            setNightDisplayCustomStartTime_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setNightDisplayCustomStartTimeInternal(time);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.display.Time getNightDisplayCustomStartTime() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.getNightDisplayCustomStartTimeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setNightDisplayCustomEndTime(android.hardware.display.Time time) {
            setNightDisplayCustomEndTime_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setNightDisplayCustomEndTimeInternal(time);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.hardware.display.Time getNightDisplayCustomEndTime() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.getNightDisplayCustomEndTimeInternal();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setDisplayWhiteBalanceEnabled(boolean z) {
            setDisplayWhiteBalanceEnabled_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setDisplayWhiteBalanceSettingEnabled(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDisplayWhiteBalanceEnabled() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.isDisplayWhiteBalanceSettingEnabled();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isReduceBrightColorsActivated() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.isActivated();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setReduceBrightColorsActivated(boolean z) {
            setReduceBrightColorsActivated_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setReduceBrightColorsActivatedInternal(z);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getReduceBrightColorsStrength() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.getStrength();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public float getReduceBrightColorsOffsetFactor() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.mReduceBrightColorsTintController.getOffsetFactor();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS")
        public boolean setReduceBrightColorsStrength(int i) {
            setReduceBrightColorsStrength_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.display.color.ColorDisplayService.this.setReduceBrightColorsStrengthInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.display.color.ColorDisplayService.this.getContext(), com.android.server.display.color.ColorDisplayService.TAG, printWriter)) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.display.color.ColorDisplayService.this.dumpInternal(printWriter);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, java.lang.String[] strArr) {
            com.android.server.display.color.ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to use ADB color transform commands");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return new com.android.server.display.color.ColorDisplayShellCommand(com.android.server.display.color.ColorDisplayService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
