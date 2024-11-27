package android.graphics;

/* loaded from: classes.dex */
public class PathMeasure {
    public static final int POSITION_MATRIX_FLAG = 1;
    public static final int TANGENT_MATRIX_FLAG = 2;
    private android.graphics.Path mPath;
    private long native_instance;

    private static native long native_create(long j, boolean z);

    private static native void native_destroy(long j);

    private static native float native_getLength(long j);

    private static native boolean native_getMatrix(long j, float f, long j2, int i);

    private static native boolean native_getPosTan(long j, float f, float[] fArr, float[] fArr2);

    private static native boolean native_getSegment(long j, float f, float f2, long j2, boolean z);

    private static native boolean native_isClosed(long j);

    private static native boolean native_nextContour(long j);

    private static native void native_setPath(long j, long j2, boolean z);

    public PathMeasure() {
        this.mPath = null;
        this.native_instance = native_create(0L, false);
    }

    public PathMeasure(android.graphics.Path path, boolean z) {
        this.mPath = path;
        this.native_instance = native_create(path != null ? path.readOnlyNI() : 0L, z);
    }

    public void setPath(android.graphics.Path path, boolean z) {
        this.mPath = path;
        native_setPath(this.native_instance, path != null ? path.readOnlyNI() : 0L, z);
    }

    public float getLength() {
        return native_getLength(this.native_instance);
    }

    public boolean getPosTan(float f, float[] fArr, float[] fArr2) {
        if ((fArr != null && fArr.length < 2) || (fArr2 != null && fArr2.length < 2)) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        return native_getPosTan(this.native_instance, f, fArr, fArr2);
    }

    public boolean getMatrix(float f, android.graphics.Matrix matrix, int i) {
        return native_getMatrix(this.native_instance, f, matrix.ni(), i);
    }

    public boolean getSegment(float f, float f2, android.graphics.Path path, boolean z) {
        float f3;
        float f4;
        float length = getLength();
        if (f >= 0.0f) {
            f3 = f;
        } else {
            f3 = 0.0f;
        }
        if (f2 <= length) {
            f4 = f2;
        } else {
            f4 = length;
        }
        if (f3 >= f4) {
            return false;
        }
        return native_getSegment(this.native_instance, f3, f4, path.mutateNI(), z);
    }

    public boolean isClosed() {
        return native_isClosed(this.native_instance);
    }

    public boolean nextContour() {
        return native_nextContour(this.native_instance);
    }

    protected void finalize() throws java.lang.Throwable {
        native_destroy(this.native_instance);
        this.native_instance = 0L;
    }
}
