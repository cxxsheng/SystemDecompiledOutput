package android.companion;

/* loaded from: classes.dex */
public final class WifiDeviceFilter implements android.companion.DeviceFilter<android.net.wifi.ScanResult> {
    public static final android.os.Parcelable.Creator<android.companion.WifiDeviceFilter> CREATOR;
    static com.android.internal.util.Parcelling<java.util.regex.Pattern> sParcellingForNamePattern;
    private final android.net.MacAddress mBssid;
    private final android.net.MacAddress mBssidMask;
    private final java.util.regex.Pattern mNamePattern;

    @Override // android.companion.DeviceFilter
    public boolean matches(android.net.wifi.ScanResult scanResult) {
        return android.companion.BluetoothDeviceFilterUtils.matchesName(getNamePattern(), scanResult) && (this.mBssid == null || android.net.MacAddress.fromString(scanResult.BSSID).matches(this.mBssid, this.mBssidMask));
    }

    @Override // android.companion.DeviceFilter
    public java.lang.String getDeviceDisplayName(android.net.wifi.ScanResult scanResult) {
        return android.companion.BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(scanResult);
    }

    @Override // android.companion.DeviceFilter
    public int getMediumType() {
        return 2;
    }

    WifiDeviceFilter(java.util.regex.Pattern pattern, android.net.MacAddress macAddress, android.net.MacAddress macAddress2) {
        this.mNamePattern = pattern;
        this.mBssid = macAddress;
        this.mBssidMask = macAddress2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBssidMask);
    }

    public java.util.regex.Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public android.net.MacAddress getBssid() {
        return this.mBssid;
    }

    public android.net.MacAddress getBssidMask() {
        return this.mBssidMask;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.companion.WifiDeviceFilter wifiDeviceFilter = (android.companion.WifiDeviceFilter) obj;
        if (java.util.Objects.equals(this.mNamePattern, wifiDeviceFilter.mNamePattern) && java.util.Objects.equals(this.mBssid, wifiDeviceFilter.mBssid) && java.util.Objects.equals(this.mBssidMask, wifiDeviceFilter.mBssidMask)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mNamePattern) + 31) * 31) + java.util.Objects.hashCode(this.mBssid)) * 31) + java.util.Objects.hashCode(this.mBssidMask);
    }

    static {
        sParcellingForNamePattern = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForPattern.class);
        if (sParcellingForNamePattern == null) {
            sParcellingForNamePattern = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForPattern());
        }
        CREATOR = new android.os.Parcelable.Creator<android.companion.WifiDeviceFilter>() { // from class: android.companion.WifiDeviceFilter.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.companion.WifiDeviceFilter[] newArray(int i) {
                return new android.companion.WifiDeviceFilter[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.companion.WifiDeviceFilter createFromParcel(android.os.Parcel parcel) {
                return new android.companion.WifiDeviceFilter(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mNamePattern != null ? (byte) 1 : (byte) 0;
        if (this.mBssid != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        sParcellingForNamePattern.parcel(this.mNamePattern, parcel, i);
        if (this.mBssid != null) {
            parcel.writeTypedObject(this.mBssid, i);
        }
        parcel.writeTypedObject(this.mBssidMask, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    WifiDeviceFilter(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        java.util.regex.Pattern unparcel = sParcellingForNamePattern.unparcel(parcel);
        android.net.MacAddress macAddress = (readByte & 2) == 0 ? null : (android.net.MacAddress) parcel.readTypedObject(android.net.MacAddress.CREATOR);
        android.net.MacAddress macAddress2 = (android.net.MacAddress) parcel.readTypedObject(android.net.MacAddress.CREATOR);
        this.mNamePattern = unparcel;
        this.mBssid = macAddress;
        this.mBssidMask = macAddress2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBssidMask);
    }

    public static final class Builder {
        private android.net.MacAddress mBssid;
        private android.net.MacAddress mBssidMask;
        private long mBuilderFieldsSet = 0;
        private java.util.regex.Pattern mNamePattern;

        public android.companion.WifiDeviceFilter.Builder setNamePattern(java.util.regex.Pattern pattern) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mNamePattern = pattern;
            return this;
        }

        public android.companion.WifiDeviceFilter.Builder setBssid(android.net.MacAddress macAddress) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mBssid = macAddress;
            return this;
        }

        public android.companion.WifiDeviceFilter.Builder setBssidMask(android.net.MacAddress macAddress) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mBssidMask = macAddress;
            return this;
        }

        public android.companion.WifiDeviceFilter build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mNamePattern = null;
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mBssid = null;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mBssidMask = android.net.MacAddress.BROADCAST_ADDRESS;
            }
            return new android.companion.WifiDeviceFilter(this.mNamePattern, this.mBssid, this.mBssidMask);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
