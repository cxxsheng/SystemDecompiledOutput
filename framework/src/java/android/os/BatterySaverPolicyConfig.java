package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class BatterySaverPolicyConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.BatterySaverPolicyConfig> CREATOR = new android.os.Parcelable.Creator<android.os.BatterySaverPolicyConfig>() { // from class: android.os.BatterySaverPolicyConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatterySaverPolicyConfig createFromParcel(android.os.Parcel parcel) {
            return new android.os.BatterySaverPolicyConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatterySaverPolicyConfig[] newArray(int i) {
            return new android.os.BatterySaverPolicyConfig[i];
        }
    };
    private final float mAdjustBrightnessFactor;
    private final boolean mAdvertiseIsEnabled;
    private final boolean mDeferFullBackup;
    private final boolean mDeferKeyValueBackup;
    private final java.util.Map<java.lang.String, java.lang.String> mDeviceSpecificSettings;
    private final boolean mDisableAnimation;
    private final boolean mDisableAod;
    private final boolean mDisableLaunchBoost;
    private final boolean mDisableOptionalSensors;
    private final boolean mDisableVibration;
    private final boolean mEnableAdjustBrightness;
    private final boolean mEnableDataSaver;
    private final boolean mEnableFirewall;
    private final boolean mEnableNightMode;
    private final boolean mEnableQuickDoze;
    private final boolean mForceAllAppsStandby;
    private final boolean mForceBackgroundCheck;
    private final int mLocationMode;
    private final int mSoundTriggerMode;

    private BatterySaverPolicyConfig(android.os.BatterySaverPolicyConfig.Builder builder) {
        this.mAdjustBrightnessFactor = java.lang.Math.max(0.0f, java.lang.Math.min(builder.mAdjustBrightnessFactor, 1.0f));
        this.mAdvertiseIsEnabled = builder.mAdvertiseIsEnabled;
        this.mDeferFullBackup = builder.mDeferFullBackup;
        this.mDeferKeyValueBackup = builder.mDeferKeyValueBackup;
        this.mDeviceSpecificSettings = java.util.Collections.unmodifiableMap(new android.util.ArrayMap(builder.mDeviceSpecificSettings));
        this.mDisableAnimation = builder.mDisableAnimation;
        this.mDisableAod = builder.mDisableAod;
        this.mDisableLaunchBoost = builder.mDisableLaunchBoost;
        this.mDisableOptionalSensors = builder.mDisableOptionalSensors;
        this.mDisableVibration = builder.mDisableVibration;
        this.mEnableAdjustBrightness = builder.mEnableAdjustBrightness;
        this.mEnableDataSaver = builder.mEnableDataSaver;
        this.mEnableFirewall = builder.mEnableFirewall;
        this.mEnableNightMode = builder.mEnableNightMode;
        this.mEnableQuickDoze = builder.mEnableQuickDoze;
        this.mForceAllAppsStandby = builder.mForceAllAppsStandby;
        this.mForceBackgroundCheck = builder.mForceBackgroundCheck;
        this.mLocationMode = java.lang.Math.max(0, java.lang.Math.min(builder.mLocationMode, 4));
        this.mSoundTriggerMode = java.lang.Math.max(0, java.lang.Math.min(builder.mSoundTriggerMode, 2));
    }

    private BatterySaverPolicyConfig(android.os.Parcel parcel) {
        this.mAdjustBrightnessFactor = java.lang.Math.max(0.0f, java.lang.Math.min(parcel.readFloat(), 1.0f));
        this.mAdvertiseIsEnabled = parcel.readBoolean();
        this.mDeferFullBackup = parcel.readBoolean();
        this.mDeferKeyValueBackup = parcel.readBoolean();
        int readInt = parcel.readInt();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(readInt);
        for (int i = 0; i < readInt; i++) {
            java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(parcel.readString());
            java.lang.String emptyIfNull2 = android.text.TextUtils.emptyIfNull(parcel.readString());
            if (!emptyIfNull.trim().isEmpty()) {
                arrayMap.put(emptyIfNull, emptyIfNull2);
            }
        }
        this.mDeviceSpecificSettings = java.util.Collections.unmodifiableMap(arrayMap);
        this.mDisableAnimation = parcel.readBoolean();
        this.mDisableAod = parcel.readBoolean();
        this.mDisableLaunchBoost = parcel.readBoolean();
        this.mDisableOptionalSensors = parcel.readBoolean();
        this.mDisableVibration = parcel.readBoolean();
        this.mEnableAdjustBrightness = parcel.readBoolean();
        this.mEnableDataSaver = parcel.readBoolean();
        this.mEnableFirewall = parcel.readBoolean();
        this.mEnableNightMode = parcel.readBoolean();
        this.mEnableQuickDoze = parcel.readBoolean();
        this.mForceAllAppsStandby = parcel.readBoolean();
        this.mForceBackgroundCheck = parcel.readBoolean();
        this.mLocationMode = java.lang.Math.max(0, java.lang.Math.min(parcel.readInt(), 4));
        this.mSoundTriggerMode = java.lang.Math.max(0, java.lang.Math.min(parcel.readInt(), 2));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mAdjustBrightnessFactor);
        parcel.writeBoolean(this.mAdvertiseIsEnabled);
        parcel.writeBoolean(this.mDeferFullBackup);
        parcel.writeBoolean(this.mDeferKeyValueBackup);
        java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.String>> entrySet = this.mDeviceSpecificSettings.entrySet();
        parcel.writeInt(entrySet.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : entrySet) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
        parcel.writeBoolean(this.mDisableAnimation);
        parcel.writeBoolean(this.mDisableAod);
        parcel.writeBoolean(this.mDisableLaunchBoost);
        parcel.writeBoolean(this.mDisableOptionalSensors);
        parcel.writeBoolean(this.mDisableVibration);
        parcel.writeBoolean(this.mEnableAdjustBrightness);
        parcel.writeBoolean(this.mEnableDataSaver);
        parcel.writeBoolean(this.mEnableFirewall);
        parcel.writeBoolean(this.mEnableNightMode);
        parcel.writeBoolean(this.mEnableQuickDoze);
        parcel.writeBoolean(this.mForceAllAppsStandby);
        parcel.writeBoolean(this.mForceBackgroundCheck);
        parcel.writeInt(this.mLocationMode);
        parcel.writeInt(this.mSoundTriggerMode);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.mDeviceSpecificSettings.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        return "adjust_brightness_disabled=" + (!this.mEnableAdjustBrightness) + ",adjust_brightness_factor=" + this.mAdjustBrightnessFactor + ",advertise_is_enabled=" + this.mAdvertiseIsEnabled + ",animation_disabled=" + this.mDisableAnimation + ",aod_disabled=" + this.mDisableAod + ",datasaver_disabled=" + (!this.mEnableDataSaver) + ",enable_night_mode=" + this.mEnableNightMode + ",firewall_disabled=" + (!this.mEnableFirewall) + ",force_all_apps_standby=" + this.mForceAllAppsStandby + ",force_background_check=" + this.mForceBackgroundCheck + ",fullbackup_deferred=" + this.mDeferFullBackup + ",gps_mode=" + this.mLocationMode + ",keyvaluebackup_deferred=" + this.mDeferKeyValueBackup + ",launch_boost_disabled=" + this.mDisableLaunchBoost + ",optional_sensors_disabled=" + this.mDisableOptionalSensors + ",quick_doze_enabled=" + this.mEnableQuickDoze + ",soundtrigger_mode=" + this.mSoundTriggerMode + ",vibration_disabled=" + this.mDisableVibration + "," + sb.toString();
    }

    public float getAdjustBrightnessFactor() {
        return this.mAdjustBrightnessFactor;
    }

    public boolean getAdvertiseIsEnabled() {
        return this.mAdvertiseIsEnabled;
    }

    public boolean getDeferFullBackup() {
        return this.mDeferFullBackup;
    }

    public boolean getDeferKeyValueBackup() {
        return this.mDeferKeyValueBackup;
    }

    public java.util.Map<java.lang.String, java.lang.String> getDeviceSpecificSettings() {
        return this.mDeviceSpecificSettings;
    }

    public boolean getDisableAnimation() {
        return this.mDisableAnimation;
    }

    public boolean getDisableAod() {
        return this.mDisableAod;
    }

    public boolean getDisableLaunchBoost() {
        return this.mDisableLaunchBoost;
    }

    public boolean getDisableOptionalSensors() {
        return this.mDisableOptionalSensors;
    }

    public int getSoundTriggerMode() {
        return this.mSoundTriggerMode;
    }

    @java.lang.Deprecated
    public boolean getDisableSoundTrigger() {
        return this.mSoundTriggerMode == 2;
    }

    public boolean getDisableVibration() {
        return this.mDisableVibration;
    }

    public boolean getEnableAdjustBrightness() {
        return this.mEnableAdjustBrightness;
    }

    public boolean getEnableDataSaver() {
        return this.mEnableDataSaver;
    }

    public boolean getEnableFirewall() {
        return this.mEnableFirewall;
    }

    public boolean getEnableNightMode() {
        return this.mEnableNightMode;
    }

    public boolean getEnableQuickDoze() {
        return this.mEnableQuickDoze;
    }

    public boolean getForceAllAppsStandby() {
        return this.mForceAllAppsStandby;
    }

    public boolean getForceBackgroundCheck() {
        return this.mForceBackgroundCheck;
    }

    public int getLocationMode() {
        return this.mLocationMode;
    }

    public static final class Builder {
        private float mAdjustBrightnessFactor;
        private boolean mAdvertiseIsEnabled;
        private boolean mDeferFullBackup;
        private boolean mDeferKeyValueBackup;
        private final android.util.ArrayMap<java.lang.String, java.lang.String> mDeviceSpecificSettings;
        private boolean mDisableAnimation;
        private boolean mDisableAod;
        private boolean mDisableLaunchBoost;
        private boolean mDisableOptionalSensors;
        private boolean mDisableVibration;
        private boolean mEnableAdjustBrightness;
        private boolean mEnableDataSaver;
        private boolean mEnableFirewall;
        private boolean mEnableNightMode;
        private boolean mEnableQuickDoze;
        private boolean mForceAllAppsStandby;
        private boolean mForceBackgroundCheck;
        private int mLocationMode;
        private int mSoundTriggerMode;

        public Builder() {
            this.mAdjustBrightnessFactor = 1.0f;
            this.mAdvertiseIsEnabled = false;
            this.mDeferFullBackup = false;
            this.mDeferKeyValueBackup = false;
            this.mDeviceSpecificSettings = new android.util.ArrayMap<>();
            this.mDisableAnimation = false;
            this.mDisableAod = false;
            this.mDisableLaunchBoost = false;
            this.mDisableOptionalSensors = false;
            this.mDisableVibration = false;
            this.mEnableAdjustBrightness = false;
            this.mEnableDataSaver = false;
            this.mEnableFirewall = false;
            this.mEnableNightMode = false;
            this.mEnableQuickDoze = false;
            this.mForceAllAppsStandby = false;
            this.mForceBackgroundCheck = false;
            this.mLocationMode = 0;
            this.mSoundTriggerMode = 0;
        }

        public Builder(android.os.BatterySaverPolicyConfig batterySaverPolicyConfig) {
            this.mAdjustBrightnessFactor = 1.0f;
            this.mAdvertiseIsEnabled = false;
            this.mDeferFullBackup = false;
            this.mDeferKeyValueBackup = false;
            this.mDeviceSpecificSettings = new android.util.ArrayMap<>();
            this.mDisableAnimation = false;
            this.mDisableAod = false;
            this.mDisableLaunchBoost = false;
            this.mDisableOptionalSensors = false;
            this.mDisableVibration = false;
            this.mEnableAdjustBrightness = false;
            this.mEnableDataSaver = false;
            this.mEnableFirewall = false;
            this.mEnableNightMode = false;
            this.mEnableQuickDoze = false;
            this.mForceAllAppsStandby = false;
            this.mForceBackgroundCheck = false;
            this.mLocationMode = 0;
            this.mSoundTriggerMode = 0;
            this.mAdjustBrightnessFactor = batterySaverPolicyConfig.getAdjustBrightnessFactor();
            this.mAdvertiseIsEnabled = batterySaverPolicyConfig.getAdvertiseIsEnabled();
            this.mDeferFullBackup = batterySaverPolicyConfig.getDeferFullBackup();
            this.mDeferKeyValueBackup = batterySaverPolicyConfig.getDeferKeyValueBackup();
            for (java.lang.String str : batterySaverPolicyConfig.getDeviceSpecificSettings().keySet()) {
                this.mDeviceSpecificSettings.put(str, batterySaverPolicyConfig.getDeviceSpecificSettings().get(str));
            }
            this.mDisableAnimation = batterySaverPolicyConfig.getDisableAnimation();
            this.mDisableAod = batterySaverPolicyConfig.getDisableAod();
            this.mDisableLaunchBoost = batterySaverPolicyConfig.getDisableLaunchBoost();
            this.mDisableOptionalSensors = batterySaverPolicyConfig.getDisableOptionalSensors();
            this.mDisableVibration = batterySaverPolicyConfig.getDisableVibration();
            this.mEnableAdjustBrightness = batterySaverPolicyConfig.getEnableAdjustBrightness();
            this.mEnableDataSaver = batterySaverPolicyConfig.getEnableDataSaver();
            this.mEnableFirewall = batterySaverPolicyConfig.getEnableFirewall();
            this.mEnableNightMode = batterySaverPolicyConfig.getEnableNightMode();
            this.mEnableQuickDoze = batterySaverPolicyConfig.getEnableQuickDoze();
            this.mForceAllAppsStandby = batterySaverPolicyConfig.getForceAllAppsStandby();
            this.mForceBackgroundCheck = batterySaverPolicyConfig.getForceBackgroundCheck();
            this.mLocationMode = batterySaverPolicyConfig.getLocationMode();
            this.mSoundTriggerMode = batterySaverPolicyConfig.getSoundTriggerMode();
        }

        public android.os.BatterySaverPolicyConfig.Builder setAdjustBrightnessFactor(float f) {
            this.mAdjustBrightnessFactor = f;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setAdvertiseIsEnabled(boolean z) {
            this.mAdvertiseIsEnabled = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDeferFullBackup(boolean z) {
            this.mDeferFullBackup = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDeferKeyValueBackup(boolean z) {
            this.mDeferKeyValueBackup = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder addDeviceSpecificSetting(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Key cannot be null");
            }
            java.lang.String trim = str.trim();
            if (android.text.TextUtils.isEmpty(trim)) {
                throw new java.lang.IllegalArgumentException("Key cannot be empty");
            }
            this.mDeviceSpecificSettings.put(trim, android.text.TextUtils.emptyIfNull(str2));
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDisableAnimation(boolean z) {
            this.mDisableAnimation = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDisableAod(boolean z) {
            this.mDisableAod = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDisableLaunchBoost(boolean z) {
            this.mDisableLaunchBoost = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDisableOptionalSensors(boolean z) {
            this.mDisableOptionalSensors = z;
            return this;
        }

        @java.lang.Deprecated
        public android.os.BatterySaverPolicyConfig.Builder setDisableSoundTrigger(boolean z) {
            if (z) {
                this.mSoundTriggerMode = 2;
            } else {
                this.mSoundTriggerMode = 0;
            }
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setSoundTriggerMode(int i) {
            this.mSoundTriggerMode = i;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setDisableVibration(boolean z) {
            this.mDisableVibration = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setEnableAdjustBrightness(boolean z) {
            this.mEnableAdjustBrightness = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setEnableDataSaver(boolean z) {
            this.mEnableDataSaver = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setEnableFirewall(boolean z) {
            this.mEnableFirewall = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setEnableNightMode(boolean z) {
            this.mEnableNightMode = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setEnableQuickDoze(boolean z) {
            this.mEnableQuickDoze = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setForceAllAppsStandby(boolean z) {
            this.mForceAllAppsStandby = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setForceBackgroundCheck(boolean z) {
            this.mForceBackgroundCheck = z;
            return this;
        }

        public android.os.BatterySaverPolicyConfig.Builder setLocationMode(int i) {
            this.mLocationMode = i;
            return this;
        }

        public android.os.BatterySaverPolicyConfig build() {
            return new android.os.BatterySaverPolicyConfig(this);
        }
    }
}
