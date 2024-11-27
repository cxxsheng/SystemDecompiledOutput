package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class RggbChannelVector {
    public static final int BLUE = 3;
    public static final int COUNT = 4;
    public static final int GREEN_EVEN = 1;
    public static final int GREEN_ODD = 2;
    public static final int RED = 0;
    private final float mBlue;
    private final float mGreenEven;
    private final float mGreenOdd;
    private final float mRed;

    public RggbChannelVector(float f, float f2, float f3, float f4) {
        this.mRed = com.android.internal.util.Preconditions.checkArgumentFinite(f, "red");
        this.mGreenEven = com.android.internal.util.Preconditions.checkArgumentFinite(f2, "greenEven");
        this.mGreenOdd = com.android.internal.util.Preconditions.checkArgumentFinite(f3, "greenOdd");
        this.mBlue = com.android.internal.util.Preconditions.checkArgumentFinite(f4, "blue");
    }

    public final float getRed() {
        return this.mRed;
    }

    public float getGreenEven() {
        return this.mGreenEven;
    }

    public float getGreenOdd() {
        return this.mGreenOdd;
    }

    public float getBlue() {
        return this.mBlue;
    }

    public float getComponent(int i) {
        if (i < 0 || i >= 4) {
            throw new java.lang.IllegalArgumentException("Color channel out of range");
        }
        switch (i) {
            case 0:
                return this.mRed;
            case 1:
                return this.mGreenEven;
            case 2:
                return this.mGreenOdd;
            case 3:
                return this.mBlue;
            default:
                throw new java.lang.AssertionError("Unhandled case " + i);
        }
    }

    public void copyTo(float[] fArr, int i) {
        com.android.internal.util.Preconditions.checkNotNull(fArr, "destination must not be null");
        if (fArr.length - i < 4) {
            throw new java.lang.ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        fArr[i + 0] = this.mRed;
        fArr[i + 1] = this.mGreenEven;
        fArr[i + 2] = this.mGreenOdd;
        fArr[i + 3] = this.mBlue;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.RggbChannelVector)) {
            return false;
        }
        android.hardware.camera2.params.RggbChannelVector rggbChannelVector = (android.hardware.camera2.params.RggbChannelVector) obj;
        if (this.mRed != rggbChannelVector.mRed || this.mGreenEven != rggbChannelVector.mGreenEven || this.mGreenOdd != rggbChannelVector.mGreenOdd || this.mBlue != rggbChannelVector.mBlue) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((java.lang.Float.floatToIntBits(this.mRed) ^ java.lang.Float.floatToIntBits(this.mGreenEven)) ^ java.lang.Float.floatToIntBits(this.mGreenOdd)) ^ java.lang.Float.floatToIntBits(this.mBlue);
    }

    public java.lang.String toString() {
        return java.lang.String.format("RggbChannelVector%s", toShortString());
    }

    private java.lang.String toShortString() {
        return java.lang.String.format("{R:%f, G_even:%f, G_odd:%f, B:%f}", java.lang.Float.valueOf(this.mRed), java.lang.Float.valueOf(this.mGreenEven), java.lang.Float.valueOf(this.mGreenOdd), java.lang.Float.valueOf(this.mBlue));
    }
}
