package android.animation;

/* loaded from: classes.dex */
public class RectEvaluator implements android.animation.TypeEvaluator<android.graphics.Rect> {
    private android.graphics.Rect mRect;

    public RectEvaluator() {
    }

    public RectEvaluator(android.graphics.Rect rect) {
        this.mRect = rect;
    }

    @Override // android.animation.TypeEvaluator
    public android.graphics.Rect evaluate(float f, android.graphics.Rect rect, android.graphics.Rect rect2) {
        int i = rect.left + ((int) ((rect2.left - rect.left) * f));
        int i2 = rect.top + ((int) ((rect2.top - rect.top) * f));
        int i3 = rect.right + ((int) ((rect2.right - rect.right) * f));
        int i4 = rect.bottom + ((int) ((rect2.bottom - rect.bottom) * f));
        if (this.mRect == null) {
            return new android.graphics.Rect(i, i2, i3, i4);
        }
        this.mRect.set(i, i2, i3, i4);
        return this.mRect;
    }
}
