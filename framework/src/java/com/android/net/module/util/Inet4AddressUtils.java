package com.android.net.module.util;

/* loaded from: classes5.dex */
public class Inet4AddressUtils {
    public static java.net.Inet4Address intToInet4AddressHTL(int i) {
        return intToInet4AddressHTH(java.lang.Integer.reverseBytes(i));
    }

    public static java.net.Inet4Address intToInet4AddressHTH(int i) {
        try {
            return (java.net.Inet4Address) java.net.InetAddress.getByAddress(new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)});
        } catch (java.net.UnknownHostException e) {
            throw new java.lang.AssertionError();
        }
    }

    public static int inet4AddressToIntHTH(java.net.Inet4Address inet4Address) throws java.lang.IllegalArgumentException {
        byte[] address = inet4Address.getAddress();
        return (address[3] & 255) | ((address[0] & 255) << 24) | ((address[1] & 255) << 16) | ((address[2] & 255) << 8);
    }

    public static int inet4AddressToIntHTL(java.net.Inet4Address inet4Address) {
        return java.lang.Integer.reverseBytes(inet4AddressToIntHTH(inet4Address));
    }

    public static int prefixLengthToV4NetmaskIntHTH(int i) throws java.lang.IllegalArgumentException {
        if (i < 0 || i > 32) {
            throw new java.lang.IllegalArgumentException("Invalid prefix length (0 <= prefix <= 32)");
        }
        if (i == 0) {
            return 0;
        }
        return (-1) << (32 - i);
    }

    public static int prefixLengthToV4NetmaskIntHTL(int i) throws java.lang.IllegalArgumentException {
        return java.lang.Integer.reverseBytes(prefixLengthToV4NetmaskIntHTH(i));
    }

    public static int netmaskToPrefixLength(java.net.Inet4Address inet4Address) {
        int inet4AddressToIntHTH = inet4AddressToIntHTH(inet4Address);
        int bitCount = java.lang.Integer.bitCount(inet4AddressToIntHTH);
        if (java.lang.Integer.numberOfTrailingZeros(inet4AddressToIntHTH) != 32 - bitCount) {
            throw new java.lang.IllegalArgumentException("Non-contiguous netmask: " + java.lang.Integer.toHexString(inet4AddressToIntHTH));
        }
        return bitCount;
    }

    public static int getImplicitNetmask(java.net.Inet4Address inet4Address) {
        int i = inet4Address.getAddress()[0] & 255;
        if (i < 128) {
            return 8;
        }
        if (i < 192) {
            return 16;
        }
        if (i < 224) {
            return 24;
        }
        return 32;
    }

    public static java.net.Inet4Address getBroadcastAddress(java.net.Inet4Address inet4Address, int i) throws java.lang.IllegalArgumentException {
        return intToInet4AddressHTH(inet4AddressToIntHTH(inet4Address) | (~prefixLengthToV4NetmaskIntHTH(i)));
    }

    public static java.net.Inet4Address getPrefixMaskAsInet4Address(int i) throws java.lang.IllegalArgumentException {
        return intToInet4AddressHTH(prefixLengthToV4NetmaskIntHTH(i));
    }

    public static java.lang.String trimAddressZeros(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.lang.String[] split = str.split("\\.");
        if (split.length != 4) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(16);
        for (int i = 0; i < 4; i++) {
            try {
                if (split[i].length() > 3) {
                    return str;
                }
                sb.append(java.lang.Integer.parseInt(split[i]));
                if (i < 3) {
                    sb.append('.');
                }
            } catch (java.lang.NumberFormatException e) {
                return str;
            }
        }
        return sb.toString();
    }
}
