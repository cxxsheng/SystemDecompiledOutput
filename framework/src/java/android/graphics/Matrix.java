package android.graphics;

/* loaded from: classes.dex */
public class Matrix {
    public static final android.graphics.Matrix IDENTITY_MATRIX = new android.graphics.Matrix() { // from class: android.graphics.Matrix.1
        void oops() {
            throw new java.lang.IllegalStateException("Matrix can not be modified");
        }

        @Override // android.graphics.Matrix
        public void set(android.graphics.Matrix matrix) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void reset() {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setTranslate(float f, float f2) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setScale(float f, float f2, float f3, float f4) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setScale(float f, float f2) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setRotate(float f, float f2, float f3) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setRotate(float f) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setSinCos(float f, float f2, float f3, float f4) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setSinCos(float f, float f2) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setSkew(float f, float f2, float f3, float f4) {
            oops();
        }

        @Override // android.graphics.Matrix
        public void setSkew(float f, float f2) {
            oops();
        }

        @Override // android.graphics.Matrix
        public boolean setConcat(android.graphics.Matrix matrix, android.graphics.Matrix matrix2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preTranslate(float f, float f2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preScale(float f, float f2, float f3, float f4) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preScale(float f, float f2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preRotate(float f, float f2, float f3) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preRotate(float f) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preSkew(float f, float f2, float f3, float f4) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preSkew(float f, float f2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preConcat(android.graphics.Matrix matrix) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postTranslate(float f, float f2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postScale(float f, float f2, float f3, float f4) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postScale(float f, float f2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postRotate(float f, float f2, float f3) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postRotate(float f) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postSkew(float f, float f2, float f3, float f4) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postSkew(float f, float f2) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postConcat(android.graphics.Matrix matrix) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean setRectToRect(android.graphics.RectF rectF, android.graphics.RectF rectF2, android.graphics.Matrix.ScaleToFit scaleToFit) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean setPolyToPoly(float[] fArr, int i, float[] fArr2, int i2, int i3) {
            oops();
            return false;
        }

        @Override // android.graphics.Matrix
        public void setValues(float[] fArr) {
            oops();
        }
    };
    public static final int MPERSP_0 = 6;
    public static final int MPERSP_1 = 7;
    public static final int MPERSP_2 = 8;
    public static final int MSCALE_X = 0;
    public static final int MSCALE_Y = 4;
    public static final int MSKEW_X = 1;
    public static final int MSKEW_Y = 3;
    public static final int MTRANS_X = 2;
    public static final int MTRANS_Y = 5;
    private final long native_instance;

    private static native long nCreate(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nEquals(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    @dalvik.annotation.optimization.FastNative
    private static native void nGetValues(long j, float[] fArr);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nInvert(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsAffine(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsIdentity(long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nMapPoints(long j, float[] fArr, int i, float[] fArr2, int i2, int i3, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nMapRadius(long j, float f);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nMapRect(long j, android.graphics.RectF rectF, android.graphics.RectF rectF2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostConcat(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostRotate(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostRotate(long j, float f, float f2, float f3);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostScale(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostScale(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostSkew(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostSkew(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPostTranslate(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreConcat(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreRotate(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreRotate(long j, float f, float f2, float f3);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreScale(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreScale(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreSkew(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreSkew(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nPreTranslate(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nRectStaysRect(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nReset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSet(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetConcat(long j, long j2, long j3);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nSetPolyToPoly(long j, float[] fArr, int i, float[] fArr2, int i2, int i3);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nSetRectToRect(long j, android.graphics.RectF rectF, android.graphics.RectF rectF2, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetRotate(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetRotate(long j, float f, float f2, float f3);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetScale(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetScale(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetSinCos(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetSinCos(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetSkew(long j, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetSkew(long j, float f, float f2, float f3, float f4);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTranslate(long j, float f, float f2);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetValues(long j, float[] fArr);

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Matrix.class.getClassLoader(), android.graphics.Matrix.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public Matrix() {
        this.native_instance = nCreate(0L);
        android.graphics.Matrix.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.native_instance);
    }

    public Matrix(android.graphics.Matrix matrix) {
        this.native_instance = nCreate(matrix != null ? matrix.native_instance : 0L);
        android.graphics.Matrix.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.native_instance);
    }

    public boolean isIdentity() {
        return nIsIdentity(this.native_instance);
    }

    public boolean isAffine() {
        return nIsAffine(this.native_instance);
    }

    public boolean rectStaysRect() {
        return nRectStaysRect(this.native_instance);
    }

    public void set(android.graphics.Matrix matrix) {
        if (matrix == null) {
            reset();
        } else {
            nSet(this.native_instance, matrix.native_instance);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.graphics.Matrix)) {
            return false;
        }
        return nEquals(this.native_instance, ((android.graphics.Matrix) obj).native_instance);
    }

    public int hashCode() {
        return 44;
    }

    public void reset() {
        nReset(this.native_instance);
    }

    public void setTranslate(float f, float f2) {
        nSetTranslate(this.native_instance, f, f2);
    }

    public void setScale(float f, float f2, float f3, float f4) {
        nSetScale(this.native_instance, f, f2, f3, f4);
    }

    public void setScale(float f, float f2) {
        nSetScale(this.native_instance, f, f2);
    }

    public void setRotate(float f, float f2, float f3) {
        nSetRotate(this.native_instance, f, f2, f3);
    }

    public void setRotate(float f) {
        nSetRotate(this.native_instance, f);
    }

    public void setSinCos(float f, float f2, float f3, float f4) {
        nSetSinCos(this.native_instance, f, f2, f3, f4);
    }

    public void setSinCos(float f, float f2) {
        nSetSinCos(this.native_instance, f, f2);
    }

    public void setSkew(float f, float f2, float f3, float f4) {
        nSetSkew(this.native_instance, f, f2, f3, f4);
    }

    public void setSkew(float f, float f2) {
        nSetSkew(this.native_instance, f, f2);
    }

    public boolean setConcat(android.graphics.Matrix matrix, android.graphics.Matrix matrix2) {
        nSetConcat(this.native_instance, matrix.native_instance, matrix2.native_instance);
        return true;
    }

    public boolean preTranslate(float f, float f2) {
        nPreTranslate(this.native_instance, f, f2);
        return true;
    }

    public boolean preScale(float f, float f2, float f3, float f4) {
        nPreScale(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean preScale(float f, float f2) {
        nPreScale(this.native_instance, f, f2);
        return true;
    }

    public boolean preRotate(float f, float f2, float f3) {
        nPreRotate(this.native_instance, f, f2, f3);
        return true;
    }

    public boolean preRotate(float f) {
        nPreRotate(this.native_instance, f);
        return true;
    }

    public boolean preSkew(float f, float f2, float f3, float f4) {
        nPreSkew(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean preSkew(float f, float f2) {
        nPreSkew(this.native_instance, f, f2);
        return true;
    }

    public boolean preConcat(android.graphics.Matrix matrix) {
        nPreConcat(this.native_instance, matrix.native_instance);
        return true;
    }

    public boolean postTranslate(float f, float f2) {
        nPostTranslate(this.native_instance, f, f2);
        return true;
    }

    public boolean postScale(float f, float f2, float f3, float f4) {
        nPostScale(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean postScale(float f, float f2) {
        nPostScale(this.native_instance, f, f2);
        return true;
    }

    public boolean postRotate(float f, float f2, float f3) {
        nPostRotate(this.native_instance, f, f2, f3);
        return true;
    }

    public boolean postRotate(float f) {
        nPostRotate(this.native_instance, f);
        return true;
    }

    public boolean postSkew(float f, float f2, float f3, float f4) {
        nPostSkew(this.native_instance, f, f2, f3, f4);
        return true;
    }

    public boolean postSkew(float f, float f2) {
        nPostSkew(this.native_instance, f, f2);
        return true;
    }

    public boolean postConcat(android.graphics.Matrix matrix) {
        nPostConcat(this.native_instance, matrix.native_instance);
        return true;
    }

    public enum ScaleToFit {
        FILL(0),
        START(1),
        CENTER(2),
        END(3);

        final int nativeInt;

        ScaleToFit(int i) {
            this.nativeInt = i;
        }
    }

    public boolean setRectToRect(android.graphics.RectF rectF, android.graphics.RectF rectF2, android.graphics.Matrix.ScaleToFit scaleToFit) {
        if (rectF2 == null || rectF == null) {
            throw new java.lang.NullPointerException();
        }
        return nSetRectToRect(this.native_instance, rectF, rectF2, scaleToFit.nativeInt);
    }

    private static void checkPointArrays(float[] fArr, int i, float[] fArr2, int i2, int i3) {
        int i4 = i3 << 1;
        int i5 = i + i4;
        int i6 = i4 + i2;
        if ((i | i3 | i2 | i5 | i6) < 0 || i5 > fArr.length || i6 > fArr2.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
    }

    public boolean setPolyToPoly(float[] fArr, int i, float[] fArr2, int i2, int i3) {
        if (i3 > 4) {
            throw new java.lang.IllegalArgumentException();
        }
        checkPointArrays(fArr, i, fArr2, i2, i3);
        return nSetPolyToPoly(this.native_instance, fArr, i, fArr2, i2, i3);
    }

    public boolean invert(android.graphics.Matrix matrix) {
        return nInvert(this.native_instance, matrix.native_instance);
    }

    public void mapPoints(float[] fArr, int i, float[] fArr2, int i2, int i3) {
        checkPointArrays(fArr2, i2, fArr, i, i3);
        nMapPoints(this.native_instance, fArr, i, fArr2, i2, i3, true);
    }

    public void mapVectors(float[] fArr, int i, float[] fArr2, int i2, int i3) {
        checkPointArrays(fArr2, i2, fArr, i, i3);
        nMapPoints(this.native_instance, fArr, i, fArr2, i2, i3, false);
    }

    public void mapPoints(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        mapPoints(fArr, 0, fArr2, 0, fArr.length >> 1);
    }

    public void mapVectors(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        mapVectors(fArr, 0, fArr2, 0, fArr.length >> 1);
    }

    public void mapPoints(float[] fArr) {
        mapPoints(fArr, 0, fArr, 0, fArr.length >> 1);
    }

    public void mapVectors(float[] fArr) {
        mapVectors(fArr, 0, fArr, 0, fArr.length >> 1);
    }

    public boolean mapRect(android.graphics.RectF rectF, android.graphics.RectF rectF2) {
        if (rectF == null || rectF2 == null) {
            throw new java.lang.NullPointerException();
        }
        return nMapRect(this.native_instance, rectF, rectF2);
    }

    public boolean mapRect(android.graphics.RectF rectF) {
        return mapRect(rectF, rectF);
    }

    public float mapRadius(float f) {
        return nMapRadius(this.native_instance, f);
    }

    public void getValues(float[] fArr) {
        if (fArr.length < 9) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        nGetValues(this.native_instance, fArr);
    }

    public void setValues(float[] fArr) {
        if (fArr.length < 9) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        nSetValues(this.native_instance, fArr);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        sb.append("Matrix{");
        toShortString(sb);
        sb.append('}');
        return sb.toString();
    }

    public java.lang.String toShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        toShortString(sb);
        return sb.toString();
    }

    private void toShortString(java.lang.StringBuilder sb) {
        float[] fArr = new float[9];
        getValues(fArr);
        sb.append('[');
        sb.append(fArr[0]);
        sb.append(", ");
        sb.append(fArr[1]);
        sb.append(", ");
        sb.append(fArr[2]);
        sb.append("][");
        sb.append(fArr[3]);
        sb.append(", ");
        sb.append(fArr[4]);
        sb.append(", ");
        sb.append(fArr[5]);
        sb.append("][");
        sb.append(fArr[6]);
        sb.append(", ");
        sb.append(fArr[7]);
        sb.append(", ");
        sb.append(fArr[8]);
        sb.append(']');
    }

    public final void dump(java.io.PrintWriter printWriter) {
        float[] fArr = new float[9];
        getValues(fArr);
        printWriter.print('[');
        printWriter.print(fArr[0]);
        printWriter.print(", ");
        printWriter.print(fArr[1]);
        printWriter.print(", ");
        printWriter.print(fArr[2]);
        printWriter.print("][");
        printWriter.print(fArr[3]);
        printWriter.print(", ");
        printWriter.print(fArr[4]);
        printWriter.print(", ");
        printWriter.print(fArr[5]);
        printWriter.print("][");
        printWriter.print(fArr[6]);
        printWriter.print(", ");
        printWriter.print(fArr[7]);
        printWriter.print(", ");
        printWriter.print(fArr[8]);
        printWriter.print(']');
    }

    public final long ni() {
        return this.native_instance;
    }
}
