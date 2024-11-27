package android.media.tv;

/* loaded from: classes2.dex */
public final class AdRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.AdRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.AdRequest>() { // from class: android.media.tv.AdRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AdRequest createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.AdRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AdRequest[] newArray(int i) {
            return new android.media.tv.AdRequest[i];
        }
    };
    public static final java.lang.String KEY_AUDIO_METADATA = "key_audio_metadata";
    public static final java.lang.String KEY_VIDEO_METADATA = "key_video_metadata";
    public static final int REQUEST_TYPE_START = 1;
    public static final int REQUEST_TYPE_STOP = 2;
    private final long mEchoInterval;
    private final android.os.ParcelFileDescriptor mFileDescriptor;
    private final int mId;
    private final java.lang.String mMediaFileType;
    private final android.os.Bundle mMetadata;
    private final int mRequestType;
    private final long mStartTime;
    private final long mStopTime;
    private final android.net.Uri mUri;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestType {
    }

    public AdRequest(int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, long j3, java.lang.String str, android.os.Bundle bundle) {
        this(i, i2, parcelFileDescriptor, null, j, j2, j3, str, bundle);
    }

    public AdRequest(int i, int i2, android.net.Uri uri, long j, long j2, long j3, android.os.Bundle bundle) {
        this(i, i2, null, uri, j, j2, j3, null, bundle);
    }

    private AdRequest(int i, int i2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.net.Uri uri, long j, long j2, long j3, java.lang.String str, android.os.Bundle bundle) {
        this.mId = i;
        this.mRequestType = i2;
        this.mFileDescriptor = parcelFileDescriptor;
        this.mStartTime = j;
        this.mStopTime = j2;
        this.mEchoInterval = j3;
        this.mMediaFileType = str;
        this.mMetadata = bundle;
        this.mUri = uri;
    }

    private AdRequest(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mRequestType = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt == 1) {
            this.mFileDescriptor = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
            this.mUri = null;
        } else if (readInt == 2) {
            java.lang.String readString = parcel.readString();
            this.mUri = readString == null ? null : android.net.Uri.parse(readString);
            this.mFileDescriptor = null;
        } else {
            this.mFileDescriptor = null;
            this.mUri = null;
        }
        this.mStartTime = parcel.readLong();
        this.mStopTime = parcel.readLong();
        this.mEchoInterval = parcel.readLong();
        this.mMediaFileType = parcel.readString();
        this.mMetadata = parcel.readBundle();
    }

    public int getId() {
        return this.mId;
    }

    public int getRequestType() {
        return this.mRequestType;
    }

    public android.os.ParcelFileDescriptor getFileDescriptor() {
        return this.mFileDescriptor;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public long getStartTimeMillis() {
        return this.mStartTime;
    }

    public long getStopTimeMillis() {
        return this.mStopTime;
    }

    public long getEchoIntervalMillis() {
        return this.mEchoInterval;
    }

    public java.lang.String getMediaFileType() {
        return this.mMediaFileType;
    }

    public android.os.Bundle getMetadata() {
        return this.mMetadata;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mRequestType);
        if (this.mFileDescriptor != null) {
            parcel.writeInt(1);
            this.mFileDescriptor.writeToParcel(parcel, i);
        } else if (this.mUri != null) {
            parcel.writeInt(2);
            parcel.writeString(this.mUri.toString());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mStartTime);
        parcel.writeLong(this.mStopTime);
        parcel.writeLong(this.mEchoInterval);
        parcel.writeString(this.mMediaFileType);
        parcel.writeBundle(this.mMetadata);
    }
}
