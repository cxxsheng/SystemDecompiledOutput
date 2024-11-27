package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Byte2 {
    public byte x;
    public byte y;

    public Byte2() {
    }

    public Byte2(byte b, byte b2) {
        this.x = b;
        this.y = b2;
    }

    public Byte2(android.renderscript.Byte2 byte2) {
        this.x = byte2.x;
        this.y = byte2.y;
    }

    public void add(android.renderscript.Byte2 byte2) {
        this.x = (byte) (this.x + byte2.x);
        this.y = (byte) (this.y + byte2.y);
    }

    public static android.renderscript.Byte2 add(android.renderscript.Byte2 byte2, android.renderscript.Byte2 byte22) {
        android.renderscript.Byte2 byte23 = new android.renderscript.Byte2();
        byte23.x = (byte) (byte2.x + byte22.x);
        byte23.y = (byte) (byte2.y + byte22.y);
        return byte23;
    }

    public void add(byte b) {
        this.x = (byte) (this.x + b);
        this.y = (byte) (this.y + b);
    }

    public static android.renderscript.Byte2 add(android.renderscript.Byte2 byte2, byte b) {
        android.renderscript.Byte2 byte22 = new android.renderscript.Byte2();
        byte22.x = (byte) (byte2.x + b);
        byte22.y = (byte) (byte2.y + b);
        return byte22;
    }

    public void sub(android.renderscript.Byte2 byte2) {
        this.x = (byte) (this.x - byte2.x);
        this.y = (byte) (this.y - byte2.y);
    }

    public static android.renderscript.Byte2 sub(android.renderscript.Byte2 byte2, android.renderscript.Byte2 byte22) {
        android.renderscript.Byte2 byte23 = new android.renderscript.Byte2();
        byte23.x = (byte) (byte2.x - byte22.x);
        byte23.y = (byte) (byte2.y - byte22.y);
        return byte23;
    }

    public void sub(byte b) {
        this.x = (byte) (this.x - b);
        this.y = (byte) (this.y - b);
    }

    public static android.renderscript.Byte2 sub(android.renderscript.Byte2 byte2, byte b) {
        android.renderscript.Byte2 byte22 = new android.renderscript.Byte2();
        byte22.x = (byte) (byte2.x - b);
        byte22.y = (byte) (byte2.y - b);
        return byte22;
    }

    public void mul(android.renderscript.Byte2 byte2) {
        this.x = (byte) (this.x * byte2.x);
        this.y = (byte) (this.y * byte2.y);
    }

    public static android.renderscript.Byte2 mul(android.renderscript.Byte2 byte2, android.renderscript.Byte2 byte22) {
        android.renderscript.Byte2 byte23 = new android.renderscript.Byte2();
        byte23.x = (byte) (byte2.x * byte22.x);
        byte23.y = (byte) (byte2.y * byte22.y);
        return byte23;
    }

    public void mul(byte b) {
        this.x = (byte) (this.x * b);
        this.y = (byte) (this.y * b);
    }

    public static android.renderscript.Byte2 mul(android.renderscript.Byte2 byte2, byte b) {
        android.renderscript.Byte2 byte22 = new android.renderscript.Byte2();
        byte22.x = (byte) (byte2.x * b);
        byte22.y = (byte) (byte2.y * b);
        return byte22;
    }

    public void div(android.renderscript.Byte2 byte2) {
        this.x = (byte) (this.x / byte2.x);
        this.y = (byte) (this.y / byte2.y);
    }

    public static android.renderscript.Byte2 div(android.renderscript.Byte2 byte2, android.renderscript.Byte2 byte22) {
        android.renderscript.Byte2 byte23 = new android.renderscript.Byte2();
        byte23.x = (byte) (byte2.x / byte22.x);
        byte23.y = (byte) (byte2.y / byte22.y);
        return byte23;
    }

    public void div(byte b) {
        this.x = (byte) (this.x / b);
        this.y = (byte) (this.y / b);
    }

    public static android.renderscript.Byte2 div(android.renderscript.Byte2 byte2, byte b) {
        android.renderscript.Byte2 byte22 = new android.renderscript.Byte2();
        byte22.x = (byte) (byte2.x / b);
        byte22.y = (byte) (byte2.y / b);
        return byte22;
    }

    public byte length() {
        return (byte) 2;
    }

    public void negate() {
        this.x = (byte) (-this.x);
        this.y = (byte) (-this.y);
    }

    public byte dotProduct(android.renderscript.Byte2 byte2) {
        return (byte) ((this.x * byte2.x) + (this.y * byte2.y));
    }

    public static byte dotProduct(android.renderscript.Byte2 byte2, android.renderscript.Byte2 byte22) {
        return (byte) ((byte22.x * byte2.x) + (byte22.y * byte2.y));
    }

    public void addMultiple(android.renderscript.Byte2 byte2, byte b) {
        this.x = (byte) (this.x + (byte2.x * b));
        this.y = (byte) (this.y + (byte2.y * b));
    }

    public void set(android.renderscript.Byte2 byte2) {
        this.x = byte2.x;
        this.y = byte2.y;
    }

    public void setValues(byte b, byte b2) {
        this.x = b;
        this.y = b2;
    }

    public byte elementSum() {
        return (byte) (this.x + this.y);
    }

    public byte get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, byte b) {
        switch (i) {
            case 0:
                this.x = b;
                return;
            case 1:
                this.y = b;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, byte b) {
        switch (i) {
            case 0:
                this.x = (byte) (this.x + b);
                return;
            case 1:
                this.y = (byte) (this.y + b);
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] bArr, int i) {
        bArr[i] = this.x;
        bArr[i + 1] = this.y;
    }
}
