package android.graphics.drawable;

/* loaded from: classes.dex */
public class AdaptiveIconDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
    private static final int BACKGROUND_ID = 0;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    private static final float EXTRA_INSET_PERCENTAGE = 0.25f;
    private static final int FOREGROUND_ID = 1;
    public static final float MASK_SIZE = 100.0f;
    private static final int MONOCHROME_ID = 2;
    private static final float SAFEZONE_SCALE = 0.9166667f;
    private static android.graphics.Path sMask;
    private final android.graphics.Canvas mCanvas;
    private boolean mChildRequestedInvalidation;
    private android.graphics.Rect mHotspotBounds;
    android.graphics.drawable.AdaptiveIconDrawable.LayerState mLayerState;
    private android.graphics.Bitmap mLayersBitmap;
    private android.graphics.Shader mLayersShader;
    private final android.graphics.Path mMask;
    private final android.graphics.Matrix mMaskMatrix;
    private final android.graphics.Path mMaskScaleOnly;
    private boolean mMutated;
    private android.graphics.Paint mPaint;
    private boolean mSuspendChildInvalidation;
    private final android.graphics.Rect mTmpOutRect;
    private final android.graphics.Region mTransparentRegion;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    AdaptiveIconDrawable() {
        this((android.graphics.drawable.AdaptiveIconDrawable.LayerState) null, (android.content.res.Resources) null);
    }

    AdaptiveIconDrawable(android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState, android.content.res.Resources resources) {
        android.content.res.Resources resources2;
        this.mTmpOutRect = new android.graphics.Rect();
        this.mPaint = new android.graphics.Paint(7);
        this.mLayerState = createConstantState(layerState, resources);
        if (android.app.ActivityThread.currentActivityThread() == null) {
            resources2 = android.content.res.Resources.getSystem();
        } else {
            resources2 = android.app.ActivityThread.currentActivityThread().getApplication().getResources();
        }
        sMask = android.util.PathParser.createPathFromPathData(resources2.getString(com.android.internal.R.string.config_icon_mask));
        this.mMask = new android.graphics.Path(sMask);
        this.mMaskScaleOnly = new android.graphics.Path(this.mMask);
        this.mMaskMatrix = new android.graphics.Matrix();
        this.mCanvas = new android.graphics.Canvas();
        this.mTransparentRegion = new android.graphics.Region();
    }

    private android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable createChildDrawable(android.graphics.drawable.Drawable drawable) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable(this.mLayerState.mDensity);
        childDrawable.mDrawable = drawable;
        childDrawable.mDrawable.setCallback(this);
        this.mLayerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
        return childDrawable;
    }

    android.graphics.drawable.AdaptiveIconDrawable.LayerState createConstantState(android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState, android.content.res.Resources resources) {
        return new android.graphics.drawable.AdaptiveIconDrawable.LayerState(layerState, this, resources);
    }

    public AdaptiveIconDrawable(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
        this(drawable, drawable2, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AdaptiveIconDrawable(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3) {
        this((android.graphics.drawable.AdaptiveIconDrawable.LayerState) null, (android.content.res.Resources) null);
        if (drawable != null) {
            addLayer(0, createChildDrawable(drawable));
        }
        if (drawable2 != null) {
            addLayer(1, createChildDrawable(drawable2));
        }
        if (drawable3 != null) {
            addLayer(2, createChildDrawable(drawable3));
        }
    }

    private void addLayer(int i, android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable) {
        this.mLayerState.mChildren[i] = childDrawable;
        this.mLayerState.invalidateCache();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return;
        }
        int resolveDensity = android.graphics.drawable.Drawable.resolveDensity(resources, 0);
        layerState.setDensity(resolveDensity);
        layerState.mSrcDensityOverride = this.mSrcDensityOverride;
        layerState.mSourceDrawableId = android.content.res.Resources.getAttributeSetSourceResId(attributeSet);
        for (android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable : layerState.mChildren) {
            childDrawable.setDensity(resolveDensity);
        }
        inflateLayers(resources, xmlPullParser, attributeSet, theme);
    }

    public static float getExtraInsetFraction() {
        return 0.25f;
    }

    public static float getExtraInsetPercentage() {
        return 0.25f;
    }

    public android.graphics.Path getIconMask() {
        return this.mMask;
    }

    public android.graphics.drawable.Drawable getForeground() {
        return this.mLayerState.mChildren[1].mDrawable;
    }

    public android.graphics.drawable.Drawable getBackground() {
        return this.mLayerState.mChildren[0].mDrawable;
    }

    public android.graphics.drawable.Drawable getMonochrome() {
        return this.mLayerState.mChildren[2].mDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        updateLayerBounds(rect);
    }

    private void updateLayerBounds(android.graphics.Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        try {
            suspendChildInvalidation();
            updateLayerBoundsInternal(rect);
            updateMaskBoundsInternal(rect);
        } finally {
            resumeChildInvalidation();
        }
    }

    private void updateLayerBoundsInternal(android.graphics.Rect rect) {
        int width = rect.width() / 2;
        int height = rect.height() / 2;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = this.mLayerState.mChildren[i].mDrawable;
            if (drawable != null) {
                int width2 = (int) (rect.width() / 1.3333334f);
                int height2 = (int) (rect.height() / 1.3333334f);
                android.graphics.Rect rect2 = this.mTmpOutRect;
                rect2.set(width - width2, height - height2, width2 + width, height2 + height);
                drawable.setBounds(rect2);
            }
        }
    }

    private void updateMaskBoundsInternal(android.graphics.Rect rect) {
        this.mMaskMatrix.setScale(rect.width() / 100.0f, rect.height() / 100.0f);
        sMask.transform(this.mMaskMatrix, this.mMaskScaleOnly);
        this.mMaskMatrix.postTranslate(rect.left, rect.top);
        sMask.transform(this.mMaskMatrix, this.mMask);
        if (this.mLayersBitmap == null || this.mLayersBitmap.getWidth() != rect.width() || this.mLayersBitmap.getHeight() != rect.height()) {
            this.mLayersBitmap = android.graphics.Bitmap.createBitmap(rect.width(), rect.height(), android.graphics.Bitmap.Config.ARGB_8888);
        }
        this.mPaint.setShader(null);
        this.mTransparentRegion.setEmpty();
        this.mLayersShader = null;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (this.mLayersBitmap == null) {
            return;
        }
        if (this.mLayersShader == null) {
            this.mCanvas.setBitmap(this.mLayersBitmap);
            this.mCanvas.drawColor(-16777216);
            if (this.mLayerState.mChildren[0].mDrawable != null) {
                this.mLayerState.mChildren[0].mDrawable.draw(this.mCanvas);
            }
            if (this.mLayerState.mChildren[1].mDrawable != null) {
                this.mLayerState.mChildren[1].mDrawable.draw(this.mCanvas);
            }
            this.mLayersShader = new android.graphics.BitmapShader(this.mLayersBitmap, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP);
            this.mPaint.setShader(this.mLayersShader);
        }
        if (this.mMaskScaleOnly != null) {
            android.graphics.Rect bounds = getBounds();
            canvas.translate(bounds.left, bounds.top);
            canvas.drawPath(this.mMaskScaleOnly, this.mPaint);
            canvas.translate(-bounds.left, -bounds.top);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        this.mLayersShader = null;
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        outline.setPath(this.mMask);
    }

    public android.graphics.Region getSafeZone() {
        android.graphics.Path iconMask = getIconMask();
        this.mMaskMatrix.setScale(SAFEZONE_SCALE, SAFEZONE_SCALE, getBounds().centerX(), getBounds().centerY());
        android.graphics.Path path = new android.graphics.Path();
        iconMask.transform(this.mMaskMatrix, path);
        android.graphics.Region region = new android.graphics.Region(getBounds());
        region.setPath(path, region);
        return region;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Region getTransparentRegion() {
        if (this.mTransparentRegion.isEmpty()) {
            this.mMask.toggleInverseFillType();
            this.mTransparentRegion.set(getBounds());
            this.mTransparentRegion.setPath(this.mMask, this.mTransparentRegion);
            this.mMask.toggleInverseFillType();
        }
        return this.mTransparentRegion;
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return;
        }
        int resolveDensity = android.graphics.drawable.Drawable.resolveDensity(theme.getResources(), 0);
        layerState.setDensity(resolveDensity);
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable = childDrawableArr[i];
            childDrawable.setDensity(resolveDensity);
            if (childDrawable.mThemeAttrs != null) {
                android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(childDrawable.mThemeAttrs, com.android.internal.R.styleable.AdaptiveIconDrawableLayer);
                updateLayerFromTypedArray(childDrawable, resolveAttributes);
                resolveAttributes.recycle();
            }
            android.graphics.drawable.Drawable drawable = childDrawable.mDrawable;
            if (drawable != null && drawable.canApplyTheme()) {
                drawable.applyTheme(theme);
                layerState.mChildrenChangingConfigurations = drawable.getChangingConfigurations() | layerState.mChildrenChangingConfigurations;
            }
        }
    }

    public int getSourceDrawableResId() {
        android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState = this.mLayerState;
        if (layerState == null) {
            return 0;
        }
        return layerState.mSourceDrawableId;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0009, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0098 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void inflateLayers(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        char c;
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable;
        int next;
        android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState = this.mLayerState;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 >= depth || next2 != 3) {
                    if (next2 == 2 && depth2 <= depth) {
                        java.lang.String name = xmlPullParser.getName();
                        int i = 0;
                        switch (name.hashCode()) {
                            case -1905977571:
                                if (name.equals("monochrome")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1332194002:
                                if (name.equals(android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_BACKGROUND)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1984457027:
                                if (name.equals("foreground")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                childDrawable = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable(layerState.mDensity);
                                android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AdaptiveIconDrawableLayer);
                                updateLayerFromTypedArray(childDrawable, obtainAttributes);
                                obtainAttributes.recycle();
                                if (childDrawable.mDrawable == null && childDrawable.mThemeAttrs == null) {
                                    do {
                                        next = xmlPullParser.next();
                                    } while (next == 4);
                                    if (next == 2) {
                                        throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <foreground> or <background> tag requires a 'drawable'attribute or child tag defining a drawable");
                                    }
                                    childDrawable.mDrawable = android.graphics.drawable.Drawable.createFromXmlInnerForDensity(resources, xmlPullParser, attributeSet, this.mLayerState.mSrcDensityOverride, theme);
                                    childDrawable.mDrawable.setCallback(this);
                                    layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
                                }
                                addLayer(i, childDrawable);
                                break;
                            case 1:
                                i = 1;
                                childDrawable = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable(layerState.mDensity);
                                android.content.res.TypedArray obtainAttributes2 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AdaptiveIconDrawableLayer);
                                updateLayerFromTypedArray(childDrawable, obtainAttributes2);
                                obtainAttributes2.recycle();
                                if (childDrawable.mDrawable == null) {
                                    do {
                                        next = xmlPullParser.next();
                                    } while (next == 4);
                                    if (next == 2) {
                                    }
                                    break;
                                }
                                addLayer(i, childDrawable);
                                break;
                            case 2:
                                i = 2;
                                childDrawable = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable(layerState.mDensity);
                                android.content.res.TypedArray obtainAttributes22 = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AdaptiveIconDrawableLayer);
                                updateLayerFromTypedArray(childDrawable, obtainAttributes22);
                                obtainAttributes22.recycle();
                                if (childDrawable.mDrawable == null) {
                                }
                                addLayer(i, childDrawable);
                                break;
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

    private void updateLayerFromTypedArray(android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable, android.content.res.TypedArray typedArray) {
        android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState = this.mLayerState;
        layerState.mChildrenChangingConfigurations |= typedArray.getChangingConfigurations();
        childDrawable.mThemeAttrs = typedArray.extractThemeAttrs();
        android.graphics.drawable.Drawable drawableForDensity = typedArray.getDrawableForDensity(0, layerState.mSrcDensityOverride);
        if (drawableForDensity != null) {
            if (childDrawable.mDrawable != null) {
                childDrawable.mDrawable.setCallback(null);
            }
            childDrawable.mDrawable = drawableForDensity;
            childDrawable.mDrawable.setCallback(this);
            layerState.mChildrenChangingConfigurations = childDrawable.mDrawable.getChangingConfigurations() | layerState.mChildrenChangingConfigurations;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return (this.mLayerState != null && this.mLayerState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isProjected() {
        if (super.isProjected()) {
            return true;
        }
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            if (childDrawableArr[i].mDrawable != null && childDrawableArr[i].mDrawable.isProjected()) {
                return true;
            }
        }
        return false;
    }

    private void suspendChildInvalidation() {
        this.mSuspendChildInvalidation = true;
    }

    private void resumeChildInvalidation() {
        this.mSuspendChildInvalidation = false;
        if (this.mChildRequestedInvalidation) {
            this.mChildRequestedInvalidation = false;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mSuspendChildInvalidation) {
            this.mChildRequestedInvalidation = true;
        } else {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable) {
        unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mLayerState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setHotspot(f, f2);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i5 = 0; i5 < 3; i5++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i5].mDrawable;
            if (drawable != null) {
                drawable.setHotspotBounds(i, i2, i3, i4);
            }
        }
        if (this.mHotspotBounds == null) {
            this.mHotspotBounds = new android.graphics.Rect(i, i2, i3, i4);
        } else {
            this.mHotspotBounds.set(i, i2, i3, i4);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(android.graphics.Rect rect) {
        if (this.mHotspotBounds != null) {
            rect.set(this.mHotspotBounds);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setVisible(z, z2);
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setDither(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setTintList(colorStateList);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setTintBlendMode(blendMode);
            }
        }
    }

    public void setOpacity(int i) {
        this.mLayerState.mOpacityOverride = i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mLayerState.mAutoMirrored = z;
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.setAutoMirrored(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mLayerState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.jumpToCurrentState();
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mLayerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mLayerState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        boolean z = false;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null && drawable.isStateful() && drawable.setState(iArr)) {
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        boolean z = false;
        for (int i2 = 0; i2 < 3; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null && drawable.setLevel(i)) {
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) (getMaxIntrinsicWidth() * DEFAULT_VIEW_PORT_SCALE);
    }

    private int getMaxIntrinsicWidth() {
        int intrinsicWidth;
        int i = -1;
        for (int i2 = 0; i2 < 3; i2++) {
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable = this.mLayerState.mChildren[i2];
            if (childDrawable.mDrawable != null && (intrinsicWidth = childDrawable.mDrawable.getIntrinsicWidth()) > i) {
                i = intrinsicWidth;
            }
        }
        return i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) (getMaxIntrinsicHeight() * DEFAULT_VIEW_PORT_SCALE);
    }

    private int getMaxIntrinsicHeight() {
        int intrinsicHeight;
        int i = -1;
        for (int i2 = 0; i2 < 3; i2++) {
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable = this.mLayerState.mChildren[i2];
            if (childDrawable.mDrawable != null && (intrinsicHeight = childDrawable.mDrawable.getIntrinsicHeight()) > i) {
                i = intrinsicHeight;
            }
        }
        return i;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        if (this.mLayerState.canConstantState()) {
            this.mLayerState.mChangingConfigurations = getChangingConfigurations();
            return this.mLayerState;
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLayerState = createConstantState(this.mLayerState, null);
            for (int i = 0; i < 3; i++) {
                android.graphics.drawable.Drawable drawable = this.mLayerState.mChildren[i].mDrawable;
                if (drawable != null) {
                    drawable.mutate();
                }
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i = 0; i < 3; i++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
            if (drawable != null) {
                drawable.clearMutated();
            }
        }
        this.mMutated = false;
    }

    static class ChildDrawable {
        public int mDensity;
        public android.graphics.drawable.Drawable mDrawable;
        public int[] mThemeAttrs;

        ChildDrawable(int i) {
            this.mDensity = 160;
            this.mDensity = i;
        }

        ChildDrawable(android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable childDrawable, android.graphics.drawable.AdaptiveIconDrawable adaptiveIconDrawable, android.content.res.Resources resources) {
            android.graphics.drawable.Drawable drawable;
            this.mDensity = 160;
            android.graphics.drawable.Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                android.graphics.drawable.Drawable.ConstantState constantState = drawable2.getConstantState();
                if (constantState == null) {
                    drawable = drawable2;
                } else if (resources != null) {
                    drawable = constantState.newDrawable(resources);
                } else {
                    drawable = constantState.newDrawable();
                }
                drawable.setCallback(adaptiveIconDrawable);
                drawable.setBounds(drawable2.getBounds());
                drawable.setLevel(drawable2.getLevel());
            } else {
                drawable = null;
            }
            this.mDrawable = drawable;
            this.mThemeAttrs = childDrawable.mThemeAttrs;
            this.mDensity = android.graphics.drawable.Drawable.resolveDensity(resources, childDrawable.mDensity);
        }

        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mDrawable != null && this.mDrawable.canApplyTheme());
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                this.mDensity = i;
            }
        }
    }

    static class LayerState extends android.graphics.drawable.Drawable.ConstantState {
        static final int N_CHILDREN = 3;
        private boolean mAutoMirrored;
        int mChangingConfigurations;
        private boolean mCheckedOpacity;
        private boolean mCheckedStateful;
        android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] mChildren;
        int mChildrenChangingConfigurations;
        int mDensity;
        private boolean mIsStateful;
        private int mOpacity;
        int mOpacityOverride;
        int mSourceDrawableId;
        int mSrcDensityOverride;
        private int[] mThemeAttrs;

        LayerState(android.graphics.drawable.AdaptiveIconDrawable.LayerState layerState, android.graphics.drawable.AdaptiveIconDrawable adaptiveIconDrawable, android.content.res.Resources resources) {
            int i = 0;
            this.mSrcDensityOverride = 0;
            this.mOpacityOverride = 0;
            this.mSourceDrawableId = 0;
            this.mAutoMirrored = false;
            this.mDensity = android.graphics.drawable.Drawable.resolveDensity(resources, layerState != null ? layerState.mDensity : 0);
            this.mChildren = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[3];
            if (layerState == null) {
                while (i < 3) {
                    this.mChildren[i] = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable(this.mDensity);
                    i++;
                }
                return;
            }
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
            this.mChangingConfigurations = layerState.mChangingConfigurations;
            this.mChildrenChangingConfigurations = layerState.mChildrenChangingConfigurations;
            this.mSourceDrawableId = layerState.mSourceDrawableId;
            while (i < 3) {
                this.mChildren[i] = new android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable(childDrawableArr[i], adaptiveIconDrawable, resources);
                i++;
            }
            this.mCheckedOpacity = layerState.mCheckedOpacity;
            this.mOpacity = layerState.mOpacity;
            this.mCheckedStateful = layerState.mCheckedStateful;
            this.mIsStateful = layerState.mIsStateful;
            this.mAutoMirrored = layerState.mAutoMirrored;
            this.mThemeAttrs = layerState.mThemeAttrs;
            this.mOpacityOverride = layerState.mOpacityOverride;
            this.mSrcDensityOverride = layerState.mSrcDensityOverride;
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                this.mDensity = i;
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null || super.canApplyTheme()) {
                return true;
            }
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i = 0; i < 3; i++) {
                if (childDrawableArr[i].canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.AdaptiveIconDrawable(this, (android.content.res.Resources) null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.AdaptiveIconDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
        }

        public final int getOpacity() {
            int i;
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            int i2 = 0;
            while (true) {
                if (i2 >= 3) {
                    i2 = -1;
                    break;
                }
                if (childDrawableArr[i2].mDrawable != null) {
                    break;
                }
                i2++;
            }
            if (i2 >= 0) {
                i = childDrawableArr[i2].mDrawable.getOpacity();
            } else {
                i = -2;
            }
            for (int i3 = i2 + 1; i3 < 3; i3++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i3].mDrawable;
                if (drawable != null) {
                    i = android.graphics.drawable.Drawable.resolveOpacity(i, drawable.getOpacity());
                }
            }
            this.mOpacity = i;
            this.mCheckedOpacity = true;
            return i;
        }

        public final boolean isStateful() {
            if (this.mCheckedStateful) {
                return this.mIsStateful;
            }
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i < 3) {
                    android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
                    if (drawable == null || !drawable.isStateful()) {
                        i++;
                    } else {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            this.mIsStateful = z;
            this.mCheckedStateful = true;
            return z;
        }

        public final boolean hasFocusStateSpecified() {
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i = 0; i < 3; i++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
                if (drawable != null && drawable.hasFocusStateSpecified()) {
                    return true;
                }
            }
            return false;
        }

        public final boolean canConstantState() {
            android.graphics.drawable.AdaptiveIconDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i = 0; i < 3; i++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i].mDrawable;
                if (drawable != null && drawable.getConstantState() == null) {
                    return false;
                }
            }
            return true;
        }

        public void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }
    }
}
