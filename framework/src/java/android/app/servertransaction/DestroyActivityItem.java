package android.app.servertransaction;

/* loaded from: classes.dex */
public class DestroyActivityItem extends android.app.servertransaction.ActivityLifecycleItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.DestroyActivityItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.DestroyActivityItem>() { // from class: android.app.servertransaction.DestroyActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.DestroyActivityItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.DestroyActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.DestroyActivityItem[] newArray(int i) {
            return new android.app.servertransaction.DestroyActivityItem[i];
        }
    };
    private int mConfigChanges;
    private boolean mFinished;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        clientTransactionHandler.getActivitiesToBeDestroyed().put(getActivityToken(), this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityDestroy");
        clientTransactionHandler.handleDestroyActivity(activityClientRecord, this.mFinished, this.mConfigChanges, false, "DestroyActivityItem");
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.getActivitiesToBeDestroyed().remove(getActivityToken());
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 6;
    }

    private DestroyActivityItem() {
    }

    public static android.app.servertransaction.DestroyActivityItem obtain(android.os.IBinder iBinder, boolean z, int i) {
        android.app.servertransaction.DestroyActivityItem destroyActivityItem = (android.app.servertransaction.DestroyActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.DestroyActivityItem.class);
        if (destroyActivityItem == null) {
            destroyActivityItem = new android.app.servertransaction.DestroyActivityItem();
        }
        destroyActivityItem.setActivityToken(iBinder);
        destroyActivityItem.mFinished = z;
        destroyActivityItem.mConfigChanges = i;
        return destroyActivityItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mFinished = false;
        this.mConfigChanges = 0;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mFinished);
        parcel.writeInt(this.mConfigChanges);
    }

    private DestroyActivityItem(android.os.Parcel parcel) {
        super(parcel);
        this.mFinished = parcel.readBoolean();
        this.mConfigChanges = parcel.readInt();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.DestroyActivityItem destroyActivityItem = (android.app.servertransaction.DestroyActivityItem) obj;
        return this.mFinished == destroyActivityItem.mFinished && this.mConfigChanges == destroyActivityItem.mConfigChanges;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + (this.mFinished ? 1 : 0)) * 31) + this.mConfigChanges;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "DestroyActivityItem{" + super.toString() + ",finished=" + this.mFinished + ",mConfigChanges=" + this.mConfigChanges + "}";
    }
}
