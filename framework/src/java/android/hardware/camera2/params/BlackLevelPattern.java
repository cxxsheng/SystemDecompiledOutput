package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class BlackLevelPattern {
    public static final int COUNT = 4;
    private final int[] mCfaOffsets;

    public BlackLevelPattern(int[] iArr) {
        if (iArr == null) {
            throw new java.lang.NullPointerException("Null offsets array passed to constructor");
        }
        if (iArr.length < 4) {
            throw new java.lang.IllegalArgumentException("Invalid offsets array length");
        }
        this.mCfaOffsets = java.util.Arrays.copyOf(iArr, 4);
    }

    public int getOffsetForIndex(int i, int i2) {
        if (i2 < 0 || i < 0) {
            throw new java.lang.IllegalArgumentException("column, row arguments must be positive");
        }
        return this.mCfaOffsets[(i & 1) | ((i2 & 1) << 1)];
    }

    public void copyTo(int[] iArr, int i) {
        java.util.Objects.requireNonNull(iArr, "destination must not be null");
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Null offset passed to copyTo");
        }
        if (iArr.length - i < 4) {
            throw new java.lang.ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        for (int i2 = 0; i2 < 4; i2++) {
            iArr[i + i2] = this.mCfaOffsets[i2];
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.BlackLevelPattern)) {
            return false;
        }
        return java.util.Arrays.equals(((android.hardware.camera2.params.BlackLevelPattern) obj).mCfaOffsets, this.mCfaOffsets);
    }

    public int hashCode() {
        return java.util.Arrays.hashCode(this.mCfaOffsets);
    }

    public java.lang.String toString() {
        return java.lang.String.format("BlackLevelPattern([%d, %d], [%d, %d])", java.lang.Integer.valueOf(this.mCfaOffsets[0]), java.lang.Integer.valueOf(this.mCfaOffsets[1]), java.lang.Integer.valueOf(this.mCfaOffsets[2]), java.lang.Integer.valueOf(this.mCfaOffsets[3]));
    }
}
