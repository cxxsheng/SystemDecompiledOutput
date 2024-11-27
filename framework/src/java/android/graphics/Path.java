package android.graphics;

/* loaded from: classes.dex */
public class Path {
    public final long mNativePath;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Path.class.getClassLoader(), nGetFinalizer());
    static final android.graphics.Path.FillType[] sFillTypeArray = {android.graphics.Path.FillType.WINDING, android.graphics.Path.FillType.EVEN_ODD, android.graphics.Path.FillType.INVERSE_WINDING, android.graphics.Path.FillType.INVERSE_EVEN_ODD};

    public enum Op {
        DIFFERENCE,
        INTERSECT,
        UNION,
        XOR,
        REVERSE_DIFFERENCE
    }

    private static native void nAddArc(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nAddCircle(long j, float f, float f2, float f3, int i);

    private static native void nAddOval(long j, float f, float f2, float f3, float f4, int i);

    private static native void nAddPath(long j, long j2);

    private static native void nAddPath(long j, long j2, float f, float f2);

    private static native void nAddPath(long j, long j2, long j3);

    private static native void nAddRect(long j, float f, float f2, float f3, float f4, int i);

    private static native void nAddRoundRect(long j, float f, float f2, float f3, float f4, float f5, float f6, int i);

    private static native void nAddRoundRect(long j, float f, float f2, float f3, float f4, float[] fArr, int i);

    private static native float[] nApproximate(long j, float f);

    private static native void nArcTo(long j, float f, float f2, float f3, float f4, float f5, float f6, boolean z);

    private static native void nClose(long j);

    private static native void nComputeBounds(long j, android.graphics.RectF rectF);

    private static native void nConicTo(long j, float f, float f2, float f3, float f4, float f5);

    private static native void nCubicTo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetFillType(long j);

    private static native long nGetFinalizer();

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetGenerationID(long j);

    private static native void nIncReserve(long j, int i);

    private static native long nInit();

    private static native long nInit(long j);

    private static native boolean nInterpolate(long j, long j2, float f, long j3);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsConvex(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsEmpty(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsInterpolatable(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nIsRect(long j, android.graphics.RectF rectF);

    private static native void nLineTo(long j, float f, float f2);

    private static native void nMoveTo(long j, float f, float f2);

    private static native void nOffset(long j, float f, float f2);

    private static native boolean nOp(long j, long j2, int i, long j3);

    private static native void nQuadTo(long j, float f, float f2, float f3, float f4);

    private static native void nRConicTo(long j, float f, float f2, float f3, float f4, float f5);

    private static native void nRCubicTo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nRLineTo(long j, float f, float f2);

    private static native void nRMoveTo(long j, float f, float f2);

    private static native void nRQuadTo(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nReset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nRewind(long j);

    private static native void nSet(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetFillType(long j, int i);

    private static native void nSetLastPoint(long j, float f, float f2);

    private static native void nTransform(long j, long j2);

    private static native void nTransform(long j, long j2, long j3);

    public Path() {
        this.mNativePath = nInit();
        sRegistry.registerNativeAllocation(this, this.mNativePath);
    }

    public Path(android.graphics.Path path) {
        this.mNativePath = nInit(path != null ? path.mNativePath : 0L);
        sRegistry.registerNativeAllocation(this, this.mNativePath);
    }

    public void reset() {
        android.graphics.Path.FillType fillType = getFillType();
        nReset(this.mNativePath);
        setFillType(fillType);
    }

    public void rewind() {
        nRewind(this.mNativePath);
    }

    public void set(android.graphics.Path path) {
        if (this == path) {
            return;
        }
        nSet(this.mNativePath, path.mNativePath);
    }

    public android.graphics.PathIterator getPathIterator() {
        return new android.graphics.PathIterator(this);
    }

    public boolean op(android.graphics.Path path, android.graphics.Path.Op op) {
        return op(this, path, op);
    }

    public boolean op(android.graphics.Path path, android.graphics.Path path2, android.graphics.Path.Op op) {
        return nOp(path.mNativePath, path2.mNativePath, op.ordinal(), this.mNativePath);
    }

    @java.lang.Deprecated
    public boolean isConvex() {
        return nIsConvex(this.mNativePath);
    }

    public enum FillType {
        WINDING(0),
        EVEN_ODD(1),
        INVERSE_WINDING(2),
        INVERSE_EVEN_ODD(3);

        final int nativeInt;

        FillType(int i) {
            this.nativeInt = i;
        }
    }

    public android.graphics.Path.FillType getFillType() {
        return sFillTypeArray[nGetFillType(this.mNativePath)];
    }

    public void setFillType(android.graphics.Path.FillType fillType) {
        nSetFillType(this.mNativePath, fillType.nativeInt);
    }

    public boolean isInverseFillType() {
        return (nGetFillType(this.mNativePath) & android.graphics.Path.FillType.INVERSE_WINDING.nativeInt) != 0;
    }

    public void toggleInverseFillType() {
        nSetFillType(this.mNativePath, nGetFillType(this.mNativePath) ^ android.graphics.Path.FillType.INVERSE_WINDING.nativeInt);
    }

    public boolean isEmpty() {
        return nIsEmpty(this.mNativePath);
    }

    public boolean isRect(android.graphics.RectF rectF) {
        return nIsRect(this.mNativePath, rectF);
    }

    @java.lang.Deprecated
    public void computeBounds(android.graphics.RectF rectF, boolean z) {
        computeBounds(rectF);
    }

    public void computeBounds(android.graphics.RectF rectF) {
        nComputeBounds(this.mNativePath, rectF);
    }

    public void incReserve(int i) {
        nIncReserve(this.mNativePath, i);
    }

    public void moveTo(float f, float f2) {
        nMoveTo(this.mNativePath, f, f2);
    }

    public void rMoveTo(float f, float f2) {
        nRMoveTo(this.mNativePath, f, f2);
    }

    public void lineTo(float f, float f2) {
        nLineTo(this.mNativePath, f, f2);
    }

    public void rLineTo(float f, float f2) {
        nRLineTo(this.mNativePath, f, f2);
    }

    public void quadTo(float f, float f2, float f3, float f4) {
        nQuadTo(this.mNativePath, f, f2, f3, f4);
    }

    public void rQuadTo(float f, float f2, float f3, float f4) {
        nRQuadTo(this.mNativePath, f, f2, f3, f4);
    }

    public void conicTo(float f, float f2, float f3, float f4, float f5) {
        nConicTo(this.mNativePath, f, f2, f3, f4, f5);
    }

    public void rConicTo(float f, float f2, float f3, float f4, float f5) {
        nRConicTo(this.mNativePath, f, f2, f3, f4, f5);
    }

    public void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        nCubicTo(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void rCubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        nRCubicTo(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void arcTo(android.graphics.RectF rectF, float f, float f2, boolean z) {
        arcTo(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, z);
    }

    public void arcTo(android.graphics.RectF rectF, float f, float f2) {
        arcTo(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, false);
    }

    public void arcTo(float f, float f2, float f3, float f4, float f5, float f6, boolean z) {
        nArcTo(this.mNativePath, f, f2, f3, f4, f5, f6, z);
    }

    public void close() {
        nClose(this.mNativePath);
    }

    public enum Direction {
        CW(0),
        CCW(1);

        final int nativeInt;

        Direction(int i) {
            this.nativeInt = i;
        }
    }

    public void addRect(android.graphics.RectF rectF, android.graphics.Path.Direction direction) {
        addRect(rectF.left, rectF.top, rectF.right, rectF.bottom, direction);
    }

    public void addRect(float f, float f2, float f3, float f4, android.graphics.Path.Direction direction) {
        nAddRect(this.mNativePath, f, f2, f3, f4, direction.nativeInt);
    }

    public void addOval(android.graphics.RectF rectF, android.graphics.Path.Direction direction) {
        addOval(rectF.left, rectF.top, rectF.right, rectF.bottom, direction);
    }

    public void addOval(float f, float f2, float f3, float f4, android.graphics.Path.Direction direction) {
        nAddOval(this.mNativePath, f, f2, f3, f4, direction.nativeInt);
    }

    public void addCircle(float f, float f2, float f3, android.graphics.Path.Direction direction) {
        nAddCircle(this.mNativePath, f, f2, f3, direction.nativeInt);
    }

    public void addArc(android.graphics.RectF rectF, float f, float f2) {
        addArc(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2);
    }

    public void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        nAddArc(this.mNativePath, f, f2, f3, f4, f5, f6);
    }

    public void addRoundRect(android.graphics.RectF rectF, float f, float f2, android.graphics.Path.Direction direction) {
        addRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, f, f2, direction);
    }

    public void addRoundRect(float f, float f2, float f3, float f4, float f5, float f6, android.graphics.Path.Direction direction) {
        nAddRoundRect(this.mNativePath, f, f2, f3, f4, f5, f6, direction.nativeInt);
    }

    public void addRoundRect(android.graphics.RectF rectF, float[] fArr, android.graphics.Path.Direction direction) {
        if (rectF == null) {
            throw new java.lang.NullPointerException("need rect parameter");
        }
        addRoundRect(rectF.left, rectF.top, rectF.right, rectF.bottom, fArr, direction);
    }

    public void addRoundRect(float f, float f2, float f3, float f4, float[] fArr, android.graphics.Path.Direction direction) {
        if (fArr.length < 8) {
            throw new java.lang.ArrayIndexOutOfBoundsException("radii[] needs 8 values");
        }
        nAddRoundRect(this.mNativePath, f, f2, f3, f4, fArr, direction.nativeInt);
    }

    public void addPath(android.graphics.Path path, float f, float f2) {
        nAddPath(this.mNativePath, path.mNativePath, f, f2);
    }

    public void addPath(android.graphics.Path path) {
        nAddPath(this.mNativePath, path.mNativePath);
    }

    public void addPath(android.graphics.Path path, android.graphics.Matrix matrix) {
        nAddPath(this.mNativePath, path.mNativePath, matrix.ni());
    }

    public void offset(float f, float f2, android.graphics.Path path) {
        if (path != null) {
            path.set(this);
        } else {
            path = this;
        }
        path.offset(f, f2);
    }

    public void offset(float f, float f2) {
        nOffset(this.mNativePath, f, f2);
    }

    public void setLastPoint(float f, float f2) {
        nSetLastPoint(this.mNativePath, f, f2);
    }

    public void transform(android.graphics.Matrix matrix, android.graphics.Path path) {
        nTransform(this.mNativePath, matrix.ni(), path != null ? path.mNativePath : 0L);
    }

    public void transform(android.graphics.Matrix matrix) {
        nTransform(this.mNativePath, matrix.ni());
    }

    public final long readOnlyNI() {
        return this.mNativePath;
    }

    final long mutateNI() {
        return this.mNativePath;
    }

    public float[] approximate(float f) {
        if (f < 0.0f) {
            throw new java.lang.IllegalArgumentException("AcceptableError must be greater than or equal to 0");
        }
        return nApproximate(this.mNativePath, f);
    }

    public int getGenerationId() {
        return nGetGenerationID(this.mNativePath);
    }

    public boolean isInterpolatable(android.graphics.Path path) {
        return nIsInterpolatable(this.mNativePath, path.mNativePath);
    }

    public boolean interpolate(android.graphics.Path path, float f, android.graphics.Path path2) {
        return nInterpolate(this.mNativePath, path.mNativePath, f, path2.mNativePath);
    }
}
