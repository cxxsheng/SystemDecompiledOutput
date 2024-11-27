package android.media.tv;

/* loaded from: classes2.dex */
public final class SectionResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.SectionResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.SectionResponse>() { // from class: android.media.tv.SectionResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SectionResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.SectionResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SectionResponse[] newArray(int i) {
            return new android.media.tv.SectionResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 3;
    private final android.os.Bundle mSessionData;
    private final int mSessionId;
    private final int mVersion;

    static android.media.tv.SectionResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.SectionResponse(parcel);
    }

    public SectionResponse(int i, int i2, int i3, int i4, int i5, android.os.Bundle bundle) {
        super(3, i, i2, i3);
        this.mSessionId = i4;
        this.mVersion = i5;
        this.mSessionData = bundle;
    }

    SectionResponse(android.os.Parcel parcel) {
        super(3, parcel);
        this.mSessionId = parcel.readInt();
        this.mVersion = parcel.readInt();
        this.mSessionData = parcel.readBundle();
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public android.os.Bundle getSessionData() {
        return this.mSessionData;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mSessionId);
        parcel.writeInt(this.mVersion);
        parcel.writeBundle(this.mSessionData);
    }
}
