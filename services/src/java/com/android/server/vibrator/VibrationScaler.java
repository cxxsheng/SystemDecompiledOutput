package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibrationScaler {
    static final float ADAPTIVE_SCALE_NONE = 1.0f;
    private static final float SCALE_FACTOR_HIGH = 1.2f;
    private static final float SCALE_FACTOR_LOW = 0.8f;
    private static final float SCALE_FACTOR_NONE = 1.0f;
    private static final float SCALE_FACTOR_VERY_HIGH = 1.4f;
    private static final float SCALE_FACTOR_VERY_LOW = 0.6f;
    static final int SCALE_HIGH = 1;
    private static final com.android.server.vibrator.VibrationScaler.ScaleLevel SCALE_LEVEL_NONE = new com.android.server.vibrator.VibrationScaler.ScaleLevel(1.0f);
    static final int SCALE_LOW = -1;
    static final int SCALE_NONE = 0;
    static final int SCALE_VERY_HIGH = 2;
    static final int SCALE_VERY_LOW = -2;
    private static final java.lang.String TAG = "VibrationScaler";
    private final int mDefaultVibrationAmplitude;
    private final com.android.server.vibrator.VibrationSettings mSettingsController;
    private final android.util.SparseArray<java.lang.Float> mAdaptiveHapticsScales = new android.util.SparseArray<>();
    private final android.util.SparseArray<com.android.server.vibrator.VibrationScaler.ScaleLevel> mScaleLevels = new android.util.SparseArray<>();

    VibrationScaler(android.content.Context context, com.android.server.vibrator.VibrationSettings vibrationSettings) {
        this.mSettingsController = vibrationSettings;
        this.mDefaultVibrationAmplitude = context.getResources().getInteger(android.R.integer.config_defaultRefreshRateInHbmSunlight);
        this.mScaleLevels.put(-2, new com.android.server.vibrator.VibrationScaler.ScaleLevel(SCALE_FACTOR_VERY_LOW));
        this.mScaleLevels.put(-1, new com.android.server.vibrator.VibrationScaler.ScaleLevel(SCALE_FACTOR_LOW));
        this.mScaleLevels.put(0, SCALE_LEVEL_NONE);
        this.mScaleLevels.put(1, new com.android.server.vibrator.VibrationScaler.ScaleLevel(SCALE_FACTOR_HIGH));
        this.mScaleLevels.put(2, new com.android.server.vibrator.VibrationScaler.ScaleLevel(SCALE_FACTOR_VERY_HIGH));
    }

    public int getDefaultVibrationAmplitude() {
        return this.mDefaultVibrationAmplitude;
    }

    public int getScaleLevel(int i) {
        int defaultIntensity = this.mSettingsController.getDefaultIntensity(i);
        int currentIntensity = this.mSettingsController.getCurrentIntensity(i);
        if (currentIntensity == 0) {
            return 0;
        }
        int i2 = currentIntensity - defaultIntensity;
        if (i2 >= -2 && i2 <= 2) {
            return i2;
        }
        android.util.Slog.wtf(TAG, "Error in scaling calculations, ended up with invalid scale level " + i2 + " for vibration with usage " + i);
        return 0;
    }

    public float getAdaptiveHapticsScale(int i) {
        if (android.os.vibrator.Flags.adaptiveHapticsEnabled()) {
            return this.mAdaptiveHapticsScales.get(i, java.lang.Float.valueOf(1.0f)).floatValue();
        }
        return 1.0f;
    }

    @android.annotation.NonNull
    public android.os.VibrationEffect scale(@android.annotation.NonNull android.os.VibrationEffect vibrationEffect, int i) {
        if (!(vibrationEffect instanceof android.os.VibrationEffect.Composed)) {
            android.util.Slog.wtf(TAG, "Error scaling unsupported vibration effect: " + vibrationEffect);
            return vibrationEffect;
        }
        int effectStrength = getEffectStrength(i);
        com.android.server.vibrator.VibrationScaler.ScaleLevel scaleLevel = this.mScaleLevels.get(getScaleLevel(i));
        float adaptiveHapticsScale = getAdaptiveHapticsScale(i);
        if (scaleLevel == null) {
            android.util.Slog.e(TAG, "No configured scaling level found! (current=" + this.mSettingsController.getCurrentIntensity(i) + ", default= " + this.mSettingsController.getDefaultIntensity(i) + ")");
            scaleLevel = SCALE_LEVEL_NONE;
        }
        android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
        java.util.ArrayList arrayList = new java.util.ArrayList(composed.getSegments());
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.set(i2, ((android.os.vibrator.VibrationEffectSegment) arrayList.get(i2)).resolve(this.mDefaultVibrationAmplitude).applyEffectStrength(effectStrength).scale(scaleLevel.factor).scaleLinearly(adaptiveHapticsScale));
        }
        if (arrayList.equals(composed.getSegments())) {
            return vibrationEffect;
        }
        android.os.VibrationEffect.Composed composed2 = new android.os.VibrationEffect.Composed(arrayList, composed.getRepeatIndex());
        composed2.validate();
        return composed2;
    }

    public android.os.vibrator.PrebakedSegment scale(android.os.vibrator.PrebakedSegment prebakedSegment, int i) {
        return prebakedSegment.applyEffectStrength(getEffectStrength(i));
    }

    public void updateAdaptiveHapticsScale(int i, float f) {
        this.mAdaptiveHapticsScales.put(i, java.lang.Float.valueOf(f));
    }

    public void removeAdaptiveHapticsScale(int i) {
        this.mAdaptiveHapticsScales.remove(i);
    }

    public void clearAdaptiveHapticsScales() {
        this.mAdaptiveHapticsScales.clear();
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VibrationScaler:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("defaultVibrationAmplitude = " + this.mDefaultVibrationAmplitude);
        indentingPrintWriter.println("ScaleLevels:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mScaleLevels.size(); i++) {
            int keyAt = this.mScaleLevels.keyAt(i);
            indentingPrintWriter.println(scaleLevelToString(keyAt) + " = " + this.mScaleLevels.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("AdaptiveHapticsScales:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mAdaptiveHapticsScales.size(); i2++) {
            indentingPrintWriter.println(android.os.VibrationAttributes.usageToString(this.mAdaptiveHapticsScales.keyAt(i2)) + " = " + java.lang.String.format(java.util.Locale.ROOT, "%.2f", java.lang.Float.valueOf(this.mAdaptiveHapticsScales.valueAt(i2).floatValue())));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1120986464282L, this.mDefaultVibrationAmplitude);
    }

    public java.lang.String toString() {
        return "VibrationScaler{mScaleLevels=" + this.mScaleLevels + ", mDefaultVibrationAmplitude=" + this.mDefaultVibrationAmplitude + ", mAdaptiveHapticsScales=" + this.mAdaptiveHapticsScales + '}';
    }

    private int getEffectStrength(int i) {
        int currentIntensity = this.mSettingsController.getCurrentIntensity(i);
        if (currentIntensity == 0) {
            currentIntensity = this.mSettingsController.getDefaultIntensity(i);
        }
        return intensityToEffectStrength(currentIntensity);
    }

    private static int intensityToEffectStrength(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                android.util.Slog.w(TAG, "Got unexpected vibration intensity: " + i);
                break;
        }
        return 2;
    }

    static java.lang.String scaleLevelToString(int i) {
        switch (i) {
            case -2:
                return "VERY_LOW";
            case -1:
                return "LOW";
            case 0:
                return "NONE";
            case 1:
                return com.android.server.utils.PriorityDump.PRIORITY_ARG_HIGH;
            case 2:
                return "VERY_HIGH";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    private static final class ScaleLevel {
        public final float factor;

        ScaleLevel(float f) {
            this.factor = f;
        }

        public java.lang.String toString() {
            return "ScaleLevel{factor=" + this.factor + "}";
        }
    }
}
