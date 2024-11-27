package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class SecurityLogMonitor implements java.lang.Runnable {
    private static final int BUFFER_ENTRIES_CRITICAL_LEVEL = 9216;
    private static final int BUFFER_ENTRIES_MAXIMUM_LEVEL = 10240;

    @com.android.internal.annotations.VisibleForTesting
    static final int BUFFER_ENTRIES_NOTIFICATION_LEVEL = 1024;
    private static final boolean DEBUG = false;
    private static final int MAX_AUDIT_LOG_EVENTS = 10000;
    private static final java.lang.String TAG = "SecurityLogMonitor";
    private boolean mAuditLogEnabled;
    private int mEnabledUser;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mForceSemaphore"})
    private long mLastForceNanos;
    private boolean mLegacyLogEnabled;
    private final com.android.server.devicepolicy.DevicePolicyManagerService mService;
    private static final long RATE_LIMIT_INTERVAL_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(2);
    private static final long BROADCAST_RETRY_INTERVAL_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(30);
    private static final long POLLING_INTERVAL_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(1);
    private static final long OVERLAP_NS = java.util.concurrent.TimeUnit.SECONDS.toNanos(3);
    private static final long FORCE_FETCH_THROTTLE_NS = java.util.concurrent.TimeUnit.SECONDS.toNanos(10);
    private static final long MAX_AUDIT_LOG_EVENT_AGE_NS = java.util.concurrent.TimeUnit.HOURS.toNanos(8);
    private final java.util.concurrent.locks.Lock mLock = new java.util.concurrent.locks.ReentrantLock();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Thread mMonitorThread = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> mPendingLogs = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAllowedToRetrieve = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mCriticalLevelLogged = false;
    private final java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> mLastEvents = new java.util.ArrayList<>();
    private long mLastEventNanos = -1;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mNextAllowedRetrievalTimeMillis = -1;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPaused = false;
    private final java.util.concurrent.Semaphore mForceSemaphore = new java.util.concurrent.Semaphore(0);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.app.admin.IAuditLogEventsCallback> mAuditLogCallbacks = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayDeque<android.app.admin.SecurityLog.SecurityEvent> mAuditLogEventBuffer = new java.util.ArrayDeque<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mId = 0;

    SecurityLogMonitor(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, android.os.Handler handler) {
        this.mLastForceNanos = 0L;
        this.mService = devicePolicyManagerService;
        this.mLastForceNanos = java.lang.System.nanoTime();
        this.mHandler = handler;
    }

    void start(int i) {
        android.util.Slog.i(TAG, "Starting security logging for user " + i);
        this.mEnabledUser = i;
        this.mLock.lock();
        try {
            if (this.mMonitorThread == null) {
                resetLegacyBufferLocked();
                startMonitorThreadLocked();
            } else {
                android.util.Slog.i(TAG, "Security log monitor thread is already running");
            }
            this.mLock.unlock();
        } catch (java.lang.Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    void stop() {
        android.util.Slog.i(TAG, "Stopping security logging.");
        this.mLock.lock();
        try {
            if (this.mMonitorThread != null) {
                stopMonitorThreadLocked();
                resetLegacyBufferLocked();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    void setLoggingParams(int i, boolean z, boolean z2) {
        com.android.server.utils.Slogf.i(TAG, "Setting logging params, user = %d -> %d, legacy: %b -> %b, audit %b -> %b", java.lang.Integer.valueOf(this.mEnabledUser), java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(this.mLegacyLogEnabled), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(this.mAuditLogEnabled), java.lang.Boolean.valueOf(z2));
        this.mLock.lock();
        try {
            this.mEnabledUser = i;
            if (this.mMonitorThread == null && (z || z2)) {
                startMonitorThreadLocked();
            } else if (this.mMonitorThread != null && !z && !z2) {
                stopMonitorThreadLocked();
            }
            if (this.mLegacyLogEnabled != z) {
                resetLegacyBufferLocked();
                this.mLegacyLogEnabled = z;
            }
            if (this.mAuditLogEnabled != z2) {
                resetAuditBufferLocked();
                this.mAuditLogEnabled = z2;
            }
            this.mLock.unlock();
        } catch (java.lang.Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startMonitorThreadLocked() {
        this.mId = 0L;
        this.mPaused = false;
        this.mMonitorThread = new java.lang.Thread(this);
        this.mMonitorThread.start();
        android.app.admin.SecurityLog.writeEvent(210011, new java.lang.Object[0]);
        android.util.Slog.i(TAG, "Security log monitor thread started");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopMonitorThreadLocked() {
        this.mMonitorThread.interrupt();
        try {
            this.mMonitorThread.join(java.util.concurrent.TimeUnit.SECONDS.toMillis(5L));
        } catch (java.lang.InterruptedException e) {
            android.util.Log.e(TAG, "Interrupted while waiting for thread to stop", e);
        }
        this.mMonitorThread = null;
        android.app.admin.SecurityLog.writeEvent(210012, new java.lang.Object[0]);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resetLegacyBufferLocked() {
        this.mPendingLogs = new java.util.ArrayList<>();
        this.mCriticalLevelLogged = false;
        this.mAllowedToRetrieve = false;
        this.mNextAllowedRetrievalTimeMillis = -1L;
        android.util.Slog.i(TAG, "Legacy buffer reset.");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resetAuditBufferLocked() {
        this.mAuditLogEventBuffer.clear();
        this.mAuditLogCallbacks.clear();
    }

    void pause() {
        android.util.Slog.i(TAG, "Paused.");
        this.mLock.lock();
        this.mPaused = true;
        this.mAllowedToRetrieve = false;
        this.mLock.unlock();
    }

    void resume() {
        this.mLock.lock();
        try {
            if (!this.mPaused) {
                android.util.Log.d(TAG, "Attempted to resume, but logging is not paused.");
                return;
            }
            this.mPaused = false;
            this.mAllowedToRetrieve = false;
            this.mLock.unlock();
            android.util.Slog.i(TAG, "Resumed.");
            try {
                notifyDeviceOwnerOrProfileOwnerIfNeeded(false);
            } catch (java.lang.InterruptedException e) {
                android.util.Log.w(TAG, "Thread interrupted.", e);
            }
        } finally {
            this.mLock.unlock();
        }
    }

    void discardLogs() {
        this.mLock.lock();
        this.mAllowedToRetrieve = false;
        this.mPendingLogs = new java.util.ArrayList<>();
        this.mCriticalLevelLogged = false;
        this.mLock.unlock();
        android.util.Slog.i(TAG, "Discarded all logs.");
    }

    java.util.List<android.app.admin.SecurityLog.SecurityEvent> retrieveLogs() {
        this.mLock.lock();
        try {
            if (!this.mAllowedToRetrieve) {
                this.mLock.unlock();
                return null;
            }
            this.mAllowedToRetrieve = false;
            this.mNextAllowedRetrievalTimeMillis = android.os.SystemClock.elapsedRealtime() + RATE_LIMIT_INTERVAL_MS;
            java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> arrayList = this.mPendingLogs;
            this.mPendingLogs = new java.util.ArrayList<>();
            this.mCriticalLevelLogged = false;
            return arrayList;
        } finally {
            this.mLock.unlock();
        }
    }

    private void getNextBatch(java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> arrayList) throws java.io.IOException {
        if (this.mLastEventNanos < 0) {
            android.app.admin.SecurityLog.readEvents(arrayList);
        } else {
            android.app.admin.SecurityLog.readEventsSince(this.mLastEvents.isEmpty() ? this.mLastEventNanos : java.lang.Math.max(0L, this.mLastEventNanos - OVERLAP_NS), arrayList);
        }
        int i = 0;
        while (true) {
            if (i >= arrayList.size() - 1) {
                break;
            }
            long timeNanos = arrayList.get(i).getTimeNanos();
            i++;
            if (timeNanos > arrayList.get(i).getTimeNanos()) {
                arrayList.sort(new java.util.Comparator() { // from class: com.android.server.devicepolicy.SecurityLogMonitor$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                        int lambda$getNextBatch$0;
                        lambda$getNextBatch$0 = com.android.server.devicepolicy.SecurityLogMonitor.lambda$getNextBatch$0((android.app.admin.SecurityLog.SecurityEvent) obj, (android.app.admin.SecurityLog.SecurityEvent) obj2);
                        return lambda$getNextBatch$0;
                    }
                });
                break;
            }
        }
        android.app.admin.SecurityLog.redactEvents(arrayList, this.mEnabledUser);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getNextBatch$0(android.app.admin.SecurityLog.SecurityEvent securityEvent, android.app.admin.SecurityLog.SecurityEvent securityEvent2) {
        return java.lang.Long.signum(securityEvent.getTimeNanos() - securityEvent2.getTimeNanos());
    }

    private void saveLastEvents(java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> arrayList) {
        this.mLastEvents.clear();
        if (arrayList.isEmpty()) {
            return;
        }
        this.mLastEventNanos = arrayList.get(arrayList.size() - 1).getTimeNanos();
        int size = arrayList.size() - 2;
        while (size >= 0 && this.mLastEventNanos - arrayList.get(size).getTimeNanos() < OVERLAP_NS) {
            size--;
        }
        this.mLastEvents.addAll(arrayList.subList(size + 1, arrayList.size()));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void mergeBatchLocked(java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> arrayList) {
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < this.mLastEvents.size() && i2 < arrayList.size()) {
            android.app.admin.SecurityLog.SecurityEvent securityEvent = arrayList.get(i2);
            long timeNanos = securityEvent.getTimeNanos();
            if (timeNanos > this.mLastEventNanos) {
                break;
            }
            android.app.admin.SecurityLog.SecurityEvent securityEvent2 = this.mLastEvents.get(i);
            long timeNanos2 = securityEvent2.getTimeNanos();
            if (timeNanos2 > timeNanos) {
                arrayList2.add(securityEvent);
                i2++;
            } else if (timeNanos2 < timeNanos) {
                i++;
            } else {
                if (!securityEvent2.eventEquals(securityEvent)) {
                    arrayList2.add(securityEvent);
                }
                i++;
                i2++;
            }
        }
        arrayList2.addAll(arrayList.subList(i2, arrayList.size()));
        java.util.Iterator<android.app.admin.SecurityLog.SecurityEvent> it = arrayList2.iterator();
        while (it.hasNext()) {
            assignLogId(it.next());
        }
        if (!android.app.admin.flags.Flags.securityLogV2Enabled() || this.mLegacyLogEnabled) {
            addToLegacyBufferLocked(arrayList2);
        }
        if (android.app.admin.flags.Flags.securityLogV2Enabled() && this.mAuditLogEnabled) {
            addAuditLogEventsLocked(arrayList2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addToLegacyBufferLocked(java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) {
        this.mPendingLogs.addAll(list);
        checkCriticalLevel();
        if (this.mPendingLogs.size() > BUFFER_ENTRIES_MAXIMUM_LEVEL) {
            this.mPendingLogs = new java.util.ArrayList<>(this.mPendingLogs.subList(this.mPendingLogs.size() - 5120, this.mPendingLogs.size()));
            this.mCriticalLevelLogged = false;
            android.util.Slog.i(TAG, "Pending logs buffer full. Discarding old logs.");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void checkCriticalLevel() {
        if (android.app.admin.SecurityLog.isLoggingEnabled() && this.mPendingLogs.size() >= BUFFER_ENTRIES_CRITICAL_LEVEL && !this.mCriticalLevelLogged) {
            this.mCriticalLevelLogged = true;
            android.app.admin.SecurityLog.writeEvent(210015, new java.lang.Object[0]);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assignLogId(android.app.admin.SecurityLog.SecurityEvent securityEvent) {
        securityEvent.setId(this.mId);
        if (this.mId == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            android.util.Slog.i(TAG, "Reached maximum id value; wrapping around.");
            this.mId = 0L;
        } else {
            this.mId++;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        android.os.Process.setThreadPriority(10);
        java.util.ArrayList<android.app.admin.SecurityLog.SecurityEvent> arrayList = new java.util.ArrayList<>();
        while (!java.lang.Thread.currentThread().isInterrupted()) {
            try {
                boolean tryAcquire = this.mForceSemaphore.tryAcquire(POLLING_INTERVAL_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
                getNextBatch(arrayList);
                this.mLock.lockInterruptibly();
                try {
                    mergeBatchLocked(arrayList);
                    this.mLock.unlock();
                    saveLastEvents(arrayList);
                    arrayList.clear();
                    if (!android.app.admin.flags.Flags.securityLogV2Enabled() || this.mLegacyLogEnabled) {
                        notifyDeviceOwnerOrProfileOwnerIfNeeded(tryAcquire);
                    }
                } catch (java.lang.Throwable th) {
                    this.mLock.unlock();
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Failed to read security log", e);
            } catch (java.lang.InterruptedException e2) {
                android.util.Log.i(TAG, "Thread interrupted, exiting.", e2);
            }
        }
        this.mLastEvents.clear();
        if (this.mLastEventNanos != -1) {
            this.mLastEventNanos++;
        }
        android.util.Slog.i(TAG, "MonitorThread exit.");
    }

    private void notifyDeviceOwnerOrProfileOwnerIfNeeded(boolean z) throws java.lang.InterruptedException {
        this.mLock.lockInterruptibly();
        try {
            if (this.mPaused) {
                this.mLock.unlock();
                return;
            }
            int size = this.mPendingLogs.size();
            boolean z2 = (size >= 1024 || (z && size > 0)) && !this.mAllowedToRetrieve;
            if (size > 0 && android.os.SystemClock.elapsedRealtime() >= this.mNextAllowedRetrievalTimeMillis) {
                z2 = true;
            }
            if (z2) {
                this.mAllowedToRetrieve = true;
                this.mNextAllowedRetrievalTimeMillis = android.os.SystemClock.elapsedRealtime() + BROADCAST_RETRY_INTERVAL_MS;
            }
            this.mLock.unlock();
            if (z2) {
                android.util.Slog.i(TAG, "notify DO or PO");
                this.mService.sendDeviceOwnerOrProfileOwnerCommand("android.app.action.SECURITY_LOGS_AVAILABLE", null, this.mEnabledUser);
            }
        } catch (java.lang.Throwable th) {
            this.mLock.unlock();
            throw th;
        }
    }

    public long forceLogs() {
        long nanoTime = java.lang.System.nanoTime();
        synchronized (this.mForceSemaphore) {
            try {
                long j = (this.mLastForceNanos + FORCE_FETCH_THROTTLE_NS) - nanoTime;
                if (j > 0) {
                    return java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(j) + 1;
                }
                this.mLastForceNanos = nanoTime;
                if (this.mForceSemaphore.availablePermits() == 0) {
                    this.mForceSemaphore.release();
                }
                return 0L;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setAuditLogEventsCallback(int i, android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback) {
        this.mLock.lock();
        try {
            if (iAuditLogEventsCallback == null) {
                this.mAuditLogCallbacks.remove(i);
                com.android.server.utils.Slogf.i(TAG, "Cleared audit log callback for UID %d", java.lang.Integer.valueOf(i));
            } else {
                scheduleSendAuditLogs(i, iAuditLogEventsCallback, new java.util.ArrayList(this.mAuditLogEventBuffer));
                this.mAuditLogCallbacks.append(i, iAuditLogEventsCallback);
                this.mLock.unlock();
                com.android.server.utils.Slogf.i(TAG, "Set audit log callback for UID %d", java.lang.Integer.valueOf(i));
            }
        } finally {
            this.mLock.unlock();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addAuditLogEventsLocked(java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) {
        if (this.mPaused) {
            return;
        }
        if (!list.isEmpty()) {
            for (int i = 0; i < this.mAuditLogCallbacks.size(); i++) {
                scheduleSendAuditLogs(this.mAuditLogCallbacks.keyAt(i), this.mAuditLogCallbacks.valueAt(i), list);
            }
        }
        this.mAuditLogEventBuffer.addAll(list);
        trimAuditLogBufferLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void trimAuditLogBufferLocked() {
        long nanos = java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.System.currentTimeMillis());
        java.util.Iterator<android.app.admin.SecurityLog.SecurityEvent> it = this.mAuditLogEventBuffer.iterator();
        while (it.hasNext()) {
            android.app.admin.SecurityLog.SecurityEvent next = it.next();
            if (this.mAuditLogEventBuffer.size() > 10000 || nanos - next.getTimeNanos() > MAX_AUDIT_LOG_EVENT_AGE_NS) {
                it.remove();
            } else {
                return;
            }
        }
    }

    private void scheduleSendAuditLogs(final int i, final android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback, final java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicepolicy.SecurityLogMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicepolicy.SecurityLogMonitor.this.lambda$scheduleSendAuditLogs$1(i, iAuditLogEventsCallback, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendAuditLogs, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleSendAuditLogs$1(int i, android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback, java.util.List<android.app.admin.SecurityLog.SecurityEvent> list) {
        try {
            list.size();
            iAuditLogEventsCallback.onNewAuditLogEvents(list);
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.e(TAG, e, "Failed to invoke audit log callback for UID %d", java.lang.Integer.valueOf(i));
            removeAuditLogEventsCallbackIfDead(i, iAuditLogEventsCallback);
        }
    }

    private void removeAuditLogEventsCallbackIfDead(int i, android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback) {
        android.os.IBinder asBinder = iAuditLogEventsCallback.asBinder();
        if (asBinder.isBinderAlive()) {
            android.util.Slog.i(TAG, "Callback binder is still alive, not removing.");
            return;
        }
        this.mLock.lock();
        try {
            int indexOfKey = this.mAuditLogCallbacks.indexOfKey(i);
            if (indexOfKey < 0) {
                com.android.server.utils.Slogf.i(TAG, "Callback not registered for UID %d, nothing to remove", java.lang.Integer.valueOf(i));
            } else if (!this.mAuditLogCallbacks.valueAt(indexOfKey).asBinder().equals(asBinder)) {
                com.android.server.utils.Slogf.i(TAG, "Callback is already replaced for UID %d, not removing", java.lang.Integer.valueOf(i));
            } else {
                com.android.server.utils.Slogf.i(TAG, "Removing callback for UID %d", java.lang.Integer.valueOf(i));
                this.mAuditLogCallbacks.removeAt(indexOfKey);
            }
        } finally {
            this.mLock.unlock();
        }
    }
}
