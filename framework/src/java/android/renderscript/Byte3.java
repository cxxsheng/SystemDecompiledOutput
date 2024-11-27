package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Byte3 {
    public byte x;
    public byte y;
    public byte z;

    public Byte3() {
    }

    public Byte3(byte b, byte b2, byte b3) {
        this.x = b;
        this.y = b2;
        this.z = b3;
    }

    public Byte3(android.renderscript.Byte3 byte3) {
        this.x = byte3.x;
        this.y = byte3.y;
        this.z = byte3.z;
    }

    public void add(android.renderscript.Byte3 byte3) {
        this.x = (byte) (this.x + byte3.x);
        this.y = (byte) (this.y + byte3.y);
        this.z = (byte) (this.z + byte3.z);
    }

    public static android.renderscript.Byte3 add(android.renderscript.Byte3 byte3, android.renderscript.Byte3 byte32) {
        android.renderscript.Byte3 byte33 = new android.renderscript.Byte3();
        byte33.x = (byte) (byte3.x + byte32.x);
        byte33.y = (byte) (byte3.y + byte32.y);
        byte33.z = (byte) (byte3.z + byte32.z);
        return byte33;
    }

    public void add(byte b) {
        this.x = (byte) (this.x + b);
        this.y = (byte) (this.y + b);
        this.z = (byte) (this.z + b);
    }

    public static android.renderscript.Byte3 add(android.renderscript.Byte3 byte3, byte b) {
        android.renderscript.Byte3 byte32 = new android.renderscript.Byte3();
        byte32.x = (byte) (byte3.x + b);
        byte32.y = (byte) (byte3.y + b);
        byte32.z = (byte) (byte3.z + b);
        return byte32;
    }

    public void sub(android.renderscript.Byte3 byte3) {
        this.x = (byte) (this.x - byte3.x);
        this.y = (byte) (this.y - byte3.y);
        this.z = (byte) (this.z - byte3.z);
    }

    public static android.renderscript.Byte3 sub(android.renderscript.Byte3 byte3, android.renderscript.Byte3 byte32) {
        android.renderscript.Byte3 byte33 = new android.renderscript.Byte3();
        byte33.x = (byte) (byte3.x - byte32.x);
        byte33.y = (byte) (byte3.y - byte32.y);
        byte33.z = (byte) (byte3.z - byte32.z);
        return byte33;
    }

    public void sub(byte b) {
        this.x = (byte) (this.x - b);
        this.y = (byte) (this.y - b);
        this.z = (byte) (this.z - b);
    }

    public static android.renderscript.Byte3 sub(android.renderscript.Byte3 byte3, byte b) {
        android.renderscript.Byte3 byte32 = new android.renderscript.Byte3();
        byte32.x = (byte) (byte3.x - b);
        byte32.y = (byte) (byte3.y - b);
        byte32.z = (byte) (byte3.z - b);
        return byte32;
    }

    public void mul(android.renderscript.Byte3 byte3) {
        this.x = (byte) (this.x * byte3.x);
        this.y = (byte) (this.y * byte3.y);
        this.z = (byte) (this.z * byte3.z);
    }

    public static android.renderscript.Byte3 mul(android.renderscript.Byte3 byte3, android.renderscript.Byte3 byte32) {
        android.renderscript.Byte3 byte33 = new android.renderscript.Byte3();
        byte33.x = (byte) (byte3.x * byte32.x);
        byte33.y = (byte) (byte3.y * byte32.y);
        byte33.z = (byte) (byte3.z * byte32.z);
        return byte33;
    }

    public void mul(byte b) {
        this.x = (byte) (this.x * b);
        this.y = (byte) (this.y * b);
        this.z = (byte) (this.z * b);
    }

    public static android.renderscript.Byte3 mul(android.renderscript.Byte3 byte3, byte b) {
        android.renderscript.Byte3 byte32 = new android.renderscript.Byte3();
        byte32.x = (byte) (byte3.x * b);
        byte32.y = (byte) (byte3.y * b);
        byte32.z = (byte) (byte3.z * b);
        return byte32;
    }

    public void div(android.renderscript.Byte3 byte3) {
        this.x = (byte) (this.x / byte3.x);
        this.y = (byte) (this.y / byte3.y);
        this.z = (byte) (this.z / byte3.z);
    }

    public static android.renderscript.Byte3 div(android.renderscript.Byte3 byte3, android.renderscript.Byte3 byte32) {
        android.renderscript.Byte3 byte33 = new android.renderscript.Byte3();
        byte33.x = (byte) (byte3.x / byte32.x);
        byte33.y = (byte) (byte3.y / byte32.y);
        byte33.z = (byte) (byte3.z / byte32.z);
        return byte33;
    }

    public void div(byte b) {
        this.x = (byte) (this.x / b);
        this.y = (byte) (this.y / b);
        this.z = (byte) (this.z / b);
    }

    public static android.renderscript.Byte3 div(android.renderscript.Byte3 byte3, byte b) {
        android.renderscript.Byte3 byte32 = new android.renderscript.Byte3();
        byte32.x = (byte) (byte3.x / b);
        byte32.y = (byte) (byte3.y / b);
        byte32.z = (byte) (byte3.z / b);
        return byte32;
    }

    public byte length() {
        return (byte) 3;
    }

    public void negate() {
        this.x = (byte) (-this.x);
        this.y = (byte) (-this.y);
        this.z = (byte) (-this.z);
    }

    public byte dotProduct(android.renderscript.Byte3 byte3) {
        return (byte) (((byte) (((byte) (this.x * byte3.x)) + ((byte) (this.y * byte3.y)))) + ((byte) (this.z * byte3.z)));
    }

    public static byte dotProduct(android.renderscript.Byte3 byte3, android.renderscript.Byte3 byte32) {
        return (byte) (((byte) (((byte) (byte32.x * byte3.x)) + ((byte) (byte32.y * byte3.y)))) + ((byte) (byte32.z * byte3.z)));
    }

    public void addMultiple(android.renderscript.Byte3 byte3, byte b) {
        this.x = (byte) (this.x + (byte3.x * b));
        this.y = (byte) (this.y + (byte3.y * b));
        this.z = (byte) (this.z + (byte3.z * b));
    }

    public void set(android.renderscript.Byte3 byte3) {
        this.x = byte3.x;
        this.y = byte3.y;
        this.z = byte3.z;
    }

    public void setValues(byte b, byte b2, byte b3) {
        this.x = b;
        this.y = b2;
        this.z = b3;
    }

    public byte elementSum() {
        return (byte) (this.x + this.y + this.z);
    }

    public byte get(int i) {
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] bArr, int i) {
        bArr[i] = this.x;
        bArr[i + 1] = this.y;
        bArr[i + 2] = this.z;
    }
}
