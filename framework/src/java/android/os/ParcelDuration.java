package android.os;

/* loaded from: classes3.dex */
public final class ParcelDuration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ParcelDuration> CREATOR = new android.os.Parcelable.Creator<android.os.ParcelDuration>() { // from class: android.os.ParcelDuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelDuration createFromParcel(android.os.Parcel parcel) {
            return new android.os.ParcelDuration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelDuration[] newArray(int i) {
            return new android.os.ParcelDuration[i];
        }
    };
    private final int mNanos;
    private final long mSeconds;

    public ParcelDuration(long j) {
        this(java.time.Duration.ofMillis(j));
    }

    public ParcelDuration(java.time.Duration duration) {
        this.mSeconds = duration.getSeconds();
        this.mNanos = duration.getNano();
    }

    private ParcelDuration(android.os.Parcel parcel) {
        this.mSeconds = parcel.readLong();
        this.mNanos = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mSeconds);
        parcel.writeInt(this.mNanos);
    }

    public java.time.Duration getDuration() {
        return java.time.Duration.ofSeconds(this.mSeconds, this.mNanos);
    }

    public java.lang.String toString() {
        return getDuration().toString();
    }
}
