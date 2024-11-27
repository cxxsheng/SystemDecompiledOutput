package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public final class XmlConstants {
    public static final java.lang.String ATTRIBUTE_AMPLITUDE = "amplitude";
    public static final java.lang.String ATTRIBUTE_DELAY_MS = "delayMs";
    public static final java.lang.String ATTRIBUTE_DURATION_MS = "durationMs";
    public static final java.lang.String ATTRIBUTE_FALLBACK = "fallback";
    public static final java.lang.String ATTRIBUTE_NAME = "name";
    public static final java.lang.String ATTRIBUTE_SCALE = "scale";
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;
    public static final java.lang.String NAMESPACE = null;
    public static final java.lang.String TAG_PREDEFINED_EFFECT = "predefined-effect";
    public static final java.lang.String TAG_PRIMITIVE_EFFECT = "primitive-effect";
    public static final java.lang.String TAG_REPEATING = "repeating";
    public static final java.lang.String TAG_VIBRATION_EFFECT = "vibration-effect";
    public static final java.lang.String TAG_VIBRATION_SELECT = "vibration-select";
    public static final java.lang.String TAG_WAVEFORM_EFFECT = "waveform-effect";
    public static final java.lang.String TAG_WAVEFORM_ENTRY = "waveform-entry";
    public static final java.lang.String VALUE_AMPLITUDE_DEFAULT = "default";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public enum PrimitiveEffectName {
        LOW_TICK(8),
        TICK(7),
        CLICK(1),
        SLOW_RISE(5),
        QUICK_RISE(4),
        QUICK_FALL(6),
        SPIN(3),
        THUD(2);

        private final int mPrimitiveId;

        PrimitiveEffectName(int i) {
            this.mPrimitiveId = i;
        }

        public static com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName findById(int i) {
            for (com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName primitiveEffectName : values()) {
                if (primitiveEffectName.mPrimitiveId == i) {
                    return primitiveEffectName;
                }
            }
            return null;
        }

        public static com.android.internal.vibrator.persistence.XmlConstants.PrimitiveEffectName findByName(java.lang.String str) {
            try {
                return valueOf(str.toUpperCase(java.util.Locale.ROOT));
            } catch (java.lang.IllegalArgumentException e) {
                return null;
            }
        }

        public int getPrimitiveId() {
            return this.mPrimitiveId;
        }

        @Override // java.lang.Enum
        public java.lang.String toString() {
            return name().toLowerCase(java.util.Locale.ROOT);
        }
    }

    public enum PredefinedEffectName {
        TICK(2, true),
        CLICK(0, true),
        HEAVY_CLICK(5, true),
        DOUBLE_CLICK(1, true),
        TEXTURE_TICK(21, false),
        THUD(3, false),
        POP(4, false),
        RINGTONE_1(android.os.VibrationEffect.RINGTONES[0], false),
        RINGTONE_2(android.os.VibrationEffect.RINGTONES[1], false),
        RINGTONE_3(android.os.VibrationEffect.RINGTONES[2], false),
        RINGTONE_4(android.os.VibrationEffect.RINGTONES[3], false),
        RINGTONE_5(android.os.VibrationEffect.RINGTONES[4], false),
        RINGTONE_6(android.os.VibrationEffect.RINGTONES[5], false),
        RINGTONE_7(android.os.VibrationEffect.RINGTONES[6], false),
        RINGTONE_8(android.os.VibrationEffect.RINGTONES[7], false),
        RINGTONE_9(android.os.VibrationEffect.RINGTONES[8], false),
        RINGTONE_10(android.os.VibrationEffect.RINGTONES[9], false),
        RINGTONE_11(android.os.VibrationEffect.RINGTONES[10], false),
        RINGTONE_12(android.os.VibrationEffect.RINGTONES[11], false),
        RINGTONE_13(android.os.VibrationEffect.RINGTONES[12], false),
        RINGTONE_14(android.os.VibrationEffect.RINGTONES[13], false),
        RINGTONE_15(android.os.VibrationEffect.RINGTONES[14], false);

        private final int mEffectId;
        private final boolean mIsPublic;

        PredefinedEffectName(int i, boolean z) {
            this.mEffectId = i;
            this.mIsPublic = z;
        }

        public static com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName findById(int i, int i2) {
            boolean z = (i2 & 1) != 0;
            for (com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName predefinedEffectName : values()) {
                if (predefinedEffectName.mEffectId == i) {
                    if (predefinedEffectName.mIsPublic || z) {
                        return predefinedEffectName;
                    }
                    return null;
                }
            }
            return null;
        }

        public static com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName findByName(java.lang.String str, int i) {
            boolean z = (i & 1) != 0;
            try {
                com.android.internal.vibrator.persistence.XmlConstants.PredefinedEffectName valueOf = valueOf(str.toUpperCase(java.util.Locale.ROOT));
                if (valueOf.mIsPublic || z) {
                    return valueOf;
                }
                return null;
            } catch (java.lang.IllegalArgumentException e) {
                return null;
            }
        }

        public int getEffectId() {
            return this.mEffectId;
        }

        @Override // java.lang.Enum
        public java.lang.String toString() {
            return name().toLowerCase(java.util.Locale.ROOT);
        }
    }
}
