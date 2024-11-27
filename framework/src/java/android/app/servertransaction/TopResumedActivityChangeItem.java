package android.app.servertransaction;

/* loaded from: classes.dex */
public class TopResumedActivityChangeItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.TopResumedActivityChangeItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.TopResumedActivityChangeItem>() { // from class: android.app.servertransaction.TopResumedActivityChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.TopResumedActivityChangeItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.TopResumedActivityChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.TopResumedActivityChangeItem[] newArray(int i) {
            return new android.app.servertransaction.TopResumedActivityChangeItem[i];
        }
    };
    private boolean mOnTop;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "topResumedActivityChangeItem");
        clientTransactionHandler.handleTopResumedActivityChanged(activityClientRecord, this.mOnTop, "topResumedActivityChangeItem");
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        if (this.mOnTop) {
            return;
        }
        android.app.ActivityClient.getInstance().activityTopResumedStateLost();
    }

    private TopResumedActivityChangeItem() {
    }

    public static android.app.servertransaction.TopResumedActivityChangeItem obtain(android.os.IBinder iBinder, boolean z) {
        android.app.servertransaction.TopResumedActivityChangeItem topResumedActivityChangeItem = (android.app.servertransaction.TopResumedActivityChangeItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.TopResumedActivityChangeItem.class);
        if (topResumedActivityChangeItem == null) {
            topResumedActivityChangeItem = new android.app.servertransaction.TopResumedActivityChangeItem();
        }
        topResumedActivityChangeItem.setActivityToken(iBinder);
        topResumedActivityChangeItem.mOnTop = z;
        return topResumedActivityChangeItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mOnTop = false;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mOnTop);
    }

    private TopResumedActivityChangeItem(android.os.Parcel parcel) {
        super(parcel);
        this.mOnTop = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return super.equals(obj) && this.mOnTop == ((android.app.servertransaction.TopResumedActivityChangeItem) obj).mOnTop;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + (this.mOnTop ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "TopResumedActivityChangeItem{" + super.toString() + ",onTop=" + this.mOnTop + "}";
    }
}
