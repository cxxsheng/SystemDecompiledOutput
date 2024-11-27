package android.content;

/* loaded from: classes.dex */
public abstract class Context {
    public static final java.lang.String ACCESSIBILITY_SERVICE = "accessibility";
    public static final java.lang.String ACCOUNT_SERVICE = "account";
    public static final java.lang.String ACTIVITY_SERVICE = "activity";
    public static final java.lang.String ACTIVITY_TASK_SERVICE = "activity_task";
    public static final java.lang.String ADB_SERVICE = "adb";
    public static final java.lang.String ALARM_SERVICE = "alarm";

    @android.annotation.SystemApi
    public static final java.lang.String AMBIENT_CONTEXT_SERVICE = "ambient_context";
    public static final java.lang.String APPWIDGET_SERVICE = "appwidget";
    public static final java.lang.String APP_BINDING_SERVICE = "app_binding";

    @android.annotation.SystemApi
    public static final java.lang.String APP_HIBERNATION_SERVICE = "app_hibernation";

    @android.annotation.SystemApi
    public static final java.lang.String APP_INTEGRITY_SERVICE = "app_integrity";
    public static final java.lang.String APP_OPS_SERVICE = "appops";

    @android.annotation.SystemApi
    public static final java.lang.String APP_PREDICTION_SERVICE = "app_prediction";
    public static final java.lang.String APP_SEARCH_SERVICE = "app_search";
    public static final java.lang.String ATTENTION_SERVICE = "attention";
    public static final java.lang.String ATTESTATION_VERIFICATION_SERVICE = "attestation_verification";

    @android.annotation.SystemApi
    public static final java.lang.String AUDIO_DEVICE_VOLUME_SERVICE = "audio_device_volume";
    public static final java.lang.String AUDIO_SERVICE = "audio";
    public static final java.lang.String AUTH_SERVICE = "auth";
    public static final java.lang.String AUTOFILL_MANAGER_SERVICE = "autofill";
    public static final java.lang.String BACKGROUND_INSTALL_CONTROL_SERVICE = "background_install_control";

    @android.annotation.SystemApi
    public static final java.lang.String BACKUP_SERVICE = "backup";
    public static final java.lang.String BATTERY_SERVICE = "batterymanager";

    @android.annotation.SystemApi
    public static final java.lang.String BATTERY_STATS_SERVICE = "batterystats";
    public static final java.lang.String BINARY_TRANSPARENCY_SERVICE = "transparency";
    public static final int BIND_ABOVE_CLIENT = 8;
    public static final int BIND_ADJUST_WITH_ACTIVITY = 128;
    public static final int BIND_ALLOW_ACTIVITY_STARTS = 512;

    @android.annotation.SystemApi
    public static final int BIND_ALLOW_BACKGROUND_ACTIVITY_STARTS = 1048576;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int BIND_ALLOW_FOREGROUND_SERVICE_STARTS_FROM_BACKGROUND = 262144;
    public static final int BIND_ALLOW_INSTANT = 4194304;
    public static final int BIND_ALLOW_OOM_MANAGEMENT = 16;
    public static final int BIND_ALLOW_WHITELIST_MANAGEMENT = 16777216;
    public static final int BIND_ALMOST_PERCEPTIBLE = 65536;
    public static final int BIND_AUTO_CREATE = 1;
    public static final int BIND_BYPASS_POWER_NETWORK_RESTRICTIONS = 131072;
    public static final long BIND_BYPASS_USER_NETWORK_RESTRICTIONS = 4294967296L;
    public static final int BIND_DEBUG_UNBIND = 2;
    public static final int BIND_EXTERNAL_SERVICE = Integer.MIN_VALUE;
    public static final long BIND_EXTERNAL_SERVICE_LONG = 4611686018427387904L;
    public static final int BIND_FOREGROUND_SERVICE = 67108864;
    public static final int BIND_FOREGROUND_SERVICE_WHILE_AWAKE = 33554432;
    public static final int BIND_IMPORTANT = 64;
    public static final int BIND_IMPORTANT_BACKGROUND = 8388608;
    public static final int BIND_INCLUDE_CAPABILITIES = 4096;
    public static final long BIND_MATCH_QUARANTINED_COMPONENTS = 8589934592L;
    public static final int BIND_NOT_APP_COMPONENT_USAGE = 32768;
    public static final int BIND_NOT_FOREGROUND = 4;
    public static final int BIND_NOT_PERCEPTIBLE = 256;
    public static final int BIND_NOT_VISIBLE = 1073741824;
    public static final int BIND_PACKAGE_ISOLATED_PROCESS = 16384;
    public static final int BIND_REDUCTION_FLAGS = 1073742128;
    public static final int BIND_RESTRICT_ASSOCIATIONS = 2097152;
    public static final int BIND_SCHEDULE_LIKE_TOP_APP = 524288;
    public static final int BIND_SHARED_ISOLATED_PROCESS = 8192;
    public static final int BIND_SHOWING_UI = 536870912;
    public static final int BIND_TREAT_LIKE_ACTIVITY = 134217728;
    public static final int BIND_TREAT_LIKE_VISIBLE_FOREGROUND_SERVICE = 268435456;

