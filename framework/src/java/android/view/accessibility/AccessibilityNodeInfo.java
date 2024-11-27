package android.view.accessibility;

/* loaded from: classes4.dex */
public class AccessibilityNodeInfo implements android.os.Parcelable {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final java.lang.String ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN = "android.view.accessibility.action.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN";
    public static final java.lang.String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final java.lang.String ACTION_ARGUMENT_DIRECTION_INT = "android.view.accessibility.action.ARGUMENT_DIRECTION_INT";
    public static final java.lang.String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final java.lang.String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final java.lang.String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final java.lang.String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final java.lang.String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final java.lang.String ACTION_ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT = "android.view.accessibility.action.ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT";
    public static final java.lang.String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final java.lang.String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final java.lang.String ACTION_ARGUMENT_SCROLL_AMOUNT_FLOAT = "android.view.accessibility.action.ARGUMENT_SCROLL_AMOUNT_FLOAT";
    public static final java.lang.String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final java.lang.String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final java.lang.String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_DATA_SENSITIVE = 33554432;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 1024;
    private static final int BOOLEAN_PROPERTY_CHECKABLE = 1;
    private static final int BOOLEAN_PROPERTY_CHECKED = 2;
    private static final int BOOLEAN_PROPERTY_CLICKABLE = 32;
    private static final int BOOLEAN_PROPERTY_CONTENT_INVALID = 65536;
    private static final int BOOLEAN_PROPERTY_CONTEXT_CLICKABLE = 131072;
    private static final int BOOLEAN_PROPERTY_DISMISSABLE = 16384;
    private static final int BOOLEAN_PROPERTY_EDITABLE = 4096;
    private static final int BOOLEAN_PROPERTY_ENABLED = 128;
    private static final int BOOLEAN_PROPERTY_FOCUSABLE = 4;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 8;
    private static final int BOOLEAN_PROPERTY_IMPORTANCE = 262144;
    private static final int BOOLEAN_PROPERTY_IS_HEADING = 2097152;
    private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 1048576;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 4194304;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_SELECTABLE = 8388608;
    private static final int BOOLEAN_PROPERTY_LONG_CLICKABLE = 64;
    private static final int BOOLEAN_PROPERTY_MULTI_LINE = 32768;
    private static final int BOOLEAN_PROPERTY_OPENS_POPUP = 8192;
    private static final int BOOLEAN_PROPERTY_PASSWORD = 256;
    private static final int BOOLEAN_PROPERTY_REQUEST_INITIAL_ACCESSIBILITY_FOCUS = 16777216;
    private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 524288;
    private static final int BOOLEAN_PROPERTY_SCROLLABLE = 512;
    private static final int BOOLEAN_PROPERTY_SELECTED = 16;
    private static final int BOOLEAN_PROPERTY_SUPPORTS_GRANULAR_SCROLLING = 67108864;
    private static final int BOOLEAN_PROPERTY_VISIBLE_TO_USER = 2048;
    public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityNodeInfo> CREATOR;
    private static final boolean DEBUG;
    private static final android.view.accessibility.AccessibilityNodeInfo DEFAULT;
    public static final java.lang.String EXTRA_DATA_RENDERING_INFO_KEY = "android.view.accessibility.extra.DATA_RENDERING_INFO_KEY";
    public static final java.lang.String EXTRA_DATA_REQUESTED_KEY = "android.view.accessibility.AccessibilityNodeInfo.extra_data_requested";
    public static final java.lang.String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH";
    public static final int EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH = 20000;
    public static final java.lang.String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX";
    public static final java.lang.String EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY";
    public static final int FLAG_PREFETCH_ANCESTORS = 1;
    public static final int FLAG_PREFETCH_DESCENDANTS_BREADTH_FIRST = 16;
    public static final int FLAG_PREFETCH_DESCENDANTS_DEPTH_FIRST = 8;
    public static final int FLAG_PREFETCH_DESCENDANTS_HYBRID = 4;
    public static final int FLAG_PREFETCH_DESCENDANTS_MASK = 28;
    public static final int FLAG_PREFETCH_MASK = 63;
    public static final int FLAG_PREFETCH_SIBLINGS = 2;
    public static final int FLAG_PREFETCH_UNINTERRUPTIBLE = 32;
    public static final int FLAG_REPORT_MASK = 896;
    public static final int FLAG_SERVICE_IS_ACCESSIBILITY_TOOL = 512;
    public static final int FLAG_SERVICE_REQUESTS_INCLUDE_NOT_IMPORTANT_VIEWS = 128;
    public static final int FLAG_SERVICE_REQUESTS_REPORT_VIEW_IDS = 256;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final int INVALID_ACTIONS_MASK = -4194304;
    public static final int LAST_LEGACY_STANDARD_ACTION = 2097152;
    public static final int LEASHED_ITEM_ID = 2147483645;
    public static final long LEASHED_NODE_ID;
    public static final int MAX_NUMBER_OF_PREFETCHED_NODES = 50;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    public static final int ROOT_ITEM_ID = 2147483646;
    public static final long ROOT_NODE_ID;
    private static final java.lang.String TAG = "AccessibilityNodeInfo";
    public static final int UNDEFINED_CONNECTION_ID = -1;
    public static final int UNDEFINED_ITEM_ID = Integer.MAX_VALUE;
    public static final long UNDEFINED_NODE_ID;
    public static final int UNDEFINED_SELECTION_INDEX = -1;
    private static final long VIRTUAL_DESCENDANT_ID_MASK = -4294967296L;
    private static final int VIRTUAL_DESCENDANT_ID_SHIFT = 32;
    private java.util.ArrayList<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> mActions;
    private int mBooleanProperties;
    private android.util.LongArray mChildNodeIds;
    private java.lang.CharSequence mClassName;
    private android.view.accessibility.AccessibilityNodeInfo.CollectionInfo mCollectionInfo;
    private android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo mCollectionItemInfo;
    private java.lang.CharSequence mContainerTitle;
    private java.lang.CharSequence mContentDescription;
    private int mDrawingOrderInParent;
    private java.lang.CharSequence mError;
    private java.util.ArrayList<java.lang.String> mExtraDataKeys;
    private android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo mExtraRenderingInfo;
    private android.os.Bundle mExtras;
    private java.lang.CharSequence mHintText;
    private android.os.IBinder mLeashedChild;
    private android.os.IBinder mLeashedParent;
    private int mMovementGranularities;
    private java.lang.CharSequence mOriginalText;
    private java.lang.CharSequence mPackageName;
    private java.lang.CharSequence mPaneTitle;
    private android.view.accessibility.AccessibilityNodeInfo.RangeInfo mRangeInfo;
    private boolean mSealed;
    private java.lang.CharSequence mStateDescription;
    private java.lang.CharSequence mText;
    private java.lang.CharSequence mTooltipText;
    private android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo mTouchDelegateInfo;
    private java.lang.String mUniqueId;
    private java.lang.String mViewIdResourceName;
    private int mWindowId = -1;
    private long mSourceNodeId = UNDEFINED_NODE_ID;
    private long mParentNodeId = UNDEFINED_NODE_ID;
    private long mLabelForId = UNDEFINED_NODE_ID;
    private long mLabeledById = UNDEFINED_NODE_ID;
    private long mTraversalBefore = UNDEFINED_NODE_ID;
    private long mTraversalAfter = UNDEFINED_NODE_ID;
    private long mMinDurationBetweenContentChanges = 0;
    private final android.graphics.Rect mBoundsInParent = new android.graphics.Rect();
    private final android.graphics.Rect mBoundsInScreen = new android.graphics.Rect();
    private final android.graphics.Rect mBoundsInWindow = new android.graphics.Rect();
    private int mMaxTextLength = -1;
    private int mTextSelectionStart = -1;
    private int mTextSelectionEnd = -1;
    private int mInputType = 0;
    private int mLiveRegion = 0;
    private int mConnectionId = -1;
    private long mLeashedParentNodeId = UNDEFINED_NODE_ID;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrefetchingStrategy {
    }

    static {
        DEBUG = android.util.Log.isLoggable(TAG, 3) && android.os.Build.IS_DEBUGGABLE;
        UNDEFINED_NODE_ID = makeNodeId(Integer.MAX_VALUE, Integer.MAX_VALUE);
        ROOT_NODE_ID = makeNodeId(2147483646, -1);
        LEASHED_NODE_ID = makeNodeId(LEASHED_ITEM_ID, -1);
        DEFAULT = new android.view.accessibility.AccessibilityNodeInfo();
        CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.AccessibilityNodeInfo>() { // from class: android.view.accessibility.AccessibilityNodeInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityNodeInfo createFromParcel(android.os.Parcel parcel) {
                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = new android.view.accessibility.AccessibilityNodeInfo();
                accessibilityNodeInfo.initFromParcel(parcel);
                return accessibilityNodeInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityNodeInfo[] newArray(int i) {
                return new android.view.accessibility.AccessibilityNodeInfo[i];
            }
        };
    }

    public static int getAccessibilityViewId(long j) {
        return (int) j;
    }

    public static int getVirtualDescendantId(long j) {
        return (int) ((j & VIRTUAL_DESCENDANT_ID_MASK) >> 32);
    }

    public static long makeNodeId(int i, int i2) {
        return i | (i2 << 32);
    }

    public AccessibilityNodeInfo() {
    }

    public AccessibilityNodeInfo(android.view.View view) {
        setSource(view);
    }

    public AccessibilityNodeInfo(android.view.View view, int i) {
        setSource(view, i);
    }

