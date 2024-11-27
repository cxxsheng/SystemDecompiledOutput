package android.app.servertransaction;

/* loaded from: classes.dex */
public class PendingTransactionActions {
    private boolean mCallOnPostCreate;
    private android.os.Bundle mOldState;
    private boolean mRestoreInstanceState;
    private android.app.servertransaction.PendingTransactionActions.StopInfo mStopInfo;

    public PendingTransactionActions() {
        clear();
    }

    public void clear() {
        this.mRestoreInstanceState = false;
        this.mCallOnPostCreate = false;
        this.mOldState = null;
        this.mStopInfo = null;
    }

    public boolean shouldRestoreInstanceState() {
        return this.mRestoreInstanceState;
    }

    public void setRestoreInstanceState(boolean z) {
        this.mRestoreInstanceState = z;
    }

    public boolean shouldCallOnPostCreate() {
        return this.mCallOnPostCreate;
    }

    public void setCallOnPostCreate(boolean z) {
        this.mCallOnPostCreate = z;
    }

    public android.os.Bundle getOldState() {
        return this.mOldState;
    }

    public void setOldState(android.os.Bundle bundle) {
        this.mOldState = bundle;
    }

    public android.app.servertransaction.PendingTransactionActions.StopInfo getStopInfo() {
        return this.mStopInfo;
    }

    public void setStopInfo(android.app.servertransaction.PendingTransactionActions.StopInfo stopInfo) {
        this.mStopInfo = stopInfo;
    }

    public static class StopInfo implements java.lang.Runnable {
        private static final java.lang.String TAG = "ActivityStopInfo";
        private android.app.ActivityThread.ActivityClientRecord mActivity;
        private java.lang.CharSequence mDescription;
        private android.os.PersistableBundle mPersistentState;
        private android.os.Bundle mState;

        public void setActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
            this.mActivity = activityClientRecord;
        }

        public void setState(android.os.Bundle bundle) {
            this.mState = bundle;
        }

        public void setPersistentState(android.os.PersistableBundle persistableBundle) {
            this.mPersistentState = persistableBundle;
        }

        public void setDescription(java.lang.CharSequence charSequence) {
            this.mDescription = charSequence;
        }

        private java.lang.String collectBundleStates() {
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(stringWriter, "  ");
            indentingPrintWriter.println("Bundle stats:");
            android.os.Bundle.dumpStats(indentingPrintWriter, this.mState);
            indentingPrintWriter.println("PersistableBundle stats:");
            android.os.Bundle.dumpStats(indentingPrintWriter, this.mPersistentState);
            return stringWriter.toString().stripTrailing();
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                android.app.ActivityClient.getInstance().activityStopped(this.mActivity.token, this.mState, this.mPersistentState, this.mDescription);
            } catch (java.lang.RuntimeException e) {
                java.lang.String collectBundleStates = collectBundleStates();
                if (e.getCause() instanceof android.os.TransactionTooLargeException) {
                    java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(e.getMessage() + "\n" + collectBundleStates, e.getCause());
                    if (this.mActivity.packageInfo.getTargetSdkVersion() < 24) {
                        android.util.Log.e(TAG, "App sent too much data in instance state, so it was ignored", runtimeException);
                        return;
                    }
                    throw runtimeException;
                }
                android.util.Log.w(TAG, collectBundleStates);
                throw e;
            }
        }
    }
}
