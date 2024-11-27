package android.app.servertransaction;

/* loaded from: classes.dex */
public final class PipStateTransactionItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.PipStateTransactionItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.PipStateTransactionItem>() { // from class: android.app.servertransaction.PipStateTransactionItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.PipStateTransactionItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.PipStateTransactionItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.PipStateTransactionItem[] newArray(int i) {
            return new android.app.servertransaction.PipStateTransactionItem[i];
        }
    };
    private android.app.PictureInPictureUiState mPipState;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handlePictureInPictureStateChanged(activityClientRecord, this.mPipState);
    }

    private PipStateTransactionItem() {
    }

    public static android.app.servertransaction.PipStateTransactionItem obtain(android.os.IBinder iBinder, android.app.PictureInPictureUiState pictureInPictureUiState) {
        android.app.servertransaction.PipStateTransactionItem pipStateTransactionItem = (android.app.servertransaction.PipStateTransactionItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.PipStateTransactionItem.class);
        if (pipStateTransactionItem == null) {
            pipStateTransactionItem = new android.app.servertransaction.PipStateTransactionItem();
        }
        pipStateTransactionItem.setActivityToken(iBinder);
        pipStateTransactionItem.mPipState = pictureInPictureUiState;
        return pipStateTransactionItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mPipState = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.mPipState.writeToParcel(parcel, i);
    }

    private PipStateTransactionItem(android.os.Parcel parcel) {
        super(parcel);
        this.mPipState = android.app.PictureInPictureUiState.CREATOR.createFromParcel(parcel);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return java.util.Objects.equals(this.mPipState, ((android.app.servertransaction.PipStateTransactionItem) obj).mPipState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + java.util.Objects.hashCode(this.mPipState);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "PipStateTransactionItem{" + super.toString() + "}";
    }
}
