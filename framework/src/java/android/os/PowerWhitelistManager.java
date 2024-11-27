package android.os;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes3.dex */
public class PowerWhitelistManager {
    public static final int EVENT_MMS = 2;
    public static final int EVENT_SMS = 1;
    public static final int EVENT_UNSPECIFIED = 0;
    public static final int REASON_ACTIVITY_RECOGNITION = 103;
    public static final int REASON_ACTIVITY_STARTER = 52;
    public static final int REASON_ALARM_MANAGER_ALARM_CLOCK = 301;
    public static final int REASON_ALARM_MANAGER_WHILE_IDLE = 302;
    public static final int REASON_ALLOWLISTED_PACKAGE = 65;
    public static final int REASON_APPOP = 66;
    public static final int REASON_BACKGROUND_ACTIVITY_PERMISSION = 58;
    public static final int REASON_BACKGROUND_FGS_PERMISSION = 59;
    public static final int REASON_BOOT_COMPLETED = 200;
    public static final int REASON_COMPANION_DEVICE_MANAGER = 57;
    public static final int REASON_DENIED = -1;
    public static final int REASON_DEVICE_DEMO_MODE = 63;
    public static final int REASON_DEVICE_OWNER = 55;
    public static final int REASON_DOMAIN_VERIFICATION_V1 = 307;
    public static final int REASON_DOMAIN_VERIFICATION_V2 = 308;
    public static final int REASON_EVENT_MMS = 315;
    public static final int REASON_EVENT_SMS = 314;
    public static final int REASON_FGS_BINDING = 54;
    public static final int REASON_GEOFENCING = 100;
    public static final int REASON_INSTR_BACKGROUND_ACTIVITY_PERMISSION = 60;
    public static final int REASON_INSTR_BACKGROUND_FGS_PERMISSION = 61;
    public static final int REASON_KEY_CHAIN = 304;

    @android.annotation.SystemApi
    public static final int REASON_LOCATION_PROVIDER = 312;
    public static final int REASON_LOCKED_BOOT_COMPLETED = 202;
    public static final int REASON_MEDIA_BUTTON = 313;
    public static final int REASON_NOTIFICATION_SERVICE = 310;
    public static final int REASON_OTHER = 1;
    public static final int REASON_PACKAGE_REPLACED = 311;
    public static final int REASON_PACKAGE_VERIFIER = 305;
    public static final int REASON_PRE_BOOT_COMPLETED = 201;
    public static final int REASON_PROC_STATE_BFGS = 15;
    public static final int REASON_PROC_STATE_BTOP = 13;
    public static final int REASON_PROC_STATE_FGS = 14;
    public static final int REASON_PROC_STATE_PERSISTENT = 10;
    public static final int REASON_PROC_STATE_PERSISTENT_UI = 11;
    public static final int REASON_PROC_STATE_TOP = 12;
    public static final int REASON_PROFILE_OWNER = 56;
    public static final int REASON_PUSH_MESSAGING = 101;
    public static final int REASON_PUSH_MESSAGING_OVER_QUOTA = 102;
    public static final int REASON_SERVICE_LAUNCH = 303;
    public static final int REASON_SHELL = 316;
    public static final int REASON_START_ACTIVITY_FLAG = 53;
    public static final int REASON_SYNC_MANAGER = 306;
    public static final int REASON_SYSTEM_ALERT_WINDOW_PERMISSION = 62;
    public static final int REASON_SYSTEM_ALLOW_LISTED = 300;
    public static final int REASON_SYSTEM_UID = 51;
    public static final int REASON_UID_VISIBLE = 50;
    public static final int REASON_UNKNOWN = 0;
    public static final int REASON_VPN = 309;
    public static final int TEMPORARY_ALLOWLIST_TYPE_FOREGROUND_SERVICE_ALLOWED = 0;
    public static final int TEMPORARY_ALLOWLIST_TYPE_FOREGROUND_SERVICE_NOT_ALLOWED = 1;
    private final android.content.Context mContext;
    private final android.os.PowerExemptionManager mPowerExemptionManager;
    private final android.os.IDeviceIdleController mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ReasonCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TempAllowListType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WhitelistEvent {
    }

    public PowerWhitelistManager(android.content.Context context) {
        this.mContext = context;
        this.mService = ((android.os.DeviceIdleManager) context.getSystemService(android.os.DeviceIdleManager.class)).getService();
        this.mPowerExemptionManager = (android.os.PowerExemptionManager) context.getSystemService(android.os.PowerExemptionManager.class);
    }

    @java.lang.Deprecated
    public void addToWhitelist(java.lang.String str) {
        this.mPowerExemptionManager.addToPermanentAllowList(str);
    }

    @java.lang.Deprecated
    public void addToWhitelist(java.util.List<java.lang.String> list) {
        this.mPowerExemptionManager.addToPermanentAllowList(list);
    }

    @java.lang.Deprecated
    public int[] getWhitelistedAppIds(boolean z) {
        return this.mPowerExemptionManager.getAllowListedAppIds(z);
    }

    @java.lang.Deprecated
    public boolean isWhitelisted(java.lang.String str, boolean z) {
        return this.mPowerExemptionManager.isAllowListed(str, z);
    }

    @java.lang.Deprecated
    public void removeFromWhitelist(java.lang.String str) {
        this.mPowerExemptionManager.removeFromPermanentAllowList(str);
    }

    @java.lang.Deprecated
    public void whitelistAppTemporarily(java.lang.String str, long j, int i, java.lang.String str2) {
        this.mPowerExemptionManager.addToTemporaryAllowList(str, i, str2, j);
    }

    @java.lang.Deprecated
    public void whitelistAppTemporarily(java.lang.String str, long j) {
        this.mPowerExemptionManager.addToTemporaryAllowList(str, 0, str, j);
    }

    @java.lang.Deprecated
    public long whitelistAppTemporarilyForEvent(java.lang.String str, int i, java.lang.String str2) {
        return this.mPowerExemptionManager.addToTemporaryAllowListForEvent(str, 0, str2, i);
    }

    @java.lang.Deprecated
    public long whitelistAppTemporarilyForEvent(java.lang.String str, int i, int i2, java.lang.String str2) {
        return this.mPowerExemptionManager.addToTemporaryAllowListForEvent(str, i2, str2, i);
    }

    @java.lang.Deprecated
    public static int getReasonCodeFromProcState(int i) {
        return android.os.PowerExemptionManager.getReasonCodeFromProcState(i);
    }

    @java.lang.Deprecated
    public static java.lang.String reasonCodeToString(int i) {
        return android.os.PowerExemptionManager.reasonCodeToString(i);
    }
}