    @java.lang.Deprecated
    public static final int BIND_VISIBLE = 268435456;
    public static final int BIND_WAIVE_PRIORITY = 32;
    public static final java.lang.String BIOMETRIC_SERVICE = "biometric";
    public static final java.lang.String BLOB_STORE_SERVICE = "blob_store";
    public static final java.lang.String BLUETOOTH_SERVICE = "bluetooth";
    public static final java.lang.String BUGREPORT_SERVICE = "bugreport";
    public static final java.lang.String CAMERA_SERVICE = "camera";
    public static final java.lang.String CAPTIONING_SERVICE = "captioning";
    public static final java.lang.String CARRIER_CONFIG_SERVICE = "carrier_config";
    public static final java.lang.String CLIPBOARD_SERVICE = "clipboard";

    @android.annotation.SystemApi
    public static final java.lang.String CLOUDSEARCH_SERVICE = "cloudsearch";
    public static final java.lang.String COLOR_DISPLAY_SERVICE = "color_display";
    public static final java.lang.String COMPANION_DEVICE_SERVICE = "companiondevice";
    public static final java.lang.String CONNECTIVITY_DIAGNOSTICS_SERVICE = "connectivity_diagnostics";
    public static final java.lang.String CONNECTIVITY_SERVICE = "connectivity";
    public static final java.lang.String CONSUMER_IR_SERVICE = "consumer_ir";
    public static final java.lang.String CONTACT_KEYS_SERVICE = "contact_keys";
    public static final java.lang.String CONTENT_CAPTURE_MANAGER_SERVICE = "content_capture";

    @android.annotation.SystemApi
    public static final java.lang.String CONTENT_SUGGESTIONS_SERVICE = "content_suggestions";

    @android.annotation.SystemApi
    public static final java.lang.String CONTEXTHUB_SERVICE = "contexthub";
    public static final int CONTEXT_CREDENTIAL_PROTECTED_STORAGE = 16;
    public static final int CONTEXT_DEVICE_PROTECTED_STORAGE = 8;
    public static final int CONTEXT_IGNORE_SECURITY = 2;
    public static final int CONTEXT_INCLUDE_CODE = 1;
    public static final int CONTEXT_REGISTER_PACKAGE = 1073741824;
    public static final int CONTEXT_RESTRICTED = 4;
    public static final java.lang.String COUNTRY_DETECTOR = "country_detector";
    public static final java.lang.String CREDENTIAL_SERVICE = "credential";
    public static final java.lang.String CROSS_PROFILE_APPS_SERVICE = "crossprofileapps";
    public static final java.lang.String DATA_LOADER_MANAGER_SERVICE = "dataloader_manager";
    public static final java.lang.String DEVICE_IDENTIFIERS_SERVICE = "device_identifiers";
    public static final java.lang.String DEVICE_IDLE_CONTROLLER = "deviceidle";
    public static final int DEVICE_ID_DEFAULT = 0;
    public static final int DEVICE_ID_INVALID = -1;
    public static final java.lang.String DEVICE_LOCK_SERVICE = "device_lock";
    public static final java.lang.String DEVICE_POLICY_SERVICE = "device_policy";
    public static final java.lang.String DEVICE_STATE_SERVICE = "device_state";
    public static final java.lang.String DISPLAY_HASH_SERVICE = "display_hash";
    public static final java.lang.String DISPLAY_SERVICE = "display";
    public static final java.lang.String DOMAIN_VERIFICATION_SERVICE = "domain_verification";
    public static final java.lang.String DOWNLOAD_SERVICE = "download";
    public static final java.lang.String DREAM_SERVICE = "dream";
    public static final java.lang.String DROPBOX_SERVICE = "dropbox";
    public static final java.lang.String DYNAMIC_SYSTEM_SERVICE = "dynamic_system";

    @android.annotation.SystemApi
    public static final java.lang.String ECM_ENHANCED_CONFIRMATION_SERVICE = "ecm_enhanced_confirmation";

    @android.annotation.SystemApi
    public static final java.lang.String ETHERNET_SERVICE = "ethernet";

    @android.annotation.SystemApi
    public static final java.lang.String EUICC_CARD_SERVICE = "euicc_card";
    public static final java.lang.String EUICC_SERVICE = "euicc";
    public static final java.lang.String FACE_SERVICE = "face";
    public static final java.lang.String FEATURE_FLAGS_SERVICE = "feature_flags";
    public static final java.lang.String FILE_INTEGRITY_SERVICE = "file_integrity";
    public static final java.lang.String FINGERPRINT_SERVICE = "fingerprint";

    @android.annotation.SystemApi
    public static final java.lang.String FONT_SERVICE = "font";
    public static final java.lang.String GAME_SERVICE = "game";
    public static final java.lang.String GATEKEEPER_SERVICE = "android.service.gatekeeper.IGateKeeperService";
    public static final java.lang.String GRAMMATICAL_INFLECTION_SERVICE = "grammatical_inflection";
    public static final java.lang.String HARDWARE_PROPERTIES_SERVICE = "hardware_properties";

