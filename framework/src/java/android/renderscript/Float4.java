package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Float4 {
    public float w;
    public float x;
    public float y;
    public float z;

    public Float4() {
    }

    public Float4(android.renderscript.Float4 float4) {
        this.x = float4.x;
        this.y = float4.y;
        this.z = float4.z;
        this.w = float4.w;
    }

    public Float4(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public static android.renderscript.Float4 add(android.renderscript.Float4 float4, android.renderscript.Float4 float42) {
        android.renderscript.Float4 float43 = new android.renderscript.Float4();
        float43.x = float4.x + float42.x;
        float43.y = float4.y + float42.y;
        float43.z = float4.z + float42.z;
        float43.w = float4.w + float42.w;
        return float43;
    }

    public void add(android.renderscript.Float4 float4) {
        this.x += float4.x;
        this.y += float4.y;
        this.z += float4.z;
        this.w += float4.w;
    }

    public void add(float f) {
        this.x += f;
        this.y += f;
        this.z += f;
        this.w += f;
    }

    public static android.renderscript.Float4 add(android.renderscript.Float4 float4, float f) {
        android.renderscript.Float4 float42 = new android.renderscript.Float4();
        float42.x = float4.x + f;
        float42.y = float4.y + f;
        float42.z = float4.z + f;
        float42.w = float4.w + f;
        return float42;
    }

    public void sub(android.renderscript.Float4 float4) {
        this.x -= float4.x;
        this.y -= float4.y;
        this.z -= float4.z;
        this.w -= float4.w;
    }

    public void sub(float f) {
        this.x -= f;
        this.y -= f;
        this.z -= f;
        this.w -= f;
    }

    public static android.renderscript.Float4 sub(android.renderscript.Float4 float4, float f) {
        android.renderscript.Float4 float42 = new android.renderscript.Float4();
        float42.x = float4.x - f;
        float42.y = float4.y - f;
        float42.z = float4.z - f;
        float42.w = float4.w - f;
        return float42;
    }

    public static android.renderscript.Float4 sub(android.renderscript.Float4 float4, android.renderscript.Float4 float42) {
        android.renderscript.Float4 float43 = new android.renderscript.Float4();
        float43.x = float4.x - float42.x;
        float43.y = float4.y - float42.y;
        float43.z = float4.z - float42.z;
        float43.w = float4.w - float42.w;
        return float43;
    }

    public void mul(android.renderscript.Float4 float4) {
        this.x *= float4.x;
        this.y *= float4.y;
        this.z *= float4.z;
        this.w *= float4.w;
    }

    public void mul(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
    }

    public static android.renderscript.Float4 mul(android.renderscript.Float4 float4, android.renderscript.Float4 float42) {
        android.renderscript.Float4 float43 = new android.renderscript.Float4();
        float43.x = float4.x * float42.x;
        float43.y = float4.y * float42.y;
        float43.z = float4.z * float42.z;
        float43.w = float4.w * float42.w;
        return float43;
    }

    public static android.renderscript.Float4 mul(android.renderscript.Float4 float4, float f) {
        android.renderscript.Float4 float42 = new android.renderscript.Float4();
        float42.x = float4.x * f;
        float42.y = float4.y * f;
        float42.z = float4.z * f;
        float42.w = float4.w * f;
        return float42;
    }

    public void div(android.renderscript.Float4 float4) {
        this.x /= float4.x;
        this.y /= float4.y;
        this.z /= float4.z;
        this.w /= float4.w;
    }

    public void div(float f) {
        this.x /= f;
        this.y /= f;
        this.z /= f;
        this.w /= f;
    }

    public static android.renderscript.Float4 div(android.renderscript.Float4 float4, float f) {
        android.renderscript.Float4 float42 = new android.renderscript.Float4();
        float42.x = float4.x / f;
        float42.y = float4.y / f;
        float42.z = float4.z / f;
        float42.w = float4.w / f;
        return float42;
    }

    public static android.renderscript.Float4 div(android.renderscript.Float4 float4, android.renderscript.Float4 float42) {
        android.renderscript.Float4 float43 = new android.renderscript.Float4();
        float43.x = float4.x / float42.x;
        float43.y = float4.y / float42.y;
        float43.z = float4.z / float42.z;
        float43.w = float4.w / float42.w;
        return float43;
    }

    public float dotProduct(android.renderscript.Float4 float4) {
        return (this.x * float4.x) + (this.y * float4.y) + (this.z * float4.z) + (this.w * float4.w);
    }

    public static float dotProduct(android.renderscript.Float4 float4, android.renderscript.Float4 float42) {
        return (float42.x * float4.x) + (float42.y * float4.y) + (float42.z * float4.z) + (float42.w * float4.w);
    }

    public void addMultiple(android.renderscript.Float4 float4, float f) {
        this.x += float4.x * f;
        this.y += float4.y * f;
        this.z += float4.z * f;
        this.w += float4.w * f;
    }

    public void set(android.renderscript.Float4 float4) {
        this.x = float4.x;
        this.y = float4.y;
        this.z = float4.z;
        this.w = float4.w;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public int length() {
        return 4;
    }

    public float elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.z;
            case 3:
                return this.w;
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
            case 3:
                this.w = f;
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
            case 3:
                this.w += f;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public void copyTo(float[] fArr, int i) {
        fArr[i] = this.x;
        fArr[i + 1] = this.y;
        fArr[i + 2] = this.z;
        fArr[i + 3] = this.w;
    }
}
