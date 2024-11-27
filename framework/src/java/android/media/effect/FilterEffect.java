package android.media.effect;

/* loaded from: classes2.dex */
public abstract class FilterEffect extends android.media.effect.Effect {
    protected android.media.effect.EffectContext mEffectContext;
    private java.lang.String mName;

    protected FilterEffect(android.media.effect.EffectContext effectContext, java.lang.String str) {
        this.mEffectContext = effectContext;
        this.mName = str;
    }

    @Override // android.media.effect.Effect
    public java.lang.String getName() {
        return this.mName;
    }

    protected void beginGLEffect() {
        this.mEffectContext.assertValidGLState();
        this.mEffectContext.saveGLState();
    }

    protected void endGLEffect() {
        this.mEffectContext.restoreGLState();
    }

    protected android.filterfw.core.FilterContext getFilterContext() {
        return this.mEffectContext.mFilterContext;
    }

    protected android.filterfw.core.Frame frameFromTexture(int i, int i2, int i3) {
        android.filterfw.core.Frame newBoundFrame = getFilterContext().getFrameManager().newBoundFrame(android.filterfw.format.ImageFormat.create(i2, i3, 3, 3), 100, i);
        newBoundFrame.setTimestamp(-1L);
        return newBoundFrame;
    }
}
