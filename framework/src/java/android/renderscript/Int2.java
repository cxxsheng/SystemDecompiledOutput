package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Int2 {
    public int x;
    public int y;

    public Int2() {
    }

    public Int2(int i) {
        this.y = i;
        this.x = i;
    }

    public Int2(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public Int2(android.renderscript.Int2 int2) {
        this.x = int2.x;
        this.y = int2.y;
    }

    public void add(android.renderscript.Int2 int2) {
        this.x += int2.x;
        this.y += int2.y;
    }

    public static android.renderscript.Int2 add(android.renderscript.Int2 int2, android.renderscript.Int2 int22) {
        android.renderscript.Int2 int23 = new android.renderscript.Int2();
        int23.x = int2.x + int22.x;
        int23.y = int2.y + int22.y;
        return int23;
    }

    public void add(int i) {
        this.x += i;
        this.y += i;
    }

    public static android.renderscript.Int2 add(android.renderscript.Int2 int2, int i) {
        android.renderscript.Int2 int22 = new android.renderscript.Int2();
        int22.x = int2.x + i;
        int22.y = int2.y + i;
        return int22;
    }

    public void sub(android.renderscript.Int2 int2) {
        this.x -= int2.x;
        this.y -= int2.y;
    }

    public static android.renderscript.Int2 sub(android.renderscript.Int2 int2, android.renderscript.Int2 int22) {
        android.renderscript.Int2 int23 = new android.renderscript.Int2();
        int23.x = int2.x - int22.x;
        int23.y = int2.y - int22.y;
        return int23;
    }

    public void sub(int i) {
        this.x -= i;
        this.y -= i;
    }

    public static android.renderscript.Int2 sub(android.renderscript.Int2 int2, int i) {
        android.renderscript.Int2 int22 = new android.renderscript.Int2();
        int22.x = int2.x - i;
        int22.y = int2.y - i;
        return int22;
    }

    public void mul(android.renderscript.Int2 int2) {
        this.x *= int2.x;
        this.y *= int2.y;
    }

    public static android.renderscript.Int2 mul(android.renderscript.Int2 int2, android.renderscript.Int2 int22) {
        android.renderscript.Int2 int23 = new android.renderscript.Int2();
        int23.x = int2.x * int22.x;
        int23.y = int2.y * int22.y;
        return int23;
    }

    public void mul(int i) {
        this.x *= i;
        this.y *= i;
    }

    public static android.renderscript.Int2 mul(android.renderscript.Int2 int2, int i) {
        android.renderscript.Int2 int22 = new android.renderscript.Int2();
        int22.x = int2.x * i;
        int22.y = int2.y * i;
        return int22;
    }

    public void div(android.renderscript.Int2 int2) {
        this.x /= int2.x;
        this.y /= int2.y;
    }

    public static android.renderscript.Int2 div(android.renderscript.Int2 int2, android.renderscript.Int2 int22) {
        android.renderscript.Int2 int23 = new android.renderscript.Int2();
        int23.x = int2.x / int22.x;
        int23.y = int2.y / int22.y;
        return int23;
    }

    public void div(int i) {
        this.x /= i;
        this.y /= i;
    }

    public static android.renderscript.Int2 div(android.renderscript.Int2 int2, int i) {
        android.renderscript.Int2 int22 = new android.renderscript.Int2();
        int22.x = int2.x / i;
        int22.y = int2.y / i;
        return int22;
    }

    public void mod(android.renderscript.Int2 int2) {
        this.x %= int2.x;
        this.y %= int2.y;
    }

    public static android.renderscript.Int2 mod(android.renderscript.Int2 int2, android.renderscript.Int2 int22) {
        android.renderscript.Int2 int23 = new android.renderscript.Int2();
        int23.x = int2.x % int22.x;
        int23.y = int2.y % int22.y;
        return int23;
    }

    public void mod(int i) {
        this.x %= i;
        this.y %= i;
    }

    public static android.renderscript.Int2 mod(android.renderscript.Int2 int2, int i) {
        android.renderscript.Int2 int22 = new android.renderscript.Int2();
        int22.x = int2.x % i;
        int22.y = int2.y % i;
        return int22;
    }

    public int length() {
        return 2;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public int dotProduct(android.renderscript.Int2 int2) {
        return (this.x * int2.x) + (this.y * int2.y);
    }

    public static int dotProduct(android.renderscript.Int2 int2, android.renderscript.Int2 int22) {
        return (int22.x * int2.x) + (int22.y * int2.y);
    }

    public void addMultiple(android.renderscript.Int2 int2, int i) {
        this.x += int2.x * i;
        this.y += int2.y * i;
    }

    public void set(android.renderscript.Int2 int2) {
        this.x = int2.x;
        this.y = int2.y;
    }

    public void setValues(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public int elementSum() {
        return this.x + this.y;
    }

    public int get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] iArr, int i) {
        iArr[i] = this.x;
        iArr[i + 1] = this.y;
    }
}
