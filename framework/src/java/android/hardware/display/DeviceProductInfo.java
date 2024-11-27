package android.hardware.display;

/* loaded from: classes2.dex */
public final class DeviceProductInfo implements android.os.Parcelable {
    public static final int CONNECTION_TO_SINK_BUILT_IN = 1;
    public static final int CONNECTION_TO_SINK_DIRECT = 2;
    public static final int CONNECTION_TO_SINK_TRANSITIVE = 3;
    public static final int CONNECTION_TO_SINK_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.hardware.display.DeviceProductInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.DeviceProductInfo>() { // from class: android.hardware.display.DeviceProductInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.DeviceProductInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.DeviceProductInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.DeviceProductInfo[] newArray(int i) {
            return new android.hardware.display.DeviceProductInfo[i];
        }
    };
    private final int mConnectionToSinkType;
    private final android.hardware.display.DeviceProductInfo.ManufactureDate mManufactureDate;
    private final java.lang.String mManufacturerPnpId;
    private final java.lang.Integer mModelYear;
    private final java.lang.String mName;
    private final java.lang.String mProductId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionToSinkType {
    }

    public DeviceProductInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Integer num, android.hardware.display.DeviceProductInfo.ManufactureDate manufactureDate, int i) {
        this.mName = str;
        this.mManufacturerPnpId = str2;
        this.mProductId = str3;
        this.mModelYear = num;
        this.mManufactureDate = manufactureDate;
        this.mConnectionToSinkType = i;
    }

    public DeviceProductInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
        this.mName = str;
        this.mManufacturerPnpId = (java.lang.String) java.util.Objects.requireNonNull(str2);
        this.mProductId = (java.lang.String) java.util.Objects.requireNonNull(str3);
        this.mModelYear = java.lang.Integer.valueOf(i);
        this.mManufactureDate = null;
        this.mConnectionToSinkType = i2;
    }

    private DeviceProductInfo(android.os.Parcel parcel) {
        this.mName = parcel.readString();
        this.mManufacturerPnpId = parcel.readString();
        this.mProductId = (java.lang.String) parcel.readValue(null);
        this.mModelYear = (java.lang.Integer) parcel.readValue(null);
        this.mManufactureDate = (android.hardware.display.DeviceProductInfo.ManufactureDate) parcel.readValue(null);
        this.mConnectionToSinkType = parcel.readInt();
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getManufacturerPnpId() {
        return this.mManufacturerPnpId;
    }

    public java.lang.String getProductId() {
        return this.mProductId;
    }

    public int getModelYear() {
        if (this.mModelYear != null) {
            return this.mModelYear.intValue();
        }
        return -1;
    }

    public int getManufactureYear() {
        if (this.mManufactureDate == null || this.mManufactureDate.mYear == null) {
            return -1;
        }
        return this.mManufactureDate.mYear.intValue();
    }

    public int getManufactureWeek() {
        if (this.mManufactureDate == null || this.mManufactureDate.mWeek == null) {
            return -1;
        }
        return this.mManufactureDate.mWeek.intValue();
    }

    public android.hardware.display.DeviceProductInfo.ManufactureDate getManufactureDate() {
        return this.mManufactureDate;
    }

    public int getConnectionToSinkType() {
        return this.mConnectionToSinkType;
    }

    public java.lang.String toString() {
        return "DeviceProductInfo{name=" + this.mName + ", manufacturerPnpId=" + this.mManufacturerPnpId + ", productId=" + this.mProductId + ", modelYear=" + this.mModelYear + ", manufactureDate=" + this.mManufactureDate + ", connectionToSinkType=" + this.mConnectionToSinkType + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.display.DeviceProductInfo deviceProductInfo = (android.hardware.display.DeviceProductInfo) obj;
        if (java.util.Objects.equals(this.mName, deviceProductInfo.mName) && java.util.Objects.equals(this.mManufacturerPnpId, deviceProductInfo.mManufacturerPnpId) && java.util.Objects.equals(this.mProductId, deviceProductInfo.mProductId) && java.util.Objects.equals(this.mModelYear, deviceProductInfo.mModelYear) && java.util.Objects.equals(this.mManufactureDate, deviceProductInfo.mManufactureDate) && this.mConnectionToSinkType == deviceProductInfo.mConnectionToSinkType) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mName, this.mManufacturerPnpId, this.mProductId, this.mModelYear, this.mManufactureDate, java.lang.Integer.valueOf(this.mConnectionToSinkType));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mManufacturerPnpId);
        parcel.writeValue(this.mProductId);
        parcel.writeValue(this.mModelYear);
        parcel.writeValue(this.mManufactureDate);
        parcel.writeInt(this.mConnectionToSinkType);
    }

    public static class ManufactureDate implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.display.DeviceProductInfo.ManufactureDate> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.DeviceProductInfo.ManufactureDate>() { // from class: android.hardware.display.DeviceProductInfo.ManufactureDate.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.display.DeviceProductInfo.ManufactureDate createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.display.DeviceProductInfo.ManufactureDate(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.display.DeviceProductInfo.ManufactureDate[] newArray(int i) {
                return new android.hardware.display.DeviceProductInfo.ManufactureDate[i];
            }
        };
        private final java.lang.Integer mWeek;
        private final java.lang.Integer mYear;

        public ManufactureDate(java.lang.Integer num, java.lang.Integer num2) {
            this.mWeek = num;
            this.mYear = num2;
        }

        protected ManufactureDate(android.os.Parcel parcel) {
            this.mWeek = (java.lang.Integer) parcel.readValue(null);
            this.mYear = (java.lang.Integer) parcel.readValue(null);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeValue(this.mWeek);
            parcel.writeValue(this.mYear);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.Integer getYear() {
            return this.mYear;
        }

        public java.lang.Integer getWeek() {
            return this.mWeek;
        }

        public java.lang.String toString() {
            return "ManufactureDate{week=" + this.mWeek + ", year=" + this.mYear + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.hardware.display.DeviceProductInfo.ManufactureDate manufactureDate = (android.hardware.display.DeviceProductInfo.ManufactureDate) obj;
            if (java.util.Objects.equals(this.mWeek, manufactureDate.mWeek) && java.util.Objects.equals(this.mYear, manufactureDate.mYear)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mWeek, this.mYear);
        }
    }
}
