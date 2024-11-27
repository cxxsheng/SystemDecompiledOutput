package com.android.internal.net;

/* loaded from: classes4.dex */
public class NetworkUtilsInternal {
    private static final int[] ADDRESS_FAMILIES = {android.system.OsConstants.AF_INET, android.system.OsConstants.AF_INET6};

    public static native boolean protectFromVpn(int i);

    public static native boolean protectFromVpn(java.io.FileDescriptor fileDescriptor);

    public static native void setAllowNetworkingForProcess(boolean z);

    public static boolean isWeaklyValidatedHostname(java.lang.String str) {
        if (!str.matches("^[a-zA-Z0-9_.-]+$")) {
            return false;
        }
        for (int i : ADDRESS_FAMILIES) {
            if (android.system.Os.inet_pton(i, str) != null) {
                return false;
            }
        }
        return true;
    }
}
