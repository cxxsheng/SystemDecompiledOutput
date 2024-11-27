package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Int3 {
    public int x;
    public int y;
    public int z;

    public Int3() {
    }

    public Int3(int i) {
        this.z = i;
        this.y = i;
        this.x = i;
    }

    public Int3(int i, int i2, int i3) {
        this.x = i;
        this.y = i2;
        this.z = i3;
    }

    public Int3(android.renderscript.Int3 int3) {
        this.x = int3.x;
        this.y = int3.y;
        this.z = int3.z;
    }

    public void add(android.renderscript.Int3 int3) {
        this.x += int3.x;
        this.y += int3.y;
        this.z += int3.z;
    }

    public static android.renderscript.Int3 add(android.renderscript.Int3 int3, android.renderscript.Int3 int32) {
        android.renderscript.Int3 int33 = new android.renderscript.Int3();
        int33.x = int3.x + int32.x;
        int33.y = int3.y + int32.y;
        int33.z = int3.z + int32.z;
        return int33;
    }

    public void add(int i) {
        this.x += i;
        this.y += i;
        this.z += i;
    }

    public static android.renderscript.Int3 add(android.renderscript.Int3 int3, int i) {
        android.renderscript.Int3 int32 = new android.renderscript.Int3();
        int32.x = int3.x + i;
        int32.y = int3.y + i;
        int32.z = int3.z + i;
        return int32;
    }

    public void sub(android.renderscript.Int3 int3) {
        this.x -= int3.x;
        this.y -= int3.y;
        this.z -= int3.z;
    }

    public static android.renderscript.Int3 sub(android.renderscript.Int3 int3, android.renderscript.Int3 int32) {
        android.renderscript.Int3 int33 = new android.renderscript.Int3();
        int33.x = int3.x - int32.x;
        int33.y = int3.y - int32.y;
        int33.z = int3.z - int32.z;
        return int33;
    }

    public void sub(int i) {
        this.x -= i;
        this.y -= i;
        this.z -= i;
    }

    public static android.renderscript.Int3 sub(android.renderscript.Int3 int3, int i) {
        android.renderscript.Int3 int32 = new android.renderscript.Int3();
        int32.x = int3.x - i;
        int32.y = int3.y - i;
        int32.z = int3.z - i;
        return int32;
    }

    public void mul(android.renderscript.Int3 int3) {
        this.x *= int3.x;
        this.y *= int3.y;
        this.z *= int3.z;
    }

    public static android.renderscript.Int3 mul(android.renderscript.Int3 int3, android.renderscript.Int3 int32) {
        android.renderscript.Int3 int33 = new android.renderscript.Int3();
        int33.x = int3.x * int32.x;
        int33.y = int3.y * int32.y;
        int33.z = int3.z * int32.z;
        return int33;
    }

    public void mul(int i) {
        this.x *= i;
        this.y *= i;
        this.z *= i;
    }

    public static android.renderscript.Int3 mul(android.renderscript.Int3 int3, int i) {
        android.renderscript.Int3 int32 = new android.renderscript.Int3();
        int32.x = int3.x * i;
        int32.y = int3.y * i;
        int32.z = int3.z * i;
        return int32;
    }

    public void div(android.renderscript.Int3 int3) {
        this.x /= int3.x;
        this.y /= int3.y;
        this.z /= int3.z;
    }

    public static android.renderscript.Int3 div(android.renderscript.Int3 int3, android.renderscript.Int3 int32) {
        android.renderscript.Int3 int33 = new android.renderscript.Int3();
        int33.x = int3.x / int32.x;
        int33.y = int3.y / int32.y;
        int33.z = int3.z / int32.z;
        return int33;
    }

    public void div(int i) {
        this.x /= i;
        this.y /= i;
        this.z /= i;
    }

    public static android.renderscript.Int3 div(android.renderscript.Int3 int3, int i) {
        android.renderscript.Int3 int32 = new android.renderscript.Int3();
        int32.x = int3.x / i;
        int32.y = int3.y / i;
        int32.z = int3.z / i;
        return int32;
    }

    public void mod(android.renderscript.Int3 int3) {
        this.x %= int3.x;
        this.y %= int3.y;
        this.z %= int3.z;
    }

    public static android.renderscript.Int3 mod(android.renderscript.Int3 int3, android.renderscript.Int3 int32) {
        android.renderscript.Int3 int33 = new android.renderscript.Int3();
        int33.x = int3.x % int32.x;
        int33.y = int3.y % int32.y;
        int33.z = int3.z % int32.z;
        return int33;
    }

    public void mod(int i) {
        this.x %= i;
        this.y %= i;
        this.z %= i;
    }

    public static android.renderscript.Int3 mod(android.renderscript.Int3 int3, int i) {
        android.renderscript.Int3 int32 = new android.renderscript.Int3();
        int32.x = int3.x % i;
        int32.y = int3.y % i;
        int32.z = int3.z % i;
        return int32;
    }

    public int length() {
        return 3;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public int dotProduct(android.renderscript.Int3 int3) {
        return (this.x * int3.x) + (this.y * int3.y) + (this.z * int3.z);
    }

    public static int dotProduct(android.renderscript.Int3 int3, android.renderscript.Int3 int32) {
        return (int32.x * int3.x) + (int32.y * int3.y) + (int32.z * int3.z);
    }

    public void addMultiple(android.renderscript.Int3 int3, int i) {
        this.x += int3.x * i;
        this.y += int3.y * i;
        this.z += int3.z * i;
    }

    public void set(android.renderscript.Int3 int3) {
        this.x = int3.x;
        this.y = int3.y;
        this.z = int3.z;
    }

    public void setValues(int i, int i2, int i3) {
        this.x = i;
        this.y = i2;
        this.z = i3;
    }

    public int elementSum() {
        return this.x + this.y + this.z;
    }

    public int get(int i) {
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] iArr, int i) {
        iArr[i] = this.x;
        iArr[i + 1] = this.y;
        iArr[i + 2] = this.z;
    }
}
