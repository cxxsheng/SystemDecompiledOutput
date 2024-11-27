package android.os;

/* loaded from: classes3.dex */
public final class CpuUsageInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.CpuUsageInfo> CREATOR = new android.os.Parcelable.Creator<android.os.CpuUsageInfo>() { // from class: android.os.CpuUsageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CpuUsageInfo createFromParcel(android.os.Parcel parcel) {
            return new android.os.CpuUsageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CpuUsageInfo[] newArray(int i) {
            return new android.os.CpuUsageInfo[i];
        }
    };
    private long mActive;
    private long mTotal;

    public CpuUsageInfo(long j, long j2) {
        this.mActive = j;
        this.mTotal = j2;
    }

    private CpuUsageInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public long getActive() {
        return this.mActive;
    }

    public long getTotal() {
        return this.mTotal;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mActive);
        parcel.writeLong(this.mTotal);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mActive = parcel.readLong();
        this.mTotal = parcel.readLong();
    }
}
