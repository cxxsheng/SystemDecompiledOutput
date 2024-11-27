package android.view.accessibility;

/* loaded from: classes4.dex */
public final class AccessibilityWindowInfo implements android.os.Parcelable {
    public static final int ACTIVE_WINDOW_ID = Integer.MAX_VALUE;
    public static final int ANY_WINDOW_ID = -2;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 4;
    private static final int BOOLEAN_PROPERTY_ACTIVE = 1;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 2;
    private static final int BOOLEAN_PROPERTY_PICTURE_IN_PICTURE = 8;
    private static final boolean DEBUG = false;
    private static final int MAX_POOL_SIZE = 10;
    public static final int PICTURE_IN_PICTURE_ACTION_REPLACER_WINDOW_ID = -3;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_MAGNIFICATION_OVERLAY = 6;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    public static final int TYPE_WINDOW_CONTROL = 7;
    public static final int UNDEFINED_CONNECTION_ID = -1;
    public static final int UNDEFINED_WINDOW_ID = -1;
    private static java.util.concurrent.atomic.AtomicInteger sNumInstancesInUse;
    private int mBooleanProperties;
    private android.util.LongArray mChildIds;
    private java.lang.CharSequence mTitle;
    private long mTransitionTime;
    private static final android.util.Pools.SynchronizedPool<android.view.accessibility.AccessibilityWindowInfo> sPool = new android.util.Pools.SynchronizedPool<>(10);
    public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowInfo> CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowInfo>() { // from class: android.view.accessibility.AccessibilityWindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.accessibility.AccessibilityWindowInfo createFromParcel(android.os.Parcel parcel) {
            android.view.accessibility.AccessibilityWindowInfo obtain = android.view.accessibility.AccessibilityWindowInfo.obtain();
            obtain.initFromParcel(parcel);
            return obtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.accessibility.AccessibilityWindowInfo[] newArray(int i) {
            return new android.view.accessibility.AccessibilityWindowInfo[i];
        }
    };
    private int mDisplayId = -1;
    private int mType = -1;
    private int mLayer = -1;
    private int mId = -1;
    private int mParentId = -1;
    private int mTaskId = -1;
    private android.graphics.Region mRegionInScreen = new android.graphics.Region();
    private long mAnchorId = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    private int mConnectionId = -1;
    private android.os.LocaleList mLocales = android.os.LocaleList.getEmptyLocaleList();

    public AccessibilityWindowInfo() {
    }

    public AccessibilityWindowInfo(android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
        init(accessibilityWindowInfo);
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public void setTitle(java.lang.CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getLayer() {
        return this.mLayer;
    }

    public void setLayer(int i) {
        this.mLayer = i;
    }

    public android.view.accessibility.AccessibilityNodeInfo getRoot() {
        return getRoot(4);
    }

    public android.view.accessibility.AccessibilityNodeInfo getRoot(int i) {
        if (this.mConnectionId == -1) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mId, android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, true, i, (android.os.Bundle) null);
    }

    public void setAnchorId(long j) {
        this.mAnchorId = j;
    }

    public android.view.accessibility.AccessibilityNodeInfo getAnchor() {
        if (this.mConnectionId == -1 || this.mAnchorId == android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID || this.mParentId == -1) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mParentId, this.mAnchorId, true, 0, (android.os.Bundle) null);
    }

    public void setPictureInPicture(boolean z) {
        setBooleanProperty(8, z);
    }

    public boolean isInPictureInPictureMode() {
        return getBooleanProperty(8);
    }

