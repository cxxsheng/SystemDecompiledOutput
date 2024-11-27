package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Matrix3f {
    final float[] mMat;

    public Matrix3f() {
        this.mMat = new float[9];
        loadIdentity();
    }

    public Matrix3f(float[] fArr) {
        this.mMat = new float[9];
        java.lang.System.arraycopy(fArr, 0, this.mMat, 0, this.mMat.length);
    }

    public float[] getArray() {
        return this.mMat;
    }

    public float get(int i, int i2) {
        return this.mMat[(i * 3) + i2];
    }

    public void set(int i, int i2, float f) {
        this.mMat[(i * 3) + i2] = f;
    }

    public void loadIdentity() {
        this.mMat[0] = 1.0f;
        this.mMat[1] = 0.0f;
        this.mMat[2] = 0.0f;
        this.mMat[3] = 0.0f;
        this.mMat[4] = 1.0f;
        this.mMat[5] = 0.0f;
        this.mMat[6] = 0.0f;
        this.mMat[7] = 0.0f;
        this.mMat[8] = 1.0f;
    }

    public void load(android.renderscript.Matrix3f matrix3f) {
        java.lang.System.arraycopy(matrix3f.getArray(), 0, this.mMat, 0, this.mMat.length);
    }

    public void loadRotate(float f, float f2, float f3, float f4) {
        double d = f * 0.017453292f;
        float cos = (float) java.lang.Math.cos(d);
        float sin = (float) java.lang.Math.sin(d);
        float sqrt = (float) java.lang.Math.sqrt((f2 * f2) + (f3 * f3) + (f4 * f4));
        if (sqrt == 1.0f) {
            float f5 = 1.0f / sqrt;
            f2 *= f5;
            f3 *= f5;
            f4 *= f5;
        }
        float f6 = 1.0f - cos;
        float f7 = f2 * sin;
        float f8 = f3 * sin;
        float f9 = sin * f4;
        this.mMat[0] = (f2 * f2 * f6) + cos;
        float f10 = f2 * f3 * f6;
        this.mMat[3] = f10 - f9;
        float f11 = f4 * f2 * f6;
        this.mMat[6] = f11 + f8;
        this.mMat[1] = f10 + f9;
        this.mMat[4] = (f3 * f3 * f6) + cos;
        float f12 = f3 * f4 * f6;
        this.mMat[7] = f12 - f7;
        this.mMat[2] = f11 - f8;
        this.mMat[5] = f12 + f7;
        this.mMat[8] = (f4 * f4 * f6) + cos;
    }

    public void loadRotate(float f) {
        loadIdentity();
        double d = f * 0.017453292f;
        float cos = (float) java.lang.Math.cos(d);
        float sin = (float) java.lang.Math.sin(d);
        this.mMat[0] = cos;
        this.mMat[1] = -sin;
        this.mMat[3] = sin;
        this.mMat[4] = cos;
    }

    public void loadScale(float f, float f2) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[4] = f2;
    }

    public void loadScale(float f, float f2, float f3) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[4] = f2;
        this.mMat[8] = f3;
    }

    public void loadTranslate(float f, float f2) {
        loadIdentity();
        this.mMat[6] = f;
        this.mMat[7] = f2;
    }

    public void loadMultiply(android.renderscript.Matrix3f matrix3f, android.renderscript.Matrix3f matrix3f2) {
        for (int i = 0; i < 3; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            for (int i2 = 0; i2 < 3; i2++) {
                float f4 = matrix3f2.get(i, i2);
                f += matrix3f.get(i2, 0) * f4;
                f2 += matrix3f.get(i2, 1) * f4;
                f3 += matrix3f.get(i2, 2) * f4;
            }
            set(i, 0, f);
            set(i, 1, f2);
            set(i, 2, f3);
        }
    }

    public void multiply(android.renderscript.Matrix3f matrix3f) {
        android.renderscript.Matrix3f matrix3f2 = new android.renderscript.Matrix3f();
        matrix3f2.loadMultiply(this, matrix3f);
        load(matrix3f2);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        android.renderscript.Matrix3f matrix3f = new android.renderscript.Matrix3f();
        matrix3f.loadRotate(f, f2, f3, f4);
        multiply(matrix3f);
    }

    public void rotate(float f) {
        android.renderscript.Matrix3f matrix3f = new android.renderscript.Matrix3f();
        matrix3f.loadRotate(f);
        multiply(matrix3f);
    }

    public void scale(float f, float f2) {
        android.renderscript.Matrix3f matrix3f = new android.renderscript.Matrix3f();
        matrix3f.loadScale(f, f2);
        multiply(matrix3f);
    }

    public void scale(float f, float f2, float f3) {
        android.renderscript.Matrix3f matrix3f = new android.renderscript.Matrix3f();
        matrix3f.loadScale(f, f2, f3);
        multiply(matrix3f);
    }

    public void translate(float f, float f2) {
        android.renderscript.Matrix3f matrix3f = new android.renderscript.Matrix3f();
        matrix3f.loadTranslate(f, f2);
        multiply(matrix3f);
    }

    public void transpose() {
        int i = 0;
        while (i < 2) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < 3; i3++) {
                int i4 = (i * 3) + i3;
                float f = this.mMat[i4];
                int i5 = (i3 * 3) + i;
                this.mMat[i4] = this.mMat[i5];
                this.mMat[i5] = f;
            }
            i = i2;
        }
    }
}
