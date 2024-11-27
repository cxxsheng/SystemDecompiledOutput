package android.util.apk;

/* loaded from: classes3.dex */
class ByteBufferDataSource implements android.util.apk.DataSource {
    private final java.nio.ByteBuffer mBuf;

    ByteBufferDataSource(java.nio.ByteBuffer byteBuffer) {
        this.mBuf = byteBuffer.slice();
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mBuf.capacity();
    }

    @Override // android.util.apk.DataSource
    public void feedIntoDataDigester(android.util.apk.DataDigester dataDigester, long j, int i) throws java.io.IOException, java.security.DigestException {
        java.nio.ByteBuffer slice;
        synchronized (this.mBuf) {
            this.mBuf.position(0);
            int i2 = (int) j;
            this.mBuf.limit(i + i2);
            this.mBuf.position(i2);
            slice = this.mBuf.slice();
        }
        dataDigester.consume(slice);
    }
}
