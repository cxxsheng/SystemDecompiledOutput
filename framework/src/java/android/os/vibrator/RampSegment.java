package android.os.vibrator;

/* loaded from: classes3.dex */
public final class RampSegment extends android.os.vibrator.VibrationEffectSegment {
    public static final android.os.Parcelable.Creator<android.os.vibrator.RampSegment> CREATOR = new android.os.Parcelable.Creator<android.os.vibrator.RampSegment>() { // from class: android.os.vibrator.RampSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.RampSegment createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.os.vibrator.RampSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.RampSegment[] newArray(int i) {
            return new android.os.vibrator.RampSegment[i];
        }
    };
    private final int mDuration;
    private final float mEndAmplitude;
    private final float mEndFrequencyHz;
    private final float mStartAmplitude;
    private final float mStartFrequencyHz;

    RampSegment(android.os.Parcel parcel) {
        this(parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.readInt());
    }

    public RampSegment(float f, float f2, float f3, float f4, int i) {
        this.mStartAmplitude = f;
        this.mEndAmplitude = f2;
        this.mStartFrequencyHz = f3;
        this.mEndFrequencyHz = f4;
        this.mDuration = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.vibrator.RampSegment)) {
            return false;
        }
        android.os.vibrator.RampSegment rampSegment = (android.os.vibrator.RampSegment) obj;
        return java.lang.Float.compare(this.mStartAmplitude, rampSegment.mStartAmplitude) == 0 && java.lang.Float.compare(this.mEndAmplitude, rampSegment.mEndAmplitude) == 0 && java.lang.Float.compare(this.mStartFrequencyHz, rampSegment.mStartFrequencyHz) == 0 && java.lang.Float.compare(this.mEndFrequencyHz, rampSegment.mEndFrequencyHz) == 0 && this.mDuration == rampSegment.mDuration;
    }

    public float getStartAmplitude() {
        return this.mStartAmplitude;
    }

    public float getEndAmplitude() {
        return this.mEndAmplitude;
    }

    public float getStartFrequencyHz() {
        return this.mStartFrequencyHz;
    }

    public float getEndFrequencyHz() {
        return this.mEndFrequencyHz;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo) {
        boolean hasFrequencyControl = (this.mStartFrequencyHz != this.mEndFrequencyHz || frequencyRequiresFrequencyControl(this.mStartFrequencyHz)) ? true & vibratorInfo.hasFrequencyControl() : true;
        if (this.mStartAmplitude != this.mEndAmplitude || amplitudeRequiresAmplitudeControl(this.mStartAmplitude)) {
            return hasFrequencyControl & vibratorInfo.hasAmplitudeControl();
        }
        return hasFrequencyControl;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        android.os.vibrator.VibrationEffectSegment.checkFrequencyArgument(this.mStartFrequencyHz, "startFrequencyHz");
        android.os.vibrator.VibrationEffectSegment.checkFrequencyArgument(this.mEndFrequencyHz, "endFrequencyHz");
        android.os.vibrator.VibrationEffectSegment.checkDurationArgument(this.mDuration, "duration");
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mStartAmplitude, 0.0f, 1.0f, "startAmplitude");
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mEndAmplitude, 0.0f, 1.0f, "endAmplitude");
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.RampSegment resolve(int i) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.RampSegment scale(float f) {
        float scale = android.os.VibrationEffect.scale(this.mStartAmplitude, f);
        float scale2 = android.os.VibrationEffect.scale(this.mEndAmplitude, f);
        if (java.lang.Float.compare(this.mStartAmplitude, scale) == 0 && java.lang.Float.compare(this.mEndAmplitude, scale2) == 0) {
            return this;
        }
        return new android.os.vibrator.RampSegment(scale, scale2, this.mStartFrequencyHz, this.mEndFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.RampSegment scaleLinearly(float f) {
        float scaleLinearly = android.os.VibrationEffect.scaleLinearly(this.mStartAmplitude, f);
        float scaleLinearly2 = android.os.VibrationEffect.scaleLinearly(this.mEndAmplitude, f);
        if (java.lang.Float.compare(this.mStartAmplitude, scaleLinearly) == 0 && java.lang.Float.compare(this.mEndAmplitude, scaleLinearly2) == 0) {
            return this;
        }
        return new android.os.vibrator.RampSegment(scaleLinearly, scaleLinearly2, this.mStartFrequencyHz, this.mEndFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.RampSegment applyEffectStrength(int i) {
        return this;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mStartAmplitude), java.lang.Float.valueOf(this.mEndAmplitude), java.lang.Float.valueOf(this.mStartFrequencyHz), java.lang.Float.valueOf(this.mEndFrequencyHz), java.lang.Integer.valueOf(this.mDuration));
    }

    public java.lang.String toString() {
        return "Ramp{startAmplitude=" + this.mStartAmplitude + ", endAmplitude=" + this.mEndAmplitude + ", startFrequencyHz=" + this.mStartFrequencyHz + ", endFrequencyHz=" + this.mEndFrequencyHz + ", duration=" + this.mDuration + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public java.lang.String toDebugString() {
        return java.lang.String.format("Ramp=%dms(amplitude=%.2f%s to %.2f%s)", java.lang.Integer.valueOf(this.mDuration), java.lang.Float.valueOf(this.mStartAmplitude), java.lang.Float.compare(this.mStartFrequencyHz, 0.0f) == 0 ? "" : " @ " + this.mStartFrequencyHz + "Hz", java.lang.Float.valueOf(this.mEndAmplitude), java.lang.Float.compare(this.mEndFrequencyHz, 0.0f) != 0 ? " @ " + this.mEndFrequencyHz + "Hz" : "");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(4);
        parcel.writeFloat(this.mStartAmplitude);
        parcel.writeFloat(this.mEndAmplitude);
        parcel.writeFloat(this.mStartFrequencyHz);
        parcel.writeFloat(this.mEndFrequencyHz);
        parcel.writeInt(this.mDuration);
    }
}
