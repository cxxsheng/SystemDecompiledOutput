package android.util;

/* loaded from: classes3.dex */
public class Base64InputStream extends java.io.FilterInputStream {
    private static final int BUFFER_SIZE = 2048;
    private static byte[] EMPTY = new byte[0];
    private final android.util.Base64.Coder coder;
    private boolean eof;
    private byte[] inputBuffer;
    private int outputEnd;
    private int outputStart;

    public Base64InputStream(java.io.InputStream inputStream, int i) {
        this(inputStream, i, false);
    }

    public Base64InputStream(java.io.InputStream inputStream, int i, boolean z) {
        super(inputStream);
        this.eof = false;
        this.inputBuffer = new byte[2048];
        if (z) {
            this.coder = new android.util.Base64.Encoder(i, null);
        } else {
            this.coder = new android.util.Base64.Decoder(i, null);
        }
        this.coder.output = new byte[this.coder.maxOutputSize(2048)];
        this.outputStart = 0;
        this.outputEnd = 0;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.in.close();
        this.inputBuffer = null;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() {
        return this.outputEnd - this.outputStart;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws java.io.IOException {
        if (this.outputStart >= this.outputEnd) {
            refill();
        }
        if (this.outputStart >= this.outputEnd) {
            return 0L;
        }
        long min = java.lang.Math.min(j, this.outputEnd - this.outputStart);
        this.outputStart = (int) (this.outputStart + min);
        return min;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws java.io.IOException {
        if (this.outputStart >= this.outputEnd) {
            refill();
        }
        if (this.outputStart >= this.outputEnd) {
            return -1;
        }
        byte[] bArr = this.coder.output;
        int i = this.outputStart;
        this.outputStart = i + 1;
        return bArr[i] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.outputStart >= this.outputEnd) {
            refill();
        }
        if (this.outputStart >= this.outputEnd) {
            return -1;
        }
        int min = java.lang.Math.min(i2, this.outputEnd - this.outputStart);
        java.lang.System.arraycopy(this.coder.output, this.outputStart, bArr, i, min);
        this.outputStart += min;
        return min;
    }

    private void refill() throws java.io.IOException {
        boolean process;
        if (this.eof) {
            return;
        }
        int read = this.in.read(this.inputBuffer);
        if (read == -1) {
            this.eof = true;
            process = this.coder.process(EMPTY, 0, 0, true);
        } else {
            process = this.coder.process(this.inputBuffer, 0, read, false);
        }
        if (!process) {
            throw new android.util.Base64DataException("bad base-64");
        }
        this.outputEnd = this.coder.op;
        this.outputStart = 0;
    }
}
