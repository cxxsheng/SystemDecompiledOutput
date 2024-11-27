package android.graphics.drawable;

/* loaded from: classes.dex */
public class VectorDrawable extends android.graphics.drawable.Drawable {
    private static final java.lang.String LOGTAG = android.graphics.drawable.VectorDrawable.class.getSimpleName();
    private static final java.lang.String SHAPE_CLIP_PATH = "clip-path";
    private static final java.lang.String SHAPE_GROUP = "group";
    private static final java.lang.String SHAPE_PATH = "path";
    private static final java.lang.String SHAPE_VECTOR = "vector";
    private android.graphics.BlendModeColorFilter mBlendModeColorFilter;
    private android.graphics.ColorFilter mColorFilter;
    private boolean mDpiScaledDirty;
    private int mDpiScaledHeight;
    private android.graphics.Insets mDpiScaledInsets;
    private int mDpiScaledWidth;
    private boolean mMutated;
    private int mTargetDensity;
    private android.graphics.PorterDuffColorFilter mTintFilter;
    private final android.graphics.Rect mTmpBounds;
    private android.graphics.drawable.VectorDrawable.VectorDrawableState mVectorState;

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nAddChild(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateClipPath();

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateClipPath(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateFullPath();

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateFullPath(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateGroup();

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateGroup(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateTree(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateTreeFromCopy(long j, long j2);

    private static native int nDraw(long j, long j2, long j3, android.graphics.Rect rect, boolean z, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetFillAlpha(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native int nGetFillColor(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nGetFullPathProperties(long j, byte[] bArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nGetGroupProperties(long j, float[] fArr, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetPivotX(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetPivotY(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetRootAlpha(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetRotation(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetScaleX(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetScaleY(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetStrokeAlpha(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native int nGetStrokeColor(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetStrokeWidth(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetTranslateX(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetTranslateY(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetTrimPathEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetTrimPathOffset(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native float nGetTrimPathStart(long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetAllowCaching(long j, boolean z);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetAntiAlias(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetFillAlpha(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetFillColor(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetName(long j, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetPathData(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nSetPathString(long j, java.lang.String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetPivotX(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetPivotY(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetRendererViewportSize(long j, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native boolean nSetRootAlpha(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetRotation(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetScaleX(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetScaleY(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetStrokeAlpha(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetStrokeColor(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetStrokeWidth(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetTranslateX(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetTranslateY(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetTrimPathEnd(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetTrimPathOffset(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetTrimPathStart(long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nUpdateFullPathFillGradient(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nUpdateFullPathProperties(long j, float f, int i, float f2, int i2, float f3, float f4, float f5, float f6, float f7, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nUpdateFullPathStrokeGradient(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nUpdateGroupProperties(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    public VectorDrawable() {
        this(null, null);
    }

    private VectorDrawable(android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState, android.content.res.Resources resources) {
        this.mDpiScaledWidth = 0;
        this.mDpiScaledHeight = 0;
        this.mDpiScaledInsets = android.graphics.Insets.NONE;
        this.mDpiScaledDirty = true;
        this.mTmpBounds = new android.graphics.Rect();
        this.mVectorState = new android.graphics.drawable.VectorDrawable.VectorDrawableState(vectorDrawableState);
        updateLocalState(resources);
    }

    private void updateLocalState(android.content.res.Resources resources) {
        int resolveDensity = android.graphics.drawable.Drawable.resolveDensity(resources, this.mVectorState.mDensity);
        if (this.mTargetDensity != resolveDensity) {
            this.mTargetDensity = resolveDensity;
            this.mDpiScaledDirty = true;
        }
        updateColorFilters(this.mVectorState.mBlendMode, this.mVectorState.mTint);
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new android.graphics.drawable.VectorDrawable.VectorDrawableState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    java.lang.Object getTargetByName(java.lang.String str) {
        return this.mVectorState.mVGTargetsMap.get(str);
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        this.mVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mVectorState;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        int i;
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) {
            return;
        }
        android.graphics.ColorFilter colorFilter = this.mColorFilter == null ? this.mBlendModeColorFilter : this.mColorFilter;
        int nDraw = nDraw(this.mVectorState.getNativeRenderer(), canvas.getNativeCanvasWrapper(), colorFilter == null ? 0L : colorFilter.getNativeInstance(), this.mTmpBounds, needMirroring(), this.mVectorState.canReuseCache());
        if (nDraw == 0) {
            return;
        }
        if (canvas.isHardwareAccelerated()) {
            i = (nDraw - this.mVectorState.mLastHWCachePixelCount) * 4;
            this.mVectorState.mLastHWCachePixelCount = nDraw;
        } else {
            i = (nDraw - this.mVectorState.mLastSWCachePixelCount) * 4;
            this.mVectorState.mLastSWCachePixelCount = nDraw;
        }
        if (i > 0) {
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(i);
        } else if (i < 0) {
            dalvik.system.VMRuntime.getRuntime().registerNativeFree(-i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return (int) (this.mVectorState.getAlpha() * 255.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mVectorState.setAlpha(i / 255.0f)) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.mTint != colorStateList) {
            vectorDrawableState.mTint = colorStateList;
            updateColorFilters(this.mVectorState.mBlendMode, colorStateList);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.mBlendMode != blendMode) {
            vectorDrawableState.mBlendMode = blendMode;
            updateColorFilters(vectorDrawableState.mBlendMode, vectorDrawableState.mTint);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return super.isStateful() || (this.mVectorState != null && this.mVectorState.isStateful());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mVectorState != null && this.mVectorState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z;
        if (isStateful()) {
            mutate();
        }
        android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
        if (!vectorDrawableState.onStateChange(iArr)) {
            z = false;
        } else {
            vectorDrawableState.mCacheDirty = true;
            z = true;
        }
        if (vectorDrawableState.mTint != null && vectorDrawableState.mBlendMode != null) {
            updateColorFilters(vectorDrawableState.mBlendMode, vectorDrawableState.mTint);
            return true;
        }
        return z;
    }

    private void updateColorFilters(android.graphics.BlendMode blendMode, android.content.res.ColorStateList colorStateList) {
        this.mTintFilter = updateTintFilter(this.mTintFilter, colorStateList, android.graphics.BlendMode.blendModeToPorterDuffMode(blendMode));
        this.mBlendModeColorFilter = updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, blendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return getAlpha() == 0 ? -2 : -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.mDpiScaledDirty) {
            computeVectorSize();
        }
        return this.mDpiScaledWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.mDpiScaledDirty) {
            computeVectorSize();
        }
        return this.mDpiScaledHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.Insets getOpticalInsets() {
        if (this.mDpiScaledDirty) {
            computeVectorSize();
        }
        return this.mDpiScaledInsets;
    }

    void computeVectorSize() {
        android.graphics.Insets insets = this.mVectorState.mOpticalInsets;
        int i = this.mVectorState.mDensity;
        int i2 = this.mTargetDensity;
        if (i2 != i) {
            this.mDpiScaledWidth = android.graphics.drawable.Drawable.scaleFromDensity(this.mVectorState.mBaseWidth, i, i2, true);
            this.mDpiScaledHeight = android.graphics.drawable.Drawable.scaleFromDensity(this.mVectorState.mBaseHeight, i, i2, true);
            this.mDpiScaledInsets = android.graphics.Insets.of(android.graphics.drawable.Drawable.scaleFromDensity(insets.left, i, i2, false), android.graphics.drawable.Drawable.scaleFromDensity(insets.top, i, i2, false), android.graphics.drawable.Drawable.scaleFromDensity(insets.right, i, i2, false), android.graphics.drawable.Drawable.scaleFromDensity(insets.bottom, i, i2, false));
        } else {
            this.mDpiScaledWidth = this.mVectorState.mBaseWidth;
            this.mDpiScaledHeight = this.mVectorState.mBaseHeight;
            this.mDpiScaledInsets = insets;
        }
        this.mDpiScaledDirty = false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return (this.mVectorState != null && this.mVectorState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState == null) {
            return;
        }
        this.mDpiScaledDirty = this.mVectorState.setDensity(android.graphics.drawable.Drawable.resolveDensity(theme.getResources(), 0)) | this.mDpiScaledDirty;
        if (vectorDrawableState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(vectorDrawableState.mThemeAttrs, com.android.internal.R.styleable.VectorDrawable);
            try {
                try {
                    vectorDrawableState.mCacheDirty = true;
                    updateStateFromTypedArray(resolveAttributes);
                    resolveAttributes.recycle();
                    this.mDpiScaledDirty = true;
                } catch (org.xmlpull.v1.XmlPullParserException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                resolveAttributes.recycle();
                throw th;
            }
        }
        if (vectorDrawableState.mTint != null && vectorDrawableState.mTint.canApplyTheme()) {
            vectorDrawableState.mTint = vectorDrawableState.mTint.obtainForTheme(theme);
        }
        if (this.mVectorState != null && this.mVectorState.canApplyTheme()) {
            this.mVectorState.applyTheme(theme);
        }
        updateLocalState(theme.getResources());
    }

    public float getPixelSize() {
        if (this.mVectorState == null || this.mVectorState.mBaseWidth == 0 || this.mVectorState.mBaseHeight == 0 || this.mVectorState.mViewportHeight == 0.0f || this.mVectorState.mViewportWidth == 0.0f) {
            return 1.0f;
        }
        return java.lang.Math.min(this.mVectorState.mViewportWidth / this.mVectorState.mBaseWidth, this.mVectorState.mViewportHeight / this.mVectorState.mBaseHeight);
    }

    public static android.graphics.drawable.VectorDrawable create(android.content.res.Resources resources, int i) {
        int next;
        try {
            android.content.res.XmlResourceParser xml = resources.getXml(i);
            android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
            }
            android.graphics.drawable.VectorDrawable vectorDrawable = new android.graphics.drawable.VectorDrawable();
            vectorDrawable.inflate(resources, xml, asAttributeSet);
            return vectorDrawable;
        } catch (java.io.IOException e) {
            android.util.Log.e(LOGTAG, "parser error", e);
            return null;
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Log.e(LOGTAG, "parser error", e2);
            return null;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        try {
            android.os.Trace.traceBegin(8192L, "VectorDrawable#inflate");
            if (this.mVectorState.mRootGroup != null || this.mVectorState.mNativeTree != null) {
                if (this.mVectorState.mRootGroup != null) {
                    dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mVectorState.mRootGroup.getNativeSize());
                    this.mVectorState.mRootGroup.setTree(null);
                }
                this.mVectorState.mRootGroup = new android.graphics.drawable.VectorDrawable.VGroup();
                if (this.mVectorState.mNativeTree != null) {
                    dalvik.system.VMRuntime.getRuntime().registerNativeFree(316);
                    this.mVectorState.mNativeTree.release();
                }
                this.mVectorState.createNativeTree(this.mVectorState.mRootGroup);
            }
            android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
            vectorDrawableState.setDensity(android.graphics.drawable.Drawable.resolveDensity(resources, 0));
            android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.VectorDrawable);
            updateStateFromTypedArray(obtainAttributes);
            obtainAttributes.recycle();
            this.mDpiScaledDirty = true;
            vectorDrawableState.mCacheDirty = true;
            inflateChildElements(resources, xmlPullParser, attributeSet, theme);
            vectorDrawableState.onTreeConstructionFinished();
            updateLocalState(resources);
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) throws org.xmlpull.v1.XmlPullParserException {
        android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
        vectorDrawableState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        vectorDrawableState.mThemeAttrs = typedArray.extractThemeAttrs();
        int i = typedArray.getInt(6, -1);
        if (i != -1) {
            vectorDrawableState.mBlendMode = android.graphics.drawable.Drawable.parseBlendMode(i, android.graphics.BlendMode.SRC_IN);
        }
        android.content.res.ColorStateList colorStateList = typedArray.getColorStateList(1);
        if (colorStateList != null) {
            vectorDrawableState.mTint = colorStateList;
        }
        vectorDrawableState.mAutoMirrored = typedArray.getBoolean(5, vectorDrawableState.mAutoMirrored);
        vectorDrawableState.setViewportSize(typedArray.getFloat(7, vectorDrawableState.mViewportWidth), typedArray.getFloat(8, vectorDrawableState.mViewportHeight));
        if (vectorDrawableState.mViewportWidth <= 0.0f) {
            throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (vectorDrawableState.mViewportHeight <= 0.0f) {
            throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        vectorDrawableState.mBaseWidth = typedArray.getDimensionPixelSize(3, vectorDrawableState.mBaseWidth);
        vectorDrawableState.mBaseHeight = typedArray.getDimensionPixelSize(2, vectorDrawableState.mBaseHeight);
        if (vectorDrawableState.mBaseWidth <= 0) {
            throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (vectorDrawableState.mBaseHeight <= 0) {
            throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
        }
        vectorDrawableState.mOpticalInsets = android.graphics.Insets.of(typedArray.getDimensionPixelOffset(9, vectorDrawableState.mOpticalInsets.left), typedArray.getDimensionPixelOffset(10, vectorDrawableState.mOpticalInsets.top), typedArray.getDimensionPixelOffset(11, vectorDrawableState.mOpticalInsets.right), typedArray.getDimensionPixelOffset(12, vectorDrawableState.mOpticalInsets.bottom));
        vectorDrawableState.setAlpha(typedArray.getFloat(4, vectorDrawableState.getAlpha()));
        java.lang.String string = typedArray.getString(0);
        if (string != null) {
            vectorDrawableState.mRootName = string;
            vectorDrawableState.mVGTargetsMap.put(string, vectorDrawableState);
        }
    }

    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState = this.mVectorState;
        java.util.Stack stack = new java.util.Stack();
        stack.push(vectorDrawableState.mRootGroup);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                java.lang.String name = xmlPullParser.getName();
                android.graphics.drawable.VectorDrawable.VGroup vGroup = (android.graphics.drawable.VectorDrawable.VGroup) stack.peek();
                if (SHAPE_PATH.equals(name)) {
                    android.graphics.drawable.VectorDrawable.VFullPath vFullPath = new android.graphics.drawable.VectorDrawable.VFullPath();
                    vFullPath.inflate(resources, attributeSet, theme);
                    vGroup.addChild(vFullPath);
                    if (vFullPath.getPathName() != null) {
                        vectorDrawableState.mVGTargetsMap.put(vFullPath.getPathName(), vFullPath);
                    }
                    vectorDrawableState.mChangingConfigurations = vFullPath.mChangingConfigurations | vectorDrawableState.mChangingConfigurations;
                    z = false;
                } else if (SHAPE_CLIP_PATH.equals(name)) {
                    android.graphics.drawable.VectorDrawable.VClipPath vClipPath = new android.graphics.drawable.VectorDrawable.VClipPath();
                    vClipPath.inflate(resources, attributeSet, theme);
                    vGroup.addChild(vClipPath);
                    if (vClipPath.getPathName() != null) {
                        vectorDrawableState.mVGTargetsMap.put(vClipPath.getPathName(), vClipPath);
                    }
                    vectorDrawableState.mChangingConfigurations = vClipPath.mChangingConfigurations | vectorDrawableState.mChangingConfigurations;
                } else if (SHAPE_GROUP.equals(name)) {
                    android.graphics.drawable.VectorDrawable.VGroup vGroup2 = new android.graphics.drawable.VectorDrawable.VGroup();
                    vGroup2.inflate(resources, attributeSet, theme);
                    vGroup.addChild(vGroup2);
                    stack.push(vGroup2);
                    if (vGroup2.getGroupName() != null) {
                        vectorDrawableState.mVGTargetsMap.put(vGroup2.getGroupName(), vGroup2);
                    }
                    vectorDrawableState.mChangingConfigurations = vGroup2.mChangingConfigurations | vectorDrawableState.mChangingConfigurations;
                }
            } else if (eventType == 3 && SHAPE_GROUP.equals(xmlPullParser.getName())) {
                stack.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
            if (stringBuffer.length() > 0) {
                stringBuffer.append(" or ");
            }
            stringBuffer.append(SHAPE_PATH);
            throw new org.xmlpull.v1.XmlPullParserException("no " + ((java.lang.Object) stringBuffer) + " defined");
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
    }

    void setAllowCaching(boolean z) {
        nSetAllowCaching(this.mVectorState.getNativeRenderer(), z);
    }

    private boolean needMirroring() {
        return isAutoMirrored() && getLayoutDirection() == 1;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mVectorState.mAutoMirrored != z) {
            this.mVectorState.mAutoMirrored = z;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mVectorState.mAutoMirrored;
    }

    public long getNativeTree() {
        return this.mVectorState.getNativeRenderer();
    }

    public void setAntiAlias(boolean z) {
        nSetAntiAlias(this.mVectorState.mNativeTree.get(), z);
    }

    static class VectorDrawableState extends android.graphics.drawable.Drawable.ConstantState {
        static final android.util.Property<android.graphics.drawable.VectorDrawable.VectorDrawableState, java.lang.Float> ALPHA = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VectorDrawableState>("alpha") { // from class: android.graphics.drawable.VectorDrawable.VectorDrawableState.1
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState, float f) {
                vectorDrawableState.setAlpha(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState) {
                return java.lang.Float.valueOf(vectorDrawableState.getAlpha());
            }
        };
        private static final int NATIVE_ALLOCATION_SIZE = 316;
        boolean mAutoMirrored;
        int mBaseHeight;
        int mBaseWidth;
        android.graphics.BlendMode mBlendMode;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        android.graphics.BlendMode mCachedBlendMode;
        int[] mCachedThemeAttrs;
        android.content.res.ColorStateList mCachedTint;
        int mChangingConfigurations;
        int mDensity;
        android.graphics.Insets mOpticalInsets;
        android.graphics.drawable.VectorDrawable.VGroup mRootGroup;
        java.lang.String mRootName;
        int[] mThemeAttrs;
        android.content.res.ColorStateList mTint;
        float mViewportWidth = 0.0f;
        float mViewportHeight = 0.0f;
        com.android.internal.util.VirtualRefBasePtr mNativeTree = null;
        final android.util.ArrayMap<java.lang.String, java.lang.Object> mVGTargetsMap = new android.util.ArrayMap<>();
        int mLastSWCachePixelCount = 0;
        int mLastHWCachePixelCount = 0;
        private int mAllocationOfAllNodes = 0;

        android.util.Property getProperty(java.lang.String str) {
            if (ALPHA.getName().equals(str)) {
                return ALPHA;
            }
            return null;
        }

        public VectorDrawableState(android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState) {
            this.mTint = null;
            this.mBlendMode = android.graphics.drawable.Drawable.DEFAULT_BLEND_MODE;
            this.mBaseWidth = 0;
            this.mBaseHeight = 0;
            this.mOpticalInsets = android.graphics.Insets.NONE;
            this.mRootName = null;
            this.mDensity = 160;
            if (vectorDrawableState != null) {
                this.mThemeAttrs = vectorDrawableState.mThemeAttrs;
                this.mChangingConfigurations = vectorDrawableState.mChangingConfigurations;
                this.mTint = vectorDrawableState.mTint;
                this.mBlendMode = vectorDrawableState.mBlendMode;
                this.mAutoMirrored = vectorDrawableState.mAutoMirrored;
                this.mRootGroup = new android.graphics.drawable.VectorDrawable.VGroup(vectorDrawableState.mRootGroup, this.mVGTargetsMap);
                createNativeTreeFromCopy(vectorDrawableState, this.mRootGroup);
                this.mBaseWidth = vectorDrawableState.mBaseWidth;
                this.mBaseHeight = vectorDrawableState.mBaseHeight;
                setViewportSize(vectorDrawableState.mViewportWidth, vectorDrawableState.mViewportHeight);
                this.mOpticalInsets = vectorDrawableState.mOpticalInsets;
                this.mRootName = vectorDrawableState.mRootName;
                this.mDensity = vectorDrawableState.mDensity;
                if (vectorDrawableState.mRootName != null) {
                    this.mVGTargetsMap.put(vectorDrawableState.mRootName, this);
                }
            } else {
                this.mRootGroup = new android.graphics.drawable.VectorDrawable.VGroup();
                createNativeTree(this.mRootGroup);
            }
            onTreeConstructionFinished();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createNativeTree(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
            this.mNativeTree = new com.android.internal.util.VirtualRefBasePtr(android.graphics.drawable.VectorDrawable.nCreateTree(vGroup.mNativePtr));
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(316);
        }

        private void createNativeTreeFromCopy(android.graphics.drawable.VectorDrawable.VectorDrawableState vectorDrawableState, android.graphics.drawable.VectorDrawable.VGroup vGroup) {
            this.mNativeTree = new com.android.internal.util.VirtualRefBasePtr(android.graphics.drawable.VectorDrawable.nCreateTreeFromCopy(vectorDrawableState.mNativeTree.get(), vGroup.mNativePtr));
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(316);
        }

        void onTreeConstructionFinished() {
            this.mRootGroup.setTree(this.mNativeTree);
            this.mAllocationOfAllNodes = this.mRootGroup.getNativeSize();
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(this.mAllocationOfAllNodes);
        }

        long getNativeRenderer() {
            if (this.mNativeTree == null) {
                return 0L;
            }
            return this.mNativeTree.get();
        }

        public boolean canReuseCache() {
            if (!this.mCacheDirty && this.mCachedThemeAttrs == this.mThemeAttrs && this.mCachedTint == this.mTint && this.mCachedBlendMode == this.mBlendMode && this.mCachedAutoMirrored == this.mAutoMirrored) {
                return true;
            }
            updateCacheStates();
            return false;
        }

        public void updateCacheStates() {
            this.mCachedThemeAttrs = this.mThemeAttrs;
            this.mCachedTint = this.mTint;
            this.mCachedBlendMode = this.mBlendMode;
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public void applyTheme(android.content.res.Resources.Theme theme) {
            this.mRootGroup.applyTheme(theme);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mRootGroup != null && this.mRootGroup.canApplyTheme()) || ((this.mTint != null && this.mTint.canApplyTheme()) || super.canApplyTheme());
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.VectorDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.VectorDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | (this.mTint != null ? this.mTint.getChangingConfigurations() : 0);
        }

        public boolean isStateful() {
            return (this.mTint != null && this.mTint.isStateful()) || (this.mRootGroup != null && this.mRootGroup.isStateful());
        }

        public boolean hasFocusStateSpecified() {
            return (this.mTint != null && this.mTint.hasFocusStateSpecified()) || (this.mRootGroup != null && this.mRootGroup.hasFocusStateSpecified());
        }

        void setViewportSize(float f, float f2) {
            this.mViewportWidth = f;
            this.mViewportHeight = f2;
            android.graphics.drawable.VectorDrawable.nSetRendererViewportSize(getNativeRenderer(), f, f2);
        }

        public final boolean setDensity(int i) {
            if (this.mDensity != i) {
                int i2 = this.mDensity;
                this.mDensity = i;
                applyDensityScaling(i2, i);
                return true;
            }
            return false;
        }

        private void applyDensityScaling(int i, int i2) {
            this.mBaseWidth = android.graphics.drawable.Drawable.scaleFromDensity(this.mBaseWidth, i, i2, true);
            this.mBaseHeight = android.graphics.drawable.Drawable.scaleFromDensity(this.mBaseHeight, i, i2, true);
            this.mOpticalInsets = android.graphics.Insets.of(android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.left, i, i2, false), android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.top, i, i2, false), android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.right, i, i2, false), android.graphics.drawable.Drawable.scaleFromDensity(this.mOpticalInsets.bottom, i, i2, false));
        }

        public boolean onStateChange(int[] iArr) {
            return this.mRootGroup.onStateChange(iArr);
        }

        public void finalize() throws java.lang.Throwable {
            super.finalize();
            dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mAllocationOfAllNodes + 316 + (this.mLastHWCachePixelCount * 4) + (this.mLastSWCachePixelCount * 4));
        }

        public boolean setAlpha(float f) {
            return android.graphics.drawable.VectorDrawable.nSetRootAlpha(this.mNativeTree.get(), f);
        }

        public float getAlpha() {
            return android.graphics.drawable.VectorDrawable.nGetRootAlpha(this.mNativeTree.get());
        }
    }

    static class VGroup extends android.graphics.drawable.VectorDrawable.VObject {
        private static final int NATIVE_ALLOCATION_SIZE = 100;
        private static final int PIVOT_X_INDEX = 1;
        private static final int PIVOT_Y_INDEX = 2;
        private static final int ROTATION_INDEX = 0;
        private static final int SCALE_X_INDEX = 3;
        private static final int SCALE_Y_INDEX = 4;
        private static final int TRANSFORM_PROPERTY_COUNT = 7;
        private static final int TRANSLATE_X_INDEX = 5;
        private static final int TRANSLATE_Y_INDEX = 6;
        private int mChangingConfigurations;
        private final java.util.ArrayList<android.graphics.drawable.VectorDrawable.VObject> mChildren;
        private java.lang.String mGroupName;
        private boolean mIsStateful;
        private final long mNativePtr;
        private int[] mThemeAttrs;
        private float[] mTransform;
        private static final java.util.Map<java.lang.String, java.lang.Integer> sPropertyIndexMap = java.util.Map.of("translateX", 5, "translateY", 6, "scaleX", 3, "scaleY", 4, "pivotX", 1, "pivotY", 2, "rotation", 0);
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> TRANSLATE_X = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("translateX") { // from class: android.graphics.drawable.VectorDrawable.VGroup.1
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setTranslateX(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getTranslateX());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> TRANSLATE_Y = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("translateY") { // from class: android.graphics.drawable.VectorDrawable.VGroup.2
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setTranslateY(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getTranslateY());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> SCALE_X = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("scaleX") { // from class: android.graphics.drawable.VectorDrawable.VGroup.3
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setScaleX(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getScaleX());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> SCALE_Y = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("scaleY") { // from class: android.graphics.drawable.VectorDrawable.VGroup.4
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setScaleY(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getScaleY());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> PIVOT_X = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("pivotX") { // from class: android.graphics.drawable.VectorDrawable.VGroup.5
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setPivotX(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getPivotX());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> PIVOT_Y = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("pivotY") { // from class: android.graphics.drawable.VectorDrawable.VGroup.6
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setPivotY(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getPivotY());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VGroup, java.lang.Float> ROTATION = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VGroup>("rotation") { // from class: android.graphics.drawable.VectorDrawable.VGroup.7
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VGroup vGroup, float f) {
                vGroup.setRotation(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VGroup vGroup) {
                return java.lang.Float.valueOf(vGroup.getRotation());
            }
        };
        private static final java.util.Map<java.lang.String, android.util.Property> sPropertyMap = java.util.Map.of("translateX", TRANSLATE_X, "translateY", TRANSLATE_Y, "scaleX", SCALE_X, "scaleY", SCALE_Y, "pivotX", PIVOT_X, "pivotY", PIVOT_Y, "rotation", ROTATION);

        static int getPropertyIndex(java.lang.String str) {
            if (sPropertyIndexMap.containsKey(str)) {
                return sPropertyIndexMap.get(str).intValue();
            }
            return -1;
        }

        public VGroup(android.graphics.drawable.VectorDrawable.VGroup vGroup, android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap) {
            android.graphics.drawable.VectorDrawable.VPath vClipPath;
            this.mChildren = new java.util.ArrayList<>();
            this.mGroupName = null;
            this.mIsStateful = vGroup.mIsStateful;
            this.mThemeAttrs = vGroup.mThemeAttrs;
            this.mGroupName = vGroup.mGroupName;
            this.mChangingConfigurations = vGroup.mChangingConfigurations;
            if (this.mGroupName != null) {
                arrayMap.put(this.mGroupName, this);
            }
            this.mNativePtr = android.graphics.drawable.VectorDrawable.nCreateGroup(vGroup.mNativePtr);
            java.util.ArrayList<android.graphics.drawable.VectorDrawable.VObject> arrayList = vGroup.mChildren;
            for (int i = 0; i < arrayList.size(); i++) {
                android.graphics.drawable.VectorDrawable.VObject vObject = arrayList.get(i);
                if (vObject instanceof android.graphics.drawable.VectorDrawable.VGroup) {
                    addChild(new android.graphics.drawable.VectorDrawable.VGroup((android.graphics.drawable.VectorDrawable.VGroup) vObject, arrayMap));
                } else {
                    if (vObject instanceof android.graphics.drawable.VectorDrawable.VFullPath) {
                        vClipPath = new android.graphics.drawable.VectorDrawable.VFullPath((android.graphics.drawable.VectorDrawable.VFullPath) vObject);
                    } else if (vObject instanceof android.graphics.drawable.VectorDrawable.VClipPath) {
                        vClipPath = new android.graphics.drawable.VectorDrawable.VClipPath((android.graphics.drawable.VectorDrawable.VClipPath) vObject);
                    } else {
                        throw new java.lang.IllegalStateException("Unknown object in the tree!");
                    }
                    addChild(vClipPath);
                    if (vClipPath.mPathName != null) {
                        arrayMap.put(vClipPath.mPathName, vClipPath);
                    }
                }
            }
        }

        public VGroup() {
            this.mChildren = new java.util.ArrayList<>();
            this.mGroupName = null;
            this.mNativePtr = android.graphics.drawable.VectorDrawable.nCreateGroup();
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        android.util.Property getProperty(java.lang.String str) {
            if (sPropertyMap.containsKey(str)) {
                return sPropertyMap.get(str);
            }
            return null;
        }

        public java.lang.String getGroupName() {
            return this.mGroupName;
        }

        public void addChild(android.graphics.drawable.VectorDrawable.VObject vObject) {
            android.graphics.drawable.VectorDrawable.nAddChild(this.mNativePtr, vObject.getNativePtr());
            this.mChildren.add(vObject);
            this.mIsStateful = vObject.isStateful() | this.mIsStateful;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void setTree(com.android.internal.util.VirtualRefBasePtr virtualRefBasePtr) {
            super.setTree(virtualRefBasePtr);
            for (int i = 0; i < this.mChildren.size(); i++) {
                this.mChildren.get(i).setTree(virtualRefBasePtr);
            }
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void inflate(android.content.res.Resources resources, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) {
            android.content.res.TypedArray obtainAttributes = android.graphics.drawable.Drawable.obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.VectorDrawableGroup);
            updateStateFromTypedArray(obtainAttributes);
            obtainAttributes.recycle();
        }

        void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
            this.mChangingConfigurations |= typedArray.getChangingConfigurations();
            this.mThemeAttrs = typedArray.extractThemeAttrs();
            if (this.mTransform == null) {
                this.mTransform = new float[7];
            }
            if (!android.graphics.drawable.VectorDrawable.nGetGroupProperties(this.mNativePtr, this.mTransform, 7)) {
                throw new java.lang.RuntimeException("Error: inconsistent property count");
            }
            float f = typedArray.getFloat(5, this.mTransform[0]);
            float f2 = typedArray.getFloat(1, this.mTransform[1]);
            float f3 = typedArray.getFloat(2, this.mTransform[2]);
            float f4 = typedArray.getFloat(3, this.mTransform[3]);
            float f5 = typedArray.getFloat(4, this.mTransform[4]);
            float f6 = typedArray.getFloat(6, this.mTransform[5]);
            float f7 = typedArray.getFloat(7, this.mTransform[6]);
            java.lang.String string = typedArray.getString(0);
            if (string != null) {
                this.mGroupName = string;
                android.graphics.drawable.VectorDrawable.nSetName(this.mNativePtr, this.mGroupName);
            }
            android.graphics.drawable.VectorDrawable.nUpdateGroupProperties(this.mNativePtr, f, f2, f3, f4, f5, f6, f7);
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean onStateChange(int[] iArr) {
            java.util.ArrayList<android.graphics.drawable.VectorDrawable.VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.graphics.drawable.VectorDrawable.VObject vObject = arrayList.get(i);
                if (vObject.isStateful()) {
                    z |= vObject.onStateChange(iArr);
                }
            }
            return z;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean isStateful() {
            return this.mIsStateful;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean hasFocusStateSpecified() {
            java.util.ArrayList<android.graphics.drawable.VectorDrawable.VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.graphics.drawable.VectorDrawable.VObject vObject = arrayList.get(i);
                if (vObject.isStateful()) {
                    z |= vObject.hasFocusStateSpecified();
                }
            }
            return z;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        int getNativeSize() {
            int i = 100;
            for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                i += this.mChildren.get(i2).getNativeSize();
            }
            return i;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            java.util.ArrayList<android.graphics.drawable.VectorDrawable.VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (arrayList.get(i).canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void applyTheme(android.content.res.Resources.Theme theme) {
            if (this.mThemeAttrs != null) {
                android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(this.mThemeAttrs, com.android.internal.R.styleable.VectorDrawableGroup);
                updateStateFromTypedArray(resolveAttributes);
                resolveAttributes.recycle();
            }
            java.util.ArrayList<android.graphics.drawable.VectorDrawable.VObject> arrayList = this.mChildren;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                android.graphics.drawable.VectorDrawable.VObject vObject = arrayList.get(i);
                if (vObject.canApplyTheme()) {
                    vObject.applyTheme(theme);
                    this.mIsStateful = vObject.isStateful() | this.mIsStateful;
                }
            }
        }

        public float getRotation() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetRotation(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setRotation(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetRotation(this.mNativePtr, f);
            }
        }

        public float getPivotX() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetPivotX(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setPivotX(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetPivotX(this.mNativePtr, f);
            }
        }

        public float getPivotY() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetPivotY(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setPivotY(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetPivotY(this.mNativePtr, f);
            }
        }

        public float getScaleX() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetScaleX(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setScaleX(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetScaleX(this.mNativePtr, f);
            }
        }

        public float getScaleY() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetScaleY(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setScaleY(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetScaleY(this.mNativePtr, f);
            }
        }

        public float getTranslateX() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetTranslateX(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setTranslateX(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetTranslateX(this.mNativePtr, f);
            }
        }

        public float getTranslateY() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetTranslateY(this.mNativePtr);
            }
            return 0.0f;
        }

        public void setTranslateY(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetTranslateY(this.mNativePtr, f);
            }
        }
    }

    static abstract class VPath extends android.graphics.drawable.VectorDrawable.VObject {
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VPath, android.util.PathParser.PathData> PATH_DATA = new android.util.Property<android.graphics.drawable.VectorDrawable.VPath, android.util.PathParser.PathData>(android.util.PathParser.PathData.class, "pathData") { // from class: android.graphics.drawable.VectorDrawable.VPath.1
            @Override // android.util.Property
            public void set(android.graphics.drawable.VectorDrawable.VPath vPath, android.util.PathParser.PathData pathData) {
                vPath.setPathData(pathData);
            }

            @Override // android.util.Property
            public android.util.PathParser.PathData get(android.graphics.drawable.VectorDrawable.VPath vPath) {
                return vPath.getPathData();
            }
        };
        int mChangingConfigurations;
        protected android.util.PathParser.PathData mPathData;
        java.lang.String mPathName;

        @Override // android.graphics.drawable.VectorDrawable.VObject
        android.util.Property getProperty(java.lang.String str) {
            if (PATH_DATA.getName().equals(str)) {
                return PATH_DATA;
            }
            return null;
        }

        public VPath() {
            this.mPathData = null;
        }

        public VPath(android.graphics.drawable.VectorDrawable.VPath vPath) {
            this.mPathData = null;
            this.mPathName = vPath.mPathName;
            this.mChangingConfigurations = vPath.mChangingConfigurations;
            this.mPathData = vPath.mPathData != null ? new android.util.PathParser.PathData(vPath.mPathData) : null;
        }

        public java.lang.String getPathName() {
            return this.mPathName;
        }

        public android.util.PathParser.PathData getPathData() {
            return this.mPathData;
        }

        public void setPathData(android.util.PathParser.PathData pathData) {
            this.mPathData.setPathData(pathData);
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetPathData(getNativePtr(), this.mPathData.getNativePtr());
            }
        }
    }

    private static class VClipPath extends android.graphics.drawable.VectorDrawable.VPath {
        private static final int NATIVE_ALLOCATION_SIZE = 120;
        private final long mNativePtr;

        public VClipPath() {
            this.mNativePtr = android.graphics.drawable.VectorDrawable.nCreateClipPath();
        }

        public VClipPath(android.graphics.drawable.VectorDrawable.VClipPath vClipPath) {
            super(vClipPath);
            this.mNativePtr = android.graphics.drawable.VectorDrawable.nCreateClipPath(vClipPath.mNativePtr);
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void inflate(android.content.res.Resources resources, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) {
            android.content.res.TypedArray obtainAttributes = android.graphics.drawable.Drawable.obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.VectorDrawableClipPath);
            updateStateFromTypedArray(obtainAttributes);
            obtainAttributes.recycle();
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean canApplyTheme() {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void applyTheme(android.content.res.Resources.Theme theme) {
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean onStateChange(int[] iArr) {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean isStateful() {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean hasFocusStateSpecified() {
            return false;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        int getNativeSize() {
            return 120;
        }

        private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
            this.mChangingConfigurations |= typedArray.getChangingConfigurations();
            java.lang.String string = typedArray.getString(0);
            if (string != null) {
                this.mPathName = string;
                android.graphics.drawable.VectorDrawable.nSetName(this.mNativePtr, this.mPathName);
            }
            java.lang.String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.mPathData = new android.util.PathParser.PathData(string2);
                android.graphics.drawable.VectorDrawable.nSetPathString(this.mNativePtr, string2, string2.length());
            }
        }
    }

    static class VFullPath extends android.graphics.drawable.VectorDrawable.VPath {
        private static final int FILL_ALPHA_INDEX = 4;
        private static final int FILL_COLOR_INDEX = 3;
        private static final int FILL_TYPE_INDEX = 11;
        private static final int NATIVE_ALLOCATION_SIZE = 264;
        private static final int STROKE_ALPHA_INDEX = 2;
        private static final int STROKE_COLOR_INDEX = 1;
        private static final int STROKE_LINE_CAP_INDEX = 8;
        private static final int STROKE_LINE_JOIN_INDEX = 9;
        private static final int STROKE_MITER_LIMIT_INDEX = 10;
        private static final int STROKE_WIDTH_INDEX = 0;
        private static final int TOTAL_PROPERTY_COUNT = 12;
        private static final int TRIM_PATH_END_INDEX = 6;
        private static final int TRIM_PATH_OFFSET_INDEX = 7;
        private static final int TRIM_PATH_START_INDEX = 5;
        android.content.res.ComplexColor mFillColors;
        private final long mNativePtr;
        private byte[] mPropertyData;
        android.content.res.ComplexColor mStrokeColors;
        private int[] mThemeAttrs;
        private static final java.util.Map<java.lang.String, java.lang.Integer> sPropertyIndexMap = java.util.Map.of("strokeWidth", 0, "strokeColor", 1, "strokeAlpha", 2, "fillColor", 3, "fillAlpha", 4, "trimPathStart", 5, "trimPathEnd", 6, "trimPathOffset", 7);
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Float> STROKE_WIDTH = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VFullPath>("strokeWidth") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.1
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, float f) {
                vFullPath.setStrokeWidth(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Float.valueOf(vFullPath.getStrokeWidth());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Integer> STROKE_COLOR = new android.util.IntProperty<android.graphics.drawable.VectorDrawable.VFullPath>("strokeColor") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.2
            @Override // android.util.IntProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, int i) {
                vFullPath.setStrokeColor(i);
            }

            @Override // android.util.Property
            public java.lang.Integer get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Integer.valueOf(vFullPath.getStrokeColor());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Float> STROKE_ALPHA = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VFullPath>("strokeAlpha") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.3
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, float f) {
                vFullPath.setStrokeAlpha(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Float.valueOf(vFullPath.getStrokeAlpha());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Integer> FILL_COLOR = new android.util.IntProperty<android.graphics.drawable.VectorDrawable.VFullPath>("fillColor") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.4
            @Override // android.util.IntProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, int i) {
                vFullPath.setFillColor(i);
            }

            @Override // android.util.Property
            public java.lang.Integer get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Integer.valueOf(vFullPath.getFillColor());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Float> FILL_ALPHA = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VFullPath>("fillAlpha") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.5
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, float f) {
                vFullPath.setFillAlpha(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Float.valueOf(vFullPath.getFillAlpha());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Float> TRIM_PATH_START = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VFullPath>("trimPathStart") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.6
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, float f) {
                vFullPath.setTrimPathStart(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Float.valueOf(vFullPath.getTrimPathStart());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Float> TRIM_PATH_END = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VFullPath>("trimPathEnd") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.7
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, float f) {
                vFullPath.setTrimPathEnd(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Float.valueOf(vFullPath.getTrimPathEnd());
            }
        };
        private static final android.util.Property<android.graphics.drawable.VectorDrawable.VFullPath, java.lang.Float> TRIM_PATH_OFFSET = new android.util.FloatProperty<android.graphics.drawable.VectorDrawable.VFullPath>("trimPathOffset") { // from class: android.graphics.drawable.VectorDrawable.VFullPath.8
            @Override // android.util.FloatProperty
            public void setValue(android.graphics.drawable.VectorDrawable.VFullPath vFullPath, float f) {
                vFullPath.setTrimPathOffset(f);
            }

            @Override // android.util.Property
            public java.lang.Float get(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
                return java.lang.Float.valueOf(vFullPath.getTrimPathOffset());
            }
        };
        private static final java.util.Map<java.lang.String, android.util.Property> sPropertyMap = java.util.Map.of("strokeWidth", STROKE_WIDTH, "strokeColor", STROKE_COLOR, "strokeAlpha", STROKE_ALPHA, "fillColor", FILL_COLOR, "fillAlpha", FILL_ALPHA, "trimPathStart", TRIM_PATH_START, "trimPathEnd", TRIM_PATH_END, "trimPathOffset", TRIM_PATH_OFFSET);

        public VFullPath() {
            this.mStrokeColors = null;
            this.mFillColors = null;
            this.mNativePtr = android.graphics.drawable.VectorDrawable.nCreateFullPath();
        }

        public VFullPath(android.graphics.drawable.VectorDrawable.VFullPath vFullPath) {
            super(vFullPath);
            this.mStrokeColors = null;
            this.mFillColors = null;
            this.mNativePtr = android.graphics.drawable.VectorDrawable.nCreateFullPath(vFullPath.mNativePtr);
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.mStrokeColors = vFullPath.mStrokeColors;
            this.mFillColors = vFullPath.mFillColors;
        }

        @Override // android.graphics.drawable.VectorDrawable.VPath, android.graphics.drawable.VectorDrawable.VObject
        android.util.Property getProperty(java.lang.String str) {
            android.util.Property property = super.getProperty(str);
            if (property != null) {
                return property;
            }
            if (sPropertyMap.containsKey(str)) {
                return sPropertyMap.get(str);
            }
            return null;
        }

        int getPropertyIndex(java.lang.String str) {
            if (!sPropertyIndexMap.containsKey(str)) {
                return -1;
            }
            return sPropertyIndexMap.get(str).intValue();
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean onStateChange(int[] iArr) {
            boolean z;
            if (this.mStrokeColors != null && (this.mStrokeColors instanceof android.content.res.ColorStateList)) {
                int strokeColor = getStrokeColor();
                int colorForState = ((android.content.res.ColorStateList) this.mStrokeColors).getColorForState(iArr, strokeColor);
                z = (strokeColor != colorForState) | false;
                if (strokeColor != colorForState) {
                    android.graphics.drawable.VectorDrawable.nSetStrokeColor(this.mNativePtr, colorForState);
                }
            } else {
                z = false;
            }
            if (this.mFillColors != null && (this.mFillColors instanceof android.content.res.ColorStateList)) {
                int fillColor = getFillColor();
                int colorForState2 = ((android.content.res.ColorStateList) this.mFillColors).getColorForState(iArr, fillColor);
                z |= fillColor != colorForState2;
                if (fillColor != colorForState2) {
                    android.graphics.drawable.VectorDrawable.nSetFillColor(this.mNativePtr, colorForState2);
                }
            }
            return z;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean isStateful() {
            return (this.mStrokeColors == null && this.mFillColors == null) ? false : true;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean hasFocusStateSpecified() {
            return this.mStrokeColors != null && (this.mStrokeColors instanceof android.content.res.ColorStateList) && ((android.content.res.ColorStateList) this.mStrokeColors).hasFocusStateSpecified() && this.mFillColors != null && (this.mFillColors instanceof android.content.res.ColorStateList) && ((android.content.res.ColorStateList) this.mFillColors).hasFocusStateSpecified();
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        int getNativeSize() {
            return 264;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void inflate(android.content.res.Resources resources, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) {
            android.content.res.TypedArray obtainAttributes = android.graphics.drawable.Drawable.obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.VectorDrawablePath);
            updateStateFromTypedArray(obtainAttributes);
            obtainAttributes.recycle();
        }

        private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
            int i;
            int i2;
            android.graphics.Shader shader;
            int i3;
            int i4;
            int i5;
            long j;
            if (this.mPropertyData == null) {
                this.mPropertyData = new byte[48];
            }
            if (!android.graphics.drawable.VectorDrawable.nGetFullPathProperties(this.mNativePtr, this.mPropertyData, 48)) {
                throw new java.lang.RuntimeException("Error: inconsistent property count");
            }
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(this.mPropertyData);
            wrap.order(java.nio.ByteOrder.nativeOrder());
            float f = wrap.getFloat(0);
            int i6 = wrap.getInt(4);
            float f2 = wrap.getFloat(8);
            int i7 = wrap.getInt(12);
            float f3 = wrap.getFloat(16);
            float f4 = wrap.getFloat(20);
            float f5 = wrap.getFloat(24);
            float f6 = wrap.getFloat(28);
            int i8 = wrap.getInt(32);
            int i9 = wrap.getInt(36);
            float f7 = wrap.getFloat(40);
            int i10 = wrap.getInt(44);
            this.mChangingConfigurations |= typedArray.getChangingConfigurations();
            this.mThemeAttrs = typedArray.extractThemeAttrs();
            java.lang.String string = typedArray.getString(0);
            if (string == null) {
                i = i7;
            } else {
                this.mPathName = string;
                i = i7;
                android.graphics.drawable.VectorDrawable.nSetName(this.mNativePtr, this.mPathName);
            }
            java.lang.String string2 = typedArray.getString(2);
            if (string2 != null) {
                this.mPathData = new android.util.PathParser.PathData(string2);
                i2 = i6;
                android.graphics.drawable.VectorDrawable.nSetPathString(this.mNativePtr, string2, string2.length());
            } else {
                i2 = i6;
            }
            android.content.res.ComplexColor complexColor = typedArray.getComplexColor(1);
            android.graphics.Shader shader2 = null;
            if (complexColor == null) {
                shader = null;
                i3 = i;
            } else {
                if (complexColor instanceof android.content.res.GradientColor) {
                    this.mFillColors = complexColor;
                    shader = ((android.content.res.GradientColor) complexColor).getShader();
                } else {
                    if (complexColor.isStateful() || complexColor.canApplyTheme()) {
                        this.mFillColors = complexColor;
                    } else {
                        this.mFillColors = null;
                    }
                    shader = null;
                }
                i3 = complexColor.getDefaultColor();
            }
            android.content.res.ComplexColor complexColor2 = typedArray.getComplexColor(3);
            if (complexColor2 == null) {
                i4 = i2;
            } else {
                if (complexColor2 instanceof android.content.res.GradientColor) {
                    this.mStrokeColors = complexColor2;
                    shader2 = ((android.content.res.GradientColor) complexColor2).getShader();
                } else if (complexColor2.isStateful() || complexColor2.canApplyTheme()) {
                    this.mStrokeColors = complexColor2;
                } else {
                    this.mStrokeColors = null;
                }
                i4 = complexColor2.getDefaultColor();
            }
            long j2 = this.mNativePtr;
            if (shader != null) {
                i5 = i10;
                j = shader.getNativeInstance();
            } else {
                i5 = i10;
                j = 0;
            }
            android.graphics.drawable.VectorDrawable.nUpdateFullPathFillGradient(j2, j);
            android.graphics.drawable.VectorDrawable.nUpdateFullPathStrokeGradient(this.mNativePtr, shader2 != null ? shader2.getNativeInstance() : 0L);
            float f8 = typedArray.getFloat(12, f3);
            int i11 = typedArray.getInt(8, i8);
            int i12 = typedArray.getInt(9, i9);
            float f9 = typedArray.getFloat(10, f7);
            android.graphics.drawable.VectorDrawable.nUpdateFullPathProperties(this.mNativePtr, typedArray.getFloat(4, f), i4, typedArray.getFloat(11, f2), i3, f8, typedArray.getFloat(5, f4), typedArray.getFloat(6, f5), typedArray.getFloat(7, f6), f9, i11, i12, typedArray.getInt(13, i5));
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            return canComplexColorApplyTheme(this.mFillColors) || canComplexColorApplyTheme(this.mStrokeColors);
        }

        @Override // android.graphics.drawable.VectorDrawable.VObject
        public void applyTheme(android.content.res.Resources.Theme theme) {
            if (this.mThemeAttrs != null) {
                android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(this.mThemeAttrs, com.android.internal.R.styleable.VectorDrawablePath);
                updateStateFromTypedArray(resolveAttributes);
                resolveAttributes.recycle();
            }
            boolean canComplexColorApplyTheme = canComplexColorApplyTheme(this.mFillColors);
            boolean canComplexColorApplyTheme2 = canComplexColorApplyTheme(this.mStrokeColors);
            if (canComplexColorApplyTheme) {
                this.mFillColors = this.mFillColors.obtainForTheme(theme);
                if (this.mFillColors instanceof android.content.res.GradientColor) {
                    android.graphics.drawable.VectorDrawable.nUpdateFullPathFillGradient(this.mNativePtr, ((android.content.res.GradientColor) this.mFillColors).getShader().getNativeInstance());
                } else if (this.mFillColors instanceof android.content.res.ColorStateList) {
                    android.graphics.drawable.VectorDrawable.nSetFillColor(this.mNativePtr, this.mFillColors.getDefaultColor());
                }
            }
            if (canComplexColorApplyTheme2) {
                this.mStrokeColors = this.mStrokeColors.obtainForTheme(theme);
                if (this.mStrokeColors instanceof android.content.res.GradientColor) {
                    android.graphics.drawable.VectorDrawable.nUpdateFullPathStrokeGradient(this.mNativePtr, ((android.content.res.GradientColor) this.mStrokeColors).getShader().getNativeInstance());
                } else if (this.mStrokeColors instanceof android.content.res.ColorStateList) {
                    android.graphics.drawable.VectorDrawable.nSetStrokeColor(this.mNativePtr, this.mStrokeColors.getDefaultColor());
                }
            }
        }

        private boolean canComplexColorApplyTheme(android.content.res.ComplexColor complexColor) {
            return complexColor != null && complexColor.canApplyTheme();
        }

        int getStrokeColor() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetStrokeColor(this.mNativePtr);
            }
            return 0;
        }

        void setStrokeColor(int i) {
            this.mStrokeColors = null;
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetStrokeColor(this.mNativePtr, i);
            }
        }

        float getStrokeWidth() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetStrokeWidth(this.mNativePtr);
            }
            return 0.0f;
        }

        void setStrokeWidth(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetStrokeWidth(this.mNativePtr, f);
            }
        }

        float getStrokeAlpha() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetStrokeAlpha(this.mNativePtr);
            }
            return 0.0f;
        }

        void setStrokeAlpha(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetStrokeAlpha(this.mNativePtr, f);
            }
        }

        int getFillColor() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetFillColor(this.mNativePtr);
            }
            return 0;
        }

        void setFillColor(int i) {
            this.mFillColors = null;
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetFillColor(this.mNativePtr, i);
            }
        }

        float getFillAlpha() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetFillAlpha(this.mNativePtr);
            }
            return 0.0f;
        }

        void setFillAlpha(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetFillAlpha(this.mNativePtr, f);
            }
        }

        float getTrimPathStart() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetTrimPathStart(this.mNativePtr);
            }
            return 0.0f;
        }

        void setTrimPathStart(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetTrimPathStart(this.mNativePtr, f);
            }
        }

        float getTrimPathEnd() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetTrimPathEnd(this.mNativePtr);
            }
            return 0.0f;
        }

        void setTrimPathEnd(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetTrimPathEnd(this.mNativePtr, f);
            }
        }

        float getTrimPathOffset() {
            if (isTreeValid()) {
                return android.graphics.drawable.VectorDrawable.nGetTrimPathOffset(this.mNativePtr);
            }
            return 0.0f;
        }

        void setTrimPathOffset(float f) {
            if (isTreeValid()) {
                android.graphics.drawable.VectorDrawable.nSetTrimPathOffset(this.mNativePtr, f);
            }
        }
    }

    static abstract class VObject {
        com.android.internal.util.VirtualRefBasePtr mTreePtr = null;

        abstract void applyTheme(android.content.res.Resources.Theme theme);

        abstract boolean canApplyTheme();

        abstract long getNativePtr();

        abstract int getNativeSize();

        abstract android.util.Property getProperty(java.lang.String str);

        abstract boolean hasFocusStateSpecified();

        abstract void inflate(android.content.res.Resources resources, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme);

        abstract boolean isStateful();

        abstract boolean onStateChange(int[] iArr);

        VObject() {
        }

        boolean isTreeValid() {
            return (this.mTreePtr == null || this.mTreePtr.get() == 0) ? false : true;
        }

        void setTree(com.android.internal.util.VirtualRefBasePtr virtualRefBasePtr) {
            this.mTreePtr = virtualRefBasePtr;
        }
    }
}
