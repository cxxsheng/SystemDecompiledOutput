package android.net.metrics;

/* loaded from: classes2.dex */
public final class DnsEvent {
    private static final int SIZE_LIMIT = 20000;
    public int eventCount;
    public byte[] eventTypes;
    public int[] latenciesMs;
    public final int netId;
    public byte[] returnCodes;
    public int successCount;
    public final long transports;

    public DnsEvent(int i, long j, int i2) {
        this.netId = i;
        this.transports = j;
        this.eventTypes = new byte[i2];
        this.returnCodes = new byte[i2];
        this.latenciesMs = new int[i2];
    }

    boolean addResult(byte b, byte b2, int i) {
        boolean z = b2 == 0;
        if (this.eventCount >= 20000) {
            return z;
        }
        if (this.eventCount == this.eventTypes.length) {
            resize((int) (this.eventCount * 1.4d));
        }
        this.eventTypes[this.eventCount] = b;
        this.returnCodes[this.eventCount] = b2;
        this.latenciesMs[this.eventCount] = i;
        this.eventCount++;
        if (z) {
            this.successCount++;
        }
        return z;
    }

    public void resize(int i) {
        this.eventTypes = java.util.Arrays.copyOf(this.eventTypes, i);
        this.returnCodes = java.util.Arrays.copyOf(this.returnCodes, i);
        this.latenciesMs = java.util.Arrays.copyOf(this.latenciesMs, i);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("DnsEvent(").append("netId=").append(this.netId).append(", transports=").append(java.util.BitSet.valueOf(new long[]{this.transports})).append(", ");
        append.append(java.lang.String.format("%d events, ", java.lang.Integer.valueOf(this.eventCount)));
        append.append(java.lang.String.format("%d success)", java.lang.Integer.valueOf(this.successCount)));
        return append.toString();
    }
}
