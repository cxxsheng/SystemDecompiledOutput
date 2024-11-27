package android.net.metrics;

/* loaded from: classes2.dex */
public class WakeupStats {
    private static final int NO_UID = -1;
    public final java.lang.String iface;
    public final long creationTimeMs = android.os.SystemClock.elapsedRealtime();
    public long totalWakeups = 0;
    public long rootWakeups = 0;
    public long systemWakeups = 0;
    public long nonApplicationWakeups = 0;
    public long applicationWakeups = 0;
    public long noUidWakeups = 0;
    public long durationSec = 0;
    public long l2UnicastCount = 0;
    public long l2MulticastCount = 0;
    public long l2BroadcastCount = 0;
    public final android.util.SparseIntArray ethertypes = new android.util.SparseIntArray();
    public final android.util.SparseIntArray ipNextHeaders = new android.util.SparseIntArray();

    public WakeupStats(java.lang.String str) {
        this.iface = str;
    }

    public void updateDuration() {
        this.durationSec = (android.os.SystemClock.elapsedRealtime() - this.creationTimeMs) / 1000;
    }

    public void countEvent(android.net.metrics.WakeupEvent wakeupEvent) {
        this.totalWakeups++;
        switch (wakeupEvent.uid) {
            case -1:
                this.noUidWakeups++;
                break;
            case 0:
                this.rootWakeups++;
                break;
            case 1000:
                this.systemWakeups++;
                break;
            default:
                if (wakeupEvent.uid >= 10000) {
                    this.applicationWakeups++;
                    break;
                } else {
                    this.nonApplicationWakeups++;
                    break;
                }
        }
        if (wakeupEvent.dstHwAddr != null) {
            switch (wakeupEvent.dstHwAddr.getAddressType()) {
                case 1:
                    this.l2UnicastCount++;
                    break;
                case 2:
                    this.l2MulticastCount++;
                    break;
                case 3:
                    this.l2BroadcastCount++;
                    break;
            }
        }
        increment(this.ethertypes, wakeupEvent.ethertype);
        if (wakeupEvent.ipNextHeader >= 0) {
            increment(this.ipNextHeaders, wakeupEvent.ipNextHeader);
        }
    }

    public java.lang.String toString() {
        updateDuration();
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "WakeupStats(", android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        stringJoiner.add(this.iface);
        stringJoiner.add("" + this.durationSec + android.app.blob.XmlTags.TAG_SESSION);
        stringJoiner.add("total: " + this.totalWakeups);
        stringJoiner.add("root: " + this.rootWakeups);
        stringJoiner.add("system: " + this.systemWakeups);
        stringJoiner.add("apps: " + this.applicationWakeups);
        stringJoiner.add("non-apps: " + this.nonApplicationWakeups);
        stringJoiner.add("no uid: " + this.noUidWakeups);
        stringJoiner.add(java.lang.String.format("l2 unicast/multicast/broadcast: %d/%d/%d", java.lang.Long.valueOf(this.l2UnicastCount), java.lang.Long.valueOf(this.l2MulticastCount), java.lang.Long.valueOf(this.l2BroadcastCount)));
        for (int i = 0; i < this.ethertypes.size(); i++) {
            stringJoiner.add(java.lang.String.format("ethertype 0x%x: %d", java.lang.Integer.valueOf(this.ethertypes.keyAt(i)), java.lang.Integer.valueOf(this.ethertypes.valueAt(i))));
        }
        for (int i2 = 0; i2 < this.ipNextHeaders.size(); i2++) {
            stringJoiner.add(java.lang.String.format("ipNxtHdr %d: %d", java.lang.Integer.valueOf(this.ipNextHeaders.keyAt(i2)), java.lang.Integer.valueOf(this.ipNextHeaders.valueAt(i2))));
        }
        return stringJoiner.toString();
    }

    private static void increment(android.util.SparseIntArray sparseIntArray, int i) {
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
    }
}
