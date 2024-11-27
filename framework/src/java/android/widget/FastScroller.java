package android.widget;

/* loaded from: classes4.dex */
class FastScroller {
    private static final int DURATION_CROSS_FADE = 50;
    private static final int DURATION_FADE_IN = 150;
    private static final int DURATION_FADE_OUT = 300;
    private static final int DURATION_RESIZE = 100;
    private static final long FADE_TIMEOUT = 1500;
    private static final int MIN_PAGES = 4;
    private static final int OVERLAY_ABOVE_THUMB = 2;
    private static final int OVERLAY_AT_THUMB = 1;
    private static final int OVERLAY_FLOATING = 0;
    private static final int PREVIEW_LEFT = 0;
    private static final int PREVIEW_RIGHT = 1;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final int THUMB_POSITION_INSIDE = 1;
    private static final int THUMB_POSITION_MIDPOINT = 0;
    private boolean mAlwaysShow;
    private android.animation.AnimatorSet mDecorAnimation;
    private boolean mEnabled;
    private int mFirstVisibleItem;
    private int mHeaderCount;
    private float mInitialTouchY;
    private boolean mLayoutFromRight;
    private final android.widget.AbsListView mList;
    private android.widget.Adapter mListAdapter;
    private boolean mLongList;
    private boolean mMatchDragPosition;
    private final int mMinimumTouchTarget;
    private int mOldChildCount;
    private int mOldItemCount;
    private final android.view.ViewGroupOverlay mOverlay;
    private int mOverlayPosition;
    private android.animation.AnimatorSet mPreviewAnimation;
    private final android.view.View mPreviewImage;
    private int mPreviewMinHeight;
    private int mPreviewMinWidth;
    private int mPreviewPadding;
    private final android.widget.TextView mPrimaryText;
    private int mScaledTouchSlop;
    private int mScrollBarStyle;
    private boolean mScrollCompleted;
    private final android.widget.TextView mSecondaryText;
    private android.widget.SectionIndexer mSectionIndexer;
    private java.lang.Object[] mSections;
    private boolean mShowingPreview;
    private boolean mShowingPrimary;
    private int mState;
    private int mTextAppearance;
    private android.content.res.ColorStateList mTextColor;
    private float mTextSize;
    private android.graphics.drawable.Drawable mThumbDrawable;
    private final android.widget.ImageView mThumbImage;
    private int mThumbMinHeight;
    private int mThumbMinWidth;
    private float mThumbOffset;
    private int mThumbPosition;
    private float mThumbRange;
    private android.graphics.drawable.Drawable mTrackDrawable;
    private final android.widget.ImageView mTrackImage;
    private boolean mUpdatingLayout;
    private int mWidth;
    private static final long TAP_TIMEOUT = android.view.ViewConfiguration.getTapTimeout();
    private static android.util.Property<android.view.View, java.lang.Integer> LEFT = new android.util.IntProperty<android.view.View>(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT) { // from class: android.widget.FastScroller.3
        @Override // android.util.IntProperty
        public void setValue(android.view.View view, int i) {
            view.setLeft(i);
        }

        @Override // android.util.Property
        public java.lang.Integer get(android.view.View view) {
            return java.lang.Integer.valueOf(view.getLeft());
        }
    };
    private static android.util.Property<android.view.View, java.lang.Integer> TOP = new android.util.IntProperty<android.view.View>("top") { // from class: android.widget.FastScroller.4
        @Override // android.util.IntProperty
        public void setValue(android.view.View view, int i) {
            view.setTop(i);
        }

        @Override // android.util.Property
        public java.lang.Integer get(android.view.View view) {
            return java.lang.Integer.valueOf(view.getTop());
        }
    };
    private static android.util.Property<android.view.View, java.lang.Integer> RIGHT = new android.util.IntProperty<android.view.View>(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT) { // from class: android.widget.FastScroller.5
        @Override // android.util.IntProperty
        public void setValue(android.view.View view, int i) {
            view.setRight(i);
        }

        @Override // android.util.Property
        public java.lang.Integer get(android.view.View view) {
            return java.lang.Integer.valueOf(view.getRight());
        }
    };
    private static android.util.Property<android.view.View, java.lang.Integer> BOTTOM = new android.util.IntProperty<android.view.View>("bottom") { // from class: android.widget.FastScroller.6
        @Override // android.util.IntProperty
        public void setValue(android.view.View view, int i) {
            view.setBottom(i);
        }

        @Override // android.util.Property
        public java.lang.Integer get(android.view.View view) {
            return java.lang.Integer.valueOf(view.getBottom());
        }
    };
    private final android.graphics.Rect mTempBounds = new android.graphics.Rect();
    private final android.graphics.Rect mTempMargins = new android.graphics.Rect();
    private final android.graphics.Rect mContainerRect = new android.graphics.Rect();
    private final int[] mPreviewResId = new int[2];
    private int mCurrentSection = -1;
    private int mScrollbarPosition = -1;
    private long mPendingDrag = -1;
    private final java.lang.Runnable mDeferHide = new java.lang.Runnable() { // from class: android.widget.FastScroller.1
        @Override // java.lang.Runnable
        public void run() {
            android.widget.FastScroller.this.setState(0);
        }
    };
    private final android.animation.Animator.AnimatorListener mSwitchPrimaryListener = new android.animation.AnimatorListenerAdapter() { // from class: android.widget.FastScroller.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            android.widget.FastScroller.this.mShowingPrimary = !android.widget.FastScroller.this.mShowingPrimary;
        }
    };

    public FastScroller(android.widget.AbsListView absListView, int i) {
        this.mList = absListView;
        this.mOldItemCount = absListView.getCount();
        this.mOldChildCount = absListView.getChildCount();
        android.content.Context context = absListView.getContext();
        this.mScaledTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = absListView.getScrollBarStyle();
        this.mScrollCompleted = true;
        this.mState = 1;
        this.mMatchDragPosition = context.getApplicationInfo().targetSdkVersion >= 11;
        this.mTrackImage = new android.widget.ImageView(context);
        this.mTrackImage.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        this.mThumbImage = new android.widget.ImageView(context);
        this.mThumbImage.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        this.mPreviewImage = new android.view.View(context);
        this.mPreviewImage.setAlpha(0.0f);
        this.mPrimaryText = createPreviewTextView(context);
        this.mSecondaryText = createPreviewTextView(context);
        this.mMinimumTouchTarget = absListView.getResources().getDimensionPixelSize(com.android.internal.R.dimen.fast_scroller_minimum_touch_target);
        setStyle(i);
        android.view.ViewGroupOverlay overlay = absListView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(this.mTrackImage);
        overlay.add(this.mThumbImage);
        overlay.add(this.mPreviewImage);
        overlay.add(this.mPrimaryText);
        overlay.add(this.mSecondaryText);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(absListView.getVerticalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() {
        int i;
        this.mTrackImage.lambda$setImageURIAsync$2(this.mTrackDrawable);
        if (this.mTrackDrawable == null) {
            i = 0;
        } else {
            i = java.lang.Math.max(0, this.mTrackDrawable.getIntrinsicWidth());
        }
        this.mThumbImage.lambda$setImageURIAsync$2(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        if (this.mThumbDrawable != null) {
            i = java.lang.Math.max(i, this.mThumbDrawable.getIntrinsicWidth());
        }
        this.mWidth = java.lang.Math.max(i, this.mThumbMinWidth);
        if (this.mTextAppearance != 0) {
            this.mPrimaryText.setTextAppearance(this.mTextAppearance);
            this.mSecondaryText.setTextAppearance(this.mTextAppearance);
        }
        if (this.mTextColor != null) {
            this.mPrimaryText.setTextColor(this.mTextColor);
            this.mSecondaryText.setTextColor(this.mTextColor);
        }
        if (this.mTextSize > 0.0f) {
            this.mPrimaryText.setTextSize(0, this.mTextSize);
            this.mSecondaryText.setTextSize(0, this.mTextSize);
        }
        int i2 = this.mPreviewPadding;
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mPrimaryText.setPadding(i2, i2, i2, i2);
        this.mSecondaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setPadding(i2, i2, i2, i2);
        refreshDrawablePressedState();
    }

    public void setStyle(int i) {
        android.content.res.TypedArray obtainStyledAttributes = this.mList.getContext().obtainStyledAttributes(null, com.android.internal.R.styleable.FastScroll, 16843767, i);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            switch (index) {
                case 0:
                    this.mTextAppearance = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 1:
                    this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 2:
                    this.mTextColor = obtainStyledAttributes.getColorStateList(index);
                    break;
                case 3:
                    this.mPreviewPadding = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 4:
                    this.mPreviewMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 5:
                    this.mPreviewMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 6:
                    this.mThumbPosition = obtainStyledAttributes.getInt(index, 0);
                    break;
                case 7:
                    this.mPreviewResId[0] = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 8:
                    this.mPreviewResId[1] = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 9:
                    this.mOverlayPosition = obtainStyledAttributes.getInt(index, 0);
                    break;
                case 10:
                    this.mThumbDrawable = obtainStyledAttributes.getDrawable(index);
                    break;
                case 11:
                    this.mThumbMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 12:
                    this.mThumbMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    break;
                case 13:
                    this.mTrackDrawable = obtainStyledAttributes.getDrawable(index);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        updateAppearance();
    }

    public void remove() {
        this.mOverlay.remove(this.mTrackImage);
        this.mOverlay.remove(this.mThumbImage);
        this.mOverlay.remove(this.mPreviewImage);
        this.mOverlay.remove(this.mPrimaryText);
        this.mOverlay.remove(this.mSecondaryText);
    }

    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            onStateDependencyChanged(true);
        }
    }

    public boolean isEnabled() {
        return this.mEnabled && (this.mLongList || this.mAlwaysShow);
    }

    public void setAlwaysShow(boolean z) {
        if (this.mAlwaysShow != z) {
            this.mAlwaysShow = z;
            onStateDependencyChanged(false);
        }
    }

    public boolean isAlwaysShowEnabled() {
        return this.mAlwaysShow;
    }

    private void onStateDependencyChanged(boolean z) {
        if (isEnabled()) {
            if (isAlwaysShowEnabled()) {
                setState(1);
            } else if (this.mState == 1) {
                postAutoHide();
            } else if (z) {
                setState(1);
                postAutoHide();
            }
        } else {
            stop();
        }
        this.mList.resolvePadding();
    }

    public void setScrollBarStyle(int i) {
        if (this.mScrollBarStyle != i) {
            this.mScrollBarStyle = i;
            updateLayout();
        }
    }

    public void stop() {
        setState(0);
    }

    public void setScrollbarPosition(int i) {
        boolean z = true;
        if (i == 0) {
            i = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != i) {
            this.mScrollbarPosition = i;
            if (i == 1) {
                z = false;
            }
            this.mLayoutFromRight = z;
            this.mPreviewImage.setBackgroundResource(this.mPreviewResId[this.mLayoutFromRight ? 1 : 0]);
            int max = java.lang.Math.max(0, (this.mPreviewMinWidth - this.mPreviewImage.getPaddingLeft()) - this.mPreviewImage.getPaddingRight());
            this.mPrimaryText.setMinimumWidth(max);
            this.mSecondaryText.setMinimumWidth(max);
            int max2 = java.lang.Math.max(0, (this.mPreviewMinHeight - this.mPreviewImage.getPaddingTop()) - this.mPreviewImage.getPaddingBottom());
            this.mPrimaryText.setMinimumHeight(max2);
            this.mSecondaryText.setMinimumHeight(max2);
            updateLayout();
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        updateLayout();
    }

    public void onItemCountChanged(int i, int i2) {
        if (this.mOldItemCount != i2 || this.mOldChildCount != i) {
            this.mOldItemCount = i2;
            this.mOldChildCount = i;
            if ((i2 - i > 0) && this.mState != 2) {
                setThumbPos(getPosFromItemCount(this.mList.getFirstVisiblePosition(), i, i2));
            }
            updateLongList(i, i2);
        }
    }

    private void updateLongList(int i, int i2) {
        boolean z = i > 0 && i2 / i >= 4;
        if (this.mLongList != z) {
            this.mLongList = z;
            onStateDependencyChanged(false);
        }
    }

    private android.widget.TextView createPreviewTextView(android.content.Context context) {
        android.view.ViewGroup.LayoutParams layoutParams = new android.view.ViewGroup.LayoutParams(-2, -2);
        android.widget.TextView textView = new android.widget.TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setSingleLine(true);
        textView.setEllipsize(android.text.TextUtils.TruncateAt.MIDDLE);
        textView.setGravity(17);
        textView.setAlpha(0.0f);
        textView.setLayoutDirection(this.mList.getLayoutDirection());
        return textView;
    }

    public void updateLayout() {
        if (this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        updateContainerRect();
        layoutThumb();
        layoutTrack();
        updateOffsetAndRange();
        android.graphics.Rect rect = this.mTempBounds;
        measurePreview(this.mPrimaryText, rect);
        applyLayout(this.mPrimaryText, rect);
        measurePreview(this.mSecondaryText, rect);
        applyLayout(this.mSecondaryText, rect);
        if (this.mPreviewImage != null) {
            rect.left -= this.mPreviewImage.getPaddingLeft();
            rect.top -= this.mPreviewImage.getPaddingTop();
            rect.right += this.mPreviewImage.getPaddingRight();
            rect.bottom += this.mPreviewImage.getPaddingBottom();
            applyLayout(this.mPreviewImage, rect);
        }
        this.mUpdatingLayout = false;
    }

    private void applyLayout(android.view.View view, android.graphics.Rect rect) {
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        view.setPivotX(this.mLayoutFromRight ? rect.right - rect.left : 0.0f);
    }

    private void measurePreview(android.view.View view, android.graphics.Rect rect) {
        android.graphics.Rect rect2 = this.mTempMargins;
        rect2.left = this.mPreviewImage.getPaddingLeft();
        rect2.top = this.mPreviewImage.getPaddingTop();
        rect2.right = this.mPreviewImage.getPaddingRight();
        rect2.bottom = this.mPreviewImage.getPaddingBottom();
        if (this.mOverlayPosition == 0) {
            measureFloating(view, rect2, rect);
        } else {
            measureViewToSide(view, this.mThumbImage, rect2, rect);
        }
    }

    private void measureViewToSide(android.view.View view, android.view.View view2, android.graphics.Rect rect, android.graphics.Rect rect2) {
        int i;
        int i2;
        int i3;
        int right;
        int i4;
        if (rect == null) {
            i3 = 0;
            i = 0;
            i2 = 0;
        } else {
            i = rect.left;
            i2 = rect.top;
            i3 = rect.right;
        }
        android.graphics.Rect rect3 = this.mContainerRect;
        int width = rect3.width();
        if (view2 != null) {
            if (this.mLayoutFromRight) {
                width = view2.getLeft();
            } else {
                width -= view2.getRight();
            }
        }
        int max = java.lang.Math.max(0, rect3.height());
        int max2 = java.lang.Math.max(0, (width - i) - i3);
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(max2, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeSafeMeasureSpec(max, 0));
        int min = java.lang.Math.min(max2, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            i4 = (view2 == null ? rect3.right : view2.getLeft()) - i3;
            right = i4 - min;
        } else {
            right = (view2 == null ? rect3.left : view2.getRight()) + i;
            i4 = right + min;
        }
        rect2.set(right, i2, i4, view.getMeasuredHeight() + i2);
    }

    private void measureFloating(android.view.View view, android.graphics.Rect rect, android.graphics.Rect rect2) {
        int i;
        int i2;
        int i3;
        if (rect == null) {
            i3 = 0;
            i = 0;
            i2 = 0;
        } else {
            i = rect.left;
            i2 = rect.top;
            i3 = rect.right;
        }
        android.graphics.Rect rect3 = this.mContainerRect;
        int width = rect3.width();
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (width - i) - i3), Integer.MIN_VALUE), android.view.View.MeasureSpec.makeSafeMeasureSpec(java.lang.Math.max(0, rect3.height()), 0));
        int height = rect3.height();
        int measuredWidth = view.getMeasuredWidth();
        int i4 = (height / 10) + i2 + rect3.top;
        int measuredHeight = view.getMeasuredHeight() + i4;
        int i5 = ((width - measuredWidth) / 2) + rect3.left;
        rect2.set(i5, i4, measuredWidth + i5, measuredHeight);
    }

    private void updateContainerRect() {
        android.widget.AbsListView absListView = this.mList;
        absListView.resolvePadding();
        android.graphics.Rect rect = this.mContainerRect;
        rect.left = 0;
        rect.top = 0;
        rect.right = absListView.getWidth();
        rect.bottom = absListView.getHeight();
        int i = this.mScrollBarStyle;
        if (i == 16777216 || i == 0) {
            rect.left += absListView.getPaddingLeft();
            rect.top += absListView.getPaddingTop();
            rect.right -= absListView.getPaddingRight();
            rect.bottom -= absListView.getPaddingBottom();
            if (i == 16777216) {
                int width = getWidth();
                if (this.mScrollbarPosition == 2) {
                    rect.right += width;
                } else {
                    rect.left -= width;
                }
            }
        }
    }

    private void layoutThumb() {
        android.graphics.Rect rect = this.mTempBounds;
        measureViewToSide(this.mThumbImage, null, null, rect);
        applyLayout(this.mThumbImage, rect);
    }

    private void layoutTrack() {
        int i;
        int i2;
        android.widget.ImageView imageView = this.mTrackImage;
        android.widget.ImageView imageView2 = this.mThumbImage;
        android.graphics.Rect rect = this.mContainerRect;
        imageView.measure(android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, rect.width()), Integer.MIN_VALUE), android.view.View.MeasureSpec.makeSafeMeasureSpec(java.lang.Math.max(0, rect.height()), 0));
        if (this.mThumbPosition == 1) {
            i2 = rect.top;
            i = rect.bottom;
        } else {
            int height = imageView2.getHeight() / 2;
            int i3 = rect.top + height;
            i = rect.bottom - height;
            i2 = i3;
        }
        int measuredWidth = imageView.getMeasuredWidth();
        int left = imageView2.getLeft() + ((imageView2.getWidth() - measuredWidth) / 2);
        imageView.layout(left, i2, measuredWidth + left, i);
    }

    private void updateOffsetAndRange() {
        float top;
        float bottom;
        android.widget.ImageView imageView = this.mTrackImage;
        android.widget.ImageView imageView2 = this.mThumbImage;
        if (this.mThumbPosition == 1) {
            float height = imageView2.getHeight() / 2.0f;
            top = imageView.getTop() + height;
            bottom = imageView.getBottom() - height;
        } else {
            top = imageView.getTop();
            bottom = imageView.getBottom();
        }
        this.mThumbOffset = top;
        this.mThumbRange = bottom - top;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(int i) {
        this.mList.removeCallbacks(this.mDeferHide);
        if (this.mAlwaysShow && i == 0) {
            i = 1;
        }
        if (i == this.mState) {
            return;
        }
        switch (i) {
            case 0:
                transitionToHidden();
                break;
            case 1:
                transitionToVisible();
                break;
            case 2:
                if (transitionPreviewLayout(this.mCurrentSection)) {
                    transitionToDragging();
                    break;
                } else {
                    transitionToVisible();
                    break;
                }
        }
        this.mState = i;
        refreshDrawablePressedState();
    }

    private void refreshDrawablePressedState() {
        boolean z = this.mState == 2;
        this.mThumbImage.setPressed(z);
        this.mTrackImage.setPressed(z);
    }

    private void transitionToHidden() {
        if (this.mDecorAnimation != null) {
            this.mDecorAnimation.cancel();
        }
        android.animation.Animator duration = groupAnimatorOfFloat(android.view.View.ALPHA, 0.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        android.animation.Animator duration2 = groupAnimatorOfFloat(android.view.View.TRANSLATION_X, this.mLayoutFromRight ? this.mThumbImage.getWidth() : -this.mThumbImage.getWidth(), this.mThumbImage, this.mTrackImage).setDuration(300L);
        this.mDecorAnimation = new android.animation.AnimatorSet();
        this.mDecorAnimation.playTogether(duration, duration2);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToVisible() {
        if (this.mDecorAnimation != null) {
            this.mDecorAnimation.cancel();
        }
        android.animation.Animator duration = groupAnimatorOfFloat(android.view.View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        android.animation.Animator duration2 = groupAnimatorOfFloat(android.view.View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(300L);
        android.animation.Animator duration3 = groupAnimatorOfFloat(android.view.View.TRANSLATION_X, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        this.mDecorAnimation = new android.animation.AnimatorSet();
        this.mDecorAnimation.playTogether(duration, duration2, duration3);
        this.mDecorAnimation.start();
        this.mShowingPreview = false;
    }

    private void transitionToDragging() {
        if (this.mDecorAnimation != null) {
            this.mDecorAnimation.cancel();
        }
        android.animation.Animator duration = groupAnimatorOfFloat(android.view.View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(150L);
        android.animation.Animator duration2 = groupAnimatorOfFloat(android.view.View.TRANSLATION_X, 0.0f, this.mThumbImage, this.mTrackImage).setDuration(150L);
        this.mDecorAnimation = new android.animation.AnimatorSet();
        this.mDecorAnimation.playTogether(duration, duration2);
        this.mDecorAnimation.start();
        this.mShowingPreview = true;
    }

    private void postAutoHide() {
        this.mList.removeCallbacks(this.mDeferHide);
        this.mList.postDelayed(this.mDeferHide, FADE_TIMEOUT);
    }

    public void onScroll(int i, int i2, int i3) {
        if (!isEnabled()) {
            setState(0);
            return;
        }
        if ((i3 - i2 > 0) && this.mState != 2) {
            setThumbPos(getPosFromItemCount(i, i2, i3));
        }
        this.mScrollCompleted = true;
        if (this.mFirstVisibleItem != i) {
            this.mFirstVisibleItem = i;
            if (this.mState != 2) {
                setState(1);
                postAutoHide();
            }
        }
    }

    private void getSectionsFromIndexer() {
        this.mSectionIndexer = null;
        android.widget.ListAdapter adapter = this.mList.getAdapter();
        if (adapter instanceof android.widget.HeaderViewListAdapter) {
            android.widget.HeaderViewListAdapter headerViewListAdapter = (android.widget.HeaderViewListAdapter) adapter;
            this.mHeaderCount = headerViewListAdapter.getHeadersCount();
            adapter = headerViewListAdapter.getWrappedAdapter();
        }
        if (adapter instanceof android.widget.ExpandableListConnector) {
            android.widget.ExpandableListAdapter adapter2 = ((android.widget.ExpandableListConnector) adapter).getAdapter();
            if (adapter2 instanceof android.widget.SectionIndexer) {
                this.mSectionIndexer = (android.widget.SectionIndexer) adapter2;
                this.mListAdapter = adapter;
                this.mSections = this.mSectionIndexer.getSections();
                return;
            }
            return;
        }
        if (adapter instanceof android.widget.SectionIndexer) {
            this.mListAdapter = adapter;
            this.mSectionIndexer = (android.widget.SectionIndexer) adapter;
            this.mSections = this.mSectionIndexer.getSections();
        } else {
            this.mListAdapter = adapter;
            this.mSections = null;
        }
    }

    public void onSectionsChanged() {
        this.mListAdapter = null;
    }

    private void scrollTo(float f) {
        int i;
        int i2;
        this.mScrollCompleted = false;
        int count = this.mList.getCount();
        java.lang.Object[] objArr = this.mSections;
        int length = objArr == null ? 0 : objArr.length;
        if (objArr == null || length <= 1) {
            int constrain = android.util.MathUtils.constrain((int) (f * count), 0, count - 1);
            if (this.mList instanceof android.widget.ExpandableListView) {
                android.widget.ExpandableListView expandableListView = (android.widget.ExpandableListView) this.mList;
                expandableListView.setSelectionFromTop(expandableListView.getFlatListPosition(android.widget.ExpandableListView.getPackedPositionForGroup(constrain + this.mHeaderCount)), 0);
            } else if (this.mList instanceof android.widget.ListView) {
                ((android.widget.ListView) this.mList).setSelectionFromTop(constrain + this.mHeaderCount, 0);
            } else {
                this.mList.setSelection(constrain + this.mHeaderCount);
            }
            i = -1;
        } else {
            float f2 = length;
            int i3 = length - 1;
            int constrain2 = android.util.MathUtils.constrain((int) (f * f2), 0, i3);
            int positionForSection = this.mSectionIndexer.getPositionForSection(constrain2);
            int i4 = constrain2 + 1;
            if (constrain2 >= i3) {
                i2 = count;
            } else {
                i2 = this.mSectionIndexer.getPositionForSection(i4);
            }
            int i5 = constrain2;
            if (i2 == positionForSection) {
                int i6 = positionForSection;
                while (true) {
                    if (i5 <= 0) {
                        i5 = constrain2;
                        positionForSection = i6;
                        i = i5;
                        break;
                    }
                    i5--;
                    i6 = this.mSectionIndexer.getPositionForSection(i5);
                    if (i6 != positionForSection) {
                        positionForSection = i6;
                        i = i5;
                        break;
                    } else if (i5 == 0) {
                        i5 = constrain2;
                        positionForSection = i6;
                        i = 0;
                        break;
                    }
                }
            } else {
                i = i5;
            }
            int i7 = i4 + 1;
            while (i7 < length && this.mSectionIndexer.getPositionForSection(i7) == i2) {
                i7++;
                i4++;
            }
            float f3 = i5 / f2;
            float f4 = i4 / f2;
            float f5 = count == 0 ? Float.MAX_VALUE : 0.125f / count;
            if (i5 != constrain2 || f - f3 >= f5) {
                positionForSection += (int) (((i2 - positionForSection) * (f - f3)) / (f4 - f3));
            }
            int constrain3 = android.util.MathUtils.constrain(positionForSection, 0, count - 1);
            if (this.mList instanceof android.widget.ExpandableListView) {
                android.widget.ExpandableListView expandableListView2 = (android.widget.ExpandableListView) this.mList;
                expandableListView2.setSelectionFromTop(expandableListView2.getFlatListPosition(android.widget.ExpandableListView.getPackedPositionForGroup(constrain3 + this.mHeaderCount)), 0);
            } else if (this.mList instanceof android.widget.ListView) {
                ((android.widget.ListView) this.mList).setSelectionFromTop(constrain3 + this.mHeaderCount, 0);
            } else {
                this.mList.setSelection(constrain3 + this.mHeaderCount);
            }
        }
        if (this.mCurrentSection != i) {
            this.mCurrentSection = i;
            boolean transitionPreviewLayout = transitionPreviewLayout(i);
            if (!this.mShowingPreview && transitionPreviewLayout) {
                transitionToDragging();
            } else if (this.mShowingPreview && !transitionPreviewLayout) {
                transitionToVisible();
            }
        }
    }

    private boolean transitionPreviewLayout(int i) {
        java.lang.String str;
        android.widget.TextView textView;
        android.widget.TextView textView2;
        java.lang.Object obj;
        java.lang.Object[] objArr = this.mSections;
        if (objArr != null && i >= 0 && i < objArr.length && (obj = objArr[i]) != null) {
            str = obj.toString();
        } else {
            str = null;
        }
        android.graphics.Rect rect = this.mTempBounds;
        android.view.View view = this.mPreviewImage;
        if (this.mShowingPrimary) {
            textView = this.mPrimaryText;
            textView2 = this.mSecondaryText;
        } else {
            textView = this.mSecondaryText;
            textView2 = this.mPrimaryText;
        }
        textView2.lambda$setTextAsync$0(str);
        measurePreview(textView2, rect);
        applyLayout(textView2, rect);
        if (this.mPreviewAnimation != null) {
            this.mPreviewAnimation.cancel();
        }
        android.animation.Animator duration = animateAlpha(textView2, 1.0f).setDuration(50L);
        android.animation.Animator duration2 = animateAlpha(textView, 0.0f).setDuration(50L);
        duration2.addListener(this.mSwitchPrimaryListener);
        rect.left -= view.getPaddingLeft();
        rect.top -= view.getPaddingTop();
        rect.right += view.getPaddingRight();
        rect.bottom += view.getPaddingBottom();
        android.animation.Animator animateBounds = animateBounds(view, rect);
        animateBounds.setDuration(100L);
        this.mPreviewAnimation = new android.animation.AnimatorSet();
        android.animation.AnimatorSet.Builder with = this.mPreviewAnimation.play(duration2).with(duration);
        with.with(animateBounds);
        int width = (view.getWidth() - view.getPaddingLeft()) - view.getPaddingRight();
        int width2 = textView2.getWidth();
        if (width2 > width) {
            textView2.setScaleX(width / width2);
            with.with(animateScaleX(textView2, 1.0f).setDuration(100L));
        } else {
            textView2.setScaleX(1.0f);
        }
        int width3 = textView.getWidth();
        if (width3 > width2) {
            with.with(animateScaleX(textView, width2 / width3).setDuration(100L));
        }
        this.mPreviewAnimation.start();
        return !android.text.TextUtils.isEmpty(str);
    }

    private void setThumbPos(float f) {
        float f2 = (f * this.mThumbRange) + this.mThumbOffset;
        this.mThumbImage.setTranslationY(f2 - (this.mThumbImage.getHeight() / 2.0f));
        android.view.View view = this.mPreviewImage;
        float height = view.getHeight() / 2.0f;
        switch (this.mOverlayPosition) {
            case 1:
                break;
            case 2:
                f2 -= height;
                break;
            default:
                f2 = 0.0f;
                break;
        }
        android.graphics.Rect rect = this.mContainerRect;
        float constrain = android.util.MathUtils.constrain(f2, rect.top + height, rect.bottom - height) - height;
        view.setTranslationY(constrain);
        this.mPrimaryText.setTranslationY(constrain);
        this.mSecondaryText.setTranslationY(constrain);
    }

    private float getPosFromMotionEvent(float f) {
        if (this.mThumbRange <= 0.0f) {
            return 0.0f;
        }
        return android.util.MathUtils.constrain((f - this.mThumbOffset) / this.mThumbRange, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(int i, int i2, int i3) {
        float f;
        int i4;
        int height;
        int height2;
        int i5;
        android.widget.SectionIndexer sectionIndexer = this.mSectionIndexer;
        if (sectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        float f2 = 0.0f;
        if (i2 == 0 || i3 == 0) {
            return 0.0f;
        }
        if (!((sectionIndexer == null || this.mSections == null || this.mSections.length <= 0) ? false : true) || !this.mMatchDragPosition) {
            if (i2 == i3) {
                return 0.0f;
            }
            return i / (i3 - i2);
        }
        int i6 = i - this.mHeaderCount;
        if (i6 < 0) {
            return 0.0f;
        }
        int i7 = i3 - this.mHeaderCount;
        android.view.View childAt = this.mList.getChildAt(0);
        if (childAt == null || childAt.getHeight() == 0) {
            f = 0.0f;
        } else {
            f = (this.mList.getPaddingTop() - childAt.getTop()) / childAt.getHeight();
        }
        int sectionForPosition = sectionIndexer.getSectionForPosition(i6);
        int positionForSection = sectionIndexer.getPositionForSection(sectionForPosition);
        int length = this.mSections.length;
        if (sectionForPosition < length - 1) {
            int i8 = sectionForPosition + 1;
            if (i8 < length) {
                i5 = sectionIndexer.getPositionForSection(i8);
            } else {
                i5 = i7 - 1;
            }
            i4 = i5 - positionForSection;
        } else {
            i4 = i7 - positionForSection;
        }
        if (i4 != 0) {
            f2 = ((i6 + f) - positionForSection) / i4;
        }
        float f3 = (sectionForPosition + f2) / length;
        if (i6 > 0 && i6 + i2 == i7) {
            android.view.View childAt2 = this.mList.getChildAt(i2 - 1);
            int paddingBottom = this.mList.getPaddingBottom();
            if (this.mList.getClipToPadding()) {
                height = childAt2.getHeight();
                height2 = (this.mList.getHeight() - paddingBottom) - childAt2.getTop();
            } else {
                height = childAt2.getHeight() + paddingBottom;
                height2 = this.mList.getHeight() - childAt2.getTop();
            }
            if (height2 > 0 && height > 0) {
                return f3 + ((1.0f - f3) * (height2 / height));
            }
            return f3;
        }
        return f3;
    }

    private void cancelFling() {
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.mList.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void cancelPendingDrag() {
        this.mPendingDrag = -1L;
    }

    private void startPendingDrag() {
        this.mPendingDrag = android.os.SystemClock.uptimeMillis() + TAP_TIMEOUT;
    }

    private void beginDrag() {
        this.mPendingDrag = -1L;
        setState(2);
        if (this.mListAdapter == null && this.mList != null) {
            getSectionsFromIndexer();
        }
        if (this.mList != null) {
            this.mList.requestDisallowInterceptTouchEvent(true);
            this.mList.reportScrollStateChange(1);
        }
        cancelFling();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                if (isPointInside(motionEvent.getX(), motionEvent.getY())) {
                    if (!this.mList.isInScrollingContainer()) {
                        return true;
                    }
                    this.mInitialTouchY = motionEvent.getY();
                    startPendingDrag();
                }
                return false;
            case 1:
            case 3:
                cancelPendingDrag();
                return false;
            case 2:
                if (!isPointInside(motionEvent.getX(), motionEvent.getY())) {
                    cancelPendingDrag();
                } else if (this.mPendingDrag >= 0 && this.mPendingDrag <= android.os.SystemClock.uptimeMillis()) {
                    beginDrag();
                    scrollTo(getPosFromMotionEvent(this.mInitialTouchY));
                    return onTouchEvent(motionEvent);
                }
                return false;
            default:
                return false;
        }
    }

    public boolean onInterceptHoverEvent(android.view.MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if ((actionMasked == 9 || actionMasked == 7) && this.mState == 0 && isPointInside(motionEvent.getX(), motionEvent.getY())) {
            setState(1);
            postAutoHide();
        }
        return false;
    }

    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        if (this.mState == 2 || isPointInside(motionEvent.getX(), motionEvent.getY())) {
            return android.view.PointerIcon.getSystemIcon(this.mList.getContext(), 1000);
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                if (isPointInside(motionEvent.getX(), motionEvent.getY()) && !this.mList.isInScrollingContainer()) {
                    beginDrag();
                    return true;
                }
                return false;
            case 1:
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float posFromMotionEvent = getPosFromMotionEvent(motionEvent.getY());
                    setThumbPos(posFromMotionEvent);
                    scrollTo(posFromMotionEvent);
                }
                if (this.mState == 2) {
                    if (this.mList != null) {
                        this.mList.requestDisallowInterceptTouchEvent(false);
                        this.mList.reportScrollStateChange(0);
                    }
                    setState(1);
                    postAutoHide();
                    return true;
                }
                return false;
            case 2:
                if (this.mPendingDrag >= 0 && java.lang.Math.abs(motionEvent.getY() - this.mInitialTouchY) > this.mScaledTouchSlop) {
                    beginDrag();
                }
                if (this.mState == 2) {
                    float posFromMotionEvent2 = getPosFromMotionEvent(motionEvent.getY());
                    setThumbPos(posFromMotionEvent2);
                    if (this.mScrollCompleted) {
                        scrollTo(posFromMotionEvent2);
                    }
                    return true;
                }
                return false;
            case 3:
                cancelPendingDrag();
                return false;
            default:
                return false;
        }
    }

    private boolean isPointInside(float f, float f2) {
        return isPointInsideX(f) && (this.mTrackDrawable != null || isPointInsideY(f2));
    }

    private boolean isPointInsideX(float f) {
        float translationX = this.mThumbImage.getTranslationX();
        float right = this.mMinimumTouchTarget - ((this.mThumbImage.getRight() + translationX) - (this.mThumbImage.getLeft() + translationX));
        if (right <= 0.0f) {
            right = 0.0f;
        }
        return this.mLayoutFromRight ? f >= ((float) this.mThumbImage.getLeft()) - right : f <= ((float) this.mThumbImage.getRight()) + right;
    }

    private boolean isPointInsideY(float f) {
        float translationY = this.mThumbImage.getTranslationY();
        float top = this.mThumbImage.getTop() + translationY;
        float bottom = this.mThumbImage.getBottom() + translationY;
        float f2 = this.mMinimumTouchTarget - (bottom - top);
        float f3 = f2 > 0.0f ? f2 / 2.0f : 0.0f;
        return f >= top - f3 && f <= bottom + f3;
    }

    private static android.animation.Animator groupAnimatorOfFloat(android.util.Property<android.view.View, java.lang.Float> property, float f, android.view.View... viewArr) {
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        android.animation.AnimatorSet.Builder builder = null;
        for (int length = viewArr.length - 1; length >= 0; length--) {
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(viewArr[length], property, f);
            if (builder == null) {
                builder = animatorSet.play(ofFloat);
            } else {
                builder.with(ofFloat);
            }
        }
        return animatorSet;
    }

    private static android.animation.Animator animateScaleX(android.view.View view, float f) {
        return android.animation.ObjectAnimator.ofFloat(view, android.view.View.SCALE_X, f);
    }

    private static android.animation.Animator animateAlpha(android.view.View view, float f) {
        return android.animation.ObjectAnimator.ofFloat(view, android.view.View.ALPHA, f);
    }

    private static android.animation.Animator animateBounds(android.view.View view, android.graphics.Rect rect) {
        return android.animation.ObjectAnimator.ofPropertyValuesHolder(view, android.animation.PropertyValuesHolder.ofInt(LEFT, rect.left), android.animation.PropertyValuesHolder.ofInt(TOP, rect.top), android.animation.PropertyValuesHolder.ofInt(RIGHT, rect.right), android.animation.PropertyValuesHolder.ofInt(BOTTOM, rect.bottom));
    }
}
