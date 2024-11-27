package android.graphics.drawable.shapes;

/* loaded from: classes.dex */
public class RectShape extends android.graphics.drawable.shapes.Shape {
    private android.graphics.RectF mRect = new android.graphics.RectF();

    @Override // android.graphics.drawable.shapes.Shape
    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        canvas.drawRect(this.mRect, paint);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void getOutline(android.graphics.Outline outline) {
        android.graphics.RectF rect = rect();
        outline.setRect((int) java.lang.Math.ceil(rect.left), (int) java.lang.Math.ceil(rect.top), (int) java.lang.Math.floor(rect.right), (int) java.lang.Math.floor(rect.bottom));
    }

    @Override // android.graphics.drawable.shapes.Shape
    protected void onResize(float f, float f2) {
        this.mRect.set(0.0f, 0.0f, f, f2);
    }

    protected final android.graphics.RectF rect() {
        return this.mRect;
    }

    @Override // android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public android.graphics.drawable.shapes.RectShape mo1280clone() throws java.lang.CloneNotSupportedException {
        android.graphics.drawable.shapes.RectShape rectShape = (android.graphics.drawable.shapes.RectShape) super.mo1280clone();
        rectShape.mRect = new android.graphics.RectF(this.mRect);
        return rectShape;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        return java.util.Objects.equals(this.mRect, ((android.graphics.drawable.shapes.RectShape) obj).mRect);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mRect);
    }
}
