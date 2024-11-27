package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class UiccSlotMapping implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.UiccSlotMapping> CREATOR = new android.os.Parcelable.Creator<android.telephony.UiccSlotMapping>() { // from class: android.telephony.UiccSlotMapping.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccSlotMapping createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.UiccSlotMapping(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.UiccSlotMapping[] newArray(int i) {
            return new android.telephony.UiccSlotMapping[i];
        }
    };
    private final int mLogicalSlotIndex;
    private final int mPhysicalSlotIndex;
    private final int mPortIndex;

    private UiccSlotMapping(android.os.Parcel parcel) {
        this.mPortIndex = parcel.readInt();
        this.mPhysicalSlotIndex = parcel.readInt();
        this.mLogicalSlotIndex = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPortIndex);
        parcel.writeInt(this.mPhysicalSlotIndex);
        parcel.writeInt(this.mLogicalSlotIndex);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UiccSlotMapping(int i, int i2, int i3) {
        this.mPortIndex = i;
        this.mPhysicalSlotIndex = i2;
        this.mLogicalSlotIndex = i3;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    public int getPhysicalSlotIndex() {
        return this.mPhysicalSlotIndex;
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
        android.telephony.UiccSlotMapping uiccSlotMapping = (android.telephony.UiccSlotMapping) obj;
        if (this.mPortIndex == uiccSlotMapping.mPortIndex && this.mPhysicalSlotIndex == uiccSlotMapping.mPhysicalSlotIndex && this.mLogicalSlotIndex == uiccSlotMapping.mLogicalSlotIndex) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPortIndex), java.lang.Integer.valueOf(this.mPhysicalSlotIndex), java.lang.Integer.valueOf(this.mLogicalSlotIndex));
    }

    public java.lang.String toString() {
        return "UiccSlotMapping (mPortIndex=" + this.mPortIndex + ", mPhysicalSlotIndex=" + this.mPhysicalSlotIndex + ", mLogicalSlotIndex=" + this.mLogicalSlotIndex + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
