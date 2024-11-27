package android.graphics;

/* loaded from: classes.dex */
public class ColorMatrixColorFilter extends android.graphics.ColorFilter {
    private final android.graphics.ColorMatrix mMatrix = new android.graphics.ColorMatrix();

    private static native long nativeColorMatrixFilter(float[] fArr);

    private static native void nativeSetColorMatrix(long j, float[] fArr);

    public ColorMatrixColorFilter(android.graphics.ColorMatrix colorMatrix) {
        this.mMatrix.set(colorMatrix);
    }

    public ColorMatrixColorFilter(float[] fArr) {
        if (fArr.length < 20) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        this.mMatrix.set(fArr);
    }

    public void getColorMatrix(android.graphics.ColorMatrix colorMatrix) {
        colorMatrix.set(this.mMatrix);
    }

    public void setColorMatrix(android.graphics.ColorMatrix colorMatrix) {
        if (colorMatrix == null) {
            this.mMatrix.reset();
        } else {
            this.mMatrix.set(colorMatrix);
        }
        nativeSetColorMatrix(getNativeInstance(), this.mMatrix.getArray());
    }

    public void setColorMatrixArray(float[] fArr) {
        if (fArr == null) {
            this.mMatrix.reset();
        } else {
            if (fArr.length < 20) {
                throw new java.lang.ArrayIndexOutOfBoundsException();
            }
            this.mMatrix.set(fArr);
        }
        nativeSetColorMatrix(getNativeInstance(), this.mMatrix.getArray());
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return nativeColorMatrixFilter(this.mMatrix.getArray());
    }
}
