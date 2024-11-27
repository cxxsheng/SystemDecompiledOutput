package android.net.metrics;

/* loaded from: classes2.dex */
public class WakeupEvent {
    public android.net.MacAddress dstHwAddr;
    public java.lang.String dstIp;
    public int dstPort;
    public int ethertype;
    public java.lang.String iface;
    public int ipNextHeader;
    public java.lang.String srcIp;
    public int srcPort;
    public long timestampMs;
    public int uid;

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "WakeupEvent(", android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        stringJoiner.add(java.lang.String.format("%tT.%tL", java.lang.Long.valueOf(this.timestampMs), java.lang.Long.valueOf(this.timestampMs)));
        stringJoiner.add(this.iface);
        stringJoiner.add("uid: " + java.lang.Integer.toString(this.uid));
        stringJoiner.add("eth=0x" + java.lang.Integer.toHexString(this.ethertype));
        stringJoiner.add("srcMac=" + this.dstHwAddr);
        if (this.ipNextHeader > 0) {
            stringJoiner.add("ipNxtHdr=" + this.ipNextHeader);
            stringJoiner.add("srcIp=" + this.srcIp);
            stringJoiner.add("dstIp=" + this.dstIp);
            if (this.srcPort > -1) {
                stringJoiner.add("srcPort=" + this.srcPort);
            }
            if (this.dstPort > -1) {
                stringJoiner.add("dstPort=" + this.dstPort);
            }
        }
        return stringJoiner.toString();
    }
}