    @android.annotation.SystemApi
    public static final java.lang.String HDMI_CONTROL_SERVICE = "hdmi_control";
    public static final java.lang.String HEALTHCONNECT_SERVICE = "healthconnect";
    public static final java.lang.String IDMAP_SERVICE = "idmap";
    public static final java.lang.String INCIDENT_COMPANION_SERVICE = "incidentcompanion";
    public static final java.lang.String INCIDENT_SERVICE = "incident";
    public static final java.lang.String INCREMENTAL_SERVICE = "incremental";
    public static final java.lang.String INPUT_METHOD_SERVICE = "input_method";
    public static final java.lang.String INPUT_SERVICE = "input";
    public static final java.lang.String IPSEC_SERVICE = "ipsec";
    public static final java.lang.String IRIS_SERVICE = "iris";
    public static final java.lang.String JOB_SCHEDULER_SERVICE = "jobscheduler";
    public static final java.lang.String KEYGUARD_SERVICE = "keyguard";
    public static final java.lang.String LAUNCHER_APPS_SERVICE = "launcherapps";
    public static final java.lang.String LAYOUT_INFLATER_SERVICE = "layout_inflater";
    public static final java.lang.String LEGACY_PERMISSION_SERVICE = "legacy_permission";
    public static final java.lang.String LIGHTS_SERVICE = "lights";
    public static final java.lang.String LOCALE_SERVICE = "locale";
    public static final java.lang.String LOCATION_SERVICE = "location";
    public static final java.lang.String LOWPAN_SERVICE = "lowpan";
    public static final java.lang.String MEDIA_COMMUNICATION_SERVICE = "media_communication";
    public static final java.lang.String MEDIA_METRICS_SERVICE = "media_metrics";
    public static final java.lang.String MEDIA_PROJECTION_SERVICE = "media_projection";
    public static final java.lang.String MEDIA_ROUTER_SERVICE = "media_router";
    public static final java.lang.String MEDIA_SESSION_SERVICE = "media_session";

    @android.annotation.SystemApi
    public static final java.lang.String MEDIA_TRANSCODING_SERVICE = "media_transcoding";
    public static final java.lang.String MIDI_SERVICE = "midi";
    public static final java.lang.String MMS_SERVICE = "mms";
    public static final int MODE_APPEND = 32768;
    public static final int MODE_ENABLE_WRITE_AHEAD_LOGGING = 8;

    @java.lang.Deprecated
    public static final int MODE_MULTI_PROCESS = 4;
    public static final int MODE_NO_LOCALIZED_COLLATORS = 16;
    public static final int MODE_PRIVATE = 0;

    @java.lang.Deprecated
    public static final int MODE_WORLD_READABLE = 1;

    @java.lang.Deprecated
    public static final int MODE_WORLD_WRITEABLE = 2;

    @android.annotation.SystemApi
    public static final java.lang.String MUSIC_RECOGNITION_SERVICE = "music_recognition";

    @android.annotation.SystemApi
    public static final java.lang.String NEARBY_SERVICE = "nearby";

    @android.annotation.SystemApi
    public static final java.lang.String NETD_SERVICE = "netd";
    public static final java.lang.String NETWORKMANAGEMENT_SERVICE = "network_management";
    public static final java.lang.String NETWORK_POLICY_SERVICE = "netpolicy";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String NETWORK_SCORE_SERVICE = "network_score";
    public static final java.lang.String NETWORK_STACK_SERVICE = "network_stack";
    public static final java.lang.String NETWORK_STATS_SERVICE = "netstats";
    public static final java.lang.String NETWORK_WATCHLIST_SERVICE = "network_watchlist";
    public static final java.lang.String NFC_SERVICE = "nfc";
    public static final java.lang.String NOTIFICATION_SERVICE = "notification";
    public static final java.lang.String NSD_SERVICE = "servicediscovery";

    @android.annotation.SystemApi
    public static final java.lang.String OEM_LOCK_SERVICE = "oem_lock";

    @android.annotation.SystemApi
    public static final java.lang.String ON_DEVICE_INTELLIGENCE_SERVICE = "on_device_intelligence";
    public static final java.lang.String OVERLAY_SERVICE = "overlay";
    public static final long OVERRIDABLE_COMPONENT_CALLBACKS = 193247900;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String PAC_PROXY_SERVICE = "pac_proxy";
    public static final java.lang.String PEOPLE_SERVICE = "people";
    public static final java.lang.String PERFORMANCE_HINT_SERVICE = "performance_hint";
    public static final java.lang.String PERMISSION_CHECKER_SERVICE = "permission_checker";

    @android.annotation.SystemApi
    public static final java.lang.String PERMISSION_CONTROLLER_SERVICE = "permission_controller";
    public static final java.lang.String PERMISSION_ENFORCER_SERVICE = "permission_enforcer";

    @android.annotation.SystemApi
    public static final java.lang.String PERMISSION_SERVICE = "permission";

    @android.annotation.SystemApi
    public static final java.lang.String PERSISTENT_DATA_BLOCK_SERVICE = "persistent_data_block";
    public static final java.lang.String PLATFORM_COMPAT_NATIVE_SERVICE = "platform_compat_native";
    public static final java.lang.String PLATFORM_COMPAT_SERVICE = "platform_compat";
    public static final java.lang.String POWER_EXEMPTION_SERVICE = "power_exemption";
    public static final java.lang.String POWER_SERVICE = "power";
    public static final java.lang.String POWER_STATS_SERVICE = "powerstats";

    @java.lang.Deprecated
    public static final java.lang.String POWER_WHITELIST_MANAGER = "power_whitelist";
    public static final java.lang.String PRINT_SERVICE = "print";
    public static final java.lang.String PROFILING_SERVICE = "profiling";
    public static final java.lang.String RADIO_SERVICE = "broadcastradio";

    @android.annotation.SystemApi
    public static final java.lang.String REBOOT_READINESS_SERVICE = "reboot_readiness";
    public static final int RECEIVER_EXPORTED = 2;

