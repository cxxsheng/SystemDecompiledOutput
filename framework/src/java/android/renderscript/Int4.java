package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Int4 {
    public int w;
    public int x;
    public int y;
    public int z;

    public Int4() {
    }

    public Int4(int i) {
        this.w = i;
        this.z = i;
        this.y = i;
        this.x = i;
    }

    public Int4(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.w = i4;
    }

    public Int4(android.renderscript.Int4 int4) {
        this.x = int4.x;
        this.y = int4.y;
        this.z = int4.z;
        this.w = int4.w;
    }

    public void add(android.renderscript.Int4 int4) {
        this.x += int4.x;
        this.y += int4.y;
        this.z += int4.z;
        this.w += int4.w;
    }

    public static android.renderscript.Int4 add(android.renderscript.Int4 int4, android.renderscript.Int4 int42) {
        android.renderscript.Int4 int43 = new android.renderscript.Int4();
        int43.x = int4.x + int42.x;
        int43.y = int4.y + int42.y;
        int43.z = int4.z + int42.z;
        int43.w = int4.w + int42.w;
        return int43;
    }

    public void add(int i) {
        this.x += i;
        this.y += i;
        this.z += i;
        this.w += i;
    }

    public static android.renderscript.Int4 add(android.renderscript.Int4 int4, int i) {
        android.renderscript.Int4 int42 = new android.renderscript.Int4();
        int42.x = int4.x + i;
        int42.y = int4.y + i;
        int42.z = int4.z + i;
        int42.w = int4.w + i;
        return int42;
    }

    public void sub(android.renderscript.Int4 int4) {
        this.x -= int4.x;
        this.y -= int4.y;
        this.z -= int4.z;
        this.w -= int4.w;
    }

    public static android.renderscript.Int4 sub(android.renderscript.Int4 int4, android.renderscript.Int4 int42) {
        android.renderscript.Int4 int43 = new android.renderscript.Int4();
        int43.x = int4.x - int42.x;
        int43.y = int4.y - int42.y;
        int43.z = int4.z - int42.z;
        int43.w = int4.w - int42.w;
        return int43;
    }

    public void sub(int i) {
        this.x -= i;
        this.y -= i;
        this.z -= i;
        this.w -= i;
    }

    public static android.renderscript.Int4 sub(android.renderscript.Int4 int4, int i) {
        android.renderscript.Int4 int42 = new android.renderscript.Int4();
        int42.x = int4.x - i;
        int42.y = int4.y - i;
        int42.z = int4.z - i;
        int42.w = int4.w - i;
        return int42;
    }

    public void mul(android.renderscript.Int4 int4) {
        this.x *= int4.x;
        this.y *= int4.y;
        this.z *= int4.z;
        this.w *= int4.w;
    }

    public static android.renderscript.Int4 mul(android.renderscript.Int4 int4, android.renderscript.Int4 int42) {
        android.renderscript.Int4 int43 = new android.renderscript.Int4();
        int43.x = int4.x * int42.x;
        int43.y = int4.y * int42.y;
        int43.z = int4.z * int42.z;
        int43.w = int4.w * int42.w;
        return int43;
    }

    public void mul(int i) {
        this.x *= i;
        this.y *= i;
        this.z *= i;
        this.w *= i;
    }

    public static android.renderscript.Int4 mul(android.renderscript.Int4 int4, int i) {
        android.renderscript.Int4 int42 = new android.renderscript.Int4();
        int42.x = int4.x * i;
        int42.y = int4.y * i;
        int42.z = int4.z * i;
        int42.w = int4.w * i;
        return int42;
    }

    public void div(android.renderscript.Int4 int4) {
        this.x /= int4.x;
        this.y /= int4.y;
        this.z /= int4.z;
        this.w /= int4.w;
    }

    public static android.renderscript.Int4 div(android.renderscript.Int4 int4, android.renderscript.Int4 int42) {
        android.renderscript.Int4 int43 = new android.renderscript.Int4();
        int43.x = int4.x / int42.x;
        int43.y = int4.y / int42.y;
        int43.z = int4.z / int42.z;
        int43.w = int4.w / int42.w;
        return int43;
    }

    public void div(int i) {
        this.x /= i;
        this.y /= i;
        this.z /= i;
        this.w /= i;
    }

    public static android.renderscript.Int4 div(android.renderscript.Int4 int4, int i) {
        android.renderscript.Int4 int42 = new android.renderscript.Int4();
        int42.x = int4.x / i;
        int42.y = int4.y / i;
        int42.z = int4.z / i;
        int42.w = int4.w / i;
        return int42;
    }

    public void mod(android.renderscript.Int4 int4) {
        this.x %= int4.x;
        this.y %= int4.y;
        this.z %= int4.z;
        this.w %= int4.w;
    }

    public static android.renderscript.Int4 mod(android.renderscript.Int4 int4, android.renderscript.Int4 int42) {
        android.renderscript.Int4 int43 = new android.renderscript.Int4();
        int43.x = int4.x % int42.x;
        int43.y = int4.y % int42.y;
        int43.z = int4.z % int42.z;
        int43.w = int4.w % int42.w;
        return int43;
    }

    public void mod(int i) {
        this.x %= i;
        this.y %= i;
        this.z %= i;
        this.w %= i;
    }

    public static android.renderscript.Int4 mod(android.renderscript.Int4 int4, int i) {
        android.renderscript.Int4 int42 = new android.renderscript.Int4();
        int42.x = int4.x % i;
        int42.y = int4.y % i;
        int42.z = int4.z % i;
        int42.w = int4.w % i;
        return int42;
    }

    public int length() {
        return 4;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public int dotProduct(android.renderscript.Int4 int4) {
        return (this.x * int4.x) + (this.y * int4.y) + (this.z * int4.z) + (this.w * int4.w);
    }

    public static int dotProduct(android.renderscript.Int4 int4, android.renderscript.Int4 int42) {
        return (int42.x * int4.x) + (int42.y * int4.y) + (int42.z * int4.z) + (int42.w * int4.w);
    }

    public void addMultiple(android.renderscript.Int4 int4, int i) {
        this.x += int4.x * i;
        this.y += int4.y * i;
        this.z += int4.z * i;
        this.w += int4.w * i;
    }

    public void set(android.renderscript.Int4 int4) {
        this.x = int4.x;
        this.y = int4.y;
        this.z = int4.z;
        this.w = int4.w;
    }

    public void setValues(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.w = i4;
    }

    public int elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public int get(int i) {
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

    public void setAt(int i, int i2) {
        switch (i) {
            case 0:
                this.x = i2;
                return;
            case 1:
                this.y = i2;
                return;
            case 2:
                this.z = i2;
                return;
            case 3:
                this.w = i2;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, int i2) {
        switch (i) {
            case 0:
                this.x += i2;
                return;
            case 1:
                this.y += i2;
                return;
            case 2:
                this.z += i2;
                return;
            case 3:
                this.w += i2;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] iArr, int i) {
        iArr[i] = this.x;
        iArr[i + 1] = this.y;
        iArr[i + 2] = this.z;
        iArr[i + 3] = this.w;
    }
}
