package android.app.servertransaction;

/* loaded from: classes.dex */
public class WindowContextInfoChangeItem extends android.app.servertransaction.ClientTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.WindowContextInfoChangeItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.WindowContextInfoChangeItem>() { // from class: android.app.servertransaction.WindowContextInfoChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.WindowContextInfoChangeItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.WindowContextInfoChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.WindowContextInfoChangeItem[] newArray(int i) {
            return new android.app.servertransaction.WindowContextInfoChangeItem[i];
        }
    };
    private android.os.IBinder mClientToken;
    private android.window.WindowContextInfo mInfo;

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleWindowContextInfoChanged(this.mClientToken, this.mInfo);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return clientTransactionHandler.getWindowContext(this.mClientToken);
    }

    private WindowContextInfoChangeItem() {
    }

    public static android.app.servertransaction.WindowContextInfoChangeItem obtain(android.os.IBinder iBinder, android.content.res.Configuration configuration, int i) {
        android.app.servertransaction.WindowContextInfoChangeItem windowContextInfoChangeItem = (android.app.servertransaction.WindowContextInfoChangeItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.WindowContextInfoChangeItem.class);
        if (windowContextInfoChangeItem == null) {
            windowContextInfoChangeItem = new android.app.servertransaction.WindowContextInfoChangeItem();
        }
        windowContextInfoChangeItem.mClientToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
        windowContextInfoChangeItem.mInfo = new android.window.WindowContextInfo(new android.content.res.Configuration(configuration), i);
        return windowContextInfoChangeItem;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mClientToken = null;
        this.mInfo = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mClientToken);
        parcel.writeTypedObject(this.mInfo, i);
    }

    private WindowContextInfoChangeItem(android.os.Parcel parcel) {
        this.mClientToken = parcel.readStrongBinder();
        this.mInfo = (android.window.WindowContextInfo) parcel.readTypedObject(android.window.WindowContextInfo.CREATOR);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.servertransaction.WindowContextInfoChangeItem windowContextInfoChangeItem = (android.app.servertransaction.WindowContextInfoChangeItem) obj;
        if (java.util.Objects.equals(this.mClientToken, windowContextInfoChangeItem.mClientToken) && java.util.Objects.equals(this.mInfo, windowContextInfoChangeItem.mInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((527 + java.util.Objects.hashCode(this.mClientToken)) * 31) + java.util.Objects.hashCode(this.mInfo);
    }

    public java.lang.String toString() {
        return "WindowContextInfoChangeItem{clientToken=" + this.mClientToken + ", info=" + this.mInfo + "}";
    }
}
