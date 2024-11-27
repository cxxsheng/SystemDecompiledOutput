package android.media.metrics;

/* loaded from: classes2.dex */
public final class LogSessionId {
    public static final android.media.metrics.LogSessionId LOG_SESSION_ID_NONE = new android.media.metrics.LogSessionId("");
    private final java.lang.String mSessionId;

    public LogSessionId(java.lang.String str) {
        this.mSessionId = (java.lang.String) java.util.Objects.requireNonNull(str);
    }

    public java.lang.String getStringId() {
        return this.mSessionId;
    }

    public java.lang.String toString() {
        return this.mSessionId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mSessionId, ((android.media.metrics.LogSessionId) obj).mSessionId);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mSessionId);
    }
}
