package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssConfiguration {
    private static final java.lang.String CONFIG_A_GLONASS_POS_PROTOCOL_SELECT = "A_GLONASS_POS_PROTOCOL_SELECT";
    private static final java.lang.String CONFIG_C2K_HOST = "C2K_HOST";
    private static final java.lang.String CONFIG_C2K_PORT = "C2K_PORT";
    private static final java.lang.String CONFIG_ENABLE_ACTIVE_SIM_EMERGENCY_SUPL = "ENABLE_ACTIVE_SIM_EMERGENCY_SUPL";
    private static final java.lang.String CONFIG_ENABLE_NI_SUPL_MESSAGE_INJECTION = "ENABLE_NI_SUPL_MESSAGE_INJECTION";
    private static final java.lang.String CONFIG_ENABLE_PSDS_PERIODIC_DOWNLOAD = "ENABLE_PSDS_PERIODIC_DOWNLOAD";
    private static final java.lang.String CONFIG_ES_EXTENSION_SEC = "ES_EXTENSION_SEC";
    private static final java.lang.String CONFIG_GPS_LOCK = "GPS_LOCK";
    static final java.lang.String CONFIG_LONGTERM_PSDS_SERVER_1 = "LONGTERM_PSDS_SERVER_1";
    static final java.lang.String CONFIG_LONGTERM_PSDS_SERVER_2 = "LONGTERM_PSDS_SERVER_2";
    static final java.lang.String CONFIG_LONGTERM_PSDS_SERVER_3 = "LONGTERM_PSDS_SERVER_3";
    private static final java.lang.String CONFIG_LPP_PROFILE = "LPP_PROFILE";
    static final java.lang.String CONFIG_NFW_PROXY_APPS = "NFW_PROXY_APPS";
    static final java.lang.String CONFIG_NORMAL_PSDS_SERVER = "NORMAL_PSDS_SERVER";
    static final java.lang.String CONFIG_REALTIME_PSDS_SERVER = "REALTIME_PSDS_SERVER";
    private static final java.lang.String CONFIG_SUPL_ES = "SUPL_ES";
    private static final java.lang.String CONFIG_SUPL_HOST = "SUPL_HOST";
    private static final java.lang.String CONFIG_SUPL_MODE = "SUPL_MODE";
    private static final java.lang.String CONFIG_SUPL_PORT = "SUPL_PORT";
    private static final java.lang.String CONFIG_SUPL_VER = "SUPL_VER";
    private static final java.lang.String CONFIG_USE_EMERGENCY_PDN_FOR_EMERGENCY_SUPL = "USE_EMERGENCY_PDN_FOR_EMERGENCY_SUPL";
    private static final java.lang.String DEBUG_PROPERTIES_SYSTEM_FILE = "/etc/gps_debug.conf";
    private static final java.lang.String DEBUG_PROPERTIES_VENDOR_FILE = "/vendor/etc/gps_debug.conf";
    static final java.lang.String LPP_PROFILE = "persist.sys.gps.lpp";
    private static final int MAX_EMERGENCY_MODE_EXTENSION_SECONDS = 300;
    private final android.content.Context mContext;
    private int mEsExtensionSec = 0;
    private final java.util.Properties mProperties = new java.util.Properties();
    private static final java.lang.String TAG = "GnssConfiguration";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    interface SetCarrierProperty {
        boolean set(int i);
    }

    private static native com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion native_get_gnss_configuration_version();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_emergency_supl_pdn(int i);

    private static native boolean native_set_es_extension_sec(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_gnss_pos_protocol_select(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_gps_lock(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_lpp_profile(int i);

    private static native boolean native_set_satellite_blocklist(int[] iArr, int[] iArr2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_supl_es(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_supl_mode(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_set_supl_version(int i);

    static class HalInterfaceVersion {
        static final int AIDL_INTERFACE = 3;
        final int mMajor;
        final int mMinor;

        HalInterfaceVersion(int i, int i2) {
            this.mMajor = i;
            this.mMinor = i2;
        }
    }

    public GnssConfiguration(android.content.Context context) {
        this.mContext = context;
    }

    java.util.Properties getProperties() {
        return this.mProperties;
    }

    public int getEsExtensionSec() {
        return this.mEsExtensionSec;
    }

    java.lang.String getSuplHost() {
        return this.mProperties.getProperty(CONFIG_SUPL_HOST);
    }

    int getSuplPort(int i) {
        return getIntConfig(CONFIG_SUPL_PORT, i);
    }

    java.lang.String getC2KHost() {
        return this.mProperties.getProperty(CONFIG_C2K_HOST);
    }

    int getC2KPort(int i) {
        return getIntConfig(CONFIG_C2K_PORT, i);
    }

    int getSuplMode(int i) {
        return getIntConfig(CONFIG_SUPL_MODE, i);
    }

    public int getSuplEs(int i) {
        return getIntConfig(CONFIG_SUPL_ES, i);
    }

    java.lang.String getLppProfile() {
        return this.mProperties.getProperty(CONFIG_LPP_PROFILE);
    }

    java.util.List<java.lang.String> getProxyApps() {
        java.lang.String property = this.mProperties.getProperty(CONFIG_NFW_PROXY_APPS);
        if (android.text.TextUtils.isEmpty(property)) {
            return java.util.Collections.emptyList();
        }
        java.lang.String[] split = property.trim().split("\\s+");
        if (split.length == 0) {
            return java.util.Collections.emptyList();
        }
        return java.util.Arrays.asList(split);
    }

    boolean isPsdsPeriodicDownloadEnabled() {
        return getBooleanConfig(CONFIG_ENABLE_PSDS_PERIODIC_DOWNLOAD, false);
    }

    boolean isActiveSimEmergencySuplEnabled() {
        return getBooleanConfig(CONFIG_ENABLE_ACTIVE_SIM_EMERGENCY_SUPL, false);
    }

    boolean isNiSuplMessageInjectionEnabled() {
        return getBooleanConfig(CONFIG_ENABLE_NI_SUPL_MESSAGE_INJECTION, false);
    }

    boolean isLongTermPsdsServerConfigured() {
        return (this.mProperties.getProperty(CONFIG_LONGTERM_PSDS_SERVER_1) == null && this.mProperties.getProperty(CONFIG_LONGTERM_PSDS_SERVER_2) == null && this.mProperties.getProperty(CONFIG_LONGTERM_PSDS_SERVER_3) == null) ? false : true;
    }

    void setSatelliteBlocklist(int[] iArr, int[] iArr2) {
        native_set_satellite_blocklist(iArr, iArr2);
    }

    com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion getHalInterfaceVersion() {
        return native_get_gnss_configuration_version();
    }

    void reloadGpsProperties() {
        reloadGpsProperties(false, -1);
    }

    void reloadGpsProperties(boolean z, int i) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Reset GPS properties, previous size = " + this.mProperties.size() + ", inEmergency:" + z + ", activeSubId=" + i);
        }
        loadPropertiesFromCarrierConfig(z, i);
        if (android.location.flags.Flags.gnssConfigurationFromResource()) {
            loadPropertiesFromResource(this.mContext, this.mProperties);
        }
        if (isSimAbsent(this.mContext)) {
            java.lang.String str = android.os.SystemProperties.get(LPP_PROFILE);
            if (!android.text.TextUtils.isEmpty(str)) {
                this.mProperties.setProperty(CONFIG_LPP_PROFILE, str);
            }
        }
        loadPropertiesFromGpsDebugConfig(this.mProperties, DEBUG_PROPERTIES_VENDOR_FILE);
        loadPropertiesFromGpsDebugConfig(this.mProperties, DEBUG_PROPERTIES_SYSTEM_FILE);
        this.mEsExtensionSec = getRangeCheckedConfigEsExtensionSec();
        logConfigurations();
        com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion halInterfaceVersion = getHalInterfaceVersion();
        if (halInterfaceVersion == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Skipped configuration update because GNSS configuration in GPS HAL is not supported");
                return;
            }
            return;
        }
        if (isConfigEsExtensionSecSupported(halInterfaceVersion) && !native_set_es_extension_sec(this.mEsExtensionSec)) {
            android.util.Log.e(TAG, "Unable to set ES_EXTENSION_SEC: " + this.mEsExtensionSec);
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(CONFIG_SUPL_VER, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda0
            @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
            public final boolean set(int i2) {
                boolean native_set_supl_version;
                native_set_supl_version = com.android.server.location.gnss.GnssConfiguration.native_set_supl_version(i2);
                return native_set_supl_version;
            }
        });
        hashMap.put(CONFIG_SUPL_MODE, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda1
            @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
            public final boolean set(int i2) {
                boolean native_set_supl_mode;
                native_set_supl_mode = com.android.server.location.gnss.GnssConfiguration.native_set_supl_mode(i2);
                return native_set_supl_mode;
            }
        });
        if (isConfigSuplEsSupported(halInterfaceVersion)) {
            hashMap.put(CONFIG_SUPL_ES, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda2
                @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
                public final boolean set(int i2) {
                    boolean native_set_supl_es;
                    native_set_supl_es = com.android.server.location.gnss.GnssConfiguration.native_set_supl_es(i2);
                    return native_set_supl_es;
                }
            });
        }
        hashMap.put(CONFIG_LPP_PROFILE, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda3
            @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
            public final boolean set(int i2) {
                boolean native_set_lpp_profile;
                native_set_lpp_profile = com.android.server.location.gnss.GnssConfiguration.native_set_lpp_profile(i2);
                return native_set_lpp_profile;
            }
        });
        hashMap.put(CONFIG_A_GLONASS_POS_PROTOCOL_SELECT, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda4
            @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
            public final boolean set(int i2) {
                boolean native_set_gnss_pos_protocol_select;
                native_set_gnss_pos_protocol_select = com.android.server.location.gnss.GnssConfiguration.native_set_gnss_pos_protocol_select(i2);
                return native_set_gnss_pos_protocol_select;
            }
        });
        hashMap.put(CONFIG_USE_EMERGENCY_PDN_FOR_EMERGENCY_SUPL, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda5
            @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
            public final boolean set(int i2) {
                boolean native_set_emergency_supl_pdn;
                native_set_emergency_supl_pdn = com.android.server.location.gnss.GnssConfiguration.native_set_emergency_supl_pdn(i2);
                return native_set_emergency_supl_pdn;
            }
        });
        if (isConfigGpsLockSupported(halInterfaceVersion)) {
            hashMap.put(CONFIG_GPS_LOCK, new com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty() { // from class: com.android.server.location.gnss.GnssConfiguration$$ExternalSyntheticLambda6
                @Override // com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty
                public final boolean set(int i2) {
                    boolean native_set_gps_lock;
                    native_set_gps_lock = com.android.server.location.gnss.GnssConfiguration.native_set_gps_lock(i2);
                    return native_set_gps_lock;
                }
            });
        }
        for (java.util.Map.Entry entry : hashMap.entrySet()) {
            java.lang.String str2 = (java.lang.String) entry.getKey();
            java.lang.String property = this.mProperties.getProperty(str2);
            if (property != null) {
                try {
                    if (!((com.android.server.location.gnss.GnssConfiguration.SetCarrierProperty) entry.getValue()).set(java.lang.Integer.decode(property).intValue())) {
                        android.util.Log.e(TAG, "Unable to set " + str2);
                    }
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.e(TAG, "Unable to parse propertyName: " + property);
                }
            }
        }
    }

    private void logConfigurations() {
        com.android.internal.util.FrameworkStatsLog.write(132, getSuplHost(), getSuplPort(0), getC2KHost(), getC2KPort(0), getIntConfig(CONFIG_SUPL_VER, 0), getSuplMode(0), getSuplEs(0) == 1, getIntConfig(CONFIG_LPP_PROFILE, 0), getIntConfig(CONFIG_A_GLONASS_POS_PROTOCOL_SELECT, 0), getIntConfig(CONFIG_USE_EMERGENCY_PDN_FOR_EMERGENCY_SUPL, 0) == 1, getIntConfig(CONFIG_GPS_LOCK, 0), getEsExtensionSec(), this.mProperties.getProperty(CONFIG_NFW_PROXY_APPS));
    }

    void loadPropertiesFromCarrierConfig(boolean z, int i) {
        android.telephony.CarrierConfigManager carrierConfigManager = (android.telephony.CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        if (carrierConfigManager == null) {
            return;
        }
        int defaultDataSubscriptionId = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId();
        if (!z || i < 0) {
            i = defaultDataSubscriptionId;
        }
        android.os.PersistableBundle configForSubId = android.telephony.SubscriptionManager.isValidSubscriptionId(i) ? carrierConfigManager.getConfigForSubId(i) : carrierConfigManager.getConfig();
        if (configForSubId == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "SIM not ready, use default carrier config.");
            }
            configForSubId = android.telephony.CarrierConfigManager.getDefaultConfig();
        }
        for (java.lang.String str : configForSubId.keySet()) {
            if (str.startsWith("gps.")) {
                java.lang.String upperCase = str.substring("gps.".length()).toUpperCase(java.util.Locale.ROOT);
                java.lang.Object obj = configForSubId.get(str);
                if (DEBUG) {
                    android.util.Log.d(TAG, "Gps config: " + upperCase + " = " + obj);
                }
                if (obj instanceof java.lang.String) {
                    this.mProperties.setProperty(upperCase, (java.lang.String) obj);
                } else if (obj != null) {
                    this.mProperties.setProperty(upperCase, obj.toString());
                }
            }
        }
    }

    private void loadPropertiesFromGpsDebugConfig(java.util.Properties properties, java.lang.String str) {
        java.io.FileInputStream fileInputStream;
        try {
            java.io.FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new java.io.FileInputStream(new java.io.File(str));
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                properties.load(fileInputStream);
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            } catch (java.lang.Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                throw th;
            }
        } catch (java.io.IOException e) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Could not open GPS configuration file " + str);
            }
        }
    }

    private void loadPropertiesFromResource(android.content.Context context, java.util.Properties properties) {
        int i;
        for (java.lang.String str : context.getResources().getStringArray(android.R.array.config_forceSlowJpegModeList)) {
            if (DEBUG) {
                android.util.Log.d(TAG, "GnssParamsResource: " + str);
            }
            int indexOf = str.indexOf("=");
            if (indexOf > 0 && (i = indexOf + 1) < str.length()) {
                properties.setProperty(str.substring(0, indexOf).trim().toUpperCase(java.util.Locale.ROOT), str.substring(i));
            } else {
                android.util.Log.w(TAG, "malformed contents: " + str);
            }
        }
    }

    private int getRangeCheckedConfigEsExtensionSec() {
        int intConfig = getIntConfig(CONFIG_ES_EXTENSION_SEC, 0);
        if (intConfig > 300) {
            android.util.Log.w(TAG, "ES_EXTENSION_SEC: " + intConfig + " too high, reset to 300");
            return 300;
        }
        if (intConfig >= 0) {
            return intConfig;
        }
        android.util.Log.w(TAG, "ES_EXTENSION_SEC: " + intConfig + " is negative, reset to zero.");
        return 0;
    }

    private int getIntConfig(java.lang.String str, int i) {
        java.lang.String property = this.mProperties.getProperty(str);
        if (android.text.TextUtils.isEmpty(property)) {
            return i;
        }
        try {
            return java.lang.Integer.decode(property).intValue();
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "Unable to parse config parameter " + str + " value: " + property + ". Using default value: " + i);
            return i;
        }
    }

    private boolean getBooleanConfig(java.lang.String str, boolean z) {
        java.lang.String property = this.mProperties.getProperty(str);
        if (android.text.TextUtils.isEmpty(property)) {
            return z;
        }
        return java.lang.Boolean.parseBoolean(property);
    }

    private static boolean isConfigEsExtensionSecSupported(com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion halInterfaceVersion) {
        return halInterfaceVersion.mMajor >= 2;
    }

    private static boolean isConfigSuplEsSupported(com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion halInterfaceVersion) {
        return halInterfaceVersion.mMajor < 2;
    }

    private static boolean isConfigGpsLockSupported(com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion halInterfaceVersion) {
        return halInterfaceVersion.mMajor < 2;
    }

    private static boolean isSimAbsent(android.content.Context context) {
        return ((android.telephony.TelephonyManager) context.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE)).getSimState() == 1;
    }
}
