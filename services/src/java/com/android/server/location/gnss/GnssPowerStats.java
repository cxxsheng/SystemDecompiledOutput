package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssPowerStats {
    private final int mElapsedRealtimeFlags;
    private final long mElapsedRealtimeNanos;
    private final double mElapsedRealtimeUncertaintyNanos;
    private final double mMultibandAcquisitionModeEnergyMilliJoule;
    private final double mMultibandTrackingModeEnergyMilliJoule;
    private final double[] mOtherModesEnergyMilliJoule;
    private final double mSinglebandAcquisitionModeEnergyMilliJoule;
    private final double mSinglebandTrackingModeEnergyMilliJoule;
    private final double mTotalEnergyMilliJoule;

    public GnssPowerStats(int i, long j, double d, double d2, double d3, double d4, double d5, double d6, double[] dArr) {
        this.mElapsedRealtimeFlags = i;
        this.mElapsedRealtimeNanos = j;
        this.mElapsedRealtimeUncertaintyNanos = d;
        this.mTotalEnergyMilliJoule = d2;
        this.mSinglebandTrackingModeEnergyMilliJoule = d3;
        this.mMultibandTrackingModeEnergyMilliJoule = d4;
        this.mSinglebandAcquisitionModeEnergyMilliJoule = d5;
        this.mMultibandAcquisitionModeEnergyMilliJoule = d6;
        this.mOtherModesEnergyMilliJoule = dArr;
    }

    public boolean hasElapsedRealtimeNanos() {
        return (this.mElapsedRealtimeFlags & 1) != 0;
    }

    public boolean hasElapsedRealtimeUncertaintyNanos() {
        return (this.mElapsedRealtimeFlags & 2) != 0;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    public double getElapsedRealtimeUncertaintyNanos() {
        return this.mElapsedRealtimeUncertaintyNanos;
    }

    public double getTotalEnergyMilliJoule() {
        return this.mTotalEnergyMilliJoule;
    }

    public double getSinglebandTrackingModeEnergyMilliJoule() {
        return this.mSinglebandTrackingModeEnergyMilliJoule;
    }

    public double getMultibandTrackingModeEnergyMilliJoule() {
        return this.mMultibandTrackingModeEnergyMilliJoule;
    }

    public double getSinglebandAcquisitionModeEnergyMilliJoule() {
        return this.mSinglebandAcquisitionModeEnergyMilliJoule;
    }

    public double getMultibandAcquisitionModeEnergyMilliJoule() {
        return this.mMultibandAcquisitionModeEnergyMilliJoule;
    }

    public double[] getOtherModesEnergyMilliJoule() {
        return this.mOtherModesEnergyMilliJoule;
    }

    public void validate() {
        com.android.internal.util.Preconditions.checkArgument(hasElapsedRealtimeNanos());
    }

    public void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr, android.location.GnssCapabilities gnssCapabilities) {
        if (hasElapsedRealtimeNanos()) {
            indentingPrintWriter.print("time: ");
            indentingPrintWriter.print(android.util.TimeUtils.formatRealtime(java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(this.mElapsedRealtimeNanos)));
            if (hasElapsedRealtimeUncertaintyNanos() && this.mElapsedRealtimeUncertaintyNanos != 0.0d) {
                indentingPrintWriter.print(" +/- ");
                indentingPrintWriter.print(java.util.concurrent.TimeUnit.NANOSECONDS.toMillis((long) this.mElapsedRealtimeUncertaintyNanos));
            }
        }
        if (gnssCapabilities.hasPowerTotal()) {
            indentingPrintWriter.print("total power: ");
            indentingPrintWriter.print(this.mTotalEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerSinglebandTracking()) {
            indentingPrintWriter.print("single-band tracking power: ");
            indentingPrintWriter.print(this.mSinglebandTrackingModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerMultibandTracking()) {
            indentingPrintWriter.print("multi-band tracking power: ");
            indentingPrintWriter.print(this.mMultibandTrackingModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerSinglebandAcquisition()) {
            indentingPrintWriter.print("single-band acquisition power: ");
            indentingPrintWriter.print(this.mSinglebandAcquisitionModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerMultibandAcquisition()) {
            indentingPrintWriter.print("multi-band acquisition power: ");
            indentingPrintWriter.print(this.mMultibandAcquisitionModeEnergyMilliJoule);
            indentingPrintWriter.println("mJ");
        }
        if (gnssCapabilities.hasPowerOtherModes()) {
            for (int i = 0; i < this.mOtherModesEnergyMilliJoule.length; i++) {
                indentingPrintWriter.print("other mode [" + i + "] power: ");
                indentingPrintWriter.print(this.mOtherModesEnergyMilliJoule[i]);
                indentingPrintWriter.println("mJ");
            }
        }
    }
}
