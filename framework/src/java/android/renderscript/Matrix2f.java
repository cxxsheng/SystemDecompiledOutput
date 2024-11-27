package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Matrix2f {
    final float[] mMat;

    public Matrix2f() {
        this.mMat = new float[4];
        loadIdentity();
    }

    public Matrix2f(float[] fArr) {
        this.mMat = new float[4];
        java.lang.System.arraycopy(fArr, 0, this.mMat, 0, this.mMat.length);
    }

    public float[] getArray() {
        return this.mMat;
    }

    public float get(int i, int i2) {
        return this.mMat[(i * 2) + i2];
    }

    public void set(int i, int i2, float f) {
        this.mMat[(i * 2) + i2] = f;
    }

    public void loadIdentity() {
        this.mMat[0] = 1.0f;
        this.mMat[1] = 0.0f;
        this.mMat[2] = 0.0f;
        this.mMat[3] = 1.0f;
    }

    public void load(android.renderscript.Matrix2f matrix2f) {
        java.lang.System.arraycopy(matrix2f.getArray(), 0, this.mMat, 0, this.mMat.length);
    }

    public void loadRotate(float f) {
        double d = f * 0.017453292f;
        float cos = (float) java.lang.Math.cos(d);
        float sin = (float) java.lang.Math.sin(d);
        this.mMat[0] = cos;
        this.mMat[1] = -sin;
        this.mMat[2] = sin;
        this.mMat[3] = cos;
    }

    public void loadScale(float f, float f2) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[3] = f2;
    }

    public void loadMultiply(android.renderscript.Matrix2f matrix2f, android.renderscript.Matrix2f matrix2f2) {
        for (int i = 0; i < 2; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i2 = 0; i2 < 2; i2++) {
                float f3 = matrix2f2.get(i, i2);
                f += matrix2f.get(i2, 0) * f3;
                f2 += matrix2f.get(i2, 1) * f3;
            }
            set(i, 0, f);
            set(i, 1, f2);
        }
    }

    public void multiply(android.renderscript.Matrix2f matrix2f) {
        android.renderscript.Matrix2f matrix2f2 = new android.renderscript.Matrix2f();
        matrix2f2.loadMultiply(this, matrix2f);
        load(matrix2f2);
    }

    public void rotate(float f) {
        android.renderscript.Matrix2f matrix2f = new android.renderscript.Matrix2f();
        matrix2f.loadRotate(f);
        multiply(matrix2f);
    }

    public void scale(float f, float f2) {
        android.renderscript.Matrix2f matrix2f = new android.renderscript.Matrix2f();
        matrix2f.loadScale(f, f2);
        multiply(matrix2f);
    }

    public void transpose() {
        float f = this.mMat[1];
        this.mMat[1] = this.mMat[2];
        this.mMat[2] = f;
    }
}
