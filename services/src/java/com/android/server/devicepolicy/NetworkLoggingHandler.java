package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class NetworkLoggingHandler extends android.os.Handler {
    private static final long BATCH_FINALIZATION_TIMEOUT_ALARM_INTERVAL_MS = 1800000;
    private static final long BATCH_FINALIZATION_TIMEOUT_MS = 5400000;

    @com.android.internal.annotations.VisibleForTesting
    static final int LOG_NETWORK_EVENT_MSG = 1;
    private static final int MAX_BATCHES = 5;
    private static final int MAX_EVENTS_PER_BATCH = 1200;
    static final java.lang.String NETWORK_EVENT_KEY = "network_event";
    private static final java.lang.String NETWORK_LOGGING_TIMEOUT_ALARM_TAG = "NetworkLogging.batchTimeout";
    private static final long RETRIEVED_BATCH_DISCARD_DELAY_MS = 300000;
    private final android.app.AlarmManager mAlarmManager;
    private final android.app.AlarmManager.OnAlarmListener mBatchTimeoutAlarmListener;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.LongSparseArray<java.util.ArrayList<android.app.admin.NetworkEvent>> mBatches;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mCurrentBatchToken;
    private final com.android.server.devicepolicy.DevicePolicyManagerService mDpm;
    private long mId;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastFinalizationNanos;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastRetrievedBatchToken;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.ArrayList<android.app.admin.NetworkEvent> mNetworkEvents;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mPaused;
    private int mTargetUserId;
    private static final java.lang.String TAG = com.android.server.devicepolicy.NetworkLoggingHandler.class.getSimpleName();
    private static final long FORCE_FETCH_THROTTLE_NS = java.util.concurrent.TimeUnit.SECONDS.toNanos(10);

    NetworkLoggingHandler(android.os.Looper looper, com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, int i) {
        this(looper, devicePolicyManagerService, 0L, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    NetworkLoggingHandler(android.os.Looper looper, com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, long j, int i) {
        super(looper);
        this.mLastFinalizationNanos = -1L;
        this.mBatchTimeoutAlarmListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.devicepolicy.NetworkLoggingHandler.1
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                android.os.Bundle finalizeBatchAndBuildAdminMessageLocked;
                android.util.Slog.d(com.android.server.devicepolicy.NetworkLoggingHandler.TAG, "Received a batch finalization timeout alarm, finalizing " + com.android.server.devicepolicy.NetworkLoggingHandler.this.mNetworkEvents.size() + " pending events.");
                synchronized (com.android.server.devicepolicy.NetworkLoggingHandler.this) {
                    finalizeBatchAndBuildAdminMessageLocked = com.android.server.devicepolicy.NetworkLoggingHandler.this.finalizeBatchAndBuildAdminMessageLocked();
                }
                if (finalizeBatchAndBuildAdminMessageLocked != null) {
                    com.android.server.devicepolicy.NetworkLoggingHandler.this.notifyDeviceOwnerOrProfileOwner(finalizeBatchAndBuildAdminMessageLocked);
                }
            }
        };
        this.mNetworkEvents = new java.util.ArrayList<>();
        this.mBatches = new android.util.LongSparseArray<>(5);
        this.mPaused = false;
        this.mDpm = devicePolicyManagerService;
        this.mAlarmManager = this.mDpm.mInjector.getAlarmManager();
        this.mId = j;
        this.mTargetUserId = i;
    }

    @Override // android.os.Handler
    public void handleMessage(android.os.Message message) {
        android.os.Bundle bundle;
        switch (message.what) {
            case 1:
                android.app.admin.NetworkEvent networkEvent = (android.app.admin.NetworkEvent) message.getData().getParcelable(NETWORK_EVENT_KEY, android.app.admin.NetworkEvent.class);
                if (networkEvent != null) {
                    synchronized (this) {
                        try {
                            this.mNetworkEvents.add(networkEvent);
                            if (this.mNetworkEvents.size() < MAX_EVENTS_PER_BATCH) {
                                bundle = null;
                            } else {
                                bundle = finalizeBatchAndBuildAdminMessageLocked();
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    if (bundle != null) {
                        notifyDeviceOwnerOrProfileOwner(bundle);
                        return;
                    }
                    return;
                }
                return;
            default:
                android.util.Slog.d(TAG, "NetworkLoggingHandler received an unknown of message.");
                return;
        }
    }

    void scheduleBatchFinalization() {
        this.mAlarmManager.setWindow(2, android.os.SystemClock.elapsedRealtime() + BATCH_FINALIZATION_TIMEOUT_MS, 1800000L, NETWORK_LOGGING_TIMEOUT_ALARM_TAG, this.mBatchTimeoutAlarmListener, this);
        android.util.Slog.d(TAG, "Scheduled a new batch finalization alarm 5400000ms from now.");
    }

    long forceBatchFinalization() {
        synchronized (this) {
            try {
                long nanoTime = (this.mLastFinalizationNanos + FORCE_FETCH_THROTTLE_NS) - java.lang.System.nanoTime();
                if (nanoTime > 0) {
                    return java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(nanoTime) + 1;
                }
                android.os.Bundle finalizeBatchAndBuildAdminMessageLocked = finalizeBatchAndBuildAdminMessageLocked();
                if (finalizeBatchAndBuildAdminMessageLocked != null) {
                    notifyDeviceOwnerOrProfileOwner(finalizeBatchAndBuildAdminMessageLocked);
                }
                return 0L;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    synchronized void pause() {
        android.util.Slog.d(TAG, "Paused network logging");
        this.mPaused = true;
    }

    void resume() {
        android.os.Bundle bundle;
        synchronized (this) {
            try {
                if (!this.mPaused) {
                    android.util.Slog.d(TAG, "Attempted to resume network logging, but logging is not paused.");
                    return;
                }
                android.util.Slog.d(TAG, "Resumed network logging. Current batch=" + this.mCurrentBatchToken + ", LastRetrievedBatch=" + this.mLastRetrievedBatchToken);
                this.mPaused = false;
                if (this.mBatches.size() > 0 && this.mLastRetrievedBatchToken != this.mCurrentBatchToken) {
                    scheduleBatchFinalization();
                    bundle = buildAdminMessageLocked();
                } else {
                    bundle = null;
                }
                if (bundle != null) {
                    notifyDeviceOwnerOrProfileOwner(bundle);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    synchronized void discardLogs() {
        this.mBatches.clear();
        this.mNetworkEvents = new java.util.ArrayList<>();
        android.util.Slog.d(TAG, "Discarded all network logs");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public android.os.Bundle finalizeBatchAndBuildAdminMessageLocked() {
        android.os.Bundle bundle;
        this.mLastFinalizationNanos = java.lang.System.nanoTime();
        if (this.mNetworkEvents.size() > 0) {
            java.util.Iterator<android.app.admin.NetworkEvent> it = this.mNetworkEvents.iterator();
            while (it.hasNext()) {
                it.next().setId(this.mId);
                if (this.mId == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    android.util.Slog.i(TAG, "Reached maximum id value; wrapping around ." + this.mCurrentBatchToken);
                    this.mId = 0L;
                } else {
                    this.mId++;
                }
            }
            if (this.mBatches.size() >= 5) {
                this.mBatches.removeAt(0);
            }
            this.mCurrentBatchToken++;
            this.mBatches.append(this.mCurrentBatchToken, this.mNetworkEvents);
            this.mNetworkEvents = new java.util.ArrayList<>();
            if (!this.mPaused) {
                bundle = buildAdminMessageLocked();
                scheduleBatchFinalization();
                return bundle;
            }
        } else {
            android.util.Slog.d(TAG, "Was about to finalize the batch, but there were no events to send to the DPC, the batchToken of last available batch: " + this.mCurrentBatchToken);
        }
        bundle = null;
        scheduleBatchFinalization();
        return bundle;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.os.Bundle buildAdminMessageLocked() {
        android.os.Bundle bundle = new android.os.Bundle();
        int size = this.mBatches.valueAt(this.mBatches.size() - 1).size();
        bundle.putLong("android.app.extra.EXTRA_NETWORK_LOGS_TOKEN", this.mCurrentBatchToken);
        bundle.putInt("android.app.extra.EXTRA_NETWORK_LOGS_COUNT", size);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDeviceOwnerOrProfileOwner(android.os.Bundle bundle) {
        if (java.lang.Thread.holdsLock(this)) {
            android.util.Slog.wtfStack(TAG, "Shouldn't be called with NetworkLoggingHandler lock held");
            return;
        }
        android.util.Slog.d(TAG, "Sending network logging batch broadcast to device owner or profile owner, batchToken: " + bundle.getLong("android.app.extra.EXTRA_NETWORK_LOGS_TOKEN", -1L));
        this.mDpm.sendDeviceOwnerOrProfileOwnerCommand("android.app.action.NETWORK_LOGS_AVAILABLE", bundle, this.mTargetUserId);
    }

    synchronized java.util.List<android.app.admin.NetworkEvent> retrieveFullLogBatch(final long j) {
        int indexOfKey = this.mBatches.indexOfKey(j);
        if (indexOfKey < 0) {
            return null;
        }
        postDelayed(new java.lang.Runnable() { // from class: com.android.server.devicepolicy.NetworkLoggingHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicepolicy.NetworkLoggingHandler.this.lambda$retrieveFullLogBatch$0(j);
            }
        }, 300000L);
        this.mLastRetrievedBatchToken = j;
        return this.mBatches.valueAt(indexOfKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$retrieveFullLogBatch$0(long j) {
        synchronized (this) {
            while (this.mBatches.size() > 0 && this.mBatches.keyAt(0) <= j) {
                try {
                    this.mBatches.removeAt(0);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