    @java.lang.Deprecated
    public static final int RECEIVER_EXPORTED_UNAUDITED = 2;
    public static final int RECEIVER_NOT_EXPORTED = 4;
    public static final int RECEIVER_VISIBLE_TO_INSTANT_APPS = 1;
    public static final java.lang.String RECOVERY_SERVICE = "recovery";
    public static final java.lang.String REMOTE_AUTH_SERVICE = "remote_auth";
    public static final java.lang.String REMOTE_PROVISIONING_SERVICE = "remote_provisioning";
    public static final java.lang.String RESOURCES_SERVICE = "resources";
    public static final java.lang.String RESOURCE_ECONOMY_SERVICE = "tare";
    public static final java.lang.String RESTRICTIONS_SERVICE = "restrictions";
    public static final java.lang.String ROLE_SERVICE = "role";

    @android.annotation.SystemApi
    public static final java.lang.String ROLLBACK_SERVICE = "rollback";
    public static final java.lang.String ROTATION_RESOLVER_SERVICE = "resolver";

    @android.annotation.SystemApi
    public static final java.lang.String SAFETY_CENTER_SERVICE = "safety_center";
    public static final java.lang.String SATELLITE_SERVICE = "satellite";
    public static final java.lang.String SEARCH_SERVICE = "search";

    @android.annotation.SystemApi
    public static final java.lang.String SEARCH_UI_SERVICE = "search_ui";

    @android.annotation.SystemApi
    public static final java.lang.String SECURE_ELEMENT_SERVICE = "secure_element";
    public static final java.lang.String SECURITY_STATE_SERVICE = "security_state";
    public static final java.lang.String SELECTION_TOOLBAR_SERVICE = "selection_toolbar";
    public static final java.lang.String SENSITIVE_CONTENT_PROTECTION_SERVICE = "sensitive_content_protection_service";
    public static final java.lang.String SENSOR_PRIVACY_SERVICE = "sensor_privacy";
    public static final java.lang.String SENSOR_SERVICE = "sensor";
    public static final java.lang.String SERIAL_SERVICE = "serial";

    @android.annotation.SystemApi
    public static final java.lang.String SHARED_CONNECTIVITY_SERVICE = "shared_connectivity";
    public static final java.lang.String SHORTCUT_SERVICE = "shortcut";
    public static final java.lang.String SIP_SERVICE = "sip";
    public static final java.lang.String SLICE_SERVICE = "slice";

    @android.annotation.SystemApi
    public static final java.lang.String SMARTSPACE_SERVICE = "smartspace";
    public static final java.lang.String SMS_SERVICE = "sms";
    public static final java.lang.String SOUND_TRIGGER_MIDDLEWARE_SERVICE = "soundtrigger_middleware";
    public static final java.lang.String SOUND_TRIGGER_SERVICE = "soundtrigger";
    public static final java.lang.String SPEECH_RECOGNITION_SERVICE = "speech_recognition";
    public static final java.lang.String STATS_BOOTSTRAP_ATOM_SERVICE = "statsbootstrap";
    public static final java.lang.String STATS_COMPANION_SERVICE = "statscompanion";

    @android.annotation.SystemApi
    public static final java.lang.String STATS_MANAGER = "stats";
    public static final java.lang.String STATS_MANAGER_SERVICE = "statsmanager";
    public static final java.lang.String STATUS_BAR_SERVICE = "statusbar";
    public static final java.lang.String STORAGE_SERVICE = "storage";
    public static final java.lang.String STORAGE_STATS_SERVICE = "storagestats";

    @android.annotation.SystemApi
    public static final java.lang.String SYSTEM_CONFIG_SERVICE = "system_config";
    public static final java.lang.String SYSTEM_HEALTH_SERVICE = "systemhealth";

    @android.annotation.SystemApi
    public static final java.lang.String SYSTEM_UPDATE_SERVICE = "system_update";
    public static final java.lang.String TELECOM_SERVICE = "telecom";
    public static final java.lang.String TELEPHONY_IMS_SERVICE = "telephony_ims";
    public static final java.lang.String TELEPHONY_RCS_MESSAGE_SERVICE = "ircsmessage";
    public static final java.lang.String TELEPHONY_REGISTRY_SERVICE = "telephony_registry";
    public static final java.lang.String TELEPHONY_SERVICE = "phone";
    public static final java.lang.String TELEPHONY_SUBSCRIPTION_SERVICE = "telephony_subscription_service";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String TEST_NETWORK_SERVICE = "test_network";

    @android.annotation.SystemApi
    public static final java.lang.String TETHERING_SERVICE = "tethering";
    public static final java.lang.String TEXT_CLASSIFICATION_SERVICE = "textclassification";
    public static final java.lang.String TEXT_SERVICES_MANAGER_SERVICE = "textservices";
    public static final java.lang.String TEXT_TO_SPEECH_MANAGER_SERVICE = "texttospeech";
    public static final java.lang.String THERMAL_SERVICE = "thermalservice";

    @android.annotation.SystemApi
    public static final java.lang.String THREAD_NETWORK_SERVICE = "thread_network";
    public static final java.lang.String TIME_DETECTOR_SERVICE = "time_detector";

    @android.annotation.SystemApi
    public static final java.lang.String TIME_MANAGER_SERVICE = "time_manager";
    public static final java.lang.String TIME_ZONE_DETECTOR_SERVICE = "time_zone_detector";

