package android.media.tv;

/* loaded from: classes2.dex */
public abstract class BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.BroadcastInfoResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.BroadcastInfoResponse>() { // from class: android.media.tv.BroadcastInfoResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.BroadcastInfoResponse createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            switch (readInt) {
                case 1:
                    return android.media.tv.TsResponse.createFromParcelBody(parcel);
                case 2:
                    return android.media.tv.TableResponse.createFromParcelBody(parcel);
                case 3:
                    return android.media.tv.SectionResponse.createFromParcelBody(parcel);
                case 4:
                    return android.media.tv.PesResponse.createFromParcelBody(parcel);
                case 5:
                    return android.media.tv.StreamEventResponse.createFromParcelBody(parcel);
                case 6:
                    return android.media.tv.DsmccResponse.createFromParcelBody(parcel);
                case 7:
                    return android.media.tv.CommandResponse.createFromParcelBody(parcel);
                case 8:
                    return android.media.tv.TimelineResponse.createFromParcelBody(parcel);
                case 9:
                    return android.media.tv.SignalingDataResponse.createFromParcelBody(parcel);
                default:
                    throw new java.lang.IllegalStateException("Unexpected broadcast info response type (value " + readInt + ") in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.BroadcastInfoResponse[] newArray(int i) {
            return new android.media.tv.BroadcastInfoResponse[i];
        }
    };
    public static final int RESPONSE_RESULT_CANCEL = 3;
    public static final int RESPONSE_RESULT_ERROR = 1;
    public static final int RESPONSE_RESULT_OK = 2;
    private final int mRequestId;
    private final int mResponseResult;
    private final int mSequence;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResponseResult {
    }

    BroadcastInfoResponse(int i, int i2, int i3, int i4) {
        this.mType = i;
        this.mRequestId = i2;
        this.mSequence = i3;
        this.mResponseResult = i4;
    }

    BroadcastInfoResponse(int i, android.os.Parcel parcel) {
        this.mType = i;
        this.mRequestId = parcel.readInt();
        this.mSequence = parcel.readInt();
        this.mResponseResult = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getSequence() {
        return this.mSequence;
    }

    public int getResponseResult() {
        return this.mResponseResult;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mSequence);
        parcel.writeInt(this.mResponseResult);
    }
}
