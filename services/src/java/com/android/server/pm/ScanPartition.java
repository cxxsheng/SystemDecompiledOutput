package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public class ScanPartition extends android.content.pm.PackagePartitions.SystemPartition {

    @android.annotation.Nullable
    public final com.android.server.pm.ApexManager.ActiveApexInfo apexInfo;
    public final int scanFlag;

    public ScanPartition(@android.annotation.NonNull android.content.pm.PackagePartitions.SystemPartition systemPartition) {
        super(systemPartition);
        this.scanFlag = scanFlagForPartition(systemPartition);
        this.apexInfo = null;
    }

    public ScanPartition(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull com.android.server.pm.ScanPartition scanPartition, @android.annotation.Nullable com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo) {
        super(file, scanPartition);
        int i = scanPartition.scanFlag;
        this.apexInfo = activeApexInfo;
        if (activeApexInfo != null) {
            i |= 8388608;
            i = activeApexInfo.isFactory ? i | 33554432 : i;
            if (activeApexInfo.activeApexChanged) {
                i |= 16777216;
            }
        }
        this.scanFlag = i;
    }

    private static int scanFlagForPartition(android.content.pm.PackagePartitions.SystemPartition systemPartition) {
        switch (systemPartition.type) {
            case 0:
                return 0;
            case 1:
                return 524288;
            case 2:
                return 4194304;
            case 3:
                return 262144;
            case 4:
                return 1048576;
            case 5:
                return 2097152;
            default:
                throw new java.lang.IllegalStateException("Unable to determine scan flag for " + systemPartition.getFolder());
        }
    }

    public java.lang.String toString() {
        return getFolder().getAbsolutePath() + ":" + this.scanFlag;
    }
}
