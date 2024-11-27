package com.android.server.vcn.util;

/* loaded from: classes2.dex */
public class LogUtils {
    @android.annotation.Nullable
    public static java.lang.String getHashedSubscriptionGroup(@android.annotation.Nullable android.os.ParcelUuid parcelUuid) {
        if (parcelUuid == null) {
            return null;
        }
        return com.android.internal.util.HexDump.toHexString(parcelUuid.hashCode());
    }
}
