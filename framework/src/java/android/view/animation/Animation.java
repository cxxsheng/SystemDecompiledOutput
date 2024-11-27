package android.view.animation;

/* loaded from: classes4.dex */
public abstract class Animation implements java.lang.Cloneable {
    public static final int ABSOLUTE = 0;
    public static final int INFINITE = -1;
    public static final int RELATIVE_TO_PARENT = 2;
    public static final int RELATIVE_TO_SELF = 1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    public static final int START_ON_FIRST_FRAME = -1;
    public static final int ZORDER_BOTTOM = -1;
    public static final int ZORDER_NORMAL = 0;
    public static final int ZORDER_TOP = 1;
    private int mBackdropColor;
    long mDuration;
    private boolean mHasRoundedCorners;
    android.view.animation.Interpolator mInterpolator;
    private android.view.animation.Animation.AnimationListener mListener;
    private android.os.Handler mListenerHandler;
    private java.lang.Runnable mOnEnd;
    private java.lang.Runnable mOnRepeat;
    private java.lang.Runnable mOnStart;
    private boolean mShowBackdrop;
    private boolean mShowWallpaper;
    long mStartOffset;
    private int mZAdjustment;
    boolean mEnded = false;
    boolean mStarted = false;
    boolean mCycleFlip = false;
    boolean mInitialized = false;
    boolean mFillBefore = true;
    boolean mFillAfter = false;
    boolean mFillEnabled = false;
    long mStartTime = -1;
    int mRepeatCount = 0;
    int mRepeated = 0;
    int mRepeatMode = 1;
    private float mScaleFactor = 1.0f;
    private boolean mMore = true;
    private boolean mOneMoreTime = true;
    android.graphics.RectF mPreviousRegion = new android.graphics.RectF();
    android.graphics.RectF mRegion = new android.graphics.RectF();
    android.view.animation.Transformation mTransformation = new android.view.animation.Transformation();
    android.view.animation.Transformation mPreviousTransformation = new android.view.animation.Transformation();
    private final dalvik.system.CloseGuard guard = dalvik.system.CloseGuard.get();

    public interface AnimationListener {
        void onAnimationEnd(android.view.animation.Animation animation);

        void onAnimationRepeat(android.view.animation.Animation animation);

        void onAnimationStart(android.view.animation.Animation animation);
    }

    private static class NoImagePreloadHolder {
        public static final boolean USE_CLOSEGUARD = android.os.SystemProperties.getBoolean("log.closeguard.Animation", false);

        private NoImagePreloadHolder() {
        }
    }

    public Animation() {
        ensureInterpolator();
    }

    public Animation(android.content.Context context, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Animation);
        setDuration(obtainStyledAttributes.getInt(2, 0));
        setStartOffset(obtainStyledAttributes.getInt(5, 0));
        setFillEnabled(obtainStyledAttributes.getBoolean(9, this.mFillEnabled));
        setFillBefore(obtainStyledAttributes.getBoolean(3, this.mFillBefore));
        setFillAfter(obtainStyledAttributes.getBoolean(4, this.mFillAfter));
        setRepeatCount(obtainStyledAttributes.getInt(6, this.mRepeatCount));
        setRepeatMode(obtainStyledAttributes.getInt(7, 1));
        setZAdjustment(obtainStyledAttributes.getInt(8, 0));
        setBackdropColor(obtainStyledAttributes.getInt(12, 0));
        setDetachWallpaper(obtainStyledAttributes.getBoolean(10, false));
        setShowWallpaper(obtainStyledAttributes.getBoolean(14, false));
        setHasRoundedCorners(obtainStyledAttributes.getBoolean(13, false));
        setShowBackdrop(obtainStyledAttributes.getBoolean(11, false));
        int resourceId = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        if (resourceId > 0) {
            setInterpolator(context, resourceId);
        }
        ensureInterpolator();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.view.animation.Animation mo5398clone() throws java.lang.CloneNotSupportedException {
        android.view.animation.Animation animation = (android.view.animation.Animation) super.clone();
        animation.mPreviousRegion = new android.graphics.RectF();
        animation.mRegion = new android.graphics.RectF();
        animation.mTransformation = new android.view.animation.Transformation();
        animation.mPreviousTransformation = new android.view.animation.Transformation();
        return animation;
    }

