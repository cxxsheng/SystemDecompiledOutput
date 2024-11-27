package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class ProgressBar extends android.view.View {
    private static final int MAX_LEVEL = 10000;
    private static final int PROGRESS_ANIM_DURATION = 80;
    private static final android.view.animation.DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new android.view.animation.DecelerateInterpolator();
    private final android.util.FloatProperty<android.widget.ProgressBar> VISUAL_PROGRESS;
    private boolean mAggregatedIsVisible;
    private android.view.animation.AlphaAnimation mAnimation;
    private boolean mAttached;
    private int mBehavior;
    private java.util.Locale mCachedLocale;
    private android.graphics.drawable.Drawable mCurrentDrawable;
    private int mDuration;
    private boolean mHasAnimation;
    private boolean mInDrawing;
    private boolean mIndeterminate;
    private android.graphics.drawable.Drawable mIndeterminateDrawable;
    private android.view.animation.Interpolator mInterpolator;
    private android.animation.ObjectAnimator mLastProgressAnimator;
    private int mMax;
    int mMaxHeight;
    private boolean mMaxInitialized;
    int mMaxWidth;
    private int mMin;
    int mMinHeight;
    private boolean mMinInitialized;
    int mMinWidth;
    boolean mMirrorForRtl;
    private boolean mNoInvalidate;
    private boolean mOnlyIndeterminate;
    private java.text.NumberFormat mPercentFormat;
    private int mProgress;
    private android.graphics.drawable.Drawable mProgressDrawable;
    private android.widget.ProgressBar.ProgressTintInfo mProgressTintInfo;
    private final java.util.ArrayList<android.widget.ProgressBar.RefreshData> mRefreshData;
    private boolean mRefreshIsPosted;
    private android.widget.ProgressBar.RefreshProgressRunnable mRefreshProgressRunnable;
    int mSampleWidth;
    private int mSecondaryProgress;
    private boolean mShouldStartAnimationDrawable;
    private android.view.animation.Transformation mTransformation;
    private long mUiThreadId;
    private float mVisualProgress;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.ProgressBar> {
        private int mIndeterminateDrawableId;
        private int mIndeterminateId;
        private int mIndeterminateTintBlendModeId;
        private int mIndeterminateTintId;
        private int mIndeterminateTintModeId;
        private int mInterpolatorId;
        private int mMaxId;
        private int mMinId;
        private int mMirrorForRtlId;
        private int mProgressBackgroundTintBlendModeId;
        private int mProgressBackgroundTintId;
        private int mProgressBackgroundTintModeId;
        private int mProgressDrawableId;
        private int mProgressId;
        private int mProgressTintBlendModeId;
        private int mProgressTintId;
        private int mProgressTintModeId;
        private boolean mPropertiesMapped = false;
        private int mSecondaryProgressId;
        private int mSecondaryProgressTintBlendModeId;
        private int mSecondaryProgressTintId;
        private int mSecondaryProgressTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mIndeterminateId = propertyMapper.mapBoolean("indeterminate", 16843065);
            this.mIndeterminateDrawableId = propertyMapper.mapObject("indeterminateDrawable", 16843067);
            this.mIndeterminateTintId = propertyMapper.mapObject("indeterminateTint", 16843881);
            this.mIndeterminateTintBlendModeId = propertyMapper.mapObject("indeterminateTintBlendMode", 23);
            this.mIndeterminateTintModeId = propertyMapper.mapObject("indeterminateTintMode", 16843882);
            this.mInterpolatorId = propertyMapper.mapObject("interpolator", 16843073);
            this.mMaxId = propertyMapper.mapInt("max", 16843062);
            this.mMinId = propertyMapper.mapInt("min", 16844089);
            this.mMirrorForRtlId = propertyMapper.mapBoolean("mirrorForRtl", 16843726);
            this.mProgressId = propertyMapper.mapInt(android.app.Notification.CATEGORY_PROGRESS, 16843063);
            this.mProgressBackgroundTintId = propertyMapper.mapObject("progressBackgroundTint", 16843877);
            this.mProgressBackgroundTintBlendModeId = propertyMapper.mapObject("progressBackgroundTintBlendMode", 19);
            this.mProgressBackgroundTintModeId = propertyMapper.mapObject("progressBackgroundTintMode", 16843878);
            this.mProgressDrawableId = propertyMapper.mapObject("progressDrawable", 16843068);
            this.mProgressTintId = propertyMapper.mapObject("progressTint", 16843875);
            this.mProgressTintBlendModeId = propertyMapper.mapObject("progressTintBlendMode", 17);
            this.mProgressTintModeId = propertyMapper.mapObject("progressTintMode", 16843876);
            this.mSecondaryProgressId = propertyMapper.mapInt("secondaryProgress", 16843064);
            this.mSecondaryProgressTintId = propertyMapper.mapObject("secondaryProgressTint", 16843879);
            this.mSecondaryProgressTintBlendModeId = propertyMapper.mapObject("secondaryProgressTintBlendMode", 21);
            this.mSecondaryProgressTintModeId = propertyMapper.mapObject("secondaryProgressTintMode", 16843880);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.ProgressBar progressBar, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIndeterminateId, progressBar.isIndeterminate());
            propertyReader.readObject(this.mIndeterminateDrawableId, progressBar.getIndeterminateDrawable());
            propertyReader.readObject(this.mIndeterminateTintId, progressBar.getIndeterminateTintList());
            propertyReader.readObject(this.mIndeterminateTintBlendModeId, progressBar.getIndeterminateTintBlendMode());
            propertyReader.readObject(this.mIndeterminateTintModeId, progressBar.getIndeterminateTintMode());
            propertyReader.readObject(this.mInterpolatorId, progressBar.getInterpolator());
            propertyReader.readInt(this.mMaxId, progressBar.getMax());
            propertyReader.readInt(this.mMinId, progressBar.getMin());
            propertyReader.readBoolean(this.mMirrorForRtlId, progressBar.getMirrorForRtl());
            propertyReader.readInt(this.mProgressId, progressBar.getProgress());
            propertyReader.readObject(this.mProgressBackgroundTintId, progressBar.getProgressBackgroundTintList());
            propertyReader.readObject(this.mProgressBackgroundTintBlendModeId, progressBar.getProgressBackgroundTintBlendMode());
            propertyReader.readObject(this.mProgressBackgroundTintModeId, progressBar.getProgressBackgroundTintMode());
            propertyReader.readObject(this.mProgressDrawableId, progressBar.getProgressDrawable());
            propertyReader.readObject(this.mProgressTintId, progressBar.getProgressTintList());
            propertyReader.readObject(this.mProgressTintBlendModeId, progressBar.getProgressTintBlendMode());
            propertyReader.readObject(this.mProgressTintModeId, progressBar.getProgressTintMode());
            propertyReader.readInt(this.mSecondaryProgressId, progressBar.getSecondaryProgress());
            propertyReader.readObject(this.mSecondaryProgressTintId, progressBar.getSecondaryProgressTintList());
            propertyReader.readObject(this.mSecondaryProgressTintBlendModeId, progressBar.getSecondaryProgressTintBlendMode());
            propertyReader.readObject(this.mSecondaryProgressTintModeId, progressBar.getSecondaryProgressTintMode());
        }
    }

    public ProgressBar(android.content.Context context) {
        this(context, null);
    }

    public ProgressBar(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842871);
    }

    public ProgressBar(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ProgressBar(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSampleWidth = 0;
        this.mMirrorForRtl = false;
        this.mRefreshData = new java.util.ArrayList<>();
        this.VISUAL_PROGRESS = new android.util.FloatProperty<android.widget.ProgressBar>("visual_progress") { // from class: android.widget.ProgressBar.2
            @Override // android.util.FloatProperty
            public void setValue(android.widget.ProgressBar progressBar, float f) {
                progressBar.setVisualProgress(16908301, f);
                progressBar.mVisualProgress = f;
            }

            @Override // android.util.Property
            public java.lang.Float get(android.widget.ProgressBar progressBar) {
                return java.lang.Float.valueOf(progressBar.mVisualProgress);
            }
        };
        this.mUiThreadId = java.lang.Thread.currentThread().getId();
        initProgressBar();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ProgressBar, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ProgressBar, attributeSet, obtainStyledAttributes, i, i2);
        this.mNoInvalidate = true;
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(8);
        if (drawable != null) {
            if (needsTileify(drawable)) {
                setProgressDrawableTiled(drawable);
            } else {
                setProgressDrawable(drawable);
            }
        }
        this.mDuration = obtainStyledAttributes.getInt(9, this.mDuration);
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(11, this.mMinWidth);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(0, this.mMaxWidth);
        this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(12, this.mMinHeight);
        this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(1, this.mMaxHeight);
        this.mBehavior = obtainStyledAttributes.getInt(10, this.mBehavior);
        int resourceId = obtainStyledAttributes.getResourceId(13, 17432587);
        if (resourceId > 0) {
            setInterpolator(context, resourceId);
        }
        setMin(obtainStyledAttributes.getInt(26, this.mMin));
        setMax(obtainStyledAttributes.getInt(2, this.mMax));
        setProgress(obtainStyledAttributes.getInt(3, this.mProgress));
        setSecondaryProgress(obtainStyledAttributes.getInt(4, this.mSecondaryProgress));
        android.graphics.drawable.Drawable drawable2 = obtainStyledAttributes.getDrawable(7);
        if (drawable2 != null) {
            if (needsTileify(drawable2)) {
                setIndeterminateDrawableTiled(drawable2);
            } else {
                setIndeterminateDrawable(drawable2);
            }
        }
        this.mOnlyIndeterminate = obtainStyledAttributes.getBoolean(6, this.mOnlyIndeterminate);
        this.mNoInvalidate = false;
        setIndeterminate(this.mOnlyIndeterminate || obtainStyledAttributes.getBoolean(5, this.mIndeterminate));
        this.mMirrorForRtl = obtainStyledAttributes.getBoolean(15, this.mMirrorForRtl);
        if (obtainStyledAttributes.hasValue(17)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(17, -1), null);
            this.mProgressTintInfo.mHasProgressTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(16)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressTintList = obtainStyledAttributes.getColorStateList(16);
            this.mProgressTintInfo.mHasProgressTint = true;
        }
        if (obtainStyledAttributes.hasValue(19)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(19, -1), null);
            this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(18)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundTintList = obtainStyledAttributes.getColorStateList(18);
            this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        }
        if (obtainStyledAttributes.hasValue(21)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(21, -1), null);
            this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(20)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressTintList = obtainStyledAttributes.getColorStateList(20);
            this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        }
        if (obtainStyledAttributes.hasValue(23)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(23, -1), null);
            this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(22)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateTintList = obtainStyledAttributes.getColorStateList(22);
            this.mProgressTintInfo.mHasIndeterminateTint = true;
        }
        obtainStyledAttributes.recycle();
        applyProgressTints();
        applyIndeterminateTint();
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public void setMinWidth(int i) {
        this.mMinWidth = i;
        requestLayout();
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public void setMinHeight(int i) {
        this.mMinHeight = i;
        requestLayout();
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
        requestLayout();
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private static boolean needsTileify(android.graphics.drawable.Drawable drawable) {
        if (drawable instanceof android.graphics.drawable.LayerDrawable) {
            android.graphics.drawable.LayerDrawable layerDrawable = (android.graphics.drawable.LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                if (needsTileify(layerDrawable.getDrawable(i))) {
                    return true;
                }
            }
            return false;
        }
        if (!(drawable instanceof android.graphics.drawable.StateListDrawable)) {
            return drawable instanceof android.graphics.drawable.BitmapDrawable;
        }
        android.graphics.drawable.StateListDrawable stateListDrawable = (android.graphics.drawable.StateListDrawable) drawable;
        int stateCount = stateListDrawable.getStateCount();
        for (int i2 = 0; i2 < stateCount; i2++) {
            if (needsTileify(stateListDrawable.getStateDrawable(i2))) {
                return true;
            }
        }
        return false;
    }

    private android.graphics.drawable.Drawable tileify(android.graphics.drawable.Drawable drawable, boolean z) {
        int i = 0;
        if (drawable instanceof android.graphics.drawable.LayerDrawable) {
            android.graphics.drawable.LayerDrawable layerDrawable = (android.graphics.drawable.LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            android.graphics.drawable.Drawable[] drawableArr = new android.graphics.drawable.Drawable[numberOfLayers];
            for (int i2 = 0; i2 < numberOfLayers; i2++) {
                int id = layerDrawable.getId(i2);
                drawableArr[i2] = tileify(layerDrawable.getDrawable(i2), id == 16908301 || id == 16908303);
            }
            android.graphics.drawable.LayerDrawable layerDrawable2 = new android.graphics.drawable.LayerDrawable(drawableArr);
            while (i < numberOfLayers) {
                layerDrawable2.setId(i, layerDrawable.getId(i));
                layerDrawable2.setLayerGravity(i, layerDrawable.getLayerGravity(i));
                layerDrawable2.setLayerWidth(i, layerDrawable.getLayerWidth(i));
                layerDrawable2.setLayerHeight(i, layerDrawable.getLayerHeight(i));
                layerDrawable2.setLayerInsetLeft(i, layerDrawable.getLayerInsetLeft(i));
                layerDrawable2.setLayerInsetRight(i, layerDrawable.getLayerInsetRight(i));
                layerDrawable2.setLayerInsetTop(i, layerDrawable.getLayerInsetTop(i));
                layerDrawable2.setLayerInsetBottom(i, layerDrawable.getLayerInsetBottom(i));
                layerDrawable2.setLayerInsetStart(i, layerDrawable.getLayerInsetStart(i));
                layerDrawable2.setLayerInsetEnd(i, layerDrawable.getLayerInsetEnd(i));
                i++;
            }
            return layerDrawable2;
        }
        if (drawable instanceof android.graphics.drawable.StateListDrawable) {
            android.graphics.drawable.StateListDrawable stateListDrawable = (android.graphics.drawable.StateListDrawable) drawable;
            android.graphics.drawable.StateListDrawable stateListDrawable2 = new android.graphics.drawable.StateListDrawable();
            int stateCount = stateListDrawable.getStateCount();
            while (i < stateCount) {
                stateListDrawable2.addState(stateListDrawable.getStateSet(i), tileify(stateListDrawable.getStateDrawable(i), z));
                i++;
            }
            return stateListDrawable2;
        }
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            android.graphics.drawable.BitmapDrawable bitmapDrawable = (android.graphics.drawable.BitmapDrawable) drawable.getConstantState().newDrawable(getResources());
            bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.CLAMP);
            if (this.mSampleWidth <= 0) {
                this.mSampleWidth = bitmapDrawable.getIntrinsicWidth();
            }
            if (z) {
                return new android.graphics.drawable.ClipDrawable(bitmapDrawable, 3, 1);
            }
            return bitmapDrawable;
        }
        return drawable;
    }

    android.graphics.drawable.shapes.Shape getDrawableShape() {
        return new android.graphics.drawable.shapes.RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    private android.graphics.drawable.Drawable tileifyIndeterminate(android.graphics.drawable.Drawable drawable) {
        if (drawable instanceof android.graphics.drawable.AnimationDrawable) {
            android.graphics.drawable.AnimationDrawable animationDrawable = (android.graphics.drawable.AnimationDrawable) drawable;
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            android.graphics.drawable.AnimationDrawable animationDrawable2 = new android.graphics.drawable.AnimationDrawable();
            animationDrawable2.setOneShot(animationDrawable.isOneShot());
            for (int i = 0; i < numberOfFrames; i++) {
                android.graphics.drawable.Drawable tileify = tileify(animationDrawable.getFrame(i), true);
                tileify.setLevel(10000);
                animationDrawable2.addFrame(tileify, animationDrawable.getDuration(i));
            }
            animationDrawable2.setLevel(10000);
            return animationDrawable2;
        }
        return drawable;
    }

    private void initProgressBar() {
        this.mMin = 0;
        this.mMax = 100;
        this.mProgress = 0;
        this.mSecondaryProgress = 0;
        this.mIndeterminate = false;
        this.mOnlyIndeterminate = false;
        this.mDuration = 4000;
        this.mBehavior = 1;
        this.mMinWidth = 24;
        this.mMaxWidth = 48;
        this.mMinHeight = 24;
        this.mMaxHeight = 48;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.app.Notification.CATEGORY_PROGRESS)
    public synchronized boolean isIndeterminate() {
        return this.mIndeterminate;
    }

    @android.view.RemotableViewMethod
    public synchronized void setIndeterminate(boolean z) {
        if ((!this.mOnlyIndeterminate || !this.mIndeterminate) && z != this.mIndeterminate) {
            this.mIndeterminate = z;
            if (z) {
                swapCurrentDrawable(this.mIndeterminateDrawable);
                startAnimation();
            } else {
                swapCurrentDrawable(this.mProgressDrawable);
                stopAnimation();
            }
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    private void swapCurrentDrawable(android.graphics.drawable.Drawable drawable) {
        android.graphics.drawable.Drawable drawable2 = this.mCurrentDrawable;
        this.mCurrentDrawable = drawable;
        if (drawable2 != this.mCurrentDrawable) {
            if (drawable2 != null) {
                drawable2.setVisible(false, false);
            }
            if (this.mCurrentDrawable != null) {
                this.mCurrentDrawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
        }
    }

    public android.graphics.drawable.Drawable getIndeterminateDrawable() {
        return this.mIndeterminateDrawable;
    }

    public void setIndeterminateDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mIndeterminateDrawable != drawable) {
            if (this.mIndeterminateDrawable != null) {
                this.mIndeterminateDrawable.setCallback(null);
                unscheduleDrawable(this.mIndeterminateDrawable);
            }
            this.mIndeterminateDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                applyIndeterminateTint();
            }
            if (this.mIndeterminate) {
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
        }
    }

    @android.view.RemotableViewMethod
    public void setIndeterminateTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mIndeterminateTintList = colorStateList;
        this.mProgressTintInfo.mHasIndeterminateTint = true;
        applyIndeterminateTint();
    }

    public android.content.res.ColorStateList getIndeterminateTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mIndeterminateTintList;
        }
        return null;
    }

    public void setIndeterminateTintMode(android.graphics.PorterDuff.Mode mode) {
        setIndeterminateTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setIndeterminateTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mIndeterminateBlendMode = blendMode;
        this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        applyIndeterminateTint();
    }

    public android.graphics.PorterDuff.Mode getIndeterminateTintMode() {
        android.graphics.BlendMode indeterminateTintBlendMode = getIndeterminateTintBlendMode();
        if (indeterminateTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(indeterminateTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getIndeterminateTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mIndeterminateBlendMode;
        }
        return null;
    }

    private void applyIndeterminateTint() {
        if (this.mIndeterminateDrawable != null && this.mProgressTintInfo != null) {
            android.widget.ProgressBar.ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
            if (progressTintInfo.mHasIndeterminateTint || progressTintInfo.mHasIndeterminateTintMode) {
                this.mIndeterminateDrawable = this.mIndeterminateDrawable.mutate();
                if (progressTintInfo.mHasIndeterminateTint) {
                    this.mIndeterminateDrawable.setTintList(progressTintInfo.mIndeterminateTintList);
                }
                if (progressTintInfo.mHasIndeterminateTintMode) {
                    this.mIndeterminateDrawable.setTintBlendMode(progressTintInfo.mIndeterminateBlendMode);
                }
                if (this.mIndeterminateDrawable.isStateful()) {
                    this.mIndeterminateDrawable.setState(getDrawableState());
                }
            }
        }
    }

    public void setIndeterminateDrawableTiled(android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            drawable = tileifyIndeterminate(drawable);
        }
        setIndeterminateDrawable(drawable);
    }

    public android.graphics.drawable.Drawable getProgressDrawable() {
        return this.mProgressDrawable;
    }

    public void setProgressDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mProgressDrawable != drawable) {
            if (this.mProgressDrawable != null) {
                this.mProgressDrawable.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                int minimumHeight = drawable.getMinimumHeight();
                if (this.mMaxHeight < minimumHeight) {
                    this.mMaxHeight = minimumHeight;
                    requestLayout();
                }
                applyProgressTints();
            }
            if (!this.mIndeterminate) {
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
            updateDrawableBounds(getWidth(), getHeight());
            updateDrawableState();
            doRefreshProgress(16908301, this.mProgress, false, false, false);
            doRefreshProgress(16908303, this.mSecondaryProgress, false, false, false);
        }
    }

    public boolean getMirrorForRtl() {
        return this.mMirrorForRtl;
    }

    private void applyProgressTints() {
        if (this.mProgressDrawable != null && this.mProgressTintInfo != null) {
            applyPrimaryProgressTint();
            applyProgressBackgroundTint();
            applySecondaryProgressTint();
        }
    }

    private void applyPrimaryProgressTint() {
        android.graphics.drawable.Drawable tintTarget;
        if ((this.mProgressTintInfo.mHasProgressTint || this.mProgressTintInfo.mHasProgressTintMode) && (tintTarget = getTintTarget(16908301, true)) != null) {
            if (this.mProgressTintInfo.mHasProgressTint) {
                tintTarget.setTintList(this.mProgressTintInfo.mProgressTintList);
            }
            if (this.mProgressTintInfo.mHasProgressTintMode) {
                tintTarget.setTintBlendMode(this.mProgressTintInfo.mProgressBlendMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    private void applyProgressBackgroundTint() {
        android.graphics.drawable.Drawable tintTarget;
        if ((this.mProgressTintInfo.mHasProgressBackgroundTint || this.mProgressTintInfo.mHasProgressBackgroundTintMode) && (tintTarget = getTintTarget(16908288, false)) != null) {
            if (this.mProgressTintInfo.mHasProgressBackgroundTint) {
                tintTarget.setTintList(this.mProgressTintInfo.mProgressBackgroundTintList);
            }
            if (this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
                tintTarget.setTintBlendMode(this.mProgressTintInfo.mProgressBackgroundBlendMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    private void applySecondaryProgressTint() {
        android.graphics.drawable.Drawable tintTarget;
        if ((this.mProgressTintInfo.mHasSecondaryProgressTint || this.mProgressTintInfo.mHasSecondaryProgressTintMode) && (tintTarget = getTintTarget(16908303, false)) != null) {
            if (this.mProgressTintInfo.mHasSecondaryProgressTint) {
                tintTarget.setTintList(this.mProgressTintInfo.mSecondaryProgressTintList);
            }
            if (this.mProgressTintInfo.mHasSecondaryProgressTintMode) {
                tintTarget.setTintBlendMode(this.mProgressTintInfo.mSecondaryProgressBlendMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    @android.view.RemotableViewMethod
    public void setProgressTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressTintList = colorStateList;
        this.mProgressTintInfo.mHasProgressTint = true;
        if (this.mProgressDrawable != null) {
            applyPrimaryProgressTint();
        }
    }

    public android.content.res.ColorStateList getProgressTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressTintList;
        }
        return null;
    }

    public void setProgressTintMode(android.graphics.PorterDuff.Mode mode) {
        setProgressTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setProgressTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBlendMode = blendMode;
        this.mProgressTintInfo.mHasProgressTintMode = true;
        if (this.mProgressDrawable != null) {
            applyPrimaryProgressTint();
        }
    }

    public android.graphics.PorterDuff.Mode getProgressTintMode() {
        android.graphics.BlendMode progressTintBlendMode = getProgressTintBlendMode();
        if (progressTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(progressTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getProgressTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBlendMode;
        }
        return null;
    }

    @android.view.RemotableViewMethod
    public void setProgressBackgroundTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBackgroundTintList = colorStateList;
        this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        if (this.mProgressDrawable != null) {
            applyProgressBackgroundTint();
        }
    }

    public android.content.res.ColorStateList getProgressBackgroundTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBackgroundTintList;
        }
        return null;
    }

    public void setProgressBackgroundTintMode(android.graphics.PorterDuff.Mode mode) {
        setProgressBackgroundTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setProgressBackgroundTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBackgroundBlendMode = blendMode;
        this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        if (this.mProgressDrawable != null) {
            applyProgressBackgroundTint();
        }
    }

    public android.graphics.PorterDuff.Mode getProgressBackgroundTintMode() {
        android.graphics.BlendMode progressBackgroundTintBlendMode = getProgressBackgroundTintBlendMode();
        if (progressBackgroundTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(progressBackgroundTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getProgressBackgroundTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBackgroundBlendMode;
        }
        return null;
    }

    @android.view.RemotableViewMethod
    public void setSecondaryProgressTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mSecondaryProgressTintList = colorStateList;
        this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        if (this.mProgressDrawable != null) {
            applySecondaryProgressTint();
        }
    }

    public android.content.res.ColorStateList getSecondaryProgressTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mSecondaryProgressTintList;
        }
        return null;
    }

    public void setSecondaryProgressTintMode(android.graphics.PorterDuff.Mode mode) {
        setSecondaryProgressTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setSecondaryProgressTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new android.widget.ProgressBar.ProgressTintInfo();
        }
        this.mProgressTintInfo.mSecondaryProgressBlendMode = blendMode;
        this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        if (this.mProgressDrawable != null) {
            applySecondaryProgressTint();
        }
    }

    public android.graphics.PorterDuff.Mode getSecondaryProgressTintMode() {
        android.graphics.BlendMode secondaryProgressTintBlendMode = getSecondaryProgressTintBlendMode();
        if (secondaryProgressTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(secondaryProgressTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getSecondaryProgressTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mSecondaryProgressBlendMode;
        }
        return null;
    }

    private android.graphics.drawable.Drawable getTintTarget(int i, boolean z) {
        android.graphics.drawable.Drawable drawable = this.mProgressDrawable;
        android.graphics.drawable.Drawable drawable2 = null;
        if (drawable == null) {
            return null;
        }
        this.mProgressDrawable = drawable.mutate();
        if (drawable instanceof android.graphics.drawable.LayerDrawable) {
            drawable2 = ((android.graphics.drawable.LayerDrawable) drawable).findDrawableByLayerId(i);
        }
        return (z && drawable2 == null) ? drawable : drawable2;
    }

    public void setProgressDrawableTiled(android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            drawable = tileify(drawable, false);
        }
        setProgressDrawable(drawable);
    }

    public android.graphics.drawable.Drawable getCurrentDrawable() {
        return this.mCurrentDrawable;
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return drawable == this.mProgressDrawable || drawable == this.mIndeterminateDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.jumpToCurrentState();
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onResolveDrawables(int i) {
        android.graphics.drawable.Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.setLayoutDirection(i);
        }
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setLayoutDirection(i);
        }
    }

    @Override // android.view.View
    public void postInvalidate() {
        if (!this.mNoInvalidate) {
            super.postInvalidate();
        }
    }

    private class RefreshProgressRunnable implements java.lang.Runnable {
        private RefreshProgressRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.widget.ProgressBar.this) {
                int size = android.widget.ProgressBar.this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    android.widget.ProgressBar.RefreshData refreshData = (android.widget.ProgressBar.RefreshData) android.widget.ProgressBar.this.mRefreshData.get(i);
                    android.widget.ProgressBar.this.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    refreshData.recycle();
                }
                android.widget.ProgressBar.this.mRefreshData.clear();
                android.widget.ProgressBar.this.mRefreshIsPosted = false;
            }
        }
    }

    private static class RefreshData {
        private static final int POOL_MAX = 24;
        private static final android.util.Pools.SynchronizedPool<android.widget.ProgressBar.RefreshData> sPool = new android.util.Pools.SynchronizedPool<>(24);
        public boolean animate;
        public boolean fromUser;
        public int id;
        public int progress;

        private RefreshData() {
        }

        public static android.widget.ProgressBar.RefreshData obtain(int i, int i2, boolean z, boolean z2) {
            android.widget.ProgressBar.RefreshData acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new android.widget.ProgressBar.RefreshData();
            }
            acquire.id = i;
            acquire.progress = i2;
            acquire.fromUser = z;
            acquire.animate = z2;
            return acquire;
        }

        public void recycle() {
            sPool.release(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doRefreshProgress(int i, int i2, boolean z, boolean z2, boolean z3) {
        int i3 = this.mMax - this.mMin;
        float f = i3 > 0 ? (i2 - this.mMin) / i3 : 0.0f;
        boolean z4 = i == 16908301;
        if (z4 && z3) {
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, f);
            ofFloat.setAutoCancel(true);
            ofFloat.setDuration(80L);
            ofFloat.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
            ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.widget.ProgressBar.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    android.widget.ProgressBar.this.mLastProgressAnimator = null;
                }
            });
            ofFloat.start();
            this.mLastProgressAnimator = ofFloat;
        } else {
            if (z4 && this.mLastProgressAnimator != null) {
                this.mLastProgressAnimator.cancel();
                this.mLastProgressAnimator = null;
            }
            setVisualProgress(i, f);
        }
        if (z4 && z2) {
            onProgressRefresh(f, z, i2);
        }
    }

    private float getPercent(int i) {
        float max = getMax();
        float min = getMin();
        float f = i;
        float f2 = max - min;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        return java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, (f - min) / f2));
    }

    private java.lang.CharSequence formatStateDescription(int i) {
        java.util.Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (!locale.equals(this.mCachedLocale)) {
            this.mCachedLocale = locale;
            this.mPercentFormat = java.text.NumberFormat.getPercentInstance(locale);
        }
        return this.mPercentFormat.format(getPercent(i));
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setStateDescription(java.lang.CharSequence charSequence) {
        super.setStateDescription(charSequence);
    }

    void onProgressRefresh(float f, boolean z, int i) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() && getStateDescription() == null && !isIndeterminate()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVisualProgress(int i, float f) {
        this.mVisualProgress = f;
        android.graphics.drawable.Drawable drawable = this.mCurrentDrawable;
        if ((drawable instanceof android.graphics.drawable.LayerDrawable) && (drawable = ((android.graphics.drawable.LayerDrawable) drawable).findDrawableByLayerId(i)) == null) {
            drawable = this.mCurrentDrawable;
        }
        if (drawable != null) {
            drawable.setLevel((int) (10000.0f * f));
        } else {
            invalidate();
        }
        onVisualProgressChanged(i, f);
    }

    void onVisualProgressChanged(int i, float f) {
    }

    private synchronized void refreshProgress(int i, int i2, boolean z, boolean z2) {
        if (this.mUiThreadId == java.lang.Thread.currentThread().getId()) {
            doRefreshProgress(i, i2, z, true, z2);
        } else {
            if (this.mRefreshProgressRunnable == null) {
                this.mRefreshProgressRunnable = new android.widget.ProgressBar.RefreshProgressRunnable();
            }
            this.mRefreshData.add(android.widget.ProgressBar.RefreshData.obtain(i, i2, z, z2));
            if (this.mAttached && !this.mRefreshIsPosted) {
                post(this.mRefreshProgressRunnable);
                this.mRefreshIsPosted = true;
            }
        }
    }

    @android.view.RemotableViewMethod
    public synchronized void setProgress(int i) {
        setProgressInternal(i, false, false);
    }

    public void setProgress(int i, boolean z) {
        setProgressInternal(i, false, z);
    }

    @android.view.RemotableViewMethod
    synchronized boolean setProgressInternal(int i, boolean z, boolean z2) {
        if (this.mIndeterminate) {
            return false;
        }
        int constrain = android.util.MathUtils.constrain(i, this.mMin, this.mMax);
        if (constrain == this.mProgress) {
            return false;
        }
        this.mProgress = constrain;
        refreshProgress(16908301, this.mProgress, z, z2);
        return true;
    }

    @android.view.RemotableViewMethod
    public synchronized void setSecondaryProgress(int i) {
        if (this.mIndeterminate) {
            return;
        }
        if (i < this.mMin) {
            i = this.mMin;
        }
        if (i > this.mMax) {
            i = this.mMax;
        }
        if (i != this.mSecondaryProgress) {
            this.mSecondaryProgress = i;
            refreshProgress(16908303, this.mSecondaryProgress, false, false);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = android.app.Notification.CATEGORY_PROGRESS)
    public synchronized int getProgress() {
        return this.mIndeterminate ? 0 : this.mProgress;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.app.Notification.CATEGORY_PROGRESS)
    public synchronized int getSecondaryProgress() {
        return this.mIndeterminate ? 0 : this.mSecondaryProgress;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.app.Notification.CATEGORY_PROGRESS)
    public synchronized int getMin() {
        return this.mMin;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.app.Notification.CATEGORY_PROGRESS)
    public synchronized int getMax() {
        return this.mMax;
    }

    @android.view.RemotableViewMethod
    public synchronized void setMin(int i) {
        if (this.mMaxInitialized && i > this.mMax) {
            i = this.mMax;
        }
        this.mMinInitialized = true;
        if (this.mMaxInitialized && i != this.mMin) {
            this.mMin = i;
            postInvalidate();
            if (this.mProgress < i) {
                this.mProgress = i;
            }
            refreshProgress(16908301, this.mProgress, false, false);
        } else {
            this.mMin = i;
        }
    }

    @android.view.RemotableViewMethod
    public synchronized void setMax(int i) {
        if (this.mMinInitialized && i < this.mMin) {
            i = this.mMin;
        }
        this.mMaxInitialized = true;
        if (this.mMinInitialized && i != this.mMax) {
            this.mMax = i;
            postInvalidate();
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            refreshProgress(16908301, this.mProgress, false, false);
        } else {
            this.mMax = i;
        }
    }

    public final synchronized void incrementProgressBy(int i) {
        setProgress(this.mProgress + i);
    }

    public final synchronized void incrementSecondaryProgressBy(int i) {
        setSecondaryProgress(this.mSecondaryProgress + i);
    }

    void startAnimation() {
        if (getVisibility() != 0 || getWindowVisibility() != 0) {
            return;
        }
        if (this.mIndeterminateDrawable instanceof android.graphics.drawable.Animatable) {
            this.mShouldStartAnimationDrawable = true;
            this.mHasAnimation = false;
        } else {
            this.mHasAnimation = true;
            if (this.mInterpolator == null) {
                this.mInterpolator = new android.view.animation.LinearInterpolator();
            }
            if (this.mTransformation == null) {
                this.mTransformation = new android.view.animation.Transformation();
            } else {
                this.mTransformation.clear();
            }
            if (this.mAnimation == null) {
                this.mAnimation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
            } else {
                this.mAnimation.reset();
            }
            this.mAnimation.setRepeatMode(this.mBehavior);
            this.mAnimation.setRepeatCount(-1);
            this.mAnimation.setDuration(this.mDuration);
            this.mAnimation.setInterpolator(this.mInterpolator);
            this.mAnimation.setStartTime(-1L);
        }
        postInvalidate();
    }

    void stopAnimation() {
        this.mHasAnimation = false;
        if (this.mIndeterminateDrawable instanceof android.graphics.drawable.Animatable) {
            ((android.graphics.drawable.Animatable) this.mIndeterminateDrawable).stop();
            this.mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    public void setInterpolator(android.content.Context context, int i) {
        setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context, i));
    }

    public void setInterpolator(android.view.animation.Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public android.view.animation.Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z != this.mAggregatedIsVisible) {
            this.mAggregatedIsVisible = z;
            if (this.mIndeterminate) {
                if (z) {
                    startAnimation();
                } else {
                    stopAnimation();
                }
            }
            if (this.mCurrentDrawable != null) {
                this.mCurrentDrawable.setVisible(z, false);
            }
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        if (!this.mInDrawing) {
            if (verifyDrawable(drawable)) {
                android.graphics.Rect bounds = drawable.getBounds();
                int i = this.mScrollX + this.mPaddingLeft;
                int i2 = this.mScrollY + this.mPaddingTop;
                invalidate(bounds.left + i, bounds.top + i2, bounds.right + i, bounds.bottom + i2);
                return;
            }
            super.invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        updateDrawableBounds(i, i2);
    }

    private void updateDrawableBounds(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6 = i - (this.mPaddingRight + this.mPaddingLeft);
        int i7 = i2 - (this.mPaddingTop + this.mPaddingBottom);
        if (this.mIndeterminateDrawable != null) {
            if (this.mOnlyIndeterminate && !(this.mIndeterminateDrawable instanceof android.graphics.drawable.AnimationDrawable)) {
                float intrinsicWidth = this.mIndeterminateDrawable.getIntrinsicWidth() / this.mIndeterminateDrawable.getIntrinsicHeight();
                float f = i6;
                float f2 = i7;
                float f3 = f / f2;
                if (intrinsicWidth != f3) {
                    if (f3 > intrinsicWidth) {
                        int i8 = (int) (f2 * intrinsicWidth);
                        int i9 = (i6 - i8) / 2;
                        i5 = i9;
                        i3 = i8 + i9;
                        i4 = 0;
                    } else {
                        int i10 = (int) (f * (1.0f / intrinsicWidth));
                        int i11 = (i7 - i10) / 2;
                        int i12 = i10 + i11;
                        i3 = i6;
                        i5 = 0;
                        i4 = i11;
                        i7 = i12;
                    }
                    if (!isLayoutRtl() && this.mMirrorForRtl) {
                        int i13 = i6 - i3;
                        i6 -= i5;
                        i5 = i13;
                    } else {
                        i6 = i3;
                    }
                    this.mIndeterminateDrawable.setBounds(i5, i4, i6, i7);
                }
            }
            i3 = i6;
            i4 = 0;
            i5 = 0;
            if (!isLayoutRtl()) {
            }
            i6 = i3;
            this.mIndeterminateDrawable.setBounds(i5, i4, i6, i7);
        }
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setBounds(0, 0, i6, i7);
        }
    }

    @Override // android.view.View
    protected synchronized void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void drawTrack(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = this.mCurrentDrawable;
        if (drawable != 0) {
            int save = canvas.save();
            if (isLayoutRtl() && this.mMirrorForRtl) {
                canvas.translate(getWidth() - this.mPaddingRight, this.mPaddingTop);
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            }
            long drawingTime = getDrawingTime();
            if (this.mHasAnimation) {
                this.mAnimation.getTransformation(drawingTime, this.mTransformation);
                float alpha = this.mTransformation.getAlpha();
                try {
                    this.mInDrawing = true;
                    drawable.setLevel((int) (alpha * 10000.0f));
                    this.mInDrawing = false;
                    postInvalidateOnAnimation();
                } catch (java.lang.Throwable th) {
                    this.mInDrawing = false;
                    throw th;
                }
            }
            drawable.draw(canvas);
            canvas.restoreToCount(save);
            if (this.mShouldStartAnimationDrawable && (drawable instanceof android.graphics.drawable.Animatable)) {
                ((android.graphics.drawable.Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override // android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        android.graphics.drawable.Drawable drawable = this.mCurrentDrawable;
        if (drawable == null) {
            i3 = 0;
            i4 = 0;
        } else {
            i4 = java.lang.Math.max(this.mMinWidth, java.lang.Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
            i3 = java.lang.Math.max(this.mMinHeight, java.lang.Math.min(this.mMaxHeight, drawable.getIntrinsicHeight()));
        }
        updateDrawableState();
        setMeasuredDimension(resolveSizeAndState(i4 + this.mPaddingLeft + this.mPaddingRight, i, 0), resolveSizeAndState(i3 + this.mPaddingTop + this.mPaddingBottom, i2, 0));
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        int[] drawableState = getDrawableState();
        android.graphics.drawable.Drawable drawable = this.mProgressDrawable;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        android.graphics.drawable.Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setHotspot(f, f2);
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.setHotspot(f, f2);
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.ProgressBar.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.ProgressBar.SavedState>() { // from class: android.widget.ProgressBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ProgressBar.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.ProgressBar.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ProgressBar.SavedState[] newArray(int i) {
                return new android.widget.ProgressBar.SavedState[i];
            }
        };
        int progress;
        int secondaryProgress;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.secondaryProgress = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.secondaryProgress);
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.ProgressBar.SavedState savedState = new android.widget.ProgressBar.SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        savedState.secondaryProgress = this.mSecondaryProgress;
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.ProgressBar.SavedState savedState = (android.widget.ProgressBar.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.progress);
        setSecondaryProgress(savedState.secondaryProgress);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            startAnimation();
        }
        if (this.mRefreshData != null) {
            synchronized (this) {
                int size = this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    android.widget.ProgressBar.RefreshData refreshData = this.mRefreshData.get(i);
                    doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    refreshData.recycle();
                }
                this.mRefreshData.clear();
            }
        }
        this.mAttached = true;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        if (this.mIndeterminate) {
            stopAnimation();
        }
        if (this.mRefreshProgressRunnable != null) {
            removeCallbacks(this.mRefreshProgressRunnable);
            this.mRefreshIsPosted = false;
        }
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ProgressBar.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setItemCount(this.mMax - this.mMin);
        accessibilityEvent.setCurrentItemIndex(this.mProgress);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (!isIndeterminate()) {
            accessibilityNodeInfo.setRangeInfo(android.view.accessibility.AccessibilityNodeInfo.RangeInfo.obtain(0, getMin(), getMax(), getProgress()));
        }
        if (getStateDescription() == null) {
            if (isIndeterminate()) {
                accessibilityNodeInfo.setStateDescription(getResources().getString(com.android.internal.R.string.in_progress));
            } else {
                accessibilityNodeInfo.setStateDescription(formatStateDescription(this.mProgress));
            }
        }
    }

    @Override // android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("progress:max", getMax());
        viewHierarchyEncoder.addProperty("progress:progress", getProgress());
        viewHierarchyEncoder.addProperty("progress:secondaryProgress", getSecondaryProgress());
        viewHierarchyEncoder.addProperty("progress:indeterminate", isIndeterminate());
    }

    public boolean isAnimating() {
        return isIndeterminate() && getWindowVisibility() == 0 && isShown();
    }

    private static class ProgressTintInfo {
        boolean mHasIndeterminateTint;
        boolean mHasIndeterminateTintMode;
        boolean mHasProgressBackgroundTint;
        boolean mHasProgressBackgroundTintMode;
        boolean mHasProgressTint;
        boolean mHasProgressTintMode;
        boolean mHasSecondaryProgressTint;
        boolean mHasSecondaryProgressTintMode;
        android.graphics.BlendMode mIndeterminateBlendMode;
        android.content.res.ColorStateList mIndeterminateTintList;
        android.graphics.BlendMode mProgressBackgroundBlendMode;
        android.content.res.ColorStateList mProgressBackgroundTintList;
        android.graphics.BlendMode mProgressBlendMode;
        android.content.res.ColorStateList mProgressTintList;
        android.graphics.BlendMode mSecondaryProgressBlendMode;
        android.content.res.ColorStateList mSecondaryProgressTintList;

        private ProgressTintInfo() {
        }
    }
}
