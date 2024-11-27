package android.os.vibrator;

/* loaded from: classes3.dex */
public final class StepSegment extends android.os.vibrator.VibrationEffectSegment {
    public static final android.os.Parcelable.Creator<android.os.vibrator.StepSegment> CREATOR = new android.os.Parcelable.Creator<android.os.vibrator.StepSegment>() { // from class: android.os.vibrator.StepSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.StepSegment createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.os.vibrator.StepSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.StepSegment[] newArray(int i) {
            return new android.os.vibrator.StepSegment[i];
        }
    };
    private final float mAmplitude;
    private final int mDuration;
    private final float mFrequencyHz;

    StepSegment(android.os.Parcel parcel) {
        this(parcel.readFloat(), parcel.readFloat(), parcel.readInt());
    }

    public StepSegment(float f, float f2, int i) {
        this.mAmplitude = f;
        this.mFrequencyHz = f2;
        this.mDuration = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.vibrator.StepSegment)) {
            return false;
        }
        android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.StepSegment) obj;
        return java.lang.Float.compare(this.mAmplitude, stepSegment.mAmplitude) == 0 && java.lang.Float.compare(this.mFrequencyHz, stepSegment.mFrequencyHz) == 0 && this.mDuration == stepSegment.mDuration;
    }

    public float getAmplitude() {
        return this.mAmplitude;
    }

    public float getFrequencyHz() {
        return this.mFrequencyHz;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo) {
        boolean hasFrequencyControl = frequencyRequiresFrequencyControl(this.mFrequencyHz) ? true & vibratorInfo.hasFrequencyControl() : true;
        if (amplitudeRequiresAmplitudeControl(this.mAmplitude)) {
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
        android.os.vibrator.VibrationEffectSegment.checkFrequencyArgument(this.mFrequencyHz, "frequencyHz");
        android.os.vibrator.VibrationEffectSegment.checkDurationArgument(this.mDuration, "duration");
        if (java.lang.Float.compare(this.mAmplitude, -1.0f) != 0) {
            com.android.internal.util.Preconditions.checkArgumentInRange(this.mAmplitude, 0.0f, 1.0f, com.android.internal.vibrator.persistence.XmlConstants.ATTRIBUTE_AMPLITUDE);
            android.os.vibrator.VibrationEffectSegment.checkFrequencyArgument(this.mFrequencyHz, "frequencyHz");
        } else if (java.lang.Float.compare(this.mFrequencyHz, 0.0f) != 0) {
            throw new java.lang.IllegalArgumentException("frequency must be default when amplitude is set to default");
        }
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.StepSegment resolve(int i) {
        if (i > 255 || i <= 0) {
            throw new java.lang.IllegalArgumentException("amplitude must be between 1 and 255 inclusive (amplitude=" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (java.lang.Float.compare(this.mAmplitude, -1.0f) != 0) {
            return this;
        }
        return new android.os.vibrator.StepSegment(i / 255.0f, this.mFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.StepSegment scale(float f) {
        if (java.lang.Float.compare(this.mAmplitude, -1.0f) == 0) {
            return this;
        }
        float scale = android.os.VibrationEffect.scale(this.mAmplitude, f);
        if (java.lang.Float.compare(scale, this.mAmplitude) == 0) {
            return this;
        }
        return new android.os.vibrator.StepSegment(scale, this.mFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.StepSegment scaleLinearly(float f) {
        if (java.lang.Float.compare(this.mAmplitude, -1.0f) == 0) {
            return this;
        }
        float scaleLinearly = android.os.VibrationEffect.scaleLinearly(this.mAmplitude, f);
        if (java.lang.Float.compare(scaleLinearly, this.mAmplitude) == 0) {
            return this;
        }
        return new android.os.vibrator.StepSegment(scaleLinearly, this.mFrequencyHz, this.mDuration);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.StepSegment applyEffectStrength(int i) {
        return this;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mAmplitude), java.lang.Float.valueOf(this.mFrequencyHz), java.lang.Integer.valueOf(this.mDuration));
    }

    public java.lang.String toString() {
        return "Step{amplitude=" + this.mAmplitude + ", frequencyHz=" + this.mFrequencyHz + ", duration=" + this.mDuration + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public java.lang.String toDebugString() {
        return java.lang.String.format("Step=%dms(amplitude=%.2f%s)", java.lang.Integer.valueOf(this.mDuration), java.lang.Float.valueOf(this.mAmplitude), java.lang.Float.compare(this.mFrequencyHz, 0.0f) == 0 ? "" : " @ " + this.mFrequencyHz + "Hz");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(3);
        parcel.writeFloat(this.mAmplitude);
        parcel.writeFloat(this.mFrequencyHz);
        parcel.writeInt(this.mDuration);
    }
}
