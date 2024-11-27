package com.android.internal.widget;

/* loaded from: classes5.dex */
class ViewGroupFader {
    private static final float ALPHA_LOWER_BOUND = 0.5f;
    private static final float CHAINED_BOUNDS_BOTTOM_FRACTION = 0.2f;
    private static final float CHAINED_BOUNDS_TOP_FRACTION = 0.6f;
    private static final float CHAINED_LOWER_REGION_FRACTION = 0.35f;
    private static final float CHAINED_UPPER_REGION_FRACTION = 0.55f;
    private static final float SCALE_LOWER_BOUND = 0.7f;
    private float mBottomBoundPixels;
    private final com.android.internal.widget.ViewGroupFader.AnimationCallback mCallback;
    private final com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider mChildViewBoundsProvider;
    protected final android.view.ViewGroup mParent;
    private float mTopBoundPixels;
    private float mScaleLowerBound = SCALE_LOWER_BOUND;
    private float mAlphaLowerBound = 0.5f;
    private float mChainedBoundsTop = 0.6f;
    private float mChainedBoundsBottom = 0.2f;
    private float mChainedLowerRegion = CHAINED_LOWER_REGION_FRACTION;
    private float mChainedUpperRegion = CHAINED_UPPER_REGION_FRACTION;
    private final android.graphics.Rect mContainerBounds = new android.graphics.Rect();
    private final android.graphics.Rect mOffsetViewBounds = new android.graphics.Rect();
    private android.view.animation.BaseInterpolator mTopInterpolator = new android.view.animation.PathInterpolator(0.3f, 0.0f, SCALE_LOWER_BOUND, 1.0f);
    private android.view.animation.BaseInterpolator mBottomInterpolator = new android.view.animation.PathInterpolator(0.3f, 0.0f, SCALE_LOWER_BOUND, 1.0f);
    private com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider mContainerBoundsProvider = new com.android.internal.widget.ViewGroupFader.ScreenContainerBoundsProvider();

    interface AnimationCallback {
        boolean shouldFadeFromBottom(android.view.View view);

        boolean shouldFadeFromTop(android.view.View view);

        void viewHasBecomeFullSize(android.view.View view);
    }

    interface ChildViewBoundsProvider {
        void provideBounds(android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect);
    }

    interface ContainerBoundsProvider {
        void provideBounds(android.view.ViewGroup viewGroup, android.graphics.Rect rect);
    }

    static final class ScreenContainerBoundsProvider implements com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider {
        ScreenContainerBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider
        public void provideBounds(android.view.ViewGroup viewGroup, android.graphics.Rect rect) {
            rect.set(0, 0, viewGroup.getResources().getDisplayMetrics().widthPixels, viewGroup.getResources().getDisplayMetrics().heightPixels);
        }
    }

    static final class ParentContainerBoundsProvider implements com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider {
        ParentContainerBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider
        public void provideBounds(android.view.ViewGroup viewGroup, android.graphics.Rect rect) {
            viewGroup.getGlobalVisibleRect(rect);
        }
    }

    static final class DefaultViewBoundsProvider implements com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider {
        DefaultViewBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider
        public void provideBounds(android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect) {
            view.getDrawingRect(rect);
            rect.offset(0, (int) view.getTranslationY());
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            android.graphics.Rect rect2 = new android.graphics.Rect();
            viewGroup.getGlobalVisibleRect(rect2);
            rect.offset(rect2.left, rect2.top);
        }
    }

    static final class GlobalVisibleViewBoundsProvider implements com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider {
        GlobalVisibleViewBoundsProvider() {
        }

        @Override // com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider
        public void provideBounds(android.view.ViewGroup viewGroup, android.view.View view, android.graphics.Rect rect) {
            view.getGlobalVisibleRect(rect);
        }
    }

