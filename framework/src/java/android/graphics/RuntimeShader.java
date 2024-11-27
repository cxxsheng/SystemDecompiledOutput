package android.graphics;

/* loaded from: classes.dex */
public class RuntimeShader extends android.graphics.Shader {
    private long mNativeInstanceRuntimeShaderBuilder;
    private android.util.ArrayMap<java.lang.String, android.graphics.Shader> mShaderUniforms;

    private static native long nativeCreateBuilder(java.lang.String str);

    private static native long nativeCreateShader(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static native void nativeUpdateShader(long j, java.lang.String str, long j2);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, float f, float f2, float f3, float f4, int i);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, int i, int i2, int i3, int i4, int i5);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, float[] fArr, boolean z);

    private static native void nativeUpdateUniforms(long j, java.lang.String str, int[] iArr);

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.RuntimeShader.class.getClassLoader(), android.graphics.RuntimeShader.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public RuntimeShader(java.lang.String str) {
        super(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
        this.mShaderUniforms = new android.util.ArrayMap<>();
        if (str == null) {
            throw new java.lang.NullPointerException("RuntimeShader requires a non-null AGSL string");
        }
        this.mNativeInstanceRuntimeShaderBuilder = nativeCreateBuilder(str);
        android.graphics.RuntimeShader.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstanceRuntimeShaderBuilder);
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
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, f, f2, f3, f4, i);
        discardNativeInstance();
    }

    private void setUniform(java.lang.String str, float[] fArr, boolean z) {
        if (str == null) {
            throw new java.lang.NullPointerException("The uniformName parameter must not be null");
        }
        if (fArr == null) {
            throw new java.lang.NullPointerException("The uniform values parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, fArr, z);
        discardNativeInstance();
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
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, iArr);
        discardNativeInstance();
    }

    private void setIntUniform(java.lang.String str, int i, int i2, int i3, int i4, int i5) {
        if (str == null) {
            throw new java.lang.NullPointerException("The uniformName parameter must not be null");
        }
        nativeUpdateUniforms(this.mNativeInstanceRuntimeShaderBuilder, str, i, i2, i3, i4, i5);
        discardNativeInstance();
    }

    public void setInputShader(java.lang.String str, android.graphics.Shader shader) {
        if (str == null) {
            throw new java.lang.NullPointerException("The shaderName parameter must not be null");
        }
        if (shader == null) {
            throw new java.lang.NullPointerException("The shader parameter must not be null");
        }
        this.mShaderUniforms.put(str, shader);
        nativeUpdateShader(this.mNativeInstanceRuntimeShaderBuilder, str, shader.getNativeInstance());
        discardNativeInstance();
    }

    public void setInputBuffer(java.lang.String str, android.graphics.BitmapShader bitmapShader) {
        if (str == null) {
            throw new java.lang.NullPointerException("The shaderName parameter must not be null");
        }
        if (bitmapShader == null) {
            throw new java.lang.NullPointerException("The shader parameter must not be null");
        }
        this.mShaderUniforms.put(str, bitmapShader);
        nativeUpdateShader(this.mNativeInstanceRuntimeShaderBuilder, str, bitmapShader.getNativeInstanceWithDirectSampling());
        discardNativeInstance();
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        return nativeCreateShader(this.mNativeInstanceRuntimeShaderBuilder, j);
    }

    protected long getNativeShaderBuilder() {
        return this.mNativeInstanceRuntimeShaderBuilder;
    }
}
