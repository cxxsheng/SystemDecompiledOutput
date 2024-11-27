package android.widget;

/* loaded from: classes4.dex */
public class RatingBar extends android.widget.AbsSeekBar {
    public static final java.lang.String PLURALS_MAX = "max";
    public static final java.lang.String PLURALS_RATING = "rating";
    private int mNumStars;
    private android.widget.RatingBar.OnRatingBarChangeListener mOnRatingBarChangeListener;
    private int mProgressOnStartTracking;

    public interface OnRatingBarChangeListener {
        void onRatingChanged(android.widget.RatingBar ratingBar, float f, boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.RatingBar> {
        private int mIsIndicatorId;
        private int mNumStarsId;
        private boolean mPropertiesMapped = false;
        private int mRatingId;
        private int mStepSizeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mIsIndicatorId = propertyMapper.mapBoolean("isIndicator", 16843079);
            this.mNumStarsId = propertyMapper.mapInt("numStars", 16843076);
            this.mRatingId = propertyMapper.mapFloat(android.widget.RatingBar.PLURALS_RATING, 16843077);
            this.mStepSizeId = propertyMapper.mapFloat("stepSize", 16843078);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.RatingBar ratingBar, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIsIndicatorId, ratingBar.isIndicator());
            propertyReader.readInt(this.mNumStarsId, ratingBar.getNumStars());
            propertyReader.readFloat(this.mRatingId, ratingBar.getRating());
            propertyReader.readFloat(this.mStepSizeId, ratingBar.getStepSize());
        }
    }

    public RatingBar(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RatingBar(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNumStars = 5;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RatingBar, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.RatingBar, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(0, this.mNumStars);
        setIsIndicator(obtainStyledAttributes.getBoolean(3, !this.mIsUserSeekable));
        float f = obtainStyledAttributes.getFloat(1, -1.0f);
        float f2 = obtainStyledAttributes.getFloat(2, -1.0f);
        obtainStyledAttributes.recycle();
        if (i3 > 0 && i3 != this.mNumStars) {
            setNumStars(i3);
        }
        if (f2 >= 0.0f) {
            setStepSize(f2);
        } else {
            setStepSize(0.5f);
        }
        if (f >= 0.0f) {
            setRating(f);
        }
        this.mTouchProgressOffset = 0.6f;
    }

    public RatingBar(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842876);
    }

    public RatingBar(android.content.Context context) {
        this(context, null);
    }

    public void setOnRatingBarChangeListener(android.widget.RatingBar.OnRatingBarChangeListener onRatingBarChangeListener) {
        this.mOnRatingBarChangeListener = onRatingBarChangeListener;
    }

    public android.widget.RatingBar.OnRatingBarChangeListener getOnRatingBarChangeListener() {
        return this.mOnRatingBarChangeListener;
    }

    public void setIsIndicator(boolean z) {
        this.mIsUserSeekable = !z;
        if (z) {
            setFocusable(16);
        } else {
            setFocusable(1);
        }
    }

    public boolean isIndicator() {
        return !this.mIsUserSeekable;
    }

    public void setNumStars(int i) {
        if (i <= 0) {
            return;
        }
        this.mNumStars = i;
        requestLayout();
    }

    public int getNumStars() {
        return this.mNumStars;
    }

    public void setRating(float f) {
        setProgress(java.lang.Math.round(f * getProgressPerStar()));
    }

    public float getRating() {
        return getProgress() / getProgressPerStar();
    }

    public void setStepSize(float f) {
        if (f <= 0.0f) {
            return;
        }
        float f2 = this.mNumStars / f;
        setMax((int) f2);
        setProgress((int) ((f2 / getMax()) * getProgress()));
    }

    public float getStepSize() {
        return getNumStars() / getMax();
    }

    private float getProgressPerStar() {
        if (this.mNumStars > 0) {
            return (getMax() * 1.0f) / this.mNumStars;
        }
        return 1.0f;
    }

    @Override // android.widget.ProgressBar
    android.graphics.drawable.shapes.Shape getDrawableShape() {
        return new android.graphics.drawable.shapes.RectShape();
    }

    @Override // android.widget.ProgressBar
    void onProgressRefresh(float f, boolean z, int i) {
        super.onProgressRefresh(f, z, i);
        updateSecondaryProgress(i);
        if (!z) {
            dispatchRatingChange(false);
        }
    }

    private void updateSecondaryProgress(int i) {
        float progressPerStar = getProgressPerStar();
        if (progressPerStar > 0.0f) {
            setSecondaryProgress((int) (java.lang.Math.ceil(i / progressPerStar) * progressPerStar));
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mSampleWidth > 0) {
            setMeasuredDimension(resolveSizeAndState(this.mSampleWidth * this.mNumStars, i, 0), getMeasuredHeight());
        }
    }

    @Override // android.widget.AbsSeekBar
    void onStartTrackingTouch() {
        this.mProgressOnStartTracking = getProgress();
        super.onStartTrackingTouch();
    }

    @Override // android.widget.AbsSeekBar
    void onStopTrackingTouch() {
        super.onStopTrackingTouch();
        if (getProgress() != this.mProgressOnStartTracking) {
            dispatchRatingChange(true);
        }
    }

    @Override // android.widget.AbsSeekBar
    void onKeyChange() {
        super.onKeyChange();
        dispatchRatingChange(true);
    }

    void dispatchRatingChange(boolean z) {
        if (this.mOnRatingBarChangeListener != null) {
            this.mOnRatingBarChangeListener.onRatingChanged(this, getRating(), z);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar
    public synchronized void setMax(int i) {
        if (i <= 0) {
            return;
        }
        super.setMax(i);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.RatingBar.class.getName();
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (canUserSetProgress()) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
        float max = getMax() * getStepSize();
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(PLURALS_RATING, java.lang.Float.valueOf(getRating()));
        hashMap.put("max", java.lang.Float.valueOf(max));
        accessibilityNodeInfo.setStateDescription(android.util.PluralsMessageFormatter.format(getContext().getResources(), hashMap, com.android.internal.R.string.rating_label));
    }

    @Override // android.widget.AbsSeekBar
    boolean canUserSetProgress() {
        return super.canUserSetProgress() && !isIndicator();
    }
}
