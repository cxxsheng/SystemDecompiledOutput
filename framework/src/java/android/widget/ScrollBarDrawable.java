package android.widget;

/* loaded from: classes4.dex */
public class ScrollBarDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
    private int mAlpha = 255;
    private boolean mAlwaysDrawHorizontalTrack;
    private boolean mAlwaysDrawVerticalTrack;
    private boolean mBoundsChanged;
    private android.graphics.ColorFilter mColorFilter;
    private int mExtent;
    private boolean mHasSetAlpha;
    private boolean mHasSetColorFilter;
    private android.graphics.drawable.Drawable mHorizontalThumb;
    private android.graphics.drawable.Drawable mHorizontalTrack;
    private boolean mMutated;
    private int mOffset;
    private int mRange;
    private boolean mRangeChanged;
    private boolean mVertical;
    private android.graphics.drawable.Drawable mVerticalThumb;
    private android.graphics.drawable.Drawable mVerticalTrack;

    public void setAlwaysDrawHorizontalTrack(boolean z) {
        this.mAlwaysDrawHorizontalTrack = z;
    }

    public void setAlwaysDrawVerticalTrack(boolean z) {
        this.mAlwaysDrawVerticalTrack = z;
    }

    public boolean getAlwaysDrawVerticalTrack() {
        return this.mAlwaysDrawVerticalTrack;
    }

    public boolean getAlwaysDrawHorizontalTrack() {
        return this.mAlwaysDrawHorizontalTrack;
    }

    public void setParameters(int i, int i2, int i3, boolean z) {
        if (this.mVertical != z) {
            this.mVertical = z;
            this.mBoundsChanged = true;
        }
        if (this.mRange != i || this.mOffset != i2 || this.mExtent != i3) {
            this.mRange = i;
            this.mOffset = i2;
            this.mExtent = i3;
            this.mRangeChanged = true;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        boolean z;
        boolean z2;
        boolean z3 = this.mVertical;
        int i = this.mExtent;
        int i2 = this.mRange;
        if (i <= 0 || i2 <= i) {
            z = z3 ? this.mAlwaysDrawVerticalTrack : this.mAlwaysDrawHorizontalTrack;
            z2 = false;
        } else {
            z = true;
            z2 = true;
        }
        android.graphics.Rect bounds = getBounds();
        if (canvas.quickReject(bounds.left, bounds.top, bounds.right, bounds.bottom)) {
            return;
        }
        if (z) {
            drawTrack(canvas, bounds, z3);
        }
        if (z2) {
            int height = z3 ? bounds.height() : bounds.width();
            int thumbLength = com.android.internal.widget.ScrollBarUtils.getThumbLength(height, z3 ? bounds.width() : bounds.height(), i, i2);
            drawThumb(canvas, bounds, com.android.internal.widget.ScrollBarUtils.getThumbOffset(height, thumbLength, i, i2, this.mOffset), thumbLength, z3);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        super.onBoundsChange(rect);
        this.mBoundsChanged = true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return (this.mVerticalTrack != null && this.mVerticalTrack.isStateful()) || (this.mVerticalThumb != null && this.mVerticalThumb.isStateful()) || ((this.mHorizontalTrack != null && this.mHorizontalTrack.isStateful()) || ((this.mHorizontalThumb != null && this.mHorizontalThumb.isStateful()) || super.isStateful()));
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        if (this.mVerticalTrack != null) {
            onStateChange |= this.mVerticalTrack.setState(iArr);
        }
        if (this.mVerticalThumb != null) {
            onStateChange |= this.mVerticalThumb.setState(iArr);
        }
        if (this.mHorizontalTrack != null) {
            onStateChange |= this.mHorizontalTrack.setState(iArr);
        }
        if (this.mHorizontalThumb != null) {
            return onStateChange | this.mHorizontalThumb.setState(iArr);
        }
        return onStateChange;
    }

    private void drawTrack(android.graphics.Canvas canvas, android.graphics.Rect rect, boolean z) {
        android.graphics.drawable.Drawable drawable;
        if (z) {
            drawable = this.mVerticalTrack;
        } else {
            drawable = this.mHorizontalTrack;
        }
        if (drawable != null) {
            if (this.mBoundsChanged) {
                drawable.setBounds(rect);
            }
            drawable.draw(canvas);
        }
    }

    private void drawThumb(android.graphics.Canvas canvas, android.graphics.Rect rect, int i, int i2, boolean z) {
        boolean z2 = this.mRangeChanged || this.mBoundsChanged;
        if (z) {
            if (this.mVerticalThumb != null) {
                android.graphics.drawable.Drawable drawable = this.mVerticalThumb;
                if (z2) {
                    drawable.setBounds(rect.left, rect.top + i, rect.right, rect.top + i + i2);
                }
                drawable.draw(canvas);
                return;
            }
            return;
        }
        if (this.mHorizontalThumb != null) {
            android.graphics.drawable.Drawable drawable2 = this.mHorizontalThumb;
            if (z2) {
                drawable2.setBounds(rect.left + i, rect.top, rect.left + i + i2, rect.bottom);
            }
            drawable2.draw(canvas);
        }
    }

    public void setVerticalThumbDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mVerticalThumb != null) {
            this.mVerticalThumb.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mVerticalThumb = drawable;
    }

    public android.graphics.drawable.Drawable getVerticalTrackDrawable() {
        return this.mVerticalTrack;
    }

    public android.graphics.drawable.Drawable getVerticalThumbDrawable() {
        return this.mVerticalThumb;
    }

    public android.graphics.drawable.Drawable getHorizontalTrackDrawable() {
        return this.mHorizontalTrack;
    }

    public android.graphics.drawable.Drawable getHorizontalThumbDrawable() {
        return this.mHorizontalThumb;
    }

    public void setVerticalTrackDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mVerticalTrack != null) {
            this.mVerticalTrack.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mVerticalTrack = drawable;
    }

    public void setHorizontalThumbDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mHorizontalThumb != null) {
            this.mHorizontalThumb.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mHorizontalThumb = drawable;
    }

    public void setHorizontalTrackDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mHorizontalTrack != null) {
            this.mHorizontalTrack.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mHorizontalTrack = drawable;
    }

    private void propagateCurrentState(android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            if (this.mMutated) {
                drawable.mutate();
            }
            drawable.setState(getState());
            drawable.setCallback(this);
            if (this.mHasSetAlpha) {
                drawable.setAlpha(this.mAlpha);
            }
            if (this.mHasSetColorFilter) {
                drawable.setColorFilter(this.mColorFilter);
            }
        }
    }

    public int getSize(boolean z) {
        if (z) {
            if (this.mVerticalTrack != null) {
                return this.mVerticalTrack.getIntrinsicWidth();
            }
            if (this.mVerticalThumb != null) {
                return this.mVerticalThumb.getIntrinsicWidth();
            }
            return 0;
        }
        if (this.mHorizontalTrack != null) {
            return this.mHorizontalTrack.getIntrinsicHeight();
        }
        if (this.mHorizontalThumb != null) {
            return this.mHorizontalThumb.getIntrinsicHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public android.widget.ScrollBarDrawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            if (this.mVerticalTrack != null) {
                this.mVerticalTrack.mutate();
            }
            if (this.mVerticalThumb != null) {
                this.mVerticalThumb.mutate();
            }
            if (this.mHorizontalTrack != null) {
                this.mHorizontalTrack.mutate();
            }
            if (this.mHorizontalThumb != null) {
                this.mHorizontalThumb.mutate();
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAlpha = i;
        this.mHasSetAlpha = true;
        if (this.mVerticalTrack != null) {
            this.mVerticalTrack.setAlpha(i);
        }
        if (this.mVerticalThumb != null) {
            this.mVerticalThumb.setAlpha(i);
        }
        if (this.mHorizontalTrack != null) {
            this.mHorizontalTrack.setAlpha(i);
        }
        if (this.mHorizontalThumb != null) {
            this.mHorizontalThumb.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mHasSetColorFilter = true;
        if (this.mVerticalTrack != null) {
            this.mVerticalTrack.setColorFilter(colorFilter);
        }
        if (this.mVerticalThumb != null) {
            this.mVerticalThumb.setColorFilter(colorFilter);
        }
        if (this.mHorizontalTrack != null) {
            this.mHorizontalTrack.setColorFilter(colorFilter);
        }
        if (this.mHorizontalThumb != null) {
            this.mHorizontalThumb.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public java.lang.String toString() {
        return "ScrollBarDrawable: range=" + this.mRange + " offset=" + this.mOffset + " extent=" + this.mExtent + (this.mVertical ? " V" : " H");
    }
}
