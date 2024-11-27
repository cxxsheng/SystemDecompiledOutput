package android.graphics;

/* loaded from: classes.dex */
public class Matrix44 {
    final float[] mBackingArray;

    public Matrix44() {
        this.mBackingArray = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public Matrix44(android.graphics.Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        this.mBackingArray = new float[]{fArr[0], fArr[1], 0.0f, fArr[2], fArr[3], fArr[4], 0.0f, fArr[5], 0.0f, 0.0f, 1.0f, 0.0f, fArr[6], fArr[7], 0.0f, fArr[8]};
    }

    public void getValues(float[] fArr) {
        if (fArr.length == 16) {
            java.lang.System.arraycopy(this.mBackingArray, 0, fArr, 0, this.mBackingArray.length);
            return;
        }
        throw new java.lang.IllegalArgumentException("Dst array must be of length 16");
    }

    public void setValues(float[] fArr) {
        if (fArr.length == 16) {
            java.lang.System.arraycopy(fArr, 0, this.mBackingArray, 0, this.mBackingArray.length);
            return;
        }
        throw new java.lang.IllegalArgumentException("Src array must be of length 16");
    }

    public float get(int i, int i2) {
        if (i >= 0 && i < 4 && i2 >= 0 && i2 < 4) {
            return this.mBackingArray[(i * 4) + i2];
        }
        throw new java.lang.IllegalArgumentException("invalid row and column values");
    }

    public void set(int i, int i2, float f) {
        if (i >= 0 && i < 4 && i2 >= 0 && i2 < 4) {
            this.mBackingArray[(i * 4) + i2] = f;
            return;
        }
        throw new java.lang.IllegalArgumentException("invalid row and column values");
    }

    public void reset() {
        for (int i = 0; i < this.mBackingArray.length; i++) {
            this.mBackingArray[i] = i % 4 == i / 4 ? 1.0f : 0.0f;
        }
    }

    public boolean invert() {
        float f = this.mBackingArray[0];
        float f2 = this.mBackingArray[1];
        float f3 = this.mBackingArray[2];
        float f4 = this.mBackingArray[3];
        float f5 = this.mBackingArray[4];
        float f6 = this.mBackingArray[5];
        float f7 = this.mBackingArray[6];
        float f8 = this.mBackingArray[7];
        float f9 = this.mBackingArray[8];
        float f10 = this.mBackingArray[9];
        float f11 = this.mBackingArray[10];
        float f12 = this.mBackingArray[11];
        float f13 = this.mBackingArray[12];
        float f14 = this.mBackingArray[13];
        float f15 = this.mBackingArray[14];
        float f16 = this.mBackingArray[15];
        float f17 = (f * f6) - (f2 * f5);
        float f18 = (f * f7) - (f3 * f5);
        float f19 = (f * f8) - (f4 * f5);
        float f20 = (f2 * f7) - (f3 * f6);
        float f21 = (f2 * f8) - (f4 * f6);
        float f22 = (f3 * f8) - (f4 * f7);
        float f23 = (f9 * f14) - (f10 * f13);
        float f24 = (f9 * f15) - (f11 * f13);
        float f25 = (f9 * f16) - (f12 * f13);
        float f26 = (f10 * f15) - (f11 * f14);
        float f27 = (f10 * f16) - (f12 * f14);
        float f28 = (f11 * f16) - (f12 * f15);
        float f29 = (((((f17 * f28) - (f18 * f27)) + (f19 * f26)) + (f20 * f25)) - (f21 * f24)) + (f22 * f23);
        if (f29 == 0.0f) {
            return false;
        }
        float f30 = 1.0f / f29;
        this.mBackingArray[0] = (((f6 * f28) - (f7 * f27)) + (f8 * f26)) * f30;
        this.mBackingArray[1] = ((((-f2) * f28) + (f3 * f27)) - (f4 * f26)) * f30;
        this.mBackingArray[2] = (((f14 * f22) - (f15 * f21)) + (f16 * f20)) * f30;
        this.mBackingArray[3] = ((((-f10) * f22) + (f11 * f21)) - (f12 * f20)) * f30;
        float f31 = -f5;
        this.mBackingArray[4] = (((f31 * f28) + (f7 * f25)) - (f8 * f24)) * f30;
        this.mBackingArray[5] = (((f28 * f) - (f3 * f25)) + (f4 * f24)) * f30;
        float f32 = -f13;
        this.mBackingArray[6] = (((f32 * f22) + (f15 * f19)) - (f16 * f18)) * f30;
        this.mBackingArray[7] = (((f9 * f22) - (f11 * f19)) + (f12 * f18)) * f30;
        this.mBackingArray[8] = (((f5 * f27) - (f6 * f25)) + (f8 * f23)) * f30;
        this.mBackingArray[9] = ((((-f) * f27) + (f25 * f2)) - (f4 * f23)) * f30;
        this.mBackingArray[10] = (((f13 * f21) - (f14 * f19)) + (f16 * f17)) * f30;
        this.mBackingArray[11] = ((((-f9) * f21) + (f19 * f10)) - (f12 * f17)) * f30;
        this.mBackingArray[12] = (((f31 * f26) + (f6 * f24)) - (f7 * f23)) * f30;
        this.mBackingArray[13] = (((f * f26) - (f2 * f24)) + (f3 * f23)) * f30;
        this.mBackingArray[14] = (((f32 * f20) + (f14 * f18)) - (f15 * f17)) * f30;
        this.mBackingArray[15] = (((f9 * f20) - (f10 * f18)) + (f11 * f17)) * f30;
        return true;
    }

    public boolean isIdentity() {
        for (int i = 0; i < this.mBackingArray.length; i++) {
            if ((i % 4 == i / 4 ? 1.0f : 0.0f) != this.mBackingArray[i]) {
                return false;
            }
        }
        return true;
    }

    private static float dot(android.graphics.Matrix44 matrix44, android.graphics.Matrix44 matrix442, int i, int i2) {
        return (matrix44.get(i, 0) * matrix442.get(0, i2)) + (matrix44.get(i, 1) * matrix442.get(1, i2)) + (matrix44.get(i, 2) * matrix442.get(2, i2)) + (matrix44.get(i, 3) * matrix442.get(3, i2));
    }

    private static float dot(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return (f * f5) + (f2 * f6) + (f3 * f7) + (f4 * f8);
    }

    public float[] map(float f, float f2, float f3, float f4) {
        float[] fArr = new float[4];
        map(f, f2, f3, f4, fArr);
        return fArr;
    }

    public void map(float f, float f2, float f3, float f4, float[] fArr) {
        if (fArr.length != 4) {
            throw new java.lang.IllegalArgumentException("Dst array must be of length 4");
        }
        fArr[0] = (this.mBackingArray[0] * f) + (this.mBackingArray[1] * f2) + (this.mBackingArray[2] * f3) + (this.mBackingArray[3] * f4);
        fArr[1] = (this.mBackingArray[4] * f) + (this.mBackingArray[5] * f2) + (this.mBackingArray[6] * f3) + (this.mBackingArray[7] * f4);
        fArr[2] = (this.mBackingArray[8] * f) + (this.mBackingArray[9] * f2) + (this.mBackingArray[10] * f3) + (this.mBackingArray[11] * f4);
        fArr[3] = (f * this.mBackingArray[12]) + (f2 * this.mBackingArray[13]) + (f3 * this.mBackingArray[14]) + (f4 * this.mBackingArray[15]);
    }

    public android.graphics.Matrix44 concat(android.graphics.Matrix44 matrix44) {
        float dot = dot(this, matrix44, 0, 0);
        float dot2 = dot(this, matrix44, 0, 1);
        float dot3 = dot(this, matrix44, 0, 2);
        float dot4 = dot(this, matrix44, 0, 3);
        float dot5 = dot(this, matrix44, 1, 0);
        float dot6 = dot(this, matrix44, 1, 1);
        float dot7 = dot(this, matrix44, 1, 2);
        float dot8 = dot(this, matrix44, 1, 3);
        float dot9 = dot(this, matrix44, 2, 0);
        float dot10 = dot(this, matrix44, 2, 1);
        float dot11 = dot(this, matrix44, 2, 2);
        float dot12 = dot(this, matrix44, 2, 3);
        float dot13 = dot(this, matrix44, 3, 0);
        float dot14 = dot(this, matrix44, 3, 1);
        float dot15 = dot(this, matrix44, 3, 2);
        float dot16 = dot(this, matrix44, 3, 3);
        this.mBackingArray[0] = dot;
        this.mBackingArray[1] = dot2;
        this.mBackingArray[2] = dot3;
        this.mBackingArray[3] = dot4;
        this.mBackingArray[4] = dot5;
        this.mBackingArray[5] = dot6;
        this.mBackingArray[6] = dot7;
        this.mBackingArray[7] = dot8;
        this.mBackingArray[8] = dot9;
        this.mBackingArray[9] = dot10;
        this.mBackingArray[10] = dot11;
        this.mBackingArray[11] = dot12;
        this.mBackingArray[12] = dot13;
        this.mBackingArray[13] = dot14;
        this.mBackingArray[14] = dot15;
        this.mBackingArray[15] = dot16;
        return this;
    }

    public android.graphics.Matrix44 rotate(float f, float f2, float f3, float f4) {
        float f5 = f2 + f3 + f4;
        float f6 = f2 / f5;
        float f7 = f3 / f5;
        float f8 = f4 / f5;
        double d = (f * 3.141592653589793d) / 180.0d;
        float cos = (float) java.lang.Math.cos(d);
        float sin = (float) java.lang.Math.sin(d);
        float f9 = 1.0f - cos;
        float f10 = f9 * f6;
        float f11 = (f10 * f6) + cos;
        float f12 = f10 * f7;
        float f13 = sin * f8;
        float f14 = f12 - f13;
        float f15 = f10 * f8;
        float f16 = sin * f7;
        float f17 = f15 + f16;
        float f18 = f12 + f13;
        float f19 = f9 * f7;
        float f20 = (f7 * f19) + cos;
        float f21 = f19 * f8;
        float f22 = sin * f6;
        float f23 = f21 - f22;
        float f24 = f15 - f16;
        float f25 = f22 + f21;
        float f26 = (f9 * f8 * f8) + cos;
        float dot = dot(this.mBackingArray[0], this.mBackingArray[1], this.mBackingArray[2], this.mBackingArray[3], f11, f18, f24, 0.0f);
        float dot2 = dot(this.mBackingArray[0], this.mBackingArray[1], this.mBackingArray[2], this.mBackingArray[3], f14, f20, f25, 0.0f);
        float dot3 = dot(this.mBackingArray[0], this.mBackingArray[1], this.mBackingArray[2], this.mBackingArray[3], f17, f23, f26, 0.0f);
        float dot4 = dot(this.mBackingArray[0], this.mBackingArray[1], this.mBackingArray[2], this.mBackingArray[3], 0.0f, 0.0f, 0.0f, 1.0f);
        float dot5 = dot(this.mBackingArray[4], this.mBackingArray[5], this.mBackingArray[6], this.mBackingArray[7], f11, f18, f24, 0.0f);
        float dot6 = dot(this.mBackingArray[4], this.mBackingArray[5], this.mBackingArray[6], this.mBackingArray[7], f14, f20, f25, 0.0f);
        float dot7 = dot(this.mBackingArray[4], this.mBackingArray[5], this.mBackingArray[6], this.mBackingArray[7], f17, f23, f26, 0.0f);
        float dot8 = dot(this.mBackingArray[4], this.mBackingArray[5], this.mBackingArray[6], this.mBackingArray[7], 0.0f, 0.0f, 0.0f, 1.0f);
        float dot9 = dot(this.mBackingArray[8], this.mBackingArray[9], this.mBackingArray[10], this.mBackingArray[11], f11, f18, f24, 0.0f);
        float dot10 = dot(this.mBackingArray[8], this.mBackingArray[9], this.mBackingArray[10], this.mBackingArray[11], f14, f20, f25, 0.0f);
        float dot11 = dot(this.mBackingArray[8], this.mBackingArray[9], this.mBackingArray[10], this.mBackingArray[11], f17, f23, f26, 0.0f);
        float dot12 = dot(this.mBackingArray[8], this.mBackingArray[9], this.mBackingArray[10], this.mBackingArray[11], 0.0f, 0.0f, 0.0f, 1.0f);
        float dot13 = dot(this.mBackingArray[12], this.mBackingArray[13], this.mBackingArray[14], this.mBackingArray[15], f11, f18, f24, 0.0f);
        float dot14 = dot(this.mBackingArray[12], this.mBackingArray[13], this.mBackingArray[14], this.mBackingArray[15], f14, f20, f25, 0.0f);
        float dot15 = dot(this.mBackingArray[12], this.mBackingArray[13], this.mBackingArray[14], this.mBackingArray[15], f17, f23, f26, 0.0f);
        float dot16 = dot(this.mBackingArray[12], this.mBackingArray[13], this.mBackingArray[14], this.mBackingArray[15], 0.0f, 0.0f, 0.0f, 1.0f);
        this.mBackingArray[0] = dot;
        this.mBackingArray[1] = dot2;
        this.mBackingArray[2] = dot3;
        this.mBackingArray[3] = dot4;
        this.mBackingArray[4] = dot5;
        this.mBackingArray[5] = dot6;
        this.mBackingArray[6] = dot7;
        this.mBackingArray[7] = dot8;
        this.mBackingArray[8] = dot9;
        this.mBackingArray[9] = dot10;
        this.mBackingArray[10] = dot11;
        this.mBackingArray[11] = dot12;
        this.mBackingArray[12] = dot13;
        this.mBackingArray[13] = dot14;
        this.mBackingArray[14] = dot15;
        this.mBackingArray[15] = dot16;
        return this;
    }

    public android.graphics.Matrix44 scale(float f, float f2, float f3) {
        float[] fArr = this.mBackingArray;
        fArr[0] = fArr[0] * f;
        float[] fArr2 = this.mBackingArray;
        fArr2[4] = fArr2[4] * f;
        float[] fArr3 = this.mBackingArray;
        fArr3[8] = fArr3[8] * f;
        float[] fArr4 = this.mBackingArray;
        fArr4[12] = fArr4[12] * f;
        float[] fArr5 = this.mBackingArray;
        fArr5[1] = fArr5[1] * f2;
        float[] fArr6 = this.mBackingArray;
        fArr6[5] = fArr6[5] * f2;
        float[] fArr7 = this.mBackingArray;
        fArr7[9] = fArr7[9] * f2;
        float[] fArr8 = this.mBackingArray;
        fArr8[13] = fArr8[13] * f2;
        float[] fArr9 = this.mBackingArray;
        fArr9[2] = fArr9[2] * f3;
        float[] fArr10 = this.mBackingArray;
        fArr10[6] = fArr10[6] * f3;
        float[] fArr11 = this.mBackingArray;
        fArr11[10] = fArr11[10] * f3;
        float[] fArr12 = this.mBackingArray;
        fArr12[14] = fArr12[14] * f3;
        return this;
    }

    public android.graphics.Matrix44 translate(float f, float f2, float f3) {
        float f4 = (this.mBackingArray[0] * f) + (this.mBackingArray[1] * f2) + (this.mBackingArray[2] * f3) + this.mBackingArray[3];
        float f5 = (this.mBackingArray[4] * f) + (this.mBackingArray[5] * f2) + (this.mBackingArray[6] * f3) + this.mBackingArray[7];
        float f6 = (this.mBackingArray[8] * f) + (this.mBackingArray[9] * f2) + (this.mBackingArray[10] * f3) + this.mBackingArray[11];
        float f7 = (f * this.mBackingArray[12]) + (f2 * this.mBackingArray[13]) + (f3 * this.mBackingArray[14]) + this.mBackingArray[15];
        this.mBackingArray[3] = f4;
        this.mBackingArray[7] = f5;
        this.mBackingArray[11] = f6;
        this.mBackingArray[15] = f7;
        return this;
    }

    public java.lang.String toString() {
        return java.lang.String.format("| %f %f %f %f |\n| %f %f %f %f |\n| %f %f %f %f |\n| %f %f %f %f |\n", java.lang.Float.valueOf(this.mBackingArray[0]), java.lang.Float.valueOf(this.mBackingArray[1]), java.lang.Float.valueOf(this.mBackingArray[2]), java.lang.Float.valueOf(this.mBackingArray[3]), java.lang.Float.valueOf(this.mBackingArray[4]), java.lang.Float.valueOf(this.mBackingArray[5]), java.lang.Float.valueOf(this.mBackingArray[6]), java.lang.Float.valueOf(this.mBackingArray[7]), java.lang.Float.valueOf(this.mBackingArray[8]), java.lang.Float.valueOf(this.mBackingArray[9]), java.lang.Float.valueOf(this.mBackingArray[10]), java.lang.Float.valueOf(this.mBackingArray[11]), java.lang.Float.valueOf(this.mBackingArray[12]), java.lang.Float.valueOf(this.mBackingArray[13]), java.lang.Float.valueOf(this.mBackingArray[14]), java.lang.Float.valueOf(this.mBackingArray[15]));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.graphics.Matrix44) {
            return java.util.Arrays.equals(this.mBackingArray, ((android.graphics.Matrix44) obj).mBackingArray);
        }
        return false;
    }

    public int hashCode() {
        return ((int) this.mBackingArray[0]) + ((int) this.mBackingArray[1]) + ((int) this.mBackingArray[2]) + ((int) this.mBackingArray[3]) + ((int) this.mBackingArray[4]) + ((int) this.mBackingArray[5]) + ((int) this.mBackingArray[6]) + ((int) this.mBackingArray[7]) + ((int) this.mBackingArray[8]) + ((int) this.mBackingArray[9]) + ((int) this.mBackingArray[10]) + ((int) this.mBackingArray[11]) + ((int) this.mBackingArray[12]) + ((int) this.mBackingArray[13]) + ((int) this.mBackingArray[14]) + ((int) this.mBackingArray[15]);
    }
}
