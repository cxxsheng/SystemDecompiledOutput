package com.android.server.notification;

/* loaded from: classes2.dex */
public final class VibratorHelper {
    private static final long[] DEFAULT_VIBRATE_PATTERN = {0, 250, 250, 250};
    private static final java.lang.String TAG = "NotificationVibratorHelper";
    private static final int VIBRATE_PATTERN_MAXLEN = 17;
    private final long[] mDefaultPattern;

    @android.annotation.Nullable
    private final float[] mDefaultPwlePattern;
    private final int mDefaultVibrationAmplitude;
    private final long[] mFallbackPattern;

    @android.annotation.Nullable
    private final float[] mFallbackPwlePattern;
    private final android.os.Vibrator mVibrator;

    public VibratorHelper(android.content.Context context) {
        this.mVibrator = (android.os.Vibrator) context.getSystemService(android.os.Vibrator.class);
        this.mDefaultPattern = getLongArray(context.getResources(), android.R.array.config_defaultImperceptibleKillingExemptionPkgs, 17, DEFAULT_VIBRATE_PATTERN);
        this.mFallbackPattern = getLongArray(context.getResources(), android.R.array.config_nightDisplayColorTemperatureCoefficients, 17, DEFAULT_VIBRATE_PATTERN);
        this.mDefaultPwlePattern = getFloatArray(context.getResources(), android.R.array.config_defaultImperceptibleKillingExemptionProcStates);
        this.mFallbackPwlePattern = getFloatArray(context.getResources(), android.R.array.config_nightDisplayColorTemperatureCoefficientsNative);
        this.mDefaultVibrationAmplitude = context.getResources().getInteger(android.R.integer.config_defaultRefreshRateInHbmSunlight);
    }

    @android.annotation.Nullable
    public static android.os.VibrationEffect createWaveformVibration(@android.annotation.Nullable long[] jArr, boolean z) {
        if (jArr != null) {
            try {
                return android.os.VibrationEffect.createWaveform(jArr, z ? 0 : -1);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Error creating vibration waveform with pattern: " + java.util.Arrays.toString(jArr));
                return null;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public static android.os.VibrationEffect createPwleWaveformVibration(@android.annotation.Nullable float[] fArr, boolean z) {
        if (fArr == null) {
            return null;
        }
        try {
            int length = fArr.length;
            if (length == 0 || length % 3 != 0) {
                return null;
            }
            android.os.VibrationEffect.WaveformBuilder startWaveform = android.os.VibrationEffect.startWaveform();
            for (int i = 0; i < length; i += 3) {
                startWaveform.addTransition(java.time.Duration.ofMillis((int) fArr[i + 2]), android.os.VibrationEffect.VibrationParameter.targetAmplitude(fArr[i]), android.os.VibrationEffect.VibrationParameter.targetFrequency(fArr[i + 1]));
            }
            android.os.VibrationEffect build = startWaveform.build();
            if (z) {
                return android.os.VibrationEffect.startComposition().repeatEffectIndefinitely(build).compose();
            }
            return build;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Error creating vibration PWLE waveform with pattern: " + java.util.Arrays.toString(fArr));
            return null;
        }
    }

    public android.os.VibrationEffect scale(android.os.VibrationEffect vibrationEffect, float f) {
        return vibrationEffect.resolve(this.mDefaultVibrationAmplitude).scale(f);
    }

    public void vibrate(android.os.VibrationEffect vibrationEffect, android.media.AudioAttributes audioAttributes, java.lang.String str) {
        this.mVibrator.vibrate(1000, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, vibrationEffect, str, new android.os.VibrationAttributes.Builder(audioAttributes).build());
    }

    public void cancelVibration() {
        this.mVibrator.cancel(-15);
    }

    public android.os.VibrationEffect createFallbackVibration(boolean z) {
        android.os.VibrationEffect createPwleWaveformVibration;
        if (this.mVibrator.hasFrequencyControl() && (createPwleWaveformVibration = createPwleWaveformVibration(this.mFallbackPwlePattern, z)) != null) {
            return createPwleWaveformVibration;
        }
        return createWaveformVibration(this.mFallbackPattern, z);
    }

    public android.os.VibrationEffect createDefaultVibration(boolean z) {
        android.os.VibrationEffect createPwleWaveformVibration;
        if (this.mVibrator.hasFrequencyControl() && (createPwleWaveformVibration = createPwleWaveformVibration(this.mDefaultPwlePattern, z)) != null) {
            return createPwleWaveformVibration;
        }
        return createWaveformVibration(this.mDefaultPattern, z);
    }

    public boolean areEffectComponentsSupported(android.os.VibrationEffect vibrationEffect) {
        return this.mVibrator.areVibrationFeaturesSupported(vibrationEffect);
    }

    @android.annotation.Nullable
    private static float[] getFloatArray(android.content.res.Resources resources, int i) {
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            float[] fArr = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                fArr[i2] = obtainTypedArray.getFloat(i2, Float.NaN);
                if (java.lang.Float.isNaN(fArr[i2])) {
                    obtainTypedArray.recycle();
                    return null;
                }
            }
            return fArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    private static long[] getLongArray(android.content.res.Resources resources, int i, int i2, long[] jArr) {
        int[] intArray = resources.getIntArray(i);
        if (intArray == null) {
            return jArr;
        }
        if (intArray.length <= i2) {
            i2 = intArray.length;
        }
        long[] jArr2 = new long[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            jArr2[i3] = intArray[i3];
        }
        return jArr2;
    }
}
