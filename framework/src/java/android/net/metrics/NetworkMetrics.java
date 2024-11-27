package android.net.metrics;

/* loaded from: classes2.dex */
public class NetworkMetrics {
    private static final int CONNECT_LATENCY_MAXIMUM_RECORDS = 20000;
    private static final int INITIAL_DNS_BATCH_SIZE = 100;
    public final android.net.metrics.ConnectStats connectMetrics;
    public final android.net.metrics.DnsEvent dnsMetrics;
    public final int netId;
    public android.net.metrics.NetworkMetrics.Summary pendingSummary;
    public final android.net.metrics.NetworkMetrics.Summary summary;
    public final long transports;

    public NetworkMetrics(int i, long j, com.android.internal.util.TokenBucket tokenBucket) {
        this.netId = i;
        this.transports = j;
        this.connectMetrics = new android.net.metrics.ConnectStats(i, j, tokenBucket, 20000);
        this.dnsMetrics = new android.net.metrics.DnsEvent(i, j, 100);
        this.summary = new android.net.metrics.NetworkMetrics.Summary(i, j);
    }

    public android.net.metrics.NetworkMetrics.Summary getPendingStats() {
        android.net.metrics.NetworkMetrics.Summary summary = this.pendingSummary;
        this.pendingSummary = null;
        if (summary != null) {
            this.summary.merge(summary);
        }
        return summary;
    }

    public void addDnsResult(int i, int i2, int i3) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new android.net.metrics.NetworkMetrics.Summary(this.netId, this.transports);
        }
        boolean addResult = this.dnsMetrics.addResult((byte) i, (byte) i2, i3);
        this.pendingSummary.dnsLatencies.count(i3);
        this.pendingSummary.dnsErrorRate.count(addResult ? 0.0d : 1.0d);
    }

    public void addConnectResult(int i, int i2, java.lang.String str) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new android.net.metrics.NetworkMetrics.Summary(this.netId, this.transports);
        }
        this.pendingSummary.connectErrorRate.count(this.connectMetrics.addEvent(i, i2, str) ? 0.0d : 1.0d);
        if (android.net.metrics.ConnectStats.isNonBlocking(i)) {
            this.pendingSummary.connectLatencies.count(i2);
        }
    }

    public void addTcpStatsResult(int i, int i2, int i3, int i4) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new android.net.metrics.NetworkMetrics.Summary(this.netId, this.transports);
        }
        this.pendingSummary.tcpLossRate.count(i2, i);
        this.pendingSummary.roundTripTimeUs.count(i3);
        this.pendingSummary.sentAckTimeDiffenceMs.count(i4);
    }

    public static class Summary {
        public final int netId;
        public final long transports;
        public final android.net.metrics.NetworkMetrics.Metrics dnsLatencies = new android.net.metrics.NetworkMetrics.Metrics();
        public final android.net.metrics.NetworkMetrics.Metrics dnsErrorRate = new android.net.metrics.NetworkMetrics.Metrics();
        public final android.net.metrics.NetworkMetrics.Metrics connectLatencies = new android.net.metrics.NetworkMetrics.Metrics();
        public final android.net.metrics.NetworkMetrics.Metrics connectErrorRate = new android.net.metrics.NetworkMetrics.Metrics();
        public final android.net.metrics.NetworkMetrics.Metrics tcpLossRate = new android.net.metrics.NetworkMetrics.Metrics();
        public final android.net.metrics.NetworkMetrics.Metrics roundTripTimeUs = new android.net.metrics.NetworkMetrics.Metrics();
        public final android.net.metrics.NetworkMetrics.Metrics sentAckTimeDiffenceMs = new android.net.metrics.NetworkMetrics.Metrics();

        public Summary(int i, long j) {
            this.netId = i;
            this.transports = j;
        }

        void merge(android.net.metrics.NetworkMetrics.Summary summary) {
            this.dnsLatencies.merge(summary.dnsLatencies);
            this.dnsErrorRate.merge(summary.dnsErrorRate);
            this.connectLatencies.merge(summary.connectLatencies);
            this.connectErrorRate.merge(summary.connectErrorRate);
            this.tcpLossRate.merge(summary.tcpLossRate);
        }

        public java.lang.String toString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
            stringJoiner.add("netId=" + this.netId);
            stringJoiner.add("transports=" + java.util.BitSet.valueOf(new long[]{this.transports}));
            stringJoiner.add(java.lang.String.format("dns avg=%dms max=%dms err=%.1f%% tot=%d", java.lang.Integer.valueOf((int) this.dnsLatencies.average()), java.lang.Integer.valueOf((int) this.dnsLatencies.max), java.lang.Double.valueOf(this.dnsErrorRate.average() * 100.0d), java.lang.Integer.valueOf(this.dnsErrorRate.count)));
            stringJoiner.add(java.lang.String.format("connect avg=%dms max=%dms err=%.1f%% tot=%d", java.lang.Integer.valueOf((int) this.connectLatencies.average()), java.lang.Integer.valueOf((int) this.connectLatencies.max), java.lang.Double.valueOf(this.connectErrorRate.average() * 100.0d), java.lang.Integer.valueOf(this.connectErrorRate.count)));
            stringJoiner.add(java.lang.String.format("tcp avg_loss=%.1f%% total_sent=%d total_lost=%d", java.lang.Double.valueOf(this.tcpLossRate.average() * 100.0d), java.lang.Integer.valueOf(this.tcpLossRate.count), java.lang.Integer.valueOf((int) this.tcpLossRate.sum)));
            stringJoiner.add(java.lang.String.format("tcp rtt=%dms", java.lang.Integer.valueOf((int) (this.roundTripTimeUs.average() / 1000.0d))));
            stringJoiner.add(java.lang.String.format("tcp sent-ack_diff=%dms", java.lang.Integer.valueOf((int) this.sentAckTimeDiffenceMs.average())));
            return stringJoiner.toString();
        }
    }

    static class Metrics {
        public int count;
        public double max = Double.MIN_VALUE;
        public double sum;

        Metrics() {
        }

        void merge(android.net.metrics.NetworkMetrics.Metrics metrics) {
            this.count += metrics.count;
            this.sum += metrics.sum;
            this.max = java.lang.Math.max(this.max, metrics.max);
        }

        void count(double d) {
            count(d, 1);
        }

        void count(double d, int i) {
            this.count += i;
            this.sum += d;
            this.max = java.lang.Math.max(this.max, d);
        }

        double average() {
            double d = this.sum / this.count;
            if (java.lang.Double.isNaN(d)) {
                return 0.0d;
            }
            return d;
        }
    }
}
