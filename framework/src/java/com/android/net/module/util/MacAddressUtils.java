package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class MacAddressUtils {
    private static final int ETHER_ADDR_LEN = 6;
    private static final long VALID_LONG_MASK = 281474976710655L;
    private static final long LOCALLY_ASSIGNED_MASK = longAddrFromByteAddr(android.net.MacAddress.fromString("2:0:0:0:0:0").toByteArray());
    private static final long MULTICAST_MASK = longAddrFromByteAddr(android.net.MacAddress.fromString("1:0:0:0:0:0").toByteArray());
    private static final long OUI_MASK = longAddrFromByteAddr(android.net.MacAddress.fromString("ff:ff:ff:0:0:0").toByteArray());
    private static final long NIC_MASK = longAddrFromByteAddr(android.net.MacAddress.fromString("0:0:0:ff:ff:ff").toByteArray());
    private static final android.net.MacAddress DEFAULT_MAC_ADDRESS = android.net.MacAddress.fromString("02:00:00:00:00:00");

    public static boolean isMulticastAddress(android.net.MacAddress macAddress) {
        return (longAddrFromByteAddr(macAddress.toByteArray()) & MULTICAST_MASK) != 0;
    }

    public static android.net.MacAddress createRandomUnicastAddress() {
        return createRandomUnicastAddress(null, new java.security.SecureRandom());
    }

    public static android.net.MacAddress createRandomUnicastAddress(android.net.MacAddress macAddress, java.util.Random random) {
        long longAddrFromByteAddr;
        if (macAddress == null) {
            longAddrFromByteAddr = random.nextLong() & VALID_LONG_MASK;
        } else {
            longAddrFromByteAddr = (longAddrFromByteAddr(macAddress.toByteArray()) & OUI_MASK) | (NIC_MASK & random.nextLong());
        }
        android.net.MacAddress fromBytes = android.net.MacAddress.fromBytes(byteAddrFromLongAddr((longAddrFromByteAddr | LOCALLY_ASSIGNED_MASK) & (~MULTICAST_MASK)));
        if (fromBytes.equals(DEFAULT_MAC_ADDRESS)) {
            return createRandomUnicastAddress(macAddress, random);
        }
        return fromBytes;
    }

    public static long longAddrFromByteAddr(byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        if (!isMacAddress(bArr)) {
            throw new java.lang.IllegalArgumentException(java.util.Arrays.toString(bArr) + " was not a valid MAC address");
        }
        long j = 0;
        for (byte b : bArr) {
            j = (j << 8) + (b & 255);
        }
        return j;
    }

    public static byte[] byteAddrFromLongAddr(long j) {
        int i = 6;
        byte[] bArr = new byte[6];
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                bArr[i2] = (byte) j;
                j >>= 8;
                i = i2;
            } else {
                return bArr;
            }
        }
    }

    public static boolean isMacAddress(byte[] bArr) {
        return bArr != null && bArr.length == 6;
    }
}
