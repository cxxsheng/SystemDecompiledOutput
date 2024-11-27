package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class NetworkCapabilitiesUtils {
    private static final int[] DISPLAY_TRANSPORT_PRIORITIES = {4, 0, 5, 2, 1, 3, 8};
    public static final long RESTRICTED_CAPABILITIES = com.android.net.module.util.BitUtils.packBitList(31, 5, 2, 10, 29, 3, 7, 4, 23, 8, 27, 30, 9, 33);
    private static final long FORCE_RESTRICTED_CAPABILITIES = com.android.net.module.util.BitUtils.packBitList(29, 22, 26);
    public static final long UNRESTRICTED_CAPABILITIES = com.android.net.module.util.BitUtils.packBitList(12, 0, 1, 6);

    public static int getDisplayTransport(int[] iArr) {
        for (int i : DISPLAY_TRANSPORT_PRIORITIES) {
            if (com.android.net.module.util.CollectionUtils.contains(iArr, i)) {
                return i;
            }
        }
        if (iArr.length < 1) {
            throw new java.lang.IllegalArgumentException("No transport in the provided array");
        }
        return iArr[0];
    }

    public static boolean inferRestrictedCapability(android.net.NetworkCapabilities networkCapabilities) {
        for (int i : com.android.net.module.util.BitUtils.unpackBits(FORCE_RESTRICTED_CAPABILITIES)) {
            if (networkCapabilities.hasCapability(i)) {
                return true;
            }
        }
        for (int i2 : com.android.net.module.util.BitUtils.unpackBits(UNRESTRICTED_CAPABILITIES)) {
            if (networkCapabilities.hasCapability(i2)) {
                return false;
            }
        }
        for (int i3 : com.android.net.module.util.BitUtils.unpackBits(RESTRICTED_CAPABILITIES)) {
            if (networkCapabilities.hasCapability(i3)) {
                return true;
            }
        }
        return false;
    }
}
