package android.apphibernation;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class HibernationStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.apphibernation.HibernationStats> CREATOR = new android.os.Parcelable.Creator<android.apphibernation.HibernationStats>() { // from class: android.apphibernation.HibernationStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.apphibernation.HibernationStats createFromParcel(android.os.Parcel parcel) {
            return new android.apphibernation.HibernationStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.apphibernation.HibernationStats[] newArray(int i) {
            return new android.apphibernation.HibernationStats[i];
        }
    };
    private final long mDiskBytesSaved;

    public HibernationStats(long j) {
        this.mDiskBytesSaved = j;
    }

    private HibernationStats(android.os.Parcel parcel) {
        this.mDiskBytesSaved = parcel.readLong();
    }

    public long getDiskBytesSaved() {
        return this.mDiskBytesSaved;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mDiskBytesSaved);
    }
}
