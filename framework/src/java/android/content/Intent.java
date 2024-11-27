package android.content;

/* loaded from: classes.dex */
public class Intent implements android.os.Parcelable, java.lang.Cloneable {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ACTIVITY_RECOGNIZER = "android.intent.action.ACTIVITY_RECOGNIZER";
    public static final java.lang.String ACTION_ADVANCED_SETTINGS_CHANGED = "android.intent.action.ADVANCED_SETTINGS";
    public static final java.lang.String ACTION_AIRPLANE_MODE_CHANGED = "android.intent.action.AIRPLANE_MODE";
    public static final java.lang.String ACTION_ALARM_CHANGED = "android.intent.action.ALARM_CHANGED";
    public static final java.lang.String ACTION_ALL_APPS = "android.intent.action.ALL_APPS";
    public static final java.lang.String ACTION_ANSWER = "android.intent.action.ANSWER";
    public static final java.lang.String ACTION_APPLICATION_LOCALE_CHANGED = "android.intent.action.APPLICATION_LOCALE_CHANGED";
    public static final java.lang.String ACTION_APPLICATION_PREFERENCES = "android.intent.action.APPLICATION_PREFERENCES";
    public static final java.lang.String ACTION_APPLICATION_RESTRICTIONS_CHANGED = "android.intent.action.APPLICATION_RESTRICTIONS_CHANGED";
    public static final java.lang.String ACTION_APP_ERROR = "android.intent.action.APP_ERROR";
    public static final java.lang.String ACTION_ASSIST = "android.intent.action.ASSIST";
    public static final java.lang.String ACTION_ATTACH_DATA = "android.intent.action.ATTACH_DATA";
    public static final java.lang.String ACTION_AUTO_REVOKE_PERMISSIONS = "android.intent.action.AUTO_REVOKE_PERMISSIONS";
    public static final java.lang.String ACTION_BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_BATTERY_LEVEL_CHANGED = "android.intent.action.BATTERY_LEVEL_CHANGED";
    public static final java.lang.String ACTION_BATTERY_LOW = "android.intent.action.BATTERY_LOW";
    public static final java.lang.String ACTION_BATTERY_OKAY = "android.intent.action.BATTERY_OKAY";
    public static final java.lang.String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final java.lang.String ACTION_BUG_REPORT = "android.intent.action.BUG_REPORT";
    public static final java.lang.String ACTION_CALL = "android.intent.action.CALL";
    public static final java.lang.String ACTION_CALL_BUTTON = "android.intent.action.CALL_BUTTON";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CALL_EMERGENCY = "android.intent.action.CALL_EMERGENCY";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CALL_PRIVILEGED = "android.intent.action.CALL_PRIVILEGED";
    public static final java.lang.String ACTION_CAMERA_BUTTON = "android.intent.action.CAMERA_BUTTON";
    public static final java.lang.String ACTION_CANCEL_ENABLE_ROLLBACK = "android.intent.action.CANCEL_ENABLE_ROLLBACK";
    public static final java.lang.String ACTION_CARRIER_SETUP = "android.intent.action.CARRIER_SETUP";
    public static final java.lang.String ACTION_CHOOSER = "android.intent.action.CHOOSER";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_CLOSE_SYSTEM_DIALOGS = "android.intent.action.CLOSE_SYSTEM_DIALOGS";
    public static final java.lang.String ACTION_CONFIGURATION_CHANGED = "android.intent.action.CONFIGURATION_CHANGED";
    public static final java.lang.String ACTION_CREATE_DOCUMENT = "android.intent.action.CREATE_DOCUMENT";
    public static final java.lang.String ACTION_CREATE_NOTE = "android.intent.action.CREATE_NOTE";
    public static final java.lang.String ACTION_CREATE_REMINDER = "android.intent.action.CREATE_REMINDER";
    public static final java.lang.String ACTION_CREATE_SHORTCUT = "android.intent.action.CREATE_SHORTCUT";
    public static final java.lang.String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";
    public static final java.lang.String ACTION_DEFAULT = "android.intent.action.VIEW";
    public static final java.lang.String ACTION_DEFINE = "android.intent.action.DEFINE";
    public static final java.lang.String ACTION_DELETE = "android.intent.action.DELETE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DEVICE_CUSTOMIZATION_READY = "android.intent.action.DEVICE_CUSTOMIZATION_READY";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_DEVICE_INITIALIZATION_WIZARD = "android.intent.action.DEVICE_INITIALIZATION_WIZARD";
    public static final java.lang.String ACTION_DEVICE_LOCKED_CHANGED = "android.intent.action.DEVICE_LOCKED_CHANGED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_DEVICE_STORAGE_FULL = "android.intent.action.DEVICE_STORAGE_FULL";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_DEVICE_STORAGE_LOW = "android.intent.action.DEVICE_STORAGE_LOW";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_DEVICE_STORAGE_NOT_FULL = "android.intent.action.DEVICE_STORAGE_NOT_FULL";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_DEVICE_STORAGE_OK = "android.intent.action.DEVICE_STORAGE_OK";
    public static final java.lang.String ACTION_DIAL = "android.intent.action.DIAL";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DIAL_EMERGENCY = "android.intent.action.DIAL_EMERGENCY";
    public static final java.lang.String ACTION_DISMISS_KEYBOARD_SHORTCUTS = "com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS";
    public static final java.lang.String ACTION_DISTRACTING_PACKAGES_CHANGED = "android.intent.action.DISTRACTING_PACKAGES_CHANGED";
    public static final java.lang.String ACTION_DOCK_ACTIVE = "android.intent.action.DOCK_ACTIVE";
    public static final java.lang.String ACTION_DOCK_EVENT = "android.intent.action.DOCK_EVENT";
    public static final java.lang.String ACTION_DOCK_IDLE = "android.intent.action.DOCK_IDLE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DOMAINS_NEED_VERIFICATION = "android.intent.action.DOMAINS_NEED_VERIFICATION";
    public static final java.lang.String ACTION_DREAMING_STARTED = "android.intent.action.DREAMING_STARTED";
    public static final java.lang.String ACTION_DREAMING_STOPPED = "android.intent.action.DREAMING_STOPPED";
    public static final java.lang.String ACTION_DYNAMIC_SENSOR_CHANGED = "android.intent.action.DYNAMIC_SENSOR_CHANGED";
    public static final java.lang.String ACTION_EDIT = "android.intent.action.EDIT";
    public static final java.lang.String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final java.lang.String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_FACTORY_RESET = "android.intent.action.FACTORY_RESET";
    public static final java.lang.String ACTION_FACTORY_TEST = "android.intent.action.FACTORY_TEST";
    public static final java.lang.String ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT";
    public static final java.lang.String ACTION_GET_RESTRICTION_ENTRIES = "android.intent.action.GET_RESTRICTION_ENTRIES";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_GLOBAL_BUTTON = "android.intent.action.GLOBAL_BUTTON";
    public static final java.lang.String ACTION_GTALK_SERVICE_CONNECTED = "android.intent.action.GTALK_CONNECTED";
    public static final java.lang.String ACTION_GTALK_SERVICE_DISCONNECTED = "android.intent.action.GTALK_DISCONNECTED";
    public static final java.lang.String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final java.lang.String ACTION_IDLE_MAINTENANCE_END = "android.intent.action.ACTION_IDLE_MAINTENANCE_END";
    public static final java.lang.String ACTION_IDLE_MAINTENANCE_START = "android.intent.action.ACTION_IDLE_MAINTENANCE_START";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_INCIDENT_REPORT_READY = "android.intent.action.INCIDENT_REPORT_READY";
    public static final java.lang.String ACTION_INPUT_METHOD_CHANGED = "android.intent.action.INPUT_METHOD_CHANGED";
    public static final java.lang.String ACTION_INSERT = "android.intent.action.INSERT";
    public static final java.lang.String ACTION_INSERT_OR_EDIT = "android.intent.action.INSERT_OR_EDIT";
    public static final java.lang.String ACTION_INSTALL_FAILURE = "android.intent.action.INSTALL_FAILURE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_INSTALL_INSTANT_APP_PACKAGE = "android.intent.action.INSTALL_INSTANT_APP_PACKAGE";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_INSTALL_PACKAGE = "android.intent.action.INSTALL_PACKAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_INSTANT_APP_RESOLVER_SETTINGS = "android.intent.action.INSTANT_APP_RESOLVER_SETTINGS";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_INTENT_FILTER_NEEDS_VERIFICATION = "android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION";
    public static final java.lang.String ACTION_LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE = "android.intent.action.LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_LOAD_DATA = "android.intent.action.LOAD_DATA";
    public static final java.lang.String ACTION_LOCALE_CHANGED = "android.intent.action.LOCALE_CHANGED";
    public static final java.lang.String ACTION_LOCKED_BOOT_COMPLETED = "android.intent.action.LOCKED_BOOT_COMPLETED";
    public static final java.lang.String ACTION_MAIN = "android.intent.action.MAIN";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MAIN_USER_LOCKSCREEN_KNOWLEDGE_FACTOR_CHANGED = "android.intent.action.MAIN_USER_LOCKSCREEN_KNOWLEDGE_FACTOR_CHANGED";
    public static final java.lang.String ACTION_MANAGED_PROFILE_ADDED = "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final java.lang.String ACTION_MANAGED_PROFILE_AVAILABLE = "android.intent.action.MANAGED_PROFILE_AVAILABLE";
    public static final java.lang.String ACTION_MANAGED_PROFILE_REMOVED = "android.intent.action.MANAGED_PROFILE_REMOVED";
    public static final java.lang.String ACTION_MANAGED_PROFILE_UNAVAILABLE = "android.intent.action.MANAGED_PROFILE_UNAVAILABLE";
    public static final java.lang.String ACTION_MANAGED_PROFILE_UNLOCKED = "android.intent.action.MANAGED_PROFILE_UNLOCKED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_APP_PERMISSION = "android.intent.action.MANAGE_APP_PERMISSION";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_MANAGE_APP_PERMISSIONS = "android.intent.action.MANAGE_APP_PERMISSIONS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_DEFAULT_APP = "android.intent.action.MANAGE_DEFAULT_APP";
    public static final java.lang.String ACTION_MANAGE_NETWORK_USAGE = "android.intent.action.MANAGE_NETWORK_USAGE";
    public static final java.lang.String ACTION_MANAGE_PACKAGE_STORAGE = "android.intent.action.MANAGE_PACKAGE_STORAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_PERMISSIONS = "android.intent.action.MANAGE_PERMISSIONS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_PERMISSION_APPS = "android.intent.action.MANAGE_PERMISSION_APPS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_PERMISSION_USAGE = "android.intent.action.MANAGE_PERMISSION_USAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_SPECIAL_APP_ACCESSES = "android.intent.action.MANAGE_SPECIAL_APP_ACCESSES";
    public static final java.lang.String ACTION_MANAGE_UNUSED_APPS = "android.intent.action.MANAGE_UNUSED_APPS";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_MASTER_CLEAR = "android.intent.action.MASTER_CLEAR";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MASTER_CLEAR_NOTIFICATION = "android.intent.action.MASTER_CLEAR_NOTIFICATION";
    public static final java.lang.String ACTION_MEDIA_BAD_REMOVAL = "android.intent.action.MEDIA_BAD_REMOVAL";
    public static final java.lang.String ACTION_MEDIA_BUTTON = "android.intent.action.MEDIA_BUTTON";
    public static final java.lang.String ACTION_MEDIA_CHECKING = "android.intent.action.MEDIA_CHECKING";
    public static final java.lang.String ACTION_MEDIA_EJECT = "android.intent.action.MEDIA_EJECT";
    public static final java.lang.String ACTION_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
    public static final java.lang.String ACTION_MEDIA_NOFS = "android.intent.action.MEDIA_NOFS";
    public static final java.lang.String ACTION_MEDIA_REMOVED = "android.intent.action.MEDIA_REMOVED";
    public static final java.lang.String ACTION_MEDIA_RESOURCE_GRANTED = "android.intent.action.MEDIA_RESOURCE_GRANTED";
    public static final java.lang.String ACTION_MEDIA_SCANNER_FINISHED = "android.intent.action.MEDIA_SCANNER_FINISHED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_MEDIA_SCANNER_SCAN_FILE = "android.intent.action.MEDIA_SCANNER_SCAN_FILE";
    public static final java.lang.String ACTION_MEDIA_SCANNER_STARTED = "android.intent.action.MEDIA_SCANNER_STARTED";
    public static final java.lang.String ACTION_MEDIA_SHARED = "android.intent.action.MEDIA_SHARED";
    public static final java.lang.String ACTION_MEDIA_UNMOUNTABLE = "android.intent.action.MEDIA_UNMOUNTABLE";
    public static final java.lang.String ACTION_MEDIA_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
    public static final java.lang.String ACTION_MEDIA_UNSHARED = "android.intent.action.MEDIA_UNSHARED";
    public static final java.lang.String ACTION_MY_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
    public static final java.lang.String ACTION_MY_PACKAGE_SUSPENDED = "android.intent.action.MY_PACKAGE_SUSPENDED";
    public static final java.lang.String ACTION_MY_PACKAGE_UNSUSPENDED = "android.intent.action.MY_PACKAGE_UNSUSPENDED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static final java.lang.String ACTION_OPEN_DOCUMENT = "android.intent.action.OPEN_DOCUMENT";
    public static final java.lang.String ACTION_OPEN_DOCUMENT_TREE = "android.intent.action.OPEN_DOCUMENT_TREE";
    public static final java.lang.String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    public static final java.lang.String ACTION_PACKAGES_SUSPENDED = "android.intent.action.PACKAGES_SUSPENDED";
    public static final java.lang.String ACTION_PACKAGES_SUSPENSION_CHANGED = "android.intent.action.PACKAGES_SUSPENSION_CHANGED";
    public static final java.lang.String ACTION_PACKAGES_UNSUSPENDED = "android.intent.action.PACKAGES_UNSUSPENDED";
    public static final java.lang.String ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    public static final java.lang.String ACTION_PACKAGE_CHANGED = "android.intent.action.PACKAGE_CHANGED";
    public static final java.lang.String ACTION_PACKAGE_DATA_CLEARED = "android.intent.action.PACKAGE_DATA_CLEARED";
    public static final java.lang.String ACTION_PACKAGE_ENABLE_ROLLBACK = "android.intent.action.PACKAGE_ENABLE_ROLLBACK";
    public static final java.lang.String ACTION_PACKAGE_FIRST_LAUNCH = "android.intent.action.PACKAGE_FIRST_LAUNCH";
    public static final java.lang.String ACTION_PACKAGE_FULLY_REMOVED = "android.intent.action.PACKAGE_FULLY_REMOVED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_PACKAGE_INSTALL = "android.intent.action.PACKAGE_INSTALL";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PACKAGE_NEEDS_INTEGRITY_VERIFICATION = "android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION";
    public static final java.lang.String ACTION_PACKAGE_NEEDS_VERIFICATION = "android.intent.action.PACKAGE_NEEDS_VERIFICATION";
    public static final java.lang.String ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
    public static final java.lang.String ACTION_PACKAGE_REMOVED_INTERNAL = "android.intent.action.PACKAGE_REMOVED_INTERNAL";
    public static final java.lang.String ACTION_PACKAGE_REPLACED = "android.intent.action.PACKAGE_REPLACED";
    public static final java.lang.String ACTION_PACKAGE_RESTARTED = "android.intent.action.PACKAGE_RESTARTED";
    public static final java.lang.String ACTION_PACKAGE_UNSTOPPED = "android.intent.action.PACKAGE_UNSTOPPED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PACKAGE_UNSUSPENDED_MANUALLY = "android.intent.action.PACKAGE_UNSUSPENDED_MANUALLY";
    public static final java.lang.String ACTION_PACKAGE_VERIFIED = "android.intent.action.PACKAGE_VERIFIED";
    public static final java.lang.String ACTION_PASTE = "android.intent.action.PASTE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PENDING_INCIDENT_REPORTS_CHANGED = "android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED";
    public static final java.lang.String ACTION_PICK = "android.intent.action.PICK";
    public static final java.lang.String ACTION_PICK_ACTIVITY = "android.intent.action.PICK_ACTIVITY";
    public static final java.lang.String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final java.lang.String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public static final java.lang.String ACTION_POWER_USAGE_SUMMARY = "android.intent.action.POWER_USAGE_SUMMARY";
    public static final java.lang.String ACTION_PREFERRED_ACTIVITY_CHANGED = "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_PRE_BOOT_COMPLETED = "android.intent.action.PRE_BOOT_COMPLETED";
    public static final java.lang.String ACTION_PROCESS_TEXT = "android.intent.action.PROCESS_TEXT";
    public static final java.lang.String ACTION_PROFILE_ACCESSIBLE = "android.intent.action.PROFILE_ACCESSIBLE";
    public static final java.lang.String ACTION_PROFILE_ADDED = "android.intent.action.PROFILE_ADDED";
    public static final java.lang.String ACTION_PROFILE_AVAILABLE = "android.intent.action.PROFILE_AVAILABLE";
    public static final java.lang.String ACTION_PROFILE_INACCESSIBLE = "android.intent.action.PROFILE_INACCESSIBLE";
    public static final java.lang.String ACTION_PROFILE_REMOVED = "android.intent.action.PROFILE_REMOVED";
    public static final java.lang.String ACTION_PROFILE_UNAVAILABLE = "android.intent.action.PROFILE_UNAVAILABLE";
    public static final java.lang.String ACTION_PROVIDER_CHANGED = "android.intent.action.PROVIDER_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_QUERY_PACKAGE_RESTART = "android.intent.action.QUERY_PACKAGE_RESTART";
    public static final java.lang.String ACTION_QUICK_CLOCK = "android.intent.action.QUICK_CLOCK";
    public static final java.lang.String ACTION_QUICK_VIEW = "android.intent.action.QUICK_VIEW";
    public static final java.lang.String ACTION_REBOOT = "android.intent.action.REBOOT";
    public static final java.lang.String ACTION_REMOTE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    public static final java.lang.String ACTION_REQUEST_SHUTDOWN = "com.android.internal.intent.action.REQUEST_SHUTDOWN";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_RESOLVE_INSTANT_APP_PACKAGE = "android.intent.action.RESOLVE_INSTANT_APP_PACKAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REVIEW_ACCESSIBILITY_SERVICES = "android.intent.action.REVIEW_ACCESSIBILITY_SERVICES";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REVIEW_APP_DATA_SHARING_UPDATES = "android.intent.action.REVIEW_APP_DATA_SHARING_UPDATES";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REVIEW_ONGOING_PERMISSION_USAGE = "android.intent.action.REVIEW_ONGOING_PERMISSION_USAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REVIEW_PERMISSIONS = "android.intent.action.REVIEW_PERMISSIONS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REVIEW_PERMISSION_HISTORY = "android.intent.action.REVIEW_PERMISSION_HISTORY";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REVIEW_PERMISSION_USAGE = "android.intent.action.REVIEW_PERMISSION_USAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ROLLBACK_COMMITTED = "android.intent.action.ROLLBACK_COMMITTED";
    public static final java.lang.String ACTION_RUN = "android.intent.action.RUN";
    public static final java.lang.String ACTION_SAFETY_CENTER = "android.intent.action.SAFETY_CENTER";
    public static final java.lang.String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    public static final java.lang.String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    public static final java.lang.String ACTION_SEARCH = "android.intent.action.SEARCH";
    public static final java.lang.String ACTION_SEARCH_LONG_PRESS = "android.intent.action.SEARCH_LONG_PRESS";
    public static final java.lang.String ACTION_SEND = "android.intent.action.SEND";
    public static final java.lang.String ACTION_SENDTO = "android.intent.action.SENDTO";
    public static final java.lang.String ACTION_SEND_MULTIPLE = "android.intent.action.SEND_MULTIPLE";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_SERVICE_STATE = "android.intent.action.SERVICE_STATE";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String ACTION_SETTING_RESTORED = "android.os.action.SETTING_RESTORED";
    public static final java.lang.String ACTION_SET_WALLPAPER = "android.intent.action.SET_WALLPAPER";
    public static final java.lang.String ACTION_SHOW_APP_INFO = "android.intent.action.SHOW_APP_INFO";
    public static final java.lang.String ACTION_SHOW_BRIGHTNESS_DIALOG = "com.android.intent.action.SHOW_BRIGHTNESS_DIALOG";
    public static final java.lang.String ACTION_SHOW_CONTRAST_DIALOG = "com.android.intent.action.SHOW_CONTRAST_DIALOG";
    public static final java.lang.String ACTION_SHOW_FOREGROUND_SERVICE_MANAGER = "android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER";
    public static final java.lang.String ACTION_SHOW_KEYBOARD_SHORTCUTS = "com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SHOW_SUSPENDED_APP_DETAILS = "android.intent.action.SHOW_SUSPENDED_APP_DETAILS";
    public static final java.lang.String ACTION_SHOW_WORK_APPS = "android.intent.action.SHOW_WORK_APPS";
    public static final java.lang.String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SPLIT_CONFIGURATION_CHANGED = "android.intent.action.SPLIT_CONFIGURATION_CHANGED";
    public static final java.lang.String ACTION_SYNC = "android.intent.action.SYNC";
    public static final java.lang.String ACTION_SYSTEM_TUTORIAL = "android.intent.action.SYSTEM_TUTORIAL";
    public static final java.lang.String ACTION_THERMAL_EVENT = "android.intent.action.THERMAL_EVENT";
    public static final java.lang.String ACTION_TIMEZONE_CHANGED = "android.intent.action.TIMEZONE_CHANGED";
    public static final java.lang.String ACTION_TIME_CHANGED = "android.intent.action.TIME_SET";
    public static final java.lang.String ACTION_TIME_TICK = "android.intent.action.TIME_TICK";
    public static final java.lang.String ACTION_TRANSLATE = "android.intent.action.TRANSLATE";
    public static final java.lang.String ACTION_UID_REMOVED = "android.intent.action.UID_REMOVED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_UMS_CONNECTED = "android.intent.action.UMS_CONNECTED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_UMS_DISCONNECTED = "android.intent.action.UMS_DISCONNECTED";
    public static final java.lang.String ACTION_UNARCHIVE_PACKAGE = "android.intent.action.UNARCHIVE_PACKAGE";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_UNINSTALL_PACKAGE = "android.intent.action.UNINSTALL_PACKAGE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_UPGRADE_SETUP = "android.intent.action.UPGRADE_SETUP";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
    public static final java.lang.String ACTION_USER_BACKGROUND = "android.intent.action.USER_BACKGROUND";
    public static final java.lang.String ACTION_USER_FOREGROUND = "android.intent.action.USER_FOREGROUND";
    public static final java.lang.String ACTION_USER_INFO_CHANGED = "android.intent.action.USER_INFO_CHANGED";
    public static final java.lang.String ACTION_USER_INITIALIZE = "android.intent.action.USER_INITIALIZE";
    public static final java.lang.String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USER_REMOVED = "android.intent.action.USER_REMOVED";
    public static final java.lang.String ACTION_USER_STARTED = "android.intent.action.USER_STARTED";
    public static final java.lang.String ACTION_USER_STARTING = "android.intent.action.USER_STARTING";
    public static final java.lang.String ACTION_USER_STOPPED = "android.intent.action.USER_STOPPED";
    public static final java.lang.String ACTION_USER_STOPPING = "android.intent.action.USER_STOPPING";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_USER_SWITCHED = "android.intent.action.USER_SWITCHED";
    public static final java.lang.String ACTION_USER_UNLOCKED = "android.intent.action.USER_UNLOCKED";
    public static final java.lang.String ACTION_VIEW = "android.intent.action.VIEW";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_VIEW_APP_FEATURES = "android.intent.action.VIEW_APP_FEATURES";
    public static final java.lang.String ACTION_VIEW_LOCUS = "android.intent.action.VIEW_LOCUS";
    public static final java.lang.String ACTION_VIEW_PERMISSION_USAGE = "android.intent.action.VIEW_PERMISSION_USAGE";
    public static final java.lang.String ACTION_VIEW_PERMISSION_USAGE_FOR_PERIOD = "android.intent.action.VIEW_PERMISSION_USAGE_FOR_PERIOD";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_VIEW_SAFETY_CENTER_QS = "android.intent.action.VIEW_SAFETY_CENTER_QS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_VOICE_ASSIST = "android.intent.action.VOICE_ASSIST";
    public static final java.lang.String ACTION_VOICE_COMMAND = "android.intent.action.VOICE_COMMAND";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_WALLPAPER_CHANGED = "android.intent.action.WALLPAPER_CHANGED";
    public static final java.lang.String ACTION_WEB_SEARCH = "android.intent.action.WEB_SEARCH";
    private static final java.lang.String ATTR_ACTION = "action";
    private static final java.lang.String ATTR_CATEGORY = "category";
    private static final java.lang.String ATTR_COMPONENT = "component";
    private static final java.lang.String ATTR_DATA = "data";
    private static final java.lang.String ATTR_FLAGS = "flags";
    private static final java.lang.String ATTR_IDENTIFIER = "ident";
    private static final java.lang.String ATTR_TYPE = "type";
    public static final int CAPTURE_CONTENT_FOR_NOTE_BLOCKED_BY_ADMIN = 4;
    public static final int CAPTURE_CONTENT_FOR_NOTE_FAILED = 1;
    public static final int CAPTURE_CONTENT_FOR_NOTE_SUCCESS = 0;
    public static final int CAPTURE_CONTENT_FOR_NOTE_USER_CANCELED = 2;
    public static final int CAPTURE_CONTENT_FOR_NOTE_WINDOW_MODE_UNSUPPORTED = 3;
    public static final java.lang.String CATEGORY_ACCESSIBILITY_SHORTCUT_TARGET = "android.intent.category.ACCESSIBILITY_SHORTCUT_TARGET";
    public static final java.lang.String CATEGORY_ALTERNATIVE = "android.intent.category.ALTERNATIVE";
    public static final java.lang.String CATEGORY_APP_BROWSER = "android.intent.category.APP_BROWSER";
    public static final java.lang.String CATEGORY_APP_CALCULATOR = "android.intent.category.APP_CALCULATOR";
    public static final java.lang.String CATEGORY_APP_CALENDAR = "android.intent.category.APP_CALENDAR";
    public static final java.lang.String CATEGORY_APP_CONTACTS = "android.intent.category.APP_CONTACTS";
    public static final java.lang.String CATEGORY_APP_EMAIL = "android.intent.category.APP_EMAIL";
    public static final java.lang.String CATEGORY_APP_FILES = "android.intent.category.APP_FILES";
    public static final java.lang.String CATEGORY_APP_FITNESS = "android.intent.category.APP_FITNESS";
    public static final java.lang.String CATEGORY_APP_GALLERY = "android.intent.category.APP_GALLERY";
    public static final java.lang.String CATEGORY_APP_MAPS = "android.intent.category.APP_MAPS";
    public static final java.lang.String CATEGORY_APP_MARKET = "android.intent.category.APP_MARKET";
    public static final java.lang.String CATEGORY_APP_MESSAGING = "android.intent.category.APP_MESSAGING";
    public static final java.lang.String CATEGORY_APP_MUSIC = "android.intent.category.APP_MUSIC";
    public static final java.lang.String CATEGORY_APP_WEATHER = "android.intent.category.APP_WEATHER";
    public static final java.lang.String CATEGORY_BROWSABLE = "android.intent.category.BROWSABLE";
    public static final java.lang.String CATEGORY_CAR_DOCK = "android.intent.category.CAR_DOCK";
    public static final java.lang.String CATEGORY_CAR_LAUNCHER = "android.intent.category.CAR_LAUNCHER";
    public static final java.lang.String CATEGORY_CAR_MODE = "android.intent.category.CAR_MODE";
    public static final java.lang.String CATEGORY_COMMUNAL_MODE = "android.intent.category.COMMUNAL_MODE";
    public static final java.lang.String CATEGORY_DEFAULT = "android.intent.category.DEFAULT";
    public static final java.lang.String CATEGORY_DESK_DOCK = "android.intent.category.DESK_DOCK";
    public static final java.lang.String CATEGORY_DEVELOPMENT_PREFERENCE = "android.intent.category.DEVELOPMENT_PREFERENCE";
    public static final java.lang.String CATEGORY_EMBED = "android.intent.category.EMBED";
    public static final java.lang.String CATEGORY_FRAMEWORK_INSTRUMENTATION_TEST = "android.intent.category.FRAMEWORK_INSTRUMENTATION_TEST";
    public static final java.lang.String CATEGORY_HE_DESK_DOCK = "android.intent.category.HE_DESK_DOCK";
    public static final java.lang.String CATEGORY_HOME = "android.intent.category.HOME";
    public static final java.lang.String CATEGORY_HOME_MAIN = "android.intent.category.HOME_MAIN";
    public static final java.lang.String CATEGORY_INFO = "android.intent.category.INFO";
    public static final java.lang.String CATEGORY_LAUNCHER = "android.intent.category.LAUNCHER";
    public static final java.lang.String CATEGORY_LAUNCHER_APP = "android.intent.category.LAUNCHER_APP";
    public static final java.lang.String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";

