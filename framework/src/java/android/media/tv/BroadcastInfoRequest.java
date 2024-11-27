package android.media.tv;

/* loaded from: classes2.dex */
public abstract class BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.BroadcastInfoRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.BroadcastInfoRequest>() { // from class: android.media.tv.BroadcastInfoRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.BroadcastInfoRequest createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            switch (readInt) {
                case 1:
                    return android.media.tv.TsRequest.createFromParcelBody(parcel);
                case 2:
                    return android.media.tv.TableRequest.createFromParcelBody(parcel);
                case 3:
                    return android.media.tv.SectionRequest.createFromParcelBody(parcel);
                case 4:
                    return android.media.tv.PesRequest.createFromParcelBody(parcel);
                case 5:
                    return android.media.tv.StreamEventRequest.createFromParcelBody(parcel);
                case 6:
                    return android.media.tv.DsmccRequest.createFromParcelBody(parcel);
                case 7:
                    return android.media.tv.CommandRequest.createFromParcelBody(parcel);
                case 8:
                    return android.media.tv.TimelineRequest.createFromParcelBody(parcel);
                case 9:
                    return android.media.tv.SignalingDataRequest.createFromParcelBody(parcel);
                default:
                    throw new java.lang.IllegalStateException("Unexpected broadcast info request type (value " + readInt + ") in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.BroadcastInfoRequest[] newArray(int i) {
            return new android.media.tv.BroadcastInfoRequest[i];
        }
    };
    public static final int REQUEST_OPTION_AUTO_UPDATE = 1;
    public static final int REQUEST_OPTION_ONESHOT = 3;
    public static final int REQUEST_OPTION_ONEWAY = 2;
    public static final int REQUEST_OPTION_REPEAT = 0;
    private final int mOption;
    private final int mRequestId;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestOption {
    }

    BroadcastInfoRequest(int i, int i2, int i3) {
        this.mType = i;
        this.mRequestId = i2;
        this.mOption = i3;
    }

    BroadcastInfoRequest(int i, android.os.Parcel parcel) {
        this.mType = i;
        this.mRequestId = parcel.readInt();
        this.mOption = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getOption() {
        return this.mOption;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mOption);
    }
}
