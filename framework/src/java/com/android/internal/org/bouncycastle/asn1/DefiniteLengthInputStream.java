package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class DefiniteLengthInputStream extends com.android.internal.org.bouncycastle.asn1.LimitedInputStream {
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final int _originalLength;
    private int _remaining;

    DefiniteLengthInputStream(java.io.InputStream inputStream, int i, int i2) {
        super(inputStream, i2);
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("negative lengths not allowed");
        }
        this._originalLength = i;
        this._remaining = i;
        if (i == 0) {
            setParentEofDetect(true);
        }
    }

    int getRemaining() {
        return this._remaining;
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        if (this._remaining == 0) {
            return -1;
        }
        int read = this._in.read();
        if (read < 0) {
            throw new java.io.EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
        }
        int i = this._remaining - 1;
        this._remaining = i;
        if (i == 0) {
            setParentEofDetect(true);
        }
        return read;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this._remaining == 0) {
            return -1;
        }
        int read = this._in.read(bArr, i, java.lang.Math.min(i2, this._remaining));
        if (read < 0) {
            throw new java.io.EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
        }
        int i3 = this._remaining - read;
        this._remaining = i3;
        if (i3 == 0) {
            setParentEofDetect(true);
        }
        return read;
    }

    void readAllIntoByteArray(byte[] bArr) throws java.io.IOException {
        if (this._remaining != bArr.length) {
            throw new java.lang.IllegalArgumentException("buffer length not right for data");
        }
        if (this._remaining == 0) {
            return;
        }
        int limit = getLimit();
        if (this._remaining >= limit) {
            throw new java.io.IOException("corrupted stream - out of bounds length found: " + this._remaining + " >= " + limit);
        }
        int readFully = this._remaining - com.android.internal.org.bouncycastle.util.io.Streams.readFully(this._in, bArr);
        this._remaining = readFully;
        if (readFully != 0) {
            throw new java.io.EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
        }
        setParentEofDetect(true);
    }

    byte[] toByteArray() throws java.io.IOException {
        if (this._remaining == 0) {
            return EMPTY_BYTES;
        }
        int limit = getLimit();
        if (this._remaining >= limit) {
            throw new java.io.IOException("corrupted stream - out of bounds length found: " + this._remaining + " >= " + limit);
        }
        byte[] bArr = new byte[this._remaining];
        int readFully = this._remaining - com.android.internal.org.bouncycastle.util.io.Streams.readFully(this._in, bArr);
        this._remaining = readFully;
        if (readFully != 0) {
            throw new java.io.EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
        }
        setParentEofDetect(true);
        return bArr;
    }
}
