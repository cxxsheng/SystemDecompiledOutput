package android.graphics.drawable.shapes;

/* loaded from: classes.dex */
public class RoundRectShape extends android.graphics.drawable.shapes.RectShape {
    private float[] mInnerRadii;
    private android.graphics.RectF mInnerRect;
    private android.graphics.RectF mInset;
    private float[] mOuterRadii;
    private android.graphics.Path mPath;

    public RoundRectShape(float[] fArr, android.graphics.RectF rectF, float[] fArr2) {
        if (fArr != null && fArr.length < 8) {
            throw new java.lang.ArrayIndexOutOfBoundsException("outer radii must have >= 8 values");
        }
        if (fArr2 != null && fArr2.length < 8) {
            throw new java.lang.ArrayIndexOutOfBoundsException("inner radii must have >= 8 values");
        }
        this.mOuterRadii = fArr;
        this.mInset = rectF;
        this.mInnerRadii = fArr2;
        if (rectF != null) {
            this.mInnerRect = new android.graphics.RectF();
        }
        this.mPath = new android.graphics.Path();
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        canvas.drawPath(this.mPath, paint);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public void getOutline(android.graphics.Outline outline) {
        float f;
        if (this.mInnerRect != null) {
            return;
        }
        if (this.mOuterRadii == null) {
            f = 0.0f;
        } else {
            float f2 = this.mOuterRadii[0];
            for (int i = 1; i < 8; i++) {
                if (this.mOuterRadii[i] != f2) {
                    outline.setPath(this.mPath);
                    return;
                }
            }
            f = f2;
        }
        android.graphics.RectF rect = rect();
        outline.setRoundRect((int) java.lang.Math.ceil(rect.left), (int) java.lang.Math.ceil(rect.top), (int) java.lang.Math.floor(rect.right), (int) java.lang.Math.floor(rect.bottom), f);
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    protected void onResize(float f, float f2) {
        super.onResize(f, f2);
        android.graphics.RectF rect = rect();
        this.mPath.reset();
        if (this.mOuterRadii != null) {
            this.mPath.addRoundRect(rect, this.mOuterRadii, android.graphics.Path.Direction.CW);
        } else {
            this.mPath.addRect(rect, android.graphics.Path.Direction.CW);
        }
        if (this.mInnerRect != null) {
            this.mInnerRect.set(rect.left + this.mInset.left, rect.top + this.mInset.top, rect.right - this.mInset.right, rect.bottom - this.mInset.bottom);
            if (this.mInnerRect.width() < f && this.mInnerRect.height() < f2) {
                if (this.mInnerRadii != null) {
                    this.mPath.addRoundRect(this.mInnerRect, this.mInnerRadii, android.graphics.Path.Direction.CCW);
                } else {
                    this.mPath.addRect(this.mInnerRect, android.graphics.Path.Direction.CCW);
                }
            }
        }
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    /* renamed from: clone */
    public android.graphics.drawable.shapes.RoundRectShape mo1280clone() throws java.lang.CloneNotSupportedException {
        android.graphics.drawable.shapes.RoundRectShape roundRectShape = (android.graphics.drawable.shapes.RoundRectShape) super.mo1280clone();
        roundRectShape.mOuterRadii = this.mOuterRadii != null ? (float[]) this.mOuterRadii.clone() : null;
        roundRectShape.mInnerRadii = this.mInnerRadii != null ? (float[]) this.mInnerRadii.clone() : null;
        roundRectShape.mInset = new android.graphics.RectF(this.mInset);
        roundRectShape.mInnerRect = new android.graphics.RectF(this.mInnerRect);
        roundRectShape.mPath = new android.graphics.Path(this.mPath);
        return roundRectShape;
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        android.graphics.drawable.shapes.RoundRectShape roundRectShape = (android.graphics.drawable.shapes.RoundRectShape) obj;
        if (java.util.Arrays.equals(this.mOuterRadii, roundRectShape.mOuterRadii) && java.util.Objects.equals(this.mInset, roundRectShape.mInset) && java.util.Arrays.equals(this.mInnerRadii, roundRectShape.mInnerRadii) && java.util.Objects.equals(this.mInnerRect, roundRectShape.mInnerRect) && java.util.Objects.equals(this.mPath, roundRectShape.mPath)) {
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
    public int hashCode() {
        return (((java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mInset, this.mInnerRect, this.mPath) * 31) + java.util.Arrays.hashCode(this.mOuterRadii)) * 31) + java.util.Arrays.hashCode(this.mInnerRadii);
    }
}
