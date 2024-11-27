package android.telephony;

/* loaded from: classes3.dex */
public final class CellIdentityTdscdma extends android.telephony.CellIdentity {
    private static final boolean DBG = false;
    private static final int MAX_CID = 268435455;
    private static final int MAX_CPID = 127;
    private static final int MAX_LAC = 65535;
    private static final int MAX_UARFCN = 65535;
    private final android.util.ArraySet<java.lang.String> mAdditionalPlmns;
    private final int mCid;
    private final int mCpid;
    private android.telephony.ClosedSubscriberGroupInfo mCsgInfo;
    private final int mLac;
    private final int mUarfcn;
    private static final java.lang.String TAG = android.telephony.CellIdentityTdscdma.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentityTdscdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentityTdscdma>() { // from class: android.telephony.CellIdentityTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityTdscdma createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellIdentityTdscdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityTdscdma[] newArray(int i) {
            return new android.telephony.CellIdentityTdscdma[i];
        }
    };

    public CellIdentityTdscdma() {
        super(TAG, 5, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mCpid = Integer.MAX_VALUE;
        this.mUarfcn = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new android.util.ArraySet<>();
        this.mCsgInfo = null;
        this.mGlobalCellId = null;
    }

    public CellIdentityTdscdma(java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, java.lang.String str3, java.lang.String str4, java.util.Collection<java.lang.String> collection, android.telephony.ClosedSubscriberGroupInfo closedSubscriberGroupInfo) {
        super(TAG, 5, str, str2, str3, str4);
        this.mLac = inRangeOrUnavailable(i, 0, 65535);
        this.mCid = inRangeOrUnavailable(i2, 0, 268435455);
        this.mCpid = inRangeOrUnavailable(i3, 0, 127);
        this.mUarfcn = inRangeOrUnavailable(i4, 0, 65535);
        this.mAdditionalPlmns = new android.util.ArraySet<>(collection.size());
        for (java.lang.String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        this.mCsgInfo = closedSubscriberGroupInfo;
        updateGlobalCellId();
    }

    private CellIdentityTdscdma(android.telephony.CellIdentityTdscdma cellIdentityTdscdma) {
        this(cellIdentityTdscdma.mMccStr, cellIdentityTdscdma.mMncStr, cellIdentityTdscdma.mLac, cellIdentityTdscdma.mCid, cellIdentityTdscdma.mCpid, cellIdentityTdscdma.mUarfcn, cellIdentityTdscdma.mAlphaLong, cellIdentityTdscdma.mAlphaShort, cellIdentityTdscdma.mAdditionalPlmns, cellIdentityTdscdma.mCsgInfo);
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellIdentityTdscdma sanitizeLocationInfo() {
        return new android.telephony.CellIdentityTdscdma(this.mMccStr, this.mMncStr, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns, null);
    }

    android.telephony.CellIdentityTdscdma copy() {
        return new android.telephony.CellIdentityTdscdma(this);
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

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getCpid() {
        return this.mCpid;
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
        gsmCellLocation.setLacAndCid(this.mLac != Integer.MAX_VALUE ? this.mLac : -1, this.mCid != Integer.MAX_VALUE ? this.mCid : -1);
        gsmCellLocation.setPsc(-1);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellIdentityTdscdma)) {
            return false;
        }
        android.telephony.CellIdentityTdscdma cellIdentityTdscdma = (android.telephony.CellIdentityTdscdma) obj;
        return this.mLac == cellIdentityTdscdma.mLac && this.mCid == cellIdentityTdscdma.mCid && this.mCpid == cellIdentityTdscdma.mCpid && this.mUarfcn == cellIdentityTdscdma.mUarfcn && this.mAdditionalPlmns.equals(cellIdentityTdscdma.mAdditionalPlmns) && java.util.Objects.equals(this.mCsgInfo, cellIdentityTdscdma.mCsgInfo) && super.equals(obj);
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLac), java.lang.Integer.valueOf(this.mCid), java.lang.Integer.valueOf(this.mCpid), java.lang.Integer.valueOf(this.mUarfcn), java.lang.Integer.valueOf(this.mAdditionalPlmns.hashCode()), this.mCsgInfo, java.lang.Integer.valueOf(super.hashCode()));
    }

    public java.lang.String toString() {
        return TAG + ":{ mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mLac=" + this.mLac + " mCid=" + this.mCid + " mCpid=" + this.mCpid + " mUarfcn=" + this.mUarfcn + " mAdditionalPlmns=" + this.mAdditionalPlmns + " mCsgInfo=" + this.mCsgInfo + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, 5);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mCpid);
        parcel.writeInt(this.mUarfcn);
        parcel.writeArraySet(this.mAdditionalPlmns);
        parcel.writeParcelable(this.mCsgInfo, i);
    }

    private CellIdentityTdscdma(android.os.Parcel parcel) {
        super(TAG, 5, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mCpid = parcel.readInt();
        this.mUarfcn = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        this.mCsgInfo = (android.telephony.ClosedSubscriberGroupInfo) parcel.readParcelable(null, android.telephony.ClosedSubscriberGroupInfo.class);
        updateGlobalCellId();
    }

    protected static android.telephony.CellIdentityTdscdma createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellIdentityTdscdma(parcel);
    }
}
