package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Short3 {
    public short x;
    public short y;
    public short z;

    public Short3() {
    }

    public Short3(short s) {
        this.z = s;
        this.y = s;
        this.x = s;
    }

    public Short3(short s, short s2, short s3) {
        this.x = s;
        this.y = s2;
        this.z = s3;
    }

    public Short3(android.renderscript.Short3 short3) {
        this.x = short3.x;
        this.y = short3.y;
        this.z = short3.z;
    }

    public void add(android.renderscript.Short3 short3) {
        this.x = (short) (this.x + short3.x);
        this.y = (short) (this.y + short3.y);
        this.z = (short) (this.z + short3.z);
    }

    public static android.renderscript.Short3 add(android.renderscript.Short3 short3, android.renderscript.Short3 short32) {
        android.renderscript.Short3 short33 = new android.renderscript.Short3();
        short33.x = (short) (short3.x + short32.x);
        short33.y = (short) (short3.y + short32.y);
        short33.z = (short) (short3.z + short32.z);
        return short33;
    }

    public void add(short s) {
        this.x = (short) (this.x + s);
        this.y = (short) (this.y + s);
        this.z = (short) (this.z + s);
    }

    public static android.renderscript.Short3 add(android.renderscript.Short3 short3, short s) {
        android.renderscript.Short3 short32 = new android.renderscript.Short3();
        short32.x = (short) (short3.x + s);
        short32.y = (short) (short3.y + s);
        short32.z = (short) (short3.z + s);
        return short32;
    }

    public void sub(android.renderscript.Short3 short3) {
        this.x = (short) (this.x - short3.x);
        this.y = (short) (this.y - short3.y);
        this.z = (short) (this.z - short3.z);
    }

    public static android.renderscript.Short3 sub(android.renderscript.Short3 short3, android.renderscript.Short3 short32) {
        android.renderscript.Short3 short33 = new android.renderscript.Short3();
        short33.x = (short) (short3.x - short32.x);
        short33.y = (short) (short3.y - short32.y);
        short33.z = (short) (short3.z - short32.z);
        return short33;
    }

    public void sub(short s) {
        this.x = (short) (this.x - s);
        this.y = (short) (this.y - s);
        this.z = (short) (this.z - s);
    }

    public static android.renderscript.Short3 sub(android.renderscript.Short3 short3, short s) {
        android.renderscript.Short3 short32 = new android.renderscript.Short3();
        short32.x = (short) (short3.x - s);
        short32.y = (short) (short3.y - s);
        short32.z = (short) (short3.z - s);
        return short32;
    }

    public void mul(android.renderscript.Short3 short3) {
        this.x = (short) (this.x * short3.x);
        this.y = (short) (this.y * short3.y);
        this.z = (short) (this.z * short3.z);
    }

    public static android.renderscript.Short3 mul(android.renderscript.Short3 short3, android.renderscript.Short3 short32) {
        android.renderscript.Short3 short33 = new android.renderscript.Short3();
        short33.x = (short) (short3.x * short32.x);
        short33.y = (short) (short3.y * short32.y);
        short33.z = (short) (short3.z * short32.z);
        return short33;
    }

    public void mul(short s) {
        this.x = (short) (this.x * s);
        this.y = (short) (this.y * s);
        this.z = (short) (this.z * s);
    }

    public static android.renderscript.Short3 mul(android.renderscript.Short3 short3, short s) {
        android.renderscript.Short3 short32 = new android.renderscript.Short3();
        short32.x = (short) (short3.x * s);
        short32.y = (short) (short3.y * s);
        short32.z = (short) (short3.z * s);
        return short32;
    }

    public void div(android.renderscript.Short3 short3) {
        this.x = (short) (this.x / short3.x);
        this.y = (short) (this.y / short3.y);
        this.z = (short) (this.z / short3.z);
    }

    public static android.renderscript.Short3 div(android.renderscript.Short3 short3, android.renderscript.Short3 short32) {
        android.renderscript.Short3 short33 = new android.renderscript.Short3();
        short33.x = (short) (short3.x / short32.x);
        short33.y = (short) (short3.y / short32.y);
        short33.z = (short) (short3.z / short32.z);
        return short33;
    }

    public void div(short s) {
        this.x = (short) (this.x / s);
        this.y = (short) (this.y / s);
        this.z = (short) (this.z / s);
    }

    public static android.renderscript.Short3 div(android.renderscript.Short3 short3, short s) {
        android.renderscript.Short3 short32 = new android.renderscript.Short3();
        short32.x = (short) (short3.x / s);
        short32.y = (short) (short3.y / s);
        short32.z = (short) (short3.z / s);
        return short32;
    }

    public void mod(android.renderscript.Short3 short3) {
        this.x = (short) (this.x % short3.x);
        this.y = (short) (this.y % short3.y);
        this.z = (short) (this.z % short3.z);
    }

    public static android.renderscript.Short3 mod(android.renderscript.Short3 short3, android.renderscript.Short3 short32) {
        android.renderscript.Short3 short33 = new android.renderscript.Short3();
        short33.x = (short) (short3.x % short32.x);
        short33.y = (short) (short3.y % short32.y);
        short33.z = (short) (short3.z % short32.z);
        return short33;
    }

    public void mod(short s) {
        this.x = (short) (this.x % s);
        this.y = (short) (this.y % s);
        this.z = (short) (this.z % s);
    }

    public static android.renderscript.Short3 mod(android.renderscript.Short3 short3, short s) {
        android.renderscript.Short3 short32 = new android.renderscript.Short3();
        short32.x = (short) (short3.x % s);
        short32.y = (short) (short3.y % s);
        short32.z = (short) (short3.z % s);
        return short32;
    }

    public short length() {
        return (short) 3;
    }

    public void negate() {
        this.x = (short) (-this.x);
        this.y = (short) (-this.y);
        this.z = (short) (-this.z);
    }

    public short dotProduct(android.renderscript.Short3 short3) {
        return (short) ((this.x * short3.x) + (this.y * short3.y) + (this.z * short3.z));
    }

    public static short dotProduct(android.renderscript.Short3 short3, android.renderscript.Short3 short32) {
        return (short) ((short32.x * short3.x) + (short32.y * short3.y) + (short32.z * short3.z));
    }

    public void addMultiple(android.renderscript.Short3 short3, short s) {
        this.x = (short) (this.x + (short3.x * s));
        this.y = (short) (this.y + (short3.y * s));
        this.z = (short) (this.z + (short3.z * s));
    }

    public void set(android.renderscript.Short3 short3) {
        this.x = short3.x;
        this.y = short3.y;
        this.z = short3.z;
    }

    public void setValues(short s, short s2, short s3) {
        this.x = s;
        this.y = s2;
        this.z = s3;
    }

    public short elementSum() {
        return (short) (this.x + this.y + this.z);
    }

    public short get(int i) {
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

    public void setAt(int i, short s) {
        switch (i) {
            case 0:
                this.x = s;
                return;
            case 1:
                this.y = s;
                return;
            case 2:
                this.z = s;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short s) {
        switch (i) {
            case 0:
                this.x = (short) (this.x + s);
                return;
            case 1:
                this.y = (short) (this.y + s);
                return;
            case 2:
                this.z = (short) (this.z + s);
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] sArr, int i) {
        sArr[i] = this.x;
        sArr[i + 1] = this.y;
        sArr[i + 2] = this.z;
    }
}
