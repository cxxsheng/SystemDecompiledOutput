package android.telephony.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class EuiccNotification implements android.os.Parcelable {
    public static final int ALL_EVENTS = 15;
    public static final android.os.Parcelable.Creator<android.telephony.euicc.EuiccNotification> CREATOR = new android.os.Parcelable.Creator<android.telephony.euicc.EuiccNotification>() { // from class: android.telephony.euicc.EuiccNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.EuiccNotification createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.euicc.EuiccNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.EuiccNotification[] newArray(int i) {
            return new android.telephony.euicc.EuiccNotification[i];
        }
    };
    public static final int EVENT_DELETE = 8;
    public static final int EVENT_DISABLE = 4;
    public static final int EVENT_ENABLE = 2;
    public static final int EVENT_INSTALL = 1;
    private final byte[] mData;
    private final int mEvent;
    private final int mSeq;
    private final java.lang.String mTargetAddr;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Event {
    }

    public EuiccNotification(int i, java.lang.String str, int i2, byte[] bArr) {
        this.mSeq = i;
        this.mTargetAddr = str;
        this.mEvent = i2;
        this.mData = bArr;
    }

    public int getSeq() {
        return this.mSeq;
    }

    public java.lang.String getTargetAddr() {
        return this.mTargetAddr;
    }

    public int getEvent() {
        return this.mEvent;
    }

    public byte[] getData() {
        return this.mData;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.euicc.EuiccNotification euiccNotification = (android.telephony.euicc.EuiccNotification) obj;
        if (this.mSeq == euiccNotification.mSeq && java.util.Objects.equals(this.mTargetAddr, euiccNotification.mTargetAddr) && this.mEvent == euiccNotification.mEvent && java.util.Arrays.equals(this.mData, euiccNotification.mData)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mSeq + 31) * 31) + java.util.Objects.hashCode(this.mTargetAddr)) * 31) + this.mEvent) * 31) + java.util.Arrays.hashCode(this.mData);
    }

    public java.lang.String toString() {
        return "EuiccNotification (seq=" + this.mSeq + ", targetAddr=" + this.mTargetAddr + ", event=" + this.mEvent + ", data=" + (this.mData == null ? "null" : "byte[" + this.mData.length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSeq);
        parcel.writeString(this.mTargetAddr);
        parcel.writeInt(this.mEvent);
        parcel.writeByteArray(this.mData);
    }

    private EuiccNotification(android.os.Parcel parcel) {
        this.mSeq = parcel.readInt();
        this.mTargetAddr = parcel.readString();
        this.mEvent = parcel.readInt();
        this.mData = parcel.createByteArray();
    }
}
