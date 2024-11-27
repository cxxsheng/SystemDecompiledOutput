package android.graphics.drawable;

/* loaded from: classes.dex */
public class GradientDrawable extends android.graphics.drawable.Drawable {
    private static final float DEFAULT_INNER_RADIUS_RATIO = 3.0f;
    private static final float DEFAULT_THICKNESS_RATIO = 9.0f;
    public static final int LINE = 2;
    public static final int LINEAR_GRADIENT = 0;
    public static final int OVAL = 1;
    public static final int RADIAL_GRADIENT = 1;
    private static final int RADIUS_TYPE_FRACTION = 1;
    private static final int RADIUS_TYPE_FRACTION_PARENT = 2;
    private static final int RADIUS_TYPE_PIXELS = 0;
    public static final int RECTANGLE = 0;
    public static final int RING = 3;
    public static final int SWEEP_GRADIENT = 2;
    private int mAlpha;
    private android.graphics.BlendModeColorFilter mBlendModeColorFilter;
    private android.graphics.ColorFilter mColorFilter;
    private final android.graphics.Paint mFillPaint;
    private boolean mGradientIsDirty;
    private float mGradientRadius;
    private android.graphics.drawable.GradientDrawable.GradientState mGradientState;
    private android.graphics.Paint mLayerPaint;
    private boolean mMutated;
    private android.graphics.Rect mPadding;
    private final android.graphics.Path mPath;
    private boolean mPathIsDirty;
    private final android.graphics.RectF mRect;
    private android.graphics.Path mRingPath;
    private android.graphics.Paint mStrokePaint;
    public static boolean sWrapNegativeAngleMeasurements = true;
    private static final android.graphics.drawable.GradientDrawable.Orientation DEFAULT_ORIENTATION = android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GradientType {
    }

    public enum Orientation {
        TOP_BOTTOM,
        TR_BL,
        RIGHT_LEFT,
        BR_TL,
        BOTTOM_TOP,
        BL_TR,
        LEFT_RIGHT,
        TL_BR
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RadiusType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Shape {
    }

    public GradientDrawable() {
        this(new android.graphics.drawable.GradientDrawable.GradientState(DEFAULT_ORIENTATION, (int[]) null), (android.content.res.Resources) null);
    }

