package com.android.server.vibrator;

/* loaded from: classes2.dex */
public final class HapticFeedbackVibrationProvider {
    private static final java.lang.String TAG = "HapticFeedbackVibrationProvider";

    @android.annotation.Nullable
    private final android.util.SparseArray<android.os.VibrationEffect> mHapticCustomizations;
    private final boolean mHapticTextHandleEnabled;
    private float mKeyboardVibrationFixedAmplitude;
    private final android.os.VibrationEffect mSafeModeEnabledVibrationEffect;
    private final android.os.VibratorInfo mVibratorInfo;
    private static final android.os.VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(18);
    private static final android.os.VibrationAttributes PHYSICAL_EMULATION_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(34);
    private static final android.os.VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(50);

    public HapticFeedbackVibrationProvider(android.content.res.Resources resources, android.os.Vibrator vibrator) {
        this(resources, vibrator.getInfo());
    }

    public HapticFeedbackVibrationProvider(android.content.res.Resources resources, android.os.VibratorInfo vibratorInfo) {
        this(resources, vibratorInfo, loadHapticCustomizations(resources, vibratorInfo));
    }

    @com.android.internal.annotations.VisibleForTesting
    HapticFeedbackVibrationProvider(android.content.res.Resources resources, android.os.VibratorInfo vibratorInfo, @android.annotation.Nullable android.util.SparseArray<android.os.VibrationEffect> sparseArray) {
        android.os.VibrationEffect createEffectFromResource;
        this.mVibratorInfo = vibratorInfo;
        this.mHapticTextHandleEnabled = resources.getBoolean(android.R.bool.config_enableGeofenceOverlay);
        if (sparseArray != null && sparseArray.size() == 0) {
            sparseArray = null;
        }
        this.mHapticCustomizations = sparseArray;
        if (effectHasCustomization(com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG)) {
            createEffectFromResource = this.mHapticCustomizations.get(com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG);
        } else {
            createEffectFromResource = com.android.server.vibrator.VibrationSettings.createEffectFromResource(resources, android.R.array.config_roundedCornerRadiusArray);
        }
        this.mSafeModeEnabledVibrationEffect = createEffectFromResource;
        this.mKeyboardVibrationFixedAmplitude = resources.getFloat(android.R.dimen.config_displayWhiteBalanceHighLightAmbientColorTemperature);
        if (this.mKeyboardVibrationFixedAmplitude < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mKeyboardVibrationFixedAmplitude > 1.0f) {
            this.mKeyboardVibrationFixedAmplitude = -1.0f;
        }
    }

    @android.annotation.Nullable
    public android.os.VibrationEffect getVibrationForHapticFeedback(int i) {
        switch (i) {
            case 0:
            case 14:
            case 25:
            case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG /* 10003 */:
                return getVibration(i, 5);
            case 1:
            case 5:
            case 12:
            case 15:
            case 16:
            case 19:
            case 20:
                return getVibration(i, 0);
            case 3:
            case 7:
                return getKeyboardVibration(i);
            case 4:
            case 27:
                break;
            case 6:
            case 13:
            case 18:
            case 23:
            case 26:
                return getVibration(i, 2);
            case 8:
            case 10:
            case 11:
                return getVibration(i, 2, false);
            case 9:
                if (!this.mHapticTextHandleEnabled) {
                    return null;
                }
                break;
            case 17:
                return getVibration(i, 1);
            case 21:
                return getVibration(i, 7, 0.5f, 2);
            case 22:
                return getVibration(i, 8, 0.2f, 21);
            case 24:
                return getVibration(i, 7, 0.4f, 21);
            case com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG /* 10001 */:
                return this.mSafeModeEnabledVibrationEffect;
            case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER /* 10002 */:
                return getAssistantButtonVibration();
            default:
                return null;
        }
        return getVibration(i, 21);
    }

    public android.os.VibrationAttributes getVibrationAttributesForHapticFeedback(int i, boolean z, boolean z2) {
        android.os.VibrationAttributes createKeyboardVibrationAttributes;
        int i2;
        switch (i) {
            case 3:
            case 7:
                createKeyboardVibrationAttributes = createKeyboardVibrationAttributes(z2);
                break;
            case 14:
            case 15:
                createKeyboardVibrationAttributes = PHYSICAL_EMULATION_VIBRATION_ATTRIBUTES;
                break;
            case 18:
            case 19:
            case 20:
            case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER /* 10002 */:
            case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG /* 10003 */:
                createKeyboardVibrationAttributes = HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES;
                break;
            default:
                createKeyboardVibrationAttributes = TOUCH_VIBRATION_ATTRIBUTES;
                break;
        }
        if (!z) {
            i2 = 0;
        } else {
            i2 = 2;
        }
        if (shouldBypassInterruptionPolicy(i)) {
            i2 |= 1;
        }
        if (shouldBypassIntensityScale(i, z2)) {
            i2 |= 16;
        }
        return i2 == 0 ? createKeyboardVibrationAttributes : new android.os.VibrationAttributes.Builder(createKeyboardVibrationAttributes).setFlags(i2).build();
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print("mHapticTextHandleEnabled=");
        printWriter.println(this.mHapticTextHandleEnabled);
    }

