package android.os.incremental;

/* loaded from: classes3.dex */
public class IncrementalMetrics {
    private final android.os.PersistableBundle mData;

    public IncrementalMetrics(android.os.PersistableBundle persistableBundle) {
        this.mData = persistableBundle;
    }

    public long getMillisSinceOldestPendingRead() {
        return this.mData.getLong(android.os.incremental.IIncrementalService.METRICS_MILLIS_SINCE_OLDEST_PENDING_READ, -1L);
    }

    public boolean getReadLogsEnabled() {
        return this.mData.getBoolean(android.os.incremental.IIncrementalService.METRICS_READ_LOGS_ENABLED, false);
    }

    public int getStorageHealthStatusCode() {
        return this.mData.getInt(android.os.incremental.IIncrementalService.METRICS_STORAGE_HEALTH_STATUS_CODE, -1);
    }

    public int getDataLoaderStatusCode() {
        return this.mData.getInt(android.os.incremental.IIncrementalService.METRICS_DATA_LOADER_STATUS_CODE, -1);
    }

    public long getMillisSinceLastDataLoaderBind() {
        return this.mData.getLong(android.os.incremental.IIncrementalService.METRICS_MILLIS_SINCE_LAST_DATA_LOADER_BIND, -1L);
    }

    public long getDataLoaderBindDelayMillis() {
        return this.mData.getLong(android.os.incremental.IIncrementalService.METRICS_DATA_LOADER_BIND_DELAY_MILLIS, -1L);
    }

    public int getTotalDelayedReads() {
        return this.mData.getInt(android.os.incremental.IIncrementalService.METRICS_TOTAL_DELAYED_READS, -1);
    }

    public int getTotalFailedReads() {
        return this.mData.getInt(android.os.incremental.IIncrementalService.METRICS_TOTAL_FAILED_READS, -1);
    }

    public long getTotalDelayedReadsDurationMillis() {
        return this.mData.getLong(android.os.incremental.IIncrementalService.METRICS_TOTAL_DELAYED_READS_MILLIS, -1L);
    }

    public int getLastReadErrorUid() {
        return this.mData.getInt(android.os.incremental.IIncrementalService.METRICS_LAST_READ_ERROR_UID, -1);
    }

    public long getMillisSinceLastReadError() {
        return this.mData.getLong(android.os.incremental.IIncrementalService.METRICS_MILLIS_SINCE_LAST_READ_ERROR, -1L);
    }

    public int getLastReadErrorNumber() {
        return this.mData.getInt(android.os.incremental.IIncrementalService.METRICS_LAST_READ_ERROR_NUMBER, -1);
    }
}
