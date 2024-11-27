package android.app.servertransaction;

/* loaded from: classes.dex */
public class ActivityResultItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final long CALL_ACTIVITY_RESULT_BEFORE_RESUME = 78294732;
    public static final android.os.Parcelable.Creator<android.app.servertransaction.ActivityResultItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.ActivityResultItem>() { // from class: android.app.servertransaction.ActivityResultItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ActivityResultItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.ActivityResultItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ActivityResultItem[] newArray(int i) {
            return new android.app.servertransaction.ActivityResultItem[i];
        }
    };
    private java.util.List<android.app.ResultInfo> mResultInfoList;

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return android.app.compat.CompatChanges.isChangeEnabled(CALL_ACTIVITY_RESULT_BEFORE_RESUME) ? 3 : -1;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityDeliverResult");
        clientTransactionHandler.handleSendResult(activityClientRecord, this.mResultInfoList, "ACTIVITY_RESULT");
        android.os.Trace.traceEnd(64L);
    }

    private ActivityResultItem() {
    }

    public static android.app.servertransaction.ActivityResultItem obtain(android.os.IBinder iBinder, java.util.List<android.app.ResultInfo> list) {
        android.app.servertransaction.ActivityResultItem activityResultItem = (android.app.servertransaction.ActivityResultItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ActivityResultItem.class);
        if (activityResultItem == null) {
            activityResultItem = new android.app.servertransaction.ActivityResultItem();
        }
        activityResultItem.setActivityToken(iBinder);
        activityResultItem.mResultInfoList = new java.util.ArrayList(list);
        return activityResultItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mResultInfoList = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mResultInfoList, i);
    }

    private ActivityResultItem(android.os.Parcel parcel) {
        super(parcel);
        this.mResultInfoList = parcel.createTypedArrayList(android.app.ResultInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return java.util.Objects.equals(this.mResultInfoList, ((android.app.servertransaction.ActivityResultItem) obj).mResultInfoList);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + java.util.Objects.hashCode(this.mResultInfoList);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "ActivityResultItem{" + super.toString() + ",resultInfoList=" + this.mResultInfoList + "}";
    }
}
