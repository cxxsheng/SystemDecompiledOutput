package android.location;

/* loaded from: classes2.dex */
public final class LocationTime implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.location.LocationTime> CREATOR = new android.os.Parcelable.Creator<android.location.LocationTime>() { // from class: android.location.LocationTime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.location.LocationTime createFromParcel(android.os.Parcel parcel) {
            return new android.location.LocationTime(parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.location.LocationTime[] newArray(int i) {
            return new android.location.LocationTime[i];
        }
    };
    private final long mElapsedRealtimeNanos;
    private final long mUnixEpochTimeMillis;

    public LocationTime(long j, long j2) {
        this.mUnixEpochTimeMillis = j;
        this.mElapsedRealtimeNanos = j2;
    }

    public long getUnixEpochTimeMillis() {
        return this.mUnixEpochTimeMillis;
    }

    public long getElapsedRealtimeNanos() {
        return this.mElapsedRealtimeNanos;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mUnixEpochTimeMillis);
        parcel.writeLong(this.mElapsedRealtimeNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "LocationTime{mUnixEpochTimeMillis=" + java.time.Instant.ofEpochMilli(this.mUnixEpochTimeMillis) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.mUnixEpochTimeMillis + "), mElapsedRealtimeNanos=" + java.time.Duration.ofNanos(this.mElapsedRealtimeNanos) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.mElapsedRealtimeNanos + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + '}';
    }
}
