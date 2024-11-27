package android.view.accessibility;

/* loaded from: classes4.dex */
public class AccessibilityRecord {
    protected static final boolean DEBUG_CONCISE_TOSTRING = false;
    private static final int GET_SOURCE_PREFETCH_FLAGS = 7;
    private static final int PROPERTY_ACCESSIBILITY_DATA_SENSITIVE = 1024;
    private static final int PROPERTY_CHECKED = 1;
    private static final int PROPERTY_ENABLED = 2;
    private static final int PROPERTY_FULL_SCREEN = 128;
    private static final int PROPERTY_IMPORTANT_FOR_ACCESSIBILITY = 512;
    private static final int PROPERTY_PASSWORD = 4;
    private static final int PROPERTY_SCROLLABLE = 256;
    private static final int UNDEFINED = -1;
    java.lang.CharSequence mBeforeText;
    java.lang.CharSequence mClassName;
    java.lang.CharSequence mContentDescription;
    android.os.Parcelable mParcelableData;
    boolean mSealed;
    int mBooleanProperties = 0;
    int mCurrentItemIndex = -1;
    int mItemCount = -1;
    int mFromIndex = -1;
    int mToIndex = -1;
    int mScrollX = 0;
    int mScrollY = 0;
    int mScrollDeltaX = -1;
    int mScrollDeltaY = -1;
    int mMaxScrollX = 0;
    int mMaxScrollY = 0;
    int mAddedCount = -1;
    int mRemovedCount = -1;
    long mSourceNodeId = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    int mSourceWindowId = -1;
    int mSourceDisplayId = -1;
    final java.util.List<java.lang.CharSequence> mText = new java.util.ArrayList();
    int mConnectionId = -1;

    public AccessibilityRecord() {
    }

    public AccessibilityRecord(android.view.accessibility.AccessibilityRecord accessibilityRecord) {
        init(accessibilityRecord);
    }

    public void setSource(android.view.View view) {
        setSource(view, -1);
    }

    public void setSource(android.view.View view, int i) {
        boolean z;
        int i2;
        enforceNotSealed();
        this.mSourceWindowId = -1;
        if (view == null) {
            z = true;
            i2 = Integer.MAX_VALUE;
        } else {
            z = view.isImportantForAccessibility();
            i2 = view.getAccessibilityViewId();
            this.mSourceWindowId = view.getAccessibilityWindowId();
            setBooleanProperty(1024, view.isAccessibilityDataSensitive());
        }
        setBooleanProperty(512, z);
        this.mSourceNodeId = android.view.accessibility.AccessibilityNodeInfo.makeNodeId(i2, i);
    }

    public void setSourceNodeId(long j) {
        this.mSourceNodeId = j;
    }

    public android.view.accessibility.AccessibilityNodeInfo getSource() {
        return getSource(7);
    }

