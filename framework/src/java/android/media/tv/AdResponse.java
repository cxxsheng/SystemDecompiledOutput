package android.media.tv;

/* loaded from: classes2.dex */
public final class AdResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.AdResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.AdResponse>() { // from class: android.media.tv.AdResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AdResponse createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.AdResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AdResponse[] newArray(int i) {
            return new android.media.tv.AdResponse[i];
        }
    };
    public static final int RESPONSE_TYPE_BUFFERING = 5;
    public static final int RESPONSE_TYPE_ERROR = 4;
    public static final int RESPONSE_TYPE_FINISHED = 2;
    public static final int RESPONSE_TYPE_PLAYING = 1;
    public static final int RESPONSE_TYPE_STOPPED = 3;
    private final long mElapsedTime;
    private final int mId;
    private final int mResponseType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResponseType {
    }

    public AdResponse(int i, int i2, long j) {
        this.mId = i;
        this.mResponseType = i2;
        this.mElapsedTime = j;
    }

    private AdResponse(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mResponseType = parcel.readInt();
        this.mElapsedTime = parcel.readLong();
    }

    public int getId() {
        return this.mId;
    }

    public int getResponseType() {
        return this.mResponseType;
    }

    public long getElapsedTimeMillis() {
        return this.mElapsedTime;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mResponseType);
        parcel.writeLong(this.mElapsedTime);
    }
}
