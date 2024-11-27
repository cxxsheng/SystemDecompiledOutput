package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class HidlMemory implements java.io.Closeable {
    private android.os.NativeHandle mHandle;
    private final java.lang.String mName;
    private long mNativeContext;
    private final long mSize;

    private native void nativeFinalize();

    public HidlMemory(java.lang.String str, long j, android.os.NativeHandle nativeHandle) {
        this.mName = str;
        this.mSize = j;
        this.mHandle = nativeHandle;
    }

    public android.os.HidlMemory dup() throws java.io.IOException {
        return new android.os.HidlMemory(this.mName, this.mSize, this.mHandle != null ? this.mHandle.dup() : null);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        if (this.mHandle != null) {
            this.mHandle.close();
            this.mHandle = null;
        }
    }

    public android.os.NativeHandle releaseHandle() {
        android.os.NativeHandle nativeHandle = this.mHandle;
        this.mHandle = null;
        return nativeHandle;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public long getSize() {
        return this.mSize;
    }

    public android.os.NativeHandle getHandle() {
        return this.mHandle;
    }

    protected void finalize() {
        try {
            try {
                close();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        } finally {
            nativeFinalize();
        }
    }
}
