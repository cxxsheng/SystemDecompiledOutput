package com.android.ims;

/* loaded from: classes4.dex */
public final class ImsFeatureContainer implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.ImsFeatureContainer> CREATOR = new android.os.Parcelable.Creator<com.android.ims.ImsFeatureContainer>() { // from class: com.android.ims.ImsFeatureContainer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.ImsFeatureContainer createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.ImsFeatureContainer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.ImsFeatureContainer[] newArray(int i) {
            return new com.android.ims.ImsFeatureContainer[i];
        }
    };
    public final android.telephony.ims.aidl.IImsConfig imsConfig;
    public final android.os.IBinder imsFeature;
    public final android.telephony.ims.aidl.IImsRegistration imsRegistration;
    private long mCapabilities;
    private int mState;
    public final android.telephony.ims.aidl.ISipTransport sipTransport;

    public ImsFeatureContainer(android.os.IBinder iBinder, android.telephony.ims.aidl.IImsConfig iImsConfig, android.telephony.ims.aidl.IImsRegistration iImsRegistration, android.telephony.ims.aidl.ISipTransport iSipTransport, long j) {
        this.mState = 0;
        this.imsFeature = iBinder;
        this.imsConfig = iImsConfig;
        this.imsRegistration = iImsRegistration;
        this.sipTransport = iSipTransport;
        this.mCapabilities = j;
    }

    private ImsFeatureContainer(android.os.Parcel parcel) {
        this.mState = 0;
        this.imsFeature = parcel.readStrongBinder();
        this.imsConfig = android.telephony.ims.aidl.IImsConfig.Stub.asInterface(parcel.readStrongBinder());
        this.imsRegistration = android.telephony.ims.aidl.IImsRegistration.Stub.asInterface(parcel.readStrongBinder());
        this.sipTransport = android.telephony.ims.aidl.ISipTransport.Stub.asInterface(parcel.readStrongBinder());
        this.mState = parcel.readInt();
        this.mCapabilities = parcel.readLong();
    }

    public long getCapabilities() {
        return this.mCapabilities;
    }

    public void setCapabilities(long j) {
        this.mCapabilities = j;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.ims.ImsFeatureContainer imsFeatureContainer = (com.android.ims.ImsFeatureContainer) obj;
        if (this.imsFeature.equals(imsFeatureContainer.imsFeature) && this.imsConfig.equals(imsFeatureContainer.imsConfig) && this.imsRegistration.equals(imsFeatureContainer.imsRegistration) && this.sipTransport.equals(imsFeatureContainer.sipTransport) && this.mState == imsFeatureContainer.getState() && this.mCapabilities == imsFeatureContainer.getCapabilities()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.imsFeature, this.imsConfig, this.imsRegistration, this.sipTransport, java.lang.Integer.valueOf(this.mState), java.lang.Long.valueOf(this.mCapabilities));
    }

    public java.lang.String toString() {
        return "FeatureContainer{imsFeature=" + this.imsFeature + ", imsConfig=" + this.imsConfig + ", imsRegistration=" + this.imsRegistration + ", sipTransport=" + this.sipTransport + ", state=" + android.telephony.ims.feature.ImsFeature.STATE_LOG_MAP.get(java.lang.Integer.valueOf(this.mState)) + ", capabilities = " + android.telephony.ims.ImsService.getCapabilitiesString(this.mCapabilities) + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.imsFeature);
        parcel.writeStrongInterface(this.imsConfig);
        parcel.writeStrongInterface(this.imsRegistration);
        parcel.writeStrongInterface(this.sipTransport);
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mCapabilities);
    }
}
