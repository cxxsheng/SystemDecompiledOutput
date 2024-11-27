package com.android.server.attention;

/* loaded from: classes.dex */
public class AttentionManagerService extends com.android.server.SystemService {

    @com.android.internal.annotations.VisibleForTesting
    protected static final int ATTENTION_CACHE_BUFFER_SIZE = 5;
    private static final long CONNECTION_TTL_MILLIS = 60000;
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_SERVICE_ENABLED = true;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_STALE_AFTER_MILLIS = 1000;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_SERVICE_ENABLED = "service_enabled";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_STALE_AFTER_MILLIS = "stale_after_millis";
    private static final java.lang.String LOG_TAG = "AttentionManagerService";
    private static final long SERVICE_BINDING_WAIT_MILLIS = 1000;
    private static java.lang.String sTestAttentionServicePackage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.attention.AttentionManagerService.AttentionCheckCacheBuffer mAttentionCheckCacheBuffer;
    private com.android.server.attention.AttentionManagerService.AttentionHandler mAttentionHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBinding;

    @com.android.internal.annotations.VisibleForTesting
    android.content.ComponentName mComponentName;
    private final com.android.server.attention.AttentionManagerService.AttentionServiceConnection mConnection;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.attention.AttentionManagerService.AttentionCheck mCurrentAttentionCheck;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.attention.AttentionManagerService.ProximityUpdate mCurrentProximityUpdate;

    @com.android.internal.annotations.VisibleForTesting
    boolean mIsProximityEnabled;

    @com.android.internal.annotations.VisibleForTesting
    boolean mIsServiceEnabled;
    private final java.lang.Object mLock;
    private final android.os.PowerManager mPowerManager;
    private final android.hardware.SensorPrivacyManager mPrivacyManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    protected android.service.attention.IAttentionService mService;
    private java.util.concurrent.CountDownLatch mServiceBindingLatch;

    @com.android.internal.annotations.VisibleForTesting
    long mStaleAfterMillis;

    public AttentionManagerService(android.content.Context context) {
        this(context, (android.os.PowerManager) context.getSystemService("power"), new java.lang.Object(), null);
        this.mAttentionHandler = new com.android.server.attention.AttentionManagerService.AttentionHandler();
    }

