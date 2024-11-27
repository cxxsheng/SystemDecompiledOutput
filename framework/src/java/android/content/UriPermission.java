package android.content;

/* loaded from: classes.dex */
public final class UriPermission implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.UriPermission> CREATOR = new android.os.Parcelable.Creator<android.content.UriPermission>() { // from class: android.content.UriPermission.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.UriPermission createFromParcel(android.os.Parcel parcel) {
            return new android.content.UriPermission(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.UriPermission[] newArray(int i) {
            return new android.content.UriPermission[i];
        }
    };
    public static final long INVALID_TIME = Long.MIN_VALUE;
    private final int mModeFlags;
    private final long mPersistedTime;
    private final android.net.Uri mUri;

    public UriPermission(android.net.Uri uri, int i, long j) {
        this.mUri = uri;
        this.mModeFlags = i;
        this.mPersistedTime = j;
    }

    public UriPermission(android.os.Parcel parcel) {
        this.mUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.mModeFlags = parcel.readInt();
        this.mPersistedTime = parcel.readLong();
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public boolean isReadPermission() {
        return (this.mModeFlags & 1) != 0;
    }

    public boolean isWritePermission() {
        return (this.mModeFlags & 2) != 0;
    }

    public long getPersistedTime() {
        return this.mPersistedTime;
    }

    public java.lang.String toString() {
        return "UriPermission {uri=" + this.mUri + ", modeFlags=" + this.mModeFlags + ", persistedTime=" + this.mPersistedTime + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mUri, i);
        parcel.writeInt(this.mModeFlags);
        parcel.writeLong(this.mPersistedTime);
    }
}
