package android.app.servertransaction;

/* loaded from: classes.dex */
public class ConfigurationChangeItem extends android.app.servertransaction.ClientTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.ConfigurationChangeItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.ConfigurationChangeItem>() { // from class: android.app.servertransaction.ConfigurationChangeItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ConfigurationChangeItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.ConfigurationChangeItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ConfigurationChangeItem[] newArray(int i) {
            return new android.app.servertransaction.ConfigurationChangeItem[i];
        }
    };
    private android.content.res.Configuration mConfiguration;
    private int mDeviceId;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfiguration);
        clientTransactionHandler.updatePendingConfiguration(this.mConfiguration);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleConfigurationChanged(this.mConfiguration, this.mDeviceId);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return android.app.ActivityThread.currentApplication();
    }

    private ConfigurationChangeItem() {
    }

    public static android.app.servertransaction.ConfigurationChangeItem obtain(android.content.res.Configuration configuration, int i) {
        android.app.servertransaction.ConfigurationChangeItem configurationChangeItem = (android.app.servertransaction.ConfigurationChangeItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ConfigurationChangeItem.class);
        if (configurationChangeItem == null) {
            configurationChangeItem = new android.app.servertransaction.ConfigurationChangeItem();
        }
        configurationChangeItem.mConfiguration = new android.content.res.Configuration(configuration);
        configurationChangeItem.mDeviceId = i;
        return configurationChangeItem;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mConfiguration = null;
        this.mDeviceId = 0;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeInt(this.mDeviceId);
    }

    private ConfigurationChangeItem(android.os.Parcel parcel) {
        this.mConfiguration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
        this.mDeviceId = parcel.readInt();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.servertransaction.ConfigurationChangeItem configurationChangeItem = (android.app.servertransaction.ConfigurationChangeItem) obj;
        if (java.util.Objects.equals(this.mConfiguration, configurationChangeItem.mConfiguration) && this.mDeviceId == configurationChangeItem.mDeviceId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((527 + this.mDeviceId) * 31) + this.mConfiguration.hashCode();
    }

    public java.lang.String toString() {
        return "ConfigurationChangeItem{deviceId=" + this.mDeviceId + ", config" + this.mConfiguration + "}";
    }
}
