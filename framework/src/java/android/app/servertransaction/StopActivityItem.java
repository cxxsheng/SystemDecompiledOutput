package android.app.servertransaction;

/* loaded from: classes.dex */
public class StopActivityItem extends android.app.servertransaction.ActivityLifecycleItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.StopActivityItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.StopActivityItem>() { // from class: android.app.servertransaction.StopActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.StopActivityItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.StopActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.StopActivityItem[] newArray(int i) {
            return new android.app.servertransaction.StopActivityItem[i];
        }
    };
    private static final java.lang.String TAG = "StopActivityItem";
    private int mConfigChanges;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityStop");
        clientTransactionHandler.handleStopActivity(activityClientRecord, this.mConfigChanges, pendingTransactionActions, true, "STOP_ACTIVITY_ITEM");
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportStop(pendingTransactionActions);
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 5;
    }

    private StopActivityItem() {
    }

    public static android.app.servertransaction.StopActivityItem obtain(android.os.IBinder iBinder, int i) {
        android.app.servertransaction.StopActivityItem stopActivityItem = (android.app.servertransaction.StopActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.StopActivityItem.class);
        if (stopActivityItem == null) {
            stopActivityItem = new android.app.servertransaction.StopActivityItem();
        }
        stopActivityItem.setActivityToken(iBinder);
        stopActivityItem.mConfigChanges = i;
        return stopActivityItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mConfigChanges = 0;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mConfigChanges);
    }

    private StopActivityItem(android.os.Parcel parcel) {
        super(parcel);
        this.mConfigChanges = parcel.readInt();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && this.mConfigChanges == ((android.app.servertransaction.StopActivityItem) obj).mConfigChanges;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + this.mConfigChanges;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "StopActivityItem{" + super.toString() + ",configChanges=" + this.mConfigChanges + "}";
    }
}
