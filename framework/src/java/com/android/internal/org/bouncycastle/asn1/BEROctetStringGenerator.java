package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BEROctetStringGenerator extends com.android.internal.org.bouncycastle.asn1.BERGenerator {
    public BEROctetStringGenerator(java.io.OutputStream outputStream) throws java.io.IOException {
        super(outputStream);
        writeBERHeader(36);
    }

    public BEROctetStringGenerator(java.io.OutputStream outputStream, int i, boolean z) throws java.io.IOException {
        super(outputStream, i, z);
        writeBERHeader(36);
    }

    public java.io.OutputStream getOctetOutputStream() {
        return getOctetOutputStream(new byte[1000]);
    }

    public java.io.OutputStream getOctetOutputStream(byte[] bArr) {
        return new com.android.internal.org.bouncycastle.asn1.BEROctetStringGenerator.BufferedBEROctetStream(bArr);
    }

    private class BufferedBEROctetStream extends java.io.OutputStream {
        private byte[] _buf;
        private com.android.internal.org.bouncycastle.asn1.DEROutputStream _derOut;
        private int _off = 0;

        BufferedBEROctetStream(byte[] bArr) {
            this._buf = bArr;
            this._derOut = new com.android.internal.org.bouncycastle.asn1.DEROutputStream(com.android.internal.org.bouncycastle.asn1.BEROctetStringGenerator.this._out);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            byte[] bArr = this._buf;
            int i2 = this._off;
            this._off = i2 + 1;
            bArr[i2] = (byte) i;
            if (this._off == this._buf.length) {
                com.android.internal.org.bouncycastle.asn1.DEROctetString.encode(this._derOut, true, this._buf, 0, this._buf.length);
                this._off = 0;
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            while (i2 > 0) {
                int min = java.lang.Math.min(i2, this._buf.length - this._off);
                java.lang.System.arraycopy(bArr, i, this._buf, this._off, min);
                this._off += min;
                if (this._off >= this._buf.length) {
                    com.android.internal.org.bouncycastle.asn1.DEROctetString.encode(this._derOut, true, this._buf, 0, this._buf.length);
                    this._off = 0;
                    i += min;
                    i2 -= min;
                } else {
                    return;
                }
            }
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            if (this._off != 0) {
                com.android.internal.org.bouncycastle.asn1.DEROctetString.encode(this._derOut, true, this._buf, 0, this._off);
            }
            this._derOut.flushInternal();
            com.android.internal.org.bouncycastle.asn1.BEROctetStringGenerator.this.writeBEREnd();
        }
    }
}
