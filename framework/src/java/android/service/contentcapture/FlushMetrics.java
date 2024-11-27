package android.service.contentcapture;

/* loaded from: classes3.dex */
public final class FlushMetrics implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.contentcapture.FlushMetrics> CREATOR = new android.os.Parcelable.Creator<android.service.contentcapture.FlushMetrics>() { // from class: android.service.contentcapture.FlushMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.contentcapture.FlushMetrics createFromParcel(android.os.Parcel parcel) {
            android.service.contentcapture.FlushMetrics flushMetrics = new android.service.contentcapture.FlushMetrics();
            flushMetrics.sessionStarted = parcel.readInt();
            flushMetrics.sessionFinished = parcel.readInt();
            flushMetrics.viewAppearedCount = parcel.readInt();
            flushMetrics.viewDisappearedCount = parcel.readInt();
            flushMetrics.viewTextChangedCount = parcel.readInt();
            return flushMetrics;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.contentcapture.FlushMetrics[] newArray(int i) {
            return new android.service.contentcapture.FlushMetrics[i];
        }
    };
    public int sessionFinished;
    public int sessionStarted;
    public int viewAppearedCount;
    public int viewDisappearedCount;
    public int viewTextChangedCount;

    public void reset() {
        this.viewAppearedCount = 0;
        this.viewDisappearedCount = 0;
        this.viewTextChangedCount = 0;
        this.sessionStarted = 0;
        this.sessionFinished = 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.sessionStarted);
        parcel.writeInt(this.sessionFinished);
        parcel.writeInt(this.viewAppearedCount);
        parcel.writeInt(this.viewDisappearedCount);
        parcel.writeInt(this.viewTextChangedCount);
    }
}
