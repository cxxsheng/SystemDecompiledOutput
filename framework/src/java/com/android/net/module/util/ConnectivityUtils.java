package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class ConnectivityUtils {
    private ConnectivityUtils() {
    }

    public static java.lang.String addressAndPortToString(java.net.InetAddress inetAddress, int i) {
        return java.lang.String.format(inetAddress instanceof java.net.Inet6Address ? "[%s]:%d" : "%s:%d", inetAddress.getHostAddress(), java.lang.Integer.valueOf(i));
    }

    public static boolean isIPv6ULA(java.net.InetAddress inetAddress) {
        return (inetAddress instanceof java.net.Inet6Address) && (inetAddress.getAddress()[0] & com.android.internal.midi.MidiConstants.STATUS_ACTIVE_SENSING) == 252;
    }

    public static int saturatedCast(long j) {
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (j < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j;
    }
}
