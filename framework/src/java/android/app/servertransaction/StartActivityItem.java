package android.app.servertransaction;

/* loaded from: classes.dex */
public class StartActivityItem extends android.app.servertransaction.ActivityLifecycleItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.StartActivityItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.StartActivityItem>() { // from class: android.app.servertransaction.StartActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.StartActivityItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.StartActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.StartActivityItem[] newArray(int i) {
            return new android.app.servertransaction.StartActivityItem[i];
        }
    };
    private static final java.lang.String TAG = "StartActivityItem";
    private android.app.ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "startActivityItem");
        clientTransactionHandler.handleStartActivity(activityClientRecord, pendingTransactionActions, this.mSceneTransitionInfo);
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 2;
    }

    private StartActivityItem() {
    }

    public static android.app.servertransaction.StartActivityItem obtain(android.os.IBinder iBinder, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        android.app.servertransaction.StartActivityItem startActivityItem = (android.app.servertransaction.StartActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.StartActivityItem.class);
        if (startActivityItem == null) {
            startActivityItem = new android.app.servertransaction.StartActivityItem();
        }
        startActivityItem.setActivityToken(iBinder);
        startActivityItem.mSceneTransitionInfo = sceneTransitionInfo;
        return startActivityItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mSceneTransitionInfo = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSceneTransitionInfo, i);
    }

    private StartActivityItem(android.os.Parcel parcel) {
        super(parcel);
        this.mSceneTransitionInfo = (android.app.ActivityOptions.SceneTransitionInfo) parcel.readTypedObject(android.app.ActivityOptions.SceneTransitionInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return (this.mSceneTransitionInfo == null) == (((android.app.servertransaction.StartActivityItem) obj).mSceneTransitionInfo == null);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + (this.mSceneTransitionInfo != null ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "StartActivityItem{" + super.toString() + ",sceneTransitionInfo=" + this.mSceneTransitionInfo + "}";
    }
}
