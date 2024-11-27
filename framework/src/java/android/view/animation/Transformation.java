package android.view.animation;

/* loaded from: classes4.dex */
public class Transformation {
    public static final int TYPE_ALPHA = 1;
    public static final int TYPE_BOTH = 3;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_MATRIX = 2;
    protected float mAlpha;
    private boolean mHasClipRect;
    protected android.graphics.Matrix mMatrix;
    protected int mTransformationType;
    private android.graphics.Rect mClipRect = new android.graphics.Rect();
    private android.graphics.Insets mInsets = android.graphics.Insets.NONE;

    public Transformation() {
        clear();
    }

    public void clear() {
        if (this.mMatrix == null) {
            this.mMatrix = new android.graphics.Matrix();
        } else {
            this.mMatrix.reset();
        }
        this.mClipRect.setEmpty();
        this.mHasClipRect = false;
        this.mAlpha = 1.0f;
        this.mTransformationType = 3;
    }

    public int getTransformationType() {
        return this.mTransformationType;
    }

    public void setTransformationType(int i) {
        this.mTransformationType = i;
    }

    public void set(android.view.animation.Transformation transformation) {
        this.mAlpha = transformation.getAlpha();
        this.mMatrix.set(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            setClipRect(transformation.getClipRect());
        } else {
            this.mHasClipRect = false;
            this.mClipRect.setEmpty();
        }
        this.mTransformationType = transformation.getTransformationType();
    }

    public void compose(android.view.animation.Transformation transformation) {
        this.mAlpha *= transformation.getAlpha();
        this.mMatrix.preConcat(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            android.graphics.Rect clipRect = transformation.getClipRect();
            if (this.mHasClipRect) {
                setClipRect(this.mClipRect.left + clipRect.left, this.mClipRect.top + clipRect.top, this.mClipRect.right + clipRect.right, this.mClipRect.bottom + clipRect.bottom);
            } else {
                setClipRect(clipRect);
            }
        }
        setInsets(android.graphics.Insets.add(getInsets(), transformation.getInsets()));
    }

    public void postCompose(android.view.animation.Transformation transformation) {
        this.mAlpha *= transformation.getAlpha();
        this.mMatrix.postConcat(transformation.getMatrix());
        if (transformation.mHasClipRect) {
            android.graphics.Rect clipRect = transformation.getClipRect();
            if (this.mHasClipRect) {
                setClipRect(this.mClipRect.left + clipRect.left, this.mClipRect.top + clipRect.top, this.mClipRect.right + clipRect.right, this.mClipRect.bottom + clipRect.bottom);
            } else {
                setClipRect(clipRect);
            }
        }
    }

    public android.graphics.Matrix getMatrix() {
        return this.mMatrix;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void setClipRect(android.graphics.Rect rect) {
        setClipRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setClipRect(int i, int i2, int i3, int i4) {
        this.mClipRect.set(i, i2, i3, i4);
        this.mHasClipRect = true;
    }

    public android.graphics.Rect getClipRect() {
        return this.mClipRect;
    }

    public boolean hasClipRect() {
        return this.mHasClipRect;
    }

    public void setInsets(android.graphics.Insets insets) {
        this.mInsets = insets;
    }

    public void setInsets(int i, int i2, int i3, int i4) {
        this.mInsets = android.graphics.Insets.of(i, i2, i3, i4);
    }

    public android.graphics.Insets getInsets() {
        return this.mInsets;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        sb.append("Transformation");
        toShortString(sb);
        return sb.toString();
    }

    public java.lang.String toShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        toShortString(sb);
        return sb.toString();
    }

    public void toShortString(java.lang.StringBuilder sb) {
        sb.append("{alpha=");
        sb.append(this.mAlpha);
        sb.append(" matrix=");
        sb.append(this.mMatrix.toShortString());
        sb.append('}');
    }

    public void printShortString(java.io.PrintWriter printWriter) {
        printWriter.print("{alpha=");
        printWriter.print(this.mAlpha);
        printWriter.print(" matrix=");
        this.mMatrix.dump(printWriter);
        printWriter.print('}');
    }
}
