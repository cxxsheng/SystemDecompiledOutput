package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class ConfigurationInternal {
    public static final int DETECTION_MODE_GEO = 2;
    public static final int DETECTION_MODE_MANUAL = 1;
    public static final int DETECTION_MODE_TELEPHONY = 3;
    public static final int DETECTION_MODE_UNKNOWN = 0;
    private final boolean mAutoDetectionEnabledSetting;
    private final boolean mEnhancedMetricsCollectionEnabled;
    private final boolean mGeoDetectionEnabledSetting;
    private final boolean mGeoDetectionRunInBackgroundEnabled;
    private final boolean mGeoDetectionSupported;
    private final boolean mLocationEnabledSetting;
    private final boolean mTelephonyDetectionSupported;
    private final boolean mTelephonyFallbackSupported;
    private final boolean mUserConfigAllowed;
    private final int mUserId;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DetectionMode {
    }

    private ConfigurationInternal(com.android.server.timezonedetector.ConfigurationInternal.Builder builder) {
        this.mTelephonyDetectionSupported = builder.mTelephonyDetectionSupported;
        this.mGeoDetectionSupported = builder.mGeoDetectionSupported;
        this.mTelephonyFallbackSupported = builder.mTelephonyFallbackSupported;
        this.mGeoDetectionRunInBackgroundEnabled = builder.mGeoDetectionRunInBackgroundEnabled;
        this.mEnhancedMetricsCollectionEnabled = builder.mEnhancedMetricsCollectionEnabled;
        this.mAutoDetectionEnabledSetting = builder.mAutoDetectionEnabledSetting;
        java.lang.Integer num = builder.mUserId;
        java.util.Objects.requireNonNull(num, "userId must be set");
        this.mUserId = num.intValue();
        this.mUserConfigAllowed = builder.mUserConfigAllowed;
        this.mLocationEnabledSetting = builder.mLocationEnabledSetting;
        this.mGeoDetectionEnabledSetting = builder.mGeoDetectionEnabledSetting;
    }

    public boolean isAutoDetectionSupported() {
        return this.mTelephonyDetectionSupported || this.mGeoDetectionSupported;
    }

    public boolean isTelephonyDetectionSupported() {
        return this.mTelephonyDetectionSupported;
    }

    public boolean isGeoDetectionSupported() {
        return this.mGeoDetectionSupported;
    }

    public boolean isTelephonyFallbackSupported() {
        return this.mTelephonyFallbackSupported;
    }

    boolean getGeoDetectionRunInBackgroundEnabledSetting() {
        return this.mGeoDetectionRunInBackgroundEnabled;
    }

    public boolean isEnhancedMetricsCollectionEnabled() {
        return this.mEnhancedMetricsCollectionEnabled;
    }

    public boolean getAutoDetectionEnabledSetting() {
        return this.mAutoDetectionEnabledSetting;
    }

    public boolean getAutoDetectionEnabledBehavior() {
        return isAutoDetectionSupported() && getAutoDetectionEnabledSetting();
    }

    public int getUserId() {
        return this.mUserId;
    }

    @android.annotation.NonNull
    public android.os.UserHandle getUserHandle() {
        return android.os.UserHandle.of(this.mUserId);
    }

    public boolean isUserConfigAllowed() {
        return this.mUserConfigAllowed;
    }

    public boolean getLocationEnabledSetting() {
        return this.mLocationEnabledSetting;
    }

    public boolean getGeoDetectionEnabledSetting() {
        return this.mGeoDetectionEnabledSetting;
    }

    public int getDetectionMode() {
        if (!isAutoDetectionSupported() || !getAutoDetectionEnabledSetting()) {
            return 1;
        }
        if (getGeoDetectionEnabledBehavior()) {
            return 2;
        }
        if (isTelephonyDetectionSupported()) {
            return 3;
        }
        return 0;
    }

    private boolean getGeoDetectionEnabledBehavior() {
        if (isGeoDetectionSupported() && getLocationEnabledSetting()) {
            if (isTelephonyDetectionSupported()) {
                return getGeoDetectionEnabledSetting();
            }
            return true;
        }
        return false;
    }

    public boolean isGeoDetectionExecutionEnabled() {
        return getDetectionMode() == 2 || getGeoDetectionRunInBackgroundEnabledBehavior();
    }

    private boolean getGeoDetectionRunInBackgroundEnabledBehavior() {
        return isGeoDetectionSupported() && getLocationEnabledSetting() && getAutoDetectionEnabledSetting() && getGeoDetectionRunInBackgroundEnabledSetting();
    }

    @android.annotation.NonNull
    public android.app.time.TimeZoneCapabilities asCapabilities(boolean z) {
        int i;
        android.app.time.TimeZoneCapabilities.Builder builder = new android.app.time.TimeZoneCapabilities.Builder(android.os.UserHandle.of(this.mUserId));
        boolean z2 = isUserConfigAllowed() || z;
        int i2 = 20;
        int i3 = 10;
        if (!isAutoDetectionSupported()) {
            i = 10;
        } else if (!z2) {
            i = 20;
        } else {
            i = 40;
        }
        builder.setConfigureAutoDetectionEnabledCapability(i);
        builder.setUseLocationEnabled(this.mLocationEnabledSetting);
        boolean isGeoDetectionSupported = isGeoDetectionSupported();
        boolean isTelephonyDetectionSupported = isTelephonyDetectionSupported();
        if (isGeoDetectionSupported && isTelephonyDetectionSupported) {
            if (!this.mAutoDetectionEnabledSetting || !getLocationEnabledSetting()) {
                i3 = 30;
            } else {
                i3 = 40;
            }
        }
        builder.setConfigureGeoDetectionEnabledCapability(i3);
        if (z2) {
            if (getAutoDetectionEnabledBehavior()) {
                i2 = 30;
            } else {
                i2 = 40;
            }
        }
        builder.setSetManualTimeZoneCapability(i2);
        return builder.build();
    }

    public android.app.time.TimeZoneConfiguration asConfiguration() {
        return new android.app.time.TimeZoneConfiguration.Builder().setAutoDetectionEnabled(getAutoDetectionEnabledSetting()).setGeoDetectionEnabled(getGeoDetectionEnabledSetting()).build();
    }

    public com.android.server.timezonedetector.ConfigurationInternal merge(android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
        com.android.server.timezonedetector.ConfigurationInternal.Builder builder = new com.android.server.timezonedetector.ConfigurationInternal.Builder(this);
        if (timeZoneConfiguration.hasIsAutoDetectionEnabled()) {
            builder.setAutoDetectionEnabledSetting(timeZoneConfiguration.isAutoDetectionEnabled());
        }
        if (timeZoneConfiguration.hasIsGeoDetectionEnabled()) {
            builder.setGeoDetectionEnabledSetting(timeZoneConfiguration.isGeoDetectionEnabled());
        }
        return builder.build();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.timezonedetector.ConfigurationInternal.class != obj.getClass()) {
            return false;
        }
        com.android.server.timezonedetector.ConfigurationInternal configurationInternal = (com.android.server.timezonedetector.ConfigurationInternal) obj;
        if (this.mUserId == configurationInternal.mUserId && this.mUserConfigAllowed == configurationInternal.mUserConfigAllowed && this.mTelephonyDetectionSupported == configurationInternal.mTelephonyDetectionSupported && this.mGeoDetectionSupported == configurationInternal.mGeoDetectionSupported && this.mTelephonyFallbackSupported == configurationInternal.mTelephonyFallbackSupported && this.mGeoDetectionRunInBackgroundEnabled == configurationInternal.mGeoDetectionRunInBackgroundEnabled && this.mEnhancedMetricsCollectionEnabled == configurationInternal.mEnhancedMetricsCollectionEnabled && this.mAutoDetectionEnabledSetting == configurationInternal.mAutoDetectionEnabledSetting && this.mLocationEnabledSetting == configurationInternal.mLocationEnabledSetting && this.mGeoDetectionEnabledSetting == configurationInternal.mGeoDetectionEnabledSetting) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mUserId), java.lang.Boolean.valueOf(this.mUserConfigAllowed), java.lang.Boolean.valueOf(this.mTelephonyDetectionSupported), java.lang.Boolean.valueOf(this.mGeoDetectionSupported), java.lang.Boolean.valueOf(this.mTelephonyFallbackSupported), java.lang.Boolean.valueOf(this.mGeoDetectionRunInBackgroundEnabled), java.lang.Boolean.valueOf(this.mEnhancedMetricsCollectionEnabled), java.lang.Boolean.valueOf(this.mAutoDetectionEnabledSetting), java.lang.Boolean.valueOf(this.mLocationEnabledSetting), java.lang.Boolean.valueOf(this.mGeoDetectionEnabledSetting));
    }

    public java.lang.String toString() {
        return "ConfigurationInternal{mUserId=" + this.mUserId + ", mUserConfigAllowed=" + this.mUserConfigAllowed + ", mTelephonyDetectionSupported=" + this.mTelephonyDetectionSupported + ", mGeoDetectionSupported=" + this.mGeoDetectionSupported + ", mTelephonyFallbackSupported=" + this.mTelephonyFallbackSupported + ", mGeoDetectionRunInBackgroundEnabled=" + this.mGeoDetectionRunInBackgroundEnabled + ", mEnhancedMetricsCollectionEnabled=" + this.mEnhancedMetricsCollectionEnabled + ", mAutoDetectionEnabledSetting=" + this.mAutoDetectionEnabledSetting + ", mLocationEnabledSetting=" + this.mLocationEnabledSetting + ", mGeoDetectionEnabledSetting=" + this.mGeoDetectionEnabledSetting + '}';
    }

    public static class Builder {
        private boolean mAutoDetectionEnabledSetting;
        private boolean mEnhancedMetricsCollectionEnabled;
        private boolean mGeoDetectionEnabledSetting;
        private boolean mGeoDetectionRunInBackgroundEnabled;
        private boolean mGeoDetectionSupported;
        private boolean mLocationEnabledSetting;
        private boolean mTelephonyDetectionSupported;
        private boolean mTelephonyFallbackSupported;
        private boolean mUserConfigAllowed;
        private java.lang.Integer mUserId;

        public Builder() {
        }

        public Builder(com.android.server.timezonedetector.ConfigurationInternal configurationInternal) {
            this.mUserId = java.lang.Integer.valueOf(configurationInternal.mUserId);
            this.mUserConfigAllowed = configurationInternal.mUserConfigAllowed;
            this.mTelephonyDetectionSupported = configurationInternal.mTelephonyDetectionSupported;
            this.mTelephonyFallbackSupported = configurationInternal.mTelephonyFallbackSupported;
            this.mGeoDetectionSupported = configurationInternal.mGeoDetectionSupported;
            this.mGeoDetectionRunInBackgroundEnabled = configurationInternal.mGeoDetectionRunInBackgroundEnabled;
            this.mEnhancedMetricsCollectionEnabled = configurationInternal.mEnhancedMetricsCollectionEnabled;
            this.mAutoDetectionEnabledSetting = configurationInternal.mAutoDetectionEnabledSetting;
            this.mLocationEnabledSetting = configurationInternal.mLocationEnabledSetting;
            this.mGeoDetectionEnabledSetting = configurationInternal.mGeoDetectionEnabledSetting;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setUserId(int i) {
            this.mUserId = java.lang.Integer.valueOf(i);
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setUserConfigAllowed(boolean z) {
            this.mUserConfigAllowed = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setTelephonyDetectionFeatureSupported(boolean z) {
            this.mTelephonyDetectionSupported = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setGeoDetectionFeatureSupported(boolean z) {
            this.mGeoDetectionSupported = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setTelephonyFallbackSupported(boolean z) {
            this.mTelephonyFallbackSupported = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setGeoDetectionRunInBackgroundEnabled(boolean z) {
            this.mGeoDetectionRunInBackgroundEnabled = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setEnhancedMetricsCollectionEnabled(boolean z) {
            this.mEnhancedMetricsCollectionEnabled = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setAutoDetectionEnabledSetting(boolean z) {
            this.mAutoDetectionEnabledSetting = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setLocationEnabledSetting(boolean z) {
            this.mLocationEnabledSetting = z;
            return this;
        }

        public com.android.server.timezonedetector.ConfigurationInternal.Builder setGeoDetectionEnabledSetting(boolean z) {
            this.mGeoDetectionEnabledSetting = z;
            return this;
        }

        @android.annotation.NonNull
        public com.android.server.timezonedetector.ConfigurationInternal build() {
            return new com.android.server.timezonedetector.ConfigurationInternal(this);
        }
    }
}
