package com.android.server.wm;

/* loaded from: classes3.dex */
public class ActivityServiceConnectionsHolder<T> {
    private final com.android.server.wm.ActivityRecord mActivity;

    @com.android.internal.annotations.GuardedBy({"mActivity"})
    private android.util.ArraySet<T> mConnections;
    private volatile boolean mIsDisconnecting;

    ActivityServiceConnectionsHolder(com.android.server.wm.ActivityRecord activityRecord) {
        this.mActivity = activityRecord;
    }

    public void addConnection(T t) {
        synchronized (this.mActivity) {
            try {
                if (this.mIsDisconnecting) {
                    return;
                }
                if (this.mConnections == null) {
                    this.mConnections = new android.util.ArraySet<>();
                }
                this.mConnections.add(t);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeConnection(T t) {
        synchronized (this.mActivity) {
            try {
                if (this.mConnections == null) {
                    return;
                }
                this.mConnections.remove(t);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isActivityVisible() {
        return this.mActivity.mVisibleForServiceConnection;
    }

    public int getActivityPid() {
        com.android.server.wm.WindowProcessController windowProcessController = this.mActivity.app;
        if (windowProcessController != null) {
            return windowProcessController.getPid();
        }
        return -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void forEachConnection(java.util.function.Consumer<T> consumer) {
        synchronized (this.mActivity) {
            if (this.mConnections == null || this.mConnections.isEmpty()) {
                return;
            }
            android.util.ArraySet arraySet = new android.util.ArraySet((android.util.ArraySet) this.mConnections);
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                consumer.accept(arraySet.valueAt(size));
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mActivity"})
    void disconnectActivityFromServices() {
        if (this.mConnections == null || this.mConnections.isEmpty() || this.mIsDisconnecting) {
            return;
        }
        this.mIsDisconnecting = true;
        this.mActivity.mAtmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityServiceConnectionsHolder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityServiceConnectionsHolder.this.lambda$disconnectActivityFromServices$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectActivityFromServices$0() {
        this.mActivity.mAtmService.mAmInternal.disconnectActivityFromServices(this);
        this.mIsDisconnecting = false;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "activity=" + this.mActivity);
    }

    public java.lang.String toString() {
        return java.lang.String.valueOf(this.mConnections);
    }
}
