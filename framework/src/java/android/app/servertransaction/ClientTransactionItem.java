package android.app.servertransaction;

/* loaded from: classes.dex */
public abstract class ClientTransactionItem implements android.app.servertransaction.BaseClientRequest, android.os.Parcelable {
    public int getPostExecutionState() {
        return -1;
    }

    boolean shouldHaveDefinedPreExecutionState() {
        return true;
    }

    public android.content.Context getContextToUpdate(android.app.ClientTransactionHandler clientTransactionHandler) {
        return null;
    }

    public android.os.IBinder getActivityToken() {
        return null;
    }

    public boolean isActivityLifecycleItem() {
        return false;
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter, android.app.ClientTransactionHandler clientTransactionHandler) {
        printWriter.append((java.lang.CharSequence) str).println(this);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
