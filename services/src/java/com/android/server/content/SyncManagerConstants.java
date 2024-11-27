package com.android.server.content;

/* loaded from: classes.dex */
public class SyncManagerConstants extends android.database.ContentObserver {
    private static final int DEF_EXEMPTION_TEMP_ALLOWLIST_DURATION_IN_SECONDS = 600;
    private static final int DEF_INITIAL_SYNC_RETRY_TIME_IN_SECONDS = 30;
    private static final int DEF_MAX_RETRIES_WITH_APP_STANDBY_EXEMPTION = 5;
    private static final int DEF_MAX_SYNC_RETRY_TIME_IN_SECONDS = 3600;
    private static final float DEF_RETRY_TIME_INCREASE_FACTOR = 2.0f;
    private static final java.lang.String KEY_EXEMPTION_TEMP_ALLOWLIST_DURATION_IN_SECONDS = "exemption_temp_whitelist_duration_in_seconds";
    private static final java.lang.String KEY_INITIAL_SYNC_RETRY_TIME_IN_SECONDS = "initial_sync_retry_time_in_seconds";
    private static final java.lang.String KEY_MAX_RETRIES_WITH_APP_STANDBY_EXEMPTION = "max_retries_with_app_standby_exemption";
    private static final java.lang.String KEY_MAX_SYNC_RETRY_TIME_IN_SECONDS = "max_sync_retry_time_in_seconds";
    private static final java.lang.String KEY_RETRY_TIME_INCREASE_FACTOR = "retry_time_increase_factor";
    private static final java.lang.String TAG = "SyncManagerConfig";
    private final android.content.Context mContext;
    private int mInitialSyncRetryTimeInSeconds;
    private int mKeyExemptionTempWhitelistDurationInSeconds;
    private final java.lang.Object mLock;
    private int mMaxRetriesWithAppStandbyExemption;
    private int mMaxSyncRetryTimeInSeconds;
    private float mRetryTimeIncreaseFactor;

    protected SyncManagerConstants(android.content.Context context) {
        super(null);
        this.mLock = new java.lang.Object();
        this.mInitialSyncRetryTimeInSeconds = 30;
        this.mRetryTimeIncreaseFactor = DEF_RETRY_TIME_INCREASE_FACTOR;
        this.mMaxSyncRetryTimeInSeconds = DEF_MAX_SYNC_RETRY_TIME_IN_SECONDS;
        this.mMaxRetriesWithAppStandbyExemption = 5;
        this.mKeyExemptionTempWhitelistDurationInSeconds = 600;
        this.mContext = context;
    }

    public void start() {
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.content.SyncManagerConstants$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.content.SyncManagerConstants.this.lambda$start$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0() {
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("sync_manager_constants"), false, this);
        refresh();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        refresh();
    }

    private void refresh() {
        synchronized (this.mLock) {
            java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "sync_manager_constants");
            android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
            try {
                keyValueListParser.setString(string);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.wtf(TAG, "Bad constants: " + string);
            }
            this.mInitialSyncRetryTimeInSeconds = keyValueListParser.getInt(KEY_INITIAL_SYNC_RETRY_TIME_IN_SECONDS, 30);
            this.mMaxSyncRetryTimeInSeconds = keyValueListParser.getInt(KEY_MAX_SYNC_RETRY_TIME_IN_SECONDS, DEF_MAX_SYNC_RETRY_TIME_IN_SECONDS);
            this.mRetryTimeIncreaseFactor = keyValueListParser.getFloat(KEY_RETRY_TIME_INCREASE_FACTOR, DEF_RETRY_TIME_INCREASE_FACTOR);
            this.mMaxRetriesWithAppStandbyExemption = keyValueListParser.getInt(KEY_MAX_RETRIES_WITH_APP_STANDBY_EXEMPTION, 5);
            this.mKeyExemptionTempWhitelistDurationInSeconds = keyValueListParser.getInt(KEY_EXEMPTION_TEMP_ALLOWLIST_DURATION_IN_SECONDS, 600);
        }
    }

    public int getInitialSyncRetryTimeInSeconds() {
        int i;
        synchronized (this.mLock) {
            i = this.mInitialSyncRetryTimeInSeconds;
        }
        return i;
    }

    public float getRetryTimeIncreaseFactor() {
        float f;
        synchronized (this.mLock) {
            f = this.mRetryTimeIncreaseFactor;
        }
        return f;
    }

    public int getMaxSyncRetryTimeInSeconds() {
        int i;
        synchronized (this.mLock) {
            i = this.mMaxSyncRetryTimeInSeconds;
        }
        return i;
    }

    public int getMaxRetriesWithAppStandbyExemption() {
        int i;
        synchronized (this.mLock) {
            i = this.mMaxRetriesWithAppStandbyExemption;
        }
        return i;
    }

    public int getKeyExemptionTempWhitelistDurationInSeconds() {
        int i;
        synchronized (this.mLock) {
            i = this.mKeyExemptionTempWhitelistDurationInSeconds;
        }
        return i;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mLock) {
            printWriter.print(str);
            printWriter.println("SyncManager Config:");
            printWriter.print(str);
            printWriter.print("  mInitialSyncRetryTimeInSeconds=");
            printWriter.println(this.mInitialSyncRetryTimeInSeconds);
            printWriter.print(str);
            printWriter.print("  mRetryTimeIncreaseFactor=");
            printWriter.println(this.mRetryTimeIncreaseFactor);
            printWriter.print(str);
            printWriter.print("  mMaxSyncRetryTimeInSeconds=");
            printWriter.println(this.mMaxSyncRetryTimeInSeconds);
            printWriter.print(str);
            printWriter.print("  mMaxRetriesWithAppStandbyExemption=");
            printWriter.println(this.mMaxRetriesWithAppStandbyExemption);
            printWriter.print(str);
            printWriter.print("  mKeyExemptionTempWhitelistDurationInSeconds=");
            printWriter.println(this.mKeyExemptionTempWhitelistDurationInSeconds);
        }
    }
}
