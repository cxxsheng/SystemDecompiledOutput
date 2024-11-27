package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class Gallery extends android.widget.AbsSpinner implements android.view.GestureDetector.OnGestureListener {
    private static final int SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT = 250;
    private static final java.lang.String TAG = "Gallery";
    private static final boolean localLOGV = false;
    private int mAnimationDuration;
    private android.widget.AdapterView.AdapterContextMenuInfo mContextMenuInfo;
    private java.lang.Runnable mDisableSuppressSelectionChangedRunnable;
    private int mDownTouchPosition;
    private android.view.View mDownTouchView;
    private android.widget.Gallery.FlingRunnable mFlingRunnable;
    private android.view.GestureDetector mGestureDetector;
    private int mGravity;
    private boolean mIsFirstScroll;
    private boolean mIsRtl;
    private int mLeftMost;
    private boolean mReceivedInvokeKeyDown;
    private int mRightMost;
    private int mSelectedCenterOffset;
    private android.view.View mSelectedChild;
    private boolean mShouldCallbackDuringFling;
    private boolean mShouldCallbackOnUnselectedItemClick;
    private boolean mShouldStopFling;
    private int mSpacing;
    private boolean mSuppressSelectionChanged;
    private float mUnselectedAlpha;

    public Gallery(android.content.Context context) {
        this(context, null);
    }

    public Gallery(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842864);
    }

    public Gallery(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Gallery(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSpacing = 0;
        this.mAnimationDuration = 400;
        this.mFlingRunnable = new android.widget.Gallery.FlingRunnable();
        this.mDisableSuppressSelectionChangedRunnable = new java.lang.Runnable() { // from class: android.widget.Gallery.1
            @Override // java.lang.Runnable
            public void run() {
                android.widget.Gallery.this.mSuppressSelectionChanged = false;
                android.widget.Gallery.this.selectionChanged();
            }
        };
        this.mShouldCallbackDuringFling = true;
        this.mShouldCallbackOnUnselectedItemClick = true;
        this.mIsRtl = true;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Gallery, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.Gallery, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(0, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        int i4 = obtainStyledAttributes.getInt(1, -1);
        if (i4 > 0) {
            setAnimationDuration(i4);
        }
        setSpacing(obtainStyledAttributes.getDimensionPixelOffset(2, 0));
        setUnselectedAlpha(obtainStyledAttributes.getFloat(3, 0.5f));
        obtainStyledAttributes.recycle();
        this.mGroupFlags |= 1024;
        this.mGroupFlags |= 2048;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new android.view.GestureDetector(getContext(), this);
            this.mGestureDetector.setIsLongpressEnabled(true);
        }
    }

    public void setCallbackDuringFling(boolean z) {
        this.mShouldCallbackDuringFling = z;
    }

    public void setCallbackOnUnselectedItemClick(boolean z) {
        this.mShouldCallbackOnUnselectedItemClick = z;
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public void setSpacing(int i) {
        this.mSpacing = i;
    }

    public void setUnselectedAlpha(float f) {
        this.mUnselectedAlpha = f;
    }

    @Override // android.view.ViewGroup
    protected boolean getChildStaticTransformation(android.view.View view, android.view.animation.Transformation transformation) {
        transformation.clear();
        transformation.setAlpha(view == this.mSelectedChild ? 1.0f : this.mUnselectedAlpha);
        return true;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollExtent() {
        return 1;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollOffset() {
        return this.mSelectedPosition;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        return this.mItemCount;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.Gallery.LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new android.widget.Gallery.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.Gallery.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.AbsSpinner, android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.Gallery.LayoutParams(-2, -2);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInLayout = true;
        layout(0, false);
        this.mInLayout = false;
    }

    @Override // android.widget.AbsSpinner
    int getChildHeight(android.view.View view) {
        return view.getMeasuredHeight();
    }

    void trackMotionScroll(int i) {
        if (getChildCount() == 0) {
            return;
        }
        boolean z = i < 0;
        int limitedMotionScrollAmount = getLimitedMotionScrollAmount(z, i);
        if (limitedMotionScrollAmount != i) {
            this.mFlingRunnable.endFling(false);
            onFinishedMovement();
        }
        offsetChildrenLeftAndRight(limitedMotionScrollAmount);
        detachOffScreenChildren(z);
        if (z) {
            fillToGalleryRight();
        } else {
            fillToGalleryLeft();
        }
        this.mRecycler.clear();
        setSelectionToCenterChild();
        android.view.View view = this.mSelectedChild;
        if (view != null) {
            this.mSelectedCenterOffset = (view.getLeft() + (view.getWidth() / 2)) - (getWidth() / 2);
        }
        onScrollChanged(0, 0, 0, 0);
        invalidate();
    }

    int getLimitedMotionScrollAmount(boolean z, int i) {
        android.view.View childAt = getChildAt((z != this.mIsRtl ? this.mItemCount - 1 : 0) - this.mFirstPosition);
        if (childAt == null) {
            return i;
        }
        int centerOfView = getCenterOfView(childAt);
        int centerOfGallery = getCenterOfGallery();
        if (z) {
            if (centerOfView <= centerOfGallery) {
                return 0;
            }
        } else if (centerOfView >= centerOfGallery) {
            return 0;
        }
        int i2 = centerOfGallery - centerOfView;
        if (z) {
            return java.lang.Math.max(i2, i);
        }
        return java.lang.Math.min(i2, i);
    }

    private void offsetChildrenLeftAndRight(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).offsetLeftAndRight(i);
        }
    }

    private int getCenterOfGallery() {
        return (((getWidth() - this.mPaddingLeft) - this.mPaddingRight) / 2) + this.mPaddingLeft;
    }

    private static int getCenterOfView(android.view.View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    private void detachOffScreenChildren(boolean z) {
        int i;
        int childCount = getChildCount();
        int i2 = this.mFirstPosition;
        int i3 = 0;
        if (z) {
            int i4 = this.mPaddingLeft;
            int i5 = 0;
            i = 0;
            int i6 = 0;
            while (i5 < childCount) {
                int i7 = this.mIsRtl ? (childCount - 1) - i5 : i5;
                android.view.View childAt = getChildAt(i7);
                if (childAt.getRight() >= i4) {
                    break;
                }
                i++;
                this.mRecycler.put(i2 + i7, childAt);
                i5++;
                i6 = i7;
            }
            if (this.mIsRtl) {
                i3 = i6;
            }
        } else {
            int width = getWidth() - this.mPaddingRight;
            int i8 = childCount - 1;
            int i9 = i8;
            i = 0;
            int i10 = 0;
            while (i9 >= 0) {
                int i11 = this.mIsRtl ? i8 - i9 : i9;
                android.view.View childAt2 = getChildAt(i11);
                if (childAt2.getLeft() <= width) {
                    break;
                }
                i++;
                this.mRecycler.put(i2 + i11, childAt2);
                i9--;
                i10 = i11;
            }
            if (!this.mIsRtl) {
                i3 = i10;
            }
        }
        detachViewsFromParent(i3, i);
        if (z != this.mIsRtl) {
            this.mFirstPosition += i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollIntoSlots() {
        if (getChildCount() == 0 || this.mSelectedChild == null) {
            return;
        }
        int centerOfGallery = getCenterOfGallery() - getCenterOfView(this.mSelectedChild);
        if (centerOfGallery != 0) {
            this.mFlingRunnable.startUsingDistance(centerOfGallery);
        } else {
            onFinishedMovement();
        }
    }

    private void onFinishedMovement() {
        if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
            super.selectionChanged();
        }
        this.mSelectedCenterOffset = 0;
        invalidate();
    }

    @Override // android.widget.AdapterView
    void selectionChanged() {
        if (!this.mSuppressSelectionChanged) {
            super.selectionChanged();
        }
    }

    private void setSelectionToCenterChild() {
        android.view.View view = this.mSelectedChild;
        if (this.mSelectedChild == null) {
            return;
        }
        int centerOfGallery = getCenterOfGallery();
        if (view.getLeft() <= centerOfGallery && view.getRight() >= centerOfGallery) {
            return;
        }
        int childCount = getChildCount() - 1;
        int i = Integer.MAX_VALUE;
        int i2 = 0;
        while (true) {
            if (childCount < 0) {
                childCount = i2;
                break;
            }
            android.view.View childAt = getChildAt(childCount);
            if (childAt.getLeft() <= centerOfGallery && childAt.getRight() >= centerOfGallery) {
                break;
            }
            int min = java.lang.Math.min(java.lang.Math.abs(childAt.getLeft() - centerOfGallery), java.lang.Math.abs(childAt.getRight() - centerOfGallery));
            if (min < i) {
                i2 = childCount;
                i = min;
            }
            childCount--;
        }
        int i3 = this.mFirstPosition + childCount;
        if (i3 != this.mSelectedPosition) {
            setSelectedPositionInt(i3);
            setNextSelectedPositionInt(i3);
            checkSelectionChanged();
        }
    }

    @Override // android.widget.AbsSpinner
    void layout(int i, boolean z) {
        this.mIsRtl = isLayoutRtl();
        int i2 = this.mSpinnerPadding.left;
        int i3 = ((this.mRight - this.mLeft) - this.mSpinnerPadding.left) - this.mSpinnerPadding.right;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        if (this.mItemCount == 0) {
            resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            setSelectedPositionInt(this.mNextSelectedPosition);
        }
        recycleAllViews();
        detachAllViewsFromParent();
        this.mRightMost = 0;
        this.mLeftMost = 0;
        this.mFirstPosition = this.mSelectedPosition;
        android.view.View makeAndAddView = makeAndAddView(this.mSelectedPosition, 0, 0, true);
        makeAndAddView.offsetLeftAndRight(((i2 + (i3 / 2)) - (makeAndAddView.getWidth() / 2)) + this.mSelectedCenterOffset);
        fillToGalleryRight();
        fillToGalleryLeft();
        this.mRecycler.clear();
        invalidate();
        checkSelectionChanged();
        this.mDataChanged = false;
        this.mNeedSync = false;
        setNextSelectedPositionInt(this.mSelectedPosition);
        updateSelectedItemMetadata();
    }

    private void fillToGalleryLeft() {
        if (this.mIsRtl) {
            fillToGalleryLeftRtl();
        } else {
            fillToGalleryLeftLtr();
        }
    }

    private void fillToGalleryLeftRtl() {
        int i;
        int i2;
        int i3 = this.mSpacing;
        int i4 = this.mPaddingLeft;
        int childCount = getChildCount();
        int i5 = this.mItemCount;
        android.view.View childAt = getChildAt(childCount - 1);
        if (childAt != null) {
            i = this.mFirstPosition + childCount;
            i2 = childAt.getLeft() - i3;
        } else {
            i = this.mItemCount - 1;
            this.mFirstPosition = i;
            i2 = (this.mRight - this.mLeft) - this.mPaddingRight;
            this.mShouldStopFling = true;
        }
        while (i2 > i4 && i < this.mItemCount) {
            i2 = makeAndAddView(i, i - this.mSelectedPosition, i2, false).getLeft() - i3;
            i++;
        }
    }

    private void fillToGalleryLeftLtr() {
        int i;
        int i2;
        int i3 = this.mSpacing;
        int i4 = this.mPaddingLeft;
        android.view.View childAt = getChildAt(0);
        if (childAt != null) {
            i2 = this.mFirstPosition - 1;
            i = childAt.getLeft() - i3;
        } else {
            i = (this.mRight - this.mLeft) - this.mPaddingRight;
            this.mShouldStopFling = true;
            i2 = 0;
        }
        while (i > i4 && i2 >= 0) {
            android.view.View makeAndAddView = makeAndAddView(i2, i2 - this.mSelectedPosition, i, false);
            this.mFirstPosition = i2;
            i = makeAndAddView.getLeft() - i3;
            i2--;
        }
    }

    private void fillToGalleryRight() {
        if (this.mIsRtl) {
            fillToGalleryRightRtl();
        } else {
            fillToGalleryRightLtr();
        }
    }

    private void fillToGalleryRightRtl() {
        int i;
        int i2 = this.mSpacing;
        int i3 = (this.mRight - this.mLeft) - this.mPaddingRight;
        int i4 = 0;
        android.view.View childAt = getChildAt(0);
        if (childAt != null) {
            i4 = this.mFirstPosition - 1;
            i = childAt.getRight() + i2;
        } else {
            i = this.mPaddingLeft;
            this.mShouldStopFling = true;
        }
        while (i < i3 && i4 >= 0) {
            android.view.View makeAndAddView = makeAndAddView(i4, i4 - this.mSelectedPosition, i, true);
            this.mFirstPosition = i4;
            i = makeAndAddView.getRight() + i2;
            i4--;
        }
    }

    private void fillToGalleryRightLtr() {
        int i;
        int i2;
        int i3 = this.mSpacing;
        int i4 = (this.mRight - this.mLeft) - this.mPaddingRight;
        int childCount = getChildCount();
        int i5 = this.mItemCount;
        android.view.View childAt = getChildAt(childCount - 1);
        if (childAt != null) {
            i = this.mFirstPosition + childCount;
            i2 = childAt.getRight() + i3;
        } else {
            i = this.mItemCount - 1;
            this.mFirstPosition = i;
            i2 = this.mPaddingLeft;
            this.mShouldStopFling = true;
        }
        while (i2 < i4 && i < i5) {
            i2 = makeAndAddView(i, i - this.mSelectedPosition, i2, true).getRight() + i3;
            i++;
        }
    }

    private android.view.View makeAndAddView(int i, int i2, int i3, boolean z) {
        android.view.View view;
        if (!this.mDataChanged && (view = this.mRecycler.get(i)) != null) {
            int left = view.getLeft();
            this.mRightMost = java.lang.Math.max(this.mRightMost, view.getMeasuredWidth() + left);
            this.mLeftMost = java.lang.Math.min(this.mLeftMost, left);
            setUpChild(view, i2, i3, z);
            return view;
        }
        android.view.View view2 = this.mAdapter.getView(i, null, this);
        setUpChild(view2, i2, i3, z);
        return view2;
    }

    private void setUpChild(android.view.View view, int i, int i2, boolean z) {
        int i3;
        android.widget.Gallery.LayoutParams layoutParams = (android.widget.Gallery.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (android.widget.Gallery.LayoutParams) generateDefaultLayoutParams();
        }
        addViewInLayout(view, z != this.mIsRtl ? -1 : 0, layoutParams, true);
        view.setSelected(i == 0);
        view.measure(android.view.ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, layoutParams.width), android.view.ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, layoutParams.height));
        int calculateTop = calculateTop(view, true);
        int measuredHeight = view.getMeasuredHeight() + calculateTop;
        int measuredWidth = view.getMeasuredWidth();
        if (z) {
            i3 = measuredWidth + i2;
        } else {
            int i4 = i2 - measuredWidth;
            i3 = i2;
            i2 = i4;
        }
        view.layout(i2, calculateTop, i3, measuredHeight);
    }

    private int calculateTop(android.view.View view, boolean z) {
        int measuredHeight = z ? getMeasuredHeight() : getHeight();
        int measuredHeight2 = z ? view.getMeasuredHeight() : view.getHeight();
        switch (this.mGravity) {
            case 16:
                return ((((measuredHeight - this.mSpinnerPadding.bottom) - this.mSpinnerPadding.top) - measuredHeight2) / 2) + this.mSpinnerPadding.top;
            case 48:
                return this.mSpinnerPadding.top;
            case 80:
                return (measuredHeight - this.mSpinnerPadding.bottom) - measuredHeight2;
            default:
                return 0;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        if (action == 1) {
            onUp();
        } else if (action == 3) {
            onCancel();
        }
        return onTouchEvent;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(android.view.MotionEvent motionEvent) {
        if (this.mDownTouchPosition >= 0) {
            scrollToChild(this.mDownTouchPosition - this.mFirstPosition);
            if (this.mShouldCallbackOnUnselectedItemClick || this.mDownTouchPosition == this.mSelectedPosition) {
                performItemClick(this.mDownTouchView, this.mDownTouchPosition, this.mAdapter.getItemId(this.mDownTouchPosition));
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
        if (!this.mShouldCallbackDuringFling) {
            removeCallbacks(this.mDisableSuppressSelectionChangedRunnable);
            if (!this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = true;
            }
        }
        this.mFlingRunnable.startUsingVelocity((int) (-f));
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
        this.mParent.requestDisallowInterceptTouchEvent(true);
        if (!this.mShouldCallbackDuringFling) {
            if (this.mIsFirstScroll) {
                if (!this.mSuppressSelectionChanged) {
                    this.mSuppressSelectionChanged = true;
                }
                postDelayed(this.mDisableSuppressSelectionChangedRunnable, 250L);
            }
        } else if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
        }
        trackMotionScroll(((int) f) * (-1));
        this.mIsFirstScroll = false;
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(android.view.MotionEvent motionEvent) {
        this.mFlingRunnable.stop(false);
        this.mDownTouchPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        if (this.mDownTouchPosition >= 0) {
            this.mDownTouchView = getChildAt(this.mDownTouchPosition - this.mFirstPosition);
            this.mDownTouchView.setPressed(true);
        }
        this.mIsFirstScroll = true;
        return true;
    }

    void onUp() {
        if (this.mFlingRunnable.mScroller.isFinished()) {
            scrollIntoSlots();
        }
        dispatchUnpress();
    }

    void onCancel() {
        onUp();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(android.view.MotionEvent motionEvent) {
        if (this.mDownTouchPosition < 0) {
            return;
        }
        performHapticFeedback(0);
        dispatchLongPress(this.mDownTouchView, this.mDownTouchPosition, getItemIdAtPosition(this.mDownTouchPosition), motionEvent.getX(), motionEvent.getY(), true);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(android.view.MotionEvent motionEvent) {
    }

    private void dispatchPress(android.view.View view) {
        if (view != null) {
            view.setPressed(true);
        }
        setPressed(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUnpress() {
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (childCount >= 0) {
                getChildAt(childCount).setPressed(false);
            } else {
                setPressed(false);
                return;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchSetSelected(boolean z) {
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSetPressed(boolean z) {
        if (this.mSelectedChild != null) {
            this.mSelectedChild.setPressed(z);
        }
    }

    @Override // android.view.View
    protected android.view.ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view) {
        if (isShowingContextMenuWithCoords()) {
            return false;
        }
        return showContextMenuForChildInternal(view, 0.0f, 0.0f, false);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view, float f, float f2) {
        return showContextMenuForChildInternal(view, f, f2, true);
    }

    private boolean showContextMenuForChildInternal(android.view.View view, float f, float f2, boolean z) {
        int positionForView = getPositionForView(view);
        if (positionForView < 0) {
            return false;
        }
        return dispatchLongPress(view, positionForView, this.mAdapter.getItemId(positionForView), f, f2, z);
    }

    @Override // android.view.View
    public boolean showContextMenu() {
        return showContextMenuInternal(0.0f, 0.0f, false);
    }

    @Override // android.view.View
    public boolean showContextMenu(float f, float f2) {
        return showContextMenuInternal(f, f2, true);
    }

    private boolean showContextMenuInternal(float f, float f2, boolean z) {
        if (isPressed() && this.mSelectedPosition >= 0) {
            return dispatchLongPress(getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mSelectedRowId, f, f2, z);
        }
        return false;
    }

    private boolean dispatchLongPress(android.view.View view, int i, long j, float f, float f2, boolean z) {
        boolean z2;
        if (this.mOnItemLongClickListener == null) {
            z2 = false;
        } else {
            z2 = this.mOnItemLongClickListener.onItemLongClick(this, this.mDownTouchView, this.mDownTouchPosition, j);
        }
        if (!z2) {
            this.mContextMenuInfo = new android.widget.AdapterView.AdapterContextMenuInfo(view, i, j);
            if (z) {
                z2 = super.showContextMenuForChild(view, f, f2);
            } else {
                z2 = super.showContextMenuForChild(this);
            }
        }
        if (z2) {
            performHapticFeedback(0);
        }
        return z2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        return keyEvent.dispatch(this, null, null);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        switch (i) {
            case 21:
                if (moveDirection(-1)) {
                    playSoundEffect(1);
                    return true;
                }
                break;
            case 22:
                if (moveDirection(1)) {
                    playSoundEffect(3);
                    return true;
                }
                break;
            case 23:
            case 66:
                this.mReceivedInvokeKeyDown = true;
                break;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (android.view.KeyEvent.isConfirmKey(i)) {
            if (this.mReceivedInvokeKeyDown && this.mItemCount > 0) {
                dispatchPress(this.mSelectedChild);
                postDelayed(new java.lang.Runnable() { // from class: android.widget.Gallery.2
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.Gallery.this.dispatchUnpress();
                    }
                }, android.view.ViewConfiguration.getPressedStateDuration());
                performItemClick(getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mAdapter.getItemId(this.mSelectedPosition));
            }
            this.mReceivedInvokeKeyDown = false;
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    boolean moveDirection(int i) {
        if (isLayoutRtl()) {
            i = -i;
        }
        int i2 = this.mSelectedPosition + i;
        if (this.mItemCount > 0 && i2 >= 0 && i2 < this.mItemCount) {
            scrollToChild(i2 - this.mFirstPosition);
            return true;
        }
        return false;
    }

    private boolean scrollToChild(int i) {
        android.view.View childAt = getChildAt(i);
        if (childAt != null) {
            this.mFlingRunnable.startUsingDistance(getCenterOfGallery() - getCenterOfView(childAt));
            return true;
        }
        return false;
    }

    @Override // android.widget.AdapterView
    void setSelectedPositionInt(int i) {
        super.setSelectedPositionInt(i);
        updateSelectedItemMetadata();
    }

    private void updateSelectedItemMetadata() {
        android.view.View view = this.mSelectedChild;
        android.view.View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
        this.mSelectedChild = childAt;
        if (childAt == null) {
            return;
        }
        childAt.setSelected(true);
        childAt.setFocusable(true);
        if (hasFocus()) {
            childAt.requestFocus();
        }
        if (view != null && view != childAt) {
            view.setSelected(false);
            view.setFocusable(false);
        }
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        int i3 = this.mSelectedPosition - this.mFirstPosition;
        if (i3 < 0) {
            return i2;
        }
        if (i2 == i - 1) {
            return i3;
        }
        if (i2 >= i3) {
            return i2 + 1;
        }
        return i2;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z && this.mSelectedChild != null) {
            this.mSelectedChild.requestFocus(i);
            this.mSelectedChild.setSelected(true);
        }
    }

    @Override // android.widget.AbsSpinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.Gallery.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setScrollable(this.mItemCount > 1);
        if (isEnabled()) {
            if (this.mItemCount > 0 && this.mSelectedPosition < this.mItemCount - 1) {
                accessibilityNodeInfo.addAction(4096);
            }
            if (isEnabled() && this.mItemCount > 0 && this.mSelectedPosition > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        switch (i) {
            case 4096:
                if (!isEnabled() || this.mItemCount <= 0 || this.mSelectedPosition >= this.mItemCount - 1) {
                    return false;
                }
                return scrollToChild((this.mSelectedPosition - this.mFirstPosition) + 1);
            case 8192:
                if (!isEnabled() || this.mItemCount <= 0 || this.mSelectedPosition <= 0) {
                    return false;
                }
                return scrollToChild((this.mSelectedPosition - this.mFirstPosition) - 1);
            default:
                return false;
        }
    }

    private class FlingRunnable implements java.lang.Runnable {
        private int mLastFlingX;
        private android.widget.Scroller mScroller;

        public FlingRunnable() {
            this.mScroller = new android.widget.Scroller(android.widget.Gallery.this.getContext());
        }

        private void startCommon() {
            android.widget.Gallery.this.removeCallbacks(this);
        }

        public void startUsingVelocity(int i) {
            if (i == 0) {
                return;
            }
            startCommon();
            int i2 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingX = i2;
            this.mScroller.fling(i2, 0, i, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            android.widget.Gallery.this.post(this);
        }

        public void startUsingDistance(int i) {
            if (i == 0) {
                return;
            }
            startCommon();
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, -i, 0, android.widget.Gallery.this.mAnimationDuration);
            android.widget.Gallery.this.post(this);
        }

        public void stop(boolean z) {
            android.widget.Gallery.this.removeCallbacks(this);
            endFling(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void endFling(boolean z) {
            this.mScroller.forceFinished(true);
            if (z) {
                android.widget.Gallery.this.scrollIntoSlots();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int max;
            if (android.widget.Gallery.this.mItemCount == 0) {
                endFling(true);
                return;
            }
            android.widget.Gallery.this.mShouldStopFling = false;
            android.widget.Scroller scroller = this.mScroller;
            boolean computeScrollOffset = scroller.computeScrollOffset();
            int currX = scroller.getCurrX();
            int i = this.mLastFlingX - currX;
            if (i > 0) {
                android.widget.Gallery.this.mDownTouchPosition = android.widget.Gallery.this.mIsRtl ? (android.widget.Gallery.this.mFirstPosition + android.widget.Gallery.this.getChildCount()) - 1 : android.widget.Gallery.this.mFirstPosition;
                max = java.lang.Math.min(((android.widget.Gallery.this.getWidth() - android.widget.Gallery.this.mPaddingLeft) - android.widget.Gallery.this.mPaddingRight) - 1, i);
            } else {
                android.widget.Gallery.this.getChildCount();
                android.widget.Gallery.this.mDownTouchPosition = android.widget.Gallery.this.mIsRtl ? android.widget.Gallery.this.mFirstPosition : (android.widget.Gallery.this.mFirstPosition + android.widget.Gallery.this.getChildCount()) - 1;
                max = java.lang.Math.max(-(((android.widget.Gallery.this.getWidth() - android.widget.Gallery.this.mPaddingRight) - android.widget.Gallery.this.mPaddingLeft) - 1), i);
            }
            android.widget.Gallery.this.trackMotionScroll(max);
            if (computeScrollOffset && !android.widget.Gallery.this.mShouldStopFling) {
                this.mLastFlingX = currX;
                android.widget.Gallery.this.post(this);
            } else {
                endFling(true);
            }
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }
}
