package android.content.pm;

/* loaded from: classes.dex */
public abstract class PackageManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REQUEST_PERMISSIONS = "android.content.pm.action.REQUEST_PERMISSIONS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REQUEST_PERMISSIONS_FOR_OTHER = "android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER";
    public static final boolean APPLY_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = true;
    public static final boolean APP_ENUMERATION_ENABLED_BY_DEFAULT = true;

    @android.annotation.SystemApi
    public static final int APP_METADATA_SOURCE_APK = 1;

    @android.annotation.SystemApi
    public static final int APP_METADATA_SOURCE_INSTALLER = 2;

    @android.annotation.SystemApi
    public static final int APP_METADATA_SOURCE_SYSTEM_IMAGE = 3;

    @android.annotation.SystemApi
    public static final int APP_METADATA_SOURCE_UNKNOWN = 0;
    public static final int CERT_INPUT_RAW_X509 = 0;
    public static final int CERT_INPUT_SHA256 = 1;
    public static final int COMPONENT_ENABLED_STATE_DEFAULT = 0;
    public static final int COMPONENT_ENABLED_STATE_DISABLED = 2;
    public static final int COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED = 4;
    public static final int COMPONENT_ENABLED_STATE_DISABLED_USER = 3;
    public static final int COMPONENT_ENABLED_STATE_ENABLED = 1;

    @android.annotation.SystemApi
    public static final int DELETE_ALL_USERS = 2;
    public static final int DELETE_ARCHIVE = 16;
    public static final int DELETE_CHATTY = Integer.MIN_VALUE;
    public static final int DELETE_DONT_KILL_APP = 8;

    @android.annotation.SystemApi
    public static final int DELETE_FAILED_ABORTED = -5;
    public static final int DELETE_FAILED_APP_PINNED = -7;

    @android.annotation.SystemApi
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    public static final int DELETE_FAILED_FOR_CHILD_PROFILE = -8;

    @android.annotation.SystemApi
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;

    @android.annotation.SystemApi
    public static final int DELETE_FAILED_OWNER_BLOCKED = -4;
    public static final int DELETE_FAILED_USED_SHARED_LIBRARY = -6;
    public static final int DELETE_FAILED_USER_RESTRICTED = -3;

    @android.annotation.SystemApi
    public static final int DELETE_KEEP_DATA = 1;

    @android.annotation.SystemApi
    public static final int DELETE_SUCCEEDED = 1;
    public static final int DELETE_SYSTEM_APP = 4;
    public static final int DONT_KILL_APP = 1;
    public static final boolean ENABLE_SHARED_UID_MIGRATION = true;
    public static final java.lang.String EXTRA_FAILURE_EXISTING_PACKAGE = "android.content.pm.extra.FAILURE_EXISTING_PACKAGE";
    public static final java.lang.String EXTRA_FAILURE_EXISTING_PERMISSION = "android.content.pm.extra.FAILURE_EXISTING_PERMISSION";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_INTENT_FILTER_VERIFICATION_HOSTS = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_HOSTS";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_INTENT_FILTER_VERIFICATION_ID = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_ID";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_INTENT_FILTER_VERIFICATION_PACKAGE_NAME = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_PACKAGE_NAME";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_INTENT_FILTER_VERIFICATION_URI_SCHEME = "android.content.pm.extra.INTENT_FILTER_VERIFICATION_URI_SCHEME";
    public static final java.lang.String EXTRA_MOVE_ID = "android.content.pm.extra.MOVE_ID";
    public static final java.lang.String EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT = "android.content.pm.extra.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_REQUEST_PERMISSIONS_DEVICE_ID = "android.content.pm.extra.REQUEST_PERMISSIONS_DEVICE_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_REQUEST_PERMISSIONS_LEGACY_ACCESS_PERMISSION_NAMES = "android.content.pm.extra.REQUEST_PERMISSIONS_LEGACY_ACCESS_PERMISSION_NAMES";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_REQUEST_PERMISSIONS_NAMES = "android.content.pm.extra.REQUEST_PERMISSIONS_NAMES";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_REQUEST_PERMISSIONS_RESULTS = "android.content.pm.extra.REQUEST_PERMISSIONS_RESULTS";
    public static final java.lang.String EXTRA_USER_ACTION_REQUIRED = "android.content.pm.extra.USER_ACTION_REQUIRED";
    public static final java.lang.String EXTRA_VERIFICATION_ID = "android.content.pm.extra.VERIFICATION_ID";
    public static final java.lang.String EXTRA_VERIFICATION_INSTALLER_PACKAGE = "android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE";
    public static final java.lang.String EXTRA_VERIFICATION_INSTALLER_UID = "android.content.pm.extra.VERIFICATION_INSTALLER_UID";
    public static final java.lang.String EXTRA_VERIFICATION_INSTALL_FLAGS = "android.content.pm.extra.VERIFICATION_INSTALL_FLAGS";
    public static final java.lang.String EXTRA_VERIFICATION_LONG_VERSION_CODE = "android.content.pm.extra.VERIFICATION_LONG_VERSION_CODE";
    public static final java.lang.String EXTRA_VERIFICATION_PACKAGE_NAME = "android.content.pm.extra.VERIFICATION_PACKAGE_NAME";
    public static final java.lang.String EXTRA_VERIFICATION_RESULT = "android.content.pm.extra.VERIFICATION_RESULT";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String EXTRA_VERIFICATION_ROOT_HASH = "android.content.pm.extra.VERIFICATION_ROOT_HASH";
    public static final java.lang.String EXTRA_VERIFICATION_URI = "android.content.pm.extra.VERIFICATION_URI";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_VERIFICATION_VERSION_CODE = "android.content.pm.extra.VERIFICATION_VERSION_CODE";
    public static final java.lang.String FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS = "android.software.activities_on_secondary_displays";
    public static final java.lang.String FEATURE_ADOPTABLE_STORAGE = "android.software.adoptable_storage";
    public static final java.lang.String FEATURE_APP_COMPAT_OVERRIDES = "android.software.app_compat_overrides";
    public static final java.lang.String FEATURE_APP_ENUMERATION = "android.software.app_enumeration";
    public static final java.lang.String FEATURE_APP_WIDGETS = "android.software.app_widgets";
    public static final java.lang.String FEATURE_ASSIST_GESTURE = "android.hardware.sensor.assist";
    public static final java.lang.String FEATURE_AUDIO_LOW_LATENCY = "android.hardware.audio.low_latency";
    public static final java.lang.String FEATURE_AUDIO_OUTPUT = "android.hardware.audio.output";
    public static final java.lang.String FEATURE_AUDIO_PRO = "android.hardware.audio.pro";
    public static final java.lang.String FEATURE_AUDIO_SPATIAL_HEADTRACKING_LOW_LATENCY = "android.hardware.audio.spatial.headtracking.low_latency";
    public static final java.lang.String FEATURE_AUTOFILL = "android.software.autofill";
    public static final java.lang.String FEATURE_AUTOMOTIVE = "android.hardware.type.automotive";
    public static final java.lang.String FEATURE_BACKUP = "android.software.backup";
    public static final java.lang.String FEATURE_BLUETOOTH = "android.hardware.bluetooth";
    public static final java.lang.String FEATURE_BLUETOOTH_LE = "android.hardware.bluetooth_le";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_BROADCAST_RADIO = "android.hardware.broadcastradio";
    public static final java.lang.String FEATURE_CAMERA = "android.hardware.camera";
    public static final java.lang.String FEATURE_CAMERA_ANY = "android.hardware.camera.any";
    public static final java.lang.String FEATURE_CAMERA_AR = "android.hardware.camera.ar";
    public static final java.lang.String FEATURE_CAMERA_AUTOFOCUS = "android.hardware.camera.autofocus";
    public static final java.lang.String FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING = "android.hardware.camera.capability.manual_post_processing";
    public static final java.lang.String FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR = "android.hardware.camera.capability.manual_sensor";
    public static final java.lang.String FEATURE_CAMERA_CAPABILITY_RAW = "android.hardware.camera.capability.raw";
    public static final java.lang.String FEATURE_CAMERA_CONCURRENT = "android.hardware.camera.concurrent";
    public static final java.lang.String FEATURE_CAMERA_EXTERNAL = "android.hardware.camera.external";
    public static final java.lang.String FEATURE_CAMERA_FLASH = "android.hardware.camera.flash";
    public static final java.lang.String FEATURE_CAMERA_FRONT = "android.hardware.camera.front";
    public static final java.lang.String FEATURE_CAMERA_LEVEL_FULL = "android.hardware.camera.level.full";
    public static final java.lang.String FEATURE_CANT_SAVE_STATE = "android.software.cant_save_state";
    public static final java.lang.String FEATURE_CAR_SPLITSCREEN_MULTITASKING = "android.software.car.splitscreen_multitasking";
    public static final java.lang.String FEATURE_CAR_TEMPLATES_HOST = "android.software.car.templates_host";
    public static final java.lang.String FEATURE_COMMUNAL_MODE = "android.software.communal_mode";
    public static final java.lang.String FEATURE_COMPANION_DEVICE_SETUP = "android.software.companion_device_setup";

    @java.lang.Deprecated
    public static final java.lang.String FEATURE_CONNECTION_SERVICE = "android.software.connectionservice";
    public static final java.lang.String FEATURE_CONSUMER_IR = "android.hardware.consumerir";
    public static final java.lang.String FEATURE_CONTEXTUAL_SEARCH_HELPER = "android.software.contextualsearch";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_CONTEXT_HUB = "android.hardware.context_hub";
    public static final java.lang.String FEATURE_CONTROLS = "android.software.controls";
    public static final java.lang.String FEATURE_CREDENTIALS = "android.software.credentials";
    public static final java.lang.String FEATURE_CTS = "android.software.cts";
    public static final java.lang.String FEATURE_DEVICE_ADMIN = "android.software.device_admin";
    public static final java.lang.String FEATURE_DEVICE_ID_ATTESTATION = "android.software.device_id_attestation";
    public static final java.lang.String FEATURE_DEVICE_LOCK = "android.software.device_lock";
    public static final java.lang.String FEATURE_DEVICE_UNIQUE_ATTESTATION = "android.hardware.device_unique_attestation";
    public static final java.lang.String FEATURE_DREAM_OVERLAY = "android.software.dream_overlay";
    public static final java.lang.String FEATURE_EMBEDDED = "android.hardware.type.embedded";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_EROFS = "android.software.erofs";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_EROFS_LEGACY = "android.software.erofs_legacy";
    public static final java.lang.String FEATURE_ETHERNET = "android.hardware.ethernet";
    public static final java.lang.String FEATURE_EXPANDED_PICTURE_IN_PICTURE = "android.software.expanded_picture_in_picture";
    public static final java.lang.String FEATURE_FACE = "android.hardware.biometrics.face";
    public static final java.lang.String FEATURE_FAKETOUCH = "android.hardware.faketouch";
    public static final java.lang.String FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT = "android.hardware.faketouch.multitouch.distinct";
    public static final java.lang.String FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND = "android.hardware.faketouch.multitouch.jazzhand";
    public static final java.lang.String FEATURE_FELICA = "android.hardware.felica";
    public static final java.lang.String FEATURE_FILE_BASED_ENCRYPTION = "android.software.file_based_encryption";
    public static final java.lang.String FEATURE_FINGERPRINT = "android.hardware.fingerprint";
    public static final java.lang.String FEATURE_FREEFORM_WINDOW_MANAGEMENT = "android.software.freeform_window_management";
    public static final java.lang.String FEATURE_GAMEPAD = "android.hardware.gamepad";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_GAME_SERVICE = "android.software.game_service";
    public static final java.lang.String FEATURE_HARDWARE_KEYSTORE = "android.hardware.hardware_keystore";
    public static final java.lang.String FEATURE_HDMI_CEC = "android.hardware.hdmi.cec";
    public static final java.lang.String FEATURE_HIFI_SENSORS = "android.hardware.sensor.hifi_sensors";
    public static final java.lang.String FEATURE_HOME_SCREEN = "android.software.home_screen";
    public static final java.lang.String FEATURE_IDENTITY_CREDENTIAL_HARDWARE = "android.hardware.identity_credential";
    public static final java.lang.String FEATURE_IDENTITY_CREDENTIAL_HARDWARE_DIRECT_ACCESS = "android.hardware.identity_credential_direct_access";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_INCREMENTAL_DELIVERY = "android.software.incremental_delivery";
    public static final java.lang.String FEATURE_INPUT_METHODS = "android.software.input_methods";
    public static final java.lang.String FEATURE_IPSEC_TUNNELS = "android.software.ipsec_tunnels";
    public static final java.lang.String FEATURE_IPSEC_TUNNEL_MIGRATION = "android.software.ipsec_tunnel_migration";
    public static final java.lang.String FEATURE_IRIS = "android.hardware.biometrics.iris";
    public static final java.lang.String FEATURE_KEYSTORE_APP_ATTEST_KEY = "android.hardware.keystore.app_attest_key";
    public static final java.lang.String FEATURE_KEYSTORE_LIMITED_USE_KEY = "android.hardware.keystore.limited_use_key";
    public static final java.lang.String FEATURE_KEYSTORE_SINGLE_USE_KEY = "android.hardware.keystore.single_use_key";
    public static final java.lang.String FEATURE_LEANBACK = "android.software.leanback";
    public static final java.lang.String FEATURE_LEANBACK_ONLY = "android.software.leanback_only";
    public static final java.lang.String FEATURE_LIVE_TV = "android.software.live_tv";
    public static final java.lang.String FEATURE_LIVE_WALLPAPER = "android.software.live_wallpaper";
    public static final java.lang.String FEATURE_LOCATION = "android.hardware.location";
    public static final java.lang.String FEATURE_LOCATION_GPS = "android.hardware.location.gps";
    public static final java.lang.String FEATURE_LOCATION_NETWORK = "android.hardware.location.network";
    public static final java.lang.String FEATURE_LOWPAN = "android.hardware.lowpan";
    public static final java.lang.String FEATURE_MANAGED_PROFILES = "android.software.managed_users";
    public static final java.lang.String FEATURE_MANAGED_USERS = "android.software.managed_users";
    public static final java.lang.String FEATURE_MICROPHONE = "android.hardware.microphone";
    public static final java.lang.String FEATURE_MIDI = "android.software.midi";
    public static final java.lang.String FEATURE_NFC = "android.hardware.nfc";
    public static final java.lang.String FEATURE_NFC_ANY = "android.hardware.nfc.any";
    public static final java.lang.String FEATURE_NFC_BEAM = "android.sofware.nfc.beam";
    public static final java.lang.String FEATURE_NFC_CHARGING = "android.hardware.nfc.charging";

    @java.lang.Deprecated
    public static final java.lang.String FEATURE_NFC_HCE = "android.hardware.nfc.hce";
    public static final java.lang.String FEATURE_NFC_HOST_CARD_EMULATION = "android.hardware.nfc.hce";
    public static final java.lang.String FEATURE_NFC_HOST_CARD_EMULATION_NFCF = "android.hardware.nfc.hcef";
    public static final java.lang.String FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE = "android.hardware.nfc.ese";
    public static final java.lang.String FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC = "android.hardware.nfc.uicc";
    public static final java.lang.String FEATURE_OPENGLES_DEQP_LEVEL = "android.software.opengles.deqp.level";
    public static final java.lang.String FEATURE_OPENGLES_EXTENSION_PACK = "android.hardware.opengles.aep";
    public static final java.lang.String FEATURE_PC = "android.hardware.type.pc";
    public static final java.lang.String FEATURE_PICTURE_IN_PICTURE = "android.software.picture_in_picture";
    public static final java.lang.String FEATURE_PRINTING = "android.software.print";
    public static final java.lang.String FEATURE_RAM_LOW = "android.hardware.ram.low";
    public static final java.lang.String FEATURE_RAM_NORMAL = "android.hardware.ram.normal";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_REBOOT_ESCROW = "android.hardware.reboot_escrow";
    public static final java.lang.String FEATURE_ROTARY_ENCODER_LOW_RES = "android.hardware.rotaryencoder.lowres";
    public static final java.lang.String FEATURE_SCREEN_LANDSCAPE = "android.hardware.screen.landscape";
    public static final java.lang.String FEATURE_SCREEN_PORTRAIT = "android.hardware.screen.portrait";
    public static final java.lang.String FEATURE_SDK_SANDBOX_WORK_PROFILE_INSTALL = "android.software.sdksandbox.sdk_install_work_profile";
    public static final java.lang.String FEATURE_SECURELY_REMOVES_USERS = "android.software.securely_removes_users";
    public static final java.lang.String FEATURE_SECURE_LOCK_SCREEN = "android.software.secure_lock_screen";
    public static final java.lang.String FEATURE_SECURITY_MODEL_COMPATIBLE = "android.hardware.security.model.compatible";
    public static final java.lang.String FEATURE_SENSOR_ACCELEROMETER = "android.hardware.sensor.accelerometer";
    public static final java.lang.String FEATURE_SENSOR_ACCELEROMETER_LIMITED_AXES = "android.hardware.sensor.accelerometer_limited_axes";
    public static final java.lang.String FEATURE_SENSOR_ACCELEROMETER_LIMITED_AXES_UNCALIBRATED = "android.hardware.sensor.accelerometer_limited_axes_uncalibrated";
    public static final java.lang.String FEATURE_SENSOR_AMBIENT_TEMPERATURE = "android.hardware.sensor.ambient_temperature";
    public static final java.lang.String FEATURE_SENSOR_BAROMETER = "android.hardware.sensor.barometer";
    public static final java.lang.String FEATURE_SENSOR_COMPASS = "android.hardware.sensor.compass";
    public static final java.lang.String FEATURE_SENSOR_DYNAMIC_HEAD_TRACKER = "android.hardware.sensor.dynamic.head_tracker";
    public static final java.lang.String FEATURE_SENSOR_GYROSCOPE = "android.hardware.sensor.gyroscope";
    public static final java.lang.String FEATURE_SENSOR_GYROSCOPE_LIMITED_AXES = "android.hardware.sensor.gyroscope_limited_axes";
    public static final java.lang.String FEATURE_SENSOR_GYROSCOPE_LIMITED_AXES_UNCALIBRATED = "android.hardware.sensor.gyroscope_limited_axes_uncalibrated";
    public static final java.lang.String FEATURE_SENSOR_HEADING = "android.hardware.sensor.heading";
    public static final java.lang.String FEATURE_SENSOR_HEART_RATE = "android.hardware.sensor.heartrate";
    public static final java.lang.String FEATURE_SENSOR_HEART_RATE_ECG = "android.hardware.sensor.heartrate.ecg";
    public static final java.lang.String FEATURE_SENSOR_HINGE_ANGLE = "android.hardware.sensor.hinge_angle";
    public static final java.lang.String FEATURE_SENSOR_LIGHT = "android.hardware.sensor.light";
    public static final java.lang.String FEATURE_SENSOR_PROXIMITY = "android.hardware.sensor.proximity";
    public static final java.lang.String FEATURE_SENSOR_RELATIVE_HUMIDITY = "android.hardware.sensor.relative_humidity";
    public static final java.lang.String FEATURE_SENSOR_STEP_COUNTER = "android.hardware.sensor.stepcounter";
    public static final java.lang.String FEATURE_SENSOR_STEP_DETECTOR = "android.hardware.sensor.stepdetector";
    public static final java.lang.String FEATURE_SE_OMAPI_ESE = "android.hardware.se.omapi.ese";
    public static final java.lang.String FEATURE_SE_OMAPI_SD = "android.hardware.se.omapi.sd";
    public static final java.lang.String FEATURE_SE_OMAPI_UICC = "android.hardware.se.omapi.uicc";
    public static final java.lang.String FEATURE_SIP = "android.software.sip";
    public static final java.lang.String FEATURE_SIP_VOIP = "android.software.sip.voip";
    public static final java.lang.String FEATURE_SLICES_DISABLED = "android.software.slices_disabled";
    public static final java.lang.String FEATURE_STRONGBOX_KEYSTORE = "android.hardware.strongbox_keystore";
    public static final java.lang.String FEATURE_TELECOM = "android.software.telecom";
    public static final java.lang.String FEATURE_TELEPHONY = "android.hardware.telephony";
    public static final java.lang.String FEATURE_TELEPHONY_CALLING = "android.hardware.telephony.calling";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_TELEPHONY_CARRIERLOCK = "android.hardware.telephony.carrierlock";
    public static final java.lang.String FEATURE_TELEPHONY_CDMA = "android.hardware.telephony.cdma";
    public static final java.lang.String FEATURE_TELEPHONY_DATA = "android.hardware.telephony.data";
    public static final java.lang.String FEATURE_TELEPHONY_EUICC = "android.hardware.telephony.euicc";
    public static final java.lang.String FEATURE_TELEPHONY_EUICC_MEP = "android.hardware.telephony.euicc.mep";
    public static final java.lang.String FEATURE_TELEPHONY_GSM = "android.hardware.telephony.gsm";
    public static final java.lang.String FEATURE_TELEPHONY_IMS = "android.hardware.telephony.ims";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_TELEPHONY_IMS_SINGLE_REGISTRATION = "android.hardware.telephony.ims.singlereg";
    public static final java.lang.String FEATURE_TELEPHONY_MBMS = "android.hardware.telephony.mbms";
    public static final java.lang.String FEATURE_TELEPHONY_MESSAGING = "android.hardware.telephony.messaging";
    public static final java.lang.String FEATURE_TELEPHONY_RADIO_ACCESS = "android.hardware.telephony.radio.access";
    public static final java.lang.String FEATURE_TELEPHONY_SATELLITE = "android.hardware.telephony.satellite";
    public static final java.lang.String FEATURE_TELEPHONY_SUBSCRIPTION = "android.hardware.telephony.subscription";

    @java.lang.Deprecated
    public static final java.lang.String FEATURE_TELEVISION = "android.hardware.type.television";
    public static final java.lang.String FEATURE_THREAD_NETWORK = "android.hardware.thread_network";
    public static final java.lang.String FEATURE_TOUCHSCREEN = "android.hardware.touchscreen";
    public static final java.lang.String FEATURE_TOUCHSCREEN_MULTITOUCH = "android.hardware.touchscreen.multitouch";
    public static final java.lang.String FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT = "android.hardware.touchscreen.multitouch.distinct";
    public static final java.lang.String FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND = "android.hardware.touchscreen.multitouch.jazzhand";
    public static final java.lang.String FEATURE_TUNER = "android.hardware.tv.tuner";
    public static final java.lang.String FEATURE_USB_ACCESSORY = "android.hardware.usb.accessory";
    public static final java.lang.String FEATURE_USB_HOST = "android.hardware.usb.host";
    public static final java.lang.String FEATURE_UWB = "android.hardware.uwb";
    public static final java.lang.String FEATURE_VERIFIED_BOOT = "android.software.verified_boot";

    @android.annotation.SystemApi
    public static final java.lang.String FEATURE_VIRTUALIZATION_FRAMEWORK = "android.software.virtualization_framework";
    public static final java.lang.String FEATURE_VOICE_RECOGNIZERS = "android.software.voice_recognizers";
    public static final java.lang.String FEATURE_VR_HEADTRACKING = "android.hardware.vr.headtracking";

    @java.lang.Deprecated
    public static final java.lang.String FEATURE_VR_MODE = "android.software.vr.mode";
    public static final java.lang.String FEATURE_VR_MODE_HIGH_PERFORMANCE = "android.hardware.vr.high_performance";
    public static final java.lang.String FEATURE_VULKAN_DEQP_LEVEL = "android.software.vulkan.deqp.level";
    public static final java.lang.String FEATURE_VULKAN_HARDWARE_COMPUTE = "android.hardware.vulkan.compute";
    public static final java.lang.String FEATURE_VULKAN_HARDWARE_LEVEL = "android.hardware.vulkan.level";
    public static final java.lang.String FEATURE_VULKAN_HARDWARE_VERSION = "android.hardware.vulkan.version";
    public static final java.lang.String FEATURE_WALLET_LOCATION_BASED_SUGGESTIONS = "android.software.wallet_location_based_suggestions";
    public static final java.lang.String FEATURE_WATCH = "android.hardware.type.watch";
    public static final java.lang.String FEATURE_WEBVIEW = "android.software.webview";
    public static final java.lang.String FEATURE_WIFI = "android.hardware.wifi";
    public static final java.lang.String FEATURE_WIFI_AWARE = "android.hardware.wifi.aware";
    public static final java.lang.String FEATURE_WIFI_DIRECT = "android.hardware.wifi.direct";
    public static final java.lang.String FEATURE_WIFI_PASSPOINT = "android.hardware.wifi.passpoint";
    public static final java.lang.String FEATURE_WIFI_RTT = "android.hardware.wifi.rtt";
    public static final java.lang.String FEATURE_WINDOW_MAGNIFICATION = "android.software.window_magnification";
    public static final long FILTER_APPLICATION_QUERY = 135549675;

    @android.annotation.SystemApi
    public static final int FLAGS_PERMISSION_RESERVED_PERMISSION_CONTROLLER = -268435456;
    public static final int FLAGS_PERMISSION_RESTRICTION_ANY_EXEMPT = 14336;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_APPLY_RESTRICTION = 16384;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_AUTO_REVOKED = 131072;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_GRANTED_BY_DEFAULT = 32;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_GRANTED_BY_ROLE = 32768;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_ONE_TIME = 65536;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_POLICY_FIXED = 4;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_INSTALLER_EXEMPT = 2048;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_SYSTEM_EXEMPT = 4096;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_RESTRICTION_UPGRADE_EXEMPT = 8192;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_REVIEW_REQUIRED = 64;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_REVOKED_COMPAT = 8;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int FLAG_PERMISSION_REVOKE_ON_UPGRADE = 8;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_REVOKE_WHEN_REQUESTED = 128;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_SELECTED_LOCATION_ACCURACY = 524288;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_SYSTEM_FIXED = 16;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_USER_FIXED = 2;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_USER_SENSITIVE_WHEN_DENIED = 512;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_USER_SENSITIVE_WHEN_GRANTED = 256;

    @android.annotation.SystemApi
    public static final int FLAG_PERMISSION_USER_SET = 1;
    public static final int FLAG_PERMISSION_WHITELIST_INSTALLER = 2;
    public static final int FLAG_PERMISSION_WHITELIST_SYSTEM = 1;
    public static final int FLAG_PERMISSION_WHITELIST_UPGRADE = 4;

    @android.annotation.SystemApi
    public static final int FLAG_SUSPEND_QUARANTINED = 1;
    public static final int GET_ACTIVITIES = 1;

    @java.lang.Deprecated
    public static final int GET_ATTRIBUTIONS = Integer.MIN_VALUE;
    public static final long GET_ATTRIBUTIONS_LONG = 2147483648L;
    public static final int GET_CONFIGURATIONS = 16384;

    @java.lang.Deprecated
    public static final int GET_DISABLED_COMPONENTS = 512;

    @java.lang.Deprecated
    public static final int GET_DISABLED_UNTIL_USED_COMPONENTS = 32768;
    public static final int GET_GIDS = 256;
    public static final int GET_INSTRUMENTATION = 16;

    @java.lang.Deprecated
    public static final int GET_INTENT_FILTERS = 32;
    public static final int GET_META_DATA = 128;
    public static final int GET_PERMISSIONS = 4096;
    public static final int GET_PROVIDERS = 8;
    public static final int GET_RECEIVERS = 2;
    public static final int GET_RESOLVED_FILTER = 64;
    public static final int GET_SERVICES = 4;
    public static final int GET_SHARED_LIBRARY_FILES = 1024;

    @java.lang.Deprecated
    public static final int GET_SIGNATURES = 64;
    public static final int GET_SIGNING_CERTIFICATES = 134217728;

    @java.lang.Deprecated
    public static final int GET_UNINSTALLED_PACKAGES = 8192;
    public static final int GET_URI_PERMISSION_PATTERNS = 2048;
    public static final int INSTALL_ACTIVATION_FAILED = -128;
    public static final int INSTALL_ALLOCATE_AGGRESSIVE = 32768;
    public static final int INSTALL_ALLOW_DOWNGRADE = 1048576;
    public static final int INSTALL_ALLOW_TEST = 4;
    public static final int INSTALL_ALL_USERS = 64;
    public static final int INSTALL_ALL_WHITELIST_RESTRICTED_PERMISSIONS = 4194304;
    public static final int INSTALL_APEX = 131072;
    public static final int INSTALL_ARCHIVED = 134217728;
    public static final int INSTALL_BYPASS_LOW_TARGET_SDK_BLOCK = 16777216;
    public static final int INSTALL_DEVELOPMENT_FORCE_NON_STAGED_APEX_UPDATE = 1;
    public static final int INSTALL_DISABLE_ALLOWED_APEX_UPDATE_CHECK = 8388608;
    public static final int INSTALL_DISABLE_VERIFICATION = 524288;
    public static final int INSTALL_DONT_KILL_APP = 4096;
    public static final int INSTALL_ENABLE_ROLLBACK = 262144;
    public static final int INSTALL_FAILED_ABORTED = -115;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int INSTALL_FAILED_BAD_DEX_METADATA = -117;
    public static final int INSTALL_FAILED_BAD_PERMISSION_GROUP = -127;
    public static final int INSTALL_FAILED_BAD_SIGNATURE = -118;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    public static final int INSTALL_FAILED_DEPRECATED_SDK_VERSION = -29;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_DEXOPT = -11;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION = -112;
    public static final int INSTALL_FAILED_DUPLICATE_PERMISSION_GROUP = -126;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_INVALID_APK = -2;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_INVALID_URI = -3;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    public static final int INSTALL_FAILED_MISSING_SPLIT = -28;
    public static final int INSTALL_FAILED_MULTIPACKAGE_INCONSISTENCY = -120;
    public static final int INSTALL_FAILED_MULTI_ARCH_NOT_MATCH_ALL_NATIVE_ABIS = -131;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_NEWER_SDK = -14;
    public static final int INSTALL_FAILED_NO_MATCHING_ABIS = -113;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_OLDER_SDK = -12;
    public static final int INSTALL_FAILED_OTHER_STAGED_SESSION_IN_PROGRESS = -119;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_PERMISSION_MODEL_DOWNGRADE = -26;
    public static final int INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE = -129;
    public static final int INSTALL_FAILED_PROCESS_NOT_DEFINED = -122;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_SANDBOX_VERSION_DOWNGRADE = -27;
    public static final int INSTALL_FAILED_SESSION_INVALID = -116;
    public static final int INSTALL_FAILED_SHARED_LIBRARY_BAD_CERTIFICATE_DIGEST = -130;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_TEST_ONLY = -15;
    public static final int INSTALL_FAILED_UID_CHANGED = -24;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    public static final int INSTALL_FAILED_USER_RESTRICTED = -111;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;

    @android.annotation.SystemApi
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    public static final int INSTALL_FAILED_VERSION_DOWNGRADE = -25;
    public static final int INSTALL_FAILED_WRONG_INSTALLED_VERSION = -121;
    public static final int INSTALL_FORCE_PERMISSION_PROMPT = 1024;
    public static final int INSTALL_FORCE_VOLUME_UUID = 512;
    public static final int INSTALL_FROM_ADB = 32;
    public static final int INSTALL_FROM_MANAGED_USER_OR_PROFILE = 67108864;
    public static final int INSTALL_FULL_APP = 16384;
    public static final int INSTALL_GRANT_ALL_REQUESTED_PERMISSIONS = 256;
    public static final int INSTALL_IGNORE_DEXOPT_PROFILE = 268435456;
    public static final int INSTALL_INSTANT_APP = 2048;
    public static final int INSTALL_INTERNAL = 16;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    public static final int INSTALL_PARSE_FAILED_RESOURCES_ARSC_COMPRESSED = -124;
    public static final int INSTALL_PARSE_FAILED_SKIPPED = -125;

    @android.annotation.SystemApi
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    public static final int INSTALL_REASON_DEVICE_RESTORE = 2;
    public static final int INSTALL_REASON_DEVICE_SETUP = 3;
    public static final int INSTALL_REASON_POLICY = 1;
    public static final int INSTALL_REASON_ROLLBACK = 5;
    public static final int INSTALL_REASON_UNKNOWN = 0;
    public static final int INSTALL_REASON_USER = 4;
    public static final int INSTALL_REPLACE_EXISTING = 2;
    public static final int INSTALL_REQUEST_DOWNGRADE = 128;
    public static final int INSTALL_REQUEST_UPDATE_OWNERSHIP = 33554432;
    public static final int INSTALL_SCENARIO_BULK = 2;
    public static final int INSTALL_SCENARIO_BULK_SECONDARY = 3;
    public static final int INSTALL_SCENARIO_DEFAULT = 0;
    public static final int INSTALL_SCENARIO_FAST = 1;
    public static final int INSTALL_STAGED = 2097152;

    @android.annotation.SystemApi
    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_UNARCHIVE = 1073741824;
    public static final int INSTALL_UNARCHIVE_DRAFT = 536870912;
    public static final int INSTALL_UNKNOWN = 0;
    public static final int INSTALL_VIRTUAL_PRELOAD = 65536;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS = 2;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS_ASK = 4;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ASK = 1;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_NEVER = 3;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_UNDEFINED = 0;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_VERIFICATION_FAILURE = -1;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int INTENT_FILTER_VERIFICATION_SUCCESS = 1;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int MASK_PERMISSION_FLAGS = 255;
    public static final int MASK_PERMISSION_FLAGS_ALL = 261119;
    public static final int MATCH_ALL = 131072;

    @android.annotation.SystemApi
    public static final int MATCH_ANY_USER = 4194304;
    public static final int MATCH_APEX = 1073741824;
    public static final long MATCH_ARCHIVED_PACKAGES = 4294967296L;

    @android.annotation.SystemApi
    public static final int MATCH_CLONE_PROFILE = 536870912;

    @android.annotation.SystemApi
    public static final long MATCH_CLONE_PROFILE_LONG = 17179869184L;

    @java.lang.Deprecated
    public static final int MATCH_DEBUG_TRIAGED_MISSING = 268435456;
    public static final int MATCH_DEFAULT_ONLY = 65536;
    public static final int MATCH_DIRECT_BOOT_AUTO = 268435456;
    public static final int MATCH_DIRECT_BOOT_AWARE = 524288;
    public static final int MATCH_DIRECT_BOOT_UNAWARE = 262144;
    public static final int MATCH_DISABLED_COMPONENTS = 512;
    public static final int MATCH_DISABLED_UNTIL_USED_COMPONENTS = 32768;
    public static final int MATCH_EXPLICITLY_VISIBLE_ONLY = 33554432;

    @android.annotation.SystemApi
    public static final int MATCH_FACTORY_ONLY = 2097152;

    @android.annotation.SystemApi
    public static final int MATCH_HIDDEN_UNTIL_INSTALLED_COMPONENTS = 536870912;

    @android.annotation.SystemApi
    public static final int MATCH_INSTANT = 8388608;
    public static final int MATCH_KNOWN_PACKAGES = 4202496;
    public static final long MATCH_QUARANTINED_COMPONENTS = 8589934592L;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int MATCH_STATIC_SHARED_AND_SDK_LIBRARIES = 67108864;
    public static final int MATCH_SYSTEM_ONLY = 1048576;
    public static final int MATCH_UNINSTALLED_PACKAGES = 8192;
    public static final int MATCH_VISIBLE_TO_INSTANT_APP_ONLY = 16777216;
    public static final long MAXIMUM_VERIFICATION_TIMEOUT = 3600000;

    @android.annotation.SystemApi
    public static final int MODULE_APEX_NAME = 1;

    @java.lang.Deprecated
    public static final int MOVE_EXTERNAL_MEDIA = 2;
    public static final int MOVE_FAILED_3RD_PARTY_NOT_ALLOWED_ON_INTERNAL = -9;
    public static final int MOVE_FAILED_DEVICE_ADMIN = -8;
    public static final int MOVE_FAILED_DOESNT_EXIST = -2;
    public static final int MOVE_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int MOVE_FAILED_INTERNAL_ERROR = -6;
    public static final int MOVE_FAILED_INVALID_LOCATION = -5;
    public static final int MOVE_FAILED_LOCKED_USER = -10;
    public static final int MOVE_FAILED_OPERATION_PENDING = -7;
    public static final int MOVE_FAILED_SYSTEM_PACKAGE = -3;

    @java.lang.Deprecated
    public static final int MOVE_INTERNAL = 1;
    public static final int MOVE_SUCCEEDED = -100;
    public static final int NOTIFY_PACKAGE_USE_ACTIVITY = 0;
    public static final int NOTIFY_PACKAGE_USE_BACKUP = 5;
    public static final int NOTIFY_PACKAGE_USE_BROADCAST_RECEIVER = 3;
    public static final int NOTIFY_PACKAGE_USE_CONTENT_PROVIDER = 4;
    public static final int NOTIFY_PACKAGE_USE_CROSS_PACKAGE = 6;
    public static final int NOTIFY_PACKAGE_USE_FOREGROUND_SERVICE = 2;
    public static final int NOTIFY_PACKAGE_USE_INSTRUMENTATION = 7;
    public static final int NOTIFY_PACKAGE_USE_REASONS_COUNT = 8;
    public static final int NOTIFY_PACKAGE_USE_SERVICE = 1;
    public static final int NO_NATIVE_LIBRARIES = -114;
    public static final int ONLY_IF_NO_MATCH_FOUND = 4;
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_GRANTED = 0;
    public static final java.lang.String PROPERTY_ALLOW_ADB_BACKUP = "android.backup.ALLOW_ADB_BACKUP";
    public static final java.lang.String PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT = "android.camera.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT";
    public static final java.lang.String PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST = "android.app.PROPERTY_LEGACY_UPDATE_OWNERSHIP_DENYLIST";
    public static final java.lang.String PROPERTY_MEDIA_CAPABILITIES = "android.media.PROPERTY_MEDIA_CAPABILITIES";
    public static final java.lang.String PROPERTY_NO_APP_DATA_STORAGE = "android.internal.PROPERTY_NO_APP_DATA_STORAGE";
    public static final java.lang.String PROPERTY_SELF_CERTIFIED_NETWORK_CAPABILITIES = "android.net.PROPERTY_SELF_CERTIFIED_NETWORK_CAPABILITIES";
    public static final java.lang.String PROPERTY_SPECIAL_USE_FGS_SUBTYPE = "android.app.PROPERTY_SPECIAL_USE_FGS_SUBTYPE";

    @android.annotation.SystemApi
    public static final int RESTRICTION_HIDE_FROM_SUGGESTIONS = 1;

    @android.annotation.SystemApi
    public static final int RESTRICTION_HIDE_NOTIFICATIONS = 2;

    @android.annotation.SystemApi
    public static final int RESTRICTION_NONE = 0;

    @android.annotation.SystemApi
    public static final int ROLLBACK_DATA_POLICY_RESTORE = 0;

    @android.annotation.SystemApi
    public static final int ROLLBACK_DATA_POLICY_RETAIN = 2;

    @android.annotation.SystemApi
    public static final int ROLLBACK_DATA_POLICY_WIPE = 1;

    @android.annotation.SystemApi
    public static final int ROLLBACK_USER_IMPACT_HIGH = 1;

    @android.annotation.SystemApi
    public static final int ROLLBACK_USER_IMPACT_LOW = 0;

    @android.annotation.SystemApi
    public static final int ROLLBACK_USER_IMPACT_ONLY_MANUAL = 2;
    public static final int SIGNATURE_FIRST_NOT_SIGNED = -1;
    public static final int SIGNATURE_MATCH = 0;
    public static final int SIGNATURE_NEITHER_SIGNED = 1;
    public static final int SIGNATURE_NO_MATCH = -3;
    public static final int SIGNATURE_SECOND_NOT_SIGNED = -2;
    public static final int SIGNATURE_UNKNOWN_PACKAGE = -4;
    public static final int SKIP_CURRENT_PROFILE = 2;
    public static final int SYNCHRONOUS = 2;

    @android.annotation.SystemApi
    public static final int SYSTEM_APP_STATE_HIDDEN_UNTIL_INSTALLED_HIDDEN = 0;

    @android.annotation.SystemApi
    public static final int SYSTEM_APP_STATE_HIDDEN_UNTIL_INSTALLED_VISIBLE = 1;

    @android.annotation.SystemApi
    public static final int SYSTEM_APP_STATE_INSTALLED = 2;

    @android.annotation.SystemApi
    public static final int SYSTEM_APP_STATE_UNINSTALLED = 3;
    public static final java.lang.String SYSTEM_SHARED_LIBRARY_SERVICES = "android.ext.services";
    public static final java.lang.String SYSTEM_SHARED_LIBRARY_SHARED = "android.ext.shared";
    private static final java.lang.String TAG = "PackageManager";
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_APPLICATION = 5;
    public static final int TYPE_PROVIDER = 4;
    public static final int TYPE_RECEIVER = 2;
    public static final int TYPE_SERVICE = 3;
    public static final int TYPE_UNKNOWN = 0;
    public static final int UNINSTALL_REASON_UNKNOWN = 0;
    public static final int UNINSTALL_REASON_USER_TYPE = 1;
    public static final int USER_MIN_ASPECT_RATIO_16_9 = 4;
    public static final int USER_MIN_ASPECT_RATIO_3_2 = 5;
    public static final int USER_MIN_ASPECT_RATIO_4_3 = 3;
    public static final int USER_MIN_ASPECT_RATIO_APP_DEFAULT = 7;
    public static final int USER_MIN_ASPECT_RATIO_DISPLAY_SIZE = 2;
    public static final int USER_MIN_ASPECT_RATIO_FULLSCREEN = 6;
    public static final int USER_MIN_ASPECT_RATIO_SPLIT_SCREEN = 1;
    public static final int USER_MIN_ASPECT_RATIO_UNSET = 0;
    public static final int VERIFICATION_ALLOW = 1;
    public static final int VERIFICATION_ALLOW_WITHOUT_SUFFICIENT = 2;
    public static final int VERIFICATION_REJECT = -1;
    public static final int VERSION_CODE_HIGHEST = -1;
    private static final android.app.PropertyInvalidatedCache<android.content.pm.PackageManager.ApplicationInfoQuery, android.content.pm.ApplicationInfo> sApplicationInfoCache;
    private static final android.app.PropertyInvalidatedCache.AutoCorker sCacheAutoCorker;
    private static final android.app.PropertyInvalidatedCache<android.content.pm.PackageManager.PackageInfoQuery, android.content.pm.PackageInfo> sPackageInfoCache;
    public static final java.lang.String APP_DETAILS_ACTIVITY_CLASS_NAME = android.app.AppDetailsActivity.class.getName();
    public static final java.util.List<java.security.cert.Certificate> TRUST_ALL = java.util.Collections.singletonList(null);
    public static final java.util.List<java.security.cert.Certificate> TRUST_NONE = java.util.Collections.singletonList(null);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppMetadataSource {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApplicationInfoFlagsBits {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CertificateInputType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ComponentInfoFlagsBits {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ComponentType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeleteFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DevelopmentInstallFlags {
    }

    @android.annotation.SystemApi
    public static abstract class DexModuleRegisterCallback {
        public abstract void onDexModuleRegistered(java.lang.String str, boolean z, java.lang.String str2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DistractionRestriction {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnabledFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnabledState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstallFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstallReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstallScenario {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstalledModulesFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InstrumentationInfoFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModuleInfoFlags {
    }

    public @interface NotifyReason {
    }

    @java.lang.FunctionalInterface
    public interface OnChecksumsReadyListener {
        void onChecksumsReady(java.util.List<android.content.pm.ApkChecksum> list);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PackageInfoFlagsBits {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionGroupInfoFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionInfoFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionWhitelistFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PropertyLocation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResolveInfoFlagsBits {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RollbackDataPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RollbackImpactLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignatureResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SuspendedFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemAppState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UninstallReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserMinAspectRatio {
    }

    public abstract void addCrossProfileIntentFilter(android.content.IntentFilter intentFilter, int i, int i2, int i3);

    @android.annotation.SystemApi
    public abstract void addOnPermissionsChangeListener(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener);

    @java.lang.Deprecated
    public abstract void addPackageToPreferred(java.lang.String str);

    public abstract boolean addPermission(android.content.pm.PermissionInfo permissionInfo);

    public abstract boolean addPermissionAsync(android.content.pm.PermissionInfo permissionInfo);

    @java.lang.Deprecated
    public abstract void addPreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName);

    @android.annotation.SystemApi
    public abstract boolean arePermissionsIndividuallyControlled();

    public abstract boolean canRequestPackageInstalls();

    public abstract java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr);

    public abstract int checkPermission(java.lang.String str, java.lang.String str2);

    public abstract int checkSignatures(int i, int i2);

    public abstract int checkSignatures(java.lang.String str, java.lang.String str2);

    public abstract void clearApplicationUserData(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver);

    public abstract void clearCrossProfileIntentFilters(int i);

    public abstract void clearInstantAppCookie();

    @java.lang.Deprecated
    public abstract void clearPackagePreferredActivities(java.lang.String str);

    public abstract java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr);

    public abstract void deleteApplicationCacheFiles(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver);

    public abstract void deleteApplicationCacheFilesAsUser(java.lang.String str, int i, android.content.pm.IPackageDataObserver iPackageDataObserver);

    public abstract void deletePackage(java.lang.String str, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i);

    public abstract void deletePackageAsUser(java.lang.String str, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i, int i2);

    public abstract void extendVerificationTimeout(int i, int i2, long j);

    public abstract void flushPackageRestrictionsAsUser(int i);

    public abstract void freeStorage(java.lang.String str, long j, android.content.IntentSender intentSender);

    public abstract void freeStorageAndNotify(java.lang.String str, long j, android.content.pm.IPackageDataObserver iPackageDataObserver);

    public abstract android.graphics.drawable.Drawable getActivityBanner(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.graphics.drawable.Drawable getActivityBanner(android.content.Intent intent) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.graphics.drawable.Drawable getActivityIcon(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.graphics.drawable.Drawable getActivityIcon(android.content.Intent intent) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.graphics.drawable.Drawable getActivityLogo(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.graphics.drawable.Drawable getActivityLogo(android.content.Intent intent) throws android.content.pm.PackageManager.NameNotFoundException;

    @android.annotation.SystemApi
    public abstract java.util.List<android.content.IntentFilter> getAllIntentFilters(java.lang.String str);

    public abstract java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i);

    public abstract android.graphics.drawable.Drawable getApplicationBanner(android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.graphics.drawable.Drawable getApplicationBanner(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract int getApplicationEnabledSetting(java.lang.String str);

    public abstract boolean getApplicationHiddenSettingAsUser(java.lang.String str, android.os.UserHandle userHandle);

    public abstract android.graphics.drawable.Drawable getApplicationIcon(android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.graphics.drawable.Drawable getApplicationIcon(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.ApplicationInfo getApplicationInfoAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract java.lang.CharSequence getApplicationLabel(android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.graphics.drawable.Drawable getApplicationLogo(android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.graphics.drawable.Drawable getApplicationLogo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.Intent getCarLaunchIntentForPackage(java.lang.String str);

    public abstract android.content.pm.ChangedPackages getChangedPackages(int i);

    public abstract int getComponentEnabledSetting(android.content.ComponentName componentName);

    public abstract android.graphics.drawable.Drawable getDefaultActivityIcon();

    @android.annotation.SystemApi
    public abstract java.lang.String getDefaultBrowserPackageNameAsUser(int i);

    public abstract android.graphics.drawable.Drawable getDrawable(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.content.ComponentName getHomeActivities(java.util.List<android.content.pm.ResolveInfo> list);

    public abstract int getInstallReason(java.lang.String str, android.os.UserHandle userHandle);

    public abstract java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(int i);

    public abstract java.util.List<android.content.pm.ApplicationInfo> getInstalledApplicationsAsUser(int i, int i2);

    public abstract java.util.List<android.content.pm.PackageInfo> getInstalledPackages(int i);

    @android.annotation.SystemApi
    public abstract java.util.List<android.content.pm.PackageInfo> getInstalledPackagesAsUser(int i, int i2);

    @java.lang.Deprecated
    public abstract java.lang.String getInstallerPackageName(java.lang.String str);

    public abstract java.lang.String getInstantAppAndroidId(java.lang.String str, android.os.UserHandle userHandle);

    public abstract byte[] getInstantAppCookie();

    public abstract int getInstantAppCookieMaxBytes();

    public abstract int getInstantAppCookieMaxSize();

    @android.annotation.SystemApi
    public abstract android.graphics.drawable.Drawable getInstantAppIcon(java.lang.String str);

    @android.annotation.SystemApi
    public abstract android.content.ComponentName getInstantAppInstallerComponent();

    @android.annotation.SystemApi
    public abstract android.content.ComponentName getInstantAppResolverSettingsComponent();

    @android.annotation.SystemApi
    public abstract java.util.List<android.content.pm.InstantAppInfo> getInstantApps();

    public abstract android.content.pm.InstrumentationInfo getInstrumentationInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract java.util.List<android.content.pm.IntentFilterVerificationInfo> getIntentFilterVerifications(java.lang.String str);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract int getIntentVerificationStatusAsUser(java.lang.String str, int i);

    public abstract android.content.pm.KeySet getKeySetByAlias(java.lang.String str, java.lang.String str2);

    public abstract android.content.Intent getLaunchIntentForPackage(java.lang.String str);

    public abstract android.content.Intent getLeanbackLaunchIntentForPackage(java.lang.String str);

    public abstract int getMoveStatus(int i);

    public abstract java.lang.String getNameForUid(int i);

    public abstract java.lang.String[] getNamesForUids(int[] iArr);

    public abstract java.util.List<android.os.storage.VolumeInfo> getPackageCandidateVolumes(android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.os.storage.VolumeInfo getPackageCurrentVolume(android.content.pm.ApplicationInfo applicationInfo);

    public abstract int[] getPackageGids(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract int[] getPackageGids(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.PackageInfo getPackageInfo(android.content.pm.VersionedPackage versionedPackage, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.PackageInfo getPackageInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.PackageInfo getPackageInfoAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.PackageInstaller getPackageInstaller();

    @java.lang.Deprecated
    public abstract void getPackageSizeInfoAsUser(java.lang.String str, int i, android.content.pm.IPackageStatsObserver iPackageStatsObserver);

    public abstract int getPackageUid(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract int getPackageUidAsUser(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract int getPackageUidAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract java.lang.String[] getPackagesForUid(int i);

    public abstract java.util.List<android.content.pm.PackageInfo> getPackagesHoldingPermissions(java.lang.String[] strArr, int i);

    @android.annotation.SystemApi
    public abstract int getPermissionFlags(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle);

    public abstract android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    @java.lang.Deprecated
    public abstract int getPreferredActivities(java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str);

    @java.lang.Deprecated
    public abstract java.util.List<android.content.pm.PackageInfo> getPreferredPackages(int i);

    public abstract java.util.List<android.os.storage.VolumeInfo> getPrimaryStorageCandidateVolumes();

    public abstract android.os.storage.VolumeInfo getPrimaryStorageCurrentVolume();

    public abstract android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.res.Resources getResourcesForActivity(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.res.Resources getResourcesForApplication(android.content.pm.ApplicationInfo applicationInfo) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.res.Resources getResourcesForApplication(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    @java.lang.Deprecated
    public abstract android.content.res.Resources getResourcesForApplicationAsUser(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract java.lang.String getServicesSystemSharedLibraryPackageName();

    public abstract java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibraries(int i);

    public abstract java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibrariesAsUser(int i, int i2);

    public abstract java.lang.String getSharedSystemSharedLibraryPackageName();

    public abstract android.content.pm.KeySet getSigningKeySet(java.lang.String str);

    public abstract android.content.pm.FeatureInfo[] getSystemAvailableFeatures();

    public abstract java.lang.String[] getSystemSharedLibraryNames();

    public abstract java.lang.CharSequence getText(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo);

    public abstract int getUidForSharedUser(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract android.graphics.drawable.Drawable getUserBadgeForDensity(android.os.UserHandle userHandle, int i);

    public abstract android.graphics.drawable.Drawable getUserBadgeForDensityNoBackground(android.os.UserHandle userHandle, int i);

    public abstract android.graphics.drawable.Drawable getUserBadgedDrawableForDensity(android.graphics.drawable.Drawable drawable, android.os.UserHandle userHandle, android.graphics.Rect rect, int i);

    public abstract android.graphics.drawable.Drawable getUserBadgedIcon(android.graphics.drawable.Drawable drawable, android.os.UserHandle userHandle);

    public abstract java.lang.CharSequence getUserBadgedLabel(java.lang.CharSequence charSequence, android.os.UserHandle userHandle);

    public abstract android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentity();

    public abstract android.content.res.XmlResourceParser getXml(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo);

    @android.annotation.SystemApi
    public abstract void grantRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle);

    public abstract boolean hasSystemFeature(java.lang.String str);

    public abstract boolean hasSystemFeature(java.lang.String str, int i);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract int installExistingPackage(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract int installExistingPackage(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    @java.lang.Deprecated
    public abstract int installExistingPackageAsUser(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    public abstract boolean isInstantApp();

    public abstract boolean isInstantApp(java.lang.String str);

    public abstract boolean isPackageAvailable(java.lang.String str);

    public abstract boolean isPackageSuspendedForUser(java.lang.String str, int i);

    public abstract boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2);

    public abstract boolean isSafeMode();

    public abstract boolean isSignedBy(java.lang.String str, android.content.pm.KeySet keySet);

    public abstract boolean isSignedByExactly(java.lang.String str, android.content.pm.KeySet keySet);

    public abstract boolean isUpgrade();

    public abstract boolean isWirelessConsentModeEnabled();

    public abstract android.graphics.drawable.Drawable loadItemIcon(android.content.pm.PackageItemInfo packageItemInfo, android.content.pm.ApplicationInfo applicationInfo);

    public abstract android.graphics.drawable.Drawable loadUnbadgedItemIcon(android.content.pm.PackageItemInfo packageItemInfo, android.content.pm.ApplicationInfo applicationInfo);

    public abstract int movePackage(java.lang.String str, android.os.storage.VolumeInfo volumeInfo);

    public abstract int movePrimaryStorage(android.os.storage.VolumeInfo volumeInfo);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers(android.content.Intent intent, int i);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser(android.content.Intent intent, int i, int i2);

    public abstract java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, int i2);

    public abstract java.util.List<android.content.pm.InstrumentationInfo> queryInstrumentation(java.lang.String str, int i);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, int i);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, int i, int i2);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentActivityOptions(android.content.ComponentName componentName, android.content.Intent[] intentArr, android.content.Intent intent, int i);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentContentProviders(android.content.Intent intent, int i);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersAsUser(android.content.Intent intent, int i, int i2);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, int i);

    public abstract java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, int i, int i2);

    public abstract java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

    @android.annotation.SystemApi
    public abstract void registerDexModule(java.lang.String str, android.content.pm.PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback);

    public abstract void registerMoveCallback(android.content.pm.PackageManager.MoveCallback moveCallback, android.os.Handler handler);

    @android.annotation.SystemApi
    public abstract void removeOnPermissionsChangeListener(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener);

    @java.lang.Deprecated
    public abstract void removePackageFromPreferred(java.lang.String str);

    public abstract void removePermission(java.lang.String str);

    @java.lang.Deprecated
    public abstract void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName);

    public abstract android.content.pm.ResolveInfo resolveActivity(android.content.Intent intent, int i);

    public abstract android.content.pm.ResolveInfo resolveActivityAsUser(android.content.Intent intent, int i, int i2);

    public abstract android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, int i);

    public abstract android.content.pm.ProviderInfo resolveContentProviderAsUser(java.lang.String str, int i, int i2);

    public abstract android.content.pm.ResolveInfo resolveService(android.content.Intent intent, int i);

    public abstract android.content.pm.ResolveInfo resolveServiceAsUser(android.content.Intent intent, int i, int i2);

    @android.annotation.SystemApi
    public abstract void revokeRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle);

    public abstract void setApplicationCategoryHint(java.lang.String str, int i);

    public abstract void setApplicationEnabledSetting(java.lang.String str, int i, int i2);

    public abstract boolean setApplicationHiddenSettingAsUser(java.lang.String str, boolean z, android.os.UserHandle userHandle);

    public abstract void setComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2);

    @android.annotation.SystemApi
    public abstract boolean setDefaultBrowserPackageNameAsUser(java.lang.String str, int i);

    public abstract void setInstallerPackageName(java.lang.String str, java.lang.String str2);

    public abstract boolean setInstantAppCookie(byte[] bArr);

    @android.annotation.SystemApi
    public abstract void setUpdateAvailable(java.lang.String str, boolean z);

    public abstract boolean shouldShowRequestPermissionRationale(java.lang.String str);

    public abstract void unregisterMoveCallback(android.content.pm.PackageManager.MoveCallback moveCallback);

    public abstract void updateInstantAppCookie(byte[] bArr);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract boolean updateIntentVerificationStatusAsUser(java.lang.String str, int i, int i2);

    @android.annotation.SystemApi
    public abstract void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, android.os.UserHandle userHandle);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract void verifyIntentFilter(int i, int i2, java.util.List<java.lang.String> list);

    public abstract void verifyPendingInstall(int i, int i2);

    public static class NameNotFoundException extends android.util.AndroidException {
        public NameNotFoundException() {
        }

        public NameNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    public static final class Property implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.PackageManager.Property> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageManager.Property>() { // from class: android.content.pm.PackageManager.Property.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageManager.Property createFromParcel(android.os.Parcel parcel) {
                java.lang.String readString = parcel.readString();
                int readInt = parcel.readInt();
                java.lang.String readString2 = parcel.readString();
                java.lang.String readString3 = parcel.readString();
                if (readInt == 1) {
                    return new android.content.pm.PackageManager.Property(readString, parcel.readBoolean(), readString2, readString3);
                }
                if (readInt == 2) {
                    return new android.content.pm.PackageManager.Property(readString, parcel.readFloat(), readString2, readString3);
                }
                if (readInt == 3) {
                    return new android.content.pm.PackageManager.Property(readString, parcel.readInt(), false, readString2, readString3);
                }
                if (readInt == 4) {
                    return new android.content.pm.PackageManager.Property(readString, parcel.readInt(), true, readString2, readString3);
                }
                if (readInt == 5) {
                    return new android.content.pm.PackageManager.Property(readString, parcel.readString(), readString2, readString3);
                }
                return null;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageManager.Property[] newArray(int i) {
                return new android.content.pm.PackageManager.Property[i];
            }
        };
        private static final int TYPE_BOOLEAN = 1;
        private static final int TYPE_FLOAT = 2;
        private static final int TYPE_INTEGER = 3;
        private static final int TYPE_RESOURCE = 4;
        private static final int TYPE_STRING = 5;
        private boolean mBooleanValue;
        private final java.lang.String mClassName;
        private float mFloatValue;
        private int mIntegerValue;
        private final java.lang.String mName;
        private final java.lang.String mPackageName;
        private java.lang.String mStringValue;
        private final int mType;

        public Property(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
            if (i < 1 || i > 5) {
                throw new java.lang.IllegalArgumentException("Invalid type");
            }
            this.mName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mType = i;
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str2);
            this.mClassName = str3;
        }

        public Property(java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3) {
            this(str, 1, str2, str3);
            this.mBooleanValue = z;
        }

        public Property(java.lang.String str, float f, java.lang.String str2, java.lang.String str3) {
            this(str, 2, str2, str3);
            this.mFloatValue = f;
        }

        public Property(java.lang.String str, int i, boolean z, java.lang.String str2, java.lang.String str3) {
            this(str, z ? 4 : 3, str2, str3);
            this.mIntegerValue = i;
        }

        public Property(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
            this(str, 5, str3, str4);
            this.mStringValue = str2;
        }

        public int getType() {
            return this.mType;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public java.lang.String getClassName() {
            return this.mClassName;
        }

        public boolean getBoolean() {
            return this.mBooleanValue;
        }

        public boolean isBoolean() {
            return this.mType == 1;
        }

        public float getFloat() {
            return this.mFloatValue;
        }

        public boolean isFloat() {
            return this.mType == 2;
        }

        public int getInteger() {
            if (this.mType == 3) {
                return this.mIntegerValue;
            }
            return 0;
        }

        public boolean isInteger() {
            return this.mType == 3;
        }

        public int getResourceId() {
            if (this.mType == 4) {
                return this.mIntegerValue;
            }
            return 0;
        }

        public boolean isResourceId() {
            return this.mType == 4;
        }

        public java.lang.String getString() {
            return this.mStringValue;
        }

        public boolean isString() {
            return this.mType == 5;
        }

        public android.os.Bundle toBundle(android.os.Bundle bundle) {
            if (bundle == null || bundle == android.os.Bundle.EMPTY) {
                bundle = new android.os.Bundle();
            }
            if (this.mType == 1) {
                bundle.putBoolean(this.mName, this.mBooleanValue);
            } else if (this.mType == 2) {
                bundle.putFloat(this.mName, this.mFloatValue);
            } else if (this.mType == 3) {
                bundle.putInt(this.mName, this.mIntegerValue);
            } else if (this.mType == 4) {
                bundle.putInt(this.mName, this.mIntegerValue);
            } else if (this.mType == 5) {
                bundle.putString(this.mName, this.mStringValue);
            }
            return bundle;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mName);
            parcel.writeInt(this.mType);
            parcel.writeString(this.mPackageName);
            parcel.writeString(this.mClassName);
            if (this.mType == 1) {
                parcel.writeBoolean(this.mBooleanValue);
                return;
            }
            if (this.mType == 2) {
                parcel.writeFloat(this.mFloatValue);
                return;
            }
            if (this.mType == 3) {
                parcel.writeInt(this.mIntegerValue);
            } else if (this.mType == 4) {
                parcel.writeInt(this.mIntegerValue);
            } else if (this.mType == 5) {
                parcel.writeString(this.mStringValue);
            }
        }
    }

    public static final class ComponentEnabledSetting implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.PackageManager.ComponentEnabledSetting> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageManager.ComponentEnabledSetting>() { // from class: android.content.pm.PackageManager.ComponentEnabledSetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageManager.ComponentEnabledSetting[] newArray(int i) {
                return new android.content.pm.PackageManager.ComponentEnabledSetting[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageManager.ComponentEnabledSetting createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageManager.ComponentEnabledSetting(parcel);
            }
        };
        private final android.content.ComponentName mComponentName;
        private final int mEnabledFlags;
        private final int mEnabledState;
        private final java.lang.String mPackageName;

        public ComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2) {
            this.mPackageName = null;
            this.mComponentName = (android.content.ComponentName) java.util.Objects.requireNonNull(componentName);
            this.mEnabledState = i;
            this.mEnabledFlags = i2;
        }

        public ComponentEnabledSetting(java.lang.String str, int i, int i2) {
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mComponentName = null;
            this.mEnabledState = i;
            this.mEnabledFlags = i2;
        }

        public java.lang.String getPackageName() {
            if (isComponent()) {
                return this.mComponentName.getPackageName();
            }
            return this.mPackageName;
        }

        public java.lang.String getClassName() {
            if (isComponent()) {
                return this.mComponentName.getClassName();
            }
            return null;
        }

        public boolean isComponent() {
            return this.mComponentName != null;
        }

        public android.content.ComponentName getComponentName() {
            return this.mComponentName;
        }

        public int getEnabledState() {
            return this.mEnabledState;
        }

        public int getEnabledFlags() {
            return this.mEnabledFlags;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            byte b = this.mPackageName != null ? (byte) 1 : (byte) 0;
            if (this.mComponentName != null) {
                b = (byte) (b | 2);
            }
            parcel.writeByte(b);
            if (this.mPackageName != null) {
                parcel.writeString(this.mPackageName);
            }
            if (this.mComponentName != null) {
                parcel.writeTypedObject(this.mComponentName, i);
            }
            parcel.writeInt(this.mEnabledState);
            parcel.writeInt(this.mEnabledFlags);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        ComponentEnabledSetting(android.os.Parcel parcel) {
            byte readByte = parcel.readByte();
            java.lang.String readString = (readByte & 1) == 0 ? null : parcel.readString();
            android.content.ComponentName componentName = (readByte & 2) == 0 ? null : (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            this.mPackageName = readString;
            this.mComponentName = componentName;
            this.mEnabledState = readInt;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.PackageManager.EnabledState.class, (java.lang.annotation.Annotation) null, this.mEnabledState);
            this.mEnabledFlags = readInt2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.PackageManager.EnabledFlags.class, (java.lang.annotation.Annotation) null, this.mEnabledFlags);
        }

        @java.lang.Deprecated
        private void __metadata() {
        }
    }

    @android.annotation.SystemApi
    public interface OnPermissionsChangedListener {
        void onPermissionsChanged(int i);

        default void onPermissionsChanged(int i, java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            if (java.util.Objects.equals(str, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT)) {
                onPermissionsChanged(i);
            }
        }
    }

    static {
        java.lang.String str = android.permission.PermissionManager.CACHE_KEY_PACKAGE_INFO;
        sApplicationInfoCache = new android.app.PropertyInvalidatedCache<android.content.pm.PackageManager.ApplicationInfoQuery, android.content.pm.ApplicationInfo>(32, str, "getApplicationInfo") { // from class: android.content.pm.PackageManager.2
            @Override // android.app.PropertyInvalidatedCache
            public android.content.pm.ApplicationInfo recompute(android.content.pm.PackageManager.ApplicationInfoQuery applicationInfoQuery) {
                return android.content.pm.PackageManager.getApplicationInfoAsUserUncached(applicationInfoQuery.packageName, applicationInfoQuery.flags, applicationInfoQuery.userId);
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean resultEquals(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ApplicationInfo applicationInfo2) {
                return true;
            }
        };
        sCacheAutoCorker = new android.app.PropertyInvalidatedCache.AutoCorker(android.permission.PermissionManager.CACHE_KEY_PACKAGE_INFO);
        sPackageInfoCache = new android.app.PropertyInvalidatedCache<android.content.pm.PackageManager.PackageInfoQuery, android.content.pm.PackageInfo>(64, str, "getPackageInfo") { // from class: android.content.pm.PackageManager.3
            @Override // android.app.PropertyInvalidatedCache
            public android.content.pm.PackageInfo recompute(android.content.pm.PackageManager.PackageInfoQuery packageInfoQuery) {
                return android.content.pm.PackageManager.getPackageInfoAsUserUncached(packageInfoQuery.packageName, packageInfoQuery.flags, packageInfoQuery.userId);
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean resultEquals(android.content.pm.PackageInfo packageInfo, android.content.pm.PackageInfo packageInfo2) {
                return true;
            }
        };
    }

    public static class Flags {
        final long mValue;

        protected Flags(long j) {
            this.mValue = j;
        }

        public long getValue() {
            return this.mValue;
        }
    }

    public static final class PackageInfoFlags extends android.content.pm.PackageManager.Flags {
        private PackageInfoFlags(long j) {
            super(j);
        }

        public static android.content.pm.PackageManager.PackageInfoFlags of(long j) {
            return new android.content.pm.PackageManager.PackageInfoFlags(j);
        }
    }

    public static final class ApplicationInfoFlags extends android.content.pm.PackageManager.Flags {
        private ApplicationInfoFlags(long j) {
            super(j);
        }

        public static android.content.pm.PackageManager.ApplicationInfoFlags of(long j) {
            return new android.content.pm.PackageManager.ApplicationInfoFlags(j);
        }
    }

    public static final class ComponentInfoFlags extends android.content.pm.PackageManager.Flags {
        private ComponentInfoFlags(long j) {
            super(j);
        }

        public static android.content.pm.PackageManager.ComponentInfoFlags of(long j) {
            return new android.content.pm.PackageManager.ComponentInfoFlags(j);
        }
    }

    public static final class ResolveInfoFlags extends android.content.pm.PackageManager.Flags {
        private ResolveInfoFlags(long j) {
            super(j);
        }

        public static android.content.pm.PackageManager.ResolveInfoFlags of(long j) {
            return new android.content.pm.PackageManager.ResolveInfoFlags(j);
        }
    }

    public int getUserId() {
        return android.os.UserHandle.myUserId();
    }

    @java.lang.Deprecated
    public PackageManager() {
    }

    public android.content.pm.PackageInfo getPackageInfo(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPackageInfo not implemented in subclass");
    }

    public android.content.pm.PackageInfo getPackageInfo(android.content.pm.VersionedPackage versionedPackage, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPackageInfo not implemented in subclass");
    }

    public android.content.pm.PackageInfo getPackageInfoAsUser(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPackageInfoAsUser not implemented in subclass");
    }

    public android.content.IntentSender getLaunchIntentSenderForPackage(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("getLaunchIntentSenderForPackage not implementedin subclass");
    }

    public int[] getPackageGids(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPackageGids not implemented in subclass");
    }

    public int getPackageUid(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPackageUid not implemented in subclass");
    }

    @android.annotation.SystemApi
    public int getPackageUidAsUser(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPackageUidAsUser not implemented in subclass");
    }

    public com.nvidia.NvAppProfileService getAppProfileService() {
        return null;
    }

    public void getPlatformPermissionsForGroup(java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<java.lang.String>> consumer) {
    }

    public void getGroupOfPlatformPermission(java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.String> consumer) {
    }

    public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getApplicationInfo not implemented in subclass");
    }

    public android.content.pm.ApplicationInfo getApplicationInfoAsUser(java.lang.String str, android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getApplicationInfoAsUser not implemented in subclass");
    }

    @android.annotation.SystemApi
    public android.content.pm.ApplicationInfo getApplicationInfoAsUser(java.lang.String str, int i, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationInfoAsUser(str, i, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public android.content.pm.ApplicationInfo getApplicationInfoAsUser(java.lang.String str, android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationInfoAsUser(str, applicationInfoFlags, userHandle.getIdentifier());
    }

    public int getTargetSdkVersion(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException();
    }

    public android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getActivityInfo not implemented in subclass");
    }

    public android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getReceiverInfo not implemented in subclass");
    }

    public android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getServiceInfo not implemented in subclass");
    }

    public android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getProviderInfo not implemented in subclass");
    }

    public android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getModuleInfo not implemented in subclass");
    }

    public java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) {
        throw new java.lang.UnsupportedOperationException("getInstalledModules not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageInfo> getInstalledPackages(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        throw new java.lang.UnsupportedOperationException("getInstalledPackages not implemented in subclass");
    }

    @android.annotation.SystemApi
    public android.os.PersistableBundle getAppMetadata(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getAppMetadata not implemented in subclass");
    }

    @android.annotation.SystemApi
    public int getAppMetadataSource(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getAppMetadataSource not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageInfo> getPackagesHoldingPermissions(java.lang.String[] strArr, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        throw new java.lang.UnsupportedOperationException("getPackagesHoldingPermissions not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.PackageInfo> getInstalledPackagesAsUser(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("getApplicationInfoAsUser not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.lang.String getPermissionControllerPackageName() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.lang.String getSdkSandboxPackageName() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle, java.lang.String str3) {
        revokeRuntimePermission(str, str2, userHandle);
    }

    public java.util.Set<java.lang.String> getWhitelistedRestrictedPermissions(java.lang.String str, int i) {
        return java.util.Collections.emptySet();
    }

    public boolean addWhitelistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i) {
        return false;
    }

    public boolean removeWhitelistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i) {
        return false;
    }

    public boolean setAutoRevokeWhitelisted(java.lang.String str, boolean z) {
        return false;
    }

    public boolean isAutoRevokeWhitelisted(java.lang.String str) {
        return false;
    }

    public java.lang.CharSequence getBackgroundPermissionOptionLabel() {
        return "";
    }

    public android.content.Intent buildRequestPermissionsIntent(java.lang.String[] strArr) {
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            throw new java.lang.IllegalArgumentException("permission cannot be null or empty");
        }
        android.content.Intent intent = new android.content.Intent(ACTION_REQUEST_PERMISSIONS);
        intent.putExtra(EXTRA_REQUEST_PERMISSIONS_NAMES, strArr);
        intent.setPackage(getPermissionControllerPackageName());
        return intent;
    }

    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags) {
        throw new java.lang.UnsupportedOperationException("getInstalledApplications not implemented in subclass");
    }

    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplicationsAsUser(android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("getInstalledApplicationsAsUser not implemented in subclass");
    }

    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibraries(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        throw new java.lang.UnsupportedOperationException("getSharedLibraries() not implemented in subclass");
    }

    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibrariesAsUser(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("getSharedLibrariesAsUser() not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(java.lang.String str, int i) {
        throw new java.lang.UnsupportedOperationException("getDeclaredSharedLibraries() not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        throw new java.lang.UnsupportedOperationException("getDeclaredSharedLibraries() not implemented in subclass");
    }

    public android.content.pm.ResolveInfo resolveActivity(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("resolveActivity not implemented in subclass");
    }

    public android.content.pm.ResolveInfo resolveActivityAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("resolveActivityAsUser not implemented in subclass");
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("queryIntentActivities not implemented in subclass");
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("queryIntentActivitiesAsUser not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
        return queryIntentActivitiesAsUser(intent, i, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, android.os.UserHandle userHandle) {
        return queryIntentActivitiesAsUser(intent, resolveInfoFlags, userHandle.getIdentifier());
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivityOptions(android.content.ComponentName componentName, java.util.List<android.content.Intent> list, android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("queryIntentActivityOptions not implemented in subclass");
    }

    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("queryBroadcastReceivers not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
        return queryBroadcastReceiversAsUser(intent, i, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, android.os.UserHandle userHandle) {
        return queryBroadcastReceiversAsUser(intent, resolveInfoFlags, userHandle.getIdentifier());
    }

    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("queryBroadcastReceiversAsUser not implemented in subclass");
    }

    @java.lang.Deprecated
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers(android.content.Intent intent, int i, int i2) {
        if (dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() >= 26) {
            throw new java.lang.UnsupportedOperationException("Shame on you for calling the hidden API queryBroadcastReceivers(). Shame!");
        }
        android.util.Log.d(TAG, "Shame on you for calling the hidden API queryBroadcastReceivers(). Shame!");
        return queryBroadcastReceiversAsUser(intent, i, i2);
    }

    public android.content.pm.ResolveInfo resolveService(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("resolveService not implemented in subclass");
    }

    public android.content.pm.ResolveInfo resolveServiceAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("resolveServiceAsUser not implemented in subclass");
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("queryIntentServices not implemented in subclass");
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("queryIntentServicesAsUser not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
        return queryIntentServicesAsUser(intent, i, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, android.os.UserHandle userHandle) {
        return queryIntentServicesAsUser(intent, resolveInfoFlags, userHandle.getIdentifier());
    }

    protected java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("queryIntentContentProvidersAsUser not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersAsUser(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
        return queryIntentContentProvidersAsUser(intent, i, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, android.os.UserHandle userHandle) {
        return queryIntentContentProvidersAsUser(intent, resolveInfoFlags, userHandle.getIdentifier());
    }

    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProviders(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        throw new java.lang.UnsupportedOperationException("queryIntentContentProviders not implemented in subclass");
    }

    public android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) {
        throw new java.lang.UnsupportedOperationException("resolveContentProvider not implemented in subclass");
    }

    public android.content.pm.ProviderInfo resolveContentProviderAsUser(java.lang.String str, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags, int i) {
        throw new java.lang.UnsupportedOperationException("resolveContentProviderAsUser not implemented in subclass");
    }

    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) {
        throw new java.lang.UnsupportedOperationException("queryContentProviders not implemented in subclass");
    }

    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, int i2, java.lang.String str2) {
        return queryContentProviders(str, i, i2);
    }

    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags, java.lang.String str2) {
        return queryContentProviders(str, i, componentInfoFlags);
    }

    public android.content.res.Resources getResourcesForApplication(android.content.pm.ApplicationInfo applicationInfo, android.content.res.Configuration configuration) throws android.content.pm.PackageManager.NameNotFoundException {
        return getResourcesForApplication(applicationInfo);
    }

    public android.content.pm.PackageInfo getPackageArchiveInfo(java.lang.String str, int i) {
        return getPackageArchiveInfo(str, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    public android.content.pm.PackageInfo getPackageArchiveInfo(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        int i;
        java.io.File file = new java.io.File(str);
        long value = packageInfoFlags.getValue();
        if ((value & 786432) == 0) {
            value |= 786432;
        }
        if ((134217792 & value) == 0) {
            i = 0;
        } else {
            i = 32;
        }
        try {
            return com.android.internal.pm.parsing.PackageInfoCommonUtils.generate(new com.android.internal.pm.parsing.PackageParser2(null, null, null, new com.android.internal.pm.parsing.PackageParser2.Callback() { // from class: android.content.pm.PackageManager.1
                @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
                public boolean hasFeature(java.lang.String str2) {
                    return android.content.pm.PackageManager.this.hasSystemFeature(str2);
                }

                @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
                public java.util.Set<java.lang.String> getHiddenApiWhitelistedApps() {
                    return java.util.Collections.emptySet();
                }

                @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
                public java.util.Set<java.lang.String> getInstallConstraintsAllowlist() {
                    return java.util.Collections.emptySet();
                }

                @Override // com.android.internal.pm.parsing.PackageParser2.Callback
                public boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo) {
                    return false;
                }
            }).parsePackage(file, i, false), value, android.os.UserHandle.myUserId());
        } catch (com.android.internal.pm.parsing.PackageParserException e) {
            android.util.Log.w(TAG, "Failure to parse package archive apkFile= " + file);
            return null;
        }
    }

    public android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getInstallSourceInfo not implemented");
    }

    public boolean isAppArchivable(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("isAppArchivable not implemented");
    }

    public void freeStorageAndNotify(long j, android.content.pm.IPackageDataObserver iPackageDataObserver) {
        freeStorageAndNotify(null, j, iPackageDataObserver);
    }

    public void freeStorage(long j, android.content.IntentSender intentSender) {
        freeStorage(null, j, intentSender);
    }

    @java.lang.Deprecated
    public void getPackageSizeInfo(java.lang.String str, android.content.pm.IPackageStatsObserver iPackageStatsObserver) {
        getPackageSizeInfoAsUser(str, getUserId(), iPackageStatsObserver);
    }

    @java.lang.Deprecated
    public void addPreferredActivityAsUser(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    @android.annotation.SystemApi
    public void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, java.util.List<android.content.ComponentName> list, android.content.ComponentName componentName) {
        replacePreferredActivity(intentFilter, i, (android.content.ComponentName[]) list.toArray(new android.content.ComponentName[0]), componentName);
    }

    @java.lang.Deprecated
    public void replacePreferredActivityAsUser(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }

    public void addUniquePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName) {
        throw new java.lang.UnsupportedOperationException("addUniquePreferredActivity not implemented in subclass");
    }

    public void setComponentEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list) {
        throw new java.lang.UnsupportedOperationException("setComponentEnabledSettings not implementedin subclass");
    }

    @android.annotation.SystemApi
    public void setSyntheticAppDetailsActivityEnabled(java.lang.String str, boolean z) {
        throw new java.lang.UnsupportedOperationException("setSyntheticAppDetailsActivityEnabled not implemented");
    }

    public boolean getSyntheticAppDetailsActivityEnabled(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("getSyntheticAppDetailsActivityEnabled not implemented");
    }

    @android.annotation.SystemApi
    public void setSystemAppState(java.lang.String str, int i) {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass");
    }

    @android.annotation.SystemApi
    public java.lang.String[] setDistractingPackageRestrictions(java.lang.String[] strArr, int i) {
        throw new java.lang.UnsupportedOperationException("setDistractingPackageRestrictions not implemented");
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String[] setPackagesSuspended(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @android.annotation.SystemApi
    public java.lang.String[] setPackagesSuspended(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo) {
        throw new java.lang.UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @android.annotation.SystemApi
    public java.lang.String[] setPackagesSuspended(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo, int i) {
        throw new java.lang.UnsupportedOperationException("setPackagesSuspended not implemented");
    }

    @android.annotation.SystemApi
    public java.lang.String[] getUnsuspendablePackages(java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("getUnsuspendablePackages not implemented");
    }

    public boolean isPackageSuspended(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("isPackageSuspended not implemented");
    }

    public boolean isPackageSuspended() {
        throw new java.lang.UnsupportedOperationException("isPackageSuspended not implemented");
    }

    public android.os.Bundle getSuspendedPackageAppExtras() {
        throw new java.lang.UnsupportedOperationException("getSuspendedPackageAppExtras not implemented");
    }

    public java.lang.String getSuspendingPackage(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("getSuspendingPackage not implemented");
    }

    public boolean isPackageStopped(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("isPackageStopped not implemented");
    }

    public boolean isPackageQuarantined(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("isPackageQuarantined not implemented");
    }

    public static boolean isMoveStatusFinished(int i) {
        return i < 0 || i > 100;
    }

    public static abstract class MoveCallback {
        public abstract void onStatusChanged(int i, int i2, long j);

        public void onCreated(int i, android.os.Bundle bundle) {
        }
    }

    public boolean isDeviceUpgrading() {
        return false;
    }

    public boolean removeCrossProfileIntentFilter(android.content.IntentFilter intentFilter, int i, int i2, int i3) {
        throw new java.lang.UnsupportedOperationException("removeCrossProfileIntentFilter not implemented in subclass");
    }

    public static java.lang.String installStatusToString(int i, java.lang.String str) {
        java.lang.String installStatusToString = installStatusToString(i);
        if (str != null) {
            return installStatusToString + ": " + str;
        }
        return installStatusToString;
    }

    public static java.lang.String installStatusToString(int i) {
        switch (i) {
            case INSTALL_FAILED_MULTI_ARCH_NOT_MATCH_ALL_NATIVE_ABIS /* -131 */:
                return "INSTALL_FAILED_MULTI_ARCH_NOT_MATCH_ALL_NATIVE_ABIS";
            case INSTALL_FAILED_SHARED_LIBRARY_BAD_CERTIFICATE_DIGEST /* -130 */:
                return "INSTALL_FAILED_SHARED_LIBRARY_BAD_CERTIFICATE_DIGEST";
            case INSTALL_FAILED_PROCESS_NOT_DEFINED /* -122 */:
                return "INSTALL_FAILED_PROCESS_NOT_DEFINED";
            case INSTALL_FAILED_WRONG_INSTALLED_VERSION /* -121 */:
                return "INSTALL_FAILED_WRONG_INSTALLED_VERSION";
            case INSTALL_FAILED_BAD_SIGNATURE /* -118 */:
                return "INSTALL_FAILED_BAD_SIGNATURE";
            case INSTALL_FAILED_BAD_DEX_METADATA /* -117 */:
                return "INSTALL_FAILED_BAD_DEX_METADATA";
            case INSTALL_FAILED_SESSION_INVALID /* -116 */:
                return "INSTALL_FAILED_SESSION_INVALID";
            case INSTALL_FAILED_ABORTED /* -115 */:
                return "INSTALL_FAILED_ABORTED";
            case -113:
                return "INSTALL_FAILED_NO_MATCHING_ABIS";
            case INSTALL_FAILED_DUPLICATE_PERMISSION /* -112 */:
                return "INSTALL_FAILED_DUPLICATE_PERMISSION";
            case INSTALL_FAILED_USER_RESTRICTED /* -111 */:
                return "INSTALL_FAILED_USER_RESTRICTED";
            case -110:
                return "INSTALL_FAILED_INTERNAL_ERROR";
            case INSTALL_PARSE_FAILED_MANIFEST_EMPTY /* -109 */:
                return "INSTALL_PARSE_FAILED_MANIFEST_EMPTY";
            case INSTALL_PARSE_FAILED_MANIFEST_MALFORMED /* -108 */:
                return "INSTALL_PARSE_FAILED_MANIFEST_MALFORMED";
            case INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID /* -107 */:
                return "INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID";
            case INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME /* -106 */:
                return "INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME";
            case INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING /* -105 */:
                return "INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING";
            case INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES /* -104 */:
                return "INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES";
            case -103:
                return "INSTALL_PARSE_FAILED_NO_CERTIFICATES";
            case -102:
                return "INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION";
            case -101:
                return "INSTALL_PARSE_FAILED_BAD_MANIFEST";
            case -100:
                return "INSTALL_PARSE_FAILED_NOT_APK";
            case -29:
                return "INSTALL_FAILED_DEPRECATED_SDK_VERSION";
            case -28:
                return "INSTALL_FAILED_MISSING_SPLIT";
            case -25:
                return "INSTALL_FAILED_VERSION_DOWNGRADE";
            case -24:
                return "INSTALL_FAILED_UID_CHANGED";
            case -23:
                return "INSTALL_FAILED_PACKAGE_CHANGED";
            case -22:
                return "INSTALL_FAILED_VERIFICATION_FAILURE";
            case -21:
                return "INSTALL_FAILED_VERIFICATION_TIMEOUT";
            case -20:
                return "INSTALL_FAILED_MEDIA_UNAVAILABLE";
            case -19:
                return "INSTALL_FAILED_INVALID_INSTALL_LOCATION";
            case -18:
                return "INSTALL_FAILED_CONTAINER_ERROR";
            case -17:
                return "INSTALL_FAILED_MISSING_FEATURE";
            case -16:
                return "INSTALL_FAILED_CPU_ABI_INCOMPATIBLE";
            case -15:
                return "INSTALL_FAILED_TEST_ONLY";
            case -14:
                return "INSTALL_FAILED_NEWER_SDK";
            case -13:
                return "INSTALL_FAILED_CONFLICTING_PROVIDER";
            case -12:
                return "INSTALL_FAILED_OLDER_SDK";
            case -11:
                return "INSTALL_FAILED_DEXOPT";
            case -10:
                return "INSTALL_FAILED_REPLACE_COULDNT_DELETE";
            case -9:
                return "INSTALL_FAILED_MISSING_SHARED_LIBRARY";
            case -8:
                return "INSTALL_FAILED_SHARED_USER_INCOMPATIBLE";
            case -7:
                return "INSTALL_FAILED_UPDATE_INCOMPATIBLE";
            case -6:
                return "INSTALL_FAILED_NO_SHARED_USER";
            case -5:
                return "INSTALL_FAILED_DUPLICATE_PACKAGE";
            case -4:
                return "INSTALL_FAILED_INSUFFICIENT_STORAGE";
            case -3:
                return "INSTALL_FAILED_INVALID_URI";
            case -2:
                return "INSTALL_FAILED_INVALID_APK";
            case -1:
                return "INSTALL_FAILED_ALREADY_EXISTS";
            case 1:
                return "INSTALL_SUCCEEDED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static int installStatusToPublicStatus(int i) {
        switch (i) {
            case INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE /* -129 */:
                return 2;
            case INSTALL_FAILED_BAD_SIGNATURE /* -118 */:
                return 4;
            case INSTALL_FAILED_BAD_DEX_METADATA /* -117 */:
                return 4;
            case INSTALL_FAILED_ABORTED /* -115 */:
                return 3;
            case -113:
                return 7;
            case INSTALL_FAILED_DUPLICATE_PERMISSION /* -112 */:
                return 5;
            case INSTALL_FAILED_USER_RESTRICTED /* -111 */:
                return 7;
            case -110:
                return 1;
            case INSTALL_PARSE_FAILED_MANIFEST_EMPTY /* -109 */:
                return 4;
            case INSTALL_PARSE_FAILED_MANIFEST_MALFORMED /* -108 */:
                return 4;
            case INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID /* -107 */:
                return 4;
            case INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME /* -106 */:
                return 4;
            case INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING /* -105 */:
                return 4;
            case INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES /* -104 */:
                return 4;
            case -103:
                return 4;
            case -102:
                return 4;
            case -101:
                return 4;
            case -100:
                return 4;
            case -29:
                return 7;
            case -28:
                return 7;
            case -26:
                return 4;
            case -25:
                return 4;
            case -24:
                return 4;
            case -23:
                return 4;
            case -22:
                return 3;
            case -21:
                return 3;
            case -20:
                return 6;
            case -19:
                return 6;
            case -18:
                return 6;
            case -17:
                return 7;
            case -16:
                return 7;
            case -15:
                return 4;
            case -14:
                return 7;
            case -13:
                return 5;
            case -12:
                return 7;
            case -11:
                return 4;
            case -10:
                return 5;
            case -9:
                return 7;
            case -8:
                return 5;
            case -7:
                return 5;
            case -6:
                return 5;
            case -5:
                return 5;
            case -4:
                return 6;
            case -3:
                return 4;
            case -2:
                return 4;
            case -1:
                return 5;
            case 1:
                return 0;
            default:
                return 1;
        }
    }

    public static java.lang.String deleteStatusToString(int i, java.lang.String str) {
        java.lang.String deleteStatusToString = deleteStatusToString(i);
        if (str != null) {
            return deleteStatusToString + ": " + str;
        }
        return deleteStatusToString;
    }

    public static java.lang.String deleteStatusToString(int i) {
        switch (i) {
            case -7:
                return "DELETE_FAILED_APP_PINNED";
            case -6:
                return "DELETE_FAILED_USED_SHARED_LIBRARY";
            case -5:
                return "DELETE_FAILED_ABORTED";
            case -4:
                return "DELETE_FAILED_OWNER_BLOCKED";
            case -3:
                return "DELETE_FAILED_USER_RESTRICTED";
            case -2:
                return "DELETE_FAILED_DEVICE_POLICY_MANAGER";
            case -1:
                return "DELETE_FAILED_INTERNAL_ERROR";
            case 0:
            default:
                return java.lang.Integer.toString(i);
            case 1:
                return "DELETE_SUCCEEDED";
        }
    }

    public static int deleteStatusToPublicStatus(int i) {
        switch (i) {
        }
        return 1;
    }

    public static java.lang.String permissionFlagToString(int i) {
        switch (i) {
            case 1:
                return "USER_SET";
            case 2:
                return "USER_FIXED";
            case 4:
                return "POLICY_FIXED";
            case 8:
                return "REVOKED_COMPAT";
            case 16:
                return "SYSTEM_FIXED";
            case 32:
                return "GRANTED_BY_DEFAULT";
            case 64:
                return "REVIEW_REQUIRED";
            case 128:
                return "REVOKE_WHEN_REQUESTED";
            case 256:
                return "USER_SENSITIVE_WHEN_GRANTED";
            case 512:
                return "USER_SENSITIVE_WHEN_DENIED";
            case 2048:
                return "RESTRICTION_INSTALLER_EXEMPT";
            case 4096:
                return "RESTRICTION_SYSTEM_EXEMPT";
            case 8192:
                return "RESTRICTION_UPGRADE_EXEMPT";
            case 16384:
                return "APPLY_RESTRICTION";
            case 32768:
                return "GRANTED_BY_ROLE";
            case 65536:
                return "ONE_TIME";
            case 131072:
                return "AUTO_REVOKED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static class LegacyPackageDeleteObserver extends android.app.PackageDeleteObserver {
        private final android.content.pm.IPackageDeleteObserver mLegacy;

        public LegacyPackageDeleteObserver(android.content.pm.IPackageDeleteObserver iPackageDeleteObserver) {
            this.mLegacy = iPackageDeleteObserver;
        }

        @Override // android.app.PackageDeleteObserver
        public void onPackageDeleted(java.lang.String str, int i, java.lang.String str2) {
            if (this.mLegacy == null) {
                return;
            }
            try {
                this.mLegacy.packageDeleted(str, i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @android.annotation.SystemApi
    public static final class UninstallCompleteCallback implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.PackageManager.UninstallCompleteCallback> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageManager.UninstallCompleteCallback>() { // from class: android.content.pm.PackageManager.UninstallCompleteCallback.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageManager.UninstallCompleteCallback createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageManager.UninstallCompleteCallback(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageManager.UninstallCompleteCallback[] newArray(int i) {
                return new android.content.pm.PackageManager.UninstallCompleteCallback[i];
            }
        };
        private android.content.pm.IPackageDeleteObserver2 mBinder;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface DeleteStatus {
        }

        public UninstallCompleteCallback(android.os.IBinder iBinder) {
            this.mBinder = android.content.pm.IPackageDeleteObserver2.Stub.asInterface(iBinder);
        }

        private UninstallCompleteCallback(android.os.Parcel parcel) {
            this.mBinder = android.content.pm.IPackageDeleteObserver2.Stub.asInterface(parcel.readStrongBinder());
        }

        @android.annotation.SystemApi
        public void onUninstallComplete(java.lang.String str, int i, java.lang.String str2) {
            try {
                this.mBinder.onPackageDeleted(str, i, str2);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeStrongBinder(this.mBinder.asBinder());
        }
    }

    @android.annotation.SystemApi
    public android.content.pm.dex.ArtManager getArtManager() {
        throw new java.lang.UnsupportedOperationException("getArtManager not implemented in subclass");
    }

    @android.annotation.SystemApi
    public void setHarmfulAppWarning(java.lang.String str, java.lang.CharSequence charSequence) {
        throw new java.lang.UnsupportedOperationException("setHarmfulAppWarning not implemented in subclass");
    }

    @android.annotation.SystemApi
    public java.lang.CharSequence getHarmfulAppWarning(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("getHarmfulAppWarning not implemented in subclass");
    }

    public boolean hasSigningCertificate(java.lang.String str, byte[] bArr, int i) {
        throw new java.lang.UnsupportedOperationException("hasSigningCertificate not implemented in subclass");
    }

    public boolean hasSigningCertificate(int i, byte[] bArr, int i2) {
        throw new java.lang.UnsupportedOperationException("hasSigningCertificate not implemented in subclass");
    }

    public void requestChecksums(java.lang.String str, boolean z, int i, java.util.List<java.security.cert.Certificate> list, android.content.pm.PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) throws java.security.cert.CertificateEncodingException, android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("requestChecksums not implemented in subclass");
    }

    public java.lang.String getDefaultTextClassifierPackageName() {
        throw new java.lang.UnsupportedOperationException("getDefaultTextClassifierPackageName not implemented in subclass");
    }

    public java.lang.String getSystemTextClassifierPackageName() {
        throw new java.lang.UnsupportedOperationException("getSystemTextClassifierPackageName not implemented in subclass");
    }

    public java.lang.String getAttentionServicePackageName() {
        throw new java.lang.UnsupportedOperationException("getAttentionServicePackageName not implemented in subclass");
    }

    public java.lang.String getRotationResolverPackageName() {
        throw new java.lang.UnsupportedOperationException("getRotationResolverPackageName not implemented in subclass");
    }

    public java.lang.String getWellbeingPackageName() {
        throw new java.lang.UnsupportedOperationException("getWellbeingPackageName not implemented in subclass");
    }

    public java.lang.String getAppPredictionServicePackageName() {
        throw new java.lang.UnsupportedOperationException("getAppPredictionServicePackageName not implemented in subclass");
    }

    public java.lang.String getSystemCaptionsServicePackageName() {
        throw new java.lang.UnsupportedOperationException("getSystemCaptionsServicePackageName not implemented in subclass");
    }

    public java.lang.String getSetupWizardPackageName() {
        throw new java.lang.UnsupportedOperationException("getSetupWizardPackageName not implemented in subclass");
    }

    @java.lang.Deprecated
    public final java.lang.String getContentCaptureServicePackageName() {
        throw new java.lang.UnsupportedOperationException("getContentCaptureServicePackageName is deprecated");
    }

    @android.annotation.SystemApi
    public java.lang.String getIncidentReportApproverPackageName() {
        throw new java.lang.UnsupportedOperationException("getIncidentReportApproverPackageName not implemented in subclass");
    }

    public boolean isPackageStateProtected(java.lang.String str, int i) {
        throw new java.lang.UnsupportedOperationException("isPackageStateProtected not implemented in subclass");
    }

    @android.annotation.SystemApi
    public void sendDeviceCustomizationReadyBroadcast() {
        throw new java.lang.UnsupportedOperationException("sendDeviceCustomizationReadyBroadcast not implemented in subclass");
    }

    public boolean isAutoRevokeWhitelisted() {
        throw new java.lang.UnsupportedOperationException("isAutoRevokeWhitelisted not implemented in subclass");
    }

    public boolean isDefaultApplicationIcon(android.graphics.drawable.Drawable drawable) {
        int sourceDrawableResId = drawable instanceof android.graphics.drawable.AdaptiveIconDrawable ? ((android.graphics.drawable.AdaptiveIconDrawable) drawable).getSourceDrawableResId() : 0;
        return sourceDrawableResId == 17301651 || sourceDrawableResId == 17303970;
    }

    public void setMimeGroup(java.lang.String str, java.util.Set<java.lang.String> set) {
        throw new java.lang.UnsupportedOperationException("setMimeGroup not implemented in subclass");
    }

    public java.util.Set<java.lang.String> getMimeGroup(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("getMimeGroup not implemented in subclass");
    }

    public android.content.pm.PackageManager.Property getProperty(java.lang.String str, java.lang.String str2) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getProperty not implemented in subclass");
    }

    public android.content.pm.PackageManager.Property getProperty(java.lang.String str, android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getProperty not implemented in subclass");
    }

    public android.content.pm.PackageManager.Property getPropertyAsUser(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("getPropertyAsUser not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageManager.Property> queryApplicationProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("qeuryApplicationProperty not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageManager.Property> queryActivityProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("qeuryActivityProperty not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageManager.Property> queryProviderProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("qeuryProviderProperty not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageManager.Property> queryReceiverProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("qeuryReceiverProperty not implemented in subclass");
    }

    public java.util.List<android.content.pm.PackageManager.Property> queryServiceProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("qeuryServiceProperty not implemented in subclass");
    }

    public boolean canPackageQuery(java.lang.String str, java.lang.String str2) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("canPackageQuery not implemented in subclass");
    }

    public boolean[] canPackageQuery(java.lang.String str, java.lang.String[] strArr) throws android.content.pm.PackageManager.NameNotFoundException {
        throw new java.lang.UnsupportedOperationException("canPackageQuery not implemented in subclass");
    }

    public void makeProviderVisible(int i, java.lang.String str) {
        try {
            android.app.ActivityThread.getPackageManager().makeProviderVisible(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void makeUidVisible(int i, int i2) {
        throw new java.lang.UnsupportedOperationException("makeUidVisible not implemented in subclass");
    }

    public android.content.pm.ArchivedPackageInfo getArchivedPackage(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("getArchivedPackage not implemented in subclass");
    }

    private static final class ApplicationInfoQuery {
        final long flags;
        final java.lang.String packageName;
        final int userId;

        ApplicationInfoQuery(java.lang.String str, long j, int i) {
            this.packageName = str;
            this.flags = j;
            this.userId = i;
        }

        public java.lang.String toString() {
            return java.lang.String.format("ApplicationInfoQuery(packageName=\"%s\", flags=%s, userId=%s)", this.packageName, java.lang.Long.valueOf(this.flags), java.lang.Integer.valueOf(this.userId));
        }

        public int hashCode() {
            return (((java.util.Objects.hashCode(this.packageName) * 13) + java.util.Objects.hashCode(java.lang.Long.valueOf(this.flags))) * 13) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.userId));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                android.content.pm.PackageManager.ApplicationInfoQuery applicationInfoQuery = (android.content.pm.PackageManager.ApplicationInfoQuery) obj;
                return java.util.Objects.equals(this.packageName, applicationInfoQuery.packageName) && this.flags == applicationInfoQuery.flags && this.userId == applicationInfoQuery.userId;
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.pm.ApplicationInfo getApplicationInfoAsUserUncached(java.lang.String str, long j, int i) {
        try {
            return android.app.ActivityThread.getPackageManager().getApplicationInfo(str, j, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.content.pm.ApplicationInfo getApplicationInfoAsUserCached(java.lang.String str, long j, int i) {
        return sApplicationInfoCache.query(new android.content.pm.PackageManager.ApplicationInfoQuery(str, j, i));
    }

    public static void disableApplicationInfoCache() {
        sApplicationInfoCache.disableLocal();
    }

    public static void invalidatePackageInfoCache() {
        sCacheAutoCorker.autoCork();
    }

    private static final class PackageInfoQuery {
        final long flags;
        final java.lang.String packageName;
        final int userId;

        PackageInfoQuery(java.lang.String str, long j, int i) {
            this.packageName = str;
            this.flags = j;
            this.userId = i;
        }

        public java.lang.String toString() {
            return java.lang.String.format("PackageInfoQuery(packageName=\"%s\", flags=%s, userId=%s)", this.packageName, java.lang.Long.valueOf(this.flags), java.lang.Integer.valueOf(this.userId));
        }

        public int hashCode() {
            return (((java.util.Objects.hashCode(this.packageName) * 13) + java.util.Objects.hashCode(java.lang.Long.valueOf(this.flags))) * 13) + java.util.Objects.hashCode(java.lang.Integer.valueOf(this.userId));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                android.content.pm.PackageManager.PackageInfoQuery packageInfoQuery = (android.content.pm.PackageManager.PackageInfoQuery) obj;
                return java.util.Objects.equals(this.packageName, packageInfoQuery.packageName) && this.flags == packageInfoQuery.flags && this.userId == packageInfoQuery.userId;
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.pm.PackageInfo getPackageInfoAsUserUncached(java.lang.String str, long j, int i) {
        try {
            return android.app.ActivityThread.getPackageManager().getPackageInfo(str, j, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.content.pm.PackageInfo getPackageInfoAsUserCached(java.lang.String str, long j, int i) {
        return sPackageInfoCache.query(new android.content.pm.PackageManager.PackageInfoQuery(str, j, i));
    }

    public static void disablePackageInfoCache() {
        sPackageInfoCache.disableLocal();
    }

    public static void corkPackageInfoCache() {
        android.app.PropertyInvalidatedCache.corkInvalidations(android.permission.PermissionManager.CACHE_KEY_PACKAGE_INFO);
    }

    public static void uncorkPackageInfoCache() {
        android.app.PropertyInvalidatedCache.uncorkInvalidations(android.permission.PermissionManager.CACHE_KEY_PACKAGE_INFO);
    }

    public android.os.IBinder getHoldLockToken() {
        try {
            return android.app.ActivityThread.getPackageManager().getHoldLockToken();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void holdLock(android.os.IBinder iBinder, int i) {
        try {
            android.app.ActivityThread.getPackageManager().holdLock(iBinder, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setKeepUninstalledPackages(java.util.List<java.lang.String> list) {
        try {
            android.app.ActivityThread.getPackageManager().setKeepUninstalledPackages(list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean canUserUninstall(java.lang.String str, android.os.UserHandle userHandle) {
        throw new java.lang.UnsupportedOperationException("canUserUninstall not implemented in subclass");
    }

    @android.annotation.SystemApi
    public boolean shouldShowNewAppInstalledNotification() {
        throw new java.lang.UnsupportedOperationException("isShowNewAppInstalledNotificationEnabled not implemented in subclass");
    }

    public void relinquishUpdateOwnership(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("relinquishUpdateOwnership not implemented in subclass");
    }

    public void registerPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
        throw new java.lang.UnsupportedOperationException("registerPackageMonitorCallback not implemented in subclass");
    }

    public void unregisterPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback) {
        throw new java.lang.UnsupportedOperationException("unregisterPackageMonitorCallback not implemented in subclass");
    }

    public <T> T parseAndroidManifest(java.io.File file, java.util.function.Function<android.content.res.XmlResourceParser, T> function) throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException("parseAndroidManifest not implemented in subclass");
    }
}
