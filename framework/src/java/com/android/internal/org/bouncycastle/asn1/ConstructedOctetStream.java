package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class ConstructedOctetStream extends java.io.InputStream {
    private java.io.InputStream _currentStream;
    private boolean _first = true;
    private final com.android.internal.org.bouncycastle.asn1.ASN1StreamParser _parser;

    ConstructedOctetStream(com.android.internal.org.bouncycastle.asn1.ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser nextParser;
        int i3 = 0;
        if (this._currentStream == null) {
            if (!this._first || (nextParser = getNextParser()) == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getOctetStream();
        }
        while (true) {
            int read = this._currentStream.read(bArr, i + i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser nextParser2 = getNextParser();
                if (nextParser2 == null) {
                    this._currentStream = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this._currentStream = nextParser2.getOctetStream();
            }
        }
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser nextParser;
        if (this._currentStream == null) {
            if (!this._first || (nextParser = getNextParser()) == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getOctetStream();
        }
        while (true) {
            int read = this._currentStream.read();
            if (read >= 0) {
                return read;
            }
            com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser nextParser2 = getNextParser();
            if (nextParser2 == null) {
                this._currentStream = null;
                return -1;
            }
            this._currentStream = nextParser2.getOctetStream();
        }
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser getNextParser() throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject = this._parser.readObject();
        if (readObject == null) {
            return null;
        }
        if (readObject instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser) readObject;
        }
        throw new java.io.IOException("unknown object encountered: " + readObject.getClass());
    }
}
