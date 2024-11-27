package android.os.vibrator;

/* loaded from: classes3.dex */
public final class PrimitiveSegment extends android.os.vibrator.VibrationEffectSegment {
    public static final android.os.Parcelable.Creator<android.os.vibrator.PrimitiveSegment> CREATOR = new android.os.Parcelable.Creator<android.os.vibrator.PrimitiveSegment>() { // from class: android.os.vibrator.PrimitiveSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.PrimitiveSegment createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.os.vibrator.PrimitiveSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.PrimitiveSegment[] newArray(int i) {
            return new android.os.vibrator.PrimitiveSegment[i];
        }
    };
    public static final int DEFAULT_DELAY_MILLIS = 0;
    public static final float DEFAULT_SCALE = 1.0f;
    private final int mDelay;
    private final int mPrimitiveId;
    private final float mScale;

    PrimitiveSegment(android.os.Parcel parcel) {
        this(parcel.readInt(), parcel.readFloat(), parcel.readInt());
    }

    public PrimitiveSegment(int i, float f, int i2) {
        this.mPrimitiveId = i;
        this.mScale = f;
        this.mDelay = i2;
    }

    public int getPrimitiveId() {
        return this.mPrimitiveId;
    }

    public float getScale() {
        return this.mScale;
    }

    public int getDelay() {
        return this.mDelay;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return -1L;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo) {
        return vibratorInfo.isPrimitiveSupported(this.mPrimitiveId);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrimitiveSegment resolve(int i) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrimitiveSegment scale(float f) {
        float scale = android.os.VibrationEffect.scale(this.mScale, f);
        if (java.lang.Float.compare(this.mScale, scale) == 0) {
            return this;
        }
        return new android.os.vibrator.PrimitiveSegment(this.mPrimitiveId, scale, this.mDelay);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrimitiveSegment scaleLinearly(float f) {
        float scaleLinearly = android.os.VibrationEffect.scaleLinearly(this.mScale, f);
        if (java.lang.Float.compare(this.mScale, scaleLinearly) == 0) {
            return this;
        }
        return new android.os.vibrator.PrimitiveSegment(this.mPrimitiveId, scaleLinearly, this.mDelay);
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrimitiveSegment applyEffectStrength(int i) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mPrimitiveId, 0, 8, "primitiveId");
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mScale, 0.0f, 1.0f, "scale");
        android.os.vibrator.VibrationEffectSegment.checkDurationArgument(this.mDelay, "delay");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(2);
        parcel.writeInt(this.mPrimitiveId);
        parcel.writeFloat(this.mScale);
        parcel.writeInt(this.mDelay);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "Primitive{primitive=" + android.os.VibrationEffect.Composition.primitiveToString(this.mPrimitiveId) + ", scale=" + this.mScale + ", delay=" + this.mDelay + '}';
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public java.lang.String toDebugString() {
        return java.lang.String.format("Primitive=%s(scale=%.2f, delay=%dms)", android.os.VibrationEffect.Composition.primitiveToString(this.mPrimitiveId), java.lang.Float.valueOf(this.mScale), java.lang.Integer.valueOf(this.mDelay));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.os.vibrator.PrimitiveSegment primitiveSegment = (android.os.vibrator.PrimitiveSegment) obj;
        if (this.mPrimitiveId == primitiveSegment.mPrimitiveId && java.lang.Float.compare(primitiveSegment.mScale, this.mScale) == 0 && this.mDelay == primitiveSegment.mDelay) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPrimitiveId), java.lang.Float.valueOf(this.mScale), java.lang.Integer.valueOf(this.mDelay));
    }
}
