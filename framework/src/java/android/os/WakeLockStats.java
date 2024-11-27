package android.os;

/* loaded from: classes3.dex */
public final class WakeLockStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.WakeLockStats> CREATOR = new android.os.Parcelable.Creator<android.os.WakeLockStats>() { // from class: android.os.WakeLockStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.WakeLockStats createFromParcel(android.os.Parcel parcel) {
            return new android.os.WakeLockStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.WakeLockStats[] newArray(int i) {
            return new android.os.WakeLockStats[i];
        }
    };
    private final java.util.List<android.os.WakeLockStats.WakeLock> mAggregatedWakeLocks;
    private final java.util.List<android.os.WakeLockStats.WakeLock> mWakeLocks;

    public static class WakeLockData {
        public static final android.os.WakeLockStats.WakeLockData EMPTY = new android.os.WakeLockStats.WakeLockData(0, 0, 0);
        public final long timeHeldMs;
        public final int timesAcquired;
        public final long totalTimeHeldMs;

        public WakeLockData(int i, long j, long j2) {
            this.timesAcquired = i;
            this.totalTimeHeldMs = j;
            this.timeHeldMs = j2;
        }

        public boolean isDataValid() {
            return isEmpty() || (this.timesAcquired > 0 && (this.totalTimeHeldMs > 0L ? 1 : (this.totalTimeHeldMs == 0L ? 0 : -1)) > 0 && (this.timeHeldMs > 0L ? 1 : (this.timeHeldMs == 0L ? 0 : -1)) >= 0 && (this.totalTimeHeldMs > this.timeHeldMs ? 1 : (this.totalTimeHeldMs == this.timeHeldMs ? 0 : -1)) >= 0);
        }

        private boolean isEmpty() {
            return this.timesAcquired == 0 && this.totalTimeHeldMs == 0 && this.timeHeldMs == 0;
        }

        private WakeLockData(android.os.Parcel parcel) {
            this.timesAcquired = parcel.readInt();
            this.totalTimeHeldMs = parcel.readLong();
            this.timeHeldMs = parcel.readLong();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.timesAcquired);
            parcel.writeLong(this.totalTimeHeldMs);
            parcel.writeLong(this.timeHeldMs);
        }

        public java.lang.String toString() {
            return "WakeLockData{timesAcquired=" + this.timesAcquired + ", totalTimeHeldMs=" + this.totalTimeHeldMs + ", timeHeldMs=" + this.timeHeldMs + "}";
        }
    }

    public static class WakeLock {
        public static final java.lang.String NAME_AGGREGATED = "wakelockstats_aggregated";
        public final android.os.WakeLockStats.WakeLockData backgroundWakeLockData;
        public final boolean isAggregated;
        public final java.lang.String name;
        public final android.os.WakeLockStats.WakeLockData totalWakeLockData;
        public final int uid;

        public WakeLock(int i, java.lang.String str, boolean z, android.os.WakeLockStats.WakeLockData wakeLockData, android.os.WakeLockStats.WakeLockData wakeLockData2) {
            this.uid = i;
            this.name = str;
            this.isAggregated = z;
            this.totalWakeLockData = wakeLockData;
            this.backgroundWakeLockData = wakeLockData2;
        }

        public static boolean isDataValid(android.os.WakeLockStats.WakeLockData wakeLockData, android.os.WakeLockStats.WakeLockData wakeLockData2) {
            return wakeLockData.totalTimeHeldMs > 0 && wakeLockData.isDataValid() && wakeLockData2.isDataValid() && wakeLockData.timesAcquired >= wakeLockData2.timesAcquired && wakeLockData.totalTimeHeldMs >= wakeLockData2.totalTimeHeldMs && wakeLockData.timeHeldMs >= wakeLockData2.timeHeldMs;
        }

        private WakeLock(android.os.Parcel parcel) {
            this.uid = parcel.readInt();
            this.name = parcel.readString();
            this.isAggregated = parcel.readBoolean();
            this.totalWakeLockData = new android.os.WakeLockStats.WakeLockData(parcel);
            this.backgroundWakeLockData = new android.os.WakeLockStats.WakeLockData(parcel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.uid);
            parcel.writeString(this.name);
            parcel.writeBoolean(this.isAggregated);
            this.totalWakeLockData.writeToParcel(parcel);
            this.backgroundWakeLockData.writeToParcel(parcel);
        }

        public java.lang.String toString() {
            return "WakeLock{uid=" + this.uid + ", name='" + this.name + android.text.format.DateFormat.QUOTE + ", isAggregated=" + this.isAggregated + ", totalWakeLockData=" + this.totalWakeLockData + ", backgroundWakeLockData=" + this.backgroundWakeLockData + '}';
        }
    }

    public WakeLockStats(java.util.List<android.os.WakeLockStats.WakeLock> list, java.util.List<android.os.WakeLockStats.WakeLock> list2) {
        this.mWakeLocks = list;
        this.mAggregatedWakeLocks = list2;
    }

    public java.util.List<android.os.WakeLockStats.WakeLock> getWakeLocks() {
        return this.mWakeLocks;
    }

    public java.util.List<android.os.WakeLockStats.WakeLock> getAggregatedWakeLocks() {
        return this.mAggregatedWakeLocks;
    }

    private WakeLockStats(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mWakeLocks = new java.util.ArrayList(readInt);
        int i = 0;
        while (true) {
            if (i >= readInt) {
                break;
            }
            this.mWakeLocks.add(new android.os.WakeLockStats.WakeLock(parcel));
            i++;
        }
        int readInt2 = parcel.readInt();
        this.mAggregatedWakeLocks = new java.util.ArrayList(readInt2);
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mAggregatedWakeLocks.add(new android.os.WakeLockStats.WakeLock(parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mWakeLocks.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.mWakeLocks.get(i2).writeToParcel(parcel);
        }
        int size2 = this.mAggregatedWakeLocks.size();
        parcel.writeInt(size2);
        for (int i3 = 0; i3 < size2; i3++) {
            this.mAggregatedWakeLocks.get(i3).writeToParcel(parcel);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "WakeLockStats{mWakeLocks: [" + this.mWakeLocks + "], mAggregatedWakeLocks: [" + this.mAggregatedWakeLocks + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END + '}';
    }
}
