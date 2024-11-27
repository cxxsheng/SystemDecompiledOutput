package android.telephony;

/* loaded from: classes3.dex */
public final class CellIdentityWcdma extends android.telephony.CellIdentity {
    private static final boolean DBG = false;
    private static final int MAX_CID = 268435455;
    private static final int MAX_LAC = 65535;
    private static final int MAX_PSC = 511;
    private static final int MAX_UARFCN = 16383;
    private final android.util.ArraySet<java.lang.String> mAdditionalPlmns;
    private final int mCid;
    private final android.telephony.ClosedSubscriberGroupInfo mCsgInfo;
    private final int mLac;
    private final int mPsc;
    private final int mUarfcn;
    private static final java.lang.String TAG = android.telephony.CellIdentityWcdma.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentityWcdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentityWcdma>() { // from class: android.telephony.CellIdentityWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityWcdma createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellIdentityWcdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityWcdma[] newArray(int i) {
            return new android.telephony.CellIdentityWcdma[i];
        }
    };

    public CellIdentityWcdma() {
        super(TAG, 4, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mPsc = Integer.MAX_VALUE;
        this.mUarfcn = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new android.util.ArraySet<>();
        this.mCsgInfo = null;
        this.mGlobalCellId = null;
    }

    public CellIdentityWcdma(int i, int i2, int i3, int i4, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.Collection<java.lang.String> collection, android.telephony.ClosedSubscriberGroupInfo closedSubscriberGroupInfo) {
        super(TAG, 4, str, str2, str3, str4);
        this.mLac = inRangeOrUnavailable(i, 0, 65535);
        this.mCid = inRangeOrUnavailable(i2, 0, 268435455);
        this.mPsc = inRangeOrUnavailable(i3, 0, 511);
        this.mUarfcn = inRangeOrUnavailable(i4, 0, 16383);
        this.mAdditionalPlmns = new android.util.ArraySet<>(collection.size());
        for (java.lang.String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        this.mCsgInfo = closedSubscriberGroupInfo;
        updateGlobalCellId();
    }

    private CellIdentityWcdma(android.telephony.CellIdentityWcdma cellIdentityWcdma) {
        this(cellIdentityWcdma.mLac, cellIdentityWcdma.mCid, cellIdentityWcdma.mPsc, cellIdentityWcdma.mUarfcn, cellIdentityWcdma.mMccStr, cellIdentityWcdma.mMncStr, cellIdentityWcdma.mAlphaLong, cellIdentityWcdma.mAlphaShort, cellIdentityWcdma.mAdditionalPlmns, cellIdentityWcdma.mCsgInfo);
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellIdentityWcdma sanitizeLocationInfo() {
        return new android.telephony.CellIdentityWcdma(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns, null);
    }

    android.telephony.CellIdentityWcdma copy() {
        return new android.telephony.CellIdentityWcdma(this);
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

    public int getPsc() {
        return this.mPsc;
    }

    @Override // android.telephony.CellIdentity
    public java.lang.String getMccString() {
        return this.mMccStr;
    }

    @Override // android.telephony.CellIdentity
    public java.lang.String getMncString() {
        return this.mMncStr;
    }

    public java.lang.String getMobileNetworkOperator() {
        if (this.mMccStr == null || this.mMncStr == null) {
            return null;
        }
        return this.mMccStr + this.mMncStr;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLac), java.lang.Integer.valueOf(this.mCid), java.lang.Integer.valueOf(this.mPsc), java.lang.Integer.valueOf(this.mAdditionalPlmns.hashCode()), java.lang.Integer.valueOf(super.hashCode()));
    }

    public int getUarfcn() {
        return this.mUarfcn;
    }

    @Override // android.telephony.CellIdentity
    public int getChannelNumber() {
        return this.mUarfcn;
    }

    public java.util.Set<java.lang.String> getAdditionalPlmns() {
        return java.util.Collections.unmodifiableSet(this.mAdditionalPlmns);
    }

    public android.telephony.ClosedSubscriberGroupInfo getClosedSubscriberGroupInfo() {
        return this.mCsgInfo;
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.gsm.GsmCellLocation asCellLocation() {
        android.telephony.gsm.GsmCellLocation gsmCellLocation = new android.telephony.gsm.GsmCellLocation();
        int i = this.mLac != Integer.MAX_VALUE ? this.mLac : -1;
        int i2 = this.mCid != Integer.MAX_VALUE ? this.mCid : -1;
        int i3 = this.mPsc != Integer.MAX_VALUE ? this.mPsc : -1;
        gsmCellLocation.setLacAndCid(i, i2);
        gsmCellLocation.setPsc(i3);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellIdentityWcdma)) {
            return false;
        }
        android.telephony.CellIdentityWcdma cellIdentityWcdma = (android.telephony.CellIdentityWcdma) obj;
        return this.mLac == cellIdentityWcdma.mLac && this.mCid == cellIdentityWcdma.mCid && this.mPsc == cellIdentityWcdma.mPsc && this.mUarfcn == cellIdentityWcdma.mUarfcn && android.text.TextUtils.equals(this.mMccStr, cellIdentityWcdma.mMccStr) && android.text.TextUtils.equals(this.mMncStr, cellIdentityWcdma.mMncStr) && this.mAdditionalPlmns.equals(cellIdentityWcdma.mAdditionalPlmns) && java.util.Objects.equals(this.mCsgInfo, cellIdentityWcdma.mCsgInfo) && super.equals(obj);
    }

    public java.lang.String toString() {
        return TAG + ":{ mLac=" + this.mLac + " mCid=" + this.mCid + " mPsc=" + this.mPsc + " mUarfcn=" + this.mUarfcn + " mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mAdditionalPlmns=" + this.mAdditionalPlmns + " mCsgInfo=" + this.mCsgInfo + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, 4);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mPsc);
        parcel.writeInt(this.mUarfcn);
        parcel.writeArraySet(this.mAdditionalPlmns);
        parcel.writeParcelable(this.mCsgInfo, i);
    }

    private CellIdentityWcdma(android.os.Parcel parcel) {
        super(TAG, 4, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mPsc = parcel.readInt();
        this.mUarfcn = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        this.mCsgInfo = (android.telephony.ClosedSubscriberGroupInfo) parcel.readParcelable(null, android.telephony.ClosedSubscriberGroupInfo.class);
        updateGlobalCellId();
    }

    protected static android.telephony.CellIdentityWcdma createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellIdentityWcdma(parcel);
    }
}