    @android.annotation.SystemApi
    public static final java.lang.String CATEGORY_LEANBACK_SETTINGS = "android.intent.category.LEANBACK_SETTINGS";
    public static final java.lang.String CATEGORY_LE_DESK_DOCK = "android.intent.category.LE_DESK_DOCK";
    public static final java.lang.String CATEGORY_MONKEY = "android.intent.category.MONKEY";
    public static final java.lang.String CATEGORY_OPENABLE = "android.intent.category.OPENABLE";
    public static final java.lang.String CATEGORY_PREFERENCE = "android.intent.category.PREFERENCE";
    public static final java.lang.String CATEGORY_SAMPLE_CODE = "android.intent.category.SAMPLE_CODE";
    public static final java.lang.String CATEGORY_SECONDARY_HOME = "android.intent.category.SECONDARY_HOME";
    public static final java.lang.String CATEGORY_SELECTED_ALTERNATIVE = "android.intent.category.SELECTED_ALTERNATIVE";
    public static final java.lang.String CATEGORY_SETUP_WIZARD = "android.intent.category.SETUP_WIZARD";
    public static final java.lang.String CATEGORY_TAB = "android.intent.category.TAB";
    public static final java.lang.String CATEGORY_TEST = "android.intent.category.TEST";
    public static final java.lang.String CATEGORY_TYPED_OPENABLE = "android.intent.category.TYPED_OPENABLE";
    public static final java.lang.String CATEGORY_UNIT_TEST = "android.intent.category.UNIT_TEST";
    public static final java.lang.String CATEGORY_VOICE = "android.intent.category.VOICE";
    public static final java.lang.String CATEGORY_VR_HOME = "android.intent.category.VR_HOME";
    public static final int CHOOSER_CONTENT_TYPE_ALBUM = 1;
    private static final int COPY_MODE_ALL = 0;
    private static final int COPY_MODE_FILTER = 1;
    private static final int COPY_MODE_HISTORY = 2;
    public static final android.os.Parcelable.Creator<android.content.Intent> CREATOR = new android.os.Parcelable.Creator<android.content.Intent>() { // from class: android.content.Intent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.Intent createFromParcel(android.os.Parcel parcel) {
            return new android.content.Intent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.Intent[] newArray(int i) {
            return new android.content.Intent[i];
        }
    };
    public static final int EXTENDED_FLAG_FILTER_MISMATCH = 1;
    public static final java.lang.String EXTRA_ALARM_COUNT = "android.intent.extra.ALARM_COUNT";
    public static final java.lang.String EXTRA_ALLOW_MULTIPLE = "android.intent.extra.ALLOW_MULTIPLE";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_ALLOW_REPLACE = "android.intent.extra.ALLOW_REPLACE";
    public static final java.lang.String EXTRA_ALTERNATE_INTENTS = "android.intent.extra.ALTERNATE_INTENTS";
    public static final java.lang.String EXTRA_ARCHIVAL = "android.intent.extra.ARCHIVAL";
    public static final java.lang.String EXTRA_ASSIST_CONTEXT = "android.intent.extra.ASSIST_CONTEXT";
    public static final java.lang.String EXTRA_ASSIST_INPUT_DEVICE_ID = "android.intent.extra.ASSIST_INPUT_DEVICE_ID";
    public static final java.lang.String EXTRA_ASSIST_INPUT_HINT_KEYBOARD = "android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD";
    public static final java.lang.String EXTRA_ASSIST_PACKAGE = "android.intent.extra.ASSIST_PACKAGE";
    public static final java.lang.String EXTRA_ASSIST_UID = "android.intent.extra.ASSIST_UID";
    public static final java.lang.String EXTRA_ATTRIBUTION_TAGS = "android.intent.extra.ATTRIBUTION_TAGS";
    public static final java.lang.String EXTRA_AUTO_LAUNCH_SINGLE_CHOICE = "android.intent.extra.AUTO_LAUNCH_SINGLE_CHOICE";
    public static final java.lang.String EXTRA_BCC = "android.intent.extra.BCC";
    public static final java.lang.String EXTRA_BRIGHTNESS_DIALOG_IS_FULL_WIDTH = "android.intent.extra.BRIGHTNESS_DIALOG_IS_FULL_WIDTH";
    public static final java.lang.String EXTRA_BUG_REPORT = "android.intent.extra.BUG_REPORT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALLING_PACKAGE = "android.intent.extra.CALLING_PACKAGE";
    public static final java.lang.String EXTRA_CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE = "android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE";
    public static final java.lang.String EXTRA_CC = "android.intent.extra.CC";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CDMA_DEFAULT_ROAMING_INDICATOR = "cdmaDefaultRoamingIndicator";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CDMA_ROAMING_INDICATOR = "cdmaRoamingIndicator";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CHANGED_COMPONENT_NAME = "android.intent.extra.changed_component_name";
    public static final java.lang.String EXTRA_CHANGED_COMPONENT_NAME_LIST = "android.intent.extra.changed_component_name_list";
    public static final java.lang.String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final java.lang.String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final java.lang.String EXTRA_CHOOSER_ADDITIONAL_CONTENT_URI = "android.intent.extra.CHOOSER_ADDITIONAL_CONTENT_URI";
    public static final java.lang.String EXTRA_CHOOSER_CONTENT_TYPE_HINT = "android.intent.extra.CHOOSER_CONTENT_TYPE_HINT";
    public static final java.lang.String EXTRA_CHOOSER_CUSTOM_ACTIONS = "android.intent.extra.CHOOSER_CUSTOM_ACTIONS";
    public static final java.lang.String EXTRA_CHOOSER_FOCUSED_ITEM_POSITION = "android.intent.extra.CHOOSER_FOCUSED_ITEM_POSITION";
    public static final java.lang.String EXTRA_CHOOSER_MODIFY_SHARE_ACTION = "android.intent.extra.CHOOSER_MODIFY_SHARE_ACTION";
    public static final java.lang.String EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER = "android.intent.extra.CHOOSER_REFINEMENT_INTENT_SENDER";
    public static final java.lang.String EXTRA_CHOOSER_RESULT = "android.intent.extra.CHOOSER_RESULT";
    public static final java.lang.String EXTRA_CHOOSER_RESULT_INTENT_SENDER = "android.intent.extra.CHOOSER_RESULT_INTENT_SENDER";
    public static final java.lang.String EXTRA_CHOOSER_TARGETS = "android.intent.extra.CHOOSER_TARGETS";
    public static final java.lang.String EXTRA_CHOSEN_COMPONENT = "android.intent.extra.CHOSEN_COMPONENT";
    public static final java.lang.String EXTRA_CHOSEN_COMPONENT_INTENT_SENDER = "android.intent.extra.CHOSEN_COMPONENT_INTENT_SENDER";
    public static final java.lang.String EXTRA_CLIENT_INTENT = "android.intent.extra.client_intent";
    public static final java.lang.String EXTRA_CLIENT_LABEL = "android.intent.extra.client_label";
    public static final java.lang.String EXTRA_COMPONENT_NAME = "android.intent.extra.COMPONENT_NAME";
    public static final java.lang.String EXTRA_CONTENT_ANNOTATIONS = "android.intent.extra.CONTENT_ANNOTATIONS";
    public static final java.lang.String EXTRA_CONTENT_QUERY = "android.intent.extra.CONTENT_QUERY";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CSS_INDICATOR = "cssIndicator";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DATA_OPERATOR_ALPHA_LONG = "data-operator-alpha-long";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DATA_OPERATOR_ALPHA_SHORT = "data-operator-alpha-short";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DATA_OPERATOR_NUMERIC = "data-operator-numeric";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DATA_RADIO_TECH = "dataRadioTechnology";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DATA_REG_STATE = "dataRegState";
    public static final java.lang.String EXTRA_DATA_REMOVED = "android.intent.extra.DATA_REMOVED";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DATA_ROAMING_TYPE = "dataRoamingType";
    public static final java.lang.String EXTRA_DISTRACTION_RESTRICTIONS = "android.intent.extra.distraction_restrictions";
    public static final java.lang.String EXTRA_DOCK_STATE = "android.intent.extra.DOCK_STATE";
    public static final int EXTRA_DOCK_STATE_CAR = 2;
    public static final int EXTRA_DOCK_STATE_DESK = 1;
    public static final int EXTRA_DOCK_STATE_HE_DESK = 4;
    public static final int EXTRA_DOCK_STATE_LE_DESK = 3;
    public static final int EXTRA_DOCK_STATE_UNDOCKED = 0;
    public static final java.lang.String EXTRA_DONT_KILL_APP = "android.intent.extra.DONT_KILL_APP";
    public static final java.lang.String EXTRA_DURATION_MILLIS = "android.intent.extra.DURATION_MILLIS";
    public static final java.lang.String EXTRA_EMAIL = "android.intent.extra.EMAIL";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_EMERGENCY_ONLY = "emergencyOnly";
    public static final java.lang.String EXTRA_END_TIME = "android.intent.extra.END_TIME";
    public static final java.lang.String EXTRA_EXCLUDE_COMPONENTS = "android.intent.extra.EXCLUDE_COMPONENTS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_FORCE_FACTORY_RESET = "android.intent.extra.FORCE_FACTORY_RESET";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_FORCE_MASTER_CLEAR = "android.intent.extra.FORCE_MASTER_CLEAR";
    public static final java.lang.String EXTRA_FROM_STORAGE = "android.intent.extra.FROM_STORAGE";
    public static final java.lang.String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final java.lang.String EXTRA_INDEX = "android.intent.extra.INDEX";
    public static final java.lang.String EXTRA_INITIAL_INTENTS = "android.intent.extra.INITIAL_INTENTS";
    public static final java.lang.String EXTRA_INSTALLER_PACKAGE_NAME = "android.intent.extra.INSTALLER_PACKAGE_NAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTALL_RESULT = "android.intent.extra.INSTALL_RESULT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_ACTION = "android.intent.extra.INSTANT_APP_ACTION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_BUNDLES = "android.intent.extra.INSTANT_APP_BUNDLES";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_EXTRAS = "android.intent.extra.INSTANT_APP_EXTRAS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_FAILURE = "android.intent.extra.INSTANT_APP_FAILURE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_HOSTNAME = "android.intent.extra.INSTANT_APP_HOSTNAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_SUCCESS = "android.intent.extra.INSTANT_APP_SUCCESS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_INSTANT_APP_TOKEN = "android.intent.extra.INSTANT_APP_TOKEN";
    public static final java.lang.String EXTRA_INTENT = "android.intent.extra.INTENT";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_IS_DATA_ROAMING_FROM_REGISTRATION = "isDataRoamingFromRegistration";
    public static final java.lang.String EXTRA_IS_RESTORE = "android.intent.extra.IS_RESTORE";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_IS_USING_CARRIER_AGGREGATION = "isUsingCarrierAggregation";
    public static final java.lang.String EXTRA_KEY_CONFIRM = "android.intent.extra.KEY_CONFIRM";
    public static final java.lang.String EXTRA_KEY_EVENT = "android.intent.extra.KEY_EVENT";
    public static final java.lang.String EXTRA_LOCALE_LIST = "android.intent.extra.LOCALE_LIST";
    public static final java.lang.String EXTRA_LOCAL_ONLY = "android.intent.extra.LOCAL_ONLY";
    public static final java.lang.String EXTRA_LOCUS_ID = "android.intent.extra.LOCUS_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_LONG_VERSION_CODE = "android.intent.extra.LONG_VERSION_CODE";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_LTE_EARFCN_RSRP_BOOST = "LteEarfcnRsrpBoost";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_MANUAL = "manual";
    public static final java.lang.String EXTRA_MEDIA_RESOURCE_TYPE = "android.intent.extra.MEDIA_RESOURCE_TYPE";
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_AUDIO_CODEC = 1;
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_VIDEO_CODEC = 0;
    public static final java.lang.String EXTRA_METADATA_TEXT = "android.intent.extra.METADATA_TEXT";
    public static final java.lang.String EXTRA_MIME_TYPES = "android.intent.extra.MIME_TYPES";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_NETWORK_ID = "networkId";
    public static final java.lang.String EXTRA_NOT_UNKNOWN_SOURCE = "android.intent.extra.NOT_UNKNOWN_SOURCE";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_OPERATOR_ALPHA_LONG = "operator-alpha-long";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_OPERATOR_ALPHA_SHORT = "operator-alpha-short";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_OPERATOR_NUMERIC = "operator-numeric";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ORIGINATING_UID = "android.intent.extra.ORIGINATING_UID";
    public static final java.lang.String EXTRA_ORIGINATING_URI = "android.intent.extra.ORIGINATING_URI";
    public static final java.lang.String EXTRA_PACKAGES = "android.intent.extra.PACKAGES";
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    public static final java.lang.String EXTRA_PERMISSION_GROUP_NAME = "android.intent.extra.PERMISSION_GROUP_NAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PERMISSION_NAME = "android.intent.extra.PERMISSION_NAME";
    public static final java.lang.String EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
    public static final java.lang.String EXTRA_PROCESS_TEXT = "android.intent.extra.PROCESS_TEXT";
    public static final java.lang.String EXTRA_PROCESS_TEXT_READONLY = "android.intent.extra.PROCESS_TEXT_READONLY";
    public static final java.lang.String EXTRA_QUARANTINED = "android.intent.extra.quarantined";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_QUICK_VIEW_ADVANCED = "android.intent.extra.QUICK_VIEW_ADVANCED";
    public static final java.lang.String EXTRA_QUICK_VIEW_FEATURES = "android.intent.extra.QUICK_VIEW_FEATURES";
    public static final java.lang.String EXTRA_QUIET_MODE = "android.intent.extra.QUIET_MODE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_REASON = "android.intent.extra.REASON";
    public static final java.lang.String EXTRA_REBROADCAST_ON_UNLOCK = "rebroadcastOnUnlock";
    public static final java.lang.String EXTRA_REFERRER = "android.intent.extra.REFERRER";
    public static final java.lang.String EXTRA_REFERRER_NAME = "android.intent.extra.REFERRER_NAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_REMOTE_CALLBACK = "android.intent.extra.REMOTE_CALLBACK";
    public static final java.lang.String EXTRA_REMOTE_INTENT_TOKEN = "android.intent.extra.remote_intent_token";
    public static final java.lang.String EXTRA_REMOVED_FOR_ALL_USERS = "android.intent.extra.REMOVED_FOR_ALL_USERS";
    public static final java.lang.String EXTRA_REPLACEMENT_EXTRAS = "android.intent.extra.REPLACEMENT_EXTRAS";
    public static final java.lang.String EXTRA_REPLACING = "android.intent.extra.REPLACING";
    public static final java.lang.String EXTRA_RESTRICTIONS_BUNDLE = "android.intent.extra.restrictions_bundle";
    public static final java.lang.String EXTRA_RESTRICTIONS_INTENT = "android.intent.extra.restrictions_intent";
    public static final java.lang.String EXTRA_RESTRICTIONS_LIST = "android.intent.extra.restrictions_list";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_RESULT_NEEDED = "android.intent.extra.RESULT_NEEDED";
    public static final java.lang.String EXTRA_RESULT_RECEIVER = "android.intent.extra.RESULT_RECEIVER";
    public static final java.lang.String EXTRA_RETURN_RESULT = "android.intent.extra.RETURN_RESULT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ROLE_NAME = "android.intent.extra.ROLE_NAME";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String EXTRA_SETTING_NAME = "setting_name";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String EXTRA_SETTING_NEW_VALUE = "new_value";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String EXTRA_SETTING_PREVIOUS_VALUE = "previous_value";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String EXTRA_SETTING_RESTORED_FROM_SDK_INT = "restored_from_sdk_int";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_SHORTCUT_ICON = "android.intent.extra.shortcut.ICON";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_SHORTCUT_ICON_RESOURCE = "android.intent.extra.shortcut.ICON_RESOURCE";
    public static final java.lang.String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_SHORTCUT_INTENT = "android.intent.extra.shortcut.INTENT";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_SHORTCUT_NAME = "android.intent.extra.shortcut.NAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SHOWING_ATTRIBUTION = "android.intent.extra.SHOWING_ATTRIBUTION";
    public static final java.lang.String EXTRA_SHOW_WIPE_PROGRESS = "android.intent.extra.SHOW_WIPE_PROGRESS";
    public static final java.lang.String EXTRA_SHUTDOWN_USERSPACE_ONLY = "android.intent.extra.SHUTDOWN_USERSPACE_ONLY";
    public static final java.lang.String EXTRA_SIM_ACTIVATION_RESPONSE = "android.intent.extra.SIM_ACTIVATION_RESPONSE";
    public static final java.lang.String EXTRA_SIM_LOCKED_REASON = "reason";
    public static final java.lang.String EXTRA_SIM_STATE = "ss";
    public static final java.lang.String EXTRA_SPLIT_NAME = "android.intent.extra.SPLIT_NAME";
    public static final java.lang.String EXTRA_START_TIME = "android.intent.extra.START_TIME";
    public static final java.lang.String EXTRA_STREAM = "android.intent.extra.STREAM";
    public static final java.lang.String EXTRA_SUBJECT = "android.intent.extra.SUBJECT";
    public static final java.lang.String EXTRA_SUSPENDED_PACKAGE_EXTRAS = "android.intent.extra.SUSPENDED_PACKAGE_EXTRAS";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_SYSTEM_ID = "systemId";
    public static final java.lang.String EXTRA_SYSTEM_UPDATE_UNINSTALL = "android.intent.extra.SYSTEM_UPDATE_UNINSTALL";
    public static final java.lang.String EXTRA_TASK_ID = "android.intent.extra.TASK_ID";
    public static final java.lang.String EXTRA_TEMPLATE = "android.intent.extra.TEMPLATE";
    public static final java.lang.String EXTRA_TEXT = "android.intent.extra.TEXT";
    public static final java.lang.String EXTRA_THERMAL_STATE = "android.intent.extra.THERMAL_STATE";
    public static final int EXTRA_THERMAL_STATE_EXCEEDED = 2;
    public static final int EXTRA_THERMAL_STATE_NORMAL = 0;
    public static final int EXTRA_THERMAL_STATE_WARNING = 1;
    public static final java.lang.String EXTRA_TIME = "android.intent.extra.TIME";
    public static final java.lang.String EXTRA_TIMEZONE = "time-zone";
    public static final java.lang.String EXTRA_TIME_PREF_24_HOUR_FORMAT = "android.intent.extra.TIME_PREF_24_HOUR_FORMAT";
    public static final int EXTRA_TIME_PREF_VALUE_USE_12_HOUR = 0;
    public static final int EXTRA_TIME_PREF_VALUE_USE_24_HOUR = 1;
    public static final int EXTRA_TIME_PREF_VALUE_USE_LOCALE_DEFAULT = 2;
    public static final java.lang.String EXTRA_TITLE = "android.intent.extra.TITLE";
    public static final java.lang.String EXTRA_UID = "android.intent.extra.UID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_UNINSTALL_ALL_USERS = "android.intent.extra.UNINSTALL_ALL_USERS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_UNKNOWN_INSTANT_APP = "android.intent.extra.UNKNOWN_INSTANT_APP";
    public static final java.lang.String EXTRA_USER = "android.intent.extra.USER";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
    public static final java.lang.String EXTRA_USER_ID = "android.intent.extra.USER_ID";
    public static final java.lang.String EXTRA_USER_INITIATED = "android.intent.extra.USER_INITIATED";
    public static final java.lang.String EXTRA_USER_REQUESTED_SHUTDOWN = "android.intent.extra.USER_REQUESTED_SHUTDOWN";
    public static final java.lang.String EXTRA_USE_STYLUS_MODE = "android.intent.extra.USE_STYLUS_MODE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_VERIFICATION_BUNDLE = "android.intent.extra.VERIFICATION_BUNDLE";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_VERSION_CODE = "android.intent.extra.VERSION_CODE";
    public static final java.lang.String EXTRA_VISIBILITY_ALLOW_LIST = "android.intent.extra.VISIBILITY_ALLOW_LIST";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_VOICE_RADIO_TECH = "radioTechnology";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_VOICE_REG_STATE = "voiceRegState";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_VOICE_ROAMING_TYPE = "voiceRoamingType";
    public static final java.lang.String EXTRA_WIPE_ESIMS = "com.android.internal.intent.extra.WIPE_ESIMS";
    public static final java.lang.String EXTRA_WIPE_EXTERNAL_STORAGE = "android.intent.extra.WIPE_EXTERNAL_STORAGE";
    public static final int FILL_IN_ACTION = 1;
    public static final int FILL_IN_CATEGORIES = 4;
    public static final int FILL_IN_CLIP_DATA = 128;
    public static final int FILL_IN_COMPONENT = 8;
    public static final int FILL_IN_DATA = 2;
    public static final int FILL_IN_IDENTIFIER = 256;
    public static final int FILL_IN_PACKAGE = 16;
    public static final int FILL_IN_SELECTOR = 64;
    public static final int FILL_IN_SOURCE_BOUNDS = 32;
    public static final int FLAG_ACTIVITY_BROUGHT_TO_FRONT = 4194304;
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;