    public android.view.accessibility.AccessibilityNodeInfo getSource(int i) {
        enforceSealed();
        if (this.mConnectionId == -1 || this.mSourceWindowId == -1 || android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(this.mSourceNodeId) == Integer.MAX_VALUE) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mSourceWindowId, this.mSourceNodeId, false, i, (android.os.Bundle) null);
    }

    public void setDisplayId(int i) {
        this.mSourceDisplayId = i;
    }

    public int getDisplayId() {
        return this.mSourceDisplayId;
    }

    public void setWindowId(int i) {
        this.mSourceWindowId = i;
    }

    public int getWindowId() {
        return this.mSourceWindowId;
    }

    public boolean isChecked() {
        return getBooleanProperty(1);
    }

    public void setChecked(boolean z) {
        enforceNotSealed();
        setBooleanProperty(1, z);
    }

    public boolean isEnabled() {
        return getBooleanProperty(2);
    }

    public void setEnabled(boolean z) {
        enforceNotSealed();
        setBooleanProperty(2, z);
    }

    public boolean isPassword() {
        return getBooleanProperty(4);
    }

    public void setPassword(boolean z) {
        enforceNotSealed();
        setBooleanProperty(4, z);
    }

    public boolean isFullScreen() {
        return getBooleanProperty(128);
    }

    public void setFullScreen(boolean z) {
        enforceNotSealed();
        setBooleanProperty(128, z);
    }

    public boolean isScrollable() {
        return getBooleanProperty(256);
    }

    public void setScrollable(boolean z) {
        enforceNotSealed();
        setBooleanProperty(256, z);
    }

    public boolean isImportantForAccessibility() {
        return getBooleanProperty(512);
    }

    public void setImportantForAccessibility(boolean z) {
        enforceNotSealed();
        setBooleanProperty(512, z);
    }

    boolean isAccessibilityDataSensitive() {
        return getBooleanProperty(1024);
    }

    void setAccessibilityDataSensitive(boolean z) {
        enforceNotSealed();
        setBooleanProperty(1024, z);
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public void setItemCount(int i) {
        enforceNotSealed();
        this.mItemCount = i;
    }

    public int getCurrentItemIndex() {
        return this.mCurrentItemIndex;
    }

    public void setCurrentItemIndex(int i) {
        enforceNotSealed();
        this.mCurrentItemIndex = i;
    }

    public int getFromIndex() {
        return this.mFromIndex;
    }

    public void setFromIndex(int i) {
        enforceNotSealed();
        this.mFromIndex = i;
    }

    public int getToIndex() {
        return this.mToIndex;
    }

    public void setToIndex(int i) {
        enforceNotSealed();
        this.mToIndex = i;
    }

    public int getScrollX() {
        return this.mScrollX;
    }

    public void setScrollX(int i) {
        enforceNotSealed();
        this.mScrollX = i;
    }

    public int getScrollY() {
        return this.mScrollY;
    }

    public void setScrollY(int i) {
        enforceNotSealed();
        this.mScrollY = i;
    }

    public int getScrollDeltaX() {
        return this.mScrollDeltaX;
    }

    public void setScrollDeltaX(int i) {
        enforceNotSealed();
        this.mScrollDeltaX = i;
    }

    public int getScrollDeltaY() {
        return this.mScrollDeltaY;
    }

    public void setScrollDeltaY(int i) {
        enforceNotSealed();
        this.mScrollDeltaY = i;
    }

    public int getMaxScrollX() {
        return this.mMaxScrollX;
    }

    public void setMaxScrollX(int i) {
        enforceNotSealed();
        this.mMaxScrollX = i;
    }

    public int getMaxScrollY() {
        return this.mMaxScrollY;
    }

    public void setMaxScrollY(int i) {
        enforceNotSealed();
        this.mMaxScrollY = i;
    }

    public int getAddedCount() {
        return this.mAddedCount;
    }

    public void setAddedCount(int i) {
        enforceNotSealed();
        this.mAddedCount = i;
    }

    public int getRemovedCount() {
        return this.mRemovedCount;
    }

    public void setRemovedCount(int i) {
        enforceNotSealed();
        this.mRemovedCount = i;
    }

    public java.lang.CharSequence getClassName() {
        return this.mClassName;
    }

    public void setClassName(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mClassName = charSequence;
    }

    public java.util.List<java.lang.CharSequence> getText() {
        return this.mText;
    }

    public java.lang.CharSequence getBeforeText() {
        return this.mBeforeText;
    }

    public void setBeforeText(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mBeforeText = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public void setContentDescription(java.lang.CharSequence charSequence) {
        enforceNotSealed();
        this.mContentDescription = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
    }

    public android.os.Parcelable getParcelableData() {
        return this.mParcelableData;
    }

    public void setParcelableData(android.os.Parcelable parcelable) {
        enforceNotSealed();
        this.mParcelableData = parcelable;
    }

    public long getSourceNodeId() {
        return this.mSourceNodeId;
    }

    public void setConnectionId(int i) {
        enforceNotSealed();
        this.mConnectionId = i;
    }

    public void setSealed(boolean z) {
        this.mSealed = z;
    }

    boolean isSealed() {
        return this.mSealed;
    }

    void enforceSealed() {
        if (!isSealed()) {
            throw new java.lang.IllegalStateException("Cannot perform this action on a not sealed instance.");
        }
    }

    void enforceNotSealed() {
        if (isSealed()) {
            throw new java.lang.IllegalStateException("Cannot perform this action on a sealed instance.");
        }
    }

    private boolean getBooleanProperty(int i) {
        return (this.mBooleanProperties & i) == i;
    }

    private void setBooleanProperty(int i, boolean z) {
        if (z) {
            this.mBooleanProperties = i | this.mBooleanProperties;
        } else {
            this.mBooleanProperties = (~i) & this.mBooleanProperties;
        }
    }

    @java.lang.Deprecated
    public static android.view.accessibility.AccessibilityRecord obtain(android.view.accessibility.AccessibilityRecord accessibilityRecord) {
        android.view.accessibility.AccessibilityRecord obtain = obtain();
        obtain.init(accessibilityRecord);
        return obtain;
    }

    @java.lang.Deprecated
    public static android.view.accessibility.AccessibilityRecord obtain() {
        return new android.view.accessibility.AccessibilityRecord();
    }

    @java.lang.Deprecated
    public void recycle() {
    }

    void init(android.view.accessibility.AccessibilityRecord accessibilityRecord) {
        this.mSealed = accessibilityRecord.mSealed;
        this.mBooleanProperties = accessibilityRecord.mBooleanProperties;
        this.mCurrentItemIndex = accessibilityRecord.mCurrentItemIndex;
        this.mItemCount = accessibilityRecord.mItemCount;
        this.mFromIndex = accessibilityRecord.mFromIndex;
        this.mToIndex = accessibilityRecord.mToIndex;
        this.mScrollX = accessibilityRecord.mScrollX;
        this.mScrollY = accessibilityRecord.mScrollY;
        this.mMaxScrollX = accessibilityRecord.mMaxScrollX;
        this.mMaxScrollY = accessibilityRecord.mMaxScrollY;
        this.mScrollDeltaX = accessibilityRecord.mScrollDeltaX;
        this.mScrollDeltaY = accessibilityRecord.mScrollDeltaY;
        this.mAddedCount = accessibilityRecord.mAddedCount;
        this.mRemovedCount = accessibilityRecord.mRemovedCount;
        this.mClassName = accessibilityRecord.mClassName;
        this.mContentDescription = accessibilityRecord.mContentDescription;
        this.mBeforeText = accessibilityRecord.mBeforeText;
        this.mParcelableData = accessibilityRecord.mParcelableData;
        this.mText.addAll(accessibilityRecord.mText);
        this.mSourceWindowId = accessibilityRecord.mSourceWindowId;
        this.mSourceNodeId = accessibilityRecord.mSourceNodeId;
        this.mSourceDisplayId = accessibilityRecord.mSourceDisplayId;
        this.mConnectionId = accessibilityRecord.mConnectionId;
    }

    void clear() {
        this.mSealed = false;
        this.mBooleanProperties = 0;
        this.mCurrentItemIndex = -1;
        this.mItemCount = -1;
        this.mFromIndex = -1;
        this.mToIndex = -1;
        this.mScrollX = 0;
        this.mScrollY = 0;
        this.mMaxScrollX = 0;
        this.mMaxScrollY = 0;
        this.mScrollDeltaX = -1;
        this.mScrollDeltaY = -1;
        this.mAddedCount = -1;
        this.mRemovedCount = -1;
        this.mClassName = null;
        this.mContentDescription = null;
        this.mBeforeText = null;
        this.mParcelableData = null;
        this.mText.clear();
        this.mSourceNodeId = 2147483647L;
        this.mSourceWindowId = -1;
        this.mSourceDisplayId = -1;
        this.mConnectionId = -1;
    }

    public java.lang.String toString() {
        return appendTo(new java.lang.StringBuilder()).toString();
    }

    java.lang.StringBuilder appendTo(java.lang.StringBuilder sb) {
        sb.append(" [ ClassName: ").append(this.mClassName);
        appendPropName(sb, "Text").append(this.mText);
        append(sb, "ContentDescription", this.mContentDescription);
        append(sb, "ItemCount", this.mItemCount);
        append(sb, "CurrentItemIndex", this.mCurrentItemIndex);
        appendUnless(true, 2, sb);
        appendUnless(false, 4, sb);
        appendUnless(false, 1, sb);
        appendUnless(false, 128, sb);
        appendUnless(false, 256, sb);
        appendUnless(false, 512, sb);
        appendUnless(false, 1024, sb);
        append(sb, "BeforeText", this.mBeforeText);
        append(sb, "FromIndex", this.mFromIndex);
        append(sb, "ToIndex", this.mToIndex);
        append(sb, "ScrollX", this.mScrollX);
        append(sb, "ScrollY", this.mScrollY);
        append(sb, "MaxScrollX", this.mMaxScrollX);
        append(sb, "MaxScrollY", this.mMaxScrollY);
        append(sb, "ScrollDeltaX", this.mScrollDeltaX);
        append(sb, "ScrollDeltaY", this.mScrollDeltaY);
        append(sb, "AddedCount", this.mAddedCount);
        append(sb, "RemovedCount", this.mRemovedCount);
        append(sb, "ParcelableData", this.mParcelableData);
        append(sb, "DisplayId", this.mSourceDisplayId);
        sb.append(" ]");
        return sb;
    }

    private void appendUnless(boolean z, int i, java.lang.StringBuilder sb) {
        appendPropName(sb, singleBooleanPropertyToString(i)).append(getBooleanProperty(i));
    }

    private static java.lang.String singleBooleanPropertyToString(int i) {
        switch (i) {
            case 1:
                return "Checked";
            case 2:
                return "Enabled";
            case 4:
                return "Password";
            case 128:
                return "FullScreen";
            case 256:
                return "Scrollable";
            case 512:
                return "ImportantForAccessibility";
            case 1024:
                return "AccessibilityDataSensitive";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    private void append(java.lang.StringBuilder sb, java.lang.String str, int i) {
        appendPropName(sb, str).append(i);
    }

    private void append(java.lang.StringBuilder sb, java.lang.String str, java.lang.Object obj) {
        appendPropName(sb, str).append(obj);
    }

    private java.lang.StringBuilder appendPropName(java.lang.StringBuilder sb, java.lang.String str) {
        return sb.append("; ").append(str).append(": ");
    }
}
