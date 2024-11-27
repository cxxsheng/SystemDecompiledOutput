package android.media.tv;

/* loaded from: classes2.dex */
public final class PesResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.PesResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.PesResponse>() { // from class: android.media.tv.PesResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.PesResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.PesResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.PesResponse[] newArray(int i) {
            return new android.media.tv.PesResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 4;
    private final java.lang.String mSharedFilterToken;

    static android.media.tv.PesResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.PesResponse(parcel);
    }

    public PesResponse(int i, int i2, int i3, java.lang.String str) {
        super(4, i, i2, i3);
        this.mSharedFilterToken = str;
    }

    PesResponse(android.os.Parcel parcel) {
        super(4, parcel);
        this.mSharedFilterToken = parcel.readString();
    }

    public java.lang.String getSharedFilterToken() {
        return this.mSharedFilterToken;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mSharedFilterToken);
    }
}
