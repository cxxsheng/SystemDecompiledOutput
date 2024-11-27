package android.graphics;

/* loaded from: classes.dex */
public class PathIterator implements java.util.Iterator<android.graphics.PathIterator.Segment> {
    private static final int POINT_ARRAY_SIZE = 8;
    public static final int VERB_CLOSE = 5;
    public static final int VERB_CONIC = 3;
    public static final int VERB_CUBIC = 4;
    public static final int VERB_DONE = 6;
    public static final int VERB_LINE = 1;
    public static final int VERB_MOVE = 0;
    public static final int VERB_QUAD = 2;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.PathIterator.class.getClassLoader(), nGetFinalizer());
    private int mCachedVerb = -1;
    private boolean mDone = false;
    private final long mNativeIterator;
    private final android.graphics.Path mPath;
    private final int mPathGenerationId;
    private final long mPointsAddress;
    private final float[] mPointsArray;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Verb {
    }

    private static native long nCreate(long j);

    private static native long nGetFinalizer();

    @dalvik.annotation.optimization.CriticalNative
    private static native int nNext(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nPeek(long j);

    PathIterator(android.graphics.Path path) {
        this.mPath = path;
        this.mNativeIterator = nCreate(this.mPath.mNativePath);
        this.mPathGenerationId = this.mPath.getGenerationId();
        dalvik.system.VMRuntime runtime = dalvik.system.VMRuntime.getRuntime();
        this.mPointsArray = (float[]) runtime.newNonMovableArray(java.lang.Float.TYPE, 8);
        this.mPointsAddress = runtime.addressOf(this.mPointsArray);
        sRegistry.registerNativeAllocation(this, this.mNativeIterator);
    }

    public int next(float[] fArr, int i) {
        if (fArr.length < i + 8) {
            throw new java.lang.ArrayIndexOutOfBoundsException("points array must be able to hold at least 8 entries");
        }
        int returnVerb = getReturnVerb(this.mCachedVerb);
        this.mCachedVerb = -1;
        java.lang.System.arraycopy(this.mPointsArray, 0, fArr, i, 8);
        return returnVerb;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.mCachedVerb == -1) {
            this.mCachedVerb = nextInternal();
        }
        return this.mCachedVerb != 6;
    }

    public int peek() {
        if (this.mPathGenerationId != this.mPath.getGenerationId()) {
            throw new java.util.ConcurrentModificationException("Iterator cannot be used on modified Path");
        }
        if (this.mDone) {
            return 6;
        }
        return nPeek(this.mNativeIterator);
    }

    private int nextInternal() {
        if (this.mDone) {
            return 6;
        }
        if (this.mPathGenerationId != this.mPath.getGenerationId()) {
            throw new java.util.ConcurrentModificationException("Iterator cannot be used on modified Path");
        }
        int nNext = nNext(this.mNativeIterator, this.mPointsAddress);
        if (nNext == 6) {
            this.mDone = true;
        }
        return nNext;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public android.graphics.PathIterator.Segment next() {
        float f;
        int returnVerb = getReturnVerb(this.mCachedVerb);
        this.mCachedVerb = -1;
        if (returnVerb != 3) {
            f = 0.0f;
        } else {
            f = this.mPointsArray[6];
        }
        float[] fArr = new float[8];
        java.lang.System.arraycopy(this.mPointsArray, 0, fArr, 0, 8);
        return new android.graphics.PathIterator.Segment(returnVerb, fArr, f);
    }

    private int getReturnVerb(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return nextInternal();
        }
    }

    public static class Segment {
        private final float mConicWeight;
        private final float[] mPoints;
        private final int mVerb;

        public int getVerb() {
            return this.mVerb;
        }

        public float[] getPoints() {
            return this.mPoints;
        }

        public float getConicWeight() {
            return this.mConicWeight;
        }

        Segment(int i, float[] fArr, float f) {
            this.mVerb = i;
            this.mPoints = fArr;
            this.mConicWeight = f;
        }
    }
}
