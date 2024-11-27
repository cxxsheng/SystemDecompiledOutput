package android.app.servertransaction;

/* loaded from: classes.dex */
public class RefreshCallbackItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.RefreshCallbackItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.RefreshCallbackItem>() { // from class: android.app.servertransaction.RefreshCallbackItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.RefreshCallbackItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.RefreshCallbackItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.RefreshCallbackItem[] newArray(int i) {
            return new android.app.servertransaction.RefreshCallbackItem[i];
        }
    };
    private int mPostExecutionState;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportRefresh(getActivityClientRecord(clientTransactionHandler));
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return this.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    boolean shouldHaveDefinedPreExecutionState() {
        return false;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    public static android.app.servertransaction.RefreshCallbackItem obtain(android.os.IBinder iBinder, int i) {
        if (i != 5 && i != 4) {
            throw new java.lang.IllegalArgumentException("Only ON_STOP or ON_PAUSE are allowed as a post execution state for RefreshCallbackItem but got " + i);
        }
        android.app.servertransaction.RefreshCallbackItem refreshCallbackItem = (android.app.servertransaction.RefreshCallbackItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.RefreshCallbackItem.class);
        if (refreshCallbackItem == null) {
            refreshCallbackItem = new android.app.servertransaction.RefreshCallbackItem();
        }
        refreshCallbackItem.setActivityToken(iBinder);
        refreshCallbackItem.mPostExecutionState = i;
        return refreshCallbackItem;
    }

    private RefreshCallbackItem() {
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mPostExecutionState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && this.mPostExecutionState == ((android.app.servertransaction.RefreshCallbackItem) obj).mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + this.mPostExecutionState;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "RefreshCallbackItem{" + super.toString() + ",mPostExecutionState=" + this.mPostExecutionState + "}";
    }

    private RefreshCallbackItem(android.os.Parcel parcel) {
        super(parcel);
        this.mPostExecutionState = parcel.readInt();
    }
}
