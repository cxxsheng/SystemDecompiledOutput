package android.widget;

/* loaded from: classes4.dex */
public abstract class AbsListView extends android.widget.AdapterView<android.widget.ListAdapter> implements android.text.TextWatcher, android.view.ViewTreeObserver.OnGlobalLayoutListener, android.widget.Filter.FilterListener, android.view.ViewTreeObserver.OnTouchModeChangeListener, android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    public static final int CHOICE_MODE_MULTIPLE = 2;
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final int INVALID_POINTER = -1;
    static final int LAYOUT_FORCE_BOTTOM = 3;
    static final int LAYOUT_FORCE_TOP = 1;
    static final int LAYOUT_MOVE_SELECTION = 6;
    static final int LAYOUT_NORMAL = 0;
    static final int LAYOUT_SET_SELECTION = 2;
    static final int LAYOUT_SPECIFIC = 4;
    static final int LAYOUT_SYNC = 5;
    static final int OVERSCROLL_LIMIT_DIVISOR = 3;
    private static final boolean PROFILE_FLINGING = false;
    private static final boolean PROFILE_SCROLLING = false;
    private static final java.lang.String TAG = "AbsListView";
    static final int TOUCH_MODE_DONE_WAITING = 2;
    static final int TOUCH_MODE_DOWN = 0;
    static final int TOUCH_MODE_FLING = 4;
    private static final int TOUCH_MODE_OFF = 1;
    private static final int TOUCH_MODE_ON = 0;
    static final int TOUCH_MODE_OVERFLING = 6;
    static final int TOUCH_MODE_OVERSCROLL = 5;
    static final int TOUCH_MODE_REST = -1;
    static final int TOUCH_MODE_SCROLL = 3;
    static final int TOUCH_MODE_TAP = 1;
    private static final int TOUCH_MODE_UNKNOWN = -1;
    public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL = 2;
    public static final int TRANSCRIPT_MODE_DISABLED = 0;
    public static final int TRANSCRIPT_MODE_NORMAL = 1;
    private static boolean sContentCaptureReportingEnabledByDeviceConfig = false;
    private static android.provider.DeviceConfig.OnPropertiesChangedListener sDeviceConfigChangeListener = null;
    static final android.view.animation.Interpolator sLinearInterpolator = new android.view.animation.LinearInterpolator();
    private android.widget.AbsListView.ListItemAccessibilityDelegate mAccessibilityDelegate;
    private int mActivePointerId;
    android.widget.ListAdapter mAdapter;
    boolean mAdapterHasStableIds;
    private int mCacheColorHint;
    boolean mCachingActive;
    boolean mCachingStarted;
    android.util.SparseBooleanArray mCheckStates;
    android.util.LongSparseArray<java.lang.Integer> mCheckedIdStates;
    int mCheckedItemCount;
    android.view.ActionMode mChoiceActionMode;
    int mChoiceMode;
    private java.lang.Runnable mClearScrollingCache;
    private android.view.ContextMenu.ContextMenuInfo mContextMenuInfo;
    android.widget.AbsListView.AdapterDataSetObserver mDataSetObserver;
    private android.view.inputmethod.InputConnection mDefInputConnection;
    private boolean mDeferNotifyDataSetChanged;
    private float mDensityScale;
    private android.widget.DifferentialMotionFlingHelper mDifferentialMotionFlingHelper;
    private int mDirection;
    boolean mDrawSelectorOnTop;
    public android.widget.EdgeEffect mEdgeGlowBottom;
    public android.widget.EdgeEffect mEdgeGlowTop;
    private android.widget.FastScroller mFastScroll;
    boolean mFastScrollAlwaysVisible;
    boolean mFastScrollEnabled;
    private int mFastScrollStyle;
    private boolean mFiltered;
    private int mFirstPositionDistanceGuess;
    private boolean mFlingProfilingStarted;
    private android.widget.AbsListView.FlingRunnable mFlingRunnable;
    private android.os.StrictMode.Span mFlingStrictSpan;
    private boolean mForceTranscriptScroll;
    private boolean mGlobalLayoutListenerAddedFilter;
    private android.view.HapticScrollFeedbackProvider mHapticScrollFeedbackProvider;
    private boolean mHasPerformedLongPress;
    private boolean mIsChildViewEnabled;
    private boolean mIsDetaching;
    final boolean[] mIsScrap;
    private int mLastHandledItemCount;
    private int mLastPositionDistanceGuess;
    private int mLastScrollState;
    private int mLastTouchMode;
    int mLastY;
    int mLayoutMode;
    android.graphics.Rect mListPadding;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    int mMotionCorrection;
    int mMotionPosition;
    int mMotionViewNewTop;
    int mMotionViewOriginalTop;
    int mMotionX;
    int mMotionY;
    android.widget.AbsListView.MultiChoiceModeWrapper mMultiChoiceModeCallback;
    private int mNestedYOffset;
    private android.widget.AbsListView.OnScrollListener mOnScrollListener;
    int mOverflingDistance;
    int mOverscrollDistance;
    int mOverscrollMax;
    private final java.lang.Thread mOwnerThread;
    private android.widget.AbsListView.CheckForKeyLongPress mPendingCheckForKeyLongPress;
    private android.widget.AbsListView.CheckForLongPress mPendingCheckForLongPress;
    private android.widget.AbsListView.CheckForTap mPendingCheckForTap;
    private android.widget.AbsListView.SavedState mPendingSync;
    private android.widget.AbsListView.PerformClick mPerformClick;
    android.widget.PopupWindow mPopup;
    private boolean mPopupHidden;
    java.lang.Runnable mPositionScrollAfterLayout;
    android.widget.AbsListView.AbsPositionScroller mPositionScroller;
    private android.widget.AbsListView.InputConnectionWrapper mPublicInputConnection;
    final android.widget.AbsListView.RecycleBin mRecycler;
    private android.widget.RemoteViewsAdapter mRemoteAdapter;
    private boolean mReportChildrenToContentCaptureOnNextUpdate;
    int mResurrectToPosition;
    private final int[] mScrollConsumed;
    android.view.View mScrollDown;
    private final int[] mScrollOffset;
    private boolean mScrollProfilingStarted;
    private android.os.StrictMode.Span mScrollStrictSpan;
    android.view.View mScrollUp;
    boolean mScrollingCacheEnabled;
    int mSelectedTop;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    android.graphics.drawable.Drawable mSelector;
    int mSelectorPosition;
    android.graphics.Rect mSelectorRect;
    private int[] mSelectorState;
    private boolean mSmoothScrollbarEnabled;
    boolean mStackFromBottom;
    android.widget.EditText mTextFilter;
    private boolean mTextFilterEnabled;
    private final float[] mTmpPoint;
    private android.graphics.Rect mTouchFrame;
    int mTouchMode;
    private java.lang.Runnable mTouchModeReset;
    private int mTouchSlop;
    private int mTranscriptMode;
    private float mVelocityScale;
    private android.view.VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;
    int mWidthMeasureSpec;

    public interface MultiChoiceModeListener extends android.view.ActionMode.Callback {
        void onItemCheckedStateChanged(android.view.ActionMode actionMode, int i, long j, boolean z);
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScroll(android.widget.AbsListView absListView, int i, int i2, int i3);

        void onScrollStateChanged(android.widget.AbsListView absListView, int i);
    }

    public interface RecyclerListener {
        void onMovedToScrapHeap(android.view.View view);
    }

    public interface SelectionBoundsAdjuster {
        void adjustListItemSelectionBounds(android.graphics.Rect rect);
    }

    abstract void fillGap(boolean z);

    abstract int findMotionRow(int i);

    abstract void setSelectionInt(int i);

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.AbsListView> {
        private int mCacheColorHintId;
        private int mChoiceModeId;
        private int mDrawSelectorOnTopId;
        private int mFastScrollEnabledId;
        private int mListSelectorId;
        private boolean mPropertiesMapped = false;
        private int mScrollingCacheId;
        private int mSmoothScrollbarId;
        private int mStackFromBottomId;
        private int mTextFilterEnabledId;
        private int mTranscriptModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mCacheColorHintId = propertyMapper.mapColor("cacheColorHint", 16843009);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "singleChoice");
            sparseArray.put(2, "multipleChoice");
            sparseArray.put(3, "multipleChoiceModal");
            java.util.Objects.requireNonNull(sparseArray);
            this.mChoiceModeId = propertyMapper.mapIntEnum("choiceMode", 16843051, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mDrawSelectorOnTopId = propertyMapper.mapBoolean("drawSelectorOnTop", 16843004);
            this.mFastScrollEnabledId = propertyMapper.mapBoolean("fastScrollEnabled", 16843302);
            this.mListSelectorId = propertyMapper.mapObject("listSelector", 16843003);
            this.mScrollingCacheId = propertyMapper.mapBoolean("scrollingCache", 16843006);
            this.mSmoothScrollbarId = propertyMapper.mapBoolean("smoothScrollbar", 16843313);
            this.mStackFromBottomId = propertyMapper.mapBoolean("stackFromBottom", 16843005);
            this.mTextFilterEnabledId = propertyMapper.mapBoolean("textFilterEnabled", 16843007);
            android.util.SparseArray sparseArray2 = new android.util.SparseArray();
            sparseArray2.put(0, "disabled");
            sparseArray2.put(1, android.graphics.FontListParser.STYLE_NORMAL);
            sparseArray2.put(2, "alwaysScroll");
            java.util.Objects.requireNonNull(sparseArray2);
            this.mTranscriptModeId = propertyMapper.mapIntEnum("transcriptMode", 16843008, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.AbsListView absListView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readColor(this.mCacheColorHintId, absListView.getCacheColorHint());
            propertyReader.readIntEnum(this.mChoiceModeId, absListView.getChoiceMode());
            propertyReader.readBoolean(this.mDrawSelectorOnTopId, absListView.isDrawSelectorOnTop());
            propertyReader.readBoolean(this.mFastScrollEnabledId, absListView.isFastScrollEnabled());
            propertyReader.readObject(this.mListSelectorId, absListView.getSelector());
            propertyReader.readBoolean(this.mScrollingCacheId, absListView.isScrollingCacheEnabled());
            propertyReader.readBoolean(this.mSmoothScrollbarId, absListView.isSmoothScrollbarEnabled());
            propertyReader.readBoolean(this.mStackFromBottomId, absListView.isStackFromBottom());
            propertyReader.readBoolean(this.mTextFilterEnabledId, absListView.isTextFilterEnabled());
            propertyReader.readIntEnum(this.mTranscriptModeId, absListView.getTranscriptMode());
        }
    }

    private static class DeviceConfigChangeListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private DeviceConfigChangeListener() {
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            if (!android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(properties.getNamespace())) {
                return;
            }
            for (java.lang.String str : properties.getKeyset()) {
                if (android.view.contentcapture.ContentCaptureManager.DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN.equals(str)) {
                    android.widget.AbsListView.sContentCaptureReportingEnabledByDeviceConfig = properties.getBoolean(str, false);
                }
            }
        }
    }

    private static void setupDeviceConfigProperties() {
        if (sDeviceConfigChangeListener == null) {
            sContentCaptureReportingEnabledByDeviceConfig = android.provider.DeviceConfig.getBoolean(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE, android.view.contentcapture.ContentCaptureManager.DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN, false);
            sDeviceConfigChangeListener = new android.widget.AbsListView.DeviceConfigChangeListener();
            android.provider.DeviceConfig.addOnPropertiesChangedListener(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE, android.app.ActivityThread.currentApplication().getMainExecutor(), sDeviceConfigChangeListener);
        }
    }

    public AbsListView(android.content.Context context) {
        super(context);
        this.mChoiceMode = 0;
        this.mLayoutMode = 0;
        this.mDeferNotifyDataSetChanged = false;
        this.mDrawSelectorOnTop = false;
        this.mSelectorPosition = -1;
        this.mSelectorRect = new android.graphics.Rect();
        this.mRecycler = new android.widget.AbsListView.RecycleBin();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mListPadding = new android.graphics.Rect();
        this.mWidthMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedTop = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mScrollProfilingStarted = false;
        this.mFlingProfilingStarted = false;
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mLastScrollState = 0;
        this.mReportChildrenToContentCaptureOnNextUpdate = true;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mTmpPoint = new float[2];
        this.mNestedYOffset = 0;
        this.mActivePointerId = -1;
        this.mDirection = 0;
        setupDeviceConfigProperties();
        this.mEdgeGlowBottom = new android.widget.EdgeEffect(context);
        this.mEdgeGlowTop = new android.widget.EdgeEffect(context);
        initAbsListView();
        this.mOwnerThread = java.lang.Thread.currentThread();
        setVerticalScrollBarEnabled(true);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(com.android.internal.R.styleable.View);
        initializeScrollbarsInternal(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    public AbsListView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842858);
    }

    public AbsListView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsListView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChoiceMode = 0;
        this.mLayoutMode = 0;
        this.mDeferNotifyDataSetChanged = false;
        this.mDrawSelectorOnTop = false;
        this.mSelectorPosition = -1;
        this.mSelectorRect = new android.graphics.Rect();
        this.mRecycler = new android.widget.AbsListView.RecycleBin();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mListPadding = new android.graphics.Rect();
        this.mWidthMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedTop = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mScrollProfilingStarted = false;
        this.mFlingProfilingStarted = false;
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mLastScrollState = 0;
        this.mReportChildrenToContentCaptureOnNextUpdate = true;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mTmpPoint = new float[2];
        this.mNestedYOffset = 0;
        this.mActivePointerId = -1;
        this.mDirection = 0;
        setupDeviceConfigProperties();
        this.mEdgeGlowBottom = new android.widget.EdgeEffect(context, attributeSet);
        this.mEdgeGlowTop = new android.widget.EdgeEffect(context, attributeSet);
        initAbsListView();
        this.mOwnerThread = java.lang.Thread.currentThread();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AbsListView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.AbsListView, attributeSet, obtainStyledAttributes, i, i2);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(0);
        if (drawable != null) {
            setSelector(drawable);
        }
        this.mDrawSelectorOnTop = obtainStyledAttributes.getBoolean(1, false);
        setStackFromBottom(obtainStyledAttributes.getBoolean(2, false));
        setScrollingCacheEnabled(obtainStyledAttributes.getBoolean(3, true));
        setTextFilterEnabled(obtainStyledAttributes.getBoolean(4, false));
        setTranscriptMode(obtainStyledAttributes.getInt(5, 0));
        setCacheColorHint(obtainStyledAttributes.getColor(6, 0));
        setSmoothScrollbarEnabled(obtainStyledAttributes.getBoolean(9, true));
        setChoiceMode(obtainStyledAttributes.getInt(7, 0));
        setFastScrollEnabled(obtainStyledAttributes.getBoolean(8, false));
        setFastScrollStyle(obtainStyledAttributes.getResourceId(11, 0));
        setFastScrollAlwaysVisible(obtainStyledAttributes.getBoolean(10, false));
        obtainStyledAttributes.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            setRevealOnFocusHint(false);
        }
    }

    private void initAbsListView() {
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setScrollingCacheEnabled(true);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mDensityScale = getContext().getResources().getDisplayMetrics().density;
    }

    @Override // android.widget.AdapterView
    public void setAdapter(android.widget.ListAdapter listAdapter) {
        if (listAdapter != null) {
            this.mAdapterHasStableIds = this.mAdapter.hasStableIds();
            if (this.mChoiceMode != 0 && this.mAdapterHasStableIds && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new android.util.LongSparseArray<>();
            }
        }
        clearChoices();
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public boolean isItemChecked(int i) {
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            return this.mCheckStates.get(i);
        }
        return false;
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == 1 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public android.util.SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == 0 || this.mCheckedIdStates == null || this.mAdapter == null) {
            return new long[0];
        }
        android.util.LongSparseArray<java.lang.Integer> longSparseArray = this.mCheckedIdStates;
        int size = longSparseArray.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = longSparseArray.keyAt(i);
        }
        return jArr;
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    public void setItemChecked(int i, boolean z) {
        boolean z2;
        if (this.mChoiceMode == 0) {
            return;
        }
        if (z && this.mChoiceMode == 3 && this.mChoiceActionMode == null) {
            if (this.mMultiChoiceModeCallback == null || !this.mMultiChoiceModeCallback.hasWrappedCallback()) {
                throw new java.lang.IllegalStateException("AbsListView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
            }
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
        if (this.mChoiceMode == 2 || this.mChoiceMode == 3) {
            boolean z3 = this.mCheckStates.get(i);
            this.mCheckStates.put(i, z);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (z) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(i), java.lang.Integer.valueOf(i));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                }
            }
            boolean z4 = z3 != z;
            if (z4) {
                if (z) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
            }
            if (this.mChoiceActionMode != null) {
                this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, i, this.mAdapter.getItemId(i), z);
            }
            z2 = z4;
        } else {
            boolean z5 = this.mCheckedIdStates != null && this.mAdapter.hasStableIds();
            z2 = isItemChecked(i) != z;
            if (z || isItemChecked(i)) {
                this.mCheckStates.clear();
                if (z5) {
                    this.mCheckedIdStates.clear();
                }
            }
            if (z) {
                this.mCheckStates.put(i, true);
                if (z5) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(i), java.lang.Integer.valueOf(i));
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
        }
        if (!this.mInLayout && !this.mBlockLayoutRequests && z2) {
            this.mDataChanged = true;
            rememberSyncState();
            requestLayout();
        }
    }

    @Override // android.widget.AdapterView
    public boolean performItemClick(android.view.View view, int i, long j) {
        boolean z;
        boolean z2 = false;
        boolean z3 = true;
        if (this.mChoiceMode != 0) {
            if (this.mChoiceMode == 2 || (this.mChoiceMode == 3 && this.mChoiceActionMode != null)) {
                boolean z4 = !this.mCheckStates.get(i, false);
                this.mCheckStates.put(i, z4);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (z4) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), java.lang.Integer.valueOf(i));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(i));
                    }
                }
                if (z4) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
                if (this.mChoiceActionMode == null) {
                    z2 = true;
                } else {
                    this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, i, j, z4);
                }
                z = z2;
                z2 = true;
            } else if (this.mChoiceMode != 1) {
                z = true;
            } else {
                if (!this.mCheckStates.get(i, false)) {
                    this.mCheckStates.clear();
                    this.mCheckStates.put(i, true);
                    if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                        this.mCheckedIdStates.clear();
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(i), java.lang.Integer.valueOf(i));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                    this.mCheckedItemCount = 0;
                }
                z = true;
                z2 = true;
            }
            if (z2) {
                updateOnScreenCheckedViews();
            }
            z2 = true;
            z3 = z;
        }
        if (z3) {
            return z2 | super.performItemClick(view, i, j);
        }
        return z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateOnScreenCheckedViews() {
        int i = this.mFirstPosition;
        int childCount = getChildCount();
        boolean z = getContext().getApplicationInfo().targetSdkVersion >= 11;
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            int i3 = i + i2;
            if (childAt instanceof android.widget.Checkable) {
                ((android.widget.Checkable) childAt).setChecked(this.mCheckStates.get(i3));
            } else if (z) {
                childAt.setActivated(this.mCheckStates.get(i3));
            }
        }
    }

    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    public void setChoiceMode(int i) {
        this.mChoiceMode = i;
        if (this.mChoiceActionMode != null) {
            this.mChoiceActionMode.finish();
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new android.util.SparseBooleanArray(0);
            }
            if (this.mCheckedIdStates == null && this.mAdapter != null && this.mAdapter.hasStableIds()) {
                this.mCheckedIdStates = new android.util.LongSparseArray<>(0);
            }
            if (this.mChoiceMode == 3) {
                clearChoices();
                setLongClickable(true);
            }
        }
    }

    public void setMultiChoiceModeListener(android.widget.AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
        if (this.mMultiChoiceModeCallback == null) {
            this.mMultiChoiceModeCallback = new android.widget.AbsListView.MultiChoiceModeWrapper();
        }
        this.mMultiChoiceModeCallback.setWrapped(multiChoiceModeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean contentFits() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        if (childCount != this.mItemCount) {
            return false;
        }
        return getChildAt(0).getTop() >= this.mListPadding.top && getChildAt(childCount - 1).getBottom() <= getHeight() - this.mListPadding.bottom;
    }

    public void setFastScrollEnabled(final boolean z) {
        if (this.mFastScrollEnabled != z) {
            this.mFastScrollEnabled = z;
            if (isOwnerThread()) {
                setFastScrollerEnabledUiThread(z);
            } else {
                post(new java.lang.Runnable() { // from class: android.widget.AbsListView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.AbsListView.this.setFastScrollerEnabledUiThread(z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerEnabledUiThread(boolean z) {
        if (this.mFastScroll != null) {
            this.mFastScroll.setEnabled(z);
        } else if (z) {
            this.mFastScroll = new android.widget.FastScroller(this, this.mFastScrollStyle);
            this.mFastScroll.setEnabled(true);
        }
        resolvePadding();
        if (this.mFastScroll != null) {
            this.mFastScroll.updateLayout();
        }
    }

    public void setFastScrollStyle(int i) {
        if (this.mFastScroll == null) {
            this.mFastScrollStyle = i;
        } else {
            this.mFastScroll.setStyle(i);
        }
    }

    public void setFastScrollAlwaysVisible(final boolean z) {
        if (this.mFastScrollAlwaysVisible != z) {
            if (z && !this.mFastScrollEnabled) {
                setFastScrollEnabled(true);
            }
            this.mFastScrollAlwaysVisible = z;
            if (isOwnerThread()) {
                setFastScrollerAlwaysVisibleUiThread(z);
            } else {
                post(new java.lang.Runnable() { // from class: android.widget.AbsListView.2
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.AbsListView.this.setFastScrollerAlwaysVisibleUiThread(z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFastScrollerAlwaysVisibleUiThread(boolean z) {
        if (this.mFastScroll != null) {
            this.mFastScroll.setAlwaysShow(z);
        }
    }

    private boolean isOwnerThread() {
        return this.mOwnerThread == java.lang.Thread.currentThread();
    }

    public boolean isFastScrollAlwaysVisible() {
        return this.mFastScroll == null ? this.mFastScrollEnabled && this.mFastScrollAlwaysVisible : this.mFastScroll.isEnabled() && this.mFastScroll.isAlwaysShowEnabled();
    }

    @Override // android.view.View
    public int getVerticalScrollbarWidth() {
        if (this.mFastScroll != null && this.mFastScroll.isEnabled()) {
            return java.lang.Math.max(super.getVerticalScrollbarWidth(), this.mFastScroll.getWidth());
        }
        return super.getVerticalScrollbarWidth();
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isFastScrollEnabled() {
        if (this.mFastScroll == null) {
            return this.mFastScrollEnabled;
        }
        return this.mFastScroll.isEnabled();
    }

    @Override // android.view.View
    public void setVerticalScrollbarPosition(int i) {
        super.setVerticalScrollbarPosition(i);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollbarPosition(i);
        }
    }

    @Override // android.view.View
    public void setScrollBarStyle(int i) {
        super.setScrollBarStyle(i);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollBarStyle(i);
        }
    }

    @Override // android.view.View
    protected boolean isVerticalScrollBarHidden() {
        return isFastScrollEnabled();
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    public void setOnScrollListener(android.widget.AbsListView.OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
        invokeOnItemScrollListener();
    }

    void invokeOnItemScrollListener() {
        if (this.mFastScroll != null) {
            this.mFastScroll.onScroll(this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.AbsListView.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled()) {
            if (canScrollUp()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (canScrollDown()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                accessibilityNodeInfo.setScrollable(true);
            }
        }
        accessibilityNodeInfo.removeAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        accessibilityNodeInfo.setClickable(false);
    }

    int getSelectionModeForAccessibility() {
        switch (getChoiceMode()) {
        }
        return 0;
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        switch (i) {
            case 4096:
            case 16908346:
                if (!isEnabled() || !canScrollDown()) {
                    return false;
                }
                smoothScrollBy((getHeight() - this.mListPadding.top) - this.mListPadding.bottom, 200);
                return true;
            case 8192:
            case 16908344:
                if (!isEnabled() || !canScrollUp()) {
                    return false;
                }
                smoothScrollBy(-((getHeight() - this.mListPadding.top) - this.mListPadding.bottom), 200);
                return true;
            default:
                return false;
        }
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    public void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled && !z) {
            clearScrollingCache();
        }
        this.mScrollingCacheEnabled = z;
    }

    public void setTextFilterEnabled(boolean z) {
        this.mTextFilterEnabled = z;
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isTextFilterEnabled() {
        return this.mTextFilterEnabled;
    }

    @Override // android.view.View
    public void getFocusedRect(android.graphics.Rect rect) {
        android.view.View selectedView = getSelectedView();
        if (selectedView != null && selectedView.getParent() == this) {
            selectedView.getFocusedRect(rect);
            offsetDescendantRectToMyCoords(selectedView, rect);
        } else {
            super.getFocusedRect(rect);
        }
    }

    private void useDefaultSelector() {
        setSelector(getContext().getDrawable(17301602));
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isStackFromBottom() {
        return this.mStackFromBottom;
    }

    public void setStackFromBottom(boolean z) {
        if (this.mStackFromBottom != z) {
            this.mStackFromBottom = z;
            requestLayoutIfNecessary();
        }
    }

    void requestLayoutIfNecessary() {
        if (getChildCount() > 0) {
            resetList();
            requestLayout();
            invalidate();
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.AbsListView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.AbsListView.SavedState>() { // from class: android.widget.AbsListView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.AbsListView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.AbsListView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.AbsListView.SavedState[] newArray(int i) {
                return new android.widget.AbsListView.SavedState[i];
            }
        };
        android.util.LongSparseArray<java.lang.Integer> checkIdState;
        android.util.SparseBooleanArray checkState;
        int checkedItemCount;
        java.lang.String filter;
        long firstId;
        int height;
        boolean inActionMode;
        int position;
        long selectedId;
        int viewTop;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.firstId = parcel.readLong();
            this.viewTop = parcel.readInt();
            this.position = parcel.readInt();
            this.height = parcel.readInt();
            this.filter = parcel.readString();
            this.inActionMode = parcel.readByte() != 0;
            this.checkedItemCount = parcel.readInt();
            this.checkState = parcel.readSparseBooleanArray();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.checkIdState = new android.util.LongSparseArray<>();
                for (int i = 0; i < readInt; i++) {
                    this.checkIdState.put(parcel.readLong(), java.lang.Integer.valueOf(parcel.readInt()));
                }
            }
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.selectedId);
            parcel.writeLong(this.firstId);
            parcel.writeInt(this.viewTop);
            parcel.writeInt(this.position);
            parcel.writeInt(this.height);
            parcel.writeString(this.filter);
            parcel.writeByte(this.inActionMode ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.checkedItemCount);
            parcel.writeSparseBooleanArray(this.checkState);
            int size = this.checkIdState != null ? this.checkIdState.size() : 0;
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeLong(this.checkIdState.keyAt(i2));
                parcel.writeInt(this.checkIdState.valueAt(i2).intValue());
            }
        }

        public java.lang.String toString() {
            return "AbsListView.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewTop=" + this.viewTop + " position=" + this.position + " height=" + this.height + " filter=" + this.filter + " checkState=" + this.checkState + "}";
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.EditText editText;
        android.text.Editable text;
        dismissPopup();
        android.widget.AbsListView.SavedState savedState = new android.widget.AbsListView.SavedState(super.onSaveInstanceState());
        if (this.mPendingSync != null) {
            savedState.selectedId = this.mPendingSync.selectedId;
            savedState.firstId = this.mPendingSync.firstId;
            savedState.viewTop = this.mPendingSync.viewTop;
            savedState.position = this.mPendingSync.position;
            savedState.height = this.mPendingSync.height;
            savedState.filter = this.mPendingSync.filter;
            savedState.inActionMode = this.mPendingSync.inActionMode;
            savedState.checkedItemCount = this.mPendingSync.checkedItemCount;
            savedState.checkState = this.mPendingSync.checkState;
            savedState.checkIdState = this.mPendingSync.checkIdState;
            return savedState;
        }
        boolean z = getChildCount() > 0 && this.mItemCount > 0;
        long selectedItemId = getSelectedItemId();
        savedState.selectedId = selectedItemId;
        savedState.height = getHeight();
        if (selectedItemId >= 0) {
            savedState.viewTop = this.mSelectedTop;
            savedState.position = getSelectedItemPosition();
            savedState.firstId = -1L;
        } else if (z && this.mFirstPosition > 0) {
            savedState.viewTop = getChildAt(0).getTop();
            int i = this.mFirstPosition;
            if (i >= this.mItemCount) {
                i = this.mItemCount - 1;
            }
            savedState.position = i;
            savedState.firstId = this.mAdapter.getItemId(i);
        } else {
            savedState.viewTop = 0;
            savedState.firstId = -1L;
            savedState.position = 0;
        }
        savedState.filter = null;
        if (this.mFiltered && (editText = this.mTextFilter) != null && (text = editText.getText()) != null) {
            savedState.filter = text.toString();
        }
        savedState.inActionMode = this.mChoiceMode == 3 && this.mChoiceActionMode != null;
        if (this.mCheckStates != null) {
            savedState.checkState = this.mCheckStates.m4836clone();
        }
        if (this.mCheckedIdStates != null) {
            android.util.LongSparseArray<java.lang.Integer> longSparseArray = new android.util.LongSparseArray<>();
            int size = this.mCheckedIdStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                longSparseArray.put(this.mCheckedIdStates.keyAt(i2), this.mCheckedIdStates.valueAt(i2));
            }
            savedState.checkIdState = longSparseArray;
        }
        savedState.checkedItemCount = this.mCheckedItemCount;
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.saveRemoteViewsCache();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.AbsListView.SavedState savedState = (android.widget.AbsListView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = savedState.height;
        if (savedState.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = savedState;
            this.mSyncRowId = savedState.selectedId;
            this.mSyncPosition = savedState.position;
            this.mSpecificTop = savedState.viewTop;
            this.mSyncMode = 0;
        } else if (savedState.firstId >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = savedState;
            this.mSyncRowId = savedState.firstId;
            this.mSyncPosition = savedState.position;
            this.mSpecificTop = savedState.viewTop;
            this.mSyncMode = 1;
        }
        setFilterText(savedState.filter);
        if (savedState.checkState != null) {
            this.mCheckStates = savedState.checkState;
        }
        if (savedState.checkIdState != null) {
            this.mCheckedIdStates = savedState.checkIdState;
        }
        this.mCheckedItemCount = savedState.checkedItemCount;
        if (savedState.inActionMode && this.mChoiceMode == 3 && this.mMultiChoiceModeCallback != null) {
            this.mChoiceActionMode = startActionMode(this.mMultiChoiceModeCallback);
        }
        requestLayout();
    }

    private boolean acceptFilter() {
        return this.mTextFilterEnabled && (getAdapter() instanceof android.widget.Filterable) && ((android.widget.Filterable) getAdapter()).getFilter() != null;
    }

    public void setFilterText(java.lang.String str) {
        if (this.mTextFilterEnabled && !android.text.TextUtils.isEmpty(str)) {
            createTextFilter(false);
            this.mTextFilter.lambda$setTextAsync$0(str);
            this.mTextFilter.setSelection(str.length());
            if (this.mAdapter instanceof android.widget.Filterable) {
                if (this.mPopup == null) {
                    ((android.widget.Filterable) this.mAdapter).getFilter().filter(str);
                }
                this.mFiltered = true;
                this.mDataSetObserver.clearSavedState();
            }
        }
    }

    public java.lang.CharSequence getTextFilter() {
        if (this.mTextFilterEnabled && this.mTextFilter != null) {
            return this.mTextFilter.getText();
        }
        return null;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z && this.mSelectedPosition < 0 && !isInTouchMode()) {
            if (!isAttachedToWindow() && this.mAdapter != null) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            resurrectSelection();
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    void resetList() {
        removeAllViewsInLayout();
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mPositionScrollAfterLayout = null;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        this.mSelectedTop = 0;
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        invalidate();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return 0;
        }
        if (!this.mSmoothScrollbarEnabled) {
            return 1;
        }
        int i = childCount * 100;
        android.view.View childAt = getChildAt(0);
        int top = childAt.getTop();
        int height = childAt.getHeight();
        if (height > 0) {
            i += (top * 100) / height;
        }
        android.view.View childAt2 = getChildAt(childCount - 1);
        int bottom = childAt2.getBottom();
        int height2 = childAt2.getHeight();
        if (height2 > 0) {
            return i - (((bottom - getHeight()) * 100) / height2);
        }
        return i;
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        int i = this.mFirstPosition;
        int childCount = getChildCount();
        int i2 = 0;
        if (i >= 0 && childCount > 0) {
            if (this.mSmoothScrollbarEnabled) {
                android.view.View childAt = getChildAt(0);
                int top = childAt.getTop();
                int height = childAt.getHeight();
                if (height > 0) {
                    return java.lang.Math.max(((i * 100) - ((top * 100) / height)) + ((int) ((this.mScrollY / getHeight()) * this.mItemCount * 100.0f)), 0);
                }
            } else {
                int i3 = this.mItemCount;
                if (i != 0) {
                    if (i + childCount == i3) {
                        i2 = i3;
                    } else {
                        i2 = (childCount / 2) + i;
                    }
                }
                return (int) (i + (childCount * (i2 / i3)));
            }
        }
        return 0;
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        if (this.mSmoothScrollbarEnabled) {
            int max = java.lang.Math.max(this.mItemCount * 100, 0);
            if (this.mScrollY != 0) {
                return max + java.lang.Math.abs((int) ((this.mScrollY / getHeight()) * this.mItemCount * 100.0f));
            }
            return max;
        }
        return this.mItemCount;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        int childCount = getChildCount();
        float topFadingEdgeStrength = super.getTopFadingEdgeStrength();
        if (childCount == 0) {
            return topFadingEdgeStrength;
        }
        if (this.mFirstPosition > 0) {
            return 1.0f;
        }
        return getChildAt(0).getTop() < this.mPaddingTop ? (-(r0 - this.mPaddingTop)) / getVerticalFadingEdgeLength() : topFadingEdgeStrength;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        int childCount = getChildCount();
        float bottomFadingEdgeStrength = super.getBottomFadingEdgeStrength();
        if (childCount == 0) {
            return bottomFadingEdgeStrength;
        }
        if ((this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
            return 1.0f;
        }
        int bottom = getChildAt(childCount - 1).getBottom();
        int height = getHeight();
        float verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (bottom <= height - this.mPaddingBottom) {
            return bottomFadingEdgeStrength;
        }
        return ((bottom - height) + this.mPaddingBottom) / verticalFadingEdgeLength;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mSelector == null) {
            useDefaultSelector();
        }
        android.graphics.Rect rect = this.mListPadding;
        rect.left = this.mSelectionLeftPadding + this.mPaddingLeft;
        rect.top = this.mSelectionTopPadding + this.mPaddingTop;
        rect.right = this.mSelectionRightPadding + this.mPaddingRight;
        rect.bottom = this.mSelectionBottomPadding + this.mPaddingBottom;
        if (this.mTranscriptMode == 1) {
            int childCount = getChildCount();
            int height = getHeight() - getPaddingBottom();
            android.view.View childAt = getChildAt(childCount - 1);
            this.mForceTranscriptScroll = this.mFirstPosition + childCount >= this.mLastHandledItemCount && (childAt != null ? childAt.getBottom() : height) <= height;
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInLayout = true;
        int childCount = getChildCount();
        if (z) {
            for (int i5 = 0; i5 < childCount; i5++) {
                getChildAt(i5).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        layoutChildren();
        this.mOverscrollMax = (i4 - i2) / 3;
        if (this.mFastScroll != null) {
            this.mFastScroll.onItemCountChanged(getChildCount(), this.mItemCount);
        }
        this.mInLayout = false;
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (frame) {
            boolean z = getWindowVisibility() == 0;
            if (this.mFiltered && z && this.mPopup != null && this.mPopup.isShowing()) {
                positionPopup();
            }
        }
        return frame;
    }

    protected void layoutChildren() {
    }

    android.view.View getAccessibilityFocusedChild(android.view.View view) {
        boolean z;
        android.view.ViewParent parent = view.getParent();
        while (true) {
            z = parent instanceof android.view.View;
            if (!z || parent == this) {
                break;
            }
            view = parent;
            parent = parent.getParent();
        }
        if (!z) {
            return null;
        }
        return view;
    }

    void updateScrollIndicators() {
        if (this.mScrollUp != null) {
            this.mScrollUp.setVisibility(canScrollUp() ? 0 : 4);
        }
        if (this.mScrollDown != null) {
            this.mScrollDown.setVisibility(canScrollDown() ? 0 : 4);
        }
    }

    private boolean canScrollUp() {
        boolean z = this.mFirstPosition > 0;
        if (!z && getChildCount() > 0) {
            return getChildAt(0).getTop() < this.mListPadding.top;
        }
        return z;
    }

    private boolean canScrollDown() {
        int childCount = getChildCount();
        boolean z = this.mFirstPosition + childCount < this.mItemCount;
        if (!z && childCount > 0) {
            return getChildAt(childCount - 1).getBottom() > this.mBottom - this.mListPadding.bottom;
        }
        return z;
    }

    @Override // android.widget.AdapterView
    @android.view.ViewDebug.ExportedProperty
    public android.view.View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    public int getListPaddingTop() {
        return this.mListPadding.top;
    }

    public int getListPaddingBottom() {
        return this.mListPadding.bottom;
    }

    public int getListPaddingLeft() {
        return this.mListPadding.left;
    }

    public int getListPaddingRight() {
        return this.mListPadding.right;
    }

    android.view.View obtainView(int i, boolean[] zArr) {
        android.view.View view;
        android.os.Trace.traceBegin(8L, "obtainView");
        zArr[0] = false;
        android.view.View transientStateView = this.mRecycler.getTransientStateView(i);
        if (transientStateView != null) {
            if (((android.widget.AbsListView.LayoutParams) transientStateView.getLayoutParams()).viewType == this.mAdapter.getItemViewType(i) && (view = this.mAdapter.getView(i, transientStateView, this)) != transientStateView) {
                setItemViewLayoutParams(view, i);
                this.mRecycler.addScrapView(view, i);
            }
            zArr[0] = true;
            transientStateView.dispatchFinishTemporaryDetach();
            return transientStateView;
        }
        android.view.View scrapView = this.mRecycler.getScrapView(i);
        android.view.View view2 = this.mAdapter.getView(i, scrapView, this);
        if (scrapView != null) {
            if (view2 != scrapView) {
                this.mRecycler.addScrapView(scrapView, i);
            } else if (view2.isTemporarilyDetached()) {
                zArr[0] = true;
                view2.dispatchFinishTemporaryDetach();
            }
        }
        if (this.mCacheColorHint != 0) {
            view2.setDrawingCacheBackgroundColor(this.mCacheColorHint);
        }
        if (view2.getImportantForAccessibility() == 0) {
            view2.setImportantForAccessibility(1);
        }
        setItemViewLayoutParams(view2, i);
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            if (this.mAccessibilityDelegate == null) {
                this.mAccessibilityDelegate = new android.widget.AbsListView.ListItemAccessibilityDelegate();
            }
            if (view2.getAccessibilityDelegate() == null) {
                view2.setAccessibilityDelegate(this.mAccessibilityDelegate);
            }
        }
        android.os.Trace.traceEnd(8L);
        return view2;
    }

    private void setItemViewLayoutParams(android.view.View view, int i) {
        android.widget.AbsListView.LayoutParams layoutParams;
        android.view.ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 == null) {
            layoutParams = (android.widget.AbsListView.LayoutParams) generateDefaultLayoutParams();
        } else if (!checkLayoutParams(layoutParams2)) {
            layoutParams = (android.widget.AbsListView.LayoutParams) generateLayoutParams(layoutParams2);
        } else {
            layoutParams = (android.widget.AbsListView.LayoutParams) layoutParams2;
        }
        if (this.mAdapterHasStableIds) {
            layoutParams.itemId = this.mAdapter.getItemId(i);
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        layoutParams.isEnabled = this.mAdapter.isEnabled(i);
        if (layoutParams != layoutParams2) {
            view.setLayoutParams(layoutParams);
        }
    }

    class ListItemAccessibilityDelegate extends android.view.View.AccessibilityDelegate {
        ListItemAccessibilityDelegate() {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            android.widget.AbsListView.this.onInitializeAccessibilityNodeInfoForItem(view, android.widget.AbsListView.this.getPositionForView(view), accessibilityNodeInfo);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
            boolean z;
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            int positionForView = android.widget.AbsListView.this.getPositionForView(view);
            if (positionForView == -1 || android.widget.AbsListView.this.mAdapter == null || positionForView >= android.widget.AbsListView.this.mAdapter.getCount()) {
                return false;
            }
            android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof android.widget.AbsListView.LayoutParams) {
                z = ((android.widget.AbsListView.LayoutParams) layoutParams).isEnabled;
            } else {
                z = false;
            }
            if (!android.widget.AbsListView.this.isEnabled() || !z) {
                return false;
            }
            switch (i) {
                case 4:
                    if (android.widget.AbsListView.this.getSelectedItemPosition() == positionForView) {
                        return false;
                    }
                    android.widget.AbsListView.this.setSelection(positionForView);
                    return true;
                case 8:
                    if (android.widget.AbsListView.this.getSelectedItemPosition() != positionForView) {
                        return false;
                    }
                    android.widget.AbsListView.this.setSelection(-1);
                    return true;
                case 16:
                    if (!android.widget.AbsListView.this.isItemClickable(view)) {
                        return false;
                    }
                    return android.widget.AbsListView.this.performItemClick(view, positionForView, android.widget.AbsListView.this.getItemIdAtPosition(positionForView));
                case 32:
                    if (!android.widget.AbsListView.this.isLongClickable()) {
                        return false;
                    }
                    return android.widget.AbsListView.this.performLongPress(view, positionForView, android.widget.AbsListView.this.getItemIdAtPosition(positionForView));
                default:
                    return false;
            }
        }
    }

    public void onInitializeAccessibilityNodeInfoForItem(android.view.View view, int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (i == -1) {
            return;
        }
        boolean isEnabled = isEnabled();
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof android.widget.AbsListView.LayoutParams) {
            isEnabled &= ((android.widget.AbsListView.LayoutParams) layoutParams).isEnabled;
        }
        if (i == getSelectedItemPosition()) {
            accessibilityNodeInfo.setSelected(true);
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, isEnabled, android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_SELECTION);
        } else {
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, isEnabled, android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SELECT);
        }
        if (isItemClickable(view)) {
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, isEnabled, android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            accessibilityNodeInfo.setClickable(isEnabled);
        }
        if (isLongClickable()) {
            addAccessibilityActionIfEnabled(accessibilityNodeInfo, isEnabled, android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
            accessibilityNodeInfo.setLongClickable(isEnabled);
        }
    }

    private void addAccessibilityActionIfEnabled(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, boolean z, android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        if (z) {
            accessibilityNodeInfo.addAction(accessibilityAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isItemClickable(android.view.View view) {
        return !view.hasExplicitFocusable();
    }

    void positionSelectorLikeTouch(int i, android.view.View view, float f, float f2) {
        positionSelector(i, view, true, f, f2);
    }

    void positionSelectorLikeFocus(int i, android.view.View view) {
        if (this.mSelector != null && this.mSelectorPosition != i && i != -1) {
            android.graphics.Rect rect = this.mSelectorRect;
            positionSelector(i, view, true, rect.exactCenterX(), rect.exactCenterY());
        } else {
            positionSelector(i, view);
        }
    }

    void positionSelector(int i, android.view.View view) {
        positionSelector(i, view, false, -1.0f, -1.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void positionSelector(int i, android.view.View view, boolean z, float f, float f2) {
        boolean z2 = i != this.mSelectorPosition;
        if (i != -1) {
            this.mSelectorPosition = i;
        }
        android.graphics.Rect rect = this.mSelectorRect;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        if (view instanceof android.widget.AbsListView.SelectionBoundsAdjuster) {
            ((android.widget.AbsListView.SelectionBoundsAdjuster) view).adjustListItemSelectionBounds(rect);
        }
        rect.left -= this.mSelectionLeftPadding;
        rect.top -= this.mSelectionTopPadding;
        rect.right += this.mSelectionRightPadding;
        rect.bottom += this.mSelectionBottomPadding;
        boolean isEnabled = view.isEnabled();
        if (this.mIsChildViewEnabled != isEnabled) {
            this.mIsChildViewEnabled = isEnabled;
        }
        android.graphics.drawable.Drawable drawable = this.mSelector;
        if (drawable != null) {
            if (z2) {
                drawable.setVisible(false, false);
                drawable.setState(android.util.StateSet.NOTHING);
            }
            drawable.setBounds(rect);
            if (z2) {
                if (getVisibility() == 0) {
                    drawable.setVisible(true, false);
                }
                updateSelectorState();
            }
            if (z) {
                drawable.setHotspot(f, f2);
            }
        }
    }

    public boolean isSelectedChildViewEnabled() {
        return this.mIsChildViewEnabled;
    }

    public void setSelectedChildViewEnabled(boolean z) {
        this.mIsChildViewEnabled = z;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        int i = 0;
        boolean z = (this.mGroupFlags & 34) == 34;
        if (z) {
            i = canvas.save();
            int i2 = this.mScrollX;
            int i3 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i2, this.mPaddingTop + i3, ((i2 + this.mRight) - this.mLeft) - this.mPaddingRight, ((i3 + this.mBottom) - this.mTop) - this.mPaddingBottom);
            this.mGroupFlags &= -35;
        }
        boolean z2 = this.mDrawSelectorOnTop;
        if (!z2) {
            drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (z2) {
            drawSelector(canvas);
        }
        if (z) {
            canvas.restoreToCount(i);
            this.mGroupFlags |= 34;
        }
    }

    @Override // android.view.View
    protected boolean isPaddingOffsetRequired() {
        return (this.mGroupFlags & 34) != 34;
    }

    @Override // android.view.View
    protected int getLeftPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return -this.mPaddingLeft;
    }

    @Override // android.view.View
    protected int getTopPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return -this.mPaddingTop;
    }

    @Override // android.view.View
    protected int getRightPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return this.mPaddingRight;
    }

    @Override // android.view.View
    protected int getBottomPaddingOffset() {
        if ((this.mGroupFlags & 34) == 34) {
            return 0;
        }
        return this.mPaddingBottom;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        super.internalSetPadding(i, i2, i3, i4);
        if (isLayoutRequested()) {
            handleBoundsChange();
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        handleBoundsChange();
        if (this.mFastScroll != null) {
            this.mFastScroll.onSizeChanged(i, i2, i3, i4);
        }
    }

    void handleBoundsChange() {
        int childCount;
        if (!this.mInLayout && (childCount = getChildCount()) > 0) {
            this.mDataChanged = true;
            rememberSyncState();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                android.view.ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams == null || layoutParams.width < 1 || layoutParams.height < 1) {
                    childAt.forceLayout();
                }
            }
        }
    }

    boolean touchModeDrawsInPressedState() {
        switch (this.mTouchMode) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    boolean shouldShowSelector() {
        return (isFocused() && !isInTouchMode()) || (touchModeDrawsInPressedState() && isPressed());
    }

    private void drawSelector(android.graphics.Canvas canvas) {
        if (shouldDrawSelector()) {
            android.graphics.drawable.Drawable drawable = this.mSelector;
            drawable.setBounds(this.mSelectorRect);
            drawable.draw(canvas);
        }
    }

    public final boolean shouldDrawSelector() {
        return !this.mSelectorRect.isEmpty();
    }

    public void setDrawSelectorOnTop(boolean z) {
        this.mDrawSelectorOnTop = z;
    }

    public boolean isDrawSelectorOnTop() {
        return this.mDrawSelectorOnTop;
    }

    public void setSelector(int i) {
        setSelector(getContext().getDrawable(i));
    }

    public void setSelector(android.graphics.drawable.Drawable drawable) {
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = drawable;
        android.graphics.Rect rect = new android.graphics.Rect();
        drawable.getPadding(rect);
        this.mSelectionLeftPadding = rect.left;
        this.mSelectionTopPadding = rect.top;
        this.mSelectionRightPadding = rect.right;
        this.mSelectionBottomPadding = rect.bottom;
        drawable.setCallback(this);
        updateSelectorState();
    }

    public android.graphics.drawable.Drawable getSelector() {
        return this.mSelector;
    }

    void keyPressed() {
        if (!isEnabled() || !isClickable()) {
            return;
        }
        android.graphics.drawable.Drawable drawable = this.mSelector;
        android.graphics.Rect rect = this.mSelectorRect;
        if (drawable != null) {
            if ((isFocused() || touchModeDrawsInPressedState()) && !rect.isEmpty()) {
                android.view.View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (childAt != null) {
                    if (childAt.hasExplicitFocusable()) {
                        return;
                    } else {
                        childAt.setPressed(true);
                    }
                }
                setPressed(true);
                boolean isLongClickable = isLongClickable();
                android.graphics.drawable.Drawable current = drawable.getCurrent();
                if (current != null && (current instanceof android.graphics.drawable.TransitionDrawable)) {
                    if (isLongClickable) {
                        ((android.graphics.drawable.TransitionDrawable) current).startTransition(android.view.ViewConfiguration.getLongPressTimeout());
                    } else {
                        ((android.graphics.drawable.TransitionDrawable) current).resetTransition();
                    }
                }
                if (isLongClickable && !this.mDataChanged) {
                    if (this.mPendingCheckForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new android.widget.AbsListView.CheckForKeyLongPress();
                    }
                    this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
                    postDelayed(this.mPendingCheckForKeyLongPress, android.view.ViewConfiguration.getLongPressTimeout());
                }
            }
        }
    }

    public void setScrollIndicators(android.view.View view, android.view.View view2) {
        this.mScrollUp = view;
        this.mScrollDown = view2;
    }

    void updateSelectorState() {
        android.graphics.drawable.Drawable drawable = this.mSelector;
        if (drawable != null && drawable.isStateful()) {
            if (shouldShowSelector()) {
                if (drawable.setState(getDrawableStateForSelector())) {
                    invalidateDrawable(drawable);
                    return;
                }
                return;
            }
            drawable.setState(android.util.StateSet.NOTHING);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    private int[] getDrawableStateForSelector() {
        if (this.mIsChildViewEnabled) {
            return super.getDrawableState();
        }
        int i = ENABLED_STATE_SET[0];
        int[] onCreateDrawableState = onCreateDrawableState(1);
        int length = onCreateDrawableState.length - 1;
        while (true) {
            if (length < 0) {
                length = -1;
                break;
            }
            if (onCreateDrawableState[length] == i) {
                break;
            }
            length--;
        }
        if (length >= 0) {
            java.lang.System.arraycopy(onCreateDrawableState, length + 1, onCreateDrawableState, length, (onCreateDrawableState.length - length) - 1);
        }
        return onCreateDrawableState;
    }

    @Override // android.view.View
    public boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return this.mSelector == drawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mSelector != null) {
            this.mSelector.jumpToCurrentState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null && !this.mGlobalLayoutListenerAddedFilter) {
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new android.widget.AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsDetaching = true;
        dismissPopup();
        this.mRecycler.clear();
        android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.removeOnTouchModeChangeListener(this);
        if (this.mTextFilterEnabled && this.mPopup != null) {
            viewTreeObserver.removeOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = false;
        }
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
        if (this.mScrollStrictSpan != null) {
            this.mScrollStrictSpan.finish();
            this.mScrollStrictSpan = null;
        }
        if (this.mFlingStrictSpan != null) {
            this.mFlingStrictSpan.finish();
            this.mFlingStrictSpan = null;
        }
        if (this.mFlingRunnable != null) {
            removeCallbacks(this.mFlingRunnable);
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mClearScrollingCache != null) {
            removeCallbacks(this.mClearScrollingCache);
        }
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mTouchModeReset != null) {
            removeCallbacks(this.mTouchModeReset);
            this.mTouchModeReset.run();
        }
        this.mIsDetaching = false;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        int i = !isInTouchMode() ? 1 : 0;
        if (!z) {
            setChildrenDrawingCacheEnabled(false);
            if (this.mFlingRunnable != null) {
                removeCallbacks(this.mFlingRunnable);
                this.mFlingRunnable.mSuppressIdleStateChangeCall = false;
                this.mFlingRunnable.endFling();
                if (this.mPositionScroller != null) {
                    this.mPositionScroller.stop();
                }
                if (this.mScrollY != 0) {
                    this.mScrollY = 0;
                    invalidateParentCaches();
                    finishGlows();
                    invalidate();
                }
            }
            dismissPopup();
            if (i == 1) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
        } else {
            if (this.mFiltered && !this.mPopupHidden) {
                showPopup();
            }
            if (i != this.mLastTouchMode && this.mLastTouchMode != -1) {
                if (i == 1) {
                    resurrectSelection();
                } else {
                    hideSelector();
                    this.mLayoutMode = 0;
                    layoutChildren();
                }
            }
        }
        this.mLastTouchMode = i;
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.mFastScroll != null) {
            this.mFastScroll.setScrollbarPosition(getVerticalScrollbarPosition());
        }
    }

    android.view.ContextMenu.ContextMenuInfo createContextMenuInfo(android.view.View view, int i, long j) {
        return new android.widget.AdapterView.AdapterContextMenuInfo(view, i, j);
    }

    @Override // android.view.View
    public void onCancelPendingInputEvents() {
        super.onCancelPendingInputEvents();
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mPendingCheckForTap != null) {
            removeCallbacks(this.mPendingCheckForTap);
        }
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
        if (this.mPendingCheckForKeyLongPress != null) {
            removeCallbacks(this.mPendingCheckForKeyLongPress);
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = android.widget.AbsListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            return android.widget.AbsListView.this.getWindowAttachCount() == this.mOriginalAttachCount;
        }
    }

    private class PerformClick extends android.widget.AbsListView.WindowRunnnable implements java.lang.Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View childAt;
            if (android.widget.AbsListView.this.mDataChanged) {
                return;
            }
            android.widget.ListAdapter listAdapter = android.widget.AbsListView.this.mAdapter;
            int i = this.mClickMotionPosition;
            if (listAdapter != null && android.widget.AbsListView.this.mItemCount > 0 && i != -1 && i < listAdapter.getCount() && sameWindow() && listAdapter.isEnabled(i) && (childAt = android.widget.AbsListView.this.getChildAt(i - android.widget.AbsListView.this.mFirstPosition)) != null) {
                android.widget.AbsListView.this.performItemClick(childAt, i, listAdapter.getItemId(i));
            }
        }
    }

    private class CheckForLongPress extends android.widget.AbsListView.WindowRunnnable implements java.lang.Runnable {
        private static final int INVALID_COORD = -1;
        private float mX;
        private float mY;

        private CheckForLongPress() {
            super();
            this.mX = -1.0f;
            this.mY = -1.0f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCoords(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            android.view.View childAt = android.widget.AbsListView.this.getChildAt(android.widget.AbsListView.this.mMotionPosition - android.widget.AbsListView.this.mFirstPosition);
            if (childAt != null) {
                int i = android.widget.AbsListView.this.mMotionPosition;
                long itemId = android.widget.AbsListView.this.mAdapter.getItemId(android.widget.AbsListView.this.mMotionPosition);
                if (!sameWindow() || android.widget.AbsListView.this.mDataChanged) {
                    z = false;
                } else if (this.mX != -1.0f && this.mY != -1.0f) {
                    z = android.widget.AbsListView.this.performLongPress(childAt, i, itemId, this.mX, this.mY);
                } else {
                    z = android.widget.AbsListView.this.performLongPress(childAt, i, itemId);
                }
                if (z) {
                    android.widget.AbsListView.this.mHasPerformedLongPress = true;
                    android.widget.AbsListView.this.mTouchMode = -1;
                    android.widget.AbsListView.this.setPressed(false);
                    childAt.setPressed(false);
                    return;
                }
                android.widget.AbsListView.this.mTouchMode = 2;
            }
        }
    }

    private class CheckForKeyLongPress extends android.widget.AbsListView.WindowRunnnable implements java.lang.Runnable {
        private CheckForKeyLongPress() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            if (android.widget.AbsListView.this.isPressed() && android.widget.AbsListView.this.mSelectedPosition >= 0) {
                android.view.View childAt = android.widget.AbsListView.this.getChildAt(android.widget.AbsListView.this.mSelectedPosition - android.widget.AbsListView.this.mFirstPosition);
                if (!android.widget.AbsListView.this.mDataChanged) {
                    if (!sameWindow()) {
                        z = false;
                    } else {
                        z = android.widget.AbsListView.this.performLongPress(childAt, android.widget.AbsListView.this.mSelectedPosition, android.widget.AbsListView.this.mSelectedRowId);
                    }
                    if (z) {
                        android.widget.AbsListView.this.setPressed(false);
                        childAt.setPressed(false);
                        return;
                    }
                    return;
                }
                android.widget.AbsListView.this.setPressed(false);
                if (childAt != null) {
                    childAt.setPressed(false);
                }
            }
        }
    }

    private boolean performStylusButtonPressAction(android.view.MotionEvent motionEvent) {
        android.view.View childAt;
        if (this.mChoiceMode != 3 || this.mChoiceActionMode != null || (childAt = getChildAt(this.mMotionPosition - this.mFirstPosition)) == null || !performLongPress(childAt, this.mMotionPosition, this.mAdapter.getItemId(this.mMotionPosition))) {
            return false;
        }
        this.mTouchMode = -1;
        setPressed(false);
        childAt.setPressed(false);
        return true;
    }

    boolean performLongPress(android.view.View view, int i, long j) {
        return performLongPress(view, i, j, -1.0f, -1.0f);
    }

    boolean performLongPress(android.view.View view, int i, long j, float f, float f2) {
        boolean z;
        if (this.mChoiceMode == 3) {
            if (this.mChoiceActionMode == null) {
                android.view.ActionMode startActionMode = startActionMode(this.mMultiChoiceModeCallback);
                this.mChoiceActionMode = startActionMode;
                if (startActionMode != null) {
                    setItemChecked(i, true);
                    performHapticFeedback(0);
                }
            }
            return true;
        }
        if (this.mOnItemLongClickListener == null) {
            z = false;
        } else {
            z = this.mOnItemLongClickListener.onItemLongClick(this, view, i, j);
        }
        if (!z) {
            this.mContextMenuInfo = createContextMenuInfo(view, i, j);
            if (f != -1.0f && f2 != -1.0f) {
                z = super.showContextMenuForChild(this, f, f2);
            } else {
                z = super.showContextMenuForChild(this);
            }
        }
        if (z) {
            performHapticFeedback(0);
        }
        return z;
    }

    @Override // android.view.View
    protected android.view.ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
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
        int pointToPosition = pointToPosition((int) f, (int) f2);
        if (pointToPosition != -1) {
            long itemId = this.mAdapter.getItemId(pointToPosition);
            android.view.View childAt = getChildAt(pointToPosition - this.mFirstPosition);
            if (childAt != null) {
                this.mContextMenuInfo = createContextMenuInfo(childAt, pointToPosition, itemId);
                if (z) {
                    return super.showContextMenuForChild(this, f, f2);
                }
                return super.showContextMenuForChild(this);
            }
        }
        if (z) {
            return super.showContextMenu(f, f2);
        }
        return super.showContextMenu();
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
        boolean z2 = false;
        if (positionForView < 0) {
            return false;
        }
        long itemId = this.mAdapter.getItemId(positionForView);
        if (this.mOnItemLongClickListener != null) {
            z2 = this.mOnItemLongClickListener.onItemLongClick(this, view, positionForView, itemId);
        }
        if (!z2) {
            this.mContextMenuInfo = createContextMenuInfo(getChildAt(positionForView - this.mFirstPosition), positionForView, itemId);
            if (z) {
                return super.showContextMenuForChild(view, f, f2);
            }
            return super.showContextMenuForChild(view);
        }
        return z2;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (android.view.KeyEvent.isConfirmKey(i)) {
            if (!isEnabled()) {
                return true;
            }
            if (isClickable() && isPressed() && this.mSelectedPosition >= 0 && this.mAdapter != null && this.mSelectedPosition < this.mAdapter.getCount()) {
                android.view.View childAt = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (childAt != null) {
                    performItemClick(childAt, this.mSelectedPosition, this.mSelectedRowId);
                    childAt.setPressed(false);
                }
                setPressed(false);
                return true;
            }
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSetPressed(boolean z) {
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDrawableHotspotChanged(float f, float f2) {
    }

    public int pointToPosition(int i, int i2) {
        android.graphics.Rect rect = this.mTouchFrame;
        if (rect == null) {
            this.mTouchFrame = new android.graphics.Rect();
            rect = this.mTouchFrame;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i, i2)) {
                    return this.mFirstPosition + childCount;
                }
            }
        }
        return -1;
    }

    public long pointToRowId(int i, int i2) {
        int pointToPosition = pointToPosition(i, i2);
        if (pointToPosition >= 0) {
            return this.mAdapter.getItemId(pointToPosition);
        }
        return Long.MIN_VALUE;
    }

    private final class CheckForTap implements java.lang.Runnable {
        float x;
        float y;

        private CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.widget.AbsListView.this.mTouchMode == 0) {
                android.widget.AbsListView.this.mTouchMode = 1;
                android.view.View childAt = android.widget.AbsListView.this.getChildAt(android.widget.AbsListView.this.mMotionPosition - android.widget.AbsListView.this.mFirstPosition);
                if (childAt != null && !childAt.hasExplicitFocusable()) {
                    android.widget.AbsListView.this.mLayoutMode = 0;
                    if (!android.widget.AbsListView.this.mDataChanged) {
                        float[] fArr = android.widget.AbsListView.this.mTmpPoint;
                        fArr[0] = this.x;
                        fArr[1] = this.y;
                        android.widget.AbsListView.this.transformPointToViewLocal(fArr, childAt);
                        childAt.drawableHotspotChanged(fArr[0], fArr[1]);
                        childAt.setPressed(true);
                        android.widget.AbsListView.this.setPressed(true);
                        android.widget.AbsListView.this.layoutChildren();
                        android.widget.AbsListView.this.positionSelector(android.widget.AbsListView.this.mMotionPosition, childAt);
                        android.widget.AbsListView.this.refreshDrawableState();
                        int longPressTimeout = android.view.ViewConfiguration.getLongPressTimeout();
                        boolean isLongClickable = android.widget.AbsListView.this.isLongClickable();
                        if (android.widget.AbsListView.this.mSelector != null) {
                            android.graphics.drawable.Drawable current = android.widget.AbsListView.this.mSelector.getCurrent();
                            if (current != null && (current instanceof android.graphics.drawable.TransitionDrawable)) {
                                if (isLongClickable) {
                                    ((android.graphics.drawable.TransitionDrawable) current).startTransition(longPressTimeout);
                                } else {
                                    ((android.graphics.drawable.TransitionDrawable) current).resetTransition();
                                }
                            }
                            android.widget.AbsListView.this.mSelector.setHotspot(this.x, this.y);
                        }
                        if (isLongClickable) {
                            if (android.widget.AbsListView.this.mPendingCheckForLongPress == null) {
                                android.widget.AbsListView.this.mPendingCheckForLongPress = new android.widget.AbsListView.CheckForLongPress();
                            }
                            android.widget.AbsListView.this.mPendingCheckForLongPress.setCoords(this.x, this.y);
                            android.widget.AbsListView.this.mPendingCheckForLongPress.rememberWindowAttachCount();
                            android.widget.AbsListView.this.postDelayed(android.widget.AbsListView.this.mPendingCheckForLongPress, longPressTimeout);
                            return;
                        }
                        android.widget.AbsListView.this.mTouchMode = 2;
                        return;
                    }
                    android.widget.AbsListView.this.mTouchMode = 2;
                }
            }
        }
    }

    private boolean startScrollIfNeeded(int i, int i2, android.view.MotionEvent motionEvent) {
        int i3 = i2 - this.mMotionY;
        int abs = java.lang.Math.abs(i3);
        boolean z = this.mScrollY != 0;
        if ((!z && abs <= this.mTouchSlop) || (getNestedScrollAxes() & 2) != 0) {
            return false;
        }
        createScrollingCache();
        if (z) {
            this.mTouchMode = 5;
            this.mMotionCorrection = 0;
        } else {
            this.mTouchMode = 3;
            this.mMotionCorrection = i3 > 0 ? this.mTouchSlop : -this.mTouchSlop;
        }
        removeCallbacks(this.mPendingCheckForLongPress);
        setPressed(false);
        android.view.View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (childAt != null) {
            childAt.setPressed(false);
        }
        reportScrollStateChange(1);
        android.view.ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(i, i2, motionEvent);
        return true;
    }

    private void scrollIfNeeded(int i, int i2, android.view.MotionEvent motionEvent) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int childCount;
        int i10;
        boolean z;
        android.view.ViewParent parent;
        int i11 = i2 - this.mMotionY;
        if (this.mLastY == Integer.MIN_VALUE) {
            i11 -= this.mMotionCorrection;
        }
        int releaseGlow = releaseGlow(this.mLastY != Integer.MIN_VALUE ? i2 - this.mLastY : i11, i);
        int i12 = 0;
        if (!dispatchNestedPreScroll(0, -releaseGlow, this.mScrollConsumed, this.mScrollOffset)) {
            i3 = i11;
            i4 = releaseGlow;
            i5 = 0;
        } else {
            int i13 = i11 + this.mScrollConsumed[1];
            int i14 = -this.mScrollOffset[1];
            int i15 = releaseGlow + this.mScrollConsumed[1];
            if (motionEvent != null) {
                motionEvent.offsetLocation(0.0f, this.mScrollOffset[1]);
                this.mNestedYOffset += this.mScrollOffset[1];
            }
            i3 = i13;
            i4 = i15;
            i5 = i14;
        }
        if (this.mTouchMode == 3) {
            if (this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan = android.os.StrictMode.enterCriticalSpan("AbsListView-scroll");
            }
            if (i2 != this.mLastY) {
                if ((this.mGroupFlags & 524288) == 0 && java.lang.Math.abs(i3) > this.mTouchSlop && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (this.mMotionPosition >= 0) {
                    childCount = this.mMotionPosition - this.mFirstPosition;
                } else {
                    childCount = getChildCount() / 2;
                }
                android.view.View childAt = getChildAt(childCount);
                if (childAt == null) {
                    i10 = 0;
                } else {
                    i10 = childAt.getTop();
                }
                if (i4 == 0) {
                    z = false;
                } else {
                    z = trackMotionScroll(i3, i4);
                }
                android.view.View childAt2 = getChildAt(childCount);
                if (childAt2 != null) {
                    int top = childAt2.getTop();
                    if (z) {
                        int i16 = (-i4) - (top - i10);
                        if (!dispatchNestedScroll(0, i16 - i4, 0, i16, this.mScrollOffset)) {
                            int i17 = i4;
                            boolean overScrollBy = overScrollBy(0, i16, 0, this.mScrollY, 0, 0, 0, this.mOverscrollDistance, true);
                            if (overScrollBy && this.mVelocityTracker != null) {
                                this.mVelocityTracker.clear();
                            }
                            int overScrollMode = getOverScrollMode();
                            if (overScrollMode == 0 || (overScrollMode == 1 && !contentFits())) {
                                if (!overScrollBy) {
                                    this.mDirection = 0;
                                    this.mTouchMode = 5;
                                }
                                if (i17 > 0) {
                                    this.mEdgeGlowTop.onPullDistance((-i16) / getHeight(), i / getWidth());
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        this.mEdgeGlowBottom.onRelease();
                                    }
                                    invalidateEdgeEffects();
                                } else if (i17 < 0) {
                                    this.mEdgeGlowBottom.onPullDistance(i16 / getHeight(), 1.0f - (i / getWidth()));
                                    if (!this.mEdgeGlowTop.isFinished()) {
                                        this.mEdgeGlowTop.onRelease();
                                    }
                                    invalidateEdgeEffects();
                                }
                            }
                        } else {
                            i12 = 0 - this.mScrollOffset[1];
                            if (motionEvent != null) {
                                motionEvent.offsetLocation(0.0f, this.mScrollOffset[1]);
                                this.mNestedYOffset += this.mScrollOffset[1];
                            }
                        }
                    }
                    this.mMotionY = i2 + i12 + i5;
                }
                this.mLastY = i2 + i12 + i5;
                return;
            }
            return;
        }
        int i18 = i4;
        if (this.mTouchMode == 5 && i2 != this.mLastY) {
            int i19 = this.mScrollY;
            int i20 = i19 - i18;
            int i21 = i2 > this.mLastY ? 1 : -1;
            if (this.mDirection == 0) {
                this.mDirection = i21;
            }
            int i22 = -i18;
            if ((i20 < 0 && i19 >= 0) || (i20 > 0 && i19 <= 0)) {
                int i23 = -i19;
                i7 = i18 + i23;
                i6 = i23;
            } else {
                i6 = i22;
                i7 = 0;
            }
            if (i6 != 0) {
                int i24 = i6;
                i8 = i7;
                i9 = i21;
                overScrollBy(0, i6, 0, this.mScrollY, 0, 0, 0, this.mOverscrollDistance, true);
                int overScrollMode2 = getOverScrollMode();
                if (overScrollMode2 == 0 || (overScrollMode2 == 1 && !contentFits())) {
                    if (i3 > 0) {
                        this.mEdgeGlowTop.onPullDistance(i24 / getHeight(), i / getWidth());
                        if (!this.mEdgeGlowBottom.isFinished()) {
                            this.mEdgeGlowBottom.onRelease();
                        }
                        invalidateEdgeEffects();
                    } else if (i3 < 0) {
                        this.mEdgeGlowBottom.onPullDistance((-i24) / getHeight(), 1.0f - (i / getWidth()));
                        if (!this.mEdgeGlowTop.isFinished()) {
                            this.mEdgeGlowTop.onRelease();
                        }
                        invalidateEdgeEffects();
                    }
                }
            } else {
                i8 = i7;
                i9 = i21;
            }
            int i25 = i8;
            if (i25 != 0) {
                if (this.mScrollY != 0) {
                    this.mScrollY = 0;
                    invalidateParentIfNeeded();
                }
                trackMotionScroll(i25, i25);
                this.mTouchMode = 3;
                int findClosestMotionRow = findClosestMotionRow(i2);
                this.mMotionCorrection = 0;
                android.view.View childAt3 = getChildAt(findClosestMotionRow - this.mFirstPosition);
                this.mMotionViewOriginalTop = childAt3 != null ? childAt3.getTop() : 0;
                this.mMotionY = i2 + i5;
                this.mMotionPosition = findClosestMotionRow;
            }
            this.mLastY = 0 + i2 + i5;
            this.mDirection = i9;
        }
    }

    private int releaseGlow(int i, int i2) {
        float f = 0.0f;
        if (this.mEdgeGlowTop.getDistance() != 0.0f) {
            if (canScrollUp()) {
                this.mEdgeGlowTop.onRelease();
            } else {
                f = this.mEdgeGlowTop.onPullDistance(i / getHeight(), i2 / getWidth());
            }
            invalidateEdgeEffects();
        } else if (this.mEdgeGlowBottom.getDistance() != 0.0f) {
            if (canScrollDown()) {
                this.mEdgeGlowBottom.onRelease();
            } else {
                f = -this.mEdgeGlowBottom.onPullDistance((-i) / getHeight(), 1.0f - (i2 / getWidth()));
            }
            invalidateEdgeEffects();
        }
        return i - java.lang.Math.round(f * getHeight());
    }

    private boolean doesTouchStopStretch() {
        return ((this.mEdgeGlowBottom.getDistance() == 0.0f || canScrollDown()) && (this.mEdgeGlowTop.getDistance() == 0.0f || canScrollUp())) ? false : true;
    }

    private void invalidateEdgeEffects() {
        if (!shouldDisplayEdgeEffects()) {
            return;
        }
        invalidate();
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    public void onTouchModeChanged(boolean z) {
        if (z) {
            hideSelector();
            if (getHeight() > 0 && getChildCount() > 0) {
                layoutChildren();
            }
            updateSelectorState();
            return;
        }
        int i = this.mTouchMode;
        if (i == 5 || i == 6) {
            if (this.mFlingRunnable != null) {
                this.mFlingRunnable.endFling();
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            if (this.mScrollY != 0) {
                this.mScrollY = 0;
                invalidateParentCaches();
                finishGlows();
                invalidate();
            }
        }
    }

    @Override // android.view.View
    protected boolean handleScrollBarDragging(android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (!isEnabled()) {
            return isClickable() || isLongClickable();
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            return false;
        }
        startNestedScroll(2);
        if (this.mFastScroll != null && this.mFastScroll.onTouchEvent(motionEvent)) {
            return true;
        }
        initVelocityTrackerIfNotExists();
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        obtain.offsetLocation(0.0f, this.mNestedYOffset);
        switch (actionMasked) {
            case 0:
                onTouchDown(motionEvent);
                break;
            case 1:
                onTouchUp(motionEvent);
                break;
            case 2:
                onTouchMove(motionEvent, obtain);
                break;
            case 3:
                onTouchCancel();
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                int pointerId = motionEvent.getPointerId(actionIndex);
                int x = (int) motionEvent.getX(actionIndex);
                int y = (int) motionEvent.getY(actionIndex);
                this.mMotionCorrection = 0;
                this.mActivePointerId = pointerId;
                this.mMotionX = x;
                this.mMotionY = y;
                int pointToPosition = pointToPosition(x, y);
                if (pointToPosition >= 0) {
                    this.mMotionViewOriginalTop = getChildAt(pointToPosition - this.mFirstPosition).getTop();
                    this.mMotionPosition = pointToPosition;
                }
                this.mLastY = y;
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                int i = this.mMotionX;
                int i2 = this.mMotionY;
                int pointToPosition2 = pointToPosition(i, i2);
                if (pointToPosition2 >= 0) {
                    this.mMotionViewOriginalTop = getChildAt(pointToPosition2 - this.mFirstPosition).getTop();
                    this.mMotionPosition = pointToPosition2;
                }
                this.mLastY = i2;
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    private void onTouchDown(android.view.MotionEvent motionEvent) {
        this.mHasPerformedLongPress = false;
        this.mActivePointerId = motionEvent.getPointerId(0);
        hideSelector();
        if (this.mTouchMode == 6) {
            if (this.mFlingRunnable != null) {
                this.mFlingRunnable.endFling();
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            this.mTouchMode = 5;
            this.mMotionX = (int) motionEvent.getX();
            this.mMotionY = (int) motionEvent.getY();
            this.mLastY = this.mMotionY;
            this.mMotionCorrection = 0;
            this.mDirection = 0;
            stopEdgeGlowRecede(motionEvent.getX());
        } else {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int pointToPosition = pointToPosition(x, y);
            if (!this.mDataChanged) {
                if (this.mTouchMode == 4) {
                    createScrollingCache();
                    this.mTouchMode = 3;
                    this.mMotionCorrection = 0;
                    pointToPosition = findMotionRow(y);
                    if (this.mFlingRunnable != null) {
                        this.mFlingRunnable.flywheelTouch();
                    }
                    stopEdgeGlowRecede(x);
                } else if (pointToPosition >= 0 && getAdapter().isEnabled(pointToPosition)) {
                    this.mTouchMode = 0;
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new android.widget.AbsListView.CheckForTap();
                    }
                    this.mPendingCheckForTap.x = motionEvent.getX();
                    this.mPendingCheckForTap.y = motionEvent.getY();
                    postDelayed(this.mPendingCheckForTap, android.view.ViewConfiguration.getTapTimeout());
                }
            }
            if (pointToPosition >= 0) {
                this.mMotionViewOriginalTop = getChildAt(pointToPosition - this.mFirstPosition).getTop();
            }
            this.mMotionX = x;
            this.mMotionY = y;
            this.mMotionPosition = pointToPosition;
            this.mLastY = Integer.MIN_VALUE;
        }
        if (this.mTouchMode == 0 && this.mMotionPosition != -1 && performButtonActionOnTouchDown(motionEvent)) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    private void stopEdgeGlowRecede(float f) {
        if (this.mEdgeGlowTop.getDistance() != 0.0f) {
            this.mEdgeGlowTop.onPullDistance(0.0f, f / getWidth());
        }
        if (this.mEdgeGlowBottom.getDistance() != 0.0f) {
            this.mEdgeGlowBottom.onPullDistance(0.0f, f / getWidth());
        }
    }

    private void onTouchMove(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2) {
        if (this.mHasPerformedLongPress) {
        }
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex == -1) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            findPointerIndex = 0;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        int y = (int) motionEvent.getY(findPointerIndex);
        switch (this.mTouchMode) {
            case 0:
            case 1:
            case 2:
                if (!startScrollIfNeeded((int) motionEvent.getX(findPointerIndex), y, motionEvent2)) {
                    android.view.View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
                    float x = motionEvent.getX(findPointerIndex);
                    float f = y;
                    if (!pointInView(x, f, this.mTouchSlop)) {
                        setPressed(false);
                        if (childAt != null) {
                            childAt.setPressed(false);
                        }
                        removeCallbacks(this.mTouchMode == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress);
                        this.mTouchMode = 2;
                        updateSelectorState();
                        break;
                    } else if (childAt != null) {
                        float[] fArr = this.mTmpPoint;
                        fArr[0] = x;
                        fArr[1] = f;
                        transformPointToViewLocal(fArr, childAt);
                        childAt.drawableHotspotChanged(fArr[0], fArr[1]);
                        break;
                    }
                }
                break;
            case 3:
            case 5:
                scrollIfNeeded((int) motionEvent.getX(findPointerIndex), y, motionEvent2);
                break;
        }
    }

    private void onTouchUp(android.view.MotionEvent motionEvent) {
        switch (this.mTouchMode) {
            case 0:
            case 1:
            case 2:
                int i = this.mMotionPosition;
                final android.view.View childAt = getChildAt(i - this.mFirstPosition);
                if (childAt != null) {
                    if (this.mTouchMode != 0) {
                        childAt.setPressed(false);
                    }
                    float x = motionEvent.getX();
                    if ((x > ((float) this.mListPadding.left) && x < ((float) (getWidth() - this.mListPadding.right))) && !childAt.hasExplicitFocusable()) {
                        if (this.mPerformClick == null) {
                            this.mPerformClick = new android.widget.AbsListView.PerformClick();
                        }
                        final android.widget.AbsListView.PerformClick performClick = this.mPerformClick;
                        performClick.mClickMotionPosition = i;
                        performClick.rememberWindowAttachCount();
                        this.mResurrectToPosition = i;
                        if (this.mTouchMode == 0 || this.mTouchMode == 1) {
                            removeCallbacks(this.mTouchMode == 0 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress);
                            this.mLayoutMode = 0;
                            if (!this.mDataChanged && this.mAdapter.isEnabled(i)) {
                                this.mTouchMode = 1;
                                setSelectedPositionInt(this.mMotionPosition);
                                layoutChildren();
                                childAt.setPressed(true);
                                positionSelector(this.mMotionPosition, childAt);
                                setPressed(true);
                                if (this.mSelector != null) {
                                    android.graphics.drawable.Drawable current = this.mSelector.getCurrent();
                                    if (current != null && (current instanceof android.graphics.drawable.TransitionDrawable)) {
                                        ((android.graphics.drawable.TransitionDrawable) current).resetTransition();
                                    }
                                    this.mSelector.setHotspot(x, motionEvent.getY());
                                }
                                if (this.mTouchModeReset != null) {
                                    removeCallbacks(this.mTouchModeReset);
                                }
                                this.mTouchModeReset = new java.lang.Runnable() { // from class: android.widget.AbsListView.3
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        android.widget.AbsListView.this.mTouchModeReset = null;
                                        android.widget.AbsListView.this.mTouchMode = -1;
                                        childAt.setPressed(false);
                                        android.widget.AbsListView.this.setPressed(false);
                                        if (!android.widget.AbsListView.this.mDataChanged && !android.widget.AbsListView.this.mIsDetaching && android.widget.AbsListView.this.isAttachedToWindow()) {
                                            performClick.run();
                                        }
                                    }
                                };
                                postDelayed(this.mTouchModeReset, android.view.ViewConfiguration.getPressedStateDuration());
                                return;
                            }
                            this.mTouchMode = -1;
                            updateSelectorState();
                            return;
                        }
                        if (!this.mDataChanged && this.mAdapter.isEnabled(i)) {
                            performClick.run();
                        }
                    }
                }
                this.mTouchMode = -1;
                updateSelectorState();
                break;
            case 3:
                int childCount = getChildCount();
                if (childCount > 0) {
                    int top = getChildAt(0).getTop();
                    int bottom = getChildAt(childCount - 1).getBottom();
                    int i2 = this.mListPadding.top;
                    int height = getHeight() - this.mListPadding.bottom;
                    if (this.mFirstPosition == 0 && top >= i2 && this.mFirstPosition + childCount < this.mItemCount && bottom <= getHeight() - height) {
                        this.mTouchMode = -1;
                        reportScrollStateChange(0);
                        break;
                    } else {
                        android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                        int yVelocity = (int) (velocityTracker.getYVelocity(this.mActivePointerId) * this.mVelocityScale);
                        boolean z = java.lang.Math.abs(yVelocity) > this.mMinimumVelocity;
                        if (z && !this.mEdgeGlowTop.isFinished()) {
                            if (shouldAbsorb(this.mEdgeGlowTop, yVelocity)) {
                                this.mEdgeGlowTop.onAbsorb(yVelocity);
                                break;
                            } else {
                                if (this.mFlingRunnable == null) {
                                    this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
                                }
                                this.mFlingRunnable.start(-yVelocity);
                                break;
                            }
                        } else if (z && !this.mEdgeGlowBottom.isFinished()) {
                            int i3 = -yVelocity;
                            if (shouldAbsorb(this.mEdgeGlowBottom, i3)) {
                                this.mEdgeGlowBottom.onAbsorb(i3);
                                break;
                            } else {
                                if (this.mFlingRunnable == null) {
                                    this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
                                }
                                this.mFlingRunnable.start(i3);
                                break;
                            }
                        } else if (z && ((this.mFirstPosition != 0 || top != i2 - this.mOverscrollDistance) && (this.mFirstPosition + childCount != this.mItemCount || bottom != height + this.mOverscrollDistance))) {
                            int i4 = -yVelocity;
                            float f = i4;
                            if (!dispatchNestedPreFling(0.0f, f)) {
                                if (this.mFlingRunnable == null) {
                                    this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
                                }
                                reportScrollStateChange(2);
                                this.mFlingRunnable.start(i4);
                                dispatchNestedFling(0.0f, f, true);
                                break;
                            } else {
                                this.mTouchMode = -1;
                                reportScrollStateChange(0);
                                break;
                            }
                        } else {
                            this.mTouchMode = -1;
                            reportScrollStateChange(0);
                            if (this.mFlingRunnable != null) {
                                this.mFlingRunnable.endFling();
                            }
                            if (this.mPositionScroller != null) {
                                this.mPositionScroller.stop();
                            }
                            if (z) {
                                float f2 = -yVelocity;
                                if (!dispatchNestedPreFling(0.0f, f2)) {
                                    dispatchNestedFling(0.0f, f2, false);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    this.mTouchMode = -1;
                    reportScrollStateChange(0);
                    break;
                }
                break;
            case 5:
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
                }
                android.view.VelocityTracker velocityTracker2 = this.mVelocityTracker;
                velocityTracker2.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int yVelocity2 = (int) velocityTracker2.getYVelocity(this.mActivePointerId);
                reportScrollStateChange(2);
                if (java.lang.Math.abs(yVelocity2) > this.mMinimumVelocity) {
                    this.mFlingRunnable.startOverfling(-yVelocity2);
                    break;
                } else {
                    this.mFlingRunnable.startSpringback();
                    break;
                }
        }
        setPressed(false);
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        invalidate();
        removeCallbacks(this.mPendingCheckForLongPress);
        recycleVelocityTracker();
        this.mActivePointerId = -1;
        if (this.mScrollStrictSpan != null) {
            this.mScrollStrictSpan.finish();
            this.mScrollStrictSpan = null;
        }
    }

    private boolean shouldAbsorb(android.widget.EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        float distance = edgeEffect.getDistance() * getHeight();
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
        }
        return this.mFlingRunnable.getSplineFlingDistance(-i) < distance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int consumeFlingInStretch(int i) {
        if (i < 0 && this.mEdgeGlowTop != null && this.mEdgeGlowTop.getDistance() != 0.0f) {
            float height = getHeight();
            int round = java.lang.Math.round((height / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowTop.onPullDistance((i * FLING_DESTRETCH_FACTOR) / height, 0.5f));
            if (round != i) {
                this.mEdgeGlowTop.finish();
            }
            return i - round;
        }
        if (i > 0 && this.mEdgeGlowBottom != null && this.mEdgeGlowBottom.getDistance() != 0.0f) {
            int round2 = java.lang.Math.round(((-r1) / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowBottom.onPullDistance(((-i) * FLING_DESTRETCH_FACTOR) / getHeight(), 0.5f));
            if (round2 != i) {
                this.mEdgeGlowBottom.finish();
            }
            return i - round2;
        }
        return i;
    }

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    private void onTouchCancel() {
        switch (this.mTouchMode) {
            case 5:
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
                }
                this.mFlingRunnable.startSpringback();
                break;
            case 6:
                break;
            default:
                this.mTouchMode = -1;
                setPressed(false);
                android.view.View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
                if (childAt != null) {
                    childAt.setPressed(false);
                }
                clearScrollingCache();
                removeCallbacks(this.mPendingCheckForLongPress);
                recycleVelocityTracker();
                break;
        }
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        this.mActivePointerId = -1;
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (this.mScrollY != i2) {
            onScrollChanged(this.mScrollX, i2, this.mScrollX, this.mScrollY);
            this.mScrollY = i2;
            invalidateParentIfNeeded();
            awakenScrollBars();
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        int i;
        int i2;
        int actionButton;
        switch (motionEvent.getAction()) {
            case 8:
                if (motionEvent.isFromSource(2)) {
                    i = 9;
                } else if (motionEvent.isFromSource(4194304)) {
                    i = 26;
                } else {
                    i = -1;
                }
                int round = java.lang.Math.round((i == -1 ? 0.0f : motionEvent.getAxisValue(i)) * this.mVerticalScrollFactor);
                if (round != 0) {
                    android.view.View childAt = getChildAt(round > 0 ? 0 : getChildCount() - 1);
                    if (childAt == null) {
                        i2 = 0;
                    } else {
                        i2 = childAt.getTop();
                    }
                    int overScrollMode = getOverScrollMode();
                    if (!trackMotionScroll(round, round)) {
                        if (android.view.flags.Flags.scrollFeedbackApi()) {
                            initHapticScrollFeedbackProviderIfNotExists();
                            this.mHapticScrollFeedbackProvider.onScrollProgress(motionEvent.getDeviceId(), motionEvent.getSource(), i, round);
                        }
                        initDifferentialFlingHelperIfNotExists();
                        this.mDifferentialMotionFlingHelper.onMotionEvent(motionEvent, i);
                        return true;
                    }
                    if (!motionEvent.isFromSource(8194) && childAt != null && (overScrollMode == 0 || (overScrollMode == 1 && !contentFits()))) {
                        float top = (round - (childAt.getTop() - i2)) / getHeight();
                        boolean z = round > 0;
                        if (android.view.flags.Flags.scrollFeedbackApi()) {
                            initHapticScrollFeedbackProviderIfNotExists();
                            this.mHapticScrollFeedbackProvider.onScrollLimit(motionEvent.getDeviceId(), motionEvent.getSource(), i, z);
                        }
                        if (z) {
                            this.mEdgeGlowTop.onPullDistance(top, 0.5f);
                            this.mEdgeGlowTop.onRelease();
                        } else {
                            this.mEdgeGlowBottom.onPullDistance(-top, 0.5f);
                            this.mEdgeGlowBottom.onRelease();
                        }
                        invalidate();
                        return true;
                    }
                }
                break;
            case 11:
                if (motionEvent.isFromSource(2) && (((actionButton = motionEvent.getActionButton()) == 32 || actionButton == 2) && ((this.mTouchMode == 0 || this.mTouchMode == 1) && performStylusButtonPressAction(motionEvent)))) {
                    removeCallbacks(this.mPendingCheckForLongPress);
                    removeCallbacks(this.mPendingCheckForTap);
                    break;
                }
                break;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public void fling(int i) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.start(i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i) {
        return (i & 2) != 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        android.view.View childAt = getChildAt(getChildCount() / 2);
        int top = childAt != null ? childAt.getTop() : 0;
        if (childAt != null) {
            int i7 = -i4;
            if (!trackMotionScroll(i7, i7)) {
                return;
            }
        }
        if (childAt == null) {
            i5 = 0;
            i6 = i4;
        } else {
            int top2 = childAt.getTop() - top;
            i5 = top2;
            i6 = i4 - top2;
        }
        dispatchNestedScroll(0, i5, 0, i6, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(android.view.View view, float f, float f2, boolean z) {
        int childCount = getChildCount();
        if (!z && childCount > 0) {
            int i = (int) f2;
            if (canScrollList(i) && java.lang.Math.abs(f2) > this.mMinimumVelocity) {
                reportScrollStateChange(2);
                if (this.mFlingRunnable == null) {
                    this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
                }
                if (!dispatchNestedPreFling(0.0f, f2)) {
                    this.mFlingRunnable.start(i);
                    return true;
                }
                return true;
            }
        }
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        int width;
        int height;
        int i;
        int i2;
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int i3 = this.mScrollY;
            boolean clipToPadding = getClipToPadding();
            if (clipToPadding) {
                width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                i = this.mPaddingLeft;
                i2 = this.mPaddingTop;
            } else {
                width = getWidth();
                height = getHeight();
                i = 0;
                i2 = 0;
            }
            this.mEdgeGlowTop.setSize(width, height);
            this.mEdgeGlowBottom.setSize(width, height);
            if (!this.mEdgeGlowTop.isFinished()) {
                int save = canvas.save();
                canvas.clipRect(i, i2, i + width, this.mEdgeGlowTop.getMaxHeight() + i2);
                canvas.translate(i, java.lang.Math.min(0, this.mFirstPositionDistanceGuess + i3) + i2);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    invalidateEdgeEffects();
                }
                canvas.restoreToCount(save);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                int save2 = canvas.save();
                int i4 = i2 + height;
                canvas.clipRect(i, i4 - this.mEdgeGlowBottom.getMaxHeight(), i + width, i4);
                canvas.translate((-width) + i, java.lang.Math.max(getHeight(), i3 + this.mLastPositionDistanceGuess) - (clipToPadding ? this.mPaddingBottom : 0));
                canvas.rotate(180.0f, width, 0.0f);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    invalidateEdgeEffects();
                }
                canvas.restoreToCount(save2);
            }
        }
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
    }

    private void initDifferentialFlingHelperIfNotExists() {
        if (this.mDifferentialMotionFlingHelper == null) {
            this.mDifferentialMotionFlingHelper = new android.widget.DifferentialMotionFlingHelper(this.mContext, new android.widget.AbsListView.DifferentialFlingTarget());
        }
    }

    private void initHapticScrollFeedbackProviderIfNotExists() {
        if (this.mHapticScrollFeedbackProvider == null) {
            this.mHapticScrollFeedbackProvider = new android.view.HapticScrollFeedbackProvider(this);
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptHoverEvent(android.view.MotionEvent motionEvent) {
        if (this.mFastScroll != null && this.mFastScroll.onInterceptHoverEvent(motionEvent)) {
            return true;
        }
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        android.view.PointerIcon onResolvePointerIcon;
        if (this.mFastScroll != null && motionEvent.isFromSource(8194) && (onResolvePointerIcon = this.mFastScroll.onResolvePointerIcon(motionEvent, i)) != null) {
            return onResolvePointerIcon;
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cf A[FALL_THROUGH, RETURN] */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mIsDetaching || !isAttachedToWindow()) {
            return false;
        }
        if (this.mFastScroll != null && this.mFastScroll.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        switch (actionMasked) {
            case 0:
                int i = this.mTouchMode;
                if (i == 6 || i == 5) {
                    this.mMotionCorrection = 0;
                    return true;
                }
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                int findMotionRow = findMotionRow(y);
                if (doesTouchStopStretch()) {
                    this.mTouchMode = 4;
                    i = 4;
                } else if (i != 4 && findMotionRow >= 0) {
                    this.mMotionViewOriginalTop = getChildAt(findMotionRow - this.mFirstPosition).getTop();
                    this.mMotionX = x;
                    this.mMotionY = y;
                    this.mMotionPosition = findMotionRow;
                    this.mTouchMode = 0;
                    clearScrollingCache();
                }
                this.mLastY = Integer.MIN_VALUE;
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mNestedYOffset = 0;
                startNestedScroll(2);
                return i == 4;
            case 1:
            case 3:
                this.mTouchMode = -1;
                this.mActivePointerId = -1;
                recycleVelocityTracker();
                reportScrollStateChange(0);
                stopNestedScroll();
            case 2:
                switch (this.mTouchMode) {
                    case 0:
                        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (findPointerIndex == -1) {
                            this.mActivePointerId = motionEvent.getPointerId(0);
                            findPointerIndex = 0;
                        }
                        int y2 = (int) motionEvent.getY(findPointerIndex);
                        initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement(motionEvent);
                        if (startScrollIfNeeded((int) motionEvent.getX(findPointerIndex), y2, null)) {
                            return true;
                        }
                }
            case 6:
                onSecondaryPointerUp(motionEvent);
        }
    }

    private void onSecondaryPointerUp(android.view.MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mMotionX = (int) motionEvent.getX(i);
            this.mMotionY = (int) motionEvent.getY(i);
            this.mMotionCorrection = 0;
            this.mActivePointerId = motionEvent.getPointerId(i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(java.util.ArrayList<android.view.View> arrayList) {
        int childCount = getChildCount();
        int i = this.mFirstPosition;
        android.widget.ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (listAdapter.isEnabled(i + i2)) {
                arrayList.add(childAt);
            }
            childAt.addTouchables(arrayList);
        }
    }

    void reportScrollStateChange(int i) {
        if (i != this.mLastScrollState && this.mOnScrollListener != null) {
            this.mLastScrollState = i;
            this.mOnScrollListener.onScrollStateChanged(this, i);
        }
        if (i == 0 || i == 2) {
            this.mReportChildrenToContentCaptureOnNextUpdate = true;
        }
    }

    private class FlingRunnable implements java.lang.Runnable {
        private static final int FLYWHEEL_TIMEOUT = 40;
        private final java.lang.Runnable mCheckFlywheel = new java.lang.Runnable() { // from class: android.widget.AbsListView.FlingRunnable.1
            @Override // java.lang.Runnable
            public void run() {
                int i = android.widget.AbsListView.this.mActivePointerId;
                android.view.VelocityTracker velocityTracker = android.widget.AbsListView.this.mVelocityTracker;
                android.widget.OverScroller overScroller = android.widget.AbsListView.FlingRunnable.this.mScroller;
                if (velocityTracker == null || i == -1) {
                    return;
                }
                velocityTracker.computeCurrentVelocity(1000, android.widget.AbsListView.this.mMaximumVelocity);
                float f = -velocityTracker.getYVelocity(i);
                if (java.lang.Math.abs(f) >= android.widget.AbsListView.this.mMinimumVelocity && overScroller.isScrollingInDirection(0.0f, f)) {
                    android.widget.AbsListView.this.postDelayed(this, 40L);
                    return;
                }
                android.widget.AbsListView.FlingRunnable.this.endFling();
                android.widget.AbsListView.this.mTouchMode = 3;
                android.widget.AbsListView.this.reportScrollStateChange(1);
            }
        };
        private int mLastFlingY;
        private final android.widget.OverScroller mScroller;
        private boolean mSuppressIdleStateChangeCall;

        FlingRunnable() {
            this.mScroller = new android.widget.OverScroller(android.widget.AbsListView.this.getContext());
        }

        float getSplineFlingDistance(int i) {
            return (float) this.mScroller.getSplineFlingDistance(i);
        }

        void start(int i) {
            int i2 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = i2;
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, i2, 0, i, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            android.widget.AbsListView.this.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = false;
            android.widget.AbsListView.this.removeCallbacks(this);
            android.widget.AbsListView.this.postOnAnimation(this);
            if (android.widget.AbsListView.this.mFlingStrictSpan == null) {
                android.widget.AbsListView.this.mFlingStrictSpan = android.os.StrictMode.enterCriticalSpan("AbsListView-fling");
            }
        }

        void startSpringback() {
            this.mSuppressIdleStateChangeCall = false;
            if (this.mScroller.springBack(0, android.widget.AbsListView.this.mScrollY, 0, 0, 0, 0)) {
                android.widget.AbsListView.this.mTouchMode = 6;
                android.widget.AbsListView.this.invalidate();
                android.widget.AbsListView.this.postOnAnimation(this);
            } else {
                android.widget.AbsListView.this.mTouchMode = -1;
                android.widget.AbsListView.this.reportScrollStateChange(0);
            }
        }

        void startOverfling(int i) {
            this.mScroller.setInterpolator(null);
            this.mScroller.fling(0, android.widget.AbsListView.this.mScrollY, 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, android.widget.AbsListView.this.getHeight());
            android.widget.AbsListView.this.mTouchMode = 6;
            this.mSuppressIdleStateChangeCall = false;
            android.widget.AbsListView.this.invalidate();
            android.widget.AbsListView.this.postOnAnimation(this);
        }

        void edgeReached(int i) {
            this.mScroller.notifyVerticalEdgeReached(android.widget.AbsListView.this.mScrollY, 0, android.widget.AbsListView.this.mOverflingDistance);
            int overScrollMode = android.widget.AbsListView.this.getOverScrollMode();
            if (overScrollMode == 0 || (overScrollMode == 1 && !android.widget.AbsListView.this.contentFits())) {
                android.widget.AbsListView.this.mTouchMode = 6;
                int currVelocity = (int) this.mScroller.getCurrVelocity();
                if (i > 0) {
                    android.widget.AbsListView.this.mEdgeGlowTop.onAbsorb(currVelocity);
                } else {
                    android.widget.AbsListView.this.mEdgeGlowBottom.onAbsorb(currVelocity);
                }
            } else {
                android.widget.AbsListView.this.mTouchMode = -1;
                if (android.widget.AbsListView.this.mPositionScroller != null) {
                    android.widget.AbsListView.this.mPositionScroller.stop();
                }
            }
            android.widget.AbsListView.this.invalidate();
            android.widget.AbsListView.this.postOnAnimation(this);
        }

        void startScroll(int i, int i2, boolean z, boolean z2) {
            int i3 = i < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingY = i3;
            this.mScroller.setInterpolator(z ? android.widget.AbsListView.sLinearInterpolator : null);
            this.mScroller.startScroll(0, i3, 0, i, i2);
            android.widget.AbsListView.this.mTouchMode = 4;
            this.mSuppressIdleStateChangeCall = z2;
            android.widget.AbsListView.this.postOnAnimation(this);
        }

        void endFling() {
            android.widget.AbsListView.this.mTouchMode = -1;
            android.widget.AbsListView.this.removeCallbacks(this);
            android.widget.AbsListView.this.removeCallbacks(this.mCheckFlywheel);
            if (!this.mSuppressIdleStateChangeCall) {
                android.widget.AbsListView.this.reportScrollStateChange(0);
            }
            android.widget.AbsListView.this.clearScrollingCache();
            this.mScroller.abortAnimation();
            if (android.widget.AbsListView.this.mFlingStrictSpan != null) {
                android.widget.AbsListView.this.mFlingStrictSpan.finish();
                android.widget.AbsListView.this.mFlingStrictSpan = null;
            }
        }

        void flywheelTouch() {
            android.widget.AbsListView.this.postDelayed(this.mCheckFlywheel, 40L);
        }

        @Override // java.lang.Runnable
        public void run() {
            int max;
            int i;
            boolean z = false;
            switch (android.widget.AbsListView.this.mTouchMode) {
                case 3:
                    if (this.mScroller.isFinished()) {
                        return;
                    }
                    break;
                case 4:
                    break;
                case 5:
                default:
                    endFling();
                    return;
                case 6:
                    android.widget.OverScroller overScroller = this.mScroller;
                    if (overScroller.computeScrollOffset()) {
                        int i2 = android.widget.AbsListView.this.mScrollY;
                        int currY = overScroller.getCurrY();
                        if (android.widget.AbsListView.this.overScrollBy(0, currY - i2, 0, i2, 0, 0, 0, android.widget.AbsListView.this.mOverflingDistance, false)) {
                            boolean z2 = i2 <= 0 && currY > 0;
                            if (i2 >= 0 && currY < 0) {
                                z = true;
                            }
                            if (z2 || z) {
                                int currVelocity = (int) overScroller.getCurrVelocity();
                                if (z) {
                                    currVelocity = -currVelocity;
                                }
                                overScroller.abortAnimation();
                                start(currVelocity);
                                return;
                            }
                            startSpringback();
                            return;
                        }
                        android.widget.AbsListView.this.invalidate();
                        android.widget.AbsListView.this.postOnAnimation(this);
                        return;
                    }
                    endFling();
                    return;
            }
            if (android.widget.AbsListView.this.mDataChanged) {
                android.widget.AbsListView.this.layoutChildren();
            }
            if (android.widget.AbsListView.this.mItemCount == 0 || android.widget.AbsListView.this.getChildCount() == 0) {
                android.widget.AbsListView.this.mEdgeGlowBottom.onRelease();
                android.widget.AbsListView.this.mEdgeGlowTop.onRelease();
                endFling();
                return;
            }
            android.widget.OverScroller overScroller2 = this.mScroller;
            boolean computeScrollOffset = overScroller2.computeScrollOffset();
            int currY2 = overScroller2.getCurrY();
            int consumeFlingInStretch = android.widget.AbsListView.this.consumeFlingInStretch(this.mLastFlingY - currY2);
            if (consumeFlingInStretch > 0) {
                android.widget.AbsListView.this.mMotionPosition = android.widget.AbsListView.this.mFirstPosition;
                android.widget.AbsListView.this.mMotionViewOriginalTop = android.widget.AbsListView.this.getChildAt(0).getTop();
                max = java.lang.Math.min(((android.widget.AbsListView.this.getHeight() - android.widget.AbsListView.this.mPaddingBottom) - android.widget.AbsListView.this.mPaddingTop) - 1, consumeFlingInStretch);
            } else {
                int childCount = android.widget.AbsListView.this.getChildCount() - 1;
                android.widget.AbsListView.this.mMotionPosition = android.widget.AbsListView.this.mFirstPosition + childCount;
                android.widget.AbsListView.this.mMotionViewOriginalTop = android.widget.AbsListView.this.getChildAt(childCount).getTop();
                max = java.lang.Math.max(-(((android.widget.AbsListView.this.getHeight() - android.widget.AbsListView.this.mPaddingBottom) - android.widget.AbsListView.this.mPaddingTop) - 1), consumeFlingInStretch);
            }
            android.view.View childAt = android.widget.AbsListView.this.getChildAt(android.widget.AbsListView.this.mMotionPosition - android.widget.AbsListView.this.mFirstPosition);
            if (childAt == null) {
                i = 0;
            } else {
                i = childAt.getTop();
            }
            boolean trackMotionScroll = android.widget.AbsListView.this.trackMotionScroll(max, max);
            if (trackMotionScroll && max != 0) {
                z = true;
            }
            if (z) {
                if (childAt != null) {
                    android.widget.AbsListView.this.overScrollBy(0, -(max - (childAt.getTop() - i)), 0, android.widget.AbsListView.this.mScrollY, 0, 0, 0, android.widget.AbsListView.this.mOverflingDistance, false);
                }
                if (computeScrollOffset) {
                    edgeReached(max);
                    return;
                }
                return;
            }
            if (computeScrollOffset && !z) {
                if (trackMotionScroll) {
                    android.widget.AbsListView.this.invalidate();
                }
                this.mLastFlingY = currY2;
                android.widget.AbsListView.this.postOnAnimation(this);
                return;
            }
            endFling();
        }
    }

    public void setFriction(float f) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
        }
        this.mFlingRunnable.mScroller.setFriction(f);
    }

    public void setVelocityScale(float f) {
        this.mVelocityScale = f;
    }

    android.widget.AbsListView.AbsPositionScroller createPositionScroller() {
        return new android.widget.AbsListView.PositionScroller();
    }

    public void smoothScrollToPosition(int i) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.start(i);
    }

    public void smoothScrollToPositionFromTop(int i, int i2, int i3) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(i, i2, i3);
    }

    public void smoothScrollToPositionFromTop(int i, int i2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.startWithOffset(i, i2);
    }

    public void smoothScrollToPosition(int i, int i2) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = createPositionScroller();
        }
        this.mPositionScroller.start(i, i2);
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy(i, i2, false, false);
    }

    void smoothScrollBy(int i, int i2, boolean z, boolean z2) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new android.widget.AbsListView.FlingRunnable();
        }
        int i3 = this.mFirstPosition;
        int childCount = getChildCount();
        int i4 = i3 + childCount;
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (i == 0 || this.mItemCount == 0 || childCount == 0 || ((i3 == 0 && getChildAt(0).getTop() == paddingTop && i < 0) || (i4 == this.mItemCount && getChildAt(childCount - 1).getBottom() == height && i > 0))) {
            this.mFlingRunnable.endFling();
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
                return;
            }
            return;
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.startScroll(i, i2, z, z2);
    }

    void smoothScrollByOffset(int i) {
        int i2;
        android.view.View childAt;
        if (i < 0) {
            i2 = getFirstVisiblePosition();
        } else if (i <= 0) {
            i2 = -1;
        } else {
            i2 = getLastVisiblePosition();
        }
        if (i2 > -1 && (childAt = getChildAt(i2 - getFirstVisiblePosition())) != null) {
            if (childAt.getGlobalVisibleRect(new android.graphics.Rect())) {
                float width = (r2.width() * r2.height()) / (childAt.getWidth() * childAt.getHeight());
                if (i < 0 && width < 0.75f) {
                    i2++;
                } else if (i > 0 && width < 0.75f) {
                    i2--;
                }
            }
            smoothScrollToPosition(java.lang.Math.max(0, java.lang.Math.min(getCount(), i2 + i)));
        }
    }

    private void createScrollingCache() {
        if (this.mScrollingCacheEnabled && !this.mCachingStarted && !isHardwareAccelerated()) {
            setChildrenDrawnWithCacheEnabled(true);
            setChildrenDrawingCacheEnabled(true);
            this.mCachingActive = true;
            this.mCachingStarted = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearScrollingCache() {
        if (!isHardwareAccelerated()) {
            if (this.mClearScrollingCache == null) {
                this.mClearScrollingCache = new java.lang.Runnable() { // from class: android.widget.AbsListView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.widget.AbsListView.this.mCachingStarted) {
                            android.widget.AbsListView absListView = android.widget.AbsListView.this;
                            android.widget.AbsListView.this.mCachingActive = false;
                            absListView.mCachingStarted = false;
                            android.widget.AbsListView.this.setChildrenDrawnWithCacheEnabled(false);
                            if ((android.widget.AbsListView.this.mPersistentDrawingCache & 2) == 0) {
                                android.widget.AbsListView.this.setChildrenDrawingCacheEnabled(false);
                            }
                            if (!android.widget.AbsListView.this.isAlwaysDrawnWithCacheEnabled()) {
                                android.widget.AbsListView.this.invalidate();
                            }
                        }
                    }
                };
            }
            post(this.mClearScrollingCache);
        }
    }

    public void scrollListBy(int i) {
        int i2 = -i;
        trackMotionScroll(i2, i2);
    }

    public boolean canScrollList(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        int i2 = this.mFirstPosition;
        android.graphics.Rect rect = this.mListPadding;
        if (i > 0) {
            int bottom = getChildAt(childCount - 1).getBottom();
            if (i2 + childCount >= this.mItemCount && bottom <= getHeight() - rect.bottom) {
                return false;
            }
            return true;
        }
        int top = getChildAt(0).getTop();
        if (i2 <= 0 && top >= rect.top) {
            return false;
        }
        return true;
    }

    boolean trackMotionScroll(int i, int i2) {
        int i3;
        int i4;
        int min;
        int min2;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i5;
        int i6;
        boolean z6;
        int i7;
        int i8;
        int childCount = getChildCount();
        if (childCount != 0) {
            int top = getChildAt(0).getTop();
            int i9 = childCount - 1;
            int bottom = getChildAt(i9).getBottom();
            android.graphics.Rect rect = this.mListPadding;
            if ((this.mGroupFlags & 34) != 34) {
                i3 = 0;
                i4 = 0;
            } else {
                i3 = rect.top;
                i4 = rect.bottom;
            }
            int i10 = i3 - top;
            int height = bottom - (getHeight() - i4);
            int height2 = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
            if (i < 0) {
                min = java.lang.Math.max(-(height2 - 1), i);
            } else {
                min = java.lang.Math.min(height2 - 1, i);
            }
            if (i2 < 0) {
                min2 = java.lang.Math.max(-(height2 - 1), i2);
            } else {
                min2 = java.lang.Math.min(height2 - 1, i2);
            }
            int i11 = this.mFirstPosition;
            if (i11 == 0) {
                this.mFirstPositionDistanceGuess = top - rect.top;
            } else {
                this.mFirstPositionDistanceGuess += min2;
            }
            int i12 = i11 + childCount;
            if (i12 == this.mItemCount) {
                this.mLastPositionDistanceGuess = rect.bottom + bottom;
            } else {
                this.mLastPositionDistanceGuess += min2;
            }
            if (i11 == 0 && top >= rect.top && min2 >= 0) {
                z = true;
            } else {
                z = false;
            }
            if (i12 == this.mItemCount && bottom <= getHeight() - rect.bottom && min2 <= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z) {
                z3 = true;
                z4 = false;
            } else if (z2) {
                z3 = true;
                z4 = false;
            } else {
                if (min2 < 0) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                boolean isInTouchMode = isInTouchMode();
                if (isInTouchMode) {
                    hideSelector();
                }
                int headerViewsCount = getHeaderViewsCount();
                int footerViewsCount = this.mItemCount - getFooterViewsCount();
                if (z5) {
                    int i13 = -min2;
                    if ((this.mGroupFlags & 34) == 34) {
                        i13 += rect.top;
                    }
                    int i14 = 0;
                    i6 = 0;
                    while (i14 < childCount) {
                        android.view.View childAt = getChildAt(i14);
                        if (childAt.getBottom() >= i13) {
                            break;
                        }
                        i6++;
                        int i15 = i11 + i14;
                        if (i15 < headerViewsCount || i15 >= footerViewsCount) {
                            i8 = childCount;
                        } else {
                            childAt.clearAccessibilityFocus();
                            i8 = childCount;
                            this.mRecycler.addScrapView(childAt, i15);
                        }
                        i14++;
                        childCount = i8;
                    }
                    i5 = 0;
                } else {
                    int height3 = getHeight() - min2;
                    if ((this.mGroupFlags & 34) == 34) {
                        height3 -= rect.bottom;
                    }
                    i5 = 0;
                    i6 = 0;
                    while (i9 >= 0) {
                        android.view.View childAt2 = getChildAt(i9);
                        if (childAt2.getTop() <= height3) {
                            break;
                        }
                        i6++;
                        int i16 = i11 + i9;
                        if (i16 >= headerViewsCount && i16 < footerViewsCount) {
                            childAt2.clearAccessibilityFocus();
                            this.mRecycler.addScrapView(childAt2, i16);
                        }
                        int i17 = i9;
                        i9--;
                        i5 = i17;
                    }
                }
                this.mMotionViewNewTop = this.mMotionViewOriginalTop + min;
                this.mBlockLayoutRequests = true;
                if (i6 > 0) {
                    detachViewsFromParent(i5, i6);
                    this.mRecycler.removeSkippedScrap();
                }
                if (!awakenScrollBars()) {
                    invalidate();
                }
                offsetChildrenTopAndBottom(min2);
                if (z5) {
                    this.mFirstPosition += i6;
                }
                int abs = java.lang.Math.abs(min2);
                if (i10 < abs || height < abs) {
                    fillGap(z5);
                }
                this.mRecycler.fullyDetachScrapViews();
                if (!isInTouchMode && this.mSelectedPosition != -1) {
                    int i18 = this.mSelectedPosition - this.mFirstPosition;
                    if (i18 >= 0 && i18 < getChildCount()) {
                        positionSelector(this.mSelectedPosition, getChildAt(i18));
                        z6 = true;
                    } else {
                        z6 = false;
                    }
                } else if (this.mSelectorPosition != -1 && (i7 = this.mSelectorPosition - this.mFirstPosition) >= 0 && i7 < getChildCount()) {
                    positionSelector(this.mSelectorPosition, getChildAt(i7));
                    z6 = true;
                } else {
                    z6 = false;
                }
                if (!z6) {
                    this.mSelectorRect.setEmpty();
                }
                this.mBlockLayoutRequests = false;
                invokeOnItemScrollListener();
                return false;
            }
            return min2 != 0 ? z3 : z4;
        }
        return true;
    }

    int getHeaderViewsCount() {
        return 0;
    }

    int getFooterViewsCount() {
        return 0;
    }

    void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectedTop = 0;
        }
    }

    int reconcileSelectedPosition() {
        int i = this.mSelectedPosition;
        if (i < 0) {
            i = this.mResurrectToPosition;
        }
        return java.lang.Math.min(java.lang.Math.max(0, i), this.mItemCount - 1);
    }

    int findClosestMotionRow(int i) {
        if (getChildCount() == 0) {
            return -1;
        }
        int findMotionRow = findMotionRow(i);
        return findMotionRow != -1 ? findMotionRow : (this.mFirstPosition + r0) - 1;
    }

    public void invalidateViews() {
        this.mDataChanged = true;
        rememberSyncState();
        requestLayout();
        invalidate();
    }

    boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition < 0 && resurrectSelection()) {
            updateSelectorState();
            return true;
        }
        return false;
    }

    boolean resurrectSelection() {
        boolean z;
        int i;
        int childCount = getChildCount();
        if (childCount <= 0) {
            return false;
        }
        int i2 = this.mListPadding.top;
        int i3 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        int i4 = this.mFirstPosition;
        int i5 = this.mResurrectToPosition;
        if (i5 >= i4 && i5 < i4 + childCount) {
            android.view.View childAt = getChildAt(i5 - this.mFirstPosition);
            i = childAt.getTop();
            int bottom = childAt.getBottom();
            if (i < i2) {
                i = i2 + getVerticalFadingEdgeLength();
            } else if (bottom > i3) {
                i = (i3 - childAt.getMeasuredHeight()) - getVerticalFadingEdgeLength();
            }
            z = true;
        } else if (i5 < i4) {
            int i6 = 0;
            int i7 = 0;
            while (true) {
                if (i6 >= childCount) {
                    i = i7;
                    i5 = i4;
                    break;
                }
                i = getChildAt(i6).getTop();
                if (i6 == 0) {
                    if (i4 > 0 || i < i2) {
                        i2 += getVerticalFadingEdgeLength();
                        i7 = i;
                    } else {
                        i7 = i;
                    }
                }
                if (i < i2) {
                    i6++;
                } else {
                    i5 = i6 + i4;
                    break;
                }
            }
            z = true;
        } else {
            int i8 = this.mItemCount;
            int i9 = i4 + childCount;
            int i10 = i9 - 1;
            int i11 = childCount - 1;
            int i12 = i11;
            int i13 = 0;
            while (true) {
                if (i12 < 0) {
                    z = false;
                    i5 = i10;
                    i = i13;
                    break;
                }
                android.view.View childAt2 = getChildAt(i12);
                int top = childAt2.getTop();
                int bottom2 = childAt2.getBottom();
                if (i12 == i11) {
                    if (i9 < i8 || bottom2 > i3) {
                        i3 -= getVerticalFadingEdgeLength();
                        i13 = top;
                    } else {
                        i13 = top;
                    }
                }
                if (bottom2 > i3) {
                    i12--;
                } else {
                    i5 = i4 + i12;
                    z = false;
                    i = top;
                    break;
                }
            }
        }
        int i14 = -1;
        this.mResurrectToPosition = -1;
        removeCallbacks(this.mFlingRunnable);
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        this.mTouchMode = -1;
        clearScrollingCache();
        this.mSpecificTop = i;
        int lookForSelectablePosition = lookForSelectablePosition(i5, z);
        if (lookForSelectablePosition >= i4 && lookForSelectablePosition <= getLastVisiblePosition()) {
            this.mLayoutMode = 4;
            updateSelectorState();
            setSelectionInt(lookForSelectablePosition);
            invokeOnItemScrollListener();
            i14 = lookForSelectablePosition;
        }
        reportScrollStateChange(0);
        if (i14 < 0) {
            return false;
        }
        return true;
    }

    void confirmCheckedPositionsById() {
        boolean z;
        this.mCheckStates.clear();
        int i = 0;
        boolean z2 = false;
        while (i < this.mCheckedIdStates.size()) {
            long keyAt = this.mCheckedIdStates.keyAt(i);
            int intValue = this.mCheckedIdStates.valueAt(i).intValue();
            if (keyAt != this.mAdapter.getItemId(intValue)) {
                int max = java.lang.Math.max(0, intValue - 20);
                int min = java.lang.Math.min(intValue + 20, this.mItemCount);
                while (true) {
                    if (max >= min) {
                        z = false;
                        break;
                    } else if (keyAt != this.mAdapter.getItemId(max)) {
                        max++;
                    } else {
                        this.mCheckStates.put(max, true);
                        this.mCheckedIdStates.setValueAt(i, java.lang.Integer.valueOf(max));
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.mCheckedIdStates.delete(keyAt);
                    i--;
                    this.mCheckedItemCount--;
                    if (this.mChoiceActionMode != null && this.mMultiChoiceModeCallback != null) {
                        this.mMultiChoiceModeCallback.onItemCheckedStateChanged(this.mChoiceActionMode, intValue, keyAt, false);
                    }
                    z2 = true;
                }
            } else {
                this.mCheckStates.put(intValue, true);
            }
            i++;
        }
        if (z2 && this.mChoiceActionMode != null) {
            this.mChoiceActionMode.invalidate();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.AdapterView
    protected void handleDataChanged() {
        int i = this.mItemCount;
        int i2 = this.mLastHandledItemCount;
        this.mLastHandledItemCount = this.mItemCount;
        if (this.mChoiceMode != 0 && this.mAdapter != null && this.mAdapter.hasStableIds()) {
            confirmCheckedPositionsById();
        }
        this.mRecycler.clearTransientStateViews();
        if (i > 0) {
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                if (this.mTranscriptMode == 2) {
                    this.mLayoutMode = 3;
                    return;
                }
                if (this.mTranscriptMode == 1) {
                    if (this.mForceTranscriptScroll) {
                        this.mForceTranscriptScroll = false;
                        this.mLayoutMode = 3;
                        return;
                    }
                    int childCount = getChildCount();
                    int height = getHeight() - getPaddingBottom();
                    android.view.View childAt = getChildAt(childCount - 1);
                    int bottom = childAt != null ? childAt.getBottom() : height;
                    if (this.mFirstPosition + childCount >= i2 && bottom <= height) {
                        this.mLayoutMode = 3;
                        return;
                    }
                    awakenScrollBars();
                }
                switch (this.mSyncMode) {
                    case 0:
                        if (isInTouchMode()) {
                            this.mLayoutMode = 5;
                            this.mSyncPosition = java.lang.Math.min(java.lang.Math.max(0, this.mSyncPosition), i - 1);
                            break;
                        } else {
                            int findSyncPosition = findSyncPosition();
                            if (findSyncPosition >= 0 && lookForSelectablePosition(findSyncPosition, true) == findSyncPosition) {
                                this.mSyncPosition = findSyncPosition;
                                if (this.mSyncHeight == getHeight()) {
                                    this.mLayoutMode = 5;
                                } else {
                                    this.mLayoutMode = 2;
                                }
                                setNextSelectedPositionInt(findSyncPosition);
                                break;
                            }
                        }
                        break;
                    case 1:
                        this.mLayoutMode = 5;
                        this.mSyncPosition = java.lang.Math.min(java.lang.Math.max(0, this.mSyncPosition), i - 1);
                        break;
                }
                return;
            }
            if (!isInTouchMode()) {
                int selectedItemPosition = getSelectedItemPosition();
                if (selectedItemPosition >= i) {
                    selectedItemPosition = i - 1;
                }
                if (selectedItemPosition < 0) {
                    selectedItemPosition = 0;
                }
                int lookForSelectablePosition = lookForSelectablePosition(selectedItemPosition, true);
                if (lookForSelectablePosition >= 0) {
                    setNextSelectedPositionInt(lookForSelectablePosition);
                    return;
                }
                int lookForSelectablePosition2 = lookForSelectablePosition(selectedItemPosition, false);
                if (lookForSelectablePosition2 >= 0) {
                    setNextSelectedPositionInt(lookForSelectablePosition2);
                    return;
                }
            } else if (this.mResurrectToPosition >= 0) {
                return;
            }
        }
        this.mLayoutMode = this.mStackFromBottom ? 3 : 1;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = -1;
        checkSelectionChanged();
    }

    @Override // android.view.View
    protected void onDisplayHint(int i) {
        super.onDisplayHint(i);
        switch (i) {
            case 0:
                if (this.mFiltered && this.mPopup != null && !this.mPopup.isShowing()) {
                    showPopup();
                    break;
                }
                break;
            case 4:
                if (this.mPopup != null && this.mPopup.isShowing()) {
                    dismissPopup();
                    break;
                }
                break;
        }
        this.mPopupHidden = i == 4;
    }

    private void dismissPopup() {
        if (this.mPopup != null) {
            this.mPopup.dismiss();
        }
    }

    private void showPopup() {
        if (getWindowVisibility() == 0) {
            createTextFilter(true);
            positionPopup();
            checkFocus();
        }
    }

    private void positionPopup() {
        int i = getResources().getDisplayMetrics().heightPixels;
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int height = ((i - iArr[1]) - getHeight()) + ((int) (this.mDensityScale * 20.0f));
        if (!this.mPopup.isShowing()) {
            this.mPopup.showAtLocation(this, 81, iArr[0], height);
        } else {
            this.mPopup.update(iArr[0], height, -1, -1);
        }
    }

    static int getDistance(android.graphics.Rect rect, android.graphics.Rect rect2, int i) {
        int width;
        int height;
        int width2;
        int height2;
        switch (i) {
            case 1:
            case 2:
                width = rect.right + (rect.width() / 2);
                height = (rect.height() / 2) + rect.top;
                width2 = rect2.left + (rect2.width() / 2);
                height2 = (rect2.height() / 2) + rect2.top;
                break;
            case 17:
                width = rect.left;
                height = (rect.height() / 2) + rect.top;
                width2 = rect2.right;
                height2 = (rect2.height() / 2) + rect2.top;
                break;
            case 33:
                width = rect.left + (rect.width() / 2);
                height = rect.top;
                width2 = rect2.left + (rect2.width() / 2);
                height2 = rect2.bottom;
                break;
            case 66:
                width = rect.right;
                height = (rect.height() / 2) + rect.top;
                width2 = rect2.left;
                height2 = (rect2.height() / 2) + rect2.top;
                break;
            case 130:
                width = rect.left + (rect.width() / 2);
                height = rect.bottom;
                width2 = rect2.left + (rect2.width() / 2);
                height2 = rect2.top;
                break;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
        int i2 = width2 - width;
        int i3 = height2 - height;
        return (i3 * i3) + (i2 * i2);
    }

    @Override // android.widget.AdapterView
    protected boolean isInFilterMode() {
        return this.mFiltered;
    }

    boolean sendToTextFilter(int i, int i2, android.view.KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        android.view.KeyEvent keyEvent2;
        if (!acceptFilter()) {
            return false;
        }
        switch (i) {
            case 4:
            case 111:
                if (this.mFiltered && this.mPopup != null && this.mPopup.isShowing()) {
                    if (keyEvent.getAction() != 0 || keyEvent.getRepeatCount() != 0) {
                        if (keyEvent.getAction() == 1 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                            this.mTextFilter.lambda$setTextAsync$0("");
                            z = true;
                        }
                    } else {
                        android.view.KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                        if (keyDispatcherState != null) {
                            keyDispatcherState.startTracking(keyEvent, this);
                        }
                        z = true;
                    }
                    z2 = z;
                    z3 = false;
                    break;
                }
                z = false;
                z2 = z;
                z3 = false;
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 66:
            case 160:
                z3 = false;
                z2 = false;
                break;
            case 62:
                z3 = this.mFiltered;
                z2 = false;
                break;
            default:
                z3 = true;
                z2 = false;
                break;
        }
        if (z3) {
            createTextFilter(true);
            if (keyEvent.getRepeatCount() <= 0) {
                keyEvent2 = keyEvent;
            } else {
                keyEvent2 = android.view.KeyEvent.changeTimeRepeat(keyEvent, keyEvent.getEventTime(), 0);
            }
            switch (keyEvent.getAction()) {
                case 0:
                    return this.mTextFilter.onKeyDown(i, keyEvent2);
                case 1:
                    return this.mTextFilter.onKeyUp(i, keyEvent2);
                case 2:
                    return this.mTextFilter.onKeyMultiple(i, i2, keyEvent);
                default:
                    return z2;
            }
        }
        return z2;
    }

    @Override // android.view.View
    public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo) {
        if (isTextFilterEnabled()) {
            if (this.mPublicInputConnection == null) {
                this.mDefInputConnection = new android.view.inputmethod.BaseInputConnection((android.view.View) this, false);
                this.mPublicInputConnection = new android.widget.AbsListView.InputConnectionWrapper(editorInfo);
            }
            editorInfo.inputType = 177;
            editorInfo.imeOptions = 6;
            return this.mPublicInputConnection;
        }
        return null;
    }

    private class InputConnectionWrapper implements android.view.inputmethod.InputConnection {
        private final android.view.inputmethod.EditorInfo mOutAttrs;
        private android.view.inputmethod.InputConnection mTarget;

        public InputConnectionWrapper(android.view.inputmethod.EditorInfo editorInfo) {
            this.mOutAttrs = editorInfo;
        }

        private android.view.inputmethod.InputConnection getTarget() {
            if (this.mTarget == null) {
                this.mTarget = android.widget.AbsListView.this.getTextFilterInput().onCreateInputConnection(this.mOutAttrs);
            }
            return this.mTarget;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean reportFullscreenMode(boolean z) {
            return android.widget.AbsListView.this.mDefInputConnection.reportFullscreenMode(z);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performEditorAction(int i) {
            if (i != 6) {
                return false;
            }
            android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) android.widget.AbsListView.this.getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(android.widget.AbsListView.this.getWindowToken(), 0);
                return true;
            }
            return true;
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean sendKeyEvent(android.view.KeyEvent keyEvent) {
            return android.widget.AbsListView.this.mDefInputConnection.sendKeyEvent(keyEvent);
        }

        @Override // android.view.inputmethod.InputConnection
        public java.lang.CharSequence getTextBeforeCursor(int i, int i2) {
            return this.mTarget == null ? "" : this.mTarget.getTextBeforeCursor(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public java.lang.CharSequence getTextAfterCursor(int i, int i2) {
            return this.mTarget == null ? "" : this.mTarget.getTextAfterCursor(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public java.lang.CharSequence getSelectedText(int i) {
            return this.mTarget == null ? "" : this.mTarget.getSelectedText(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
            if (this.mTarget == null) {
                return null;
            }
            return this.mTarget.getSurroundingText(i, i2, i3);
        }

        @Override // android.view.inputmethod.InputConnection
        public int getCursorCapsMode(int i) {
            if (this.mTarget == null) {
                return 16384;
            }
            return this.mTarget.getCursorCapsMode(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public android.view.inputmethod.ExtractedText getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
            return getTarget().getExtractedText(extractedTextRequest, i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean deleteSurroundingText(int i, int i2) {
            return getTarget().deleteSurroundingText(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
            return getTarget().deleteSurroundingTextInCodePoints(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setComposingText(java.lang.CharSequence charSequence, int i) {
            return getTarget().setComposingText(charSequence, i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setComposingRegion(int i, int i2) {
            return getTarget().setComposingRegion(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean finishComposingText() {
            return this.mTarget == null || this.mTarget.finishComposingText();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitText(java.lang.CharSequence charSequence, int i) {
            return getTarget().commitText(charSequence, i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
            return getTarget().commitCompletion(completionInfo);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
            return getTarget().commitCorrection(correctionInfo);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean setSelection(int i, int i2) {
            return getTarget().setSelection(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performContextMenuAction(int i) {
            return getTarget().performContextMenuAction(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean beginBatchEdit() {
            return getTarget().beginBatchEdit();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean endBatchEdit() {
            return getTarget().endBatchEdit();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean clearMetaKeyStates(int i) {
            return getTarget().clearMetaKeyStates(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
            return getTarget().performPrivateCommand(str, bundle);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean requestCursorUpdates(int i) {
            return getTarget().requestCursorUpdates(i);
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean requestCursorUpdates(int i, int i2) {
            return getTarget().requestCursorUpdates(i, i2);
        }

        @Override // android.view.inputmethod.InputConnection
        public android.os.Handler getHandler() {
            return getTarget().getHandler();
        }

        @Override // android.view.inputmethod.InputConnection
        public void closeConnection() {
            getTarget().closeConnection();
        }

        @Override // android.view.inputmethod.InputConnection
        public boolean commitContent(android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle) {
            return getTarget().commitContent(inputContentInfo, i, bundle);
        }
    }

    @Override // android.view.View
    public boolean checkInputConnectionProxy(android.view.View view) {
        return view == this.mTextFilter;
    }

    private void createTextFilter(boolean z) {
        if (this.mPopup == null) {
            android.widget.PopupWindow popupWindow = new android.widget.PopupWindow(getContext());
            popupWindow.setFocusable(false);
            popupWindow.setTouchable(false);
            popupWindow.setInputMethodMode(2);
            popupWindow.setContentView(getTextFilterInput());
            popupWindow.setWidth(-2);
            popupWindow.setHeight(-2);
            popupWindow.setBackgroundDrawable(null);
            this.mPopup = popupWindow;
            getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.mGlobalLayoutListenerAddedFilter = true;
        }
        if (z) {
            this.mPopup.setAnimationStyle(com.android.internal.R.style.Animation_TypingFilter);
        } else {
            this.mPopup.setAnimationStyle(com.android.internal.R.style.Animation_TypingFilterRestore);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.EditText getTextFilterInput() {
        if (this.mTextFilter == null) {
            this.mTextFilter = (android.widget.EditText) android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.typing_filter, (android.view.ViewGroup) null);
            this.mTextFilter.setRawInputType(177);
            this.mTextFilter.setImeOptions(268435456);
            this.mTextFilter.addTextChangedListener(this);
        }
        return this.mTextFilter;
    }

    public void clearTextFilter() {
        if (this.mFiltered) {
            getTextFilterInput().lambda$setTextAsync$0("");
            this.mFiltered = false;
            if (this.mPopup != null && this.mPopup.isShowing()) {
                dismissPopup();
            }
        }
    }

    public boolean hasTextFilter() {
        return this.mFiltered;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (isShown()) {
            if (this.mFiltered && this.mPopup != null && !this.mPopup.isShowing() && !this.mPopupHidden) {
                showPopup();
                return;
            }
            return;
        }
        if (this.mPopup != null && this.mPopup.isShowing()) {
            dismissPopup();
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (isTextFilterEnabled()) {
            createTextFilter(true);
            int length = charSequence.length();
            boolean isShowing = this.mPopup.isShowing();
            if (!isShowing && length > 0) {
                showPopup();
                this.mFiltered = true;
            } else if (isShowing && length == 0) {
                dismissPopup();
                this.mFiltered = false;
            }
            if (this.mAdapter instanceof android.widget.Filterable) {
                android.widget.Filter filter = ((android.widget.Filterable) this.mAdapter).getFilter();
                if (filter != null) {
                    filter.filter(charSequence, this);
                    return;
                }
                throw new java.lang.IllegalStateException("You cannot call onTextChanged with a non filterable adapter");
            }
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(android.text.Editable editable) {
    }

    @Override // android.widget.Filter.FilterListener
    public void onFilterComplete(int i) {
        if (this.mSelectedPosition < 0 && i > 0) {
            this.mResurrectToPosition = -1;
            resurrectSelection();
        }
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.AbsListView.LayoutParams(-1, -2, 0);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new android.widget.AbsListView.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public android.widget.AbsListView.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.AbsListView.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.AbsListView.LayoutParams;
    }

    public void setTranscriptMode(int i) {
        this.mTranscriptMode = i;
    }

    public int getTranscriptMode() {
        return this.mTranscriptMode;
    }

    @Override // android.view.View
    public int getSolidColor() {
        return this.mCacheColorHint;
    }

    public void setCacheColorHint(int i) {
        if (i != this.mCacheColorHint) {
            this.mCacheColorHint = i;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setDrawingCacheBackgroundColor(i);
            }
            this.mRecycler.setCacheColorHint(i);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public int getCacheColorHint() {
        return this.mCacheColorHint;
    }

    public void reclaimViews(java.util.List<android.view.View> list) {
        int childCount = getChildCount();
        android.widget.AbsListView.RecyclerListener recyclerListener = this.mRecycler.mRecyclerListener;
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) childAt.getLayoutParams();
            if (layoutParams != null && this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) {
                list.add(childAt);
                childAt.setAccessibilityDelegate(null);
                childAt.resetSubtreeAutofillIds();
                if (recyclerListener != null) {
                    recyclerListener.onMovedToScrapHeap(childAt);
                }
            }
        }
        this.mRecycler.reclaimScrapViews(list);
        removeAllViewsInLayout();
    }

    private void finishGlows() {
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.finish();
            this.mEdgeGlowBottom.finish();
        }
    }

    public void setRemoteViewsAdapter(android.content.Intent intent) {
        setRemoteViewsAdapter(intent, false);
    }

    public java.lang.Runnable setRemoteViewsAdapterAsync(android.content.Intent intent) {
        return new android.widget.RemoteViewsAdapter.AsyncRemoteAdapterAction(this, intent);
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void setRemoteViewsAdapter(android.content.Intent intent, boolean z) {
        if (this.mRemoteAdapter != null && new android.content.Intent.FilterComparison(intent).equals(new android.content.Intent.FilterComparison(this.mRemoteAdapter.getRemoteViewsServiceIntent()))) {
            return;
        }
        this.mDeferNotifyDataSetChanged = false;
        this.mRemoteAdapter = new android.widget.RemoteViewsAdapter(getContext(), intent, this, z);
        if (this.mRemoteAdapter.isDataReady()) {
            setAdapter((android.widget.ListAdapter) this.mRemoteAdapter);
        }
    }

    public void setRemoteViewsInteractionHandler(android.widget.RemoteViews.InteractionHandler interactionHandler) {
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.setRemoteViewsInteractionHandler(interactionHandler);
        }
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void deferNotifyDataSetChanged() {
        this.mDeferNotifyDataSetChanged = true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public boolean onRemoteAdapterConnected() {
        if (this.mRemoteAdapter != this.mAdapter) {
            setAdapter((android.widget.ListAdapter) this.mRemoteAdapter);
            if (this.mDeferNotifyDataSetChanged) {
                this.mRemoteAdapter.notifyDataSetChanged();
                this.mDeferNotifyDataSetChanged = false;
            }
            return false;
        }
        if (this.mRemoteAdapter == null) {
            return false;
        }
        this.mRemoteAdapter.superNotifyDataSetChanged();
        return true;
    }

    @Override // android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback
    public void onRemoteAdapterDisconnected() {
    }

    void setVisibleRangeHint(int i, int i2) {
        if (this.mRemoteAdapter != null) {
            this.mRemoteAdapter.setVisibleRangeHint(i, i2);
        }
    }

    public void setEdgeEffectColor(int i) {
        setTopEdgeEffectColor(i);
        setBottomEdgeEffectColor(i);
    }

    public void setBottomEdgeEffectColor(int i) {
        this.mEdgeGlowBottom.setColor(i);
        invalidateEdgeEffects();
    }

    public void setTopEdgeEffectColor(int i) {
        this.mEdgeGlowTop.setColor(i);
        invalidateEdgeEffects();
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    public void setRecyclerListener(android.widget.AbsListView.RecyclerListener recyclerListener) {
        this.mRecycler.mRecyclerListener = recyclerListener;
    }

    @Override // android.view.View
    public void onProvideContentCaptureStructure(android.view.ViewStructure viewStructure, int i) {
        super.onProvideContentCaptureStructure(viewStructure, i);
        if (!sContentCaptureReportingEnabledByDeviceConfig) {
            return;
        }
        android.os.Bundle extras = viewStructure.getExtras();
        if (extras == null) {
            android.util.Log.wtf(TAG, "Unexpected null extras Bundle in ViewStructure");
            return;
        }
        int childCount = getChildCount();
        java.util.ArrayList<? extends android.os.Parcelable> arrayList = new java.util.ArrayList<>(childCount);
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt != null) {
                arrayList.add(childAt.getAutofillId());
            }
        }
        extras.putParcelableArrayList(android.view.ViewStructure.EXTRA_ACTIVE_CHILDREN_IDS, arrayList);
        extras.putInt(android.view.ViewStructure.EXTRA_FIRST_ACTIVE_POSITION, getFirstVisiblePosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportActiveViewsToContentCapture() {
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        if (sContentCaptureReportingEnabledByDeviceConfig && (contentCaptureSession = getContentCaptureSession()) != null) {
            android.view.ViewStructure newViewStructure = contentCaptureSession.newViewStructure(this);
            onProvideContentCaptureStructure(newViewStructure, 0);
            contentCaptureSession.notifyViewAppeared(newViewStructure);
        }
    }

    class AdapterDataSetObserver extends android.widget.AdapterView<android.widget.ListAdapter>.AdapterDataSetObserver {
        AdapterDataSetObserver() {
            super();
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver, android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            android.widget.AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = true;
            if (android.widget.AbsListView.this.mFastScroll != null) {
                android.widget.AbsListView.this.mFastScroll.onSectionsChanged();
            }
        }

        @Override // android.widget.AdapterView.AdapterDataSetObserver, android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            android.widget.AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = true;
            if (android.widget.AbsListView.this.mFastScroll != null) {
                android.widget.AbsListView.this.mFastScroll.onSectionsChanged();
            }
        }
    }

    class MultiChoiceModeWrapper implements android.widget.AbsListView.MultiChoiceModeListener {
        private android.widget.AbsListView.MultiChoiceModeListener mWrapped;

        MultiChoiceModeWrapper() {
        }

        public void setWrapped(android.widget.AbsListView.MultiChoiceModeListener multiChoiceModeListener) {
            this.mWrapped = multiChoiceModeListener;
        }

        public boolean hasWrappedCallback() {
            return this.mWrapped != null;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            if (!this.mWrapped.onCreateActionMode(actionMode, menu)) {
                return false;
            }
            android.widget.AbsListView.this.setLongClickable(false);
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(android.view.ActionMode actionMode, android.view.MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(android.view.ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            android.widget.AbsListView.this.mChoiceActionMode = null;
            android.widget.AbsListView.this.clearChoices();
            android.widget.AbsListView.this.mDataChanged = true;
            android.widget.AbsListView.this.rememberSyncState();
            android.widget.AbsListView.this.requestLayout();
            android.widget.AbsListView.this.setLongClickable(true);
        }

        @Override // android.widget.AbsListView.MultiChoiceModeListener
        public void onItemCheckedStateChanged(android.view.ActionMode actionMode, int i, long j, boolean z) {
            this.mWrapped.onItemCheckedStateChanged(actionMode, i, j, z);
            if (android.widget.AbsListView.this.getCheckedItemCount() == 0) {
                actionMode.finish();
            }
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {

        @android.view.ViewDebug.ExportedProperty(category = android.app.slice.Slice.HINT_LIST)
        boolean forceAdd;
        boolean isEnabled;
        long itemId;

        @android.view.ViewDebug.ExportedProperty(category = android.app.slice.Slice.HINT_LIST)
        boolean recycledHeaderFooter;
        int scrappedFromPosition;

        @android.view.ViewDebug.ExportedProperty(category = android.app.slice.Slice.HINT_LIST, mapping = {@android.view.ViewDebug.IntToString(from = -1, to = "ITEM_VIEW_TYPE_IGNORE"), @android.view.ViewDebug.IntToString(from = -2, to = "ITEM_VIEW_TYPE_HEADER_OR_FOOTER")})
        int viewType;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.itemId = -1L;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.itemId = -1L;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.itemId = -1L;
            this.viewType = i3;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.itemId = -1L;
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("list:viewType", this.viewType);
            viewHierarchyEncoder.addProperty("list:recycledHeaderFooter", this.recycledHeaderFooter);
            viewHierarchyEncoder.addProperty("list:forceAdd", this.forceAdd);
            viewHierarchyEncoder.addProperty("list:isEnabled", this.isEnabled);
        }
    }

    class RecycleBin {
        private android.view.View[] mActiveViews = new android.view.View[0];
        private java.util.ArrayList<android.view.View> mCurrentScrap;
        private int mFirstActivePosition;
        private android.widget.AbsListView.RecyclerListener mRecyclerListener;
        private java.util.ArrayList<android.view.View>[] mScrapViews;
        private java.util.ArrayList<android.view.View> mSkippedScrap;
        private android.util.SparseArray<android.view.View> mTransientStateViews;
        private android.util.LongSparseArray<android.view.View> mTransientStateViewsById;
        private int mViewTypeCount;

        RecycleBin() {
        }

        public void setViewTypeCount(int i) {
            if (i < 1) {
                throw new java.lang.IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            java.util.ArrayList<android.view.View>[] arrayListArr = new java.util.ArrayList[i];
            for (int i2 = 0; i2 < i; i2++) {
                arrayListArr[i2] = new java.util.ArrayList<>();
            }
            this.mViewTypeCount = i;
            this.mCurrentScrap = arrayListArr[0];
            this.mScrapViews = arrayListArr;
        }

        public void markChildrenDirty() {
            if (this.mViewTypeCount == 1) {
                java.util.ArrayList<android.view.View> arrayList = this.mCurrentScrap;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    arrayList.get(i).forceLayout();
                }
            } else {
                int i2 = this.mViewTypeCount;
                for (int i3 = 0; i3 < i2; i3++) {
                    java.util.ArrayList<android.view.View> arrayList2 = this.mScrapViews[i3];
                    int size2 = arrayList2.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        arrayList2.get(i4).forceLayout();
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                int size3 = this.mTransientStateViews.size();
                for (int i5 = 0; i5 < size3; i5++) {
                    this.mTransientStateViews.valueAt(i5).forceLayout();
                }
            }
            if (this.mTransientStateViewsById != null) {
                int size4 = this.mTransientStateViewsById.size();
                for (int i6 = 0; i6 < size4; i6++) {
                    this.mTransientStateViewsById.valueAt(i6).forceLayout();
                }
            }
        }

        public boolean shouldRecycleViewType(int i) {
            return i >= 0;
        }

        void clear() {
            if (this.mViewTypeCount == 1) {
                clearScrap(this.mCurrentScrap);
            } else {
                int i = this.mViewTypeCount;
                for (int i2 = 0; i2 < i; i2++) {
                    clearScrap(this.mScrapViews[i2]);
                }
            }
            clearTransientStateViews();
        }

        void fillActiveViews(int i, int i2) {
            if (this.mActiveViews.length < i) {
                this.mActiveViews = new android.view.View[i];
            }
            this.mFirstActivePosition = i2;
            android.view.View[] viewArr = this.mActiveViews;
            for (int i3 = 0; i3 < i; i3++) {
                android.view.View childAt = android.widget.AbsListView.this.getChildAt(i3);
                android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) childAt.getLayoutParams();
                if (layoutParams != null && layoutParams.viewType != -2) {
                    viewArr[i3] = childAt;
                    layoutParams.scrappedFromPosition = i2 + i3;
                }
            }
            if (android.widget.AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate && i > 0) {
                android.widget.AbsListView.this.reportActiveViewsToContentCapture();
                android.widget.AbsListView.this.mReportChildrenToContentCaptureOnNextUpdate = false;
            }
        }

        android.view.View getActiveView(int i) {
            int i2 = i - this.mFirstActivePosition;
            android.view.View[] viewArr = this.mActiveViews;
            if (i2 < 0 || i2 >= viewArr.length) {
                return null;
            }
            android.view.View view = viewArr[i2];
            viewArr[i2] = null;
            return view;
        }

        android.view.View getTransientStateView(int i) {
            int indexOfKey;
            if (android.widget.AbsListView.this.mAdapter != null && android.widget.AbsListView.this.mAdapterHasStableIds && this.mTransientStateViewsById != null) {
                long itemId = android.widget.AbsListView.this.mAdapter.getItemId(i);
                android.view.View view = this.mTransientStateViewsById.get(itemId);
                this.mTransientStateViewsById.remove(itemId);
                return view;
            }
            if (this.mTransientStateViews != null && (indexOfKey = this.mTransientStateViews.indexOfKey(i)) >= 0) {
                android.view.View valueAt = this.mTransientStateViews.valueAt(indexOfKey);
                this.mTransientStateViews.removeAt(indexOfKey);
                return valueAt;
            }
            return null;
        }

        void clearTransientStateViews() {
            android.util.SparseArray<android.view.View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    removeDetachedView(sparseArray.valueAt(i), false);
                }
                sparseArray.clear();
            }
            android.util.LongSparseArray<android.view.View> longSparseArray = this.mTransientStateViewsById;
            if (longSparseArray != null) {
                int size2 = longSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    removeDetachedView(longSparseArray.valueAt(i2), false);
                }
                longSparseArray.clear();
            }
        }

        android.view.View getScrapView(int i) {
            int itemViewType = android.widget.AbsListView.this.mAdapter.getItemViewType(i);
            if (itemViewType < 0) {
                return null;
            }
            if (this.mViewTypeCount == 1) {
                return retrieveFromScrap(this.mCurrentScrap, i);
            }
            if (itemViewType >= this.mScrapViews.length) {
                return null;
            }
            return retrieveFromScrap(this.mScrapViews[itemViewType], i);
        }

        void addScrapView(android.view.View view, int i) {
            android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.scrappedFromPosition = i;
            int i2 = layoutParams.viewType;
            if (!shouldRecycleViewType(i2)) {
                if (i2 != -2) {
                    getSkippedScrap().add(view);
                    return;
                }
                return;
            }
            view.dispatchStartTemporaryDetach();
            android.widget.AbsListView.this.notifyViewAccessibilityStateChangedIfNeeded(1);
            if (view.hasTransientState()) {
                if (android.widget.AbsListView.this.mAdapter != null && android.widget.AbsListView.this.mAdapterHasStableIds) {
                    if (this.mTransientStateViewsById == null) {
                        this.mTransientStateViewsById = new android.util.LongSparseArray<>();
                    }
                    this.mTransientStateViewsById.put(layoutParams.itemId, view);
                    return;
                } else if (!android.widget.AbsListView.this.mDataChanged) {
                    if (this.mTransientStateViews == null) {
                        this.mTransientStateViews = new android.util.SparseArray<>();
                    }
                    this.mTransientStateViews.put(i, view);
                    return;
                } else {
                    clearScrapForRebind(view);
                    getSkippedScrap().add(view);
                    return;
                }
            }
            clearScrapForRebind(view);
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(view);
            } else {
                this.mScrapViews[i2].add(view);
            }
            if (this.mRecyclerListener != null) {
                this.mRecyclerListener.onMovedToScrapHeap(view);
            }
        }

        private java.util.ArrayList<android.view.View> getSkippedScrap() {
            if (this.mSkippedScrap == null) {
                this.mSkippedScrap = new java.util.ArrayList<>();
            }
            return this.mSkippedScrap;
        }

        void removeSkippedScrap() {
            if (this.mSkippedScrap == null) {
                return;
            }
            int size = this.mSkippedScrap.size();
            for (int i = 0; i < size; i++) {
                removeDetachedView(this.mSkippedScrap.get(i), false);
            }
            this.mSkippedScrap.clear();
        }

        void scrapActiveViews() {
            android.view.View[] viewArr = this.mActiveViews;
            boolean z = this.mRecyclerListener != null;
            boolean z2 = this.mViewTypeCount > 1;
            java.util.ArrayList<android.view.View> arrayList = this.mCurrentScrap;
            for (int length = viewArr.length - 1; length >= 0; length--) {
                android.view.View view = viewArr[length];
                if (view != null) {
                    android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
                    int i = layoutParams.viewType;
                    viewArr[length] = null;
                    if (view.hasTransientState()) {
                        view.dispatchStartTemporaryDetach();
                        if (android.widget.AbsListView.this.mAdapter != null && android.widget.AbsListView.this.mAdapterHasStableIds) {
                            if (this.mTransientStateViewsById == null) {
                                this.mTransientStateViewsById = new android.util.LongSparseArray<>();
                            }
                            this.mTransientStateViewsById.put(android.widget.AbsListView.this.mAdapter.getItemId(this.mFirstActivePosition + length), view);
                        } else if (!android.widget.AbsListView.this.mDataChanged) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new android.util.SparseArray<>();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + length, view);
                        } else if (i != -2) {
                            removeDetachedView(view, false);
                        }
                    } else if (!shouldRecycleViewType(i)) {
                        if (i != -2) {
                            removeDetachedView(view, false);
                        }
                    } else {
                        if (z2) {
                            arrayList = this.mScrapViews[i];
                        }
                        layoutParams.scrappedFromPosition = this.mFirstActivePosition + length;
                        removeDetachedView(view, false);
                        arrayList.add(view);
                        if (z) {
                            this.mRecyclerListener.onMovedToScrapHeap(view);
                        }
                    }
                }
            }
            pruneScrapViews();
        }

        void fullyDetachScrapViews() {
            int i = this.mViewTypeCount;
            java.util.ArrayList<android.view.View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                java.util.ArrayList<android.view.View> arrayList = arrayListArr[i2];
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    android.view.View view = arrayList.get(size);
                    if (view.isTemporarilyDetached()) {
                        removeDetachedView(view, false);
                    }
                }
            }
        }

        private void pruneScrapViews() {
            int length = this.mActiveViews.length;
            int i = this.mViewTypeCount;
            java.util.ArrayList<android.view.View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                java.util.ArrayList<android.view.View> arrayList = arrayListArr[i2];
                int size = arrayList.size();
                while (size > length) {
                    size--;
                    arrayList.remove(size);
                }
            }
            android.util.SparseArray<android.view.View> sparseArray = this.mTransientStateViews;
            if (sparseArray != null) {
                int i3 = 0;
                while (i3 < sparseArray.size()) {
                    android.view.View valueAt = sparseArray.valueAt(i3);
                    if (!valueAt.hasTransientState()) {
                        removeDetachedView(valueAt, false);
                        sparseArray.removeAt(i3);
                        i3--;
                    }
                    i3++;
                }
            }
            android.util.LongSparseArray<android.view.View> longSparseArray = this.mTransientStateViewsById;
            if (longSparseArray != null) {
                int i4 = 0;
                while (i4 < longSparseArray.size()) {
                    android.view.View valueAt2 = longSparseArray.valueAt(i4);
                    if (!valueAt2.hasTransientState()) {
                        removeDetachedView(valueAt2, false);
                        longSparseArray.removeAt(i4);
                        i4--;
                    }
                    i4++;
                }
            }
        }

        void reclaimScrapViews(java.util.List<android.view.View> list) {
            if (this.mViewTypeCount == 1) {
                list.addAll(this.mCurrentScrap);
                return;
            }
            int i = this.mViewTypeCount;
            java.util.ArrayList<android.view.View>[] arrayListArr = this.mScrapViews;
            for (int i2 = 0; i2 < i; i2++) {
                list.addAll(arrayListArr[i2]);
            }
        }

        void setCacheColorHint(int i) {
            if (this.mViewTypeCount == 1) {
                java.util.ArrayList<android.view.View> arrayList = this.mCurrentScrap;
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.get(i2).setDrawingCacheBackgroundColor(i);
                }
            } else {
                int i3 = this.mViewTypeCount;
                for (int i4 = 0; i4 < i3; i4++) {
                    java.util.ArrayList<android.view.View> arrayList2 = this.mScrapViews[i4];
                    int size2 = arrayList2.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        arrayList2.get(i5).setDrawingCacheBackgroundColor(i);
                    }
                }
            }
            for (android.view.View view : this.mActiveViews) {
                if (view != null) {
                    view.setDrawingCacheBackgroundColor(i);
                }
            }
        }

        private android.view.View retrieveFromScrap(java.util.ArrayList<android.view.View> arrayList, int i) {
            int size = arrayList.size();
            if (size > 0) {
                int i2 = size - 1;
                for (int i3 = i2; i3 >= 0; i3--) {
                    android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) arrayList.get(i3).getLayoutParams();
                    if (android.widget.AbsListView.this.mAdapterHasStableIds) {
                        if (android.widget.AbsListView.this.mAdapter.getItemId(i) == layoutParams.itemId) {
                            return arrayList.remove(i3);
                        }
                    } else if (layoutParams.scrappedFromPosition == i) {
                        android.view.View remove = arrayList.remove(i3);
                        clearScrapForRebind(remove);
                        return remove;
                    }
                }
                android.view.View remove2 = arrayList.remove(i2);
                clearScrapForRebind(remove2);
                return remove2;
            }
            return null;
        }

        private void clearScrap(java.util.ArrayList<android.view.View> arrayList) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                removeDetachedView(arrayList.remove((size - 1) - i), false);
            }
        }

        private void clearScrapForRebind(android.view.View view) {
            view.clearAccessibilityFocus();
            view.setAccessibilityDelegate(null);
            view.resetSubtreeAutofillIds();
        }

        private void removeDetachedView(android.view.View view, boolean z) {
            view.setAccessibilityDelegate(null);
            view.resetSubtreeAutofillIds();
            android.widget.AbsListView.this.removeDetachedView(view, z);
        }
    }

    int getHeightForPosition(int i) {
        int firstVisiblePosition = getFirstVisiblePosition();
        int childCount = getChildCount();
        int i2 = i - firstVisiblePosition;
        if (i2 >= 0 && i2 < childCount) {
            return getChildAt(i2).getHeight();
        }
        android.view.View obtainView = obtainView(i, this.mIsScrap);
        obtainView.measure(this.mWidthMeasureSpec, 0);
        int measuredHeight = obtainView.getMeasuredHeight();
        this.mRecycler.addScrapView(obtainView, i);
        return measuredHeight;
    }

    public void setSelectionFromTop(int i, int i2) {
        if (this.mAdapter == null) {
            return;
        }
        if (!isInTouchMode()) {
            i = lookForSelectablePosition(i, true);
            if (i >= 0) {
                setNextSelectedPositionInt(i);
            }
        } else {
            this.mResurrectToPosition = i;
        }
        if (i >= 0) {
            this.mLayoutMode = 4;
            this.mSpecificTop = this.mListPadding.top + i2;
            if (this.mNeedSync) {
                this.mSyncPosition = i;
                this.mSyncRowId = this.mAdapter.getItemId(i);
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            requestLayout();
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("drawing:cacheColorHint", getCacheColorHint());
        viewHierarchyEncoder.addProperty("list:fastScrollEnabled", isFastScrollEnabled());
        viewHierarchyEncoder.addProperty("list:scrollingCacheEnabled", isScrollingCacheEnabled());
        viewHierarchyEncoder.addProperty("list:smoothScrollbarEnabled", isSmoothScrollbarEnabled());
        viewHierarchyEncoder.addProperty("list:stackFromBottom", isStackFromBottom());
        viewHierarchyEncoder.addProperty("list:textFilterEnabled", isTextFilterEnabled());
        android.view.View selectedView = getSelectedView();
        if (selectedView != null) {
            viewHierarchyEncoder.addPropertyKey("selectedView");
            selectedView.encode(viewHierarchyEncoder);
        }
    }

    static abstract class AbsPositionScroller {
        public abstract void start(int i);

        public abstract void start(int i, int i2);

        public abstract void startWithOffset(int i, int i2);

        public abstract void startWithOffset(int i, int i2, int i3);

        public abstract void stop();

        AbsPositionScroller() {
        }
    }

    class PositionScroller extends android.widget.AbsListView.AbsPositionScroller implements java.lang.Runnable {
        private static final int MOVE_DOWN_BOUND = 3;
        private static final int MOVE_DOWN_POS = 1;
        private static final int MOVE_OFFSET = 5;
        private static final int MOVE_UP_BOUND = 4;
        private static final int MOVE_UP_POS = 2;
        private static final int SCROLL_DURATION = 200;
        private int mBoundPos;
        private final int mExtraScroll;
        private int mLastSeenPos;
        private int mMode;
        private int mOffsetFromTop;
        private int mScrollDuration;
        private int mTargetPos;

        PositionScroller() {
            this.mExtraScroll = android.view.ViewConfiguration.get(android.widget.AbsListView.this.mContext).getScaledFadingEdgeLength();
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void start(final int i) {
            int i2;
            stop();
            if (android.widget.AbsListView.this.mDataChanged) {
                android.widget.AbsListView.this.mPositionScrollAfterLayout = new java.lang.Runnable() { // from class: android.widget.AbsListView.PositionScroller.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.AbsListView.PositionScroller.this.start(i);
                    }
                };
                return;
            }
            int childCount = android.widget.AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int i3 = android.widget.AbsListView.this.mFirstPosition;
            int i4 = (childCount + i3) - 1;
            int max = java.lang.Math.max(0, java.lang.Math.min(android.widget.AbsListView.this.getCount() - 1, i));
            if (max < i3) {
                i2 = (i3 - max) + 1;
                this.mMode = 2;
            } else if (max > i4) {
                i2 = (max - i4) + 1;
                this.mMode = 1;
            } else {
                scrollToVisible(max, -1, 200);
                return;
            }
            if (i2 > 0) {
                this.mScrollDuration = 200 / i2;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = max;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            android.widget.AbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void start(final int i, final int i2) {
            int i3;
            stop();
            if (i2 == -1) {
                start(i);
                return;
            }
            if (android.widget.AbsListView.this.mDataChanged) {
                android.widget.AbsListView.this.mPositionScrollAfterLayout = new java.lang.Runnable() { // from class: android.widget.AbsListView.PositionScroller.2
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.AbsListView.PositionScroller.this.start(i, i2);
                    }
                };
                return;
            }
            int childCount = android.widget.AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int i4 = android.widget.AbsListView.this.mFirstPosition;
            int i5 = (childCount + i4) - 1;
            int max = java.lang.Math.max(0, java.lang.Math.min(android.widget.AbsListView.this.getCount() - 1, i));
            if (max < i4) {
                int i6 = i5 - i2;
                if (i6 < 1) {
                    return;
                }
                i3 = (i4 - max) + 1;
                int i7 = i6 - 1;
                if (i7 < i3) {
                    this.mMode = 4;
                    i3 = i7;
                } else {
                    this.mMode = 2;
                }
            } else if (max > i5) {
                int i8 = i2 - i4;
                if (i8 < 1) {
                    return;
                }
                int i9 = (max - i5) + 1;
                i3 = i8 - 1;
                if (i3 < i9) {
                    this.mMode = 3;
                } else {
                    this.mMode = 1;
                    i3 = i9;
                }
            } else {
                scrollToVisible(max, i2, 200);
                return;
            }
            if (i3 > 0) {
                this.mScrollDuration = 200 / i3;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = max;
            this.mBoundPos = i2;
            this.mLastSeenPos = -1;
            android.widget.AbsListView.this.postOnAnimation(this);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void startWithOffset(int i, int i2) {
            startWithOffset(i, i2, 200);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void startWithOffset(final int i, final int i2, final int i3) {
            int i4;
            stop();
            if (android.widget.AbsListView.this.mDataChanged) {
                android.widget.AbsListView.this.mPositionScrollAfterLayout = new java.lang.Runnable() { // from class: android.widget.AbsListView.PositionScroller.3
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.AbsListView.PositionScroller.this.startWithOffset(i, i2, i3);
                    }
                };
                return;
            }
            int childCount = android.widget.AbsListView.this.getChildCount();
            if (childCount == 0) {
                return;
            }
            int paddingTop = i2 + android.widget.AbsListView.this.getPaddingTop();
            this.mTargetPos = java.lang.Math.max(0, java.lang.Math.min(android.widget.AbsListView.this.getCount() - 1, i));
            this.mOffsetFromTop = paddingTop;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.mMode = 5;
            int i5 = android.widget.AbsListView.this.mFirstPosition;
            int i6 = (i5 + childCount) - 1;
            if (this.mTargetPos < i5) {
                i4 = i5 - this.mTargetPos;
            } else if (this.mTargetPos > i6) {
                i4 = this.mTargetPos - i6;
            } else {
                android.widget.AbsListView.this.smoothScrollBy(android.widget.AbsListView.this.getChildAt(this.mTargetPos - i5).getTop() - paddingTop, i3, true, false);
                return;
            }
            float f = i4 / childCount;
            if (f >= 1.0f) {
                i3 = (int) (i3 / f);
            }
            this.mScrollDuration = i3;
            this.mLastSeenPos = -1;
            android.widget.AbsListView.this.postOnAnimation(this);
        }

        private void scrollToVisible(int i, int i2, int i3) {
            int i4;
            int i5 = android.widget.AbsListView.this.mFirstPosition;
            int childCount = (android.widget.AbsListView.this.getChildCount() + i5) - 1;
            int i6 = android.widget.AbsListView.this.mListPadding.top;
            int height = android.widget.AbsListView.this.getHeight() - android.widget.AbsListView.this.mListPadding.bottom;
            if (i < i5 || i > childCount) {
                android.util.Log.w(android.widget.AbsListView.TAG, "scrollToVisible called with targetPos " + i + " not visible [" + i5 + ", " + childCount + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (i2 < i5 || i2 > childCount) {
                i2 = -1;
            }
            android.view.View childAt = android.widget.AbsListView.this.getChildAt(i - i5);
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            if (bottom <= height) {
                i4 = 0;
            } else {
                i4 = bottom - height;
            }
            if (top < i6) {
                i4 = top - i6;
            }
            if (i4 == 0) {
                return;
            }
            if (i2 >= 0) {
                android.view.View childAt2 = android.widget.AbsListView.this.getChildAt(i2 - i5);
                int top2 = childAt2.getTop();
                int bottom2 = childAt2.getBottom();
                int abs = java.lang.Math.abs(i4);
                if (i4 < 0 && bottom2 + abs > height) {
                    i4 = java.lang.Math.max(0, bottom2 - height);
                } else if (i4 > 0 && top2 - abs < i6) {
                    i4 = java.lang.Math.min(0, top2 - i6);
                }
            }
            android.widget.AbsListView.this.smoothScrollBy(i4, i3);
        }

        @Override // android.widget.AbsListView.AbsPositionScroller
        public void stop() {
            android.widget.AbsListView.this.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            int height = android.widget.AbsListView.this.getHeight();
            int i = android.widget.AbsListView.this.mFirstPosition;
            switch (this.mMode) {
                case 1:
                    int childCount = android.widget.AbsListView.this.getChildCount() - 1;
                    int i2 = i + childCount;
                    if (childCount >= 0) {
                        if (i2 == this.mLastSeenPos) {
                            android.widget.AbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            android.view.View childAt = android.widget.AbsListView.this.getChildAt(childCount);
                            android.widget.AbsListView.this.smoothScrollBy((childAt.getHeight() - (height - childAt.getTop())) + (i2 < android.widget.AbsListView.this.mItemCount - 1 ? java.lang.Math.max(android.widget.AbsListView.this.mListPadding.bottom, this.mExtraScroll) : android.widget.AbsListView.this.mListPadding.bottom), this.mScrollDuration, true, i2 < this.mTargetPos);
                            this.mLastSeenPos = i2;
                            if (i2 < this.mTargetPos) {
                                android.widget.AbsListView.this.postOnAnimation(this);
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    if (i == this.mLastSeenPos) {
                        android.widget.AbsListView.this.postOnAnimation(this);
                        break;
                    } else {
                        android.view.View childAt2 = android.widget.AbsListView.this.getChildAt(0);
                        if (childAt2 != null) {
                            android.widget.AbsListView.this.smoothScrollBy(childAt2.getTop() - (i > 0 ? java.lang.Math.max(this.mExtraScroll, android.widget.AbsListView.this.mListPadding.top) : android.widget.AbsListView.this.mListPadding.top), this.mScrollDuration, true, i > this.mTargetPos);
                            this.mLastSeenPos = i;
                            if (i > this.mTargetPos) {
                                android.widget.AbsListView.this.postOnAnimation(this);
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    int childCount2 = android.widget.AbsListView.this.getChildCount();
                    if (i == this.mBoundPos || childCount2 <= 1 || childCount2 + i >= android.widget.AbsListView.this.mItemCount) {
                        android.widget.AbsListView.this.reportScrollStateChange(0);
                        break;
                    } else {
                        int i3 = i + 1;
                        if (i3 == this.mLastSeenPos) {
                            android.widget.AbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            android.view.View childAt3 = android.widget.AbsListView.this.getChildAt(1);
                            int height2 = childAt3.getHeight();
                            int top = childAt3.getTop();
                            int max = java.lang.Math.max(android.widget.AbsListView.this.mListPadding.bottom, this.mExtraScroll);
                            if (i3 < this.mBoundPos) {
                                android.widget.AbsListView.this.smoothScrollBy(java.lang.Math.max(0, (height2 + top) - max), this.mScrollDuration, true, true);
                                this.mLastSeenPos = i3;
                                android.widget.AbsListView.this.postOnAnimation(this);
                                break;
                            } else if (top > max) {
                                android.widget.AbsListView.this.smoothScrollBy(top - max, this.mScrollDuration, true, false);
                                break;
                            } else {
                                android.widget.AbsListView.this.reportScrollStateChange(0);
                                break;
                            }
                        }
                    }
                case 4:
                    int childCount3 = android.widget.AbsListView.this.getChildCount() - 2;
                    if (childCount3 >= 0) {
                        int i4 = i + childCount3;
                        if (i4 == this.mLastSeenPos) {
                            android.widget.AbsListView.this.postOnAnimation(this);
                            break;
                        } else {
                            android.view.View childAt4 = android.widget.AbsListView.this.getChildAt(childCount3);
                            int height3 = childAt4.getHeight();
                            int top2 = childAt4.getTop();
                            int i5 = height - top2;
                            int max2 = java.lang.Math.max(android.widget.AbsListView.this.mListPadding.top, this.mExtraScroll);
                            this.mLastSeenPos = i4;
                            if (i4 > this.mBoundPos) {
                                android.widget.AbsListView.this.smoothScrollBy(-(i5 - max2), this.mScrollDuration, true, true);
                                android.widget.AbsListView.this.postOnAnimation(this);
                                break;
                            } else {
                                int i6 = height - max2;
                                int i7 = top2 + height3;
                                if (i6 > i7) {
                                    android.widget.AbsListView.this.smoothScrollBy(-(i6 - i7), this.mScrollDuration, true, false);
                                    break;
                                } else {
                                    android.widget.AbsListView.this.reportScrollStateChange(0);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    if (this.mLastSeenPos == i) {
                        android.widget.AbsListView.this.postOnAnimation(this);
                        break;
                    } else {
                        this.mLastSeenPos = i;
                        int childCount4 = android.widget.AbsListView.this.getChildCount();
                        if (childCount4 > 0) {
                            int i8 = this.mTargetPos;
                            int i9 = (i + childCount4) - 1;
                            int height4 = android.widget.AbsListView.this.getChildAt(0).getHeight();
                            int height5 = android.widget.AbsListView.this.getChildAt(childCount4 - 1).getHeight();
                            float f = height4;
                            float f2 = 0.0f;
                            float top3 = f == 0.0f ? 1.0f : (height4 + r6.getTop()) / f;
                            float f3 = height5;
                            float height6 = f3 == 0.0f ? 1.0f : ((height5 + android.widget.AbsListView.this.getHeight()) - r8.getBottom()) / f3;
                            if (i8 < i) {
                                f2 = (i - i8) + (1.0f - top3) + 1.0f;
                            } else if (i8 > i9) {
                                f2 = (i8 - i9) + (1.0f - height6);
                            }
                            float min = java.lang.Math.min(java.lang.Math.abs(f2 / childCount4), 1.0f);
                            if (i8 >= i) {
                                if (i8 > i9) {
                                    android.widget.AbsListView.this.smoothScrollBy((int) (android.widget.AbsListView.this.getHeight() * min), (int) (this.mScrollDuration * min), true, true);
                                    android.widget.AbsListView.this.postOnAnimation(this);
                                    break;
                                } else {
                                    android.widget.AbsListView.this.smoothScrollBy(android.widget.AbsListView.this.getChildAt(i8 - i).getTop() - this.mOffsetFromTop, (int) (this.mScrollDuration * (java.lang.Math.abs(r0) / android.widget.AbsListView.this.getHeight())), true, false);
                                    break;
                                }
                            } else {
                                android.widget.AbsListView.this.smoothScrollBy((int) ((-android.widget.AbsListView.this.getHeight()) * min), (int) (this.mScrollDuration * min), true, true);
                                android.widget.AbsListView.this.postOnAnimation(this);
                                break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    private class DifferentialFlingTarget implements android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget {
        private DifferentialFlingTarget() {
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public boolean startDifferentialMotionFling(float f) {
            stopDifferentialMotionFling();
            android.widget.AbsListView.this.fling((int) f);
            return true;
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public void stopDifferentialMotionFling() {
            if (android.widget.AbsListView.this.mFlingRunnable != null) {
                android.widget.AbsListView.this.mFlingRunnable.endFling();
            }
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public float getScaledScrollFactor() {
            return -android.widget.AbsListView.this.mVerticalScrollFactor;
        }
    }
}
