package com.android.internal.util;

/* loaded from: classes5.dex */
public class ProcFileReader implements java.io.Closeable {
    private final byte[] mBuffer;
    private boolean mLineFinished;
    private final java.io.InputStream mStream;
    private int mTail;

    public ProcFileReader(java.io.InputStream inputStream) throws java.io.IOException {
        this(inputStream, 4096);
    }

    public ProcFileReader(java.io.InputStream inputStream, int i) throws java.io.IOException {
        this.mStream = inputStream;
        this.mBuffer = new byte[i];
        if (inputStream.markSupported()) {
            this.mStream.mark(0);
        }
        fillBuf();
    }

    private int fillBuf() throws java.io.IOException {
        int length = this.mBuffer.length - this.mTail;
        if (length == 0) {
            throw new java.io.IOException("attempting to fill already-full buffer");
        }
        int read = this.mStream.read(this.mBuffer, this.mTail, length);
        if (read != -1) {
            this.mTail += read;
        }
        return read;
    }

    private void consumeBuf(int i) throws java.io.IOException {
        while (i < this.mTail && this.mBuffer[i] == 32) {
            i++;
        }
        java.lang.System.arraycopy(this.mBuffer, i, this.mBuffer, 0, this.mTail - i);
        this.mTail -= i;
        if (this.mTail == 0) {
            fillBuf();
        }
    }

    private int nextTokenIndex() throws java.io.IOException {
        if (this.mLineFinished) {
            return -1;
        }
        int i = 0;
        while (true) {
            if (i < this.mTail) {
                byte b = this.mBuffer[i];
                if (b == 10) {
                    this.mLineFinished = true;
                    return i;
                }
                if (b != 32) {
                    i++;
                } else {
                    return i;
                }
            } else if (fillBuf() <= 0) {
                throw new java.net.ProtocolException("End of stream while looking for token boundary");
            }
        }
    }

    public boolean hasMoreData() {
        return this.mTail > 0;
    }

    public void finishLine() throws java.io.IOException {
        int i = 0;
        if (this.mLineFinished) {
            this.mLineFinished = false;
            return;
        }
        while (true) {
            if (i < this.mTail) {
                if (this.mBuffer[i] != 10) {
                    i++;
                } else {
                    consumeBuf(i + 1);
                    return;
                }
            } else if (fillBuf() <= 0) {
                throw new java.net.ProtocolException("End of stream while looking for line boundary");
            }
        }
    }

    public java.lang.String nextString() throws java.io.IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            throw new java.net.ProtocolException("Missing required string");
        }
        return parseAndConsumeString(nextTokenIndex);
    }

    public long nextLong() throws java.io.IOException {
        return nextLong(false);
    }

    public long nextLong(boolean z) throws java.io.IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            throw new java.net.ProtocolException("Missing required long");
        }
        return parseAndConsumeLong(nextTokenIndex, z);
    }

    public long nextOptionalLong(long j) throws java.io.IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            return j;
        }
        return parseAndConsumeLong(nextTokenIndex, false);
    }

    private java.lang.String parseAndConsumeString(int i) throws java.io.IOException {
        java.lang.String str = new java.lang.String(this.mBuffer, 0, i, java.nio.charset.StandardCharsets.US_ASCII);
        consumeBuf(i + 1);
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private long parseAndConsumeLong(int i, boolean z) throws java.io.IOException {
        int i2 = this.mBuffer[0] == 45 ? 1 : 0;
        long j = 0;
        int i3 = i2;
        while (i3 < i) {
            int i4 = this.mBuffer[i3] + com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE;
            if (i4 < 0 || i4 > 9) {
                if (!z) {
                    throw invalidLong(i);
                }
                consumeBuf(i + 1);
                return i2 == 0 ? j : -j;
            }
            long j2 = (10 * j) - i4;
            if (j2 <= j) {
                i3++;
                j = j2;
            } else {
                throw invalidLong(i);
            }
        }
        consumeBuf(i + 1);
        if (i2 == 0) {
        }
    }

    private java.lang.NumberFormatException invalidLong(int i) {
        return new java.lang.NumberFormatException("invalid long: " + new java.lang.String(this.mBuffer, 0, i, java.nio.charset.StandardCharsets.US_ASCII));
    }

    public int nextInt() throws java.io.IOException {
        long nextLong = nextLong();
        if (nextLong > 2147483647L || nextLong < -2147483648L) {
            throw new java.lang.NumberFormatException("parsed value larger than integer");
        }
        return (int) nextLong;
    }

    public void nextIgnored() throws java.io.IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            throw new java.net.ProtocolException("Missing required token");
        }
        consumeBuf(nextTokenIndex + 1);
    }

    public void rewind() throws java.io.IOException {
        if (this.mStream instanceof java.io.FileInputStream) {
            ((java.io.FileInputStream) this.mStream).getChannel().position(0L);
        } else if (this.mStream.markSupported()) {
            this.mStream.reset();
        } else {
            throw new java.io.IOException("The InputStream is NOT markable");
        }
        this.mTail = 0;
        this.mLineFinished = false;
        fillBuf();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.mStream.close();
    }
}
