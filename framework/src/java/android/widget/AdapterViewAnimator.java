package android.widget;

/* loaded from: classes4.dex */
public abstract class AdapterViewAnimator extends android.widget.AdapterView<android.widget.Adapter> implements android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback, android.widget.Advanceable {
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final java.lang.String TAG = "RemoteViewAnimator";
    static final int TOUCH_MODE_DOWN_IN_CURRENT_VIEW = 1;
    static final int TOUCH_MODE_HANDLED = 2;
    static final int TOUCH_MODE_NONE = 0;
    int mActiveOffset;
    android.widget.Adapter mAdapter;
    boolean mAnimateFirstTime;
    int mCurrentWindowEnd;
    int mCurrentWindowStart;
    int mCurrentWindowStartUnbounded;
    android.widget.AdapterView<android.widget.Adapter>.AdapterDataSetObserver mDataSetObserver;
    boolean mDeferNotifyDataSetChanged;
    boolean mFirstTime;
    android.animation.ObjectAnimator mInAnimation;
    boolean mLoopViews;
    int mMaxNumActiveViews;
    android.animation.ObjectAnimator mOutAnimation;
    private java.lang.Runnable mPendingCheckForTap;
    java.util.ArrayList<java.lang.Integer> mPreviousViews;
    int mReferenceChildHeight;
    int mReferenceChildWidth;
    android.widget.RemoteViewsAdapter mRemoteViewsAdapter;
    private int mRestoreWhichChild;
    private int mTouchMode;
    java.util.HashMap<java.lang.Integer, android.widget.AdapterViewAnimator.ViewAndMetaData> mViewsMap;
    int mWhichChild;

    public AdapterViewAnimator(android.content.Context context) {
        this(context, null);
    }

