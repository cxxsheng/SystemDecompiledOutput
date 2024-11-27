package android.app.servertransaction;

/* loaded from: classes.dex */
public class ActivityRelaunchItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.ActivityRelaunchItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.ActivityRelaunchItem>() { // from class: android.app.servertransaction.ActivityRelaunchItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ActivityRelaunchItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.ActivityRelaunchItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ActivityRelaunchItem[] newArray(int i) {
            return new android.app.servertransaction.ActivityRelaunchItem[i];
        }
    };
    private static final java.lang.String TAG = "ActivityRelaunchItem";
    private android.app.ActivityThread.ActivityClientRecord mActivityClientRecord;
    private android.window.ActivityWindowInfo mActivityWindowInfo;
    private android.util.MergedConfiguration mConfig;
    private int mConfigChanges;
    private java.util.List<com.android.internal.content.ReferrerIntent> mPendingNewIntents;
    private java.util.List<android.app.ResultInfo> mPendingResults;
    private boolean mPreserveWindow;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        if (!clientTransactionHandler.isExecutingLocalTransaction()) {
            android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfig);
        }
        this.mActivityClientRecord = clientTransactionHandler.prepareRelaunchActivity(getActivityToken(), this.mPendingResults, this.mPendingNewIntents, this.mConfigChanges, this.mConfig, this.mPreserveWindow, this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        if (this.mActivityClientRecord == null) {
            return;
        }
        android.os.Trace.traceBegin(64L, "activityRestart");
        clientTransactionHandler.handleRelaunchActivity(this.mActivityClientRecord, pendingTransactionActions);
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.reportRelaunch(getActivityClientRecord(clientTransactionHandler));
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return clientTransactionHandler.getActivity(getActivityToken());
    }

    private ActivityRelaunchItem() {
    }

    public static android.app.servertransaction.ActivityRelaunchItem obtain(android.os.IBinder iBinder, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, int i, android.util.MergedConfiguration mergedConfiguration, boolean z, android.window.ActivityWindowInfo activityWindowInfo) {
        android.app.servertransaction.ActivityRelaunchItem activityRelaunchItem = (android.app.servertransaction.ActivityRelaunchItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ActivityRelaunchItem.class);
        if (activityRelaunchItem == null) {
            activityRelaunchItem = new android.app.servertransaction.ActivityRelaunchItem();
        }
        activityRelaunchItem.setActivityToken(iBinder);
        activityRelaunchItem.mPendingResults = list != null ? new java.util.ArrayList(list) : null;
        activityRelaunchItem.mPendingNewIntents = list2 != null ? new java.util.ArrayList(list2) : null;
        activityRelaunchItem.mConfigChanges = i;
        activityRelaunchItem.mConfig = new android.util.MergedConfiguration(mergedConfiguration);
        activityRelaunchItem.mPreserveWindow = z;
        activityRelaunchItem.mActivityWindowInfo = new android.window.ActivityWindowInfo(activityWindowInfo);
        return activityRelaunchItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mPendingResults = null;
        this.mPendingNewIntents = null;
        this.mConfigChanges = 0;
        this.mConfig = null;
        this.mPreserveWindow = false;
        this.mActivityClientRecord = null;
        this.mActivityWindowInfo = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mPendingResults, i);
        parcel.writeTypedList(this.mPendingNewIntents, i);
        parcel.writeInt(this.mConfigChanges);
        parcel.writeTypedObject(this.mConfig, i);
        parcel.writeBoolean(this.mPreserveWindow);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private ActivityRelaunchItem(android.os.Parcel parcel) {
        super(parcel);
        this.mPendingResults = parcel.createTypedArrayList(android.app.ResultInfo.CREATOR);
        this.mPendingNewIntents = parcel.createTypedArrayList(com.android.internal.content.ReferrerIntent.CREATOR);
        this.mConfigChanges = parcel.readInt();
        this.mConfig = (android.util.MergedConfiguration) parcel.readTypedObject(android.util.MergedConfiguration.CREATOR);
        this.mPreserveWindow = parcel.readBoolean();
        this.mActivityWindowInfo = (android.window.ActivityWindowInfo) parcel.readTypedObject(android.window.ActivityWindowInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.ActivityRelaunchItem activityRelaunchItem = (android.app.servertransaction.ActivityRelaunchItem) obj;
        return java.util.Objects.equals(this.mPendingResults, activityRelaunchItem.mPendingResults) && java.util.Objects.equals(this.mPendingNewIntents, activityRelaunchItem.mPendingNewIntents) && this.mConfigChanges == activityRelaunchItem.mConfigChanges && java.util.Objects.equals(this.mConfig, activityRelaunchItem.mConfig) && this.mPreserveWindow == activityRelaunchItem.mPreserveWindow && java.util.Objects.equals(this.mActivityWindowInfo, activityRelaunchItem.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((((((((((527 + super.hashCode()) * 31) + java.util.Objects.hashCode(this.mPendingResults)) * 31) + java.util.Objects.hashCode(this.mPendingNewIntents)) * 31) + this.mConfigChanges) * 31) + java.util.Objects.hashCode(this.mConfig)) * 31) + (this.mPreserveWindow ? 1 : 0)) * 31) + java.util.Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "ActivityRelaunchItem{" + super.toString() + ",pendingResults=" + this.mPendingResults + ",pendingNewIntents=" + this.mPendingNewIntents + ",configChanges=" + this.mConfigChanges + ",config=" + this.mConfig + ",preserveWindow=" + this.mPreserveWindow + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
