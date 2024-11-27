package com.android.server.locksettings.recoverablekeystore;

/* loaded from: classes2.dex */
public class RecoverySnapshotListenersStorage {
    private static final java.lang.String TAG = "RecoverySnapshotLstnrs";

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.util.SparseArray<android.app.PendingIntent> mAgentIntents = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.util.ArraySet<java.lang.Integer> mAgentsWithPendingSnapshots = new android.util.ArraySet<>();

    public synchronized void setSnapshotListener(int i, @android.annotation.Nullable android.app.PendingIntent pendingIntent) {
        android.util.Log.i(TAG, "Registered listener for agent with uid " + i);
        this.mAgentIntents.put(i, pendingIntent);
        if (this.mAgentsWithPendingSnapshots.contains(java.lang.Integer.valueOf(i))) {
            android.util.Log.i(TAG, "Snapshot already created for agent. Immediately triggering intent.");
            tryToSendIntent(i, pendingIntent);
        }
    }

    public synchronized boolean hasListener(int i) {
        return this.mAgentIntents.get(i) != null;
    }

    public synchronized void recoverySnapshotAvailable(int i) {
        android.app.PendingIntent pendingIntent = this.mAgentIntents.get(i);
        if (pendingIntent == null) {
            android.util.Log.i(TAG, "Snapshot available for agent " + i + " but agent has not yet initialized. Will notify agent when it does.");
            this.mAgentsWithPendingSnapshots.add(java.lang.Integer.valueOf(i));
            return;
        }
        tryToSendIntent(i, pendingIntent);
    }

    private synchronized void tryToSendIntent(int i, android.app.PendingIntent pendingIntent) {
        try {
            pendingIntent.send();
            this.mAgentsWithPendingSnapshots.remove(java.lang.Integer.valueOf(i));
            android.util.Log.d(TAG, "Successfully notified listener.");
        } catch (android.app.PendingIntent.CanceledException e) {
            android.util.Log.e(TAG, "Failed to trigger PendingIntent for " + i, e);
            this.mAgentsWithPendingSnapshots.add(java.lang.Integer.valueOf(i));
        }
    }
}