    @android.annotation.SystemApi
    public static final java.lang.String TRANSLATION_MANAGER_SERVICE = "translation";
    public static final java.lang.String TRUST_SERVICE = "trust";
    public static final java.lang.String TV_AD_SERVICE = "tv_ad";
    public static final java.lang.String TV_INPUT_SERVICE = "tv_input";
    public static final java.lang.String TV_INTERACTIVE_APP_SERVICE = "tv_interactive_app";
    public static final java.lang.String TV_TUNER_RESOURCE_MGR_SERVICE = "tv_tuner_resource_mgr";
    public static final java.lang.String UI_MODE_SERVICE = "uimode";

    @android.annotation.SystemApi
    public static final java.lang.String UI_TRANSLATION_SERVICE = "ui_translation";
    public static final java.lang.String UPDATE_LOCK_SERVICE = "updatelock";
    public static final java.lang.String URI_GRANTS_SERVICE = "uri_grants";
    public static final java.lang.String USAGE_STATS_SERVICE = "usagestats";
    public static final java.lang.String USB_SERVICE = "usb";
    public static final java.lang.String USER_SERVICE = "user";

    @android.annotation.SystemApi
    public static final java.lang.String UWB_SERVICE = "uwb";
    public static final java.lang.String VCN_MANAGEMENT_SERVICE = "vcn_management";
    public static final java.lang.String VIBRATOR_MANAGER_SERVICE = "vibrator_manager";

    @java.lang.Deprecated
    public static final java.lang.String VIBRATOR_SERVICE = "vibrator";

    @android.annotation.SystemApi
    public static final java.lang.String VIRTUALIZATION_SERVICE = "virtualization";
    public static final java.lang.String VIRTUAL_DEVICE_SERVICE = "virtualdevice";
    public static final java.lang.String VOICE_INTERACTION_MANAGER_SERVICE = "voiceinteraction";
    public static final java.lang.String VPN_MANAGEMENT_SERVICE = "vpn_management";

    @android.annotation.SystemApi
    public static final java.lang.String VR_SERVICE = "vrmanager";

    @android.annotation.SystemApi
    public static final java.lang.String WALLPAPER_EFFECTS_GENERATION_SERVICE = "wallpaper_effects_generation";
    public static final java.lang.String WALLPAPER_SERVICE = "wallpaper";

    @android.annotation.SystemApi
    public static final java.lang.String WEARABLE_SENSING_SERVICE = "wearable_sensing";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String WEBVIEW_UPDATE_SERVICE = "webviewupdate";
    public static final java.lang.String WIFI_AWARE_SERVICE = "wifiaware";

    @android.annotation.SystemApi
    public static final java.lang.String WIFI_NL80211_SERVICE = "wifinl80211";
    public static final java.lang.String WIFI_P2P_SERVICE = "wifip2p";
    public static final java.lang.String WIFI_RTT_RANGING_SERVICE = "wifirtt";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String WIFI_RTT_SERVICE = "rttmanager";

