package com.android.server.power;

/* loaded from: classes2.dex */
public class AttentionDetector {
    private static final boolean DEBUG = false;
    static final long DEFAULT_POST_DIM_CHECK_DURATION_MILLIS = 0;
    static final long DEFAULT_PRE_DIM_CHECK_DURATION_MILLIS = 2000;
    static final java.lang.String KEY_MAX_EXTENSION_MILLIS = "max_extension_millis";
    static final java.lang.String KEY_POST_DIM_CHECK_DURATION_MILLIS = "post_dim_check_duration_millis";
    static final java.lang.String KEY_PRE_DIM_CHECK_DURATION_MILLIS = "pre_dim_check_duration_millis";
    private static final java.lang.String TAG = "AttentionDetector";

    @com.android.internal.annotations.VisibleForTesting
    protected android.attention.AttentionManagerInternal mAttentionManager;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.power.AttentionDetector.AttentionCallbackInternalImpl mCallback;

    @com.android.internal.annotations.VisibleForTesting
    protected android.content.ContentResolver mContentResolver;
    private android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    protected long mDefaultMaximumExtensionMillis;
    private long mEffectivePostDimTimeoutMillis;
    private boolean mIsSettingEnabled;
    private long mLastActedOnNextScreenDimming;
    private long mLastUserActivityTime;
    private final java.lang.Object mLock;
    private long mMaximumExtensionMillis;
    private final java.lang.Runnable mOnUserAttention;

    @com.android.internal.annotations.VisibleForTesting
    protected long mPreDimCheckDurationMillis;
    private long mRequestedPostDimTimeoutMillis;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.wm.WindowManagerInternal mWindowManager;
    private java.util.concurrent.atomic.AtomicLong mConsecutiveTimeoutExtendedCount = new java.util.concurrent.atomic.AtomicLong(0);
    private final java.util.concurrent.atomic.AtomicBoolean mRequested = new java.util.concurrent.atomic.AtomicBoolean(false);

    @com.android.internal.annotations.VisibleForTesting
    protected int mRequestId = 0;
    private int mWakefulness = 1;

    public AttentionDetector(java.lang.Runnable runnable, java.lang.Object obj) {
        this.mOnUserAttention = runnable;
        this.mLock = obj;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateEnabledFromSettings(android.content.Context context) {
        this.mIsSettingEnabled = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "adaptive_sleep", 0, -2) == 1;
    }

