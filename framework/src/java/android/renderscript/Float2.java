package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Float2 {
    public float x;
    public float y;

    public Float2() {
    }

    public Float2(android.renderscript.Float2 float2) {
        this.x = float2.x;
        this.y = float2.y;
    }

    public Float2(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public static android.renderscript.Float2 add(android.renderscript.Float2 float2, android.renderscript.Float2 float22) {
        android.renderscript.Float2 float23 = new android.renderscript.Float2();
        float23.x = float2.x + float22.x;
        float23.y = float2.y + float22.y;
        return float23;
    }

    public void add(android.renderscript.Float2 float2) {
        this.x += float2.x;
        this.y += float2.y;
    }

    public void add(float f) {
        this.x += f;
        this.y += f;
    }

    public static android.renderscript.Float2 add(android.renderscript.Float2 float2, float f) {
        android.renderscript.Float2 float22 = new android.renderscript.Float2();
        float22.x = float2.x + f;
        float22.y = float2.y + f;
        return float22;
    }

    public void sub(android.renderscript.Float2 float2) {
        this.x -= float2.x;
        this.y -= float2.y;
    }

    public static android.renderscript.Float2 sub(android.renderscript.Float2 float2, android.renderscript.Float2 float22) {
        android.renderscript.Float2 float23 = new android.renderscript.Float2();
        float23.x = float2.x - float22.x;
        float23.y = float2.y - float22.y;
        return float23;
    }

    public void sub(float f) {
        this.x -= f;
        this.y -= f;
    }

    public static android.renderscript.Float2 sub(android.renderscript.Float2 float2, float f) {
        android.renderscript.Float2 float22 = new android.renderscript.Float2();
        float22.x = float2.x - f;
        float22.y = float2.y - f;
        return float22;
    }

    public void mul(android.renderscript.Float2 float2) {
        this.x *= float2.x;
        this.y *= float2.y;
    }

    public static android.renderscript.Float2 mul(android.renderscript.Float2 float2, android.renderscript.Float2 float22) {
        android.renderscript.Float2 float23 = new android.renderscript.Float2();
        float23.x = float2.x * float22.x;
        float23.y = float2.y * float22.y;
        return float23;
    }

    public void mul(float f) {
        this.x *= f;
        this.y *= f;
    }

    public static android.renderscript.Float2 mul(android.renderscript.Float2 float2, float f) {
        android.renderscript.Float2 float22 = new android.renderscript.Float2();
        float22.x = float2.x * f;
        float22.y = float2.y * f;
        return float22;
    }

    public void div(android.renderscript.Float2 float2) {
        this.x /= float2.x;
        this.y /= float2.y;
    }

    public static android.renderscript.Float2 div(android.renderscript.Float2 float2, android.renderscript.Float2 float22) {
        android.renderscript.Float2 float23 = new android.renderscript.Float2();
        float23.x = float2.x / float22.x;
        float23.y = float2.y / float22.y;
        return float23;
    }

    public void div(float f) {
        this.x /= f;
        this.y /= f;
    }

    public static android.renderscript.Float2 div(android.renderscript.Float2 float2, float f) {
        android.renderscript.Float2 float22 = new android.renderscript.Float2();
        float22.x = float2.x / f;
        float22.y = float2.y / f;
        return float22;
    }

    public float dotProduct(android.renderscript.Float2 float2) {
        return (this.x * float2.x) + (this.y * float2.y);
    }

    public static float dotProduct(android.renderscript.Float2 float2, android.renderscript.Float2 float22) {
        return (float22.x * float2.x) + (float22.y * float2.y);
    }

    public void addMultiple(android.renderscript.Float2 float2, float f) {
        this.x += float2.x * f;
        this.y += float2.y * f;
    }

    public void set(android.renderscript.Float2 float2) {
        this.x = float2.x;
        this.y = float2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public int length() {
        return 2;
    }

    public float elementSum() {
        return this.x + this.y;
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void copyTo(float[] fArr, int i) {
        fArr[i] = this.x;
        fArr[i + 1] = this.y;
    }
}
