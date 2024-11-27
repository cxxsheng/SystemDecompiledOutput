package android.companion;

/* loaded from: classes.dex */
public final class BluetoothDeviceFilter implements android.companion.DeviceFilter<android.bluetooth.BluetoothDevice> {
    public static final android.os.Parcelable.Creator<android.companion.BluetoothDeviceFilter> CREATOR = new android.os.Parcelable.Creator<android.companion.BluetoothDeviceFilter>() { // from class: android.companion.BluetoothDeviceFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.BluetoothDeviceFilter createFromParcel(android.os.Parcel parcel) {
            return new android.companion.BluetoothDeviceFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.BluetoothDeviceFilter[] newArray(int i) {
            return new android.companion.BluetoothDeviceFilter[i];
        }
    };
    private final java.lang.String mAddress;
    private final java.util.regex.Pattern mNamePattern;
    private final java.util.List<android.os.ParcelUuid> mServiceUuidMasks;
    private final java.util.List<android.os.ParcelUuid> mServiceUuids;

    private BluetoothDeviceFilter(java.util.regex.Pattern pattern, java.lang.String str, java.util.List<android.os.ParcelUuid> list, java.util.List<android.os.ParcelUuid> list2) {
        this.mNamePattern = pattern;
        this.mAddress = str;
        this.mServiceUuids = com.android.internal.util.CollectionUtils.emptyIfNull(list);
        this.mServiceUuidMasks = com.android.internal.util.CollectionUtils.emptyIfNull(list2);
    }

    private BluetoothDeviceFilter(android.os.Parcel parcel) {
        this(android.companion.BluetoothDeviceFilterUtils.patternFromString(parcel.readString()), parcel.readString(), readUuids(parcel), readUuids(parcel));
    }

    private static java.util.List<android.os.ParcelUuid> readUuids(android.os.Parcel parcel) {
        return parcel.readParcelableList(new java.util.ArrayList(), android.os.ParcelUuid.class.getClassLoader(), android.os.ParcelUuid.class);
    }

    @Override // android.companion.DeviceFilter
    public boolean matches(android.bluetooth.BluetoothDevice bluetoothDevice) {
        return android.companion.BluetoothDeviceFilterUtils.matchesAddress(this.mAddress, bluetoothDevice) && android.companion.BluetoothDeviceFilterUtils.matchesServiceUuids(this.mServiceUuids, this.mServiceUuidMasks, bluetoothDevice) && android.companion.BluetoothDeviceFilterUtils.matchesName(getNamePattern(), bluetoothDevice);
    }

    @Override // android.companion.DeviceFilter
    public java.lang.String getDeviceDisplayName(android.bluetooth.BluetoothDevice bluetoothDevice) {
        return android.companion.BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(bluetoothDevice);
    }

    @Override // android.companion.DeviceFilter
    public int getMediumType() {
        return 0;
    }

    public java.util.regex.Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public java.lang.String getAddress() {
        return this.mAddress;
    }

    public java.util.List<android.os.ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public java.util.List<android.os.ParcelUuid> getServiceUuidMasks() {
        return this.mServiceUuidMasks;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(android.companion.BluetoothDeviceFilterUtils.patternToString(getNamePattern()));
        parcel.writeString(this.mAddress);
        parcel.writeParcelableList(this.mServiceUuids, i);
        parcel.writeParcelableList(this.mServiceUuidMasks, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.companion.BluetoothDeviceFilter bluetoothDeviceFilter = (android.companion.BluetoothDeviceFilter) obj;
        if (java.util.Objects.equals(this.mNamePattern, bluetoothDeviceFilter.mNamePattern) && java.util.Objects.equals(this.mAddress, bluetoothDeviceFilter.mAddress) && java.util.Objects.equals(this.mServiceUuids, bluetoothDeviceFilter.mServiceUuids) && java.util.Objects.equals(this.mServiceUuidMasks, bluetoothDeviceFilter.mServiceUuidMasks)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mNamePattern, this.mAddress, this.mServiceUuids, this.mServiceUuidMasks);
    }

    public java.lang.String toString() {
        return "BluetoothDeviceFilter{mNamePattern=" + this.mNamePattern + ", mAddress='" + this.mAddress + android.text.format.DateFormat.QUOTE + ", mServiceUuids=" + this.mServiceUuids + ", mServiceUuidMasks=" + this.mServiceUuidMasks + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder extends android.provider.OneTimeUseBuilder<android.companion.BluetoothDeviceFilter> {
        private java.lang.String mAddress;
        private java.util.regex.Pattern mNamePattern;
        private java.util.ArrayList<android.os.ParcelUuid> mServiceUuid;
        private java.util.ArrayList<android.os.ParcelUuid> mServiceUuidMask;

        public android.companion.BluetoothDeviceFilter.Builder setNamePattern(java.util.regex.Pattern pattern) {
            checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }

        public android.companion.BluetoothDeviceFilter.Builder setAddress(java.lang.String str) {
            checkNotUsed();
            this.mAddress = str;
            return this;
        }

        public android.companion.BluetoothDeviceFilter.Builder addServiceUuid(android.os.ParcelUuid parcelUuid, android.os.ParcelUuid parcelUuid2) {
            checkNotUsed();
            this.mServiceUuid = com.android.internal.util.ArrayUtils.add(this.mServiceUuid, parcelUuid);
            this.mServiceUuidMask = com.android.internal.util.ArrayUtils.add(this.mServiceUuidMask, parcelUuid2);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public android.companion.BluetoothDeviceFilter build() {
            markUsed();
            return new android.companion.BluetoothDeviceFilter(this.mNamePattern, this.mAddress, this.mServiceUuid, this.mServiceUuidMask);
        }
    }
}
