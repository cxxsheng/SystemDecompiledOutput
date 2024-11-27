package android.media.tv;

/* loaded from: classes2.dex */
public final class SectionRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.SectionRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.SectionRequest>() { // from class: android.media.tv.SectionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SectionRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.SectionRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SectionRequest[] newArray(int i) {
            return new android.media.tv.SectionRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 3;
    private final int mTableId;
    private final int mTsPid;
    private final int mVersion;

    static android.media.tv.SectionRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.SectionRequest(parcel);
    }

    public SectionRequest(int i, int i2, int i3, int i4, int i5) {
        super(3, i, i2);
        this.mTsPid = i3;
        this.mTableId = i4;
        this.mVersion = i5;
    }

    SectionRequest(android.os.Parcel parcel) {
        super(3, parcel);
        this.mTsPid = parcel.readInt();
        this.mTableId = parcel.readInt();
        this.mVersion = parcel.readInt();
    }

    public int getTsPid() {
        return this.mTsPid;
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTsPid);
        parcel.writeInt(this.mTableId);
        parcel.writeInt(this.mVersion);
    }
}
