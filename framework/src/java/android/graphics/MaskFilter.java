package android.graphics;

/* loaded from: classes.dex */
public class MaskFilter {
    long native_instance;

    private static native void nativeDestructor(long j);

    protected void finalize() throws java.lang.Throwable {
        nativeDestructor(this.native_instance);
        this.native_instance = 0L;
    }
}
