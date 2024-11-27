package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class TransportConnection {
    private static final int LOG_BUFFER_SIZE = 5;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG = "TransportConnection";
    private final android.content.Intent mBindIntent;
    private final dalvik.system.CloseGuard mCloseGuard;
    private final android.content.ServiceConnection mConnection;
    private final android.content.Context mContext;
    private final java.lang.String mCreatorLogString;
    private final java.lang.String mIdentifier;
    private final android.os.Handler mListenerHandler;

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private final java.util.Map<com.android.server.backup.transport.TransportConnectionListener, java.lang.String> mListeners;

    @com.android.internal.annotations.GuardedBy({"mLogBufferLock"})
    private final java.util.List<java.lang.String> mLogBuffer;
    private final java.lang.Object mLogBufferLock;
    private final java.lang.String mPrefixForLog;

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private int mState;
    private final java.lang.Object mStateLock;

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private volatile com.android.server.backup.transport.BackupTransportClient mTransport;
    private final android.content.ComponentName mTransportComponent;
    private final com.android.server.backup.transport.TransportStats mTransportStats;
    private final int mUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface State {
        public static final int BOUND_AND_CONNECTING = 2;
        public static final int CONNECTED = 3;
        public static final int IDLE = 1;
        public static final int UNUSABLE = 0;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface Transition {
        public static final int DOWN = -1;
        public static final int NO_TRANSITION = 0;
        public static final int UP = 1;
    }

    TransportConnection(int i, android.content.Context context, com.android.server.backup.transport.TransportStats transportStats, android.content.Intent intent, android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        this(i, context, transportStats, intent, componentName, str, str2, new android.os.Handler(android.os.Looper.getMainLooper()));
    }

    @com.android.internal.annotations.VisibleForTesting
    TransportConnection(int i, android.content.Context context, com.android.server.backup.transport.TransportStats transportStats, android.content.Intent intent, android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.os.Handler handler) {
        this.mStateLock = new java.lang.Object();
        this.mLogBufferLock = new java.lang.Object();
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mLogBuffer = new java.util.LinkedList();
        this.mListeners = new android.util.ArrayMap();
        this.mState = 1;
        this.mUserId = i;
        this.mContext = context;
        this.mTransportStats = transportStats;
        this.mTransportComponent = componentName;
        this.mBindIntent = intent;
        this.mIdentifier = str;
        this.mCreatorLogString = str2;
        this.mListenerHandler = handler;
        this.mConnection = new com.android.server.backup.transport.TransportConnection.TransportConnectionMonitor(context, this);
        this.mPrefixForLog = this.mTransportComponent.getShortClassName().replaceFirst(".*\\.", "") + "#" + this.mIdentifier + ":";
        this.mCloseGuard.open("markAsDisposed");
    }

    public android.content.ComponentName getTransportComponent() {
        return this.mTransportComponent;
    }

    public void connectAsync(com.android.server.backup.transport.TransportConnectionListener transportConnectionListener, java.lang.String str) {
        synchronized (this.mStateLock) {
            try {
                checkStateIntegrityLocked();
                switch (this.mState) {
                    case 0:
                        log(5, str, "Async connect: UNUSABLE client");
                        notifyListener(transportConnectionListener, null, str);
                        break;
                    case 1:
                        if (this.mContext.bindServiceAsUser(this.mBindIntent, this.mConnection, 1, android.os.UserHandle.of(this.mUserId))) {
                            log(3, str, "Async connect: service bound, connecting");
                            setStateLocked(2, null);
                            this.mListeners.put(transportConnectionListener, str);
                            break;
                        } else {
                            log(6, "Async connect: bindService returned false");
                            this.mContext.unbindService(this.mConnection);
                            notifyListener(transportConnectionListener, null, str);
                            break;
                        }
                    case 2:
                        log(3, str, "Async connect: already connecting, adding listener");
                        this.mListeners.put(transportConnectionListener, str);
                        break;
                    case 3:
                        log(3, str, "Async connect: reusing transport");
                        notifyListener(transportConnectionListener, this.mTransport, str);
                        break;
                }
            } finally {
            }
        }
    }

    public void unbind(java.lang.String str) {
        synchronized (this.mStateLock) {
            try {
                checkStateIntegrityLocked();
                log(3, str, "Unbind requested (was " + stateToString(this.mState) + ")");
                switch (this.mState) {
                    case 2:
                        setStateLocked(1, null);
                        this.mContext.unbindService(this.mConnection);
                        notifyListenersAndClearLocked(null);
                        break;
                    case 3:
                        setStateLocked(1, null);
                        this.mContext.unbindService(this.mConnection);
                        break;
                }
            } finally {
            }
        }
    }

    public void markAsDisposed() {
        synchronized (this.mStateLock) {
            com.android.internal.util.Preconditions.checkState(this.mState < 2, "Can't mark as disposed if still bound");
            this.mCloseGuard.close();
        }
    }

    @android.annotation.Nullable
    public com.android.server.backup.transport.BackupTransportClient connect(java.lang.String str) {
        com.android.internal.util.Preconditions.checkState(!android.os.Looper.getMainLooper().isCurrentThread(), "Can't call connect() on main thread");
        com.android.server.backup.transport.BackupTransportClient backupTransportClient = this.mTransport;
        if (backupTransportClient != null) {
            log(3, str, "Sync connect: reusing transport");
            return backupTransportClient;
        }
        synchronized (this.mStateLock) {
            try {
                if (this.mState == 0) {
                    log(5, str, "Sync connect: UNUSABLE client");
                    return null;
                }
                final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
                com.android.server.backup.transport.TransportConnectionListener transportConnectionListener = new com.android.server.backup.transport.TransportConnectionListener() { // from class: com.android.server.backup.transport.TransportConnection$$ExternalSyntheticLambda0
                    @Override // com.android.server.backup.transport.TransportConnectionListener
                    public final void onTransportConnectionResult(com.android.server.backup.transport.BackupTransportClient backupTransportClient2, com.android.server.backup.transport.TransportConnection transportConnection) {
                        completableFuture.complete(backupTransportClient2);
                    }
                };
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                log(3, str, "Sync connect: calling async");
                connectAsync(transportConnectionListener, str);
                try {
                    com.android.server.backup.transport.BackupTransportClient backupTransportClient2 = (com.android.server.backup.transport.BackupTransportClient) completableFuture.get();
                    long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
                    this.mTransportStats.registerConnectionTime(this.mTransportComponent, elapsedRealtime2);
                    log(3, str, java.lang.String.format(java.util.Locale.US, "Connect took %d ms", java.lang.Long.valueOf(elapsedRealtime2)));
                    return backupTransportClient2;
                } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                    log(6, str, e.getClass().getSimpleName() + " while waiting for transport: " + e.getMessage());
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.backup.transport.BackupTransportClient connectOrThrow(java.lang.String str) throws com.android.server.backup.transport.TransportNotAvailableException {
        com.android.server.backup.transport.BackupTransportClient connect = connect(str);
        if (connect == null) {
            log(6, str, "Transport connection failed");
            throw new com.android.server.backup.transport.TransportNotAvailableException();
        }
        return connect;
    }

    public com.android.server.backup.transport.BackupTransportClient getConnectedTransport(java.lang.String str) throws com.android.server.backup.transport.TransportNotAvailableException {
        com.android.server.backup.transport.BackupTransportClient backupTransportClient = this.mTransport;
        if (backupTransportClient == null) {
            log(6, str, "Transport not connected");
            throw new com.android.server.backup.transport.TransportNotAvailableException();
        }
        return backupTransportClient;
    }

    public java.lang.String toString() {
        return "TransportClient{" + this.mTransportComponent.flattenToShortString() + "#" + this.mIdentifier + "}";
    }

    protected void finalize() throws java.lang.Throwable {
        synchronized (this.mStateLock) {
            this.mCloseGuard.warnIfOpen();
            if (this.mState >= 2) {
                log(6, "TransportClient.finalize()", "Dangling TransportClient created in [" + this.mCreatorLogString + "] being GC'ed. Left bound, unbinding...");
                try {
                    unbind("TransportClient.finalize()");
                } catch (java.lang.IllegalStateException e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceConnected(android.os.IBinder iBinder) {
        com.android.server.backup.transport.BackupTransportClient backupTransportClient = new com.android.server.backup.transport.BackupTransportClient(com.android.internal.backup.IBackupTransport.Stub.asInterface(iBinder));
        synchronized (this.mStateLock) {
            try {
                checkStateIntegrityLocked();
                if (this.mState != 0) {
                    log(3, "Transport connected");
                    setStateLocked(3, backupTransportClient);
                    notifyListenersAndClearLocked(backupTransportClient);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceDisconnected() {
        synchronized (this.mStateLock) {
            try {
                log(6, "Service disconnected: client UNUSABLE");
                if (this.mTransport != null) {
                    this.mTransport.onBecomingUnusable();
                }
                setStateLocked(0, null);
                try {
                    this.mContext.unbindService(this.mConnection);
                } catch (java.lang.IllegalArgumentException e) {
                    log(5, "Exception trying to unbind onServiceDisconnected(): " + e.getMessage());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBindingDied() {
        synchronized (this.mStateLock) {
            try {
                checkStateIntegrityLocked();
                log(6, "Binding died: client UNUSABLE");
                if (this.mTransport != null) {
                    this.mTransport.onBecomingUnusable();
                }
                switch (this.mState) {
                    case 1:
                        log(6, "Unexpected state transition IDLE => UNUSABLE");
                        setStateLocked(0, null);
                        break;
                    case 2:
                        setStateLocked(0, null);
                        this.mContext.unbindService(this.mConnection);
                        notifyListenersAndClearLocked(null);
                        break;
                    case 3:
                        setStateLocked(0, null);
                        this.mContext.unbindService(this.mConnection);
                        break;
                }
            } finally {
            }
        }
    }

    private void notifyListener(final com.android.server.backup.transport.TransportConnectionListener transportConnectionListener, @android.annotation.Nullable final com.android.server.backup.transport.BackupTransportClient backupTransportClient, java.lang.String str) {
        log(4, "Notifying [" + str + "] transport = " + (backupTransportClient != null ? "BackupTransportClient" : "null"));
        this.mListenerHandler.post(new java.lang.Runnable() { // from class: com.android.server.backup.transport.TransportConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.backup.transport.TransportConnection.this.lambda$notifyListener$1(transportConnectionListener, backupTransportClient);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyListener$1(com.android.server.backup.transport.TransportConnectionListener transportConnectionListener, com.android.server.backup.transport.BackupTransportClient backupTransportClient) {
        transportConnectionListener.onTransportConnectionResult(backupTransportClient, this);
    }

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private void notifyListenersAndClearLocked(@android.annotation.Nullable com.android.server.backup.transport.BackupTransportClient backupTransportClient) {
        for (java.util.Map.Entry<com.android.server.backup.transport.TransportConnectionListener, java.lang.String> entry : this.mListeners.entrySet()) {
            notifyListener(entry.getKey(), backupTransportClient, entry.getValue());
        }
        this.mListeners.clear();
    }

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private void setStateLocked(int i, @android.annotation.Nullable com.android.server.backup.transport.BackupTransportClient backupTransportClient) {
        log(2, "State: " + stateToString(this.mState) + " => " + stateToString(i));
        onStateTransition(this.mState, i);
        this.mState = i;
        this.mTransport = backupTransportClient;
    }

    private void onStateTransition(int i, int i2) {
        java.lang.String flattenToShortString = this.mTransportComponent.flattenToShortString();
        int transitionThroughState = transitionThroughState(i, i2, 2);
        int transitionThroughState2 = transitionThroughState(i, i2, 3);
        if (transitionThroughState != 0) {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_LIFECYCLE, flattenToShortString, java.lang.Integer.valueOf(transitionThroughState == 1 ? 1 : 0));
        }
        if (transitionThroughState2 != 0) {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_CONNECTION, flattenToShortString, java.lang.Integer.valueOf(transitionThroughState2 == 1 ? 1 : 0));
        }
    }

    private int transitionThroughState(int i, int i2, int i3) {
        if (i < i3 && i3 <= i2) {
            return 1;
        }
        if (i >= i3 && i3 > i2) {
            return -1;
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mStateLock"})
    private void checkStateIntegrityLocked() {
        switch (this.mState) {
            case 0:
                checkState(this.mListeners.isEmpty(), "Unexpected listeners when state = UNUSABLE");
                checkState(this.mTransport == null, "Transport expected to be null when state = UNUSABLE");
                break;
            case 1:
                break;
            case 2:
                checkState(this.mTransport == null, "Transport expected to be null when state = BOUND_AND_CONNECTING");
                return;
            case 3:
                checkState(this.mListeners.isEmpty(), "Unexpected listeners when state = CONNECTED");
                checkState(this.mTransport != null, "Transport expected to be non-null when state = CONNECTED");
                return;
            default:
                checkState(false, "Unexpected state = " + stateToString(this.mState));
                return;
        }
        checkState(this.mListeners.isEmpty(), "Unexpected listeners when state = IDLE");
        checkState(this.mTransport == null, "Transport expected to be null when state = IDLE");
    }

    private void checkState(boolean z, java.lang.String str) {
        if (!z) {
            log(6, str);
        }
    }

    private java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "UNUSABLE";
            case 1:
                return "IDLE";
            case 2:
                return "BOUND_AND_CONNECTING";
            case 3:
                return "CONNECTED";
            default:
                return "<UNKNOWN = " + i + ">";
        }
    }

    private void log(int i, java.lang.String str) {
        com.android.server.backup.transport.TransportUtils.log(i, TAG, com.android.server.backup.transport.TransportUtils.formatMessage(this.mPrefixForLog, null, str));
        saveLogEntry(com.android.server.backup.transport.TransportUtils.formatMessage(null, null, str));
    }

    private void log(int i, java.lang.String str, java.lang.String str2) {
        com.android.server.backup.transport.TransportUtils.log(i, TAG, com.android.server.backup.transport.TransportUtils.formatMessage(this.mPrefixForLog, str, str2));
        saveLogEntry(com.android.server.backup.transport.TransportUtils.formatMessage(null, str, str2));
    }

    private void saveLogEntry(java.lang.String str) {
        java.lang.String str2 = ((java.lang.Object) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", java.lang.System.currentTimeMillis())) + " " + str;
        synchronized (this.mLogBufferLock) {
            try {
                if (this.mLogBuffer.size() == 5) {
                    this.mLogBuffer.remove(this.mLogBuffer.size() - 1);
                }
                this.mLogBuffer.add(0, str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<java.lang.String> getLogBuffer() {
        java.util.List<java.lang.String> unmodifiableList;
        synchronized (this.mLogBufferLock) {
            unmodifiableList = java.util.Collections.unmodifiableList(this.mLogBuffer);
        }
        return unmodifiableList;
    }

    private static class TransportConnectionMonitor implements android.content.ServiceConnection {
        private final android.content.Context mContext;
        private final java.lang.ref.WeakReference<com.android.server.backup.transport.TransportConnection> mTransportClientRef;

        private TransportConnectionMonitor(android.content.Context context, com.android.server.backup.transport.TransportConnection transportConnection) {
            this.mContext = context;
            this.mTransportClientRef = new java.lang.ref.WeakReference<>(transportConnection);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.server.backup.transport.TransportConnection transportConnection = this.mTransportClientRef.get();
            if (transportConnection == null) {
                referenceLost("TransportConnection.onServiceConnected()");
            } else {
                android.os.Binder.allowBlocking(iBinder);
                transportConnection.onServiceConnected(iBinder);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.server.backup.transport.TransportConnection transportConnection = this.mTransportClientRef.get();
            if (transportConnection == null) {
                referenceLost("TransportConnection.onServiceDisconnected()");
            } else {
                transportConnection.onServiceDisconnected();
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            com.android.server.backup.transport.TransportConnection transportConnection = this.mTransportClientRef.get();
            if (transportConnection == null) {
                referenceLost("TransportConnection.onBindingDied()");
            } else {
                transportConnection.onBindingDied();
            }
        }

        private void referenceLost(java.lang.String str) {
            this.mContext.unbindService(this);
            com.android.server.backup.transport.TransportUtils.log(4, com.android.server.backup.transport.TransportConnection.TAG, str + " called but TransportClient reference has been GC'ed");
        }
    }
}
