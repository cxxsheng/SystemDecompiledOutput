package android.graphics.drawable.shapes;

/* loaded from: classes.dex */
public class OvalShape extends android.graphics.drawable.shapes.RectShape {
    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        canvas.drawOval(rect(), paint);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void getOutline(android.graphics.Outline outline) {
        android.graphics.RectF rect = rect();
        outline.setOval((int) java.lang.Math.ceil(rect.left), (int) java.lang.Math.ceil(rect.top), (int) java.lang.Math.floor(rect.right), (int) java.lang.Math.floor(rect.bottom));
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public android.graphics.drawable.shapes.OvalShape mo1280clone() throws java.lang.CloneNotSupportedException {
        return (android.graphics.drawable.shapes.OvalShape) super.mo1280clone();
    }
}
