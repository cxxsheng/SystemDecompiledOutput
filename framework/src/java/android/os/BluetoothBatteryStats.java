package android.os;

/* loaded from: classes3.dex */
public class BluetoothBatteryStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.BluetoothBatteryStats> CREATOR = new android.os.Parcelable.Creator<android.os.BluetoothBatteryStats>() { // from class: android.os.BluetoothBatteryStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BluetoothBatteryStats createFromParcel(android.os.Parcel parcel) {
            return new android.os.BluetoothBatteryStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BluetoothBatteryStats[] newArray(int i) {
            return new android.os.BluetoothBatteryStats[i];
        }
    };
    private final java.util.List<android.os.BluetoothBatteryStats.UidStats> mUidStats;

    public static class UidStats {
        public final long rxTimeMs;
        public final int scanResultCount;
        public final long scanTimeMs;
        public final long txTimeMs;
        public final int uid;
        public final long unoptimizedScanTimeMs;

        public UidStats(int i, long j, long j2, int i2, long j3, long j4) {
            this.uid = i;
            this.scanTimeMs = j;
            this.unoptimizedScanTimeMs = j2;
            this.scanResultCount = i2;
            this.rxTimeMs = j3;
            this.txTimeMs = j4;
        }

        private UidStats(android.os.Parcel parcel) {
            this.uid = parcel.readInt();
            this.scanTimeMs = parcel.readLong();
            this.unoptimizedScanTimeMs = parcel.readLong();
            this.scanResultCount = parcel.readInt();
            this.rxTimeMs = parcel.readLong();
            this.txTimeMs = parcel.readLong();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.uid);
            parcel.writeLong(this.scanTimeMs);
            parcel.writeLong(this.unoptimizedScanTimeMs);
            parcel.writeInt(this.scanResultCount);
            parcel.writeLong(this.rxTimeMs);
            parcel.writeLong(this.txTimeMs);
        }

        public java.lang.String toString() {
            return "UidStats{uid=" + this.uid + ", scanTimeMs=" + this.scanTimeMs + ", unoptimizedScanTimeMs=" + this.unoptimizedScanTimeMs + ", scanResultCount=" + this.scanResultCount + ", rxTimeMs=" + this.rxTimeMs + ", txTimeMs=" + this.txTimeMs + '}';
        }
    }

    public BluetoothBatteryStats(java.util.List<android.os.BluetoothBatteryStats.UidStats> list) {
        this.mUidStats = list;
    }

    public java.util.List<android.os.BluetoothBatteryStats.UidStats> getUidStats() {
        return this.mUidStats;
    }

    protected BluetoothBatteryStats(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mUidStats = new java.util.ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mUidStats.add(new android.os.BluetoothBatteryStats.UidStats(parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mUidStats.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.mUidStats.get(i2).writeToParcel(parcel);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
