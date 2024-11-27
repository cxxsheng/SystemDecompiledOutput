package android.app.servertransaction;

/* loaded from: classes.dex */
public class ResumeActivityItem extends android.app.servertransaction.ActivityLifecycleItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.ResumeActivityItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.ResumeActivityItem>() { // from class: android.app.servertransaction.ResumeActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ResumeActivityItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.ResumeActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ResumeActivityItem[] newArray(int i) {
            return new android.app.servertransaction.ResumeActivityItem[i];
        }
    };
    private static final java.lang.String TAG = "ResumeActivityItem";
    private boolean mIsForward;
    private int mProcState;
    private boolean mShouldSendCompatFakeFocus;
    private boolean mUpdateProcState;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        if (this.mUpdateProcState) {
            clientTransactionHandler.updateProcessState(this.mProcState, false);
        }
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityResume");
        clientTransactionHandler.handleResumeActivity(activityClientRecord, true, this.mIsForward, this.mShouldSendCompatFakeFocus, "RESUME_ACTIVITY");
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.app.ActivityClient.getInstance().activityResumed(getActivityToken(), clientTransactionHandler.isHandleSplashScreenExit(getActivityToken()));
    }

    @Override // android.app.servertransaction.ActivityLifecycleItem
    public int getTargetState() {
        return 3;
    }

    private ResumeActivityItem() {
    }

    public static android.app.servertransaction.ResumeActivityItem obtain(android.os.IBinder iBinder, int i, boolean z, boolean z2) {
        android.app.servertransaction.ResumeActivityItem resumeActivityItem = (android.app.servertransaction.ResumeActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ResumeActivityItem.class);
        if (resumeActivityItem == null) {
            resumeActivityItem = new android.app.servertransaction.ResumeActivityItem();
        }
        resumeActivityItem.setActivityToken(iBinder);
        resumeActivityItem.mProcState = i;
        resumeActivityItem.mUpdateProcState = true;
        resumeActivityItem.mIsForward = z;
        resumeActivityItem.mShouldSendCompatFakeFocus = z2;
        return resumeActivityItem;
    }

    public static android.app.servertransaction.ResumeActivityItem obtain(android.os.IBinder iBinder, boolean z, boolean z2) {
        android.app.servertransaction.ResumeActivityItem resumeActivityItem = (android.app.servertransaction.ResumeActivityItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ResumeActivityItem.class);
        if (resumeActivityItem == null) {
            resumeActivityItem = new android.app.servertransaction.ResumeActivityItem();
        }
        resumeActivityItem.setActivityToken(iBinder);
        resumeActivityItem.mProcState = -1;
        resumeActivityItem.mUpdateProcState = false;
        resumeActivityItem.mIsForward = z;
        resumeActivityItem.mShouldSendCompatFakeFocus = z2;
        return resumeActivityItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mProcState = -1;
        this.mUpdateProcState = false;
        this.mIsForward = false;
        this.mShouldSendCompatFakeFocus = false;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mProcState);
        parcel.writeBoolean(this.mUpdateProcState);
        parcel.writeBoolean(this.mIsForward);
        parcel.writeBoolean(this.mShouldSendCompatFakeFocus);
    }

    private ResumeActivityItem(android.os.Parcel parcel) {
        super(parcel);
        this.mProcState = parcel.readInt();
        this.mUpdateProcState = parcel.readBoolean();
        this.mIsForward = parcel.readBoolean();
        this.mShouldSendCompatFakeFocus = parcel.readBoolean();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.ResumeActivityItem resumeActivityItem = (android.app.servertransaction.ResumeActivityItem) obj;
        return this.mProcState == resumeActivityItem.mProcState && this.mUpdateProcState == resumeActivityItem.mUpdateProcState && this.mIsForward == resumeActivityItem.mIsForward && this.mShouldSendCompatFakeFocus == resumeActivityItem.mShouldSendCompatFakeFocus;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((((527 + super.hashCode()) * 31) + this.mProcState) * 31) + (this.mUpdateProcState ? 1 : 0)) * 31) + (this.mIsForward ? 1 : 0)) * 31) + (this.mShouldSendCompatFakeFocus ? 1 : 0);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "ResumeActivityItem{" + super.toString() + ",procState=" + this.mProcState + ",updateProcState=" + this.mUpdateProcState + ",isForward=" + this.mIsForward + ",shouldSendCompatFakeFocus=" + this.mShouldSendCompatFakeFocus + "}";
    }
}
