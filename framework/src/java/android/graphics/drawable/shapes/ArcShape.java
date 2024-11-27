package android.graphics.drawable.shapes;

/* loaded from: classes.dex */
public class ArcShape extends android.graphics.drawable.shapes.RectShape {
    private final float mStartAngle;
    private final float mSweepAngle;

    public ArcShape(float f, float f2) {
        this.mStartAngle = f;
        this.mSweepAngle = f2;
    }

    public final float getStartAngle() {
        return this.mStartAngle;
    }

    public final float getSweepAngle() {
        return this.mSweepAngle;
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        canvas.drawArc(rect(), this.mStartAngle, this.mSweepAngle, true, paint);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void getOutline(android.graphics.Outline outline) {
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public android.graphics.drawable.shapes.ArcShape mo1280clone() throws java.lang.CloneNotSupportedException {
        return (android.graphics.drawable.shapes.ArcShape) super.mo1280clone();
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        android.graphics.drawable.shapes.ArcShape arcShape = (android.graphics.drawable.shapes.ArcShape) obj;
        if (java.lang.Float.compare(arcShape.mStartAngle, this.mStartAngle) == 0 && java.lang.Float.compare(arcShape.mSweepAngle, this.mSweepAngle) == 0) {
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), java.lang.Float.valueOf(this.mStartAngle), java.lang.Float.valueOf(this.mSweepAngle));
    }
}
