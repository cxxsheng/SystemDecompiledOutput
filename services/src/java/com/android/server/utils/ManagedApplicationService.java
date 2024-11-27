package com.android.server.utils;

/* loaded from: classes2.dex */
public class ManagedApplicationService {
    private static final int MAX_RETRY_COUNT = 4;
    private static final long MAX_RETRY_DURATION_MS = 16000;
    private static final long MIN_RETRY_DURATION_MS = 2000;
    public static final int RETRY_BEST_EFFORT = 3;
    public static final int RETRY_FOREVER = 1;
    public static final int RETRY_NEVER = 2;
    private static final long RETRY_RESET_TIME_MS = 64000;
    private android.os.IInterface mBoundInterface;
    private final com.android.server.utils.ManagedApplicationService.BinderChecker mChecker;
    private final int mClientLabel;
    private final android.content.ComponentName mComponent;
    private android.content.ServiceConnection mConnection;
    private final android.content.Context mContext;
    private final com.android.server.utils.ManagedApplicationService.EventCallback mEventCb;
    private final android.os.Handler mHandler;
    private final boolean mIsImportant;
    private long mLastRetryTimeMs;
    private com.android.server.utils.ManagedApplicationService.PendingEvent mPendingEvent;
    private int mRetryCount;
    private final int mRetryType;
    private boolean mRetrying;
    private final java.lang.String mSettingsAction;
    private final int mUserId;
    private final java.lang.String TAG = getClass().getSimpleName();
    private final java.lang.Runnable mRetryRunnable = new java.lang.Runnable() { // from class: com.android.server.utils.ManagedApplicationService$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.utils.ManagedApplicationService.this.doRetry();
        }
    };
    private final java.lang.Object mLock = new java.lang.Object();
    private long mNextRetryDurationMs = MIN_RETRY_DURATION_MS;

    public interface BinderChecker {
        android.os.IInterface asInterface(android.os.IBinder iBinder);

        boolean checkType(android.os.IInterface iInterface);
    }

    public interface EventCallback {
        void onServiceEvent(com.android.server.utils.ManagedApplicationService.LogEvent logEvent);
    }

    public interface LogFormattable {
        java.lang.String toLogString(java.text.SimpleDateFormat simpleDateFormat);
    }

    public interface PendingEvent {
        void runEvent(android.os.IInterface iInterface) throws android.os.RemoteException;
    }

    public static class LogEvent implements com.android.server.utils.ManagedApplicationService.LogFormattable {
        public static final int EVENT_BINDING_DIED = 3;
        public static final int EVENT_CONNECTED = 1;
        public static final int EVENT_DISCONNECTED = 2;
        public static final int EVENT_STOPPED_PERMANENTLY = 4;
        public final android.content.ComponentName component;
        public final int event;
        public final long timestamp;

        public LogEvent(long j, android.content.ComponentName componentName, int i) {
            this.timestamp = j;
            this.component = componentName;
            this.event = i;
        }

        @Override // com.android.server.utils.ManagedApplicationService.LogFormattable
        public java.lang.String toLogString(java.text.SimpleDateFormat simpleDateFormat) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(simpleDateFormat.format(new java.util.Date(this.timestamp)));
            sb.append("   ");
            sb.append(eventToString(this.event));
            sb.append(" Managed Service: ");
            sb.append(this.component == null ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : this.component.flattenToString());
            return sb.toString();
        }

        public static java.lang.String eventToString(int i) {
            switch (i) {
                case 1:
                    return "Connected";
                case 2:
                    return "Disconnected";
                case 3:
                    return "Binding Died For";
                case 4:
                    return "Permanently Stopped";
                default:
                    return "Unknown Event Occurred";
            }
        }
    }

    private ManagedApplicationService(android.content.Context context, android.content.ComponentName componentName, int i, int i2, java.lang.String str, com.android.server.utils.ManagedApplicationService.BinderChecker binderChecker, boolean z, int i3, android.os.Handler handler, com.android.server.utils.ManagedApplicationService.EventCallback eventCallback) {
        this.mContext = context;
        this.mComponent = componentName;
        this.mUserId = i;
        this.mClientLabel = i2;
        this.mSettingsAction = str;
        this.mChecker = binderChecker;
        this.mIsImportant = z;
        this.mRetryType = i3;
        this.mHandler = handler;
        this.mEventCb = eventCallback;
    }

    public static com.android.server.utils.ManagedApplicationService build(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable com.android.server.utils.ManagedApplicationService.BinderChecker binderChecker, boolean z, int i3, @android.annotation.NonNull android.os.Handler handler, @android.annotation.Nullable com.android.server.utils.ManagedApplicationService.EventCallback eventCallback) {
        return new com.android.server.utils.ManagedApplicationService(context, componentName, i, i2, str, binderChecker, z, i3, handler, eventCallback);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public android.content.ComponentName getComponent() {
        return this.mComponent;
    }

    public boolean disconnectIfNotMatching(android.content.ComponentName componentName, int i) {
        if (matches(componentName, i)) {
            return false;
        }
        disconnect();
        return true;
    }

    public void sendEvent(@android.annotation.NonNull com.android.server.utils.ManagedApplicationService.PendingEvent pendingEvent) {
        android.os.IInterface iInterface;
        synchronized (this.mLock) {
            try {
                iInterface = this.mBoundInterface;
                if (iInterface == null) {
                    this.mPendingEvent = pendingEvent;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (iInterface != null) {
            try {
                pendingEvent.runEvent(iInterface);
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                android.util.Slog.e(this.TAG, "Received exception from user service: ", e);
            }
        }
    }

    public void disconnect() {
        synchronized (this.mLock) {
            try {
                if (this.mConnection == null) {
                    return;
                }
                this.mContext.unbindService(this.mConnection);
                this.mConnection = null;
                this.mBoundInterface = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void connect() {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mConnection != null) {
                    return;
                }
                android.content.Intent component = new android.content.Intent().setComponent(this.mComponent);
                if (this.mClientLabel != 0) {
                    component.putExtra("android.intent.extra.client_label", this.mClientLabel);
                }
                if (this.mSettingsAction != null) {
                    component.putExtra("android.intent.extra.client_intent", android.app.PendingIntent.getActivity(this.mContext, 0, new android.content.Intent(this.mSettingsAction), 67108864));
                }
                this.mConnection = new com.android.server.utils.ManagedApplicationService.AnonymousClass1();
                if (!this.mIsImportant) {
                    i = android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN;
                } else {
                    i = 67108929;
                }
                try {
                    if (!this.mContext.bindServiceAsUser(component, this.mConnection, i, new android.os.UserHandle(this.mUserId))) {
                        android.util.Slog.w(this.TAG, "Unable to bind service: " + component);
                        startRetriesLocked();
                    }
                } catch (java.lang.SecurityException e) {
                    android.util.Slog.w(this.TAG, "Unable to bind service: " + component, e);
                    startRetriesLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.utils.ManagedApplicationService$1, reason: invalid class name */
    class AnonymousClass1 implements android.content.ServiceConnection {
        AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.util.Slog.w(com.android.server.utils.ManagedApplicationService.this.TAG, "Service binding died: " + componentName);
            synchronized (com.android.server.utils.ManagedApplicationService.this.mLock) {
                try {
                    if (com.android.server.utils.ManagedApplicationService.this.mConnection != this) {
                        return;
                    }
                    com.android.server.utils.ManagedApplicationService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.utils.ManagedApplicationService$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.utils.ManagedApplicationService.AnonymousClass1.this.lambda$onBindingDied$0(currentTimeMillis);
                        }
                    });
                    com.android.server.utils.ManagedApplicationService.this.mBoundInterface = null;
                    com.android.server.utils.ManagedApplicationService.this.startRetriesLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindingDied$0(long j) {
            com.android.server.utils.ManagedApplicationService.this.mEventCb.onServiceEvent(new com.android.server.utils.ManagedApplicationService.LogEvent(j, com.android.server.utils.ManagedApplicationService.this.mComponent, 3));
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.server.utils.ManagedApplicationService.PendingEvent pendingEvent;
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.util.Slog.i(com.android.server.utils.ManagedApplicationService.this.TAG, "Service connected: " + componentName);
            synchronized (com.android.server.utils.ManagedApplicationService.this.mLock) {
                try {
                    if (com.android.server.utils.ManagedApplicationService.this.mConnection != this) {
                        return;
                    }
                    com.android.server.utils.ManagedApplicationService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.utils.ManagedApplicationService$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.utils.ManagedApplicationService.AnonymousClass1.this.lambda$onServiceConnected$1(currentTimeMillis);
                        }
                    });
                    com.android.server.utils.ManagedApplicationService.this.stopRetriesLocked();
                    android.os.IInterface iInterface = null;
                    com.android.server.utils.ManagedApplicationService.this.mBoundInterface = null;
                    if (com.android.server.utils.ManagedApplicationService.this.mChecker == null) {
                        pendingEvent = null;
                    } else {
                        com.android.server.utils.ManagedApplicationService.this.mBoundInterface = com.android.server.utils.ManagedApplicationService.this.mChecker.asInterface(iBinder);
                        if (!com.android.server.utils.ManagedApplicationService.this.mChecker.checkType(com.android.server.utils.ManagedApplicationService.this.mBoundInterface)) {
                            com.android.server.utils.ManagedApplicationService.this.mBoundInterface = null;
                            android.util.Slog.w(com.android.server.utils.ManagedApplicationService.this.TAG, "Invalid binder from " + componentName);
                            com.android.server.utils.ManagedApplicationService.this.startRetriesLocked();
                            return;
                        }
                        android.os.IInterface iInterface2 = com.android.server.utils.ManagedApplicationService.this.mBoundInterface;
                        pendingEvent = com.android.server.utils.ManagedApplicationService.this.mPendingEvent;
                        com.android.server.utils.ManagedApplicationService.this.mPendingEvent = null;
                        iInterface = iInterface2;
                    }
                    if (iInterface != null && pendingEvent != null) {
                        try {
                            pendingEvent.runEvent(iInterface);
                        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                            android.util.Slog.e(com.android.server.utils.ManagedApplicationService.this.TAG, "Received exception from user service: ", e);
                            com.android.server.utils.ManagedApplicationService.this.startRetriesLocked();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$1(long j) {
            com.android.server.utils.ManagedApplicationService.this.mEventCb.onServiceEvent(new com.android.server.utils.ManagedApplicationService.LogEvent(j, com.android.server.utils.ManagedApplicationService.this.mComponent, 1));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.util.Slog.w(com.android.server.utils.ManagedApplicationService.this.TAG, "Service disconnected: " + componentName);
            synchronized (com.android.server.utils.ManagedApplicationService.this.mLock) {
                try {
                    if (com.android.server.utils.ManagedApplicationService.this.mConnection != this) {
                        return;
                    }
                    com.android.server.utils.ManagedApplicationService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.utils.ManagedApplicationService$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.utils.ManagedApplicationService.AnonymousClass1.this.lambda$onServiceDisconnected$2(currentTimeMillis);
                        }
                    });
                    com.android.server.utils.ManagedApplicationService.this.mBoundInterface = null;
                    com.android.server.utils.ManagedApplicationService.this.startRetriesLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceDisconnected$2(long j) {
            com.android.server.utils.ManagedApplicationService.this.mEventCb.onServiceEvent(new com.android.server.utils.ManagedApplicationService.LogEvent(j, com.android.server.utils.ManagedApplicationService.this.mComponent, 2));
        }
    }

    private boolean matches(android.content.ComponentName componentName, int i) {
        return java.util.Objects.equals(this.mComponent, componentName) && this.mUserId == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRetriesLocked() {
        if (checkAndDeliverServiceDiedCbLocked()) {
            disconnect();
        } else {
            if (this.mRetrying) {
                return;
            }
            this.mRetrying = true;
            queueRetryLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRetriesLocked() {
        this.mRetrying = false;
        this.mHandler.removeCallbacks(this.mRetryRunnable);
    }

    private void queueRetryLocked() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (uptimeMillis - this.mLastRetryTimeMs > RETRY_RESET_TIME_MS) {
            this.mNextRetryDurationMs = MIN_RETRY_DURATION_MS;
            this.mRetryCount = 0;
        }
        this.mLastRetryTimeMs = uptimeMillis;
        this.mHandler.postDelayed(this.mRetryRunnable, this.mNextRetryDurationMs);
        this.mNextRetryDurationMs = java.lang.Math.min(this.mNextRetryDurationMs * 2, MAX_RETRY_DURATION_MS);
        this.mRetryCount++;
    }

    private boolean checkAndDeliverServiceDiedCbLocked() {
        if (this.mRetryType == 2 || (this.mRetryType == 3 && this.mRetryCount >= 4)) {
            android.util.Slog.e(this.TAG, "Service " + this.mComponent + " has died too much, not retrying.");
            if (this.mEventCb != null) {
                final long currentTimeMillis = java.lang.System.currentTimeMillis();
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.utils.ManagedApplicationService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.utils.ManagedApplicationService.this.lambda$checkAndDeliverServiceDiedCbLocked$0(currentTimeMillis);
                    }
                });
                return true;
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAndDeliverServiceDiedCbLocked$0(long j) {
        this.mEventCb.onServiceEvent(new com.android.server.utils.ManagedApplicationService.LogEvent(j, this.mComponent, 4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRetry() {
        synchronized (this.mLock) {
            try {
                if (this.mConnection == null) {
                    return;
                }
                if (this.mRetrying) {
                    android.util.Slog.i(this.TAG, "Attempting to reconnect " + this.mComponent + "...");
                    disconnect();
                    if (checkAndDeliverServiceDiedCbLocked()) {
                        return;
                    }
                    queueRetryLocked();
                    connect();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
