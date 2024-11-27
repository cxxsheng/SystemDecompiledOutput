package android.graphics.drawable.shapes;

/* loaded from: classes.dex */
public abstract class Shape implements java.lang.Cloneable {
    private float mHeight;
    private float mWidth;

    public abstract void draw(android.graphics.Canvas canvas, android.graphics.Paint paint);

    public final float getWidth() {
        return this.mWidth;
    }

    public final float getHeight() {
        return this.mHeight;
    }

    public final void resize(float f, float f2) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (this.mWidth != f || this.mHeight != f2) {
            this.mWidth = f;
            this.mHeight = f2;
            onResize(f, f2);
        }
    }

    public boolean hasAlpha() {
        return true;
    }

    protected void onResize(float f, float f2) {
    }

    public void getOutline(android.graphics.Outline outline) {
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.graphics.drawable.shapes.Shape mo1280clone() throws java.lang.CloneNotSupportedException {
        return (android.graphics.drawable.shapes.Shape) super.clone();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.drawable.shapes.Shape shape = (android.graphics.drawable.shapes.Shape) obj;
        if (java.lang.Float.compare(shape.mWidth, this.mWidth) == 0 && java.lang.Float.compare(shape.mHeight, this.mHeight) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mWidth), java.lang.Float.valueOf(this.mHeight));
    }
}
