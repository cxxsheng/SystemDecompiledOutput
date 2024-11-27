package com.android.server.backup;

/* loaded from: classes.dex */
public class BackupAgentTimeoutParameters extends android.util.KeyValueSettingObserver {

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS = 300000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_KV_BACKUP_AGENT_TIMEOUT_MILLIS = 30000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS = 3000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_RESTORE_AGENT_FINISHED_TIMEOUT_MILLIS = 30000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_RESTORE_AGENT_TIMEOUT_MILLIS = 60000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_RESTORE_SESSION_TIMEOUT_MILLIS = 60000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_RESTORE_SYSTEM_AGENT_TIMEOUT_MILLIS = 180000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_SHARED_BACKUP_AGENT_TIMEOUT_MILLIS = 1800000;

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING = "backup_agent_timeout_parameters";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_FULL_BACKUP_AGENT_TIMEOUT_MILLIS = "full_backup_agent_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_KV_BACKUP_AGENT_TIMEOUT_MILLIS = "kv_backup_agent_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_QUOTA_EXCEEDED_TIMEOUT_MILLIS = "quota_exceeded_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_RESTORE_AGENT_FINISHED_TIMEOUT_MILLIS = "restore_agent_finished_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_RESTORE_AGENT_TIMEOUT_MILLIS = "restore_agent_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_RESTORE_SESSION_TIMEOUT_MILLIS = "restore_session_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_RESTORE_SYSTEM_AGENT_TIMEOUT_MILLIS = "restore_system_agent_timeout_millis";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SETTING_SHARED_BACKUP_AGENT_TIMEOUT_MILLIS = "shared_backup_agent_timeout_millis";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mFullBackupAgentTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mKvBackupAgentTimeoutMillis;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mQuotaExceededTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mRestoreAgentFinishedTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mRestoreAgentTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mRestoreSessionTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mRestoreSystemAgentTimeoutMillis;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mSharedBackupAgentTimeoutMillis;

    public BackupAgentTimeoutParameters(android.os.Handler handler, android.content.ContentResolver contentResolver) {
        super(handler, contentResolver, android.provider.Settings.Global.getUriFor(SETTING));
        this.mLock = new java.lang.Object();
    }

    public java.lang.String getSettingValue(android.content.ContentResolver contentResolver) {
        return android.provider.Settings.Global.getString(contentResolver, SETTING);
    }

    public void update(android.util.KeyValueListParser keyValueListParser) {
        synchronized (this.mLock) {
            this.mKvBackupAgentTimeoutMillis = keyValueListParser.getLong(SETTING_KV_BACKUP_AGENT_TIMEOUT_MILLIS, 30000L);
            this.mFullBackupAgentTimeoutMillis = keyValueListParser.getLong(SETTING_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
            this.mSharedBackupAgentTimeoutMillis = keyValueListParser.getLong(SETTING_SHARED_BACKUP_AGENT_TIMEOUT_MILLIS, 1800000L);
            this.mRestoreAgentTimeoutMillis = keyValueListParser.getLong(SETTING_RESTORE_AGENT_TIMEOUT_MILLIS, 60000L);
            this.mRestoreSystemAgentTimeoutMillis = keyValueListParser.getLong(SETTING_RESTORE_SYSTEM_AGENT_TIMEOUT_MILLIS, 180000L);
            this.mRestoreAgentFinishedTimeoutMillis = keyValueListParser.getLong(SETTING_RESTORE_AGENT_FINISHED_TIMEOUT_MILLIS, 30000L);
            this.mRestoreSessionTimeoutMillis = keyValueListParser.getLong(SETTING_RESTORE_SESSION_TIMEOUT_MILLIS, 60000L);
            this.mQuotaExceededTimeoutMillis = keyValueListParser.getLong(SETTING_QUOTA_EXCEEDED_TIMEOUT_MILLIS, DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS);
        }
    }

    public long getKvBackupAgentTimeoutMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mKvBackupAgentTimeoutMillis;
        }
        return j;
    }

    public long getFullBackupAgentTimeoutMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mFullBackupAgentTimeoutMillis;
        }
        return j;
    }

    public long getSharedBackupAgentTimeoutMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mSharedBackupAgentTimeoutMillis;
        }
        return j;
    }

    public long getRestoreAgentTimeoutMillis(int i) {
        long j;
        synchronized (this.mLock) {
            try {
                j = android.os.UserHandle.isCore(i) ? this.mRestoreSystemAgentTimeoutMillis : this.mRestoreAgentTimeoutMillis;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return j;
    }

    public long getRestoreSessionTimeoutMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mRestoreSessionTimeoutMillis;
        }
        return j;
    }

    public long getRestoreAgentFinishedTimeoutMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mRestoreAgentFinishedTimeoutMillis;
        }
        return j;
    }

    public long getQuotaExceededTimeoutMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.mQuotaExceededTimeoutMillis;
        }
        return j;
    }
}
