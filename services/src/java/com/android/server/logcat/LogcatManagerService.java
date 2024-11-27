package com.android.server.logcat;

/* loaded from: classes2.dex */
public final class LogcatManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_CALLBACK = "EXTRA_CALLBACK";
    private static final int MSG_APPROVE_LOG_ACCESS = 1;
    private static final int MSG_DECLINE_LOG_ACCESS = 2;
    private static final int MSG_LOG_ACCESS_FINISHED = 3;
    private static final int MSG_LOG_ACCESS_REQUESTED = 0;
    private static final int MSG_LOG_ACCESS_STATUS_EXPIRED = 5;
    private static final int MSG_PENDING_TIMEOUT = 4;

    @com.android.internal.annotations.VisibleForTesting
    static final int PENDING_CONFIRMATION_TIMEOUT_MILLIS;
    private static final int STATUS_APPROVED = 2;
    private static final int STATUS_DECLINED = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATUS_EXPIRATION_TIMEOUT_MILLIS = 60000;
    private static final int STATUS_NEW_REQUEST = 0;
    private static final int STATUS_PENDING = 1;
    private static final java.lang.String TAG = "LogcatManagerService";
    private static final java.lang.String TARGET_ACTIVITY_NAME = "com.android.systemui.logcat.LogAccessDialogActivity";
    private static final java.lang.String TARGET_PACKAGE_NAME = "com.android.systemui";
    private final java.util.Map<com.android.server.logcat.LogcatManagerService.LogAccessClient, java.lang.Integer> mActiveLogAccessCount;
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private final com.android.server.logcat.LogcatManagerService.BinderService mBinderService;
    private final java.util.function.Supplier<java.lang.Long> mClock;
    private final android.content.Context mContext;
    private final com.android.server.logcat.LogcatManagerService.LogAccessDialogCallback mDialogCallback;
    private final android.os.Handler mHandler;
    private final com.android.server.logcat.LogcatManagerService.Injector mInjector;
    private final java.util.Map<com.android.server.logcat.LogcatManagerService.LogAccessClient, com.android.server.logcat.LogcatManagerService.LogAccessStatus> mLogAccessStatus;
    private android.os.ILogd mLogdService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LogAccessRequestStatus {
    }

    static {
        PENDING_CONFIRMATION_TIMEOUT_MILLIS = android.os.Build.IS_DEBUGGABLE ? com.android.server.policy.EventLogTags.SCREEN_TOGGLED : 400000;
    }

    private static final class LogAccessClient {

        @android.annotation.NonNull
        final java.lang.String mPackageName;
        final int mUid;

        LogAccessClient(int i, @android.annotation.NonNull java.lang.String str) {
            this.mUid = i;
            this.mPackageName = str;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.logcat.LogcatManagerService.LogAccessClient)) {
                return false;
            }
            com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient = (com.android.server.logcat.LogcatManagerService.LogAccessClient) obj;
            return this.mUid == logAccessClient.mUid && java.util.Objects.equals(this.mPackageName, logAccessClient.mPackageName);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mUid), this.mPackageName);
        }

        public java.lang.String toString() {
            return "LogAccessClient{mUid=" + this.mUid + ", mPackageName=" + this.mPackageName + '}';
        }
    }

    private static final class LogAccessRequest {
        final int mFd;
        final int mGid;
        final int mPid;
        final int mUid;

        private LogAccessRequest(int i, int i2, int i3, int i4) {
            this.mUid = i;
            this.mGid = i2;
            this.mPid = i3;
            this.mFd = i4;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.logcat.LogcatManagerService.LogAccessRequest)) {
                return false;
            }
            com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest = (com.android.server.logcat.LogcatManagerService.LogAccessRequest) obj;
            return this.mUid == logAccessRequest.mUid && this.mGid == logAccessRequest.mGid && this.mPid == logAccessRequest.mPid && this.mFd == logAccessRequest.mFd;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mUid), java.lang.Integer.valueOf(this.mGid), java.lang.Integer.valueOf(this.mPid), java.lang.Integer.valueOf(this.mFd));
        }

        public java.lang.String toString() {
            return "LogAccessRequest{mUid=" + this.mUid + ", mGid=" + this.mGid + ", mPid=" + this.mPid + ", mFd=" + this.mFd + '}';
        }
    }

    private static final class LogAccessStatus {
        final java.util.List<com.android.server.logcat.LogcatManagerService.LogAccessRequest> mPendingRequests;
        int mStatus;

        private LogAccessStatus() {
            this.mStatus = 0;
            this.mPendingRequests = new java.util.ArrayList();
        }
    }

    private final class BinderService extends android.os.logcat.ILogcatManagerService.Stub {
        private BinderService() {
        }

        public void startThread(int i, int i2, int i3, int i4) {
            com.android.server.logcat.LogcatManagerService.this.mHandler.sendMessageAtTime(com.android.server.logcat.LogcatManagerService.this.mHandler.obtainMessage(0, new com.android.server.logcat.LogcatManagerService.LogAccessRequest(i, i2, i3, i4)), ((java.lang.Long) com.android.server.logcat.LogcatManagerService.this.mClock.get()).longValue());
        }

        public void finishThread(int i, int i2, int i3, int i4) {
            com.android.server.logcat.LogcatManagerService.this.mHandler.sendMessageAtTime(com.android.server.logcat.LogcatManagerService.this.mHandler.obtainMessage(3, new com.android.server.logcat.LogcatManagerService.LogAccessRequest(i, i2, i3, i4)), ((java.lang.Long) com.android.server.logcat.LogcatManagerService.this.mClock.get()).longValue());
        }
    }

    final class LogAccessDialogCallback extends com.android.internal.app.ILogAccessDialogCallback.Stub {
        LogAccessDialogCallback() {
        }

        public void approveAccessForClient(int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.logcat.LogcatManagerService.this.mHandler.sendMessageAtTime(com.android.server.logcat.LogcatManagerService.this.mHandler.obtainMessage(1, new com.android.server.logcat.LogcatManagerService.LogAccessClient(i, str)), ((java.lang.Long) com.android.server.logcat.LogcatManagerService.this.mClock.get()).longValue());
        }

        public void declineAccessForClient(int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.logcat.LogcatManagerService.this.mHandler.sendMessageAtTime(com.android.server.logcat.LogcatManagerService.this.mHandler.obtainMessage(2, new com.android.server.logcat.LogcatManagerService.LogAccessClient(i, str)), ((java.lang.Long) com.android.server.logcat.LogcatManagerService.this.mClock.get()).longValue());
        }
    }

    private android.os.ILogd getLogdService() {
        if (this.mLogdService == null) {
            this.mLogdService = this.mInjector.getLogdService();
        }
        return this.mLogdService;
    }

    private static class LogAccessRequestHandler extends android.os.Handler {
        private final com.android.server.logcat.LogcatManagerService mService;

        LogAccessRequestHandler(android.os.Looper looper, com.android.server.logcat.LogcatManagerService logcatManagerService) {
            super(looper);
            this.mService = logcatManagerService;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    this.mService.onLogAccessRequested((com.android.server.logcat.LogcatManagerService.LogAccessRequest) message.obj);
                    break;
                case 1:
                    this.mService.onAccessApprovedForClient((com.android.server.logcat.LogcatManagerService.LogAccessClient) message.obj);
                    break;
                case 2:
                    this.mService.onAccessDeclinedForClient((com.android.server.logcat.LogcatManagerService.LogAccessClient) message.obj);
                    break;
                case 3:
                    this.mService.onLogAccessFinished((com.android.server.logcat.LogcatManagerService.LogAccessRequest) message.obj);
                    break;
                case 4:
                    this.mService.onPendingTimeoutExpired((com.android.server.logcat.LogcatManagerService.LogAccessClient) message.obj);
                    break;
                case 5:
                    this.mService.onAccessStatusExpired((com.android.server.logcat.LogcatManagerService.LogAccessClient) message.obj);
                    break;
            }
        }
    }

    static class Injector {
        Injector() {
        }

        protected java.util.function.Supplier<java.lang.Long> createClock() {
            return new java.util.function.Supplier() { // from class: com.android.server.logcat.LogcatManagerService$Injector$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis());
                }
            };
        }

        protected android.os.Looper getLooper() {
            return android.os.Looper.getMainLooper();
        }

        protected android.os.ILogd getLogdService() {
            return android.os.ILogd.Stub.asInterface(android.os.ServiceManager.getService("logd"));
        }
    }

    public LogcatManagerService(android.content.Context context) {
        this(context, new com.android.server.logcat.LogcatManagerService.Injector());
    }

    public LogcatManagerService(android.content.Context context, com.android.server.logcat.LogcatManagerService.Injector injector) {
        super(context);
        this.mLogAccessStatus = new android.util.ArrayMap();
        this.mActiveLogAccessCount = new android.util.ArrayMap();
        this.mContext = context;
        this.mInjector = injector;
        this.mClock = injector.createClock();
        this.mBinderService = new com.android.server.logcat.LogcatManagerService.BinderService();
        this.mDialogCallback = new com.android.server.logcat.LogcatManagerService.LogAccessDialogCallback();
        this.mHandler = new com.android.server.logcat.LogcatManagerService.LogAccessRequestHandler(injector.getLooper(), this);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        try {
            this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            publishBinderService("logcat", this.mBinderService);
        } catch (java.lang.Throwable th) {
            android.util.Slog.e(TAG, "Could not start the LogcatManagerService.", th);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.logcat.LogcatManagerService.LogAccessDialogCallback getDialogCallback() {
        return this.mDialogCallback;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.logcat.ILogcatManagerService getBinderService() {
        return this.mBinderService;
    }

    @android.annotation.Nullable
    private com.android.server.logcat.LogcatManagerService.LogAccessClient getClientForRequest(com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest) {
        java.lang.String packageName = getPackageName(logAccessRequest);
        if (packageName == null) {
            return null;
        }
        return new com.android.server.logcat.LogcatManagerService.LogAccessClient(logAccessRequest.mUid, packageName);
    }

    private java.lang.String getPackageName(com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            android.util.Slog.e(TAG, "PackageManager is null, declining the logd access");
            return null;
        }
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(logAccessRequest.mUid);
        if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
            android.util.Slog.e(TAG, "Unknown calling package name, declining the logd access");
            return null;
        }
        if (this.mActivityManagerInternal != null) {
            int i = logAccessRequest.mPid;
            java.lang.String packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
            while (true) {
                if ((packageNameByPid == null || !com.android.internal.util.ArrayUtils.contains(packagesForUid, packageNameByPid)) && i != -1) {
                    i = android.os.Process.getParentPid(i);
                    packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
                }
            }
            if (packageNameByPid != null && com.android.internal.util.ArrayUtils.contains(packagesForUid, packageNameByPid)) {
                return packageNameByPid;
            }
        }
        java.util.Arrays.sort(packagesForUid);
        java.lang.String str = packagesForUid[0];
        if (str == null || str.isEmpty()) {
            android.util.Slog.e(TAG, "Unknown calling package name, declining the logd access");
            return null;
        }
        return str;
    }

    void onLogAccessRequested(com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest) {
        com.android.server.logcat.LogcatManagerService.LogAccessClient clientForRequest = getClientForRequest(logAccessRequest);
        if (clientForRequest == null) {
            declineRequest(logAccessRequest);
        }
        com.android.server.logcat.LogcatManagerService.LogAccessStatus logAccessStatus = this.mLogAccessStatus.get(clientForRequest);
        if (logAccessStatus == null) {
            logAccessStatus = new com.android.server.logcat.LogcatManagerService.LogAccessStatus();
            this.mLogAccessStatus.put(clientForRequest, logAccessStatus);
        }
        switch (logAccessStatus.mStatus) {
            case 0:
                logAccessStatus.mPendingRequests.add(logAccessRequest);
                processNewLogAccessRequest(clientForRequest);
                break;
            case 1:
                logAccessStatus.mPendingRequests.add(logAccessRequest);
                break;
            case 2:
                approveRequest(clientForRequest, logAccessRequest);
                break;
            case 3:
                declineRequest(logAccessRequest);
                break;
        }
    }

    private boolean shouldShowConfirmationDialog(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        return this.mActivityManagerInternal.getUidProcessState(logAccessClient.mUid) == 2;
    }

    private void processNewLogAccessRequest(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        if (this.mActivityManagerInternal.getInstrumentationSourceUid(logAccessClient.mUid) != -1) {
            onAccessApprovedForClient(logAccessClient);
            return;
        }
        if (!shouldShowConfirmationDialog(logAccessClient)) {
            onAccessDeclinedForClient(logAccessClient);
            return;
        }
        this.mLogAccessStatus.get(logAccessClient).mStatus = 1;
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(4, logAccessClient), this.mClock.get().longValue() + PENDING_CONFIRMATION_TIMEOUT_MILLIS);
        android.content.Intent createIntent = createIntent(logAccessClient);
        createIntent.setFlags(268435456);
        createIntent.setComponent(new android.content.ComponentName(TARGET_PACKAGE_NAME, TARGET_ACTIVITY_NAME));
        this.mContext.startActivityAsUser(createIntent, android.os.UserHandle.SYSTEM);
    }

    void onAccessApprovedForClient(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        scheduleStatusExpiry(logAccessClient);
        com.android.server.logcat.LogcatManagerService.LogAccessStatus logAccessStatus = this.mLogAccessStatus.get(logAccessClient);
        if (logAccessStatus != null) {
            java.util.Iterator<com.android.server.logcat.LogcatManagerService.LogAccessRequest> it = logAccessStatus.mPendingRequests.iterator();
            while (it.hasNext()) {
                approveRequest(logAccessClient, it.next());
            }
            logAccessStatus.mStatus = 2;
            logAccessStatus.mPendingRequests.clear();
        }
    }

    void onAccessDeclinedForClient(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        scheduleStatusExpiry(logAccessClient);
        com.android.server.logcat.LogcatManagerService.LogAccessStatus logAccessStatus = this.mLogAccessStatus.get(logAccessClient);
        if (logAccessStatus != null) {
            java.util.Iterator<com.android.server.logcat.LogcatManagerService.LogAccessRequest> it = logAccessStatus.mPendingRequests.iterator();
            while (it.hasNext()) {
                declineRequest(it.next());
            }
            logAccessStatus.mStatus = 3;
            logAccessStatus.mPendingRequests.clear();
        }
    }

    private void scheduleStatusExpiry(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        this.mHandler.removeMessages(4, logAccessClient);
        this.mHandler.removeMessages(5, logAccessClient);
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(5, logAccessClient), this.mClock.get().longValue() + 60000);
    }

    void onPendingTimeoutExpired(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        com.android.server.logcat.LogcatManagerService.LogAccessStatus logAccessStatus = this.mLogAccessStatus.get(logAccessClient);
        if (logAccessStatus != null && logAccessStatus.mStatus == 1) {
            onAccessDeclinedForClient(logAccessClient);
        }
    }

    void onAccessStatusExpired(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        this.mLogAccessStatus.remove(logAccessClient);
    }

    void onLogAccessFinished(com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest) {
        com.android.server.logcat.LogcatManagerService.LogAccessClient clientForRequest = getClientForRequest(logAccessRequest);
        int intValue = this.mActiveLogAccessCount.getOrDefault(clientForRequest, 1).intValue() - 1;
        if (intValue == 0) {
            this.mActiveLogAccessCount.remove(clientForRequest);
        } else {
            this.mActiveLogAccessCount.put(clientForRequest, java.lang.Integer.valueOf(intValue));
        }
    }

    private void approveRequest(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient, com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest) {
        try {
            try {
                getLogdService().approve(logAccessRequest.mUid, logAccessRequest.mGid, logAccessRequest.mPid, logAccessRequest.mFd);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.w(TAG, "Logd connection no longer valid while approving, trying once more.");
                this.mLogdService = null;
                getLogdService().approve(logAccessRequest.mUid, logAccessRequest.mGid, logAccessRequest.mPid, logAccessRequest.mFd);
            }
            this.mActiveLogAccessCount.put(logAccessClient, java.lang.Integer.valueOf(this.mActiveLogAccessCount.getOrDefault(logAccessClient, 0).intValue() + 1));
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Fails to call remote functions", e2);
        }
    }

    private void declineRequest(com.android.server.logcat.LogcatManagerService.LogAccessRequest logAccessRequest) {
        try {
            try {
                getLogdService().decline(logAccessRequest.mUid, logAccessRequest.mGid, logAccessRequest.mPid, logAccessRequest.mFd);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.w(TAG, "Logd connection no longer valid while declining, trying once more.");
                this.mLogdService = null;
                getLogdService().decline(logAccessRequest.mUid, logAccessRequest.mGid, logAccessRequest.mPid, logAccessRequest.mFd);
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Fails to call remote functions", e2);
        }
    }

    public android.content.Intent createIntent(com.android.server.logcat.LogcatManagerService.LogAccessClient logAccessClient) {
        android.content.Intent intent = new android.content.Intent();
        intent.setFlags(268468224);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", logAccessClient.mPackageName);
        intent.putExtra("android.intent.extra.UID", logAccessClient.mUid);
        intent.putExtra(EXTRA_CALLBACK, this.mDialogCallback.asBinder());
        return intent;
    }
}
