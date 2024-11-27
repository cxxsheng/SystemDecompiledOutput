package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PublishAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.PublishAttributes> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.PublishAttributes>() { // from class: android.telephony.ims.PublishAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.PublishAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.PublishAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.PublishAttributes[] newArray(int i) {
            return new android.telephony.ims.PublishAttributes[i];
        }
    };
    private java.util.List<android.telephony.ims.RcsContactPresenceTuple> mPresenceTuples;
    private final int mPublishState;
    private android.telephony.ims.SipDetails mSipDetails;

    public static final class Builder {
        private android.telephony.ims.PublishAttributes mAttributes;

        public Builder(int i) {
            this.mAttributes = new android.telephony.ims.PublishAttributes(i);
        }

        public android.telephony.ims.PublishAttributes.Builder setSipDetails(android.telephony.ims.SipDetails sipDetails) {
            this.mAttributes.mSipDetails = sipDetails;
            return this;
        }

        public android.telephony.ims.PublishAttributes.Builder setPresenceTuples(java.util.List<android.telephony.ims.RcsContactPresenceTuple> list) {
            this.mAttributes.mPresenceTuples = list;
            return this;
        }

        public android.telephony.ims.PublishAttributes build() {
            return this.mAttributes;
        }
    }

    private PublishAttributes(int i) {
        this.mPublishState = i;
    }

    public int getPublishState() {
        return this.mPublishState;
    }

    public java.util.List<android.telephony.ims.RcsContactPresenceTuple> getPresenceTuples() {
        if (this.mPresenceTuples == null) {
            return java.util.Collections.emptyList();
        }
        return this.mPresenceTuples;
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
        parcel.writeInt(this.mPublishState);
        parcel.writeList(this.mPresenceTuples);
        parcel.writeParcelable(this.mSipDetails, 0);
    }

    private PublishAttributes(android.os.Parcel parcel) {
        this.mPublishState = parcel.readInt();
        this.mPresenceTuples = new java.util.ArrayList();
        parcel.readList(this.mPresenceTuples, null, android.telephony.ims.RcsContactPresenceTuple.class);
        this.mSipDetails = (android.telephony.ims.SipDetails) parcel.readParcelable(android.telephony.ims.SipDetails.class.getClassLoader(), android.telephony.ims.SipDetails.class);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.PublishAttributes publishAttributes = (android.telephony.ims.PublishAttributes) obj;
        if (this.mPublishState == publishAttributes.mPublishState && java.util.Objects.equals(this.mPresenceTuples, publishAttributes.mPresenceTuples) && java.util.Objects.equals(this.mSipDetails, publishAttributes.mSipDetails)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPublishState), this.mPresenceTuples, this.mSipDetails);
    }

    public java.lang.String toString() {
        return "PublishAttributes { publishState= " + this.mPublishState + ", presenceTuples=[" + this.mPresenceTuples + "]SipDetails=" + this.mSipDetails + "}";
    }
}
