package android.os;

/* loaded from: classes3.dex */
public final class PowerMonitorReadings {
    public static final int ENERGY_UNAVAILABLE = -1;
    private static final java.util.Comparator<android.os.PowerMonitor> POWER_MONITOR_COMPARATOR = java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.os.PowerMonitorReadings$$ExternalSyntheticLambda0
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(java.lang.Object obj) {
            int i;
            i = ((android.os.PowerMonitor) obj).index;
            return i;
        }
    });
    private final long[] mEnergyUws;
    private final android.os.PowerMonitor[] mPowerMonitors;
    private final long[] mTimestampsMs;

    public PowerMonitorReadings(android.os.PowerMonitor[] powerMonitorArr, long[] jArr, long[] jArr2) {
        this.mPowerMonitors = powerMonitorArr;
        this.mEnergyUws = jArr;
        this.mTimestampsMs = jArr2;
    }

    public long getConsumedEnergy(android.os.PowerMonitor powerMonitor) {
        int binarySearch = java.util.Arrays.binarySearch(this.mPowerMonitors, powerMonitor, POWER_MONITOR_COMPARATOR);
        if (binarySearch >= 0) {
            return this.mEnergyUws[binarySearch];
        }
        return -1L;
    }

    public long getTimestampMillis(android.os.PowerMonitor powerMonitor) {
        int binarySearch = java.util.Arrays.binarySearch(this.mPowerMonitors, powerMonitor, POWER_MONITOR_COMPARATOR);
        if (binarySearch >= 0) {
            return this.mTimestampsMs[binarySearch];
        }
        return 0L;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(" monitors: [");
        for (int i = 0; i < this.mPowerMonitors.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mPowerMonitors[i].getName()).append(" = ").append(this.mEnergyUws[i]).append(" (").append(this.mTimestampsMs[i]).append(')');
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
