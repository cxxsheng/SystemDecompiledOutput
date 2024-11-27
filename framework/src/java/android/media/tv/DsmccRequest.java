package android.media.tv;

/* loaded from: classes2.dex */
public final class DsmccRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.DsmccRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.DsmccRequest>() { // from class: android.media.tv.DsmccRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.DsmccRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.DsmccRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.DsmccRequest[] newArray(int i) {
            return new android.media.tv.DsmccRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 6;
    private final android.net.Uri mUri;

    static android.media.tv.DsmccRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.DsmccRequest(parcel);
    }

    public DsmccRequest(int i, int i2, android.net.Uri uri) {
        super(6, i, i2);
        this.mUri = uri;
    }

    DsmccRequest(android.os.Parcel parcel) {
        super(6, parcel);
        java.lang.String readString = parcel.readString();
        this.mUri = readString == null ? null : android.net.Uri.parse(readString);
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mUri == null ? null : this.mUri.toString());
    }
}
