package android.media.tv;

/* loaded from: classes2.dex */
public final class CommandResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.CommandResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.CommandResponse>() { // from class: android.media.tv.CommandResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.CommandResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.CommandResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.CommandResponse[] newArray(int i) {
            return new android.media.tv.CommandResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 7;
    public static final java.lang.String RESPONSE_TYPE_JSON = "json";
    public static final java.lang.String RESPONSE_TYPE_XML = "xml";
    private final java.lang.String mResponse;
    private final java.lang.String mResponseType;

    static android.media.tv.CommandResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.CommandResponse(parcel);
    }

    public CommandResponse(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        super(7, i, i2, i3);
        this.mResponse = str;
        this.mResponseType = str2;
    }

    CommandResponse(android.os.Parcel parcel) {
        super(7, parcel);
        this.mResponse = parcel.readString();
        this.mResponseType = parcel.readString();
    }

    public java.lang.String getResponse() {
        return this.mResponse;
    }

    public java.lang.String getResponseType() {
        return this.mResponseType;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mResponse);
        parcel.writeString(this.mResponseType);
    }
}
