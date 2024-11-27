package android.hardware.input;

/* loaded from: classes2.dex */
public class TouchCalibration implements android.os.Parcelable {
    private final float mXOffset;
    private final float mXScale;
    private final float mXYMix;
    private final float mYOffset;
    private final float mYScale;
    private final float mYXMix;
    public static final android.hardware.input.TouchCalibration IDENTITY = new android.hardware.input.TouchCalibration();
    public static final android.os.Parcelable.Creator<android.hardware.input.TouchCalibration> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.TouchCalibration>() { // from class: android.hardware.input.TouchCalibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.TouchCalibration createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.TouchCalibration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.TouchCalibration[] newArray(int i) {
            return new android.hardware.input.TouchCalibration[i];
        }
    };

    public TouchCalibration() {
        this(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public TouchCalibration(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mXScale = f;
        this.mXYMix = f2;
        this.mXOffset = f3;
        this.mYXMix = f4;
        this.mYScale = f5;
        this.mYOffset = f6;
    }

    public TouchCalibration(android.os.Parcel parcel) {
        this.mXScale = parcel.readFloat();
        this.mXYMix = parcel.readFloat();
        this.mXOffset = parcel.readFloat();
        this.mYXMix = parcel.readFloat();
        this.mYScale = parcel.readFloat();
        this.mYOffset = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mXScale);
        parcel.writeFloat(this.mXYMix);
        parcel.writeFloat(this.mXOffset);
        parcel.writeFloat(this.mYXMix);
        parcel.writeFloat(this.mYScale);
        parcel.writeFloat(this.mYOffset);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float[] getAffineTransform() {
        return new float[]{this.mXScale, this.mXYMix, this.mXOffset, this.mYXMix, this.mYScale, this.mYOffset};
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.input.TouchCalibration)) {
            return false;
        }
        android.hardware.input.TouchCalibration touchCalibration = (android.hardware.input.TouchCalibration) obj;
        return touchCalibration.mXScale == this.mXScale && touchCalibration.mXYMix == this.mXYMix && touchCalibration.mXOffset == this.mXOffset && touchCalibration.mYXMix == this.mYXMix && touchCalibration.mYScale == this.mYScale && touchCalibration.mYOffset == this.mYOffset;
    }

    public int hashCode() {
        return ((((java.lang.Float.floatToIntBits(this.mXScale) ^ java.lang.Float.floatToIntBits(this.mXYMix)) ^ java.lang.Float.floatToIntBits(this.mXOffset)) ^ java.lang.Float.floatToIntBits(this.mYXMix)) ^ java.lang.Float.floatToIntBits(this.mYScale)) ^ java.lang.Float.floatToIntBits(this.mYOffset);
    }
}
