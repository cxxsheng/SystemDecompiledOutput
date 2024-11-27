package android.os.vibrator;

/* loaded from: classes3.dex */
public class VibrationConfig {
    private final int mDefaultAlarmVibrationIntensity;
    private final int mDefaultHapticFeedbackIntensity;
    private final boolean mDefaultKeyboardVibrationEnabled;
    private final int mDefaultMediaVibrationIntensity;
    private final int mDefaultNotificationVibrationIntensity;
    private final int mDefaultRingVibrationIntensity;
    private final float mHapticChannelMaxVibrationAmplitude;
    private final boolean mHasFixedKeyboardAmplitude;
    private final boolean mIgnoreVibrationsOnWirelessCharger;
    private final int mRampDownDurationMs;
    private final int mRampStepDurationMs;
    private final int[] mRequestVibrationParamsForUsages;
    private final int mRequestVibrationParamsTimeoutMs;

    public VibrationConfig(android.content.res.Resources resources) {
        this.mHapticChannelMaxVibrationAmplitude = loadFloat(resources, com.android.internal.R.dimen.config_hapticChannelMaxVibrationAmplitude, 0.0f);
        this.mRampDownDurationMs = loadInteger(resources, com.android.internal.R.integer.config_vibrationWaveformRampDownDuration, 0);
        this.mRampStepDurationMs = loadInteger(resources, com.android.internal.R.integer.config_vibrationWaveformRampStepDuration, 0);
        this.mRequestVibrationParamsTimeoutMs = loadInteger(resources, com.android.internal.R.integer.config_requestVibrationParamsTimeout, 0);
        this.mRequestVibrationParamsForUsages = loadIntArray(resources, com.android.internal.R.array.config_requestVibrationParamsForUsages);
        this.mIgnoreVibrationsOnWirelessCharger = loadBoolean(resources, com.android.internal.R.bool.config_ignoreVibrationsOnWirelessCharger, false);
        this.mDefaultKeyboardVibrationEnabled = loadBoolean(resources, com.android.internal.R.bool.config_defaultKeyboardVibrationEnabled, true);
        this.mHasFixedKeyboardAmplitude = loadFloat(resources, com.android.internal.R.dimen.config_keyboardHapticFeedbackFixedAmplitude, -1.0f) > 0.0f;
        this.mDefaultAlarmVibrationIntensity = loadDefaultIntensity(resources, com.android.internal.R.integer.config_defaultAlarmVibrationIntensity);
        this.mDefaultHapticFeedbackIntensity = loadDefaultIntensity(resources, com.android.internal.R.integer.config_defaultHapticFeedbackIntensity);
        this.mDefaultMediaVibrationIntensity = loadDefaultIntensity(resources, com.android.internal.R.integer.config_defaultMediaVibrationIntensity);
        this.mDefaultNotificationVibrationIntensity = loadDefaultIntensity(resources, com.android.internal.R.integer.config_defaultNotificationVibrationIntensity);
        this.mDefaultRingVibrationIntensity = loadDefaultIntensity(resources, com.android.internal.R.integer.config_defaultRingVibrationIntensity);
    }

    private static int loadDefaultIntensity(android.content.res.Resources resources, int i) {
        int loadInteger = loadInteger(resources, i, 2);
        if (loadInteger < 0 || loadInteger > 3) {
            return 2;
        }
        return loadInteger;
    }

    private static float loadFloat(android.content.res.Resources resources, int i, float f) {
        return resources != null ? resources.getFloat(i) : f;
    }

    private static int loadInteger(android.content.res.Resources resources, int i, int i2) {
        return resources != null ? resources.getInteger(i) : i2;
    }

    private static boolean loadBoolean(android.content.res.Resources resources, int i, boolean z) {
        return resources != null ? resources.getBoolean(i) : z;
    }

    private static int[] loadIntArray(android.content.res.Resources resources, int i) {
        return resources != null ? resources.getIntArray(i) : new int[0];
    }

    public float getHapticChannelMaximumAmplitude() {
        if (this.mHapticChannelMaxVibrationAmplitude <= 0.0f) {
            return Float.NaN;
        }
        return this.mHapticChannelMaxVibrationAmplitude;
    }

