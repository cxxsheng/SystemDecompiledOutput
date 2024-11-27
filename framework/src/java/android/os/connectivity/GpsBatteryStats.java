package android.os.connectivity;

/* loaded from: classes3.dex */
public final class GpsBatteryStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.connectivity.GpsBatteryStats> CREATOR = new android.os.Parcelable.Creator<android.os.connectivity.GpsBatteryStats>() { // from class: android.os.connectivity.GpsBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.GpsBatteryStats createFromParcel(android.os.Parcel parcel) {
            return new android.os.connectivity.GpsBatteryStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.connectivity.GpsBatteryStats[] newArray(int i) {
            return new android.os.connectivity.GpsBatteryStats[i];
        }
    };
    private long mEnergyConsumedMaMs;
    private long mLoggingDurationMs;
    private long[] mTimeInGpsSignalQualityLevel;

    public GpsBatteryStats() {
        initialize();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mLoggingDurationMs);
        parcel.writeLong(this.mEnergyConsumedMaMs);
        parcel.writeLongArray(this.mTimeInGpsSignalQualityLevel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mLoggingDurationMs = parcel.readLong();
        this.mEnergyConsumedMaMs = parcel.readLong();
        parcel.readLongArray(this.mTimeInGpsSignalQualityLevel);
    }

    public long getLoggingDurationMs() {
        return this.mLoggingDurationMs;
    }

    public long getEnergyConsumedMaMs() {
        return this.mEnergyConsumedMaMs;
    }

    public long[] getTimeInGpsSignalQualityLevel() {
        return this.mTimeInGpsSignalQualityLevel;
    }

    public void setLoggingDurationMs(long j) {
        this.mLoggingDurationMs = j;
    }

    public void setEnergyConsumedMaMs(long j) {
        this.mEnergyConsumedMaMs = j;
    }

    public void setTimeInGpsSignalQualityLevel(long[] jArr) {
        this.mTimeInGpsSignalQualityLevel = java.util.Arrays.copyOfRange(jArr, 0, java.lang.Math.min(jArr.length, 2));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private GpsBatteryStats(android.os.Parcel parcel) {
        initialize();
        readFromParcel(parcel);
    }

    private void initialize() {
        this.mLoggingDurationMs = 0L;
        this.mEnergyConsumedMaMs = 0L;
        this.mTimeInGpsSignalQualityLevel = new long[2];
    }
}