    public android.view.accessibility.AccessibilityWindowInfo getParent() {
        if (this.mConnectionId == -1 || this.mParentId == -1) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mParentId);
    }

    public void setParentId(int i) {
        this.mParentId = i;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public void setTaskId(int i) {
        this.mTaskId = i;
    }

    public void setConnectionId(int i) {
        this.mConnectionId = i;
    }

    public void getRegionInScreen(android.graphics.Region region) {
        region.set(this.mRegionInScreen);
    }

    public void setRegionInScreen(android.graphics.Region region) {
        this.mRegionInScreen.set(region);
    }

    public void getBoundsInScreen(android.graphics.Rect rect) {
        rect.set(this.mRegionInScreen.getBounds());
    }

    public boolean isActive() {
        return getBooleanProperty(1);
    }

    public void setActive(boolean z) {
        setBooleanProperty(1, z);
    }

    public boolean isFocused() {
        return getBooleanProperty(2);
    }

    public void setFocused(boolean z) {
        setBooleanProperty(2, z);
    }

    public boolean isAccessibilityFocused() {
        return getBooleanProperty(4);
    }

    public void setAccessibilityFocused(boolean z) {
        setBooleanProperty(4, z);
    }

    public int getChildCount() {
        if (this.mChildIds != null) {
            return this.mChildIds.size();
        }
        return 0;
    }

    public android.view.accessibility.AccessibilityWindowInfo getChild(int i) {
        if (this.mChildIds == null) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (this.mConnectionId == -1) {
            return null;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, (int) this.mChildIds.get(i));
    }

    public void addChild(int i) {
        if (this.mChildIds == null) {
            this.mChildIds = new android.util.LongArray();
        }
        this.mChildIds.add(i);
    }

    public void setDisplayId(int i) {
        this.mDisplayId = i;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public void setTransitionTimeMillis(long j) {
        this.mTransitionTime = j;
    }

    public long getTransitionTimeMillis() {
        return this.mTransitionTime;
    }

    public void setLocales(android.os.LocaleList localeList) {
        this.mLocales = localeList;
    }

    public android.os.LocaleList getLocales() {
        return this.mLocales;
    }

    public static android.view.accessibility.AccessibilityWindowInfo obtain() {
        android.view.accessibility.AccessibilityWindowInfo acquire = sPool.acquire();
        if (acquire == null) {
            acquire = new android.view.accessibility.AccessibilityWindowInfo();
        }
        if (sNumInstancesInUse != null) {
            sNumInstancesInUse.incrementAndGet();
        }
        return acquire;
    }

    public static android.view.accessibility.AccessibilityWindowInfo obtain(android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
        android.view.accessibility.AccessibilityWindowInfo obtain = obtain();
        obtain.init(accessibilityWindowInfo);
        return obtain;
    }

    public static void setNumInstancesInUseCounter(java.util.concurrent.atomic.AtomicInteger atomicInteger) {
        if (sNumInstancesInUse != null) {
            sNumInstancesInUse = atomicInteger;
        }
    }

    public void recycle() {
        clear();
        sPool.release(this);
        if (sNumInstancesInUse != null) {
            sNumInstancesInUse.decrementAndGet();
        }
    }

    public boolean refresh() {
        android.view.accessibility.AccessibilityWindowInfo window;
        if (this.mConnectionId == -1 || this.mId == -1 || (window = android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mId, true)) == null) {
            return false;
        }
        init(window);
        window.recycle();
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mLayer);
        parcel.writeInt(this.mBooleanProperties);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mParentId);
        parcel.writeInt(this.mTaskId);
        this.mRegionInScreen.writeToParcel(parcel, i);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeLong(this.mAnchorId);
        parcel.writeLong(this.mTransitionTime);
        android.util.LongArray longArray = this.mChildIds;
        if (longArray == null) {
            parcel.writeInt(0);
        } else {
            int size = longArray.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt((int) longArray.get(i2));
            }
        }
        parcel.writeInt(this.mConnectionId);
        parcel.writeParcelable(this.mLocales, i);
    }

    private void init(android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
        this.mDisplayId = accessibilityWindowInfo.mDisplayId;
        this.mType = accessibilityWindowInfo.mType;
        this.mLayer = accessibilityWindowInfo.mLayer;
        this.mBooleanProperties = accessibilityWindowInfo.mBooleanProperties;
        this.mId = accessibilityWindowInfo.mId;
        this.mParentId = accessibilityWindowInfo.mParentId;
        this.mTaskId = accessibilityWindowInfo.mTaskId;
        this.mRegionInScreen.set(accessibilityWindowInfo.mRegionInScreen);
        this.mTitle = accessibilityWindowInfo.mTitle;
        this.mAnchorId = accessibilityWindowInfo.mAnchorId;
        this.mTransitionTime = accessibilityWindowInfo.mTransitionTime;
        if (this.mChildIds != null) {
            this.mChildIds.clear();
        }
        if (accessibilityWindowInfo.mChildIds != null && accessibilityWindowInfo.mChildIds.size() > 0) {
            if (this.mChildIds == null) {
                this.mChildIds = accessibilityWindowInfo.mChildIds.m4813clone();
            } else {
                this.mChildIds.addAll(accessibilityWindowInfo.mChildIds);
            }
        }
        this.mConnectionId = accessibilityWindowInfo.mConnectionId;
        this.mLocales = accessibilityWindowInfo.mLocales;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(android.os.Parcel parcel) {
        this.mDisplayId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mLayer = parcel.readInt();
        this.mBooleanProperties = parcel.readInt();
        this.mId = parcel.readInt();
        this.mParentId = parcel.readInt();
        this.mTaskId = parcel.readInt();
        this.mRegionInScreen = android.graphics.Region.CREATOR.createFromParcel(parcel);
        this.mTitle = parcel.readCharSequence();
        this.mAnchorId = parcel.readLong();
        this.mTransitionTime = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            if (this.mChildIds == null) {
                this.mChildIds = new android.util.LongArray(readInt);
            }
            for (int i = 0; i < readInt; i++) {
                this.mChildIds.add(parcel.readInt());
            }
        }
        this.mConnectionId = parcel.readInt();
        this.mLocales = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
    }

    public int hashCode() {
        return this.mId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mId == ((android.view.accessibility.AccessibilityWindowInfo) obj).mId) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("AccessibilityWindowInfo[");
        sb.append("title=").append(this.mTitle);
        sb.append(", displayId=").append(this.mDisplayId);
        sb.append(", id=").append(this.mId);
        sb.append(", taskId=").append(this.mTaskId);
        sb.append(", type=").append(typeToString(this.mType));
        sb.append(", layer=").append(this.mLayer);
        sb.append(", region=").append(this.mRegionInScreen);
        sb.append(", bounds=").append(this.mRegionInScreen.getBounds());
        sb.append(", focused=").append(isFocused());
        sb.append(", active=").append(isActive());
        sb.append(", pictureInPicture=").append(isInPictureInPictureMode());
        sb.append(", transitionTime=").append(this.mTransitionTime);
        sb.append(", hasParent=").append(this.mParentId != -1);
        sb.append(", isAnchored=").append(this.mAnchorId != android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID);
        sb.append(", hasChildren=").append(this.mChildIds != null && this.mChildIds.size() > 0);
        sb.append(']');
        return sb.toString();
    }

    private void clear() {
        this.mDisplayId = -1;
        this.mType = -1;
        this.mLayer = -1;
        this.mBooleanProperties = 0;
        this.mId = -1;
        this.mParentId = -1;
        this.mTaskId = -1;
        this.mRegionInScreen.setEmpty();
        this.mChildIds = null;
        this.mConnectionId = -1;
        this.mAnchorId = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        this.mTitle = null;
        this.mTransitionTime = 0L;
        this.mLocales = android.os.LocaleList.getEmptyLocaleList();
    }

    private boolean getBooleanProperty(int i) {
        return (i & this.mBooleanProperties) != 0;
    }

    private void setBooleanProperty(int i, boolean z) {
        if (z) {
            this.mBooleanProperties = i | this.mBooleanProperties;
        } else {
            this.mBooleanProperties = (~i) & this.mBooleanProperties;
        }
    }

    public static java.lang.String typeToString(int i) {
        if (android.view.accessibility.Flags.addTypeWindowControl() && i == 7) {
            return "TYPE_WINDOW_CONTROL";
        }
        switch (i) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            case 5:
                return "TYPE_SPLIT_SCREEN_DIVIDER";
            case 6:
                return "TYPE_MAGNIFICATION_OVERLAY";
            default:
                return "<UNKNOWN:" + i + ">";
        }
    }

    public int differenceFrom(android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
        int i;
        if (accessibilityWindowInfo.mId != this.mId) {
            throw new java.lang.IllegalArgumentException("Not same window.");
        }
        if (accessibilityWindowInfo.mType != this.mType) {
            throw new java.lang.IllegalArgumentException("Not same type.");
        }
        if (android.text.TextUtils.equals(this.mTitle, accessibilityWindowInfo.mTitle)) {
            i = 0;
        } else {
            i = 4;
        }
        if (!this.mRegionInScreen.equals(accessibilityWindowInfo.mRegionInScreen)) {
            i |= 8;
        }
        if (this.mLayer != accessibilityWindowInfo.mLayer) {
            i |= 16;
        }
        if (getBooleanProperty(1) != accessibilityWindowInfo.getBooleanProperty(1)) {
            i |= 32;
        }
        if (getBooleanProperty(2) != accessibilityWindowInfo.getBooleanProperty(2)) {
            i |= 64;
        }
        if (getBooleanProperty(4) != accessibilityWindowInfo.getBooleanProperty(4)) {
            i |= 128;
        }
        if (getBooleanProperty(8) != accessibilityWindowInfo.getBooleanProperty(8)) {
            i |= 1024;
        }
        if (this.mParentId != accessibilityWindowInfo.mParentId) {
            i |= 256;
        }
        if (!java.util.Objects.equals(this.mChildIds, accessibilityWindowInfo.mChildIds)) {
            return i | 512;
        }
        return i;
    }

    public static final class WindowListSparseArray extends android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray> CREATOR = new android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray>() { // from class: android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray createFromParcel(android.os.Parcel parcel) {
                android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray windowListSparseArray = new android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray();
                java.lang.ClassLoader classLoader = windowListSparseArray.getClass().getClassLoader();
                int readInt = parcel.readInt();
                for (int i = 0; i < readInt; i++) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    parcel.readParcelableList(arrayList, classLoader, android.view.accessibility.AccessibilityWindowInfo.class);
                    windowListSparseArray.put(parcel.readInt(), arrayList);
                }
                return windowListSparseArray;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray[] newArray(int i) {
                return new android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            int size = size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeParcelableList(valueAt(i2), 0);
                parcel.writeInt(keyAt(i2));
            }
        }
    }
}
