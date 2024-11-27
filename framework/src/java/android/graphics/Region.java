package android.graphics;

/* loaded from: classes.dex */
public class Region implements android.os.Parcelable {
    private static final int MAX_POOL_SIZE = 10;
    public long mNativeRegion;
    private static final android.util.Pools.SynchronizedPool<android.graphics.Region> sPool = new android.util.Pools.SynchronizedPool<>(10);
    public static final android.os.Parcelable.Creator<android.graphics.Region> CREATOR = new android.os.Parcelable.Creator<android.graphics.Region>() { // from class: android.graphics.Region.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Region createFromParcel(android.os.Parcel parcel) {
            long nativeCreateFromParcel = android.graphics.Region.nativeCreateFromParcel(parcel);
            if (nativeCreateFromParcel == 0) {
                throw new java.lang.RuntimeException();
            }
            return new android.graphics.Region(nativeCreateFromParcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Region[] newArray(int i) {
            return new android.graphics.Region[i];
        }
    };

    private static native long nativeConstructor();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateFromParcel(android.os.Parcel parcel);

    private static native void nativeDestructor(long j);

    private static native boolean nativeEquals(long j, long j2);

    private static native boolean nativeGetBoundaryPath(long j, long j2);

    private static native boolean nativeGetBounds(long j, android.graphics.Rect rect);

    private static native boolean nativeOp(long j, int i, int i2, int i3, int i4, int i5);

    private static native boolean nativeOp(long j, long j2, long j3, int i);

    private static native boolean nativeOp(long j, android.graphics.Rect rect, long j2, int i);

    private static native boolean nativeSetPath(long j, long j2, long j3);

    private static native boolean nativeSetRect(long j, int i, int i2, int i3, int i4);

    private static native void nativeSetRegion(long j, long j2);

    private static native java.lang.String nativeToString(long j);

    private static native boolean nativeWriteToParcel(long j, android.os.Parcel parcel);

    public native boolean contains(int i, int i2);

    public native boolean isComplex();

    public native boolean isEmpty();

    public native boolean isRect();

    public native boolean quickContains(int i, int i2, int i3, int i4);

    public native boolean quickReject(int i, int i2, int i3, int i4);

    public native boolean quickReject(android.graphics.Region region);

    public native void scale(float f, android.graphics.Region region);

    public native void translate(int i, int i2, android.graphics.Region region);

    public enum Op {
        DIFFERENCE(0),
        INTERSECT(1),
        UNION(2),
        XOR(3),
        REVERSE_DIFFERENCE(4),
        REPLACE(5);

        public final int nativeInt;

        Op(int i) {
            this.nativeInt = i;
        }
    }

    public Region() {
        this(nativeConstructor());
    }

    public Region(android.graphics.Region region) {
        this(nativeConstructor());
        nativeSetRegion(this.mNativeRegion, region.mNativeRegion);
    }

    public Region(android.graphics.Rect rect) {
        this.mNativeRegion = nativeConstructor();
        nativeSetRect(this.mNativeRegion, rect.left, rect.top, rect.right, rect.bottom);
    }

    public Region(int i, int i2, int i3, int i4) {
        this.mNativeRegion = nativeConstructor();
        nativeSetRect(this.mNativeRegion, i, i2, i3, i4);
    }

    public void setEmpty() {
        nativeSetRect(this.mNativeRegion, 0, 0, 0, 0);
    }

    public boolean set(android.graphics.Region region) {
        nativeSetRegion(this.mNativeRegion, region.mNativeRegion);
        return true;
    }

    public boolean set(android.graphics.Rect rect) {
        return nativeSetRect(this.mNativeRegion, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean set(int i, int i2, int i3, int i4) {
        return nativeSetRect(this.mNativeRegion, i, i2, i3, i4);
    }

    public boolean setPath(android.graphics.Path path, android.graphics.Region region) {
        return nativeSetPath(this.mNativeRegion, path.readOnlyNI(), region.mNativeRegion);
    }

    public android.graphics.Rect getBounds() {
        android.graphics.Rect rect = new android.graphics.Rect();
        nativeGetBounds(this.mNativeRegion, rect);
        return rect;
    }

    public boolean getBounds(android.graphics.Rect rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        return nativeGetBounds(this.mNativeRegion, rect);
    }

    public android.graphics.Path getBoundaryPath() {
        android.graphics.Path path = new android.graphics.Path();
        nativeGetBoundaryPath(this.mNativeRegion, path.mutateNI());
        return path;
    }

    public boolean getBoundaryPath(android.graphics.Path path) {
        return nativeGetBoundaryPath(this.mNativeRegion, path.mutateNI());
    }

    public boolean quickContains(android.graphics.Rect rect) {
        return quickContains(rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean quickReject(android.graphics.Rect rect) {
        return quickReject(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void translate(int i, int i2) {
        translate(i, i2, null);
    }

    public void scale(float f) {
        scale(f, null);
    }

    public final boolean union(android.graphics.Rect rect) {
        return op(rect, android.graphics.Region.Op.UNION);
    }

    public boolean op(android.graphics.Rect rect, android.graphics.Region.Op op) {
        return nativeOp(this.mNativeRegion, rect.left, rect.top, rect.right, rect.bottom, op.nativeInt);
    }

    public boolean op(int i, int i2, int i3, int i4, android.graphics.Region.Op op) {
        return nativeOp(this.mNativeRegion, i, i2, i3, i4, op.nativeInt);
    }

    public boolean op(android.graphics.Region region, android.graphics.Region.Op op) {
        return op(this, region, op);
    }

    public boolean op(android.graphics.Rect rect, android.graphics.Region region, android.graphics.Region.Op op) {
        return nativeOp(this.mNativeRegion, rect, region.mNativeRegion, op.nativeInt);
    }

    public boolean op(android.graphics.Region region, android.graphics.Region region2, android.graphics.Region.Op op) {
        return nativeOp(this.mNativeRegion, region.mNativeRegion, region2.mNativeRegion, op.nativeInt);
    }

    public java.lang.String toString() {
        return nativeToString(this.mNativeRegion);
    }

    public static android.graphics.Region obtain() {
        android.graphics.Region acquire = sPool.acquire();
        return acquire != null ? acquire : new android.graphics.Region();
    }

    public static android.graphics.Region obtain(android.graphics.Region region) {
        android.graphics.Region obtain = obtain();
        obtain.set(region);
        return obtain;
    }

    public void recycle() {
        setEmpty();
        sPool.release(this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (!nativeWriteToParcel(this.mNativeRegion, parcel)) {
            throw new java.lang.RuntimeException();
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.graphics.Region)) {
            return false;
        }
        return nativeEquals(this.mNativeRegion, ((android.graphics.Region) obj).mNativeRegion);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            nativeDestructor(this.mNativeRegion);
            this.mNativeRegion = 0L;
        } finally {
            super.finalize();
        }
    }

    Region(long j) {
        if (j == 0) {
            throw new java.lang.RuntimeException();
        }
        this.mNativeRegion = j;
    }

    private Region(long j, int i) {
        this(j);
    }

    final long ni() {
        return this.mNativeRegion;
    }
}
