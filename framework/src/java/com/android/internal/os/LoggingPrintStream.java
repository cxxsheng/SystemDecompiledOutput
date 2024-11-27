package com.android.internal.os;

/* loaded from: classes4.dex */
public abstract class LoggingPrintStream extends java.io.PrintStream {
    private final java.lang.StringBuilder builder;
    private java.nio.CharBuffer decodedChars;
    private java.nio.charset.CharsetDecoder decoder;
    private java.nio.ByteBuffer encodedBytes;
    private final java.util.Formatter formatter;

    protected abstract void log(java.lang.String str);

    protected LoggingPrintStream() {
        super(new java.io.OutputStream() { // from class: com.android.internal.os.LoggingPrintStream.1
            @Override // java.io.OutputStream
            public void write(int i) throws java.io.IOException {
                throw new java.lang.AssertionError();
            }
        });
        this.builder = new java.lang.StringBuilder();
        this.formatter = new java.util.Formatter(this.builder, (java.util.Locale) null);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public synchronized void flush() {
        flush(true);
    }

    private void flush(boolean z) {
        int length = this.builder.length();
        int i = 0;
        while (i < length) {
            int indexOf = this.builder.indexOf("\n", i);
            if (indexOf == -1) {
                break;
            }
            log(this.builder.substring(i, indexOf));
            i = indexOf + 1;
        }
        if (!z) {
            this.builder.delete(0, i);
            return;
        }
        if (i < length) {
            log(this.builder.substring(i));
        }
        this.builder.setLength(0);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) {
        write(new byte[]{(byte) i}, 0, 1);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public synchronized void write(byte[] bArr, int i, int i2) {
        java.nio.charset.CoderResult decode;
        if (this.decoder == null) {
            this.encodedBytes = java.nio.ByteBuffer.allocate(80);
            this.decodedChars = java.nio.CharBuffer.allocate(80);
            this.decoder = java.nio.charset.Charset.defaultCharset().newDecoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE).onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE);
        }
        int i3 = i2 + i;
        while (i < i3) {
            int min = java.lang.Math.min(this.encodedBytes.remaining(), i3 - i);
            this.encodedBytes.put(bArr, i, min);
            i += min;
            this.encodedBytes.flip();
            do {
                decode = this.decoder.decode(this.encodedBytes, this.decodedChars, false);
                this.decodedChars.flip();
                this.builder.append((java.lang.CharSequence) this.decodedChars);
                this.decodedChars.clear();
            } while (decode.isOverflow());
            this.encodedBytes.compact();
        }
        flush(false);
    }

    @Override // java.io.PrintStream
    public boolean checkError() {
        return false;
    }

    @Override // java.io.PrintStream
    protected void setError() {
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.PrintStream
    public java.io.PrintStream format(java.lang.String str, java.lang.Object... objArr) {
        return format(java.util.Locale.getDefault(), str, objArr);
    }

    @Override // java.io.PrintStream
    public java.io.PrintStream printf(java.lang.String str, java.lang.Object... objArr) {
        return format(str, objArr);
    }

    @Override // java.io.PrintStream
    public java.io.PrintStream printf(java.util.Locale locale, java.lang.String str, java.lang.Object... objArr) {
        return format(locale, str, objArr);
    }

    @Override // java.io.PrintStream
    public synchronized java.io.PrintStream format(java.util.Locale locale, java.lang.String str, java.lang.Object... objArr) {
        if (str == null) {
            throw new java.lang.NullPointerException(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT);
        }
        this.formatter.format(locale, str, objArr);
        flush(false);
        return this;
    }

    @Override // java.io.PrintStream
    public synchronized void print(char[] cArr) {
        this.builder.append(cArr);
        flush(false);
    }

    @Override // java.io.PrintStream
    public synchronized void print(char c) {
        this.builder.append(c);
        if (c == '\n') {
            flush(false);
        }
    }

    @Override // java.io.PrintStream
    public synchronized void print(double d) {
        this.builder.append(d);
    }

    @Override // java.io.PrintStream
    public synchronized void print(float f) {
        this.builder.append(f);
    }

    @Override // java.io.PrintStream
    public synchronized void print(int i) {
        this.builder.append(i);
    }

    @Override // java.io.PrintStream
    public synchronized void print(long j) {
        this.builder.append(j);
    }

    @Override // java.io.PrintStream
    public synchronized void print(java.lang.Object obj) {
        this.builder.append(obj);
        flush(false);
    }

    @Override // java.io.PrintStream
    public synchronized void print(java.lang.String str) {
        this.builder.append(str);
        flush(false);
    }

    @Override // java.io.PrintStream
    public synchronized void print(boolean z) {
        this.builder.append(z);
    }

    @Override // java.io.PrintStream
    public synchronized void println() {
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(char[] cArr) {
        this.builder.append(cArr);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(char c) {
        this.builder.append(c);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(double d) {
        this.builder.append(d);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(float f) {
        this.builder.append(f);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(int i) {
        this.builder.append(i);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(long j) {
        this.builder.append(j);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(java.lang.Object obj) {
        this.builder.append(obj);
        flush(true);
    }

    @Override // java.io.PrintStream
    public synchronized void println(java.lang.String str) {
        if (this.builder.length() == 0 && str != null) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int indexOf = str.indexOf(10, i);
                if (indexOf == -1) {
                    break;
                }
                log(str.substring(i, indexOf));
                i = indexOf + 1;
            }
            if (i < length) {
                log(str.substring(i));
            }
        } else {
            this.builder.append(str);
            flush(true);
        }
    }

    @Override // java.io.PrintStream
    public synchronized void println(boolean z) {
        this.builder.append(z);
        flush(true);
    }

    @Override // java.io.PrintStream, java.lang.Appendable
    public synchronized java.io.PrintStream append(char c) {
        print(c);
        return this;
    }

    @Override // java.io.PrintStream, java.lang.Appendable
    public synchronized java.io.PrintStream append(java.lang.CharSequence charSequence) {
        this.builder.append(charSequence);
        flush(false);
        return this;
    }

    @Override // java.io.PrintStream, java.lang.Appendable
    public synchronized java.io.PrintStream append(java.lang.CharSequence charSequence, int i, int i2) {
        this.builder.append(charSequence, i, i2);
        flush(false);
        return this;
    }
}
