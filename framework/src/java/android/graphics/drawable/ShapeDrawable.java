package android.graphics.drawable;

/* loaded from: classes.dex */
public class ShapeDrawable extends android.graphics.drawable.Drawable {
    private android.graphics.BlendModeColorFilter mBlendModeColorFilter;
    private boolean mMutated;
    private android.graphics.drawable.ShapeDrawable.ShapeState mShapeState;

    public static abstract class ShaderFactory {
        public abstract android.graphics.Shader resize(int i, int i2);
    }

    public ShapeDrawable() {
        this(new android.graphics.drawable.ShapeDrawable.ShapeState(), null);
    }

    public ShapeDrawable(android.graphics.drawable.shapes.Shape shape) {
        this(new android.graphics.drawable.ShapeDrawable.ShapeState(), null);
        this.mShapeState.mShape = shape;
    }

    public android.graphics.drawable.shapes.Shape getShape() {
        return this.mShapeState.mShape;
    }

    public void setShape(android.graphics.drawable.shapes.Shape shape) {
        this.mShapeState.mShape = shape;
        updateShape();
    }

    public void setShaderFactory(android.graphics.drawable.ShapeDrawable.ShaderFactory shaderFactory) {
        this.mShapeState.mShaderFactory = shaderFactory;
    }

    public android.graphics.drawable.ShapeDrawable.ShaderFactory getShaderFactory() {
        return this.mShapeState.mShaderFactory;
    }

    public android.graphics.Paint getPaint() {
        return this.mShapeState.mPaint;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if ((i | i2 | i3 | i4) == 0) {
            this.mShapeState.mPadding = null;
        } else {
            if (this.mShapeState.mPadding == null) {
                this.mShapeState.mPadding = new android.graphics.Rect();
            }
            this.mShapeState.mPadding.set(i, i2, i3, i4);
        }
        invalidateSelf();
    }

    public void setPadding(android.graphics.Rect rect) {
        if (rect == null) {
            this.mShapeState.mPadding = null;
        } else {
            if (this.mShapeState.mPadding == null) {
                this.mShapeState.mPadding = new android.graphics.Rect();
            }
            this.mShapeState.mPadding.set(rect);
        }
        invalidateSelf();
    }

    public void setIntrinsicWidth(int i) {
        this.mShapeState.mIntrinsicWidth = i;
        invalidateSelf();
    }

