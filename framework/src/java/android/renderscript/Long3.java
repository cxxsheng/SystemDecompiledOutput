package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Long3 {
    public long x;
    public long y;
    public long z;

    public Long3() {
    }

    public Long3(long j) {
        this.z = j;
        this.y = j;
        this.x = j;
    }

    public Long3(long j, long j2, long j3) {
        this.x = j;
        this.y = j2;
        this.z = j3;
    }

    public Long3(android.renderscript.Long3 long3) {
        this.x = long3.x;
        this.y = long3.y;
        this.z = long3.z;
    }

    public void add(android.renderscript.Long3 long3) {
        this.x += long3.x;
        this.y += long3.y;
        this.z += long3.z;
    }

    public static android.renderscript.Long3 add(android.renderscript.Long3 long3, android.renderscript.Long3 long32) {
        android.renderscript.Long3 long33 = new android.renderscript.Long3();
        long33.x = long3.x + long32.x;
        long33.y = long3.y + long32.y;
        long33.z = long3.z + long32.z;
        return long33;
    }

    public void add(long j) {
        this.x += j;
        this.y += j;
        this.z += j;
    }

    public static android.renderscript.Long3 add(android.renderscript.Long3 long3, long j) {
        android.renderscript.Long3 long32 = new android.renderscript.Long3();
        long32.x = long3.x + j;
        long32.y = long3.y + j;
        long32.z = long3.z + j;
        return long32;
    }

    public void sub(android.renderscript.Long3 long3) {
        this.x -= long3.x;
        this.y -= long3.y;
        this.z -= long3.z;
    }

    public static android.renderscript.Long3 sub(android.renderscript.Long3 long3, android.renderscript.Long3 long32) {
        android.renderscript.Long3 long33 = new android.renderscript.Long3();
        long33.x = long3.x - long32.x;
        long33.y = long3.y - long32.y;
        long33.z = long3.z - long32.z;
        return long33;
    }

    public void sub(long j) {
        this.x -= j;
        this.y -= j;
        this.z -= j;
    }

    public static android.renderscript.Long3 sub(android.renderscript.Long3 long3, long j) {
        android.renderscript.Long3 long32 = new android.renderscript.Long3();
        long32.x = long3.x - j;
        long32.y = long3.y - j;
        long32.z = long3.z - j;
        return long32;
    }

    public void mul(android.renderscript.Long3 long3) {
        this.x *= long3.x;
        this.y *= long3.y;
        this.z *= long3.z;
    }

    public static android.renderscript.Long3 mul(android.renderscript.Long3 long3, android.renderscript.Long3 long32) {
        android.renderscript.Long3 long33 = new android.renderscript.Long3();
        long33.x = long3.x * long32.x;
        long33.y = long3.y * long32.y;
        long33.z = long3.z * long32.z;
        return long33;
    }

    public void mul(long j) {
        this.x *= j;
        this.y *= j;
        this.z *= j;
    }

    public static android.renderscript.Long3 mul(android.renderscript.Long3 long3, long j) {
        android.renderscript.Long3 long32 = new android.renderscript.Long3();
        long32.x = long3.x * j;
        long32.y = long3.y * j;
        long32.z = long3.z * j;
        return long32;
    }

    public void div(android.renderscript.Long3 long3) {
        this.x /= long3.x;
        this.y /= long3.y;
        this.z /= long3.z;
    }

    public static android.renderscript.Long3 div(android.renderscript.Long3 long3, android.renderscript.Long3 long32) {
        android.renderscript.Long3 long33 = new android.renderscript.Long3();
        long33.x = long3.x / long32.x;
        long33.y = long3.y / long32.y;
        long33.z = long3.z / long32.z;
        return long33;
    }

    public void div(long j) {
        this.x /= j;
        this.y /= j;
        this.z /= j;
    }

    public static android.renderscript.Long3 div(android.renderscript.Long3 long3, long j) {
        android.renderscript.Long3 long32 = new android.renderscript.Long3();
        long32.x = long3.x / j;
        long32.y = long3.y / j;
        long32.z = long3.z / j;
        return long32;
    }

    public void mod(android.renderscript.Long3 long3) {
        this.x %= long3.x;
        this.y %= long3.y;
        this.z %= long3.z;
    }

    public static android.renderscript.Long3 mod(android.renderscript.Long3 long3, android.renderscript.Long3 long32) {
        android.renderscript.Long3 long33 = new android.renderscript.Long3();
        long33.x = long3.x % long32.x;
        long33.y = long3.y % long32.y;
        long33.z = long3.z % long32.z;
        return long33;
    }

    public void mod(long j) {
        this.x %= j;
        this.y %= j;
        this.z %= j;
    }

    public static android.renderscript.Long3 mod(android.renderscript.Long3 long3, long j) {
        android.renderscript.Long3 long32 = new android.renderscript.Long3();
        long32.x = long3.x % j;
        long32.y = long3.y % j;
        long32.z = long3.z % j;
        return long32;
    }

    public long length() {
        return 3L;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public long dotProduct(android.renderscript.Long3 long3) {
        return (this.x * long3.x) + (this.y * long3.y) + (this.z * long3.z);
    }

    public static long dotProduct(android.renderscript.Long3 long3, android.renderscript.Long3 long32) {
        return (long32.x * long3.x) + (long32.y * long3.y) + (long32.z * long3.z);
    }

    public void addMultiple(android.renderscript.Long3 long3, long j) {
        this.x += long3.x * j;
        this.y += long3.y * j;
        this.z += long3.z * j;
    }

    public void set(android.renderscript.Long3 long3) {
        this.x = long3.x;
        this.y = long3.y;
        this.z = long3.z;
    }

    public void setValues(long j, long j2, long j3) {
        this.x = j;
        this.y = j2;
        this.z = j3;
    }

    public long elementSum() {
        return this.x + this.y + this.z;
    }

    public long get(int i) {
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

    public void setAt(int i, long j) {
        switch (i) {
            case 0:
                this.x = j;
                return;
            case 1:
                this.y = j;
                return;
            case 2:
                this.z = j;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long j) {
        switch (i) {
            case 0:
                this.x += j;
                return;
            case 1:
                this.y += j;
                return;
            case 2:
                this.z += j;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] jArr, int i) {
        jArr[i] = this.x;
        jArr[i + 1] = this.y;
        jArr[i + 2] = this.z;
    }
}
