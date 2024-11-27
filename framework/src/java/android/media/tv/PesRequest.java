package android.media.tv;

/* loaded from: classes2.dex */
public final class PesRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.PesRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.PesRequest>() { // from class: android.media.tv.PesRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.PesRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.PesRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.PesRequest[] newArray(int i) {
            return new android.media.tv.PesRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 4;
    private final int mStreamId;
    private final int mTsPid;

    static android.media.tv.PesRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.PesRequest(parcel);
    }

    public PesRequest(int i, int i2, int i3, int i4) {
        super(4, i, i2);
        this.mTsPid = i3;
        this.mStreamId = i4;
    }

    PesRequest(android.os.Parcel parcel) {
        super(4, parcel);
        this.mTsPid = parcel.readInt();
        this.mStreamId = parcel.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTsPid);
        parcel.writeInt(this.mStreamId);
    }
}