    @java.lang.Deprecated
    public static final int FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET = 524288;
    public static final int FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS = 8388608;
    public static final int FLAG_ACTIVITY_FORWARD_RESULT = 33554432;
    public static final int FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY = 1048576;
    public static final int FLAG_ACTIVITY_LAUNCH_ADJACENT = 4096;
    public static final int FLAG_ACTIVITY_MATCH_EXTERNAL = 2048;
    public static final int FLAG_ACTIVITY_MULTIPLE_TASK = 134217728;
    public static final int FLAG_ACTIVITY_NEW_DOCUMENT = 524288;
    public static final int FLAG_ACTIVITY_NEW_TASK = 268435456;
    public static final int FLAG_ACTIVITY_NO_ANIMATION = 65536;
    public static final int FLAG_ACTIVITY_NO_HISTORY = 1073741824;
    public static final int FLAG_ACTIVITY_NO_USER_ACTION = 262144;
    public static final int FLAG_ACTIVITY_PREVIOUS_IS_TOP = 16777216;
    public static final int FLAG_ACTIVITY_REORDER_TO_FRONT = 131072;
    public static final int FLAG_ACTIVITY_REQUIRE_DEFAULT = 512;
    public static final int FLAG_ACTIVITY_REQUIRE_NON_BROWSER = 1024;
    public static final int FLAG_ACTIVITY_RESET_TASK_IF_NEEDED = 2097152;
    public static final int FLAG_ACTIVITY_RETAIN_IN_RECENTS = 8192;
    public static final int FLAG_ACTIVITY_SINGLE_TOP = 536870912;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    public static final int FLAG_DEBUG_LOG_RESOLUTION = 8;

