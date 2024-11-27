package android.telephony.ims.feature;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CapabilityChangeRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.feature.CapabilityChangeRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.feature.CapabilityChangeRequest>() { // from class: android.telephony.ims.feature.CapabilityChangeRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.feature.CapabilityChangeRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.feature.CapabilityChangeRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.feature.CapabilityChangeRequest[] newArray(int i) {
            return new android.telephony.ims.feature.CapabilityChangeRequest[i];
        }
    };
    private final java.util.Set<android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair> mCapabilitiesToDisable;
    private final java.util.Set<android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair> mCapabilitiesToEnable;

    public static class CapabilityPair {
        private final int mCapability;
        private final int radioTech;

        public CapabilityPair(int i, int i2) {
            this.mCapability = i;
            this.radioTech = i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair)) {
                return false;
            }
            android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair capabilityPair = (android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair) obj;
            return getCapability() == capabilityPair.getCapability() && getRadioTech() == capabilityPair.getRadioTech();
        }

        public int hashCode() {
            return (getCapability() * 31) + getRadioTech();
        }

        public int getCapability() {
            return this.mCapability;
        }

        public int getRadioTech() {
            return this.radioTech;
        }

        public java.lang.String toString() {
            return "CapabilityPair{mCapability=" + this.mCapability + ", radioTech=" + this.radioTech + '}';
        }
    }

    public CapabilityChangeRequest() {
        this.mCapabilitiesToEnable = new android.util.ArraySet();
        this.mCapabilitiesToDisable = new android.util.ArraySet();
    }

    public void addCapabilitiesToEnableForTech(int i, int i2) {
        addAllCapabilities(this.mCapabilitiesToEnable, i, i2);
    }

    public void addCapabilitiesToDisableForTech(int i, int i2) {
        addAllCapabilities(this.mCapabilitiesToDisable, i, i2);
    }

    public java.util.List<android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair> getCapabilitiesToEnable() {
        return new java.util.ArrayList(this.mCapabilitiesToEnable);
    }

    public java.util.List<android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair> getCapabilitiesToDisable() {
        return new java.util.ArrayList(this.mCapabilitiesToDisable);
    }

    private void addAllCapabilities(java.util.Set<android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair> set, int i, int i2) {
        long highestOneBit = java.lang.Long.highestOneBit(i);
        for (int i3 = 1; i3 <= highestOneBit; i3 *= 2) {
            if ((i3 & i) > 0) {
                set.add(new android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair(i3, i2));
            }
        }
    }

    protected CapabilityChangeRequest(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mCapabilitiesToEnable = new android.util.ArraySet(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mCapabilitiesToEnable.add(new android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair(parcel.readInt(), parcel.readInt()));
        }
        int readInt2 = parcel.readInt();
        this.mCapabilitiesToDisable = new android.util.ArraySet(readInt2);
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mCapabilitiesToDisable.add(new android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair(parcel.readInt(), parcel.readInt()));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCapabilitiesToEnable.size());
        for (android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair capabilityPair : this.mCapabilitiesToEnable) {
            parcel.writeInt(capabilityPair.getCapability());
            parcel.writeInt(capabilityPair.getRadioTech());
        }
        parcel.writeInt(this.mCapabilitiesToDisable.size());
        for (android.telephony.ims.feature.CapabilityChangeRequest.CapabilityPair capabilityPair2 : this.mCapabilitiesToDisable) {
            parcel.writeInt(capabilityPair2.getCapability());
            parcel.writeInt(capabilityPair2.getRadioTech());
        }
    }

    public java.lang.String toString() {
        return "CapabilityChangeRequest{mCapabilitiesToEnable=" + this.mCapabilitiesToEnable + ", mCapabilitiesToDisable=" + this.mCapabilitiesToDisable + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.ims.feature.CapabilityChangeRequest)) {
            return false;
        }
        android.telephony.ims.feature.CapabilityChangeRequest capabilityChangeRequest = (android.telephony.ims.feature.CapabilityChangeRequest) obj;
        if (this.mCapabilitiesToEnable.equals(capabilityChangeRequest.mCapabilitiesToEnable)) {
            return this.mCapabilitiesToDisable.equals(capabilityChangeRequest.mCapabilitiesToDisable);
        }
        return false;
    }

    public int hashCode() {
        return (this.mCapabilitiesToEnable.hashCode() * 31) + this.mCapabilitiesToDisable.hashCode();
    }
}
