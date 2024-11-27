package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1OutputStream {
    private java.io.OutputStream os;

    public static com.android.internal.org.bouncycastle.asn1.ASN1OutputStream create(java.io.OutputStream outputStream) {
        return new com.android.internal.org.bouncycastle.asn1.ASN1OutputStream(outputStream);
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1OutputStream create(java.io.OutputStream outputStream, java.lang.String str) {
        if (str.equals(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER)) {
            return new com.android.internal.org.bouncycastle.asn1.DEROutputStream(outputStream);
        }
        if (str.equals(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DL)) {
            return new com.android.internal.org.bouncycastle.asn1.DLOutputStream(outputStream);
        }
        return new com.android.internal.org.bouncycastle.asn1.ASN1OutputStream(outputStream);
    }

    public ASN1OutputStream(java.io.OutputStream outputStream) {
        this.os = outputStream;
    }

    final void writeLength(int i) throws java.io.IOException {
        if (i > 127) {
            int i2 = i;
            int i3 = 1;
            while (true) {
                i2 >>>= 8;
                if (i2 == 0) {
                    break;
                } else {
                    i3++;
                }
            }
            write((byte) (i3 | 128));
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                write((byte) (i >> i4));
            }
            return;
        }
        write((byte) i);
    }

    final void write(int i) throws java.io.IOException {
        this.os.write(i);
    }

    final void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.os.write(bArr, i, i2);
    }

    final void writeElements(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) throws java.io.IOException {
        for (com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable : aSN1EncodableArr) {
            writePrimitive(aSN1Encodable.toASN1Primitive(), true);
        }
    }

    final void writeElements(java.util.Enumeration enumeration) throws java.io.IOException {
        while (enumeration.hasMoreElements()) {
            writePrimitive(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) enumeration.nextElement()).toASN1Primitive(), true);
        }
    }

    final void writeEncoded(boolean z, int i, byte b) throws java.io.IOException {
        if (z) {
            write(i);
        }
        writeLength(1);
        write(b);
    }

    final void writeEncoded(boolean z, int i, byte[] bArr) throws java.io.IOException {
        if (z) {
            write(i);
        }
        writeLength(bArr.length);
        write(bArr, 0, bArr.length);
    }

    final void writeEncoded(boolean z, int i, byte[] bArr, int i2, int i3) throws java.io.IOException {
        if (z) {
            write(i);
        }
        writeLength(i3);
        write(bArr, i2, i3);
    }

    final void writeEncoded(boolean z, int i, byte b, byte[] bArr) throws java.io.IOException {
        if (z) {
            write(i);
        }
        writeLength(bArr.length + 1);
        write(b);
        write(bArr, 0, bArr.length);
    }

    final void writeEncoded(boolean z, int i, byte b, byte[] bArr, int i2, int i3, byte b2) throws java.io.IOException {
        if (z) {
            write(i);
        }
        writeLength(i3 + 2);
        write(b);
        write(bArr, i2, i3);
        write(b2);
    }

    final void writeEncoded(boolean z, int i, int i2, byte[] bArr) throws java.io.IOException {
        writeTag(z, i, i2);
        writeLength(bArr.length);
        write(bArr, 0, bArr.length);
    }

    final void writeEncodedIndef(boolean z, int i, int i2, byte[] bArr) throws java.io.IOException {
        writeTag(z, i, i2);
        write(128);
        write(bArr, 0, bArr.length);
        write(0);
        write(0);
    }

    final void writeEncodedIndef(boolean z, int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) throws java.io.IOException {
        if (z) {
            write(i);
        }
        write(128);
        writeElements(aSN1EncodableArr);
        write(0);
        write(0);
    }

    final void writeEncodedIndef(boolean z, int i, java.util.Enumeration enumeration) throws java.io.IOException {
        if (z) {
            write(i);
        }
        write(128);
        writeElements(enumeration);
        write(0);
        write(0);
    }

    final void writeTag(boolean z, int i, int i2) throws java.io.IOException {
        if (!z) {
            return;
        }
        if (i2 < 31) {
            write(i | i2);
            return;
        }
        write(31 | i);
        if (i2 < 128) {
            write(i2);
            return;
        }
        byte[] bArr = new byte[5];
        int i3 = 4;
        bArr[4] = (byte) (i2 & 127);
        do {
            i2 >>= 7;
            i3--;
            bArr[i3] = (byte) ((i2 & 127) | 128);
        } while (i2 > 127);
        write(bArr, i3, 5 - i3);
    }

    public void writeObject(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        if (aSN1Encodable == null) {
            throw new java.io.IOException("null object detected");
        }
        writePrimitive(aSN1Encodable.toASN1Primitive(), true);
        flushInternal();
    }

    public void writeObject(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) throws java.io.IOException {
        if (aSN1Primitive == null) {
            throw new java.io.IOException("null object detected");
        }
        writePrimitive(aSN1Primitive, true);
        flushInternal();
    }

    void writePrimitive(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, boolean z) throws java.io.IOException {
        aSN1Primitive.encode(this, z);
    }

    public void close() throws java.io.IOException {
        this.os.close();
    }

    public void flush() throws java.io.IOException {
        this.os.flush();
    }

    void flushInternal() throws java.io.IOException {
    }

    com.android.internal.org.bouncycastle.asn1.DEROutputStream getDERSubStream() {
        return new com.android.internal.org.bouncycastle.asn1.DEROutputStream(this.os);
    }

    com.android.internal.org.bouncycastle.asn1.ASN1OutputStream getDLSubStream() {
        return new com.android.internal.org.bouncycastle.asn1.DLOutputStream(this.os);
    }
}