    public GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation orientation, int[] iArr) {
        this(new android.graphics.drawable.GradientDrawable.GradientState(orientation, iArr), (android.content.res.Resources) null);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(android.graphics.Rect rect) {
        if (this.mPadding != null) {
            rect.set(this.mPadding);
            return true;
        }
        return super.getPadding(rect);
    }

    public void setCornerRadii(float[] fArr) {
        this.mGradientState.setCornerRadii(fArr);
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float[] getCornerRadii() {
        float[] fArr = this.mGradientState.mRadiusArray;
        if (fArr == null) {
            return null;
        }
        return (float[]) fArr.clone();
    }

    public void setCornerRadius(float f) {
        this.mGradientState.setCornerRadius(f);
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float getCornerRadius() {
        return this.mGradientState.mRadius;
    }

    public void setStroke(int i, int i2) {
        setStroke(i, i2, 0.0f, 0.0f);
    }

    public void setStroke(int i, android.content.res.ColorStateList colorStateList) {
        setStroke(i, colorStateList, 0.0f, 0.0f);
    }

    public void setStroke(int i, int i2, float f, float f2) {
        this.mGradientState.setStroke(i, android.content.res.ColorStateList.valueOf(i2), f, f2);
        setStrokeInternal(i, i2, f, f2);
    }

    public void setStroke(int i, android.content.res.ColorStateList colorStateList, float f, float f2) {
        this.mGradientState.setStroke(i, colorStateList, f, f2);
        int i2 = 0;
        if (colorStateList != null) {
            i2 = colorStateList.getColorForState(getState(), 0);
        }
        setStrokeInternal(i, i2, f, f2);
    }

    private void setStrokeInternal(int i, int i2, float f, float f2) {
        android.graphics.DashPathEffect dashPathEffect;
        if (this.mStrokePaint == null) {
            this.mStrokePaint = new android.graphics.Paint(1);
            this.mStrokePaint.setStyle(android.graphics.Paint.Style.STROKE);
        }
        this.mStrokePaint.setStrokeWidth(i);
        this.mStrokePaint.setColor(i2);
        if (f <= 0.0f) {
            dashPathEffect = null;
        } else {
            dashPathEffect = new android.graphics.DashPathEffect(new float[]{f, f2}, 0.0f);
        }
        this.mStrokePaint.setPathEffect(dashPathEffect);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public void setSize(int i, int i2) {
        this.mGradientState.setSize(i, i2);
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public void setShape(int i) {
        this.mRingPath = null;
        this.mPathIsDirty = true;
        this.mGradientState.setShape(i);
        invalidateSelf();
    }

    public int getShape() {
        return this.mGradientState.mShape;
    }

    public void setGradientType(int i) {
        this.mGradientState.setGradientType(i);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public int getGradientType() {
        return this.mGradientState.mGradient;
    }

    public void setGradientCenter(float f, float f2) {
        this.mGradientState.setGradientCenter(f, f2);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public float getGradientCenterX() {
        return this.mGradientState.mCenterX;
    }

    public float getGradientCenterY() {
        return this.mGradientState.mCenterY;
    }

    public void setGradientRadius(float f) {
        this.mGradientState.setGradientRadius(f, 0);
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public float getGradientRadius() {
        if (this.mGradientState.mGradient != 1) {
            return 0.0f;
        }
        ensureValidRect();
        return this.mGradientRadius;
    }

    public void setUseLevel(boolean z) {
        this.mGradientState.mUseLevel = z;
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public boolean getUseLevel() {
        return this.mGradientState.mUseLevel;
    }

    private int modulateAlpha(int i) {
        return (i * (this.mAlpha + (this.mAlpha >> 7))) >> 8;
    }

    public android.graphics.drawable.GradientDrawable.Orientation getOrientation() {
        return this.mGradientState.mOrientation;
    }

    public void setOrientation(android.graphics.drawable.GradientDrawable.Orientation orientation) {
        this.mGradientState.mOrientation = orientation;
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public void setColors(int[] iArr) {
        setColors(iArr, null);
    }

    public void setColors(int[] iArr, float[] fArr) {
        this.mGradientState.setGradientColors(iArr);
        this.mGradientState.mPositions = fArr;
        this.mGradientIsDirty = true;
        invalidateSelf();
    }

    public int[] getColors() {
        if (this.mGradientState.mGradientColors == null) {
            return null;
        }
        int[] iArr = new int[this.mGradientState.mGradientColors.length];
        for (int i = 0; i < this.mGradientState.mGradientColors.length; i++) {
            if (this.mGradientState.mGradientColors[i] != null) {
                iArr[i] = this.mGradientState.mGradientColors[i].getDefaultColor();
            }
        }
        return iArr;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (!ensureValidRect()) {
            return;
        }
        int alpha = this.mFillPaint.getAlpha();
        int alpha2 = this.mStrokePaint != null ? this.mStrokePaint.getAlpha() : 0;
        int modulateAlpha = modulateAlpha(alpha);
        int modulateAlpha2 = modulateAlpha(alpha2);
        boolean z = modulateAlpha2 > 0 && this.mStrokePaint != null && this.mStrokePaint.getStrokeWidth() > 0.0f;
        boolean z2 = modulateAlpha > 0;
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        android.graphics.ColorFilter colorFilter = this.mColorFilter != null ? this.mColorFilter : this.mBlendModeColorFilter;
        boolean z3 = z && z2 && gradientState.mShape != 2 && modulateAlpha2 < 255 && (this.mAlpha < 255 || colorFilter != null);
        if (z3) {
            if (this.mLayerPaint == null) {
                this.mLayerPaint = new android.graphics.Paint();
            }
            this.mLayerPaint.setDither(gradientState.mDither);
            this.mLayerPaint.setAlpha(this.mAlpha);
            this.mLayerPaint.setColorFilter(colorFilter);
            float strokeWidth = this.mStrokePaint.getStrokeWidth();
            canvas.saveLayer(this.mRect.left - strokeWidth, this.mRect.top - strokeWidth, this.mRect.right + strokeWidth, this.mRect.bottom + strokeWidth, this.mLayerPaint);
            this.mFillPaint.setColorFilter(null);
            this.mStrokePaint.setColorFilter(null);
        } else {
            this.mFillPaint.setAlpha(modulateAlpha);
            this.mFillPaint.setDither(gradientState.mDither);
            this.mFillPaint.setColorFilter(colorFilter);
            if (colorFilter != null && gradientState.mSolidColors == null) {
                this.mFillPaint.setColor(this.mAlpha << 24);
            }
            if (z) {
                this.mStrokePaint.setAlpha(modulateAlpha2);
                this.mStrokePaint.setDither(gradientState.mDither);
                this.mStrokePaint.setColorFilter(colorFilter);
            }
        }
        switch (gradientState.mShape) {
            case 0:
                if (gradientState.mRadiusArray == null) {
                    if (gradientState.mRadius > 0.0f) {
                        float min = java.lang.Math.min(gradientState.mRadius, java.lang.Math.min(this.mRect.width(), this.mRect.height()) * 0.5f);
                        canvas.drawRoundRect(this.mRect, min, min, this.mFillPaint);
                        if (z) {
                            canvas.drawRoundRect(this.mRect, min, min, this.mStrokePaint);
                            break;
                        }
                    } else {
                        if (this.mFillPaint.getColor() != 0 || colorFilter != null || this.mFillPaint.getShader() != null) {
                            canvas.drawRect(this.mRect, this.mFillPaint);
                        }
                        if (z) {
                            canvas.drawRect(this.mRect, this.mStrokePaint);
                            break;
                        }
                    }
                } else {
                    buildPathIfDirty();
                    canvas.drawPath(this.mPath, this.mFillPaint);
                    if (z) {
                        canvas.drawPath(this.mPath, this.mStrokePaint);
                        break;
                    }
                }
                break;
            case 1:
                canvas.drawOval(this.mRect, this.mFillPaint);
                if (z) {
                    canvas.drawOval(this.mRect, this.mStrokePaint);
                    break;
                }
                break;
            case 2:
                android.graphics.RectF rectF = this.mRect;
                float centerY = rectF.centerY();
                if (z) {
                    canvas.drawLine(rectF.left, centerY, rectF.right, centerY, this.mStrokePaint);
                    break;
                }
                break;
            case 3:
                android.graphics.Path buildRing = buildRing(gradientState);
                canvas.drawPath(buildRing, this.mFillPaint);
                if (z) {
                    canvas.drawPath(buildRing, this.mStrokePaint);
                    break;
                }
                break;
        }
        if (z3) {
            canvas.restore();
            return;
        }
        this.mFillPaint.setAlpha(alpha);
        if (z) {
            this.mStrokePaint.setAlpha(alpha2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setXfermode(android.graphics.Xfermode xfermode) {
        super.setXfermode(xfermode);
        this.mFillPaint.setXfermode(xfermode);
    }

    public void setAntiAlias(boolean z) {
        this.mFillPaint.setAntiAlias(z);
    }

    private void buildPathIfDirty() {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        if (this.mPathIsDirty) {
            ensureValidRect();
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, gradientState.mRadiusArray, android.graphics.Path.Direction.CW);
            this.mPathIsDirty = false;
        }
    }

    public void setInnerRadiusRatio(float f) {
        if (f <= 0.0f) {
            throw new java.lang.IllegalArgumentException("Ratio must be greater than zero");
        }
        this.mGradientState.mInnerRadiusRatio = f;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float getInnerRadiusRatio() {
        return this.mGradientState.mInnerRadiusRatio;
    }

    public void setInnerRadius(int i) {
        this.mGradientState.mInnerRadius = i;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public int getInnerRadius() {
        return this.mGradientState.mInnerRadius;
    }

    public void setThicknessRatio(float f) {
        if (f <= 0.0f) {
            throw new java.lang.IllegalArgumentException("Ratio must be greater than zero");
        }
        this.mGradientState.mThicknessRatio = f;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public float getThicknessRatio() {
        return this.mGradientState.mThicknessRatio;
    }

    public void setThickness(int i) {
        this.mGradientState.mThickness = i;
        this.mPathIsDirty = true;
        invalidateSelf();
    }

    public int getThickness() {
        return this.mGradientState.mThickness;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if (this.mGradientState.mPadding == null) {
            this.mGradientState.mPadding = new android.graphics.Rect();
        }
        this.mGradientState.mPadding.set(i, i2, i3, i4);
        this.mPadding = this.mGradientState.mPadding;
        invalidateSelf();
    }

    private android.graphics.Path buildRing(android.graphics.drawable.GradientDrawable.GradientState gradientState) {
        if (this.mRingPath != null && (!gradientState.mUseLevelForShape || !this.mPathIsDirty)) {
            return this.mRingPath;
        }
        this.mPathIsDirty = false;
        float level = gradientState.mUseLevelForShape ? (getLevel() * 360.0f) / 10000.0f : 360.0f;
        android.graphics.RectF rectF = new android.graphics.RectF(this.mRect);
        float width = rectF.width() / 2.0f;
        float height = rectF.height() / 2.0f;
        float width2 = gradientState.mThickness != -1 ? gradientState.mThickness : rectF.width() / gradientState.mThicknessRatio;
        float width3 = gradientState.mInnerRadius != -1 ? gradientState.mInnerRadius : rectF.width() / gradientState.mInnerRadiusRatio;
        android.graphics.RectF rectF2 = new android.graphics.RectF(rectF);
        rectF2.inset(width - width3, height - width3);
        android.graphics.RectF rectF3 = new android.graphics.RectF(rectF2);
        float f = -width2;
        rectF3.inset(f, f);
        if (this.mRingPath == null) {
            this.mRingPath = new android.graphics.Path();
        } else {
            this.mRingPath.reset();
        }
        android.graphics.Path path = this.mRingPath;
        if (level < 360.0f && level > -360.0f) {
            path.setFillType(android.graphics.Path.FillType.EVEN_ODD);
            float f2 = width + width3;
            path.moveTo(f2, height);
            path.lineTo(f2 + width2, height);
            path.arcTo(rectF3, 0.0f, level, false);
            path.arcTo(rectF2, level, -level, false);
            path.close();
        } else {
            path.addOval(rectF3, android.graphics.Path.Direction.CW);
            path.addOval(rectF2, android.graphics.Path.Direction.CCW);
        }
        return path;
    }

    public void setColor(int i) {
        this.mGradientState.setSolidColors(android.content.res.ColorStateList.valueOf(i));
        this.mFillPaint.setColor(i);
        invalidateSelf();
    }

    public void setColor(android.content.res.ColorStateList colorStateList) {
        if (colorStateList == null) {
            setColor(0);
            return;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        this.mGradientState.setSolidColors(colorStateList);
        this.mFillPaint.setColor(colorForState);
        invalidateSelf();
    }

    public android.content.res.ColorStateList getColor() {
        return this.mGradientState.mSolidColors;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        android.content.res.ColorStateList colorStateList;
        int colorForState;
        int colorForState2;
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        android.content.res.ColorStateList colorStateList2 = gradientState.mSolidColors;
        if (colorStateList2 != null && this.mFillPaint.getColor() != (colorForState2 = colorStateList2.getColorForState(iArr, 0))) {
            this.mFillPaint.setColor(colorForState2);
            z = true;
        } else {
            z = false;
        }
        android.graphics.Paint paint = this.mStrokePaint;
        if (paint != null && (colorStateList = gradientState.mStrokeColors) != null && paint.getColor() != (colorForState = colorStateList.getColorForState(iArr, 0))) {
            paint.setColor(colorForState);
            z = true;
        }
        if (gradientState.mTint != null && gradientState.mBlendMode != null) {
            this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, gradientState.mTint, gradientState.mBlendMode);
            z = true;
        }
        if (!z) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        return super.isStateful() || (gradientState.mSolidColors != null && gradientState.mSolidColors.isStateful()) || ((gradientState.mStrokeColors != null && gradientState.mStrokeColors.isStateful()) || (gradientState.mTint != null && gradientState.mTint.isStateful()));
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        return (gradientState.mSolidColors != null && gradientState.mSolidColors.hasFocusStateSpecified()) || (gradientState.mStrokeColors != null && gradientState.mStrokeColors.hasFocusStateSpecified()) || (gradientState.mTint != null && gradientState.mTint.hasFocusStateSpecified());
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mGradientState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        if (z != this.mGradientState.mDither) {
            this.mGradientState.mDither = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        if (colorFilter != this.mColorFilter) {
            this.mColorFilter = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        this.mGradientState.mTint = colorStateList;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, this.mGradientState.mBlendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mGradientState.mBlendMode = blendMode;
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, this.mGradientState.mTint, blendMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return (this.mAlpha == 255 && this.mGradientState.mOpaqueOverBounds && isOpaqueForState()) ? -1 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        super.onBoundsChange(rect);
        this.mRingPath = null;
        this.mPathIsDirty = true;
        this.mGradientIsDirty = true;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        super.onLevelChange(i);
        this.mGradientIsDirty = true;
        this.mPathIsDirty = true;
        invalidateSelf();
        return true;
    }

    private boolean ensureValidRect() {
        float f;
        int[] iArr;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        if (this.mGradientIsDirty) {
            this.mGradientIsDirty = false;
            android.graphics.Rect bounds = getBounds();
            if (this.mStrokePaint == null) {
                f = 0.0f;
            } else {
                f = this.mStrokePaint.getStrokeWidth() * 0.5f;
            }
            android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
            this.mRect.set(bounds.left + f, bounds.top + f, bounds.right - f, bounds.bottom - f);
            float[] fArr = null;
            if (gradientState.mGradientColors == null) {
                iArr = null;
            } else {
                int length = gradientState.mGradientColors.length;
                iArr = new int[length];
                for (int i = 0; i < length; i++) {
                    if (gradientState.mGradientColors[i] != null) {
                        iArr[i] = gradientState.mGradientColors[i].getDefaultColor();
                    }
                }
            }
            if (iArr != null) {
                android.graphics.RectF rectF = this.mRect;
                if (gradientState.mGradient == 0) {
                    float level = gradientState.mUseLevel ? getLevel() / 10000.0f : 1.0f;
                    switch (gradientState.mOrientation) {
                        case TOP_BOTTOM:
                            f3 = rectF.left;
                            f4 = rectF.top;
                            f5 = level * rectF.bottom;
                            f6 = f3;
                            break;
                        case TR_BL:
                            float f7 = rectF.right;
                            f4 = rectF.top;
                            f3 = rectF.left * level;
                            f5 = level * rectF.bottom;
                            f6 = f7;
                            break;
                        case RIGHT_LEFT:
                            float f8 = rectF.right;
                            f4 = rectF.top;
                            f5 = f4;
                            f3 = level * rectF.left;
                            f6 = f8;
                            break;
                        case BR_TL:
                            float f9 = rectF.right;
                            f4 = rectF.bottom;
                            f3 = rectF.left * level;
                            f5 = level * rectF.top;
                            f6 = f9;
                            break;
                        case BOTTOM_TOP:
                            f3 = rectF.left;
                            f4 = rectF.bottom;
                            f5 = level * rectF.top;
                            f6 = f3;
                            break;
                        case BL_TR:
                            float f10 = rectF.left;
                            f4 = rectF.bottom;
                            f3 = rectF.right * level;
                            f5 = level * rectF.top;
                            f6 = f10;
                            break;
                        case LEFT_RIGHT:
                            float f11 = rectF.left;
                            f4 = rectF.top;
                            f5 = f4;
                            f3 = level * rectF.right;
                            f6 = f11;
                            break;
                        default:
                            float f12 = rectF.left;
                            f4 = rectF.top;
                            f3 = rectF.right * level;
                            f5 = level * rectF.bottom;
                            f6 = f12;
                            break;
                    }
                    this.mFillPaint.setShader(new android.graphics.LinearGradient(f6, f4, f3, f5, iArr, gradientState.mPositions, android.graphics.Shader.TileMode.CLAMP));
                } else if (gradientState.mGradient == 1) {
                    float f13 = rectF.left + ((rectF.right - rectF.left) * gradientState.mCenterX);
                    float f14 = rectF.top + ((rectF.bottom - rectF.top) * gradientState.mCenterY);
                    float f15 = gradientState.mGradientRadius;
                    if (gradientState.mGradientRadiusType == 1) {
                        f15 *= java.lang.Math.min(gradientState.mWidth >= 0 ? gradientState.mWidth : rectF.width(), gradientState.mHeight >= 0 ? gradientState.mHeight : rectF.height());
                    } else if (gradientState.mGradientRadiusType == 2) {
                        f15 *= java.lang.Math.min(rectF.width(), rectF.height());
                    }
                    if (gradientState.mUseLevel) {
                        f15 *= getLevel() / 10000.0f;
                    }
                    this.mGradientRadius = f15;
                    if (f15 > 0.0f) {
                        f2 = f15;
                    } else {
                        f2 = 0.001f;
                    }
                    this.mFillPaint.setShader(new android.graphics.RadialGradient(f13, f14, f2, iArr, (float[]) null, android.graphics.Shader.TileMode.CLAMP));
                } else if (gradientState.mGradient == 2) {
                    float f16 = rectF.left + ((rectF.right - rectF.left) * gradientState.mCenterX);
                    float f17 = rectF.top + ((rectF.bottom - rectF.top) * gradientState.mCenterY);
                    if (gradientState.mUseLevel) {
                        int[] iArr2 = gradientState.mTempColors;
                        int length2 = iArr.length;
                        if (iArr2 == null || iArr2.length != length2 + 1) {
                            iArr2 = new int[length2 + 1];
                            gradientState.mTempColors = iArr2;
                        }
                        java.lang.System.arraycopy(iArr, 0, iArr2, 0, length2);
                        int i2 = length2 - 1;
                        iArr2[length2] = iArr[i2];
                        float[] fArr2 = gradientState.mTempPositions;
                        float f18 = 1.0f / i2;
                        if (fArr2 == null || fArr2.length != length2 + 1) {
                            fArr2 = new float[length2 + 1];
                            gradientState.mTempPositions = fArr2;
                        }
                        float level2 = getLevel() / 10000.0f;
                        for (int i3 = 0; i3 < length2; i3++) {
                            fArr2[i3] = i3 * f18 * level2;
                        }
                        fArr2[length2] = 1.0f;
                        fArr = fArr2;
                        iArr = iArr2;
                    }
                    this.mFillPaint.setShader(new android.graphics.SweepGradient(f16, f17, iArr, fArr));
                }
                if (gradientState.mSolidColors == null) {
                    this.mFillPaint.setColor(-16777216);
                }
            }
        }
        return !this.mRect.isEmpty();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.mGradientState.setDensity(android.graphics.drawable.Drawable.resolveDensity(resources, 0));
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        updateLocalState(resources);
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        if (gradientState == null) {
            return;
        }
        gradientState.setDensity(android.graphics.drawable.Drawable.resolveDensity(theme.getResources(), 0));
        if (gradientState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(gradientState.mThemeAttrs, com.android.internal.R.styleable.GradientDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (gradientState.mTint != null && gradientState.mTint.canApplyTheme()) {
            gradientState.mTint = gradientState.mTint.obtainForTheme(theme);
        }
        if (gradientState.mSolidColors != null && gradientState.mSolidColors.canApplyTheme()) {
            gradientState.mSolidColors = gradientState.mSolidColors.obtainForTheme(theme);
        }
        if (gradientState.mStrokeColors != null && gradientState.mStrokeColors.canApplyTheme()) {
            gradientState.mStrokeColors = gradientState.mStrokeColors.obtainForTheme(theme);
        }
        if (gradientState.mGradientColors != null) {
            for (int i = 0; i < gradientState.mGradientColors.length; i++) {
                if (gradientState.mGradientColors[i] != null && gradientState.mGradientColors[i].canApplyTheme()) {
                    gradientState.mGradientColors[i] = gradientState.mGradientColors[i].obtainForTheme(theme);
                }
            }
        }
        applyThemeChildElements(theme);
        updateLocalState(theme.getResources());
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mThemeAttrs = typedArray.extractThemeAttrs();
        gradientState.mShape = typedArray.getInt(3, gradientState.mShape);
        gradientState.mDither = typedArray.getBoolean(0, gradientState.mDither);
        if (gradientState.mShape == 3) {
            gradientState.mInnerRadius = typedArray.getDimensionPixelSize(7, gradientState.mInnerRadius);
            if (gradientState.mInnerRadius == -1) {
                gradientState.mInnerRadiusRatio = typedArray.getFloat(4, gradientState.mInnerRadiusRatio);
            }
            gradientState.mThickness = typedArray.getDimensionPixelSize(8, gradientState.mThickness);
            if (gradientState.mThickness == -1) {
                gradientState.mThicknessRatio = typedArray.getFloat(5, gradientState.mThicknessRatio);
            }
            gradientState.mUseLevelForShape = typedArray.getBoolean(6, gradientState.mUseLevelForShape);
        }
        int i = typedArray.getInt(9, -1);
        if (i != -1) {
            gradientState.mBlendMode = android.graphics.drawable.Drawable.parseBlendMode(i, android.graphics.BlendMode.SRC_IN);
        }
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            gradientState.mTint = colorStateList;
        }
        gradientState.mOpticalInsets = android.graphics.Insets.of(typedArray.getDimensionPixelSize(10, gradientState.mOpticalInsets.left), typedArray.getDimensionPixelSize(11, gradientState.mOpticalInsets.top), typedArray.getDimensionPixelSize(12, gradientState.mOpticalInsets.right), typedArray.getDimensionPixelSize(13, gradientState.mOpticalInsets.bottom));
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return (this.mGradientState != null && this.mGradientState.canApplyTheme()) || super.canApplyTheme();
    }

    private void applyThemeChildElements(android.content.res.Resources.Theme theme) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        if (gradientState.mAttrSize != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(gradientState.mAttrSize, com.android.internal.R.styleable.GradientDrawableSize);
            updateGradientDrawableSize(resolveAttributes);
            resolveAttributes.recycle();
        }
        if (gradientState.mAttrGradient != null) {
            android.content.res.TypedArray resolveAttributes2 = theme.resolveAttributes(gradientState.mAttrGradient, com.android.internal.R.styleable.GradientDrawableGradient);
            try {
                updateGradientDrawableGradient(theme.getResources(), resolveAttributes2);
                resolveAttributes2.recycle();
            } finally {
                resolveAttributes2.recycle();
            }
        }
        if (gradientState.mAttrSolid != null) {
            android.content.res.TypedArray resolveAttributes3 = theme.resolveAttributes(gradientState.mAttrSolid, com.android.internal.R.styleable.GradientDrawableSolid);
            updateGradientDrawableSolid(resolveAttributes3);
            resolveAttributes3.recycle();
        }
        if (gradientState.mAttrStroke != null) {
            android.content.res.TypedArray resolveAttributes4 = theme.resolveAttributes(gradientState.mAttrStroke, com.android.internal.R.styleable.GradientDrawableStroke);
            updateGradientDrawableStroke(resolveAttributes4);
            resolveAttributes4.recycle();
        }
        if (gradientState.mAttrCorners != null) {
            updateDrawableCorners(theme.resolveAttributes(gradientState.mAttrCorners, com.android.internal.R.styleable.DrawableCorners));
        }
        if (gradientState.mAttrPadding != null) {
            updateGradientDrawablePadding(theme.resolveAttributes(gradientState.mAttrPadding, com.android.internal.R.styleable.GradientDrawablePadding));
        }
    }

    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 >= depth || next != 3) {
                    if (next == 2 && depth2 <= depth) {
                        java.lang.String name = xmlPullParser.getName();
                        if (name.equals("size")) {
                            android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientDrawableSize);
                            updateGradientDrawableSize(obtainAttributes);
                            obtainAttributes.recycle();
                        } else if (name.equals("gradient")) {
                            android.content.res.TypedArray obtainAttributes2 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientDrawableGradient);
                            updateGradientDrawableGradient(resources, obtainAttributes2);
                            obtainAttributes2.recycle();
                        } else if (name.equals("solid")) {
                            android.content.res.TypedArray obtainAttributes3 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientDrawableSolid);
                            updateGradientDrawableSolid(obtainAttributes3);
                            obtainAttributes3.recycle();
                        } else if (name.equals("stroke")) {
                            android.content.res.TypedArray obtainAttributes4 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientDrawableStroke);
                            updateGradientDrawableStroke(obtainAttributes4);
                            obtainAttributes4.recycle();
                        } else if (name.equals("corners")) {
                            android.content.res.TypedArray obtainAttributes5 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.DrawableCorners);
                            updateDrawableCorners(obtainAttributes5);
                            obtainAttributes5.recycle();
                        } else if (name.equals("padding")) {
                            android.content.res.TypedArray obtainAttributes6 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.GradientDrawablePadding);
                            updateGradientDrawablePadding(obtainAttributes6);
                            obtainAttributes6.recycle();
                        } else {
                            android.util.Log.w("drawable", "Bad element under <shape>: " + name);
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void updateGradientDrawablePadding(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrPadding = typedArray.extractThemeAttrs();
        if (gradientState.mPadding == null) {
            gradientState.mPadding = new android.graphics.Rect();
        }
        android.graphics.Rect rect = gradientState.mPadding;
        rect.set(typedArray.getDimensionPixelOffset(0, rect.left), typedArray.getDimensionPixelOffset(1, rect.top), typedArray.getDimensionPixelOffset(2, rect.right), typedArray.getDimensionPixelOffset(3, rect.bottom));
        this.mPadding = rect;
    }

    private void updateDrawableCorners(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrCorners = typedArray.extractThemeAttrs();
        int dimensionPixelSize = typedArray.getDimensionPixelSize(0, (int) gradientState.mRadius);
        setCornerRadius(dimensionPixelSize);
        int dimensionPixelSize2 = typedArray.getDimensionPixelSize(1, dimensionPixelSize);
        int dimensionPixelSize3 = typedArray.getDimensionPixelSize(2, dimensionPixelSize);
        int dimensionPixelSize4 = typedArray.getDimensionPixelSize(3, dimensionPixelSize);
        int dimensionPixelSize5 = typedArray.getDimensionPixelSize(4, dimensionPixelSize);
        if (dimensionPixelSize2 != dimensionPixelSize || dimensionPixelSize3 != dimensionPixelSize || dimensionPixelSize4 != dimensionPixelSize || dimensionPixelSize5 != dimensionPixelSize) {
            float f = dimensionPixelSize2;
            float f2 = dimensionPixelSize3;
            float f3 = dimensionPixelSize5;
            float f4 = dimensionPixelSize4;
            setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
        }
    }

    private void updateGradientDrawableStroke(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrStroke = typedArray.extractThemeAttrs();
        int dimensionPixelSize = typedArray.getDimensionPixelSize(0, java.lang.Math.max(0, gradientState.mStrokeWidth));
        float dimension = typedArray.getDimension(2, gradientState.mStrokeDashWidth);
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList == null) {
            colorStateList = gradientState.mStrokeColors;
        }
        if (dimension != 0.0f) {
            setStroke(dimensionPixelSize, colorStateList, dimension, typedArray.getDimension(3, gradientState.mStrokeDashGap));
        } else {
            setStroke(dimensionPixelSize, colorStateList);
        }
    }

    private void updateGradientDrawableSolid(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrSolid = typedArray.extractThemeAttrs();
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(0);
        if (colorStateList != null) {
            setColor(colorStateList);
        }
    }

    private void updateGradientDrawableGradient(android.content.res.Resources resources, android.content.res.TypedArray typedArray) {
        boolean z;
        boolean z2;
        float f;
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrGradient = typedArray.extractThemeAttrs();
        gradientState.mCenterX = getFloatOrFraction(typedArray, 5, gradientState.mCenterX);
        gradientState.mCenterY = getFloatOrFraction(typedArray, 6, gradientState.mCenterY);
        gradientState.mUseLevel = typedArray.getBoolean(2, gradientState.mUseLevel);
        gradientState.mGradient = typedArray.getInt(4, gradientState.mGradient);
        int i = 0;
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(0);
        android.content.res.ColorStateList colorStateList2 = typedArray.getColorStateList(8);
        android.content.res.ColorStateList colorStateList3 = typedArray.getColorStateList(1);
        if (gradientState.mGradientColors == null) {
            z = false;
        } else {
            z = true;
        }
        boolean hasCenterColor = gradientState.hasCenterColor();
        int defaultColor = colorStateList != null ? colorStateList.getDefaultColor() : 0;
        int defaultColor2 = colorStateList2 != null ? colorStateList2.getDefaultColor() : 0;
        int defaultColor3 = colorStateList3 != null ? colorStateList3.getDefaultColor() : 0;
        if (z && gradientState.mGradientColors[0] != null) {
            defaultColor = gradientState.mGradientColors[0].getDefaultColor();
        }
        if (hasCenterColor && gradientState.mGradientColors[1] != null) {
            defaultColor2 = gradientState.mGradientColors[1].getDefaultColor();
        }
        if (hasCenterColor && gradientState.mGradientColors[2] != null) {
            defaultColor3 = gradientState.mGradientColors[2].getDefaultColor();
        } else if (z && gradientState.mGradientColors[1] != null) {
            defaultColor3 = gradientState.mGradientColors[1].getDefaultColor();
        }
        if (!typedArray.hasValue(8) && !hasCenterColor) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            gradientState.mGradientColors = new android.content.res.ColorStateList[3];
            android.content.res.ColorStateList[] colorStateListArr = gradientState.mGradientColors;
            if (colorStateList == null) {
                colorStateList = android.content.res.ColorStateList.valueOf(defaultColor);
            }
            colorStateListArr[0] = colorStateList;
            android.content.res.ColorStateList[] colorStateListArr2 = gradientState.mGradientColors;
            if (colorStateList2 == null) {
                colorStateList2 = android.content.res.ColorStateList.valueOf(defaultColor2);
            }
            colorStateListArr2[1] = colorStateList2;
            android.content.res.ColorStateList[] colorStateListArr3 = gradientState.mGradientColors;
            if (colorStateList3 == null) {
                colorStateList3 = android.content.res.ColorStateList.valueOf(defaultColor3);
            }
            colorStateListArr3[2] = colorStateList3;
            gradientState.mPositions = new float[3];
            gradientState.mPositions[0] = 0.0f;
            gradientState.mPositions[1] = gradientState.mCenterX != 0.5f ? gradientState.mCenterX : gradientState.mCenterY;
            gradientState.mPositions[2] = 1.0f;
        } else {
            gradientState.mGradientColors = new android.content.res.ColorStateList[2];
            android.content.res.ColorStateList[] colorStateListArr4 = gradientState.mGradientColors;
            if (colorStateList == null) {
                colorStateList = android.content.res.ColorStateList.valueOf(defaultColor);
            }
            colorStateListArr4[0] = colorStateList;
            android.content.res.ColorStateList[] colorStateListArr5 = gradientState.mGradientColors;
            if (colorStateList3 == null) {
                colorStateList3 = android.content.res.ColorStateList.valueOf(defaultColor3);
            }
            colorStateListArr5[1] = colorStateList3;
        }
        int i2 = (int) typedArray.getFloat(3, gradientState.mAngle);
        if (sWrapNegativeAngleMeasurements) {
            gradientState.mAngle = ((i2 % 360) + 360) % 360;
        } else {
            gradientState.mAngle = i2 % 360;
        }
        if (gradientState.mAngle >= 0) {
            switch (gradientState.mAngle) {
                case 0:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT;
                    break;
                case 45:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.BL_TR;
                    break;
                case 90:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP;
                    break;
                case 135:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.BR_TL;
                    break;
                case 180:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.RIGHT_LEFT;
                    break;
                case 225:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.TR_BL;
                    break;
                case 270:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;
                    break;
                case 315:
                    gradientState.mOrientation = android.graphics.drawable.GradientDrawable.Orientation.TL_BR;
                    break;
            }
        } else {
            gradientState.mOrientation = DEFAULT_ORIENTATION;
        }
        android.util.TypedValue peekValue = typedArray.peekValue(7);
        if (peekValue != null) {
            if (peekValue.type == 6) {
                f = peekValue.getFraction(1.0f, 1.0f);
                i = ((peekValue.data >> 0) & 15) != 1 ? 1 : 2;
            } else if (peekValue.type == 5) {
                f = peekValue.getDimension(resources.getDisplayMetrics());
            } else {
                f = peekValue.getFloat();
            }
            gradientState.mGradientRadius = f;
            gradientState.mGradientRadiusType = i;
        }
    }

    private void updateGradientDrawableSize(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        gradientState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        gradientState.mAttrSize = typedArray.extractThemeAttrs();
        gradientState.mWidth = typedArray.getDimensionPixelSize(1, gradientState.mWidth);
        gradientState.mHeight = typedArray.getDimensionPixelSize(0, gradientState.mHeight);
    }

    private static float getFloatOrFraction(android.content.res.TypedArray typedArray, int i, float f) {
        android.util.TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue != null) {
            return peekValue.type == 6 ? peekValue.getFraction(1.0f, 1.0f) : peekValue.getFloat();
        }
        return f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mGradientState.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mGradientState.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Insets getOpticalInsets() {
        return this.mGradientState.mOpticalInsets;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mGradientState.mChangingConfigurations = getChangingConfigurations();
        return this.mGradientState;
    }

    private boolean isOpaqueForState() {
        if (this.mGradientState.mStrokeWidth < 0 || this.mStrokePaint == null || isOpaque(this.mStrokePaint.getColor())) {
            return this.mGradientState.mGradientColors != null || isOpaque(this.mFillPaint.getColor());
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        float f;
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        android.graphics.Rect bounds = getBounds();
        float f2 = 0.0f;
        if (gradientState.mOpaqueOverShape && (this.mGradientState.mStrokeWidth <= 0 || this.mStrokePaint == null || this.mStrokePaint.getAlpha() == this.mFillPaint.getAlpha())) {
            f = modulateAlpha(this.mFillPaint.getAlpha()) / 255.0f;
        } else {
            f = 0.0f;
        }
        outline.setAlpha(f);
        switch (gradientState.mShape) {
            case 0:
                if (gradientState.mRadiusArray != null) {
                    buildPathIfDirty();
                    outline.setPath(this.mPath);
                    break;
                } else {
                    if (gradientState.mRadius > 0.0f) {
                        f2 = java.lang.Math.min(gradientState.mRadius, java.lang.Math.min(bounds.width(), bounds.height()) * 0.5f);
                    }
                    outline.setRoundRect(bounds, f2);
                    break;
                }
            case 1:
                outline.setOval(bounds);
                break;
            case 2:
                float strokeWidth = this.mStrokePaint == null ? 1.0E-4f : this.mStrokePaint.getStrokeWidth() * 0.5f;
                float centerY = bounds.centerY();
                outline.setRect(bounds.left, (int) java.lang.Math.floor(centerY - strokeWidth), bounds.right, (int) java.lang.Math.ceil(centerY + strokeWidth));
                break;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mGradientState = new android.graphics.drawable.GradientDrawable.GradientState(this.mGradientState, (android.content.res.Resources) null);
            updateLocalState(null);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static final class GradientState extends android.graphics.drawable.Drawable.ConstantState {
        public int mAngle;
        int[] mAttrCorners;
        int[] mAttrGradient;
        int[] mAttrPadding;
        int[] mAttrSize;
        int[] mAttrSolid;
        int[] mAttrStroke;
        android.graphics.BlendMode mBlendMode;
        float mCenterX;
        float mCenterY;
        public int mChangingConfigurations;
        int mDensity;
        public boolean mDither;
        public int mGradient;
        public android.content.res.ColorStateList[] mGradientColors;
        float mGradientRadius;
        int mGradientRadiusType;
        public int mHeight;
        public int mInnerRadius;
        public float mInnerRadiusRatio;
        boolean mOpaqueOverBounds;
        boolean mOpaqueOverShape;
        public android.graphics.Insets mOpticalInsets;
        public android.graphics.drawable.GradientDrawable.Orientation mOrientation;
        public android.graphics.Rect mPadding;
        public float[] mPositions;
        public float mRadius;
        public float[] mRadiusArray;
        public int mShape;
        public android.content.res.ColorStateList mSolidColors;
        public android.content.res.ColorStateList mStrokeColors;
        public float mStrokeDashGap;
        public float mStrokeDashWidth;
        public int mStrokeWidth;
        public int[] mTempColors;
        public float[] mTempPositions;
        int[] mThemeAttrs;
        public int mThickness;
        public float mThicknessRatio;
        android.content.res.ColorStateList mTint;
        boolean mUseLevel;
        boolean mUseLevelForShape;
        public int mWidth;

        public GradientState(android.graphics.drawable.GradientDrawable.Orientation orientation, int[] iArr) {
            this.mShape = 0;
            this.mGradient = 0;
            this.mAngle = 0;
            this.mStrokeWidth = -1;
            this.mStrokeDashWidth = 0.0f;
            this.mStrokeDashGap = 0.0f;
            this.mRadius = 0.0f;
            this.mRadiusArray = null;
            this.mPadding = null;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mInnerRadiusRatio = 3.0f;
            this.mThicknessRatio = android.graphics.drawable.GradientDrawable.DEFAULT_THICKNESS_RATIO;
            this.mInnerRadius = -1;
            this.mThickness = -1;
            this.mDither = false;
            this.mOpticalInsets = android.graphics.Insets.NONE;
            this.mCenterX = 0.5f;
            this.mCenterY = 0.5f;
            this.mGradientRadius = 0.5f;
            this.mGradientRadiusType = 0;
            this.mUseLevel = false;
            this.mUseLevelForShape = true;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mDensity = 160;
            this.mOrientation = orientation;
            setGradientColors(iArr);
        }

        public GradientState(android.graphics.drawable.GradientDrawable.GradientState gradientState, android.content.res.Resources resources) {
            this.mShape = 0;
            this.mGradient = 0;
            this.mAngle = 0;
            this.mStrokeWidth = -1;
            this.mStrokeDashWidth = 0.0f;
            this.mStrokeDashGap = 0.0f;
            this.mRadius = 0.0f;
            this.mRadiusArray = null;
            this.mPadding = null;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mInnerRadiusRatio = 3.0f;
            this.mThicknessRatio = android.graphics.drawable.GradientDrawable.DEFAULT_THICKNESS_RATIO;
            this.mInnerRadius = -1;
            this.mThickness = -1;
            this.mDither = false;
            this.mOpticalInsets = android.graphics.Insets.NONE;
            this.mCenterX = 0.5f;
            this.mCenterY = 0.5f;
            this.mGradientRadius = 0.5f;
            this.mGradientRadiusType = 0;
            this.mUseLevel = false;
            this.mUseLevelForShape = true;
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mDensity = 160;
            this.mChangingConfigurations = gradientState.mChangingConfigurations;
            this.mShape = gradientState.mShape;
            this.mGradient = gradientState.mGradient;
            this.mAngle = gradientState.mAngle;
            this.mOrientation = gradientState.mOrientation;
            this.mSolidColors = gradientState.mSolidColors;
            if (gradientState.mGradientColors != null) {
                this.mGradientColors = (android.content.res.ColorStateList[]) gradientState.mGradientColors.clone();
            }
            if (gradientState.mPositions != null) {
                this.mPositions = (float[]) gradientState.mPositions.clone();
            }
            this.mStrokeColors = gradientState.mStrokeColors;
            this.mStrokeWidth = gradientState.mStrokeWidth;
            this.mStrokeDashWidth = gradientState.mStrokeDashWidth;
            this.mStrokeDashGap = gradientState.mStrokeDashGap;
            this.mRadius = gradientState.mRadius;
            if (gradientState.mRadiusArray != null) {
                this.mRadiusArray = (float[]) gradientState.mRadiusArray.clone();
            }
            if (gradientState.mPadding != null) {
                this.mPadding = new android.graphics.Rect(gradientState.mPadding);
            }
            this.mWidth = gradientState.mWidth;
            this.mHeight = gradientState.mHeight;
            this.mInnerRadiusRatio = gradientState.mInnerRadiusRatio;
            this.mThicknessRatio = gradientState.mThicknessRatio;
            this.mInnerRadius = gradientState.mInnerRadius;
            this.mThickness = gradientState.mThickness;
            this.mDither = gradientState.mDither;
            this.mOpticalInsets = gradientState.mOpticalInsets;
            this.mCenterX = gradientState.mCenterX;
            this.mCenterY = gradientState.mCenterY;
            this.mGradientRadius = gradientState.mGradientRadius;
            this.mGradientRadiusType = gradientState.mGradientRadiusType;
            this.mUseLevel = gradientState.mUseLevel;
            this.mUseLevelForShape = gradientState.mUseLevelForShape;
            this.mOpaqueOverBounds = gradientState.mOpaqueOverBounds;
            this.mOpaqueOverShape = gradientState.mOpaqueOverShape;
            this.mTint = gradientState.mTint;
            this.mBlendMode = gradientState.mBlendMode;
            this.mThemeAttrs = gradientState.mThemeAttrs;
            this.mAttrSize = gradientState.mAttrSize;
            this.mAttrGradient = gradientState.mAttrGradient;
            this.mAttrSolid = gradientState.mAttrSolid;
            this.mAttrStroke = gradientState.mAttrStroke;
            this.mAttrCorners = gradientState.mAttrCorners;
            this.mAttrPadding = gradientState.mAttrPadding;
            this.mDensity = android.graphics.drawable.Drawable.resolveDensity(resources, gradientState.mDensity);
            if (gradientState.mDensity != this.mDensity) {
                applyDensityScaling(gradientState.mDensity, this.mDensity);
            }
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                int i2 = this.mDensity;
                this.mDensity = i;
                applyDensityScaling(i2, i);
            }
        }

        public boolean hasCenterColor() {
            return this.mGradientColors != null && this.mGradientColors.length == 3;
        }

        private void applyDensityScaling(int i, int i2) {
            if (this.mInnerRadius > 0) {
                this.mInnerRadius = android.graphics.drawable.Drawable.scaleFromDensity(this.mInnerRadius, i, i2, true);
            }
            if (this.mThickness > 0) {
                this.mThickness = android.graphics.drawable.Drawable.scaleFromDensity(this.mThickness, i, i2, true);
            }
            if (this.mOpticalInsets != android.graphics.Insets.NONE) {
                this.mOpticalInsets = android.graphics.Insets.of(android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.left, i, i2, true), android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.top, i, i2, true), android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.right, i, i2, true), android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.bottom, i, i2, true));
            }
            if (this.mPadding != null) {
                this.mPadding.left = android.graphics.drawable.Drawable.scaleFromDensity(this.mPadding.left, i, i2, false);
                this.mPadding.top = android.graphics.drawable.Drawable.scaleFromDensity(this.mPadding.top, i, i2, false);
                this.mPadding.right = android.graphics.drawable.Drawable.scaleFromDensity(this.mPadding.right, i, i2, false);
                this.mPadding.bottom = android.graphics.drawable.Drawable.scaleFromDensity(this.mPadding.bottom, i, i2, false);
            }
            if (this.mRadius > 0.0f) {
                this.mRadius = android.graphics.drawable.Drawable.scaleFromDensity(this.mRadius, i, i2);
            }
            if (this.mRadiusArray != null) {
                this.mRadiusArray[0] = android.graphics.drawable.Drawable.scaleFromDensity((int) this.mRadiusArray[0], i, i2, true);
                this.mRadiusArray[1] = android.graphics.drawable.Drawable.scaleFromDensity((int) this.mRadiusArray[1], i, i2, true);
                this.mRadiusArray[2] = android.graphics.drawable.Drawable.scaleFromDensity((int) this.mRadiusArray[2], i, i2, true);
                this.mRadiusArray[3] = android.graphics.drawable.Drawable.scaleFromDensity((int) this.mRadiusArray[3], i, i2, true);
            }
            if (this.mStrokeWidth > 0) {
                this.mStrokeWidth = android.graphics.drawable.Drawable.scaleFromDensity(this.mStrokeWidth, i, i2, true);
            }
            if (this.mStrokeDashWidth > 0.0f) {
                this.mStrokeDashWidth = android.graphics.drawable.Drawable.scaleFromDensity(this.mStrokeDashGap, i, i2);
            }
            if (this.mStrokeDashGap > 0.0f) {
                this.mStrokeDashGap = android.graphics.drawable.Drawable.scaleFromDensity(this.mStrokeDashGap, i, i2);
            }
            if (this.mGradientRadiusType == 0) {
                this.mGradientRadius = android.graphics.drawable.Drawable.scaleFromDensity(this.mGradientRadius, i, i2);
            }
            if (this.mWidth > 0) {
                this.mWidth = android.graphics.drawable.Drawable.scaleFromDensity(this.mWidth, i, i2, true);
            }
            if (this.mHeight > 0) {
                this.mHeight = android.graphics.drawable.Drawable.scaleFromDensity(this.mHeight, i, i2, true);
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            boolean z = this.mGradientColors != null;
            if (this.mGradientColors != null) {
                for (int i = 0; i < this.mGradientColors.length; i++) {
                    z |= this.mGradientColors[i] != null && this.mGradientColors[i].canApplyTheme();
                }
            }
            if (this.mThemeAttrs != null || this.mAttrSize != null || this.mAttrGradient != null || this.mAttrSolid != null || this.mAttrStroke != null || this.mAttrCorners != null || this.mAttrPadding != null) {
                return true;
            }
            if (this.mTint != null && this.mTint.canApplyTheme()) {
                return true;
            }
            if (this.mStrokeColors == null || !this.mStrokeColors.canApplyTheme()) {
                return (this.mSolidColors != null && this.mSolidColors.canApplyTheme()) || z || super.canApplyTheme();
            }
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.GradientDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            android.graphics.drawable.GradientDrawable.GradientState gradientState;
            if (android.graphics.drawable.Drawable.resolveDensity(resources, this.mDensity) != this.mDensity) {
                gradientState = new android.graphics.drawable.GradientDrawable.GradientState(this, resources);
            } else {
                gradientState = this;
            }
            return new android.graphics.drawable.GradientDrawable(gradientState, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mStrokeColors != null ? this.mStrokeColors.getChangingConfigurations() : 0) | (this.mSolidColors != null ? this.mSolidColors.getChangingConfigurations() : 0) | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }

        public void setShape(int i) {
            this.mShape = i;
            computeOpacity();
        }

        public void setGradientType(int i) {
            this.mGradient = i;
        }

        public void setGradientCenter(float f, float f2) {
            this.mCenterX = f;
            this.mCenterY = f2;
        }

        public android.graphics.drawable.GradientDrawable.Orientation getOrientation() {
            return this.mOrientation;
        }

        public void setGradientColors(int[] iArr) {
            if (iArr == null) {
                this.mGradientColors = null;
            } else {
                if (this.mGradientColors == null || this.mGradientColors.length != iArr.length) {
                    this.mGradientColors = new android.content.res.ColorStateList[iArr.length];
                }
                for (int i = 0; i < iArr.length; i++) {
                    this.mGradientColors[i] = android.content.res.ColorStateList.valueOf(iArr[i]);
                }
            }
            this.mSolidColors = null;
            computeOpacity();
        }

        public void setSolidColors(android.content.res.ColorStateList colorStateList) {
            this.mGradientColors = null;
            this.mSolidColors = colorStateList;
            computeOpacity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void computeOpacity() {
            boolean z = false;
            this.mOpaqueOverBounds = false;
            this.mOpaqueOverShape = false;
            if (this.mGradientColors != null) {
                for (int i = 0; i < this.mGradientColors.length; i++) {
                    if (this.mGradientColors[i] != null && !android.graphics.drawable.GradientDrawable.isOpaque(this.mGradientColors[i].getDefaultColor())) {
                        return;
                    }
                }
            }
            if (this.mGradientColors == null && this.mSolidColors == null) {
                return;
            }
            this.mOpaqueOverShape = true;
            if (this.mShape == 0 && this.mRadius <= 0.0f && this.mRadiusArray == null) {
                z = true;
            }
            this.mOpaqueOverBounds = z;
        }

        public void setStroke(int i, android.content.res.ColorStateList colorStateList, float f, float f2) {
            this.mStrokeWidth = i;
            this.mStrokeColors = colorStateList;
            this.mStrokeDashWidth = f;
            this.mStrokeDashGap = f2;
            computeOpacity();
        }

        public void setCornerRadius(float f) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            this.mRadius = f;
            this.mRadiusArray = null;
            computeOpacity();
        }

        public void setCornerRadii(float[] fArr) {
            this.mRadiusArray = fArr;
            if (fArr == null) {
                this.mRadius = 0.0f;
            }
            computeOpacity();
        }

        public void setSize(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        public void setGradientRadius(float f, int i) {
            this.mGradientRadius = f;
            this.mGradientRadiusType = i;
        }
    }

    static boolean isOpaque(int i) {
        return ((i >> 24) & 255) == 255;
    }

    private GradientDrawable(android.graphics.drawable.GradientDrawable.GradientState gradientState, android.content.res.Resources resources) {
        this.mFillPaint = new android.graphics.Paint(1);
        this.mAlpha = 255;
        this.mPath = new android.graphics.Path();
        this.mRect = new android.graphics.RectF();
        this.mPathIsDirty = true;
        this.mGradientState = gradientState;
        updateLocalState(resources);
    }

    private void updateLocalState(android.content.res.Resources resources) {
        android.graphics.drawable.GradientDrawable.GradientState gradientState = this.mGradientState;
        if (gradientState.mSolidColors != null) {
            this.mFillPaint.setColor(gradientState.mSolidColors.getColorForState(getState(), 0));
        } else if (gradientState.mGradientColors == null) {
            this.mFillPaint.setColor(0);
        } else {
            this.mFillPaint.setColor(-16777216);
        }
        this.mPadding = gradientState.mPadding;
        if (gradientState.mStrokeWidth >= 0) {
            this.mStrokePaint = new android.graphics.Paint(1);
            this.mStrokePaint.setStyle(android.graphics.Paint.Style.STROKE);
            this.mStrokePaint.setStrokeWidth(gradientState.mStrokeWidth);
            if (gradientState.mStrokeColors != null) {
                this.mStrokePaint.setColor(gradientState.mStrokeColors.getColorForState(getState(), 0));
            }
            if (gradientState.mStrokeDashWidth != 0.0f) {
                this.mStrokePaint.setPathEffect(new android.graphics.DashPathEffect(new float[]{gradientState.mStrokeDashWidth, gradientState.mStrokeDashGap}, 0.0f));
            }
        }
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, gradientState.mTint, gradientState.mBlendMode);
        this.mGradientIsDirty = true;
        gradientState.computeOpacity();
    }
}
