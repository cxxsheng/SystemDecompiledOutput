package android.util;

/* loaded from: classes3.dex */
public class TeeWriter extends java.io.Writer {
    private final java.io.Writer[] mWriters;

    public TeeWriter(java.io.Writer... writerArr) {
        for (java.io.Writer writer : writerArr) {
            java.util.Objects.requireNonNull(writer);
        }
        this.mWriters = writerArr;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws java.io.IOException {
        for (java.io.Writer writer : this.mWriters) {
            writer.write(cArr, i, i2);
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws java.io.IOException {
        for (java.io.Writer writer : this.mWriters) {
            writer.flush();
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        for (java.io.Writer writer : this.mWriters) {
            writer.close();
        }
    }
}
