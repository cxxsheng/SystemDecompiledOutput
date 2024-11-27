package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class ReprocessFormatsMap {
    private final int[] mEntry;
    private final int mInputCount;

    public ReprocessFormatsMap(int[] iArr) {
        com.android.internal.util.Preconditions.checkNotNull(iArr, "entry must not be null");
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < iArr.length) {
            int checkArgumentFormatInternal = android.hardware.camera2.params.StreamConfigurationMap.checkArgumentFormatInternal(iArr[i]);
            int i3 = length - 1;
            int i4 = i + 1;
            if (i3 < 1) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("Input %x had no output format length listed", java.lang.Integer.valueOf(checkArgumentFormatInternal)));
            }
            int i5 = iArr[i4];
            length = i3 - 1;
            i = i4 + 1;
            for (int i6 = 0; i6 < i5; i6++) {
                android.hardware.camera2.params.StreamConfigurationMap.checkArgumentFormatInternal(iArr[i + i6]);
            }
            if (i5 > 0) {
                if (length < i5) {
                    throw new java.lang.IllegalArgumentException(java.lang.String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", java.lang.Integer.valueOf(checkArgumentFormatInternal), java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(i5)));
                }
                i += i5;
                length -= i5;
            }
            i2++;
        }
        this.mEntry = iArr;
        this.mInputCount = i2;
    }

    public int[] getInputs() {
        int[] iArr = new int[this.mInputCount];
        int length = this.mEntry.length;
        int i = 0;
        int i2 = 0;
        while (i < this.mEntry.length) {
            int i3 = this.mEntry[i];
            int i4 = length - 1;
            int i5 = i + 1;
            if (i4 < 1) {
                throw new java.lang.AssertionError(java.lang.String.format("Input %x had no output format length listed", java.lang.Integer.valueOf(i3)));
            }
            int i6 = this.mEntry[i5];
            length = i4 - 1;
            i = i5 + 1;
            if (i6 > 0) {
                if (length < i6) {
                    throw new java.lang.AssertionError(java.lang.String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(i6)));
                }
                i += i6;
                length -= i6;
            }
            iArr[i2] = i3;
            i2++;
        }
        return android.hardware.camera2.params.StreamConfigurationMap.imageFormatToPublic(iArr);
    }

    public int[] getOutputs(int i) {
        int length = this.mEntry.length;
        int i2 = 0;
        while (i2 < this.mEntry.length) {
            int i3 = this.mEntry[i2];
            int i4 = length - 1;
            int i5 = i2 + 1;
            if (i4 < 1) {
                throw new java.lang.AssertionError(java.lang.String.format("Input %x had no output format length listed", java.lang.Integer.valueOf(i)));
            }
            int i6 = this.mEntry[i5];
            int i7 = i4 - 1;
            int i8 = i5 + 1;
            if (i6 > 0 && i7 < i6) {
                throw new java.lang.AssertionError(java.lang.String.format("Input %x had too few output formats listed (actual: %d, expected: %d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i6)));
            }
            if (i3 == i) {
                int[] iArr = new int[i6];
                for (int i9 = 0; i9 < i6; i9++) {
                    iArr[i9] = this.mEntry[i8 + i9];
                }
                return android.hardware.camera2.params.StreamConfigurationMap.imageFormatToPublic(iArr);
            }
            i2 = i8 + i6;
            length = i7 - i6;
        }
        throw new java.lang.IllegalArgumentException(java.lang.String.format("Input format %x was not one in #getInputs", java.lang.Integer.valueOf(i)));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.ReprocessFormatsMap)) {
            return false;
        }
        return java.util.Arrays.equals(this.mEntry, ((android.hardware.camera2.params.ReprocessFormatsMap) obj).mEntry);
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mEntry);
    }
}
