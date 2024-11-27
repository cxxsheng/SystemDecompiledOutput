package android.media.tv;

/* loaded from: classes2.dex */
public final class TsResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TsResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TsResponse>() { // from class: android.media.tv.TsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TsResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.TsResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TsResponse[] newArray(int i) {
            return new android.media.tv.TsResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 1;
    private final java.lang.String mSharedFilterToken;

    static android.media.tv.TsResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.TsResponse(parcel);
    }

    public TsResponse(int i, int i2, int i3, java.lang.String str) {
        super(1, i, i2, i3);
        this.mSharedFilterToken = str;
    }

    TsResponse(android.os.Parcel parcel) {
        super(1, parcel);
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
