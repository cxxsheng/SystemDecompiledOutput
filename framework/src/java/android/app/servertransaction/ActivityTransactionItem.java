package android.app.servertransaction;

/* loaded from: classes.dex */
public abstract class ActivityTransactionItem extends android.app.servertransaction.ClientTransactionItem {
    private android.os.IBinder mActivityToken;

    public abstract void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions);

    ActivityTransactionItem() {
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public final void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        execute(clientTransactionHandler, getActivityClientRecord(clientTransactionHandler), pendingTransactionActions);
    }

    final android.app.ActivityThread.ActivityClientRecord getActivityClientRecord(android.app.ClientTransactionHandler clientTransactionHandler) {
        android.app.ActivityThread.ActivityClientRecord activityClient = clientTransactionHandler.getActivityClient(getActivityToken());
        if (activityClient == null) {
            throw new java.lang.IllegalArgumentException("Activity client record must not be null to execute transaction item: " + this);
        }
        if (clientTransactionHandler.getActivity(getActivityToken()) == null) {
            throw new java.lang.IllegalArgumentException("Activity must not be null to execute transaction item: " + this);
        }
        return activityClient;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public android.os.IBinder getActivityToken() {
        return this.mActivityToken;
    }

    void setActivityToken(android.os.IBinder iBinder) {
        this.mActivityToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
    }

    ActivityTransactionItem(android.os.Parcel parcel) {
        this.mActivityToken = parcel.readStrongBinder();
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mActivityToken);
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mActivityToken = null;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    void dump(java.lang.String str, java.io.PrintWriter printWriter, android.app.ClientTransactionHandler clientTransactionHandler) {
        super.dump(str, printWriter, clientTransactionHandler);
        printWriter.append((java.lang.CharSequence) str).append("Target activity: ").println(android.app.servertransaction.TransactionExecutorHelper.getActivityName(this.mActivityToken, clientTransactionHandler));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mActivityToken, ((android.app.servertransaction.ActivityTransactionItem) obj).mActivityToken);
    }

    public int hashCode() {
        return java.util.Objects.hashCode(this.mActivityToken);
    }

    public java.lang.String toString() {
        return "mActivityToken=" + this.mActivityToken;
    }
}
