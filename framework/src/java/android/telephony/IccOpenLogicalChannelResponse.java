package android.telephony;

/* loaded from: classes3.dex */
public class IccOpenLogicalChannelResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.IccOpenLogicalChannelResponse> CREATOR = new android.os.Parcelable.Creator<android.telephony.IccOpenLogicalChannelResponse>() { // from class: android.telephony.IccOpenLogicalChannelResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.IccOpenLogicalChannelResponse createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.IccOpenLogicalChannelResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.IccOpenLogicalChannelResponse[] newArray(int i) {
            return new android.telephony.IccOpenLogicalChannelResponse[i];
        }
    };
    public static final int INVALID_CHANNEL = -1;
    public static final int STATUS_MISSING_RESOURCE = 2;
    public static final int STATUS_NO_ERROR = 1;
    public static final int STATUS_NO_SUCH_ELEMENT = 3;
    public static final int STATUS_UNKNOWN_ERROR = 4;
    private final int mChannel;
    private final byte[] mSelectResponse;
    private final int mStatus;

    public IccOpenLogicalChannelResponse(int i, int i2, byte[] bArr) {
        this.mChannel = i;
        this.mStatus = i2;
        this.mSelectResponse = bArr;
    }

    private IccOpenLogicalChannelResponse(android.os.Parcel parcel) {
        this.mChannel = parcel.readInt();
        this.mStatus = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mSelectResponse = new byte[readInt];
            parcel.readByteArray(this.mSelectResponse);
        } else {
            this.mSelectResponse = null;
        }
    }

    public int getChannel() {
        return this.mChannel;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public byte[] getSelectResponse() {
        return this.mSelectResponse;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mChannel);
        parcel.writeInt(this.mStatus);
        if (this.mSelectResponse != null && this.mSelectResponse.length > 0) {
            parcel.writeInt(this.mSelectResponse.length);
            parcel.writeByteArray(this.mSelectResponse);
        } else {
            parcel.writeInt(0);
        }
    }

    public java.lang.String toString() {
        return "Channel: " + this.mChannel + " Status: " + this.mStatus;
    }
}