    public void systemReady(final android.content.Context context) {
        this.mContext = context;
        updateEnabledFromSettings(context);
        this.mContentResolver = context.getContentResolver();
        this.mAttentionManager = (android.attention.AttentionManagerInternal) com.android.server.LocalServices.getService(android.attention.AttentionManagerInternal.class);
        this.mWindowManager = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mDefaultMaximumExtensionMillis = context.getResources().getInteger(android.R.integer.config_attentionMaximumExtension);
        try {
            android.app.ActivityManager.getService().registerUserSwitchObserver(new com.android.server.power.AttentionDetector.UserSwitchObserver(), TAG);
        } catch (android.os.RemoteException e) {
        }
        context.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("adaptive_sleep"), false, new android.database.ContentObserver(new android.os.Handler(context.getMainLooper())) { // from class: com.android.server.power.AttentionDetector.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.power.AttentionDetector.this.updateEnabledFromSettings(context);
            }
        }, -1);
        readValuesFromDeviceConfig();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("attention_manager_service", context.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.AttentionDetector$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.power.AttentionDetector.this.lambda$systemReady$0(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    public long updateUserActivity(long j, long j2) {
        if (j == this.mLastActedOnNextScreenDimming || !this.mIsSettingEnabled || !isAttentionServiceSupported() || this.mWindowManager.isKeyguardShowingAndNotOccluded()) {
            return j;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long j3 = j - this.mPreDimCheckDurationMillis;
        long j4 = this.mLastUserActivityTime + this.mMaximumExtensionMillis;
        if (uptimeMillis < j3) {
            return j3;
        }
        if (j4 < j3) {
            return j;
        }
        if (this.mRequested.get()) {
            return j3;
        }
        this.mRequested.set(true);
        this.mRequestId++;
        this.mLastActedOnNextScreenDimming = j;
        this.mCallback = new com.android.server.power.AttentionDetector.AttentionCallbackInternalImpl(this.mRequestId);
        this.mEffectivePostDimTimeoutMillis = java.lang.Math.min(this.mRequestedPostDimTimeoutMillis, j2);
        android.util.Slog.v(TAG, "Checking user attention, ID: " + this.mRequestId);
        if (!this.mAttentionManager.checkAttention(this.mPreDimCheckDurationMillis + this.mEffectivePostDimTimeoutMillis, this.mCallback)) {
            this.mRequested.set(false);
        }
        return j3;
    }

    public int onUserActivity(long j, int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                cancelCurrentRequestIfAny();
                this.mLastUserActivityTime = j;
                resetConsecutiveExtensionCount();
                return 1;
            case 4:
                this.mConsecutiveTimeoutExtendedCount.incrementAndGet();
                return 0;
            default:
                return -1;
        }
    }

    public void onWakefulnessChangeStarted(int i) {
        this.mWakefulness = i;
        if (i != 1) {
            cancelCurrentRequestIfAny();
            resetConsecutiveExtensionCount();
        }
    }

    private void cancelCurrentRequestIfAny() {
        if (this.mRequested.get()) {
            this.mAttentionManager.cancelAttentionCheck(this.mCallback);
            this.mRequested.set(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetConsecutiveExtensionCount() {
        long andSet = this.mConsecutiveTimeoutExtendedCount.getAndSet(0L);
        if (andSet > 0) {
            com.android.internal.util.FrameworkStatsLog.write(168, andSet);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isAttentionServiceSupported() {
        return this.mAttentionManager != null && this.mAttentionManager.isAttentionServiceSupported();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("AttentionDetector:");
        printWriter.println(" mIsSettingEnabled=" + this.mIsSettingEnabled);
        printWriter.println(" mMaxExtensionMillis=" + this.mMaximumExtensionMillis);
        printWriter.println(" mPreDimCheckDurationMillis=" + this.mPreDimCheckDurationMillis);
        printWriter.println(" mEffectivePostDimTimeout=" + this.mEffectivePostDimTimeoutMillis);
        printWriter.println(" mLastUserActivityTime(excludingAttention)=" + this.mLastUserActivityTime);
        printWriter.println(" mAttentionServiceSupported=" + isAttentionServiceSupported());
        printWriter.println(" mRequested=" + this.mRequested);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getPreDimCheckDurationMillis() {
        long j = android.provider.DeviceConfig.getLong("attention_manager_service", KEY_PRE_DIM_CHECK_DURATION_MILLIS, DEFAULT_PRE_DIM_CHECK_DURATION_MILLIS);
        if (j < 0 || j > 13000) {
            android.util.Slog.w(TAG, "Bad flag value supplied for: pre_dim_check_duration_millis");
            return DEFAULT_PRE_DIM_CHECK_DURATION_MILLIS;
        }
        return j;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getPostDimCheckDurationMillis() {
        long j = android.provider.DeviceConfig.getLong("attention_manager_service", KEY_POST_DIM_CHECK_DURATION_MILLIS, 0L);
        if (j < 0 || j > com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY) {
            android.util.Slog.w(TAG, "Bad flag value supplied for: post_dim_check_duration_millis");
            return 0L;
        }
        return j;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getMaxExtensionMillis() {
        long j = android.provider.DeviceConfig.getLong("attention_manager_service", KEY_MAX_EXTENSION_MILLIS, this.mDefaultMaximumExtensionMillis);
        if (j < 0 || j > 3600000) {
            android.util.Slog.w(TAG, "Bad flag value supplied for: max_extension_millis");
            return this.mDefaultMaximumExtensionMillis;
        }
        return j;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        char c;
        for (java.lang.String str : set) {
            switch (str.hashCode()) {
                case -2018189628:
                    if (str.equals(KEY_POST_DIM_CHECK_DURATION_MILLIS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -511526975:
                    if (str.equals(KEY_MAX_EXTENSION_MILLIS)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 417901319:
                    if (str.equals(KEY_PRE_DIM_CHECK_DURATION_MILLIS)) {
                        c = 2;
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
                case 2:
                    readValuesFromDeviceConfig();
                    return;
                default:
                    android.util.Slog.i(TAG, "Ignoring change on " + str);
            }
        }
    }

    private void readValuesFromDeviceConfig() {
        this.mMaximumExtensionMillis = getMaxExtensionMillis();
        this.mPreDimCheckDurationMillis = getPreDimCheckDurationMillis();
        this.mRequestedPostDimTimeoutMillis = getPostDimCheckDurationMillis();
        android.util.Slog.i(TAG, "readValuesFromDeviceConfig():\nmMaximumExtensionMillis=" + this.mMaximumExtensionMillis + "\nmPreDimCheckDurationMillis=" + this.mPreDimCheckDurationMillis + "\nmRequestedPostDimTimeoutMillis=" + this.mRequestedPostDimTimeoutMillis);
    }

    @com.android.internal.annotations.VisibleForTesting
    final class AttentionCallbackInternalImpl extends android.attention.AttentionManagerInternal.AttentionCallbackInternal {
        private final int mId;

        AttentionCallbackInternalImpl(int i) {
            this.mId = i;
        }

        public void onSuccess(int i, long j) {
            android.util.Slog.v(com.android.server.power.AttentionDetector.TAG, "onSuccess: " + i + ", ID: " + this.mId);
            if (this.mId == com.android.server.power.AttentionDetector.this.mRequestId && com.android.server.power.AttentionDetector.this.mRequested.getAndSet(false)) {
                synchronized (com.android.server.power.AttentionDetector.this.mLock) {
                    try {
                        if (com.android.server.power.AttentionDetector.this.mWakefulness != 1) {
                            return;
                        }
                        if (i == 1) {
                            com.android.server.power.AttentionDetector.this.mOnUserAttention.run();
                        } else {
                            com.android.server.power.AttentionDetector.this.resetConsecutiveExtensionCount();
                        }
                    } finally {
                    }
                }
            }
        }

        public void onFailure(int i) {
            android.util.Slog.i(com.android.server.power.AttentionDetector.TAG, "Failed to check attention: " + i + ", ID: " + this.mId);
            com.android.server.power.AttentionDetector.this.mRequested.set(false);
        }
    }

    private final class UserSwitchObserver extends android.app.SynchronousUserSwitchObserver {
        private UserSwitchObserver() {
        }

        public void onUserSwitching(int i) throws android.os.RemoteException {
            com.android.server.power.AttentionDetector.this.updateEnabledFromSettings(com.android.server.power.AttentionDetector.this.mContext);
        }
    }
}
