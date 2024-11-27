package android.app.servertransaction;

/* loaded from: classes.dex */
public class ActivityConfigurationChangeItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.ActivityConfigurationChangeItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.ActivityConfigurationChangeItem>() { // from class: android.app.servertransaction.ActivityConfigurationChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ActivityConfigurationChangeItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.ActivityConfigurationChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ActivityConfigurationChangeItem[] newArray(int i) {
            return new android.app.servertransaction.ActivityConfigurationChangeItem[i];
        }
    };
    private android.window.ActivityWindowInfo mActivityWindowInfo;
    private android.content.res.Configuration mConfiguration;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfiguration);
        clientTransactionHandler.updatePendingActivityConfiguration(getActivityToken(), this.mConfiguration);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityConfigChanged");
        clientTransactionHandler.handleActivityConfigurationChanged(activityClientRecord, this.mConfiguration, -1, this.mActivityWindowInfo);
        android.os.Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return clientTransactionHandler.getActivity(getActivityToken());
    }

    private ActivityConfigurationChangeItem() {
    }

    public static android.app.servertransaction.ActivityConfigurationChangeItem obtain(android.os.IBinder iBinder, android.content.res.Configuration configuration, android.window.ActivityWindowInfo activityWindowInfo) {
        android.app.servertransaction.ActivityConfigurationChangeItem activityConfigurationChangeItem = (android.app.servertransaction.ActivityConfigurationChangeItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ActivityConfigurationChangeItem.class);
        if (activityConfigurationChangeItem == null) {
            activityConfigurationChangeItem = new android.app.servertransaction.ActivityConfigurationChangeItem();
        }
        activityConfigurationChangeItem.setActivityToken(iBinder);
        activityConfigurationChangeItem.mConfiguration = new android.content.res.Configuration(configuration);
        activityConfigurationChangeItem.mActivityWindowInfo = new android.window.ActivityWindowInfo(activityWindowInfo);
        return activityConfigurationChangeItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mConfiguration = null;
        this.mActivityWindowInfo = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
    }

    private ActivityConfigurationChangeItem(android.os.Parcel parcel) {
        super(parcel);
        this.mConfiguration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
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
        android.app.servertransaction.ActivityConfigurationChangeItem activityConfigurationChangeItem = (android.app.servertransaction.ActivityConfigurationChangeItem) obj;
        return java.util.Objects.equals(this.mConfiguration, activityConfigurationChangeItem.mConfiguration) && java.util.Objects.equals(this.mActivityWindowInfo, activityConfigurationChangeItem.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + java.util.Objects.hashCode(this.mConfiguration)) * 31) + java.util.Objects.hashCode(this.mActivityWindowInfo);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "ActivityConfigurationChange{" + super.toString() + ",config=" + this.mConfiguration + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
