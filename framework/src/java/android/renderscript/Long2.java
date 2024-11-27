package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Long2 {
    public long x;
    public long y;

    public Long2() {
    }

    public Long2(long j) {
        this.y = j;
        this.x = j;
    }

    public Long2(long j, long j2) {
        this.x = j;
        this.y = j2;
    }

    public Long2(android.renderscript.Long2 long2) {
        this.x = long2.x;
        this.y = long2.y;
    }

    public void add(android.renderscript.Long2 long2) {
        this.x += long2.x;
        this.y += long2.y;
    }

    public static android.renderscript.Long2 add(android.renderscript.Long2 long2, android.renderscript.Long2 long22) {
        android.renderscript.Long2 long23 = new android.renderscript.Long2();
        long23.x = long2.x + long22.x;
        long23.y = long2.y + long22.y;
        return long23;
    }

    public void add(long j) {
        this.x += j;
        this.y += j;
    }

    public static android.renderscript.Long2 add(android.renderscript.Long2 long2, long j) {
        android.renderscript.Long2 long22 = new android.renderscript.Long2();
        long22.x = long2.x + j;
        long22.y = long2.y + j;
        return long22;
    }

    public void sub(android.renderscript.Long2 long2) {
        this.x -= long2.x;
        this.y -= long2.y;
    }

    public static android.renderscript.Long2 sub(android.renderscript.Long2 long2, android.renderscript.Long2 long22) {
        android.renderscript.Long2 long23 = new android.renderscript.Long2();
        long23.x = long2.x - long22.x;
        long23.y = long2.y - long22.y;
        return long23;
    }

    public void sub(long j) {
        this.x -= j;
        this.y -= j;
    }

    public static android.renderscript.Long2 sub(android.renderscript.Long2 long2, long j) {
        android.renderscript.Long2 long22 = new android.renderscript.Long2();
        long22.x = long2.x - j;
        long22.y = long2.y - j;
        return long22;
    }

    public void mul(android.renderscript.Long2 long2) {
        this.x *= long2.x;
        this.y *= long2.y;
    }

    public static android.renderscript.Long2 mul(android.renderscript.Long2 long2, android.renderscript.Long2 long22) {
        android.renderscript.Long2 long23 = new android.renderscript.Long2();
        long23.x = long2.x * long22.x;
        long23.y = long2.y * long22.y;
        return long23;
    }

    public void mul(long j) {
        this.x *= j;
        this.y *= j;
    }

    public static android.renderscript.Long2 mul(android.renderscript.Long2 long2, long j) {
        android.renderscript.Long2 long22 = new android.renderscript.Long2();
        long22.x = long2.x * j;
        long22.y = long2.y * j;
        return long22;
    }

    public void div(android.renderscript.Long2 long2) {
        this.x /= long2.x;
        this.y /= long2.y;
    }

    public static android.renderscript.Long2 div(android.renderscript.Long2 long2, android.renderscript.Long2 long22) {
        android.renderscript.Long2 long23 = new android.renderscript.Long2();
        long23.x = long2.x / long22.x;
        long23.y = long2.y / long22.y;
        return long23;
    }

    public void div(long j) {
        this.x /= j;
        this.y /= j;
    }

    public static android.renderscript.Long2 div(android.renderscript.Long2 long2, long j) {
        android.renderscript.Long2 long22 = new android.renderscript.Long2();
        long22.x = long2.x / j;
        long22.y = long2.y / j;
        return long22;
    }

    public void mod(android.renderscript.Long2 long2) {
        this.x %= long2.x;
        this.y %= long2.y;
    }

    public static android.renderscript.Long2 mod(android.renderscript.Long2 long2, android.renderscript.Long2 long22) {
        android.renderscript.Long2 long23 = new android.renderscript.Long2();
        long23.x = long2.x % long22.x;
        long23.y = long2.y % long22.y;
        return long23;
    }

    public void mod(long j) {
        this.x %= j;
        this.y %= j;
    }

    public static android.renderscript.Long2 mod(android.renderscript.Long2 long2, long j) {
        android.renderscript.Long2 long22 = new android.renderscript.Long2();
        long22.x = long2.x % j;
        long22.y = long2.y % j;
        return long22;
    }

    public long length() {
        return 2L;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public long dotProduct(android.renderscript.Long2 long2) {
        return (this.x * long2.x) + (this.y * long2.y);
    }

    public static long dotProduct(android.renderscript.Long2 long2, android.renderscript.Long2 long22) {
        return (long22.x * long2.x) + (long22.y * long2.y);
    }

    public void addMultiple(android.renderscript.Long2 long2, long j) {
        this.x += long2.x * j;
        this.y += long2.y * j;
    }

    public void set(android.renderscript.Long2 long2) {
        this.x = long2.x;
        this.y = long2.y;
    }

    public void setValues(long j, long j2) {
        this.x = j;
        this.y = j2;
    }

    public long elementSum() {
        return this.x + this.y;
    }

    public long get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] jArr, int i) {
        jArr[i] = this.x;
        jArr[i + 1] = this.y;
    }
}
