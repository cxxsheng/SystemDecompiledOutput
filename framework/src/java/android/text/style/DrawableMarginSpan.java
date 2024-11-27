package android.text.style;

/* loaded from: classes3.dex */
public class DrawableMarginSpan implements android.text.style.LeadingMarginSpan, android.text.style.LineHeightSpan {
    private static final int STANDARD_PAD_WIDTH = 0;
    private final android.graphics.drawable.Drawable mDrawable;
    private final int mPad;

    public DrawableMarginSpan(android.graphics.drawable.Drawable drawable) {
        this(drawable, 0);
    }

    public DrawableMarginSpan(android.graphics.drawable.Drawable drawable, int i) {
        this.mDrawable = drawable;
        this.mPad = i;
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return this.mDrawable.getIntrinsicWidth() + this.mPad;
    }

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, boolean z, android.text.Layout layout) {
        int lineTop = layout.getLineTop(layout.getLineForOffset(((android.text.Spanned) charSequence).getSpanStart(this)));
        this.mDrawable.setBounds(i, lineTop, this.mDrawable.getIntrinsicWidth() + i, this.mDrawable.getIntrinsicHeight() + lineTop);
        this.mDrawable.draw(canvas);
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        if (i2 == ((android.text.Spanned) charSequence).getSpanEnd(this)) {
            int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
            int i5 = intrinsicHeight - (((fontMetricsInt.descent + i4) - fontMetricsInt.ascent) - i3);
            if (i5 > 0) {
                fontMetricsInt.descent += i5;
            }
            int i6 = intrinsicHeight - (((i4 + fontMetricsInt.bottom) - fontMetricsInt.top) - i3);
            if (i6 > 0) {
                fontMetricsInt.bottom += i6;
            }
        }
    }

    public java.lang.String toString() {
        return "DrawableMarginSpan{drawable=" + this.mDrawable + ", padding=" + this.mPad + '}';
    }

    public android.graphics.drawable.Drawable getDrawable() {
        return this.mDrawable;
    }

    public int getPadding() {
        return this.mPad;
    }
}
