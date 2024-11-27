package android.graphics;

/* loaded from: classes.dex */
public final class RenderEffect {
    private final long mNativeRenderEffect;

    private static native long nativeCreateBitmapEffect(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    private static native long nativeCreateBlendModeEffect(long j, long j2, int i);

    private static native long nativeCreateBlurEffect(float f, float f2, long j, int i);

    private static native long nativeCreateChainEffect(long j, long j2);

    private static native long nativeCreateColorFilterEffect(long j, long j2);

    private static native long nativeCreateOffsetEffect(float f, float f2, long j);

    private static native long nativeCreateRuntimeShaderEffect(long j, java.lang.String str);

    private static native long nativeCreateShaderEffect(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static class RenderEffectHolder {
        public static final libcore.util.NativeAllocationRegistry RENDER_EFFECT_REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.RenderEffect.class.getClassLoader(), android.graphics.RenderEffect.nativeGetFinalizer());

        private RenderEffectHolder() {
        }
    }

    public static android.graphics.RenderEffect createOffsetEffect(float f, float f2) {
        return new android.graphics.RenderEffect(nativeCreateOffsetEffect(f, f2, 0L));
    }

    public static android.graphics.RenderEffect createOffsetEffect(float f, float f2, android.graphics.RenderEffect renderEffect) {
        return new android.graphics.RenderEffect(nativeCreateOffsetEffect(f, f2, renderEffect.getNativeInstance()));
    }

    public static android.graphics.RenderEffect createBlurEffect(float f, float f2, android.graphics.RenderEffect renderEffect, android.graphics.Shader.TileMode tileMode) {
        return new android.graphics.RenderEffect(nativeCreateBlurEffect(f, f2, renderEffect != null ? renderEffect.mNativeRenderEffect : 0L, tileMode.nativeInt));
    }

    public static android.graphics.RenderEffect createBlurEffect(float f, float f2, android.graphics.Shader.TileMode tileMode) {
        return new android.graphics.RenderEffect(nativeCreateBlurEffect(f, f2, 0L, tileMode.nativeInt));
    }

    public static android.graphics.RenderEffect createBitmapEffect(android.graphics.Bitmap bitmap) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        return new android.graphics.RenderEffect(nativeCreateBitmapEffect(bitmap.getNativeInstance(), 0.0f, 0.0f, width, height, 0.0f, 0.0f, width, height));
    }

    public static android.graphics.RenderEffect createBitmapEffect(android.graphics.Bitmap bitmap, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return new android.graphics.RenderEffect(nativeCreateBitmapEffect(bitmap.getNativeInstance(), rect == null ? 0 : rect.left, rect != null ? rect.top : 0, rect == null ? bitmap.getWidth() : rect.right, rect == null ? bitmap.getHeight() : rect.bottom, rect2.left, rect2.top, rect2.right, rect2.bottom));
    }

    public static android.graphics.RenderEffect createColorFilterEffect(android.graphics.ColorFilter colorFilter, android.graphics.RenderEffect renderEffect) {
        return new android.graphics.RenderEffect(nativeCreateColorFilterEffect(colorFilter.getNativeInstance(), renderEffect.getNativeInstance()));
    }

    public static android.graphics.RenderEffect createColorFilterEffect(android.graphics.ColorFilter colorFilter) {
        return new android.graphics.RenderEffect(nativeCreateColorFilterEffect(colorFilter.getNativeInstance(), 0L));
    }

    public static android.graphics.RenderEffect createBlendModeEffect(android.graphics.RenderEffect renderEffect, android.graphics.RenderEffect renderEffect2, android.graphics.BlendMode blendMode) {
        return new android.graphics.RenderEffect(nativeCreateBlendModeEffect(renderEffect.getNativeInstance(), renderEffect2.getNativeInstance(), blendMode.getXfermode().porterDuffMode));
    }

    public static android.graphics.RenderEffect createChainEffect(android.graphics.RenderEffect renderEffect, android.graphics.RenderEffect renderEffect2) {
        return new android.graphics.RenderEffect(nativeCreateChainEffect(renderEffect.getNativeInstance(), renderEffect2.getNativeInstance()));
    }

    public static android.graphics.RenderEffect createShaderEffect(android.graphics.Shader shader) {
        return new android.graphics.RenderEffect(nativeCreateShaderEffect(shader.getNativeInstance()));
    }

    public static android.graphics.RenderEffect createRuntimeShaderEffect(android.graphics.RuntimeShader runtimeShader, java.lang.String str) {
        return new android.graphics.RenderEffect(nativeCreateRuntimeShaderEffect(runtimeShader.getNativeShaderBuilder(), str));
    }

    private RenderEffect(long j) {
        this.mNativeRenderEffect = j;
        android.graphics.RenderEffect.RenderEffectHolder.RENDER_EFFECT_REGISTRY.registerNativeAllocation(this, this.mNativeRenderEffect);
    }

    long getNativeInstance() {
        return this.mNativeRenderEffect;
    }
}
