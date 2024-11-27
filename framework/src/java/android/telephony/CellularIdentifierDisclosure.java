package android.telephony;

/* loaded from: classes3.dex */
public final class CellularIdentifierDisclosure implements android.os.Parcelable {
    public static final int CELLULAR_IDENTIFIER_IMEI = 2;
    public static final int CELLULAR_IDENTIFIER_IMSI = 1;
    public static final int CELLULAR_IDENTIFIER_SUCI = 3;
    public static final int CELLULAR_IDENTIFIER_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.telephony.CellularIdentifierDisclosure> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellularIdentifierDisclosure>() { // from class: android.telephony.CellularIdentifierDisclosure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellularIdentifierDisclosure createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellularIdentifierDisclosure(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellularIdentifierDisclosure[] newArray(int i) {
            return new android.telephony.CellularIdentifierDisclosure[i];
        }
    };
    public static final int NAS_PROTOCOL_MESSAGE_ATTACH_REQUEST = 1;
    public static final int NAS_PROTOCOL_MESSAGE_AUTHENTICATION_AND_CIPHERING_RESPONSE = 6;
    public static final int NAS_PROTOCOL_MESSAGE_CM_REESTABLISHMENT_REQUEST = 9;
    public static final int NAS_PROTOCOL_MESSAGE_CM_SERVICE_REQUEST = 10;
    public static final int NAS_PROTOCOL_MESSAGE_DEREGISTRATION_REQUEST = 8;
    public static final int NAS_PROTOCOL_MESSAGE_DETACH_REQUEST = 3;
    public static final int NAS_PROTOCOL_MESSAGE_IDENTITY_RESPONSE = 2;
    public static final int NAS_PROTOCOL_MESSAGE_IMSI_DETACH_INDICATION = 11;
    public static final int NAS_PROTOCOL_MESSAGE_LOCATION_UPDATE_REQUEST = 5;
    public static final int NAS_PROTOCOL_MESSAGE_REGISTRATION_REQUEST = 7;
    public static final int NAS_PROTOCOL_MESSAGE_TRACKING_AREA_UPDATE_REQUEST = 4;
    public static final int NAS_PROTOCOL_MESSAGE_UNKNOWN = 0;
    private static final java.lang.String TAG = "CellularIdentifierDisclosure";
    private int mCellularIdentifier;
    private boolean mIsEmergency;
    private int mNasProtocolMessage;
    private java.lang.String mPlmn;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CellularIdentifier {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NasProtocolMessage {
    }

    public CellularIdentifierDisclosure(int i, int i2, java.lang.String str, boolean z) {
        this.mNasProtocolMessage = i;
        this.mCellularIdentifier = i2;
        this.mPlmn = str;
        this.mIsEmergency = z;
    }

    private CellularIdentifierDisclosure(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getNasProtocolMessage() {
        return this.mNasProtocolMessage;
    }

    public int getCellularIdentifier() {
        return this.mCellularIdentifier;
    }

    public java.lang.String getPlmn() {
        return this.mPlmn;
    }

    public boolean isEmergency() {
        return this.mIsEmergency;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mNasProtocolMessage);
        parcel.writeInt(this.mCellularIdentifier);
        parcel.writeBoolean(this.mIsEmergency);
        parcel.writeString8(this.mPlmn);
    }

    public java.lang.String toString() {
        return "CellularIdentifierDisclosure:{ mNasProtocolMessage = " + this.mNasProtocolMessage + " mCellularIdentifier = " + this.mCellularIdentifier + " mIsEmergency = " + this.mIsEmergency + " mPlmn = " + this.mPlmn;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellularIdentifierDisclosure)) {
            return false;
        }
        android.telephony.CellularIdentifierDisclosure cellularIdentifierDisclosure = (android.telephony.CellularIdentifierDisclosure) obj;
        return this.mNasProtocolMessage == cellularIdentifierDisclosure.mNasProtocolMessage && this.mCellularIdentifier == cellularIdentifierDisclosure.mCellularIdentifier && this.mIsEmergency == cellularIdentifierDisclosure.mIsEmergency && this.mPlmn.equals(cellularIdentifierDisclosure.mPlmn);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mNasProtocolMessage), java.lang.Integer.valueOf(this.mCellularIdentifier), java.lang.Boolean.valueOf(this.mIsEmergency), this.mPlmn);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mNasProtocolMessage = parcel.readInt();
        this.mCellularIdentifier = parcel.readInt();
        this.mIsEmergency = parcel.readBoolean();
        this.mPlmn = parcel.readString8();
    }
}
