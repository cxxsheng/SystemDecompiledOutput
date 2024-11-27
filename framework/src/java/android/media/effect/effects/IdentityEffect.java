package android.media.effect.effects;

/* loaded from: classes2.dex */
public class IdentityEffect extends android.media.effect.FilterEffect {
    public IdentityEffect(android.media.effect.EffectContext effectContext, java.lang.String str) {
        super(effectContext, str);
    }

    @Override // android.media.effect.Effect
    public void apply(int i, int i2, int i3, int i4) {
        beginGLEffect();
        android.filterfw.core.Frame frameFromTexture = frameFromTexture(i, i2, i3);
        android.filterfw.core.Frame frameFromTexture2 = frameFromTexture(i4, i2, i3);
        frameFromTexture2.setDataFromFrame(frameFromTexture);
        frameFromTexture.release();
        frameFromTexture2.release();
        endGLEffect();
    }

    @Override // android.media.effect.Effect
    public void setParameter(java.lang.String str, java.lang.Object obj) {
        throw new java.lang.IllegalArgumentException("Unknown parameter " + str + " for IdentityEffect!");
    }

    @Override // android.media.effect.Effect
    public void release() {
    }
}
