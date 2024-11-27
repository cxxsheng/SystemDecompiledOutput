package android.telephony;

/* loaded from: classes3.dex */
public class DataConnectionRealTimeInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.DataConnectionRealTimeInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.DataConnectionRealTimeInfo>() { // from class: android.telephony.DataConnectionRealTimeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.DataConnectionRealTimeInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.DataConnectionRealTimeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.DataConnectionRealTimeInfo[] newArray(int i) {
            return new android.telephony.DataConnectionRealTimeInfo[i];
        }
    };
    public static final int DC_POWER_STATE_HIGH = 3;
    public static final int DC_POWER_STATE_LOW = 1;
    public static final int DC_POWER_STATE_MEDIUM = 2;
    public static final int DC_POWER_STATE_UNKNOWN = Integer.MAX_VALUE;
    private int mDcPowerState;
    private long mTime;

    public DataConnectionRealTimeInfo(long j, int i) {
        this.mTime = j;
        this.mDcPowerState = i;
    }

    public DataConnectionRealTimeInfo() {
        this.mTime = Long.MAX_VALUE;
        this.mDcPowerState = Integer.MAX_VALUE;
    }

    private DataConnectionRealTimeInfo(android.os.Parcel parcel) {
        this.mTime = parcel.readLong();
        this.mDcPowerState = parcel.readInt();
    }

    public long getTime() {
        return this.mTime;
    }

    public int getDcPowerState() {
        return this.mDcPowerState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mTime);
        parcel.writeInt(this.mDcPowerState);
    }

    public int hashCode() {
        long j = this.mTime + 17;
        return (int) (j + (17 * j) + this.mDcPowerState);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo = (android.telephony.DataConnectionRealTimeInfo) obj;
        if (this.mTime == dataConnectionRealTimeInfo.mTime && this.mDcPowerState == dataConnectionRealTimeInfo.mDcPowerState) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("mTime=").append(this.mTime);
        stringBuffer.append(" mDcPowerState=").append(this.mDcPowerState);
        return stringBuffer.toString();
    }
}
