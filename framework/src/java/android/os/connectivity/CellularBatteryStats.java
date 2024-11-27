package android.os.connectivity;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CellularBatteryStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.connectivity.CellularBatteryStats> CREATOR = new android.os.Parcelable.Creator<android.os.connectivity.CellularBatteryStats>() { // from class: android.os.connectivity.CellularBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.CellularBatteryStats createFromParcel(android.os.Parcel parcel) {
            long readLong = parcel.readLong();
            long readLong2 = parcel.readLong();
            long readLong3 = parcel.readLong();
            long readLong4 = parcel.readLong();
            long readLong5 = parcel.readLong();
            long readLong6 = parcel.readLong();
            long readLong7 = parcel.readLong();
            long readLong8 = parcel.readLong();
            long readLong9 = parcel.readLong();
            long readLong10 = parcel.readLong();
            return new android.os.connectivity.CellularBatteryStats(readLong, readLong2, readLong3, readLong4, readLong5, readLong6, readLong7, readLong8, readLong9, java.lang.Long.valueOf(readLong10), parcel.createLongArray(), parcel.createLongArray(), parcel.createLongArray(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.CellularBatteryStats[] newArray(int i) {
            return new android.os.connectivity.CellularBatteryStats[i];
        }
    };
    private final long mEnergyConsumedMaMs;
    private final long mIdleTimeMs;
    private final long mKernelActiveTimeMs;
    private final long mLoggingDurationMs;
    private final long mMonitoredRailChargeConsumedMaMs;
    private final long mNumBytesRx;
    private final long mNumBytesTx;
    private final long mNumPacketsRx;
    private final long mNumPacketsTx;
    private final long mRxTimeMs;
    private final long mSleepTimeMs;
    private final long[] mTimeInRatMs;
    private final long[] mTimeInRxSignalStrengthLevelMs;
    private final long[] mTxTimeMs;

    public CellularBatteryStats(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, java.lang.Long l, long[] jArr, long[] jArr2, long[] jArr3, long j10) {
        this.mLoggingDurationMs = j;
        this.mKernelActiveTimeMs = j2;
        this.mNumPacketsTx = j3;
        this.mNumBytesTx = j4;
        this.mNumPacketsRx = j5;
        this.mNumBytesRx = j6;
        this.mSleepTimeMs = j7;
        this.mIdleTimeMs = j8;
        this.mRxTimeMs = j9;
        this.mEnergyConsumedMaMs = l.longValue();
        this.mTimeInRatMs = java.util.Arrays.copyOfRange(jArr, 0, java.lang.Math.min(jArr.length, android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES));
        this.mTimeInRxSignalStrengthLevelMs = java.util.Arrays.copyOfRange(jArr2, 0, java.lang.Math.min(jArr2.length, android.telephony.CellSignalStrength.getNumSignalStrengthLevels()));
        this.mTxTimeMs = java.util.Arrays.copyOfRange(jArr3, 0, java.lang.Math.min(jArr3.length, android.telephony.ModemActivityInfo.getNumTxPowerLevels()));
        this.mMonitoredRailChargeConsumedMaMs = j10;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mLoggingDurationMs);
        parcel.writeLong(this.mKernelActiveTimeMs);
        parcel.writeLong(this.mNumPacketsTx);
        parcel.writeLong(this.mNumBytesTx);
        parcel.writeLong(this.mNumPacketsRx);
        parcel.writeLong(this.mNumBytesRx);
        parcel.writeLong(this.mSleepTimeMs);
        parcel.writeLong(this.mIdleTimeMs);
        parcel.writeLong(this.mRxTimeMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLongArray(this.mTimeInRatMs);
        parcel.writeLongArray(this.mTimeInRxSignalStrengthLevelMs);
        parcel.writeLongArray(this.mTxTimeMs);
        parcel.writeLong(this.mMonitoredRailChargeConsumedMaMs);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.connectivity.CellularBatteryStats)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.os.connectivity.CellularBatteryStats cellularBatteryStats = (android.os.connectivity.CellularBatteryStats) obj;
        return this.mLoggingDurationMs == cellularBatteryStats.mLoggingDurationMs && this.mKernelActiveTimeMs == cellularBatteryStats.mKernelActiveTimeMs && this.mNumPacketsTx == cellularBatteryStats.mNumPacketsTx && this.mNumBytesTx == cellularBatteryStats.mNumBytesTx && this.mNumPacketsRx == cellularBatteryStats.mNumPacketsRx && this.mNumBytesRx == cellularBatteryStats.mNumBytesRx && this.mSleepTimeMs == cellularBatteryStats.mSleepTimeMs && this.mIdleTimeMs == cellularBatteryStats.mIdleTimeMs && this.mRxTimeMs == cellularBatteryStats.mRxTimeMs && this.mEnergyConsumedMaMs == cellularBatteryStats.mEnergyConsumedMaMs && java.util.Arrays.equals(this.mTimeInRatMs, cellularBatteryStats.mTimeInRatMs) && java.util.Arrays.equals(this.mTimeInRxSignalStrengthLevelMs, cellularBatteryStats.mTimeInRxSignalStrengthLevelMs) && java.util.Arrays.equals(this.mTxTimeMs, cellularBatteryStats.mTxTimeMs) && this.mMonitoredRailChargeConsumedMaMs == cellularBatteryStats.mMonitoredRailChargeConsumedMaMs;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mLoggingDurationMs), java.lang.Long.valueOf(this.mKernelActiveTimeMs), java.lang.Long.valueOf(this.mNumPacketsTx), java.lang.Long.valueOf(this.mNumBytesTx), java.lang.Long.valueOf(this.mNumPacketsRx), java.lang.Long.valueOf(this.mNumBytesRx), java.lang.Long.valueOf(this.mSleepTimeMs), java.lang.Long.valueOf(this.mIdleTimeMs), java.lang.Long.valueOf(this.mRxTimeMs), java.lang.Long.valueOf(this.mEnergyConsumedMaMs), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTimeInRatMs)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTimeInRxSignalStrengthLevelMs)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTxTimeMs)), java.lang.Long.valueOf(this.mMonitoredRailChargeConsumedMaMs));
    }

    public long getLoggingDurationMillis() {
        return this.mLoggingDurationMs;
    }

    public long getKernelActiveTimeMillis() {
        return this.mKernelActiveTimeMs;
    }

    public long getNumPacketsTx() {
        return this.mNumPacketsTx;
    }

    public long getNumBytesTx() {
        return this.mNumBytesTx;
    }

    public long getNumPacketsRx() {
        return this.mNumPacketsRx;
    }

    public long getNumBytesRx() {
        return this.mNumBytesRx;
    }

    public long getSleepTimeMillis() {
        return this.mSleepTimeMs;
    }

    public long getIdleTimeMillis() {
        return this.mIdleTimeMs;
    }

    public long getRxTimeMillis() {
        return this.mRxTimeMs;
    }

    public long getEnergyConsumedMaMillis() {
        return this.mEnergyConsumedMaMs;
    }

    public long getTimeInRatMicros(int i) {
        if (i >= this.mTimeInRatMs.length) {
            return -1L;
        }
        return this.mTimeInRatMs[i];
    }

    public long getTimeInRxSignalStrengthLevelMicros(int i) {
        if (i >= this.mTimeInRxSignalStrengthLevelMs.length) {
            return -1L;
        }
        return this.mTimeInRxSignalStrengthLevelMs[i];
    }

    public long getTxTimeMillis(int i) {
        if (i >= this.mTxTimeMs.length) {
            return -1L;
        }
        return this.mTxTimeMs[i];
    }

    public long getMonitoredRailChargeConsumedMaMillis() {
        return this.mMonitoredRailChargeConsumedMaMs;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
