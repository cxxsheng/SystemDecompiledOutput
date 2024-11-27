package android.app.servertransaction;

/* loaded from: classes.dex */
public class MoveToDisplayItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.MoveToDisplayItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.MoveToDisplayItem>() { // from class: android.app.servertransaction.MoveToDisplayItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.MoveToDisplayItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.MoveToDisplayItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.MoveToDisplayItem[] newArray(int i) {
            return new android.app.servertransaction.MoveToDisplayItem[i];
        }
    };
    private android.window.ActivityWindowInfo mActivityWindowInfo;
    private android.content.res.Configuration mConfiguration;
    private int mTargetDisplayId;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfiguration);
        clientTransactionHandler.updatePendingActivityConfiguration(getActivityToken(), this.mConfiguration);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityMovedToDisplay");
        clientTransactionHandler.handleActivityConfigurationChanged(activityClientRecord, this.mConfiguration, this.mTargetDisplayId, this.mActivityWindowInfo);
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return clientTransactionHandler.getActivity(getActivityToken());
    }

    private MoveToDisplayItem() {
    }

    public static android.app.servertransaction.MoveToDisplayItem obtain(android.os.IBinder iBinder, int i, android.content.res.Configuration configuration, android.window.ActivityWindowInfo activityWindowInfo) {
        android.app.servertransaction.MoveToDisplayItem moveToDisplayItem = (android.app.servertransaction.MoveToDisplayItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.MoveToDisplayItem.class);
        if (moveToDisplayItem == null) {
            moveToDisplayItem = new android.app.servertransaction.MoveToDisplayItem();
        }
        moveToDisplayItem.setActivityToken(iBinder);
        moveToDisplayItem.mTargetDisplayId = i;
        moveToDisplayItem.mConfiguration = new android.content.res.Configuration(configuration);
        moveToDisplayItem.mActivityWindowInfo = new android.window.ActivityWindowInfo(activityWindowInfo);
        return moveToDisplayItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mTargetDisplayId = 0;
        this.mConfiguration = null;
        this.mActivityWindowInfo = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTargetDisplayId);
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private MoveToDisplayItem(android.os.Parcel parcel) {
        super(parcel);
        this.mTargetDisplayId = parcel.readInt();
        this.mConfiguration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
        this.mActivityWindowInfo = (android.window.ActivityWindowInfo) parcel.readTypedObject(android.window.ActivityWindowInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.MoveToDisplayItem moveToDisplayItem = (android.app.servertransaction.MoveToDisplayItem) obj;
        return this.mTargetDisplayId == moveToDisplayItem.mTargetDisplayId && java.util.Objects.equals(this.mConfiguration, moveToDisplayItem.mConfiguration) && java.util.Objects.equals(this.mActivityWindowInfo, moveToDisplayItem.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((527 + super.hashCode()) * 31) + this.mTargetDisplayId) * 31) + this.mConfiguration.hashCode()) * 31) + java.util.Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "MoveToDisplayItem{" + super.toString() + ",targetDisplayId=" + this.mTargetDisplayId + ",configuration=" + this.mConfiguration + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
