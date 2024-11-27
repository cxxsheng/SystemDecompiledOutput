package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PhoneCapability implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.PhoneCapability> CREATOR;
    public static final android.telephony.PhoneCapability DEFAULT_DSDS_CAPABILITY;
    public static final android.telephony.PhoneCapability DEFAULT_SSSS_CAPABILITY;

    @android.annotation.SystemApi
    public static final int DEVICE_NR_CAPABILITY_NSA = 1;

    @android.annotation.SystemApi
    public static final int DEVICE_NR_CAPABILITY_SA = 2;
    private final int[] mDeviceNrCapabilities;
    private final java.util.List<android.telephony.ModemInfo> mLogicalModemList;
    private final int mMaxActiveDataSubscriptions;
    private final int mMaxActiveVoiceSubscriptions;
    private final boolean mNetworkValidationBeforeSwitchSupported;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceNrCapability {
    }

    static {
        android.telephony.ModemInfo modemInfo = new android.telephony.ModemInfo(0, 0, true, true);
        android.telephony.ModemInfo modemInfo2 = new android.telephony.ModemInfo(1, 0, true, true);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(modemInfo);
        arrayList.add(modemInfo2);
        int[] iArr = new int[0];
        DEFAULT_DSDS_CAPABILITY = new android.telephony.PhoneCapability(1, 1, arrayList, false, iArr);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        arrayList2.add(modemInfo);
        DEFAULT_SSSS_CAPABILITY = new android.telephony.PhoneCapability(1, 1, arrayList2, false, iArr);
        CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.PhoneCapability.1
            @Override // android.os.Parcelable.Creator
            public android.telephony.PhoneCapability createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.PhoneCapability(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public android.telephony.PhoneCapability[] newArray(int i) {
                return new android.telephony.PhoneCapability[i];
            }
        };
    }

    public PhoneCapability(int i, int i2, java.util.List<android.telephony.ModemInfo> list, boolean z, int[] iArr) {
        this.mMaxActiveVoiceSubscriptions = i;
        this.mMaxActiveDataSubscriptions = i2;
        this.mLogicalModemList = list == null ? new java.util.ArrayList<>() : list;
        this.mNetworkValidationBeforeSwitchSupported = z;
        this.mDeviceNrCapabilities = iArr;
    }

    private PhoneCapability(android.telephony.PhoneCapability.Builder builder) {
        this.mMaxActiveVoiceSubscriptions = builder.mMaxActiveVoiceSubscriptions;
        this.mMaxActiveDataSubscriptions = builder.mMaxActiveDataSubscriptions;
        this.mLogicalModemList = builder.mLogicalModemList == null ? new java.util.ArrayList<>() : builder.mLogicalModemList;
        this.mNetworkValidationBeforeSwitchSupported = builder.mNetworkValidationBeforeSwitchSupported;
        this.mDeviceNrCapabilities = builder.mDeviceNrCapabilities;
    }

    public java.lang.String toString() {
        return "mMaxActiveVoiceSubscriptions=" + this.mMaxActiveVoiceSubscriptions + " mMaxActiveDataSubscriptions=" + this.mMaxActiveDataSubscriptions + " mNetworkValidationBeforeSwitchSupported=" + this.mNetworkValidationBeforeSwitchSupported + " mDeviceNrCapability " + java.util.Arrays.toString(this.mDeviceNrCapabilities);
    }

    private PhoneCapability(android.os.Parcel parcel) {
        this.mMaxActiveVoiceSubscriptions = parcel.readInt();
        this.mMaxActiveDataSubscriptions = parcel.readInt();
        this.mNetworkValidationBeforeSwitchSupported = parcel.readBoolean();
        this.mLogicalModemList = new java.util.ArrayList();
        parcel.readList(this.mLogicalModemList, android.telephony.ModemInfo.class.getClassLoader(), android.telephony.ModemInfo.class);
        this.mDeviceNrCapabilities = parcel.createIntArray();
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mMaxActiveVoiceSubscriptions), java.lang.Integer.valueOf(this.mMaxActiveDataSubscriptions), this.mLogicalModemList, java.lang.Boolean.valueOf(this.mNetworkValidationBeforeSwitchSupported), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mDeviceNrCapabilities)));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.PhoneCapability) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.PhoneCapability phoneCapability = (android.telephony.PhoneCapability) obj;
        if (this.mMaxActiveVoiceSubscriptions != phoneCapability.mMaxActiveVoiceSubscriptions || this.mMaxActiveDataSubscriptions != phoneCapability.mMaxActiveDataSubscriptions || this.mNetworkValidationBeforeSwitchSupported != phoneCapability.mNetworkValidationBeforeSwitchSupported || !this.mLogicalModemList.equals(phoneCapability.mLogicalModemList) || !java.util.Arrays.equals(this.mDeviceNrCapabilities, phoneCapability.mDeviceNrCapabilities)) {
            return false;
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMaxActiveVoiceSubscriptions);
        parcel.writeInt(this.mMaxActiveDataSubscriptions);
        parcel.writeBoolean(this.mNetworkValidationBeforeSwitchSupported);
        parcel.writeList(this.mLogicalModemList);
        parcel.writeIntArray(this.mDeviceNrCapabilities);
    }

    @android.annotation.SystemApi
    public int getMaxActiveVoiceSubscriptions() {
        return this.mMaxActiveVoiceSubscriptions;
    }

    @android.annotation.SystemApi
    public int getMaxActiveDataSubscriptions() {
        return this.mMaxActiveDataSubscriptions;
    }

    public boolean isNetworkValidationBeforeSwitchSupported() {
        return this.mNetworkValidationBeforeSwitchSupported;
    }

    public java.util.List<android.telephony.ModemInfo> getLogicalModemList() {
        return this.mLogicalModemList;
    }

    @android.annotation.SystemApi
    public int[] getDeviceNrCapabilities() {
        return this.mDeviceNrCapabilities == null ? new int[0] : this.mDeviceNrCapabilities;
    }

    public static class Builder {
        private int[] mDeviceNrCapabilities;
        private java.util.List<android.telephony.ModemInfo> mLogicalModemList;
        private int mMaxActiveDataSubscriptions;
        private int mMaxActiveVoiceSubscriptions;
        private boolean mNetworkValidationBeforeSwitchSupported;

        public Builder() {
            this.mMaxActiveVoiceSubscriptions = 0;
            this.mMaxActiveDataSubscriptions = 0;
            this.mNetworkValidationBeforeSwitchSupported = false;
            this.mLogicalModemList = new java.util.ArrayList();
            this.mDeviceNrCapabilities = new int[0];
        }

        public Builder(android.telephony.PhoneCapability phoneCapability) {
            this.mMaxActiveVoiceSubscriptions = 0;
            this.mMaxActiveDataSubscriptions = 0;
            this.mNetworkValidationBeforeSwitchSupported = false;
            this.mLogicalModemList = new java.util.ArrayList();
            this.mDeviceNrCapabilities = new int[0];
            this.mMaxActiveVoiceSubscriptions = phoneCapability.mMaxActiveVoiceSubscriptions;
            this.mMaxActiveDataSubscriptions = phoneCapability.mMaxActiveDataSubscriptions;
            this.mNetworkValidationBeforeSwitchSupported = phoneCapability.mNetworkValidationBeforeSwitchSupported;
            this.mLogicalModemList = phoneCapability.mLogicalModemList;
            this.mDeviceNrCapabilities = phoneCapability.mDeviceNrCapabilities;
        }

        public android.telephony.PhoneCapability.Builder setMaxActiveVoiceSubscriptions(int i) {
            this.mMaxActiveVoiceSubscriptions = i;
            return this;
        }

        public android.telephony.PhoneCapability.Builder setMaxActiveDataSubscriptions(int i) {
            this.mMaxActiveDataSubscriptions = i;
            return this;
        }

        public android.telephony.PhoneCapability.Builder setNetworkValidationBeforeSwitchSupported(boolean z) {
            this.mNetworkValidationBeforeSwitchSupported = z;
            return this;
        }

        public android.telephony.PhoneCapability.Builder setLogicalModemList(java.util.List<android.telephony.ModemInfo> list) {
            this.mLogicalModemList = list;
            return this;
        }

        public android.telephony.PhoneCapability.Builder setDeviceNrCapabilities(int[] iArr) {
            this.mDeviceNrCapabilities = iArr;
            return this;
        }

        public android.telephony.PhoneCapability build() {
            return new android.telephony.PhoneCapability(this);
        }
    }
}
