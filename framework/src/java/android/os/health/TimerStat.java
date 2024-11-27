package android.os.health;

/* loaded from: classes3.dex */
public final class TimerStat implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.health.TimerStat> CREATOR = new android.os.Parcelable.Creator<android.os.health.TimerStat>() { // from class: android.os.health.TimerStat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.health.TimerStat createFromParcel(android.os.Parcel parcel) {
            return new android.os.health.TimerStat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.health.TimerStat[] newArray(int i) {
            return new android.os.health.TimerStat[i];
        }
    };
    private int mCount;
    private long mTime;

    public TimerStat() {
    }

    public TimerStat(int i, long j) {
        this.mCount = i;
        this.mTime = j;
    }

    public TimerStat(android.os.Parcel parcel) {
        this.mCount = parcel.readInt();
        this.mTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCount);
        parcel.writeLong(this.mTime);
    }

    public void setCount(int i) {
        this.mCount = i;
    }

    public int getCount() {
        return this.mCount;
    }

    public void setTime(long j) {
        this.mTime = j;
    }

    public long getTime() {
        return this.mTime;
    }
}
