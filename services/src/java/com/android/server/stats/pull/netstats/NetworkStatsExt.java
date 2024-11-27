package com.android.server.stats.pull.netstats;

/* loaded from: classes2.dex */
public class NetworkStatsExt {
    public final boolean isTypeProxy;
    public final int oemManaged;
    public final int ratType;
    public final boolean slicedByFgbg;
    public final boolean slicedByMetered;
    public final boolean slicedByTag;

    @android.annotation.NonNull
    public final android.net.NetworkStats stats;

    @android.annotation.Nullable
    public final com.android.server.stats.pull.netstats.SubInfo subInfo;
    public final int[] transports;

    public NetworkStatsExt(@android.annotation.NonNull android.net.NetworkStats networkStats, int[] iArr, boolean z) {
        this(networkStats, iArr, z, false, false, 0, null, -1, false);
    }

    public NetworkStatsExt(@android.annotation.NonNull android.net.NetworkStats networkStats, int[] iArr, boolean z, boolean z2, boolean z3, int i, @android.annotation.Nullable com.android.server.stats.pull.netstats.SubInfo subInfo, int i2, boolean z4) {
        this.stats = networkStats;
        this.transports = java.util.Arrays.copyOf(iArr, iArr.length);
        java.util.Arrays.sort(this.transports);
        this.slicedByFgbg = z;
        this.slicedByTag = z2;
        this.slicedByMetered = z3;
        this.ratType = i;
        this.subInfo = subInfo;
        this.oemManaged = i2;
        this.isTypeProxy = z4;
    }

    public boolean hasSameSlicing(@android.annotation.NonNull com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt) {
        return java.util.Arrays.equals(this.transports, networkStatsExt.transports) && this.slicedByFgbg == networkStatsExt.slicedByFgbg && this.slicedByTag == networkStatsExt.slicedByTag && this.slicedByMetered == networkStatsExt.slicedByMetered && this.ratType == networkStatsExt.ratType && java.util.Objects.equals(this.subInfo, networkStatsExt.subInfo) && this.oemManaged == networkStatsExt.oemManaged && this.isTypeProxy == networkStatsExt.isTypeProxy;
    }
}
