package com.android.internal.widget;

/* loaded from: classes5.dex */
public class RecyclerView extends android.view.ViewGroup implements com.android.internal.widget.ScrollingView, com.android.internal.widget.NestedScrollingChild {
    static final boolean DEBUG = false;
    static final boolean DISPATCH_TEMP_DETACH = false;
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    static final int MAX_SCROLL_DURATION = 2000;
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final java.lang.String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final java.lang.String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final java.lang.String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final java.lang.String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final java.lang.String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final java.lang.String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final java.lang.String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final java.lang.String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final java.lang.String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int VERTICAL = 1;
    com.android.internal.widget.RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private com.android.internal.widget.RecyclerView.OnItemTouchListener mActiveOnItemTouchListener;
    com.android.internal.widget.RecyclerView.Adapter mAdapter;
    com.android.internal.widget.AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private android.widget.EdgeEffect mBottomGlow;
    private com.android.internal.widget.RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback;
    com.android.internal.widget.ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout;
    private int mDispatchScrollCounter;
    private int mEatRequestLayout;
    private int mEatenAccessibilityChangeFlags;
    boolean mFirstLayoutComplete;
    com.android.internal.widget.GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    boolean mIsAttached;
    com.android.internal.widget.RecyclerView.ItemAnimator mItemAnimator;
    private com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private java.lang.Runnable mItemAnimatorRunner;
    final java.util.ArrayList<com.android.internal.widget.RecyclerView.ItemDecoration> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    com.android.internal.widget.RecyclerView.LayoutManager mLayout;
    boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter;
    boolean mLayoutRequestEaten;
    private android.widget.EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final com.android.internal.widget.RecyclerView.RecyclerViewDataObserver mObserver;
    private java.util.List<com.android.internal.widget.RecyclerView.OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private com.android.internal.widget.RecyclerView.OnFlingListener mOnFlingListener;
    private final java.util.ArrayList<com.android.internal.widget.RecyclerView.OnItemTouchListener> mOnItemTouchListeners;
    final java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> mPendingAccessibilityImportanceChange;
    private com.android.internal.widget.RecyclerView.SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    com.android.internal.widget.GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final com.android.internal.widget.RecyclerView.Recycler mRecycler;
    com.android.internal.widget.RecyclerView.RecyclerListener mRecyclerListener;
    private android.widget.EdgeEffect mRightGlow;
    private final int[] mScrollConsumed;
    private float mScrollFactor;
    private com.android.internal.widget.RecyclerView.OnScrollListener mScrollListener;
    private java.util.List<com.android.internal.widget.RecyclerView.OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    final com.android.internal.widget.RecyclerView.State mState;
    final android.graphics.Rect mTempRect;
    private final android.graphics.Rect mTempRect2;
    final android.graphics.RectF mTempRectF;
    private android.widget.EdgeEffect mTopGlow;
    private int mTouchSlop;
    final java.lang.Runnable mUpdateChildViewsRunnable;
    private android.view.VelocityTracker mVelocityTracker;
    final com.android.internal.widget.RecyclerView.ViewFlinger mViewFlinger;
    private final com.android.internal.widget.ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final com.android.internal.widget.ViewInfoStore mViewInfoStore;
    private static final int[] NESTED_SCROLLING_ATTRS = {16843830};
    private static final int[] CLIP_TO_PADDING_ATTR = {16842987};
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST = false;
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = true;
    static final boolean POST_UPDATES_ON_ANIMATION = true;
    private static final boolean ALLOW_THREAD_GAP_WORK = true;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION = false;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD = false;
    private static final java.lang.Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = {android.content.Context.class, android.util.AttributeSet.class, java.lang.Integer.TYPE, java.lang.Integer.TYPE};
    static final android.view.animation.Interpolator sQuinticInterpolator = new android.view.animation.Interpolator() { // from class: com.android.internal.widget.RecyclerView.3
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i, int i2);
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(android.view.View view);

