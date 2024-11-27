package android.telephony;

/* loaded from: classes3.dex */
public abstract class CellIdentity implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentity> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentity>() { // from class: android.telephony.CellIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentity createFromParcel(android.os.Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return android.telephony.CellIdentityGsm.createFromParcelBody(parcel);
                case 2:
                    return android.telephony.CellIdentityCdma.createFromParcelBody(parcel);
                case 3:
                    return android.telephony.CellIdentityLte.createFromParcelBody(parcel);
                case 4:
                    return android.telephony.CellIdentityWcdma.createFromParcelBody(parcel);
                case 5:
                    return android.telephony.CellIdentityTdscdma.createFromParcelBody(parcel);
                case 6:
                    return android.telephony.CellIdentityNr.createFromParcelBody(parcel);
                default:
                    throw new java.lang.IllegalArgumentException("Bad Cell identity Parcel");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentity[] newArray(int i) {
            return new android.telephony.CellIdentity[i];
        }
    };
    public static final int INVALID_CHANNEL_NUMBER = Integer.MAX_VALUE;
    public static final int MCC_LENGTH = 3;
    public static final int MNC_MAX_LENGTH = 3;
    public static final int MNC_MIN_LENGTH = 2;
    protected java.lang.String mAlphaLong;
    protected java.lang.String mAlphaShort;
    protected java.lang.String mGlobalCellId;
    protected final java.lang.String mMccStr;
    protected final java.lang.String mMncStr;
    protected final java.lang.String mTag;
    protected final int mType;

    @android.annotation.SystemApi
    public abstract android.telephony.CellLocation asCellLocation();

    @android.annotation.SystemApi
    public abstract android.telephony.CellIdentity sanitizeLocationInfo();

    protected abstract void updateGlobalCellId();

    protected CellIdentity(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        this.mTag = str;
        this.mType = i;
        if (str2 == null || isMcc(str2)) {
            this.mMccStr = str2;
        } else if (str2.isEmpty() || str2.equals(java.lang.String.valueOf(Integer.MAX_VALUE))) {
            this.mMccStr = null;
        } else {
            this.mMccStr = null;
            log("invalid MCC format: " + str2);
        }
        if (str3 == null || isMnc(str3)) {
            this.mMncStr = str3;
        } else if (str3.isEmpty() || str3.equals(java.lang.String.valueOf(Integer.MAX_VALUE))) {
            this.mMncStr = null;
        } else {
            this.mMncStr = null;
            log("invalid MNC format: " + str3);
        }
        if ((this.mMccStr != null && this.mMncStr == null) || (this.mMccStr == null && this.mMncStr != null)) {
            android.telephony.AnomalyReporter.reportAnomaly(java.util.UUID.fromString("e257ae06-ac0a-44c0-ba63-823b9f07b3e4"), "CellIdentity Missing Half of PLMN ID");
        }
        this.mAlphaLong = str4;
        this.mAlphaShort = str5;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getMccString() {
        return this.mMccStr;
    }

    public java.lang.String getMncString() {
        return this.mMncStr;
    }

    public int getChannelNumber() {
        return Integer.MAX_VALUE;
    }

    public java.lang.CharSequence getOperatorAlphaLong() {
        return this.mAlphaLong;
    }

    public void setOperatorAlphaLong(java.lang.String str) {
        this.mAlphaLong = str;
    }

    public java.lang.CharSequence getOperatorAlphaShort() {
        return this.mAlphaShort;
    }

    public void setOperatorAlphaShort(java.lang.String str) {
        this.mAlphaShort = str;
    }

    public java.lang.String getGlobalCellId() {
        return this.mGlobalCellId;
    }

    public boolean isSameCell(android.telephony.CellIdentity cellIdentity) {
        if (cellIdentity == null || getClass() != cellIdentity.getClass()) {
            return false;
        }
        return android.text.TextUtils.equals(getGlobalCellId(), cellIdentity.getGlobalCellId());
    }

    public java.lang.String getPlmn() {
        if (this.mMccStr == null || this.mMncStr == null) {
            return null;
        }
        return this.mMccStr + this.mMncStr;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellIdentity)) {
            return false;
        }
        android.telephony.CellIdentity cellIdentity = (android.telephony.CellIdentity) obj;
        return this.mType == cellIdentity.mType && android.text.TextUtils.equals(this.mMccStr, cellIdentity.mMccStr) && android.text.TextUtils.equals(this.mMncStr, cellIdentity.mMncStr) && android.text.TextUtils.equals(this.mAlphaLong, cellIdentity.mAlphaLong) && android.text.TextUtils.equals(this.mAlphaShort, cellIdentity.mAlphaShort);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mAlphaLong, this.mAlphaShort, this.mMccStr, this.mMncStr, java.lang.Integer.valueOf(this.mType));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(i);
        parcel.writeString(this.mMccStr);
        parcel.writeString(this.mMncStr);
        parcel.writeString(this.mAlphaLong);
        parcel.writeString(this.mAlphaShort);
    }

    public static boolean isValidPlmn(java.lang.String str) {
        return str.length() >= 5 && str.length() <= 6 && isMcc(str.substring(0, 3)) && isMnc(str.substring(3));
    }

    protected CellIdentity(java.lang.String str, int i, android.os.Parcel parcel) {
        this(str, i, parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
    }

    protected void log(java.lang.String str) {
        com.android.telephony.Rlog.w(this.mTag, str);
    }

    protected static final int inRangeOrUnavailable(int i, int i2, int i3) {
        if (i < i2 || i > i3) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    protected static final long inRangeOrUnavailable(long j, long j2, long j3) {
        if (j < j2 || j > j3) {
            return Long.MAX_VALUE;
        }
        return j;
    }

    protected static final int inRangeOrUnavailable(int i, int i2, int i3, int i4) {
        if ((i < i2 || i > i3) && i != i4) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    private static boolean isMcc(java.lang.String str) {
        if (str.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isMnc(java.lang.String str) {
        if (str.length() < 2 || str.length() > 3) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
