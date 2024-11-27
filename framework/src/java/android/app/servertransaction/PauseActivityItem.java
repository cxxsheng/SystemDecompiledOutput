package android.app.servertransaction;

/* loaded from: classes.dex */
public class PauseActivityItem extends android.app.servertransaction.ActivityLifecycleItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.PauseActivityItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.PauseActivityItem>() { // from class: android.app.servertransaction.PauseActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.PauseActivityItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.PauseActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.PauseActivityItem[] newArray(int i) {
            return new android.app.servertransaction.PauseActivityItem[i];
        }
    };
    private static final java.lang.String TAG = "PauseActivityItem";
    private boolean mAutoEnteringPip;
    private int mConfigChanges;
    private boolean mDontReport;
    private boolean mFinished;
    private boolean mUserLeaving;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityPause");
        clientTransactionHandler.handlePauseActivity(activityClientRecord, this.mFinished, this.mUserLeaving, this.mConfigChanges, this.mAutoEnteringPip, pendingTransactionActions, "PAUSE_ACTIVITY_ITEM");
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 4;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        if (this.mDontReport) {
            return;
        }
        android.app.ActivityClient.getInstance().activityPaused(getActivityToken());
    }

    private PauseActivityItem() {
    }

    public static android.app.servertransaction.PauseActivityItem obtain(android.os.IBinder iBinder, boolean z, boolean z2, int i, boolean z3, boolean z4) {
        android.app.servertransaction.PauseActivityItem pauseActivityItem = (android.app.servertransaction.PauseActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.PauseActivityItem.class);
        if (pauseActivityItem == null) {
            pauseActivityItem = new android.app.servertransaction.PauseActivityItem();
        }
        pauseActivityItem.setActivityToken(iBinder);
        pauseActivityItem.mFinished = z;
        pauseActivityItem.mUserLeaving = z2;
        pauseActivityItem.mConfigChanges = i;
        pauseActivityItem.mDontReport = z3;
        pauseActivityItem.mAutoEnteringPip = z4;
        return pauseActivityItem;
    }

    public static android.app.servertransaction.PauseActivityItem obtain(android.os.IBinder iBinder) {
        return obtain(iBinder, false, false, 0, true, false);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mFinished = false;
        this.mUserLeaving = false;
        this.mConfigChanges = 0;
        this.mDontReport = false;
        this.mAutoEnteringPip = false;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mFinished);
        parcel.writeBoolean(this.mUserLeaving);
        parcel.writeInt(this.mConfigChanges);
        parcel.writeBoolean(this.mDontReport);
        parcel.writeBoolean(this.mAutoEnteringPip);
    }

    private PauseActivityItem(android.os.Parcel parcel) {
        super(parcel);
        this.mFinished = parcel.readBoolean();
        this.mUserLeaving = parcel.readBoolean();
        this.mConfigChanges = parcel.readInt();
        this.mDontReport = parcel.readBoolean();
        this.mAutoEnteringPip = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.PauseActivityItem pauseActivityItem = (android.app.servertransaction.PauseActivityItem) obj;
        return this.mFinished == pauseActivityItem.mFinished && this.mUserLeaving == pauseActivityItem.mUserLeaving && this.mConfigChanges == pauseActivityItem.mConfigChanges && this.mDontReport == pauseActivityItem.mDontReport && this.mAutoEnteringPip == pauseActivityItem.mAutoEnteringPip;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((((((527 + super.hashCode()) * 31) + (this.mFinished ? 1 : 0)) * 31) + (this.mUserLeaving ? 1 : 0)) * 31) + this.mConfigChanges) * 31) + (this.mDontReport ? 1 : 0)) * 31) + (this.mAutoEnteringPip ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "PauseActivityItem{" + super.toString() + ",finished=" + this.mFinished + ",userLeaving=" + this.mUserLeaving + ",configChanges=" + this.mConfigChanges + ",dontReport=" + this.mDontReport + ",autoEnteringPip=" + this.mAutoEnteringPip + "}";
    }
}
