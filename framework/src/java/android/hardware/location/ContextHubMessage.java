package android.hardware.location;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class ContextHubMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.ContextHubMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.ContextHubMessage>() { // from class: android.hardware.location.ContextHubMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ContextHubMessage createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.ContextHubMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ContextHubMessage[] newArray(int i) {
            return new android.hardware.location.ContextHubMessage[i];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private byte[] mData;
    private int mType;
    private int mVersion;

    public int getMsgType() {
        return this.mType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public byte[] getData() {
        return java.util.Arrays.copyOf(this.mData, this.mData.length);
    }

    public void setMsgType(int i) {
        this.mType = i;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public void setMsgData(byte[] bArr) {
        this.mData = java.util.Arrays.copyOf(bArr, bArr.length);
    }

    public ContextHubMessage(int i, int i2, byte[] bArr) {
        this.mType = i;
        this.mVersion = i2;
        this.mData = java.util.Arrays.copyOf(bArr, bArr.length);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ContextHubMessage(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mVersion = parcel.readInt();
        this.mData = new byte[parcel.readInt()];
        parcel.readByteArray(this.mData);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mData.length);
        parcel.writeByteArray(this.mData);
    }

    public java.lang.String toString() {
        int length = this.mData.length;
        java.lang.String str = "ContextHubMessage[type = " + this.mType + ", length = " + this.mData.length + " bytes](";
        if (length > 0) {
            str = str + "data = 0x";
        }
        int i = 0;
        while (i < java.lang.Math.min(length, 16)) {
            str = str + libcore.util.HexEncoding.encodeToString(this.mData[i], true);
            i++;
            if (i % 4 == 0) {
                str = str + " ";
            }
        }
        if (length > 16) {
            str = str + android.telecom.Logging.Session.TRUNCATE_STRING;
        }
        return str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
