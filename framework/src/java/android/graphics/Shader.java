package android.graphics;

/* loaded from: classes.dex */
public class Shader {
    private java.lang.Runnable mCleaner;
    private final android.graphics.ColorSpace mColorSpace;
    private android.graphics.Matrix mLocalMatrix;
    private long mNativeInstance;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Shader.class.getClassLoader(), android.graphics.Shader.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    @java.lang.Deprecated
    public Shader() {
        this.mColorSpace = null;
    }

    protected Shader(android.graphics.ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (colorSpace == null) {
            throw new java.lang.IllegalArgumentException("Use Shader() to create a Shader with no ColorSpace");
        }
        this.mColorSpace.getNativeInstance();
    }

    protected android.graphics.ColorSpace colorSpace() {
        return this.mColorSpace;
    }

    public enum TileMode {
        CLAMP(0),
        REPEAT(1),
        MIRROR(2),
        DECAL(3);

        final int nativeInt;

        TileMode(int i) {
            this.nativeInt = i;
        }
    }

    public boolean getLocalMatrix(android.graphics.Matrix matrix) {
        if (this.mLocalMatrix != null) {
            matrix.set(this.mLocalMatrix);
            return true;
        }
        return false;
    }

    public void setLocalMatrix(android.graphics.Matrix matrix) {
        if (matrix == null || matrix.isIdentity()) {
            if (this.mLocalMatrix != null) {
                this.mLocalMatrix = null;
                discardNativeInstance();
                return;
            }
            return;
        }
        if (this.mLocalMatrix == null) {
            this.mLocalMatrix = new android.graphics.Matrix(matrix);
            discardNativeInstance();
        } else if (!this.mLocalMatrix.equals(matrix)) {
            this.mLocalMatrix.set(matrix);
            discardNativeInstance();
        }
    }

    protected long createNativeInstance(long j, boolean z) {
        return 0L;
    }

    protected final synchronized void discardNativeInstance() {
        discardNativeInstanceLocked();
    }

    private void discardNativeInstanceLocked() {
        if (this.mNativeInstance != 0) {
            this.mCleaner.run();
            this.mCleaner = null;
            this.mNativeInstance = 0L;
        }
    }

    protected boolean shouldDiscardNativeInstance(boolean z) {
        return false;
    }

    public final synchronized long getNativeInstance(boolean z) {
        if (shouldDiscardNativeInstance(z)) {
            discardNativeInstanceLocked();
        }
        if (this.mNativeInstance == 0) {
            this.mNativeInstance = createNativeInstance(this.mLocalMatrix == null ? 0L : this.mLocalMatrix.ni(), z);
            if (this.mNativeInstance != 0) {
                this.mCleaner = android.graphics.Shader.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }

    public final long getNativeInstance() {
        return getNativeInstance(false);
    }

    protected static long[] convertColors(int[] iArr) {
        if (iArr.length < 2) {
            throw new java.lang.IllegalArgumentException("needs >= 2 number of colors");
        }
        long[] jArr = new long[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            jArr[i] = android.graphics.Color.pack(iArr[i]);
        }
        return jArr;
    }

    protected static android.graphics.ColorSpace detectColorSpace(long[] jArr) {
        if (jArr.length < 2) {
            throw new java.lang.IllegalArgumentException("needs >= 2 number of colors");
        }
        android.graphics.ColorSpace colorSpace = android.graphics.Color.colorSpace(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            if (android.graphics.Color.colorSpace(jArr[i]) != colorSpace) {
                throw new java.lang.IllegalArgumentException("All colors must be in the same ColorSpace!");
            }
        }
        return colorSpace;
    }
}
