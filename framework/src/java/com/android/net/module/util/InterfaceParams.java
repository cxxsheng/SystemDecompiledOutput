package com.android.net.module.util;

/* loaded from: classes5.dex */
public class InterfaceParams {
    private static final int ETHER_MTU = 1500;
    private static final int IPV6_MIN_MTU = 1280;
    public final int defaultMtu;
    public final boolean hasMacAddress;
    public final int index;
    public final android.net.MacAddress macAddr;
    public final java.lang.String name;

    public static com.android.net.module.util.InterfaceParams getByName(java.lang.String str) {
        java.net.NetworkInterface networkInterfaceByName = getNetworkInterfaceByName(str);
        if (networkInterfaceByName == null) {
            return null;
        }
        try {
            return new com.android.net.module.util.InterfaceParams(str, networkInterfaceByName.getIndex(), getMacAddress(networkInterfaceByName), networkInterfaceByName.getMTU());
        } catch (java.lang.IllegalArgumentException | java.net.SocketException e) {
            return null;
        }
    }

    public InterfaceParams(java.lang.String str, int i, android.net.MacAddress macAddress) {
        this(str, i, macAddress, 1500);
    }

    public InterfaceParams(java.lang.String str, int i, android.net.MacAddress macAddress, int i2) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("impossible interface name");
        }
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("invalid interface index");
        }
        this.name = str;
        this.index = i;
        this.hasMacAddress = macAddress != null;
        this.macAddr = this.hasMacAddress ? macAddress : android.net.MacAddress.fromBytes(new byte[]{2, 0, 0, 0, 0, 0});
        this.defaultMtu = i2 <= 1280 ? 1280 : i2;
    }

    public java.lang.String toString() {
        return java.lang.String.format("%s/%d/%s/%d", this.name, java.lang.Integer.valueOf(this.index), this.macAddr, java.lang.Integer.valueOf(this.defaultMtu));
    }

    private static java.net.NetworkInterface getNetworkInterfaceByName(java.lang.String str) {
        try {
            return java.net.NetworkInterface.getByName(str);
        } catch (java.lang.NullPointerException | java.net.SocketException e) {
            return null;
        }
    }

    private static android.net.MacAddress getMacAddress(java.net.NetworkInterface networkInterface) {
        try {
            return android.net.MacAddress.fromBytes(networkInterface.getHardwareAddress());
        } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException | java.net.SocketException e) {
            return null;
        }
    }
}
