package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Matrix4f {
    final float[] mMat;

    public Matrix4f() {
        this.mMat = new float[16];
        loadIdentity();
    }

    public Matrix4f(float[] fArr) {
        this.mMat = new float[16];
        java.lang.System.arraycopy(fArr, 0, this.mMat, 0, this.mMat.length);
    }

    public float[] getArray() {
        return this.mMat;
    }

    public float get(int i, int i2) {
        return this.mMat[(i * 4) + i2];
    }

    public void set(int i, int i2, float f) {
        this.mMat[(i * 4) + i2] = f;
    }

    public void loadIdentity() {
        this.mMat[0] = 1.0f;
        this.mMat[1] = 0.0f;
        this.mMat[2] = 0.0f;
        this.mMat[3] = 0.0f;
        this.mMat[4] = 0.0f;
        this.mMat[5] = 1.0f;
        this.mMat[6] = 0.0f;
        this.mMat[7] = 0.0f;
        this.mMat[8] = 0.0f;
        this.mMat[9] = 0.0f;
        this.mMat[10] = 1.0f;
        this.mMat[11] = 0.0f;
        this.mMat[12] = 0.0f;
        this.mMat[13] = 0.0f;
        this.mMat[14] = 0.0f;
        this.mMat[15] = 1.0f;
    }

    public void load(android.renderscript.Matrix4f matrix4f) {
        java.lang.System.arraycopy(matrix4f.getArray(), 0, this.mMat, 0, this.mMat.length);
    }

    public void load(android.renderscript.Matrix3f matrix3f) {
        this.mMat[0] = matrix3f.mMat[0];
        this.mMat[1] = matrix3f.mMat[1];
        this.mMat[2] = matrix3f.mMat[2];
        this.mMat[3] = 0.0f;
        this.mMat[4] = matrix3f.mMat[3];
        this.mMat[5] = matrix3f.mMat[4];
        this.mMat[6] = matrix3f.mMat[5];
        this.mMat[7] = 0.0f;
        this.mMat[8] = matrix3f.mMat[6];
        this.mMat[9] = matrix3f.mMat[7];
        this.mMat[10] = matrix3f.mMat[8];
        this.mMat[11] = 0.0f;
        this.mMat[12] = 0.0f;
        this.mMat[13] = 0.0f;
        this.mMat[14] = 0.0f;
        this.mMat[15] = 1.0f;
    }

    public void loadRotate(float f, float f2, float f3, float f4) {
        this.mMat[3] = 0.0f;
        this.mMat[7] = 0.0f;
        this.mMat[11] = 0.0f;
        this.mMat[12] = 0.0f;
        this.mMat[13] = 0.0f;
        this.mMat[14] = 0.0f;
        this.mMat[15] = 1.0f;
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
        this.mMat[4] = f10 - f9;
        float f11 = f4 * f2 * f6;
        this.mMat[8] = f11 + f8;
        this.mMat[1] = f10 + f9;
        this.mMat[5] = (f3 * f3 * f6) + cos;
        float f12 = f3 * f4 * f6;
        this.mMat[9] = f12 - f7;
        this.mMat[2] = f11 - f8;
        this.mMat[6] = f12 + f7;
        this.mMat[10] = (f4 * f4 * f6) + cos;
    }

    public void loadScale(float f, float f2, float f3) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[5] = f2;
        this.mMat[10] = f3;
    }

    public void loadTranslate(float f, float f2, float f3) {
        loadIdentity();
        this.mMat[12] = f;
        this.mMat[13] = f2;
        this.mMat[14] = f3;
    }

    public void loadMultiply(android.renderscript.Matrix4f matrix4f, android.renderscript.Matrix4f matrix4f2) {
        for (int i = 0; i < 4; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            for (int i2 = 0; i2 < 4; i2++) {
                float f5 = matrix4f2.get(i, i2);
                f += matrix4f.get(i2, 0) * f5;
                f2 += matrix4f.get(i2, 1) * f5;
                f3 += matrix4f.get(i2, 2) * f5;
                f4 += matrix4f.get(i2, 3) * f5;
            }
            set(i, 0, f);
            set(i, 1, f2);
            set(i, 2, f3);
            set(i, 3, f4);
        }
    }

    public void loadOrtho(float f, float f2, float f3, float f4, float f5, float f6) {
        loadIdentity();
        float f7 = f2 - f;
        this.mMat[0] = 2.0f / f7;
        float f8 = f4 - f3;
        this.mMat[5] = 2.0f / f8;
        float f9 = f6 - f5;
        this.mMat[10] = (-2.0f) / f9;
        this.mMat[12] = (-(f2 + f)) / f7;
        this.mMat[13] = (-(f4 + f3)) / f8;
        this.mMat[14] = (-(f6 + f5)) / f9;
    }

    public void loadOrthoWindow(int i, int i2) {
        loadOrtho(0.0f, i, i2, 0.0f, -1.0f, 1.0f);
    }

    public void loadFrustum(float f, float f2, float f3, float f4, float f5, float f6) {
        loadIdentity();
        float f7 = 2.0f * f5;
        float f8 = f2 - f;
        this.mMat[0] = f7 / f8;
        float f9 = f4 - f3;
        this.mMat[5] = f7 / f9;
        this.mMat[8] = (f2 + f) / f8;
        this.mMat[9] = (f4 + f3) / f9;
        float f10 = f6 - f5;
        this.mMat[10] = (-(f6 + f5)) / f10;
        this.mMat[11] = -1.0f;
        this.mMat[14] = ((f6 * (-2.0f)) * f5) / f10;
        this.mMat[15] = 0.0f;
    }

    public void loadPerspective(float f, float f2, float f3, float f4) {
        float tan = f3 * ((float) java.lang.Math.tan((float) ((f * 3.141592653589793d) / 360.0d)));
        float f5 = -tan;
        loadFrustum(f5 * f2, tan * f2, f5, tan, f3, f4);
    }

    public void loadProjectionNormalized(int i, int i2) {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        android.renderscript.Matrix4f matrix4f2 = new android.renderscript.Matrix4f();
        if (i > i2) {
            float f = i / i2;
            matrix4f.loadFrustum(-f, f, -1.0f, 1.0f, 1.0f, 100.0f);
        } else {
            float f2 = i2 / i;
            matrix4f.loadFrustum(-1.0f, 1.0f, -f2, f2, 1.0f, 100.0f);
        }
        matrix4f2.loadRotate(180.0f, 0.0f, 1.0f, 0.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        matrix4f2.loadScale(-2.0f, 2.0f, 1.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        matrix4f2.loadTranslate(0.0f, 0.0f, 2.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        load(matrix4f);
    }

    public void multiply(android.renderscript.Matrix4f matrix4f) {
        android.renderscript.Matrix4f matrix4f2 = new android.renderscript.Matrix4f();
        matrix4f2.loadMultiply(this, matrix4f);
        load(matrix4f2);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        matrix4f.loadRotate(f, f2, f3, f4);
        multiply(matrix4f);
    }

    public void scale(float f, float f2, float f3) {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        matrix4f.loadScale(f, f2, f3);
        multiply(matrix4f);
    }

    public void translate(float f, float f2, float f3) {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        matrix4f.loadTranslate(f, f2, f3);
        multiply(matrix4f);
    }

    private float computeCofactor(int i, int i2) {
        int i3 = (i + 1) % 4;
        int i4 = (i + 2) % 4;
        int i5 = (i + 3) % 4;
        int i6 = ((i2 + 1) % 4) * 4;
        int i7 = ((i2 + 2) % 4) * 4;
        int i8 = i4 + i7;
        int i9 = ((i2 + 3) % 4) * 4;
        int i10 = i5 + i9;
        int i11 = i4 + i9;
        int i12 = i5 + i7;
        int i13 = i4 + i6;
        int i14 = i5 + i6;
        float f = ((this.mMat[i3 + i6] * ((this.mMat[i8] * this.mMat[i10]) - (this.mMat[i11] * this.mMat[i12]))) - (this.mMat[i7 + i3] * ((this.mMat[i13] * this.mMat[i10]) - (this.mMat[i11] * this.mMat[i14])))) + (this.mMat[i3 + i9] * ((this.mMat[i13] * this.mMat[i12]) - (this.mMat[i8] * this.mMat[i14])));
        return ((i + i2) & 1) != 0 ? -f : f;
    }

    public boolean inverse() {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                matrix4f.mMat[(i * 4) + i2] = computeCofactor(i, i2);
            }
        }
        float f = (this.mMat[0] * matrix4f.mMat[0]) + (this.mMat[4] * matrix4f.mMat[1]) + (this.mMat[8] * matrix4f.mMat[2]) + (this.mMat[12] * matrix4f.mMat[3]);
        if (java.lang.Math.abs(f) < 1.0E-6d) {
            return false;
        }
        float f2 = 1.0f / f;
        for (int i3 = 0; i3 < 16; i3++) {
            this.mMat[i3] = matrix4f.mMat[i3] * f2;
        }
        return true;
    }

    public boolean inverseTranspose() {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                matrix4f.mMat[(i2 * 4) + i] = computeCofactor(i, i2);
            }
        }
        float f = (this.mMat[0] * matrix4f.mMat[0]) + (this.mMat[4] * matrix4f.mMat[4]) + (this.mMat[8] * matrix4f.mMat[8]) + (this.mMat[12] * matrix4f.mMat[12]);
        if (java.lang.Math.abs(f) < 1.0E-6d) {
            return false;
        }
        float f2 = 1.0f / f;
        for (int i3 = 0; i3 < 16; i3++) {
            this.mMat[i3] = matrix4f.mMat[i3] * f2;
        }
        return true;
    }

    public void transpose() {
        int i = 0;
        while (i < 3) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < 4; i3++) {
                int i4 = (i * 4) + i3;
                float f = this.mMat[i4];
                int i5 = (i3 * 4) + i;
                this.mMat[i4] = this.mMat[i5];
                this.mMat[i5] = f;
            }
            i = i2;
        }
    }
}
