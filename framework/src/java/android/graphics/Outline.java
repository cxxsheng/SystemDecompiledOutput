package android.graphics;

/* loaded from: classes.dex */
public final class Outline {
    public static final int MODE_EMPTY = 0;
    public static final int MODE_PATH = 2;
    public static final int MODE_ROUND_RECT = 1;
    private static final float RADIUS_UNDEFINED = Float.NEGATIVE_INFINITY;
    public float mAlpha;
    public android.graphics.Path mPath;
    public int mMode = 0;
    public final android.graphics.Rect mRect = new android.graphics.Rect();
    public float mRadius = RADIUS_UNDEFINED;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public Outline() {
    }

    public Outline(android.graphics.Outline outline) {
        set(outline);
    }

    public void setEmpty() {
        if (this.mPath != null) {
            this.mPath.rewind();
        }
        this.mMode = 0;
        this.mRect.setEmpty();
        this.mRadius = RADIUS_UNDEFINED;
    }

    public boolean isEmpty() {
        return this.mMode == 0;
    }

    public boolean canClip() {
        return true;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void set(android.graphics.Outline outline) {
        this.mMode = outline.mMode;
        if (outline.mMode == 2) {
            if (this.mPath == null) {
                this.mPath = new android.graphics.Path();
            }
            this.mPath.set(outline.mPath);
        }
        this.mRect.set(outline.mRect);
        this.mRadius = outline.mRadius;
        this.mAlpha = outline.mAlpha;
    }

    public void setRect(int i, int i2, int i3, int i4) {
        setRoundRect(i, i2, i3, i4, 0.0f);
    }

    public void setRect(android.graphics.Rect rect) {
        setRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setRoundRect(int i, int i2, int i3, int i4, float f) {
        if (i >= i3 || i2 >= i4) {
            setEmpty();
            return;
        }
        if (this.mMode == 2) {
            this.mPath.rewind();
        }
        this.mMode = 1;
        this.mRect.set(i, i2, i3, i4);
        this.mRadius = f;
    }

    public void setRoundRect(android.graphics.Rect rect, float f) {
        setRoundRect(rect.left, rect.top, rect.right, rect.bottom, f);
    }

    public boolean getRect(android.graphics.Rect rect) {
        if (this.mMode != 1) {
            return false;
        }
        rect.set(this.mRect);
        return true;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public void setOval(int i, int i2, int i3, int i4) {
        if (i >= i3 || i2 >= i4) {
            setEmpty();
            return;
        }
        int i5 = i4 - i2;
        if (i5 == i3 - i) {
            setRoundRect(i, i2, i3, i4, i5 / 2.0f);
            return;
        }
        if (this.mPath == null) {
            this.mPath = new android.graphics.Path();
        } else {
            this.mPath.rewind();
        }
        this.mMode = 2;
        this.mPath.addOval(i, i2, i3, i4, android.graphics.Path.Direction.CW);
        this.mRect.setEmpty();
        this.mRadius = RADIUS_UNDEFINED;
    }

    public void setOval(android.graphics.Rect rect) {
        setOval(rect.left, rect.top, rect.right, rect.bottom);
    }

    @java.lang.Deprecated
    public void setConvexPath(android.graphics.Path path) {
        setPath(path);
    }

    public void setPath(android.graphics.Path path) {
        if (path.isEmpty()) {
            setEmpty();
            return;
        }
        if (this.mPath == null) {
            this.mPath = new android.graphics.Path();
        }
        this.mMode = 2;
        this.mPath.set(path);
        this.mRect.setEmpty();
        this.mRadius = RADIUS_UNDEFINED;
    }

    public void offset(int i, int i2) {
        if (this.mMode == 1) {
            this.mRect.offset(i, i2);
        } else if (this.mMode == 2) {
            this.mPath.offset(i, i2);
        }
    }
}
