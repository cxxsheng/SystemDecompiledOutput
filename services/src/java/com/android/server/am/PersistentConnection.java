package com.android.server.am;

/* loaded from: classes.dex */
public abstract class PersistentConnection<T> {
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBound;
    private final android.content.ComponentName mComponentName;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsConnected;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastConnectedTime;
    private long mNextBackoffMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNumBindingDied;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNumConnected;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNumDisconnected;
    private final double mRebindBackoffIncrease;
    private final long mRebindBackoffMs;
    private final long mRebindMaxBackoffMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mRebindScheduled;
    private long mReconnectTime;
    private final long mResetBackoffDelay;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private T mService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mShouldBeBound;
    private final java.lang.String mTag;
    private final int mUserId;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.content.ServiceConnection mServiceConnection = new android.content.ServiceConnection() { // from class: com.android.server.am.PersistentConnection.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.am.PersistentConnection.this.mLock) {
                try {
                    if (!com.android.server.am.PersistentConnection.this.mBound) {
                        android.util.Log.w(com.android.server.am.PersistentConnection.this.mTag, "Connected: " + com.android.server.am.PersistentConnection.this.mComponentName.flattenToShortString() + " u" + com.android.server.am.PersistentConnection.this.mUserId + " but not bound, ignore.");
                        return;
                    }
                    android.util.Log.i(com.android.server.am.PersistentConnection.this.mTag, "Connected: " + com.android.server.am.PersistentConnection.this.mComponentName.flattenToShortString() + " u" + com.android.server.am.PersistentConnection.this.mUserId);
                    com.android.server.am.PersistentConnection persistentConnection = com.android.server.am.PersistentConnection.this;
                    persistentConnection.mNumConnected = persistentConnection.mNumConnected + 1;
                    com.android.server.am.PersistentConnection.this.mIsConnected = true;
                    com.android.server.am.PersistentConnection.this.mLastConnectedTime = com.android.server.am.PersistentConnection.this.injectUptimeMillis();
                    com.android.server.am.PersistentConnection.this.mService = com.android.server.am.PersistentConnection.this.asInterface(iBinder);
                    com.android.server.am.PersistentConnection.this.scheduleStableCheckLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.server.am.PersistentConnection.this.mLock) {
                android.util.Log.i(com.android.server.am.PersistentConnection.this.mTag, "Disconnected: " + com.android.server.am.PersistentConnection.this.mComponentName.flattenToShortString() + " u" + com.android.server.am.PersistentConnection.this.mUserId);
                com.android.server.am.PersistentConnection persistentConnection = com.android.server.am.PersistentConnection.this;
                persistentConnection.mNumDisconnected = persistentConnection.mNumDisconnected + 1;
                com.android.server.am.PersistentConnection.this.cleanUpConnectionLocked();
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            synchronized (com.android.server.am.PersistentConnection.this.mLock) {
                try {
                    if (!com.android.server.am.PersistentConnection.this.mBound) {
                        android.util.Log.w(com.android.server.am.PersistentConnection.this.mTag, "Binding died: " + com.android.server.am.PersistentConnection.this.mComponentName.flattenToShortString() + " u" + com.android.server.am.PersistentConnection.this.mUserId + " but not bound, ignore.");
                        return;
                    }
                    android.util.Log.w(com.android.server.am.PersistentConnection.this.mTag, "Binding died: " + com.android.server.am.PersistentConnection.this.mComponentName.flattenToShortString() + " u" + com.android.server.am.PersistentConnection.this.mUserId);
                    com.android.server.am.PersistentConnection persistentConnection = com.android.server.am.PersistentConnection.this;
                    persistentConnection.mNumBindingDied = persistentConnection.mNumBindingDied + 1;
                    com.android.server.am.PersistentConnection.this.scheduleRebindLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };
    private final java.lang.Runnable mBindForBackoffRunnable = new java.lang.Runnable() { // from class: com.android.server.am.PersistentConnection$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.am.PersistentConnection.this.lambda$new$0();
        }
    };
    private final java.lang.Runnable mStableCheck = new java.lang.Runnable() { // from class: com.android.server.am.PersistentConnection$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.am.PersistentConnection.this.stableConnectionCheck();
        }
    };

    protected abstract T asInterface(android.os.IBinder iBinder);

    protected abstract int getBindFlags();

    public PersistentConnection(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.NonNull android.content.ComponentName componentName, long j, double d, long j2, long j3) {
        this.mTag = str;
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = i;
        this.mComponentName = componentName;
        this.mRebindBackoffMs = j * 1000;
        this.mRebindBackoffIncrease = d;
        this.mRebindMaxBackoffMs = j2 * 1000;
        this.mResetBackoffDelay = j3 * 1000;
        this.mNextBackoffMs = this.mRebindBackoffMs;
    }

    public final android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final int getUserId() {
        return this.mUserId;
    }

    public final boolean isBound() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mBound;
        }
        return z;
    }

    public final boolean isRebindScheduled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mRebindScheduled;
        }
        return z;
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsConnected;
        }
        return z;
    }

    public final T getServiceBinder() {
        T t;
        synchronized (this.mLock) {
            t = this.mService;
        }
        return t;
    }

    public final void bind() {
        synchronized (this.mLock) {
            this.mShouldBeBound = true;
            bindInnerLocked(true);
        }
    }

    public long getNextBackoffMs() {
        long j;
        synchronized (this.mLock) {
            j = this.mNextBackoffMs;
        }
        return j;
    }

    public int getNumConnected() {
        int i;
        synchronized (this.mLock) {
            i = this.mNumConnected;
        }
        return i;
    }

    public int getNumDisconnected() {
        int i;
        synchronized (this.mLock) {
            i = this.mNumDisconnected;
        }
        return i;
    }

    public int getNumBindingDied() {
        int i;
        synchronized (this.mLock) {
            i = this.mNumBindingDied;
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resetBackoffLocked() {
        if (this.mNextBackoffMs != this.mRebindBackoffMs) {
            this.mNextBackoffMs = this.mRebindBackoffMs;
            android.util.Log.i(this.mTag, "Backoff reset to " + this.mNextBackoffMs);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public final void bindInnerLocked(boolean z) {
        unscheduleRebindLocked();
        if (this.mBound) {
            return;
        }
        this.mBound = true;
        unscheduleStableCheckLocked();
        if (z) {
            resetBackoffLocked();
        }
        android.content.Intent component = new android.content.Intent().setComponent(this.mComponentName);
        if (!this.mContext.bindServiceAsUser(component, this.mServiceConnection, getBindFlags() | 1, this.mHandler, android.os.UserHandle.of(this.mUserId))) {
            android.util.Log.e(this.mTag, "Binding: " + component.getComponent() + " u" + this.mUserId + " failed.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: bindForBackoff, reason: merged with bridge method [inline-methods] */
    public final void lambda$new$0() {
        synchronized (this.mLock) {
            try {
                if (this.mShouldBeBound) {
                    bindInnerLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void cleanUpConnectionLocked() {
        this.mIsConnected = false;
        this.mService = null;
        unscheduleStableCheckLocked();
    }

    public final void unbind() {
        synchronized (this.mLock) {
            this.mShouldBeBound = false;
            unbindLocked();
            unscheduleStableCheckLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final void unbindLocked() {
        unscheduleRebindLocked();
        if (!this.mBound) {
            return;
        }
        android.util.Log.i(this.mTag, "Stopping: " + this.mComponentName.flattenToShortString() + " u" + this.mUserId);
        this.mBound = false;
        this.mContext.unbindService(this.mServiceConnection);
        cleanUpConnectionLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void unscheduleRebindLocked() {
        injectRemoveCallbacks(this.mBindForBackoffRunnable);
        this.mRebindScheduled = false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void scheduleRebindLocked() {
        unbindLocked();
        if (!this.mRebindScheduled) {
            android.util.Log.i(this.mTag, "Scheduling to reconnect in " + this.mNextBackoffMs + " ms (uptime)");
            this.mReconnectTime = injectUptimeMillis() + this.mNextBackoffMs;
            injectPostAtTime(this.mBindForBackoffRunnable, this.mReconnectTime);
            this.mNextBackoffMs = java.lang.Math.min(this.mRebindMaxBackoffMs, (long) (((double) this.mNextBackoffMs) * this.mRebindBackoffIncrease));
            this.mRebindScheduled = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stableConnectionCheck() {
        synchronized (this.mLock) {
            try {
                long injectUptimeMillis = (this.mLastConnectedTime + this.mResetBackoffDelay) - injectUptimeMillis();
                if (this.mBound && this.mIsConnected && injectUptimeMillis <= 0) {
                    resetBackoffLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unscheduleStableCheckLocked() {
        injectRemoveCallbacks(this.mStableCheck);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void scheduleStableCheckLocked() {
        unscheduleStableCheckLocked();
        injectPostAtTime(this.mStableCheck, injectUptimeMillis() + this.mResetBackoffDelay);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.print(str);
                printWriter.print(this.mComponentName.flattenToShortString());
                printWriter.print(" u");
                printWriter.print(this.mUserId);
                printWriter.print(this.mBound ? " [bound]" : " [not bound]");
                printWriter.print(this.mIsConnected ? " [connected]" : " [not connected]");
                if (this.mRebindScheduled) {
                    printWriter.print(" reconnect in ");
                    android.util.TimeUtils.formatDuration(this.mReconnectTime - injectUptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  Next backoff(sec): ");
                printWriter.print(this.mNextBackoffMs / 1000);
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  Connected: ");
                printWriter.print(this.mNumConnected);
                printWriter.print("  Disconnected: ");
                printWriter.print(this.mNumDisconnected);
                printWriter.print("  Died: ");
                printWriter.print(this.mNumBindingDied);
                if (this.mIsConnected) {
                    printWriter.print("  Duration: ");
                    android.util.TimeUtils.formatDuration(injectUptimeMillis() - this.mLastConnectedTime, printWriter);
                }
                printWriter.println();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectRemoveCallbacks(java.lang.Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectPostAtTime(java.lang.Runnable runnable, long j) {
        this.mHandler.postAtTime(runnable, j);
    }

    @com.android.internal.annotations.VisibleForTesting
    long injectUptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }

    @com.android.internal.annotations.VisibleForTesting
    long getNextBackoffMsForTest() {
        return this.mNextBackoffMs;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getReconnectTimeForTest() {
        return this.mReconnectTime;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.ServiceConnection getServiceConnectionForTest() {
        return this.mServiceConnection;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.Runnable getBindForBackoffRunnableForTest() {
        return this.mBindForBackoffRunnable;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.Runnable getStableCheckRunnableForTest() {
        return this.mStableCheck;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldBeBoundForTest() {
        return this.mShouldBeBound;
    }
}