        void onChildViewDetachedFromWindow(android.view.View view);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int i, int i2);
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(com.android.internal.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(com.android.internal.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent);
    }

    public interface RecyclerListener {
        void onViewRecycled(com.android.internal.widget.RecyclerView.ViewHolder viewHolder);
    }

    public static abstract class ViewCacheExtension {
        public abstract android.view.View getViewForPositionAndType(com.android.internal.widget.RecyclerView.Recycler recycler, int i, int i2);
    }

    public RecyclerView(android.content.Context context) {
        this(context, null);
    }

    public RecyclerView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        boolean z;
        this.mObserver = new com.android.internal.widget.RecyclerView.RecyclerViewDataObserver();
        this.mRecycler = new com.android.internal.widget.RecyclerView.Recycler();
        this.mViewInfoStore = new com.android.internal.widget.ViewInfoStore();
        this.mUpdateChildViewsRunnable = new java.lang.Runnable() { // from class: com.android.internal.widget.RecyclerView.1
            @Override // java.lang.Runnable
            public void run() {
                if (!com.android.internal.widget.RecyclerView.this.mFirstLayoutComplete || com.android.internal.widget.RecyclerView.this.isLayoutRequested()) {
                    return;
                }
                if (!com.android.internal.widget.RecyclerView.this.mIsAttached) {
                    com.android.internal.widget.RecyclerView.this.requestLayout();
                } else if (com.android.internal.widget.RecyclerView.this.mLayoutFrozen) {
                    com.android.internal.widget.RecyclerView.this.mLayoutRequestEaten = true;
                } else {
                    com.android.internal.widget.RecyclerView.this.consumePendingUpdateOperations();
                }
            }
        };
        this.mTempRect = new android.graphics.Rect();
        this.mTempRect2 = new android.graphics.Rect();
        this.mTempRectF = new android.graphics.RectF();
        this.mItemDecorations = new java.util.ArrayList<>();
        this.mOnItemTouchListeners = new java.util.ArrayList<>();
        this.mEatRequestLayout = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mItemAnimator = new com.android.internal.widget.DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScrollFactor = Float.MIN_VALUE;
        boolean z2 = true;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new com.android.internal.widget.RecyclerView.ViewFlinger();
        this.mPrefetchRegistry = ALLOW_THREAD_GAP_WORK ? new com.android.internal.widget.GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mState = new com.android.internal.widget.RecyclerView.State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new com.android.internal.widget.RecyclerView.ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mPendingAccessibilityImportanceChange = new java.util.ArrayList();
        this.mItemAnimatorRunner = new java.lang.Runnable() { // from class: com.android.internal.widget.RecyclerView.2
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.internal.widget.RecyclerView.this.mItemAnimator != null) {
                    com.android.internal.widget.RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                com.android.internal.widget.RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new com.android.internal.widget.ViewInfoStore.ProcessCallback() { // from class: com.android.internal.widget.RecyclerView.4
            @Override // com.android.internal.widget.ViewInfoStore.ProcessCallback
            public void processDisappeared(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                com.android.internal.widget.RecyclerView.this.mRecycler.unscrapView(viewHolder);
                com.android.internal.widget.RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override // com.android.internal.widget.ViewInfoStore.ProcessCallback
            public void processAppeared(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                com.android.internal.widget.RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override // com.android.internal.widget.ViewInfoStore.ProcessCallback
            public void processPersistent(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (com.android.internal.widget.RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (com.android.internal.widget.RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) {
                        com.android.internal.widget.RecyclerView.this.postAnimationRunner();
                    }
                } else if (com.android.internal.widget.RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                    com.android.internal.widget.RecyclerView.this.postAnimationRunner();
                }
            }

            @Override // com.android.internal.widget.ViewInfoStore.ProcessCallback
            public void unused(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
                com.android.internal.widget.RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, com.android.internal.widget.RecyclerView.this.mRecycler);
            }
        };
        if (attributeSet != null) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, CLIP_TO_PADDING_ATTR, i, 0);
            this.mClipToPadding = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        } else {
            this.mClipToPadding = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        if (getOverScrollMode() != 2) {
            z = false;
        } else {
            z = true;
        }
        setWillNotDraw(z);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        initAdapterManager();
        initChildrenHelper();
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) getContext().getSystemService(android.content.Context.ACCESSIBILITY_SERVICE);
        setAccessibilityDelegateCompat(new com.android.internal.widget.RecyclerViewAccessibilityDelegate(this));
        if (attributeSet != null) {
            android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RecyclerView, i, 0);
            java.lang.String string = obtainStyledAttributes2.getString(2);
            if (obtainStyledAttributes2.getInt(1, -1) == -1) {
                setDescendantFocusability(262144);
            }
            obtainStyledAttributes2.recycle();
            createLayoutManager(context, string, attributeSet, i, 0);
            android.content.res.TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, i, 0);
            z2 = obtainStyledAttributes3.getBoolean(0, true);
            obtainStyledAttributes3.recycle();
        } else {
            setDescendantFocusability(262144);
        }
        context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.EdgeEffect).recycle();
        setNestedScrollingEnabled(z2);
    }

    public com.android.internal.widget.RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegateCompat(com.android.internal.widget.RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    private void createLayoutManager(android.content.Context context, java.lang.String str, android.util.AttributeSet attributeSet, int i, int i2) {
        java.lang.ClassLoader classLoader;
        java.lang.reflect.Constructor constructor;
        java.lang.Object[] objArr;
        if (str != null) {
            java.lang.String trim = str.trim();
            if (trim.length() != 0) {
                java.lang.String fullClassName = getFullClassName(context, trim);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    java.lang.Class<? extends U> asSubclass = classLoader.loadClass(fullClassName).asSubclass(com.android.internal.widget.RecyclerView.LayoutManager.class);
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        objArr = new java.lang.Object[]{context, attributeSet, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)};
                    } catch (java.lang.NoSuchMethodException e) {
                        try {
                            constructor = asSubclass.getConstructor(new java.lang.Class[0]);
                            objArr = null;
                        } catch (java.lang.NoSuchMethodException e2) {
                            e2.initCause(e);
                            throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + fullClassName, e2);
                        }
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((com.android.internal.widget.RecyclerView.LayoutManager) constructor.newInstance(objArr));
                } catch (java.lang.ClassCastException e3) {
                    throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + fullClassName, e3);
                } catch (java.lang.ClassNotFoundException e4) {
                    throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + fullClassName, e4);
                } catch (java.lang.IllegalAccessException e5) {
                    throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + fullClassName, e5);
                } catch (java.lang.InstantiationException e6) {
                    throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + fullClassName, e6);
                } catch (java.lang.reflect.InvocationTargetException e7) {
                    throw new java.lang.IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + fullClassName, e7);
                }
            }
        }
    }

    private java.lang.String getFullClassName(android.content.Context context, java.lang.String str) {
        if (str.charAt(0) == '.') {
            return context.getPackageName() + str;
        }
        if (str.contains(android.media.MediaMetrics.SEPARATOR)) {
            return str;
        }
        return com.android.internal.widget.RecyclerView.class.getPackage().getName() + '.' + str;
    }

    private void initChildrenHelper() {
        this.mChildHelper = new com.android.internal.widget.ChildHelper(new com.android.internal.widget.ChildHelper.Callback() { // from class: com.android.internal.widget.RecyclerView.5
            @Override // com.android.internal.widget.ChildHelper.Callback
            public int getChildCount() {
                return com.android.internal.widget.RecyclerView.this.getChildCount();
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void addView(android.view.View view, int i) {
                com.android.internal.widget.RecyclerView.this.addView(view, i);
                com.android.internal.widget.RecyclerView.this.dispatchChildAttached(view);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public int indexOfChild(android.view.View view) {
                return com.android.internal.widget.RecyclerView.this.indexOfChild(view);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void removeViewAt(int i) {
                android.view.View childAt = com.android.internal.widget.RecyclerView.this.getChildAt(i);
                if (childAt != null) {
                    com.android.internal.widget.RecyclerView.this.dispatchChildDetached(childAt);
                }
                com.android.internal.widget.RecyclerView.this.removeViewAt(i);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public android.view.View getChildAt(int i) {
                return com.android.internal.widget.RecyclerView.this.getChildAt(i);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void removeAllViews() {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    com.android.internal.widget.RecyclerView.this.dispatchChildDetached(getChildAt(i));
                }
                com.android.internal.widget.RecyclerView.this.removeAllViews();
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public com.android.internal.widget.RecyclerView.ViewHolder getChildViewHolder(android.view.View view) {
                return com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void attachViewToParent(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    if (!childViewHolderInt.isTmpDetached() && !childViewHolderInt.shouldIgnore()) {
                        throw new java.lang.IllegalArgumentException("Called attach on a child which is not detached: " + childViewHolderInt);
                    }
                    childViewHolderInt.clearTmpDetachFlag();
                }
                com.android.internal.widget.RecyclerView.this.attachViewToParent(view, i, layoutParams);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void detachViewFromParent(int i) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt;
                android.view.View childAt = getChildAt(i);
                if (childAt != null && (childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(childAt)) != null) {
                    if (childViewHolderInt.isTmpDetached() && !childViewHolderInt.shouldIgnore()) {
                        throw new java.lang.IllegalArgumentException("called detach on an already detached child " + childViewHolderInt);
                    }
                    childViewHolderInt.addFlags(256);
                }
                com.android.internal.widget.RecyclerView.this.detachViewFromParent(i);
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void onEnteredHiddenState(android.view.View view) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    childViewHolderInt.onEnteredHiddenState(com.android.internal.widget.RecyclerView.this);
                }
            }

            @Override // com.android.internal.widget.ChildHelper.Callback
            public void onLeftHiddenState(android.view.View view) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    childViewHolderInt.onLeftHiddenState(com.android.internal.widget.RecyclerView.this);
                }
            }
        });
    }

    void initAdapterManager() {
        this.mAdapterHelper = new com.android.internal.widget.AdapterHelper(new com.android.internal.widget.AdapterHelper.Callback() { // from class: com.android.internal.widget.RecyclerView.6
            @Override // com.android.internal.widget.AdapterHelper.Callback
            public com.android.internal.widget.RecyclerView.ViewHolder findViewHolder(int i) {
                com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForPosition = com.android.internal.widget.RecyclerView.this.findViewHolderForPosition(i, true);
                if (findViewHolderForPosition == null || com.android.internal.widget.RecyclerView.this.mChildHelper.isHidden(findViewHolderForPosition.itemView)) {
                    return null;
                }
                return findViewHolderForPosition;
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void offsetPositionsForRemovingInvisible(int i, int i2) {
                com.android.internal.widget.RecyclerView.this.offsetPositionRecordsForRemove(i, i2, true);
                com.android.internal.widget.RecyclerView.this.mItemsAddedOrRemoved = true;
                com.android.internal.widget.RecyclerView.this.mState.mDeletedInvisibleItemCountSincePreviousLayout += i2;
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2) {
                com.android.internal.widget.RecyclerView.this.offsetPositionRecordsForRemove(i, i2, false);
                com.android.internal.widget.RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void markViewHoldersUpdated(int i, int i2, java.lang.Object obj) {
                com.android.internal.widget.RecyclerView.this.viewRangeUpdate(i, i2, obj);
                com.android.internal.widget.RecyclerView.this.mItemsChanged = true;
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void onDispatchFirstPass(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
                dispatchUpdate(updateOp);
            }

            void dispatchUpdate(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
                switch (updateOp.cmd) {
                    case 1:
                        com.android.internal.widget.RecyclerView.this.mLayout.onItemsAdded(com.android.internal.widget.RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        break;
                    case 2:
                        com.android.internal.widget.RecyclerView.this.mLayout.onItemsRemoved(com.android.internal.widget.RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        break;
                    case 4:
                        com.android.internal.widget.RecyclerView.this.mLayout.onItemsUpdated(com.android.internal.widget.RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                        break;
                    case 8:
                        com.android.internal.widget.RecyclerView.this.mLayout.onItemsMoved(com.android.internal.widget.RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
                        break;
                }
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void onDispatchSecondPass(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
                dispatchUpdate(updateOp);
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void offsetPositionsForAdd(int i, int i2) {
                com.android.internal.widget.RecyclerView.this.offsetPositionRecordsForInsert(i, i2);
                com.android.internal.widget.RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override // com.android.internal.widget.AdapterHelper.Callback
            public void offsetPositionsForMove(int i, int i2) {
                com.android.internal.widget.RecyclerView.this.offsetPositionRecordsForMove(i, i2);
                com.android.internal.widget.RecyclerView.this.mItemsAddedOrRemoved = true;
            }
        });
    }

    public void setHasFixedSize(boolean z) {
        this.mHasFixedSize = z;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean z) {
        if (z != this.mClipToPadding) {
            invalidateGlows();
        }
        this.mClipToPadding = z;
        super.setClipToPadding(z);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public void setScrollingTouchSlop(int i) {
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(getContext());
        switch (i) {
            case 0:
                break;
            case 1:
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            default:
                android.util.Log.w(TAG, "setScrollingTouchSlop(): bad argument constant " + i + "; using default value");
                break;
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void swapAdapter(com.android.internal.widget.RecyclerView.Adapter adapter, boolean z) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, true, z);
        setDataSetChangedAfterLayout();
        requestLayout();
    }

    public void setAdapter(com.android.internal.widget.RecyclerView.Adapter adapter) {
        setLayoutFrozen(false);
        setAdapterInternal(adapter, false, true);
        requestLayout();
    }

    void removeAndRecycleViews() {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        if (this.mLayout != null) {
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    private void setAdapterInternal(com.android.internal.widget.RecyclerView.Adapter adapter, boolean z, boolean z2) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!z || z2) {
            removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        com.android.internal.widget.RecyclerView.Adapter adapter2 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        if (this.mLayout != null) {
            this.mLayout.onAdapterChanged(adapter2, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(adapter2, this.mAdapter, z);
        this.mState.mStructureChanged = true;
        markKnownViewsInvalid();
    }

    public com.android.internal.widget.RecyclerView.Adapter getAdapter() {
        return this.mAdapter;
    }

    public void setRecyclerListener(com.android.internal.widget.RecyclerView.RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    @Override // android.view.View
    public int getBaseline() {
        if (this.mLayout != null) {
            return this.mLayout.getBaseline();
        }
        return super.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(com.android.internal.widget.RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new java.util.ArrayList();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void removeOnChildAttachStateChangeListener(com.android.internal.widget.RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            return;
        }
        this.mOnChildAttachStateListeners.remove(onChildAttachStateChangeListener);
    }

    public void clearOnChildAttachStateChangeListeners() {
        if (this.mOnChildAttachStateListeners != null) {
            this.mOnChildAttachStateListeners.clear();
        }
    }

    public void setLayoutManager(com.android.internal.widget.RecyclerView.LayoutManager layoutManager) {
        if (layoutManager == this.mLayout) {
            return;
        }
        stopScroll();
        if (this.mLayout != null) {
            if (this.mItemAnimator != null) {
                this.mItemAnimator.endAnimations();
            }
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            this.mRecycler.clear();
            if (this.mIsAttached) {
                this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
            }
            this.mLayout.setRecyclerView(null);
            this.mLayout = null;
        } else {
            this.mRecycler.clear();
        }
        this.mChildHelper.removeAllViewsUnfiltered();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                throw new java.lang.IllegalArgumentException("LayoutManager " + layoutManager + " is already attached to a RecyclerView: " + layoutManager.mRecyclerView);
            }
            this.mLayout.setRecyclerView(this);
            if (this.mIsAttached) {
                this.mLayout.dispatchAttachedToWindow(this);
            }
        }
        this.mRecycler.updateViewCacheSize();
        requestLayout();
    }

    public void setOnFlingListener(com.android.internal.widget.RecyclerView.OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    public com.android.internal.widget.RecyclerView.OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    @Override // android.view.View
    protected android.os.Parcelable onSaveInstanceState() {
        com.android.internal.widget.RecyclerView.SavedState savedState = new com.android.internal.widget.RecyclerView.SavedState(super.onSaveInstanceState());
        if (this.mPendingSavedState != null) {
            savedState.copyFrom(this.mPendingSavedState);
        } else if (this.mLayout != null) {
            savedState.mLayoutState = this.mLayout.onSaveInstanceState();
        } else {
            savedState.mLayoutState = null;
        }
        return savedState;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (!(parcelable instanceof com.android.internal.widget.RecyclerView.SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        this.mPendingSavedState = (com.android.internal.widget.RecyclerView.SavedState) parcelable;
        super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
        if (this.mLayout != null && this.mPendingSavedState.mLayoutState != null) {
            this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    private void addAnimatingView(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        android.view.View view = viewHolder.itemView;
        boolean z = view.getParent() == this;
        this.mRecycler.unscrapView(getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
        } else if (!z) {
            this.mChildHelper.addView(view, true);
        } else {
            this.mChildHelper.hide(view);
        }
    }

    boolean removeAnimatingView(android.view.View view) {
        eatRequestLayout();
        boolean removeViewIfHidden = this.mChildHelper.removeViewIfHidden(view);
        if (removeViewIfHidden) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
            this.mRecycler.unscrapView(childViewHolderInt);
            this.mRecycler.recycleViewHolderInternal(childViewHolderInt);
        }
        resumeRequestLayout(!removeViewIfHidden);
        return removeViewIfHidden;
    }

    public com.android.internal.widget.RecyclerView.LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public com.android.internal.widget.RecyclerView.RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public void setRecycledViewPool(com.android.internal.widget.RecyclerView.RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    public void setViewCacheExtension(com.android.internal.widget.RecyclerView.ViewCacheExtension viewCacheExtension) {
        this.mRecycler.setViewCacheExtension(viewCacheExtension);
    }

    public void setItemViewCacheSize(int i) {
        this.mRecycler.setViewCacheSize(i);
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    void setScrollState(int i) {
        if (i == this.mScrollState) {
            return;
        }
        this.mScrollState = i;
        if (i != 2) {
            stopScrollersInternal();
        }
        dispatchOnScrollStateChanged(i);
    }

    public void addItemDecoration(com.android.internal.widget.RecyclerView.ItemDecoration itemDecoration, int i) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(i, itemDecoration);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void addItemDecoration(com.android.internal.widget.RecyclerView.ItemDecoration itemDecoration) {
        addItemDecoration(itemDecoration, -1);
    }

    public void removeItemDecoration(com.android.internal.widget.RecyclerView.ItemDecoration itemDecoration) {
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(com.android.internal.widget.RecyclerView.ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        setChildrenDrawingOrderEnabled(this.mChildDrawingOrderCallback != null);
    }

    @java.lang.Deprecated
    public void setOnScrollListener(com.android.internal.widget.RecyclerView.OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void addOnScrollListener(com.android.internal.widget.RecyclerView.OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new java.util.ArrayList();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    public void removeOnScrollListener(com.android.internal.widget.RecyclerView.OnScrollListener onScrollListener) {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.remove(onScrollListener);
        }
    }

    public void clearOnScrollListeners() {
        if (this.mScrollListeners != null) {
            this.mScrollListeners.clear();
        }
    }

    public void scrollToPosition(int i) {
        if (this.mLayoutFrozen) {
            return;
        }
        stopScroll();
        if (this.mLayout == null) {
            android.util.Log.e(TAG, "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            this.mLayout.scrollToPosition(i);
            awakenScrollBars();
        }
    }

    void jumpToPositionForSmoothScroller(int i) {
        if (this.mLayout == null) {
            return;
        }
        this.mLayout.scrollToPosition(i);
        awakenScrollBars();
    }

    public void smoothScrollToPosition(int i) {
        if (this.mLayoutFrozen) {
            return;
        }
        if (this.mLayout == null) {
            android.util.Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            this.mLayout.smoothScrollToPosition(this, this.mState, i);
        }
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        android.util.Log.w(TAG, "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        if (this.mLayout == null) {
            android.util.Log.e(TAG, "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) {
            return;
        }
        boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (canScrollHorizontally || canScrollVertically) {
            if (!canScrollHorizontally) {
                i = 0;
            }
            if (!canScrollVertically) {
                i2 = 0;
            }
            scrollByInternal(i, i2, null);
        }
    }

    void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            android.os.Trace.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            dispatchLayout();
            android.os.Trace.endSection();
            return;
        }
        if (!this.mAdapterHelper.hasPendingUpdates()) {
            return;
        }
        if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
            android.os.Trace.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
            eatRequestLayout();
            onEnterLayoutOrScroll();
            this.mAdapterHelper.preProcess();
            if (!this.mLayoutRequestEaten) {
                if (hasUpdatedView()) {
                    dispatchLayout();
                } else {
                    this.mAdapterHelper.consumePostponedUpdates();
                }
            }
            resumeRequestLayout(true);
            onExitLayoutOrScroll();
            android.os.Trace.endSection();
            return;
        }
        if (this.mAdapterHelper.hasPendingUpdates()) {
            android.os.Trace.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            dispatchLayout();
            android.os.Trace.endSection();
        }
    }

    private boolean hasUpdatedView() {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    boolean scrollByInternal(int i, int i2, android.view.MotionEvent motionEvent) {
        int i3;
        int i4;
        int i5;
        int i6;
        consumePendingUpdateOperations();
        if (this.mAdapter == null) {
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
        } else {
            eatRequestLayout();
            onEnterLayoutOrScroll();
            android.os.Trace.beginSection(TRACE_SCROLL_TAG);
            if (i == 0) {
                i3 = 0;
                i4 = 0;
            } else {
                i3 = this.mLayout.scrollHorizontallyBy(i, this.mRecycler, this.mState);
                i4 = i - i3;
            }
            if (i2 == 0) {
                i5 = 0;
                i6 = 0;
            } else {
                i5 = this.mLayout.scrollVerticallyBy(i2, this.mRecycler, this.mState);
                i6 = i2 - i5;
            }
            android.os.Trace.endSection();
            repositionShadowingViews();
            onExitLayoutOrScroll();
            resumeRequestLayout(false);
        }
        if (!this.mItemDecorations.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll(i3, i5, i4, i6, this.mScrollOffset)) {
            this.mLastTouchX -= this.mScrollOffset[0];
            this.mLastTouchY -= this.mScrollOffset[1];
            if (motionEvent != null) {
                motionEvent.offsetLocation(this.mScrollOffset[0], this.mScrollOffset[1]);
            }
            int[] iArr = this.mNestedOffsets;
            iArr[0] = iArr[0] + this.mScrollOffset[0];
            int[] iArr2 = this.mNestedOffsets;
            iArr2[1] = iArr2[1] + this.mScrollOffset[1];
        } else if (getOverScrollMode() != 2) {
            if (motionEvent != null) {
                pullGlows(motionEvent.getX(), i4, motionEvent.getY(), i6);
            }
            considerReleasingGlowsOnScroll(i, i2);
        }
        if (i3 != 0 || i5 != 0) {
            dispatchOnScrolled(i3, i5);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        return (i3 == 0 && i5 == 0) ? false : true;
    }

    @Override // android.view.View
    public int computeHorizontalScrollOffset() {
        if (this.mLayout != null && this.mLayout.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeHorizontalScrollExtent() {
        if (this.mLayout != null && this.mLayout.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() {
        if (this.mLayout != null && this.mLayout.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() {
        if (this.mLayout != null && this.mLayout.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() {
        if (this.mLayout != null && this.mLayout.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return 0;
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() {
        if (this.mLayout != null && this.mLayout.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return 0;
    }

    void eatRequestLayout() {
        this.mEatRequestLayout++;
        if (this.mEatRequestLayout == 1 && !this.mLayoutFrozen) {
            this.mLayoutRequestEaten = false;
        }
    }

    void resumeRequestLayout(boolean z) {
        if (this.mEatRequestLayout < 1) {
            this.mEatRequestLayout = 1;
        }
        if (!z) {
            this.mLayoutRequestEaten = false;
        }
        if (this.mEatRequestLayout == 1) {
            if (z && this.mLayoutRequestEaten && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutRequestEaten = false;
            }
        }
        this.mEatRequestLayout--;
    }

    public void setLayoutFrozen(boolean z) {
        if (z != this.mLayoutFrozen) {
            assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if (!z) {
                this.mLayoutFrozen = false;
                if (this.mLayoutRequestEaten && this.mLayout != null && this.mAdapter != null) {
                    requestLayout();
                }
                this.mLayoutRequestEaten = false;
                return;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            onTouchEvent(android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
            this.mLayoutFrozen = true;
            this.mIgnoreMotionEventTillDown = true;
            stopScroll();
        }
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy(i, i2, null);
    }

    public void smoothScrollBy(int i, int i2, android.view.animation.Interpolator interpolator) {
        if (this.mLayout == null) {
            android.util.Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutFrozen) {
            return;
        }
        if (!this.mLayout.canScrollHorizontally()) {
            i = 0;
        }
        if (!this.mLayout.canScrollVertically()) {
            i2 = 0;
        }
        if (i != 0 || i2 != 0) {
            this.mViewFlinger.smoothScrollBy(i, i2, interpolator);
        }
    }

    public boolean fling(int i, int i2) {
        boolean z;
        if (this.mLayout == null) {
            android.util.Log.e(TAG, "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        }
        if (this.mLayoutFrozen) {
            return false;
        }
        boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (!canScrollHorizontally || java.lang.Math.abs(i) < this.mMinFlingVelocity) {
            i = 0;
        }
        if (!canScrollVertically || java.lang.Math.abs(i2) < this.mMinFlingVelocity) {
            i2 = 0;
        }
        if (i == 0 && i2 == 0) {
            return false;
        }
        float f = i;
        float f2 = i2;
        if (!dispatchNestedPreFling(f, f2)) {
            if (this.mLayout.getChildCount() <= 0) {
                z = false;
            } else {
                android.view.View childAt = this.mLayout.getChildAt(0);
                android.view.View childAt2 = this.mLayout.getChildAt(this.mLayout.getChildCount() - 1);
                if (i2 >= 0) {
                    z = false;
                } else {
                    z = getChildAdapterPosition(childAt) > 0 || childAt.getTop() < getPaddingTop();
                }
                if (i2 > 0) {
                    z = getChildAdapterPosition(childAt2) < this.mAdapter.getItemCount() - 1 || childAt2.getBottom() > getHeight() - getPaddingBottom();
                }
            }
            dispatchNestedFling(f, f2, z);
            if (this.mOnFlingListener != null && this.mOnFlingListener.onFling(i, i2)) {
                return true;
            }
            if (canScrollHorizontally || canScrollVertically) {
                this.mViewFlinger.fling(java.lang.Math.max(-this.mMaxFlingVelocity, java.lang.Math.min(i, this.mMaxFlingVelocity)), java.lang.Math.max(-this.mMaxFlingVelocity, java.lang.Math.min(i2, this.mMaxFlingVelocity)));
                return true;
            }
        }
        return false;
    }

    public void stopScroll() {
        setScrollState(0);
        stopScrollersInternal();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        if (this.mLayout != null) {
            this.mLayout.stopSmoothScroller();
        }
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    private void pullGlows(float f, float f2, float f3, float f4) {
        boolean z;
        boolean z2 = true;
        if (f2 < 0.0f) {
            ensureLeftGlow();
            this.mLeftGlow.onPull((-f2) / getWidth(), 1.0f - (f3 / getHeight()));
            z = true;
        } else if (f2 <= 0.0f) {
            z = false;
        } else {
            ensureRightGlow();
            this.mRightGlow.onPull(f2 / getWidth(), f3 / getHeight());
            z = true;
        }
        if (f4 < 0.0f) {
            ensureTopGlow();
            this.mTopGlow.onPull((-f4) / getHeight(), f / getWidth());
        } else if (f4 <= 0.0f) {
            z2 = z;
        } else {
            ensureBottomGlow();
            this.mBottomGlow.onPull(f4 / getHeight(), 1.0f - (f / getWidth()));
        }
        if (z2 || f2 != 0.0f || f4 != 0.0f) {
            postInvalidateOnAnimation();
        }
    }

    private void releaseGlows() {
        boolean z;
        boolean z2 = true;
        if (this.mLeftGlow == null) {
            z = false;
        } else {
            this.mLeftGlow.onRelease();
            z = true;
        }
        if (this.mTopGlow != null) {
            this.mTopGlow.onRelease();
            z = true;
        }
        if (this.mRightGlow != null) {
            this.mRightGlow.onRelease();
            z = true;
        }
        if (this.mBottomGlow == null) {
            z2 = z;
        } else {
            this.mBottomGlow.onRelease();
        }
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

    void considerReleasingGlowsOnScroll(int i, int i2) {
        boolean z;
        boolean z2 = true;
        if (this.mLeftGlow != null && !this.mLeftGlow.isFinished() && i > 0) {
            this.mLeftGlow.onRelease();
            z = true;
        } else {
            z = false;
        }
        if (this.mRightGlow != null && !this.mRightGlow.isFinished() && i < 0) {
            this.mRightGlow.onRelease();
            z = true;
        }
        if (this.mTopGlow != null && !this.mTopGlow.isFinished() && i2 > 0) {
            this.mTopGlow.onRelease();
            z = true;
        }
        if (this.mBottomGlow != null && !this.mBottomGlow.isFinished() && i2 < 0) {
            this.mBottomGlow.onRelease();
        } else {
            z2 = z;
        }
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

    void absorbGlows(int i, int i2) {
        if (i < 0) {
            ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-i);
        } else if (i > 0) {
            ensureRightGlow();
            this.mRightGlow.onAbsorb(i);
        }
        if (i2 < 0) {
            ensureTopGlow();
            this.mTopGlow.onAbsorb(-i2);
        } else if (i2 > 0) {
            ensureBottomGlow();
            this.mBottomGlow.onAbsorb(i2);
        }
        if (i != 0 || i2 != 0) {
            postInvalidateOnAnimation();
        }
    }

    void ensureLeftGlow() {
        if (this.mLeftGlow != null) {
            return;
        }
        this.mLeftGlow = new android.widget.EdgeEffect(getContext());
        if (this.mClipToPadding) {
            this.mLeftGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    void ensureRightGlow() {
        if (this.mRightGlow != null) {
            return;
        }
        this.mRightGlow = new android.widget.EdgeEffect(getContext());
        if (this.mClipToPadding) {
            this.mRightGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    void ensureTopGlow() {
        if (this.mTopGlow != null) {
            return;
        }
        this.mTopGlow = new android.widget.EdgeEffect(getContext());
        if (this.mClipToPadding) {
            this.mTopGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    void ensureBottomGlow() {
        if (this.mBottomGlow != null) {
            return;
        }
        this.mBottomGlow = new android.widget.EdgeEffect(getContext());
        if (this.mClipToPadding) {
            this.mBottomGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public android.view.View focusSearch(android.view.View view, int i) {
        android.view.View view2;
        boolean z;
        android.view.View onInterceptFocusSearch = this.mLayout.onInterceptFocusSearch(view, i);
        if (onInterceptFocusSearch != null) {
            return onInterceptFocusSearch;
        }
        boolean z2 = (this.mAdapter == null || this.mLayout == null || isComputingLayout() || this.mLayoutFrozen) ? false : true;
        android.view.FocusFinder focusFinder = android.view.FocusFinder.getInstance();
        if (z2 && (i == 2 || i == 1)) {
            if (!this.mLayout.canScrollVertically()) {
                z = false;
            } else {
                int i2 = i == 2 ? 130 : 33;
                z = focusFinder.findNextFocus(this, view, i2) == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    i = i2;
                }
            }
            if (!z && this.mLayout.canScrollHorizontally()) {
                int i3 = (this.mLayout.getLayoutDirection() == 1) ^ (i == 2) ? 66 : 17;
                boolean z3 = focusFinder.findNextFocus(this, view, i3) == null;
                if (!FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    z = z3;
                } else {
                    i = i3;
                    z = z3;
                }
            }
            if (z) {
                consumePendingUpdateOperations();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                eatRequestLayout();
                this.mLayout.onFocusSearchFailed(view, i, this.mRecycler, this.mState);
                resumeRequestLayout(false);
            }
            view2 = focusFinder.findNextFocus(this, view, i);
        } else {
            android.view.View findNextFocus = focusFinder.findNextFocus(this, view, i);
            if (findNextFocus == null && z2) {
                consumePendingUpdateOperations();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                eatRequestLayout();
                view2 = this.mLayout.onFocusSearchFailed(view, i, this.mRecycler, this.mState);
                resumeRequestLayout(false);
            } else {
                view2 = findNextFocus;
            }
        }
        return isPreferredNextFocus(view, view2, i) ? view2 : super.focusSearch(view, i);
    }

    private boolean isPreferredNextFocus(android.view.View view, android.view.View view2, int i) {
        boolean z;
        boolean z2 = false;
        if (view2 == null || view2 == this) {
            return false;
        }
        if (view == null) {
            return true;
        }
        if (i == 2 || i == 1) {
            if (this.mLayout.getLayoutDirection() != 1) {
                z = false;
            } else {
                z = true;
            }
            if (i == 2) {
                z2 = true;
            }
            if (isPreferredNextFocusAbsolute(view, view2, z2 ^ z ? 66 : 17)) {
                return true;
            }
            if (i == 2) {
                return isPreferredNextFocusAbsolute(view, view2, 130);
            }
            return isPreferredNextFocusAbsolute(view, view2, 33);
        }
        return isPreferredNextFocusAbsolute(view, view2, i);
    }

    private boolean isPreferredNextFocusAbsolute(android.view.View view, android.view.View view2, int i) {
        this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
        this.mTempRect2.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        offsetDescendantRectToMyCoords(view2, this.mTempRect2);
        switch (i) {
            case 17:
                return (this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left;
            case 33:
                return (this.mTempRect.bottom > this.mTempRect2.bottom || this.mTempRect.top >= this.mTempRect2.bottom) && this.mTempRect.top > this.mTempRect2.top;
            case 66:
                return (this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right;
            case 130:
                return (this.mTempRect.top < this.mTempRect2.top || this.mTempRect.bottom <= this.mTempRect2.top) && this.mTempRect.bottom < this.mTempRect2.bottom;
            default:
                throw new java.lang.IllegalArgumentException("direction must be absolute. received:" + i);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.mTempRect.set(0, 0, view2.getWidth(), view2.getHeight());
            android.view.ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            if (layoutParams instanceof com.android.internal.widget.RecyclerView.LayoutParams) {
                com.android.internal.widget.RecyclerView.LayoutParams layoutParams2 = (com.android.internal.widget.RecyclerView.LayoutParams) layoutParams;
                if (!layoutParams2.mInsetsDirty) {
                    android.graphics.Rect rect = layoutParams2.mDecorInsets;
                    this.mTempRect.left -= rect.left;
                    this.mTempRect.right += rect.right;
                    this.mTempRect.top -= rect.top;
                    this.mTempRect.bottom += rect.bottom;
                }
            }
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            offsetRectIntoDescendantCoords(view, this.mTempRect);
            requestChildRectangleOnScreen(view, this.mTempRect, !this.mFirstLayoutComplete);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, z);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i, int i2) {
        if (this.mLayout == null || !this.mLayout.onAddFocusables(this, arrayList, i, i2)) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, android.graphics.Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0050, code lost:
    
        if (r0 >= 30.0f) goto L22;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onAttachedToWindow() {
        float f;
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        boolean z = true;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || isLayoutRequested()) {
            z = false;
        }
        this.mFirstLayoutComplete = z;
        if (this.mLayout != null) {
            this.mLayout.dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker = com.android.internal.widget.GapWorker.sGapWorker.get();
            if (this.mGapWorker == null) {
                this.mGapWorker = new com.android.internal.widget.GapWorker();
                android.view.Display display = getDisplay();
                if (!isInEditMode() && display != null) {
                    f = display.getRefreshRate();
                }
                f = 60.0f;
                this.mGapWorker.mFrameIntervalNs = (long) (1.0E9f / f);
                com.android.internal.widget.GapWorker.sGapWorker.set(this.mGapWorker);
            }
            this.mGapWorker.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
        }
        stopScroll();
        this.mIsAttached = false;
        if (this.mLayout != null) {
            this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker.remove(this);
            this.mGapWorker = null;
        }
    }

    @Override // android.view.View
    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    void assertInLayoutOrScroll(java.lang.String str) {
        if (!isComputingLayout()) {
            if (str == null) {
                throw new java.lang.IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling");
            }
            throw new java.lang.IllegalStateException(str);
        }
    }

    void assertNotInLayoutOrScroll(java.lang.String str) {
        if (isComputingLayout()) {
            if (str == null) {
                throw new java.lang.IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling");
            }
            throw new java.lang.IllegalStateException(str);
        }
        if (this.mDispatchScrollCounter > 0) {
            android.util.Log.w(TAG, "Cannot call this method in a scroll callback. Scroll callbacks might be run during a measure & layout pass where you cannot change the RecyclerView data. Any method call that might change the structure of the RecyclerView or the adapter contents should be postponed to the next frame.", new java.lang.IllegalStateException(""));
        }
    }

    public void addOnItemTouchListener(com.android.internal.widget.RecyclerView.OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void removeOnItemTouchListener(com.android.internal.widget.RecyclerView.OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mActiveOnItemTouchListener == onItemTouchListener) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    private boolean dispatchOnItemTouchIntercept(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.widget.RecyclerView.OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent) && action != 3) {
                this.mActiveOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouch(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.mActiveOnItemTouchListener != null) {
            if (action == 0) {
                this.mActiveOnItemTouchListener = null;
            } else {
                this.mActiveOnItemTouchListener.onTouchEvent(this, motionEvent);
                if (action == 3 || action == 1) {
                    this.mActiveOnItemTouchListener = null;
                }
                return true;
            }
        }
        if (action != 0) {
            int size = this.mOnItemTouchListeners.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.widget.RecyclerView.OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
                if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent)) {
                    this.mActiveOnItemTouchListener = onItemTouchListener;
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int i;
        boolean z;
        if (this.mLayoutFrozen) {
            return false;
        }
        if (dispatchOnItemTouchIntercept(motionEvent)) {
            cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        switch (actionMasked) {
            case 0:
                if (this.mIgnoreMotionEventTillDown) {
                    this.mIgnoreMotionEventTillDown = false;
                }
                this.mScrollPointerId = motionEvent.getPointerId(0);
                int x = (int) (motionEvent.getX() + 0.5f);
                this.mLastTouchX = x;
                this.mInitialTouchX = x;
                int y = (int) (motionEvent.getY() + 0.5f);
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                if (stopGlowAnimations(motionEvent) || this.mScrollState == 2) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                }
                int[] iArr = this.mNestedOffsets;
                this.mNestedOffsets[1] = 0;
                iArr[0] = 0;
                if (!canScrollHorizontally) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (canScrollVertically) {
                    i |= 2;
                }
                startNestedScroll(i);
                break;
            case 1:
                this.mVelocityTracker.clear();
                stopNestedScroll();
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
                if (findPointerIndex >= 0) {
                    int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                    int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                    if (this.mScrollState != 1) {
                        int i2 = x2 - this.mInitialTouchX;
                        int i3 = y2 - this.mInitialTouchY;
                        if (canScrollHorizontally && java.lang.Math.abs(i2) > this.mTouchSlop) {
                            this.mLastTouchX = this.mInitialTouchX + (this.mTouchSlop * (i2 < 0 ? -1 : 1));
                            z = true;
                        } else {
                            z = false;
                        }
                        if (canScrollVertically && java.lang.Math.abs(i3) > this.mTouchSlop) {
                            this.mLastTouchY = this.mInitialTouchY + (this.mTouchSlop * (i3 >= 0 ? 1 : -1));
                            z = true;
                        }
                        if (z) {
                            setScrollState(1);
                            break;
                        }
                    }
                } else {
                    android.util.Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                break;
            case 3:
                cancelTouch();
                break;
            case 5:
                this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchX = x3;
                this.mInitialTouchX = x3;
                int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                this.mLastTouchY = y3;
                this.mInitialTouchY = y3;
                break;
            case 6:
                onPointerUp(motionEvent);
                break;
        }
        return this.mScrollState == 1;
    }

    private boolean stopGlowAnimations(android.view.MotionEvent motionEvent) {
        boolean z;
        if (this.mLeftGlow != null && this.mLeftGlow.getDistance() != 0.0f) {
            this.mLeftGlow.onPullDistance(0.0f, 1.0f - (motionEvent.getY() / getHeight()));
            z = true;
        } else {
            z = false;
        }
        if (this.mRightGlow != null && this.mRightGlow.getDistance() != 0.0f) {
            this.mRightGlow.onPullDistance(0.0f, motionEvent.getY() / getHeight());
            z = true;
        }
        if (this.mTopGlow != null && this.mTopGlow.getDistance() != 0.0f) {
            this.mTopGlow.onPullDistance(0.0f, motionEvent.getX() / getWidth());
            z = true;
        }
        if (this.mBottomGlow != null && this.mBottomGlow.getDistance() != 0.0f) {
            this.mBottomGlow.onPullDistance(0.0f, 1.0f - (motionEvent.getX() / getWidth()));
            return true;
        }
        return z;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int i;
        float f;
        float f2;
        boolean z;
        boolean z2 = false;
        if (this.mLayoutFrozen || this.mIgnoreMotionEventTillDown) {
            return false;
        }
        if (dispatchOnItemTouch(motionEvent)) {
            cancelTouch();
            return true;
        }
        if (this.mLayout == null) {
            return false;
        }
        boolean canScrollHorizontally = this.mLayout.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            int[] iArr = this.mNestedOffsets;
            this.mNestedOffsets[1] = 0;
            iArr[0] = 0;
        }
        obtain.offsetLocation(this.mNestedOffsets[0], this.mNestedOffsets[1]);
        switch (actionMasked) {
            case 0:
                this.mScrollPointerId = motionEvent.getPointerId(0);
                int x = (int) (motionEvent.getX() + 0.5f);
                this.mLastTouchX = x;
                this.mInitialTouchX = x;
                int y = (int) (motionEvent.getY() + 0.5f);
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                if (!canScrollHorizontally) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (canScrollVertically) {
                    i |= 2;
                }
                startNestedScroll(i);
                break;
            case 1:
                this.mVelocityTracker.addMovement(obtain);
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
                if (!canScrollHorizontally) {
                    f = 0.0f;
                } else {
                    f = -this.mVelocityTracker.getXVelocity(this.mScrollPointerId);
                }
                if (!canScrollVertically) {
                    f2 = 0.0f;
                } else {
                    f2 = -this.mVelocityTracker.getYVelocity(this.mScrollPointerId);
                }
                if ((f == 0.0f && f2 == 0.0f) || !fling((int) f, (int) f2)) {
                    setScrollState(0);
                }
                resetTouch();
                z2 = true;
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
                if (findPointerIndex >= 0) {
                    int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                    int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                    int i2 = this.mLastTouchX - x2;
                    int i3 = this.mLastTouchY - y2;
                    int releaseHorizontalGlow = i2 - releaseHorizontalGlow(i2, motionEvent.getY());
                    int releaseVerticalGlow = i3 - releaseVerticalGlow(i3, motionEvent.getX());
                    if (dispatchNestedPreScroll(releaseHorizontalGlow, releaseVerticalGlow, this.mScrollConsumed, this.mScrollOffset)) {
                        releaseHorizontalGlow -= this.mScrollConsumed[0];
                        releaseVerticalGlow -= this.mScrollConsumed[1];
                        obtain.offsetLocation(this.mScrollOffset[0], this.mScrollOffset[1]);
                        int[] iArr2 = this.mNestedOffsets;
                        iArr2[0] = iArr2[0] + this.mScrollOffset[0];
                        int[] iArr3 = this.mNestedOffsets;
                        iArr3[1] = iArr3[1] + this.mScrollOffset[1];
                    }
                    if (this.mScrollState != 1) {
                        if (canScrollHorizontally && java.lang.Math.abs(releaseHorizontalGlow) > this.mTouchSlop) {
                            if (releaseHorizontalGlow > 0) {
                                releaseHorizontalGlow -= this.mTouchSlop;
                            } else {
                                releaseHorizontalGlow += this.mTouchSlop;
                            }
                            z = true;
                        } else {
                            z = false;
                        }
                        if (canScrollVertically && java.lang.Math.abs(releaseVerticalGlow) > this.mTouchSlop) {
                            if (releaseVerticalGlow > 0) {
                                releaseVerticalGlow -= this.mTouchSlop;
                            } else {
                                releaseVerticalGlow += this.mTouchSlop;
                            }
                            z = true;
                        }
                        if (z) {
                            setScrollState(1);
                        }
                    }
                    if (this.mScrollState == 1) {
                        this.mLastTouchX = x2 - this.mScrollOffset[0];
                        this.mLastTouchY = y2 - this.mScrollOffset[1];
                        if (scrollByInternal(canScrollHorizontally ? releaseHorizontalGlow : 0, canScrollVertically ? releaseVerticalGlow : 0, obtain)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        if (this.mGapWorker != null && (releaseHorizontalGlow != 0 || releaseVerticalGlow != 0)) {
                            this.mGapWorker.postFromTraversal(this, releaseHorizontalGlow, releaseVerticalGlow);
                            break;
                        }
                    }
                } else {
                    android.util.Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    obtain.recycle();
                    return false;
                }
                break;
            case 3:
                cancelTouch();
                break;
            case 5:
                this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchX = x3;
                this.mInitialTouchX = x3;
                int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                this.mLastTouchY = y3;
                this.mInitialTouchY = y3;
                break;
            case 6:
                onPointerUp(motionEvent);
                break;
        }
        if (!z2) {
            this.mVelocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int releaseHorizontalGlow(int i, float f) {
        float onPullDistance;
        int round;
        float height = f / getHeight();
        float width = i / getWidth();
        float f2 = 0.0f;
        if (this.mLeftGlow != null && this.mLeftGlow.getDistance() != 0.0f) {
            onPullDistance = -this.mLeftGlow.onPullDistance(-width, 1.0f - height);
            if (this.mLeftGlow.getDistance() == 0.0f) {
                this.mLeftGlow.onRelease();
            }
        } else {
            if (this.mRightGlow != null && this.mRightGlow.getDistance() != 0.0f) {
                onPullDistance = this.mRightGlow.onPullDistance(width, height);
                if (this.mRightGlow.getDistance() == 0.0f) {
                    this.mRightGlow.onRelease();
                }
            }
            round = java.lang.Math.round(f2 * getWidth());
            if (round != 0) {
                invalidate();
            }
            return round;
        }
        f2 = onPullDistance;
        round = java.lang.Math.round(f2 * getWidth());
        if (round != 0) {
        }
        return round;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int releaseVerticalGlow(int i, float f) {
        float onPullDistance;
        int round;
        float width = f / getWidth();
        float height = i / getHeight();
        float f2 = 0.0f;
        if (this.mTopGlow != null && this.mTopGlow.getDistance() != 0.0f) {
            onPullDistance = -this.mTopGlow.onPullDistance(-height, width);
            if (this.mTopGlow.getDistance() == 0.0f) {
                this.mTopGlow.onRelease();
            }
        } else {
            if (this.mBottomGlow != null && this.mBottomGlow.getDistance() != 0.0f) {
                onPullDistance = this.mBottomGlow.onPullDistance(height, 1.0f - width);
                if (this.mBottomGlow.getDistance() == 0.0f) {
                    this.mBottomGlow.onRelease();
                }
            }
            round = java.lang.Math.round(f2 * getHeight());
            if (round != 0) {
                invalidate();
            }
            return round;
        }
        f2 = onPullDistance;
        round = java.lang.Math.round(f2 * getHeight());
        if (round != 0) {
        }
        return round;
    }

    private void resetTouch() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        stopNestedScroll();
        releaseGlows();
    }

    private void cancelTouch() {
        resetTouch();
        setScrollState(0);
    }

    private void onPointerUp(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(i);
            int x = (int) (motionEvent.getX(i) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY(i) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        float f;
        float f2;
        if (this.mLayout != null && !this.mLayoutFrozen && (motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            if (this.mLayout.canScrollVertically()) {
                f = -motionEvent.getAxisValue(9);
            } else {
                f = 0.0f;
            }
            if (this.mLayout.canScrollHorizontally()) {
                f2 = motionEvent.getAxisValue(10);
            } else {
                f2 = 0.0f;
            }
            if (f != 0.0f || f2 != 0.0f) {
                float scrollFactor = getScrollFactor();
                scrollByInternal((int) (f2 * scrollFactor), (int) (f * scrollFactor), motionEvent);
            }
        }
        return false;
    }

    private float getScrollFactor() {
        if (this.mScrollFactor == Float.MIN_VALUE) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            if (getContext().getTheme().resolveAttribute(16842829, typedValue, true)) {
                this.mScrollFactor = typedValue.getDimension(getContext().getResources().getDisplayMetrics());
            } else {
                return 0.0f;
            }
        }
        return this.mScrollFactor;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mLayout == null) {
            defaultOnMeasure(i, i2);
            return;
        }
        boolean z = false;
        if (this.mLayout.mAutoMeasure) {
            int mode = android.view.View.MeasureSpec.getMode(i);
            int mode2 = android.view.View.MeasureSpec.getMode(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            if (z || this.mAdapter == null) {
                return;
            }
            if (this.mState.mLayoutStep == 1) {
                dispatchLayoutStep1();
            }
            this.mLayout.setMeasureSpecs(i, i2);
            this.mState.mIsMeasuring = true;
            dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(i, i2);
            if (this.mLayout.shouldMeasureTwice()) {
                this.mLayout.setMeasureSpecs(android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                this.mState.mIsMeasuring = true;
                dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren(i, i2);
                return;
            }
            return;
        }
        if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            return;
        }
        if (this.mAdapterUpdateDuringMeasure) {
            eatRequestLayout();
            onEnterLayoutOrScroll();
            processAdapterUpdatesAndSetAnimationFlags();
            onExitLayoutOrScroll();
            if (this.mState.mRunPredictiveAnimations) {
                this.mState.mInPreLayout = true;
            } else {
                this.mAdapterHelper.consumeUpdatesInOnePass();
                this.mState.mInPreLayout = false;
            }
            this.mAdapterUpdateDuringMeasure = false;
            resumeRequestLayout(false);
        }
        if (this.mAdapter != null) {
            this.mState.mItemCount = this.mAdapter.getItemCount();
        } else {
            this.mState.mItemCount = 0;
        }
        eatRequestLayout();
        this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
        resumeRequestLayout(false);
        this.mState.mInPreLayout = false;
    }

    void defaultOnMeasure(int i, int i2) {
        setMeasuredDimension(com.android.internal.widget.RecyclerView.LayoutManager.chooseSize(i, getPaddingLeft() + getPaddingRight(), getMinimumWidth()), com.android.internal.widget.RecyclerView.LayoutManager.chooseSize(i2, getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            invalidateGlows();
        }
    }

    public void setItemAnimator(com.android.internal.widget.RecyclerView.ItemAnimator itemAnimator) {
        if (this.mItemAnimator != null) {
            this.mItemAnimator.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = itemAnimator;
        if (this.mItemAnimator != null) {
            this.mItemAnimator.setListener(this.mItemAnimatorListener);
        }
    }

    void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    void onExitLayoutOrScroll() {
        this.mLayoutOrScrollCounter--;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            dispatchContentChangedIfNecessary();
            dispatchPendingImportantForAccessibilityChanges();
        }
    }

    boolean isAccessibilityEnabled() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isEnabled();
    }

    private void dispatchContentChangedIfNecessary() {
        int i = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (i != 0 && isAccessibilityEnabled()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(i);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    boolean shouldDeferAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        int i;
        if (!isComputingLayout()) {
            return false;
        }
        if (accessibilityEvent == null) {
            i = 0;
        } else {
            i = accessibilityEvent.getContentChangeTypes();
        }
        this.mEatenAccessibilityChangeFlags |= i != 0 ? i : 0;
        return true;
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (shouldDeferAccessibilityEvent(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public com.android.internal.widget.RecyclerView.ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            postOnAnimation(this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            this.mLayout.onItemsChanged(this);
        }
        if (predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean z = false;
        boolean z2 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        this.mState.mRunSimpleAnimations = this.mFirstLayoutComplete && this.mItemAnimator != null && (this.mDataSetHasChangedAfterLayout || z2 || this.mLayout.mRequestedSimpleAnimations) && (!this.mDataSetHasChangedAfterLayout || this.mAdapter.hasStableIds());
        com.android.internal.widget.RecyclerView.State state = this.mState;
        if (this.mState.mRunSimpleAnimations && z2 && !this.mDataSetHasChangedAfterLayout && predictiveItemAnimationsEnabled()) {
            z = true;
        }
        state.mRunPredictiveAnimations = z;
    }

    void dispatchLayout() {
        if (this.mAdapter == null) {
            android.util.Log.e(TAG, "No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            android.util.Log.e(TAG, "No layout manager attached; skipping layout");
            return;
        }
        this.mState.mIsMeasuring = false;
        if (this.mState.mLayoutStep == 1) {
            dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            dispatchLayoutStep2();
        } else if (this.mAdapterHelper.hasUpdates() || this.mLayout.getWidth() != getWidth() || this.mLayout.getHeight() != getHeight()) {
            this.mLayout.setExactMeasureSpecsFrom(this);
            dispatchLayoutStep2();
        } else {
            this.mLayout.setExactMeasureSpecsFrom(this);
        }
        dispatchLayoutStep3();
    }

    private void saveFocusInfo() {
        android.view.View view;
        int adapterPosition;
        if (this.mPreserveFocusAfterLayout && hasFocus() && this.mAdapter != null) {
            view = getFocusedChild();
        } else {
            view = null;
        }
        com.android.internal.widget.RecyclerView.ViewHolder findContainingViewHolder = view != null ? findContainingViewHolder(view) : null;
        if (findContainingViewHolder == null) {
            resetFocusInfo();
            return;
        }
        this.mState.mFocusedItemId = this.mAdapter.hasStableIds() ? findContainingViewHolder.getItemId() : -1L;
        com.android.internal.widget.RecyclerView.State state = this.mState;
        if (this.mDataSetHasChangedAfterLayout) {
            adapterPosition = -1;
        } else {
            adapterPosition = findContainingViewHolder.isRemoved() ? findContainingViewHolder.mOldPosition : findContainingViewHolder.getAdapterPosition();
        }
        state.mFocusedItemPosition = adapterPosition;
        this.mState.mFocusedSubChildId = getDeepestFocusedViewWithId(findContainingViewHolder.itemView);
    }

    private void resetFocusInfo() {
        this.mState.mFocusedItemId = -1L;
        this.mState.mFocusedItemPosition = -1;
        this.mState.mFocusedSubChildId = -1;
    }

    private android.view.View findNextViewToFocus() {
        com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForAdapterPosition;
        int i = this.mState.mFocusedItemPosition != -1 ? this.mState.mFocusedItemPosition : 0;
        int itemCount = this.mState.getItemCount();
        for (int i2 = i; i2 < itemCount; i2++) {
            com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForAdapterPosition2 = findViewHolderForAdapterPosition(i2);
            if (findViewHolderForAdapterPosition2 == null) {
                break;
            }
            if (findViewHolderForAdapterPosition2.itemView.hasFocusable()) {
                return findViewHolderForAdapterPosition2.itemView;
            }
        }
        int min = java.lang.Math.min(itemCount, i);
        do {
            min--;
            if (min < 0 || (findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(min)) == null) {
                return null;
            }
        } while (!findViewHolderForAdapterPosition.itemView.hasFocusable());
        return findViewHolderForAdapterPosition.itemView;
    }

    private void recoverFocusFromState() {
        com.android.internal.widget.RecyclerView.ViewHolder viewHolder;
        android.view.View findViewById;
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && hasFocus() && getDescendantFocusability() != 393216) {
            if (getDescendantFocusability() == 131072 && isFocused()) {
                return;
            }
            if (!isFocused()) {
                android.view.View focusedChild = getFocusedChild();
                if (IGNORE_DETACHED_FOCUSED_CHILD && (focusedChild.getParent() == null || !focusedChild.hasFocus())) {
                    if (this.mChildHelper.getChildCount() == 0) {
                        requestFocus();
                        return;
                    }
                } else if (!this.mChildHelper.isHidden(focusedChild)) {
                    return;
                }
            }
            android.view.View view = null;
            if (this.mState.mFocusedItemId != -1 && this.mAdapter.hasStableIds()) {
                viewHolder = findViewHolderForItemId(this.mState.mFocusedItemId);
            } else {
                viewHolder = null;
            }
            if (viewHolder == null || this.mChildHelper.isHidden(viewHolder.itemView) || !viewHolder.itemView.hasFocusable()) {
                if (this.mChildHelper.getChildCount() > 0) {
                    view = findNextViewToFocus();
                }
            } else {
                view = viewHolder.itemView;
            }
            if (view != null) {
                if (this.mState.mFocusedSubChildId != -1 && (findViewById = view.findViewById(this.mState.mFocusedSubChildId)) != null && findViewById.isFocusable()) {
                    view = findViewById;
                }
                view.requestFocus();
            }
        }
    }

    private int getDeepestFocusedViewWithId(android.view.View view) {
        int id = view.getId();
        while (!view.isFocused() && (view instanceof android.view.ViewGroup) && view.hasFocus()) {
            view = ((android.view.ViewGroup) view).getFocusedChild();
            if (view.getId() != -1) {
                id = view.getId();
            }
        }
        return id;
    }

    private void dispatchLayoutStep1() {
        this.mState.assertLayoutStep(1);
        this.mState.mIsMeasuring = false;
        eatRequestLayout();
        this.mViewInfoStore.clear();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        saveFocusInfo();
        this.mState.mTrackOldChangeHolders = this.mState.mRunSimpleAnimations && this.mItemsChanged;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        this.mState.mInPreLayout = this.mState.mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            int childCount = this.mChildHelper.getChildCount();
            for (int i = 0; i < childCount; i++) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (!childViewHolderInt.shouldIgnore() && (!childViewHolderInt.isInvalid() || this.mAdapter.hasStableIds())) {
                    this.mViewInfoStore.addToPreLayout(childViewHolderInt, this.mItemAnimator.recordPreLayoutInformation(this.mState, childViewHolderInt, com.android.internal.widget.RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt), childViewHolderInt.getUnmodifiedPayloads()));
                    if (this.mState.mTrackOldChangeHolders && childViewHolderInt.isUpdated() && !childViewHolderInt.isRemoved() && !childViewHolderInt.shouldIgnore() && !childViewHolderInt.isInvalid()) {
                        this.mViewInfoStore.addToOldChangeHolders(getChangedHolderKey(childViewHolderInt), childViewHolderInt);
                    }
                }
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            saveOldPositions();
            boolean z = this.mState.mStructureChanged;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = z;
            for (int i2 = 0; i2 < this.mChildHelper.getChildCount(); i2++) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt2 = getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                if (!childViewHolderInt2.shouldIgnore() && !this.mViewInfoStore.isInPreLayout(childViewHolderInt2)) {
                    int buildAdapterChangeFlagsForAnimations = com.android.internal.widget.RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt2);
                    boolean hasAnyOfTheFlags = childViewHolderInt2.hasAnyOfTheFlags(8192);
                    if (!hasAnyOfTheFlags) {
                        buildAdapterChangeFlagsForAnimations |= 4096;
                    }
                    com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo recordPreLayoutInformation = this.mItemAnimator.recordPreLayoutInformation(this.mState, childViewHolderInt2, buildAdapterChangeFlagsForAnimations, childViewHolderInt2.getUnmodifiedPayloads());
                    if (hasAnyOfTheFlags) {
                        recordAnimationInfoIfBouncedHiddenView(childViewHolderInt2, recordPreLayoutInformation);
                    } else {
                        this.mViewInfoStore.addToAppearedInPreLayoutHolders(childViewHolderInt2, recordPreLayoutInformation);
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() {
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        this.mState.mRunSimpleAnimations = this.mState.mRunSimpleAnimations && this.mItemAnimator != null;
        this.mState.mLayoutStep = 4;
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.assertLayoutStep(4);
        eatRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int childCount = this.mChildHelper.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(childCount));
                if (!childViewHolderInt.shouldIgnore()) {
                    long changedHolderKey = getChangedHolderKey(childViewHolderInt);
                    com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo recordPostLayoutInformation = this.mItemAnimator.recordPostLayoutInformation(this.mState, childViewHolderInt);
                    com.android.internal.widget.RecyclerView.ViewHolder fromOldChangeHolders = this.mViewInfoStore.getFromOldChangeHolders(changedHolderKey);
                    if (fromOldChangeHolders != null && !fromOldChangeHolders.shouldIgnore()) {
                        boolean isDisappearing = this.mViewInfoStore.isDisappearing(fromOldChangeHolders);
                        boolean isDisappearing2 = this.mViewInfoStore.isDisappearing(childViewHolderInt);
                        if (isDisappearing && fromOldChangeHolders == childViewHolderInt) {
                            this.mViewInfoStore.addToPostLayout(childViewHolderInt, recordPostLayoutInformation);
                        } else {
                            com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo popFromPreLayout = this.mViewInfoStore.popFromPreLayout(fromOldChangeHolders);
                            this.mViewInfoStore.addToPostLayout(childViewHolderInt, recordPostLayoutInformation);
                            com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo popFromPostLayout = this.mViewInfoStore.popFromPostLayout(childViewHolderInt);
                            if (popFromPreLayout == null) {
                                handleMissingPreInfoForChangeError(changedHolderKey, childViewHolderInt, fromOldChangeHolders);
                            } else {
                                animateChange(fromOldChangeHolders, childViewHolderInt, popFromPreLayout, popFromPostLayout, isDisappearing, isDisappearing2);
                            }
                        }
                    } else {
                        this.mViewInfoStore.addToPostLayout(childViewHolderInt, recordPostLayoutInformation);
                    }
                }
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        this.mState.mPreviousLayoutItemCount = this.mState.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            this.mLayout.mPrefetchMaxCountObserved = 0;
            this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        onExitLayoutOrScroll();
        resumeRequestLayout(false);
        this.mViewInfoStore.clear();
        if (didChildRangeChange(this.mMinMaxLayoutPositions[0], this.mMinMaxLayoutPositions[1])) {
            dispatchOnScrolled(0, 0);
        }
        recoverFocusFromState();
        resetFocusInfo();
    }

    private void handleMissingPreInfoForChangeError(long j, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2) {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
            if (childViewHolderInt != viewHolder && getChangedHolderKey(childViewHolderInt) == j) {
                if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
                    throw new java.lang.IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + childViewHolderInt + " \n View Holder 2:" + viewHolder);
                }
                throw new java.lang.IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + childViewHolderInt + " \n View Holder 2:" + viewHolder);
            }
        }
        android.util.Log.e(TAG, "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + viewHolder2 + " cannot be found but it is necessary for " + viewHolder);
    }

    void recordAnimationInfoIfBouncedHiddenView(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            this.mViewInfoStore.addToOldChangeHolders(getChangedHolderKey(viewHolder), viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    private void findMinMaxChildLayoutPositions(int[] iArr) {
        int childCount = this.mChildHelper.getChildCount();
        if (childCount == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < childCount; i3++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i3));
            if (!childViewHolderInt.shouldIgnore()) {
                int layoutPosition = childViewHolderInt.getLayoutPosition();
                if (layoutPosition < i) {
                    i = layoutPosition;
                }
                if (layoutPosition > i2) {
                    i2 = layoutPosition;
                }
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    private boolean didChildRangeChange(int i, int i2) {
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        return (this.mMinMaxLayoutPositions[0] == i && this.mMinMaxLayoutPositions[1] == i2) ? false : true;
    }

    @Override // android.view.ViewGroup
    protected void removeDetachedView(android.view.View view, boolean z) {
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            if (childViewHolderInt.isTmpDetached()) {
                childViewHolderInt.clearTmpDetachFlag();
            } else if (!childViewHolderInt.shouldIgnore()) {
                throw new java.lang.IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + childViewHolderInt);
            }
        }
        dispatchChildDetached(view);
        super.removeDetachedView(view, z);
    }

    long getChangedHolderKey(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        return this.mAdapter.hasStableIds() ? viewHolder.getItemId() : viewHolder.mPosition;
    }

    void animateAppearance(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            postAnimationRunner();
        }
    }

    void animateDisappearance(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (this.mItemAnimator.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
            postAnimationRunner();
        }
    }

    private void animateChange(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean z, boolean z2) {
        viewHolder.setIsRecyclable(false);
        if (z) {
            addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (z2) {
                addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            addAnimatingView(viewHolder);
            this.mRecycler.unscrapView(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (this.mItemAnimator.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) {
            postAnimationRunner();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        android.os.Trace.beginSection(TRACE_ON_LAYOUT_TAG);
        dispatchLayout();
        android.os.Trace.endSection();
        this.mFirstLayoutComplete = true;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mEatRequestLayout == 0 && !this.mLayoutFrozen) {
            super.requestLayout();
        } else {
            this.mLayoutRequestEaten = true;
        }
    }

    void markItemDecorInsetsDirty() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ((com.android.internal.widget.RecyclerView.LayoutParams) this.mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
        }
        this.mRecycler.markItemDecorInsetsDirty();
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        boolean z;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).onDrawOver(canvas, this, this.mState);
        }
        if (this.mLeftGlow != null && !this.mLeftGlow.isFinished()) {
            int save = canvas.save();
            int paddingBottom = this.mClipToPadding ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, 0.0f);
            z = this.mLeftGlow != null && this.mLeftGlow.draw(canvas);
            canvas.restoreToCount(save);
        } else {
            z = false;
        }
        if (this.mTopGlow != null && !this.mTopGlow.isFinished()) {
            int save2 = canvas.save();
            if (this.mClipToPadding) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            z |= this.mTopGlow != null && this.mTopGlow.draw(canvas);
            canvas.restoreToCount(save2);
        }
        if (this.mRightGlow != null && !this.mRightGlow.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.mClipToPadding ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(-paddingTop, -width);
            z |= this.mRightGlow != null && this.mRightGlow.draw(canvas);
            canvas.restoreToCount(save3);
        }
        if (this.mBottomGlow != null && !this.mBottomGlow.isFinished()) {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.mClipToPadding) {
                canvas.translate((-getWidth()) + getPaddingRight(), (-getHeight()) + getPaddingBottom());
            } else {
                canvas.translate(-getWidth(), -getHeight());
            }
            if (this.mBottomGlow != null && this.mBottomGlow.draw(canvas)) {
                z2 = true;
            }
            z |= z2;
            canvas.restoreToCount(save4);
        }
        if ((z || this.mItemAnimator == null || this.mItemDecorations.size() <= 0 || !this.mItemAnimator.isRunning()) ? z : true) {
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).onDraw(canvas, this, this.mState);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof com.android.internal.widget.RecyclerView.LayoutParams) && this.mLayout.checkLayoutParams((com.android.internal.widget.RecyclerView.LayoutParams) layoutParams);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        if (this.mLayout == null) {
            throw new java.lang.IllegalStateException("RecyclerView has no LayoutManager");
        }
        return this.mLayout.generateDefaultLayoutParams();
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        if (this.mLayout == null) {
            throw new java.lang.IllegalStateException("RecyclerView has no LayoutManager");
        }
        return this.mLayout.generateLayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mLayout == null) {
            throw new java.lang.IllegalStateException("RecyclerView has no LayoutManager");
        }
        return this.mLayout.generateLayoutParams(layoutParams);
    }

    public boolean isAnimating() {
        return this.mItemAnimator != null && this.mItemAnimator.isRunning();
    }

    void saveOldPositions() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.saveOldPosition();
            }
        }
    }

    void clearOldPositions() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.clearOldPosition();
            }
        }
        this.mRecycler.clearOldPositions();
    }

    void offsetPositionRecordsForMove(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        if (i < i2) {
            i5 = -1;
            i4 = i;
            i3 = i2;
        } else {
            i3 = i;
            i4 = i2;
            i5 = 1;
        }
        for (int i6 = 0; i6 < unfilteredChildCount; i6++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i6));
            if (childViewHolderInt != null && childViewHolderInt.mPosition >= i4 && childViewHolderInt.mPosition <= i3) {
                if (childViewHolderInt.mPosition == i) {
                    childViewHolderInt.offsetPosition(i2 - i, false);
                } else {
                    childViewHolderInt.offsetPosition(i5, false);
                }
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForMove(i, i2);
        requestLayout();
    }

    void offsetPositionRecordsForInsert(int i, int i2) {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i3 = 0; i3 < unfilteredChildCount; i3++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i3));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i) {
                childViewHolderInt.offsetPosition(i2, false);
                this.mState.mStructureChanged = true;
            }
        }
        this.mRecycler.offsetPositionRecordsForInsert(i, i2);
        requestLayout();
    }

    void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
        int i3 = i + i2;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i4 = 0; i4 < unfilteredChildCount; i4++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i4));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                if (childViewHolderInt.mPosition >= i3) {
                    childViewHolderInt.offsetPosition(-i2, z);
                    this.mState.mStructureChanged = true;
                } else if (childViewHolderInt.mPosition >= i) {
                    childViewHolderInt.flagRemovedAndOffsetPosition(i - 1, -i2, z);
                    this.mState.mStructureChanged = true;
                }
            }
        }
        this.mRecycler.offsetPositionRecordsForRemove(i, i2, z);
        requestLayout();
    }

    void viewRangeUpdate(int i, int i2, java.lang.Object obj) {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        int i3 = i + i2;
        for (int i4 = 0; i4 < unfilteredChildCount; i4++) {
            android.view.View unfilteredChildAt = this.mChildHelper.getUnfilteredChildAt(i4);
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(unfilteredChildAt);
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i && childViewHolderInt.mPosition < i3) {
                childViewHolderInt.addFlags(2);
                childViewHolderInt.addChangePayload(obj);
                ((com.android.internal.widget.RecyclerView.LayoutParams) unfilteredChildAt.getLayoutParams()).mInsetsDirty = true;
            }
        }
        this.mRecycler.viewRangeUpdate(i, i2);
    }

    boolean canReuseUpdatedViewHolder(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        return this.mItemAnimator == null || this.mItemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads());
    }

    void setDataSetChangedAfterLayout() {
        if (this.mDataSetHasChangedAfterLayout) {
            return;
        }
        this.mDataSetHasChangedAfterLayout = true;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.addFlags(512);
            }
        }
        this.mRecycler.setAdapterPositionsAsUnknown();
        markKnownViewsInvalid();
    }

    void markKnownViewsInvalid() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        this.mRecycler.markKnownViewsInvalid();
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() == 0) {
            return;
        }
        if (this.mLayout != null) {
            this.mLayout.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public void setPreserveFocusAfterLayout(boolean z) {
        this.mPreserveFocusAfterLayout = z;
    }

    public com.android.internal.widget.RecyclerView.ViewHolder getChildViewHolder(android.view.View view) {
        android.view.ViewParent parent = view.getParent();
        if (parent != null && parent != this) {
            throw new java.lang.IllegalArgumentException("View " + view + " is not a direct child of " + this);
        }
        return getChildViewHolderInt(view);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:?, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.view.View findContainingItemView(android.view.View view) {
        android.view.ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof android.view.View)) {
            view = parent;
            parent = view.getParent();
        }
        return null;
    }

    public com.android.internal.widget.RecyclerView.ViewHolder findContainingViewHolder(android.view.View view) {
        android.view.View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            return null;
        }
        return getChildViewHolder(findContainingItemView);
    }

    static com.android.internal.widget.RecyclerView.ViewHolder getChildViewHolderInt(android.view.View view) {
        if (view == null) {
            return null;
        }
        return ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mViewHolder;
    }

    @java.lang.Deprecated
    public int getChildPosition(android.view.View view) {
        return getChildAdapterPosition(view);
    }

    public int getChildAdapterPosition(android.view.View view) {
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getAdapterPosition();
        }
        return -1;
    }

    public int getChildLayoutPosition(android.view.View view) {
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getLayoutPosition();
        }
        return -1;
    }

    public long getChildItemId(android.view.View view) {
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt;
        if (this.mAdapter == null || !this.mAdapter.hasStableIds() || (childViewHolderInt = getChildViewHolderInt(view)) == null) {
            return -1L;
        }
        return childViewHolderInt.getItemId();
    }

    @java.lang.Deprecated
    public com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForPosition(int i) {
        return findViewHolderForPosition(i, false);
    }

    public com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForLayoutPosition(int i) {
        return findViewHolderForPosition(i, false);
    }

    public com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForAdapterPosition(int i) {
        com.android.internal.widget.RecyclerView.ViewHolder viewHolder = null;
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && getAdapterPositionFor(childViewHolderInt) == i) {
                if (this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    viewHolder = childViewHolderInt;
                } else {
                    return childViewHolderInt;
                }
            }
        }
        return viewHolder;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForPosition(int i, boolean z) {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        com.android.internal.widget.RecyclerView.ViewHolder viewHolder = null;
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved()) {
                if (z) {
                    if (childViewHolderInt.mPosition != i) {
                        continue;
                    }
                    if (!this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                        viewHolder = childViewHolderInt;
                    } else {
                        return childViewHolderInt;
                    }
                } else {
                    if (childViewHolderInt.getLayoutPosition() != i) {
                        continue;
                    }
                    if (!this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    }
                }
            }
        }
        return viewHolder;
    }

    public com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForItemId(long j) {
        com.android.internal.widget.RecyclerView.ViewHolder viewHolder = null;
        if (this.mAdapter == null || !this.mAdapter.hasStableIds()) {
            return null;
        }
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && childViewHolderInt.getItemId() == j) {
                if (this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    viewHolder = childViewHolderInt;
                } else {
                    return childViewHolderInt;
                }
            }
        }
        return viewHolder;
    }

    public android.view.View findChildViewUnder(float f, float f2) {
        for (int childCount = this.mChildHelper.getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = this.mChildHelper.getChildAt(childCount);
            float translationX = childAt.getTranslationX();
            float translationY = childAt.getTranslationY();
            if (f >= childAt.getLeft() + translationX && f <= childAt.getRight() + translationX && f2 >= childAt.getTop() + translationY && f2 <= childAt.getBottom() + translationY) {
                return childAt;
            }
        }
        return null;
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    public void offsetChildrenVertical(int i) {
        int childCount = this.mChildHelper.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            this.mChildHelper.getChildAt(i2).offsetTopAndBottom(i);
        }
    }

    public void onChildAttachedToWindow(android.view.View view) {
    }

    public void onChildDetachedFromWindow(android.view.View view) {
    }

    public void offsetChildrenHorizontal(int i) {
        int childCount = this.mChildHelper.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            this.mChildHelper.getChildAt(i2).offsetLeftAndRight(i);
        }
    }

    public void getDecoratedBoundsWithMargins(android.view.View view, android.graphics.Rect rect) {
        getDecoratedBoundsWithMarginsInt(view, rect);
    }

    static void getDecoratedBoundsWithMarginsInt(android.view.View view, android.graphics.Rect rect) {
        com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        android.graphics.Rect rect2 = layoutParams.mDecorInsets;
        rect.set((view.getLeft() - rect2.left) - layoutParams.leftMargin, (view.getTop() - rect2.top) - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    android.graphics.Rect getItemDecorInsetsForChild(android.view.View view) {
        com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.mDecorInsets;
        }
        android.graphics.Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public void onScrolled(int i, int i2) {
    }

    void dispatchOnScrolled(int i, int i2) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        onScrolled(i, i2);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrolled(this, i, i2);
        }
        if (this.mScrollListeners != null) {
            for (int size = this.mScrollListeners.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).onScrolled(this, i, i2);
            }
        }
        this.mDispatchScrollCounter--;
    }

    public void onScrollStateChanged(int i) {
    }

    void dispatchOnScrollStateChanged(int i) {
        if (this.mLayout != null) {
            this.mLayout.onScrollStateChanged(i);
        }
        onScrollStateChanged(i);
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(this, i);
        }
        if (this.mScrollListeners != null) {
            for (int size = this.mScrollListeners.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).onScrollStateChanged(this, i);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates();
    }

    class ViewFlinger implements java.lang.Runnable {
        private int mLastFlingX;
        private int mLastFlingY;
        private android.widget.OverScroller mScroller;
        android.view.animation.Interpolator mInterpolator = com.android.internal.widget.RecyclerView.sQuinticInterpolator;
        private boolean mEatRunOnAnimationRequest = false;
        private boolean mReSchedulePostAnimationCallback = false;

        ViewFlinger() {
            this.mScroller = new android.widget.OverScroller(com.android.internal.widget.RecyclerView.this.getContext(), com.android.internal.widget.RecyclerView.sQuinticInterpolator);
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            if (com.android.internal.widget.RecyclerView.this.mLayout == null) {
                stop();
                return;
            }
            disableRunOnAnimationRequests();
            com.android.internal.widget.RecyclerView.this.consumePendingUpdateOperations();
            android.widget.OverScroller overScroller = this.mScroller;
            com.android.internal.widget.RecyclerView.SmoothScroller smoothScroller = com.android.internal.widget.RecyclerView.this.mLayout.mSmoothScroller;
            if (overScroller.computeScrollOffset()) {
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i6 = currX - this.mLastFlingX;
                int i7 = currY - this.mLastFlingY;
                this.mLastFlingX = currX;
                this.mLastFlingY = currY;
                if (com.android.internal.widget.RecyclerView.this.mAdapter == null) {
                    i = 0;
                    i2 = 0;
                    i3 = 0;
                    i4 = 0;
                } else {
                    com.android.internal.widget.RecyclerView.this.eatRequestLayout();
                    com.android.internal.widget.RecyclerView.this.onEnterLayoutOrScroll();
                    android.os.Trace.beginSection(com.android.internal.widget.RecyclerView.TRACE_SCROLL_TAG);
                    if (i6 != 0) {
                        i = com.android.internal.widget.RecyclerView.this.mLayout.scrollHorizontallyBy(i6, com.android.internal.widget.RecyclerView.this.mRecycler, com.android.internal.widget.RecyclerView.this.mState);
                        i2 = i6 - i;
                    } else {
                        i = 0;
                        i2 = 0;
                    }
                    if (i7 != 0) {
                        i3 = com.android.internal.widget.RecyclerView.this.mLayout.scrollVerticallyBy(i7, com.android.internal.widget.RecyclerView.this.mRecycler, com.android.internal.widget.RecyclerView.this.mState);
                        i4 = i7 - i3;
                    } else {
                        i3 = 0;
                        i4 = 0;
                    }
                    android.os.Trace.endSection();
                    com.android.internal.widget.RecyclerView.this.repositionShadowingViews();
                    com.android.internal.widget.RecyclerView.this.onExitLayoutOrScroll();
                    com.android.internal.widget.RecyclerView.this.resumeRequestLayout(false);
                    if (smoothScroller != null && !smoothScroller.isPendingInitialRun() && smoothScroller.isRunning()) {
                        int itemCount = com.android.internal.widget.RecyclerView.this.mState.getItemCount();
                        if (itemCount == 0) {
                            smoothScroller.stop();
                        } else if (smoothScroller.getTargetPosition() >= itemCount) {
                            smoothScroller.setTargetPosition(itemCount - 1);
                            smoothScroller.onAnimation(i6 - i2, i7 - i4);
                        } else {
                            smoothScroller.onAnimation(i6 - i2, i7 - i4);
                        }
                    }
                }
                if (!com.android.internal.widget.RecyclerView.this.mItemDecorations.isEmpty()) {
                    com.android.internal.widget.RecyclerView.this.invalidate();
                }
                if (com.android.internal.widget.RecyclerView.this.getOverScrollMode() != 2) {
                    com.android.internal.widget.RecyclerView.this.considerReleasingGlowsOnScroll(i6, i7);
                }
                if (i2 != 0 || i4 != 0) {
                    int currVelocity = (int) overScroller.getCurrVelocity();
                    if (i2 == currX) {
                        i5 = 0;
                    } else {
                        i5 = i2 < 0 ? -currVelocity : i2 > 0 ? currVelocity : 0;
                    }
                    if (i4 == currY) {
                        currVelocity = 0;
                    } else if (i4 < 0) {
                        currVelocity = -currVelocity;
                    } else if (i4 <= 0) {
                        currVelocity = 0;
                    }
                    if (com.android.internal.widget.RecyclerView.this.getOverScrollMode() != 2) {
                        com.android.internal.widget.RecyclerView.this.absorbGlows(i5, currVelocity);
                    }
                    if ((i5 != 0 || i2 == currX || overScroller.getFinalX() == 0) && (currVelocity != 0 || i4 == currY || overScroller.getFinalY() == 0)) {
                        overScroller.abortAnimation();
                    }
                }
                if (i != 0 || i3 != 0) {
                    com.android.internal.widget.RecyclerView.this.dispatchOnScrolled(i, i3);
                }
                if (!com.android.internal.widget.RecyclerView.this.awakenScrollBars()) {
                    com.android.internal.widget.RecyclerView.this.invalidate();
                }
                boolean z = (i6 == 0 && i7 == 0) || (i6 != 0 && com.android.internal.widget.RecyclerView.this.mLayout.canScrollHorizontally() && i == i6) || (i7 != 0 && com.android.internal.widget.RecyclerView.this.mLayout.canScrollVertically() && i3 == i7);
                if (overScroller.isFinished() || !z) {
                    com.android.internal.widget.RecyclerView.this.setScrollState(0);
                    if (com.android.internal.widget.RecyclerView.ALLOW_THREAD_GAP_WORK) {
                        com.android.internal.widget.RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                    }
                } else {
                    postOnAnimation();
                    if (com.android.internal.widget.RecyclerView.this.mGapWorker != null) {
                        com.android.internal.widget.RecyclerView.this.mGapWorker.postFromTraversal(com.android.internal.widget.RecyclerView.this, i6, i7);
                    }
                }
            }
            if (smoothScroller != null) {
                if (smoothScroller.isPendingInitialRun()) {
                    smoothScroller.onAnimation(0, 0);
                }
                if (!this.mReSchedulePostAnimationCallback) {
                    smoothScroller.stop();
                }
            }
            enableRunOnAnimationRequests();
        }

        private void disableRunOnAnimationRequests() {
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
        }

        private void enableRunOnAnimationRequests() {
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                postOnAnimation();
            }
        }

        void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
            } else {
                com.android.internal.widget.RecyclerView.this.removeCallbacks(this);
                com.android.internal.widget.RecyclerView.this.postOnAnimation(this);
            }
        }

        public void fling(int i, int i2) {
            com.android.internal.widget.RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            postOnAnimation();
        }

        public void smoothScrollBy(int i, int i2) {
            smoothScrollBy(i, i2, 0, 0);
        }

        public void smoothScrollBy(int i, int i2, int i3, int i4) {
            smoothScrollBy(i, i2, computeScrollDuration(i, i2, i3, i4));
        }

        private float distanceInfluenceForSnapDuration(float f) {
            return (float) java.lang.Math.sin((float) ((f - 0.5f) * 0.4712389167638204d));
        }

        private int computeScrollDuration(int i, int i2, int i3, int i4) {
            int i5;
            int abs = java.lang.Math.abs(i);
            int abs2 = java.lang.Math.abs(i2);
            boolean z = abs > abs2;
            int sqrt = (int) java.lang.Math.sqrt((i3 * i3) + (i4 * i4));
            int sqrt2 = (int) java.lang.Math.sqrt((i * i) + (i2 * i2));
            com.android.internal.widget.RecyclerView recyclerView = com.android.internal.widget.RecyclerView.this;
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            int i6 = width / 2;
            float f = width;
            float f2 = i6;
            float distanceInfluenceForSnapDuration = f2 + (distanceInfluenceForSnapDuration(java.lang.Math.min(1.0f, (sqrt2 * 1.0f) / f)) * f2);
            if (sqrt > 0) {
                i5 = java.lang.Math.round(java.lang.Math.abs(distanceInfluenceForSnapDuration / sqrt) * 1000.0f) * 4;
            } else {
                if (!z) {
                    abs = abs2;
                }
                i5 = (int) (((abs / f) + 1.0f) * 300.0f);
            }
            return java.lang.Math.min(i5, 2000);
        }

        public void smoothScrollBy(int i, int i2, int i3) {
            smoothScrollBy(i, i2, i3, com.android.internal.widget.RecyclerView.sQuinticInterpolator);
        }

        public void smoothScrollBy(int i, int i2, android.view.animation.Interpolator interpolator) {
            int computeScrollDuration = computeScrollDuration(i, i2, 0, 0);
            if (interpolator == null) {
                interpolator = com.android.internal.widget.RecyclerView.sQuinticInterpolator;
            }
            smoothScrollBy(i, i2, computeScrollDuration, interpolator);
        }

        public void smoothScrollBy(int i, int i2, int i3, android.view.animation.Interpolator interpolator) {
            if (this.mInterpolator != interpolator) {
                this.mInterpolator = interpolator;
                this.mScroller = new android.widget.OverScroller(com.android.internal.widget.RecyclerView.this.getContext(), interpolator);
            }
            com.android.internal.widget.RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, i, i2, i3);
            postOnAnimation();
        }

        public void stop() {
            com.android.internal.widget.RecyclerView.this.removeCallbacks(this);
            this.mScroller.abortAnimation();
        }
    }

    void repositionShadowingViews() {
        int childCount = this.mChildHelper.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = this.mChildHelper.getChildAt(i);
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolder = getChildViewHolder(childAt);
            if (childViewHolder != null && childViewHolder.mShadowingHolder != null) {
                android.view.View view = childViewHolder.mShadowingHolder.itemView;
                int left = childAt.getLeft();
                int top = childAt.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    private class RecyclerViewDataObserver extends com.android.internal.widget.RecyclerView.AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        @Override // com.android.internal.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            com.android.internal.widget.RecyclerView.this.assertNotInLayoutOrScroll(null);
            com.android.internal.widget.RecyclerView.this.mState.mStructureChanged = true;
            com.android.internal.widget.RecyclerView.this.setDataSetChangedAfterLayout();
            if (!com.android.internal.widget.RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                com.android.internal.widget.RecyclerView.this.requestLayout();
            }
        }

        @Override // com.android.internal.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2, java.lang.Object obj) {
            com.android.internal.widget.RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (com.android.internal.widget.RecyclerView.this.mAdapterHelper.onItemRangeChanged(i, i2, obj)) {
                triggerUpdateProcessor();
            }
        }

        @Override // com.android.internal.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            com.android.internal.widget.RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (com.android.internal.widget.RecyclerView.this.mAdapterHelper.onItemRangeInserted(i, i2)) {
                triggerUpdateProcessor();
            }
        }

        @Override // com.android.internal.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            com.android.internal.widget.RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (com.android.internal.widget.RecyclerView.this.mAdapterHelper.onItemRangeRemoved(i, i2)) {
                triggerUpdateProcessor();
            }
        }

        @Override // com.android.internal.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i, int i2, int i3) {
            com.android.internal.widget.RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (com.android.internal.widget.RecyclerView.this.mAdapterHelper.onItemRangeMoved(i, i2, i3)) {
                triggerUpdateProcessor();
            }
        }

        void triggerUpdateProcessor() {
            if (com.android.internal.widget.RecyclerView.POST_UPDATES_ON_ANIMATION && com.android.internal.widget.RecyclerView.this.mHasFixedSize && com.android.internal.widget.RecyclerView.this.mIsAttached) {
                com.android.internal.widget.RecyclerView.this.postOnAnimation(com.android.internal.widget.RecyclerView.this.mUpdateChildViewsRunnable);
            } else {
                com.android.internal.widget.RecyclerView.this.mAdapterUpdateDuringMeasure = true;
                com.android.internal.widget.RecyclerView.this.requestLayout();
            }
        }
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        android.util.SparseArray<com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData> mScrap = new android.util.SparseArray<>();
        private int mAttachCount = 0;

        static class ScrapData {
            java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> mScrapHeap = new java.util.ArrayList<>();
            int mMaxScrap = 5;
            long mCreateRunningAverageNs = 0;
            long mBindRunningAverageNs = 0;

            ScrapData() {
            }
        }

        public void clear() {
            for (int i = 0; i < this.mScrap.size(); i++) {
                this.mScrap.valueAt(i).mScrapHeap.clear();
            }
        }

        public void setMaxRecycledViews(int i, int i2) {
            com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData scrapDataForType = getScrapDataForType(i);
            scrapDataForType.mMaxScrap = i2;
            java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> arrayList = scrapDataForType.mScrapHeap;
            if (arrayList != null) {
                while (arrayList.size() > i2) {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
        }

        public int getRecycledViewCount(int i) {
            return getScrapDataForType(i).mScrapHeap.size();
        }

        public com.android.internal.widget.RecyclerView.ViewHolder getRecycledView(int i) {
            com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData scrapData = this.mScrap.get(i);
            if (scrapData != null && !scrapData.mScrapHeap.isEmpty()) {
                return scrapData.mScrapHeap.remove(r2.size() - 1);
            }
            return null;
        }

        int size() {
            int i = 0;
            for (int i2 = 0; i2 < this.mScrap.size(); i2++) {
                java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> arrayList = this.mScrap.valueAt(i2).mScrapHeap;
                if (arrayList != null) {
                    i += arrayList.size();
                }
            }
            return i;
        }

        public void putRecycledView(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> arrayList = getScrapDataForType(itemViewType).mScrapHeap;
            if (this.mScrap.get(itemViewType).mMaxScrap <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        long runningAverage(long j, long j2) {
            if (j == 0) {
                return j2;
            }
            return ((j / 4) * 3) + (j2 / 4);
        }

        void factorInCreateTime(int i, long j) {
            com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData scrapDataForType = getScrapDataForType(i);
            scrapDataForType.mCreateRunningAverageNs = runningAverage(scrapDataForType.mCreateRunningAverageNs, j);
        }

        void factorInBindTime(int i, long j) {
            com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData scrapDataForType = getScrapDataForType(i);
            scrapDataForType.mBindRunningAverageNs = runningAverage(scrapDataForType.mBindRunningAverageNs, j);
        }

        boolean willCreateInTime(int i, long j, long j2) {
            long j3 = getScrapDataForType(i).mCreateRunningAverageNs;
            return j3 == 0 || j + j3 < j2;
        }

        boolean willBindInTime(int i, long j, long j2) {
            long j3 = getScrapDataForType(i).mBindRunningAverageNs;
            return j3 == 0 || j + j3 < j2;
        }

        void attach(com.android.internal.widget.RecyclerView.Adapter adapter) {
            this.mAttachCount++;
        }

        void detach() {
            this.mAttachCount--;
        }

        void onAdapterChanged(com.android.internal.widget.RecyclerView.Adapter adapter, com.android.internal.widget.RecyclerView.Adapter adapter2, boolean z) {
            if (adapter != null) {
                detach();
            }
            if (!z && this.mAttachCount == 0) {
                clear();
            }
            if (adapter2 != null) {
                attach(adapter2);
            }
        }

        private com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData getScrapDataForType(int i) {
            com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData scrapData = this.mScrap.get(i);
            if (scrapData == null) {
                com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData scrapData2 = new com.android.internal.widget.RecyclerView.RecycledViewPool.ScrapData();
                this.mScrap.put(i, scrapData2);
                return scrapData2;
            }
            return scrapData;
        }
    }

    static com.android.internal.widget.RecyclerView findNestedRecyclerView(android.view.View view) {
        if (!(view instanceof android.view.ViewGroup)) {
            return null;
        }
        if (view instanceof com.android.internal.widget.RecyclerView) {
            return (com.android.internal.widget.RecyclerView) view;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            com.android.internal.widget.RecyclerView findNestedRecyclerView = findNestedRecyclerView(viewGroup.getChildAt(i));
            if (findNestedRecyclerView != null) {
                return findNestedRecyclerView;
            }
        }
        return null;
    }

    static void clearNestedRecyclerViewIfNotNested(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.mNestedRecyclerView != null) {
            com.android.internal.widget.RecyclerView recyclerView = viewHolder.mNestedRecyclerView.get();
            while (recyclerView != null) {
                if (recyclerView == viewHolder.itemView) {
                    return;
                }
                java.lang.Object parent = recyclerView.getParent();
                if (parent instanceof android.view.View) {
                    recyclerView = (android.view.View) parent;
                } else {
                    recyclerView = null;
                }
            }
            viewHolder.mNestedRecyclerView = null;
        }
    }

    long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return java.lang.System.nanoTime();
        }
        return 0L;
    }

    public final class Recycler {
        static final int DEFAULT_CACHE_SIZE = 2;
        com.android.internal.widget.RecyclerView.RecycledViewPool mRecyclerPool;
        private com.android.internal.widget.RecyclerView.ViewCacheExtension mViewCacheExtension;
        final java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> mAttachedScrap = new java.util.ArrayList<>();
        java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> mChangedScrap = null;
        final java.util.ArrayList<com.android.internal.widget.RecyclerView.ViewHolder> mCachedViews = new java.util.ArrayList<>();
        private final java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = java.util.Collections.unmodifiableList(this.mAttachedScrap);
        private int mRequestedCacheMax = 2;
        int mViewCacheMax = 2;

        public Recycler() {
        }

        public void clear() {
            this.mAttachedScrap.clear();
            recycleAndClearCachedViews();
        }

        public void setViewCacheSize(int i) {
            this.mRequestedCacheMax = i;
            updateViewCacheSize();
        }

        void updateViewCacheSize() {
            this.mViewCacheMax = this.mRequestedCacheMax + (com.android.internal.widget.RecyclerView.this.mLayout != null ? com.android.internal.widget.RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0);
            for (int size = this.mCachedViews.size() - 1; size >= 0 && this.mCachedViews.size() > this.mViewCacheMax; size--) {
                recycleCachedViewAt(size);
            }
        }

        public java.util.List<com.android.internal.widget.RecyclerView.ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        boolean validateViewHolderForOffsetPosition(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.isRemoved()) {
                return com.android.internal.widget.RecyclerView.this.mState.isPreLayout();
            }
            if (viewHolder.mPosition < 0 || viewHolder.mPosition >= com.android.internal.widget.RecyclerView.this.mAdapter.getItemCount()) {
                throw new java.lang.IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + viewHolder);
            }
            if (com.android.internal.widget.RecyclerView.this.mState.isPreLayout() || com.android.internal.widget.RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) == viewHolder.getItemViewType()) {
                return !com.android.internal.widget.RecyclerView.this.mAdapter.hasStableIds() || viewHolder.getItemId() == com.android.internal.widget.RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition);
            }
            return false;
        }

        private boolean tryBindViewHolderByDeadline(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i, int i2, long j) {
            viewHolder.mOwnerRecyclerView = com.android.internal.widget.RecyclerView.this;
            int itemViewType = viewHolder.getItemViewType();
            long nanoTime = com.android.internal.widget.RecyclerView.this.getNanoTime();
            if (j != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(itemViewType, nanoTime, j)) {
                return false;
            }
            com.android.internal.widget.RecyclerView.this.mAdapter.bindViewHolder(viewHolder, i);
            this.mRecyclerPool.factorInBindTime(viewHolder.getItemViewType(), com.android.internal.widget.RecyclerView.this.getNanoTime() - nanoTime);
            attachAccessibilityDelegate(viewHolder.itemView);
            if (com.android.internal.widget.RecyclerView.this.mState.isPreLayout()) {
                viewHolder.mPreLayoutPosition = i2;
                return true;
            }
            return true;
        }

        public void bindViewToPosition(android.view.View view, int i) {
            com.android.internal.widget.RecyclerView.LayoutParams layoutParams;
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt == null) {
                throw new java.lang.IllegalArgumentException("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
            }
            int findPositionOffset = com.android.internal.widget.RecyclerView.this.mAdapterHelper.findPositionOffset(i);
            if (findPositionOffset < 0 || findPositionOffset >= com.android.internal.widget.RecyclerView.this.mAdapter.getItemCount()) {
                throw new java.lang.IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + findPositionOffset + ").state:" + com.android.internal.widget.RecyclerView.this.mState.getItemCount());
            }
            tryBindViewHolderByDeadline(childViewHolderInt, findPositionOffset, i, Long.MAX_VALUE);
            android.view.ViewGroup.LayoutParams layoutParams2 = childViewHolderInt.itemView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) com.android.internal.widget.RecyclerView.this.generateDefaultLayoutParams();
                childViewHolderInt.itemView.setLayoutParams(layoutParams);
            } else if (!com.android.internal.widget.RecyclerView.this.checkLayoutParams(layoutParams2)) {
                layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) com.android.internal.widget.RecyclerView.this.generateLayoutParams(layoutParams2);
                childViewHolderInt.itemView.setLayoutParams(layoutParams);
            } else {
                layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) layoutParams2;
            }
            layoutParams.mInsetsDirty = true;
            layoutParams.mViewHolder = childViewHolderInt;
            layoutParams.mPendingInvalidate = childViewHolderInt.itemView.getParent() == null;
        }

        public int convertPreLayoutPositionToPostLayout(int i) {
            if (i < 0 || i >= com.android.internal.widget.RecyclerView.this.mState.getItemCount()) {
                throw new java.lang.IndexOutOfBoundsException("invalid position " + i + ". State item count is " + com.android.internal.widget.RecyclerView.this.mState.getItemCount());
            }
            if (!com.android.internal.widget.RecyclerView.this.mState.isPreLayout()) {
                return i;
            }
            return com.android.internal.widget.RecyclerView.this.mAdapterHelper.findPositionOffset(i);
        }

        public android.view.View getViewForPosition(int i) {
            return getViewForPosition(i, false);
        }

        android.view.View getViewForPosition(int i, boolean z) {
            return tryGetViewHolderForPositionByDeadline(i, z, Long.MAX_VALUE).itemView;
        }

        /* JADX WARN: Removed duplicated region for block: B:84:0x01e3  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x01f1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        com.android.internal.widget.RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int i, boolean z, long j) {
            com.android.internal.widget.RecyclerView.ViewHolder viewHolder;
            boolean z2;
            com.android.internal.widget.RecyclerView.ViewHolder viewHolder2;
            boolean z3;
            boolean tryBindViewHolderByDeadline;
            android.view.ViewGroup.LayoutParams layoutParams;
            com.android.internal.widget.RecyclerView.LayoutParams layoutParams2;
            com.android.internal.widget.RecyclerView findNestedRecyclerView;
            android.view.View viewForPositionAndType;
            if (i < 0 || i >= com.android.internal.widget.RecyclerView.this.mState.getItemCount()) {
                throw new java.lang.IndexOutOfBoundsException("Invalid item position " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + i + "). Item count:" + com.android.internal.widget.RecyclerView.this.mState.getItemCount());
            }
            if (!com.android.internal.widget.RecyclerView.this.mState.isPreLayout()) {
                viewHolder = null;
                z2 = false;
            } else {
                viewHolder = getChangedScrapViewForPosition(i);
                z2 = viewHolder != null;
            }
            if (viewHolder == null && (viewHolder = getScrapOrHiddenOrCachedHolderForPosition(i, z)) != null) {
                if (!validateViewHolderForOffsetPosition(viewHolder)) {
                    if (!z) {
                        viewHolder.addFlags(4);
                        if (viewHolder.isScrap()) {
                            com.android.internal.widget.RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                            viewHolder.unScrap();
                        } else if (viewHolder.wasReturnedFromScrap()) {
                            viewHolder.clearReturnedFromScrapFlag();
                        }
                        recycleViewHolderInternal(viewHolder);
                    }
                    viewHolder = null;
                } else {
                    z2 = true;
                }
            }
            if (viewHolder != null) {
                viewHolder2 = viewHolder;
                z3 = z2;
            } else {
                int findPositionOffset = com.android.internal.widget.RecyclerView.this.mAdapterHelper.findPositionOffset(i);
                if (findPositionOffset < 0 || findPositionOffset >= com.android.internal.widget.RecyclerView.this.mAdapter.getItemCount()) {
                    throw new java.lang.IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + findPositionOffset + ").state:" + com.android.internal.widget.RecyclerView.this.mState.getItemCount());
                }
                int itemViewType = com.android.internal.widget.RecyclerView.this.mAdapter.getItemViewType(findPositionOffset);
                if (com.android.internal.widget.RecyclerView.this.mAdapter.hasStableIds() && (viewHolder = getScrapOrCachedViewForId(com.android.internal.widget.RecyclerView.this.mAdapter.getItemId(findPositionOffset), itemViewType, z)) != null) {
                    viewHolder.mPosition = findPositionOffset;
                    z2 = true;
                }
                if (viewHolder == null && this.mViewCacheExtension != null && (viewForPositionAndType = this.mViewCacheExtension.getViewForPositionAndType(this, i, itemViewType)) != null) {
                    viewHolder = com.android.internal.widget.RecyclerView.this.getChildViewHolder(viewForPositionAndType);
                    if (viewHolder == null) {
                        throw new java.lang.IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder");
                    }
                    if (viewHolder.shouldIgnore()) {
                        throw new java.lang.IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                    }
                }
                if (viewHolder == null) {
                    com.android.internal.widget.RecyclerView.ViewHolder recycledView = getRecycledViewPool().getRecycledView(itemViewType);
                    if (recycledView != null) {
                        recycledView.resetInternal();
                        if (com.android.internal.widget.RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                            invalidateDisplayListInt(recycledView);
                        }
                    }
                    viewHolder = recycledView;
                }
                if (viewHolder != null) {
                    viewHolder2 = viewHolder;
                    z3 = z2;
                } else {
                    long nanoTime = com.android.internal.widget.RecyclerView.this.getNanoTime();
                    if (j != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(itemViewType, nanoTime, j)) {
                        return null;
                    }
                    com.android.internal.widget.RecyclerView.ViewHolder createViewHolder = com.android.internal.widget.RecyclerView.this.mAdapter.createViewHolder(com.android.internal.widget.RecyclerView.this, itemViewType);
                    if (com.android.internal.widget.RecyclerView.ALLOW_THREAD_GAP_WORK && (findNestedRecyclerView = com.android.internal.widget.RecyclerView.findNestedRecyclerView(createViewHolder.itemView)) != null) {
                        createViewHolder.mNestedRecyclerView = new java.lang.ref.WeakReference<>(findNestedRecyclerView);
                    }
                    this.mRecyclerPool.factorInCreateTime(itemViewType, com.android.internal.widget.RecyclerView.this.getNanoTime() - nanoTime);
                    viewHolder2 = createViewHolder;
                    z3 = z2;
                }
            }
            if (z3 && !com.android.internal.widget.RecyclerView.this.mState.isPreLayout() && viewHolder2.hasAnyOfTheFlags(8192)) {
                viewHolder2.setFlags(0, 8192);
                if (com.android.internal.widget.RecyclerView.this.mState.mRunSimpleAnimations) {
                    com.android.internal.widget.RecyclerView.this.recordAnimationInfoIfBouncedHiddenView(viewHolder2, com.android.internal.widget.RecyclerView.this.mItemAnimator.recordPreLayoutInformation(com.android.internal.widget.RecyclerView.this.mState, viewHolder2, com.android.internal.widget.RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations(viewHolder2) | 4096, viewHolder2.getUnmodifiedPayloads()));
                }
            }
            if (com.android.internal.widget.RecyclerView.this.mState.isPreLayout() && viewHolder2.isBound()) {
                viewHolder2.mPreLayoutPosition = i;
            } else if (!viewHolder2.isBound() || viewHolder2.needsUpdate() || viewHolder2.isInvalid()) {
                tryBindViewHolderByDeadline = tryBindViewHolderByDeadline(viewHolder2, com.android.internal.widget.RecyclerView.this.mAdapterHelper.findPositionOffset(i), i, j);
                layoutParams = viewHolder2.itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams2 = (com.android.internal.widget.RecyclerView.LayoutParams) com.android.internal.widget.RecyclerView.this.generateDefaultLayoutParams();
                    viewHolder2.itemView.setLayoutParams(layoutParams2);
                } else if (!com.android.internal.widget.RecyclerView.this.checkLayoutParams(layoutParams)) {
                    layoutParams2 = (com.android.internal.widget.RecyclerView.LayoutParams) com.android.internal.widget.RecyclerView.this.generateLayoutParams(layoutParams);
                    viewHolder2.itemView.setLayoutParams(layoutParams2);
                } else {
                    layoutParams2 = (com.android.internal.widget.RecyclerView.LayoutParams) layoutParams;
                }
                layoutParams2.mViewHolder = viewHolder2;
                layoutParams2.mPendingInvalidate = !z3 && tryBindViewHolderByDeadline;
                return viewHolder2;
            }
            tryBindViewHolderByDeadline = false;
            layoutParams = viewHolder2.itemView.getLayoutParams();
            if (layoutParams != null) {
            }
            layoutParams2.mViewHolder = viewHolder2;
            layoutParams2.mPendingInvalidate = !z3 && tryBindViewHolderByDeadline;
            return viewHolder2;
        }

        private void attachAccessibilityDelegate(android.view.View view) {
            if (com.android.internal.widget.RecyclerView.this.isAccessibilityEnabled()) {
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
                if (view.getAccessibilityDelegate() == null) {
                    view.setAccessibilityDelegate(com.android.internal.widget.RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
                }
            }
        }

        private void invalidateDisplayListInt(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof android.view.ViewGroup) {
                invalidateDisplayListInt((android.view.ViewGroup) viewHolder.itemView, false);
            }
        }

        private void invalidateDisplayListInt(android.view.ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                android.view.View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof android.view.ViewGroup) {
                    invalidateDisplayListInt((android.view.ViewGroup) childAt, true);
                }
            }
            if (!z) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
            } else {
                int visibility = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(visibility);
            }
        }

        public void recycleView(android.view.View view) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isTmpDetached()) {
                com.android.internal.widget.RecyclerView.this.removeDetachedView(view, false);
            }
            if (childViewHolderInt.isScrap()) {
                childViewHolderInt.unScrap();
            } else if (childViewHolderInt.wasReturnedFromScrap()) {
                childViewHolderInt.clearReturnedFromScrapFlag();
            }
            recycleViewHolderInternal(childViewHolderInt);
        }

        void recycleViewInternal(android.view.View view) {
            recycleViewHolderInternal(com.android.internal.widget.RecyclerView.getChildViewHolderInt(view));
        }

        void recycleAndClearCachedViews() {
            for (int size = this.mCachedViews.size() - 1; size >= 0; size--) {
                recycleCachedViewAt(size);
            }
            this.mCachedViews.clear();
            if (com.android.internal.widget.RecyclerView.ALLOW_THREAD_GAP_WORK) {
                com.android.internal.widget.RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
            }
        }

        void recycleCachedViewAt(int i) {
            addViewHolderToRecycledViewPool(this.mCachedViews.get(i), true);
            this.mCachedViews.remove(i);
        }

        void recycleViewHolderInternal(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            boolean z;
            boolean z2 = true;
            if (viewHolder.isScrap() || viewHolder.itemView.getParent() != null) {
                throw new java.lang.IllegalArgumentException("Scrapped or attached views may not be recycled. isScrap:" + viewHolder.isScrap() + " isAttached:" + (viewHolder.itemView.getParent() != null));
            }
            if (viewHolder.isTmpDetached()) {
                throw new java.lang.IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + viewHolder);
            }
            if (viewHolder.shouldIgnore()) {
                throw new java.lang.IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
            }
            boolean doesTransientStatePreventRecycling = viewHolder.doesTransientStatePreventRecycling();
            if ((com.android.internal.widget.RecyclerView.this.mAdapter != null && doesTransientStatePreventRecycling && com.android.internal.widget.RecyclerView.this.mAdapter.onFailedToRecycleView(viewHolder)) || viewHolder.isRecyclable()) {
                if (this.mViewCacheMax > 0 && !viewHolder.hasAnyOfTheFlags(526)) {
                    int size = this.mCachedViews.size();
                    if (size >= this.mViewCacheMax && size > 0) {
                        recycleCachedViewAt(0);
                        size--;
                    }
                    if (com.android.internal.widget.RecyclerView.ALLOW_THREAD_GAP_WORK && size > 0 && !com.android.internal.widget.RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(viewHolder.mPosition)) {
                        int i = size - 1;
                        while (i >= 0) {
                            if (!com.android.internal.widget.RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(this.mCachedViews.get(i).mPosition)) {
                                break;
                            } else {
                                i--;
                            }
                        }
                        size = i + 1;
                    }
                    this.mCachedViews.add(size, viewHolder);
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    z2 = false;
                    r1 = z;
                } else {
                    addViewHolderToRecycledViewPool(viewHolder, true);
                    r1 = z;
                }
            } else {
                z2 = false;
            }
            com.android.internal.widget.RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            if (!r1 && !z2 && doesTransientStatePreventRecycling) {
                viewHolder.mOwnerRecyclerView = null;
            }
        }

        void addViewHolderToRecycledViewPool(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
            com.android.internal.widget.RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            viewHolder.itemView.setAccessibilityDelegate(null);
            if (z) {
                dispatchViewRecycled(viewHolder);
            }
            viewHolder.mOwnerRecyclerView = null;
            getRecycledViewPool().putRecycledView(viewHolder);
        }

        void quickRecycleScrapView(android.view.View view) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.mScrapContainer = null;
            childViewHolderInt.mInChangeScrap = false;
            childViewHolderInt.clearReturnedFromScrapFlag();
            recycleViewHolderInternal(childViewHolderInt);
        }

        void scrapView(android.view.View view) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.hasAnyOfTheFlags(12) || !childViewHolderInt.isUpdated() || com.android.internal.widget.RecyclerView.this.canReuseUpdatedViewHolder(childViewHolderInt)) {
                if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved() && !com.android.internal.widget.RecyclerView.this.mAdapter.hasStableIds()) {
                    throw new java.lang.IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                }
                childViewHolderInt.setScrapContainer(this, false);
                this.mAttachedScrap.add(childViewHolderInt);
                return;
            }
            if (this.mChangedScrap == null) {
                this.mChangedScrap = new java.util.ArrayList<>();
            }
            childViewHolderInt.setScrapContainer(this, true);
            this.mChangedScrap.add(childViewHolderInt);
        }

        void unscrapView(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        android.view.View getScrapViewAt(int i) {
            return this.mAttachedScrap.get(i).itemView;
        }

        void clearScrap() {
            this.mAttachedScrap.clear();
            if (this.mChangedScrap != null) {
                this.mChangedScrap.clear();
            }
        }

        com.android.internal.widget.RecyclerView.ViewHolder getChangedScrapViewForPosition(int i) {
            int size;
            int findPositionOffset;
            if (this.mChangedScrap == null || (size = this.mChangedScrap.size()) == 0) {
                return null;
            }
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mChangedScrap.get(i2);
                if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == i) {
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            if (com.android.internal.widget.RecyclerView.this.mAdapter.hasStableIds() && (findPositionOffset = com.android.internal.widget.RecyclerView.this.mAdapterHelper.findPositionOffset(i)) > 0 && findPositionOffset < com.android.internal.widget.RecyclerView.this.mAdapter.getItemCount()) {
                long itemId = com.android.internal.widget.RecyclerView.this.mAdapter.getItemId(findPositionOffset);
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.internal.widget.RecyclerView.ViewHolder viewHolder2 = this.mChangedScrap.get(i3);
                    if (!viewHolder2.wasReturnedFromScrap() && viewHolder2.getItemId() == itemId) {
                        viewHolder2.addFlags(32);
                        return viewHolder2;
                    }
                }
            }
            return null;
        }

        com.android.internal.widget.RecyclerView.ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int i, boolean z) {
            android.view.View findHiddenNonRemovedView;
            int size = this.mAttachedScrap.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mAttachedScrap.get(i2);
                if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == i && !viewHolder.isInvalid() && (com.android.internal.widget.RecyclerView.this.mState.mInPreLayout || !viewHolder.isRemoved())) {
                    viewHolder.addFlags(32);
                    return viewHolder;
                }
            }
            if (!z && (findHiddenNonRemovedView = com.android.internal.widget.RecyclerView.this.mChildHelper.findHiddenNonRemovedView(i)) != null) {
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(findHiddenNonRemovedView);
                com.android.internal.widget.RecyclerView.this.mChildHelper.unhide(findHiddenNonRemovedView);
                int indexOfChild = com.android.internal.widget.RecyclerView.this.mChildHelper.indexOfChild(findHiddenNonRemovedView);
                if (indexOfChild == -1) {
                    throw new java.lang.IllegalStateException("layout index should not be -1 after unhiding a view:" + childViewHolderInt);
                }
                com.android.internal.widget.RecyclerView.this.mChildHelper.detachViewFromParent(indexOfChild);
                scrapView(findHiddenNonRemovedView);
                childViewHolderInt.addFlags(8224);
                return childViewHolderInt;
            }
            int size2 = this.mCachedViews.size();
            for (int i3 = 0; i3 < size2; i3++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder2 = this.mCachedViews.get(i3);
                if (!viewHolder2.isInvalid() && viewHolder2.getLayoutPosition() == i) {
                    if (!z) {
                        this.mCachedViews.remove(i3);
                    }
                    return viewHolder2;
                }
            }
            return null;
        }

        com.android.internal.widget.RecyclerView.ViewHolder getScrapOrCachedViewForId(long j, int i, boolean z) {
            for (int size = this.mAttachedScrap.size() - 1; size >= 0; size--) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mAttachedScrap.get(size);
                if (viewHolder.getItemId() == j && !viewHolder.wasReturnedFromScrap()) {
                    if (i == viewHolder.getItemViewType()) {
                        viewHolder.addFlags(32);
                        if (viewHolder.isRemoved() && !com.android.internal.widget.RecyclerView.this.mState.isPreLayout()) {
                            viewHolder.setFlags(2, 14);
                        }
                        return viewHolder;
                    }
                    if (!z) {
                        this.mAttachedScrap.remove(size);
                        com.android.internal.widget.RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                        quickRecycleScrapView(viewHolder.itemView);
                    }
                }
            }
            int size2 = this.mCachedViews.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder2 = this.mCachedViews.get(size2);
                if (viewHolder2.getItemId() == j) {
                    if (i == viewHolder2.getItemViewType()) {
                        if (!z) {
                            this.mCachedViews.remove(size2);
                        }
                        return viewHolder2;
                    }
                    if (!z) {
                        recycleCachedViewAt(size2);
                        return null;
                    }
                }
            }
        }

        void dispatchViewRecycled(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            if (com.android.internal.widget.RecyclerView.this.mRecyclerListener != null) {
                com.android.internal.widget.RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
            }
            if (com.android.internal.widget.RecyclerView.this.mAdapter != null) {
                com.android.internal.widget.RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (com.android.internal.widget.RecyclerView.this.mState != null) {
                com.android.internal.widget.RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
            }
        }

        void onAdapterChanged(com.android.internal.widget.RecyclerView.Adapter adapter, com.android.internal.widget.RecyclerView.Adapter adapter2, boolean z) {
            clear();
            getRecycledViewPool().onAdapterChanged(adapter, adapter2, z);
        }

        void offsetPositionRecordsForMove(int i, int i2) {
            int i3;
            int i4;
            int i5;
            if (i < i2) {
                i3 = -1;
                i5 = i;
                i4 = i2;
            } else {
                i3 = 1;
                i4 = i;
                i5 = i2;
            }
            int size = this.mCachedViews.size();
            for (int i6 = 0; i6 < size; i6++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i6);
                if (viewHolder != null && viewHolder.mPosition >= i5 && viewHolder.mPosition <= i4) {
                    if (viewHolder.mPosition == i) {
                        viewHolder.offsetPosition(i2 - i, false);
                    } else {
                        viewHolder.offsetPosition(i3, false);
                    }
                }
            }
        }

        void offsetPositionRecordsForInsert(int i, int i2) {
            int size = this.mCachedViews.size();
            for (int i3 = 0; i3 < size; i3++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i3);
                if (viewHolder != null && viewHolder.mPosition >= i) {
                    viewHolder.offsetPosition(i2, true);
                }
            }
        }

        void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
            int i3 = i + i2;
            for (int size = this.mCachedViews.size() - 1; size >= 0; size--) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(size);
                if (viewHolder != null) {
                    if (viewHolder.mPosition >= i3) {
                        viewHolder.offsetPosition(-i2, z);
                    } else if (viewHolder.mPosition >= i) {
                        viewHolder.addFlags(8);
                        recycleCachedViewAt(size);
                    }
                }
            }
        }

        void setViewCacheExtension(com.android.internal.widget.RecyclerView.ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        void setRecycledViewPool(com.android.internal.widget.RecyclerView.RecycledViewPool recycledViewPool) {
            if (this.mRecyclerPool != null) {
                this.mRecyclerPool.detach();
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool != null) {
                this.mRecyclerPool.attach(com.android.internal.widget.RecyclerView.this.getAdapter());
            }
        }

        com.android.internal.widget.RecyclerView.RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new com.android.internal.widget.RecyclerView.RecycledViewPool();
            }
            return this.mRecyclerPool;
        }

        void viewRangeUpdate(int i, int i2) {
            int layoutPosition;
            int i3 = i2 + i;
            for (int size = this.mCachedViews.size() - 1; size >= 0; size--) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(size);
                if (viewHolder != null && (layoutPosition = viewHolder.getLayoutPosition()) >= i && layoutPosition < i3) {
                    viewHolder.addFlags(2);
                    recycleCachedViewAt(size);
                }
            }
        }

        void setAdapterPositionsAsUnknown() {
            int size = this.mCachedViews.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder != null) {
                    viewHolder.addFlags(512);
                }
            }
        }

        void markKnownViewsInvalid() {
            if (com.android.internal.widget.RecyclerView.this.mAdapter != null && com.android.internal.widget.RecyclerView.this.mAdapter.hasStableIds()) {
                int size = this.mCachedViews.size();
                for (int i = 0; i < size; i++) {
                    com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
                    if (viewHolder != null) {
                        viewHolder.addFlags(6);
                        viewHolder.addChangePayload(null);
                    }
                }
                return;
            }
            recycleAndClearCachedViews();
        }

        void clearOldPositions() {
            int size = this.mCachedViews.size();
            for (int i = 0; i < size; i++) {
                this.mCachedViews.get(i).clearOldPosition();
            }
            int size2 = this.mAttachedScrap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mAttachedScrap.get(i2).clearOldPosition();
            }
            if (this.mChangedScrap != null) {
                int size3 = this.mChangedScrap.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    this.mChangedScrap.get(i3).clearOldPosition();
                }
            }
        }

        void markItemDecorInsetsDirty() {
            int size = this.mCachedViews.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) this.mCachedViews.get(i).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.mInsetsDirty = true;
                }
            }
        }
    }

    public static abstract class Adapter<VH extends com.android.internal.widget.RecyclerView.ViewHolder> {
        private final com.android.internal.widget.RecyclerView.AdapterDataObservable mObservable = new com.android.internal.widget.RecyclerView.AdapterDataObservable();
        private boolean mHasStableIds = false;

        public abstract int getItemCount();

        public abstract void onBindViewHolder(VH vh, int i);

        public abstract VH onCreateViewHolder(android.view.ViewGroup viewGroup, int i);

        public void onBindViewHolder(VH vh, int i, java.util.List<java.lang.Object> list) {
            onBindViewHolder(vh, i);
        }

        public final VH createViewHolder(android.view.ViewGroup viewGroup, int i) {
            android.os.Trace.beginSection(com.android.internal.widget.RecyclerView.TRACE_CREATE_VIEW_TAG);
            VH onCreateViewHolder = onCreateViewHolder(viewGroup, i);
            onCreateViewHolder.mItemViewType = i;
            android.os.Trace.endSection();
            return onCreateViewHolder;
        }

        public final void bindViewHolder(VH vh, int i) {
            vh.mPosition = i;
            if (hasStableIds()) {
                vh.mItemId = getItemId(i);
            }
            vh.setFlags(1, 519);
            android.os.Trace.beginSection(com.android.internal.widget.RecyclerView.TRACE_BIND_VIEW_TAG);
            onBindViewHolder(vh, i, vh.getUnmodifiedPayloads());
            vh.clearPayload();
            android.view.ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof com.android.internal.widget.RecyclerView.LayoutParams) {
                ((com.android.internal.widget.RecyclerView.LayoutParams) layoutParams).mInsetsDirty = true;
            }
            android.os.Trace.endSection();
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public void setHasStableIds(boolean z) {
            if (hasObservers()) {
                throw new java.lang.IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.mHasStableIds = z;
        }

        public long getItemId(int i) {
            return -1L;
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public void onViewRecycled(VH vh) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public void registerAdapterDataObserver(com.android.internal.widget.RecyclerView.AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public void unregisterAdapterDataObserver(com.android.internal.widget.RecyclerView.AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver(adapterDataObserver);
        }

        public void onAttachedToRecyclerView(com.android.internal.widget.RecyclerView recyclerView) {
        }

        public void onDetachedFromRecyclerView(com.android.internal.widget.RecyclerView recyclerView) {
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int i) {
            this.mObservable.notifyItemRangeChanged(i, 1);
        }

        public final void notifyItemChanged(int i, java.lang.Object obj) {
            this.mObservable.notifyItemRangeChanged(i, 1, obj);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.mObservable.notifyItemRangeChanged(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2, java.lang.Object obj) {
            this.mObservable.notifyItemRangeChanged(i, i2, obj);
        }

        public final void notifyItemInserted(int i) {
            this.mObservable.notifyItemRangeInserted(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.notifyItemMoved(i, i2);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.notifyItemRangeInserted(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.mObservable.notifyItemRangeRemoved(i, 1);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.notifyItemRangeRemoved(i, i2);
        }
    }

    void dispatchChildDetached(android.view.View view) {
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        onChildDetachedFromWindow(view);
        if (this.mAdapter != null && childViewHolderInt != null) {
            this.mAdapter.onViewDetachedFromWindow(childViewHolderInt);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int size = this.mOnChildAttachStateListeners.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).onChildViewDetachedFromWindow(view);
            }
        }
    }

    void dispatchChildAttached(android.view.View view) {
        com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        onChildAttachedToWindow(view);
        if (this.mAdapter != null && childViewHolderInt != null) {
            this.mAdapter.onViewAttachedToWindow(childViewHolderInt);
        }
        if (this.mOnChildAttachStateListeners != null) {
            for (int size = this.mOnChildAttachStateListeners.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).onChildViewAttachedToWindow(view);
            }
        }
    }

    public static abstract class LayoutManager {
        com.android.internal.widget.ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        com.android.internal.widget.RecyclerView mRecyclerView;
        com.android.internal.widget.RecyclerView.SmoothScroller mSmoothScroller;
        private int mWidth;
        private int mWidthMode;
        boolean mRequestedSimpleAnimations = false;
        boolean mIsAttachedToWindow = false;
        boolean mAutoMeasure = false;
        private boolean mMeasurementCacheEnabled = true;
        private boolean mItemPrefetchEnabled = true;

        public interface LayoutPrefetchRegistry {
            void addPosition(int i, int i2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        public abstract com.android.internal.widget.RecyclerView.LayoutParams generateDefaultLayoutParams();

        void setRecyclerView(com.android.internal.widget.RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        void setMeasureSpecs(int i, int i2) {
            this.mWidth = android.view.View.MeasureSpec.getSize(i);
            this.mWidthMode = android.view.View.MeasureSpec.getMode(i);
            if (this.mWidthMode == 0 && !com.android.internal.widget.RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = android.view.View.MeasureSpec.getSize(i2);
            this.mHeightMode = android.view.View.MeasureSpec.getMode(i2);
            if (this.mHeightMode == 0 && !com.android.internal.widget.RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mHeight = 0;
            }
        }

        void setMeasuredDimensionFromChildren(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.mRecyclerView.defaultOnMeasure(i, i2);
                return;
            }
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MAX_VALUE;
            for (int i7 = 0; i7 < childCount; i7++) {
                android.view.View childAt = getChildAt(i7);
                android.graphics.Rect rect = this.mRecyclerView.mTempRect;
                getDecoratedBoundsWithMargins(childAt, rect);
                if (rect.left < i6) {
                    i6 = rect.left;
                }
                if (rect.right > i3) {
                    i3 = rect.right;
                }
                if (rect.top < i4) {
                    i4 = rect.top;
                }
                if (rect.bottom > i5) {
                    i5 = rect.bottom;
                }
            }
            this.mRecyclerView.mTempRect.set(i6, i4, i3, i5);
            setMeasuredDimension(this.mRecyclerView.mTempRect, i, i2);
        }

        public void setMeasuredDimension(android.graphics.Rect rect, int i, int i2) {
            setMeasuredDimension(chooseSize(i, rect.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth()), chooseSize(i2, rect.height() + getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
        }

        public void requestLayout() {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.requestLayout();
            }
        }

        public void assertInLayoutOrScroll(java.lang.String str) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertInLayoutOrScroll(str);
            }
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = android.view.View.MeasureSpec.getMode(i);
            int size = android.view.View.MeasureSpec.getSize(i);
            switch (mode) {
                case Integer.MIN_VALUE:
                    return java.lang.Math.min(size, java.lang.Math.max(i2, i3));
                case 1073741824:
                    return size;
                default:
                    return java.lang.Math.max(i2, i3);
            }
        }

        public void assertNotInLayoutOrScroll(java.lang.String str) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.assertNotInLayoutOrScroll(str);
            }
        }

        public void setAutoMeasureEnabled(boolean z) {
            this.mAutoMeasure = z;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public final void setItemPrefetchEnabled(boolean z) {
            if (z != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = z;
                this.mPrefetchMaxCountObserved = 0;
                if (this.mRecyclerView != null) {
                    this.mRecyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int i, com.android.internal.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        void dispatchAttachedToWindow(com.android.internal.widget.RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            onAttachedToWindow(recyclerView);
        }

        void dispatchDetachedFromWindow(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.Recycler recycler) {
            this.mIsAttachedToWindow = false;
            onDetachedFromWindow(recyclerView, recycler);
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public void postOnAnimation(java.lang.Runnable runnable) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.postOnAnimation(runnable);
            }
        }

        public boolean removeCallbacks(java.lang.Runnable runnable) {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void onAttachedToWindow(com.android.internal.widget.RecyclerView recyclerView) {
        }

        @java.lang.Deprecated
        public void onDetachedFromWindow(com.android.internal.widget.RecyclerView recyclerView) {
        }

        public void onDetachedFromWindow(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.Recycler recycler) {
            onDetachedFromWindow(recyclerView);
        }

        public boolean getClipToPadding() {
            return this.mRecyclerView != null && this.mRecyclerView.mClipToPadding;
        }

        public void onLayoutChildren(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            android.util.Log.e(com.android.internal.widget.RecyclerView.TAG, "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(com.android.internal.widget.RecyclerView.State state) {
        }

        public boolean checkLayoutParams(com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public com.android.internal.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof com.android.internal.widget.RecyclerView.LayoutParams) {
                return new com.android.internal.widget.RecyclerView.LayoutParams((com.android.internal.widget.RecyclerView.LayoutParams) layoutParams);
            }
            if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                return new com.android.internal.widget.RecyclerView.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            }
            return new com.android.internal.widget.RecyclerView.LayoutParams(layoutParams);
        }

        public com.android.internal.widget.RecyclerView.LayoutParams generateLayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            return new com.android.internal.widget.RecyclerView.LayoutParams(context, attributeSet);
        }

        public int scrollHorizontallyBy(int i, com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int scrollVerticallyBy(int i, com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public void scrollToPosition(int i) {
        }

        public void smoothScrollToPosition(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state, int i) {
            android.util.Log.e(com.android.internal.widget.RecyclerView.TAG, "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(com.android.internal.widget.RecyclerView.SmoothScroller smoothScroller) {
            if (this.mSmoothScroller != null && smoothScroller != this.mSmoothScroller && this.mSmoothScroller.isRunning()) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            this.mSmoothScroller.start(this.mRecyclerView, this);
        }

        public boolean isSmoothScrolling() {
            return this.mSmoothScroller != null && this.mSmoothScroller.isRunning();
        }

        public int getLayoutDirection() {
            return this.mRecyclerView.getLayoutDirection();
        }

        public void endAnimation(android.view.View view) {
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(com.android.internal.widget.RecyclerView.getChildViewHolderInt(view));
            }
        }

        public void addDisappearingView(android.view.View view) {
            addDisappearingView(view, -1);
        }

        public void addDisappearingView(android.view.View view, int i) {
            addViewInt(view, i, true);
        }

        public void addView(android.view.View view) {
            addView(view, -1);
        }

        public void addView(android.view.View view, int i) {
            addViewInt(view, i, false);
        }

        private void addViewInt(android.view.View view, int i, boolean z) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (z || childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(childViewHolderInt);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt);
            }
            com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
            if (childViewHolderInt.wasReturnedFromScrap() || childViewHolderInt.isScrap()) {
                if (childViewHolderInt.isScrap()) {
                    childViewHolderInt.unScrap();
                } else {
                    childViewHolderInt.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.mRecyclerView) {
                int indexOfChild = this.mChildHelper.indexOfChild(view);
                if (i == -1) {
                    i = this.mChildHelper.getChildCount();
                }
                if (indexOfChild == -1) {
                    throw new java.lang.IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(view));
                }
                if (indexOfChild != i) {
                    this.mRecyclerView.mLayout.moveView(indexOfChild, i);
                }
            } else {
                this.mChildHelper.addView(view, i, false);
                layoutParams.mInsetsDirty = true;
                if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                    this.mSmoothScroller.onChildAttachedToWindow(view);
                }
            }
            if (layoutParams.mPendingInvalidate) {
                childViewHolderInt.itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public void removeView(android.view.View view) {
            this.mChildHelper.removeView(view);
        }

        public void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                this.mChildHelper.removeViewAt(i);
            }
        }

        public void removeAllViews() {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                this.mChildHelper.removeViewAt(childCount);
            }
        }

        public int getBaseline() {
            return -1;
        }

        public int getPosition(android.view.View view) {
            return ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getItemViewType(android.view.View view) {
            return com.android.internal.widget.RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        public android.view.View findContainingItemView(android.view.View view) {
            android.view.View findContainingItemView;
            if (this.mRecyclerView == null || (findContainingItemView = this.mRecyclerView.findContainingItemView(view)) == null || this.mChildHelper.isHidden(findContainingItemView)) {
                return null;
            }
            return findContainingItemView;
        }

        public android.view.View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                android.view.View childAt = getChildAt(i2);
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(childAt);
                if (childViewHolderInt != null && childViewHolderInt.getLayoutPosition() == i && !childViewHolderInt.shouldIgnore() && (this.mRecyclerView.mState.isPreLayout() || !childViewHolderInt.isRemoved())) {
                    return childAt;
                }
            }
            return null;
        }

        public void detachView(android.view.View view) {
            int indexOfChild = this.mChildHelper.indexOfChild(view);
            if (indexOfChild >= 0) {
                detachViewInternal(indexOfChild, view);
            }
        }

        public void detachViewAt(int i) {
            detachViewInternal(i, getChildAt(i));
        }

        private void detachViewInternal(int i, android.view.View view) {
            this.mChildHelper.detachViewFromParent(i);
        }

        public void attachView(android.view.View view, int i, com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(childViewHolderInt);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt);
            }
            this.mChildHelper.attachViewToParent(view, i, layoutParams, childViewHolderInt.isRemoved());
        }

        public void attachView(android.view.View view, int i) {
            attachView(view, i, (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams());
        }

        public void attachView(android.view.View view) {
            attachView(view, -1);
        }

        public void removeDetachedView(android.view.View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void moveView(int i, int i2) {
            android.view.View childAt = getChildAt(i);
            if (childAt == null) {
                throw new java.lang.IllegalArgumentException("Cannot move a child from non-existing index:" + i);
            }
            detachViewAt(i);
            attachView(childAt, i2);
        }

        public void detachAndScrapView(android.view.View view, com.android.internal.widget.RecyclerView.Recycler recycler) {
            scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(view), view);
        }

        public void detachAndScrapViewAt(int i, com.android.internal.widget.RecyclerView.Recycler recycler) {
            scrapOrRecycleView(recycler, i, getChildAt(i));
        }

        public void removeAndRecycleView(android.view.View view, com.android.internal.widget.RecyclerView.Recycler recycler) {
            removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int i, com.android.internal.widget.RecyclerView.Recycler recycler) {
            android.view.View childAt = getChildAt(i);
            removeViewAt(i);
            recycler.recycleView(childAt);
        }

        public int getChildCount() {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildCount();
            }
            return 0;
        }

        public android.view.View getChildAt(int i) {
            if (this.mChildHelper != null) {
                return this.mChildHelper.getChildAt(i);
            }
            return null;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getPaddingLeft() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingTop() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPaddingRight() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingBottom() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingStart() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingStart();
            }
            return 0;
        }

        public int getPaddingEnd() {
            if (this.mRecyclerView != null) {
                return this.mRecyclerView.getPaddingEnd();
            }
            return 0;
        }

        public boolean isFocused() {
            return this.mRecyclerView != null && this.mRecyclerView.isFocused();
        }

        public boolean hasFocus() {
            return this.mRecyclerView != null && this.mRecyclerView.hasFocus();
        }

        public android.view.View getFocusedChild() {
            android.view.View focusedChild;
            if (this.mRecyclerView == null || (focusedChild = this.mRecyclerView.getFocusedChild()) == null || this.mChildHelper.isHidden(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public int getItemCount() {
            com.android.internal.widget.RecyclerView.Adapter adapter = this.mRecyclerView != null ? this.mRecyclerView.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public void offsetChildrenHorizontal(int i) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenHorizontal(i);
            }
        }

        public void offsetChildrenVertical(int i) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.offsetChildrenVertical(i);
            }
        }

        public void ignoreView(android.view.View view) {
            if (view.getParent() != this.mRecyclerView || this.mRecyclerView.indexOfChild(view) == -1) {
                throw new java.lang.IllegalArgumentException("View should be fully attached to be ignored");
            }
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.addFlags(128);
            this.mRecyclerView.mViewInfoStore.removeViewHolder(childViewHolderInt);
        }

        public void stopIgnoringView(android.view.View view) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.stopIgnoring();
            childViewHolderInt.resetInternal();
            childViewHolderInt.addFlags(4);
        }

        public void detachAndScrapAttachedViews(com.android.internal.widget.RecyclerView.Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                scrapOrRecycleView(recycler, childCount, getChildAt(childCount));
            }
        }

        private void scrapOrRecycleView(com.android.internal.widget.RecyclerView.Recycler recycler, int i, android.view.View view) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.shouldIgnore()) {
                return;
            }
            if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                removeViewAt(i);
                recycler.recycleViewHolderInternal(childViewHolderInt);
            } else {
                detachViewAt(i);
                recycler.scrapView(view);
                this.mRecyclerView.mViewInfoStore.onViewDetached(childViewHolderInt);
            }
        }

        void removeAndRecycleScrapInt(com.android.internal.widget.RecyclerView.Recycler recycler) {
            int scrapCount = recycler.getScrapCount();
            for (int i = scrapCount - 1; i >= 0; i--) {
                android.view.View scrapViewAt = recycler.getScrapViewAt(i);
                com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(scrapViewAt);
                if (!childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.setIsRecyclable(false);
                    if (childViewHolderInt.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(scrapViewAt, false);
                    }
                    if (this.mRecyclerView.mItemAnimator != null) {
                        this.mRecyclerView.mItemAnimator.endAnimation(childViewHolderInt);
                    }
                    childViewHolderInt.setIsRecyclable(true);
                    recycler.quickRecycleScrapView(scrapViewAt);
                }
            }
            recycler.clearScrap();
            if (scrapCount > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void measureChild(android.view.View view, int i, int i2) {
            com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
            android.graphics.Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int i3 = i + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right;
            int i4 = i2 + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + i3, layoutParams.width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + i4, layoutParams.height, canScrollVertically());
            if (shouldMeasureChild(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        boolean shouldReMeasureChild(android.view.View view, int i, int i2, com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
            return (this.mMeasurementCacheEnabled && isMeasurementUpToDate(view.getMeasuredWidth(), i, layoutParams.width) && isMeasurementUpToDate(view.getMeasuredHeight(), i2, layoutParams.height)) ? false : true;
        }

        boolean shouldMeasureChild(android.view.View view, int i, int i2, com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
            return (!view.isLayoutRequested() && this.mMeasurementCacheEnabled && isMeasurementUpToDate(view.getWidth(), i, layoutParams.width) && isMeasurementUpToDate(view.getHeight(), i2, layoutParams.height)) ? false : true;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public void setMeasurementCacheEnabled(boolean z) {
            this.mMeasurementCacheEnabled = z;
        }

        private static boolean isMeasurementUpToDate(int i, int i2, int i3) {
            int mode = android.view.View.MeasureSpec.getMode(i2);
            int size = android.view.View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            switch (mode) {
                case Integer.MIN_VALUE:
                    if (size >= i) {
                        break;
                    }
                    break;
                case 0:
                    break;
                case 1073741824:
                    if (size == i) {
                        break;
                    }
                    break;
            }
            return false;
        }

        public void measureChildWithMargins(android.view.View view, int i, int i2) {
            com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
            android.graphics.Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int i3 = i + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right;
            int i4 = i2 + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + i3, layoutParams.width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + i4, layoutParams.height, canScrollVertically());
            if (shouldMeasureChild(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        @java.lang.Deprecated
        public static int getChildMeasureSpec(int i, int i2, int i3, boolean z) {
            int i4 = i - i2;
            int i5 = 0;
            int max = java.lang.Math.max(0, i4);
            if (z) {
                if (i3 >= 0) {
                    i5 = 1073741824;
                } else {
                    i3 = 0;
                }
            } else if (i3 >= 0) {
                i5 = 1073741824;
            } else if (i3 == -1) {
                i3 = max;
                i5 = 1073741824;
            } else if (i3 != -2) {
                i3 = 0;
            } else {
                i5 = Integer.MIN_VALUE;
                i3 = max;
            }
            return android.view.View.MeasureSpec.makeMeasureSpec(i3, i5);
        }

        public static int getChildMeasureSpec(int i, int i2, int i3, int i4, boolean z) {
            int max = java.lang.Math.max(0, i - i3);
            if (z) {
                if (i4 >= 0) {
                    i2 = 1073741824;
                } else if (i4 == -1) {
                    switch (i2) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            i4 = max;
                            break;
                        case 0:
                            i2 = 0;
                            i4 = 0;
                            break;
                        default:
                            i2 = 0;
                            i4 = 0;
                            break;
                    }
                } else {
                    if (i4 == -2) {
                        i2 = 0;
                        i4 = 0;
                    }
                    i2 = 0;
                    i4 = 0;
                }
            } else if (i4 >= 0) {
                i2 = 1073741824;
            } else if (i4 == -1) {
                i4 = max;
            } else {
                if (i4 == -2) {
                    if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                        i2 = Integer.MIN_VALUE;
                        i4 = max;
                    } else {
                        i4 = max;
                        i2 = 0;
                    }
                }
                i2 = 0;
                i4 = 0;
            }
            return android.view.View.MeasureSpec.makeMeasureSpec(i4, i2);
        }

        public int getDecoratedMeasuredWidth(android.view.View view) {
            android.graphics.Rect rect = ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedMeasuredHeight(android.view.View view) {
            android.graphics.Rect rect = ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public void layoutDecorated(android.view.View view, int i, int i2, int i3, int i4) {
            android.graphics.Rect rect = ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets;
            view.layout(i + rect.left, i2 + rect.top, i3 - rect.right, i4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(android.view.View view, int i, int i2, int i3, int i4) {
            com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
            android.graphics.Rect rect = layoutParams.mDecorInsets;
            view.layout(i + rect.left + layoutParams.leftMargin, i2 + rect.top + layoutParams.topMargin, (i3 - rect.right) - layoutParams.rightMargin, (i4 - rect.bottom) - layoutParams.bottomMargin);
        }

        public void getTransformedBoundingBox(android.view.View view, boolean z, android.graphics.Rect rect) {
            android.graphics.Matrix matrix;
            if (z) {
                android.graphics.Rect rect2 = ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.mRecyclerView != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
                android.graphics.RectF rectF = this.mRecyclerView.mTempRectF;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) java.lang.Math.floor(rectF.left), (int) java.lang.Math.floor(rectF.top), (int) java.lang.Math.ceil(rectF.right), (int) java.lang.Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public void getDecoratedBoundsWithMargins(android.view.View view, android.graphics.Rect rect) {
            com.android.internal.widget.RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
        }

        public int getDecoratedLeft(android.view.View view) {
            return view.getLeft() - getLeftDecorationWidth(view);
        }

        public int getDecoratedTop(android.view.View view) {
            return view.getTop() - getTopDecorationHeight(view);
        }

        public int getDecoratedRight(android.view.View view) {
            return view.getRight() + getRightDecorationWidth(view);
        }

        public int getDecoratedBottom(android.view.View view) {
            return view.getBottom() + getBottomDecorationHeight(view);
        }

        public void calculateItemDecorationsForChild(android.view.View view, android.graphics.Rect rect) {
            if (this.mRecyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(this.mRecyclerView.getItemDecorInsetsForChild(view));
            }
        }

        public int getTopDecorationHeight(android.view.View view) {
            return ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top;
        }

        public int getBottomDecorationHeight(android.view.View view) {
            return ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.bottom;
        }

        public int getLeftDecorationWidth(android.view.View view) {
            return ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left;
        }

        public int getRightDecorationWidth(android.view.View view) {
            return ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.right;
        }

        public android.view.View onFocusSearchFailed(android.view.View view, int i, com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            return null;
        }

        public android.view.View onInterceptFocusSearch(android.view.View view, int i) {
            return null;
        }

        public boolean requestChildRectangleOnScreen(com.android.internal.widget.RecyclerView recyclerView, android.view.View view, android.graphics.Rect rect, boolean z) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width2 = rect.width() + left;
            int height2 = rect.height() + top;
            int i = left - paddingLeft;
            int min = java.lang.Math.min(0, i);
            int i2 = top - paddingTop;
            int min2 = java.lang.Math.min(0, i2);
            int i3 = width2 - width;
            int max = java.lang.Math.max(0, i3);
            int max2 = java.lang.Math.max(0, height2 - height);
            if (getLayoutDirection() == 1) {
                if (max == 0) {
                    max = java.lang.Math.max(min, i3);
                }
            } else {
                if (min == 0) {
                    min = java.lang.Math.min(i, max);
                }
                max = min;
            }
            if (min2 == 0) {
                min2 = java.lang.Math.min(i2, max2);
            }
            if (max == 0 && min2 == 0) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(max, min2);
            } else {
                recyclerView.smoothScrollBy(max, min2);
            }
            return true;
        }

        @java.lang.Deprecated
        public boolean onRequestChildFocus(com.android.internal.widget.RecyclerView recyclerView, android.view.View view, android.view.View view2) {
            return isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public boolean onRequestChildFocus(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state, android.view.View view, android.view.View view2) {
            return onRequestChildFocus(recyclerView, view, view2);
        }

        public void onAdapterChanged(com.android.internal.widget.RecyclerView.Adapter adapter, com.android.internal.widget.RecyclerView.Adapter adapter2) {
        }

        public boolean onAddFocusables(com.android.internal.widget.RecyclerView recyclerView, java.util.ArrayList<android.view.View> arrayList, int i, int i2) {
            return false;
        }

        public void onItemsChanged(com.android.internal.widget.RecyclerView recyclerView) {
        }

        public void onItemsAdded(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsRemoved(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(com.android.internal.widget.RecyclerView recyclerView, int i, int i2, java.lang.Object obj) {
            onItemsUpdated(recyclerView, i, i2);
        }

        public void onItemsMoved(com.android.internal.widget.RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public int computeHorizontalScrollExtent(com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int computeVerticalScrollRange(com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public void onMeasure(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i, int i2) {
            this.mRecyclerView.defaultOnMeasure(i, i2);
        }

        public void setMeasuredDimension(int i, int i2) {
            this.mRecyclerView.setMeasuredDimension(i, i2);
        }

        public int getMinimumWidth() {
            return this.mRecyclerView.getMinimumWidth();
        }

        public int getMinimumHeight() {
            return this.mRecyclerView.getMinimumHeight();
        }

        public android.os.Parcelable onSaveInstanceState() {
            return null;
        }

        public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        }

        void stopSmoothScroller() {
            if (this.mSmoothScroller != null) {
                this.mSmoothScroller.stop();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSmoothScrollerStopped(com.android.internal.widget.RecyclerView.SmoothScroller smoothScroller) {
            if (this.mSmoothScroller == smoothScroller) {
                this.mSmoothScroller = null;
            }
        }

        public void onScrollStateChanged(int i) {
        }

        public void removeAndRecycleAllViews(com.android.internal.widget.RecyclerView.Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!com.android.internal.widget.RecyclerView.getChildViewHolderInt(getChildAt(childCount)).shouldIgnore()) {
                    removeAndRecycleViewAt(childCount, recycler);
                }
            }
        }

        void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfo);
        }

        public void onInitializeAccessibilityNodeInfo(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfo.addAction(8192);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfo.addAction(4096);
                accessibilityNodeInfo.setScrollable(true);
            }
            accessibilityNodeInfo.setCollectionInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            if (this.mRecyclerView == null || accessibilityEvent == null) {
                return;
            }
            boolean z = true;
            if (!this.mRecyclerView.canScrollVertically(1) && !this.mRecyclerView.canScrollVertically(-1) && !this.mRecyclerView.canScrollHorizontally(-1) && !this.mRecyclerView.canScrollHorizontally(1)) {
                z = false;
            }
            accessibilityEvent.setScrollable(z);
            if (this.mRecyclerView.mAdapter != null) {
                accessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
            }
        }

        void onInitializeAccessibilityNodeInfoForItem(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && !this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfo);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(canScrollVertically() ? getPosition(view) : 0, 1, canScrollHorizontally() ? getPosition(view) : 0, 1, false, false));
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int getSelectionModeForAccessibility(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            return 0;
        }

        public int getRowCountForAccessibility(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !canScrollVertically()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getColumnCountForAccessibility(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            if (this.mRecyclerView == null || this.mRecyclerView.mAdapter == null || !canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public boolean isLayoutHierarchical(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
            return false;
        }

        boolean performAccessibilityAction(int i, android.os.Bundle bundle) {
            return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, i, bundle);
        }

        public boolean performAccessibilityAction(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i, android.os.Bundle bundle) {
            int i2;
            int i3;
            if (this.mRecyclerView == null) {
                return false;
            }
            switch (i) {
                case 4096:
                    if (!this.mRecyclerView.canScrollVertically(1)) {
                        i2 = 0;
                    } else {
                        i2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                    }
                    if (!this.mRecyclerView.canScrollHorizontally(1)) {
                        i3 = 0;
                        break;
                    } else {
                        i3 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                        break;
                    }
                case 8192:
                    if (!this.mRecyclerView.canScrollVertically(-1)) {
                        i2 = 0;
                    } else {
                        i2 = -((getHeight() - getPaddingTop()) - getPaddingBottom());
                    }
                    if (!this.mRecyclerView.canScrollHorizontally(-1)) {
                        i3 = 0;
                        break;
                    } else {
                        i3 = -((getWidth() - getPaddingLeft()) - getPaddingRight());
                        break;
                    }
                default:
                    i2 = 0;
                    i3 = 0;
                    break;
            }
            if (i2 == 0 && i3 == 0) {
                return false;
            }
            this.mRecyclerView.smoothScrollBy(i3, i2);
            return true;
        }

        boolean performAccessibilityActionForItem(android.view.View view, int i, android.os.Bundle bundle) {
            return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, i, bundle);
        }

        public boolean performAccessibilityActionForItem(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, android.view.View view, int i, android.os.Bundle bundle) {
            return false;
        }

        public static com.android.internal.widget.RecyclerView.LayoutManager.Properties getProperties(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            com.android.internal.widget.RecyclerView.LayoutManager.Properties properties = new com.android.internal.widget.RecyclerView.LayoutManager.Properties();
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RecyclerView, i, i2);
            properties.orientation = obtainStyledAttributes.getInt(0, 1);
            properties.spanCount = obtainStyledAttributes.getInt(4, 1);
            properties.reverseLayout = obtainStyledAttributes.getBoolean(3, false);
            properties.stackFromEnd = obtainStyledAttributes.getBoolean(5, false);
            obtainStyledAttributes.recycle();
            return properties;
        }

        void setExactMeasureSpecsFrom(com.android.internal.widget.RecyclerView recyclerView) {
            setMeasureSpecs(android.view.View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        boolean shouldMeasureTwice() {
            return false;
        }

        boolean hasFlexibleChildInBothOrientations() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public static abstract class ItemDecoration {
        public void onDraw(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state) {
            onDraw(canvas, recyclerView);
        }

        @java.lang.Deprecated
        public void onDraw(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView) {
        }

        public void onDrawOver(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state) {
            onDrawOver(canvas, recyclerView);
        }

        @java.lang.Deprecated
        public void onDrawOver(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView) {
        }

        @java.lang.Deprecated
        public void getItemOffsets(android.graphics.Rect rect, int i, com.android.internal.widget.RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(android.graphics.Rect rect, android.view.View view, com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.State state) {
            getItemOffsets(rect, ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }
    }

    public static class SimpleOnItemTouchListener implements com.android.internal.widget.RecyclerView.OnItemTouchListener {
        @Override // com.android.internal.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(com.android.internal.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
            return false;
        }

        @Override // com.android.internal.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(com.android.internal.widget.RecyclerView recyclerView, android.view.MotionEvent motionEvent) {
        }

        @Override // com.android.internal.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(com.android.internal.widget.RecyclerView recyclerView, int i) {
        }

        public void onScrolled(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final java.util.List<java.lang.Object> FULLUPDATE_PAYLOADS = java.util.Collections.EMPTY_LIST;
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final android.view.View itemView;
        private int mFlags;
        java.lang.ref.WeakReference<com.android.internal.widget.RecyclerView> mNestedRecyclerView;
        com.android.internal.widget.RecyclerView mOwnerRecyclerView;
        int mPosition = -1;
        int mOldPosition = -1;
        long mItemId = -1;
        int mItemViewType = -1;
        int mPreLayoutPosition = -1;
        com.android.internal.widget.RecyclerView.ViewHolder mShadowedHolder = null;
        com.android.internal.widget.RecyclerView.ViewHolder mShadowingHolder = null;
        java.util.List<java.lang.Object> mPayloads = null;
        java.util.List<java.lang.Object> mUnmodifiedPayloads = null;
        private int mIsRecyclableCount = 0;
        private com.android.internal.widget.RecyclerView.Recycler mScrapContainer = null;
        private boolean mInChangeScrap = false;
        private int mWasImportantForAccessibilityBeforeHidden = 0;
        int mPendingAccessibilityState = -1;

        public ViewHolder(android.view.View view) {
            if (view == null) {
                throw new java.lang.IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        void flagRemovedAndOffsetPosition(int i, int i2, boolean z) {
            addFlags(8);
            offsetPosition(i2, z);
            this.mPosition = i;
        }

        void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            if (this.itemView.getLayoutParams() != null) {
                ((com.android.internal.widget.RecyclerView.LayoutParams) this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        boolean shouldIgnore() {
            return (this.mFlags & 128) != 0;
        }

        @java.lang.Deprecated
        public final int getPosition() {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getLayoutPosition() {
            return this.mPreLayoutPosition == -1 ? this.mPosition : this.mPreLayoutPosition;
        }

        public final int getAdapterPosition() {
            if (this.mOwnerRecyclerView == null) {
                return -1;
            }
            return this.mOwnerRecyclerView.getAdapterPositionFor(this);
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        boolean isScrap() {
            return this.mScrapContainer != null;
        }

        void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        void stopIgnoring() {
            this.mFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }

        void setScrapContainer(com.android.internal.widget.RecyclerView.Recycler recycler, boolean z) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = z;
        }

        boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        boolean hasAnyOfTheFlags(int i) {
            return (i & this.mFlags) != 0;
        }

        boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        boolean isAdapterPositionUnknown() {
            return (this.mFlags & 512) != 0 || isInvalid();
        }

        void setFlags(int i, int i2) {
            this.mFlags = (i & i2) | (this.mFlags & (~i2));
        }

        void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        void addChangePayload(java.lang.Object obj) {
            if (obj != null) {
                if ((1024 & this.mFlags) == 0) {
                    createPayloadsIfNeeded();
                    this.mPayloads.add(obj);
                    return;
                }
                return;
            }
            addFlags(1024);
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new java.util.ArrayList();
                this.mUnmodifiedPayloads = java.util.Collections.unmodifiableList(this.mPayloads);
            }
        }

        void clearPayload() {
            if (this.mPayloads != null) {
                this.mPayloads.clear();
            }
            this.mFlags &= -1025;
        }

        java.util.List<java.lang.Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) == 0) {
                if (this.mPayloads == null || this.mPayloads.size() == 0) {
                    return FULLUPDATE_PAYLOADS;
                }
                return this.mUnmodifiedPayloads;
            }
            return FULLUPDATE_PAYLOADS;
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            com.android.internal.widget.RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onEnteredHiddenState(com.android.internal.widget.RecyclerView recyclerView) {
            this.mWasImportantForAccessibilityBeforeHidden = this.itemView.getImportantForAccessibility();
            recyclerView.setChildImportantForAccessibilityInternal(this, 4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onLeftHiddenState(com.android.internal.widget.RecyclerView recyclerView) {
            recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("ViewHolder{" + java.lang.Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                sb.append(" scrap ").append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            if (needsUpdate()) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (isAdapterPositionUnknown()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void setIsRecyclable(boolean z) {
            int i = this.mIsRecyclableCount;
            this.mIsRecyclableCount = z ? i - 1 : i + 1;
            if (this.mIsRecyclableCount < 0) {
                this.mIsRecyclableCount = 0;
                android.util.Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && this.mIsRecyclableCount == 1) {
                this.mFlags |= 16;
            } else if (z && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !this.itemView.hasTransientState();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && this.itemView.hasTransientState();
        }

        boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }
    }

    boolean setChildImportantForAccessibilityInternal(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
        if (isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = i;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        viewHolder.itemView.setImportantForAccessibility(i);
        return true;
    }

    void dispatchPendingImportantForAccessibilityChanges() {
        int i;
        for (int size = this.mPendingAccessibilityImportanceChange.size() - 1; size >= 0; size--) {
            com.android.internal.widget.RecyclerView.ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(size);
            if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (i = viewHolder.mPendingAccessibilityState) != -1) {
                viewHolder.itemView.setImportantForAccessibility(i);
                viewHolder.mPendingAccessibilityState = -1;
            }
        }
        this.mPendingAccessibilityImportanceChange.clear();
    }

    int getAdapterPositionFor(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.hasAnyOfTheFlags(524) || !viewHolder.isBound()) {
            return -1;
        }
        return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        final android.graphics.Rect mDecorInsets;
        boolean mInsetsDirty;
        boolean mPendingInvalidate;
        com.android.internal.widget.RecyclerView.ViewHolder mViewHolder;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mDecorInsets = new android.graphics.Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mDecorInsets = new android.graphics.Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mDecorInsets = new android.graphics.Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mDecorInsets = new android.graphics.Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public LayoutParams(com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
            super((android.view.ViewGroup.LayoutParams) layoutParams);
            this.mDecorInsets = new android.graphics.Rect();
            this.mInsetsDirty = true;
            this.mPendingInvalidate = false;
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        @java.lang.Deprecated
        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int i, int i2) {
        }

        public void onItemRangeChanged(int i, int i2, java.lang.Object obj) {
            onItemRangeChanged(i, i2);
        }

        public void onItemRangeInserted(int i, int i2) {
        }

        public void onItemRangeRemoved(int i, int i2) {
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
        }
    }

    public static abstract class SmoothScroller {
        private com.android.internal.widget.RecyclerView.LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private com.android.internal.widget.RecyclerView mRecyclerView;
        private boolean mRunning;
        private android.view.View mTargetView;
        private int mTargetPosition = -1;
        private final com.android.internal.widget.RecyclerView.SmoothScroller.Action mRecyclingAction = new com.android.internal.widget.RecyclerView.SmoothScroller.Action(0, 0);

        public interface ScrollVectorProvider {
            android.graphics.PointF computeScrollVectorForPosition(int i);
        }

        protected abstract void onSeekTargetStep(int i, int i2, com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.RecyclerView.SmoothScroller.Action action);

        protected abstract void onStart();

        protected abstract void onStop();

        protected abstract void onTargetFound(android.view.View view, com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.RecyclerView.SmoothScroller.Action action);

        void start(com.android.internal.widget.RecyclerView recyclerView, com.android.internal.widget.RecyclerView.LayoutManager layoutManager) {
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            if (this.mTargetPosition == -1) {
                throw new java.lang.IllegalArgumentException("Invalid target position");
            }
            this.mRecyclerView.mState.mTargetPosition = this.mTargetPosition;
            this.mRunning = true;
            this.mPendingInitialRun = true;
            this.mTargetView = findViewByPosition(getTargetPosition());
            onStart();
            this.mRecyclerView.mViewFlinger.postOnAnimation();
        }

        public void setTargetPosition(int i) {
            this.mTargetPosition = i;
        }

        public com.android.internal.widget.RecyclerView.LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        protected final void stop() {
            if (!this.mRunning) {
                return;
            }
            onStop();
            this.mRecyclerView.mState.mTargetPosition = -1;
            this.mTargetView = null;
            this.mTargetPosition = -1;
            this.mPendingInitialRun = false;
            this.mRunning = false;
            this.mLayoutManager.onSmoothScrollerStopped(this);
            this.mLayoutManager = null;
            this.mRecyclerView = null;
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAnimation(int i, int i2) {
            com.android.internal.widget.RecyclerView recyclerView = this.mRecyclerView;
            if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null) {
                stop();
            }
            this.mPendingInitialRun = false;
            if (this.mTargetView != null) {
                if (getChildPosition(this.mTargetView) == this.mTargetPosition) {
                    onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    stop();
                } else {
                    android.util.Log.e(com.android.internal.widget.RecyclerView.TAG, "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                onSeekTargetStep(i, i2, recyclerView.mState, this.mRecyclingAction);
                boolean hasJumpTarget = this.mRecyclingAction.hasJumpTarget();
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (hasJumpTarget) {
                    if (this.mRunning) {
                        this.mPendingInitialRun = true;
                        recyclerView.mViewFlinger.postOnAnimation();
                    } else {
                        stop();
                    }
                }
            }
        }

        public int getChildPosition(android.view.View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public android.view.View findViewByPosition(int i) {
            return this.mRecyclerView.mLayout.findViewByPosition(i);
        }

        @java.lang.Deprecated
        public void instantScrollToPosition(int i) {
            this.mRecyclerView.scrollToPosition(i);
        }

        protected void onChildAttachedToWindow(android.view.View view) {
            if (getChildPosition(view) == getTargetPosition()) {
                this.mTargetView = view;
            }
        }

        protected void normalize(android.graphics.PointF pointF) {
            double sqrt = java.lang.Math.sqrt((pointF.x * pointF.x) + (pointF.y * pointF.y));
            pointF.x = (float) (pointF.x / sqrt);
            pointF.y = (float) (pointF.y / sqrt);
        }

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean mChanged;
            private int mConsecutiveUpdates;
            private int mDuration;
            private int mDx;
            private int mDy;
            private android.view.animation.Interpolator mInterpolator;
            private int mJumpToPosition;

            public Action(int i, int i2) {
                this(i, i2, Integer.MIN_VALUE, null);
            }

            public Action(int i, int i2, int i3) {
                this(i, i2, i3, null);
            }

            public Action(int i, int i2, int i3, android.view.animation.Interpolator interpolator) {
                this.mJumpToPosition = -1;
                this.mChanged = false;
                this.mConsecutiveUpdates = 0;
                this.mDx = i;
                this.mDy = i2;
                this.mDuration = i3;
                this.mInterpolator = interpolator;
            }

            public void jumpTo(int i) {
                this.mJumpToPosition = i;
            }

            boolean hasJumpTarget() {
                return this.mJumpToPosition >= 0;
            }

            void runIfNecessary(com.android.internal.widget.RecyclerView recyclerView) {
                if (this.mJumpToPosition >= 0) {
                    int i = this.mJumpToPosition;
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(i);
                    this.mChanged = false;
                    return;
                }
                if (this.mChanged) {
                    validate();
                    if (this.mInterpolator == null) {
                        if (this.mDuration == Integer.MIN_VALUE) {
                            recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                        } else {
                            recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                        }
                    } else {
                        recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                    }
                    this.mConsecutiveUpdates++;
                    if (this.mConsecutiveUpdates > 10) {
                        android.util.Log.e(com.android.internal.widget.RecyclerView.TAG, "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.mChanged = false;
                    return;
                }
                this.mConsecutiveUpdates = 0;
            }

            private void validate() {
                if (this.mInterpolator != null && this.mDuration < 1) {
                    throw new java.lang.IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.mDuration < 1) {
                    throw new java.lang.IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public int getDx() {
                return this.mDx;
            }

            public void setDx(int i) {
                this.mChanged = true;
                this.mDx = i;
            }

            public int getDy() {
                return this.mDy;
            }

            public void setDy(int i) {
                this.mChanged = true;
                this.mDy = i;
            }

            public int getDuration() {
                return this.mDuration;
            }

            public void setDuration(int i) {
                this.mChanged = true;
                this.mDuration = i;
            }

            public android.view.animation.Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            public void setInterpolator(android.view.animation.Interpolator interpolator) {
                this.mChanged = true;
                this.mInterpolator = interpolator;
            }

            public void update(int i, int i2, int i3, android.view.animation.Interpolator interpolator) {
                this.mDx = i;
                this.mDy = i2;
                this.mDuration = i3;
                this.mInterpolator = interpolator;
                this.mChanged = true;
            }
        }
    }

    static class AdapterDataObservable extends android.database.Observable<com.android.internal.widget.RecyclerView.AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return !this.mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((com.android.internal.widget.RecyclerView.AdapterDataObserver) this.mObservers.get(size)).onChanged();
            }
        }

        public void notifyItemRangeChanged(int i, int i2) {
            notifyItemRangeChanged(i, i2, null);
        }

        public void notifyItemRangeChanged(int i, int i2, java.lang.Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((com.android.internal.widget.RecyclerView.AdapterDataObserver) this.mObservers.get(size)).onItemRangeChanged(i, i2, obj);
            }
        }

        public void notifyItemRangeInserted(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((com.android.internal.widget.RecyclerView.AdapterDataObserver) this.mObservers.get(size)).onItemRangeInserted(i, i2);
            }
        }

        public void notifyItemRangeRemoved(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((com.android.internal.widget.RecyclerView.AdapterDataObserver) this.mObservers.get(size)).onItemRangeRemoved(i, i2);
            }
        }

        public void notifyItemMoved(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((com.android.internal.widget.RecyclerView.AdapterDataObserver) this.mObservers.get(size)).onItemRangeMoved(i, i2, 1);
            }
        }
    }

    public static class SavedState extends android.view.AbsSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.widget.RecyclerView.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.widget.RecyclerView.SavedState>() { // from class: com.android.internal.widget.RecyclerView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.RecyclerView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.widget.RecyclerView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.RecyclerView.SavedState[] newArray(int i) {
                return new com.android.internal.widget.RecyclerView.SavedState[i];
            }
        };
        android.os.Parcelable mLayoutState;

        SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.mLayoutState = parcel.readParcelable(com.android.internal.widget.RecyclerView.LayoutManager.class.getClassLoader());
        }

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

        void copyFrom(com.android.internal.widget.RecyclerView.SavedState savedState) {
            this.mLayoutState = savedState.mLayoutState;
        }
    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private android.util.SparseArray<java.lang.Object> mData;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        private int mTargetPosition = -1;
        int mPreviousLayoutItemCount = 0;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        int mLayoutStep = 1;
        int mItemCount = 0;
        boolean mStructureChanged = false;
        boolean mInPreLayout = false;
        boolean mTrackOldChangeHolders = false;
        boolean mIsMeasuring = false;
        boolean mRunSimpleAnimations = false;
        boolean mRunPredictiveAnimations = false;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface LayoutState {
        }

        void assertLayoutStep(int i) {
            if ((this.mLayoutStep & i) == 0) {
                throw new java.lang.IllegalStateException("Layout state should be one of " + java.lang.Integer.toBinaryString(i) + " but it is " + java.lang.Integer.toBinaryString(this.mLayoutStep));
            }
        }

        com.android.internal.widget.RecyclerView.State reset() {
            this.mTargetPosition = -1;
            if (this.mData != null) {
                this.mData.clear();
            }
            this.mItemCount = 0;
            this.mStructureChanged = false;
            this.mIsMeasuring = false;
            return this;
        }

        void prepareForNestedPrefetch(com.android.internal.widget.RecyclerView.Adapter adapter) {
            this.mLayoutStep = 1;
            this.mItemCount = adapter.getItemCount();
            this.mStructureChanged = false;
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }

        public void remove(int i) {
            if (this.mData == null) {
                return;
            }
            this.mData.remove(i);
        }

        public <T> T get(int i) {
            if (this.mData == null) {
                return null;
            }
            return (T) this.mData.get(i);
        }

        public void put(int i, java.lang.Object obj) {
            if (this.mData == null) {
                this.mData = new android.util.SparseArray<>();
            }
            this.mData.put(i, obj);
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            return this.mTargetPosition != -1;
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public int getItemCount() {
            if (this.mInPreLayout) {
                return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
            }
            return this.mItemCount;
        }

        public java.lang.String toString() {
            return "State{mTargetPosition=" + this.mTargetPosition + ", mData=" + this.mData + ", mItemCount=" + this.mItemCount + ", mPreviousLayoutItemCount=" + this.mPreviousLayoutItemCount + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.mDeletedInvisibleItemCountSincePreviousLayout + ", mStructureChanged=" + this.mStructureChanged + ", mInPreLayout=" + this.mInPreLayout + ", mRunSimpleAnimations=" + this.mRunSimpleAnimations + ", mRunPredictiveAnimations=" + this.mRunPredictiveAnimations + '}';
        }
    }

    private class ItemAnimatorRestoreListener implements com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        @Override // com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorListener
        public void onAnimationFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (!viewHolder.shouldBeKeptAsChild() && !com.android.internal.widget.RecyclerView.this.removeAnimatingView(viewHolder.itemView) && viewHolder.isTmpDetached()) {
                com.android.internal.widget.RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorListener mListener = null;
        private java.util.ArrayList<com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener> mFinishedListeners = new java.util.ArrayList<>();
        private long mAddDuration = 120;
        private long mRemoveDuration = 120;
        private long mMoveDuration = 250;
        private long mChangeDuration = 250;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        interface ItemAnimatorListener {
            void onAnimationFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder);
        }

        public abstract boolean animateAppearance(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        public abstract void endAnimation(com.android.internal.widget.RecyclerView.ViewHolder viewHolder);

        public abstract void endAnimations();

        public abstract boolean isRunning();

        public abstract void runPendingAnimations();

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public void setMoveDuration(long j) {
            this.mMoveDuration = j;
        }

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public void setAddDuration(long j) {
            this.mAddDuration = j;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public void setRemoveDuration(long j) {
            this.mRemoveDuration = j;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public void setChangeDuration(long j) {
            this.mChangeDuration = j;
        }

        void setListener(com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }

        public com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo recordPreLayoutInformation(com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i, java.util.List<java.lang.Object> list) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        public com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo recordPostLayoutInformation(com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        static int buildAdapterChangeFlagsForAnimations(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            int i = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((i & 4) == 0) {
                int oldPosition = viewHolder.getOldPosition();
                int adapterPosition = viewHolder.getAdapterPosition();
                if (oldPosition != -1 && adapterPosition != -1 && oldPosition != adapterPosition) {
                    return i | 2048;
                }
                return i;
            }
            return i;
        }

        public final void dispatchAnimationFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            onAnimationFinished(viewHolder);
            if (this.mListener != null) {
                this.mListener.onAnimationFinished(viewHolder);
            }
        }

        public void onAnimationFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        }

        public final void dispatchAnimationStarted(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            onAnimationStarted(viewHolder);
        }

        public void onAnimationStarted(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        }

        public final boolean isRunning(com.android.internal.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean isRunning = isRunning();
            if (itemAnimatorFinishedListener != null) {
                if (!isRunning) {
                    itemAnimatorFinishedListener.onAnimationsFinished();
                } else {
                    this.mFinishedListeners.add(itemAnimatorFinishedListener);
                }
            }
            return isRunning;
        }

        public boolean canReuseUpdatedViewHolder(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, java.util.List<java.lang.Object> list) {
            return canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int size = this.mFinishedListeners.size();
            for (int i = 0; i < size; i++) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }

        public com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo obtainHolderInfo() {
            return new com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo();
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo setFrom(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
                return setFrom(viewHolder, 0);
            }

            public com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo setFrom(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
                android.view.View view = viewHolder.itemView;
                this.left = view.getLeft();
                this.top = view.getTop();
                this.right = view.getRight();
                this.bottom = view.getBottom();
                return this;
            }
        }
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        if (this.mChildDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(i, i2);
        }
        return this.mChildDrawingOrderCallback.onGetChildDrawingOrder(i, i2);
    }
}