    @java.lang.Deprecated
    public static final int FLAG_DEBUG_TRIAGED_MISSING = 256;
    public static final int FLAG_DIRECT_BOOT_AUTO = 256;
    public static final int FLAG_EXCLUDE_STOPPED_PACKAGES = 16;
    public static final int FLAG_FROM_BACKGROUND = 4;
    public static final int FLAG_GRANT_PERSISTABLE_URI_PERMISSION = 64;
    public static final int FLAG_GRANT_PREFIX_URI_PERMISSION = 128;
    public static final int FLAG_GRANT_READ_URI_PERMISSION = 1;
    public static final int FLAG_GRANT_WRITE_URI_PERMISSION = 2;
    public static final int FLAG_IGNORE_EPHEMERAL = Integer.MIN_VALUE;
    public static final int FLAG_INCLUDE_STOPPED_PACKAGES = 32;
    public static final int FLAG_RECEIVER_BOOT_UPGRADE = 33554432;
    public static final int FLAG_RECEIVER_EXCLUDE_BACKGROUND = 8388608;
    public static final int FLAG_RECEIVER_FOREGROUND = 268435456;
    public static final int FLAG_RECEIVER_FROM_SHELL = 4194304;

    @android.annotation.SystemApi
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 16777216;
    public static final int FLAG_RECEIVER_NO_ABORT = 134217728;
    public static final int FLAG_RECEIVER_OFFLOAD = Integer.MIN_VALUE;
    public static final int FLAG_RECEIVER_OFFLOAD_FOREGROUND = 2048;
    public static final int FLAG_RECEIVER_REGISTERED_ONLY = 1073741824;

    @android.annotation.SystemApi
    public static final int FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT = 67108864;
    public static final int FLAG_RECEIVER_REPLACE_PENDING = 536870912;
    public static final int FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS = 2097152;
    public static final int IMMUTABLE_FLAGS = 195;
    private static final int LOCAL_FLAG_FROM_COPY = 1;
    private static final int LOCAL_FLAG_FROM_PARCEL = 2;
    private static final int LOCAL_FLAG_FROM_PROTECTED_COMPONENT = 4;
    public static final int LOCAL_FLAG_FROM_SYSTEM = 32;
    private static final int LOCAL_FLAG_FROM_URI = 16;
    private static final int LOCAL_FLAG_UNFILTERED_EXTRAS = 8;
    public static final java.lang.String METADATA_DOCK_HOME = "android.dock_home";

    @android.annotation.SystemApi
    public static final java.lang.String METADATA_SETUP_VERSION = "android.SETUP_VERSION";
    public static final java.lang.String SIM_ABSENT_ON_PERM_DISABLED = "PERM_DISABLED";
    public static final java.lang.String SIM_LOCKED_NETWORK = "NETWORK";
    public static final java.lang.String SIM_LOCKED_ON_PIN = "PIN";
    public static final java.lang.String SIM_LOCKED_ON_PUK = "PUK";
    public static final java.lang.String SIM_STATE_ABSENT = "ABSENT";
    public static final java.lang.String SIM_STATE_CARD_IO_ERROR = "CARD_IO_ERROR";
    public static final java.lang.String SIM_STATE_CARD_RESTRICTED = "CARD_RESTRICTED";
    public static final java.lang.String SIM_STATE_IMSI = "IMSI";
    public static final java.lang.String SIM_STATE_LOADED = "LOADED";
    public static final java.lang.String SIM_STATE_LOCKED = "LOCKED";
    public static final java.lang.String SIM_STATE_NOT_READY = "NOT_READY";
    public static final java.lang.String SIM_STATE_PRESENT = "PRESENT";
    public static final java.lang.String SIM_STATE_READY = "READY";
    public static final java.lang.String SIM_STATE_UNKNOWN = "UNKNOWN";
    private static final java.lang.String TAG = "Intent";
    private static final java.lang.String TAG_CATEGORIES = "categories";
    private static final java.lang.String TAG_EXTRA = "extra";
    public static final int URI_ALLOW_UNSAFE = 4;
    public static final int URI_ANDROID_APP_SCHEME = 2;
    public static final int URI_INTENT_SCHEME = 1;
    private java.lang.String mAction;
    private android.util.ArraySet<java.lang.String> mCategories;
    private android.content.ClipData mClipData;
    private android.content.ComponentName mComponent;
    private int mContentUserHint;
    private android.net.Uri mData;
    private int mExtendedFlags;
    private android.os.Bundle mExtras;
    private int mFlags;
    private java.lang.String mIdentifier;
    private java.lang.String mLaunchToken;
    private int mLocalFlags;
    private android.content.Intent mOriginalIntent;
    private java.lang.String mPackage;
    private android.content.Intent mSelector;
    private android.graphics.Rect mSourceBounds;
    private java.lang.String mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AccessUriMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CaptureContentForNoteStatusCodes {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChooserContentType {
    }

    public interface CommandOptionHandler {
        boolean handleOption(java.lang.String str, android.os.ShellCommand shellCommand);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CopyMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ExtendedFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FillInFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GrantUriMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MutableFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UriFlags {
    }

    public static class ShortcutIconResource implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.Intent.ShortcutIconResource> CREATOR = new android.os.Parcelable.Creator<android.content.Intent.ShortcutIconResource>() { // from class: android.content.Intent.ShortcutIconResource.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.Intent.ShortcutIconResource createFromParcel(android.os.Parcel parcel) {
                android.content.Intent.ShortcutIconResource shortcutIconResource = new android.content.Intent.ShortcutIconResource();
                shortcutIconResource.packageName = parcel.readString8();
                shortcutIconResource.resourceName = parcel.readString8();
                return shortcutIconResource;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.Intent.ShortcutIconResource[] newArray(int i) {
                return new android.content.Intent.ShortcutIconResource[i];
            }
        };
        public java.lang.String packageName;
        public java.lang.String resourceName;

        public static android.content.Intent.ShortcutIconResource fromContext(android.content.Context context, int i) {
            android.content.Intent.ShortcutIconResource shortcutIconResource = new android.content.Intent.ShortcutIconResource();
            shortcutIconResource.packageName = context.getPackageName();
            shortcutIconResource.resourceName = context.getResources().getResourceName(i);
            return shortcutIconResource;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.packageName);
            parcel.writeString8(this.resourceName);
        }

        public java.lang.String toString() {
            return this.resourceName;
        }
    }

    public static android.content.Intent createChooser(android.content.Intent intent, java.lang.CharSequence charSequence) {
        return createChooser(intent, charSequence, null);
    }

    public static android.content.Intent createChooser(android.content.Intent intent, java.lang.CharSequence charSequence, android.content.IntentSender intentSender) {
        java.lang.String[] strArr;
        android.content.Intent intent2 = new android.content.Intent(ACTION_CHOOSER);
        intent2.putExtra(EXTRA_INTENT, intent);
        if (charSequence != null) {
            intent2.putExtra(EXTRA_TITLE, charSequence);
        }
        if (intentSender != null) {
            if (android.service.chooser.Flags.enableChooserResult()) {
                intent2.putExtra(EXTRA_CHOOSER_RESULT_INTENT_SENDER, intentSender);
            } else {
                intent2.putExtra(EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, intentSender);
            }
        }
        int flags = intent.getFlags() & 195;
        if (flags != 0) {
            android.content.ClipData clipData = intent.getClipData();
            if (clipData == null && intent.getData() != null) {
                android.content.ClipData.Item item = new android.content.ClipData.Item(intent.getData());
                if (intent.getType() != null) {
                    strArr = new java.lang.String[]{intent.getType()};
                } else {
                    strArr = new java.lang.String[0];
                }
                clipData = new android.content.ClipData(null, strArr, item);
            }
            if (clipData != null) {
                intent2.setClipData(clipData);
                intent2.addFlags(flags);
            }
        }
        return intent2;
    }

    public static boolean isAccessUriMode(int i) {
        return (i & 3) != 0;
    }

    public Intent() {
        this.mContentUserHint = -2;
    }

    public Intent(android.content.Intent intent) {
        this(intent, 0);
    }

    private Intent(android.content.Intent intent, int i) {
        this.mContentUserHint = -2;
        this.mAction = intent.mAction;
        this.mData = intent.mData;
        this.mType = intent.mType;
        this.mIdentifier = intent.mIdentifier;
        this.mPackage = intent.mPackage;
        this.mComponent = intent.mComponent;
        this.mOriginalIntent = intent.mOriginalIntent;
        if (intent.mCategories != null) {
            this.mCategories = new android.util.ArraySet<>((android.util.ArraySet) intent.mCategories);
        }
        this.mLocalFlags = intent.mLocalFlags;
        this.mLocalFlags |= 1;
        if (i != 1) {
            this.mFlags = intent.mFlags;
            this.mExtendedFlags = intent.mExtendedFlags;
            this.mContentUserHint = intent.mContentUserHint;
            this.mLaunchToken = intent.mLaunchToken;
            if (intent.mSourceBounds != null) {
                this.mSourceBounds = new android.graphics.Rect(intent.mSourceBounds);
            }
            if (intent.mSelector != null) {
                this.mSelector = new android.content.Intent(intent.mSelector);
            }
            if (i != 2) {
                if (intent.mExtras != null) {
                    this.mExtras = new android.os.Bundle(intent.mExtras);
                }
                if (intent.mClipData != null) {
                    this.mClipData = new android.content.ClipData(intent.mClipData);
                    return;
                }
                return;
            }
            if (intent.mExtras != null && !intent.mExtras.isDefinitelyEmpty()) {
                this.mExtras = android.os.Bundle.STRIPPED;
            }
        }
    }

    public java.lang.Object clone() {
        return new android.content.Intent(this);
    }

    public android.content.Intent cloneFilter() {
        return new android.content.Intent(this, 1);
    }

    public Intent(java.lang.String str) {
        this.mContentUserHint = -2;
        setAction(str);
    }

    public Intent(java.lang.String str, android.net.Uri uri) {
        this.mContentUserHint = -2;
        setAction(str);
        this.mData = uri;
    }

    public Intent(android.content.Context context, java.lang.Class<?> cls) {
        this.mContentUserHint = -2;
        this.mComponent = new android.content.ComponentName(context, cls);
    }

    public Intent(java.lang.String str, android.net.Uri uri, android.content.Context context, java.lang.Class<?> cls) {
        this.mContentUserHint = -2;
        setAction(str);
        this.mData = uri;
        this.mComponent = new android.content.ComponentName(context, cls);
    }

    public static android.content.Intent makeMainActivity(android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent(ACTION_MAIN);
        intent.setComponent(componentName);
        intent.addCategory(CATEGORY_LAUNCHER);
        return intent;
    }

    public static android.content.Intent makeMainSelectorActivity(java.lang.String str, java.lang.String str2) {
        android.content.Intent intent = new android.content.Intent(ACTION_MAIN);
        intent.addCategory(CATEGORY_LAUNCHER);
        android.content.Intent intent2 = new android.content.Intent();
        intent2.setAction(str);
        intent2.addCategory(str2);
        intent.setSelector(intent2);
        return intent;
    }

    public static android.content.Intent makeRestartActivityTask(android.content.ComponentName componentName) {
        android.content.Intent makeMainActivity = makeMainActivity(componentName);
        makeMainActivity.addFlags(268468224);
        return makeMainActivity;
    }

    @java.lang.Deprecated
    public static android.content.Intent getIntent(java.lang.String str) throws java.net.URISyntaxException {
        return parseUri(str, 0);
    }

    public static android.content.Intent parseUri(java.lang.String str, int i) throws java.net.URISyntaxException {
        android.content.Intent parseUriInternal = parseUriInternal(str, i);
        parseUriInternal.mLocalFlags |= 16;
        return parseUriInternal;
    }

