package android.media.metrics;

/* loaded from: classes2.dex */
public final class BundleSession implements java.lang.AutoCloseable {
    public static final java.lang.String KEY_STATSD_ATOM = "bundlesession-statsd-atom";
    private final java.lang.String mId;
    private final android.media.metrics.LogSessionId mLogSessionId;
    private final android.media.metrics.MediaMetricsManager mManager;

    public BundleSession(java.lang.String str, android.media.metrics.MediaMetricsManager mediaMetricsManager) {
        this.mId = str;
        this.mManager = mediaMetricsManager;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mId);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mManager);
        this.mLogSessionId = new android.media.metrics.LogSessionId(this.mId);
    }

    public void reportBundleMetrics(android.os.PersistableBundle persistableBundle) {
        this.mManager.reportBundleMetrics(this.mId, persistableBundle);
    }

    public android.media.metrics.LogSessionId getSessionId() {
        return this.mLogSessionId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mId, ((android.media.metrics.BundleSession) obj).mId);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mManager.releaseSessionId(this.mLogSessionId.getStringId());
    }
}