    public void reset() {
        this.mPreviousRegion.setEmpty();
        this.mPreviousTransformation.clear();
        this.mInitialized = false;
        this.mCycleFlip = false;
        this.mRepeated = 0;
        this.mMore = true;
        this.mOneMoreTime = true;
        this.mListenerHandler = null;
    }

    public void cancel() {
        if (this.mStarted && !this.mEnded) {
            fireAnimationEnd();
            this.mEnded = true;
            this.guard.close();
        }
        this.mStartTime = Long.MIN_VALUE;
        this.mOneMoreTime = false;
        this.mMore = false;
    }

    public void detach() {
        if (this.mStarted && !this.mEnded) {
            this.mEnded = true;
            this.guard.close();
            fireAnimationEnd();
        }
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }

    public void initialize(int i, int i2, int i3, int i4) {
        reset();
        this.mInitialized = true;
    }

    public void setListenerHandler(android.os.Handler handler) {
        if (this.mListenerHandler == null) {
            this.mOnStart = new java.lang.Runnable() { // from class: android.view.animation.Animation.1
                @Override // java.lang.Runnable
                public void run() {
                    android.view.animation.Animation.this.dispatchAnimationStart();
                }
            };
            this.mOnRepeat = new java.lang.Runnable() { // from class: android.view.animation.Animation.2
                @Override // java.lang.Runnable
                public void run() {
                    android.view.animation.Animation.this.dispatchAnimationRepeat();
                }
            };
            this.mOnEnd = new java.lang.Runnable() { // from class: android.view.animation.Animation.3
                @Override // java.lang.Runnable
                public void run() {
                    android.view.animation.Animation.this.dispatchAnimationEnd();
                }
            };
        }
        this.mListenerHandler = handler;
    }