    private static android.content.Intent parseUriInternal(java.lang.String str, int i) throws java.net.URISyntaxException {
        java.lang.String str2;
        boolean z;
        boolean z2;
        android.content.Intent intent;
        java.lang.String str3;
        java.lang.String str4;
        int i2;
        int i3 = 0;
        try {
            boolean startsWith = str.startsWith("android-app:");
            if ((i & 3) == 0 || str.startsWith("intent:") || startsWith) {
                int lastIndexOf = str.lastIndexOf("#");
                try {
                    if (lastIndexOf != -1) {
                        if (!str.startsWith("#Intent;", lastIndexOf)) {
                            if (!startsWith) {
                                return getIntentOld(str, i);
                            }
                            lastIndexOf = -1;
                        }
                    } else if (!startsWith) {
                        return new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(str));
                    }
                    android.content.Intent intent2 = new android.content.Intent("android.intent.action.VIEW");
                    if (lastIndexOf >= 0) {
                        str2 = str.substring(0, lastIndexOf);
                        lastIndexOf += 8;
                        z = false;
                        z2 = false;
                        intent = intent2;
                        str3 = null;
                    } else {
                        str2 = str;
                        z = false;
                        z2 = false;
                        intent = intent2;
                        str3 = null;
                    }
                    while (true) {
                        if (lastIndexOf < 0 || str.startsWith("end", lastIndexOf)) {
                            break;
                        }
                        int indexOf = str.indexOf(61, lastIndexOf);
                        if (indexOf < 0) {
                            indexOf = lastIndexOf - 1;
                        }
                        int indexOf2 = str.indexOf(59, lastIndexOf);
                        java.lang.String decode = indexOf < indexOf2 ? android.net.Uri.decode(str.substring(indexOf + 1, indexOf2)) : "";
                        if (!str.startsWith("action=", lastIndexOf)) {
                            if (!str.startsWith("category=", lastIndexOf)) {
                                if (!str.startsWith("type=", lastIndexOf)) {
                                    if (!str.startsWith("identifier=", lastIndexOf)) {
                                        if (!str.startsWith("launchFlags=", lastIndexOf)) {
                                            if (!str.startsWith("extendedLaunchFlags=", lastIndexOf)) {
                                                if (!str.startsWith("package=", lastIndexOf)) {
                                                    if (!str.startsWith("component=", lastIndexOf)) {
                                                        if (!str.startsWith("scheme=", lastIndexOf)) {
                                                            if (str.startsWith("sourceBounds=", lastIndexOf)) {
                                                                intent.mSourceBounds = android.graphics.Rect.unflattenFromString(decode);
                                                            } else if (indexOf2 != lastIndexOf + 3 || !str.startsWith("SEL", lastIndexOf)) {
                                                                java.lang.String decode2 = android.net.Uri.decode(str.substring(lastIndexOf + 2, indexOf));
                                                                if (intent.mExtras == null) {
                                                                    intent.mExtras = new android.os.Bundle();
                                                                }
                                                                android.os.Bundle bundle = intent.mExtras;
                                                                if (str.startsWith("S.", lastIndexOf)) {
                                                                    bundle.putString(decode2, decode);
                                                                } else if (str.startsWith("B.", lastIndexOf)) {
                                                                    bundle.putBoolean(decode2, java.lang.Boolean.parseBoolean(decode));
                                                                } else if (str.startsWith("b.", lastIndexOf)) {
                                                                    bundle.putByte(decode2, java.lang.Byte.parseByte(decode));
                                                                } else if (!str.startsWith("c.", lastIndexOf)) {
                                                                    if (str.startsWith("d.", lastIndexOf)) {
                                                                        bundle.putDouble(decode2, java.lang.Double.parseDouble(decode));
                                                                    } else if (str.startsWith("f.", lastIndexOf)) {
                                                                        bundle.putFloat(decode2, java.lang.Float.parseFloat(decode));
                                                                    } else if (str.startsWith("i.", lastIndexOf)) {
                                                                        bundle.putInt(decode2, java.lang.Integer.parseInt(decode));
                                                                    } else if (str.startsWith("l.", lastIndexOf)) {
                                                                        bundle.putLong(decode2, java.lang.Long.parseLong(decode));
                                                                    } else {
                                                                        if (!str.startsWith("s.", lastIndexOf)) {
                                                                            throw new java.net.URISyntaxException(str, "unknown EXTRA type", lastIndexOf);
                                                                        }
                                                                        bundle.putShort(decode2, java.lang.Short.parseShort(decode));
                                                                    }
                                                                } else {
                                                                    bundle.putChar(decode2, decode.charAt(0));
                                                                }
                                                            } else {
                                                                intent = new android.content.Intent();
                                                                z = true;
                                                            }
                                                        } else if (z) {
                                                            intent.mData = android.net.Uri.parse(decode + ":");
                                                        } else {
                                                            str3 = decode;
                                                        }
                                                    } else {
                                                        intent.mComponent = android.content.ComponentName.unflattenFromString(decode);
                                                    }
                                                } else {
                                                    intent.mPackage = decode;
                                                }
                                            } else {
                                                intent.mExtendedFlags = java.lang.Integer.decode(decode).intValue();
                                            }
                                        } else {
                                            intent.mFlags = java.lang.Integer.decode(decode).intValue();
                                            if ((i & 4) == 0) {
                                                intent.mFlags &= -196;
                                            }
                                        }
                                    } else {
                                        intent.mIdentifier = decode;
                                    }
                                } else {
                                    intent.mType = decode;
                                }
                            } else {
                                intent.addCategory(decode);
                            }
                        } else {
                            intent.setAction(decode);
                            if (!z) {
                                z2 = true;
                            }
                        }
                        lastIndexOf = indexOf2 + 1;
                    }
                    if (!z) {
                        intent2 = intent;
                    } else if (intent2.mPackage == null) {
                        intent2.setSelector(intent);
                    }
                    if (str2 != null) {
                        if (str2.startsWith("intent:")) {
                            str2 = str2.substring(7);
                            if (str3 != null) {
                                str2 = str3 + com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR + str2;
                            }
                        } else if (str2.startsWith("android-app:")) {
                            if (str2.charAt(12) == '/' && str2.charAt(13) == '/') {
                                int indexOf3 = str2.indexOf(47, 14);
                                if (indexOf3 < 0) {
                                    intent2.mPackage = android.net.Uri.decodeIfNeeded(str2.substring(14));
                                    if (!z2) {
                                        intent2.setAction(ACTION_MAIN);
                                    }
                                    str2 = "";
                                } else {
                                    intent2.mPackage = android.net.Uri.decodeIfNeeded(str2.substring(14, indexOf3));
                                    int i4 = indexOf3 + 1;
                                    if (i4 >= str2.length()) {
                                        str4 = null;
                                    } else {
                                        int indexOf4 = str2.indexOf(47, i4);
                                        if (indexOf4 >= 0) {
                                            str3 = android.net.Uri.decodeIfNeeded(str2.substring(i4, indexOf4));
                                            if (indexOf4 < str2.length() && (indexOf3 = str2.indexOf(47, (i2 = indexOf4 + 1))) >= 0) {
                                                str4 = android.net.Uri.decodeIfNeeded(str2.substring(i2, indexOf3));
                                            } else {
                                                indexOf3 = indexOf4;
                                                str4 = null;
                                            }
                                        } else {
                                            str3 = android.net.Uri.decodeIfNeeded(str2.substring(i4));
                                            str4 = null;
                                        }
                                    }
                                    if (str3 == null) {
                                        if (!z2) {
                                            intent2.setAction(ACTION_MAIN);
                                        }
                                        str2 = "";
                                    } else if (str4 == null) {
                                        str2 = str3 + ":";
                                    } else {
                                        str2 = str3 + "://" + str4 + str2.substring(indexOf3);
                                    }
                                }
                            } else {
                                str2 = "";
                            }
                        }
                        if (str2.length() > 0) {
                            try {
                                intent2.mData = android.net.Uri.parse(str2);
                            } catch (java.lang.IllegalArgumentException e) {
                                throw new java.net.URISyntaxException(str, e.getMessage());
                            }
                        }
                    }
                    return intent2;
                } catch (java.lang.IndexOutOfBoundsException e2) {
                    i3 = lastIndexOf;
                    throw new java.net.URISyntaxException(str, "illegal Intent URI format", i3);
                }
            }
            android.content.Intent intent3 = new android.content.Intent("android.intent.action.VIEW");
            try {
                intent3.setData(android.net.Uri.parse(str));
                return intent3;
            } catch (java.lang.IllegalArgumentException e3) {
                throw new java.net.URISyntaxException(str, e3.getMessage());
            }
        } catch (java.lang.IndexOutOfBoundsException e4) {
        }
    }

    public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
        if (android.app.Flags.visitRiskyUris()) {
            consumer.accept(this.mData);
            if (this.mSelector != null) {
                this.mSelector.visitUris(consumer);
            }
            if (this.mOriginalIntent != null) {
                this.mOriginalIntent.visitUris(consumer);
            }
        }
    }

    public static android.content.Intent getIntentOld(java.lang.String str) throws java.net.URISyntaxException {
        android.content.Intent intentOld = getIntentOld(str, 0);
        intentOld.mLocalFlags |= 16;
        return intentOld;
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b6 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.Intent getIntentOld(java.lang.String str, int i) throws java.net.URISyntaxException {
        boolean z;
        int i2;
        java.lang.String str2;
        boolean z2;
        char charAt;
        int lastIndexOf = str.lastIndexOf(35);
        if (lastIndexOf >= 0) {
            int i3 = lastIndexOf + 1;
            if (!str.regionMatches(i3, "action(", 0, 7)) {
                z = false;
                i2 = i3;
                str2 = null;
            } else {
                int i4 = i3 + 7;
                int indexOf = str.indexOf(41, i4);
                str2 = str.substring(i4, indexOf);
                i2 = indexOf + 1;
                z = true;
            }
            android.content.Intent intent = new android.content.Intent(str2);
            if (str.regionMatches(i2, "categories(", 0, 11)) {
                int i5 = i2 + 11;
                int indexOf2 = str.indexOf(41, i5);
                while (i5 < indexOf2) {
                    int indexOf3 = str.indexOf(33, i5);
                    if (indexOf3 < 0 || indexOf3 > indexOf2) {
                        indexOf3 = indexOf2;
                    }
                    if (i5 < indexOf3) {
                        intent.addCategory(str.substring(i5, indexOf3));
                    }
                    i5 = indexOf3 + 1;
                }
                i2 = indexOf2 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "type(", 0, 5)) {
                int i6 = i2 + 5;
                int indexOf4 = str.indexOf(41, i6);
                intent.mType = str.substring(i6, indexOf4);
                i2 = indexOf4 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "launchFlags(", 0, 12)) {
                int i7 = i2 + 12;
                int indexOf5 = str.indexOf(41, i7);
                intent.mFlags = java.lang.Integer.decode(str.substring(i7, indexOf5)).intValue();
                if ((i & 4) == 0) {
                    intent.mFlags &= -196;
                }
                i2 = indexOf5 + 1;
                z = true;
            }
            if (str.regionMatches(i2, "component(", 0, 10)) {
                int i8 = i2 + 10;
                int indexOf6 = str.indexOf(41, i8);
                int indexOf7 = str.indexOf(33, i8);
                if (indexOf7 >= 0 && indexOf7 < indexOf6) {
                    intent.mComponent = new android.content.ComponentName(str.substring(i8, indexOf7), str.substring(indexOf7 + 1, indexOf6));
                }
                i2 = indexOf6 + 1;
                z = true;
            }
            if (!str.regionMatches(i2, "extras(", 0, 7)) {
                z2 = z;
            } else {
                int i9 = i2 + 7;
                int indexOf8 = str.indexOf(41, i9);
                if (indexOf8 == -1) {
                    throw new java.net.URISyntaxException(str, "EXTRA missing trailing ')'", i9);
                }
                while (i9 < indexOf8) {
                    int indexOf9 = str.indexOf(61, i9);
                    int i10 = i9 + 1;
                    if (indexOf9 <= i10 || i9 >= indexOf8) {
                        throw new java.net.URISyntaxException(str, "EXTRA missing '='", i9);
                    }
                    char charAt2 = str.charAt(i9);
                    java.lang.String substring = str.substring(i10, indexOf9);
                    int i11 = indexOf9 + 1;
                    int indexOf10 = str.indexOf(33, i11);
                    if (indexOf10 == -1 || indexOf10 >= indexOf8) {
                        indexOf10 = indexOf8;
                    }
                    if (i11 >= indexOf10) {
                        throw new java.net.URISyntaxException(str, "EXTRA missing '!'", i11);
                    }
                    java.lang.String substring2 = str.substring(i11, indexOf10);
                    if (intent.mExtras == null) {
                        intent.mExtras = new android.os.Bundle();
                    }
                    switch (charAt2) {
                        case 'B':
                            intent.mExtras.putBoolean(substring, java.lang.Boolean.parseBoolean(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                                if (charAt != '!') {
                                    throw new java.net.URISyntaxException(str, "EXTRA missing '!'", indexOf10);
                                }
                                i9 = indexOf10 + 1;
                            } else {
                                z2 = true;
                                break;
                            }
                        case 'S':
                            intent.mExtras.putString(substring, android.net.Uri.decode(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 'b':
                            intent.mExtras.putByte(substring, java.lang.Byte.parseByte(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 'c':
                            intent.mExtras.putChar(substring, android.net.Uri.decode(substring2).charAt(0));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 'd':
                            intent.mExtras.putDouble(substring, java.lang.Double.parseDouble(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 'f':
                            intent.mExtras.putFloat(substring, java.lang.Float.parseFloat(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 'i':
                            intent.mExtras.putInt(substring, java.lang.Integer.parseInt(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 'l':
                            intent.mExtras.putLong(substring, java.lang.Long.parseLong(substring2));
                            charAt = str.charAt(indexOf10);
                            if (charAt != ')') {
                            }
                            break;
                        case 's':
                            try {
                                intent.mExtras.putShort(substring, java.lang.Short.parseShort(substring2));
                                charAt = str.charAt(indexOf10);
                                if (charAt != ')') {
                                }
                            } catch (java.lang.NumberFormatException e) {
                                throw new java.net.URISyntaxException(str, "EXTRA value can't be parsed", indexOf10);
                            }
                            break;
                        default:
                            throw new java.net.URISyntaxException(str, "EXTRA has unknown type", indexOf10);
                    }
                    throw new java.net.URISyntaxException(str, "EXTRA value can't be parsed", indexOf10);
                }
                z2 = true;
            }
            if (z2) {
                intent.mData = android.net.Uri.parse(str.substring(0, lastIndexOf));
            } else {
                intent.mData = android.net.Uri.parse(str);
            }
            if (intent.mAction == null) {
                intent.mAction = "android.intent.action.VIEW";
                return intent;
            }
            return intent;
        }
        return new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse(str));
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type update terminated with stack overflow, arg: (r4v1 ?? I:??[int, boolean, OBJECT, ARRAY, byte, short, char]), method size: 2374
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public static android.content.Intent parseCommandArgs(android.os.ShellCommand r17, android.content.Intent.CommandOptionHandler r18) throws java.net.URISyntaxException {
        /*
            Method dump skipped, instructions count: 2374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.Intent.parseCommandArgs(android.os.ShellCommand, android.content.Intent$CommandOptionHandler):android.content.Intent");
    }

    public static void printIntentArgsHelp(java.io.PrintWriter printWriter, java.lang.String str) {
        java.lang.String[] strArr = {"<INTENT> specifications include these flags and arguments:", "    [-a <ACTION>] [-d <DATA_URI>] [-t <MIME_TYPE>] [-i <IDENTIFIER>]", "    [-c <CATEGORY> [-c <CATEGORY>] ...]", "    [-n <COMPONENT_NAME>]", "    [-e|--es <EXTRA_KEY> <EXTRA_STRING_VALUE> ...]", "    [--esn <EXTRA_KEY> ...]", "    [--ez <EXTRA_KEY> <EXTRA_BOOLEAN_VALUE> ...]", "    [--ei <EXTRA_KEY> <EXTRA_INT_VALUE> ...]", "    [--el <EXTRA_KEY> <EXTRA_LONG_VALUE> ...]", "    [--ef <EXTRA_KEY> <EXTRA_FLOAT_VALUE> ...]", "    [--ed <EXTRA_KEY> <EXTRA_DOUBLE_VALUE> ...]", "    [--eu <EXTRA_KEY> <EXTRA_URI_VALUE> ...]", "    [--ecn <EXTRA_KEY> <EXTRA_COMPONENT_NAME_VALUE>]", "    [--eia <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (multiple extras passed as Integer[])", "    [--eial <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (multiple extras passed as List<Integer>)", "    [--ela <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (multiple extras passed as Long[])", "    [--elal <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (multiple extras passed as List<Long>)", "    [--efa <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (multiple extras passed as Float[])", "    [--efal <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (multiple extras passed as List<Float>)", "    [--eda <EXTRA_KEY> <EXTRA_DOUBLE_VALUE>[,<EXTRA_DOUBLE_VALUE...]]", "        (multiple extras passed as Double[])", "    [--edal <EXTRA_KEY> <EXTRA_DOUBLE_VALUE>[,<EXTRA_DOUBLE_VALUE...]]", "        (multiple extras passed as List<Double>)", "    [--esa <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (multiple extras passed as String[]; to embed a comma into a string,", "         escape it using \"\\,\")", "    [--esal <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (multiple extras passed as List<String>; to embed a comma into a string,", "         escape it using \"\\,\")", "    [-f <FLAG>]", "    [--grant-read-uri-permission] [--grant-write-uri-permission]", "    [--grant-persistable-uri-permission] [--grant-prefix-uri-permission]", "    [--debug-log-resolution] [--exclude-stopped-packages]", "    [--include-stopped-packages]", "    [--activity-brought-to-front] [--activity-clear-top]", "    [--activity-clear-when-task-reset] [--activity-exclude-from-recents]", "    [--activity-launched-from-history] [--activity-multiple-task]", "    [--activity-no-animation] [--activity-no-history]", "    [--activity-no-user-action] [--activity-previous-is-top]", "    [--activity-reorder-to-front] [--activity-reset-task-if-needed]", "    [--activity-single-top] [--activity-clear-task]", "    [--activity-task-on-home] [--activity-match-external]", "    [--receiver-registered-only] [--receiver-replace-pending]", "    [--receiver-foreground] [--receiver-no-abort]", "    [--receiver-include-background]", "    [--selector]", "    [<URI> | <PACKAGE> | <COMPONENT>]"};
        for (int i = 0; i < 53; i++) {
            java.lang.String str2 = strArr[i];
            printWriter.print(str);
            printWriter.println(str2);
        }
    }

    public java.lang.String getAction() {
        return this.mAction;
    }

    public android.net.Uri getData() {
        return this.mData;
    }

    public java.lang.String getDataString() {
        if (this.mData != null) {
            return this.mData.toString();
        }
        return null;
    }

    public java.lang.String getScheme() {
        if (this.mData != null) {
            return this.mData.getScheme();
        }
        return null;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public android.content.Intent getOriginalIntent() {
        return this.mOriginalIntent;
    }

    public void setOriginalIntent(android.content.Intent intent) {
        this.mOriginalIntent = intent;
    }

    public java.lang.String resolveType(android.content.Context context) {
        return resolveType(context.getContentResolver());
    }

    public java.lang.String resolveType(android.content.ContentResolver contentResolver) {
        if (this.mType != null) {
            return this.mType;
        }
        if (this.mData != null && "content".equals(this.mData.getScheme())) {
            return contentResolver.getType(this.mData);
        }
        return null;
    }

    public java.lang.String resolveTypeIfNeeded(android.content.ContentResolver contentResolver) {
        if (this.mComponent != null && (android.os.Process.myUid() == 0 || android.os.Process.myUid() == 1000 || this.mComponent.getPackageName().equals(android.app.ActivityThread.currentPackageName()))) {
            return this.mType;
        }
        return resolveType(contentResolver);
    }

    public java.lang.String getIdentifier() {
        return this.mIdentifier;
    }

    public boolean hasCategory(java.lang.String str) {
        return this.mCategories != null && this.mCategories.contains(str);
    }

    public java.util.Set<java.lang.String> getCategories() {
        return this.mCategories;
    }

    public android.content.Intent getSelector() {
        return this.mSelector;
    }

    public android.content.ClipData getClipData() {
        return this.mClipData;
    }

    public int getContentUserHint() {
        return this.mContentUserHint;
    }

    public java.lang.String getLaunchToken() {
        return this.mLaunchToken;
    }

    public void setLaunchToken(java.lang.String str) {
        this.mLaunchToken = str;
    }

    public void setExtrasClassLoader(java.lang.ClassLoader classLoader) {
        if (this.mExtras != null) {
            this.mExtras.setClassLoader(classLoader);
        }
    }

    public boolean hasExtra(java.lang.String str) {
        return this.mExtras != null && this.mExtras.containsKey(str);
    }

    public boolean hasFileDescriptors() {
        return this.mExtras != null && this.mExtras.hasFileDescriptors();
    }

    public void setAllowFds(boolean z) {
        if (this.mExtras != null) {
            this.mExtras.setAllowFds(z);
        }
    }

    public void setDefusable(boolean z) {
        if (this.mExtras != null) {
            this.mExtras.setDefusable(z);
        }
    }

    @java.lang.Deprecated
    public java.lang.Object getExtra(java.lang.String str) {
        return getExtra(str, null);
    }

    public boolean getBooleanExtra(java.lang.String str, boolean z) {
        if (this.mExtras == null) {
            return z;
        }
        return this.mExtras.getBoolean(str, z);
    }

    public byte getByteExtra(java.lang.String str, byte b) {
        if (this.mExtras == null) {
            return b;
        }
        return this.mExtras.getByte(str, b).byteValue();
    }

    public short getShortExtra(java.lang.String str, short s) {
        if (this.mExtras == null) {
            return s;
        }
        return this.mExtras.getShort(str, s);
    }

    public char getCharExtra(java.lang.String str, char c) {
        if (this.mExtras == null) {
            return c;
        }
        return this.mExtras.getChar(str, c);
    }

    public int getIntExtra(java.lang.String str, int i) {
        if (this.mExtras == null) {
            return i;
        }
        return this.mExtras.getInt(str, i);
    }

    public long getLongExtra(java.lang.String str, long j) {
        if (this.mExtras == null) {
            return j;
        }
        return this.mExtras.getLong(str, j);
    }

    public float getFloatExtra(java.lang.String str, float f) {
        if (this.mExtras == null) {
            return f;
        }
        return this.mExtras.getFloat(str, f);
    }

    public double getDoubleExtra(java.lang.String str, double d) {
        if (this.mExtras == null) {
            return d;
        }
        return this.mExtras.getDouble(str, d);
    }

    public java.lang.String getStringExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getString(str);
    }

    public java.lang.CharSequence getCharSequenceExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharSequence(str);
    }

    @java.lang.Deprecated
    public <T extends android.os.Parcelable> T getParcelableExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return (T) this.mExtras.getParcelable(str);
    }

    public <T> T getParcelableExtra(java.lang.String str, java.lang.Class<T> cls) {
        if (this.mExtras == null) {
            return null;
        }
        return (T) this.mExtras.getParcelable(str, cls);
    }

    @java.lang.Deprecated
    public android.os.Parcelable[] getParcelableArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getParcelableArray(str);
    }

    public <T> T[] getParcelableArrayExtra(java.lang.String str, java.lang.Class<T> cls) {
        if (this.mExtras == null) {
            return null;
        }
        return (T[]) this.mExtras.getParcelableArray(str, cls);
    }

    @java.lang.Deprecated
    public <T extends android.os.Parcelable> java.util.ArrayList<T> getParcelableArrayListExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getParcelableArrayList(str);
    }

    public <T> java.util.ArrayList<T> getParcelableArrayListExtra(java.lang.String str, java.lang.Class<? extends T> cls) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getParcelableArrayList(str, cls);
    }

    public java.io.Serializable getSerializableExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getSerializable(str);
    }

    public <T extends java.io.Serializable> T getSerializableExtra(java.lang.String str, java.lang.Class<T> cls) {
        if (this.mExtras == null) {
            return null;
        }
        return (T) this.mExtras.getSerializable(str, cls);
    }

    public java.util.ArrayList<java.lang.Integer> getIntegerArrayListExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getIntegerArrayList(str);
    }

    public java.util.ArrayList<java.lang.String> getStringArrayListExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getStringArrayList(str);
    }

    public java.util.ArrayList<java.lang.CharSequence> getCharSequenceArrayListExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharSequenceArrayList(str);
    }

    public boolean[] getBooleanArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getBooleanArray(str);
    }

    public byte[] getByteArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getByteArray(str);
    }

    public short[] getShortArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getShortArray(str);
    }

    public char[] getCharArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharArray(str);
    }

    public int[] getIntArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getIntArray(str);
    }

    public long[] getLongArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getLongArray(str);
    }

    public float[] getFloatArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getFloatArray(str);
    }

    public double[] getDoubleArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getDoubleArray(str);
    }

    public java.lang.String[] getStringArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getStringArray(str);
    }

    public java.lang.CharSequence[] getCharSequenceArrayExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharSequenceArray(str);
    }

    public android.os.Bundle getBundleExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getBundle(str);
    }

    @java.lang.Deprecated
    public android.os.IBinder getIBinderExtra(java.lang.String str) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getIBinder(str);
    }

    @java.lang.Deprecated
    public java.lang.Object getExtra(java.lang.String str, java.lang.Object obj) {
        java.lang.Object obj2;
        if (this.mExtras != null && (obj2 = this.mExtras.get(str)) != null) {
            return obj2;
        }
        return obj;
    }

    public android.os.Bundle getExtras() {
        if (this.mExtras != null) {
            return new android.os.Bundle(this.mExtras);
        }
        return null;
    }

    public int getExtrasTotalSize() {
        if (this.mExtras != null) {
            return this.mExtras.getSize();
        }
        return 0;
    }

    public boolean canStripForHistory() {
        return (this.mExtras != null && this.mExtras.isParcelled()) || this.mClipData != null;
    }

    public android.content.Intent maybeStripForHistory() {
        if (!canStripForHistory()) {
            return this;
        }
        return new android.content.Intent(this, 2);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getExtendedFlags() {
        return this.mExtendedFlags;
    }

    public boolean isExcludingStopped() {
        return (this.mFlags & 48) == 16;
    }

    public java.lang.String getPackage() {
        return this.mPackage;
    }

    public android.content.ComponentName getComponent() {
        return this.mComponent;
    }

    public android.graphics.Rect getSourceBounds() {
        return this.mSourceBounds;
    }

    public android.content.ComponentName resolveActivity(android.content.pm.PackageManager packageManager) {
        if (this.mComponent != null) {
            return this.mComponent;
        }
        android.content.pm.ResolveInfo resolveActivity = packageManager.resolveActivity(this, 65536);
        if (resolveActivity != null) {
            return new android.content.ComponentName(resolveActivity.activityInfo.applicationInfo.packageName, resolveActivity.activityInfo.name);
        }
        return null;
    }

    public android.content.pm.ActivityInfo resolveActivityInfo(android.content.pm.PackageManager packageManager, int i) {
        if (this.mComponent != null) {
            try {
                return packageManager.getActivityInfo(this.mComponent, i);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        } else {
            android.content.pm.ResolveInfo resolveActivity = packageManager.resolveActivity(this, i | 65536);
            if (resolveActivity != null) {
                return resolveActivity.activityInfo;
            }
        }
        return null;
    }

    public android.content.ComponentName resolveSystemService(android.content.pm.PackageManager packageManager, int i) {
        if (this.mComponent != null) {
            return this.mComponent;
        }
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(this, i);
        android.content.ComponentName componentName = null;
        if (queryIntentServices == null) {
            return null;
        }
        for (int i2 = 0; i2 < queryIntentServices.size(); i2++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentServices.get(i2);
            if ((resolveInfo.serviceInfo.applicationInfo.flags & 1) != 0) {
                android.content.ComponentName componentName2 = new android.content.ComponentName(resolveInfo.serviceInfo.applicationInfo.packageName, resolveInfo.serviceInfo.name);
                if (componentName != null) {
                    throw new java.lang.IllegalStateException("Multiple system services handle " + this + ": " + componentName + ", " + componentName2);
                }
                componentName = componentName2;
            }
        }
        return componentName;
    }

    public android.content.Intent setAction(java.lang.String str) {
        this.mAction = str != null ? str.intern() : null;
        return this;
    }

    public android.content.Intent setData(android.net.Uri uri) {
        this.mData = uri;
        this.mType = null;
        return this;
    }

    public android.content.Intent setDataAndNormalize(android.net.Uri uri) {
        return setData(uri.normalizeScheme());
    }

    public android.content.Intent setType(java.lang.String str) {
        this.mData = null;
        this.mType = str;
        return this;
    }

    public android.content.Intent setTypeAndNormalize(java.lang.String str) {
        return setType(normalizeMimeType(str));
    }

    public android.content.Intent setDataAndType(android.net.Uri uri, java.lang.String str) {
        this.mData = uri;
        this.mType = str;
        return this;
    }

    public android.content.Intent setDataAndTypeAndNormalize(android.net.Uri uri, java.lang.String str) {
        return setDataAndType(uri.normalizeScheme(), normalizeMimeType(str));
    }

    public android.content.Intent setIdentifier(java.lang.String str) {
        this.mIdentifier = str;
        return this;
    }

    public android.content.Intent addCategory(java.lang.String str) {
        if (this.mCategories == null) {
            this.mCategories = new android.util.ArraySet<>();
        }
        this.mCategories.add(str.intern());
        return this;
    }

    public void removeCategory(java.lang.String str) {
        if (this.mCategories != null) {
            this.mCategories.remove(str);
            if (this.mCategories.size() == 0) {
                this.mCategories = null;
            }
        }
    }

    public void setSelector(android.content.Intent intent) {
        if (intent == this) {
            throw new java.lang.IllegalArgumentException("Intent being set as a selector of itself");
        }
        if (intent != null && this.mPackage != null) {
            throw new java.lang.IllegalArgumentException("Can't set selector when package name is already set");
        }
        this.mSelector = intent;
    }

    public void setClipData(android.content.ClipData clipData) {
        this.mClipData = clipData;
    }

    public void prepareToLeaveUser(int i) {
        if (this.mContentUserHint == -2) {
            this.mContentUserHint = i;
        }
    }

    public android.content.Intent putExtra(java.lang.String str, boolean z) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putBoolean(str, z);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, byte b) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putByte(str, b);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, char c) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putChar(str, c);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, short s) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putShort(str, s);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, int i) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putInt(str, i);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, long j) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putLong(str, j);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, float f) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putFloat(str, f);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, double d) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putDouble(str, d);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, java.lang.String str2) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putString(str, str2);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, java.lang.CharSequence charSequence) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putCharSequence(str, charSequence);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, android.os.Parcelable parcelable) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putParcelable(str, parcelable);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, android.os.Parcelable[] parcelableArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putParcelableArray(str, parcelableArr);
        return this;
    }

    public android.content.Intent putParcelableArrayListExtra(java.lang.String str, java.util.ArrayList<? extends android.os.Parcelable> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putParcelableArrayList(str, arrayList);
        return this;
    }

    public android.content.Intent putIntegerArrayListExtra(java.lang.String str, java.util.ArrayList<java.lang.Integer> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putIntegerArrayList(str, arrayList);
        return this;
    }

    public android.content.Intent putStringArrayListExtra(java.lang.String str, java.util.ArrayList<java.lang.String> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putStringArrayList(str, arrayList);
        return this;
    }

    public android.content.Intent putCharSequenceArrayListExtra(java.lang.String str, java.util.ArrayList<java.lang.CharSequence> arrayList) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putCharSequenceArrayList(str, arrayList);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, java.io.Serializable serializable) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putSerializable(str, serializable);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, boolean[] zArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putBooleanArray(str, zArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, byte[] bArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putByteArray(str, bArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, short[] sArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putShortArray(str, sArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, char[] cArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putCharArray(str, cArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, int[] iArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putIntArray(str, iArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, long[] jArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putLongArray(str, jArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, float[] fArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putFloatArray(str, fArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, double[] dArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putDoubleArray(str, dArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, java.lang.String[] strArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putStringArray(str, strArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, java.lang.CharSequence[] charSequenceArr) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putCharSequenceArray(str, charSequenceArr);
        return this;
    }

    public android.content.Intent putExtra(java.lang.String str, android.os.Bundle bundle) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putBundle(str, bundle);
        return this;
    }

    @java.lang.Deprecated
    public android.content.Intent putExtra(java.lang.String str, android.os.IBinder iBinder) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putIBinder(str, iBinder);
        return this;
    }

    public android.content.Intent putExtras(android.content.Intent intent) {
        if (intent.mExtras != null) {
            if (this.mExtras == null) {
                this.mExtras = new android.os.Bundle(intent.mExtras);
            } else {
                this.mExtras.putAll(intent.mExtras);
            }
        }
        if ((intent.mLocalFlags & 2) != 0 && (intent.mLocalFlags & 36) == 0) {
            this.mLocalFlags |= 8;
        }
        return this;
    }

    public android.content.Intent putExtras(android.os.Bundle bundle) {
        if (bundle.isParcelled()) {
            this.mLocalFlags |= 8;
        }
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putAll(bundle);
        return this;
    }

    public android.content.Intent replaceExtras(android.content.Intent intent) {
        this.mExtras = intent.mExtras != null ? new android.os.Bundle(intent.mExtras) : null;
        return this;
    }

    public android.content.Intent replaceExtras(android.os.Bundle bundle) {
        this.mExtras = bundle != null ? new android.os.Bundle(bundle) : null;
        return this;
    }

    public void removeExtra(java.lang.String str) {
        if (this.mExtras != null) {
            this.mExtras.remove(str);
            if (this.mExtras.size() == 0) {
                this.mExtras = null;
            }
        }
    }

    public android.content.Intent setFlags(int i) {
        this.mFlags = i;
        return this;
    }

    public android.content.Intent addFlags(int i) {
        this.mFlags = i | this.mFlags;
        return this;
    }

    public android.content.Intent addExtendedFlags(int i) {
        this.mExtendedFlags = i | this.mExtendedFlags;
        return this;
    }

    public void removeFlags(int i) {
        this.mFlags = (~i) & this.mFlags;
    }

    public void removeExtendedFlags(int i) {
        this.mExtendedFlags = (~i) & this.mExtendedFlags;
    }

    public android.content.Intent setPackage(java.lang.String str) {
        if (str != null && this.mSelector != null) {
            throw new java.lang.IllegalArgumentException("Can't set package name when selector is already set");
        }
        this.mPackage = str;
        return this;
    }

    public android.content.Intent setComponent(android.content.ComponentName componentName) {
        this.mComponent = componentName;
        return this;
    }

    public android.content.Intent setClassName(android.content.Context context, java.lang.String str) {
        this.mComponent = new android.content.ComponentName(context, str);
        return this;
    }

    public android.content.Intent setClassName(java.lang.String str, java.lang.String str2) {
        this.mComponent = new android.content.ComponentName(str, str2);
        return this;
    }

    public android.content.Intent setClass(android.content.Context context, java.lang.Class<?> cls) {
        this.mComponent = new android.content.ComponentName(context, cls);
        return this;
    }

    public void setSourceBounds(android.graphics.Rect rect) {
        if (rect != null) {
            this.mSourceBounds = new android.graphics.Rect(rect);
        } else {
            this.mSourceBounds = null;
        }
    }

    public int fillIn(android.content.Intent intent, int i) {
        int i2;
        boolean z = true;
        boolean z2 = false;
        if (intent.mAction != null && (this.mAction == null || (i & 1) != 0)) {
            this.mAction = intent.mAction;
            i2 = 1;
        } else {
            i2 = 0;
        }
        if ((intent.mData != null || intent.mType != null) && ((this.mData == null && this.mType == null) || (i & 2) != 0)) {
            this.mData = intent.mData;
            this.mType = intent.mType;
            i2 |= 2;
            z2 = true;
        }
        if (intent.mIdentifier != null && (this.mIdentifier == null || (i & 256) != 0)) {
            this.mIdentifier = intent.mIdentifier;
            i2 |= 256;
        }
        if (intent.mCategories != null && (this.mCategories == null || (i & 4) != 0)) {
            if (intent.mCategories != null) {
                this.mCategories = new android.util.ArraySet<>((android.util.ArraySet) intent.mCategories);
            }
            i2 |= 4;
        }
        if (intent.mPackage != null && ((this.mPackage == null || (i & 16) != 0) && this.mSelector == null)) {
            this.mPackage = intent.mPackage;
            i2 |= 16;
        }
        if (intent.mSelector != null && (i & 64) != 0 && this.mPackage == null) {
            this.mSelector = new android.content.Intent(intent.mSelector);
            this.mPackage = null;
            i2 |= 64;
        }
        if (intent.mClipData != null && (this.mClipData == null || (i & 128) != 0)) {
            this.mClipData = intent.mClipData;
            i2 |= 128;
            z2 = true;
        }
        if (intent.mComponent != null && (i & 8) != 0) {
            this.mComponent = intent.mComponent;
            i2 |= 8;
        }
        this.mFlags |= intent.mFlags;
        this.mExtendedFlags |= intent.mExtendedFlags;
        if (intent.mSourceBounds != null && (this.mSourceBounds == null || (i & 32) != 0)) {
            this.mSourceBounds = new android.graphics.Rect(intent.mSourceBounds);
            i2 |= 32;
        }
        if (this.mExtras == null) {
            if (intent.mExtras != null) {
                this.mExtras = new android.os.Bundle(intent.mExtras);
            }
            z = z2;
        } else {
            if (intent.mExtras != null) {
                try {
                    android.os.Bundle bundle = new android.os.Bundle(intent.mExtras);
                    bundle.putAll(this.mExtras);
                    this.mExtras = bundle;
                } catch (java.lang.RuntimeException e) {
                    android.util.Log.w(TAG, "Failure filling in extras", e);
                }
            }
            z = z2;
        }
        if (z && this.mContentUserHint == -2 && intent.mContentUserHint != -2) {
            this.mContentUserHint = intent.mContentUserHint;
        }
        return i2;
    }

    public void mergeExtras(android.content.Intent intent, android.os.BundleMerger bundleMerger) {
        this.mExtras = bundleMerger.merge(this.mExtras, intent.mExtras);
    }

    public static final class FilterComparison {
        private final int mHashCode;
        private final android.content.Intent mIntent;

        public FilterComparison(android.content.Intent intent) {
            this.mIntent = intent;
            this.mHashCode = intent.filterHashCode();
        }

        public android.content.Intent getIntent() {
            return this.mIntent;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof android.content.Intent.FilterComparison) {
                return this.mIntent.filterEquals(((android.content.Intent.FilterComparison) obj).mIntent);
            }
            return false;
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    public boolean filterEquals(android.content.Intent intent) {
        if (intent == null || !java.util.Objects.equals(this.mAction, intent.mAction) || !java.util.Objects.equals(this.mData, intent.mData) || !java.util.Objects.equals(this.mType, intent.mType) || !java.util.Objects.equals(this.mIdentifier, intent.mIdentifier) || !java.util.Objects.equals(this.mPackage, intent.mPackage) || !java.util.Objects.equals(this.mComponent, intent.mComponent) || !java.util.Objects.equals(this.mCategories, intent.mCategories)) {
            return false;
        }
        return true;
    }

    public int filterHashCode() {
        int hashCode = this.mAction != null ? 0 + this.mAction.hashCode() : 0;
        if (this.mData != null) {
            hashCode += this.mData.hashCode();
        }
        if (this.mType != null) {
            hashCode += this.mType.hashCode();
        }
        if (this.mIdentifier != null) {
            hashCode += this.mIdentifier.hashCode();
        }
        if (this.mPackage != null) {
            hashCode += this.mPackage.hashCode();
        }
        if (this.mComponent != null) {
            hashCode += this.mComponent.hashCode();
        }
        if (this.mCategories != null) {
            return hashCode + this.mCategories.hashCode();
        }
        return hashCode;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        toString(sb);
        return sb.toString();
    }

    public void toString(java.lang.StringBuilder sb) {
        sb.append("Intent { ");
        toShortString(sb, true, true, true, false);
        sb.append(" }");
    }

    public java.lang.String toInsecureString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Intent { ");
        toShortString(sb, false, true, true, false);
        sb.append(" }");
        return sb.toString();
    }

    public java.lang.String toShortString(boolean z, boolean z2, boolean z3, boolean z4) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        toShortString(sb, z, z2, z3, z4);
        return sb.toString();
    }

    public void toShortString(java.lang.StringBuilder sb, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        boolean z6 = false;
        boolean z7 = true;
        if (this.mAction == null) {
            z5 = true;
        } else {
            sb.append("act=").append(this.mAction);
            z5 = false;
        }
        if (this.mCategories != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("cat=[");
            for (int i = 0; i < this.mCategories.size(); i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(this.mCategories.valueAt(i));
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            z5 = false;
        }
        if (this.mData != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("dat=");
            if (z) {
                sb.append(this.mData.toSafeString());
            } else {
                sb.append(this.mData);
            }
            z5 = false;
        }
        if (this.mType != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("typ=").append(this.mType);
            z5 = false;
        }
        if (this.mIdentifier != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("id=").append(this.mIdentifier);
            z5 = false;
        }
        if (this.mFlags != 0) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("flg=0x").append(java.lang.Integer.toHexString(this.mFlags));
            z5 = false;
        }
        if (this.mExtendedFlags != 0) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("xflg=0x").append(java.lang.Integer.toHexString(this.mExtendedFlags));
            z5 = false;
        }
        if (this.mPackage != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("pkg=").append(this.mPackage);
            z5 = false;
        }
        if (z2 && this.mComponent != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("cmp=").append(this.mComponent.flattenToShortString());
            z5 = false;
        }
        if (this.mSourceBounds != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("bnds=").append(this.mSourceBounds.toShortString());
            z5 = false;
        }
        if (this.mClipData != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("clip={");
            android.content.ClipData clipData = this.mClipData;
            if (z4 && !z) {
                z7 = false;
            }
            clipData.toShortString(sb, z7);
            sb.append('}');
            z5 = false;
        }
        if (z3 && this.mExtras != null) {
            if (!z5) {
                sb.append(' ');
            }
            sb.append("(has extras)");
        } else {
            z6 = z5;
        }
        if (this.mContentUserHint != -2) {
            if (!z6) {
                sb.append(' ');
            }
            sb.append("u=").append(this.mContentUserHint);
        }
        if (this.mSelector != null) {
            sb.append(" sel=");
            this.mSelector.toShortString(sb, z, z2, z3, z4);
            sb.append("}");
        }
        if (this.mOriginalIntent != null) {
            sb.append(" org={");
            this.mOriginalIntent.toShortString(sb, z, z2, z3, z4);
            sb.append("}");
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        dumpDebug(protoOutputStream, j, true, true, true, false);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        dumpDebugWithoutFieldId(protoOutputStream, true, true, true, false);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, boolean z, boolean z2, boolean z3, boolean z4) {
        long start = protoOutputStream.start(j);
        dumpDebugWithoutFieldId(protoOutputStream, z, z2, z3, z4);
        protoOutputStream.end(start);
    }

    private void dumpDebugWithoutFieldId(android.util.proto.ProtoOutputStream protoOutputStream, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mAction != null) {
            protoOutputStream.write(1138166333441L, this.mAction);
        }
        if (this.mCategories != null) {
            java.util.Iterator<java.lang.String> it = this.mCategories.iterator();
            while (it.hasNext()) {
                protoOutputStream.write(2237677961218L, it.next());
            }
        }
        if (this.mData != null) {
            android.net.Uri uri = this.mData;
            protoOutputStream.write(1138166333443L, z ? uri.toSafeString() : uri.toString());
        }
        if (this.mType != null) {
            protoOutputStream.write(1138166333444L, this.mType);
        }
        if (this.mIdentifier != null) {
            protoOutputStream.write(1138166333453L, this.mIdentifier);
        }
        if (this.mFlags != 0) {
            protoOutputStream.write(1138166333445L, "0x" + java.lang.Integer.toHexString(this.mFlags));
        }
        if (this.mExtendedFlags != 0) {
            protoOutputStream.write(1138166333454L, "0x" + java.lang.Integer.toHexString(this.mExtendedFlags));
        }
        if (this.mPackage != null) {
            protoOutputStream.write(1138166333446L, this.mPackage);
        }
        if (z2 && this.mComponent != null) {
            this.mComponent.dumpDebug(protoOutputStream, 1146756268039L);
        }
        if (this.mSourceBounds != null) {
            protoOutputStream.write(1138166333448L, this.mSourceBounds.toShortString());
        }
        if (this.mClipData != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            this.mClipData.toShortString(sb, !z4 || z);
            protoOutputStream.write(1138166333449L, sb.toString());
        }
        if (z3 && this.mExtras != null) {
            protoOutputStream.write(1138166333450L, this.mExtras.toShortString());
        }
        if (this.mContentUserHint != 0) {
            protoOutputStream.write(1120986464267L, this.mContentUserHint);
        }
        if (this.mSelector != null) {
            protoOutputStream.write(1138166333452L, this.mSelector.toShortString(z, z2, z3, z4));
        }
    }

    @java.lang.Deprecated
    public java.lang.String toURI() {
        return toUri(0);
    }

    public java.lang.String toUri(int i) {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        java.lang.String str2 = null;
        if ((i & 2) != 0) {
            if (this.mPackage == null) {
                throw new java.lang.IllegalArgumentException("Intent must include an explicit package name to build an android-app: " + this);
            }
            sb.append("android-app://");
            sb.append(android.net.Uri.encode(this.mPackage));
            if (this.mData != null) {
                java.lang.String encodeIfNotEncoded = android.net.Uri.encodeIfNotEncoded(this.mData.getScheme(), null);
                if (encodeIfNotEncoded != null) {
                    sb.append('/');
                    sb.append(encodeIfNotEncoded);
                    java.lang.String encodeIfNotEncoded2 = android.net.Uri.encodeIfNotEncoded(this.mData.getEncodedAuthority(), null);
                    if (encodeIfNotEncoded2 != null) {
                        sb.append('/');
                        sb.append(encodeIfNotEncoded2);
                        java.lang.String encodeIfNotEncoded3 = android.net.Uri.encodeIfNotEncoded(this.mData.getEncodedPath(), "/");
                        if (encodeIfNotEncoded3 != null) {
                            sb.append(encodeIfNotEncoded3);
                        }
                        java.lang.String encodeIfNotEncoded4 = android.net.Uri.encodeIfNotEncoded(this.mData.getEncodedQuery(), null);
                        if (encodeIfNotEncoded4 != null) {
                            sb.append('?');
                            sb.append(encodeIfNotEncoded4);
                        }
                        java.lang.String encodeIfNotEncoded5 = android.net.Uri.encodeIfNotEncoded(this.mData.getEncodedFragment(), null);
                        if (encodeIfNotEncoded5 != null) {
                            sb.append('#');
                            sb.append(encodeIfNotEncoded5);
                        }
                    }
                }
                str2 = encodeIfNotEncoded;
            }
            toUriFragment(sb, null, str2 == null ? ACTION_MAIN : "android.intent.action.VIEW", this.mPackage, i);
            return sb.toString();
        }
        if (this.mData != null) {
            java.lang.String uri = this.mData.toString();
            if ((i & 1) != 0) {
                int length = uri.length();
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    char charAt = uri.charAt(i2);
                    if ((charAt >= 'a' && charAt <= 'z') || ((charAt >= 'A' && charAt <= 'Z') || ((charAt >= '0' && charAt <= '9') || charAt == '.' || charAt == '-' || charAt == '+'))) {
                        i2++;
                    } else if (charAt == ':' && i2 > 0) {
                        str2 = uri.substring(0, i2);
                        sb.append("intent:");
                        uri = uri.substring(i2 + 1);
                    }
                }
            }
            sb.append(uri);
            str = str2;
        } else {
            if ((i & 1) != 0) {
                sb.append("intent:");
            }
            str = null;
        }
        toUriFragment(sb, str, "android.intent.action.VIEW", null, i);
        return sb.toString();
    }

    private void toUriFragment(java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder(128);
        toUriInner(sb2, str, str2, str3, i);
        if (this.mSelector != null) {
            sb2.append("SEL;");
            this.mSelector.toUriInner(sb2, this.mSelector.mData != null ? this.mSelector.mData.getScheme() : null, null, null, i);
        }
        if (sb2.length() > 0) {
            sb.append("#Intent;");
            sb.append((java.lang.CharSequence) sb2);
            sb.append("end");
        }
    }

    private void toUriInner(java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        char c;
        if (str != null) {
            sb.append("scheme=").append(android.net.Uri.encode(str)).append(';');
        }
        if (this.mAction != null && !this.mAction.equals(str2)) {
            sb.append("action=").append(android.net.Uri.encode(this.mAction)).append(';');
        }
        if (this.mCategories != null) {
            for (int i2 = 0; i2 < this.mCategories.size(); i2++) {
                sb.append("category=").append(android.net.Uri.encode(this.mCategories.valueAt(i2))).append(';');
            }
        }
        if (this.mType != null) {
            sb.append("type=").append(android.net.Uri.encode(this.mType, "/")).append(';');
        }
        if (this.mIdentifier != null) {
            sb.append("identifier=").append(android.net.Uri.encode(this.mIdentifier, "/")).append(';');
        }
        if (this.mFlags != 0) {
            sb.append("launchFlags=0x").append(java.lang.Integer.toHexString(this.mFlags)).append(';');
        }
        if (this.mExtendedFlags != 0) {
            sb.append("extendedLaunchFlags=0x").append(java.lang.Integer.toHexString(this.mExtendedFlags)).append(';');
        }
        if (this.mPackage != null && !this.mPackage.equals(str3)) {
            sb.append("package=").append(android.net.Uri.encode(this.mPackage)).append(';');
        }
        if (this.mComponent != null) {
            sb.append("component=").append(android.net.Uri.encode(this.mComponent.flattenToShortString(), "/")).append(';');
        }
        if (this.mSourceBounds != null) {
            sb.append("sourceBounds=").append(android.net.Uri.encode(this.mSourceBounds.flattenToString())).append(';');
        }
        if (this.mExtras != null) {
            for (java.lang.String str4 : this.mExtras.keySet()) {
                java.lang.Object obj = this.mExtras.get(str4);
                if (obj instanceof java.lang.String) {
                    c = 'S';
                } else if (obj instanceof java.lang.Boolean) {
                    c = 'B';
                } else if (obj instanceof java.lang.Byte) {
                    c = 'b';
                } else if (obj instanceof java.lang.Character) {
                    c = 'c';
                } else if (obj instanceof java.lang.Double) {
                    c = android.text.format.DateFormat.DATE;
                } else if (obj instanceof java.lang.Float) {
                    c = 'f';
                } else if (obj instanceof java.lang.Integer) {
                    c = 'i';
                } else if (obj instanceof java.lang.Long) {
                    c = 'l';
                } else {
                    c = obj instanceof java.lang.Short ? 's' : (char) 0;
                }
                if (c != 0) {
                    sb.append(c);
                    sb.append('.');
                    sb.append(android.net.Uri.encode(str4));
                    sb.append('=');
                    sb.append(android.net.Uri.encode(obj.toString()));
                    sb.append(';');
                }
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.mExtras != null) {
            return this.mExtras.describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mAction);
        android.net.Uri.writeToParcel(parcel, this.mData);
        parcel.writeString8(this.mType);
        parcel.writeString8(this.mIdentifier);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mExtendedFlags);
        parcel.writeString8(this.mPackage);
        android.content.ComponentName.writeToParcel(this.mComponent, parcel);
        if (this.mSourceBounds != null) {
            parcel.writeInt(1);
            this.mSourceBounds.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mCategories != null) {
            int size = this.mCategories.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString8(this.mCategories.valueAt(i2));
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.mSelector != null) {
            parcel.writeInt(1);
            this.mSelector.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.mClipData != null) {
            parcel.writeInt(1);
            this.mClipData.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mContentUserHint);
        parcel.writeBundle(this.mExtras);
        if (this.mOriginalIntent != null) {
            parcel.writeInt(1);
            this.mOriginalIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }

    protected Intent(android.os.Parcel parcel) {
        this.mContentUserHint = -2;
        this.mLocalFlags = 2;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        setAction(parcel.readString8());
        this.mData = android.net.Uri.CREATOR.createFromParcel(parcel);
        this.mType = parcel.readString8();
        this.mIdentifier = parcel.readString8();
        this.mFlags = parcel.readInt();
        this.mExtendedFlags = parcel.readInt();
        this.mPackage = parcel.readString8();
        this.mComponent = android.content.ComponentName.readFromParcel(parcel);
        if (parcel.readInt() != 0) {
            this.mSourceBounds = android.graphics.Rect.CREATOR.createFromParcel(parcel);
        }
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mCategories = new android.util.ArraySet<>();
            for (int i = 0; i < readInt; i++) {
                this.mCategories.add(parcel.readString8().intern());
            }
        } else {
            this.mCategories = null;
        }
        if (parcel.readInt() != 0) {
            this.mSelector = new android.content.Intent(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mClipData = new android.content.ClipData(parcel);
        }
        this.mContentUserHint = parcel.readInt();
        this.mExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.mOriginalIntent = new android.content.Intent(parcel);
        }
    }

    public static android.content.Intent parseIntent(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.Intent intent = new android.content.Intent();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.Intent);
        intent.setAction(obtainAttributes.getString(2));
        java.lang.String string = obtainAttributes.getString(3);
        intent.setDataAndType(string != null ? android.net.Uri.parse(string) : null, obtainAttributes.getString(1));
        intent.setIdentifier(obtainAttributes.getString(5));
        java.lang.String string2 = obtainAttributes.getString(0);
        java.lang.String string3 = obtainAttributes.getString(4);
        if (string2 != null && string3 != null) {
            intent.setComponent(new android.content.ComponentName(string2, string3));
        }
        obtainAttributes.recycle();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("categories")) {
                    android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.IntentCategory);
                    java.lang.String string4 = obtainAttributes2.getString(0);
                    obtainAttributes2.recycle();
                    if (string4 != null) {
                        intent.addCategory(string4);
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                } else if (name.equals(TAG_EXTRA)) {
                    if (intent.mExtras == null) {
                        intent.mExtras = new android.os.Bundle();
                    }
                    resources.parseBundleExtra(TAG_EXTRA, attributeSet, intent.mExtras);
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return intent;
    }

    public void saveToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        if (this.mAction != null) {
            xmlSerializer.attribute(null, "action", this.mAction);
        }
        if (this.mData != null) {
            xmlSerializer.attribute(null, "data", this.mData.toString());
        }
        if (this.mType != null) {
            xmlSerializer.attribute(null, "type", this.mType);
        }
        if (this.mIdentifier != null) {
            xmlSerializer.attribute(null, ATTR_IDENTIFIER, this.mIdentifier);
        }
        if (this.mComponent != null) {
            xmlSerializer.attribute(null, "component", this.mComponent.flattenToShortString());
        }
        xmlSerializer.attribute(null, "flags", java.lang.Integer.toHexString(getFlags()));
        if (this.mCategories != null) {
            xmlSerializer.startTag(null, "categories");
            for (int size = this.mCategories.size() - 1; size >= 0; size--) {
                xmlSerializer.attribute(null, ATTR_CATEGORY, this.mCategories.valueAt(size));
            }
            xmlSerializer.endTag(null, "categories");
        }
    }

    public static android.content.Intent restoreFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.Intent intent = new android.content.Intent();
        int depth = xmlPullParser.getDepth();
        for (int attributeCount = xmlPullParser.getAttributeCount() - 1; attributeCount >= 0; attributeCount--) {
            java.lang.String attributeName = xmlPullParser.getAttributeName(attributeCount);
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(attributeCount);
            if ("action".equals(attributeName)) {
                intent.setAction(attributeValue);
            } else if ("data".equals(attributeName)) {
                intent.setData(android.net.Uri.parse(attributeValue));
            } else if ("type".equals(attributeName)) {
                intent.setType(attributeValue);
            } else if (ATTR_IDENTIFIER.equals(attributeName)) {
                intent.setIdentifier(attributeValue);
            } else if ("component".equals(attributeName)) {
                intent.setComponent(android.content.ComponentName.unflattenFromString(attributeValue));
            } else if ("flags".equals(attributeName)) {
                intent.setFlags(java.lang.Integer.parseInt(attributeValue, 16));
            } else {
                android.util.Log.e(TAG, "restoreFromXml: unknown attribute=" + attributeName);
            }
        }
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() >= depth)) {
                break;
            }
            if (next == 2) {
                java.lang.String name = xmlPullParser.getName();
                if ("categories".equals(name)) {
                    for (int attributeCount2 = xmlPullParser.getAttributeCount() - 1; attributeCount2 >= 0; attributeCount2--) {
                        intent.addCategory(xmlPullParser.getAttributeValue(attributeCount2));
                    }
                } else {
                    android.util.Log.w(TAG, "restoreFromXml: unknown name=" + name);
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return intent;
    }

    public static java.lang.String normalizeMimeType(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.lang.String lowerCase = str.trim().toLowerCase(java.util.Locale.ROOT);
        int indexOf = lowerCase.indexOf(59);
        if (indexOf != -1) {
            return lowerCase.substring(0, indexOf);
        }
        return lowerCase;
    }

    public void prepareToLeaveProcess(android.content.Context context) {
        boolean z = true;
        if (this.mComponent != null) {
            z = true ^ java.util.Objects.equals(this.mComponent.getPackageName(), context.getPackageName());
        } else if (this.mPackage != null) {
            z = true ^ java.util.Objects.equals(this.mPackage, context.getPackageName());
        }
        prepareToLeaveProcess(z);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0148, code lost:
    
        if (r1.equals(android.content.Intent.ACTION_PROVIDER_CHANGED) != false) goto L100;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void prepareToLeaveProcess(boolean z) {
        char c;
        boolean z2 = false;
        setAllowFds(false);
        if (this.mSelector != null) {
            this.mSelector.prepareToLeaveProcess(z);
        }
        if (this.mClipData != null) {
            this.mClipData.prepareToLeaveProcess(z, getFlags());
        }
        if (this.mOriginalIntent != null) {
            this.mOriginalIntent.prepareToLeaveProcess(z);
        }
        if (this.mExtras != null && !this.mExtras.isParcelled()) {
            java.lang.Object obj = this.mExtras.get(EXTRA_INTENT);
            if (obj instanceof android.content.Intent) {
                ((android.content.Intent) obj).prepareToLeaveProcess(z);
            }
        }
        if (this.mAction != null && this.mData != null && android.os.StrictMode.vmFileUriExposureEnabled() && z) {
            java.lang.String str = this.mAction;
            switch (str.hashCode()) {
                case -2015721043:
                    if (str.equals(ACTION_PACKAGE_NEEDS_INTEGRITY_VERIFICATION)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1823790459:
                    if (str.equals(ACTION_MEDIA_SHARED)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1665311200:
                    if (str.equals(ACTION_MEDIA_REMOVED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1514214344:
                    if (str.equals(ACTION_MEDIA_MOUNTED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1142424621:
                    if (str.equals(ACTION_MEDIA_SCANNER_FINISHED)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -963871873:
                    if (str.equals(ACTION_MEDIA_UNMOUNTED)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -625887599:
                    if (str.equals(ACTION_MEDIA_EJECT)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 257177710:
                    if (str.equals(ACTION_MEDIA_NOFS)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 410719838:
                    if (str.equals(ACTION_MEDIA_UNSHARED)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 582421979:
                    if (str.equals(ACTION_PACKAGE_NEEDS_VERIFICATION)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 852070077:
                    if (str.equals(ACTION_MEDIA_SCANNER_SCAN_FILE)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1412829408:
                    if (str.equals(ACTION_MEDIA_SCANNER_STARTED)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 1431947322:
                    if (str.equals(ACTION_MEDIA_UNMOUNTABLE)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1599438242:
                    if (str.equals(ACTION_PACKAGE_ENABLE_ROLLBACK)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 1920444806:
                    if (str.equals(ACTION_PACKAGE_VERIFIED)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1964681210:
                    if (str.equals(ACTION_MEDIA_CHECKING)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2045140818:
                    if (str.equals(ACTION_MEDIA_BAD_REMOVAL)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    this.mData.checkFileUriExposed("Intent.getData()");
                    break;
            }
        }
        if (this.mAction != null && this.mData != null && android.os.StrictMode.vmContentUriWithoutPermissionEnabled() && z) {
            java.lang.String str2 = this.mAction;
            switch (str2.hashCode()) {
                case -577088908:
                    if (str2.equals(android.provider.ContactsContract.QuickContact.ACTION_QUICK_CONTACT)) {
                        z2 = true;
                        break;
                    }
                    z2 = -1;
                    break;
                case 1662413067:
                    break;
                default:
                    z2 = -1;
                    break;
            }
            switch (z2) {
                case false:
                case true:
                    break;
                default:
                    this.mData.checkContentUriWithoutPermission("Intent.getData()", getFlags());
                    break;
            }
        }
        if (ACTION_MEDIA_SCANNER_SCAN_FILE.equals(this.mAction) && this.mData != null && "file".equals(this.mData.getScheme()) && z) {
            android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) android.app.AppGlobals.getInitialApplication().getSystemService(android.os.storage.StorageManager.class);
            java.io.File file = new java.io.File(this.mData.getPath());
            java.io.File translateAppToSystem = storageManager.translateAppToSystem(file, android.os.Process.myPid(), android.os.Process.myUid());
            if (!java.util.Objects.equals(file, translateAppToSystem)) {
                android.util.Log.v(TAG, "Translated " + file + " to " + translateAppToSystem);
                this.mData = android.net.Uri.fromFile(translateAppToSystem);
            }
        }
        if (android.os.StrictMode.vmUnsafeIntentLaunchEnabled()) {
            if ((this.mLocalFlags & 2) != 0 && (this.mLocalFlags & 36) == 0) {
                android.os.StrictMode.onUnsafeIntentLaunch(this);
                return;
            }
            if ((this.mLocalFlags & 8) != 0) {
                android.os.StrictMode.onUnsafeIntentLaunch(this);
                return;
            }
            if ((this.mLocalFlags & 16) != 0) {
                if (this.mCategories == null || !this.mCategories.contains(CATEGORY_BROWSABLE) || this.mComponent != null) {
                    android.os.StrictMode.onUnsafeIntentLaunch(this);
                }
            }
        }
    }

    public void prepareToEnterProcess(boolean z, android.content.AttributionSource attributionSource) {
        if (z) {
            prepareToEnterProcess(4, attributionSource);
        } else {
            prepareToEnterProcess(0, attributionSource);
        }
    }

    public void prepareToEnterProcess(int i, android.content.AttributionSource attributionSource) {
        android.bluetooth.BluetoothDevice bluetoothDevice;
        setDefusable(true);
        if (this.mSelector != null) {
            this.mSelector.prepareToEnterProcess(0, attributionSource);
        }
        if (this.mClipData != null) {
            this.mClipData.prepareToEnterProcess(attributionSource);
        }
        if (this.mOriginalIntent != null) {
            this.mOriginalIntent.prepareToEnterProcess(0, attributionSource);
        }
        if (this.mContentUserHint != -2 && android.os.UserHandle.getAppId(android.os.Process.myUid()) != 1000) {
            fixUris(this.mContentUserHint);
            this.mContentUserHint = -2;
        }
        this.mLocalFlags = i | this.mLocalFlags;
        if (this.mAction != null && this.mAction.startsWith("android.bluetooth.") && hasExtra("android.bluetooth.device.extra.DEVICE") && (bluetoothDevice = (android.bluetooth.BluetoothDevice) getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class)) != null) {
            bluetoothDevice.prepareToEnterProcess(attributionSource);
        }
    }

    public boolean hasWebURI() {
        if (getData() == null) {
            return false;
        }
        java.lang.String scheme = getScheme();
        if (android.text.TextUtils.isEmpty(scheme)) {
            return false;
        }
        return scheme.equals(android.content.IntentFilter.SCHEME_HTTP) || scheme.equals(android.content.IntentFilter.SCHEME_HTTPS);
    }

    public boolean isWebIntent() {
        return "android.intent.action.VIEW".equals(this.mAction) && hasWebURI();
    }

    private boolean isImageCaptureIntent() {
        return "android.media.action.IMAGE_CAPTURE".equals(this.mAction) || "android.media.action.IMAGE_CAPTURE_SECURE".equals(this.mAction) || "android.media.action.VIDEO_CAPTURE".equals(this.mAction);
    }

    public boolean isImplicitImageCaptureIntent() {
        return this.mPackage == null && this.mComponent == null && isImageCaptureIntent();
    }

    public boolean isMismatchingFilter() {
        return (this.mExtendedFlags & 1) != 0;
    }

    public void fixUris(int i) {
        android.net.Uri uri;
        android.net.Uri data = getData();
        if (data != null) {
            this.mData = android.content.ContentProvider.maybeAddUserId(data, i);
        }
        if (this.mClipData != null) {
            this.mClipData.fixUris(i);
        }
        java.lang.String action = getAction();
        if (ACTION_SEND.equals(action)) {
            android.net.Uri uri2 = (android.net.Uri) getParcelableExtra(EXTRA_STREAM, android.net.Uri.class);
            if (uri2 != null) {
                putExtra(EXTRA_STREAM, android.content.ContentProvider.maybeAddUserId(uri2, i));
                return;
            }
            return;
        }
        if (ACTION_SEND_MULTIPLE.equals(action)) {
            java.util.ArrayList parcelableArrayListExtra = getParcelableArrayListExtra(EXTRA_STREAM, android.net.Uri.class);
            if (parcelableArrayListExtra != null) {
                java.util.ArrayList<? extends android.os.Parcelable> arrayList = new java.util.ArrayList<>();
                for (int i2 = 0; i2 < parcelableArrayListExtra.size(); i2++) {
                    arrayList.add(android.content.ContentProvider.maybeAddUserId((android.net.Uri) parcelableArrayListExtra.get(i2), i));
                }
                putParcelableArrayListExtra(EXTRA_STREAM, arrayList);
                return;
            }
            return;
        }
        if (isImageCaptureIntent() && (uri = (android.net.Uri) getParcelableExtra("output", android.net.Uri.class)) != null) {
            putExtra("output", android.content.ContentProvider.maybeAddUserId(uri, i));
        }
    }

    public boolean migrateExtraStreamToClipData() {
        return migrateExtraStreamToClipData(android.app.AppGlobals.getInitialApplication());
    }

    public boolean migrateExtraStreamToClipData(android.content.Context context) {
        boolean z;
        int i;
        if ((this.mExtras != null && this.mExtras.isParcelled()) || getClipData() != null) {
            return false;
        }
        java.lang.String action = getAction();
        if (ACTION_CHOOSER.equals(action)) {
            try {
                android.content.Intent intent = (android.content.Intent) getParcelableExtra(EXTRA_INTENT, android.content.Intent.class);
                if (intent == null) {
                    z = false;
                } else {
                    z = intent.migrateExtraStreamToClipData(context) | false;
                }
            } catch (java.lang.ClassCastException e) {
                z = false;
            }
            try {
                android.os.Parcelable[] parcelableArrayExtra = getParcelableArrayExtra(EXTRA_INITIAL_INTENTS);
                if (parcelableArrayExtra != null) {
                    for (android.os.Parcelable parcelable : parcelableArrayExtra) {
                        android.content.Intent intent2 = (android.content.Intent) parcelable;
                        if (intent2 != null) {
                            z |= intent2.migrateExtraStreamToClipData(context);
                        }
                    }
                }
            } catch (java.lang.ClassCastException e2) {
            }
            return z;
        }
        if (ACTION_SEND.equals(action)) {
            try {
                android.net.Uri uri = (android.net.Uri) getParcelableExtra(EXTRA_STREAM, android.net.Uri.class);
                java.lang.CharSequence charSequenceExtra = getCharSequenceExtra(EXTRA_TEXT);
                java.lang.String stringExtra = getStringExtra(EXTRA_HTML_TEXT);
                if (uri != null || charSequenceExtra != null || stringExtra != null) {
                    setClipData(new android.content.ClipData(null, new java.lang.String[]{getType()}, new android.content.ClipData.Item(charSequenceExtra, stringExtra, null, uri)));
                    if (uri != null) {
                        addFlags(1);
                    }
                    return true;
                }
            } catch (java.lang.ClassCastException e3) {
            }
        } else if (ACTION_SEND_MULTIPLE.equals(action)) {
            try {
                java.util.ArrayList parcelableArrayListExtra = getParcelableArrayListExtra(EXTRA_STREAM, android.net.Uri.class);
                java.util.ArrayList<java.lang.CharSequence> charSequenceArrayListExtra = getCharSequenceArrayListExtra(EXTRA_TEXT);
                java.util.ArrayList<java.lang.String> stringArrayListExtra = getStringArrayListExtra(EXTRA_HTML_TEXT);
                if (parcelableArrayListExtra == null) {
                    i = -1;
                } else {
                    i = parcelableArrayListExtra.size();
                }
                if (charSequenceArrayListExtra != null) {
                    if (i >= 0 && i != charSequenceArrayListExtra.size()) {
                        return false;
                    }
                    i = charSequenceArrayListExtra.size();
                }
                if (stringArrayListExtra != null) {
                    if (i >= 0 && i != stringArrayListExtra.size()) {
                        return false;
                    }
                    i = stringArrayListExtra.size();
                }
                if (i > 0) {
                    android.content.ClipData clipData = new android.content.ClipData(null, new java.lang.String[]{getType()}, makeClipItem(parcelableArrayListExtra, charSequenceArrayListExtra, stringArrayListExtra, 0));
                    for (int i2 = 1; i2 < i; i2++) {
                        clipData.addItem(makeClipItem(parcelableArrayListExtra, charSequenceArrayListExtra, stringArrayListExtra, i2));
                    }
                    setClipData(clipData);
                    if (parcelableArrayListExtra != null) {
                        addFlags(1);
                    }
                    return true;
                }
            } catch (java.lang.ClassCastException e4) {
            }
        } else if (isImageCaptureIntent()) {
            try {
                android.net.Uri uri2 = (android.net.Uri) getParcelableExtra("output", android.net.Uri.class);
                if (uri2 != null) {
                    android.net.Uri maybeConvertFileToContentUri = maybeConvertFileToContentUri(context, uri2);
                    putExtra("output", maybeConvertFileToContentUri);
                    setClipData(android.content.ClipData.newRawUri("", maybeConvertFileToContentUri));
                    addFlags(3);
                    return true;
                }
            } catch (java.lang.ClassCastException e5) {
                return false;
            }
        }
        return false;
    }

    private android.net.Uri maybeConvertFileToContentUri(android.content.Context context, android.net.Uri uri) {
        if ("file".equals(uri.getScheme()) && context.getApplicationInfo().targetSdkVersion < 30) {
            java.io.File file = new java.io.File(uri.getPath());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                uri = android.provider.MediaStore.scanFile(context.getContentResolver(), new java.io.File(uri.getPath()));
                if (uri != null) {
                    return uri;
                }
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Ignoring failure to create file " + file, e);
            }
        }
        return uri;
    }

    public static java.lang.String dockStateToString(int i) {
        switch (i) {
            case 0:
                return "EXTRA_DOCK_STATE_UNDOCKED";
            case 1:
                return "EXTRA_DOCK_STATE_DESK";
            case 2:
                return "EXTRA_DOCK_STATE_CAR";
            case 3:
                return "EXTRA_DOCK_STATE_LE_DESK";
            case 4:
                return "EXTRA_DOCK_STATE_HE_DESK";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static android.content.ClipData.Item makeClipItem(java.util.ArrayList<android.net.Uri> arrayList, java.util.ArrayList<java.lang.CharSequence> arrayList2, java.util.ArrayList<java.lang.String> arrayList3, int i) {
        return new android.content.ClipData.Item(arrayList2 != null ? arrayList2.get(i) : null, arrayList3 != null ? arrayList3.get(i) : null, null, arrayList != null ? arrayList.get(i) : null);
    }

    public boolean isDocument() {
        return (this.mFlags & 524288) == 524288;
    }

    @java.lang.Deprecated
    public boolean isSandboxActivity(android.content.Context context) {
        if (this.mAction != null && this.mAction.equals("android.app.sdksandbox.action.START_SANDBOXED_ACTIVITY")) {
            return true;
        }
        java.lang.String sdkSandboxPackageName = context.getPackageManager().getSdkSandboxPackageName();
        if (this.mPackage == null || !this.mPackage.equals(sdkSandboxPackageName)) {
            return this.mComponent != null && this.mComponent.getPackageName().equals(sdkSandboxPackageName);
        }
        return true;
    }
}
