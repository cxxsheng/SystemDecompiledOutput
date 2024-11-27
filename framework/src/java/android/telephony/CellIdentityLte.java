package android.telephony;

/* loaded from: classes3.dex */
public final class CellIdentityLte extends android.telephony.CellIdentity {
    private static final boolean DBG = false;
    private static final int MAX_BANDWIDTH = 20000;
    private static final int MAX_CI = 268435455;
    private static final int MAX_EARFCN = 262143;
    private static final int MAX_PCI = 503;
    private static final int MAX_TAC = 65535;
    private final android.util.ArraySet<java.lang.String> mAdditionalPlmns;
    private final int[] mBands;
    private final int mBandwidth;
    private final int mCi;
    private android.telephony.ClosedSubscriberGroupInfo mCsgInfo;
    private final int mEarfcn;
    private final int mPci;
    private final int mTac;
    private static final java.lang.String TAG = android.telephony.CellIdentityLte.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentityLte> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentityLte>() { // from class: android.telephony.CellIdentityLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityLte createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellIdentityLte.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityLte[] newArray(int i) {
            return new android.telephony.CellIdentityLte[i];
        }
    };

    public CellIdentityLte() {
        super(TAG, 3, null, null, null, null);
        this.mCi = Integer.MAX_VALUE;
        this.mPci = Integer.MAX_VALUE;
        this.mTac = Integer.MAX_VALUE;
        this.mEarfcn = Integer.MAX_VALUE;
        this.mBands = new int[0];
        this.mBandwidth = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new android.util.ArraySet<>();
        this.mCsgInfo = null;
        this.mGlobalCellId = null;
    }

    public CellIdentityLte(int i, int i2, int i3, int i4, int i5) {
        this(i3, i4, i5, Integer.MAX_VALUE, new int[0], Integer.MAX_VALUE, java.lang.String.valueOf(i), java.lang.String.valueOf(i2), null, null, new android.util.ArraySet(), null);
    }

    public CellIdentityLte(int i, int i2, int i3, int i4, int[] iArr, int i5, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.Collection<java.lang.String> collection, android.telephony.ClosedSubscriberGroupInfo closedSubscriberGroupInfo) {
        super(TAG, 3, str, str2, str3, str4);
        this.mCi = inRangeOrUnavailable(i, 0, 268435455);
        this.mPci = inRangeOrUnavailable(i2, 0, 503);
        this.mTac = inRangeOrUnavailable(i3, 0, 65535);
        this.mEarfcn = inRangeOrUnavailable(i4, 0, 262143);
        this.mBands = iArr;
        this.mBandwidth = inRangeOrUnavailable(i5, 0, 20000);
        this.mAdditionalPlmns = new android.util.ArraySet<>(collection.size());
        for (java.lang.String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        this.mCsgInfo = closedSubscriberGroupInfo;
        updateGlobalCellId();
    }

    private CellIdentityLte(android.telephony.CellIdentityLte cellIdentityLte) {
        this(cellIdentityLte.mCi, cellIdentityLte.mPci, cellIdentityLte.mTac, cellIdentityLte.mEarfcn, cellIdentityLte.mBands, cellIdentityLte.mBandwidth, cellIdentityLte.mMccStr, cellIdentityLte.mMncStr, cellIdentityLte.mAlphaLong, cellIdentityLte.mAlphaShort, cellIdentityLte.mAdditionalPlmns, cellIdentityLte.mCsgInfo);
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellIdentityLte sanitizeLocationInfo() {
        return new android.telephony.CellIdentityLte(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mBands, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns, null);
    }

    android.telephony.CellIdentityLte copy() {
        return new android.telephony.CellIdentityLte(this);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        java.lang.String plmn = getPlmn();
        if (plmn == null || this.mCi == Integer.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = plmn + android.text.TextUtils.formatSimple("%07x", java.lang.Integer.valueOf(this.mCi));
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

    public int getCi() {
        return this.mCi;
    }

    public int getPci() {
        return this.mPci;
    }

    public int getTac() {
        return this.mTac;
    }

    public int getEarfcn() {
        return this.mEarfcn;
    }

    public int[] getBands() {
        return java.util.Arrays.copyOf(this.mBands, this.mBands.length);
    }

    public int getBandwidth() {
        return this.mBandwidth;
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
    public int getChannelNumber() {
        return this.mEarfcn;
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
        gsmCellLocation.setLacAndCid(this.mTac != Integer.MAX_VALUE ? this.mTac : -1, this.mCi != Integer.MAX_VALUE ? this.mCi : -1);
        gsmCellLocation.setPsc(0);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mCi), java.lang.Integer.valueOf(this.mPci), java.lang.Integer.valueOf(this.mTac), java.lang.Integer.valueOf(this.mEarfcn), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mBands)), java.lang.Integer.valueOf(this.mBandwidth), java.lang.Integer.valueOf(this.mAdditionalPlmns.hashCode()), this.mCsgInfo, java.lang.Integer.valueOf(super.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellIdentityLte)) {
            return false;
        }
        android.telephony.CellIdentityLte cellIdentityLte = (android.telephony.CellIdentityLte) obj;
        return this.mCi == cellIdentityLte.mCi && this.mPci == cellIdentityLte.mPci && this.mTac == cellIdentityLte.mTac && this.mEarfcn == cellIdentityLte.mEarfcn && java.util.Arrays.equals(this.mBands, cellIdentityLte.mBands) && this.mBandwidth == cellIdentityLte.mBandwidth && android.text.TextUtils.equals(this.mMccStr, cellIdentityLte.mMccStr) && android.text.TextUtils.equals(this.mMncStr, cellIdentityLte.mMncStr) && this.mAdditionalPlmns.equals(cellIdentityLte.mAdditionalPlmns) && java.util.Objects.equals(this.mCsgInfo, cellIdentityLte.mCsgInfo) && super.equals(obj);
    }

    public java.lang.String toString() {
        return TAG + ":{ mCi=" + this.mCi + " mPci=" + this.mPci + " mTac=" + this.mTac + " mEarfcn=" + this.mEarfcn + " mBands=" + java.util.Arrays.toString(this.mBands) + " mBandwidth=" + this.mBandwidth + " mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mAdditionalPlmns=" + this.mAdditionalPlmns + " mCsgInfo=" + this.mCsgInfo + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, 3);
        parcel.writeInt(this.mCi);
        parcel.writeInt(this.mPci);
        parcel.writeInt(this.mTac);
        parcel.writeInt(this.mEarfcn);
        parcel.writeIntArray(this.mBands);
        parcel.writeInt(this.mBandwidth);
        parcel.writeArraySet(this.mAdditionalPlmns);
        parcel.writeParcelable(this.mCsgInfo, i);
    }

    private CellIdentityLte(android.os.Parcel parcel) {
        super(TAG, 3, parcel);
        this.mCi = parcel.readInt();
        this.mPci = parcel.readInt();
        this.mTac = parcel.readInt();
        this.mEarfcn = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mBandwidth = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        this.mCsgInfo = (android.telephony.ClosedSubscriberGroupInfo) parcel.readParcelable(null, android.telephony.ClosedSubscriberGroupInfo.class);
        updateGlobalCellId();
    }

    protected static android.telephony.CellIdentityLte createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellIdentityLte(parcel);
    }
}
