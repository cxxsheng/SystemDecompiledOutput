package android.text.style;

/* loaded from: classes3.dex */
public class MaskFilterSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateAppearance {
    private android.graphics.MaskFilter mFilter;

    public MaskFilterSpan(android.graphics.MaskFilter maskFilter) {
        this.mFilter = maskFilter;
    }

    public android.graphics.MaskFilter getMaskFilter() {
        return this.mFilter;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.setMaskFilter(this.mFilter);
    }

    public java.lang.String toString() {
        return "MaskFilterSpan{filter=" + getMaskFilter() + '}';
    }
}
