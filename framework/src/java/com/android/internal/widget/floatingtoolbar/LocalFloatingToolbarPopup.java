package com.android.internal.widget.floatingtoolbar;

/* loaded from: classes5.dex */
public final class LocalFloatingToolbarPopup implements com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup {
    private static final int MAX_OVERFLOW_SIZE = 4;
    private static final int MIN_OVERFLOW_SIZE = 2;
    private final android.graphics.drawable.Drawable mArrow;
    private final android.view.animation.AnimationSet mCloseOverflowAnimation;
    private final android.view.ViewGroup mContentContainer;
    private final android.content.Context mContext;
    private final android.animation.AnimatorSet mDismissAnimation;
    private final android.view.animation.Interpolator mFastOutLinearInInterpolator;
    private final android.view.animation.Interpolator mFastOutSlowInInterpolator;
    private boolean mHidden;
    private final android.animation.AnimatorSet mHideAnimation;
    private final int mIconTextSpacing;
    private boolean mIsOverflowOpen;
    private final int mLineHeight;
    private final android.view.animation.Interpolator mLinearOutSlowInInterpolator;
    private final android.view.ViewGroup mMainPanel;
    private android.util.Size mMainPanelSize;
    private final int mMarginHorizontal;
    private final int mMarginVertical;
    private android.view.MenuItem.OnMenuItemClickListener mOnMenuItemClickListener;
    private final android.view.animation.AnimationSet mOpenOverflowAnimation;
    private boolean mOpenOverflowUpwards;
    private final android.graphics.drawable.Drawable mOverflow;
    private final android.view.animation.Animation.AnimationListener mOverflowAnimationListener;
    private final android.widget.ImageButton mOverflowButton;
    private final android.util.Size mOverflowButtonSize;
    private final com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanel mOverflowPanel;
    private android.util.Size mOverflowPanelSize;
    private final com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanelViewHelper mOverflowPanelViewHelper;
    private final android.view.View mParent;
    private final android.widget.PopupWindow mPopupWindow;
    private final android.animation.AnimatorSet mShowAnimation;
    private int mSuggestedWidth;
    private final android.graphics.drawable.AnimatedVectorDrawable mToArrow;
    private final android.graphics.drawable.AnimatedVectorDrawable mToOverflow;
    private int mTransitionDurationScale;
    private final android.graphics.Rect mViewPortOnScreen = new android.graphics.Rect();
    private final android.graphics.Point mCoordsOnWindow = new android.graphics.Point();
    private final int[] mTmpCoords = new int[2];
    private final android.graphics.Region mTouchableRegion = new android.graphics.Region();
    private final android.view.ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new android.view.ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda2
        @Override // android.view.ViewTreeObserver.OnComputeInternalInsetsListener
        public final void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.lambda$new$0(internalInsetsInfo);
        }
    };
    private final java.lang.Runnable mPreparePopupContentRTLHelper = new java.lang.Runnable() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setAlpha(1.0f);
        }
    };
    private boolean mDismissed = true;
    private final java.util.Map<com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr, android.view.MenuItem> mMenuItems = new java.util.LinkedHashMap();
    private final android.view.View.OnClickListener mMenuItemButtonOnClickListener = new android.view.View.OnClickListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.2
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.view.MenuItem menuItem;
            if (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOnMenuItemClickListener == null) {
                return;
            }
            java.lang.Object tag = view.getTag();
            if (!(tag instanceof com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr) || (menuItem = (android.view.MenuItem) com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMenuItems.get((com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr) tag)) == null) {
                return;
            }
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }
    };
    private final android.graphics.Rect mPreviousContentRect = new android.graphics.Rect();
    private boolean mWidthChanged = true;
    private final android.view.animation.Interpolator mLogAccelerateInterpolator = new com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.LogAccelerateInterpolator();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.contentInsets.setEmpty();
        internalInsetsInfo.visibleInsets.setEmpty();
        internalInsetsInfo.touchableRegion.set(this.mTouchableRegion);
        internalInsetsInfo.setTouchableInsets(3);
    }

    public LocalFloatingToolbarPopup(android.content.Context context, android.view.View view) {
        this.mParent = (android.view.View) java.util.Objects.requireNonNull(view);
        this.mContext = applyDefaultTheme(context);
        this.mContentContainer = createContentContainer(this.mContext);
        this.mPopupWindow = createPopupWindow(this.mContentContainer);
        this.mMarginHorizontal = view.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_horizontal_margin);
        this.mMarginVertical = view.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_vertical_margin);
        this.mLineHeight = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_height);
        this.mIconTextSpacing = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_icon_text_spacing);
        this.mFastOutSlowInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(this.mContext, 17563661);
        this.mLinearOutSlowInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(this.mContext, 17563662);
        this.mFastOutLinearInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(this.mContext, 17563663);
        this.mArrow = this.mContext.getResources().getDrawable(com.android.internal.R.drawable.ft_avd_tooverflow, this.mContext.getTheme());
        this.mArrow.setAutoMirrored(true);
        this.mOverflow = this.mContext.getResources().getDrawable(com.android.internal.R.drawable.ft_avd_toarrow, this.mContext.getTheme());
        this.mOverflow.setAutoMirrored(true);
        this.mToArrow = (android.graphics.drawable.AnimatedVectorDrawable) this.mContext.getResources().getDrawable(com.android.internal.R.drawable.ft_avd_toarrow_animation, this.mContext.getTheme());
        this.mToArrow.setAutoMirrored(true);
        this.mToOverflow = (android.graphics.drawable.AnimatedVectorDrawable) this.mContext.getResources().getDrawable(com.android.internal.R.drawable.ft_avd_tooverflow_animation, this.mContext.getTheme());
        this.mToOverflow.setAutoMirrored(true);
        this.mOverflowButton = createOverflowButton();
        this.mOverflowButtonSize = measure(this.mOverflowButton);
        this.mMainPanel = createMainPanel();
        this.mOverflowPanelViewHelper = new com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanelViewHelper(this.mContext, this.mIconTextSpacing);
        this.mOverflowPanel = createOverflowPanel();
        this.mOverflowAnimationListener = createOverflowAnimationListener();
        this.mOpenOverflowAnimation = new android.view.animation.AnimationSet(true);
        this.mOpenOverflowAnimation.setAnimationListener(this.mOverflowAnimationListener);
        this.mCloseOverflowAnimation = new android.view.animation.AnimationSet(true);
        this.mCloseOverflowAnimation.setAnimationListener(this.mOverflowAnimationListener);
        this.mShowAnimation = createEnterAnimation(this.mContentContainer);
        this.mDismissAnimation = createExitAnimation(this.mContentContainer, 150, new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mPopupWindow.dismiss();
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.removeAllViews();
            }
        });
        this.mHideAnimation = createExitAnimation(this.mContentContainer, 0, new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mPopupWindow.dismiss();
            }
        });
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean setOutsideTouchable(boolean z, android.widget.PopupWindow.OnDismissListener onDismissListener) {
        boolean z2;
        if (!(this.mPopupWindow.isOutsideTouchable() ^ z)) {
            z2 = false;
        } else {
            this.mPopupWindow.setOutsideTouchable(z);
            z2 = true;
            this.mPopupWindow.setFocusable(!z);
            this.mPopupWindow.update();
        }
        this.mPopupWindow.setOnDismissListener(onDismissListener);
        return z2;
    }

    private void layoutMenuItems(java.util.List<android.view.MenuItem> list, android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener, int i) {
        cancelOverflowAnimations();
        clearPanels();
        updateMenuItems(list, onMenuItemClickListener);
        java.util.List<android.view.MenuItem> layoutMainPanelItems = layoutMainPanelItems(list, getAdjustedToolbarWidth(i));
        if (!layoutMainPanelItems.isEmpty()) {
            layoutOverflowPanelItems(layoutMainPanelItems);
        }
        updatePopupSize();
    }

    private void updateMenuItems(java.util.List<android.view.MenuItem> list, android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mMenuItems.clear();
        for (android.view.MenuItem menuItem : list) {
            this.mMenuItems.put(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr.of(menuItem), menuItem);
        }
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    private boolean isLayoutRequired(java.util.List<android.view.MenuItem> list) {
        return !com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr.reprEquals(list, this.mMenuItems.values());
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void setWidthChanged(boolean z) {
        this.mWidthChanged = z;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void setSuggestedWidth(int i) {
        this.mWidthChanged = ((double) java.lang.Math.abs(i - this.mSuggestedWidth)) > ((double) this.mSuggestedWidth) * 0.2d;
        this.mSuggestedWidth = i;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void show(java.util.List<android.view.MenuItem> list, android.view.MenuItem.OnMenuItemClickListener onMenuItemClickListener, android.graphics.Rect rect) {
        if (isLayoutRequired(list) || this.mWidthChanged) {
            dismiss();
            layoutMenuItems(list, onMenuItemClickListener, this.mSuggestedWidth);
        } else {
            updateMenuItems(list, onMenuItemClickListener);
        }
        if (!isShowing()) {
            show(rect);
        } else if (!this.mPreviousContentRect.equals(rect)) {
            updateCoordinates(rect);
        }
        this.mWidthChanged = false;
        this.mPreviousContentRect.set(rect);
    }

    private void show(android.graphics.Rect rect) {
        java.util.Objects.requireNonNull(rect);
        if (isShowing()) {
            return;
        }
        this.mHidden = false;
        this.mDismissed = false;
        cancelDismissAndHideAnimations();
        cancelOverflowAnimations();
        refreshCoordinatesAndOverflowDirection(rect);
        preparePopupContent();
        this.mPopupWindow.showAtLocation(this.mParent, 0, this.mCoordsOnWindow.x, this.mCoordsOnWindow.y);
        setTouchableSurfaceInsetsComputer();
        runShowAnimation();
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void dismiss() {
        if (this.mDismissed) {
            return;
        }
        this.mHidden = false;
        this.mDismissed = true;
        this.mHideAnimation.cancel();
        runDismissAnimation();
        setZeroTouchableSurface();
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public void hide() {
        if (!isShowing()) {
            return;
        }
        this.mHidden = true;
        runHideAnimation();
        setZeroTouchableSurface();
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isShowing() {
        return (this.mDismissed || this.mHidden) ? false : true;
    }

    @Override // com.android.internal.widget.floatingtoolbar.FloatingToolbarPopup
    public boolean isHidden() {
        return this.mHidden;
    }

    private void updateCoordinates(android.graphics.Rect rect) {
        java.util.Objects.requireNonNull(rect);
        if (!isShowing() || !this.mPopupWindow.isShowing()) {
            return;
        }
        cancelOverflowAnimations();
        refreshCoordinatesAndOverflowDirection(rect);
        preparePopupContent();
        this.mPopupWindow.update(this.mCoordsOnWindow.x, this.mCoordsOnWindow.y, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
    }

    private void refreshCoordinatesAndOverflowDirection(android.graphics.Rect rect) {
        int i;
        refreshViewPort();
        int min = java.lang.Math.min(rect.centerX() - (this.mPopupWindow.getWidth() / 2), this.mViewPortOnScreen.right - this.mPopupWindow.getWidth());
        int i2 = rect.top - this.mViewPortOnScreen.top;
        int i3 = this.mViewPortOnScreen.bottom - rect.bottom;
        int i4 = this.mMarginVertical * 2;
        int i5 = this.mLineHeight + i4;
        if (!hasOverflow()) {
            if (i2 >= i5) {
                i = rect.top - i5;
            } else if (i3 >= i5) {
                i = rect.bottom;
            } else if (i3 >= this.mLineHeight) {
                i = rect.bottom - this.mMarginVertical;
            } else {
                i = java.lang.Math.max(this.mViewPortOnScreen.top, rect.top - i5);
            }
        } else {
            int calculateOverflowHeight = calculateOverflowHeight(2) + i4;
            int i6 = (this.mViewPortOnScreen.bottom - rect.top) + i5;
            int i7 = (rect.bottom - this.mViewPortOnScreen.top) + i5;
            if (i2 >= calculateOverflowHeight) {
                updateOverflowHeight(i2 - i4);
                i = rect.top - this.mPopupWindow.getHeight();
                this.mOpenOverflowUpwards = true;
            } else if (i2 >= i5 && i6 >= calculateOverflowHeight) {
                updateOverflowHeight(i6 - i4);
                i = rect.top - i5;
                this.mOpenOverflowUpwards = false;
            } else if (i3 >= calculateOverflowHeight) {
                updateOverflowHeight(i3 - i4);
                i = rect.bottom;
                this.mOpenOverflowUpwards = false;
            } else if (i3 >= i5 && this.mViewPortOnScreen.height() >= calculateOverflowHeight) {
                updateOverflowHeight(i7 - i4);
                i = (rect.bottom + i5) - this.mPopupWindow.getHeight();
                this.mOpenOverflowUpwards = true;
            } else {
                updateOverflowHeight(this.mViewPortOnScreen.height() - i4);
                i = this.mViewPortOnScreen.top;
                this.mOpenOverflowUpwards = false;
            }
        }
        this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
        int i8 = this.mTmpCoords[0];
        int i9 = this.mTmpCoords[1];
        this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
        this.mCoordsOnWindow.set(java.lang.Math.max(0, min - (i8 - this.mTmpCoords[0])), java.lang.Math.max(0, i - (i9 - this.mTmpCoords[1])));
    }

    private void runShowAnimation() {
        this.mShowAnimation.start();
    }

    private void runDismissAnimation() {
        this.mDismissAnimation.start();
    }

    private void runHideAnimation() {
        this.mHideAnimation.start();
    }

    private void cancelDismissAndHideAnimations() {
        this.mDismissAnimation.cancel();
        this.mHideAnimation.cancel();
    }

    private void cancelOverflowAnimations() {
        this.mContentContainer.clearAnimation();
        this.mMainPanel.animate().cancel();
        this.mOverflowPanel.animate().cancel();
        this.mToArrow.stop();
        this.mToOverflow.stop();
    }

    private void openOverflow() {
        final int width = this.mOverflowPanelSize.getWidth();
        final int height = this.mOverflowPanelSize.getHeight();
        final int width2 = this.mContentContainer.getWidth();
        final int height2 = this.mContentContainer.getHeight();
        final float y = this.mContentContainer.getY();
        final float x = this.mContentContainer.getX();
        final float width3 = x + this.mContentContainer.getWidth();
        android.view.animation.Animation animation = new android.view.animation.Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.5
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.setWidth(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer, width2 + ((int) (f * (width - width2))));
                if (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.isInRTLMode()) {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setX(x);
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMainPanel.setX(0.0f);
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
                } else {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setX(width3 - com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth());
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMainPanel.setX(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2);
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanel.setX(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width);
                }
            }
        };
        android.view.animation.Animation animation2 = new android.view.animation.Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.6
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.setHeight(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer, height2 + ((int) (f * (height - height2))));
                if (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOpenOverflowUpwards) {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setY(y - (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getHeight() - height2));
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
                }
            }
        };
        final float x2 = this.mOverflowButton.getX();
        final float width4 = isInRTLMode() ? (width + x2) - this.mOverflowButton.getWidth() : (x2 - width) + this.mOverflowButton.getWidth();
        android.view.animation.Animation animation3 = new android.view.animation.Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.7
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowButton.setX(x2 + (f * (width4 - x2)) + (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.isInRTLMode() ? 0.0f : com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2));
            }
        };
        animation.setInterpolator(this.mLogAccelerateInterpolator);
        animation.setDuration(getAdjustedDuration(250));
        animation2.setInterpolator(this.mFastOutSlowInInterpolator);
        animation2.setDuration(getAdjustedDuration(250));
        animation3.setInterpolator(this.mFastOutSlowInInterpolator);
        animation3.setDuration(getAdjustedDuration(250));
        this.mOpenOverflowAnimation.getAnimations().clear();
        this.mOpenOverflowAnimation.getAnimations().clear();
        this.mOpenOverflowAnimation.addAnimation(animation);
        this.mOpenOverflowAnimation.addAnimation(animation2);
        this.mOpenOverflowAnimation.addAnimation(animation3);
        this.mContentContainer.startAnimation(this.mOpenOverflowAnimation);
        this.mIsOverflowOpen = true;
        this.mMainPanel.animate().alpha(0.0f).withLayer().setInterpolator(this.mLinearOutSlowInInterpolator).setDuration(250L).start();
        this.mOverflowPanel.setAlpha(1.0f);
    }

    private void closeOverflow() {
        final int width = this.mMainPanelSize.getWidth();
        final int width2 = this.mContentContainer.getWidth();
        final float x = this.mContentContainer.getX();
        final float width3 = x + this.mContentContainer.getWidth();
        android.view.animation.Animation animation = new android.view.animation.Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.8
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.setWidth(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer, width2 + ((int) (f * (width - width2))));
                if (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.isInRTLMode()) {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setX(x);
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMainPanel.setX(0.0f);
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
                } else {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setX(width3 - com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth());
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMainPanel.setX(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width);
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanel.setX(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2);
                }
            }
        };
        final int height = this.mMainPanelSize.getHeight();
        final int height2 = this.mContentContainer.getHeight();
        final float y = this.mContentContainer.getY() + this.mContentContainer.getHeight();
        android.view.animation.Animation animation2 = new android.view.animation.Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.9
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.setHeight(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer, height2 + ((int) (f * (height - height2))));
                if (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOpenOverflowUpwards) {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.setY(y - com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getHeight());
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
                }
            }
        };
        final float x2 = this.mOverflowButton.getX();
        final float width4 = isInRTLMode() ? (x2 - width2) + this.mOverflowButton.getWidth() : (width2 + x2) - this.mOverflowButton.getWidth();
        android.view.animation.Animation animation3 = new android.view.animation.Animation() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.10
            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowButton.setX(x2 + (f * (width4 - x2)) + (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.isInRTLMode() ? 0.0f : com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.getWidth() - width2));
            }
        };
        animation.setInterpolator(this.mFastOutSlowInInterpolator);
        animation.setDuration(getAdjustedDuration(250));
        animation2.setInterpolator(this.mLogAccelerateInterpolator);
        animation2.setDuration(getAdjustedDuration(250));
        animation3.setInterpolator(this.mFastOutSlowInInterpolator);
        animation3.setDuration(getAdjustedDuration(250));
        this.mCloseOverflowAnimation.getAnimations().clear();
        this.mCloseOverflowAnimation.addAnimation(animation);
        this.mCloseOverflowAnimation.addAnimation(animation2);
        this.mCloseOverflowAnimation.addAnimation(animation3);
        this.mContentContainer.startAnimation(this.mCloseOverflowAnimation);
        this.mIsOverflowOpen = false;
        this.mMainPanel.animate().alpha(1.0f).withLayer().setInterpolator(this.mFastOutLinearInInterpolator).setDuration(100L).start();
        this.mOverflowPanel.animate().alpha(0.0f).withLayer().setInterpolator(this.mLinearOutSlowInInterpolator).setDuration(150L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPanelsStatesAtRestingPosition() {
        this.mOverflowButton.setEnabled(true);
        this.mOverflowPanel.awakenScrollBars();
        if (this.mIsOverflowOpen) {
            setSize(this.mContentContainer, this.mOverflowPanelSize);
            this.mMainPanel.setAlpha(0.0f);
            this.mMainPanel.setVisibility(4);
            this.mOverflowPanel.setAlpha(1.0f);
            this.mOverflowPanel.setVisibility(0);
            this.mOverflowButton.setImageDrawable(this.mArrow);
            this.mOverflowButton.setContentDescription(this.mContext.getString(com.android.internal.R.string.floating_toolbar_close_overflow_description));
            if (isInRTLMode()) {
                this.mContentContainer.setX(this.mMarginHorizontal);
                this.mMainPanel.setX(0.0f);
                this.mOverflowButton.setX(r0.getWidth() - this.mOverflowButtonSize.getWidth());
                this.mOverflowPanel.setX(0.0f);
            } else {
                this.mContentContainer.setX((this.mPopupWindow.getWidth() - r0.getWidth()) - this.mMarginHorizontal);
                this.mMainPanel.setX(-this.mContentContainer.getX());
                this.mOverflowButton.setX(0.0f);
                this.mOverflowPanel.setX(0.0f);
            }
            if (this.mOpenOverflowUpwards) {
                this.mContentContainer.setY(this.mMarginVertical);
                this.mMainPanel.setY(r0.getHeight() - this.mContentContainer.getHeight());
                this.mOverflowButton.setY(r0.getHeight() - this.mOverflowButtonSize.getHeight());
                this.mOverflowPanel.setY(0.0f);
                return;
            }
            this.mContentContainer.setY(this.mMarginVertical);
            this.mMainPanel.setY(0.0f);
            this.mOverflowButton.setY(0.0f);
            this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
            return;
        }
        setSize(this.mContentContainer, this.mMainPanelSize);
        this.mMainPanel.setAlpha(1.0f);
        this.mMainPanel.setVisibility(0);
        this.mOverflowPanel.setAlpha(0.0f);
        this.mOverflowPanel.setVisibility(4);
        this.mOverflowButton.setImageDrawable(this.mOverflow);
        this.mOverflowButton.setContentDescription(this.mContext.getString(com.android.internal.R.string.floating_toolbar_open_overflow_description));
        if (hasOverflow()) {
            if (isInRTLMode()) {
                this.mContentContainer.setX(this.mMarginHorizontal);
                this.mMainPanel.setX(0.0f);
                this.mOverflowButton.setX(0.0f);
                this.mOverflowPanel.setX(0.0f);
            } else {
                this.mContentContainer.setX((this.mPopupWindow.getWidth() - r0.getWidth()) - this.mMarginHorizontal);
                this.mMainPanel.setX(0.0f);
                this.mOverflowButton.setX(r0.getWidth() - this.mOverflowButtonSize.getWidth());
                this.mOverflowPanel.setX(r0.getWidth() - this.mOverflowPanelSize.getWidth());
            }
            if (this.mOpenOverflowUpwards) {
                this.mContentContainer.setY((this.mMarginVertical + this.mOverflowPanelSize.getHeight()) - r0.getHeight());
                this.mMainPanel.setY(0.0f);
                this.mOverflowButton.setY(0.0f);
                this.mOverflowPanel.setY(r0.getHeight() - this.mOverflowPanelSize.getHeight());
                return;
            }
            this.mContentContainer.setY(this.mMarginVertical);
            this.mMainPanel.setY(0.0f);
            this.mOverflowButton.setY(0.0f);
            this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
            return;
        }
        this.mContentContainer.setX(this.mMarginHorizontal);
        this.mContentContainer.setY(this.mMarginVertical);
        this.mMainPanel.setX(0.0f);
        this.mMainPanel.setY(0.0f);
    }

    private void updateOverflowHeight(int i) {
        if (hasOverflow()) {
            int calculateOverflowHeight = calculateOverflowHeight((i - this.mOverflowButtonSize.getHeight()) / this.mLineHeight);
            if (this.mOverflowPanelSize.getHeight() != calculateOverflowHeight) {
                this.mOverflowPanelSize = new android.util.Size(this.mOverflowPanelSize.getWidth(), calculateOverflowHeight);
            }
            setSize(this.mOverflowPanel, this.mOverflowPanelSize);
            if (this.mIsOverflowOpen) {
                setSize(this.mContentContainer, this.mOverflowPanelSize);
                if (this.mOpenOverflowUpwards) {
                    float height = this.mOverflowPanelSize.getHeight() - calculateOverflowHeight;
                    this.mContentContainer.setY(this.mContentContainer.getY() + height);
                    this.mOverflowButton.setY(this.mOverflowButton.getY() - height);
                }
            } else {
                setSize(this.mContentContainer, this.mMainPanelSize);
            }
            updatePopupSize();
        }
    }

    private void updatePopupSize() {
        int i;
        int i2 = 0;
        if (this.mMainPanelSize == null) {
            i = 0;
        } else {
            i2 = java.lang.Math.max(0, this.mMainPanelSize.getWidth());
            i = java.lang.Math.max(0, this.mMainPanelSize.getHeight());
        }
        if (this.mOverflowPanelSize != null) {
            i2 = java.lang.Math.max(i2, this.mOverflowPanelSize.getWidth());
            i = java.lang.Math.max(i, this.mOverflowPanelSize.getHeight());
        }
        this.mPopupWindow.setWidth(i2 + (this.mMarginHorizontal * 2));
        this.mPopupWindow.setHeight(i + (this.mMarginVertical * 2));
        maybeComputeTransitionDurationScale();
    }

    private void refreshViewPort() {
        this.mParent.getWindowVisibleDisplayFrame(this.mViewPortOnScreen);
    }

    private int getAdjustedToolbarWidth(int i) {
        refreshViewPort();
        int width = this.mViewPortOnScreen.width() - (this.mParent.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_horizontal_margin) * 2);
        if (i <= 0) {
            i = this.mParent.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_preferred_width);
        }
        return java.lang.Math.min(i, width);
    }

    private void setZeroTouchableSurface() {
        this.mTouchableRegion.setEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setContentAreaAsTouchableSurface() {
        int width;
        int height;
        java.util.Objects.requireNonNull(this.mMainPanelSize);
        if (this.mIsOverflowOpen) {
            java.util.Objects.requireNonNull(this.mOverflowPanelSize);
            width = this.mOverflowPanelSize.getWidth();
            height = this.mOverflowPanelSize.getHeight();
        } else {
            width = this.mMainPanelSize.getWidth();
            height = this.mMainPanelSize.getHeight();
        }
        this.mTouchableRegion.set((int) this.mContentContainer.getX(), (int) this.mContentContainer.getY(), ((int) this.mContentContainer.getX()) + width, ((int) this.mContentContainer.getY()) + height);
    }

    private void setTouchableSurfaceInsetsComputer() {
        android.view.ViewTreeObserver viewTreeObserver = this.mPopupWindow.getContentView().getRootView().getViewTreeObserver();
        viewTreeObserver.removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        viewTreeObserver.addOnComputeInternalInsetsListener(this.mInsetsComputer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInRTLMode() {
        return this.mContext.getApplicationInfo().hasRtlSupport() && this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    private boolean hasOverflow() {
        return this.mOverflowPanelSize != null;
    }

    public java.util.List<android.view.MenuItem> layoutMainPanelItems(java.util.List<android.view.MenuItem> list, int i) {
        java.util.Objects.requireNonNull(list);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (android.view.MenuItem menuItem : list) {
            if (menuItem.getItemId() != 16908353 && menuItem.requiresOverflow()) {
                arrayList2.add(menuItem);
            } else {
                arrayList.add(menuItem);
            }
        }
        arrayList.addAll(arrayList2);
        this.mMainPanel.removeAllViews();
        this.mMainPanel.setPaddingRelative(0, 0, 0, 0);
        boolean z = true;
        int i2 = i;
        while (!arrayList.isEmpty()) {
            android.view.MenuItem menuItem2 = (android.view.MenuItem) arrayList.get(0);
            if (!z && menuItem2.requiresOverflow()) {
                break;
            }
            boolean z2 = z && menuItem2.getItemId() == 16908353;
            android.view.View createMenuItemButton = createMenuItemButton(this.mContext, menuItem2, this.mIconTextSpacing, z2);
            if (!z2 && (createMenuItemButton instanceof android.widget.LinearLayout)) {
                ((android.widget.LinearLayout) createMenuItemButton).setGravity(17);
            }
            if (z) {
                createMenuItemButton.setPaddingRelative((int) (createMenuItemButton.getPaddingStart() * 1.5d), createMenuItemButton.getPaddingTop(), createMenuItemButton.getPaddingEnd(), createMenuItemButton.getPaddingBottom());
            }
            boolean z3 = arrayList.size() == 1;
            if (z3) {
                createMenuItemButton.setPaddingRelative(createMenuItemButton.getPaddingStart(), createMenuItemButton.getPaddingTop(), (int) (createMenuItemButton.getPaddingEnd() * 1.5d), createMenuItemButton.getPaddingBottom());
            }
            createMenuItemButton.measure(0, 0);
            int min = java.lang.Math.min(createMenuItemButton.getMeasuredWidth(), i);
            boolean z4 = min <= i2 - this.mOverflowButtonSize.getWidth();
            boolean z5 = z3 && min <= i2;
            if (!z4 && !z5) {
                break;
            }
            setButtonTagAndClickListener(createMenuItemButton, menuItem2);
            createMenuItemButton.setTooltipText(menuItem2.getTooltipText());
            this.mMainPanel.addView(createMenuItemButton);
            android.view.ViewGroup.LayoutParams layoutParams = createMenuItemButton.getLayoutParams();
            layoutParams.width = min;
            createMenuItemButton.setLayoutParams(layoutParams);
            i2 -= min;
            arrayList.remove(0);
            menuItem2.getGroupId();
            z = false;
        }
        if (!arrayList.isEmpty()) {
            this.mMainPanel.setPaddingRelative(0, 0, this.mOverflowButtonSize.getWidth(), 0);
        }
        this.mMainPanelSize = measure(this.mMainPanel);
        return arrayList;
    }

    private void layoutOverflowPanelItems(java.util.List<android.view.MenuItem> list) {
        android.widget.ArrayAdapter arrayAdapter = (android.widget.ArrayAdapter) this.mOverflowPanel.getAdapter();
        arrayAdapter.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arrayAdapter.add(list.get(i));
        }
        this.mOverflowPanel.setAdapter((android.widget.ListAdapter) arrayAdapter);
        if (this.mOpenOverflowUpwards) {
            this.mOverflowPanel.setY(0.0f);
        } else {
            this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
        }
        this.mOverflowPanelSize = new android.util.Size(java.lang.Math.max(getOverflowWidth(), this.mOverflowButtonSize.getWidth()), calculateOverflowHeight(4));
        setSize(this.mOverflowPanel, this.mOverflowPanelSize);
    }

    private void preparePopupContent() {
        this.mContentContainer.removeAllViews();
        if (hasOverflow()) {
            this.mContentContainer.addView(this.mOverflowPanel);
        }
        this.mContentContainer.addView(this.mMainPanel);
        if (hasOverflow()) {
            this.mContentContainer.addView(this.mOverflowButton);
        }
        setPanelsStatesAtRestingPosition();
        setContentAreaAsTouchableSurface();
        if (isInRTLMode()) {
            this.mContentContainer.setAlpha(0.0f);
            this.mContentContainer.post(this.mPreparePopupContentRTLHelper);
        }
    }

    private void clearPanels() {
        this.mOverflowPanelSize = null;
        this.mMainPanelSize = null;
        this.mIsOverflowOpen = false;
        this.mMainPanel.removeAllViews();
        android.widget.ArrayAdapter arrayAdapter = (android.widget.ArrayAdapter) this.mOverflowPanel.getAdapter();
        arrayAdapter.clear();
        this.mOverflowPanel.setAdapter((android.widget.ListAdapter) arrayAdapter);
        this.mContentContainer.removeAllViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void positionContentYCoordinatesIfOpeningOverflowUpwards() {
        if (this.mOpenOverflowUpwards) {
            this.mMainPanel.setY(this.mContentContainer.getHeight() - this.mMainPanelSize.getHeight());
            this.mOverflowButton.setY(this.mContentContainer.getHeight() - this.mOverflowButton.getHeight());
            this.mOverflowPanel.setY(this.mContentContainer.getHeight() - this.mOverflowPanelSize.getHeight());
        }
    }

    private int getOverflowWidth() {
        int count = this.mOverflowPanel.getAdapter().getCount();
        int i = 0;
        for (int i2 = 0; i2 < count; i2++) {
            i = java.lang.Math.max(this.mOverflowPanelViewHelper.calculateWidth((android.view.MenuItem) this.mOverflowPanel.getAdapter().getItem(i2)), i);
        }
        return i;
    }

    private int calculateOverflowHeight(int i) {
        int i2;
        int min = java.lang.Math.min(4, java.lang.Math.min(java.lang.Math.max(2, i), this.mOverflowPanel.getCount()));
        if (min >= this.mOverflowPanel.getCount()) {
            i2 = 0;
        } else {
            i2 = (int) (this.mLineHeight * 0.5f);
        }
        return (min * this.mLineHeight) + this.mOverflowButtonSize.getHeight() + i2;
    }

    private void setButtonTagAndClickListener(android.view.View view, android.view.MenuItem menuItem) {
        view.setTag(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr.of(menuItem));
        view.setOnClickListener(this.mMenuItemButtonOnClickListener);
    }

    private int getAdjustedDuration(int i) {
        if (this.mTransitionDurationScale < 150) {
            return java.lang.Math.max(i - 50, 0);
        }
        if (this.mTransitionDurationScale > 300) {
            return i + 50;
        }
        return (int) (i * android.animation.ValueAnimator.getDurationScale());
    }

    private void maybeComputeTransitionDurationScale() {
        if (this.mMainPanelSize != null && this.mOverflowPanelSize != null) {
            int width = this.mMainPanelSize.getWidth() - this.mOverflowPanelSize.getWidth();
            int height = this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
            this.mTransitionDurationScale = (int) (java.lang.Math.sqrt((width * width) + (height * height)) / this.mContentContainer.getContext().getResources().getDisplayMetrics().density);
        }
    }

    private android.view.ViewGroup createMainPanel() {
        return new android.widget.LinearLayout(this.mContext) { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.11
            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                if (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.isOverflowAnimating()) {
                    i = android.view.View.MeasureSpec.makeMeasureSpec(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMainPanelSize.getWidth(), 1073741824);
                }
                super.onMeasure(i, i2);
            }

            @Override // android.view.ViewGroup
            public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
                return com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.isOverflowAnimating();
            }
        };
    }

    private android.widget.ImageButton createOverflowButton() {
        final android.widget.ImageButton imageButton = (android.widget.ImageButton) android.view.LayoutInflater.from(this.mContext).inflate(com.android.internal.R.layout.floating_popup_overflow_button, (android.view.ViewGroup) null);
        imageButton.setImageDrawable(this.mOverflow);
        imageButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.lambda$createOverflowButton$1(imageButton, view);
            }
        });
        return imageButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOverflowButton$1(android.widget.ImageButton imageButton, android.view.View view) {
        if (this.mIsOverflowOpen) {
            imageButton.setImageDrawable(this.mToOverflow);
            this.mToOverflow.start();
            closeOverflow();
        } else {
            imageButton.setImageDrawable(this.mToArrow);
            this.mToArrow.start();
            openOverflow();
        }
    }

    private com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanel createOverflowPanel() {
        final com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanel overflowPanel = new com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanel(this);
        overflowPanel.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
        overflowPanel.setDivider(null);
        overflowPanel.setDividerHeight(0);
        overflowPanel.setAdapter((android.widget.ListAdapter) new android.widget.ArrayAdapter<android.view.MenuItem>(this.mContext, 0) { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.12
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
                return com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanelViewHelper.getView(getItem(i), com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanelSize.getWidth(), view);
            }
        });
        overflowPanel.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.lambda$createOverflowPanel$2(overflowPanel, adapterView, view, i, j);
            }
        });
        return overflowPanel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOverflowPanel$2(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.OverflowPanel overflowPanel, android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        android.view.MenuItem menuItem = (android.view.MenuItem) overflowPanel.getAdapter().getItem(i);
        if (this.mOnMenuItemClickListener != null) {
            this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOverflowAnimating() {
        return (this.mOpenOverflowAnimation.hasStarted() && !this.mOpenOverflowAnimation.hasEnded()) || (this.mCloseOverflowAnimation.hasStarted() && !this.mCloseOverflowAnimation.hasEnded());
    }

    /* renamed from: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$13, reason: invalid class name */
    class AnonymousClass13 implements android.view.animation.Animation.AnimationListener {
        AnonymousClass13() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(android.view.animation.Animation animation) {
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowButton.setEnabled(false);
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mMainPanel.setVisibility(0);
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mOverflowPanel.setVisibility(0);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(android.view.animation.Animation animation) {
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.mContentContainer.post(new java.lang.Runnable() { // from class: com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.AnonymousClass13.this.lambda$onAnimationEnd$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(android.view.animation.Animation animation) {
        }
    }

    private android.view.animation.Animation.AnimationListener createOverflowAnimationListener() {
        return new com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.AnonymousClass13();
    }

    private static android.util.Size measure(android.view.View view) {
        com.android.internal.util.Preconditions.checkState(view.getParent() == null);
        view.measure(0, 0);
        return new android.util.Size(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    private static void setSize(android.view.View view, int i, int i2) {
        view.setMinimumWidth(i);
        view.setMinimumHeight(i2);
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new android.view.ViewGroup.LayoutParams(0, 0);
        }
        layoutParams.width = i;
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    private static void setSize(android.view.View view, android.util.Size size) {
        setSize(view, size.getWidth(), size.getHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setWidth(android.view.View view, int i) {
        setSize(view, i, view.getLayoutParams().height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setHeight(android.view.View view, int i) {
        setSize(view, view.getLayoutParams().width, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class OverflowPanel extends android.widget.ListView {
        private final com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup mPopup;

        OverflowPanel(com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup localFloatingToolbarPopup) {
            super(((com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup) java.util.Objects.requireNonNull(localFloatingToolbarPopup)).mContext);
            this.mPopup = localFloatingToolbarPopup;
            setScrollBarDefaultDelayBeforeFade(android.view.ViewConfiguration.getScrollDefaultDelay() * 3);
            setScrollIndicators(3);
        }

        @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, android.view.View.MeasureSpec.makeMeasureSpec(this.mPopup.mOverflowPanelSize.getHeight() - this.mPopup.mOverflowButtonSize.getHeight(), 1073741824));
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
            if (this.mPopup.isOverflowAnimating()) {
                return true;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.view.View
        public boolean awakenScrollBars() {
            return super.awakenScrollBars();
        }
    }

    private static final class LogAccelerateInterpolator implements android.view.animation.Interpolator {
        private static final int BASE = 100;
        private static final float LOGS_SCALE = 1.0f / computeLog(1.0f, 100);

        private LogAccelerateInterpolator() {
        }

        private static float computeLog(float f, int i) {
            return (float) (1.0d - java.lang.Math.pow(i, -f));
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return 1.0f - (computeLog(1.0f - f, 100) * LOGS_SCALE);
        }
    }

    private static final class OverflowPanelViewHelper {
        private final android.view.View mCalculator = createMenuButton(null);
        private final android.content.Context mContext;
        private final int mIconTextSpacing;
        private final int mSidePadding;

        OverflowPanelViewHelper(android.content.Context context, int i) {
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
            this.mIconTextSpacing = i;
            this.mSidePadding = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.floating_toolbar_overflow_side_padding);
        }

        public android.view.View getView(android.view.MenuItem menuItem, int i, android.view.View view) {
            java.util.Objects.requireNonNull(menuItem);
            if (view != null) {
                com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.updateMenuItemButton(view, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
            } else {
                view = createMenuButton(menuItem);
            }
            view.setMinimumWidth(i);
            return view;
        }

        public int calculateWidth(android.view.MenuItem menuItem) {
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.updateMenuItemButton(this.mCalculator, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
            this.mCalculator.measure(0, 0);
            return this.mCalculator.getMeasuredWidth();
        }

        private android.view.View createMenuButton(android.view.MenuItem menuItem) {
            android.view.View createMenuItemButton = com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.createMenuItemButton(this.mContext, menuItem, this.mIconTextSpacing, shouldShowIcon(menuItem));
            createMenuItemButton.setPadding(this.mSidePadding, 0, this.mSidePadding, 0);
            return createMenuItemButton;
        }

        private boolean shouldShowIcon(android.view.MenuItem menuItem) {
            return menuItem != null && menuItem.getGroupId() == 16908353;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.View createMenuItemButton(android.content.Context context, android.view.MenuItem menuItem, int i, boolean z) {
        android.view.View inflate = android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.floating_popup_menu_button, (android.view.ViewGroup) null);
        if (menuItem != null) {
            updateMenuItemButton(inflate, menuItem, i, z);
        }
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateMenuItemButton(android.view.View view, android.view.MenuItem menuItem, int i, boolean z) {
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(com.android.internal.R.id.floating_toolbar_menu_item_text);
        textView.setEllipsize(null);
        if (android.text.TextUtils.isEmpty(menuItem.getTitle())) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.lambda$setTextAsync$0(menuItem.getTitle());
        }
        android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.floating_toolbar_menu_item_image);
        if (menuItem.getIcon() == null || !z) {
            imageView.setVisibility(8);
            if (textView != null) {
                textView.setPaddingRelative(0, 0, 0, 0);
            }
        } else {
            imageView.setVisibility(0);
            imageView.setImageDrawable(menuItem.getIcon());
            if (textView != null) {
                textView.setPaddingRelative(i, 0, 0, 0);
            }
        }
        java.lang.CharSequence contentDescription = menuItem.getContentDescription();
        if (android.text.TextUtils.isEmpty(contentDescription)) {
            view.setContentDescription(menuItem.getTitle());
        } else {
            view.setContentDescription(contentDescription);
        }
    }

    private static android.view.ViewGroup createContentContainer(android.content.Context context) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.floating_popup_container, (android.view.ViewGroup) null);
        viewGroup.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        viewGroup.setTag(com.android.internal.widget.floatingtoolbar.FloatingToolbar.FLOATING_TOOLBAR_TAG);
        viewGroup.setClipToOutline(true);
        return viewGroup;
    }

    private static android.widget.PopupWindow createPopupWindow(android.view.ViewGroup viewGroup) {
        android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(viewGroup.getContext());
        android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(linearLayout);
        popupWindow.setClippingEnabled(false);
        popupWindow.setWindowLayoutType(1005);
        popupWindow.setAnimationStyle(0);
        popupWindow.setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
        viewGroup.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        linearLayout.addView(viewGroup);
        return popupWindow;
    }

    private static android.animation.AnimatorSet createEnterAnimation(android.view.View view) {
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, 0.0f, 1.0f).setDuration(150L));
        return animatorSet;
    }

    private static android.animation.AnimatorSet createExitAnimation(android.view.View view, int i, android.animation.Animator.AnimatorListener animatorListener) {
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, 1.0f, 0.0f).setDuration(100L));
        animatorSet.setStartDelay(i);
        animatorSet.addListener(animatorListener);
        return animatorSet;
    }

    private static android.content.Context applyDefaultTheme(android.content.Context context) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16844176});
        int i = obtainStyledAttributes.getBoolean(0, true) ? 16974123 : 16974120;
        obtainStyledAttributes.recycle();
        return new android.view.ContextThemeWrapper(context, i);
    }

    public static final class MenuItemRepr {
        public final int groupId;
        public final int itemId;
        private final android.graphics.drawable.Drawable mIcon;
        public final java.lang.String title;

        private MenuItemRepr(int i, int i2, java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
            this.itemId = i;
            this.groupId = i2;
            this.title = charSequence == null ? null : charSequence.toString();
            this.mIcon = drawable;
        }

        public static com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr of(android.view.MenuItem menuItem) {
            return new com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr(menuItem.getItemId(), menuItem.getGroupId(), menuItem.getTitle(), menuItem.getIcon());
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.itemId), java.lang.Integer.valueOf(this.groupId), this.title, this.mIcon);
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr)) {
                return false;
            }
            com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr menuItemRepr = (com.android.internal.widget.floatingtoolbar.LocalFloatingToolbarPopup.MenuItemRepr) obj;
            return this.itemId == menuItemRepr.itemId && this.groupId == menuItemRepr.groupId && android.text.TextUtils.equals(this.title, menuItemRepr.title) && java.util.Objects.equals(this.mIcon, menuItemRepr.mIcon);
        }

        public static boolean reprEquals(java.util.Collection<android.view.MenuItem> collection, java.util.Collection<android.view.MenuItem> collection2) {
            if (collection.size() != collection2.size()) {
                return false;
            }
            java.util.Iterator<android.view.MenuItem> it = collection2.iterator();
            java.util.Iterator<android.view.MenuItem> it2 = collection.iterator();
            while (it2.hasNext()) {
                if (!of(it2.next()).equals(of(it.next()))) {
                    return false;
                }
            }
            return true;
        }
    }
}