    public void setIntrinsicHeight(int i) {
        this.mShapeState.mIntrinsicHeight = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mShapeState.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mShapeState.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(android.graphics.Rect rect) {
        if (this.mShapeState.mPadding != null) {
            rect.set(this.mShapeState.mPadding);
            return true;
        }
        return super.getPadding(rect);
    }

    private static int modulateAlpha(int i, int i2) {
        return (i * (i2 + (i2 >>> 7))) >>> 8;
    }

    protected void onDraw(android.graphics.drawable.shapes.Shape shape, android.graphics.Canvas canvas, android.graphics.Paint paint) {
        shape.draw(canvas, paint);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        boolean z;
        android.graphics.Rect bounds = getBounds();
        android.graphics.drawable.ShapeDrawable.ShapeState shapeState = this.mShapeState;
        android.graphics.Paint paint = shapeState.mPaint;
        int alpha = paint.getAlpha();
        paint.setAlpha(modulateAlpha(alpha, shapeState.mAlpha));
        if (paint.getAlpha() != 0 || paint.getXfermode() != null || paint.hasShadowLayer()) {
            if (this.mBlendModeColorFilter != null && paint.getColorFilter() == null) {
                paint.setColorFilter(this.mBlendModeColorFilter);
                z = true;
            } else {
                z = false;
            }
            if (shapeState.mShape != null) {
                int save = canvas.save();
                canvas.translate(bounds.left, bounds.top);
                onDraw(shapeState.mShape, canvas, paint);
                canvas.restoreToCount(save);
            } else {
                canvas.drawRect(bounds, paint);
            }
            if (z) {
                paint.setColorFilter(null);
            }
        }
        paint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mShapeState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mShapeState.mAlpha = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mShapeState.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        this.mShapeState.mTint = colorStateList;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, this.mShapeState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mShapeState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mShapeState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mShapeState.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(android.graphics.Xfermode xfermode) {
        this.mShapeState.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mShapeState.mShape == null) {
            android.graphics.Paint paint = this.mShapeState.mPaint;
            if (paint.getXfermode() == null) {
                int alpha = paint.getAlpha();
                if (alpha == 0) {
                    return -2;
                }
                if (alpha == 255) {
                    return -1;
                }
                return -3;
            }
            return -3;
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mShapeState.mPaint.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        super.onBoundsChange(rect);
        updateShape();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        android.graphics.drawable.ShapeDrawable.ShapeState shapeState = this.mShapeState;
        if (shapeState.mTint != null && shapeState.mBlendMode != null) {
            this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, shapeState.mTint, shapeState.mBlendMode);
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        android.graphics.drawable.ShapeDrawable.ShapeState shapeState = this.mShapeState;
        return super.isStateful() || (shapeState.mTint != null && shapeState.mTint.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mShapeState.mTint != null && this.mShapeState.mTint.hasFocusStateSpecified();
    }

    protected boolean inflateTag(java.lang.String str, android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) {
        if (!"padding".equals(str)) {
            return false;
        }
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.ShapeDrawablePadding);
        setPadding(obtainAttributes.getDimensionPixelOffset(0, 0), obtainAttributes.getDimensionPixelOffset(1, 0), obtainAttributes.getDimensionPixelOffset(2, 0), obtainAttributes.getDimensionPixelOffset(3, 0));
        obtainAttributes.recycle();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.ShapeDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (!inflateTag(name, resources, xmlPullParser, attributeSet)) {
                    android.util.Log.w("drawable", "Unknown element: " + name + " for ShapeDrawable " + this);
                }
            }
        }
        updateLocalState();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.ShapeDrawable.ShapeState shapeState = this.mShapeState;
        if (shapeState == null) {
            return;
        }
        if (shapeState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(shapeState.mThemeAttrs, com.android.internal.R.styleable.ShapeDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (shapeState.mTint != null && shapeState.mTint.canApplyTheme()) {
            shapeState.mTint = shapeState.mTint.obtainForTheme(theme);
        }
        updateLocalState();
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.ShapeDrawable.ShapeState shapeState = this.mShapeState;
        android.graphics.Paint paint = shapeState.mPaint;
        shapeState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        shapeState.mThemeAttrs = typedArray.extractThemeAttrs();
        paint.setColor(typedArray.getColor(4, paint.getColor()));
        paint.setDither(typedArray.getBoolean(0, paint.isDither()));
        shapeState.mIntrinsicWidth = (int) typedArray.getDimension(3, shapeState.mIntrinsicWidth);
        shapeState.mIntrinsicHeight = (int) typedArray.getDimension(2, shapeState.mIntrinsicHeight);
        int i = typedArray.getInt(5, -1);
        if (i != -1) {
            shapeState.mBlendMode = android.graphics.drawable.Drawable.parseBlendMode(i, android.graphics.BlendMode.SRC_IN);
        }
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            shapeState.mTint = colorStateList;
        }
    }

    private void updateShape() {
        if (this.mShapeState.mShape != null) {
            android.graphics.Rect bounds = getBounds();
            int width = bounds.width();
            int height = bounds.height();
            this.mShapeState.mShape.resize(width, height);
            if (this.mShapeState.mShaderFactory != null) {
                this.mShapeState.mPaint.setShader(this.mShapeState.mShaderFactory.resize(width, height));
            }
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        if (this.mShapeState.mShape != null) {
            this.mShapeState.mShape.getOutline(outline);
            outline.setAlpha(getAlpha() / 255.0f);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mShapeState.mChangingConfigurations = getChangingConfigurations();
        return this.mShapeState;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mShapeState = new android.graphics.drawable.ShapeDrawable.ShapeState(this.mShapeState);
            updateLocalState();
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static final class ShapeState extends android.graphics.drawable.Drawable.ConstantState {
        int mAlpha;
        android.graphics.BlendMode mBlendMode;
        int mChangingConfigurations;
        int mIntrinsicHeight;
        int mIntrinsicWidth;
        android.graphics.Rect mPadding;
        final android.graphics.Paint mPaint;
        android.graphics.drawable.ShapeDrawable.ShaderFactory mShaderFactory;
        android.graphics.drawable.shapes.Shape mShape;
        int[] mThemeAttrs;
        android.content.res.ColorStateList mTint;

        ShapeState() {
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mAlpha = 255;
            this.mPaint = new android.graphics.Paint(1);
        }

        ShapeState(android.graphics.drawable.ShapeDrawable.ShapeState shapeState) {
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mAlpha = 255;
            this.mChangingConfigurations = shapeState.mChangingConfigurations;
            this.mPaint = new android.graphics.Paint(shapeState.mPaint);
            this.mThemeAttrs = shapeState.mThemeAttrs;
            if (shapeState.mShape != null) {
                try {
                    this.mShape = shapeState.mShape.mo1280clone();
                } catch (java.lang.CloneNotSupportedException e) {
                    this.mShape = shapeState.mShape;
                }
            }
            this.mTint = shapeState.mTint;
            this.mBlendMode = shapeState.mBlendMode;
            if (shapeState.mPadding != null) {
                this.mPadding = new android.graphics.Rect(shapeState.mPadding);
            }
            this.mIntrinsicWidth = shapeState.mIntrinsicWidth;
            this.mIntrinsicHeight = shapeState.mIntrinsicHeight;
            this.mAlpha = shapeState.mAlpha;
            this.mShaderFactory = shapeState.mShaderFactory;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mTint != null && this.mTint.canApplyTheme());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.ShapeDrawable(new android.graphics.drawable.ShapeDrawable.ShapeState(this), null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.ShapeDrawable(new android.graphics.drawable.ShapeDrawable.ShapeState(this), resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }
    }

    private ShapeDrawable(android.graphics.drawable.ShapeDrawable.ShapeState shapeState, android.content.res.Resources resources) {
        this.mShapeState = shapeState;
        updateLocalState();
    }

    private void updateLocalState() {
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mShapeState.mTint, this.mShapeState.mBlendMode);
    }
}
