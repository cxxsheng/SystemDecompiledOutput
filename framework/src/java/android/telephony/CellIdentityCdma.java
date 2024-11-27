package android.telephony;

/* loaded from: classes3.dex */
public final class CellIdentityCdma extends android.telephony.CellIdentity {
    private static final int BASESTATION_ID_MAX = 65535;
    private static final boolean DBG = false;
    private static final int LATITUDE_MAX = 1296000;
    private static final int LATITUDE_MIN = -1296000;
    private static final int LONGITUDE_MAX = 2592000;
    private static final int LONGITUDE_MIN = -2592000;
    private static final int NETWORK_ID_MAX = 65535;
    private static final int SYSTEM_ID_MAX = 32767;
    private final int mBasestationId;
    private final int mLatitude;
    private final int mLongitude;
    private final int mNetworkId;
    private final int mSystemId;
    private static final java.lang.String TAG = android.telephony.CellIdentityCdma.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.telephony.CellIdentityCdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellIdentityCdma>() { // from class: android.telephony.CellIdentityCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityCdma createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellIdentityCdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellIdentityCdma[] newArray(int i) {
            return new android.telephony.CellIdentityCdma[i];
        }
    };

    public CellIdentityCdma() {
        super(TAG, 2, null, null, null, null);
        this.mNetworkId = Integer.MAX_VALUE;
        this.mSystemId = Integer.MAX_VALUE;
        this.mBasestationId = Integer.MAX_VALUE;
        this.mLongitude = Integer.MAX_VALUE;
        this.mLatitude = Integer.MAX_VALUE;
        this.mGlobalCellId = null;
    }

    public CellIdentityCdma(int i, int i2, int i3, int i4, int i5, java.lang.String str, java.lang.String str2) {
        super(TAG, 2, null, null, str, str2);
        this.mNetworkId = inRangeOrUnavailable(i, 0, 65535);
        this.mSystemId = inRangeOrUnavailable(i2, 0, 32767);
        this.mBasestationId = inRangeOrUnavailable(i3, 0, 65535);
        int inRangeOrUnavailable = inRangeOrUnavailable(i5, LATITUDE_MIN, LATITUDE_MAX);
        int inRangeOrUnavailable2 = inRangeOrUnavailable(i4, LONGITUDE_MIN, LONGITUDE_MAX);
        if (!isNullIsland(inRangeOrUnavailable, inRangeOrUnavailable2)) {
            this.mLongitude = inRangeOrUnavailable2;
            this.mLatitude = inRangeOrUnavailable;
        } else {
            this.mLatitude = Integer.MAX_VALUE;
            this.mLongitude = Integer.MAX_VALUE;
        }
        updateGlobalCellId();
    }

    private CellIdentityCdma(android.telephony.CellIdentityCdma cellIdentityCdma) {
        this(cellIdentityCdma.mNetworkId, cellIdentityCdma.mSystemId, cellIdentityCdma.mBasestationId, cellIdentityCdma.mLongitude, cellIdentityCdma.mLatitude, cellIdentityCdma.mAlphaLong, cellIdentityCdma.mAlphaShort);
    }

    android.telephony.CellIdentityCdma copy() {
        return new android.telephony.CellIdentityCdma(this);
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.CellIdentityCdma sanitizeLocationInfo() {
        return new android.telephony.CellIdentityCdma(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        if (this.mNetworkId == Integer.MAX_VALUE || this.mSystemId == Integer.MAX_VALUE || this.mBasestationId == Integer.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = android.text.TextUtils.formatSimple("%04x%04x%04x", java.lang.Integer.valueOf(this.mSystemId), java.lang.Integer.valueOf(this.mNetworkId), java.lang.Integer.valueOf(this.mBasestationId));
    }

    private boolean isNullIsland(int i, int i2) {
        return java.lang.Math.abs(i) <= 1 && java.lang.Math.abs(i2) <= 1;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    public int getBasestationId() {
        return this.mBasestationId;
    }

    public int getLongitude() {
        return this.mLongitude;
    }

    public int getLatitude() {
        return this.mLatitude;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mNetworkId), java.lang.Integer.valueOf(this.mSystemId), java.lang.Integer.valueOf(this.mBasestationId), java.lang.Integer.valueOf(this.mLatitude), java.lang.Integer.valueOf(this.mLongitude), java.lang.Integer.valueOf(super.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public android.telephony.cdma.CdmaCellLocation asCellLocation() {
        android.telephony.cdma.CdmaCellLocation cdmaCellLocation = new android.telephony.cdma.CdmaCellLocation();
        cdmaCellLocation.setCellLocationData(this.mBasestationId != Integer.MAX_VALUE ? this.mBasestationId : -1, this.mLatitude, this.mLongitude, this.mSystemId != Integer.MAX_VALUE ? this.mSystemId : -1, this.mNetworkId != Integer.MAX_VALUE ? this.mNetworkId : -1);
        return cdmaCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellIdentityCdma)) {
            return false;
        }
        android.telephony.CellIdentityCdma cellIdentityCdma = (android.telephony.CellIdentityCdma) obj;
        return this.mNetworkId == cellIdentityCdma.mNetworkId && this.mSystemId == cellIdentityCdma.mSystemId && this.mBasestationId == cellIdentityCdma.mBasestationId && this.mLatitude == cellIdentityCdma.mLatitude && this.mLongitude == cellIdentityCdma.mLongitude && super.equals(obj);
    }

    public java.lang.String toString() {
        return TAG + ":{ mNetworkId=" + this.mNetworkId + " mSystemId=" + this.mSystemId + " mBasestationId=" + this.mBasestationId + " mLongitude=" + com.android.telephony.Rlog.pii(com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE, java.lang.Integer.valueOf(this.mLongitude)) + " mLatitude=" + com.android.telephony.Rlog.pii(com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE, java.lang.Integer.valueOf(this.mLatitude)) + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, 2);
        parcel.writeInt(this.mNetworkId);
        parcel.writeInt(this.mSystemId);
        parcel.writeInt(this.mBasestationId);
        parcel.writeInt(this.mLongitude);
        parcel.writeInt(this.mLatitude);
    }

    private CellIdentityCdma(android.os.Parcel parcel) {
        super(TAG, 2, parcel);
        this.mNetworkId = parcel.readInt();
        this.mSystemId = parcel.readInt();
        this.mBasestationId = parcel.readInt();
        this.mLongitude = parcel.readInt();
        this.mLatitude = parcel.readInt();
        updateGlobalCellId();
    }

    protected static android.telephony.CellIdentityCdma createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellIdentityCdma(parcel);
    }
}
