package android.content.pm;

/* loaded from: classes.dex */
public class LimitedLengthInputStream extends java.io.FilterInputStream {
    private final long mEnd;
    private long mOffset;

    public LimitedLengthInputStream(java.io.InputStream inputStream, long j, long j2) throws java.io.IOException {
        super(inputStream);
        if (inputStream == null) {
            throw new java.io.IOException("in == null");
        }
        if (j < 0) {
            throw new java.io.IOException("offset < 0");
        }
        if (j2 < 0) {
            throw new java.io.IOException("length < 0");
        }
        if (j2 > Long.MAX_VALUE - j) {
            throw new java.io.IOException("offset + length > Long.MAX_VALUE");
        }
        this.mEnd = j2 + j;
        skip(j);
        this.mOffset = j;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws java.io.IOException {
        if (this.mOffset >= this.mEnd) {
            return -1;
        }
        this.mOffset++;
        return super.read();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.mOffset >= this.mEnd) {
            return -1;
        }
        com.android.internal.util.ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
        long j = i2;
        if (this.mOffset > Long.MAX_VALUE - j) {
            throw new java.io.IOException("offset out of bounds: " + this.mOffset + " + " + i2);
        }
        if (this.mOffset + j > this.mEnd) {
            i2 = (int) (this.mEnd - this.mOffset);
        }
        int read = super.read(bArr, i, i2);
        this.mOffset += read;
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws java.io.IOException {
        return read(bArr, 0, bArr.length);
    }
}
