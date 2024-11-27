package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class ColorSpaceTransform {
    private static final int COLUMNS = 3;
    private static final int COUNT = 9;
    private static final int COUNT_INT = 18;
    private static final int OFFSET_DENOMINATOR = 1;
    private static final int OFFSET_NUMERATOR = 0;
    private static final int RATIONAL_SIZE = 2;
    private static final int ROWS = 3;
    private final int[] mElements;

    public ColorSpaceTransform(android.util.Rational[] rationalArr) {
        com.android.internal.util.Preconditions.checkNotNull(rationalArr, "elements must not be null");
        if (rationalArr.length != 9) {
            throw new java.lang.IllegalArgumentException("elements must be 9 length");
        }
        this.mElements = new int[18];
        for (int i = 0; i < rationalArr.length; i++) {
            com.android.internal.util.Preconditions.checkNotNull(rationalArr, "element[%d] must not be null", java.lang.Integer.valueOf(i));
            int i2 = i * 2;
            this.mElements[i2 + 0] = rationalArr[i].getNumerator();
            this.mElements[i2 + 1] = rationalArr[i].getDenominator();
        }
    }

    public ColorSpaceTransform(int[] iArr) {
        com.android.internal.util.Preconditions.checkNotNull(iArr, "elements must not be null");
        if (iArr.length != 18) {
            throw new java.lang.IllegalArgumentException("elements must be 18 length");
        }
        for (int i = 0; i < iArr.length; i++) {
            com.android.internal.util.Preconditions.checkNotNull(iArr, "element %d must not be null", java.lang.Integer.valueOf(i));
        }
        this.mElements = java.util.Arrays.copyOf(iArr, iArr.length);
    }

    public android.util.Rational getElement(int i, int i2) {
        if (i < 0 || i >= 3) {
            throw new java.lang.IllegalArgumentException("column out of range");
        }
        if (i2 < 0 || i2 >= 3) {
            throw new java.lang.IllegalArgumentException("row out of range");
        }
        int i3 = ((i2 * 3) + i) * 2;
        return new android.util.Rational(this.mElements[i3 + 0], this.mElements[i3 + 1]);
    }

    public void copyElements(android.util.Rational[] rationalArr, int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "offset must not be negative");
        com.android.internal.util.Preconditions.checkNotNull(rationalArr, "destination must not be null");
        if (rationalArr.length - i < 9) {
            throw new java.lang.ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < 9) {
            rationalArr[i2 + i] = new android.util.Rational(this.mElements[i3 + 0], this.mElements[i3 + 1]);
            i2++;
            i3 += 2;
        }
    }

    public void copyElements(int[] iArr, int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "offset must not be negative");
        com.android.internal.util.Preconditions.checkNotNull(iArr, "destination must not be null");
        if (iArr.length - i < 18) {
            throw new java.lang.ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        for (int i2 = 0; i2 < 18; i2++) {
            iArr[i2 + i] = this.mElements[i2];
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.ColorSpaceTransform)) {
            return false;
        }
        android.hardware.camera2.params.ColorSpaceTransform colorSpaceTransform = (android.hardware.camera2.params.ColorSpaceTransform) obj;
        int i = 0;
        int i2 = 0;
        while (i < 9) {
            int i3 = i2 + 0;
            int i4 = i2 + 1;
            if (!new android.util.Rational(this.mElements[i3], this.mElements[i4]).equals((java.lang.Object) new android.util.Rational(colorSpaceTransform.mElements[i3], colorSpaceTransform.mElements[i4]))) {
                return false;
            }
            i++;
            i2 += 2;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mElements);
    }

    public java.lang.String toString() {
        return java.lang.String.format("ColorSpaceTransform%s", toShortString());
    }

    private java.lang.String toShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            int i3 = 0;
            while (i3 < 3) {
                int i4 = this.mElements[i + 0];
                int i5 = this.mElements[i + 1];
                sb.append(i4);
                sb.append("/");
                sb.append(i5);
                if (i3 < 2) {
                    sb.append(", ");
                }
                i3++;
                i += 2;
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            if (i2 < 2) {
                sb.append(", ");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
