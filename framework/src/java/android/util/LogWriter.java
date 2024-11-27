package android.util;

/* loaded from: classes3.dex */
public class LogWriter extends java.io.Writer {
    private final int mBuffer;
    private java.lang.StringBuilder mBuilder;
    private final int mPriority;
    private final java.lang.String mTag;

    public LogWriter(int i, java.lang.String str) {
        this.mBuilder = new java.lang.StringBuilder(128);
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = 0;
    }

    public LogWriter(int i, java.lang.String str, int i2) {
        this.mBuilder = new java.lang.StringBuilder(128);
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = i2;
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        flushBuilder();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append(c);
            }
        }
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            android.util.Log.println_native(this.mBuffer, this.mPriority, this.mTag, this.mBuilder.toString());
            this.mBuilder.delete(0, this.mBuilder.length());
        }
    }
}