    @com.android.internal.annotations.VisibleForTesting
    AttentionManagerService(android.content.Context context, android.os.PowerManager powerManager, java.lang.Object obj, com.android.server.attention.AttentionManagerService.AttentionHandler attentionHandler) {
        super(context);
        this.mConnection = new com.android.server.attention.AttentionManagerService.AttentionServiceConnection();
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mPowerManager = powerManager;
        this.mLock = obj;
        this.mAttentionHandler = attentionHandler;
        this.mPrivacyManager = android.hardware.SensorPrivacyManager.getInstance(context);
        this.mServiceBindingLatch = new java.util.concurrent.CountDownLatch(1);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            this.mContext.registerReceiver(new com.android.server.attention.AttentionManagerService.ScreenStateReceiver(), new android.content.IntentFilter("android.intent.action.SCREEN_OFF"));
            readValuesFromDeviceConfig();
            android.provider.DeviceConfig.addOnPropertiesChangedListener("attention_manager_service", android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.attention.AttentionManagerService$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.attention.AttentionManagerService.this.lambda$onBootPhase$0(properties);
                }
            });
            this.mIsProximityEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_enableNightMode);
            android.util.Slog.i(LOG_TAG, "mIsProximityEnabled is: " + this.mIsProximityEnabled);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("attention", new com.android.server.attention.AttentionManagerService.BinderService());
        publishLocalService(android.attention.AttentionManagerInternal.class, new com.android.server.attention.AttentionManagerService.LocalService());
    }

    public static boolean isServiceConfigured(android.content.Context context) {
        return !android.text.TextUtils.isEmpty(getServiceConfigPackage(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isServiceAvailable() {
        if (this.mComponentName == null) {
            this.mComponentName = resolveAttentionService(this.mContext);
        }
        return this.mComponentName != null;
    }

    private boolean getIsServiceEnabled() {
        return android.provider.DeviceConfig.getBoolean("attention_manager_service", KEY_SERVICE_ENABLED, true);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getStaleAfterMillis() {
        long j = android.provider.DeviceConfig.getLong("attention_manager_service", KEY_STALE_AFTER_MILLIS, 1000L);
        if (j < 0 || j > com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY) {
            android.util.Slog.w(LOG_TAG, "Bad flag value supplied for: stale_after_millis");
            return 1000L;
        }
        return j;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        char c;
        for (java.lang.String str : set) {
            switch (str.hashCode()) {
                case -337803025:
                    if (str.equals(KEY_STALE_AFTER_MILLIS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1914663863:
                    if (str.equals(KEY_SERVICE_ENABLED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    readValuesFromDeviceConfig();
                    return;
                default:
                    android.util.Slog.i(LOG_TAG, "Ignoring change on " + str);
            }
        }
    }

    private void readValuesFromDeviceConfig() {
        this.mIsServiceEnabled = getIsServiceEnabled();
        this.mStaleAfterMillis = getStaleAfterMillis();
        android.util.Slog.i(LOG_TAG, "readValuesFromDeviceConfig():\nmIsServiceEnabled=" + this.mIsServiceEnabled + "\nmStaleAfterMillis=" + this.mStaleAfterMillis);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean checkAttention(long j, android.attention.AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
        java.util.Objects.requireNonNull(attentionCallbackInternal);
        if (!this.mIsServiceEnabled) {
            android.util.Slog.w(LOG_TAG, "Trying to call checkAttention() on an unsupported device.");
            return false;
        }
        if (!isServiceAvailable()) {
            android.util.Slog.w(LOG_TAG, "Service is not available at this moment.");
            return false;
        }
        if (this.mPrivacyManager.isSensorPrivacyEnabled(2)) {
            android.util.Slog.w(LOG_TAG, "Camera is locked by a toggle.");
            return false;
        }
        if (!this.mPowerManager.isInteractive() || this.mPowerManager.isPowerSaveMode()) {
            return false;
        }
        synchronized (this.mLock) {
            freeIfInactiveLocked();
            bindLocked();
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        awaitServiceBinding(java.lang.Math.min(1000L, j));
        synchronized (this.mLock) {
            try {
                com.android.server.attention.AttentionManagerService.AttentionCheckCache last = this.mAttentionCheckCacheBuffer == null ? null : this.mAttentionCheckCacheBuffer.getLast();
                if (last != null && uptimeMillis < last.mLastComputed + this.mStaleAfterMillis) {
                    attentionCallbackInternal.onSuccess(last.mResult, last.mTimestamp);
                    return true;
                }
                if (this.mCurrentAttentionCheck != null && (!this.mCurrentAttentionCheck.mIsDispatched || !this.mCurrentAttentionCheck.mIsFulfilled)) {
                    return false;
                }
                this.mCurrentAttentionCheck = new com.android.server.attention.AttentionManagerService.AttentionCheck(attentionCallbackInternal, this);
                if (this.mService != null) {
                    try {
                        cancelAfterTimeoutLocked(j);
                        this.mService.checkAttention(this.mCurrentAttentionCheck.mIAttentionCallback);
                        this.mCurrentAttentionCheck.mIsDispatched = true;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(LOG_TAG, "Cannot call into the AttentionService");
                        return false;
                    }
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void cancelAttentionCheck(android.attention.AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
        synchronized (this.mLock) {
            try {
                if (!this.mCurrentAttentionCheck.mCallbackInternal.equals(attentionCallbackInternal)) {
                    android.util.Slog.w(LOG_TAG, "Cannot cancel a non-current request");
                } else {
                    cancel();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean onStartProximityUpdates(android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal proximityUpdateCallbackInternal) {
        java.util.Objects.requireNonNull(proximityUpdateCallbackInternal);
        if (!this.mIsProximityEnabled) {
            android.util.Slog.w(LOG_TAG, "Trying to call onProximityUpdate() on an unsupported device.");
            return false;
        }
        if (!isServiceAvailable()) {
            android.util.Slog.w(LOG_TAG, "Service is not available at this moment.");
            return false;
        }
        if (!this.mPowerManager.isInteractive()) {
            android.util.Slog.w(LOG_TAG, "Proximity Service is unavailable during screen off at this moment.");
            return false;
        }
        synchronized (this.mLock) {
            freeIfInactiveLocked();
            bindLocked();
        }
        awaitServiceBinding(1000L);
        synchronized (this.mLock) {
            try {
                if (this.mCurrentProximityUpdate != null && this.mCurrentProximityUpdate.mStartedUpdates) {
                    if (this.mCurrentProximityUpdate.mCallbackInternal == proximityUpdateCallbackInternal) {
                        android.util.Slog.w(LOG_TAG, "Provided callback is already registered. Skipping.");
                        return true;
                    }
                    android.util.Slog.w(LOG_TAG, "New proximity update cannot be processed because there is already an ongoing update");
                    return false;
                }
                this.mCurrentProximityUpdate = new com.android.server.attention.AttentionManagerService.ProximityUpdate(proximityUpdateCallbackInternal);
                return this.mCurrentProximityUpdate.startUpdates();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onStopProximityUpdates(android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal proximityUpdateCallbackInternal) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentProximityUpdate != null) {
                    if (this.mCurrentProximityUpdate.mCallbackInternal.equals(proximityUpdateCallbackInternal) && this.mCurrentProximityUpdate.mStartedUpdates) {
                        this.mCurrentProximityUpdate.cancelUpdates();
                        this.mCurrentProximityUpdate = null;
                        return;
                    }
                }
                android.util.Slog.w(LOG_TAG, "Cannot stop a non-current callback");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    protected void freeIfInactiveLocked() {
        this.mAttentionHandler.removeMessages(1);
        this.mAttentionHandler.sendEmptyMessageDelayed(1, 60000L);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void cancelAfterTimeoutLocked(long j) {
        this.mAttentionHandler.sendEmptyMessageDelayed(2, j);
    }

    private static java.lang.String getServiceConfigPackage(android.content.Context context) {
        return context.getPackageManager().getAttentionServicePackageName();
    }

    private void awaitServiceBinding(long j) {
        try {
            this.mServiceBindingLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.e(LOG_TAG, "Interrupted while waiting to bind Attention Service.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.ComponentName resolveAttentionService(android.content.Context context) {
        int i;
        java.lang.String str;
        java.lang.String serviceConfigPackage = getServiceConfigPackage(context);
        if (!android.text.TextUtils.isEmpty(sTestAttentionServicePackage)) {
            str = sTestAttentionServicePackage;
            i = 128;
        } else {
            if (android.text.TextUtils.isEmpty(serviceConfigPackage)) {
                return null;
            }
            i = 1048576;
            str = serviceConfigPackage;
        }
        android.content.pm.ResolveInfo resolveService = context.getPackageManager().resolveService(new android.content.Intent("android.service.attention.AttentionService").setPackage(str), i);
        if (resolveService == null || resolveService.serviceInfo == null) {
            android.util.Slog.wtf(LOG_TAG, java.lang.String.format("Service %s not found in package %s", "android.service.attention.AttentionService", serviceConfigPackage));
            return null;
        }
        android.content.pm.ServiceInfo serviceInfo = resolveService.serviceInfo;
        if (!"android.permission.BIND_ATTENTION_SERVICE".equals(serviceInfo.permission)) {
            android.util.Slog.e(LOG_TAG, java.lang.String.format("Service %s should require %s permission. Found %s permission", serviceInfo.getComponentName(), "android.permission.BIND_ATTENTION_SERVICE", serviceInfo.permission));
            return null;
        }
        return serviceInfo.getComponentName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Attention Manager Service (dumpsys attention) state:\n");
        indentingPrintWriter.println("isServiceEnabled=" + this.mIsServiceEnabled);
        indentingPrintWriter.println("mIsProximityEnabled=" + this.mIsProximityEnabled);
        indentingPrintWriter.println("mStaleAfterMillis=" + this.mStaleAfterMillis);
        indentingPrintWriter.println("AttentionServicePackageName=" + getServiceConfigPackage(this.mContext));
        indentingPrintWriter.println("Resolved component:");
        if (this.mComponentName != null) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Component=" + this.mComponentName.getPackageName());
            indentingPrintWriter.println("Class=" + this.mComponentName.getClassName());
            indentingPrintWriter.decreaseIndent();
        }
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("binding=" + this.mBinding);
                indentingPrintWriter.println("current attention check:");
                if (this.mCurrentAttentionCheck != null) {
                    this.mCurrentAttentionCheck.dump(indentingPrintWriter);
                }
                if (this.mAttentionCheckCacheBuffer != null) {
                    this.mAttentionCheckCacheBuffer.dump(indentingPrintWriter);
                }
                if (this.mCurrentProximityUpdate != null) {
                    this.mCurrentProximityUpdate.dump(indentingPrintWriter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final class LocalService extends android.attention.AttentionManagerInternal {
        private LocalService() {
        }

        public boolean isAttentionServiceSupported() {
            return com.android.server.attention.AttentionManagerService.this.mIsServiceEnabled;
        }

        public boolean isProximitySupported() {
            return com.android.server.attention.AttentionManagerService.this.mIsProximityEnabled;
        }

        public boolean checkAttention(long j, android.attention.AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
            return com.android.server.attention.AttentionManagerService.this.checkAttention(j, attentionCallbackInternal);
        }

        public void cancelAttentionCheck(android.attention.AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal) {
            com.android.server.attention.AttentionManagerService.this.cancelAttentionCheck(attentionCallbackInternal);
        }

        public boolean onStartProximityUpdates(android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal proximityUpdateCallbackInternal) {
            return com.android.server.attention.AttentionManagerService.this.onStartProximityUpdates(proximityUpdateCallbackInternal);
        }

        public void onStopProximityUpdates(android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal proximityUpdateCallbackInternal) {
            com.android.server.attention.AttentionManagerService.this.onStopProximityUpdates(proximityUpdateCallbackInternal);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static final class AttentionCheckCacheBuffer {
        private final com.android.server.attention.AttentionManagerService.AttentionCheckCache[] mQueue = new com.android.server.attention.AttentionManagerService.AttentionCheckCache[5];
        private int mStartIndex = 0;
        private int mSize = 0;

        AttentionCheckCacheBuffer() {
        }

        public com.android.server.attention.AttentionManagerService.AttentionCheckCache getLast() {
            int i = ((this.mStartIndex + this.mSize) - 1) % 5;
            if (this.mSize == 0) {
                return null;
            }
            return this.mQueue[i];
        }

        public void add(@android.annotation.NonNull com.android.server.attention.AttentionManagerService.AttentionCheckCache attentionCheckCache) {
            this.mQueue[(this.mStartIndex + this.mSize) % 5] = attentionCheckCache;
            if (this.mSize == 5) {
                this.mStartIndex++;
            } else {
                this.mSize++;
            }
        }

        public com.android.server.attention.AttentionManagerService.AttentionCheckCache get(int i) {
            if (i >= this.mSize) {
                return null;
            }
            return this.mQueue[(this.mStartIndex + i) % 5];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("attention check cache:");
            for (int i = 0; i < this.mSize; i++) {
                com.android.server.attention.AttentionManagerService.AttentionCheckCache attentionCheckCache = get(i);
                if (attentionCheckCache != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("timestamp=" + attentionCheckCache.mTimestamp);
                    indentingPrintWriter.println("result=" + attentionCheckCache.mResult);
                    indentingPrintWriter.decreaseIndent();
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static final class AttentionCheckCache {
        private final long mLastComputed;
        private final int mResult;
        private final long mTimestamp;

        AttentionCheckCache(long j, int i, long j2) {
            this.mLastComputed = j;
            this.mResult = i;
            this.mTimestamp = j2;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class AttentionCheck {
        private final android.attention.AttentionManagerInternal.AttentionCallbackInternal mCallbackInternal;
        private final android.service.attention.IAttentionCallback mIAttentionCallback;
        private boolean mIsDispatched;
        private boolean mIsFulfilled;

        AttentionCheck(final android.attention.AttentionManagerInternal.AttentionCallbackInternal attentionCallbackInternal, final com.android.server.attention.AttentionManagerService attentionManagerService) {
            this.mCallbackInternal = attentionCallbackInternal;
            this.mIAttentionCallback = new android.service.attention.IAttentionCallback.Stub() { // from class: com.android.server.attention.AttentionManagerService.AttentionCheck.1
                public void onSuccess(int i, long j) {
                    if (com.android.server.attention.AttentionManagerService.AttentionCheck.this.mIsFulfilled) {
                        return;
                    }
                    com.android.server.attention.AttentionManagerService.AttentionCheck.this.mIsFulfilled = true;
                    attentionCallbackInternal.onSuccess(i, j);
                    logStats(i);
                    attentionManagerService.appendResultToAttentionCacheBuffer(new com.android.server.attention.AttentionManagerService.AttentionCheckCache(android.os.SystemClock.uptimeMillis(), i, j));
                }

                public void onFailure(int i) {
                    if (com.android.server.attention.AttentionManagerService.AttentionCheck.this.mIsFulfilled) {
                        return;
                    }
                    com.android.server.attention.AttentionManagerService.AttentionCheck.this.mIsFulfilled = true;
                    attentionCallbackInternal.onFailure(i);
                    logStats(i);
                }

                private void logStats(int i) {
                    com.android.internal.util.FrameworkStatsLog.write(143, i);
                }
            };
        }

        void cancelInternal() {
            this.mIsFulfilled = true;
            this.mCallbackInternal.onFailure(3);
        }

        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("is dispatched=" + this.mIsDispatched);
            indentingPrintWriter.println("is fulfilled:=" + this.mIsFulfilled);
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class ProximityUpdate {
        private final android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal mCallbackInternal;
        private final android.service.attention.IProximityUpdateCallback mIProximityUpdateCallback;
        private boolean mStartedUpdates;

        ProximityUpdate(android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal proximityUpdateCallbackInternal) {
            this.mCallbackInternal = proximityUpdateCallbackInternal;
            this.mIProximityUpdateCallback = new android.service.attention.IProximityUpdateCallback.Stub() { // from class: com.android.server.attention.AttentionManagerService.ProximityUpdate.1
                public void onProximityUpdate(double d) {
                    com.android.server.attention.AttentionManagerService.ProximityUpdate.this.mCallbackInternal.onProximityUpdate(d);
                    synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                        com.android.server.attention.AttentionManagerService.this.freeIfInactiveLocked();
                    }
                }
            };
        }

        boolean startUpdates() {
            synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                try {
                    if (this.mStartedUpdates) {
                        android.util.Slog.w(com.android.server.attention.AttentionManagerService.LOG_TAG, "Already registered to a proximity service.");
                        return false;
                    }
                    if (com.android.server.attention.AttentionManagerService.this.mService == null) {
                        android.util.Slog.w(com.android.server.attention.AttentionManagerService.LOG_TAG, "There is no service bound. Proximity update request rejected.");
                        return false;
                    }
                    try {
                        com.android.server.attention.AttentionManagerService.this.mService.onStartProximityUpdates(this.mIProximityUpdateCallback);
                        this.mStartedUpdates = true;
                        return true;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.attention.AttentionManagerService.LOG_TAG, "Cannot call into the AttentionService", e);
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void cancelUpdates() {
            synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                if (this.mStartedUpdates) {
                    if (com.android.server.attention.AttentionManagerService.this.mService == null) {
                        this.mStartedUpdates = false;
                        return;
                    }
                    try {
                        com.android.server.attention.AttentionManagerService.this.mService.onStopProximityUpdates();
                        this.mStartedUpdates = false;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.attention.AttentionManagerService.LOG_TAG, "Cannot call into the AttentionService", e);
                    }
                }
            }
        }

        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("is StartedUpdates=" + this.mStartedUpdates);
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendResultToAttentionCacheBuffer(com.android.server.attention.AttentionManagerService.AttentionCheckCache attentionCheckCache) {
        synchronized (this.mLock) {
            try {
                if (this.mAttentionCheckCacheBuffer == null) {
                    this.mAttentionCheckCacheBuffer = new com.android.server.attention.AttentionManagerService.AttentionCheckCacheBuffer();
                }
                this.mAttentionCheckCacheBuffer.add(attentionCheckCache);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class AttentionServiceConnection implements android.content.ServiceConnection {
        private AttentionServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            init(android.service.attention.IAttentionService.Stub.asInterface(iBinder));
            com.android.server.attention.AttentionManagerService.this.mServiceBindingLatch.countDown();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            cleanupService();
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            cleanupService();
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(android.content.ComponentName componentName) {
            cleanupService();
        }

        void cleanupService() {
            init(null);
            com.android.server.attention.AttentionManagerService.this.mServiceBindingLatch = new java.util.concurrent.CountDownLatch(1);
        }

        private void init(@android.annotation.Nullable android.service.attention.IAttentionService iAttentionService) {
            synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                com.android.server.attention.AttentionManagerService.this.mService = iAttentionService;
                com.android.server.attention.AttentionManagerService.this.mBinding = false;
                com.android.server.attention.AttentionManagerService.this.handlePendingCallbackLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void handlePendingCallbackLocked() {
        if (this.mCurrentAttentionCheck != null && !this.mCurrentAttentionCheck.mIsDispatched) {
            if (this.mService != null) {
                try {
                    this.mService.checkAttention(this.mCurrentAttentionCheck.mIAttentionCallback);
                    this.mCurrentAttentionCheck.mIsDispatched = true;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(LOG_TAG, "Cannot call into the AttentionService");
                }
            } else {
                this.mCurrentAttentionCheck.mCallbackInternal.onFailure(2);
            }
        }
        if (this.mCurrentProximityUpdate != null && this.mCurrentProximityUpdate.mStartedUpdates) {
            if (this.mService != null) {
                try {
                    this.mService.onStartProximityUpdates(this.mCurrentProximityUpdate.mIProximityUpdateCallback);
                    return;
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(LOG_TAG, "Cannot call into the AttentionService", e2);
                    return;
                }
            }
            this.mCurrentProximityUpdate.cancelUpdates();
            this.mCurrentProximityUpdate = null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected class AttentionHandler extends android.os.Handler {
        private static final int ATTENTION_CHECK_TIMEOUT = 2;
        private static final int CHECK_CONNECTION_EXPIRATION = 1;

        AttentionHandler() {
            super(android.os.Looper.myLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                        com.android.server.attention.AttentionManagerService.this.cancelAndUnbindLocked();
                    }
                    return;
                case 2:
                    synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                        com.android.server.attention.AttentionManagerService.this.cancel();
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void cancel() {
        if (this.mCurrentAttentionCheck.mIsFulfilled) {
            return;
        }
        if (this.mService == null) {
            this.mCurrentAttentionCheck.cancelInternal();
            return;
        }
        try {
            this.mService.cancelAttentionCheck(this.mCurrentAttentionCheck.mIAttentionCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Unable to cancel attention check");
            this.mCurrentAttentionCheck.cancelInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void cancelAndUnbindLocked() {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentAttentionCheck != null) {
                    cancel();
                }
                if (this.mCurrentProximityUpdate != null) {
                    this.mCurrentProximityUpdate.cancelUpdates();
                }
                if (this.mService == null) {
                    return;
                }
                this.mAttentionHandler.post(new java.lang.Runnable() { // from class: com.android.server.attention.AttentionManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.attention.AttentionManagerService.this.lambda$cancelAndUnbindLocked$1();
                    }
                });
                this.mConnection.cleanupService();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAndUnbindLocked$1() {
        this.mContext.unbindService(this.mConnection);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void bindLocked() {
        if (this.mBinding || this.mService != null) {
            return;
        }
        this.mBinding = true;
        this.mAttentionHandler.post(new java.lang.Runnable() { // from class: com.android.server.attention.AttentionManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.attention.AttentionManagerService.this.lambda$bindLocked$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindLocked$2() {
        this.mContext.bindServiceAsUser(new android.content.Intent("android.service.attention.AttentionService").setComponent(this.mComponentName), this.mConnection, 67112961, android.os.UserHandle.CURRENT);
    }

    private final class ScreenStateReceiver extends android.content.BroadcastReceiver {
        private ScreenStateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                    com.android.server.attention.AttentionManagerService.this.cancelAndUnbindLocked();
                }
            }
        }
    }

    private final class AttentionManagerServiceShellCommand extends android.os.ShellCommand {
        final com.android.server.attention.AttentionManagerService.AttentionManagerServiceShellCommand.TestableAttentionCallbackInternal mTestableAttentionCallback;
        final com.android.server.attention.AttentionManagerService.AttentionManagerServiceShellCommand.TestableProximityUpdateCallbackInternal mTestableProximityUpdateCallback;

        class TestableAttentionCallbackInternal extends android.attention.AttentionManagerInternal.AttentionCallbackInternal {
            private int mLastCallbackCode = -1;

            TestableAttentionCallbackInternal() {
            }

            public void onSuccess(int i, long j) {
                this.mLastCallbackCode = i;
            }

            public void onFailure(int i) {
                this.mLastCallbackCode = i;
            }

            public void reset() {
                this.mLastCallbackCode = -1;
            }

            public int getLastCallbackCode() {
                return this.mLastCallbackCode;
            }
        }

        private AttentionManagerServiceShellCommand() {
            this.mTestableAttentionCallback = new com.android.server.attention.AttentionManagerService.AttentionManagerServiceShellCommand.TestableAttentionCallbackInternal();
            this.mTestableProximityUpdateCallback = new com.android.server.attention.AttentionManagerService.AttentionManagerServiceShellCommand.TestableProximityUpdateCallbackInternal();
        }

        class TestableProximityUpdateCallbackInternal implements android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal {
            private double mLastCallbackCode = -1.0d;

            TestableProximityUpdateCallbackInternal() {
            }

            public void onProximityUpdate(double d) {
                this.mLastCallbackCode = d;
            }

            public void reset() {
                this.mLastCallbackCode = -1.0d;
            }

            public double getLastCallbackCode() {
                return this.mLastCallbackCode;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(@android.annotation.Nullable java.lang.String str) {
            char c;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            java.io.PrintWriter errPrintWriter = getErrPrintWriter();
            try {
                boolean z = false;
                switch (str.hashCode()) {
                    case -1208709968:
                        if (str.equals("getLastTestCallbackCode")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1002424240:
                        if (str.equals("getAttentionServiceComponent")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -415045819:
                        if (str.equals("setTestableAttentionService")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3045982:
                        if (str.equals("call")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1048378748:
                        if (str.equals("getLastTestProximityUpdateCallbackCode")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1193447472:
                        if (str.equals("clearTestableAttentionService")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        return cmdResolveAttentionServiceComponent();
                    case 1:
                        java.lang.String nextArgRequired = getNextArgRequired();
                        switch (nextArgRequired.hashCode()) {
                            case -1571871954:
                                if (nextArgRequired.equals("onStartProximityUpdates")) {
                                    z = 2;
                                    break;
                                }
                                z = -1;
                                break;
                            case 685821932:
                                if (nextArgRequired.equals("onStopProximityUpdates")) {
                                    z = 3;
                                    break;
                                }
                                z = -1;
                                break;
                            case 763077136:
                                if (nextArgRequired.equals("cancelCheckAttention")) {
                                    z = true;
                                    break;
                                }
                                z = -1;
                                break;
                            case 1485997302:
                                if (nextArgRequired.equals("checkAttention")) {
                                    break;
                                }
                                z = -1;
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                return cmdCallCheckAttention();
                            case true:
                                return cmdCallCancelAttention();
                            case true:
                                return cmdCallOnStartProximityUpdates();
                            case true:
                                return cmdCallOnStopProximityUpdates();
                            default:
                                throw new java.lang.IllegalArgumentException("Invalid argument");
                        }
                    case 2:
                        return cmdSetTestableAttentionService(getNextArgRequired());
                    case 3:
                        return cmdClearTestableAttentionService();
                    case 4:
                        return cmdGetLastTestCallbackCode();
                    case 5:
                        return cmdGetLastTestProximityUpdateCallbackCode();
                    default:
                        return handleDefaultCommands(str);
                }
            } catch (java.lang.IllegalArgumentException e) {
                errPrintWriter.println("Error: " + e.getMessage());
                return -1;
            }
        }

        private int cmdSetTestableAttentionService(java.lang.String str) {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            if (android.text.TextUtils.isEmpty(str)) {
                outPrintWriter.println("false");
                return 0;
            }
            com.android.server.attention.AttentionManagerService.sTestAttentionServicePackage = str;
            resetStates();
            outPrintWriter.println(com.android.server.attention.AttentionManagerService.this.mComponentName != null ? "true" : "false");
            return 0;
        }

        private int cmdClearTestableAttentionService() {
            com.android.server.attention.AttentionManagerService.sTestAttentionServicePackage = "";
            this.mTestableAttentionCallback.reset();
            this.mTestableProximityUpdateCallback.reset();
            resetStates();
            return 0;
        }

        private int cmdCallCheckAttention() {
            getOutPrintWriter().println(com.android.server.attention.AttentionManagerService.this.checkAttention(2000L, this.mTestableAttentionCallback) ? "true" : "false");
            return 0;
        }

        private int cmdCallCancelAttention() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            com.android.server.attention.AttentionManagerService.this.cancelAttentionCheck(this.mTestableAttentionCallback);
            outPrintWriter.println("true");
            return 0;
        }

        private int cmdCallOnStartProximityUpdates() {
            getOutPrintWriter().println(com.android.server.attention.AttentionManagerService.this.onStartProximityUpdates(this.mTestableProximityUpdateCallback) ? "true" : "false");
            return 0;
        }

        private int cmdCallOnStopProximityUpdates() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            com.android.server.attention.AttentionManagerService.this.onStopProximityUpdates(this.mTestableProximityUpdateCallback);
            outPrintWriter.println("true");
            return 0;
        }

        private int cmdResolveAttentionServiceComponent() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            android.content.ComponentName resolveAttentionService = com.android.server.attention.AttentionManagerService.resolveAttentionService(com.android.server.attention.AttentionManagerService.this.mContext);
            outPrintWriter.println(resolveAttentionService != null ? resolveAttentionService.flattenToShortString() : "");
            return 0;
        }

        private int cmdGetLastTestCallbackCode() {
            getOutPrintWriter().println(this.mTestableAttentionCallback.getLastCallbackCode());
            return 0;
        }

        private int cmdGetLastTestProximityUpdateCallbackCode() {
            getOutPrintWriter().println(this.mTestableProximityUpdateCallback.getLastCallbackCode());
            return 0;
        }

        private void resetStates() {
            synchronized (com.android.server.attention.AttentionManagerService.this.mLock) {
                com.android.server.attention.AttentionManagerService.this.mCurrentProximityUpdate = null;
                com.android.server.attention.AttentionManagerService.this.cancelAndUnbindLocked();
            }
            com.android.server.attention.AttentionManagerService.this.mComponentName = com.android.server.attention.AttentionManagerService.resolveAttentionService(com.android.server.attention.AttentionManagerService.this.mContext);
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Attention commands: ");
            outPrintWriter.println("  setTestableAttentionService <service_package>: Bind to a custom implementation of attention service");
            outPrintWriter.println("  ---<service_package>:");
            outPrintWriter.println("       := Package containing the Attention Service implementation to bind to");
            outPrintWriter.println("  ---returns:");
            outPrintWriter.println("       := true, if was bound successfully");
            outPrintWriter.println("       := false, if was not bound successfully");
            outPrintWriter.println("  clearTestableAttentionService: Undo custom bindings. Revert to previous behavior");
            outPrintWriter.println("  getAttentionServiceComponent: Get the current service component string");
            outPrintWriter.println("  ---returns:");
            outPrintWriter.println("       := If valid, the component string (in shorten form) for the currently bound service.");
            outPrintWriter.println("       := else, empty string");
            outPrintWriter.println("  call checkAttention: Calls check attention");
            outPrintWriter.println("  ---returns:");
            outPrintWriter.println("       := true, if the call was successfully dispatched to the service implementation. (to see the result, call getLastTestCallbackCode)");
            outPrintWriter.println("       := false, otherwise");
            outPrintWriter.println("  call cancelCheckAttention: Cancels check attention");
            outPrintWriter.println("  call onStartProximityUpdates: Calls onStartProximityUpdates");
            outPrintWriter.println("  ---returns:");
            outPrintWriter.println("       := true, if the request was successfully dispatched to the service implementation. (to see the result, call getLastTestProximityUpdateCallbackCode)");
            outPrintWriter.println("       := false, otherwise");
            outPrintWriter.println("  call onStopProximityUpdates: Cancels proximity updates");
            outPrintWriter.println("  getLastTestCallbackCode");
            outPrintWriter.println("  ---returns:");
            outPrintWriter.println("       := An integer, representing the last callback code received from the bounded implementation. If none, it will return -1");
            outPrintWriter.println("  getLastTestProximityUpdateCallbackCode");
            outPrintWriter.println("  ---returns:");
            outPrintWriter.println("       := A double, representing the last proximity value received from the bounded implementation. If none, it will return -1.0");
        }
    }

    private final class BinderService extends android.os.Binder {
        com.android.server.attention.AttentionManagerService.AttentionManagerServiceShellCommand mAttentionManagerServiceShellCommand;

        private BinderService() {
            this.mAttentionManagerServiceShellCommand = new com.android.server.attention.AttentionManagerService.AttentionManagerServiceShellCommand();
        }

        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            this.mAttentionManagerServiceShellCommand.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.attention.AttentionManagerService.this.mContext, com.android.server.attention.AttentionManagerService.LOG_TAG, printWriter)) {
                return;
            }
            com.android.server.attention.AttentionManagerService.this.dumpInternal(new com.android.internal.util.IndentingPrintWriter(printWriter, "  "));
        }
    }
}
