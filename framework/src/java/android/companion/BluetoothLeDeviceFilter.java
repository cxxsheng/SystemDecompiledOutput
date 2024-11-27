package android.companion;

/* loaded from: classes.dex */
public final class BluetoothLeDeviceFilter implements android.companion.DeviceFilter<android.bluetooth.le.ScanResult> {
    public static final android.os.Parcelable.Creator<android.companion.BluetoothLeDeviceFilter> CREATOR = new android.os.Parcelable.Creator<android.companion.BluetoothLeDeviceFilter>() { // from class: android.companion.BluetoothLeDeviceFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.BluetoothLeDeviceFilter createFromParcel(android.os.Parcel parcel) {
            android.companion.BluetoothLeDeviceFilter.Builder scanFilter = new android.companion.BluetoothLeDeviceFilter.Builder().setNamePattern(android.companion.BluetoothDeviceFilterUtils.patternFromString(parcel.readString())).setScanFilter((android.bluetooth.le.ScanFilter) parcel.readParcelable(null, android.bluetooth.le.ScanFilter.class));
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            if (createByteArray != null) {
                scanFilter.setRawDataFilter(createByteArray, createByteArray2);
            }
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            if (readString != null) {
                if (readInt >= 0) {
                    scanFilter.setRenameFromBytes(readString, readString2, readInt, readInt2, readBoolean ? java.nio.ByteOrder.LITTLE_ENDIAN : java.nio.ByteOrder.BIG_ENDIAN);
                } else {
                    scanFilter.setRenameFromName(readString, readString2, readInt3, readInt4);
                }
            }
            return scanFilter.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.BluetoothLeDeviceFilter[] newArray(int i) {
            return new android.companion.BluetoothLeDeviceFilter[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "CDM_BluetoothLeDeviceFilter";
    private static final int RENAME_PREFIX_LENGTH_LIMIT = 10;
    private final java.util.regex.Pattern mNamePattern;
    private final byte[] mRawDataFilter;
    private final byte[] mRawDataFilterMask;
    private final int mRenameBytesFrom;
    private final int mRenameBytesLength;
    private final boolean mRenameBytesReverseOrder;
    private final int mRenameNameFrom;
    private final int mRenameNameLength;
    private final java.lang.String mRenamePrefix;
    private final java.lang.String mRenameSuffix;
    private final android.bluetooth.le.ScanFilter mScanFilter;

    private BluetoothLeDeviceFilter(java.util.regex.Pattern pattern, android.bluetooth.le.ScanFilter scanFilter, byte[] bArr, byte[] bArr2, java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, boolean z) {
        this.mNamePattern = pattern;
        this.mScanFilter = (android.bluetooth.le.ScanFilter) com.android.internal.util.ObjectUtils.firstNotNull(scanFilter, new android.bluetooth.le.ScanFilter.Builder().build());
        this.mRawDataFilter = bArr;
        this.mRawDataFilterMask = bArr2;
        this.mRenamePrefix = str;
        this.mRenameSuffix = str2;
        this.mRenameBytesFrom = i;
        this.mRenameBytesLength = i2;
        this.mRenameNameFrom = i3;
        this.mRenameNameLength = i4;
        this.mRenameBytesReverseOrder = z;
    }

    public java.util.regex.Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public android.bluetooth.le.ScanFilter getScanFilter() {
        return this.mScanFilter;
    }

    public byte[] getRawDataFilter() {
        return this.mRawDataFilter;
    }

    public byte[] getRawDataFilterMask() {
        return this.mRawDataFilterMask;
    }

    public java.lang.String getRenamePrefix() {
        return this.mRenamePrefix;
    }

    public java.lang.String getRenameSuffix() {
        return this.mRenameSuffix;
    }

    public int getRenameBytesFrom() {
        return this.mRenameBytesFrom;
    }

    public int getRenameBytesLength() {
        return this.mRenameBytesLength;
    }

    public boolean isRenameBytesReverseOrder() {
        return this.mRenameBytesReverseOrder;
    }

    @Override // android.companion.DeviceFilter
    public java.lang.String getDeviceDisplayName(android.bluetooth.le.ScanResult scanResult) {
        if (this.mRenameBytesFrom < 0 && this.mRenameNameFrom < 0) {
            return android.companion.BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(scanResult.getDevice());
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(android.text.TextUtils.emptyIfNull(this.mRenamePrefix));
        if (this.mRenameBytesFrom >= 0) {
            byte[] bytes = scanResult.getScanRecord().getBytes();
            int i = this.mRenameBytesFrom;
            int i2 = (this.mRenameBytesFrom + this.mRenameBytesLength) - 1;
            int i3 = this.mRenameBytesReverseOrder ? -1 : 1;
            for (int i4 = this.mRenameBytesReverseOrder ? i2 : i; i <= i4 && i4 <= i2; i4 += i3) {
                sb.append(libcore.util.HexEncoding.encodeToString(bytes[i4], true));
            }
        } else {
            sb.append(android.companion.BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(scanResult.getDevice()).substring(this.mRenameNameFrom, this.mRenameNameFrom + this.mRenameNameLength));
        }
        return sb.append(android.text.TextUtils.emptyIfNull(this.mRenameSuffix)).toString();
    }

    @Override // android.companion.DeviceFilter
    public boolean matches(android.bluetooth.le.ScanResult scanResult) {
        return getScanFilter().matches(scanResult) && android.companion.BluetoothDeviceFilterUtils.matchesName(getNamePattern(), scanResult.getDevice()) && (this.mRawDataFilter == null || com.android.internal.util.BitUtils.maskedEquals(scanResult.getScanRecord().getBytes(), this.mRawDataFilter, this.mRawDataFilterMask));
    }

    @Override // android.companion.DeviceFilter
    public int getMediumType() {
        return 1;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.companion.BluetoothLeDeviceFilter bluetoothLeDeviceFilter = (android.companion.BluetoothLeDeviceFilter) obj;
        if (this.mRenameBytesFrom == bluetoothLeDeviceFilter.mRenameBytesFrom && this.mRenameBytesLength == bluetoothLeDeviceFilter.mRenameBytesLength && this.mRenameNameFrom == bluetoothLeDeviceFilter.mRenameNameFrom && this.mRenameNameLength == bluetoothLeDeviceFilter.mRenameNameLength && this.mRenameBytesReverseOrder == bluetoothLeDeviceFilter.mRenameBytesReverseOrder && java.util.Objects.equals(this.mNamePattern, bluetoothLeDeviceFilter.mNamePattern) && java.util.Objects.equals(this.mScanFilter, bluetoothLeDeviceFilter.mScanFilter) && java.util.Arrays.equals(this.mRawDataFilter, bluetoothLeDeviceFilter.mRawDataFilter) && java.util.Arrays.equals(this.mRawDataFilterMask, bluetoothLeDeviceFilter.mRawDataFilterMask) && java.util.Objects.equals(this.mRenamePrefix, bluetoothLeDeviceFilter.mRenamePrefix) && java.util.Objects.equals(this.mRenameSuffix, bluetoothLeDeviceFilter.mRenameSuffix)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mNamePattern, this.mScanFilter, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mRawDataFilter)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mRawDataFilterMask)), this.mRenamePrefix, this.mRenameSuffix, java.lang.Integer.valueOf(this.mRenameBytesFrom), java.lang.Integer.valueOf(this.mRenameBytesLength), java.lang.Integer.valueOf(this.mRenameNameFrom), java.lang.Integer.valueOf(this.mRenameNameLength), java.lang.Boolean.valueOf(this.mRenameBytesReverseOrder));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(android.companion.BluetoothDeviceFilterUtils.patternToString(getNamePattern()));
        parcel.writeParcelable(this.mScanFilter, i);
        parcel.writeByteArray(this.mRawDataFilter);
        parcel.writeByteArray(this.mRawDataFilterMask);
        parcel.writeString(this.mRenamePrefix);
        parcel.writeString(this.mRenameSuffix);
        parcel.writeInt(this.mRenameBytesFrom);
        parcel.writeInt(this.mRenameBytesLength);
        parcel.writeInt(this.mRenameNameFrom);
        parcel.writeInt(this.mRenameNameLength);
        parcel.writeBoolean(this.mRenameBytesReverseOrder);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "BluetoothLEDeviceFilter{mNamePattern=" + this.mNamePattern + ", mScanFilter=" + this.mScanFilter + ", mRawDataFilter=" + java.util.Arrays.toString(this.mRawDataFilter) + ", mRawDataFilterMask=" + java.util.Arrays.toString(this.mRawDataFilterMask) + ", mRenamePrefix='" + this.mRenamePrefix + android.text.format.DateFormat.QUOTE + ", mRenameSuffix='" + this.mRenameSuffix + android.text.format.DateFormat.QUOTE + ", mRenameBytesFrom=" + this.mRenameBytesFrom + ", mRenameBytesLength=" + this.mRenameBytesLength + ", mRenameNameFrom=" + this.mRenameNameFrom + ", mRenameNameLength=" + this.mRenameNameLength + ", mRenameBytesReverseOrder=" + this.mRenameBytesReverseOrder + '}';
    }

    public static int getRenamePrefixLengthLimit() {
        return 10;
    }

    public static final class Builder extends android.provider.OneTimeUseBuilder<android.companion.BluetoothLeDeviceFilter> {
        private java.util.regex.Pattern mNamePattern;
        private byte[] mRawDataFilter;
        private byte[] mRawDataFilterMask;
        private int mRenameBytesLength;
        private int mRenameNameLength;
        private java.lang.String mRenamePrefix;
        private java.lang.String mRenameSuffix;
        private android.bluetooth.le.ScanFilter mScanFilter;
        private int mRenameBytesFrom = -1;
        private int mRenameNameFrom = -1;
        private boolean mRenameBytesReverseOrder = false;

        public android.companion.BluetoothLeDeviceFilter.Builder setNamePattern(java.util.regex.Pattern pattern) {
            checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }

        public android.companion.BluetoothLeDeviceFilter.Builder setScanFilter(android.bluetooth.le.ScanFilter scanFilter) {
            checkNotUsed();
            this.mScanFilter = scanFilter;
            return this;
        }

        public android.companion.BluetoothLeDeviceFilter.Builder setRawDataFilter(byte[] bArr, byte[] bArr2) {
            checkNotUsed();
            java.util.Objects.requireNonNull(bArr);
            com.android.internal.util.Preconditions.checkArgument(bArr2 == null || bArr.length == bArr2.length, "Mask and filter should be the same length");
            this.mRawDataFilter = bArr;
            this.mRawDataFilterMask = bArr2;
            return this;
        }

        public android.companion.BluetoothLeDeviceFilter.Builder setRenameFromBytes(java.lang.String str, java.lang.String str2, int i, int i2, java.nio.ByteOrder byteOrder) {
            checkRenameNotSet();
            checkRangeNotEmpty(i2);
            this.mRenameBytesFrom = i;
            this.mRenameBytesLength = i2;
            this.mRenameBytesReverseOrder = byteOrder == java.nio.ByteOrder.LITTLE_ENDIAN;
            return setRename(str, str2);
        }

        public android.companion.BluetoothLeDeviceFilter.Builder setRenameFromName(java.lang.String str, java.lang.String str2, int i, int i2) {
            checkRenameNotSet();
            checkRangeNotEmpty(i2);
            this.mRenameNameFrom = i;
            this.mRenameNameLength = i2;
            this.mRenameBytesReverseOrder = false;
            return setRename(str, str2);
        }

        private void checkRenameNotSet() {
            com.android.internal.util.Preconditions.checkState(this.mRenamePrefix == null, "Renaming rule can only be set once");
        }

        private void checkRangeNotEmpty(int i) {
            com.android.internal.util.Preconditions.checkArgument(i > 0, "Range must be non-empty");
        }

        private android.companion.BluetoothLeDeviceFilter.Builder setRename(java.lang.String str, java.lang.String str2) {
            checkNotUsed();
            com.android.internal.util.Preconditions.checkArgument(android.text.TextUtils.length(str) <= android.companion.BluetoothLeDeviceFilter.getRenamePrefixLengthLimit(), "Prefix is too long");
            this.mRenamePrefix = str;
            this.mRenameSuffix = str2;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public android.companion.BluetoothLeDeviceFilter build() {
            markUsed();
            return new android.companion.BluetoothLeDeviceFilter(this.mNamePattern, this.mScanFilter, this.mRawDataFilter, this.mRawDataFilterMask, this.mRenamePrefix, this.mRenameSuffix, this.mRenameBytesFrom, this.mRenameBytesLength, this.mRenameNameFrom, this.mRenameNameLength, this.mRenameBytesReverseOrder);
        }
    }
}
