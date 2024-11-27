package android.service.carrier;

/* loaded from: classes3.dex */
public class CarrierIdentifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.carrier.CarrierIdentifier> CREATOR = new android.os.Parcelable.Creator<android.service.carrier.CarrierIdentifier>() { // from class: android.service.carrier.CarrierIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.carrier.CarrierIdentifier createFromParcel(android.os.Parcel parcel) {
            return new android.service.carrier.CarrierIdentifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.carrier.CarrierIdentifier[] newArray(int i) {
            return new android.service.carrier.CarrierIdentifier[i];
        }
    };
    private int mCarrierId;
    private java.lang.String mGid1;
    private java.lang.String mGid2;
    private java.lang.String mImsi;
    private java.lang.String mMcc;
    private java.lang.String mMnc;
    private int mSpecificCarrierId;
    private java.lang.String mSpn;

    public interface MatchType {
        public static final int ALL = 0;
        public static final int GID1 = 3;
        public static final int GID2 = 4;
        public static final int IMSI_PREFIX = 2;
        public static final int SPN = 1;
    }

    public CarrierIdentifier(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6) {
        this(str, str2, str3, str4, str5, str6, -1, -1);
    }

    public CarrierIdentifier(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, int i, int i2) {
        this.mCarrierId = -1;
        this.mSpecificCarrierId = -1;
        this.mMcc = str;
        this.mMnc = str2;
        this.mSpn = str3;
        this.mImsi = str4;
        this.mGid1 = str5;
        this.mGid2 = str6;
        this.mCarrierId = i;
        this.mSpecificCarrierId = i2;
    }

    public CarrierIdentifier(byte[] bArr, java.lang.String str, java.lang.String str2) {
        this.mCarrierId = -1;
        this.mSpecificCarrierId = -1;
        if (bArr.length != 3) {
            throw new java.lang.IllegalArgumentException("MCC & MNC must be set by a 3-byte array: byte[" + bArr.length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        java.lang.String bytesToHexString = com.android.internal.telephony.uicc.IccUtils.bytesToHexString(bArr);
        this.mMcc = new java.lang.String(new char[]{bytesToHexString.charAt(1), bytesToHexString.charAt(0), bytesToHexString.charAt(3)});
        if (bytesToHexString.charAt(2) == 'F') {
            this.mMnc = new java.lang.String(new char[]{bytesToHexString.charAt(5), bytesToHexString.charAt(4)});
        } else {
            this.mMnc = new java.lang.String(new char[]{bytesToHexString.charAt(5), bytesToHexString.charAt(4), bytesToHexString.charAt(2)});
        }
        this.mGid1 = str;
        this.mGid2 = str2;
        this.mSpn = null;
        this.mImsi = null;
    }

    public CarrierIdentifier(android.os.Parcel parcel) {
        this.mCarrierId = -1;
        this.mSpecificCarrierId = -1;
        readFromParcel(parcel);
    }

    public java.lang.String getMcc() {
        return this.mMcc;
    }

    public java.lang.String getMnc() {
        return this.mMnc;
    }

    public java.lang.String getSpn() {
        return this.mSpn;
    }

    public java.lang.String getImsi() {
        return this.mImsi;
    }

    public java.lang.String getGid1() {
        return this.mGid1;
    }

    public java.lang.String getGid2() {
        return this.mGid2;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public int getSpecificCarrierId() {
        return this.mSpecificCarrierId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.carrier.CarrierIdentifier carrierIdentifier = (android.service.carrier.CarrierIdentifier) obj;
        if (java.util.Objects.equals(this.mMcc, carrierIdentifier.mMcc) && java.util.Objects.equals(this.mMnc, carrierIdentifier.mMnc) && java.util.Objects.equals(this.mSpn, carrierIdentifier.mSpn) && java.util.Objects.equals(this.mImsi, carrierIdentifier.mImsi) && java.util.Objects.equals(this.mGid1, carrierIdentifier.mGid1) && java.util.Objects.equals(this.mGid2, carrierIdentifier.mGid2) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCarrierId), java.lang.Integer.valueOf(carrierIdentifier.mCarrierId)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mSpecificCarrierId), java.lang.Integer.valueOf(carrierIdentifier.mSpecificCarrierId))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mMcc, this.mMnc, this.mSpn, this.mImsi, this.mGid1, this.mGid2, java.lang.Integer.valueOf(this.mCarrierId), java.lang.Integer.valueOf(this.mSpecificCarrierId));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mMcc);
        parcel.writeString(this.mMnc);
        parcel.writeString(this.mSpn);
        parcel.writeString(this.mImsi);
        parcel.writeString(this.mGid1);
        parcel.writeString(this.mGid2);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mSpecificCarrierId);
    }

    public java.lang.String toString() {
        return "CarrierIdentifier{mcc=" + this.mMcc + ",mnc=" + this.mMnc + ",spn=" + this.mSpn + ",imsi=" + com.android.telephony.Rlog.pii(false, (java.lang.Object) this.mImsi) + ",gid1=" + this.mGid1 + ",gid2=" + this.mGid2 + ",carrierid=" + this.mCarrierId + ",specificCarrierId=" + this.mSpecificCarrierId + "}";
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mMcc = parcel.readString();
        this.mMnc = parcel.readString();
        this.mSpn = parcel.readString();
        this.mImsi = parcel.readString();
        this.mGid1 = parcel.readString();
        this.mGid2 = parcel.readString();
        this.mCarrierId = parcel.readInt();
        this.mSpecificCarrierId = parcel.readInt();
    }
}
