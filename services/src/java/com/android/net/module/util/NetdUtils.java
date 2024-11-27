package com.android.net.module.util;

/* loaded from: classes.dex */
public class NetdUtils {
    private static final java.lang.String TAG = com.android.net.module.util.NetdUtils.class.getSimpleName();

    public enum ModifyOperation {
        ADD,
        REMOVE
    }

    public static android.net.InterfaceConfigurationParcel getInterfaceConfigParcel(@androidx.annotation.NonNull android.net.INetd iNetd, @androidx.annotation.NonNull java.lang.String str) {
        try {
            return iNetd.interfaceGetCfg(str);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    private static void validateFlag(java.lang.String str) {
        if (str.indexOf(32) >= 0) {
            throw new java.lang.IllegalArgumentException("flag contains space: " + str);
        }
    }

    public static boolean hasFlag(@androidx.annotation.NonNull android.net.InterfaceConfigurationParcel interfaceConfigurationParcel, @androidx.annotation.NonNull java.lang.String str) {
        validateFlag(str);
        return new java.util.HashSet(java.util.Arrays.asList(interfaceConfigurationParcel.flags)).contains(str);
    }

    @androidx.annotation.VisibleForTesting
    protected static java.lang.String[] removeAndAddFlags(@androidx.annotation.NonNull java.lang.String[] strArr, @androidx.annotation.NonNull java.lang.String str, @androidx.annotation.NonNull java.lang.String str2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            validateFlag(str2);
            for (java.lang.String str3 : strArr) {
                if (!str.equals(str3) && !str2.equals(str3)) {
                    arrayList.add(str3);
                }
            }
            arrayList.add(str2);
            return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.lang.IllegalStateException("Invalid InterfaceConfigurationParcel", e);
        }
    }

    public static void setInterfaceConfig(android.net.INetd iNetd, android.net.InterfaceConfigurationParcel interfaceConfigurationParcel) {
        try {
            iNetd.interfaceSetCfg(interfaceConfigurationParcel);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public static void setInterfaceUp(android.net.INetd iNetd, java.lang.String str) {
        android.net.InterfaceConfigurationParcel interfaceConfigParcel = getInterfaceConfigParcel(iNetd, str);
        interfaceConfigParcel.flags = removeAndAddFlags(interfaceConfigParcel.flags, android.net.INetd.IF_STATE_DOWN, android.net.INetd.IF_STATE_UP);
        setInterfaceConfig(iNetd, interfaceConfigParcel);
    }

    public static void setInterfaceDown(android.net.INetd iNetd, java.lang.String str) {
        android.net.InterfaceConfigurationParcel interfaceConfigParcel = getInterfaceConfigParcel(iNetd, str);
        interfaceConfigParcel.flags = removeAndAddFlags(interfaceConfigParcel.flags, android.net.INetd.IF_STATE_UP, android.net.INetd.IF_STATE_DOWN);
        setInterfaceConfig(iNetd, interfaceConfigParcel);
    }

    public static void tetherStart(android.net.INetd iNetd, boolean z, java.lang.String[] strArr) throws android.os.RemoteException, android.os.ServiceSpecificException {
        android.net.TetherConfigParcel tetherConfigParcel = new android.net.TetherConfigParcel();
        tetherConfigParcel.usingLegacyDnsProxy = z;
        tetherConfigParcel.dhcpRanges = strArr;
        iNetd.tetherStartWithConfiguration(tetherConfigParcel);
    }

    public static void tetherInterface(android.net.INetd iNetd, java.lang.String str, android.net.IpPrefix ipPrefix) throws android.os.RemoteException, android.os.ServiceSpecificException {
        tetherInterface(iNetd, str, ipPrefix, 20, 50);
    }

    public static void tetherInterface(android.net.INetd iNetd, java.lang.String str, android.net.IpPrefix ipPrefix, int i, int i2) throws android.os.RemoteException, android.os.ServiceSpecificException {
        iNetd.tetherInterfaceAdd(str);
        networkAddInterface(iNetd, str, i, i2);
        modifyRoute(iNetd, com.android.net.module.util.NetdUtils.ModifyOperation.ADD, 99, new android.net.RouteInfo(ipPrefix, null, str, 1));
        modifyRoute(iNetd, com.android.net.module.util.NetdUtils.ModifyOperation.ADD, 99, new android.net.RouteInfo(new android.net.IpPrefix("fe80::/64"), null, str, 1));
    }

    private static void networkAddInterface(android.net.INetd iNetd, java.lang.String str, int i, int i2) throws android.os.ServiceSpecificException, android.os.RemoteException {
        for (int i3 = 1; i3 <= i; i3++) {
            try {
                iNetd.networkAddInterface(99, str);
                return;
            } catch (android.os.ServiceSpecificException e) {
                if (e.errorCode == android.system.OsConstants.EBUSY && i3 < i) {
                    android.os.SystemClock.sleep(i2);
                } else {
                    android.util.Log.e(TAG, "Retry Netd#networkAddInterface failure: " + e);
                    throw e;
                }
            }
        }
    }

    public static void untetherInterface(android.net.INetd iNetd, java.lang.String str) throws android.os.RemoteException, android.os.ServiceSpecificException {
        try {
            iNetd.tetherInterfaceRemove(str);
        } finally {
            iNetd.networkRemoveInterface(99, str);
        }
    }

    public static void addRoutesToLocalNetwork(android.net.INetd iNetd, java.lang.String str, java.util.List<android.net.RouteInfo> list) {
        for (android.net.RouteInfo routeInfo : list) {
            if (!routeInfo.isDefaultRoute()) {
                modifyRoute(iNetd, com.android.net.module.util.NetdUtils.ModifyOperation.ADD, 99, routeInfo);
            }
        }
        modifyRoute(iNetd, com.android.net.module.util.NetdUtils.ModifyOperation.ADD, 99, new android.net.RouteInfo(new android.net.IpPrefix("fe80::/64"), null, str, 1));
    }

    public static int removeRoutesFromLocalNetwork(android.net.INetd iNetd, java.util.List<android.net.RouteInfo> list) {
        java.util.Iterator<android.net.RouteInfo> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                modifyRoute(iNetd, com.android.net.module.util.NetdUtils.ModifyOperation.REMOVE, 99, it.next());
            } catch (java.lang.IllegalStateException e) {
                i++;
            }
        }
        return i;
    }

    @android.annotation.SuppressLint({"NewApi"})
    private static java.lang.String findNextHop(android.net.RouteInfo routeInfo) {
        switch (routeInfo.getType()) {
            case 1:
                return routeInfo.hasGateway() ? routeInfo.getGateway().getHostAddress() : "";
            case 7:
                return android.net.INetd.NEXTHOP_UNREACHABLE;
            case 9:
                return android.net.INetd.NEXTHOP_THROW;
            default:
                return "";
        }
    }

    public static void modifyRoute(android.net.INetd iNetd, com.android.net.module.util.NetdUtils.ModifyOperation modifyOperation, int i, android.net.RouteInfo routeInfo) {
        java.lang.String str = routeInfo.getInterface();
        java.lang.String obj = routeInfo.getDestination().toString();
        java.lang.String findNextHop = findNextHop(routeInfo);
        try {
            switch (com.android.net.module.util.NetdUtils.AnonymousClass1.$SwitchMap$com$android$net$module$util$NetdUtils$ModifyOperation[modifyOperation.ordinal()]) {
                case 1:
                    iNetd.networkAddRoute(i, str, obj, findNextHop);
                    return;
                case 2:
                    iNetd.networkRemoveRoute(i, str, obj, findNextHop);
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unsupported modify operation:" + modifyOperation);
            }
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    /* renamed from: com.android.net.module.util.NetdUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$net$module$util$NetdUtils$ModifyOperation = new int[com.android.net.module.util.NetdUtils.ModifyOperation.values().length];

        static {
            try {
                $SwitchMap$com$android$net$module$util$NetdUtils$ModifyOperation[com.android.net.module.util.NetdUtils.ModifyOperation.ADD.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$net$module$util$NetdUtils$ModifyOperation[com.android.net.module.util.NetdUtils.ModifyOperation.REMOVE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    public static android.net.RouteInfoParcel toRouteInfoParcel(android.net.RouteInfo routeInfo) {
        java.lang.String str = "";
        switch (routeInfo.getType()) {
            case 1:
                if (routeInfo.hasGateway()) {
                    str = routeInfo.getGateway().getHostAddress();
                    break;
                }
                break;
            case 7:
                str = android.net.INetd.NEXTHOP_UNREACHABLE;
                break;
            case 9:
                str = android.net.INetd.NEXTHOP_THROW;
                break;
        }
        android.net.RouteInfoParcel routeInfoParcel = new android.net.RouteInfoParcel();
        routeInfoParcel.ifName = routeInfo.getInterface();
        routeInfoParcel.destination = routeInfo.getDestination().toString();
        routeInfoParcel.nextHop = str;
        routeInfoParcel.mtu = routeInfo.getMtu();
        return routeInfoParcel;
    }
}
