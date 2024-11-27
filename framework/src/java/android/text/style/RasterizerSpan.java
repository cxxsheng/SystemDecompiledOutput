package android.text.style;

/* loaded from: classes3.dex */
public class RasterizerSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateAppearance {
    private android.graphics.Rasterizer mRasterizer;

    public RasterizerSpan(android.graphics.Rasterizer rasterizer) {
        this.mRasterizer = rasterizer;
    }

    public android.graphics.Rasterizer getRasterizer() {
        return this.mRasterizer;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.setRasterizer(this.mRasterizer);
    }
}