    @android.annotation.SystemApi
    public static final java.lang.String WIFI_SCANNING_SERVICE = "wifiscanner";
    public static final java.lang.String WIFI_SERVICE = "wifi";
    public static final java.lang.String WINDOW_SERVICE = "window";
    private static int sLastAutofillId = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BindServiceFlagsBits {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BindServiceFlagsLongBits {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CreatePackageOptions {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DatabaseMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FileMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PreferencesMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RegisterReceiverFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceName {
    }

    public abstract boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i);

    public abstract boolean canLoadUnsafeResources();

    public abstract int checkCallingOrSelfPermission(java.lang.String str);

    public abstract int checkCallingOrSelfUriPermission(android.net.Uri uri, int i);

    public abstract int checkCallingPermission(java.lang.String str);

    public abstract int checkCallingUriPermission(android.net.Uri uri, int i);

    public abstract int checkPermission(java.lang.String str, int i, int i2);

    public abstract int checkPermission(java.lang.String str, int i, int i2, android.os.IBinder iBinder);

    public abstract int checkSelfPermission(java.lang.String str);

    public abstract int checkUriPermission(android.net.Uri uri, int i, int i2, int i3);

    public abstract int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, android.os.IBinder iBinder);

    public abstract int checkUriPermission(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, int i3);

    @java.lang.Deprecated
    public abstract void clearWallpaper() throws java.io.IOException;

    public abstract android.content.Context createApplicationContext(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.Context createConfigurationContext(android.content.res.Configuration configuration);

    public abstract android.content.Context createContextForSplit(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    @android.annotation.SystemApi
    public abstract android.content.Context createCredentialProtectedStorageContext();

    public abstract android.content.Context createDeviceProtectedStorageContext();

    public abstract android.content.Context createDisplayContext(android.view.Display display);

    public abstract android.content.Context createPackageContext(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract java.lang.String[] databaseList();

    public abstract boolean deleteDatabase(java.lang.String str);

    public abstract boolean deleteFile(java.lang.String str);

    public abstract boolean deleteSharedPreferences(java.lang.String str);

    public abstract void enforceCallingOrSelfPermission(java.lang.String str, java.lang.String str2);

    public abstract void enforceCallingOrSelfUriPermission(android.net.Uri uri, int i, java.lang.String str);

    public abstract void enforceCallingPermission(java.lang.String str, java.lang.String str2);

    public abstract void enforceCallingUriPermission(android.net.Uri uri, int i, java.lang.String str);

    public abstract void enforcePermission(java.lang.String str, int i, int i2, java.lang.String str2);

    public abstract void enforceUriPermission(android.net.Uri uri, int i, int i2, int i3, java.lang.String str);

    public abstract void enforceUriPermission(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3);

    public abstract java.lang.String[] fileList();

    public abstract android.content.Context getApplicationContext();

    public abstract android.content.pm.ApplicationInfo getApplicationInfo();

    public abstract android.content.res.AssetManager getAssets();

    public abstract java.lang.String getBasePackageName();

    public abstract java.io.File getCacheDir();

    public abstract java.lang.ClassLoader getClassLoader();

    public abstract java.io.File getCodeCacheDir();

    public abstract android.content.ContentResolver getContentResolver();

    public abstract java.io.File getDataDir();

    public abstract java.io.File getDatabasePath(java.lang.String str);

    public abstract java.io.File getDir(java.lang.String str, int i);

    public abstract android.view.DisplayAdjustments getDisplayAdjustments(int i);

    public abstract int getDisplayId();

    public abstract java.io.File getExternalCacheDir();

    public abstract java.io.File[] getExternalCacheDirs();

    public abstract java.io.File getExternalFilesDir(java.lang.String str);

    public abstract java.io.File[] getExternalFilesDirs(java.lang.String str);

    @java.lang.Deprecated
    public abstract java.io.File[] getExternalMediaDirs();

    public abstract java.io.File getFileStreamPath(java.lang.String str);

    public abstract java.io.File getFilesDir();

    public abstract android.os.Looper getMainLooper();

    public abstract java.io.File getNoBackupFilesDir();

    public abstract java.io.File getObbDir();

    public abstract java.io.File[] getObbDirs();

    public abstract java.lang.String getPackageCodePath();

    public abstract android.content.pm.PackageManager getPackageManager();

    public abstract java.lang.String getPackageName();

    public abstract java.lang.String getPackageResourcePath();

    @android.annotation.SystemApi
    public abstract java.io.File getPreloadsFileCache();

    public abstract android.content.res.Resources getResources();

    public abstract android.content.SharedPreferences getSharedPreferences(java.io.File file, int i);

    public abstract android.content.SharedPreferences getSharedPreferences(java.lang.String str, int i);

    public abstract java.io.File getSharedPreferencesPath(java.lang.String str);

    public abstract java.lang.Object getSystemService(java.lang.String str);

    public abstract java.lang.String getSystemServiceName(java.lang.Class<?> cls);

    @android.view.ViewDebug.ExportedProperty(deepExport = true)
    public abstract android.content.res.Resources.Theme getTheme();

    @java.lang.Deprecated
    public abstract android.graphics.drawable.Drawable getWallpaper();

    @java.lang.Deprecated
    public abstract int getWallpaperDesiredMinimumHeight();

    @java.lang.Deprecated
    public abstract int getWallpaperDesiredMinimumWidth();

    public abstract void grantUriPermission(java.lang.String str, android.net.Uri uri, int i);

    @android.annotation.SystemApi
    public abstract boolean isCredentialProtectedStorage();

    public abstract boolean isDeviceProtectedStorage();

    public abstract boolean moveDatabaseFrom(android.content.Context context, java.lang.String str);

    public abstract boolean moveSharedPreferencesFrom(android.content.Context context, java.lang.String str);

    public abstract java.io.FileInputStream openFileInput(java.lang.String str) throws java.io.FileNotFoundException;

    public abstract java.io.FileOutputStream openFileOutput(java.lang.String str, int i) throws java.io.FileNotFoundException;

    public abstract android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory);

    public abstract android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, android.database.DatabaseErrorHandler databaseErrorHandler);

    @java.lang.Deprecated
    public abstract android.graphics.drawable.Drawable peekWallpaper();

    public abstract android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter);

    public abstract android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, int i);