    ViewGroupFader(android.view.ViewGroup viewGroup, com.android.internal.widget.ViewGroupFader.AnimationCallback animationCallback, com.android.internal.widget.ViewGroupFader.ChildViewBoundsProvider childViewBoundsProvider) {
        this.mParent = viewGroup;
        this.mCallback = animationCallback;
        this.mChildViewBoundsProvider = childViewBoundsProvider;
    }

    com.android.internal.widget.ViewGroupFader.AnimationCallback getAnimationCallback() {
        return this.mCallback;
    }

    void setScaleLowerBound(float f) {
        this.mScaleLowerBound = f;
    }

    void setAlphaLowerBound(float f) {
        this.mAlphaLowerBound = f;
    }

    void setTopInterpolator(android.view.animation.BaseInterpolator baseInterpolator) {
        this.mTopInterpolator = baseInterpolator;
    }

    void setBottomInterpolator(android.view.animation.BaseInterpolator baseInterpolator) {
        this.mBottomInterpolator = baseInterpolator;
    }

    void setContainerBoundsProvider(com.android.internal.widget.ViewGroupFader.ContainerBoundsProvider containerBoundsProvider) {
        this.mContainerBoundsProvider = containerBoundsProvider;
    }

    void updateFade() {
        this.mContainerBoundsProvider.provideBounds(this.mParent, this.mContainerBounds);
        this.mTopBoundPixels = this.mContainerBounds.height() * this.mChainedBoundsTop;
        this.mBottomBoundPixels = this.mContainerBounds.height() * this.mChainedBoundsBottom;
        updateListElementFades(this.mParent, true);
    }

    private void updateListElementFades(android.view.ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0 && z) {
                fadeElement(viewGroup, childAt);
            }
        }
    }

    private void fadeElement(android.view.ViewGroup viewGroup, android.view.View view) {
        this.mChildViewBoundsProvider.provideBounds(viewGroup, view, this.mOffsetViewBounds);
        setViewPropertiesByPosition(view, this.mOffsetViewBounds, this.mTopBoundPixels, this.mBottomBoundPixels);
    }

    private void setViewPropertiesByPosition(android.view.View view, android.graphics.Rect rect, float f, float f2) {
        float f3;
        if (view.getHeight() < f && view.getHeight() > f2) {
            f3 = lerp(this.mChainedLowerRegion, this.mChainedUpperRegion, (view.getHeight() - f2) / (f - f2));
        } else if (view.getHeight() < f2) {
            f3 = this.mChainedLowerRegion;
        } else {
            f3 = this.mChainedUpperRegion;
        }
        int height = (int) (this.mContainerBounds.height() * f3);
        int i = this.mContainerBounds.top + height;
        int i2 = this.mContainerBounds.bottom - height;
        boolean z = view.getScaleX() == 1.0f;
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.setPivotX(view.getWidth() * 0.5f);
        if (rect.top > i2 && this.mCallback.shouldFadeFromBottom(view)) {
            view.setPivotY(-marginLayoutParams.topMargin);
            scaleAndFadeByRelativeOffsetFraction(view, this.mBottomInterpolator.getInterpolation((this.mContainerBounds.bottom - rect.top) / height));
        } else if (rect.bottom < i && this.mCallback.shouldFadeFromTop(view)) {
            view.setPivotY(view.getMeasuredHeight() + marginLayoutParams.bottomMargin);
            scaleAndFadeByRelativeOffsetFraction(view, this.mTopInterpolator.getInterpolation((rect.bottom - this.mContainerBounds.top) / height));
        } else {
            if (!z) {
                this.mCallback.viewHasBecomeFullSize(view);
            }
            setDefaultSizeAndAlphaForView(view);
        }
    }

    private void scaleAndFadeByRelativeOffsetFraction(android.view.View view, float f) {
        view.setTransitionAlpha(lerp(this.mAlphaLowerBound, 1.0f, f));
        float lerp = lerp(this.mScaleLowerBound, 1.0f, f);
        view.setScaleX(lerp);
        view.setScaleY(lerp);
    }

    private void setDefaultSizeAndAlphaForView(android.view.View view) {
        view.setTransitionAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }

    private static float lerp(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }
}
