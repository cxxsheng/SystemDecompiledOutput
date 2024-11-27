package android.text.style;

/* loaded from: classes3.dex */
public class IconMarginSpan implements android.text.style.LeadingMarginSpan, android.text.style.LineHeightSpan {
    private final android.graphics.Bitmap mBitmap;
    private final int mPad;

    public IconMarginSpan(android.graphics.Bitmap bitmap) {
        this(bitmap, 0);
    }

    public IconMarginSpan(android.graphics.Bitmap bitmap, int i) {
        this.mBitmap = bitmap;
        this.mPad = i;
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return this.mBitmap.getWidth() + this.mPad;
    }

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, boolean z, android.text.Layout layout) {
        int lineTop = layout.getLineTop(layout.getLineForOffset(((android.text.Spanned) charSequence).getSpanStart(this)));
        if (i2 < 0) {
            i -= this.mBitmap.getWidth();
        }
        canvas.drawBitmap(this.mBitmap, i, lineTop, paint);
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        if (i2 == ((android.text.Spanned) charSequence).getSpanEnd(this)) {
            int height = this.mBitmap.getHeight();
            int i5 = height - (((fontMetricsInt.descent + i4) - fontMetricsInt.ascent) - i3);
            if (i5 > 0) {
                fontMetricsInt.descent += i5;
            }
            int i6 = height - (((i4 + fontMetricsInt.bottom) - fontMetricsInt.top) - i3);
            if (i6 > 0) {
                fontMetricsInt.bottom += i6;
            }
        }
    }

    public java.lang.String toString() {
        return "IconMarginSpan{bitmap=" + getBitmap() + ", padding=" + getPadding() + '}';
    }

    public android.graphics.Bitmap getBitmap() {
        return this.mBitmap;
    }

    public int getPadding() {
        return this.mPad;
    }
}
