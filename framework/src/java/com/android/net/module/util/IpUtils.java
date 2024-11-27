package com.android.net.module.util;

/* loaded from: classes5.dex */
public class IpUtils {
    private static int intAbs(short s) {
        return s & 65535;
    }

    public static int checksum(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3) {
        int i4 = i + 65535;
        int position = byteBuffer.position();
        byteBuffer.position(i2);
        java.nio.ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
        byteBuffer.position(position);
        int i5 = (i3 - i2) / 2;
        for (int i6 = 0; i6 < i5; i6++) {
            i4 += intAbs(asShortBuffer.get(i6));
        }
        int i7 = i2 + (i5 * 2);
        if (i3 != i7) {
            short s = byteBuffer.get(i7);
            if (s < 0) {
                s = (short) (s + android.hardware.gnss.V1_0.IGnss.GnssAidingData.DELETE_SVSTEER);
            }
            i4 += s * android.hardware.gnss.V1_0.IGnss.GnssAidingData.DELETE_SVSTEER;
        }
        int i8 = ((i4 >> 16) & 65535) + (i4 & 65535);
        return (((i8 >> 16) & 65535) + (i8 & 65535)) ^ 65535;
    }

    private static int pseudoChecksumIPv4(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3) {
        return i2 + i3 + intAbs(byteBuffer.getShort(i + 12)) + intAbs(byteBuffer.getShort(i + 14)) + intAbs(byteBuffer.getShort(i + 16)) + intAbs(byteBuffer.getShort(i + 18));
    }

    private static int pseudoChecksumIPv6(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3) {
        int i4 = i2 + i3;
        for (int i5 = 8; i5 < 40; i5 += 2) {
            i4 += intAbs(byteBuffer.getShort(i + i5));
        }
        return i4;
    }

    private static byte ipversion(java.nio.ByteBuffer byteBuffer, int i) {
        return (byte) ((byteBuffer.get(i) & (-16)) >> 4);
    }

    public static short ipChecksum(java.nio.ByteBuffer byteBuffer, int i) {
        return (short) checksum(byteBuffer, 0, i, (((byte) (byteBuffer.get(i) & 15)) * 4) + i);
    }

    private static short transportChecksum(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3, int i4) {
        int pseudoChecksumIPv6;
        if (i4 < 0) {
            throw new java.lang.IllegalArgumentException("Transport length < 0: " + i4);
        }
        byte ipversion = ipversion(byteBuffer, i2);
        if (ipversion == 4) {
            pseudoChecksumIPv6 = pseudoChecksumIPv4(byteBuffer, i2, i, i4);
        } else if (ipversion == 6) {
            pseudoChecksumIPv6 = pseudoChecksumIPv6(byteBuffer, i2, i, i4);
        } else {
            throw new java.lang.UnsupportedOperationException("Checksum must be IPv4 or IPv6");
        }
        int checksum = checksum(byteBuffer, pseudoChecksumIPv6, i3, i4 + i3);
        if (i == android.system.OsConstants.IPPROTO_UDP && checksum == 0) {
            checksum = -1;
        }
        return (short) checksum;
    }

    public static short udpChecksum(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        return transportChecksum(byteBuffer, android.system.OsConstants.IPPROTO_UDP, i, i2, intAbs(byteBuffer.getShort(i2 + 4)));
    }

    public static short tcpChecksum(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3) {
        return transportChecksum(byteBuffer, android.system.OsConstants.IPPROTO_TCP, i, i2, i3);
    }

    public static short icmpChecksum(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        return (short) checksum(byteBuffer, 0, i, i2 + i);
    }

    public static short icmpv6Checksum(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3) {
        return transportChecksum(byteBuffer, android.system.OsConstants.IPPROTO_ICMPV6, i, i2, i3);
    }

    public static java.lang.String addressAndPortToString(java.net.InetAddress inetAddress, int i) {
        return java.lang.String.format(inetAddress instanceof java.net.Inet6Address ? "[%s]:%d" : "%s:%d", inetAddress.getHostAddress(), java.lang.Integer.valueOf(i));
    }

    public static boolean isValidUdpOrTcpPort(int i) {
        return i > 0 && i < 65536;
    }
}
