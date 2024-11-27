package com.android.server.vcn.repackaged.util;

/* loaded from: classes5.dex */
public class LogUtils {
    public static java.lang.String getHashedSubscriptionGroup(android.os.ParcelUuid parcelUuid) {
        if (parcelUuid == null) {
            return null;
        }
        return com.android.internal.util.HexDump.toHexString(parcelUuid.hashCode());
    }
}
