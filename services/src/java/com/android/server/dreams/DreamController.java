package com.android.server.dreams;

/* loaded from: classes.dex */
final class DreamController {
    private static final int DREAM_CONNECTION_TIMEOUT = 5000;
    private static final int DREAM_FINISH_TIMEOUT = 5000;
    private static final java.lang.String EXTRA_REASON_KEY = "reason";
    private static final java.lang.String EXTRA_REASON_VALUE = "dream";
    private static final java.lang.String TAG = "DreamController";
    private final android.app.ActivityTaskManager mActivityTaskManager;
    private final android.os.Bundle mCloseNotificationShadeOptions;
    private final android.content.Context mContext;
    private com.android.server.dreams.DreamController.DreamRecord mCurrentDream;
    private final android.os.Handler mHandler;
    private final com.android.server.dreams.DreamController.Listener mListener;
    private final android.os.PowerManager mPowerManager;
    private final boolean mResetScreenTimeoutOnUnexpectedDreamExit;
    private static final java.lang.String DREAMING_DELIVERY_GROUP_NAMESPACE = java.util.UUID.randomUUID().toString();
    private static final java.lang.String DREAMING_DELIVERY_GROUP_KEY = java.util.UUID.randomUUID().toString();
    private final android.content.Intent mDreamingStartedIntent = new android.content.Intent("android.intent.action.DREAMING_STARTED").addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
    private final android.content.Intent mDreamingStoppedIntent = new android.content.Intent("android.intent.action.DREAMING_STOPPED").addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
    private final android.os.Bundle mDreamingStartedStoppedOptions = createDreamingStartedStoppedOptions();
    private boolean mSentStartBroadcast = false;
    private final java.util.ArrayList<com.android.server.dreams.DreamController.DreamRecord> mPreviousDreams = new java.util.ArrayList<>();
    private final android.content.Intent mCloseNotificationShadeIntent = new android.content.Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS");

    public interface Listener {
        void onDreamStarted(android.os.Binder binder);

        void onDreamStopped(android.os.Binder binder);
    }

    public DreamController(android.content.Context context, android.os.Handler handler, com.android.server.dreams.DreamController.Listener listener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = listener;
        this.mActivityTaskManager = (android.app.ActivityTaskManager) this.mContext.getSystemService(android.app.ActivityTaskManager.class);
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        this.mCloseNotificationShadeIntent.putExtra("reason", EXTRA_REASON_VALUE);
        this.mCloseNotificationShadeIntent.addFlags(268435456);
        this.mCloseNotificationShadeOptions = android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey("android.intent.action.CLOSE_SYSTEM_DIALOGS", EXTRA_REASON_VALUE).setDeferralPolicy(2).toBundle();
        this.mResetScreenTimeoutOnUnexpectedDreamExit = context.getResources().getBoolean(android.R.bool.config_refreshRateSynchronizationEnabled);
    }

    private android.os.Bundle createDreamingStartedStoppedOptions() {
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        makeBasic.setDeliveryGroupMatchingKey(DREAMING_DELIVERY_GROUP_NAMESPACE, DREAMING_DELIVERY_GROUP_KEY);
        makeBasic.setDeferralPolicy(2);
        return makeBasic.toBundle();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Dreamland:");
        if (this.mCurrentDream != null) {
            printWriter.println("  mCurrentDream:");
            printWriter.println("    mToken=" + this.mCurrentDream.mToken);
            printWriter.println("    mName=" + this.mCurrentDream.mName);
            printWriter.println("    mIsPreviewMode=" + this.mCurrentDream.mIsPreviewMode);
            printWriter.println("    mCanDoze=" + this.mCurrentDream.mCanDoze);
            printWriter.println("    mUserId=" + this.mCurrentDream.mUserId);
            printWriter.println("    mBound=" + this.mCurrentDream.mBound);
            printWriter.println("    mService=" + this.mCurrentDream.mService);
            printWriter.println("    mWakingGently=" + this.mCurrentDream.mWakingGently);
        } else {
            printWriter.println("  mCurrentDream: null");
        }
        printWriter.println("  mSentStartBroadcast=" + this.mSentStartBroadcast);
    }

