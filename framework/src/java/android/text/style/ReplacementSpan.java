package android.text.style;

/* loaded from: classes3.dex */
public abstract class ReplacementSpan extends android.text.style.MetricAffectingSpan {
    private java.lang.CharSequence mContentDescription = null;

    public abstract void draw(android.graphics.Canvas canvas, java.lang.CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, android.graphics.Paint paint);

    public abstract int getSize(android.graphics.Paint paint, java.lang.CharSequence charSequence, int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt);

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public void setContentDescription(java.lang.CharSequence charSequence) {
        this.mContentDescription = charSequence;
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
    }
}
