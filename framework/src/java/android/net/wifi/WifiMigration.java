package android.net.wifi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class WifiMigration {
    private static final java.lang.String LEGACY_WIFI_STORE_DIRECTORY_NAME = "wifi";
    public static final int STORE_FILE_SHARED_GENERAL = 0;
    public static final int STORE_FILE_SHARED_SOFTAP = 1;
    public static final int STORE_FILE_USER_GENERAL = 2;
    public static final int STORE_FILE_USER_NETWORK_SUGGESTIONS = 3;
    private static final android.util.SparseArray<java.lang.String> STORE_ID_TO_FILE_NAME = new android.util.SparseArray<java.lang.String>() { // from class: android.net.wifi.WifiMigration.1
        {
            put(0, "WifiConfigStore.xml");
            put(1, "WifiConfigStoreSoftAp.xml");
            put(2, "WifiConfigStore.xml");
            put(3, "WifiConfigStoreNetworkSuggestions.xml");
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SharedStoreFileId {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserStoreFileId {
    }

    private static java.io.File getLegacyWifiSharedDirectory() {
        return new java.io.File(android.os.Environment.getDataMiscDirectory(), "wifi");
    }

    private static java.io.File getLegacyWifiUserDirectory(int i) {
        return new java.io.File(android.os.Environment.getDataMiscCeDirectory(i), "wifi");
    }

    private static android.util.AtomicFile getSharedAtomicFile(int i) {
        return new android.util.AtomicFile(new java.io.File(getLegacyWifiSharedDirectory(), STORE_ID_TO_FILE_NAME.get(i)));
    }

    private static android.util.AtomicFile getUserAtomicFile(int i, int i2) {
        return new android.util.AtomicFile(new java.io.File(getLegacyWifiUserDirectory(i2), STORE_ID_TO_FILE_NAME.get(i)));
    }

    private WifiMigration() {
    }

    public static java.io.InputStream convertAndRetrieveSharedConfigStoreFile(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid shared store file id");
        }
        try {
            return getSharedAtomicFile(i).openRead();
        } catch (java.io.FileNotFoundException e) {
            if (i == 1) {
                return android.net.wifi.SoftApConfToXmlMigrationUtil.convert();
            }
            return null;
        }
    }

    public static void removeSharedConfigStoreFile(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid shared store file id");
        }
        android.util.AtomicFile sharedAtomicFile = getSharedAtomicFile(i);
        if (sharedAtomicFile.exists()) {
            sharedAtomicFile.delete();
        } else if (i == 1) {
            android.net.wifi.SoftApConfToXmlMigrationUtil.remove();
        }
    }

    public static java.io.InputStream convertAndRetrieveUserConfigStoreFile(int i, android.os.UserHandle userHandle) {
        if (i != 2 && i != 3) {
            throw new java.lang.IllegalArgumentException("Invalid user store file id");
        }
        java.util.Objects.requireNonNull(userHandle);
        try {
            return getUserAtomicFile(i, userHandle.getIdentifier()).openRead();
        } catch (java.io.FileNotFoundException e) {
            return null;
        }
    }

    public static void removeUserConfigStoreFile(int i, android.os.UserHandle userHandle) {
        if (i != 2 && i != 3) {
            throw new java.lang.IllegalArgumentException("Invalid user store file id");
        }
        java.util.Objects.requireNonNull(userHandle);
        android.util.AtomicFile userAtomicFile = getUserAtomicFile(i, userHandle.getIdentifier());
        if (userAtomicFile.exists()) {
            userAtomicFile.delete();
        }
    }

    public static final class SettingsMigrationData implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.net.wifi.WifiMigration.SettingsMigrationData> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.WifiMigration.SettingsMigrationData>() { // from class: android.net.wifi.WifiMigration.SettingsMigrationData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.net.wifi.WifiMigration.SettingsMigrationData createFromParcel(android.os.Parcel parcel) {
                return new android.net.wifi.WifiMigration.SettingsMigrationData(parcel.readBoolean(), parcel.readBoolean(), parcel.readString(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.net.wifi.WifiMigration.SettingsMigrationData[] newArray(int i) {
                return new android.net.wifi.WifiMigration.SettingsMigrationData[i];
            }
        };
        private final java.lang.String mP2pDeviceName;
        private final boolean mP2pFactoryResetPending;
        private final boolean mScanAlwaysAvailable;
        private final boolean mScanThrottleEnabled;
        private final boolean mSoftApTimeoutEnabled;
        private final boolean mVerboseLoggingEnabled;
        private final boolean mWakeupEnabled;

        private SettingsMigrationData(boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.mScanAlwaysAvailable = z;
            this.mP2pFactoryResetPending = z2;
            this.mP2pDeviceName = str;
            this.mSoftApTimeoutEnabled = z3;
            this.mWakeupEnabled = z4;
            this.mScanThrottleEnabled = z5;
            this.mVerboseLoggingEnabled = z6;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBoolean(this.mScanAlwaysAvailable);
            parcel.writeBoolean(this.mP2pFactoryResetPending);
            parcel.writeString(this.mP2pDeviceName);
            parcel.writeBoolean(this.mSoftApTimeoutEnabled);
            parcel.writeBoolean(this.mWakeupEnabled);
            parcel.writeBoolean(this.mScanThrottleEnabled);
            parcel.writeBoolean(this.mVerboseLoggingEnabled);
        }

        public boolean isScanAlwaysAvailable() {
            return this.mScanAlwaysAvailable;
        }

        public boolean isP2pFactoryResetPending() {
            return this.mP2pFactoryResetPending;
        }

        public java.lang.String getP2pDeviceName() {
            return this.mP2pDeviceName;
        }

        public boolean isSoftApTimeoutEnabled() {
            return this.mSoftApTimeoutEnabled;
        }

        public boolean isWakeUpEnabled() {
            return this.mWakeupEnabled;
        }

        public boolean isScanThrottleEnabled() {
            return this.mScanThrottleEnabled;
        }

        public boolean isVerboseLoggingEnabled() {
            return this.mVerboseLoggingEnabled;
        }

        public static final class Builder {
            private java.lang.String mP2pDeviceName;
            private boolean mP2pFactoryResetPending;
            private boolean mScanAlwaysAvailable;
            private boolean mScanThrottleEnabled;
            private boolean mSoftApTimeoutEnabled;
            private boolean mVerboseLoggingEnabled;
            private boolean mWakeupEnabled;

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setScanAlwaysAvailable(boolean z) {
                this.mScanAlwaysAvailable = z;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setP2pFactoryResetPending(boolean z) {
                this.mP2pFactoryResetPending = z;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setP2pDeviceName(java.lang.String str) {
                this.mP2pDeviceName = str;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setSoftApTimeoutEnabled(boolean z) {
                this.mSoftApTimeoutEnabled = z;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setWakeUpEnabled(boolean z) {
                this.mWakeupEnabled = z;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setScanThrottleEnabled(boolean z) {
                this.mScanThrottleEnabled = z;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData.Builder setVerboseLoggingEnabled(boolean z) {
                this.mVerboseLoggingEnabled = z;
                return this;
            }

            public android.net.wifi.WifiMigration.SettingsMigrationData build() {
                return new android.net.wifi.WifiMigration.SettingsMigrationData(this.mScanAlwaysAvailable, this.mP2pFactoryResetPending, this.mP2pDeviceName, this.mSoftApTimeoutEnabled, this.mWakeupEnabled, this.mScanThrottleEnabled, this.mVerboseLoggingEnabled);
            }
        }
    }

    public static android.net.wifi.WifiMigration.SettingsMigrationData loadFromSettings(android.content.Context context) {
        if (android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_MIGRATION_COMPLETED, 0) == 1) {
            return null;
        }
        android.net.wifi.WifiMigration.SettingsMigrationData build = new android.net.wifi.WifiMigration.SettingsMigrationData.Builder().setScanAlwaysAvailable(android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_SCAN_ALWAYS_AVAILABLE, 0) == 1).setP2pFactoryResetPending(android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_P2P_PENDING_FACTORY_RESET, 0) == 1).setP2pDeviceName(android.provider.Settings.Global.getString(context.getContentResolver(), android.provider.Settings.Global.WIFI_P2P_DEVICE_NAME)).setSoftApTimeoutEnabled(android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.SOFT_AP_TIMEOUT_ENABLED, 1) == 1).setWakeUpEnabled(android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_WAKEUP_ENABLED, 0) == 1).setScanThrottleEnabled(android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_SCAN_THROTTLE_ENABLED, 1) == 1).setVerboseLoggingEnabled(android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_VERBOSE_LOGGING_ENABLED, 0) == 1).build();
        android.provider.Settings.Global.putInt(context.getContentResolver(), android.provider.Settings.Global.WIFI_MIGRATION_COMPLETED, 1);
        return build;
    }
}
