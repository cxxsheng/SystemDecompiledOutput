package android.graphics.drawable;

/* loaded from: classes.dex */
public class LayerDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Drawable.Callback {
    public static final int INSET_UNDEFINED = Integer.MIN_VALUE;
    private static final java.lang.String LOG_TAG = "LayerDrawable";
    public static final int PADDING_MODE_NEST = 0;
    public static final int PADDING_MODE_STACK = 1;
    private boolean mChildRequestedInvalidation;
    private android.graphics.Rect mHotspotBounds;
    android.graphics.drawable.LayerDrawable.LayerState mLayerState;
    private boolean mMutated;
    private int[] mPaddingB;
    private int[] mPaddingL;
    private int[] mPaddingR;
    private int[] mPaddingT;
    private boolean mSuspendChildInvalidation;
    private final android.graphics.Rect mTmpContainer;
    private final android.graphics.Rect mTmpOutRect;
    private final android.graphics.Rect mTmpRect;

    public LayerDrawable(android.graphics.drawable.Drawable[] drawableArr) {
        this(drawableArr, (android.graphics.drawable.LayerDrawable.LayerState) null);
    }

    LayerDrawable(android.graphics.drawable.Drawable[] drawableArr, android.graphics.drawable.LayerDrawable.LayerState layerState) {
        this(layerState, (android.content.res.Resources) null);
        if (drawableArr == null) {
            throw new java.lang.IllegalArgumentException("layers must be non-null");
        }
        int length = drawableArr.length;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = new android.graphics.drawable.LayerDrawable.ChildDrawable[length];
        for (int i = 0; i < length; i++) {
            childDrawableArr[i] = new android.graphics.drawable.LayerDrawable.ChildDrawable(this.mLayerState.mDensity);
            android.graphics.drawable.Drawable drawable = drawableArr[i];
            childDrawableArr[i].mDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                android.graphics.drawable.LayerDrawable.LayerState layerState2 = this.mLayerState;
                layerState2.mChildrenChangingConfigurations = drawable.getChangingConfigurations() | layerState2.mChildrenChangingConfigurations;
            }
        }
        this.mLayerState.mNumChildren = length;
        this.mLayerState.mChildren = childDrawableArr;
        ensurePadding();
        refreshPadding();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    LayerDrawable() {
        this((android.graphics.drawable.LayerDrawable.LayerState) null, (android.content.res.Resources) null);
    }

    LayerDrawable(android.graphics.drawable.LayerDrawable.LayerState layerState, android.content.res.Resources resources) {
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpOutRect = new android.graphics.Rect();
        this.mTmpContainer = new android.graphics.Rect();
        this.mLayerState = createConstantState(layerState, resources);
        if (this.mLayerState.mNumChildren > 0) {
            ensurePadding();
            refreshPadding();
        }
    }

