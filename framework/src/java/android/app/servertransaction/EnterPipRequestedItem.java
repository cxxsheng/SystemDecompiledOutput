package android.app.servertransaction;

/* loaded from: classes.dex */
public final class EnterPipRequestedItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.EnterPipRequestedItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.EnterPipRequestedItem>() { // from class: android.app.servertransaction.EnterPipRequestedItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.EnterPipRequestedItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.EnterPipRequestedItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.EnterPipRequestedItem[] newArray(int i) {
            return new android.app.servertransaction.EnterPipRequestedItem[i];
        }
    };

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handlePictureInPictureRequested(activityClientRecord);
    }

    private EnterPipRequestedItem() {
    }

    public static android.app.servertransaction.EnterPipRequestedItem obtain(android.os.IBinder iBinder) {
        android.app.servertransaction.EnterPipRequestedItem enterPipRequestedItem = (android.app.servertransaction.EnterPipRequestedItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.EnterPipRequestedItem.class);
        if (enterPipRequestedItem == null) {
            enterPipRequestedItem = new android.app.servertransaction.EnterPipRequestedItem();
        }
        enterPipRequestedItem.setActivityToken(iBinder);
        return enterPipRequestedItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    private EnterPipRequestedItem(android.os.Parcel parcel) {
        super(parcel);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "EnterPipRequestedItem{" + super.toString() + "}";
    }
}
