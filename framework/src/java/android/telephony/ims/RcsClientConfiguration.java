package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RcsClientConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.RcsClientConfiguration> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RcsClientConfiguration>() { // from class: android.telephony.ims.RcsClientConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsClientConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.RcsClientConfiguration(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), java.lang.Boolean.valueOf(parcel.readBoolean()).booleanValue());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsClientConfiguration[] newArray(int i) {
            return new android.telephony.ims.RcsClientConfiguration[i];
        }
    };
    public static final java.lang.String RCS_PROFILE_1_0 = "UP_1.0";
    public static final java.lang.String RCS_PROFILE_2_3 = "UP_2.3";
    public static final java.lang.String RCS_PROFILE_2_4 = "UP_2.4";
    private java.lang.String mClientVendor;
    private java.lang.String mClientVersion;
    private boolean mRcsEnabledByUser;
    private java.lang.String mRcsProfile;
    private java.lang.String mRcsVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StringRcsProfile {
    }

    @java.lang.Deprecated
    public RcsClientConfiguration(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this(str, str2, str3, str4, true);
    }

    public RcsClientConfiguration(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z) {
        this.mRcsVersion = str;
        this.mRcsProfile = str2;
        this.mClientVendor = str3;
        this.mClientVersion = str4;
        this.mRcsEnabledByUser = z;
    }

    public java.lang.String getRcsVersion() {
        return this.mRcsVersion;
    }

    public java.lang.String getRcsProfile() {
        return this.mRcsProfile;
    }

    public java.lang.String getClientVendor() {
        return this.mClientVendor;
    }

    public java.lang.String getClientVersion() {
        return this.mClientVersion;
    }

    public boolean isRcsEnabledByUser() {
        return this.mRcsEnabledByUser;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mRcsVersion);
        parcel.writeString(this.mRcsProfile);
        parcel.writeString(this.mClientVendor);
        parcel.writeString(this.mClientVersion);
        parcel.writeBoolean(this.mRcsEnabledByUser);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.ims.RcsClientConfiguration)) {
            return false;
        }
        android.telephony.ims.RcsClientConfiguration rcsClientConfiguration = (android.telephony.ims.RcsClientConfiguration) obj;
        return this.mRcsVersion.equals(rcsClientConfiguration.mRcsVersion) && this.mRcsProfile.equals(rcsClientConfiguration.mRcsProfile) && this.mClientVendor.equals(rcsClientConfiguration.mClientVendor) && this.mClientVersion.equals(rcsClientConfiguration.mClientVersion) && this.mRcsEnabledByUser == rcsClientConfiguration.mRcsEnabledByUser;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mRcsVersion, this.mRcsProfile, this.mClientVendor, this.mClientVersion, java.lang.Boolean.valueOf(this.mRcsEnabledByUser));
    }
}
