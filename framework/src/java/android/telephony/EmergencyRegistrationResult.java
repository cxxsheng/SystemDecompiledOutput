package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class EmergencyRegistrationResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.EmergencyRegistrationResult> CREATOR = new android.os.Parcelable.Creator<android.telephony.EmergencyRegistrationResult>() { // from class: android.telephony.EmergencyRegistrationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.EmergencyRegistrationResult createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.EmergencyRegistrationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.EmergencyRegistrationResult[] newArray(int i) {
            return new android.telephony.EmergencyRegistrationResult[i];
        }
    };
    private int mAccessNetworkType;
    private java.lang.String mCountryIso;
    private int mDomain;
    private boolean mIsEmcBearerSupported;
    private boolean mIsVopsSupported;
    private java.lang.String mMcc;
    private java.lang.String mMnc;
    private int mNwProvidedEmc;
    private int mNwProvidedEmf;
    private int mRegState;

    public EmergencyRegistrationResult(int i, int i2, int i3, boolean z, boolean z2, int i4, int i5, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mAccessNetworkType = i;
        this.mRegState = i2;
        this.mDomain = i3;
        this.mIsVopsSupported = z;
        this.mIsEmcBearerSupported = z2;
        this.mNwProvidedEmc = i4;
        this.mNwProvidedEmf = i5;
        this.mMcc = str;
        this.mMnc = str2;
        this.mCountryIso = str3;
    }

    public EmergencyRegistrationResult(android.telephony.EmergencyRegistrationResult emergencyRegistrationResult) {
        this.mAccessNetworkType = emergencyRegistrationResult.mAccessNetworkType;
        this.mRegState = emergencyRegistrationResult.mRegState;
        this.mDomain = emergencyRegistrationResult.mDomain;
        this.mIsVopsSupported = emergencyRegistrationResult.mIsVopsSupported;
        this.mIsEmcBearerSupported = emergencyRegistrationResult.mIsEmcBearerSupported;
        this.mNwProvidedEmc = emergencyRegistrationResult.mNwProvidedEmc;
        this.mNwProvidedEmf = emergencyRegistrationResult.mNwProvidedEmf;
        this.mMcc = emergencyRegistrationResult.mMcc;
        this.mMnc = emergencyRegistrationResult.mMnc;
        this.mCountryIso = emergencyRegistrationResult.mCountryIso;
    }

    private EmergencyRegistrationResult(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAccessNetwork() {
        return this.mAccessNetworkType;
    }

    public int getRegState() {
        return this.mRegState;
    }

    public int getDomain() {
        return this.mDomain;
    }

    public boolean isVopsSupported() {
        return this.mIsVopsSupported;
    }

    public boolean isEmcBearerSupported() {
        return this.mIsEmcBearerSupported;
    }

    public int getNwProvidedEmc() {
        return this.mNwProvidedEmc;
    }

    public int getNwProvidedEmf() {
        return this.mNwProvidedEmf;
    }

    public java.lang.String getMcc() {
        return this.mMcc;
    }

    public java.lang.String getMnc() {
        return this.mMnc;
    }

    public java.lang.String getCountryIso() {
        return this.mCountryIso;
    }

    public java.lang.String toString() {
        return "{ accessNetwork=" + android.telephony.AccessNetworkConstants.AccessNetworkType.toString(this.mAccessNetworkType) + ", regState=" + android.telephony.NetworkRegistrationInfo.registrationStateToString(this.mRegState) + ", domain=" + android.telephony.NetworkRegistrationInfo.domainToString(this.mDomain) + ", vops=" + this.mIsVopsSupported + ", emcBearer=" + this.mIsEmcBearerSupported + ", emc=" + this.mNwProvidedEmc + ", emf=" + this.mNwProvidedEmf + ", mcc=" + this.mMcc + ", mnc=" + this.mMnc + ", iso=" + this.mCountryIso + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.EmergencyRegistrationResult emergencyRegistrationResult = (android.telephony.EmergencyRegistrationResult) obj;
        if (this.mAccessNetworkType == emergencyRegistrationResult.mAccessNetworkType && this.mRegState == emergencyRegistrationResult.mRegState && this.mDomain == emergencyRegistrationResult.mDomain && this.mIsVopsSupported == emergencyRegistrationResult.mIsVopsSupported && this.mIsEmcBearerSupported == emergencyRegistrationResult.mIsEmcBearerSupported && this.mNwProvidedEmc == emergencyRegistrationResult.mNwProvidedEmc && this.mNwProvidedEmf == emergencyRegistrationResult.mNwProvidedEmf && android.text.TextUtils.equals(this.mMcc, emergencyRegistrationResult.mMcc) && android.text.TextUtils.equals(this.mMnc, emergencyRegistrationResult.mMnc) && android.text.TextUtils.equals(this.mCountryIso, emergencyRegistrationResult.mCountryIso)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAccessNetworkType), java.lang.Integer.valueOf(this.mRegState), java.lang.Integer.valueOf(this.mDomain), java.lang.Boolean.valueOf(this.mIsVopsSupported), java.lang.Boolean.valueOf(this.mIsEmcBearerSupported), java.lang.Integer.valueOf(this.mNwProvidedEmc), java.lang.Integer.valueOf(this.mNwProvidedEmf), this.mMcc, this.mMnc, this.mCountryIso);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAccessNetworkType);
        parcel.writeInt(this.mRegState);
        parcel.writeInt(this.mDomain);
        parcel.writeBoolean(this.mIsVopsSupported);
        parcel.writeBoolean(this.mIsEmcBearerSupported);
        parcel.writeInt(this.mNwProvidedEmc);
        parcel.writeInt(this.mNwProvidedEmf);
        parcel.writeString8(this.mMcc);
        parcel.writeString8(this.mMnc);
        parcel.writeString8(this.mCountryIso);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mAccessNetworkType = parcel.readInt();
        this.mRegState = parcel.readInt();
        this.mDomain = parcel.readInt();
        this.mIsVopsSupported = parcel.readBoolean();
        this.mIsEmcBearerSupported = parcel.readBoolean();
        this.mNwProvidedEmc = parcel.readInt();
        this.mNwProvidedEmf = parcel.readInt();
        this.mMcc = parcel.readString8();
        this.mMnc = parcel.readString8();
        this.mCountryIso = parcel.readString8();
    }
}