    public int getRampDownDurationMs() {
        if (this.mRampDownDurationMs < 0) {
            return 0;
        }
        return this.mRampDownDurationMs;
    }

    public int getRequestVibrationParamsTimeoutMs() {
        return java.lang.Math.max(this.mRequestVibrationParamsTimeoutMs, 0);
    }

    public int[] getRequestVibrationParamsForUsages() {
        return this.mRequestVibrationParamsForUsages;
    }

    public int getRampStepDurationMs() {
        if (this.mRampStepDurationMs < 0) {
            return 0;
        }
        return this.mRampStepDurationMs;
    }

    public boolean ignoreVibrationsOnWirelessCharger() {
        return this.mIgnoreVibrationsOnWirelessCharger;
    }

    public boolean isDefaultKeyboardVibrationEnabled() {
        return this.mDefaultKeyboardVibrationEnabled;
    }

    public boolean hasFixedKeyboardAmplitude() {
        return this.mHasFixedKeyboardAmplitude;
    }

    public int getDefaultVibrationIntensity(int i) {
        switch (i) {
            case 17:
                return this.mDefaultAlarmVibrationIntensity;
            case 18:
            case 34:
            case 50:
            case 66:
                return this.mDefaultHapticFeedbackIntensity;
            case 33:
                return this.mDefaultRingVibrationIntensity;
            case 49:
            case 65:
                return this.mDefaultNotificationVibrationIntensity;
            default:
                return this.mDefaultMediaVibrationIntensity;
        }
    }

    public java.lang.String toString() {
        return "VibrationConfig{mIgnoreVibrationsOnWirelessCharger=" + this.mIgnoreVibrationsOnWirelessCharger + ", mHapticChannelMaxVibrationAmplitude=" + this.mHapticChannelMaxVibrationAmplitude + ", mRampStepDurationMs=" + this.mRampStepDurationMs + ", mRampDownDurationMs=" + this.mRampDownDurationMs + ", mRequestVibrationParamsForUsages=" + java.util.Arrays.toString(getRequestVibrationParamsForUsagesNames()) + ", mRequestVibrationParamsTimeoutMs=" + this.mRequestVibrationParamsTimeoutMs + ", mDefaultAlarmIntensity=" + this.mDefaultAlarmVibrationIntensity + ", mDefaultHapticFeedbackIntensity=" + this.mDefaultHapticFeedbackIntensity + ", mDefaultMediaIntensity=" + this.mDefaultMediaVibrationIntensity + ", mDefaultNotificationIntensity=" + this.mDefaultNotificationVibrationIntensity + ", mDefaultRingIntensity=" + this.mDefaultRingVibrationIntensity + ", mDefaultKeyboardVibrationEnabled=" + this.mDefaultKeyboardVibrationEnabled + "}";
    }

    public void dumpWithoutDefaultSettings(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VibrationConfig:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("ignoreVibrationsOnWirelessCharger = " + this.mIgnoreVibrationsOnWirelessCharger);
        indentingPrintWriter.println("hapticChannelMaxAmplitude = " + this.mHapticChannelMaxVibrationAmplitude);
        indentingPrintWriter.println("rampStepDurationMs = " + this.mRampStepDurationMs);
        indentingPrintWriter.println("rampDownDurationMs = " + this.mRampDownDurationMs);
        indentingPrintWriter.println("requestVibrationParamsForUsages = " + java.util.Arrays.toString(getRequestVibrationParamsForUsagesNames()));
        indentingPrintWriter.println("requestVibrationParamsTimeoutMs = " + this.mRequestVibrationParamsTimeoutMs);
        indentingPrintWriter.decreaseIndent();
    }

    private java.lang.String[] getRequestVibrationParamsForUsagesNames() {
        int length = this.mRequestVibrationParamsForUsages.length;
        java.lang.String[] strArr = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = android.os.VibrationAttributes.usageToString(this.mRequestVibrationParamsForUsages[i]);
        }
        return strArr;
    }
}
