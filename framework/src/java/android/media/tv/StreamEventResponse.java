package android.media.tv;

/* loaded from: classes2.dex */
public final class StreamEventResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.StreamEventResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.StreamEventResponse>() { // from class: android.media.tv.StreamEventResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.StreamEventResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.StreamEventResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.StreamEventResponse[] newArray(int i) {
            return new android.media.tv.StreamEventResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 5;
    private final byte[] mData;
    private final int mEventId;
    private final long mNptMillis;

    static android.media.tv.StreamEventResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.StreamEventResponse(parcel);
    }

    public StreamEventResponse(int i, int i2, int i3, int i4, long j, byte[] bArr) {
        super(5, i, i2, i3);
        this.mEventId = i4;
        this.mNptMillis = j;
        this.mData = bArr;
    }

    private StreamEventResponse(android.os.Parcel parcel) {
        super(5, parcel);
        this.mEventId = parcel.readInt();
        this.mNptMillis = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mData = new byte[readInt];
            parcel.readByteArray(this.mData);
        } else {
            this.mData = null;
        }
    }

    public int getEventId() {
        return this.mEventId;
    }

    public long getNptMillis() {
        return this.mNptMillis;
    }

    public byte[] getData() {
        return this.mData;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mEventId);
        parcel.writeLong(this.mNptMillis);
        if (this.mData != null && this.mData.length > 0) {
            parcel.writeInt(this.mData.length);
            parcel.writeByteArray(this.mData);
        } else {
            parcel.writeInt(0);
        }
    }
}
