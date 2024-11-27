package android.text;

/* loaded from: classes3.dex */
public class TextPaint extends android.graphics.Paint {
    public int baselineShift;
    public int bgColor;
    public float density;
    public int[] drawableState;
    public int linkColor;
    public int underlineColor;
    public float underlineThickness;

    public TextPaint() {
        this.density = 1.0f;
        this.underlineColor = 0;
    }

    public TextPaint(int i) {
        super(i);
        this.density = 1.0f;
        this.underlineColor = 0;
    }

    public TextPaint(android.graphics.Paint paint) {
        super(paint);
        this.density = 1.0f;
        this.underlineColor = 0;
    }

    public void set(android.text.TextPaint textPaint) {
        super.set((android.graphics.Paint) textPaint);
        this.bgColor = textPaint.bgColor;
        this.baselineShift = textPaint.baselineShift;
        this.linkColor = textPaint.linkColor;
        this.drawableState = textPaint.drawableState;
        this.density = textPaint.density;
        this.underlineColor = textPaint.underlineColor;
        this.underlineThickness = textPaint.underlineThickness;
    }

    public void setUnderlineText(int i, float f) {
        this.underlineColor = i;
        this.underlineThickness = f;
    }

    @Override // android.graphics.Paint
    public float getUnderlineThickness() {
        if (this.underlineColor != 0) {
            return this.underlineThickness;
        }
        return super.getUnderlineThickness();
    }
}
