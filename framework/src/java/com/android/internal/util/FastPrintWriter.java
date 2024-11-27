package com.android.internal.util;

/* loaded from: classes5.dex */
public class FastPrintWriter extends java.io.PrintWriter {
    private final boolean mAutoFlush;
    private final int mBufferLen;
    private final java.nio.ByteBuffer mBytes;
    private java.nio.charset.CharsetEncoder mCharset;
    private boolean mIoError;
    private final java.io.OutputStream mOutputStream;
    private int mPos;
    private final android.util.Printer mPrinter;
    private final java.lang.String mSeparator;
    private final char[] mText;
    private final java.io.Writer mWriter;

    private static class DummyWriter extends java.io.Writer {
        private DummyWriter() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            throw new java.lang.UnsupportedOperationException("Shouldn't be here");
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() throws java.io.IOException {
            close();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) throws java.io.IOException {
            close();
        }
    }

    public FastPrintWriter(java.io.OutputStream outputStream) {
        this(outputStream, false, 8192);
    }

    public FastPrintWriter(java.io.OutputStream outputStream, boolean z) {
        this(outputStream, z, 8192);
    }

    public FastPrintWriter(java.io.OutputStream outputStream, boolean z, int i) {
        super(new com.android.internal.util.FastPrintWriter.DummyWriter(), z);
        if (outputStream == null) {
            throw new java.lang.NullPointerException("out is null");
        }
        this.mBufferLen = i;
        this.mText = new char[i];
        this.mBytes = java.nio.ByteBuffer.allocate(this.mBufferLen);
        this.mOutputStream = outputStream;
        this.mWriter = null;
        this.mPrinter = null;
        this.mAutoFlush = z;
        this.mSeparator = java.lang.System.lineSeparator();
        initDefaultEncoder();
    }

    public FastPrintWriter(java.io.Writer writer) {
        this(writer, false, 8192);
    }

    public FastPrintWriter(java.io.Writer writer, boolean z) {
        this(writer, z, 8192);
    }

    public FastPrintWriter(java.io.Writer writer, boolean z, int i) {
        super(new com.android.internal.util.FastPrintWriter.DummyWriter(), z);
        if (writer == null) {
            throw new java.lang.NullPointerException("wr is null");
        }
        this.mBufferLen = i;
        this.mText = new char[i];
        this.mBytes = null;
        this.mOutputStream = null;
        this.mWriter = writer;
        this.mPrinter = null;
        this.mAutoFlush = z;
        this.mSeparator = java.lang.System.lineSeparator();
        initDefaultEncoder();
    }

    public FastPrintWriter(android.util.Printer printer) {
        this(printer, 512);
    }

    public FastPrintWriter(android.util.Printer printer, int i) {
        super((java.io.Writer) new com.android.internal.util.FastPrintWriter.DummyWriter(), true);
        if (printer == null) {
            throw new java.lang.NullPointerException("pr is null");
        }
        this.mBufferLen = i;
        this.mText = new char[i];
        this.mBytes = null;
        this.mOutputStream = null;
        this.mWriter = null;
        this.mPrinter = printer;
        this.mAutoFlush = true;
        this.mSeparator = java.lang.System.lineSeparator();
        initDefaultEncoder();
    }

