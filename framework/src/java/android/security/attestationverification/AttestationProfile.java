package android.security.attestationverification;

/* loaded from: classes3.dex */
public final class AttestationProfile implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.attestationverification.AttestationProfile> CREATOR = new android.os.Parcelable.Creator<android.security.attestationverification.AttestationProfile>() { // from class: android.security.attestationverification.AttestationProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.attestationverification.AttestationProfile[] newArray(int i) {
            return new android.security.attestationverification.AttestationProfile[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.attestationverification.AttestationProfile createFromParcel(android.os.Parcel parcel) {
            return new android.security.attestationverification.AttestationProfile(parcel);
        }
    };
    private static final java.lang.String TAG = "AVF";
    private final int mAttestationProfileId;
    private final java.lang.String mPackageName;
    private final java.lang.String mProfileName;

    private AttestationProfile(int i, java.lang.String str, java.lang.String str2) {
        this.mAttestationProfileId = i;
        this.mPackageName = str;
        this.mProfileName = str2;
    }

    public AttestationProfile(int i) {
        this(i, null, null);
        if (i == 1) {
            throw new java.lang.IllegalArgumentException("App-defined profiles must be specified with the constructor AttestationProfile#constructor(String, String)");
        }
    }

    public AttestationProfile(java.lang.String str, java.lang.String str2) {
        this(1, str, str2);
        if (str == null || str2 == null) {
            throw new java.lang.IllegalArgumentException("Both packageName and profileName must be non-null");
        }
    }

    public java.lang.String toString() {
        java.lang.String str;
        if (this.mAttestationProfileId == 1) {
            return "AttestationProfile(package=" + this.mPackageName + ", name=" + this.mProfileName + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        switch (this.mAttestationProfileId) {
            case 0:
                str = "PROFILE_UNKNOWN";
                break;
            default:
                android.util.Log.e(TAG, "ERROR: Missing case in AttestationProfile#toString");
                str = android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
                break;
        }
        return "AttestationProfile(" + str + "/" + this.mAttestationProfileId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public int getAttestationProfileId() {
        return this.mAttestationProfileId;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getProfileName() {
        return this.mProfileName;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.security.attestationverification.AttestationProfile attestationProfile = (android.security.attestationverification.AttestationProfile) obj;
        if (this.mAttestationProfileId == attestationProfile.mAttestationProfileId && java.util.Objects.equals(this.mPackageName, attestationProfile.mPackageName) && java.util.Objects.equals(this.mProfileName, attestationProfile.mProfileName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mAttestationProfileId + 31) * 31) + java.util.Objects.hashCode(this.mPackageName)) * 31) + java.util.Objects.hashCode(this.mProfileName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mPackageName != null ? (byte) 2 : (byte) 0;
        if (this.mProfileName != null) {
            b = (byte) (b | 4);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mAttestationProfileId);
        if (this.mPackageName != null) {
            parcel.writeString(this.mPackageName);
        }
        if (this.mProfileName != null) {
            parcel.writeString(this.mProfileName);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AttestationProfile(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        java.lang.String readString = (readByte & 2) == 0 ? null : parcel.readString();
        java.lang.String readString2 = (readByte & 4) == 0 ? null : parcel.readString();
        this.mAttestationProfileId = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.security.attestationverification.AttestationVerificationManager.AttestationProfileId.class, (java.lang.annotation.Annotation) null, this.mAttestationProfileId);
        this.mPackageName = readString;
        this.mProfileName = readString2;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
