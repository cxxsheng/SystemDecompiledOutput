package android.media.tv;

/* loaded from: classes2.dex */
public final class StreamEventRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.StreamEventRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.StreamEventRequest>() { // from class: android.media.tv.StreamEventRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.StreamEventRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.StreamEventRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.StreamEventRequest[] newArray(int i) {
            return new android.media.tv.StreamEventRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 5;
    private final java.lang.String mEventName;
    private final android.net.Uri mTargetUri;

    static android.media.tv.StreamEventRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.StreamEventRequest(parcel);
    }

    public StreamEventRequest(int i, int i2, android.net.Uri uri, java.lang.String str) {
        super(5, i, i2);
        this.mTargetUri = uri;
        this.mEventName = str;
    }

    StreamEventRequest(android.os.Parcel parcel) {
        super(5, parcel);
        java.lang.String readString = parcel.readString();
        this.mTargetUri = readString == null ? null : android.net.Uri.parse(readString);
        this.mEventName = parcel.readString();
    }

    public android.net.Uri getTargetUri() {
        return this.mTargetUri;
    }

    public java.lang.String getEventName() {
        return this.mEventName;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mTargetUri == null ? null : this.mTargetUri.toString());
        parcel.writeString(this.mEventName);
    }
}