    public void setInterpolator(android.content.Context context, int i) {
        setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context, i));
    }

    public void setInterpolator(android.view.animation.Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void setStartOffset(long j) {
        this.mStartOffset = j;
    }

    public void setDuration(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Animation duration cannot be negative");
        }
        this.mDuration = j;
    }

    public void restrictDuration(long j) {
        if (this.mStartOffset > j) {
            this.mStartOffset = j;
            this.mDuration = 0L;
            this.mRepeatCount = 0;
            return;
        }
        long j2 = this.mDuration + this.mStartOffset;
        if (j2 > j) {
            this.mDuration = j - this.mStartOffset;
            j2 = j;
        }
        if (this.mDuration <= 0) {
            this.mDuration = 0L;
            this.mRepeatCount = 0;
        } else if (this.mRepeatCount < 0 || this.mRepeatCount > j || this.mRepeatCount * j2 > j) {
            this.mRepeatCount = ((int) (j / j2)) - 1;
            if (this.mRepeatCount < 0) {
                this.mRepeatCount = 0;
            }
        }
    }

    public void scaleCurrentDuration(float f) {
        this.mDuration = (long) (this.mDuration * f);
        this.mStartOffset = (long) (this.mStartOffset * f);
    }

    public void setStartTime(long j) {
        this.mStartTime = j;
        this.mEnded = false;
        this.mStarted = false;
        this.mCycleFlip = false;
        this.mRepeated = 0;
        this.mMore = true;
    }

    public void start() {
        setStartTime(-1L);
    }

    public void startNow() {
        setStartTime(android.view.animation.AnimationUtils.currentAnimationTimeMillis());
    }

    public void setRepeatMode(int i) {
        this.mRepeatMode = i;
    }

    public void setRepeatCount(int i) {
        if (i < 0) {
            i = -1;
        }
        this.mRepeatCount = i;
    }

    public boolean isFillEnabled() {
        return this.mFillEnabled;
    }

    public void setFillEnabled(boolean z) {
        this.mFillEnabled = z;
    }

    public void setFillBefore(boolean z) {
        this.mFillBefore = z;
    }

    public void setFillAfter(boolean z) {
        this.mFillAfter = z;
    }

    public void setZAdjustment(int i) {
        this.mZAdjustment = i;
    }

    @java.lang.Deprecated
    public void setBackgroundColor(int i) {
    }

    protected float getScaleFactor() {
        return this.mScaleFactor;
    }

    @java.lang.Deprecated
    public void setDetachWallpaper(boolean z) {
    }

    public void setShowWallpaper(boolean z) {
        this.mShowWallpaper = z;
    }

    public void setHasRoundedCorners(boolean z) {
        this.mHasRoundedCorners = z;
    }

    public void setShowBackdrop(boolean z) {
        this.mShowBackdrop = z;
    }

    public void setBackdropColor(int i) {
        this.mBackdropColor = i;
    }

    public android.view.animation.Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public long getStartOffset() {
        return this.mStartOffset;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public boolean getFillBefore() {
        return this.mFillBefore;
    }

    public boolean getFillAfter() {
        return this.mFillAfter;
    }

    public int getZAdjustment() {
        return this.mZAdjustment;
    }

    @java.lang.Deprecated
    public int getBackgroundColor() {
        return 0;
    }

    @java.lang.Deprecated
    public boolean getDetachWallpaper() {
        return true;
    }

    public boolean getShowWallpaper() {
        return this.mShowWallpaper;
    }

    public boolean hasRoundedCorners() {
        return this.mHasRoundedCorners;
    }

    public boolean hasExtension() {
        return false;
    }

    public boolean getShowBackdrop() {
        return this.mShowBackdrop;
    }

    public int getBackdropColor() {
        return this.mBackdropColor;
    }

    public boolean willChangeTransformationMatrix() {
        return true;
    }

    public boolean willChangeBounds() {
        return true;
    }

    private boolean hasAnimationListener() {
        return this.mListener != null;
    }

    public void setAnimationListener(android.view.animation.Animation.AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    protected void ensureInterpolator() {
        if (this.mInterpolator == null) {
            this.mInterpolator = new android.view.animation.AccelerateDecelerateInterpolator();
        }
    }

    public long computeDurationHint() {
        return (getStartOffset() + getDuration()) * (getRepeatCount() + 1);
    }

    public void getTransformationAt(float f, android.view.animation.Transformation transformation) {
        applyTransformation(this.mInterpolator.getInterpolation(f), transformation);
    }

    public boolean getTransformation(long j, android.view.animation.Transformation transformation) {
        float f;
        if (this.mStartTime == -1) {
            this.mStartTime = j;
        }
        long startOffset = getStartOffset();
        long j2 = this.mDuration;
        if (j2 != 0) {
            f = (j - (this.mStartTime + startOffset)) / j2;
        } else {
            f = j < this.mStartTime ? 0.0f : 1.0f;
        }
        boolean z = f >= 1.0f || isCanceled();
        this.mMore = !z;
        if (!this.mFillEnabled) {
            f = java.lang.Math.max(java.lang.Math.min(f, 1.0f), 0.0f);
        }
        if ((f >= 0.0f || this.mFillBefore) && (f <= 1.0f || this.mFillAfter)) {
            if (!this.mStarted) {
                fireAnimationStart();
                this.mStarted = true;
                if (android.view.animation.Animation.NoImagePreloadHolder.USE_CLOSEGUARD) {
                    this.guard.open("cancel or detach or getTransformation");
                }
            }
            if (this.mFillEnabled) {
                f = java.lang.Math.max(java.lang.Math.min(f, 1.0f), 0.0f);
            }
            if (this.mCycleFlip) {
                f = 1.0f - f;
            }
            getTransformationAt(f, transformation);
        }
        if (z) {
            if (this.mRepeatCount == this.mRepeated || isCanceled()) {
                if (!this.mEnded) {
                    this.mEnded = true;
                    this.guard.close();
                    fireAnimationEnd();
                }
            } else {
                if (this.mRepeatCount > 0) {
                    this.mRepeated++;
                }
                if (this.mRepeatMode == 2) {
                    this.mCycleFlip = !this.mCycleFlip;
                }
                this.mStartTime = -1L;
                this.mMore = true;
                fireAnimationRepeat();
            }
        }
        if (!this.mMore && this.mOneMoreTime) {
            this.mOneMoreTime = false;
            return true;
        }
        return this.mMore;
    }

    private boolean isCanceled() {
        return this.mStartTime == Long.MIN_VALUE;
    }

    private void fireAnimationStart() {
        if (hasAnimationListener()) {
            if (this.mListenerHandler != null) {
                this.mListenerHandler.postAtFrontOfQueue(this.mOnStart);
            } else {
                dispatchAnimationStart();
            }
        }
    }

    private void fireAnimationRepeat() {
        if (hasAnimationListener()) {
            if (this.mListenerHandler != null) {
                this.mListenerHandler.postAtFrontOfQueue(this.mOnRepeat);
            } else {
                dispatchAnimationRepeat();
            }
        }
    }

    private void fireAnimationEnd() {
        if (hasAnimationListener()) {
            if (this.mListenerHandler != null) {
                this.mListenerHandler.postAtFrontOfQueue(this.mOnEnd);
            } else {
                dispatchAnimationEnd();
            }
        }
    }

    void dispatchAnimationStart() {
        if (this.mListener != null) {
            this.mListener.onAnimationStart(this);
        }
    }

    void dispatchAnimationRepeat() {
        if (this.mListener != null) {
            this.mListener.onAnimationRepeat(this);
        }
    }

    void dispatchAnimationEnd() {
        if (this.mListener != null) {
            this.mListener.onAnimationEnd(this);
        }
    }

    public boolean getTransformation(long j, android.view.animation.Transformation transformation, float f) {
        this.mScaleFactor = f;
        return getTransformation(j, transformation);
    }

    public boolean hasStarted() {
        return this.mStarted;
    }

    public boolean hasEnded() {
        return this.mEnded;
    }

    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
    }

    protected float resolveSize(int i, float f, int i2, int i3) {
        switch (i) {
            case 0:
                return f;
            case 1:
                return i2 * f;
            case 2:
                return i3 * f;
            default:
                return f;
        }
    }

    public void getInvalidateRegion(int i, int i2, int i3, int i4, android.graphics.RectF rectF, android.view.animation.Transformation transformation) {
        android.graphics.RectF rectF2 = this.mRegion;
        android.graphics.RectF rectF3 = this.mPreviousRegion;
        rectF.set(i, i2, i3, i4);
        transformation.getMatrix().mapRect(rectF);
        rectF.inset(-1.0f, -1.0f);
        rectF2.set(rectF);
        rectF.union(rectF3);
        rectF3.set(rectF2);
        android.view.animation.Transformation transformation2 = this.mTransformation;
        android.view.animation.Transformation transformation3 = this.mPreviousTransformation;
        transformation2.set(transformation);
        transformation.set(transformation3);
        transformation3.set(transformation2);
    }

    public void initializeInvalidateRegion(int i, int i2, int i3, int i4) {
        android.graphics.RectF rectF = this.mPreviousRegion;
        rectF.set(i, i2, i3, i4);
        rectF.inset(-1.0f, -1.0f);
        if (this.mFillBefore) {
            applyTransformation(this.mInterpolator.getInterpolation(0.0f), this.mPreviousTransformation);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
        } finally {
            super.finalize();
        }
    }

    public boolean hasAlpha() {
        return false;
    }

    protected static class Description {
        public int type;
        public float value;

        protected Description() {
        }

        static android.view.animation.Animation.Description parseValue(android.util.TypedValue typedValue, android.content.Context context) {
            android.view.animation.Animation.Description description = new android.view.animation.Animation.Description();
            if (typedValue == null) {
                description.type = 0;
                description.value = 0.0f;
            } else {
                if (typedValue.type == 6) {
                    description.type = (typedValue.data & 15) == 1 ? 2 : 1;
                    description.value = android.util.TypedValue.complexToFloat(typedValue.data);
                    return description;
                }
                if (typedValue.type == 4) {
                    description.type = 0;
                    description.value = typedValue.getFloat();
                    return description;
                }
                if (typedValue.type >= 16 && typedValue.type <= 31) {
                    description.type = 0;
                    description.value = typedValue.data;
                    return description;
                }
                if (typedValue.type == 5) {
                    description.type = 0;
                    description.value = android.util.TypedValue.complexToDimension(typedValue.data, context.getResources().getDisplayMetrics());
                    return description;
                }
            }
            description.type = 0;
            description.value = 0.0f;
            return description;
        }
    }
}
