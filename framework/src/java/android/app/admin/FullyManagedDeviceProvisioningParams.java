package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class FullyManagedDeviceProvisioningParams implements android.os.Parcelable {
    private static final java.lang.String CAN_DEVICE_OWNER_GRANT_SENSOR_PERMISSIONS_PARAM = "CAN_DEVICE_OWNER_GRANT_SENSOR_PERMISSIONS";
    public static final android.os.Parcelable.Creator<android.app.admin.FullyManagedDeviceProvisioningParams> CREATOR = new android.os.Parcelable.Creator<android.app.admin.FullyManagedDeviceProvisioningParams>() { // from class: android.app.admin.FullyManagedDeviceProvisioningParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.FullyManagedDeviceProvisioningParams createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.FullyManagedDeviceProvisioningParams((android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR), parcel.readString(), parcel.readBoolean(), parcel.readString(), parcel.readLong(), parcel.readString(), parcel.readBoolean(), parcel.readPersistableBundle(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.FullyManagedDeviceProvisioningParams[] newArray(int i) {
            return new android.app.admin.FullyManagedDeviceProvisioningParams[i];
        }
    };
    private static final java.lang.String DEMO_DEVICE = "DEMO_DEVICE";
    private static final java.lang.String LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM = "LEAVE_ALL_SYSTEM_APPS_ENABLED";
    private static final java.lang.String LOCALE_PROVIDED_PARAM = "LOCALE_PROVIDED";
    private static final java.lang.String TIME_ZONE_PROVIDED_PARAM = "TIME_ZONE_PROVIDED";
    private final android.os.PersistableBundle mAdminExtras;
    private final boolean mDemoDevice;
    private final android.content.ComponentName mDeviceAdminComponentName;
    private final boolean mDeviceOwnerCanGrantSensorsPermissions;
    private final boolean mLeaveAllSystemAppsEnabled;
    private final long mLocalTime;
    private final java.util.Locale mLocale;
    private final java.lang.String mOwnerName;
    private final java.lang.String mTimeZone;

    private FullyManagedDeviceProvisioningParams(android.content.ComponentName componentName, java.lang.String str, boolean z, java.lang.String str2, long j, java.util.Locale locale, boolean z2, android.os.PersistableBundle persistableBundle, boolean z3) {
        this.mDeviceAdminComponentName = (android.content.ComponentName) java.util.Objects.requireNonNull(componentName);
        this.mOwnerName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mLeaveAllSystemAppsEnabled = z;
        this.mTimeZone = str2;
        this.mLocalTime = j;
        this.mLocale = locale;
        this.mDeviceOwnerCanGrantSensorsPermissions = z2;
        this.mAdminExtras = persistableBundle;
        this.mDemoDevice = z3;
    }

    private FullyManagedDeviceProvisioningParams(android.content.ComponentName componentName, java.lang.String str, boolean z, java.lang.String str2, long j, java.lang.String str3, boolean z2, android.os.PersistableBundle persistableBundle, boolean z3) {
        this(componentName, str, z, str2, j, getLocale(str3), z2, persistableBundle, z3);
    }

    private static java.util.Locale getLocale(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return java.util.Locale.forLanguageTag(str);
    }

    public android.content.ComponentName getDeviceAdminComponentName() {
        return this.mDeviceAdminComponentName;
    }

    public java.lang.String getOwnerName() {
        return this.mOwnerName;
    }

    public boolean isLeaveAllSystemAppsEnabled() {
        return this.mLeaveAllSystemAppsEnabled;
    }

    public java.lang.String getTimeZone() {
        return this.mTimeZone;
    }

    public long getLocalTime() {
        return this.mLocalTime;
    }

    public java.util.Locale getLocale() {
        return this.mLocale;
    }

    public boolean canDeviceOwnerGrantSensorsPermissions() {
        return this.mDeviceOwnerCanGrantSensorsPermissions;
    }

    public android.os.PersistableBundle getAdminExtras() {
        return new android.os.PersistableBundle(this.mAdminExtras);
    }

    public boolean isDemoDevice() {
        return this.mDemoDevice;
    }

    public void logParams(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        logParam(str, LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM, this.mLeaveAllSystemAppsEnabled);
        logParam(str, CAN_DEVICE_OWNER_GRANT_SENSOR_PERMISSIONS_PARAM, this.mDeviceOwnerCanGrantSensorsPermissions);
        logParam(str, TIME_ZONE_PROVIDED_PARAM, this.mTimeZone != null);
        logParam(str, LOCALE_PROVIDED_PARAM, this.mLocale != null);
        logParam(str, DEMO_DEVICE, this.mDemoDevice);
    }

    private void logParam(java.lang.String str, java.lang.String str2, boolean z) {
        android.app.admin.DevicePolicyEventLogger.createEvent(197).setStrings(str).setAdmin(this.mDeviceAdminComponentName).setStrings(str2).setBoolean(z).write();
    }

    public static final class Builder {
        private android.os.PersistableBundle mAdminExtras;
        private final android.content.ComponentName mDeviceAdminComponentName;
        private boolean mLeaveAllSystemAppsEnabled;
        private long mLocalTime;
        private java.util.Locale mLocale;
        private final java.lang.String mOwnerName;
        private java.lang.String mTimeZone;
        boolean mDeviceOwnerCanGrantSensorsPermissions = true;
        boolean mDemoDevice = false;

        public Builder(android.content.ComponentName componentName, java.lang.String str) {
            this.mDeviceAdminComponentName = (android.content.ComponentName) java.util.Objects.requireNonNull(componentName);
            this.mOwnerName = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setLeaveAllSystemAppsEnabled(boolean z) {
            this.mLeaveAllSystemAppsEnabled = z;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setTimeZone(java.lang.String str) {
            this.mTimeZone = str;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setLocalTime(long j) {
            this.mLocalTime = j;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setLocale(java.util.Locale locale) {
            this.mLocale = locale;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setCanDeviceOwnerGrantSensorsPermissions(boolean z) {
            this.mDeviceOwnerCanGrantSensorsPermissions = z;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setAdminExtras(android.os.PersistableBundle persistableBundle) {
            android.os.PersistableBundle persistableBundle2;
            if (persistableBundle != null) {
                persistableBundle2 = new android.os.PersistableBundle(persistableBundle);
            } else {
                persistableBundle2 = new android.os.PersistableBundle();
            }
            this.mAdminExtras = persistableBundle2;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams.Builder setDemoDevice(boolean z) {
            this.mDemoDevice = z;
            return this;
        }

        public android.app.admin.FullyManagedDeviceProvisioningParams build() {
            return new android.app.admin.FullyManagedDeviceProvisioningParams(this.mDeviceAdminComponentName, this.mOwnerName, this.mLeaveAllSystemAppsEnabled, this.mTimeZone, this.mLocalTime, this.mLocale, this.mDeviceOwnerCanGrantSensorsPermissions, this.mAdminExtras != null ? this.mAdminExtras : new android.os.PersistableBundle(), this.mDemoDevice);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "FullyManagedDeviceProvisioningParams{mDeviceAdminComponentName=" + this.mDeviceAdminComponentName + ", mOwnerName=" + this.mOwnerName + ", mLeaveAllSystemAppsEnabled=" + this.mLeaveAllSystemAppsEnabled + ", mTimeZone=" + (this.mTimeZone == null ? "null" : this.mTimeZone) + ", mLocalTime=" + this.mLocalTime + ", mLocale=" + (this.mLocale != null ? this.mLocale : "null") + ", mDeviceOwnerCanGrantSensorsPermissions=" + this.mDeviceOwnerCanGrantSensorsPermissions + ", mAdminExtras=" + this.mAdminExtras + ", mDemoDevice=" + this.mDemoDevice + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mDeviceAdminComponentName, i);
        parcel.writeString(this.mOwnerName);
        parcel.writeBoolean(this.mLeaveAllSystemAppsEnabled);
        parcel.writeString(this.mTimeZone);
        parcel.writeLong(this.mLocalTime);
        parcel.writeString(this.mLocale == null ? null : this.mLocale.toLanguageTag());
        parcel.writeBoolean(this.mDeviceOwnerCanGrantSensorsPermissions);
        parcel.writePersistableBundle(this.mAdminExtras);
        parcel.writeBoolean(this.mDemoDevice);
    }
}