    public AccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        init(accessibilityNodeInfo);
    }

    public void setSource(android.view.View view) {
        setSource(view, -1);
    }

    public void setSource(android.view.View view, int i) {
        enforceNotSealed();
        this.mWindowId = view != null ? view.getAccessibilityWindowId() : Integer.MAX_VALUE;
        this.mSourceNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public android.view.accessibility.AccessibilityNodeInfo findFocus(int i) {
        enforceSealed();
        enforceValidFocusType(i);
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i);
    }

    public android.view.accessibility.AccessibilityNodeInfo focusSearch(int i) {
        enforceSealed();
        enforceValidFocusDirection(i);
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().focusSearch(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i);
    }

    public int getWindowId() {
        return this.mWindowId;
    }

    public boolean refresh(android.os.Bundle bundle, boolean z) {
        android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoByAccessibilityId;
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId) || (findAccessibilityNodeInfoByAccessibilityId = android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, z, 0, bundle)) == null) {
            return false;
        }
        init(findAccessibilityNodeInfoByAccessibilityId);
        return true;
    }

    public boolean refresh() {
        return refresh(null, true);
    }

    public boolean refreshWithExtraData(java.lang.String str, android.os.Bundle bundle) {
        if (bundle.getInt(EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1) > 20000) {
            bundle.putInt(EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, 20000);
        }
        bundle.putString(EXTRA_DATA_REQUESTED_KEY, str);
        return refresh(bundle, true);
    }

    public android.util.LongArray getChildNodeIds() {
        return this.mChildNodeIds;
    }

    public long getChildId(int i) {
        if (this.mChildNodeIds == null) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mChildNodeIds.get(i);
    }

    public int getChildCount() {
        if (this.mChildNodeIds == null) {
            return 0;
        }
        return this.mChildNodeIds.size();
    }

    public android.view.accessibility.AccessibilityNodeInfo getChild(int i) {
        return getChild(i, 4);
    }

    public android.view.accessibility.AccessibilityNodeInfo getChild(int i, int i2) {
        enforceSealed();
        if (this.mChildNodeIds == null || !canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        long j = this.mChildNodeIds.get(i);
        android.view.accessibility.AccessibilityInteractionClient accessibilityInteractionClient = android.view.accessibility.AccessibilityInteractionClient.getInstance();
        return (this.mLeashedChild == null || j != LEASHED_NODE_ID) ? accessibilityInteractionClient.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, j, false, i2, (android.os.Bundle) null) : accessibilityInteractionClient.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mLeashedChild, ROOT_NODE_ID, false, i2, (android.os.Bundle) null);
    }

    public void addChild(android.view.View view) {
        addChildInternal(view, -1, true);
    }

    public void addChild(android.os.IBinder iBinder) {
        enforceNotSealed();
        if (iBinder == null) {
            return;
        }
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new android.util.LongArray();
        }
        this.mLeashedChild = iBinder;
        if (this.mChildNodeIds.indexOf(LEASHED_NODE_ID) >= 0) {
            return;
        }
        this.mChildNodeIds.add(LEASHED_NODE_ID);
    }

    public void addChildUnchecked(android.view.View view) {
        addChildInternal(view, -1, false);
    }

    public boolean removeChild(android.view.View view) {
        return removeChild(view, -1);
    }

    public boolean removeChild(android.os.IBinder iBinder) {
        enforceNotSealed();
        if (this.mChildNodeIds == null || this.mLeashedChild == null || !this.mLeashedChild.equals(iBinder)) {
            return false;
        }
        int indexOf = this.mChildNodeIds.indexOf(LEASHED_NODE_ID);
        this.mLeashedChild = null;
        if (indexOf < 0) {
            return false;
        }
        this.mChildNodeIds.remove(indexOf);
        return true;
    }

    public void addChild(android.view.View view, int i) {
        addChildInternal(view, i, true);
    }

    private void addChildInternal(android.view.View view, int i, boolean z) {
        enforceNotSealed();
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new android.util.LongArray();
        }
        long makeNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
        if (makeNodeId == this.mSourceNodeId) {
            android.util.Log.e(TAG, "Rejecting attempt to make a View its own child");
        } else {
            if (z && this.mChildNodeIds.indexOf(makeNodeId) >= 0) {
                return;
            }
            this.mChildNodeIds.add(makeNodeId);
        }
    }

    public boolean removeChild(android.view.View view, int i) {
        enforceNotSealed();
        android.util.LongArray longArray = this.mChildNodeIds;
        if (longArray == null) {
            return false;
        }
        int indexOf = longArray.indexOf(makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i));
        if (indexOf < 0) {
            return false;
        }
        longArray.remove(indexOf);
        return true;
    }

    public java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getActionList() {
        return com.android.internal.util.CollectionUtils.emptyIfNull(this.mActions);
    }

    @java.lang.Deprecated
    public int getActions() {
        if (this.mActions == null) {
            return 0;
        }
        int size = this.mActions.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int id = this.mActions.get(i2).getId();
            if (id <= 2097152) {
                i |= id;
            }
        }
        return i;
    }

    public void addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        enforceNotSealed();
        addActionUnchecked(accessibilityAction);
    }

    private void addActionUnchecked(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        if (accessibilityAction == null) {
            return;
        }
        if (this.mActions == null) {
            this.mActions = new java.util.ArrayList<>();
        }
        this.mActions.remove(accessibilityAction);
        this.mActions.add(accessibilityAction);
    }

    @java.lang.Deprecated
    public void addAction(int i) {
        enforceNotSealed();
        if ((INVALID_ACTIONS_MASK & i) != 0) {
            throw new java.lang.IllegalArgumentException("Action is not a combination of the standard actions: " + i);
        }
        addStandardActions(i);
    }

    @java.lang.Deprecated
    public void removeAction(int i) {
        enforceNotSealed();
        removeAction(getActionSingleton(i));
    }

    public boolean removeAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        enforceNotSealed();
        if (this.mActions == null || accessibilityAction == null) {
            return false;
        }
        return this.mActions.remove(accessibilityAction);
    }

    public void removeAllActions() {
        if (this.mActions != null) {
            this.mActions.clear();
        }
    }

    public android.view.accessibility.AccessibilityNodeInfo getTraversalBefore() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalBefore);
    }

    public void setTraversalBefore(android.view.View view) {
        setTraversalBefore(view, -1);
    }

    public void setTraversalBefore(android.view.View view, int i) {
        enforceNotSealed();
        this.mTraversalBefore = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public android.view.accessibility.AccessibilityNodeInfo getTraversalAfter() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalAfter);
    }

    public void setTraversalAfter(android.view.View view) {
        setTraversalAfter(view, -1);
    }

    public void setTraversalAfter(android.view.View view, int i) {
        enforceNotSealed();
        this.mTraversalAfter = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public java.util.List<java.lang.String> getAvailableExtraData() {
        if (this.mExtraDataKeys != null) {
            return java.util.Collections.unmodifiableList(this.mExtraDataKeys);
        }
        return java.util.Collections.EMPTY_LIST;
    }

    public void setAvailableExtraData(java.util.List<java.lang.String> list) {
        enforceNotSealed();
        this.mExtraDataKeys = new java.util.ArrayList<>(list);
    }

    public void setMaxTextLength(int i) {
        enforceNotSealed();
        this.mMaxTextLength = i;
    }

    public int getMaxTextLength() {
        return this.mMaxTextLength;
    }

    public void setMovementGranularities(int i) {
        enforceNotSealed();
        this.mMovementGranularities = i;
    }

    public int getMovementGranularities() {
        return this.mMovementGranularities;
    }

    public void setMinDurationBetweenContentChanges(java.time.Duration duration) {
        enforceNotSealed();
        this.mMinDurationBetweenContentChanges = duration.toMillis();
    }

    public java.time.Duration getMinDurationBetweenContentChanges() {
        return java.time.Duration.ofMillis(this.mMinDurationBetweenContentChanges);
    }

    public boolean performAction(int i) {
        android.os.Bundle bundle;
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        android.view.accessibility.AccessibilityInteractionClient accessibilityInteractionClient = android.view.accessibility.AccessibilityInteractionClient.getInstance();
        if (this.mExtras == null) {
            bundle = null;
        } else {
            bundle = this.mExtras;
        }
        return accessibilityInteractionClient.performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i, bundle);
    }

    public boolean performAction(int i, android.os.Bundle bundle) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, i, bundle);
    }

    public java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByText(java.lang.String str) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return java.util.Collections.emptyList();
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfosByText(this.mConnectionId, this.mWindowId, this.mSourceNodeId, str);
    }

    public java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(java.lang.String str) {
        enforceSealed();
        if (str == null) {
            android.util.Log.e(TAG, "returns empty list due to null viewId.");
            return java.util.Collections.emptyList();
        }
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return java.util.Collections.emptyList();
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfosByViewId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, str);
    }

    public android.view.accessibility.AccessibilityWindowInfo getWindow() {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mWindowId);
    }

    public android.view.accessibility.AccessibilityNodeInfo getParent() {
        enforceSealed();
        if (this.mLeashedParent != null && this.mLeashedParentNodeId != UNDEFINED_NODE_ID) {
            return getNodeForAccessibilityId(this.mConnectionId, this.mLeashedParent, this.mLeashedParentNodeId, 3);
        }
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId);
    }

    public android.view.accessibility.AccessibilityNodeInfo getParent(int i) {
        enforceSealed();
        if (this.mLeashedParent != null && this.mLeashedParentNodeId != UNDEFINED_NODE_ID) {
            return getNodeForAccessibilityId(this.mConnectionId, this.mLeashedParent, this.mLeashedParentNodeId, i);
        }
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId, i);
    }

    public long getParentNodeId() {
        return this.mParentNodeId;
    }

    public void setParent(android.view.View view) {
        setParent(view, -1);
    }

    public void setParent(android.view.View view, int i) {
        enforceNotSealed();
        this.mParentNodeId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    @java.lang.Deprecated
    public void getBoundsInParent(android.graphics.Rect rect) {
        rect.set(this.mBoundsInParent.left, this.mBoundsInParent.top, this.mBoundsInParent.right, this.mBoundsInParent.bottom);
    }

    @java.lang.Deprecated
    public void setBoundsInParent(android.graphics.Rect rect) {
        enforceNotSealed();
        this.mBoundsInParent.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getBoundsInScreen(android.graphics.Rect rect) {
        rect.set(this.mBoundsInScreen.left, this.mBoundsInScreen.top, this.mBoundsInScreen.right, this.mBoundsInScreen.bottom);
    }

    public android.graphics.Rect getBoundsInScreen() {
        return this.mBoundsInScreen;
    }

    public void setBoundsInScreen(android.graphics.Rect rect) {
        enforceNotSealed();
        this.mBoundsInScreen.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getBoundsInWindow(android.graphics.Rect rect) {
        rect.set(this.mBoundsInWindow.left, this.mBoundsInWindow.top, this.mBoundsInWindow.right, this.mBoundsInWindow.bottom);
    }

    public android.graphics.Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public void setBoundsInWindow(android.graphics.Rect rect) {
        enforceNotSealed();
        this.mBoundsInWindow.set(rect);
    }

    public boolean isCheckable() {
        return getBooleanProperty(1);
    }

    public void setCheckable(boolean z) {
        setBooleanProperty(1, z);
    }

    public boolean isChecked() {
        return getBooleanProperty(2);
    }

    public void setChecked(boolean z) {
        setBooleanProperty(2, z);
    }

    public boolean isFocusable() {
        return getBooleanProperty(4);
    }

    public void setFocusable(boolean z) {
        setBooleanProperty(4, z);
    }

    public boolean isFocused() {
        return getBooleanProperty(8);
    }

    public void setFocused(boolean z) {
        setBooleanProperty(8, z);
    }

    public boolean isVisibleToUser() {
        return getBooleanProperty(2048);
    }

    public void setVisibleToUser(boolean z) {
        setBooleanProperty(2048, z);
    }

    public boolean isAccessibilityFocused() {
        return getBooleanProperty(1024);
    }

    public void setAccessibilityFocused(boolean z) {
        setBooleanProperty(1024, z);
    }

    public boolean isSelected() {
        return getBooleanProperty(16);
    }

    public void setSelected(boolean z) {
        setBooleanProperty(16, z);
    }

    public boolean isClickable() {
        return getBooleanProperty(32);
    }

    public void setClickable(boolean z) {
        setBooleanProperty(32, z);
    }

    public boolean isLongClickable() {
        return getBooleanProperty(64);
    }

    public void setLongClickable(boolean z) {
        setBooleanProperty(64, z);
    }

    public boolean isEnabled() {
        return getBooleanProperty(128);
    }

    public void setEnabled(boolean z) {
        setBooleanProperty(128, z);
    }

    public boolean isPassword() {
        return getBooleanProperty(256);
    }

    public void setPassword(boolean z) {
        setBooleanProperty(256, z);
    }

    public boolean isScrollable() {
        return getBooleanProperty(512);
    }

    public void setScrollable(boolean z) {
        setBooleanProperty(512, z);
    }

    public boolean isGranularScrollingSupported() {
        return getBooleanProperty(67108864);
    }

    public void setGranularScrollingSupported(boolean z) {
        setBooleanProperty(67108864, z);
    }

    public boolean isTextSelectable() {
        return getBooleanProperty(8388608);
    }

    public void setTextSelectable(boolean z) {
        setBooleanProperty(8388608, z);
    }

    public boolean hasRequestInitialAccessibilityFocus() {
        return getBooleanProperty(16777216);
    }

    public void setRequestInitialAccessibilityFocus(boolean z) {
        setBooleanProperty(16777216, z);
    }

    public boolean isEditable() {
        return getBooleanProperty(4096);
    }

    public void setEditable(boolean z) {
        setBooleanProperty(4096, z);
    }

    public boolean isAccessibilityDataSensitive() {
        return getBooleanProperty(33554432);
    }

    public void setAccessibilityDataSensitive(boolean z) {
        setBooleanProperty(33554432, z);
    }

    public void setPaneTitle(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mPaneTitle = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public java.lang.CharSequence getPaneTitle() {
        return this.mPaneTitle;
    }

    public int getDrawingOrder() {
        return this.mDrawingOrderInParent;
    }

    public void setDrawingOrder(int i) {
        enforceNotSealed();
        this.mDrawingOrderInParent = i;
    }

    public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo getCollectionInfo() {
        return this.mCollectionInfo;
    }

    public void setCollectionInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo collectionInfo) {
        enforceNotSealed();
        this.mCollectionInfo = collectionInfo;
    }

    public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo getCollectionItemInfo() {
        return this.mCollectionItemInfo;
    }

    public void setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo) {
        enforceNotSealed();
        this.mCollectionItemInfo = collectionItemInfo;
    }

    public android.view.accessibility.AccessibilityNodeInfo.RangeInfo getRangeInfo() {
        return this.mRangeInfo;
    }

    public void setRangeInfo(android.view.accessibility.AccessibilityNodeInfo.RangeInfo rangeInfo) {
        enforceNotSealed();
        this.mRangeInfo = rangeInfo;
    }

    public android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo getExtraRenderingInfo() {
        return this.mExtraRenderingInfo;
    }

    public void setExtraRenderingInfo(android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo) {
        enforceNotSealed();
        this.mExtraRenderingInfo = extraRenderingInfo;
    }

    public boolean isContentInvalid() {
        return getBooleanProperty(65536);
    }

    public void setContentInvalid(boolean z) {
        setBooleanProperty(65536, z);
    }

    public boolean isContextClickable() {
        return getBooleanProperty(131072);
    }

    public void setContextClickable(boolean z) {
        setBooleanProperty(131072, z);
    }

    public int getLiveRegion() {
        return this.mLiveRegion;
    }

    public void setLiveRegion(int i) {
        enforceNotSealed();
        this.mLiveRegion = i;
    }

    public boolean isMultiLine() {
        return getBooleanProperty(32768);
    }

    public void setMultiLine(boolean z) {
        setBooleanProperty(32768, z);
    }

    public boolean canOpenPopup() {
        return getBooleanProperty(8192);
    }

    public void setCanOpenPopup(boolean z) {
        enforceNotSealed();
        setBooleanProperty(8192, z);
    }

    public boolean isDismissable() {
        return getBooleanProperty(16384);
    }

    public void setDismissable(boolean z) {
        setBooleanProperty(16384, z);
    }

    public boolean isImportantForAccessibility() {
        return getBooleanProperty(262144);
    }

    public void setImportantForAccessibility(boolean z) {
        setBooleanProperty(262144, z);
    }

    public boolean isScreenReaderFocusable() {
        return getBooleanProperty(524288);
    }

    public void setScreenReaderFocusable(boolean z) {
        setBooleanProperty(524288, z);
    }

    public boolean isShowingHintText() {
        return getBooleanProperty(1048576);
    }

    public void setShowingHintText(boolean z) {
        setBooleanProperty(1048576, z);
    }

    public boolean isHeading() {
        if (getBooleanProperty(2097152)) {
            return true;
        }
        android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = getCollectionItemInfo();
        return collectionItemInfo != null && collectionItemInfo.mHeading;
    }

    public void setHeading(boolean z) {
        setBooleanProperty(2097152, z);
    }

    public boolean isTextEntryKey() {
        return getBooleanProperty(4194304);
    }

    public void setTextEntryKey(boolean z) {
        setBooleanProperty(4194304, z);
    }

    public java.lang.CharSequence getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mPackageName = charSequence;
    }

    public java.lang.CharSequence getClassName() {
        return this.mClassName;
    }

    public void setClassName(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mClassName = charSequence;
    }

    public java.lang.CharSequence getText() {
        if (this.mText instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) this.mText;
            for (android.text.style.AccessibilityClickableSpan accessibilityClickableSpan : (android.text.style.AccessibilityClickableSpan[]) spanned.getSpans(0, this.mText.length(), android.text.style.AccessibilityClickableSpan.class)) {
                accessibilityClickableSpan.copyConnectionDataFrom(this);
            }
            for (android.text.style.AccessibilityURLSpan accessibilityURLSpan : (android.text.style.AccessibilityURLSpan[]) spanned.getSpans(0, this.mText.length(), android.text.style.AccessibilityURLSpan.class)) {
                accessibilityURLSpan.copyConnectionDataFrom(this);
            }
        }
        return this.mText;
    }

    public java.lang.CharSequence getOriginalText() {
        return this.mOriginalText;
    }

    public void setText(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mOriginalText = charSequence;
        if (charSequence instanceof android.text.Spanned) {
            this.mText = replaceReplacementSpan(replaceClickableSpan(charSequence));
        } else {
            this.mText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        }
    }

    private java.lang.CharSequence replaceClickableSpan(java.lang.CharSequence charSequence) {
        java.lang.Object accessibilityClickableSpan;
        android.text.style.ClickableSpan[] clickableSpanArr = (android.text.style.ClickableSpan[]) ((android.text.Spanned) charSequence).getSpans(0, charSequence.length(), android.text.style.ClickableSpan.class);
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence);
        if (clickableSpanArr.length == 0) {
            return charSequence;
        }
        for (android.text.style.ClickableSpan clickableSpan : clickableSpanArr) {
            if ((clickableSpan instanceof android.text.style.AccessibilityClickableSpan) || (clickableSpan instanceof android.text.style.AccessibilityURLSpan)) {
                break;
            }
            int spanStart = spannableStringBuilder.getSpanStart(clickableSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(clickableSpan);
            int spanFlags = spannableStringBuilder.getSpanFlags(clickableSpan);
            if (spanStart >= 0) {
                spannableStringBuilder.removeSpan(clickableSpan);
                if (clickableSpan instanceof android.text.style.URLSpan) {
                    accessibilityClickableSpan = new android.text.style.AccessibilityURLSpan((android.text.style.URLSpan) clickableSpan);
                } else {
                    accessibilityClickableSpan = new android.text.style.AccessibilityClickableSpan(clickableSpan.getId());
                }
                spannableStringBuilder.setSpan(accessibilityClickableSpan, spanStart, spanEnd, spanFlags);
            }
        }
        return spannableStringBuilder;
    }

    private java.lang.CharSequence replaceReplacementSpan(java.lang.CharSequence charSequence) {
        android.text.style.ReplacementSpan[] replacementSpanArr = (android.text.style.ReplacementSpan[]) ((android.text.Spanned) charSequence).getSpans(0, charSequence.length(), android.text.style.ReplacementSpan.class);
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence);
        if (replacementSpanArr.length == 0) {
            return charSequence;
        }
        for (android.text.style.ReplacementSpan replacementSpan : replacementSpanArr) {
            java.lang.CharSequence contentDescription = replacementSpan.getContentDescription();
            if (replacementSpan instanceof android.text.style.AccessibilityReplacementSpan) {
                break;
            }
            if (contentDescription != null) {
                int spanStart = spannableStringBuilder.getSpanStart(replacementSpan);
                int spanEnd = spannableStringBuilder.getSpanEnd(replacementSpan);
                int spanFlags = spannableStringBuilder.getSpanFlags(replacementSpan);
                if (spanStart >= 0) {
                    spannableStringBuilder.removeSpan(replacementSpan);
                    spannableStringBuilder.setSpan(new android.text.style.AccessibilityReplacementSpan(contentDescription), spanStart, spanEnd, spanFlags);
                }
            }
        }
        return spannableStringBuilder;
    }

    public java.lang.CharSequence getHintText() {
        return this.mHintText;
    }

    public void setHintText(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mHintText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public void setError(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mError = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public java.lang.CharSequence getError() {
        return this.mError;
    }

    public java.lang.CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public void setStateDescription(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mStateDescription = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public void setContentDescription(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mContentDescription = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public java.lang.CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public void setTooltipText(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mTooltipText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public void setLabelFor(android.view.View view) {
        setLabelFor(view, -1);
    }

    public void setLabelFor(android.view.View view, int i) {
        enforceNotSealed();
        this.mLabelForId = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public android.view.accessibility.AccessibilityNodeInfo getLabelFor() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabelForId);
    }

    public void setLabeledBy(android.view.View view) {
        setLabeledBy(view, -1);
    }

    public void setLabeledBy(android.view.View view, int i) {
        enforceNotSealed();
        this.mLabeledById = makeNodeId(view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, i);
    }

    public android.view.accessibility.AccessibilityNodeInfo getLabeledBy() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabeledById);
    }

    public void setViewIdResourceName(java.lang.String str) {
        enforceNotSealed();
        this.mViewIdResourceName = str;
    }

    public java.lang.String getViewIdResourceName() {
        return this.mViewIdResourceName;
    }

    public int getTextSelectionStart() {
        return this.mTextSelectionStart;
    }

    public int getTextSelectionEnd() {
        return this.mTextSelectionEnd;
    }

    public void setTextSelection(int i, int i2) {
        enforceNotSealed();
        this.mTextSelectionStart = i;
        this.mTextSelectionEnd = i2;
    }

    public int getInputType() {
        return this.mInputType;
    }

    public void setInputType(int i) {
        enforceNotSealed();
        this.mInputType = i;
    }

    public android.os.Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        return this.mExtras;
    }

    public boolean hasExtras() {
        return this.mExtras != null;
    }

    public android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo getTouchDelegateInfo() {
        if (this.mTouchDelegateInfo != null) {
            this.mTouchDelegateInfo.setConnectionId(this.mConnectionId);
            this.mTouchDelegateInfo.setWindowId(this.mWindowId);
        }
        return this.mTouchDelegateInfo;
    }

    public void setTouchDelegateInfo(android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo) {
        enforceNotSealed();
        this.mTouchDelegateInfo = touchDelegateInfo;
    }

    private boolean getBooleanProperty(int i) {
        return (i & this.mBooleanProperties) != 0;
    }

    private void setBooleanProperty(int i, boolean z) {
        enforceNotSealed();
        if (z) {
            this.mBooleanProperties = i | this.mBooleanProperties;
        } else {
            this.mBooleanProperties = (~i) & this.mBooleanProperties;
        }
    }

    public void setConnectionId(int i) {
        enforceNotSealed();
        this.mConnectionId = i;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setSourceNodeId(long j, int i) {
        enforceNotSealed();
        this.mSourceNodeId = j;
        this.mWindowId = i;
    }

    public long getSourceNodeId() {
        return this.mSourceNodeId;
    }

    public void setUniqueId(java.lang.String str) {
        enforceNotSealed();
        this.mUniqueId = str;
    }

    public java.lang.String getUniqueId() {
        return this.mUniqueId;
    }

    public void setContainerTitle(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mContainerTitle = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public java.lang.CharSequence getContainerTitle() {
        return this.mContainerTitle;
    }

    public void setLeashedParent(android.os.IBinder iBinder, int i) {
        enforceNotSealed();
        this.mLeashedParent = iBinder;
        this.mLeashedParentNodeId = makeNodeId(i, -1);
    }

    public android.os.IBinder getLeashedParent() {
        return this.mLeashedParent;
    }

    public long getLeashedParentNodeId() {
        return this.mLeashedParentNodeId;
    }

    public void setQueryFromAppProcessEnabled(android.view.View view, boolean z) {
        enforceNotSealed();
        if (z) {
            if (this.mConnectionId != -1) {
                return;
            }
            android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
            if (viewRootImpl == null) {
                throw new java.lang.IllegalStateException("Cannot link a node to a view that is not attached to a window.");
            }
            setConnectionId(viewRootImpl.getDirectAccessibilityConnectionId());
            return;
        }
        setConnectionId(-1);
    }

    public void setSealed(boolean z) {
        this.mSealed = z;
    }

    public boolean isSealed() {
        return this.mSealed;
    }

    private static boolean usingDirectConnection(int i) {
        return android.view.accessibility.AccessibilityInteractionClient.getConnection(i) instanceof android.view.accessibility.DirectAccessibilityConnection;
    }

    protected void enforceSealed() {
        if (!usingDirectConnection(this.mConnectionId) && !isSealed()) {
            throw new java.lang.IllegalStateException("Cannot perform this action on a not sealed instance.");
        }
    }

    private void enforceValidFocusDirection(int i) {
        switch (i) {
            case 1:
            case 2:
            case 17:
            case 33:
            case 66:
            case 130:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown direction: " + i);
        }
    }

    private void enforceValidFocusType(int i) {
        switch (i) {
            case 1:
            case 2:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown focus type: " + i);
        }
    }

    protected void enforceNotSealed() {
        if (isSealed()) {
            throw new java.lang.IllegalStateException("Cannot perform this action on a sealed instance.");
        }
    }

    @java.lang.Deprecated
    public static android.view.accessibility.AccessibilityNodeInfo obtain(android.view.View view) {
        return new android.view.accessibility.AccessibilityNodeInfo(view);
    }

    @java.lang.Deprecated
    public static android.view.accessibility.AccessibilityNodeInfo obtain(android.view.View view, int i) {
        return new android.view.accessibility.AccessibilityNodeInfo(view, i);
    }

    @java.lang.Deprecated
    public static android.view.accessibility.AccessibilityNodeInfo obtain() {
        return new android.view.accessibility.AccessibilityNodeInfo();
    }

    @java.lang.Deprecated
    public static android.view.accessibility.AccessibilityNodeInfo obtain(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        return new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @java.lang.Deprecated
    public void recycle() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelNoRecycle(parcel, i);
    }

    public void writeToParcelNoRecycle(android.os.Parcel parcel, int i) {
        long bitAt = isSealed() != DEFAULT.isSealed() ? com.android.internal.util.BitUtils.bitAt(0) | 0 : 0L;
        if (this.mSourceNodeId != DEFAULT.mSourceNodeId) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(1);
        }
        if (this.mWindowId != DEFAULT.mWindowId) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(2);
        }
        if (this.mParentNodeId != DEFAULT.mParentNodeId) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(3);
        }
        if (this.mLabelForId != DEFAULT.mLabelForId) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(4);
        }
        if (this.mLabeledById != DEFAULT.mLabeledById) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(5);
        }
        if (this.mTraversalBefore != DEFAULT.mTraversalBefore) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(6);
        }
        if (this.mTraversalAfter != DEFAULT.mTraversalAfter) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(7);
        }
        if (this.mMinDurationBetweenContentChanges != DEFAULT.mMinDurationBetweenContentChanges) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(8);
        }
        if (this.mConnectionId != DEFAULT.mConnectionId) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(9);
        }
        if (!android.util.LongArray.elementsEqual(this.mChildNodeIds, DEFAULT.mChildNodeIds)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(10);
        }
        if (!java.util.Objects.equals(this.mBoundsInParent, DEFAULT.mBoundsInParent)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(11);
        }
        if (!java.util.Objects.equals(this.mBoundsInScreen, DEFAULT.mBoundsInScreen)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(12);
        }
        if (!java.util.Objects.equals(this.mBoundsInWindow, DEFAULT.mBoundsInWindow)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(13);
        }
        if (!java.util.Objects.equals(this.mActions, DEFAULT.mActions)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(14);
        }
        if (this.mMaxTextLength != DEFAULT.mMaxTextLength) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(15);
        }
        if (this.mMovementGranularities != DEFAULT.mMovementGranularities) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(16);
        }
        if (this.mBooleanProperties != DEFAULT.mBooleanProperties) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(17);
        }
        if (!java.util.Objects.equals(this.mPackageName, DEFAULT.mPackageName)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(18);
        }
        if (!java.util.Objects.equals(this.mClassName, DEFAULT.mClassName)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(19);
        }
        if (!java.util.Objects.equals(this.mText, DEFAULT.mText)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(20);
        }
        if (!java.util.Objects.equals(this.mHintText, DEFAULT.mHintText)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(21);
        }
        if (!java.util.Objects.equals(this.mError, DEFAULT.mError)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(22);
        }
        if (!java.util.Objects.equals(this.mStateDescription, DEFAULT.mStateDescription)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(23);
        }
        if (!java.util.Objects.equals(this.mContentDescription, DEFAULT.mContentDescription)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(24);
        }
        if (!java.util.Objects.equals(this.mPaneTitle, DEFAULT.mPaneTitle)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(25);
        }
        if (!java.util.Objects.equals(this.mTooltipText, DEFAULT.mTooltipText)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(26);
        }
        if (!java.util.Objects.equals(this.mContainerTitle, DEFAULT.mContainerTitle)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(27);
        }
        if (!java.util.Objects.equals(this.mViewIdResourceName, DEFAULT.mViewIdResourceName)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(28);
        }
        if (!java.util.Objects.equals(this.mUniqueId, DEFAULT.mUniqueId)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(29);
        }
        if (this.mTextSelectionStart != DEFAULT.mTextSelectionStart) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(30);
        }
        if (this.mTextSelectionEnd != DEFAULT.mTextSelectionEnd) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(31);
        }
        if (this.mInputType != DEFAULT.mInputType) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(32);
        }
        if (this.mLiveRegion != DEFAULT.mLiveRegion) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(33);
        }
        if (this.mDrawingOrderInParent != DEFAULT.mDrawingOrderInParent) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(34);
        }
        if (!java.util.Objects.equals(this.mExtraDataKeys, DEFAULT.mExtraDataKeys)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(35);
        }
        if (!java.util.Objects.equals(this.mExtras, DEFAULT.mExtras)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(36);
        }
        if (!java.util.Objects.equals(this.mRangeInfo, DEFAULT.mRangeInfo)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(37);
        }
        if (!java.util.Objects.equals(this.mCollectionInfo, DEFAULT.mCollectionInfo)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(38);
        }
        if (!java.util.Objects.equals(this.mCollectionItemInfo, DEFAULT.mCollectionItemInfo)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(39);
        }
        if (!java.util.Objects.equals(this.mTouchDelegateInfo, DEFAULT.mTouchDelegateInfo)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(40);
        }
        if (!java.util.Objects.equals(this.mExtraRenderingInfo, DEFAULT.mExtraRenderingInfo)) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(41);
        }
        if (this.mLeashedChild != DEFAULT.mLeashedChild) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(42);
        }
        if (this.mLeashedParent != DEFAULT.mLeashedParent) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(43);
        }
        if (this.mLeashedParentNodeId != DEFAULT.mLeashedParentNodeId) {
            bitAt |= com.android.internal.util.BitUtils.bitAt(44);
        }
        parcel.writeLong(bitAt);
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 0)) {
            parcel.writeInt(isSealed() ? 1 : 0);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 1)) {
            parcel.writeLong(this.mSourceNodeId);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 2)) {
            parcel.writeInt(this.mWindowId);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 3)) {
            parcel.writeLong(this.mParentNodeId);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 4)) {
            parcel.writeLong(this.mLabelForId);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 5)) {
            parcel.writeLong(this.mLabeledById);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 6)) {
            parcel.writeLong(this.mTraversalBefore);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 7)) {
            parcel.writeLong(this.mTraversalAfter);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 8)) {
            parcel.writeLong(this.mMinDurationBetweenContentChanges);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 9)) {
            parcel.writeInt(this.mConnectionId);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 10)) {
            android.util.LongArray longArray = this.mChildNodeIds;
            if (longArray == null) {
                parcel.writeInt(0);
            } else {
                int size = longArray.size();
                parcel.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    parcel.writeLong(longArray.get(i2));
                }
            }
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 11)) {
            parcel.writeInt(this.mBoundsInParent.top);
            parcel.writeInt(this.mBoundsInParent.bottom);
            parcel.writeInt(this.mBoundsInParent.left);
            parcel.writeInt(this.mBoundsInParent.right);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 12)) {
            parcel.writeInt(this.mBoundsInScreen.top);
            parcel.writeInt(this.mBoundsInScreen.bottom);
            parcel.writeInt(this.mBoundsInScreen.left);
            parcel.writeInt(this.mBoundsInScreen.right);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 13)) {
            parcel.writeInt(this.mBoundsInWindow.top);
            parcel.writeInt(this.mBoundsInWindow.bottom);
            parcel.writeInt(this.mBoundsInWindow.left);
            parcel.writeInt(this.mBoundsInWindow.right);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 14)) {
            if (this.mActions != null && !this.mActions.isEmpty()) {
                int size2 = this.mActions.size();
                int i3 = 0;
                long j = 0;
                for (int i4 = 0; i4 < size2; i4++) {
                    android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction = this.mActions.get(i4);
                    if (isDefaultStandardAction(accessibilityAction)) {
                        j |= accessibilityAction.mSerializationFlag;
                    } else {
                        i3++;
                    }
                }
                parcel.writeLong(j);
                parcel.writeInt(i3);
                for (int i5 = 0; i5 < size2; i5++) {
                    android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction2 = this.mActions.get(i5);
                    if (!isDefaultStandardAction(accessibilityAction2)) {
                        accessibilityAction2.writeToParcel(parcel, i);
                    }
                }
            } else {
                parcel.writeLong(0L);
                parcel.writeInt(0);
            }
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 15)) {
            parcel.writeInt(this.mMaxTextLength);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 16)) {
            parcel.writeInt(this.mMovementGranularities);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 17)) {
            parcel.writeInt(this.mBooleanProperties);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 18)) {
            parcel.writeCharSequence(this.mPackageName);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 19)) {
            parcel.writeCharSequence(this.mClassName);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 20)) {
            parcel.writeCharSequence(this.mText);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 21)) {
            parcel.writeCharSequence(this.mHintText);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 22)) {
            parcel.writeCharSequence(this.mError);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 23)) {
            parcel.writeCharSequence(this.mStateDescription);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 24)) {
            parcel.writeCharSequence(this.mContentDescription);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 25)) {
            parcel.writeCharSequence(this.mPaneTitle);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 26)) {
            parcel.writeCharSequence(this.mTooltipText);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 27)) {
            parcel.writeCharSequence(this.mContainerTitle);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 28)) {
            parcel.writeString(this.mViewIdResourceName);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 29)) {
            parcel.writeString(this.mUniqueId);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 30)) {
            parcel.writeInt(this.mTextSelectionStart);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 31)) {
            parcel.writeInt(this.mTextSelectionEnd);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 32)) {
            parcel.writeInt(this.mInputType);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 33)) {
            parcel.writeInt(this.mLiveRegion);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 34)) {
            parcel.writeInt(this.mDrawingOrderInParent);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 35)) {
            parcel.writeStringList(this.mExtraDataKeys);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 36)) {
            parcel.writeBundle(this.mExtras);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 37)) {
            parcel.writeInt(this.mRangeInfo.getType());
            parcel.writeFloat(this.mRangeInfo.getMin());
            parcel.writeFloat(this.mRangeInfo.getMax());
            parcel.writeFloat(this.mRangeInfo.getCurrent());
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 38)) {
            parcel.writeInt(this.mCollectionInfo.getRowCount());
            parcel.writeInt(this.mCollectionInfo.getColumnCount());
            parcel.writeInt(this.mCollectionInfo.isHierarchical() ? 1 : 0);
            parcel.writeInt(this.mCollectionInfo.getSelectionMode());
            parcel.writeInt(this.mCollectionInfo.getItemCount());
            parcel.writeInt(this.mCollectionInfo.getImportantForAccessibilityItemCount());
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 39)) {
            parcel.writeString(this.mCollectionItemInfo.getRowTitle());
            parcel.writeInt(this.mCollectionItemInfo.getRowIndex());
            parcel.writeInt(this.mCollectionItemInfo.getRowSpan());
            parcel.writeString(this.mCollectionItemInfo.getColumnTitle());
            parcel.writeInt(this.mCollectionItemInfo.getColumnIndex());
            parcel.writeInt(this.mCollectionItemInfo.getColumnSpan());
            parcel.writeInt(this.mCollectionItemInfo.isHeading() ? 1 : 0);
            parcel.writeInt(this.mCollectionItemInfo.isSelected() ? 1 : 0);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 40)) {
            this.mTouchDelegateInfo.writeToParcel(parcel, i);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 41)) {
            parcel.writeValue(this.mExtraRenderingInfo.getLayoutSize());
            parcel.writeFloat(this.mExtraRenderingInfo.getTextSizeInPx());
            parcel.writeInt(this.mExtraRenderingInfo.getTextSizeUnit());
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 42)) {
            parcel.writeStrongBinder(this.mLeashedChild);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 43)) {
            parcel.writeStrongBinder(this.mLeashedParent);
        }
        if (com.android.internal.util.BitUtils.isBitSet(bitAt, 44)) {
            parcel.writeLong(this.mLeashedParentNodeId);
        }
    }

    private void init(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mSealed = accessibilityNodeInfo.mSealed;
        this.mSourceNodeId = accessibilityNodeInfo.mSourceNodeId;
        this.mParentNodeId = accessibilityNodeInfo.mParentNodeId;
        this.mLabelForId = accessibilityNodeInfo.mLabelForId;
        this.mLabeledById = accessibilityNodeInfo.mLabeledById;
        this.mTraversalBefore = accessibilityNodeInfo.mTraversalBefore;
        this.mTraversalAfter = accessibilityNodeInfo.mTraversalAfter;
        this.mMinDurationBetweenContentChanges = accessibilityNodeInfo.mMinDurationBetweenContentChanges;
        this.mWindowId = accessibilityNodeInfo.mWindowId;
        this.mConnectionId = accessibilityNodeInfo.mConnectionId;
        this.mUniqueId = accessibilityNodeInfo.mUniqueId;
        this.mBoundsInParent.set(accessibilityNodeInfo.mBoundsInParent);
        this.mBoundsInScreen.set(accessibilityNodeInfo.mBoundsInScreen);
        this.mBoundsInWindow.set(accessibilityNodeInfo.mBoundsInWindow);
        this.mPackageName = accessibilityNodeInfo.mPackageName;
        this.mClassName = accessibilityNodeInfo.mClassName;
        this.mText = accessibilityNodeInfo.mText;
        this.mOriginalText = accessibilityNodeInfo.mOriginalText;
        this.mHintText = accessibilityNodeInfo.mHintText;
        this.mError = accessibilityNodeInfo.mError;
        this.mStateDescription = accessibilityNodeInfo.mStateDescription;
        this.mContentDescription = accessibilityNodeInfo.mContentDescription;
        this.mPaneTitle = accessibilityNodeInfo.mPaneTitle;
        this.mTooltipText = accessibilityNodeInfo.mTooltipText;
        this.mContainerTitle = accessibilityNodeInfo.mContainerTitle;
        this.mViewIdResourceName = accessibilityNodeInfo.mViewIdResourceName;
        if (this.mActions != null) {
            this.mActions.clear();
        }
        java.util.ArrayList<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> arrayList = accessibilityNodeInfo.mActions;
        if (arrayList != null && arrayList.size() > 0) {
            if (this.mActions == null) {
                this.mActions = new java.util.ArrayList<>(arrayList);
            } else {
                this.mActions.addAll(accessibilityNodeInfo.mActions);
            }
        }
        this.mBooleanProperties = accessibilityNodeInfo.mBooleanProperties;
        this.mMaxTextLength = accessibilityNodeInfo.mMaxTextLength;
        this.mMovementGranularities = accessibilityNodeInfo.mMovementGranularities;
        if (this.mChildNodeIds != null) {
            this.mChildNodeIds.clear();
        }
        android.util.LongArray longArray = accessibilityNodeInfo.mChildNodeIds;
        if (longArray != null && longArray.size() > 0) {
            if (this.mChildNodeIds == null) {
                this.mChildNodeIds = longArray.m4813clone();
            } else {
                this.mChildNodeIds.addAll(longArray);
            }
        }
        this.mTextSelectionStart = accessibilityNodeInfo.mTextSelectionStart;
        this.mTextSelectionEnd = accessibilityNodeInfo.mTextSelectionEnd;
        this.mInputType = accessibilityNodeInfo.mInputType;
        this.mLiveRegion = accessibilityNodeInfo.mLiveRegion;
        this.mDrawingOrderInParent = accessibilityNodeInfo.mDrawingOrderInParent;
        this.mExtraDataKeys = accessibilityNodeInfo.mExtraDataKeys;
        this.mExtras = accessibilityNodeInfo.mExtras != null ? new android.os.Bundle(accessibilityNodeInfo.mExtras) : null;
        initCopyInfos(accessibilityNodeInfo);
        android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo = accessibilityNodeInfo.mTouchDelegateInfo;
        this.mTouchDelegateInfo = touchDelegateInfo != null ? new android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo(touchDelegateInfo.mTargetMap, true) : null;
        this.mLeashedChild = accessibilityNodeInfo.mLeashedChild;
        this.mLeashedParent = accessibilityNodeInfo.mLeashedParent;
        this.mLeashedParentNodeId = accessibilityNodeInfo.mLeashedParentNodeId;
    }

    private void initCopyInfos(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        android.view.accessibility.AccessibilityNodeInfo.RangeInfo rangeInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionInfo collectionInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo build;
        android.view.accessibility.AccessibilityNodeInfo.RangeInfo rangeInfo2 = accessibilityNodeInfo.mRangeInfo;
        android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo = null;
        byte b = 0;
        if (rangeInfo2 == null) {
            rangeInfo = null;
        } else {
            rangeInfo = new android.view.accessibility.AccessibilityNodeInfo.RangeInfo(rangeInfo2.mType, rangeInfo2.mMin, rangeInfo2.mMax, rangeInfo2.mCurrent);
        }
        this.mRangeInfo = rangeInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionInfo collectionInfo2 = accessibilityNodeInfo.mCollectionInfo;
        if (collectionInfo2 == null) {
            collectionInfo = null;
        } else {
            collectionInfo = new android.view.accessibility.AccessibilityNodeInfo.CollectionInfo(collectionInfo2.mRowCount, collectionInfo2.mColumnCount, collectionInfo2.mHierarchical, collectionInfo2.mSelectionMode, collectionInfo2.mItemCount, collectionInfo2.mImportantForAccessibilityItemCount);
        }
        this.mCollectionInfo = collectionInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = accessibilityNodeInfo.mCollectionItemInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder builder = new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder();
        if (collectionItemInfo == null) {
            build = null;
        } else {
            build = builder.setRowTitle(collectionItemInfo.mRowTitle).setRowIndex(collectionItemInfo.mRowIndex).setRowSpan(collectionItemInfo.mRowSpan).setColumnTitle(collectionItemInfo.mColumnTitle).setColumnIndex(collectionItemInfo.mColumnIndex).setColumnSpan(collectionItemInfo.mColumnSpan).setHeading(collectionItemInfo.mHeading).setSelected(collectionItemInfo.mSelected).build();
        }
        this.mCollectionItemInfo = build;
        android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo2 = accessibilityNodeInfo.mExtraRenderingInfo;
        if (extraRenderingInfo2 != null) {
            extraRenderingInfo = new android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo(extraRenderingInfo2);
        }
        this.mExtraRenderingInfo = extraRenderingInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(android.os.Parcel parcel) {
        boolean z;
        java.util.ArrayList<java.lang.String> arrayList;
        android.os.Bundle bundle;
        android.view.accessibility.AccessibilityNodeInfo.RangeInfo rangeInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionInfo collectionInfo;
        android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo;
        long readLong = parcel.readLong();
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 0)) {
            z = parcel.readInt() == 1;
        } else {
            z = DEFAULT.mSealed;
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 1)) {
            this.mSourceNodeId = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 2)) {
            this.mWindowId = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 3)) {
            this.mParentNodeId = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 4)) {
            this.mLabelForId = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 5)) {
            this.mLabeledById = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 6)) {
            this.mTraversalBefore = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 7)) {
            this.mTraversalAfter = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 8)) {
            this.mMinDurationBetweenContentChanges = parcel.readLong();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 9)) {
            this.mConnectionId = parcel.readInt();
        }
        android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo = null;
        byte b = 0;
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 10)) {
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.mChildNodeIds = new android.util.LongArray(readInt);
                for (int i = 0; i < readInt; i++) {
                    this.mChildNodeIds.add(parcel.readLong());
                }
            } else {
                this.mChildNodeIds = null;
            }
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 11)) {
            this.mBoundsInParent.top = parcel.readInt();
            this.mBoundsInParent.bottom = parcel.readInt();
            this.mBoundsInParent.left = parcel.readInt();
            this.mBoundsInParent.right = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 12)) {
            this.mBoundsInScreen.top = parcel.readInt();
            this.mBoundsInScreen.bottom = parcel.readInt();
            this.mBoundsInScreen.left = parcel.readInt();
            this.mBoundsInScreen.right = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 13)) {
            this.mBoundsInWindow.top = parcel.readInt();
            this.mBoundsInWindow.bottom = parcel.readInt();
            this.mBoundsInWindow.left = parcel.readInt();
            this.mBoundsInWindow.right = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 14)) {
            addStandardActions(parcel.readLong());
            int readInt2 = parcel.readInt();
            for (int i2 = 0; i2 < readInt2; i2++) {
                addActionUnchecked(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.CREATOR.createFromParcel(parcel));
            }
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 15)) {
            this.mMaxTextLength = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 16)) {
            this.mMovementGranularities = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 17)) {
            this.mBooleanProperties = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 18)) {
            this.mPackageName = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 19)) {
            this.mClassName = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 20)) {
            this.mText = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 21)) {
            this.mHintText = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 22)) {
            this.mError = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 23)) {
            this.mStateDescription = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 24)) {
            this.mContentDescription = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 25)) {
            this.mPaneTitle = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 26)) {
            this.mTooltipText = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 27)) {
            this.mContainerTitle = parcel.readCharSequence();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 28)) {
            this.mViewIdResourceName = parcel.readString();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 29)) {
            this.mUniqueId = parcel.readString();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 30)) {
            this.mTextSelectionStart = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 31)) {
            this.mTextSelectionEnd = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 32)) {
            this.mInputType = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 33)) {
            this.mLiveRegion = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 34)) {
            this.mDrawingOrderInParent = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 35)) {
            arrayList = parcel.createStringArrayList();
        } else {
            arrayList = null;
        }
        this.mExtraDataKeys = arrayList;
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 36)) {
            bundle = parcel.readBundle();
        } else {
            bundle = null;
        }
        this.mExtras = bundle;
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 37)) {
            rangeInfo = new android.view.accessibility.AccessibilityNodeInfo.RangeInfo(parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
        } else {
            rangeInfo = null;
        }
        this.mRangeInfo = rangeInfo;
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 38)) {
            collectionInfo = new android.view.accessibility.AccessibilityNodeInfo.CollectionInfo(parcel.readInt(), parcel.readInt(), parcel.readInt() == 1, parcel.readInt(), parcel.readInt(), parcel.readInt());
        } else {
            collectionInfo = null;
        }
        this.mCollectionInfo = collectionInfo;
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 39)) {
            collectionItemInfo = new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 1, parcel.readInt() == 1);
        } else {
            collectionItemInfo = null;
        }
        this.mCollectionItemInfo = collectionItemInfo;
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 40)) {
            this.mTouchDelegateInfo = android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo.CREATOR.createFromParcel(parcel);
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 41)) {
            this.mExtraRenderingInfo = new android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo(extraRenderingInfo);
            this.mExtraRenderingInfo.mLayoutSize = (android.util.Size) parcel.readValue(null);
            this.mExtraRenderingInfo.mTextSizeInPx = parcel.readFloat();
            this.mExtraRenderingInfo.mTextSizeUnit = parcel.readInt();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 42)) {
            this.mLeashedChild = parcel.readStrongBinder();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 43)) {
            this.mLeashedParent = parcel.readStrongBinder();
        }
        if (com.android.internal.util.BitUtils.isBitSet(readLong, 44)) {
            this.mLeashedParentNodeId = parcel.readLong();
        }
        this.mSealed = z;
    }

    private void clear() {
        init(DEFAULT);
    }

    private static boolean isDefaultStandardAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        return accessibilityAction.mSerializationFlag != -1 && android.text.TextUtils.isEmpty(accessibilityAction.getLabel());
    }

    private static android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction getActionSingleton(int i) {
        int size = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.sStandardActions.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction valueAt = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.sStandardActions.valueAt(i2);
            if (i == valueAt.getId()) {
                return valueAt;
            }
        }
        return null;
    }

    private static android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction getActionSingletonBySerializationFlag(long j) {
        int size = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.sStandardActions.size();
        for (int i = 0; i < size; i++) {
            android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction valueAt = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.sStandardActions.valueAt(i);
            if (j == valueAt.mSerializationFlag) {
                return valueAt;
            }
        }
        return null;
    }

    private void addStandardActions(long j) {
        while (j > 0) {
            long numberOfTrailingZeros = 1 << java.lang.Long.numberOfTrailingZeros(j);
            j &= ~numberOfTrailingZeros;
            addAction(getActionSingletonBySerializationFlag(numberOfTrailingZeros));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getActionSymbolicName(int i) {
        switch (i) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            case 262144:
                return "ACTION_EXPAND";
            case 524288:
                return "ACTION_COLLAPSE";
            case 1048576:
                return "ACTION_DISMISS";
            case 2097152:
                return "ACTION_SET_TEXT";
            case 16908342:
                return "ACTION_SHOW_ON_SCREEN";
            case 16908343:
                return "ACTION_SCROLL_TO_POSITION";
            case 16908344:
                return "ACTION_SCROLL_UP";
            case 16908345:
                return "ACTION_SCROLL_LEFT";
            case 16908346:
                return "ACTION_SCROLL_DOWN";
            case 16908347:
                return "ACTION_SCROLL_RIGHT";
            case 16908348:
                return "ACTION_CONTEXT_CLICK";
            case 16908349:
                return "ACTION_SET_PROGRESS";
            case 16908356:
                return "ACTION_SHOW_TOOLTIP";
            case 16908357:
                return "ACTION_HIDE_TOOLTIP";
            case 16908358:
                return "ACTION_PAGE_UP";
            case 16908359:
                return "ACTION_PAGE_DOWN";
            case 16908360:
                return "ACTION_PAGE_LEFT";
            case 16908361:
                return "ACTION_PAGE_RIGHT";
            case 16908362:
                return "ACTION_PRESS_AND_HOLD";
            case 16908372:
                return "ACTION_IME_ENTER";
            case 16908373:
                return "ACTION_DRAG";
            case 16908374:
                return "ACTION_DROP";
            case 16908375:
                return "ACTION_CANCEL_DRAG";
            default:
                if (i == 16908376) {
                    return "ACTION_SHOW_TEXT_SUGGESTIONS";
                }
                if (i == 16908382) {
                    return "ACTION_SCROLL_IN_DIRECTION";
                }
                return "ACTION_UNKNOWN";
        }
    }

    private static java.lang.String getMovementGranularitySymbolicName(int i) {
        switch (i) {
            case 1:
                return "MOVEMENT_GRANULARITY_CHARACTER";
            case 2:
                return "MOVEMENT_GRANULARITY_WORD";
            case 4:
                return "MOVEMENT_GRANULARITY_LINE";
            case 8:
                return "MOVEMENT_GRANULARITY_PARAGRAPH";
            case 16:
                return "MOVEMENT_GRANULARITY_PAGE";
            default:
                throw new java.lang.IllegalArgumentException("Unknown movement granularity: " + i);
        }
    }

    private static boolean canPerformRequestOverConnection(int i, int i2, long j) {
        return ((!usingDirectConnection(i) && !(i2 != -1)) || getAccessibilityViewId(j) == Integer.MAX_VALUE || i == -1) ? false : true;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = (android.view.accessibility.AccessibilityNodeInfo) obj;
        if (this.mSourceNodeId == accessibilityNodeInfo.mSourceNodeId && this.mWindowId == accessibilityNodeInfo.mWindowId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((getAccessibilityViewId(this.mSourceNodeId) + 31) * 31) + getVirtualDescendantId(this.mSourceNodeId)) * 31) + this.mWindowId;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(super.toString());
        if (DEBUG) {
            sb.append("; sourceNodeId: 0x").append(java.lang.Long.toHexString(this.mSourceNodeId));
            sb.append("; windowId: 0x").append(java.lang.Long.toHexString(this.mWindowId));
            sb.append("; accessibilityViewId: 0x").append(java.lang.Long.toHexString(getAccessibilityViewId(this.mSourceNodeId)));
            sb.append("; virtualDescendantId: 0x").append(java.lang.Long.toHexString(getVirtualDescendantId(this.mSourceNodeId)));
            sb.append("; mParentNodeId: 0x").append(java.lang.Long.toHexString(this.mParentNodeId));
            sb.append("; traversalBefore: 0x").append(java.lang.Long.toHexString(this.mTraversalBefore));
            sb.append("; traversalAfter: 0x").append(java.lang.Long.toHexString(this.mTraversalAfter));
            sb.append("; minDurationBetweenContentChanges: ").append(this.mMinDurationBetweenContentChanges);
            int i = this.mMovementGranularities;
            sb.append("; MovementGranularities: [");
            while (i != 0) {
                int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
                i &= ~numberOfTrailingZeros;
                sb.append(getMovementGranularitySymbolicName(numberOfTrailingZeros));
                if (i != 0) {
                    sb.append(", ");
                }
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            sb.append("; childAccessibilityIds: [");
            android.util.LongArray longArray = this.mChildNodeIds;
            if (longArray != null) {
                int size = longArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    sb.append("0x").append(java.lang.Long.toHexString(longArray.get(i2)));
                    if (i2 < size - 1) {
                        sb.append(", ");
                    }
                }
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        sb.append("; boundsInParent: ").append(this.mBoundsInParent);
        sb.append("; boundsInScreen: ").append(this.mBoundsInScreen);
        sb.append("; boundsInWindow: ").append(this.mBoundsInScreen);
        sb.append("; packageName: ").append(this.mPackageName);
        sb.append("; className: ").append(this.mClassName);
        sb.append("; text: ").append(this.mText);
        sb.append("; error: ").append(this.mError);
        sb.append("; maxTextLength: ").append(this.mMaxTextLength);
        sb.append("; stateDescription: ").append(this.mStateDescription);
        sb.append("; contentDescription: ").append(this.mContentDescription);
        sb.append("; tooltipText: ").append(this.mTooltipText);
        sb.append("; containerTitle: ").append(this.mContainerTitle);
        sb.append("; viewIdResName: ").append(this.mViewIdResourceName);
        sb.append("; uniqueId: ").append(this.mUniqueId);
        sb.append("; checkable: ").append(isCheckable());
        sb.append("; checked: ").append(isChecked());
        sb.append("; focusable: ").append(isFocusable());
        sb.append("; focused: ").append(isFocused());
        sb.append("; selected: ").append(isSelected());
        sb.append("; clickable: ").append(isClickable());
        sb.append("; longClickable: ").append(isLongClickable());
        sb.append("; contextClickable: ").append(isContextClickable());
        sb.append("; enabled: ").append(isEnabled());
        sb.append("; password: ").append(isPassword());
        sb.append("; scrollable: ").append(isScrollable());
        sb.append("; granularScrollingSupported: ").append(isGranularScrollingSupported());
        sb.append("; importantForAccessibility: ").append(isImportantForAccessibility());
        sb.append("; visible: ").append(isVisibleToUser());
        sb.append("; actions: ").append(this.mActions);
        sb.append("; isTextSelectable: ").append(isTextSelectable());
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.accessibility.AccessibilityNodeInfo getNodeForAccessibilityId(int i, int i2, long j) {
        return getNodeForAccessibilityId(i, i2, j, 7);
    }

    private static android.view.accessibility.AccessibilityNodeInfo getNodeForAccessibilityId(int i, int i2, long j, int i3) {
        if (!canPerformRequestOverConnection(i, i2, j)) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(i, i2, j, false, i3, (android.os.Bundle) null);
    }

    private static android.view.accessibility.AccessibilityNodeInfo getNodeForAccessibilityId(int i, android.os.IBinder iBinder, long j) {
        return getNodeForAccessibilityId(i, iBinder, j, 7);
    }

    private static android.view.accessibility.AccessibilityNodeInfo getNodeForAccessibilityId(int i, android.os.IBinder iBinder, long j, int i2) {
        if (iBinder == null || getAccessibilityViewId(j) == Integer.MAX_VALUE || i == -1) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(i, iBinder, j, false, i2, (android.os.Bundle) null);
    }

    public static java.lang.String idToString(long j) {
        int accessibilityViewId = getAccessibilityViewId(j);
        int virtualDescendantId = getVirtualDescendantId(j);
        if (virtualDescendantId == -1) {
            return idItemToString(accessibilityViewId);
        }
        return idItemToString(accessibilityViewId) + ":" + idItemToString(virtualDescendantId);
    }

    private static java.lang.String idItemToString(int i) {
        switch (i) {
            case -1:
                return "HOST";
            case 2147483646:
                return "ROOT";
            case Integer.MAX_VALUE:
                return android.app.admin.DevicePolicyResources.UNDEFINED;
            default:
                return "" + i;
        }
    }

    public static final class AccessibilityAction implements android.os.Parcelable {
        private final int mActionId;
        private final java.lang.CharSequence mLabel;
        public long mSerializationFlag;
        public static final android.util.ArraySet<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> sStandardActions = new android.util.ArraySet<>();
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_FOCUS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(1);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_CLEAR_FOCUS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(2);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SELECT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(4);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_CLEAR_SELECTION = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(8);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_CLICK = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_LONG_CLICK = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(32);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_ACCESSIBILITY_FOCUS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(64);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_CLEAR_ACCESSIBILITY_FOCUS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(128);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(256);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(512);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_NEXT_HTML_ELEMENT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(1024);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PREVIOUS_HTML_ELEMENT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(2048);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_FORWARD = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(4096);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_BACKWARD = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(8192);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_COPY = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16384);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PASTE = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(32768);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_CUT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(65536);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SET_SELECTION = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(131072);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_EXPAND = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(262144);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_COLLAPSE = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(524288);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_DISMISS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(1048576);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SET_TEXT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(2097152);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SHOW_ON_SCREEN = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908342);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_TO_POSITION = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908343);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_IN_DIRECTION = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908382);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_UP = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908344);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_LEFT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908345);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_DOWN = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908346);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SCROLL_RIGHT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908347);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PAGE_UP = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908358);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PAGE_DOWN = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908359);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PAGE_LEFT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908360);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PAGE_RIGHT = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908361);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_CONTEXT_CLICK = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908348);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SET_PROGRESS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908349);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_MOVE_WINDOW = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908354);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SHOW_TOOLTIP = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908356);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_HIDE_TOOLTIP = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908357);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_PRESS_AND_HOLD = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908362);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_IME_ENTER = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908372);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_DRAG_START = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908373);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_DRAG_DROP = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908374);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_DRAG_CANCEL = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908375);
        public static final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction ACTION_SHOW_TEXT_SUGGESTIONS = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908376);
        public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction>() { // from class: android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction createFromParcel(android.os.Parcel parcel) {
                return new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction[] newArray(int i) {
                return new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction[i];
            }
        };

        public AccessibilityAction(int i, java.lang.CharSequence charSequence) {
            this.mSerializationFlag = -1L;
            this.mActionId = i;
            this.mLabel = charSequence;
        }

        private AccessibilityAction(int i) {
            this(i, (java.lang.CharSequence) null);
            this.mSerializationFlag = com.android.internal.util.BitUtils.bitAt(sStandardActions.size());
            sStandardActions.add(this);
        }

        public int getId() {
            return this.mActionId;
        }

        public java.lang.CharSequence getLabel() {
            return this.mLabel;
        }

        public int hashCode() {
            return this.mActionId;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (getClass() != obj.getClass() || this.mActionId != ((android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction) obj).mActionId) {
                return false;
            }
            return true;
        }

        public java.lang.String toString() {
            return "AccessibilityAction: " + android.view.accessibility.AccessibilityNodeInfo.getActionSymbolicName(this.mActionId) + " - " + ((java.lang.Object) this.mLabel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mActionId);
            parcel.writeCharSequence(this.mLabel);
        }

        private AccessibilityAction(android.os.Parcel parcel) {
            this.mSerializationFlag = -1L;
            this.mActionId = parcel.readInt();
            this.mLabel = parcel.readCharSequence();
        }
    }

    public static final class RangeInfo {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        private float mCurrent;
        private float mMax;
        private float mMin;
        private int mType;

        @java.lang.Deprecated
        public static android.view.accessibility.AccessibilityNodeInfo.RangeInfo obtain(int i, float f, float f2, float f3) {
            return new android.view.accessibility.AccessibilityNodeInfo.RangeInfo(i, f, f2, f3);
        }

        public RangeInfo(int i, float f, float f2, float f3) {
            this.mType = i;
            this.mMin = f;
            this.mMax = f2;
            this.mCurrent = f3;
        }

        public int getType() {
            return this.mType;
        }

        public float getMin() {
            return this.mMin;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getCurrent() {
            return this.mCurrent;
        }

        @java.lang.Deprecated
        void recycle() {
        }

        private void clear() {
            this.mType = 0;
            this.mMin = 0.0f;
            this.mMax = 0.0f;
            this.mCurrent = 0.0f;
        }
    }

    public static final class CollectionInfo {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        public static final int UNDEFINED = -1;
        private int mColumnCount;
        private boolean mHierarchical;
        private int mImportantForAccessibilityItemCount;
        private int mItemCount;
        private int mRowCount;
        private int mSelectionMode;

        public static android.view.accessibility.AccessibilityNodeInfo.CollectionInfo obtain(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo collectionInfo) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionInfo(collectionInfo.mRowCount, collectionInfo.mColumnCount, collectionInfo.mHierarchical, collectionInfo.mSelectionMode, collectionInfo.mItemCount, collectionInfo.mImportantForAccessibilityItemCount);
        }

        public static android.view.accessibility.AccessibilityNodeInfo.CollectionInfo obtain(int i, int i2, boolean z) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionInfo(i, i2, z, 0);
        }

        public static android.view.accessibility.AccessibilityNodeInfo.CollectionInfo obtain(int i, int i2, boolean z, int i3) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionInfo(i, i2, z, i3);
        }

        public CollectionInfo(int i, int i2, boolean z) {
            this(i, i2, z, 0);
        }

        public CollectionInfo(int i, int i2, boolean z, int i3) {
            this.mRowCount = i;
            this.mColumnCount = i2;
            this.mHierarchical = z;
            this.mSelectionMode = i3;
            this.mItemCount = -1;
            this.mImportantForAccessibilityItemCount = -1;
        }

        public CollectionInfo(int i, int i2, boolean z, int i3, int i4, int i5) {
            this.mRowCount = i;
            this.mColumnCount = i2;
            this.mHierarchical = z;
            this.mSelectionMode = i3;
            this.mItemCount = i4;
            this.mImportantForAccessibilityItemCount = i5;
        }

        public int getRowCount() {
            return this.mRowCount;
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public boolean isHierarchical() {
            return this.mHierarchical;
        }

        public int getSelectionMode() {
            return this.mSelectionMode;
        }

        public int getItemCount() {
            return this.mItemCount;
        }

        public int getImportantForAccessibilityItemCount() {
            return this.mImportantForAccessibilityItemCount;
        }

        @java.lang.Deprecated
        void recycle() {
        }

        private void clear() {
            this.mRowCount = 0;
            this.mColumnCount = 0;
            this.mHierarchical = false;
            this.mSelectionMode = 0;
            this.mItemCount = -1;
            this.mImportantForAccessibilityItemCount = -1;
        }

        public static final class Builder {
            private int mSelectionMode;
            private int mRowCount = 0;
            private int mColumnCount = 0;
            private boolean mHierarchical = false;
            private int mItemCount = -1;
            private int mImportantForAccessibilityItemCount = -1;

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.Builder setRowCount(int i) {
                this.mRowCount = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.Builder setColumnCount(int i) {
                this.mColumnCount = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.Builder setHierarchical(boolean z) {
                this.mHierarchical = z;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.Builder setSelectionMode(int i) {
                this.mSelectionMode = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.Builder setItemCount(int i) {
                this.mItemCount = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.Builder setImportantForAccessibilityItemCount(int i) {
                this.mImportantForAccessibilityItemCount = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionInfo build() {
                android.view.accessibility.AccessibilityNodeInfo.CollectionInfo collectionInfo = new android.view.accessibility.AccessibilityNodeInfo.CollectionInfo(this.mRowCount, this.mColumnCount, this.mHierarchical);
                collectionInfo.mSelectionMode = this.mSelectionMode;
                collectionInfo.mItemCount = this.mItemCount;
                collectionInfo.mImportantForAccessibilityItemCount = this.mImportantForAccessibilityItemCount;
                return collectionInfo;
            }
        }
    }

    public static final class CollectionItemInfo {
        private int mColumnIndex;
        private int mColumnSpan;
        private java.lang.String mColumnTitle;
        private boolean mHeading;
        private int mRowIndex;
        private int mRowSpan;
        private java.lang.String mRowTitle;
        private boolean mSelected;

        @java.lang.Deprecated
        public static android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo obtain(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo(collectionItemInfo.mRowTitle, collectionItemInfo.mRowIndex, collectionItemInfo.mRowSpan, collectionItemInfo.mColumnTitle, collectionItemInfo.mColumnIndex, collectionItemInfo.mColumnSpan, collectionItemInfo.mHeading, collectionItemInfo.mSelected);
        }

        @java.lang.Deprecated
        public static android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo obtain(int i, int i2, int i3, int i4, boolean z) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo(i, i2, i3, i4, z, false);
        }

        @java.lang.Deprecated
        public static android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo obtain(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo(i, i2, i3, i4, z, z2);
        }

        @java.lang.Deprecated
        public static android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo obtain(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4, boolean z, boolean z2) {
            return new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo(str, i, i2, str2, i3, i4, z, z2);
        }

        private CollectionItemInfo() {
        }

        public CollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
            this(i, i2, i3, i4, z, false);
        }

        public CollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            this(null, i, i2, null, i3, i4, z, z2);
        }

        public CollectionItemInfo(java.lang.String str, int i, int i2, java.lang.String str2, int i3, int i4, boolean z, boolean z2) {
            this.mRowIndex = i;
            this.mRowSpan = i2;
            this.mColumnIndex = i3;
            this.mColumnSpan = i4;
            this.mHeading = z;
            this.mSelected = z2;
            this.mRowTitle = str;
            this.mColumnTitle = str2;
        }

        public int getColumnIndex() {
            return this.mColumnIndex;
        }

        public int getRowIndex() {
            return this.mRowIndex;
        }

        public int getColumnSpan() {
            return this.mColumnSpan;
        }

        public int getRowSpan() {
            return this.mRowSpan;
        }

        public boolean isHeading() {
            return this.mHeading;
        }

        public boolean isSelected() {
            return this.mSelected;
        }

        public java.lang.String getRowTitle() {
            return this.mRowTitle;
        }

        public java.lang.String getColumnTitle() {
            return this.mColumnTitle;
        }

        @java.lang.Deprecated
        void recycle() {
        }

        private void clear() {
            this.mColumnIndex = 0;
            this.mColumnSpan = 0;
            this.mRowIndex = 0;
            this.mRowSpan = 0;
            this.mHeading = false;
            this.mSelected = false;
            this.mRowTitle = null;
            this.mColumnTitle = null;
        }

        public static final class Builder {
            private int mColumnIndex;
            private int mColumnSpan;
            private java.lang.String mColumnTitle;
            private boolean mHeading;
            private int mRowIndex;
            private int mRowSpan;
            private java.lang.String mRowTitle;
            private boolean mSelected;

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setHeading(boolean z) {
                this.mHeading = z;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setColumnIndex(int i) {
                this.mColumnIndex = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setRowIndex(int i) {
                this.mRowIndex = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setColumnSpan(int i) {
                this.mColumnSpan = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setRowSpan(int i) {
                this.mRowSpan = i;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setSelected(boolean z) {
                this.mSelected = z;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setRowTitle(java.lang.String str) {
                this.mRowTitle = str;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.Builder setColumnTitle(java.lang.String str) {
                this.mColumnTitle = str;
                return this;
            }

            public android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo build() {
                android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = new android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo();
                collectionItemInfo.mHeading = this.mHeading;
                collectionItemInfo.mColumnIndex = this.mColumnIndex;
                collectionItemInfo.mRowIndex = this.mRowIndex;
                collectionItemInfo.mColumnSpan = this.mColumnSpan;
                collectionItemInfo.mRowSpan = this.mRowSpan;
                collectionItemInfo.mSelected = this.mSelected;
                collectionItemInfo.mRowTitle = this.mRowTitle;
                collectionItemInfo.mColumnTitle = this.mColumnTitle;
                return collectionItemInfo;
            }
        }
    }

    public static final class TouchDelegateInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo> CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo>() { // from class: android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo createFromParcel(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt == 0) {
                    return null;
                }
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(readInt);
                for (int i = 0; i < readInt; i++) {
                    arrayMap.put(android.graphics.Region.CREATOR.createFromParcel(parcel), java.lang.Long.valueOf(parcel.readLong()));
                }
                return new android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo(arrayMap, false);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo[] newArray(int i) {
                return new android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo[i];
            }
        };
        private int mConnectionId;
        private android.util.ArrayMap<android.graphics.Region, java.lang.Long> mTargetMap;
        private int mWindowId;

        public TouchDelegateInfo(java.util.Map<android.graphics.Region, android.view.View> map) {
            com.android.internal.util.Preconditions.checkArgument((map.isEmpty() || map.containsKey(null) || map.containsValue(null)) ? false : true);
            this.mTargetMap = new android.util.ArrayMap<>(map.size());
            java.util.Iterator<android.graphics.Region> it = map.keySet().iterator();
            while (it.hasNext()) {
                this.mTargetMap.put(it.next(), java.lang.Long.valueOf(map.get(r1).getAccessibilityViewId()));
            }
        }

        TouchDelegateInfo(android.util.ArrayMap<android.graphics.Region, java.lang.Long> arrayMap, boolean z) {
            com.android.internal.util.Preconditions.checkArgument((arrayMap.isEmpty() || arrayMap.containsKey(null) || arrayMap.containsValue(null)) ? false : true);
            if (z) {
                this.mTargetMap = new android.util.ArrayMap<>(arrayMap.size());
                this.mTargetMap.putAll((android.util.ArrayMap<? extends android.graphics.Region, ? extends java.lang.Long>) arrayMap);
            } else {
                this.mTargetMap = arrayMap;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConnectionId(int i) {
            this.mConnectionId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setWindowId(int i) {
            this.mWindowId = i;
        }

        public int getRegionCount() {
            return this.mTargetMap.size();
        }

        public android.graphics.Region getRegionAt(int i) {
            return this.mTargetMap.keyAt(i);
        }

        public android.view.accessibility.AccessibilityNodeInfo getTargetForRegion(android.graphics.Region region) {
            return android.view.accessibility.AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTargetMap.get(region).longValue());
        }

        public long getAccessibilityIdForRegion(android.graphics.Region region) {
            return this.mTargetMap.get(region).longValue();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mTargetMap.size());
            for (int i2 = 0; i2 < this.mTargetMap.size(); i2++) {
                android.graphics.Region keyAt = this.mTargetMap.keyAt(i2);
                java.lang.Long valueAt = this.mTargetMap.valueAt(i2);
                keyAt.writeToParcel(parcel, i);
                parcel.writeLong(valueAt.longValue());
            }
        }
    }

    public static final class ExtraRenderingInfo {
        private static final int UNDEFINED_VALUE = -1;
        private android.util.Size mLayoutSize;
        private float mTextSizeInPx;
        private int mTextSizeUnit;

        @java.lang.Deprecated
        public static android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo obtain() {
            return new android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo(null);
        }

        @java.lang.Deprecated
        private static android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo obtain(android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo) {
            return new android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo(extraRenderingInfo);
        }

        private ExtraRenderingInfo(android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo extraRenderingInfo) {
            this.mTextSizeInPx = -1.0f;
            this.mTextSizeUnit = -1;
            if (extraRenderingInfo != null) {
                this.mLayoutSize = extraRenderingInfo.mLayoutSize;
                this.mTextSizeInPx = extraRenderingInfo.mTextSizeInPx;
                this.mTextSizeUnit = extraRenderingInfo.mTextSizeUnit;
            }
        }

        public android.util.Size getLayoutSize() {
            return this.mLayoutSize;
        }

        public void setLayoutSize(int i, int i2) {
            this.mLayoutSize = new android.util.Size(i, i2);
        }

        public float getTextSizeInPx() {
            return this.mTextSizeInPx;
        }

        public void setTextSizeInPx(float f) {
            this.mTextSizeInPx = f;
        }

        public int getTextSizeUnit() {
            return this.mTextSizeUnit;
        }

        public void setTextSizeUnit(int i) {
            this.mTextSizeUnit = i;
        }

        @java.lang.Deprecated
        void recycle() {
        }

        private void clear() {
            this.mLayoutSize = null;
            this.mTextSizeInPx = -1.0f;
            this.mTextSizeUnit = -1;
        }
    }
}
