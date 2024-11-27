package com.android.server.cpu;

/* loaded from: classes.dex */
public final class CpuAvailabilityInfo {
    public static final int MISSING_CPU_AVAILABILITY_PERCENT = -1;
    public final int cpuset;
    public final long dataTimestampUptimeMillis;
    public final int latestAvgAvailabilityPercent;
    public final int pastNMillisAvgAvailabilityPercent;
    public final long pastNMillisDuration;

    public java.lang.String toString() {
        return "CpuAvailabilityInfo{cpuset = " + this.cpuset + ", dataTimestampUptimeMillis = " + this.dataTimestampUptimeMillis + ", latestAvgAvailabilityPercent = " + this.latestAvgAvailabilityPercent + ", pastNMillisAvgAvailabilityPercent = " + this.pastNMillisAvgAvailabilityPercent + ", pastNMillisDuration = " + this.pastNMillisDuration + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.cpu.CpuAvailabilityInfo)) {
            return false;
        }
        com.android.server.cpu.CpuAvailabilityInfo cpuAvailabilityInfo = (com.android.server.cpu.CpuAvailabilityInfo) obj;
        return this.cpuset == cpuAvailabilityInfo.cpuset && this.dataTimestampUptimeMillis == cpuAvailabilityInfo.dataTimestampUptimeMillis && this.latestAvgAvailabilityPercent == cpuAvailabilityInfo.latestAvgAvailabilityPercent && this.pastNMillisAvgAvailabilityPercent == cpuAvailabilityInfo.pastNMillisAvgAvailabilityPercent && this.pastNMillisDuration == cpuAvailabilityInfo.pastNMillisDuration;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.cpuset), java.lang.Long.valueOf(this.dataTimestampUptimeMillis), java.lang.Integer.valueOf(this.latestAvgAvailabilityPercent), java.lang.Integer.valueOf(this.pastNMillisAvgAvailabilityPercent), java.lang.Long.valueOf(this.pastNMillisDuration));
    }

    CpuAvailabilityInfo(int i, long j, int i2, int i3, long j2) {
        this.cpuset = com.android.internal.util.Preconditions.checkArgumentInRange(i, 1, 2, "cpuset");
        this.dataTimestampUptimeMillis = com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
        this.latestAvgAvailabilityPercent = com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        this.pastNMillisAvgAvailabilityPercent = i3;
        this.pastNMillisDuration = com.android.internal.util.Preconditions.checkArgumentNonnegative(j2);
    }
}
