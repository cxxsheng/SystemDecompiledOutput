package android.app.servertransaction;

/* loaded from: classes.dex */
public class NewIntentItem extends android.app.servertransaction.ActivityTransactionItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.NewIntentItem> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.NewIntentItem>() { // from class: android.app.servertransaction.NewIntentItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.NewIntentItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.NewIntentItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.NewIntentItem[] newArray(int i) {
            return new android.app.servertransaction.NewIntentItem[i];
        }
    };
    private java.util.List<com.android.internal.content.ReferrerIntent> mIntents;
    private boolean mResume;

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return this.mResume ? 3 : -1;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.os.Trace.traceBegin(64L, "activityNewIntent");
        clientTransactionHandler.handleNewIntent(activityClientRecord, this.mIntents);
        android.os.Trace.traceEnd(64L);
    }

    private NewIntentItem() {
    }

    public static android.app.servertransaction.NewIntentItem obtain(android.os.IBinder iBinder, java.util.List<com.android.internal.content.ReferrerIntent> list, boolean z) {
        android.app.servertransaction.NewIntentItem newIntentItem = (android.app.servertransaction.NewIntentItem) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.NewIntentItem.class);
        if (newIntentItem == null) {
            newIntentItem = new android.app.servertransaction.NewIntentItem();
        }
        newIntentItem.setActivityToken(iBinder);
        newIntentItem.mIntents = new java.util.ArrayList(list);
        newIntentItem.mResume = z;
        return newIntentItem;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mIntents = null;
        this.mResume = false;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mResume);
        parcel.writeTypedList(this.mIntents, i);
    }

    private NewIntentItem(android.os.Parcel parcel) {
        super(parcel);
        this.mResume = parcel.readBoolean();
        this.mIntents = parcel.createTypedArrayList(com.android.internal.content.ReferrerIntent.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.app.servertransaction.NewIntentItem newIntentItem = (android.app.servertransaction.NewIntentItem) obj;
        return this.mResume == newIntentItem.mResume && java.util.Objects.equals(this.mIntents, newIntentItem.mIntents);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + (this.mResume ? 1 : 0)) * 31) + this.mIntents.hashCode();
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public java.lang.String toString() {
        return "NewIntentItem{" + super.toString() + ",intents=" + this.mIntents + ",resume=" + this.mResume + "}";
    }
}
