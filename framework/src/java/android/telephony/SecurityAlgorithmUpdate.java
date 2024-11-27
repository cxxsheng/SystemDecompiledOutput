package android.telephony;

/* loaded from: classes3.dex */
public final class SecurityAlgorithmUpdate implements android.os.Parcelable {
    public static final int CONNECTION_EVENT_AS_SIGNALLING_5G = 11;
    public static final int CONNECTION_EVENT_AS_SIGNALLING_LTE = 5;
    public static final int CONNECTION_EVENT_CS_SIGNALLING_3G = 2;
    public static final int CONNECTION_EVENT_CS_SIGNALLING_GSM = 0;
    public static final int CONNECTION_EVENT_NAS_SIGNALLING_5G = 10;
    public static final int CONNECTION_EVENT_NAS_SIGNALLING_LTE = 4;
    public static final int CONNECTION_EVENT_PS_SIGNALLING_3G = 3;
    public static final int CONNECTION_EVENT_PS_SIGNALLING_GPRS = 1;
    public static final int CONNECTION_EVENT_VOLTE_RTP = 8;
    public static final int CONNECTION_EVENT_VOLTE_RTP_SOS = 9;
    public static final int CONNECTION_EVENT_VOLTE_SIP = 6;
    public static final int CONNECTION_EVENT_VOLTE_SIP_SOS = 7;
    public static final int CONNECTION_EVENT_VONR_RTP = 14;
    public static final int CONNECTION_EVENT_VONR_RTP_SOS = 15;
    public static final int CONNECTION_EVENT_VONR_SIP = 12;
    public static final int CONNECTION_EVENT_VONR_SIP_SOS = 13;
    public static final android.os.Parcelable.Creator<android.telephony.SecurityAlgorithmUpdate> CREATOR = new android.os.Parcelable.Creator<android.telephony.SecurityAlgorithmUpdate>() { // from class: android.telephony.SecurityAlgorithmUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SecurityAlgorithmUpdate createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SecurityAlgorithmUpdate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SecurityAlgorithmUpdate[] newArray(int i) {
            return new android.telephony.SecurityAlgorithmUpdate[i];
        }
    };
    public static final int SECURITY_ALGORITHM_A50 = 0;
    public static final int SECURITY_ALGORITHM_A51 = 1;
    public static final int SECURITY_ALGORITHM_A52 = 2;
    public static final int SECURITY_ALGORITHM_A53 = 3;
    public static final int SECURITY_ALGORITHM_A54 = 4;
    public static final int SECURITY_ALGORITHM_AES_CBC = 71;
    public static final int SECURITY_ALGORITHM_AES_EDE3_CBC = 73;
    public static final int SECURITY_ALGORITHM_AES_GCM = 69;
    public static final int SECURITY_ALGORITHM_AES_GMAC = 70;
    public static final int SECURITY_ALGORITHM_AUTH_HMAC_SHA2_256_128 = 101;
    public static final int SECURITY_ALGORITHM_DES_EDE3_CBC = 72;
    public static final int SECURITY_ALGORITHM_EEA0 = 41;
    public static final int SECURITY_ALGORITHM_EEA1 = 42;
    public static final int SECURITY_ALGORITHM_EEA2 = 43;
    public static final int SECURITY_ALGORITHM_EEA3 = 44;
    public static final int SECURITY_ALGORITHM_ENCR_AES_CBC = 100;
    public static final int SECURITY_ALGORITHM_ENCR_AES_GCM_16 = 99;
    public static final int SECURITY_ALGORITHM_GEA0 = 14;
    public static final int SECURITY_ALGORITHM_GEA1 = 15;
    public static final int SECURITY_ALGORITHM_GEA2 = 16;
    public static final int SECURITY_ALGORITHM_GEA3 = 17;
    public static final int SECURITY_ALGORITHM_GEA4 = 18;
    public static final int SECURITY_ALGORITHM_GEA5 = 19;
    public static final int SECURITY_ALGORITHM_HMAC_MD5_96 = 75;
    public static final int SECURITY_ALGORITHM_HMAC_SHA1_96 = 74;
    public static final int SECURITY_ALGORITHM_IMS_NULL = 67;
    public static final int SECURITY_ALGORITHM_NEA0 = 55;
    public static final int SECURITY_ALGORITHM_NEA1 = 56;
    public static final int SECURITY_ALGORITHM_NEA2 = 57;
    public static final int SECURITY_ALGORITHM_NEA3 = 58;
    public static final int SECURITY_ALGORITHM_ORYX = 124;
    public static final int SECURITY_ALGORITHM_OTHER = 114;
    public static final int SECURITY_ALGORITHM_RTP = 85;
    public static final int SECURITY_ALGORITHM_SIP_NO_IPSEC_CONFIG = 66;
    public static final int SECURITY_ALGORITHM_SIP_NULL = 68;
    public static final int SECURITY_ALGORITHM_SRTP_AES_COUNTER = 87;
    public static final int SECURITY_ALGORITHM_SRTP_AES_F8 = 88;
    public static final int SECURITY_ALGORITHM_SRTP_HMAC_SHA1 = 89;
    public static final int SECURITY_ALGORITHM_SRTP_NULL = 86;
    public static final int SECURITY_ALGORITHM_UEA0 = 29;
    public static final int SECURITY_ALGORITHM_UEA1 = 30;
    public static final int SECURITY_ALGORITHM_UEA2 = 31;
    public static final int SECURITY_ALGORITHM_UNKNOWN = 113;
    private static final java.lang.String TAG = "SecurityAlgorithmUpdate";
    private int mConnectionEvent;
    private int mEncryption;
    private int mIntegrity;
    private boolean mIsUnprotectedEmergency;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionEvent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SecurityAlgorithm {
    }

    public SecurityAlgorithmUpdate(int i, int i2, int i3, boolean z) {
        this.mConnectionEvent = i;
        this.mEncryption = i2;
        this.mIntegrity = i3;
        this.mIsUnprotectedEmergency = z;
    }

    private SecurityAlgorithmUpdate(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getConnectionEvent() {
        return this.mConnectionEvent;
    }

    public int getEncryption() {
        return this.mEncryption;
    }

    public int getIntegrity() {
        return this.mIntegrity;
    }

    public boolean isUnprotectedEmergency() {
        return this.mIsUnprotectedEmergency;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mConnectionEvent);
        parcel.writeInt(this.mEncryption);
        parcel.writeInt(this.mIntegrity);
        parcel.writeBoolean(this.mIsUnprotectedEmergency);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mConnectionEvent = parcel.readInt();
        this.mEncryption = parcel.readInt();
        this.mIntegrity = parcel.readInt();
        this.mIsUnprotectedEmergency = parcel.readBoolean();
    }

    public java.lang.String toString() {
        return "SecurityAlgorithmUpdate:{ mConnectionEvent = " + this.mConnectionEvent + " mEncryption = " + this.mEncryption + " mIntegrity = " + this.mIntegrity + " mIsUnprotectedEmergency = " + this.mIsUnprotectedEmergency;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.SecurityAlgorithmUpdate)) {
            return false;
        }
        android.telephony.SecurityAlgorithmUpdate securityAlgorithmUpdate = (android.telephony.SecurityAlgorithmUpdate) obj;
        return this.mConnectionEvent == securityAlgorithmUpdate.mConnectionEvent && this.mEncryption == securityAlgorithmUpdate.mEncryption && this.mIntegrity == securityAlgorithmUpdate.mIntegrity && this.mIsUnprotectedEmergency == securityAlgorithmUpdate.mIsUnprotectedEmergency;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mConnectionEvent), java.lang.Integer.valueOf(this.mEncryption), java.lang.Integer.valueOf(this.mIntegrity), java.lang.Boolean.valueOf(this.mIsUnprotectedEmergency));
    }
}
