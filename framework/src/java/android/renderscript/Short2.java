package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Short2 {
    public short x;
    public short y;

    public Short2() {
    }

    public Short2(short s) {
        this.y = s;
        this.x = s;
    }

    public Short2(short s, short s2) {
        this.x = s;
        this.y = s2;
    }

    public Short2(android.renderscript.Short2 short2) {
        this.x = short2.x;
        this.y = short2.y;
    }

    public void add(android.renderscript.Short2 short2) {
        this.x = (short) (this.x + short2.x);
        this.y = (short) (this.y + short2.y);
    }

    public static android.renderscript.Short2 add(android.renderscript.Short2 short2, android.renderscript.Short2 short22) {
        android.renderscript.Short2 short23 = new android.renderscript.Short2();
        short23.x = (short) (short2.x + short22.x);
        short23.y = (short) (short2.y + short22.y);
        return short23;
    }

    public void add(short s) {
        this.x = (short) (this.x + s);
        this.y = (short) (this.y + s);
    }

    public static android.renderscript.Short2 add(android.renderscript.Short2 short2, short s) {
        android.renderscript.Short2 short22 = new android.renderscript.Short2();
        short22.x = (short) (short2.x + s);
        short22.y = (short) (short2.y + s);
        return short22;
    }

    public void sub(android.renderscript.Short2 short2) {
        this.x = (short) (this.x - short2.x);
        this.y = (short) (this.y - short2.y);
    }

    public static android.renderscript.Short2 sub(android.renderscript.Short2 short2, android.renderscript.Short2 short22) {
        android.renderscript.Short2 short23 = new android.renderscript.Short2();
        short23.x = (short) (short2.x - short22.x);
        short23.y = (short) (short2.y - short22.y);
        return short23;
    }

    public void sub(short s) {
        this.x = (short) (this.x - s);
        this.y = (short) (this.y - s);
    }

    public static android.renderscript.Short2 sub(android.renderscript.Short2 short2, short s) {
        android.renderscript.Short2 short22 = new android.renderscript.Short2();
        short22.x = (short) (short2.x - s);
        short22.y = (short) (short2.y - s);
        return short22;
    }

    public void mul(android.renderscript.Short2 short2) {
        this.x = (short) (this.x * short2.x);
        this.y = (short) (this.y * short2.y);
    }

    public static android.renderscript.Short2 mul(android.renderscript.Short2 short2, android.renderscript.Short2 short22) {
        android.renderscript.Short2 short23 = new android.renderscript.Short2();
        short23.x = (short) (short2.x * short22.x);
        short23.y = (short) (short2.y * short22.y);
        return short23;
    }

    public void mul(short s) {
        this.x = (short) (this.x * s);
        this.y = (short) (this.y * s);
    }

    public static android.renderscript.Short2 mul(android.renderscript.Short2 short2, short s) {
        android.renderscript.Short2 short22 = new android.renderscript.Short2();
        short22.x = (short) (short2.x * s);
        short22.y = (short) (short2.y * s);
        return short22;
    }

    public void div(android.renderscript.Short2 short2) {
        this.x = (short) (this.x / short2.x);
        this.y = (short) (this.y / short2.y);
    }

    public static android.renderscript.Short2 div(android.renderscript.Short2 short2, android.renderscript.Short2 short22) {
        android.renderscript.Short2 short23 = new android.renderscript.Short2();
        short23.x = (short) (short2.x / short22.x);
        short23.y = (short) (short2.y / short22.y);
        return short23;
    }

    public void div(short s) {
        this.x = (short) (this.x / s);
        this.y = (short) (this.y / s);
    }

    public static android.renderscript.Short2 div(android.renderscript.Short2 short2, short s) {
        android.renderscript.Short2 short22 = new android.renderscript.Short2();
        short22.x = (short) (short2.x / s);
        short22.y = (short) (short2.y / s);
        return short22;
    }

    public void mod(android.renderscript.Short2 short2) {
        this.x = (short) (this.x % short2.x);
        this.y = (short) (this.y % short2.y);
    }

    public static android.renderscript.Short2 mod(android.renderscript.Short2 short2, android.renderscript.Short2 short22) {
        android.renderscript.Short2 short23 = new android.renderscript.Short2();
        short23.x = (short) (short2.x % short22.x);
        short23.y = (short) (short2.y % short22.y);
        return short23;
    }

    public void mod(short s) {
        this.x = (short) (this.x % s);
        this.y = (short) (this.y % s);
    }

    public static android.renderscript.Short2 mod(android.renderscript.Short2 short2, short s) {
        android.renderscript.Short2 short22 = new android.renderscript.Short2();
        short22.x = (short) (short2.x % s);
        short22.y = (short) (short2.y % s);
        return short22;
    }

    public short length() {
        return (short) 2;
    }

    public void negate() {
        this.x = (short) (-this.x);
        this.y = (short) (-this.y);
    }

    public short dotProduct(android.renderscript.Short2 short2) {
        return (short) ((this.x * short2.x) + (this.y * short2.y));
    }

    public static short dotProduct(android.renderscript.Short2 short2, android.renderscript.Short2 short22) {
        return (short) ((short22.x * short2.x) + (short22.y * short2.y));
    }

    public void addMultiple(android.renderscript.Short2 short2, short s) {
        this.x = (short) (this.x + (short2.x * s));
        this.y = (short) (this.y + (short2.y * s));
    }

    public void set(android.renderscript.Short2 short2) {
        this.x = short2.x;
        this.y = short2.y;
    }

    public void setValues(short s, short s2) {
        this.x = s;
        this.y = s2;
    }

    public short elementSum() {
        return (short) (this.x + this.y);
    }

    public short get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] sArr, int i) {
        sArr[i] = this.x;
        sArr[i + 1] = this.y;
    }
}
