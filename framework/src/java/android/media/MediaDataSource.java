package android.media;

/* loaded from: classes2.dex */
public abstract class MediaDataSource implements java.io.Closeable {
    public abstract long getSize() throws java.io.IOException;

    public abstract int readAt(long j, byte[] bArr, int i, int i2) throws java.io.IOException;
}
