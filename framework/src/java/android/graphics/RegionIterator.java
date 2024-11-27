package android.graphics;

/* loaded from: classes.dex */
public class RegionIterator {
    private long mNativeIter;

    private static native long nativeConstructor(long j);

    private static native void nativeDestructor(long j);

    private static native boolean nativeNext(long j, android.graphics.Rect rect);

    public RegionIterator(android.graphics.Region region) {
        this.mNativeIter = nativeConstructor(region.ni());
    }

    public final boolean next(android.graphics.Rect rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("The Rect must be provided");
        }
        return nativeNext(this.mNativeIter, rect);
    }

    protected void finalize() throws java.lang.Throwable {
        nativeDestructor(this.mNativeIter);
        this.mNativeIter = 0L;
    }
}
