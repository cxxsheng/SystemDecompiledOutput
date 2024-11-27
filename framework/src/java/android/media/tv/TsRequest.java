package android.media.tv;

/* loaded from: classes2.dex */
public final class TsRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TsRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TsRequest>() { // from class: android.media.tv.TsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TsRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.TsRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TsRequest[] newArray(int i) {
            return new android.media.tv.TsRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 1;
    private final int mTsPid;

    static android.media.tv.TsRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.TsRequest(parcel);
    }

    public TsRequest(int i, int i2, int i3) {
        super(1, i, i2);
        this.mTsPid = i3;
    }

    TsRequest(android.os.Parcel parcel) {
        super(1, parcel);
        this.mTsPid = parcel.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTsPid);
    }
}
