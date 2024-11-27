package android.telephony;

/* loaded from: classes3.dex */
public final class PhysicalChannelConfig implements android.os.Parcelable {
    public static final int BAND_UNKNOWN = 0;
    public static final int CELL_BANDWIDTH_UNKNOWN = 0;
    public static final int CHANNEL_NUMBER_UNKNOWN = Integer.MAX_VALUE;

    @java.lang.Deprecated
    public static final int CONNECTION_PRIMARY_SERVING = 1;

    @java.lang.Deprecated
    public static final int CONNECTION_SECONDARY_SERVING = 2;

    @java.lang.Deprecated
    public static final int CONNECTION_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.telephony.PhysicalChannelConfig> CREATOR = new android.os.Parcelable.Creator<android.telephony.PhysicalChannelConfig>() { // from class: android.telephony.PhysicalChannelConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PhysicalChannelConfig createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.PhysicalChannelConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PhysicalChannelConfig[] newArray(int i) {
            return new android.telephony.PhysicalChannelConfig[i];
        }
    };
    public static final int FREQUENCY_UNKNOWN = -1;
    public static final int PHYSICAL_CELL_ID_MAXIMUM_VALUE = 1007;
    public static final int PHYSICAL_CELL_ID_UNKNOWN = -1;
    private int mBand;
    private int mCellBandwidthDownlinkKhz;
    private int mCellBandwidthUplinkKhz;
    private int mCellConnectionStatus;
    private int[] mContextIds;
    private int mDownlinkChannelNumber;
    private int mDownlinkFrequency;
    private int mFrequencyRange;
    private int mNetworkType;
    private int mPhysicalCellId;
    private int mUplinkChannelNumber;
    private int mUplinkFrequency;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCellConnectionStatus);
        parcel.writeInt(this.mCellBandwidthDownlinkKhz);
        parcel.writeInt(this.mCellBandwidthUplinkKhz);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mDownlinkChannelNumber);
        parcel.writeInt(this.mUplinkChannelNumber);
        parcel.writeInt(this.mFrequencyRange);
        parcel.writeIntArray(this.mContextIds);
        parcel.writeInt(this.mPhysicalCellId);
        parcel.writeInt(this.mBand);
    }

    public int getCellBandwidthDownlinkKhz() {
        return this.mCellBandwidthDownlinkKhz;
    }

    public int getCellBandwidthUplinkKhz() {
        return this.mCellBandwidthUplinkKhz;
    }

    public int[] getContextIds() {
        return this.mContextIds;
    }

    public int getFrequencyRange() {
        return this.mFrequencyRange;
    }

    public int getDownlinkChannelNumber() {
        return this.mDownlinkChannelNumber;
    }

    public int getUplinkChannelNumber() {
        return this.mUplinkChannelNumber;
    }

    public int getBand() {
        return this.mBand;
    }

    public int getDownlinkFrequencyKhz() {
        return this.mDownlinkFrequency;
    }

    public int getUplinkFrequencyKhz() {
        return this.mUplinkFrequency;
    }

    public int getPhysicalCellId() {
        return this.mPhysicalCellId;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public int getConnectionStatus() {
        return this.mCellConnectionStatus;
    }

    public android.telephony.PhysicalChannelConfig createLocationInfoSanitizedCopy() {
        return new android.telephony.PhysicalChannelConfig.Builder(this).setPhysicalCellId(-1).build();
    }

    private java.lang.String getConnectionStatusString() {
        switch (this.mCellConnectionStatus) {
            case -1:
                return "Unknown";
            case 0:
            default:
                return "Invalid(" + this.mCellConnectionStatus + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "PrimaryServing";
            case 2:
                return "SecondaryServing";
        }
    }

    private void setDownlinkFrequency() {
        switch (this.mNetworkType) {
            case 1:
            case 2:
            case 16:
                this.mDownlinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromArfcn(this.mBand, this.mDownlinkChannelNumber, false);
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 15:
            case 17:
                this.mDownlinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromUarfcn(this.mBand, this.mDownlinkChannelNumber, false);
                break;
            case 13:
                this.mDownlinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromEarfcn(this.mBand, this.mDownlinkChannelNumber, false);
                break;
            case 20:
                this.mDownlinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromNrArfcn(this.mDownlinkChannelNumber);
                break;
        }
    }

    private void setUplinkFrequency() {
        switch (this.mNetworkType) {
            case 1:
            case 2:
            case 16:
                this.mUplinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromArfcn(this.mBand, this.mUplinkChannelNumber, true);
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 15:
            case 17:
                this.mUplinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromUarfcn(this.mBand, this.mUplinkChannelNumber, true);
                break;
            case 13:
                this.mUplinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromEarfcn(this.mBand, this.mUplinkChannelNumber, true);
                break;
            case 20:
                this.mUplinkFrequency = android.telephony.AccessNetworkUtils.getFrequencyFromNrArfcn(this.mUplinkChannelNumber);
                break;
        }
    }

    private void setFrequencyRange() {
        if (this.mFrequencyRange != 0) {
            return;
        }
        switch (this.mNetworkType) {
            case 1:
            case 2:
            case 16:
                this.mFrequencyRange = android.telephony.AccessNetworkUtils.getFrequencyRangeGroupFromGeranBand(this.mBand);
                break;
            case 3:
            case 8:
            case 9:
            case 10:
            case 15:
            case 17:
                this.mFrequencyRange = android.telephony.AccessNetworkUtils.getFrequencyRangeGroupFromUtranBand(this.mBand);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 14:
            case 18:
            case 19:
            default:
                this.mFrequencyRange = 0;
                break;
            case 13:
                this.mFrequencyRange = android.telephony.AccessNetworkUtils.getFrequencyRangeGroupFromEutranBand(this.mBand);
                break;
            case 20:
                this.mFrequencyRange = android.telephony.AccessNetworkUtils.getFrequencyRangeGroupFromNrBand(this.mBand);
                break;
        }
        if (this.mFrequencyRange == 0) {
            this.mFrequencyRange = android.telephony.AccessNetworkUtils.getFrequencyRangeFromArfcn(this.mDownlinkFrequency);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.PhysicalChannelConfig)) {
            return false;
        }
        android.telephony.PhysicalChannelConfig physicalChannelConfig = (android.telephony.PhysicalChannelConfig) obj;
        return this.mCellConnectionStatus == physicalChannelConfig.mCellConnectionStatus && this.mCellBandwidthDownlinkKhz == physicalChannelConfig.mCellBandwidthDownlinkKhz && this.mCellBandwidthUplinkKhz == physicalChannelConfig.mCellBandwidthUplinkKhz && this.mNetworkType == physicalChannelConfig.mNetworkType && this.mFrequencyRange == physicalChannelConfig.mFrequencyRange && this.mDownlinkChannelNumber == physicalChannelConfig.mDownlinkChannelNumber && this.mUplinkChannelNumber == physicalChannelConfig.mUplinkChannelNumber && this.mPhysicalCellId == physicalChannelConfig.mPhysicalCellId && java.util.Arrays.equals(this.mContextIds, physicalChannelConfig.mContextIds) && this.mBand == physicalChannelConfig.mBand && this.mDownlinkFrequency == physicalChannelConfig.mDownlinkFrequency && this.mUplinkFrequency == physicalChannelConfig.mUplinkFrequency;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mCellConnectionStatus), java.lang.Integer.valueOf(this.mCellBandwidthDownlinkKhz), java.lang.Integer.valueOf(this.mCellBandwidthUplinkKhz), java.lang.Integer.valueOf(this.mNetworkType), java.lang.Integer.valueOf(this.mFrequencyRange), java.lang.Integer.valueOf(this.mDownlinkChannelNumber), java.lang.Integer.valueOf(this.mUplinkChannelNumber), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mContextIds)), java.lang.Integer.valueOf(this.mPhysicalCellId), java.lang.Integer.valueOf(this.mBand), java.lang.Integer.valueOf(this.mDownlinkFrequency), java.lang.Integer.valueOf(this.mUplinkFrequency));
    }

    public java.lang.String toString() {
        return "{mConnectionStatus=" + getConnectionStatusString() + ",mCellBandwidthDownlinkKhz=" + this.mCellBandwidthDownlinkKhz + ",mCellBandwidthUplinkKhz=" + this.mCellBandwidthUplinkKhz + ",mNetworkType=" + android.telephony.TelephonyManager.getNetworkTypeName(this.mNetworkType) + ",mFrequencyRange=" + android.telephony.ServiceState.frequencyRangeToString(this.mFrequencyRange) + ",mDownlinkChannelNumber=" + this.mDownlinkChannelNumber + ",mUplinkChannelNumber=" + this.mUplinkChannelNumber + ",mContextIds=" + java.util.Arrays.toString(this.mContextIds) + ",mPhysicalCellId=" + this.mPhysicalCellId + ",mBand=" + this.mBand + ",mDownlinkFrequency=" + this.mDownlinkFrequency + ",mUplinkFrequency=" + this.mUplinkFrequency + "}";
    }

    private PhysicalChannelConfig(android.os.Parcel parcel) {
        this.mCellConnectionStatus = parcel.readInt();
        this.mCellBandwidthDownlinkKhz = parcel.readInt();
        this.mCellBandwidthUplinkKhz = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mDownlinkChannelNumber = parcel.readInt();
        this.mUplinkChannelNumber = parcel.readInt();
        this.mFrequencyRange = parcel.readInt();
        this.mContextIds = parcel.createIntArray();
        this.mPhysicalCellId = parcel.readInt();
        this.mBand = parcel.readInt();
        if (this.mBand > 0) {
            setDownlinkFrequency();
            setUplinkFrequency();
            setFrequencyRange();
        }
    }

    private PhysicalChannelConfig(android.telephony.PhysicalChannelConfig.Builder builder) {
        this.mCellConnectionStatus = builder.mCellConnectionStatus;
        this.mCellBandwidthDownlinkKhz = builder.mCellBandwidthDownlinkKhz;
        this.mCellBandwidthUplinkKhz = builder.mCellBandwidthUplinkKhz;
        this.mNetworkType = builder.mNetworkType;
        this.mDownlinkChannelNumber = builder.mDownlinkChannelNumber;
        this.mUplinkChannelNumber = builder.mUplinkChannelNumber;
        this.mFrequencyRange = builder.mFrequencyRange;
        this.mContextIds = builder.mContextIds;
        this.mPhysicalCellId = builder.mPhysicalCellId;
        this.mBand = builder.mBand;
        if (this.mBand > 0) {
            setDownlinkFrequency();
            setUplinkFrequency();
            setFrequencyRange();
        }
    }

    public static final class Builder {
        private int mBand;
        private int mCellBandwidthDownlinkKhz;
        private int mCellBandwidthUplinkKhz;
        private int mCellConnectionStatus;
        private int[] mContextIds;
        private int mDownlinkChannelNumber;
        private int mFrequencyRange;
        private int mNetworkType;
        private int mPhysicalCellId;
        private int mUplinkChannelNumber;

        public Builder() {
            this.mNetworkType = 0;
            this.mFrequencyRange = 0;
            this.mDownlinkChannelNumber = Integer.MAX_VALUE;
            this.mUplinkChannelNumber = Integer.MAX_VALUE;
            this.mCellBandwidthDownlinkKhz = 0;
            this.mCellBandwidthUplinkKhz = 0;
            this.mCellConnectionStatus = -1;
            this.mContextIds = new int[0];
            this.mPhysicalCellId = -1;
            this.mBand = 0;
        }

        public Builder(android.telephony.PhysicalChannelConfig physicalChannelConfig) {
            this.mNetworkType = physicalChannelConfig.getNetworkType();
            this.mFrequencyRange = physicalChannelConfig.getFrequencyRange();
            this.mDownlinkChannelNumber = physicalChannelConfig.getDownlinkChannelNumber();
            this.mUplinkChannelNumber = physicalChannelConfig.getUplinkChannelNumber();
            this.mCellBandwidthDownlinkKhz = physicalChannelConfig.getCellBandwidthDownlinkKhz();
            this.mCellBandwidthUplinkKhz = physicalChannelConfig.getCellBandwidthUplinkKhz();
            this.mCellConnectionStatus = physicalChannelConfig.getConnectionStatus();
            this.mContextIds = java.util.Arrays.copyOf(physicalChannelConfig.getContextIds(), physicalChannelConfig.getContextIds().length);
            this.mPhysicalCellId = physicalChannelConfig.getPhysicalCellId();
            this.mBand = physicalChannelConfig.getBand();
        }

        public android.telephony.PhysicalChannelConfig build() {
            return new android.telephony.PhysicalChannelConfig(this);
        }

        public android.telephony.PhysicalChannelConfig.Builder setNetworkType(int i) {
            if (!android.telephony.TelephonyManager.isNetworkTypeValid(i)) {
                throw new java.lang.IllegalArgumentException("Network type " + i + " is invalid.");
            }
            this.mNetworkType = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setFrequencyRange(int i) {
            if (!android.telephony.ServiceState.isFrequencyRangeValid(i) && i != 0) {
                throw new java.lang.IllegalArgumentException("Frequency range " + i + " is invalid.");
            }
            this.mFrequencyRange = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setDownlinkChannelNumber(int i) {
            this.mDownlinkChannelNumber = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setUplinkChannelNumber(int i) {
            this.mUplinkChannelNumber = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setCellBandwidthDownlinkKhz(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Cell downlink bandwidth(kHz) " + i + " is invalid.");
            }
            this.mCellBandwidthDownlinkKhz = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setCellBandwidthUplinkKhz(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Cell uplink bandwidth(kHz) " + i + " is invalid.");
            }
            this.mCellBandwidthUplinkKhz = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setCellConnectionStatus(int i) {
            this.mCellConnectionStatus = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setContextIds(int[] iArr) {
            if (iArr != null) {
                java.util.Arrays.sort(iArr);
            }
            this.mContextIds = iArr;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setPhysicalCellId(int i) {
            if (i > 1007) {
                throw new java.lang.IllegalArgumentException("Physical cell ID " + i + " is over limit.");
            }
            this.mPhysicalCellId = i;
            return this;
        }

        public android.telephony.PhysicalChannelConfig.Builder setBand(int i) {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Band " + i + " is invalid.");
            }
            this.mBand = i;
            return this;
        }
    }
}
