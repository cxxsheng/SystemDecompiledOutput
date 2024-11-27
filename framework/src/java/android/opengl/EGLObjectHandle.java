package android.opengl;

/* loaded from: classes3.dex */
public abstract class EGLObjectHandle {
    private final long mHandle;

    @java.lang.Deprecated
    protected EGLObjectHandle(int i) {
        this.mHandle = i;
    }

    protected EGLObjectHandle(long j) {
        this.mHandle = j;
    }

    @java.lang.Deprecated
    public int getHandle() {
        if ((this.mHandle & 4294967295L) != this.mHandle) {
            throw new java.lang.UnsupportedOperationException();
        }
        return (int) this.mHandle;
    }

    public long getNativeHandle() {
        return this.mHandle;
    }

    public int hashCode() {
        return 527 + ((int) (this.mHandle ^ (this.mHandle >>> 32)));
    }
}
