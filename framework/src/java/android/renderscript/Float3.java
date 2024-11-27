package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Float3 {
    public float x;
    public float y;
    public float z;

    public Float3() {
    }

    public Float3(android.renderscript.Float3 float3) {
        this.x = float3.x;
        this.y = float3.y;
        this.z = float3.z;
    }

    public Float3(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public static android.renderscript.Float3 add(android.renderscript.Float3 float3, android.renderscript.Float3 float32) {
        android.renderscript.Float3 float33 = new android.renderscript.Float3();
        float33.x = float3.x + float32.x;
        float33.y = float3.y + float32.y;
        float33.z = float3.z + float32.z;
        return float33;
    }

    public void add(android.renderscript.Float3 float3) {
        this.x += float3.x;
        this.y += float3.y;
        this.z += float3.z;
    }

    public void add(float f) {
        this.x += f;
        this.y += f;
        this.z += f;
    }

    public static android.renderscript.Float3 add(android.renderscript.Float3 float3, float f) {
        android.renderscript.Float3 float32 = new android.renderscript.Float3();
        float32.x = float3.x + f;
        float32.y = float3.y + f;
        float32.z = float3.z + f;
        return float32;
    }

    public void sub(android.renderscript.Float3 float3) {
        this.x -= float3.x;
        this.y -= float3.y;
        this.z -= float3.z;
    }

    public static android.renderscript.Float3 sub(android.renderscript.Float3 float3, android.renderscript.Float3 float32) {
        android.renderscript.Float3 float33 = new android.renderscript.Float3();
        float33.x = float3.x - float32.x;
        float33.y = float3.y - float32.y;
        float33.z = float3.z - float32.z;
        return float33;
    }

    public void sub(float f) {
        this.x -= f;
        this.y -= f;
        this.z -= f;
    }

    public static android.renderscript.Float3 sub(android.renderscript.Float3 float3, float f) {
        android.renderscript.Float3 float32 = new android.renderscript.Float3();
        float32.x = float3.x - f;
        float32.y = float3.y - f;
        float32.z = float3.z - f;
        return float32;
    }

    public void mul(android.renderscript.Float3 float3) {
        this.x *= float3.x;
        this.y *= float3.y;
        this.z *= float3.z;
    }

    public static android.renderscript.Float3 mul(android.renderscript.Float3 float3, android.renderscript.Float3 float32) {
        android.renderscript.Float3 float33 = new android.renderscript.Float3();
        float33.x = float3.x * float32.x;
        float33.y = float3.y * float32.y;
        float33.z = float3.z * float32.z;
        return float33;
    }

    public void mul(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public static android.renderscript.Float3 mul(android.renderscript.Float3 float3, float f) {
        android.renderscript.Float3 float32 = new android.renderscript.Float3();
        float32.x = float3.x * f;
        float32.y = float3.y * f;
        float32.z = float3.z * f;
        return float32;
    }

    public void div(android.renderscript.Float3 float3) {
        this.x /= float3.x;
        this.y /= float3.y;
        this.z /= float3.z;
    }

    public static android.renderscript.Float3 div(android.renderscript.Float3 float3, android.renderscript.Float3 float32) {
        android.renderscript.Float3 float33 = new android.renderscript.Float3();
        float33.x = float3.x / float32.x;
        float33.y = float3.y / float32.y;
        float33.z = float3.z / float32.z;
        return float33;
    }

    public void div(float f) {
        this.x /= f;
        this.y /= f;
        this.z /= f;
    }

    public static android.renderscript.Float3 div(android.renderscript.Float3 float3, float f) {
        android.renderscript.Float3 float32 = new android.renderscript.Float3();
        float32.x = float3.x / f;
        float32.y = float3.y / f;
        float32.z = float3.z / f;
        return float32;
    }

    public java.lang.Float dotProduct(android.renderscript.Float3 float3) {
        return new java.lang.Float((this.x * float3.x) + (this.y * float3.y) + (this.z * float3.z));
    }

    public static java.lang.Float dotProduct(android.renderscript.Float3 float3, android.renderscript.Float3 float32) {
        return new java.lang.Float((float32.x * float3.x) + (float32.y * float3.y) + (float32.z * float3.z));
    }

    public void addMultiple(android.renderscript.Float3 float3, float f) {
        this.x += float3.x * f;
        this.y += float3.y * f;
        this.z += float3.z * f;
    }

    public void set(android.renderscript.Float3 float3) {
        this.x = float3.x;
        this.y = float3.y;
        this.z = float3.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public int length() {
        return 3;
    }

    public java.lang.Float elementSum() {
        return new java.lang.Float(this.x + this.y + this.z);
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.z;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, float f) {
        switch (i) {
            case 0:
                this.x = f;
                return;
            case 1:
                this.y = f;
                return;
            case 2:
                this.z = f;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float f) {
        switch (i) {
            case 0:
                this.x += f;
                return;
            case 1:
                this.y += f;
                return;
            case 2:
                this.z += f;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public void copyTo(float[] fArr, int i) {
        fArr[i] = this.x;
        fArr[i + 1] = this.y;
        fArr[i + 2] = this.z;
    }
}