    private final void initEncoder(java.lang.String str) throws java.io.UnsupportedEncodingException {
        try {
            this.mCharset = java.nio.charset.Charset.forName(str).newEncoder();
            this.mCharset.onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE);
            this.mCharset.onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE);
        } catch (java.lang.Exception e) {
            throw new java.io.UnsupportedEncodingException(str);
        }
    }

    @Override // java.io.PrintWriter
    public boolean checkError() {
        boolean z;
        flush();
        synchronized (this.lock) {
            z = this.mIoError;
        }
        return z;
    }

    @Override // java.io.PrintWriter
    protected void clearError() {
        synchronized (this.lock) {
            this.mIoError = false;
        }
    }

    @Override // java.io.PrintWriter
    protected void setError() {
        synchronized (this.lock) {
            this.mIoError = true;
        }
    }

    private final void initDefaultEncoder() {
        this.mCharset = java.nio.charset.Charset.defaultCharset().newEncoder();
        this.mCharset.onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE);
        this.mCharset.onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE);
    }

    private void appendLocked(char c) throws java.io.IOException {
        int i = this.mPos;
        if (i >= this.mBufferLen - 1) {
            flushLocked();
            i = this.mPos;
        }
        this.mText[i] = c;
        this.mPos = i + 1;
    }

    private void appendLocked(java.lang.String str, int i, int i2) throws java.io.IOException {
        int i3 = this.mBufferLen;
        if (i2 > i3) {
            int i4 = i2 + i;
            while (i < i4) {
                int i5 = i + i3;
                appendLocked(str, i, i5 < i4 ? i3 : i4 - i);
                i = i5;
            }
            return;
        }
        int i6 = this.mPos;
        if (i6 + i2 > i3) {
            flushLocked();
            i6 = this.mPos;
        }
        str.getChars(i, i + i2, this.mText, i6);
        this.mPos = i6 + i2;
    }

    private void appendLocked(char[] cArr, int i, int i2) throws java.io.IOException {
        int i3 = this.mBufferLen;
        if (i2 > i3) {
            int i4 = i2 + i;
            while (i < i4) {
                int i5 = i + i3;
                appendLocked(cArr, i, i5 < i4 ? i3 : i4 - i);
                i = i5;
            }
            return;
        }
        int i6 = this.mPos;
        if (i6 + i2 > i3) {
            flushLocked();
            i6 = this.mPos;
        }
        java.lang.System.arraycopy(cArr, i, this.mText, i6, i2);
        this.mPos = i6 + i2;
    }

    private void flushBytesLocked() throws java.io.IOException {
        int position;
        if (!this.mIoError && (position = this.mBytes.position()) > 0) {
            this.mBytes.flip();
            this.mOutputStream.write(this.mBytes.array(), 0, position);
            this.mBytes.clear();
        }
    }

    private void flushLocked() throws java.io.IOException {
        if (this.mPos > 0) {
            if (this.mOutputStream != null) {
                java.nio.CharBuffer wrap = java.nio.CharBuffer.wrap(this.mText, 0, this.mPos);
                java.nio.charset.CoderResult encode = this.mCharset.encode(wrap, this.mBytes, true);
                while (!this.mIoError) {
                    if (encode.isError()) {
                        throw new java.io.IOException(encode.toString());
                    }
                    if (!encode.isOverflow()) {
                        break;
                    }
                    flushBytesLocked();
                    encode = this.mCharset.encode(wrap, this.mBytes, true);
                }
                if (!this.mIoError) {
                    flushBytesLocked();
                    this.mOutputStream.flush();
                }
            } else if (this.mWriter != null) {
                if (!this.mIoError) {
                    this.mWriter.write(this.mText, 0, this.mPos);
                    this.mWriter.flush();
                }
            } else {
                int length = this.mSeparator.length();
                if (length >= this.mPos) {
                    length = this.mPos;
                }
                int i = 0;
                while (i < length && this.mText[(this.mPos - 1) - i] == this.mSeparator.charAt((this.mSeparator.length() - 1) - i)) {
                    i++;
                }
                if (i >= this.mPos) {
                    this.mPrinter.println("");
                } else {
                    this.mPrinter.println(new java.lang.String(this.mText, 0, this.mPos - i));
                }
            }
            this.mPos = 0;
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Flushable
    public void flush() {
        synchronized (this.lock) {
            try {
                flushLocked();
                if (!this.mIoError) {
                    if (this.mOutputStream != null) {
                        this.mOutputStream.flush();
                    } else if (this.mWriter != null) {
                        this.mWriter.flush();
                    }
                }
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            try {
                flushLocked();
                if (this.mOutputStream != null) {
                    this.mOutputStream.close();
                } else if (this.mWriter != null) {
                    this.mWriter.close();
                }
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(char[] cArr) {
        synchronized (this.lock) {
            try {
                appendLocked(cArr, 0, cArr.length);
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(char c) {
        synchronized (this.lock) {
            try {
                appendLocked(c);
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(java.lang.String str) {
        if (str == null) {
            str = java.lang.String.valueOf((java.lang.Object) null);
        }
        synchronized (this.lock) {
            try {
                appendLocked(str, 0, str.length());
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void print(int i) {
        if (i == 0) {
            print(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
        } else {
            super.print(i);
        }
    }

    @Override // java.io.PrintWriter
    public void print(long j) {
        if (j == 0) {
            print(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
        } else {
            super.print(j);
        }
    }

    @Override // java.io.PrintWriter
    public void println() {
        synchronized (this.lock) {
            try {
                appendLocked(this.mSeparator, 0, this.mSeparator.length());
                if (this.mAutoFlush) {
                    flushLocked();
                }
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter
    public void println(int i) {
        if (i == 0) {
            println(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
        } else {
            super.println(i);
        }
    }

    @Override // java.io.PrintWriter
    public void println(long j) {
        if (j == 0) {
            println(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
        } else {
            super.println(j);
        }
    }

    @Override // java.io.PrintWriter
    public void println(char[] cArr) {
        print(cArr);
        println();
    }

    @Override // java.io.PrintWriter
    public void println(char c) {
        print(c);
        println();
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        synchronized (this.lock) {
            try {
                appendLocked(cArr, i, i2);
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        synchronized (this.lock) {
            try {
                appendLocked((char) i);
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(java.lang.String str) {
        synchronized (this.lock) {
            try {
                appendLocked(str, 0, str.length());
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(java.lang.String str, int i, int i2) {
        synchronized (this.lock) {
            try {
                appendLocked(str, i, i2);
            } catch (java.io.IOException e) {
                android.util.Log.w("FastPrintWriter", "Write failure", e);
                setError();
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.lang.Appendable
    public java.io.PrintWriter append(java.lang.CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        java.lang.String charSequence2 = charSequence.subSequence(i, i2).toString();
        write(charSequence2, 0, charSequence2.length());
        return this;
    }
}
