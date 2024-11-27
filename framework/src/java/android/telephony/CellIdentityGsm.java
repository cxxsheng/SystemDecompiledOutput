package android.telephony;

/* loaded from: classes3.dex */
public final class CellIdentityGsm extends android.telephony.CellIdentity {
    private static final boolean DBG = false;
    private static final int MAX_ARFCN = 65535;
    private static final int MAX_BSIC = 63;
    private static final int MAX_CID = 65535;
    private static final int MAX_LAC = 65535;
    private final android.util.ArraySet<java.lang.String> mAdditionalPlmns;
    private final int mArfcn;
    private final int mBsic;
    private final int mCid;
    private final int mLac;
    private static final java.lang.String TAG = android.telephony.CellIdentityGsm.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentityGsm> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentityGsm>() { // from class: android.telephony.CellIdentityGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityGsm createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellIdentityGsm.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityGsm[] newArray(int i) {
            return new android.telephony.CellIdentityGsm[i];
        }
    };

    public CellIdentityGsm() {
        super(TAG, 1, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mArfcn = Integer.MAX_VALUE;
        this.mBsic = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new android.util.ArraySet<>();
        this.mGlobalCellId = null;
    }

    public CellIdentityGsm(int i, int i2, int i3, int i4, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.Collection<java.lang.String> collection) {
        super(TAG, 1, str, str2, str3, str4);
        this.mLac = inRangeOrUnavailable(i, 0, 65535);
        this.mCid = inRangeOrUnavailable(i2, 0, 65535);
        this.mArfcn = inRangeOrUnavailable(i3, 0, 65535);
        this.mBsic = inRangeOrUnavailable(i4, 0, 63);
        this.mAdditionalPlmns = new android.util.ArraySet<>(collection.size());
        for (java.lang.String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        updateGlobalCellId();
    }

    private CellIdentityGsm(android.telephony.CellIdentityGsm cellIdentityGsm) {
        this(cellIdentityGsm.mLac, cellIdentityGsm.mCid, cellIdentityGsm.mArfcn, cellIdentityGsm.mBsic, cellIdentityGsm.mMccStr, cellIdentityGsm.mMncStr, cellIdentityGsm.mAlphaLong, cellIdentityGsm.mAlphaShort, cellIdentityGsm.mAdditionalPlmns);
    }

    android.telephony.CellIdentityGsm copy() {
        return new android.telephony.CellIdentityGsm(this);
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellIdentityGsm sanitizeLocationInfo() {
        return new android.telephony.CellIdentityGsm(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        java.lang.String plmn = getPlmn();
        if (plmn == null || this.mLac == Integer.MAX_VALUE || this.mCid == Integer.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = plmn + android.text.TextUtils.formatSimple("%04x%04x", java.lang.Integer.valueOf(this.mLac), java.lang.Integer.valueOf(this.mCid));
    }

    @java.lang.Deprecated
    public int getMcc() {
        if (this.mMccStr != null) {
            return java.lang.Integer.valueOf(this.mMccStr).intValue();
        }
        return Integer.MAX_VALUE;
    }

    @java.lang.Deprecated
    public int getMnc() {
        if (this.mMncStr != null) {
            return java.lang.Integer.valueOf(this.mMncStr).intValue();
        }
        return Integer.MAX_VALUE;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getArfcn() {
        return this.mArfcn;
    }

    public int getBsic() {
        return this.mBsic;
    }

    public java.lang.String getMobileNetworkOperator() {
        if (this.mMccStr == null || this.mMncStr == null) {
            return null;
        }
        return this.mMccStr + this.mMncStr;
    }

    @Override // android.telephony.CellIdentity
    public java.lang.String getMccString() {
        return this.mMccStr;
    }

    @Override // android.telephony.CellIdentity
    public java.lang.String getMncString() {
        return this.mMncStr;
    }

    @Override // android.telephony.CellIdentity
    public int getChannelNumber() {
        return this.mArfcn;
    }

    public java.util.Set<java.lang.String> getAdditionalPlmns() {
        return java.util.Collections.unmodifiableSet(this.mAdditionalPlmns);
    }

    @java.lang.Deprecated
    public int getPsc() {
        return Integer.MAX_VALUE;
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.gsm.GsmCellLocation asCellLocation() {
        android.telephony.gsm.GsmCellLocation gsmCellLocation = new android.telephony.gsm.GsmCellLocation();
        gsmCellLocation.setLacAndCid(this.mLac != Integer.MAX_VALUE ? this.mLac : -1, this.mCid != Integer.MAX_VALUE ? this.mCid : -1);
        gsmCellLocation.setPsc(-1);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLac), java.lang.Integer.valueOf(this.mCid), java.lang.Integer.valueOf(this.mAdditionalPlmns.hashCode()), java.lang.Integer.valueOf(super.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellIdentityGsm)) {
            return false;
        }
        android.telephony.CellIdentityGsm cellIdentityGsm = (android.telephony.CellIdentityGsm) obj;
        return this.mLac == cellIdentityGsm.mLac && this.mCid == cellIdentityGsm.mCid && this.mArfcn == cellIdentityGsm.mArfcn && this.mBsic == cellIdentityGsm.mBsic && android.text.TextUtils.equals(this.mMccStr, cellIdentityGsm.mMccStr) && android.text.TextUtils.equals(this.mMncStr, cellIdentityGsm.mMncStr) && this.mAdditionalPlmns.equals(cellIdentityGsm.mAdditionalPlmns) && super.equals(obj);
    }

    public java.lang.String toString() {
        return TAG + ":{ mLac=" + this.mLac + " mCid=" + this.mCid + " mArfcn=" + this.mArfcn + " mBsic=0x" + java.lang.Integer.toHexString(this.mBsic) + " mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mAdditionalPlmns=" + this.mAdditionalPlmns + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, 1);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mArfcn);
        parcel.writeInt(this.mBsic);
        parcel.writeArraySet(this.mAdditionalPlmns);
    }

    private CellIdentityGsm(android.os.Parcel parcel) {
        super(TAG, 1, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mArfcn = parcel.readInt();
        this.mBsic = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        updateGlobalCellId();
    }

    protected static android.telephony.CellIdentityGsm createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellIdentityGsm(parcel);
    }
}
