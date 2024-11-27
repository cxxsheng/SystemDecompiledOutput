package android.os.connectivity;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class WifiActivityEnergyInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.connectivity.WifiActivityEnergyInfo> CREATOR = new android.os.Parcelable.Creator<android.os.connectivity.WifiActivityEnergyInfo>() { // from class: android.os.connectivity.WifiActivityEnergyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.WifiActivityEnergyInfo createFromParcel(android.os.Parcel parcel) {
            return new android.os.connectivity.WifiActivityEnergyInfo(parcel.readLong(), parcel.readInt(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.WifiActivityEnergyInfo[] newArray(int i) {
            return new android.os.connectivity.WifiActivityEnergyInfo[i];
        }
    };
    public static final int STACK_STATE_INVALID = 0;
    public static final int STACK_STATE_STATE_ACTIVE = 1;
    public static final int STACK_STATE_STATE_IDLE = 3;
    public static final int STACK_STATE_STATE_SCANNING = 2;
    private final long mControllerEnergyUsedMicroJoules;
    private final long mControllerIdleDurationMillis;
    private final long mControllerRxDurationMillis;
    private final long mControllerScanDurationMillis;
    private final long mControllerTxDurationMillis;
    private final int mStackState;
    private final long mTimeSinceBootMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StackState {
    }

    public WifiActivityEnergyInfo(long j, int i, long j2, long j3, long j4, long j5) {
        this(j, i, j2, j3, j4, j5, calculateEnergyMicroJoules(j2, j3, j5));
    }

    private static long calculateEnergyMicroJoules(long j, long j2, long j3) {
        android.app.ContextImpl systemContext = android.app.ActivityThread.currentActivityThread().getSystemContext();
        if (systemContext == null) {
            return 0L;
        }
        com.android.internal.os.PowerProfile powerProfile = new com.android.internal.os.PowerProfile(systemContext);
        double averagePower = powerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_WIFI_CONTROLLER_IDLE);
        return (long) (((j * powerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_WIFI_CONTROLLER_TX)) + (j2 * powerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_WIFI_CONTROLLER_RX)) + (j3 * averagePower)) * (powerProfile.getAveragePower(com.android.internal.os.PowerProfile.POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE) / 1000.0d));
    }

    public WifiActivityEnergyInfo(long j, int i, long j2, long j3, long j4, long j5, long j6) {
        this.mTimeSinceBootMillis = j;
        this.mStackState = i;
        this.mControllerTxDurationMillis = j2;
        this.mControllerRxDurationMillis = j3;
        this.mControllerScanDurationMillis = j4;
        this.mControllerIdleDurationMillis = j5;
        this.mControllerEnergyUsedMicroJoules = j6;
    }

    public java.lang.String toString() {
        return "WifiActivityEnergyInfo{ mTimeSinceBootMillis=" + this.mTimeSinceBootMillis + " mStackState=" + this.mStackState + " mControllerTxDurationMillis=" + this.mControllerTxDurationMillis + " mControllerRxDurationMillis=" + this.mControllerRxDurationMillis + " mControllerScanDurationMillis=" + this.mControllerScanDurationMillis + " mControllerIdleDurationMillis=" + this.mControllerIdleDurationMillis + " mControllerEnergyUsedMicroJoules=" + this.mControllerEnergyUsedMicroJoules + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mTimeSinceBootMillis);
        parcel.writeInt(this.mStackState);
        parcel.writeLong(this.mControllerTxDurationMillis);
        parcel.writeLong(this.mControllerRxDurationMillis);
        parcel.writeLong(this.mControllerScanDurationMillis);
        parcel.writeLong(this.mControllerIdleDurationMillis);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public long getTimeSinceBootMillis() {
        return this.mTimeSinceBootMillis;
    }

    public int getStackState() {
        return this.mStackState;
    }

    public long getControllerTxDurationMillis() {
        return this.mControllerTxDurationMillis;
    }

    public long getControllerRxDurationMillis() {
        return this.mControllerRxDurationMillis;
    }

    public long getControllerScanDurationMillis() {
        return this.mControllerScanDurationMillis;
    }

    public long getControllerIdleDurationMillis() {
        return this.mControllerIdleDurationMillis;
    }

    public long getControllerEnergyUsedMicroJoules() {
        return this.mControllerEnergyUsedMicroJoules;
    }

    public boolean isValid() {
        return this.mControllerTxDurationMillis >= 0 && this.mControllerRxDurationMillis >= 0 && this.mControllerScanDurationMillis >= 0 && this.mControllerIdleDurationMillis >= 0;
    }
}
