package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NanoAppMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoAppMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoAppMessage>() { // from class: android.hardware.location.NanoAppMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppMessage createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoAppMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppMessage[] newArray(int i) {
            return new android.hardware.location.NanoAppMessage[i];
        }
    };
    private static final int DEBUG_LOG_NUM_BYTES = 16;
    private boolean mIsBroadcasted;
    private boolean mIsReliable;
    private byte[] mMessageBody;
    private int mMessageSequenceNumber;
    private int mMessageType;
    private long mNanoAppId;

    private NanoAppMessage(long j, int i, byte[] bArr, boolean z, boolean z2, int i2) {
        this.mNanoAppId = j;
        this.mMessageType = i;
        this.mMessageBody = bArr;
        this.mIsBroadcasted = z;
        this.mIsReliable = z2;
        this.mMessageSequenceNumber = i2;
    }

    public static android.hardware.location.NanoAppMessage createMessageToNanoApp(long j, int i, byte[] bArr) {
        return new android.hardware.location.NanoAppMessage(j, i, bArr, false, false, 0);
    }

    public static android.hardware.location.NanoAppMessage createMessageFromNanoApp(long j, int i, byte[] bArr, boolean z) {
        return new android.hardware.location.NanoAppMessage(j, i, bArr, z, false, 0);
    }

    public static android.hardware.location.NanoAppMessage createMessageFromNanoApp(long j, int i, byte[] bArr, boolean z, boolean z2, int i2) {
        return new android.hardware.location.NanoAppMessage(j, i, bArr, z, z2, i2);
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public byte[] getMessageBody() {
        return this.mMessageBody;
    }

    public boolean isBroadcastMessage() {
        return this.mIsBroadcasted;
    }

    public boolean isReliable() {
        return this.mIsReliable;
    }

    public int getMessageSequenceNumber() {
        return this.mMessageSequenceNumber;
    }

    public void setIsReliable(boolean z) {
        this.mIsReliable = z;
    }

    public void setMessageSequenceNumber(int i) {
        this.mMessageSequenceNumber = i;
    }

    private NanoAppMessage(android.os.Parcel parcel) {
        this.mNanoAppId = parcel.readLong();
        this.mIsBroadcasted = parcel.readInt() == 1;
        this.mMessageType = parcel.readInt();
        this.mMessageBody = new byte[parcel.readInt()];
        parcel.readByteArray(this.mMessageBody);
        this.mIsReliable = parcel.readInt() == 1;
        this.mMessageSequenceNumber = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt(this.mIsBroadcasted ? 1 : 0);
        parcel.writeInt(this.mMessageType);
        parcel.writeInt(this.mMessageBody.length);
        parcel.writeByteArray(this.mMessageBody);
        parcel.writeInt(this.mIsReliable ? 1 : 0);
        parcel.writeInt(this.mMessageSequenceNumber);
    }

    public java.lang.String toString() {
        int length = this.mMessageBody.length;
        java.lang.String str = "NanoAppMessage[type = " + this.mMessageType + ", length = " + this.mMessageBody.length + " bytes, " + (this.mIsBroadcasted ? android.hardware.hdmi.HdmiControlManager.POWER_CONTROL_MODE_BROADCAST : "unicast") + ", nanoapp = 0x" + java.lang.Long.toHexString(this.mNanoAppId) + ", isReliable = " + (this.mIsReliable ? "true" : "false") + ", messageSequenceNumber = " + this.mMessageSequenceNumber + "](";
        if (length > 0) {
            str = str + "data = 0x";
        }
        int i = 0;
        while (i < java.lang.Math.min(length, 16)) {
            str = str + libcore.util.HexEncoding.encodeToString(this.mMessageBody[i], true);
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

    public boolean equals(java.lang.Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.location.NanoAppMessage)) {
            return false;
        }
        android.hardware.location.NanoAppMessage nanoAppMessage = (android.hardware.location.NanoAppMessage) obj;
        if (nanoAppMessage.getNanoAppId() != this.mNanoAppId || nanoAppMessage.getMessageType() != this.mMessageType || nanoAppMessage.isBroadcastMessage() != this.mIsBroadcasted || !java.util.Arrays.equals(nanoAppMessage.getMessageBody(), this.mMessageBody) || ((android.chre.flags.Flags.reliableMessage() && nanoAppMessage.isReliable() != this.mIsReliable) || (android.chre.flags.Flags.reliableMessage() && nanoAppMessage.getMessageSequenceNumber() != this.mMessageSequenceNumber))) {
            z = false;
        }
        return z;
    }
}
