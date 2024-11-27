package android.os.vibrator;

/* loaded from: classes3.dex */
public abstract class VibrationEffectSegment implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.vibrator.VibrationEffectSegment> CREATOR = new android.os.Parcelable.Creator<android.os.vibrator.VibrationEffectSegment>() { // from class: android.os.vibrator.VibrationEffectSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.VibrationEffectSegment createFromParcel(android.os.Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return new android.os.vibrator.PrebakedSegment(parcel);
                case 2:
                    return new android.os.vibrator.PrimitiveSegment(parcel);
                case 3:
                    return new android.os.vibrator.StepSegment(parcel);
                case 4:
                    return new android.os.vibrator.RampSegment(parcel);
                default:
                    throw new java.lang.IllegalStateException("Unexpected vibration event type token in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.VibrationEffectSegment[] newArray(int i) {
            return new android.os.vibrator.VibrationEffectSegment[i];
        }
    };
    static final int PARCEL_TOKEN_PREBAKED = 1;
    static final int PARCEL_TOKEN_PRIMITIVE = 2;
    static final int PARCEL_TOKEN_RAMP = 4;
    static final int PARCEL_TOKEN_STEP = 3;

    public abstract <T extends android.os.vibrator.VibrationEffectSegment> T applyEffectStrength(int i);

    public abstract boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo);

    public abstract long getDuration();

    public abstract boolean isHapticFeedbackCandidate();

    public abstract <T extends android.os.vibrator.VibrationEffectSegment> T resolve(int i);

    public abstract <T extends android.os.vibrator.VibrationEffectSegment> T scale(float f);

    public abstract <T extends android.os.vibrator.VibrationEffectSegment> T scaleLinearly(float f);

    public abstract java.lang.String toDebugString();

    public abstract void validate();

    VibrationEffectSegment() {
    }

    public static void checkFrequencyArgument(float f, java.lang.String str) {
        if (java.lang.Float.isNaN(f)) {
            throw new java.lang.IllegalArgumentException(str + " must not be NaN");
        }
        if (java.lang.Float.isInfinite(f)) {
            throw new java.lang.IllegalArgumentException(str + " must not be infinite");
        }
        if (f < 0.0f) {
            throw new java.lang.IllegalArgumentException(str + " must be >= 0, got " + f);
        }
    }

    public static void checkDurationArgument(long j, java.lang.String str) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException(str + " must be >= 0, got " + j);
        }
    }

    protected static boolean amplitudeRequiresAmplitudeControl(float f) {
        return (f == 0.0f || f == 1.0f || f == -1.0f) ? false : true;
    }

    protected static boolean frequencyRequiresFrequencyControl(float f) {
        return f != 0.0f;
    }
}