    public void startDream(android.os.Binder binder, android.content.ComponentName componentName, boolean z, boolean z2, int i, android.os.PowerManager.WakeLock wakeLock, android.content.ComponentName componentName2, java.lang.String str) {
        android.os.Trace.traceBegin(131072L, "startDream");
        try {
            this.mContext.sendBroadcastAsUser(this.mCloseNotificationShadeIntent, android.os.UserHandle.ALL, null, this.mCloseNotificationShadeOptions);
            android.util.Slog.i(TAG, "Starting dream: name=" + componentName + ", isPreviewMode=" + z + ", canDoze=" + z2 + ", userId=" + i + ", reason='" + str + "'");
            com.android.server.dreams.DreamController.DreamRecord dreamRecord = this.mCurrentDream;
            this.mCurrentDream = new com.android.server.dreams.DreamController.DreamRecord(binder, componentName, z, z2, i, wakeLock);
            if (dreamRecord != null) {
                if (java.util.Objects.equals(dreamRecord.mName, this.mCurrentDream.mName)) {
                    stopDreamInstance(true, "restarting same dream", dreamRecord);
                } else {
                    this.mPreviousDreams.add(dreamRecord);
                }
            }
            this.mCurrentDream.mDreamStartTime = android.os.SystemClock.elapsedRealtime();
            com.android.internal.logging.MetricsLogger.visible(this.mContext, this.mCurrentDream.mCanDoze ? com.android.internal.util.FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED : 222);
            android.content.Intent intent = new android.content.Intent("android.service.dreams.DreamService");
            intent.setComponent(componentName);
            intent.addFlags(8388608);
            intent.putExtra("android.service.dream.DreamService.dream_overlay_component", componentName2);
            try {
                if (this.mContext.bindServiceAsUser(intent, this.mCurrentDream, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, new android.os.UserHandle(i))) {
                    this.mCurrentDream.mBound = true;
                    this.mHandler.postDelayed(this.mCurrentDream.mStopUnconnectedDreamRunnable, 5000L);
                    android.os.Trace.traceEnd(131072L);
                } else {
                    android.util.Slog.e(TAG, "Unable to bind dream service: " + intent);
                    stopDream(true, "bindService failed");
                    android.os.Trace.traceEnd(131072L);
                }
            } catch (java.lang.SecurityException e) {
                android.util.Slog.e(TAG, "Unable to bind dream service: " + intent, e);
                stopDream(true, "unable to bind service: SecExp.");
                android.os.Trace.traceEnd(131072L);
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(131072L);
            throw th;
        }
    }

    void setDreamAppTask(android.os.Binder binder, android.app.IAppTask iAppTask) {
        if (this.mCurrentDream == null || this.mCurrentDream.mToken != binder || this.mCurrentDream.mAppTask != null) {
            android.util.Slog.e(TAG, "Illegal dream activity start. mCurrentDream.mToken = " + this.mCurrentDream.mToken + ", illegal dreamToken = " + binder + ". Ending this dream activity.");
            try {
                iAppTask.finishAndRemoveTask();
                return;
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                android.util.Slog.e(TAG, "Unable to stop illegal dream activity.");
                return;
            }
        }
        this.mCurrentDream.mAppTask = iAppTask;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetScreenTimeout() {
        android.util.Slog.i(TAG, "Resetting screen timeout");
        this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), 0, 1);
    }

    public void stopDream(boolean z, java.lang.String str) {
        stopPreviousDreams();
        stopDreamInstance(z, str, this.mCurrentDream);
    }