    android.graphics.drawable.LayerDrawable.LayerState createConstantState(android.graphics.drawable.LayerDrawable.LayerState layerState, android.content.res.Resources resources) {
        return new android.graphics.drawable.LayerDrawable.LayerState(layerState, this, resources);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        int resolveDensity = android.graphics.drawable.Drawable.resolveDensity(resources, 0);
        layerState.setDensity(resolveDensity);
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.LayerDrawable);
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
        int i = layerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            childDrawableArr[i2].setDensity(resolveDensity);
        }
        inflateLayers(resources, xmlPullParser, attributeSet, theme);
        ensurePadding();
        refreshPadding();
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        int resolveDensity = android.graphics.drawable.Drawable.resolveDensity(theme.getResources(), 0);
        layerState.setDensity(resolveDensity);
        if (layerState.mThemeAttrs != null) {
            android.content.res.TypedArray resolveAttributes = theme.resolveAttributes(layerState.mThemeAttrs, com.android.internal.R.styleable.LayerDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
        int i = layerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = childDrawableArr[i2];
            childDrawable.setDensity(resolveDensity);
            if (childDrawable.mThemeAttrs != null) {
                android.content.res.TypedArray resolveAttributes2 = theme.resolveAttributes(childDrawable.mThemeAttrs, com.android.internal.R.styleable.LayerDrawableItem);
                updateLayerFromTypedArray(childDrawable, resolveAttributes2);
                resolveAttributes2.recycle();
            }
            android.graphics.drawable.Drawable drawable = childDrawable.mDrawable;
            if (drawable != null && drawable.canApplyTheme()) {
                drawable.applyTheme(theme);
                layerState.mChildrenChangingConfigurations = drawable.getChangingConfigurations() | layerState.mChildrenChangingConfigurations;
            }
        }
    }

    private void inflateLayers(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 >= depth || next2 != 3) {
                    if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                        android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = new android.graphics.drawable.LayerDrawable.ChildDrawable(layerState.mDensity);
                        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.LayerDrawableItem);
                        updateLayerFromTypedArray(childDrawable, obtainAttributes);
                        obtainAttributes.recycle();
                        if (childDrawable.mDrawable == null && (childDrawable.mThemeAttrs == null || childDrawable.mThemeAttrs[4] == 0)) {
                            do {
                                next = xmlPullParser.next();
                            } while (next == 4);
                            if (next != 2) {
                                throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                            }
                            childDrawable.mDrawable = android.graphics.drawable.Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                            childDrawable.mDrawable.setCallback(this);
                            layerState.mChildrenChangingConfigurations |= childDrawable.mDrawable.getChangingConfigurations();
                        }
                        addLayer(childDrawable);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        layerState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        layerState.mThemeAttrs = typedArray.extractThemeAttrs();
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case 0:
                    layerState.mPaddingLeft = typedArray.getDimensionPixelOffset(index, layerState.mPaddingLeft);
                    break;
                case 1:
                    layerState.mPaddingTop = typedArray.getDimensionPixelOffset(index, layerState.mPaddingTop);
                    break;
                case 2:
                    layerState.mPaddingRight = typedArray.getDimensionPixelOffset(index, layerState.mPaddingRight);
                    break;
                case 3:
                    layerState.mPaddingBottom = typedArray.getDimensionPixelOffset(index, layerState.mPaddingBottom);
                    break;
                case 4:
                    layerState.mOpacityOverride = typedArray.getInt(index, layerState.mOpacityOverride);
                    break;
                case 5:
                    layerState.mPaddingStart = typedArray.getDimensionPixelOffset(index, layerState.mPaddingStart);
                    break;
                case 6:
                    layerState.mPaddingEnd = typedArray.getDimensionPixelOffset(index, layerState.mPaddingEnd);
                    break;
                case 7:
                    layerState.mAutoMirrored = typedArray.getBoolean(index, layerState.mAutoMirrored);
                    break;
                case 8:
                    layerState.mPaddingMode = typedArray.getInteger(index, layerState.mPaddingMode);
                    break;
            }
        }
    }

    private void updateLayerFromTypedArray(android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable, android.content.res.TypedArray typedArray) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        layerState.mChildrenChangingConfigurations |= typedArray.getChangingConfigurations();
        childDrawable.mThemeAttrs = typedArray.extractThemeAttrs();
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case 0:
                    childDrawable.mGravity = typedArray.getInteger(index, childDrawable.mGravity);
                    break;
                case 1:
                    childDrawable.mId = typedArray.getResourceId(index, childDrawable.mId);
                    break;
                case 2:
                    childDrawable.mHeight = typedArray.getDimensionPixelSize(index, childDrawable.mHeight);
                    break;
                case 3:
                    childDrawable.mWidth = typedArray.getDimensionPixelSize(index, childDrawable.mWidth);
                    break;
                case 5:
                    childDrawable.mInsetL = typedArray.getDimensionPixelOffset(index, childDrawable.mInsetL);
                    break;
                case 6:
                    childDrawable.mInsetT = typedArray.getDimensionPixelOffset(index, childDrawable.mInsetT);
                    break;
                case 7:
                    childDrawable.mInsetR = typedArray.getDimensionPixelOffset(index, childDrawable.mInsetR);
                    break;
                case 8:
                    childDrawable.mInsetB = typedArray.getDimensionPixelOffset(index, childDrawable.mInsetB);
                    break;
                case 9:
                    childDrawable.mInsetS = typedArray.getDimensionPixelOffset(index, childDrawable.mInsetS);
                    break;
                case 10:
                    childDrawable.mInsetE = typedArray.getDimensionPixelOffset(index, childDrawable.mInsetE);
                    break;
            }
        }
        android.graphics.drawable.Drawable drawable = typedArray.getDrawable(4);
        if (drawable != null) {
            if (childDrawable.mDrawable != null) {
                childDrawable.mDrawable.setCallback(null);
            }
            childDrawable.mDrawable = drawable;
            childDrawable.mDrawable.setCallback(this);
            layerState.mChildrenChangingConfigurations = childDrawable.mDrawable.getChangingConfigurations() | layerState.mChildrenChangingConfigurations;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mLayerState.canApplyTheme() || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isProjected() {
        if (super.isProjected()) {
            return true;
        }
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null && drawable.isProjected()) {
                return true;
            }
        }
        return false;
    }

    int addLayer(android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        int length = layerState.mChildren != null ? layerState.mChildren.length : 0;
        int i = layerState.mNumChildren;
        if (i >= length) {
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = new android.graphics.drawable.LayerDrawable.ChildDrawable[length + 10];
            if (i > 0) {
                java.lang.System.arraycopy(layerState.mChildren, 0, childDrawableArr, 0, i);
            }
            layerState.mChildren = childDrawableArr;
        }
        layerState.mChildren[i] = childDrawable;
        layerState.mNumChildren++;
        layerState.invalidateCache();
        return i;
    }

    android.graphics.drawable.LayerDrawable.ChildDrawable addLayer(android.graphics.drawable.Drawable drawable, int[] iArr, int i, int i2, int i3, int i4, int i5) {
        android.graphics.drawable.LayerDrawable.ChildDrawable createLayer = createLayer(drawable);
        createLayer.mId = i;
        createLayer.mThemeAttrs = iArr;
        createLayer.mDrawable.setAutoMirrored(isAutoMirrored());
        createLayer.mInsetL = i2;
        createLayer.mInsetT = i3;
        createLayer.mInsetR = i4;
        createLayer.mInsetB = i5;
        addLayer(createLayer);
        this.mLayerState.mChildrenChangingConfigurations |= drawable.getChangingConfigurations();
        drawable.setCallback(this);
        return createLayer;
    }

    private android.graphics.drawable.LayerDrawable.ChildDrawable createLayer(android.graphics.drawable.Drawable drawable) {
        android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = new android.graphics.drawable.LayerDrawable.ChildDrawable(this.mLayerState.mDensity);
        childDrawable.mDrawable = drawable;
        return childDrawable;
    }

    public int addLayer(android.graphics.drawable.Drawable drawable) {
        android.graphics.drawable.LayerDrawable.ChildDrawable createLayer = createLayer(drawable);
        int addLayer = addLayer(createLayer);
        ensurePadding();
        refreshChildPadding(addLayer, createLayer);
        return addLayer;
    }

    public android.graphics.drawable.Drawable findDrawableByLayerId(int i) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i2 = this.mLayerState.mNumChildren - 1; i2 >= 0; i2--) {
            if (childDrawableArr[i2].mId == i) {
                return childDrawableArr[i2].mDrawable;
            }
        }
        return null;
    }

    public void setId(int i, int i2) {
        this.mLayerState.mChildren[i].mId = i2;
    }

    public int getId(int i) {
        if (i >= this.mLayerState.mNumChildren) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mLayerState.mChildren[i].mId;
    }

    public int getNumberOfLayers() {
        return this.mLayerState.mNumChildren;
    }

    public boolean setDrawableByLayerId(int i, android.graphics.drawable.Drawable drawable) {
        int findIndexByLayerId = findIndexByLayerId(i);
        if (findIndexByLayerId < 0) {
            return false;
        }
        setDrawable(findIndexByLayerId, drawable);
        return true;
    }

    public int findIndexByLayerId(int i) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNumChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            if (childDrawableArr[i3].mId == i) {
                return i3;
            }
        }
        return -1;
    }

    public void setDrawable(int i, android.graphics.drawable.Drawable drawable) {
        if (i >= this.mLayerState.mNumChildren) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = this.mLayerState.mChildren[i];
        if (childDrawable.mDrawable != null) {
            if (drawable != null) {
                drawable.setBounds(childDrawable.mDrawable.getBounds());
            }
            childDrawable.mDrawable.setCallback(null);
        }
        if (drawable != null) {
            drawable.setCallback(this);
        }
        childDrawable.mDrawable = drawable;
        this.mLayerState.invalidateCache();
        refreshChildPadding(i, childDrawable);
    }

    public android.graphics.drawable.Drawable getDrawable(int i) {
        if (i >= this.mLayerState.mNumChildren) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mLayerState.mChildren[i].mDrawable;
    }

    public void setLayerSize(int i, int i2, int i3) {
        android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = this.mLayerState.mChildren[i];
        childDrawable.mWidth = i2;
        childDrawable.mHeight = i3;
    }

    public void setLayerWidth(int i, int i2) {
        this.mLayerState.mChildren[i].mWidth = i2;
    }

    public int getLayerWidth(int i) {
        return this.mLayerState.mChildren[i].mWidth;
    }

    public void setLayerHeight(int i, int i2) {
        this.mLayerState.mChildren[i].mHeight = i2;
    }

    public int getLayerHeight(int i) {
        return this.mLayerState.mChildren[i].mHeight;
    }

    public void setLayerGravity(int i, int i2) {
        this.mLayerState.mChildren[i].mGravity = i2;
    }

    public int getLayerGravity(int i) {
        return this.mLayerState.mChildren[i].mGravity;
    }

    public void setLayerInset(int i, int i2, int i3, int i4, int i5) {
        setLayerInsetInternal(i, i2, i3, i4, i5, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public void setLayerInsetRelative(int i, int i2, int i3, int i4, int i5) {
        setLayerInsetInternal(i, 0, i3, 0, i5, i2, i4);
    }

    public void setLayerInsetLeft(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetL = i2;
    }

    public int getLayerInsetLeft(int i) {
        return this.mLayerState.mChildren[i].mInsetL;
    }

    public void setLayerInsetRight(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetR = i2;
    }

    public int getLayerInsetRight(int i) {
        return this.mLayerState.mChildren[i].mInsetR;
    }

    public void setLayerInsetTop(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetT = i2;
    }

    public int getLayerInsetTop(int i) {
        return this.mLayerState.mChildren[i].mInsetT;
    }

    public void setLayerInsetBottom(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetB = i2;
    }

    public int getLayerInsetBottom(int i) {
        return this.mLayerState.mChildren[i].mInsetB;
    }

    public void setLayerInsetStart(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetS = i2;
    }

    public int getLayerInsetStart(int i) {
        return this.mLayerState.mChildren[i].mInsetS;
    }

    public void setLayerInsetEnd(int i, int i2) {
        this.mLayerState.mChildren[i].mInsetE = i2;
    }

    public int getLayerInsetEnd(int i) {
        return this.mLayerState.mChildren[i].mInsetE;
    }

    private void setLayerInsetInternal(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = this.mLayerState.mChildren[i];
        childDrawable.mInsetL = i2;
        childDrawable.mInsetT = i3;
        childDrawable.mInsetR = i4;
        childDrawable.mInsetB = i5;
        childDrawable.mInsetS = i6;
        childDrawable.mInsetE = i7;
    }

    public void setPaddingMode(int i) {
        if (this.mLayerState.mPaddingMode != i) {
            this.mLayerState.mPaddingMode = i;
        }
    }

    public int getPaddingMode() {
        return this.mLayerState.mPaddingMode;
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
            this.mLayerState.invalidateCache();
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
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mLayerState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(android.graphics.Rect rect) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        if (layerState.mPaddingMode == 0) {
            computeNestedPadding(rect);
        } else {
            computeStackedPadding(rect);
        }
        int i = layerState.mPaddingTop;
        int i2 = layerState.mPaddingBottom;
        boolean z = getLayoutDirection() == 1;
        int i3 = z ? layerState.mPaddingEnd : layerState.mPaddingStart;
        int i4 = z ? layerState.mPaddingStart : layerState.mPaddingEnd;
        if (i3 < 0) {
            i3 = layerState.mPaddingLeft;
        }
        if (i4 < 0) {
            i4 = layerState.mPaddingRight;
        }
        if (i3 >= 0) {
            rect.left = i3;
        }
        if (i >= 0) {
            rect.top = i;
        }
        if (i4 >= 0) {
            rect.right = i4;
        }
        if (i2 >= 0) {
            rect.bottom = i2;
        }
        return (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) ? false : true;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        layerState.mPaddingLeft = i;
        layerState.mPaddingTop = i2;
        layerState.mPaddingRight = i3;
        layerState.mPaddingBottom = i4;
        layerState.mPaddingStart = -1;
        layerState.mPaddingEnd = -1;
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        android.graphics.drawable.LayerDrawable.LayerState layerState = this.mLayerState;
        layerState.mPaddingStart = i;
        layerState.mPaddingTop = i2;
        layerState.mPaddingEnd = i3;
        layerState.mPaddingBottom = i4;
        layerState.mPaddingLeft = -1;
        layerState.mPaddingRight = -1;
    }

    public int getLeftPadding() {
        return this.mLayerState.mPaddingLeft;
    }

    public int getRightPadding() {
        return this.mLayerState.mPaddingRight;
    }

    public int getStartPadding() {
        return this.mLayerState.mPaddingStart;
    }

    public int getEndPadding() {
        return this.mLayerState.mPaddingEnd;
    }

    public int getTopPadding() {
        return this.mLayerState.mPaddingTop;
    }

    public int getBottomPadding() {
        return this.mLayerState.mPaddingBottom;
    }

    private void computeNestedPadding(android.graphics.Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            refreshChildPadding(i2, childDrawableArr[i2]);
            rect.left += this.mPaddingL[i2];
            rect.top += this.mPaddingT[i2];
            rect.right += this.mPaddingR[i2];
            rect.bottom += this.mPaddingB[i2];
        }
    }

    private void computeStackedPadding(android.graphics.Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            refreshChildPadding(i2, childDrawableArr[i2]);
            rect.left = java.lang.Math.max(rect.left, this.mPaddingL[i2]);
            rect.top = java.lang.Math.max(rect.top, this.mPaddingT[i2]);
            rect.right = java.lang.Math.max(rect.right, this.mPaddingR[i2]);
            rect.bottom = java.lang.Math.max(rect.bottom, this.mPaddingB[i2]);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(android.graphics.Outline outline) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.getOutline(outline);
                if (!outline.isEmpty()) {
                    return;
                }
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setHotspot(f, f2);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i5 = this.mLayerState.mNumChildren;
        for (int i6 = 0; i6 < i5; i6++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i6].mDrawable;
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
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setVisible(z, z2);
            }
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setDither(z);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNumChildren;
        for (int i3 = 0; i3 < i2; i3++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i3].mDrawable;
            if (drawable != null) {
                drawable.setAlpha(i);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        android.graphics.drawable.Drawable firstNonNullDrawable = getFirstNonNullDrawable();
        if (firstNonNullDrawable != null) {
            return firstNonNullDrawable.getAlpha();
        }
        return super.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(android.content.res.ColorStateList colorStateList) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setTintList(colorStateList);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.setTintBlendMode(blendMode);
            }
        }
    }

    private android.graphics.drawable.Drawable getFirstNonNullDrawable() {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                return drawable;
            }
        }
        return null;
    }

    public void setOpacity(int i) {
        this.mLayerState.mOpacityOverride = i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mLayerState.mOpacityOverride != 0) {
            return this.mLayerState.mOpacityOverride;
        }
        return this.mLayerState.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mLayerState.mAutoMirrored = z;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
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
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
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
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null && drawable.isStateful() && drawable.setState(iArr)) {
                refreshChildPadding(i2, childDrawableArr[i2]);
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
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNumChildren;
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i3].mDrawable;
            if (drawable != null && drawable.setLevel(i)) {
                refreshChildPadding(i3, childDrawableArr[i3]);
                z = true;
            }
        }
        if (z) {
            updateLayerBounds(getBounds());
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        updateLayerBounds(rect);
    }

    private void updateLayerBounds(android.graphics.Rect rect) {
        try {
            suspendChildInvalidation();
            updateLayerBoundsInternal(rect);
        } finally {
            resumeChildInvalidation();
        }
    }

    private void updateLayerBoundsInternal(android.graphics.Rect rect) {
        boolean z;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr;
        android.graphics.Rect rect2 = this.mTmpOutRect;
        int layoutDirection = getLayoutDirection();
        boolean z2 = layoutDirection == 1;
        boolean z3 = this.mLayerState.mPaddingMode == 0;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr2 = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i2 < i) {
            android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = childDrawableArr2[i2];
            android.graphics.drawable.Drawable drawable = childDrawable.mDrawable;
            if (drawable == null) {
                z = z2;
                childDrawableArr = childDrawableArr2;
            } else {
                int i7 = childDrawable.mInsetT;
                int i8 = childDrawable.mInsetB;
                int i9 = z2 ? childDrawable.mInsetE : childDrawable.mInsetS;
                int i10 = z2 ? childDrawable.mInsetS : childDrawable.mInsetE;
                z = z2;
                if (i9 == Integer.MIN_VALUE) {
                    i9 = childDrawable.mInsetL;
                }
                if (i10 == Integer.MIN_VALUE) {
                    i10 = childDrawable.mInsetR;
                }
                android.graphics.Rect rect3 = this.mTmpContainer;
                childDrawableArr = childDrawableArr2;
                rect3.set(rect.left + i9 + i3, rect.top + i7 + i4, (rect.right - i10) - i5, (rect.bottom - i8) - i6);
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int i11 = childDrawable.mWidth;
                int i12 = childDrawable.mHeight;
                int resolveGravity = resolveGravity(childDrawable.mGravity, i11, i12, intrinsicWidth, intrinsicHeight);
                if (i11 >= 0) {
                    intrinsicWidth = i11;
                }
                if (i12 >= 0) {
                    intrinsicHeight = i12;
                }
                android.view.Gravity.apply(resolveGravity, intrinsicWidth, intrinsicHeight, rect3, rect2, layoutDirection);
                drawable.setBounds(rect2);
                if (z3) {
                    i3 += this.mPaddingL[i2];
                    i5 += this.mPaddingR[i2];
                    i4 += this.mPaddingT[i2];
                    i6 += this.mPaddingB[i2];
                }
            }
            i2++;
            z2 = z;
            childDrawableArr2 = childDrawableArr;
        }
    }

    private static int resolveGravity(int i, int i2, int i3, int i4, int i5) {
        if (!android.view.Gravity.isHorizontal(i)) {
            if (i2 < 0) {
                i |= 7;
            } else {
                i |= android.view.Gravity.START;
            }
        }
        if (!android.view.Gravity.isVertical(i)) {
            if (i3 < 0) {
                i |= 112;
            } else {
                i |= 48;
            }
        }
        if (i2 < 0 && i4 < 0) {
            i |= 7;
        }
        if (i3 < 0 && i5 < 0) {
            return i | 112;
        }
        return i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        boolean z = this.mLayerState.mPaddingMode == 0;
        boolean z2 = getLayoutDirection() == 1;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        int i2 = 0;
        int i3 = 0;
        int i4 = -1;
        for (int i5 = 0; i5 < i; i5++) {
            android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = childDrawableArr[i5];
            if (childDrawable.mDrawable != null) {
                int i6 = z2 ? childDrawable.mInsetE : childDrawable.mInsetS;
                int i7 = z2 ? childDrawable.mInsetS : childDrawable.mInsetE;
                if (i6 == Integer.MIN_VALUE) {
                    i6 = childDrawable.mInsetL;
                }
                if (i7 == Integer.MIN_VALUE) {
                    i7 = childDrawable.mInsetR;
                }
                int intrinsicWidth = childDrawable.mWidth < 0 ? childDrawable.mDrawable.getIntrinsicWidth() : childDrawable.mWidth;
                int i8 = intrinsicWidth < 0 ? -1 : intrinsicWidth + i6 + i7 + i2 + i3;
                if (i8 > i4) {
                    i4 = i8;
                }
                if (z) {
                    i2 += this.mPaddingL[i5];
                    i3 += this.mPaddingR[i5];
                }
            }
        }
        return i4;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        boolean z = this.mLayerState.mPaddingMode == 0;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        int i2 = 0;
        int i3 = 0;
        int i4 = -1;
        for (int i5 = 0; i5 < i; i5++) {
            android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable = childDrawableArr[i5];
            if (childDrawable.mDrawable != null) {
                int intrinsicHeight = childDrawable.mHeight < 0 ? childDrawable.mDrawable.getIntrinsicHeight() : childDrawable.mHeight;
                int i6 = intrinsicHeight < 0 ? -1 : intrinsicHeight + childDrawable.mInsetT + childDrawable.mInsetB + i2 + i3;
                if (i6 > i4) {
                    i4 = i6;
                }
                if (z) {
                    i2 += this.mPaddingT[i5];
                    i3 += this.mPaddingB[i5];
                }
            }
        }
        return i4;
    }

    private boolean refreshChildPadding(int i, android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable) {
        if (childDrawable.mDrawable != null) {
            android.graphics.Rect rect = this.mTmpRect;
            childDrawable.mDrawable.getPadding(rect);
            if (rect.left != this.mPaddingL[i] || rect.top != this.mPaddingT[i] || rect.right != this.mPaddingR[i] || rect.bottom != this.mPaddingB[i]) {
                this.mPaddingL[i] = rect.left;
                this.mPaddingT[i] = rect.top;
                this.mPaddingR[i] = rect.right;
                this.mPaddingB[i] = rect.bottom;
                return true;
            }
            return false;
        }
        return false;
    }

    void ensurePadding() {
        int i = this.mLayerState.mNumChildren;
        if (this.mPaddingL != null && this.mPaddingL.length >= i) {
            return;
        }
        this.mPaddingL = new int[i];
        this.mPaddingT = new int[i];
        this.mPaddingR = new int[i];
        this.mPaddingB = new int[i];
    }

    void refreshPadding() {
        int i = this.mLayerState.mNumChildren;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            refreshChildPadding(i2, childDrawableArr[i2]);
        }
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
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
            int i = this.mLayerState.mNumChildren;
            for (int i2 = 0; i2 < i; i2++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
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
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
            if (drawable != null) {
                drawable.clearMutated();
            }
        }
        this.mMutated = false;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i2 = this.mLayerState.mNumChildren;
        boolean z = false;
        for (int i3 = 0; i3 < i2; i3++) {
            android.graphics.drawable.Drawable drawable = childDrawableArr[i3].mDrawable;
            if (drawable != null) {
                z |= drawable.setLayoutDirection(i);
            }
        }
        updateLayerBounds(getBounds());
        return z;
    }

    static class ChildDrawable {
        public int mDensity;
        public android.graphics.drawable.Drawable mDrawable;
        public int mGravity;
        public int mHeight;
        public int mId;
        public int mInsetB;
        public int mInsetE;
        public int mInsetL;
        public int mInsetR;
        public int mInsetS;
        public int mInsetT;
        public int[] mThemeAttrs;
        public int mWidth;

        ChildDrawable(int i) {
            this.mDensity = 160;
            this.mInsetS = Integer.MIN_VALUE;
            this.mInsetE = Integer.MIN_VALUE;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mGravity = 0;
            this.mId = -1;
            this.mDensity = i;
        }

        ChildDrawable(android.graphics.drawable.LayerDrawable.ChildDrawable childDrawable, android.graphics.drawable.LayerDrawable layerDrawable, android.content.res.Resources resources) {
            android.graphics.drawable.Drawable drawable;
            this.mDensity = 160;
            this.mInsetS = Integer.MIN_VALUE;
            this.mInsetE = Integer.MIN_VALUE;
            this.mWidth = -1;
            this.mHeight = -1;
            this.mGravity = 0;
            this.mId = -1;
            android.graphics.drawable.Drawable drawable2 = childDrawable.mDrawable;
            if (drawable2 != null) {
                android.graphics.drawable.Drawable.ConstantState constantState = drawable2.getConstantState();
                if (constantState == null) {
                    if (drawable2.getCallback() != null) {
                        android.util.Log.w(android.graphics.drawable.LayerDrawable.LOG_TAG, "Invalid drawable added to LayerDrawable! Drawable already belongs to another owner but does not expose a constant state.", new java.lang.RuntimeException());
                    }
                    drawable = drawable2;
                } else if (resources != null) {
                    drawable = constantState.newDrawable(resources);
                } else {
                    drawable = constantState.newDrawable();
                }
                drawable.setLayoutDirection(drawable2.getLayoutDirection());
                drawable.setBounds(drawable2.getBounds());
                drawable.setLevel(drawable2.getLevel());
                drawable.setCallback(layerDrawable);
            } else {
                drawable = null;
            }
            this.mDrawable = drawable;
            this.mThemeAttrs = childDrawable.mThemeAttrs;
            this.mInsetL = childDrawable.mInsetL;
            this.mInsetT = childDrawable.mInsetT;
            this.mInsetR = childDrawable.mInsetR;
            this.mInsetB = childDrawable.mInsetB;
            this.mInsetS = childDrawable.mInsetS;
            this.mInsetE = childDrawable.mInsetE;
            this.mWidth = childDrawable.mWidth;
            this.mHeight = childDrawable.mHeight;
            this.mGravity = childDrawable.mGravity;
            this.mId = childDrawable.mId;
            this.mDensity = android.graphics.drawable.Drawable.resolveDensity(resources, childDrawable.mDensity);
            if (childDrawable.mDensity != this.mDensity) {
                applyDensityScaling(childDrawable.mDensity, this.mDensity);
            }
        }

        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || (this.mDrawable != null && this.mDrawable.canApplyTheme());
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                int i2 = this.mDensity;
                this.mDensity = i;
                applyDensityScaling(i2, i);
            }
        }

        private void applyDensityScaling(int i, int i2) {
            this.mInsetL = android.graphics.drawable.Drawable.scaleFromDensity(this.mInsetL, i, i2, false);
            this.mInsetT = android.graphics.drawable.Drawable.scaleFromDensity(this.mInsetT, i, i2, false);
            this.mInsetR = android.graphics.drawable.Drawable.scaleFromDensity(this.mInsetR, i, i2, false);
            this.mInsetB = android.graphics.drawable.Drawable.scaleFromDensity(this.mInsetB, i, i2, false);
            if (this.mInsetS != Integer.MIN_VALUE) {
                this.mInsetS = android.graphics.drawable.Drawable.scaleFromDensity(this.mInsetS, i, i2, false);
            }
            if (this.mInsetE != Integer.MIN_VALUE) {
                this.mInsetE = android.graphics.drawable.Drawable.scaleFromDensity(this.mInsetE, i, i2, false);
            }
            if (this.mWidth > 0) {
                this.mWidth = android.graphics.drawable.Drawable.scaleFromDensity(this.mWidth, i, i2, true);
            }
            if (this.mHeight > 0) {
                this.mHeight = android.graphics.drawable.Drawable.scaleFromDensity(this.mHeight, i, i2, true);
            }
        }
    }

    static class LayerState extends android.graphics.drawable.Drawable.ConstantState {
        private boolean mAutoMirrored;
        int mChangingConfigurations;
        private boolean mCheckedOpacity;
        private boolean mCheckedStateful;
        android.graphics.drawable.LayerDrawable.ChildDrawable[] mChildren;
        int mChildrenChangingConfigurations;
        int mDensity;
        private boolean mIsStateful;
        int mNumChildren;
        private int mOpacity;
        int mOpacityOverride;
        int mPaddingBottom;
        int mPaddingEnd;
        int mPaddingLeft;
        private int mPaddingMode;
        int mPaddingRight;
        int mPaddingStart;
        int mPaddingTop;
        private int[] mThemeAttrs;

        LayerState(android.graphics.drawable.LayerDrawable.LayerState layerState, android.graphics.drawable.LayerDrawable layerDrawable, android.content.res.Resources resources) {
            this.mPaddingTop = -1;
            this.mPaddingBottom = -1;
            this.mPaddingLeft = -1;
            this.mPaddingRight = -1;
            this.mPaddingStart = -1;
            this.mPaddingEnd = -1;
            this.mOpacityOverride = 0;
            this.mAutoMirrored = false;
            this.mPaddingMode = 0;
            this.mDensity = android.graphics.drawable.Drawable.resolveDensity(resources, layerState != null ? layerState.mDensity : 0);
            if (layerState != null) {
                android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
                int i = layerState.mNumChildren;
                this.mNumChildren = i;
                this.mChildren = new android.graphics.drawable.LayerDrawable.ChildDrawable[i];
                this.mChangingConfigurations = layerState.mChangingConfigurations;
                this.mChildrenChangingConfigurations = layerState.mChildrenChangingConfigurations;
                for (int i2 = 0; i2 < i; i2++) {
                    this.mChildren[i2] = new android.graphics.drawable.LayerDrawable.ChildDrawable(childDrawableArr[i2], layerDrawable, resources);
                }
                this.mCheckedOpacity = layerState.mCheckedOpacity;
                this.mOpacity = layerState.mOpacity;
                this.mCheckedStateful = layerState.mCheckedStateful;
                this.mIsStateful = layerState.mIsStateful;
                this.mAutoMirrored = layerState.mAutoMirrored;
                this.mPaddingMode = layerState.mPaddingMode;
                this.mThemeAttrs = layerState.mThemeAttrs;
                this.mPaddingTop = layerState.mPaddingTop;
                this.mPaddingBottom = layerState.mPaddingBottom;
                this.mPaddingLeft = layerState.mPaddingLeft;
                this.mPaddingRight = layerState.mPaddingRight;
                this.mPaddingStart = layerState.mPaddingStart;
                this.mPaddingEnd = layerState.mPaddingEnd;
                this.mOpacityOverride = layerState.mOpacityOverride;
                if (layerState.mDensity != this.mDensity) {
                    applyDensityScaling(layerState.mDensity, this.mDensity);
                    return;
                }
                return;
            }
            this.mNumChildren = 0;
            this.mChildren = null;
        }

        public final void setDensity(int i) {
            if (this.mDensity != i) {
                int i2 = this.mDensity;
                this.mDensity = i;
                onDensityChanged(i2, i);
            }
        }

        protected void onDensityChanged(int i, int i2) {
            applyDensityScaling(i, i2);
        }

        private void applyDensityScaling(int i, int i2) {
            if (this.mPaddingLeft > 0) {
                this.mPaddingLeft = android.graphics.drawable.Drawable.scaleFromDensity(this.mPaddingLeft, i, i2, false);
            }
            if (this.mPaddingTop > 0) {
                this.mPaddingTop = android.graphics.drawable.Drawable.scaleFromDensity(this.mPaddingTop, i, i2, false);
            }
            if (this.mPaddingRight > 0) {
                this.mPaddingRight = android.graphics.drawable.Drawable.scaleFromDensity(this.mPaddingRight, i, i2, false);
            }
            if (this.mPaddingBottom > 0) {
                this.mPaddingBottom = android.graphics.drawable.Drawable.scaleFromDensity(this.mPaddingBottom, i, i2, false);
            }
            if (this.mPaddingStart > 0) {
                this.mPaddingStart = android.graphics.drawable.Drawable.scaleFromDensity(this.mPaddingStart, i, i2, false);
            }
            if (this.mPaddingEnd > 0) {
                this.mPaddingEnd = android.graphics.drawable.Drawable.scaleFromDensity(this.mPaddingEnd, i, i2, false);
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null || super.canApplyTheme()) {
                return true;
            }
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            int i = this.mNumChildren;
            for (int i2 = 0; i2 < i; i2++) {
                if (childDrawableArr[i2].canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.LayerDrawable(this, (android.content.res.Resources) null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.LayerDrawable(this, resources);
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
            int i2 = this.mNumChildren;
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    i3 = -1;
                    break;
                }
                if (childDrawableArr[i3].mDrawable != null) {
                    break;
                }
                i3++;
            }
            if (i3 >= 0) {
                i = childDrawableArr[i3].mDrawable.getOpacity();
            } else {
                i = -2;
            }
            for (int i4 = i3 + 1; i4 < i2; i4++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i4].mDrawable;
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
            int i = this.mNumChildren;
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 < i) {
                    android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
                    if (drawable == null || !drawable.isStateful()) {
                        i2++;
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
            int i = this.mNumChildren;
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            for (int i2 = 0; i2 < i; i2++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
                if (drawable != null && drawable.hasFocusStateSpecified()) {
                    return true;
                }
            }
            return false;
        }

        public final boolean canConstantState() {
            android.graphics.drawable.LayerDrawable.ChildDrawable[] childDrawableArr = this.mChildren;
            int i = this.mNumChildren;
            for (int i2 = 0; i2 < i; i2++) {
                android.graphics.drawable.Drawable drawable = childDrawableArr[i2].mDrawable;
                if (drawable != null && drawable.getConstantState() == null) {
                    return false;
                }
            }
            return true;
        }

        void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }
    }
}
