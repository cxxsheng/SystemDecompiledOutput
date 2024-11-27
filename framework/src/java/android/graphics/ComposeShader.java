package android.graphics;

/* loaded from: classes.dex */
public class ComposeShader extends android.graphics.Shader {
    private long mNativeInstanceShaderA;
    private long mNativeInstanceShaderB;
    private int mPorterDuffMode;
    android.graphics.Shader mShaderA;
    android.graphics.Shader mShaderB;

    private static native long nativeCreate(long j, long j2, long j3, int i);

    @java.lang.Deprecated
    public ComposeShader(android.graphics.Shader shader, android.graphics.Shader shader2, android.graphics.Xfermode xfermode) {
        this(shader, shader2, xfermode.porterDuffMode);
    }

    public ComposeShader(android.graphics.Shader shader, android.graphics.Shader shader2, android.graphics.PorterDuff.Mode mode) {
        this(shader, shader2, mode.nativeInt);
    }

    public ComposeShader(android.graphics.Shader shader, android.graphics.Shader shader2, android.graphics.BlendMode blendMode) {
        this(shader, shader2, blendMode.getXfermode().porterDuffMode);
    }

    private ComposeShader(android.graphics.Shader shader, android.graphics.Shader shader2, int i) {
        if (shader == null || shader2 == null) {
            throw new java.lang.IllegalArgumentException("Shader parameters must not be null");
        }
        this.mShaderA = shader;
        this.mShaderB = shader2;
        this.mPorterDuffMode = i;
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        this.mNativeInstanceShaderA = this.mShaderA.getNativeInstance(z);
        this.mNativeInstanceShaderB = this.mShaderB.getNativeInstance(z);
        return nativeCreate(j, this.mShaderA.getNativeInstance(), this.mShaderB.getNativeInstance(), this.mPorterDuffMode);
    }

    @Override // android.graphics.Shader
    protected boolean shouldDiscardNativeInstance(boolean z) {
        return (this.mShaderA.getNativeInstance(z) == this.mNativeInstanceShaderA && this.mShaderB.getNativeInstance(z) == this.mNativeInstanceShaderB) ? false : true;
    }
}
