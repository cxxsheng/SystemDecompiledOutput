package android.graphics;

/* loaded from: classes.dex */
public class Mesh {
    public static final int TRIANGLES = 0;
    public static final int TRIANGLE_STRIP = 1;
    private boolean mIsIndexed;
    private long mNativeMeshWrapper;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface Mode {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native long nativeMake(long j, int i, java.nio.Buffer buffer, boolean z, int i2, int i3, float f, float f2, float f3, float f4);

    private static native long nativeMakeIndexed(long j, int i, java.nio.Buffer buffer, boolean z, int i2, int i3, java.nio.ShortBuffer shortBuffer, boolean z2, int i4, int i5, float f, float f2, float f3, float f4);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, float f, float f2, float f3, float f4, int i);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, int i, int i2, int i3, int i4, int i5);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, float[] fArr, boolean z);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, int[] iArr);

    private static class MeshHolder {
        public static final libcore.util.NativeAllocationRegistry MESH_SPECIFICATION_REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.MeshSpecification.class.getClassLoader(), android.graphics.Mesh.nativeGetFinalizer());

        private MeshHolder() {
        }
    }

    public Mesh(android.graphics.MeshSpecification meshSpecification, int i, java.nio.Buffer buffer, int i2, android.graphics.RectF rectF) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid value passed in for mode parameter");
        }
        long nativeMake = nativeMake(meshSpecification.mNativeMeshSpec, i, buffer, buffer.isDirect(), i2, buffer.position(), rectF.left, rectF.top, rectF.right, rectF.bottom);
        if (nativeMake == 0) {
            throw new java.lang.IllegalArgumentException("Mesh construction failed.");
        }
        meshSetup(nativeMake, false);
    }

    public Mesh(android.graphics.MeshSpecification meshSpecification, int i, java.nio.Buffer buffer, int i2, java.nio.ShortBuffer shortBuffer, android.graphics.RectF rectF) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid value passed in for mode parameter");
        }
        long nativeMakeIndexed = nativeMakeIndexed(meshSpecification.mNativeMeshSpec, i, buffer, buffer.isDirect(), i2, buffer.position(), shortBuffer, shortBuffer.isDirect(), shortBuffer.capacity(), shortBuffer.position(), rectF.left, rectF.top, rectF.right, rectF.bottom);
        if (nativeMakeIndexed == 0) {
            throw new java.lang.IllegalArgumentException("Mesh construction failed.");
        }
        meshSetup(nativeMakeIndexed, true);
    }

    public void setColorUniform(java.lang.String str, int i) {
        setUniform(str, android.graphics.Color.valueOf(i).getComponents(), true);
    }

    public void setColorUniform(java.lang.String str, long j) {
        setUniform(str, android.graphics.Color.valueOf(j).convert(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.EXTENDED_SRGB)).getComponents(), true);
    }

    public void setColorUniform(java.lang.String str, android.graphics.Color color) {
        if (color == null) {
            throw new java.lang.NullPointerException("The color parameter must not be null");
        }
        setUniform(str, color.convert(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.EXTENDED_SRGB)).getComponents(), true);
    }

    public void setFloatUniform(java.lang.String str, float f) {
        setFloatUniform(str, f, 0.0f, 0.0f, 0.0f, 1);
    }

    public void setFloatUniform(java.lang.String str, float f, float f2) {
        setFloatUniform(str, f, f2, 0.0f, 0.0f, 2);
    }

    public void setFloatUniform(java.lang.String str, float f, float f2, float f3) {
        setFloatUniform(str, f, f2, f3, 0.0f, 3);
    }

    public void setFloatUniform(java.lang.String str, float f, float f2, float f3, float f4) {
        setFloatUniform(str, f, f2, f3, f4, 4);
    }

    public void setFloatUniform(java.lang.String str, float[] fArr) {
        setUniform(str, fArr, false);
    }

    private void setFloatUniform(java.lang.String str, float f, float f2, float f3, float f4, int i) {
        if (str == null) {
            throw new java.lang.NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, f, f2, f3, f4, i);
    }

    private void setUniform(java.lang.String str, float[] fArr, boolean z) {
        if (str == null) {
            throw new java.lang.NullPointerException("The uniformName parameter must not be null");
        }
        if (fArr == null) {
            throw new java.lang.NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, fArr, z);
    }

    public void setIntUniform(java.lang.String str, int i) {
        setIntUniform(str, i, 0, 0, 0, 1);
    }

    public void setIntUniform(java.lang.String str, int i, int i2) {
        setIntUniform(str, i, i2, 0, 0, 2);
    }

    public void setIntUniform(java.lang.String str, int i, int i2, int i3) {
        setIntUniform(str, i, i2, i3, 0, 3);
    }

    public void setIntUniform(java.lang.String str, int i, int i2, int i3, int i4) {
        setIntUniform(str, i, i2, i3, i4, 4);
    }

    public void setIntUniform(java.lang.String str, int[] iArr) {
        if (str == null) {
            throw new java.lang.NullPointerException("The uniformName parameter must not be null");
        }
        if (iArr == null) {
            throw new java.lang.NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, iArr);
    }

    long getNativeWrapperInstance() {
        return this.mNativeMeshWrapper;
    }

    private void setIntUniform(java.lang.String str, int i, int i2, int i3, int i4, int i5) {
        if (str == null) {
            throw new java.lang.NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeMeshWrapper, str, i, i2, i3, i4, i5);
    }

    private void meshSetup(long j, boolean z) {
        this.mNativeMeshWrapper = j;
        this.mIsIndexed = z;
        android.graphics.Mesh.MeshHolder.MESH_SPECIFICATION_REGISTRY.registerNativeAllocation(this, this.mNativeMeshWrapper);
    }
}
