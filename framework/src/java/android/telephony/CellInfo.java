package android.telephony;

/* loaded from: classes3.dex */
public abstract class CellInfo implements android.os.Parcelable {
    public static final int CONNECTION_NONE = 0;
    public static final int CONNECTION_PRIMARY_SERVING = 1;
    public static final int CONNECTION_SECONDARY_SERVING = 2;
    public static final int CONNECTION_UNKNOWN = Integer.MAX_VALUE;
    public static final android.os.Parcelable.Creator<android.telephony.CellInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfo>() { // from class: android.telephony.CellInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfo createFromParcel(android.os.Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return android.telephony.CellInfoGsm.createFromParcelBody(parcel);
                case 2:
                    return android.telephony.CellInfoCdma.createFromParcelBody(parcel);
                case 3:
                    return android.telephony.CellInfoLte.createFromParcelBody(parcel);
                case 4:
                    return android.telephony.CellInfoWcdma.createFromParcelBody(parcel);
                case 5:
                    return android.telephony.CellInfoTdscdma.createFromParcelBody(parcel);
                case 6:
                    return android.telephony.CellInfoNr.createFromParcelBody(parcel);
                default:
                    throw new java.lang.RuntimeException("Bad CellInfo Parcel");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfo[] newArray(int i) {
            return new android.telephony.CellInfo[i];
        }
    };
    public static final int TIMESTAMP_TYPE_ANTENNA = 1;
    public static final int TIMESTAMP_TYPE_JAVA_RIL = 4;
    public static final int TIMESTAMP_TYPE_MODEM = 2;
    public static final int TIMESTAMP_TYPE_OEM_RIL = 3;
    public static final int TIMESTAMP_TYPE_UNKNOWN = 0;
    public static final int TYPE_CDMA = 2;
    public static final int TYPE_GSM = 1;
    public static final int TYPE_LTE = 3;
    public static final int TYPE_NR = 6;
    public static final int TYPE_TDSCDMA = 5;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WCDMA = 4;
    public static final int UNAVAILABLE = Integer.MAX_VALUE;
    public static final long UNAVAILABLE_LONG = Long.MAX_VALUE;
    private int mCellConnectionStatus;
    private boolean mRegistered;
    private long mTimeStamp;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CellConnectionStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public abstract android.telephony.CellIdentity getCellIdentity();

    public abstract android.telephony.CellSignalStrength getCellSignalStrength();

    @Override // android.os.Parcelable
    public abstract void writeToParcel(android.os.Parcel parcel, int i);

    protected CellInfo(int i, boolean z, long j) {
        this.mCellConnectionStatus = i;
        this.mRegistered = z;
        this.mTimeStamp = j;
    }

    protected CellInfo() {
        this.mRegistered = false;
        this.mTimeStamp = Long.MAX_VALUE;
        this.mCellConnectionStatus = 0;
    }

    protected CellInfo(android.telephony.CellInfo cellInfo) {
        this.mRegistered = cellInfo.mRegistered;
        this.mTimeStamp = cellInfo.mTimeStamp;
        this.mCellConnectionStatus = cellInfo.mCellConnectionStatus;
    }

    public boolean isRegistered() {
        return this.mRegistered;
    }

    public void setRegistered(boolean z) {
        this.mRegistered = z;
    }

    public long getTimestampMillis() {
        return this.mTimeStamp / 1000000;
    }

    @java.lang.Deprecated
    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public void setTimeStamp(long j) {
        this.mTimeStamp = j;
    }

    public android.telephony.CellInfo sanitizeLocationInfo() {
        return null;
    }

    public int getCellConnectionStatus() {
        return this.mCellConnectionStatus;
    }

    public void setCellConnectionStatus(int i) {
        this.mCellConnectionStatus = i;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mCellConnectionStatus), java.lang.Boolean.valueOf(this.mRegistered), java.lang.Long.valueOf(this.mTimeStamp));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CellInfo)) {
            return false;
        }
        android.telephony.CellInfo cellInfo = (android.telephony.CellInfo) obj;
        return this.mCellConnectionStatus == cellInfo.mCellConnectionStatus && this.mRegistered == cellInfo.mRegistered && this.mTimeStamp == cellInfo.mTimeStamp;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("mRegistered=").append(this.mRegistered ? "YES" : "NO");
        stringBuffer.append(" mTimeStamp=").append(this.mTimeStamp).append("ns");
        stringBuffer.append(" mCellConnectionStatus=").append(this.mCellConnectionStatus);
        return stringBuffer.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected void writeToParcel(android.os.Parcel parcel, int i, int i2) {
        parcel.writeInt(i2);
        parcel.writeInt(this.mRegistered ? 1 : 0);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeInt(this.mCellConnectionStatus);
    }

    protected CellInfo(android.os.Parcel parcel) {
        this.mRegistered = parcel.readInt() == 1;
        this.mTimeStamp = parcel.readLong();
        this.mCellConnectionStatus = parcel.readInt();
    }
}
