package android.net.metrics;

/* loaded from: classes2.dex */
public class DefaultNetworkEvent {
    public final long creationTimeMs;
    public long durationMs;
    public int finalScore;
    public int initialScore;
    public boolean ipv4;
    public boolean ipv6;
    public int netId = 0;
    public int previousTransports;
    public int transports;
    public long validatedMs;

    public DefaultNetworkEvent(long j) {
        this.creationTimeMs = j;
    }

    public void updateDuration(long j) {
        this.durationMs = j - this.creationTimeMs;
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "DefaultNetworkEvent(", android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        stringJoiner.add("netId=" + this.netId);
        stringJoiner.add("transports=" + java.util.BitSet.valueOf(new long[]{this.transports}));
        stringJoiner.add("ip=" + ipSupport());
        if (this.initialScore > 0) {
            stringJoiner.add("initial_score=" + this.initialScore);
        }
        if (this.finalScore > 0) {
            stringJoiner.add("final_score=" + this.finalScore);
        }
        stringJoiner.add(java.lang.String.format("duration=%.0fs", java.lang.Double.valueOf(this.durationMs / 1000.0d)));
        stringJoiner.add(java.lang.String.format("validation=%04.1f%%", java.lang.Double.valueOf((this.validatedMs * 100.0d) / this.durationMs)));
        return stringJoiner.toString();
    }

    private java.lang.String ipSupport() {
        if (this.ipv4 && this.ipv6) {
            return "IPv4v6";
        }
        if (this.ipv6) {
            return "IPv6";
        }
        if (this.ipv4) {
            return "IPv4";
        }
        return android.security.keystore.KeyProperties.DIGEST_NONE;
    }
}
