package android.telephony;

/* loaded from: classes3.dex */
public final class CarrierInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CarrierInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.CarrierInfo>() { // from class: android.telephony.CarrierInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CarrierInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CarrierInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CarrierInfo[] newArray(int i) {
            return new android.telephony.CarrierInfo[i];
        }
    };
    private java.util.List<java.lang.String> mEhplmn;
    private java.lang.String mGid1;
    private java.lang.String mGid2;
    private java.lang.String mIccid;
    private java.lang.String mImpi;
    private java.lang.String mImsiPrefix;
    private java.lang.String mMcc;
    private java.lang.String mMnc;
    private java.lang.String mSpn;

    public java.lang.String getMcc() {
        return this.mMcc;
    }

    public java.lang.String getMnc() {
        return this.mMnc;
    }

    public java.lang.String getSpn() {
        return this.mSpn;
    }

    public java.lang.String getGid1() {
        return this.mGid1;
    }

    public java.lang.String getGid2() {
        return this.mGid2;
    }

    public java.lang.String getImsiPrefix() {
        return this.mImsiPrefix;
    }

    public java.lang.String getIccid() {
        return this.mIccid;
    }

    public java.lang.String getImpi() {
        return this.mImpi;
    }

    public java.util.List<java.lang.String> getEhplmn() {
        return this.mEhplmn;
    }

    public CarrierInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, java.util.List<java.lang.String> list) {
        this.mMcc = str;
        this.mMnc = str2;
        this.mSpn = str3;
        this.mGid1 = str4;
        this.mGid2 = str5;
        this.mImsiPrefix = str6;
        this.mIccid = str7;
        this.mImpi = str8;
        this.mEhplmn = list;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mMcc);
        parcel.writeString8(this.mMnc);
        parcel.writeString8(this.mSpn);
        parcel.writeString8(this.mGid1);
        parcel.writeString8(this.mGid2);
        parcel.writeString8(this.mImsiPrefix);
        parcel.writeString8(this.mIccid);
        parcel.writeString8(this.mImpi);
        parcel.writeStringList(this.mEhplmn);
    }

    public CarrierInfo(android.os.Parcel parcel) {
        this.mEhplmn = new java.util.ArrayList();
        this.mMcc = parcel.readString8();
        this.mMnc = parcel.readString8();
        this.mSpn = parcel.readString8();
        this.mGid1 = parcel.readString8();
        this.mGid2 = parcel.readString8();
        this.mImsiPrefix = parcel.readString8();
        this.mIccid = parcel.readString8();
        this.mImpi = parcel.readString8();
        parcel.readStringList(this.mEhplmn);
    }

    public java.lang.String toString() {
        return "CarrierInfo MCC = " + this.mMcc + "   MNC = " + this.mMnc + "  SPN = " + this.mSpn + "   GID1 = " + this.mGid1 + "   GID2 = " + this.mGid2 + "   IMSI = " + getPrintableImsi() + "   ICCID = " + android.telephony.SubscriptionInfo.getPrintableId(this.mIccid) + "  IMPI = " + this.mImpi + "  EHPLMN = [ " + getEhplmn_toString() + " ]";
    }

    private java.lang.String getEhplmn_toString() {
        return java.lang.String.join("  ", this.mEhplmn);
    }

    private java.lang.String getPrintableImsi() {
        return (this.mImsiPrefix == null || this.mImsiPrefix.length() <= 6) ? this.mImsiPrefix : this.mImsiPrefix.substring(0, 6) + com.android.telephony.Rlog.pii(com.android.telephony.Rlog.isLoggable("CarrierInfo", 2), this.mImsiPrefix.substring(6));
    }
}
