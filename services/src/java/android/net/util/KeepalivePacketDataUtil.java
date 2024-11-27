package android.net.util;

/* loaded from: classes.dex */
public final class KeepalivePacketDataUtil {
    private static final int IPV4_HEADER_LENGTH = 20;
    private static final int IPV6_HEADER_LENGTH = 40;
    private static final java.lang.String TAG = android.net.util.KeepalivePacketDataUtil.class.getSimpleName();

    @android.annotation.NonNull
    public static android.net.NattKeepalivePacketDataParcelable toStableParcelable(@android.annotation.NonNull android.net.NattKeepalivePacketData nattKeepalivePacketData) {
        android.net.NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable = new android.net.NattKeepalivePacketDataParcelable();
        java.net.InetAddress srcAddress = nattKeepalivePacketData.getSrcAddress();
        java.net.InetAddress dstAddress = nattKeepalivePacketData.getDstAddress();
        nattKeepalivePacketDataParcelable.srcAddress = srcAddress.getAddress();
        nattKeepalivePacketDataParcelable.srcPort = nattKeepalivePacketData.getSrcPort();
        nattKeepalivePacketDataParcelable.dstAddress = dstAddress.getAddress();
        nattKeepalivePacketDataParcelable.dstPort = nattKeepalivePacketData.getDstPort();
        return nattKeepalivePacketDataParcelable;
    }

    @android.annotation.NonNull
    public static android.net.TcpKeepalivePacketDataParcelable toStableParcelable(@android.annotation.NonNull android.net.TcpKeepalivePacketData tcpKeepalivePacketData) {
        android.net.TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable = new android.net.TcpKeepalivePacketDataParcelable();
        java.net.InetAddress srcAddress = tcpKeepalivePacketData.getSrcAddress();
        java.net.InetAddress dstAddress = tcpKeepalivePacketData.getDstAddress();
        tcpKeepalivePacketDataParcelable.srcAddress = srcAddress.getAddress();
        tcpKeepalivePacketDataParcelable.srcPort = tcpKeepalivePacketData.getSrcPort();
        tcpKeepalivePacketDataParcelable.dstAddress = dstAddress.getAddress();
        tcpKeepalivePacketDataParcelable.dstPort = tcpKeepalivePacketData.getDstPort();
        tcpKeepalivePacketDataParcelable.seq = tcpKeepalivePacketData.getTcpSeq();
        tcpKeepalivePacketDataParcelable.ack = tcpKeepalivePacketData.getTcpAck();
        tcpKeepalivePacketDataParcelable.rcvWnd = tcpKeepalivePacketData.getTcpWindow();
        tcpKeepalivePacketDataParcelable.rcvWndScale = tcpKeepalivePacketData.getTcpWindowScale();
        tcpKeepalivePacketDataParcelable.tos = tcpKeepalivePacketData.getIpTos();
        tcpKeepalivePacketDataParcelable.ttl = tcpKeepalivePacketData.getIpTtl();
        return tcpKeepalivePacketDataParcelable;
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    public static android.net.TcpKeepalivePacketDataParcelable parseTcpKeepalivePacketData(@android.annotation.Nullable android.net.KeepalivePacketData keepalivePacketData) {
        if (keepalivePacketData == null) {
            return null;
        }
        android.util.Log.wtf(TAG, "parseTcpKeepalivePacketData should not be used after R, use TcpKeepalivePacketData instead.");
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(keepalivePacketData.getPacket());
        wrap.order(java.nio.ByteOrder.BIG_ENDIAN);
        try {
            int i = wrap.getInt(24);
            int i2 = wrap.getInt(28);
            short s = wrap.getShort(34);
            byte b = wrap.get(1);
            byte b2 = wrap.get(8);
            android.net.TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable = new android.net.TcpKeepalivePacketDataParcelable();
            tcpKeepalivePacketDataParcelable.srcAddress = keepalivePacketData.getSrcAddress().getAddress();
            tcpKeepalivePacketDataParcelable.srcPort = keepalivePacketData.getSrcPort();
            tcpKeepalivePacketDataParcelable.dstAddress = keepalivePacketData.getDstAddress().getAddress();
            tcpKeepalivePacketDataParcelable.dstPort = keepalivePacketData.getDstPort();
            tcpKeepalivePacketDataParcelable.seq = i;
            tcpKeepalivePacketDataParcelable.ack = i2;
            tcpKeepalivePacketDataParcelable.rcvWnd = s;
            tcpKeepalivePacketDataParcelable.rcvWndScale = 0;
            tcpKeepalivePacketDataParcelable.tos = b;
            tcpKeepalivePacketDataParcelable.ttl = b2;
            return tcpKeepalivePacketDataParcelable;
        } catch (java.lang.IndexOutOfBoundsException e) {
            return null;
        }
    }
}
