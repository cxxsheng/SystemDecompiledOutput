package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class LensShadingMap {
    public static final float MINIMUM_GAIN_FACTOR = 1.0f;
    private final int mColumns;
    private final float[] mElements;
    private final int mRows;

    public LensShadingMap(float[] fArr, int i, int i2) {
        this.mRows = com.android.internal.util.Preconditions.checkArgumentPositive(i, "rows must be positive");
        this.mColumns = com.android.internal.util.Preconditions.checkArgumentPositive(i2, "columns must be positive");
        this.mElements = (float[]) java.util.Objects.requireNonNull(fArr, "elements must not be null");
        if (fArr.length != getGainFactorCount()) {
            throw new java.lang.IllegalArgumentException("elements must be " + getGainFactorCount() + " length, received " + fArr.length);
        }
        com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr, 1.0f, Float.MAX_VALUE, "elements");
    }

    public int getRowCount() {
        return this.mRows;
    }

    public int getColumnCount() {
        return this.mColumns;
    }

    public int getGainFactorCount() {
        return this.mRows * this.mColumns * 4;
    }

    public float getGainFactor(int i, int i2, int i3) {
        if (i < 0 || i > 4) {
            throw new java.lang.IllegalArgumentException("colorChannel out of range");
        }
        if (i2 < 0 || i2 >= this.mColumns) {
            throw new java.lang.IllegalArgumentException("column out of range");
        }
        if (i3 < 0 || i3 >= this.mRows) {
            throw new java.lang.IllegalArgumentException("row out of range");
        }
        return this.mElements[i + (((i3 * this.mColumns) + i2) * 4)];
    }

    public android.hardware.camera2.params.RggbChannelVector getGainFactorVector(int i, int i2) {
        if (i < 0 || i >= this.mColumns) {
            throw new java.lang.IllegalArgumentException("column out of range");
        }
        if (i2 < 0 || i2 >= this.mRows) {
            throw new java.lang.IllegalArgumentException("row out of range");
        }
        int i3 = ((i2 * this.mColumns) + i) * 4;
        return new android.hardware.camera2.params.RggbChannelVector(this.mElements[i3 + 0], this.mElements[i3 + 1], this.mElements[i3 + 2], this.mElements[i3 + 3]);
    }

    public void copyGainFactors(float[] fArr, int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "offset must not be negative");
        java.util.Objects.requireNonNull(fArr, "destination must not be null");
        if (fArr.length + i < getGainFactorCount()) {
            throw new java.lang.ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        java.lang.System.arraycopy(this.mElements, 0, fArr, i, getGainFactorCount());
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.LensShadingMap)) {
            return false;
        }
        android.hardware.camera2.params.LensShadingMap lensShadingMap = (android.hardware.camera2.params.LensShadingMap) obj;
        if (this.mRows != lensShadingMap.mRows || this.mColumns != lensShadingMap.mColumns || !java.util.Arrays.equals(this.mElements, lensShadingMap.mElements)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mRows, this.mColumns, android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mElements));
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("LensShadingMap{");
        java.lang.String[] strArr = {"R:(", "G_even:(", "G_odd:(", "B:("};
        for (int i = 0; i < 4; i++) {
            sb.append(strArr[i]);
            for (int i2 = 0; i2 < this.mRows; i2++) {
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
                for (int i3 = 0; i3 < this.mColumns; i3++) {
                    sb.append(getGainFactor(i, i3, i2));
                    if (i3 < this.mColumns - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
                if (i2 < this.mRows - 1) {
                    sb.append(", ");
                }
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            if (i < 3) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
