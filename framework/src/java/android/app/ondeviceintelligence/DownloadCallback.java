package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public interface DownloadCallback {
    public static final int DOWNLOAD_FAILURE_STATUS_DOWNLOADING = 3;
    public static final int DOWNLOAD_FAILURE_STATUS_NETWORK_FAILURE = 2;
    public static final int DOWNLOAD_FAILURE_STATUS_NOT_ENOUGH_DISK_SPACE = 1;
    public static final int DOWNLOAD_FAILURE_STATUS_UNAVAILABLE = 4;
    public static final int DOWNLOAD_FAILURE_STATUS_UNKNOWN = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DownloadFailureStatus {
    }

    void onDownloadCompleted(android.os.PersistableBundle persistableBundle);

    void onDownloadFailed(int i, java.lang.String str, android.os.PersistableBundle persistableBundle);

    default void onDownloadStarted(long j) {
    }

    default void onDownloadProgress(long j) {
    }
}