    public AdapterViewAnimator(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterViewAnimator(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AdapterViewAnimator(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWhichChild = 0;
        this.mRestoreWhichChild = -1;
        this.mAnimateFirstTime = true;
        this.mActiveOffset = 0;
        this.mMaxNumActiveViews = 1;
        this.mViewsMap = new java.util.HashMap<>();
        this.mCurrentWindowStart = 0;
        this.mCurrentWindowEnd = -1;
        this.mCurrentWindowStartUnbounded = 0;
        this.mDeferNotifyDataSetChanged = false;
        this.mFirstTime = true;
        this.mLoopViews = true;
        this.mReferenceChildWidth = -1;
        this.mReferenceChildHeight = -1;
        this.mTouchMode = 0;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AdapterViewAnimator, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.AdapterViewAnimator, attributeSet, obtainStyledAttributes, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInAnimation(context, resourceId);
        } else {
            setInAnimation(getDefaultInAnimation());
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 > 0) {
            setOutAnimation(context, resourceId2);
        } else {
            setOutAnimation(getDefaultOutAnimation());
        }
        setAnimateFirstView(obtainStyledAttributes.getBoolean(2, true));
        this.mLoopViews = obtainStyledAttributes.getBoolean(3, false);
        obtainStyledAttributes.recycle();
        initViewAnimator();
    }

    private void initViewAnimator() {
        this.mPreviousViews = new java.util.ArrayList<>();
    }

    class ViewAndMetaData {
        int adapterPosition;
        long itemId;
        int relativeIndex;
        android.view.View view;

        ViewAndMetaData(android.view.View view, int i, int i2, long j) {
            this.view = view;
            this.relativeIndex = i;
            this.adapterPosition = i2;
            this.itemId = j;
        }
    }

    void configureViewAnimator(int i, int i2) {
        this.mMaxNumActiveViews = i;
        this.mActiveOffset = i2;
        this.mPreviousViews.clear();
        this.mViewsMap.clear();
        removeAllViewsInLayout();
        this.mCurrentWindowStart = 0;
        this.mCurrentWindowEnd = -1;
    }

    void transformViewForTransition(int i, int i2, android.view.View view, boolean z) {
        if (i == -1) {
            this.mInAnimation.setTarget(view);
            this.mInAnimation.start();
        } else if (i2 == -1) {
            this.mOutAnimation.setTarget(view);
            this.mOutAnimation.start();
        }
    }

    android.animation.ObjectAnimator getDefaultInAnimation() {
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat((java.lang.Object) null, "alpha", 0.0f, 1.0f);
        ofFloat.setDuration(200L);
        return ofFloat;
    }

    android.animation.ObjectAnimator getDefaultOutAnimation() {
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat((java.lang.Object) null, "alpha", 1.0f, 0.0f);
        ofFloat.setDuration(200L);
        return ofFloat;
    }

    @android.view.RemotableViewMethod
    public void setDisplayedChild(int i) {
        setDisplayedChild(i, true);
    }

    private void setDisplayedChild(int i, boolean z) {
        if (this.mAdapter != null) {
            this.mWhichChild = i;
            if (i >= getWindowSize()) {
                this.mWhichChild = this.mLoopViews ? 0 : getWindowSize() - 1;
            } else if (i < 0) {
                this.mWhichChild = this.mLoopViews ? getWindowSize() - 1 : 0;
            }
            boolean z2 = getFocusedChild() != null;
            showOnly(this.mWhichChild, z);
            if (z2) {
                requestFocus(2);
            }
        }
    }

    void applyTransformForChildAtIndex(android.view.View view, int i) {
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    public void showNext() {
        setDisplayedChild(this.mWhichChild + 1);
    }

    public void showPrevious() {
        setDisplayedChild(this.mWhichChild - 1);
    }

    int modulo(int i, int i2) {
        if (i2 > 0) {
            return ((i % i2) + i2) % i2;
        }
        return 0;
    }

    android.view.View getViewAtRelativeIndex(int i) {
        if (i >= 0 && i <= getNumActiveViews() - 1 && this.mAdapter != null) {
            int modulo = modulo(this.mCurrentWindowStartUnbounded + i, getWindowSize());
            if (this.mViewsMap.get(java.lang.Integer.valueOf(modulo)) != null) {
                return this.mViewsMap.get(java.lang.Integer.valueOf(modulo)).view;
            }
            return null;
        }
        return null;
    }

    int getNumActiveViews() {
        if (this.mAdapter != null) {
            return java.lang.Math.min(getCount() + 1, this.mMaxNumActiveViews);
        }
        return this.mMaxNumActiveViews;
    }

    int getWindowSize() {
        if (this.mAdapter != null) {
            int count = getCount();
            if (count <= getNumActiveViews() && this.mLoopViews) {
                return count * this.mMaxNumActiveViews;
            }
            return count;
        }
        return 0;
    }

    private android.widget.AdapterViewAnimator.ViewAndMetaData getMetaDataForChild(android.view.View view) {
        for (android.widget.AdapterViewAnimator.ViewAndMetaData viewAndMetaData : this.mViewsMap.values()) {
            if (viewAndMetaData.view == view) {
                return viewAndMetaData;
            }
        }
        return null;
    }

    android.view.ViewGroup.LayoutParams createOrReuseLayoutParams(android.view.View view) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            return layoutParams;
        }
        return new android.view.ViewGroup.LayoutParams(0, 0);
    }

