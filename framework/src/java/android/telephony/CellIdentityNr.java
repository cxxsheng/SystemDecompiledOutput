package android.telephony;

/* loaded from: classes3.dex */
public final class CellIdentityNr extends android.telephony.CellIdentity {
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentityNr> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentityNr>() { // from class: android.telephony.CellIdentityNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityNr createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellIdentityNr.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityNr[] newArray(int i) {
            return new android.telephony.CellIdentityNr[i];
        }
    };
    private static final long MAX_NCI = 68719476735L;
    private static final int MAX_NRARFCN = 3279165;
    private static final int MAX_PCI = 1007;
    private static final int MAX_TAC = 16777215;
    private static final java.lang.String TAG = "CellIdentityNr";
    private final android.util.ArraySet<java.lang.String> mAdditionalPlmns;
    private final int[] mBands;
    private final long mNci;
    private final int mNrArfcn;
    private final int mPci;
    private final int mTac;

    public CellIdentityNr() {
        super(TAG, 6, null, null, null, null);
        this.mNrArfcn = Integer.MAX_VALUE;
        this.mPci = Integer.MAX_VALUE;
        this.mTac = Integer.MAX_VALUE;
        this.mNci = 2147483647L;
        this.mBands = new int[0];
        this.mAdditionalPlmns = new android.util.ArraySet<>();
        this.mGlobalCellId = null;
    }

    public CellIdentityNr(int i, int i2, int i3, int[] iArr, java.lang.String str, java.lang.String str2, long j, java.lang.String str3, java.lang.String str4, java.util.Collection<java.lang.String> collection) {
        super(TAG, 6, str, str2, str3, str4);
        this.mPci = inRangeOrUnavailable(i, 0, 1007);
        this.mTac = inRangeOrUnavailable(i2, 0, 16777215);
        this.mNrArfcn = inRangeOrUnavailable(i3, 0, MAX_NRARFCN);
        this.mBands = iArr;
        this.mNci = inRangeOrUnavailable(j, 0L, MAX_NCI);
        this.mAdditionalPlmns = new android.util.ArraySet<>(collection.size());
        for (java.lang.String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        updateGlobalCellId();
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellIdentityNr sanitizeLocationInfo() {
        return new android.telephony.CellIdentityNr(Integer.MAX_VALUE, Integer.MAX_VALUE, this.mNrArfcn, this.mBands, this.mMccStr, this.mMncStr, Long.MAX_VALUE, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        java.lang.String plmn = getPlmn();
        if (plmn == null || this.mNci == Long.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = plmn + android.text.TextUtils.formatSimple("%09x", java.lang.Long.valueOf(this.mNci));
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellLocation asCellLocation() {
        android.telephony.gsm.GsmCellLocation gsmCellLocation = new android.telephony.gsm.GsmCellLocation();
        gsmCellLocation.setLacAndCid(this.mTac != Integer.MAX_VALUE ? this.mTac : -1, -1);
        gsmCellLocation.setPsc(0);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), java.lang.Integer.valueOf(this.mPci), java.lang.Integer.valueOf(this.mTac), java.lang.Integer.valueOf(this.mNrArfcn), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mBands)), java.lang.Long.valueOf(this.mNci), java.lang.Integer.valueOf(this.mAdditionalPlmns.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellIdentityNr)) {
            return false;
        }
        android.telephony.CellIdentityNr cellIdentityNr = (android.telephony.CellIdentityNr) obj;
        return super.equals(cellIdentityNr) && this.mPci == cellIdentityNr.mPci && this.mTac == cellIdentityNr.mTac && this.mNrArfcn == cellIdentityNr.mNrArfcn && java.util.Arrays.equals(this.mBands, cellIdentityNr.mBands) && this.mNci == cellIdentityNr.mNci && this.mAdditionalPlmns.equals(cellIdentityNr.mAdditionalPlmns);
    }

    public long getNci() {
        return this.mNci;
    }

    public int getNrarfcn() {
        return this.mNrArfcn;
    }

    public int[] getBands() {
        return java.util.Arrays.copyOf(this.mBands, this.mBands.length);
    }

    public int getPci() {
        return this.mPci;
    }

    public int getTac() {
        return this.mTac;
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
        return this.mNrArfcn;
    }

    public java.util.Set<java.lang.String> getAdditionalPlmns() {
        return java.util.Collections.unmodifiableSet(this.mAdditionalPlmns);
    }

    public java.lang.String toString() {
        return "CellIdentityNr:{ mPci = " + this.mPci + " mTac = " + this.mTac + " mNrArfcn = " + this.mNrArfcn + " mBands = " + java.util.Arrays.toString(this.mBands) + " mMcc = " + this.mMccStr + " mMnc = " + this.mMncStr + " mNci = " + this.mNci + " mAlphaLong = " + this.mAlphaLong + " mAlphaShort = " + this.mAlphaShort + " mAdditionalPlmns = " + this.mAdditionalPlmns + " }";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, 6);
        parcel.writeInt(this.mPci);
        parcel.writeInt(this.mTac);
        parcel.writeInt(this.mNrArfcn);
        parcel.writeIntArray(this.mBands);
        parcel.writeLong(this.mNci);
        parcel.writeArraySet(this.mAdditionalPlmns);
    }

    private CellIdentityNr(android.os.Parcel parcel) {
        super(TAG, 6, parcel);
        this.mPci = parcel.readInt();
        this.mTac = parcel.readInt();
        this.mNrArfcn = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mNci = parcel.readLong();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        updateGlobalCellId();
    }

    protected static android.telephony.CellIdentityNr createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellIdentityNr(parcel);
    }
}
