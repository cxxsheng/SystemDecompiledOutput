package android.telephony.ims;

/* loaded from: classes3.dex */
public final class ImsRegistrationAttributes implements android.os.Parcelable {
    public static final int ATTR_EPDG_OVER_CELL_INTERNET = 1;
    public static final int ATTR_REGISTRATION_TYPE_EMERGENCY = 2;
    public static final int ATTR_VIRTUAL_FOR_ANONYMOUS_EMERGENCY_CALL = 4;
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsRegistrationAttributes> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsRegistrationAttributes>() { // from class: android.telephony.ims.ImsRegistrationAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsRegistrationAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsRegistrationAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsRegistrationAttributes[] newArray(int i) {
            return new android.telephony.ims.ImsRegistrationAttributes[i];
        }
    };
    private final java.util.ArrayList<java.lang.String> mFeatureTags;
    private final int mImsAttributeFlags;
    private final int mRegistrationTech;
    private final android.telephony.ims.SipDetails mSipDetails;
    private final int mTransportType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsAttributeFlag {
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private int mAttributeFlags;
        private java.util.Set<java.lang.String> mFeatureTags = java.util.Collections.emptySet();
        private final int mRegistrationTech;
        private android.telephony.ims.SipDetails mSipDetails;

        public Builder(int i) {
            this.mRegistrationTech = i;
            if (i == 2) {
                this.mAttributeFlags |= 1;
            }
        }

        public android.telephony.ims.ImsRegistrationAttributes.Builder setFeatureTags(java.util.Set<java.lang.String> set) {
            if (set == null) {
                throw new java.lang.IllegalArgumentException("feature tag set must not be null");
            }
            this.mFeatureTags = new android.util.ArraySet(set);
            return this;
        }

        public android.telephony.ims.ImsRegistrationAttributes.Builder setSipDetails(android.telephony.ims.SipDetails sipDetails) {
            this.mSipDetails = sipDetails;
            return this;
        }

        public android.telephony.ims.ImsRegistrationAttributes.Builder setFlagRegistrationTypeEmergency() {
            this.mAttributeFlags |= 2;
            return this;
        }

        public android.telephony.ims.ImsRegistrationAttributes.Builder setFlagVirtualRegistrationForEmergencyCall() {
            this.mAttributeFlags |= 4;
            return this;
        }

        public android.telephony.ims.ImsRegistrationAttributes build() {
            return new android.telephony.ims.ImsRegistrationAttributes(this.mRegistrationTech, android.telephony.ims.RegistrationManager.getAccessType(this.mRegistrationTech), this.mAttributeFlags, this.mFeatureTags, this.mSipDetails);
        }
    }

    public ImsRegistrationAttributes(int i, int i2, int i3, java.util.Set<java.lang.String> set) {
        this.mRegistrationTech = i;
        this.mTransportType = i2;
        this.mImsAttributeFlags = i3;
        this.mFeatureTags = new java.util.ArrayList<>(set);
        this.mSipDetails = null;
    }

    public ImsRegistrationAttributes(int i, int i2, int i3, java.util.Set<java.lang.String> set, android.telephony.ims.SipDetails sipDetails) {
        this.mRegistrationTech = i;
        this.mTransportType = i2;
        this.mImsAttributeFlags = i3;
        this.mFeatureTags = new java.util.ArrayList<>(set);
        this.mSipDetails = sipDetails;
    }

    public ImsRegistrationAttributes(android.os.Parcel parcel) {
        this.mRegistrationTech = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mImsAttributeFlags = parcel.readInt();
        this.mFeatureTags = new java.util.ArrayList<>();
        parcel.readList(this.mFeatureTags, null, java.lang.String.class);
        this.mSipDetails = (android.telephony.ims.SipDetails) parcel.readParcelable(null, android.telephony.ims.SipDetails.class);
    }

    @android.annotation.SystemApi
    public int getRegistrationTechnology() {
        return this.mRegistrationTech;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getAttributeFlags() {
        return this.mImsAttributeFlags;
    }

    public java.util.Set<java.lang.String> getFeatureTags() {
        if (this.mFeatureTags == null) {
            return java.util.Collections.emptySet();
        }
        return new android.util.ArraySet(this.mFeatureTags);
    }

    public android.telephony.ims.SipDetails getSipDetails() {
        return this.mSipDetails;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRegistrationTech);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mImsAttributeFlags);
        parcel.writeList(this.mFeatureTags);
        parcel.writeParcelable(this.mSipDetails, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.ImsRegistrationAttributes imsRegistrationAttributes = (android.telephony.ims.ImsRegistrationAttributes) obj;
        if (this.mRegistrationTech == imsRegistrationAttributes.mRegistrationTech && this.mTransportType == imsRegistrationAttributes.mTransportType && this.mImsAttributeFlags == imsRegistrationAttributes.mImsAttributeFlags && java.util.Objects.equals(this.mFeatureTags, imsRegistrationAttributes.mFeatureTags) && java.util.Objects.equals(this.mSipDetails, imsRegistrationAttributes.mSipDetails)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRegistrationTech), java.lang.Integer.valueOf(this.mTransportType), java.lang.Integer.valueOf(this.mImsAttributeFlags), this.mFeatureTags, this.mSipDetails);
    }

    public java.lang.String toString() {
        return "ImsRegistrationAttributes { transportType= " + this.mTransportType + ", attributeFlags=" + this.mImsAttributeFlags + ", featureTags=[" + this.mFeatureTags + "],SipDetails=" + this.mSipDetails + "}";
    }
}
