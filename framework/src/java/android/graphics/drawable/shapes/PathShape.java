package android.graphics.drawable.shapes;

/* loaded from: classes.dex */
public class PathShape extends android.graphics.drawable.shapes.Shape {
    private android.graphics.Path mPath;
    private float mScaleX;
    private float mScaleY;
    private final float mStdHeight;
    private final float mStdWidth;

    public PathShape(android.graphics.Path path, float f, float f2) {
        this.mPath = path;
        this.mStdWidth = f;
        this.mStdHeight = f2;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        canvas.save();
        canvas.scale(this.mScaleX, this.mScaleY);
        canvas.drawPath(this.mPath, paint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.shapes.Shape
    protected void onResize(float f, float f2) {
        this.mScaleX = f / this.mStdWidth;
        this.mScaleY = f2 / this.mStdHeight;
    }

    @Override // android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public android.graphics.drawable.shapes.PathShape mo1280clone() throws java.lang.CloneNotSupportedException {
        android.graphics.drawable.shapes.PathShape pathShape = (android.graphics.drawable.shapes.PathShape) super.mo1280clone();
        pathShape.mPath = new android.graphics.Path(this.mPath);
        return pathShape;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        android.graphics.drawable.shapes.PathShape pathShape = (android.graphics.drawable.shapes.PathShape) obj;
        if (java.lang.Float.compare(pathShape.mStdWidth, this.mStdWidth) == 0 && java.lang.Float.compare(pathShape.mStdHeight, this.mStdHeight) == 0 && java.lang.Float.compare(pathShape.mScaleX, this.mScaleX) == 0 && java.lang.Float.compare(pathShape.mScaleY, this.mScaleY) == 0 && java.util.Objects.equals(this.mPath, pathShape.mPath)) {
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), java.lang.Float.valueOf(this.mStdWidth), java.lang.Float.valueOf(this.mStdHeight), this.mPath, java.lang.Float.valueOf(this.mScaleX), java.lang.Float.valueOf(this.mScaleY));
    }
}
