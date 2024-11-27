package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Byte4 {
    public byte w;
    public byte x;
    public byte y;
    public byte z;

    public Byte4() {
    }

    public Byte4(byte b, byte b2, byte b3, byte b4) {
        this.x = b;
        this.y = b2;
        this.z = b3;
        this.w = b4;
    }

    public Byte4(android.renderscript.Byte4 byte4) {
        this.x = byte4.x;
        this.y = byte4.y;
        this.z = byte4.z;
        this.w = byte4.w;
    }

    public void add(android.renderscript.Byte4 byte4) {
        this.x = (byte) (this.x + byte4.x);
        this.y = (byte) (this.y + byte4.y);
        this.z = (byte) (this.z + byte4.z);
        this.w = (byte) (this.w + byte4.w);
    }

    public static android.renderscript.Byte4 add(android.renderscript.Byte4 byte4, android.renderscript.Byte4 byte42) {
        android.renderscript.Byte4 byte43 = new android.renderscript.Byte4();
        byte43.x = (byte) (byte4.x + byte42.x);
        byte43.y = (byte) (byte4.y + byte42.y);
        byte43.z = (byte) (byte4.z + byte42.z);
        byte43.w = (byte) (byte4.w + byte42.w);
        return byte43;
    }

    public void add(byte b) {
        this.x = (byte) (this.x + b);
        this.y = (byte) (this.y + b);
        this.z = (byte) (this.z + b);
        this.w = (byte) (this.w + b);
    }

    public static android.renderscript.Byte4 add(android.renderscript.Byte4 byte4, byte b) {
        android.renderscript.Byte4 byte42 = new android.renderscript.Byte4();
        byte42.x = (byte) (byte4.x + b);
        byte42.y = (byte) (byte4.y + b);
        byte42.z = (byte) (byte4.z + b);
        byte42.w = (byte) (byte4.w + b);
        return byte42;
    }

    public void sub(android.renderscript.Byte4 byte4) {
        this.x = (byte) (this.x - byte4.x);
        this.y = (byte) (this.y - byte4.y);
        this.z = (byte) (this.z - byte4.z);
        this.w = (byte) (this.w - byte4.w);
    }

    public static android.renderscript.Byte4 sub(android.renderscript.Byte4 byte4, android.renderscript.Byte4 byte42) {
        android.renderscript.Byte4 byte43 = new android.renderscript.Byte4();
        byte43.x = (byte) (byte4.x - byte42.x);
        byte43.y = (byte) (byte4.y - byte42.y);
        byte43.z = (byte) (byte4.z - byte42.z);
        byte43.w = (byte) (byte4.w - byte42.w);
        return byte43;
    }

    public void sub(byte b) {
        this.x = (byte) (this.x - b);
        this.y = (byte) (this.y - b);
        this.z = (byte) (this.z - b);
        this.w = (byte) (this.w - b);
    }

    public static android.renderscript.Byte4 sub(android.renderscript.Byte4 byte4, byte b) {
        android.renderscript.Byte4 byte42 = new android.renderscript.Byte4();
        byte42.x = (byte) (byte4.x - b);
        byte42.y = (byte) (byte4.y - b);
        byte42.z = (byte) (byte4.z - b);
        byte42.w = (byte) (byte4.w - b);
        return byte42;
    }

    public void mul(android.renderscript.Byte4 byte4) {
        this.x = (byte) (this.x * byte4.x);
        this.y = (byte) (this.y * byte4.y);
        this.z = (byte) (this.z * byte4.z);
        this.w = (byte) (this.w * byte4.w);
    }

    public static android.renderscript.Byte4 mul(android.renderscript.Byte4 byte4, android.renderscript.Byte4 byte42) {
        android.renderscript.Byte4 byte43 = new android.renderscript.Byte4();
        byte43.x = (byte) (byte4.x * byte42.x);
        byte43.y = (byte) (byte4.y * byte42.y);
        byte43.z = (byte) (byte4.z * byte42.z);
        byte43.w = (byte) (byte4.w * byte42.w);
        return byte43;
    }

    public void mul(byte b) {
        this.x = (byte) (this.x * b);
        this.y = (byte) (this.y * b);
        this.z = (byte) (this.z * b);
        this.w = (byte) (this.w * b);
    }

    public static android.renderscript.Byte4 mul(android.renderscript.Byte4 byte4, byte b) {
        android.renderscript.Byte4 byte42 = new android.renderscript.Byte4();
        byte42.x = (byte) (byte4.x * b);
        byte42.y = (byte) (byte4.y * b);
        byte42.z = (byte) (byte4.z * b);
        byte42.w = (byte) (byte4.w * b);
        return byte42;
    }

    public void div(android.renderscript.Byte4 byte4) {
        this.x = (byte) (this.x / byte4.x);
        this.y = (byte) (this.y / byte4.y);
        this.z = (byte) (this.z / byte4.z);
        this.w = (byte) (this.w / byte4.w);
    }

    public static android.renderscript.Byte4 div(android.renderscript.Byte4 byte4, android.renderscript.Byte4 byte42) {
        android.renderscript.Byte4 byte43 = new android.renderscript.Byte4();
        byte43.x = (byte) (byte4.x / byte42.x);
        byte43.y = (byte) (byte4.y / byte42.y);
        byte43.z = (byte) (byte4.z / byte42.z);
        byte43.w = (byte) (byte4.w / byte42.w);
        return byte43;
    }

    public void div(byte b) {
        this.x = (byte) (this.x / b);
        this.y = (byte) (this.y / b);
        this.z = (byte) (this.z / b);
        this.w = (byte) (this.w / b);
    }

    public static android.renderscript.Byte4 div(android.renderscript.Byte4 byte4, byte b) {
        android.renderscript.Byte4 byte42 = new android.renderscript.Byte4();
        byte42.x = (byte) (byte4.x / b);
        byte42.y = (byte) (byte4.y / b);
        byte42.z = (byte) (byte4.z / b);
        byte42.w = (byte) (byte4.w / b);
        return byte42;
    }

    public byte length() {
        return (byte) 4;
    }

    public void negate() {
        this.x = (byte) (-this.x);
        this.y = (byte) (-this.y);
        this.z = (byte) (-this.z);
        this.w = (byte) (-this.w);
    }

    public byte dotProduct(android.renderscript.Byte4 byte4) {
        return (byte) ((this.x * byte4.x) + (this.y * byte4.y) + (this.z * byte4.z) + (this.w * byte4.w));
    }

    public static byte dotProduct(android.renderscript.Byte4 byte4, android.renderscript.Byte4 byte42) {
        return (byte) ((byte42.x * byte4.x) + (byte42.y * byte4.y) + (byte42.z * byte4.z) + (byte42.w * byte4.w));
    }

    public void addMultiple(android.renderscript.Byte4 byte4, byte b) {
        this.x = (byte) (this.x + (byte4.x * b));
        this.y = (byte) (this.y + (byte4.y * b));
        this.z = (byte) (this.z + (byte4.z * b));
        this.w = (byte) (this.w + (byte4.w * b));
    }

    public void set(android.renderscript.Byte4 byte4) {
        this.x = byte4.x;
        this.y = byte4.y;
        this.z = byte4.z;
        this.w = byte4.w;
    }

    public void setValues(byte b, byte b2, byte b3, byte b4) {
        this.x = b;
        this.y = b2;
        this.z = b3;
        this.w = b4;
    }

    public byte elementSum() {
        return (byte) (this.x + this.y + this.z + this.w);
    }

    public byte get(int i) {
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

    public void setAt(int i, byte b) {
        switch (i) {
            case 0:
                this.x = b;
                return;
            case 1:
                this.y = b;
                return;
            case 2:
                this.z = b;
                return;
            case 3:
                this.w = b;
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
            case 2:
                this.z = (byte) (this.z + b);
                return;
            case 3:
                this.w = (byte) (this.w + b);
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] bArr, int i) {
        bArr[i] = this.x;
        bArr[i + 1] = this.y;
        bArr[i + 2] = this.z;
        bArr[i + 3] = this.w;
    }
}