    private void stopDreamInstance(boolean z, java.lang.String str, com.android.server.dreams.DreamController.DreamRecord dreamRecord) {
        java.lang.String str2;
        if (dreamRecord == null) {
            return;
        }
        android.os.Trace.traceBegin(131072L, "stopDream");
        if (!z) {
            try {
                if (dreamRecord.mWakingGently) {
                    return;
                }
                if (dreamRecord.mService != null) {
                    dreamRecord.mWakingGently = true;
                    try {
                        dreamRecord.mStopReason = str;
                        dreamRecord.mService.wakeUp();
                        this.mHandler.postDelayed(dreamRecord.mStopStubbornDreamRunnable, 5000L);
                        return;
                    } catch (android.os.RemoteException e) {
                    }
                }
            } finally {
                android.os.Trace.traceEnd(131072L);
            }
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Stopping dream: name=");
        sb.append(dreamRecord.mName);
        sb.append(", isPreviewMode=");
        sb.append(dreamRecord.mIsPreviewMode);
        sb.append(", canDoze=");
        sb.append(dreamRecord.mCanDoze);
        sb.append(", userId=");
        sb.append(dreamRecord.mUserId);
        sb.append(", reason='");
        sb.append(str);
        sb.append("'");
        if (dreamRecord.mStopReason == null) {
            str2 = "";
        } else {
            str2 = "(from '" + dreamRecord.mStopReason + "')";
        }
        sb.append(str2);
        android.util.Slog.i(TAG, sb.toString());
        com.android.internal.logging.MetricsLogger.hidden(this.mContext, dreamRecord.mCanDoze ? com.android.internal.util.FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED : 222);
        com.android.internal.logging.MetricsLogger.histogram(this.mContext, dreamRecord.mCanDoze ? "dozing_minutes" : "dreaming_minutes", (int) ((android.os.SystemClock.elapsedRealtime() - dreamRecord.mDreamStartTime) / 60000));
        this.mHandler.removeCallbacks(dreamRecord.mStopUnconnectedDreamRunnable);
        this.mHandler.removeCallbacks(dreamRecord.mStopStubbornDreamRunnable);
        if (dreamRecord.mService != null) {
            try {
                dreamRecord.mService.detach();
            } catch (android.os.RemoteException e2) {
            }
            try {
                dreamRecord.mService.asBinder().unlinkToDeath(dreamRecord, 0);
            } catch (java.util.NoSuchElementException e3) {
            }
            dreamRecord.mService = null;
        }
        if (dreamRecord.mBound) {
            this.mContext.unbindService(dreamRecord);
        }
        dreamRecord.releaseWakeLockIfNeeded();
        if (dreamRecord == this.mCurrentDream) {
            this.mCurrentDream = null;
            if (this.mSentStartBroadcast) {
                this.mContext.sendBroadcastAsUser(this.mDreamingStoppedIntent, android.os.UserHandle.ALL, null, this.mDreamingStartedStoppedOptions);
                this.mSentStartBroadcast = false;
            }
            if (this.mCurrentDream != null && this.mCurrentDream.mAppTask != null) {
                try {
                    this.mCurrentDream.mAppTask.finishAndRemoveTask();
                } catch (android.os.RemoteException | java.lang.RuntimeException e4) {
                    android.util.Slog.e(TAG, "Unable to stop dream activity.");
                }
            }
            this.mListener.onDreamStopped(dreamRecord.mToken);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopPreviousDreams() {
        if (this.mPreviousDreams.isEmpty()) {
            return;
        }
        java.util.Iterator<com.android.server.dreams.DreamController.DreamRecord> it = this.mPreviousDreams.iterator();
        while (it.hasNext()) {
            stopDreamInstance(true, "stop previous dream", it.next());
            it.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attach(android.service.dreams.IDreamService iDreamService) {
        try {
            iDreamService.asBinder().linkToDeath(this.mCurrentDream, 0);
            iDreamService.attach(this.mCurrentDream.mToken, this.mCurrentDream.mCanDoze, this.mCurrentDream.mIsPreviewMode, this.mCurrentDream.mDreamingStartedCallback);
            this.mCurrentDream.mService = iDreamService;
            if (!this.mCurrentDream.mIsPreviewMode && !this.mSentStartBroadcast) {
                this.mContext.sendBroadcastAsUser(this.mDreamingStartedIntent, android.os.UserHandle.ALL, null, this.mDreamingStartedStoppedOptions);
                this.mListener.onDreamStarted(this.mCurrentDream.mToken);
                this.mSentStartBroadcast = true;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "The dream service died unexpectedly.", e);
            stopDream(true, "attach failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DreamRecord implements android.os.IBinder.DeathRecipient, android.content.ServiceConnection {
        public android.app.IAppTask mAppTask;
        public boolean mBound;
        public final boolean mCanDoze;
        public boolean mConnected;
        private long mDreamStartTime;
        public final boolean mIsPreviewMode;
        public final android.content.ComponentName mName;
        public android.service.dreams.IDreamService mService;
        private java.lang.String mStopReason;
        public final android.os.Binder mToken;
        public final int mUserId;
        public android.os.PowerManager.WakeLock mWakeLock;
        public boolean mWakingGently;
        private final java.lang.Runnable mStopPreviousDreamsIfNeeded = new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamController.DreamRecord.this.stopPreviousDreamsIfNeeded();
            }
        };
        private final java.lang.Runnable mReleaseWakeLockIfNeeded = new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamController.DreamRecord.this.releaseWakeLockIfNeeded();
            }
        };
        private final java.lang.Runnable mStopUnconnectedDreamRunnable = new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamController.DreamRecord.this.lambda$new$0();
            }
        };
        private final java.lang.Runnable mStopStubbornDreamRunnable = new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.dreams.DreamController.DreamRecord.this.lambda$new$1();
            }
        };
        private final android.os.IRemoteCallback mDreamingStartedCallback = new android.os.IRemoteCallback.Stub() { // from class: com.android.server.dreams.DreamController.DreamRecord.1
            public void sendResult(android.os.Bundle bundle) {
                com.android.server.dreams.DreamController.this.mHandler.post(com.android.server.dreams.DreamController.DreamRecord.this.mStopPreviousDreamsIfNeeded);
                com.android.server.dreams.DreamController.this.mHandler.post(com.android.server.dreams.DreamController.DreamRecord.this.mReleaseWakeLockIfNeeded);
            }
        };

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            if (this.mBound && !this.mConnected) {
                android.util.Slog.w(com.android.server.dreams.DreamController.TAG, "Bound dream did not connect in the time allotted");
                com.android.server.dreams.DreamController.this.stopDream(true, "slow to connect");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            android.util.Slog.w(com.android.server.dreams.DreamController.TAG, "Stubborn dream did not finish itself in the time allotted");
            com.android.server.dreams.DreamController.this.stopDream(true, "slow to finish");
            this.mStopReason = null;
        }

