package android.telephony.data;

/* loaded from: classes3.dex */
public final class NetworkSlicingConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.NetworkSlicingConfig> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.NetworkSlicingConfig>() { // from class: android.telephony.data.NetworkSlicingConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.NetworkSlicingConfig createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.NetworkSlicingConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.NetworkSlicingConfig[] newArray(int i) {
            return new android.telephony.data.NetworkSlicingConfig[i];
        }
    };
    private final java.util.List<android.telephony.data.NetworkSliceInfo> mSliceInfo;
    private final java.util.List<android.telephony.data.UrspRule> mUrspRules;

    public NetworkSlicingConfig() {
        this.mUrspRules = new java.util.ArrayList();
        this.mSliceInfo = new java.util.ArrayList();
    }

    public NetworkSlicingConfig(java.util.List<android.telephony.data.UrspRule> list, java.util.List<android.telephony.data.NetworkSliceInfo> list2) {
        this();
        this.mUrspRules.addAll(list);
        this.mSliceInfo.addAll(list2);
    }

    public NetworkSlicingConfig(android.os.Parcel parcel) {
        this.mUrspRules = parcel.createTypedArrayList(android.telephony.data.UrspRule.CREATOR);
        this.mSliceInfo = parcel.createTypedArrayList(android.telephony.data.NetworkSliceInfo.CREATOR);
    }

    public java.util.List<android.telephony.data.UrspRule> getUrspRules() {
        return this.mUrspRules;
    }

    public java.util.List<android.telephony.data.NetworkSliceInfo> getSliceInfo() {
        return this.mSliceInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mUrspRules, i);
        parcel.writeTypedList(this.mSliceInfo, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.data.NetworkSlicingConfig networkSlicingConfig = (android.telephony.data.NetworkSlicingConfig) obj;
        if (this.mUrspRules.size() == networkSlicingConfig.mUrspRules.size() && this.mUrspRules.containsAll(networkSlicingConfig.mUrspRules) && this.mSliceInfo.size() == networkSlicingConfig.mSliceInfo.size() && this.mSliceInfo.containsAll(networkSlicingConfig.mSliceInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUrspRules, this.mSliceInfo);
    }

    public java.lang.String toString() {
        return "{.urspRules = " + this.mUrspRules + ", .sliceInfo = " + this.mSliceInfo + "}";
    }
}
