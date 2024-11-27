package android.app.pinner;

/* loaded from: classes.dex */
public final class PinnedFileStat implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.pinner.PinnedFileStat> CREATOR = new android.os.Parcelable.Creator<android.app.pinner.PinnedFileStat>() { // from class: android.app.pinner.PinnedFileStat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.pinner.PinnedFileStat createFromParcel(android.os.Parcel parcel) {
            return new android.app.pinner.PinnedFileStat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.pinner.PinnedFileStat[] newArray(int i) {
            return new android.app.pinner.PinnedFileStat[i];
        }
    };
    private long bytesPinned;
    private java.lang.String filename;
    private java.lang.String groupName;

    public long getBytesPinned() {
        return this.bytesPinned;
    }

    public java.lang.String getFilename() {
        return this.filename;
    }

    public java.lang.String getGroupName() {
        return this.groupName;
    }

    public PinnedFileStat(java.lang.String str, long j, java.lang.String str2) {
        this.filename = str;
        this.bytesPinned = j;
        this.groupName = str2;
    }

    private PinnedFileStat(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.filename);
        parcel.writeLong(this.bytesPinned);
        parcel.writeString8(this.groupName);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.filename = parcel.readString8();
        this.bytesPinned = parcel.readLong();
        this.groupName = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