        DreamRecord(android.os.Binder binder, android.content.ComponentName componentName, boolean z, boolean z2, int i, android.os.PowerManager.WakeLock wakeLock) {
            this.mToken = binder;
            this.mName = componentName;
            this.mIsPreviewMode = z;
            this.mCanDoze = z2;
            this.mUserId = i;
            this.mWakeLock = wakeLock;
            if (this.mWakeLock != null) {
                this.mWakeLock.acquire();
            }
            com.android.server.dreams.DreamController.this.mHandler.postDelayed(this.mReleaseWakeLockIfNeeded, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.dreams.DreamController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.dreams.DreamController.DreamRecord.this.lambda$binderDied$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$2() {
            this.mService = null;
            if (com.android.server.dreams.DreamController.this.mCurrentDream == this) {
                if (com.android.server.dreams.DreamController.this.mResetScreenTimeoutOnUnexpectedDreamExit) {
                    com.android.server.dreams.DreamController.this.resetScreenTimeout();
                }
                com.android.server.dreams.DreamController.this.stopDream(true, "binder died");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, final android.os.IBinder iBinder) {
            com.android.server.dreams.DreamController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.dreams.DreamController.DreamRecord.this.lambda$onServiceConnected$3(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$3(android.os.IBinder iBinder) {
            this.mConnected = true;
            if (com.android.server.dreams.DreamController.this.mCurrentDream == this && this.mService == null) {
                com.android.server.dreams.DreamController.this.attach(android.service.dreams.IDreamService.Stub.asInterface(iBinder));
            } else {
                releaseWakeLockIfNeeded();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.server.dreams.DreamController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.dreams.DreamController.DreamRecord.this.lambda$onServiceDisconnected$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceDisconnected$4() {
            this.mService = null;
            if (com.android.server.dreams.DreamController.this.mCurrentDream == this) {
                if (com.android.server.dreams.DreamController.this.mResetScreenTimeoutOnUnexpectedDreamExit) {
                    com.android.server.dreams.DreamController.this.resetScreenTimeout();
                }
                com.android.server.dreams.DreamController.this.stopDream(true, "service disconnected");
            }
        }

        void stopPreviousDreamsIfNeeded() {
            if (com.android.server.dreams.DreamController.this.mCurrentDream == this) {
                com.android.server.dreams.DreamController.this.stopPreviousDreams();
            }
        }

        void releaseWakeLockIfNeeded() {
            if (this.mWakeLock != null) {
                this.mWakeLock.release();
                this.mWakeLock = null;
                com.android.server.dreams.DreamController.this.mHandler.removeCallbacks(this.mReleaseWakeLockIfNeeded);
            }
        }
    }
}