    void refreshChildren() {
        int count = this.mAdapter == null ? 0 : getCount();
        for (int i = this.mCurrentWindowStart; i <= this.mCurrentWindowEnd; i++) {
            int modulo = modulo(i, getWindowSize());
            android.view.View view = null;
            if (i < count) {
                view = this.mAdapter.getView(modulo(i, count), null, this);
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
            }
            if (this.mViewsMap.containsKey(java.lang.Integer.valueOf(modulo))) {
                android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) this.mViewsMap.get(java.lang.Integer.valueOf(modulo)).view;
                frameLayout.removeAllViewsInLayout();
                if (view != null) {
                    frameLayout.addView(view);
                }
            }
        }
    }

    android.widget.FrameLayout getFrameForChild() {
        return new android.widget.FrameLayout(this.mContext);
    }

    void showOnly(int i, boolean z) {
        int count;
        int i2;
        int i3;
        int i4;
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z3;
        if (this.mAdapter == null || (count = getCount()) == 0) {
            return;
        }
        boolean z4 = false;
        int i10 = 0;
        while (true) {
            i2 = -1;
            if (i10 >= this.mPreviousViews.size()) {
                break;
            }
            android.view.View view = this.mViewsMap.get(this.mPreviousViews.get(i10)).view;
            this.mViewsMap.remove(this.mPreviousViews.get(i10));
            view.clearAnimation();
            if (view instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) view).removeAllViewsInLayout();
            }
            applyTransformForChildAtIndex(view, -1);
            removeViewInLayout(view);
            i10++;
        }
        this.mPreviousViews.clear();
        int i11 = i - this.mActiveOffset;
        boolean z5 = true;
        int numActiveViews = (getNumActiveViews() + i11) - 1;
        int max = java.lang.Math.max(0, i11);
        int min = java.lang.Math.min(count - 1, numActiveViews);
        if (!this.mLoopViews) {
            i3 = max;
            i4 = min;
        } else {
            i4 = numActiveViews;
            i3 = i11;
        }
        int modulo = modulo(i3, getWindowSize());
        int modulo2 = modulo(i4, getWindowSize());
        if (modulo <= modulo2) {
            z2 = false;
        } else {
            z2 = true;
        }
        for (java.lang.Integer num : this.mViewsMap.keySet()) {
            if (!z2 && (num.intValue() < modulo || num.intValue() > modulo2)) {
                z3 = true;
            } else if (z2 && num.intValue() > modulo2 && num.intValue() < modulo) {
                z3 = true;
            } else {
                z3 = z4;
            }
            if (z3) {
                android.view.View view2 = this.mViewsMap.get(num).view;
                int i12 = this.mViewsMap.get(num).relativeIndex;
                this.mPreviousViews.add(num);
                transformViewForTransition(i12, -1, view2, z);
            }
            z4 = false;
        }
        if (i3 != this.mCurrentWindowStart || i4 != this.mCurrentWindowEnd || i11 != this.mCurrentWindowStartUnbounded) {
            int i13 = i3;
            while (i13 <= i4) {
                int modulo3 = modulo(i13, getWindowSize());
                if (this.mViewsMap.containsKey(java.lang.Integer.valueOf(modulo3))) {
                    i5 = this.mViewsMap.get(java.lang.Integer.valueOf(modulo3)).relativeIndex;
                } else {
                    i5 = i2;
                }
                int i14 = i13 - i11;
                if ((!this.mViewsMap.containsKey(java.lang.Integer.valueOf(modulo3)) || this.mPreviousViews.contains(java.lang.Integer.valueOf(modulo3))) ? false : z5) {
                    android.view.View view3 = this.mViewsMap.get(java.lang.Integer.valueOf(modulo3)).view;
                    this.mViewsMap.get(java.lang.Integer.valueOf(modulo3)).relativeIndex = i14;
                    applyTransformForChildAtIndex(view3, i14);
                    transformViewForTransition(i5, i14, view3, z);
                    i6 = count;
                    i9 = i2;
                    i7 = i11;
                    i8 = i4;
                } else {
                    int modulo4 = modulo(i13, count);
                    android.view.View view4 = this.mAdapter.getView(modulo4, null, this);
                    long itemId = this.mAdapter.getItemId(modulo4);
                    android.widget.FrameLayout frameForChild = getFrameForChild();
                    if (view4 != null) {
                        frameForChild.addView(view4);
                    }
                    i6 = count;
                    i7 = i11;
                    i8 = i4;
                    this.mViewsMap.put(java.lang.Integer.valueOf(modulo3), new android.widget.AdapterViewAnimator.ViewAndMetaData(frameForChild, i14, modulo4, itemId));
                    addChild(frameForChild);
                    applyTransformForChildAtIndex(frameForChild, i14);
                    i9 = -1;
                    transformViewForTransition(-1, i14, frameForChild, z);
                }
                this.mViewsMap.get(java.lang.Integer.valueOf(modulo3)).view.bringToFront();
                i13++;
                i2 = i9;
                count = i6;
                i11 = i7;
                i4 = i8;
                z5 = true;
            }
            int i15 = count;
            this.mCurrentWindowStart = i3;
            this.mCurrentWindowEnd = i4;
            this.mCurrentWindowStartUnbounded = i11;
            if (this.mRemoteViewsAdapter != null) {
                this.mRemoteViewsAdapter.setVisibleRangeHint(modulo(this.mCurrentWindowStart, i15), modulo(this.mCurrentWindowEnd, i15));
            }
        }
        requestLayout();
        invalidate();
    }

    private void addChild(android.view.View view) {
        addViewInLayout(view, -1, createOrReuseLayoutParams(view));
        if (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) {
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 0);
            view.measure(makeMeasureSpec, makeMeasureSpec);
            this.mReferenceChildWidth = view.getMeasuredWidth();
            this.mReferenceChildHeight = view.getMeasuredHeight();
        }
    }

    void showTapFeedback(android.view.View view) {
        view.setPressed(true);
    }

    void hideTapFeedback(android.view.View view) {
        view.setPressed(false);
    }

    void cancelHandleClick() {
        android.view.View currentView = getCurrentView();
        if (currentView != null) {
            hideTapFeedback(currentView);
        }
        this.mTouchMode = 0;
    }

    final class CheckForTap implements java.lang.Runnable {
        CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.widget.AdapterViewAnimator.this.mTouchMode == 1) {
                android.widget.AdapterViewAnimator.this.showTapFeedback(android.widget.AdapterViewAnimator.this.getCurrentView());
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z = true;
        switch (motionEvent.getAction()) {
            case 0:
                android.view.View currentView = getCurrentView();
                if (currentView != null && isTransformedTouchPointInView(motionEvent.getX(), motionEvent.getY(), currentView, null)) {
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new android.widget.AdapterViewAnimator.CheckForTap();
                    }
                    this.mTouchMode = 1;
                    postDelayed(this.mPendingCheckForTap, android.view.ViewConfiguration.getTapTimeout());
                    break;
                }
                break;
            case 1:
                if (this.mTouchMode == 1) {
                    final android.view.View currentView2 = getCurrentView();
                    final android.widget.AdapterViewAnimator.ViewAndMetaData metaDataForChild = getMetaDataForChild(currentView2);
                    if (currentView2 != null && isTransformedTouchPointInView(motionEvent.getX(), motionEvent.getY(), currentView2, null)) {
                        android.os.Handler handler = getHandler();
                        if (handler != null) {
                            handler.removeCallbacks(this.mPendingCheckForTap);
                        }
                        showTapFeedback(currentView2);
                        postDelayed(new java.lang.Runnable() { // from class: android.widget.AdapterViewAnimator.1
                            @Override // java.lang.Runnable
                            public void run() {
                                android.widget.AdapterViewAnimator.this.hideTapFeedback(currentView2);
                                android.widget.AdapterViewAnimator.this.post(new java.lang.Runnable() { // from class: android.widget.AdapterViewAnimator.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (metaDataForChild != null) {
                                            android.widget.AdapterViewAnimator.this.performItemClick(currentView2, metaDataForChild.adapterPosition, metaDataForChild.itemId);
                                        } else {
                                            android.widget.AdapterViewAnimator.this.performItemClick(currentView2, 0, 0L);
                                        }
                                    }
                                });
                            }
                        }, android.view.ViewConfiguration.getPressedStateDuration());
                        this.mTouchMode = 0;
                        break;
                    }
                }
                z = false;
                this.mTouchMode = 0;
                break;
            case 3:
                android.view.View currentView3 = getCurrentView();
                if (currentView3 != null) {
                    hideTapFeedback(currentView3);
                }
                this.mTouchMode = 0;
                break;
        }
        return false;
    }

    private void measureChildren() {
        int childCount = getChildCount();
        int measuredWidth = (getMeasuredWidth() - this.mPaddingLeft) - this.mPaddingRight;
        int measuredHeight = (getMeasuredHeight() - this.mPaddingTop) - this.mPaddingBottom;
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).measure(android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        boolean z = (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) ? false : true;
        if (mode2 == 0) {
            size2 = z ? this.mReferenceChildHeight + this.mPaddingTop + this.mPaddingBottom : 0;
        } else if (mode2 == Integer.MIN_VALUE && z) {
            int i3 = this.mReferenceChildHeight + this.mPaddingTop + this.mPaddingBottom;
            size2 = i3 > size2 ? size2 | 16777216 : i3;
        }
        if (mode == 0) {
            size = z ? this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight : 0;
        } else if (mode2 == Integer.MIN_VALUE && z) {
            int i4 = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight;
            size = i4 > size ? size | 16777216 : i4;
        }
        setMeasuredDimension(size, size2);
        measureChildren();
    }

    void checkForAndHandleDataChanged() {
        if (this.mDataChanged) {
            post(new java.lang.Runnable() { // from class: android.widget.AdapterViewAnimator.2
                @Override // java.lang.Runnable
                public void run() {
                    android.widget.AdapterViewAnimator.this.handleDataChanged();
                    if (android.widget.AdapterViewAnimator.this.mWhichChild >= android.widget.AdapterViewAnimator.this.getWindowSize()) {
                        android.widget.AdapterViewAnimator.this.mWhichChild = 0;
                        android.widget.AdapterViewAnimator.this.showOnly(android.widget.AdapterViewAnimator.this.mWhichChild, false);
                    } else if (android.widget.AdapterViewAnimator.this.mOldItemCount != android.widget.AdapterViewAnimator.this.getCount()) {
                        android.widget.AdapterViewAnimator.this.showOnly(android.widget.AdapterViewAnimator.this.mWhichChild, false);
                    }
                    android.widget.AdapterViewAnimator.this.refreshChildren();
                    android.widget.AdapterViewAnimator.this.requestLayout();
                }
            });
        }
        this.mDataChanged = false;
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        checkForAndHandleDataChanged();
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            android.view.View childAt = getChildAt(i5);
            childAt.layout(this.mPaddingLeft, this.mPaddingTop, this.mPaddingLeft + childAt.getMeasuredWidth(), this.mPaddingTop + childAt.getMeasuredHeight());
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.AdapterViewAnimator.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.AdapterViewAnimator.SavedState>() { // from class: android.widget.AdapterViewAnimator.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.AdapterViewAnimator.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.AdapterViewAnimator.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.AdapterViewAnimator.SavedState[] newArray(int i) {
                return new android.widget.AdapterViewAnimator.SavedState[i];
            }
        };
        int whichChild;

        SavedState(android.os.Parcelable parcelable, int i) {
            super(parcelable);
            this.whichChild = i;
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.whichChild = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.whichChild);
        }

        public java.lang.String toString() {
            return "AdapterViewAnimator.SavedState{ whichChild = " + this.whichChild + " }";
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (this.mRemoteViewsAdapter != null) {
            this.mRemoteViewsAdapter.saveRemoteViewsCache();
        }
        return new android.widget.AdapterViewAnimator.SavedState(onSaveInstanceState, this.mWhichChild);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.AdapterViewAnimator.SavedState savedState = (android.widget.AdapterViewAnimator.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mWhichChild = savedState.whichChild;
        if (this.mRemoteViewsAdapter != null && this.mAdapter == null) {
            this.mRestoreWhichChild = this.mWhichChild;
        } else {
            setDisplayedChild(this.mWhichChild, false);
        }
    }

    public android.view.View getCurrentView() {
        return getViewAtRelativeIndex(this.mActiveOffset);
    }

    public android.animation.ObjectAnimator getInAnimation() {
        return this.mInAnimation;
    }

    public void setInAnimation(android.animation.ObjectAnimator objectAnimator) {
        this.mInAnimation = objectAnimator;
    }

    public android.animation.ObjectAnimator getOutAnimation() {
        return this.mOutAnimation;
    }

    public void setOutAnimation(android.animation.ObjectAnimator objectAnimator) {
        this.mOutAnimation = objectAnimator;
    }

    public void setInAnimation(android.content.Context context, int i) {
        setInAnimation((android.animation.ObjectAnimator) android.animation.AnimatorInflater.loadAnimator(context, i));
    }

    public void setOutAnimation(android.content.Context context, int i) {
        setOutAnimation((android.animation.ObjectAnimator) android.animation.AnimatorInflater.loadAnimator(context, i));
    }

    public void setAnimateFirstView(boolean z) {
        this.mAnimateFirstTime = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        return getCurrentView() != null ? getCurrentView().getBaseline() : super.getBaseline();
    }

    @Override // android.widget.AdapterView
    public android.widget.Adapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AdapterView
    public void setAdapter(android.widget.Adapter adapter) {
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mAdapter = adapter;
        checkFocus();
        if (this.mAdapter != null) {
            this.mDataSetObserver = new android.widget.AdapterView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mItemCount = this.mAdapter.getCount();
        }
        setFocusable(true);
        this.mWhichChild = 0;
        showOnly(this.mWhichChild, false);
    }

    @android.view.RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(android.content.Intent intent) {
        setRemoteViewsAdapter(intent, false);
    }

    public java.lang.Runnable setRemoteViewsAdapterAsync(android.content.Intent intent) {
        return new android.widget.RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void setRemoteViewsAdapter(android.content.Intent intent, boolean z) {
        if (this.mRemoteViewsAdapter != null && new android.content.Intent.FilterComparison(intent).equals(new android.content.Intent.FilterComparison(this.mRemoteViewsAdapter.getRemoteViewsServiceIntent()))) {
            return;
        }
        this.mDeferNotifyDataSetChanged = false;
        this.mRemoteViewsAdapter = new android.widget.RemoteViewsAdapter(getContext(), intent, this, z);
        if (this.mRemoteViewsAdapter.isDataReady()) {
            setAdapter(this.mRemoteViewsAdapter);
        }
    }

    public void setRemoteViewsOnClickHandler(android.widget.RemoteViews.InteractionHandler interactionHandler) {
        if (this.mRemoteViewsAdapter != null) {
            this.mRemoteViewsAdapter.setRemoteViewsInteractionHandler(interactionHandler);
        }
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        setDisplayedChild(i);
    }

    @Override // android.widget.AdapterView
    public android.view.View getSelectedView() {
        return getViewAtRelativeIndex(this.mActiveOffset);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public boolean onRemoteAdapterConnected() {
        if (this.mRemoteViewsAdapter != this.mAdapter) {
            setAdapter(this.mRemoteViewsAdapter);
            if (this.mDeferNotifyDataSetChanged) {
                this.mRemoteViewsAdapter.notifyDataSetChanged();
                this.mDeferNotifyDataSetChanged = false;
            }
            if (this.mRestoreWhichChild > -1) {
                setDisplayedChild(this.mRestoreWhichChild, false);
                this.mRestoreWhichChild = -1;
            }
            return false;
        }
        if (this.mRemoteViewsAdapter == null) {
            return false;
        }
        this.mRemoteViewsAdapter.superNotifyDataSetChanged();
        return true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void onRemoteAdapterDisconnected() {
    }

    @Override // android.widget.Advanceable
    public void advance() {
        showNext();
    }

    @Override // android.widget.Advanceable
    public void fyiWillBeAdvancedByHostKThx() {
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.AdapterViewAnimator.class.getName();
    }
}
