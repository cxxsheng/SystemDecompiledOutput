package android.telephony;

/* loaded from: classes3.dex */
public final class UiccPortInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.UiccPortInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.UiccPortInfo>() { // from class: android.telephony.UiccPortInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccPortInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.UiccPortInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccPortInfo[] newArray(int i) {
            return new android.telephony.UiccPortInfo[i];
        }
    };
    public static final java.lang.String ICCID_REDACTED = "FFFFFFFFFFFFFFFFFFFF";
    private final java.lang.String mIccId;
    private final boolean mIsActive;
    private final int mLogicalSlotIndex;
    private final int mPortIndex;

    private UiccPortInfo(android.os.Parcel parcel) {
        this.mIccId = parcel.readString8();
        this.mPortIndex = parcel.readInt();
        this.mLogicalSlotIndex = parcel.readInt();
        this.mIsActive = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mIccId);
        parcel.writeInt(this.mPortIndex);
        parcel.writeInt(this.mLogicalSlotIndex);
        parcel.writeBoolean(this.mIsActive);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UiccPortInfo(java.lang.String str, int i, int i2, boolean z) {
        this.mIccId = str;
        this.mPortIndex = i;
        this.mLogicalSlotIndex = i2;
        this.mIsActive = z;
    }

    public java.lang.String getIccId() {
        return this.mIccId;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    public boolean isActive() {
        return this.mIsActive;
    }

    public int getLogicalSlotIndex() {
        return this.mLogicalSlotIndex;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.UiccPortInfo uiccPortInfo = (android.telephony.UiccPortInfo) obj;
        if (java.util.Objects.equals(this.mIccId, uiccPortInfo.mIccId) && this.mPortIndex == uiccPortInfo.mPortIndex && this.mLogicalSlotIndex == uiccPortInfo.mLogicalSlotIndex && this.mIsActive == uiccPortInfo.mIsActive) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mIccId, java.lang.Integer.valueOf(this.mPortIndex), java.lang.Integer.valueOf(this.mLogicalSlotIndex), java.lang.Boolean.valueOf(this.mIsActive));
    }

    public java.lang.String toString() {
        return "UiccPortInfo (isActive=" + this.mIsActive + ", iccId=" + android.telephony.SubscriptionInfo.getPrintableId(this.mIccId) + ", portIndex=" + this.mPortIndex + ", mLogicalSlotIndex=" + this.mLogicalSlotIndex + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
