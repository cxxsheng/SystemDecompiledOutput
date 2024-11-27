package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class ImageView extends android.view.View {
    private static final java.lang.String LOG_TAG = "ImageView";
    private static boolean sCompatAdjustViewBounds;
    private static boolean sCompatDone;
    private static boolean sCompatDrawableVisibilityDispatch;
    private static boolean sCompatUseCorrectStreamDensity;
    private boolean mAdjustViewBounds;
    private int mAlpha;
    private int mBaseline;
    private boolean mBaselineAlignBottom;
    private android.graphics.ColorFilter mColorFilter;
    private boolean mCropToPadding;
    private android.graphics.Matrix mDrawMatrix;
    private android.graphics.drawable.Drawable mDrawable;
    private android.graphics.BlendMode mDrawableBlendMode;
    private int mDrawableHeight;
    private android.content.res.ColorStateList mDrawableTintList;
    private int mDrawableWidth;
    private boolean mHasAlpha;
    private boolean mHasColorFilter;
    private boolean mHasDrawableBlendMode;
    private boolean mHasDrawableTint;
    private boolean mHasLevelSet;
    private boolean mHasXfermode;
    private boolean mHaveFrame;
    private int mLevel;
    private android.graphics.Matrix mMatrix;
    private int mMaxHeight;
    private int mMaxWidth;
    private boolean mMergeState;
    private android.graphics.drawable.BitmapDrawable mRecycleableBitmapDrawable;
    private int mResource;
    private android.widget.ImageView.ScaleType mScaleType;
    private int[] mState;
    private final android.graphics.RectF mTempDst;
    private final android.graphics.RectF mTempSrc;
    private android.net.Uri mUri;
    private final int mViewAlphaScale;
    private android.graphics.Xfermode mXfermode;
    private static final android.widget.ImageView.ScaleType[] sScaleTypeArray = {android.widget.ImageView.ScaleType.MATRIX, android.widget.ImageView.ScaleType.FIT_XY, android.widget.ImageView.ScaleType.FIT_START, android.widget.ImageView.ScaleType.FIT_CENTER, android.widget.ImageView.ScaleType.FIT_END, android.widget.ImageView.ScaleType.CENTER, android.widget.ImageView.ScaleType.CENTER_CROP, android.widget.ImageView.ScaleType.CENTER_INSIDE};
    private static final android.graphics.Matrix.ScaleToFit[] sS2FArray = {android.graphics.Matrix.ScaleToFit.FILL, android.graphics.Matrix.ScaleToFit.START, android.graphics.Matrix.ScaleToFit.CENTER, android.graphics.Matrix.ScaleToFit.END};

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.ImageView> {
        private int mAdjustViewBoundsId;
        private int mBaselineAlignBottomId;
        private int mBaselineId;
        private int mBlendModeId;
        private int mCropToPaddingId;
        private int mMaxHeightId;
        private int mMaxWidthId;
        private boolean mPropertiesMapped = false;
        private int mScaleTypeId;
        private int mSrcId;
        private int mTintId;
        private int mTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mAdjustViewBoundsId = propertyMapper.mapBoolean("adjustViewBounds", 16843038);
            this.mBaselineId = propertyMapper.mapInt("baseline", 16843548);
            this.mBaselineAlignBottomId = propertyMapper.mapBoolean("baselineAlignBottom", 16843042);
            this.mBlendModeId = propertyMapper.mapObject("blendMode", 9);
            this.mCropToPaddingId = propertyMapper.mapBoolean("cropToPadding", 16843043);
            this.mMaxHeightId = propertyMapper.mapInt("maxHeight", 16843040);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mScaleTypeId = propertyMapper.mapObject("scaleType", 16843037);
            this.mSrcId = propertyMapper.mapObject("src", 16843033);
            this.mTintId = propertyMapper.mapObject("tint", 16843041);
            this.mTintModeId = propertyMapper.mapObject("tintMode", 16843771);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.ImageView imageView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAdjustViewBoundsId, imageView.getAdjustViewBounds());
            propertyReader.readInt(this.mBaselineId, imageView.getBaseline());
            propertyReader.readBoolean(this.mBaselineAlignBottomId, imageView.getBaselineAlignBottom());
            propertyReader.readObject(this.mBlendModeId, imageView.getImageTintBlendMode());
            propertyReader.readBoolean(this.mCropToPaddingId, imageView.getCropToPadding());
            propertyReader.readInt(this.mMaxHeightId, imageView.getMaxHeight());
            propertyReader.readInt(this.mMaxWidthId, imageView.getMaxWidth());
            propertyReader.readObject(this.mScaleTypeId, imageView.getScaleType());
            propertyReader.readObject(this.mSrcId, imageView.getDrawable());
            propertyReader.readObject(this.mTintId, imageView.getImageTintList());
            propertyReader.readObject(this.mTintModeId, imageView.getImageTintMode());
        }
    }

    public ImageView(android.content.Context context) {
        super(context);
        this.mResource = 0;
        this.mHaveFrame = false;
        this.mAdjustViewBounds = false;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mColorFilter = null;
        this.mHasColorFilter = false;
        this.mHasXfermode = false;
        this.mAlpha = 255;
        this.mHasAlpha = false;
        this.mViewAlphaScale = 256;
        this.mDrawable = null;
        this.mRecycleableBitmapDrawable = null;
        this.mDrawableTintList = null;
        this.mDrawableBlendMode = null;
        this.mHasDrawableTint = false;
        this.mHasDrawableBlendMode = false;
        this.mState = null;
        this.mMergeState = false;
        this.mHasLevelSet = false;
        this.mLevel = 0;
        this.mDrawMatrix = null;
        this.mTempSrc = new android.graphics.RectF();
        this.mTempDst = new android.graphics.RectF();
        this.mBaseline = -1;
        this.mBaselineAlignBottom = false;
        initImageView();
    }

    public ImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mResource = 0;
        this.mHaveFrame = false;
        this.mAdjustViewBounds = false;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mColorFilter = null;
        this.mHasColorFilter = false;
        this.mHasXfermode = false;
        this.mAlpha = 255;
        this.mHasAlpha = false;
        this.mViewAlphaScale = 256;
        this.mDrawable = null;
        this.mRecycleableBitmapDrawable = null;
        this.mDrawableTintList = null;
        this.mDrawableBlendMode = null;
        this.mHasDrawableTint = false;
        this.mHasDrawableBlendMode = false;
        this.mState = null;
        this.mMergeState = false;
        this.mHasLevelSet = false;
        this.mLevel = 0;
        this.mDrawMatrix = null;
        this.mTempSrc = new android.graphics.RectF();
        this.mTempDst = new android.graphics.RectF();
        this.mBaseline = -1;
        this.mBaselineAlignBottom = false;
        initImageView();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ImageView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ImageView, attributeSet, obtainStyledAttributes, i, i2);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            setImageDrawable(drawable);
        }
        this.mBaselineAlignBottom = obtainStyledAttributes.getBoolean(6, false);
        this.mBaseline = obtainStyledAttributes.getDimensionPixelSize(8, -1);
        setAdjustViewBounds(obtainStyledAttributes.getBoolean(2, false));
        setMaxWidth(obtainStyledAttributes.getDimensionPixelSize(3, Integer.MAX_VALUE));
        setMaxHeight(obtainStyledAttributes.getDimensionPixelSize(4, Integer.MAX_VALUE));
        int i3 = obtainStyledAttributes.getInt(1, -1);
        if (i3 >= 0) {
            setScaleType(sScaleTypeArray[i3]);
        }
        if (obtainStyledAttributes.hasValue(5)) {
            this.mDrawableTintList = obtainStyledAttributes.getColorStateList(5);
            this.mHasDrawableTint = true;
            this.mDrawableBlendMode = android.graphics.BlendMode.SRC_ATOP;
            this.mHasDrawableBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(9)) {
            this.mDrawableBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(9, -1), this.mDrawableBlendMode);
            this.mHasDrawableBlendMode = true;
        }
        applyImageTint();
        int i4 = obtainStyledAttributes.getInt(10, 255);
        if (i4 != 255) {
            setImageAlpha(i4);
        }
        this.mCropToPadding = obtainStyledAttributes.getBoolean(7, false);
        obtainStyledAttributes.recycle();
    }

    private void initImageView() {
        this.mMatrix = new android.graphics.Matrix();
        this.mScaleType = android.widget.ImageView.ScaleType.FIT_CENTER;
        if (!sCompatDone) {
            int i = this.mContext.getApplicationInfo().targetSdkVersion;
            sCompatAdjustViewBounds = i <= 17;
            sCompatUseCorrectStreamDensity = i > 23;
            sCompatDrawableVisibilityDispatch = i < 24;
            sCompatDone = true;
        }
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(2);
        }
        if (getImportantForContentCapture() == 0) {
            setImportantForContentCapture(1);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return this.mDrawable == drawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mDrawable != null) {
            this.mDrawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable == this.mDrawable) {
            if (drawable != null) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicWidth != this.mDrawableWidth || intrinsicHeight != this.mDrawableHeight) {
                    this.mDrawableWidth = intrinsicWidth;
                    this.mDrawableHeight = intrinsicHeight;
                    configureBounds();
                }
            }
            invalidate();
            return;
        }
        super.invalidateDrawable(drawable);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return (getBackground() == null || getBackground().getCurrent() == null) ? false : true;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        java.lang.CharSequence contentDescription = getContentDescription();
        if (!android.text.TextUtils.isEmpty(contentDescription)) {
            accessibilityEvent.getText().add(contentDescription);
        }
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    @android.view.RemotableViewMethod
    public void setAdjustViewBounds(boolean z) {
        this.mAdjustViewBounds = z;
        if (z) {
            setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    @android.view.RemotableViewMethod
    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    @android.view.RemotableViewMethod
    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
    }

    public android.graphics.drawable.Drawable getDrawable() {
        if (this.mDrawable == this.mRecycleableBitmapDrawable) {
            this.mRecycleableBitmapDrawable = null;
        }
        return this.mDrawable;
    }

    private class ImageDrawableCallback implements java.lang.Runnable {
        private final android.graphics.drawable.Drawable drawable;
        private final int resource;
        private final android.net.Uri uri;

        ImageDrawableCallback(android.graphics.drawable.Drawable drawable, android.net.Uri uri, int i) {
            this.drawable = drawable;
            this.uri = uri;
            this.resource = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.widget.ImageView.this.setImageDrawable(this.drawable);
            android.widget.ImageView.this.mUri = this.uri;
            android.widget.ImageView.this.mResource = this.resource;
        }
    }

    @android.view.RemotableViewMethod(asyncImpl = "setImageResourceAsync")
    public void setImageResource(int i) {
        int i2 = this.mDrawableWidth;
        int i3 = this.mDrawableHeight;
        updateDrawable(null);
        this.mResource = i;
        this.mUri = null;
        resolveUri();
        if (i2 != this.mDrawableWidth || i3 != this.mDrawableHeight) {
            requestLayout();
        }
        invalidate();
    }

    public java.lang.Runnable setImageResourceAsync(int i) {
        android.graphics.drawable.Drawable drawable;
        if (i == 0) {
            drawable = null;
        } else {
            try {
                drawable = getContext().getDrawable(i);
            } catch (java.lang.Exception e) {
                android.util.Log.w(LOG_TAG, "Unable to find resource: " + i, e);
                i = 0;
                drawable = null;
            }
        }
        return new android.widget.ImageView.ImageDrawableCallback(drawable, null, i);
    }

    @android.view.RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(android.net.Uri uri) {
        if (this.mResource == 0) {
            if (this.mUri == uri) {
                return;
            }
            if (uri != null && this.mUri != null && uri.equals(this.mUri)) {
                return;
            }
        }
        updateDrawable(null);
        this.mResource = 0;
        this.mUri = uri;
        int i = this.mDrawableWidth;
        int i2 = this.mDrawableHeight;
        resolveUri();
        if (i != this.mDrawableWidth || i2 != this.mDrawableHeight) {
            requestLayout();
        }
        invalidate();
    }

    public java.lang.Runnable setImageURIAsync(android.net.Uri uri) {
        if (this.mResource == 0 && (this.mUri == uri || (uri != null && this.mUri != null && uri.equals(this.mUri)))) {
            return null;
        }
        android.graphics.drawable.Drawable drawableFromUri = uri == null ? null : getDrawableFromUri(uri);
        if (drawableFromUri == null) {
            uri = null;
        }
        return new android.widget.ImageView.ImageDrawableCallback(drawableFromUri, uri, 0);
    }

    public void setImageDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mDrawable != drawable) {
            this.mResource = 0;
            this.mUri = null;
            int i = this.mDrawableWidth;
            int i2 = this.mDrawableHeight;
            updateDrawable(drawable);
            if (i != this.mDrawableWidth || i2 != this.mDrawableHeight) {
                requestLayout();
            }
            invalidate();
        }
    }

    @android.view.RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(android.graphics.drawable.Icon icon) {
        setImageDrawable(icon == null ? null : icon.loadDrawable(this.mContext));
    }

    public java.lang.Runnable setImageIconAsync(android.graphics.drawable.Icon icon) {
        return new android.widget.ImageView.ImageDrawableCallback(icon == null ? null : icon.loadDrawable(this.mContext), null, 0);
    }

    @android.view.RemotableViewMethod
    public void setImageTintList(android.content.res.ColorStateList colorStateList) {
        this.mDrawableTintList = colorStateList;
        this.mHasDrawableTint = true;
        applyImageTint();
    }

    public android.content.res.ColorStateList getImageTintList() {
        return this.mDrawableTintList;
    }

    public void setImageTintMode(android.graphics.PorterDuff.Mode mode) {
        setImageTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setImageTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mDrawableBlendMode = blendMode;
        this.mHasDrawableBlendMode = true;
        applyImageTint();
    }

    public android.graphics.PorterDuff.Mode getImageTintMode() {
        if (this.mDrawableBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mDrawableBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getImageTintBlendMode() {
        return this.mDrawableBlendMode;
    }

    private void applyImageTint() {
        if (this.mDrawable != null) {
            if (this.mHasDrawableTint || this.mHasDrawableBlendMode) {
                this.mDrawable = this.mDrawable.mutate();
                if (this.mHasDrawableTint) {
                    this.mDrawable.setTintList(this.mDrawableTintList);
                }
                if (this.mHasDrawableBlendMode) {
                    this.mDrawable.setTintBlendMode(this.mDrawableBlendMode);
                }
                if (this.mDrawable.isStateful()) {
                    this.mDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @android.view.RemotableViewMethod
    public void setImageBitmap(android.graphics.Bitmap bitmap) {
        this.mDrawable = null;
        if (this.mRecycleableBitmapDrawable == null) {
            this.mRecycleableBitmapDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), bitmap);
        } else {
            this.mRecycleableBitmapDrawable.setBitmap(bitmap);
        }
        setImageDrawable(this.mRecycleableBitmapDrawable);
    }

    public void setImageState(int[] iArr, boolean z) {
        this.mState = iArr;
        this.mMergeState = z;
        if (this.mDrawable != null) {
            refreshDrawableState();
            resizeFromDrawable();
        }
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        resizeFromDrawable();
    }

    @android.view.RemotableViewMethod
    public void setImageLevel(int i) {
        this.mLevel = i;
        this.mHasLevelSet = true;
        if (this.mDrawable != null) {
            this.mDrawable.setLevel(i);
            resizeFromDrawable();
        }
    }

    public enum ScaleType {
        MATRIX(0),
        FIT_XY(1),
        FIT_START(2),
        FIT_CENTER(3),
        FIT_END(4),
        CENTER(5),
        CENTER_CROP(6),
        CENTER_INSIDE(7);

        final int nativeInt;

        ScaleType(int i) {
            this.nativeInt = i;
        }
    }

    public void setScaleType(android.widget.ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            throw new java.lang.NullPointerException();
        }
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            requestLayout();
            invalidate();
        }
    }

    public android.widget.ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public android.graphics.Matrix getImageMatrix() {
        if (this.mDrawMatrix == null) {
            return new android.graphics.Matrix(android.graphics.Matrix.IDENTITY_MATRIX);
        }
        return this.mDrawMatrix;
    }

    public void setImageMatrix(android.graphics.Matrix matrix) {
        if (matrix != null && matrix.isIdentity()) {
            matrix = null;
        }
        if ((matrix == null && !this.mMatrix.isIdentity()) || (matrix != null && !this.mMatrix.equals(matrix))) {
            this.mMatrix.set(matrix);
            configureBounds();
            invalidate();
        }
    }

    public boolean getCropToPadding() {
        return this.mCropToPadding;
    }

    public void setCropToPadding(boolean z) {
        if (this.mCropToPadding != z) {
            this.mCropToPadding = z;
            requestLayout();
            invalidate();
        }
    }

    private void resolveUri() {
        if (this.mDrawable != null || getResources() == null) {
            return;
        }
        android.graphics.drawable.Drawable drawable = null;
        if (this.mResource != 0) {
            try {
                drawable = this.mContext.getDrawable(this.mResource);
            } catch (java.lang.Exception e) {
                android.util.Log.w(LOG_TAG, "Unable to find resource: " + this.mResource, e);
                this.mResource = 0;
            }
        } else if (this.mUri != null) {
            android.graphics.drawable.Drawable drawableFromUri = getDrawableFromUri(this.mUri);
            if (drawableFromUri == null) {
                android.util.Log.w(LOG_TAG, "resolveUri failed on bad bitmap uri: " + this.mUri);
                this.mUri = null;
            }
            drawable = drawableFromUri;
        } else {
            return;
        }
        updateDrawable(drawable);
    }

    private android.graphics.drawable.Drawable getDrawableFromUri(android.net.Uri uri) {
        java.lang.String scheme = uri.getScheme();
        if (android.content.ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            try {
                android.content.ContentResolver.OpenResourceIdResult resourceId = this.mContext.getContentResolver().getResourceId(uri);
                return resourceId.r.getDrawable(resourceId.id, this.mContext.getTheme());
            } catch (java.lang.Exception e) {
                android.util.Log.w(LOG_TAG, "Unable to open content: " + uri, e);
            }
        } else if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return android.graphics.ImageDecoder.decodeDrawable(android.graphics.ImageDecoder.createSource(this.mContext.getContentResolver(), uri, sCompatUseCorrectStreamDensity ? getResources() : null), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.widget.ImageView$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                        imageDecoder.setAllocator(1);
                    }
                });
            } catch (java.io.IOException e2) {
                android.util.Log.w(LOG_TAG, "Unable to open content: " + uri, e2);
            }
        } else {
            return android.graphics.drawable.Drawable.createFromPath(uri.toString());
        }
        return null;
    }

    @Override // android.view.View
    public int[] onCreateDrawableState(int i) {
        if (this.mState == null) {
            return super.onCreateDrawableState(i);
        }
        if (!this.mMergeState) {
            return this.mState;
        }
        return mergeDrawableStates(super.onCreateDrawableState(i + this.mState.length), this.mState);
    }

    private void updateDrawable(android.graphics.drawable.Drawable drawable) {
        boolean z;
        if (drawable != this.mRecycleableBitmapDrawable && this.mRecycleableBitmapDrawable != null) {
            this.mRecycleableBitmapDrawable.setBitmap(null);
        }
        boolean z2 = false;
        if (this.mDrawable == null) {
            z = false;
        } else {
            z = this.mDrawable == drawable;
            this.mDrawable.setCallback(null);
            unscheduleDrawable(this.mDrawable);
            if (!sCompatDrawableVisibilityDispatch && !z && isAttachedToWindow()) {
                this.mDrawable.setVisible(false, false);
            }
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (!z || sCompatDrawableVisibilityDispatch) {
                if (sCompatDrawableVisibilityDispatch) {
                    if (getVisibility() == 0) {
                        z2 = true;
                    }
                } else if (isAttachedToWindow() && getWindowVisibility() == 0 && isShown()) {
                    z2 = true;
                }
                drawable.setVisible(z2, true);
            }
            if (this.mHasLevelSet) {
                drawable.setLevel(this.mLevel);
            }
            this.mDrawableWidth = drawable.getIntrinsicWidth();
            this.mDrawableHeight = drawable.getIntrinsicHeight();
            applyImageTint();
            applyColorFilter();
            applyAlpha();
            applyXfermode();
            configureBounds();
            return;
        }
        this.mDrawableHeight = -1;
        this.mDrawableWidth = -1;
    }

    private void resizeFromDrawable() {
        android.graphics.drawable.Drawable drawable = this.mDrawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (intrinsicWidth < 0) {
                intrinsicWidth = this.mDrawableWidth;
            }
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicHeight < 0) {
                intrinsicHeight = this.mDrawableHeight;
            }
            if (intrinsicWidth != this.mDrawableWidth || intrinsicHeight != this.mDrawableHeight) {
                this.mDrawableWidth = intrinsicWidth;
                this.mDrawableHeight = intrinsicHeight;
                requestLayout();
            }
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.mDrawable != null) {
            this.mDrawable.setLayoutDirection(i);
        }
    }

    private static android.graphics.Matrix.ScaleToFit scaleTypeToScaleToFit(android.widget.ImageView.ScaleType scaleType) {
        return sS2FArray[scaleType.nativeInt - 1];
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        float f;
        boolean z;
        boolean z2;
        int i5;
        int i6;
        boolean z3;
        resolveUri();
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        if (this.mDrawable == null) {
            this.mDrawableWidth = -1;
            this.mDrawableHeight = -1;
            f = 0.0f;
            z = false;
            z2 = false;
            i3 = 0;
            i4 = 0;
        } else {
            i3 = this.mDrawableWidth;
            i4 = this.mDrawableHeight;
            if (i3 <= 0) {
                i3 = 1;
            }
            if (i4 <= 0) {
                i4 = 1;
            }
            if (!this.mAdjustViewBounds) {
                f = 0.0f;
                z = false;
                z2 = false;
            } else {
                z = mode != 1073741824;
                z2 = mode2 != 1073741824;
                f = i3 / i4;
            }
        }
        int i7 = this.mPaddingLeft;
        int i8 = this.mPaddingRight;
        int i9 = this.mPaddingTop;
        int i10 = this.mPaddingBottom;
        if (z || z2) {
            int resolveAdjustedSize = resolveAdjustedSize(i3 + i7 + i8, this.mMaxWidth, i);
            int resolveAdjustedSize2 = resolveAdjustedSize(i4 + i9 + i10, this.mMaxHeight, i2);
            if (f != 0.0f) {
                float f2 = (resolveAdjustedSize2 - i9) - i10;
                if (java.lang.Math.abs((((resolveAdjustedSize - i7) - i8) / f2) - f) <= 1.0E-7d) {
                    i5 = resolveAdjustedSize;
                    i6 = resolveAdjustedSize2;
                } else {
                    if (!z) {
                        z3 = false;
                    } else {
                        int i11 = ((int) (f2 * f)) + i7 + i8;
                        if (!z2 && !sCompatAdjustViewBounds) {
                            resolveAdjustedSize = resolveAdjustedSize(i11, this.mMaxWidth, i);
                        }
                        if (i11 > resolveAdjustedSize) {
                            z3 = false;
                        } else {
                            resolveAdjustedSize = i11;
                            z3 = true;
                        }
                    }
                    if (!z3 && z2) {
                        int i12 = ((int) (((resolveAdjustedSize - i7) - i8) / f)) + i9 + i10;
                        if (!z && !sCompatAdjustViewBounds) {
                            resolveAdjustedSize2 = resolveAdjustedSize(i12, this.mMaxHeight, i2);
                        }
                        if (i12 > resolveAdjustedSize2) {
                            i5 = resolveAdjustedSize;
                            i6 = resolveAdjustedSize2;
                        } else {
                            i6 = i12;
                            i5 = resolveAdjustedSize;
                        }
                    } else {
                        i5 = resolveAdjustedSize;
                        i6 = resolveAdjustedSize2;
                    }
                }
            } else {
                i5 = resolveAdjustedSize;
                i6 = resolveAdjustedSize2;
            }
        } else {
            int max = java.lang.Math.max(i3 + i7 + i8, getSuggestedMinimumWidth());
            int max2 = java.lang.Math.max(i4 + i9 + i10, getSuggestedMinimumHeight());
            i5 = resolveSizeAndState(max, i, 0);
            i6 = resolveSizeAndState(max2, i2, 0);
        }
        setMeasuredDimension(i5, i6);
    }

    private int resolveAdjustedSize(int i, int i2, int i3) {
        int mode = android.view.View.MeasureSpec.getMode(i3);
        int size = android.view.View.MeasureSpec.getSize(i3);
        switch (mode) {
            case Integer.MIN_VALUE:
                return java.lang.Math.min(java.lang.Math.min(i, size), i2);
            case 0:
                return java.lang.Math.min(i, i2);
            case 1073741824:
                return size;
            default:
                return i;
        }
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        this.mHaveFrame = true;
        configureBounds();
        return frame;
    }

    private void configureBounds() {
        float min;
        float f;
        float f2;
        if (this.mDrawable == null || !this.mHaveFrame) {
            return;
        }
        int i = this.mDrawableWidth;
        int i2 = this.mDrawableHeight;
        int width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
        int height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
        boolean z = (i < 0 || width == i) && (i2 < 0 || height == i2);
        if (i <= 0 || i2 <= 0 || android.widget.ImageView.ScaleType.FIT_XY == this.mScaleType) {
            this.mDrawable.setBounds(0, 0, width, height);
            this.mDrawMatrix = null;
            return;
        }
        this.mDrawable.setBounds(0, 0, i, i2);
        if (android.widget.ImageView.ScaleType.MATRIX == this.mScaleType) {
            if (this.mMatrix.isIdentity()) {
                this.mDrawMatrix = null;
                return;
            } else {
                this.mDrawMatrix = this.mMatrix;
                return;
            }
        }
        if (z) {
            this.mDrawMatrix = null;
            return;
        }
        if (android.widget.ImageView.ScaleType.CENTER == this.mScaleType) {
            this.mDrawMatrix = this.mMatrix;
            this.mDrawMatrix.setTranslate(java.lang.Math.round((width - i) * 0.5f), java.lang.Math.round((height - i2) * 0.5f));
            return;
        }
        float f3 = 0.0f;
        if (android.widget.ImageView.ScaleType.CENTER_CROP == this.mScaleType) {
            this.mDrawMatrix = this.mMatrix;
            if (i * height > width * i2) {
                f2 = height / i2;
                float f4 = (width - (i * f2)) * 0.5f;
                f = 0.0f;
                f3 = f4;
            } else {
                float f5 = width / i;
                f = (height - (i2 * f5)) * 0.5f;
                f2 = f5;
            }
            this.mDrawMatrix.setScale(f2, f2);
            this.mDrawMatrix.postTranslate(java.lang.Math.round(f3), java.lang.Math.round(f));
            return;
        }
        if (android.widget.ImageView.ScaleType.CENTER_INSIDE == this.mScaleType) {
            this.mDrawMatrix = this.mMatrix;
            if (i <= width && i2 <= height) {
                min = 1.0f;
            } else {
                min = java.lang.Math.min(width / i, height / i2);
            }
            float round = java.lang.Math.round((width - (i * min)) * 0.5f);
            float round2 = java.lang.Math.round((height - (i2 * min)) * 0.5f);
            this.mDrawMatrix.setScale(min, min);
            this.mDrawMatrix.postTranslate(round, round2);
            return;
        }
        this.mTempSrc.set(0.0f, 0.0f, i, i2);
        this.mTempDst.set(0.0f, 0.0f, width, height);
        this.mDrawMatrix = this.mMatrix;
        this.mDrawMatrix.setRectToRect(this.mTempSrc, this.mTempDst, scaleTypeToScaleToFit(this.mScaleType));
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable drawable = this.mDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mDrawable != null) {
            this.mDrawable.setHotspot(f, f2);
        }
    }

    public void animateTransform(android.graphics.Matrix matrix) {
        if (this.mDrawable == null) {
            return;
        }
        if (matrix != null) {
            this.mDrawable.setBounds(0, 0, this.mDrawableWidth, this.mDrawableHeight);
            if (this.mDrawMatrix == null) {
                this.mDrawMatrix = new android.graphics.Matrix();
            }
            this.mDrawMatrix.set(matrix);
        } else {
            this.mDrawable.setBounds(0, 0, (getWidth() - this.mPaddingLeft) - this.mPaddingRight, (getHeight() - this.mPaddingTop) - this.mPaddingBottom);
            this.mDrawMatrix = null;
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawable == null || this.mDrawableWidth == 0 || this.mDrawableHeight == 0) {
            return;
        }
        if (this.mDrawMatrix == null && this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
            this.mDrawable.draw(canvas);
            return;
        }
        int saveCount = canvas.getSaveCount();
        canvas.save();
        if (this.mCropToPadding) {
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i, this.mPaddingTop + i2, ((i + this.mRight) - this.mLeft) - this.mPaddingRight, ((i2 + this.mBottom) - this.mTop) - this.mPaddingBottom);
        }
        canvas.translate(this.mPaddingLeft, this.mPaddingTop);
        if (this.mDrawMatrix != null) {
            canvas.concat(this.mDrawMatrix);
        }
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override // android.view.View
    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    public int getBaseline() {
        if (this.mBaselineAlignBottom) {
            return getMeasuredHeight();
        }
        return this.mBaseline;
    }

    public void setBaseline(int i) {
        if (this.mBaseline != i) {
            this.mBaseline = i;
            requestLayout();
        }
    }

    public void setBaselineAlignBottom(boolean z) {
        if (this.mBaselineAlignBottom != z) {
            this.mBaselineAlignBottom = z;
            requestLayout();
        }
    }

    public boolean getBaselineAlignBottom() {
        return this.mBaselineAlignBottom;
    }

    public final void setColorFilter(int i, android.graphics.PorterDuff.Mode mode) {
        setColorFilter(new android.graphics.PorterDuffColorFilter(i, mode));
    }

    @android.view.RemotableViewMethod
    public final void setColorFilter(int i) {
        setColorFilter(i, android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    public final void clearColorFilter() {
        setColorFilter((android.graphics.ColorFilter) null);
    }

    public final void setXfermode(android.graphics.Xfermode xfermode) {
        if (this.mXfermode != xfermode) {
            this.mXfermode = xfermode;
            this.mHasXfermode = true;
            applyXfermode();
            invalidate();
        }
    }

    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            this.mHasColorFilter = true;
            applyColorFilter();
            invalidate();
        }
    }

    public int getImageAlpha() {
        return this.mAlpha;
    }

    @android.view.RemotableViewMethod
    public void setImageAlpha(int i) {
        setAlpha(i);
    }

    @android.view.RemotableViewMethod
    @java.lang.Deprecated
    public void setAlpha(int i) {
        int i2 = i & 255;
        if (this.mAlpha != i2) {
            this.mAlpha = i2;
            this.mHasAlpha = true;
            applyAlpha();
            invalidate();
        }
    }

    private void applyXfermode() {
        if (this.mDrawable != null && this.mHasXfermode) {
            this.mDrawable = this.mDrawable.mutate();
            this.mDrawable.setXfermode(this.mXfermode);
        }
    }

    private void applyColorFilter() {
        if (this.mDrawable != null && this.mHasColorFilter) {
            this.mDrawable = this.mDrawable.mutate();
            this.mDrawable.setColorFilter(this.mColorFilter);
        }
    }

    private void applyAlpha() {
        if (this.mDrawable != null && this.mHasAlpha) {
            this.mDrawable = this.mDrawable.mutate();
            this.mDrawable.setAlpha((this.mAlpha * 256) >> 8);
        }
    }

    @Override // android.view.View
    public boolean isOpaque() {
        return super.isOpaque() || (this.mDrawable != null && this.mXfermode == null && this.mDrawable.getOpacity() == -1 && ((this.mAlpha * 256) >> 8) == 255 && isFilledByImage());
    }

    private boolean isFilledByImage() {
        if (this.mDrawable == null) {
            return false;
        }
        android.graphics.Rect bounds = this.mDrawable.getBounds();
        android.graphics.Matrix matrix = this.mDrawMatrix;
        if (matrix == null) {
            return bounds.left <= 0 && bounds.top <= 0 && bounds.right >= getWidth() && bounds.bottom >= getHeight();
        }
        if (!matrix.rectStaysRect()) {
            return false;
        }
        android.graphics.RectF rectF = this.mTempSrc;
        android.graphics.RectF rectF2 = this.mTempDst;
        rectF.set(bounds);
        matrix.mapRect(rectF2, rectF);
        return rectF2.left <= 0.0f && rectF2.top <= 0.0f && rectF2.right >= ((float) getWidth()) && rectF2.bottom >= ((float) getHeight());
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (this.mDrawable != null && !sCompatDrawableVisibilityDispatch) {
            this.mDrawable.setVisible(z, false);
        }
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mDrawable != null && sCompatDrawableVisibilityDispatch) {
            this.mDrawable.setVisible(i == 0, false);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDrawable != null && sCompatDrawableVisibilityDispatch) {
            this.mDrawable.setVisible(getVisibility() == 0, false);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mDrawable != null && sCompatDrawableVisibilityDispatch) {
            this.mDrawable.setVisible(false, false);
        }
    }

    @Override // android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ImageView.class.getName();
    }

    @Override // android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baseline", getBaseline());
    }

    @Override // android.view.View
    public boolean isDefaultFocusHighlightNeeded(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
        return super.isDefaultFocusHighlightNeeded(drawable, drawable2) && (this.mDrawable == null || !this.mDrawable.isStateful() || !this.mDrawable.hasFocusStateSpecified());
    }
}