    private android.os.VibrationEffect getVibration(int i, int i2) {
        return getVibration(i, i2, true);
    }

    private android.os.VibrationEffect getVibration(int i, int i2, boolean z) {
        if (effectHasCustomization(i)) {
            return this.mHapticCustomizations.get(i);
        }
        return android.os.VibrationEffect.get(i2, z);
    }

    private android.os.VibrationEffect getVibration(int i, int i2, float f, int i3) {
        if (effectHasCustomization(i)) {
            return this.mHapticCustomizations.get(i);
        }
        if (this.mVibratorInfo.isPrimitiveSupported(i2)) {
            return android.os.VibrationEffect.startComposition().addPrimitive(i2, f).compose();
        }
        return android.os.VibrationEffect.get(i3);
    }

    private android.os.VibrationEffect getAssistantButtonVibration() {
        if (effectHasCustomization(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER)) {
            return this.mHapticCustomizations.get(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER);
        }
        if (this.mVibratorInfo.isPrimitiveSupported(4) && this.mVibratorInfo.isPrimitiveSupported(7)) {
            return android.os.VibrationEffect.startComposition().addPrimitive(4, 0.25f).addPrimitive(7, 1.0f, 50).compose();
        }
        return android.os.VibrationEffect.get(5);
    }

    private boolean effectHasCustomization(int i) {
        return this.mHapticCustomizations != null && this.mHapticCustomizations.contains(i);
    }

    private android.os.VibrationEffect getKeyboardVibration(int i) {
        boolean z;
        int i2;
        if (effectHasCustomization(i)) {
            return this.mHapticCustomizations.get(i);
        }
        int i3 = 0;
        switch (i) {
            case 7:
                z = false;
                i3 = 2;
                i2 = 7;
                break;
            default:
                z = true;
                i2 = 1;
                break;
        }
        if (android.os.vibrator.Flags.keyboardCategoryEnabled() && this.mKeyboardVibrationFixedAmplitude > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && this.mVibratorInfo.isPrimitiveSupported(i2)) {
            return android.os.VibrationEffect.startComposition().addPrimitive(i2, this.mKeyboardVibrationFixedAmplitude).compose();
        }
        return getVibration(i, i3, z);
    }

    private boolean shouldBypassIntensityScale(int i, boolean z) {
        if (!android.os.vibrator.Flags.keyboardCategoryEnabled() || this.mKeyboardVibrationFixedAmplitude < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || !z) {
            return false;
        }
        switch (i) {
            case 3:
                return this.mVibratorInfo.isPrimitiveSupported(1);
            case 7:
                return this.mVibratorInfo.isPrimitiveSupported(7);
            default:
                return false;
        }
    }

    private android.os.VibrationAttributes createKeyboardVibrationAttributes(boolean z) {
        if (!android.os.vibrator.Flags.keyboardCategoryEnabled() || !z) {
            return TOUCH_VIBRATION_ATTRIBUTES;
        }
        return new android.os.VibrationAttributes.Builder(TOUCH_VIBRATION_ATTRIBUTES).setCategory(1).build();
    }

    @android.annotation.Nullable
    private static android.util.SparseArray<android.os.VibrationEffect> loadHapticCustomizations(android.content.res.Resources resources, android.os.VibratorInfo vibratorInfo) {
        try {
            return com.android.server.vibrator.HapticFeedbackCustomization.loadVibrations(resources, vibratorInfo);
        } catch (com.android.server.vibrator.HapticFeedbackCustomization.CustomizationParserException | java.io.IOException e) {
            android.util.Slog.e(TAG, "Unable to load haptic customizations.", e);
            return null;
        }
    }

    private static boolean shouldBypassInterruptionPolicy(int i) {
        switch (i) {
            case 18:
            case 19:
            case 20:
                return android.view.flags.Flags.scrollFeedbackApi();
            default:
                return false;
        }
    }
}
