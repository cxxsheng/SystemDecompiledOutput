package android.view;

/* loaded from: classes4.dex */
public class WindowInfo implements android.os.Parcelable {
    private static final int MAX_POOL_SIZE = 10;
    public android.os.IBinder activityToken;
    public java.util.List<android.os.IBinder> childTokens;
    public boolean focused;
    public boolean hasFlagWatchOutsideTouch;
    public boolean inPictureInPicture;
    public int layer;
    public android.os.IBinder parentToken;
    public java.lang.CharSequence title;
    public android.os.IBinder token;
    public int type;
    private static final android.util.Pools.SynchronizedPool<android.view.WindowInfo> sPool = new android.util.Pools.SynchronizedPool<>(10);
    public static final android.os.Parcelable.Creator<android.view.WindowInfo> CREATOR = new android.os.Parcelable.Creator<android.view.WindowInfo>() { // from class: android.view.WindowInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowInfo createFromParcel(android.os.Parcel parcel) {
            android.view.WindowInfo obtain = android.view.WindowInfo.obtain();
            obtain.initFromParcel(parcel);
            return obtain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowInfo[] newArray(int i) {
            return new android.view.WindowInfo[i];
        }
    };
    public android.graphics.Region regionInScreen = new android.graphics.Region();
    public long accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    public int displayId = -1;
    public int taskId = -1;
    public float[] mTransformMatrix = new float[9];
    public android.view.MagnificationSpec mMagnificationSpec = new android.view.MagnificationSpec();
    public android.os.LocaleList locales = android.os.LocaleList.getEmptyLocaleList();

    private WindowInfo() {
    }

    public static android.view.WindowInfo obtain() {
        android.view.WindowInfo acquire = sPool.acquire();
        if (acquire == null) {
            return new android.view.WindowInfo();
        }
        return acquire;
    }

    public static android.view.WindowInfo obtain(android.view.WindowInfo windowInfo) {
        android.view.WindowInfo obtain = obtain();
        obtain.displayId = windowInfo.displayId;
        obtain.taskId = windowInfo.taskId;
        obtain.type = windowInfo.type;
        obtain.layer = windowInfo.layer;
        obtain.token = windowInfo.token;
        obtain.parentToken = windowInfo.parentToken;
        obtain.activityToken = windowInfo.activityToken;
        obtain.focused = windowInfo.focused;
        obtain.regionInScreen.set(windowInfo.regionInScreen);
        obtain.title = windowInfo.title;
        obtain.accessibilityIdOfAnchor = windowInfo.accessibilityIdOfAnchor;
        obtain.inPictureInPicture = windowInfo.inPictureInPicture;
        obtain.hasFlagWatchOutsideTouch = windowInfo.hasFlagWatchOutsideTouch;
        for (int i = 0; i < obtain.mTransformMatrix.length; i++) {
            obtain.mTransformMatrix[i] = windowInfo.mTransformMatrix[i];
        }
        if (windowInfo.childTokens != null && !windowInfo.childTokens.isEmpty()) {
            if (obtain.childTokens == null) {
                obtain.childTokens = new java.util.ArrayList(windowInfo.childTokens);
            } else {
                obtain.childTokens.addAll(windowInfo.childTokens);
            }
        }
        obtain.mMagnificationSpec.setTo(windowInfo.mMagnificationSpec);
        obtain.locales = windowInfo.locales;
        return obtain;
    }

    public void recycle() {
        clear();
        sPool.release(this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.type);
        parcel.writeInt(this.layer);
        parcel.writeStrongBinder(this.token);
        parcel.writeStrongBinder(this.parentToken);
        parcel.writeStrongBinder(this.activityToken);
        parcel.writeInt(this.focused ? 1 : 0);
        this.regionInScreen.writeToParcel(parcel, i);
        parcel.writeCharSequence(this.title);
        parcel.writeLong(this.accessibilityIdOfAnchor);
        parcel.writeInt(this.inPictureInPicture ? 1 : 0);
        parcel.writeInt(this.hasFlagWatchOutsideTouch ? 1 : 0);
        parcel.writeFloatArray(this.mTransformMatrix);
        if (this.childTokens != null && !this.childTokens.isEmpty()) {
            parcel.writeInt(1);
            parcel.writeBinderList(this.childTokens);
        } else {
            parcel.writeInt(0);
        }
        this.mMagnificationSpec.writeToParcel(parcel, i);
        parcel.writeParcelable(this.locales, i);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("WindowInfo[");
        sb.append("title=").append(this.title);
        sb.append(", displayId=").append(this.displayId);
        sb.append(", taskId=").append(this.taskId);
        sb.append(", type=").append(this.type);
        sb.append(", layer=").append(this.layer);
        sb.append(", token=").append(this.token);
        sb.append(", region=").append(this.regionInScreen);
        sb.append(", bounds=").append(this.regionInScreen.getBounds());
        sb.append(", parent=").append(this.parentToken);
        sb.append(", focused=").append(this.focused);
        sb.append(", children=").append(this.childTokens);
        sb.append(", accessibility anchor=").append(this.accessibilityIdOfAnchor);
        sb.append(", pictureInPicture=").append(this.inPictureInPicture);
        sb.append(", watchOutsideTouch=").append(this.hasFlagWatchOutsideTouch);
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setValues(this.mTransformMatrix);
        sb.append(", mTransformMatrix=").append(matrix);
        sb.append(", mMagnificationSpec=").append(this.mMagnificationSpec);
        sb.append(", locales=").append(this.locales);
        sb.append(']');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(android.os.Parcel parcel) {
        this.displayId = parcel.readInt();
        this.taskId = parcel.readInt();
        this.type = parcel.readInt();
        this.layer = parcel.readInt();
        this.token = parcel.readStrongBinder();
        this.parentToken = parcel.readStrongBinder();
        this.activityToken = parcel.readStrongBinder();
        this.focused = parcel.readInt() == 1;
        this.regionInScreen = android.graphics.Region.CREATOR.createFromParcel(parcel);
        this.title = parcel.readCharSequence();
        this.accessibilityIdOfAnchor = parcel.readLong();
        this.inPictureInPicture = parcel.readInt() == 1;
        this.hasFlagWatchOutsideTouch = parcel.readInt() == 1;
        parcel.readFloatArray(this.mTransformMatrix);
        if (parcel.readInt() == 1) {
            if (this.childTokens == null) {
                this.childTokens = new java.util.ArrayList();
            }
            parcel.readBinderList(this.childTokens);
        }
        this.mMagnificationSpec = android.view.MagnificationSpec.CREATOR.createFromParcel(parcel);
        this.locales = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
    }

    private void clear() {
        this.displayId = -1;
        this.taskId = -1;
        this.type = 0;
        this.layer = 0;
        this.token = null;
        this.parentToken = null;
        this.activityToken = null;
        this.focused = false;
        this.regionInScreen.setEmpty();
        if (this.childTokens != null) {
            this.childTokens.clear();
        }
        this.inPictureInPicture = false;
        this.hasFlagWatchOutsideTouch = false;
        for (int i = 0; i < this.mTransformMatrix.length; i++) {
            this.mTransformMatrix[i] = 0.0f;
        }
        this.mMagnificationSpec.clear();
        this.title = null;
        this.accessibilityIdOfAnchor = android.view.accessibility.AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        this.locales = android.os.LocaleList.getEmptyLocaleList();
    }
}
