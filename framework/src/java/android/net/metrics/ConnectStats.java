package android.net.metrics;

/* loaded from: classes2.dex */
public class ConnectStats {
    private static final int EALREADY = android.system.OsConstants.EALREADY;
    private static final int EINPROGRESS = android.system.OsConstants.EINPROGRESS;
    public final com.android.internal.util.TokenBucket mLatencyTb;
    public final int mMaxLatencyRecords;
    public final int netId;
    public final long transports;
    public final android.util.SparseIntArray errnos = new android.util.SparseIntArray();
    public final android.util.IntArray latencies = new android.util.IntArray();
    public int eventCount = 0;
    public int connectCount = 0;
    public int connectBlockingCount = 0;
    public int ipv6ConnectCount = 0;

    public ConnectStats(int i, long j, com.android.internal.util.TokenBucket tokenBucket, int i2) {
        this.netId = i;
        this.transports = j;
        this.mLatencyTb = tokenBucket;
        this.mMaxLatencyRecords = i2;
    }

    boolean addEvent(int i, int i2, java.lang.String str) {
        this.eventCount++;
        if (isSuccess(i)) {
            countConnect(i, str);
            countLatency(i, i2);
            return true;
        }
        countError(i);
        return false;
    }

    private void countConnect(int i, java.lang.String str) {
        this.connectCount++;
        if (!isNonBlocking(i)) {
            this.connectBlockingCount++;
        }
        if (isIPv6(str)) {
            this.ipv6ConnectCount++;
        }
    }

    private void countLatency(int i, int i2) {
        if (isNonBlocking(i) || !this.mLatencyTb.get() || this.latencies.size() >= this.mMaxLatencyRecords) {
            return;
        }
        this.latencies.add(i2);
    }

    private void countError(int i) {
        this.errnos.put(i, this.errnos.get(i, 0) + 1);
    }

    private static boolean isSuccess(int i) {
        return i == 0 || isNonBlocking(i);
    }

    static boolean isNonBlocking(int i) {
        return i == EINPROGRESS || i == EALREADY;
    }

    private static boolean isIPv6(java.lang.String str) {
        return str.contains(":");
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("ConnectStats(").append("netId=").append(this.netId).append(", transports=").append(java.util.BitSet.valueOf(new long[]{this.transports})).append(", ");
        append.append(java.lang.String.format("%d events, ", java.lang.Integer.valueOf(this.eventCount)));
        append.append(java.lang.String.format("%d success, ", java.lang.Integer.valueOf(this.connectCount)));
        append.append(java.lang.String.format("%d blocking, ", java.lang.Integer.valueOf(this.connectBlockingCount)));
        append.append(java.lang.String.format("%d IPv6 dst", java.lang.Integer.valueOf(this.ipv6ConnectCount)));
        for (int i = 0; i < this.errnos.size(); i++) {
            append.append(java.lang.String.format(", %s: %d", android.system.OsConstants.errnoName(this.errnos.keyAt(i)), java.lang.Integer.valueOf(this.errnos.valueAt(i))));
        }
        return append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END).toString();
    }
}
