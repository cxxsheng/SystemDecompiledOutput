package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class UiccSlotInfo implements android.os.Parcelable {
    public static final int CARD_STATE_INFO_ABSENT = 1;
    public static final int CARD_STATE_INFO_ERROR = 3;
    public static final int CARD_STATE_INFO_PRESENT = 2;
    public static final int CARD_STATE_INFO_RESTRICTED = 4;
    public static final android.os.Parcelable.Creator<android.telephony.UiccSlotInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.UiccSlotInfo>() { // from class: android.telephony.UiccSlotInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccSlotInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.UiccSlotInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccSlotInfo[] newArray(int i) {
            return new android.telephony.UiccSlotInfo[i];
        }
    };
    private final java.lang.String mCardId;
    private final int mCardStateInfo;
    private final boolean mIsActive;
    private final boolean mIsEuicc;
    private final boolean mIsExtendedApduSupported;
    private final boolean mIsRemovable;
    private boolean mLogicalSlotAccessRestricted;
    private final int mLogicalSlotIdx;
    private final java.util.List<android.telephony.UiccPortInfo> mPortList;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CardStateInfo {
    }

    private UiccSlotInfo(android.os.Parcel parcel) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsActive = parcel.readBoolean();
        this.mIsEuicc = parcel.readBoolean();
        this.mCardId = parcel.readString8();
        this.mCardStateInfo = parcel.readInt();
        this.mLogicalSlotIdx = parcel.readInt();
        this.mIsExtendedApduSupported = parcel.readBoolean();
        this.mIsRemovable = parcel.readBoolean();
        this.mPortList = new java.util.ArrayList();
        parcel.readTypedList(this.mPortList, android.telephony.UiccPortInfo.CREATOR);
        this.mLogicalSlotAccessRestricted = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsActive);
        parcel.writeBoolean(this.mIsEuicc);
        parcel.writeString8(this.mCardId);
        parcel.writeInt(this.mCardStateInfo);
        parcel.writeInt(this.mLogicalSlotIdx);
        parcel.writeBoolean(this.mIsExtendedApduSupported);
        parcel.writeBoolean(this.mIsRemovable);
        parcel.writeTypedList(this.mPortList, i);
        parcel.writeBoolean(this.mLogicalSlotAccessRestricted);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @java.lang.Deprecated
    public UiccSlotInfo(boolean z, boolean z2, java.lang.String str, int i, int i2, boolean z3) {
        this.mLogicalSlotAccessRestricted = false;
        this.mIsActive = z;
        this.mIsEuicc = z2;
        this.mCardId = str;
        this.mCardStateInfo = i;
        this.mLogicalSlotIdx = i2;
        this.mIsExtendedApduSupported = z3;
        this.mIsRemovable = false;
        this.mPortList = new java.util.ArrayList();
    }

    public UiccSlotInfo(boolean z, java.lang.String str, int i, boolean z2, boolean z3, java.util.List<android.telephony.UiccPortInfo> list) {
        int logicalSlotIndex;
        this.mLogicalSlotAccessRestricted = false;
        this.mIsEuicc = z;
        this.mCardId = str;
        this.mCardStateInfo = i;
        this.mIsExtendedApduSupported = z2;
        this.mIsRemovable = z3;
        this.mPortList = list;
        this.mIsActive = !list.isEmpty() && list.get(0).isActive();
        if (list.isEmpty()) {
            logicalSlotIndex = -1;
        } else {
            logicalSlotIndex = list.get(0).getLogicalSlotIndex();
        }
        this.mLogicalSlotIdx = logicalSlotIndex;
    }

    @java.lang.Deprecated
    public boolean getIsActive() {
        if (this.mLogicalSlotAccessRestricted) {
            throw new java.lang.UnsupportedOperationException("getIsActive() is not supported by UiccSlotInfo. Please Use UiccPortInfo API instead");
        }
        return this.mIsActive;
    }

    public boolean getIsEuicc() {
        return this.mIsEuicc;
    }

    public java.lang.String getCardId() {
        return this.mCardId;
    }

    public int getCardStateInfo() {
        return this.mCardStateInfo;
    }

    @java.lang.Deprecated
    public int getLogicalSlotIdx() {
        if (this.mLogicalSlotAccessRestricted) {
            throw new java.lang.UnsupportedOperationException("getLogicalSlotIdx() is not supported by UiccSlotInfo. Please use UiccPortInfo API instead");
        }
        return this.mLogicalSlotIdx;
    }

    public boolean getIsExtendedApduSupported() {
        return this.mIsExtendedApduSupported;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public java.util.Collection<android.telephony.UiccPortInfo> getPorts() {
        return java.util.Collections.unmodifiableList(this.mPortList);
    }

    public void setLogicalSlotAccessRestricted(boolean z) {
        this.mLogicalSlotAccessRestricted = z;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.UiccSlotInfo uiccSlotInfo = (android.telephony.UiccSlotInfo) obj;
        if (this.mIsActive == uiccSlotInfo.mIsActive && this.mIsEuicc == uiccSlotInfo.mIsEuicc && java.util.Objects.equals(this.mCardId, uiccSlotInfo.mCardId) && this.mCardStateInfo == uiccSlotInfo.mCardStateInfo && this.mLogicalSlotIdx == uiccSlotInfo.mLogicalSlotIdx && this.mIsExtendedApduSupported == uiccSlotInfo.mIsExtendedApduSupported && this.mIsRemovable == uiccSlotInfo.mIsRemovable && java.util.Objects.equals(this.mPortList, uiccSlotInfo.mPortList)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsActive), java.lang.Boolean.valueOf(this.mIsEuicc), this.mCardId, java.lang.Integer.valueOf(this.mCardStateInfo), java.lang.Integer.valueOf(this.mLogicalSlotIdx), java.lang.Boolean.valueOf(this.mIsExtendedApduSupported), java.lang.Boolean.valueOf(this.mIsRemovable), this.mPortList);
    }

    public java.lang.String toString() {
        return "UiccSlotInfo (, mIsEuicc=" + this.mIsEuicc + ", mCardId=" + android.telephony.SubscriptionInfo.getPrintableId(this.mCardId) + ", cardState=" + this.mCardStateInfo + ", mIsExtendedApduSupported=" + this.mIsExtendedApduSupported + ", mIsRemovable=" + this.mIsRemovable + ", mPortList=" + this.mPortList + ", mLogicalSlotAccessRestricted=" + this.mLogicalSlotAccessRestricted + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