    public abstract android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler);

    public abstract android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i);

    public abstract android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler);

    public abstract android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i);

    public abstract void reloadSharedPreferences();

    @java.lang.Deprecated
    public abstract void removeStickyBroadcast(android.content.Intent intent);

    @java.lang.Deprecated
    public abstract void removeStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

    public abstract void revokeUriPermission(android.net.Uri uri, int i);

    public abstract void revokeUriPermission(java.lang.String str, android.net.Uri uri, int i);

    public abstract void sendBroadcast(android.content.Intent intent);

    public abstract void sendBroadcast(android.content.Intent intent, java.lang.String str);

    public abstract void sendBroadcast(android.content.Intent intent, java.lang.String str, int i);

    public abstract void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

    public abstract void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str);

    public abstract void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i);

    @android.annotation.SystemApi
    public abstract void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, android.os.Bundle bundle);

    public abstract void sendBroadcastAsUserMultiplePermissions(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String[] strArr);

    public abstract void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str);

    public abstract void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle);

    public abstract void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle);

    public abstract void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle);

    public abstract void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i, android.os.Bundle bundle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle2);

    public abstract void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle);

    @java.lang.Deprecated
    public abstract void sendStickyBroadcast(android.content.Intent intent);

    @java.lang.Deprecated
    public abstract void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

    @java.lang.Deprecated
    public abstract void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, android.os.Bundle bundle);

    @java.lang.Deprecated
    public abstract void sendStickyOrderedBroadcast(android.content.Intent intent, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str, android.os.Bundle bundle);

    @java.lang.Deprecated
    public abstract void sendStickyOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str, android.os.Bundle bundle);

    public abstract void setTheme(int i);

    @java.lang.Deprecated
    public abstract void setWallpaper(android.graphics.Bitmap bitmap) throws java.io.IOException;

    @java.lang.Deprecated
    public abstract void setWallpaper(java.io.InputStream inputStream) throws java.io.IOException;

    public abstract void startActivities(android.content.Intent[] intentArr);

    public abstract void startActivities(android.content.Intent[] intentArr, android.os.Bundle bundle);

    public abstract void startActivity(android.content.Intent intent);

    public abstract void startActivity(android.content.Intent intent, android.os.Bundle bundle);

    public abstract android.content.ComponentName startForegroundService(android.content.Intent intent);

    public abstract android.content.ComponentName startForegroundServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

    public abstract boolean startInstrumentation(android.content.ComponentName componentName, java.lang.String str, android.os.Bundle bundle);

    public abstract void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3) throws android.content.IntentSender.SendIntentException;

    public abstract void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException;

    public abstract android.content.ComponentName startService(android.content.Intent intent);

    public abstract android.content.ComponentName startServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

    public abstract boolean stopService(android.content.Intent intent);

    public abstract boolean stopServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

    public abstract void unbindService(android.content.ServiceConnection serviceConnection);

    public abstract void unregisterReceiver(android.content.BroadcastReceiver broadcastReceiver);

    public abstract void updateDisplay(int i);

    public static final class BindServiceFlags {
        private final long mValue;

        private BindServiceFlags(long j) {
            this.mValue = j;
        }

        public long getValue() {
            return this.mValue;
        }

        public static android.content.Context.BindServiceFlags of(long j) {
            if ((java.lang.Integer.toUnsignedLong(Integer.MIN_VALUE) & j) != 0) {
                throw new java.lang.IllegalArgumentException("BIND_EXTERNAL_SERVICE is deprecated. Use BIND_EXTERNAL_SERVICE_LONG instead");
            }
            return new android.content.Context.BindServiceFlags(j);
        }
    }

    public java.util.concurrent.Executor getMainExecutor() {
        return new android.os.HandlerExecutor(new android.os.Handler(getMainLooper()));
    }

    public int getNextAutofillId() {
        if (sLastAutofillId == 1073741822) {
            sLastAutofillId = -1;
        }
        sLastAutofillId++;
        return sLastAutofillId;
    }

    public void registerComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        getApplicationContext().registerComponentCallbacks(componentCallbacks);
    }

    public void unregisterComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        getApplicationContext().unregisterComponentCallbacks(componentCallbacks);
    }

    public final java.lang.CharSequence getText(int i) {
        return getResources().getText(i);
    }

    public final java.lang.String getString(int i) {
        return getResources().getString(i);
    }

    public final java.lang.String getString(int i, java.lang.Object... objArr) {
        return getResources().getString(i, objArr);
    }

    public final int getColor(int i) {
        return getResources().getColor(i, getTheme());
    }

    public final android.graphics.drawable.Drawable getDrawable(int i) {
        return getResources().getDrawable(i, getTheme());
    }

    public final android.content.res.ColorStateList getColorStateList(int i) {
        return getResources().getColorStateList(i, getTheme());
    }

    public int getThemeResId() {
        return 0;
    }

    public final android.content.res.TypedArray obtainStyledAttributes(int[] iArr) {
        return getTheme().obtainStyledAttributes(iArr);
    }

    public final android.content.res.TypedArray obtainStyledAttributes(int i, int[] iArr) throws android.content.res.Resources.NotFoundException {
        return getTheme().obtainStyledAttributes(i, iArr);
    }

    public final android.content.res.TypedArray obtainStyledAttributes(android.util.AttributeSet attributeSet, int[] iArr) {
        return getTheme().obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    public final android.content.res.TypedArray obtainStyledAttributes(android.util.AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return getTheme().obtainStyledAttributes(attributeSet, iArr, i, i2);
    }

    public java.lang.String getOpPackageName() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public java.lang.String getAttributionTag() {
        return null;
    }

    public android.content.AttributionSource getAttributionSource() {
        return null;
    }

    @java.lang.Deprecated
    public java.lang.String getFeatureId() {
        return getAttributionTag();
    }

    public android.content.ContextParams getParams() {
        return null;
    }

    @java.lang.Deprecated
    public java.io.File getSharedPrefsFile(java.lang.String str) {
        return getSharedPreferencesPath(str);
    }

    public java.io.File getCrateDir(java.lang.String str) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public void startActivityAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public void startActivityAsUser(android.content.Intent intent, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void startActivityForResult(java.lang.String str, android.content.Intent intent, int i, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("This method is only implemented for Activity-based Contexts. Check canStartActivityForResult() before calling.");
    }

    public boolean canStartActivityForResult() {
        return false;
    }

    public int startActivitiesAsUser(android.content.Intent[] intentArr, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, java.lang.String[] strArr2) {
        sendBroadcastMultiplePermissions(intent, strArr, strArr2, null);
    }

    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3) {
        sendBroadcastMultiplePermissions(intent, strArr, strArr2, strArr3, null);
    }

    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, android.app.BroadcastOptions broadcastOptions) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, android.app.BroadcastOptions broadcastOptions) {
        sendBroadcastMultiplePermissions(intent, strArr, broadcastOptions == null ? null : broadcastOptions.toBundle());
    }

    public void sendBroadcastWithMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr) {
        sendBroadcastMultiplePermissions(intent, strArr);
    }

    public void sendBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle2) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, java.lang.String str2, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str3, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void sendOrderedBroadcast(android.content.Intent intent, int i, java.lang.String str, java.lang.String str2, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, java.lang.String str3, android.os.Bundle bundle, android.os.Bundle bundle2) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @java.lang.Deprecated
    public void sendStickyBroadcast(android.content.Intent intent, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindService(android.content.Intent intent, int i, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindService(android.content.Intent intent, android.content.Context.BindServiceFlags bindServiceFlags, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindIsolatedService(android.content.Intent intent, int i, java.lang.String str, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindIsolatedService(android.content.Intent intent, android.content.Context.BindServiceFlags bindServiceFlags, java.lang.String str, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.Handler handler, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.Handler handler, android.os.UserHandle userHandle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void updateServiceGroup(android.content.ServiceConnection serviceConnection, int i, int i2) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public final <T> T getSystemService(java.lang.Class<T> cls) {
        java.lang.String systemServiceName = getSystemServiceName(cls);
        if (systemServiceName != null) {
            return (T) getSystemService(systemServiceName);
        }
        return null;
    }

    public int checkContentUriPermissionFull(android.net.Uri uri, int i, int i2, int i3) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int[] checkUriPermissions(java.util.List<android.net.Uri> list, int i, int i2, int i3) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int[] checkCallingUriPermissions(java.util.List<android.net.Uri> list, int i) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int[] checkCallingOrSelfUriPermissions(java.util.List<android.net.Uri> list, int i) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void revokeSelfPermissionOnKill(java.lang.String str) {
        revokeSelfPermissionsOnKill(java.util.Collections.singletonList(str));
    }

    public void revokeSelfPermissionsOnKill(java.util.Collection<java.lang.String> collection) {
        throw new java.lang.AbstractMethodError("Must be overridden in implementing class");
    }

    @android.annotation.SystemApi
    public android.content.Context createPackageContextAsUser(java.lang.String str, int i, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        if (android.os.Build.IS_ENG) {
            throw new java.lang.IllegalStateException("createPackageContextAsUser not overridden!");
        }
        return this;
    }

    @android.annotation.SystemApi
    public android.content.Context createContextAsUser(android.os.UserHandle userHandle, int i) {
        if (android.os.Build.IS_ENG) {
            throw new java.lang.IllegalStateException("createContextAsUser not overridden!");
        }
        return this;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.content.Context createContextForSdkInSandbox(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.os.UserHandle getUser() {
        return android.os.Process.myUserHandle();
    }

    public int getUserId() {
        return android.os.UserHandle.myUserId();
    }

    public android.content.Context createDeviceContext(int i) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.content.Context createWindowContext(int i, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.content.Context createWindowContext(android.view.Display display, int i, android.os.Bundle bundle) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.content.Context createContext(android.content.ContextParams contextParams) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.content.Context createAttributionContext(java.lang.String str) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @java.lang.Deprecated
    public android.content.Context createFeatureContext(java.lang.String str) {
        return createContext(new android.content.ContextParams.Builder(getParams()).setAttributionTag(str).build());
    }

    public android.content.Context createTokenContext(android.os.IBinder iBinder, android.view.Display display) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.view.Display getDisplay() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.view.Display getDisplayNoVerify() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int getAssociatedDisplayId() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void updateDeviceId(int i) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public int getDeviceId() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void registerDeviceIdChangeListener(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void unregisterDeviceIdChangeListener(java.util.function.IntConsumer intConsumer) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public boolean isRestricted() {
        return false;
    }

    public android.os.IBinder getActivityToken() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.os.IBinder getWindowContextToken() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public static android.os.IBinder getToken(android.content.Context context) {
        return context.getActivityToken() != null ? context.getActivityToken() : context.getWindowContextToken();
    }

    public android.app.IServiceConnection getServiceDispatcher(android.content.ServiceConnection serviceConnection, android.os.Handler handler, long j) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.app.IApplicationThread getIApplicationThread() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.os.IBinder getProcessToken() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.os.Handler getMainThreadHandler() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public android.view.autofill.AutofillManager.AutofillClient getAutofillClient() {
        return null;
    }

    public void setAutofillClient(android.view.autofill.AutofillManager.AutofillClient autofillClient) {
    }

    public android.view.contentcapture.ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return null;
    }

    public final boolean isAutofillCompatibilityEnabled() {
        android.content.AutofillOptions autofillOptions = getAutofillOptions();
        return autofillOptions != null && autofillOptions.compatModeEnabled;
    }

    public android.content.AutofillOptions getAutofillOptions() {
        return null;
    }

    public void setAutofillOptions(android.content.AutofillOptions autofillOptions) {
    }

    public android.content.ContentCaptureOptions getContentCaptureOptions() {
        return null;
    }

    public void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) {
    }

    public void assertRuntimeOverlayThemable() {
        if (getResources() == android.content.res.Resources.getSystem()) {
            throw new java.lang.IllegalArgumentException("Non-UI context used to display UI; get a UI context from ActivityThread#getSystemUiContext()");
        }
    }

    public boolean isUiContext() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void destroy() {
    }

    public boolean isConfigurationContext() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void closeSystemDialogs() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }
}
