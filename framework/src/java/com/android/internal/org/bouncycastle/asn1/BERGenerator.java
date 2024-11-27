package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BERGenerator extends com.android.internal.org.bouncycastle.asn1.ASN1Generator {
    private boolean _isExplicit;
    private int _tagNo;
    private boolean _tagged;

    protected BERGenerator(java.io.OutputStream outputStream) {
        super(outputStream);
        this._tagged = false;
    }

    protected BERGenerator(java.io.OutputStream outputStream, int i, boolean z) {
        super(outputStream);
        this._tagged = false;
        this._tagged = true;
        this._isExplicit = z;
        this._tagNo = i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Generator
    public java.io.OutputStream getRawOutputStream() {
        return this._out;
    }

    private void writeHdr(int i) throws java.io.IOException {
        this._out.write(i);
        this._out.write(128);
    }

    protected void writeBERHeader(int i) throws java.io.IOException {
        if (this._tagged) {
            int i2 = this._tagNo | 128;
            if (this._isExplicit) {
                writeHdr(i2 | 32);
                writeHdr(i);
                return;
            } else if ((i & 32) != 0) {
                writeHdr(i2 | 32);
                return;
            } else {
                writeHdr(i2);
                return;
            }
        }
        writeHdr(i);
    }

    protected void writeBEREnd() throws java.io.IOException {
        this._out.write(0);
        this._out.write(0);
        if (this._tagged && this._isExplicit) {
            this._out.write(0);
            this._out.write(0);
        }
    }
}
