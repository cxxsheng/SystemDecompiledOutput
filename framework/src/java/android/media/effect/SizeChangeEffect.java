package android.media.effect;

/* loaded from: classes2.dex */
public class SizeChangeEffect extends android.media.effect.SingleFilterEffect {
    public SizeChangeEffect(android.media.effect.EffectContext effectContext, java.lang.String str, java.lang.Class cls, java.lang.String str2, java.lang.String str3, java.lang.Object... objArr) {
        super(effectContext, str, cls, str2, str3, objArr);
    }

    @Override // android.media.effect.SingleFilterEffect, android.media.effect.Effect
    public void apply(int i, int i2, int i3, int i4) {
        beginGLEffect();
        android.filterfw.core.Frame frameFromTexture = frameFromTexture(i, i2, i3);
        android.filterfw.core.Frame executeWithArgList = this.mFunction.executeWithArgList(this.mInputName, frameFromTexture);
        android.filterfw.core.Frame frameFromTexture2 = frameFromTexture(i4, executeWithArgList.getFormat().getWidth(), executeWithArgList.getFormat().getHeight());
        frameFromTexture2.setDataFromFrame(executeWithArgList);
        frameFromTexture.release();
        frameFromTexture2.release();
        executeWithArgList.release();
        endGLEffect();
    }
}
