package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ViewPager extends android.view.ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SCROLL_X = 16777216;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final java.lang.String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private int mActivePointerId;
    private com.android.internal.widget.PagerAdapter mAdapter;
    private com.android.internal.widget.ViewPager.OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private final int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private final int mDefaultGutterSize;
    private int mDrawingOrder;
    private java.util.ArrayList<android.view.View> mDrawingOrderedChildren;
    private final java.lang.Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private boolean mFirstLayout;
    private float mFirstOffset;
    private final int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private com.android.internal.widget.ViewPager.OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final java.util.ArrayList<com.android.internal.widget.ViewPager.ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
    private final android.widget.EdgeEffect mLeftEdge;
    private int mLeftIncr;
    private android.graphics.drawable.Drawable mMarginDrawable;
    private final int mMaximumVelocity;
    private final int mMinimumVelocity;
    private com.android.internal.widget.ViewPager.PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private com.android.internal.widget.ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private com.android.internal.widget.ViewPager.PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private android.os.Parcelable mRestoredAdapterState;
    private java.lang.ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    private final android.widget.EdgeEffect mRightEdge;
    private int mScrollState;
    private final android.widget.Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final com.android.internal.widget.ViewPager.ItemInfo mTempItem;
    private final android.graphics.Rect mTempRect;
    private int mTopPageBounds;
    private final int mTouchSlop;
    private android.view.VelocityTracker mVelocityTracker;
    private static final int[] LAYOUT_ATTRS = {16842931};
    private static final java.util.Comparator<com.android.internal.widget.ViewPager.ItemInfo> COMPARATOR = new java.util.Comparator<com.android.internal.widget.ViewPager.ItemInfo>() { // from class: com.android.internal.widget.ViewPager.1
        @Override // java.util.Comparator
        public int compare(com.android.internal.widget.ViewPager.ItemInfo itemInfo, com.android.internal.widget.ViewPager.ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    };
    private static final android.view.animation.Interpolator sInterpolator = new android.view.animation.Interpolator() { // from class: com.android.internal.widget.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private static final com.android.internal.widget.ViewPager.ViewPositionComparator sPositionComparator = new com.android.internal.widget.ViewPager.ViewPositionComparator();

    interface Decor {
    }

    interface OnAdapterChangeListener {
        void onAdapterChanged(com.android.internal.widget.PagerAdapter pagerAdapter, com.android.internal.widget.PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public interface PageTransformer {
        void transformPage(android.view.View view, float f);
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<com.android.internal.widget.ViewPager.LayoutParams> {
            private int mLayout_gravityId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(com.android.internal.widget.ViewPager.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            }
        }

        public LayoutParams() {
            super(-1, -1);
            this.widthFactor = 0.0f;
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.widthFactor = 0.0f;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.widget.ViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    static class ItemInfo {
        java.lang.Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class SimpleOnPageChangeListener implements com.android.internal.widget.ViewPager.OnPageChangeListener {
        @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
        }

        @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }
    }

    public ViewPager(android.content.Context context) {
        this(context, null);
    }

    public ViewPager(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewPager(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ViewPager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mItems = new java.util.ArrayList<>();
        this.mTempItem = new com.android.internal.widget.ViewPager.ItemInfo();
        this.mTempRect = new android.graphics.Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mLeftIncr = -1;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new java.lang.Runnable() { // from class: com.android.internal.widget.ViewPager.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.ViewPager.this.setScrollState(0);
                com.android.internal.widget.ViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        this.mScroller = new android.widget.Scroller(context, sInterpolator);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new android.widget.EdgeEffect(context, attributeSet);
        this.mRightEdge = new android.widget.EdgeEffect(context, attributeSet);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScrollState(int i) {
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        if (this.mPageTransformer != null) {
            enableLayers(i != 0);
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    public void setAdapter(com.android.internal.widget.PagerAdapter pagerAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate((android.view.ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(i);
                this.mAdapter.destroyItem((android.view.ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((android.view.ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        com.android.internal.widget.PagerAdapter pagerAdapter2 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new com.android.internal.widget.ViewPager.PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!z) {
                populate();
            } else {
                requestLayout();
            }
        }
        if (this.mAdapterChangeListener != null && pagerAdapter2 != pagerAdapter) {
            this.mAdapterChangeListener.onAdapterChanged(pagerAdapter2, pagerAdapter);
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((com.android.internal.widget.ViewPager.LayoutParams) getChildAt(i).getLayoutParams()).isDecor) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    public com.android.internal.widget.PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    void setOnAdapterChangeListener(com.android.internal.widget.ViewPager.OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    private int getPaddedWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, z, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    boolean setCurrentItemInternal(int i, boolean z, boolean z2) {
        return setCurrentItemInternal(i, z, z2, 0);
    }

    boolean setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return false;
        }
        int constrain = android.util.MathUtils.constrain(i, 0, this.mAdapter.getCount() - 1);
        if (!z2 && this.mCurItem == constrain && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return false;
        }
        int i3 = this.mOffscreenPageLimit;
        if (constrain > this.mCurItem + i3 || constrain < this.mCurItem - i3) {
            for (int i4 = 0; i4 < this.mItems.size(); i4++) {
                this.mItems.get(i4).scrolling = true;
            }
        }
        boolean z3 = this.mCurItem != constrain;
        if (this.mFirstLayout) {
            this.mCurItem = constrain;
            if (z3 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(constrain);
            }
            if (z3 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(constrain);
            }
            requestLayout();
        } else {
            populate(constrain);
            scrollToItem(constrain, z, i2, z3);
        }
        return true;
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        int leftEdgeForItem = getLeftEdgeForItem(i);
        if (z) {
            smoothScrollTo(leftEdgeForItem, 0, i2);
            if (z2 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(i);
            }
            if (z2 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(i);
                return;
            }
            return;
        }
        if (z2 && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(i);
        }
        if (z2 && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(i);
        }
        completeScroll(false);
        scrollTo(leftEdgeForItem, 0);
        pageScrolled(leftEdgeForItem);
    }

    private int getLeftEdgeForItem(int i) {
        com.android.internal.widget.ViewPager.ItemInfo infoForPosition = infoForPosition(i);
        if (infoForPosition == null) {
            return 0;
        }
        float paddedWidth = getPaddedWidth();
        int constrain = (int) (android.util.MathUtils.constrain(infoForPosition.offset, this.mFirstOffset, this.mLastOffset) * paddedWidth);
        if (isLayoutRtl()) {
            return (16777216 - ((int) ((paddedWidth * infoForPosition.widthFactor) + 0.5f))) - constrain;
        }
        return constrain;
    }

    public void setOnPageChangeListener(com.android.internal.widget.ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageTransformer(boolean z, com.android.internal.widget.ViewPager.PageTransformer pageTransformer) {
        boolean z2 = pageTransformer != null;
        boolean z3 = z2 != (this.mPageTransformer != null);
        this.mPageTransformer = pageTransformer;
        setChildrenDrawingOrderEnabled(z2);
        if (z2) {
            this.mDrawingOrder = z ? 2 : 1;
        } else {
            this.mDrawingOrder = 0;
        }
        if (z3) {
            populate();
        }
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        if (this.mDrawingOrder == 2) {
            i2 = (i - 1) - i2;
        }
        return ((com.android.internal.widget.ViewPager.LayoutParams) this.mDrawingOrderedChildren.get(i2).getLayoutParams()).childIndex;
    }

    com.android.internal.widget.ViewPager.OnPageChangeListener setInternalPageChangeListener(com.android.internal.widget.ViewPager.OnPageChangeListener onPageChangeListener) {
        com.android.internal.widget.ViewPager.OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int i) {
        if (i < 1) {
            android.util.Log.w(TAG, "Requested offscreen page limit " + i + " too small; defaulting to 1");
            i = 1;
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i;
            populate();
        }
    }

    public void setPageMargin(int i) {
        int i2 = this.mPageMargin;
        this.mPageMargin = i;
        int width = getWidth();
        recomputeScrollPosition(width, width, i, i2);
        requestLayout();
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMarginDrawable(android.graphics.drawable.Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageMarginDrawable(int i) {
        setPageMarginDrawable(getContext().getDrawable(i));
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable drawable = this.mMarginDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) java.lang.Math.sin((float) ((f - 0.5f) * 0.4712389167638204d));
    }

    void smoothScrollTo(int i, int i2) {
        smoothScrollTo(i, i2, 0);
    }

    void smoothScrollTo(int i, int i2, int i3) {
        int abs;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i4 = i - scrollX;
        int i5 = i2 - scrollY;
        if (i4 == 0 && i5 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int paddedWidth = getPaddedWidth();
        int i6 = paddedWidth / 2;
        float f = paddedWidth;
        float f2 = i6;
        float distanceInfluenceForSnapDuration = f2 + (distanceInfluenceForSnapDuration(java.lang.Math.min(1.0f, (java.lang.Math.abs(i4) * 1.0f) / f)) * f2);
        int abs2 = java.lang.Math.abs(i3);
        if (abs2 > 0) {
            abs = java.lang.Math.round(java.lang.Math.abs(distanceInfluenceForSnapDuration / abs2) * 1000.0f) * 4;
        } else {
            abs = (int) (((java.lang.Math.abs(i4) / ((f * this.mAdapter.getPageWidth(this.mCurItem)) + this.mPageMargin)) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(scrollX, scrollY, i4, i5, java.lang.Math.min(abs, 600));
        postInvalidateOnAnimation();
    }

    com.android.internal.widget.ViewPager.ItemInfo addNewItem(int i, int i2) {
        com.android.internal.widget.ViewPager.ItemInfo itemInfo = new com.android.internal.widget.ViewPager.ItemInfo();
        itemInfo.position = i;
        itemInfo.object = this.mAdapter.instantiateItem((android.view.ViewGroup) this, i);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(i2, itemInfo);
        }
        return itemInfo;
    }

    void dataSetChanged() {
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int i = this.mCurItem;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < this.mItems.size()) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z2) {
                        this.mAdapter.startUpdate((android.view.ViewGroup) this);
                        z2 = true;
                    }
                    this.mAdapter.destroyItem((android.view.ViewGroup) this, itemInfo.position, itemInfo.object);
                    if (this.mCurItem != itemInfo.position) {
                        z = true;
                    } else {
                        i = java.lang.Math.max(0, java.lang.Math.min(this.mCurItem, count - 1));
                        z = true;
                    }
                } else if (itemInfo.position != itemPosition) {
                    if (itemInfo.position == this.mCurItem) {
                        i = itemPosition;
                    }
                    itemInfo.position = itemPosition;
                    z = true;
                }
            }
            i2++;
        }
        if (z2) {
            this.mAdapter.finishUpdate((android.view.ViewGroup) this);
        }
        java.util.Collections.sort(this.mItems, COMPARATOR);
        if (z) {
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                com.android.internal.widget.ViewPager.LayoutParams layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) getChildAt(i3).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(i, false, true);
            requestLayout();
        }
    }

    public void populate() {
        populate(this.mCurItem);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0073, code lost:
    
        if (r9.position == r17.mCurItem) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void populate(int i) {
        int i2;
        com.android.internal.widget.ViewPager.ItemInfo itemInfo;
        java.lang.String hexString;
        com.android.internal.widget.ViewPager.ItemInfo itemInfo2;
        android.graphics.Rect rect;
        com.android.internal.widget.ViewPager.ItemInfo infoForChild;
        if (this.mCurItem == i) {
            i2 = 2;
            itemInfo = null;
        } else {
            i2 = this.mCurItem < i ? 66 : 17;
            itemInfo = infoForPosition(this.mCurItem);
            this.mCurItem = i;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
            return;
        }
        if (this.mPopulatePending) {
            sortChildDrawingOrder();
            return;
        }
        if (getWindowToken() != null) {
            this.mAdapter.startUpdate((android.view.ViewGroup) this);
            int i3 = this.mOffscreenPageLimit;
            int max = java.lang.Math.max(0, this.mCurItem - i3);
            int count = this.mAdapter.getCount();
            int min = java.lang.Math.min(count - 1, this.mCurItem + i3);
            if (count != this.mExpectedAdapterCount) {
                try {
                    hexString = getResources().getResourceName(getId());
                } catch (android.content.res.Resources.NotFoundException e) {
                    hexString = java.lang.Integer.toHexString(getId());
                }
                throw new java.lang.IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + count + " Pager id: " + hexString + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
            }
            int i4 = 0;
            while (true) {
                if (i4 >= this.mItems.size()) {
                    break;
                }
                itemInfo2 = this.mItems.get(i4);
                if (itemInfo2.position < this.mCurItem) {
                    i4++;
                }
            }
            itemInfo2 = null;
            if (itemInfo2 == null && count > 0) {
                itemInfo2 = addNewItem(this.mCurItem, i4);
            }
            if (itemInfo2 != null) {
                int i5 = i4 - 1;
                com.android.internal.widget.ViewPager.ItemInfo itemInfo3 = i5 >= 0 ? this.mItems.get(i5) : null;
                int paddedWidth = getPaddedWidth();
                float paddingLeft = paddedWidth <= 0 ? 0.0f : (2.0f - itemInfo2.widthFactor) + (getPaddingLeft() / paddedWidth);
                float f = 0.0f;
                for (int i6 = this.mCurItem - 1; i6 >= 0; i6--) {
                    if (f >= paddingLeft && i6 < max) {
                        if (itemInfo3 == null) {
                            break;
                        }
                        if (i6 == itemInfo3.position && !itemInfo3.scrolling) {
                            this.mItems.remove(i5);
                            this.mAdapter.destroyItem((android.view.ViewGroup) this, i6, itemInfo3.object);
                            i5--;
                            i4--;
                            itemInfo3 = i5 >= 0 ? this.mItems.get(i5) : null;
                        }
                    } else if (itemInfo3 != null && i6 == itemInfo3.position) {
                        f += itemInfo3.widthFactor;
                        i5--;
                        itemInfo3 = i5 >= 0 ? this.mItems.get(i5) : null;
                    } else {
                        f += addNewItem(i6, i5 + 1).widthFactor;
                        i4++;
                        itemInfo3 = i5 >= 0 ? this.mItems.get(i5) : null;
                    }
                }
                float f2 = itemInfo2.widthFactor;
                int i7 = i4 + 1;
                if (f2 < 2.0f) {
                    com.android.internal.widget.ViewPager.ItemInfo itemInfo4 = i7 < this.mItems.size() ? this.mItems.get(i7) : null;
                    float paddingRight = paddedWidth <= 0 ? 0.0f : (getPaddingRight() / paddedWidth) + 2.0f;
                    int i8 = this.mCurItem;
                    while (true) {
                        i8++;
                        if (i8 >= count) {
                            break;
                        }
                        if (f2 >= paddingRight && i8 > min) {
                            if (itemInfo4 == null) {
                                break;
                            }
                            if (i8 == itemInfo4.position && !itemInfo4.scrolling) {
                                this.mItems.remove(i7);
                                this.mAdapter.destroyItem((android.view.ViewGroup) this, i8, itemInfo4.object);
                                itemInfo4 = i7 < this.mItems.size() ? this.mItems.get(i7) : null;
                            }
                        } else if (itemInfo4 != null && i8 == itemInfo4.position) {
                            f2 += itemInfo4.widthFactor;
                            i7++;
                            itemInfo4 = i7 < this.mItems.size() ? this.mItems.get(i7) : null;
                        } else {
                            com.android.internal.widget.ViewPager.ItemInfo addNewItem = addNewItem(i8, i7);
                            i7++;
                            f2 += addNewItem.widthFactor;
                            itemInfo4 = i7 < this.mItems.size() ? this.mItems.get(i7) : null;
                        }
                    }
                }
                calculatePageOffsets(itemInfo2, i4, itemInfo);
            }
            this.mAdapter.setPrimaryItem((android.view.ViewGroup) this, this.mCurItem, itemInfo2 != null ? itemInfo2.object : null);
            this.mAdapter.finishUpdate((android.view.ViewGroup) this);
            int childCount = getChildCount();
            for (int i9 = 0; i9 < childCount; i9++) {
                android.view.View childAt = getChildAt(i9);
                com.android.internal.widget.ViewPager.LayoutParams layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) childAt.getLayoutParams();
                layoutParams.childIndex = i9;
                if (!layoutParams.isDecor && layoutParams.widthFactor == 0.0f && (infoForChild = infoForChild(childAt)) != null) {
                    layoutParams.widthFactor = infoForChild.widthFactor;
                    layoutParams.position = infoForChild.position;
                }
            }
            sortChildDrawingOrder();
            if (hasFocus()) {
                android.view.View findFocus = findFocus();
                com.android.internal.widget.ViewPager.ItemInfo infoForAnyChild = findFocus != null ? infoForAnyChild(findFocus) : null;
                if (infoForAnyChild == null || infoForAnyChild.position != this.mCurItem) {
                    for (int i10 = 0; i10 < getChildCount(); i10++) {
                        android.view.View childAt2 = getChildAt(i10);
                        com.android.internal.widget.ViewPager.ItemInfo infoForChild2 = infoForChild(childAt2);
                        if (infoForChild2 != null && infoForChild2.position == this.mCurItem) {
                            if (findFocus == null) {
                                rect = null;
                            } else {
                                rect = this.mTempRect;
                                findFocus.getFocusedRect(this.mTempRect);
                                offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
                                offsetRectIntoDescendantCoords(childAt2, this.mTempRect);
                            }
                            if (childAt2.requestFocus(i2, rect)) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new java.util.ArrayList<>();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            java.util.Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(com.android.internal.widget.ViewPager.ItemInfo itemInfo, int i, com.android.internal.widget.ViewPager.ItemInfo itemInfo2) {
        com.android.internal.widget.ViewPager.ItemInfo itemInfo3;
        com.android.internal.widget.ViewPager.ItemInfo itemInfo4;
        int count = this.mAdapter.getCount();
        int paddedWidth = getPaddedWidth();
        float f = paddedWidth > 0 ? this.mPageMargin / paddedWidth : 0.0f;
        if (itemInfo2 != null) {
            int i2 = itemInfo2.position;
            if (i2 < itemInfo.position) {
                float f2 = itemInfo2.offset + itemInfo2.widthFactor + f;
                int i3 = i2 + 1;
                int i4 = 0;
                while (i3 <= itemInfo.position && i4 < this.mItems.size()) {
                    com.android.internal.widget.ViewPager.ItemInfo itemInfo5 = this.mItems.get(i4);
                    while (true) {
                        itemInfo4 = itemInfo5;
                        if (i3 <= itemInfo4.position || i4 >= this.mItems.size() - 1) {
                            break;
                        }
                        i4++;
                        itemInfo5 = this.mItems.get(i4);
                    }
                    while (i3 < itemInfo4.position) {
                        f2 += this.mAdapter.getPageWidth(i3) + f;
                        i3++;
                    }
                    itemInfo4.offset = f2;
                    f2 += itemInfo4.widthFactor + f;
                    i3++;
                }
            } else if (i2 > itemInfo.position) {
                int size = this.mItems.size() - 1;
                float f3 = itemInfo2.offset;
                while (true) {
                    i2--;
                    if (i2 < itemInfo.position || size < 0) {
                        break;
                    }
                    com.android.internal.widget.ViewPager.ItemInfo itemInfo6 = this.mItems.get(size);
                    while (true) {
                        itemInfo3 = itemInfo6;
                        if (i2 >= itemInfo3.position || size <= 0) {
                            break;
                        }
                        size--;
                        itemInfo6 = this.mItems.get(size);
                    }
                    while (i2 > itemInfo3.position) {
                        f3 -= this.mAdapter.getPageWidth(i2) + f;
                        i2--;
                    }
                    f3 -= itemInfo3.widthFactor + f;
                    itemInfo3.offset = f3;
                }
            }
        }
        int size2 = this.mItems.size();
        float f4 = itemInfo.offset;
        int i5 = itemInfo.position - 1;
        this.mFirstOffset = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        int i6 = count - 1;
        this.mLastOffset = itemInfo.position == i6 ? (itemInfo.offset + itemInfo.widthFactor) - 1.0f : Float.MAX_VALUE;
        int i7 = i - 1;
        while (i7 >= 0) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo7 = this.mItems.get(i7);
            while (i5 > itemInfo7.position) {
                f4 -= this.mAdapter.getPageWidth(i5) + f;
                i5--;
            }
            f4 -= itemInfo7.widthFactor + f;
            itemInfo7.offset = f4;
            if (itemInfo7.position == 0) {
                this.mFirstOffset = f4;
            }
            i7--;
            i5--;
        }
        float f5 = itemInfo.offset + itemInfo.widthFactor + f;
        int i8 = itemInfo.position + 1;
        int i9 = i + 1;
        while (i9 < size2) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo8 = this.mItems.get(i9);
            while (i8 < itemInfo8.position) {
                f5 += this.mAdapter.getPageWidth(i8) + f;
                i8++;
            }
            if (itemInfo8.position == i6) {
                this.mLastOffset = (itemInfo8.widthFactor + f5) - 1.0f;
            }
            itemInfo8.offset = f5;
            f5 += itemInfo8.widthFactor + f;
            i9++;
            i8++;
        }
    }

    public static class SavedState extends android.view.AbsSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.widget.ViewPager.SavedState> CREATOR = new android.os.Parcelable.ClassLoaderCreator<com.android.internal.widget.ViewPager.SavedState>() { // from class: com.android.internal.widget.ViewPager.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public com.android.internal.widget.ViewPager.SavedState createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
                return new com.android.internal.widget.ViewPager.SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.ViewPager.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.widget.ViewPager.SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.ViewPager.SavedState[] newArray(int i) {
                return new com.android.internal.widget.ViewPager.SavedState[i];
            }
        };
        android.os.Parcelable adapterState;
        java.lang.ClassLoader loader;
        int position;

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }

        public java.lang.String toString() {
            return "FragmentPager.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        SavedState(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        com.android.internal.widget.ViewPager.SavedState savedState = new com.android.internal.widget.ViewPager.SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (!(parcelable instanceof com.android.internal.widget.ViewPager.SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        com.android.internal.widget.ViewPager.SavedState savedState = (com.android.internal.widget.ViewPager.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
        } else {
            this.mRestoredCurItem = savedState.position;
            this.mRestoredAdapterState = savedState.adapterState;
            this.mRestoredClassLoader = savedState.loader;
        }
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        com.android.internal.widget.ViewPager.LayoutParams layoutParams2 = (com.android.internal.widget.ViewPager.LayoutParams) layoutParams;
        layoutParams2.isDecor |= view instanceof com.android.internal.widget.ViewPager.Decor;
        if (this.mInLayout) {
            if (layoutParams2 != null && layoutParams2.isDecor) {
                throw new java.lang.IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams2.needsMeasure = true;
            addViewInLayout(view, i, layoutParams);
            return;
        }
        super.addView(view, i, layoutParams);
    }

    public java.lang.Object getCurrent() {
        com.android.internal.widget.ViewPager.ItemInfo infoForPosition = infoForPosition(getCurrentItem());
        if (infoForPosition == null) {
            return null;
        }
        return infoForPosition.object;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(android.view.View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    com.android.internal.widget.ViewPager.ItemInfo infoForChild(android.view.View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    com.android.internal.widget.ViewPager.ItemInfo infoForAnyChild(android.view.View view) {
        while (true) {
            java.lang.Object parent = view.getParent();
            if (parent != this) {
                if (parent == null || !(parent instanceof android.view.View)) {
                    return null;
                }
                view = (android.view.View) parent;
            } else {
                return infoForChild(view);
            }
        }
    }

    com.android.internal.widget.ViewPager.ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        com.android.internal.widget.ViewPager.LayoutParams layoutParams;
        com.android.internal.widget.ViewPager.LayoutParams layoutParams2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z = false;
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = java.lang.Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i7 = 0;
        while (true) {
            boolean z2 = true;
            int i8 = 1073741824;
            if (i7 >= childCount) {
                break;
            }
            android.view.View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8 && (layoutParams2 = (com.android.internal.widget.ViewPager.LayoutParams) childAt.getLayoutParams()) != null && layoutParams2.isDecor) {
                int i9 = layoutParams2.gravity & 7;
                int i10 = layoutParams2.gravity & 112;
                boolean z3 = (i10 == 48 || i10 == 80) ? true : z;
                if (i9 != 3 && i9 != 5) {
                    z2 = z;
                }
                int i11 = Integer.MIN_VALUE;
                if (z3) {
                    i3 = Integer.MIN_VALUE;
                    i11 = 1073741824;
                } else if (!z2) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i3 = 1073741824;
                }
                if (layoutParams2.width == -2) {
                    i4 = i11;
                    i5 = paddingLeft;
                } else if (layoutParams2.width == -1) {
                    i5 = paddingLeft;
                    i4 = 1073741824;
                } else {
                    i5 = layoutParams2.width;
                    i4 = 1073741824;
                }
                if (layoutParams2.height == -2) {
                    i6 = measuredHeight;
                    i8 = i3;
                } else if (layoutParams2.height == -1) {
                    i6 = measuredHeight;
                } else {
                    i6 = layoutParams2.height;
                }
                childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(i5, i4), android.view.View.MeasureSpec.makeMeasureSpec(i6, i8));
                if (z3) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z2) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i7++;
            z = false;
        }
        this.mChildWidthMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.mChildHeightMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            android.view.View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(android.view.View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), 1073741824), this.mChildHeightMeasureSpec);
            }
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            recomputeScrollPosition(i, i3, this.mPageMargin, this.mPageMargin);
        }
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        if (i2 > 0 && !this.mItems.isEmpty()) {
            int scrollX = (int) ((getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)) * (((i - getPaddingLeft()) - getPaddingRight()) + i3));
            scrollTo(scrollX, getScrollY());
            if (!this.mScroller.isFinished()) {
                this.mScroller.startScroll(scrollX, 0, (int) (infoForPosition(this.mCurItem).offset * i), 0, this.mScroller.getDuration() - this.mScroller.timePassed());
                return;
            }
            return;
        }
        com.android.internal.widget.ViewPager.ItemInfo infoForPosition = infoForPosition(this.mCurItem);
        int min = (int) ((infoForPosition != null ? java.lang.Math.min(infoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
        if (min != getScrollX()) {
            completeScroll(false);
            scrollTo(min, getScrollY());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        com.android.internal.widget.ViewPager.ItemInfo infoForChild;
        int i5;
        int i6;
        int i7;
        int childCount = getChildCount();
        int i8 = i3 - i;
        int i9 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            android.view.View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                com.android.internal.widget.ViewPager.LayoutParams layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i12 = layoutParams.gravity & 7;
                    int i13 = layoutParams.gravity & 112;
                    switch (i12) {
                        case 1:
                            i6 = paddingLeft;
                            paddingLeft = java.lang.Math.max((i8 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 2:
                        case 4:
                        default:
                            i6 = paddingLeft;
                            break;
                        case 3:
                            i6 = childAt.getMeasuredWidth() + paddingLeft;
                            break;
                        case 5:
                            int measuredWidth = (i8 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                            i6 = paddingLeft;
                            paddingLeft = measuredWidth;
                            break;
                    }
                    switch (i13) {
                        case 16:
                            i7 = paddingTop;
                            paddingTop = java.lang.Math.max((i9 - childAt.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        case 48:
                            i7 = childAt.getMeasuredHeight() + paddingTop;
                            break;
                        case 80:
                            int measuredHeight = (i9 - paddingBottom) - childAt.getMeasuredHeight();
                            paddingBottom += childAt.getMeasuredHeight();
                            i7 = paddingTop;
                            paddingTop = measuredHeight;
                            break;
                        default:
                            i7 = paddingTop;
                            break;
                    }
                    int i14 = paddingLeft + scrollX;
                    childAt.layout(i14, paddingTop, childAt.getMeasuredWidth() + i14, paddingTop + childAt.getMeasuredHeight());
                    i10++;
                    paddingTop = i7;
                    paddingLeft = i6;
                }
            }
        }
        int i15 = (i8 - paddingLeft) - paddingRight;
        for (int i16 = 0; i16 < childCount; i16++) {
            android.view.View childAt2 = getChildAt(i16);
            if (childAt2.getVisibility() != 8) {
                com.android.internal.widget.ViewPager.LayoutParams layoutParams2 = (com.android.internal.widget.ViewPager.LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor && (infoForChild = infoForChild(childAt2)) != null) {
                    if (layoutParams2.needsMeasure) {
                        layoutParams2.needsMeasure = false;
                        childAt2.measure(android.view.View.MeasureSpec.makeMeasureSpec((int) (i15 * layoutParams2.widthFactor), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec((i9 - paddingTop) - paddingBottom, 1073741824));
                    }
                    int measuredWidth2 = childAt2.getMeasuredWidth();
                    int i17 = (int) (i15 * infoForChild.offset);
                    if (isLayoutRtl()) {
                        i5 = ((16777216 - paddingRight) - i17) - measuredWidth2;
                    } else {
                        i5 = paddingLeft + i17;
                    }
                    childAt2.layout(i5, paddingTop, measuredWidth2 + i5, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.mTopPageBounds = paddingTop;
        this.mBottomPageBounds = i9 - paddingBottom;
        this.mDecorChildCount = i10;
        if (this.mFirstLayout) {
            z2 = false;
            scrollToItem(this.mCurItem, false, 0, false);
        } else {
            z2 = false;
        }
        this.mFirstLayout = z2;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                scrollTo(currX, currY);
                if (!pageScrolled(currX)) {
                    this.mScroller.abortAnimation();
                    scrollTo(0, currY);
                }
            }
            postInvalidateOnAnimation();
            return;
        }
        completeScroll(true);
    }

    private boolean pageScrolled(int i) {
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new java.lang.IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        if (isLayoutRtl()) {
            i = 16777216 - i;
        }
        com.android.internal.widget.ViewPager.ItemInfo infoForFirstVisiblePage = infoForFirstVisiblePage();
        int paddedWidth = getPaddedWidth();
        int i2 = this.mPageMargin + paddedWidth;
        float f = paddedWidth;
        int i3 = infoForFirstVisiblePage.position;
        float f2 = ((i / f) - infoForFirstVisiblePage.offset) / (infoForFirstVisiblePage.widthFactor + (this.mPageMargin / f));
        this.mCalledSuper = false;
        onPageScrolled(i3, f2, (int) (i2 * f2));
        if (!this.mCalledSuper) {
            throw new java.lang.IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        return true;
    }

    protected void onPageScrolled(int i, float f, int i2) {
        int i3;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                android.view.View childAt = getChildAt(i4);
                com.android.internal.widget.ViewPager.LayoutParams layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    switch (layoutParams.gravity & 7) {
                        case 1:
                            i3 = paddingLeft;
                            paddingLeft = java.lang.Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 2:
                        case 4:
                        default:
                            i3 = paddingLeft;
                            break;
                        case 3:
                            i3 = childAt.getWidth() + paddingLeft;
                            break;
                        case 5:
                            int measuredWidth = (width - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                            i3 = paddingLeft;
                            paddingLeft = measuredWidth;
                            break;
                    }
                    int left = (paddingLeft + scrollX) - childAt.getLeft();
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left);
                    }
                    paddingLeft = i3;
                }
            }
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(i, f, i2);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(i, f, i2);
        }
        if (this.mPageTransformer != null) {
            int scrollX2 = getScrollX();
            int childCount2 = getChildCount();
            for (int i5 = 0; i5 < childCount2; i5++) {
                android.view.View childAt2 = getChildAt(i5);
                if (!((com.android.internal.widget.ViewPager.LayoutParams) childAt2.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(childAt2, (childAt2.getLeft() - scrollX2) / getPaddedWidth());
                }
            }
        }
        this.mCalledSuper = true;
    }

    private void completeScroll(boolean z) {
        boolean z2 = this.mScrollState == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                scrollTo(currX, currY);
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z2 = true;
            }
        }
        if (z2) {
            if (z) {
                postOnAnimation(this.mEndScrollRunnable);
            } else {
                this.mEndScrollRunnable.run();
            }
        }
    }

    private boolean isGutterDrag(float f, float f2) {
        return (f < ((float) this.mGutterSize) && f2 > 0.0f) || (f > ((float) (getWidth() - this.mGutterSize)) && f2 < 0.0f);
    }

    private void enableLayers(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setLayerType(z ? 2 : 0, null);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = motionEvent.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && java.lang.Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    populate();
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    break;
                } else if (this.mLeftEdge.getDistance() != 0.0f || this.mRightEdge.getDistance() != 0.0f) {
                    this.mIsBeingDragged = true;
                    setScrollState(1);
                    if (this.mLeftEdge.getDistance() != 0.0f) {
                        this.mLeftEdge.onPullDistance(0.0f, 1.0f - (this.mLastMotionY / getHeight()));
                    }
                    if (this.mRightEdge.getDistance() != 0.0f) {
                        this.mRightEdge.onPullDistance(0.0f, this.mLastMotionY / getHeight());
                        break;
                    }
                } else {
                    completeScroll(false);
                    this.mIsBeingDragged = false;
                    break;
                }
                break;
            case 2:
                int i = this.mActivePointerId;
                if (i != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i);
                    float x2 = motionEvent.getX(findPointerIndex);
                    float f = x2 - this.mLastMotionX;
                    float abs = java.lang.Math.abs(f);
                    float y2 = motionEvent.getY(findPointerIndex);
                    float abs2 = java.lang.Math.abs(y2 - this.mInitialMotionY);
                    if (f != 0.0f && !isGutterDrag(this.mLastMotionX, f) && canScroll(this, false, (int) f, (int) x2, (int) y2)) {
                        this.mLastMotionX = x2;
                        this.mLastMotionY = y2;
                        this.mIsUnableToDrag = true;
                        return false;
                    }
                    if (abs > this.mTouchSlop && abs * 0.5f > abs2) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        setScrollState(1);
                        this.mLastMotionX = f > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
                        this.mLastMotionY = y2;
                        setScrollingCacheEnabled(true);
                    } else if (abs2 > this.mTouchSlop) {
                        this.mIsUnableToDrag = true;
                    }
                    if (this.mIsBeingDragged && performDrag(x2, y2)) {
                        postInvalidateOnAnimation();
                        break;
                    }
                }
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        float f;
        boolean z = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                float x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                float y = motionEvent.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                this.mActivePointerId = motionEvent.getPointerId(0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                    this.mPopulatePending = true;
                    float scrollStart = getScrollStart() / getPaddedWidth();
                    com.android.internal.widget.ViewPager.ItemInfo infoForFirstVisiblePage = infoForFirstVisiblePage();
                    int i = infoForFirstVisiblePage.position;
                    if (isLayoutRtl()) {
                        f = (infoForFirstVisiblePage.offset - scrollStart) / infoForFirstVisiblePage.widthFactor;
                    } else {
                        f = (scrollStart - infoForFirstVisiblePage.offset) / infoForFirstVisiblePage.widthFactor;
                    }
                    setCurrentItemInternal(determineTargetPage(i, f, xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, xVelocity);
                    this.mActivePointerId = -1;
                    endDrag();
                    this.mLeftEdge.onRelease();
                    this.mRightEdge.onRelease();
                    z = true;
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    float x2 = motionEvent.getX(findPointerIndex);
                    float abs = java.lang.Math.abs(x2 - this.mLastMotionX);
                    float y2 = motionEvent.getY(findPointerIndex);
                    float abs2 = java.lang.Math.abs(y2 - this.mLastMotionY);
                    if (abs > this.mTouchSlop && abs > abs2) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        this.mLastMotionX = x2 - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
                        this.mLastMotionY = y2;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        android.view.ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                    z = false | performDrag(motionEvent.getX(findPointerIndex2), motionEvent.getY(findPointerIndex2));
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    this.mActivePointerId = -1;
                    endDrag();
                    this.mLeftEdge.onRelease();
                    this.mRightEdge.onRelease();
                    z = true;
                    break;
                }
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.mLastMotionX = motionEvent.getX(actionIndex);
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                break;
        }
        if (z) {
            postInvalidateOnAnimation();
        }
        return true;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        android.view.ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private float releaseHorizontalGlow(float f, float f2) {
        float height = f2 / getHeight();
        float width = f / getWidth();
        float f3 = 0.0f;
        if (this.mLeftEdge.getDistance() != 0.0f) {
            f3 = -this.mLeftEdge.onPullDistance(-width, 1.0f - height);
        } else if (this.mRightEdge.getDistance() != 0.0f) {
            f3 = this.mRightEdge.onPullDistance(width, height);
        }
        return f3 * getWidth();
    }

    private boolean performDrag(float f, float f2) {
        boolean z;
        android.widget.EdgeEffect edgeEffect;
        android.widget.EdgeEffect edgeEffect2;
        float f3;
        float f4;
        float f5 = this.mLastMotionX - f;
        int paddedWidth = getPaddedWidth();
        this.mLastMotionX = f;
        float releaseHorizontalGlow = releaseHorizontalGlow(f5, f2);
        float f6 = f5 - releaseHorizontalGlow;
        boolean z2 = true;
        if (releaseHorizontalGlow == 0.0f) {
            z = false;
        } else {
            z = true;
        }
        if (java.lang.Math.abs(f6) < 1.0E-4f) {
            return z;
        }
        if (isLayoutRtl()) {
            edgeEffect = this.mRightEdge;
            edgeEffect2 = this.mLeftEdge;
        } else {
            edgeEffect = this.mLeftEdge;
            edgeEffect2 = this.mRightEdge;
        }
        float scrollX = getScrollX() + f6;
        if (isLayoutRtl()) {
            scrollX = 1.6777216E7f - scrollX;
        }
        com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(0);
        boolean z3 = itemInfo.position == 0;
        if (z3) {
            f3 = itemInfo.offset * paddedWidth;
        } else {
            f3 = paddedWidth * this.mFirstOffset;
        }
        com.android.internal.widget.ViewPager.ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        boolean z4 = itemInfo2.position == this.mAdapter.getCount() - 1;
        if (z4) {
            f4 = itemInfo2.offset * paddedWidth;
        } else {
            f4 = paddedWidth * this.mLastOffset;
        }
        if (scrollX < f3) {
            if (!z3) {
                z2 = z;
            } else {
                edgeEffect.onPullDistance((f3 - scrollX) / paddedWidth, 1.0f - (f2 / getHeight()));
            }
            scrollX = f3;
            z = z2;
        } else if (scrollX > f4) {
            if (!z4) {
                z2 = z;
            } else {
                edgeEffect2.onPullDistance((scrollX - f4) / paddedWidth, f2 / getHeight());
            }
            z = z2;
            scrollX = f4;
        }
        if (isLayoutRtl()) {
            scrollX = 1.6777216E7f - scrollX;
        }
        int i = (int) scrollX;
        this.mLastMotionX += scrollX - i;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        return z;
    }

    private com.android.internal.widget.ViewPager.ItemInfo infoForFirstVisiblePage() {
        com.android.internal.widget.ViewPager.ItemInfo itemInfo;
        int i;
        int scrollStart = getScrollStart();
        int paddedWidth = getPaddedWidth();
        float f = 0.0f;
        float f2 = paddedWidth > 0 ? scrollStart / paddedWidth : 0.0f;
        float f3 = paddedWidth > 0 ? this.mPageMargin / paddedWidth : 0.0f;
        int size = this.mItems.size();
        int i2 = 0;
        boolean z = true;
        com.android.internal.widget.ViewPager.ItemInfo itemInfo2 = null;
        int i3 = -1;
        float f4 = 0.0f;
        while (i2 < size) {
            com.android.internal.widget.ViewPager.ItemInfo itemInfo3 = this.mItems.get(i2);
            if (!z && itemInfo3.position != (i = i3 + 1)) {
                com.android.internal.widget.ViewPager.ItemInfo itemInfo4 = this.mTempItem;
                itemInfo4.offset = f + f4 + f3;
                itemInfo4.position = i;
                itemInfo4.widthFactor = this.mAdapter.getPageWidth(itemInfo4.position);
                i2--;
                itemInfo = itemInfo4;
            } else {
                itemInfo = itemInfo3;
            }
            f = itemInfo.offset;
            if (!z && f2 < f) {
                return itemInfo2;
            }
            if (f2 < itemInfo.widthFactor + f + f3 || i2 == this.mItems.size() - 1) {
                return itemInfo;
            }
            int i4 = itemInfo.position;
            float f5 = itemInfo.widthFactor;
            i2++;
            z = false;
            com.android.internal.widget.ViewPager.ItemInfo itemInfo5 = itemInfo;
            i3 = i4;
            f4 = f5;
            itemInfo2 = itemInfo5;
        }
        return itemInfo2;
    }

    private int getScrollStart() {
        if (isLayoutRtl()) {
            return 16777216 - getScrollX();
        }
        return getScrollX();
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        int i4;
        if (java.lang.Math.abs(i3) > this.mFlingDistance && java.lang.Math.abs(i2) > this.mMinimumVelocity && this.mLeftEdge.getDistance() == 0.0f && this.mRightEdge.getDistance() == 0.0f) {
            i4 = i - (i2 < 0 ? this.mLeftIncr : 0);
        } else {
            i4 = (int) (i - (this.mLeftIncr * (f + (i >= this.mCurItem ? 0.4f : 0.6f))));
        }
        if (this.mItems.size() > 0) {
            return android.util.MathUtils.constrain(i4, this.mItems.get(0).position, this.mItems.get(this.mItems.size() - 1).position);
        }
        return i4;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean z = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((-height) + getPaddingTop(), this.mFirstOffset * width);
                this.mLeftEdge.setSize(height, width);
                z = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.mRightEdge.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
                this.mRightEdge.setSize(height2, width2);
                z |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (z) {
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        float pageWidth;
        float f;
        float f2;
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollX = getScrollX();
            float width = getWidth();
            float f3 = this.mPageMargin / width;
            int i = 0;
            com.android.internal.widget.ViewPager.ItemInfo itemInfo = this.mItems.get(0);
            float f4 = itemInfo.offset;
            int size = this.mItems.size();
            int i2 = itemInfo.position;
            int i3 = this.mItems.get(size - 1).position;
            while (i2 < i3) {
                while (i2 > itemInfo.position && i < size) {
                    i++;
                    itemInfo = this.mItems.get(i);
                }
                if (i2 == itemInfo.position) {
                    f4 = itemInfo.offset;
                    pageWidth = itemInfo.widthFactor;
                } else {
                    pageWidth = this.mAdapter.getPageWidth(i2);
                }
                float f5 = f4 * width;
                if (isLayoutRtl()) {
                    f = 1.6777216E7f - f5;
                } else {
                    f = (pageWidth * width) + f5;
                }
                f4 = f4 + pageWidth + f3;
                if (this.mPageMargin + f > scrollX) {
                    f2 = f3;
                    this.mMarginDrawable.setBounds((int) f, this.mTopPageBounds, (int) (this.mPageMargin + f + 0.5f), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                } else {
                    f2 = f3;
                }
                if (f <= scrollX + r2) {
                    i2++;
                    f3 = f2;
                } else {
                    return;
                }
            }
        }
    }

    private void onSecondaryPointerUp(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        if (this.mAdapter == null) {
            return false;
        }
        int paddedWidth = getPaddedWidth();
        int scrollX = getScrollX();
        return i < 0 ? scrollX > ((int) (((float) paddedWidth) * this.mFirstOffset)) : i > 0 && scrollX < ((int) (((float) paddedWidth) * this.mLastOffset));
    }

    protected boolean canScroll(android.view.View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                android.view.View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(android.view.KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            switch (keyEvent.getKeyCode()) {
                case 21:
                    return arrowScroll(17);
                case 22:
                    return arrowScroll(66);
                case 61:
                    if (keyEvent.hasNoModifiers()) {
                        return arrowScroll(2);
                    }
                    if (keyEvent.hasModifiers(1)) {
                        return arrowScroll(1);
                    }
                default:
                    return false;
            }
        }
        return false;
    }

    public boolean arrowScroll(int i) {
        boolean z;
        android.view.View findFocus = findFocus();
        boolean z2 = false;
        if (findFocus == this) {
            findFocus = null;
        } else if (findFocus != null) {
            android.view.ViewParent parent = findFocus.getParent();
            while (true) {
                if (!(parent instanceof android.view.ViewGroup)) {
                    z = false;
                    break;
                }
                if (parent != this) {
                    parent = parent.getParent();
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(findFocus.getClass().getSimpleName());
                for (android.view.ViewParent parent2 = findFocus.getParent(); parent2 instanceof android.view.ViewGroup; parent2 = parent2.getParent()) {
                    sb.append(" => ").append(parent2.getClass().getSimpleName());
                }
                android.util.Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                findFocus = null;
            }
        }
        android.view.View findNextFocus = android.view.FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        if (findNextFocus != null && findNextFocus != findFocus) {
            if (i != 17) {
                if (i == 66) {
                    int i2 = getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left;
                    int i3 = getChildRectInPagerCoordinates(this.mTempRect, findFocus).left;
                    if (findFocus != null && i2 <= i3) {
                        z2 = pageRight();
                    } else {
                        z2 = findNextFocus.requestFocus();
                    }
                }
            } else {
                int i4 = getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left;
                int i5 = getChildRectInPagerCoordinates(this.mTempRect, findFocus).left;
                if (findFocus != null && i4 >= i5) {
                    z2 = pageLeft();
                } else {
                    z2 = findNextFocus.requestFocus();
                }
            }
        } else if (i == 17 || i == 1) {
            z2 = pageLeft();
        } else if (i == 66 || i == 2) {
            z2 = pageRight();
        }
        if (z2) {
            playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i));
        }
        return z2;
    }

    private android.graphics.Rect getChildRectInPagerCoordinates(android.graphics.Rect rect, android.view.View view) {
        if (rect == null) {
            rect = new android.graphics.Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        android.view.ViewParent parent = view.getParent();
        while ((parent instanceof android.view.ViewGroup) && parent != this) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) parent;
            rect.left += viewGroup.getLeft();
            rect.right += viewGroup.getRight();
            rect.top += viewGroup.getTop();
            rect.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect;
    }

    boolean pageLeft() {
        return setCurrentItemInternal(this.mCurItem + this.mLeftIncr, true, false);
    }

    boolean pageRight() {
        return setCurrentItemInternal(this.mCurItem - this.mLeftIncr, true, false);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i == 0) {
            this.mLeftIncr = -1;
        } else {
            this.mLeftIncr = 1;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i, int i2) {
        com.android.internal.widget.ViewPager.ItemInfo infoForChild;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                android.view.View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                    childAt.addFocusables(arrayList, i, i2);
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if (((i2 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
            arrayList.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(java.util.ArrayList<android.view.View> arrayList) {
        com.android.internal.widget.ViewPager.ItemInfo infoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, android.graphics.Rect rect) {
        int i2;
        int i3;
        int i4;
        com.android.internal.widget.ViewPager.ItemInfo infoForChild;
        int childCount = getChildCount();
        if ((i & 2) != 0) {
            i3 = childCount;
            i2 = 0;
            i4 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
            i4 = -1;
        }
        while (i2 != i3) {
            android.view.View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new com.android.internal.widget.ViewPager.LayoutParams();
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof com.android.internal.widget.ViewPager.LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new com.android.internal.widget.ViewPager.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(com.android.internal.widget.ViewPager.class.getName());
        accessibilityEvent.setScrollable(canScroll());
        if (accessibilityEvent.getEventType() == 4096 && this.mAdapter != null) {
            accessibilityEvent.setItemCount(this.mAdapter.getCount());
            accessibilityEvent.setFromIndex(this.mCurItem);
            accessibilityEvent.setToIndex(this.mCurItem);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(com.android.internal.widget.ViewPager.class.getName());
        accessibilityNodeInfo.setScrollable(canScroll());
        if (canScrollHorizontally(1)) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
        }
        if (canScrollHorizontally(-1)) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        switch (i) {
            case 4096:
            case 16908347:
                if (!canScrollHorizontally(1)) {
                    return false;
                }
                setCurrentItem(this.mCurItem + 1);
                return true;
            case 8192:
            case 16908345:
                if (!canScrollHorizontally(-1)) {
                    return false;
                }
                setCurrentItem(this.mCurItem - 1);
                return true;
            default:
                return false;
        }
    }

    private boolean canScroll() {
        return this.mAdapter != null && this.mAdapter.getCount() > 1;
    }

    private class PagerObserver extends android.database.DataSetObserver {
        private PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            com.android.internal.widget.ViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            com.android.internal.widget.ViewPager.this.dataSetChanged();
        }
    }

    static class ViewPositionComparator implements java.util.Comparator<android.view.View> {
        ViewPositionComparator() {
        }

        @Override // java.util.Comparator
        public int compare(android.view.View view, android.view.View view2) {
            com.android.internal.widget.ViewPager.LayoutParams layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) view.getLayoutParams();
            com.android.internal.widget.ViewPager.LayoutParams layoutParams2 = (com.android.internal.widget.ViewPager.LayoutParams) view2.getLayoutParams();
            if (layoutParams.isDecor != layoutParams2.isDecor) {
                return layoutParams.isDecor ? 1 : -1;
            }
            return layoutParams.position - layoutParams2.position;
        }
    }
}
