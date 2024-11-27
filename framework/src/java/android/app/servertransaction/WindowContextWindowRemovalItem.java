package android.app.servertransaction;

/* loaded from: classes.dex */
public class WindowContextWindowRemovalItem extends android.app.servertransaction.ClientTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.WindowContextWindowRemovalItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.WindowContextWindowRemovalItem>() { // from class: android.app.servertransaction.WindowContextWindowRemovalItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.WindowContextWindowRemovalItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.WindowContextWindowRemovalItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.WindowContextWindowRemovalItem[] newArray(int i) {
            return new android.app.servertransaction.WindowContextWindowRemovalItem[i];
        }
    };
    private android.os.IBinder mClientToken;

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleWindowContextWindowRemoval(this.mClientToken);
    }

    private WindowContextWindowRemovalItem() {
    }

    public static android.app.servertransaction.WindowContextWindowRemovalItem obtain(android.os.IBinder iBinder) {
        android.app.servertransaction.WindowContextWindowRemovalItem windowContextWindowRemovalItem = (android.app.servertransaction.WindowContextWindowRemovalItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.WindowContextWindowRemovalItem.class);
        if (windowContextWindowRemovalItem == null) {
            windowContextWindowRemovalItem = new android.app.servertransaction.WindowContextWindowRemovalItem();
        }
        windowContextWindowRemovalItem.mClientToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
        return windowContextWindowRemovalItem;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mClientToken = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mClientToken);
    }

    private WindowContextWindowRemovalItem(android.os.Parcel parcel) {
        this.mClientToken = parcel.readStrongBinder();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mClientToken, ((android.app.servertransaction.WindowContextWindowRemovalItem) obj).mClientToken);
    }

    public int hashCode() {
        return 527 + java.util.Objects.hashCode(this.mClientToken);
    }

    public java.lang.String toString() {
        return "WindowContextWindowRemovalItem{clientToken=" + this.mClientToken + "}";
    }
}
