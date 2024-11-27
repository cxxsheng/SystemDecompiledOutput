package com.android.net.module.util;

/* loaded from: classes5.dex */
public class InetAddressUtils {
    private static final int INET4_ADDR_LENGTH = 4;
    private static final int INET6_ADDR_LENGTH = 16;
    private static final java.lang.String TAG = com.android.net.module.util.InetAddressUtils.class.getSimpleName();

    public static void parcelInetAddress(android.os.Parcel parcel, java.net.InetAddress inetAddress, int i) {
        parcel.writeByteArray(inetAddress != null ? inetAddress.getAddress() : null);
        if (inetAddress instanceof java.net.Inet6Address) {
            java.net.Inet6Address inet6Address = (java.net.Inet6Address) inetAddress;
            boolean z = inet6Address.getScopeId() != 0;
            parcel.writeBoolean(z);
            if (z) {
                parcel.writeInt(inet6Address.getScopeId());
            }
        }
    }

    public static java.net.InetAddress unparcelInetAddress(android.os.Parcel parcel) {
        byte[] createByteArray = parcel.createByteArray();
        if (createByteArray == null) {
            return null;
        }
        try {
            if (createByteArray.length == 16) {
                return java.net.Inet6Address.getByAddress((java.lang.String) null, createByteArray, parcel.readBoolean() ? parcel.readInt() : 0);
            }
            return java.net.InetAddress.getByAddress(createByteArray);
        } catch (java.net.UnknownHostException e) {
            return null;
        }
    }

    public static java.net.Inet6Address withScopeId(java.net.Inet6Address inet6Address, int i) {
        if (!inet6Address.isLinkLocalAddress()) {
            return inet6Address;
        }
        try {
            return java.net.Inet6Address.getByAddress((java.lang.String) null, inet6Address.getAddress(), i);
        } catch (java.net.UnknownHostException e) {
            android.util.Log.wtf(TAG, "Cannot construct scoped Inet6Address with Inet6Address.getAddress(" + inet6Address.getHostAddress() + "): ", e);
            return null;
        }
    }

    public static java.net.Inet6Address v4MappedV6Address(java.net.Inet4Address inet4Address) {
        byte[] bArr = new byte[16];
        bArr[10] = -1;
        bArr[11] = -1;
        java.lang.System.arraycopy(inet4Address.getAddress(), 0, bArr, 12, 4);
        try {
            return java.net.Inet6Address.getByAddress((java.lang.String) null, bArr, -1);
        } catch (java.net.UnknownHostException e) {
            android.util.Log.wtf(TAG, "Failed to generate v4-mapped v6 address from " + inet4Address, e);
            return null;
        }
    }

    private InetAddressUtils() {
    }
}
