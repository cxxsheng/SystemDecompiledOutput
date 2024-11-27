package android.text.style;

/* loaded from: classes3.dex */
public abstract class DynamicDrawableSpan extends android.text.style.ReplacementSpan {
    public static final int ALIGN_BASELINE = 1;
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 2;
    private java.lang.ref.WeakReference<android.graphics.drawable.Drawable> mDrawableRef;
    protected final int mVerticalAlignment;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AlignmentType {
    }

    public abstract android.graphics.drawable.Drawable getDrawable();

    public DynamicDrawableSpan() {
        this.mVerticalAlignment = 0;
    }

    protected DynamicDrawableSpan(int i) {
        this.mVerticalAlignment = i;
    }

    public int getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(android.graphics.Paint paint, java.lang.CharSequence charSequence, int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        android.graphics.Rect bounds = getCachedDrawable().getBounds();
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = -bounds.bottom;
            fontMetricsInt.descent = 0;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = 0;
        }
        return bounds.right;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(android.graphics.Canvas canvas, java.lang.CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, android.graphics.Paint paint) {
        android.graphics.drawable.Drawable cachedDrawable = getCachedDrawable();
        canvas.save();
        int i6 = i5 - cachedDrawable.getBounds().bottom;
        if (this.mVerticalAlignment == 1) {
            i6 -= paint.getFontMetricsInt().descent;
        } else if (this.mVerticalAlignment == 2) {
            i6 = (i3 + ((i5 - i3) / 2)) - (cachedDrawable.getBounds().height() / 2);
        }
        canvas.translate(f, i6);
        cachedDrawable.draw(canvas);
        canvas.restore();
    }

    private android.graphics.drawable.Drawable getCachedDrawable() {
        android.graphics.drawable.Drawable drawable;
        java.lang.ref.WeakReference<android.graphics.drawable.Drawable> weakReference = this.mDrawableRef;
        if (weakReference == null) {
            drawable = null;
        } else {
            drawable = weakReference.get();
        }
        if (drawable == null) {
            android.graphics.drawable.Drawable drawable2 = getDrawable();
            this.mDrawableRef = new java.lang.ref.WeakReference<>(drawable2);
            return drawable2;
        }
        return drawable;
    }

    public java.lang.String toString() {
        return "DynamicDrawableSpan{verticalAlignment=" + getVerticalAlignment() + ", drawable=" + getDrawable() + '}';
    }
}
