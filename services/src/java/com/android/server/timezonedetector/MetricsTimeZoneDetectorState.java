package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class MetricsTimeZoneDetectorState {
    public static final int DETECTION_MODE_GEO = 2;
    public static final int DETECTION_MODE_MANUAL = 1;
    public static final int DETECTION_MODE_TELEPHONY = 3;
    public static final int DETECTION_MODE_UNKNOWN = 0;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.ConfigurationInternal mConfigurationInternal;

    @android.annotation.Nullable
    private final java.lang.String mDeviceTimeZoneId;
    private final int mDeviceTimeZoneIdOrdinal;

    @android.annotation.Nullable
    private final com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion mLatestGeolocationSuggestion;

    @android.annotation.Nullable
    private final com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion mLatestManualSuggestion;

    @android.annotation.Nullable
    private final com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion mLatestTelephonySuggestion;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DetectionMode {
    }

    private MetricsTimeZoneDetectorState(@android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion, @android.annotation.Nullable com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion2, @android.annotation.Nullable com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion3) {
        java.util.Objects.requireNonNull(configurationInternal);
        this.mConfigurationInternal = configurationInternal;
        this.mDeviceTimeZoneIdOrdinal = i;
        this.mDeviceTimeZoneId = str;
        this.mLatestManualSuggestion = metricsTimeZoneSuggestion;
        this.mLatestTelephonySuggestion = metricsTimeZoneSuggestion2;
        this.mLatestGeolocationSuggestion = metricsTimeZoneSuggestion3;
    }

    public static com.android.server.timezonedetector.MetricsTimeZoneDetectorState create(@android.annotation.NonNull com.android.server.timezonedetector.OrdinalGenerator<java.lang.String> ordinalGenerator, @android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion, @android.annotation.Nullable android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion, @android.annotation.Nullable com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion;
        boolean isEnhancedMetricsCollectionEnabled = configurationInternal.isEnhancedMetricsCollectionEnabled();
        java.lang.String str2 = isEnhancedMetricsCollectionEnabled ? str : null;
        java.util.Objects.requireNonNull(str);
        int ordinal = ordinalGenerator.ordinal(str);
        com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createMetricsTimeZoneSuggestion = createMetricsTimeZoneSuggestion(ordinalGenerator, manualTimeZoneSuggestion, isEnhancedMetricsCollectionEnabled);
        com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createMetricsTimeZoneSuggestion2 = createMetricsTimeZoneSuggestion(ordinalGenerator, telephonyTimeZoneSuggestion, isEnhancedMetricsCollectionEnabled);
        if (locationAlgorithmEvent == null) {
            metricsTimeZoneSuggestion = null;
        } else {
            metricsTimeZoneSuggestion = createMetricsTimeZoneSuggestion(ordinalGenerator, locationAlgorithmEvent.getSuggestion(), isEnhancedMetricsCollectionEnabled);
        }
        return new com.android.server.timezonedetector.MetricsTimeZoneDetectorState(configurationInternal, ordinal, str2, createMetricsTimeZoneSuggestion, createMetricsTimeZoneSuggestion2, metricsTimeZoneSuggestion);
    }

    public boolean isTelephonyDetectionSupported() {
        return this.mConfigurationInternal.isTelephonyDetectionSupported();
    }

    public boolean isGeoDetectionSupported() {
        return this.mConfigurationInternal.isGeoDetectionSupported();
    }

    public boolean isTelephonyTimeZoneFallbackSupported() {
        return this.mConfigurationInternal.isTelephonyFallbackSupported();
    }

    public boolean getGeoDetectionRunInBackgroundEnabled() {
        return this.mConfigurationInternal.getGeoDetectionRunInBackgroundEnabledSetting();
    }

    public boolean isEnhancedMetricsCollectionEnabled() {
        return this.mConfigurationInternal.isEnhancedMetricsCollectionEnabled();
    }

    public boolean getUserLocationEnabledSetting() {
        return this.mConfigurationInternal.getLocationEnabledSetting();
    }

    public boolean getGeoDetectionEnabledSetting() {
        return this.mConfigurationInternal.getGeoDetectionEnabledSetting();
    }

    public boolean getAutoDetectionEnabledSetting() {
        return this.mConfigurationInternal.getAutoDetectionEnabledSetting();
    }

    public int getDetectionMode() {
        switch (this.mConfigurationInternal.getDetectionMode()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    public int getDeviceTimeZoneIdOrdinal() {
        return this.mDeviceTimeZoneIdOrdinal;
    }

    @android.annotation.Nullable
    public java.lang.String getDeviceTimeZoneId() {
        return this.mDeviceTimeZoneId;
    }

    @android.annotation.Nullable
    public com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion getLatestManualSuggestion() {
        return this.mLatestManualSuggestion;
    }

    @android.annotation.Nullable
    public com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion getLatestTelephonySuggestion() {
        return this.mLatestTelephonySuggestion;
    }

    @android.annotation.Nullable
    public com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion getLatestGeolocationSuggestion() {
        return this.mLatestGeolocationSuggestion;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.timezonedetector.MetricsTimeZoneDetectorState.class != obj.getClass()) {
            return false;
        }
        com.android.server.timezonedetector.MetricsTimeZoneDetectorState metricsTimeZoneDetectorState = (com.android.server.timezonedetector.MetricsTimeZoneDetectorState) obj;
        if (this.mDeviceTimeZoneIdOrdinal == metricsTimeZoneDetectorState.mDeviceTimeZoneIdOrdinal && java.util.Objects.equals(this.mDeviceTimeZoneId, metricsTimeZoneDetectorState.mDeviceTimeZoneId) && this.mConfigurationInternal.equals(metricsTimeZoneDetectorState.mConfigurationInternal) && java.util.Objects.equals(this.mLatestManualSuggestion, metricsTimeZoneDetectorState.mLatestManualSuggestion) && java.util.Objects.equals(this.mLatestTelephonySuggestion, metricsTimeZoneDetectorState.mLatestTelephonySuggestion) && java.util.Objects.equals(this.mLatestGeolocationSuggestion, metricsTimeZoneDetectorState.mLatestGeolocationSuggestion)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mConfigurationInternal, java.lang.Integer.valueOf(this.mDeviceTimeZoneIdOrdinal), this.mDeviceTimeZoneId, this.mLatestManualSuggestion, this.mLatestTelephonySuggestion, this.mLatestGeolocationSuggestion);
    }

    public java.lang.String toString() {
        return "MetricsTimeZoneDetectorState{mConfigurationInternal=" + this.mConfigurationInternal + ", mDeviceTimeZoneIdOrdinal=" + this.mDeviceTimeZoneIdOrdinal + ", mDeviceTimeZoneId=" + this.mDeviceTimeZoneId + ", mLatestManualSuggestion=" + this.mLatestManualSuggestion + ", mLatestTelephonySuggestion=" + this.mLatestTelephonySuggestion + ", mLatestGeolocationSuggestion=" + this.mLatestGeolocationSuggestion + '}';
    }

    @android.annotation.Nullable
    private static com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createMetricsTimeZoneSuggestion(@android.annotation.NonNull com.android.server.timezonedetector.OrdinalGenerator<java.lang.String> ordinalGenerator, @android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion, boolean z) {
        if (manualTimeZoneSuggestion == null) {
            return null;
        }
        java.lang.String zoneId = manualTimeZoneSuggestion.getZoneId();
        return com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion.createCertain(z ? new java.lang.String[]{zoneId} : null, new int[]{ordinalGenerator.ordinal(zoneId)});
    }

    @android.annotation.Nullable
    private static com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createMetricsTimeZoneSuggestion(@android.annotation.NonNull com.android.server.timezonedetector.OrdinalGenerator<java.lang.String> ordinalGenerator, @android.annotation.NonNull android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion, boolean z) {
        if (telephonyTimeZoneSuggestion == null) {
            return null;
        }
        java.lang.String zoneId = telephonyTimeZoneSuggestion.getZoneId();
        if (zoneId == null) {
            return com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion.createUncertain();
        }
        return com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion.createCertain(z ? new java.lang.String[]{zoneId} : null, new int[]{ordinalGenerator.ordinal(zoneId)});
    }

    @android.annotation.Nullable
    private static com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createMetricsTimeZoneSuggestion(@android.annotation.NonNull com.android.server.timezonedetector.OrdinalGenerator<java.lang.String> ordinalGenerator, @android.annotation.Nullable com.android.server.timezonedetector.GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion, boolean z) {
        if (geolocationTimeZoneSuggestion == null) {
            return null;
        }
        java.util.List<java.lang.String> zoneIds = geolocationTimeZoneSuggestion.getZoneIds();
        if (zoneIds == null) {
            return com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion.createUncertain();
        }
        return com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion.createCertain(z ? (java.lang.String[]) zoneIds.toArray(new java.lang.String[0]) : null, ordinalGenerator.ordinals(zoneIds));
    }

    public static final class MetricsTimeZoneSuggestion {

        @android.annotation.Nullable
        private final int[] mZoneIdOrdinals;

        @android.annotation.Nullable
        private final java.lang.String[] mZoneIds;

        private MetricsTimeZoneSuggestion(@android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable int[] iArr) {
            this.mZoneIds = strArr;
            this.mZoneIdOrdinals = iArr;
        }

        @android.annotation.NonNull
        static com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createUncertain() {
            return new com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion(null, null);
        }

        @android.annotation.NonNull
        static com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion createCertain(@android.annotation.Nullable java.lang.String[] strArr, @android.annotation.NonNull int[] iArr) {
            return new com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion(strArr, iArr);
        }

        public boolean isCertain() {
            return this.mZoneIdOrdinals != null;
        }

        @android.annotation.Nullable
        public int[] getZoneIdOrdinals() {
            return this.mZoneIdOrdinals;
        }

        @android.annotation.Nullable
        public java.lang.String[] getZoneIds() {
            return this.mZoneIds;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion.class != obj.getClass()) {
                return false;
            }
            com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion = (com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion) obj;
            if (java.util.Arrays.equals(this.mZoneIdOrdinals, metricsTimeZoneSuggestion.mZoneIdOrdinals) && java.util.Arrays.equals(this.mZoneIds, metricsTimeZoneSuggestion.mZoneIds)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (java.util.Arrays.hashCode(this.mZoneIds) * 31) + java.util.Arrays.hashCode(this.mZoneIdOrdinals);
        }

        public java.lang.String toString() {
            return "MetricsTimeZoneSuggestion{mZoneIdOrdinals=" + java.util.Arrays.toString(this.mZoneIdOrdinals) + ", mZoneIds=" + java.util.Arrays.toString(this.mZoneIds) + '}';
        }
    }
}
