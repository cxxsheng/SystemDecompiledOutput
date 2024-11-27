package android.os.connectivity;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class WifiBatteryStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.connectivity.WifiBatteryStats> CREATOR = new android.os.Parcelable.Creator<android.os.connectivity.WifiBatteryStats>() { // from class: android.os.connectivity.WifiBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.WifiBatteryStats createFromParcel(android.os.Parcel parcel) {
            return new android.os.connectivity.WifiBatteryStats(parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.createLongArray(), parcel.createLongArray(), parcel.createLongArray(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.WifiBatteryStats[] newArray(int i) {
            return new android.os.connectivity.WifiBatteryStats[i];
        }
    };
    private final long mAppScanRequestCount;
    private final long mEnergyConsumedMaMillis;
    private final long mIdleTimeMillis;
    private final long mKernelActiveTimeMillis;
    private final long mLoggingDurationMillis;
    private final long mMonitoredRailChargeConsumedMaMillis;
    private final long mNumBytesRx;
    private final long mNumBytesTx;
    private final long mNumPacketsRx;
    private final long mNumPacketsTx;
    private final long mRxTimeMillis;
    private final long mScanTimeMillis;
    private final long mSleepTimeMillis;
    private final long[] mTimeInRxSignalStrengthLevelMillis;
    private final long[] mTimeInStateMillis;
    private final long[] mTimeInSupplicantStateMillis;
    private final long mTxTimeMillis;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mLoggingDurationMillis);
        parcel.writeLong(this.mKernelActiveTimeMillis);
        parcel.writeLong(this.mNumPacketsTx);
        parcel.writeLong(this.mNumBytesTx);
        parcel.writeLong(this.mNumPacketsRx);
        parcel.writeLong(this.mNumBytesRx);
        parcel.writeLong(this.mSleepTimeMillis);
        parcel.writeLong(this.mScanTimeMillis);
        parcel.writeLong(this.mIdleTimeMillis);
        parcel.writeLong(this.mRxTimeMillis);
        parcel.writeLong(this.mTxTimeMillis);
        parcel.writeLong(this.mEnergyConsumedMaMillis);
        parcel.writeLong(this.mAppScanRequestCount);
        parcel.writeLongArray(this.mTimeInStateMillis);
        parcel.writeLongArray(this.mTimeInRxSignalStrengthLevelMillis);
        parcel.writeLongArray(this.mTimeInSupplicantStateMillis);
        parcel.writeLong(this.mMonitoredRailChargeConsumedMaMillis);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.connectivity.WifiBatteryStats)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.os.connectivity.WifiBatteryStats wifiBatteryStats = (android.os.connectivity.WifiBatteryStats) obj;
        return this.mLoggingDurationMillis == wifiBatteryStats.mLoggingDurationMillis && this.mKernelActiveTimeMillis == wifiBatteryStats.mKernelActiveTimeMillis && this.mNumPacketsTx == wifiBatteryStats.mNumPacketsTx && this.mNumBytesTx == wifiBatteryStats.mNumBytesTx && this.mNumPacketsRx == wifiBatteryStats.mNumPacketsRx && this.mNumBytesRx == wifiBatteryStats.mNumBytesRx && this.mSleepTimeMillis == wifiBatteryStats.mSleepTimeMillis && this.mScanTimeMillis == wifiBatteryStats.mScanTimeMillis && this.mIdleTimeMillis == wifiBatteryStats.mIdleTimeMillis && this.mRxTimeMillis == wifiBatteryStats.mRxTimeMillis && this.mTxTimeMillis == wifiBatteryStats.mTxTimeMillis && this.mEnergyConsumedMaMillis == wifiBatteryStats.mEnergyConsumedMaMillis && this.mAppScanRequestCount == wifiBatteryStats.mAppScanRequestCount && java.util.Arrays.equals(this.mTimeInStateMillis, wifiBatteryStats.mTimeInStateMillis) && java.util.Arrays.equals(this.mTimeInSupplicantStateMillis, wifiBatteryStats.mTimeInSupplicantStateMillis) && java.util.Arrays.equals(this.mTimeInRxSignalStrengthLevelMillis, wifiBatteryStats.mTimeInRxSignalStrengthLevelMillis) && this.mMonitoredRailChargeConsumedMaMillis == wifiBatteryStats.mMonitoredRailChargeConsumedMaMillis;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mLoggingDurationMillis), java.lang.Long.valueOf(this.mKernelActiveTimeMillis), java.lang.Long.valueOf(this.mNumPacketsTx), java.lang.Long.valueOf(this.mNumBytesTx), java.lang.Long.valueOf(this.mNumPacketsRx), java.lang.Long.valueOf(this.mNumBytesRx), java.lang.Long.valueOf(this.mSleepTimeMillis), java.lang.Long.valueOf(this.mScanTimeMillis), java.lang.Long.valueOf(this.mIdleTimeMillis), java.lang.Long.valueOf(this.mRxTimeMillis), java.lang.Long.valueOf(this.mTxTimeMillis), java.lang.Long.valueOf(this.mEnergyConsumedMaMillis), java.lang.Long.valueOf(this.mAppScanRequestCount), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTimeInStateMillis)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTimeInSupplicantStateMillis)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTimeInRxSignalStrengthLevelMillis)), java.lang.Long.valueOf(this.mMonitoredRailChargeConsumedMaMillis));
    }

    public WifiBatteryStats(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long[] jArr, long[] jArr2, long[] jArr3, long j14) {
        this.mLoggingDurationMillis = j;
        this.mKernelActiveTimeMillis = j2;
        this.mNumPacketsTx = j3;
        this.mNumBytesTx = j4;
        this.mNumPacketsRx = j5;
        this.mNumBytesRx = j6;
        this.mSleepTimeMillis = j7;
        this.mScanTimeMillis = j8;
        this.mIdleTimeMillis = j9;
        this.mRxTimeMillis = j10;
        this.mTxTimeMillis = j11;
        this.mEnergyConsumedMaMillis = j12;
        this.mAppScanRequestCount = j13;
        this.mTimeInStateMillis = java.util.Arrays.copyOfRange(jArr, 0, java.lang.Math.min(jArr.length, 8));
        this.mTimeInRxSignalStrengthLevelMillis = java.util.Arrays.copyOfRange(jArr2, 0, java.lang.Math.min(jArr2.length, 5));
        this.mTimeInSupplicantStateMillis = java.util.Arrays.copyOfRange(jArr3, 0, java.lang.Math.min(jArr3.length, 13));
        this.mMonitoredRailChargeConsumedMaMillis = j14;
    }

    public long getLoggingDurationMillis() {
        return this.mLoggingDurationMillis;
    }

    public long getKernelActiveTimeMillis() {
        return this.mKernelActiveTimeMillis;
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
        return this.mSleepTimeMillis;
    }

    public long getScanTimeMillis() {
        return this.mScanTimeMillis;
    }

    public long getIdleTimeMillis() {
        return this.mIdleTimeMillis;
    }

    public long getRxTimeMillis() {
        return this.mRxTimeMillis;
    }

    public long getTxTimeMillis() {
        return this.mTxTimeMillis;
    }

    public long getEnergyConsumedMaMillis() {
        return this.mEnergyConsumedMaMillis;
    }

    public long getAppScanRequestCount() {
        return this.mAppScanRequestCount;
    }

    public long getMonitoredRailChargeConsumedMaMillis() {
        return this.mMonitoredRailChargeConsumedMaMillis;
    }
}
