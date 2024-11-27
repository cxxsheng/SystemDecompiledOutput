package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class NetUtils {
    public static boolean addressTypeMatches(java.net.InetAddress inetAddress, java.net.InetAddress inetAddress2) {
        return ((inetAddress instanceof java.net.Inet4Address) && (inetAddress2 instanceof java.net.Inet4Address)) || ((inetAddress instanceof java.net.Inet6Address) && (inetAddress2 instanceof java.net.Inet6Address));
    }

    public static android.net.RouteInfo selectBestRoute(java.util.Collection<android.net.RouteInfo> collection, java.net.InetAddress inetAddress) {
        if (collection == null || inetAddress == null) {
            return null;
        }
        android.net.RouteInfo routeInfo = null;
        for (android.net.RouteInfo routeInfo2 : collection) {
            if (addressTypeMatches(routeInfo2.getDestination().getAddress(), inetAddress) && (routeInfo == null || routeInfo.getDestination().getPrefixLength() < routeInfo2.getDestination().getPrefixLength())) {
                if (routeInfo2.matches(inetAddress)) {
                    routeInfo = routeInfo2;
                }
            }
        }
        if (routeInfo == null || routeInfo.getType() != 1) {
            return null;
        }
        return routeInfo;
    }

    public static java.net.InetAddress getNetworkPart(java.net.InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        maskRawAddress(address, i);
        try {
            return java.net.InetAddress.getByAddress(address);
        } catch (java.net.UnknownHostException e) {
            throw new java.lang.RuntimeException("getNetworkPart error - " + e.toString());
        }
    }

    public static void maskRawAddress(byte[] bArr, int i) {
        if (i < 0 || i > bArr.length * 8) {
            throw new java.lang.RuntimeException("IP address with " + bArr.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (255 << (8 - (i % 8)));
        if (i2 < bArr.length) {
            bArr[i2] = (byte) (b & bArr[i2]);
        }
        while (true) {
            i2++;
            if (i2 < bArr.length) {
                bArr[i2] = 0;
            } else {
                return;
            }
        }
    }
}
