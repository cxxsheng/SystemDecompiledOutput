package android.graphics;

/* loaded from: classes.dex */
public final class Gainmap implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.graphics.Gainmap> CREATOR = new android.os.Parcelable.Creator<android.graphics.Gainmap>() { // from class: android.graphics.Gainmap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Gainmap createFromParcel(android.os.Parcel parcel) {
            android.graphics.Gainmap gainmap = new android.graphics.Gainmap((android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR));
            android.graphics.Gainmap.nReadGainmapFromParcel(gainmap.mNativePtr, parcel);
            return gainmap;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.graphics.Gainmap[] newArray(int i) {
            return new android.graphics.Gainmap[i];
        }
    };
    private android.graphics.Bitmap mGainmapContents;
    final long mNativePtr;

    private static native long nCreateCopy(long j);

    private static native long nCreateEmpty();

    private static native float nGetDisplayRatioHdr(long j);

    private static native float nGetDisplayRatioSdr(long j);

    private static native void nGetEpsilonHdr(long j, float[] fArr);

    private static native void nGetEpsilonSdr(long j, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFinalizer();

    private static native void nGetGamma(long j, float[] fArr);

    private static native void nGetRatioMax(long j, float[] fArr);

    private static native void nGetRatioMin(long j, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nReadGainmapFromParcel(long j, android.os.Parcel parcel);

    private static native void nSetBitmap(long j, android.graphics.Bitmap bitmap);

    private static native void nSetDisplayRatioHdr(long j, float f);

    private static native void nSetDisplayRatioSdr(long j, float f);

    private static native void nSetEpsilonHdr(long j, float f, float f2, float f3);

    private static native void nSetEpsilonSdr(long j, float f, float f2, float f3);

    private static native void nSetGamma(long j, float f, float f2, float f3);

    private static native void nSetRatioMax(long j, float f, float f2, float f3);

    private static native void nSetRatioMin(long j, float f, float f2, float f3);

    private static native void nWriteGainmapToParcel(long j, android.os.Parcel parcel);

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Gainmap.class.getClassLoader(), android.graphics.Gainmap.nGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    private Gainmap(android.graphics.Bitmap bitmap, long j) {
        if (j == 0) {
            throw new java.lang.RuntimeException("internal error: native gainmap is 0");
        }
        this.mNativePtr = j;
        setGainmapContents(bitmap);
        android.graphics.Gainmap.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, j);
    }

    public Gainmap(android.graphics.Bitmap bitmap) {
        this(bitmap, nCreateEmpty());
    }

    public Gainmap(android.graphics.Gainmap gainmap, android.graphics.Bitmap bitmap) {
        this(bitmap, nCreateCopy(gainmap.mNativePtr));
    }

    public android.graphics.Bitmap getGainmapContents() {
        return this.mGainmapContents;
    }

    public void setGainmapContents(android.graphics.Bitmap bitmap) {
        if (bitmap.isRecycled()) {
            throw new java.lang.IllegalArgumentException("Bitmap is recycled");
        }
        nSetBitmap(this.mNativePtr, bitmap);
        this.mGainmapContents = bitmap;
    }

    public void setRatioMin(float f, float f2, float f3) {
        nSetRatioMin(this.mNativePtr, f, f2, f3);
    }

    public float[] getRatioMin() {
        float[] fArr = new float[3];
        nGetRatioMin(this.mNativePtr, fArr);
        return fArr;
    }

    public void setRatioMax(float f, float f2, float f3) {
        nSetRatioMax(this.mNativePtr, f, f2, f3);
    }

    public float[] getRatioMax() {
        float[] fArr = new float[3];
        nGetRatioMax(this.mNativePtr, fArr);
        return fArr;
    }

    public void setGamma(float f, float f2, float f3) {
        nSetGamma(this.mNativePtr, f, f2, f3);
    }

    public float[] getGamma() {
        float[] fArr = new float[3];
        nGetGamma(this.mNativePtr, fArr);
        return fArr;
    }

    public void setEpsilonSdr(float f, float f2, float f3) {
        nSetEpsilonSdr(this.mNativePtr, f, f2, f3);
    }

    public float[] getEpsilonSdr() {
        float[] fArr = new float[3];
        nGetEpsilonSdr(this.mNativePtr, fArr);
        return fArr;
    }

    public void setEpsilonHdr(float f, float f2, float f3) {
        nSetEpsilonHdr(this.mNativePtr, f, f2, f3);
    }

    public float[] getEpsilonHdr() {
        float[] fArr = new float[3];
        nGetEpsilonHdr(this.mNativePtr, fArr);
        return fArr;
    }

    public void setDisplayRatioForFullHdr(float f) {
        if (!java.lang.Float.isFinite(f) || f < 1.0f) {
            throw new java.lang.IllegalArgumentException("setDisplayRatioForFullHdr must be >= 1.0f, got = " + f);
        }
        nSetDisplayRatioHdr(this.mNativePtr, f);
    }

    public float getDisplayRatioForFullHdr() {
        return nGetDisplayRatioHdr(this.mNativePtr);
    }

    public void setMinDisplayRatioForHdrTransition(float f) {
        if (!java.lang.Float.isFinite(f) || f < 1.0f) {
            throw new java.lang.IllegalArgumentException("setMinDisplayRatioForHdrTransition must be >= 1.0f, got = " + f);
        }
        nSetDisplayRatioSdr(this.mNativePtr, f);
    }

    public float getMinDisplayRatioForHdrTransition() {
        return nGetDisplayRatioSdr(this.mNativePtr);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("Cannot be written to a parcel");
        }
        parcel.writeTypedObject(this.mGainmapContents, i);
        nWriteGainmapToParcel(this.mNativePtr, parcel);
    }
}
