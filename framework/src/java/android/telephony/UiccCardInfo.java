package android.telephony;

/* loaded from: classes3.dex */
public final class UiccCardInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.UiccCardInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.UiccCardInfo>() { // from class: android.telephony.UiccCardInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccCardInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.UiccCardInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccCardInfo[] newArray(int i) {
            return new android.telephony.UiccCardInfo[i];
        }
    };
    private final int mCardId;
    private final java.lang.String mEid;
    private final java.lang.String mIccId;
    private boolean mIccIdAccessRestricted;
    private final boolean mIsEuicc;
    private final boolean mIsMultipleEnabledProfilesSupported;
    private final boolean mIsRemovable;
    private final int mPhysicalSlotIndex;
    private final java.util.List<android.telephony.UiccPortInfo> mPortList;

    private UiccCardInfo(android.os.Parcel parcel) {
        this.mIccIdAccessRestricted = false;
        this.mIsEuicc = parcel.readBoolean();
        this.mCardId = parcel.readInt();
        this.mEid = parcel.readString8();
        this.mIccId = parcel.readString8();
        this.mPhysicalSlotIndex = parcel.readInt();
        this.mIsRemovable = parcel.readBoolean();
        this.mIsMultipleEnabledProfilesSupported = parcel.readBoolean();
        this.mPortList = new java.util.ArrayList();
        parcel.readTypedList(this.mPortList, android.telephony.UiccPortInfo.CREATOR);
        this.mIccIdAccessRestricted = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEuicc);
        parcel.writeInt(this.mCardId);
        parcel.writeString8(this.mEid);
        parcel.writeString8(this.mIccId);
        parcel.writeInt(this.mPhysicalSlotIndex);
        parcel.writeBoolean(this.mIsRemovable);
        parcel.writeBoolean(this.mIsMultipleEnabledProfilesSupported);
        parcel.writeTypedList(this.mPortList, i);
        parcel.writeBoolean(this.mIccIdAccessRestricted);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UiccCardInfo(boolean z, int i, java.lang.String str, int i2, boolean z2, boolean z3, java.util.List<android.telephony.UiccPortInfo> list) {
        this.mIccIdAccessRestricted = false;
        this.mIsEuicc = z;
        this.mCardId = i;
        this.mEid = str;
        this.mIccId = null;
        this.mPhysicalSlotIndex = i2;
        this.mIsRemovable = z2;
        this.mIsMultipleEnabledProfilesSupported = z3;
        this.mPortList = list;
    }

    public boolean isEuicc() {
        return this.mIsEuicc;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public java.lang.String getEid() {
        if (!this.mIsEuicc) {
            return null;
        }
        return this.mEid;
    }

    @java.lang.Deprecated
    public java.lang.String getIccId() {
        if (this.mIccIdAccessRestricted) {
            throw new java.lang.UnsupportedOperationException("getIccId() is not supported by UiccCardInfo. Please Use UiccPortInfo API instead");
        }
        if (this.mPortList.isEmpty()) {
            return null;
        }
        return this.mPortList.get(0).getIccId();
    }

    @java.lang.Deprecated
    public int getSlotIndex() {
        return this.mPhysicalSlotIndex;
    }

    public int getPhysicalSlotIndex() {
        return this.mPhysicalSlotIndex;
    }

    public boolean isRemovable() {
        return this.mIsRemovable;
    }

    public boolean isMultipleEnabledProfilesSupported() {
        return this.mIsMultipleEnabledProfilesSupported;
    }

    public java.util.Collection<android.telephony.UiccPortInfo> getPorts() {
        return java.util.Collections.unmodifiableList(this.mPortList);
    }

    public void setIccIdAccessRestricted(boolean z) {
        this.mIccIdAccessRestricted = z;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.UiccCardInfo uiccCardInfo = (android.telephony.UiccCardInfo) obj;
        if (this.mIsEuicc == uiccCardInfo.mIsEuicc && this.mCardId == uiccCardInfo.mCardId && java.util.Objects.equals(this.mEid, uiccCardInfo.mEid) && java.util.Objects.equals(this.mIccId, uiccCardInfo.mIccId) && this.mPhysicalSlotIndex == uiccCardInfo.mPhysicalSlotIndex && this.mIsRemovable == uiccCardInfo.mIsRemovable && this.mIsMultipleEnabledProfilesSupported == uiccCardInfo.mIsMultipleEnabledProfilesSupported && java.util.Objects.equals(this.mPortList, uiccCardInfo.mPortList)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsEuicc), java.lang.Integer.valueOf(this.mCardId), this.mEid, this.mIccId, java.lang.Integer.valueOf(this.mPhysicalSlotIndex), java.lang.Boolean.valueOf(this.mIsRemovable), java.lang.Boolean.valueOf(this.mIsMultipleEnabledProfilesSupported), this.mPortList);
    }

    public java.lang.String toString() {
        return "UiccCardInfo (mIsEuicc=" + this.mIsEuicc + ", mCardId=" + this.mCardId + ", mEid=" + com.android.telephony.Rlog.pii(com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE, this.mEid) + ", mPhysicalSlotIndex=" + this.mPhysicalSlotIndex + ", mIsRemovable=" + this.mIsRemovable + ", mIsMultipleEnabledProfilesSupported=" + this.mIsMultipleEnabledProfilesSupported + ", mPortList=" + this.mPortList + ", mIccIdAccessRestricted=" + this.mIccIdAccessRestricted + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
