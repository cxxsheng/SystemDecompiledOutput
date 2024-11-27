package android.app.slice;

/* loaded from: classes.dex */
public class SliceMetrics {
    private static final java.lang.String TAG = "SliceMetrics";
    private com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();
    private android.metrics.LogMaker mLogMaker = new android.metrics.LogMaker(0);

    public SliceMetrics(android.content.Context context, android.net.Uri uri) {
        this.mLogMaker.addTaggedData(1402, uri.getAuthority());
        this.mLogMaker.addTaggedData(1403, uri.getPath());
    }

    public void logVisible() {
        synchronized (this.mLogMaker) {
            this.mLogMaker.setCategory(1401).setType(1);
            this.mMetricsLogger.write(this.mLogMaker);
        }
    }

    public void logHidden() {
        synchronized (this.mLogMaker) {
            this.mLogMaker.setCategory(1401).setType(2);
            this.mMetricsLogger.write(this.mLogMaker);
        }
    }

    public void logTouch(int i, android.net.Uri uri) {
        synchronized (this.mLogMaker) {
            this.mLogMaker.setCategory(1401).setType(4).addTaggedData(1404, uri.getAuthority()).addTaggedData(1405, uri.getPath());
            this.mMetricsLogger.write(this.mLogMaker);
        }
    }
}
