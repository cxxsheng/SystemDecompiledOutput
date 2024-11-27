package android.app.servertransaction;

/* loaded from: classes.dex */
public class TransferSplashScreenViewStateItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.TransferSplashScreenViewStateItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.TransferSplashScreenViewStateItem>() { // from class: android.app.servertransaction.TransferSplashScreenViewStateItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.TransferSplashScreenViewStateItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.TransferSplashScreenViewStateItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.TransferSplashScreenViewStateItem[] newArray(int i) {
            return new android.app.servertransaction.TransferSplashScreenViewStateItem[i];
        }
    };
    private android.window.SplashScreenView.SplashScreenViewParcelable mSplashScreenViewParcelable;
    private android.view.SurfaceControl mStartingWindowLeash;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleAttachSplashScreenView(activityClientRecord, this.mSplashScreenViewParcelable, this.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mSplashScreenViewParcelable = null;
        this.mStartingWindowLeash = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSplashScreenViewParcelable, i);
        parcel.writeTypedObject(this.mStartingWindowLeash, i);
    }

    private TransferSplashScreenViewStateItem() {
    }

    private TransferSplashScreenViewStateItem(android.os.Parcel parcel) {
        super(parcel);
        this.mSplashScreenViewParcelable = (android.window.SplashScreenView.SplashScreenViewParcelable) parcel.readTypedObject(android.window.SplashScreenView.SplashScreenViewParcelable.CREATOR);
        this.mStartingWindowLeash = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
    }

    public static android.app.servertransaction.TransferSplashScreenViewStateItem obtain(android.os.IBinder iBinder, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, android.view.SurfaceControl surfaceControl) {
        android.app.servertransaction.TransferSplashScreenViewStateItem transferSplashScreenViewStateItem = (android.app.servertransaction.TransferSplashScreenViewStateItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.TransferSplashScreenViewStateItem.class);
        if (transferSplashScreenViewStateItem == null) {
            transferSplashScreenViewStateItem = new android.app.servertransaction.TransferSplashScreenViewStateItem();
        }
        transferSplashScreenViewStateItem.setActivityToken(iBinder);
        transferSplashScreenViewStateItem.mSplashScreenViewParcelable = splashScreenViewParcelable;
        transferSplashScreenViewStateItem.mStartingWindowLeash = surfaceControl;
        return transferSplashScreenViewStateItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.TransferSplashScreenViewStateItem transferSplashScreenViewStateItem = (android.app.servertransaction.TransferSplashScreenViewStateItem) obj;
        return java.util.Objects.equals(this.mSplashScreenViewParcelable, transferSplashScreenViewStateItem.mSplashScreenViewParcelable) && java.util.Objects.equals(this.mStartingWindowLeash, transferSplashScreenViewStateItem.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + java.util.Objects.hashCode(this.mSplashScreenViewParcelable)) * 31) + java.util.Objects.hashCode(this.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "TransferSplashScreenViewStateItem{" + super.toString() + ",splashScreenViewParcelable=" + this.mSplashScreenViewParcelable + ",startingWindowLeash=" + this.mStartingWindowLeash + "}";
    }
}
