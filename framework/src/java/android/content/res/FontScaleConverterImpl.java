package android.content.res;

/* loaded from: classes.dex */
public class FontScaleConverterImpl implements android.content.res.FontScaleConverter {
    public final float[] mFromSpValues;
    public final float[] mToDpValues;

    public FontScaleConverterImpl(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length || fArr.length == 0) {
            throw new java.lang.IllegalArgumentException("Array lengths must match and be nonzero");
        }
        this.mFromSpValues = fArr;
        this.mToDpValues = fArr2;
    }

    @Override // android.content.res.FontScaleConverter
    public float convertDpToSp(float f) {
        return lookupAndInterpolate(f, this.mToDpValues, this.mFromSpValues);
    }

    @Override // android.content.res.FontScaleConverter
    public float convertSpToDp(float f) {
        return lookupAndInterpolate(f, this.mFromSpValues, this.mToDpValues);
    }

    private static float lookupAndInterpolate(float f, float[] fArr, float[] fArr2) {
        float f2;
        float f3;
        float f4;
        float abs = java.lang.Math.abs(f);
        float signum = java.lang.Math.signum(f);
        int binarySearch = java.util.Arrays.binarySearch(fArr, abs);
        if (binarySearch >= 0) {
            return signum * fArr2[binarySearch];
        }
        int i = (-(binarySearch + 1)) - 1;
        float f5 = 0.0f;
        if (i >= fArr.length - 1) {
            float f6 = fArr[fArr.length - 1];
            float f7 = fArr2[fArr.length - 1];
            if (f6 == 0.0f) {
                return 0.0f;
            }
            return f * (f7 / f6);
        }
        if (i == -1) {
            f2 = fArr[0];
            f3 = fArr2[0];
            f4 = 0.0f;
        } else {
            float f8 = fArr[i];
            int i2 = i + 1;
            f2 = fArr[i2];
            float f9 = fArr2[i];
            f3 = fArr2[i2];
            f4 = f8;
            f5 = f9;
        }
        return signum * android.util.MathUtils.constrainedMap(f5, f3, f4, f2, abs);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.content.res.FontScaleConverterImpl)) {
            return false;
        }
        android.content.res.FontScaleConverterImpl fontScaleConverterImpl = (android.content.res.FontScaleConverterImpl) obj;
        if (java.util.Arrays.equals(this.mFromSpValues, fontScaleConverterImpl.mFromSpValues) && java.util.Arrays.equals(this.mToDpValues, fontScaleConverterImpl.mToDpValues)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Arrays.hashCode(this.mFromSpValues) * 31) + java.util.Arrays.hashCode(this.mToDpValues);
    }

    public java.lang.String toString() {
        return "FontScaleConverter{fromSpValues=" + java.util.Arrays.toString(this.mFromSpValues) + ", toDpValues=" + java.util.Arrays.toString(this.mToDpValues) + '}';
    }
}
