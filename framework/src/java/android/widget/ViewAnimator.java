package android.widget;

/* loaded from: classes4.dex */
public class ViewAnimator extends android.widget.FrameLayout {
    boolean mAnimateFirstTime;
    boolean mFirstTime;
    android.view.animation.Animation mInAnimation;
    android.view.animation.Animation mOutAnimation;
    int mWhichChild;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.ViewAnimator> {
        private int mAnimateFirstViewId;
        private int mInAnimationId;
        private int mOutAnimationId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mAnimateFirstViewId = propertyMapper.mapBoolean("animateFirstView", 16843477);
            this.mInAnimationId = propertyMapper.mapObject("inAnimation", 16843127);
            this.mOutAnimationId = propertyMapper.mapObject("outAnimation", 16843128);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.ViewAnimator viewAnimator, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAnimateFirstViewId, viewAnimator.getAnimateFirstView());
            propertyReader.readObject(this.mInAnimationId, viewAnimator.getInAnimation());
            propertyReader.readObject(this.mOutAnimationId, viewAnimator.getOutAnimation());
        }
    }

    public ViewAnimator(android.content.Context context) {
        super(context);
        this.mWhichChild = 0;
        this.mFirstTime = true;
        this.mAnimateFirstTime = true;
        initViewAnimator(context, null);
    }

    public ViewAnimator(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWhichChild = 0;
        this.mFirstTime = true;
        this.mAnimateFirstTime = true;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewAnimator);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ViewAnimator, attributeSet, obtainStyledAttributes, 0, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInAnimation(context, resourceId);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 > 0) {
            setOutAnimation(context, resourceId2);
        }
        setAnimateFirstView(obtainStyledAttributes.getBoolean(2, true));
        obtainStyledAttributes.recycle();
        initViewAnimator(context, attributeSet);
    }

    private void initViewAnimator(android.content.Context context, android.util.AttributeSet attributeSet) {
        if (attributeSet == null) {
            this.mMeasureAllChildren = true;
            return;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.FrameLayout);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.FrameLayout, attributeSet, obtainStyledAttributes, 0, 0);
        setMeasureAllChildren(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
    }

    @android.view.RemotableViewMethod
    public void setDisplayedChild(int i) {
        this.mWhichChild = i;
        if (i >= getChildCount()) {
            this.mWhichChild = 0;
        } else if (i < 0) {
            this.mWhichChild = getChildCount() - 1;
        }
        boolean z = getFocusedChild() != null;
        showOnly(this.mWhichChild);
        if (z) {
            requestFocus(2);
        }
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    @android.view.RemotableViewMethod
    public void showNext() {
        setDisplayedChild(this.mWhichChild + 1);
    }

    @android.view.RemotableViewMethod
    public void showPrevious() {
        setDisplayedChild(this.mWhichChild - 1);
    }

    void showOnly(int i, boolean z) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (i2 == i) {
                if (z && this.mInAnimation != null) {
                    childAt.startAnimation(this.mInAnimation);
                }
                childAt.setVisibility(0);
                this.mFirstTime = false;
            } else {
                if (z && this.mOutAnimation != null && childAt.getVisibility() == 0) {
                    childAt.startAnimation(this.mOutAnimation);
                } else if (childAt.getAnimation() == this.mInAnimation) {
                    childAt.clearAnimation();
                }
                childAt.setVisibility(8);
            }
        }
    }

    void showOnly(int i) {
        showOnly(i, !this.mFirstTime || this.mAnimateFirstTime);
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (getChildCount() == 1) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        if (i >= 0 && this.mWhichChild >= i) {
            setDisplayedChild(this.mWhichChild + 1);
        }
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        this.mWhichChild = 0;
        this.mFirstTime = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(android.view.View view) {
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            removeViewAt(indexOfChild);
        }
    }

    @Override // android.view.ViewGroup
    public void removeViewAt(int i) {
        super.removeViewAt(i);
        int childCount = getChildCount();
        if (childCount == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
        } else if (this.mWhichChild >= childCount) {
            setDisplayedChild(childCount - 1);
        } else if (this.mWhichChild == i) {
            setDisplayedChild(this.mWhichChild);
        }
    }

    @Override // android.view.ViewGroup
    public void removeViewInLayout(android.view.View view) {
        removeView(view);
    }

    @Override // android.view.ViewGroup
    public void removeViews(int i, int i2) {
        super.removeViews(i, i2);
        if (getChildCount() == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
        } else if (this.mWhichChild >= i && this.mWhichChild < i + i2) {
            setDisplayedChild(this.mWhichChild);
        }
    }

    @Override // android.view.ViewGroup
    public void removeViewsInLayout(int i, int i2) {
        removeViews(i, i2);
    }

    public android.view.View getCurrentView() {
        return getChildAt(this.mWhichChild);
    }

    public android.view.animation.Animation getInAnimation() {
        return this.mInAnimation;
    }

    public void setInAnimation(android.view.animation.Animation animation) {
        this.mInAnimation = animation;
    }

    public android.view.animation.Animation getOutAnimation() {
        return this.mOutAnimation;
    }

    public void setOutAnimation(android.view.animation.Animation animation) {
        this.mOutAnimation = animation;
    }

    public void setInAnimation(android.content.Context context, int i) {
        setInAnimation(android.view.animation.AnimationUtils.loadAnimation(context, i));
    }

    public void setOutAnimation(android.content.Context context, int i) {
        setOutAnimation(android.view.animation.AnimationUtils.loadAnimation(context, i));
    }

    public boolean getAnimateFirstView() {
        return this.mAnimateFirstTime;
    }

    public void setAnimateFirstView(boolean z) {
        this.mAnimateFirstTime = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        return getCurrentView() != null ? getCurrentView().getBaseline() : super.getBaseline();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ViewAnimator.class.getName();
    }
}
