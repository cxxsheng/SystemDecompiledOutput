package android.security.net.config;

/* loaded from: classes3.dex */
public final class Domain {
    public final java.lang.String hostname;
    public final boolean subdomainsIncluded;

    public Domain(java.lang.String str, boolean z) {
        if (str == null) {
            throw new java.lang.NullPointerException("Hostname must not be null");
        }
        this.hostname = str.toLowerCase(java.util.Locale.US);
        this.subdomainsIncluded = z;
    }

    public int hashCode() {
        return this.hostname.hashCode() ^ (this.subdomainsIncluded ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : com.android.internal.logging.nano.MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.security.net.config.Domain)) {
            return false;
        }
        android.security.net.config.Domain domain = (android.security.net.config.Domain) obj;
        return domain.subdomainsIncluded == this.subdomainsIncluded && domain.hostname.equals(this.hostname);
    }
}
